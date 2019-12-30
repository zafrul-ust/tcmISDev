function confirmNotAutoShipment() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'confirmNotAutoShipment';
    submitOnlyOnce();
    document.genericForm.submit();
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
  //var resultdoc = getResultFrame();
  for(var i=0; i<rowCount.value && !rowChecked; i++) {
    var checkbox = document.getElementById("checkbox" + i);
    if(checkbox.checked == true) {
      rowChecked = true;
    }
  }
  return rowChecked;
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

function consolidatedBol() {
  var action = document.getElementById("action");
  action.value = 'consolidatedBol';
  submitOnlyOnce();
  document.genericForm.submit();
}
function cancel() {
  window.close();
}

function returnToMain() {
  var action = opener.document.getElementById("action");
  action.value = 'submitSearch';
	opener.parent.showPleaseWait();
	opener.document.genericForm.submit();	
  //opener.window.frames["searchFrame"].search();
  window.close();
}
function showErrorMessages()
{
		parent.showErrorMessages();
}
