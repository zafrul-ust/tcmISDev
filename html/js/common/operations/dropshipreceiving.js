var selectedrow=null;
var selectedRowId=null;

function init() { /*This is to initialize the YUI*/
 this.cfg = new YAHOO.util.Config(this);
 if (this.isSecure)
 {
  this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 }
}

function showRadianPo(poNumber) { 
  var dataCount =  document.getElementById("selectedDataCount"+selectedRowId+"").value;
  var poNumber =  document.getElementById("radianPo"+dataCount+"").value;
  loc = "/tcmIS/supply/purchaseorder.do?po=" + poNumber + "&Action=searchlike";
  try {
 	parent.children[parent.children.length] = openWinGeneric(loc,"showradianPo","800","600","yes","50","50","yes");
 } catch (ex){
 	openWinGeneric(loc,"showradianPo","800","600","yes","50","50","yes");
 }
  
}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function selectRow(rowId)
{
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
   var dataCount =  document.getElementById("selectedDataCount"+selectedRowId+"").value;
   var enableLinkToPo =  document.getElementById("enableLinkToPo"+dataCount+"").value;
  
   if (enableLinkToPo == 'Y')
     {toggleContextMenu('showPurchaseOrder');}
}  //end of selectRow

function showChemicalReceivedReceipts(receivedReceipts) {
 receivedReceipts = receivedReceipts.replace(/,/gi, "%2c");
 var loc = "/tcmIS/hub/showdropshipreceivedreceipts.do?receivedReceipts="+receivedReceipts+"";
 /*var sourceHub = document.getElementById("sourceHub");
 var inventoryGroup = document.getElementById("inventoryGroup");
 loc = loc + "&sourceHub="+sourceHub.value+"&inventoryGroup="+inventoryGroup.value+"";*/
 try {
 	parent.children[parent.children.length] = openWinGeneric(loc,"showdropshipreceivedreceipts11","800","500","yes","80","80");
 } catch (ex){
 	openWinGeneric(loc,"showdropshipreceivedreceipts11","800","500","yes","80","80");
 }
 
 myOnload();
}


function checkAllChemicalLines() {
  var totalLines = document.getElementById("totallines").value;
  var result = true;
  for ( var p = 0 ; p < totalLines ; p ++){
    try {
      var updateStatus = document.getElementById("updateStatus"+p+"").value;
    }catch (ex) {
      updateStatus = "";
    }

    if (updateStatus != 'readOnly') {
      try {
        var ok = document.getElementById("ok"+p+"");
        if (ok != null && typeof(ok) != 'undefined' && ok.checked) {
          var lineResult = checkChemicalReceivingInput(p);
          if (lineResult == false) {
            result = false;
          }
        }
      }catch (ex){
      }
    }
  }
  return result;
}  //end of method


