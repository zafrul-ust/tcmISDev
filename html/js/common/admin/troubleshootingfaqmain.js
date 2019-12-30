/*function submitSearchForm() {
	//Make sure to not set the target of the form to anything other than resultFrame
	document.genericForm.target='resultFrame';
	document.getElementById("uAction").value = 'search';

	//set start search time
	var now = new Date();
	var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	var flag = validateForm();
	if(flag) {
		showPleaseWait();
		return true;
	}
	else {
		return false;
	}
}*/

function validateForm(){
	var errorMessage = "";
	var errorCount = 0;

	try {
		var searchInput = document.getElementById("searchArgument");
		var pageInput = document.getElementById("pageField");
		if((searchInput.value.trim().length == 0) && (pageInput.value.trim().length == 0)) {
			errorMessage +=  messagesData.searchInput + "\n";
			errorCount = 1;
			searchInput.value = "";
		} 
	}
	catch (ex) {
		errorCount++;
	}
	if (errorCount >0) {
		alert(errorMessage);
		return false;
	}
    return true;
}

/*function generateExcel(){
  	if(validateForm()) {
		var el = document.createElement("input");
		el.type = "hidden";
		el.name = "createExcel";
		el.value = "true";
		document.genericForm.appendChild(el);
  	
    		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_DatabaseObjectsExcel','650','600','yes');
    		document.genericForm.target='_DatabaseObjectsExcel';
    		window.setTimeout("document.genericForm.submit();",50);
	}
}*/
