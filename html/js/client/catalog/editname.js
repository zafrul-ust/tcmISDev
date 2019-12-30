windowCloseOnEsc = true;

var dhxFreezeWins = null;
var selectedRowId = null;

var multiplePermissions = true;

// Build up the array for the columns which use different permissions
var permissionColumns = new Array();
permissionColumns = {
        "commentId" : true
};

function resultOnLoad()
{
  //document.getElementById("commentBean").style["display"] = ""; 
  doInitGrid(); 
  
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultSize();
}

function doInitGrid() {
	beanGrid = new dhtmlXGridObject('commentBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	initGridWithConfig(beanGrid, config, false, true, true);
	if (typeof (jsonMainData) != 'undefined') {
		beanGrid.parse(jsonMainData, "json");
	}

	// set all kinds of event for the grid. refer to http://www.dhtmlx.com
	// beanGrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	beanGrid.attachEvent("onRowSelect", selectRow);
	
}

function selectRow(rowId,cellInd){
   selectedRowId = rowId;
  
   if(cellValue(rowId,"status") == 'new')
   		$("deleteSpan").style["display"] = ""; 
   else
   		$("deleteSpan").style["display"] = "none";   
}

function deleteLine() {
	beanGrid.deleteRow(selectedRowId);
}

function addLine() {
	  var rowId = (new Date()).valueOf();
 	  ind = beanGrid.getRowsNum();
	  
	  count = 0 ;
	  
	  var thisrow = beanGrid.addRow(rowId,"",ind);
//    alertthis = true;
	  beanGrid.cells(rowId, count++).setValue('Y');
	  beanGrid.cells(rowId, count++).setValue('Y');
	  beanGrid.cells(rowId, count++).setValue('');
	  beanGrid.cells(rowId, count++).setValue('');
	  beanGrid.cells(rowId, count++).setValue('');
	  beanGrid.cells(rowId, count++).setValue('new');

	  selectedRowId = rowId;
	  beanGrid.selectRow(beanGrid.getRowIndex(rowId));  
}

function update() {
	if (validationforUpdate()) {
	
		document.genericForm.target = '';
		document.getElementById('uAction').value = 'update';
	
		showPleaseWait(); // Show "please wait" while updating
	
		if (beanGrid != null)
			beanGrid.parentFormOnSubmit(); // Got to call this function to send the data from grid back to the server
	
		document.genericForm.submit(); // back to server
	}
}

function validationforUpdate() {
	var rowsNum = beanGrid.getRowsNum();

	for ( var p = 0; p < rowsNum; p++) {
		rowId = beanGrid.getRowId(p);
		if (validateLine(rowId) == false) {
			beanGrid.selectRowById(rowId, null, false, false); // Make the selected row fall on the one which does pass the validation
			return false;
		}
	}

	return true;
}

function validateLine(rowId) {
	var errorMessage = messagesData.validvalues + "\n\n";
	var count = 0;

		var commentId = gridCellValue(beanGrid,rowId, "commentId");
		var commentTxt = gridCellValue(beanGrid,rowId, "commentTxt");
		
		if (commentId.trim() == "" && commentTxt.trim() != '' ) {
			errorMessage += messagesData.name;
			count = 1;
		}

		if (commentTxt.trim() == "" && commentId.trim() != '') { 
			errorMessage += "\n" + messagesData.description;
			count = 1;
		} 

		var commentTxt = gridCellValue(beanGrid,rowId, "commentTxt");
		if (commentTxt.length > 200) { // Limit the txt field to 200 characters
			errorMessage += "\n" + messagesData.maximum200;
			beanGrid.cellById(rowId, beanGrid.getColIndexById("commentTxt")).setValue(commentTxt.substring(0, 200));
			count = 1;
		}

		if (count > 0) {
			alert(errorMessage);
			return false;
		}
	
	return true;
}

function initializeDhxWins() {
	if (dhxFreezeWins == null) {
		dhxFreezeWins = new dhtmlXWindows();
		dhxFreezeWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}


function showTransitWin(messageType)
{
	var waitingMsg = messagesData.waitingforinputfrom+"...";
	$("transitLabel").innerHTML = waitingMsg.replace(/[{]0[}]/g,messageType);

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.innerHTML = document.getElementById("transitDailogWinBody").innerHTML;
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxFreezeWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxFreezeWins.createWindow("transitDailogWin",0,0, 400, 200);
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
		transitWin.setModal(true);  // freeze the window here
		dhxFreezeWins.window("transitDailogWin").show();
	}
}

function closeTransitWin() {
	if (dhxFreezeWins != null) {
		if (dhxFreezeWins.window("transitDailogWin")) {
			dhxFreezeWins.window("transitDailogWin").setModal(false);
			dhxFreezeWins.window("transitDailogWin").hide();
		}
	}
}
