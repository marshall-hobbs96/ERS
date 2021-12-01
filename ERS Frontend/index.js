let loginButton = document.evaluate("//strong[contains(text(),'Log in')]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
let usernameField = document.evaluate("//body/nav[1]/div[4]/div[1]/div[1]/div[1]/div[1]/p[1]/input[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
let passwordField = document.evaluate("//body/nav[1]/div[4]/div[1]/div[1]/div[1]/div[2]/p[1]/input[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
let usernameHelp = document.evaluate("//body/nav[1]/div[4]/div[1]/div[1]/div[1]/div[1]/p[2]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
let passwordHelp = document.evaluate("//body/nav[1]/div[4]/div[1]/div[1]/div[1]/div[2]/p[2]", document, null, XPathResult.ANY_TYPE, null).iterateNext();

//let url = 'localhost';
let url = "ec2-18-117-174-173.us-east-2.compute.amazonaws.com";

loginButton.addEventListener('click', submitLogin);

async function submitLogin() {

    let userLoggingIn = {

        'ers_username' : usernameField.value,
        'ers_password' : passwordField.value

    }

    let goodToGo = true; 

    try{

        if(userLoggingIn.ers_username == "" || userLoggingIn.ers_username === null || userLoggingIn.ers_username === undefined) {

            usernameHelp.textContent = "Please enter a username";
            goodToGo = false;

        }

        if(userLoggingIn.ers_password == "" || userLoggingIn.ers_password === null || userLoggingIn.ers_password === undefined) {

            passwordHelp.textContent = "Please enter a password";
            goodToGo = false; 

        }

        if(goodToGo == false) {

            throw "Unable to login. One or both inputs missing";

        }

        let res = await fetch(`http://${url}:8081/ers_users/login`, {

            method: 'POST',
            body: JSON.stringify(userLoggingIn),
            credentials: 'include'

        });

        if(res.status == 404) {    //Something wrong with accessing the endpoint 

            throw "Can't find endpoint";

        } else if(res.status == 400) { //something wrong with info we sent

            throw res.body;

        }

        let data = await res.json();
        console.log(data);
        window.location.replace('welcome.html');
    }

    catch(err){

        console.log(err);

    }

}