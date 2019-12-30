var windowCloseOnEsc = true;
window.onresize = resizeFrames;
var completed;
var errorEncountered;
var windowClose = true;
var selectedRowId = "";


function doUpdate() {
	if (checkIfEmpty()) {
		//alert(messagesData.emptygridmsg);
		return;
	}
	if (!checkForDuplicateCountValues()) {		
		return;
	}
	if (validateRows()) {
		$('uAction').value = 'update';
	    myGrid.parentFormOnSubmit(); //prepare grid for data sending
	    windowClose = false;	
	    document.genericForm.submit();
	    return true;
	}
	return;
}

function checkIfEmpty() {
	var rowsNum = myGrid.getRowsNum();
	var empty = false;
	var noOfBlanksBeforeDataFound = 0;
	for ( var nrowId = 0; nrowId < rowsNum; nrowId++) {
		var pos = myGrid.getRowId(nrowId);
		if (myGrid.isItemExists(pos)) {			
			if (gridCellValue(myGrid,pos,"countQuantity") != "" && gridCellValue(myGrid,pos,"inventoryQuantity") == "") {
				myGrid.selectRowById(pos, null, false, false);
				setRowClass(pos,'grid_red');
				empty = true;				
			} else if (gridCellValue(myGrid,pos,"countQuantity") == "" && gridCellValue(myGrid,pos,"inventoryQuantity") != "") {
				myGrid.selectRowById(pos, null, false, false);
				setRowClass(pos,'grid_red');
				empty = true;				
			}						
		}
	}
	if (empty) {
		alert(messagesData.missingdata);
	}
	return empty;
}


function checkForDuplicateCountValues() {
	var rowsNum = myGrid.getRowsNum();
	var countErrFound = false;
	// This reflects the rowId we put in the JSON data 
	for ( var rowId = 0; rowId < rowsNum; rowId++) {
		var pos = myGrid.getRowId(rowId);
		if (myGrid.isItemExists(pos)) {
			var countValues = gridCellValue(myGrid,pos,"countQuantity");
			var inventoryValues = gridCellValue(myGrid,pos,"inventoryQuantity");
			if (checkIfCountExists(rowId, countValues, inventoryValues)) {
				countErrFound = true;
			}						
		}
	}
	if (countErrFound) {
		alert(messagesData.duplicatecounterr);
		return false;
	}
	return true;
}

function validateRows() {
	var rowsNum = myGrid.getRowsNum();
	var countErrFound = false;
	// This reflects the rowId we put in the JSON data 
	for ( var rowId = 0; rowId < rowsNum; rowId++) {
		var pos = myGrid.getRowId(rowId);
		if (myGrid.isItemExists(pos)) {
			var countValues = gridCellValue(myGrid,pos,"countQuantity");
			var inventoryValues = gridCellValue(myGrid,pos,"inventoryQuantity");
			if (countValues == "" || inventoryValues == "")
				break;
			if (checkForSeqQty(rowId,countValues,inventoryValues)) {
				countErrFound = true
			}
		}
	}
	if (countErrFound) {
		alert(messagesData.invalidsequencedata);
		return false;
	}
	return true;
}

function checkIfCountExists(startingCount, countValues, invCountValues) {
	var rowsNum = myGrid.getRowsNum();
	for ( var nrowId = startingCount+1; nrowId < rowsNum; nrowId++) {
		pos = myGrid.getRowId(nrowId);
		if (myGrid.isItemExists(pos)) {
			if (gridCellValue(myGrid,pos,"countQuantity") == "") {
				break;
			}
			if (countValues == gridCellValue(myGrid,pos,"countQuantity")) {
				myGrid.selectRowById(pos, null, false, false);
				setRowClass(pos,'grid_red');
				return true;
			}
			if (invCountValues == gridCellValue(myGrid,pos,"inventoryQuantity")) {
				myGrid.selectRowById(pos, null, false, false);
				setRowClass(pos,'grid_red');
				return true;
			}
		}
	}
	return false;
}

