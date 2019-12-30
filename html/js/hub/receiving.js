/**todo Internationalize**/
var submitCount=0;
var updatecount=0;

var selectedRowId=null;
var children = new Array();

var dhxFreezeWins = null;

function SubmitOnlyOnce()
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

   if (submitCount == 0)
   {
        submitCount++;
        try
        {
         //Turns off the main page and shows the transitions page
         var transitPage = document.getElementById("transitPage");
         transitPage.style["display"] = "";

         var mainPage = document.getElementById("mainPage");
         mainPage.style["display"] = "none";
         /*This is for IE, fomr some reason the table background is visible*/
         var resultsMaskTable = document.getElementById("resultsMaskTable");
         resultsMaskTable.style["display"] = "none";
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

function buildDropDown( arr, defaultObj, eleName ) {
   var obj = $(eleName);
   for (var i = obj.length; i > 0;i--)
     obj[i] = null;
  // if size = 1 or 2 show last one, first one is all, second one is the only choice.
  if( arr == null || arr.length == 0 ) 
	  setOption(0,defaultObj.name,defaultObj.id, eleName); 
  else if( arr.length == 1 )
	  setOption(0,arr[0].name,arr[0].id, eleName);
  else {
      var start = 0;
  	  if( defaultObj.nodefault )
  	  	start = 0 ; // will do something??
  	  else {
	  setOption(0,defaultObj.name,defaultObj.id, eleName); 
		  start = 1;
	  }
	  for ( var i=0; i < arr.length; i++) {
	    	setOption(start++,arr[i].name,arr[i].id,eleName);
	  }
  }
  obj.selectedIndex = 0;
}

function setOps() {
 	buildDropDown(opshubig,defaults.ops,"opsEntityId");
 	$('opsEntityId').onchange = opsChanged;
    $('sourceHub').onchange = hubChanged;
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0){
    	$('opsEntityId').value = defaultOpsEntityId;
    }
    opsChanged();
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0 && defaultHub != null && defaultHub.length > 0) {
    	$('sourceHub').value = defaultHub;
    	hubChanged();
}
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0 && defaultHub != null && defaultHub.length > 0 && preferredInventoryGroup != null && preferredInventoryGroup.length > 0)
    	$('inventoryGroup').value = preferredInventoryGroup;
}

function opsChanged()
{
   var opsO = $("opsEntityId");
   var arr = null;
   if( opsO.value != '' ) {
   	   for(i=0;i< opshubig.length;i++)
   	   		if( opshubig[i].id == opsO.value ) {
   	   			arr = opshubig[i].coll;
   	   			break;
   	   		}
   }

   buildDropDown(arr,defaults.hub,"sourceHub");
   hubChanged();
   if( defaults.ops.callback ) defaults.ops.callback();
}

function hubChanged()
{
   var opsO = $("opsEntityId");
   var hubO = $("sourceHub");
   var arr = null;
   if( opsO.value != '' && hubO.value != '' ) {
   	   for(i=0;i< opshubig.length;i++)
   	   		if( opshubig[i].id == opsO.value ) {
		   	   for(j=0;j< opshubig[i].coll.length;j++)
	   	   		if( opshubig[i].coll[j].id == hubO.value ) {
	   	   			document.getElementById("sourceHubName").value =  hubO.options[hubO.selectedIndex].text;
	   	   			arr = opshubig[i].coll[j].coll;
	   	   			if($v('category') == 'Chemicals')
	   	   			{
		   	   			if(opshubig[i].coll[j].autoPutHub == 'Y')
		   	   				$('isAutoPutHub').value = 'Y';
		   	   			else
		   	   				$('isAutoPutHub').value = 'N';
	   	   			}
	   	   			else
	   	   				$('isAutoPutHub').value = 'N';
	   	   			break;
   	   		    }
   	   		 break;
   	   		}
   }
   buildDropDown(arr,defaults.inv,"inventoryGroup");
   if( defaults.hub.callback ) defaults.hub.callback();

}

function setDefault() {
	if ($v("selectedHub").length > 0) {
		$("opsEntityId").value = $v("selectedOpsEntityId");
		opsChanged();
	}

	if ($v("selectedHub").length > 0) {
		$("sourceHub").value = $v("selectedHub");
		hubChanged();
	}

	if ($v("selectedInventoryGroup").length > 0)
		$("inventoryGroup").value = $v("selectedInventoryGroup");

}


function selectRow(rowId) {
	var selectedRow = document.getElementById("rowId" + rowId + "");

	var selectedRowClass = document.getElementById("colorClass" + rowId + "");
	selectedRow.className = "selected" + selectedRowClass.value + "";

	if (selectedRowId != null && rowId != selectedRowId) {
		var previouslySelectedRow = document.getElementById("rowId" + selectedRowId + "");
		var previouslySelectedRowClass = document.getElementById("colorClass" + selectedRowId + "");
		previouslySelectedRow.className = "" + previouslySelectedRowClass.value + "";
	}
	selectedRowId = rowId;

	var dataCount = $v("selectedDataCount" + selectedRowId + "");
	var transType = $v("transType" + dataCount + "");
	var radianPo = $v("radianPo" + dataCount + "");
	var lineItem = $v("lineItem" + dataCount + "");
	var customerRmaId = $v("customerRmaId" + dataCount + "");
	var itemId = $v("itemId" + dataCount + "");

	vitems = new Array();
	if (radianPo.length > 0) {
		if (transType == 'IT') {
			vitems[vitems.length] = "text=" + messagesData.titlebar + ";url=javascript:showrecforinvtransfr();";
			vitems[vitems.length] = "text=" + messagesData.receivingchecklist + ";url=javascript:showReceivingJacket();";
			vitems[vitems.length] = "text=" + messagesData.itemtitle + ";url=javascript:showPreviousrec();";
		}
		else {
			if (lineItem.length > 0) {
				if(!disabledPoLink)
					vitems[vitems.length] = "text=" + messagesData.viewpurchaseorder + ";url=javascript:showRadianPo();";
				vitems[vitems.length] = "text=" + messagesData.potitle + ";url=javascript:showPreviousPoLineReceipts();";
				vitems[vitems.length] = "text=" + messagesData.receivingchecklist + ";url=javascript:showReceivingJacket();";
				vitems[vitems.length] = "text=" + messagesData.itemtitle + ";url=javascript:showPreviousrec();";
			}
			else {
				if(!disabledPoLink)
					vitems[vitems.length] = "text=" + messagesData.viewpurchaseorder + ";url=javascript:showRadianPo();";
				vitems[vitems.length] = "text=" + messagesData.receivingchecklist + ";url=javascript:showReceivingJacket();";
				vitems[vitems.length] = "text=" + messagesData.itemtitle + ";url=javascript:showPreviousrec();";
			}
		}
	}
	else {
		if (transType == 'IT') {
			if (!customerRmaId.length > 0) {
				// If it's NOT an RMA, add show invoice for transfer to menu
				vitems[vitems.length] = "text=" + messagesData.titlebar + ";url=javascript:showrecforinvtransfr();";
			}
			vitems[vitems.length] = "text=" + messagesData.itemtitle + ";url=javascript:showPreviousrec();";
		}
		else {
			if (lineItem.length > 0) {
				vitems[vitems.length] = "text=" + messagesData.potitle + ";url=javascript:showPreviousPoLineReceipts();";
				vitems[vitems.length] = "text=" + messagesData.itemtitle + ";url=javascript:showPreviousrec();";
			}
			else {
				vitems[vitems.length] = "text=" + messagesData.itemtitle + ";url=javascript:showPreviousrec();";
			}
		}
	}
	if (transType == 'IA' && customerRmaId != null)
		vitems[vitems.length] = "text=" + messagesData.viewrma + ";url=javascript:viewRMA();";

	if (itemId.length > 0 && !disabledItemNotes)
		vitems[vitems.length] = "text=" + messagesData.itemnotes + ";url=javascript:showItemNotes();";
	
	if(printGHSLabels){
		vitems[vitems.length] = "text="+messagesData.printGHSLabels + ";url=javascript:printGHSLabel();";
	}

	/*
	 * if (customerRmaId.trim().length > 0) vitems[vitems.length ] =
	 * "text="+messagesData.viewcustomerreturnrequest+";url=javascript:showCustomerReturnRequest();";
	 */

	replaceMenu('receivingMenu', vitems);

	toggleContextMenu('receivingMenu');
	// } catch(ex){}
}

