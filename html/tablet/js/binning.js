var binnableReceipts = new Array();
var binnableComponents = new Array();
var refreshTimer;
var lastSortBy;
var binningMessages = null;

/*
 * filter object = {filterField : filterFieldName, filterValues : [value1, value2, ...]} lastFilter is an array of filter objects
 */
var lastFilter = new Array();

function showFilterPopup(displayFilterField, filterField) {
	if (ifDefined(filterField != "")) {
		var searchFor;
		var filterPopupHtml = new String;
		var uniqueValues = _(binnableComponents).uniq(function(component) {
			return component.components[0][filterField];
		})
		filterPopupHtml = "<div data-role='popup' id='filterPopup" + filterField + "' class='ui-content'>";
		filterPopupHtml += "<h2>Filter by " + displayFilterField + "</h2>"
		filterPopupHtml += "<h3>Select values to filter by</h3>";
		filterPopupHtml += "<div data-role='fieldcontain'>";
		filterPopupHtml += "<fieldset id='uniqueValueList' data-role='controlgroup'>";
		for ( var x = 0; x < uniqueValues.length; x++) {
			filterPopupHtml += "<input type='checkbox' id='uniqueValue" + x + "' data-value='" + uniqueValues[x].components[0][filterField] + "' />";
			filterPopupHtml += "<label for='uniqueValue" + x + "'>"
			filterPopupHtml += uniqueValues[x].components[0][filterField];
			filterPopupHtml += "</label>";
		}
		filterPopupHtml += "</fieldset>";
		filterPopupHtml += "<button type='button' data-role='button' id='filterButton" + filterField + "'>Filter</button>";
		filterPopupHtml += "</div>";
		filterPopupHtml += "</div>";
		$("#binningPage").append(filterPopupHtml);
		$("#filterPopup" + filterField).popup();
		$("#filterPopup" + filterField).trigger("create");
		$("#filterButton" + filterField).on("click", function(e) {
			var thisFilter = new Object();
			thisFilter.filterField = $(e.currentTarget).attr("id").replace("filterButton", "");
			thisFilter.filterValues = $.map($("#" + $(e.currentTarget).attr("id").replace("Button", "Popup") + " input:checked"), function(a) {
				return $(a).attr("data-value");
			});
			if (thisFilter.filterValues.length > 0) {
				lastFilter.push(thisFilter);
				var filteredBinnables = _.filter(binnableComponents, function(binnable) {
					if (this.filterField == "receiptId") {
						searchFor = binnable.receiptId.toString();
					}
					else {
						searchFor = binnable.components[0][this.filterField];
					}
					var idx = _.indexOf(this.filterValues, searchFor);
					debug(binnable.receiptId + ", Looking for " + this.filterField + " found at idx " + idx);
					// only zero = false, if the return index is 0 it will be intereperated as false, if it is -1 it will be interperatd to True.
					// adding one makes 0 true and -1 false
					return idx + 1;
				}, thisFilter);
				displayBinnableTable(filteredBinnables);
			}
			$("#" + $(e.currentTarget).attr("id").replace("Button", "Popup")).popup("close");
		});
		$("#filterPopup" + filterField).on("popupafterclose", function(e) {
			$(e.currentTarget).remove();
		});
		$("#filterPopup" + filterField).popup("open");
		clearRefreshTimer();
	}
}

function sortBinnableComponents(componentList, sortByField) {
	if (ifDefined(sortByField) != "") {
		return _(componentList).sortBy(function(component) {
			return component.components[0][sortByField];
		});
	}
	else {
		return componentList;
	}
}

function findReceiptId(rid) {
	debug("find receipt Id " + rid);
	for ( var x = 0; x < binnableComponents.length; x++) {
		if (binnableComponents[x].receiptId == rid) {
			return x;
		}
	}
	return -1;
}

function clearRefreshTimer() {
	debug("clear refresh timer");
	if (typeof (refreshTimer) != "undefined") {
		debug("clear interval");
		clearInterval(refreshTimer);
	}
}

function loadBinnableReceipts() {
	if (lastFilter.length == 0) {
		debug("loadBinnableReceipts");
		clearRefreshTimer();
		var currentHub = sessionStorage.getItem("currentHub");
		$.mobile.loading('show');
		$.post("/tcmIS/haas/tabletBinnableReceipts.do", {
			"hub" : currentHub
		}, displayBinnableReceipts);
	}
}

