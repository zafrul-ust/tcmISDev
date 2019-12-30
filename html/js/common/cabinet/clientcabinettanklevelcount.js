windowCloseOnEsc = true;
window.onresize = resizeFrames;
var completed;
var errorEncountered;
var windowClose = true;
var selectedRowId = "";


function doUpdate() {
	if (checkIfEmpty()) {
		alert(messagesData.emptygridmsg);
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
	var empty = true;
	var noOfBlanksBeforeDataFound = 0;
	for ( var nrowId = 0; nrowId < rowsNum; nrowId++) {
		var pos = myGrid.getRowId(nrowId);
		if (myGrid.isItemExists(pos)) {
			if (gridCellValue(myGrid,pos,"countDatetime") != "" && gridCellValue(myGrid,pos,"countQuantity") != "") {
				empty = false;
				break;
			} else {
				noOfBlanksBeforeDataFound++;
			}
		}
	}
	if (noOfBlanksBeforeDataFound >0) {
		//alert("Blank rows are present prior to the level data in the grid. Please remove the empty rows to submit.");
		empty = true;		
	} 
	return empty;
}


function validateRows() {
	var rowsNum = myGrid.getRowsNum();
	var dateErrFound = false;
	// This reflects the rowId we put in the JSON data 
	for ( var rowId = 0; rowId < rowsNum; rowId++) {
		var pos = myGrid.getRowId(rowId);
		if (myGrid.isItemExists(pos)) {			
			var countdate = gridCellValue(myGrid,pos,"countDatetime");
			if (countdate == "")
				break;
			if (checkIfDateExists(rowId, countdate)) {
				dateErrFound = true;
			}					
			if (!validateLevelCount(pos)) {
				//Select the failing line				 
				setRowClass(pos,'grid_red'); 
				return false;
			}
			if (!validateLevelFillCount(pos)) {
				//Select the failing line
				setRowClass(pos,'grid_red'); 
				return false;
			}	
		}
	}
	if (dateErrFound) {
		alert(messagesData.duplicatedateerr);
		return false;
	}
	return true;
}

function checkIfDateExists(startingCount, countDate) {
	var rowsNum = myGrid.getRowsNum();
	for ( var nrowId = startingCount+1; nrowId < rowsNum; nrowId++) {
		pos = myGrid.getRowId(nrowId);
		if (myGrid.isItemExists(pos)) {
			if (gridCellValue(myGrid,pos,"countDatetime") == "") {
				break;
			}
			if (countDate == gridCellValue(myGrid,pos,"countDatetime")) {
				myGrid.selectRowById(pos, null, false, false);
				setRowClass(pos,'grid_red');
				return true;
			}		
		}
	}
	return false;
}


function myOnUnload() {	
    if (windowClose) {
        opener.parent.closeTransitWin();
    }
}

function closePopup() {	
	var qtyOnHand = $('lastQtyOnHand').value;	
	opener.tankLevelCountCallback(updateSuccess, showErrorMessage, qtyOnHand);
    try {
        opener.parent.closeTransitWin();
    } catch(e) { opener.closeTransitWin(); }
    windowClose = false;
    window.close();
}

function onRowSelected(rowId,cellInd) {
    gridCell(myGrid,rowId,"updated").setValue("Y");    
    selectedRowId = rowId;
    if (cellInd == 4)
    	populateNewDate(rowId);
}

var monthNameArr = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
var monthArr = {Jan:0, Feb:1, Mar:2, Apr:3, May:4, Jun:5, Jul:6, Aug:7, Sep:8, Oct:9, Nov:10, Dec:11};

function populateNewDate(rowId)  {
	if (myGrid.getRowIndex(rowId) == 0)
		return;	
	//get previous rowindex
	var previousId = myGrid.getRowIndex(rowId)-1;	 
	if (gridCellValue(myGrid,myGrid.getRowId(previousId),"countQuantity") == "") {
		alert(messagesData.validvaluenoblank);		
		myGrid.selectRowById(myGrid.getRowId(previousId), false, false, true);
		return;
	}		
	var currentDate = gridCellValue(myGrid,myGrid.getRowId(previousId),"countDatetime");	
	var newDate = gridCellValue(myGrid,rowId,"countDatetime");	
	if (newDate == "" && currentDate != "") {
		var splitdate = currentDate.split("-");
		var dayAfter = new Date(splitdate[2], monthArr[splitdate[1]], splitdate[0]); //year,month,day
		dayAfter.setDate(dayAfter.getDate()+1);
		var today = new Date();
		var comparetoday = new Date(today.getFullYear(), today.getMonth(),today.getDate());
		if(dayAfter.getTime() > today.getTime()) {	        
	        alert(messagesData.futuredateerror);
	    } else {
	    	var twodigitdate = ("0" + dayAfter.getDate()).slice(-2);
			var nextDayDate = twodigitdate + "-" + monthNameArr[dayAfter.getMonth()] + "-" + dayAfter.getFullYear();
			gridCell(myGrid,rowId,"countDatetime").setValue(nextDayDate);
	    }
	}
}


/* commented out to support the data population on row selection of the current 
 * row rather than onChange of the previous row
function populateNextDate(rowId)  {
	if(validateLevelCount(rowId)) {	
		var currentDate = gridCellValue(myGrid,rowId,"countDatetime");
		if ((myGrid.getRowIndex(rowId)+1) ==  myGrid.getRowsNum()) {// means user reached the last row
			//alert((myGrid.getRowIndex(rowId)+1) + " and " +  myGrid.getRowsNum());
			return true;
		}	
		//while (!myGrid.isItemExists(rowId+1)) //keep searching for the next valid rowid (as there may be deleted rows in between)
		//	rowId = rowId + 1 ;		
		var nextRowId = getNextRowId(rowId);
		var nextDate = gridCellValue(myGrid,nextRowId,"countDatetime");
		if (nextDate == "" && currentDate != "") {
			var splitdate = currentDate.split("-");
			var dayAfter = new Date(splitdate[2], monthArr[splitdate[1]], splitdate[0]); //year,month,day
			dayAfter.setDate(dayAfter.getDate()+1);
			var nextDayDate = dayAfter.getDate() + "-" + monthNameArr[dayAfter.getMonth()] + "-" + dayAfter.getFullYear();
			gridCell(myGrid,nextRowId,"countDatetime").setValue(nextDayDate);
		}
	}
}

function getNextRowId(rowId) {	
	var rowIndex = myGrid.getRowIndex(rowId);
	var nextRowId = myGrid.getRowId(rowIndex+1);
	//alert("rowId = " + rowId + "\nrowIndex = " + myGrid.getRowIndex(rowId) + "\nnextRowId=" + nextRowId +  "\nmyGrid.getRowsNum() " + myGrid.getRowsNum());
	return nextRowId;
}
*/

function validateLevelCount(rowId) {
	myGrid.selectRowById(rowId, false, false, true);
	var countQuantity = gridCellValue(myGrid,rowId,"countQuantity");
	if ( countQuantity == null || countQuantity == "") {
		alert(messagesData.validvaluenoblank);
		gridCell(myGrid,rowId,"countQuantity").setValue("");
		return false;
	} else if (isNaN(countQuantity)) {		
		alert(messagesData.validlevelfield);
		gridCell(myGrid,rowId,"countQuantity").setValue("");	
		return false;
	} 
	populateNewDate(rowId);
	return true;
}

function validateLevelFillCount(rowId) {
	var levelFillQuantity = gridCellValue(myGrid,rowId,"afterReplLevelQty");
	if (isNaN(levelFillQuantity)) {		
		alert(messagesData.validlevelfillfield);
		gridCell(myGrid,rowId,"afterReplLevelQty").setValue("");	
		return false;
	} 
	return true;
}

function myOnLoad(uploadSeq) {
	//alert(uploadSeq);
    popupOnLoadWithGrid(gridConfig);
    if (updateSuccess) {
		alert(messagesData.updateSuccess);
		closePopup();
	}

	if (showUpdateLinks){
		document.getElementById("mainUpdateLinks").style["display"] = "";
	}
	else {
		document.getElementById("mainUpdateLinks").style["display"] = "none";
	}
    //do not add empty lines when there is already some load data entered previously i.e. error scenarios
    if(uploadSeq == null || uploadSeq == undefined) {
    	var iCount = 0;
        while (iCount < 31){
            addNewRow(iCount+1);
            iCount++; 
        }    	
    }
}

function delTankLevelRow() {
	myGrid.deleteRow(selectedRowId);	
}

function onRightClickRemove(rowId) {
	selectedRowId = rowId;
	toggleContextMenu('rightClickTankLevelRemove');
}

function callAddNewRow() {
	var rowsNumId = myGrid.getRowId(myGrid.getRowsNum()-1);
	var lastnumber = gridCellValue(myGrid,rowsNumId,"id");
	addNewRow(Number(lastnumber)+1);
}