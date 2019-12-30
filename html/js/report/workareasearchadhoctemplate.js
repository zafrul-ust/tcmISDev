var callServ = true;
var allowMyFacilities = false;  //if page allow My Facilities then that page has to override this flag
var preSelectSavedValuesOnload = true;
var calledFromFacChange = false;

function showFacilityGroupOptions() {
	var facilityGroupArray = altFacilityGroupId;
	var facilityGroupDescArray = altFacilityGroupDescription;

	var selectedFacilityGroupIndex = 0;
	if(facilityGroupArray != null) {
		var count = 0;
		if(facilityGroupArray.length > 2) 
		for (var i=0; i < facilityGroupArray.length; i++) {
			if(facilityGroupArray[i].length == 0)
				setOption(i+count,messagesData.other,'', "facilityGroupId");
			else
				setOption(i+count,facilityGroupDescArray[i],facilityGroupArray[i], "facilityGroupId");
			//selected facility
			if ($v('preSelectDropsDowns').length == 0) 
				if ($v("templateFacilityGroupId") != null && facilityGroupArray[i] == $v("templateFacilityGroupId")) 
							selectedFacilityGroupIndex = i+count;
			//end of if selected facility is not null
		}
		else {
			var insertCount = 0;
			for (var i=0; i < facilityGroupArray.length; i++) {
	
				if (facilityGroupArray[i] != 'All' ) 
					if(facilityGroupArray[i].length > 0)
						setOption(insertCount++,facilityGroupDescArray[i],facilityGroupArray[i], "facilityGroupId");
					else
						setOption(insertCount++,messagesData.other,'', "facilityGroupId");
	
			}
		}
	}

	$("facilityGroupId").selectedIndex = selectedFacilityGroupIndex;
	facilityGroupChanged();
}

function facilityGroupChanged() {
	var facilityGroupO = document.getElementById("facilityGroupId");
	var facilityIdO = document.getElementById("facilityId");
	var selectedFacilityGroup = facilityGroupO.value;

	$('facilityGroupName').value = facilityGroupO.options[facilityGroupO.selectedIndex].text;

	for (var i = facilityIdO.length; i >= 0;i--) {
		facilityIdO[i] = null;
	}
	
	showFacilityOptions(selectedFacilityGroup);
}

function showFacilityOptions(selectedFacilityGroup) {
	var facilityArray = altFacilityId[selectedFacilityGroup];
	var facilityNameArray = altFacilityName[selectedFacilityGroup];

	var selectedFacilityIndex = 0;
	if(facilityArray != null) {
		var count = 0;
		if(facilityArray.length > 1) {
	   	    setOption(count++,messagesData.myFacilities,"All", "facilityId");
		}
		for (var i=0; i < facilityArray.length; i++) {
			setOption(i+count,facilityNameArray[i],facilityArray[i], "facilityId");
			//selected facility
			if ($v("templateFacilityId") != null) {
				if ($v("templateFacilityId").length > 0) {
					if (facilityArray[i] == $v("templateFacilityId")) {
						selectedFacilityIndex = i+count;
					}
				}
			} //end of if selected facility is not null
		}
	}else {
		setOption(0,messagesData.myFacilities,"All", "facilityId");
	}
	$("facilityId").selectedIndex = selectedFacilityIndex;
	
	facilityChanged();
}

function createElement(which,id)
{
	var input = document.createElement("input");
	input.setAttribute("type", "hidden");
	input.setAttribute("id", which + id);	
	var span = document.getElementById(which + 'Span');
	span.appendChild(input);
}

function clearElements(which)
{
	var span = document.getElementById(which + 'Span');
	span.innerHTML = '';
}

function facilityChanged() {

	callServ = false;
	calledFromFacChange = true;
	if (hideAreaBldgFlrRmOptionsJsp == 'Y') 
		hideAreaBldgFlrRmOptionsSpan.style['display'] = 'none';
	
	var facilityO = document.getElementById("facilityId");
	var selectedFacility = facilityO.value;		
	$('facilityName').value = facilityO.options[facilityO.selectedIndex].text;

	if (hideAreaBldgFlrRmOptionsJsp == 'N') {
		var areaO = document.getElementById("areaIdSel");
		for (var i = areaO.length; i >= 0;i--) {
			areaO[i] = null;
		}
		
		var off = $('areaName');
		if(off.style['display'] == '')
		{
			off.style['display'] = 'none';
			clearElements('areaId');
			 $('areaIdSel').style['display'] = '';
		}
	}
	
	off = $('deptName');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('deptId');
		$('deptIdSel').style['display'] = '';
	}
	
	off = $('applicationDesc');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('application');
		$('applicationSel').style['display'] = '';
	}
	
	showWagOptions(selectedFacility);
	showDeptOptions(selectedFacility);
	if (hideAreaBldgFlrRmOptionsJsp == 'N')
		showAreaOptions(selectedFacility);
	
	if(facilityO.value != '') {	
		if('Y' == $v("showFlammabilityVocZone"))
		{	
			var flammableEle = $('flammable');
			if(flammableEle && flammableEle != null)
			{
				var templateFlammableVal = $v('templateFlammable');
				flammableEle.value = templateFlammableVal;
				if(templateFlammableVal == 'Y' || templateFlammableVal == 'y')
					flammableEle.checked = true;			
			}	
			
			var pageIdEleU = $('pageID');
			
			if (facilityO.value != 'All' && (!pageIdEleU || pageIdEleU == null || pageIdEleU.value != 'adHocMatMx') ) {
				showFlammabilityControlZoneIdOptions(selectedFacility);
				showVocZoneIdOptions(selectedFacility);	
			}
		}
		storageAreaCall();		
	} else {
		showWorkArea();
	}
	
	//var selectedFacility = document.getElementById("facilityId").value;
	
	if ($v("reportTypeForWorkAreaSearchAdHocTemplate") == 'AdHocUsage' && hideDockDeliveryPointJsp == 'N')
		showDockLinks(selectedFacility);
	
	if ($v("reportTypeForWorkAreaSearchAdHocTemplate") == 'AdHocInventory' 
		|| $v("reportTypeForWorkAreaSearchAdHocTemplate") == 'AdHocUsage'){
		checkFacilityPermission(selectedFacility);
	}
}

function showCatalogOptions(selectedFacility) {
	var catalogIdArray = altCatalogId[selectedFacility];
	var catalogDescArray = altCatalogDesc[selectedFacility];
		
	if ($v('hideCatalogId') != 'true' && selectedFacility != '' && selectedFacility != 'All' && catalogIdArray.length > 1)
		$('showCatalogSpan').style['display']='';
	else 
		$('showCatalogSpan').style['display']='none';
	
	var selectedCatalogIdIndex = 0;
	if (catalogIdArray != null) {
		var count = 0;
		if (catalogIdArray.length > 1 || catalogIdArray.length == 0) {
			setCatalog(count++,messagesData.all,"");
		}
		for (var i=0; i < catalogIdArray.length; i++) {
			setCatalog(i+count,catalogDescArray[i],catalogIdArray[i]);
			//selected reporting Entity
			if ($v("templateCatalogId") != null) {
				if ($v("templateCatalogId").length > 0) {
					if (catalogIdArray[i] == $v("templateCatalogId")) {
						selectedCatalogIndex = i+count;
					}
				}
			} //end of if selected reporting Entity is not null
		}
	}else {
		setCatalog(0,messagesData.all,"");
	}
	
	myLocalCatalogId = $("catalogId");
	
	if($v('hideCatalogId') == 'true')
		{
			selectedCatalogIdIndex = 0;
			myLocalCatalogId.value = myLocalCatalogId.options[0].value; 
		}		

	myLocalCatalogId.selectedIndex = selectedCatalogIdIndex;
	catalogChanged();
}

