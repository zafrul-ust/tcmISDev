var beangrid;
var windowCloseOnEsc = true;

var resizeGridWithWindow = true;


/* The reason for this is to show the error messages from the main page */
function showErrorMessages() {
	parent.showErrorMessages();
}

function resultOnLoad() {
	if (updateSuccess) {
		alert(messagesData.updatesucess);
	}
	
	totalLines = document.getElementById("totalLines").value;

	if (totalLines > 0) {
		document.getElementById("cabinetPutAwayBean").style["display"] = "";

		initializeGrid();

		if (showUpdateLinks){
			parent.document.getElementById("updateResultLink").style["display"] = "";
		}
		else {
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		}
	}
	else {
		document.getElementById("cabinetPutAwayBean").style["display"] = "none";
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
	beangrid = new dhtmlXGridObject('cabinetPutAwayBean');
	initGridWithConfig(beangrid, config, true, true, true);

	if (typeof (jsonMainData) != 'undefined') {
		beangrid.parse(jsonMainData, "json");
	}
}

function auditSubmitData() {
    var result = false;
    var rowsNum = beangrid.getRowsNum(); // Get the total rows of the grid
	for ( var rowId = 1; rowId <= rowsNum; rowId++) {
    	if (cellValue(rowId, "okDoUpdate") == "true") {
            result = true;
            break;
        }
    }
    return result;
}

function submitUpdate() {
	if (auditSubmitData()) {
        var action = $("uAction");
        action.value = 'update';
        parent.showPleaseWait();
        parent.$("startSearchTime").value = new Date().getTime();

        if (beangrid != null) {
            beangrid.parentFormOnSubmit();
        }
        /* Submit the form in the result frame */
        document.clientCabinetPutAwayForm.submit();
    }else {
        alert(messagesData.noRowSelected);    
    }
}

//to perform the check all function in the header.
function checkAll(checkboxname) {

	var checkall = $("chkAllOkDoUpdate");
	var rowsNum = beangrid.getRowsNum(); // Get the total rows of the grid
	var columnId = beangrid.getColIndexById(checkboxname);

	if (checkall.checked) {
		for ( var rowId = 1; rowId <= rowsNum; rowId++) {
			if(cellValue(rowId, "permission") == 'Y') {
				var curCheckBox = checkboxname + rowId;
				if ($(curCheckBox)) { // Make sure the row has been rendered and the element exists
					$(curCheckBox).checked = true;
					updateHchStatusA(curCheckBox);
				}
				else { // The HTML element hasn't been drawn yet, update the JSON data directly
					beangrid._haas_json_data.rows[beangrid.getRowIndex(rowId)].data[columnId] = true;
				}
			}
		}
	}
	else {
		for ( var rowId = 1; rowId <= rowsNum; rowId++) {
			if(cellValue(rowId, "permission") == 'Y') {
				var curCheckBox = checkboxname + rowId;
				if ($(curCheckBox)) {  // Make sure the row has been rendered and the element exists
					$(curCheckBox).checked = false;
					updateHchStatusA(curCheckBox);
				}
				else { // The HTML element hasn't been drawn yet, update the JSON data directly
					beangrid._haas_json_data.rows[beangrid.getRowIndex(rowId)].data[columnId] = false;
				}
			}
		}
	}
	return true;
	
}

function printPackingList() {
	var shipmentIds='';
	var applicationIds='';
	var shipmentApplicationIds='';
	var prevShipmentId='';

	var rowsNum = beangrid.getRowsNum(); // Get the total rows of the grid
	for ( var rowId = 1; rowId <= rowsNum; rowId++) {

			if (cellValue(rowId, "okDoUpdate") == "true") { 
				if(shipmentIds.length > 0)
					shipmentIds += ',';
				shipmentIds +=  cellValue(rowId, "shipmentId");
				
				if(cellValue(rowId, "shipmentId") != prevShipmentId ){
					// add the previous shipment id and selected work areas to param string
					if(shipmentApplicationIds.length > 0)
						shipmentApplicationIds += '|';
					if(prevShipmentId != '')
						shipmentApplicationIds += prevShipmentId + '-' + applicationIds;
					
					// set new shipment id and clear work area string
					prevShipmentId =  cellValue(rowId, "shipmentId");
					applicationIds = '';
				}				
				
				if(applicationIds.length > 0)
					applicationIds += ',';
				applicationIds +=  cellValue(rowId, "applicationId");				
			}
	}
	
	// add selected work areas for the last selected shipment id
	if (applicationIds.length > 0){
		if(shipmentApplicationIds.length > 0)
			shipmentApplicationIds += '|';
		shipmentApplicationIds +=prevShipmentId + '-' + applicationIds;
	}

    if (shipmentApplicationIds.length == 0) 
        alert(messagesData.noRowSelected);
    else
        openWinGeneric('/tcmIS/hub/clientcabinetputawaymain.do?uAction=print&putAwayMethod='+$v('putAwayMethod')+'&shipmentIds='+shipmentIds+'&shipmentApplicationIds='+shipmentApplicationIds,'_PrintPL','650','500','yes');
}

/*function printPackingList() {
	var shipmentIds='';
	var applicationIds='';
	var shipmentApplicationIds='';
	var prevShipmentId='';

	var rowsNum = beangrid.getRowsNum(); // Get the total rows of the grid
	for ( var rowId = 1; rowId <= rowsNum; rowId++) {

			if (cellValue(rowId, "okDoUpdate") == "true") { 
				if(shipmentIds.length > 0)
					shipmentIds += ',';
				shipmentIds +=  cellValue(rowId, "shipmentId");
				
				if(shipmentApplicationIds.length > 0)
					shipmentApplicationIds += '|';
				
				shipmentApplicationIds += cellValue(rowId, "shipmentId") + '-' + cellValue(rowId, "applicationId") + '-' + cellValue(rowId, "putAwayMethod");
			}
	}
	
    if (shipmentApplicationIds.length == 0) 
        alert(messagesData.noRowSelected);
    else
        openWinGeneric('/tcmIS/hub/clientcabinetputawaymain.do?uAction=print&shipmentIds='+shipmentIds+'&shipmentApplicationIds='+shipmentApplicationIds,'_PrintPL','650','500','yes');
}
*/