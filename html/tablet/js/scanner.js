// lazy test for scanner and camera hardware

function enableScanner(callbackFunction, message, popupDiv, buttonText) {
	debug("enableScanner, " + callbackFunction + ", " + message + ", " + popupDiv);
	if (isMotorola()) {
		// turn off soft keyboard
		sip.manual();
		scanner.decodeEvent = callbackFunction;
		scanner.enabled = "scn2";		
		altPopup(message, popupDiv, function() {			
			debug("close alt popup");
			//altPopupHide(popupDiv);
			disableScanner();
		}, buttonText);
	}
}

function disableScanner() {
	debug("disableScanner");
	if (isMotorola()) {
		debug("scanner off");
		scanner.disable();
	}
}

function onTrackingNumberScan(jsonScanData) {
	debug("onTrackingNumberScan");
	disableScanner();
	var barcode = jsonScanData.data;
	$('#trkNum').val(barcode);
	// $('#rcvDate').val('3/1/2013');
	altPopupHide("#trkNumPage_popup");
	$("#carrierInput").focus();
	newInboundShipment(barcode);
}

function onPoNumberScan(jsonScanData) {
	debug("onPoNumberScan");
	var barcode = jsonScanData.data;
	$('#poNum').val(barcode);
	scanner.disable();
	altPopupHide("#trkNumPage_popup");
	$("#poNum").trigger("change");
}

function onReceiptIdScan(jsonScanData) {
	debug("onReceiptIdScan");
	if (isMotorola()) {
		disableScanner();

		var barcode = jsonScanData.data;
		$("#receiptIdInput").val(barcode.replace("RCPT", ""));
		debug("calling altPopupDestroy");
		altPopupHide("#receiptFormPage_popup");
		verifyReceiptIdIsNew(barcode);
	}
}

function onOriginalReceiptIdScan(jsonScanData) {
	debug("onReceiptIdScan");
	if (isMotorola()) {
		disableScanner();
		getOriginalReceiptData(jsonScanData.data);
		altPopupHide("#receiptFormPage_popup");
	}
}

function onBinIdScan(jsonScanData) {
	debug("onBinNumberScan");
	disableScanner();
	var barcode = jsonScanData.data;
	// remove * from the start of the string
	if (barcode.charAt(0) === "*") {
		barcode = barcode.substr(1);
	}
	$('#binInput').val(barcode.replace("BIN", ""));
	altPopupHide("#receiptFormPage_popup");
	$("#binInput").trigger("change");
}

function enableDocNumScan() {
	debug("enableDocNumScan");
	if (isMotorola()) {
		sip.manual();
		scanner.decodeEvent = 'onDocumentNumberScan(%json)';
		scanner.enabled = "SCN2";
	}
}

function onDocumentNumberScan(jsonScanData) {
	debug("onDocumentNumberScan " + jsonScanData.data);
	if (isMotorola()) {
		disableScanner();
		if (testDocumentNumber(jsonScanData.data)) {
			var newli = "<li data-icon='delete' class='newRDOC'><a href='#'>";
			newli += $.trim(jsonScanData.data) + "</a></li>";
			debug("creat doc line " + newli);
			$("#documentList").append(newli);
			$("#documentList").listview("refresh");
			$("#documentList li.newRDOC").on("click", function(e) {
				$(e.currentTarget).remove();
			});
		}
		altPopupHide("#documentsPage_popup");
		enableScanner('onDocumentNumberScan(%json)', 'Scan Document Number', 'documentsPage', 'Done');
	}
}

function testDocumentNumber(docNum) {
	var docNotDupe = true;
	debug("testDocumentNumber");
	var rdocTest = new RegExp("RDOC");
	if (!rdocTest.test(docNum)) {
		alert(docNum + " is not a valid document ID.");
		return false;
	}
	debug("docNum = " + docNum);
	$("#documentList li").each(function(index, li) {
		debug("li.text = " + $(li).text());
		if ($.trim(docNum) == $.trim($(li).text())) {
			debug("duplicate docNum");
			alert(docNum + " is already in the document list.");
			docNotDupe = false;
		}
	});
	return docNotDupe;
}

function testScanData(scanData, testRegex) {
	var scanDataTest = new RegExp(testRegex);
	if (!scanDataTest.test(scanData)) {
		debug("testScanData fail" + scanData + " " + testRegex);
		return false;
	}
	debug("testScanData pass" + scanData + " " + testRegex);
	return true;
}

