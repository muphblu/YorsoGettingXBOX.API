using System;
using Newtonsoft.Json;

namespace YorsoGettingXbox.Models
{
    public class SignInfoEntity
    {
        public string SignDate { get; set; }

        public SignerEntity Signer { get; set; }
        public bool IsSigned { get; set; }
        public string TransactionId { get; set; }
    }
}