function checkForSeqQty(indexPos, countValues, inventoryValues) {
	var rowsNum = myGrid.getRowsNum();
	var matchCountPos = 0;
	var min = [];
	var max = [];		
	var maxindex = 0;
	var minindex = 0;

	for ( var nrowId = 0; nrowId < rowsNum; nrowId++) {
		if (indexPos == nrowId)
			break;		
		pos = myGrid.getRowId(nrowId);
		if (myGrid.isItemExists(pos)) {
			var count = gridCellValue(myGrid,pos,"countQuantity");
			var inventory = gridCellValue(myGrid,pos,"inventoryQuantity");
			if (count == "" || inventory == "") {
				break;
			}
			if (Number(count) > Number(countValues)) {   //store all the numbers and indices greater than countvalues into a max 2-D array 
				var index = maxindex++;
				max[index] = [count, nrowId];
			}				
			if (Number(count) < Number(countValues)) {   //store all the numbers and indices less than countvalues into a min 2-D array
				var index = minindex++;
				min[index] = [count, nrowId];
			}
		}
	}
    //sort the arrays based on the count qty i.e the first col in the 2D array.
	max.sort(sortFunction);
	min.sort(sortFunction);
	var second = max[0,0];                          // get the first element from the max list i.e. the lowest count qty value from the max array
	var first = min[min.length-1,min.length-1];     // get the last element from the min array i.e. the largesy count qty from the min values
	
	// the countvalues passed to this method should lie between lowest max value and lasrget min value.
	// retrieve their positions or indices from the second column of the 2d array.
	// retrieve the inventory values for the above positions i.e. maxinventory (upper limit) and mininventory (lower limit)
	// compare the inventoryValues passed to this method using the maxinventory and mininventory values found using the above method.
	// do the comparisons and return the boolean flag.  
	if (typeof first !== 'undefined' && first && typeof second !== 'undefined' && second ) {
		
		var firstpos = first[1];
		var secondpos = second[1];		
		var lowestinv = gridCellValue(myGrid,myGrid.getRowId(firstpos),"inventoryQuantity");
		var highinv = gridCellValue(myGrid,myGrid.getRowId(secondpos),"inventoryQuantity");
		//alert(" countValues = " + countValues + " indexPos = " + indexPos + " and rowid is - " + myGrid.getRowId(indexPos) + " > " + max + " and " + min + " and values are - " + lowestinv + " - " + inventoryValues  + " - " +  highinv  + " is - " + (Number(inventoryValues) > Number(lowestinv) && Number(inventoryValues) < Number(highinv)));
		if (Number(inventoryValues) > Number(lowestinv) && Number(inventoryValues) < Number(highinv) ) {
			
		} else {			
			alert(messagesData.invalidinventorymsg.replace('{0}',lowestinv).replace('{1}',highinv).replace('{2}',countValues));
			myGrid.selectRowById(myGrid.getRowId(indexPos), null, false, false);
			setRowClass(myGrid.getRowId(indexPos),'grid_red');
			return true;
		}		
	} else if (typeof first !== 'undefined' && first ) {
		var firstpos = first[1];
		var lowestinv = gridCellValue(myGrid,myGrid.getRowId(firstpos),"inventoryQuantity");
		//alert(" countValues = " + countValues + " indexPos = " + indexPos + " and rowid is - " + myGrid.getRowId(indexPos) + " > " + max + " and " + min + " and values are - " + lowestinv + " - " + inventoryValues  + " is - " + (inventoryValues > lowestinv ));		
		if (Number(inventoryValues) > Number(lowestinv) ) {
			
		} else {
			alert(messagesData.invalidmaxinventorymsg.replace('{0}',lowestinv).replace('{1}',countValues));
			myGrid.selectRowById(myGrid.getRowId(indexPos), null, false, false);
			setRowClass(myGrid.getRowId(indexPos),'grid_red');
			return true;
		}	
	} else if (typeof second !== 'undefined' && second  ) {
		var secondpos = second[1];		
		var highinv = gridCellValue(myGrid,myGrid.getRowId(secondpos),"inventoryQuantity");
		//alert(" countValues = " + countValues + " indexPos = " + indexPos + " and rowid is - " + myGrid.getRowId(indexPos) + " > " + max + " and " + min + " and values are - " + highinv + " - " + inventoryValues  + " is - " + (inventoryValues < highinv ));
		if (Number(inventoryValues) < Number(highinv) ) {
			
		} else {			
			alert(messagesData.invalidmininventorymsg.replace('{0}',highinv).replace('{1}',countValues));
			myGrid.selectRowById(myGrid.getRowId(indexPos), null, false, false);
			setRowClass(myGrid.getRowId(indexPos),'grid_red');
			return true;
		}
	}
	return false;	
}
//sort function used to the sort the 2D array. use the number function to sort numbers.
function sortFunction(a, b) {
    if (a[0] === b[0]) {
        return 0;
    }
    else {
        return (Number(a[0]) < Number(b[0])) ? -1 : 1;
    }
}

function myOnUnload() {	
    if (windowClose) {
        opener.parent.closeTransitWin();
    }
}

function closePopup() {	
    try {
        opener.parent.closeTransitWin();
    } catch(e) { opener.closeTransitWin(); }
    windowClose = false;
    window.close();
}

function onRowSelected(rowId,cellInd) {
    selectedRowId = rowId;    
}

