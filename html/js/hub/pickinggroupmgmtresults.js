var resizeGridWithWindow = true;

var selectedRowId = null;

function resultOnLoad() {
	totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		document.getElementById("pickingGroupData").style["display"] = "";
		initGridWithGlobal(gridConfig); 
	}
	else {
		document.getElementById("pickingGroupData").style["display"] = "none";
	}
	
	displayGridSearchDuration();
	setResultFrameSize();
}

function addPickingGroupRow() {
	if ($v("sourceHub").length == 0) {
		alert(messagesData.pickinggroupselecthub);
		return false;
	}
	else {
		var selectedGrid = window["beanGrid"];
		var id = selectedGrid.getRowsNum() + 1;
		selectedGrid.addRow(id, ['Y', '', $v("sourceHub"), '', true, '', '', ''], id - 1);
		selectedGrid.selectRowById(id);
	}
}

function validateSubmission() {
	var numRows = beanGrid.getRowsNum();
	for (var rowId = 1; rowId <= numRows; rowId++) {
		var pickingGroupId = cellValue(rowId,"pickingGroupId")
		var pickingGroupName = cellValue(rowId, "pickingGroupName");
		
		if (pickingGroupId != "" && pickingGroupName == "") {
			alert(messagesData.pickinggroupnameerror);
			return false;
		}
	}
	return true;
}

function submitUpdate() {
	if (validateSubmission()) {
		parent.showPleaseWait();
		$("uAction").value = "update";
		beanGrid.parentFormOnSubmit();
		document.genericForm.submit();
	}
}

function setRowStatusColors(rowId) {
	var rowIndex = beanGrid.getRowIndex(rowId)+1;
	if (rowId) {
		var checkbox = $("active"+rowIndex);
		if (checkbox != null && ! checkbox.checked) {
			beanGrid.haasSetColSpanStyle(rowId, 0, 8, "background-color: #727272;");
		}
	}
}