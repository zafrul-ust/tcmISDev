function receiptObj(rcptFormObj) {
	debug("new receiptObj");
	if (typeof (rcptFormObj) != "undefined") {
		var thisDetail = inboundShipment.details[inboundShipment.currentDetail]
		var thisItem = thisDetail.itemList.items[thisDetail.itemList.currentItem];

		this.inboundShipmentDetailId = thisDetail.inboundShipmentDetailId;
		this.receiptId = rcptFormObj.receiptIdInput.value;
		// All receipts saved as QC Ready
		this.receivingStatus = "QC Ready";

		this.trxType = rcptFormObj.hiddenTransactionType.value;
		this.poLine = rcptFormObj.poLineInput.value;
		this.itemId = thisItem.components[0].itemId;
		// this.lot = rcptFormObj.lotNumberInput.value;
		this.hubId = thisItem.poLines[rcptFormObj.hiddenPoLineIndex.value].hubId;
		this.poNumber = thisItem.poLines[rcptFormObj.hiddenPoLineIndex.value].poNumber;
		this.manageKitsAsSingleUnit = thisItem.poLines[rcptFormObj.hiddenPoLineIndex.value].manageKitsAsSingleUnit;
		this.poLineQtyOpen = thisItem.poLines[rcptFormObj.hiddenPoLineIndex.value].qtyOpen;
		this.inventoryGroup = thisItem.poLines[rcptFormObj.hiddenPoLineIndex.value].inventoryGroup;
		this.definedShelfLifeItem = thisItem.poLines[rcptFormObj.hiddenPoLineIndex.value].definedShelfLifeItem;
		this.definedShelfLifeBasis = thisItem.poLines[rcptFormObj.hiddenPoLineIndex.value].definedShelfLifeBasis;
		this.accountNumber = ifDefined(thisItem.poLines[rcptFormObj.hiddenPoLineIndex.value].accountNumber);
		this.accountNumber2 = ifDefined(thisItem.poLines[rcptFormObj.hiddenPoLineIndex.value].accountNumber2);
		this.accountNumber3 = ifDefined(thisItem.poLines[rcptFormObj.hiddenPoLineIndex.value].accountNumber3);
		this.accountNumber4 = ifDefined(thisItem.poLines[rcptFormObj.hiddenPoLineIndex.value].accountNumber4);
		this.customerReceiptId = ifDefined(thisItem.poLines[rcptFormObj.hiddenPoLineIndex.value].customerReceiptId);
		
		this.bin = rcptFormObj.binInput.value;

		this.dateOfReceipt = inboundShipment.dateOfReceipt;
		//this.initialScanDate = getServerDateTime("Time");
		this.docType = thisItem.poLines[rcptFormObj.hiddenPoLineIndex.value].transtype;
		this.dateOfShipment = rcptFormObj.DOS.value;
		this.dateOfManufacture = rcptFormObj.DOM.value;
		this.vendorShipDate = rcptFormObj.ASSD.value;
		this.unitLabelPrinted = rcptFormObj.chkBox129P.checked;
		this.hazComLabelFlag = mapBoolToYesNo(rcptForm.chkBoxHazCom.checked);
		this.receivingNotes = rcptFormObj.hiddenReceiptNotes.value;
		this.internalReceivingNotes = rcptFormObj.hiddenInternalReceiptNotes.value;
		this.components = new Array();
		this.imageList = receiptImageList;
		this.receiptListIndex = rcptFormObj.hiddenReceiptListIndex.value;
		this.origin = "entry";
		this.mvItem = thisItem.components[0].mvItem;
		this.newMsdsRevReceivedFlag = thisItem.newMsdsRevReceivedFlag;
		var componentCount = thisItem.components.length;
		debug("receiptObj " + componentCount + " components");

		if (componentCount == 1) {
			var thisComponent = new Object();
			thisComponent.lot = rcptFormObj.lotNumberInput.value;
			thisComponent.quantity = rcptFormObj.quantityInput.value;
			thisComponent.mvQty = rcptFormObj.mvQtyInput.value;
			thisComponent.mvSize = rcptFormObj.mvSizeInput.value;
			if (this.definedShelfLifeItem == "N") 
				thisComponent.expireDate = rcptFormObj.expDate.value;			
			this.components.push(thisComponent);
		}
		else {
			for ( var x = 0; x < componentCount; x++) {
				var thisComponent = new Object();
				thisComponent.lot = rcptFormObj.lotNumberInput[x].value;
				thisComponent.quantity = rcptFormObj.quantityInput.value;
				thisComponent.mvQty = rcptFormObj.mvQtyInput.value;
				thisComponent.mvSize = rcptFormObj.mvSizeInput.value;
				if (this.definedShelfLifeItem == "N") 
					thisComponent.expireDate = rcptFormObj.expDate[x].value;				
				this.components.push(thisComponent);
			}
		}
		
		this.ghsLabelFlag = rcptForm.chkBoxGHS.checked;
		if(this.ghsLabelFlag){
			this.productName = true;
			this.signalWord = true;
			this.pictogram = true;
			this.hazardStatement = true;
			this.supplierInfo = true;
			this.precautionaryStatement = true;

		}
		else{
			this.productName = rcptFormObj.hiddenGHSProductName.value == "true" ? true : false;
			this.signalWord = rcptFormObj.hiddenGHSSignalWord.value == "true" ? true : false;
			this.pictogram = rcptFormObj.hiddenGHSPictogram.value == "true" ? true : false;
			this.hazardStatement = rcptFormObj.hiddenGHSHazardStatement.value == "true" ? true : false;
			this.precautionaryStatement = rcptFormObj.hiddenGHSPrecautionaryStatement.value == "true" ? true : false;
			this.supplierInfo = rcptFormObj.hiddenGHSSupplierIdentification.value == "true" ? true : false;
		}
	}
}

