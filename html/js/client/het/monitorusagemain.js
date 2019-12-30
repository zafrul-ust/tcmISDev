function submitSearchForm() {
	/*
	 * Make sure to not set the target of the form to anything other than
	 * resultFrame
	 */
	var now = new Date();
	document.getElementById("startSearchTime").value = now.getTime();

	if (validateSearchForm()) {
		$('uAction').value = 'search';
		document.genericForm.target = 'resultFrame';
		showPleaseWait();
		return true;
	}
	else {
		return false;
	}
}

function validateSearchForm() {
	// if(document.getElementById("reportingEntity").value == "")
	// {
	// alert(messagesData.pleaseselectwag);
	// return false;
	// }
	// else
	return true;
}

function createXSL() {

	if (validateSearchForm()) {
		$('uAction').value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_Cabinet_Turns_Excel', '650', '600', 'yes');
		document.genericForm.target = '_Cabinet_Turns_Excel';
		var a = window.setTimeout("document.genericForm.submit();", 50);
	}
}

function showFacilityOptions() {
	var facilityArray = altFacilityId;
	var facilityNameArray = altFacilityName;

	if (facilityArray.length == 0) {
		setOption(0, messagesData.all, "", "facilityId")
	}

	for ( var i = 0; i < facilityArray.length; i++) {
		setOption(i, facilityNameArray[i], facilityArray[i], "facilityId")
	}
}

function facilityChanged() {
	var facilityO = document.getElementById("facilityId");
	var workAreaGroupO = document.getElementById("reportingEntity");
	var workAreaO = document.getElementById("applicationId");
	var selectedFacility = facilityO.value;

	for ( var i = workAreaGroupO.length; i > 0; i--) {
		workAreaGroupO[i] = null;
	}
	for ( var i = workAreaO.length; i > 0; i--) {
		workAreaO[i] = null;
	}
	showWorkAreaGroupOptions(selectedFacility);
	workAreaGroupO.selectedIndex = 0;
	workAreaGroupChanged();
}

function showWorkAreaGroupOptions(selectedFacility) {
	var workAreaGroupArray = altWorkAreaGroupId[selectedFacility];
	var workAreaGroupDescArray = altWorkAreaGroupDesc[selectedFacility];

	if (workAreaGroupArray != null) {
		for ( var i = 0; i < workAreaGroupArray.length; i++) {
			setOption(i, workAreaGroupDescArray[i], workAreaGroupArray[i], "reportingEntity");
		}
	}
	else {
		setOption(0, messagesData.all, "", "reportingEntity");
	}
}

function workAreaGroupChanged() {
	var facilityO = document.getElementById("facilityId");
	var workAreaGroupO = document.getElementById("reportingEntity");
	var workAreaO = document.getElementById("applicationId");

	var selectedFacility = facilityO.value;
	var selectedWorkAreaGroup = workAreaGroupO.value;

	if (workAreaO != null) {
		for ( var i = workAreaO.length; i > 0; i--) {
			workAreaO[i] = null;
		}
	}
	showWorkAreaOptions(selectedFacility + "-" + selectedWorkAreaGroup);
	workAreaO.selectedIndex = 0;
}

function showWorkAreaOptions(selectedWorkAreaGroup) {
	var workAreaArray = altWorkAreaArray[selectedWorkAreaGroup];
	var workAreaDescArray = altWorkAreaDescArray[selectedWorkAreaGroup];

	if (workAreaArray != null) {

		for ( var i = 0; i < workAreaArray.length; i++) {
			setOption(i, workAreaDescArray[i], workAreaArray[i], "applicationId");
		}
	}
	else {
		setOption(0, "", "", "applicationId");
	}
}

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
	showFacilityOptions();
	// set default facilityId
	var selectedFacilityIndex = 0;
	for ( var i = 0; i < altFacilityId.length; i++) {
		if (altFacilityId[i] == $v("myDefaultFacilityId")) {
			selectedFacilityIndex = i;
			break;
		}
	}
	$("facilityId").selectedIndex = selectedFacilityIndex;
	facilityChanged();
}
