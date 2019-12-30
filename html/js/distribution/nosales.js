////////// search area scripts.
function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  
  if(checkInoput()) {
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

function checkInoput() {
  var errorMessage = messagesData.validvalues + "\n\n";
  var errorCount = 0;

  var notIssuedIn = document.getElementById("notIssuedIn");
  if ( !isInteger(notIssuedIn.value.trim(),false) )
  {
      errorMessage = errorMessage + messagesData.days + "\n";
      errorCount = 1;
      notIssuedIn.value = "";
  }

  
 if (errorCount >0)
 {
    alert(errorMessage);
    return false;
 }
 return true;
}

function createXSL(){
  var flag = true;//validateForm();
  if(flag) {
	$('uAction').value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_NoSalesExcel','650','600','yes');
    document.genericForm.target='_NoSalesExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}

function selectRightclick(rowId, cellInd) {
	beanGrid.selectRowById(rowId, null, false, false);
	toggleContextMenu('showInventoryDetail');
}

function viewInventoryDetail() {
	var hubId = beanGrid.cellById(beanGrid.getSelectedRowId(),
			beanGrid.getColIndexById("hubId")).getValue();

	var itemId = beanGrid.cellById(beanGrid.getSelectedRowId(),
			beanGrid.getColIndexById("itemId")).getValue();

	if ((hubId != '') && (itemId != '')) {
		loc = "/tcmIS/hub/inventorydetails.do?itemId=" + itemId + "&hub=" + hubId;
		openWinGeneric(loc, "inventorydetailspage", "800", "450", "yes", "50","50", "no");
	}
}



