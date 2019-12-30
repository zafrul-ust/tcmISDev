windowCloseOnEsc = true;
function validateForm() {
	
	var errorMsg = '';
	if($v('transitTimeInDays') && !isInteger($v('transitTimeInDays',true))){
		errorMsg += "\n"+messagesData.transitTimeInDays +" "+ messagesData.integer;	
		alert(errorMsg);
	  return false;
	}
	return true;
}

/*function editDataWinClose()
{
	if(closeEditDataWin)
	{ 
		window.close();	
		window.opener.document.getElementById("uAction").value = 'search';
		window.opener.document.genericForm.target='resultFrame';
		window.opener.parent.showPleaseWait();
		window.opener.document.genericForm.submit();
	}
}
*/
function editData() {
	document.genericForm.target='';
	document.getElementById("uAction").value = "editData";
	var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");    
	startSearchTime.value = now.getTime();
   var flag = validateForm();
	
	if(flag) 
	{
		showPleaseWait();
		document.genericForm.submit();  	   
   		return true;
  	}
  	else
  	{
    	return false;
  	}
		
}

function searchSourcer()
{
   loc = "searchpersonnelmain.do?fixedCompanyId=Y&displayArea=sourcerName&valueElementId=sourcer";
   openWinGeneric(loc,"searchSourcer","600","450","yes","50","50","20","20","no");
}

function clearSourcer()
{
    document.getElementById("sourcerName").value = "";
    document.getElementById("sourcer").value = "";
}

function searchCsr()
{
   loc = "searchpersonnelmain.do?fixedCompanyId=Y&displayArea=customerServiceRepName&valueElementId=defCustomerServiceRepId";
   openWinGeneric(loc,"searchCsr","600","450","yes","50","50","20","20","no");
}

function clearCsr()
{
    document.getElementById("customerServiceRepName").value = "";
    document.getElementById("defCustomerServiceRepId").value = "";
}
/*
function searchCarrier()
{
   loc = "/tcmIS/supply/pocarriermain.do?displayElementId=carrierName&valueElementId=carrier";
   openWinGeneric(loc,"searchCarrier","600","450","yes","50","50","20","20","no");
}


function clearCarrier()
{
    document.getElementById("carrierName").value = "";
    document.getElementById("carrier").value = "";
}
*/
function changeValue(element) {

	if (element.value == 'N' || element.value == '')
		element.value = 'Y';
	else
		element.value='N';
}










