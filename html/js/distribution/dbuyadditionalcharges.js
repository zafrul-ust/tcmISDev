var beangrid;
/*Set this to be false if you don't want the grid width to resize based on window size.
* You will also need to set the resultsMaskTable width appropriately in the JSP.*/
var resizeGridWithWindow = true;
windowCloseOnEsc = true;

var returnSelectedValue=null;
var returnSelectedId=null;

/*This function is called onload from the page*/
function myResultOnload()
{
	/*try
 {
 if (!showUpdateLinks) Dont show any update links if the user does not have permissions
 {
  document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  document.getElementById("updateResultLink").style["display"] = "";
 }
 }
 catch(ex)
 {}*/

/*If there is data to be shown initialize the grid*/
 var totalLines = document.getElementById("totalLines").value;
 if (totalLines >= 0)
 {
  document.getElementById("dbuyAdditionalCharges").style["display"] = "";
  initializeGrid();  
 }
 else
 {
   document.getElementById("dbuyAdditionalCharges").style["display"] = "none";
 }

 /*if (valueElementId.length>0 && displayElementId.length>0 )
 {
  document.getElementById("selectedRow").innerHTML="";
 }*/

/*This displays our standard footer message*/
displayNoSearchSecDuration();

 /*It is important to call this after all the divs have been turned on or off.
 * This sets all sizes to be a good fit on the screen.*/
 setResultSize();

 
 
}
 
 
function initializeGrid(){
	 beangrid = new dhtmlXGridObject('dbuyAdditionalCharges');

	 initGridWithConfig(beangrid,config,false,true);
	 if( typeof( jsonMainData ) != 'undefined' ) {
	 beangrid.parse(jsonMainData,"json");

	 }
	 beangrid.attachEvent("onRowSelect",selectRow);
	// beangrid.attachEvent("onRightClick",selectRightclick);
	}

var selectedRowId = null;

function selectRow(rowId,cellInd) {
	selectedRowId = rowId;
} 


function adddbuycharge(dbuychargeinfo) {
	
	$("dbuyAdditionalCharges").style["display"] = "";

	   if(beangrid == null) {
	     initializeGrid(); 
	   }  
	   
	   var ind = beangrid.getRowsNum();  
	    var rowid = ind*1+1;
		var thisrow = beangrid.addRow(rowid,"",ind);
		beangrid.selectRow(beangrid.getRowIndex(rowid),null,false,false);
	 
	    
		beangrid.cells(rowid,beangrid.getColIndexById("permission")).setValue('Y');
		beangrid.cells(rowid,beangrid.getColIndexById("addChargeItemId")).setValue(dbuychargeinfo.itemId);
		beangrid.cells(rowid,beangrid.getColIndexById("itemDesc")).setValue(dbuychargeinfo.itemDesc);
		beangrid.cells(rowid,beangrid.getColIndexById("okDoUpdate")).setValue(false);
		beangrid.cells(rowid,beangrid.getColIndexById("unitPrice")).setValue('');
		beangrid.cells(rowid,beangrid.getColIndexById("dbuyContractId")).setValue(dbuychargeinfo.dbuyContractId);
		beangrid.cells(rowid,beangrid.getColIndexById("isNewLine")).setValue(true);
		beangrid.scrollPage(thisrow.offsetTop);
	   
	 }

function validationforUpdate() {
	var rowsNum = beangrid.getRowsNum();

	for ( var p = 1; p < (rowsNum + 1); p++) {
		if (validateLine(p) == false) {
			beangrid.selectRowById(p, null, false, false); 
			return false;
		}
	}

	return true;
}

