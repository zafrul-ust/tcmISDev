var defaultWAGdesc = "";
var facilityDesc="";
var children = new Array();
var chargeNumberSetup = false;
var selectedRowId;

function myResultOnLoad() {
 	if ($v("totalLines") > 0) {
 		defaultWAGdesc = parent.$v("lastSearchWAG");
 		facilityDesc = parent.$v("lastSearchFacility");
		parent.$("infoDiv").innerHTML = messagesData.cabinetsForWorkarea+": "+defaultWAGdesc;
		parent.$("infoDiv").style["display"] = "";
		if (defaultWAGdesc == 'All') {
			defaultWAGdesc = "";
		}
	}
}


function toggleColor(rowId, colId) {
	if (!$("active"+rowId).checked) {
		mygrid.haasSetRowClass(rowId, "grid_lightgray");
	}
	else {
		var rowIndex=mygrid.getRowIndex(rowId);
		mygrid.haasSetRowClass(rowId, rowIndex % 2 == 0 ? "ev_haas" : "odd_haas");
	}
}

function validateForm() {
	for ( var i = 0; i < mygrid.getRowsNum(); i++) {
		var rowid = mygrid.getRowId(i);
		var touched = gridCellValue(mygrid,rowid, "touched");
		if (touched) {
			var description = gridCellValue(mygrid, rowid, "applicationDesc");
			var appId = gridCellValue(mygrid,rowid, "applicationId");
			//if (description == "" && appId > 0) {
			if (description == "") {
				mygrid.selectRowById(rowid);
				alert(messagesData.descRequired);
				return false;
			}
			var method = gridCellValue(mygrid,rowid, "orderingMethod");
			if (method != "MANUAL") {
				if (gridCellValue(mygrid,rowid, "deliveryPoint").length  == 0) {
					mygrid.selectRowById(rowid);
					alert(messagesData.deliveryRequired);
					return false;
				}
				if (gridCellValue(mygrid,rowid, "locationId").length  == 0) {
					mygrid.selectRowById(rowid);
					alert(messagesData.dockRequired);
					return false;
				}
			}
			var pullWithinDaysToExpiration = gridCellValue(mygrid,rowid, "pullWithinDaysToExpiration");
			var daysBetweenScan = gridCellValue(mygrid,rowid, "daysBetweenScan");
			
			if(pullWithinDaysToExpiration != "" && !isInteger(pullWithinDaysToExpiration))
				{
					alert(messagesData.integerErr.replace(/[{]0[}]/g, messagesData.pullWithinDaysToExpiration));
					return false;
				}
			else if (daysBetweenScan != "" && !isInteger(daysBetweenScan))
				{
					alert(messagesData.integerErr.replace(/[{]0[}]/g, messagesData.daysBetweenScan));
					return false;
				}
		}
	}
	return true;
}
function doUpdate() {
	var index = mygrid.getRowsNum();
	for(var i = 0; i < index; i++)
	{
		
		var rowId = mygrid.getRowId(i);
		var active = cellValue(rowId, "touched");
		if(active == "true")
		{
			var applicationDesc = cellValue(rowId, "applicationDesc");
			var  newstr = applicationDesc.replace(/^\s*/, "").replace(/\s*$/, ""); 
			newstr = newstr.replace(/\s{2,}/g, " "); 
			gridCell(mygrid, rowId, "applicationDesc").setValue(newstr);
		}
	}
	if( !validateForm() ) return false;
	$('uAction').value = 'update';

	parent.showPleaseWait();
	parent.$("startSearchTime").value = new Date().getTime();
	mygrid.parentFormOnSubmit(); // prepare grid for data sending
	document.cabinetSetupForm.submit();
	return false;
}

