var beanGrid;
var selectedRowId = null;

var resizeGridWithWindow = true;

function resultOnLoad() {
	try {
		// Dont show any update links if the user does not have permissions
		if (!showUpdateLinks) {
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		}
		else {
			parent.document.getElementById("updateResultLink").style["display"] = "";
		}
	}
	catch (ex) {
	}

	document.getElementById("mixtureManagementBean").style["display"] = "";
	try {
		initGridWithGlobal(gridConfig);
	}
	catch (ex) {
	}

	beanGrid.attachEvent("onBeforeSelect", doOnBeforeSelect);

	var totalLines = $v("totalLines") * 1 - 1;

	if (totalLines > 0)
		parent.document.getElementById("hasDataLink").style["display"] = "";

	var startSearchTime = parent.window.document.getElementById("startSearchTime");
	var now = new Date();
	var minutes = 0;
	var seconds = 0;
	// the duration is in milliseconds
	var searchDuration = now.getTime() - (startSearchTime.value * 1);
	if (searchDuration > (1000 * 60)) { // calculating minutes
		minutes = Math.round((searchDuration / (1000 * 60)));
		var remainder = searchDuration % (1000 * 60);
		if (remainder > 0) { // calculating seconds
			seconds = Math.round(remainder / 1000);
		}
	}
	else { // calculating seconds
		seconds = Math.round(searchDuration / 1000);
	}
	var footer = parent.document.getElementById("footer");
	if (minutes != 0) {
		footer.innerHTML = messagesData.recordFound + ": " + totalLines + " -- " + messagesData.searchDuration + ": " + minutes + " " + messagesData.minutes + " " + seconds + " " + messagesData.seconds;
	}
	else {
		footer.innerHTML = messagesData.recordFound + ": " + totalLines + " -- " + messagesData.searchDuration + ": " + seconds + " " + messagesData.seconds;
	}

	/* It is important to call this after all the divs have been turned on or off. */
	setResultFrameSize();
}

function selectRow(rowId, cellInd) {
	selectedRowId = rowId;

	// if(cellInd == beanGrid.getColIndexById("msdsNoDisplay"))
	// initMsdsAutocomplete(rowId);

	if ("deleteByMsds" == cellValue(rowId, "status") || "deleteByMixture" == cellValue(rowId, "status"))
		colorAllDeleted(rowId);

	if (oldRowId != 0)
		colorAllDeleted(oldRowId);
}

