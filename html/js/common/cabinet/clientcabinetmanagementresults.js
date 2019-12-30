var myGrid;

var selectedRowId = null;

// Set this to be false if you don't want the grid width to resize based on window size.

var resizeGridWithWindow = true;

var chargeNumberSetup = false;
var directedChargeAssignment = false;


function resultOnLoad() {
//	parent.closeTransitWin();
	var totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		parent.document.getElementById("showlegendLink").style["display"] = "";
		if (!showUpdateLinks) {
			parent.document.getElementById("updateResultLink").style["display"] = "none";
			parent.document.getElementById("addNonManagedMaterialLink").style["display"] = "none";
			parent.document.getElementById("addPartLink").style["display"] = "none";
            parent.canChangeOwnership = false;
        }else {
            if (inventoryPartMgmt || nonStockedPermissionForWorkArea) {
                parent.document.getElementById("updateResultLink").style["display"] = "";
            }else {
                parent.document.getElementById("updateResultLink").style["display"] = "none";    
            }
            if (inventoryPartMgmt) {
                parent.document.getElementById("addPartLink").style["display"] = "";
                parent.canChangeOwnership = showChangeOwnership;
            }else {
                parent.document.getElementById("addPartLink").style["display"] = "none";
                parent.canChangeOwnership = false;
            }
            if(nonStockedPermissionForWorkArea) {
				parent.document.getElementById("addNonManagedMaterialLink").style["display"] = "";
            }else {
				parent.document.getElementById("addNonManagedMaterialLink").style["display"] = "none";
            }
        }

		document.getElementById("cabinetPartLevelViewBean").style["display"] = "";
		// build the grid for display
		doInitGrid();
	}else {
		document.getElementById("cabinetPartLevelViewBean").style["display"] = "none";
		parent.document.getElementById("showlegendLink").style["display"] = "none";
		parent.document.getElementById("addPartLink").style["display"] = "none";
        parent.canChangeOwnership = false;
        parent.document.getElementById("updateResultLink").style["display"] = "none";
		parent.document.getElementById("addNonManagedMaterialLink").style["display"] = "none";
	}

	/* This displays our standard footer message */
	displayGridSearchDuration();

	/*
	 * It is important to call this after all the divs have been turned on or off.
	 */
	setResultFrameSize();

	//I need to do it here because I am not doing the normal link
	if (totalLines == 0) {
		if (showUpdateLinks) {
            parent.document.getElementById("mainUpdateLinks").style["display"] = "";
			parent.document.getElementById("updateResultLink").style["display"] = "none";
			parent.document.getElementById("showlegendLink").style["display"] = "none";
            if (inventoryPartMgmt) {
                parent.document.getElementById("addPartLink").style["display"] = "";
                parent.canChangeOwnership = showChangeOwnership;
            }else {
                parent.document.getElementById("addPartLink").style["display"] = "none";
                parent.canChangeOwnership = false;
            }
            if(nonStockedPermissionForWorkArea) {
				parent.document.getElementById("addNonManagedMaterialLink").style["display"] = "";
            }else {
				parent.document.getElementById("addNonManagedMaterialLink").style["display"] = "none";
            }
		}else {
			parent.document.getElementById("mainUpdateLinks").style["display"] = "none";
			parent.document.getElementById("updateResultLink").style["display"] = "none";
            parent.document.getElementById("showlegendLink").style["display"] = "none";
            parent.document.getElementById("addPartLink").style["display"] = "none";
            parent.canChangeOwnership = false;
            parent.document.getElementById("addNonManagedMaterialLink").style["display"] = "none";
		}
	}

}

function doInitGrid() {
	myGrid = new dhtmlXGridObject('cabinetPartLevelViewBean');
	initGridWithConfig(myGrid,config,true,true);

	//make sure this is set for onAfterHaasGridRendered to work correctly
	myGrid._haas_bg_render_enabled = true;
	myGrid.enableSmartRendering(true);
	myGrid._haas_row_span = true;
	myGrid._haas_row_span_map = lineMap;
	myGrid._haas_row_span_class_map = lineMap3;
	myGrid._haas_row_span_cols = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,27,28,29,30,31,32,33,34,35,36,37,38,39,61,65];

	if (lineMap2) {
		myGrid._haas_row_span_lvl2 = true;
		myGrid._haas_row_span_lvl2_map = lineMap2;
		myGrid._haas_row_span_lvl2_cols = [14,15,16,17,18,19,20,21,22,23,24,25,26];
	}
   	myGrid.attachEvent("onAfterHaasRenderRow", setInactiveRowColor);
	if( typeof( jsonMainData ) != 'undefined' ) {
		myGrid.parse(jsonMainData,"json");
	}
	
	myGrid.attachEvent("onRowSelect",doOnRowSelected);
	myGrid.attachEvent("onRightClick", selectRightclick);
	//myGrid.attachEvent("onAfterHaasRenderRowSpan", calculateBinPartStatus);
	
	myGrid.attachEvent("onAfterHaasRenderRow", toggleSubstrate);
}

function toggleSubstrate(rowId) {
  try {
	  if($v("showDefault") == 'Y') {
		var rowIndex = myGrid.getRowIndex(rowId);
		if (cellValue(rowId, "defaultPartType") == "" || cellValue(rowId, "defaultPartType") == "F") {
				$("defaultSubstrateCode" + rowId).disabled = true;
				$("defaultSubstrateCode" + rowId).selectedIndex = 0;
		}
		else {
				$("defaultSubstrateCode" + rowId).disabled = false;
		}
	  }	
  } catch(ex) {}
}

