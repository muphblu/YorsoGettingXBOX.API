using System.Net;
using System.Net.Http;
using System.Text;
using System.Web.Helpers;
using System.Web.Http;
using System.Web.Mvc;
using System.Web.Script.Serialization;
using YorsoGettingXbox.Models;

namespace YorsoGettingXbox.Controllers
{
    public class HomeController : ApiController
    {
        public HttpResponseMessage Index()
        {
            var response = Request.CreateResponse(HttpStatusCode.OK);
            response.Content = new StringContent("Home-Index", Encoding.UTF8);
            return response;
        }
    }
}
