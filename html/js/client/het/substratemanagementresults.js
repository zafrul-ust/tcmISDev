//Allow different permissions for different columns
var waWindow;
var app;
var appId;
var selectedRowId = 1;

function myResultOnLoadWithGrid(gridConfig) {

	totalLines = document.getElementById("totalLines").value;

	document.getElementById("SubstrateManagement").style["display"] = "";

	parent.document.getElementById("mainUpdateLinks").style["display"] = "";

	try {
		initGridWithGlobal(gridConfig);
	}
	catch (ex) {
	}

	/* Display the standard footer message */
	displayGridSearchDuration();

	/*
	 * It is important to call this after all the divs have been turned on or off.
	 */
	if ($v("totalLines") == '' || $v("totalLines") == 0)
		$("totalLines").value = 1;

	setResultFrameSize();
}

function selectRightclick(rowId, cellInd) {
	selectRow(rowId, cellInd);
	var startArea = mygrid.haasGetRowSpanStart(rowId);
	var startBuilding = mygrid.haasGetRowSpanStartLvl2(rowId);

	if (cellValue(selectedRowId, "areaId") == -1) {
		toggleContextMenu('rightClickMenuAreaOnly');
	}
	else if (cellValue(selectedRowId, "buildingId") == "-1") {
		toggleContextMenu('rightClickMenuOneBuildingNoWorkArea');
	}
	else if (areaHasMoreThanOneBuildingRemaining(startArea)) {
		if (buildingHasMoreThanOneWorkAreaRemaining(startBuilding)) {
			toggleContextMenu('fullRightClickMenu');
		}
		else {
			toggleContextMenu('rightClickMenuOneWorkArea');
		}
	}
	else if (buildingHasMoreThanOneWorkAreaRemaining(startBuilding)) {
		toggleContextMenu('rightClickMenuOneBuilding');
	}
	else {
		toggleContextMenu('rightClickMenuOneBuildingOneWorkArea');
	}

}

function areaHasMoreThanOneBuildingRemaining(rowIndex) {
	if (mygrid._haas_row_span_map[rowIndex] > 1) {
		var count = 0;
		var end = rowIndex + mygrid._haas_row_span_map[rowIndex] - 1;
		for ( var index = rowIndex; index <= end; index++) {
			if (mygrid._haas_row_span_lvl2_map[index] > 0 && cellValue(mygrid.getRowId(index), "deleted") != "true") {
				count++;
			}
		}
		return count > 1;
	}
	return false;
}

function buildingHasMoreThanOneWorkAreaRemaining(rowIndex) {
	if (mygrid._haas_row_span_lvl2_map[rowIndex] > 1) {
		var count = 0;
		var end = rowIndex + mygrid._haas_row_span_lvl2_map[rowIndex] - 1;
		for ( var index = rowIndex; index <= end; index++) {
			if (cellValue(mygrid.getRowId(index), "deleted") != "true") {
				count++;
			}
		}
		return count > 1;
	}
	return false;
}

function buildingHasAtLeastOneWorkAreaRemaining(rowIndex) {
	if (mygrid._haas_row_span_lvl2_map[rowIndex] >= 1) {
		var count = 0;
		var end = rowIndex + mygrid._haas_row_span_lvl2_map[rowIndex] - 1;
		for ( var index = rowIndex; index <= end; index++) {
			if (cellValue(mygrid.getRowId(index), "deleted") != "true") {
				count++;
			}
		}
		return count > 0;
	}
	return false;
}

function selectRow(rowId, cellInd) {
	colorDeletedRow(selectedRowId);
	selectedRowId = rowId;
	colorDeletedRow(selectedRowId);
}

function newSubstrate() {
	if ($v("totalLines") == 0) {
		$("totalLines").value = 1;
		setResultFrameSize();
	}
	var rowIndex = mygrid.getRowsNum(); // data array[] index, 0
	// starting point
	var rowId = rowIndex + 1; // actual ID: value (STRING) we put in
	// the JSON data
	var rowData = [
					'Y', '', '', messagesData.all, messagesData.all, messagesData.all, '', $v("companyId"), $v("facilityId"), '-1', '-1', '-1', true, true, false, '', ''
	];
	mygrid.haasAddRowToRowSpan(rowData, rowIndex);
	mygrid.haasRenderRow(rowId);
	mygrid.selectRow(rowIndex);
}