receiptObj.prototype.receiptImport = function(rcptData) {
	debug("import receiptObj");
	var thisDetail = currentShipmentDetail();
	var thisItem = currentItem();

	this.inboundShipmentDetailId = thisDetail.inboundShipmentDetailId;
	this.receiptId = rcptData.receiptId;
	this.receivingStatus = rcptData.receivingStatus;
	this.poLine = rcptData.poLine;
	this.itemId = rcptData.itemId;
	this.hubId = rcptData.hub;
	this.poNumber = rcptData.poNumber;
	// this.manageKitsAsSingleUnit = thisItem.poLines[0].manageKitsAsSingleUnit;
	// this.inventoryGroup = thisItem.poLines[0].inventoryGroup;
	this.definedShelfLifeItem = rcptData.definedShelfLifeItem;
	this.definedShelfLifeBasis = rcptData.definedShelfLifeBasis;
	this.accountNumber = rcptData.accountNumber;
	this.accountNumber2 = rcptData.accountNumber2;
	this.accountNumber3 = rcptData.accountNumber3;
	this.accountNumber4 = rcptData.accountNumber4;
	this.customerReceiptId = rcptData.customerReceiptId;

	this.bin = rcptData.bin;
	this.dateOfReceipt = rcptData.dateOfReceipt;
	this.receiverId = rcptData.receiverId;
	if (rcptData.radianPo) {
		this.trxType = receivingMessages.label_purchaseorder;
	}
	else if (rcptData.transferRequestId) {
		// debug("Setting trxType = Transfer Request");
		this.trxType = receivingMessages.tablet_transferrequest;
	}
	else if (rcptData.customerRmaId) {
		debug("Setting trxType = RMA");
		this.trxType = receivingMessages.label_rma;
	}
	else if (rcptData.docNum) {
		// debug("Setting trxType = Customer Owned Inventory");
		this.trxType = receivingMessages.tablet_customerownedinventory;
	}
	this.dateOfShipment = rcptData.dateOfShipment;
	this.dateOfManufacture = rcptData.dateOfManufacture;
	this.vendorShipDate = rcptData.vendorShipDate;
	this.unitLabelPrinted = rcptData.unitLabelPrinted;
	this.hazComLabelFlag = rcptData.hazcomLabelFlag;
	/*
	 * All receipts will be QA Ready - qa ready flag no longer reqd SSS 8/7/13 if(rcptData.receivingStatus == "QC Ready"){ this.qaReadyFlag = true; } else{ this.qaReadyFlag = false; }
	 */
	this.receivingNotes = rcptData.notes;
	this.internalReceivingNotes = rcptData.internalReceiptNotes;
	this.origin = "import";
	this.components = new Array();
	// this.images = receiptImageList.images;
	// this.receiptListIndex = rcptFormObj.hiddenReceiptListIndex.value;
	if (typeof (rcptData.components) == "undefined") {
		var thisComponent = new Object();
		thisComponent.lot = rcptData.mfgLot;
		thisComponent.quantity = rcptData.quantityReceived;
		thisComponent.expireDate = rcptData.expireDate;
		this.components.push(thisComponent);
	}
	else {
		for ( var x = 0; x < rcptData.components.length; x++) {
			var thisComponent = new Object();
			thisComponent.lot = rcptData.components[x].mfgLot;
			thisComponent.quantity = rcptData.quantityReceived;
			thisComponent.expireDate = rcptData.components[x].expireDate;
			this.components.push(thisComponent);
		}
	}
	
	this.productName = rcptData.ghsLabelReqs.productName;
	this.signalWord = rcptData.ghsLabelReqs.signalWord;
	this.pictogram = rcptData.ghsLabelReqs.pictogram;
	this.hazardStatement = rcptData.ghsLabelReqs.hazardStatement;
	this.supplierInfo = rcptData.ghsLabelReqs.supplierInfo;
	this.precautionaryStatement = rcptData.ghsLabelReqs.precautionaryStatement;
	
	this.ghsLabelFlag = true;
	if(!this.productName || !this.signalWord ||
			!this.pictogram || !this.hazardStatement || 
			!this.supplierInfo || !this.precautionaryStatement)
		this.ghsLabelFlag = false;
	
	this.imageList = new receiptImageListObj();
	this.getImages();
	this.receiptListIndex = -1;
}

