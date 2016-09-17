using System.Web.Helpers;
using System.Web.Http;
using System.Web.Mvc;
using YorsoGettingXbox.Models;

namespace YorsoGettingXbox.Controllers
{
    public class HomeController : ApiController
    {
        public ActionResult Index()
        {
            return new JsonResult();
        }
    }
}
