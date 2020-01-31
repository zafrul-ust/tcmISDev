var sgrid = null;
var carrierGrid = null;
var dontChangElemCss = true;

var resizeGridWithWindow = true;
var resizeResultscount=1;

var  appResizeFramecount = 0;

var children = new Array(); 

var myWidth = 0;
var myHeight = 0;
var lastWindowWidth=0; /*This is to keep track of what the size of the window was before re-size*/
var resultGridHeight =0;
var cSaveRowClass = null;
var sSaveRowClass = null;

var multiplePermissions = true;

// Build up the array for the columns which use different permissions
var permissionColumns = new Array();
permissionColumns = {
        "facilityPriceGroupId" : true
};



function billChanged()
{

}



function cDoOnBeforeSelect(rowId,oldRowId) {
	haasGrid = cgrid;	
	if (oldRowId != 0) {
		setRowClass(oldRowId, cSaveRowClass);		
	}
	cSaveRowClass = getRowClass(rowId);
	if (cSaveRowClass.search(/haas/) == -1 && cSaveRowClass.search(/Selected/) == -1)
		overrideDefaultSelect(rowId,cSaveRowClass);
	return true;	
}

function sDoOnBeforeSelect(rowId,oldRowId) {
	haasGrid = sgrid;
	if (oldRowId != 0) {
		setRowClass(oldRowId, sSaveRowClass);		
	}
	sSaveRowClass = getRowClass(rowId);
	if (sSaveRowClass.search(/haas/) == -1 && sSaveRowClass.search(/Selected/) == -1)
		overrideDefaultSelect(rowId,sSaveRowClass);
	return true;	
}



function submitSearchForm() {
  /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = checkTransactionsInput();
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();

  if (isValidSearchForm) {
    if (document.getElementById('txnOnDate').value != '') {
      document.getElementById('daysOld').value = '';
    }
    document.genericForm.target = 'resultFrame';
    showPleaseWait();
    return true;
  }
  else {
    return false;
  }
}

function validateSearchFormXSL() {
  if (checkTransactionsInput()) {
    document.getElementById("uAction").value = "createExcel";
    if (document.getElementById('txnOnDate').value != '') {
      document.getElementById('daysOld').value = '';
    }
    document.genericForm.target = 'transactions_excel_report_file';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', 'transactions_excel_report_file', '650', '600', 'yes');
    setTimeout("document.genericForm.submit()", 300);
  }
}

// ***************************************************************************
// checkTransactionsInput()
//
// Checks the Transactions forms for valid input
// fields verified: txnOnDate, receiptId, mrNumber, radianPo, daysOld, itemId
//
function checkTransactionsInput() {
  var errorMessage = messagesData.validValues + "\n\n";
  var warningMessage = messagesData.alert + "\n\n";
  var errorCount = 0;
  var warnCount = 0;

  try {
    var receiptId = document.getElementById("receiptId");

    if (receiptId.value.trim().length != 0 && (!(isSignedInteger(receiptId.value.trim())) || receiptId.value * 1 == 0)) {
      errorMessage += messagesData.receiptId + "\n";
      errorCount = 1;
      receiptId.value = "";
    }

    var mrNumber = document.getElementById("mrNumber");
    if (mrNumber.value.trim().length != 0 && (!(isInteger(mrNumber.value.trim())) || mrNumber.value * 1 == 0)) {
      errorMessage += messagesData.mrNumber + "\n";
      errorCount = 1;
      mrNumber.value = "";
    }

    var radianPo = document.getElementById("radianPo");
    if (radianPo.value.trim().length != 0 && (!(isInteger(radianPo.value.trim())) || radianPo.value * 1 == 0)) {
      errorMessage += messagesData.po + "\n";
      errorCount = 1;
      radianPo.value = "";
    }

    var daysOld = document.getElementById("daysOld");
    if (daysOld.value.trim().length != 0 && (!(isInteger(daysOld.value.trim())) || daysOld.value * 1 == 0)) {
      errorMessage += messagesData.withinXDays + "\n";
      errorCount = 1;
      daysOld.value = "";
    }

    var itemId = document.getElementById("itemId");
    if (itemId.value.trim().length != 0 && (!(isInteger(itemId.value.trim())) || itemId.value * 1 == 0)) {
      errorMessage += messagesData.itemId + "\n";
      errorCount = 1;
      itemId.value = "";
    }
  } catch(ex) {
    errorCount++;
  }
  var trackingNumber = document.getElementById('trackingNumber');
  if (trackingNumber.value.trim().length != 0) {
    document.getElementById('transType').selectedIndex = 3; // remember to change this number if the sequence is changed.
  }

  if (errorCount > 0) {
    alert(errorMessage);
    return false;
  }
  // set hub name
  var selIndex = document.getElementById('branchPlant').options.selectedIndex;
  document.getElementById('hubName').value = document.getElementById('branchPlant').options[selIndex].text;
  return true;
}

function hubChanged() {
  var hubO = document.getElementById("branchPlant");
  var inventoryGroupO = document.getElementById("inventoryGroup");
  var selhub = hubO.value;
  for (var i = inventoryGroupO.length; i > 0; i--) {
    inventoryGroupO[i] = null;
  }

  if (selhub.length > 0) {
    showInventoryGroupOptions(selhub);
  } else {
    setOption(0, messagesData.all, "", "inventoryGroup");
  }
  inventoryGroupO.selectedIndex = 0;
}

function showInventoryGroupOptions(selectedhub) {
  var invgrpid = new Array();
  invgrpid = altinvid[selectedhub];

  var invgrpName = new Array();
  invgrpName = altinvName[selectedhub];

  if (invgrpid.length == 0) {
    setOption(0, messagesData.all, "", "inventoryGroup");
  }

  for (var i = 0; i < invgrpid.length; i++) {
    setOption(i, invgrpName[i], invgrpid[i], "inventoryGroup")
  }
}

function getSalesRep() {
  loc = "searchpersonnelmain.do?displayElementId=fieldSalesRepName&valueElementId=fieldSalesRepId&fixedCompanyId=Y";
  children[children.length] = openWinGeneric(loc, "changepersonnel", "600", "450", "yes", "50", "50", "20", "20", "no");
}

