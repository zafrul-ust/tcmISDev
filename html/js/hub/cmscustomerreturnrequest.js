var selectedReturnReasonRowId = null;
var selectedReturnReceiptRowId = null;
var selectedReturnIssueViewRowId = null;
var returnReasonBeanGrid;
var returnReceiptBeanGrid;
var returnIssueBeanGrid;
//used to get the next available id, since users can remove/insert row at will.
var totalNumRowReturnReasonGrid = 0;
var dhxWins;
var returnIssueJson = {};

function resultOnLoadWithGrid() {
	initGridWithGlobal(returnReasonGridConfig);
	$(returnReasonGridConfig.divName).style["display"] = "";
	
	initGridWithGlobal(returnReceiptGridConfig);
	$(returnReceiptGridConfig.divName).style["display"] = "";
	
	totalNumRowReturnReasonGrid = returnReasonBeanGrid.getRowsNum();
	
	if ($("reasonRemoveLine")) {
		$("reasonRemoveLine").style["display"] = returnReasonBeanGrid.getRowsNum() > 0 ? "" : "none";
	}
	
	if (isWhitespace($v("returnQuantityRequested"))) {
		var totalRequestQty = 0;
		for (var index = 0; index < returnReceiptBeanGrid.getRowsNum(); index++) {
			totalRequestQty += parseInt(gridCellValue(returnReceiptBeanGrid,
														returnReceiptBeanGrid.getRowId(index),
														"quantity")
											|| 0);
		}
		
		setTotalReturnRequested(totalRequestQty);
	}
	
	resizeResultsIncludeGrids();
	
	if (showNoticeMessage) {
		showNoticeMessages();
	}
}

function resizeResultsIncludeGrids() {
	try {
		//grids' width cannot be more than its container
		setGridWidthFromColWidth(returnReceiptGridConfig,
									getElementWidth($("detailFieldSet")) - getElementWidth($("receiptLabelTd")));
		setGridWidthFromColWidth(returnReasonGridConfig,
									getElementWidth($("detailFieldSet")) - getElementWidth($("receiptLabelTd")));
		
		//5 is an arbitrary value that is selected so the page doesn't become too long if there's too many rows in a grid
		setGridHeightFromRowHeight(returnReceiptGridConfig, 5);
		setGridHeightFromRowHeight(returnReasonGridConfig, 5);
	} catch (e) {}
}

function getElementWidth(element) {
	return element.innerWidth || element.clientWidth || element.offsetWidth;
}

function getElementHeight(element) {
	return element.innerHeight || element.clientHeight || element.offsetHeight;
}

function setTotalReturnRequested(totalRequestQty) {
	$("returnQuantityRequestedSpan").innerHTML = totalRequestQty;
	$("returnQuantityRequested").value = totalRequestQty;
}

function selectReturnReasonRow(rowId,cellInd) {
	selectedReturnReasonRowId = rowId;
}

function selectReturnReceiptRow(rowId,cellInd) {
	selectedReturnReceiptRowId = rowId;
}

function selectReturnIssueViewRow(rowId,cellInd) {
	selectedReturnIssueViewRowId = rowId;
}

function calculateTotalReturnRequested(rowId, colId) {
	var newQuantity = gridCellValue(returnIssueBeanGrid, rowId, colId);
	var lastQuantity = gridCellValue(returnIssueBeanGrid, rowId, "lastRequestedQuantity");
	
	if (isWhitespace(newQuantity)) {
		issueReturnQuantityRequestedChanged(rowId, "");
	} else if (isNonnegativeInteger(newQuantity)) {
		newQuantity = parseInt(newQuantity) || 0;
		lastQuantity = parseInt(lastQuantity) || 0;
		
		if (newQuantity > parseInt(gridCellValue(returnIssueBeanGrid, rowId, "totalAvailable"))) {
			alert(messagesData.xCannotBeMoreThanY.replace("{0}", messagesData.requestedQuantity)
													.replace("{1}", messagesData.availableQty));
			
			setGridCellValue(returnIssueBeanGrid, rowId, colId, lastQuantity);
		} else {
			issueReturnQuantityRequestedChanged(rowId, newQuantity);
		}
	} else {
		alert(messagesData.xxNonNegativeIntegerError.replace("{0}", messagesData.requestedQuantity));
		
		setGridCellValue(returnIssueBeanGrid, rowId, colId, lastQuantity);
	}
}

