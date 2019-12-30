function submitSearchForm()
{
	 /*Make sure to not set the target of the form to anything other than resultFrame*/
	 document.genericForm.target='resultFrame';
	 document.getElementById("uAction").value = 'search';
	 //set start search time
	 var now = new Date();
	 var startSearchTime = document.getElementById("startSearchTime");
	 startSearchTime.value = now.getTime();
	 var flag = validateForm();
     if(flag)
	 {
	   	showPleaseWait();
	   	return true;
	  }
	  else
	  {
	    return false;
	  }
}

function generateExcel()
{
	var flag = true;//validateForm();
	if(flag) 
	{
		$('uAction').value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_DisplayOnlyExcel','650','600','yes');
		document.genericForm.target='_DisplayOnlyExcel';
		var a = window.setTimeout("document.genericForm.submit();",50);
	}
}

function validateForm()
{
	var errorMessage = "";
	var errorCount = 0;

	try
	{
		 var searchInput = document.getElementById("searchArgument");
		 if((searchInput.value.trim().length != 0) && (!(isPositiveInteger(searchInput.value.trim())) || (searchInput.value*1 == 0 )  ))
		 {
		    errorMessage +=  messagesData.searchInput + "\n";
		    errorCount = 1;
		    searchInput.value = "";
		}
	} 
	catch (ex) 
	{
	  	errorCount++;
	}
	if (errorCount >0)
	{
	    alert(errorMessage);
	    return false;
	}
    return true;
}
