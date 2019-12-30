var beangrid;

// Global variable: specially useful for right-click
var selectedRowId = null;

// Set this to be false if you don't want the grid width to resize based on window size.
var resizeGridWithWindow = true;

// This works only for popup windows and IE. Close the window after clicking Esc key
var windowCloseOnEsc = true; 

//Allow different permissions for different columns
var multiplePermissions = true;

// Build up the array for the columns which use different permissions
var permissionColumns = new Array();
permissionColumns = {
		"permission": true,
		"pageId": true,
        "question" : true,
        "answer": true
};

function resultOnLoad() {
	try{
		//Dont show any update links if the user does not have permissions
		if (!showUpdateLinks)  {
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		}
		else {
			parent.document.getElementById("updateResultLink").style["display"] = "";
		}
	}
 	catch(ex){
 	}	
	
	document.getElementById("TroubleShootingTblBean").style["display"] = "";
	initializeGrid();
		
    var totalLines = $v("totalLines")*1 - 1;
 	var startSearchTime = parent.window.document.getElementById("startSearchTime");
    var now = new Date();
    var minutes = 0;
    var seconds = 0;
    //the duration is in milliseconds
    var searchDuration = now.getTime()-(startSearchTime.value*1);
    if (searchDuration > (1000*60)) {   //calculating minutes
         minutes = Math.round((searchDuration / (1000*60)));
         var remainder = searchDuration % (1000*60);
         if (remainder > 0) {   //calculating seconds
           seconds = Math.round(remainder / 1000);
         }
    }else {  //calculating seconds
         seconds = Math.round(searchDuration / 1000);
    }
    var footer = parent.document.getElementById("footer");
    if (minutes != 0) {
         footer.innerHTML = messagesData.recordFound+": "+totalLines+" -- "+messagesData.searchDuration+": "+minutes+" "+messagesData.minutes+" "+seconds+" "+messagesData.seconds;
    }else {
         footer.innerHTML = messagesData.recordFound+": "+totalLines+" -- "+messagesData.searchDuration+": "+seconds+" "+messagesData.seconds;
    }
 
	/*It is important to call this after all the divs have been turned on or off.*/
 	setResultFrameSize();
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('TroubleShootingTblBean');

	beangrid.enableMultiline(true);
	beangrid.enableSmartRendering(false);
	
	initGridWithConfig(beangrid,config,false,true,true);
	if( typeof( jsonMainData ) != 'undefined' ) {		
		beangrid.parse(jsonMainData,"json");
	}
	
	//beangrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	//beangrid.attachEvent("onRightClick",selectRightclick);
	//beangrid.attachEvent("onRowSelect",doOnRowSelected);
}

function callValidateLinefunction(rowId) {
	// Validate the line here
	if (cellValue(rowId, "okDoUpdate") == "true")
		validateLine(rowId);
}

//to perform the check all function in the header.
function checkAll(checkboxname) {
	renderAllRows();
	
	var checkall = $("chkAllOkDoUpdate");
	var rowsNum = beangrid.getRowsNum(); // Get the total rows of the
						// grid
	if (checkall.checked) {
		for ( var p = 1; p < (rowsNum * 1 + 1); p++) {
			var cid = checkboxname + p;
			if (!$(cid).disabled && !$(cid).checked) {
				$(cid).checked = true;
				updateHchStatusA(cid); // This function is to
							// update the global
							// variable for
							// hchstatus
			}
		}
	}
	else {
		for ( var p = 1; p < (rowsNum + 1); p++) {
			var cid = checkboxname + p;
			if (!$(cid).disabled && $(cid).checked) {
				$(cid).checked = false;
				updateHchStatusA(cid); // This function is to
							// update the global
							// variable for
							// hchstatus
			}
		}
	}
	return true;
	
}

function submitUpdate() {
	if (validationforUpdate()) {
		document.genericForm.target = '';
		document.getElementById('uAction').value = 'update';

		parent.showPleaseWait(); // Show "please wait" while updating

		if (beangrid != null)
			beangrid.parentFormOnSubmit(); // Got to call this function to send the data from grid back to the server

		document.genericForm.submit(); // back to server
	}
}