function setCatalog(href,text,id) {
	var optionName = new Option(text, id, false, false)
	var catalogId = document.getElementById("catalogId");
	catalogId[href] = optionName;
}

function catalogChanged()
{
	var catalogCompanyIdArr = altCatalogCompanyId[$v('facilityId')];
	var catalogId = $('catalogId');
	if(catalogId.value == '')
		$('catalogCompanyId').value = '';
	else
		$('catalogCompanyId').value = catalogCompanyIdArr[(catalogId.options.length > 1  ? catalogId.selectedIndex - 1:catalogId.selectedIndex)];
	
	materialCategorySearchCall('catalogId');
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
			{
				setOption(j+1,currWag[j].wagName, currWag[j].wagId, "reportingEntityId");
				if ($v("templateReportingEntityId") != null && $v("templateReportingEntityId").length > 0) {
						if (currWag[j].wagId == $v("templateReportingEntityId")) {
							selectedWagIndex = j+1;
                        }
					}
			}
	wagO.selectedIndex = selectedWagIndex;
    workAreaGroupChanged();
}

function workAreaGroupChanged() {  
	
	reportingEntityId = $('reportingEntityId');
	$('reportingEntityName').value = reportingEntityId.options[reportingEntityId.selectedIndex].text;
	
	var off = $('applicationDesc');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('application');
		$('applicationSel').style['display'] = '';
	}
	
	if(callServ)
		storageAreaCall();
}

function showDeptOptions(selectedFacility)
{
	if (selectedFacility != '' && selectedFacility != 'All' && deptColl != null && deptColl[selectedFacility].length > 1)
		$('showDeptSpan').style['display']='';
	else 
		$('showDeptSpan').style['display']='none';
	
	var currDept = deptColl[selectedFacility];
	var deptO = document.getElementById('deptIdSel');
	for (var i = deptO.length; i >= 0;i--) {
		deptO[i] = null;
	}
	
	var preSelectDropDown = false;
	var templateDeptId = '';
	if($("templateDeptId") != null && preSelectSavedValuesOnload)
	{
		preSelectDropDown = true;
		templateDeptId = $v("templateDeptId");
		$('deptId').value = templateDeptId;
		var templateDeptIdArr = templateDeptId.split('\|');
		
		if(templateDeptIdArr.length > 1)
		{
			 for(var i = 0; i < templateDeptIdArr.length; i++)
				 createElement('deptId',templateDeptIdArr[i]);
			 preSelectDropDown = false;
			 deptO.style['display'] = 'none';
			 var deptName =  $('deptName');
			 var templateDeptName = $v('templateDeptName').replace(/\|/g, '; ');
			 deptName.value = templateDeptName;
			 deptName.title = templateDeptName;
			 deptName.style['display'] = '';
		}
		else
			{
			 	createElement('deptId',templateDeptIdArr[0]);
				$("deptName").style['display'] = 'none';
			}
	}
	
	var selectedDeptIndex = 0;
	setOption(0,messagesData.all, "", 'deptIdSel');
	if(selectedFacility != 'All' && selectedFacility != '')
		for(var j = 0; j < currDept.length;j++)
		{
			setOption(j+1,currDept[j].deptName, currDept[j].deptId, 'deptIdSel');
			if (preSelectDropDown && currDept[j].deptId == templateDeptId) 
					selectedDeptIndex = j+1;
		}
	
	if($('deptName').style['display'] == '')
		$('deptMultiSel').style['display']='';
	else if($('deptIdSel').options.length == 1)
			$('deptMultiSel').style['display']='none';
	else
		$('deptMultiSel').style['display']='';
	
	deptO.selectedIndex = selectedDeptIndex;
	
	deptChanged();
}

function deptChanged()
{
	var deptO = document.getElementById('deptIdSel');

	if($('deptName').style['display'] == 'none')
	{
		$('deptId').value = deptO.value;
		$('deptName').value = deptO.options[deptO.selectedIndex].text;
		$('deptName').style['display']='none';
		$('deptIdSel').style['display']='';
		clearElements('deptId');
		createElement('deptId',deptO.value);
	}
	
	var off = $('applicationDesc');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('application');
		$('applicationSel').style['display'] = '';
	}
	
	if(callServ)
		storageAreaCall();
}

function showAreaOptions(selectedFacility) {

	var preSelectDropDown = false;
	var templateAreaId = '';
	if(preSelectSavedValuesOnload)
	{
		preSelectDropDown = true;
		templateAreaId = $v("templateAreaId");
		$('areaId').value = templateAreaId;
		var templateAreaIdArr = $v("templateAreaId").split('\|');
		
		if(templateAreaIdArr.length > 1)
		{
			for(var i = 0; i < templateAreaIdArr.length; i++)
				createElement('areaId',templateAreaIdArr[i]);
			 preSelectDropDown = false;
			 $("areaIdSel").style['display'] = 'none';
			 var areaName =  $('areaName');
			 var templateAreaName = $v('templateAreaName').replace(/\|/g, '; ');
			 areaName.value = templateAreaName;
			 areaName.title = templateAreaName;
			 areaName.style['display'] = '';
		}
		else
		{
		 	createElement('areaId',templateAreaIdArr[0]);
			$("areaName").style['display'] = 'none';
		}

	}
	
	var areaArray = altAreaId[selectedFacility];
	var areaNameArray = altAreaName[selectedFacility];

	var count = 0,selectedAreaIdIndex = 0;
	setArea(count++,messagesData.all,"");
	if (areaArray != null) {
		for (var i=0; i < areaArray.length; i++) {
			setArea(i+count,areaNameArray[i],areaArray[i]);
			//selected reporting Entity
			if (preSelectDropDown) {
				if (areaArray[i] == templateAreaId) {
					selectedAreaIdIndex = i+count;
				}
			}
		}
	}	
	
	if($('areaName').style['display'] == '')
		$('areaMultiSel').style['display']='';
	else if($('areaIdSel').options.length == 1)
			$('areaMultiSel').style['display']='none';
	else
		$('areaMultiSel').style['display']='';
	
	$("areaIdSel").selectedIndex = selectedAreaIdIndex;
	areaChanged();
}

function setArea(href,text,id) {
	var optionName = new Option(text, id, false, false)
	var areaO = document.getElementById("areaIdSel");
	areaO[href] = optionName;
}

function areaChanged() {
	
	var areaCallServ = false;
	if(callServ)
		{
			areaCallServ = callServ;
			callServ = false;
		}
	
	var areaIdSel = $("areaIdSel");
	if($('areaName').style['display'] == 'none')
	{
		$('areaId').value = areaIdSel.value;
		$('areaName').value = areaIdSel.options[areaIdSel.selectedIndex].text;
		$('areaName').style['display']='none';
		clearElements('areaId');
		createElement('areaId',areaIdSel.value);
		//$('buildingFloorRoom').style['display']='';
	}	
	
	var off = $('buildingName');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('buildingId');
		 $('buildingIdSel').style['display'] = '';
	}
	
	off = $('applicationDesc');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('application');
		$('applicationSel').style['display'] = '';
	}
	
	var buildingIdO = document.getElementById("buildingIdSel");
	for (var i = buildingIdO.length; i >= 0;i--) {
		buildingIdO[i] = null;
	}
		
	var aOptions = areaIdSel.options;
	var aCount = 0;
	for(var j = 0; j < aOptions.length;j++)
		if(aOptions[j].selected)
			++aCount;
	
	if($('areaName').style['display'] == 'none')
	{
		if(aCount==1)
			showBuildingOptions($v("facilityId")+$v("areaIdSel"));
		else
			showBuildingOptions($v("facilityId")+'All');
	}
	else
		showBuildingOptions('#^%&*!$@');
	
	if(areaCallServ)
		{
			areaCallServ = false;
			storageAreaCall();
		}
}