function issueReturnQuantityRequestedChanged(rowId, newRowQuantity) {
	var receiptCurQuantity = parseInt(gridCellValue(returnReceiptBeanGrid,
													selectedReturnReceiptRowId,
													"quantity"))
								|| 0;
	var lastQuantity = parseInt(gridCellValue(returnIssueBeanGrid, rowId, "lastRequestedQuantity")) || 0;
	newRowQuantity = parseInt(newRowQuantity) || 0;
	
	setGridCellValue(returnIssueBeanGrid, rowId, "lastRequestedQuantity", newRowQuantity);
	
	setGridCellValue(returnIssueBeanGrid, rowId, "issueReturnApprovedQuantity", newRowQuantity);

	setGridCellValue(returnIssueBeanGrid,
						rowId,
						"returnIssue",
						isWhitespace(newRowQuantity + "") || newRowQuantity == 0 ? "" : "true");
	
	setGridCellValue(returnReceiptBeanGrid,
						selectedReturnReceiptRowId,
						"quantity",
						receiptCurQuantity - lastQuantity + newRowQuantity);
	
	var totalRequestedQty = parseInt($v("returnQuantityRequested")) || 0;
	
	setTotalReturnRequested(totalRequestedQty - lastQuantity + newRowQuantity);
	
	returnIssueBeanGrid.haasSyncJsonDataWithScreen(rowId);
}

function confirmCustomerReturnRequest() {
	if (validateUpdateInput()) {
		$("action").value = "confirm";
		$("startSearchTime").value = new Date().getTime();
		
		var rowsArr = [];
		count = 0;
		for (var receiptId in returnIssueJsonColl) {
			var curArr = returnIssueJsonColl[receiptId].rows;
			
			for (var i = 0; i < curArr.length; i++) {
				curArr[i].id = count + 1;
				
				rowsArr.push(curArr[i]);
				
				count += 1;
			}
		}
		
		returnIssueJson = {
			rows : rowsArr
		}
		
		returnIssueGridConfig.divName = "returnIssueSubmitBean";
		
		initGridWithGlobal(returnIssueGridConfig);
		
		returnReasonBeanGrid.parentFormOnSubmit();
		returnIssueBeanGrid.parentFormOnSubmit();
		
		$(returnIssueGridConfig.divName).style.display = "none";
		
		showPleaseWait();
		
		document.genericForm.submit();
	}
}

function validateUpdateInput() {
	var errorMessage = "";
	
	//Things to validate:
	// - Must provide a return reason
	// - The total requested quantity must be positive integer
	// - The total requested quantity cannot be more than shipped quantity
	if (returnReasonBeanGrid.getRowsNum() == 0) {
		errorMessage += "\n" + messagesData.xIsRequired.replace("{0}", messagesData.returnReason);
	}

	var returnQuantityRequested = parseInt($v("returnQuantityRequested")) || 0;
	if (returnQuantityRequested == 0) {
		errorMessage += "\n" + messagesData.xIsRequired.replace("{0}", messagesData.requestedQuantity);
	} else if (returnQuantityRequested > parseInt($v('quantityShipped'))) {
		errorMessage += "\n" + messagesData.originalShippedQtyError;
	}
	
	if (isWhitespace(errorMessage)) {
		return true;
	} else {
		alert(messagesData.invalidInputs + ":\n" + errorMessage);
		
		return false;
	}
}

function addReturnReasonLine() {
	var newRowId = ++totalNumRowReturnReasonGrid;
	
	returnReasonBeanGrid.addRow(newRowId, ["Y", returnReasonId[0].value], -1);
	
	$("reasonRemoveLine").style["display"] = returnReasonBeanGrid.getRowsNum() > 0 ? "" : "none";
	
	setGridHeightFromRowHeight(returnReasonGridConfig, 5);
}