function addNewRow() {
	var newId = (mygrid.getRowsNum() + 1) + "";
	var newData = new Array();
	var cntr = 0;
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = true;	// Active
	newData[cntr++] = '';	// applicationDesc
	newData[cntr++] = ''; // deptId
	newData[cntr++] = ''; // deptName
	newData[cntr++] = '<input name="searchForDept' + newId + '" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="lookupDept(' + newId + ');">';
	newData[cntr++] = false;	// DropShip
	newData[cntr++] = false;	// Offsite
	newData[cntr++] = defaultWAGdesc;	// WorkAreaGroupDesc
	newData[cntr++] = '<input name="searchForWAG' + newId + '" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="lookupWorkAreaGroup(' + newId + ');">';
	newData[cntr++] = 'MANUAL'; //orderingMethod
	newData[cntr++] = '0'; // "pullWithinDaysToExpiration",
	newData[cntr++] = ''; // "daysBetweenScan",
	newData[cntr++] = ''; // areaDesc
	newData[cntr++] = ''; // buildingDesc
	newData[cntr++] = ''; // floorDesc
	newData[cntr++] = ''; // roomDesc
	newData[cntr++] = ''; // interior/exterior
	newData[cntr++] = '<input name="searchForBuilding' + newId + '" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="lookupBuilding(' + newId + ');">';
	if(showHmrbFeatures) {
		newData[cntr++] = 'N'; // CompassPointPermission
		newData[cntr++] = ''; // CompassPoint
		newData[cntr++] = 'N'; // locColumnPermission
		newData[cntr++] = ''; // columnId
	}
	if(showLocSection) {
		newData[cntr++] = ''; // locSection
	}
	if(showEmissionPoint) {
		newData[cntr++] = ''; // EmissionPoint
	}
	//if(showReportUsage) {
		newData[cntr++] = true; // Report Usage
	//}
	if(showControlledColInWADefiniton) {
		newData[cntr++] = false; //specificUseApprovalRequired
	}
	//if(showReportInventory) {
		newData[cntr++] = true; // Report Inventory
	//}
	newData[cntr++] = defaultInventoryGroup; // Inventory Group
	if (inventoryGroup.length == 1) { // Extra column if only one OR no permission
		newData[cntr++] = inventoryGroup[0].text;   //inventoryGroupDisplay
	}
	newData[cntr++] = '';  // stockingAccountSysId

	if(showChargeTypeDefault == 'Y'){
		newData[cntr++] = ''; // default Charge type
	}
	
	newData[cntr++] = defaultDockId;    //locationId
	if (locationId.length == 1) { // Extra column if only one
		newData[cntr++] = locationId[0].text;   //locationIdDisplay
	}
	newData[cntr++] = false;    //dockFixed
	newData[cntr++] = '';       //deliveryPointDesc
	newData[cntr++] = false;    //deliveryPointFixed
	newData[cntr++] = '<input name="searchForDeliveryPoint' + newId + '" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="lookupDeliveryPoint(' + newId + ');">';
	if(isUseCodeRequired == 'Y'){
		newData[cntr++] = '';   //useCodeName
		newData[cntr++] = '<input name="searchApprovalCode' + newId + '" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="lookupApprovalCode(' + newId + ');">'; // Lookup
		newData[cntr++] = '';   //useCodeIdString
	}
	if (showEmissionPoints) {
		newData[cntr++] = messagesData.lookupForMoreDetails;
		newData[cntr++] = '<input name="searchEmissionPoints' + newId + '" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="lookupEmissionPoints(' + newId + ');">'; // Lookup
	}
	if(isAllowSplitKitsColView == 'Y') {
		newData[cntr++] = false;// allowSplitKits
	}
    if(ShowHETColumns) {
		newData[cntr++] = false; // Daily Usage Logging
		newData[cntr++] = false; // Allow Separable Mixture
		newData[cntr++] = false; // HET Multiple Building Usage
	}
	if (showIncludeExpired) {
		newData[cntr++] = ''; // include expired material    
	}
	newData[cntr++] = ''; // locationDetail
	newData[cntr++] = ''; // contactName
	newData[cntr++] = ''; // contactPhone
	newData[cntr++] = ''; // contactEmail
	if(showAlternateContact) {
		newData[cntr++] = ''; // Alternate2Name
		newData[cntr++] = ''; // Alternate2Phone
		newData[cntr++] = ''; // Alternate2Email
	}
	newData[cntr++] = ''; // customerCabinetId
	newData[cntr++] = defaultFacilityId;    //facilityId
	newData[cntr++] = defaultCompanyId;     //companyId
	newData[cntr++] = '-1'; // -1 for applicationId to indicate new record
	newData[cntr++] = ''; // deliveryPoint
	newData[cntr++] = defaultWorkAreaGroup; // reportingEntityId
	newData[cntr++] = ''; // areaId
	newData[cntr++] = ''; // buildingId
    newData[cntr++] = ''; // floorId
    newData[cntr++] = ''; // roomId
	newData[cntr++] = ''; // application
	if(showFlammabilityControlZone) {
		newData[cntr++] = ''; // FlammabilityControlZoneId
		newData[cntr++] = ''; // FlammabilityControlZoneDesc
		newData[cntr++] = '<input name="searchForFlamCtrlZn' + newId + '" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="lookupFlamCtrlZn(' + newId + ');">'; // FlammabilityControlZoneLookup
	}
	if(showVocZone) {
		newData[cntr++] = ''; // VocZoneId
		newData[cntr++] = ''; // VocZoneDescription
		newData[cntr++] = '<input name="searchForVocZone' + newId + '" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="lookupVocZone(' + newId + ');">'; // VocZoneLookup
	}
	newData[cntr++] = true;
	              
	mygrid.addRow(newId,newData);
	mygrid.selectRowById(newId);
}

