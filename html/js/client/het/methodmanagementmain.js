var dhxFreezeWins = null;
var children = new Array();

function submitSearchForm() {
	/*
	 * Make sure to not set the target of the form to anything other than
	 * resultFrame
	 */
	var now = new Date();
	document.getElementById("startSearchTime").value = now.getTime();

	// if(validateSearchForm())
	{
		$('uAction').value = 'search';
		document.genericForm.target = 'resultFrame';
		showPleaseWait();
		return true;
	}
	/*
	 * else { return false; }
	 */
}

/*
 * function validateSearchForm() {
 * if(document.getElementById("workAreaGroup").value == "") {
 * alert(messagesData.pleaseselectwag); return false; } else return true; }
 */

function createXSL() {

	// if(validateSearchForm())
	{
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
	var facilitySelection = document.getElementById("facilityId");
	var areaSelection = document.getElementById("areaId");
	var selectedFacility = facilitySelection.value;

	for ( var i = areaSelection.length; i > 0; i--) {
		areaSelection[i] = null;
	}

	showAreaOptions(selectedFacility);
	areaSelection.selectedIndex = 0;
	areaChanged();
}

function areaChanged() {
	var areaSelection = document.getElementById("areaId");
	var buildingSelection = document.getElementById("buildingId");
	var selectedArea = areaSelection.value;

	for ( var i = buildingSelection.length; i > 0; i--) {
		buildingSelection[i] = null;
	}

	showBuildingOptions(selectedArea);
	buildingSelection.selectedIndex = 0;
	buildingChanged();
}


function buildingChanged() {
	var buildingSelection = document.getElementById("buildingId");
	var workareaSelection = document.getElementById("workArea");
	var selectedBuilding = buildingSelection.value;

	for ( var i = workareaSelection.length; i > 0; i--) {
		workareaSelection[i] = null;
	}

	showWorkAreaOptions(selectedBuilding);
	workareaSelection.selectedIndex = 0;
}

function showAreaOptions(selectedFacility) {
	var areaIdArray = altAreaId[selectedFacility];
	var areaDescArray = altAreaDesc[selectedFacility];

	if (areaIdArray != null) {
		var i = 0;
		if (areaIdArray.length == 0 || areaIdArray.length > 1) {
			setOption(i++, messagesData.all, "", "areaId");
		}
		if (areaIdArray.length == 1) {
			setOption(0, areaDescArray[0], areaIdArray[0], "areaId")
		}
		else {
			for (; i <= areaIdArray.length; i++) {
				setOption(i, areaDescArray[i - 1], areaIdArray[i - 1], "areaId");
			}
		}
	}
	else {
		setOption(0, messagesData.all, "", "areaId");
	}
}

function showBuildingOptions(selectedArea) {
	var buildingIdArray = altBuildingId[selectedArea];
	var buildingDescArray = altBuildingDesc[selectedArea];

	if (buildingIdArray != null) {
		var i = 0;
		if (buildingIdArray.length == 0 || buildingIdArray.length > 1) {
			setOption(i++, messagesData.all, "", "buildingId");
		}
		if (buildingIdArray.length == 1) {
			setOption(0, buildingDescArray[0], buildingIdArray[0], "buildingId")
		}
		else {
			for (; i <= buildingIdArray.length; i++) {
				setOption(i, buildingDescArray[i - 1], buildingIdArray[i - 1], "buildingId");
			}
		}
	}
	else {
		setOption(0, messagesData.all, "", "buildingId");
	}
}

function showWorkAreaOptions(selectedBuilding) {
	var workAreaArray = altWorkAreaId[selectedBuilding];
	var workareaDescArray = altWorkAreaDesc[selectedBuilding];

	if (workAreaArray != null) {
		var i = 0;
		if (workAreaArray.length == 0 || workAreaArray.length > 1) {
			setOption(i++, messagesData.all, "", "workArea");
		}
		if (workAreaArray.length == 1) {
			setOption(0, workareaDescArray[0], workAreaArray[0], "workArea")
		}
		else {
			for (; i <= workAreaArray.length; i++) {
				setOption(i, workareaDescArray[i - 1], workAreaArray[i - 1], "workArea");
			}
		}
	}
	else {
		setOption(0, messagesData.all, "", "workArea");
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

function initializeDhxWins() {
	if (dhxFreezeWins == null) {
		dhxFreezeWins = new dhtmlXWindows();
		dhxFreezeWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showTransitWin(messageType) {
	var waitingMsg = messagesData.waitingforinputfrom + "...";
	$("transitLabel").innerHTML = waitingMsg.replace(/[{]0[}]/g, messageType);

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.innerHTML = document.getElementById("transitDailogWinBody").innerHTML;
	transitDailogWin.style.display = "";

	initializeDhxWins();
	if (!dhxFreezeWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxFreezeWins.createWindow("transitDailogWin", 0, 0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon(); // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark(); // deny parking

		transitWin.attachObject("transitDailogWin");
		// transitWin.attachEvent("onClose",
		// function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem
		// to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}
	else {
		// just show
		transitWin.setModal(true);
		dhxFreezeWins.window("transitDailogWin").show();
	}
}

function closeTransitWin() {
	if (dhxFreezeWins != null) {
		if (dhxFreezeWins.window("transitDailogWin")) {
			dhxFreezeWins.window("transitDailogWin").setModal(false);
			dhxFreezeWins.window("transitDailogWin").hide();
		}
	}
}

function closeAllchildren() {
	// if (("document.getElementByIduAction").value != 'new') {
	try {
		for ( var n = 0; n < children.length; n++) {
			try {
				children[n].closeAllchildren();
			}
			catch (ex) {
			}
			children[n].close();
		}
	}
	catch (ex) {
	}
	children = new Array();
	// }
}