const btn = document.querySelector('button');

let accountType;

function getAccountType(){
	accountType = document.getElementById("account").value == 1? "checking" : "saving";
}

function onSubmitFrom(user){
	console.log(user);
    let xhr = new XMLHttpRequest();
	
	const url = "/create-account";

    let data = {
        "accountType": accountType,
        "firstName": document.getElementById("firstName").value,
        "lastName" : document.getElementById("lastName").value,
        "phoneNumber": document.getElementById("phoneNumber").value,
        "email": document.getElementById("email").value,
        "accountName": document.getElementById("accountName").value,
        "balance": document.getElementById("balance").value
    };
	
	xhr.onreadystatechange = function(){
		switch(xhr.readyState){
			
			case 0:
				console.log("nothing, not initalized yet!");
				break;
			case 1: 
				console.log("connection established");
				break;
			case 2:
				console.log("request sent");
				break;
			case 3:
				console.log("awaiting request");
				break;
            case 4: 
				console.log("FINISHED!!!!")
		}
	}
	console.log("Right about to send the xhr request now!")
	xhr.open("POST", url);
	xhr.setRequestHeader("Content-Type","application/json;charset=UTF-8");
	xhr.send(JSON.stringify(data));
}

btn.onclick = onSubmitFrom;
