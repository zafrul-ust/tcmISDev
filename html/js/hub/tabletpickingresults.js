var beangrid;
var resizeGridWithWindow = true;
var saveRowClass = null;
var selectedRowId = null;

function resultOnLoad() {

	totalLines = document.getElementById("totalLines").value;

	if (totalLines > 0) {
		document.getElementById("picklistViewBean").style["display"] = "";

		initializeGrid();
	}
	else {
		document.getElementById("picklistViewBean").style["display"] = "none";
	}

	displayGridSearchDuration();

	if (!showUpdateLinks) // Dont show any update links if the user does
				// not have permissions
	{
		parent.document.getElementById("updateResultLink").style["display"] = "none";
		try {
			document.getElementById("chkAllOk").style["display"] = "none";
		}
		catch (ex) {
		}
	}
	else {
		parent.document.getElementById("updateResultLink").style["display"] = "";
	}

	/*
	 * It is important to call this after all the divs have been turned on
	 * or off.
	 */
	setResultFrameSize();

	genPickingUnitView();
/*	if (($("picklistId").value.trim().length > 0)) {
		var loc = "picklistreprintmain.do?fromPickingPicklist=Y&action=search&inventoryGroup=" + $("inventoryGroup").value + "&picklistId=" + $("picklistId").value + "&sortBy=" + $("sortBy").value + "&opsEntityId=" + $("opsEntityId").value;
		
		 if ($v("companyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
		  		
		openWinGeneric(loc, "picklistreprint", "800", "400", "yes", "80", "80");
	}*/
}

function genPickingUnitView() {
	if (($("picklistId").value.trim().length > 0)) {
		var loc = "tabletpickableunitviewmain.do?action=search&hub=" + $v("hub") + "&inventoryGroup=" + $v("inventoryGroup") + "&picklistId=" + $v("picklistId") + "&sortBy=" + $v("sortBy") + "&opsEntityId=" + $v("opsEntityId");
		
		 if ($v("companyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
		  		
		openWinGeneric(loc, "pickableunitview", "900", "400", "yes", "80", "80");
	}
}

function initializeGrid() {
	beangrid = new dhtmlXGridObject('picklistViewBean');

	initGridWithConfig(beangrid, config, false, true);
	beangrid._haas_bg_render_enabled = true;
	beangrid.enableSmartRendering(true);

	if (typeof (jsonMainData) != 'undefined') {
		beangrid.parse(jsonMainData, "json");
		beangrid.attachEvent("onRowSelect", selectRow);
		beangrid.attachEvent("onRightClick", selectRightclick);
	}
}

function selectRow(rowId, cellInd) {
	//beangrid.selectRowById(rowId, null, false, false);
/*
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		setRowClass(rowId, '' + saveRowClass + 'Selected'); */

	selectedRowId = rowId;
}

function selectRightclick(rowId, cellInd) {
	selectRow(rowId, cellInd)

	var itemId = gridCellValue(beangrid, selectedRowId, "itemId");
	var distribution = gridCellValue(beangrid, selectedRowId, "distribution");
	if (itemId.length > 0 && distribution == 'Y')
		toggleContextMenu('picklistPickingMenu');
	else if (distribution == 'Y')
		toggleContextMenu('openMrMenu');
	else if (itemId.length > 0 && !disabledItemNotes)
		toggleContextMenu('itemNotesMenu');
}

function showItemNotes() {
	var itemId = gridCellValue(beangrid, selectedRowId, "itemId");
	//var loc = "/tcmIS/supply/edititemnotes.do?itemId=" + itemId;
	var loc = "edititemnotes.do?itemId=" + itemId;
	
	 if ($v("companyId") == 'Radian') 
		  loc = "/tcmIS/supply/" + loc;
	
	var winId = 'showItemNotes' + $v("prNumber");

	openWinGeneric(loc, winId.replace('.', 'a'), "800", "600", "yes", "50", "50", "20", "20", "no");
}

function openMr() {
	var prNumber = gridCellValue(beangrid, selectedRowId, "prNumber");
	var loc = "/tcmIS/distribution/scratchpadmain.do?uAction=searchScratchPadId&scratchPadId=" + prNumber + "&tabId=" + encodeURIComponent('scratchPad' + prNumber + '');
	try {
		parent.parent.openIFrame("scratchPad" + prNumber + "", loc, "MR " + prNumber + "", "", "N");
	}
	catch (ex) {
		openWinGeneric(loc, "scratchPad" + prNumber, "900", "600", "yes", "80", "80", "yes");
	}
}

function showDOT() {
	var itemId = gridCellValue(beangrid, selectedRowId, "itemId");
	//var loc = "/tcmIS/hub/shippinginfo.do?uAction=search&itemId=" + itemId;
	var loc = "shippinginfo.do?uAction=search&itemId=" + itemId;
	
	 if ($v("companyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
   	
	parent.parent.openIFrame("dotShippingInfo"+itemId,loc,""+messagesData.shipinfo+" "+itemId,"","N");
}

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
}

function countChecked() {
	// *note: requires TOTAL_ROWS to be defined prior to the inclusion of
	// this
	var totallines = $v("totalLines");
	// first do rows
	var totalChecked = 0;
	var okValue;
	for ( var p = 1; p <= totallines; p++) {
		try {
			okValue = gridCellValue(beangrid, p, "ok");
			if (okValue == 'true') {
				totalChecked++;
			}
		}
		catch (ex) {
		}
	}
	return totalChecked;
}

function viewPickableUnits() {
	var checked = countChecked();
	if (checked == 0) {
		alert(messagesData.pleaseSelect);
		return false;
	}
	else {
		var action = document.getElementById("action");
		action.value = 'pickableUnits';
		document.genericForm.target = '';
		beangrid.parentFormOnSubmit();
		parent.showPleaseWait();
		document.genericForm.submit();
		return true;
	}
}