function doOnRowSelected(rowId, cellInd) {
	selectedRowId = rowId;
}
//cabinet bin section
function selectRightclick(rowId, cellInd) {
  if(cellValue(rowId,"nonManaged") == 'Y') {
  		var haasMaterialIds = cellValue(rowId,"haasMaterialIdString");
		var haasMaterialIdArray = haasMaterialIds.split(";");
		var mitems = new Array();
		if (haasMaterialIdArray.length == 1) {
			var tmpId = haasMaterialIdArray[0];
			var tmpIdArray = tmpId.split(":");
			if (tmpIdArray[0].trim().length > 0) {
				mitems[mitems.length ] = "text="+messagesData.viewMsds+": "+tmpIdArray[0].trim()+";url=javascript:viewMSDS('"+tmpIdArray[1].trim()+"');";
			}
		}else {
			for(i = 0; i < haasMaterialIdArray.length; i++ ) {
				var tmpId = haasMaterialIdArray[i];
				var tmpIdArray = tmpId.split(":");
				if (tmpIdArray[0].trim().length > 0) {
					mitems[mitems.length ] = "text="+tmpIdArray[0].trim()+";url=javascript:viewComponentMSDS('"+tmpIdArray[1].trim()+"');";
				}
			}
		}
		
		if (mitems.length > 1) {
			replaceMenu('showMsdsSub',mitems);
			mm_insertItem('showMsdsSub',mitems.length,'text='+messagesData.viewNonmanagedMatlHist+';url=javascript:viewNonmanagedHist();');
			toggleContextMenu('itemRightClickMenu');

		}else if (mitems.length == 1) {
			replaceMenu('showMsds',mitems);
			mm_insertItem('showMsdsSub',mitems.length,'text='+messagesData.viewNonmanagedMatlHist+';url=javascript:viewNonmanagedHist();');
			toggleContextMenu('showMsds');	
			
		}
		else {
			replaceMenu('showEmpty');
			mm_insertItem('showEmpty',2,'text='+messagesData.viewNonmanagedMatlHist+';url=javascript:viewNonmanagedHist();');
			toggleContextMenu("showEmpty");
		}
			
  }else {
	if (cellInd < myGrid.getColIndexById("countType")) {
		var aitems = new Array();
		if (inventoryPartMgmt) {
			aitems[aitems.length] = "text="+messagesData.addPart+";url=javascript:addPartFromRightMouseClick();";
		}
		aitems[aitems.length] = "text="+messagesData.generateLabelsForWorkArea+";url=javascript:createWorkAreaLabels('generatecablabels');";
		aitems[aitems.length] = "text="+messagesData.generateBinLabelForWorkArea+";url=javascript:createWorkAreaLabels('generatecabbinlabels');";

        if(showChangeOwnership && cellValue(rowId,"countType") != 'PART' && cellValue(rowId,"countType") != 'RECEIPT')
			aitems[aitems.length] = "text="+messagesData.changeOwnership+";url=javascript:changeOwnership();";
        aitems[aitems.length] = "text="+messagesData.viewPartHistory+";url=javascript:viewPartHistory();";
        if(cellValue(rowId,"countType") == 'LEVEL')
        	aitems[aitems.length] = "text="+messagesData.setCabinetCountValues+";url=javascript:setCabinetCountInterpolation();";
        replaceMenu('workAreaPartRightClickMenu',aitems);
		toggleContextMenu('workAreaPartRightClickMenu');
	}else if (cellInd < myGrid.getColIndexById("itemId")) {
		var aitems = new Array();
		if (inventoryPartMgmt && "NotCounted" != cellValue(rowId,"countType")) {
			aitems[aitems.length] = "text="+messagesData.setPartLevel+";url=javascript:changeMinMaxCabinet();";
		}
		if (directedChargeAssignment) {
			aitems[aitems.length] = "text="+messagesData.editPartDirectedCharge+";url=javascript:editPartDirectedCharge();";
		}else {
            if ($v("companyId") == 'LOCKHEED') {
                aitems[aitems.length] = "text="+messagesData.viewPartDirectedCharge+";url=javascript:editPartDirectedCharge();";
            }
        }
		if(showChangeOwnership && cellValue(rowId,"countType") != 'PART' && cellValue(rowId,"countType") != 'RECEIPT')
			aitems[aitems.length] = "text="+messagesData.changeOwnership+";url=javascript:changeOwnership();";
		if(inventoryPartMgmt)
			aitems[aitems.length] = "text="+messagesData.changeBinName+";url=javascript:changeBinName();";
        aitems[aitems.length] = "text="+messagesData.viewPartHistory+";url=javascript:viewPartHistory();";
        if(cellValue(rowId,"countType") == 'LEVEL')
        	aitems[aitems.length] = "text="+messagesData.setCabinetCountValues+";url=javascript:setCabinetCountInterpolation();";
        if (aitems.length > 0) {
			replaceMenu('workAreaPartRightClickMenu',aitems);
			toggleContextMenu('workAreaPartRightClickMenu');
		}else {
			replaceMenu('showEmpty');
			toggleContextMenu("showEmpty");
		}
	}else {
		var haasMaterialIds = cellValue(rowId,"haasMaterialIdString");
		var haasMaterialIdArray = haasMaterialIds.split(";");
		var mitems = new Array();		
		hasMsdsNo = false;
		if(haasMaterialIdArray[0].indexOf(':') != -1)
			hasMsdsNo = true;	
		
		if (haasMaterialIdArray.length == 1) {
			if(hasMsdsNo)
			{
				var tmpId = haasMaterialIdArray[0];
				var tmpIdArray = tmpId.split(":");
				if (tmpIdArray[0].trim().length > 0) {
					mitems[mitems.length ] = "text="+messagesData.viewMsds+": "+tmpIdArray[0].trim()+";url=javascript:viewMSDS('"+tmpIdArray[1].trim()+"');";
				}
			}
			else if (haasMaterialIdArray[0].trim().length > 0) {
				mitems[mitems.length ] = "text="+haasMaterialIdArray[0].trim()+";url=javascript:viewMSDS('"+haasMaterialIdArray[0].trim()+"');";
			}
				
		}else {
			for(i = 0; i < haasMaterialIdArray.length; i++ ) {
				if(hasMsdsNo)
					{
					    var tmpId = haasMaterialIdArray[i];
						var tmpIdArray = tmpId.split(":");
						if (tmpIdArray[0].trim().length > 0) {
							mitems[mitems.length ] = "text="+tmpIdArray[0].trim()+";url=javascript:viewComponentMSDS('"+tmpIdArray[1].trim()+"');";
						}
					}
				else if (haasMaterialIdArray[i].trim().length > 0) {
							mitems[mitems.length ] = "text="+haasMaterialIdArray[i].trim()+";url=javascript:viewComponentMSDS('"+haasMaterialIdArray[i].trim()+"');";
						}
			}

		}
        if(showChangeOwnership && cellValue(rowId,"countType") != 'PART' && cellValue(rowId,"countType") != 'RECEIPT')
			mitems[mitems.length] = "text="+messagesData.changeOwnership+";url=javascript:changeOwnership();";
        if (mitems.length > 1) {
			replaceMenu('showMsdsSub',mitems);
			toggleContextMenu('itemRightClickMenu');
		}else if (mitems.length == 1) {
			replaceMenu('showMsds',mitems);
			toggleContextMenu('showMsds');	
		}else {
			replaceMenu('showEmpty');
			toggleContextMenu("showEmpty");
		}
	}
  }
}

/*
function calculateBinPartStatus(rowId) {
	tmpStartingRowIndex = myGrid.haasGetRowSpanStart(rowId);
	tmpEndingRowIndex = myGrid.haasGetRowSpanEndIndex(rowId);
	//don't need to do anything if it's not a spanned row
	if (tmpEndingRowIndex*1 - tmpStartingRowIndex*1 > 1) {
		var tmpBinHasAnActivePart = binHasAnActivePart(rowId);
		for (var i = tmpStartingRowIndex; i < tmpEndingRowIndex; i++) {
			if (tmpBinHasAnActivePart) {
				if (cellValue(i+1,"binPartStatus") == 'A') {
					//child records will not have lineMap2 data
					if (lineMap2[i] != null && lineMap2[i] != 'undefined') {
					   //setBinPartStatusPermission(i+1,i,'Y');
						$("showBinPartStatus"+(i+1)).disabled = false;
					}
				}else {
					//child records will not have lineMap2 data
					if (lineMap2[i] != null && lineMap2[i] != 'undefined') {
						//setBinPartStatusPermission(i+1,i,'N');
						$("showBinPartStatus"+(i+1)).disabled = true;
					}
				}
			}else {
				//child records will not have lineMap2 data
				if (lineMap2[i] != null && lineMap2[i] != 'undefined') {
					//setBinPartStatusPermission(i+1,i,'Y');
					$("showBinPartStatus"+(i+1)).disabled = false;
				}
			}
		}
	}
}
*/

//this method check screen data
function binHasAnActivePart(rowId) {
	var tmpBinHasAnActivePart = false;
	try {
		tmpStartingRowIndex = myGrid.haasGetRowSpanStart(rowId);
		tmpEndingRowIndex = myGrid.haasGetRowSpanEndIndex(rowId);
		//don't need to do anything if it's not a spanned row
		if (tmpEndingRowIndex*1 - tmpStartingRowIndex*1 > 1) {
			for (var i = tmpStartingRowIndex; i < tmpEndingRowIndex; i++) {
				if (cellValue(i+1,"binPartStatus") == 'A') {
					tmpBinHasAnActivePart = true;
					break;
				}
			}
		}
	}catch (err) {}
	return tmpBinHasAnActivePart;
}

//the reason for this method is that you can change to screen data but has not click 'update'
//until he click update he can't add new part to the bin with an active part
function binHasAnOriginalActivePart(rowId) {
	var tmpBinHasAnActivePart = false;
	try {
		tmpStartingRowIndex = myGrid.haasGetRowSpanStart(rowId);
		tmpEndingRowIndex = myGrid.haasGetRowSpanEndIndex(rowId);
		for (var i = tmpStartingRowIndex; i < tmpEndingRowIndex; i++) {
			if (cellValue(i+1,"oldBinPartStatus") == 'A') {
				tmpBinHasAnActivePart = true;
				break;
			}
		}
	}catch (err) {}
	return tmpBinHasAnActivePart;
}