function rowSelected(rowId) {
	gridCell(mygrid, rowId, "touched").setValue(true);
	selectedRowId = rowId;
}

function valueChanged(rowId) {
	gridCell(mygrid, rowId, "touched").setValue(true);
}

function showRightClickMenu (rowId, colId) {
	// Only allow them to edit directed charges on EXISTING work areas
	selectedRowId = rowId;
	if (gridCellValue(mygrid, rowId, "applicationId") > 0) {
		toggleContextMenu('rightClickMenu');
	}
	else {
		toggleContextMenu('contextMenu');
	}
}

function showHistory() {
	var url = "clientcabinetsetupresults.do?uAction=history"
    + "&companyId=" + gridCellValue(mygrid, selectedRowId, "companyId")          
    + "&facilityId=" + URLEncode(gridCellValue(mygrid, selectedRowId, "facilityId"))
	+ "&applicationId=" + gridCellValue(mygrid, selectedRowId, "applicationId")
	+ "&areaId=" + gridCellValue(mygrid, selectedRowId, "areaId")
	+ "&buildingId=" + gridCellValue(mygrid, selectedRowId, "buildingId")
	+ "&deptId=" + gridCellValue(mygrid, selectedRowId, "deptId");
		  
	itemWindow = openWinGeneric(url, "UsageLogChangeHistory", "1000", "400",
	              "yes", "50", "50", "no");
	//parent.children[parent.children.length] = itemWindow;
}

function lookupWorkAreaGroup(rowId) {
	parent.showTransitWin(formatMessage(messagesData.waitingforinput, messagesData.workareagroup)+"...");
	var loc = "manageworkareagroups.do?uAction=search&facilityId="+URLEncode(defaultFacilityId)+"&rowId="+rowId + "&workAreaGroup=" + cellValue(rowId, "workAreaGroupDesc");	
	parent.children[children.length] = openWinGeneric(loc,"ManageWorkAreaGroups1","600","500","yes","50","50","20","20","no");	
}

function workAreaGroupCallback(rowId, workAreaGroupId, workAreaGroupDesc) {
	gridCell(mygrid, rowId, "reportingEntityId").setValue(workAreaGroupId);
	gridCell(mygrid, rowId, "workAreaGroupDesc").setValue(workAreaGroupDesc);
	var companyId = gridCellValue(mygrid, rowId, "companyId"); 
	parent.addWorkAreaGroupIfNew(companyId, defaultFacilityId, workAreaGroupId, workAreaGroupDesc);
	parent.closeTransitWin();
}

function getDockName(dockId) {
	for (var i = 0; i < locationId.length; i++) {
		if (dockId == locationId[i].value) {
			return locationId[i].text;
		}
	}
	return dockId;
}

function clearDeliveryPoint(rowId) {
	deliveryPointCallback(rowId, "", "");
}

function lookupDeliveryPoint(rowId) {
	parent.showTransitWin(formatMessage(messagesData.waitingforinput, messagesData.deliverypoint)+"...");
	var dockId = gridCellValue(mygrid,rowId,"locationId");
	var loc = "managedeliverypoints.do?uAction=search&facilityId="+URLEncode(defaultFacilityId);
	loc += "&facilityDesc="+facilityDesc;
	loc += "&rowId="+rowId;
	loc += "&locationId="+dockId;
	loc += "&dock="+getDockName(dockId);
	loc += "&curDeliveryPoint="+gridCellValue(mygrid,rowId,"deliveryPoint");
	parent.children[children.length] = openWinGeneric(loc,"ManageDeliveryPoints","1200","500","yes","50","50","20","20","no");
}

