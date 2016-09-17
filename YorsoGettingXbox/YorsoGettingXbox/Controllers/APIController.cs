using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace YorsoGettingXbox.Controllers
{
    public class APIController : ApiController
    {
        [HttpPost]
        public HttpResponseMessage UploadDocument()
        {
            return new HttpResponseMessage(HttpStatusCode.Accepted);
        }

        [HttpGet]
        public HttpResponseMessage Deals()
        {
            return new HttpResponseMessage(HttpStatusCode.Accepted);
        }

        [HttpGet]
        public HttpResponseMessage Documents()
        {
            return new HttpResponseMessage(HttpStatusCode.Accepted);
        }

        [HttpPost]
        public HttpResponseMessage AddDeal()
        {
            return new HttpResponseMessage(HttpStatusCode.Accepted);
        }

        [HttpPost]
        public HttpResponseMessage AddDocument()
        {
            return new HttpResponseMessage(HttpStatusCode.Accepted);
        }

        [HttpPost]
        public HttpResponseMessage Sign()
        {
            return new HttpResponseMessage(HttpStatusCode.Accepted);
        }

        [HttpGet]
        public HttpResponseMessage Check()
        {
            return new HttpResponseMessage(HttpStatusCode.Accepted);
        }

        [HttpGet]
        public HttpResponseMessage DocInfo()
        {
            return new HttpResponseMessage(HttpStatusCode.Accepted);
        }
    }
}
