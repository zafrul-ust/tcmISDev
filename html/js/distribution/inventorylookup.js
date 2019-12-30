var beangrid;

// Global variable: specially useful for right-click
var selectedRowId = null;

// Set this to be false if you don't want the grid width to resize based on window size.
var resizeGridWithWindow = true;

// This works only for popup windows and IE. Close the window after clicking Esc key
var windowCloseOnEsc = true; 

function resultOnLoad()
{
 try{
	 if (!showUpdateLinks) //Dont show any update links if the user does not have permissions
	 {
	  parent.document.getElementById("updateResultLink").style["display"] = "none";
	 }
	 else 
	 {
	  parent.document.getElementById("updateResultLink").style["display"] = "";
	 }
 }
 catch(ex){}	
	
 totalLines = document.getElementById("totalLines").value;
 
 if (totalLines > 0)
 {
  document.getElementById("logisticsViewBean").style["display"] = "";
  initializeGrid();  
 }  
 else
 {
   document.getElementById("logisticsViewBean").style["display"] = "none";   
 }
 
 try {
	 if ($v("successfullyAdded") == 'Y'){
		 	parent.window.opener.alertSuccess();
		   parent.window.close();
		 }
 } catch(ex) {}
 // clear selectedRow span after re-search
 // parent.$("selectedRow").innerHTML = "";

 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('logisticsViewBean');

	initGridWithConfig(beangrid,config,false,true);
	if( typeof( jsonMainData ) != 'undefined' ) {		
		
		beangrid.parse(jsonMainData,"json");
	}
	
//	beangrid.attachEvent("onRightClick",selectRightclick);
	beangrid.attachEvent("onRowSelect",doOnRowSelected);
}

function doOnRowSelected(rowId,cellInd) {
	selectedRowId = rowId;
}

function returnReceipt() {
	if($v("callAddReceipt") != "Y" && parent.window.opener.addReturnItemLine && validateGrid()) {
	  try {
		for(var i=0;i<beangrid.getRowsNum();i++){
		    rowid = beangrid.getRowId(i);
		    if(cellValue(rowid,'ok') == "true") {
				var r = new itemReceipt(rowid);
		  		parent.window.opener.addReturnItemLine(r); 
		  		beangrid.cells(rowid, beangrid.getColIndexById("qtyShipped")).setValue("");
				beangrid.cells(rowid, beangrid.getColIndexById("ok")).setValue(false);
				hchstatusA["ok"+rowid] = false;
	  		}
	  	  }
		}  
	  catch (ex) {
//	  	parent.window.close();  // If smart rendering occurrs, no need to keep going
	  }	
//	    parent.window.close();
	} 

	if($v("callAddReceipt") == "Y" && validateGrid()) {
	  $("uAction").value = "addReceipts";
	  parent.showPleaseWait();
	  
	  if (beangrid != null) {
	    beangrid.parentFormOnSubmit();
	  }
	  
	  document.genericForm.submit();  
	}
}

function validateGrid() {
  try {
	for(var i=0;i<beangrid.getRowsNum();i++){
		    rowid = beangrid.getRowId(i);
		    if(cellValue(rowid,'ok') == "true") {
		    	if(!$v("callAddReceipt") == "Y"){
		    	if(cellValue(rowid,'quantity')*1 < cellValue(rowid,'qtyShipped')*1) {
		    		alert(messagesData.quantityshippednotgreaterthanquantity);
		    		beangrid.cells(rowid, beangrid.getColIndexById("qtyShipped")).setValue("");
		    		beangrid.selectRowById(rowid,null,false,false);
		    		return false;
		    	}
		    	}
		    	if(!(isInteger(cellValue(rowid,'qtyShipped'),false)) || cellValue(rowid,'qtyShipped')*1 < 1) {
		    		alert(messagesData.integer);
		    		beangrid.cells(rowid, beangrid.getColIndexById("qtyShipped")).setValue("");
		    		beangrid.selectRowById(rowid,null,false,false);
		    		return false;
		    	}
		    	
	  		}
	}  
  }
  catch(ex) {
    return true;
  }
	return true;
}

function itemReceipt(rowid) 
{
    this.itemId = cellValue(rowid,'itemId'); 
	this.itemDesc = cellValue(rowid,'itemDesc');
	this.receiptId = cellValue(rowid,'receiptId');
	this.quantity = cellValue(rowid,'quantity'); 
	this.qtyShipped = cellValue(rowid,'qtyShipped'); 
}
/*
function selectRightclick(rowId,cellInd){
	beangrid.selectRowById(rowId,null,false,false);
	// The right click event needs to call selectRow function.
	doOnRowSelected(rowId,cellInd);
	// Show right-click menu
    toggleContextMenu('rightClickMenu');
}  */
