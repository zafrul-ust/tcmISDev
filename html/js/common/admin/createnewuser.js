windowCloseOnEsc = true;

function onMyLoad() {
	var tcmisError = document.getElementById("tcmisError");
	if (tcmisError.value == "initialLoad") {
		// do nothing
	} else {
		if (tcmisError.value.length > 0)
			alert(tcmisError.value);
		else
			returnToMain();
	}
}

function returnToMain() {
	window.opener.$("searchText").value = $v("newPersonnelId");
	window.opener.$("companyId").value = $v("companyId");
	opener.mySearchOnLoad();
	window.close();
}

function createNewUser() {	
	//check mandatory fields
	if (isEmpty($v("firstName")) || isEmpty($v("lastName")) || isEmpty($v("email"))) {
		alert(messagesData.missingData);
		return false;
	}
	$("action").value = "createNewUser";
	
	return true;
}

function isEmpty(value) {
	if (value == null || value.length < 1)
		return true;
	
	return false;
}