

function submitUpdate() {
  var flag = validateForm();
  if(flag) {
    $("uAction").value = 'update';
    document.genericForm.submit();
  }
  return flag;
}


function validateForm() {
    if(document.genericForm.reorderPoint == null || document.genericForm.reorderPoint.value.trim().length == 0) {
      alert(messagesData.reorderPointRequired);
      return false;
    }
    if(!isInteger(document.genericForm.reorderPoint.value.trim(), true)) {
      alert(messagesData.reorderPointInteger);
      return false;
    }
    if(document.genericForm.stockingLevel == null || document.genericForm.stockingLevel.value.trim().length == 0) {
      alert(messagesData.stockingLevelRequired);
      return false;
    }
    if(!isInteger(document.genericForm.stockingLevel.value.trim(), true)) {
      alert(messagesData.stockingLevelInteger);
      return false;
    }

	 if(document.genericForm.leadTimeDays == null || document.genericForm.leadTimeDays.value.trim().length == 0) {
      alert(messagesData.leadTimeDaysRequired); 
      return false;
    }
    if(!isInteger(document.genericForm.leadTimeDays.value.trim(), true)) {
      alert(messagesData.leadTimeDaysInteger);
      return false;
    }
    if(parseInt(document.genericForm.reorderPoint.value.trim()) > parseInt(document.genericForm.stockingLevel.value.trim())) {
      alert(messagesData.reorderPointLessThanStockingLevel);
      return false;
    }
	 if (parseInt(document.genericForm.reorderPoint.value.trim()) == 0 && parseInt(document.genericForm.stockingLevel.value.trim()) != 0) {
		alert(messagesData.reorderPointGreaterThanZero);
		return false;
  	 }
	 if(!isFormChanged()) {
      alert(messagesData.noChange);
      return false;
    }
  return true;
}

function isFormChanged() {
  if(document.genericForm.reorderPoint.value.trim() != document.genericForm.oldReorderPoint.value) {
    return true;
  }
  if(document.genericForm.stockingLevel.value.trim() != document.genericForm.oldStockingLevel.value) {
    return true;
  }
  if(document.genericForm.leadTimeDays.value.trim() != document.genericForm.oldLeadTimeDays.value) {
    return true;
  }
  return false;
}

