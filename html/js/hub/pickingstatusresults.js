var beangrid;
var resizeGridWithWindow = true;
var saveRowClass = null;
var selectedRowId = null;
var children = [];

function resultOnLoad() {
	totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		document.getElementById("pickingStatusData").style["display"] = "";
		initGridWithGlobal(gridConfig);
		
	}
	else {
		document.getElementById("pickingStatusData").style["display"] = "none";
	}
	
	displayGridSearchDuration();
	setResultFrameSize();
};

function selectRow(rowId, cellInd) {
	selectedRowId = rowId;
};



function checkAll(checkboxname) {
	var checkall = $("chkAllOk");
	var rowsNum = beangrid.getRowsNum();

	rowsNum = rowsNum * 1;

	gridRenderAllRows(beangrid);
	
	if (checkall.checked) {
		for ( var p = 1; p < (rowsNum + 1); p++) {
			var cid = checkboxname + p;
			if (!$(cid).disabled && !$(cid).checked) {
				$(cid).checked = true;
				updateHchStatusA(cid);
			}
		}
	}
	else {
		for ( var p = 1; p < (rowsNum + 1); p++) {
			var cid = checkboxname + p;
			if (!$(cid).disabled && $(cid).checked) {
				$(cid).checked = false;
				updateHchStatusA(cid);
			}
		}
	}
	return true;
};

function countChecked() {
	// *note: requires TOTAL_ROWS to be defined prior to the inclusion of
	// this
	var totallines = $v("totalLines");
	// first do rows
	var totalChecked = 0;
	var okValue;
	for ( var p = 1; p <= totallines; p++) {
		try {
			var chkBox = $("ok"+p);
			if (chkBox.checked) {
				totalChecked++;
			}
		}
		catch (ex) {
		}
	}
	return totalChecked;
};

function submitUpdate() {
	if (countChecked() > 0) {
		parent.showPleaseWait();
		$("uAction").value = "update";
		beangrid.parentFormOnSubmit();
		document.genericForm.submit();
		return true;
	}
	else {
		alert(messagesData.pleaseSelect);
		return false;
	}
};

function submitDOTOverride(override) {
		parent.showPleaseWait();
		$("uAction").value = "UpdateDotOverride";
		$("pickingUnitId").value = cellValue(selectedRowId, "pickingUnitId");
		$("dotOverride").value = override;
		beangrid.parentFormOnSubmit();
		document.genericForm.submit();
};

function rightClickRow(rowId, cellInd) {
	selectedRowId = rowId;
	if (cellInd >= beangrid.getColIndexById("pickingUnitId")) {
		updateRightClickMenu(rowId, cellInd);
		updateDocumentMenu(rowId, cellInd);
		if (shippingOverridePermission) {
			updateDOTOverrideMenu(rowId);
		}
		toggleContextMenu('rightClickMenu');
	}
	else {
		toggleContextMenu('contextMenu');
	}
};

function updateRightClickMenu(rowId) {
	var overrideApprovalPerm = $v("overrideApproval");
	var overrideOptionValid = gridCellValue(beangrid, rowId, "overrideOption");
	var status = gridCellValue(beangrid, rowId, "pickingState");
	
	mm_deleteItemByText(messagesData.printpvr);
	if (status == "PACK_READY" ||status == "PACK_PROGRESS" ||status == "PRE_SHIP" ||status == "DEPARTURE_READY" || status == "SHIP_READY" || status == "SHIP_CONFIRMED")
		mm_insertItem("rightClickMenu",0,"text="+messagesData.printpvr+";url=javascript:printPVR();");
	
	mm_deleteItemByText(messagesData.overrideapproval);
	if (overrideApprovalPerm == "Y" && overrideOptionValid == "Y") {
		mm_insertItem("rightClickMenu",0,"text="+messagesData.overrideapproval+";url=javascript:overrideApproval();");
	}
	
	mm_deleteItemByText(messagesData.printStraightBOL);
	if ((status == "SHIP_READY" || status == "SHIP_CONFIRMED") && gridCellValue(beangrid, rowId, "tabletShipmentId") != "")
		mm_editItem("rightClickMenu", 0,"text="+messagesData.printStraightBOL+";url=javascript:printStraightBOL();");
};

function updateDocumentMenu(rowId) {
	var puid = cellValue(selectedRowId, "pickingUnitId");
	var itemCount = mm_returnMenuItemCount("documentMenu");
	for(var i = itemCount; i > 1; i--) {
		mm_deleteItem("documentMenu",i);
	}

	// Default to one entry, "None"
	mm_insertItem("documentMenu",1,"text=<font color='gray'>"+messagesData.none+"</font>;url=javascript:doNothing();");
	mm_deleteItem("documentMenu",1);

	for (x in pickingUnitDocs) {
		var pDoc = pickingUnitDocs[x];
		if (pDoc.pickingUnitId == puid) {
			for (var d in pDoc.documents) {
				var url = pDoc.documents[d].url;
				var txt = pDoc.documents[d].type;
				mm_insertItem("documentMenu",(d+1),"text="+txt+";url=javascript:openPickingUnitDocument(\""+url+"\");");
				if (d == 0) {
					mm_deleteItem("documentMenu",1);
				}
			}
		}
	}
};

