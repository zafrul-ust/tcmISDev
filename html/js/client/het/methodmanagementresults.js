//Allow different permissions for different columns
var waWindow;
var app;
var appId;
var selectedRowId = 1;

function myResultOnLoadWithGrid(gridConfig) {

	totalLines = document.getElementById("totalLines").value;

	document.getElementById("MethodManagement").style["display"] = "";

	parent.document.getElementById("mainUpdateLinks").style["display"] = "";

	try {
		initGridWithGlobal(gridConfig);
	} catch(ex) {}

	/* Display the standard footer message */
	displayGridSearchDuration();

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
//	colorDeletedRow(selectedRowId);
	selectedRowId = rowId;
	colorDeletedRow(selectedRowId);
}

function newMethod() {
	if ($v("totalLines") == 0) {
		$("totalLines").value = 1;
		setResultFrameSize();
	}
	var rowIndex = mygrid.getRowsNum(); // data array[] index, 0
	// starting point
	var rowId = rowIndex + 1; // actual ID: value (STRING) we put in
	// the JSON data
	var rowData = [ 'Y',
	                '',
	                '',
	                false,
	    			messagesData.all,
	    			messagesData.all,
	    			messagesData.all,
	                '',
	                $v("companyId"),
	                $v("facilityId"),
	                '-1',
	                '-1',
	                '-1',
	                true,
	                true,
	                false,
	                '',
	                '' ];
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
	deleteRow(selectedRowId);
}

function deleteMethod () {
	if (confirm(messagesData.verifyDelete)) {
		var methodId = cellValue(selectedRowId, "methodId");

		for (var index = 0; index < mygrid.getRowsNum(); index++) {
			var rowId = mygrid.getRowId(index);
			if (methodId == cellValue(rowId, "methodId")) {
				deleteRow(rowId);
			}
		}
	}
}

function removeArea () {
	var methodId = cellValue(selectedRowId, "methodId");
	var areaId = cellValue(selectedRowId, "areaId");

	for (var index = 0; index < mygrid.getRowsNum(); index++) {
		var rowId = mygrid.getRowId(index);
		if (methodId == cellValue(rowId, "methodId") && areaId == cellValue(rowId, "areaId")) {
			deleteRow(rowId);
		}
	}
}