function putAwayReceiptIdValidate(receiptId) {
	debug("put away receipt validate " + receiptId);
	if (receiptId != $("#hiddenPutAwayReceiptId").val()) {
		alert(binningMessages.tablet_nomatchreceiptid1 + $("#hiddenPutAwayReceiptId").val() + ", " + binningMessages.tablet_nomatchreceiptid2 + " " + receiptId);
		return false;
	}
	return true;
}

function putAwayBinValidate(binId) {
	debug("put away bin validate " + binId)
	var callArgs = new Object();
	callArgs.hubId = sessionStorage.getItem("currentHub");
	callArgs.bin = binId;
	var params = $.param(callArgs);
	var binResult;
	return $.Deferred(function(def) {
		$.post("/tcmIS/haas/tabletValidateBin.do", params, function(data) {
			var returnData = $.parseJSON(data);
			if (returnData.Status == "OK") {
				if (returnData.Results == "Active") {
					def.resolve(true);
				}
				else if (returnData.Results == "Inactive") {
					alert("Bin " + binId + " is inactive and cannot be used.");
					def.resolve(false);
				}
				else {
					alert("Bin " + binId + " is not valid.");
					def.resolve(false);
				}
			}
			else {
				alert(binningMessages.tablet_binvalidationfailed + "= " + returnData.Status);
				def.resolve(false);
			}
		});
	});
}

function inputBinId(method, component) {
	debug("input bin " + method + ", component " + component);
	if (ifDefined($("#putAwayReceiptId").val() == "")) {
		alert(binningMessages.tablet_scanreceipttocontinue);
	}
	else {
		if (method == "scanner") {
			enableScanner('onPutAwayBinScan(%json, ' + component + ')', binningMessages.tablet_scanbinid, 'putAwayPage');
		}
		else if (method == "keyboard") {
			altAlphaInput(binningMessages.tablet_enterbinid, 'putAwayPage', 'putAwayBinId' + component);
		}
	}
}

function displayBinnableReceipts(response) {
	debug("display binnable receipts");
	var results = new responseObj(response);
	$.mobile.loading('hide');
	if (results.isError()) {
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + results.Message).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	}
	else {
		displayBinnableTable(sortBinnableComponents(createBinnableComponents(results.ReceiptStatuses), lastSortBy));
	}
}

