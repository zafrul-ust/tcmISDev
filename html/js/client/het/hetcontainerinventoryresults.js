var beangrid;

// Global variable: specially useful for right-click
var selectedRowId = null;

// Set this to be false if you don't want the grid width to resize based on window size.
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
}

function transfer() {
	if (validationforUpdate()) {
		$("uAction").value = "Transfer";
		parent.showPleaseWait();
		if (mygrid != null) {
			mygrid.parentFormOnSubmit();
		}
		document.genericForm.submit();
	}
}
function update() {
	if (validationforUpdate("update")) {
		$("uAction").value = "update";
		parent.showPleaseWait();
		if (mygrid != null) {
			mygrid.parentFormOnSubmit();
		}
		document.genericForm.submit();
	}
}

function deleteRecord() {
	if (validationforUpdate("delete") && confirm(messagesData.suredeletetherecord)) {
		var rowsNum = mygrid.getRowsNum() + 1;
		for ( var p = 1; p < rowsNum; p++) {
			if (cellValue(p, "okDoUpdate") == "true") {
				if (messagesData.indefinite == cellValue(p, "expirationDate")) {
					setCellValue(p, "expirationDate", "01-Jan-3000");
				}
			}
		}
		$("uAction").value = "Delete";
		parent.showPleaseWait();
		if (mygrid != null) {
			mygrid.parentFormOnSubmit();
		}
		document.genericForm.submit();
	}
}

function validationforUpdate(mode) {
	var errMsg = "";

	var rowsNum = mygrid.getRowsNum() + 1;
	var selectedRowCount = 0;
	for ( var p = 1; p < rowsNum; p++) {
		if (cellValue(p, "okDoUpdate") == "true") {
			selectedRowCount++;
			if (mode == "update") {
				var quantity = cellValue(p, "amountRemaining");
				if (("" + quantity).length == 0) {
					errMsg += messagesData.amountRequired + "\n";
				}
				else if (!isFloat(quantity, false) || quantity + 1.1 < 1.1) {
					errMsg += messagesData.amountNumber + "\n";
				}

			}
			else if (mode == "delete") {
				if (("" + cellValue(p, "deletionCode")).length == 0) {
					errMsg += messagesData.reasonRequired + "\n";
				}
				else {
					var reason = $("deletionCode" + (p));
					cell(p, "deletionDesc").setValue(reason.options[reason.selectedIndex].text);
				}
			}
			if (errMsg.length > 0) {
				mygrid.selectRow(p - 1);
				alert(errMsg);
				return false;
			}
		}
	}

	if (selectedRowCount == 0) {
		alert(messagesData.pleaseselectarowforupdate);
		return false;
	}
	else
		return true;
}

function selectRow(rowId, cellInd) {
	selectedRowId = rowId;
	var workArea = $("applicationId" + rowId);
	if (workArea != null) {
		getWorkAreasForTransfer(rowId);
	}

}

var loadedWorkAreas = {};

function getWorkAreasForTransfer(rowId) {
	if (!(rowId in loadedWorkAreas)) {
		loadedWorkAreas[rowId] = rowId;
		url = "hetcontainerinventoryresults.do";
		url += "?uAction=getTransferWorkAreas";
		url += "&facilityId=" + $v('facilityId');
		url += "&materialId=" + cellValue(rowId, "materialId");
		url += "&rowId=" + rowId;
		callToServer(url);
	}
}

function loadWorkArea(workAreas, rowId) {
	var workAreaFieldName = "applicationId" + rowId;
	var workArea = $(workAreaFieldName);
	var workAreaDesc = workArea.options[workArea.selectedIndex].text;
	var workAreaId = cellValue(rowId, "applicationId");
	for ( var i = workArea.length; i > 0; i--) {
		workArea[i] = null;
	}
	var i = 0;
	var workAreaFound = false;
	for (i = 0; i < workAreas.areas.length; i++) {
		if (workAreaId == workAreas.areas[i].applicationId) {
			workAreaFound = true;
		}
		setOption(i, workAreas.areas[i].applicationDesc, workAreas.areas[i].applicationId, workAreaFieldName, undefined, workAreaId == workAreas.areas[i].applicationId);
	}
	if (!workAreaFound) {
		setOption(i, workAreaDesc, workAreaId, workAreaFieldName, undefined, true);
	}
}
