windowCloseOnEsc = true;

var dhxFreezeWins = null;
var selectedRowId = null;
//var rowIds = new Array();
/*
function loadrowIds() {
	var up = typeBean.rows.length
	for(i=1;i<= up; i++)
		rowIds[""+i] = i;
}  */

function resultOnLoad()
{
  doInitGrid(); 

 /*It is important to call this after all the divs have been turned on or off.*/
 setResultSize();
}

function doInitGrid() {
	beanGrid = new dhtmlXGridObject('typeBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	initGridWithConfig(beanGrid, config, false, true, false);
	if (typeof (jsonMainData) != 'undefined') {
		beanGrid.parse(jsonMainData, "json");
	}

	// set all kinds of event for the grid. refer to http://www.dhtmlx.com
	// beanGrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	beanGrid.attachEvent("onRowSelect", selectRow);
}

function selectRow(rowId,cellInd){
   selectedRowId = rowId;
   beanGrid.selectRowById(rowId);
   if(cellValue(selectedRowId,"status") == 'new')
   		$("deleteSpan").style["display"] = ""; 
   else
   		$("deleteSpan").style["display"] = "none"; 
}

function deleteLine() {
	beanGrid.deleteRow(selectedRowId);
/*	delete( shiptorowids[shiptoSelectedRowId] ) ;
	if( shiptoGrid.getRowsNum() == 0 ) 
		$('newShiptoRemoveLine').style["display"] = "none";
	return ; */
}

function addLine() {
	  var rowId = (new Date()).valueOf();
 	  ind = beanGrid.getRowsNum();
	  
	  count = 0 ;
	  
	  var thisrow = beanGrid.addRow(rowId,"",ind);
//    alertthis = true;
	  beanGrid.cells(rowId, count++).setValue('Y');
	  beanGrid.cells(rowId, count++).setValue('');
	  beanGrid.cells(rowId, count++).setValue('');
	  beanGrid.cells(rowId, count++).setValue('');
	  beanGrid.cells(rowId, count++).setValue('new');

//	  rowIds[""+rowId] = rowId;
	  selectedRowId = rowId;
	  beanGrid.selectRow(beanGrid.getRowIndex(rowId));  
//	  $('newShiptoRemoveLine').style.display="";
//	  typeChanged(rowid,"shipToCountryAbbrev");
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

		var typeName = gridCellValue(beanGrid,rowId, "typeName");
		
		if (typeName.trim() == "") {
			errorMessage += messagesData.type;
			count = 1;
		}

		if (typeName.length > 100) { // Limit the txt field to 200 characters
			errorMessage += "\n" + messagesData.maximum100;
			beanGrid.cellById(rowId, beanGrid.getColIndexById("typeName")).setValue(typeName.substring(0, 100));
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
