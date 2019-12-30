function showFacilityOptions() {
	var facilityArray = altFacilityId;
	var facilityNameArray = altFacilityName;

	if (facilityArray.length == 0) {
		setOption(0, messagesData.all, "", "facilityId");
	}

	for ( var i = 0; i < facilityArray.length; i++) {
		setOption(i, facilityNameArray[i], facilityArray[i], "facilityId");
	}
}

function facilityChanged() {
	var facilityO = $("facilityId");
	var workAreaGroupO = $("workAreaGroup");
	var workAreaO = $("workArea");
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
		if (workAreaGroupArray.length == 1) {
			setOption(0, workAreaGroupDescArray[0], workAreaGroupArray[0], "workAreaGroup")
		}
		else {
			for (var i = 0; i < workAreaGroupArray.length; i++) {
				setOption(i, workAreaGroupDescArray[i], workAreaGroupArray[i], "workAreaGroup");
			}
		}
	}
}

function workAreaGroupChanged() {
	var facilityO = document.getElementById("facilityId");
	var workAreaGroupO = document.getElementById("workAreaGroup");
	var workAreaO = document.getElementById("workArea");

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
		if (workAreaArray.length == 1) {
			setOption(0, workAreaDescArray[0], workAreaArray[0], "workArea");
		}
		else {
			setOption(0, messagesData.all, "", "workArea");
			for (var i = 0; i < workAreaArray.length; i++) {
				setOption(i+1, workAreaDescArray[i], workAreaArray[i], "workArea");
			}
		}
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

function search() {
	showPleaseWait();
	document.genericForm.target='resultFrame';
	$("uAction").value = 'search';
	$("startSearchTime").value = new Date().getTime();
	document.genericForm.submit();
}

function generateExcel() {
    $("uAction").value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_UsageLogging','650','600','yes');
    document.genericForm.target='_UsageLogging';
    window.setTimeout("document.genericForm.submit();",500);
}

