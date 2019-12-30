function submitSearchForm()
{
	
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  if(validateSearchForm()) { 
   $('uAction').value = 'showAll';
      document.genericForm.target='resultFrame';
   
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function validateSearchForm() {
	if ($v('reviewer') == null || $v('reviewer') == "") {
		return false;
	}
	if ($v('approvedMrsOnly') == 'Y' && $v('searchTerms') == "") {
		alert(messagesData.approvedmrsearchtext);
		return false;
	}
	return true;
}

function approvedMrSelected() {
	if($("approvedMrs").checked) {
		$("approvedMrsOnly").value = "Y";
	}
	else {
		$("approvedMrsOnly").value = "N";
	}
}
