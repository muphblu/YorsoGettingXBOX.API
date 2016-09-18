using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using System.Web.Http.Cors;
using System.Web.Script.Serialization;
using System.Web.WebPages;
using YorsoGettingXbox.Models;

namespace YorsoGettingXbox.Controllers
{
    [RoutePrefix("api/deals")]
    [EnableCors(origins: "*", headers: "*", methods: "*")]

    public class DealsController : ApiController
    {
        private static int _nextNum;
        public static IList<DealEntity> Deals = new List<DealEntity>();
        public static int NextNum => _nextNum++;

        private static IList<string> EtherumUsers = new List<string>()
        {
            "0x0702f136d69416a2ba7802a9e5bf5561e91c6a79",
            "0x249171bf442c9981bf4c87f086630c8389aad43b",
            "0x8e91549f6b3660623bb694f82f3fa6068232967c",
        };

        // GET: api/Deals/pending
        [Route("pending")]
        public IList<DealEntity> GetPending()
        {
            var responseDeals = new List<DealEntity>();
            foreach (var deal in Deals)
            {
                if (deal.ContractId.IsEmpty())
                {
                    responseDeals.Add(deal);
                }
            }
            return responseDeals;
        }

        // GET: api/Deals
        public IList<DealEntity> Get()
        {
            return Deals;
        }

        // GET: api/Deals/5
        public DealEntity Get(int id)
        {
            if (Deals.Count < id || !Deals.Any())
            {
                throw new HttpResponseException(HttpStatusCode.NotFound);
            }

            var deal = Deals[id];
            return deal;
        }

        // GET: api/deals/1/documents
        [Route("{id:int}/documents")]
        public IList<DocumentEntity> GetDealDocuments(int id)
        {
            if (!Deals.Any())
            {
                throw new HttpResponseException(HttpStatusCode.NotFound);
            }
            var deal = Deals[id];
            return deal.Documents;
        }

        // POST: api/deals/1/documents
        [Route("{id:int}/documents")]
        public async Task<HttpResponseMessage> PostDealDocuments(int id)
        {
            // Check if the request contains multipart/form-data.
            if (!Request.Content.IsMimeMultipartContent())
            {
                throw new HttpResponseException(HttpStatusCode.UnsupportedMediaType);
            }

            if (!Deals.Any())
            {
                throw new HttpResponseException(HttpStatusCode.NotFound);
            }

            HttpStatusCode resultCode;
            string resultMessage;

            try
            {
                var deal = Deals[id];
                var root = HttpContext.Current.Server.MapPath("~/Files");
                var provider = new MultipartFormDataStreamProvider(root);

                var docNum = 0;
                if (deal.Documents.Any())
                {
                    docNum = deal.Documents.Count;
                }

                // Read the form data.
                await Request.Content.ReadAsMultipartAsync(provider);

                // This illustrates how to get the file names.
                foreach (var file in provider.FileData)
                {
                    var doc = new DocumentEntity()
                    {
                        Id = docNum,
                        Name = file.Headers.ContentDisposition.FileName
                    };
                    string fileName;

                    using (var md5 = MD5.Create())
                    {
                        using (var stream = File.OpenRead(file.LocalFileName))
                        {
                            doc.Hash = BitConverter.ToString(md5.ComputeHash(stream)).Replace("-", "");
                            var fileExtPos = doc.Name.LastIndexOf(".", StringComparison.Ordinal);
                            if (fileExtPos >= 0)
                            {
                                fileName = doc.Hash + doc.Name.Substring(fileExtPos, doc.Name.Length-fileExtPos-1);
                            }
                            else
                            {
                                continue; // don't upload files without extension
                            }

                            doc.Link = "/Files/" + fileName;
                        }
                    }
                    //replace the same file for the prototype reasons
                    var newFullPath = root + "/" + fileName;
                    if (File.Exists(newFullPath))
                    {
                        File.Delete(newFullPath);
                    };

                    File.Move(file.LocalFileName, newFullPath);
                    doc.SignInfo = new List<SignInfoEntity>();
                    foreach (var user in EtherumUsers)
                    {
                        doc.SignInfo.Add(new SignInfoEntity()
                        {
                            IsSigned = false,
                            SignDate = null,
                            Signer = new SignerEntity()
                            {
                                Id = 0,
                                Name = user,
                                PublicKey = user
                            }
                        });
                    }
                    deal.Documents.Add(doc);
                }
                resultCode = HttpStatusCode.OK;
                resultMessage = new JavaScriptSerializer().Serialize(deal);
            }
            catch (Exception e)
            {
                resultCode = HttpStatusCode.InternalServerError;
                resultMessage = e.Message + "\nTrace: " + e.StackTrace;
            }

            var response = Request.CreateResponse(resultCode);
            response.Content = new StringContent(resultMessage, Encoding.UTF8, "application/json");
            return response;
        }

        // POST: api/deals
        [HttpPost]
        public DealEntity Post([FromBody]DealEntity entity)
        {
            entity.Id = NextNum;
            entity.ContractId = ""; // empty
            entity.Documents = new List<DocumentEntity>();
            Deals.Add(entity);
            return entity;
        }

        // POST: api/Deals/5
        public DealEntity PostDeal(int id, [FromBody]DealEntity entity)
        {
            if (Deals.Count < id || !Deals.Any())
            {
                throw new HttpResponseException(HttpStatusCode.NotFound);
            }

            Deals[id].ContractId = entity.ContractId;
            return Deals[id];
        }

        // DELETE: api/Deals/5
        public void Delete(int id)
        {
        }

        [Route("{dealid:int}/documents/{docid:int}/sign")]
        [HttpPost]
        public SignInfoEntity Sign(int dealid, int docid, SignInfoEntity signInfoEntity)
        {
            return signInfoEntity;
        }

        // GET: api/Deals/documents/pending
        [Route("documents/pending")]
        public IList<DealEntity> GetPendingDocuments()
        {
            var response = new List<DealEntity>();
            foreach (var deal in Deals)
            {
                var dealDocs = new List<DocumentEntity>();
                foreach (var doc in deal.Documents)
                {
                    if (doc.Status == DocumentStatus.Verified)
                    {
                        continue;
                    }
                    dealDocs.Add(doc);
                }
                if (dealDocs.Any())
                {
                    response.Add(new DealEntity()
                    {
                        ContractId = deal.ContractId,
                        Id = deal.Id,
                        Documents = dealDocs
                    });
                }
            }
            return response;
        }
    }
}