function receiptListObj() {
	debug("receiptListObj");
	this.currentReceipt;
	this.receipts = new Array();
}

receiptObj.prototype.validate = function() {

	return "error message";
	return "OK";
}

// receiptObj.prototype.displayDiv = function(itemObj, currentReceipt){
receiptObj.prototype.displayDiv = function() {
	debug("receiptObj.displayDiv");
	// skip the div if this receipt was edited instead of created
	if (this.receiptListIndex == -1) {
		var rcptDivHTML = "<div class='receiptRow' id='receipt_" + this.receiptId + "'></div>";
		$("#receiptViewContent").append(rcptDivHTML);
	}
	var rcptObjHTML = "<div class='rcptSeparator'><b>Receipt ID:</b> " + this.receiptId + "</div>";
	rcptObjHTML += "<div class='ui-grid-a'>";
	rcptObjHTML += "<div class='ui-block-a' style='width:80%;'>";
	
	rcptObjHTML += "<div class='ui-grid-b'>";
	rcptObjHTML += "<div class='ui-block-a'>";
	rcptObjHTML += "<b>PO-Line:</b> " + this.poLine + "<br>";
	rcptObjHTML += "</div>";
	rcptObjHTML += "<div class='ui-block-b'>";
	rcptObjHTML += "<b>HAZCOM Labeling:</b> " + this.hazComLabelFlag + "<br>";
	rcptObjHTML += "</div>";
	rcptObjHTML += "<div class='ui-block-c'>";
	rcptObjHTML += "<b>Date of Mfg:</b> " + ifDefined(this.dateOfManufacture) + "<br>";
	rcptObjHTML += "</div>";
	rcptObjHTML += "</div>";
	
	rcptObjHTML += "<div class='ui-grid-b'>";
	rcptObjHTML += "<div class='ui-block-a'>";
	rcptObjHTML += "<b>Date of Receipt:</b> " + ifDefined(this.dateOfReceipt) + "<br>";
	rcptObjHTML += "</div>";
	rcptObjHTML += "<div class='ui-block-b'>";
	if (currentItem().poLines[0].polchemIg == "Y" && ifDefined(this.unitLabelPrinted) != "") {
		rcptObjHTML += "<b>Labeled IAW 129P:</b> " + this.unitLabelPrinted + "<br>";
	}
	rcptObjHTML += "</div>";
	rcptObjHTML += "<div class='ui-block-c'>";
	rcptObjHTML += "<b>Mfg Ship Date:</b> " + ifDefined(this.dateOfShipment) + "<br>";
	rcptObjHTML += "</div>";
	rcptObjHTML += "</div>";
	
	rcptObjHTML += "<div class='ui-grid-b'>";
	rcptObjHTML += "<div class='ui-block-a'>";
	rcptObjHTML += "<b>Bin:</b> " + this.bin + "<br>";
	rcptObjHTML += "</div>";
	rcptObjHTML += "<div class='ui-block-b'>";
	rcptObjHTML += "</div>";
	rcptObjHTML += "<div class='ui-block-c'>";
	rcptObjHTML += "<b>Supplier Ship Date:</b> " + ifDefined(this.vendorShipDate) + "<br>";
	rcptObjHTML += "</div>";
	rcptObjHTML += "</div>";
	
	for ( var x = 0; x < this.components.length; x++) {
		if (this.components.length > 1) {
			rcptObjHTML += "<div class='componentSeparator'><b>Component: " + (x + 1) + " - </b>" + ifDefined(currentItem().components[x].materialDesc) + "</div>";
		}
		rcptObjHTML += "<div class='ui-grid-b'>";
		rcptObjHTML += "<div class='ui-block-a'>";
		rcptObjHTML += "<b>Lot:</b> " + ifDefined(this.components[x].lot);
		rcptObjHTML += "</div>";
		rcptObjHTML += "<div class='ui-block-b'>";
		// only display qty for first component
		if (x == 0) {
			if (ifDefined(this.mvItem).trim() == "Y") {
				var qtyString = this.components[x].mvQty + " x " + this.components[x].mvSize + " " + currentItem().components[x].purchasingUnitOfMeasure + " " + currentItem().components[x].displayPkgStyle;
			}
			else {
				var qtyString = this.components[x].quantity + " (" + currentItem().components[x].displayPkgStyle + ")";
			}
			rcptObjHTML += "<b>Quantity:</b> " + qtyString;
		}
		rcptObjHTML += "</div>";
		rcptObjHTML += "<div class='ui-block-c'>";
		rcptObjHTML += "<b>Label Expire Date:</b> ";
		if (this.components[x].expireDate == "1/1/3000") {
			rcptObjHTML += "Indefinite";
		}
		else {
			rcptObjHTML += ifDefined(this.components[x].expireDate);
		}
		rcptObjHTML += "</div>";
		rcptObjHTML += "</div>";
	}
	
	rcptObjHTML += "<div class='ui-grid-b'>";
	rcptObjHTML += "<div class='ui-block-a'>";
	rcptObjHTML += "<b>GHS Label Requirements:</b><br>";
	rcptObjHTML += "</div>";
	rcptObjHTML += "<div class='ui-block-b'>";
	rcptObjHTML += "<b>Product Name:</b> " + mapBoolToYesNo(this.productName) + "<br>";
	rcptObjHTML += "</div>";
	rcptObjHTML += "<div class='ui-block-c'>";
	rcptObjHTML += "<b>Signal Word:</b> " + mapBoolToYesNo(this.signalWord) + "<br>";
	rcptObjHTML += "</div>";
	rcptObjHTML += "</div>";
	
	rcptObjHTML += "<div class='ui-grid-b'>";
	rcptObjHTML += "<div class='ui-block-a'>";
	rcptObjHTML += "</div>";
	rcptObjHTML += "<div class='ui-block-b'>";
	rcptObjHTML += "<b>Pictogram:</b> " + mapBoolToYesNo(this.pictogram) + "<br>";
	rcptObjHTML += "</div>";
	rcptObjHTML += "<div class='ui-block-c'>";
	rcptObjHTML += "<b>Hazard Statement:</b> " + mapBoolToYesNo(this.hazardStatement) + "<br>";
	rcptObjHTML += "</div>";
	rcptObjHTML += "</div>";
	
	rcptObjHTML += "<div class='ui-grid-b'>";
	rcptObjHTML += "<div class='ui-block-a'>";
	rcptObjHTML += "</div>";
	rcptObjHTML += "<div class='ui-block-b'>";
	rcptObjHTML += "<b>Precautionary Statement:</b> " + mapBoolToYesNo(this.precautionaryStatement) + "<br>";
	rcptObjHTML += "</div>";
	rcptObjHTML += "<div class='ui-block-c'>";
	rcptObjHTML += "<b>Supplier Info:</b> " + mapBoolToYesNo(this.supplierInfo) + "<br>";
	rcptObjHTML += "</div>";
	rcptObjHTML += "</div>";
	
	rcptObjHTML += "<div><p><b>Notes:</b> " + ifDefined(this.receivingNotes) + "</p></div>";
	rcptObjHTML += "<div><p><b>Internal Notes:</b> " + ifDefined(this.internalReceivingNotes) + "</p></div>";
	rcptObjHTML += "</div>";
	rcptObjHTML += "<div class='ui-block-b' style='width:20%;'>";
	debug("currentItem = " + currentItem().components.length);
	debug("currentReceipt = " + currentItem().receiptList.currentReceipt);
	if (this.receivingStatus == "In Process" || this.receivingStatus == "Re-Image" || this.receivingStatus == "Reverted") {
		rcptObjHTML += "<button data-role='button' data-rcptIndex='" + currentItem().receiptList.currentReceipt + "' id='edit_" + this.receiptId + "'>Edit</button>";
	}
	if (_(currentItem().poLines).reduce(function(memo, poLine) {
		return memo + poLine.qtyOpen
	}, 0) > 0) {
		rcptObjHTML += "<button data-role='button' data-rcptIndex='" + currentItem().receiptList.currentReceipt + "' id='duplicate_" + this.receiptId + "' class='addSameLot'>+ same Lot</button>";
	}
	rcptObjHTML += "</div>";

	$("#receipt_" + this.receiptId).html(rcptObjHTML);
	$("#receipt_" + this.receiptId + " button").button();
	// $("#edit_" + this.receiptId).button();
	// $("#duplicate_" + this.receiptId).button();
	$("#receipt_" + this.receiptId).trigger("create");
	$("#edit_" + this.receiptId).on("click", function(e) {
		currentItem().receiptList.receipts[$(e.currentTarget).attr("data-rcptIndex")].fillRcptForm("edit");
	});
	$("#duplicate_" + this.receiptId).on("click", function(e) {
		currentItem().receiptList.receipts[$(e.currentTarget).attr("data-rcptIndex")].fillRcptForm("copy");
	});
}

