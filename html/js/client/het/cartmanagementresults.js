var mygrid;
var windowCloseOnEsc = true;
var resizeGridWithWindow = true;
var selectedRowId = 1;
var lastSelectedRowId = 1;
var itemWindow;
var formattedToday;

/* The reason for this is to show the error messages from the main page */
function showErrorMessages() {
	parent.showErrorMessages();
}

function myResultOnLoadWithGrid(gridConfig) {

	totalLines = document.getElementById("totalLines").value;

	document.getElementById("cartManagement").style["display"] = "";

//	if (updateSuccess) {
//		alert(messagesData.updateSuccess);
//	}

	parent.document.getElementById("mainUpdateLinks").style["display"] = "";

	initGridWithGlobal(gridConfig);

	/* Display the standard footer message */
	displayGridSearchDuration();

	/*
	 * It is important to call this after all the divs have been turned on
	 * or off.
	 */
	setResultFrameSize();
}

function displayGridSearchDuration() {
	var totalLines = document.getElementById("totalLines");
	if (totalLines.value != null) {
		var startSearchTime = parent.window.document.getElementById("startSearchTime");
		var now = new Date();
		var minutes = 0;
		var seconds = 0;
		// the duration is in milliseconds
		var searchDuration = now.getTime() - (startSearchTime.value * 1);
		if (searchDuration > (1000 * 60)) { // calculating
			// minutes
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
			footer.innerHTML = messagesData.recordFound + ": " + totalLines.value + " -- " + messagesData.searchDuration + ": " + minutes + " " + messagesData.minutes + " " + seconds + " " + messagesData.seconds;
		}
		else {
			footer.innerHTML = messagesData.recordFound + ": " + totalLines.value + " -- " + messagesData.searchDuration + ": " + seconds + " " + messagesData.seconds;
		}
	}
	else {
		var footer = parent.document.getElementById("footer");
		footer.innerHTML = "&nbsp;";
	}
}

var errorsExist = false;
var validations=0;

function submitMainUpdate() {
	if (validateForm() && validations == 0) {
		finishSubmit();
	}
}

function finishSubmit() {
	validations--;
	if (validations < 1 && !errorsExist) {
		$("uAction").value = 'update';
		parent.showPleaseWait();

		if (mygrid != null) {
			mygrid.parentFormOnSubmit();
		}
		/* Submit the form in the result frame */
		document.genericForm.submit();
	}
}

function validateForm() {
	validations = 0;
	errorsExist = false;
	var errMsg = "";
	var numRows = mygrid.getRowsNum();
	var selectedIndex;
	for ( var rowIndex = 0; rowIndex <= numRows; rowIndex++) {
		var rowId = mygrid.getRowId(rowIndex);
		if (mygrid._haas_row_span_map[rowIndex] == 1) {
			if (!cartRowHasName(rowId)) {
				if (!cartRowHasItem(rowId)) {
					// Blank new row, set newCart to false
					cell(rowId, "newCart").setValue(false);
				}
				else {
					errMsg += messagesData.nameRequired + "\n";
					errMsg += messagesData.itemRequired + "\n";
				}
			}
			else { 
				if (!cartRowHasItem(rowId)) {
					errMsg += messagesData.itemRequired + "\n";
				}
				if (cellValue(rowId, "active") == "true" && cellValue(rowId, "oldStatus") == "I") {
					var employee = "" + cellValue(rowId, "employee");
					if (employee.length == 0) {
						errMsg += messagesData.employeeRequired + "\n";
					}
					else {
						validateEmployee(employee, rowId);
					}
				}
			}
		}
		else if (mygrid._haas_row_span_map[rowIndex] > 1) {
			if (!cartRowHasName(rowId)) {
				errMsg += messagesData.nameRequired + "\n";
			}
			//if (!cartHasMoreThanOneItemRemaining(rowIndex)) {
			//	errMsg += messagesData.itemRequired + "\n";
			//}
			if (cellValue(rowId, "active") == "true" && cellValue(rowId, "oldStatus") == "I") {
				var employee = "" + cellValue(rowId, "employee");
				if (employee.length == 0) {
					errMsg += messagesData.employeeRequired + "\n";
				}
				else {
					validateEmployee(employee, rowId);
				}
			}
		}

		if (errMsg.length > 0) {
			mygrid.selectRow(rowIndex, true);
			alert(errMsg);
			errorsExist = true;
			return false;
		}
	
	}
	return true;
}

function invalidQuantity(rowId, containerId, quantity, remaining, uom) {
}

function invalidUOM(rowId, uom, containerId) {
}

function invalidContainer(rowId, containerId, itemId) {
}

function invalidEmptyContainer(rowId, containerId) {
}