function checkChemicalReceivingInput(rowNumber)
{
  var errorMessage = " "+messagesData.validvalues+" \n\n";
  var warningMessage = messagesData.alert+" \n\n";
  var errorCount = 0;
  var warnCount = 0;
  var qtyErrorMessage = "";
  var qtyErrorCount = 0;

  var ok = document.getElementById("ok"+rowNumber+"");
  if (ok.checked){
    var closePoAllowed = false;
    try {
      var closePoLine = document.getElementById("closePoLine"+rowNumber+"");
      if (closePoLine.checked){
        closePoAllowed = true;
      }
    }catch (ex){
      closePoAllowed = false;
    }

    try {
      var mvItem = document.getElementById("mvItem"+rowNumber+"");
      var rowSpan = document.getElementById("rowSpan"+rowNumber+"");
      var quantityReceived = document.getElementById("quantityReceived"+rowNumber+"");
      var nonintegerReceiving = document.getElementById("nonintegerReceiving"+rowNumber+"");

      var totalMvItemRecevied = 0;

      if (mvItem.value == "Y"){
        for ( var p = 0 ; p < rowSpan.value ; p ++) {
          var packagedQty = document.getElementById("packagedQty"+(rowNumber+p)+"");
          var packagedSize = document.getElementById("packagedSize"+(rowNumber+p)+"");
          if (packagedQty.value.length > 0 && ( !(isInteger(packagedQty.value)) || packagedQty.value*1 == 0 )){
            if (closePoAllowed && closePoLine.checked && packagedQty.value*1 == 0) {
            }else {
              errorMessage = errorMessage + messagesData.packagedqtyreceived+" \n";
	      errorCount = 1;
              packagedQty.value = "";
            }
          }

          totalMvItemRecevied = totalMvItemRecevied +(packagedQty.value*1)*(packagedSize.value*1);
          var purchasingUnitsPerItem = document.getElementById("purchasingUnitsPerItem"+(rowNumber)+"");
          var purchasingUnitOfMeasure = document.getElementById("purchasingUnitOfMeasure"+(rowNumber)+"");

          if (packagedQty.value.length > 0 && ( !(isInteger(packagedQty.value)) || packagedQty.value*1 == 0 )){
          if (Math.abs(((packagedSize.value*1-purchasingUnitsPerItem.value*1)/purchasingUnitsPerItem.value*1)*100) > 25) {
            warningMessage = warningMessage +" "+ messagesData.checkpackagedsize+" ("+purchasingUnitsPerItem.value+" "+purchasingUnitOfMeasure.value+"). \n";
            warnCount = 1;
          }
         }
        }

        if (Math.round(100*totalMvItemRecevied)/100 !==  Math.round(100*(quantityReceived.value.trim()*1))/100) {
          errorMessage = errorMessage + messagesData.qtyreceivednotmatch+" \n";
          errorCount = 1;
        }
      }else {
        if ( !(isInteger(quantityReceived.value)) || quantityReceived.value*1 == 0 ) {
          if (closePoAllowed && closePoLine.checked && quantityReceived.value*1 == 0) {
 	  }else if (!(nonintegerReceiving.value == "Y" && isFloat(quantityReceived.value))) {
            errorMessage = errorMessage + messagesData.qtyreceived+" \n";
	    errorCount = 1;
            quantityReceived.value = "";
          }
        }
      }
      var qtyOpen = document.getElementById("qtyOpen"+rowNumber+"");
      var mrQtyOpen = document.getElementById("mrQtyOpen"+rowNumber+"");
      var purchasingUnitsPerItem = document.getElementById("purchasingUnitsPerItem"+(rowNumber)+"");
      if (mvItem.value == "Y"){
          var totalPackagedQty = 0;
          for ( var p = 0 ; p < rowSpan.value ; p ++) {
          var packagedQty = document.getElementById("packagedQty"+(rowNumber+p)+"");
              totalPackagedQty =totalPackagedQty +packagedQty;
          }          
          if (Math.round(100*(mrQtyOpen.value.trim()*1))/100 < Math.round(100*(totalPackagedQty*1/purchasingUnitsPerItem.value*1))/100) {
            warningMessage = warningMessage + messagesData.qtyrecgreaterthanmralloc+" \n";
            warnCount = 1;
         }
      }
      else
      {
       if (Math.round(100*(mrQtyOpen.value.trim()*1))/100 < Math.round(100*(quantityReceived.value.trim()*1))/100) {
    	   if (Math.round(100*(mrQtyOpen.value.trim()*1))/100 < Math.round(100*((quantityReceived.value.trim()*1/purchasingUnitsPerItem.value*1)))/100){
    		   warningMessage = warningMessage + messagesData.qtyrecgreaterthanmralloc+" \n";
    	        warnCount = 1;	   
    	   }
       }
      }
      if ((Math.round(100*(mrQtyOpen.value.trim()*1))/100 !== Math.round(100*(qtyOpen.value.trim()*1))/100) 
    		  && ((Math.round(100*(mrQtyOpen.value.trim()*1))/100 < purchasingUnitsPerItem.value*1) || (Math.round(100*(mrQtyOpen.value.trim()*1))/100 % purchasingUnitsPerItem.value*1) > 0))  {
    	  warningMessage = warningMessage + messagesData.enterpoqtyforqtyreceived+" \n";
          warnCount = 1;
      }
      if (Math.round(100*(qtyOpen.value.trim()*1))/100 < Math.round(100*(quantityReceived.value.trim()*1))/100) {
        warningMessage = warningMessage + messagesData.qtyreceivedgreaterthanopen+" \n";
        warnCount = 1;
      }
      if (((quantityReceived.value.trim()*1-qtyOpen.value.trim()*1)/qtyOpen.value.trim()*1)*100 > 25) {
        qtyErrorMessage = qtyErrorMessage +" "+ messagesData.qtybeingreceived +" ("+quantityReceived.value.trim()+") "+ messagesData.is +Math.round(10000*(quantityReceived.value.trim()*1-qtyOpen.value.trim()*1)/qtyOpen.value.trim()*1)/100+"% "+messagesData.greaterthanqtyopen+" ("+qtyOpen.value.trim()+").\n"+messagesData.polinecannotreceived+"\n";
        qtyErrorCount = 1;
      }
    }catch (ex) {
    }

    if (closePoAllowed) {
    }else {
      /*var bin = document.getElementById("bin"+rowNumber+"");
      if (bin.value == "NONE")
      {
        errorMessage = errorMessage +   " BIN. \n" ;
        errorCount = 1;
      }*/

      var mfgLot = document.getElementById("mfgLot"+rowNumber+"");
      if (mfgLot.value.trim().length == 0) {
        errorMessage = errorMessage +" "+ messagesData.mfglot+". \n" ;
        errorCount = 1;
      }

      /*var transType = document.getElementById("transType"+rowNumber+"");
      if (transType.value == "IT")
      {
        var transferReceiptId = document.getElementById("transferReceiptId"+rowNumber+"");
        if (transferReceiptId.value.trim().length == 0)
        {
          errorMessage = errorMessage + " Transfer Receipt ID #. \n" ;
          errorCount = 1;
        }
      }*/
 /*
      var supplierShipDate = document.getElementById("supplierShipDate"+rowNumber+"");
      if (supplierShipDate.value.trim().length == 10) {
        if (!checkdate(supplierShipDate)) {
          errorMessage = errorMessage + messagesData.actualsuppliershipdate+ " \n" ;
          errorCount = 1;
          supplierShipDate.value = "";
        }
      }else if (supplierShipDate.value.trim().length > 0) {
        errorMessage = errorMessage + messagesData.actualsuppliershipdate+ " \n" ;
        errorCount = 1;
        supplierShipDate.value = "";
      }

      var dateOfReceipt = document.getElementById("dateOfReceipt"+rowNumber+"");
      if (dateOfReceipt.value.trim().length == 10) {
        if (!checkdate(dateOfReceipt)) {
          errorMessage = errorMessage + messagesData.dorformat+" \n" ;
          errorCount = 1;
          dateOfReceipt.value = "";
        }
      }else {
        errorMessage = errorMessage +messagesData.dorformat+" \n" ;
        errorCount = 1;
        dateOfReceipt.value = "";
      }
     var dom = document.getElementById("dom"+rowNumber+"");
      if (dom.value.trim().length == 10)
      {
        if (!checkdate(dom))
        {
          errorMessage = errorMessage +   " Date Manufactured in mm/dd/yyyy Format. \n" ;
          errorCount = 1;
          dom.value="";
        }
      }else if (dom.value.trim().length > 0) {
        errorMessage = errorMessage +   " Date Manufactured in mm/dd/yyyy Format. \n" ;
        errorCount = 1;
        dom.value = "";
      }*/
      /*var dos = document.getElementById("dos"+rowNumber+"");
      if (dos.value.trim().length == 10)
      {
        if (!checkdate(dos))
        {
          errorMessage = errorMessage +   " Manufacturer Date of Shipment in mm/dd/yyyy Format. \n" ;
          errorCount = 1;
          dos.value = "";
        }
      }
      else if (dos.value.trim().length > 0)
      {
        errorMessage = errorMessage +   " Manufacturer Date of Shipment in mm/dd/yyyy Format. \n" ;
        errorCount = 1;
        dos.value = "";
      }
*/
      var dateOfReceipt = document.getElementById("dateOfReceipt"+rowNumber+"");
          if (dateOfReceipt.value.trim().length == 0) {            
            errorMessage = errorMessage + " "+messagesData.dor+"\n" ;
            errorCount = 1;
            dateOfReceipt.value = document.getElementById("today").value;
          }
      
      var expirationDateString = document.getElementById("expirationDateString"+rowNumber+"");      
//      if (!( expirationDateString.value.trim() == messagesData.indefinite)) {
          if (expirationDateString.value.trim().length == 0) {            
            errorMessage = errorMessage + " "+messagesData.expiredate+"\n" ;
            errorCount = 1;
            expirationDateString.value = "";
          }
 //     }
 	
      /*
      try
      {
        var lotStatus = document.getElementById("lotStatus"+rowNumber+"");
        if ( lotStatus.value  == "Incoming" || lotStatus.value.length == 0)
        {
          errorMessage = errorMessage +   " Lot Status cannot be Incoming. \n" ;
          errorCount = 1;
        }
      }
      catch (ex)
      {
      }
      */
    }
  }  //end of if ok checked

  if (qtyErrorCount >0) {
    alert(qtyErrorMessage);
    ok.checked = false;
    return false;
  }
	
    var supplierShipDate = dateToIntString(document.getElementById("supplierShipDate"+rowNumber+"").value);
    var dateOfReceipt = dateToIntString(document.getElementById("dateOfReceipt"+rowNumber+"").value);
	if (document.getElementById("supplierShipDate"+rowNumber+"").value.trim().length > 0 && supplierShipDate>dateOfReceipt)
  	{
  		errorMessage = errorMessage + " " +messagesData.actsupshpdate+ "\n";
		errorCount = 1;
  	}  
  			
  if (errorCount >0) {
    alert(errorMessage);
    ok.checked = false;
    return false;
  }

  if (warnCount >0) {
    if (confirm(warningMessage)) {
    }else {
      ok.checked = false;
      return false;
    }
  }
  return true;
} //end of method


