/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;
var dhxWins = null;

function checkAll(checkboxname) {
	var checkall = $("checkAllCounted");
	var rowsNum = mygrid.getRowsNum()*1;	
	var alertCount = 0;

	// Make sure the background render has rendered all rows
	renderAllRows();
	
	if( checkall.checked ) {
		for(var p = 1 ; p < (rowsNum+1) ; p ++) {
			var cid = checkboxname+p;
			if($(cid) != null && !$(cid).disabled && !$(cid).checked) {
				$(cid).checked = true;
				updateHchStatusA(cid);
			}
		}
	}
	else {
		for(var p = 1 ; p < (rowsNum+1) ; p ++) {
			var cid = checkboxname+p;
			if( !$(cid) != null && !$(cid).disabled && $(cid).checked) {
				$(cid).checked = false;
				updateHchStatusA(cid);
			}
		}
	}
	return true;  
}

function myResultOnLoadWithGrid(gridConfig) {

	initGridWithGlobal(gridConfig);

	/* Display the standard footer message */
	displayGridSearchDuration();
	
	/* It is important to call this after all the divs have been turned on
	 * or off.*/
	setResultFrameSize();
	
	if (noCountId) {
		parent.document.getElementById("newCount").style["display"] = "";		
	} else {
		parent.document.getElementById("newCount").style["display"] = "none";
	}	
	var totalLines = document.getElementById("totalLines").value;	
	if (totalLines < 1) {
		parent.document.getElementById("singleUpdateLinks").style["display"] = "none";		
		if (!noCountId && noResultsFound) {
			parent.document.getElementById("mainUpdateLinks").style["display"] = ""; 
			parent.document.getElementById("singleCloseLinks").style.display = "";			
		} else {
			parent.document.getElementById("singleCloseLinks").style.display = "none";			
		}
	}
	else {
		parent.document.getElementById("singleUpdateLinks").style["display"] = "";
		parent.document.getElementById("singleCloseLinks").style["display"] = "";
	}		
}

/*This is called from the main page to set the grid height.
*/
function setGridHeight(resultFrameHeight) {
	try {
		if (haasGrid !=null) {
			var id=haasGrid.entBox.id;
			var griDiv = document.getElementById(id);
			griDiv.style.height = resultFrameHeight-30 + "px";
		}
	}
	catch(ex) {
      //alert("THis means the grid was not initialized");
	}
}

function validateCountedQuantity(rowId,columnId) {
	if (isValidQuantity(rowId, columnId)) {
		// Check the other requirements
		var count = cellValue(rowId,columnId);
		var expected = cellValue(rowId,"expectedQuantity")*1;
		//if (count != expected) {
			cell(rowId, "counted").setValue(true);
		//}	
		
		// Was change greater than 25%		
		if ((expected > 0  && ((count - expected) / expected) * 100 > 25) || expected == 0) {
			if (expected > 0) {
				//alert(formatMessage(messagesData.quantitycounted, count, ((count - expected) / expected) * 100, expected) + "\n" + messagesData.doublecheckquantity);
				alert(formatMessage(messagesData.quantitycounteddifferent, count, expected) + "\n" + messagesData.doublecheckquantity);
			}
			else {
				//alert(formatMessage(messagesData.quantitycounted, count, "100", expected) + "\n" + messagesData.doublecheckquantity);	
				alert(formatMessage(messagesData.quantitycounteddifferent, count, expected) + "\n" + messagesData.doublecheckquantity);
			}
		}
	}
}

// When Actual Count is changed, verify it is float and check Counted if it is
function isValidQuantity(rowId, columnId) {
	var count = cellValue(rowId,columnId);
	if (count != null && (count + "").length > 0) {
		if(!isFloat(count,false) ) {
			// Select the row with the error and grab the cursor
			// there as well
			mygrid.selectRowById(rowId, null, false, false);
			document.getElementById("actualQuantity" + rowId).focus();
			alert( messagesData.validvalues + "\n" + cellValue(rowId, "receiptId") + " - " + messagesData.actualCount );
			return false;
		}
	}
	return true;
	
}

function countedChecked(rowId,columnId) {
	if (!isValidQuantity(rowId, "actualQuantity")) {
		cell(rowId, "counted").setValue(false);
		return false;
	}
	return true;
}

function processCycleCount() {
	if(validationforUpdate()) {
  		document.genericForm.target='';
		document.getElementById('uAction').value = 'save';
	
		parent.showPleaseWait();
		
		if (mygrid != null) {
   			mygrid.parentFormOnSubmit(); 
   			// Call this function to send the
			// data from grid back to the server
   		} 
   	
   		document.genericForm.submit();
	}
}

// validate the whole grid
function validationforUpdate() {
	var totalRows = mygrid.getRowsNum();
 
	for (var rowId = 1; rowId <= totalRows ; rowId++) { 
    		if (!isValidQuantity(rowId, "actualQuantity")) {
 			return false;
		}
    }
    
    return true;
}

function startCycleCount () {
	parent.showPleaseWait(); 
	var now = new Date();
	parent.document.getElementById("startSearchTime").value = now.getTime();
	$('uAction').value = 'startCount';
	document.genericForm.target='resultFrame';
	document.genericForm.submit(); 
}

function cycleCountClose () {		
	parent.showPleaseWait(); 
	$('uAction').value = 'closeCount';
	document.genericForm.target='resultFrame';
	document.genericForm.submit(); 
}

function showWait(message){
	$("transitLabel").innerHTML = message;
	
	var transitDailogWin = $("transitDailogWin");
	transitDailogWin.style.display="";

	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}

	if (!dhxWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);
		dhxWins.window("transitDailogWin").show();
	}
}

function stopShowingWait() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
	return true;
}

