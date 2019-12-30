var vv_carriers;
var inboundShipment = new inboundShipmentObj();
var poItemList = new itemListObj();
var receiptList = new receiptListObj();
var receiptImageList = new receiptImageListObj();
var currentHub = new Object();
var defaultHub = new Object();
var receivingMessages = null;

/**
 * @alias findExistingInboundShipment
 * @author Stephen Skidmore
 * @classDescription calls inboundShipment.getExistingInboundShipment if the tracking number is not empty.
 */
function findExistingInboundShipment() {
	debug("findExistingInboundShipment");
	if (inboundShipment.trackingNumber != "") {
		inboundShipment.getExistingInboundShipment();
	}
}

/**
 * @alias resetApp
 * @author Stephen Skidmore
 * @classDescription empties all structures associated with the current shipment in prep for new shipment
 * @param {event}
 *            unnecessary?
 */
function resetApp(e) {
	debug("resetApp");
	if (typeof (inboundShipment.trackingNumber) != "undefined") {
		// if(confirm("Start a new shipment?")){
		document.forms["trkNumForm"].reset();
		$("#trkNumForm [type = 'hidden']").val("");
		document.forms["rcptForm"].reset();
		$("#rcptForm [type = 'hidden']").val("");
		inboundShipment = new inboundShipmentObj;
		receiptImageList = new receiptImageListObj();
		$("#receiptImageListView li").remove();
		$("#receiptImageTag").attr("src", "");
		$("#receiptImageTag").css("display", "none");
		$("#documentList li:gt(0)").remove();
		$("#receiptViewContent").empty();
		// $("#receiptView .ui-btn-left").has($("#receiptViewAddReceiptButton")).button("enable");
		$("#lineList").empty();
		$("#lotList").empty();
		$("#newMsdsChkBox").attr('checked', false).checkboxradio("refresh");
		$.mobile.changePage("#trkNumPage");
	}
}

/**
 * @alias cancelReceiptForm
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function cancelReceiptForm() {
	debug("cancelReceiptForm");
	receiptImageList = new receiptImageListObj();
	$("#receiptImageTag").attr("src", "");
	$("#receiptImageTag").css("display", "none");
	$("#receiptImageListView li").remove();
	$("#lineList").empty();
	$("#componentFormData").empty();
	document.forms["rcptForm"].reset();
	$("#rcptForm [type = 'hidden']").val("");

	if (currentItem().receiptList.receipts.length > 0) {
		$.mobile.changePage("#receiptView");
	}
	else {
		$.mobile.changePage("#itemViewPage");
	}
}

function getInitialScanTime(type) {
	$.ajax({
		url : '/tcmIS/haas/tabletTime.do',
		type : "GET",
		async : false,
		timeout : 1000,
		success : function(data) {
			var response = new responseObj(data);
			if (response.isOK()) {
				sessionStorage.setItem("indefinite", response.Indefinite);
				sessionStorage.setItem("initialScanTime", response.Time);
				debug("Time set - " + response.Time);
				sessionStorage.setItem("initialScanDate", response.Date);
				debug("Date set - " + response.Date);
			}
		}
	});
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function newInboundShipment(trackingNumber) {
	debug("newInboundShipment");
	getInitialScanTime();
	if (inboundShipment.trackingNumber != undefined) {
		if (trackingNumber == inboundShipment.trackingNumber) {
			debug("nevermind - same tracking number");
			return;
		}
		if (!confirm(receivingMessages.tablet_newtrackingnumber)) {
			return;
		}
	}
	$("#carrierInput").val("");
	$("#incomingTrxType").val(receivingMessages.label_purchaseorder);
	$('#leftPane [for="poNum"]').html(receivingMessages.label_purchaseorder);
	$("#poNum").val("");
	inboundShipment = new inboundShipmentObj(trackingNumber);
	if (typeof (inboundShipment.carrierParentShortName) == "undefined") {
		carrierListOpen();
	}
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function getCarrierList() {
	// skip this if vv_carriers is already populated
	if (typeof vv_carriers == 'undefined') {
		debug("getCarrierList");
		$.post('/tcmIS/haas/tabletListCarriers.do', function(data) {
			var response = $.parseJSON(data);
			if (response.Status == "OK") {
				if (response.Results != "undefined") {
					vv_carriers = response.Results;
				}
				else {
					alert("Carriers not loaded.");
				}
			}
			else if (response.Status == "SESSION EXPIRED") {
				refreshSession();
			}
			else {
				$("carrierInput").val("Carrier load error " + " " + response.Status);
			}
		});
	}
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function carriersListPopupPrep() {
	var abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var newHTML = new String;
	newHTML += "<h3 id='carriersListTitle'>Select the first letter of the Carrier's name.</h3>";
	for ( var x = 0; x < 26; x++) {
		if (x == 13) {
			newHTML += "<br>";
		}
		newHTML += "<a href='#' data-role='button' data-inline='true'>";
		newHTML += abc.charAt(x);
		newHTML += "</a>";
	}
	$("#alphaLinks").html(newHTML);
	$("#alphaLinks a").on("click", function(e) {
		carriersListAlphaClick(e);
	});
	$("#alphaLinks").trigger("create");

}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function carriersListAlphaClick(e) {
	// e.preventDefault();
	$("#carriersList").attr("data-firstLetter", $(e.currentTarget).text());
	$("#carriersList").on("popupafterclose", function() {
		populateCarriersListPopup();
	});
	$("#carriersList").popup("close");
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function populateCarriersListPopup() {
	$("#carrierLinks").empty();
	var firstLetter = $("#carriersList").attr("data-firstLetter");
	if (firstLetter == "undefined") {
		$("#carrierList").off("popupafterclose");
	}
	else {
		var regex = new RegExp("^" + firstLetter, "i");
		var carrierLinks = new String;
		carrierLinks += "<hr>";
		carrierLinks += "<h3>" + receivingMessages.label_selectacarrier + "</h3>";
		for ( var x = 0; x < vv_carriers.length; x++) {
			if (regex.test(vv_carriers[x].carrierParentFullName)) {
				carrierLinks += "<a href='#' data-role='button' data-inline='true' data-index='" + x + "'>";
				carrierLinks += vv_carriers[x].carrierParentFullName;
				carrierLinks += "</a>";
			}
		}
		carrierLinks += "<a href='#' data-role='button' data-inline='true'>";
		carrierLinks += "Cancel";
		carrierLinks += "</a>";
		$("#carrierLinks").html(carrierLinks);
		$("#carrierLinks").trigger("create");
		$("#carrierLinks a").on("click", function(e) {
			carrierClick(e);
		});
		$("#carriersList").off("popupafterclose");
		$("#carriersList").popup("open");
	}
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function itemImageAdvance() {
	debug("itemImageAdvance");
	var curDetail = inboundShipment.details[inboundShipment.currentDetail];
	curDetail.itemList.currentItem++;
	if (curDetail.itemList.currentItem >= curDetail.itemList.items.length) {
		curDetail.itemList.currentItem = 0;
	}
	curDetail.itemList.items[curDetail.itemList.currentItem].display();
	// updateItemView(poItemIndex);
	getSelectedItemReceiveInfo(curDetail.itemList.items[curDetail.itemList.currentItem].components[0].itemId);
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function itemImageReverse() {
	debug("itemImageReverse");
	var curDetail = inboundShipment.details[inboundShipment.currentDetail];
	curDetail.itemList.currentItem--;
	if (curDetail.itemList.currentItem < 0) {
		curDetail.itemList.currentItem = curDetail.itemList.items.length - 1;
	}
	curDetail.itemList.items[curDetail.itemList.currentItem].display();
	// updateItemView(poItemIndex);
	getSelectedItemReceiveInfo(curDetail.itemList.items[curDetail.itemList.currentItem].components[0].itemId);
}

function getSelectedItemReceiveInfo(itemId) {
	debug("getSelectedItemReceiveInfo - " + itemId);
	var sessionHubId = sessionStorage.getItem("currentHub");
	if (sessionHubId == '2101' || sessionHubId == '2102' || sessionHubId == '2103') {
		debug("fetching and displaying the receive info item note for hub = " + sessionHubId);
		getReceiveInfoItemNote(itemId, sessionHubId);
	}
	else {
		debug("NOT fetching the receive infor notes because of hub id - " + sessionHubId);
	}
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function getSelectedCarrier(rawSearchText) {
	debug("getSelectedCarrier");
	// trim the trailing space - not sure where its coming from but its gotta go
	searchText = rawSearchText.substr(0, rawSearchText.length - 1);
	for ( var x = 0; x < vv_carriers.length; x++) {
		if (vv_carriers[x].carrierParentShortName == searchText) {
			return vv_carriers[x];
		}
	}
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function carrierListOpen() {
	debug("carrierListOpen");
	if (inboundShipment.trackingNumber != undefined) {
		$('#carriersList').popup('open');
	}
	else {
		alert(receivingMessages.tablet_pleaseentertrackingnumber);
	}
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function poNumberEntry(entryType, trxType, label) {
	debug("poNumberEntry");
	if (inboundShipment.trackingNumber != undefined) {
		if (entryType == "scanner") {
			enableScanner('onPONumberScan(%json)', receivingMessages.tablet_scanponumber, 'trkNumPage');
		}
		else if (trxType == receivingMessages.tablet_customerownedinventory) {
			altAlphaInput("Enter " + label, "trkNumPage", "poNum");
		}
		else {
			altNumInput("Enter " + label, "N", "trkNumPage", "poNum");
		}
	}
	else {
		alert(receivingMessages.tablet_pleaseentertrackingnumber);
	}

}

function validateTrxNum(e) {
	var trxNum = $(e.currentTarget).val();
	if ($("#incomingShipmentPoListView li:contains('" + trxNum + "')").length == 1) {
		$("#incomingShipmentPoListView li:contains('" + trxNum + "')").trigger("click");
	}
	else {
		var callArgs = new Object();
		var trxType = $("#incomingTrxType").val();
		if (ifDefined(trxNum) != "") {
			switch (trxType) {
				case receivingMessages.label_purchaseorder:
					callArgs.po = trxNum;
					break;
				case receivingMessages.tablet_transferrequest:
					callArgs.transferRequest = trxNum;
					break;
				case receivingMessages.label_rma:
					callArgs.rma = trxNum;
					break;
				case receivingMessages.tablet_customerownedinventory:
					callArgs.citrPo = trxNum;
					break;
			}
			var params = $.param(callArgs);
			$.post('/tcmIS/haas/tabletVerifyExists.do', params, function(data) {
				var response = $.parseJSON(data);
				if (response.Status == "OK") {
					if (!response.Exists) {
						alert(trxNum + " " + receivingMessages.tablet_isnotavalid + " " + trxType);
						$(e.currentTarget).val("");
					}
					else {
						if (response.Results) {
							// May need to check for more than one result in future
							if (!response.Results[0].permissionToReceive) {
								var name = response.Results[0].inventoryGroupName ? response.Results[0].inventoryGroupName : response.Results[0].inventoryGroup;
								alert("You do not have permission to receive " + trxNum + " for " + name);
								$(e.currentTarget).val("");
							}
							else {
								$("#hiddenDocNum").val(response.Results[0].transactionId);
								sessionStorage.setItem("inventoryGroup", response.Results[0].inventoryGroup);
								onPoNumberChange();
							}
						}
					}
				}
				else if (response.Status == "SESSION EXPIRED") {
					refreshSession();
				}
				else {
					alert(trxType + " validation call failed: " + response.Message);
					$(e.currentTarget).val("");
				}
			});
		}
	}
}

function verifyReceiptIdIsNew(receiptId) {
	var callArgs = new Object();
	callArgs.receiptId = receiptId;
	var params = $.param(callArgs);
	$.post('/tcmIS/haas/tabletVerifyExists.do', params, function(data) {
		var response = $.parseJSON(data);
		if (response.Status == "OK") {
			if (response.Exists) {
				alert("ReceiptId " + receiptId + " has already been used.");
			}
		}
		else if (response.Status == "SESSION EXPIRED") {
			refreshSession();
		}
	});
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function carrierListClose() {
	debug("carrierListClose");
	$('#carriersList').popup('close');
	$("#carrierLinks").empty();
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function carrierClick(e) {
	// loop through vv_carrier to get full and short names or set teh currrent index
	// var selectedCarrier = getSelectedCarrier($(e.currentTarget).text());
	// inboundShipment.carrierParentShortName = selectedCarrier.carrierParentShortName;
	// changed to use val is index into vv_carrier
	debug("carrierClick");
	if ($(e.currentTarget).text() != "Cancel") {
		var selectedCarrier = vv_carriers[$(e.currentTarget).attr("data-index")];
		$("#carrierInput").val(selectedCarrier.carrierParentFullName);
		inboundShipment.carrier = selectedCarrier;
		inboundShipment.carrierParentShortName = selectedCarrier.carrierParentShortName;
		carrierListClose();
		findExistingInboundShipment();
	}
	else {
		carrierListClose();
	}
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function trxTypeListOpen() {
	debug("trxTypeListOpen");
	$('#trxTypeList').popup('open');
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function trxTypeListClose() {
	debug("trxTypeListClose");
	$('#trxTypeList').popup('close');
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function trxTypeListClick(e) {
	debug("trxTypeListClick");
	$("#incomingTrxType").val($(e.currentTarget).text());
	$('#trkNumPage [for="poNum"]').html($(e.currentTarget).attr("data-label"));
	trxTypeListClose();
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function onPoNumberChange() {
	/*
	 * $.mobile.loading('show',{ text : "Searching for existing records.", textVisible : true });
	 */
	debug("onPoNumberChange");
	debug("current Detail type = " + typeof (inboundShipment.details[inboundShipment.currentDetail]));
	if (typeof (inboundShipment.inboundShipmentId) == "undefined") {
		// this is a new inbound shipment so we need to save it to get an inbound shipment ID
		inboundShipment.dateOfReceipt = $("#rcvDate").val();
		inboundShipment.hub = sessionStorage.getItem("currentHub");
		inboundShipment.saveToDb();
		return;
	}
	else {
		// existing shipment adding po
		debug("poNum input = " + $("#poNum").val());
		// debug("current detail poNum = " + currentShipmentDetail().getTrxDetail().trxId);
		if (typeof (inboundShipment.details[inboundShipment.currentDetail]) != "undefined") {
			// we already have a shipment detail record, upsert only if po has changed or documents added.
			if (inboundShipment.details[inboundShipment.currentDetail].getTrxDetail().trxId == $("#poNum").val()) {
				debug($("#documentList li.newRDOC").length);
				if ($("#documentList li.newRDOC").length > 0) {
					inboundShipment.saveToDb();
				}
				else {
					debug("po number has not changed");
				}
				$.mobile.changePage("#itemViewPage");
				return;
			}
		}
		$.when(inboundShipment.addShipmentDetail(inboundShipment.details.length)).then(currentShipmentDetail().saveToDb());

	}
}

