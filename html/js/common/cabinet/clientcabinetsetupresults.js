function myResultOnLoad() {
 	if ($v("totalLines") > 0) {
		parent.$("infoDiv").innerHTML = messagesData.cabinetsForWorkarea+": "+parent.$v("lastSearchApplication");
		parent.$("infoDiv").style["display"] = ""; 
	}

	for (var i = 0; i < newCabinetIds.length; i++) {
		//alert("*"+newCabinetIds[i].value+"*"+newCabinetIds[i].text+"*");
		parent.addNewCabinetToDropdown(newCabinetIds[i].value,newCabinetIds[i].text);
	}
}


function toggleColor(rowId, colId) {
	if (!$("active"+rowId).checked) {
		mygrid.haasSetRowClass(rowId, "grid_lightgray");
	}
	else {
		var rowIndex=mygrid.getRowIndex(rowId);
		mygrid.haasSetRowClass(rowId, rowIndex % 2 == 0 ? "ev_haas" : "odd_haas");
	}
}

function validateForm() {
	for ( var i = 0; i < mygrid.getRowsNum(); i++) {
		var rowid = mygrid.getRowId(i);
		var cabinetName = cellValue(rowid, "cabinetName");
		var touched = cellValue(rowid, "touched"); 
		if (touched && cabinetName == "") {
			mygrid.selectRowById(rowid);
			alert(messagesData.required);
			return false;
		}
	}
	return true;
}
function doUpdate() {
	if( !validateForm() ) return false;
	$('uAction').value = 'update';
	

	
	
	parent.showPleaseWait();
	mygrid.parentFormOnSubmit(); //prepare grid for data sending
	document.clientCabinetSetupForm.submit();
	return false;
}

function addNewRow() {
	var newId = (mygrid.getRowsNum() + 1) + "";
	var newData = new Array();
	var cntr = 0;
	newData[cntr++] = 'Y';
	newData[cntr++] = true;
	newData[cntr++] = defaultInventoryGroup;
	if (inventoryGroup.length == 1) { // Extra column if only one OR no permission
		newData[cntr++] = inventoryGroup[0].text;
	}
	newData[cntr++] = defaultDockId;
	if (locationId.length == 1) { // Extra column if only one
		newData[cntr++] = locationId[0].text;
	}
	newData[cntr++] = '';
	newData[cntr++] = '';
	newData[cntr++] = '';
	newData[cntr++] = '';
	newData[cntr++] = '';
	newData[cntr++] = '';
	newData[cntr++] = '';
	newData[cntr++] = '';
	newData[cntr++] = defaultFacilityId;
	newData[cntr++] = '';
	newData[cntr++] = '-1'; // -1 for cabinetId to indicate new record
	newData[cntr++] = '';
	newData[cntr++] = '';
	newData[cntr++] = true;
	              
	mygrid.addRow(newId,newData);
	mygrid.selectRowById(newId);
}

function rowSelected(rowId) {
	gridCell(mygrid, rowId, "touched").setValue(true);
}