function deliveryPointCallback(rowId, selectedId, selectedDesc) {
	gridCell(mygrid, rowId, "deliveryPoint").setValue(selectedId);
	gridCell(mygrid, rowId, "deliveryPointDesc").setValue(selectedDesc);
	parent.closeTransitWin();
}

function lookupBuilding(rowId) {
	parent.showTransitWin(formatMessage(messagesData.waitingforinput, messagesData.area)+"...");
	var loc = "managebuildingrooms.do?uAction=search&facilityId="+URLEncode(defaultFacilityId);
	loc += "&facilityDesc="+facilityDesc;
	loc += "&rowId="+rowId;
	loc += "&areaId="+gridCellValue(mygrid, rowId, "areaId");
	loc += "&buildingId="+gridCellValue(mygrid, rowId, "buildingId");
	loc += "&floorId="+gridCellValue(mygrid, rowId, "floorId");
	loc += "&roomId="+gridCellValue(mygrid, rowId, "roomId");
	parent.children[children.length] = openWinGeneric(loc,"ManageBuildingRooms","860","700","yes","50","50","20","20","no");
}

function buildingCallback(rowId, buildingId, buildingDesc, roomId, roomDesc, areaId, areaDesc, interior, floor) {
	gridCell(mygrid, rowId, "areaId").setValue(areaId);
	gridCell(mygrid, rowId, "areaDesc").setValue(areaDesc);
	gridCell(mygrid, rowId, "buildingId").setValue(buildingId);
	gridCell(mygrid, rowId, "buildingDesc").setValue(buildingDesc);
	gridCell(mygrid, rowId, "roomId").setValue(roomId);
	gridCell(mygrid, rowId, "roomDesc").setValue(roomDesc);
	gridCell(mygrid, rowId, "interior").setValue(interior);
	gridCell(mygrid, rowId, "floor").setValue(floor);
	
	if (showHmrbFeatures) {
		var compassPoint = "" + gridCellValue(mygrid, rowId, "compassPoint");
		var locColumn = "" + gridCellValue(mygrid, rowId, "locColumn");
		gridCell(mygrid, rowId, "compassPointPermission").setValue('Y');
		gridCell(mygrid, rowId, "locColumnPermission").setValue('Y');
		gridCell(mygrid, rowId, "compassPoint").setValue(compassPoint);
		gridCell(mygrid, rowId, "locColumn").setValue(locColumn);
	}
	parent.closeTransitWin();
}

function setWorkAreaDirectedChargeNumbers(chargeType,chargeNumbers,poNumber,releaseNumber,userEnteredChargeNumber,ignoreNullChargeNumber) {
	var url = "clientcabinetmanagementmain.do?uAction=setWorkAreaCabinetPartDirectedCharge";
	url += "&companyId="+defaultCompanyId;
	url += "&accountSysId="+curAccountSysId;
	url += "&facilityId="+URLEncode(defaultFacilityId);
	url += "&poNumber="+poNumber;
	url += "&poLine="+releaseNumber;
	url += "&chargeType="+chargeType;
	url += "&chargeNumbers="+chargeNumbers;
	url += "&userEnteredChargeNumber="+userEnteredChargeNumber;
    url += "&ignoreNullChargeNumber="+ignoreNullChargeNumber;
    url += "&sourcePage=clientCabinetManagement";
	url += "&searchText=workArea";   // I am using this field to keep track of where this is called from
	url += '&applicationId='+gridCellValue(mygrid, mygrid.getSelectedRowId(),"application");

	callToServer(url);
} // end of method


function setChargeNumberPoData(chargeType,chargeNumber,poNumber,releaseNumber,userEnteredChargeNumber,ignoreNullChargeNumber) {
	if (chargeNumber.length == 0 && poNumber.length == 0) {
		parent.closeTransitWin();
	}else {
		setWorkAreaDirectedChargeNumbers(chargeType,chargeNumber,poNumber,releaseNumber,userEnteredChargeNumber,ignoreNullChargeNumber);
	}
}

