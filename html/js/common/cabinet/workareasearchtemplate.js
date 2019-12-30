var callServ = true;
var allowMyFacilities = false;  //if page allow My Facilities then that page has to override this flag
var children = new Array();
var uploadPartLevelPermission = false;

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
	
	var facilityO = document.getElementById("facilityId");
	var areaO = document.getElementById("areaIdSel");
	var selectedFacility = facilityO.value;

	for (var i = areaO.length; i >= 0;i--) {
		areaO[i] = null;
	}
	
	var off = $('areaIdMultiSelTxt');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('areaId');
		 $('areaIdSel').style['display'] = '';
	}
	
	off = $('deptIdMultiSelTxt');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('deptId');
		$('deptIdSel').style['display'] = '';
	}
	
	off = $('applicationIdMultiSelTxt');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('applicationId');
		$('applicationIdSel').style['display'] = '';
	}

    var selectedCompany = $v("companyId");
    showWagOptions(selectedCompany+selectedFacility);
	showDeptOptions(selectedCompany+selectedFacility);
	showAreaOptions(selectedCompany+selectedFacility);
	
	if(facilityO.value != '')
		storageAreaCall();
	else
		showWorkArea();
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
	if(selectedFacility != '')
		for(var j = 0; j < currWag.length;j++)
			{
				setOption(j+1,currWag[j].wagName, currWag[j].wagId, "reportingEntityId");
				/*if ($v("templatereportingEntityId") != null && $v("templatereportingEntityId").length > 0) {
						if (currWag[j].wagId == $v("templatereportingEntityId")) {
							selectedWagIndex = j+1;
						}
					}*/
			}
	wagO.selectedIndex = selectedWagIndex;
    workAreaGroupChanged();
}

function workAreaGroupChanged() {   
	var off = $('applicationIdMultiSelTxt');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('applicationId');
		$('applicationIdSel').style['display'] = '';
	}
	
	if(callServ)
		storageAreaCall();
}

function showDeptOptions(selectedFacility)
{
	var currDept = deptColl[selectedFacility];
	var deptO = document.getElementById('deptIdSel');
	for (var i = deptO.length; i >= 0;i--) {
		deptO[i] = null;
	}
	var selectedDeptIndex = 0;
	setOption(0,messagesData.all, "", 'deptIdSel');
	if(selectedFacility != '')
		for(var j = 0; j < currDept.length;j++)
		{
			setOption(j+1,currDept[j].deptName, currDept[j].deptId, 'deptIdSel');
			/*if ($v("templateDeptId") != null && $v("templateDeptId").length > 0) {
				if (currDept[j].deptId== $v("templateDeptId")) {
					selectedDeptIndex = j+1;
				}
			}*/
		}

	if($('deptIdMultiSelTxt').style['display'] == '' && typeof(hideMultiSel) == 'undefined')
		$('deptMultiSel').style['display']='';
	else if($('deptIdSel').options.length == 1)
			$('deptMultiSel').style['display']='none';
	else if(typeof(hideMultiSel) == 'undefined')
		$('deptMultiSel').style['display']='';
	
	deptO.selectedIndex = selectedDeptIndex;
	
	deptChanged();
}

function deptChanged()
{
	var deptO = document.getElementById('deptIdSel');

	if($('deptIdMultiSelTxt').style['display'] == 'none')
	{
		$('deptId').value = deptO.value;
		$('deptIdMultiSelTxt').value = '';
		$('deptIdMultiSelTxt').style['display']='none';
		$('deptIdSel').style['display']='';
	}
	
	var off = $('applicationIdMultiSelTxt');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('applicationId');
		$('applicationIdSel').style['display'] = '';
	}
	
	if(callServ)
		storageAreaCall();
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
			//selected reporting Entity
			/*if ($v("templateAreaId") != null) {
				if ($v("templateAreaId").length > 0) {
					if (areaArray[i] == $v("templateAreaId")) {
						selectedAreaIdIndex = i+count;
					}
				}
			} //end of if selected reporting Entity is not null*/
		}

	}else 
		setArea(0,messagesData.all,"");
	
	if($('areaIdMultiSelTxt').style['display'] == '' && typeof(hideMultiSel) == 'undefined')
		$('areaMultiSel').style['display']='';
	else if($('areaIdSel').options.length == 1)
			$('areaMultiSel').style['display']='none';
	else if (typeof(hideMultiSel) == 'undefined')
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
	if($('areaIdMultiSelTxt').style['display'] == 'none')
	{
		$('areaId').value = areaIdSel.value;
		$('areaIdMultiSelTxt').value = '';
		$('areaIdMultiSelTxt').style['display']='none';
		//$('buildingFloorRoom').style['display']='';
	}	
	
	var off = $('buildingIdMultiSelTxt');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('buildingId');
		 $('buildingIdSel').style['display'] = '';
	}
	
	off = $('applicationIdMultiSelTxt');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('applicationId');
		$('applicationIdSel').style['display'] = '';
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
	
	if($('areaIdMultiSelTxt').style['display'] == 'none')
	{
		if(aCount==1)
			showBuildingOptions($v("companyId")+$v("facilityId")+$v("areaIdSel"));
		else
			showBuildingOptions($v("companyId")+$v("facilityId")+'All');
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
	
	if($('buildingIdMultiSelTxt').style['display'] == '' && typeof(hideMultiSel) == 'undefined')
		$('buildingMultiSel').style['display']='';
	else if($('buildingIdSel').options.length == 1)
		$('buildingMultiSel').style['display']='none';
	else if (typeof(hideMultiSel) == 'undefined')
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

	var buildingIdSel = $("buildingIdSel");
	
	if($('buildingIdMultiSelTxt').style['display'] == 'none')
	{
		$('buildingId').value =  buildingIdSel.value;
		$('buildingIdMultiSelTxt').value = '';
		$('buildingIdMultiSelTxt').style['display']='none';
	}
	
	var off = $('applicationIdMultiSelTxt');
	if(off.style['display'] == '')
	{
		off.style['display'] = 'none';
		clearElements('applicationId');
		$('applicationIdSel').style['display'] = '';
	}
	
	if(callServ)
		storageAreaCall();
}

