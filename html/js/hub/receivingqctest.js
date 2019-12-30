/**todo Internationalize**/
var submitcount=0;
var updatecount=0;
function SubmitOnlyOnceNawaz()
{
   try
   {
     var sourceHubName = null;
     sourceHubName =  hubO.options[hubO.selectedIndex].text;

     var sourceHubNameObject = document.getElementById("sourceHubName");
     sourceHubNameObject.value = sourceHubName;
   }
   catch (ex)
   {
     //alert("Non-Pickable Status");
   }

  

    if (submitcount == 0)
    {
        submitcount++;
        try
        {
         var transitPage = document.getElementById("TRANSITPAGE");
         transitPage.style["display"] = "";

         var mainPage = document.getElementById("MAINPAGE");
         mainPage.style["display"] = "none";

        }
        catch (ex)
        {
        }

        return true;
    }
    else
    {
        alert(messagesData.submitOnlyOnce);
        return false;
    }
}

String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function doexcelpopup()
{    
    var userAction = document.getElementById("userAction");
    userAction.value = "createExcel";
    
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_newGenerateExcel','650','600','yes');
    document.genericForm.target='_newGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);     
    return false;
}

function searchForm()
{
 document.genericForm.target='';
 return true;
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function openWinGenericExcel(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=yes,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}


function checkClosePoLine(rowNumber)
{

}

function addBintoMainPage()
{
 var totallines = opener.document.getElementById("totallines").value*1;
 var selectedRow = false;
 var vvHubBin = document.getElementById("vvHubBin").value;
 var itemId = document.getElementById("itemId");
 var selectedRowNumber = document.getElementById("rowNumber").value;

 for ( var rowNumber = 0 ; rowNumber < totallines ; rowNumber ++)
 {
  var mainItemId = opener.document.getElementById("itemId"+rowNumber+"");
  if (mainItemId.value == itemId.value)
  {
   var mainBinO = opener.document.getElementById("bin"+rowNumber+"");
   //alert("Found the line to add Bin "+vvHubBin+"");
   if (selectedRowNumber == rowNumber)
   {
     selectedRow = true;
   }
   else
   {
     selectedRow = false;
   }

   try
   {
     var binName = null;
     binName =  mainBinO.value;
	  if (mainBinO.value == "NONE" || mainBinO.value.length == 0)
	  {
	    mainBinO[0] = null;
       opener.addOptionItem(rowNumber,vvHubBin,vvHubBin,selectedRow);
	  }
	  else
	  {
       opener.addOptionItem(rowNumber,vvHubBin,vvHubBin,selectedRow);
	  }
   }
   catch (ex)
   {
//		alert("error");
   }

  }
 }
 cancel();
}

function addOptionItem(rowNumber,value,text,selectedRow) {
     obj = document.getElementById("bin"+rowNumber+"")
	  var index = obj.length;
     obj.options[index]=new Option(text,value);
     if (selectedRow)
     {
     obj.options[index].selected = true;
     }
}

function showVvHubBins(rowNumber)
{
  var itemId = document.getElementById("itemId"+rowNumber+"");
  var branchPlant = document.getElementById("branchPlant"+rowNumber+"");

  var loc = "/tcmIS/hub/showhubbin.do?branchPlant="+branchPlant.value+"&userAction=showBins&rowNumber="+rowNumber+"&itemId=";
  loc = loc + itemId.value;
  openWinGeneric(loc,"showVvHubBins","300","150","no","80","80");
}


function checkAllNonChemicalReceipts()
{
var result ;
var checkNonChemicalReceipts = document.getElementById("checkNonChemicalReceipts");
if (checkNonChemicalReceipts.checked)
{
  result = true;
}
else
{
  result = false;
}

 var totalLines = document.getElementById("totallines").value;
 for ( var p = 0 ; p < totalLines ; p ++)
 {
   try
   {
    var updateStatus = document.getElementById("updateStatus"+p+"").value;
   }
   catch (ex)
   {
	 updateStatus = "";
   }

	if (updateStatus != 'readOnly')
	{
    try
    {
    var ok = document.getElementById("ok"+p+"");
    ok.checked =result;
    }
    catch (ex)
    {
    }
   }
 }
}

function showProjectDocuments(receiptId,rowNumber)
{
 var inventoryGroup = document.getElementById("inventoryGroup"+rowNumber+"");
 var loc = "/tcmIS/hub/receiptdocuments.do?receiptId="+receiptId+"&showDocuments=Yes&inventoryGroup="+inventoryGroup.value+"";
 openWinGeneric(loc,"showAllProjectDocuments","450","300","no","80","80");
}

/*function assignpaperSize(size)
{
 var paperSize = document.getElementById("paperSize");
 paperSize.value =size;
}*/

function printReceivingQcLabels()
{
 var loc = "/tcmIS/hub/receivingqclabels.do?";
 var paperSize = document.getElementById("paperSize").value;
 loc = loc + "paperSize=" + paperSize;
 var hubNumber = document.getElementById("sourceHub").value;
 loc = loc + "&hubNumber=" + hubNumber;
 var skipKitLabels = document.getElementById("skipKitLabels");
 if (skipKitLabels.checked)
 {
  loc = loc + "&skipKitLabels=Yes";
 }
 else
 {
  loc = loc + "&skipKitLabels=No";
 }

 openWinGeneric(loc,"printReceivingQcLabels","800","500","yes","80","80");
}

function printReceivingBoxLabels()
{
var totalLines = document.getElementById("totallines").value;
var checkedCount =0;
var labelReceipts="";
for ( var p = 0 ; p < totalLines ; p ++)
{
 try
 {
  var updateStatus = document.getElementById("updateStatus"+p+"").value;
 }
 catch (ex)
 {
 updateStatus = "";
 }

if (updateStatus != 'readOnly')
{
  try
  {
  var ok = document.getElementById("ok"+p+"");

  if (ok.checked)
  {
    var receiptId = document.getElementById("receiptId"+p+"");
    if (checkedCount >0)
    {
      labelReceipts += ','
    }
    labelReceipts += receiptId.value;
    checkedCount++
  }
  }
  catch (ex)
  {
  }
 }
}

 //var labelReceipts = document.getElementById("labelReceipts").value;
 //labelReceipts = labelReceipts.replace(/,/gi, "%2c");
 if (labelReceipts.trim().length > 0)
 {
 var loc = "/tcmIS/hub/printreceiptboxlabels.do?labelReceipts="+labelReceipts+"";
 loc = loc + "&paperSize=64";
 openWinGeneric(loc,"printBinLabels11","800","500","yes","80","80");
 }
}

function reverseReceipt(rowNumber)
{
    var found = false;
    var receiptId =  document.getElementById("receiptId"+rowNumber+"");

    if ( receiptId.value.trim().length > 0 )
    {
        loc = "/tcmIS/hub/showreversereceipt.do?receiptId=";
        loc = loc + receiptId.value;
        openWinGeneric(loc,"Reverse_Receiving","300","150","no")
        return true;
    }
    return false;
}

function submitMainPage()
{
 var submitSearch = opener.document.getElementById("submitSearch");
 submitSearch.click();
 window.close();
}

function checkReceiptStatus(rowNumber)
{
 var lotStatus =  document.getElementById("lotStatus"+rowNumber+"");
 var origlotStatus =  document.getElementById("origlotStatus"+rowNumber+"");

 if (lotStatus.value.length == 0 )
 {
	var selecelemet = lotStatus.selectedIndex;
	var testelementtext = lotStatus.options[selecelemet].text;
	if (origlotStatus.value != testelementtext)
	{
  	 alert(label.nopermissiontochangestatus+" "+testelementtext+".")
	}

	i = 0;
	while(i<lotStatus.length)
	{
	  var elementtext = lotStatus.options[i].text;

	  if (elementtext == origlotStatus.value)
	  {
		lotStatus.selectedIndex = i;
	  }
	   i+=1;
	}
 }
 else
 {
	if (lotStatus.value == "Customer Purchase" || lotStatus.value == "Write Off Requested" || lotStatus.value == "3PL Purchase")
	 {
	   loc = "/tcmIS/hub/terminalstatusrootcause.do?lotStatus=";
	   loc = loc + lotStatus.value + "&rowNumber=" + rowNumber;
	   openWinGeneric(loc,"terminal_root_cause","500","300","no");
	 }
	 else
	 {
	   lotStatusRootCause = document.getElementById("lotStatusRootCause"+rowNumber+"");
	   lotStatusRootCause.value = "";

	   responsibleCompanyId = document.getElementById("responsibleCompanyId"+rowNumber+"");
	   responsibleCompanyId.value = "";

	   lotStatusRootCauseNotes = document.getElementById("lotStatusRootCauseNotes"+rowNumber+"");
	   lotStatusRootCauseNotes.value = "";
	 }
 }
}

function sendTerminalRootCauseValues()
{
rowNumber = document.getElementById("rowNumber").value;
rootCause = document.getElementById("rootCause");
rootCauseCompany = document.getElementById("rootCauseCompany");
rootCauseNotes = document.getElementById("rootCauseNotes");

lotStatusRootCause = opener.document.getElementById("lotStatusRootCause"+rowNumber+"");
lotStatusRootCause.value = rootCause.value;

responsibleCompanyId = opener.document.getElementById("responsibleCompanyId"+rowNumber+"");
responsibleCompanyId.value = rootCauseCompany.value;

lotStatusRootCauseNotes = opener.document.getElementById("lotStatusRootCauseNotes"+rowNumber+"");
lotStatusRootCauseNotes.value = rootCauseNotes.value;

window.close();
}

function showrecforinvtransfrQc(rowNumber)
{
    var transferRequestId = document.getElementById("transferRequestId"+rowNumber+"");
    loc = "/tcmIS/hub/transfertransactions.do?transferRequestId=";
    loc = loc + transferRequestId.value;
    openWinGeneric(loc,"Previous_Transfer_Transactions","700","400","yes");
}

function showPreviousReceivedQc(rowNumber)
{
	var itemId = document.getElementById("itemId"+rowNumber+"");
	var branchPlant = document.getElementById("branchPlant"+rowNumber+"");
 	var inventoryGroup = document.getElementById("inventoryGroup"+rowNumber+"");

	loc = "/tcmIS/hub/showreceivedreceipts.do?itemId="+itemId.value+"&hub="+branchPlant.value+"&inventoryGroup="+inventoryGroup.value+"&approved=Y";

    /*loc = "/tcmIS/Hub/ReceivingQC?session=Active&UserAction=previousreceiving&poline=";
    loc = loc + itemId.value;
    loc = loc + "&HubNo=" + branchPlant.value;*/
    openWinGeneric(loc,"Previous_Receiving","700","450","yes")
}

function showPreviousPoLineQc(rowNumber)
{
 var radianPo = document.getElementById("radianPo"+rowNumber+"");
 var poLine = document.getElementById("poLine"+rowNumber+"");
 var itemId = document.getElementById("itemId"+rowNumber+"");
 var branchPlant = document.getElementById("branchPlant"+rowNumber+"");
 var inventoryGroup = document.getElementById("inventoryGroup"+rowNumber+"");

 loc = "/tcmIS/hub/showreceivedreceipts.do?radianPo="+radianPo.value+"&poLine="+poLine.value+"&hub="+branchPlant.value+"&inventoryGroup="+inventoryGroup.value+"&approved=Y";

 /*loc = "/tcmIS/Hub/ReceivingQC?session=Active&UserAction=previousreceiving&poline=";
 loc = loc + poLine.value;
 loc = loc + "&purchorder=" + radianPo.value;
 loc = loc + "&HubNo=" + branchPlant.value;*/

 openWinGeneric(loc,"Previous_PO_Line_Receiving","700","450","yes")
}

function addnewBin()
{
 var sourceHub = document.getElementById("sourceHub");
 var sourceHubName = document.getElementById("sourceHubName");

 if (sourceHubName.value.length > 0 && sourceHub.value.length > 0)
 {
  var loc = "/tcmIS/hub/addhubbin.do?branchPlant="+sourceHub.value+"&sourceHubName="+sourceHubName.value+"&userAction=addNewBin";
  openWinGeneric(loc,"addnewBin","400","200","no","80","80");
 }
}

function showreceivingpagelegend()
{
  openWinGeneric("/tcmIS/help/receivingpagelegend.html","receivingpagelegend","290","300","no","50","50");
}

function checkNonChemicalReceivingQcInput(rowNumber)
{

}

function checkLabelQuantity(rowNumber)
{
var errorMessage = " "+messagesData.validvalues+" \n\n";
var warningMessage = messagesData.alert+" \n\n";
var errorCount = 0;
var warnCount = 0;

var ok = document.getElementById("ok"+rowNumber+"");
if (ok.checked)
{
try
{
 var labelQuantity = document.getElementById("labelQuantity"+rowNumber+"");
 if ( !(isInteger(labelQuantity.value)) || labelQuantity.value*1 == 0 )
 {
   errorMessage = errorMessage + " " +messagesData.labelquantity+". \n";
	errorCount = 1;
   labelQuantity.value = "";
 }
}
catch (ex)
{

}
}

if (errorCount >0)
{
  alert(errorMessage);
  ok.checked = false;
}

if (warnCount >0)
{
  alert(warningMessage);
}
}

function checkAllChemicalLines()
{
 var totalLines = document.getElementById("totallines").value;
 var result = true;

 for ( var p = 0 ; p < totalLines ; p ++)
 {
   try
   {
    var updateStatus = document.getElementById("updateStatus"+p+"").value;
   }
   catch (ex)
   {
	 updateStatus = "";
   }

	if (updateStatus != 'readOnly')
	{
    try
    {
    var ok = document.getElementById("ok"+p+"");
    if (ok.checked)
    {
      var lineResult = checkChemicalReceivingQcInput(p,'confirm');
      if (lineResult == false)
      {
	    result = false;
      break;  
      }
    }
    }
    catch (ex)
    {
    }
   }
 }

 return result;
}

function checkChemicalReceivingQcInput(rowNumber,action)
{
var receiptId = document.getElementById("receiptId"+rowNumber+"");
var errorMessage = messagesData.forreceipt + " "+receiptId.value+" " +messagesData.validvalues+" \n\n";
var warningMessage = messagesData.alert+" \n\n";
var errorCount = 0;
var warnCount = 0;

var ok = document.getElementById("ok"+rowNumber+"");
if (ok.checked)
{

var mfgLot = document.getElementById("mfgLot"+rowNumber+"");
if (mfgLot.value.trim().length == 0)
{
 errorMessage = errorMessage + " "+ messagesData.mfglot+". \n" ;
 errorCount = 1;
}
/*
var vendorShipDate = document.getElementById("vendorShipDate"+rowNumber+"");
if (vendorShipDate.value.trim().length == 10)
{
  if (!checkdate(vendorShipDate))
  {
  errorMessage = errorMessage +   " Actual Supplier Ship Date in mm/dd/yyyy Format. \n" ;
  errorCount = 1;
  vendorShipDate.value = "";
  }
}
else if (vendorShipDate.value.trim().length > 0)
{
 errorMessage = errorMessage +   " Actual Supplier Ship Date in mm/dd/yyyy Format. \n" ;
 errorCount = 1;
 vendorShipDate.value = "";
}

var dateOfReceipt = document.getElementById("dateOfReceipt"+rowNumber+"");
if (dateOfReceipt.value.trim().length == 10)
{
  if (!checkdate(dateOfReceipt))
  {
  errorMessage = errorMessage +   " Date of Receipt in mm/dd/yyyy Format. \n" ;
  errorCount = 1;
  dateOfReceipt.value = "";
  }
}
else
{
 errorMessage = errorMessage +   " Date of Receipt in mm/dd/yyyy Format. \n" ;
 errorCount = 1;
 dateOfReceipt.value = "";
}


var dateOfManufacture = document.getElementById("dateOfManufacture"+rowNumber+"");
if (dateOfManufacture.value.trim().length == 10)
{
  if (!checkdate(dateOfManufacture))
  {
  errorMessage = errorMessage +   " Date Manufactured in mm/dd/yyyy Format. \n" ;
  errorCount = 1;
  dateOfManufacture.value="";
  }
}
else if (dateOfManufacture.value.trim().length > 0)
{
 errorMessage = errorMessage +   " Date Manufactured in mm/dd/yyyy Format. \n" ;
 errorCount = 1;
 dateOfManufacture.value = "";
}

var dateOfShipment = document.getElementById("dateOfShipment"+rowNumber+"");
if (dateOfShipment.value.trim().length == 10)
{
  if (!checkdate(dateOfShipment))
  {
  errorMessage = errorMessage +   " Manufacturer Date of Shipment in mm/dd/yyyy Format. \n" ;
  errorCount = 1;
  dateOfShipment.value = "";
  }
}
else if (dateOfShipment.value.trim().length > 0)
{
 errorMessage = errorMessage +   " Manufacturer Date of Shipment in mm/dd/yyyy Format. \n" ;
 errorCount = 1;
 dateOfShipment.value = "";
}
*/
var dateOfReceipt = document.getElementById("dateOfReceipt"+rowNumber+"");
          if (dateOfReceipt.value.trim().length == 0) {            
            errorMessage = errorMessage + " "+messagesData.dor+"\n" ;
            errorCount = 1;
            dateOfReceipt.value = document.getElementById("todayDate").value;
          }

 var expireDateString = document.getElementById("expireDateString"+rowNumber+"");      
          if (expireDateString.value.trim().length == 0) {            
            errorMessage = errorMessage + " "+messagesData.expiredate+"\n" ;
            errorCount = 1;
            expireDateString.value = "";
          }

try
{
var lotStatusValue = document.getElementById("lotStatus"+rowNumber+"").value;
}
catch (ex)
{
lotStatusValue = "";
}
/*
if (lotStatusValue.trim().length > 0 && action=="confirm")
{
 var dateOfReceipt = document.getElementById("dateOfReceipt"+rowNumber+"");
          if (dateOfReceipt.value.trim().length == 0) {            
            errorMessage = errorMessage + " "+messagesData.dor+"\n" ;
            errorCount = 1;
            dateOfReceipt.value = document.getElementById("todayDate").value;
          }
          
          
var expireDateString = document.getElementById("expireDateString"+rowNumber+"");
// if (!( expireDate.value.trim() == "Indefinite" || expireDate.value.trim() == "indefinite" || expireDate.value.trim() == "INDEFINITE" ))
// {
 if (expireDateString.value.trim().length == 0)
 { 
 errorMessage = errorMessage + " "+messagesData.expiredate+"\n" ;
 errorCount = 1;
 expireDate.value = "";
 }
//}
}
*/
if (action=="confirm")
{
  
  try
  {
     var lotStatus = document.getElementById("lotStatus"+rowNumber+"");
     if ( lotStatus.value.trim() == "Incoming" || lotStatus.value.length == 0 )
     {
         errorMessage = errorMessage +" "+messagesData.lotstatus +" " + messagesData.cannotbe+" " +messagesData.incoming  +". \n" ;
         errorCount = 1;
     }
     else if (lotStatus.value.length == 0)
     {
     var selecelemet = lotStatus.selectedIndex;
     var testelementtext = lotStatus.options[selecelemet].text;

      errorMessage = errorMessage +  " "+messagesData.nopermissionstoqcstatus +" "+testelementtext+".";
     errorCount = 1;
     }
  }
  catch (ex)
  {

  }
  }
}
   if (errorCount >0)
   {
    alert(errorMessage);
		//ok.checked = false;
		return false;
   }

   if (warnCount >0)
   {
       alert(warningMessage);
   }
   return true;
}

function cancel()
{
    window.close();
}

function hubchanged()
{
	var hubO = document.getElementById("sourceHub");

	try
   {
     var sourceHubName = null;
     sourceHubName =  hubO.options[hubO.selectedIndex].text;

     var sourceHubNameObject = document.getElementById("sourceHubName");
     sourceHubNameObject.value = sourceHubName;
   }
   catch (ex)
   {
     //alert("Non-Pickable Status");
   }

	var inventoryGroupO = document.getElementById("inventoryGroup");
	var selhub = hubO.value;

	for (var i = inventoryGroupO.length; i > 0;i--)
   {
      inventoryGroupO[i] = null;
   }
	showinvlinks(selhub);
	inventoryGroupO.selectedIndex = 0;
}

function showinvlinks(selectedhub)
{
    var invgrpid = new Array();
    invgrpid = altinvid[selectedhub];

	 var invgrpName = new Array();
    invgrpName = altinvName[selectedhub];

    if(invgrpid.length == 0)
    {
      setCab(0,messagesData.all,"")
    }

    for (var i=0; i < invgrpid.length; i++)
    {
        setCab(i,invgrpName[i],invgrpid[i])
    }
}

function setCab(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var inventoryGroupO = document.getElementById("inventoryGroup");
	 inventoryGroupO[href] = optionName;
}

function checkOriginalLot(rowNumber)
{
 try
 {
  var mfgLot = document.getElementById("mfgLot"+rowNumber+"");
  var origMfgLot = document.getElementById("origMfgLot"+rowNumber+"");

  if (origMfgLot.value.trim().length > 0)
  {
   var ok = document.getElementById("ok"+rowNumber+"");
   if (origMfgLot.value.trim() == mfgLot.value.trim())
   {
     if (ok.disabled)
     {
       ok.disabled = false;
     }
   }
   else
   {
     if (!ok.disabled)
     {
       ok.disabled = true;
     }
   }
  }
 }
 catch (ex)
 {
 }
}

function submitGenLabel(actionElementName)
{
 var actionElement = document.getElementById(""+actionElementName+"");
 actionElement.value = ""+actionElement+"";

 var submitReceive = document.getElementById("submitReceive");
 submitReceive.value = "";

 document.genericForm.submit();
}

function submitNonChemConfirm(actionElementName)
{
 var actionElement = document.getElementById(""+actionElementName+"");
 actionElement.value = ""+actionElement+"";

 var submitPrint = document.getElementById("submitPrint");
 submitPrint.value = "";
 var submitSearch = parent.document.getElementById("submitSearch");
 submitSearch.click();
 document.genericForm.submit();
}

function submitConfirm(actionElementName)
{
 var actionElement = document.getElementById(""+actionElementName+"");
 actionElement.value = ""+actionElement+"";

 var submitPrint = document.getElementById("submitPrint");
 submitPrint.value = "";

 if (checkAllChemicalLines())
 {
  var submitSearch = parent.document.getElementById("submitSearch");
  submitSearch.click();
  document.genericForm.submit();
 }
}

function changeMlItem()
{
 var checkedCount =0;
 var selectedItem  =  $("selectedItem");
 if (checkMlItemInput())/* selectedItem.value.trim().length > 0 && )*/
 {
   if ( selectedItem.value.trim().length > 0 )
   {
   var receiptsList = "";
   var totalRecords = $("totallines");
   for(j=0;j<(totalRecords.value*1);j++)
   {
    var receiptId  =  "";
    receiptId  =  $("receiptId"+(j)+"");
    currentcheckBox1 = $("ok"+(j)+"");
    itemType  =  $("itemType"+(j)+"");
    newChemRequestId  =  $("newChemRequestId"+(j)+"");

    if (currentcheckBox1.checked && itemType.value == "ML" && newChemRequestId.value.trim().length == 0)
    {
      if (checkedCount >0)
     {
      receiptsList += ','
     }
     receiptsList += receiptId.value;
     checkedCount++
    }
   }

   //var labelReceipts = document.getElementById("labelReceipts").value;
   receiptsList = receiptsList.replace(/,/gi, "%2c");
   if (receiptsList.trim().length > 0)
   {
   var loc = "/tcmIS/hub/receivingitemsearchmain.do?receipts="+receiptsList+"";
   var hubNumber = document.getElementById("sourceHub").value;
   loc = loc + "&hubNumber=" + hubNumber;
   loc = loc + "&listItemId=" +  $("selectedItem").value;
   loc = loc + "&inventoryGroup=" + $("selectedInventoryGroup").value;
   loc = loc + "&catPartNo=" + $("selectedCatPartNo").value;
   loc = loc + "&catalog=" +$("selectedCatalogId").value;
   loc = loc + "&catalogCompanyId=" +$("selectedCatalogCompanyId").value;

   openWinGeneric(loc,"changeItem","800","400","yes","80","80");
   }
  }
  }
}

function checkMlItemInput()
{
var noLinesChecked = 0;
var rowNumber ="";
//var currentcheckBox = $("ok"+rowNumber+"");
var totalRecords = $("totallines");
var selectedItem  =  $("selectedItem");
//var lineitemID  =  $("itemId"+rowNumber+"");

/*if (currentcheckBox.checked)
{
 noLinesChecked ++;
}*/

/*if ( selectedItem.value.trim().length > 0 && (lineitemID.value != selectedItem.value) )
{
  alert("You cannot choose a receipt with Different ML Item");
  currentcheckBox.checked = false;
  return false;
}*/

var allClear = 0;
var finalMsgt;
finalMsgt = messagesData.cannotselectreceiptwith +" \n\n";

for(j=0;j<(totalRecords.value*1);j++)
{
 var lineitemID1  =  "";
 lineitemID1  =  $("itemId"+(j)+"");
 itemType  =  $("itemType"+(j)+"");
 currentcheckBox1 = $("ok"+(j)+"");
 newChemRequestId  =  $("newChemRequestId"+(j)+"");

 if (currentcheckBox1.checked && itemType.value == "ML")
 {
  if (noLinesChecked == 0)
  {
    lineitemID  =  $("itemId"+(j)+"");
    rowNumber = j;
  }

  noLinesChecked ++;
  if (lineitemID.value != lineitemID1.value)
  {
    if (allClear == 0)
    {
     finalMsgt = finalMsgt + messagesData.differentmlitem+"\n";
    }
    allClear += 1;
   }
   else if (newChemRequestId.value.trim().length > 0)
   {
    if (allClear == 0)
    {
     finalMsgt = finalMsgt +" "+ messagesData.pendingnewchemrequest+" "+newChemRequestId.value+"\n";
    }
    allClear += 1;
   }
 }

}

if ( noLinesChecked == 0)
{
  selectedItem.value = "";
}

if (allClear < 1)
{
  if (noLinesChecked != 0)
  {
  selectedItem.value =  $("itemId"+rowNumber+"").value; 
  $("selectedInventoryGroup").value = $("inventoryGroup"+rowNumber+"").value;
  $("selectedCatalogId").value = $("catalogId"+rowNumber+"").value;
  $("selectedCatPartNo").value = $("catPartNo"+rowNumber+"").value;
  $("selectedCatalogCompanyId").value = $("catalogCompanyId"+rowNumber+"").value;
  }
  return true;
}
else
{
    alert(finalMsgt);    
    return false;
}
}

function $(a) {
	return document.getElementById(a);
}

function submitSearchForm()
{
 var submitSearch = document.getElementById("submitSearch");
 submitSearch.click();
 //document.genericForm.submit();
}

function unitLabelPartNumber(rowNumber)
{
 var unitLabelPrinted =  document.getElementById("unitLabelPrinted"+rowNumber+"");
 if (unitLabelPrinted.checked)
 {
  var itemId =  document.getElementById("itemId"+rowNumber+"");
  var branchPlant =  document.getElementById("branchPlant"+rowNumber+"");
  var inventoryGroup =  document.getElementById("inventoryGroup"+rowNumber+"");

  loc = "/tcmIS/hub/unitlabelpartnumber.do?&rowNumber=" + rowNumber;
	loc = loc + "&itemId=" +itemId.value;
	loc = loc + "&hub=" +branchPlant.value;
	loc = loc + "&inventoryGroup=" +inventoryGroup.value;
	openWinGeneric(loc,"terminal_root_cause","500","300","no");
 }
}

function sendUnitLabelPartNumber()
{
rowNumber = document.getElementById("rowNumber").value;
catPartNo = document.getElementById("catPartNo");

openerunitLabelCatPartNo = opener.document.getElementById("unitLabelCatPartNo"+rowNumber+"");
openerunitLabelCatPartNo.value = catPartNo.value;

window.close();
}