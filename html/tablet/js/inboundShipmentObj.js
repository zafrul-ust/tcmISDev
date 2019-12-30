function inboundShipmentObj(inboundTrackingNumber) {
	debug("inboundShipmentObj " + inboundTrackingNumber);
	// from tcm_ops.inboundShipment
	this.inboundShipmentId;
	this.trackingNumber;
	this.carrierParentShortName;
	this.estimatedDeliveryDate;
	this.dateOfReceipt;
	this.countAndConditionNotes;
	this.receivingStatus;
	this.arrivalScanUser;
	this.arrivalScanDate;
	this.updateUser;
	this.dateUpdated;
	this.dateInserted;
	this.hub;
	this.carrier;
	this.details = new Array();
	this.documents = new Array();
	this.currentDetail = 0;
	this.arrivalScanTimestamp = sessionStorage.getItem("initialScanTime");
	if (typeof (inboundTrackingNumber) != "undefined") {
		this.trackingNumber = inboundTrackingNumber;
		this.carrier = this.getCarrier(this.trackingNumber);
		this.dateOfReceipt = sessionStorage.getItem("initialScanDate");
		$("#rcvDate").val(this.dateOfReceipt);
		this.receivingStatus = "New";
		if (typeof (this.carrier) != "undefined") {
			this.carrierParentShortName = this.carrier.carrierParentShortName;
			$("#carrierInput").val(this.carrier.carrierParentFullName);
			// $("#carrierInput").trigger("change");
			this.getExistingInboundShipment();
		}
		else {
			$("#carriersList").popup("open");
		}
	}
}

inboundShipmentObj.prototype.getExistingInboundShipment = function() {
	$.mobile.loading('show', {
		text : "Searching for existing shipment records.",
		textVisible : true
	});
	debug("inboundShipmentObj.getExistingInboundShipment");
	var thisObj = this;
	var callArgs = new Object();
	callArgs.trackingNumber = this.trackingNumber;
	callArgs.carrierParentShortName = this.carrierParentShortName;
	callArgs.hub = sessionStorage.getItem("currentHub");
	var params = $.param(callArgs);
	$.post('/tcmIS/haas/tabletInboundShipmentSearch.do', params, function(data) {
		var inboundShipmentSearchReturn;
		debug("getExistingInboundShipment callback start");
		inboundShipmentSearchReturn = $.parseJSON(data);
		if (inboundShipmentSearchReturn.Status == 'OK') {
			debug("inboundShipmentObj.getExistingInboundShipment returned OK");
			foundShipment = inboundShipmentSearchReturn.InboundShipment[0];
			if (typeof (foundShipment) != "undefined") {

				thisObj.inboundShipmentId = foundShipment.inboundShipmentId;
				thisObj.estimatedDeliveryDate = foundShipment.estimatedDeliveryDate;
				if (typeof (foundShipment.dateOfReceipt) != "undefined") {
					thisObj.dateOfReceipt = foundShipment.dateOfReceipt;
					$("#rcvDate").val(thisObj.dateOfReceipt);
				}
				thisObj.countAndConditionNotes = foundShipment.countAndConditionNotes;
				thisObj.receivingStatus = foundShipment.receivingStatus;
				thisObj.arrivalScanUser = foundShipment.arrivalScanUser;
				thisObj.arrivalScanDate = foundShipment.arrivalScanDate;
				thisObj.updateUser = foundShipment.updateUser;
				thisObj.dateUpdated = foundShipment.dateUpdated;
				thisObj.dateInserted = foundShipment.dateInserted;
				thisObj.hub = foundShipment.hub;
				thisObj.documents = foundShipment.documents;
				if (thisObj.documents.length > 0) {
					var newLiElements = new String();
					for ( var x = 0; x < thisObj.documents.length; x++) {
						newLiElements += "<li data-icon='none'>";
						newLiElements += thisObj.documents[x].inboundShipmentDocumentId + "</li>";
					}
					$("#documentList").append(newLiElements);
				}
				if (typeof (thisObj.inboundShipmentId) != "undefined") {
					/*
					 * $.when(thisObj.getExistingInboundShipmentDetails()) .then(function(){ thisObj.details[thisObj.currentDetail].getPoItemList(); });
					 */
					thisObj.getExistingInboundShipmentDetails();
				}
			}
		}
		else {
			alert("Inbound Shipment search failed.\n" + inboundShipmentSearchReturn.Status + inboundShipmentSearchReturn.Message);
		}
		$.mobile.loading("hide");
		debug("getExistingInboundShipment callback finished");
	});
	debug("leaving getExistingInboundShipment");
}

// use regex on tracking number to determine carrier
// return carrier object or noMatch
inboundShipmentObj.prototype.getCarrier = function(trackingNumber) {
	debug("inboundShipmentObj.getCarrier - " + trackingNumber);
	for ( var x = 0; x < vv_carriers.length; x++) {
		if (typeof (vv_carriers[x].carrierTrkNumRegexPattern) != "undefined" && vv_carriers[x].carrierTrkNumRegexPattern != "") {
			var pattern = new RegExp(vv_carriers[x].carrierTrkNumRegexPattern);
			if (pattern.test(trackingNumber)) {
				// alert("Match: " + vv_carriers.Results[x].carrierParentFullName);
				debug("found carrier after " + x + " tries");
				return {
					"carrierParentFullName" : vv_carriers[x].carrierParentFullName,
					"carrierParentShortName" : vv_carriers[x].carrierParentShortName
				};
			}
		}
	}
	debug("could not match a carrier");
	// return {"carrierParentFullName": "Unknown carrier", "carrierParentShortName": "Other"};
}