function changeBinPartStatus(rowId,cellInd) {
	tmpStartingRowIndex = myGrid.haasGetRowSpanStartLvl2(rowId);
	tmpEndingRowIndex = myGrid.haasGetRowSpanEndIndexLvl2(rowId);
	if($("showBinPartStatus"+rowId).checked == true) {
		for (var i = tmpStartingRowIndex; i < tmpEndingRowIndex; i++) {
			myGrid.cells(i+1,myGrid.getColIndexById("binPartStatus")).setValue('A');
			myGrid._haas_json_data.rows[myGrid.getRowIndex(i+1)].data[myGrid.getColIndexById("binPartStatus")] = 'A';
		}
		setActiveRowColor(rowId);
	}else {
		for (var i = tmpStartingRowIndex; i < tmpEndingRowIndex; i++) {
			myGrid.cells(i+1,myGrid.getColIndexById("binPartStatus")).setValue('I');
			myGrid._haas_json_data.rows[myGrid.getRowIndex(i+1)].data[myGrid.getColIndexById("binPartStatus")] = 'I';
		}
		setInactiveRowColor(rowId);
	}
	//calculateBinPartStatus(rowId);
	setChanged(rowId);
}

function setInactiveRowColor(rowId) {
	rowIndex = myGrid.getRowIndex(rowId);
	// Check JSON data because grid cell may not have been rendered yet
	if (myGrid._haas_json_data.rows[rowIndex].data[myGrid.getColIndexById("binPartStatus")] == 'I') {
		//grid black
        myGrid.haasSetColSpanStyle(rowId, 0, 38, "background-color: #747170;");
    }
	if (myGrid._haas_json_data.rows[rowIndex].data[myGrid.getColIndexById("status")] == 'O') {
		//grid black
        myGrid.haasSetColSpanStyle(rowId, 14, 26, "background-color: #747170;");
    }
}

function setActiveRowColor(rowId) {
	colorStyle = myGrid.rowsAr[rowId].className;
    myGrid.haasSetColSpanStyle(rowId, 0, 38, colorStyle);
}

function addPartFromRightMouseClick() {
	parent.$("companyId").value = cellValue(myGrid.getSelectedRowId(),"companyId");
	parent.$("application").value = cellValue(myGrid.getSelectedRowId(),"application");
	parent.$("applicationDesc").value = cellValue(myGrid.getSelectedRowId(),"applicationDesc");
	parent.$("applicationId").value = cellValue(myGrid.getSelectedRowId(),"applicationId");    
    parent.$("binId").value = cellValue(myGrid.getSelectedRowId(),"binId");
	parent.addPart("addPartFromRightMouseClick");
}