// mode = edit(same lot, same receipt) - fill entire form from this object
// mode = copy(same lot, new receipt) - fill form except for receipt ID, qty,
receiptObj.prototype.fillRcptForm = function(mode) {
	var thisObj = this;
	debug("receiptObj.fillRcptForm");
	document.forms["rcptForm"].reset();
	$.when(buildReceiptForm(currentShipmentDetail())).then(function() {
		if (mode == "edit") {

			debug("fill receipt form - existing receipt");
			debug("fill receipt form - " + currentItem().poLines.length + " po lines")
			debug("receipt poLine = " + currentReceipt().poLine);
			populatePoLineList(currentItem().poLines, currentShipmentDetail().getTrxDetail().trxType);

			$("#receiptIdInput").val(thisObj.receiptId);
			$("#hiddenReceiptListIndex").val(receiptList.receipts.indexOfObject("receipt", thisObj.receiptId));

			for ( var x = 0; x < thisObj.components.length; x++) {
				$("#quantityInput" + x).val(thisObj.components[x].quantity);
				$("#mvQtyInput" + x).val(thisObj.components[x].mvQty);
				$("#mvSizeInput" + x).val(thisObj.components[x].mvSize);
			}
			// $("#receipt_" + thisObj.receiptId).remove();
		}
		else {
			$("#hiddenReceiptListIndex").val(-1);
		}
		for ( var x = 0; x < thisObj.components.length; x++) {
			$("#lotNumberInput" + x).val(thisObj.components[x].lot);
			$("#expDate" + x).val(thisObj.components[x].expireDate);
		}
		$("#binInput").val(thisObj.bin);
		debug("receiptObj Setting poLineInput=" + thisObj.poLine);

		/*
		 * SSS 8/7/13 If this receipt was loaded with receiptSearch.do it is possible that it's poLine was satified and not loaded this session. Search currentItem().poLines for this poLine if found populate the input and hidden value. If not found user
		 * must choose PO.
		 */
		var poLineIndex = currentItem().poLines.indexOfObject("lineItem", thisObj.poLine);
		if (poLineIndex != -1) {
			$("#poLineInput").val(thisObj.poLine);
			$("#hiddenPoLineIndex").val(poLineIndex);
		}

		$("#DOS").val(thisObj.dateOfShipment);
		$("#DOM").val(thisObj.dateOfManufacture);
		$("#ASSD").val(thisObj.vendorShipDate);
		if (ifDefined(thisObj.hazComLabelFlag) == "Y") {
			$("#chkBoxHazCom").attr("checked", "checked");
		}
		if (ifDefined(thisObj.unitLabelPrinted) == "Y") {
			$("#chkBox129P").attr("checked", "checked");
		}
		
		$("#hiddenGHSProductName").val(true);
		$("#hiddenGHSSignalWord").val(true);
		$("#hiddenGHSPictogram").val(true);
		$("#hiddenGHSHazardStatement").val(true);
		$("#hiddenGHSPrecautionaryStatement").val(true);
		$("#hiddenGHSSupplierIdentification").val(true);
		
		if (ifDefined(thisObj.GHSLabelFlag)) {
			$("#chkBoxGHS").attr("checked", "checked");		
		}
		else{
			if(ifDefined(!thisObj.productName)){
				$("#chkboxGHSProductName").attr("checked", "checked");
				$("#hiddenGHSProductName").val(false);
			}
			
			if(ifDefined(!thisObj.signalWord)){
				$("#chkboxGHSSignalWord").attr("checked", "checked");
				$("#hiddenGHSSignalWord").val(false);
			}
			
			if(ifDefined(!thisObj.pictogram)){
				$("#chkboxGHSPictogram").attr("checked", "checked");
				$("#hiddenGHSPictogram").val(false);
			}
			
			if(ifDefined(!thisObj.hazardStatement)){
				$("#chkboxGHSHazardStatement").attr("checked", "checked");
				$("#hiddenGHSHazardStatement").val(false);
			}
			
			if(ifDefined(!thisObj.precautionaryStatement)){
				$("#chkboxGHSPrecautionaryStatement").attr("checked", "checked");
				$("#hiddenGHSPrecautionaryStatement").val(false);
			}
			
			if(ifDefined(!thisObj.supplierInfo)){
				$("#chkboxGHSSupplierIdentification").attr("checked", "checked");
				$("#hiddenGHSSupplierIdentification").val(false);
			}	
		}
		/*
		 * if(ifDefined(thisObj.qaReadyFlag) == "Y"){ $("#chkBoxQAReady").attr("checked", "checked"); }
		 */
		$("#hiddenReceiptNotes").val(thisObj.receivingNotes);
		$("#hiddenInternalReceiptNotes").val(thisObj.internalReceivingNotes);
		$("#hiddenDefinedShelfLifeItem").val(thisObj.definedShelfLifeItem);
		$("#hiddenDefinedShelfLifeBasis").val(thisObj.definedShelfLifeBasis);
		
		receiptImageList.images = thisObj.imageList.images;
		if (thisObj.imageList.images.length > 0) {
			var newImageListItems = new String;
			for ( var x = 0; x < receiptImageList.images.length; x++) {
				newImageListItems += '<li data-imageIndex="' + x + '">';
				newImageListItems += receiptImageList.images[x].imageType;
				newImageListItems += '</li>';
			}
			$("#receiptImageListView").html(newImageListItems);
			$("#receiptImageListView li").on("click", function(e) {
				showReceiptImage(e);
			});
			try {
				$("#receiptImageListView").listview("refresh");
			}
			catch (err) {
			}
			receiptImageList.currentImage = 0;
			receiptImageList.images[0].display();
		}
		$.mobile.changePage("#receiptFormPage");
	});
}

