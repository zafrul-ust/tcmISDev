
function myOnload()
{   
	//it's import to put this before setResultFrameSize(), otherwise it will not resize correctly
	displaySearchDuration();
	setResultFrameSize();
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
  checkBinPermission(selectedHub);
  setHubName();

 // var facilityName = getFacilityName(hubO.value, facilityO.value);
 // var facilityNameO = document.getElementById("facilityName");
 // facilityNameO.value = facilityName;
}

function showFacilityOptions(selectedHub) {
  var facilityArray = new Array();
  facilityArray = altFacilityId[selectedHub];

  var facilityNameArray = new Array();
  facilityNameArray = altFacilityName[selectedHub];

  if(facilityArray.length == 0) {
    setOption(0,"All","", "facilityId")
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

 // var facilityName = getFacilityName(hubO.value, facilityO.value);
 // var facilityNameO = document.getElementById("facilityName");
 // facilityNameO.value = facilityName;
}

function showApplicationOptions(selectedFacility) {
  var applicationArray = new Array();
  applicationArray = altApplicationId[selectedFacility];

  var applicationDescArray = new Array();
  applicationDescArray = altApplicationDesc[selectedFacility];

  if(applicationArray.length == 0) {
    setOption(0,"All","", "application")
  }

  for (var i=0; i < applicationArray.length; i++) {
    setOption(i,applicationDescArray[i],applicationArray[i], "application")
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
    setOption(0,"All","", "cabinetIdArray")
  }
  else {
    for (var i=0; i < cabinetArray.length; i++) {
      setOption(i,cabinetNameArray[i],cabinetArray[i], "cabinetIdArray")
    }
  }
}

function generateExcel() {

    var action = document.getElementById("userAction");
    action.value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_newGenerateExcel','650','600','yes');
    document.cabinetManagementForm.target='_newGenerateExcel';
    var a = window.setTimeout("document.cabinetManagementForm.submit();",500);
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

function checkBinPermission(selectedHub) {

  var hubName = altHubName[selectedHub]
  var found = false;

  for(var i=0; i < facilityPermissionArray.length; i++) {
    if(facilityPermissionArray[i] == hubName) {
      found = true;
    }
  }
/* disable add new bin for now...
  if(found) {
    //document.cabinetManagementForm.submitNewBin.disabled=false;
    document.cabinetManagementForm.submitNewBin.style.visibility = 'visible';
  }
  else {
    //document.cabinetManagementForm.submitNewBin.disabled=true;
    document.cabinetManagementForm.submitNewBin.style.visibility = 'hidden';
  }
*/
}

function addBin() {
  var bp = document.getElementById("branchPlant");
  var facId = document.getElementById("facilityId");
  var addBinUrl = "/tcmIS/hub/cabinetbin.do?action=view&branchPlant=" + bp.value + "&facilityId=" + facId.value;
  openWinGeneric(addBinUrl,"add_bin_screen","650","400","yes","50","50");
}

function cabdefOnLoad() {
  var hubO = document.getElementById("branchPlant");
  var selectedHub = hubO.value;
  //if user does not have permission to add bin hide the button
  checkBinPermission(selectedHub);
  setHubName();
}

function setHubName() {
  var hubO = document.getElementById("branchPlant");
  var selectedHub = hubO.value;
  var hubName = document.getElementById("hubName");
  hubName.value = altHubName[selectedHub];
}

function search() {
    var now = new Date();
    document.getElementById("startSearchTime").value = now.getTime();
    
    var action = document.getElementById("userAction");
    document.cabinetManagementForm.target='resultFrame';
    action.value = 'search';
   	parent.showPleaseWait();
	document.cabinetManagementForm.submit();
}


