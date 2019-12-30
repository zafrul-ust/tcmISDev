
// This works only for popup windows and IE. Close the window after clicking Esc key
var windowCloseOnEsc = true; 

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = validateSearchCriteriaInput();
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();

  if(isValidSearchForm) {
   $("uAction").value = 'search';
   document.genericForm.target='resultFrame';
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function buildDropDown() {

  if( inventoryArray.length == 1 )
	  setOption(0,inventoryArray[0].name,inventoryArray[0].id, "inventoryGroup");
  else {
	  setOption(0,defaults.name,defaults.id, "inventoryGroup"); 
	  start = 1;

	  for ( var i=0; i < inventoryArray.length; i++) {
	    	setOption(start++,inventoryArray[i].name,inventoryArray[i].id,"inventoryGroup");
	  }
  }
  $("inventoryGroup").value = $v("defaultInventoryGroup");
}

function validateSearchCriteriaInput()
{
	var errorMessage = "";
	var errorCount = 0;
	
	var searchField = $v("searchField");
	var searchArgument = $v("searchArgument");
	if( (searchField == 'radianPo'	||
      	searchField == 'itemId'	||
      	searchField == 'receiptId'	||
      	searchField == 'originalReceiptId') && !isInteger(searchArgument,true ))
	{
		errorCount = 1;
		errorMessage = messagesData.mustbeaninteger.replace(/[{]0[}]/g,searchField);
	}

	var searchField2 = $v("searchField2");
	var searchArgument2 = $v("searchArgument2");
	if( (searchField2 == 'radianPo'	||
      	searchField2 == 'itemId'	||
      	searchField2 == 'receiptId'	||
      	searchField2 == 'originalReceiptId') && !isInteger(searchArgument2,true ))
	{
		errorCount = 1;
		errorMessage += "\n" + messagesData.mustbeaninteger.replace(/[{]0[}]/g,searchField2);
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
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_InventoryLookupExcel','650','600','yes');
    document.genericForm.target='_InventoryLookupExcel';
    var a = window.setTimeout("document.genericForm.submit();",50);
  }
}