function displayBinnableTable(statuses) {
	debug("display binnable table");
	if (typeof (refreshTimer) != "undefined") {
		clearInterval(refreshTimer);
	}
	binnableComponents = statuses;
	var tableHTML = new String;
	tableHTML = "<table data-role='table' id='binnableTable'>";
	tableHTML += "<thead>";
	tableHTML += "<tr>";
	tableHTML += "<th data-fieldName='receiptId' class='receiptId'>" + binningMessages.label_receiptid + "</th>";
	tableHTML += "<th data-fieldName='bin' class='bin'>" + binningMessages.label_bin + "</th>";
	tableHTML += "<th data-fieldName='lotStatus' class='lotStatus'>" + binningMessages.label_lotstatus + "</th>";
	tableHTML += "<th data-fieldName='inventoryGroupName' class='invGroup'>" + binningMessages.label_inventorygroup + "</th>";
	tableHTML += "<th data-fieldName='dot' class='hazClass'>" + binningMessages.label_hazardclass + "</th>";
	tableHTML += "<th>" + binningMessages.label_description + "</th>";
	tableHTML += "</tr>";
	tableHTML += "</thead>";

	if (statuses.length == 0) {
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + binningMessages.tablet_noreciptsready).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	}
	else {
		for ( var i = 0; i < statuses.length; i++) {
			var rec = statuses[i].components[0];
			var tdClass;
			if (rec.lotStatus != 'Available' && rec.lotStatus != 'Cert/Not Pickable') {
				tdClass = ' available';
				// blue
			}
			else if (rec.demandTypeOOR == false) {
				tdClass = ' MinMax';
				// green
			}
			else if (rec.demandTypeOOR == true) {
				tdClass = ' OOR' // pink
			}
			else {
				tdClass = "";
			}

			tableHTML += "<tr class='binnableRow' data-index='" + i + "'>";
			tableHTML += "<td class='receiptIdData" + tdClass + "'>" + rec.receiptId + "</td>";
			tableHTML += "<td class='" + tdClass + "Lt" + "'>" + rec.bin + "</td>";
			tableHTML += "<td class='" + tdClass + "Lt" + "'>" + rec.lotStatus + "</td>";
			tableHTML += "<td class='" + tdClass + "Lt" + "'>" + rec.inventoryGroupName + "</td>";
			tableHTML += "<td class='" + tdClass + "Lt" + "'>" + rec.dot + "</td>";
			tableHTML += "<td class='" + tdClass + "Lt" + "'>";
			tableHTML += "<b>" + binningMessages.tablet_desc + ": </b>" + rec.itemDesc + "<br>";
			tableHTML += "<b>" + binningMessages.tablet_propershippingname + ": </b>" + "<br>";
			tableHTML += "<b>" + binningMessages.tablet_flashpoint + ": </b>" + "<br>";
			tableHTML += "<b>" + binningMessages.tablet_lastbin + ": </b>" + rec.lastbin + "<br>";
			tableHTML += "<b>" + binningMessages.label_storagetemp + ": </b>" + ifDefined(rec.storageTemp).replace("  ", "") + "<br>";
			tableHTML += "<b>" + binningMessages.tablet_salesvelocity + ": </b>" + ifDefined(rec.salesVelocity) + "<br>";
			tableHTML += "</td>";
			tableHTML += "</tr>"
		}
	}
	tableHTML += "</table>";
	$("#binnablesTable").html(tableHTML);
	$("#binnableTable th").on("click", function(e) {
		lastSortBy = $(e.currentTarget).attr("data-fieldName");
		displayBinnableTable(sortBinnableComponents(binnableComponents, lastSortBy));
	});
	$("#binnableTable th").on("taphold", function(e) {
		var a = $(e.currentTarget).html();
		var b = $(e.currentTarget).attr("data-fieldname");
		showFilterPopup(a, b);
	});
	$("#binnableTable .binnableRow").on("click", function(e) {
		e.stopImmediatePropagation();
		showPutAwayPage($(e.currentTarget).attr("data-index"));
	});
	$("#binnableTable").trigger("create");

	var hdrString = "<div class='ui-header-trx-desc'>";
	hdrString += sessionStorage.getItem("currentHubName");
	hdrString += "&nbsp;&#183;&nbsp;" + binningMessages.label_lastupdated + ": " + now() + "</div>";
	if ($("#binningPage .ui-header-trx-desc").length > 0) {
		$("#binningPage .ui-header-trx-desc").html(hdrString);
	}
	else {
		$("#binningPage [data-role = 'header']").append("<div class='ui-header-trx-desc'>" + hdrString + "</div>");
	}

	debug("set timer");
	refreshTimer = setInterval(function() {
		loadBinnableReceipts();
	}, 300000);
	// 5 minutes
}

function parseBinnables(statuses, createMenuList) {
	debug("parse binnables");
	binnableReceipts = statuses;
	$('#binnables').empty();

	if (statuses.length == 0) {
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + binningMessages.tablet_noreciptsready).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	}
	else {
		for ( var i = 0; i < statuses.length; i++) {
			var rec = statuses[i];
			if (rec.receiptId == ifDefined(statuses[i - 1]))
				continue;
			var displayText = "<img src='/tcmIS/haas/thumbnail.do?image=" + rec.imageUrl + "'/> " + binningMessages.label_receiptid + ": " + rec.receiptId;
			displayText += "&nbsp;&#183;&nbsp;" + binningMessages.label_itemid + ": " + rec.itemId;
			displayText += "&nbsp;&#183;&nbsp;" + binningMessages.label_order_type + ": " + rec.demandType;
			displayText += "&nbsp;&#183;&nbsp;" + binningMessages.label_lotstatus + ": " + rec.lotStatus;
			displayText += "\n<br>" + binningMessages.tablet_currentlocation + ": " + rec.bin;
			displayText += "&nbsp;&#183;&nbsp;" + binningMessages.tablet_lastbin + ": " + rec.lastbin;
			displayText += "&nbsp;&#183;&nbsp;" + binningMessages.label_inventorygroup + ": " + rec.inventoryGroupName;
			displayText += "\n<BR>" + binningMessages.tablet_dothazarddata + ": " + rec.dot;
			displayText += "\n<BR>" + rec.itemDesc;

			if (rec.lotStatus != 'Available' && rec.lotStatus != 'Cert/Not Pickable') {
				liTheme = 'f';
				// blue
			}
			else if (rec.demandType == "MinMax") {
				liTheme = 'd';
				// green
			}
			else if (rec.demandType == "OOR") {
				liTheme = 'e' // pink
			}

			$('#binnables').append($('<li/>', {// here appending `<li>`
				'id' : rec.receiptId,
				'data-index' : i,
				'data-theme' : liTheme
			}).append(displayText));
		}
		$('#binnables').listview('refresh');
		// sss
		$("#binnables li").on("click", function(e) {
			showPutAwayPage(e);
		});
		$('#binnables').show();
	}

	var hdrString = "<div class='ui-header-trx-desc'>";
	hdrString += sessionStorage.getItem("currentHubName");
	hdrString += "&nbsp;&#183;&nbsp;" + binningMessages.label_lastupdated + ": " + now() + "</div>";
	if ($("#binningPage .ui-header-trx-desc").length > 0) {
		$("#binningPage .ui-header-trx-desc").html(hdrString);
	}
	else {
		$("#binningPage [data-role = 'header']").append("<div class='ui-header-trx-desc'>" + hdrString + "</div>");
	}

	// $.mobile.changePage($('#binningPage'));
}

