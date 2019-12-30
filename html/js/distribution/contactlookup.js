windowCloseOnEsc = true;

var beangrid;
var resizeGridWithWindow = true;

function resultOnLoad()
{
 totalLines = document.getElementById("totalLines").value;
 
 if (totalLines > 0)
 {
  document.getElementById("customerContactSearchViewBean").style["display"] = "";
  
  initializeGrid();  
 }  
 else
 {
   document.getElementById("customerContactSearchViewBean").style["display"] = "none";   
 }

 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('customerContactSearchViewBean');

	initGridWithConfig(beangrid,config,false,false);
	if( typeof( jsonMainData ) != 'undefined' ) {		
		beangrid.parse(jsonMainData,"json");
	}	
	
	beangrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	beangrid.attachEvent("onRowSelect",doOnRowSelected);
//	setTimeout('loadRowSpans()',100); 
}

/*
 * Grid event OnBeforeSelect function
 * Save the row class of currently 
 * selected row, before class changes.
 */
var saveRowClass = null;
function doOnBeforeSelect(rowId,oldRowId) {	
	var creditStatus = cellValue(rowId,'creditStatus');
    var status = cellValue(rowId,'status');
 	if(creditStatus.toUpperCase() == 'STOP' || status.toUpperCase() == 'INACTIVE') return false;
 	
	if (oldRowId != 0) {
		setRowClass(oldRowId, saveRowClass);		
	}
	saveRowClass = getRowClass(rowId);
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		overrideDefaultSelect(rowId,saveRowClass);
	return true;	
}


/*I am looping thru the map I created in the JSP to set the row spans for different columns*/
function loadRowSpans()
{
 for(var i=0;i<mygrid.getRowsNum();i++){
   try
   {
     var rowSpan = lineMap[i];
     if( rowSpan == null || rowSpan == 1 ) continue;
     mygrid.setRowspan(i+1,0,rowSpan*1);
     mygrid.setRowspan(i+1,1,rowSpan*1);
     mygrid.setRowspan(i+1,2,rowSpan*1);
     mygrid.setRowspan(i+1,3,rowSpan*1);
     mygrid.setRowspan(i+1,4,rowSpan*1);
     mygrid.setRowspan(i+1,5,rowSpan*1);
   }
   catch (ex)
   {
     //alert("here 269");
   }
 }
 /*Need to call this only if the grid has rowspans > 1*/
 mygrid._fixAlterCss();
}

function doOnRowSelected(rowId,cellInd) {
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		setRowClass(rowId,''+saveRowClass+'Selected');

	var selectedContact = parent.document.getElementById("selectedContact");

	selectedContact.style["display"] = "";
	// Get the value of current user from selected grid row
//	var contactName = cellValue(rowId,'customerName'); alert(contactName);
    var contactName = cellValue(rowId,'fullName');
    if (parent.$v('customerId').length > 0)
		selectedContact.innerHTML = ' | <a href="#" onclick="selectedContact(); return false;">'+messagesData.selectedcontact;
	else
		selectedContact.innerHTML = ' | <a href="#" onclick="selectedContact(); return false;">'+messagesData.selectedcustomercontact;
	parent.returnSelectedContactId = cellValue(rowId,'contactId');
	parent.returnSelectedContactName = contactName;
	parent.returnSelectedCustomerId = cellValue(rowId,'customerId');  
	parent.returnSelectedAddressLine1 = cellValue(rowId,'addressLine1Display');
	parent.returnSelectedAddressLine2 = cellValue(rowId,'addressLine2Display');  
	parent.returnSelectedAddressLine3 = cellValue(rowId,'addressLine3Display');  
	parent.returnSelectedAddressLine4 = cellValue(rowId,'addressLine4Display');
	parent.returnSelectedAddressLine5 = cellValue(rowId,'addressLine5Display');
	parent.returnSelectedBillToCompanyId = cellValue(rowId,'billToCompanyId');
	parent.returnSelectedBillToLocationId = cellValue(rowId,'billToLocationId');
	parent.returnSelectedPhone = cellValue(rowId,'phone');
	parent.returnSelectedFax = cellValue(rowId,'fax');
	parent.returnSelectedEmail = cellValue(rowId,'email');    

    setResultFrameSize();
}


function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = checkInput();
  var now = new Date();
  document.getElementById("uAction").value = 'search';
  document.getElementById("startSearchTime").value = now.getTime();

  if(isValidSearchForm) {
   document.genericForm.target='resultFrame';
   parent.showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function validateSearchFormXSL()
{
  if( checkInput() ) {
      document.getElementById("uAction").value = "createExcel"; 
		document.genericForm.target='_ContactLookUpExcel';
	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_ContactLookUpExcel','650','600','yes');
		setTimeout("document.genericForm.submit()",300);
	}
}

function checkInput()
{
var errorMessage = messagesData.validvalues + "\n\n";
var errorCount = 0;

try
{
 var searchCustomerIdArgument = document.getElementById("searchCustomerIdArgument");

 if ( searchCustomerIdArgument.value.trim().length != 0 && (!(isSignedInteger(searchCustomerIdArgument.value.trim())) || searchCustomerIdArgument.value*1 == 0 ) )
 {
    errorMessage +=  messagesData.customerid + "\n";
    errorCount = 1;
    receiptId.value = "";
 }
 } catch (ex) {
  errorCount++;
}

 if (errorCount >0)
 {
    alert(errorMessage);
    return false;
 }

 return true;
}