function validateLine(rowId) {
	var errorMessage = messagesData.validvalues + "\n\n";
	var count = 0;
	var unitPrice = cellValue(rowId, "unitPrice");
	if (cellValue(rowId, "okDoUpdate") != true){
		if ((unitPrice.length != 0 ) && (!(isFloat(unitPrice)))) {
			errorMessage += "\n"+messagesData.unitprice;
			alert(errorMessage);
			return false;
		}
		return true; // If not checked, don't validate
	}	
	else {
		
		if ((unitPrice.length != 0 ) && (!(isFloat(unitPrice)))) {
			errorMessage += "\n"+messagesData.unitprice;
			count = 1;
		}
		if ((unitPrice.length == 0)) {
			errorMessage += "\n"+messagesData.unitprice;
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

function validateUpdateForm(numberOfRows) {
	var errorMessage = messagesData.validvalues+"\n";;
	var errorCount = 0;
	  var flag = true;
	  var selected = false;
	   if(numberOfRows == 0){
		   selected = true; 
	   }
	  if(numberOfRows != null) {
	    for(var i=1; i<=numberOfRows; i++) {
	       var checked = false;
		  try {
			  checked = cellValue(i,'okDoUpdate');
		  } catch(ex){}
		  if( !checked ) continue;
		  selected = true;
		  var found = false;
		  var unitPrice = cellValue(i,"unitPrice");
		 alert(unitPrice);
		  if ((unitPrice.length != 0 ) && (!(isFloat(unitPrice))))
		   {
			  found = true;
		   } 
	    }
	  }
	    if( found ) {
	    	 	errorMessage += messagesData.unitprice;
			  errorCount = 1;
		  } 
	   if( !selected ) {
		  errorMessage +=  messagesData.norowselected;
		    errorCount = 1;
	     }
	  if(errorCount >0 ) {
	  	   alert(errorMessage);
	  	  
		  return false;
	  }   
	  
	  return flag;
}
function validateDeleteForm(numberOfRows) {
	  var flag = true;
	  var selected = false;
	  if(numberOfRows != null) {
	    for(var i=1; i<=numberOfRows; i++) {
		  var checked = false;
		  try {
			  checked = cellValue(i,'okDoUpdate');
		  } catch(ex){}
		  if( !checked ) continue;
		  selected = true;
	    }
	  }
		  if( !selected ) {
			  alert(messagesData.norowselected);
			  return false;
		  }	  
		  
		  return flag;
	}
		




function updateDbuyCharges()
{
	 if(validationforUpdate()) {
		    /*Set any variables you want to send to the server*/
		document.genericForm.target='';
		document.getElementById('action').value = 'update';
		parent.showPleaseWait();
	    /*prepare grid for data sending, this is important to get the data from the grid to the server.*/
	    beangrid.parentFormOnSubmit();
	    document.genericForm.submit();
	   }
	
}

function deleteDbuyCharges()
{
	numberOfRows = $v('totalLines');
	  
	  var flag = validateDeleteForm(numberOfRows);
	  if(flag) {
	document.genericForm.target='';
	document.getElementById('action').value = 'delete';
	parent.showPleaseWait();
    /*prepare grid for data sending, this is important to get the data from the grid to the server.*/
    beangrid.parentFormOnSubmit();
    document.genericForm.submit();
	}
}


function newDbuyCharges(){
	var dbuyContractId =  $v("dbuyContractId"); 
	var startDate =  $v("startDate"); 
	var loc = "/tcmIS/distribution/newdbuyaddtlcharges.do?action=new&dbuyContractId="+dbuyContractId+"&startDate="+startDate;
	openWinGeneric(loc,'_newDbuyCharges','380','400','yes',"50","50");
}




function editSupplierContact()
{
	var supplier= cellValue(beangrid.getSelectedRowId(),"supplier");   
	var contactId =  cellValue(beangrid.getSelectedRowId(),"contactId"); 
	var loc = "/tcmIS/supply/newposuppliercontact.do?action=showSupplierContactData&actionType=edit&supplier="+supplier+"&contactId="+contactId;
    openWinGeneric(loc, "newPoSupplierContact", "800", "250", "yes", "50", "50");
}


function refreshPage( supplier)
{		
			
	document.getElementById("supplier").value=supplier;
	document.genericForm.target='';
	var action = document.getElementById("action");
	action.value="search";
	var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	document.genericForm.submit();
	
}