windowCloseOnEsc = true;
var beanGrid;
var gridWidth;

function resultOnLoadWithGrid() {
	document.getElementById("transitPage").style["display"] = "none";
	document.getElementById("resultGridDiv").style["display"] = "";
	
	try {
		if (showUpdateLinks) // Dont show any update links if the user does not have permissions
			parent.document.getElementById("updateResultLink").style["display"] = "";
		else
			parent.document.getElementById("updateResultLink").style["display"] = "none";
	} catch (ex) {}
	
	initGridWithGlobal(gridConfig);
	
	var totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		document.getElementById(gridConfig.divName).style["display"] = "";
		document.getElementById("resultsPageTable").style["display"] = "none";
	} else {
		document.getElementById(gridConfig.divName).style["display"] = "none";
		document.getElementById("resultsPageTable").style["display"] = "";
	}

	/* This display our standard footer message */
	displayNoSearchSecDuration();

	/*
	 * It is important to call this after all the divs have been turned on or
	 * off.
	 */
	myResize();
	
	if (showErrorMessage)
		showErrorMessages();
}

function resizeMainPage() {
	var ht = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
	var wt = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	var mainPageStyle = document.getElementById("resultsPage").style;
	mainPageStyle.height = ht + "px";
	mainPageStyle.width = wt + "px";
	//40 came from manual test
	gridWidth = wt - 40;
}

function myResize() {
	try {
		resizeMainPage();
		$(beanGrid.entBox.id).style.width = gridWidth + "px";
		//need this to fit the scrolls (if exist) within view
		beanGrid.setSizes();
		updateColumnWidths(beanGrid);
	} catch (e) {}
}

function addNewEmissionPoint() {
	if (document.getElementById(gridConfig.divName).style["display"] == "none") {
		document.getElementById(gridConfig.divName).style["display"] = "";
		document.getElementById("resultsPageTable").style["display"] = "none";
	}
	
	var newData = new Array();
	var j = 0;
	newData[j++] = 'Y';								//permission
	newData[j++] = 'Y';								//facEmissionPointPermission
	newData[j++] = 'Y';								//appEmissionPointPermission
	newData[j++] = window['active'][0].value;		//active
	newData[j++] = '';								//facEmissionPoint
	newData[j++] = '';								//appEmissionPoint
	newData[j++] = '';								//appEmissionPointDesc
	newData[j++] = true;							//isNew
	newData[j++] = true;							//changed
	newData[j++] = window['active'][0].value;		//originalActive
	newData[j++] = '';								//originalAppEmissionPointDesc

	var newRowId = beanGrid.getRowsNum() + 1;
	beanGrid.addRow(newRowId, newData, beanGrid.getRowsNum());
	beanGrid.selectRowById(newRowId, null, true, true);
}

function updateEmissionPoint() {
	if (invalidateData() && beanGrid.getRowsNum() != 0) {
		try {
		    document.getElementById('uAction').value="update";
		    document.genericForm.target= '';
		    beanGrid.parentFormOnSubmit();
		    showPleaseWait();
		    document.genericForm.submit();
		} catch (e) {
			stopPleaseWait();
		}
	}
}

function invalidateData() {
	try {
		var errorMsg = "";
		for (var rowId = 1; rowId <= beanGrid.getRowsNum(); rowId++) {
			var curErrorMsg = "";
			if (isBlankString(gridCellValue(beanGrid, rowId, "facEmissionPoint")))
				curErrorMsg += "\t" + messagesData.facilityEmissionPoint + "\n";
			if (isBlankString(gridCellValue(beanGrid, rowId, "appEmissionPoint")))
				curErrorMsg += "\t" + messagesData.workAreaEmissionPoint + "\n";
			
			if (!isBlankString(curErrorMsg))
				errorMsg += messagesData.rowX.replace("{0}", rowId) + ": " + "\n" + curErrorMsg;
		}
		
		if (!isBlankString(errorMsg)) {
			alert(messagesData.enterValidValues + "\n" + errorMsg);
			return false;
		}
	} catch (e) {
		alert(messagesData.genericError);
		return false;
	}
	
	return true;
}

function buildRightClickOption(rowId, colId) {
	if (gridCellValue(beanGrid, rowId, "isNew")) {
		var vitems = new Array();
		vitems[0] = "text=" + messagesData.deleteStr + ";url=javascript:deleteSelectedRow(" + rowId + ");";
		replaceMenu('rightClickMenu', vitems);
		toggleContextMenu('rightClickMenu');
	} else
		toggleContextMenu('contextMenu');
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

function deleteSelectedRow(rowId) {
	beanGrid.deleteRow(rowId);
	
	if (beanGrid.getRowsNum() == 0) {
		document.getElementById(gridConfig.divName).style["display"] = "none";
		document.getElementById("resultsPageTable").style["display"] = "";
	}
}

function compareToOriginalVal(rowId, colId) {
	//if a new row is added, we handle submission check while invalidating data, since 
	//columns that affect submission at that time are fapEmissionPoint and appEmissionPoint, which don't go here
	if (gridCellValue(beanGrid, rowId, "isNew"))
		return;
	
	var originalColId = "original" + colId.charAt(0).toUpperCase() + colId.slice(1);
	if (gridCellValue(beanGrid, rowId, colId) != gridCellValue(beanGrid, rowId, originalColId))
		setGridCellValue(beanGrid, rowId, "changed", true);
	else
		setGridCellValue(beanGrid, rowId, "changed", false);
}

function isBlankString(s) {
	try {
		if (s == null || s.trim().length == 0)
			return true;
		else
			return false;
	} catch (e) {
		return false;
	}
}