function submitSearchForm() {
	/*
	 * Make sure to not set the target of the form to anything other than
	 * resultFrame
	 */
	document.genericForm.target = 'resultFrame';
	document.getElementById("uAction").value = 'search';
	// set start search time
	var now = new Date();
	var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	var flag = validateForm();
	if (flag) {
		showPleaseWait();
		return true;
	}
	else {
		return false;
	}
}


var mygrid;

//Set this to be false if you don't want the grid width to resize based on
//window size.
var resizeGridWithWindow = true;


//to perform the check all function in the header.
function checkAll(checkboxname) {
	var checkall = $("chkAllOkDoUpdate");
	var rowsNum = mygrid.getRowsNum(); // Get the total rows of the grid
	var columnId = mygrid.getColIndexById(checkboxname);

	if (checkall.checked) {
		for ( var rowId = 1; rowId <= rowsNum; rowId++) {
			if(cellValue(rowId, "permission") == 'Y') {
				var curCheckBox = checkboxname + rowId;
				if ($(curCheckBox)) { // Make sure the row has been rendered and the element exists
					$(curCheckBox).checked = true;
					updateHchStatusA(curCheckBox);
				}
				else { // The HTML element hasn't been drawn yet, update the JSON data directly
					mygrid._haas_json_data.rows[mygrid.getRowIndex(rowId)].data[columnId] = true;
				}
			}
		}
	}
	else {
		for ( var rowId = 1; rowId <= rowsNum; rowId++) {
			if(cellValue(rowId, "permission") == 'Y') {
				var curCheckBox = checkboxname + rowId;
				if ($(curCheckBox)) {  // Make sure the row has been rendered and the element exists
					$(curCheckBox).checked = false;
					updateHchStatusA(curCheckBox);
				}
				else { // The HTML element hasn't been drawn yet, update the JSON data directly
					mygrid._haas_json_data.rows[mygrid.getRowIndex(rowId)].data[columnId] = false;
				}
			}
		}
	}
	return true;
}

function update() {
	parent.document.getElementById("uAction").value = 'update';	
	document.getElementById('uAction').value = 'update';
	var now = new Date();
	var startSearchTime = parent.document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();	

	parent.showPleaseWait(); // Show "please wait" while updating

	var rowsNum = mygrid.getRowsNum(); // Get the total rows of the grid
	//var columnId = mygrid.getColIndexById('okDoUpdate');
	gridRenderAllRows(mygrid);
	var mrLineItemString = '';
	for ( var rowId = 1; rowId <= rowsNum; rowId++) 
	{
		var curCheckBox = 'okDoUpdate' + rowId;
		if(rowId == 1 && $(curCheckBox).checked)
			mrLineItemString += cellValue(rowId, "prNumber") + '-' + cellValue(rowId, "lineItem");
		else if ($(curCheckBox).checked)
			mrLineItemString += '!!!!!' + cellValue(rowId, "prNumber") + '-' + cellValue(rowId, "lineItem");

	}
	
	parent.opener.submitAribaRepair(mrLineItemString);
	parent.window.close();
	/*if (mygrid != null) {
		// This function prepares the grid dat for submitting top the server
		mygrid.parentFormOnSubmit();
	}
	try{
		parent.opener.closeAirbaRepairWin = false;
		parent.opener.window.close();
	}
	catch(ex){}
	
	document.genericForm.target=parent.window.name;
	document.genericForm.submit(); // Submit the form*/
	
}

function facilityChanged() {
	  var selectedFacility = $("facilityId").value;
	  var idArray = altApplication[selectedFacility];
	  var nameArray = altApplicationDesc[selectedFacility];
	  
	  var inv = $("application");
	  for (var i = inv.length; i > 0; i--) {
	    inv[i] = null;
	  }

	  if( nameArray != null ) {
		  var startingIndex = 0;
		  if (nameArray.length == 0 || nameArray.length > 1) {
		  	 setOption(0,messagesData.myworkareas,"", "application");
			 startingIndex = 1;
		  }
		  for (var i=0; i < nameArray.length; i++) {
		    	setOption(i+startingIndex,nameArray[i],idArray[i], "application");
		  }
	  }else {
	    setOption(0,messagesData.myworkareas,"", "application");
	  }
} //end of method