function changeMinMaxCabinet() {
	var companyId = cellValue(myGrid.getSelectedRowId(),"companyId");
	var facilityId = cellValue(myGrid.getSelectedRowId(),"facilityId");
	var facilityName = cellValue(myGrid.getSelectedRowId(),"facilityName");
	var application = cellValue(myGrid.getSelectedRowId(),"application");
	var applicationDesc = cellValue(myGrid.getSelectedRowId(),"applicationDesc");
	var applicationId = cellValue(myGrid.getSelectedRowId(),"applicationId");
	var cabinetName = cellValue(myGrid.getSelectedRowId(),"applicationDesc");
	var binId = cellValue(myGrid.getSelectedRowId(),"binId");
	var catalogCompanyId = cellValue(myGrid.getSelectedRowId(),"catalogCompanyId");
	var catalogId = cellValue(myGrid.getSelectedRowId(),"catalogId");
	var partNumber = cellValue(myGrid.getSelectedRowId(),"catPartNo");
	var partGroupNo = cellValue(myGrid.getSelectedRowId(),"partGroupNo");
	var status = cellValue(myGrid.getSelectedRowId(),"binPartStatus");
	var itemId = cellValue(myGrid.getSelectedRowId(),"itemId");
	var inventoryGroup = cellValue(myGrid.getSelectedRowId(),"inventoryGroup");

	inventoryGroup = inventoryGroup.replace(/&/gi, "%26");
	inventoryGroup = inventoryGroup.replace(/#/gi, "%23");
	facilityId = facilityId.replace(/&amp;/gi, "%26");
	facilityId = facilityId.replace(/&/gi, "%26");
	facilityId = facilityId.replace(/#/gi, "%23");
	facilityName = facilityName.replace(/&amp;/gi, "%26");
	facilityName = facilityName.replace(/&/gi, "%26");
	facilityName = facilityName.replace(/#/gi, "%23");
	application = application.replace(/&/gi, "%26");
	application = application.replace(/#/gi, "%23");
	applicationDesc = applicationDesc.replace(/&/gi, "%26");
	applicationDesc = applicationDesc.replace(/#/gi, "%23");
	cabinetName = cabinetName.replace(/&/gi, "%26");
	cabinetName = cabinetName.replace(/#/gi, "%23");
	catalogCompanyId = catalogCompanyId.replace(/&/gi, "%26");
	catalogCompanyId = catalogCompanyId.replace(/#/gi, "%23");
	partNumber = partNumber.replace(/%/gi, "%25");
	partNumber = partNumber.replace(/&amp;/gi, "%26");
	partNumber = partNumber.replace(/&/gi, "%26");
	partNumber = partNumber.replace(/#/gi, "%23");
	
    partNumber = partNumber.replace(/\+/gi, "%2b");
	var levelChangeUrl = "clientcabinetlevel.do?uAction=viewMinMax"
			+ "&companyId="+companyId
			+ "&facilityId="+facilityId
			+ "&facilityName="+facilityName
			+ "&application="+application
			+ "&applicationDesc="+applicationDesc
			+ "&cabinetId="+applicationId
			+ "&cabinetName="+cabinetName
			+ "&catalogCompanyId="+catalogCompanyId
	      + "&catalogId="+catalogId
			+ "&catPartNo="+partNumber
			+ "&partGroupNo="+partGroupNo
			+ "&itemId="+itemId
		   + "&inventoryGroup="+inventoryGroup
		   + "&binId="+binId
			+ "&status="+status;
	openWinGeneric(levelChangeUrl, "getchang_elevelscreen", "900", "600","yes", "50", "50");
	parent.showTransitWin();
}

function updateGrid() {
	if($v("sourcePage") == 'cabinetManagement')
    {	
    	parent.showPleaseWait();
		var now = new Date();
		parent.$("startSearchTime").value = now.getTime();
		$('uAction').value = 'update';
	
		//prepare grid for data sending
		myGrid.parentFormOnSubmit();
        document.cabinetManagementForm.target = 'resultFrame';
        document.cabinetManagementForm.submit();
    }
  	else {
  		if(validateGrid()) {
  			parent.showPleaseWait();
			var now = new Date();
			parent.$("startSearchTime").value = now.getTime();
			$('uAction').value = 'update';

			//prepare grid for data sending
			myGrid.parentFormOnSubmit();
			document.clientCabinetManagementForm.submit();
		}
	}
}

function validateGrid() {
	var rowsNum = myGrid.getRowsNum();
	for (var p = 1 ; p < (rowsNum+1) ; p ++)
  	{	
		if(!checkInputValues(p)) return false;
  	}
    return true;
}

function checkInputValues(rowId) {
    var errorMsg = "";
    var invalidDataMsg = "";
    var defaultFieldsError = "";

    if('Y' == $v("showDefault") && "Y" == cellValue(rowId,"defaultPartTypePermission")) {
        if(cellValue(rowId,"defaultPartType") == 'N' && cellValue(rowId,"defaultSubstrateCode") == 'SELECT')
            defaultFieldsError = "error";
        if(cellValue(rowId,"defaultPartType") != 'SELECT' && (cellValue(rowId,"defaultPermitId") == 'SELECT' || cellValue(rowId,"defaultApplicationMethodCod") == 'SELECT'))
             defaultFieldsError = "error";
        if(cellValue(rowId,"defaultPermitId") != 'SELECT' && (cellValue(rowId,"defaultPartType") == 'SELECT' || cellValue(rowId,"defaultApplicationMethodCod") == 'SELECT'))
             defaultFieldsError = "error";
        if(cellValue(rowId,"defaultApplicationMethodCod") != 'SELECT' && (cellValue(rowId,"defaultPermitId") == 'SELECT' || cellValue(rowId,"defaultPartType") == 'SELECT'))
             defaultFieldsError = "error";
    }

    if ("Y" ==  cellValue(rowId,"avgAmountPermission")) {
        var avgAmount = cellValue(rowId,"avgAmount");
        var maxAmount = cellValue(rowId,"maxAmount");

        if(avgAmount == '') {
            errorMsg += "\n"+messagesData.averageAmount;
        }else if(avgAmount != '&nbsp;' && avgAmount != '0' && (!isFloat(avgAmount) || avgAmount <= 0*1)) {
            errorMsg += "\n"+messagesData.averageAmount;
        }

        if(maxAmount == '') {
            errorMsg += "\n"+messagesData.maxAmount;
        }else if(maxAmount != '&nbsp;' && maxAmount != '0' && (!isFloat(maxAmount) || maxAmount <= 0*1)) {
            errorMsg += "\n"+messagesData.maxAmount;
        }

        if(maxAmount != '' && maxAmount != '&nbsp;' && avgAmount != '' &&  avgAmount != '&nbsp;') {
            if(parseFloat(avgAmount) > parseFloat(maxAmount)) {
                invalidDataMsg += "\n"+ messagesData.avgGreaterMax;
            }
        }
    }

    if ("Y" ==  cellValue(rowId,"largestContainerSizePermission")) {
        var largestContainerSize = cellValue(rowId,"largestContainerSize");
        if(largestContainerSize == '') {
            errorMsg += "\n"+messagesData.largestContainerSize;
        }else if(largestContainerSize != '&nbsp;' && largestContainerSize != '0' && (!isFloat(largestContainerSize) || largestContainerSize <= 0*1)) {
            errorMsg += "\n"+messagesData.largestContainerSize;
        }
    }

    if ("Y" ==  cellValue(rowId,"sizeUnitPermission")) {
        var sizeUnit = $("sizeUnit" + rowId);
        if(sizeUnit != undefined && sizeUnit.value.length  == 0) {
            errorMsg += "\n"+messagesData.sizeUnit;
        }
    }

    if(tierIIRequired)
    {
	    if ("Y" ==  cellValue(rowId,"tierIIStoragePermission")) {
	        var tierIIStorage = $("tierIIStorage" + rowId);
	        if(tierIIStorage != undefined && tierIIStorage.value.length  == 0) {
	            errorMsg += "\n"+messagesData.tierIIStorageMethod;
	        }
	    }
	
	    if ("Y" ==  cellValue(rowId,"tierIITemperaturePermission")) {
	        var tierIITemperature = $("tierIITemperature" + rowId);
	        if(tierIITemperature != undefined && tierIITemperature.value.length  == 0) {
	            errorMsg += "\n"+messagesData.tierIITemperature;
	        }
	    }
    }

    //put error messages together
    var errorMessage = "";
    //default fields
    if (defaultFieldsError.length > 0 ) {
        errorMessage += messagesData.defaultvalusforall;
    }
    //fields with data does not match
    if (invalidDataMsg.length > 0 ) {
        errorMessage += invalidDataMsg;
    }
    //fields missing data
    if (errorMsg.length > 0 ) {
        errorMessage += "\n"+messagesData.validvalues+errorMsg;
    }

    if (errorMessage.length > 0) {
        myGrid.selectRowById(rowId, null, false, false);
        alert(errorMessage);
        return false;
    }

    return true;
} //end of method

// all same level, at least one item
function replaceMenu(menuname,menus){
	var i = mm_returnMenuItemCount(menuname);

	for(;i> 1; i-- )
		mm_deleteItem(menuname,i);
	
	if(menus != null || menus != undefined)
	{
		for( i = 0; i < menus.length; ){
			var str = menus[i];
			i++;
			mm_insertItem(menuname,i,str);
			// delete original first item.
			if( i == 1 ) mm_deleteItem(menuname,1);
		}
	}
}

function viewComponentMSDS(msds) {
	if(newMsdsViewer)
		openWinGeneric('viewmsds.do?act=msds'+
				'&materialId='+ msds +
				'&showspec=N' +
				'&spec=' + // do we need to know spec id?
				'&cl='+ cellValue(myGrid.getSelectedRowId(),"companyId") +
				'&facility=' + encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"facilityId")) +
				'&catpartno=' + encodeURIComponent( cellValue(myGrid.getSelectedRowId(),"catPartNo")  )
				,"ViewMSDS","1024","720",'yes' );
	else
		openWinGeneric('ViewMsds?act=msds'+
				'&id='+ msds +
				'&showspec=N' +
				'&spec=' + // do we need to know spec id?
				'&cl='+ cellValue(myGrid.getSelectedRowId(),"companyId") +
				'&facility=' + encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"facilityId")) +
				'&catpartno=' + encodeURIComponent( cellValue(myGrid.getSelectedRowId(),"catPartNo")  )
				,"ViewMSDS","1024","720",'yes' );
}


function viewMSDS(materialId) {
	facilityId = cellValue(myGrid.getSelectedRowId(),"facilityId");
	if($v("sourcePage") == 'cabinetManagement') {
		itemId = cellValue(myGrid.getSelectedRowId(),"itemId");
		var loc = "/tcmIS/all/ViewMsds?act=msds&cl=Internal&facility="+encodeURIComponent(facilityId)+"&itemid="+itemId+"&id="+materialId;
		openWinGeneric(loc,"ViewMsds","1024","720","yes","50","50","20","20","no"); 
	}
	else {
		companyId = cellValue(myGrid.getSelectedRowId(),"companyId");
		catPartNo = cellValue(myGrid.getSelectedRowId(),"catPartNo");
		if(newMsdsViewer)
			openWinGeneric('viewmsds.do?act=msds'+'&materialId='+materialId+
				'&showspec=N' +
				'&spec=' +
				'&cl='+companyId+
				'&facility='+encodeURIComponent(facilityId) +
				'&catpartno='+encodeURIComponent(catPartNo)
				,"ViewMSDS","1024","720",'yes' );
		else
			openWinGeneric('ViewMsds?act=msds'+'&id='+materialId+
				'&showspec=N' +
				'&spec=' +
				'&cl='+companyId+
				'&facility='+encodeURIComponent(facilityId) +
				'&catpartno='+encodeURIComponent(catPartNo)
				,"ViewMSDS","1024","720",'yes' );
	}
}

var callEditDirectedChargeFrom = "";
function editWorkAreaDirectedCharge() {
	callEditDirectedChargeFrom = "workArea";
	getAccountSysId();
}

function editCabinetDirectedCharge() {
	callEditDirectedChargeFrom = "cabinet";
	getAccountSysId();
}

function editPartDirectedCharge() {
	callEditDirectedChargeFrom = "part";
	getAccountSysId();
}

function getAccountSysId() {
	if (altAccountSysId.length > 1) {
		parent.accountSysId = altAccountSysId;
		parent.showAccountInputDialog();
	}else {
		parent.$("accountSysId").value = altAccountSysId[0].id;
		editDirectedCharge();
	}
}

function editDirectedCharge() {
	parent.showTransitWin(formatMessage(messagesData.waitingforinput, messagesData.directedCharge)+"...");
	companyId = cellValue(myGrid.getSelectedRowId(),"companyId");
	facilityId = cellValue(myGrid.getSelectedRowId(),"facilityId");
	facilityName = cellValue(myGrid.getSelectedRowId(),"facilityName");

	var url = 'clientcabinetmanagementmain.do?uAction=editCabinetDirectedCharge&companyId='+companyId+'&accountSysId='+parent.$v("accountSysId")
		       +'&facilityId='+encodeURIComponent(facilityId)+'&facilityName='+encodeURIComponent(facilityName)+'&sourcePage='+$v("sourcePage")+
				 '&searchText='+callEditDirectedChargeFrom;   //I am using this field to keep track of where this is called from
	if (callEditDirectedChargeFrom == 'cabinet') {
		url += '&applicationId='+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"application"));
	}else if (callEditDirectedChargeFrom == 'part') {
		url += '&applicationId='+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"application"))+
				 '&catalogCompanyId='+cellValue(myGrid.getSelectedRowId(),"catalogCompanyId")+
				 '&catalogId='+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"catalogId"))+
				 '&catalogAddPartGroupNo='+cellValue(myGrid.getSelectedRowId(),"partGroupNo")+
				 '&catalogAddCatPartNo='+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"catPartNo"));  //the reason I am using this is because cat_part_no is an array
	}else {
		url += '&applicationId='+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"application"));
	}
    if (directedChargeAssignment) {
        url += '&canEditData=Y';
    }else {
         url += '&canEditData=N';
    }

    openWinGeneric(url,"_editWorkAreaCabinetPartDirectedCharge",480,380,'no' );
}

