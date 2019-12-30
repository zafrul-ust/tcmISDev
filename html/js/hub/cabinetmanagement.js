var dhxWins = null;
var children = new Array();
var accountSysId = new Array();

function closeAllchildren()
{
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
//	if (document.getElementById("uAction").value != 'new') {
		try {
			for(var n=0;n<children.length;n++) {
				try {
				children[n].closeAllchildren();
				} catch(ex){}
			children[n].close();
			}
		} catch(ex){}
		children = new Array();
}

/* The reason for this is to show the error messages from the main page */
function showErrorMessages() {
	//parent.showErrorMessages();
}

function myOnload() {
	setResultFrameSize();
	if (!showUpdateLinks) /*
							 * Dont show any update links if the user does not
							 * have permissions
							 */
	{
		parent.document.getElementById("updateResultLink").style["display"] = "none";
	} else {
		parent.document.getElementById("updateResultLink").style["display"] = "";
	}
}

function submitSearchForm() {
	var flag = validateForm();
	if (flag) {
		parent.showPleaseWait();
		return true;
	} else {
		return false;
	}
}

function hubChanged() {
	var hubO = document.getElementById("branchPlant");
	var facilityO = document.getElementById("facilityId");
	var selectedHub = hubO.value;

	for ( var i = facilityO.length; i > 0; i--) {
		facilityO[i] = null;
	}
	showFacilityOptions(selectedHub);
	facilityO.selectedIndex = 0;
	facilityChanged();
	// if user does not have permission to add bin hide the button
	//checkBinPermission(selectedHub);
	setHubName();

	var facilityName = getFacilityName(hubO.value, facilityO.value);
	var facilityNameO = document.getElementById("facilityName");
	facilityNameO.value = facilityName;
}

function showFacilityOptions(selectedHub) {
	var facilityArray = new Array();
	facilityArray = altFacilityId[selectedHub];

	var facilityNameArray = new Array();
	facilityNameArray = altFacilityName[selectedHub];

	if (facilityArray.length == 0) {
		setOption(0, messagesData.all, "", "facilityId")
	}

	for ( var i = 0; i < facilityArray.length; i++) {
		setOption(i, facilityNameArray[i], facilityArray[i], "facilityId")
	}
}

function facilityChanged() {
	var hubO = document.getElementById("branchPlant");
	var facilityO = document.getElementById("facilityId");
	var applicationO = document.getElementById("application");
	var cabinetO = document.getElementById("cabinetIdArray");
	var selectedHub = hubO.value;
	var selectedFacility = facilityO.value;

	for ( var i = applicationO.length; i > 0; i--) {
		applicationO[i] = null;
	}
	for ( var i = cabinetO.length; i > 0; i--) {
		cabinetO[i] = null;
	}
	showApplicationOptions(selectedHub + "-" + selectedFacility);
	applicationO.selectedIndex = 0;
	applicationChanged();

	var facilityName = getFacilityName(hubO.value, facilityO.value);
	var facilityNameO = document.getElementById("facilityName");
	facilityNameO.value = facilityName;
}

function showApplicationOptions(selectedFacility) {
	var applicationArray = new Array();
	applicationArray = altApplicationId[selectedFacility];

	var applicationDescArray = new Array();
	applicationDescArray = altApplicationDesc[selectedFacility];

	if (applicationArray.length == 0) {
		setOption(0, messagesData.all, "", "useApplication")
	}

	for ( var i = 0; i < applicationArray.length; i++) {
		setOption(i, applicationDescArray[i], applicationArray[i],"useApplication")
	}
}

function applicationChanged() {
	var hub0 = document.getElementById("branchPlant");
	var facilityO = document.getElementById("facilityId");
	var applicationO = document.getElementById("application");
	var cabinetO = document.getElementById("cabinetIdArray");

	var selectedHub = hub0.value;
	var selectedFacility = facilityO.value;
	var selectedApplication = applicationO.value;

	if (cabinetO != null) {
		for ( var i = cabinetO.length; i > 0; i--) {
			cabinetO[i] = null;
		}
	}
	showCabinetOptions(selectedHub + "-" + selectedFacility + "-"
			+ selectedApplication);
	cabinetO.selectedIndex = 0;
}

