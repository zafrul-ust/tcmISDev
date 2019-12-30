function showResultWhenPopup() {
	if($v("passedOpsEntityId") != "" || $v("itemId") != "") {
	
		$("opsEntityId").value = $v("passedOpsEntityId");
		opsChanged();
		$("hub").value = $v("passedHub");
		hubChanged();
		$("inventoryGroup").value = $v("passedInventoryGroup");
		$("searchArgument").value = $v("itemId");
		
		submitSearchForm();
	}
}


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
   document.genericForm.submit();
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
	if($v("searchField") == 'itemId' && !(isPositiveInteger($v("searchArgument"),true))){
		alert(messagesData.searchInput);
		return false;
	}
	else
    	return true;
}

