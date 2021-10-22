async function getSessionUser() {
    const response = await fetch('/api/SessionUser');

    if(response.status == 200) {
        activeUser = await response.json();
    } else if(response.status == 401) {
        window.location.href = "../index.html";
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

async function loadManagePage() {
    const response = await fetch('/api/manager/pending', {method: 'get'});
    if(response.status == 200) {
        let requestFormHtml = await fetch('manageReimbursement.html');
        requestFormHtml = await requestFormHtml.text();
        document.querySelector("#content").innerHTML = requestFormHtml;

        let reimbursement = await response.json();
        manageReimbursement(reimbursement);
    } else if(response.status == 404) {
        document.querySelector("#content").innerHTML = "<div class=\"request-box\">No pending reimbursements</div>";
    }
    
}

function manageReimbursement(reimbursement) {
    document.querySelector(".userName").innerHTML = "UserName: " + reimbursement.userName;
    document.querySelector(".purpose").innerHTML = "Purpose: " + reimbursement.purpose;
    document.querySelector(".amount").innerHTML = "Amount: " + reimbursement.amount;
    document.querySelector(".transactionDate").innerHTML = "Transaction Date: " + reimbursement.transactionDate;
    document.querySelector(".requestDate").innerHTML = "Request Date: " + reimbursement.requestDate;

    document.querySelector(".approve").addEventListener("click", async function(){
        console.log("Update ticket status to approved" + reimbursement.id);
        reimbursement.status = "APPROVED";
        await fetch('/api/manager/updateReimbursement', {method: 'put', body: JSON.stringify(reimbursement)})
        loadManagePage()
    });

    document.querySelector(".deny").addEventListener("click", async function(){
        console.log("Update ticket status to denied" + reimbursement.id);
        reimbursement.status = "DENIED";
        await fetch('/api/manager/updateReimbursement', {method: 'put', body: JSON.stringify(reimbursement)})
        loadManagePage()
    });
}

async function viewReimbursements() {
    let requestFormHtml = await fetch('ReimbursementView.html');
    requestFormHtml = await requestFormHtml.text();

    document.querySelector("#content").innerHTML = requestFormHtml;
}

async function queryreimbursements() {
    const dropDown = document.getElementById("reimbursements");
    let selected = dropDown.value;

    const response = await fetch('/api/manager/queryReimbursement', {method: 'post', body: JSON.stringify(selected)});
    let reimbursements = await response.json();

    let output = document.querySelector(".output");
    output.innerHTML = "";

    if(response.status == 200) {
        if(reimbursements.length > 0) {
            reimbursements.forEach(r => {
                output.innerHTML += `<h3>Reimbursement</h3>
                <div>UserName: ${r.userName}</div><br>
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

async function viewEmployees() {
    const response = await fetch('/api/manager/viewEmployees', {method: 'get'});
    let employees = await response.json();

    let content = document.querySelector("#content");
    content.innerHTML = "<div class=\"request-box\"></div>";
    let output = document.querySelector(".request-box");

    if(response.status == 200) {
        if(employees.length > 0) {
            employees.forEach(e => {
                output.innerHTML += `<h3>Employee</h3>
                <div>First Name: ${e.firstName}</div><br>
                <div>Last Name: ${e.lastName}</div><br>
                <div>UserName: ${e.userName}</div><br>`;
            });
        } else {
            output.innerHTML = "<div style=\"text-align: center;\">No employees</div>";
        }
    }
}