function setChargeNumberPoData(chargeType,chargeNumber,poNumber,releaseNumber,userEnteredChargeNumber,ignoreNullChargeNumber) {
	if (chargeNumber.length == 0 && poNumber.length == 0) {
		parent.closeTransitWin();
	}else {
		setWorkAreaCabinetPartDirectedChargeNumbers(chargeType,chargeNumber,poNumber,releaseNumber,userEnteredChargeNumber,ignoreNullChargeNumber);
	}
}

function setWorkAreaCabinetPartDirectedChargeNumbers(chargeType,chargeNumbers,poNumber,releaseNumber,userEnteredChargeNumber,ignoreNullChargeNumber) {
	companyId = cellValue(myGrid.getSelectedRowId(),"companyId");
	facilityId = cellValue(myGrid.getSelectedRowId(),"facilityId");

	url = "clientcabinetmanagementmain.do?uAction=setWorkAreaCabinetPartDirectedCharge&companyId="+companyId+"&accountSysId="+parent.$v("accountSysId")+
			"&facilityId="+encodeURIComponent(facilityId)+"&poNumber="+encodeURIComponent(poNumber)+"&poLine="+releaseNumber+
			"&chargeType="+chargeType+"&chargeNumbers="+chargeNumbers+"&userEnteredChargeNumber="+userEnteredChargeNumber+
			"&sourcePage="+$v("sourcePage")+"&ignoreNullChargeNumber="+ignoreNullChargeNumber+
			"&searchText="+callEditDirectedChargeFrom;   //I am using this field to keep track of where this is called from
	if (callEditDirectedChargeFrom == 'cabinet') {
		url += "&applicationId="+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"application"));
	}else if (callEditDirectedChargeFrom == 'part') {
		url += "&applicationId="+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"application"))+
				 "&catalogCompanyId="+cellValue(myGrid.getSelectedRowId(),"catalogCompanyId")+
				 "&catalogId="+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"catalogId"))+
				 '&catalogAddPartGroupNo='+cellValue(myGrid.getSelectedRowId(),"partGroupNo")+
				 "&catalogAddCatPartNo="+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"catPartNo"));  //the reason I am using this is because cat_part_no is an array
	}else {
		url += "&applicationId="+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"application"));
	}

	callToServer(url);
} //end of method

function addWorkAreaCabinetPartDirectedChargeNumbers(chargeType,chargeNumbers,poNumber,releaseNumber,userEnteredChargeNumber) {
	parent.showTransitWin(formatMessage(messagesData.waitingforinput, messagesData.directedCharge)+"...");
	companyId = cellValue(myGrid.getSelectedRowId(),"companyId");
	facilityId = cellValue(myGrid.getSelectedRowId(),"facilityId");

	url = "clientcabinetmanagementmain.do?uAction=addWorkAreaCabinetPartDirectedCharge&companyId="+companyId+"&accountSysId="+parent.$v("accountSysId")+
			"&facilityId="+encodeURIComponent(facilityId)+"&poNumber="+encodeURIComponent(poNumber)+"&poLine="+releaseNumber+
			"&chargeType="+chargeType+"&chargeNumbers="+chargeNumbers+"&userEnteredChargeNumber="+userEnteredChargeNumber+
			"&sourcePage="+$v("sourcePage")+
			"&searchText="+callEditDirectedChargeFrom;   //I am using this field to keep track of where this is called from
	if (callEditDirectedChargeFrom == 'cabinet') {
		url += "&applicationId="+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"application"));
	}else if (callEditDirectedChargeFrom == 'part') {
		url += "&applicationId="+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"application"))+
				 "&catalogCompanyId="+cellValue(myGrid.getSelectedRowId(),"catalogCompanyId")+
				 "&catalogId="+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"catalogId"))+
				 '&catalogAddPartGroupNo='+cellValue(myGrid.getSelectedRowId(),"partGroupNo")+
				 "&catalogAddCatPartNo="+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"catPartNo"));  //the reaso
		// n I am using this is because cat_part_no is an array
	}else {
		url += "&applicationId="+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"application"));
	}

	callToServer(url);
} //end of method

function deleteDirectedCharge(chargeType) {
	companyId = cellValue(myGrid.getSelectedRowId(),"companyId");
	facilityId = cellValue(myGrid.getSelectedRowId(),"facilityId");

	url = "clientcabinetmanagementmain.do?uAction=deleteDirectedCharge&companyId="+companyId+"&facilityId="+encodeURIComponent(facilityId)+
		   "&searchText="+callEditDirectedChargeFrom;   //I am using this field to keep track of where this is called from
    url += "&chargeType="+chargeType;
    if (callEditDirectedChargeFrom == 'cabinet') {
		url += "&applicationId="+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"application"));
	}else if (callEditDirectedChargeFrom == 'part') {
		url += "&applicationId="+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"application"))+
				 "&catalogCompanyId="+cellValue(myGrid.getSelectedRowId(),"catalogCompanyId")+
				 "&catalogId="+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"catalogId"))+
				 '&catalogAddPartGroupNo='+cellValue(myGrid.getSelectedRowId(),"partGroupNo")+
				 "&catalogAddCatPartNo="+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"catPartNo"));  //the reason I am using this is because cat_part_no is an array
	}else {
		url += "&applicationId="+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"application"));
	}

	callToServer(url);
} //end of method

function validateChargeNumber(chargeNumbersOk,tmpCallEditDirectedChargeFrom,tmpAccountSysId,tmpChargeType,tmpChargeNumbers,tmpPo,tmpPoLine,tmpUserEnteredChargeNumber) {
	parent.closeTransitWin();
	if (chargeNumbersOk != 'OK') {
		parent.$("accountSysId").value = tmpAccountSysId;
		callEditDirectedChargeFrom = tmpCallEditDirectedChargeFrom;
		if (chargeNumberSetup) {
			var answer = confirm(messagesData.invalidChargeNumberAddTolist);
			if (answer) {
				addWorkAreaCabinetPartDirectedChargeNumbers(tmpChargeType,tmpChargeNumbers,tmpPo,tmpPoLine,tmpUserEnteredChargeNumber);
			}else {
				editDirectedCharge();
			}
		}else {
			alert(messagesData.invalidChargeNumbers);
			editDirectedCharge();
		}
	}
} //end of method

