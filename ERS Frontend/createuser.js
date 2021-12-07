let submitButton = document.evaluate("//button[contains(text(),'Submit')]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
let usernameForm = document.evaluate("//body/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
let passwordForm = document.evaluate("//body/div[2]/div[2]/div[1]/div[3]/div[1]/div[1]/div[1]/input[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
let retypePasswordForm = document.evaluate("//body/div[2]/div[2]/div[1]/div[4]/div[1]/div[1]/div[1]/input[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
let firstnameForm = document.evaluate("//body/div[2]/div[2]/div[1]/div[5]/div[1]/div[1]/div[1]/input[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
let lastnameForm = document.evaluate("//body/div[2]/div[2]/div[1]/div[6]/div[1]/div[1]/div[1]/input[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
let emailForm =  document.evaluate("//body/div[2]/div[2]/div[1]/div[7]/div[1]/div[1]/div[1]/input[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
let usernameHelp = document.evaluate("//body/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/p[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
let passwordHelp = document.evaluate("//body/div[2]/div[2]/div[1]/div[3]/div[1]/div[1]/div[1]/p[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
let retypePasswordHelp = document.evaluate("//body/div[2]/div[2]/div[1]/div[4]/div[1]/div[1]/div[1]/p[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
let firstnameHelp = document.evaluate("//body/div[2]/div[2]/div[1]/div[5]/div[1]/div[1]/div[1]/p[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
let lastnameHelp = document.evaluate("//body/div[2]/div[2]/div[1]/div[6]/div[1]/div[1]/div[1]/p[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
let emailHelp = document.evaluate("//body/div[2]/div[2]/div[1]/div[7]/div[1]/div[1]/div[1]/p[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();
let submitHelper = document.evaluate("//body/div[2]/div[2]/div[1]/div[9]/p[1]", document, null, XPathResult.ANY_TYPE, null).iterateNext();

let url = 'localhost';
//let url = "ec2-18-117-174-173.us-east-2.compute.amazonaws.com";
usernameForm.addEventListener('input', verifyUsername);
retypePasswordForm.addEventListener('input', verifyRetypedPassword);
firstnameForm.addEventListener('input', verifyFirstname);
lastnameForm.addEventListener('input', verifyLastname);
emailForm.addEventListener('input', verifyEmail);

submitButton.addEventListener('click', async () => {    //Just grabbing all the data from the fields we made

    let userToAdd = {   //Fills out all the data for our ers user object. Defaults to role of employee on server, and postgres db will handle assinging it an ID. 

        'ers_username' : usernameForm.value.trim(),
        'ers_password' : passwordForm.value,
        'user_first_name' : firstnameForm.value,
        'user_last_name' : lastnameForm.value,
        'user_email' : emailForm.value

    }

    //Ok I need to check if all the input are valid before I send it off. How should I go about doing this? check object values? Form values? See if any help elements have text? 
    //First we'll check if any of the object's values are empty/null/undefined/whatever weird types javascript has(like for real, why is null and undefined a type?!?!)
    //Then we'll see if any help fields have any value. If they do, then we wont go forward anymore

    try{

        let goodToGo = true;        

        //Input validation *******************************************************************************************************

        if(userToAdd.ers_username == "" || userToAdd.ers_username === undefined) {

            usernameHelp.textContent = "Please enter a valid username";
            goodToGo = false; 

        }

        if(userToAdd.ers_password == "" || userToAdd.ers_password === undefined) {

            passwordHelp.textContent = "Please enter a valid password";
            retypePasswordHelp.textContent = "Please enter a valid password";
            goodToGo = false;

        }

        if(userToAdd.user_first_name == "" || userToAdd.user_first_name === undefined) {

            firstnameHelp.textContent = "Please enter a valid firstname";
            goodToGo = false; 

        }

        if(userToAdd.user_last_name == "" || userToAdd.user_last_name === undefined) {

            lastnameHelp.textContent = "Please enter a valid lastname";
            goodToGo = false; 

        }

        if(userToAdd.user_email == "" || userToAdd.user_email === undefined) {

            emailHelp.textContent = "Please enter a valid email";
            goodToGo = false; 
            

        }

        if((/\s/).test(userToAdd.ers_username)){    //tests if our username has any whitespace in it

            usernameHelp.textContent = "Username must be one word. No whitespace between characters!";
            goodToGo = false; 
    
        }

        if(passwordForm.value != retypePasswordForm.value) {

            retypePasswordHelp.textContent = "Passwords don't match!";
            goodToGo = false; 
    
        }

        if(!(/^\S+@\S+\.\S+$/).test(emailForm.value)) { 
            
            emailHelp.textContent="Please enter a valid email";
            goodToGo = false; 

        }

        if(goodToGo == false) {     //Yes I know, a wall of if statements. Probably a more elegant way to do this, but it works without being convoluted so hey. 

            throw "Unable to submit form. Missing fields. Please fill out fields";

        }








        //Begin sending http request ***************************************************************************










        let res = await fetch(`http://${url}:8081/ers_users`, { //Sending a post request with data of new user we want to create

            method: 'POST',
            body: JSON.stringify(userToAdd),
            crednetials: 'include'
    
        });

        if(res.status == 404) {     //I avoid using 404 in my implemented methods, so 404 means theres something wrong with the endpoint we're trying to access. 

            throw "Can't find endpoint";

        } else if(res.status == 400) {      //400 means we sent a bad request of some kind. Maybe wrong format for a username or something. No implemented yet serverside, but should give reason in log when done

            throw res.body;

        }

        let data = await res.json();    //This will just spit back out the user we just created. 
        console.log(data);
        submitHelper.textContent = "User successfully created";

    }
    catch(err) {

        console.log(err);
        submitHelper.textContent = err;

    }

});

//Going to add some event listeners to my input fields, in order to let the user know what would be a valid input or not. Maybe I should adjust the submit button listener
//In order to have some error handling too. 

async function verifyUsername() {   //username needs to be one word (no spaces between characters). Other than that really anything goes. Leading and trailing spcaes get trimmed, maybe let user know

    
    let trimmedUsername = usernameForm.value.trim();
    if(trimmedUsername != usernameForm.value) {

        usernameHelp.textContent = "Warning: leading and trailing whitespace will be trimmed";

    } else if((/\s/).test(trimmedUsername)){    //tests if our username has any whitespace in it

        usernameHelp.textContent = "Username must be one word. No whitespace between characters!";

    } else {

        usernameHelp.textContent = "";

    }

}

// async function verifyPassword() {    //Actually, I dont think i wanna do anything with this. Password really should be anything the user wants



// }

async function verifyRetypedPassword() {    //just verifying password and retyped password match 

    if(passwordForm.value != retypePasswordForm.value) {

        retypePasswordHelp.textContent = "Passwords don't match!";

    } else {

        retypePasswordHelp.textContent = "";

    }

}

async function verifyFirstname() {  //Just let them know whitespace will be trimmed

    let trimmedFirstname = firstnameForm.value.trim();
    if(trimmedFirstname != firstnameForm.value) {

        firstnameHelp.textContent = "Warning: leading and trailing whitespace will be trimmed";

    } else {

        firstnameHelp.textContent = "";

    }

}

async function verifyLastname() {   //same here

    let trimmedLastname = lastnameForm.value.trim();
    if(trimmedLastname != lastnameForm.value) {

        lastnameHelp.textContent = "Warning: leading and trailing whitespace will be trimmed";

    } else {

        lastnameHelp.textContent = "";

    }

}

async function verifyEmail() {      //This one might be a bit difficult. Really just verify that the email has a domain name

    if(!(/^\S+@\S+\.\S+$/).test(emailForm.value)) { //Yea I lowkey just ripped this regex statement off SO, so hopefully it kinda validated emails. Honestly the only way to really
                                                    //validate an email is to send an email to someone and make them click a link in it to finish creating their account. I'm not going 
                                                    //to do that for this project because thats just way too much for this and I have better things to focus on. 
        emailHelp.textContent="Please enter a valid email";


    }

    else{

        emailHelp.textContent="";

    }

}