var directedChargeAssignment = false;
var curAccountSysId = "";
function editDirectedCharge(accountSysId) {
	parent.showTransitWin(formatMessage(messagesData.waitingforinput, messagesData.directedCharge)+"...");
	curAccountSysId = accountSysId;

	var url = 'clientcabinetmanagementmain.do?uAction=editCabinetDirectedCharge';
	url += '&companyId='+defaultCompanyId;
	url += '&accountSysId='+accountSysId;
	url += '&facilityId='+URLEncode(defaultFacilityId);
	url += '&facilityName='+facilityDesc;
	url += '&sourcePage=clientCabinetManagement';
	url += '&searchText=workArea';   // I am using this field to keep track of where this is called from
	url += '&applicationId='+gridCellValue(mygrid, mygrid.getSelectedRowId(),"application");
    if (directedChargeAssignment) {
        url += '&canEditData=Y';
    }else {
         url += '&canEditData=N';
    }

    openWinGeneric(url,"_editWorkAreaCabinetPartDirectedCharge",480,380,'no' );
}


function deleteDirectedCharge(chargeType) {
	var url = "clientcabinetmanagementmain.do?uAction=deleteDirectedCharge";
	url += "&companyId="+defaultCompanyId;
	url += "&facilityId="+URLEncode(defaultFacilityId);
    url += "&chargeType="+chargeType;
    url += "&sourcePage=clientCabinetManagement";
    url += "&searchText=workArea";   // I am using this field to keep track of where this is called from
	url += "&applicationId="+gridCellValue(mygrid, mygrid.getSelectedRowId(),"application");
	callToServer(url);
} 

function validateChargeNumber(chargeNumbersOk,tmpCallEditDirectedChargeFrom,tmpAccountSysId,tmpChargeType,tmpChargeNumbers,tmpPo,tmpPoLine,tmpUserEnteredChargeNumber) {
	parent.closeTransitWin();
	if (chargeNumbersOk != 'OK') {
		curAccountSysId = tmpAccountSysId;
		if (chargeNumberSetup) {
			var answer = confirm(messagesData.invalidChargeNumberAddTolist);
			if (answer) {
				addWorkAreaDirectedChargeNumbers(tmpChargeType,tmpChargeNumbers,tmpPo,tmpPoLine,tmpUserEnteredChargeNumber);
			}else {
				editDirectedCharge(tmpAccountSysId);
			}
		}else {
			alert(messagesData.invalidChargeNumbers);
			editDirectedCharge(tmpAccountSysId);
		}
	}
} 

function addWorkAreaDirectedChargeNumbers(chargeType,chargeNumbers,poNumber,releaseNumber,userEnteredChargeNumber) {
	parent.showTransitWin(formatMessage(messagesData.waitingforinput, messagesData.directedCharge)+"...");

	var url = "clientcabinetmanagementmain.do?uAction=addWorkAreaCabinetPartDirectedCharge";
	url += "&companyId="+defaultCompanyId;
	url += "&accountSysId="+curAccountSysId;
	url += "&facilityId="+URLEncode(defaultFacilityId);
	url += "&poNumber="+poNumber;
	url += "&poLine="+releaseNumber;
	url += "&chargeType="+chargeType;
	url += "&chargeNumbers="+chargeNumbers;
	url += "&userEnteredChargeNumber="+userEnteredChargeNumber;
	url += "&sourcePage=clientCabinetManagement";
	url += "&searchText=workArea";   // I am using this field to keep track of where this is called from
	url += '&applicationId='+gridCellValue(mygrid, mygrid.getSelectedRowId(),"application");

	callToServer(url);
} 

function lookupApprovalCode(rowId) {
	parent.showTransitWin(formatMessage(messagesData.waitingforinput, messagesData.approvalCode)+"...");
	var loc = "manageusecode.do?uAction=search&facilityId="+URLEncode(defaultFacilityId);
	loc += "&companyId="+gridCellValue(mygrid,rowId,"companyId"); 
	loc += "&rowId="+rowId;
	loc += "&useCodeIdString="+gridCellValue(mygrid,rowId,"useCodeIdString");
	parent.children[children.length] = openWinGeneric(loc,"ManageApprovalCode","515","350","yes","50","50","20","20","no");
}

function approvalCodeCallBack(rowId,id,name) {
	gridCell(mygrid, rowId, "useCodeName").setValue(name);
	gridCell(mygrid, rowId, "useCodeIdString").setValue(id);
}