function prepLabelForm(labelForm, labelAction, userAction) {
	// Remove any previous values in the form
	if ( labelForm.hasChildNodes() ) {
		while ( labelForm.childNodes.length >= 1 ) {
			labelForm.removeChild( labelForm.firstChild );       
		} 
	}	

	labelForm.action = labelAction;
	
	var facilityId = cellValue(myGrid.getSelectedRowId(),"facilityId");
	addNewFormElement(labelForm, "UserAction", userAction);
	addNewFormElement(labelForm, "facilityName", facilityId);
	addNewFormElement(labelForm, "generate_labels", "yes");
	addNewFormElement(labelForm, "paperSize", "31");

}

function createBinLabel() {
	var labelForm = document.getElementById("LabelPrintForm");
	
	prepLabelForm(labelForm, "/tcmIS/Hub/Cabinetbin", "generatebinlabels");
	addNewFormElement(labelForm, "binId", cellValue(myGrid.getSelectedRowId(), "binId"));
	
	labelForm.submit();
	
	setTimeout('window.status="";',3000);
}

function createCabinetLabels() {
	var labelForm = document.getElementById("LabelPrintForm");
	
	prepLabelForm(labelForm, "/tcmIS/Hub/Cabinetlabel", "generatecablabels");
	addNewFormElement(labelForm, "cabId", cellValue(myGrid.getSelectedRowId(), "applicationId"));
	addNewFormElement(labelForm, "fromWebApp", "Y");
	
	labelForm.submit();
	
	setTimeout('window.status="";',3000);
}


function createWorkAreaLabels(labelType) {
	if(labelType == 'generatecablabels')
		createCabinetLabels();
	else
		createBinLabel();

}

//helper function to add elements to the form
function addNewFormElement(inputForm, elementName, elementValue){
	var input = document.createElement("input");
	input.setAttribute("type", "hidden");
	input.setAttribute("name", elementName);
	input.setAttribute("value", elementValue);
	inputForm.appendChild(input);
}

function setChanged(rowId, cellInd) {
	myGrid.cells(rowId,myGrid.getColIndexById("changed")).setValue('Y');

	if($v("showDefault") == 'Y' && cellInd == "defaultPartType")
		toggleSubstrate(rowId);
}

function countTypeChanged(rowId, cellInd) {
	if( "KanBan" == cellValue(rowId,"countType")) {
	    validateKanBan(rowId);
    }else if ( "NotCounted" == cellValue(rowId,"countType")) {
        //changed from other count type to NotCounted
        myGrid.cells(rowId,myGrid.getColIndexById("avgAmountPermission")).setValue("Y");
        myGrid.cells(rowId,myGrid.getColIndexById("maxAmountPermission")).setValue("Y");
        myGrid._haas_json_data.rows[myGrid.getRowIndex(rowId)].data["avgAmountPermission"] = 'Y';
        myGrid._haas_json_data.rows[myGrid.getRowIndex(rowId)].data["maxAmountPermission"] = 'Y';
        myGrid.cells(rowId,myGrid.getColIndexById("avgAmount")).setValue("");
        myGrid.cells(rowId,myGrid.getColIndexById("maxAmount")).setValue("");
        var parentIndex = myGrid.haasGetRowSpanStart(rowId);
	    myGrid.haasRenderRow(myGrid.getRowId(parentIndex));
    }else if ( "NotCounted" == cellValue(rowId,"lastCountType")) {
        //changed from NotCounted to other count type
        myGrid.cells(rowId,myGrid.getColIndexById("avgAmountPermission")).setValue("N");
        myGrid.cells(rowId,myGrid.getColIndexById("maxAmountPermission")).setValue("N");
        myGrid._haas_json_data.rows[myGrid.getRowIndex(rowId)].data["avgAmountPermission"] = 'N';
        myGrid._haas_json_data.rows[myGrid.getRowIndex(rowId)].data["maxAmountPermission"] = 'N';
        myGrid.cells(rowId,myGrid.getColIndexById("avgAmount")).setValue("");
        myGrid.cells(rowId,myGrid.getColIndexById("maxAmount")).setValue("");
        var parentIndex = myGrid.haasGetRowSpanStart(rowId);
	    myGrid.haasRenderRow(myGrid.getRowId(parentIndex));
    }else {
        if ("Y" == cellValue(rowId,"permission") && "N" == cellValue(rowId,"hcoFlag")) {
            myGrid.cells(rowId,myGrid.getColIndexById("dropShipOverridePermission")).setValue("Y");
            myGrid._haas_json_data.rows[myGrid.getRowIndex(rowId)].data["dropShipOverridePermission"] = 'Y';
            if (cellValue(rowId,"dropShipOverride") == 'true')
                myGrid.cells(rowId,myGrid.getColIndexById("dropShipOverride")).setValue(true);
            else
                myGrid.cells(rowId,myGrid.getColIndexById("dropShipOverride")).setValue(false);
            var parentIndex = myGrid.haasGetRowSpanStart(rowId);
	        myGrid.haasRenderRow(myGrid.getRowId(parentIndex));
        }
    }
    //this is to keep track whether to blank out avg and max amount or not
    myGrid.cells(rowId,myGrid.getColIndexById("lastCountType")).setValue(cellValue(rowId,"countType"));
}

function validateKanBan(rowId) {
	if(cellValue(rowId,"kanbanReorderQuantity") == null || cellValue(rowId,"kanbanReorderQuantity").length == 0 || cellValue(rowId,"kanbanReorderQuantity") == "&nbsp") {
		alert(messagesData.nokanbanreorderqty);
		myGrid.cells(rowId,myGrid.getColIndexById("countType")).setValue(cellValue(rowId,"lastCountType"));
	}
}

var win = null;
function getNonManagedMaterial() {
	var applicationId = parent.$('applicationId');
	if(applicationId.value == '')
	{
		 var selectNonManagedMaterialWorkAreaUrl = "selectworkareafornonmanagedmatl.do?facilityId="+encodeURIComponent($v("facilityId"))+"&uAction=addNonmanagedMatlWorkAreaSelect&companyId="+$v("companyId");
		 win = openWinGeneric(selectNonManagedMaterialWorkAreaUrl, "selectWorkAreaForNonmanagedMaterial", "600", "125","yes", "50", "50");
		 parent.children[parent.children.length] = win;
	}
	else
		{
			var count = 0;
			var multiSelected = false;	
			var selectCount =  applicationId.value.split('|');
			if(selectCount.length > 1)
				multiSelected = true;
			if(multiSelected)
				{
					var selectNonManagedMaterialWorkAreaUrl = "selectworkareafornonmanagedmatl.do?facilityId="+encodeURIComponent($v("facilityId"))+"&uAction=addNonmanagedMatlWorkAreaSelect&companyId="+$v("companyId");
					win = openWinGeneric(selectNonManagedMaterialWorkAreaUrl, "selectWorkAreaForNonmanagedMaterial", "600", "125","yes", "50", "50");
					parent.children[parent.children.length] = win;
				}
			else
				{
					var applicationIdSel= parent.$('applicationIdSel');
					var nonManagedMaterialUrl = "materialsearchmain.do?userAction=search&facilityId="+encodeURIComponent($v("facilityId"))+"&companyId="+$v("companyId")+"&application="+encodeURIComponent(applicationIdSel.value)+"&applicationDesc="+encodeURIComponent(applicationIdSel.options[applicationIdSel.selectedIndex].text);
					openMaterialSearchWindow(nonManagedMaterialUrl);
				}
		}

	 parent.showTransitWin();
}

