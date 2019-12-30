var beanGrid;

// Set this to be false if you don't want the grid width to resize based on
// window size.
var resizeGridWithWindow = true;

function resultOnLoadWithGrid() {
	try{
		if (!showUpdateLinks) {//Dont show any update links if the user does not have permissions
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		} else {
			parent.document.getElementById("updateResultLink").style["display"] = "";
		}
	} catch(ex) {
	}	
	
	initGridWithGlobal(gridConfig);
	
	totalLines = document.getElementById("totalLines").value;

	if (totalLines > 0) {
		document.getElementById(gridConfig.divName).style["display"] = "";
	} else {
		document.getElementById(gridConfig.divName).style["display"] = "none";   
	}

	displayGridSearchDuration();
 
	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
}

//to perform the check all function in the header.
function checkAll(checkboxname) {
	var checkall = $("chkAllConfirm");
	var rowsNum = beanGrid.getRowsNum(); // Get the total rows of the grid
	var columnId = beanGrid.getColIndexById(checkboxname);

	for ( var rowId = 1; rowId <= rowsNum; rowId++) {
		if(cellValue(rowId, "permission") == 'Y') {
			var curCheckBox = checkboxname + rowId;
			if ($(curCheckBox)) { // Make sure the row has been rendered and the element exists
				$(curCheckBox).checked = checkall.checked;
				updateHchStatusA(curCheckBox);
			} else { // The HTML element hasn't been drawn yet, update the JSON data directly
				setGridCellValue(beanGrid, rowId, columnId, checkall.checked);
			}
		}
	}
		
	return true;
}



function validationforUpdate() {
	var rowsNum = beanGrid.getRowsNum();

    for (var p = 1; p < (rowsNum+1) ; p ++)
    {
    	if (validateLine(p) == false) {
    		beanGrid.selectRowById(p,null,false,false);	// Make the selected row fall on the one which does pass the validation
			return false;
		}
    }

    return true;
}


// validate one line here
function validateLine(rowId) {
	var errorMessage = messagesData.validvalues + "\n\n";
	var count = 0;

	if (cellValue(rowId, "confirm") != "true")
		return true; // If not checked, don't validate
	else {
		var confirmationDate = cellValue(rowId, "confirmationDate");
		if (confirmationDate.trim() == "") {
			errorMessage += messagesData.deliveredDate;
			count++;
		}

		
		var comments = cellValue(rowId, "comments");
		if (comments.length > 2000) { // Limit the txt field to 2000 characters
			errorMessage += "\n" + messagesData.maximum2000;
			beanGrid.cellById(rowId, beanGrid.getColIndexById("comments")).setValue(comments.substring(0, 2000));
			count++;
		}

		if (count > 0) {
			alert(errorMessage);
			return false;
		}
	}
	beanGrid.cells(rowId,beanGrid.getColIndexById("isConfirmed")).setValue('Y');
	return true;
}

function submitUpdate()
{
	 if(validationforUpdate()){
	document.getElementById("uAction").value = 'update';
    
    beanGrid.parentFormOnSubmit();
    document.genericForm.submit();
	parent.showPleaseWait();
  }
	
}
