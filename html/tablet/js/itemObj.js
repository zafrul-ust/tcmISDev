function itemListObj() {
	this.currentItem = 0;
	this.items = new Array();
	this.poLines = new Array();
}
/*
 * itemListObj.prototype.display = function(index){ if $("#itemImage").attr("src", this.imageUrl); $("#itemImage").css("visibility", "visible"); var poItemText = "<p><b>Item ID: </b> " + this.itemId + "</p>"; poItemText += "<p><b>Item Description:</b> " +
 * this.itemDescription + "</p>"; poItemText += "<p><b>Package Description: </b>" + this.packaging + "</p>"; poItemText += "<p><b>Storage Temp: </b>" + this.storageTemp + "</p>"; if(this.numberOfKits > 1){ poItemText += "<p><b>Parts per kit:
 * </b>" + this.numberOfKits + "</p>"; poItemText += "<p><b>Kit component: </b>" + this.componentId + "</p>";} $("#itemText").html(poItemText); }
 */
itemListObj.prototype.sortItems = function() {
	// this will add another dimension to the array to handle kits
	// sort order will be itemId, componentId
	//     

	// first sort ascending by itemId, then component
	this.items.sort(function(a, b) {
		if (a.itemId - b.itemId != 0) {
			return a.itemId - b.itemId;
		}
		else {
			return a.component - b.component;
		}
	});
	var tempItemList = new Array();
	for ( var x = 0; x < this.items.length; x++) {
		var counter = 0;
		var currentItemId = this.items[x].itemId;
		// count number of records for each item Id
		while (this.items[x + counter].itemId == currentItemId) {
			counter++;
			if (x + counter >= this.items.length) {
				break;
			}
		}
		var components = new Array();
		for ( var y = 0; y < counter; y++) {
			buffer.push(this.items[x + y]);
		}
		tempItemList.push(buffer);
		x += (counter - 1);
	}
	this.items = tempItemList;
}

function itemObj(componentArray) {
	this.currentComponent = 0;
	this.components = componentArray;
	this.poLines = new Array();
	this.receiptList = new receiptListObj();
	this.newMSDSRevReceivedFlag = 'N';
};

itemObj.prototype.getComponentLots = function() {
	// currently turned off
	// return;
	var thisObj = this;
	var callArgs = new Object();
	callArgs.itemId = this.components[0].itemId;
	var params = $.param(callArgs);
	$.post("/tcmIS/haas/tabletListLotsForItemComponents.do", params, function(data) {
		var getComponentLotReturn = $.parseJSON(data);
		if (getComponentLotReturn.Status == "OK") {
			if (typeof (getComponentLotReturn.componentLots.length) > 0) {
				var componentLotList = getComponentLotReturn.componentLots;
				thisLot = new Object();
				// kits were causing beyond array bounds exceptions when managed as single
				// for(var x = 0; x < componentLotList.length; x++){
				for ( var x = 0; x < thisObj.components.length; x++) {
					thisLot = componentLotList[x];
					if (typeof (thisObj.components[thisLot.partId - 1]) != "undefined") {
						thisObj.components[thisLot.partId - 1].existingLots.push(thisLot.mfgLot);
					}
					else {
						alert("Error fetching lots - invalid component count.");
					}
				}
			}
		}
		else {
			alert("Error retreiving existing lots for item " + thisObj.itemId);
		}
	});
};

itemObj.prototype.getItemLots = function() {
	// currently turned off
	// return;
	var thisObj = this;
	var callArgs = new Object();
	callArgs.itemId = this.components[0].itemId;
	var params = $.param(callArgs);
	$.post("/tcmIS/haas/tabletListLotsForItem.do", params, function(data) {
		var getItemLotReturn = $.parseJSON(data);
		if (getItemLotReturn.Status == "OK") {
			if (getItemLotReturn.Lots.length > 0) {
				thisObj.components[0].existingLots = getItemLotReturn.Lots;
			}
		}
	});
};

