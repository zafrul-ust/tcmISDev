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
  var cabinetO = document.getElementById("cabinetId");
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
}

function showApplicationOptions(selectedFacility) {
  var applicationArray = new Array();
  applicationArray = altApplicationId[selectedFacility];

  var applicationDescArray = new Array();
  applicationDescArray = altApplicationDesc[selectedFacility];

//  if(applicationArray.length == 0) {
//    setOption(0,"All","", "application")
//  }

  for (var i=0; i < applicationArray.length; i++) {
    setOption(i,applicationDescArray[i],applicationArray[i], "application")
  }
}

function applicationChanged() {
  var hub0 = document.getElementById("branchPlant");
  var facilityO = document.getElementById("facilityId");
  var applicationO = document.getElementById("application");
  var cabinetO = document.getElementById("cabinetId");
  var selectedApplication = applicationO.value;
  var selectedHub = hub0.value;
  var selectedFacility = facilityO.value;

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
  cabinetArray = altCabinetId[selectedApplication];

  var cabinetNameArray = new Array();
  cabinetNameArray = altCabinetName[selectedApplication];

  if(cabinetArray == null || cabinetArray.length == 0) {
//    setOption(0,"All","", "cabinetId")
  }
  else {
    for (var i=0; i < cabinetArray.length; i++) {
      setOption(i,cabinetNameArray[i],cabinetArray[i], "cabinetId")
    }
  }
}

function validateForm() {
	var catalogD = $("catalogId");
	$("companyId").value = altCompanyId[catalogD.selectedIndex];
	$("catalogCompanyId").value = altCompanyId[catalogD.selectedIndex];

  if(document.genericForm.binName == null || document.genericForm.binName.value.trim().length == 0) {
    alert(messagesData.binNameRequired);
    return false;
  }
  if(document.genericForm.facPartNo == null || document.genericForm.facPartNo.value.trim().length == 0) {
    alert(messagesData.facPartNoRequired);
    return false;
  }
  if(document.genericForm.stockingLevel == null || document.genericForm.stockingLevel.value.trim().length == 0) {
    alert(messagesData.stockingLevelRequired);
    return false;
  }
  if(document.genericForm.reorderPoint == null || document.genericForm.reorderPoint.value.trim().length == 0) {
    alert(messagesData.reorderPointRequired);
    return false;
  }
  if(document.genericForm.leadTimeDays == null || document.genericForm.leadTimeDays.value.trim().length == 0) {
    alert(messagesData.leadTimeDaysRequired);
    return false;
  }
  if(!isInteger(document.genericForm.reorderPoint.value.trim(), true)) {
    alert(messagesData.reorderPointInteger);
    return false;
  }
  if(!isInteger(document.genericForm.stockingLevel.value.trim(), true)) {
    alert(messagesData.stockingLevelInteger);
    return false;
  }

  if(!isInteger(document.genericForm.leadTimeDays.value.trim(), true)) {
    alert(messagesData.leadTimeDaysInteger);
    return false;
  }
  if(parseInt(document.genericForm.reorderPoint.value.trim()) > parseInt(document.genericForm.stockingLevel.value.trim())) {
    alert(messagesData.reorderPointLessThanStockingLevel);
    return false;
  }
  if (parseInt(document.genericForm.reorderPoint.value.trim()) == 0 && parseInt(document.genericForm.stockingLevel.value.trim()) != 0) {
		alert(messagesData.reorderPointGreaterThanZero);
		return false;
  }	
  return true;
}


function myOnLoad() {
//  facilityChanged();
}