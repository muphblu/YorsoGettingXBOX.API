pragma solidity ^0.4.0;
contract Deal { 
    struct Document {
        string name;
        string hash;
        mapping(address => bool) signedby;
        address[] required;
    }

    Document[] public _documents;
    mapping (address => bool) public _participants;

    function Deal(address[] participants) {
        for(uint i=0; i<participants.length; i++) {
            _participants[participants[i]] = true;
        }
    }

    function addDocument(string name, string hash, address[] required) returns (bool result) {

        _documents.push(Document({
                name: name,
                hash: hash,
                required: required
            }));
        result = true;
    }

    function signDocument(string hash) returns (bool result) {
        result = false; 
        if(!_participants[msg.sender]) {
            return;
        }

        for (uint i=0; i<_documents.length; i++) {
            if (stringsEqual(_documents[i].hash, hash)) {
                _documents[i].signedby[msg.sender] = true;
                result = true;
                return;
            }
            i++;
        }
    }

    function check(string hash) constant returns (bool result) {
        result = false;
        if(!_participants[msg.sender]) {
            return;
        }
        
        for (uint i=0; i<_documents.length; i++) {
            if (stringsEqual(_documents[i].hash, hash)) {
                for(uint j=0; j<_documents[i].required.length; j++) {
                    if (!_documents[i].signedby[_documents[i].required[j]]) {
                        return;
                    }
                }
                result = true;
                return;
            }
            i++;
        }
    }
    
    function stringsEqual (string storage _a, string memory _b) internal returns (bool) {
        bytes storage a = bytes(_a);
        bytes memory b = bytes(_b);
        
        if(a.length != b.length) return false;
        for(uint i=0; i<a.length; i++) {
            if(a[i] != b[i])
               return false;
        }
        return true;
    }
}                                          