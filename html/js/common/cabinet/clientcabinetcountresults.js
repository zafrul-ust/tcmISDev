var beangrid;
var windowCloseOnEsc = true;

var resizeGridWithWindow = true;

var multiplePermissions = true;
var permissionColumns = new Array();
permissionColumns={"countQuantity":true,"kanbanBinScanQty":true};

var selectedRowId;


/* The reason for this is to show the error messages from the main page */
function showErrorMessages() {
	parent.showErrorMessages();
}

function myResultBodyOnload() {

	totalLines = document.getElementById("totalLines").value;

	if (totalLines > 0) {
		document.getElementById("cabinetBinItemBean").style["display"] = "";

		initializeGrid();

		if (updateSuccess) {
			alert(messagesData.updateSuccess);
		}

		if (showUpdateLinks){
			parent.document.getElementById("updateResultLink").style["display"] = "";
		}
		else {
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		}
	}
	else {

		document.getElementById("cabinetBinItemBean").style["display"] = "none";
	}

	/* This dislpays our standard footer message */
	displayGridSearchDuration();

	/*
	 * It is important to call this after all the divs have been turned on
	 * or off.
	 */
	setResultFrameSize();
}

function initializeGrid() {

    beangrid = new dhtmlXGridObject('cabinetBinItemBean');
	initGridWithConfig(beangrid, config, true, true, true);

    beangrid._haas_bg_render_enabled = true;
	beangrid.enableSmartRendering(true);
	beangrid._haas_row_span = true;
	beangrid._haas_row_span_map = lineMap;
	beangrid._haas_row_span_class_map = lineMap3;
	beangrid._haas_row_span_cols = [0,1,2,3,4,5,6,7,8,9,10,11,12,16];

	if (lineMap2) {
		beangrid._haas_row_span_lvl2 = true;
		beangrid._haas_row_span_lvl2_map = lineMap2;
		beangrid._haas_row_span_lvl2_cols = [13,14,15];
	}

    if (typeof (jsonMainData) != 'undefined') {

		beangrid.parse(jsonMainData, "json");

	}

    beangrid.attachEvent("onRowSelect", selectRow);
    beangrid.attachEvent("onRightClick", selectRightClick);
}

function submitMainUpdate() {
	var flag = validateForm();
	if (flag) {
		/* Set any variables you want to send to the server */
		var action = $("uAction");
		action.value = 'update';
		parent.showPleaseWait();

		if (beangrid != null) {
			beangrid.parentFormOnSubmit();
		}
        parent.$("startSearchTime").value = new Date().getTime();
        /* Submit the form in the result frame */
		document.clientCabinetCountForm.submit();
	}
}

function validateForm() {
	for ( var i = 0; i < beangrid.getRowsNum(); i++) {
		rowid = beangrid.getRowId(i);
		if (cellValue(rowid, "permission") != 'Y') continue;
		var pri = cellValue(rowid, "countQuantity");
		if (pri != null && pri != '&nbsp;' && pri != '' && !isInteger(pri, false)) {
			alert(messagesData.validvalues + messagesData.quantity);
			return false;
		}
        //automatic Refill then
        if (cellValue(rowid, "countType") == 'AutomaticRefill') {
            //check to make sure that if user enter qty or count date than they have to have both
            var cdt = cellValue(rowid, "countDatetime");
            if ((pri != null && pri != '&nbsp;' && pri != '') && (cdt == null || cdt == '')) {
                alert(messagesData.validCountDateForPartNo.replace('{0}',cellValue(rowid, "partNo")));
                return false;
            }
            if ((pri == null || pri == '&nbsp;' || pri == '') && (cdt != null && cdt != '')) {
                alert(messagesData.validQtyOnHandForPartNo.replace('{0}',cellValue(rowid, "partNo")));
                return false;
            }
            //check to make sure that count date is > than prior count date
            var tmpDate = cellValue(rowid, "priorCountDatetime");
            var countDateValue = Date.parse(cdt.replace("-", " "));
            var priorCountDateValue = Date.parse(tmpDate.replace("-"," "));
            //if the bin never been counted then count date can't be before cabinet start date
            if (cellValue(rowid, "startupCountNeeded") == 'Y') {
                tmpDate = cellValue(rowid, "cabinetStartDate");
                priorCountDateValue = Date.parse(tmpDate.replace("-"," "));
            }

            if (countDateValue.valueOf() <= priorCountDateValue.valueOf()) {
                alert(messagesData.countDateLater.replace('{0}',tmpDate).replace('{1}',cellValue(rowid, "partNo")));
                return false;
            }
        }
    }
    return true;
}

function selectRow() {
    var colId0 = arguments[1];
    selectedRowId = arguments[0];
    
    if (colId0 == beangrid.getColIndexById("receiptQtyDisplay")) {
    	var countType = "" + cellValue(selectedRowId,"countType");
    	if (countType.startsWith('RECEIPT') || 'ADVRECEIPT' == countType) { 
    		openReceiptCount();
    	}
    }
}

function selectRightClick(rowId, cellInd){
	selectedRowId = rowId;
	var countType = "" + cellValue(rowId,"countType");
	
	if(countType.startsWith('RECEIPT') || 'ADVRECEIPT' == countType){
		toggleContextMenu("receiptCountMenu");
	}
	else if('LEVEL' == countType){
		toggleContextMenu("levelCountMenu");
	}
	else {
		toggleContextMenu("contextMenu");
	}
}