function removeBuilding() {
	var parentIndex = mygrid.haasGetRowSpanStartLvl2(selectedRowId);
	var endIndex = mygrid.haasGetRowSpanEndIndexLvl2(selectedRowId);

	for ( var index = parentIndex; index < endIndex; index++) {
		var rowId = mygrid.getRowId(index);
		deleteRow(rowId);
	}

}

function removeWorkArea() {
	colorDeletedRow(selectedRowId);
	deleteRow(selectedRowId);
}

function deleteSubstrate() {
	if (confirm(messagesData.verifyDelete)) {
		var substrateId = cellValue(selectedRowId, "substrateId");

		for ( var index = 0; index < mygrid.getRowsNum(); index++) {
			var rowId = mygrid.getRowId(index);
			if (substrateId == cellValue(rowId, "substrateId")) {
				deleteRow(rowId);
			}
		}
	}
}

function removeArea() {
	var substrateId = cellValue(selectedRowId, "substrateId");
	var areaId = cellValue(selectedRowId, "areaId");

	for ( var index = 0; index < mygrid.getRowsNum(); index++) {
		var rowId = mygrid.getRowId(index);
		if (substrateId == cellValue(rowId, "substrateId") && areaId == cellValue(rowId, "areaId")) {
			deleteRow(rowId);
		}
	}
}

function substrateHasNoAreas(rowIndex) {
	if (mygrid._haas_row_span_map[rowIndex] > 0) {
		var end = rowIndex + mygrid._haas_row_span_map[rowIndex] - 1;
		for ( var index = rowIndex; index <= end; index++) {
			if (cellValue(mygrid.getRowId(index), "deleted") != "true") {
				return false;
			}
		}
		return true;
	}
	return false;
}

function colorDeletedRow(rowId) {
	if (cellValue(rowId, "deleted") == "true") {
		if (substrateHasNoAreas(mygrid.getRowIndex(rowId))) {
			mygrid.haasSetColSpanStyle(rowId, 1, 5, "background-color: #BEBEBE; text-decoration:line-through;");
		}
		else if (buildingHasAtLeastOneWorkAreaRemaining(mygrid.getRowIndex(rowId))) {
			mygrid.haasSetColSpanStyle(rowId, 5, 5, "background-color: #BEBEBE; text-decoration:line-through;");
		}
		else {
			mygrid.haasSetColSpanStyle(rowId, 4, 5, "background-color: #BEBEBE; text-decoration:line-through;");
		}
	}
}

var openwindow = null;

function addArea() {
	var url = "areasearch.do?uAction=searchForAreas" + "&companyId=" + $v("companyId") + "&facilityId=" + $v("facilityId");

	openwindow = openWinGeneric(url, "AreaSearchPage", "450", "450", "yes", "50", "50", "no");
	parent.children[parent.children.length] = window;
	parent.showTransitWin(messagesData.area);
}

function addBuilding() {
	var url = "buildingsearch.do?uAction=searchForBuildings" + "&companyId=" + $v("companyId") + "&areaId=" + cellValue(selectedRowId, "areaId");

	openwindow = openWinGeneric(url, "BuildingSearchPage", "450", "450", "yes", "50", "50", "no");
	parent.children[parent.children.length] = openwindow;
	parent.showTransitWin(messagesData.building);
}

function addWorkArea() {
	var url = "workareasearch.do?uAction=searchForWorkAreas" + "&companyId=" + $v("companyId") + "&facilityId=" + $v("facilityId") + "&buildingId=" + cellValue(selectedRowId, "buildingId");

	openwindow = openWinGeneric(url, "WorkAreaSearchPage", "450", "450", "yes", "50", "50", "no");
	parent.children[parent.children.length] = openwindow;
	parent.showTransitWin(messagesData.workarea);
}

