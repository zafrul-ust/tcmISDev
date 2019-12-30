//Allow different permissions for different columns
var waWindow;
var app;
var appId;
var selectedRowId = 1;

function myResultOnLoadWithGrid(gridConfig) {

	totalLines = document.getElementById("totalLines").value;
	
	document.getElementById("mixturePermission").style["display"] = "";

	if(totalLines == 0)
		parent.document.getElementById("hasDataLink").style["display"] = "none";
	else
		parent.document.getElementById("hasDataLink").style["display"] = "";

	try {
		initGridWithGlobal(gridConfig);
	} catch(ex) {}


	/* Display the standard footer message */
	displayGridSearchDuration();

	/*
	 * It is important to call this after all the divs have been turned on
	 * or off.
	 */
	if ($v("totalLines") == '' || $v("totalLines") == 0) 
		$("totalLines").value = 1;
	
	setResultFrameSize();
}

function selectRightclick(rowId, cellInd) {
	selectRow(rowId, cellInd);  
//	var startArea = mygrid.haasGetRowSpanStart(rowId);
//	var startBuilding = mygrid.haasGetRowSpanStartLvl2(rowId);
	
	if(cellValue(rowId, "status") == 'deleteAll') {
 		toggleContextMenu('nothingMenu');
 		return;
 	}
	
	vitems = new Array();
	
	if (cellValue(rowId, "areaId") == -1 || cellValue(rowId, "areaId").trim().length == 0) {
		vitems[vitems.length ] = "text="+messagesData.addarea+";url=javascript:addArea();";
	} else if(cellValue(rowId, "status") != 'deleteAll') {
		vitems[vitems.length ] = "text="+messagesData.addbuilding+";url=javascript:addBuilding();";
		vitems[vitems.length ] = "text="+messagesData.addallbuildings+";url=javascript:addAllBuildings();";
	}
	
	if (cellValue(rowId, "status") != 'deleteAll' && cellInd >= mygrid.getColIndexById("building") && cellValue(selectedRowId, "buildingId") != "-1" && cellValue(selectedRowId, "buildingId").trim().length != 0) {
		vitems[vitems.length ] = "text="+messagesData.removebuilding+";url=javascript:removeBuilding();";
		vitems[vitems.length ] = "text="+messagesData.addworkarea+";url=javascript:addWorkArea();";
		vitems[vitems.length ] = "text="+messagesData.addallworkareass+";url=javascript:addAllWorkAreas();";
	}
	
	if (cellValue(rowId, "status") != 'deleteBuilding' && cellInd >= mygrid.getColIndexById("applicationDesc") && 
		cellValue(rowId, "applicationId") != "-1" && cellValue(rowId, "applicationId").trim().length != 0) {
		vitems[vitems.length ] = "text="+messagesData.removeworkarea+";url=javascript:removeWorkArea();";
	}
	
	if(cellValue(rowId, "status") != 'deleteAll' && cellValue(rowId, "areaId") != -1 && cellValue(rowId, "areaId").trim().length != 0)
		vitems[vitems.length ] = "text="+messagesData.removeallpermissions+";url=javascript:deleteAllPermissions();";
		
	if(cellValue(rowId, "status") != 'deleteAll') 
		vitems[vitems.length ] = "text="+messagesData.deleteandcreatenewpermission+";url=javascript:dupMixture();";

	replaceMenu('rightClickMenu',vitems);  
   
    if(vitems.length > 0)
 		toggleContextMenu('rightClickMenu');
 	else
 		toggleContextMenu('nothingMenu');
 	
}

function replaceMenu(menuname,menus){
	  var i = mm_returnMenuItemCount(menuname);

	  for(;i> 1; i-- )
			mm_deleteItem(menuname,i);

	  for( i = 0; i < menus.length; ){
 		var str = menus[i];

 		i++;
		mm_insertItem(menuname,i,str);
		// delete original first item.
    	if( i == 1 ) mm_deleteItem(menuname,1);
      }
}