function replaceMenu(menuname,menus){
	  var i = mm_returnMenuItemCount(menuname);

	  for(;i> 1; i-- )
			mm_deleteItem(menuname,i);

	  for( i = 0; i < menus.length; ){
 		var str = menus[i];

 		i++;
		mm_insertItem(menuname,i,str);
		// delete original first item.
    	if( i == 1 ) mm_deleteItem(menuname,1);
      }
}

function selectRowNonChem(rowId) {
   var selectedRow = document.getElementById("rowId"+rowId+"");

   var selectedRowClass = document.getElementById("colorClass"+rowId+"");
   selectedRow.className = "selected"+selectedRowClass.value+"";

   if (selectedRowId != null && rowId != selectedRowId)
   {
     var previouslySelectedRow = document.getElementById("rowId"+selectedRowId+"");
     var previouslySelectedRowClass = document.getElementById("colorClass"+selectedRowId+"");
     previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";
   }
   selectedRowId =rowId;

	toggleContextMenu('showPreviousReceipt');
}

function showRadianPo(poNumber) {
  var dataCount =  document.getElementById("selectedDataCount"+selectedRowId+"").value;
  var poNumber =  document.getElementById("radianPo"+dataCount+"").value;

  loc = "/tcmIS/supply/purchaseorder.do?po=" + poNumber + "&Action=searchlike&subUserAction=po";
  try {
 	children[children.length] = openWinGeneric(loc,"showradianPo","800","600","yes","50","50","yes");
 } catch (ex){
 	openWinGeneric(loc,"showradianPo","800","600","yes","50","50","yes");
 }
}

function viewRMA() {
  var dataCount =  document.getElementById("selectedDataCount"+selectedRowId+"").value;
  var customerRmaId =  document.getElementById("customerRmaId"+dataCount+"").value;
  var lineItem =  document.getElementById("lineItem"+dataCount+"").value;

  var loc = "/tcmIS/distribution/customerreturnrequest.do?action=search&rmaId="+customerRmaId+"&lineItem="+lineItem; //+"&prNumber="+prNumber;

  try
	{
		parent.parent.openIFrame("showcustomerreturnrequest"+customerRmaId+"",loc,""+messagesData.customerreturnrequest+" "+customerRmaId+"","","N");
	}
	catch (ex)
	{
		openWinGeneric(loc,"showcustomerreturnrequest","900","600","yes","80","80","yes");
	}

}

function validateForm() {
   var result = true;
   var what = document.getElementById('searchWhat');
   if( what.value != "customerPo" && what.value != "itemDescription" && what.value != "lastSupplier" &&
   	   !isFloat($v('search').trim(),true) ) {
   	   alert( what.options[what.selectedIndex].text+ " : "+messagesData.mustbeanumberinthisfield);
   	   document.getElementById('search').focus();
   	   result = false;
   }
   document.genericForm.target='';
   return result;
}

function doexcelpopup()
{
 var flag = validateForm();
 if(flag == false) return false;
 var userAction = document.getElementById("userAction");
 userAction.value = "createExcel";

 try {
 	children[children.length] = openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_newGenerateExcel','800','450','yes');
 } catch (ex){
 	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_newGenerateExcel','800','450','yes');
 }

 document.genericForm.target='_newGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
    return false;
}

function closedatedivs()
{
var target1 = document.all.item("DATEPAGE");
target1.style["display"] = "none";
}

function assignpaperSize(size)
{
 var paperSize = document.getElementById("paperSize");
 paperSize.value =size;
}

function checkClosePoLine(rowNumber)
{

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
  	 alert(messagesData.nopermissiontochangestatus +" "+testelementtext+".")
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
 /*else
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
 }*/
}

function printContainerLabels()
{
 var labelReceipts = document.getElementById("labelReceipts").value;
 labelReceipts = labelReceipts.replace(/,/gi, "%2c");
 //var loc = "/tcmIS/hub/printcontainerlabels.do?labelReceipts="+labelReceipts+"";
 var paperSize = document.getElementById("paperSize").value;
 var loc = loc + "&paperSize=" + paperSize;
 var hubNumber = document.getElementById("hubNumber").value;
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
 try {
 	children[children.length] = openWinGeneric(loc,"printContainerLabels11","800","500","yes","80","80");
 } catch (ex){
 	openWinGeneric(loc,"printContainerLabels11","800","500","yes","80","80");
 }
}

