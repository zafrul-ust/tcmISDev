function submitSearchForm() {
	/*
	 * Make sure to not set the target of the form to anything other than
	 * resultFrame
	 */
	if (validateSearchCriteriaInput()) {
		$("action").value = 'search';
		document.genericForm.target = 'resultFrame';
		showPleaseWait();
		document.getElementById("startSearchTime").value = new Date().getTime();
		return true;
	}
	else {
		return false;
	}
}

function generateExcel() {
	if (validateSearchCriteriaInput()) {
		$('action').value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_GasOrderTracking', '650', '600', 'yes');
		document.genericForm.target = '_GasOrderTracking';
		var a = window.setTimeout("document.genericForm.submit();", 50);
	}
	else {
		return false;
	}
}

function validateSearchCriteriaInput()
{
	if($v('searchArgument') == '' && $v('dateFrom') == '' && $v('dateTo') == '')
	{
			alert(messagesData.validvalues);
			return false;			
	}
	else if(($v('dateFrom') != '' || $v('dateTo') != '') && $v('searchField') == '')
	{
		alert(messagesData.entersearch);
		return false;		
	}
		
	return true;	
}