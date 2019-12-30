var cycleCountables = new Array();
var refreshTimer;
var lastSortBy;
var cycleCountMessages = null;

/*
 * filter object = {filterField : filterFieldName, filterValues : [value1, value2, ...]} lastFilter is an array of filter objects
 */
var lastFilter = new Array();

function showFilterPopup(displayFilterField, filterField) {
	if (ifDefined(filterField != "")) {
		var searchFor;
		var filterPopupHtml = new String;
		var sortedCountables = _(cycleCountables).sortBy(function(countable){
			return countable[filterField];
			});
		var uniqueValues = _(sortedCountables).uniq(function(countable) {
			return countable[filterField];
		});
		filterPopupHtml = "<div data-role='popup' id='filterPopup" + filterField + "' class='ui-content'>";
		filterPopupHtml += "<h2>Filter by " + displayFilterField + "</h2>";
		filterPopupHtml += "<h3>Select values to filter by</h3>";
		filterPopupHtml += "<div data-role='fieldcontain'>";
		filterPopupHtml += "<fieldset id='uniqueValueList' data-role='controlgroup'>";
		for ( var x = 0; x < uniqueValues.length; x++) {
			filterPopupHtml += "<input type='checkbox' id='uniqueValue" + x + "' data-value='" + uniqueValues[x][filterField] + "' />";
			filterPopupHtml += "<label for='uniqueValue" + x + "'>";
/*			if(filterField = "actualQuantity" && uniqueValues[x][filterField] == "") 
				filterPopupHtml += cycleCountMessages.tablet_uncounted;
			else */
				filterPopupHtml += uniqueValues[x][filterField];
			filterPopupHtml += "</label>";
		}
		filterPopupHtml += "</fieldset>";
		filterPopupHtml += "<button type='button' data-role='button' id='filterButton" + filterField + "'>Filter</button>";
		filterPopupHtml += "</div>";
		filterPopupHtml += "</div>";
		$("#cycleCountingPage").append(filterPopupHtml);
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
				var filteredCountables = _.filter(cycleCountables, function(countable) {
					if (this.filterField == "receiptId") {
						searchFor = countable.receiptId.toString();
					}
					else if (this.filterField == "expectedQuantity") {
						searchFor = countable.expectedQuantity.toString();
					}
					else if (this.filterField == "actualQuantity") {
						searchFor = countable.actualQuantity.toString();
					}
					else {
						searchFor = countable[this.filterField];
					}
					var idx = _.indexOf(this.filterValues, searchFor);
					debug(countable.receiptId + ", Looking for " + this.filterField + " found at idx " + idx);
					// only zero = false, if the return index is 0 it will be intereperated as false, if it is -1 it will be interperatd to True.
					// adding one makes 0 true and -1 false
					return idx + 1;
				}, thisFilter);
				displayCycleCountTable(filteredCountables);
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

function clearRefreshTimer() {
	debug("clear refresh timer");
	if (typeof (refreshTimer) != "undefined") {
		debug("clear interval");
		clearInterval(refreshTimer);
	}
}

function loadCycleCountables() {
	/*if (lastFilter.length == 0) {*/
		debug("loadCycleCounts");
		clearRefreshTimer();
		var currentHub = sessionStorage.getItem("currentHub");
		$.mobile.loading('show');
		$.post("/tcmIS/haas//tabletCycleCountSearch.do", {
			"hub" : currentHub
		}, displayCycleCounts);
	//}
}

function displayCycleCounts(response) {
	debug("display hub cycle counts");
	var results = new responseObj(response);
	$.mobile.loading('hide');
	if (results.isError()) {
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + results.Message).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	}
	else {
		cycleCountables = results.HubCycleCounts;
		displayCycleCountTable(cycleCountables);
	}
}

function displayCycleCountTable(cycleCountables) {
	debug("display cycle count table");
	if (typeof (refreshTimer) != "undefined") {
		clearInterval(refreshTimer);
	}
	var tableHTML = new String;
	tableHTML = "<table data-role='table' id='resultTable'>";
	tableHTML += "<thead>";
	tableHTML += "<tr>";
	tableHTML += "<th data-fieldName='countId' class='countId'/>";
	tableHTML += "<th data-fieldName='room' class='room'>" + cycleCountMessages.label_room + "</th>";
	tableHTML += "<th data-fieldName='bin' class='bin'>" + cycleCountMessages.label_bin + "</th>";
	tableHTML += "<th data-fieldName='receiptId' class='receiptId'>" + cycleCountMessages.label_receiptid + "</th>";
	tableHTML += "<th data-fieldName='expectedQuantity' class='expectedQuantity'>" + cycleCountMessages.cyclecountresults_label_expectedqty + "</th>";
	tableHTML += "<th data-fieldName='actualQuantity' class='actualQuantity'>" + cycleCountMessages.label_actualcount + "</th>";
	tableHTML += "<th>" + cycleCountMessages.label_description + "</th>";
	tableHTML += "</tr>";
	tableHTML += "</thead>";

	if (cycleCountables.length == 0) {
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + cycleCountMessages.tablet_norcptsinopencountstatus).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	}
	else {
		for ( var i = 0; i < cycleCountables.length; i++) {
			if(cycleCountables[i] != false){
				var rec = cycleCountables[i];
	
				tableHTML += "<tr data-index='" + i + "'>";
				tableHTML += "<td class='countId'>" + rec.countId + "</td>";
				tableHTML += "<td class='room'>" + rec.room + "</td>";
				tableHTML += "<td class='bin'>" + rec.bin + "</td>";
				tableHTML += "<td class='receiptId'>" + rec.receiptId + "</td>";
				tableHTML += "<td class='expectedQuantity'>" + rec.expectedQuantity + "</td>";
				tableHTML += "<td class='actualQuantity'><input type='text' class='actualQuantityData' id='actualQuantity" + i + "' name='actualQuantity' readonly='readonly' data-index='" + i + "' value='" + rec.actualQuantity + "'/></td>";
				tableHTML += "<td>" + rec.itemDesc + "</td>";
				tableHTML += "</tr>";
			}
		}
	}
	tableHTML += "</table>";
	$("#cycleCountables").html(tableHTML);
	$("#resultTable th").on("click", function(e) {
		lastSortBy = $(e.currentTarget).attr("data-fieldName");
		displayCycleCountTable(sortCountables(cycleCountables, lastSortBy));
	});
	$("#resultTable th").on("taphold", function(e) {
		var a = $(e.currentTarget).html();
		var b = $(e.currentTarget).attr("data-fieldname");
		showFilterPopup(a, b);
	});
	$(".actualQuantityData").on("click", function(e) {
		altNumInput(cycleCountMessages.label_actualcount, 'N', 'cycleCountables', $(e.currentTarget).attr("id"));
	});
	$(".actualQuantityData").on("change", function(e) {
		var ind = $(e.currentTarget).attr("data-index");
		if (ifDefined($(e.currentTarget).val()) == "" ||  parseInt($(e.currentTarget).val(), 10) < 0) {
			alert(cycleCountMessages.tablet_pleaseenteravalidqty);
		}
		else{
			$("#actualQuantity"+ind).val(parseInt($(e.currentTarget).val(), 10));
			cycleCountUpdate($(e.currentTarget).attr("data-index"));
		}
	});
	$("#cycleCountableTable").trigger("create");

	var hdrString = "<div class='ui-header-trx-desc'>";
	hdrString += sessionStorage.getItem("currentHubName");
	hdrString += "&nbsp;&#183;&nbsp;" + cycleCountMessages.label_lastupdated + ": " + now() + "</div>";
	if ($("#cycleCountingPage .ui-header-trx-desc").length > 0) {
		$("#cycleCountingPage .ui-header-trx-desc").html(hdrString);
	}
	else {
		$("#cycleCountingPage [data-role = 'header']").append("<div class='ui-header-trx-desc'>" + hdrString + "</div>");
	}

	debug("set timer");
	refreshTimer = setInterval(function() {
		loadCycleCountables();
	}, 300000);
	// 5 minutes
}