function printRecDocumentLabels()
{
 var labelReceipts = document.getElementById("labelReceipts").value;
 labelReceipts = labelReceipts.replace(/,/gi, "%2c");
 var loc = "/tcmIS/hub/printcontainerlabels.do?labelReceipts="+labelReceipts+"";
 loc = loc + "&paperSize=receiptDocument";
 var hubNumber = document.getElementById("hubNumber").value;
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
 try {
 	children[children.length] = openWinGeneric(loc,"printContainerLabels11","800","500","yes","80","80");
 } catch (ex){
 	openWinGeneric(loc,"printRecDocumentLabels11","800","500","yes","80","80");
 }
}

function printBinLabels()
{
 var labelReceipts = document.getElementById("labelReceipts").value;
 labelReceipts = labelReceipts.replace(/,/gi, "%2c");
 var loc = "/tcmIS/hub/printbinlabels.do?labelReceipts="+labelReceipts+"";
 var paperSize = document.getElementById("paperSize").value;
 loc = loc + "&paperSize=" + paperSize;
 var hubNumber = document.getElementById("hubNumber").value;
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
 loc = loc + "&binLabels=Yes";

 try {
 	children[children.length] = openWinGeneric(loc,"printBinLabels11","800","500","yes","80","80");
 } catch (ex){
 	openWinGeneric(loc,"printBinLabels11","800","500","yes","80","80");
 }
}

function printReceivingBoxLabels()
{
 var labelReceipts = document.getElementById("labelReceipts").value;
 labelReceipts = labelReceipts.replace(/,/gi, "%2c");
 var loc = "/tcmIS/hub/printreceiptboxlabels.do?labelReceipts="+labelReceipts+"";
 loc = loc + "&paperSize=64";
 try {
 	children[children.length] = openWinGeneric(loc,"printBinLabels11","800","500","yes","80","80");
 } catch (ex){
 	openWinGeneric(loc,"printBinLabels11","800","500","yes","80","80");
 }

}
var bg;
function updateRoom(r)
{
	try{
		bg.cellById(bg.getSelectedRowId(),bg.getColIndexById("room")).setValue(r[0].room);
		opener.parent.closeTransitWin();
		cancel();
	}catch(ex){}
}

function addBintoMainPage()
{
 var vvHubBin = document.getElementById("vvHubBin").value;
 var selectedRowNumber = document.getElementById("rowNumber").value;
 var branchPlant = document.getElementById("branchPlant").value;
 if(opener.document.title == "Reconciliation")
 {
	 try{
	 bg = opener.window.beangrid;
	 bg.cellById(bg.getSelectedRowId(),bg.getColIndexById("bin")).setValue(vvHubBin);
	 callToServer("reconciliationmain.do?callback=updateRoom&uAction=getRoom&binTo="+vvHubBin + "&hub=" + branchPlant); 
	 }catch(e){}
	 return null;
 }
 else if(opener.document.title == "Pick New Bin")
	 {
	    var vvHubBinSelect = opener.document.getElementById("vvHubBin");
	 	if(vvHubBinSelect != null)
	 		 opener.addOptionItem(vvHubBin,vvHubBin);
	 	//this.close();
	 }
 else{
 var totallines = opener.document.getElementById("totallines").value*1;
 var selectedRow = false;

 var itemId = document.getElementById("itemId");

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

  var loc = "showhubbin.do?branchPlant="+branchPlant.value+"&userAction=showBins&rowNumber="+rowNumber+"&itemId=";
  
  if ($v("pageid") == 'receiving') 
	  loc = "/tcmIS/hub/" + loc;
  
  loc = loc + itemId.value;
  try {
 	children[children.length] = openWinGeneric(loc,"showVvHubBins","300","150","no","80","80");
 } catch (ex){
 	openWinGeneric(loc,"showVvHubBins","300","150","no","80","80");
 }
}

function showPreviousrec(rowNumber)
{
 var dataCount =  document.getElementById("selectedDataCount"+selectedRowId+"").value;
 var itemId = document.getElementById("itemId"+dataCount+"");
 var branchPlant = document.getElementById("branchPlant"+dataCount+"");
 var inventoryGroup = document.getElementById("inventoryGroup"+dataCount+"");

 /*loc = "/tcmIS/Hub/Receiving?session=Active&UserAction=previousreceiving&poline=";
 loc = loc + itemId.value;
 loc = loc + "&HubNo=" + branchPlant.value;*/

 var loc = "showreceivedreceipts.do?itemId="+itemId.value+"&hub="+branchPlant.value+"&inventoryGroup="+inventoryGroup.value+"";
 
 if ($v("pageid") == 'receiving') 
	  loc = "/tcmIS/hub/" + loc;
 
 try {
 	children[children.length] = openWinGeneric(loc,"Previous_Receiving","700","450","yes");
 } catch (ex){
 	openWinGeneric(loc,"Previous_Receiving","700","450","yes");
 }
}

function showPreviousPoLineReceipts(rowNumber)
{
 var dataCount =  document.getElementById("selectedDataCount"+selectedRowId+"").value;
 var radianPo = document.getElementById("radianPo"+dataCount+"");
 var poLine = document.getElementById("lineItem"+dataCount+"");
 var branchPlant = document.getElementById("branchPlant"+dataCount+"");
 var inventoryGroup = document.getElementById("inventoryGroup"+dataCount+"");

 var loc = "showreceivedreceipts.do?radianPo="+radianPo.value+"&poLine="+poLine.value+"&hub="+branchPlant.value+"&inventoryGroup="+inventoryGroup.value+"";
 
 if ($v("pageid") == 'receiving') 
	  loc = "/tcmIS/hub/" + loc;
 
 try {
 	children[children.length] = openWinGeneric(loc,"Previous_PO_Line_Receiving","700","450","yes");
 } catch (ex){
 	openWinGeneric(loc,"Previous_PO_Line_Receiving","700","450","yes");
 }
}

function showPreviousPoNonChemReceipts(rowNumber)
{
 var dataCount = document.getElementById("selectedDataCount"+selectedRowId+"").value;
 var radianPo = document.getElementById("radianPo"+dataCount+"");
 var branchPlant = document.getElementById("branchPlant"+dataCount+"");
 var inventoryGroup = document.getElementById("inventoryGroup"+dataCount+"");
 var poLine = document.getElementById("lineItem"+dataCount+"");

 var loc = "showreceivedreceipts.do?radianPo="+radianPo.value+"&hub="+branchPlant.value+"&inventoryGroup="+inventoryGroup.value+"&poLine="+poLine.value+"";
 
 if ($v("pageid") == 'receiving') 
	  loc = "/tcmIS/hub/" + loc;
 
 try {
 	children[children.length] = openWinGeneric(loc,"Previous_PO_Line_Receiving","700","450","yes");
 } catch (ex){
 	openWinGeneric(loc,"Previous_PO_Line_Receiving","700","450","yes");
 }
}

function showrecforinvtransfr(rowNumber)
{
    var dataCount =  document.getElementById("selectedDataCount"+selectedRowId+"").value;
    var transferRequestId = $v("transferRequestId"+dataCount+"");
    var loc = "transfertransactions.do?transferRequestId="+transferRequestId;
    
    if ($v("pageid") == 'receiving') 
  	  loc = "/tcmIS/hub/" + loc;
    
 //   loc = loc + transferRequestId.value;
    try {
 		children[children.length] = openWinGeneric(loc,"Previous_Transfer_Transactions","700","400","yes");
 	} catch (ex){
 		openWinGeneric(loc,"Previous_Transfer_Transactions","700","400","yes");
 	}
}

function showReceivingJacket(rowNumber)
{
 var dataCount =  document.getElementById("selectedDataCount"+selectedRowId+"").value;
 var radianPo = document.getElementById("radianPo"+dataCount+"");
 var lineItem = document.getElementById("lineItem"+dataCount+"");
 var itemId = document.getElementById("itemId"+dataCount+"");
 var branchPlant = document.getElementById("branchPlant"+dataCount+"");

    loc = "/cgi-bin/radweb/rec_sheet.cgi?po=";
    loc = loc + radianPo.value;
    loc = loc + "&item=" + itemId.value;
    loc = loc + "&line=" + lineItem.value;
    if (branchPlant.value == "2101")
    {
       loc = loc + "&tab=N";
    }
    else
    {
       loc = loc + "&tab=Y";
    }
    //alert(loc);
    try {
 		children[children.length] = openWinGenericViewFile(loc,"showReceivingJacket11","600","600","yes");
 	} catch (ex){
 		openWinGenericViewFile(loc,"showReceivingJacket11","800","600","yes");
 	}
}

function showChemicalReceivedReceipts(receivedReceipts)
{
 receivedReceipts = receivedReceipts.replace(/,/gi, "%2c");
 var loc = "showchemreceivedreceiptsmain.do?justReceived=y&receivedReceipts="+receivedReceipts+"";
 
 if ($v("pageid") == 'receiving') 
	  loc = "/tcmIS/hub/" + loc;
 
 var sourceHub = document.getElementById("sourceHub");
 var inventoryGroup = document.getElementById("inventoryGroup");
 loc = loc + "&sourceHub="+escape(sourceHub.value)+"&inventoryGroup="+inventoryGroup.value+"";
 try {
 	children[children.length] = openWinGeneric(loc,"showChemicalReceivedReceipts11","800","600","yes","80","80");
 } catch (ex){
 	openWinGeneric(loc,"showChemicalReceivedReceipts11","800","600","yes","80","80");
 }

}

function showNonChemicalReceivedReceipts(receivedReceipts)
{
 receivedReceipts = receivedReceipts.replace(/,/gi, "%2c");
 var loc = "shownonchemicalreceivedreceipts.do?justReceived=y&receivedReceipts="+receivedReceipts+"";
 
 if ($v("pageid") == 'receiving') 
	  loc = "/tcmIS/hub/" + loc;
 
 var sourceHub = document.getElementById("sourceHub");
 var inventoryGroup = document.getElementById("inventoryGroup");
 loc = loc + "&sourceHub="+escape(sourceHub.value)+"&inventoryGroup="+inventoryGroup.value+"";
 try {
 	children[children.length] = openWinGeneric(loc,"shownonChemicalReceivedReceipts11","800","600","yes","80","80");
 } catch (ex){
 	openWinGeneric(loc,"shownonChemicalReceivedReceipts11","800","600","yes","80","80");
 }
}

function showItemNotes() {
	var dataCount =  $v("selectedDataCount"+selectedRowId+"");
 	var itemId = $v("itemId"+dataCount+"");
	var loc = "edititemnotes.do?itemId=" + itemId;
 	
 	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/supply/" + loc;
 	
   	var winId= 'showItemNotes';

	children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"800","600","yes","50","50","20","20","no");
}