function componentObj(poItem) {
	this.purchasingUnitsPerItem = poItem.purchasingUnitsPerItem;
	this.imageFileName = poItem.imageFileName;
	this.storageTemp = poItem.storageTemp;
	this.itemDescription = poItem.itemDescription;
	this.imageUrl = poItem.imageUrl;
	this.numberOfKits = poItem.numberOfKits;
	this.displayPkgStyle = poItem.displayPkgStyle;
	this.purchasingUnitOfMeasure = poItem.purchasingUnitOfMeasure;
	this.componentId = poItem.componentId;
	this.itemId = poItem.itemId;
	this.itemStatus = poItem.itemStatus;
	this.packaging = poItem.packaging;
	this.materialDesc = poItem.materialDesc;
	if (ifDefined(poItem.mvItem) != "") {
		this.mvItem = poItem.mvItem;
	}
	else {
		this.mvItem = "N";
	}
	this.existingLots = new Array();
	this.msdsVersions = poItem.msdsVersions;
	this.qualityControlItem = poItem.qualityControlItem;
}

itemObj.prototype.display = function() {
	debug("displaying item->" + this.components[0].itemId +", qci=" + this.components[0].qualityControlItem);
	$("#itemImage").attr("src", this.components[0].imageUrl);
	$("#itemImage").css("visibility", "visible");
	$("#itemImgPopup img").attr("src", this.components[0].imageUrl);

	var storageRange = storageType(this.components[0].storageTemp);
	switch (storageRange) {
		case "Controlled":
			$("#itemView").css("background-color", "#FFD700");
			break;
		case "Refrigerated":
			$("#itemView").css("background-color", "#00BFFF");
			break;
		case "Frozen":
			$("#itemView").css("background-color", "#0000CD");
			break;
		default:
			$("#itemView").css("background-color", "#FFFFFF");
	}
	var poItemText = "<div class='rcptSeparator'><b>Item ID:  " + this.components[0].itemId + "</b>";
	if (this.components.length > 1) {
		poItemText += " (" + this.components[0].numberOfKits + " part kit)";
	}
	poItemText += "</div>";
	poItemText += "<div class='poItemText'>";
	poItemText += "<div><b>Description:</b> " + this.components[0].itemDescription + "</div>";
	poItemText += "<div><b>Storage Temp:</b> " + this.components[0].storageTemp + "</div>";
	if (this.components.length > 1) {
		if (this.components[0].packaging == this.components[1].packaging) {
			poItemText += "<div><b>Packaging:</b> " + this.components[0].packaging + "</div>";
		}
	}
	else {
		poItemText += "<div><b>Packaging:</b> " + this.components[0].packaging + "</div>";
	}
	if (this.components.length > 1) {
		for ( var x = 0; x < this.components.length; x++) {
			poItemText += "<b>Component " + this.components[x].componentId + "</b>";
			poItemText += "<ul>";
			poItemText += "<li><b>Description:</b> " + this.components[x].materialDesc + "</li>";
			if (this.components[0].packaging == this.components[1].packaging) {
				poItemText += "<li><b>Package:</b> " + this.components[x].displayPkgStyle + "</li>";
			}
			else {
				poItemText += "<li><b>Packaging:</b> " + this.components[x].packaging + "</li>";
			}
			poItemText += "</ul>";
		}
	}
	poItemText += "</div>";
	poItemText += "<div class='ui-grid-a msdsHeader'>";
	poItemText += "<div class='ui-block-a'>";
	poItemText += "<b>Component Description</b>";
	poItemText += "</div>";
	poItemText += "<div class='ui-block-b'>";
	poItemText += "<b>MSDS Rev. Date</b>";
	poItemText += "</div>";
	poItemText += "</div>";
	for ( var x = 0; x < this.components[0].msdsVersions.length; x++) {
		poItemText += "<div class='ui-grid-a msdsGrid'>";
		poItemText += "<div class='ui-block-a'>";
		poItemText += this.components[0].msdsVersions[x].description;
		poItemText += "</div>";
		poItemText += "<div class='ui-block-b'>";
		poItemText += this.components[0].msdsVersions[x].revisionDate;
		poItemText += "</div>";
		poItemText += "</div>";
	}

	// poItemText += "<ul>";
	$("#itemText").html(poItemText);
	$("#itemDtlPopupContent").html(poItemText);
	if (this.components[0].qualityControlItem) {
		debug("fetching qci note");
		getQualityControlItemNote(this.components[0].itemId, sessionStorage.getItem("inventoryGroup"));
	}
	else {
		debug("NOT fetching qci note because " + this.components[0].qualityControlItem);
	}
};