function companyChanged() {
  var companyIdO = document.getElementById("companyId");
  var facilityIdO = document.getElementById("facilityId");
  var selectedCompany = companyIdO.value;

  for (var i = facilityIdO.length; i > 0;i--) {
    facilityIdO[i] = null;
  }

  showFacility(selectedCompany);
  facilityIdO.selectedIndex = 0;
  facilityChanged();
}

function showFacility(selectedCompany) {
  var facilityIdArray = new Array();
  facilityIdArray = altFacilityId[selectedCompany];
  var facilityNameArray = new Array();
  facilityNameArray = altFacilityName[selectedCompany];

  for (var i=0; i < facilityIdArray.length; i++) {
    setOption(i,facilityNameArray[i],facilityIdArray[i], "facilityId")
  }
}

function facilityChanged() {
  var facilityO = document.getElementById("facilityId");
  var dockIdO = document.getElementById("dockId");
  var selectedFacility = facilityO.value;

  for (var i = dockIdO.length; i > 0;i--) {
    dockIdO[i] = null;
  }

  showDockLinks(selectedFacility);
  dockIdO.selectedIndex = 0;
}

function showDockLinks(selectedFacility)
{
  var dockIdArray = new Array();
  dockIdArray = altDock[selectedFacility];
				
  var dockDescArray = new Array();
  dockDescArray = altDockDesc[selectedFacility];

  for (var i=0; i < dockIdArray.length; i++) {
    setDock(i,dockDescArray[i],dockIdArray[i])
  }
}