function updateDOTOverrideMenu() {
	var dotOverrideMenu = "dotOverrideMenu";
	var currentOverride = cellValue(selectedRowId, "dotOverride");
	var itemCount = mm_returnMenuItemCount(dotOverrideMenu);
	for(var i = itemCount; i > 1; i--) { // Must always leave at least one entry
		mm_deleteItem(dotOverrideMenu,i);
	}
	
	//since data needs to be in English when storing in database, values are hard-coded
	var dotOverrideOptions = {
		"" : messagesData.none,
		"Limited Quantity Material" : messagesData.limitedquantitymaterial,
		"Not Regulated" : messagesData.notregulated
	};
	
	//if override value is not recognized, default to None
	if (!dotOverrideOptions.hasOwnProperty(currentOverride))
		currentOverride = "";

	var selectedTextFormat = "text=&#10003 <font color='gray'>{0}</font>;url=javascript:doNothing();";
	for (var optionValue in dotOverrideOptions)
		if (optionValue === currentOverride) {
			mm_insertItem(dotOverrideMenu,1,selectedTextFormat.replace("{0}", dotOverrideOptions[optionValue]));
		} else {
			mm_insertItem(dotOverrideMenu,1,"text=" + dotOverrideOptions[optionValue] + ";url=javascript:submitDOTOverride('" + optionValue + "');");
		}
	mm_deleteItem(dotOverrideMenu,1); // Now that we've added an entry, Delete old first entry
};

function printPVR() {
	var pvrLoc = "/HaasReports/report/printConfigurableReport.do"+
	    "?pr_pickingUnitId="+cellValue(selectedRowId,"pickingUnitId")+
	    "&rpt_ReportBeanId=PickValidationRecordReportDefinition";
	openWinGeneric(pvrLoc,"printPVR","700","550","yes","100","100");
};

function printDOTLabel() {
	$("labelPrintType").value = "dot";
	$("uAction").value = "printIataDotLabels";
	selectPrintQty();
	
}

function printIATALabel() {
	$("labelPrintType").value = "iata";
	$("uAction").value = "printIataDotLabels";
	selectPrintQty();
}

function printSampleDeliveryLabel() {
	$("labelPrintType").value = "samplebox";
	$("uAction").value = "printSampleDeliveryLabel";
	selectPrintQty();
}

function selectPrintQty() {
	openWinGeneric("selectprintqty.do","selectPrintQty","300","200","yes","100","100");
}

function printQtyChanged(printQty) {
	$("labelPrintQty").value = printQty;
	
	$("pickingUnitId").value = gridCellValue(beangrid, selectedRowId, "pickingUnitId");
	beangrid.parentFormOnSubmit();
	openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_GenerateLabels','650','600','yes');
	document.genericForm.target = "_GenerateLabels";
	document.genericForm.submit();
	
	setTimeout('window.status="";',5000);
}

function printStraightBOL() {
	$("uAction").value = "printStraightBOL";
	$("tabletShipmentId").value = gridCellValue(beangrid, selectedRowId, "tabletShipmentId");
	beangrid.parentFormOnSubmit();
	openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_GenerateLabels','650','600','yes');
	document.genericForm.target = "_GenerateLabels";
	document.genericForm.submit();
	
	setTimeout('window.status="";',5000);
}

function openPickingUnitDocument(loc) {
	children[children.length] = openWinGeneric(loc,"pickingUnitDocumentViewer","700","320","yes","100","100","20","20","no");
};

function overrideApproval() {
	var loc = "overrideapproval.do?uAction=search&pickingUnitId="+cellValue(selectedRowId, "pickingUnitId")
	+"&rid="+cellValue(selectedRowId, "receiptId")
	+"&qty="+cellValue(selectedRowId, "quantity")
	+"&pickingState="+encodeURIComponent(cellValue(selectedRowId, "pickingState"))
	+"&issueId="+cellValue(selectedRowId, "issueId");
//	alert(loc);
	children[children.length] = openWinGeneric(loc, "overrideApproval", "500", "450", "yes", "80", "80");
};

function reloadResults() {
	parent.resultFrame.doRefresh("Picking Status");
	
};

function doRefresh(requestTabId) {
	window.setTimeout('finishRefresh();', 10);
};

function finishRefresh() {
	// redo the search
	document.genericForm.target='';
	document.getElementById('uAction').value = 'search';

	parent.showPleaseWait();


	document.genericForm.submit();
};
		
function doNothing() {};



function closeAllchildren() {
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
//	if (document.getElementById("uAction").value != 'new') {
	try {
		for(var n=0;n<children.length;n++) {
			try {
			children[n].closeAllchildren(); 
			} catch(ex){}
		children[n].close();
		}
	} catch(ex){}
	children = new Array(); 
};