function showCabinetOptions(selectedApplication) {
	var cabinetArray = new Array();
	var hub = document.getElementById("branchPlant");
	var fac = document.getElementById("facilityId");

	var currApp = document.getElementById("application");
	if (currApp.value == '') {
		selectedApplication = hub.value + "-" + fac.value + "-All";
	}

	cabinetArray = altCabinetIdArray[selectedApplication];

	var cabinetNameArray = new Array();
	cabinetNameArray = altCabinetNameArray[selectedApplication];

	if (cabinetArray == null || cabinetArray.length == 0) {
		setOption(0, messagesData.all, "", "cabinetIdArray")
	} else {
		for ( var i = 0; i < cabinetArray.length; i++) {
			setOption(i, cabinetNameArray[i], cabinetArray[i], "cabinetIdArray")
		}
	}
}

function submitUpdate() {
	var flag = validateForm();
	if (flag) {
		var action = document.getElementById("uAction");
		action.value = 'update';
		document.cabinetManagementForm.submit();
	}
}

function generateExcel() {
	var flag = validateForm();
	if (flag) {
		var action = document.getElementById("uAction");
		action.value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',
				'_newGenerateExcel', '650', '600', 'yes');
		document.cabinetManagementForm.target = '_newGenerateExcel';
		// document.genericForm.action='/tcmIS/hub/allocationanalysis.do';
		var a = window.setTimeout("document.cabinetManagementForm.submit();",
				500);
	}
}

function validateForm() {
	document.cabinetManagementForm.target = 'resultFrame';
	// validate that item id is numberic
	if (document.cabinetManagementForm.itemOrPart.value == 'item') {
		if (!isInteger(document.cabinetManagementForm.criteria.value.trim(),
				true)) {
			alert(messagesData.itemInteger);
			return false;
		}
	}
	return true;
}

