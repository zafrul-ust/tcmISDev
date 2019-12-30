var callServ = true;
var allowMyFacilities = false;  //if page allow My Facilities then that page has to override this flag
var children = new Array();
var selectedInputIndex = -1;
var maxInputIndex = 1;
var isFacilityChange = false;

function getInputField(fieldName, index) {
	if(index == null)
		index = selectedInputIndex;
	
	return $("input[" + index + "]." + fieldName);
}

function getInputFieldValue(fieldName, index) {
	if(index == null)
		index = selectedInputIndex;
	var value = "";
	var field = getInputField(fieldName, index);
	if (field) {
		value = field.value;
	}
	return value.trim();
}


function showFacilityOptions(selectedCompany) {
	
	var facilityArray = altFacilityId[selectedCompany];
	var facilityNameArray = altFacilityName[selectedCompany];

	var selectedFacilityIndex = 0;
	var defaultFacility = myDefaultFacilities[selectedCompany];
	
	if(facilityArray != null && facilityArray.length > 0) {
		var count = 0;
        if(facilityArray.length > 1 && allowMyFacilities) {
	   	    setOption(count++,messagesData.myFacilities,"", "facilityId");
		}
        var defaultFacility;
		if (myDefaultFacilities != null && myDefaultFacilities.length > 0) 
			defaultFacility = myDefaultFacilities[selectedCompany];
			
		for (var i=0; i < facilityArray.length; i++) {
			setOption(i+count,facilityNameArray[i],facilityArray[i], "facilityId");
			//set default facility
			if (defaultFacility != null && facilityArray[i] == defaultFacility) 
				selectedFacilityIndex = i+count;
		}
	}else {
		  setOption(0,messagesData.all,"", "facilityId");
	}
	
    $("facilityId").selectedIndex = selectedFacilityIndex;
	facilityChanged();
}

// only used by multiselect.jsp
function createElement(which,id)
{
	var input = document.createElement("input");
	input.setAttribute("type", "hidden");
	input.setAttribute("id", which + id);	
	var span = $(which + 'Span');
	span.appendChild(input);
}

function clearElements(which,index)
{
	var span;
	
	if(index == null )
		span = $(which + 'Span');
	else
		span = getInputField(which + 'Span',index);
		
	span.innerHTML = '';
}

function facilityChanged() {
	
	callServ = false;
	
	var selectedFacilityId = $v("facilityId");
	var selectedCompanyId = $v("companyId");
    for(var index=0; index <= maxInputIndex; index++){
		selectedInputIndex = index;
		
		var areaO = getInputField("areaIdSel");

		for (var i = areaO.length; i >= 0;i--) {
			areaO[i] = null;
		}
		
		var off = getInputField('areaIdMultiSelTxt');
		if(off.style['display'] == '')
		{
			off.style['display'] = 'none';
			clearElements('areaId');
			getInputField('areaIdSel').style['display'] = '';
		}
		
		off = getInputField('deptIdMultiSelTxt');
		if(off.style['display'] == '')
		{
			off.style['display'] = 'none';
			clearElements('deptId');
			getInputField('deptIdSel').style['display'] = '';
		}

		showWagOptions(selectedCompanyId+selectedFacilityId,index);
		showDeptOptions(selectedCompanyId+selectedFacilityId,index);
		showAreaOptions(selectedCompanyId+selectedFacilityId,index);
		
	}
	
	isFacilityChange = true;
	if(selectedFacilityId != '') {
		simpleCallToServer();
    }else {
		showWorkArea();
        showInventoryGroup(new Array(0));
    }
}

function showWagOptions(selectedFacility)
{
    var currWag = wagColl[selectedFacility];

	var wagO = getInputField("reportingEntityId");
		for (var i = wagO.length; i >= 0;i--) {
			wagO[i] = null;
		}
		
		var selectedWagIndex = 0;
		setOption(0,messagesData.all, "", "input[" + selectedInputIndex + "]." + "reportingEntityId");
		if(selectedFacility != '') {
			for(var j = 0; j < currWag.length;j++)
				setOption(j+1,currWag[j].wagName, currWag[j].wagId, "input[" + selectedInputIndex + "]." + "reportingEntityId");
        }
        wagO.selectedIndex = selectedWagIndex;
	    workAreaGroupChanged(selectedInputIndex);
}

