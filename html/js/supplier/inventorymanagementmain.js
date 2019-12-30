var children = new Array();
var inProcessPopupWindowObj;
var doSearchReload;
var dhxWins;

// Initialize drop downs
function initializeDropDowns() {
	buildDropDown(supplierArr, "supplier");
	supplierChanged();
	buildDropDown(transactionTypeArr, "transactionType");
	transactionTypeChanged();
}

function supplierChanged() {
	buildDropDown(locationArr[$v("supplier")], "shipFromLocationId");
	$("supplierName").value = $("supplier").options[$("supplier").selectedIndex].text;
	locationChanged();
}

function locationChanged() {
	$("locationDesc").value = $("shipFromLocationId").options[$("shipFromLocationId").selectedIndex].text;
	//if user selects All option, do not allow usage of all buttons except Create Excel
	if (isWhitespace($v("shipFromLocationId"))) {
		$("searchBtn").disabled = true;
		$("addPartEditMinMaxBtn").disabled = true;
		$("addInventoryBtn").disabled = true;
		$("addPOBtn").disabled = true;
	} else {
		//if user select specific shipFromLocationId, show/hide editMinMax based on permission and then allow usage to all buttons
		//shipFromLocationId index = locationArr index + 1 (from All option)
		var editMinMaxPermission = locationArr[$v("supplier")][$("shipFromLocationId").selectedIndex - 1].editMinMax;
		if ("true" === editMinMaxPermission)
			$("addPartEditMinMaxBtnDiv").style.display = "inline";
		else
			$("addPartEditMinMaxBtnDiv").style.display = "none";
		
		$("searchBtn").disabled = false;
		$("addPartEditMinMaxBtn").disabled = false;
		$("addInventoryBtn").disabled = false;
		$("addPOBtn").disabled = false;
	}
}

function transactionTypeChanged() {
	buildDropDown(transactionTypeStatusArr[$v("transactionType")], "status");
	statusChanged();
}

function statusChanged() {
	if (isWhitespace($v("status")))
		$("includeHistory").checked = "checked";
}

function buildDropDown(arr, elementName) {
	var obj = $(elementName);
	for (var i = obj.length; i > 0; i--)
		obj[i] = null;

	var start = 0;
	if (elementName === "shipFromLocationId" || elementName === "status")
		setOption(start++, messagesData.all, "", elementName);
	
	if (arr && arr.length != 0) {
		for (var i = 0; i < arr.length; i++)
			setOption(start++, arr[i].name, arr[i].id, elementName);
	}
	if (elementName === "shipFromLocationId")
		obj.selectedIndex = 1;
	else
		obj.selectedIndex = 0;
}

function isValidSearch() {
	if (isWhitespace($v("searchArgument")))
		if (isWhitespace($v("transactionFromDate"))) {
			alert(messagesData.pleaseSelectAValidValueFor.replace("{0}", messagesData.transactionDate));
			return false;
		}
	
	return true;
}

function submitSearch() {
	if (isValidSearch()) {
		// set start search time
		$("startSearchTime").value = new Date().getTime();
		document.genericForm.target = 'resultFrame';
		$("uAction").value = 'search';
		$('searchFieldName').value = $('searchField').options[$('searchField').selectedIndex].text;
		$('searchModeName').value = $('searchMode').options[$('searchMode').selectedIndex].text;
		document.genericForm.submit();
		showTransitWin();
	}
}

function createExcel() {
	$("uAction").value = "createExcel";
	document.genericForm.target = '_SamplePageExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_SamplePageExcel', '650', '600', 'yes');
	$('searchFieldName').value = $('searchField').options[$('searchField').selectedIndex].text;
	$('searchModeName').value = $('searchMode').options[$('searchMode').selectedIndex].text;
	document.genericForm.submit();
}

//after resizing main parts, center in-window popup to the new layout
function resizePage() {
	var doRecenter = resizeFramecount == 1 ? false : true;
	resizeFrames();
	if (doRecenter) {
		if (dhxWins && dhxWins.getTopmostWindow(true))
			dhxWins.getTopmostWindow(true).center();
		else if (window.frames["resultFrame"].dhxWins && window.frames["resultFrame"].dhxWins.getTopmostWindow(true))
			window.frames["resultFrame"].dhxWins.getTopmostWindow(true).center();
	}
}

