function myOnLoad() {
	// load drop downs
	showCompanyOptions();
}

function search() {
	var now = new Date();
	$("startSearchTime").value = now.getTime();
	document.clientCabinetLabelForm.target='resultFrame';
	$("uAction").value = 'search';
	showPleaseWait();
	document.clientCabinetLabelForm.submit();
}

function generateExcel() {
    $("uAction").value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_clientCabinetLabel','650','600','yes');
    document.clientCabinetLabelForm.target='_clientCabinetLabel';
    window.setTimeout("document.clientCabinetLabelForm.submit();",500);
}

