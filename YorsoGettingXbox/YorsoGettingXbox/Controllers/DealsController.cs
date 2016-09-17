﻿using System.Net;
using System.Net.Http;
using System.Text;
using System.Web.Http;
using System.Web.Script.Serialization;
using YorsoGettingXbox.Models;

namespace YorsoGettingXbox.Controllers
{
    [RoutePrefix("api/deals")]

    public class DealsController : ApiController
    {
        // GET: api/Deals
        public DealEntity[] Get()
        {
            var deals = new[]
            {
                new DealEntity() {Description = "Description 1", Title = "Title 1", Id = 1},
                new DealEntity() {Description = "Description 2", Title = "Title 2", Id = 2},
            };
            /*var response = Request.CreateResponse(HttpStatusCode.OK);
            response.Content = new StringContent(new JavaScriptSerializer().Serialize(deals), Encoding.UTF8, "application/json");
            return response;*/
            return deals;
        }

        // GET: api/Deals/5
        public DealEntity Get(int id)
        {
            /*var response = Request.CreateResponse(HttpStatusCode.OK);
            response.Content = new StringContent(
                new JavaScriptSerializer().Serialize(new DealEntity() { Description = "Description 1", Title = "Title 1", ID = 1 }), 
                Encoding.UTF8, 
                "application/json");*/
            return new DealEntity { Description = "Description 1", Title = "Title 1", Id = 1 };
        }

        // GET: api/deals/1/documents
        [Route("{id:int}/documents")]
        public DocumentEntity[] GetDealDocuments(int id)
        {
            var documents = new[]
            {
                new DocumentEntity() { Id = 1 },
                new DocumentEntity() { Id = 2 },
                new DocumentEntity() { Id = 3 },
            };

            /*var response = Request.CreateResponse(HttpStatusCode.OK);
            response.Content = new StringContent(
                new JavaScriptSerializer().Serialize(documents), 
                Encoding.UTF8, 
                "application/json");*/
            return documents;
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
