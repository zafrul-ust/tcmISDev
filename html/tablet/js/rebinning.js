var localizedMessages = null;
var binnableComponents = new Array();
var reBinReceiptData= null;
var curReceiptId = "";

function validateBin(binId) {
	debug("validateBin - " + binId)
	var callArgs = new Object();
	callArgs.hubId = sessionStorage.getItem("currentHub");
	callArgs.bin = binId;
	return $.Deferred(function(def) {
		$.post("/tcmIS/haas/tabletValidateBin.do", $.param(callArgs), function(data) {
			var results = new responseObj(data);
			if (results.isError()) {
				alert(localizedMessages.tablet_binvalidationfailed + "= " + returnData.Status);
				def.resolve(false);
			}
			else {
				if (results.Results == "Active") {
					def.resolve(true);
				}
				else if (results.Results == "Inactive") {
					alert("Bin " + binId + " is inactive and cannot be used.");
					def.resolve(false);
				}
				else {
					alert("Bin " + binId + " is not valid.");
					def.resolve(false);
				}
			}
		});
	});
}

function inputBinId(method, component) {
	debug("input bin " + method + ", component " + component);
	if (curReceiptId == "") {
		alert(localizedMessages.tablet_scanreceipttocontinue);
	}
	else {
		if (method == "scanner") {
			enableScanner("onReBinBinScan(%json, " + component + ")", localizedMessages.tablet_scanbinid, 'reBinPage');
		}
		else if (method == "keyboard") {
			altAlphaInput(localizedMessages.tablet_enterbinid, 'reBinPage', 'reBinBinId' + component);
		}
	}
}

function loadReceiptIdData(receiptId) {	
	debug("loadReceiptIdData");
	var callArgs = new Object();
	callArgs.receiptId = receiptId;
	callArgs.hub = sessionStorage.getItem("currentHub");
	$.mobile.loading('show');
	curReceiptId = "";
	//debug("callArgs = " + callArgs.hub + " and " + callArgs.receiptId);
	$.post('/tcmIS/haas/tabletVerifyRetreiveReceiptData.do', $.param(callArgs), function(data) {
		$.mobile.loading('hide');
		var results = new responseObj(data);
		debug(results.isOK());
		if (results.isOK()) {
				if (!results.ReceiptData ) {
					var message = formatMessage(localizedMessages.tablet_norecordfoundforreceipt, receiptId); 
					alert(message);
				} 
				else if (!results.ReceiptData[0].receivingQCDone) {
					alert(formatMessage(localizedMessages.tablet_waitingforqc, receiptId));
				}
				else {
					curReceiptId = receiptId;
					showReBinPage(results.ReceiptData);
				}
				$("#reBinReceiptId").val("");
		}
	});
}

