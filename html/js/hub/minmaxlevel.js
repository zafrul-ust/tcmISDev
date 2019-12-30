
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
  if(document.genericForm.criteria == null || document.genericForm.criteria.value.trim().length == 0) {
    alert(messagesData.criteriaRequired);
    return false;
  }

  if(document.genericForm.criterion.value == 'itemId') {
    if(!isInteger(document.genericForm.criteria.value.trim(), true)) {
      alert(messagesData.itemIdInteger);
      return false;
    }
  }
  showPleaseWait();
  return true;
}

function validateUpdateForm(numberOfRows) {
  var flag = true;
  if(numberOfRows != null) {
    for(var i=0; i<numberOfRows; i++) {
      var stockingMethod = document.getElementById("stockingMethod" + i);
      var found = false;
      if(stockingMethod != null && stockingMethod.value == 'MM') {
        found = true
      }
      else {
        found = false;
      }
      if(found) {
        if(!checkReorderPoint(i)) {
          flag = false;
        }
        if(!checkReorderQuantity(i)) {
          flag = false;
        }
        if(!checkStockingLevel(i)) {
          flag = false;
        }
        if(!checkLookAheadDays(i)) {
          flag = false;
        }
      }
    }
  }
  return flag;
}

function submitSearchForm() {
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
if(validateForm())
{
 document.genericForm.submit();
 showPleaseWait();
}

}

function submitUpdate() {
  numberOfRows = document.getElementById('totalLines').value;
  var flag = validateUpdateForm(numberOfRows);
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'update';
    submitOnlyOnce();
    document.genericForm.submit();
  }
  showPleaseWait();
  return flag;
}

function stockingMethodChanged(rowNumber) {
  var stockingMethod = document.getElementById("stockingMethod" + rowNumber);

  var found = false;
  if(stockingMethod != null && stockingMethod.value == 'MM') {
    found = true
  }
  else {
    found = false;
  }
  var reorderPoint = document.getElementById("reorderPoint" + rowNumber);
  var oldReorderPoint = document.getElementById("oldReorderPoint" + rowNumber);
  var stockingLevel = document.getElementById("stockingLevel" + rowNumber);
  var oldStockingLevel = document.getElementById("oldStockingLevel" + rowNumber);
  var reorderQuantity = document.getElementById("reorderQuantity" + rowNumber);
  var oldReorderQuantity = document.getElementById("oldReorderQuantity" + rowNumber);
  if(found) {
    oldReorderPoint.disabled=false;
    reorderPoint.disabled=false;
    reorderPoint.style.visibility = 'visible';
    oldStockingLevel.disabled=false;
    stockingLevel.disabled=false;
    stockingLevel.style.visibility = 'visible';
    oldReorderQuantity.disabled=false;
    reorderQuantity.disabled=false;
    reorderQuantity.style.visibility = 'visible';
  }
  else if(stockingMethod != null){
    oldReorderPoint.disabled=true;
    reorderPoint.disabled=true;
    reorderPoint.style.visibility = 'hidden';
    oldStockingLevel.disabled=true;
    stockingLevel.disabled=true;
    stockingLevel.style.visibility = 'hidden';
    oldReorderQuantity.disabled=true;
    reorderQuantity.disabled=true;
    reorderQuantity.style.visibility = 'hidden';
  }
  receiptProcessingMethodChanged(rowNumber);
}

function receiptProcessingMethodChanged(rowNumber) {
  var receiptProcessingMethod = document.getElementById("receiptProcessingMethod" + rowNumber);
  var found = false;
  if(receiptProcessingMethod != null && receiptProcessingMethod.value == 'Issue On Receipt') {
    found = true
  }
  else {
    found = false;
  }
  var reorderPoint = document.getElementById("reorderPoint" + rowNumber);
  var oldReorderPoint = document.getElementById("oldReorderPoint" + rowNumber);
  var stockingLevel = document.getElementById("stockingLevel" + rowNumber);
  var oldStockingLevel = document.getElementById("oldStockingLevel" + rowNumber);
  var reorderQuantity = document.getElementById("reorderQuantity" + rowNumber);
  var oldReorderQuantity = document.getElementById("oldReorderQuantity" + rowNumber);
  if(found) {
    reorderPoint.value = "0";
    stockingLevel.value = "0";
    reorderQuantity.value = "0";
    reorderPoint.disabled=true;
    stockingLevel.disabled=true;
    reorderQuantity.disabled=true;
  }
  else {
    reorderPoint.disabled=false;
    stockingLevel.disabled=false;
    reorderQuantity.disabled=false;
  }
}

