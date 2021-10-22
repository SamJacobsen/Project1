var user;

async function login() {
    const uName = document.querySelector(".uname").value;
    if(isEmpty(uName) || isBlank(uName)) {
        document.querySelector(".uname").classList.add("error");
        document.getElementById("login_status").innerHTML = "No username";
        return;
    }

    const psw = document.querySelector(".psw").value;
    if(isEmpty(psw)) {
        document.querySelector(".psw").classList.add("error");
        document.getElementById("login_status").innerHTML = "No password";
        return;
    }

    const response = await fetch('/api/login', {method: 'post', body: JSON.stringify({uName, psw})});

   if(response.status == 401) {
       console.dir("status code 401");
       document.querySelector(".uname").classList.add("error");
       document.querySelector(".psw").classList.add("error");
       document.getElementById("login_status").innerHTML = "Login attempt failed, username or password is incorrect";
   } else if(response.status == 200) {
        let res = response;
        if(res.redirected == true) {
            window.location.href = res.url;
        }
        //console.dir(res);
        //userType = await response.json();
   }
}

function isEmpty(str) {
    return (!str || str.length === 0);
}

function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}

// document.getElementById("b").addEventListener("click", async function() {
//     const response = await fetch('http://localhost:7000/api/reimbursement/request');
//     const reimbursement = await response.json();
//     reimbursement.postDate = new Date(reimbursement.postDate);
// });