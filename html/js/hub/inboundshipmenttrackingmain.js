// This works only for popup windows and IE. Close the window after clicking Esc key
var windowCloseOnEsc = true;

function submitSearchForm() {
	/*
	 * Make sure to not set the target of the form to anything other than
	 * resultFrame
	 */
	if (true)//validateSearchCriteriaInput()) 
	{
		$("uAction").value = 'search';
		document.genericForm.target = 'resultFrame';
		showPleaseWait();
		document.getElementById("startSearchTime").value = new Date().getTime();
		return true;
	}
	else {
		return false;
	}
}

function validateSearchCriteriaInput() {
	var errorMessage = messagesData.validvalues + "\n\n";
	var warningMessage = messagesData.alert + "\n\n";
	var errorCount = 0;
	var warnCount = 0;

	var searchField = $v("searchField");
	var searchArgument = $v("searchArgument");
	if (searchField == 'prNumber' && !isFloat(searchArgument, true)) {
		errorMessage = errorMessage + messagesData.mr;
		$("searchArgument").value = "";
		errorCount = 1;
	}

	if (errorCount > 0) {
		alert(errorMessage);
		return false;
	}

	return true;
}

function createXSL() {
	var flag = true;// validateSearchCriteriaInput();
	if (flag) {
		$('uAction').value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_InboundShipmentTrackingExcel', '650', '600', 'yes');
		document.genericForm.target = '_InboundShipmentTrackingExcel';
		var a = window.setTimeout("document.genericForm.submit();", 50);
	}
}
