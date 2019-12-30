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
	var facilityO = document.getElementById("facilityId");
	var applicationO = document.getElementById("application");
	var selectedFacility = facilityO.value;

	for ( var i = applicationO.length; i > 0; i--) {
		applicationO[i] = null;
	}
	showApplicationOptions(selectedFacility);
	applicationO.selectedIndex = 0;
}

function showApplicationOptions(selectedFacility) {
	var applicationArray = altApplicationId[selectedFacility];
	var applicationDescArray = altApplicationDesc[selectedFacility];

	var i = 0;
	if(applicationArray.length == 0 || applicationArray.length > 1) {
	 	setOption(i++,messagesData.all,"", "application");
	}

	if (applicationArray.length == 1) {
		setOption(0,applicationDescArray[0],applicationArray[0], "application")
	}else {
		for (; i <= applicationArray.length; i++) {
			setOption(i,applicationDescArray[i-1],applicationArray[i-1], "application");
		}
	}
}

function addNewCabinetToDropdown(cabinetId,cabinetName) {
	var facilityO = document.getElementById("facilityId");
	var applicationO = document.getElementById("application");
	//first add new cabinets to array
	var cabinetArray = altCabinetIdArray[facilityO.value+"-"+applicationO.value];
	cabinetArray[cabinetArray.length] = cabinetId;
	var cabinetNameArray = altCabinetNameArray[facilityO.value+"-"+applicationO.value];
   cabinetNameArray[cabinetNameArray.length] = cabinetName;
	//new add data to dropdown
	var cabinet0 = document.getElementById("cabinetIdArray");
	setOption(cabinet0.length,cabinetName,cabinetId,"cabinetIdArray");
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
	for ( var i = 0; i < altFacilityId; i++) {
		if (altFacilityId[i] == $v("myDefaultFacilityId")) {
			selectedFacilityIndex = i;
			break;
		}
	}
	$("facilityId").selectedIndex = selectedFacilityIndex;
	facilityChanged();
}

function search() {
	var now = new Date();
	$("startSearchTime").value = now.getTime();
	var app = document.getElementById('application');
	$("lastSearchApplication").value = app.options[app.selectedIndex].text;
	document.clientCabinetSetupForm.target='resultFrame';
	$("uAction").value = 'search';
	showPleaseWait();
	document.clientCabinetSetupForm.submit();
}

function generateExcel() {
    $("uAction").value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_cabinetDefinition','650','600','yes');
    document.clientCabinetSetupForm.target='_cabinetDefinition';
    window.setTimeout("document.clientCabinetSetupForm.submit();",500);
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

function showTransitWin(message) {
	if (message != null && message.length > 0) {
		$("transitLabel").innerHTML = message;
	}else {
		$("transitLabel").innerHTML = messagesData.pleasewait;
	}

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

