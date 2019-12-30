windowCloseOnEsc = true;

var selectedId = null;
var selectedDescription = null;
var orderCount = 1;

function resultOnLoadWithGrid(gridConfig)
{
 totalLines = document.getElementById("totalLines").value; 
 if (totalLines > 0)
 {
  document.getElementById("storageAreaBean").style["display"] = ""; 
  /*this is the new part.*/
  initGridWithGlobal(gridConfig); 
 }
 else
 {
   document.getElementById("storageAreaBean").style["display"] = "none";   
 }

 /*This dislpays our standard footer message*/
 displayNoSearchSecDuration();

 /*It is important to call this after all the divs have been turned on or off.*/
 setResultSize();
 
 var idArray=$v("StorageAreaIdString").split("|");
 var rowsNum = beanGrid.getRowsNum();
 for ( var i=0; i < idArray.length; i++) {
  	for ( var rowId = 1; rowId <= rowsNum; rowId++) {
		if (idArray[i] == cellValue(rowId, "storageAreaId")) { 
			var curCheckBox = 'ok' + rowId;
			$(curCheckBox).checked = true;
			updateHchStatusA(curCheckBox);
			beanGrid.cells(rowId, beanGrid.getColIndexById("order")).setValue(orderCount++);
			break;
		}
  	}
 }
 
}

function selectRow(rowId,cellInd){
 
   rowId0 = arguments[0];
   colId0 = arguments[1];
    
   $("selectedStorageAreaSpan").innerHTML = '<a href="#" onclick="selectedStorageArea(); return false;">' + messagesData.returnselectedvalues + '</a> | ';
	  
   selectedId = cellValue(rowId,'storageAreaId');
   selectedDescription = cellValue(rowId,'storageAreaDesc');
  
}

function selectedStorageArea()
{ 
	beanGrid.sortRows(4,"int","asc");
	var idString = '';
	var rowsNum = beanGrid.getRowsNum();
  	for ( var i = 0; i < rowsNum; i++) {
  		rowId = beanGrid.getRowId(i);
		var curCheckBox = 'ok' + rowId;
		
		if ($(curCheckBox) && $(curCheckBox).checked == true) { // Make sure the row has been rendered and the element exists
			if(idString == '') 
				idString = cellValue(rowId, "storageAreaId") + ';' + cellValue(rowId, "storageAreaDesc") + ';' + cellValue(rowId, "facilityId")+ ';' + cellValue(rowId, "areaId")+ ';' + cellValue(rowId, "buildingId" )+ ';' + cellValue(rowId, "floorId") + ';' + cellValue(rowId, "roomId") + ';' + cellValue(rowId, "reportingEntityId")+ ';' + cellValue(rowId, "deptId");
			else
				idString += "|" + cellValue(rowId, "storageAreaId") + ';' + cellValue(rowId, "storageAreaDesc") + ';' + cellValue(rowId, "facilityId")+ ';' + cellValue(rowId, "areaId")+ ';' + cellValue(rowId, "buildingId") + ';' + cellValue(rowId, "floorId") + ';' + cellValue(rowId, "roomId") + ';' + cellValue(rowId, "reportingEntityId")+ ';' + cellValue(rowId, "deptId");
		}
  	}
	
  	if(idString.length == 0) {
  		alert(messagesData.norowselected);
  		return false;
  	}
	
  	window.opener.setStorageAreas(idString);
  	window.close();
}

function setOrder(rowId) {
	beanGrid.cells(rowId, beanGrid.getColIndexById("order")).setValue(orderCount++);
}

function clearAllCheckedBox() {

	var rowsNum = beanGrid.getRowsNum();
	var columnId = beanGrid.getColIndexById('ok');
	
	for ( var rowId = 1; rowId <= rowsNum; rowId++) {
		var curCheckBox = 'ok' + rowId;
//		if ($(curCheckBox)) {  // Make sure the row has been rendered and the element exists
			$(curCheckBox).checked = false;
			updateHchStatusA(curCheckBox);
			beanGrid.cells(rowId, beanGrid.getColIndexById("order")).setValue("");
			orderCount = 1;
/*		}
		else { // The HTML element hasn't been drawn yet, update the JSON data directly
			// Basically we don't need this here because the smart rendering is disable
			beanGrid._haas_json_data.rows[beanGrid.getRowIndex(rowId)].data[columnId] = false;
		}   */
	}
}

