const requestTypeField = document.evaluate("//body/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
const requestAmountField = document.evaluate("//body/div[2]/div[2]/div[1]/div[3]/div[1]/div[1]/div[1]/input[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
const requestReceiptField = document.evaluate("//body/div[2]/div[2]/div[1]/div[5]/div[1]/label[1]/input[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
const requestDescriptionField = document.evaluate("//body/div[2]/div[2]/div[1]/div[7]/div[1]/div[1]/div[1]/textarea[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
const submitButton = document.evaluate("//button[contains(text(),'Submit')]", document, null, XPathResult.ANY_TYPE, null).iterateNext();

const requestTypeHelper = document.evaluate("//body/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/p[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
const requestAmountHelper = document.evaluate("//body/div[2]/div[2]/div[1]/div[3]/div[1]/div[1]/div[1]/p[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
const requestReceiptHelper = document.evaluate("//body/div[2]/div[2]/div[1]/div[5]/p[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
const requestDescriptionHelper = document.evaluate("//body/div[2]/div[2]/div[1]/div[7]/div[1]/div[1]/div[1]/p[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
const submitButtonHelper = document.evaluate("//body/div[2]/div[2]/div[1]/div[9]/p[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
const logoutButton = document.evaluate("//body/nav[1]/div[4]/div[2]/div[1]/a[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();

//const url = "ec2-18-117-174-173.us-east-2.compute.amazonaws.com";
const url = "localhost";
requestReceiptField.addEventListener('change', validateReceipt);
requestAmountField.addEventListener('change', validateAmount);
requestTypeField.addEventListener('change', validateType);
requestDescriptionField.addEventListener('change', validateDescription);
submitButton.addEventListener('click', submitRequest);
logoutButton.addEventListener('click', logoutUser);

window.addEventListener('load', async () => {

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

});

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

let receiptChecker = true; 
let amountChecker = true;
let typeChecker = true;
let descriptionChecker = true;

function validateReceipt() {

    let receiptFile = requestReceiptField.value;
    //todo I think. Might just have to regex the file. Honestly lets just get mvp rn 

}

function validateAmount() {

    let requestAmount = requestAmountField.value;

    if(requestAmount <= 0) {

        requestAmountHelper.textContent = "Request amount can't be 0 or less!";
        amountChecker = false;

    } else {

        requestAmountHelper.textContent = "";
        amountChecker = true;

    }

}

function validateType() {

    let requestType = requestTypeField.value;
    requestType = requestType.toUpperCase();

    if((requestType != "FOOD") && (requestType != "LODGING") && (requestType != "TRAVEL") && (requestType != "OTHER")) {

        requestTypeHelper.textContent = "Type is not valid. Please use 'food', 'lodging', 'travel' or 'other'";  //yea this is a placeholder. Gunna replace this text field with a multiselector later
        typeChecker = false; 

    } else {

        requestTypeHelper.textContent = "";
        typeChecker = true; 

    }

}

function validateDescription() {

    let requestDescription = requestDescriptionField.value;
    

    if(requestDescription.length > 255) {

        requestDescriptionHelper.textContent = "Description too long. Please input a description with 255 or less characters";
        descriptionChecker = false; 

    } else {

        requestDescriptionHelper.textContent = "";
        descriptionChecker = true; 

    }


}


async function submitRequest() {


    const file = requestReceiptField.files[0];

    let formData = new FormData();
    formData.append('reimb_receipt', file);
    formData.append('reimb_type', requestTypeField.value.toUpperCase());
    formData.append('reimb_amount', requestAmountField.value);
    formData.append('reimb_description', requestDescriptionField.value);

    try {

        let res = await fetch(`http://${url}:8081/ers_reimbursements`, { //Sending a post request with data of new user we want to create

        method: 'POST',
        body: formData,
        credentials: 'include'

     });

        if(res.status == 404) {     //I avoid using 404 in my implemented methods, so 404 means theres something wrong with the endpoint we're trying to access. 

            throw "Can't find endpoint";

        } else if(res.status == 400) {      //400 means we sent a bad request of some kind. Maybe wrong format for a username or something. No implemented yet serverside, but should give reason in log when done

            throw res.body;

        }

        //let data = await res.json();  
        console.log(res.status);
        submitButtonHelper.textContent = "Request successfully created";

        requestTypeField.value = "";
        requestAmountField.value = "";
        requestReceiptField.value = "";
        requestDescriptionField.value = "";

    }

    catch(err) {

        //let data = err.json();
        console.log(err);
        submitButtonHelper.textContent = err;

    }

}
