function getFacilityName(branchPlant, facilityId) {
	var facName = null;
	var facIdArray = new Array();
	var facNameArray = new Array();
	facIdArray = altFacilityId[branchPlant];
	facNameArray = altFacilityName[branchPlant];
	for ( var i = 0; i < facIdArray.length; i++) {
		if (facIdArray[i] == facilityId) {
			facName = facNameArray[i];
		}
	}
	return facName;
}

function myOnLoad() {
	// load drop downs
	showCompanyOptions();
}

function search() {
	if (validateForm()) {
		showPleaseWait();
	
		document.clientCabinetCountForm.target='resultFrame';
		$("uAction").value = 'search';
		$("startSearchTime").value = new Date().getTime();
		document.clientCabinetCountForm.submit();
	}
}

function generateExcel() {
    $("uAction").value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_workAreaAck','650','600','yes');
    document.clientCabinetCountForm.target='_workAreaAck';
    window.setTimeout("document.clientCabinetCountForm.submit();",500);
}

function validateForm() {
	// validate that item id is numberic
	if (document.clientCabinetCountForm.searchField.value == 'itemId') {
		if (!isInteger(document.clientCabinetCountForm.searchArgument.value.trim(),true)) {
			alert(messagesData.itemInteger);
			return false;
		}
	}
	return true;
}