function showPutAwayPage(e) {
	debug("show put away page " + e);
	// var thisBinnable = binnableReceipts[$(e.currentTarget).attr("data-index")];
	var thisBinnable = binnableComponents[e];
	// e.preventDefault();
	var imageSrc = thisBinnable.components[0].imageUrl.replace(/^(https?:\/\/[^\/]+)?\/?/, "/");
	$("#putAwayImage").attr("src", imageSrc);
	$("#hiddenPutAwayReceiptId").val(thisBinnable.components[0].receiptId);
	$("#hiddenPutAwayIndex").val(e);
	var receiptDetails1 = "<b>" + binningMessages.label_receiptid + ": </b>" + thisBinnable.components[0].receiptId + "<br>";
	receiptDetails1 += "<b>" + binningMessages.label_quantity + ": </b>" + thisBinnable.components[0].quantityReceived + "<br>";
	receiptDetails1 += "<b>" + binningMessages.tablet_currentlocation + "n: </b>" + thisBinnable.components[0].bin + "<br>";
	receiptDetails1 += "<b>" + binningMessages.tablet_salesvelocity + ": </b>" + ifDefined(thisBinnable.components[0].salesVelocity) + "<br>";
	receiptDetails1 += "<b>" + binningMessages.label_inventorygroup + "p: </b>" + thisBinnable.components[0].inventoryGroupName + "<br>";
	receiptDetails1 += "<b>" + binningMessages.tablet_lastbin + ": </b>" + thisBinnable.components[0].lastbin + "<br>";
	receiptDetails1 += "<b>" + binningMessages.label_storagetemp + ": </b>" + ifDefined(thisBinnable.components[0].storageTemp).replace("  ", "").replace(":", " ") + "<br>";
	receiptDetails1 += "<b>" + binningMessages.tablet_dot + ": </b>" + thisBinnable.components[0].dot;
	// var receiptDetails2 = "<b>Hub: </b>" + thisBinnable.components[0].hubName + " <br>";
	var receiptDetails2 = "<b>" + binningMessages.label_entity + ": </b>" + thisBinnable.components[0].opsEntityName + " <br>";
	receiptDetails2 += "<b>" + binningMessages.tablet_transaction + ": </b>" + thisBinnable.components[0].transactionNumber + " <br>";
	receiptDetails2 += "<b>" + binningMessages.label_supplier + ": </b>" + thisBinnable.components[0].supplierName + " <br>";
	var receiptDetails3 = "<b>" + binningMessages.label_receiptid + ": </b>" + thisBinnable.components[0].receiptId + " <br>";
	receiptDetails3 += "<b>" + binningMessages.label_quantity + ": </b>" + thisBinnable.components[0].quantityReceived + " <br>";
	receiptDetails3 += "<b>" + binningMessages.label_receivingstatus + ": </b>" + thisBinnable.components[0].receivingStatus + " <br>";
	receiptDetails3 += "<b>" + binningMessages.tablet_receivingstatusdate + ": </b>" + thisBinnable.components[0].receivingStatusDate;
	var receiptDetails4 = "<b>" + binningMessages.label_itemid + ": </b>" + thisBinnable.components[0].itemId + " <br>";
	receiptDetails4 += "<b>" + binningMessages.label_description + ": </b>" + thisBinnable.components[0].itemDesc + " <br>";
	receiptDetails4 += "<b>" + binningMessages.label_storagetemp + ": </b>" + ifDefined(thisBinnable.components[0].storageTemp).replace("  ", "").replace(":", " ") + "<br>";
	var receiptDetails5 = "<b>" + binningMessages.label_trackingnumber + ": </b>" + thisBinnable.components[0].trackingNumber + " <br>";
	receiptDetails5 += "<b>" + binningMessages.label_carrier + ": </b>" + thisBinnable.components[0].carrier + " <br>";
	receiptDetails5 += "<b>" + binningMessages.tablet_dot + ": </b>" + thisBinnable.components[0].dot;
	$("#binningDataCollapsible").html(receiptDetails1);
	$("#transactionDataCollapsible").html(receiptDetails2);
	$("#receiptDataCollapsible").html(receiptDetails3);
	$("#itemDataCollapsible").html(receiptDetails4);
	$("#shipmentDataCollapsible").html(receiptDetails5);
	$("#receiptDetailsDiv p").css({
		"margin-top" : "3px",
		"margin-bottom" : "3px"
	});
	if ($("#binningDataCollapsible").attr("initialized") != false) {
		$("#binningDataCollapsible").trigger("expand");
	}

	var binInputHtml = new String;
	var noNoteShown = true;
	for ( var x = 0; x < thisBinnable.components.length; x++) {
		if (thisBinnable.components.length > 1) {
			binInputHtml += "<div><b>" + binningMessages.label_component + " " + (x + 1) + ": </b>" + thisBinnable.components[x].materialDesc + "</div>";
			$("#multiBinSwitchDiv").show();
		}
		else {
			$("#multiBinSwitchDiv").hide();
		}
		if (thisBinnable.components[x].qualityControlItem  && noNoteShown) {
			noNoteShown = false;
			getQualityControlItemNote(thisBinnable.components[x].itemId, thisBinnable.components[x].inventoryGroup);
		}
		binInputHtml += "<div class='ui-grid-b'>";
		binInputHtml += "<div class='ui-block-a block50'>";
		binInputHtml += "<label for='putAwayBinId" + x + "'>" + binningMessages.tablet_scanbinidlabel + "</label>";
		binInputHtml += "<label><b>" + binningMessages.label_storagetemp + ": </b>" + ifDefined(thisBinnable.components[x].storageTemp).replace("  ", "") + "</label>";
		binInputHtml += "<BR><label><b>" + binningMessages.tablet_salesvelocity + ": </b>" + ifDefined(thisBinnable.components[x].salesVelocity) + "</label>";
		binInputHtml += "</div>";
		binInputHtml += "<div class='ui-block-b block30'>";
		binInputHtml += "<input type='text' id='putAwayBinId" + x + "' class='putAwayBinId' name='putAwayBinId' readonly='readonly' data-index='" + x + "'/>";
		binInputHtml += "</div>";
		binInputHtml += "<div class='ui-block-c block20'>";
		binInputHtml += "<button type='button' data-role='button' id='putAwayBinIdScanButton" + x + "' class='putAwayBinIdScanButton' data-theme='a' data-index='" + x + "'>" + binningMessages.tablet_scan + "</button>";
		binInputHtml += "</div>";
		binInputHtml += "</div>";
		binInputHtml += "<hr>";
	}
	$(".putAwayBinDiv").html(binInputHtml);
	$(".putAwayBinDiv").trigger('create');

	$(".putAwayBinId").on("click", function(e) {
		inputBinId('keyboard', $(e.currentTarget).attr("data-index"));
	});
	$(".putAwayBinIdScanButton").on("click", function(e) {
		inputBinId('scanner', $(e.currentTarget).attr("data-index"));
	});
	$(".putAwayBinId").on("change", function(e) {
		$.when(putAwayBinValidate($(e.currentTarget).val())).then(function(isValid) {
			if (!isValid) {
				$(e.currentTarget).val("");
			}
			if ($(e.currentTarget).attr("data-index") == 0) {
				if ($("#multiBinSwitch").is(":checked")) {
					$(".putAwayBinId").val($(e.currentTarget).val());
				}
			}
		});
	});

	var hdrString = "<div class='ui-header-trx-desc'>";
	hdrString += "" + binningMessages.label_receiptid + ": " + thisBinnable.components[0].receiptId + "</div>";
	if ($("#putAwayPage .ui-header-trx-desc").length > 0) {
		$("#putAwayPage .ui-header-trx-desc").html(hdrString);
	}
	else {
		$("#putAwayPage [data-role = 'header']").append("<div class='ui-header-trx-desc'>" + hdrString + "</div>");
	}

	$.mobile.changePage("#putAwayPage");
}

