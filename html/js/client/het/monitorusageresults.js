var mygrid;

// Global variable: specially useful for right-click
var selectedRowId = null;

// Set this to be false if you don't want the grid width to resize based on
// window size.
var resizeGridWithWindow = true;

/*
 * This is really the same as before. Except now there is a call to initialize the grid.
 */
function resultOnLoad() {
	try {
		if (!showUpdateLinks) /*
								 * Dont show any update links if the user does not have permissions
								 */
			parent.document.getElementById("resultLink").style["display"] = "none";
		else
			parent.document.getElementById("resultLink").style["display"] = "";
	}
	catch (ex) {
	}

	var totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		// make result area visible if data exist
		document.getElementById("HetUsageBean").style["display"] = "";
		// build the grid for display
		doInitGrid(false);
	}
	else {
		document.getElementById("HetUsageBean").style["display"] = "none";
	}

	/* This displays our standard footer message */
	displayGridSearchDuration();

	/*
	 * It is important to call this after all the divs have been turned on or off.
	 */
	setResultFrameSize();
}

function resultOnLoadForHistory() {
	try {
		if (!showUpdateLinks) /*
								 * Dont show any update links if the user does not have permissions
								 */
			parent.document.getElementById("resultLink").style["display"] = "none";
		else
			parent.document.getElementById("resultLink").style["display"] = "";
	}
	catch (ex) {
	}

	var totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		// make result area visible if data exist
		document.getElementById("HetUsageBean").style["display"] = "";
		// build the grid for display
		doInitGrid(true);
	}
	else {
		document.getElementById("HetUsageBean").style["display"] = "none";
	}

	/*
	 * It is important to call this after all the divs have been turned on or off.
	 */
	setResultFrameSize();
}

