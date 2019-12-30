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
	var facilityO = document.getElementById("facilityId");
	var workAreaGroupO = document.getElementById("useApplication");
	var workAreaO = document.getElementById("cabinetIdArray");
	var selectedFacility = facilityO.value;

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
			setOption(i++, messagesData.all, "", "useApplication");
		}

		if (workAreaGroupArray.length == 1) {
			setOption(0, workAreaGroupDescArray[0], workAreaGroupArray[0], "useApplication");
		}
		else {
			for (; i <= workAreaGroupArray.length; i++) {
				setOption(i, workAreaGroupDescArray[i - 1], workAreaGroupArray[i - 1], "useApplication");
			}
		}
	}else {
		setOption(0, messagesData.all, "", "useApplication");
	}
}

function workAreaGroupChanged() {
	var facilityO = document.getElementById("facilityId");
	var workAreaGroupO = document.getElementById("useApplication");
	var workAreaO = document.getElementById("cabinetIdArray");

	var selectedFacility = facilityO.value;
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
	var fac = document.getElementById("facilityId");
	var currApp = document.getElementById("useApplication");

	if (currApp.value == '') {
		selectedWorkAreaGroup = fac.value + "-All";
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
	}else {
		setOption(0, messagesData.all, "", "cabinetIdArray");	
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
  //load drop downs
  showFacilityOptions();
  //set default facilityId
  var selectedFacilityIndex = 0;
  for (var i = 0; i < altFacilityId.length; i++) {
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
    document.clientCabinetLabelForm.target='resultFrame';
    $("uAction").value = 'search';
    showPleaseWait();
	 document.clientCabinetLabelForm.submit();
}


function generateExcel() {
    $("uAction").value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_cabinetDetail','650','600','yes');
    document.clientCabinetLabelForm.target='_cabinetDetail';
    window.setTimeout("document.clientCabinetLabelForm.submit();",500);
}


