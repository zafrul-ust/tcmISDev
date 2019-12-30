var mygrid;

// Global variable: specially useful for right-click
var selectedRowId = null;

// Set this to be false if you don't want the grid width to resize based on
// window size.
var resizeGridWithWindow = true;

// Allow different permissions for different columns
var multiplePermissions = true;

// Build up the array for the columns which use different permissions
var permissionColumns = new Array();
permissionColumns = {
        "transferDate" : true
};

var showGrid = false;

function resultOnLoad() {
	try {
		if (!showUpdateLinks) 
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		else
			parent.document.getElementById("updateResultLink").style["display"] = "";
	}
	catch (ex) {
	}
	
	var totalLines = document.getElementById("totalLines").value;
	if(totalLines > 0*1 && $('sourceInventoryGroup') != null && $v('sourceInventoryGroup') != null && $v('sourceInventoryGroup').length > 0){
		parent.document.getElementById("addItemSpan1").style["display"] = "";
		parent.document.getElementById("addItemSpan2").style["display"] = "none";
		parent.document.getElementById("sourceInventoryGroup").value = $v('sourceInventoryGroup');
	}
	else if(totalLines == 0 && $('sourceInventoryGroup') != null && $v('sourceInventoryGroup') != null && $v('sourceInventoryGroup').length > 0){
		parent.document.getElementById("addItemSpan1").style["display"] = "none";
		parent.document.getElementById("addItemSpan2").style["display"] = "";
		parent.document.getElementById("sourceInventoryGroup").value = $v('sourceInventoryGroup');
	}
	else {
		parent.document.getElementById("addItemSpan1").style["display"] = "none";
		parent.document.getElementById("addItemSpan2").style["display"] = "none";
	}

	if (totalLines > 0) {
		// make result area visible if data exist
		document.getElementById("transferRequestInputBean").style["display"] = "";
		// build the grid for display
		doInitGrid();
	}
	else {
		document.getElementById("transferRequestInputBean").style["display"] = "none";
	}

	/* This displays our standard footer message */
	displayGridSearchDuration();

	setResultFrameSize();

	if(showResults)
		setTimeout("parent.createResultsWindow()",60);;
}