function showBuildingOptions(selectedFacility) {
	var buildingIdArray = altBuildingId[selectedFacility];
	var buildingNameArray = altBuildingName[selectedFacility];
	
	var preSelectDropDown = false;
	var templateBuildingId = '';
	if(preSelectSavedValuesOnload)
	{
		preSelectDropDown = true;
		templateBuildingId = $v("templateBuildingId");
		$('buildingId').value = templateBuildingId;
		var templateBuildingIdArr = $v("templateBuildingId").split('\|');
		
		if(templateBuildingIdArr.length > 1)
		{
			 for(var i = 0; i < templateBuildingIdArr.length; i++)
				createElement('buildingId',templateBuildingIdArr[i]);
			 preSelectDropDown = false;
			 $('buildingIdSel').style['display']='none';
			 var buildingName =  $('buildingName');
			 var templateBuildingName = $v('templateBuildingName').replace(/\|/g, '; ');
			 buildingName.value = templateBuildingName;
			 buildingName.title = templateBuildingName;
			 buildingName.style['display'] = '';
		}
		else
			{
				createElement('buildingId',templateBuildingIdArr[0]);
				$("buildingName").style['display'] = 'none';
			}
	}

	var count = 0,selectedBuildingIdIndex = 0;
	setBuilding(count++,messagesData.all,"");
	if (buildingIdArray != null) {
		for (var i=0; i < buildingIdArray.length; i++) {
			setBuilding(i+count,buildingNameArray[i],buildingIdArray[i]);
			//selected building
				if (preSelectDropDown) {
					if (buildingIdArray[i] == templateBuildingId) {
						selectedBuildingIdIndex = i+count;
					}
				}
		}
	}
	
	if($('buildingName').style['display'] == '')
		$('buildingMultiSel').style['display']='';
	else if($('buildingIdSel').options.length == 1)
		$('buildingMultiSel').style['display']='none';
	else
		$('buildingMultiSel').style['display']='';
	
	$("buildingIdSel").selectedIndex = selectedBuildingIdIndex;
    buildingChanged();
}

function setBuilding(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var buildingIdO = document.getElementById("buildingIdSel");
  buildingIdO[href] = optionName;
}

function buildingChanged() {
	
	var buildingCallServ = false;
	if(callServ)
		{
			buildingCallServ = callServ;
			callServ = false;
		}

	var buildingIdSel = $("buildingIdSel");
	
	if($('buildingName').style['display'] == 'none')
	{
		$('buildingId').value =  buildingIdSel.value;
		$('buildingName').value = buildingIdSel.options[buildingIdSel.selectedIndex].text;
		$('buildingName').style['display']='none';
		clearElements('buildingId');
		createElement('buildingId',buildingIdSel.value);
	}
	
	var off = $('applicationDesc');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('application');
		$('applicationSel').style['display'] = '';
	}
	
	showFloorOptions($v("facilityId")+$v("areaId")+$v("buildingId"));
	
	if(buildingCallServ)
	{
		buildingCallServ = false;
		storageAreaCall();
	}
}

function showFloorOptions(selectedFacility) {
    var floorIdArray = altFloorId[selectedFacility];
	var floorDescriptionArray = altFloorName[selectedFacility];

	var count = 0,selectedFloorIdIndex = 0;
	setFloor(count++,messagesData.all,"");
	if (floorIdArray != null) {
		for (var i=0; i < floorIdArray.length; i++) {
			setFloor(i+count,floorDescriptionArray[i],floorIdArray[i]);
			//selected room
			if ($v("templateFloorId") != null) {
				if ($v("templateFloorId").length > 0) {
					if (floorIdArray[i] == $v("templateFloorId")) {
						selectedFloorIdIndex = i+count;
					}
				}
			} //end of if selected room is not null
		}
	}
	
	//test options
	//setFloor($('floorId').options.length,'TEST1','TEST1');
	//setFloor($('floorId').options.length,'TEST2','TEST2');
	$("floorId").selectedIndex = selectedFloorIdIndex;
	floorChanged();
}

function setFloor(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var floorIdO = document.getElementById("floorId");
  floorIdO[href] = optionName;
}

function floorChanged() {

	floorId = $('floorId');
	$('floorName').value = floorId.options[floorId.selectedIndex].text;
	
	var floorCallServ = false;
	if(callServ)
		{
		floorCallServ = callServ;
			callServ = false;
		}
	var roomIdO = document.getElementById("roomId");
	for (var i = roomIdO.length; i >= 0;i--) {
		roomIdO[i] = null;
	}
	var off = $('applicationDesc');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('application');
		$('applicationSel').style['display'] = '';
	}
	showRoomOptions($v("facilityId")+$v("areaId")+$v("buildingId")+$v("floorId"));
	if(floorCallServ)
	{
		floorCallServ = false;
		storageAreaCall();
	}
}

function showRoomOptions(selectedFacility) {
    var roomIdArray = altRoomId[selectedFacility];
	var roomNameArray = altRoomName[selectedFacility];

	var count = 0,selectedRoomIdIndex = 0;
	setRoom(count++,messagesData.all,"");	
	if (roomIdArray != null) {
		for (var i=0; i < roomIdArray.length; i++) {
			setRoom(i+count,roomNameArray[i],roomIdArray[i]);
			//selected room
			if ($v("templateRoomId") != null) {
				if ($v("templateRoomId").length > 0) {
					if (roomIdArray[i] == $v("templateRoomId")) {
						selectedRoomIdIndex = i+count;
					}
				}
			} //end of if selected room is not null
		}
	}
	//test options
	//setRoom($('roomId').options.length,'TEST1','TEST1');
	//setRoom($('roomId').options.length,'TEST2','TEST2');
	$("roomId").selectedIndex = selectedRoomIdIndex;
	roomChanged();
}

function setRoom(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var roomIdO = document.getElementById("roomId");
  roomIdO[href] = optionName;
}

function roomChanged() {
	
	roomId = $('roomId');
	$('roomName').value = roomId.options[roomId.selectedIndex].text;
	
	var off = $('applicationDesc');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('application');
		$('applicationSel').style['display'] = '';
	}
	if(callServ)
		storageAreaCall();
}

function storageAreaCall() {
	var loc = "storagearea.do?";
    var tmpReportType = 'AdHocMaterialMatrix';
    if ($v("reportTypeForWorkAreaSearchAdHocTemplate") != '')
        tmpReportType = $v("reportTypeForWorkAreaSearchAdHocTemplate");
    loc += "reportType="+tmpReportType;

    if($v("facilityId") != '')
		loc += "&facilityId="+$v("facilityId");
	if($v('areaId') != '')
		loc += "&areaId="+$v('areaId');
	if($v('buildingId') != '')
		loc += "&buildingId="+$v('buildingId');
	if($v("reportingEntityId") != '')
		loc += "&reportingEntityId="+$v("reportingEntityId");
	if($v("deptId") != '')
		loc += "&deptId="+$v("deptId");
	if($v("floorId") != '')
		loc += "&floorId="+$v("floorId");
	if($v("roomId") != '')
		loc += "&roomId="+$v("roomId");
	if($v("flammabilityControlZoneId") != '')
		loc += "&flammabilityControlZoneId="+$v("flammabilityControlZoneId");
	if($v("vocZoneId") != '')
		loc += "&vocZoneId="+$v("vocZoneId");
	callToServer(loc);
}