function setDock(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var dockIdO = document.getElementById("dockId");
  dockIdO[href] = optionName;
}

function submitSearchForm()
{
 document.genericForm.target='resultFrame';
 var action = document.getElementById("action");
 action.value = 'search';
 //set start search time
 var now = new Date();
 var startSearchTime = parent.window.frames["searchFrame"].document.getElementById("startSearchTime");
 startSearchTime.value = now.getTime();
 var flag = validateForm();
  if(flag) {
    parent.showPleaseWait();
    return true;
  }
  else
  {
    return false;
  }
}

function validateForm() {
   var result = true;
   return result;
}

function myOnload()
{
   /*Dont show any update links if the user does not have permissions.
   Remove this section if you don't have any links on the main page*/
   if (!showUpdateLinks)
   {
     parent.document.getElementById("updateResultLink").style["display"] = "none";
   }
   else
   {
     parent.document.getElementById("updateResultLink").style["display"] = "";
   }
	//it's import to put this before setResultFrameSize(), otherwise it will not resize correctly
	displaySearchDuration();
	setResultFrameSize();
}

function duplLine(rowNumber)
{
  var duplicateLine = document.getElementById("duplicateLine");
  duplicateLine.value = rowNumber;
  //set start search time
  var now = new Date();
  var startSearchTime = parent.window.frames["searchFrame"].document.getElementById("startSearchTime");
  startSearchTime.value = now.getTime();

    var duplicatePkgLine = document.getElementById("duplicatePkgLine");
    duplicatePkgLine.value = "";

    var duplicateKitLine = document.getElementById("duplicateKitLine");
    duplicateKitLine.value = "";

  var action = document.getElementById("action");
  action.value = 'receive';
  parent.showPleaseWait();
  return true;
}

