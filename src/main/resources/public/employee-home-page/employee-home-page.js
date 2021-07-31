window.onload = function(){
	getAccounts();
}

function getAccounts(){
	let xhr = new XMLHttpRequest();
	const url = "/view-accounts";

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
				

				if(xhr.status == 200){
					let accounts = JSON.parse(xhr.responseText);
					let count = 0;
					accounts.forEach(
						account => {
							addRow(account);
						}
					)
				}
		}	
	}
	xhr.open("GET",url);
	xhr.send();
}

function addRow(account){
	let table = document.getElementById("account-table")
	
	let tableRow = document.createElement("tr");
	let idCol = document.createElement("td");
	let firstNameCol = document.createElement("td");
	let lastNameCol = document.createElement("td");
	let accountNumberCol = document.createElement("td");
	let accountNameCol = document.createElement("td");
	let accountTypeCol = document.createElement("td");
	let statusCol = document.createElement("td");
	let balanceCol = document.createElement("td");

	tableRow.appendChild(idCol);
	tableRow.appendChild(firstNameCol);
	tableRow.appendChild(lastNameCol);
	tableRow.appendChild(accountNumberCol);
	tableRow.appendChild(accountNameCol);
	tableRow.appendChild(accountTypeCol);
	tableRow.appendChild(statusCol);
	tableRow.appendChild(balanceCol);
;
	table.appendChild(tableRow);
	idCol.innerHTML = account.customerId;
	firstNameCol.innerHTML = account.firstName;
	lastNameCol.innerHTML = account.lastName;
	accountNumberCol.innerHTML = account.accountNumber;
	accountTypeCol.innerHTML = account.accountType;
	accountNameCol.innerHTML = account.accountName;
	statusCol.innerHTML = account.status;
	balanceCol.innerHTML = account.balance;
};
	