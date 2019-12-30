function mySearchOnload() {
	setSearchFrameSize();
}

function submitSearchForm() {
	document.genericForm.target = 'resultFrame';
	var action = document.getElementById("action");
	action.value = 'search';

	var now = new Date();
	document.getElementById("startSearchTime").value = now.getTime();

	var flag = validateForm();
	if (flag) {
		parent.showPleaseWait();
		return true;
	} else {
		return false;
	}
}

function generateExcel() {

	var flag = validateForm();
	if (flag) {
		var action = document.getElementById("action");
		action.value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_OrderTrackingGenerateExcel', '650', '600', 'yes');
		document.genericForm.target = '_OrderTrackingGenerateExcel';
		var a = window.setTimeout("document.genericForm.submit();", 500);
	}
}

function myResultOnload() {
	displaySearchDuration();
	setResultFrameSize();
}

function validateForm() {
	return true;
}

function findMsds(id, shipmentId, prNumber, lineItem) {
	if (shipmentId.length > 0)
		openWinGeneric('/tcmIS/usgov/viewmsds.do?shipmentId=' + shipmentId + '&tcn=' + id + '&prNumber=' + prNumber + '&lineItem=' + lineItem, '_findmsds', '650', '600', 'yes');
}

function showNist(nistUrl) {
	if (nistUrl.length > 0)
		openWinGeneric('' + nistUrl + '', '_showNist', '800', '600', 'yes');
}

function viewMsds(id) {
	openWinGeneric(id, '_view', '800', '550', 'yes');
}

function getShipToAddress(locationId, dodaac) {
	openWinGeneric("/tcmIS/usgov/viewaddress.do?locationId=" + locationId + "&ultimateDodaac=" + dodaac, '_viewaddress', '500', '300', 'yes');
}

function onRightclick(rowId, cellId) {
	var ritems = new Array();

	var shipViaLocationId = cellValue(rowId, "shipViaLocationId");
	if (!isEmpty(shipViaLocationId))
		ritems[ritems.length] = 'text=' + messagesData.viewShipToDodaac + ';url=javascript:getShipToAddress(\'' + shipViaLocationId + '\',\'' + cellValue(rowId, "shipToDodaac") + '\');';

	var shipToLocationId = cellValue(rowId, "shipToLocationId");
	if (!isEmpty(shipToLocationId))
		ritems[ritems.length] = 'text=' + messagesData.viewUltimateDodaac + ';url=javascript:getShipToAddress(\'' + shipToLocationId + '\',\'' + cellValue(rowId, "markForDodaac") + '\');';

	var transportationControlNo = cellValue(rowId, "transportationControlNo");
	if (!isEmpty(transportationControlNo))
		ritems[ritems.length] = 'text=' + messagesData.viewMsds + ';url=javascript:findMsds(\'' + transportationControlNo + '\',\'' + cellValue(rowId, "shipmentId") + '\',\'' + cellValue(rowId, "prNumber") + '\',\'' + cellValue(rowId, "lineItem") + '\');';

	var mfgLiteratureContent = cellValue(rowId, "mfgLiteratureContent");
	if (!isEmpty(mfgLiteratureContent))
		ritems[ritems.length] = 'text=' + messagesData.viewNist + ';url=javascript:showNist(' + mfgLiteratureContent + ');';

	var carrier = cellValue(rowId, "carrier");
	if (!isEmpty(carrier)) {
		carrierNo = cellValue(rowId, "trackingNo");
		carrierString = 'text=' + messagesData.viewTrackingInformation + ' ' + carrierNo + ';url=javascript:openURL("';
		if (carrier == 'UPS Ground' || carrier == 'UPS' || carrier.indexOf('UPS') !== -1)
			carrierString += "http://wwwapps.ups.com/WebTracking/processInputRequest?HTMLVersion=5.0&loc=en_US&Requester=UPSHome&AgreeToTermsAndConditions=yes&ignore=&track.x=32&track.y=6&tracknum=" + encodeURI(carrierNo) + '");';
		else if (carrier == 'Old Dominion')
			carrierString += "http://www.odfl.com/trace/Trace.jsp?action=Status&Type=P&pronum=" + encodeURI(carrierNo) + '");';
		else if (carrier == 'Fedex' || carrier.indexOf('Fedex') !== -1)
			carrierString += "http://www.fedex.com/Tracking?ascend_header=1&clienttype=dotcom&cntry_code=us&language=english&tracknumbers=" + encodeURI(carrierNo) + '");';
		else if (carrier == 'ABF')
			carrierString += "https://www.abfs.com/tools/trace/default.asp?hidSubmitted=Y&hidNotifyBy=S&DefaultView=S&reftype0=a&refno0=" + encodeURI(carrierNo) + '");';
		else if (carrier == 'Southeast')
			carrierString += "https://www.sefl.com/seflWebsite/servlet?GUID=&externalMenu=true&action=Tracing_Trace_by_pro&Type=PN&RefNum=" + encodeURI(carrierNo) + '");';
		else
			carrierString = 'text=' + messagesData.noTrackingLinkAvailable + ';url=javascript:doNothing();';
	} else
		carrierString = 'text=' + messagesData.noTrackingLinkAvailable + ';url=javascript:doNothing();';

	if (!isEmpty(cellValue(rowId, "prNumber")))
		ritems[ritems.length] = 'text=' + messagesData.orderDocs + ';url=javascript:showDocs(' + rowId + ');';

	ritems[ritems.length] = carrierString;

	replaceMenu('rcMenu', ritems);
	toggleContextMenu('rcMenu');
}

function openURL(url) {
	openWinGeneric(url, "_viewTrackingInformation", "800", "550", "yes", "100", "100");
}

function replaceMenu(menuname, menus) {
	var i = mm_returnMenuItemCount(menuname);

	for (; i > 1; i--)
		mm_deleteItem(menuname, i);

	for (i = 0; i < menus.length;) {
		var str = menus[i];

		i++;
		mm_insertItem(menuname, i, str);
		// delete original first item.
		if (i == 1)
			mm_deleteItem(menuname, 1);
	}
}

function showDocs(rowid) {
	var prNumber = cellValue(rowid, "prNumber");
	var poNumber = cellValue(rowid, "contractNumber");

	if (!isEmpty(prNumber)) {
		var loc = "/tcmIS/distribution/showmrdocuments.do?showDocuments=Yes&prNumber=" + prNumber + "&custPoNumber=" + poNumber + "&launchedFrom=supplier";
		try {
			parent.children[parent.children.length] = openWinGeneric(loc, "showAllMrDocuments", "450", "300", "no", "80", "80");
		} catch (ex) {
			openWinGeneric(loc, "showAllMrDocuments", "600", "300", "yes", "80", "80");
		}
	}
}

function isEmpty(s) {
	if (s == null || s.length < 1)
		return true;
	else
		return false;
}