function putAwaySave() {
	debug("put away save");
	var thisBinnable = binnableComponents[$("#hiddenPutAwayIndex").val()];
	if (ifDefined($("#putAwayReceiptId").val()) == "") {
		alert(binningMessages.tablet_pleaseenterreceiptid);
	}
	for ( var x = 0; x < thisBinnable.components.length; x++) {
		if (ifDefined($("#putAwayBinId" + x).val()) == "") {
			alert(binningMessages.tablet_pleaseenterbinid + " " + (x + 1));
			return;
		}
	}

	$.mobile.loading('show', {
		text : "Saving Receipt ID " + $("#putAwayReceiptId").val(),
		textVisible : true
	});

	for ( var x = 0; x < thisBinnable.components.length; x++) {
		var callArgs = new Object();
		callArgs.receiptId = $("#putAwayReceiptId").val();
		callArgs.bin = $("#putAwayBinId" + x).val();
		if (thisBinnable.components.length > 1) {
			callArgs.manageKitsAsSingleUnit = 'N';
			callArgs.componentId = x + 1;
		}
		else {
			callArgs.manageKitsAsSingleUnit = 'Y';
		}
		var params = $.param(callArgs);
		$.post("/tcmIS/haas/tabletBinReceipt.do", params, function(data) {
			debug("start putAway callback");
			putAwayResult = $.parseJSON(data);
			debug("put away result = " + putAwayResult.Status);
			if (putAwayResult.Status != "OK") {
				$.mobile.loading("hide");
				alert(binningMessages.tablet_receiptupdatefailed + "\n" + putAwayResult.Message);
			}
			else {
				postPutAwayCleanup();
			}

		});
	}
}