function toggleStatus(rowId, colId) {
    var checked = $("delete" + rowId).checked;
    gridCell(myGrid,rowId,"updated").setValue("Y");
    if (!checked) {
        //myGrid.haasSetRowClass(rowId, "grid_lightgray");
    } else {
        var rowIndex=myGrid.getRowIndex(rowId);
        myGrid.haasSetRowClass(rowId, rowIndex % 2 == 0 ? "ev_haas" : "odd_haas");
    }    
}

function validateCount(rowId) {
	var countQuantity = gridCellValue(myGrid,rowId,"countQuantity");
	if ( countQuantity == null || countQuantity == "") {
		alert(messagesData.countfieldoblank);
		gridCell(myGrid,rowId,"countQuantity").setValue("");
		return false;
	} else if (isNaN(countQuantity)) {		
		alert(messagesData.validcountvalue);
		gridCell(myGrid,rowId,"countQuantity").setValue("");	
		return false;
	}

	gridCell(myGrid,rowId,"updated").setValue("Y");
	/*
	if (gridCellValue(myGrid,rowId,"newrow") != "Y" ) {
		if (myGrid.getRowIndex(rowId) == 0)
			return true;	
		//get prev row id
		var previousId = myGrid.getRowIndex(rowId)-1;
		var prevCountQty = gridCellValue(myGrid,myGrid.getRowId(previousId),"countQuantity");		
		//get next row id
		var nextId = myGrid.getRowIndex(rowId)+1;	
		if (!myGrid.isItemExists(myGrid.getRowId(nextId)))
			return true;
		var nextCountQty = gridCellValue(myGrid,myGrid.getRowId(nextId),"countQuantity");
		
		if (Number(countQuantity) > Number(prevCountQty) && Number(countQuantity) < Number(nextCountQty)) {
			return true;
		} else {
			alert ("Count quantity should be greater than " + Number(prevCountQty) + " and less than " + Number(nextCountQty));
			return false;
		}	
	}
	*/
	return true;
}

function validateInventoryCount(rowId) {
	var inventoryQuantity = gridCellValue(myGrid,rowId,"inventoryQuantity");
	if (inventoryQuantity == null || inventoryQuantity == "") {		
		alert(messagesData.countfieldoblank);
		gridCell(myGrid,rowId,"inventoryQuantity").setValue("");	
		return false;
	} else if (isNaN(inventoryQuantity)) {		
		alert(messagesData.validcountvalue);
		gridCell(myGrid,rowId,"inventoryQuantity").setValue("");	
		return false;
	} else if (Number(inventoryQuantity) < 0) {
		alert(messagesData.positiveinventoryvalue);
		gridCell(myGrid,rowId,"inventoryQuantity").setValue("");	
		return false;
	}
	
	gridCell(myGrid,rowId,"updated").setValue("Y");
	/*
	if (gridCellValue(myGrid,rowId,"newrow") != "Y" ) {
		if (myGrid.getRowIndex(rowId) == 0)
			return true;
		//get prev row id
		var previousId = myGrid.getRowIndex(rowId)-1;
		var prevInvQty = gridCellValue(myGrid,myGrid.getRowId(previousId),"inventoryQuantity");
		
		//get next row id
		var nextId = myGrid.getRowIndex(rowId)+1;	
		if (!myGrid.isItemExists(myGrid.getRowId(nextId)))
			return true;
		var nextInvQty = gridCellValue(myGrid,myGrid.getRowId(nextId),"inventoryQuantity");
		
		if (Number(inventoryQuantity) > Number(prevInvQty) && Number(inventoryQuantity) < Number(nextInvQty)) {
			return true;
		} else {
			alert ("Inventory quantity should be greater than " + Number(prevInvQty) + " and less than " + Number(nextInvQty));
			return false;
		}
	}	
	*/
	
	return true;
}

function myOnLoad() {
    popupOnLoadWithGrid(gridConfig);
    
    if (updateSuccess) {
		alert(messagesData.updateSuccess);
		//closePopup();
	}

	if (showUpdateLinks){
		document.getElementById("mainUpdateLinks").style["display"] = "";
	}
	else {
		document.getElementById("mainUpdateLinks").style["display"] = "none";
	}
    //add 1 empty line when there is already some load data entered previously 
   	var iCount = myGrid.getRowsNum();
   	if (gridEmpty){
   		alert(messagesData.noupdate);
   	} else  if (!showErrorMessage) {
   		if (iCount == 0){       
   	    	addNewRow();
   	    	addNewRow();
   	   } else {
   		   addNewRow();
   	   }	
   	}    
}

function delInterpolationRow() {
	myGrid.deleteRow(selectedRowId);	
}

function onRightClickRemove(rowId) {
	selectedRowId = rowId;
	toggleContextMenu('rightClickInterpolationRemove');
}
