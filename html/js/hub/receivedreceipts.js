windowCloseOnEsc = true;
var selectedRowId = null;

var children = new Array();

function $(a) {
	return document.getElementById(a);
}

function showErrorMessages() {
	parent.showErrorMessages();
}

function searchOnLoad() {
	setSearchFrameSize();
	$("userAction").value = "results"
	document.genericForm.target = 'resultFrame';
	parent.showPleaseWait();
	document.genericForm.submit();
}

function myResultOnload() {
	setResultFrameSize();
	/*
	 * Dont show any update links if the user does not have permissions.
	 * Remove this section if you don't have any links on the main page
	 */
	if (!showUpdateLinks) {
		parent.document.getElementById("updateResultLink").style["display"] = "none";
	}
	else {
		parent.document.getElementById("updateResultLink").style["display"] = "";
	}

	/* Set the list of receipts to be labeled in the search frame. */
	parent.window.frames["searchFrame"].document.getElementById("labelReceipts").value = $("labelReceipts").value;
}

function submitSearchForm() {
	var userAction = document.getElementById("userAction");
	userAction.value = 'results';
	parent.showPleaseWait();
	document.genericForm.submit();
}

function printResults() {
	if (window.print) {
		parent["resultFrame"].focus();
		parent["resultFrame"].print();
	}
}

function assignpaperSize(size) {
	var paperSize = $("paperSize");
	paperSize.value = size;
}

function changeItem() {
	var checkedCount = 0;
	var selectedItem = $("selectedItem");
	if (selectedItem.value.trim().length > 0) {
		var receiptsList = "";
		var totalRecords = $("totalLines");
		for (j = 0; j < (totalRecords.value * 1); j++) {
			var receiptId = "";
			receiptId = $("receiptId" + (j) + "");
			currentcheckBox1 = $("ok" + (j) + "");

			if (currentcheckBox1.checked) {
				if (checkedCount > 0) {
					receiptsList += ','
				}
				receiptsList += receiptId.value;
				checkedCount++
			}
		}

		// var labelReceipts =
		// document.getElementById("labelReceipts").value;
		receiptsList = receiptsList.replace(/,/gi, "%2c");
		//var loc = "/tcmIS/hub/receivingitemsearchmain.do?receipts=" + receiptsList + "";
		var loc = "receivingitemsearchmain.do?receipts=" + receiptsList + "";
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/hub/" + loc;
		
		var hubNumber = document.getElementById("hubNumber").value;
		loc = loc + "&hubNumber=" + hubNumber;
		loc = loc + "&listItemId=" + $("selectedItem").value;
		loc = loc + "&inventoryGroup=" + $("selectedInventoryGroup").value;
		loc = loc + "&catPartNo=" + $("selectedCatPartNo").value;
		loc = loc + "&catalog=" + $("selectedCatalogId").value;
		loc = loc + "&catalogCompanyId=" + $("selectedCatalogCompanyId").value;

		try {
			parent.children[parent.children.length] = openWinGeneric(loc, "changeItem", "800", "400", "yes", "80", "80");
		}
		catch (ex) {
			openWinGeneric(loc, "changeItem", "800", "400", "yes", "80", "80");
		}
	}
}

function checkMlItemInput(rowNumber) {
	var noLinesChecked = 0;
	var currentcheckBox = $("ok" + rowNumber + "");
	var totalRecords = $("totalLines");
	var selectedItem = $("selectedItem");
	var lineitemID = $("itemId" + rowNumber + "");
	var selNewChemRequestId = $("newChemRequestId" + rowNumber + "");

	if (currentcheckBox.checked) {
		noLinesChecked++;
	}

	if (selectedItem.value.trim().length > 0 && (lineitemID.value != selectedItem.value)) {
		alert("You cannot choose a receipt with Different ML Item");
		currentcheckBox.checked = false;
		return false;
	}
	else if (selNewChemRequestId.value.trim().length > 0) {
		alert("You cannot choose a receipt with Pending New Chem Request- " + selNewChemRequestId.value);
		currentcheckBox.checked = false;
		return false;
	}

	var allClear = 0;
	var finalMsgt;
	finalMsgt = "You cannot select receipts with: \n\n";

	for (j = 0; j < (totalRecords.value * 1); j++) {
		var lineitemID1 = "";
		lineitemID1 = $("itemId" + (j) + "");
		currentcheckBox1 = $("ok" + (j) + "");
		newChemRequestId = $("newChemRequestId" + (j) + "");

		if ((j + 1) != (rowNumber * 1) && currentcheckBox1.checked) {
			noLinesChecked++;
			if (lineitemID.value != lineitemID1.value) {
				if (allClear == 0) {
					finalMsgt = finalMsgt + "Different ML Item\n";
				}
				allClear += 1;
			}
			else if (newChemRequestId.value.trim().length > 0) {
				if (allClear == 0) {
					finalMsgt = finalMsgt + "Pending New Chem Request- " + newChemRequestId.value + "\n";
				}
				allClear += 1;
			}
		}
	}

	if (allClear < 1) {
		selectedItem.value = lineitemID.value;
		$("selectedInventoryGroup").value = $("inventoryGroup" + rowNumber + "").value;
		$("selectedCatalogId").value = $("catalogId" + rowNumber + "").value;
		$("selectedCatPartNo").value = $("catPartNo" + rowNumber + "").value;
		$("selectedCatalogCompanyId").value = $("catalogCompanyId" + rowNumber + "").value;
	}
	else {
		alert(finalMsgt);
		currentcheckBox.checked = false;
	}

	if (noLinesChecked == 0) {
		selectedItem.value = "";
	}

}