function changeMinMaxCabinet(branchPlant, facilityId, itemId, cabinetId, binId,
		cabinetName, useApplication, partNumber, companyId, catalogId,
		partGroupNumber, status, catalogCompanyId) {
	// var facilityName = getFacilityName(branchPlant, facilityId);
	var facilityNameO = document.getElementById("facilityName");
	var facilityName = facilityNameO.value;
	var hubNameO = document.getElementById("hubName");
	var hubName = hubNameO.value;
	useApplication = useApplication.replace(/&/gi, "%26");
	useApplication = useApplication.replace(/#/gi, "%23");
	facilityId = facilityId.replace(/&/gi, "%26");
	facilityId = facilityId.replace(/#/gi, "%23");
	facilityName = facilityName.replace(/&/gi, "%26");
	facilityName = facilityName.replace(/#/gi, "%23");
	partNumber = partNumber.replace(/&/gi, "%26");
	partNumber = partNumber.replace(/#/gi, "%23");
	partNumber = partNumber.replace(/%/gi, "%25");
    partNumber = partNumber.replace(/\+/gi, "%2b");
    catalogCompanyId = catalogCompanyId.replace(/&/gi, "%26");
	catalogCompanyId = catalogCompanyId.replace(/#/gi, "%23");
	cabinetName = cabinetName.replace(/&/gi, "%26");
	cabinetName = cabinetName.replace(/#/gi, "%23");
	var levelChangeUrl = "/tcmIS/hub/cabinetlevel.do?action=viewMinMax&branchPlant="
			+ branchPlant
			+ "&hubName="
			+ hubName
			+ "&facilityId="
			+ facilityId
			+ "&facilityName="
			+ facilityName
			+ "&application="
			+ useApplication
			+ "&itemId="
			+ itemId
			+ "&cabinetName="
			+ cabinetName
			+ "&cabinetId="
			+ cabinetId
			+ "&catPartNo="
			+ partNumber
			+ "&companyId="
			+ companyId
			+ "&catalogId="
			+ catalogId
			+ "&partGroupNo="
			+ partGroupNumber
			+ "&status="
			+ status + "&catalogCompanyId=" + catalogCompanyId;
	openWinGeneric(levelChangeUrl, "getchang_elevelscreen", "650", "600",
			"yes", "50", "50");
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

function checkBinPermission(selectedHub) {
	var hubName = altHubName[selectedHub]
	var found = false;

	for ( var i = 0; i < facilityPermissionArray.length; i++) {
		if (facilityPermissionArray[i] == hubName) {
			found = true;
		}
	}
/*	if (found) {
		// document.cabinetManagementForm.submitNewBin.disabled=false;
		document.cabinetManagementForm.submitNewBin.style.visibility = 'visible';
	} else {
		// document.cabinetManagementForm.submitNewBin.disabled=true;
		document.cabinetManagementForm.submitNewBin.style.visibility = 'hidden';
	} */
}
/*
 * function addBin() { var bp = document.getElementById("branchPlant"); var
 * facId = document.getElementById("facilityId"); var myFac = facId.value; myFac =
 * myFac.replace(/&/g,"%26amp;"); var addBinUrl =
 * "/tcmIS/hub/cabinetbin.do?action=view&branchPlant=" + bp.value +
 * "&facilityId=" + myFac;
 * openWinGeneric(addBinUrl,"add_bin_screen","650","400","yes","50","50"); }
 */

function addBin() {
	var action = document.getElementById("uAction");
	action.value = 'addbin';
	openWinGeneric('/tcmIS/common/loadingpleasewait.jsp', '_newAddBin', '650',
			'600', 'yes');
	document.cabinetManagementForm.target = '_newAddBin';
	// document.genericForm.action='/tcmIS/hub/allocationanalysis.do';
	var a = window.setTimeout("document.cabinetManagementForm.submit();", 500);

}

function myOnLoad() {
//	setSearchFrameSize();

	var hubO = document.getElementById("branchPlant");
	var selectedHub = hubO.value;
	// if user does not have permission to add bin hide the button
	//checkBinPermission(selectedHub);
	setHubName();
}

function setHubName() {
	var hubO = document.getElementById("branchPlant");
	var selectedHub = hubO.value;
	var hubName = document.getElementById("hubName");
	hubName.value = altHubName[selectedHub];
}

function checkActiveStatus(rowNumber) {
	var checkbox = document.getElementById("cabinetManagementInputBean["
			+ rowNumber + "].status");
	var displayRow = document.getElementById("row" + rowNumber);

	if (checkbox.checked) {
		if ((rowNumber * 1) % 2 == 0) {
			displayRow.className = "";
		} else {
			displayRow.className = "alt";
		}
	} else {
		displayRow.className = "black";
	}
}

function setCountTypeValue(obj, rowNumber) {
	var countType = document.getElementById("cabinetManagementInputBean["
			+ rowNumber + "].countType");
	countType.value = obj.value;
	var displayRow = document.getElementById("row" + rowNumber);

	/*if ((rowNumber * 1) % 2 == 0) {
		displayRow.className = "";
	} else {
		displayRow.className = "alt";
	}*/
}

function search() {
	if (validateForm()) {
		var now = new Date();
		$("startSearchTime").value = now.getTime();
		document.cabinetManagementForm.target='resultFrame';
		$("uAction").value = 'search';
		showPleaseWait();
		document.cabinetManagementForm.submit();
	}
}

function generateExcel() {
	if (validateForm()) {
		$("uAction").value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_cabinetManagement','650','600','yes');
		document.cabinetManagementForm.target='_cabinetManagement';
		window.setTimeout("document.cabinetManagementForm.submit();",500);
	}
}


function addBin() {
	$("uAction").value = 'addBin';
	var size = $("cabinetIdArray").length;
	var numberSelected = 0;
	var tmpWorkAreaId = '';
	var tmpWorkAreaDesc = '';
	for(i=0;i<size;i++) {
		if( $("cabinetIdArray").options[i].selected ) {
			tmpWorkAreaId = $("cabinetIdArray").options[i].value;
			tmpWorkAreaDesc = $("cabinetIdArray").options[i].text;
			numberSelected++;
		}
	}
	if (numberSelected > 1 || tmpWorkAreaId.length == 0) {
		alert("Please select a Cabinet that you would like to create a new bin to.");
		return;
	}
	$("binId").value = '';
	$("applicationId").value = tmpWorkAreaId;
	$("cabinetId").value = tmpWorkAreaId;
	$("cabinetName").value = tmpWorkAreaDesc;
	showTransitWin();
	children[children.length] = openWinGeneric('/tcmIS/common/loadingpleasewait.jsp', '_newAddBin', '650','600', 'yes');
	document.cabinetManagementForm.target = '_newAddBin';
	window.setTimeout("document.cabinetManagementForm.submit();", 500);
}

//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
}

function showTransitWin()
{
	document.getElementById("transitLabel").innerHTML = messagesData.pleasewait;

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);
		dhxWins.window("transitDailogWin").show();
	}
}

function showLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows()
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 100);
  legendWin.setText(messagesData.showlegend);
  legendWin.clearIcon();  // hide icon
  legendWin.denyResize(); // deny resizing
  legendWin.denyPark();   // deny parking
  legendWin.attachObject("showLegendArea");
  legendWin.attachEvent("onClose", function(legendWin){legendWin.hide();});
  legendWin.center();
  }
  else
  {
    // just show
    dhxWins.window("legendwin").show();
  }
}