function workAreaGroupChanged(index) {   
	selectedInputIndex = index;

	if(callServ)
		simpleCallToServer();
}

function showDeptOptions(selectedFacility)
{
    var currDept = deptColl[selectedFacility];
	
    var deptO = getInputField('deptIdSel');
    for (var i = deptO.length; i >= 0;i--) {
        deptO[i] = null;
    }
    var selectedDeptIndex = 0;
    setOption(0,messagesData.all, "","input[" + selectedInputIndex + "]." + 'deptIdSel');
    if(selectedFacility != '') {
        for(var j = 0; j < currDept.length;j++)
            setOption(j+1,currDept[j].deptName, currDept[j].deptId, "input[" + selectedInputIndex + "]." + 'deptIdSel');

    }
    if(getInputField('deptIdMultiSelTxt').style['display'] == '' && typeof(hideMultiSel) == 'undefined')
        getInputField('deptMultiSel').style['display']='';
    else if(getInputField('deptIdSel').options.length == 1)
        getInputField('deptMultiSel').style['display']='none';
    else if(typeof(hideMultiSel) == 'undefined')
        getInputField('deptMultiSel').style['display']='';

    deptO.selectedIndex = selectedDeptIndex;

    deptChanged(selectedInputIndex);
	
}

function deptChanged(index)
{
	selectedInputIndex = index;
	
	var deptO = getInputField('deptIdSel');

	if(getInputField('deptIdMultiSelTxt').style['display'] == 'none')
	{
		getInputField('deptId').value = deptO.value;
		getInputField('deptIdMultiSelTxt').value = '';
		getInputField('deptIdMultiSelTxt').style['display']='none';
		getInputField('deptIdSel').style['display']='';
	}

	if(callServ)
		simpleCallToServer();
}

function showAreaOptions(selectedFacility) {
	var areaArray = altAreaId[selectedFacility];
	var areaNameArray = altAreaName[selectedFacility];

		var selectedAreaIdIndex = 0;
		if (areaArray != null) {
			var count = 0;
			setArea(count++,messagesData.all,"");
			
			for (var i=0; i < areaArray.length; i++) {
				setArea(i+count,areaNameArray[i],areaArray[i]);
			}
	
		}else 
			setArea(0,messagesData.all,"");
		
		if(getInputField('areaIdMultiSelTxt').style['display'] == '' && typeof(hideMultiSel) == 'undefined')
			getInputField('areaMultiSel').style['display']='';
		else if(getInputField('areaIdSel').options.length == 1)
			getInputField('areaMultiSel').style['display']='none';
		else if (typeof(hideMultiSel) == 'undefined')
			getInputField('areaMultiSel').style['display']='';
		
		getInputField("areaIdSel").selectedIndex = selectedAreaIdIndex;
		areaChanged(selectedInputIndex);
	
}

function setArea(href,text,id) {
	var optionName = new Option(text, id, false, false)
	var areaO = getInputField("areaIdSel");
	areaO[href] = optionName;
}

function areaChanged(index) {
	selectedInputIndex = index;
	
	var areaCallServ = false;
	if(callServ)
		{
			areaCallServ = callServ;
			callServ = false;
		}
	
	var areaIdSel = getInputField("areaIdSel");
	if(getInputField('areaIdMultiSelTxt').style['display'] == 'none')
	{
		getInputField('areaId').value = areaIdSel.value;
		getInputField('areaIdMultiSelTxt').value = '';
		getInputField('areaIdMultiSelTxt').style['display']='none';
	}
	
	var off = getInputField('buildingIdMultiSelTxt');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('buildingId');
		getInputField('buildingIdSel').style['display'] = '';
	}

	var buildingIdO = getInputField("buildingIdSel");
	for (var i = buildingIdO.length; i >= 0;i--) {
		buildingIdO[i] = null;
	}
		
	var aOptions = areaIdSel.options;
	var aCount = 0;
	for(var j = 0; j < aOptions.length;j++)
		if(aOptions[j].selected)
			++aCount;
	
	if(getInputField('areaIdMultiSelTxt').style['display'] == 'none')
	{
		if(aCount==1)
			showBuildingOptions($v("companyId")+$v("facilityId")+getInputFieldValue("areaIdSel"));
		else
			showBuildingOptions($v("companyId")+$v("facilityId")+'All');
	}
	else
		showBuildingOptions('#^%&*!$@');
	
	if(areaCallServ)
		{
			areaCallServ = false;
			simpleCallToServer();
		}
}