function showWorkArea(workAreaArray)
{
    var workAreaO = document.getElementById("applicationSel");
    
    if (workAreaO != null) {
        for ( var i = workAreaO.length; i >= 0; i--) {
            workAreaO[i] = null;
        }
    }
    
	var preSelectDropDown = false;
	var templateApplication = '';
	var preSelectIndex = 0;
	if(preSelectSavedValuesOnload)
	{
		preSelectDropDown = true;
		templateApplication = $v("templateApplication");
		$('application').value = templateApplication;
		var templateApplicationArr = $v("templateApplication").split('\|');
		
		if(templateApplicationArr.length > 1)
		{
			preSelectDropDown = true;
			 for(var i = 0; i < templateApplicationArr.length; i++)
				 createElement('application',templateApplicationArr[i]);
			 $('applicationSel').style['display'] = 'none';
			 preSelectDropDown = false;
			 var applicationDesc =  $('applicationDesc');
			 var templateApplicationDesc = $v('templateApplicationDesc').replace(/\|/g, '; ');
			 applicationDesc.value = templateApplicationDesc;
			 applicationDesc.title = templateApplicationDesc;
			 applicationDesc.style['display'] = '';
		}
		else
		{
			createElement('application',templateApplicationArr[0]);
			$("applicationDesc").style['display'] = 'none';
		}
			
	}

	 if (workAreaArray != null) {
		 var i = 0;
		 if (workAreaArray.rows.length == 0 || workAreaArray.rows.length > 1) {
			  setOption(i++, messagesData.all, "", "applicationSel");
		 }

		 if (workAreaArray.rows.length == 1) {
			  setOption(0, workAreaArray.rows[0].desc, workAreaArray.rows[0].id, "applicationSel");
		 }
		 else {
			  for (; i <= workAreaArray.rows.length; i++) {
					setOption(i, workAreaArray.rows[i - 1].desc, workAreaArray.rows[i - 1].id, "applicationSel");
					if(preSelectDropDown && workAreaArray.rows[i - 1].id == templateApplication)
						preSelectIndex = i;
			  }
		 }

		 }else
			setOption(0, messagesData.all, "", "applicationSel");
	 
	 workAreaO.selectedIndex = preSelectIndex;
	 
	 workAreaChanged();
	 
	 if(calledFromFacChange)
	 {
	 	setMaterialCategory();
	 	calledFromFacChange = false;
	 }
		 
	 callServ = true;
}

function workAreaChanged()
{
	if($('applicationDesc').style['display'] == 'none')
	{
		applicationSel = $('applicationSel');
		$('applicationDesc').value = applicationSel.options[applicationSel.selectedIndex].text;
		$('application').value = applicationSel.value;
		applicationSel.style['display']='';
		if(applicationSel.options.length == 1)
			$('applicationMultiSel').style['display']='none';
		else
			$('applicationMultiSel').style['display']='';	
		clearElements('application');
		createElement('application',applicationSel.value);
	}
	else
		$('applicationMultiSel').style['display']='';
}


function popMultiSel(dropDownId) 
{
	 children[children.length] = openWinGeneric("adhocinventoryreport.do?uAction=multiSelect&dropDownId="+dropDownId,"edit_field_list","800","500","yes");
	 showTransitWin();
}