Array.prototype.indexOfObject = function(key, value) {
	for ( var x = 0; x < this.length; x++) {
		if (this[x][key] == value) {
			return x;
		}
	}
	return -1;
}

receiptObj.prototype.saveToDb = function() {
	debug("receiptObj.saveToDb, trxType=" + this.trxType + ", hiddenCompanyId=" + $("#hiddenCompanyId").val());
	var thisDetail = inboundShipment.details[inboundShipment.currentDetail]
	var thisItem = thisDetail.itemList.items[thisDetail.itemList.currentItem];

	var callArgs = new Object();
	callArgs.inboundShipmentDetailId = this.inboundShipmentDetailId;
	callArgs.receiptId = this.receiptId;
	callArgs.receivingStatus = this.receivingStatus;
	callArgs.itemId = this.itemId;
	if (this.trxType == receivingMessages.tablet_transferrequest) {
		callArgs.originalReceiptId = this.poLine;
	}
	if (this.trxType == receivingMessages.label_rma) {
		callArgs.originalReceiptId = this.poLine;
	}
	else if (this.trxType == receivingMessages.tablet_customerownedinventory) {
		callArgs.poNumber = this.poLine;
		callArgs.ownerCompanyId = $("#hiddenCompanyId").val();
	}
	else {
		callArgs.poLine = this.poLine;
		callArgs.poNumber = ifDefined(this.poNumber);
		callArgs.radianPo = this.radianPo;
	}
	callArgs.hubId = this.hubId;
	callArgs.inventoryGroup = this.inventoryGroup;
	callArgs.definedShelfLifeItem = this.definedShelfLifeItem;
	callArgs.definedShelfLifeBasis = this.definedShelfLifeBasis;
	callArgs.dateOfReceipt = this.dateOfReceipt;
	callArgs.initialScanDate = sessionStorage.getItem("initialScanTime");
	callArgs.receiverId = 4023;
	callArgs.docType = this.docType;
	callArgs.dateOfShipment = this.dateOfShipment;
	callArgs.dateOfManufacture = this.dateOfManufacture;
	callArgs.vendorShipDate = this.vendorShipDate;
	callArgs.notes = this.receivingNotes;
	callArgs.internalReceiptNotes = this.internalReceivingNotes;
	callArgs.manageKitsAsSingleUnit = this.manageKitsAsSingleUnit;
	callArgs.newMsdsRevReceivedFlag = this.newMsdsRevReceivedFlag;
	callArgs.accountNumber = ifDefined(this.accountNumber);
	callArgs.accountNumber2 = ifDefined(this.accountNumber2);
	callArgs.accountNumber3 = ifDefined(this.accountNumber3);
	callArgs.accountNumber4 = ifDefined(this.accountNumber4);
	callArgs.customerReceiptId = ifDefined(this.customerReceiptId);

	if (this.unitLabelPrinted) {
		callArgs.unitLabelPrinted = "Y";
	}
	callArgs.hazComLabelFlag = this.hazComLabelFlag;
	callArgs.productName = this.productName;
	callArgs.signalWord = this.signalWord;
	callArgs.pictogram = this.pictogram;
	callArgs.hazardStatement = this.hazardStatement;
	callArgs.precautionaryStatement = this.precautionaryStatement;
	callArgs.supplierInfo = this.supplierInfo;

	var componentCount = thisDetail.itemList.items[thisDetail.itemList.currentItem].components.length;
	if (componentCount == 1) {
		callArgs.expireDate = this.components[0].expireDate;
		callArgs.lot = this.components[0].lot;
		callArgs.bin = this.bin;
		if (currentComponent().mvItem.trim() == "N") {
			callArgs.quantity = this.components[0].quantity;
		}
		else {
			callArgs.quantity = this.components[0].mvQty;
			callArgs.receivedPurchasingUnits = this.components[0].mvSize;
		}
		callArgs.itemId = this.itemId;
	}
	else {
		var componentStrArray = new Array(); // [";
		for ( var x = 0; x < componentCount; x++) {
			var componentStr = '{"partId" : ' + (x + 1) + ', ';
			componentStr += '"mfgLabelExpireDate" : "' + this.components[x].expireDate + '", ';
			componentStr += '"expireDate" : "' + this.components[x].expireDate + '", ';
			componentStr += '"mfgLot" : "' + this.components[x].lot + '", ';
			componentStr += '"bin" : "' + this.bin + '", ';
			if (currentComponent().mvItem.trim() == "N") {
				componentStr += '"quantity" : ' + this.components[x].quantity + ', ';
			}
			else {
				componentStr += '"quantity" : ' + this.components[x].mvQty + ', ';
				componentStr += '"receivedPurchasingUnits" : ' + this.components[x].mvSize + ', ';
			}
			componentStr += '"itemId" : ' + this.itemId + '}';
			componentStrArray.push(componentStr);
			debug("component String " + componentStr);
		}
	}

	var params = $.param(callArgs);
	if (componentCount > 1) {
		params += "&" + "components=[" + componentStrArray + "]";
	}

	var thisObj = this;
	return $.Deferred(function(def) {
		debug("begin deferred 5");
		debug("receiptUpdate.do" + params);
		$.mobile.loading('show', {
			text : "Saving Receipt ID " + thisObj.receiptId,
			textVisible : true
		});
		$.post("/tcmIS/haas/tabletReceiptUpdate.do", params, function(data) {
			debug("start receiptUpdate callback");
			receiptUpdateResult = $.parseJSON(data);
			debug("receiptUpdateResult = " + receiptUpdateResult.Status);
			if (receiptUpdateResult.Status != "OK") {
				alert("The receipt could not be saved.\n" + receiptUpdateResult.Message);
				def.resolve(false);
				debug("deferred 5 resolved");
				$.mobile.loading("hide");
				return; // back to the form to try again
			}
			thisObj.updateLocalPoLines();
			for ( var x = 0; x < thisObj.imageList.images.length; x++) {
				debug("saving image " + x);
				thisObj.imageList.images[x].saveImage(thisObj.receiptId);
			}
			def.resolve(true);
			debug("deferred 5 resolved");
			$.mobile.loading("hide");
		})
	});
}