function showBuildingOptions(selectedFacility) {
	var buildingIdArray = altBuildingId[selectedFacility];
	var buildingNameArray = altBuildingName[selectedFacility];

		var selectedBuildingIdIndex = 0;
		if (buildingIdArray != null) {
			var count = 0;
			setBuilding(count++,messagesData.all,"");
	
			for (var i=0; i < buildingIdArray.length; i++) {
				setBuilding(i+count,buildingNameArray[i],buildingIdArray[i]);
				//selected building
				/*if ($v("templateBuildingId") != null) {
					if ($v("templateBuildingId").length > 0) {
						if (buildingIdArray[i] == $v("templateBuildingId")) {
							selectedBuildingIdIndex = i+count;
						}
					}
				} //end of if selected building is not null*/
			}
	
		}else
			setBuilding(0,messagesData.all,"");
		
		if(getInputField('buildingIdMultiSelTxt').style['display'] == '' && typeof(hideMultiSel) == 'undefined')
			getInputField('buildingMultiSel').style['display']='';
		else if(getInputField('buildingIdSel').options.length == 1)
			getInputField('buildingMultiSel').style['display']='none';
		else if (typeof(hideMultiSel) == 'undefined')
			getInputField('buildingMultiSel').style['display']='';
		
		getInputField("buildingIdSel").selectedIndex = selectedBuildingIdIndex;
	    buildingChanged(selectedInputIndex);

}

function setBuilding(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var buildingIdO = getInputField("buildingIdSel");
  buildingIdO[href] = optionName;
}

function buildingChanged(index) {
	selectedInputIndex = index;
	
	var buildingIdSel = getInputField("buildingIdSel");
	
	if(getInputField('buildingIdMultiSelTxt').style['display'] == 'none')
	{
		getInputField('buildingId').value =  buildingIdSel.value;
		getInputField('buildingIdMultiSelTxt').value = '';
		getInputField('buildingIdMultiSelTxt').style['display']='none';
	}

	if(callServ)
		simpleCallToServer();
}

function popMultiSel(dropDownId)
{
	 children[children.length] = openWinGeneric("adhocinventoryreport.do?uAction=multiSelect&dropDownId="+dropDownId,"edit_field_list","800","500","yes");
	 showTransitWin();
}

function getWhich(which) {
	return which.substr(9);
}