function openReceiptCount(){
	var cabinetId = cellValue(beangrid.getSelectedRowId(),"cabinetId");
    var binId = cellValue(beangrid.getSelectedRowId(),"binId");
    var itemId = cellValue(beangrid.getSelectedRowId(),"itemId");
	var receiptId = cellValue(beangrid.getSelectedRowId(),"receiptId");
	var receiptQty = cellValue(beangrid.getSelectedRowId(),"receiptQty");	
	var damaged = cellValue(beangrid.getSelectedRowId(),"receiptDamagedQty");	
	var expired = cellValue(beangrid.getSelectedRowId(),"receiptExpiredQty");	
	var countType = "" + cellValue(beangrid.getSelectedRowId(),"countType");

	var receiptCountUrl = "workareareceiptcount.do?action=loadReceiptCount&cabinetId="+cabinetId+"&binId="+binId+"&itemId="+itemId+"&countType="+countType;
	
	if (receiptId != null && receiptId.length > 0) {
		receiptCountUrl += "&receiptId="+receiptId+"&receiptQty="+receiptQty;
		if ("ADVRECEIPT" == countType) {
			receiptCountUrl += "&receiptDamagedQty="+damaged+"&receiptExpiredQty="+expired;
		}
	}
	if ("ADVRECEIPT" == countType) {
		openWinGeneric(receiptCountUrl, "receiptCount", 440, 500,"no");
	}
	else {
		openWinGeneric(receiptCountUrl, "receiptCount", 380, 500,"no");
	}
	parent.showTransitWin();
}

function setReceiptCount(receiptId, receiptQty, damagedQty, expiredQty) {	
	var countType = "" + cellValue(beangrid.getSelectedRowId(),"countType");
	var totalReceiptQty = 0;
	var totalNewQty = 0;
	var expired = 0;

	if(receiptQty.length > 0){
		var quantities = receiptQty.split("|");	
		for (var i = 0; i < quantities.length; i++){
			if (quantities[i].length > 0) {
				totalReceiptQty += parseInt(quantities[i]);
				totalNewQty += parseInt(quantities[i]);
			}
		}
		if ("ADVRECEIPT" == countType) {
			var dmgQuantities = damagedQty.split("|");			
			for (var i = 0; i < dmgQuantities.length; i++){
				if (dmgQuantities[i].length > 0) {
					totalReceiptQty += parseInt(dmgQuantities[i]);
				}
			}	
			var expQuantities = expiredQty.split("|");			
			for (var i = 0; i < dmgQuantities.length; i++){
				if (expQuantities[i].length > 0) {
					totalReceiptQty += parseInt(expQuantities[i]);
					expired += parseInt(expQuantities[i]);
				}
			}
		}
		beangrid.cellById(selectedRowId, beangrid.getColIndexById("receiptQtyDisplay")).setValue(totalReceiptQty);
	}
	else
		beangrid.cellById(selectedRowId, beangrid.getColIndexById("receiptQtyDisplay")).setValue('');
	
	beangrid.cellById(selectedRowId, beangrid.getColIndexById("receiptId")).setValue(receiptId);
	beangrid.cellById(selectedRowId, beangrid.getColIndexById("receiptQty")).setValue(receiptQty);
	if ("ADVRECEIPT" == countType) {
		beangrid.cellById(selectedRowId, beangrid.getColIndexById("receiptDamagedQty")).setValue(damagedQty);
		beangrid.cellById(selectedRowId, beangrid.getColIndexById("receiptExpiredQty")).setValue(expiredQty);
		if (expired > 0) {
			beangrid.cellById(selectedRowId, beangrid.getColIndexById("countQuantity")).setValue(0);			
		}
		else {
			beangrid.cellById(selectedRowId, beangrid.getColIndexById("countQuantity")).setValue(totalNewQty);
		}
	}	
}

function updateLevelCount() {
	parent.showTransitWin();	
	var partDesc = cellValue(beangrid.getSelectedRowId(),"description");
	partDesc = partDesc.replace(/&amp;/gi, "%26");
	partDesc = partDesc.replace(/&/gi, "%26");
	partDesc = partDesc.replace(/#/gi, "%23");
	
	var loc = "clientcabinettanklevelcount.do?uAction=showform";
	loc += "&companyId="+cellValue(beangrid.getSelectedRowId(),"companyId");
	loc += "&rowId="+selectedRowId;
	loc += "&binId="+cellValue(beangrid.getSelectedRowId(),"binId");
	loc += "&receiptId="+cellValue(beangrid.getSelectedRowId(),"receiptId");
	loc += "&countType="+cellValue(beangrid.getSelectedRowId(),"countType");	
	loc += "&workAreaDesc="+cellValue(beangrid.getSelectedRowId(),"useApplication");	 //work area description
	loc += "&partNumber="+cellValue(beangrid.getSelectedRowId(),"partNo");	//part number
	loc += "&partDesc="+encodeURIComponent(partDesc);	//part description
	
	openWinGeneric(loc,"TankLevelCount","860","675","yes","50","50","20","20","no");	
}

function tankLevelCountCallback(completed, errorEncountered, qtyOnHand) {
	//alert(completed + " and " + errorEncountered  + " and " + qtyOnHand);
	if (completed) {
		beangrid.cellById(selectedRowId, beangrid.getColIndexById("countQuantity")).setValue(qtyOnHand);
	} else {
		beangrid.cellById(selectedRowId, beangrid.getColIndexById("countQuantity")).setValue('');
	}	
}

