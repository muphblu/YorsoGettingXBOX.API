using System;
namespace YorsoGettingXbox.Models
{
    public class DealEntity
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public string ContractId { get; set; }
        public DocumentEntity[] Documents { get; set; }
    }
}