var resizeGridWithWindow = true;
var beanGrid;
var selectedRowId = "";
var hubCarrierWithNew = new Array();

function initializeGrid(){
 	beanGrid = new dhtmlXGridObject('hubTrackingNumbersViewBean');
 	initGridWithConfig(beanGrid,config,-1,true);
 	if( typeof( jsonMainData ) != 'undefined' ) {
   	beanGrid.parse(jsonMainData,"json");
 	}
	beanGrid.attachEvent("onRowSelect",selectRow);
	beanGrid.attachEvent("onRightClick",selectRow);
}

function myOnLoad() {
	//close transit window
	parent.closeTransitWin();
	var totalLines = $("totalLines").value;
	if (totalLines > 0) {
		document.getElementById('hubTrackingNumbersViewBean').style["display"]="";
		initializeGrid();
		//looping thru table to see if any row has new status
		//the reason i starts with 1 is because table row ID starts with 1 not 0 (zero)
		for(var i = 1;i <= beanGrid.getRowsNum();i++){
			if (cellValue(i,"status") == 'NEW') {
				hubCarrierWithNew[hubCarrierWithNew.length] = cellValue(i,"carrierName")+cellValue(i,"hub");
			}
		}

		if(parent.$("hasEditPermission").value == 'true') {
			if (hubCarrierWithNew.length > 0) {
				parent.document.getElementById("updateResultLink").style["display"] = "";
			}else {
				parent.document.getElementById("updateResultLink").style["display"] = "none";
			}
		}

	}else {
		  document.getElementById("hubTrackingNumbersViewBean").style["display"] = "none";
	}

	/*This dislpays our standard footer message*/
	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
} //end of method

function submitUpdate() {
  if (validateForm()) {
	  parent.showTransitWin();
	  $("uAction").value = 'update';
	  beanGrid.parentFormOnSubmit();
	  document.genericForm.submit();
  }else {
	  return false;
  }
} //end of method

function validateForm(){
	var hasSelectedRow = false;
	var startNotInteger = false;
	var endNotInteger = false;
	//var newStartTrackingColl = new Array();
	var errorMsg = "";

	for(var i = 1;i <= beanGrid.getRowsNum();i++){
		if(cellValue(i,"ok")+'' == 'true') {
			//check to make sure user entered an integer for start and end tracking number
			if(!isInteger(cellValue(i,"firstTrackingNumber"))) {
				startNotInteger = true;
				if (errorMsg.length > 1) {
					errorMsg += "\n";
				}
				errorMsg += messagesData.mustBeAnInteger.replace(/[{]0[}]/g,messagesData.firstTrackingNumber);
			}
			if (!isInteger(cellValue(i,"lastTrackingNumber"))) {
				endNotInteger = true;
				if (errorMsg.length > 1) {
					errorMsg += "\n";
				}
				errorMsg += messagesData.mustBeAnInteger.replace(/[{]0[}]/g,messagesData.lastTrackingNumber);
			}
			if (startNotInteger || endNotInteger) {
				beanGrid.selectRow(beanGrid.getRowIndex(i),false,false,true);
				break;
			}

			/*
			var dataKey = cellValue(i,"hub")+cellValue(i,"carrierName");
			//make sure that end tracking no is greater than start tracking no
			if (cellValue(i,"lastTrackingNumber")*1 <= cellValue(i,"firstTrackingNumber")*1) {
				if (errorMsg.length > 1) {
					errorMsg += "\n";
				}
				errorMsg += messagesData.startIsGreatertThanEnd.replace(/[{]0[}]/g,messagesData.firstTrackingNumber).replace(/[{]1[}]/g,messagesData.lastTrackingNumber);
				beanGrid.selectRow(beanGrid.getRowIndex(i),false,false,true);
				break;
			}
			//keeping tracking of all new start tracking number
			newStartTrackingColl[dataKey] = {
				trackingNumber: 	cellValue(i,"firstTrackingNumber"),
				rowid:				i
			};
         */
			hasSelectedRow = true;
		}
	}

	/*
	//making sure that new start tracking number is greater than current end tracking number
	for(var i = 1;i <= beanGrid.getRowsNum();i++){
		if(cellValue(i,"status") == 'CURRENT') {
			var dataKey = cellValue(i,"hub")+cellValue(i,"carrierName");
			if (newStartTrackingColl[dataKey] == null) continue;
			if (newStartTrackingColl[dataKey].trackingNumber*1 <= cellValue(i,"lastTrackingNumber")*1) {
				if (errorMsg.length > 1) {
					errorMsg += "\n";
				}
				errorMsg += messagesData.newHasToBeGreaterThanCurrent.replace(/[{]0[}]/g,messagesData.firstTrackingNumber).replace(/[{]1[}]/g,newStartTrackingColl[dataKey].trackingNumber).replace(/[{]2[}]/g,cellValue(i,"lastTrackingNumber"));
				beanGrid.selectRow(beanGrid.getRowIndex(newStartTrackingColl[dataKey].rowid),false,false,true);
				break;
			}
		}
	}
	*/


	if (errorMsg.length > 1) {
		alert(errorMsg);
		return false;
	}else if (!hasSelectedRow) {
		alert(messagesData.pleaseSelectRowForUpdate);
		return false;
	}else {
		return true;
	}
}