function doInitGrid(history) {
	mygrid = new dhtmlXGridObject('HetUsageBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	// if rowSpan == true, sorting and smart rendering won't work; if
	// rowSpan == false, sorting and smart rendering will work
	// rowSpan == -1 is recommended for the page with update function
	// -1 is for disable rowSpan and smart rendering, but sorting still
	// works; false will disable rowSpan and sorting but smart rendering is
	// enabled
	// set submitDefault to true: Send the data to the server
	// singleClickEdit: this is for type:"txt",
	initGridWithConfig(mygrid, config, -1, true, true);
	_setPermColumn("modified", "modifiedPermission");
	if (!history) {
		mygrid.attachEvent("onRightClick", onRightClick);
		mygrid.attachEvent("onAfterHaasRenderRow", checkPulldownValues);
	}
	if (typeof (jsonMainData) != 'undefined') {
		mygrid.parse(jsonMainData, "json");
	}
}

function onRightClick(rowId, cellInd) {
	selectedRowId = rowId;
	var modified = gridCellValue(mygrid, rowId, "modified");

	if (modified.endsWith('true')) {
		toggleContextMenu('rightClickMenu');
	}
	else {
		toggleContextMenu('rightClickMenuNoHistory');
	}
}

function showHistory() {
	var url = "monitorusageresults.do?uAction=history" + "&usageId=" + gridCellValue(mygrid, selectedRowId, "usageId");

	itemWindow = openWinGeneric(url, "UsageLogChangeHistory", "1000", "400", "yes", "50", "50", "no");
	// parent.children[parent.children.length] = itemWindow;
}

// perform the input box onChange function here. Good for calculating total,
// margin, etc.

function callValidateLinefunction(rowId) {
	// Validate the line here
	if (cellValue(rowId, "ok") == "true")
		validateLine(rowId);
}

function update() {
	validationforUpdate();
}

function finishSubmit() {
	validations--;
	if (validations < 1 && !errorsExist) {
		document.genericForm.target = '';
		document.getElementById('uAction').value = 'update';

		parent.showPleaseWait();

		if (mygrid != null)
			mygrid.parentFormOnSubmit();

		document.genericForm.submit(); // back to server
	}
}

function invalidQuantity(rowId, containerId, quantity, remaining, uom) {
}

function invalidUOM(rowId, uom, containerId) {
}

function invalidContainer(rowId, containerId, itemId) {
}

function invalidEmptyContainer(rowId, containerId) {
}

function invalidEmployee(rowId) {
	mygrid.selectRowById(rowId, true);
	alert(messagesData.invalidEmployee);
}

function checkMaxUpdates(rowId) {
	if (cellValue(rowId, "ok") == "true") {
		var rowsNum = mygrid.getRowsNum();
		var updates = 0;
		for ( var p = 1; p <= rowsNum; p++) {
			if (cellValue(p, "ok") == "true") {
				updates++;
			}
			if (updates > 1) {
				cell(rowId, "ok").setValue(false);
				$("ok" + rowId).checked = false;
				updateHchStatusA("ok" + rowId);

				alert(messagesData.updateoneonly);
				return false;
			}
		}
	}
}

function deleteRows() {
	var updates = 0;
	var rowsNum = mygrid.getRowsNum();

	for ( var p = 1; p <= rowsNum; p++) {
		if (cellValue(p, "ok") == "true") {
			updates++;
			break;
		}
	}

	if (updates == 0) {
		alert(messagesData.nothingSelected);
		return false;
	}
	document.genericForm.target = '';
	document.getElementById('uAction').value = 'delete';

	parent.showPleaseWait(); // Show "please wait" while
	// updating

	if (mygrid != null) {
		mygrid.parentFormOnSubmit();
	}

	document.genericForm.submit(); // back to server
}

var errorsExist = false;
var validations = 0;

// validate the whole grid
function validationforUpdate() {
	errorsExist = false;
	validations = 0;
	var updates = 0;
	var rowsNum = mygrid.getRowsNum();

	for ( var p = 1; p <= rowsNum; p++) {
		if (cellValue(p, "ok") == "true") {
			updates++;
			if (!validateLine(p)) {
				return false;
			}
		}
	}
	if (updates == 0) {
		alert(messagesData.nothingSelected);
		return false;
	}
	return true;
}

// validate one line here
function validateLine(rowId) {
	var quantity = cellValue(rowId, "quantity");
	if (!isFloat(quantity, false) || quantity + 1.1 < 1.1) {
		alert(messagesData.quantityError);
		mygrid.selectRowById(rowId);
		return false;
	}
	var date = "" + cellValue(rowId, "usageDate");
	if (date.length == 0) {
		alert(messagesData.dateRequired);
		mygrid.selectRowById(rowId);
		return false;
	}
	var remarks = "" + cellValue(rowId, "remarks");
	if (remarks.length == 0) {
		alert(messagesData.remarksRequired);
		mygrid.selectRowById(rowId);
		return false;
	}
	var url = "containervalidation.do?employee=" + cellValue(rowId, "employee");
	url += "&rowId=" + rowId;
	url += "&companyId=" + $v("companyId");
	url += "&facilityId=" + $v("facilityId");

	validations++;
	callToServer(url);
	return true;
}

function toggleSubstrate(rowId) {
	var partType = cellValue(rowId, "partType");
	if (partType == "F" || partType == "R") {
		$("substrate" + rowId).disabled = true;
	}
	else {
		$("substrate" + rowId).disabled = false;
	}
}

function setControlDevice(rowId) {
	var permit = cellValue(rowId, "permit");
	if (null != controlDevices[permit]) {
		cell(rowId, "controlDevice").setValue(controlDevices[permit]);
	}
	else {
		cell(rowId, "controlDevice").setValue("");
	}
}

var searchBuildingId = 0;

function checkPulldownValues(rowId) {
	if (cellValue(rowId, "buildingId") != searchBuildingId) {
		togglePermits(rowId);
	}
	addValueToDropdownIfNecessary(cellJsonValue(rowId, "applicationMethod"), $("applicationMethod" + rowId));
	addValueToDropdownIfNecessary(cellJsonValue(rowId, "substrate"), $("substrate" + rowId));
}

function togglePermits(rowId) {
	var permitFieldName = "permit" + rowId;
	var permit = $(permitFieldName);
	var buildingId = cellValue(rowId, "buildingId");
	var curVal = cellValue(rowId, "permit");
	var permits = altPermitId[buildingId];
	var permitDescs = altPermitDesc[buildingId];
	if (permit) {
		for ( var i = permit.length; i > 0; i--) {
			permit[i] = null;
		}
		var rowCntr = 0;
		// setSelectOption(rowCntr++, messagesData.pleaseselect, 'SELECT', permit);
		if (null != permits) {
			for ( var i = 0; i < permits.length; i++) {
				setSelectOption(rowCntr++, permitDescs[i], permits[i], permit, null, curVal == permits[i]);
			}
		}
		setSelectOption(rowCntr, messagesData.none, 'NONE', permit, null, curVal == 'NONE');
	}
}

function addValueToDropdownIfNecessary(value, dropdown) {
	if (dropdown != null) {
		for ( var index = 0; index < dropdown.options.length; index++) {
			if (dropdown.options[index].value == value) {
				return;
			}
		}
		var position = dropdown.options.length;
		dropdown.options[position] = new Option(value, value, true, true);
		dropdown.options[position].className = "lightgray";
	}
}