function multiSelRet(retArr,which)
{
	if(retArr.length < 2)
		{
			if(which == 'areaId')
				{
					$('areaName').style['display'] = 'none';
					//$('buildingFloorRoom').style['display'] = '';
				    var areaIdSel = $("areaIdSel");
				    areaIdSel.style['display'] = '';
				    if(retArr.length > 0)
				    {
					    var selected = retArr[0].split('#^%&*!$@')[1];
						var options = areaIdSel.options;
						for(var i = 0; i < options.length; i++)
							if(options[i].value == selected)
							{
								areaIdSel.selectedIndex = i;
								break;
							}
				    }
				    else
				    	areaIdSel.selectedIndex = 0;
					areaChanged();
				}
			else if(which == 'deptId')
			{
				$('deptName').style['display'] = 'none';
			    var deptIdSel = $("deptIdSel");
			    deptIdSel.style['display'] = '';
			    if(retArr.length > 0)
			    {
				    var selected = retArr[0].split('#^%&*!$@')[1];
					var options = deptIdSel.options;
					for(var i = 0; i < options.length; i++)
						if(options[i].value == selected)
						{
							deptIdSel.selectedIndex = i;
							break;
						}
			    }
			    else
			    	deptIdSel.selectedIndex = 0;
				deptChanged();
			}
			else if(which == 'buildingId')
				{
					$('buildingName').style['display'] = 'none';
				    var buildingIdSel = $("buildingIdSel");
				    buildingIdSel.style['display'] = '';
				    if(retArr.length > 0)
				    {
					    var selected = retArr[0].split('#^%&*!$@')[1];
						var options = buildingIdSel.options;
						for(var i = 0; i < options.length; i++)
							if(options[i].value == selected)
							{
								buildingIdSel.selectedIndex = i;
								break;
							}
				    }
				    else
				    	buildingIdSel.selectedIndex = 0;
				buildingChanged();
				}
			else if(which == 'countTypeArray')
			{
				$('countTypeArrayMultiSelTxt').style['display'] = 'none';
			    var countTypeArraySel = $("countTypeArraySel");
			    countTypeArraySel.style['display'] = '';
			    if(retArr.length > 0)
			    {
				    var selected = retArr[0].split('#^%&*!$@')[1];
					var options = countTypeArraySel.options;
					for(var i = 0; i < options.length; i++)
						if(options[i].value == selected)
						{
							countTypeArraySel.selectedIndex = i;
							break;
						}
			    }
			    else
			    	countTypeArraySel.selectedIndex = 0;
				countTypeArrayChanged();
			}
			else if(which == 'materialCategoryId')
			{
				$('materialCategoryName').style['display'] = 'none';
			    var materialCategoryIdSel = $("materialCategoryIdSel");
			    materialCategoryIdSel.style['display'] = '';
			    if(retArr.length > 0)
			    {
				    var selected = retArr[0].split('#^%&*!$@')[1];
					var options = materialCategoryIdSel.options;
					for(var i = 0; i < options.length; i++)
						if(options[i].value == selected)
						{
							materialCategoryIdSel.selectedIndex = i;
							break;
						}
			    }
			    else
			    	materialCategoryIdSel.selectedIndex = 0;
			    
			    materialCategorySearchCall(which);
			}
			else if(which == 'materialSubcategoryId')
			{
				$('materialSubcategoryName').style['display'] = 'none';
			    var materialSubcategoryIdSel = $("materialSubcategoryIdSel");
			    materialSubcategoryIdSel.style['display'] = '';
			    if(retArr.length > 0)
			    {
				    var selected = retArr[0].split('#^%&*!$@')[1];
					var options = materialSubcategoryIdSel.options;
					for(var i = 0; i < options.length; i++)
						if(options[i].value == selected)
						{
							materialSubcategoryIdSel.selectedIndex = i;
							break;
						}
			    }
			    else
			    	materialSubcategoryIdSel.selectedIndex = 0;
			}
			else if(which == 'flammabilityControlZoneId')
			{
				$('flammabilityControlZoneDesc').style['display'] = 'none';
			    var flammabilityControlZoneIdSel = $("flammabilityControlZoneIdSel");
			    flammabilityControlZoneIdSel.style['display'] = '';
			    if(retArr.length > 0)
			    {
				    var selected = retArr[0].split('#^%&*!$@')[1];
					var options = flammabilityControlZoneIdSel.options;
					for(var i = 0; i < options.length; i++)
						if(options[i].value == selected)
						{
							flammabilityControlZoneIdSel.selectedIndex = i;
							break;
						}
			    }
			    else
			    	flammabilityControlZoneIdSel.selectedIndex = 0;
			}
			else if(which == 'vocZoneId')
			{
				$('vocZoneDescription').style['display'] = 'none';
			    var vocZoneIdSel = $("vocZoneIdSel");
			    vocZoneIdSel.style['display'] = '';
			    if(retArr.length > 0)
			    {
				    var selected = retArr[0].split('#^%&*!$@')[1];
					var options = vocZoneIdSel.options;
					for(var i = 0; i < options.length; i++)
						if(options[i].value == selected)
						{
							vocZoneIdSel.selectedIndex = i;
							break;
						}
			    }
			    else
			    	vocZoneIdSel.selectedIndex = 0;
			}
			else
			{
				$('applicationDesc').style['display'] = 'none';
			    var applicationSel = $("applicationSel");
			    applicationSel.style['display'] = '';
			    if(retArr.length > 0)
			    {
				    var selected = retArr[0].split('#^%&*!$@')[1];
				    $('application').value = selected;
					var options = applicationSel.options;
					for(var i = 0; i < options.length; i++)
						if(options[i].value == selected)
						{
							applicationSel.selectedIndex = i;
							break;
						}
					$('applicationDesc').value = applicationSel.options[applicationSel.selectedIndex].text;
			    }
			    else
			    	{
			    		applicationSel.selectedIndex = 0;
					    $('application').value = applicationSel.value;
						$('applicationDesc').value = applicationSel.options[0].text;
			    	}
			}
		}
	else{
		if(which == 'areaId')
		{
			//$('buildingFloorRoom').style['display'] = 'none';
			$('areaIdSel').style['display'] = 'none';
			var areaName = $('areaName');
			areaName.style['display'] = '';
			var areaDisplay = '';
			var areaIds = '';
            var areaTooltipDisplay = '';
            for(var i = 0; i < retArr.length; i++)
				{
					var selectedArr = retArr[i].split('#^%&*!$@');
					areaDisplay += selectedArr[0];
                    areaTooltipDisplay += selectedArr[0];
                    if(i != retArr.length - 1) {
						areaDisplay += "; ";
                        areaTooltipDisplay += "\n";
                    }
                    areaIds += selectedArr[1];
					if(i != retArr.length - 1)
						areaIds += "|";
				}
			areaName.value = areaDisplay;
			areaName.title = areaTooltipDisplay;
		    $('areaId').value = areaIds;
		    areaChanged();
		    // callServ = false;
			//showBuildingOptions('#^%&*!$@');
			//storageAreaCall();
		}
		else if(which == 'deptId')
		{
			$('deptIdSel').style['display'] = 'none';
			var deptName = $('deptName');
			deptName.style['display'] = '';
			var deptDisplay = '';
			var deptIds = '';
            var deptTooltipDisplay = '';
            for(var i = 0; i < retArr.length; i++)
				{
					var selectedArr = retArr[i].split('#^%&*!$@');
					deptDisplay += selectedArr[0];
                    deptTooltipDisplay += selectedArr[0];
                    if(i != retArr.length - 1) {
						deptDisplay += "; ";
                        deptTooltipDisplay += "\n";
                    }
                    deptIds += selectedArr[1];
					if(i != retArr.length - 1)
						deptIds += "|";
				}
			deptName.value = deptDisplay;
			deptName.title = deptTooltipDisplay;
		    $('deptId').value = deptIds;
			deptChanged();
		}
		else if(which == 'buildingId')
			{
				$('buildingIdSel').style['display'] = 'none';
				var buildingName = $('buildingName');
				buildingName.style['display'] = '';
				var buildingDisplay = '';
				var buildingIds = '';
                var buildingTooltipDisplay = '';
                for(var i = 0; i < retArr.length; i++)
					{
						var selectedArr = retArr[i].split('#^%&*!$@');
						buildingDisplay += selectedArr[0];
                        buildingTooltipDisplay += selectedArr[0];
                        if(i != retArr.length - 1) {
							buildingDisplay += "; ";
                            buildingTooltipDisplay += "\n";
                        }
                        buildingIds += selectedArr[1];
						if(i != retArr.length - 1)
							buildingIds += "|";
					}
				buildingName.value = buildingDisplay;
				buildingName.title = buildingTooltipDisplay;
			    $('buildingId').value = buildingIds;
				buildingChanged();
			}
		else if(which == 'countTypeArray')
		{
			$('countTypeArraySel').style['display'] = 'none';
			var countTypeArrayMultiSelTxt = $('countTypeArrayMultiSelTxt');
			countTypeArrayMultiSelTxt.style['display'] = '';
			var countTypeArrayDisplay = '';
			var countTypeArrays = '';
            var countTypeTooltipDisplay = '';
            for(var i = 0; i < retArr.length; i++)
				{
					var selectedArr = retArr[i].split('#^%&*!$@');
					countTypeArrayDisplay += selectedArr[0];
                    countTypeTooltipDisplay += selectedArr[0];
                    if(i != retArr.length - 1) {
						countTypeArrayDisplay += "; ";
                        countTypeTooltipDisplay += "\n";
                    }
                    countTypeArrays += selectedArr[1];
					if(i != retArr.length - 1)
						countTypeArrays += "|";
				}
			countTypeArrayMultiSelTxt.value = countTypeArrayDisplay;
			countTypeArrayMultiSelTxt.title = countTypeTooltipDisplay;
            $('countTypeArray').value = countTypeArrays;
		}
		else if(which == 'materialSubcategoryId')
		{
			$('materialSubcategoryIdSel').style['display'] = 'none';
			var materialSubcategoryName = $('materialSubcategoryName');
			materialSubcategoryName.style['display'] = '';
			var materialSubcategoryDisplay = '';
			var materialSubcategoryIds = '';
            var materialSubcategoryTooltipDisplay = '';
            for(var i = 0; i < retArr.length; i++)
				{
					var selectedArr = retArr[i].split('#^%&*!$@');
					materialSubcategoryDisplay += selectedArr[0];
                    materialSubcategoryTooltipDisplay += selectedArr[0];
                    if(i != retArr.length - 1) {
						materialSubcategoryDisplay += "; ";
                        materialSubcategoryTooltipDisplay += "\n";
                    }
                    materialSubcategoryIds += selectedArr[1];
					if(i != retArr.length - 1)
						materialSubcategoryIds += "|";
				}
            materialSubcategoryName.value = materialSubcategoryDisplay;
            materialSubcategoryName.title = materialSubcategoryTooltipDisplay;
		    $('materialSubcategoryId').value = materialSubcategoryIds;
		}
		else if(which == 'materialCategoryId')
		{
			$('materialCategoryIdSel').style['display'] = 'none';
			var materialCategoryName = $('materialCategoryName');
			materialCategoryName.style['display'] = '';
			var materialCategoryDisplay = '';
			var materialCategoryIds = '';
            var materialCategoryTooltipDisplay = '';
            for(var i = 0; i < retArr.length; i++)
				{
					var selectedArr = retArr[i].split('#^%&*!$@');
					materialCategoryDisplay += selectedArr[0];
                    materialCategoryTooltipDisplay += selectedArr[0];
                    if(i != retArr.length - 1) {
						materialCategoryDisplay += "; ";
                        materialCategoryTooltipDisplay += "\n";
                    }
                    materialCategoryIds += selectedArr[1];
					if(i != retArr.length - 1)
						materialCategoryIds += "|";
				}
            materialCategoryName.value = materialCategoryDisplay;
            materialCategoryName.title = materialCategoryTooltipDisplay;
		    $('materialCategoryId').value = materialCategoryIds;
		    materialCategorySearchCall(which);
		}
		else if(which == 'flammabilityControlZoneId')
		{
			$('flammabilityControlZoneIdSel').style['display'] = 'none';
			var flammabilityControlZoneDesc = $('flammabilityControlZoneDesc');
			flammabilityControlZoneDesc.style['display'] = '';
			var flammabilityControlZoneIdDisplay = '';
			var flammabilityControlZoneIds = '';
            var flammabilityControlZoneIdTooltipDisplay = '';
            for(var i = 0; i < retArr.length; i++)
				{
					var selectedArr = retArr[i].split('#^%&*!$@');
					flammabilityControlZoneIdDisplay += selectedArr[0];
                    flammabilityControlZoneIdTooltipDisplay += selectedArr[0];
                    if(i != retArr.length - 1) {
						flammabilityControlZoneIdDisplay += "; ";
                        flammabilityControlZoneIdTooltipDisplay += "\n";
                    }
                    flammabilityControlZoneIds += selectedArr[1];
					if(i != retArr.length - 1)
						flammabilityControlZoneIds += "|";
				}
			flammabilityControlZoneDesc.value = flammabilityControlZoneIdDisplay;
			flammabilityControlZoneDesc.title = flammabilityControlZoneIdTooltipDisplay;
		    $('flammabilityControlZoneId').value = flammabilityControlZoneIds;
			flammabilityControlZoneIdChanged();		
		}
		else if(which == 'vocZoneId')
		{
			$('vocZoneIdSel').style['display'] = 'none';
			var vocZoneDescription = $('vocZoneDescription');
			vocZoneDescription.style['display'] = '';
			var vocZoneIdDisplay = '';
			var vocZoneIds = '';
            var vocZoneIdTooltipDisplay = '';
            for(var i = 0; i < retArr.length; i++)
				{
					var selectedArr = retArr[i].split('#^%&*!$@');
					vocZoneIdDisplay += selectedArr[0];
                    vocZoneIdTooltipDisplay += selectedArr[0];
                    if(i != retArr.length - 1) {
						vocZoneIdDisplay += "; ";
                        vocZoneIdTooltipDisplay += "\n";
                    }
                    vocZoneIds += selectedArr[1];
					if(i != retArr.length - 1)
						vocZoneIds += "|";
				}
			vocZoneDescription.value = vocZoneIdDisplay;
			vocZoneDescription.title = vocZoneIdTooltipDisplay;
		    $('vocZoneId').value = vocZoneIds;
			vocZoneIdChanged();
		}
		else
			{
				$('applicationSel').style['display'] = 'none';
				var applicationDesc = $('applicationDesc');
				applicationDesc.style['display'] = '';
				var applicationDisplay = '';
				var applications = '';
                var applicationTooltipDisplay = '';
                for(var i = 0; i < retArr.length; i++)
					{
						var selectedArr = retArr[i].split('#^%&*!$@');
						applicationDisplay += selectedArr[0];
                        applicationTooltipDisplay += selectedArr[0];
                        if(i != retArr.length - 1) {
							applicationDisplay += "; ";
                            applicationTooltipDisplay += "\n";
                        }
                        applications += selectedArr[1];
						if(i != retArr.length - 1)
							applications += "|";
					}
                applicationDesc.value = applicationDisplay;
				applicationDesc.title = applicationTooltipDisplay;
			    $('application').value = applications;
			}

	}
}