function reprint()
{
 var category = document.getElementById("category");
 var sourceHub = document.getElementById("sourceHub");
 var sourceHubName = document.getElementById("sourceHubName");
 var inventoryGroup = document.getElementById("inventoryGroup");
 if (sourceHubName.value.length > 0 && sourceHub.value.length > 0)
 {
     if (category.value == "Chemicals")
     {
	     var loc = "showchemreceivedreceiptsmain.do?sourceHub="+sourceHub.value+"&inventoryGroup="+inventoryGroup.value+"";
    	 
    	 if ($v("pageid") == 'receiving') 
    		  loc = "/tcmIS/hub/" + loc;
    	 
	     try {
		 	children[children.length] = openWinGeneric(loc,"showChemicalReceivedReceipts11","800","600","yes","80","80");
		 } catch (ex){
		 	openWinGeneric(loc,"showChemicalReceivedReceipts11","800","600","yes","80","80");
		 }
     }
     else
     {
	     var loc = "shownonchemicalreceivedreceipts.do?sourceHub="+sourceHub.value+"&inventoryGroup="+inventoryGroup.value+"";
	     try {
		 	children[children.length] = openWinGeneric(loc,"shownonChemicalReceivedReceipts11","800","600","yes","80","80");
		 } catch (ex){
		 	openWinGeneric(loc,"shownonChemicalReceivedReceipts11","800","600","yes","80","80");
		 }
     }
 }
}

function addnewBin()
{
 var sourceHub = document.getElementById("sourceHub");
 var sourceHubName = document.getElementById("sourceHubName");

 if (sourceHubName.value.length > 0 && sourceHub.value.length > 0)
 {
  var loc = "addhubbin.do?branchPlant="+sourceHub.value+"&sourceHubName="+escape(sourceHubName.value)+"&userAction=addNewBin";
  
  if ($v("personnelCompanyId") == 'Radian') 
	  loc = "/tcmIS/hub/" + loc;
  
  try {
 	children[children.length] = openWinGeneric(loc,"addnewBin","400","200","no","80","80");
  } catch (ex){
 	openWinGeneric(loc,"addnewBin","600","200","no","80","80");
  }
 }
}

function showLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows()
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 180);
  legendWin.setText(messagesData.showlegend);
  legendWin.clearIcon();  // hide icon
  legendWin.denyResize(); // deny resizing
  legendWin.denyPark();   // deny parking
  legendWin.attachObject("showLegendArea");
  legendWin.attachEvent("onClose", function(legendWin){legendWin.hide();});
  legendWin.center();
  }
  else
  {
    // just show
    dhxWins.window("legendwin").show();
  }
} 

function duplLine(rowNumber)
{
	var duplicateLine = document.getElementById("duplicateLine");
	duplicateLine.value = rowNumber;

   return true;
}

function duplpkg(rowNumber)
{
	var duplicatePkgLine = document.getElementById("duplicatePkgLine"+rowNumber+"");
	duplicatePkgLine.value = rowNumber;
   //alert(rowNumber);
	var duplicatePkgLine = document.getElementById("duplicatePkgLine");
	duplicatePkgLine.value = rowNumber;

   return true;
}

function duplkit(rowNumber)
{
 var kitSize  =  (document.getElementById("kitSize"+rowNumber+"").value)*1;
 //alert("rownum "+rowNumber+"  kitSize  "+kitSize+"");

 var firstRowNum =0;
 for ( var j = 0 ; j < kitSize ; j ++)
 {
   firstRowNum = (rowNumber-j);
 }

 for ( var j = 0 ; j < kitSize ; j ++)
 {
  var duplicateKitLine  =  document.getElementById("duplicateKitLine"+(rowNumber-j)+"");
  duplicateKitLine.value = firstRowNum;
 }

 var duplicateKitLine = document.getElementById("duplicateKitLine");
 duplicateKitLine.value = firstRowNum;

 return true;
}

