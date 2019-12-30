function hubChanged() {
  var hub0 = document.getElementById("hub");
  var inventoryGroup0 = document.getElementById("inventoryGroup");
  var selectedHub = hub0.value;
  for (var i = inventoryGroup0.length; i > 0; i--) {
    inventoryGroup0[i] = null;
  }

  showInventoryGroupOptions(selectedHub);
  inventoryGroup0.selectedIndex = 0;
}

function showInventoryGroupOptions(selectedHub) {
  var inventoryGroupArray = new Array();
  inventoryGroupArray = altInventoryGroup[selectedHub];

  var inventoryGroupNameArray = new Array();
  inventoryGroupNameArray = altInventoryGroupName[selectedHub];

  if(inventoryGroupArray.length == 0) {
    setOption(0,messagesData.all,"", "inventoryGroup")
  }

  for (var i=0; i < inventoryGroupArray.length; i++) {
    setOption(i,inventoryGroupNameArray[i],inventoryGroupArray[i], "inventoryGroup")
  }
}

function search() {
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  var action = document.getElementById("action");
  action.value = 'submitSearch';
  document.genericForm.target='resultFrame';
  parent.showPleaseWait();
  
  return true;
}

function generateExcel() {
  var action = document.getElementById("action");
  action.value = 'createExcel';
  openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',"show_excel_report_file","650","600","yes");             
	document.genericForm.target='_ShipConfirmGenerateExcel';
	var a = window.setTimeout("document.genericForm.submit();",500);
}

function myOnload()
{
  displaySearchDuration();
  setResultFrameSize();
}

function createShipment() {
  var action = document.getElementById("action");
  action.value = 'createShipment';
	parent.showPleaseWait();
  document.genericForm.submit();
}

function updateShipment() {
  var action = document.getElementById("action");
  action.value = 'updateShipment';
	parent.showPleaseWait();
  document.genericForm.submit();
}


function confirmShipment(isAutoShipConfirm) {
  if(isAutoShipConfirm == 'true') {
	  document.getElementById('deliveredDate').value = parent.document.getElementById('deliveredDate').value;
    var flag = validateForm();
    if(flag) {
      var action = document.getElementById("action");
      action.value = 'confirmShipment';
	    parent.showPleaseWait();
	    document.genericForm.submit();
    }
  }
  else {
	 var loc = 'confirmshipment.do?action=showconfirmshipment&branchPlant='+document.getElementById('branchPlant').value;
		 
	 if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	 
    openWinGeneric(loc,'_ConfirmShipment','900','600','yes');
  }
}

function confirmNotAutoShipment() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'confirmNotAutoShipment';
    submitOnlyOnce();
    document.getElementById("genericForm").submit();
  }
}

function carrierChanged(rowNumber) {
  var shipmentId = document.getElementById("shipmentId"+rowNumber);
  var carrierCode = document.getElementById("carrierCode"+rowNumber);
  var account = document.getElementById("account"+rowNumber);
  var companyId = document.getElementById("companyId"+rowNumber);
  account.value = accountNumber[shipmentId.value+"-"+carrierCode.value];
  companyId.value = accountOwner[shipmentId.value+"-"+carrierCode.value];
//alert(accountNumber[shipmentId.value+"-"+carrierCode.value]);
//alert(shipmentId.value + "-" + carrierCode.value);
}

function validateForm() {
  var deliveredDate = document.getElementById("deliveredDate");
  if(deliveredDate.value.length == 10) {
    if(checkdate(deliveredDate) == false ){
      alert(messagesData.invalidDateFormat);
      return false;
    }
  }
  else if(deliveredDate.value.length > 0) {
    alert(messagesData.invalidDateFormat);
    return false;
  }
  if(!isAnyRowChecked()) {
    alert(messagesData.noRowChecked);
    return false;
  }
  return true;
}

function isAnyRowChecked() {
  var rowCount = document.getElementById("rowCount");
  var rowChecked = false;
  var resultdoc = getResultFrame();
  for(var i=0; i<rowCount.value && !rowChecked; i++) {
    var checkbox = resultdoc.document.getElementById("checkbox" + i);
    if(checkbox.checked == true) {
      rowChecked = true;
    }
  }
  return rowChecked;
}

function showLegend(){
  openWinGeneric("/tcmIS/help/shipconfirmlegend.html","shipconfirmlegend","290","300","no","50","50")
}

function checkAll(rowCount) {
  var allCheck = document.getElementById("allCheck");
  var check;
  var value;
  if(!allCheck.checked) {
    check = false;
  }
  else {
    check = true;
  }
  for(var i=0; i<rowCount; i++) {
    var checkbox = document.getElementById("checkbox" + i);
    checkbox.checked = check;
    checkbox.value = value;
  }
}

function checkBox(rowNumber, originalShipmentId) {
  var shipmentId = document.getElementById("shipmentId" + rowNumber);
  var check;
  if(shipmentId.value == originalShipmentId) {
    check = false;
  }
  else {
    check = true;
  }
  var checkbox = document.getElementById("checkbox" + rowNumber);
  checkbox.checked = check;
}

function printBolLong() {
  var action = getResultFrame().document.getElementById("action");
  action.value = 'printBolLong';
  getResultFrame().document.genericForm.submit();
}

function printBolShort() {
  var action = getResultFrame().document.getElementById("action");
  action.value = 'printBolShort';
  getResultFrame().document.genericForm.submit();
}

function printPackingList() {
  var action = getResultFrame().document.getElementById("action");
  action.value = 'printPackingList';
  getResultFrame().document.genericForm.submit();
}

function printBoxLabels() {
  var action = getResultFrame().document.getElementById("action");
  action.value = 'printBoxLabels';
  getResultFrame().document.genericForm.submit();
}

function consolidatedBol() {
  var action = getResultFrame().document.getElementById("action");
  action.value = 'consolidatedBol';
  getResultFrame().document.genericForm.submit();
}

function cancel() {
  window.close();
}

function showErrorMessages()
{
		parent.showErrorMessages();
}

function returnToMain() {
  var action = opener.document.getElementById("action");
  action.value = 'submitSearch';
  opener.search();
  window.close();
}

function printDelDocuments()
{
	var loc = 'printlabel.do?labelType=DELIVERYDOCS&printerLocation=PHILAS1';
		
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
    openWinGeneric('/tcmIS/common/loadingfile.jsp','_newprintDelDocuments','650','600','yes');
    document.shipconfirm.target='_newprintDelDocuments';
    document.shipconfirm.action=loc;
    var a = window.setTimeout("document.shipconfirm.submit();",500);
}
