/**
 * Created by stinger112 on 17.09.16.
 */

"use strict";

const baseUrl = 'http://we-need-xbox.azurewebsites.net';

class API {
    constructor() {
        // this.dealsTimeout = setTimeout(() => getDeals, 1)
    }

    // /api/deals
    // [
    //     {
    //         "ID":12,
    //         "Title": "title"
    //         "Description":"desc"
    //     },
    //     {
    //         "ID":12,
    //         "Title": "title"
    //         "Description":"desc"
    //     }
    // ]
    static async getDeals() {
        let body = await fetch(`${baseUrl}/api/deals`);

        return body.json();
    }

    // GET /api/deals/<DealID>
    // {
    //     "Id": 0,
    //     "Title": "Title 1",
    //     "Description": "Description 1",
    //     "ContractId": "123123123123123",
    //     "Documents": [
    // {
    //     "Id": 1,
    //     "Name": "\"data.txt\"",
    //     "Link": "/Files/D7D323EF1C5FB9F271E3EAB6BD00615D.txt",
    //     "SignInfo": null,
    //     "Status": 0,
    //     "Hash": "D7D323EF1C5FB9F271E3EAB6BD00615D"
    // },
    // {
    //     "Id": 1,
    //     "Name": "\"rules.txt\"",
    //     "Link": "/Files/B4CA0C01B2015928036A1304FB4936F4.txt",
    //     "SignInfo": null,
    //     "Status": 0,
    //     "Hash": "B4CA0C01B2015928036A1304FB4936F4"
    // }
    //     ]
    // }
    static async getDeal(id) {
        let body = await fetch(`${baseUrl}/api/deals/${id}`);

        return body.json();
    }

    // POST /api/deals/<DealID>/documents
    // {
    //     "Id": 0,
    //     "Title": "Title 1",
    //     "Description": "Description 1",
    //     "ContractId": "123123123123123",
    //     "Documents": [
    //         {
    //             "Id": 0,
    //             "Name": "\"data.txt\"",
    //             "Link": "/Files/D7D323EF1C5FB9F271E3EAB6BD00615D.txt",
    //             "SignInfo": null,
    //             "Status": 0,
    //             "Hash": "D7D323EF1C5FB9F271E3EAB6BD00615D"
    //         },
    //         {
    //             "Id": 1,
    //             "Name": "\"data.txt\"",
    //             "Link": "/Files/D7D323EF1C5FB9F271E3EAB6BD00615D.txt",
    //             "SignInfo": null,
    //             "Status": 0,
    //             "Hash": "D7D323EF1C5FB9F271E3EAB6BD00615D"
    //         },
    //         {
    //             "Id": 2,
    //             "Name": "\"rules.txt\"",
    //             "Link": "/Files/B4CA0C01B2015928036A1304FB4936F4.txt",
    //             "SignInfo": null,
    //             "Status": 0,
    //             "Hash": "B4CA0C01B2015928036A1304FB4936F4"
    //         },
    //         {
    //             "Id": 3,
    //             "Name": "\"desktop.ini\"",
    //             "Link": "/Files/9E36CC3537EE9EE1E3B10FA4E761045B.ini",
    //             "SignInfo": null,
    //             "Status": 0,
    //             "Hash": "9E36CC3537EE9EE1E3B10FA4E761045B"
    //         }
    //         ]
    // }
    static async postDocument(dealId, file) {
        let formData = new FormData();
        formData.append('document', file);

        let body = await fetch(`${baseUrl}/api/deals/${dealId}/documents`, {
            method: 'post',
            mode: 'no-cors',
            body: formData
        });

        return true;
    }

    static async signDocument(dealId, docId)
    {
        let body = await fetch(`${baseUrl}/api/deals/${dealId}/documents/${docId}/sign`, { method: 'post' });
        return body.json();
    }


    static async addDeal(title, desc) {
        let body = await fetch(`${baseUrl}/api/deals`, {
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify( { Title: title, Description: desc } ),
            method: 'post' });
        return body.json();
    }
}

export default API;
