function hubChanged() {

  var hubO = document.getElementById("preferredWarehouse");
  var facilityO = document.getElementById("facilityId");
  var applicationO = document.getElementById("application");
  var selectedHub = hubO.value;

  for (var i = facilityO.length; i > 0; i--) {
    facilityO[i] = null;
  }
  showFacilityOptions(selectedHub);
  facilityO.selectedIndex = 0;
  facilityChanged();
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

  var facilityO = document.getElementById("facilityId");
  var applicationO = document.getElementById("application");
  var selectedFacility = facilityO.value;

  for (var i = applicationO.length; i > 0; i--) {
    applicationO[i] = null;
  }
  showApplicationOptions(selectedFacility);
  applicationO.selectedIndex = 0;
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


function showBinDetails(cabinetId, hub) {
    var binDetails = "/tcmIS/hub/cabinetbinlabel.do?&cabinetId=" + cabinetId + "&hub=" + hub;
    openWinGeneric(binDetails,"bin_details","700","400","yes")
}

function generate(action) {
    var url = "/tcmIS/hub/label.do?&" + action + "=" + action;
    windowprops = "toolbar=no,location=no,directories=no,menubar=yes,scrollbars=yes,status=no,top=200,left=200,width=700,height=400,resizable=true;"
    window.open(url, "cabinetbinwindow",windowprops);
    var a = window.setTimeout("document.forms[1].submit();",500);
}

function checkAll(rowCount) {
  var allCheck = document.getElementById("allCheck");
  var check;
  var value;
  if(!allCheck.checked) {
    check = false;
  }
  else {
    check = true;
  }
  for(var i=0; i<rowCount; i++) {
    var checkbox = document.getElementById("checkbox" + i);
    checkbox.checked = check;
    checkbox.value = value;
  }
}