function postPutAwayCleanup() {
	document.forms["putAwayForm"].reset();

	/*
	 * If we just put away the only binnable on the page reset the filter list then reload the binnable receipts else just remove it from the table and binnableList
	 */
	if (binnableComponents.length <= 1) {
		lastFilter = new Array();
		loadBinnableReceipts();
	}
	else {
		$(".binnableRow:has(.receiptIdData:contains('" + $("#hiddenPutAwayReceiptId").val() + "'))").remove();
		savedReceiptId = $("#hiddenPutAwayReceiptId").val();
		binnableComponents = _.reject(binnableComponents, function(bc, savedReceiptId) {
			bc.receiptId == savedReceiptId;
		});
	}

	$.mobile.loading("hide");
	$.mobile.changePage("#binningPage");
}

function now() {
	debug("now");
	var d = new Date;
	var dd = d.getDate();
	var mm = d.getMonth() + 1;
	var yy = d.getFullYear();
	var h = d.getHours();
	var m = d.getMinutes().toString();
	var ampm = "am";

	if (m.length == 1) {
		m = "0" + m;
	}
	if (h > 12) {
		h = h - 12;
		ampm = "pm";
	}
	return mm + "/" + dd + "/" + yy + " at " + h + ":" + m + " " + ampm;
}

function createBinnableComponents(arrayIn) {
	var arrayOut = new Array();
	if (arrayIn) {
		for ( var x = 0; x < arrayIn.length; x++) {
			if (typeof (arrayIn[x].componentId) == "undefined") {
				arrayIn[x].componentId = 1;
			}
		}

		arrayIn.sort(function(a, b) {
			if (a.receiptId - b.receiptId != 0) {
				return a.receiptId - b.receiptId;
			}
			else {
				return a.componentId - b.componentId;
			}
		});

		for ( var x = 0; x < arrayIn.length; x++) {
			var componentCounter = 0;
			var currentReceiptId = arrayIn[x].receiptId;
			// count number of records for each receipt Id
			while (arrayIn[x + componentCounter].receiptId == currentReceiptId) {
				componentCounter++;
				if (x + componentCounter >= arrayIn.length)
					break;
			}
			var componentList = new Array();
			for ( var y = 0; y < componentCounter; y++) {
				componentList.push(arrayIn[x + y]);
			}
			var newBinnableObj = new Object;
			newBinnableObj.receiptId = componentList[0].receiptId;
			newBinnableObj.components = componentList;
			arrayOut.push(newBinnableObj);
			x += (componentCounter - 1);
		}
	}
	return arrayOut;
}