function doInitGrid() {
	mygrid = new dhtmlXGridObject('transferRequestInputBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	// if rowSpan == true, sorting and smart rendering won't work; if
	// rowSpan == false, sorting and smart rendering will work
	// rowSpan == -1 is recommended for the page with update function
	// -1 is for disable rowSpan and smart rendering, but sorting still
	// works; false will disable rowSpan and sorting but smart rendering is
	// enabled
	// set submitDefault to true: Send the data to the server
	// singleClickEdit: this is for type:"txt",
	initGridWithConfig(mygrid, searchConfig, false, true, true);
	if (typeof (jsonMainData) != 'undefined') {
		mygrid.parse(jsonMainData, "json");
	}

	// set all kinds of event for the grid. refer to http://www.dhtmlx.com
	// for more events
	// mygrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	mygrid.attachEvent("onRowSelect", selectRow);
	mygrid.attachEvent("onRightClick", selectRightclick);
}

function selectRightclick(rowId, cellInd) {
	selectRow(rowId, cellInd); // The right click event needs to call
					// selectRow function.
}

function selectRow(rowId, cellInd) {
	selectedRowId = rowId; 
}
/*
function initPopupGrid() {
	transferResultGrid = new dhtmlXGridObject('successMessageArea');

	initGridWithConfig(transferResultGrid, tConfig, false, false, false);
	if (typeof (tjsonMainData) != 'undefined') {
		transferResultGrid.parse(tjsonMainData, "json");
	}
	
	transferResultGrid.attachEvent("onRowSelect", selectResultRow);
}

function selectResultRow(rowId,cellInd) {alert(1);
	var opsEntityId = window.frames['resultFrame'].$v('opsEntityId'+selectedRowId);
 
	if (opsEntityId == 'HAASPSUSA' || opsEntityId == 'WESCOTCMUK' || opsEntityId == 'WESCOSCMUK')
		toggleContextMenu('rightClickMenu');  
	
	selectedRowId = rowId;
}  */

function buildGridIgValudset(hub) {
//   	var opsid = $v('opsEntityId');
   	var newInvArray = new Array();
		
   	if( !hub ) return newInvArray;
	 
   	for( j = 0;j< hubig.length;j++ ){
   		var igcoll = hubig[j].coll;
   		if( hubig[j].id == hub ) {
	   		for( k=0;k< igcoll.length;k++ ){
	   				newInvArray[newInvArray.length] = {text:igcoll[k].name,value:igcoll[k].id};
	   		}
   		}
   	}	
   
   	return newInvArray;
}

function toHubChanged(rowId,columnId) {

	 var selectedHub = cellValue(rowId, "toBranchPlant");
	 var toInventoryGroup = cellValue(rowId, "toInventoryGroup");
	 var selectedOps = gridCellValue(haasGrid,rowId,columnId);
  
	  var inv = $("toInventoryGroup"+rowId);
	  for (var i = inv.length; i > 0; i--) {
	    inv[i] = null;
	  }
	  var selectedIndex = 0 ;

	  var invarr = buildGridIgValudset(selectedHub);
	  if(invarr.length == 0) {
	    setOption(0,"","", "toInventoryGroup"+rowId)
	  }
	  
	  var originalInventoryGroup = cellValue(rowId, "inventoryGroup");
	  for (var i=0; i < invarr.length; i++) {
	    setOption(i,invarr[i].text,invarr[i].value, "toInventoryGroup"+rowId);
	    if( invarr[i].value == originalInventoryGroup ) selectedIndex = i;
	  }
	  inv.selectedIndex = selectedIndex;
	  mygrid.cellById(rowId, mygrid.getColIndexById("toInventoryGroupName")).setValue($("toInventoryGroup"+rowId).options[$("toInventoryGroup"+rowId).selectedIndex].text);
}

function toInventoryGroupChanged(rowId,columnId) {

	 var toInventoryGroup = cellValue(rowId, "toInventoryGroup");
	 
	 var toInventoryGroupName = $("toInventoryGroup"+rowId).options[$("toInventoryGroup"+rowId).selectedIndex].text;
	 mygrid.cellById(rowId, mygrid.getColIndexById("toInventoryGroupName")).setValue(toInventoryGroupName);

}

function submitTransfer() {
	if (validationforUpdate()) {
		document.genericForm.target = '';
		document.getElementById('uAction').value = 'transfer';

		parent.showPleaseWait(); 

		if (mygrid != null)
			mygrid.parentFormOnSubmit(); 

		document.genericForm.submit(); // back to server
	}
}

var flag = null;
function validationforUpdate() {
	var rowsNum = mygrid.getRowsNum();
	flag = false;
	
	for ( var p = 1; p < (rowsNum + 1); p++) {
		if (mygrid.haasRowIsRendered(p) && validateLine(p) == false) {
			mygrid.selectRowById(p, null, false, false); 
			return false;
		}
	}

	if(!flag) {
		alert(messagesData.noTransfer); 
		return false;
	}

	return true;
}

// validate one line here
function validateLine(rowId) {
	var errorMessage = messagesData.validvalues + "\n\n";
	var count = 0;
	
	if (cellValue(rowId, "ok") != "true")
		return true; // If not checked, don't validate
	else {

		var transferQuantity = cellValue(rowId, "transferQuantity");
		if (!isPositiveInteger(transferQuantity, false)) { 
			errorMessage += "\n" + messagesData.transferQuantityInteger;
			count = 1;
		}
		
		var quantity = cellValue(rowId, "quantity");
		if (transferQuantity*1 < transferQuantity*1) {
			errorMessage += messagesData.Quantitytosendnotgreaterthanavailable;
			count = 1;
		}

		var comments = cellValue(rowId, "comments");
		if (comments.length > 4000) { 
			errorMessage += "\n" + messagesData.maximum4000;
			mygrid.cellById(rowId, mygrid.getColIndexById("comments")).setValue(comments.substring(0, 4000));
			count = 1;
		}

		if (count > 0) {
			alert(errorMessage);
			$("ok" + rowId).checked = false;
			updateHchStatusA("ok" + rowId);
			return false;
		}
		else {
			flag = true;
			return true;
		}
	}
}



