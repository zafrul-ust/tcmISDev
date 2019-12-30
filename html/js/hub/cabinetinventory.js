function init() { /*This is to initialize the YUI*/
 this.cfg = new YAHOO.util.Config(this);
 if (this.isSecure)
 {
  this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 }

}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function myResultBodyOnload()
{
	//alert("executing myResultBodyOnload()...");
 	setResultFrameSize();
 // if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
 /*
 if (false) /*Dont show any update links if the user does not have permissions* /
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
 */
}

function submitSearchForm()
{
 	var isValid = validateSearchForm();
  	if(isValid) 
  	{
   		var action = document.getElementById("action");
   		action.value = "search";
   		document.getElementById("companyId").value = 
   			altCompanyId[document.getElementById("branchPlant").value][document.getElementById("facilityId").selectedIndex];
   		parent.showPleaseWait();
   		return true;
 	}
  	else
  	{
    	return false;
  	}
}

function validateSearchForm() 
{
  	document.cabinetInventoryForm.target='resultFrame';
  	var action = document.getElementById("action");
  	action.value = '';
	//var errorMessage = messagesData.validvalues +"\n\n";
	var errorMessage = "";
	
	var errorCount = 0;

	var selectedCabinets  =  document.getElementById("cabinetIdArray");
	
/*
	if (selectedCabinets[0].value == "" || selectedCabinets[0].value.length==0)
	{
		//alert("cant choose All cabinets");
  		errorMessage = errorMessage + messagesData.pleaseDoNotSelectAllCabinets + ".\n" ;
  		errorCount++;
	}
*/
	var isAnyCabinetSelected = false;
	for (var i = 0; i < selectedCabinets.options.length; i++ )
	{
		if (selectedCabinets[i].selected == true)
		{
			isAnyCabinetSelected = true;
		}
	}
	if ( isAnyCabinetSelected == false )
	{
  		errorMessage = errorMessage + messagesData.pleaseSelectACabinet + ".\n" ;
  		errorCount++;
	}
	
	var searchField  =  document.getElementById("searchField").value;	
  	if(searchField == 'ITEM_ID') 
  	{
  		var searchArgument  =  document.getElementById("searchArgument");
    	if(!isInteger(searchArgument.value.trim(), true)) 
    	{
    		errorMessage = errorMessage + messagesData.itemInteger + "\n" ;     		
      		errorCount++;
    	}
  	}
	
	var expireInFromField = document.getElementById("expireInFrom").value;
	if ( expireInFromField.length > 0)
	{
  		if(!isSignedInteger( expireInFromField.trim(), true) ) 
  		{
    		errorMessage = errorMessage + messagesData.expireInFromDaysInteger + ".\n" ;
    		errorCount++;
  		}
  	}
	var expireInToField = document.getElementById("expireInTo").value;
	if ( expireInToField.length > 0)
	{
  		if(!isSignedInteger( expireInToField.trim(), true) ) 
  		{
    		errorMessage = errorMessage + messagesData.expireInToDaysInteger + ".\n" ;
    		errorCount++;
  		}
  	}
	
	if (errorCount >0)
	{
  		alert(errorMessage);
  		return false;
	}
	else
	{
  		return true;
	}
}

function doShowInventoryDetail ( itemId, hub )
{
	//alert("selecting itemId: " + itemId + "  and hub: " + hub);
 	openWinGeneric("inventorydetailsmain.do?itemId=" + itemId + "&hub=" + hub,
 		"inventorydetailspage", "700", "400", "yes");	 		
}

function hubChanged() {
  var hubO = document.getElementById("branchPlant");
  var facilityO = document.getElementById("facilityId");
  var selectedHub = hubO.value;

  for (var i = facilityO.length; i > 0; i--) {
    facilityO[i] = null;
  }
  showFacilityOptions(selectedHub);
  facilityO.selectedIndex = 0;
  facilityChanged();
  //if user does not have permission to add bin hide the button
//  checkBinPermission(selectedHub);
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

  if(facilityArray.length == 0) {
    setOption(0, messagesData.all, "", "facilityId")
  }

  for (var i=0; i < facilityArray.length; i++) {
    setOption(i,facilityNameArray[i],facilityArray[i], "facilityId")
  }
}

