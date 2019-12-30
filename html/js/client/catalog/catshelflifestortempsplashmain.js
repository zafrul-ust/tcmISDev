var resizeGridWithWindow = true;

var workAreaSearchType = "Work Area Approved";
var allCatalogSearchType = "All Catalogs";
var fullFacilitySearchType = "Full Facility Catalogs";

var allFacilitiesId = "My Facilities";
var allWorkAreasId = "My Work Areas";

function loadFacility() {
	showFacilityOptions();
	// set default facilityId
	var facilityArray = altFacilityId;
	var selectedFacilityIndex = 0;
	for ( var i = 0; i < facilityArray.length; i++) {
		
		if (altFacilityId[i] == $v("myDefaultFacilityId")) {
			selectedFacilityIndex = i;
			break;
		}
	}
	$("facilityId").selectedIndex = selectedFacilityIndex;
	facilityChanged();
		 
}

function showFacilityOptions() {
	var facilityArray = altFacilityId;
	var facilityNameArray = altFacilityName;

	if(facilityArray != null && facilityArray.length > 1) {
		setOption(0,messagesData.allFacilities, allFacilitiesId, "facilityId");
	
		for ( var i = 1; i <= facilityArray.length; i++) {
			setOption(i, facilityNameArray[i-1], facilityArray[i-1], "facilityId");
		}
	}
	else
		setOption(0, facilityNameArray[0], facilityArray[0], "facilityId");
}

function facilityChanged() {
	
	var facilityO = document.getElementById("facilityId");
	var selectedFacility = facilityO.value;
	var applicationO = document.getElementById("applicationId");

	for ( var i = applicationO.length; i > 0; i--) {
		applicationO[i] = null;
	}	

	showApplicationOptions(selectedFacility);
}

function showApplicationOptions(selectedFacility) {
	var applicationArray = altApplicationId[selectedFacility];
	var applicationDescArray = altApplicationDesc[selectedFacility];
	
	if(selectedFacility == allFacilitiesId)
		setOption(0, messagesData.allWorkAreas, allWorkAreasId, "applicationId"); 
	else if (applicationArray.length == 1) {
		setOption(0, applicationDescArray[0], applicationArray[0], "applicationId");
	}
	else {
		setOption(0, messagesData.allWorkAreas, allWorkAreasId, "applicationId"); 
		
		for (i=1; i <= applicationArray.length; i++) {			
			setOption(i, applicationDescArray[i - 1], applicationArray[i - 1], "applicationId");
		}
	}
}


function submitSearchForm()
{
	/*Make sure to not set the target of the form to anything other than resultFrame*/
	var now = new Date();
	document.getElementById("startSearchTime").value = now.getTime();

	if(validateForm()) { 
		/* Set the search mode based on all facilities / all work areas in one facility / specific work area */
		var selectedFacility = $v("facilityId");
		var selectedWorkArea = $v("applicationId");

		if(selectedFacility == allFacilitiesId){
			document.getElementById("facilityOrAllCatalog").value = allCatalogSearchType;
			document.getElementById("facilityOrAllShelflife").value = true;			
		}
		else if(selectedWorkArea == allWorkAreasId){
			document.getElementById("facilityOrAllCatalog").value = fullFacilitySearchType;
			document.getElementById("facilityOrAllShelflife").value = true;		
		}
		else{
			document.getElementById("facilityOrAllCatalog").value = workAreaSearchType;
			document.getElementById("facilityOrAllShelflife").value = false;		
		}
	  
		$('uAction').value = 'search';
		document.genericForm.target='resultFrame';
   
		showPleaseWait();
		return true;   
	}
	else
	{
		return false;
	}
}

function validateForm() {
			
		if($v('applicationId')=='')
		{
			alert(messagesData.pleaseselectaworkarea);
			return false;
		}
		return true;
}

function createXSL() {
	if(validateForm()) { 
		var fac = document.getElementById('facilityId');
		document.getElementById('facilityName').value = fac.options[fac.selectedIndex].text;
		var app = document.getElementById('applicationId');
		document.getElementById('applicationDesc').value = app.options[app.selectedIndex].text;    
		$('uAction').value = 'createXSL';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_excel_report_file','650','600','yes');
		document.genericForm.target='_excel_report_file';
		setTimeout("document.genericForm.submit()",300);
	}
	else
	{
		return false;
	}	
}

var dhxFreezeWins = null;
function initializeDhxWins() {
	if (dhxFreezeWins == null) {
		dhxFreezeWins = new dhtmlXWindows();
		dhxFreezeWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showTransitWin(messageTest)
{
	var waitingMsg = messagesData.waitingforinputfrom+"...";
	$("transitLabel").innerHTML = waitingMsg.replace(/[{]0[}]/g,messageTest);

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.innerHTML = document.getElementById("transitDailogWinBody").innerHTML;
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxFreezeWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxFreezeWins.createWindow("transitDailogWin",0,0, 400, 200);
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
		transitWin.setModal(true);  // freeze the window here
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


