function submitSearchForm() {
	/*
	 * Make sure to not set the target of the form to anything other than
	 * resultFrame
	 */
	var now = new Date();
	document.getElementById("startSearchTime").value = now.getTime();

	$('uAction').value = 'search';
	document.genericForm.target = 'resultFrame';
	showPleaseWait();
	return true;
}

function facilityChanged() {
	var workAreaGroupO = document.getElementById("application");
	var workAreaO = document.getElementById("cabinetIdArray");
	var selectedFacility = $v("facilityId");

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
		var i = 0;
		if (workAreaGroupArray.length == 0 || workAreaGroupArray.length > 1) {
			setOption(i++, messagesData.all, "", "application");
		}
		if (workAreaGroupArray.length == 1) {
			setOption(0, workAreaGroupDescArray[0], workAreaGroupArray[0], "application")
		}
		else {
			for (; i <= workAreaGroupArray.length; i++) {
				setOption(i, workAreaGroupDescArray[i - 1], workAreaGroupArray[i - 1], "application");
			}
		}
	}
	else {
		setOption(0, messagesData.all, "", "application");
	}
}

function workAreaGroupChanged() {
	var workAreaGroupO = document.getElementById("application");
	var workAreaO = document.getElementById("cabinetIdArray");

	var selectedFacility = $v("facilityId");
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
	var currApp = document.getElementById("application");

	if (currApp.value == '') {
		selectedWorkAreaGroup = $v("facilityId"); + "-All";
	}

	var workAreaArray = altWorkAreaArray[selectedWorkAreaGroup];
	var workAreaDescArray = altWorkAreaDescArray[selectedWorkAreaGroup];

	if (workAreaArray != null) {
		var i = 0;
		if (workAreaArray.length == 0 || workAreaArray.length > 1) {
			setOption(i++, messagesData.all, "", "cabinetIdArray");
		}

		if (workAreaArray.length == 1) {
			setOption(0, workAreaDescArray[0], workAreaArray[0], "cabinetIdArray");
		}
		else {
			for (; i <= workAreaArray.length; i++) {
				setOption(i, workAreaDescArray[i - 1], workAreaArray[i - 1], "cabinetIdArray");
			}
		}
	}
	else {
		setOption(0, messagesData.all, "", "cabinetIdArray");
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
	$("facilityId").value = altFacilityId[selectedFacIndex];
	facilityChanged();
	$("application").selectedIndex = selectedWagIndex;
	workAreaGroupChanged();

	submitSearchForm();
	document.genericForm.submit();
	//document.getElementById("facilityId").disabled = true;
}