//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showTransitWin(message) {
	$("transitLabel").innerHTML = isWhitespace(message) ? messagesData.pleasewait : message;
	$("transitDialogWin").style.display = "";

	initializeDhxWins();
	var transitWin;
	if (!dhxWins.window("transitDialogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDialogWin", 0, 0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking
		transitWin.attachObject("transitDialogWin");
		transitWin.center();
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	} else {
		transitWin = dhxWins.window("transitDialogWin");
		transitWin.center();
		transitWin.setModal(true);
		transitWin.show();
	}
}

//to be called by popup's onunload event. Delay is due to onunload being called before page is actually closed
function closeTransitWin(callerId) {
	window.setTimeout(function() {checkAndCloseTransitWin(callerId)}, 500);
}

function checkAndCloseTransitWin(callerId) {
	if (dhxWins != null) {
		if (callerId != null) {
			//if page is closed, accessing it will raise error
			try {
				for (var i = 0; i < children.length; i++)
					if (children[i] && children[i].name == callerId)
						return;
			} catch (e) {}
		}
		
		if (dhxWins.window("transitDialogWin")) {
			dhxWins.window("transitDialogWin").setModal(false);
			dhxWins.window("transitDialogWin").hide();
		}
	}
}

function openUpdateInsertPartPopup(callFromResultFrame) {
	var loc = "/tcmIS/supplier/editminmaxlevelpopup.do";
	if (callFromResultFrame)
		loc += "?callFromResultFrame=" + callFromResultFrame;
	openChildWinGeneric(loc, "updateInsertPart", "410", "240", "yes", "80", "80", "yes");
}

function openPOPopup(callFromResultFrame, popupName) {
	var loc = "/tcmIS/supplier/editpopopup.do";
	if (callFromResultFrame)
		loc += "?callFromResultFrame=" + callFromResultFrame;
	openChildWinGeneric(loc, popupName, "400", "290", "yes", "80", "80", "yes");
}

function openAddAdjustmentPopup(callFromResultFrame) {
	var loc = "/tcmIS/supplier/addadjustmentpopup.do";
	if (callFromResultFrame)
		loc += "?callFromResultFrame=" + callFromResultFrame;
	openChildWinGeneric(loc, "addAdjustment", "370", "260", "yes", "80", "80", "yes");
}

function openInventoryPopup(callFromResultFrame, popupName) {
	var loc = "/tcmIS/supplier/editinventorypopup.do";
	if (callFromResultFrame)
		loc += "?callFromResultFrame=" + callFromResultFrame;
	openChildWinGeneric(loc, popupName, "450", "440", "yes", "80", "80", "yes");
}

function openConvertTransactionPopup(callFromResultFrame, popupName) {
	var loc = "/tcmIS/supplier/converttransactionpopup.do";
	if (callFromResultFrame)
		loc += "?callFromResultFrame=" + callFromResultFrame;
	openChildWinGeneric(loc, popupName, "450", "440", "yes", "80", "80", "yes");
}

function openChildWinGeneric(destination, WinName, WinWidth, WinHeight, Resizable, topCoor, leftCoor, scrollbars) {
	var preview = openWinGeneric(destination, WinName, WinWidth, WinHeight, Resizable, topCoor, leftCoor, scrollbars);
	try {
		children[children.length] = preview;
		showTransitWin();
	} catch (e) {}
	
	return preview;
}

function getInitialEditMinMaxPopupData(popupName, callFromResultFrame) {
	var popupData = new Array();
	popupData.popupTitle = messagesData.addPartEditMinMax;
	if (callFromResultFrame && callFromResultFrame === 'Y') {
		popupData.supplierId = {
			fixedElement : true,
			data : [
				{
					id : getResultFrameElementValue("supplier"),
					name : getResultFrameElementValue("supplierName")
				}
			]
		};
		popupData.locationId = {
			fixedElement : true,
			data : [
				{
					id : getResultFrameSelectedRowGridCellValue("shipFromLocationId"),
					name : getResultFrameSelectedRowGridCellValue("locationDesc")
				}
			]
		};
		popupData.catPartNo = {
			fixedElement : true,
			data : getResultFrameSelectedRowGridCellValue("catPartNo")
		};
		popupData.reorderPoint = {
			data : getResultFrameSelectedRowGridCellValue("reorderPoint")
		};
		popupData.stockingLevel = {
			data : getResultFrameSelectedRowGridCellValue("stockingLevel")
		};
		popupData.inventoryLevelId = {
			data : getResultFrameSelectedRowGridCellValue("inventoryLevelId")
		};
		popupData.isGFP = {
			data : getResultFrameSelectedRowGridCellValue("gfp")
		};
	} else {
		popupData.useAutoComplete = true;
		popupData.supplierId = {
			data : supplierArr,
			selectedId : $v("supplier")
		};
		popupData.locationId = {
			data : locationArr
		};
		if (!isWhitespace($v("shipFromLocationId")))
			popupData.locationId.selectedId = $v("shipFromLocationId");
	}
	
	return popupData;
}

