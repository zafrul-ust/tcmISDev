windowCloseOnEsc = true;

function doOnLoad() {
	//if user is looking for a specific carrier, prepare to automatically return if exact match is found
	if (!isWhitespace($v("searchArgument")) && $v("source") == "purchaseOrderPage") {
		//check if searching for exact match of carrier code is valid, and decide whether auto return is allowed or not 
		var searchTermsFound = 0;
		for (var i = 0; i < $("searchField").options.length; i++)
			if ("carrierCode" == $("searchField").options[i].value) {
				$("searchField").value = "carrierCode";
				searchTermsFound++;
				break;
			}
		for (var i = 0; i < $("searchMode").options.length; i++)
			if ("is" == $("searchMode").options[i].value) {
				$("searchMode").value = "is";
				searchTermsFound++;
				break;
			}
		if (searchTermsFound == 2)
			$("autoReturnIfMatch").value = "Y";
	}
	
	if (submitSearchForm())
		document.genericForm.submit();
}

function submitSearchForm()
{
 	var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  
  document.getElementById("selectedCarrier").innerHTML = "";

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

function validateSearchForm()
{
	return true;
}


var returnSelectedCarrierCode=null;
var returnSelectedCarrierName=null; 
var returnSelectedAccount=null;
var returnSelectedCompanyHub=null;
var valueElementId=null;
var displayElementId=null;
var displayAccount=null;
var displayArea=null;

function selectedCarrier()
{	
  try {
	  var openervalueElementId = opener.document.getElementById($v('valueElementId'));
	  if (openervalueElementId)
		  openervalueElementId.value = returnSelectedCarrierCode; 
  } catch( ex ) {}
  try {
	  var openerdisplayElementId = opener.document.getElementById(displayElementId);
	  if (openerdisplayElementId) {
		  openerdisplayElementId.className = "inputBox";
		  openerdisplayElementId.value = returnSelectedCarrierName;
	  }
  } catch( ex ) {}
  try {
	  var openerdisplayArea = opener.document.getElementById($v('displayArea')); 
	  if (openerdisplayArea) {
		  openerdisplayArea.value = returnSelectedCarrierName;
		  var displayAccount = opener.document.getElementById($('displayAccount').value); 
		  displayAccount.value = returnSelectedAccount;
	  }
  } catch( ex ) {}
   
  try {	  
	  if( $v("callbackfunction") == 'carrierChanged' && window.opener.carrierChanged ) {		
	  	window.opener.carrierChanged(returnSelectedCarrierCode,returnSelectedCarrierName,returnSelectedAccount, returnSelectedCompanyHub, callbackparam);
	  }
	  
	  if( $v("callbackfunction") == 'criticalOrderCarrierChanged' && window.opener.criticalOrderCarrierChanged ) {
	  	window.opener.criticalOrderCarrierChanged(returnSelectedCarrierCode,returnSelectedCarrierName,callbackparam);
	  }
  } catch(exx) {}
 
  window.close();
  returnSelectedCarrierCode=null;
  returnSelectedCarrierName=null;
  returnSelectedAccount=null;
  returnSelectedCompanyHub = null;
  valueElementId=null;
  displayElementId=null;
  displayAccount=null;
  displayArea=null;
}
