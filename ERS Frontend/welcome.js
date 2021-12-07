//const url = "ec2-18-117-174-173.us-east-2.compute.amazonaws.com";
const url = "localhost";

const tableBody = document.evaluate("//tbody", document, null, XPathResult.ANY_TYPE, null).iterateNext();
const logoutButton = document.evaluate("//body/nav[1]/div[4]/div[2]/div[1]/a[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();


window.addEventListener('load', getAndPopulateRequests);
logoutButton.addEventListener('click', logoutUser);


async function logoutUSer() {

    console.log("logout User");

    let res = await fetch(`http://${url}:8081/ers_users/logout`, {

        method: 'POST',
        credentials: 'include'

    });

    if(res.status == 200) {

        console.log("User successfully logged out");
        window.location.href = "index.html";

    } else {

        console.log("Issue logging out");

    }


}

async function getAndPopulateRequests() {

    console.log("checking logged in user");

    let res = await fetch(`http://${url}:8081/ers_users`, {

        method: 'GET',
        credentials: 'include'

    });

    if(res.status == 200) {

        let userObj = await res.json();

         if (userObj.userRole == "manager") {

            window.location.href = "welcomemanager.html";

        }

    } else {

        window.location.href = "index.html";

    }

    console.log("getAndPopulateRequests");

    res = await fetch(`http://${url}:8081/ers_reimbursements`, {

        method: 'GET',
        credentials: 'include'

    });

    let requestsArray = await res.json();

    populateRequestsTable(requestsArray);


}

function populateRequestsTable(array) {

    console.log("populateRequestsTable");

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