/**
 * Validate data from editMinMax popup:
 * 	- Valid Part
 * 	- Reorder Point is a non-negative integer
 * 	- Stocking Level is a non-negative integer
 * 	- Reorder Point is less than or equals to Stocking Level
 * @param popupWindowObj
 * @param popupReorderPoint
 * @param popupStockingLevel
 * @returns
 */
function validateEditMinMaxPopupData(popupWindowObj, popupCatPartNo, popupReorderPoint, popupStockingLevel) {
	var errorMsg = "";
	if (isWhitespace(popupCatPartNo)) {
		errorMsg += messagesData.valueRequired.replace("{0}", messagesData.partNo) + "\n";
		popupWindowObj.$("catPartNo").focus();
	} else if (!popupWindowObj.isValidPart) {
		errorMsg += messagesData.valueNotFoundInDatabase.replace("{0}", popupWindowObj.$v("catPartNo")) + "\n";
		popupWindowObj.$("catPartNo").focus();
	}
	if (!isNonnegativeInteger(popupReorderPoint, false)) {
		errorMsg += messagesData.xxNonNegativeInteger.replace("{0}", messagesData.reorderPoint) + "\n";
		popupWindowObj.$("reorderPoint").focus();
	}
	if (!isNonnegativeInteger(popupStockingLevel, false)) {
		errorMsg += messagesData.xxNonNegativeInteger.replace("{0}", messagesData.stockingLevel) + "\n";
		popupWindowObj.$("stockingLevel").focus();
	}
	if (Number(popupReorderPoint) > Number(popupStockingLevel))
		errorMsg += messagesData.reorderPointMoreThanStockingLevel;
	
	if (!isWhitespace(errorMsg)) {
		popupWindowObj.alert(errorMsg);
		return false;
	} else
		return true;
}

function submitEditMinMaxPopup(popupObj, popupInventoryLevelId, popupSupplierId, popupLocationId, popupCatPartNo, popupReorderPoint, popupStockingLevel) {
	if (validateEditMinMaxPopupData(popupObj, popupCatPartNo, popupReorderPoint, popupStockingLevel)) {
		var dataObj = {
			uAction : "addPartUpdateMinMax",
			inventoryLevelId : popupInventoryLevelId,
			supplier : popupSupplierId,
			shipFromLocationId : popupLocationId,
			catPartNo : popupCatPartNo,
			reorderPoint : popupReorderPoint,
			stockingLevel : popupStockingLevel
		};
		makeGenericAjaxCall(popupObj, dataObj, true);
	}
}