function dupMixture(rowId) {
	deleteAllPermissions();
	
	var rowData = [ 'Y',
					cellValue(selectedRowId,"mixtureName"),
					messagesData.none,
					messagesData.none,
					messagesData.none,
					cellValue(selectedRowId,"mixtureId"),
					$v("companyId"),
					$v("facilityId"),
					'','','',
					'','','',
					'new'];
	
	var newLinePosition = mygrid.haasGetRowSpanEndIndex(selectedRowId);
	mygrid.haasAddRowToRowSpan(rowData, newLinePosition);
	mygrid.haasRenderRow(mygrid.getRowId(newLinePosition));
	mygrid.selectRow(newLinePosition);
	
}
/*
function areaHasMoreThanOneBuildingRemaining(rowIndex) {
	if (mygrid._haas_row_span_map[rowIndex] > 1) {
		var count = 0;
		var end = rowIndex + mygrid._haas_row_span_map[rowIndex] - 1;
		for ( var index = rowIndex; index <= end; index++) {
			if (mygrid._haas_row_span_lvl2_map[index] > 0 && cellValue(mygrid.getRowId(index), "status") != "delete") {
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
			if (cellValue(mygrid.getRowId(index), "status") != "delete") {
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
			if (cellValue(mygrid.getRowId(index), "status") != "delete") {
				count++;
			}
		}
		return count > 0;
	}
	return false;
}
*/
function selectRow(rowId, cellInd) {
//	colorDeletedRow(selectedRowId);
	selectedRowId = rowId;
	
	var length = mygrid.getRowsNum();
	for ( var rowIndex = 0; rowIndex < length; rowIndex++) {
		var rowId = mygrid.getRowId(rowIndex);
		try {
			if (cellValue(rowId, "status").substring(0,6) == "delete") {
					colorDeletedRow(rowId);
			}
		} catch(ex) {}
	}  
	
//	colorDeletedRow(selectedRowId);
}

function removeBuilding() {
	var parentIndex = mygrid.haasGetRowSpanStartLvl2(selectedRowId);
	var endIndex = mygrid.haasGetRowSpanEndIndexLvl2(selectedRowId);

	for ( var index = parentIndex; index < endIndex; index++) {
			var rowId = mygrid.getRowId(index);
			cell(rowId, "status").setValue('deleteBuilding');
			colorDeletedRow(rowId);
	}
	
}

function removeWorkArea() {
	cell(selectedRowId, "status").setValue('deleteWorkArea');
	colorDeletedRow(selectedRowId);
}

function deleteAllPermissions() {
	if (confirm(messagesData.verifyDelete)) {
		var parentIndex = mygrid.haasGetRowSpanStart(selectedRowId);
		var endIndex = mygrid.haasGetRowSpanEndIndex(selectedRowId);
	
		for ( var index = parentIndex; index < endIndex; index++) {
				var rowId = mygrid.getRowId(index);
				cell(rowId, "status").setValue('deleteAll');
				colorDeletedRow(mygrid.getRowId(index));
		}
	}
}

function colorDeletedRow(rowId) {
	if (cellValue(rowId, "status") == 'deleteAll') 
		mygrid.haasSetColSpanStyle(rowId, 1, 4, "background-color: #BEBEBE; text-decoration:line-through;");
	else if (cellValue(rowId, "status") == "deleteBuilding") 
		mygrid.haasSetColSpanStyle(rowId, 3, 4, "background-color: #BEBEBE; text-decoration:line-through;");
	else if (cellValue(rowId, "status") == "deleteWorkArea")
		mygrid.haasSetColSpanStyle(rowId, 4, 4, "background-color: #BEBEBE; text-decoration:line-through;");
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
	if (cellValue(selectedRowId, "areaId") == -1 || cellValue(selectedRowId, "areaId").trim().length == 0) { 
		cell(selectedRowId, "areaId").setValue(areaId);
		cell(selectedRowId, "area").setValue(areaName);
		if(cellValue(selectedRowId, "areaId") == -1)
			cell(selectedRowId, "status").setValue('changed');
	}
	else {
		var rowData = [ 'Y', cellValue(selectedRowId, "mixtureName"), areaName, messagesData.all, messagesData.all, cellValue(selectedRowId, "mixtureId"), $v("companyId"), $v("facilityId"), areaId, '-1', '-1', 'new'];
		var newLinePosition = mygrid.haasGetRowSpanEndIndex(selectedRowId);
		mygrid.haasAddRowToRowSpan(rowData, newLinePosition);
		mygrid.haasRenderRow(mygrid.getRowId(newLinePosition));
		mygrid.selectRow(newLinePosition);
	}
}