function lookupDept(rowId) {
	parent.showTransitWin(formatMessage(messagesData.waitingforinput, messagesData.department)+"...");
	var loc = "managedepartments.do?uAction=search"
	loc += "&deptId=" + gridCellValue(mygrid, rowId, "deptId");
	loc += "&companyId="+gridCellValue(mygrid,rowId,"companyId"); 
	loc += "&rowId="+rowId;
	parent.children[children.length] = openWinGeneric(loc,"ManageDepartment","515","350","yes","50","50","20","20","no");
}

function departmentCallBack(rowId,id,name) {
	gridCell(mygrid, rowId, "deptId").setValue(id);
	gridCell(mygrid, rowId, "deptName").setValue(name);
	parent.closeTransitWin();
}

function orderingMethodChanged() {
    var tmpOrderingMethod = gridCellValue(mygrid, mygrid.getSelectedRowId(), "orderingMethod");
    if ((tmpOrderingMethod == 'STOCKLEVEL' || tmpOrderingMethod == 'BOTH')) {
        if (gridCellValue(mygrid, mygrid.getSelectedRowId(), "applicationId") > 0) {
            parent.showTransitWin(messagesData.pleasewait);
            var url = "clientcabinetmanagementmain.do?uAction=checkDirectedChargeForWorkArea";
            url += "&companyId="+defaultCompanyId;
            url += "&facilityId="+URLEncode(defaultFacilityId);
            url += "&searchText=workArea";   // I am using this field to keep track of where this is called from
            url += "&sourcePage=clientCabinetManagement";
            url += "&applicationId="+gridCellValue(mygrid, mygrid.getSelectedRowId(),"application");
            callToServer(url);
        }else {
            alert("After clicking on Update, remember to go back and setup charge number for this work area.");
        }
    }
}

function checkDirectedCharge(chargeNumbersFound) {
    parent.closeTransitWin();
	if (chargeNumbersFound == 'Directed charge is empty') {
        alert("Charge number has not been setup for this work area.  Please do so before clicking on Update.")
    }
}

function lookupVocZone(rowId) {
	parent.showTransitWin(formatMessage(messagesData.waitingforinput, messagesData.voczone)+"...");
	var loc = "managevoczones.do?uAction=search"
	loc += "&vocZoneId=" + gridCellValue(mygrid, rowId, "vocZoneId");
	loc += "&companyId="+gridCellValue(mygrid,rowId,"companyId"); 
	loc += "&facilityId="+ URLEncode(gridCellValue(mygrid,rowId,"facilityId")); 
	loc += "&rowId="+rowId;
	parent.children[children.length] = openWinGeneric(loc,"ManageVocZone","515","350","yes","50","50","20","20","no");
}

function voczoneCallBack(rowId,id,name) {
	gridCell(mygrid, rowId, "vocZoneId").setValue(id);
	gridCell(mygrid, rowId, "vocZoneDescription").setValue(name);
	parent.closeTransitWin();
}

function lookupFlamCtrlZn(rowId) {
	parent.showTransitWin(formatMessage(messagesData.waitingforinput, messagesData.voczone)+"...");
	var loc = "manageflamctrlzones.do?uAction=search"
	loc += "&flammabilityControlZoneId=" + gridCellValue(mygrid, rowId, "flammabilityControlZoneId");
	loc += "&companyId="+gridCellValue(mygrid,rowId,"companyId"); 
	loc += "&facilityId="+ URLEncode(gridCellValue(mygrid,rowId,"facilityId")); 
	loc += "&rowId="+rowId;
	parent.children[children.length] = openWinGeneric(loc,"ManageFlammabilityControlZone","750","400","yes","50","50","20","20","no");
}

function flamCtrlZnCallBack(rowId,id,name) {
	gridCell(mygrid, rowId, "flammabilityControlZoneId").setValue(id);
	gridCell(mygrid, rowId, "flammabilityControlZoneDesc").setValue(name);
	parent.closeTransitWin();
}

function lookupEmissionPoints(rowId) {
	var loc = "emissionpointmanagement.do?uAction=search"
			+ "&facilityId="+ URLEncode(gridCellValue(mygrid, rowId, "facilityId"))
			+ "&application="+URLEncode(gridCellValue(mygrid, rowId, "application"));
	parent.children[children.length] = openWinGeneric(loc,"ManageEmissionPoint","900","400","yes","50","50","20","20","no");
	parent.showTransitWin();
}