function getInitialEditInventoryPopupData(popupName, callFromResultFrame) {
	var popupData = new Array();
	popupData.useAutoComplete = true;
	switch(popupName) {
		case "addInventory":
			popupData.popupTitle = messagesData.addInventory;
			if (callFromResultFrame && callFromResultFrame === 'Y') {
				popupData.domRequired = getResultFrameSelectedRowGridCellValue("domRequired") == "Y" ? true : false;
				popupData.vendorPO = {
					data : getResultFrameSelectedRowGridCellValue("supplierPoNumber")
				};
				popupData.supplierId = {
					fixedElement : true,
					data : [
						{
							id : getResultFrameElementValue("supplier"),
							name : getResultFrameElementValue("supplierName")
						}
					]
				};
				popupData.locationId = {
					fixedElement : true,
					data : [
						{
							id : getResultFrameSelectedRowGridCellValue("shipFromLocationId"),
							name : getResultFrameSelectedRowGridCellValue("locationDesc")
						}
					]
				};
				popupData.catPartNo = {
					fixedElement : true,
					data : getResultFrameSelectedRowGridCellValue("catPartNo")
				};
				popupData.quantity = {
					data : getResultFrameSelectedRowGridCellValue("quantity")
				};
				popupData.lot = {
					data : getResultFrameSelectedRowGridCellValue("mfgLot")
				};
				popupData.expirationDate = {
					data : getResultFrameSelectedRowGridCellValue("expireDate")
				};
				popupData.dateOfManufacture = {
					data : getResultFrameSelectedRowGridCellValue("dateOfManufacture")
				};
				popupData.bolTrackingNum = {
					data : getResultFrameSelectedRowGridCellValue("bolTrackingNum")
				};
				popupData.bin = {
					data : getResultFrameSelectedRowGridCellValue("bin")
				};
			} else {
				popupData.supplierId = {
					data : supplierArr,
					selectedId : $v("supplier")
				};
				popupData.locationId = {
					data : locationArr
				};
				if (!isWhitespace($v("shipFromLocationId")))
					popupData.locationId.selectedId = $v("shipFromLocationId");
			}
			break;
		case "receivePOInventory":
			popupData.popupTitle = messagesData.receivePOInventory;
			popupData.domRequired = getResultFrameSelectedRowGridCellValue("domRequired") == "Y" ? true : false;
			popupData.vendorPO = {
				fixedElement : true,
				data : getResultFrameSelectedRowGridCellValue("supplierPoNumber")
			};
			popupData.supplierId = {
				fixedElement : true,
				data : [
					{
						id : getResultFrameElementValue("supplier"),
						name : getResultFrameElementValue("supplierName")
					}
				]
			};
			popupData.locationId = {
				fixedElement : true,
				data : [
					{
						id : getResultFrameSelectedRowGridCellValue("shipFromLocationId"),
						name : getResultFrameSelectedRowGridCellValue("locationDesc")
					}
				]
			};
			popupData.catPartNo = {
				fixedElement : true,
				data : getResultFrameSelectedRowGridCellValue("catPartNo")
			};
			popupData.quantity = {
				data : getResultFrameSelectedRowGridCellValue("quantity")
			};
			popupData.isVMI = {
				fixedElement : true,
				checked : getResultFrameSelectedRowGridCellValue("vmi") == "Y" ? true : false
			};
			popupData.notes = {
				data : getResultFrameSelectedRowGridCellValue("notes")
			};
			break;
		case "editInventory":
			popupData.popupTitle = messagesData.editInventory;
			popupData.domRequired = getResultFrameSelectedRowGridCellValue("domRequired") == "Y" ? true : false;
			popupData.vendorPO = {
				fixedElement : true,
				data : getResultFrameSelectedRowGridCellValue("supplierPoNumber")
			};
			popupData.supplierId = {
				fixedElement : true,
				data : [
					{
						id : getResultFrameElementValue("supplier"),
						name : getResultFrameElementValue("supplierName")
					}
				]
			};
			popupData.locationId = {
				fixedElement : true,
				data : [
					{
						id : getResultFrameSelectedRowGridCellValue("shipFromLocationId"),
						name : getResultFrameSelectedRowGridCellValue("locationDesc")
					}
				]
			};
			popupData.catPartNo = {
				fixedElement : true,
				data : getResultFrameSelectedRowGridCellValue("catPartNo")
			};
			popupData.quantity = {
				fixedElement : true,
				data : getResultFrameSelectedRowGridCellValue("quantity")
			};
			popupData.lot = {
				data : getResultFrameSelectedRowGridCellValue("mfgLot")
			};
			popupData.expirationDate = {
				data : getResultFrameSelectedRowGridCellValue("expireDate")
			};
			popupData.dateOfManufacture = {
				data : getResultFrameSelectedRowGridCellValue("dateOfManufacture")
			};
			popupData.bolTrackingNum = {
				data : getResultFrameSelectedRowGridCellValue("bolTrackingNum")
			};
			popupData.bin = {
				data : getResultFrameSelectedRowGridCellValue("bin")
			};
			popupData.inventoryId = {
				data : getResultFrameSelectedRowGridCellValue("inventoryId")
			};
			popupData.isVMI = {
				fixedElement : true,
				checked : getResultFrameSelectedRowGridCellValue("vmi") == "Y" ? true : false
			};
			popupData.notes = {
				data : getResultFrameSelectedRowGridCellValue("notes")
			};
			break;
	}
	
	return popupData;
}

/**
 * Validate data from editInventory popup:
 * 	- Valid Part
 * 	- If required, dateOfManufacture is not empty
 * 	- Quantity is a positive integer
 *  - Lot and expirationDate is not empty
 *  - If a bin is entered, it must be active
 * @param popupWindowObj
 * @param popupReorderPoint
 * @param popupStockingLevel
 * @returns
 */
