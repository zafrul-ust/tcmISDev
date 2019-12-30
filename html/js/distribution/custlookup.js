var selectedRowId=null;


function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 document.genericForm.target='resultFrame';
 document.getElementById("uAction").value = 'search';
// var flag = validateForm();
//  if(flag) {
   parent.showPleaseWait();
   /*This is used to display the time it took to get the resutls back.*/ 
   var now = new Date();
   document.getElementById("startSearchTime").value = now.getTime();

   return true;
//  }
//  else
// {
//    return false;
//  }
}

function createExcel() {
    var action = document.getElementById("uAction");
    action.value = 'createExcel';

	 openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_CustomerLookUpExcel','650','600','yes');
     document.genericForm.target='_CustomerLookUpExcel';
     var a = window.setTimeout("document.genericForm.submit();",500);
}

function myOnload()
{
 displaySearchDuration();
 setResultFrameSize();
}


function selectRow(rowId)
{
   var selectedRow = document.getElementById("rowId"+rowId+"");
   
   var selectedRowClass = document.getElementById("colorClass"+rowId+"");
   
   selectedRow.className = "selected"+selectedRowClass.value+"";
   
     //update selected user display
    var selectedCustomerSpan = parent.document.getElementById("selectedCustomerSpan");
    var currentCustomerId = document.getElementById("customerId"+rowId+"");
    var addressId = document.getElementById("addressId"+rowId+"");
    selectedCustomerSpan.innerHTML = '<a href="#" onclick="selectedCustomerCall(); return false;">'+messagesData.selectedcustomer+' : '+currentCustomerId.value+'/'+addressId.value+'</a>';
   
//set variable to main
    parent.returnSelectedCustomerId = currentCustomerId.value;
    parent.returnSelectedAddressId = addressId.value;
  
   if (selectedRowId != null && rowId != selectedRowId)
   {
     var previouslySelectedRow = document.getElementById("rowId"+selectedRowId+"");
     var previouslySelectedRowClass = document.getElementById("colorClass"+selectedRowId+"");
     previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";
   }
   selectedRowId =rowId;
   
  // toggleContextMenu('showDetails');
}  //end of selectRow

function selectedCustomerCall()
{ 
  window.opener.customerIdChanged(returnSelectedCustomerId);
/*	
  try {
  var openervalueElementId = opener.document.getElementById(valueElementId);
  openervalueElementId.value = returnSelectedUserId;
  var openerdisplayElementId = opener.document.getElementById(displayElementId);
  openerdisplayElementId.className = "inputBox";
  openerdisplayElementId.value = returnSelectedUserName;
  //reset valiable
  returnSelectedUserName = null;
  returnSelectedCustomerId=null;
  valueElementId=null;
  displayElementId=null;
  } catch( ex ) {}
  
  try {
  if( window.opener.customerIdChanged ) {
  	window.opener.customerIdChanged(returnSelectedCustomerId);
  }
  } catch(exx) {}
  */
  window.close();
}