function showReBinPage(receiptData) {
	debug("showReBinPage");
	reBinReceiptData = receiptData;
	var imageSrc = receiptData[0].imageUrl.replace(/^(https?:\/\/[^\/]+)?\/?/, "/");
	$("#reBinImage").attr("src", imageSrc);
	var receiptDetails1 = "<b>" + localizedMessages.label_receiptid + ": </b>" + receiptData[0].receiptId + "<br>";
	receiptDetails1 += "<b>" + localizedMessages.label_quantity + ": </b>" + receiptData[0].quantityReceived + "<br>";
	receiptDetails1 += "<b>" + localizedMessages.tablet_currentlocation + ": </b>" + receiptData[0].bin + "<br>";
	receiptDetails1 += "<b>" + localizedMessages.label_inventorygroup + ": </b>" + receiptData[0].inventoryGroupName + "<br>";
	//receiptDetails1 += "<b>" + localizedMessages.tablet_lastbin + ": </b>" + receiptData[0].lastbin + "<br>";
	receiptDetails1 += "<b>" + localizedMessages.label_storagetemp + ": </b>" + ifDefined(receiptData[0].storageTemp).replace("  ", "").replace(":", " ") + "<br>";
	receiptDetails1 += "<b>" + localizedMessages.tablet_dot + ": </b>" + receiptData[0].dot;
	//var receiptDetails2 = "<b>" + localizedMessages.label_entity + ": </b>" + receiptData[0].opsEntityName + " <br>";
	//receiptDetails2 += "<b>" + localizedMessages.tablet_transaction + ": </b>" + receiptData[0].transactionNumber + " <br>";
	//receiptDetails2 += "<b>" + localizedMessages.label_supplier + ": </b>" + receiptData[0].supplierName + " <br>";
	//var receiptDetails3 = "<b>" + localizedMessages.label_receiptid + ": </b>" + receiptData[0].receiptId + " <br>";
	//receiptDetails3 += "<b>" + localizedMessages.label_quantity + ": </b>" + receiptData[0].quantityReceived + " <br>";
	//receiptDetails3 += "<b>" + localizedMessages.label_receivingstatus + ": </b>" + receiptData[0].receivingStatus + " <br>";
	//receiptDetails3 += "<b>" + localizedMessages.tablet_receivingstatusdate + ": </b>" + receiptData[0].receivingStatusDate;
	var receiptDetails4 = "<b>" + localizedMessages.label_itemid + ": </b>" + receiptData[0].itemId + " <br>";
	receiptDetails4 += "<b>" + localizedMessages.label_description + ": </b>" + receiptData[0].itemDesc + " <br>";
	receiptDetails4 += "<b>" + localizedMessages.label_storagetemp + ": </b>" + ifDefined(receiptData[0].storageTemp).replace("  ", "").replace(":", " ") + "<br>";
	//var receiptDetails5 = "<b>" + localizedMessages.label_trackingnumber + ": </b>" + receiptData[0].trackingNumber + " <br>";
	//receiptDetails5 += "<b>" + localizedMessages.label_carrier + ": </b>" + receiptData[0].carrier + " <br>";
	//receiptDetails5 += "<b>" + localizedMessages.tablet_dot + ": </b>" + receiptData[0].dot;
	$("#rebinningDataCollapsible").html(receiptDetails1);
	//$("#transactionDataCollapsible").html(receiptDetails2);
	//$("#receiptDataCollapsible").html(receiptDetails3);
	$("#itemDataCollapsible").html(receiptDetails4);
	//$("#shipmentDataCollapsible").html(receiptDetails5);
	$("#receiptDetailsDiv p").css({
		"margin-top" : "3px",
		"margin-bottom" : "3px"
	});
	if ($("#rebinningDataCollapsible").attr("initialized") != false) {
		$("#rebinningDataCollapsible").trigger("expand");
	}

	var binInputHtml = new String;
	for ( var x = 0; x < receiptData.length; x++) {
		if (receiptData.length > 1) {
			binInputHtml += "<div><b>" + localizedMessages.label_component + " " + (x + 1) + ": </b>" + receiptData[x].materialDesc + "</div>";
			$("#multiBinSwitchDiv").show();
		}
		else {
			$("#multiBinSwitchDiv").hide();
		}
		binInputHtml += "<div class='ui-grid-b'>";
		binInputHtml += "<div class='ui-block-a block30'>";
		binInputHtml += "<label for='reBinBinId" + x + "'>" + localizedMessages.tablet_scanbinidlabel + "</label>";
		binInputHtml += "<label><b>" + localizedMessages.label_storagetemp + ": </b>" + ifDefined(receiptData[x].storageTemp).replace("  ", "").replace(":", " ") + "</label>";
		binInputHtml += "</div>";
		binInputHtml += "<div class='ui-block-b block40'>";
		binInputHtml += "<input type='text' id='reBinBinId" + x + "' class='reBinBinId' name='reBinBinId' readonly='readonly' data-index='" + x + "'/>";
		binInputHtml += "</div>";
		binInputHtml += "<div class='ui-block-c block30'>";
		binInputHtml += "<button type='button' data-role='button' id='reBinBinIdScanButton" + x + "' class='reBinBinIdScanButton' data-theme='a' data-index='" + x + "'>" + localizedMessages.tablet_scan + "</button>";
		binInputHtml += "</div>";
		binInputHtml += "</div>";
		binInputHtml += "<hr>";
	}
	$(".reBinBinDiv").html(binInputHtml);
	$(".reBinBinDiv").trigger('create');

	$(".reBinBinId").on("click", function(e) {
		inputBinId('keyboard', $(e.currentTarget).attr("data-index"));
	});
	$(".reBinBinIdScanButton").on("click", function(e) {
		inputBinId('scanner', $(e.currentTarget).attr("data-index"));
	});
	$(".reBinBinId").on("change", function(e) {
		$.when(reBinBinValidate($(e.currentTarget).val())).then(function(isValid) {
			if (!isValid) {
				$(e.currentTarget).val("");
			}
			if ($(e.currentTarget).attr("data-index") == 0) {
				if ($("#multiBinSwitch").is(":checked")) {
					$(".reBinBinId").val($(e.currentTarget).val());
				}
			}
		});
	});
	$.mobile.changePage("#reBinPage");
}

