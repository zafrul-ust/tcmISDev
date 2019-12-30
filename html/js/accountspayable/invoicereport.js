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

function search()
{
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  
  var showOnlyToBeQced  =  document.getElementById("showOnlyToBeQced");
  var showOnlyWithReceipts  =  document.getElementById("showOnlyWithReceipts");
  var status  =  document.getElementById("status");
  var userAction  =  document.getElementById("userAction");
  userAction.value = 'search';

  if (showOnlyToBeQced.checked)
  {
	status.disabled=false;
	showOnlyWithReceipts.checked = false;
  }

  if (showOnlyToBeQced.checked && status.value != 'Approved')
  {
    status.value = 'Approved';
    status.selected = true;
  }

   	document.genericForm.target='resultFrame';
   	parent.showPleaseWait();
  	return true;
}

function showErrorMessages()
{
		parent.showErrorMessages();
}

function resultOnLoad()
{
   displaySearchDuration(); 
   setResultFrameSize();
}

function createExcel() {
    var action = document.getElementById("userAction");
    action.value = 'createExcel';

	 openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_SupplierInvoiceReportExcel','650','600','yes');
     document.genericForm.target='_SupplierInvoiceReportExcel';
     var a = window.setTimeout("document.genericForm.submit();",500);
}