function facilityChanged() {
  var hubO = document.getElementById("branchPlant");
  var facilityO = document.getElementById("facilityId");
  var applicationO = document.getElementById("application");
  var cabinetO = document.getElementById("cabinetIdArray");
  var selectedHub = hubO.value;
  var selectedFacility = facilityO.value;

  for (var i = applicationO.length; i > 0; i--) {
    applicationO[i] = null;
  }
  for (var i = cabinetO.length; i > 0; i--) {
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

  if(applicationArray.length == 0) {
    setOption(0, messagesData.all, "", "application")
  }

  for (var i=0; i < applicationArray.length; i++) {
    setOption(i,applicationDescArray[i],applicationArray[i], "application")
  }
  var selectedCabinets = document.getElementById("cabinetIdArray");
  selectedCabinets[0].selected = true;
}

function applicationChanged() {
  var hub0 = document.getElementById("branchPlant");
  var facilityO = document.getElementById("facilityId");
  var applicationO = document.getElementById("application");
  var cabinetO = document.getElementById("cabinetIdArray");

  var selectedHub = hub0.value;
  var selectedFacility = facilityO.value;
  var selectedApplication = applicationO.value;

  if(cabinetO != null) {
    for (var i = cabinetO.length; i > 0; i--) {
      cabinetO[i] = null;
    }
  }
  showCabinetOptions(selectedHub + "-" + selectedFacility + "-" + selectedApplication);
  cabinetO.selectedIndex = 0;
}

function showCabinetOptions(selectedApplication) {
  var cabinetArray = new Array();
  var hub = document.getElementById("branchPlant");
  var fac = document.getElementById("facilityId");

  var currApp = document.getElementById("application");
  if(currApp.value == '') {
    selectedApplication = hub.value + "-" + fac.value + "-All";
  }

  cabinetArray = altCabinetIdArray[selectedApplication];

  var cabinetNameArray = new Array();
  cabinetNameArray = altCabinetNameArray[selectedApplication];

  if(cabinetArray == null || cabinetArray.length == 0) {
    setOption(0, messagesData.all, "", "cabinetIdArray")
  }
  else {
    for (var i=0; i < cabinetArray.length; i++) {
      setOption(i,cabinetNameArray[i],cabinetArray[i], "cabinetIdArray")
    }
  }
}

function generateExcel() 
{
  	var isValidForm = validateSearchForm();
  	if ( isValidForm ) 
  	{
  		var action = document.getElementById("action");
    	action.value = 'createExcel';
    	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_newGenerateExcel','650','600','yes');
    	document.cabinetInventoryForm.target='_newGenerateExcel';
   		document.getElementById("companyId").value = 
   			altCompanyId[document.getElementById("branchPlant").value][document.getElementById("facilityId").selectedIndex];
    	var a = window.setTimeout("document.cabinetInventoryForm.submit();",500);
  	}
}

function getFacilityName(branchPlant, facilityId) {
  var facName = null;
  var facIdArray = new Array();
  var facNameArray = new Array();
  facIdArray = altFacilityId[branchPlant];
  facNameArray = altFacilityName[branchPlant];
  for (var i = 0; i < facIdArray.length; i++) {
    if(facIdArray[i] == facilityId) {
      facName = facNameArray[i];
    }
  }
  return facName;
}

function myOnLoad() {
  setSearchFrameSize();

  var hubO = document.getElementById("branchPlant");
  var selectedHub = hubO.value;
  //if user does not have permission to add bin hide the button
//  checkBinPermission(selectedHub);
  setHubName();
  var selectedCabinets = document.getElementById("cabinetIdArray");
  selectedCabinets[0].selected = true;
}

function setHubName() {
  var hubO = document.getElementById("branchPlant");
  var selectedHub = hubO.value;
  var hubName = document.getElementById("hubName");
  hubName.value = altHubName[selectedHub];
}
