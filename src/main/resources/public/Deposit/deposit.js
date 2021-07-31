const btn = document.querySelector('button');

function onSubmitFrom(){
    let depositAmount = document.getElementById("deposit").value;
    let xhr = new XMLHttpRequest();
	
	//const url = "localhost:9000/planet
	let planet = {};
	let formData = new FormData();
	formData.append("name", document.getElementById("planet-name").value);
	
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
				console.log("HEllo!")
				console.log(document.getElementsByClassName("toBeDeleted"));
				resetTable();
				grabPlanets();
				console.log("FINISHED!!!!")
		}
	}
	console.log("Right about to send the xhr request now!")
	xhr.open("DELETE", url, true);
	xhr.send(formData);
}

btn.onclick = onSubmitFrom;