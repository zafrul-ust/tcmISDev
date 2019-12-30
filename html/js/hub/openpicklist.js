function validateOpenPicklist()
{
   if (document.getElementById("picklistId").value.trim().length == 0) {
      alert(messagesData.pleaseSelectId);
      return false;
   } else 
      return true;
}

function submitSearchForm() {
	if( !validateOpenPicklist() ) return;

  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();

/*
	var userAction = document.getElementById("action");
	userAction.value = 'search';
*/
   	document.genericForm.target='resultFrame';
   	parent.showPleaseWait();
//   	document.genericForm.submit();
}


function generatePickListExcel() {
	  var flag = validateOpenPicklist();
	  if(flag) {
	    var action = document.getElementById("action");
	    action.value = 'createExcel';		
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_PickListGenerateExcel','650','600','yes');
	    document.genericForm.target='_PickListGenerateExcel';
	    var a = window.setTimeout("document.genericForm.submit();",500);
	  }
	}