// should be renamed getTrxLines
itemObj.prototype.getPoLines = function(shipmentDetailId) {
	var thisObj = this;
	debug("getPoLines");
	return $.Deferred(function(def) {
		debug("deferred6 start");
		if (typeof thisObj.poLines == "undefined" || thisObj.poLines.length < 1) {
			var callArgs = new Object();
			callArgs.inboundShipmentDetailId = shipmentDetailId;
			callArgs.itemId = thisObj.components[0].itemId;
			var params = $.param(callArgs);
			var forgetIt = $.post('/tcmIS/haas/tabletPoLineDetail.do', params, function(data) {
				var poLineDetailReturn = $.parseJSON(data);
				if (poLineDetailReturn.Status == "OK") {
					if (poLineDetailReturn.Lines.length > 0) {
						thisObj.poLines = poLineDetailReturn.Lines;
						var transType = currentShipmentDetail().getTrxDetail().trxType;
						populatePoLineList(thisObj.poLines, transType);
					}
					debug("deferred6 resolved");
					def.resolve();
				}
				else {
					alert("Error retrieving poLines");
					def.reject();
				}
			});
		}
		else {
			var transType = currentShipmentDetail().getTrxDetail().trxType;
			populatePoLineList(thisObj.poLines, transType);
			debug("deferred6 resolved");
			def.resolve();
		}
	});
};

itemObj.prototype.getTrxLines = function(shipmentDetailId) {
	var thisObj = this;
	var callArgs = new Object();
	callArgs.inboundShipmentDetailId = shipmentDetailId;
	callArgs.itemId = thisObj.components[0].itemId;
	$.post('/tcmIS/haas/tabletPoLineDetail.do', $.param(callArgs), function(data) {
		var poLineDetailReturn = $.parseJSON(data);
		if (poLineDetailReturn.Status == "OK" && poLineDetailReturn.Lines.length > 0) {
			thisObj.poLines = poLineDetailReturn.Lines;
		}
	});
};

itemObj.prototype.hasFlowDowns = function() {
	if (typeof this.poLines == "undefined" || this.poLines.length < 1) {
		return false;
	}
	else if (typeof this.poLines[0].flowdowns == "undefined" || this.poLines[0].flowdowns.length < 1) {
		return false;
	}
	return true;
};

itemObj.prototype.getExistingReceipts = function() {
	var thisObj = this;
	return $.Deferred(function(def) {
		var callArgs = new Object();
		callArgs.inboundShipmentId = inboundShipment.inboundShipmentId;
		callArgs.po = currentShipmentDetail().getTrxDetail().trxId;
		callArgs.itemId = thisObj.components[0].itemId;
		var params = $.param(callArgs);
		$.post("/tcmIS/haas/tabletReceiptSearch.do", params, function(data) {
			var existingReceipts = $.parseJSON(data);
			if (existingReceipts.Status == "OK") {
				for ( var x = 0; x < existingReceipts.Receipts.length; x++) {
					var newReceipt = new receiptObj();
					newReceipt.receiptImport(existingReceipts.Receipts[x]);
					thisObj.receiptList.receipts.push(newReceipt);
				}
				def.resolve();
			}
			else {
				alert("Existing receipt search failed");
				def.reject();
			}
		});
	});
};
