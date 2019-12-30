/*Call this if you want to initialize something on load in the search frame.*/
////////// search area scripts.
function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = validateForm();
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  
  if(isValidSearchForm) {
   $('uAction').value = 'search';
   document.genericForm.target='resultFrame';
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function validateForm() {
	if( !$('checkbox').checked && $('searchArgument').value.trim() == '' ) {
		alert( messagesData.pleaseinput ) ;
		$('searchArgument').focus();
		return false;
	}
	return true;
}

function createXSL(){
  var flag = true;//validateForm();
  if(flag) {
	$('uAction').value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_HarmonizedCodeExcel','650','600','yes');
    document.genericForm.target='_HarmonizedCodeExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}

