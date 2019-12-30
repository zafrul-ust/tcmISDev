function submitSearchForm() {
	if(validateSearchForm()) {
		var now = new Date();
		$("startSearchTime").value = now.getTime();
		$("uAction").value = "search";
		if (altCompanyId != null) {
			if (altCompanyId.length > 0) {
				$("companyId").value = altCompanyId[$("companyId").selectedIndex];
			}
		}
		showPleaseWait();
		document.clientCabinetInventoryForm.submit();
 	}
}

function validateSearchForm() 
{
  	document.clientCabinetInventoryForm.target='resultFrame';
	var errorMessage = "";
	var errorCount = 0;

	var searchField  =  document.getElementById("searchField").value;	
  	if(searchField == 'ITEM_ID') 
  	{
  		var searchArgument  =  document.getElementById("searchArgument");
    	if(!isInteger(searchArgument.value.trim(), true)) 
    	{
    		errorMessage = errorMessage + messagesData.itemInteger + "\n" ;     		
      		errorCount++;
    	}
  	}
	
	var expireInFromField = document.getElementById("expireInFrom").value;
	if ( expireInFromField.length > 0)
	{
  		if(!isSignedInteger( expireInFromField.trim(), true) ) 
  		{
    		errorMessage = errorMessage + messagesData.expireInFromDaysInteger + ".\n" ;
    		errorCount++;
  		}
  	}
	var expireInToField = document.getElementById("expireInTo").value;
	if ( expireInToField.length > 0)
	{
  		if(!isSignedInteger( expireInToField.trim(), true) ) 
  		{
    		errorMessage = errorMessage + messagesData.expireInToDaysInteger + ".\n" ;
    		errorCount++;
  		}
  	}
	
	if (errorCount >0)
	{
  		alert(errorMessage);
  		return false;
	}
	else
	{
  		return true;
	}
}

function generateExcel() 
{
  	var isValidForm = validateSearchForm();
  	if ( isValidForm ) 
  	{
    	$("uAction").value = 'createExcel';
    	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_newGenerateExcel','650','600','yes');
    	document.clientCabinetInventoryForm.target='_newGenerateExcel';
   	$("companyId").value = altCompanyId[$("companyId").selectedIndex];
    	var a = window.setTimeout("document.clientCabinetInventoryForm.submit();",500);
  	}
}

function myOnLoad() {
  //load drop downs
  showCompanyOptions();
}