function checkNonChemicalReceivingInput(rowNumber)
{
var errorMessage = " "+messagesData.validvalues+" \n\n";
var warningMessage = messagesData.alert+" \n\n";
var errorCount = 0;
var warnCount = 0;

var ok = document.getElementById("ok"+rowNumber+"");
var nonintegerReceiving = document.getElementById("nonintegerReceiving"+rowNumber+"");

if (ok.checked)
{
var mfgLot = document.getElementById("mfgLot"+rowNumber+"");
if (mfgLot.value.trim().length == 0)
{
 errorMessage = errorMessage + " " +messagesData.supplieref+ ". \n" ;
 errorCount = 1;
}

/*
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
*/
var dateOfReceipt = document.getElementById("dateOfReceipt"+rowNumber+"");
          if (dateOfReceipt.value.trim().length == 0) {            
            errorMessage = errorMessage + " "+messagesData.dor+"\n" ;
            errorCount = 1;
            dateOfReceipt.value = document.getElementById("todayDate").value;
          }

try
{
 var quantityReceived = document.getElementById("quantityReceived"+rowNumber+"");
 if ( !(isInteger(quantityReceived.value)) || quantityReceived.value*1 == 0 )
 {
   if (!(nonintegerReceiving.value == "Y" && isFloat(quantityReceived.value)) )
	 {
    errorMessage = errorMessage +" "+ messagesData.quantityreceived + ". \n";
	  errorCount = 1;
    quantityReceived.value = "";
	 }
 }

 var qtyOpen = document.getElementById("qtyOpen"+rowNumber+"");
 if (qtyOpen.value.trim()*1 < quantityReceived.value.trim()*1)
 {
   warningMessage = warningMessage + " " +label.qtyreceivedgreaterthanopen+". \n";
	warnCount = 1;
 }
}
catch (ex)
{

}

try
{
   var lotStatus = document.getElementById("lotStatus"+rowNumber+"");
   if ( lotStatus.value  == "Incoming" || lotStatus.value.length == 0)
   {
       errorMessage = errorMessage + " "+ messagesData.lotstatus +" " + messagesData.cannotbe+" " +messagesData.incoming + ". \n" ;
       errorCount = 1;
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
		return false;
   }

   if (warnCount >0)
   {
       alert(warningMessage);
   }
   return true;
}

function checkAllNonChemicalLines()
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
      var lineResult = checkNonChemicalReceivingInput(p);
      if (lineResult == false)
      {
	    result = false;
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
      var lineResult = checkChemicalReceivingInput(p,"N");
      if (lineResult == false)
      {
	    result = false;
	    return false;
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


function checkChemicalReceivingInput(rowNumber,checReceipt)
{
	var errorMessage = " "+messagesData.validvalues+" \n\n";
	var warningMessage = messagesData.alert+" \n\n";
	var errorCount = 0;
	var warnCount = 0;
	var qtyErrorMessage = "";
	var qtyErrorCount = 0;
	
	var ok = document.getElementById("ok"+rowNumber+"");
	if (ok.checked)
	{
		var closePoAllowed = false;
		try
		{
			var closePoLine = document.getElementById("closePoLine"+rowNumber+"");
			if (closePoLine.checked)
			{
			 closePoAllowed = true;
			}
		}
		catch (ex)
		{
			closePoAllowed = false;
		}
		
		try
		{
			 var mvItem = document.getElementById("mvItem"+rowNumber+"");
			 var rowSpan = document.getElementById("rowSpan"+rowNumber+"");
			 var quantityReceived = document.getElementById("quantityReceived"+rowNumber+"");
			 var nonintegerReceiving = document.getElementById("nonintegerReceiving"+rowNumber+"");
			 
			 
			 var totalMvItemRecevied = 0;

			 if (mvItem.value == "Y")
			 {
			  for ( var p = 0 ; p < rowSpan.value ; p ++)
			  {
			  var packagedQty = document.getElementById("packagedQty"+(rowNumber+p)+"");
			  var packagedSize = document.getElementById("packagedSize"+(rowNumber+p)+"");
			
			  if (packagedQty.value.length > 0 && ( !(isInteger(packagedQty.value)) || packagedQty.value*1 == 0 ))
			  {
			   if (closePoAllowed && closePoLine.checked && packagedQty.value*1 == 0)
			   {
			
			   }
			   else
			   {
			   errorMessage = errorMessage + " " +messagesData.packagedqtyreceived+" \n";
				errorCount = 1;
			   packagedQty.value = "";
			   }
			  }
			
			  totalMvItemRecevied = totalMvItemRecevied +(packagedQty.value*1)*(packagedSize.value*1);
			
			  var purchasingUnitsPerItem = document.getElementById("purchasingUnitsPerItem"+(rowNumber)+"");
			  var purchasingUnitOfMeasure = document.getElementById("purchasingUnitOfMeasure"+(rowNumber)+"");
			  if (packagedQty.value.length > 0 && ( !(isInteger(packagedQty.value)) || packagedQty.value*1 == 0 ))
			  {
			  if (Math.abs(((packagedSize.value*1-purchasingUnitsPerItem.value*1)/purchasingUnitsPerItem.value*1)*100) > 25)
			  {
			   warningMessage = warningMessage + +" "+ messagesData.checkpackagedsize+"  ("+purchasingUnitsPerItem.value+" "+purchasingUnitOfMeasure.value+") \n";
			   warnCount = 1;
			  }
			  }
			  }
			
			  //alert (""+Math.round(100*totalMvItemRecevied)/100+" !==  "+Math.round(100*(quantityReceived.value.trim()*1))/100+"");
			  if (Math.round(100*totalMvItemRecevied)/100 !==  Math.round(100*(quantityReceived.value.trim()*1))/100)
			  //if (totalMvItemRecevied !== quantityReceived.value.trim()*1)
			  {
			   errorMessage = errorMessage + " "+messagesData.qtyreceivednotmatch+" \n";
			   errorCount = 1;
			  }
			 }
			 else
			 {
			  if ( !(isInteger(quantityReceived.value)) || quantityReceived.value*1 == 0 )
			  {
			   if (closePoAllowed && closePoLine.checked && quantityReceived.value*1 == 0)
			   {
			
				 }
				 else if (!(nonintegerReceiving.value == "Y" && isFloat(quantityReceived.value)) )
				 {
			    errorMessage = errorMessage + " "+messagesData.quantityreceived + " \n";
				  errorCount = 1;
			    quantityReceived.value = "";
			   }
			  }
			 }
			
			 var qtyOpen = document.getElementById("qtyOpen"+rowNumber+"");
			 if (Math.round(100*(qtyOpen.value.trim()*1))/100 < Math.round(100*(quantityReceived.value.trim()*1))/100)
			 //if (qtyOpen.value.trim()*1 < quantityReceived.value.trim()*1)
			 {
			  warningMessage = warningMessage + " " + messagesData.qtyreceivedgreaterthanopen+" \n";
			  warnCount = 1;
			 }
			
			 if (((quantityReceived.value.trim()*1-qtyOpen.value.trim()*1)/qtyOpen.value.trim()*1)*100 > 25)
			 {
			  qtyErrorMessage = qtyErrorMessage + " " + messagesData.qtybeingreceived +" ("+quantityReceived.value.trim()+") " +messagesData.is+" "+Math.round(10000*(quantityReceived.value.trim()*1-qtyOpen.value.trim()*1)/qtyOpen.value.trim()*1)/100+"% "+messagesData.greaterthanqtyopen+" ("+qtyOpen.value.trim()+").\n"+messagesData.polinecannotreceived+"\n";
			  qtyErrorCount = 1;
			 }
		}
		catch (ex)
		{
		
		}
		
		if (closePoAllowed)
		{
		
		}
		else
		{
			var bin = document.getElementById("bin"+rowNumber+"");
			if (bin.value == "NONE")
			{
			 errorMessage = errorMessage +   " "+messagesData.bin+" \n" ;
			 errorCount = 1;
			}
			
			var mfgLot = document.getElementById("mfgLot"+rowNumber+"");
			if (mfgLot.value.trim().length == 0)
			{
			 errorMessage = errorMessage + " " +messagesData.mfglot+" \n" ;
			 errorCount = 1;
			}
			
			var transType = document.getElementById("transType"+rowNumber+"");
			if (transType.value == "IT")
			{
				 var transferReceiptId = document.getElementById("transferReceiptId"+rowNumber+"");
				 if (transferReceiptId.value.trim().length == 0)
				 {
				  errorMessage = errorMessage + " "+messagesData.transferreceiptid+" \n" ;
				  errorCount = 1;
				 }
			}
			
			var qaStatement = $v("qaStatement"+rowNumber+"");
	        if (qaStatement !=null && qaStatement != "" && qaStatement != "1" && qaStatement != "2")
			{
				  errorMessage = errorMessage + " "+messagesData.qastatement+"\n" ;
				  $("qaStatement"+rowNumber+"").value = "";
				  errorCount = 1;
			}
	  		
			var dateOfReceipt = $v("dateOfReceipt"+rowNumber+"");
			
			          
			var supplierShipDate = dateToIntString($v("supplierShipDate"+rowNumber+""));
			var dateOfReceipt = dateToIntString($v("dateOfReceipt"+rowNumber+""));
			var dateOfManufacture = dateToIntString($v("dom"+rowNumber+""));
			if ($v("supplierShipDate"+rowNumber+"").length > 0 && (supplierShipDate > dateOfReceipt || supplierShipDate < dateOfManufacture))
	  		{
	  			errorMessage = errorMessage + " " +messagesData.actsupshpdate+ "\n";
				errorCount = 1;
	  		}
			          
			var dateOfManufacture = dateToIntString($v("dom"+rowNumber+""));
			var dos = dateToIntString($v("dos"+rowNumber+""));
		          
			try
			{
			   var lotStatus = document.getElementById("lotStatus"+rowNumber+"");
			   if ( lotStatus.value  == "Incoming" || lotStatus.value.length == 0)
			   {
			       errorMessage = errorMessage + "  " +messagesData.lotstatus+" "+messagesData.cannotbe+" "+messagesData.incoming+" \n" ;
			       errorCount = 1;
			   }
			}
			catch (ex)
			{
			}
		}
		
		 var definedShelfLifeItem = document.getElementById("definedShelfLifeItem"+rowNumber+"");
		 // if defined_shelf_life_item is set to Y, check for the required date value
		 if (definedShelfLifeItem != null && definedShelfLifeItem.value == "Y")
		 {
			 var definedShelfLifeBasis = document.getElementById("definedShelfLifeBasis"+rowNumber+"");
			 
			 if (definedShelfLifeBasis != null) 
			 {
				 if(definedShelfLifeBasis.value == "M")
				 {
					 if ($v("dom"+rowNumber+"").length <= 0 )
				  	 {
			  			errorMessage = errorMessage + " " +messagesData.dom+ "\n";
						errorCount = 1;
						domError = true;
				  	 }
				 }
				 else if(definedShelfLifeBasis.value == "S")
				 {
					 if ($v("dos"+rowNumber+"").length <= 0 )
				  	 {
			  			errorMessage = errorMessage + " " +messagesData.dos+ "\n";
						errorCount = 1;
				  	 }
				 }
				 else if(definedShelfLifeBasis.value == "R")
				 {
					 if ($v("dateOfReceipt"+rowNumber+"").length <= 0 )
				  	 {
			  			errorMessage = errorMessage + " " +messagesData.dor+ "\n";
						errorCount = 1;
						dorError = true;
				  	 }
				 }
			 }
		 }
		 else
		 {
			 if (dateOfReceipt.length == 0) {            
	            errorMessage = errorMessage + " "+messagesData.dor+"\n" ;
	            errorCount = 1;
	            dateOfReceipt.value = document.getElementById("todayDate").value;
			 }
			 
			 if ($v("dom"+rowNumber+"").length > 0 && $v("dos"+rowNumber+"").length > 0 && dateOfManufacture > dos)
	  		 {
	  			errorMessage = errorMessage + " " +messagesData.dom+ " \n";
				errorCount = 1;
	  		 }
			          
			var expirationDateString = $v("expirationDateString"+rowNumber+"");      
		    if (expirationDateString.length == 0) {            
		            errorMessage = errorMessage + " "+messagesData.expiredate+" \n" ;
		            errorCount = 1;
		            expirationDateString.value = "";
		    }
		 }
	}
		
	if (qtyErrorCount >0)
	{
		    alert(qtyErrorMessage);
			ok.checked = false;
			return false;
	}
		   
		
	if (errorCount >0)
	{
		    alert(errorMessage);
			ok.checked = false;
			return false;
	}
		
	if (warnCount >0)
	{
		       if (confirm(warningMessage))
		       {
		
		       }
		       else
		       {
		         ok.checked = false;
		         return false;
		       }
	}
		
	itemId = $v("itemId"+rowNumber+"");	
	inventoryGroup = $v("inventoryGroup"+rowNumber+"");	 	     
	if (checReceipt == 'Y' && ok.checked && transType.value == "IT" && $("transferReceiptId"+rowNumber+"") != null && $v("transferReceiptId"+rowNumber+"") != $v("originalReceiptId"+rowNumber+""))
	{
//		showTransitWin('');
		callToServer("receiving.do?userAction=checkValidReceiptId&itemId="+itemId+"&transferReceiptId="+$v("transferReceiptId"+rowNumber+"")+"&originalReceiptId="+$v("originalReceiptId"+rowNumber+"")+"&rowNumber="+rowNumber);
	}  
	
	return true;
}

function checkReceiptId(rowNumber) {
	ok = $("ok"+rowNumber+"");	
	transType = $v("transType"+rowNumber+"");	
	itemId = $v("itemId"+rowNumber+"");	
	inventoryGroup = $v("inventoryGroup"+rowNumber+"");
	if (ok.checked && transType == "IT" && $("transferReceiptId"+rowNumber+"") != null && $v("transferReceiptId"+rowNumber+"") != $v("originalReceiptId"+rowNumber+""))
	{
//		showTransitWin("");
		callToServer("receiving.do?userAction=checkValidReceiptId&itemId="+itemId+"&transferReceiptId="+$v("transferReceiptId"+rowNumber+"")+"&originalReceiptId="+$v("originalReceiptId"+rowNumber+"")+"&rowNumber="+rowNumber);
	}
	else
		return true;
}

function alertIfIsValidReceiptId(isValidReceiptId,inventoryGroup,mfgLot,itemId,rowNumber) {
//try {	closeTransitWin(); } catch(ex) {}
	if(isValidReceiptId == "Y") {
	 	msg = messagesData.inventorywarning.replace(/[{]0[}]/g,inventoryGroup);
		alert(msg);
		$("mfgLot"+rowNumber+"").value = mfgLot; //Do we need to make it readonly here??
		return true;
	}
	else {
	 	msg = messagesData.invalidreceiptiderror.replace(/[{]1[}]/g,itemId);
	 	msg = msg.replace(/[{]0[}]/g,inventoryGroup);
		alert(msg);
		$("ok"+rowNumber+"").checked = false;
		$("transferReceiptId"+rowNumber+"").value = $v("originalReceiptId"+rowNumber+"");
		$("mfgLot"+rowNumber+"").value = mfgLot;
		return false;
	}
}
/*
function initializeDhxWins() {
	if (dhxFreezeWins == null) {
		dhxFreezeWins = new dhtmlXWindows();
		dhxFreezeWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}


function showTransitWin(messageType)
{
	var waitingMsg = "...";
	$("transitLabel").innerHTML = waitingMsg.replace(/[{]0[}]/g,messageType);

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.innerHTML = document.getElementById("transitDailogWinBody").innerHTML;
	transitDailogWin.style.display="";
alert("transitDailogWin"+transitDailogWin.innerHTML);
	initializeDhxWins();
	if (!dhxFreezeWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxFreezeWins.createWindow("transitDailogWin",0,0, 400, 200);alert("xxxxtransitWin"+transitWin);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);alert(1);
	}else{
		// just show
		transitWin.setModal(true);
		dhxFreezeWins.window("transitDailogWin").show();alert("zzzztransitWin"+transitWin);
	}
}

function closeTransitWin() {
	if (dhxFreezeWins != null) {
		if (dhxFreezeWins.window("transitDailogWin")) {
			dhxFreezeWins.window("transitDailogWin").setModal(false);
			dhxFreezeWins.window("transitDailogWin").hide();
		}
	}
}  */

function cancel()
{
    window.close();
}

function checkDropShip()
{

 var category = document.getElementById("category");
 var showdropshiponly = document.getElementById("showdropshiponly");

 if (category.value == "Non-Chemicals")
 {
  	showdropshiponly.style["display"] = "none";
  	setSearchWhat(searchWhatNonChemArray);
 }
 else
 {
	  showdropshiponly.style["display"] = "";
	  setSearchWhat(searchWhatChemArray);
 }
}
var onloadSearchType = true;
function setSearchWhat(searchWhatArray)
{
   var obj = $("searchWhat");
   for (var i = obj.length; i >= 0;i--)
     obj[i] = null;
     
	  for ( var j=0; j < searchWhatArray.length; j++) {
	    	setOption(j,searchWhatArray[j].text,searchWhatArray[j].id,"searchWhat");
	  }

	var category = document.getElementById("category");
	if($v("selectedSearchWhat") == '' || (category.value == "Non-Chemicals" && ($v("selectedSearchWhat") == 'transferRequestId' || $v("selectedSearchWhat") == 'customerRmaId')))
		obj.selectedIndex = 0;
	else
	 	$("searchWhat").value = $v("selectedSearchWhat");
	
	changeSearchTypeOptions(obj.value);
//  obj.selectedIndex = 0;
}

function getSelectedSearchWhat(selectedValue) {
	if($("searchWhat").selectedIndex < 5)
		$("selectedSearchWhat").value = $v("searchWhat");
	else
		$("selectedSearchWhat").value = '';
	
	changeSearchTypeOptions(selectedValue);
}

function changeSearchTypeOptions(selectedValue) 
{
	  var searchType = $('searchType');
	  for (var i = searchType.length; i > 0;i--)
		  searchType[i] = null;
	  
	  var actuallyAddedCount = 0;
	  for (var i=0; i < searchMyArr.length; i++) 
	  {
		    if((searchMyArr[i].value == 'contains' || searchMyArr[i].value == 'endsWith') &&
		    	(selectedValue == 'radianPo' || selectedValue == 'itemId' || selectedValue == 'transferRequestId' || selectedValue == 'customerRmaId'))
		    	continue;
		    if(searchMyArr[i].value == 'startsWith' && selectedValue == 'radianPo')
		    	continue;
		    setOption(actuallyAddedCount,searchMyArr[i].text,searchMyArr[i].value, "searchType")
		    if(onloadSearchType && searchMyArr[i].value == $v('searchTypeSelected'))
		    	searchType.selectedIndex = actuallyAddedCount;
		    actuallyAddedCount++;
	  }
	  onloadSearchType = false;
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

function submitReceiveAction(actionElementName)
{
 
 var actionElement = document.getElementById(""+actionElementName+"");
 actionElement.value = ""+actionElement+"";

 if (checkAllChemicalLines())
 {
/*   try
     {
       var sourceHubName = null;
       sourceHubName =  hubO.options[hubO.selectedIndex].text;

       var sourceHubNameObject = document.getElementById("sourceHubName");
       sourceHubNameObject.value = sourceHubName;
     }
     catch (ex)
     {
       //alert("Non-Pickable Status");
     } */
  SubmitOnlyOnce();
  document.genericForm.submit();
 }
}

function submitNonChemReceiveAction(actionElementName)
{
 var actionElement = document.getElementById(""+actionElementName+"");
 actionElement.value = ""+actionElement+"";

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
 SubmitOnlyOnce();
 document.genericForm.submit();
}

function showReceiptDocuments(receiptId,inventoryGroup,companyId,shipToLocationId)
{
 var loc = "receiptdocuments.do?receiptId="+receiptId+"&showDocuments=Yes&inventoryGroup="+inventoryGroup+
           "&companyId="+companyId+"&shipToLocationId="+shipToLocationId+"";
 
 if ($v("personnelCompanyId") == 'Radian') 
	  loc = "/tcmIS/hub/" + loc;
 
 try {
 	children[children.length] = openWinGeneric(loc,"showAllReceiptDocuments","450","300","no","80","80");
 } catch (ex){
 	openWinGeneric(loc,"showAllReceiptDocuments","450","300","no","80","80");
 }
}

function expiredDateChanged(rowid) {
	$("indefiniteDate").value = "01-"+month_abbrev_Locale_Java[pageLocale][0]+"-3000";//alert("indefiniteDate"+$v("indefiniteDate"));
//alert("expirationDateString"+$v("expirationDateString"+rowid)+"      messagesData:"+messagesData.indefinite);
	if($v("expirationDateString"+rowid) == messagesData.indefinite) {
		$("expirationDate"+rowid).value = $v("indefiniteDate"); }
	else {
		$("expirationDate"+rowid).value = $v("expirationDateString"+rowid); 
		} 
//alert("expirationDate"+$v("expirationDate"+rowid));
}

function closeAllchildren() 
{
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
//	if (document.getElementById("uAction").value != 'new') {
		try {
			for(var n=0;n<children.length;n++) {
				try {
				children[n].closeAllchildren(); 
				} catch(ex){}
			children[n].close();
			}
		} catch(ex){}
		children = new Array(); 
}
function showRoomOptions() 
{
 
 var selectedHub = $v('branchPlant');	
  var roomArray = new Array();
  roomArray = altRoomId[selectedHub];

  
  if(null!= $('room' ))
 {		  
  var obj = $('room');	  
   for (var i = obj.length; i > 0;i--)
   {	  
   obj[i] = null;
   }
 
 
  var roomNameArray = new Array();
  roomNameArray = altRoomName[selectedHub]; 
  try { 
	  
  if(roomArray.length == 0) 
  {
    setOption(0, messagesData.all, "", "room")
  }

 // if(roomArray.length > 1)
 // {	  
  for (var i=0; i < roomArray.length; i++) 
  {
    setOption(i,roomNameArray[i],roomArray[i], "room")
  }
  }
  //}
  
  catch (e) {
	  alert(e);
		// TODO: handle exception
	}
  
  
  if($('selectedRoom').value.trim().length > 0 )
  {
	  setSelect("room",$v('selectedRoom'));	  
  }
  
 }
}

function createnewroom() {	
	var loc = "addnewroom.do?branchPlant="+$v('hub')+"&sourceHubName="+escape($v('sourceHubName'))+"&userAction=showNewRoom";
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	openWinGeneric(loc,"addnewRoom","600","200","yes","80","80");	
	
}

function refreshAddBinsPage(refreshFlag, selectedRoom, sourceHub,sourceHubName )
{		
	if(refreshFlag)
	{		
	$('selectedRoom').value=selectedRoom;
	document.genericForm.target='';
	var action = document.getElementById("userAction");	
	action.value="addNewBin";
	$('branchPlant').value=sourceHub;
	$('sourceHubName').value=sourceHubName;	
	
	//var now = new Date();
    //var startSearchTime = document.getElementById("startSearchTime");
	//startSearchTime.value = now.getTime();
	document.genericForm.submit();
	}
}

function printGHSLabel() {
	var dataCount =  document.getElementById("selectedDataCount"+selectedRowId+"").value;
	var itemId = document.getElementById("itemId"+dataCount+"");
	var personnelId = document.getElementById("personnelId");
	var printerLocation = document.getElementById("printerLocation");

	var reportLoc = "/HaasReports/report/printghslabels.do"+
                    "?itemId="+itemId.value +
                    "&personnel_Id="+personnelId.value + 
                	"&printerLocation="+printerLocation.value;
	openWinGeneric(reportLoc,"printGHSLabels","300","200","yes","200","200");
}