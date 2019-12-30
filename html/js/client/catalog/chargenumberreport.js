
function showFacilityOptions() {
	var facilityArray = altFacilityId;
	var facilityNameArray = altFacilityName;

	var selectedFacilityIndex = 0;
	if(facilityArray != null) {
		var count = 0;
		for (var i=0; i < facilityArray.length; i++) {
			setOption(i+count,facilityNameArray[i],facilityArray[i], "facilityId");
			//selected facility
		}
	}

	$("facilityId").selectedIndex = selectedFacilityIndex;
	facilityChanged();
}
function facilityChanged() {

	var facilityO = document.getElementById("facilityId");
	var areaO = document.getElementById("areaId");
	var selectedFacility = facilityO.value;
	
	showWagOptions(selectedFacility);
	showDeptOptions(selectedFacility);
	showAcctSysOptions(selectedFacility);

	for (var i = areaO.length; i >= 0;i--) {
		areaO[i] = null;
	}

	showAreaOptions(selectedFacility);
}

function showAcctSysOptions(selectedFacility)
{
	var currAcctSys = acctSysColl[selectedFacility];
	var acctSysO = document.getElementById("accountSysId");
	for (var i = acctSysO.length; i >= 0;i--) {
		acctSysO[i] = null;
	}
	
	var selectedAcctSysIndex = 0;
	if(selectedFacility != 'All' && selectedFacility != '')
		for(var j = 0; j < currAcctSys.length;j++)
			setOption(j,currAcctSys[j].acctSysName, currAcctSys[j].acctSysId, "accountSysId");
	acctSysO.selectedIndex = selectedAcctSysIndex;
}

function showWagOptions(selectedFacility)
{
	var currWag = wagColl[selectedFacility];
	var wagO = document.getElementById("reportingEntityId");
	for (var i = wagO.length; i >= 0;i--) {
		wagO[i] = null;
	}
	
	var selectedWagIndex = 0;
	setOption(0,messagesData.all, "", "reportingEntityId");
	if(selectedFacility != 'All' && selectedFacility != '')
		for(var j = 0; j < currWag.length;j++)
			setOption(j+1,currWag[j].wagName, currWag[j].wagId, "reportingEntityId");

	wagO.selectedIndex = selectedWagIndex;
}

function showDeptOptions(selectedFacility)
{
	var currDept = deptColl[selectedFacility];
	var deptO = document.getElementById("deptId");
	for (var i = deptO.length; i >= 0;i--) {
		deptO[i] = null;
	}
	var selectedDeptIndex = 0;
	setOption(0,messagesData.all, "", "deptId");
	if(selectedFacility != 'All' && selectedFacility != '')
		for(var j = 0; j < currDept.length;j++)
			setOption(j+1,currDept[j].deptName, currDept[j].deptId, "deptId");

	deptO.selectedIndex = selectedDeptIndex;
}

function showAreaOptions(selectedFacility) {
	var areaArray = altAreaId[selectedFacility];
	var areaNameArray = altAreaName[selectedFacility];

	var selectedAreaIdIndex = 0;
	if (areaArray != null) {
		var count = 0;
		if (areaArray.length > 1 || areaArray.length == 0) {
			setArea(count++,messagesData.all,"");
		}
		for (var i=0; i < areaArray.length; i++) {
			setArea(i+count,areaNameArray[i],areaArray[i]);
			//selected reporting Entity
		}
	}else {
		setArea(0,messagesData.all,"");
	}
	$("areaId").selectedIndex = selectedAreaIdIndex;
	areaChanged();
}

function setArea(href,text,id) {
	var optionName = new Option(text, id, false, false)
	var areaO = document.getElementById("areaId");
	areaO[href] = optionName;
}

function areaChanged() {
	var buildingIdO = document.getElementById("buildingId");
	for (var i = buildingIdO.length; i >= 0;i--) {
		buildingIdO[i] = null;
	}
	var areaId = $v('areaId');
	showBuildingOptions($v("facilityId")+$v("areaId"));
}

function showBuildingOptions(selectedFacility) {
	var buildingIdArray = altBuildingId[selectedFacility];
	var buildingNameArray = altBuildingName[selectedFacility];

	var selectedBuildingIdIndex = 0;
	if (buildingIdArray != null) {
		var count = 0;
		if (buildingIdArray.length > 1 || buildingIdArray.length == 0) {
			setBuilding(count++,messagesData.all,"");
		}
		for (var i=0; i < buildingIdArray.length; i++) {
			setBuilding(i+count,buildingNameArray[i],buildingIdArray[i]);
		}
	}else {
		setBuilding(0,messagesData.all,"");
	}
	$("buildingId").selectedIndex = selectedBuildingIdIndex;
    buildingChanged();
}

function setBuilding(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var buildingIdO = document.getElementById("buildingId");
  buildingIdO[href] = optionName;
}

function buildingChanged() {
	var floorIdO = document.getElementById("floorId");
	for (var i = floorIdO.length; i >= 0;i--) {
		floorIdO[i] = null;
	}
	var buildingId = $v('buildingId');
	showFloorOptions($v("facilityId")+$v("areaId")+$v("buildingId"));
}

function showFloorOptions(selectedFacility) {
    var floorIdArray = altFloorId[selectedFacility];
	var floorDescriptionArray = altFloorName[selectedFacility];

	var selectedFloorIdIndex = 0;
	if (floorIdArray != null) {
		var count = 0;
		if (floorIdArray.length > 1 || floorIdArray.length == 0) {
			setFloor(count++,messagesData.all,"");
		}
		for (var i=0; i < floorIdArray.length; i++) {
			setFloor(i+count,floorDescriptionArray[i],floorIdArray[i]);
		}
	}else {
		setFloor(0,messagesData.all,"");
	}
	$("floorId").selectedIndex = selectedFloorIdIndex;
	floorChanged();
}

function setFloor(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var floorIdO = document.getElementById("floorId");
  floorIdO[href] = optionName;
}

function floorChanged() {
	var roomIdO = document.getElementById("roomId");
	for (var i = roomIdO.length; i >= 0;i--) {
		roomIdO[i] = null;
	}
	showRoomOptions($v("facilityId")+$v("areaId")+$v("buildingId")+$v("floorId"));
}

function showRoomOptions(selectedFacility) {
    var roomIdArray = altRoomId[selectedFacility];
	var roomNameArray = altRoomName[selectedFacility];

	var selectedRoomIdIndex = 0;
	if (roomIdArray != null) {
		var count = 0;
		if (roomIdArray.length > 1 || roomIdArray.length == 0) {
			setRoom(count++,messagesData.all,"");
		}
		for (var i=0; i < roomIdArray.length; i++) {
			setRoom(i+count,roomNameArray[i],roomIdArray[i]);
		}
	}else {
		setRoom(0,messagesData.all,"");
	}
	$("roomId").selectedIndex = selectedRoomIdIndex;
}

function setRoom(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var roomIdO = document.getElementById("roomId");
  roomIdO[href] = optionName;
}

function createXSL() {
	var flag = true;// validateForm();
	if (flag) {
		$('uAction').value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_ChareNumberReportExcel', '650', '600', 'yes');
		document.genericForm.target = '_ChareNumberReportExcel';
		var a = window.setTimeout("document.genericForm.submit();", 50);
	}
}