var beangrid;

// Global variable: specially useful for right-click
var selectedRowId = null;

// Set this to be false if you don't want the grid width to resize based on
// window size.
var resizeGridWithWindow = true;

function resultOnLoad() {
	try {
		if (!showUpdateLinks) /*
								 * Dont show any update links if the user does not have permissions
								 */
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		else
			parent.document.getElementById("updateResultLink").style["display"] = "";
	}
	catch (ex) {
	}
	if (updateSuccess) {
		alert(messagesData.updateSuccess);
	}
}

function validateLine(rowId) {
	var url = "containervalidation.do?uAction=containerInentoriedDupCheck&&callback=returnValidateContainerId&";
	url += "itemId=" + cellValue(rowId, "itemId");
	url += "&containerId=" + cellValue(rowId, "receiptId") + "-" + cellValue(rowId, "containerId");
	url += "&rowId=" + rowId;

	callToServer(url);
}

function returnValidateContainerId(val, containerId, receiptId, itemId, rowId) {
	if (val == "true") {
		mygrid.selectRowById(rowId, true);
		alert(formatMessage(messagesData.invalidContainer, messagesData.containerid, containerId));
		mygrid.cells(rowId, mygrid.getColIndexById("containerId")).setValue("");
	}
}

function save() {
	if (validationforSave()) {
		$("uAction").value = "save";
		parent.showPleaseWait();
		if (mygrid != null) {
			mygrid.parentFormOnSubmit();
		}
		document.genericForm.submit();
	}
}

function validationforSave() {

	var rowsNum = mygrid.getRowsNum();

	for ( var p = 1; p < (rowsNum + 1); p++) {
		if (cellValue(p, "containerId") != null && cellValue(p, "containerId").trim().length > 0 && (checkAllInput(p) == false || checkedDupInput(p) == false || !validateRow(p))) {
			mygrid.selectRowById(p, null, false, false);
			return false;
		}
	}

	return true;
}

function validateRow(rowId) {
	var errorCount = 0;
	var errorMessage = messagesData.validvalues + "\n";

	if (!cellValue(rowId, "usageLoggingReq")) {
		if (cellValue(rowId, "defaultPartType") == 'N' && cellValue(rowId, "defaultSubstrateCode") == 'SELECT')
			errorCount++;

		if (cellValue(rowId, "defaultPartType") == 'SELECT' || (cellValue(rowId, "defaultPermitId") == 'SELECT' || cellValue(rowId, "defaultApplicationMethodCod") == 'SELECT'))
			errorCount++;

		if (errorCount > 0) {
			alert(messagesData.defaultvalusforall);
			return false;
		}
	}
	return true;
}

function checkAllInput(checkedRowId) {
	var parentIndex = mygrid.haasGetRowSpanStart(checkedRowId);
	var endIndex = mygrid.haasGetRowSpanEndIndex(checkedRowId);

	var startRowId = mygrid.getRowId(parentIndex);
	for ( var index = parentIndex + 1; index < endIndex; index++) {
		var rowId = mygrid.getRowId(index);
		mygrid.cells(rowId, mygrid.getColIndexById("defaultPartType")).setValue(cellValue(startRowId, "defaultPartType"));
		mygrid.cells(rowId, mygrid.getColIndexById("defaultPermitId")).setValue(cellValue(startRowId, "defaultPermitId"));
		mygrid.cells(rowId, mygrid.getColIndexById("defaultApplicationMethodCod")).setValue(cellValue(startRowId, "defaultApplicationMethodCod"));
		mygrid.cells(rowId, mygrid.getColIndexById("defaultSubstrateCode")).setValue(cellValue(startRowId, "defaultSubstrateCode"));
	}

	for ( var index = parentIndex; index < endIndex; index++) {
		var rowId = mygrid.getRowId(index);
		if (cellValue(rowId, "containerId") == null || cellValue(rowId, "containerId").trim().length == 0) {
			alert(messagesData.inputcontaineridforsamekit);
			return false;
		}
	}
}

function checkedDupInput(checkedRowId) {
	var rowsNum = mygrid.getRowsNum();
	var checkedContainerId = cellValue(checkedRowId, "receiptId") + "-" + cellValue(checkedRowId, "containerId").trim();

	for ( var k = checkedRowId + 1; k < (rowsNum + 1); k++) {

		if (cellValue(k, "receiptId") != null && cellValue(k, "containerId").trim().length > 0) {
			compContainerId = cellValue(k, "receiptId") + "-" + cellValue(k, "containerId").trim()

			if (compContainerId == checkedContainerId) {
				mygrid.selectRowById(checkedRowId, null, false, false);
				alert(messagesData.duplicatecontainerid);
				return false;
			}
		}

	}

	return true;
}

var initializedRows = new Array();
var index = 0;
function initRow(rowId) {
	if (!initializedRows[rowId]) {
		initializedRows[rowId] = true;
		if ("Y" == cellValue(rowId, "defaultPartTypePermission")) {
			toggleSubstrate(rowId);
		}
		// if (cellValue(rowId, "usageLoggingReq") && cellValue(rowId, "solvent").endsWith("true")) {
		// alert(rowId + " is a solvent and OTR");
		// }
	}
}

function toggleSubstrate(rowId) {
	try {
		if (cellValue(rowId, "defaultPartType") != "N") {
			$("defaultSubstrateCode" + rowId).disabled = true;
			$("defaultSubstrateCode" + rowId).selectedIndex = 0;
		}
		else {
			$("defaultSubstrateCode" + rowId).disabled = false;
		}
	}
	catch (ex) {
	}
}
