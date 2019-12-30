// This works only for popup windows and IE. Close the window after clicking Esc key
var windowCloseOnEsc = true; 

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();

  if(validateSearchForm()) { 
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


  function validateSearchForm() {
	if ($v("searchArgument").length == 0 && $v("supplier").length == 0) {
		alert(messagesData.missingSearchAndSupplier);
		return false;
	}
	if($v("searchArgument").length > 0 && $v("searchField") == "itemId" && !isInteger($v("searchArgument"),true)) {
		alert(messagesData.itemInteger);
		return false;
	}
	return true;
}

	
function setOps() {
 	buildDropDown(opspg,defaults.ops,"opsEntityId");
}

function createXSL(){
  var flag = true;//validateForm();
  if(flag) {
	$('uAction').value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_Item_Notes_Excel','650','600','yes');
    document.genericForm.target='_Item_Notes_Excel';
    var a = window.setTimeout("document.genericForm.submit();",50);
  }
}

function getSuppliers()
{
   loc = "/tcmIS/supply/posuppliermain.do?displayElementId=supplierName&valueElementId=supplier&statusFlag=true";
   openWinGeneric(loc,"supplierssearch","800","500","yes","50","50")
}

function clearSupplier()
{
    document.getElementById("supplierName").value = "";
    document.getElementById("supplier").value = "";
}