function validateEditInventoryPopupData(popupWindowObj, popupCatPartNo, popupQuantity, popupLot, popupExpirationDate, popupBin, popupNotes) {
	var errorMsg = "";
	if (isWhitespace(popupCatPartNo)) {
		errorMsg += messagesData.valueRequired.replace("{0}", messagesData.partNo) + "\n";
		popupWindowObj.$("catPartNo").focus();
	} else if (!popupWindowObj.isValidPart) {
		errorMsg += messagesData.valueNotFoundInDatabase.replace("{0}", popupWindowObj.$v("catPartNo")) + "\n";
		popupWindowObj.$("catPartNo").focus();
	}
	if (!popupWindowObj.isValidDOM)
		errorMsg += messagesData.valueRequired.replace("{0}", messagesData.dateOfManufacture) + "\n";
	if (!isPositiveInteger(popupQuantity)) {
		errorMsg += messagesData.xxPositiveInteger.replace("{0}", messagesData.quantity) + "\n";
		popupWindowObj.$("quantity").focus();
	}
	if (isWhitespace(popupLot))
		errorMsg += messagesData.valueRequired.replace("{0}", messagesData.mfgLot) + "\n";
	if (isWhitespace(popupExpirationDate))
		errorMsg += messagesData.valueRequired.replace("{0}", messagesData.expirationDate) + "\n";
	if (!isWhitespace(popupBin) && !popupWindowObj.isValidBin) {
		errorMsg += messagesData.valueNotFoundInDatabase.replace("{0}", popupWindowObj.$v("bin")) + "\n";
		popupWindowObj.$("bin").focus();
	}
	if (popupNotes.length > 200) {
		errorMsg += messagesData.exceedMaxLength.replace("{0}", messagesData.notes).replace("{1}", 200) + "\n";
		popupWindowObj.$("notes").focus();
	}
	
	if (!isWhitespace(errorMsg)) {
		popupWindowObj.alert(errorMsg);
		return false;
	} else
		return true;
}

function submitEditInventoryPopup(popupObj, popupInventoryId, popupVendorPO, popupSupplierId, popupLocationId, popupCatPartNo, popupQuantity, popupLot, popupExpirationDate, popupDateOfManufacture, popupBOLTrackingNum, popupBin, isVMI, popupNotes) {
	if (validateEditInventoryPopupData(popupObj, popupCatPartNo, popupQuantity, popupLot, popupExpirationDate, popupBin, popupNotes)) {
		if (popupObj.name === "addInventory" && isVMI === "Y")
			if (!popupObj.confirm(messagesData.addVMIInventoryCheck))
				return;
		
		var dataObj = {
			uAction : "updateInsertInventory",
			inventoryId : popupInventoryId,
			supplierPoNumber : popupVendorPO,
			supplier : popupSupplierId,
			shipFromLocationId : popupLocationId,
			catPartNo : popupCatPartNo,
			quantity : popupQuantity,
			mfgLot : popupLot,
			expireDate : popupExpirationDate,
			dateOfManufacture : popupDateOfManufacture,
			bolTrackingNum : popupBOLTrackingNum,
			bin : popupBin,
			vmi : isVMI,
			notes : popupNotes
		};
		makeGenericAjaxCall(popupObj, dataObj, true);
	}
}

function getInitialEditPOPopupData(popupName, callFromResultFrame) {
	var popupData = new Array();
	switch(popupName) {
		case "addPO":
			popupData.popupTitle = messagesData.addPO;
			popupData.useAutoComplete = true;
			if (callFromResultFrame && callFromResultFrame === 'Y') {
				popupData.supplierId = {
					fixedElement : true,
					data : [
						{
							id : getResultFrameElementValue("supplier"),
							name : getResultFrameElementValue("supplierName")
						}
					]
				};
				popupData.locationId = {
					data : locationArr,
					selectedId : getResultFrameSelectedRowGridCellValue("shipFromLocationId")
				};
				popupData.catPartNo = {
					data : getResultFrameSelectedRowGridCellValue("catPartNo")
				};
				popupData.quantity = {
					data : getResultFrameSelectedRowGridCellValue("quantity")
				};
				popupData.isBlanketPO = {
					checked : getResultFrameSelectedRowGridCellValue("blanketPo") == "Y" ? true : false
				};
			} else {
				popupData.supplierId = {
					data : supplierArr,
					selectedId : $v("supplier")
				};
				popupData.locationId = {
					data : locationArr
				};
				if (!isWhitespace($v("shipFromLocationId")))
					popupData.locationId.selectedId = $v("shipFromLocationId");
			}
			break;
		case "editPO":
			popupData.popupTitle = messagesData.editPO;
			popupData.useAutoComplete = true;
			popupData.supplierId = {
				fixedElement : true,
				data : [
					{
						id : getResultFrameElementValue("supplier"),
						name : getResultFrameElementValue("supplierName")
					}
				]
			};
			popupData.locationId = {
				data : locationArr,
				selectedId : getResultFrameSelectedRowGridCellValue("shipFromLocationId")
			};
			popupData.catPartNo = {
				data : getResultFrameSelectedRowGridCellValue("catPartNo")
			};
			popupData.poNumber = {
				data : getResultFrameSelectedRowGridCellValue("supplierPoNumber")
			};
			popupData.quantity = {
				data : getResultFrameSelectedRowGridCellValue("quantity")
			};
			popupData.transactionId = {
				data : getResultFrameSelectedRowGridCellValue("transactionId")
			};
			popupData.isBlanketPO = {
				checked : getResultFrameSelectedRowGridCellValue("blanketPo") == "Y" ? true : false
			};
			popupData.notes = {
				data : getResultFrameSelectedRowGridCellValue("notes")
			};
			break;
	}
	
	return popupData;
}

