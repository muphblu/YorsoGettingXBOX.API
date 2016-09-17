using System.Diagnostics;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using System.Web.Script.Serialization;
using YorsoGettingXbox.Models;

namespace YorsoGettingXbox.Controllers
{
    [RoutePrefix("api/deals")]

    public class DealsController : ApiController
    {
        // GET: api/Deals
        public HttpResponseMessage Get()
        {
            var deals = new[]
            {
                new DealEntity() {Description = "Description 1", Title = "Title 1", ID = 1},
                new DealEntity() {Description = "Description 2", Title = "Title 2", ID = 2},
            };
            var response = Request.CreateResponse(HttpStatusCode.OK);
            response.Content = new StringContent(new JavaScriptSerializer().Serialize(deals), Encoding.UTF8, "application/json");
            return response;
        }

        // GET: api/Deals/5
        public HttpResponseMessage Get(int id)
        {
            var response = Request.CreateResponse(HttpStatusCode.OK);
            response.Content = new StringContent(
                new JavaScriptSerializer().Serialize(new DealEntity() { Description = "Description 1", Title = "Title 1", ID = 1 }), 
                Encoding.UTF8, 
                "application/json");
            return response;
        }

        // GET: api/deals/1/documents
        [Route("{id:int}/documents")]
        public HttpResponseMessage GetDealDocuments(int id)
        {
            var documents = new[]
            {
                new DocumentEntity() { ID = 1 },
                new DocumentEntity() { ID = 2 },
                new DocumentEntity() { ID = 3 },
            };

            var response = Request.CreateResponse(HttpStatusCode.OK);
            response.Content = new StringContent(
                new JavaScriptSerializer().Serialize(documents), 
                Encoding.UTF8, 
                "application/json");
            return response;
        }

        [Route("{id:int}/documents")]
        public async Task<HttpResponseMessage> PostFormData()
        {
            // Check if the request contains multipart/form-data.
            if (!Request.Content.IsMimeMultipartContent())
            {
                throw new HttpResponseException(HttpStatusCode.UnsupportedMediaType);
            }

            var root = HttpContext.Current.Server.MapPath("~/App_Data");
            var provider = new MultipartFormDataStreamProvider(root);

            try
            {
                // Read the form data.
                await Request.Content.ReadAsMultipartAsync(provider);

                // This illustrates how to get the file names.
                foreach (MultipartFileData file in provider.FileData)
                {
                    Trace.WriteLine(file.Headers.ContentDisposition.FileName);
                    Trace.WriteLine("Server file path: " + file.LocalFileName);
                }
                return Request.CreateResponse(HttpStatusCode.OK);
            }
            catch (System.Exception e)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, e);
            }
        }

        // POST: api/Deals
        public void Post([FromBody]string value)
        {
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