function invalidEmployee(rowId) {
	mygrid.selectRowById(rowId, true);
	alert(messagesData.invalidEmployee);
}

function validateEmployee(employee, rowId) {
	var url = "containervalidation.do?employee="+cellValue(rowId,"employee");
	url += "&rowId="+rowId;
	url += "&companyId="+$v("companyId");
	url += "&facilityId="+$v("facilityId");
	url += "&validation="+validations++;
	callToServer(url);
}


function confirmCheckin(rowId) {
	if (cellValue(rowId, "active") == "false" && cellValue(rowId, "oldStatus") == "A") {
		if (!confirm(messagesData.checkingWarning)) {
			cell(rowId, "active").setValue(true);
			$("active" + rowId).checked = true;
			updateHchStatusA("active" + rowId);
		}
	}
}

function rippleChangesToKit(rowId) { 
	var parentIndex = mygrid.getRowIndex(rowId);
	if (mygrid._haas_row_span_lvl2_map[parentIndex] > 1) {
		var sortOrder = cellValue(rowId, "sortOrder");
		var end = parentIndex + mygrid._haas_row_span_lvl2_map[parentIndex] - 1;
		for ( var index = parentIndex; index <= end; index++) {
			cell(mygrid.getRowId(index), "sortOrder").setValue(sortOrder);
		}
	}
}

function cartRowHasItem(rowId) {
	return (cellValue(rowId, "itemId") + "").length > 0;
}

function cartRowHasName(rowId) {
	return (cellValue(rowId, "cartName") + "").length > 0;
}

function onRightClick(rowId, cellInd) {
	if (cellValue(rowId, "oldStatus") == "I") {
		selectRow(rowId, cellInd);
		var start = mygrid.haasGetRowSpanStart(rowId);
		if (mygrid._haas_row_span_map[start] > 1 && cartHasMoreThanOneItemRemaining(start))
			toggleContextMenu('rightClickMenu');
		else
			toggleContextMenu('rightClickMenuNoRemove');
	}
	else {
		toggleContextMenu('contextMenu');
	}
}

function cartHasMoreThanOneItemRemaining(rowIndex) {
	return getItemCountForCart(rowIndex) > 0;
}

function getItemCountForCart (rowIndex) {
	var count = 0;
	var end = rowIndex + mygrid._haas_row_span_map[rowIndex] - 1;
	for ( var index = rowIndex; index <= end; index++) {
		if (mygrid._haas_row_span_lvl2_map[index] != 0 && cellValue(mygrid.getRowId(index), "cartItemDeleted") == false) {
			count++;
		}
	}
	return count;
}

function deleteCart () {
	if (confirm(messagesData.verifyDelete)) {
		var parentIndex = mygrid.haasGetRowSpanStart(selectedRowId);
		var endIndex = mygrid.haasGetRowSpanEndIndex(selectedRowId);
	
		for ( var index = parentIndex; index < endIndex; index++) {
				var rowId = mygrid.getRowId(index);
				cell(rowId, "cartItemDeleted").setValue(true);
		}
		for ( var index = parentIndex; index < endIndex; index++) {
			colorDeletedRowSpan(mygrid.getRowId(index));
		}
	}
}

function selectRow(rowId, cellInd) {
	colorDeletedRowSpan(selectedRowId);
	selectedRowId = rowId; // set global variable, selectedRowId, here for
	colorDeletedRowSpan(selectedRowId);
}

function newCart() {
	if ($v("totalLines") == 0) {
		$("totalLines").value = 1;
		setResultFrameSize();
	}

	var rowId = mygrid.getRowsNum();
	var ind = rowId + 1;
	var newRowData = [	'Y',
				'Y',
				'Y',
				'Y',
				'Y',
				false,
				'',
				'',
				formattedToday,
				$v("workAreaName"),
				'',
				'',
				'',
				'',
				'',
				'',
				'',
				'',
				true,
				false,
				false,
				'',
				'I',
				'',
				$v("companyId"),
				$v("facilityId"),
				$v("workArea") ];

	mygrid.haasAddRowToRowSpan(newRowData, rowId);
	mygrid.haasRenderRow(mygrid.getRowId(ind));
	mygrid.selectRow(ind);
}