/**
 * Validate data from editPO popup:
 * 	- Valid Part
 *  - PO Number is not empty
 * 	- Quantity is a non-negative integer
 * @param popupWindowObj
 * @param popupReorderPoint
 * @param popupStockingLevel
 * @returns
 */
function validateEditPOPopupData(popupWindowObj, popupCatPartNo, popupPONumber, popupQuantity, popupNotes) {
	var errorMsg = "";
	if (isWhitespace(popupCatPartNo)) {
		errorMsg += messagesData.valueRequired.replace("{0}", messagesData.partNo) + "\n";
		popupWindowObj.$("catPartNo").focus();
	} else if (!popupWindowObj.isValidPart) {
		errorMsg += messagesData.valueNotFoundInDatabase.replace("{0}", popupWindowObj.$v("catPartNo")) + "\n";
		popupWindowObj.$("catPartNo").focus();
	}
	if (isWhitespace(popupPONumber)) {
		errorMsg += messagesData.valueRequired.replace("{0}", messagesData.poNumber) + "\n";
		popupWindowObj.$("poNumber").focus();
	}
	if (!isNonnegativeInteger(popupQuantity)) {
		errorMsg += messagesData.xxNonNegativeInteger.replace("{0}", messagesData.quantity) + "\n";
		popupWindowObj.$("quantity").focus();
	}
	if (popupNotes.length > 200) {
		errorMsg += messagesData.exceedMaxLength.replace("{0}", messagesData.notes).replace("{1}", 200) + "\n";
		popupWindowObj.$("notes").focus();
	}
	if (!isWhitespace(errorMsg)) {
		popupWindowObj.alert(errorMsg);
		return false;
	} else
		return true;
}

function submitEditPOPopup(popupObj, popupTransactionId, popupSupplierId, popupLocationId, popupCatPartNo, popupPONumber, popupQuantity, popupBlanketPO, popupNotes) {
	if (validateEditPOPopupData(popupObj, popupCatPartNo, popupPONumber, popupQuantity, popupNotes)) {
		var dataObj = {
				uAction : "updateInsertPO",
				transactionId : popupTransactionId,
				supplier : popupSupplierId,
				shipFromLocationId : popupLocationId,
				catPartNo : popupCatPartNo,
				supplierPoNumber : popupPONumber,
				quantity : popupQuantity,
				blanketPo : popupBlanketPO,
				notes : popupNotes
			};
		makeGenericAjaxCall(popupObj, dataObj, true);
	}
}

function getInitialAddAdjustmentPopupData() {
	var popupData = new Array();
	popupData.popupTitle = messagesData.addAdjustment;
	popupData.locationId = {
		fixedElement : true,
		data : [
			{
				id : getResultFrameSelectedRowGridCellValue("shipFromLocationId"),
				name : getResultFrameSelectedRowGridCellValue("locationDesc")
			}
		]
	};
	popupData.catPartNo = {
		fixedElement : true,
		data : getResultFrameSelectedRowGridCellValue("catPartNo")
	};
	popupData.inventoryId = {
		fixedElement : true,
		data : getResultFrameSelectedRowGridCellValue("inventoryId")
	};
	popupData.adjustedQuantity = {
		data : getResultFrameSelectedRowGridCellValue("availableQty") * -1
	};
	popupData.status = {
		data : window.frames["resultFrame"]["inventoryAdjCode"]
	};
	if (!isWhitespace(getResultFrameSelectedRowGridCellValue("status")))
		popupData.status.selectedId = getResultFrameSelectedRowGridCellValue("status");
	
	return popupData;
}

