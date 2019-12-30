function isArray(testObject) {
	return testObject && !(testObject.propertyIsEnumerable('length'))
			&& typeof testObject === 'object'
			&& typeof testObject.length === 'number';
}

function searchOnLoad() {
	setSearchFrameSize();
	if (!isWhitespace($v("orderStatus"))) {
		var radioBtns = document.getElementsByName('orderStatuses');
		for (var i = 0; i < radioBtns.length; i++)
			if (radioBtns[i].value === $v("orderStatus")) {
				$("orderStatus").checked = true;
				break;
			}
	}
}

function showSupplierLocation(selectedSupplier, selectedInv) {
	var idArray = altSupplierLocationId[selectedSupplier];
	var nameArray = altSupplierLocationName[selectedSupplier];
	var selectI = 0;
	var inserted = 0;

	var inv = $("suppLocationIdArray");
	for (var i = inv.length; i > 0; i--) {
		inv[i] = null;
	}
	for (var i = 0; i < nameArray.length; i++) {
		{
			setOption(inserted, nameArray[i], idArray[i], "suppLocationIdArray");
			if (selectedInv == idArray[i])
				selectI = inserted;
			inserted++;
		}
	}
	if (inserted == 0)
		setOption(inserted, messagesData.all, "", "suppLocationIdArray");
	$("suppLocationIdArray").selectedIndex = selectI;
}

function showSupplier(oldSupplier) {
	var idArray = altSupplierId;
	var nameArray = altSupplierName;
	var selectI = 0;
	var inserted = 0;

	for (var i = 0; i < nameArray.length; i++) {
		if (idArray[i] != "") {
			setOption(inserted, nameArray[i], idArray[i], "supplier");
			if (oldSupplier == idArray[i])
				selectI = inserted;
			inserted++;
		}
	}
	$("supplier").selectedIndex = selectI;
}

function supplierChanged() {
	var Supplier = $("supplier");
	var inv = $("suppLocationIdArray");
	var selectedSupplier = Supplier.value;
	showSupplierLocation(selectedSupplier, null);
}

function submitSearchForm() {
	var now = new Date();
	document.getElementById("startSearchTime").value = now.getTime();
	var searchOn = $('searchField').value;
	var searchVal = $('searchArgument').value.trim();
	if ((searchOn == "radianPo" || searchOn == "itemId" || searchOn == "mrNumber")
			&& ((searchVal != null && searchVal != "" && !isInteger(searchVal)) || parseInt(searchVal) < 1)) {
		alert(titleRequired[searchOn] + " " + messagesData.errorInteger);
		$('searchArgument').focus();
		return false;
	}
	
	var radioBtns = document.getElementsByName('orderStatuses');
	for (var i = 0; i < radioBtns.length; i++)
		if (radioBtns[i].checked) {
			$("orderStatus").value = radioBtns[i].value;
			break;
		}
	document.supplierLocationForm.target = 'resultFrame';
	document.getElementById("_action").value = "search";
	parent.showPleaseWait();
	document.supplierLocationForm.submit();
}

function showErrorMessages() {
	parent.showErrorMessages();
}

function resultOnLoad() {
	displaySearchDuration();
	setResultFrameSize();
	/*Dont show any update links if the user does not have permissions*/
	parent.document.getElementById("updateResultLink").style["display"] = showUpdateLinks ? "" : "none";
	if (!isWhitespace($v("picklistId"))) {
		parent.parent.openIFrame("pack", "packmain.do?userAction=display&pageDo=searchLoad&supplier=" + $v("supplier") + "&shipFromLocationId=" + $v("suppLocationIdArray") + "&picklistId=" + $v("picklistId"), "Pack", "", "Y");  
	}
}

function createExcel() {
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',
			'show_excel_report_file', '800', '600', 'yes');
	document.supplierLocationForm.target = 'show_excel_report_file';
	var userAction = document.getElementById("_action");
	userAction.value = 'createExcel';
	var a = window.setTimeout("document.supplierLocationForm.submit();", 1000);
	return false;
}

function updatePO(poNumber, lineNumber) {
	var loc = "splitpoline.do?action=search&radianPo=" + poNumber + "";
	openWinGeneric(loc, "showVvHubBins", "900", "600", "yes", "80", "80");
}

function getShipToAddress(locationId, dodaac) {
	openWinGeneric("/tcmIS/supplier/viewaddress.do?locationId=" + locationId
			+ "&ultimateDodaac=" + dodaac, '_viewaddress', '500', '300', 'yes');
}

function allocateOrder(rowNum) {
	var loc = "allocateorder.do?catPartNo=" + $("catPartNo_" + rowNum).value
		+ "&mfgDateRequired=" + $("mfgDateRequired_" + rowNum).value
		+ "&originInspectionRequired=" + $("originInspectionRequired_" + rowNum).value
		+ "&supplier=" + $("supplier_" + rowNum).value
		+ "&shipFromLocationId=" + $("shipFromLocationId_" + rowNum).value
		+ "&qtyToPick=" + $("qtyOpen_" + rowNum).value
		+ "&branchPlant=" + $("branchPlant_" + rowNum).value
		+ "&itemId=" + $("itemId_" + rowNum).value
		+ "&mrNumber=" + $("mrNumber_" + rowNum).value
		+ "&mrLineItem=" + $("mrLineItem_" + rowNum).value
		+ "&poLine=" + $("poLine_" + rowNum).value
		+ "&radianPo=" + $("radianPo_" + rowNum).value
		+ "&receiptDate=" + $("receiptDate_" + rowNum).value
		+ "&vendorShipDate=" + $("vendorShipDate_" + rowNum).value
		+ "&customerPo=" + $("customerPo_" + rowNum).value
		+ "&supplierSalesOrderNo=" + $("supplierSalesOrderNo_" + rowNum).value
		+ "&shiptoAddress=" + $("shipToAddress_" + rowNum).value
		+ "&oconus=" + $("oconus_" + rowNum).value
		+ "&partsPerBox=" + $("partsPerBox_" + rowNum).value
		+ "&vmi=" + ($("originalTransactionType_" + rowNum).value === '940' ? "Y" : "N");
	openWinGeneric(loc, "allocateOrder", "900", "600", "yes", "80", "80");
}

function confirmSuccess(batchId) {
	$("_action").value = "reloadAfterConfirm";
	$("picklistId").value = batchId;
	document.supplierLocationForm.target = 'resultFrame';
	parent.showPleaseWait();
	document.supplierLocationForm.submit();
}