function selectRightclick(rowId, cellInd) {
	// beanGrid.selectRowById(rowId,null,false,false);

	selectRow(rowId, cellInd);

	var columnId = beanGrid.getColumnId(cellInd);

	vitems = new Array();

	vitems[vitems.length] = "text=" + messagesData.deletemixture + ";url=javascript:deletePart('deleteByMixture')";

	if ("deleteByMixture" != cellValue(rowId, "status") && cellInd > beanGrid.getColIndexById("mixtureName"))
		vitems[vitems.length] = "text=" + messagesData.addmsds + ";url=javascript:searchMSDS('addMSDS')";

	if (cellInd > beanGrid.getColIndexById("mixtureName"))
		vitems[vitems.length] = "text=" + messagesData.deletemsds + ";url=javascript:deletePart('deleteByMsds')";

	replaceMenu('rightClickMenu', vitems);
	toggleContextMenu('rightClickMenu');

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

function searchMSDS(action) {
	parent.showTransitWin(messagesData.searchmsds);
	loc = "searchmsdsmfgmain.do?callback=" + action + "&facilityId=" + $v("facilityId");

	openWinGeneric(loc, 'searcgMSDSMfg', "650", "500", "yes", "50", "50", "20", "20", "no");
}

function newMixture(m) {
	var ind = beanGrid.getRowsNum();
	var rowid = ind * 1 + 1;

	var rowData = [
					'Y', '', '', '', 'Y', m.customerMsdsNumber, m.manufacturer, m.materialId, m.materialDesc, parent.$v("companyId"), $v("facilityId"), '', 'newMixture'
	];

	var thisrow = beanGrid.haasAddRowToRowSpan(rowData, ind);

	beanGrid.haasRenderRow(rowid);
	beanGrid.selectRow(ind);

	parent.document.getElementById("hasDataLink").style["display"] = "";

	try {
		parent.closeTransitWin();
	}
	catch (ex) {
	}
}

function addMSDS(m) {
	var rowId = beanGrid.getSelectedRowId();
	var ind = beanGrid.getRowIndex(rowId);
	var parentIndex = beanGrid.haasGetRowSpanStart(rowId);
	var newLinePosition = beanGrid.haasGetRowSpanEndIndex(rowId);

	var newRowData = [
					'Y', cellValue(rowId, "mixtureId"), cellValue(rowId, "mixtureName"), '', 'Y', m.customerMsdsNumber, m.manufacturer, m.materialId, m.materialDesc, cellValue(rowId, "companyId"), cellValue(rowId, "facilityId"), '', 'newMsds'
	];
	beanGrid.haasAddRowToRowSpan(newRowData, newLinePosition, parentIndex);
	beanGrid.haasRenderRow(beanGrid.getRowId(parentIndex));

	beanGrid.selectRow(parentIndex);

	try {
		parent.closeTransitWin();
	}
	catch (ex) {
	}
}

function colorAllDeleted(oldRowId) {
	if ("deleteByMsds" == cellValue(oldRowId, "status")) {
		colorDeletedRow(oldRowId);
	}
	else if ("deleteByMixture" == cellValue(oldRowId, "status")) {
		var parentIndex = beanGrid.haasGetRowSpanStart(oldRowId);
		var endIndex = beanGrid.haasGetRowSpanEndIndex(oldRowId);

		for ( var index = parentIndex; index < endIndex; index++) {
			colorDeletedRow(beanGrid.getRowId(index));
		}
	}
}

function deletePart(action) {
	if ("deleteByMsds" == action) {
		cell(selectedRowId, "status").setValue("deleteByMsds");
		colorDeletedRow(selectedRowId);
	}
	else if ("deleteByMixture" == action) {
		var parentIndex = beanGrid.haasGetRowSpanStart(selectedRowId);
		var endIndex = beanGrid.haasGetRowSpanEndIndex(selectedRowId);

		for ( var index = parentIndex; index < endIndex; index++) {
			var currentRowId = beanGrid.getRowId(index);
			cell(currentRowId, "status").setValue("deleteByMixture");
			colorDeletedRow(currentRowId);
		}
	}
}

function markChanged(rowId, cellInd) {

	if ("newMixture" != cellValue(rowId, "status") && "newMsds" != cellValue(rowId, "status") && "deleteByMsds" != cellValue(rowId, "status") && "deleteByMixture" != cellValue(rowId, "status")) {
		beanGrid.cells(rowId, beanGrid.getColIndexById("status")).setValue("changed");
	}

}

function updateParts() {
	if (validationforUpdate()) {
		document.genericForm.target = '';
		$('uAction').value = 'update';

		var now = new Date();
		var startSearchTime = parent.document.getElementById("startSearchTime");
		startSearchTime.value = now.getTime();

		parent.showPleaseWait();

		if (beanGrid != null) {
			// This function prepares the grid dat for submitting top the server
			beanGrid.parentFormOnSubmit();
		}

		document.genericForm.submit(); // Submit the form
	}
}

// validate the whole grid
function validationforUpdate() {
	var rowsNum = beanGrid.getRowsNum();

	// This reflects the rowId we put in the JSON data
	for ( var rowId = 1; rowId <= rowsNum; rowId++) {
		if (!validateLine(rowId)) {
			// Select the failing line
			beanGrid.selectRowById(rowId, null, false, false);
			return false;
		}
	}

	return true;
}

// validate one line here
function validateLine(rowId, cellInd) {
	var errorMessage = "";
	var errorCount = 0;

	if ("N" == cellValue(rowId, "permission")) {
		return true;// Don't need to check read only rows
	}

	if ("deleteByMsds" == cellValue(rowId, "status") || "deleteByMixture" == cellValue(rowId, "status"))
		return true;

	var mixtureName = cellValue(rowId, "mixtureName");
	if (mixtureName == null || mixtureName.trim().length == 0) {
		errorMessage = errorMessage + "\n" + messagesData.validvalues + " " + messagesData.mixturename;
		errorCount = 1;
	}

	var msdsNo = cellValue(rowId, "msdsNo");
	if (msdsNo == null || msdsNo.trim().length == 0) {
		errorMessage = errorMessage + "\n" + messagesData.validvalues + " " + messagesData.msdsno;
		errorCount = 1;
	}

	var parentIndex = beanGrid.haasGetRowSpanStart(rowId);
	var endIndex = beanGrid.haasGetRowSpanEndIndex(rowId);
	var count = 0;

	for ( var index = parentIndex + 1; index <= endIndex; index++) {
		var status = cellValue(index, "status");
		// alert(index + " - '" + status + "'");
		if ("deleteByMsds" != status && "deleteByMixture" != status) {
			count++;
		}
	}

	if (count <= 1 * 1) {
		errorMessage = errorMessage + "\n" + messagesData.addmoremsds;
		errorCount = 1;
	}

	if (errorCount > 0) {
		alert(errorMessage);
		return false;
	}

	return true;
}

var oldRowId = 0;
function doOnBeforeSelect(rowId, oldRowId0) {
	oldRowId = oldRowId0;
	return true;
}

function colorDeletedRow(rowId) {
	if ("deleteByMsds" == cellValue(rowId, "status")) {
		beanGrid.haasSetColSpanStyle(rowId, 3, 11, "background-color: #BEBEBE; text-decoration:line-through;");
		$("separable" + rowId).disabled = true;
	}
	else if ("deleteByMixture" == cellValue(rowId, "status")) {
		beanGrid.haasSetColSpanStyle(rowId, 2, 11, "background-color: #BEBEBE; text-decoration:line-through;");
		$("separable" + rowId).disabled = true;
	}
}

/*
 * var msdsNoDisplayFlag = new Array(); function initMsdsAutocomplete(rowId) { if( msdsNoDisplayFlag[rowId] == undefined) { j$("#msdsNoDisplay"+rowId).livequery(function() { j$("#msdsNoDisplay"+rowId).autocomplete("getmsdsmfg.do",{ extraParams:
 * {facilityId: function() { return cellValue(rowId, "facilityId"); }}, width: 100, max: 200, // default value is 10 cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off. scrollHeight: 150,
 * formatItem: function(data, i, n, value) { return value.split("::")[1]; }, formatResult: function(data, value) { return value.split("::")[1]; } });
 * 
 * j$("#msdsNoDisplay"+rowId).bind('keyup',(function(e) { var keyCode = (e.keyCode ? e.keyCode : e.which);
 * 
 * if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab invalidateMsdsNoDisplayInput(rowId); }));
 * 
 * 
 * j$("#msdsNoDisplay"+rowId).result(log).next().click(function() { j$(this).prev().search(); j$(this).unbind(); });
 * 
 * function log(event, data, formatted) { $("msdsNoDisplay"+rowId).className = "inputBox";
 * 
 * beanGrid.cells(rowId,beanGrid.getColIndexById("msdsNo")).setValue(formatted.split("::")[0]); beanGrid.cells(rowId,beanGrid.getColIndexById("mfgDesc")).setValue(formatted.split("::")[2]);
 * beanGrid.cells(rowId,beanGrid.getColIndexById("materialDesc")).setValue(formatted.split("::")[3]); }
 * 
 * }); msdsNoDisplayFlag[rowId] = "true"; } }
 * 
 * 
 * function invalidateMsdsNoDisplayInput(rowId) { var msdsNoDisplay = document.getElementById("msdsNoDisplay"+rowId);
 * 
 * if (msdsNoDisplay.value.length == 0) { msdsNoDisplay.className = "inputBox"; }else { msdsNoDisplay.className = "inputBox red"; }
 * 
 * beanGrid.cells(rowId,beanGrid.getColIndexById("msdsNo")).setValue(""); }
 */
