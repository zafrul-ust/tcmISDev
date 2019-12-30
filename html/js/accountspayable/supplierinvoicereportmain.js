
function freezeInvoiceStatus()
{
  var showOnlyToBeQced  =  document.getElementById("showOnlyToBeQced");
  var showOnlyWithReceipts  =  document.getElementById("showOnlyWithReceipts");
  var status  =  document.getElementById("status");
  var approvedDates  =  document.getElementById("approvedDates");

  if (showOnlyToBeQced.checked)
  {
    status.value = 'Approved';
    status.selected = true;
    status.disabled=true;
    showOnlyWithReceipts.checked = false;
    showOnlyWithReceipts.disabled=true;
    approvedDates.style["display"] = "";
  }
  else
  {
    status.disabled=false;
    showOnlyWithReceipts.disabled=false;
    approvedDates.style["display"] = "none";
  }
}

function validateOps() {
	if($v("opsEntityId").length == 0) {
		alert(messagesData.pleaseinput.replace(/[{]0[}]/g,messagesData.operatingentity));
		return false;
	}  
	return true;
}

function lookupSupplier() {
	if(validateOps()) {
		loc = "/tcmIS/supply/posuppliermain.do?popUp=Y&displayElementId=supplierName&valueElementId=supplier&statusFlag=true&opsEntityId="+$v("opsEntityId");
		openWinGeneric(loc,"supplierlookup","800","500","yes","50","50","20","20","no"); 
	}  
}

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = checkInput();
  var now = new Date();
  document.getElementById("userAction").value = 'search';
  document.getElementById("startSearchTime").value = new Date().getTime();
  document.getElementById("branchPlant").value = document.getElementById("hub").value 
  
  if(isValidSearchForm) {
   document.genericForm.target='resultFrame';
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function createXSL() {
	if( !checkInput() ) return false;
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', 'cust_search_lookup','800','600','yes');
	document.genericForm.target='cust_search_lookup';
	document.getElementById("userAction").value = 'createExcel';
    var a = window.setTimeout("document.genericForm.submit();",1000);
    //document.genericForm.submit();
   	return false;
}


function checkInput()
{
  var errorMessage = messagesData.validvalues + "\n\n";
  var errorCount = 0;

  var voucherAge = document.getElementById("voucherAge");

  if ( voucherAge.value.trim().length != 0 && !(isSignedInteger(voucherAge.value.trim())))
  {
    errorMessage +=  messagesData.invoiceAge + "\n";
    errorCount = 1;
    voucherAge.value = "";
  }

  if (errorCount >0)
  {
    alert(errorMessage);
    return false;
  }

  return true;
}


