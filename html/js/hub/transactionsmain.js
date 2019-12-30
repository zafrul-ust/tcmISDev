function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = checkTransactionsInput();
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  
  if(isValidSearchForm) {
   document.genericForm.target='resultFrame';
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function submitSearchXmlForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = checkTransactionsInput();
  var now = new Date();
  document.getElementById("uAction").value = "doXml";
  document.getElementById("startSearchTime").value = now.getTime();  

  if(isValidSearchForm) {
   document.genericForm.target='resultFrame';
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function validateSearchFormXSL()
{
  if( checkTransactionsInput() ) {
      document.getElementById("uAction").value = "createExcel"; 
       if( document.getElementById('txnOnDate').value != '' ) {
			document.getElementById('daysOld').value = '';
   		}
		document.genericForm.target='transactions_excel_report_file';
	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','transactions_excel_report_file','650','600','yes');
		setTimeout("document.genericForm.submit()",300);
	}
}


// ***************************************************************************
// checkTransactionsInput()
//
// Checks the Transactions forms for valid input
// fields verified: txnOnDate, receiptId, mrNumber, radianPo, daysOld, itemId
//
function checkTransactionsInput()
{
var errorMessage = messagesData.validvalues + "\n\n";
var warningMessage = messagesData.alert + "\n\n";
var errorCount = 0;
var warnCount = 0;

try
{
 var receiptId = document.getElementById("receiptId");

 if ( receiptId.value.trim().length != 0 && (!(isSignedInteger(receiptId.value.trim())) || receiptId.value*1 == 0 ) )
 {
    errorMessage +=  messagesData.receiptid + "\n";
    errorCount = 1;
    receiptId.value = "";
 }
 
 var issueId = document.getElementById("issueId");

 if ( issueId.value.trim().length != 0 && (!(isSignedInteger(issueId.value.trim())) || issueId.value*1 == 0 ) )
 {
    errorMessage +=  messagesData.issueid + "\n";
    errorCount = 1;
    issueId.value = "";
 }

 var mrNumber = document.getElementById("mrNumber");
 if ( mrNumber.value.trim().length != 0 && (!(isFloat(mrNumber.value.trim())) || mrNumber.value*1 == 0 ) )
 {
    errorMessage += messagesData.mr + "\n";
    errorCount = 1;
    mrNumber.value = "";
 }

 var radianPo = document.getElementById("radianPo");
 if ( radianPo.value.trim().length != 0 && (!(isFloat(radianPo.value.trim())) || radianPo.value*1 == 0 ) )
 {
    errorMessage += messagesData.po + "\n";
    errorCount = 1;
    radianPo.value = "";
 }

 var daysOld = document.getElementById("daysOld");
 if (daysOld != null && daysOld.value.trim().length != 0 && (!(isInteger(daysOld.value.trim())) || daysOld.value*1 == 0 ) )
 {
    errorMessage += messagesData.days + "\n";
    errorCount = 1;
    daysOld.value = "";
 }

 var itemId = document.getElementById("itemId");
 if ( itemId.value.trim().length != 0 && (!(isInteger(itemId.value.trim())) || itemId.value*1 == 0 ) )
 {
    errorMessage += messagesData.itemid + "\n";
    errorCount = 1;
    itemId.value = "";
 }

 var trackingNumber = document.getElementById("trackingNumber");
 var mfgLot = document.getElementById("mfgLot");
 // if search fields are empty, limit the transaction date to within 65 days
 if(receiptId != null && receiptId.value.trim().length == 0 && mrNumber != null && mrNumber.value.trim().length == 0 
		 && trackingNumber != null && trackingNumber.value.trim().length == 0 && radianPo != null && radianPo.value.trim().length == 0
		 && itemId != null && itemId.value.trim().length == 0 && mfgLot != null && mfgLot.value.trim().length == 0
		 && issueId  != null && issueId.value.trim().length == 0)
 {
	 var transactionDate = document.getElementById("transactionDate");	
	 var type = transactionDate.options[transactionDate.selectedIndex].value;
	 
	 if(type == "within" && daysOld.value > 95)
	 {
		 errorMessage += messagesData.days + "\n";
		 errorCount = 1;
	 }
	 else if(type == "onDate")
	 {
		 var txnOnDate = parseDate(document.getElementById("txnOnDate").value);
		 var today = new Date();
		 
		 if( Math.ceil(today.getTime()-txnOnDate.getTime())/86400000 > 95)
		 {
			 errorMessage += messagesData.days + "\n";
			 errorCount = 1;
		 }
	 }
	 else if(type == "fromDate")
	 {
		 var transactionFromDate = parseDate(document.getElementById("transactionFromDate").value);
		 var transactionToDate = parseDate(document.getElementById("transactionToDate").value);
		 
		 if( Math.ceil(transactionFromDate.getTime()-transactionToDate.getTime())/86400000 > 95)
		 {
			 errorMessage += messagesData.days + "\n";
			 errorCount = 1;
		 }
	 }
 }
 
} catch (ex) {
  errorCount++;
}
 /*var trackingNumber = document.getElementById('trackingNumber');
 if ( trackingNumber.value.trim().length != 0 ) {
	document.getElementById('transType').selectedIndex = 3; // remember to change this number if the sequence is changed.
 }*/


 if (errorCount >0)
 {
    alert(errorMessage);
    return false;
 }
  
// set hub name
 var selIndex = document.getElementById('hub').options.selectedIndex;
 document.getElementById('hubName').value = 
 	document.getElementById('hub').options[selIndex].text;
 return true;
}
function transDateChange(value)
{
	if(document.getElementById('daysOld').value.trim().length > 0)
		document.getElementById('daysOld').value = "";
	if(document.getElementById('txnOnDate').value.trim().length > 0)
		document.getElementById('txnOnDate').value = "";
	if(document.getElementById('transactionToDate').value.trim().length > 0)
		document.getElementById('transactionToDate').value = "";
	if(document.getElementById('transactionFromDate').value.trim().length > 0)
		document.getElementById('transactionFromDate').value = "";
	switch(value)
	{
	case "within":
     	document.getElementById("withinSpan").style.display = "";
		document.getElementById('daysOld').value = "60";
     	document.getElementById("txnSpan").style.display = "none";
     	document.getElementById("fromSpan").style.display = "none";
     	document.getElementById("toSpan").style.display = "none";
     	break;
	case "onDate":
     	document.getElementById("withinSpan").style.display = "none";
     	document.getElementById("txnSpan").style.display = "";
     	document.getElementById("fromSpan").style.display = "none";
     	document.getElementById("toSpan").style.display = "none";
    	break;
	case "fromDate":
     	document.getElementById("withinSpan").style.display = "none";
     	document.getElementById("txnSpan").style.display = "none";
     	document.getElementById("fromSpan").style.display = "";
     	document.getElementById("toSpan").style.display = "";
       break;
	}

}
/*
function hubChanged()
{
   var hubO = document.getElementById("branchPlant");
   var inventoryGroupO = document.getElementById("inventoryGroup");
   var selhub = hubO.value;
   for (var i = inventoryGroupO.length; i > 0;i--)
   {
     inventoryGroupO[i] = null;
   }

   if (selhub.length > 0 ) {
     showInventoryGroupOptions(selhub);
   } else {
     setOption(0,messagesData.all,"","inventoryGroup");
   }
   inventoryGroupO.selectedIndex = 0;
}

function showInventoryGroupOptions(selectedhub)
{
    var invgrp = new Array();
    invgrp = altinv[selectedhub];

    if(invgrp.length == 0)
    {
        setOption(0,messagesData.all,"","inventoryGroup");
    }

    for (var i=0; i < invgrp.length; i++)
    {
        setOption(i,invgrp[i]['name'],invgrp[i]['id'],"inventoryGroup");
    }
}  */