function multiSelRet(retArr,ele)
{
	selectedInputIndex = ele.substr(6,1);
	if(retArr.length < 2)
		{
			if(getWhich(ele) == 'areaId')
			{
				getInputField('areaIdMultiSelTxt').style['display'] = 'none';
				//$('buildingFloorRoom').style['display'] = '';
			    var areaIdSel = getInputField("areaIdSel");
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
				areaChanged(selectedInputIndex);
			}
			else if(getWhich(ele) == 'deptId')
			{
				getInputField('deptIdMultiSelTxt').style['display'] = 'none';
			    var deptIdSel = getInputField("deptIdSel");
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
				deptChanged(selectedInputIndex);
			}
			else if(getWhich(ele) == 'buildingId')
			{
				var index = which.substr(10);
				getInputField('buildingIdMultiSelTxt').style['display'] = 'none';
			    var buildingIdSel = getInputField("buildingIdSel");
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
			buildingChanged(selectedInputIndex);
			}
			
			
		}
	else{
		if(getWhich(ele) == 'areaId')
		{
			//$('buildingFloorRoom').style['display'] = 'none';
			getInputField('areaIdSel').style['display'] = 'none';
			var areaIdMultiSelTxt = getInputField('areaIdMultiSelTxt');
			areaIdMultiSelTxt.style['display'] = '';
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
			areaIdMultiSelTxt.value = areaDisplay;
			areaIdMultiSelTxt.title = areaTooltipDisplay;
			getInputField('areaId').value = areaIds;
		    areaChanged(selectedInputIndex);
		}
		else if(getWhich(ele) == 'deptId')
		{
			getInputField('deptIdSel').style['display'] = 'none';
			var deptIdMultiSelTxt = getInputField('deptIdMultiSelTxt');
			deptIdMultiSelTxt.style['display'] = '';
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
			deptIdMultiSelTxt.value = deptDisplay;
			deptIdMultiSelTxt.title = deptTooltipDisplay;
			getInputField('deptId').value = deptIds;
			deptChanged(selectedInputIndex);
		}
		else if(getWhich(ele) == 'buildingId')
		{
			getInputField('buildingIdSel').style['display'] = 'none';
			var buildingIdMultiSelTxt = getInputField('buildingIdMultiSelTxt');
			buildingIdMultiSelTxt.style['display'] = '';
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
			buildingIdMultiSelTxt.value = buildingDisplay;
			buildingIdMultiSelTxt.title = buildingTooltipDisplay;
			getInputField('buildingId').value = buildingIds;
			buildingChanged(selectedInputIndex);
		}
	}
}

function showCompanyOptions() {
	
	var companyArray = altCompanyId;
	var companyNameArray = altCompanyName;

	var selectedCompanyIndex = 0;
	if(companyArray != null ) {
		var count = 0;

		for (var i=0; i < companyArray.length; i++) {
			setOption(i+count,companyNameArray[i],companyArray[i], "companyId");
		}
	
		if(companyArray.length <= 1){
			// hide company drop down 
			var off = $('company');
			off.style['display'] = 'none';
		}
	}
	
    $("companyId").selectedIndex = selectedCompanyIndex;
	companyChanged();
}

function companyChanged() {
	callServ = false;
	
	var selectedCompany = $v("companyId");
	var off = $('facilityId');

	// clear facility drop down
	var facilityO = document.getElementById("facilityId");

	for (var i = facilityO.length; i >= 0;i--) {
		facilityO[i] = null;
	}
	
	showFacilityOptions(selectedCompany);
}

var dhxWins = null;
//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDialogWin")) {
			dhxWins.window("transitDialogWin").setModal(false);
			dhxWins.window("transitDialogWin").hide();
		}
	}
}

function showTransitWin(message) {
	if (message != null && message.length > 0) {
		document.getElementById("transitLabel").innerHTML = message;
	}else {
		document.getElementById("transitLabel").innerHTML = messagesData.pleasewait;
	}

	var transitDialogWin = document.getElementById("transitDialogWin");
	transitDialogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("transitDialogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDialogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDialogWin");
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
		dhxWins.window("transitDialogWin").show();
	}
}

function myOnLoad() {
	// load drop downs
	showCompanyOptions();
}

function validate(){
	if(getInputFieldValue('applicationId',0) == ''){
		alert(messagesData.select);
		return false;
    }else if(getInputFieldValue('applicationId',1) == '' && $v("inventoryGroup") == ''){
		alert(messagesData.selectWorkAreaOrInventoryGroup);
		return false;
    }else if (getInputFieldValue('applicationId',0) == getInputFieldValue('applicationId',1)) {
        alert(messagesData.differentFromWorkArea);
		return false;    
    }
	
	return true;
}

function search() {
	if(validate()){
		showPleaseWait();
	
		document.genericForm.target='resultFrame';
		$("uAction").value = 'search';
		$("startSearchTime").value = new Date().getTime();
		document.genericForm.submit();
	}
}

function generateExcel() {
	$("uAction").value = 'createExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_workAreaStockTransfer','650','600','yes');
	document.genericForm.target='_workAreaStockTransfer';
	window.setTimeout("document.genericForm.submit();",500);
}