function initializeCustomerCarrier() {
  $('customerCarrierBean').style.height = "200px";
  //initGridWithConfigForPopUp(carrierGridConfig);
  carrierGrid = new dhtmlXGridObject('customerCarrierBean');

  initGridWithConfigForPopUp(carrierGrid, carrierConfig, false, true);

  if (typeof(carrierData) != 'undefined') {
    carrierGrid.parse(carrierData, "json");

  }

  carrierGrid.attachEvent("onRightClick", selectRightclick);

}

function selectRightclick(rowId, cellInd) {
  carrierGrid.selectRowById(rowId, null, false, false);
  if(showUpdateLinks)
  {	  
  toggleContextMenu("newCarrier");
  }
}

function carriermenu(e) {
  rightClick = false;
  var ee = e || window.event;
  if (ee != null) {
    if (ee.button != null && ee.button == 2) rightClick = true;
    else if (ee.which == 3) rightClick = true;
  }
  if (!rightClick)
  {	  return;
  }
  if(showUpdateLinks)
  {	 
  toggleContextMenu('newCarrier');
  }
}

function updateCustomer() {
	
 var flagChange = checkChanges();
 var flagValidate = validateForm();
 if(flagChange && flagValidate)
 {		
  $('uAction').value = 'update';
  showTransitWin();
  

  
  if (cgrid != null)
    cgrid.parentFormOnSubmit();
  
  if (sgrid != null)
    sgrid.parentFormOnSubmit();
   
  if (carrierGrid != null)
    carrierGrid.parentFormOnSubmit();

    document.genericForm.target='';
    document.genericForm.submit();
 }
}

