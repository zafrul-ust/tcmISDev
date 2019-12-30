var dhxWins = null;
var resizeGridWithWindow = true;
var beangrid;
windowCloseOnEsc = true;

function resultOnLoad() {
	var totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		// make result area visible if data exist
		document.getElementById("cabinetInventoryCountBean").style["display"] = "";
		// build the grid for display
		doInitGrid();
	}
	else {
		document.getElementById("cabinetInventoryCountBean").style["display"] = "none";
	}
	
	if (showUpdateLinks){
		parent.document.getElementById("mainUpdateLinks").style["display"] = "";
	}
	else {
		parent.document.getElementById("mainUpdateLinks").style["display"] = "none";
	}
	
	/*It is important to call this after all the divs have been turned on or off.*/
 	//setResultFrameSize();
 	
	if($v('action') == 'setReceiptCount') { 
	    if(showErrorMessage)
	    	$("errorMessagesArea").style.display="";
	    else{
	    	// update line in parent window
	    	var rowsNum = beangrid.getRowsNum(); // Get the total rows of the grid
	    	var receiptId = '';
	    	var countQuantity = '';
	    	var damagedQuantity = '';
	    	var expiredQuantity = '';
	    	
	    	for ( var rowId = 1; rowId <= rowsNum; rowId++) {
	    		if (cellValue(rowId,"countQuantity").length > 0 && parseInt(cellValue(rowId,"receiptId")) > 0){
	    			if(receiptId.length > 0){
	    				receiptId += '|';
	    				countQuantity += '|';
	    				damagedQuantity += '|';
	    				expiredQuantity += '|';
	    			}
	    			
	    			receiptId += cellValue(rowId, "receiptId");
	    			countQuantity += cellValue(rowId, "countQuantity") || 0;
	    			if(advReceipt) {
	    				damagedQuantity += cellValue(rowId, "receiptDamagedQty") || 0;
	    				expiredQuantity += cellValue(rowId, "receiptExpiredQty") || 0;
	    			}
	    		}
	    	}
	    	
	    	opener.setReceiptCount(receiptId, countQuantity, damagedQuantity, expiredQuantity);
	    	window.close();
	    }
	}

}

function doInitGrid() {
	beangrid = new dhtmlXGridObject('cabinetInventoryCountBean');

	initGridWithConfig(beangrid, config, false, true, false);
	if (typeof (jsonMainData) != 'undefined') {
		beangrid.parse(jsonMainData, "json");
	}
}

function validateForm() {
	var message = "";
	
	for ( var rowId = 1; rowId <= beangrid.getRowsNum(); rowId++) {
		if(cellValue(rowId,"countQuantity").length > 0){
			if (!isInteger(cellValue(rowId, "receiptId"))  || parseInt(cellValue(rowId,"receiptId")) < 0) {
				alert(messagesData.receipt + " " + messagesData.positiveInteger);
				return false;
			}
			if (!isInteger(cellValue(rowId, "countQuantity")) || parseInt(cellValue(rowId,"countQuantity")) < 0) {
				alert(messagesData.qty + " " + messagesData.positiveInteger);
				return false;
			}
		}
		if(advReceipt) {
			if(cellValue(rowId,"receiptDamagedQty").length > 0){
				if (!isInteger(cellValue(rowId, "receiptDamagedQty")) || parseInt(cellValue(rowId,"receiptDamagedQty")) < 0) {
					alert(messagesData.damaged + " " + messagesData.positiveInteger);
					return false;
				}
			}
			if(cellValue(rowId,"receiptExpiredQty").length > 0){
				if (!isInteger(cellValue(rowId, "receiptExpiredQty")) || parseInt(cellValue(rowId,"receiptExpiredQty")) < 0) {
					alert(messagesData.expired + " " + messagesData.positiveInteger);
					return false;
				}
				var expired = parseInt(cellValue(rowId,"receiptExpiredQty")); 
//				if (expired != 0 &&
//						(cellValue(rowId,"receiptDamagedQty").length > 0 && parseInt(cellValue(rowId,"receiptDamagedQty")) > 0) ||
//						(cellValue(rowId,"countQuantity").length > 0 && parseInt(cellValue(rowId,"countQuantity")) > 0)) {
				if (expired > 0) {
					if (cellValue(rowId,"receiptDamagedQty").length > 0 && parseInt(cellValue(rowId,"receiptDamagedQty")) > 0) {
						alert(messagesData.expiredOnly);
						return false;
					}
					if (cellValue(rowId,"countQuantity").length > 0 && parseInt(cellValue(rowId,"countQuantity")) > 0) {
						alert(messagesData.expiredOnly);
						return false;
					}
				}
			}
		}

	}
	return true;
}

function submitUpdate() {
	if (validateForm()) {
		/* Set any variables you want to send to the server */
		var action = $("action");
		action.value = 'setReceiptCount';

		if (beangrid != null) {
			beangrid.parentFormOnSubmit();
		}
		document.genericForm.submit();
	}
	
}

function addLine() {
	var rowid = beangrid.getRowsNum()*1+1;
    var ind = beangrid.getRowsNum();		
	var thisrow = beangrid.addRow(rowid,"",ind);
	var count = 0;
	
	beangrid.cells(rowid, count++).setValue('Y');
	beangrid.cells(rowid, count++).setValue('Y');
	beangrid.cells(rowid, count++).setValue('');
	beangrid.cells(rowid, count++).setValue('');
	if (advReceipt) {
		beangrid.cells(rowid, count++).setValue('');
		beangrid.cells(rowid, count++).setValue('');
	}
	beangrid.cells(rowid, count++).setValue('Y');
}

function emptyBin() {
	var countExists = false;
	
	for ( var rowId = 1; rowId <= beangrid.getRowsNum(); rowId++) {
		if (isInteger(cellValue(rowId, "receiptId"))  && parseInt(cellValue(rowId,"receiptId")) > 0
				&& ((isInteger(cellValue(rowId, "countQuantity")) &&  parseInt(cellValue(rowId,"countQuantity")) > 0) ||
				(advReceipt && isInteger(cellValue(rowId, "receiptDamagedQty")) &&  parseInt(cellValue(rowId,"receiptDamagedQty")) > 0) ||
				(advReceipt && isInteger(cellValue(rowId, "receiptExpiredQty")) &&  parseInt(cellValue(rowId,"receiptExpiredQty")) > 0))) {
			countExists = true;
			break;
		}
	}
	
	if (countExists) {
		if(confirm(messagesData.verifyemptybin)){
			opener.setReceiptCount('0', '0', '0', '0');
	    	window.close();
		}
	}	
	else{
		opener.setReceiptCount('0', '0', '0', '0');
    	window.close();
	}
}