receiptObj.prototype.updateLocalPoLines = function() {
	// subtract receipt qty from po line qty
	debug("updateLocalPoLines");
	if (currentItem().poLines.length == 1) {
		currentItem().poLines[0].qtyOpen -= this.components[0].quantity;
	}
	else {
		for ( var x = 0; x < currentItem().poLines.length; x++) {
			if (currentItem().poLines[x].lineItem == this.poLine) {
				currentItem().poLines[x].qtyOpen -= this.components[0].quantity;
			}
		}
	}
}

receiptObj.prototype.getImages = function() {
	var thisObj = this;
	return $.Deferred(function(def) {
		var callArgs = new Object();
		callArgs.receiptId = thisObj.receiptId;
		var params = $.param(callArgs);
		$.post("/tcmIS/haas/tabletReceiptImages.do", params, function(data) {
			var imageReturn = $.parseJSON(data);
			if (imageReturn.Status == "OK") {
				for ( var x = 0; x < imageReturn.ReceiptDocuments.length; x++) {
					var thisImage = new receiptImageObj();
					thisImage.imageType = imageReturn.ReceiptDocuments[x].documentType;
					thisImage.imageData = imageReturn.ReceiptDocuments[x].documentUrl;
					thisImage.imageUrl = imageReturn.ReceiptDocuments[x].documentUrl;
					thisObj.imageList.images.push(thisImage);
				}
			}
			else {
				alert("Failed to retrieve images for Receipt ID " + thisObj.receiptId);
			}
			def.resolve();
		});
	});
}
