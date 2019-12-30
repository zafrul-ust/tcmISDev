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

function validateForm(){
	var errorMessage = "";
	var errorCount = 0;

	try
	{
	//  var age = document.getElementById("expediteAge");
	 var searchInput = document.getElementById("searchArgument");
	 var searchField = document.getElementById("searchField");

	 //if ( age.value.trim().length != 0 && (!(isPositiveInteger(age.value.trim())) || age.value*1 == 0 ) )
	 //{
	 //   errorMessage +=  messagesData.age + "\n";
	 //   errorCount = 1;
	 //   age.value = "";
	 //}

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

function generateExcel() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_ExcessInventoryGenerateExcel','650','600','yes');
    document.genericForm.target='_ExcessInventoryGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}
