var resizeGridWithWindow = true;


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

	
	for ( var i = 0; i < facilityArray.length; i++) {
		setOption(i, facilityNameArray[i], facilityArray[i], "facilityId")
	}
}

function facilityChanged() {
	
	var facilityO = document.getElementById("facilityId");
	var applicationO = document.getElementById("applicationId");
	var catalogO = document.getElementById("catalogId");
	var selectedFacility = facilityO.value;

	for ( var i = catalogO.length; i > 0; i--) {
		catalogO[i] = null;
	}
	for ( var i = applicationO.length; i > 0; i--) {
		applicationO[i] = null;
	}
	
	showCatalogOptions(selectedFacility);
	catalogO.selectedIndex = 0;
	catalogChanged();
}

function showApplicationOptions(selectedFacility) {
	var applicationArray = altApplicationId[selectedFacility];
	var applicationDescArray = altApplicationDesc[selectedFacility];

	var i = 0;
	if (applicationArray.length == 0 || applicationArray.length > 1) {
		setOption(i++, messagesData.allworkareas, "", "applicationId");
	}

	if (applicationArray.length == 1) {
		setOption(0, applicationDescArray[0], applicationArray[0], "applicationId")
	}
	else {
		for (; i <= applicationArray.length; i++) {
			
			setOption(i, applicationDescArray[i - 1], applicationArray[i - 1], "applicationId");
		}
	}
}

function catalogChanged() {
	
	var facilityO = document.getElementById("facilityId");
	var applicationO = document.getElementById("applicationId");
	var catalogO = document.getElementById("catalogId");

	var selectedFacility = facilityO.value;
	var selectedApplication = applicationO.value;

	if (applicationO != null) {
		for ( var i = applicationO.length; i > 0; i--) {
			applicationO[i] = null;
		}
	}
	showApplicationOptions(selectedFacility);
	applicationO.selectedIndex = 0;
}



function showCatalogOptions(selectedApplication) {
	var fac = document.getElementById("facilityId");
		
	var catalogArray = altCatalogId[selectedApplication];
	var catalogDescArray = altCatalogDesc[selectedApplication];

	var i = 0;
	if (catalogArray.length == 0 || catalogArray.length > 1) {
		setOption(i++, messagesData.allCatalogs, "", "catalogId")
	}

	if (catalogArray.length == 1) {
		setOption(0, catalogDescArray[0], catalogArray[0], "catalogId")
	}
	else {
		for (; i <= catalogArray.length; i++) {
			setOption(i, catalogDescArray[i - 1], catalogArray[i - 1], "catalogId");
		}
	}
}

function applicationChanged() {
	
	checkFacilityApproved();
}

function checkFacilityApproved(){
	
	var selectedApplication = $("applicationId").value;
	
	if(selectedApplication != '')
	{
		document.getElementById("workAreaApprovedOnly").disabled="disabled";
		
		document.getElementById("workAreaApprovedOnly").checked=true;
		
		
	}
	else
	{
		document.getElementById("workAreaApprovedOnly").disabled="";
	}
}


function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  
  if(validateForm()) { 
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
	var auditResult = true;
		return auditResult;
}

function createXSL() {
	var fac = document.getElementById('facilityId');
	document.getElementById('facilityName').value = fac.options[fac.selectedIndex].text;
	var app = document.getElementById('applicationId');
	document.getElementById('applicationDesc').value = app.options[app.selectedIndex].text;    
    $('uAction').value = 'createXSL';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_excel_report_file','650','600','yes');
    document.genericForm.target='_excel_report_file';
	setTimeout("document.genericForm.submit()",300);
	
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


