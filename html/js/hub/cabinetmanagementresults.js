var myGrid;

var selectedRowId = null;

// Set this to be false if you don't want the grid width to resize based on window size.

var resizeGridWithWindow = true;

// Allow different permissions for different columns
var multiplePermissions = true;

// Build up the array for the columns which use different permissions
var permissionColumns = new Array();
permissionColumns = {
        "status" : true
};

var inventoryPartMgmt = false;
var chargeNumberSetup = false;
var directedChargeAssignment = false;

function resultOnLoad() {
//	parent.closeTransitWin();
	var totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		if (!showUpdateLinks) {
			parent.document.getElementById("updateResultLink").style["display"] = "none";
			parent.document.getElementById("showlegendLink").style["display"] = "";
			parent.document.getElementById("createNewBinLink").style["display"] = "none";
		}else {
			parent.document.getElementById("updateResultLink").style["display"] = "";
			parent.document.getElementById("showlegendLink").style["display"] = "";
			parent.document.getElementById("createNewBinLink").style["display"] = "none";
		}
		// make result area visible if data exist
		document.getElementById("cabinetPartLevelViewBean").style["display"] = "";
		// build the grid for display
		doInitGrid();
	}else {
		document.getElementById("cabinetPartLevelViewBean").style["display"] = "none";
		parent.document.getElementById("showlegendLink").style["display"] = "none";
		parent.document.getElementById("createNewBinLink").style["display"] = "none";
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
			parent.document.getElementById("createNewBinLink").style["display"] = "";
		}else {
			parent.document.getElementById("mainUpdateLinks").style["display"] = "none";
			parent.document.getElementById("updateResultLink").style["display"] = "none";
			parent.document.getElementById("createNewBinLink").style["display"] = "none";
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
	myGrid._haas_row_span_cols = [0,1,2,3,4,5,6,7,8,9];

	if (lineMap2) {
		myGrid._haas_row_span_lvl2 = true;
		myGrid._haas_row_span_lvl2_map = lineMap2;
		myGrid._haas_row_span_lvl2_cols = [10,11,12,13];
	}
   myGrid.attachEvent("onAfterHaasRenderRow", setInactiveRowColor);
	if( typeof( jsonMainData ) != 'undefined' ) {
		myGrid.parse(jsonMainData,"json");
	}
	myGrid.attachEvent("onRightClick", selectRightclick);
	//myGrid.attachEvent("onAfterHaasRenderRowSpan", calculateBinPartStatus);
}

