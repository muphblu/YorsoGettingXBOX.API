using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace YorsoGettingXbox.Models
{
    public class SignerEntity
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string PrivateKey { get; set; }
        public string PublicKey { get; set; }
    }
}