function setMaterialCategory()
{
	var selectedFacility = $v('facilityId');
	if($("subcategorySpan") != null && featureReleaseCheck[selectedFacility] == 'Y') {
		$("subcategorySpan").style.display = "";
		var catalogId = document.getElementById("catalogId");
		for (var i = catalogId.length; i >= 0;i--) {
			catalogId[i] = null;
		}
		$("isMatCatFX").value = true;
		showCatalogOptions(selectedFacility);
	}
	else if($("subcategorySpan") != null) {
		$("isMatCatFX").value = false;
		$("subcategorySpan").style.display = "none";
		$("materialSubcategoryIdSel").value = "";
		$("materialCategoryIdSel").value = "";
		$("materialCategoryId").value = "";
		$("materialSubcategoryId").value = "";
		$("catalogCompanyId").value = "";
	}
	
	if($('materialSubcategoryName').style['display'] == '')
	{
		$('materialSubcategoryName').style['display'] = 'none';
		clearElements('materialSubcategoryId');
		$('materialSubcategoryIdSel').style['display'] = '';
	}
	if($('materialCategoryName').style['display'] == '')
	{
		$('materialCategoryName').style['display'] = 'none';
		clearElements('materialCategoryId');
		$('materialCategoryIdSel').style['display'] = '';
	}
}


function materialCategorySearchCall(id)
{
	var loc = "materialcategorytemplate.do?catalogCompanyId="+$v('catalogCompanyId')+"&which=" + id;
	
	if($v("templateCatalogId") != '' && preSelectSavedValuesOnload)
	{
		loc += "&catalogId="+ $v('templateCatalogId');
		loc += "&materialCategoryId=" +  $v('templateMaterialCategoryId');
	}
	else
	{
		loc += "&catalogId="+ $v('catalogId');
		if(id == 'materialCategoryId')
		{	
			if($('materialCategoryName').style['display'] == 'none')
				{
					var materialCategoryIdSel = $('materialCategoryIdSel');
					$('materialCategoryName').value = materialCategoryIdSel.options[materialCategoryIdSel.selectedIndex].text;
					$('materialCategoryId').value = materialCategoryIdSel.value;
					loc += "&materialCategoryId=" + materialCategoryIdSel.value;
					clearElements('materialCategoryId');
					createElement('materialCategoryId',materialCategoryIdSel.value);
					
				}
			else
				loc += "&materialCategoryId="+$v('materialCategoryId');
		}
		else
			{
				$('materialCategoryIdSel').style['display'] = '';
				$('materialCategoryName').style['display'] = 'none';
				$('materialCategoryName').value = '';
				clearElements('materialCategoryId');
			}
	}
	callToServer(loc);
}