function insertItem(partno, itemId, msds, containerId, desc, size, close) {
	if ($v("totalLines") == 0) {
		$("totalLines").value = 1;
		setResultFrameSize();
	}

	var parentIndex = mygrid.haasGetRowSpanStart(selectedRowId);
	if (mygrid._haas_row_span_map[parentIndex] == 1 && ("" + cellValue(selectedRowId, "catPartNo")) == "") {
		cell(selectedRowId, "catPartNo").setValue(partno);
		cell(selectedRowId, "itemId").setValue(itemId);
		cell(selectedRowId, "containerId").setValue(containerId);
		cell(selectedRowId, "materialDesc").setValue(desc);
		cell(selectedRowId, "custMsdsNo").setValue(msds);
		cell(selectedRowId, "containerSize").setValue(size);
		cell(selectedRowId, "newCartItem").setValue(true);
		cell(selectedRowId, "sortOrder").setValue(1);
	}
	else {
		var numRows = mygrid.getRowsNum();
		for ( var rowIndex = 0; rowIndex < numRows; rowIndex++) {
			if (cellValue(rowIndex + 1,"containerId") == containerId) {
				alert(formatMessage(messagesData.onCart, containerId));
				return false;
			}
		}

		var cartId = cellValue(selectedRowId, "cartId");
		var newLinePosition = mygrid.haasGetRowSpanEndIndex(selectedRowId);
		var numRows = mygrid.getRowsNum();

		var newRowData = [	'Y',
					'Y',
					'Y',
					'Y',
					'Y',
					false,
					cellValue(selectedRowId,"oldName"),
					'',
					formattedToday,
					$v("workAreaName"),
					getItemCountForCart(parentIndex) + 1,
					partno,
					itemId,
					containerId,
					desc,
					msds,
					size,
					cartId,
					false,
					true,
					false,
					cellValue(selectedRowId,"oldName"),
					cellValue(selectedRowId,"oldStatus"),
					'',
					$v("companyId"),
					$v("facilityId"),
					$v("workArea") ];

		mygrid.haasAddRowToRowSpan(newRowData, newLinePosition, parentIndex);
		mygrid.haasRenderRow(mygrid.getRowId(parentIndex));
		mygrid.selectRow(parentIndex, true);
	}

	return true;
}

function insertItemChild(partno, itemId, msds, containerId, desc, size) {

	var parentIndex = mygrid.haasGetRowSpanStart(selectedRowId);
	var newLinePosition = mygrid.haasGetRowSpanEndIndex(selectedRowId);
	var parentRowId = mygrid.getRowId(newLinePosition - 1);
	var parent2Index = mygrid.haasGetRowSpanStartLvl2(parentRowId);

	var newRowData = [	'Y',
				'Y',
				'Y',
				'Y',
				'Y',
				false,
				cellValue(selectedRowId,"oldName"),
				'',
				formattedToday,
				$v("workAreaName"),
				cellValue(selectedRowId,"sortOrder"),
				partno,
				itemId,
				containerId,
				desc,
				msds,
				size,
				cellValue(selectedRowId,"cartId"),
				false,
				true,
				false,
				cellValue(selectedRowId,"oldName"),
				cellValue(selectedRowId,"oldStatus"),
				'',
				$v("companyId"),
				$v("facilityId"),
				$v("workArea") ];

	mygrid.haasAddRowToRowSpan(newRowData, newLinePosition, parentIndex, parent2Index);
	mygrid.haasRenderRow(mygrid.getRowId(parentIndex));
	mygrid.selectRow(parentIndex, true);
}

function removeItem() {
	var parentIndex = mygrid.haasGetRowSpanStartLvl2(selectedRowId);
	var endIndex = mygrid.haasGetRowSpanEndIndexLvl2(selectedRowId);
	for ( var index = parentIndex; index < endIndex; index++) {
		cell(mygrid.getRowId(index), "cartItemDeleted").setValue(true);
	}
	colorDeletedRowSpan(selectedRowId);
}

function colorDeletedRowSpan(rowId) {
	var rowIndex = mygrid.getRowIndex(rowId);
	if (mygrid._haas_row_span_map[rowIndex] > 0 && !cartHasMoreThanOneItemRemaining(rowIndex)) {
		mygrid.haasSetColSpanStyle(rowId, 1, 17, "background-color: #BEBEBE; text-decoration:line-through;");
	}
	//else {
		var parentIndex = mygrid.haasGetRowSpanStartLvl2(rowId);
		if (cellValue(mygrid.getRowId(parentIndex), "cartItemDeleted")) {
			var endIndex = mygrid.haasGetRowSpanEndIndexLvl2(rowId);
			for ( var index = parentIndex; index < endIndex; index++) {
				mygrid.haasSetColSpanStyle(mygrid.getRowId(index), 5, 17, "background-color: #BEBEBE; text-decoration:line-through;");
			}
		}
	//}
}

function addItem() {
	var url = "cartitemsearchmain.do?facilityId=" + $v("facilityId") + "&workArea=" + $v("workArea") + "&workAreaName=" + $v("workAreaName");

	itemWindow = openWinGeneric(url, "CartItemSearchPage", "800", "450", "yes", "50", "50", "no");
	parent.children[parent.children.length] = itemWindow;
	parent.showTransitWin(messagesData.waiting);
}