function insertArea(areaId, areaName) {
	openwindow.close();
	var permission = "N";

	if (cellValue(selectedRowId, "areaId") == -1) { // Brand new Permit
		deleteRow(selectedRowId);
		permission = "Y";
	}
	var rowData = [
					permission,
					cellValue(selectedRowId, "substrateCode"),
					cellValue(selectedRowId, "substrate"),
					areaName,
					messagesData.all,
					messagesData.all,
					cellValue(selectedRowId, "substrateId"),
					$v("companyId"),
					$v("facilityId"),
					areaId,
					'-1',
					'-1',
					false,
					true,
					false,
					'',
					''
	];
	var newLinePosition = mygrid.haasGetRowSpanEndIndex(selectedRowId);
	mygrid.haasAddRowToRowSpan(rowData, newLinePosition);
	mygrid.haasRenderRow(mygrid.getRowId(newLinePosition));
	mygrid.selectRow(newLinePosition);
}

function insertBuilding(buildingId, buildingName) {
	openwindow.close();
	var curBuilding = "" + cellValue(selectedRowId, "buildingId");
	if ((curBuilding == "-1" || curBuilding.length == 0) && cellValue(selectedRowId, "newSubstrate") == "true") { // Brand
		// new

		cell(selectedRowId, "buildingId").setValue(buildingId);
		cell(selectedRowId, "building").setValue(buildingName);
		cell(selectedRowId, "applicationId").setValue("-1");
		cell(selectedRowId, "applicationDesc").setValue(messagesData.all);
	}
	else {
		if (!buildingExists(buildingId)) {
			var rowData = [
							'Y',
							cellValue(selectedRowId, "substrateCode"),
							cellValue(selectedRowId, "substrate"),
							cellValue(selectedRowId, "area"),
							buildingName,
							messagesData.all,
							'',
							$v("companyId"),
							$v("facilityId"),
							cellValue(selectedRowId, "areaId"),
							buildingId,
							'-1',
							false,
							true,
							false,
							'',
							''
			];

			var parentIndex = mygrid.haasGetRowSpanStart(selectedRowId);
			var newLinePosition = mygrid.haasGetRowSpanEndIndex(selectedRowId);
			var parentRowId = mygrid.getRowId(newLinePosition - 1);

			mygrid.haasAddRowToRowSpan(rowData, newLinePosition, parentIndex);
			mygrid.haasRenderRow(mygrid.getRowId(parentIndex));
			mygrid.selectRow(parentIndex, true);

			for ( var index = parentIndex; index < newLinePosition; index++) {
				var rowId = mygrid.getRowId(index);
				if (cellValue(selectedRowId, "buildingId") == "-1") {
					cell(rowId, "deleted").setValue(true);
					colorDeletedRow(rowId);
				}
			}
		}
		else {
			alert(formatMessage(messagesData.exists, messagesData.building, buildingName));
		}
	}
}

function insertWorkArea(applicationId, applicationName) {
	openwindow.close();
	var curWorkArea = "" + cellValue(selectedRowId, "applicationId");
	if (curWorkArea == "-1" && (cellValue(selectedRowId, "newSubstrate") == "true" || cellValue(selectedRowId, "newSubstrateRow") == "true")) {
		cell(selectedRowId, "applicationId").setValue(applicationId);
		cell(selectedRowId, "applicationDesc").setValue(applicationName);
	}
	else {
		if (!workAreaExists(applicationId)) {
			var parentIndex = mygrid.haasGetRowSpanStart(selectedRowId);
			var parent2Index = mygrid.haasGetRowSpanStartLvl2(selectedRowId);
			var endIndex = mygrid.haasGetRowSpanEndIndexLvl2(selectedRowId);
			var parentRowId = mygrid.getRowId(endIndex - 1);

			for ( var index = parent2Index; index < endIndex; index++) {
				var rowId = mygrid.getRowId(index);
				if (cellValue(selectedRowId, "applicationId") == "-1") {
					cell(rowId, "deleted").setValue(true);
					colorDeletedRow(rowId);
				}
			}

			var rowData = [
							'Y',
							cellValue(selectedRowId, "substrateCode"),
							cellValue(selectedRowId, "substrate"),
							cellValue(selectedRowId, "area"),
							cellValue(selectedRowId, "building"),
							applicationName,
							'',
							$v("companyId"),
							$v("facilityId"),
							cellValue(selectedRowId, "areaId"),
							cellValue(selectedRowId, "buildingId"),
							applicationId,
							false,
							true,
							false,
							'',
							''
			];

			mygrid.haasAddRowToRowSpan(rowData, endIndex, parentIndex, parent2Index);
			mygrid.haasRenderRow(mygrid.getRowId(parentIndex));
			mygrid.selectRow(endIndex, true);
		}
		else {
			alert(formatMessage(messagesData.exists, messagesData.workArea, applicationName));
		}
	}

}