function duplpkg(rowNumber)
{
  var duplicatePkgLine = document.getElementById("duplicatePkgLine"+rowNumber+"");
  duplicatePkgLine.value = rowNumber;
  var duplicatePkgLine = document.getElementById("duplicatePkgLine");
  duplicatePkgLine.value = rowNumber;
  //set start search time
  var now = new Date();
  var startSearchTime = parent.window.frames["searchFrame"].document.getElementById("startSearchTime");
  startSearchTime.value = now.getTime();

    var duplicateLine = document.getElementById("duplicateLine");
    duplicateLine.value = "";

    var duplicateKitLine = document.getElementById("duplicateKitLine");
    duplicateKitLine.value = "";

  var action = document.getElementById("action");
  action.value = 'receive';
  parent.showPleaseWait();
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

    var duplicateLine = document.getElementById("duplicateLine");
    duplicateLine.value = "";

    var duplicatePkgLine = document.getElementById("duplicatePkgLine");
    duplicatePkgLine.value = "";

  //set start search time
  var now = new Date();
  var startSearchTime = parent.window.frames["searchFrame"].document.getElementById("startSearchTime");
  startSearchTime.value = now.getTime();
  var action = document.getElementById("action");
  action.value = 'receive';
  parent.showPleaseWait();

 return true;
}
function generateExcel() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'createExcel';
  
  try {
 	parent.children[parent.children.length] = openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_ReceivingReportGenerateExcel','650','600','yes');
 } catch (ex){
 	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_ReceivingReportGenerateExcel','650','600','yes');
 } 
 
    document.genericForm.target='_ReceivingReportGenerateExcel';
	var a = window.setTimeout("document.genericForm.submit();",500);
  }
  return false;
}

function expiredDateChanged(rowid) {
	$("indefiniteDate").value = "01-"+month_abbrev_Locale_Java[pageLocale][0]+"-3000";

	if($v("expirationDateString"+rowid) == messagesData.indefinite) {
		$("expirationDate"+rowid).value = $v("indefiniteDate"); }
	else {
		$("expirationDate"+rowid).value = $v("expirationDateString"+rowid); 
		} 
//alert("expireDate"+$v("expireDate"+rowid));
}

function changeSearchTypeOptions(selectedValue)
{
	  var searchType = $('searchType');
	  for (var i = searchType.length; i > 0;i--)
		  searchType[i] = null;

	  var actuallyAddedCount = 0;
	  for (var i=0; i < searchMyArr.length; i++) 
	  {
		    if(selectedValue != 'itemDescription' && (searchMyArr[i].value == 'LIKE' || searchMyArr[i].value == 'ENDSWITH' ))
		    	continue;
		    setOption(actuallyAddedCount++,searchMyArr[i].text,searchMyArr[i].value, "searchType")
	  }
}