//cabinet bin section
function selectRightclick(rowId, cellInd) {
	if (cellInd < myGrid.getColIndexById("countType")) {
		toggleContextMenu('workAreaRightClickMenu');
	}else if (cellInd < myGrid.getColIndexById("itemId")) {
		toggleContextMenu("partRightClickMenu");
	}else {
		toggleContextMenu("showEmpty");
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
}

function setInactiveRowColor(rowId) {
	rowIndex = myGrid.getRowIndex(rowId);
	// Check JSON data because grid cell may not have been rendered yet
	if (myGrid._haas_json_data.rows[rowIndex].data[myGrid.getColIndexById("binPartStatus")] == 'I') {
		//grid black
		myGrid.haasSetColSpanStyle(rowId, 0, 14, "background-color: #747170;");
	}
}

function setActiveRowColor(rowId) {
	colorStyle = myGrid.rowsAr[rowId].className;
	myGrid.haasSetColSpanStyle(rowId, 0, 14, colorStyle);
}

function addPartFromRightMouseClick() {
	parent.$("companyId").value = cellValue(myGrid.getSelectedRowId(),"companyId");
	parent.$("application").value = cellValue(myGrid.getSelectedRowId(),"application");
	parent.$("applicationDesc").value = cellValue(myGrid.getSelectedRowId(),"applicationDesc");
	parent.$("applicationId").value = cellValue(myGrid.getSelectedRowId(),"applicationId");
   parent.$("binId").value = cellValue(myGrid.getSelectedRowId(),"binId");
	parent.addPart();
}

function addBin() {
	$("uAction").value = 'addbin';
	$("catalogCompanyId").value = cellValue(myGrid.getSelectedRowId(),"catalogCompanyId");
	$("catalogId").value = cellValue(myGrid.getSelectedRowId(),"catalogId");
	$("catalogDesc").value = cellValue(myGrid.getSelectedRowId(),"catalogDesc");
	$("companyId").value = cellValue(myGrid.getSelectedRowId(),"companyId");
	$("application").value = cellValue(myGrid.getSelectedRowId(),"application");
	$("cabinetId").value = cellValue(myGrid.getSelectedRowId(),"applicationId");
	$("cabinetName").value = cellValue(myGrid.getSelectedRowId(),"cabinetName");
	openWinGeneric('/tcmIS/common/loadingpleasewait.jsp', '_newAddBin', '650','600', 'yes');
	document.cabinetManagementForm.target = '_newAddBin';
	var a = window.setTimeout("document.cabinetManagementForm.submit();", 500);

}

function changeMinMaxCabinet() {
	var hubName = parent.$v("hubName");
	var companyId = cellValue(myGrid.getSelectedRowId(),"companyId");
	var facilityId = cellValue(myGrid.getSelectedRowId(),"facilityId");
	var facilityName = cellValue(myGrid.getSelectedRowId(),"facilityName");
	var cabinetId = cellValue(myGrid.getSelectedRowId(),"applicationId");
	var cabinetName = cellValue(myGrid.getSelectedRowId(),"applicationDesc");  
	var binId = cellValue(myGrid.getSelectedRowId(),"binId");
	var catalogCompanyId = cellValue(myGrid.getSelectedRowId(),"catalogCompanyId");
	var catalogId = cellValue(myGrid.getSelectedRowId(),"catalogId");
	var partNumber = cellValue(myGrid.getSelectedRowId(),"catPartNo");
	var partGroupNo = cellValue(myGrid.getSelectedRowId(),"partGroupNo");
	var status = cellValue(myGrid.getSelectedRowId(),"status");
	var itemId = cellValue(myGrid.getSelectedRowId(),"itemId");
   
	facilityId = facilityId.replace(/&amp;/gi, "%26");
	facilityId = facilityId.replace(/&/gi, "%26");
	facilityId = facilityId.replace(/#/gi, "%23");
	facilityName = facilityName.replace(/&amp;/gi, "%26");
	facilityName = facilityName.replace(/&/gi, "%26");
	facilityName = facilityName.replace(/#/gi, "%23");
	cabinetName = cabinetName.replace(/&/gi, "%26");
	cabinetName = cabinetName.replace(/#/gi, "%23");
	catalogCompanyId = catalogCompanyId.replace(/&/gi, "%26");
	catalogCompanyId = catalogCompanyId.replace(/#/gi, "%23");
	partNumber = partNumber.replace(/%/gi, "%25");
	partNumber = partNumber.replace(/&amp;/gi, "%26");
	partNumber = partNumber.replace(/&/gi, "%26");
	partNumber = partNumber.replace(/#/gi, "%23");
	
    partNumber = partNumber.replace(/\+/gi, "%2b");
      
	var levelChangeUrl = "cabinetlevel.do?uAction=viewMinMax"
			+ "&hubName="+hubName					
			+ "&companyId="+companyId
			+ "&facilityId="+facilityId
			+ "&facilityName="+facilityName
			+ "&application="+cabinetName					
			+ "&cabinetId="+cabinetId
			+ "&cabinetName="+cabinetName
			+ "&catalogCompanyId="+catalogCompanyId
	      + "&catalogId="+catalogId
			+ "&catPartNo="+partNumber
			+ "&partGroupNo="+partGroupNo
			+ "&itemId="+itemId
		   + "&binId="+binId
			+ "&status="+status;
	openWinGeneric(levelChangeUrl, "getchang_elevelscreen", "900", "600","yes", "50", "50");
}

function updateGrid() {
	parent.showPleaseWait();
	var now = new Date();
	parent.$("startSearchTime").value = now.getTime();
	$('uAction').value = 'update';
	document.cabinetManagementForm.target = 'resultFrame';
	//prepare grid for data sending
	myGrid.parentFormOnSubmit();
	if($v("sourcePage") == 'cabinetManagement')
		document.cabinetManagementForm.submit();
	else
		document.clientCabinetManagementForm.submit();
}


// all same level, at least one item
function replaceMenu(menuname,menus){
	var i = mm_returnMenuItemCount(menuname);

	for(;i> 1; i-- )
		mm_deleteItem(menuname,i);

	for( i = 0; i < menus.length; ){
		var str = menus[i];
		i++;
		mm_insertItem(menuname,i,str);
		// delete original first item.
		if( i == 1 ) mm_deleteItem(menuname,1);
	}
}

function viewMSDS(materialId) {
	facilityId = cellValue(myGrid.getSelectedRowId(),"facilityId");
	if($v("sourcePage") == 'cabinetManagement') {
		itemId = cellValue(myGrid.getSelectedRowId(),"itemId");
		var loc = "/tcmIS/all/ViewMsds?act=msds&cl=Internal&facility="+facilityId+"&itemid="+itemId+"&id="+materialId;
		openWinGeneric(loc,"ViewMsds","900","700","yes","50","50","20","20","no");
	}
	else {
		companyId = cellValue(myGrid.getSelectedRowId(),"companyId");
		catPartNo = cellValue(myGrid.getSelectedRowId(),"catPartNo");
		openWinGeneric('ViewMsds?act=msds'+'&id='+materialId+
				'&showspec=N' +
				'&spec=' +
				'&cl='+companyId+
				'&facility='+encodeURIComponent(facilityId) +
				'&catpartno='+encodeURIComponent(catPartNo)
				,"ViewMSDS","800","800",'yes' );
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
		       +'&facilityId='+facilityId+'&facilityName='+facilityName+'&sourcePage='+$v("sourcePage")+
				 '&searchText='+callEditDirectedChargeFrom;   //I am using this field to keep track of where this is called from
	if (callEditDirectedChargeFrom == 'cabinet') {
		url += '&applicationId='+cellValue(myGrid.getSelectedRowId(),"application");
	}else if (callEditDirectedChargeFrom == 'part') {
		url += '&applicationId='+cellValue(myGrid.getSelectedRowId(),"application")+
				 '&catalogCompanyId='+cellValue(myGrid.getSelectedRowId(),"catalogCompanyId")+
				 '&catalogId='+cellValue(myGrid.getSelectedRowId(),"catalogId")+
				 '&catalogAddPartGroupNo='+cellValue(myGrid.getSelectedRowId(),"partGroupNo")+
				 '&catalogAddCatPartNo='+cellValue(myGrid.getSelectedRowId(),"catPartNo");  //the reason I am using this is because cat_part_no is an array
	}else {
		url += '&applicationId='+cellValue(myGrid.getSelectedRowId(),"application");
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
			"&facilityId="+facilityId+"&poNumber="+poNumber+"&poLine="+releaseNumber+
			"&chargeType="+chargeType+"&chargeNumbers="+chargeNumbers+"&userEnteredChargeNumber="+userEnteredChargeNumber+
			"&sourcePage="+$v("sourcePage")++"&ignoreNullChargeNumber="+ignoreNullChargeNumber+
			"&searchText="+callEditDirectedChargeFrom;   //I am using this field to keep track of where this is called from
	if (callEditDirectedChargeFrom == 'cabinet') {
		url += "&applicationId="+cellValue(myGrid.getSelectedRowId(),"application");
	}else if (callEditDirectedChargeFrom == 'part') {
		url += "&applicationId="+cellValue(myGrid.getSelectedRowId(),"application")+
				 "&catalogCompanyId="+cellValue(myGrid.getSelectedRowId(),"catalogCompanyId")+
				 "&catalogId="+cellValue(myGrid.getSelectedRowId(),"catalogId")+
				 '&catalogAddPartGroupNo='+cellValue(myGrid.getSelectedRowId(),"partGroupNo")+
				 "&catalogAddCatPartNo="+cellValue(myGrid.getSelectedRowId(),"catPartNo");  //the reason I am using this is because cat_part_no is an array
	}else {
		url += "&applicationId="+cellValue(myGrid.getSelectedRowId(),"application");
	}

	callToServer(url);
} //end of method

function addWorkAreaCabinetPartDirectedChargeNumbers(chargeType,chargeNumbers,poNumber,releaseNumber,userEnteredChargeNumber) {
	parent.showTransitWin(formatMessage(messagesData.waitingforinput, messagesData.directedCharge)+"...");
	companyId = cellValue(myGrid.getSelectedRowId(),"companyId");
	facilityId = cellValue(myGrid.getSelectedRowId(),"facilityId");

	url = "clientcabinetmanagementmain.do?uAction=addWorkAreaCabinetPartDirectedCharge&companyId="+companyId+"&accountSysId="+parent.$v("accountSysId")+
			"&facilityId="+facilityId+"&poNumber="+poNumber+"&poLine="+releaseNumber+
			"&chargeType="+chargeType+"&chargeNumbers="+chargeNumbers+"&userEnteredChargeNumber="+userEnteredChargeNumber+
			"&sourcePage="+$v("sourcePage")+
			"&searchText="+callEditDirectedChargeFrom;   //I am using this field to keep track of where this is called from
	if (callEditDirectedChargeFrom == 'cabinet') {
		url += "&applicationId="+cellValue(myGrid.getSelectedRowId(),"application");
	}else if (callEditDirectedChargeFrom == 'part') {
		url += "&applicationId="+cellValue(myGrid.getSelectedRowId(),"application")+
				 "&catalogCompanyId="+cellValue(myGrid.getSelectedRowId(),"catalogCompanyId")+
				 "&catalogId="+cellValue(myGrid.getSelectedRowId(),"catalogId")+
				 '&catalogAddPartGroupNo='+cellValue(myGrid.getSelectedRowId(),"partGroupNo")+
				 "&catalogAddCatPartNo="+cellValue(myGrid.getSelectedRowId(),"catPartNo");  //the reaso
		// n I am using this is because cat_part_no is an array
	}else {
		url += "&applicationId="+cellValue(myGrid.getSelectedRowId(),"application");
	}

	callToServer(url);
} //end of method

function deleteDirectedCharge(chargeType) {
	companyId = cellValue(myGrid.getSelectedRowId(),"companyId");
	facilityId = cellValue(myGrid.getSelectedRowId(),"facilityId");

	url = "clientcabinetmanagementmain.do?uAction=deleteDirectedCharge&companyId="+companyId+"&facilityId="+facilityId+
		   "&searchText="+callEditDirectedChargeFrom;   //I am using this field to keep track of where this is called from
    url += "&chargeType="+chargeType;
    if (callEditDirectedChargeFrom == 'cabinet') {
		url += "&applicationId="+cellValue(myGrid.getSelectedRowId(),"application");
	}else if (callEditDirectedChargeFrom == 'part') {
		url += "&applicationId="+cellValue(myGrid.getSelectedRowId(),"application")+
				 "&catalogCompanyId="+cellValue(myGrid.getSelectedRowId(),"catalogCompanyId")+
				 "&catalogId="+cellValue(myGrid.getSelectedRowId(),"catalogId")+
				 '&catalogAddPartGroupNo='+cellValue(myGrid.getSelectedRowId(),"partGroupNo")+
				 "&catalogAddCatPartNo="+cellValue(myGrid.getSelectedRowId(),"catPartNo");  //the reason I am using this is because cat_part_no is an array
	}else {
		url += "&applicationId="+cellValue(myGrid.getSelectedRowId(),"application");
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

function createCabinetLabels(labelType) {
	var labelForm = document.getElementById("LabelPrintForm");

	prepLabelForm(labelForm, "/tcmIS/Hub/Cabinetlabel", labelType);
	addNewFormElement(labelForm, "cabId", cellValue(myGrid.getSelectedRowId(), "applicationId"));

	labelForm.submit();

	setTimeout('window.status="";',3000);
}


function createWorkAreaLabels(labelType) {
	var labelForm = document.getElementById("LabelPrintForm");

	prepLabelForm(labelForm, "/tcmIS/Hub/Cabinetlabel", labelType);
	//addNewFormElement(labelForm, "workAreaName", cellValue(myGrid.getSelectedRowId(), "application"));
	addNewFormElement(labelForm, "cabId", cellValue(myGrid.getSelectedRowId(), "applicationId"));

	labelForm.submit();

	setTimeout('window.status="";',3000);
}

//helper function to add elements to the form
function addNewFormElement(inputForm, elementName, elementValue){
	var input = document.createElement("input");
	input.setAttribute("type", "hidden");
	input.setAttribute("name", elementName);
	input.setAttribute("value", elementValue);
	inputForm.appendChild(input);
}