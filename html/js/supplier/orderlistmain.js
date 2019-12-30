
function showDetails() { 
      if (document.getElementById("problem").checked==false &&
         document.getElementById("reject").checked==false) {
         alert(messagesData.checkatleastone);
      } else {
 //        document.detailFrm.submit();
      }
    }


function submitSearchForm() 
{
	/*Make sure to not set the target of the form to anything other than resultFrame*/
	document.supplierForm.target='resultFrame';
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
	// validates the age input field
	var errorMessage = "";
	var errorCount = 0;

	try
	{
		var poNumber = document.getElementById("poNumber");
		var showPOs = document.getElementById("showPOs");

		if(showPOs.value == "bypo")
		{
			if ( (poNumber.value.trim().length == 0) || (!(isPositiveInteger(poNumber.value.trim())) || poNumber.value*1 == 0 ) )
			{
				errorMessage +=  messagesData.poNumber + "\n";
				errorCount = 1;
				poNumber.value = "";
			}	 
		}		 		

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

function unCheckPoChkboxs()
{
	var statusChkboxs = document.getElementsByName("poStatusChkbxArray");

	document.getElementById("showPOs").value="bypo";

	for (var j = 0; j < 6; j++) 
	{   
		statusChkboxs[j].disabled = true;

        if (statusChkboxs[j].checked == true && statusChkboxs[j].value=="CONFIRMED")
		{
			document.getElementById("dateConfirmedrow").disabled = true;
		}		
    }
}

function checkPoChkboxs()
{
	var statusChkboxs = document.getElementsByName("poStatusChkbxArray");

	document.getElementById("showPOs").value="bystatus";

	for (var j =0 ; j < 6; j++) 
	{   
		if (statusChkboxs[j].disabled == true)
		{
			statusChkboxs[j].disabled = false;			   
		}

        if (statusChkboxs[j].checked == true && statusChkboxs[j].value=="CONFIRMED")
		{
			document.getElementById("dateConfirmedrow").disabled = false;				
		}
    }
}


function generateExcel() {
	var flag = validateForm();
	if(flag) {
		var action = document.getElementById("action");
		action.value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_ReceivingReportGenerateExcel','650','600','yes');
		document.supplierForm.target='_ReceivingReportGenerateExcel';
		var a = window.setTimeout("document.supplierForm.submit();",500);

	}
}

function showHideDateConfirmed()
{
	var confirmedChkBx = document.getElementById("poStatusChkbxArray");
	var statusChkboxs = document.getElementsByName("poStatusChkbxArray");



	for (var j =0 ; j < 6; j++) 
	{   
		//alert(statusChkboxs[j].checked);
		//alert(statusChkboxs[j].value);
		
		if (statusChkboxs[j].checked == true && statusChkboxs[j].value=="CONFIRMED")
		{
			document.getElementById("dateConfirmedrow").style["display"] = "";	
			document.getElementById("dateConfirmedrowHide").style["display"] = "none";
			
			break;
		}		
		
		else
		{
			document.getElementById("dateConfirmedrow").style["display"] = "none";
			document.getElementById("dateConfirmedrowHide").style["display"] = "";
		}	
		
		
	}
	

}