function localizeLabels() {
	var labels = sessionStorage.getItem("localizedBinLabels");

	if (labels == null) {
		var labelList = "tablet_allpartsstored,tablet_scanreceipt,tablet_shipmentdata,label_itemdata,tablet_receiptdata,tablet_transactiondata,label_image,tablet_binningsummary,label_save,tablet_putawayscan,label_back"
				+ ",tablet_scan,label_refresh,tablet_binning,label_home,tablet_scanreceiptid,tablet_enterreceiptid";
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
		sessionStorage.setItem("localizedBinLabels", JSON.stringify(results.localizedList));
	}
}

function applyLocalizedLabels(labels) {
	
	$("#putAwayReceiptId").on("click", function() {
		altNumInput(labels.tablet_enterreceiptid, 'N', 'putAwayPage', 'putAwayReceiptId');
	});
	$('#putAwayReceiptIdScanButton').on('click', function(event, ui) {
		enableScanner('onPutAwayReceiptScan(%json)', labels.tablet_scanreceiptid, 'putAwayPage');
	});
	$("#binnablesReceiptScan").on("click", function(e) {
		enableScanner("onBinnableScan(%json)", labels.tablet_scanreceiptid, 'binningPage');
	});

	$("#tablet_allpartsstored").html(labels.tablet_allpartsstored);
	$("#tablet_scan2").html(labels.tablet_scan);
	$("#tablet_scan1").html(labels.tablet_scan);
	$("#tablet_scanreceipt").html(labels.tablet_scanreceipt);
	$("#tablet_shipmentdata").html(labels.tablet_shipmentdata);
	$("#label_itemdata").html(labels.label_itemdata);
	$("#tablet_receiptdata").html(labels.tablet_receiptdata);
	$("#tablet_transactiondata").html(labels.tablet_transactiondata);
	$("#label_image").html(labels.label_image);
	$("#tablet_binningsummary").html(labels.tablet_binningsummary);
	$("#label_save").html(labels.label_save);
	$("#tablet_putawayscan").html(labels.tablet_putawayscan);
	$("#label_back").html(labels.label_back);
	$("#label_refresh").html(labels.label_refresh);
	$("#tablet_binning").html(labels.tablet_binning);
	$("#label_home").html(labels.label_home);
}

function localizeMessageList() {
	var messages = sessionStorage.getItem("localizedBinMessages");

	if (messages == null) {
		var messageList = "label_receiptid,label_quantity,tablet_currentlocation,label_inventorygroup,tablet_lastbin,label_storagetemp,tablet_dot,label_hub,label_entity,tablet_transaction,label_supplier,"
				+ "label_receivingstatus,tablet_receivingstatusdate,label_itemid,label_description,label_trackingnumber,label_carrier,label_bin,label_lotstatus,label_hazardclass,tablet_flashpoint,"
				+ "label_order_type,tablet_dothazarddata,label_lastupdated,label_component,tablet_scanbinidlabel,tablet_scan,tablet_pleaseenterreceiptid,tablet_pleaseenterbinid,tablet_desc,tablet_propershippingname,"
				+ "tablet_receiptupdatefailed,tablet_sessionexpired,label_username,label_cancel,label_password,label_ok,tablet_pleaseenteruname,tablet_pleaseenterpwd,tablet_inventorygroupname,tablet_scanreceiptid"
				+ "tablet_scanreceipttocontinue,tablet_enterbinid,tablet_scanbinid,tablet_nomatchreceiptid1,tablet_nomatchreceiptid2,tablet_noreciptsready,tablet_binvalidationfailed,tablet_noreciptsready,tablet_salesvelocity";
		$.post("/tcmIS/haas/tabletLocalizeLabel.do", {
			"input" : messageList
		}, localizeMessageListReturn);
	}
	else {
		binningMessages = JSON.parse(messages);
	}
}

function localizeMessageListReturn(res) {
	var results = new responseObj(res);

	if (results.isError()) {
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + results.Message).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	}
	else {
		binningMessages = results.localizedList;
		sessionStorage.setItem("localizedBinMessages", JSON.stringify(binningMessages));
	}
}
