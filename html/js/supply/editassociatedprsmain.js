function submitSearchForm() {
	/*
	 * Make sure to not set the target of the form to anything other than
	 * resultFrame
	 */
	document.genericForm.target = 'resultFrame';
	var userAction = $v('userAction');
	if(userAction == 'addBuyOrdersToPO')		
		document.getElementById("uAction").value = 'addBuyOrdersToPO';
	else 
		document.getElementById("uAction").value = 'editassociatedPRs';
		
	// set start search time
	var now = new Date();
	var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	var flag = true;//validateForm();
	if (flag) {
		showPleaseWait();
		return true;
	}
	else {
		return false;
	}
}


function refreshParentGrid(isFullyDisassociated) {
	var userAction = $v('userAction');
	if(userAction == 'addBuyOrdersToPO')
		opener.submitSearchForm();	
	else
		opener.refereshEditAssociatedPrs(isFullyDisassociated);
	window.close();
}

function cancelAndReturn() {	
	window.close();
}