function printContainerLabels() {
	var labelReceipts = document.getElementById("labelReceipts").value;
	labelReceipts = labelReceipts.replace(/,/gi, "%2c");
	//var loc = "/tcmIS/hub/printcontainerlabels.do?labelReceipts=" + labelReceipts + "";
	var loc = "printcontainerlabels.do?labelReceipts=" + labelReceipts + "";
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	var paperSize = document.getElementById("paperSize").value;
	loc = loc + "&paperSize=" + paperSize;
	var hubNumber = document.getElementById("hubNumber").value;
	loc = loc + "&hubNumber=" + hubNumber;
	var skipKitLabels = document.getElementById("skipKitLabels");
	if (skipKitLabels.checked) {
		loc = loc + "&skipKitLabels=Yes";
	}
	else {
		loc = loc + "&skipKitLabels=No";
	}
	try {
		parent.children[parent.children.length] = openWinGeneric(loc, "printContainerLabels11", "800", "500", "yes", "80", "80");
	}
	catch (ex) {
		openWinGeneric(loc, "printContainerLabels11", "800", "500", "yes", "80", "80");
	}
}

function printRecDocumentLabels() {
	var labelReceipts = document.getElementById("labelReceipts").value;
	labelReceipts = labelReceipts.replace(/,/gi, "%2c");
	/*var loc = "/tcmIS/hub/printcontainerlabels.do?labelReceipts=" + labelReceipts + "";
	loc = loc + "&paperSize=receiptDocument";*/
	var loc = "printcontainerlabels.do?labelReceipts=" + labelReceipts + "&paperSize=receiptDocument";
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	var hubNumber = document.getElementById("hubNumber").value;
	loc = loc + "&hubNumber=" + hubNumber;
	var skipKitLabels = document.getElementById("skipKitLabels");
	if (skipKitLabels.checked) {
		loc = loc + "&skipKitLabels=Yes";
	}
	else {
		loc = loc + "&skipKitLabels=No";
	}
	try {
		children[children.length] = openWinGeneric(loc, "printContainerLabels11", "800", "500", "yes", "80", "80");
	}
	catch (ex) {
		openWinGeneric(loc, "printRecDocumentLabels11", "800", "500", "yes", "80", "80");
	}
}


function printReceiptDetailLabels() {
	var labelReceipts = document.getElementById("labelReceipts").value;
	labelReceipts = labelReceipts.replace(/,/gi, "%2c");
	/*var loc = "/tcmIS/hub/printcontainerlabels.do?labelReceipts=" + labelReceipts + "";
	loc = loc + "&paperSize=receiptDetail";*/
	var loc = "printcontainerlabels.do?labelReceipts=" + labelReceipts + "&paperSize=receiptDetail";
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	var hubNumber = document.getElementById("hubNumber").value;
	loc = loc + "&hubNumber=" + hubNumber;
	var skipKitLabels = document.getElementById("skipKitLabels");
    loc = loc + "&skipKitLabels=Yes";
	try {
		children[children.length] = openWinGeneric(loc, "printContainerLabels11", "800", "500", "yes", "80", "80");
	}
	catch (ex) {
		openWinGeneric(loc, "printRecDocumentLabels11", "800", "500", "yes", "80", "80");
	}
}

function printBinLabels() {
	var labelReceipts = document.getElementById("labelReceipts").value;
	labelReceipts = labelReceipts.replace(/,/gi, "%2c");
	//var loc = "/tcmIS/hub/printbinlabels.do?labelReceipts=" + labelReceipts + "";
	var loc = "printbinlabels.do?labelReceipts=" + labelReceipts + "";
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	var paperSize = document.getElementById("paperSize").value;
	loc = loc + "&paperSize=" + paperSize;
	var hubNumber = document.getElementById("hubNumber").value;
	loc = loc + "&hubNumber=" + hubNumber;
	var skipKitLabels = document.getElementById("skipKitLabels");
	if (skipKitLabels.checked) {
		loc = loc + "&skipKitLabels=Yes";
	}
	else {
		loc = loc + "&skipKitLabels=No";
	}
	loc = loc + "&binLabels=Yes";

	try {
		parent.children[parent.children.length] = openWinGeneric(loc, "printBinLabels11", "800", "500", "yes", "80", "80");
	}
	catch (ex) {
		openWinGeneric(loc, "printBinLabels11", "800", "500", "yes", "80", "80");
	}
}

