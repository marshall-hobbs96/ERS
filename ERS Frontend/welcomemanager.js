//const url = "ec2-18-117-174-173.us-east-2.compute.amazonaws.com";
const url = "localhost";

const tableBody = document.evaluate("//tbody", document, null, XPathResult.ANY_TYPE, null).iterateNext();
const logout = document.evaluate("//body/nav[1]/div[4]/div[2]/div[1]/a[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();


window.addEventListener('load', getAndPopulateRequests);
logout.addEventListener('click', logoutUser);


async function logoutUser() {

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

        if(userObj.userRole == "employee") {

            window.location.href = "welcome.html";

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
        td.className = "request_id";
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

        let approveButton = document.createElement('button');
        approveButton.innerText = "approve";
        tr.appendChild(approveButton);


        let actionButton = document.createElement('button');
        actionButton.innerText = "deny";
        tr.appendChild(actionButton);

        tableBody.appendChild(tr);

    }

}