function simpleCallToServer() {
    var loc = "simplecalltoserver.do?calledFrom=workAreaStockTransfer&companyId="+encodeURIComponent($v("companyId"))+"&status=A";
    if (isFacilityChange)
        loc += "&facilityChanged=Y";
    else
        loc += "&facilityChanged=N";
    if($v("facilityId") != '')
		loc += "&facilityId="+encodeURIComponent($v("facilityId"));
	if(getInputFieldValue('areaId') != '')
		loc += "&areaId="+encodeURIComponent(getInputFieldValue('areaId'));
	if(getInputFieldValue('buildingId') != '')
		loc += "&buildingId="+encodeURIComponent(getInputFieldValue('buildingId'));
	if(getInputFieldValue("reportingEntityId") != '')
		loc += "&reportingEntityId="+encodeURIComponent(getInputFieldValue("reportingEntityId"));
	if(getInputFieldValue("deptId") != '')
		loc += "&deptId="+encodeURIComponent(getInputFieldValue("deptId"));

	callToServer(loc);
}

function showData(inventoryGroupArray,workAreaArray) {
    if (isFacilityChange) {
        showWorkArea(workAreaArray);
        showInventory(inventoryGroupArray);
    }else {
        showWorkArea(workAreaArray);
    }
}

function showInventory(inventoryGroupArray) {
    //first clear current inventory group
	var inventoryGroup0 = $("inventoryGroup");
	for (var i = inventoryGroup0.length; i >= 0;i--) {
		inventoryGroup0[i] = null;
	}
    //set data
	if(inventoryGroupArray != null && inventoryGroupArray.rows.length > 0) {
		var count = 0;
	   	setOption(count++,messagesData.selectInventoryGroup,"", "inventoryGroup");
		for (var i=0; i < inventoryGroupArray.rows.length; i++) {
			setOption(i+count,inventoryGroupArray.rows[i].desc,inventoryGroupArray.rows[i].id, "inventoryGroup");
		}
	}else {
		  setOption(0,messagesData.selectInventoryGroup,"", "inventoryGroup");
	}
}

function inventoryGroupChanged() {
    getInputField('applicationId',1).selectedIndex = 0;
}

function showWorkArea(workAreaArray)
{
	// if called when facility changed, as indicated by selectedIndex = -1, set the for loop to load multiple drop downs
	// otherwise the loop only executes once for the selectedIndex
	var indexCount = selectedInputIndex;
	if(isFacilityChange){
		indexCount = maxInputIndex;
		selectedInputIndex = 0;
		// toggle flag
		isFacilityChange = false;
	}

    for(; selectedInputIndex <= indexCount; selectedInputIndex++){
        var workAreaO = getInputField("applicationId");

	    if (workAreaO != null) {
	        for ( var i = workAreaO.length; i >= 0; i--) {
	            workAreaO[i] = null;
	        }
	    }

		 if (workAreaArray != null) {
			 var i = 0;
             if (selectedInputIndex == 1) {
                 //always add please select if it's to work area drop down
                 setOption(i++, messagesData.select, "", "input[" + selectedInputIndex + "]." + "applicationId");
             }else {
                 if (workAreaArray.rows.length == 0 || workAreaArray.rows.length > 1) {
                      setOption(i++, messagesData.select, "", "input[" + selectedInputIndex + "]." + "applicationId");
                 }
             }

             if (workAreaArray.rows.length == 1) {
				  setOption(0, workAreaArray.rows[0].desc, workAreaArray.rows[0].id, "input[" + selectedInputIndex + "]." + "applicationId");
			 }
			 else {
				  for (; i <= workAreaArray.rows.length; i++) {
						setOption(i, workAreaArray.rows[i-1].desc, workAreaArray.rows[i-1].id, "input[" + selectedInputIndex + "]." + "applicationId");
				  }
			 }
		 }else
			setOption(0, messagesData.select, "", "input[" + selectedInputIndex + "]." + "applicationId");

		 callServ = true;
	}
}

function workAreaChanged(index) {
    //if to work area changed then set inventory group to select
    if (index == 1) {
        $("inventoryGroup").selectedIndex = 0;
    }
}