function methodHasNoAreas(rowIndex) {
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
		if (methodHasNoAreas(mygrid.getRowIndex(rowId))) {
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
	var url = "areasearch.do?uAction=searchForAreas" +
	"&companyId=" + $v("companyId") +
	"&facilityId=" + $v("facilityId");
	
	openwindow = openWinGeneric(url, "AreaSearchPage", "450", "450", "yes", "50", "50", "no");
	parent.children[parent.children.length] = window;
	parent.showTransitWin(messagesData.area);
}

function addBuilding() {
	var url = "buildingsearch.do?uAction=searchForBuildings" +
	"&companyId=" + $v("companyId") +
	"&areaId=" + cellValue(selectedRowId, "areaId");

	openwindow = openWinGeneric(url, "BuildingSearchPage", "450", "450", "yes", "50", "50", "no");
	parent.children[parent.children.length] = openwindow;
	parent.showTransitWin(messagesData.building);
}

function addWorkArea() {
	var url = "workareasearch.do?uAction=searchForWorkAreas" +
	"&companyId=" + $v("companyId") +
	"&facilityId=" + $v("facilityId") +
	"&buildingId=" + cellValue(selectedRowId, "buildingId");

	openwindow = openWinGeneric(url, "WorkAreaSearchPage", "450", "450", "yes", "50", "50", "no");
	parent.children[parent.children.length] = openwindow;
	parent.showTransitWin(messagesData.workarea);
}

function insertArea(areaId, areaName) {
	openwindow.close();
	var permission = "N";

	if (cellValue(selectedRowId, "areaId") == -1) { 
		deleteRow(selectedRowId);
		permission = "Y";
	}
	var rowData = [	permission,
		                cellValue(selectedRowId, "methodCode"),
		                cellValue(selectedRowId, "method"),
		                cellValue(selectedRowId, "forSolvent"),
		                areaName,
						messagesData.all,
						messagesData.all,
		                cellValue(selectedRowId, "methodId"),
		                $v("companyId"),
		                $v("facilityId"),
		                areaId,
		                '-1',
		                '-1',
		                false,
		                true,
		                false,
		                '',
		                '' ];
	var newLinePosition = mygrid.haasGetRowSpanEndIndex(selectedRowId);
	mygrid.haasAddRowToRowSpan(rowData, newLinePosition);
	mygrid.haasRenderRow(mygrid.getRowId(newLinePosition));
	mygrid.selectRow(newLinePosition);
}

function insertBuilding(buildingId, buildingName) {
	openwindow.close();
	var curBuilding = "" + cellValue(selectedRowId, "buildingId");
	if ((curBuilding == "-1" || curBuilding.length == 0) && cellValue(selectedRowId, "newMethod") == "true") { // Brand new

		cell(selectedRowId, "buildingId").setValue(buildingId);
		cell(selectedRowId, "building").setValue(buildingName);
		cell(selectedRowId, "applicationId").setValue("-1");
		cell(selectedRowId, "applicationDesc").setValue(messagesData.all);
	}
	else {
		if (!buildingExists(buildingId)) {
			var rowData = [ 'Y',
					cellValue(selectedRowId,"methodCode"),
					cellValue(selectedRowId,"method"),
	                cellValue(selectedRowId, "forSolvent"),
					cellValue(selectedRowId,"area"),
					buildingName,
					messagesData.all,
					'',
					$v("companyId"),
					$v("facilityId"),
					cellValue(selectedRowId,"areaId"),
					buildingId,
					'-1',
					false,
					true,
					false,
					'',
			                '' ];
	
			
			var parentIndex = mygrid.haasGetRowSpanStart(selectedRowId);
			var newLinePosition = mygrid.haasGetRowSpanEndIndex(selectedRowId);
			var parentRowId = mygrid.getRowId(newLinePosition - 1);
			
			mygrid.haasAddRowToRowSpan(rowData,newLinePosition, parentIndex);
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
	if (curWorkArea == "-1" && (cellValue(selectedRowId, "newMethod") == "true" || cellValue(selectedRowId, "newMethodRow") == "true" )) {
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
			
			var rowData = [ 'Y', 
			                cellValue(selectedRowId, "methodCode"), 
			                cellValue(selectedRowId, "method"), 
			                cellValue(selectedRowId, "forSolvent"),
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
			                '',
			                cellValue(selectedRowId, "forSolvent")];
	
			mygrid.haasAddRowToRowSpan(rowData,endIndex, parentIndex, parent2Index);
			mygrid.haasRenderRow(mygrid.getRowId(parentIndex));
			mygrid.selectRow(endIndex, true);
		}
		else {
			alert(formatMessage(messagesData.exists, messagesData.workArea, applicationName));
		}
	}
	
}

function getMethodStart(selectedRowId, methodId) {
	for (var index = selectedRowId - 1; index >= 0; index--) {
		if (methodId != cellValue(mygrid.getRowId(index), "methodId")) {
			return index + 1;
		}
	}
	return 0;
}

function getMethodEnd(selectedRowId, methodId) {
	var max = mygrid.getRowsNum();
	for (var index = selectedRowId - 1; index < max; index++) {
		if (methodId != cellValue(mygrid.getRowId(index), "methodId")) {
			return index - 1;
		}
	}
	return max - 1;
}

function deleteRow(rowId) {
	var method = cellValue(rowId, "method");
	var methodCode = cellValue(rowId, "methodCode");
	cell(rowId, "permission").setValue("N");
	cell(rowId, "method").setValue(method);
	cell(rowId, "methodCode").setValue(methodCode);
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
		var methodId = "" + cellValue(selectedRowId, "methodId");
		var parentIndex = getMethodStart(selectedRowId, methodId);
		var endIndex = getMethodEnd(selectedRowId, methodId);
		for ( var index = parentIndex; index <= endIndex; index++) {
			var rowId = mygrid.getRowId(index);
			deleteRow(rowId);
		}
		var rowData = [ 'Y',
						cellValue(selectedRowId,"methodCode"),
						cellValue(selectedRowId,"method"),
						cellValue(selectedRowId, "forSolvent"),
						messagesData.all,
						messagesData.all,
						messagesData.all,
						'',
						$v("companyId"),
						$v("facilityId"),
						'-1',
						'-1',
						'-1',
						false,
						true,
						false,
						'',
						'',
						cellValue(selectedRowId, "forSolvent")];
		endIndex++;
		mygrid.haasAddRowToRowSpan(rowData,endIndex);
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
		var rowData = [ 'Y',
				cellValue(selectedRowId,"methodCode"),
				cellValue(selectedRowId,"method"),
				cellValue(selectedRowId, "forSolvent"),
				cellValue(selectedRowId,"area"),
				messagesData.all,
				messagesData.all,
				'',
				$v("companyId"),
				$v("facilityId"),
				cellValue(selectedRowId,"areaId"),
				'-1',
				'-1',
				false,
				true,
				false,
				'',
				'',
				cellValue(selectedRowId, "forSolvent")];
		mygrid.haasAddRowToRowSpan(rowData,endIndex, parentIndex);
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
		
		var rowData = [ 'Y', 
		                cellValue(selectedRowId, "methodCode"), 
		                cellValue(selectedRowId, "method"),
						cellValue(selectedRowId, "forSolvent"),
		                cellValue(selectedRowId, "area"), 
		                cellValue(selectedRowId, "building"), 
		                messagesData.all, 
		                cellValue(selectedRowId, "methodId"), 
		                $v("companyId"), 
		                $v("facilityId"), 
		                cellValue(selectedRowId, "areaId"), 
		                cellValue(selectedRowId, "buildingId"), 
		                '-1', 
		                false, 
		                true, 
		                false, 
		                '',
		                '',
						cellValue(selectedRowId, "forSolvent")];
		mygrid.haasAddRowToRowSpan(rowData,newLinePosition, parentIndex, parent2Index);
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
			var methodCode = cellValue(rowId, "methodCode"); 
			if (cellValue(rowId, "newMethod") == "true") {
				if (methodCode == "") {
					alert(formatMessage(messagesData.required, messagesData.code));
					mygrid.selectRowById(rowId);
					return false;
				}
				else if (cellValue(rowId, "method") == "") {
					alert(formatMessage(messagesData.required, messagesData.method));
					mygrid.selectRowById(rowId);
					return false;
				}
				else if (existingCodes.indexOf(methodCode) >= 0) {
					alert(formatMessage(messagesData.existsForFacility, messagesData.method, methodCode));
					mygrid.selectRowById(rowId);
					return false;
				}
			}
			else if (cellValue(rowId, "oldMethodCode") != methodCode && existingCodes.indexOf(methodCode) >= 0) {
				alert(formatMessage(messagesData.existsForFacility, messagesData.method, methodCode));
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