function removeReturnReasonLine(rowId) {
	if (rowId == null) {
		if (selectedReturnReasonRowId != null) {
			rowId = selectedReturnReasonRowId;
		} else {
			alert(messagesData.noRowSelected);
			return false;
		}
	}
	
	returnReasonBeanGrid.deleteRow(rowId);
	
	$("reasonRemoveLine").style["display"] = returnReasonBeanGrid.getRowsNum() > 0 ? "" : "none";
	
	setGridHeightFromRowHeight(returnReasonGridConfig, 5);
}

function openPopupWin(popupWinId, popupWinTitle, objectId, height, width) {
	if (typeof height === 'undefined' || height == null || height == 0) {
		height = 200;
	}
	
	if (typeof width === 'undefined' || width == null || width == 0) {
		width = 300;
	}
	
	initializeDhxWins();
	
	var popupWin = dhxWins.createWindow(popupWinId, 0, 0, width, height);
	popupWin.setText(popupWinTitle);
	popupWin.clearIcon(); // hide icon
	popupWin.denyResize(); // deny resizing
	popupWin.denyPark(); // deny parking
	popupWin.attachObject(objectId);
	popupWin.attachEvent("onClose", function(popupWinObj) {
		closeInWindowPopup(popupWinObj.getId());
	});
	popupWin.center();
	popupWin.setModal(true);
}

function showNoticeMessages() {
	var noticeMessagesArea = document.getElementById("noticeMessagesArea");
	noticeMessagesArea.style.display = "";
	noticeMessagesArea.innerHTML = document.getElementById("noticeMessagesAreaBody").innerHTML;
	
	openPopupWin("noticeWin", messagesData.noticeTitle, "noticeMessagesArea", 450, 300);
}

function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function closeInWindowPopup(popupWinId) {
	if (dhxWins != null) {
		if (dhxWins.window(popupWinId)) {
			dhxWins.window(popupWinId).setModal(false);
			dhxWins.window(popupWinId).hide();
		}
	}
}

function returnRequestorEmailChanged() {
	var inputEmail = $("requestorEmail");
	if (!isEmail(inputEmail.value, true)) {
		alert(messagesData.xIsInvalidEmail.replace("{0}", inputEmail.value));
		
		inputEmail.value = "";
	}
}

function returnRequestorPhoneChanged() {
	var inputPhone = $("requestorPhone");
	if (!isPositiveInteger(inputPhone.value, true)) {
		alert(messagesData.xMustBeNumberOnly.replace("{0}", messagesData.phoneNumber));
		
		inputPhone.value = "";
	}
}

function viewReceiptIssues(selectedReceiptId) {
	returnIssueJson = returnIssueJsonColl[selectedReceiptId];
	
	initGridWithGlobal(returnIssueGridConfig);
	$(returnIssueGridConfig.divName).style["display"] = "";
	var t = 0
	for ( var i = 0; i < returnIssueBeanGrid.initCellWidth.length; i++) {
		var newColWidth = fontSizeFactor * parseInt(returnIssueBeanGrid.initCellWidth[i]);
		returnIssueBeanGrid.setColWidth(i, newColWidth);
		t += newColWidth;
	}

	//grids' width cannot be more than its container
	setGridWidthFromColWidth(returnIssueGridConfig, getElementWidth($("resultsMaskTable")) * 4 / 5);

	setGridHeightFromRowHeight(returnIssueGridConfig, 5);
	
	openPopupWin("receiptIssuePopupWin" + selectedReceiptId,
					"Issues",
					"issueViewArea",
					parseInt($(returnIssueGridConfig.divName).style.height) + 100,
					Math.min(t, getElementWidth($("resultsMaskTable")) * 4 / 5) + 70);
}

function closeReceiptIssuesPopupWin() {
	closeInWindowPopup("receiptIssuePopupWin" + gridCellValue(returnReceiptBeanGrid, selectedReturnReceiptRowId, "receiptId"));
}