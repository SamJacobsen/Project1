var activeUser;

async function getSessionUser() {
    const response = await fetch('/api/SessionUser');

    if(response.status == 200) {
        activeUser = await response.json();
        displayUser(activeUser);
    } else if(response.status == 401) {
        window.location.href = "../index.html";
    }
}

async function userInfo() {
    let userInfoHtml = await fetch('userInfo.html');
    userInfoHtml = await userInfoHtml.text();
  	
  	document.querySelector("#content").innerHTML = userInfoHtml;
    
      document.querySelector("#firstname").innerHTML = "FirstName: " + activeUser.firstName;
      document.querySelector("#lastname").innerHTML = "LastName: " + activeUser.lastName;
      document.querySelector("#username").innerHTML = "UserName: " + activeUser.userName;
}

async function request() {
    let requestFormHtml = await fetch('ReimbursementRequest.html');
    requestFormHtml = await requestFormHtml.text();

    document.querySelector("#content").innerHTML = requestFormHtml;
}

async function postReimbursement() {
    document.querySelectorAll(".request-box .error").forEach((e) => {
        e.classList.remove("error");
    })

    const amountElement = document.querySelector(".request-box .amount");
    let amount = amountElement.value;
    const dateElement = document.querySelector(".request-box .date");
    let transactionDate = dateElement.value;
    const purposeElement = document.querySelector(".request-box .purpose");
    let purpose = purposeElement.value;

    const statusElement = document.getElementById("request_status");
    statusElement.classList.add("statusError");

    if(isBlank(amount) || isEmpty(amount) || amount <= 0) {
        amountElement.classList.add("error");
        statusElement.innerHTML = "Invalid amount";
        return;
    }
    amountElement.classList.remove("error");

    if(isBlank(transactionDate) || isEmpty(transactionDate)) {
        dateElement.classList.add("error");
        statusElement.innerHTML = "Invalid date";
        return;
    }
    dateElement.classList.remove("error");
    
    if(isBlank(purpose) || isEmpty(purpose)) {
        purposeElement.classList.add("error");
        statusElement.innerHTML = "Invalid purpose";
        return;
    }
    purposeElement.classList.remove("error");
    
    const response = await fetch('/api/employee/submitReimbursement', {method: 'post', body: JSON.stringify({amount, transactionDate, purpose})});

    if(response.status == 200) {
        statusElement.classList.remove("statusError");
        statusElement.innerHTML = "Successfully created new reimbursement request";
    } else if(response.status == 400) {
        statusElement.innerHTML = "Server could not interpret input, try reformatting your input";
    } else if(response.status == 500) {
        statusElement.innerHTML = "Server error, please try again later";
    }
}

async function logout() {
    const response = await fetch('/api/logout', {method: 'post'});

    console.dir('is logout request');
    let res = response;
    console.dir(res);
    if(res.redirected == true) {
        console.dir('is redirect')
        window.location.href = res.url;
    }
}

async function viewReimbursements() {
    let requestFormHtml = await fetch('ReimbursementView.html');
    requestFormHtml = await requestFormHtml.text();

    document.querySelector("#content").innerHTML = requestFormHtml;
}

async function queryreimbursements() {
    const dropDown = document.getElementById("reimbursements");

    let selected = dropDown.value;

    const response = await fetch('/api/employee/queryReimbursement', {method: 'post', body: JSON.stringify(selected)});
    let reimbursements = await response.json();

    let output = document.querySelector(".output");
    output.innerHTML = "";

    if(response.status == 200) {
        if(reimbursements.length > 0) {
            reimbursements.forEach(r => {
                output.innerHTML += `<h3>Reimbursement</h3>
        <div>Purpose: ${r.purpose}</div><br>
        <div>Amount: ${r.amount}</div><br>
        <div>Date of Transaction: ${r.transactionDate}</div><br>
        <div>Date of Request: ${r.requestDate}</div><br>
        <div>Status: ${r.status}</div><br>`;
            });
        } else {
            output.innerHTML = "<div>No reimbursements</div>";
        }
    } 
}

function displayUser(activeUser) {
    //document.querySelector("#user").innerHTML = "Welcome back " + activeUser.userName;
    console.dir(activeUser);
}

function isEmpty(str) {
    return (!str || str.length === 0);
}

function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}