function getSubstrateStart(selectedRowId, substrateId) {
	for ( var index = selectedRowId - 1; index >= 0; index--) {
		if (substrateId != cellValue(mygrid.getRowId(index), "substrateId")) {
			return index + 1;
		}
	}
	return 0;
}

function getSubstrateEnd(selectedRowId, substrateId) {
	var max = mygrid.getRowsNum();
	for ( var index = selectedRowId - 1; index < max; index++) {
		if (substrateId != cellValue(mygrid.getRowId(index), "substrateId")) {
			return index - 1;
		}
	}
	return max - 1;
}

function deleteRow(rowId) {
	var substrate = cellValue(rowId, "substrate");
	var substrateCode = cellValue(rowId, "substrateCode");
	cell(rowId, "permission").setValue("N");
	cell(rowId, "substrate").setValue(substrate);
	cell(rowId, "substrateCode").setValue(substrateCode);
	cell(rowId, "deleted").setValue(true);
	colorDeletedRow(rowId);
}

function addAllAreas() {
	var curArea = "" + cellValue(selectedRowId, "areaId");
	if (curArea == "-1" || curArea.length == 0) {
		cell(selectedRowId, "areaId").setValue("-1");
		cell(selectedRowId, "area").setValue(messagesData.all);
		cell(selectedRowId, "applicationId").setValue("-1");
		cell(selectedRowId, "applicationDesc").setValue(messagesData.all);
	}
	else {
		var substrateId = "" + cellValue(selectedRowId, "substrateId");
		var parentIndex = getSubstrateStart(selectedRowId, substrateId);
		var endIndex = getSubstrateEnd(selectedRowId, substrateId);
		for ( var index = parentIndex; index <= endIndex; index++) {
			var rowId = mygrid.getRowId(index);
			deleteRow(rowId);
		}
		var rowData = [
						'Y', cellValue(selectedRowId, "substrateCode"), cellValue(selectedRowId, "substrate"), messagesData.all, messagesData.all, messagesData.all, '', $v("companyId"), $v("facilityId"), '-1', '-1', '-1', false, true, false, '', ''
		];
		endIndex++;
		mygrid.haasAddRowToRowSpan(rowData, endIndex);
		mygrid.haasRenderRow(mygrid.getRowId(endIndex));
		mygrid.selectRow(endIndex, true);

	}
}

function addAllBuildings() {
	var curBuilding = "" + cellValue(selectedRowId, "buildingId");
	if (curBuilding == "-1" || curBuilding.length == 0) {
		cell(selectedRowId, "buildingId").setValue("-1");
		cell(selectedRowId, "building").setValue(messagesData.all);
		cell(selectedRowId, "applicationId").setValue("-1");
		cell(selectedRowId, "applicationDesc").setValue(messagesData.all);
	}
	else {
		var parentIndex = mygrid.haasGetRowSpanStart(selectedRowId);
		var endIndex = mygrid.haasGetRowSpanEndIndex(selectedRowId);
		for ( var index = parentIndex; index < endIndex; index++) {
			var rowId = mygrid.getRowId(index);
			deleteRow(rowId);
		}
		var rowData = [
						'Y',
						cellValue(selectedRowId, "substrateCode"),
						cellValue(selectedRowId, "substrate"),
						cellValue(selectedRowId, "area"),
						messagesData.all,
						messagesData.all,
						'',
						$v("companyId"),
						$v("facilityId"),
						cellValue(selectedRowId, "areaId"),
						'-1',
						'-1',
						false,
						true,
						false,
						'',
						''
		];
		mygrid.haasAddRowToRowSpan(rowData, endIndex, parentIndex);
		mygrid.haasRenderRow(mygrid.getRowId(endIndex));
		mygrid.selectRow(endIndex, true);
	}
}