function selectRow() {
	// to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

	if( ee != null ) {
   	if( ee.button != null && ee.button == 2 ) rightClick = true;
   	else if( ee.which == 3  ) rightClick = true;
   }
	beanGrid.selectRowById(rowId0,null,false,false);
	
	selectedRowId = rowId0;

	if( !rightClick ) return;

	if(parent.$("hasEditPermission").value != 'true') return;
	//if right mouse clicked
	var alreadyHasNew = false;
	var currentHubCarrier = cellValue(selectedRowId,"carrierName")+cellValue(selectedRowId,"hub");
	for(var i=0; i < hubCarrierWithNew.length;i++) {
		if (hubCarrierWithNew[i] == currentHubCarrier) {
			alreadyHasNew = true;
			break;
		}
	}
	if (!alreadyHasNew) {
		toggleContextMenu("newTrackingBlock");
	}else {
		toggleContextMenu("empty");
	}
}

function addNewBlock() {
	if(beanGrid == null) {
		return false;
	}

	if( selectedRowId == null || selectedRowId == "") {
		alert(messagesData.noRowSelected);
		return false;
	}

	var ind = beanGrid.getRowsNum();
	var rowid = ind*1+1;
	var selectedIndex = beanGrid.getRowIndex(selectedRowId)*1 + 1;
	var thisrow = beanGrid.addRow(rowid,"",selectedIndex);
	beanGrid.selectRow(beanGrid.getRowIndex(rowid),false,false,true);
	var count = 0;
	beanGrid.cells(rowid,count++).setValue('Y');
	beanGrid.cells(rowid,count++).setValue('');
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("hubName")).getValue());
	beanGrid.cells(rowid,count++).setValue('');
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("carrierName")).getValue());
	beanGrid.cells(rowid,count++).setValue('');
	beanGrid.cells(rowid,count++).setValue('');
	beanGrid.cells(rowid,count++).setValue('');
	beanGrid.cells(rowid,count++).setValue('');
	beanGrid.cells(rowid,count++).setValue(messagesData.newLabel);
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("checkdigitFunction")).getValue());
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("hub")).getValue());
	//add hub carrier to list with new tracking block
	hubCarrierWithNew[hubCarrierWithNew.length] = beanGrid.cells(selectedRowId,beanGrid.getColIndexById("carrierName")).getValue()+
																 beanGrid.cells(selectedRowId,beanGrid.getColIndexById("hub")).getValue();
	parent.document.getElementById("updateResultLink").style["display"] = "";
	//check new row
	$("ok"+rowid).checked = true;
	updateHchA("ok"+rowid);
}