function insertBuilding(buildingId, buildingName) {
	openwindow.close();
	var curBuilding = "" + cellValue(selectedRowId, "buildingId");
	if ((curBuilding == "-1" || curBuilding.length == 0)) {

		cell(selectedRowId, "buildingId").setValue(buildingId);
		cell(selectedRowId, "building").setValue(buildingName);
		cell(selectedRowId, "applicationId").setValue("-1");
		cell(selectedRowId, "applicationDesc").setValue(messagesData.all);
		if(cellValue(selectedRowId, "status") != "new")
			cell(selectedRowId, "status").setValue('changed');
	}
	else {
		if (!buildingExists(buildingId)) {
			var rowData = [ 'Y',
					cellValue(selectedRowId,"mixtureName"),
					cellValue(selectedRowId,"area"),
					buildingName,
					messagesData.all,
					cellValue(selectedRowId,"mixtureId"),
					$v("companyId"),
					$v("facilityId"),
					cellValue(selectedRowId,"areaId"),
					buildingId,
					'-1','','','',
					'new'];
	
			var parentIndex = mygrid.haasGetRowSpanStart(selectedRowId);
			var newLinePosition = mygrid.haasGetRowSpanEndIndex(selectedRowId);
			var parentRowId = mygrid.getRowId(newLinePosition - 1);

			mygrid.haasAddRowToRowSpan(rowData,newLinePosition, parentIndex);
			mygrid.haasRenderRow(mygrid.getRowId(parentIndex));
			mygrid.selectRow(parentIndex, true);	

			for ( var index = parentIndex; index < newLinePosition; index++) {
				var rowId = mygrid.getRowId(index);
				if (cellValue(rowId, "buildingId") == "-1") {
					cell(rowId, "status").setValue("deleteBuilding");
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
	if (curWorkArea == "-1") {
		cell(selectedRowId, "applicationId").setValue(applicationId);
		cell(selectedRowId, "applicationDesc").setValue(applicationName);
		if(cellValue(selectedRowId, "status") != "new")
			cell(selectedRowId, "status").setValue('changed');
	}
	else {
		if (!workAreaExists(applicationId)) {
			var parentIndex = mygrid.haasGetRowSpanStart(selectedRowId);
			var parent2Index = mygrid.haasGetRowSpanStartLvl2(selectedRowId);
			var endIndex = mygrid.haasGetRowSpanEndIndexLvl2(selectedRowId);
			var parentRowId = mygrid.getRowId(endIndex - 1);
	
			for ( var index = parent2Index; index < endIndex; index++) {
				var rowId = mygrid.getRowId(index);
				if (cellValue(rowId, "applicationId") == "-1") {
					cell(rowId, "status").setValue('deleteWorkArea');
					colorDeletedRow(rowId);
				}
			}
			
			var rowData = [ 'Y', 
			                cellValue(selectedRowId, "mixtureName"), 
			                cellValue(selectedRowId, "area"), 
			                cellValue(selectedRowId, "building"), 
			                applicationName, 
			                '', 
			                $v("companyId"), 
			                $v("facilityId"), 
			                cellValue(selectedRowId, "areaId"), 
			                cellValue(selectedRowId, "buildingId"), 
			                applicationId,'','','', 
			                'new'];
	
			mygrid.haasAddRowToRowSpan(rowData,endIndex, parentIndex, parent2Index);
			mygrid.haasRenderRow(mygrid.getRowId(parentIndex));
			mygrid.selectRow(endIndex, true);
		}
		else {
			alert(formatMessage(messagesData.exists, messagesData.workArea, applicationName));
		}
	}
	
}

function addAllBuildings() {
	var curBuilding = "" + cellValue(selectedRowId, "buildingId");
	if (curBuilding == "-1" || curBuilding.length == 0) {
		cell(selectedRowId, "buildingId").setValue("-1");
		cell(selectedRowId, "building").setValue(messagesData.all);
		cell(selectedRowId, "applicationId").setValue("-1");
		cell(selectedRowId, "applicationDesc").setValue(messagesData.all);
		if(cellValue(selectedRowId, "status") != "new")
			cell(selectedRowId, "status").setValue('changed');
	}
	else {
		var parentIndex = mygrid.haasGetRowSpanStart(selectedRowId);
		var endIndex = mygrid.haasGetRowSpanEndIndex(selectedRowId);
		for ( var index = parentIndex; index < endIndex; index++) {
			var rowId = mygrid.getRowId(index);
			cell(rowId, "status").setValue('deleteBuilding');
			colorDeletedRow(rowId);
		}
		var rowData = [ 'Y',
				cellValue(selectedRowId,"mixtureName"),
				cellValue(selectedRowId,"area"),
				messagesData.all,
				messagesData.all,
				cellValue(selectedRowId,"mixtureId"),
				$v("companyId"),
				$v("facilityId"),
				cellValue(selectedRowId,"areaId"),
				'-1',
				'-1','','','',
				'new'];
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
		if(cellValue(selectedRowId, "status") != "new")
			cell(selectedRowId, "status").setValue('changed');
	}
	else {
		var parentIndex = mygrid.haasGetRowSpanStart(selectedRowId);
		var newLinePosition = mygrid.haasGetRowSpanEndIndexLvl2(selectedRowId);
		var parentRowId = mygrid.getRowId(newLinePosition - 1);
		var parent2Index = mygrid.haasGetRowSpanStartLvl2(selectedRowId);
		var endIndex = mygrid.haasGetRowSpanEndIndexLvl2(selectedRowId);
		for ( var index = parent2Index; index < endIndex; index++) {
			var rowId = mygrid.getRowId(index);
			cell(rowId, "status").setValue('deleteWorkArea');
			colorDeletedRow(rowId);
		}  

		var rowData = [ 'Y',
		                cellValue(selectedRowId, "mixtureName"),
		                cellValue(selectedRowId, "area"),
		                cellValue(selectedRowId, "building"),
		                messagesData.all, cellValue(selectedRowId, "mixtureId"),
		                $v("companyId"),
		                $v("facilityId"),
		                cellValue(selectedRowId, "areaId"),
		                cellValue(selectedRowId, "buildingId"),
		                '-1','','','',
		                'new'];
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
		
			if (cellValue(rowId, "status") == "new") {
				if (cellValue(rowId, "areaId") == "-1") {
					alert(formatMessage(messagesData.required, messagesData.area));
					mygrid.selectRowById(rowId);
					return false;
				}
				else if (cellValue(rowId, "areaId").trim().length > 0 && cellValue(rowId, "buildingId") == "") {
					alert(formatMessage(messagesData.required, messagesData.building));
					mygrid.selectRowById(rowId);
					return false;
				}
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
		if (cellValue(mygrid.getRowId(index), "status").substring(0,6) != "delete" && applicationId == cellValue(mygrid.getRowId(index), "applicationId")) { 
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
		if (cellValue(mygrid.getRowId(index), "status").substring(0,6) != "delete" && buildingId == cellValue(mygrid.getRowId(index), "buildingId")) { 
			return true;
		}
	}
	return false;
}