function addDocumentDone() {
	$.mobile.changePage("#itemViewPage");
	inboundShipment.saveDocsToDb();
	getSelectedItemReceiveInfo(inboundShipment.details[inboundShipment.currentDetail].itemList.items[0].components[0].itemId);
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function setupItemViewPage() {
	debug("setupItemViewPage");

	if (inboundShipment.details[inboundShipment.currentDetail].itemList.items.length > 1) {
		$("#prevButtonBlock").css("visibility", "visible");
		$("#nextButtonBlock").css("visibility", "visible");
		$("#itemImage").on("swipeleft", function() {
			itemImageAdvance();
		});
		$("#itemImage").on("swiperight", function() {
			itemImageReverse();
		});
	}
	else {
		$("#prevButtonBlock .ui-btn").css("visibility", "hidden");
		$("#nextButtonBlock .ui-btn").css("visibility", "hidden");
		$("#itemImage").off("swipeleft", "**");
		$("#itemImage").off("swiperight", "**");
	}
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function getPOItemList(inboundShipmentDtlObj) {
	debug("getPOItemList");
	var callArgs = new Object();
	callArgs.inboundShipmentDetailId = inboundShipmentDtlObj.inboundShipmentDetailId;
	var params = $.param(callArgs);
	$.mobile.loading('show', {
		text : receivingMessages.tablet_retrievingpoitemlist,
		textVisible : true
	});
	$.post('/tcmIS/haas/tabletPOItems.do', params, function(data) {
		var response = $.parseJSON(data);
		if (response.Status == "OK") {
			poItemList = new itemListObj();
			poItemList.items = response.Items;
			poItemList.currentPoItem = 0;
			updateItemView(0);
		}
		else if (response.Status == "SESSION EXPIRED") {
			refreshSession();
		}
		else {
			alert(receivingMessages.tablet_errorretrievingitems + "\n" + response.Message);
		}
	});
	$.mobile.loading("hide");
}

function showExistingReceipts() {
	$.mobile.loading('show');
	if (currentItem().receiptList && currentItem().receiptList.receipts && currentItem().receiptList.receipts.length > 0) {
		$.mobile.changePage("#receiptView");
	}
	else {
		$.when(currentItem().getPoLines(currentShipmentDetail().inboundShipmentDetailId)).then(currentItem().getExistingReceipts()).then(function() {
			if (currentItem().receiptList && currentItem().receiptList.receipts && currentItem().receiptList.receipts.length > 0) {
				for ( var x = 0; x < currentItem().receiptList.receipts.length; x++) {
					currentItem().receiptList.currentReceipt = x;
					currentItem().receiptList.receipts[x].displayDiv();
				}
				$.mobile.changePage("#receiptView");
			}
			else {
				showReceiptForm(inboundShipment.details[inboundShipment.currentDetail]);
			}
		});
	}
	$.mobile.loading("hide");
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function buildReceiptForm(shipmentDetailObj) {
	return $.Deferred(function(def) {
		debug("buildReceiptForm start");
		$.mobile.loading('show');

		var currentItemObj = shipmentDetailObj.itemList.items[shipmentDetailObj.itemList.currentItem];
		// $.when(currentItemObj.getPoLines(shipmentDetailObj.inboundShipmentDetailId)).then(function() {
		// debug("deferred componentsForm Begin");
		var componentsForm = "";
		for ( var x = 0; x < currentItemObj.components.length; x++) {
			if (currentItemObj.components.length > 1) {
				componentsForm += '<div class="componentSeparator">' + receivingMessages.tablet_component + ' ' + (x + 1) + ' - ';
				componentsForm += currentItemObj.components[x].materialDesc + '</div>';
			}
			componentsForm += '<div class="ui-grid-b">';
			componentsForm += '<div class="ui-block-a">';
			componentsForm += '<div class="ui-grid-a">';
			// Lot number input
			componentsForm += '<div class="ui-block-a scanInputDiv">';
			// var scannerCall = "enableScanner('onLotNumberScan(-1)','Scan Lot Number', $('#rcptFormPageScanPopup'))";
			componentsForm += '<label for="lotNumberInput" class="ui-input-text">' + receivingMessages.label_lotnumber + ':</label>';
			componentsForm += '<input type="text" data-type="lotNum" data-component=' + x + ' id="lotNumberInput' + x + '" name="lotNumberInput" class="lotNumberInput" readonly="readonly"/>';
			componentsForm += "</div>";
			componentsForm += '<div class="ui-block-b scanButtonDiv">';
			componentsForm += '<button data-role="button" data-component="' + x + '" class="lotNumberScanButton" type="button">' + receivingMessages.tablet_scancaps + '</button>';
			componentsForm += '</div>';
			componentsForm += '</div>';
			componentsForm += '</div>';
			componentsForm += '<div class="ui-block-b">';
			// Qty input
			if (x == 0) {
				componentsForm += '<div id="itemQtyDiv" class="ui-grid-a itemQtyDiv">';
				componentsForm += '<label for="quantityInput" id="quantityInputLabel" class="ui-input-text">';
				if (typeof (currentItemObj.components[x].purchasingUnitOfMeasure != "undefined")) {
					componentsForm += receivingMessages.tablet_qty + "(" + currentItemObj.components[x].purchasingUnitOfMeasure + ")</label>";
				}
				else {
					componentsForm += receivingMessages.tablet_qty + "</label>";
				}
				componentsForm += '<input type="text" id="quantityInput' + x + '" class="quantityInput" name="quantityInput" data-component="' + x + '" data-inline="true"></input>';
				componentsForm += '</div>';
				// MV Qty input
				componentsForm += '<div id="mvItemQtyDiv" name="mvItemQtyDiv" class="ui-grid-c mvItemQtyDiv">';
				componentsForm += '<div class="ui-block-a">';
				componentsForm += '<label for="mvQtyInput" class="ui-input-text">' + receivingMessages.tablet_pkgqty + '</label>';
				componentsForm += '<input type="text" id="mvQtyInput' + x + '" class="quantityInput" name="mvQtyInput" data-component="' + x + '" data-inline="true"></input>';
				componentsForm += '</div>';
				componentsForm += '<div class="ui-block-b">';
				componentsForm += 'x';
				componentsForm += '</div>';
				componentsForm += '<div class="ui-block-c">';
				componentsForm += '<label for="mvSizeInput" class="ui-input-text">' + receivingMessages.label_size + '</label>';
				componentsForm += '<input type="text" id="mvSizeInput' + x + '" name="mvSizeInput" data-component="' + x + '" data-inline="true"></input>';
				componentsForm += '</div>';
				componentsForm += '<div class="ui-block-d">';
				componentsForm += currentItemObj.components[x].purchasingUnitOfMeasure + " " + currentItemObj.components[x].displayPkgStyle;
				componentsForm += '</div>';
				componentsForm += '</div>';
			}
			componentsForm += '</div>';
			componentsForm += '<div class="ui-block-c">';
			componentsForm += '<div class="ui-grid-a">';
			componentsForm += '<div class="ui-block-a scanInputDiv">';
			// Exp date input
			if (currentItemObj.poLines[$("#hiddenPoLineIndex").val()].definedShelfLifeItem == "N") {
				componentsForm += '<label for="expDate" class="ui-input-text">' + receivingMessages.label_labelexpiredate + '</label>';
				// componentsForm += '<input type="date" id="expDate' + x + '" name="expDate" data-inline="true"></input>'
				componentsForm += '<input type="date" id="expDate' + x + '" name="expDate" data-role="datebox" ';
				componentsForm += "data-options='{";
				componentsForm += '"mode": "datebox", "useFocus":true, "afterToday":"true", "useClearButton": true, ';
				componentsForm += '"overrideTitleDateDialogLabel": "' + receivingMessages.label_labelexpiredate + '","transition":"none"';
				componentsForm += "}'></input>";
				componentsForm += '</div>';
				componentsForm += '<div class="ui-block-b scanButtonDiv">';
				componentsForm += '<button data-role="button" type="button" class="indefButton" ' + 'data-component="' + x + '">' + receivingMessages.tablet_indef + '</button>';
				componentsForm += '</div>';
			}
			componentsForm += '</div>';
			componentsForm += '</div>';
			componentsForm += '</div>';
			componentsForm += '</div>';
		}
		$("#componentFormData").html(componentsForm);
		$("#componentFormData").trigger("create");

		if (currentItemObj.poLines[$("#hiddenPoLineIndex").val()].definedShelfLifeItem == "N") {
			$("button.indefButton").on("click", function(e) {
				$("#expDate" + $(e.currentTarget).attr("data-component")).val(sessionStorage.getItem("indefinite"));
			});
		}

		$("button.lotNumberScanButton").on("click", function(e) {
			enableScanner('onLotNumberScan(%json, ' + $(e.currentTarget).attr("data-component") + ')', receivingMessages.tablet_scanlotnumber, 'receiptFormPage');
		});
		$("input.lotNumberInput").on("click", function(e) {
			altAlphaInput(receivingMessages.tablet_enterlotnumber, 'receiptFormPage', "lotNumberInput" + $(e.currentTarget).attr("data-component"));
		});

		$("input.lotNumberInput").on("change", function(e) {
			var lotUpperCase = $(e.currentTarget).val().toUpperCase();
			$(e.currentTarget).val(lotUpperCase);
		});
		if (currentItemObj.components[0].mvItem.trim() == "Y") {
			$("div.mvItemQtyDiv").removeClass("qtyDivHide").addClass("qtyDivShow");
			$("div.itemQtyDiv").removeClass("qtyDivShow").addClass("qtyDivHide");
		}
		else {
			$("div.itemQtyDiv").removeClass("qtyDivHide").addClass("qtyDivShow");
			$("div.mvItemQtyDiv").removeClass("qtyDivShow").addClass("qtyDivHide");
		}

		for ( var x = 0; x < currentItemObj.components.length; x++) {
			/*
			 * var altInputElementId = "lotNumberInput" + x; $("#" + altInputElementId).on("click", function(e){altAlphaInput('Enter the Lot Number' ,'receiptFormPage' , "lotNumberInput" + $(e.currentTarget).attr("data-component"));});
			 */
			var altInputElementId = "quantityInput" + x;
			var qtyPromptStr = receivingMessages.tablet_enterthequantity + " (" + currentItemObj.components[x].displayPkgStyle + ")";
			var decimalEntry = currentItemObj.poLines[0].nonIntegerReceiving;
			$("#" + altInputElementId).on("click", function(e) {
				altNumInput(qtyPromptStr, decimalEntry, "receiptFormPage", "quantityInput" + $(e.currentTarget).attr("data-component"), 0, currentItemObj.poLines[$("#hiddenPoLineIndex").val()].qtyOpen);
			});
			var altInputElementId = "mvQtyInput" + x;
			$("#" + altInputElementId).on("click", function(e) {
				altNumInput(qtyPromptStr, decimalEntry, "receiptFormPage", "mvQtyInput" + $(e.currentTarget).attr("data-component"));
			});
			var altInputElementId = "mvSizeInput" + x;
			var sizePromptStr = receivingMessages.tablet_enterthepkgsize + " (" + currentItemObj.components[x].purchasingUnitOfMeasure + ")";
			$("#" + altInputElementId).on("click", function(e) {
				altNumInput(sizePromptStr, decimalEntry, "receiptFormPage", "mvSizeInput" + $(e.currentTarget).attr("data-component"));
			});
		}

		for ( var x = 0; x < currentItemObj.components.length; x++) {
			if (currentItemObj.components[x].existingLots.length > 0) {
				if (currentItemObj.components.length == 1) {
					$("#lotNumberInput").attr("readonly", "readonly");
					// $("#lotNumberInput").click(function(e){showLotNumberChooser(e);});
				}
				else {
					$("#lotNumberInput").eq(x).attr("readonly", "readonly");
					// $("#lotNumberInput").eq(x).click(function(e){showLotNumberChooser(e);});
				}
			}
		}

		$("#hiddenTransactionType").val(currentShipmentDetail().getTrxDetail().trxType);

		// if only 1 po line populate qty fields
		if (currentItemObj.poLines.length == 1) {
			$("#poLineInput").off("click");
			$("#poLineInput").attr("readonly", "readonly");
			$("#quantityInputLabel").html("Qty (" + currentItem().poLines[0].qtyOpen + " " + ifDefined(currentItem().components[0].purchasingUnitOfMeasure) + ")");
			$("#hiddenPoLineIndex").val(0);
			$("#hiddenDefinedShelfLifeItem").val(currentItem().poLines[0].definedShelfLifeItem);
			$("#hiddenDefinedShelfLifeBasis").val(currentItem().poLines[0].definedShelfLifeBasis);
		}

		if (currentItemObj.poLines[0].polchemIg == "Y") {
			$("#chkBox129PDiv").css("display", "block");
		}
		else {
			$("#chkBox129PDiv").css("display", "none");
		}
		$("#hiddenReceiptListIndex").val(-1);
		// $.mobile.changePage("#receiptFormPage");
		// enableScanner('onReceiptIdScan(%json)','Scan Receipt ID', 'receiptFormPage');
		clearGHSCheckboxes();
		debug("buildReceiptForm resolved");
		def.resolve();
		$.mobile.loading('hide');
		// });
	});
}

function buildFlowdownTable(flowdowns) {
	debug("build flowdown table");
	var tableHTML = new String;
	tableHTML = "<table id='flowdownsTable'>";
	tableHTML += "<thead>";
	tableHTML += "<tr>";
	tableHTML += "<th>" + receivingMessages.label_flowdown + "</th>";
	tableHTML += "<th>" + receivingMessages.label_revisiondate + "</th>";
	tableHTML += "<th>" + receivingMessages.label_description + "</th>";
	tableHTML += "<th>" + receivingMessages.label_catalog + "</th>";
	tableHTML += "<th>" + receivingMessages.label_company + "</th>";
	tableHTML += "<th>" + receivingMessages.label_type + "</th>";
	tableHTML += "</tr>";
	tableHTML += "</thead>";

	for ( var i = 0; i < flowdowns.length; i++) {
		tableHTML += "<tr>";
		tableHTML += "<td>" + flowdowns[i].flowDown + "</td>";
		tableHTML += "<td>" + flowdowns[i].revisionDate + "</td>";
		tableHTML += "<td>" + flowdowns[i].flowDownDesc + "</td>";
		tableHTML += "<td>" + flowdowns[i].catalogId + "</td>";
		tableHTML += "<td>" + flowdowns[i].companyId + "</td>";
		tableHTML += "<td>" + flowdowns[i].flowDownType + "</td>";
		tableHTML += "</tr>"
	}

	tableHTML += "</table>";
	$("#flowdowns").html(tableHTML);
}

function showReceiptForm(shipmentDetailObj) {
	debug("build receipt form");
	$.when(buildReceiptForm(shipmentDetailObj)).then(function() {
		if (currentItem().hasFlowDowns()) {
			buildFlowdownTable(currentItem().poLines[0].flowdowns);
			$.mobile.changePage("#flowdownPage");
		}
		else {
			$.mobile.changePage("#receiptFormPage");
		}
		// SSS 9/20/13 added to prevent receipt form multiple submission
		$("#receiptFormDoneBtn").data("clicked", false);
		enableScanner('onReceiptIdScan(%json)', receivingMessages.tablet_scanreceiptid, 'receiptFormPage');
	});
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription - prepares the receipt form for the next receipt
 * @param {boolean}
 *            clearImagesFlag signals to clear or reuse the images collected withthe last receipt.
 * @return {}
 */
function reUseReceiptForm(clearImagesFlag) {
	debug("reUseReceiptForm");
	// check to see if the form has been built yet
	if ($("#componentFormData input").length == 0) {
		showReceiptForm(currentShipmentDetail());
	}
	else {
		document.forms["rcptForm"].reset();
		$("#rcptForm [type = 'hidden']").val("");
		$("#hiddenReceiptListIndex").val(-1);
		// $("#receiptView .ui-btn-left").has($("#receiptViewAddReceiptButton")).button("enable");
		if (clearImagesFlag) {
			debug("new receiptImageListObj");
			receiptImageList = new receiptImageListObj();
			$("#receiptImageTag").attr("src", "");
			$("#receiptImageTag").css("display", "none");
			$("#receiptImageListView li").remove();
		}
		populatePoLineList(currentItem().poLines, currentShipmentDetail().getTrxDetail().trxType);
		$.mobile.changePage("#receiptFormPage");
	}
}

function getOriginalReceiptData(origId) {
	var params = new Object();
	var callArgs = new Object();
	callArgs.receiptId = origId;
	params = $.param(callArgs);
	$.post("/tcmIS/haas/tabletReceiptIdSearch.do", params, function(data) {
		var response = $.parseJSON(data);
		if (response.Status == "OK") {
			if (response.Receipts.length > 0) {
				$("#DOM").val(ifDefined(response.Receipts[0].dateOfManufacture));
				$("#ASSD").val(ifDefined(response.Receipts[0].vendorShipDate));
				$("#DOS").val(ifDefined(response.Receipts[0].dateOfShipment));
				$("#chkBoxHazCom").val(ifDefined(response.Receipts[0].hazComLabelFlag));
				$("#lotNumberInput0").val(ifDefined(response.Receipts[0].mfgLot));
				$("#expDate0").val(ifDefined(response.Receipts[0].expireDate));
				debug("getOriginalReceiptData() Setting poLineInput=" + ifDefined(response.Receipts[0].receiptId));
				$("#poLineInput").val(ifDefined(response.Receipts[0].receiptId));
				$("#transferReceiptId").val(ifDefined(response.Receipts[0].receiptId));

				$("#chkboxGHSProductName").val(ifDefined(response.Receipts[0].ghsLabelReqs.productName));
				$("#chkboxGHSSignalWord").val(ifDefined(response.Receipts[0].ghsLabelReqs.signalWord));
				$("#chkboxGHSPictogram").val(ifDefined(response.Receipts[0].ghsLabelReqs.pictogram));
				$("#chkboxGHSHazardStatement").val(ifDefined(response.Receipts[0].ghsLabelReqs.hazardStatement));
				$("#chkboxGHSPrecautionaryStatement").val(ifDefined(response.Receipts[0].ghsLabelReqs.precautionaryStatement));
				$("#chkboxGHSSupplierIdentification").val(ifDefined(response.Receipts[0].ghsLabelReqs.supplierInfo));

				var GHSFlag = true;
				/*
				 * if($("#chkboxGHSProductName").val() == 'N' || $("#chkboxGHSSignalWord").val() == 'N' || $("#chkboxGHSSignalWord").val() == 'N' || $("#chkboxGHSHazardStatement").val() == 'N' || $("#chkboxGHSPrecautionaryStatement").val() == 'N' ||
				 * $("#chkboxGHSSupplierIdentification").val() == 'N') GHSFlag = 'N';
				 */
				if (!$("#chkboxGHSProductName").val() || !$("#chkboxGHSSignalWord").val() || !$("#chkboxGHSSignalWord").val() || !$("#chkboxGHSHazardStatement").val() || !$("#chkboxGHSPrecautionaryStatement").val()
						|| !$("#chkboxGHSSupplierIdentification").val())
					GHSFlag = false;
				$("#chkBoxGHS").val(GHSFlag);
			}
			else if (response.Status == "SESSION EXPIRED") {
				refreshSession();
			}
			else {
				alert(receivingMessages.tablet_originalrcptidnotfound);
			}
		}
		else {
			alert(receivingMessages.tablet_errorfetchingorigrcptid + "\n" + ifDefined(response.Message));
		}
	});
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function populatePoLineList(poLines, transType) {
	if (poLines.length > 0) {
		$("#hiddenTransactionType").val(transType);
		if (transType == receivingMessages.label_purchaseorder) {
			fillPoLinesList(receivingMessages.tablet_selectpoline + ":", receivingMessages.tablet_selectpoline, poLines, "Line: ", "lineItem");
		}
		else if (transType == receivingMessages.tablet_transferrequest) {
			$("#originalRIDScanButton").hide();
			fillPoLinesList(receivingMessages.tablet_selecttransferreceiptid + ":", receivingMessages.tablet_selecttransferreceiptid, poLines, "Transfer Receipt ID: ", "transferReceiptId");
		}
		else if (transType == 'RMA') {
			fillPoLinesList(receivingMessages.tablet_selectoriginalreceiptid + ":", receivingMessages.tablet_selectoriginalreceiptid, poLines, "Original Receipt ID: ", "transferReceiptId");
		}
		else if (transType == receivingMessages.tablet_customerownedinventory) {
			fillPoLinesList(receivingMessages.tablet_selectcustomerpo + ":", receivingMessages.tablet_selectcustomerpo, poLines, "PO: ", "poNumber");
		}
	}
	else {
		alert(receivingMessages.tablet_errornopolines);
	}
}

function receiptFormBinValidation(hubId, binId) {
	var callArgs = new Object();
	callArgs.hubId = hubId;
	callArgs.bin = binId;
	var params = $.param(callArgs);
	var result;
	$.post("/tcmIS/haas/tabletValidateBin.do", params, function(data) {
		var response = $.parseJSON(data);
		if (response.Status == "OK") {
			if (response.Results == "Active") {
				result = true;
			}
			else if (response.Results == "Inactive") {
				alert(receivingMessages.label_bin + " " + binId + " " + receivingMessages.tablet_inactivecannotuse);
				result = false;
				$("#binInput").val("");
				$("#binInput").focus();
			}
			else {
				alert(receivingMessages.label_bin + " " + binId + " " + receivingMessages.tablet_notvalid);
				result = false;
				$("#binInput").val("");
				$("#binInput").focus();
			}
		}
		else if (response.Status == "SESSION EXPIRED") {
			refreshSession();
		}
		else {
			alert(receivingMessages.tablet_binvalidationfailed + " = " + response.Status);
			result = false;
			$("#binInput").val("");
			$("#binInput").focus();
		}
	});
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */

function receiptFormQtyValidate(e) {

	var qty = $(e.currentTarget).val();

	// Check for non-integer receiving
	if (currentItem().poLines[rcptForm.hiddenPoLineIndex.val()].nonIntegerReceiving == "N" && qty % 1 != 0) {
		alert(receivingMessages.tablet_doesnotallownoninteger);
		return false;
	}

	// Qty rcvd <= qty open
	var maxQty = currentItem().poLines[rcptForm.hiddenPoLineIndex.val()].qtyOpen;
	if (qty > maxQty) {
		var errText = receivingMessages.tablet_component + " " + (x + 1) + " " + receivingMessages.tablet_qtynotgreaterthanpolineqty;
		errText += "(";
		errText += currentItem().poLines[rcptForm.hiddenPoLineIndex.val()].qtyOpen + ")";
		alert(errText);
		return false;
	}
	return true;
}

function receiptFormASSDValidate() {
	// Actual supplier ship date must be > DOM and < DOR if supplied
	if (rcptForm.ASSD != "") {
		var errText;
		var ASSDdate = convertDateToTimeStamp($("#ASSD").val());
		var DORdate = convertDateToTimeStamp(inboundShipment.dateOfReceipt);

		if (ASSDdate > DORdate) {
			if (errText.length > 0) {
				errText += "\n";
			}
			errText += receivingMessages.tablet_incorrectsuppliershipmentdate;
		}
		if (rcptForm.DOM.val() != "") {
			var DOMdate = convertDateToTimeStamp($("#DOM").val());
			if (ASSDdate < DOMdate) {
				if (errText.length > 0) {
					errText += "\n";
				}
				errText += receivingMessages.tablet_incorrectsuppliershipdate;
			}
		}
	}
}

function receiptFormDOSValidate() {
	// DOM < DOS
	// if (rcptForm.DOS.val() != "" && rcptForm.DOM.val() != "") {
	if (ifDefined($("#DOS").val()) != "" && ifDefined($("#DOM").val()) != "") {
		var DOMdate = convertDateToTimeStamp($("#DOM").val());
		var DOSdate = convertDateToTimeStamp($("#DOS").val());
		if (DOMdate > DOSdate) {
			altPopup(receivingMessages.tablet_incorectdateofmanu, "receiptFormPage", "function(){altPopUpHide('receiptFormPage');}", "OK");
			return false;
		}
	}
	return true;
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function displayFormError(errText) {

}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function addReceiptData() {
	if (!validateReceiptForm()) {
		// SSS 9/20/13 added to prevent receipt form multiple submission
		$("#receiptFormDoneBtn").data("clicked", false);
		return;
	}

	debug("addReceiptData");
	var currentItemListObj = currentShipmentDetail().itemList;

	// hazCom labeling checkbox has not been selected so confirm with popup
	if (!$("#chkBoxHazCom").is(":checked")) {
		if ($("#chkBoxHazCom").attr("data-confirmed") == "false") {
			$("#hazComLabelWarning").popup("open");
			// SSS 9/20/13 added to prevent receipt form multiple submission
			$("#receiptFormDoneBtn").data("clicked", false);
			return;
		}
	}

	// GHS labeling checkbox has not been selected so confirm with popup
		if ($("#chkBoxGHS").attr("data-confirmed") == "false") {
			$("#GHSLabelWarning").popup("open");
			// SSS 9/20/13 added to prevent receipt form multiple submission
			$("#receiptFormDoneBtn").data("clicked", false);
			return;
		}

	// moved reset flags to here so that the above checks don't loop
	$("#chkBoxHazCom").attr("data-confirmed", "false");
	$("#chkBoxGHS").attr("data-confirmed", "false");

	if ($("#receiptIdInput").val().lengh < 1) {

	}
	else if ($("#binInput").val().length < 1) {

	}

	var rcptFormData = new receiptObj(document.forms["rcptForm"]);

	// receipts that are being edited have listIndex value > -1, new receipts are -1
	if ($("#hiddenReceiptListIndex").val() >= 0) {
		currentItem().receiptList.receipts[$("#hiddenReceiptListIndex").val()] = rcptFormData;
	}
	else {
		currentItem().receiptList.receipts.push(rcptFormData);
		currentItem().receiptList.currentReceipt = currentItem().receiptList.receipts.length - 1;

		var totalOpenQty = _(currentItem().poLines).reduce(function(memo, poLine) {
			return memo + poLine.qtyOpen
		}, 0);

		var totalOpenQty;
		if (currentShipmentDetail().trxType == receivingMessages.tablet_transferrequest) {
			totalOpenQty = currentItem().poLines[0].qtyOpen;
		}
		else {
			totalOpenQty = _(currentItem().poLines).reduce(function(memo, poLine) {
				return memo + poLine.qtyOpen
			}, 0);
		}
		/*
		 * if(rcptFormData.components[0].quantity == totalOpenQty.toString()){ if ($("#receiptViewAddReceiptButton")) { try { $("#receiptViewAddReceiptButton").button("disable"); $("#receiptView.addSameLot").button("disable"); } catch (e) { } } }
		 */
	}
	// trying when instead of deferred
	// var deferred = receiptList.receipts[receiptList.currentReceipt].saveToDb();
	// deferred.then(function(receiptSaved){
	$.when(currentReceipt().saveToDb()).then(function(receiptSaved) {
		if (receiptSaved) {
			debug("begin post receipt save");

			currentReceipt().displayDiv();
			document.forms["rcptForm"].reset();
			$("#rcptForm [type = 'hidden']").val("");
			receiptImageList = new receiptImageListObj();
			$("#receiptImageListView li").remove();
			$("#receiptImageTag").attr("src", "");
			$("#receiptImageTag").css("display", "none");
			$.mobile.changePage("#receiptView");
			if (rcptFormData.components[0].quantity == totalOpenQty.toString()) {
				if ($("#receiptViewAddReceiptButton")) {
					try {
						// $("#receiptViewAddReceiptButton").button("disable");
						// $("#receiptView.addSameLot").button("disable");
					}
					catch (e) {
					}
				}
				else {
					try {
						// $("#receiptViewAddReceiptButton").button("enable");
						// $("#receiptView.addSameLot").button("enable");
					}
					catch (e) {
					}

				}
			}

		}
		// SSS 9/20/13 added to prevent receipt form multiple submission
		$("#receiptFormDoneBtn").data("clicked", false);
	});
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function showReceiptIdScanPopup() {
	debug("showReceiptIdScanPopup");
	if (!testReceiptId($("#receiptIdInput").val())) {
		$("#scanReceiptIdPopup").popup("open");
	}
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function hideReceiptIdScanPopup() {
	debug("hideReceiptIdScanPopup");
	disableScanner();
	$("#scanReceiptIdPopup").popup("close");
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function cancelReceiptIdScanPopup() {
	debug("cancelReceiptIdScanPopup");
	disableScanner();
	$("#scanReceiptIdPopup").popup("close");
	$("receiptIdInput").focus();
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function hideTrackingNumScanPopup() {
	debug("hideTrackingNumScanPopup");
	disableScanner();
	$("#trackingNumScanPopup").popup("close");
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function hidePoNumScanPopup() {
	debug("hidePoNumScanPopup");
	disableScanner();
	$("#poNumScanPopup").popup("close");
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function injectReceiptIdScan() {
	debug("injectReceiptIdScan");
	if (testReceiptId($("#receiptIdScanInput").val())) {
		alert(receivingMessages.tablet_true);
	}
	else {
		alert(receivingMessages.tablet_false);
	}
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function testReceiptId(receiptId) {
	debug("testReceiptId");
	var receiptIdPattern = new RegExp("RCPT/d{7,8}");
	var testResult = receiptIdPattern.test(receiptId);
	return testResult;
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function getBrowserInfo() {
	debug("getBrowserInfo");
	txt = "<p>" + receivingMessages.tablet_browsercodename + ": " + navigator.appCodeName + "</p>";
	txt += "<p>" + receivingMessages.tablet_browsername + ": " + navigator.appName + "</p>";
	txt += "<p>" + receivingMessages.tablet_browserversion + ": " + navigator.appVersion + "</p>";
	txt += "<p>" + receivingMessages.tablet_cookiesenabled + ": " + navigator.cookieEnabled + "</p>";
	txt += "<p>" + receivingMessages.tablet_platform + ": " + navigator.platform + "</p>";
	txt += "<p>" + receivingMessages.tablet_useragentheader + ": " + navigator.userAgent + "</p>";
	txt += "<p>" + receivingMessages.tablet_useragentlanguage + ": " + navigator.systemLanguage + "</p>";
	alert(txt);
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function manualDocumentAdd() {
	debug("manualDocumentAdd");
	$("#manualDocumentPopup").popup("open");
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function insertManualDocument() {
	debug("insertManualDocument");
	var newDocElement = "<li data-icon='delete' class='newRDOC'>"
	newDocElement += "<a>";
	newDocElement += $("#manualDocumentInput").val();
	newDocElement += "</a>";
	newDocElement += "</li>";
	$("#documentList").append(newDocElement);
	$("#documentList").listview("refresh");
	$("#documentList li.newRDOC").on("click", function(e) {
		$(e.currentTarget).remove();
	});
	$("#manualDocumentInput").val("")
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function insertManualDocumentCnx() {
	debug("insertManualDocumentCnx");
	$("#manualDocumentInput").val("");
	$("#manualDocumentPopup").popup("close");
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function showPoLineChooser() {
	debug("showPoLineChooser");
	$("#poLineChooserPopup").popup("open");
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function showLotNumberChooser(e) {
	debug("showLotNumberChooser");
	var currentLotInput = e.currentTarget;
	var thisComponent = $(e.currentTarget).attr("data-component");
	$("#hiddenCurrentComponent").val(thisComponent);
	var thisDetail = inboundShipment.details[inboundShipment.currentDetail]
	var thisItem = thisDetail.itemList.items[thisDetail.itemList.currentItem];
	var lots = thisItem.components[thisComponent].existingLots;
	var lotListHTML = "";
	for ( var x = 0; x < lots.length; x++) {
		lotListHTML += "<li value='" + lots[x] + "'>" + lots[x] + "</li>";
	}
	$("#lotList").html(lotListHTML);
	$("#lotList").listview('refresh');
	$("#lotList li").on("click", function(e) {
		lotChooserClick(e, currentLotInput);
	});
	$("#lotNumberChooserPopupButton").on("click", function(e) {
		returnLotNumber(e, currentLotInput);
	});
	$("#lotNumberChooserPopup").popup("open");
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function poLineChooserClick(e) {
	debug("poLineChooserClick");
	var poLineIndex = $(e.currentTarget).attr("data-poLineIndex");
	var thisDetail = inboundShipment.details[inboundShipment.currentDetail];
	var thisItem = thisDetail.itemList.items[thisDetail.itemList.currentItem];
	var poLine = thisItem.poLines[poLineIndex];
	debug("poLineChooserClick() Setting poLineInput=" + poLine.lineItem);
	$("#poLineInput").val(poLine.lineItem);
	$("#quantityInputLabel").html("Qty (" + currentItem().poLines[poLineIndex].qtyOpen + " " + ifDefined(currentItem().components[0].purchasingUnitOfMeasure) + ")");
	$("#hiddenPoLineIndex").val(poLineIndex);
	$("#hiddenCompanyId").val(poLine.ownerCompanyId);

	if (typeof (poLine.transferReceiptId) != "undefined") {
		getOriginalReceiptData(poLine.transferReceiptId);
	}
	$("#poLineChooserPopup").popup("close");
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function lotChooserClick(e, currentLotInput) {
	debug("lotChooserClick");
	var lot = $(e.currentTarget).text();
	$(currentLotInput).val(lot);
	$("#lotNumberChooserPopup").popup("close");
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function stopSubmit(e) {
	// catch enter key and change to tab
	// instead of submitting the form move to next field
	/*
	 * if(e.keyCode == 13){ alert("enter pressed"); e.keyCode = 9; return false; }
	 */

	// return !(e.keyCode == 13);
	return (e.keyCode == 9);

}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function lotNumberChooserFilter(e) {
	debug("lotNumberChooserFilter");
	if (e.keyCode == 13) {
		returnLotNumber();
	}
	else {
		var filterRegex = new RegExp("^" + $("#lotNumberPopupInput").val(), "i");
		$("#lotList li").each(function(index, li) {
			if (filterRegex.test($(li).val())) {
				$(li).show();
			}
			else {
				$(li).hide();
			}
		});
		$("#lotNumberPopupInput").focus();
	}
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function returnLotNumber(e, currentLotInput) {
	debug("returnLotNumber");
	var lot = $("#lotNumberPopupInput").val();
	$(currentLotInput).val(lot);
	$("#lotNumberChooserPopup").popup("close");
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function storageType(storageTemp) {
	debug("storageType");
	if (typeof (storageTemp) == "undefined") {
		return "General";
	}
	else if (storageTemp == "undefined") {
		return "General";
	}

	var tempRange = new RegExp("\d*( - )\d*");
	var tempMax = new RegExp(".*max");
	var tempTolerance = new RegExp(".*/.*");
	var maxTemp;

	if (tempRange.test(storageTemp)) {
		debug(storageTemp + " = range");
		var strArray1 = storageTemp.split(" - ");
		var strArray2 = strArray1[1].split(" ");
		maxTemp = strArray2[0];
	}
	else if (tempMax.test(storageTemp)) {
		debug(storageTemp + " = MAX");
		var strArray1 = storageTemp.split(" ");
		maxTemp = strArray1[0];
	}
	else if (tempTolerance.test(storageTemp)) {
		debug(storageTemp + " = tolerance");
		var strArray1 = storageTemp.split(" ");
		var baseTemp = new Number(strArray1[0]);
		var tolerance = new Number(strArray1[2]);
		var maxTemp = baseTemp + tolerance;
	}
	debug(maxTemp);

	if (maxTemp <= 32) {
		debug("Frozen");
		return "Frozen";
	}
	else if (maxTemp <= 45) {
		debug("Refrigerated");
		return "Refrigerated";
	}
	else if (maxTemp <= 85) {
		debug("Controlled");
		return "Controlled";
	}
	else {
		debug("General");
		return "General";
	}
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function buildInboundShipmentDocList() {
	debug("buildInboundShipmentDocList");
	var docListParams = "";
	$("#documentList li.newRDOC").each(function(index, li) {
		docListParams += "&docId[";
		docListParams += index;
		docListParams += "].inboundShipmentDocumentId=";
		docListParams += $.trim($(li).text().replace("RDOC", ""));
	});
	return docListParams;
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function imageViewPrep() {
	debug("itemViewPrep");
	if ($("#imageTypeList").html() == "") {
		$.post("../tcmIS/haas/tabletListReceiptDocumentImageTypes.do", function(data) {
			var imageTypeListReturn = $.parseJSON(data);
			if (imageTypeListReturn.Status == "OK") {
				var imageTypeList = imageTypeListReturn.Results;
				var imageTypeListHTML = "";
				for ( var x = 0; x < imageTypeList.length; x++) {
					imageTypeListHTML += "<li data-docType='";
					imageTypeListHTML += imageTypeList[x].documentType + "'>";
					imageTypeListHTML += imageTypeList[x].documentTypeDesc;
					imageTypeListHTML += "</li>";
				}
				$("#imageTypeList").html(imageTypeListHTML);
				$("#imageTypeList.ui-listview").listview("refresh");
				$("#imageTypeList li").on("click", function(e) {
					imageTypeListClick(e);
				});
			}
			else {
				alert(receivingMessages.tablet_errorfetchingimagetypes);
				return;
			}
		});
	}
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function showImageTypePopup() {
	debug("showImageTypePopup");
	$("#imageTypePopup").popup("open");
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function imageTypeListClick(e) {
	debug("imageTypeListClick");
	var imageType = $(e.currentTarget).attr("data-docType");
	// alert(imageType);
	debug(imageType);
	$("#cameraPage H1").html(imageType);
	$("#imageTypePopup").popup("close");
	$.mobile.changePage("#cameraPage");
	cameraOn();
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function showReceiptImage(e) {
	debug("showReceiptImage");
	var imageIndex = $(e.currentTarget).attr("data-imageIndex");
	receiptImageList.currentImage = imageIndex;
	receiptImageList.images[receiptImageList.currentImage].display();

	$("#imageTag").css("display", "block");
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function currentShipmentDetail() {
	return inboundShipment.details[inboundShipment.currentDetail];
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function currentItem() {
	return currentShipmentDetail().itemList.items[currentShipmentDetail().itemList.currentItem];
}

/**
 * @alias
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 * @return {}
 */
function currentComponent() {
	return currentItem().components[currentItem().currentComponent];
}

function currentReceipt() {
	return currentItem().receiptList.receipts[currentItem().receiptList.currentReceipt];
}

/**
 * @alias ifDefined
 * @author Stephen Skidmore
 * @classDescription
 * @param {}
 *            any value
 * @return {} an empty string or the value passed if it is defined.
 */
function ifDefined(value) {
	if (typeof (value) != "undefined") {
		if (value != "undefined") {
			return value;
		}
	}
	return "";
}

function setPoVal(e) {
	var detailIndex = $(e.currentTarget).attr("data-index");
	$("#poNum").val($(e.currentTarget).text());
	$("#poInputDiv label").html($(e.currentTarget).attr("data-trxtype"));
	$("#trkNumForm [for=poNum]").html($("#trxTypeListView li:contains(" + $(e.currentTarget).attr("data-trxtype") + ")").attr("data-label"));
	$("#incomingTrxType").val($(e.currentTarget).attr("data-trxtype"));
	inboundShipment.currentDetail = detailIndex;
	currentShipmentDetail().getPoItemList();
	// updatePoHdr($(e.currentTarget).text());
	sessionStorage.setItem("inventoryGroup", $(e.currentTarget).attr("data-inventoryGroup"));
	$("#incomingShipmentPoList").popup("close");
}

function addCannedNotes(e) {
	debug("tablet notes length = " + $("#tabletNotes").text.length);
	debug($(e.currentTarget).attr("data-string"));
	var tabletNotes = ifDefined($("#tabletNotes").val());
	if (tabletNotes.length > 1) {
		tabletNotes += "\n";
	}
	tabletNotes += $(e.currentTarget).attr("data-string");
	$("#tabletNotes").val(tabletNotes);
}

function prepNotesPage(returnPage, returnElement, pageTitle) {
	$("#notesPageReturnTo").val(returnPage);
	$("#notesPageReturnNotesTo").val(returnElement);
	var currentValue = $(returnElement).val();
	$("#tabletNotes").val(ifDefined(currentValue));
	$("#notesPageTitle").html(pageTitle);
	$.mobile.changePage("#notesPage");
}

function updateHeader(e) {
	var thisPage = new String("#" + e.currentTarget.id);
	var hdrString = new String();
	debug(thisPage);
	if ($(e.currentTarget).attr("data-hdrType") == "trkNum") {
		hdrString = "<div class='ui-header-trx-desc'>" + $("#carrierInput").val() + " - " + $("#trkNum").val() + "</div>";
	}
	else if ($(e.currentTarget).attr("data-hdrType") == "poNum") {
		hdrString = "<div class='ui-header-trx-desc'>";
		hdrString += currentShipmentDetail().getTrxDetail().trxType;
		hdrString += " " + currentShipmentDetail().getTrxDetail().trxId;
		hdrString += "</div>"
	}
	else if ($(e.currentTarget).attr("data-hdrType") == "poLine") {
		hdrString = "<div class='ui-header-trx-desc'>";
		hdrString += currentShipmentDetail().getTrxDetail().trxType;
		hdrString += " " + currentShipmentDetail().getTrxDetail().trxId;
		hdrString += "-";
		hdrString += "</div>"
	}
	else {
		return;
	}

	if ($(thisPage + " .ui-header-trx-desc").length > 0) {
		$(thisPage + " .ui-header-trx-desc").html(hdrString);
	}
	else {
		$(thisPage + " [data-role = 'header']").append("<div class='ui-header-trx-desc'>" + hdrString + "</div>");
	}
}

function validateReceiptForm() {

	var defShelfLife = currentItem().poLines[0].definedShelfLifeItem;
	if (ifDefined($("#receiptIdInput").val()) == "") {
		altPopup(receivingMessages.tablet_pleasescanreceiptid, "receiptFormPage", "function(){altPopUpHide('receiptFormPage');}", "OK");
		return false;
	}
	if (ifDefined($("#binInput").val()) == "") {
		altPopup(receivingMessages.tablet_pleasescanbinid, "receiptFormPage", "function(){altPopUpHide('receiptFormPage');}", "OK");
		return false;
	}

	for (x = 0; x < currentItem().components.length; x++) {
		if (ifDefined($("#lotNumberInput" + x).val()) == "") {
			altPopup(receivingMessages.tablet_pleaseenterlotnumber + (x + 1), "receiptFormPage", "function(){altPopUpHide('receiptFormPage');}", "OK");
			return false;
		}
		if ($("#lotNumberInput" + x).val().length > 36) {
			altPopup(receivingMessages.tablet_componentnum + (x + 1) + receivingMessages.tablet_incorrectlotnumber, "receiptFormPage", "function(){altPopUpHide('receiptFormPage');}", "OK");
			return false;
		}
		if (ifDefined($("#expDate" + x).val()) == "" && defShelfLife != "Y") {
			altPopup(receivingMessages.tablet_incorrectexpirationdate + (x + 1) + ". " + receivingMessages.tablet_nodateonlabel, "receiptFormPage", "function(){altPopUpHide('receiptFormPage');}", "OK");
			return false;
		}
		// no longer collecting qtys for all components just the first
		if (x == 0) {
			if (currentComponent().mvItem.trim() == "N") {
				if (ifDefined($("#quantityInput" + x).val()) == "") {
					altPopup(receivingMessages.tablet_pleaseenterquantity + (x + 1), "receiptFormPage", "function(){altPopUpHide('receiptFormPage');}", "OK");
					return false;
				}
			}
			else {
				if (ifDefined($("#mvQtyInput" + x).val()) == "") {
					altPopup(receivingMessages.tablet_pleaseenterquantity + (x + 1), "receiptFormPage", "function(){altPopUpHide('receiptFormPage');}", "OK");
					return false;
				}
				if (ifDefined($("#mvSizeInput" + x).val()) == "") {
					altPopup(receivingMessages.tablet_pleaseentersize + (x + 1), "receiptFormPage", "function(){altPopUpHide('receiptFormPage');}", "OK");
					return false;
				}
			}
		}
	}
	if (!receiptFormDefinedShelfLifeValidate()) {
		return false;
	}
	if (defShelfLife != "Y" && !receiptFormDOSValidate()) {
		return false;
	}
	return true;
}

function receiptFormDefinedShelfLifeValidate() {
	debug("receiptFormDefinedShelfLifeValidate");
	var definedShelfLifeItem = currentItem().poLines[0].definedShelfLifeItem;
	if (definedShelfLifeItem == "Y") {
		var definedShelfLifeBasis = currentItem().poLines[0].definedShelfLifeBasis;
		if (definedShelfLifeBasis == "M" && ifDefined($("#DOM").val()) == "") {
			altPopup(receivingMessages.tablet_pleaseenterdom, "receiptFormPage", "function(){altPopUpHide('receiptFormPage');}", "OK");
			return false;
		}
		if (definedShelfLifeBasis == "S" && ifDefined($("#DOS").val()) == "") {
			altPopup(receivingMessages.tablet_pleaseenterdos, "receiptFormPage", "function(){altPopUpHide('receiptFormPage');}", "OK");
			return false;
		}
		if (definedShelfLifeBasis == "R" && ifDefined($("#rcvDate").val()) == "") {
			altPopup(receivingMessages.tablet_pleaseenterasds, "receiptFormPage", "function(){altPopUpHide('receiptFormPage');}", "OK");
			return false;
		}
	}
	return true;
}

function tabletWait(action) {
	if (action == "wait") {
		$.mobile.activePage.append("<img id='waitGif' src='/images/gears.gif' class='waitGifCentered'/>");
	}
	else {
		$("#waitGif").remove();
	}
}

function trkNumPageValidate(e) {
	var errMsg = new String;
	if (ifDefined(inboundShipment.trackingNumber) == "") {
		altPopup(receivingMessages.tablet_pleaseentertrackingnumber, "trkNumPage", "function(){altPopUpHide('trkNumPage');}", "OK");
	}
	else if (inboundShipment.trackingNumber.length > 50) {
		altPopup(receivingMessages.tablet_incorrecttrackingnumber, "trkNumPage", "function(){altPopUpHide('trkNumPage');}", "OK");
	}
	else if (ifDefined(inboundShipment.carrier) == "") {
		altPopup(receivingMessages.label_selectacarrier, "trkNumPage", "function(){altPopUpHide('trkNumPage');}", "OK");
	}
	else if (ifDefined($("#rcvDate").val()) == "") {
		altPopup(receivingMessages.tablet_selectreceiveddate, "trkNumPage", "function(){altPopUpHide('trkNumPage');}", "OK");
	}
	else if (ifDefined($("#poNum").val()) == "") {
		altPopup(receivingMessages.tablet_pleaseentera + " " + $("#incomingTrxType").val() + " " + receivingMessages.tablet_number, "trkNumPage", "function(){altPopUpHide('trkNumPage');}", "OK");
	}
	else {
		$.mobile.changePage("#documentsPage");
	}
}

function mapBoolToYesNo(boolValue) {
	if (boolValue == true) {
		return 'Y';
	}
	else {
		return 'N';
	}
}

function fillPoLinesList(poLineInputLabel, chooserHeader, poLines, lineText, fieldName) {
	$("label[for=poLineInput]").text(poLineInputLabel);
	if (poLines.length == 1) {
		$("#poLineInput").val(poLines[0][fieldName]);
		if (poLines[0].ownerCompanyId) {
			$("#hiddenCompanyId").val(poLines[0].ownerCompanyId);
		}
		$("#poLineInput").off("click");
		$("#poLineInput").attr("readonly", "readonly");
		$("#hiddenPoLineIndex").val(0);
		$("#quantityInputLabel").html("Qty (" + poLines[0].qtyOpen + " " + ifDefined(currentItem().components[0].purchasingUnitOfMeasure) + ")");
		if (fieldName == 'transferReceiptId') {
			getOriginalReceiptData(poLines[0].transferReceiptId);
		}
		$("#hiddenDefinedShelfLifeItem").val(poLines[0].definedShelfLifeItem);
		$("#hiddenDefinedShelfLifeBasis").val(poLines[0].definedShelfLifeBasis);
	}
	else {
		$("#poLineInput").click(function(e) {
			showPoLineChooser();
		});
		$("#poLineChooserHeader").text(chooserHeader);
		var listText = new String();
		for ( var x = 0; x < poLines.length; x++) {
			listText += "<li data-poLineIndex='" + x + "'>";
			listText += lineText + poLines[x][fieldName];
			listText += " | ";
			listText += receivingMessages.tablet_expecteddate + ": " + poLines[x].expectedDate;
			listText += " | ";
			listText += receivingMessages.tablet_remainingqty + ": " + poLines[x].qtyOpen;
			listText += "</li>";
		}
		$("#lineList").html(listText);
		if (fieldName == 'transferReceiptId') {
			$("#lineList li").click(function(e) {
				getOriginalReceiptData(currentItem().poLines[$(e.currentTarget).attr("data-poLineIndex")].transferReceiptId);
				// SSS 9/19/13 added to troubleshoot
				// SSS 9/19/13 poLineChooserClick(e);
				$("#poLineChooserPopup").popup("close");
			});
		}
		else {
			$("#lineList li").click(function(e) {
				poLineChooserClick(e);
			});
		}
		if ($("#lineList").hasClass("ui-listview")) {
			$("#lineList").listview("refresh");
		}
	}
}

function localizeMessages() {
	var messages = sessionStorage.getItem("localizedReceivingMessages");

	if (messages == null) {
		var messageList = "tablet_newtrackingnumber,label_purchaseorder,tablet_carrierloaderror,tablet_selectfirstletterofcarrier,label_selectacarrier,"
				+ "tablet_pleaseentertrackingnumber,tablet_scanponumber,label_ponumber,tablet_retrievingpoitemlist,label_lotnumber,tablet_scancaps,tablet_pkgqty,"
				+ "tablet_qty,label_size,label_labelexpiredate,tablet_indef,tablet_scanlotnumber,tablet_enterlotnumber,tablet_enterthequantity,"
				+ "tablet_enterthepkgsize,tablet_scanreceiptid,tablet_selectpoline,tablet_selecttransferreceiptid,tablet_selectoriginalreceiptid,"
				+ "tablet_selectcustomerpo,tablet_binvalidationfailed,label_bin,tablet_notvalid,tablet_inactivecannotuse,tablet_doesnotallownoninteger,"
				+ "tablet_component,tablet_qtynotgreaterthanpolineqty,tablet_incorrectsuppliershipmentdate,tablet_incorrectsuppliershipdate,"
				+ "tablet_incorectdateofmanu,tablet_browsercodename,tablet_browsername,tablet_browserversion,tablet_cookiesenabled," + "tablet_platform,tablet_useragentheader,tablet_useragentlanguage,tablet_pleasescanreceiptid,tablet_pleasescanbinid,"
				+ "tablet_pleaseenterlotnumber,tablet_componentnum,tablet_incorrectlotnumber,tablet_incorrectexpirationdate,tablet_nodateonlabel," + "tablet_pleaseenterquantity,tablet_pleaseentersize,tablet_incorrecttrackingnumber,label_selectacarrier,"
				+ "tablet_selectreceiveddate,tablet_pleaseentera,tablet_number,tablet_sessionexpired,label_username,label_cancel,label_password,"
				+ "label_ok,tablet_pleaseenteruname,tablet_pleaseenterpwd,tablet_expecteddate,tablet_remainingqty,tablet_originalrcptidnotfound,"
				+ "tablet_errorfetchingorigrcptid,tablet_errornopolines,tablet_true,tablet_false,tablet_errorfetchingimagetypes,label_dateofmanufacture,"
				+ "label_dateofshipment,tablet_actualsuppliersdos,tablet_isnotavalid,tablet_receiveddate,tablet_transferrequest,tablet_customerownedinventory,label_rma,"
				+ "tablet_trnumber,label_customerpo,tablet_rmanumber,tablet_pleaseenterdom,tablet_pleaseenterdos,tablet_pleaseenterasds"
				+", label_flowdown, label_revisiondate, label_description, label_catalog, label_company, label_type";
		$.post("/tcmIS/haas/tabletLocalizeLabel.do", {
			input : messageList
		}, localizeMessagesReturn);
	}
	else {
		receivingMessages = JSON.parse(messages);
		applyLocalizedMessages();
	}
}

function localizeMessagesReturn(res) {
	var response = new responseObj(res);
	if (response.isError()) {
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + response.Message).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	}
	else {
		receivingMessages = response.localizedList;
		sessionStorage.setItem("localizedReceivingMessages", JSON.stringify(receivingMessages));
		applyLocalizedMessages();
	}
}

function applyLocalizedMessages() {
	$("#incomingTrxType").val(receivingMessages.label_purchaseorder);
	$('#transactionTypeInputLabel').html(receivingMessages.label_ponumber);
	$('#trxTypeListPO').html(receivingMessages.label_purchaseorder);
	$('#trxTypeListPO').attr("data-label", receivingMessages.label_ponumber);
	$('#trxTypeListTransfer').html(receivingMessages.tablet_transferrequest);
	$('#trxTypeListTransfer').attr("data-label", receivingMessages.tablet_trnumber);
	$('#trxTypeListCustomerPO').html(receivingMessages.tablet_customerownedinventory);
	$('#trxTypeListCustomerPO').attr("data-label", receivingMessages.label_customerpo);
	$('#trxTypeListRMA').html(receivingMessages.label_rma);
	$('#trxTypeListRMA').attr("data-label", receivingMessages.tablet_rmanumber);
	$("#rcvDate").datebox("option", {
		overrideTitleDateDialogLabel : receivingMessages.tablet_receiveddate
	});
	$("#rcvDate").datebox("refresh");
	$("#DOM").datebox("option", {
		overrideTitleDateDialogLabel : receivingMessages.label_dateofmanufacture
	});
	$("#DOM").datebox("refresh");
	$("#DOS").datebox("option", {
		overrideTitleDateDialogLabel : receivingMessages.label_dateofshipment
	});
	$("#DOS").datebox("refresh");
	$("#ASSD").datebox("option", {
		overrideTitleDateDialogLabel : receivingMessages.tablet_actualsuppliersdos
	});
	$("#ASSD").datebox("refresh");
}

function localizeLabels() {

	var messageList = "tablet_entertrackingnumber,tablet_scantrackingnumber,tablet_scandocumentnumber,tablet_enterthebinid,tablet_scanoriginalreceiptid,tablet_countconditionnotes,tablet_receiptnotes,"
			+ "tablet_receiptnotesinternal,label_home,tablet_inboundshipmentreceiving,label_next,label_trackingnumber,tablet_scan,label_carrier,tablet_receiveddate,tablet_transactiontype,"
			+ "label_purchaseorder,tablet_scantablet_countconditionnotes,label_close,tablet_transferrequestlabel_customerowenedinventory,label_rma,tablet_selecttransaction,"
			+ "tablet_addnewtransaction,tablet_inboundshipmentdocs,label_back,tablet_pleasescandocument,tablet_done,tablet_adddocuments,tablet_enterdocumentid,"
			+ "tablet_adddocument,tablet_receivingitems,tablet_newmsdsrevisionreceived,tablet_addreceipt,graphs_label_receipts,"
			+ "label_cancel,label_save,label_receiptid,tablet_scancaps,tablet_purchaseorderline,tablet_origrid,label_bin,label_dateofmanufacture,"
			+ "tablet_mfgdateofshipment,tablet_actualsupplier,tablet_hazcomlabeling,tablet_129plabeled,tablet_selectpoline,tablet_itemlabelingrequirements,tablet_yes,"
			+ "tablet_no,tablet_showitemimage,tablet_showitemdetail,tablet_internalnotes,tablet_receiptimages,label_images,tablet_addimage,label_deleteimage,"
			+ "tablet_selectimagetype,tablet_deletethisimage,label_delete,tablet_pagetitle,tablet_shutter,tablet_flashon,tablet_accept,label_reject,"
			+ "tablet_notes,tablet_commonnotes,tablet_externaldamage,tablet_hiddendamage,tablet_leaking,tablet_wrongpackagesize,tablet_labeledincorrectly,tablet_missingdocuments,"
			+ "tablet_damagedduringreceipt,tablet_lotdatecode,tablet_selectfirstletterofcarrier,tablet_flowdowns";

	$.post("/tcmIS/haas/tabletLocalizeLabel.do", {
		input : messageList
	}, localizeLabelsReturn);
}

function localizeLabelsReturn(res) {
	var results = new responseObj(res);

	if (results.isError()) {
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + results.Message).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	}
	else {
		var localizedLabelList = results.localizedList;
		$("#trkNum").on("click", function() {
			altAlphaInput(localizedLabelList.tablet_entertrackingnumber, 'trkNumPage', 'trkNum');
		});
		$("#trkNumScanButton").on("click", function(e) {
			enableScanner('onTrackingNumberScan(%json)', localizedLabelList.tablet_scantrackingnumber, 'trkNumPage');
		});
		$("#originalRIDScanButton").on("click", function(e) {
			enableScanner('onOriginalReceiptIdScan(%json)', localizedLabelList.tablet_scanoriginalreceiptid, 'receiptFormPage');
		});

		$("#countAndConditionNotesButton").on("click", function(e) {
			prepNotesPage("#trkNumPage", "#countAndConditionNotes", localizedLabelList.tablet_countconditionnotes);
		});
		$("#receiptNotesButton").on("click", function(e) {
			prepNotesPage("#receiptFormPage", "#hiddenReceiptNotes", localizedLabelList.tablet_receiptnotes);
		});
		$("#internalNotesButton").on("click", function(e) {
			prepNotesPage("#receiptFormPage", "#hiddenInternalReceiptNotes", localizedLabelList.tablet_receiptnotesinternal);
		});
		$('#addDocumentButton').on('click', function(event, ui) {
			enableScanner('onDocumentNumberScan(%json)', localizedLabelList.tablet_scandocumentnumber, 'documentsPage', 'Done');
		});

		$("#tablet_entertrackingnumber").html(localizedLabelList.tablet_entertrackingnumber);
		$("#tablet_scantrackingnumber").html(localizedLabelList.tablet_scantrackingnumber);
		$("#tablet_scandocumentnumber").html(localizedLabelList.tablet_scandocumentnumber);
		$("#tablet_enterthebinid").html(localizedLabelList.tablet_enterthebinid);
		$("#tablet_scanoriginalreceiptid").html(localizedLabelList.tablet_scanoriginalreceiptid);
		$("#tablet_countconditionnotes").html(localizedLabelList.tablet_countconditionnotes);
		$("#tablet_receiptnotes").html(localizedLabelList.tablet_receiptnotes);
		$("#tablet_receiptnotesinternal").html(localizedLabelList.tablet_receiptnotesinternal);
		$("#label_home1").html(localizedLabelList.label_home);
		$("#tablet_inboundshipmentreceiving").html(localizedLabelList.tablet_inboundshipmentreceiving);
		$("#label_next1").html(localizedLabelList.label_next);
		$("#label_trackingnumber").html(localizedLabelList.label_trackingnumber);
		$("#tablet_scan1").html(localizedLabelList.tablet_scan);
		$("#label_carrier").html(localizedLabelList.label_carrier);
		$("#tablet_receiveddate").html(localizedLabelList.tablet_receiveddate);
		$("#tablet_transactiontype").html(localizedLabelList.tablet_transactiontype);
		$("#label_purchaseorder1").html(localizedLabelList.label_purchaseorder);
		$("#tablet_scan2").html(localizedLabelList.tablet_scan);
		$("#tablet_countconditionnotes").html(localizedLabelList.tablet_countconditionnotes);
		$("#label_close1").html(localizedLabelList.label_close);
		$("#label_purchaseorder2").html(localizedLabelList.label_purchaseorder);
		$("#tablet_transferrequest").html(localizedLabelList.tablet_transferrequest);
		$("#label_customerowenedinventory").html(localizedLabelList.label_customerowenedinventory);
		$("#label_rma").html(localizedLabelList.label_rma);
		$("#tablet_selecttransaction").html(localizedLabelList.tablet_selecttransaction);
		$("#tablet_addnewtransaction").html(localizedLabelList.tablet_addnewtransaction);
		$("#tablet_inboundshipmentdocs").html(localizedLabelList.tablet_inboundshipmentdocs);
		$("#label_back1").html(localizedLabelList.label_back);
		$("#label_next2").html(localizedLabelList.label_next);
		$("#tablet_pleasescandocument").html(localizedLabelList.tablet_pleasescandocument);
		$("#tablet_done1").html(localizedLabelList.tablet_done);
		$("#tablet_adddocuments").html(localizedLabelList.tablet_adddocuments);
		$("#tablet_enterdocumentid").html(localizedLabelList.tablet_enterdocumentid);
		$("#tablet_adddocument").html(localizedLabelList.tablet_adddocument);
		$("#tablet_done2").html(localizedLabelList.tablet_done);
		$("#label_back2").html(localizedLabelList.label_back);
		$("#label_home2").html(localizedLabelList.label_home);
		$("#label_next3").html(localizedLabelList.label_next);
		$("#label_back4").html(localizedLabelList.label_back);
		$("#label_home4").html(localizedLabelList.label_home);
		$("#label_next4").html(localizedLabelList.label_next);
		$("#label_flowdowns").html(localizedLabelList.tablet_flowdowns);
		$("#tablet_receivingitems").html(localizedLabelList.tablet_receivingitems);
		$("#tablet_newmsdsrevisionreceived").html(localizedLabelList.tablet_newmsdsrevisionreceived);
		$("#tablet_addreceipt").html(localizedLabelList.tablet_addreceipt);
		$("#tablet_done3").html(localizedLabelList.tablet_done);
		$("#graphs_label_receipts1").html(localizedLabelList.graphs_label_receipts);
		$("#label_cancel1").html(localizedLabelList.label_cancel);
		$("#graphs_label_receipts2").html(localizedLabelList.graphs_label_receipts);
		$("#label_save").html(localizedLabelList.label_save);
		$("#label_receiptid").html(localizedLabelList.label_receiptid);
		$("#tablet_scancaps1").html(localizedLabelList.tablet_scancaps);
		$("#tablet_purchaseorderline").html(localizedLabelList.tablet_purchaseorderline);
		$("#tablet_origrid").html(localizedLabelList.tablet_origrid);
		$("#label_bin").html(localizedLabelList.label_bin);
		$("#tablet_scancaps2").html(localizedLabelList.tablet_scancaps);
		$("#label_dateofmanufacture").html(localizedLabelList.label_dateofmanufacture);
		$("#tablet_mfgdateofshipment").html(localizedLabelList.tablet_mfgdateofshipment);
		$("#tablet_actualsupplier").html(localizedLabelList.tablet_actualsupplier);
		$("#tablet_hazcomlabeling").html(localizedLabelList.tablet_hazcomlabeling);
		$("#tablet_129plabeled").html(localizedLabelList.tablet_129plabeled);
		$("#label_close2").html(localizedLabelList.label_close);
		$("#label_close3").html(localizedLabelList.label_close);
		$("#tablet_selectpoline").html(localizedLabelList.tablet_selectpoline);
		$("#tablet_itemlabelingrequirements").html(localizedLabelList.tablet_itemlabelingrequirements);
		$("#tablet_yes").html(localizedLabelList.tablet_yes);
		$("#tablet_no").html(localizedLabelList.tablet_no);
		$("#tablet_showitemimage").html(localizedLabelList.tablet_showitemimage);
		$("#tablet_showitemdetail").html(localizedLabelList.tablet_showitemdetail);
		$("#tablet_internalnotes").html(localizedLabelList.tablet_internalnotes);
		$("#tablet_notes1").html(localizedLabelList.tablet_notes);
		$("#tablet_receiptimages").html(localizedLabelList.tablet_receiptimages);
		$("#tablet_done4").html(localizedLabelList.tablet_done);
		$("#label_images").html(localizedLabelList.label_images);
		$("#tablet_addimage").html(localizedLabelList.tablet_addimage);
		$("#label_deleteimage").html(localizedLabelList.label_deleteimage);
		$("#tablet_selectimagetype").html(localizedLabelList.tablet_selectimagetype);
		$("#tablet_deletethisimage").html(localizedLabelList.tablet_deletethisimage);
		$("#label_delete").html(localizedLabelList.label_delete);
		$("#label_cancel2").html(localizedLabelList.label_cancel);
		$("#tablet_pagetitle").html(localizedLabelList.tablet_pagetitle);
		$("#label_back3").html(localizedLabelList.label_back);
		$("#tablet_shutter").html(localizedLabelList.tablet_shutter);
		$("#tablet_flashon").html(localizedLabelList.tablet_flashon);
		$("#label_cancel3").html(localizedLabelList.label_cancel);
		$("#tablet_accept").html(localizedLabelList.tablet_accept);
		$("#label_reject").html(localizedLabelList.label_reject);
		$("#tablet_notes2").html(localizedLabelList.tablet_notes);
		$("#tablet_done5").html(localizedLabelList.tablet_done);
		$("#tablet_commonnotes").html(localizedLabelList.tablet_commonnotes);
		$("#tablet_externaldamage").html(localizedLabelList.tablet_externaldamage);
		$("#tablet_hiddendamage").html(localizedLabelList.tablet_hiddendamage);
		$("#tablet_leaking").html(localizedLabelList.tablet_leaking);
		$("#tablet_wrongpackagesize").html(localizedLabelList.tablet_wrongpackagesize);
		$("#tablet_labeledincorrectly").html(localizedLabelList.tablet_labeledincorrectly);
		$("#tablet_missingdocuments").html(localizedLabelList.tablet_missingdocuments);
		$("#tablet_damagedduringreceipt").html(localizedLabelList.tablet_damagedduringreceipt);
		$("#tablet_lotdatecode").html(localizedLabelList.tablet_lotdatecode);
		$("#label_close4").html(localizedLabelList.label_close);
		$("#carriersListTitle").html(localizedLabelList.tablet_selectfirstletterofcarrier);
	}
}

function showReceiveInfoItemNote(itemnote) {
	debug("showReceiveInfoItemNote - " + itemnote);
	if (itemnote && itemnote.length > 0) {
		var itemNotePopup = "<div data-role='popup' id='itemNotePopupDiv' class='ui-content' data-overlay-theme='a'>";
		itemNotePopup += "<div id='itemNotePopupErrText' style='color:red;text-align:center'>" + itemnote + "</div>";
		itemNotePopup += "<div style='display:block;text-align:center;'>";
		itemNotePopup += "<button type='button' data-role='button' id='itemNoteOK' style='margin:0 auto; display:block;'>" + receivingMessages.label_ok + "</button>";
		itemNotePopup += "</div>";
		itemNotePopup += "</div>";

		$.mobile.activePage.append(itemNotePopup);
		$("#itemNotePopupDiv").trigger("create");
		$("#itemNotePopupDiv").popup();
		$("#itemNoteOK").on("click", function(e) {
			$("#itemNotePopupDiv").popup("close");
			$("#itemNotePopupDiv").remove();
		});
		$("#itemNotePopupDiv").popup("open");
	}
}

function getReceiveInfoItemNote(itemId, hubId) {
	var callArgs = new Object();
	callArgs.itemId = itemId;
	callArgs.hubId = hubId;
	var itemnote = sessionStorage.getItem("ReceiveInfo" + itemId);
	if (itemnote == null) {
		$.post("/tcmIS/haas/tabletReceiveInfoItemLabel.do", $.param(callArgs), function(data) {
			var response = new responseObj(data);
			if (response.isError()) {
				$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + response.Message).css("color", "red").css('font-size', '20px');
				$("#errorMessagesArea").show();
			}
			else {
				var infoNote = ifDefined(response.ReceiveInfoItemNote);
				showReceiveInfoItemNote(infoNote);
				sessionStorage.setItem("ReceiveInfo" + itemId, infoNote);
			}
		});
	}
	else {
		debug("showReceiveInfoItemNote from session ");
		showReceiveInfoItemNote(itemnote);
	}
}

function clearGHSCheckboxes() {
	$("#chkboxGHSProductIdentifier").data("checked", false);
	$("#chkboxGHSSignalWord").data("checked", false);
	$("#chkboxGHSPictogram").data("checked", false);
	$("#chkboxGHSHazardStatement").data("checked", false);
	$("#chkboxGHSPrecautionaryStatement").data("checked", false);
	$("#chkboxGHSSupplierIdentification").data("checked", false);

	$("#hiddenGHSProductName").val(true);
	$("#hiddenGHSSignalWord").val(true);
	$("#hiddenGHSPictogram").val(true);
	$("#hiddenGHSHazardStatement").val(true);
	$("#hiddenGHSPrecautionaryStatement").val(true);
	$("#hiddenGHSSupplierIdentification").val(true);
}