/**
 * Validate data from addAdjustment popup:
 * 	- Quantity is a negative integer
 * @param popupWindowObj
 * @param popupReorderPoint
 * @param popupStockingLevel
 * @returns
 */
function validateAddAdjustmentPopupData(popupWindowObj, popupQuantity, popupStatus, popupNotes) {
	var errorMsg = "";
	if (!isNegativeInteger(popupQuantity)) {
		errorMsg += messagesData.xxNegativeInteger.replace("{0}", messagesData.quantity) + "\n";
		popupWindowObj.$("adjustedQuantity").focus();
	}
	if (popupNotes.length > 200) {
		errorMsg += messagesData.exceedMaxLength.replace("{0}", messagesData.notes).replace("{1}", 200) + "\n";
		popupWindowObj.$("notes").focus();
	}
	
	if (!isWhitespace(errorMsg)) {
		popupWindowObj.alert(errorMsg);
		return false;
	} else
		return true;
}

function submitAddAdjustmentPopup(popupObj, popupLocationId, popupCatPartNo, popupInventoryId, popupAdjustedQuantity, popupStatus, popupNotes) {
	if (popupObj && validateAddAdjustmentPopupData(popupObj, popupAdjustedQuantity, popupStatus, popupNotes)) {
		var dataObj = {
			uAction : "addAdjustment",
			shipFromLocationId : popupLocationId,
			catPartNo : popupCatPartNo,
			inventoryId : popupInventoryId,
			quantity : popupAdjustedQuantity,
			status : popupStatus,
			notes : popupNotes
		};
		makeGenericAjaxCall(popupObj, dataObj, true);
	}
}

function getInitialConvertTransactionPopupData(popupName) {
	var popupData = new Array();
	popupData.transactionId = {
		data : getResultFrameSelectedRowGridCellValue("transactionId")
	};
	popupData.transactionType = {
		data : getResultFrameSelectedRowGridCellValue("transactionType")
	};
	popupData.supplierId = {
		fixedElement : true,
		data : [
			{
				id : getResultFrameElementValue("supplier"),
				name : getResultFrameElementValue("supplierName")
			}
		]
	};
	popupData.locationId = {
		fixedElement : true,
		data : [
			{
				id : getResultFrameElementValue("shipFromLocationId"),
				name : getResultFrameElementValue("locationDesc")
			}
		]
	};
	popupData.catPartNo = {
		fixedElement : true,
		data : getResultFrameSelectedRowGridCellValue("catPartNo")
	};
	popupData.conversionGroup = {
		fixedElement : true,
		data : getResultFrameSelectedRowGridCellValue("conversionGroup")
	};
	popupData.originalQty = {
		fixedElement : true,
		data : getResultFrameSelectedRowGridCellValue("quantity")
	};
	
	switch(popupName) {
		case "convertPO":
			popupData.popupTitle = messagesData.convertPO;
			popupData.availableQty = {
				fixedElement : true,
				data : getResultFrameSelectedRowGridCellValue("openQty")
			};
			break;
		case "convertInventory":
			popupData.popupTitle = messagesData.convertInventory;
			popupData.availableQty = {
				fixedElement : true,
				data : getResultFrameSelectedRowGridCellValue("availableQty")
			};
			break;
	}
	
	return popupData;
}

function validateConvertTransactionPopupData(popupWindowObj, popupConvertToCatPartNo, popupAvailableQty, popupQtyToConvert, popupNotes, defaultNotes) {
	var errorMsg = "";
	if (isWhitespace(popupConvertToCatPartNo)) {
		errorMsg += messagesData.valueRequired.replace("{0}", messagesData.convertTo) + "\n";
	}
	if (!isPositiveInteger(popupQtyToConvert)) {
		errorMsg += messagesData.xxPositiveInteger.replace("{0}", messagesData.quantity) + "\n";
		popupWindowObj.$("qtyToConvert").focus();
	} else if (Number(popupAvailableQty) < Number(popupQtyToConvert)) {
		errorMsg += messagesData.xCannotBeMoreThanY.replace("{0}", messagesData.qtyToConvert).replace("{1}", messagesData.availableQty) + "\n";
		popupWindowObj.$("qtyToConvert").focus();
	}
	if (popupNotes.length > 200 - defaultNotes.length) {
		errorMsg += messagesData.exceedMaxLength.replace("{0}", messagesData.notes).replace("{1}", 200 - defaultNotes.length) + "\n";
		popupWindowObj.$("notes").focus();
	}
	
	if (!isWhitespace(errorMsg)) {
		popupWindowObj.alert(errorMsg);
		return false;
	} else
		return true;
}