function showMaterialCategoryResults(materialCategoryJsonData,materialSubcategoryJsonData)
{
    var materialCategoryIdSel = $("materialCategoryIdSel");
     
    var materialSubcategoryIdSel = $("materialSubcategoryIdSel");
    
    if (materialSubcategoryIdSel != null) {
        for ( var i = materialSubcategoryIdSel.length; i >= 0; i--) {
        	materialSubcategoryIdSel[i] = null;
        }
    }
    
	var preMaterialCategoryId = false;
	var preMaterialSubCategoryId = false;
	var templateApplication = '';
	var preSelectIndex = 0;
	if(preSelectSavedValuesOnload)
	{
		preMaterialCategoryId = true;
		preMaterialSubCategoryId = true;
		templateMaterialCategoryId = $v("templateMaterialCategoryId");
		$('materialCategoryId').value = templateMaterialCategoryId;
		var templateMaterialCategoryIdArr = templateMaterialCategoryId.split('\|');
		
		if(templateMaterialCategoryIdArr.length > 1)
		{
			preMaterialCategoryId = true;
			 for(var i = 0; i < templateMaterialCategoryIdArr.length; i++)
				 createElement('materialCategoryId',templateMaterialCategoryIdArr[i]);
			 $('materialCategoryIdSel').style['display'] = 'none';
			 preMaterialCategoryId = false;
			 var materialCategoryName =  $('materialCategoryName');
			 var templateMaterialCategoryName = $v('templateMaterialCategoryName').replace(/\|/g, '; ');
			 materialCategoryName.value = templateMaterialCategoryName;
			 materialCategoryName.title = templateMaterialCategoryName;
			 materialCategoryName.style['display'] = '';
		}
		else
		{
			createElement('materialCategoryId',templateMaterialCategoryIdArr[0]);
			$("materialCategoryName").value = $v('templateMaterialCategoryName');
			$("materialCategoryName").style['display'] = 'none';
		}
		
		
		templateMaterialSubcategoryId = $v("templateMaterialSubcategoryId");
		$('materialSubcategoryId').value = templateMaterialSubcategoryId;
		var templateMaterialSubcategoryIdArr = templateMaterialSubcategoryId.split('\|');
		
		if(templateMaterialSubcategoryIdArr.length > 1)
		{
			preMaterialSubCategoryId = true;
			 for(var i = 0; i < templateMaterialSubcategoryIdArr.length; i++)
				 createElement('materialSubcategoryId',templateMaterialSubcategoryIdArr[i]);
			 $('materialSubcategoryIdSel').style['display'] = 'none';
			 preMaterialSubCategoryId = false;
			 var materialSubcategoryName =  $('materialSubcategoryName');
			 var templateMaterialSubcategoryName = $v('templateMaterialSubcategoryName').replace(/\|/g, '; ');
			 materialSubcategoryName.value = templateMaterialSubcategoryName;
			 materialSubcategoryName.title = templateMaterialSubcategoryName;
			 materialSubcategoryName.style['display'] = '';
		}
		else
		{
			createElement('materialSubcategoryId',templateMaterialSubcategoryIdArr[0]);
			$("materialSubcategoryName").value = $v('templateMaterialCategoryName');
			$("materialSubcategoryName").style['display'] = 'none';
		}
			
	}
	else
		{
			$('materialSubcategoryIdSel').style['display'] = '';
			$('materialSubcategoryName').style['display'] = 'none';
			$('materialSubcategoryName').value = '';
			clearElements('materialSubcategoryId');
		}
	
	 if (materialCategoryJsonData != null) {
		 
		    if (materialCategoryIdSel != null) {
		        for ( var i = materialCategoryIdSel.length; i >= 0; i--) {
		        	materialCategoryIdSel[i] = null;
		        }
		    }
		 
		 var i = 0;
		 
		 setOption(i++, messagesData.all, "", "materialCategoryIdSel");


	  for (; i <= materialCategoryJsonData.rows.length; i++) {
			setOption(i, materialCategoryJsonData.rows[i - 1].desc, materialCategoryJsonData.rows[i - 1].id, "materialCategoryIdSel");
			if(preMaterialCategoryId && materialCategoryJsonData.rows[i - 1].id == templateMaterialCategoryId)
				preSelectIndex = i;
	  }
	  
	  materialCategoryIdSel.selectedIndex = preSelectIndex;

		 
		  if(!preSelectSavedValuesOnload)
			  {
			   var materialCategoryIdSel = $('materialCategoryIdSel');
			  	$('materialCategoryId').value = materialCategoryIdSel.value;
			  	$('materialCategoryName').value = materialCategoryIdSel.options[materialCategoryIdSel.selectedIndex].text;
			  }

		 }
	 /*else
		 {
			//setOption(0, messagesData.all, "", "materialCategoryIdSel");
			if(!preSelectSavedValuesOnload)
				  $('materialCategoryId').value = '';
		 }*/
	 
	 if (materialSubcategoryJsonData != null) {
			
		 var i = 0;
		 preSelectIndex = 0;

		setOption(i++, messagesData.all, "", "materialSubcategoryIdSel");

		for (; i <= materialSubcategoryJsonData.rows.length; i++) {
			setOption(i, materialSubcategoryJsonData.rows[i - 1].desc,
					materialSubcategoryJsonData.rows[i - 1].id,
					"materialSubcategoryIdSel");
			if (preMaterialSubCategoryId
					&& materialSubcategoryJsonData.rows[i - 1].id == templateMaterialSubcategoryId)
				preSelectIndex = i;
		}
		materialSubcategoryIdSel.selectedIndex = preSelectIndex;

		if (!preSelectSavedValuesOnload) {
			var materialSubcategoryIdSel = $('materialSubcategoryIdSel');
			$('materialSubcategoryId').value = materialSubcategoryIdSel.value;
			$('materialSubcategoryName').value = materialSubcategoryIdSel.options[materialSubcategoryIdSel.selectedIndex].text;
		}
	 }
	 else
		{
			setOption(0, messagesData.all, "", "materialSubcategoryIdSel");
			if(!preSelectSavedValuesOnload)
				  $('materialSubcategoryId').value = '';
		}
	 
	 
	 
	 if(preSelectSavedValuesOnload)
		 preSelectSavedValuesOnload = false;
	 
}

function showFlammabilityControlZoneIdOptions(selectedFacility){
	
	if (selectedFacility != '' && selectedFacility != 'All' && flammabilityControlZoneIdColl[selectedFacility].length != 0) {
		$('flammabilityControlZoneIdShowSpan').style['display']='';
		if ($v("reportTypeForWorkAreaSearchAdHocTemplate") == 'AdHocInventory')
			showOverFlamCtrlZnLimitChanged();
	}
	
	var currFlammabilityControlZoneId = flammabilityControlZoneIdColl[selectedFacility];
	var flammabilityControlZoneIdO = document.getElementById('flammabilityControlZoneIdSel');
	for (var i = flammabilityControlZoneIdO.length; i >= 0;i--) {
		flammabilityControlZoneIdO[i] = null;
	}
	
	var preSelectDropDown = false;
	var templateFlammabilityControlZoneId = '';
	if($("templateFlammabilityControlZoneId") != null && preSelectSavedValuesOnload)
	{
		preSelectDropDown = true;
		templateFlammabilityControlZoneId = $v("templateFlammabilityControlZoneId");
		$('flammabilityControlZoneId').value = templateFlammabilityControlZoneId;
		var templateFlammabilityControlZoneIdArr = templateFlammabilityControlZoneId.split('\|');
		
		if(templateFlammabilityControlZoneIdArr.length > 1)
		{
			 for(var i = 0; i < templateFlammabilityControlZoneIdArr.length; i++)
				 createElement('flammabilityControlZoneId',templateFlammabilityControlZoneIdArr[i]);
			 preSelectDropDown = false;
			 flammabilityControlZoneIdO.style['display'] = 'none';
			 var flammabilityControlZoneDesc =  $('flammabilityControlZoneDesc');
			 var templateFlammabilityControlZoneDesc = $v('templateFlammabilityControlZoneDesc').replace(/\|/g, '; ');
			 flammabilityControlZoneDesc.value = templateFlammabilityControlZoneDesc;
			 flammabilityControlZoneDesc.title = templateFlammabilityControlZoneDesc;
			 flammabilityControlZoneDesc.style['display'] = '';
		}
		else
			{
			 	createElement('flammabilityControlZoneId',templateFlammabilityControlZoneIdArr[0]);
				$("flammabilityControlZoneDesc").style['display'] = 'none';
			}
	}
	
	var selectedFlammabilityControlZoneIdIndex = 0;
	setOption(0,messagesData.all, "", 'flammabilityControlZoneIdSel');
	if(selectedFacility != 'All' && selectedFacility != '')
		for(var j = 0; j < currFlammabilityControlZoneId.length;j++)
		{
			setOption(j+1,currFlammabilityControlZoneId[j].flammabilityControlZoneDesc, currFlammabilityControlZoneId[j].flammabilityControlZoneId, 'flammabilityControlZoneIdSel');
			if (preSelectDropDown && currFlammabilityControlZoneId[j].flammabilityControlZoneId == templateFlammabilityControlZoneId) 
					selectedFlammabilityControlZoneIdIndex = j+1;
		}
	
	if($('flammabilityControlZoneDesc').style['display'] == '')
		$('flammabilityControlZoneIdMultiSel').style['display']='';
	else if($('flammabilityControlZoneIdSel').options.length == 1)
			$('flammabilityControlZoneIdMultiSel').style['display']='none';
	else
		$('flammabilityControlZoneIdMultiSel').style['display']='';
	
	flammabilityControlZoneIdO.selectedIndex = selectedFlammabilityControlZoneIdIndex;
		
	flammabilityControlZoneIdChanged();
}

