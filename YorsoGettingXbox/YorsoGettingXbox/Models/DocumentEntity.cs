using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace YorsoGettingXbox.Models
{
    public class DocumentEntity
    {
        public int Id { get; set; }
        public int Name { get; set; }
        public SignInfoEntity[] SignInfo { get; set; }
    }
}