function addAllWorkAreas() {
	var curWorkArea = "" + cellValue(selectedRowId, "applicationId");
	if (curWorkArea == "-1" || curWorkArea.length == 0) {
		cell(selectedRowId, "applicationId").setValue("-1");
		cell(selectedRowId, "applicationDesc").setValue(messagesData.all);
	}
	else {
		var parentIndex = mygrid.haasGetRowSpanStart(selectedRowId);
		var newLinePosition = mygrid.haasGetRowSpanEndIndexLvl2(selectedRowId);
		var parentRowId = mygrid.getRowId(newLinePosition - 1);
		var parent2Index = mygrid.haasGetRowSpanStartLvl2(selectedRowId);
		var endIndex = mygrid.haasGetRowSpanEndIndexLvl2(selectedRowId);
		for ( var index = parent2Index; index < endIndex; index++) {
			var rowId = mygrid.getRowId(index);
			deleteRow(rowId);
		}

		var rowData = [
						'Y',
						cellValue(selectedRowId, "substrateCode"),
						cellValue(selectedRowId, "substrate"),
						cellValue(selectedRowId, "area"),
						cellValue(selectedRowId, "building"),
						messagesData.all,
						cellValue(selectedRowId, "substrateId"),
						$v("companyId"),
						$v("facilityId"),
						cellValue(selectedRowId, "areaId"),
						cellValue(selectedRowId, "buildingId"),
						'-1',
						false,
						true,
						false,
						'',
						''
		];
		mygrid.haasAddRowToRowSpan(rowData, newLinePosition, parentIndex, parent2Index);
		mygrid.haasRenderRow(mygrid.getRowId(parentIndex));
		mygrid.selectRow(parentIndex, true);
	}
}

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

function validateForm() {

	var length = mygrid.getRowsNum();
	for ( var rowIndex = 0; rowIndex < length; rowIndex++) {
		if (mygrid._haas_row_span_map[rowIndex] > 0) {
			var rowId = mygrid.getRowId(rowIndex);
			var substrateCode = cellValue(rowId, "substrateCode");
			if (cellValue(rowId, "newSubstrate") == "true") {
				if (substrateCode == "") {
					alert(formatMessage(messagesData.required, messagesData.substrate));
					mygrid.selectRowById(rowId);
					return false;
				}
				else if (cellValue(rowId, "substrate") == "") {
					alert(formatMessage(messagesData.required, messagesData.substrate));
					mygrid.selectRowById(rowId);
					return false;
				}
				else if (existingCodes.indexOf(substrateCode) >= 0) {
					alert(formatMessage(messagesData.existsForFacility, messagesData.substrate, substrateCode));
					mygrid.selectRowById(rowId);
					return false;
				}
			}
			else if (cellValue(rowId, "oldSubstrateCode") != substrateCode && existingCodes.indexOf(substrateCode) >= 0) {
				alert(formatMessage(messagesData.existsForFacility, messagesData.substrate, substrateCode));
				mygrid.selectRowById(rowId);
				return false;
			}
		}
	}
	return true;
}

function workAreaExists(applicationId) {
	var parentIndex = mygrid.haasGetRowSpanStart(selectedRowId);
	var endIndex = mygrid.haasGetRowSpanEndIndex(selectedRowId);
	for ( var index = parentIndex; index < endIndex; index++) {
		var rowId = mygrid.getRowId(index);
		if (cellValue(rowId, "deleted") != "true" && applicationId == cellValue(rowId, "applicationId")) {
			return true;
		}
	}
	return false;
}

function buildingExists(buildingId) {
	var parentIndex = mygrid.haasGetRowSpanStart(selectedRowId);
	var endIndex = mygrid.haasGetRowSpanEndIndex(selectedRowId);
	for ( var index = parentIndex; index < endIndex; index++) {
		var rowId = mygrid.getRowId(index);
		if (cellValue(rowId, "deleted") != "true" && buildingId == cellValue(rowId, "buildingId")) {
			return true;
		}
	}
	return false;
}
