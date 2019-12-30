var beangrid;
var windowCloseOnEsc = true;

var resizeGridWithWindow = true;

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
	initGridWithConfig(beangrid, config, false, true, true);

    beangrid._haas_bg_render_enabled = true;
	beangrid.enableSmartRendering(true);

    if (typeof (jsonMainData) != 'undefined') {

		beangrid.parse(jsonMainData, "json");

	}
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
	
	var oneRowChecked = false;
	for ( var i = 0; i < beangrid.getRowsNum(); i++) {
		rowid = beangrid.getRowId(i);
		if (cellValue(rowid, "permission") != 'Y') continue;
		else if ($('okDoUpdate' + rowid).checked)
			oneRowChecked = true;
    }
	
	if(!oneRowChecked)
	{
		alert(messagesData.pleaseSelect);
		return false;
	}
	
    return true;
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




