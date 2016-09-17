using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace YorsoGettingXbox.Models
{
    public class SignInfoEntity
    {
        public DateTime SignDate { get; set; }
        public SignerEntity Signer { get; set; }
        public bool IsSigned { get; set; }
        public string TransactionId { get; set; }

    }
}