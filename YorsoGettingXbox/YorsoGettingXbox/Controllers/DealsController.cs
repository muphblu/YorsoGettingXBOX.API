using System;
using System.Collections;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Security.Cryptography;
using System.Security.Policy;
using System.Text;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using System.Web.Script.Serialization;
using Newtonsoft.Json;
using YorsoGettingXbox.Models;

namespace YorsoGettingXbox.Controllers
{
    [RoutePrefix("api/deals")]

    public class DealsController : ApiController
    {
        private static int _nextNum;
        public static IList<DealEntity> Deals = new List<DealEntity>();
        public static int NextNum => _nextNum++;

        // GET: api/Deals
        public IList<DealEntity> Get()
        {
            return Deals;
        }

        // GET: api/Deals/5
        public DealEntity Get(int id)
        {
            var deal = Deals[id];
            return deal;
        }

        // GET: api/deals/1/documents
        [Route("{id:int}/documents")]
        public IList<DocumentEntity> GetDealDocuments(int id)
        {
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

            var deal = Deals[id];
            var root = HttpContext.Current.Server.MapPath("~/Files");
            var provider = new MultipartFormDataStreamProvider(root);

            try
            {
                // Read the form data.
                await Request.Content.ReadAsMultipartAsync(provider);

                // This illustrates how to get the file names.
                foreach (var file in provider.FileData)
                {
                    var doc = new DocumentEntity()
                    {
                        Id = 1,
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
                                continue; // dont upload files without extension
                            }

                            doc.Link = "/Files/" + fileName; // just for tests the extension
                        }
                    }
                    //do not upload twice
                    if (File.Exists(root + "/" + fileName)) continue;

                    File.Move(file.LocalFileName, root + "/" + fileName);
                    deal.Documents.Add(doc);
                }
                var response = Request.CreateResponse(HttpStatusCode.OK);
                response.Content = new StringContent(new JavaScriptSerializer().Serialize(deal), Encoding.UTF8, "application/json");
                return response;
            }
            catch (System.Exception e)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, e);
            }
        }

        // POST: api/deals
        [HttpPost]
        public DealEntity Post([FromBody]DealEntity entity)
        {
            entity.Id = NextNum;
            entity.ContractId = "123123123123123"; // get from Etherium
            entity.Documents = new List<DocumentEntity>();
            Deals.Add(entity);
            return entity;
        }

        // PUT: api/Deals/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE: api/Deals/5
        public void Delete(int id)
        {
        }
    }
}
