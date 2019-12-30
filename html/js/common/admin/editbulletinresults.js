var jsonMainData;
var mygrid;
var showUpdateLink;
var jsonConnectionPoolData;
var dhxWins = null;

//Set this to be false if you don't want the grid width to resize based on
//window size.
var resizeGridWithWindow = true;

// used to initialized grid for result frame onload.
function myResultOnLoadWithGrid(gridConfig)
{
	resultOnLoadWithGrid(gridConfig);

	// always show update links if user has permission to edit
	 if (showUpdateLinks) 
	 {
		 parent.document.getElementById("mainUpdateLinks").style["display"] = "";
	 parent.document.getElementById("updateResultLink").style["display"] = "";
	 }
}

//validate the whole grid
function validationforUpdate() {
	var rowsNum = mygrid.getRowsNum();

	// This reflects the rowId we put in the JSON data 
	for ( var rowId = 1; rowId <= rowsNum; rowId++) {
		if (!validateLine(rowId)) {
			//Select the failing line
			mygrid.selectRowById(rowId, null, false, false); 
			return false;
		}
	}

	return true;
}

// validate one line here
function validateLine(rowId) {	
	var update = cellValue(rowId, "okDoUpdate");
	if (update) {	
		var errorMessage = messagesData.validvalues + "\n\n";
		var count = 0;
		var colCount = 0;
		var isAddNew = cellValue(rowId, "addNew");
		
		// All fields except sort order are required
		
		// Validate sort order is a positive value
		var messageSortOrder = cellValue(rowId,"messageSortOrder");
		if (!isInteger(cellValue(rowId,"messageSortOrder"), true)){
			errorMessage += "\n" + messagesData.messagesortorder;
			count++;
		}
		
		var messageTitle = cellValue(rowId,"messageTitle");
		if(messageTitle.trim() == ""){
			errorMessage += messagesData.messagetitle;
			count++;
		}
		else {
			colCount++;
		}
		
		var messageExpirationDate = cellValue(rowId, "messageExpirationDate");
		if (messageExpirationDate.trim() == "") { 
			errorMessage += "\n" + messagesData.messageexpirationdate;
			count++;
		}
		else {
			colCount++;
		}
		
		var messageText = cellValue(rowId, "messageText");
		if(messageText.trim() == ""){
			errorMessage += "\n" + messagesData.messagetext;
			count++;
		}
		else if (messageText.length > 4000) { // Limit the txt field to 4000 characters
			errorMessage += "\n" + messagesData.maximum4000;
			mygrid.cellById(rowId, mygrid.getColIndexById("messageText")).setValue(messageText.substring(0, 4000));
			colCount++;
			count++;
		}
		else {
			colCount++;
		}	

		if (isAddNew && colCount == 0) {
			// if this is a new line with no columns filled out, then it is not an update line
			mygrid.cellById(rowId, mygrid.getColIndexById("okDoUpdate")).setValue('false');
			return true;
		}
		
		if (count > 0) {
			alert(errorMessage);
			return false;
		}
	}
	return true;
}

function markUpdate(rowId,colId) {
	rowSelected(rowId,colId);
	
	cell(rowId, "okDoUpdate").setValue(true);
}

function updateMessage() {
	if (validationforUpdate()) {
		document.genericForm.target = ''; // Form name "genericForm" comes from struts config file
		$('uAction').value = 'update';		// $('formVariableName') is a utility function that does a document.getElementById('variableName')
							// $v('formVariableName') does the same with document.getElementById('variableName').value

		parent.showPleaseWait(); // Show "please wait" while updating

		if (mygrid != null) {
			// This function prepares the grid data for submitting top the server
			mygrid.parentFormOnSubmit();
		}

		document.genericForm.submit(); // Submit the form
	}
}

function addMessage() {
	// Set company to search parameter
	var rowData = ['Y',true,true,''+$v('companyId'),''+$v('companyId'),'','','','','','','','','']; 
	var rowIndex;
	var rowId;
	if(mygrid == null) {
		// no grid exists, create a new one
		jsonMainData = {rows: [{id: 1, data: rowData}]};
		myResultOnLoadWithGrid(gridConfig);
		rowIndex = 1;
		rowId = 1;
	}
	else {
		rowIndex = mygrid.getRowsNum();
		rowId = rowIndex + 1; 
		var thisrow = mygrid.addRow(rowId, rowData, rowIndex);
	}
	
	mygrid.selectRowById(rowId);
	$("totalLines").value = rowId;
} 

function selectRightClick(rowId,cellId) {
	rowSelected(rowId,cellId);
	var updatePermission = cellValue(rowId,"permission");

	  if('Y' == updatePermission){
	   	toggleContextMenu('rightClickMenu');
	  }
	  else {
		  toggleContextMenu('contextMenu');
	  }
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
		// transitWin.attachEvent("onClose",
		// function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem
		// to work, also hidding buttons.
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

function cloneMessage() {
	$('uAction').value = 'cloneMessage';
	var loc = "clonemessage.do";
	showWait(formatMessage(messagesData.waitingFor, messagesData.cloneMessage));
	openWinGeneric(loc,"clonemessage","650","600","yes");
}

function getConnectionPool(){
	return jsonConnectionPoolData;
}

function getTitle(){
	return getCellValue("messageTitle");
}

function getExpirationDate(){
	return getCellValue("messageExpirationDate");
}

function getMessage(){
	return getCellValue("messageText");
}

function getCell(colId) {
	return gridCell(mygrid,selectedRowId,colId);
}

function rowSelected(rowId, colId) {
	selectedRowId = rowId;
}
function getCellValue(colId) {
	return getCell(colId).getValue();
}
