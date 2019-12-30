/*Call this if you want to initialize something on load in the search frame.*/
function mySearchOnload()
{
 setSearchFrameSize();
 var showPreviousPo = document.getElementById("showPreviousPo");
 if (showPreviousPo.value == "Yes") {
 	var searchText = document.getElementById("searchText");
	var previousPoNumber = document.getElementById("previousPoNumber");
	searchText.value = previousPoNumber.value;
	submitSearchForm();
	var a = window.setTimeout("document.genericForm.submit();",500);
 }
}

function submitSearchForm()
{
 document.genericForm.target='resultFrame';
 var action = document.getElementById("action");
 action.value = 'search';
 //set start search time
 var now = new Date();
 var startSearchTime = parent.window.frames["searchFrame"].document.getElementById("startSearchTime");
 startSearchTime.value = now.getTime();
 var flag = validateForm();
  if(flag) {
    parent.showPleaseWait();
    return true;
  }
  else
  {
    return false;
  }
}

function validateForm() {
   var result = true;
  return result;
} //end of validateForm

function generateExcel() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_PurchaseOrdersGenerateExcel','650','600','yes');
    document.genericForm.target='_PurchaseOrdersGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}