function openMaterialSearchWindow(nonManagedMaterialUrl)
{
	if(win != null)
		win.close();
	openWinGeneric(nonManagedMaterialUrl, "nonManagedMaterial", "900", "600","yes", "50", "50");
}


function addMaterial(cabinetId, materialId, avgAmount,maxAmount, sizeUnit, catalogId, catalogCompanyId, msdsno, tierIIStorage, tierIITemperature, largestContainerSize) {
	$("cabinetId").value = cabinetId;
	$("materialId").value = materialId;
	$("catalogId").value = catalogId;
	$("catalogCompanyId").value = catalogCompanyId;
	$("msdsNumber").value = msdsno;
	$("avgAmount").value = avgAmount;
	$("maxAmount").value = maxAmount;
	$("sizeUnit").value = sizeUnit;
	$("binPartStatus").value = 'A';
	$("tierIIStorage").value = tierIIStorage;
	$("tierIITemperature").value = tierIITemperature;
	$("largestContainerSize").value = largestContainerSize;
	
	parent.showPleaseWait();
	var now = new Date();
	parent.$("startSearchTime").value = now.getTime();
	$('uAction').value = 'insertNewRow';

	document.clientCabinetManagementForm.submit();
}

function deleteRow() {
	$("cabinetId").value = cellValue(myGrid.getSelectedRowId(),"cabinetId");
	$("materialId").value = cellValue(myGrid.getSelectedRowId(),"materialId");
	
	parent.showPleaseWait();
	var now = new Date();
	parent.$("startSearchTime").value = now.getTime();
	$('uAction').value = 'deletRow';

	document.clientCabinetManagementForm.submit();
}

function viewNonmanagedHist()
{
	var now = new Date();
	var nonManagedMaterialHistUrl = 'clientcabinetmanagementresults.do?uAction=nonManMatlHist&application='+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"cabinetId"))+
                                    '&facilityId='+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"facilityId"))+
                                    '&materialId=' + cellValue(myGrid.getSelectedRowId(),"materialId") +
                                    '&msdsString=' + encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"msdsString")) +
                                    '&catalogId=' + encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"catalogId")) +
                                    '&catalogCompanyId='  + cellValue(myGrid.getSelectedRowId(),"catalogCompanyId") +
                                    '&companyId='  + cellValue(myGrid.getSelectedRowId(),"companyId") + '&startSearchTime=' + now.getTime();
	parent.children[parent.children.length] = openWinGeneric(nonManagedMaterialHistUrl, "nonManagedMaterialHist", "900", "600","yes", "50", "50");
}

function viewPartHistory()
{
	var now = new Date();
	var partHistoryUrl = 'clientcabinetmanagementresults.do?uAction=viewPartHistory&application='+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"application"))+
                                    '&facilityId='+encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"facilityId"))+
                                    '&catPartNo=' + cellValue(myGrid.getSelectedRowId(),"catPartNo") +
                                    '&partGroupNo=' + cellValue(myGrid.getSelectedRowId(),"partGroupNo") +
                                    '&catalogId=' + encodeURIComponent(cellValue(myGrid.getSelectedRowId(),"catalogId")) +
                                    '&catalogCompanyId='  + cellValue(myGrid.getSelectedRowId(),"catalogCompanyId") +
                                    '&companyId='  + cellValue(myGrid.getSelectedRowId(),"companyId") + '&startSearchTime=' + now.getTime();
	parent.children[parent.children.length] = openWinGeneric(partHistoryUrl, "partHistoryUrl", "900", "600","yes", "50", "50");
}

