// Set this to be false if you don't want the grid width to resize based on window size.
var resizeGridWithWindow = true;

// Global variable: specially useful for right-click
var selectedRowId = null;

/*
 * Grid event OnBeforeSelect function Save the row class of currently selected
 * row, before class changes.
 */
var saveRowClass = null;
function doOnBeforeSelect(rowId, oldRowId) {
	// set the color for previous row
	if (oldRowId != 0) {
		setRowClass(oldRowId, saveRowClass);
	}
	saveRowClass = getRowClass(rowId);
	if (saveRowClass.search(/haas/) == -1
			&& saveRowClass.search(/Selected/) == -1)
		overrideDefaultSelect(rowId, saveRowClass);

	return true;
}

function doOnRowSelected(rowId, cellInd) {
	var columnId = beanGrid.getColumnId(cellInd);
	selectedRowId = rowId;
}

function selectRightclick(rowId, cellInd) {
	beanGrid.selectRowById(rowId, null, false, false);
	// The right click event needs to call selectRow function.
	doOnRowSelected(rowId, cellInd);
	// Show right-click menu
	toggleContextMenu('rightClickMenu');
}

/*
 * function called on submit to validate data entered in each row
 * 
 */
function validationforUpdate() {
	var rowsNum = beanGrid.getRowsNum();
	var oneChecked = false;
	for ( var p = 1; p < (rowsNum + 1); p++) {
		// only validate lines with permission
		if (cellValue(p, "permission") == "Y")
		{
			if (validateLine(p) == false) {
				beanGrid.selectRowById(p, null, false, false);
				return false;
			}
			else if ($('okDoUpdate'+ p).checked == true)
				oneChecked = true;
		}
	}
	if(!oneChecked)
		{
			alert(messagesData.noRowSelected);
			return false;
		}
	
	return true;
}

/*
 * Function called when the search button is clicked in the Default Buyer column.
 * sourcePage parameter passed
 */
var rId;
var personnelType;
function getUsersList(rowId,pType) {
	personnelType = pType;
	rId = rowId;
	loc = "searchpersonnelmain.do?";
	parent.children[parent.children.length] = openWinGeneric(loc,"changepersonnel","600","450","yes","50","50","20","20","no");
	parent.showTransitWin(messagesData.searchPersonnel);
}

/*
 * Function called when the clear button is clicked in the Default Buyer column.
 */
function clearUserEntry( rowId ) {
	if(personnelType == 'buyer')
	{
		beanGrid.cells(rowId,beanGrid.getColIndexById("defaultBuyerId")).setValue("");
		beanGrid.cells(rowId,beanGrid.getColIndexById("defaultBuyerName")).setValue("");
	}
    else
	{
		beanGrid.cells(selectedRowId,beanGrid.getColIndexById("csrName")).setValue("");
		beanGrid.cells(selectedRowId,beanGrid.getColIndexById("csrPersonnelId")).setValue("");
	}
	
}

/*
 * Function called from searchpersonnel.js to pass the selected user id.
 * Used to set the value of the buyer id in the selected row. 
 */
function personnelChanged(userId,userName)
{
	if(personnelType == 'buyer')
		{
			beanGrid.cells(selectedRowId,beanGrid.getColIndexById("defaultBuyerName")).setValue(userName);
			beanGrid.cells(selectedRowId,beanGrid.getColIndexById("defaultBuyerId")).setValue(userId);
		}
	else
		{
			beanGrid.cells(selectedRowId,beanGrid.getColIndexById("csrName")).setValue(userName);
			beanGrid.cells(selectedRowId,beanGrid.getColIndexById("csrPersonnelId")).setValue(userId);
		}
	parent.closeTransitWin();
}


/*
 * Validates entry for given row.
 * 
 * rowId: id of the row to validate
 */
function validateLine(rowId) {
	var errorMessage = messagesData.validvalues + "\n";
	var count = 0;

	var minGrossMargin = cellValue(rowId, "minimumGrossMargin");
	if (!isInteger(minGrossMargin, true)) { 
		errorMessage += "\n" + messagesData.maxGrossMargin;
		count = 1;
	}

	var maxGrossMargin = cellValue(rowId, "maximumGrossMargin");
	if (!isInteger(maxGrossMargin, true)) { 
		errorMessage += "\n" + messagesData.minGrossMargin;
		count = 1;
	}
	
	var orderQtyUpdate = cellValue(rowId, "orderQtyUpdateOnReceipt");
	if (!isFloat(orderQtyUpdate, true) || orderQtyUpdate == '') { 
		errorMessage += "\n" + messagesData.updateOrderOnReceipt;
		count = 1;
	}
	
	var defaultSalesMargin = cellValue(rowId, "defaultSalesMargin");
	if (!isInteger(defaultSalesMargin, true)) { 
		errorMessage += "\n" + messagesData.defaultSalesMargin;
		count = 1;
	}
	
	if (count > 0) {
		alert(errorMessage);
		return false;
	}
	return true;
}

function onChangeDefaultSalesMargin(rowId, columnid) {
	var errorMessage = messagesData.validvalues + "\n";
	var defaultSalesMargin = cellValue(rowId, "defaultSalesMargin");
	if (!isInteger(defaultSalesMargin, true)) { 
		errorMessage += "\n" + messagesData.defaultSalesMargin;
		alert(errorMessage);
		return false;
	}
	return true;
}

function onChangeMinGrossMargin(rowId, columnid) {
	var errorMessage = messagesData.validvalues + "\n";
	var minGrossMargin = cellValue(rowId, "minimumGrossMargin");
	if (!isInteger(minGrossMargin, true)) { 
		errorMessage += "\n" + messagesData.minGrossMargin;
		alert(errorMessage);
		return false;
	}
	return true;
}

function onChangeMaxGrossMargin(rowId, columnid) {
	var errorMessage = messagesData.validvalues + "\n";
	var maxGrossMargin = cellValue(rowId, "maximumGrossMargin");
	if (!isInteger(maxGrossMargin, true)) { 
		errorMessage += "\n" + messagesData.maxGrossMargin;
		alert(errorMessage);
		return false;
	}
	return true;
}

function onChangeOrderQtyUpdateOnReceipt(rowId, columnid) {
	var errorMessage = messagesData.validvalues + "\n";
	var orderQtyUpdate = cellValue(rowId, "orderQtyUpdateOnReceipt");
	if (!isFloat(orderQtyUpdate, true)) { 
		errorMessage += "\n" + messagesData.updateOrderOnReceipt;
		alert(errorMessage);
		return false;
	}
	return true;
}

function submitUpdate() {
	if (validationforUpdate()) {

		document.genericForm.target = '';
		document.getElementById('uAction').value = 'update';

		parent.showPleaseWait(); // Show "please wait" while updating

		if (beanGrid != null)
			beanGrid.parentFormOnSubmit();
		document.genericForm.submit(); // back to server
	}
}
