//Allow different permissions for different columns
var waWindow;
var app;
var appId;
var selectedRowId = 1;
var resizeGridWithWindow = true;

function myResultOnLoadWithGrid(gridConfig) {

	totalLines = document.getElementById("totalLines").value;

	document.getElementById("PermitManagement").style["display"] = "";

	parent.document.getElementById("mainUpdateLinks").style["display"] = "";

	try {
		initGridWithGlobal(gridConfig);
	} catch (ex) {
	}

	/* Display the standard footer message */
	displayGridSearchDuration();

	if ($v("totalLines") == '' || $v("totalLines") == 0)
		$("totalLines").value = 1;
	/*
	 * It is important to call this after all the divs have been turned on or
	 * off.
	 */
	setResultFrameSize();
}

function newPermit() {
	if ($v("totalLines") == 0) {
		$("totalLines").value = 1;
		setResultFrameSize();
	}
	var rowIndex = mygrid.getRowsNum(); // data array[] index, 0
	// starting point
	var rowId = rowIndex + 1; // actual ID: value (STRING) we put in
	// the JSON data
	var rowData = [ 'Y', true, $v("areaId"), $v("buildingId"), '', '', true,
			'', '', $v("companyId"), $v("facilityId") ];
	mygrid.addRow(rowId, rowData, rowIndex);
	mygrid.haasRenderRow(rowIndex);
	mygrid.selectRow(rowIndex);
}

var openwindow = null;

function update() {
	if (validateForm()) {
		document.genericForm.target = '';
		$('uAction').value = 'update';

		parent.showPleaseWait(); // Show "please wait" while
		// updating

		if (mygrid != null)
			mygrid.parentFormOnSubmit();

		document.genericForm.submit(); // back to server
	}
}

function deletePermits() {
	if (confirm(messagesData.verifyDelete)) {
		document.genericForm.target = '';
		$('uAction').value = 'delete';

		parent.showPleaseWait(); // Show "please wait" while
		// updating

		if (mygrid != null)
			mygrid.parentFormOnSubmit();

		document.genericForm.submit(); // back to server
	}
}

function validateForm() {
	var rowCntr=0;
	for ( var i = 0; i < mygrid.getRowsNum(); i++) {
		var rowId = mygrid.getRowId(i);
		if ("true" == cellValue(rowId, "updated")) {
			rowCntr++;
			if ("" + cellValue(rowId, "permitId") == ""
					&& cellValue(rowId, "permitName") == "") {
				alert(formatMessage(messagesData.required,
						messagesData.permitName));
				mygrid.selectRowById(rowId);
				return false;
			}
		}
	}
	if(rowCntr == 0) {
		alert(messagesData.selectSomething);
		return false;
	}
	return true;
}

function setBuildingsforArea(rowId) {
	var buildingIdFieldName = "buildingId" + rowId;
	var buildingId = $(buildingIdFieldName);
	var areaId = cellValue(rowId, "areaId");
	var curVal = cellValue(rowId, "buildingId");
	var buildingIds = parent.altBuildingId[areaId];
	var buildingDescs = parent.altBuildingDesc[areaId];
	for ( var i = buildingId.length; i > 0; i--) {
		buildingId[i] = null;
	}
	for ( var i = 0; i < buildingIds.length; i++) {
		setSelectOption(i, buildingDescs[i], buildingIds[i], buildingId, null,
				curVal == buildingIds[i]);
	}
}
