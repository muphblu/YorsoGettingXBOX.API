using System;
using System.Collections.Generic;

namespace YorsoGettingXbox.Models
{
    public class DealEntity
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public string ContractId { get; set; }
        public IList<DocumentEntity> Documents { get; set; }
    }
}