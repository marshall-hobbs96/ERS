const url = "ec2-18-117-174-173.us-east-2.compute.amazonaws.com";

const tableBody = document.evaluate("//tbody", document, null, XPathResult.ANY_TYPE, null).iterateNext();


window.addEventListener('load', getAndPopulateRequests);

async function getAndPopulateRequests() {

    let res = await fetch(`http://${url}:8081/ers_reimbursement`, {

        method: 'get',
        credentials: 'include'

    });

    let requestsArray = await res.json();

    populateRequestsTable(requestsArray);


}

function populateRequestsTable(array) {

    for(let requestObject of array) {

        let tr = document.createElement('tr');

        let td = document.createElement('td');
        td.innerText = requestObject.reimb_id;
        tr.appendChild(td);

        td = document.createElement('td');
        td.innerText = requestObject.reimb_amount;
        tr.appendChild(td);

        td = document.createElement('td');
        td.innerText = requestObject.reimb_submitted;
        tr.appendChild(td);

        td = document.createElement('td');
        td.innerText = requestObject.reimb_resolved;
        tr.appendChild(td);

        td = document.createElement('td');
        td.innerText = requestObject.reimb_status;
        tr.appendChild(td);

        td = document.createElement('td');
        td.innerText = requestObject.reimb_type;
        tr.appendChild(td);

        td = document.createElement('td');
        td.innerText = requestObject.reimb_description;
        tr.appendChild(td);

        td = document.createElement('td');
        td.innerText = requestObject.reimb_receipt;
        tr.appendChild(td);

        td = document.createElement('td');
        td.innerText = requestObject.reimb_author;
        tr.appendChild(td);

        td = document.createElement('td');
        td.innerText = requestObject.reimb_resolver ;
        tr.appendChild(td);

        tableBody.appendChild(tr);

    }

}


