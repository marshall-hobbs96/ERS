//const url = "ec2-18-117-174-173.us-east-2.compute.amazonaws.com";
const url = "localhost";

const tableBody = document.evaluate("//tbody", document, null, XPathResult.ANY_TYPE, null).iterateNext();
const logout = document.evaluate("//body/nav[1]/div[4]/div[3]/div[1]/a[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
const pendingFilter = document.evaluate("//a[contains(text(),'PENDING')]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
const approvedFilter = document.evaluate("//a[contains(text(),'APPROVED')]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
const deniedFilter = document.evaluate("//a[contains(text(),'DENIED')]", document, null, XPathResult.ANY_TYPE, null).iterateNext();


window.addEventListener('load', getAndPopulateRequests);
logout.addEventListener('click', logoutUser);
pendingFilter.addEventListener('click', getAndPopulateRequstsPending);
approvedFilter.addEventListener('click', getAndPopulateRequestsApproved);
deniedFilter.addEventListener('click', getAndPopulateRequestsDenied);


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

        let viewImageButton = document.createElement('button');
        viewImageButton.innerText = 'View Image';
        

        viewImageButton.addEventListener('click', () => {

            let assignmentImageModal = document.querySelector('#assignment-image-modal');

            let modalCloseElement = assignmentImageModal.querySelector('button');
            modalCloseElement.addEventListener('click', () => {

                assignmentImageModal.classList.remove('is-active');

            });

            let modalContentElement = assignmentImageModal.querySelector('.modal-content');
            modalContentElement.innerHTML = '';

            let imageElement = document.createElement('img');
            imageElement.setAttribute('src', `http://${url}:8081/ers_reimbursements/get_image/${requestObject.reimb_id}`);
            modalContentElement.appendChild(imageElement);

            assignmentImageModal.classList.add('is-active'); // add a class to the modal element to have it display

        });

        tr.appendChild(viewImageButton);

        td = document.createElement('td');
        td.innerText = requestObject.reimb_author;
        tr.appendChild(td);

        td = document.createElement('td');
        td.innerText = requestObject.reimb_resolver ;
        tr.appendChild(td);

        let approveButton = document.createElement('button');
        approveButton.className = "action-button";
        approveButton.innerText = "approve";

        //ok lets just....add the request ID to the button's dataset? Should be all I need to send a fetch request and modify the request's status

        approveButton.dataset.request_id = requestObject.reimb_id;
        approveButton.dataset.action = "APPROVED";
        tr.appendChild(approveButton);


        let actionButton = document.createElement('button');
        actionButton.className = "action-button";
        actionButton.innerText = "deny";
        actionButton.dataset.request_id = requestObject.reimb_id;
        actionButton.dataset.action = "DENIED";
        tr.appendChild(actionButton);

        tableBody.appendChild(tr);

    }

    tableBody.addEventListener('click', async event => {

        if(event.target.className == "action-button") {

            console.log(event.target.dataset.request_id);
            console.log(event.target.dataset.action);

            //ok now we need to send the fetch request from here, and then repopulate the table with our newly modified request.
            //maybe just reload the page after a successful fetch request? probably easier than clearing the table body and repopulating

            const formData = new FormData();

            formData.append('action', event.target.dataset.action);

            let res = await fetch(`http://${url}:8081/ers_reimbursements/${event.target.dataset.request_id}`, {

                method: 'POST',
                credentials: 'include',
                body: formData

            });

            if(res.status == 200) {

                window.location.reload();

            } else {

                console.log("error updating request");

            }

        }

    });



}

async function getAndPopulateRequstsPending() {


    let res = await fetch(`http://${url}:8081/ers_reimbursements/PENDING`, {

        method: 'GET',
        credentials: 'include'

    });

    if(res.staus = 200) {

        clearTableBody();

        let data = await res.json();
        console.log(data);
        //populateRequestsTable(data);

    }else{

        console.log("Error getting reimbursements by status");

    }

}

async function getAndPopulateRequestsApproved() {

    let res = await fetch(`http://${url}:8081/ers_reimbursements/APPROVED`, {

        method: 'GET',
        credentials: 'include'

    });

    if(res.staus = 200) {

        clearTableBody();

        let data = await res.json();
        populateRequestsTable(data);

    }else{

        console.log("Error getting reimbursements by status");

    }

}

async function getAndPopulateRequestsDenied() {

    let res = await fetch(`http://${url}:8081/ers_reimbursements/DENIED`, {

        method: 'GET',
        credentials: 'include'

    });

    if(res.staus = 200) {

        clearTableBody();

        let data = await res.json();
        populateRequestsTable(data);

    }else{

        console.log("Error getting reimbursements by status");

    }

}

function clearTableBody() {

    while(tableBody.firstChild) {

        tableBody.removeChild(tableBody.firstChild);

    }

}