function localizeLabels() {
	var labels = sessionStorage.getItem("localizedCycleCountLabels");

	if (labels == null) {
		var labelList = "label_refresh,tabletCycleCounting,label_home,tablet_enterreceiptid";
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
		sessionStorage.setItem("localizedCycleCountLabels", JSON.stringify(results.localizedList));
	}
}

function applyLocalizedLabels(labels) {
	$("#label_refresh").html(labels.label_refresh);
	$("#tablet_cycle_counting").html(labels.tabletCycleCounting);
	$("#label_home").html(labels.label_home);
}

function localizeMessageList() {
	var messages = sessionStorage.getItem("localizedCycleCountMessages");

	if (messages == null) {
		var messageList = "label_receiptid,cyclecountresults_label_expectedqty,label_actualcount,label_hub,label_room,label_bin,label_description,"
				+ "tablet_countupdatefailed,tablet_sessionexpired,label_username,label_cancel,label_password,label_ok,tablet_pleaseenteruname,tablet_pleaseenterpwd,"
				+ "label_lastupdated,tablet_noreciptsready, tablet_pleaseenteravalidqty, tablet_norcptsinopencountstatus, tablet_uncounted";
		$.post("/tcmIS/haas/tabletLocalizeLabel.do", {
			"input" : messageList
		}, localizeMessageListReturn);
	}
	else {
		cycleCountMessages = JSON.parse(messages);
	}
}

function localizeMessageListReturn(res) {
	var results = new responseObj(res);

	if (results.isError()) {
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + results.Message).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	}
	else {
		cycleCountMessages = results.localizedList;
		sessionStorage.setItem("localizedCycleCountMessages", JSON.stringify(cycleCountMessages));
	}
}

function sortCountables(countableList, sortByField) {
	if (ifDefined(sortByField) != "") {
		return _(countableList).sortBy(function(countable) {
			return countable[sortByField];
		});
	}
	else {
		return countableList;
	}
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

function cycleCountUpdate(index) {
	debug("cycle count update");
	var thisCountable = cycleCountables[index];
	var actualQuantity = $("#actualQuantity"+index).val();
	var receiptId = thisCountable.receiptId;
	var countId = thisCountable.countId;
	
	$.mobile.loading('show', {
		text : "Saving count for Receipt ID " + receiptId,
		textVisible : true
	});

	var callArgs = new Object();
	callArgs.actualQuantity = actualQuantity;
	callArgs.receiptId = receiptId;
	callArgs.countId = countId;
		
	var params = $.param(callArgs);
	$.post("/tcmIS/haas//tabletCycleCountUpdate.do", params, function(data) {
		debug("start loadCycleCountables callback");
		cycleCountResult = $.parseJSON(data);
		debug("cycle count update result = " + cycleCountResult.Status);
		if (cycleCountResult.Status != "OK") {
			$.mobile.loading("hide");
			alert(cycleCountMessages.tablet_countupdatefailed + "\n" + cycleCountResult.Message);
		}
		else {
			loadCycleCountables();
		}
	});
}