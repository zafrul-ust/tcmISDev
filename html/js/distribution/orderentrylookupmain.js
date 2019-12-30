windowCloseOnEsc = true;

function submitSearchForm() {
	var isValidSearchForm = validateSearchCriteriaInput();
	var now = new Date();
	document.getElementById("startSearchTime").value = now.getTime();

	if(isValidSearchForm) {
		var userAction = document.getElementById("uAction");
		userAction.value = 'searchOrders';
		/*Make sure to not set the target of the form to anything other than resultFrame*/
		document.orderentitysearch.target='resultFrame';
		showPleaseWait();
		return true;
	} else {
		return false;
	}
}

function validateSearchCriteriaInput() {
	var errorMessage = messagesData.validvalues + "\n\n";
	var errorCount = 0;

	var searchField = $v("searchField");
	var searchArgument = $v("searchArgument");
	if (searchField == 'Order' && !isFloat(searchArgument,true)) {
		errorMessage = errorMessage +"\n"+ messagesData.searchargument;
		$("searchArgument").value = "";
		errorCount = 1;
	}

	if (errorCount >0) {
		alert(errorMessage);
		return false;
	}

	return true;
}

function createXSL() {
	var flag = validateSearchCriteriaInput();
	if(flag) {
		$('uAction').value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_OrderEntryExcel','650','600','yes');
		document.orderentitysearch.target='_OrderEntryExcel';
		var a = window.setTimeout("document.orderentitysearch.submit();",50);
	}
}

function lookupCustomer() {
	loc = "../distribution/customerlookupsearchmain.do?popup=Y&displayElementId=customerName&valueElementId=customerId";  
	openWinGeneric(loc,"customerlookup","800","500","yes","50","50","20","20","no"); 
}

function lookupPersonnel() {
	 loc = "/tcmIS/haas/searchpersonnelmain.do?displayArea=personnelName&valueElementId=personnelId";
	 openWinGeneric(loc,"PersonnelId","650","455","yes","50","50");
}

function buildSearchMode() {
	if($v("searchField") != 'Part Description')
	  setOption(0,messagesData.is,"is","searchMode");
	else
	  setOption(0,messagesData.contains,"contains","searchMode");
}

function invalidateCustomer() {
	var customerName  =  document.getElementById("customerName");
	var customerId  =  document.getElementById("customerId");
	if (customerName.value.length == 0) {
		customerName.className = "inputBox";
	}else {
		customerName.className = "inputBox red";
	}
	//set to empty
	customerId.value = "";
}

function invalidatePersonnel() {
	var personnelName  =  document.getElementById("personnelName");
	var personnelId  =  document.getElementById("personnelId");
	if (personnelName.value.length == 0) {
		personnelName.className = "inputBox";
	}else {
		personnelName.className = "inputBox red";
	}
	//set to empty
	personnelId.value = "";
}