function checkReorderQuantity(rowNumber) {
  var message = "";
  var errorFlag = false;
  var stockingMethod  =  document.getElementById("stockingMethod"+rowNumber+"");
  var reorderPoint  =  document.getElementById("reorderPoint"+rowNumber+"");
  var stockingLevel  =  document.getElementById("stockingLevel"+rowNumber+"");
  var reorderQuantity  =  document.getElementById("reorderQuantity"+rowNumber+"");

  if (stockingMethod != null && stockingMethod.value == "MM") {
    if ( !(isInteger(reorderQuantity.value, true)) ) {
      message = message + messagesData.reorderQuantityInteger + "\n\n";
      errorFlag = true;

    }
    else if (reorderQuantity.value.trim().length > 0 ) {
    		if( reorderQuantity.value.trim()*1 == 0) {
      			message = message + messagesData.reorderQuantityNotZero + "\n\n";
      			reorderQuantity.value = "";
      			errorFlag = true;
    		}
		    else {
      			stockingLevel.value = "0";
    		}
  	}
  }

  if (errorFlag) {
    alert(message);
  }
  return !errorFlag;
}

function checkReorderPoint (rowNumber) {

  var message = "";
  var errorFlag = false;
  var stockingMethod  =  document.getElementById("stockingMethod"+rowNumber);
  var reorderPoint  =  document.getElementById("reorderPoint"+rowNumber);
  var stockingLevel  =  document.getElementById("stockingLevel"+rowNumber);
  var reorderQuantity  =  document.getElementById("reorderQuantity"+rowNumber);


  if (stockingMethod != null && stockingMethod.value == "MM") {
    if ( !(isInteger(reorderPoint.value, false)) ) {
      message = message + messagesData.reorderPointInteger + "\n\n";
      errorFlag = true;
    }
    else if ( !(isInteger(stockingLevel.value, false)) ) {
      message = message + messagesData.stockingLevelInteger + "\n\n";
      errorFlag = true;
    }
    else if (reorderQuantity.value.trim().length == 0 && (reorderPoint.value.trim()*1 > stockingLevel.value.trim()*1)){
      message = message + messagesData.reorderPointVersusLevel + "\n\n";
      errorFlag = true;
    }
  }

  if (errorFlag) {
    alert(message);
  }
  return !errorFlag;
}

function checkStockingLevel (rowNumber) {
  var message = "";
  var errorFlag = false;
  var stockingMethod  =  document.getElementById("stockingMethod"+rowNumber+"");
  var reorderPoint  =  document.getElementById("reorderPoint"+rowNumber+"");
  var stockingLevel  =  document.getElementById("stockingLevel"+rowNumber+"");
  var reorderQuantity  =  document.getElementById("reorderQuantity"+rowNumber+"");

  if (stockingMethod != null && stockingMethod.value == "MM") {
    if ( !(isInteger(stockingLevel.value, false)) ) {
     message = message + messagesData.stockingLevelInteger + "\n\n";
      errorFlag = true;
    }
    else if ( !(isInteger(reorderPoint.value, false)) ) {
      message = message + messagesData.reorderPointInteger + "\n\n";
      errorFlag = true;
    }
    else {
      if(stockingLevel.value.trim()*1 != 0) {
        reorderQuantity.value = "";
      }
      /* already checked at check reorder point.
      if (reorderQuantity.value.trim().length == 0 && (reorderPoint.value.trim()*1 > stockingLevel.value.trim()*1)){
        message = message + messagesData.reorderPointVersusLevel + "\n\n";
        errorFlag = true;
      }
      */
    }
  }

  if (errorFlag) {
    alert(message);
  }
    return !errorFlag;
}

function checkLookAheadDays(rowNumber) {
 var message = "";
  var errorFlag = false;
  var stockingMethod  =  document.getElementById("stockingMethod"+rowNumber+"");
  var lookAheadDays  =  document.getElementById("lookAheadDays"+rowNumber+"");

  if ( !(isInteger(lookAheadDays.value, true)) ) {
    message = message + messagesData.lookAheadDaysInteger + "\n\n";
    errorFlag = true;
  }

  if (errorFlag) {
    alert(message);
  }
  return !errorFlag;
}

function myOnLoad(numberOfRows) {
  if(numberOfRows != null) {
    for(var i=0; i<numberOfRows; i++) {
      stockingMethodChanged(i);
      receiptProcessingMethodChanged(i);
    }
  }
 displaySearchDuration(); 
 setResultFrameSize();
 showUpdateLink();
}

function showErrorMessages()
{
		parent.showErrorMessages();
}

function showUpdateLink() {
	if( showUpdateLinks )
		parent.document.getElementById("updateResultLink").style['display'] = "";
	parent.document.getElementById("mainDescription").innerHTML = " | " + mainDescription;
}

function generateExcel() {
    var action = document.getElementById("action");
    action.value = 'createExcel';

	 openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_MinMaxLevelGenerateExcel','650','600','yes');
     document.genericForm.target='_MinMaxLevelGenerateExcel';
     var a = window.setTimeout("document.genericForm.submit();",500);
}

function viewHistory(hub, inventoryGroup, itemId, partNumber) {
	//alert(hub + "," + inventoryGroup + "," + itemId + "," + partNumber);
    openWinGeneric("/tcmIS/hub/minmaxlevelhistory.do?action=history&branchPlant="+hub+"&inventoryGroup="+inventoryGroup+"&itemId="+itemId+"&catPartNo="+partNumber,"recipeDetail","700","800","yes");
    return false;
}