function storageAreaCall()
{
	var loc = "storagearea.do?reportType=waStkMgmt&companyId="+$v("companyId")+"&status=A";
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
	callToServer(loc);
}

function showWorkArea(workAreaArray)
{
    var workAreaO = document.getElementById("applicationIdSel");

    if (workAreaO != null) {
        for ( var i = workAreaO.length; i >= 0; i--) {
            workAreaO[i] = null;
        }
    }

	 if (workAreaArray != null) {
		 var i = 0;
		 if (workAreaArray.rows.length == 0 || workAreaArray.rows.length > 1) {
			  setOption(i++, messagesData.all, "", "applicationIdSel");
		 }

		 if (workAreaArray.rows.length == 1) {
			  setOption(0, workAreaArray.rows[0].desc, workAreaArray.rows[0].id, "applicationIdSel");
		 }
		 else {
			  for (; i <= workAreaArray.rows.length; i++) {
					setOption(i, workAreaArray.rows[i - 1].desc, workAreaArray.rows[i - 1].id, "applicationIdSel");
			  }
		 }

		 }else
			setOption(0, messagesData.all, "", "applicationIdSel");

	 workAreaChanged();

	 callServ = true;
}

function workAreaChanged()
{
	if($('applicationIdMultiSelTxt').style['display'] == 'none')
	{
		$('applicationIdMultiSelTxt').value = '';
		$('applicationId').value = $v('applicationIdSel');
		$('applicationIdSel').style['display']='';
		if($('applicationIdSel').options.length == 1)
			$('applicationIdMultiSel').style['display']='none';
		else if (typeof(hideMultiSel) == 'undefined')
			$('applicationIdMultiSel').style['display']='';				
	}
	else
		{	
			if(typeof(hideMultiSel) == 'undefined')
				$('applicationIdMultiSel').style['display']='';
			clearElements('applicationId');
		}
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
					$('areaIdMultiSelTxt').style['display'] = 'none';
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
				$('deptIdMultiSelTxt').style['display'] = 'none';
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
					$('buildingIdMultiSelTxt').style['display'] = 'none';
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
			else
			{
				$('applicationIdMultiSelTxt').style['display'] = 'none';
			    var applicationIdSel = $("applicationIdSel");
			    applicationIdSel.style['display'] = '';
			    if(retArr.length > 0)
			    {
				    var selected = retArr[0].split('#^%&*!$@')[1];
				    $('applicationId').value = selected;
					var options = applicationIdSel.options;
					for(var i = 0; i < options.length; i++)
						if(options[i].value == selected)
						{
							applicationIdSel.selectedIndex = i;
							break;
						}
			    }
			    else
			    	{
			    		applicationIdSel.selectedIndex = 0;
					    $('applicationId').value = applicationIdSel.value;
			    	}
			}
		}
	else{
		if(which == 'areaId')
		{
			//$('buildingFloorRoom').style['display'] = 'none';
			$('areaIdSel').style['display'] = 'none';
			var areaIdMultiSelTxt = $('areaIdMultiSelTxt');
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
		    $('areaId').value = areaIds;
		    areaChanged();
		    // callServ = false;
			//showBuildingOptions('#^%&*!$@');
			//storageAreaCall();
		}
		else if(which == 'deptId')
		{
			$('deptIdSel').style['display'] = 'none';
			var deptIdMultiSelTxt = $('deptIdMultiSelTxt');
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
		    $('deptId').value = deptIds;
			deptChanged();
		}
		else if(which == 'buildingId')
			{
				$('buildingIdSel').style['display'] = 'none';
				var buildingIdMultiSelTxt = $('buildingIdMultiSelTxt');
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
		else
			{
				$('applicationIdSel').style['display'] = 'none';
				var applicationIdMultiSelTxt = $('applicationIdMultiSelTxt');
				applicationIdMultiSelTxt.style['display'] = '';
				var applicationDisplay = '';
				var applicationIds = '';
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
                        applicationIds += selectedArr[1];
						if(i != retArr.length - 1)
							applicationIds += "|";
					}
                applicationIdMultiSelTxt.value = applicationDisplay;
				applicationIdMultiSelTxt.title = applicationTooltipDisplay;
			    $('applicationId').value = applicationIds;
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
	
	if (uploadPartLevelPermission){
		checkCompanyPermission(selectedCompany);
	}
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