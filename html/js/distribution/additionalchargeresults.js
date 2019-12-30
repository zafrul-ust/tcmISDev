/*
 * Grid event onRowSelect function
 */

function selectRow(rowId, cellId) {
	validateGrid();
	var columnId = mygrid.getColumnId(cellId);
	switch (columnId) {
		case "headerChargeType":
		case "lineChargeType":
		case "defaultPrice":
		case "defaultCurrencyId":
			markRowChanged(rowId);
		default:
	}
}

function markRowChanged(rowId) {
	mygrid.cellById(rowId, mygrid.getColIndexById("updated")).setValue("true");
}

function updateCharges() {
	if (validateGrid()) {
		document.genericForm.target = '';
		document.getElementById('uAction').value = 'update';

		parent.showPleaseWait();

		if (mygrid != null) {
			// Call this function to send the grid data back to the
			// server
			mygrid.parentFormOnSubmit();
		}

		document.genericForm.submit();
	}
}

// validate the whole grid
function validateGrid() {
	var rowsNum = mygrid.getRowsNum();

	for ( var lineNum = 1; lineNum <= (rowsNum); lineNum++) {
		if (!validateLine(lineNum)) {
			return false;
		}
	}

	return true;
}

// validate one line here
function validateLine(rowId) {
	// If not modified, don't validate
	if (cellValue(rowId, "updated") != "true") {
		return true;
	}
	else {
		var price = cellValue(rowId, "defaultPrice");
		if (price != null && price.length > 0 && !isFloat(price)) {
			// Select the row with the error and grab the cursor there as well
			mygrid.selectRowById(rowId, null, false, false);
			document.getElementById("defaultPrice" + rowId).focus();
			alert(messagesData.notFloat);
			return false;
		}
	}

	return true;
}