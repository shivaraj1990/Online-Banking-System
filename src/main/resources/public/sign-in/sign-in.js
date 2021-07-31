import { onSubmitFrom } from "../create-account/create-account";


const btn = document.querySelector('button');

function onSingInForm(){

    let xhr = new XMLHttpRequest();
	
	const url = "/sign-in";

    let data = {
        "userName": document.getElementById("userName").value,
        "password": document.getElementById("password").value
    };
	
	xhr.onreadystatechange = function(){
		switch(this.readyState){
			
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
				console.log("FINISHED!!!!");
				if(xhr.status == 200){
					let user = JSON.parse(xhr.responseText);
					onSubmitFrom(user);
				}

		}
	}
	console.log("Right about to send the xhr request now!")
	xhr.open("POST", url,true);
	xhr.setRequestHeader("Content-Type","application/json;charset=UTF-8");
	xhr.send(JSON.stringify(data));
}

btn.onclick = onSingInForm;