function printReceivingBoxLabels() {
	var labelReceipts = document.getElementById("labelReceipts").value;
	labelReceipts = labelReceipts.replace(/,/gi, "%2c");
	/*var loc = "/tcmIS/hub/printreceiptboxlabels.do?labelReceipts=" + labelReceipts + "";
	loc = loc + "&paperSize=64";*/
	var loc = "printreceiptboxlabels.do?labelReceipts=" + labelReceipts + "&paperSize=64";

	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	try {
		parent.children[parent.children.length] = openWinGeneric(loc, "printBinLabels11", "800", "500", "yes", "80", "80");
	}
	catch (ex) {
		openWinGeneric(loc, "printBinLabels11", "800", "500", "yes", "80", "80");
	}
}

function showReceiptDocuments(receiptId, inventoryGroup, companyId, shipToLocationId) {
	var dataCount = document.getElementById("selectedDataCount" + selectedRowId + "").value;
	var receiptId = $v("receiptId" + dataCount + "");
	var inventoryGroup = $v("inventoryGroup" + selectedRowId + "");
	//var loc = "/tcmIS/hub/receiptdocuments.do?receiptId=" + receiptId + "&showDocuments=Yes&inventoryGroup=" + inventoryGroup + "&companyId=" + companyId + "&shipToLocationId=" + shipToLocationId + "";
	var loc = "receiptdocuments.do?receiptId=" + receiptId + "&showDocuments=Yes&inventoryGroup=" + inventoryGroup + "&companyId=" + companyId + "&shipToLocationId=" + shipToLocationId + "";
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	try {
		parent.children[parent.children.length] = openWinGeneric(loc, "showAllReceiptDocuments", "450", "300", "no", "80", "80");
	}
	catch (ex) {
		openWinGeneric(loc, "showAllReceiptDocuments", "450", "300", "no", "80", "80");
	}
}

function showMvReceiptDocuments(receiptId, inventoryGroup) {
	//var loc = "/tcmIS/hub/receiptdocuments.do?receiptId=" + receiptId + "&showDocuments=Yes&inventoryGroup=" + inventoryGroup + "";
	var loc = "receiptdocuments.do?receiptId=" + receiptId + "&showDocuments=Yes&inventoryGroup=" + inventoryGroup + "";
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	try {
		parent.children[parent.children.length] = openWinGeneric(loc, "showAllReceiptDocuments", "450", "300", "no", "80", "80");
	}
	catch (ex) {
		openWinGeneric(loc, "showAllReceiptDocuments", "450", "300", "no", "80", "80");
	}
}

function selectRow(rowId) {
	var selectedRow = document.getElementById("rowId" + rowId + "");

	var selectedRowClass = document.getElementById("colorClass" + rowId + "");
	selectedRow.className = "selected" + selectedRowClass.value + "";

	if (selectedRowId != null && rowId != selectedRowId) {
		var previouslySelectedRow = document.getElementById("rowId" + selectedRowId + "");
		var previouslySelectedRowClass = document.getElementById("colorClass" + selectedRowId + "");
		previouslySelectedRow.className = "" + previouslySelectedRowClass.value + "";
	}
	selectedRowId = rowId;

	if (document.getElementById("mvItem" + rowId + "").value == 'Y') {
		toggleContextMenu('print');
	}
	else {
		toggleContextMenu('receivedReceiptsMenu');
	}
}

function receiptSpec() {
	var dataCount = document.getElementById("selectedDataCount" + selectedRowId + "").value;
	var receiptId = $v("receiptId" + dataCount + "");// alert("receiptId"+receiptId);
	//var loc = "/tcmIS/distribution/receiptspec.do?receiptId=" + receiptId;
	var loc = "receiptspec.do?receiptId=" + receiptId;
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/distribution/" + loc;
	
	try {
		parent.children[parent.children.length] = openWinGeneric(loc, "receiptSpec", "810", "400", "yes");
	}
	catch (ex) {
		openWinGeneric(loc, "receiptSpec", "810", "400", "yes");
	}
}

function mvReceiptSpec(receiptId) {
	//var loc = "/tcmIS/distribution/receiptspec.do?receiptId=" + receiptId;
	var loc = "receiptspec.do?receiptId=" + receiptId;
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/distribution/" + loc;
	
	try {
		parent.children[parent.children.length] = openWinGeneric(loc, "receiptSpec", "810", "400", "yes");
	}
	catch (ex) {
		openWinGeneric(loc, "receiptSpec", "810", "400", "yes");
	}
}

function closeAllchildren() {
	// You need to add all your submit button vlues here. If not, the window
	// will close by itself right after we hit submit button.
	// if (document.getElementById("uAction").value != 'new') {
	try {
		for ( var n = 0; n < children.length; n++) {
			try {
				children[n].closeAllchildren();
			}
			catch (ex) {
			}
			children[n].close();
		}
	}
	catch (ex) {
	}
	children = new Array();
}