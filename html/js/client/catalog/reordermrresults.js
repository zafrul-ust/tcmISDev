var beanGrid;
// Global variable: specially useful for right-click
var selectedRowId = null;
// Set this to be false if you don't want the grid width to resize based on window size.
var resizeGridWithWindow = true;

function resultOnLoadWithGrid() {
	try {
		parent.closeTransitWin();
	} catch (e) {}
	
	try {
		parent.$("updateResultLink").style["display"] = showUpdateLinks ? "" : "none";
	} catch (ex) {}
	
	initGridWithGlobal(gridConfig);
	if ($v("totalLines") > 0) {
		$(gridConfig.divName).style["display"] = "";
		beanGrid._haas_ok_column = "okDoUpdate";
		if ("Y" == $v("isFirstLoad")) {
			parent.$("facilityId").value = $v("facilityId");
			parent.facilityChanged($v("applicationId"), false);
			$("isFirstLoad").value = "N";
			parent.$("isFirstLoad").value = "N";
		}
		
		//'txt' doesn't support permission column
		for (var rowId = 1; rowId <= beanGrid.getRowsNum(); rowId++)
			if (getColPermission(beanGrid, rowId, "notes") == 'N')
				beanGrid.lockRow(rowId, true);
	} else
		$(gridConfig.divName).style["display"] = "none";
	
	if (showErrorMessage)
		parent.showErrorMessages();
	
	displayGridSearchDuration();
	setResultFrameSize();
}

function checkAll(checkboxname) {
	var checkall = $("chkAllOkDoUpdate");
	var rowsNum = beanGrid.getRowsNum(); // Get the total rows of the grid
	var columnId = beanGrid.getColIndexById(checkboxname);

	for (var rowId = 1; rowId <= rowsNum; rowId++)
		if (getColPermission(beanGrid, rowId, "okDoUpdate") == 'Y')
			cell(rowId, columnId).setChecked(checkall.checked);

	return true;
}

function showPleaseWait() {
	parent.showPleaseWait();
}

function stopPleaseWait() {
	parent.$("resultGridDiv").style["display"] = "";
	parent.stopPleaseWait();
}

function quantityChanged(rowId) {
	if (!isPositiveInteger(cellValue(rowId, "quantity"))) {
		alert(messagesData.xxPositiveInteger.replace("{0}", messagesData.quantity));
		$("quantity" + rowId).focus();
	} else
		setCellValue(rowId, "extPrice", cellValue(rowId, "catalogPrice") * cellValue(rowId, "quantity") * 1);
}

function multiplyQuantities(multiplier) {
	for (var rowId = 1; rowId <= beanGrid.getRowsNum(); rowId++)
		if (getColPermission(beanGrid, rowId, "quantity") == 'Y') {
			setCellValue(rowId, "quantity", cellValue(rowId, "quantity") * multiplier * 1);
			quantityChanged(rowId);
		}
}

function resetQuantityMultiplication() {
	for (var rowId = 1; rowId <= beanGrid.getRowsNum(); rowId++)
		if (getColPermission(beanGrid, rowId, "quantity") == 'Y') {
			setCellValue(rowId, "quantity", cellValue(rowId, "originalQuantity"));
			quantityChanged(rowId);
		}
}

function validateCheckOut() {
	if (isWhitespace($v("applicationId"))) {
		alert(messagesData.selectA.replace("{0}", messagesData.workArea));
		return false;
	} else if (isWhitespace($v("accountSysId"))) {
		alert(messagesData.selectA.replace("{0}", messagesData.accountSystem));
		return false;
	} else {
		var isAnyChecked = false;
		for (var rowId = 1; rowId <= beanGrid.getRowsNum(); rowId++)
			if (cell(rowId, "okDoUpdate").isChecked()) {
				isAnyChecked = true;
				break;
			}
		
		if (isAnyChecked)
			return true;
		else {
			alert(messagesData.noRowSelected);
			return false;
		}
	}
}

function checkOut() {
	if (validateCheckOut()) {
		try {
			showPleaseWait();
			beanGrid.parentFormOnSubmit();
			$("uAction").value = "checkOut";
			// serializes the form's elements.
			var frmdt =  j$("form").serialize();
			
			j$.ajax({
				type : "POST",
				async : false,
				url : "reordermrmain.do",
				data : frmdt,
				success : checkOutComplete
			});
		} catch (e) {
			stopPleaseWait();
		}
	}
}

function checkOutComplete(prNumber) {
	stopPleaseWait();
	if (prNumber != "-1") {
		if (parent.opener && parent.opener.viewReorderedMR)
			parent.opener.viewReorderedMR(prNumber);
	} else
		alert(messagesData.genericError);
}

function doNothing() {}