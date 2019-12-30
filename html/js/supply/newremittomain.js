function validateForm()
{
    return true;
}



function submitSearchForm()
{	
	document.genericForm.target='resultFrame';
	var action = document.getElementById("action");
	action.value="search";
	var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	var flag = validateForm();
	
	if(flag) 
  	{	showPleaseWait();
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
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_supplierSearchGenerateExcel','650','600','yes');
	    document.genericForm.target='_supplierSearchGenerateExcel';
	    var a = window.setTimeout("document.genericForm.submit();",500);
	  }
	}