function setFlammabilityControlZoneId(href,text,id) {
	var optionName = new Option(text, id, false, false)
	var flammabilityControlZoneIdO = document.getElementById("flammabilityControlZoneIdSel");
	flammabilityControlZoneIdO[href] = optionName;
}

function flammabilityControlZoneIdChanged() {
	var flammabilityControlZoneIdSelO = document.getElementById('flammabilityControlZoneIdSel');

	if($('flammabilityControlZoneDesc').style['display'] == 'none')
	{
		$('flammabilityControlZoneId').value = flammabilityControlZoneIdSelO.value;
		$('flammabilityControlZoneDesc').value = flammabilityControlZoneIdSelO.options[flammabilityControlZoneIdSelO.selectedIndex].text;
		$('flammabilityControlZoneDesc').style['display']='none';
		$('flammabilityControlZoneIdSel').style['display']='';
		clearElements('flammabilityControlZoneId');
		createElement('flammabilityControlZoneId',flammabilityControlZoneIdSelO.value);
	}
	
	var off = $('applicationDesc');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('application');
		$('applicationSel').style['display'] = '';
	}
	if(callServ)
		storageAreaCall();
}


function showVocZoneIdOptions(selectedFacility){
	if (selectedFacility != '' && selectedFacility != 'All' && vocZoneIdColl[selectedFacility].length != 0) {
		$('vocZoneIdShowSpan').style['display']='';
	}
	var currVocZoneId = vocZoneIdColl[selectedFacility];
	var vocZoneIdO = document.getElementById('vocZoneIdSel');
	for (var i = vocZoneIdO.length; i >= 0;i--) {
		vocZoneIdO[i] = null;
	}
	
	var preSelectDropDown = false;
	var templateVocZoneId = '';
	if($("templateVocZoneId") != null && preSelectSavedValuesOnload)
	{
		preSelectDropDown = true;
		templateVocZoneId = $v("templateVocZoneId");
		$('vocZoneId').value = templateVocZoneId;
		var templateVocZoneIdArr = templateVocZoneId.split('\|');
		
		if(templateVocZoneIdArr.length > 1)
		{
			 for(var i = 0; i < templateVocZoneIdArr.length; i++)
				 createElement('vocZoneId',templateVocZoneIdArr[i]);
			 preSelectDropDown = false;
			 vocZoneIdO.style['display'] = 'none';
			 var vocZoneDescription =  $('vocZoneDescription');
			 var templateVocZoneDescription = $v('templateVocZoneDescription').replace(/\|/g, '; ');
			 vocZoneDescription.value = templateVocZoneDescription;
			 vocZoneDescription.title = templateVocZoneDescription;
			 vocZoneDescription.style['display'] = '';
		}
		else
			{
			 	createElement('vocZoneId',templateVocZoneIdArr[0]);
				$("vocZoneDescription").style['display'] = 'none';
			}
	}
	
	var selectedVocZoneIdIndex = 0;
	setOption(0,messagesData.all, "", 'vocZoneIdSel');
	if(selectedFacility != 'All' && selectedFacility != '')
		for(var j = 0; j < currVocZoneId.length;j++)
		{
			setOption(j+1,currVocZoneId[j].vocZoneDescription, currVocZoneId[j].vocZoneId, 'vocZoneIdSel');
			if (preSelectDropDown && currVocZoneId[j].vocZoneId == templateVocZoneId) 
					selectedVocZoneIdIndex = j+1;
		}
	
	if($('vocZoneDescription').style['display'] == '')
		$('vocZoneIdMultiSel').style['display']='';
	else if($('vocZoneIdSel').options.length == 1)
			$('vocZoneIdMultiSel').style['display']='none';
	else
		$('vocZoneIdMultiSel').style['display']='';
	
	vocZoneIdO.selectedIndex = selectedVocZoneIdIndex;
	
	vocZoneIdChanged();
}

function vocZoneIdChanged() {	
	var vocZoneIdSelO = document.getElementById('vocZoneIdSel');

	if($('vocZoneDescription').style['display'] == 'none')	{
		$('vocZoneId').value = vocZoneIdSelO.value;
		$('vocZoneDescription').value = vocZoneIdSelO.options[vocZoneIdSelO.selectedIndex].text;
		$('vocZoneDescription').style['display']='none';
		$('vocZoneIdSel').style['display']='';
		clearElements('vocZoneId');
		createElement('vocZoneId',vocZoneIdSelO.value);
	}
	
	var off = $('applicationDesc');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('application');
		$('applicationSel').style['display'] = '';
	}
	
	if(callServ)
		storageAreaCall();
}

function setVocZoneIdId(href,text,id) {
	var optionName = new Option(text, id, false, false)
	var vocZoneIdIdO = document.getElementById("vocZoneIdIdSel");
	vocZoneIdIdO[href] = optionName;
}

function showOverFlamCtrlZnLimitChanged() {
	var fieldVal = $('templateOverFlamCtrlZnLimit');	
	if (fieldVal.value == 'Y') {
		$('overFlamCtrlZnLimit').checked = true
		$('overFlamCtrlZnLmtPercent').disabled = false;
		$('overFlamCtrlZnLmtPercent').value = $v('templateOverFlamCtrlZnLmtPercent');
	} else {
		$('overFlamCtrlZnLimit').checked = false
		$('overFlamCtrlZnLmtPercent').disabled = true;
	}	
}

function overFlamCtrlZnLimitChanged() {
	var field = $('overFlamCtrlZnLimit');
	if (field.checked == true) {
		$('overFlamCtrlZnLmtPercent').disabled = false;
	} else {
		$('overFlamCtrlZnLmtPercent').value = '';
		$('overFlamCtrlZnLmtPercent').disabled = true;
	}	
}

function validateOverFlamCtrlZnLmtPercent() {
	var val = $v('overFlamCtrlZnLmtPercent');
	if(val != '' && (!isFloat(val,false) || val < 0 || val > 100 ))
	{
		alert(messagesData.entervalidinteger);
		$('overFlamCtrlZnLmtPercent').value = '';
		return false;
	}
}
