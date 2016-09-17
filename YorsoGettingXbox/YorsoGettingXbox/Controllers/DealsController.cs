﻿using System.Collections;
using System.Collections.Generic;
using System.Net;
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
            return documents;
        }

        // POST: api/deals
        [HttpPost]
        public DealEntity Post([FromBody]DealEntity entity)
        {
            entity.Id = NextNum;
            entity.ContractId = "123123123123123"; // get from Etherium
            entity.Documents = new DocumentEntity[] { };
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
