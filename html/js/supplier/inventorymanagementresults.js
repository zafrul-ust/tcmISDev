var beanGrid;
// Global variable: specially useful for right-click
var selectedRowId = null;
// Set this to be false if you don't want the grid width to resize based on window size.
var resizeGridWithWindow = true;
var dhxWins;

function resultOnLoadWithGrid() {
	try {
		parent.closeTransitWin();
	} catch (e) {}
	
	try {
		parent.$("updateResultLink").style["display"] = showUpdateLinks ? "" : "none";
	} catch (ex) {}
	
	initGridWithGlobal(gridConfig);
	$(gridConfig.divName).style["display"] = $v("totalLines") > 0 ? "" : "none";
	
	displayGridSearchDuration();
	setResultFrameSize();
}

function selectRow(rowId, colId) {
	selectedRowId = rowId;
}

function printInventoryLabel() {
	$("uAction").value = "printInventoryLabel";
	selectPrintQty();
}

function selectPrintQty() {
	openWinGeneric("selectprintqty.do","selectPrintQty","300","200","yes","100","100");
}

function printQtyChanged(printQty) {
	$("labelQuantity").value = printQty;
	//only submit the currently selected row
	if (beanGrid != null) {
		var FormSubmitOnlySelected = beanGrid.FormSubmitOnlySelected;
		beanGrid.submitOnlySelected(true);
		beanGrid.parentFormOnSubmit();
		//reset the status after form has been prepared
		beanGrid.submitOnlySelected(FormSubmitOnlySelected);
	}
	openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_GenerateLabels','650','600','yes');
	document.genericForm.target = "_GenerateLabels";
	document.genericForm.submit();
	
	setTimeout('window.status="";',5000);
}

function reloadPreviousSearch() {
	parent.$("startSearchTime").value = new Date().getTime();
	$("uAction").value = 'search';
	document.genericForm.target = 'resultFrame';
	document.genericForm.submit();
	parent.showTransitWin();
}

function isInventoryType(rowId) {
	return "INV" === cellValue(rowId, "transactionType");
}

function isPOType(rowId) {
	return "PO" === cellValue(rowId, "transactionType");
}

function buildRightClickOption(rowId, colId) {
	selectedRowId = rowId;
	
	if (colId >= beanGrid.getColIndexById("transactionType")) {
		var vitems = new Array();
		if (isInventoryType(rowId) && !isInventoryDepleted(rowId)) {
			//if selected row is of type INV, allows adding adjustment for inventory qty and, if it has not been received by a PO, editing inventory's lot and expiration date
			vitems[vitems.length] = "text=" + messagesData.addAdjustment + ";url=javascript:parent.openAddAdjustmentPopup('Y');";
			if (!isWhitespace(cellValue(rowId, "inventoryId")))
				vitems[vitems.length] = "text=" + messagesData.editInventory + ";url=javascript:parent.openInventoryPopup('Y', 'editInventory');";
			if (cellValue(rowId, "canConvertPartNo") == "Y")
				vitems[vitems.length] = "text=" + messagesData.convertInventory + ";url=javascript:parent.openConvertTransactionPopup('Y', 'convertInventory');";
		} else if (isPOType(rowId) && !isPOReceived(rowId)) {
			vitems[vitems.length] = "text=" + messagesData.receivePOInventory + ";url=javascript:parent.openInventoryPopup('Y', 'receivePOInventory');";
			vitems[vitems.length] = "text=" + messagesData.editPO + ";url=javascript:parent.openPOPopup('Y', 'editPO');";
			if (cellValue(rowId, "canConvertPartNo") == "Y")
				vitems[vitems.length] = "text=" + messagesData.convertPO + ";url=javascript:parent.openConvertTransactionPopup('Y', 'convertPO');";
		}
		if (!isWhitespace(cellValue(rowId, "inventoryId")) && cellValue(rowId, "inventoryId") != "-1")
			vitems[vitems.length] = "text=" + messagesData.printLabels + ";url=javascript:printInventoryLabel();";
     	vitems[vitems.length ] = isWhitespace(cellValue(rowId, "notes")) ? 'text=<font color="gray">' + messagesData.showNotes + '</font>;url=javascript:doNothing();' : "text=" + messagesData.showNotes + ";url=javascript:showNotes();";
		
		if (vitems.length > 0) {
			replaceMenu('typeRightClickMenu', vitems);
			toggleContextMenu("typeRightClickMenu");
		} else
			toggleContextMenu("contextMenu");
	} else
		toggleContextMenu("locationLevelRightClickMenu");
}

function isInventoryDepleted(rowId) {
	if (isInventoryType(rowId)) {
		if (cellValue(rowId, "status").toLowerCase() == "depleted")
			return true;
		else
			return false;
	} else
		throw "Given row is not of type Inventory";
}

function isPOReceived(rowId) {
	if (isPOType(rowId)) {
		if (cellValue(rowId, "status").toLowerCase() == "closed")
			return true;
		else
			return false;
	} else
		throw "Given row is not of type PO";
}

function showMinMaxHistory() {
	var loc = "showinventoryminmaxhistory.do?uAction=showMinMaxHistory"
			+ "&inventoryLevelId=" + cellValue(selectedRowId, "inventoryLevelId")
			+ "&titleDesc=" + URLEncode(cellValue(selectedRowId, "locationDesc") + "|" + cellValue(selectedRowId, "catPartNo"));
    openWinGeneric(loc,'_showMinMaxHistory','650','600','yes');
}

function showNotes() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
	
	$("notesWin").innerHTML = cellValue(selectedRowId, "notes");
	$("notesWin").style.display = "";
	
	var notesWin;
	if (!dhxWins.window("notesWin")) {
		// create window first time
		notesWin = dhxWins.createWindow("notesWin", 0, 0, 400, 200);
		notesWin.setText(messagesData.notes);
		notesWin.clearIcon();  // hide icon
		notesWin.denyResize(); // deny resizing
		notesWin.denyPark();   // deny parking
		notesWin.attachObject("notesWin");
		notesWin.attachEvent("onClose", function(notesWin) {
			closeNotes();
		});
		notesWin.center();
		notesWin.setModal(true);
	} else {
		notesWin = dhxWins.window("notesWin");
		notesWin.center();
		notesWin.setModal(true);
		notesWin.show();
	}
}

function closeNotes() {
	if (dhxWins.window("notesWin")) {
		dhxWins.window("notesWin").setModal(false);
		$("notesWin").style.display = "none";
		dhxWins.window("notesWin").hide();
	}
}

function replaceMenu(menuname, menus) {
	var i = mm_returnMenuItemCount(menuname);
	for (; i > 1; i--)
		mm_deleteItem(menuname, i);

	for (i = 0; i < menus.length;) {
		var str = menus[i];

		i++;
		mm_insertItem(menuname, i, str);
		// delete original first item.
		if (i == 1)
			mm_deleteItem(menuname, 1);
	}
}

function doNothing() {}