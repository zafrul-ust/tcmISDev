
function hubChanged() {
  var hub0 = document.getElementById("branchPlant");
  var ig0 = document.getElementById("inventoryGroup");
  var selectedHub = hub0.value;

  for (var i = ig0.length; i > 0; i--) {
    ig0[i] = null;
  }
  showIgOptions(selectedHub);
  ig0.selectedIndex = 0;
}

function showIgOptions(selectedHub) {
  var igArray = new Array();
  igArray = altInventoryGroupId[selectedHub];

  var igNameArray = new Array();
  igNameArray = altInventoryGroupName[selectedHub];

  if(igArray.length == 0) {
    setOption(0,messagesData.all,"", "inventoryGroup")
  }

  for (var i=0; i < igArray.length; i++) {
    setOption(i,igNameArray[i],igArray[i], "inventoryGroup")
  }
}

function validateForm() {
  if(document.genericForm.itemOrDescription.value == 'itemId') {
    if(!isInteger(document.genericForm.criteria.value.trim(), true)) {
      alert(messagesData.itemIdInteger);
      return false;
    }
  }
  document.genericForm.target='resultFrame';
  parent.showPleaseWait();
  return true;
}

function validateTransferForm(numberOfRows) {
  var totalLines = document.getElementById("totalLines").value;
  var flag = false;
    for(var i=0; i< totalLines; i++) {
	  var ok = document.getElementById("ok" + i);
	  if( ok == null || !ok.checked ) continue;
      var available = document.getElementById("quantity" + i);
      var send = document.getElementById("transferQuantity" + i);
		
		send.value = send.value.trim();
        if(!isPositiveInteger(send.value, false)) {
          alert(messagesData.transferQuantityInteger);
          ok.checked = false;
          return false;
        }
        if(available.value*1 < send.value*1 ) {
          alert(messagesData.Quantitytosendnotgreaterthanavailable);
          ok.checked = false;
          return false;
        }
      flag = true;
     }
     if(!flag) { alert(messagesData.noTransfer); return false;
     }
  return true;
}

function submitTransfer(numberOfRows) {
  var flag = validateTransferForm(numberOfRows);
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'transfer';
	submitOnlyOnce();
	parent.showPleaseWait();
    document.genericForm.submit();
  }
  return flag;
}

function validateQuantity(rowNumber) {
  var available = document.getElementById("quantity" + rowNumber);
  var send = document.getElementById("transferQuantity" + rowNumber);
  var ok = document.getElementById("ok" + rowNumber);
  if(send != null) {
	send.value = send.value.trim();

    if(!isPositiveInteger( send.value , false)) {
      alert(messagesData.transferQuantityInteger);
      ok.checked = false;
    }
    if(available.value*1 < send.value*1) {
        alert(messagesData.Quantitytosendnotgreaterthanavailable);
   		ok.checked = false;
        }
  }
}

function toHubChanged(rowNumber) {
  var hub0 = document.getElementById("toBranchPlant" + rowNumber);
  var ig0 = document.getElementById("toInventoryGroup" + rowNumber);
  var selectedHub = hub0.value;

  for (var i = ig0.length; i > 0; i--) {
    ig0[i] = null;
  }
  showToIgOptions(selectedHub, rowNumber);
  ig0.selectedIndex = 0;
}

function showToIgOptions(selectedHub, rowNumber) {
  var igArray = new Array();
  igArray = toAltInventoryGroupId[selectedHub];

  var igNameArray = new Array();
  igNameArray = toAltInventoryGroupName[selectedHub];

  if(igArray.length == 0) {
    setOption(0,messagesData.all,"", "toInventoryGroup" + rowNumber)
  }

  for (var i=0; i < igArray.length; i++) {
    setOption(i,igNameArray[i],igArray[i], "toInventoryGroup" + rowNumber)
  }
}

function myOnLoad() {
		standardResultOnLoad();
}

function limitText(rownum, limitNum) {
    var limitCount;
    var limitField  =  document.getElementById("comments"+rownum+"");
// alert(limitField.value.length);
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
		alert(messagesData.maximum4000);
	} 
}