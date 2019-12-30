function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 document.genericForm.target='resultFrame';
 document.getElementById("action").value = 'search'; 
 //set start search time
 var now = new Date();
 var startSearchTime = document.getElementById("startSearchTime");
 startSearchTime.value = now.getTime();
 var flag = validateForm();
 if(flag) {
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}



function generateExcel() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'createExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_nWeekInventoryGenerateExcel','650','600','yes');
    document.genericForm.target='_nWeekInventoryGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}

function validateForm()
{
	// validates the age input field
	var errorMessage = "";
	var errorCount = 0;
	
	try
	{
	
	 var searchInput = document.getElementById("searchArgument").value.trim();
	
	 if ( !isInteger(searchInput, false)) 
	 {
	    errorMessage +=  messagesData.integer + "\n";
	    errorCount = 1;
	    searchInput.value = "";
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