//validate the whole grid
function validationforUpdate() {
	var rowsNum = beangrid.getRowsNum();

	for ( var p = 1; p < (rowsNum + 1); p++) {
		if (validateLine(p) == false) {
			beangrid.selectRowById(p, null, false, false); // Make the selected row fall on the one which does pass the validation
			return false;
		}
	}

	return true;
}

/*
 * Grid event OnBeforeSelect function
 * Save the row class of currently 
 * selected row, before class changes.
 */
var saveRowClass = null;
function doOnBeforeSelect(rowId,oldRowId) {
	// set the color for previous row
	if (oldRowId != 0) {
		setRowClass(oldRowId, saveRowClass);		
	}
	saveRowClass = getRowClass(rowId);
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		overrideDefaultSelect(rowId,saveRowClass);
   		
	return true;	
}

function doOnRowSelected(rowId,cellInd) {
	// set the color for selected row
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		setRowClass(rowId,''+saveRowClass+'Selected');
		
	selectedRowId = rowId;
	
	var prNumber = beangrid.cellById(rowId, beangrid.getColIndexById("prNumber")).getValue();
	if (showUpdateLinks)
	{ 
		 parent.$("selectedRow").innerHTML = " | <a href=\"#\" onclick=call('openMr'); return false;>"+messagesData.open+" : "+prNumber+"</a>";
	}	
}
/*
function selectRightclick(rowId,cellInd){
	beangrid.selectRowById(rowId,null,false,false);
	// The right click event needs to call selectRow function.
	doOnRowSelected(rowId,cellInd);
	// Show right-click menu
    toggleContextMenu('rightClickMenu');
}  */

function addNewDatabaseObjectLine() {
    var ind = beangrid.getRowsNum();  
    var rowid = ind*1+1;
	var thisrow = beangrid.addRow(rowid,"",ind);
	beangrid.selectRow(beangrid.getRowIndex(rowid),null,false,false);
	beangrid.cells(rowid,beangrid.getColIndexById("permission")).setValue('Y');
	beangrid.cells(rowid,beangrid.getColIndexById("pageIdPermission")).setValue('Y');
	beangrid.cells(rowid,beangrid.getColIndexById("questionPermission")).setValue('Y');
	beangrid.cells(rowid,beangrid.getColIndexById("answerPermission")).setValue('Y');
	beangrid.cells(rowid,beangrid.getColIndexById("isAddLine")).setValue(true);
	beangrid.cells(rowid,beangrid.getColIndexById("pageId")).setValue('');
	if($v("pageField").length > 0)
		$("pageId"+rowid).value = $v("pageField"); 
	beangrid.cells(rowid,beangrid.getColIndexById("okDoUpdate")).setValue(true);
	hchstatusA["okDoUpdate"+rowid] = true;
	beangrid.cells(rowid,beangrid.getColIndexById("question")).setValue('');	
	beangrid.cells(rowid,beangrid.getColIndexById("answer")).setValue('');
	beangrid.scrollPage(thisrow.offsetTop);
	
}


//validate one line here
function validateLine(rowId) {
	var errorMessage = messagesData.validvalues + "";
	var count = 0;

	if (cellValue(rowId, "okDoUpdate") != "true")
		return true; // If not checked, don't validate
	else {
		var question = cellValue(rowId, "question");
		if (question.trim() == "" ) {
			errorMessage += "\n"+messagesData.question;
			count = 1;
		}
		
		var answer = cellValue(rowId, "answer");
		if (answer.trim() == "") {
			errorMessage += "\n"+messagesData.answer;
			count = 1;
		}
		
		if (question.length > 4000) { // Limit the txt field to 4000 characters
			errorMessage += "\n\n" + messagesData.question + " : " +messagesData.maximum4000;
			beangrid.cellById(rowId, beangrid.getColIndexById("question")).setValue(question.substring(0, 4000));
			count = 1;
		}
		
		if (answer.length > 4000) { // Limit the txt field to 4000 characters
			errorMessage += "\n\n" + messagesData.answer+ " : " +messagesData.maximum4000;
			beangrid.cellById(rowId, beangrid.getColIndexById("answer")).setValue(question.substring(0, 4000));
			count = 1;
		}

		if (count > 0) {
			alert(errorMessage);
			$("okDoUpdate" + rowId).checked = false;
			updateHchStatusA("okDoUpdate" + rowId);
			return false;
		}  
	}
	return true;
}
