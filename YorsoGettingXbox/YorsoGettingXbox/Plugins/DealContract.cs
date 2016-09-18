using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
//using Nethereum.Web3;

namespace YorsoGettingXbox.Plugins
{
    public class DealContract
    {
        public const string PersonOne = "0x0702f136d69416a2ba7802a9e5bf5561e91c6a79";
        public const string PersonTwo = "0x249171bf442c9981bf4c87f086630c8389aad43b";
        public const string PersonThree = "0x8e91549f6b3660623bb694f82f3fa6068232967c";
        public const string Password = "!234Qwer";

        private const string Abi = @"[{""constant"":false,""inputs"":[{""name"":""val"",""type"":""int256""}],""name"":""multiply"",""outputs"":[{""name"":""d"",""type"":""int256""}],""type"":""function""},{""inputs"":[{""name"":""multiplier"",""type"":""int256""}],""type"":""constructor""}]";
        private const string Host = "http://myethnode1.northeurope.cloudapp.azure.com:8080";

        private string _person;
        //private Web3 _web3;

        public DealContract(string person)
        {
            _person = person;
            //_web3 = new Web3(Host);
        }
    }
}