function changeOwnership() {
	if(cellValue(myGrid.getSelectedRowId(),"changed") == 'Y'){
		alert(messagesData.saveBefore);
	}
	else{
		var companyId = cellValue(myGrid.getSelectedRowId(),"companyId");
		var facilityId = cellValue(myGrid.getSelectedRowId(),"facilityId");
		var facilityName = cellValue(myGrid.getSelectedRowId(),"facilityName");
		var application = cellValue(myGrid.getSelectedRowId(),"application");
		var applicationDesc = cellValue(myGrid.getSelectedRowId(),"applicationDesc");
		var applicationId = cellValue(myGrid.getSelectedRowId(),"applicationId");
		var cabinetName = cellValue(myGrid.getSelectedRowId(),"applicationDesc");
		var binId = cellValue(myGrid.getSelectedRowId(),"binId");
		var rpSlRq = cellValue(myGrid.getSelectedRowId(),"reorderPointStockingLevel").split("/");
		var reorderPoint = rpSlRq[0];
		var stockingLevel = rpSlRq[1];
		var kanbanReorderQuantity = cellValue(myGrid.getSelectedRowId(),"kanbanReorderQuantity");
		var reorderQuantity = rpSlRq[2];
		var leadTimeDays = cellValue(myGrid.getSelectedRowId(),"leadTimeInDays");
		var countType = cellValue(myGrid.getSelectedRowId(),"countType");
		var ownershipName = cellValue(myGrid.getSelectedRowId(),"ownershipName");
		var hcoFlag = cellValue(myGrid.getSelectedRowId(),"hcoFlag");
		var catalogCompanyId = cellValue(myGrid.getSelectedRowId(),"catalogCompanyId");
		var catalogId = cellValue(myGrid.getSelectedRowId(),"catalogId");
		var partNumber = cellValue(myGrid.getSelectedRowId(),"catPartNo");
		var partGroupNo = cellValue(myGrid.getSelectedRowId(),"partGroupNo");

		facilityId = facilityId.replace(/&amp;/gi, "%26");
		facilityId = facilityId.replace(/&/gi, "%26");
		facilityId = facilityId.replace(/#/gi, "%23");
		facilityName = facilityName.replace(/&amp;/gi, "%26");
		facilityName = facilityName.replace(/&/gi, "%26");
		facilityName = facilityName.replace(/#/gi, "%23");
		application = application.replace(/&/gi, "%26");
		application = application.replace(/#/gi, "%23");
		applicationDesc = applicationDesc.replace(/&/gi, "%26");
		applicationDesc = applicationDesc.replace(/#/gi, "%23");
		cabinetName = cabinetName.replace(/&/gi, "%26");
		cabinetName = cabinetName.replace(/#/gi, "%23");
		ownershipName = ownershipName.replace(/&amp;/gi, "%26");
		ownershipName = ownershipName.replace(/&/gi, "%26");
		ownershipName = ownershipName.replace(/#/gi, "%23");
		catalogCompanyId = catalogCompanyId.replace(/&/gi, "%26");
		catalogCompanyId = catalogCompanyId.replace(/#/gi, "%23");
		partNumber = partNumber.replace(/%/gi, "%25");
		partNumber = partNumber.replace(/&amp;/gi, "%26");
		partNumber = partNumber.replace(/&/gi, "%26");
		partNumber = partNumber.replace(/#/gi, "%23");
	    partNumber = partNumber.replace(/\+/gi, "%2b");
	
		var changeOwnershipUrl = "clientcabinetmanagementmain.do?uAction=loadChangeOwnership"
				+ "&companyId="+companyId
				+ "&facilityId="+facilityId
				+ "&facilityName="+facilityName
				+ "&application="+application
				+ "&applicationDesc="+applicationDesc
				+ "&cabinetName="+cabinetName
				+ "&reorderPoint="+reorderPoint
				+ "&stockingLevel="+stockingLevel
				+ "&kanbanReorderQuantity="+kanbanReorderQuantity
				+ "&reorderQuantity="+reorderQuantity
				+ "&leadTimeDays="+leadTimeDays
				+ "&countType="+countType
				+ "&binId="+binId
				+ "&ownershipName="+ownershipName
				+ "&hcoFlag="+hcoFlag
				+ "&catalogCompanyId="+catalogCompanyId
				+ "&catalogId="+catalogId
				+ "&catPartNo="+partNumber
				+ "&partGroupNo="+partGroupNo;
		
		var width = 325;
		var height = 400;
		
		var left = window.screenLeft + document.documentElement.offsetWidth/2 - width/2 + "";
		var top = window.screenTop + document.documentElement.offsetHeight/2 - height/2 + "";
		
		openWinGeneric(changeOwnershipUrl, "changeOwnership", width, height,"yes", top, left);
		parent.showTransitWin();
	}
}

function setOwnership(reorderPoint, stockingLevel, kanbanReorderQuantity, reorderQuantity, leadTimeDays, countType, ownershipName, hcoFlag, dropShipOverride){
	var rpSlRq = reorderPoint + " / " + stockingLevel + " / " + reorderQuantity; 
    cell(myGrid.getSelectedRowId(),"reorderPointStockingLevel").setValue(rpSlRq);
    cell(myGrid.getSelectedRowId(),"kanbanReorderQuantity").setValue(kanbanReorderQuantity);
    cell(myGrid.getSelectedRowId(),"leadTimeInDays").setValue(leadTimeDays);
    cell(myGrid.getSelectedRowId(),"countType").setValue(countType);
    cell(myGrid.getSelectedRowId(),"ownershipName").setValue(ownershipName);
    cell(myGrid.getSelectedRowId(),"hcoFlag").setValue(hcoFlag);

    var tmpRowId = myGrid.getSelectedRowId();
    if ("Y" == cellValue(tmpRowId,"permission")) {
       if("N" == hcoFlag) {
            myGrid.cells(tmpRowId,myGrid.getColIndexById("dropShipOverridePermission")).setValue("Y");
            myGrid._haas_json_data.rows[myGrid.getRowIndex(tmpRowId)].data["dropShipOverridePermission"] = 'Y';
            if ('Y' == dropShipOverride)
                myGrid.cells(tmpRowId,myGrid.getColIndexById("dropShipOverride")).setValue(true);
            else
                myGrid.cells(tmpRowId,myGrid.getColIndexById("dropShipOverride")).setValue(false);
        }else {
            myGrid.cells(tmpRowId,myGrid.getColIndexById("dropShipOverridePermission")).setValue("N");
            myGrid._haas_json_data.rows[myGrid.getRowIndex(tmpRowId)].data["dropShipOverridePermission"] = 'N';
            myGrid.cells(tmpRowId,myGrid.getColIndexById("dropShipOverride")).setValue(false);
        }
    }
    
    var parentIndex = myGrid.haasGetRowSpanStart(myGrid.getSelectedRowId());
    myGrid.haasRenderRow(myGrid.getRowId(parentIndex));
}

function changeBinName() {
	var binId = cellValue(myGrid.getSelectedRowId(),"binId");
	var binName = cellValue(myGrid.getSelectedRowId(),"binName");
    var tmpDropShipOverride = "N";
    if (cellValue(myGrid.getSelectedRowId(),"dropShipOverride") == 'true')
        tmpDropShipOverride = "Y";

    binId = binId.replace(/&amp;/gi, "%26");
	binId = binId.replace(/&/gi, "%26");
	binId = binId.replace(/#/gi, "%23");
	binName = binName.replace(/&amp;/gi, "%26");
	binName = binName.replace(/&/gi, "%26");
	binName = binName.replace(/#/gi, "%23");

	var changeBinNameUrl = "clientcabinetmanagementmain.do?uAction=loadChangeBinName"
			+ "&binId="+binId
			+ "&binName="+encodeURIComponent(binName)
            + "&dropShipOverride="+tmpDropShipOverride;
	
	var width = 325;
	var height = 150;
	
	var left = window.screenLeft + document.documentElement.offsetWidth/2 - width/2 + "";
	var top = window.screenTop + document.documentElement.offsetHeight/2 - height/2 + "";
	
	openWinGeneric(changeBinNameUrl, "changeBinName", width, height,"yes", top, left);
	parent.showTransitWin();
	
}

function setBinName(binId, binName){
	cell(myGrid.getSelectedRowId(),"binId").setValue(binId);
    cell(myGrid.getSelectedRowId(),"binName").setValue(binName);
    
    var parentIndex = myGrid.haasGetRowSpanStart(myGrid.getSelectedRowId());
    myGrid.haasRenderRow(myGrid.getRowId(parentIndex));
}

function setCabinetCountInterpolation() {
   var binId = cellValue(myGrid.getSelectedRowId(),"binId");
   var binName = cellValue(myGrid.getSelectedRowId(),"binName");   
   binId = binId.replace(/&amp;/gi, "%26");
   binId = binId.replace(/&/gi, "%26");
   binId = binId.replace(/#/gi, "%23");
   binName = binName.replace(/&amp;/gi, "%26");
   binName = binName.replace(/&/gi, "%26");
   binName = binName.replace(/#/gi, "%23");
   
	var setCabinetCountInterpolationUrl = "clientcabinetinterpolation.do?uAction=viewInterpolationCount"
		+ "&binId="+binId
		+ "&binName="+encodeURIComponent(binName);
	   
	openWinGeneric(setCabinetCountInterpolationUrl, "CabinetCountInterpolation", "600", "550","yes", "50", "50");
	parent.showTransitWin();	
}

/*
function changeDropShipOverride(rowId,cellInd) {
	tmpStartingRowIndex = myGrid.haasGetRowSpanStartLvl2(rowId);
	tmpEndingRowIndex = myGrid.haasGetRowSpanEndIndexLvl2(rowId);
	if($("showBinPartStatus"+rowId).checked == true) {
		for (var i = tmpStartingRowIndex; i < tmpEndingRowIndex; i++) {
			myGrid.cells(i+1,myGrid.getColIndexById("binPartStatus")).setValue('A');
			myGrid._haas_json_data.rows[myGrid.getRowIndex(i+1)].data[myGrid.getColIndexById("binPartStatus")] = 'A';
		}
	}else {
		for (var i = tmpStartingRowIndex; i < tmpEndingRowIndex; i++) {
			myGrid.cells(i+1,myGrid.getColIndexById("binPartStatus")).setValue('I');
			myGrid._haas_json_data.rows[myGrid.getRowIndex(i+1)].data[myGrid.getColIndexById("binPartStatus")] = 'I';
		}
	}
	setChanged(rowId);
}
*/


/*
function addMaterial(msdsNumber, materialId, materialDesc) {
	
	var totalRows = myGrid.getRowsNum(); 
	var rowId = (new Date()).valueOf(); //totalRows*1+1;
     
    var thisrow = myGrid.addRow(rowId,"",totalRows);
	
 //   var rowId = myGrid.getRowId(totalRows-1);
	var parentIndex = myGrid.getRowIndex(rowId);
	var newLinePosition = myGrid.getRowIndex(rowId);
	
	lineMap[newLinePosition] = 1;
    lineMap2[newLinePosition] = 1;
    lineMap3[newLinePosition] = (newLinePosition)%2;	

	var newRowData = ['Y','N','Y','Y','Y',
			  parent.$("cabinetIdArray").options[parent.$("cabinetIdArray").selectedIndex].value,
			  '','',
			  true,
			  '','','','','','',
			  materialDesc,
			  '',
			  msdsNumber,
			  '','','','','','',
			  $v("companyId"),
			  '','','','',
			  $v("facilityId"),
			  $v("facilityName"),
			  '','A','','','',materialId,'Y','Y'];
		  
	myGrid.haasAddRowToRowSpan(newRowData,newLinePosition, parentIndex);
	
	myGrid.haasRenderRow(newLinePosition);

	alertrowid = true;

	myGrid.haasRenderRow(myGrid.getRowId(parentIndex));
	
	myGrid.selectRow(parentIndex);
	$("showBinPartStatus"+rowId).checked = true;
	updateHchStatusA("showBinPartStatus"+rowId);
	
}

function deleteRow() {
	myGrid.deleteRow(selectedRowId);
}
*/