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
	// replace company and facility data with put away required facilities only
	altCompanyId = altCompanyId2;
	altCompanyName = altCompanyName2;
	altFacilityId = altFacilityId2;
	altFacilityName = altFacilityName2;
	
	// load drop downs
	showCompanyOptions();
}

function search() {
	showPleaseWait();

	document.clientCabinetPutAwayForm.target='resultFrame';
	$("uAction").value = 'search';
	$("startSearchTime").value = new Date().getTime();
	document.clientCabinetPutAwayForm.submit();
}

function generateExcel() {
	$("uAction").value = 'createExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_cabinetPutAway','650','600','yes');
	document.clientCabinetPutAwayForm.target='_cabinetPutAway';
	window.setTimeout("document.clientCabinetPutAwayForm.submit();",500);
}