function enableTrkNumScan() {
	debug("enableTrkNumScan");
	if (isMotorola()) {
		sip.manual();
		altPopup("Scan TrkNum", "trkNumPage", "disableScanner();")
		scanner.decodeEvent = 'onTrackingNumberScan(%json)';
		scanner.enabled = 'SCN2';
	}
}

function trackingNumberFocus() {
	debug("trackingNumFocus");
	if (isMototola()) {
		scanner.disable();
		$("#trackingNumScanPopup").popup("close");
	}
}

function onLotNumberScan(jsonScanData, index) {
	debug("onLotNumberScan");
	var barcode = jsonScanData.data;
	$("#lotNumberInput" + index).val(barcode);
	scanner.disable();
	altPopupHide("#receiptFormPage_popup");
}

function onPutAwayBinScan(jsonScanData, component) {
	debug("onPutAwayBinScan, " + component);
	disableScanner();
	var barcode = jsonScanData.data.replace("BIN", "");
	// var BinRegex = new RegExp("BIN");
	$.when(putAwayBinValidate(barcode)).then(function(binValid) {
		// turn off bin prefix testing not all bins have th eproper prefix
		// if(BinRegex.test(barcode) && binValid){
		if (binValid) {
			$('#putAwayBinId' + component).val(barcode);
			$("#putAwayBinId" + component).trigger("change");
			altPopupHide("#putAwayPage_popup");
		}
		else {
			enableScanner('onPutAwayBinScan(%json, ' + component + ')', 'Scan Bin ID', 'putAwayPage');
		}
	});
}

function onPutAwayReceiptScan(jsonScanData) {
	debug("onPutAwayReceiptIdScan");
	disableScanner();
	var barcode = jsonScanData.data.replace("RCPT", "");
	// turn off RCPT testing so existing container labels can be used
	// var ReceiptRegex = new RegExp("RCPT");
	// if(ReceiptRegex.test(barcode) && putAwayReceiptIdValidate(barcode.replace("RCPT",""))){
	if (putAwayReceiptIdValidate(barcode)) {
		debug("Put Away Receipt ID scan accepted");
		$("#putAwayReceiptId").val(barcode);
		debug("calling altPopupDestroy");
		altPopupHide("#putAwayPage_popup");
	}
	else {
		enableScanner('onPutAwayReceiptScan(%json)', 'Scan Receipt ID', 'putAwayPage');
	}
}

function onBinnableScan(jsonScanData) {
	debug("onBinnableScan " + jsonScanData.data);
	disableScanner();
	var barcode = jsonScanData.data.replace("RCPT", "");
	var rIndex = findReceiptId(barcode);
	debug("findReceiptId = " + rIndex);
	if (rIndex > -1) {
		debug("calling altPopupDestroy");
		altPopupHide("#putAwayPage_popup");
		showPutAwayPage(rIndex);
		$("#putAwayReceiptId").val(barcode);
	}
	else {
		enableScanner("onBinnableScan(%json)", 'Scan Receipt Id', 'binningPage');
	}
}

function reBinReceiptScanner(jsonScanData){
	debug("reBinReceiptScanner" + jsonScanData.data);	
    disableScanner();
    var barcode = jsonScanData.data.replace("RCPT","").substr(0,8);
    $.when(loadReceiptIdData(barcode)).then(function(isValid){
		if(isValid){
			debug("Re Bin Receipt ID scan accepted");
			$("#reBinReceiptId").val(barcode);
			debug("calling altPopupDestroy");
			altPopupHide("#reBinReceiptPage_popup");
		}
		else {
			enableScanner('reBinReceiptScanner(%json)','Scan Receipt ID','reBinReceiptPage');
		}
    });
}
  

function onReBinBinScan(jsonScanData, component){    
	debug("onReBinBinScan, " + component);
    disableScanner();        
    var barcode = jsonScanData.data.replace("BIN","");
    // remove * from the start of the string
	if (barcode.charAt(0) === "*") {
		barcode = barcode.substr(1);
	}
    $.when(validateBin(barcode))
        .then(function(binValid){
            if(binValid){
                $('#reBinBinId' + component).val(barcode);
                $('#reBinBinId' + component).trigger("change");
                altPopupHide("#reBinPage_popup");
            }
            else{
                enableScanner('onReBinBinScan(%json, ' + component + ')', 'Scan Bin ID', 'reBinPage');
            }
        });
}
 
