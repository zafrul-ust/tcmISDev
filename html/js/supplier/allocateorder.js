function doOnLoad() {
	//if the confirm procedure succeed for all inventory allocation, the batchId will be sent to front-end
	if (!isWhitespace($v("batchId"))) {
		try {
			opener.confirmSuccess($v("batchId"));
		} catch (e) {}
		window.close();
	}
	
	stopPleaseWait();
	//if there's no inventory to pick, hide all actions
	$("mainUpdateLinks").style["display"] = showUpdateLinks && $v("totalLines") != "0" ? "" : "none";
	
	if ($v("totalLines") != "0") {
		$(gridConfig.divName).style["display"] = "";
		initGridWithGlobal(gridConfig);
		//prepare initial overhead counts for available qty and picked qty
		var totalAvailableQty = 0;
		var totalQty = 0;
		for (var curRowId = 1; curRowId <= beanGrid.getRowsNum(); curRowId++) {
			//if an inventory id is shown, it will always have a valid available qty
			totalAvailableQty += Number(cellValue(curRowId, "availableQty"));
			//an inventory id may or may not be picked initially
			if (!isWhitespace(cellValue(curRowId, "pickedQty")))
				totalQty += Number(cellValue(curRowId, "pickedQty"));
		}
		$("totalAvailableQty").innerHTML = totalAvailableQty;
		$("totalQty").innerHTML = totalQty;
	}
	
	if (showMessage)
		showMessages();
	
	displayNoSearchSecDuration();
	//the data display needs to be accounted for so the grid can be shown in full
	setResultSize($("overheadSection").offsetHeight - $("mainUpdateLinks").offsetHeight);
}

function qtyChanged(rowId) {
	var inputQty = cellValue(rowId, "pickedQty");
	//if user clears out picked qty, assume the qty is 0
	if (isWhitespace(inputQty))
		inputQty = "0";
	
	//check if the input is a non-negative integer. This will also account for non-numeric value
	if (!isNonnegativeInteger(inputQty)) {
		alert(messagesData.xxNonnegativeInteger.replace("{0}", messagesData.qty));
		$("pickedQty" + rowId).focus();
	} else {
		var inputQtyNum = Number(inputQty);
		if (inputQtyNum > Number(cellValue(rowId, "availableQty"))) {
			alert(messagesData.shipMoreThanAvailableQty);
			$("pickedQty" + rowId).focus();
		} else {
			//if the qty is 0, the inventory id is not allocated
			if (inputQtyNum == 0)
				setCellValue(rowId, "allocated", false);
			else
				setCellValue(rowId, "allocated", true);
			
			//calculate new picked qty count by first subtracting old picked qty of the row and then adding new qty
			var newTotalQty = Number($("totalQty").innerHTML);
			//initially, the old picked qty can be nothing if the row is not allocated by view
			if (!isWhitespace(cellValue(rowId, "oldQty")))
				newTotalQty -= Number(cellValue(rowId, "oldQty"));
			newTotalQty += inputQtyNum;
			$("totalQty").innerHTML = newTotalQty;
			setCellValue(rowId, "oldQty", inputQty);
		}
	}
}

/**
 * Validate inputs before confirming:
 * 	- picked qty must be equal or less than qty to pick
 * 	- if this is OI order, do not allow partial shipping
 * 	- if order requires mfg date, check for the data's emptiness
 * @returns
 */
function isValidInputs() {
	var totalQtyToShip = Number($("totalQty").innerHTML);
	var totalAvailableQty = Number($("totalAvailableQty").innerHTML);
	
	if (totalQtyToShip > Number($v("qtyToPick"))) {
		alert(messagesData.shipMoreThanQtyToPick);
		return false;
	} else if ("Y" === $v("originInspectionRequired") && totalQtyToShip != Number($v("qtyToPick"))) {
		alert(messagesData.partialShipOIOrder);
		return false;
	} else if ("Y" === $v("mfgDateRequired")) {
		for (var curRowId = 1; curRowId <= beanGrid.getRowsNum(); curRowId++)
			if (isWhitespace(cellValue(curRowId, "dateOfManufacture")) && cellValue(curRowId, "allocated") === 'true') {
				alert(messagesData.valueRequiredOnRow.replace("{0}", messagesData.dateOfManufacture).replace("{1}", curRowId));
				return false;
			}
	}
	
	return true;
}

function confirm() {
	if (isValidInputs() && Number($("totalQty").innerHTML) != 0) {
		document.genericForm.target = "";
		document.genericForm.action = "allocateorder.do";
		$("uAction").value = "confirmAllocation";
	    $("startSearchTime").value = new Date().getTime();
	    showPleaseWait();
	    
		if (beanGrid != null)
			beanGrid.parentFormOnSubmit();
		document.genericForm.submit();
	}
}

function openPrintPicklistPopup() {
	$("uAction").value = "printFromObject";
	//Since variable customerPo is used by confirm procedure and print, temporarily modify it and revert to original value after submitting form
	var temp = $v("customerPo");
	$("customerPo").value = $v("displayedCustomerPo");
	if (beanGrid != null)
		beanGrid.parentFormOnSubmit();
	openWinGeneric('/tcmIS/common/loadingfile.jsp', 'printPicklist','800','600','yes');
	document.genericForm.target = "printPicklist";
	document.genericForm.action = "/HaasReports/report/getinventorypicklist.do?beanName=supplierInventoryBean";
	document.genericForm.submit();
	$("customerPo").value = temp;
}

function showMessages() {
	document.getElementById("messagesArea").style.display = "";

	var dhxWins = new dhtmlXWindows();
	dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	if (!dhxWins.window("messageWin")) {
		// create window first time
		var messageWin = dhxWins.createWindow("messageWin", 0, 0, 450, 150);
		messageWin.setText(messagesData.messageWinHeader);
		messageWin.clearIcon(); // hide icon
		messageWin.denyResize(); // deny resizing
		messageWin.denyPark(); // deny parking
		messageWin.attachObject("messagesArea");
		messageWin.attachEvent("onClose", function(messageWin) {
			messageWin.hide();
		});
		messageWin.center();
	} else {
		// just show
		dhxWins.window("messageWin").show();
	}
}