function reBinSave() {
	debug("re bin save");

	for ( var x = 0; x < reBinReceiptData.length; x++) {
		if (ifDefined($("#reBinBinId" + x).val()) == "") {
			alert(localizedMessages.tablet_pleaseenterbinid + " " + (x + 1));
			return;
		}
	}

	$.mobile.loading('show', {text : localizedMessages.tablet_saverebininfo + " " + curReceiptId, textVisible : true});

	var callArgs = new Object();
	callArgs.receiptId = curReceiptId;
	for ( var x = 0; x < reBinReceiptData.length; x++) {
		callArgs.bin = $("#reBinBinId" + x).val();
		if (reBinReceiptData.length > 1) {
			callArgs.manageKitsAsSingleUnit = reBinReceiptData[x].manageKitsAsSingleUnit;
			callArgs.componentId = x + 1;
		}
		else {
			callArgs.manageKitsAsSingleUnit = 'Y';
		}

		$.post("/tcmIS/haas/tabletReBinReceipt.do",  $.param(callArgs), function(data) {
			var results = new responseObj(data);
			if (results.isError()) {
				$.mobile.loading("hide");
				alert(localizedMessages.tablet_receiptupdatefailed + "\n" + results.Message);
			}
			postReBinCleanup();
		});
	}
}

function postReBinCleanup() {
	debug("postReBinCleanup");
	document.forms["reBinForm"].reset();
	$("#reBinReceiptId").val("");
	$.mobile.loading("hide");
	$.mobile.changePage("#reBinReceiptPage");
}

function localizeLabels() {
	var labels = sessionStorage.getItem("localizedReBinLabels");
	if (labels == null) {
		var labelList = "label_save,tablet_scan,tablet_rebinning,label_home,tablet_enterreceiptid,tablet_scanreceiptid, tablet_scanreceipt,"
				+ "tablet_rebinningsummary,label_image,tablet_transactiondata,tablet_receiptdata,label_itemdata,tablet_shipmentdata";
		$.post("/tcmIS/haas/tabletLocalizeLabel.do", {
			"input" : labelList
		}, localizeLabelsReturn);
	}
	else {
		applyLocalizedLabels(JSON.parse(labels));
	}
}

function localizeLabelsReturn(res) {
	var results = new responseObj(res);

	if (results.isError()) {
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + results.Message).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	}
	else {
		applyLocalizedLabels(results.localizedList);
		sessionStorage.setItem("localizedReBinLabels", JSON.stringify(results.localizedList));
	}
}

function applyLocalizedLabels(labels) {
	$("#reBinReceiptId").on("click", function() {
		altNumInput(labels.tablet_enterreceiptid, 'N', 'reBinReceiptPage', 'reBinReceiptId');
	});
	$("#reBinReceiptIdScanButton").on("click", function(e) {
		enableScanner('reBinReceiptScanner(%json)', labels.tablet_scanreceiptid, 'reBinReceiptPage');
	});

	$("#tablet_scan1").html(labels.tablet_scan);
	$("#label_save").html(labels.label_save);
	$("#tablet_binning").html(labels.tablet_binning);
	$("#label_home").html(labels.label_home);
}

function localizeMessageList() {
	var messages = sessionStorage.getItem("localizedReBinMessages");

	if (messages == null) {
		var messageList = "label_receiptid,label_quantity,tablet_currentlocation,label_inventorygroup,tablet_lastbin,label_storagetemp,tablet_dot,label_hub,label_entity,tablet_transaction,label_supplier,"
				+ "label_receivingstatus,tablet_receivingstatusdate,label_itemid,label_description,label_trackingnumber,label_carrier,label_bin,label_lotstatus,label_hazardclass,tablet_flashpoint,"
				+ "label_order_type,tablet_dothazarddata,label_lastupdated,label_component,tablet_scanbinidlabel,tablet_scan,tablet_pleaseenterreceiptid,tablet_pleaseenterbinid,tablet_desc,tablet_propershippingname,"
				+ "tablet_receiptupdatefailed,tablet_sessionexpired,label_username,label_cancel,label_password,label_ok,tablet_pleaseenteruname,tablet_pleaseenterpwd,tablet_inventorygroupname,"
				+ "tablet_scanreceipttocontinue,tablet_enterbinid,tablet_scanbinid,tablet_nomatchreceiptid1,tablet_nomatchreceiptid2,tablet_noreciptsready,tablet_binvalidationfailed,tablet_noreciptsready," 
				+ "tablet_saverebininfo,tablet_waitingforqc,tablet_norecordfoundforreceipt";
		$.post("/tcmIS/haas/tabletLocalizeLabel.do", {
			"input" : messageList
		}, localizeMessageListReturn);
	}
	else {
		localizedMessages = JSON.parse(messages);
	}
}

function localizeMessageListReturn(res) {
	var results = new responseObj(res);

	if (results.isError()) {
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + results.Message).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	}
	else {
		localizedMessages = results.localizedList;
		sessionStorage.setItem("localizedReBinMessages", JSON.stringify(localizedMessages));
	}
}