function checkChanges()
{
	var changesMade = false;
	var customerName = $v("customerName");
	var addressLine1 = $v("addressLine1");
	var addressLine2 = $v("addressLine2");
	var addressLine3 = $v("addressLine3");
	var addressLine4 = $v("addressLine4");
	var addressLine5 = $v("addressLine5");
	var countryAbbrev = $v("countryAbbrev");
	var stateAbbrev = $v("stateAbbrev");
	var city = $v("city");
	var zip = $v("zip");
	var taxRegistrationType = $v("taxRegistrationType");
	var taxRegistrationType2 = $v("taxRegistrationType2");
	var taxRegistrationType3 = $v("taxRegistrationType3");
	var taxRegistrationType4 = $v("taxRegistrationType4"); 
	var taxRegistrationNumber = $v("taxRegistrationNumber");
	var taxRegistrationNumber2 = $v("taxRegistrationNumber2");
	var taxRegistrationNumber3 = $v("taxRegistrationNumber3");
	var taxRegistrationNumber4 = $v("taxRegistrationNumber4");
	var creditLimit = $v("creditLimit");
	var overdueLimit = $v("overdueLimit");
	var creditStatus = $v("creditStatus");
	var paymentTerms = $v("paymentTerms");	
	var autoEmailInvoice = $v("autoEmailInvoice");
	var autoEmailAddress = $v("autoEmailAddress");
	var autoEmailBatchSize = $v("autoEmailBatchSize");

	if (customerName != null && customerName.toLowerCase() != billingMap['customerName'].toLowerCase())
		{
			changesMade = true;
			document.getElementById('billingChange').value = true;
		}
	else if (addressLine1 != null && addressLine1.toLowerCase() != billingMap['addressLine1'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('billingChange').value = true;
	}
	else if (addressLine2 != null && addressLine2.toLowerCase() != billingMap['addressLine2'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('billingChange').value = true;
	}
	else if (addressLine3 != null && addressLine3.toLowerCase() != billingMap['addressLine3'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('billingChange').value = true;
	}
	else if (addressLine4 != null && addressLine4.toLowerCase() != billingMap['addressLine4'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('billingChange').value = true;
	}
	else if (addressLine5 != null && addressLine5.toLowerCase() != billingMap['addressLine5'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('billingChange').value = true;
	}
	//($v(invoiceConsolidation} != null && !=
	else if (countryAbbrev != null && countryAbbrev.toLowerCase() != billingMap['countryAbbrev'].toLowerCase())
	{
		changesMade = true;	
		document.getElementById('billingChange').value = true;
	}
	else if (stateAbbrev != null && stateAbbrev.toLowerCase() != billingMap['stateAbbrev'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('billingChange').value = true;
	}
	else if (city != null && city.toLowerCase() != billingMap['city'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('billingChange').value = true;
	}
	else if (zip != null && zip.toLowerCase() != billingMap['zip'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('billingChange').value = true;
	}
	else if (creditLimit != null && creditLimit.toLowerCase() != billingMap['creditLimit'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('billingChange').value = true;
	}
	else if (overdueLimit != null && overdueLimit.toLowerCase() != billingMap['overdueLimit'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('billingChange').value = true;
	}
	else if (creditStatus != null && creditStatus.toLowerCase() != billingMap['creditStatus'].toLowerCase())
	{
		changesMade = true;	
		document.getElementById('billingChange').value = true;
	}
	else if (paymentTerms != null && paymentTerms.toLowerCase() != billingMap['paymentTerms'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('billingChange').value = true;
	}
	else if ($v("abcClassification") != null && $v("abcClassification").toLowerCase() != billingMap['abcClassification'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('billingChange').value = true;
	}
	else if (taxRegistrationNumber != null && taxRegistrationNumber.toLowerCase() !=  billingMap['taxRegistrationNumber'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('billingChange').value = true;
	}
	else if (taxRegistrationNumber2 != null && taxRegistrationNumber2.toLowerCase() !=  billingMap['taxRegistrationNumber2'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('billingChange').value = true;
	}
	else if (taxRegistrationNumber3 != null && taxRegistrationNumber3.toLowerCase() != billingMap['taxRegistrationNumber3'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('billingChange').value = true;
	}
	else if (taxRegistrationNumber4 != null && taxRegistrationNumber4.toLowerCase() != billingMap['taxRegistrationNumber4'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('billingChange').value = true;
	}
	else if ((autoEmailInvoice.checked && billingMap['autoEmailInvoice'].toLowerCase() == '') || (!autoEmailInvoice.checked && billingMap['autoEmailInvoice'].toLowerCase() == 'y'))
	{
		changesMade = true;
		document.getElementById('billingChange').value  = true;
	}
	else if (autoEmailAddress != null && autoEmailAddress.toLowerCase() != billingMap['autoEmailAddress'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('billingChange').value = true;
	}
	else if (autoEmailBatchSize != null && autoEmailBatchSize != billingMap['autoEmailBatchSize'])
	{
		changesMade = true;
		document.getElementById('billingChange').value = true;
	}
		
	if ($v("priceGroupId").toLowerCase() != otherMap['priceGroupId'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('otherChange').value  = true;
	}
	else if ($v("sapIndustryKey").toLowerCase() != otherMap['sapIndustryKey'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('otherChange').value  = true;
	}
	else if ($v("industry").toLowerCase() != otherMap['industryKey'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('otherChange').value  = true;
	}
	else if ($v("shipComplete").toLowerCase() != otherMap['shipComplete'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('otherChange').value  = true;
	}
	else if ($v("fieldSalesRepName").toLowerCase() != otherMap['fieldSalesRepName'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('otherChange').value  = true;
	}
	else if ($v("notes").toLowerCase() != otherMap['notes'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('otherChange').value  = true;
	}
	else if ($v("taxNotes").toLowerCase() != otherMap['taxNotes'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('otherChange').value  = true;
	}
	else if (($("chargeFreight").checked && otherMap['chargeFreight'].toLowerCase() == '') || (!$("chargeFreight").checked && otherMap['chargeFreight'].toLowerCase() == 'y'))
	{
		changesMade = true;
		document.getElementById('otherChange').value  = true;
	}
	else if ($v("shelfLifeRequired").toLowerCase()!= otherMap['shelfLifeRequired'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('otherChange').value  = true;
	}
	else if (($("emailOrderAck1").checked && otherMap['emailOrderAck'].toLowerCase() == 'n') || ($("emailOrderAck2").checked && otherMap['emailOrderAck'].toLowerCase() == 'y'))
	{
		changesMade = true;
		document.getElementById('otherChange').value  = true;
	}
	else if ($v("exitLabelFormat").toLowerCase() != otherMap['exitLabelFormat'].toLowerCase())
	{
		changesMade = true;
		document.getElementById('otherChange').value  = true;
	}
	else if (($("shipMfgCoc").checked && otherMap['shipMfgCoc'].toLowerCase() == '') || (!$("shipMfgCoc").checked && otherMap['shipMfgCoc'].toLowerCase() == 'y'))
	{
		changesMade = true;
		document.getElementById('otherChange').value  = true;
	}
	else if (($("shipMsds").checked && otherMap['shipMsds'].toLowerCase() == '') || (!$("shipMsds").checked && otherMap['shipMsds'].toLowerCase() == 'y'))
	{
		changesMade = true;
		document.getElementById('otherChange').value  = true;
	}
	var mapSize = parseInt(contactInfoMap['count']);
	for(var i = 0; i < mapSize; i++)
		{
		try{
		if(contactInfoMap['active' + i] != cgrid.cells(cgrid.getRowId(i),cgrid.getColIndexById("status")).getValue())
			{
				changesMade = true;
				cgrid.cells(cgrid.getRowId(i),cgrid.getColIndexById("contactInfoChange")).setValue(true);
			}
		else if(contactInfoMap['dContact' + i] != cgrid.cells(cgrid.getRowId(i),cgrid.getColIndexById("defaultContact")).getValue())
			{
				changesMade = true;
				cgrid.cells(cgrid.getRowId(i),cgrid.getColIndexById("contactInfoChange")).setValue(true);
			}
		}catch(e){alert("contact"+ out); break;}
		}
	
	mapSize = parseInt(shipMap['count']);
	for(var i = 0; i < mapSize; i++)
		{	try{			
			if(shipMap['shiptoPriceGroupName' + i] != sgrid.cells(sgrid.getRowId(i),sgrid.getColIndexById("facilityPriceGroupId")).getValue())
			{
				changesMade = true;
				sgrid.cells(sgrid.getRowId(i),sgrid.getColIndexById("shipInfoChange")).setValue(true);
			}
			else if(shipMap['salesAgentName' + i] != sgrid.cells(sgrid.getRowId(i),sgrid.getColIndexById("salesAgentName")).getValue())
			{
				changesMade = true;
				sgrid.cells(sgrid.getRowId(i),sgrid.getColIndexById("shipInfoChange")).setValue(true);
			}
			else if(shipMap['fieldSalesRepName' + i] != sgrid.cells(sgrid.getRowId(i),sgrid.getColIndexById("fieldSalesRepName")).getValue())
			{
				changesMade = true;
				sgrid.cells(sgrid.getRowId(i),sgrid.getColIndexById("shipInfoChange")).setValue(true);
			}
			else if(shipMap['shiptoStatus' + i] != sgrid.cells(sgrid.getRowId(i),sgrid.getColIndexById("shiptoStatus")).getValue())
			{
				changesMade = true;
				sgrid.cells(sgrid.getRowId(i),sgrid.getColIndexById("shipInfoChange")).setValue(true);
			}
			else if(shipMap['msdsLocaleOverride' + i] != sgrid.cells(sgrid.getRowId(i),sgrid.getColIndexById("msdsLocaleOverride")).getValue())
			{
				changesMade = true;
				sgrid.cells(sgrid.getRowId(i),sgrid.getColIndexById("shipInfoChange")).setValue(true);
			}
			else if(shipMap['shiptoNotes' + i] != sgrid.cells(sgrid.getRowId(i),sgrid.getColIndexById("shiptoNotes")).getValue())
			{
				changesMade = true;
				sgrid.cells(sgrid.getRowId(i),sgrid.getColIndexById("shipInfoChange")).setValue(true);
			}
		}catch(e){alert("ship"+i);break;}
		}

	if(!changesMade) {
		alert(messagesData.nochange);
		return false;
	 }
  return true; 
}

// change this to use global config later.
function initializeShipto() {
  $('shiptoBean').style.height = "200px";
  sgrid = new dhtmlXGridObject('shiptoBean');

  initGridWithConfigForPopUp(sgrid, shipToConfig, -1, true);

  if (typeof(shipToData) != 'undefined') {
    sgrid.parse(shipToData, "json");
  }
  
  sgrid.attachEvent("onBeforeSelect",sDoOnBeforeSelect);
  sgrid.attachEvent("onRowSelect", sSelectRow);
  sgrid.attachEvent("onRightClick", shipToRightclick);
  sgrid.enableEditEvents(true,false,false);
}

var shipSelectedRowId = null;
function sSelectRow( rowId,oldRowId) {
	haasGrid = sgrid;
	shipSelectedRowId = rowId;
  // to show menu directly
/*  rightClick = false;
  rowId0 = arguments[0];
  colId0 = arguments[1];
  ee = arguments[2];
  //
  if (ee != null) {
    if (ee.button != null && ee.button == 2) rightClick = true;
    else if (ee.which == 3) rightClick = true;
  }
  sgrid.selectRowById(rowId0, null, false, false);  */
  
  if (sSaveRowClass.search(/haas/) == -1 && sSaveRowClass.search(/Selected/) == -1)
		  setRowClass(rowId,''+sSaveRowClass+'Selected');  
/*
  if (!rightClick)
  {	  return;
  }  */
  if(showUpdateLinks)
  {	 
  toggleContextMenu('newShipToMenu');
  }
}

function shipToRightclick(rowId, cellInd) {
  haasGrid = sgrid;
  sgrid.selectRowById(rowId, null, false, false);
  sSelectRow(rowId, cellInd);
  if(showUpdateLinks)
  {	 
  toggleContextMenu("newShipToMenu");
  }
}

function newContactInfo(shipinfo) {
  ind = cgrid.getRowsNum();
  var rowid = ind + 1;
  var thisrow = cgrid.addRow(rowid, "", ind);
  var count = 0;
  cgrid.cells(rowid, count++).setValue(shipinfo.contactName);
  cgrid.cells(rowid, count++).setValue(shipinfo.contactType);
  cgrid.cells(rowid, count++).setValue(shipinfo.phone);
  cgrid.cells(rowid, count++).setValue(shipinfo.mobile);
  cgrid.cells(rowid, count++).setValue(shipinfo.fax);
  cgrid.cells(rowid, count++).setValue(shipinfo.email);
  cgrid.cells(rowid, count++).setValue("");

}

function contactmenu(e) {
  rightClick = false;
  var ee = e || window.event;
  if (ee != null) {
    if (ee.button != null && ee.button == 2) rightClick = true;
    else if (ee.which == 3) rightClick = true;
  }
  if (!rightClick)
  {
	  return;
  }
  if(showUpdateLinks)
  {	 
  toggleContextMenu('newContact');
  }
}


function cSelectRow( rowId,oldRowId) {
	haasGrid = cgrid;
  // to show menu directly
	 if (cSaveRowClass.search(/haas/) == -1 && cSaveRowClass.search(/Selected/) == -1)
		  setRowClass(rowId,''+cSaveRowClass+'Selected');  
}


function cSelectRightclick(rowId,cellInd){
	haasGrid = cgrid;
	cgrid.selectRowById(rowId,null,false,false);	
	cSelectRow(rowId,cellInd);

	 if(showUpdateLinks)
	  {	 
	  toggleContextMenu('newContact');
	  }
}


function shiptomenu(e) {
  rightClick = false;
  var ee = e || window.event;
  if (ee != null) {
    if (ee.button != null && ee.button == 2) rightClick = true;
    else if (ee.which == 3) rightClick = true;
  }
  if (!rightClick)
  {
	  return;
  }	  
  if(showUpdateLinks)
  {	 
  toggleContextMenu('newShipToMenu');
  }
}

function initializeContact() {
  $('contactBean').style.height = "200px";
 // initGridWithGlobal(gridConfig);
  
  
  cgrid = new dhtmlXGridObject('contactBean');

	initGridWithConfig(cgrid,cconfig,-1,true);
	if( typeof( cdata ) != 'undefined' ) {
		cgrid.parse(cdata,"json");

	}

	cgrid.attachEvent("onBeforeSelect",cDoOnBeforeSelect);
	cgrid.attachEvent("onRowSelect",cSelectRow);
	cgrid.attachEvent("onRightClick",cSelectRightclick);

}

function loadGrids() {
	resizeResultscount = 0;
  initializeContact();
  initializeShipto();
  initializeCustomerCarrier();
  $('contactFooter').innerHTML = "&nbsp;&nbsp;"+messagesData.recordFound+": "+$('contactTotalLines').value;
  $('sfooter').innerHTML = "&nbsp;&nbsp;"+messagesData.recordFound+": "+$('shipToTotalLines').value;
  $('carrierFooter').innerHTML = "&nbsp;&nbsp;"+messagesData.recordFound+": "+$('carrierToTotalLines').value;
  
	if (showErrorMessage)
	 {
	  setTimeout('showNewCustErrorMessages()',50); /*Showing error messages if any*/
	 }
	
	 try{		 
			
		 if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
		 {
		  document.getElementById("updateResultLink").style["display"] = "none";
		 }
		 else
		 {
		  document.getElementById("updateResultLink").style["display"] = "";
		  
		 }
		 }
		 catch(ex)
		 {}		 
		
}

function openNewCarrierWin() {
  var custId = document.getElementById("customerId").value;
  var loc = "/tcmIS/distribution/newcarrieraccount.do?action=newcarrieraccount&fromCustomerDetail=Yes&customerId=" + custId;
  children[children.length] = openWinGeneric(loc, "newcarrieraccount" + "", "530", "200", "yes", "50", "50");
}

// menu stuff here.
function newShipTo() {

  var loc = "/tcmIS/distribution/newshiptoaddress.do?fromCustomerDetail=Yes&customerIdRequestType="+ "&customerId=" + $v('customerId')+ "&billToCompanyId=" + $v('billToCompanyId')+ "&priceList=" + $v('priceGroupId')+ "&fieldSalesRepId=" + $v('fieldSalesRepId')+ "&fieldSalesRepName=" + encodeURIComponent($v('fieldSalesRepName'));
  children[children.length] = openWinGeneric(loc, "newShipToAddress" + "", "900", "530", "yes", "50", "50");

}

function newContact() {

  var loc = "/tcmIS/distribution/newcustomercontact.do?fromCustomerDetail=Yes&customerIdRequestType=" + encodeURIComponent('Modify') + "&customerId=" + $v('customerId') + "&billToCompanyId=" + $v('billToCompanyId');
  children[children.length] = openWinGeneric(loc, "newContact", "700", "250", "yes", "50", "50");

}

function editContact() {

  var loc = "/tcmIS/distribution/newcustomercontact.do?uAction=showRecord&modifyRecord=Yes&fromCustomerDetail=Yes&customerIdRequestType=" + encodeURIComponent('Modify') + "&customerId=" + $v('customerId')+ "&billToCompanyId=" + $v('billToCompanyId') + "&contactPersonnelId=" + 
  gridCellValue(cgrid, cgrid.getSelectedRowId(), "contactPersonnelId");
  children[children.length] = openWinGeneric(loc, "editContact", "800", "215", "yes", "50", "50");

}

function editAddress() {

	 var shipToLocationId = gridCellValue(sgrid,shipSelectedRowId,"shipToLocationId");
	  var loc = "/tcmIS/distribution/customerupdateinputaddress.do?ShipToOrBillTo=S&tabName="+$v('billToCompanyId')+"-"+shipSelectedRowId+"&uAction=search&billToCompanyId="+$v('billToCompanyId') + "&locationId="+ shipToLocationId;

	  //var loc = "/tcmIS/distribution/newcustomercontact.do?uAction=showRecord&modifyRecord=Yes&fromCustomerDetail=Yes&customerIdRequestType=" + encodeURIComponent('Modify') + "&customerId=" + $v('customerId')+ "&billToCompanyId=" + $v('billToCompanyId') + "&contactPersonnelId=" + gridCellValue(cgrid, cgrid.getSelectedRowId(), "contactPersonnelId");
	  children[children.length] = openWinGeneric(loc, "editContact", "800", "215", "yes", "50", "50");
	}

function fillAddress(a)
{
	sgrid.cells(shipSelectedRowId,3).setValue(a.addressLine1Display + " " + a.addressLine2Display + " " +a.addressLine3Display + " " +a.addressLine4Display + " " + a.addressLine5Display + " ");
}

function setCountry() {

  var country0 = $('countryAbbrev');
  var state0 = $('stateAbbrev');
  
  if(isPerm1True)
  {	  
  var selectedIndex = 0;
  for (var i = 0; i < altCountryId.length; i++) {
    setOption(i, altCountryName[i], altCountryId[i], 'countryAbbrev');
    if (oriCountry == altCountryId[i]) {
      selectedIndex = i;
    }
  }
  country0.selectedIndex = selectedIndex;
  showStateOptions(country0.value, oriState);
  }
}

function countryChanged() {
  var country0 = $('countryAbbrev');
  var state0 = $('stateAbbrev');
  var selectedCountry = country0.value;
  for (var i = state0.length; i > 0; i--) {
    state0[i] = null;
  }
  showStateOptions(selectedCountry, '');
  state0.selectedIndex = 0;
}

function showStateOptions(selectedCountry, selectedState) {
  var stateArray = new Array();
  stateArray = altState[selectedCountry];
  var state0 = $('stateAbbrev');
  
  if (stateArray == null || stateArray.length == 0) {
    setOption(0, messagesData.pleaseselect, "", 'stateAbbrev');
    return;
  }
  var stateNameArray = new Array();
  stateNameArray = altStateName[selectedCountry];
  if (stateArray.length == 1) {
	    setOption(0, stateNameArray[0], stateArray[0], 'stateAbbrev');
	    return;
  }

  setOption(0, messagesData.pleaseselect, "", 'stateAbbrev');
  var selectedIndex = 0;
  var start = 1;
  for (var i = 0; i < stateArray.length; i++) {
	  if ((null != selectedState) && (selectedState == stateArray[i])) {
	        selectedIndex = start;
	  }
    setOption(start++, stateNameArray[i], stateArray[i], 'stateAbbrev');
  }
  state0.selectedIndex = selectedIndex;
}

function showTransitWin() {
	 // resizeFramecount = 1;
	 $("searchSectionDiv").style["display"] = "none";
	 $("shiptoFrameDiv").style["display"] = "none";	  
	 $("customerCarrierFrameDiv").style["display"] = "none";	 
	 $("resultFrameDiv").style["display"] = "none";	 
	 $("transitPage").style["display"] = "";
	}


function showNewCustErrorMessages()
{
  var resulterrorMessages = document.getElementById("errorMessagesAreaBody");
  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.innerHTML = resulterrorMessages.innerHTML;

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";

  var dhxWins = new dhtmlXWindows();
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window("errorWin")) {
  // create window first time
  var errorWin = dhxWins.createWindow("errorWin", 0, 0, 400, 250);
  errorWin.setText(messagesData.errors);
  errorWin.clearIcon();  // hide icon
  errorWin.denyResize(); // deny resizing
  errorWin.denyPark();   // deny parking
  errorWin.attachObject("errorMessagesArea");
  errorWin.attachEvent("onClose", function(errorWin){errorWin.hide();});
  errorWin.center();
  }
  else
  {
    // just show
    dhxWins.window("errorWin").show();
  }
}

function validateForm(approvestage) {
	  var errorMsg = "";
	  var addval = "";
	  for(i=1;i<=5;i++)
		  addval += $v('addressLine'+i).toUpperCase();
	  var z1 = $v("zip");
	  if( z1 && addval.indexOf(z1) == -1 ) {
		  alert(messagesData.fulladdressnozip);
		  return;
	  }

	  if(document.getElementById("countryAbbrev") == null ||document.getElementById("countryAbbrev").value.trim().length == 0) {
	    errorMsg += "\n"+messagesData.countryRequired;
	  }
	  if(document.getElementById("addressLine1") == null ||document.getElementById("addressLine1").value.trim().length == 0) {
	    errorMsg += "\n"+messagesData.addressRequired;
	  }
	  if( !$v('city') ) {
	    errorMsg += "\n"+messagesData.cityRequired;
	  }
	  var c1 = $v("city").toUpperCase();
	  if( c1 && addval.indexOf(c1) == -1 ) {
		  alert(messagesData.fulladdressnocity);
		  return;
	  }
	  var c1 = $v("countryAbbrev");
	  if( c1 == 'USA' || c1 == 'CAN' )
	  if(( document.getElementById("stateAbbrev") == null ||document.getElementById("stateAbbrev").value.trim().length == 0 ) && $("stateAbbrev").options.length > 1) {
		  errorMsg += "\n"+messagesData.stateProvinceRequired;
	  }
	  var z2 = $v("zipPlus");
	  if( !zipCheck(c1,z1,z2) ) {
	    errorMsg += "\n"+messagesData.zipRequired;
	  }

	  var billcountry = $v("countryAbbrev");
	  var billstate   = $v("stateAbbrev");
	  var vatkey1 = billcountry + "_" + billstate;
	  var vatkey2 = billcountry + "_";
	  var vatcountrystate = true;
	  var vatval = VAT_EXP[vatkey1];
	  if( vatval == null ) {
		  vatval = VAT_EXP[vatkey2];
		  vatcountrystate = false;
	  }
	  if( vatval != null ) 
	  for( reg = 1; reg <=4; reg++) {
		  var regi = reg;
		  if( regi == 1 ) regi = "";
		  if( $v('taxRegistrationType'+regi) == 'VAT' ) {
			  var vatnum = $v('taxRegistrationNumber'+regi);
			  var erms = ""; 
			  if( !vatnum || !vatnum.match(vatval) ) {

				  var countryText = $("countryAbbrev").options[$("countryAbbrev").selectedIndex].text;
				  var stateText = $("stateAbbrev").options[$("stateAbbrev").selectedIndex].text;

				  if( billstate && vatcountrystate ) {
					  erms = messagesData.vatinvalidforcountryregion.replace(/[{]0[}]/g,countryText).replace(/[{]1[}]/g,stateText);
				  }
				  else {
					  erms = messagesData.vatinvalidforcountry.replace(/[{]0[}]/g,countryText);
				  }
				  errorMsg += "\n"+messagesData.registration +" # "+reg+" :" + erms; 
			  }
		  }
	  }
	  
	  if( isEmptyV("paymentTerms") ){
		    errorMsg += "\n"+messagesData.paymentTermsRequired;
		  }

		/*  if( !$v("taxRegistrationNumber") )
			  errorMsg += "\n"+messagesData.taxregistrationnum;*/
			  
		  
		  
		  
//		  if( ( custReqType == 'New' || custReqType == 'Activate') && !$v("opsEntityId")) {
//		    errorMsg += "\n"+messagesData.operatingentity;
//		  }
		  
		  if( !isFloat($v('creditLimit'),true)) {
		    	alert( messagesData.creditlimit +":\n"+messagesData.mustbeanumber );
		    	$("creditLimit").focus();
		    	return false;
		  }
		  if( !isFloat($v('overdueLimit'),true)) {
		    	alert( messagesData.overduelimit +":\n"+messagesData.mustbeanumber );
		    	$("overdueLimit").focus();
		    	return false;
		  }
		  if( !isPositiveInteger($v('shelfLifeRequired'),true)) {
		    	alert( messagesData.defaultshelflife +":\n"+messagesData.mustbeanumber );
		    	$("shelfLifeRequired").focus();
		    	return false;
		  }

		  if( $v('shelfLifeRequired') > 100*1) {
		    	alert( messagesData.validvalues +"\n"+messagesData.defaultshelflife );
		    	return false;
		  }		  
		  if( !isFloat($v('jdeCustomerBillTo'),true)) {
		    	alert( messagesData.jdecustomerbillto +":\n"+messagesData.mustbeanumber );
		    	$("jdeCustomerBillTo").focus();
		    	return false;
		  }
		  
		  var autoEmailAddresses = $('autoEmailAddress').value;	
		  var listOfEmails = autoEmailAddresses.split(',');
		  for (var i = 0;i < listOfEmails.length;++i)
		  {	
			  if( !isEmail(listOfEmails[i].trim(),true)) {
				  alert(messagesData.einvoiceemailaddressinvalid);
				  autoEmailAddresses.focus();
			      return false;
			  }
		  }
		  

		   if(document.getElementById("autoEmailInvoice").checked)
			   {
			      if(autoEmailAddresses == null ||autoEmailAddresses.trim().length == 0)
			      {
		        	  errorMsg += "\n"+messagesData.autoEmailAddressesRequired;        	
				  
				  } 
			      
			      var autoEmailBatchSize = $v("autoEmailBatchSize");
			      
			      if(autoEmailBatchSize == null || autoEmailBatchSize.trim().length == 0) {
			    	  errorMsg += "\n"+messagesData.einvoicesbatchsizeRequired;    
			      }
			   }          
		  
	  if( errorMsg != '' ) {
		  alert(messagesData.validvalues+errorMsg);
		  return false;
	  }
	  return true;
}


function openCustTaxExempCert()
{
	var custId = document.getElementById("customerId").value;
	var loc = "/tcmIS/distribution/customertaxexemptioncertificate.do?customerId="+custId;		
	children[children.length] = openWinGeneric(loc,"customerTaxExemptionCertificate","650","300","yes","80","80","yes");	
}


function showDiv() {
	 document.getElementById("searchFrameDiv").style["display"] = "";
	 document.getElementById("showDivRow").style["display"] = "none";
	 document.getElementById("hideDivRow").style["display"] = "";
	 
}

function hideDiv() {
	 document.getElementById("searchFrameDiv").style["display"] = "none";
	 document.getElementById("showDivRow").style["display"] = "";
	 document.getElementById("hideDivRow").style["display"] = "none";
}





function showContactDiv() {
	 document.getElementById("resultGridDiv").style["display"] = "";
	 document.getElementById("showContactDivRow").style["display"] = "none";
	 document.getElementById("hideContactDivRow").style["display"] = "";
	 
}


function hideContactDiv() {
	 document.getElementById("resultGridDiv").style["display"] = "none";
	 document.getElementById("showContactDivRow").style["display"] = "";
	 document.getElementById("hideContactDivRow").style["display"] = "none";
}


function showShipToDiv() {
	 document.getElementById("shiptoGridDiv").style["display"] = "";
	 document.getElementById("showShipToDivRow").style["display"] = "none";
	 document.getElementById("hideShipToDivRow").style["display"] = "";
	 
}

function hideShipToDiv() {
	 document.getElementById("shiptoGridDiv").style["display"] = "none";
	 document.getElementById("showShipToDivRow").style["display"] = "";
	 document.getElementById("hideShipToDivRow").style["display"] = "none";
}


function showCustomerCarrierDiv() {
	 document.getElementById("customerCarrierGridDiv").style["display"] = "";
	 document.getElementById("showCustomerCarrierDivRow").style["display"] = "none";
	 document.getElementById("hideCustomerCarrierDivRow").style["display"] = "";
	 
}

function hideCustomerCarrierDiv() {
	 document.getElementById("customerCarrierGridDiv").style["display"] = "none";
	 document.getElementById("showCustomerCarrierDivRow").style["display"] = "";
	 document.getElementById("hideCustomerCarrierDivRow").style["display"] = "none";
}



// resizing functions start

function resizeWin() {
	
	 if (resizeResultscount ==0 && appResizeFramecount ==0 )
	 {
		 
	  resizeThisWinResults();
	  resizeResultscount=0;
	  resizeResultscount++;
	  try
	  {
	   if (resizeGridWithWindow)
	   {
	    setTimeout('reSizeNoSearchGridWidths();',5);
	    
	   }
	  }
	  catch(exGrid)
	  {
		  
	   //alert("Here 209");
	  }    
	 }
	 
	 resizeResultscount =0;
	}


function reSizeNoSearchGridWidths()
{
/* if (cgrid !=null)  { 
   reSizeCoLumnWidths(cgrid);
 }
 
 if (sgrid !=null)
 { 
   reSizeCoLumnWidths(sgrid);
 }
 
 if (carrierGrid !=null)
 { 
   reSizeCoLumnWidths(carrierGrid);
 }*/
}


function resizeThisWinResults(extraReduction) {

	 if (extraReduction ==  null)
	 {
	  extraReduction = 0;
	 }

	  var matchResultSectionDivs= false;
	  try
	  {
	   setWindowSizes();
	      
	   var topNavigationDivHeight	=0;
	   try
	   {
	    var topNavigationDiv = document.getElementById("topNavigation");
	    topNavigationDivHeight = topNavigationDiv.offsetHeight;
	   }
	   catch (ex)
	   {
	    topNavigationDivHeight	=0;
	   }

	   var footer1DivHeight=8;
	   var footer2DivHeight=8;
	   var footer3DivHeight=8;
	   try
	   {
	    var footer1Div = document.getElementById("contactFooter");
	    var footer2Div = document.getElementById("sfooter");
	    var footer3Div = document.getElementById("carrierFooter");
	    
	    footer1DivHeight = footer1Div.offsetHeight+8;
	    footer2DivHeight = footer2Div.offsetHeight+8;	    
	    footer3DivHeight = footer3Div.offsetHeight+8;
	    
	    if(footer1DivHeight == 0)
	    {
	    	footer1DivHeight = 8;
	    }
	    if(footer2DivHeight == 0)
	    {
	    	footer2DivHeight = 8;
	    }
	    if(footer3DivHeight == 0)
	    {
	    	footer3DivHeight = 8;
	    }
	    
	    
	    
	   }
	   catch (ex)
	   {
		   footer1DivHeight	= 8;
		   footer2DivHeight	= 8;
		   footer3DivHeight = 8;
	   }

	   var mainUpdateLinksHeight=0;
	   try
	   {
	    var mainUpdateLinksDiv = document.getElementById("mainUpdateLinks");
	    mainUpdateLinksHeight = mainUpdateLinksDiv.offsetHeight;
	    if(mainUpdateLinksHeight ==0)
	    {
	      mainUpdateLinksHeight = 8;
	    }
	    else
	    {
	        if (_isIE)
	        {
	            mainUpdateLinksHeight = mainUpdateLinksHeight +5;
	        }
	        else
	        {
	            mainUpdateLinksHeight = mainUpdateLinksHeight +8;
	        }
	    }
	   }
	   catch (ex)
	   {
	    mainUpdateLinksHeight=8;
	   }

	   //bodyOffsetHeight = window.document.body.offsetHeight;   
	   matchResultSectionDivs = true;
	   var contactTotalLines  =0;
	   var shipToTotalLines = 0;
	   var carrierToTotalLines =0;
	   
	   
	   try
	   {
		   contactTotalLines = document.getElementById("contactTotalLines").value;
		   shipToTotalLines = document.getElementById("shipToTotalLines").value;
		   carrierToTotalLines = document.getElementById("carrierToTotalLines").value;
	   }
	   catch (ex)
	   {
	    
	    contactTotalLines = 0;
		shipToTotalLines = 0;
		carrierToTotalLines =0;
	   }

	   //alert("myHeight: "+myHeight+" topNavigationDivHeight: "+topNavigationDivHeight+" searchSectionHeight: "+searchSectionHeight+" mainUpdateLinksHeight "+mainUpdateLinksHeight+" footerDivHeight: "+footerDivHeight+"");
	   if (_isIE)
	   {
	    resultSectionHeight = myHeight-12-topNavigationDivHeight-mainUpdateLinksHeight-footer1DivHeight-footer2DivHeight-footer3DivHeight-internalHeightIEOffset-extraReduction;
	   }
	   else
	   {
	    resultSectionHeight = myHeight-12-topNavigationDivHeight-mainUpdateLinksHeight-footer1DivHeight-footer2DivHeight-footer3DivHeight-internalHeightFFOffset-extraReduction;
	   }

	   if ( (contactTotalLines == 0) && (shipToTotalLines==0) && (carrierToTotalLines ==0))
	   {
	     resultSectionHeight = 25;
	   }

	   /*If the result table is less than the minimum height we keep the result frame height at minimum height.
	   When there are no records no need to show minimum height*/
	   var minHeight = document.getElementById("minHeight");
	   var minHeightValue = 0;
	   try
	   {
	     //alert("minHeight  "+minHeight.value.trim()*1);
	     minHeightValue = minHeight.value.trim()*1;
	   }
	   catch(ex)
	   {
	     //alert("Here error 86");
	     minHeightValue = 0;
	   }

	  if ((contactTotalLines != 0) && (shipToTotalLines!=0) & (carrierToTotalLines!=0))
	  {
	   if (resultSectionHeight < minHeightValue)
	   {
	    resultSectionHeight = minHeightValue;
	   }
	  }

	  if (resultSectionHeight > 150 || matchResultSectionDivs)
	  {
	   //frameName.height=resultSectionHeight;

	   if ((contactTotalLines != 0) && (shipToTotalLines!=0) && (carrierToTotalLines!=0))
	   {
	     try
	     {
	    	 //setTimeout('setThisGrid1Height('+resultSectionHeight+');',2);
	    	 //setTimeout('setThisGrid2Height('+resultSectionHeight+');',2);
	     }
	     catch(exGrid)
	     {
	       //alert("Here 209");
	     }

	     try
	     {
	       setTimeout('setGrid1Size();',2);
	       setTimeout('setGrid2Size();',2);
	       setTimeout('setGrid3Size();',2);
	     }
	     catch(exGrid)
	     {
	       //alert("Here 209");
	     }
	   }
	 }

	  if (resizeGridWithWindow)
	  {  
	  try
	  {
	    setTimeout('setGrid1Width();',50);
	    setTimeout('setGrid2Width();',50);
	    setTimeout('setGrid3Width();',50);
	    
	  }
	  catch(exGrid)
	  {
	    //alert("Here 209");
	  }    	 
	  }
	 }
	 catch (ex)
	 { //alert(ex);
	   alert("here error 68 resizeResults()");
	 }
	}

/*function setThisGrid1Height(resultGridHeight)
{
 try
 {
  var id=cptGrid.entBox.id;
  var griDiv = document.getElementById(id);
  griDiv.style.height = resultGridHeight-3 + "px";
 }
 catch(ex)
 {
     //alert("THis means the grid was not initialized");
 }
}

function setThisGrid2Height(resultGridHeight)
{
 try
 {
  var id=cptExpGrid.entBox.id;
  var griDiv = document.getElementById(id);
  griDiv.style.height = resultGridHeight-3 + "px";
 }
 catch(ex)
 {
     //alert("THis means the grid was not initialized");
 }
}
*/

function setGrid1Size()
{
	cgrid.setSizes();
}


function setGrid2Size()
{
	sgrid.setSizes();
}


function setGrid3Size()
{
	carrierGrid.setSizes();
}


function setGrid1Width()
{
setWindowSizes(); 
if (_isIE)
{
  myWidth = myWidth - internalWidthIEOffset;
}
else
{
  myWidth = myWidth - internalWidthFFOffset;
}
   
 try
 {
  var id=cgrid.entBox.id;
  var griDiv = document.getElementById(id);
  griDiv.style.width = myWidth + "px";
  lastWindowWidth = myWidth;
 }
 catch(ex)
 {
     //alert("THis means the grid was not initialized");
 }
}

function setGrid2Width()
{
setWindowSizes(); 
if (_isIE)
{
  myWidth = myWidth - internalWidthIEOffset;
}
else
{
  myWidth = myWidth - internalWidthFFOffset;
}
   
 try
 {
  var id=sgrid.entBox.id;
  var griDiv = document.getElementById(id);
  griDiv.style.width = myWidth + "px";
  lastWindowWidth = myWidth;
 }
 catch(ex)
 {
     //alert("THis means the grid was not initialized");
 }
}


function setGrid3Width()
{
setWindowSizes(); 
if (_isIE)
{
  myWidth = myWidth - internalWidthIEOffset;
}
else
{
  myWidth = myWidth - internalWidthFFOffset;
}
   
 try
 {
  var id=carrierGrid.entBox.id;
  var griDiv = document.getElementById(id);
  griDiv.style.width = myWidth + "px";
  lastWindowWidth = myWidth;
 }
 catch(ex)
 {
     //alert("THis means the grid was not initialized");
 }
}
// resizing functions end

function showCreditStatusMsg()
{
	var creditStatus = $('creditStatus');
	var answer;
	if((creditStatus.options[creditStatus.selectedIndex].text=="Stop"))		
	{
		answer = confirm(messagesData.creditStatusChange);	
		if(answer==false)
		{
			creditStatus.selectedIndex=0;
		}	
	}
}

function showCreditReview() {

	var cusId = $v("customerId");
	var opsEntityId = $v("opsEntityId"); 
	
	var loc = "/tcmIS/distribution/creditreview.do?customerId="+cusId+"&opsEntityId="+opsEntityId;	
  
  	children[children.length] = openWinGeneric(loc,"creditreview"+cusId,"400","420","yes","80","80","yes");	
}


function checkOne(rowId) {
	for(var i=0;i<cgrid.getRowsNum();i++){
		contactRowid = cgrid.getRowId(i);
		
		if( !$("defaultContact"+rowId).disabled && $("defaultContact"+rowId).checked && contactRowid != rowId && $("defaultContact"+contactRowid).checked) {
			$("defaultContact"+contactRowid).checked = false;
			updateHchStatusA("defaultContact"+contactRowid);
		}
	} 
}

function closeAllchildren()
{
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
//	} 
}