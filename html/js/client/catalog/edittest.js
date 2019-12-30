windowCloseOnEsc = true;

var dhxFreezeWins = null;
var selectedRowId = null;

// Allow different permissions for different columns
var multiplePermissions = true;

// Build up the array for the columns which use different permissions
var permissionColumns = new Array();
permissionColumns = {
        "testDesc" : true,
        "criteria" : true
};

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
	beanGrid = new dhtmlXGridObject('testBean');

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
 //  beanGrid.selectRowById(rowId);
   if(cellValue(selectedRowId,"status") == 'new')
   		$("deleteSpan").style["display"] = ""; 
   else
   		$("deleteSpan").style["display"] = "none"; 
}


function deleteLine() {
	beanGrid.deleteRow(selectedRowId);
	$("deleteSpan").style["display"] = "none"; 
}

function addLine() {
	  var rowId = (new Date()).valueOf();
 	  ind = beanGrid.getRowsNum();
	  
	  count = 0 ;
	  
	  var thisrow = beanGrid.addRow(rowId,"",ind);
//    alertthis = true;
	  beanGrid.cells(rowId, count++).setValue('Y');     //row permision
	  beanGrid.cells(rowId, count++).setValue('Y');     //desc permission
	  beanGrid.cells(rowId, count++).setValue('Y');     //criteria permission
	  beanGrid.cells(rowId, count++).setValue('');      //short name
      beanGrid.cells(rowId, count++).setValue('');      //active
      beanGrid.cells(rowId, count++).setValue('');      //default
      beanGrid.cells(rowId, count++).setValue('');      //description
	  beanGrid.cells(rowId, count++).setValue('');      //criteria
	  beanGrid.cells(rowId, count++).setValue('');      //original short name
	  beanGrid.cells(rowId, count++).setValue('');      //original desc
	  beanGrid.cells(rowId, count++).setValue('');      //original criteria
	  beanGrid.cells(rowId, count++).setValue('');      //test ID
	  beanGrid.cells(rowId, count++).setValue('new');   //status

//	  rowIds[""+rowId] = rowId;
	  beanGrid.selectRow(beanGrid.getRowIndex(rowId)); 
	  
	  selectedRowId = rowId;  
	  
	  try{
			$("shortName"+rowId).focus();
	  } catch(ex) {
			setTimeout('$("shortName"+selectedRowId).focus()',100);
	  }  
	  
	  $("deleteSpan").style["display"] = ""; 
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

		var shortName = gridCellValue(beanGrid,rowId, "shortName");
		
		if (shortName.trim() == "") {
			errorMessage += messagesData.shortname+ "\n";
			count = 1;
		}
		
		if('new' == gridCellValue(beanGrid,rowId, "status")) {
			var testDesc = gridCellValue(beanGrid,rowId, "testDesc");
			if (testDesc.trim() == "") {
				errorMessage += messagesData.description+ "\n";
				count = 1;
			}
	
			if (testDesc.length > 500) { // Limit the txt field to 500 characters
				errorMessage += "\n" + messagesData.description + ":" + messagesData.maximumallowed500;
				beanGrid.cellById(rowId, beanGrid.getColIndexById("testDesc")).setValue(testDesc.substring(0, 500));
				count = 1;
			}
			
			var criteria = gridCellValue(beanGrid,rowId, "criteria");
			if (criteria.trim() == "") {
				errorMessage += messagesData.criteria+ "\n";
				count = 1;
			}
	
			if (criteria.length > 64) { // Limit the txt field to 64 characters
				errorMessage += "\n" + messagesData.criteria + ":" + messagesData.maximumallowed64;
				beanGrid.cellById(rowId, beanGrid.getColIndexById("criteria")).setValue(criteria.substring(0, 64));
				count = 1;
			}
		}

		if (count > 0) {
			alert(errorMessage);
			return false;
		}
	
	return true;
}

function changeStatus(rowId) {
	if('new' != gridCellValue(beanGrid,rowId, "status"))
		beanGrid.cellById(rowId, beanGrid.getColIndexById("status")).setValue("updated");
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
