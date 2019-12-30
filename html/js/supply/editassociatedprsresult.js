var mygrid;
var selectedRowId;
// Set this to be false if you don't want the grid width to resize based on
// window size.
var resizeGridWithWindow = true;

function myOnLoad() {
	if (showErrorMessage)
		parent.showErrorMessages();
	else if ($v("updateSuccess") === "true")
		parent.refreshParentGrid($v("isFullyDisassociated"));
	
	var totalLines = $("totalLines").value;
	if (totalLines > 0) {		
		document.getElementById('editAssociatedPrsViewBean').style["display"]="";		
		initGridWithGlobal(gridConfig);		
		if (showUpdateLinks) {
			parent.document.getElementById("mainUpdateLinks").style["display"] = "";
		} else {
			parent.document.getElementById("mainUpdateLinks").style["display"] = "none";
		}		
	}
	/*This dislpays our standard footer message*/
	displayGridSearchDuration();
	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();	
} //end of method

function isValidDisassociate(rowId) {
	if (!cell(rowId, "ok").isChecked()) {
		if (cellValue(rowId, "everConfirmed") === 'Y') {
			try {
				return parent.opener.checkSelectedLineQuantity(0) > 0 ? false : true;
			} catch (e) {
				return true;
			}
		}
	}
	
	return true;
}

function checkUpdateDeleteFlag(rowId) {
	//when result grid is first loaded, 'changed' value is 'N'. Check/uncheck will flip the value accordingly.
	var isNextChangedValueY = cellValue(rowId, "changed") === 'Y' ? false : true;
	
	if ($v("userAction") == "editassociatedPRs") {
		var msg = "";
		var noCheckedRows = 0;
		for (var curRowId = 1; curRowId <= mygrid.getRowsNum(); curRowId++)
			if (cell(curRowId, "ok").isChecked()) {
				noCheckedRows++;
				if (curRowId == rowId) {
					if (cellValue(curRowId, "itemId") != $v("itemId"))
						msg += "\n\t" + messagesData.item;
				} else {
					if (cellValue(curRowId, "inventoryGroup") != cellValue(rowId, "inventoryGroup"))
						msg += "\n\t" + messagesData.inventoryGroup;
					if (cellValue(curRowId, "branchPlant") == "2202" || cellValue(rowId, "branchPlant") == "2202")
						if (cellValue(curRowId, "clientPO") != cellValue(rowId, "clientPO")
							&& (cellValue(curRowId, "company") == "SWA" || cellValue(rowId, "company") == "SWA"))
							msg += "\n\t" + messagesData.customerPOForDropShip;
				}
			}
		
		if (msg.trim().length == 0) {
			if (noCheckedRows == 0 && !isValidDisassociate(rowId))
				msg = messagesData.disassociatePrsWithNonzeroQty;
		} else
			msg = messagesData.selectDifferentPrs + "\n" + msg;
		
		if (msg.trim().length > 0) {
			alert(msg);
		    setCellValue(rowId, "ok", !cell(rowId, "ok").isChecked());
			isNextChangedValueY = !isNextChangedValueY;
		}
		
	}
	
	setCellValue(rowId, "changed", isNextChangedValueY ? "Y" : "N");
}

function updateBuyOrdersToPO() {
	if (validationForUpdate()) {
		document.genericForm.target = '';
		var userAction = $v('userAction');
		if(userAction == 'addBuyOrdersToPO')
			$('uAction').value = 'UpdateaddBuyOrdersToPO';
		else 
			$('uAction').value = 'UpdateEditAssociatedPRs';		
		// Reset search time for update
		var now = new Date();
		var startSearchTime = parent.document.getElementById("startSearchTime");
		startSearchTime.value = now.getTime();	
		parent.showPleaseWait(); // Show "please wait" while updating
		if (mygrid != null) {
			// This function prepares the grid dat for submitting top the server
			mygrid.parentFormOnSubmit();
		}		
		document.genericForm.submit(); // Submit the form
	}
}

// validate the whole grid
function validationForUpdate() {
	if ($v("userAction") == "editassociatedPRs") {
		//we check the row for disassociation again in case user unchecks row when it is valid then make change on another window 
		for (var curRow = 1; curRow <= mygrid.getRowsNum(); curRow++)
			if (!isValidDisassociate(curRow)) {
				alert(messagesData.disassociatePrsWithNonzeroQty);
				return false;
			}
	} else if ($v("userAction") == "addBuyOrdersToPO") {
		for (var curRow = 1; curRow <= mygrid.getRowsNum(); curRow++)
			if (cell(curRow, "ok").isChecked())
				return true;
		
		return false;
	}
	
	return true;
}