inboundShipmentObj.prototype.getExistingInboundShipmentDetails = function() {
	debug("inboundShipmentObj.getExistingImboundShipmentDetails");
	$.mobile.loading('show', {
		text : "Searching for existing shipment detail records.",
		textVisible : true
	});
	var thisObj = this;
	var callArgs = new Object();
	callArgs.inboundShipmentId = thisObj.inboundShipmentId;
	var params = $.param(callArgs);
	var deferred7 = $.Deferred();
	$.post('/tcmIS/haas/tabletInboundShipmentDetails.do', params, function(data) {
		debug("getExistingInboundShipmentDetail callback start");
		var inboundShipmentDetailsReturn = $.parseJSON(data);
		if (inboundShipmentDetailsReturn.Status == 'OK') {
			debug("inboundShipmentObj.getExistingImboundShipmentDetails returned OK");
			var inboundShipmentDetails = new Array();
			inboundShipmentDetails = inboundShipmentDetailsReturn.InboundShipmentDetail;
			// I think this should test length not type of
			if (inboundShipmentDetails.length > 0) {
				debug("inboundShipmentObj.getExistingImboundShipmentDetails returned " + inboundShipmentDetails.length + " records");
				var poListPopupItems = new String();
				for ( var x = 0; x < inboundShipmentDetails.length; x++) {
					var newDtlObj = new inboundShipmentDetailObj({
						"existingDtlObj" : inboundShipmentDetails[x]
					});
					thisObj.details.push(newDtlObj);
					thisObj.currentDetail = thisObj.details.length - 1;
					var trxDetails = new Object();
					trxDetails = thisObj.details[thisObj.currentDetail].getTrxDetail();
					poListPopupItems += "<li data-trxType='" + trxDetails.trxType + "' ";
					poListPopupItems += "data-inventoryGroup='" + inboundShipmentDetails[x].inventoryGroup + "' ";
					poListPopupItems += "data-index='" + x + "'>";
					poListPopupItems += trxDetails.trxId + "</li>"
				}
				$("#incomingShipmentPoListView").html(poListPopupItems);
				$("#incomingShipmentPoListView").listview("refresh");
				$("#incomingShipmentPoListView li").on("click", function(e) {
					setPoVal(e);
				});
				$("#incomingShipmentPoList").popup("open");
				$("#poNumListButton").button("enable");
				deferred7.resolve();
			}
			else {
				debug("inbound shipment details returned 0 records");
				$("#poNumListButton").button("disable");
				deferred7.reject();
			}
		}
		else {
			debug("inbound shipment detail retuned an error");
			deferred7.reject();
			alert("Error retreiving inbound shipment detail records");
		}
		debug("getExistingInboundShipmentDetails callback finished");
		$.mobile.loading("hide");
	});
	debug("leaving getExistingInboundShipmentDetails");
	return deferred7;
}

inboundShipmentObj.prototype.saveToDb = function() {
	debug("inboundShipmentObj.saveToDb");
	var thisObj = this;
	var callArgs = new Object();
	callArgs.trackingNumber = thisObj.trackingNumber;
	callArgs.carrierParentShortName = thisObj.carrierParentShortName;
	callArgs.dateOfReceipt = thisObj.dateOfReceipt;
	callArgs.receivingStatus = thisObj.receivingStatus;
	callArgs.arrivalScanTimestamp = this.arrivalScanTimestamp;
	callArgs.hub = sessionStorage.getItem("currentHub");
	if (ifDefined(thisObj.inboundShipmentId) != "") {
		callArgs.inboundShipmentId = thisObj.inboundShipmentId;
	}
	var params = $.param(callArgs);

	$.post('/tcmIS/haas/tabletInboundShipmentUpdate.do', params, function(data) {
		var UpdateReturn = $.parseJSON(data);
		if (UpdateReturn.Status == "OK") {
			if (ifDefined(thisObj.inboundShipmentId) == "") {
				thisObj.inboundShipmentId = UpdateReturn.inboundShipmentId;
				thisObj.addShipmentDetail(0);
				thisObj.details[0].saveToDb();
			}
		}
	});
	debug("leaving shipment save");
	return;
}

inboundShipmentObj.prototype.saveDocsToDb = function() {
	var thisObj = this;
	var callArgs = new Object();
	callArgs.inboundShipmentId = thisObj.inboundShipmentId;
	var params = $.param(callArgs);
	params += buildInboundShipmentDocList();
	$.post('/tcmIS/haas/tabletInboundShipmentUpdate.do', params, function(data) {
		var UpdateReturn = $.parseJSON(data);
		if (UpdateReturn.Status != "OK") {
			alert("Error associating Shipment Documents. " + UpdateReturn.message);
		}
		
	});
	debug("Done saving Inbound Shipment Docs");
	$.mobile.changePage("#itemViewPage");
}

inboundShipmentObj.prototype.addShipmentDetail = function(detailIndex) {
	debug("inboundShipmentObj.addShipmentDetailstart");
	if (this.inboundShipmentId != "undefined") {
		var newShipmentDetailObj = new inboundShipmentDetailObj({
			trxType : $("#incomingTrxType").val(),
			trxId : $("#poNum").val(),
			inboundShipmentId : this.inboundShipmentId
		});
		this.details.push(newShipmentDetailObj);
		this.currentDetail = this.details.length - 1;

		debug("add detail resolve");
	}
	else {
		alert("Cannot create new inbound shipment detail\ninbound shipment ID is missing.");
	}
	debug("leaving add detail");
}