function submitConvertTransactionPopup(popupObj, popupTransactionId, popupTransactionType, popupSupplierId, popupLocationId, popupCatPartNo, popupConvertToCatPartNo, popupOriginalQty, popupAvailableQty, popupQtyToConvert, popupNotes) {
	var defaultNotes = messagesData.convertPartWithQty.replace("{0}", popupCatPartNo).replace("{1}", popupQtyToConvert);
	if (validateConvertTransactionPopupData(popupObj, popupConvertToCatPartNo, popupAvailableQty, popupQtyToConvert, popupNotes, defaultNotes)) {
		var dataObj = {
			uAction : "convertTransaction",
			transactionId : popupTransactionId,
			transactionType : popupTransactionType,
			catPartNo : popupConvertToCatPartNo,
			quantity : popupQtyToConvert,
			notes : defaultNotes + " " + popupNotes
		};
		makeGenericAjaxCall(popupObj, dataObj, true);
	}
}

function getInitialEditBinPopupData() {
	var popupData = new Array();
	popupData.popupTitle = messagesData.manageStorageLocations;
	popupData.useAutoComplete = true;
	
	return popupData;
}

function validateEditBinPopupData(popupWindowObj, popupBin) {
	var errorMsg = "";
	if (isWhitespace(popupBin)) {
		errorMsg += messagesData.valueRequired.replace("{0}", messagesData.storageLocation) + "\n";
		popupWindowObj.$("bin").focus();
	}
	
	if (!isWhitespace(errorMsg)) {
		popupWindowObj.alert(errorMsg);
		return false;
	} else
		return true;
}

function submitEditBinPopup(popupObj, popupSupplierId, popupLocationId, popupBin, popupIsActive) {
	if (validateEditBinPopupData(popupObj, popupBin)) {
		var dataObj = {
			uAction : "updateInsertBin",
			supplier : popupSupplierId,
			shipFromLocationId : popupLocationId,
			bin : popupBin,
			active : popupIsActive
		};
		makeGenericAjaxCall(popupObj, dataObj, false);
	}
}

/**
 * Makes preparations for an Ajax call
 * @param popupObj
 * @param dataObj
 * @param allowSearchReload
 * @returns
 */
function makeGenericAjaxCall(popupObj, dataObj, allowSearchReload) {
	inProcessPopupWindowObj = popupObj;
	doSearchReload = typeof allowSearchReload === "boolean" ? allowSearchReload : true;
	//remove keys that don't have a value, since some Java types cannot handle null case
	var newDataObj = {};
	for (var key in dataObj)
		if (dataObj[key] != null && typeof dataObj[key] != 'undefined')
			newDataObj[key] = dataObj[key];
	
	j$.ajax({
	    type: "POST",
	    url: "/tcmIS/supplier/inventorymanagementmain.do",
		data: newDataObj,
		success : submitPopupComplete
	});
}

function submitPopupComplete(msg) {
	if (inProcessPopupWindowObj && inProcessPopupWindowObj.doAfterSubmit)
		inProcessPopupWindowObj.doAfterSubmit(msg);
	else if (msg === "OK") {
		if (inProcessPopupWindowObj)
			inProcessPopupWindowObj.close();
		if (window.frames["resultFrame"] && doSearchReload)
			window.frames["resultFrame"].reloadPreviousSearch();
	} else {
		if (inProcessPopupWindowObj)
			inProcessPopupWindowObj.alert(msg);
		else
			alert(msg);
	}
}

function getResultFrameSelectedRowGridCellValue(columnId) {
	return window.frames["resultFrame"].gridCellValue(window.frames["resultFrame"].beanGrid, window.frames["resultFrame"].selectedRowId, columnId);
}

function getResultFrameElementValue(elementId) {
	return window.frames["resultFrame"].$v(elementId);
}

function closeAllchildren() {
	try {
		for (var n = 0; n < children.length; n++) {
			try {
				children[n].closeAllchildren();
			} catch (ex) {}
			children[n].close();
		}
	} catch (ex) {}
	children = new Array();
}