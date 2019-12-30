var children = new Array(); 

var inputSize = new Array();
inputSize = {
  "returnReasonId": 50,
  "itemId": 100
};

var children = new Array(); 

var rowCounter = 1;
var reasonRowRemovalCounter = 0;
var newFeesRowRemovalCounter = 0;
var reasonGrid;
var showReasonGrid = false;
var showNewFeesGrid = false
var actionDesired;
var newUnitPriceErrCounter = 0;
var originalFeesGrid;
var newFeesGrid;
var newFeesGridRowCounter = 1;
var newprice_hedFunction = "onChange= checkNewFeePrice";
var submitReasonGridForDeletion = false;
var submitNewFeesGridForDeletion = false;
var selectedReasonRowId = null;

function validateUpdateInput() {
  var errorMessage = messagesData.validvalues + "\n\n";
  
  var errorCount = 0;
  var action = $("action");
  var replacementRdBtn = document.getElementsByName("wantReplacement");

  if ((typeof($("returnQuantityRequested")) != 'undefined') && (typeof($("newUnitPrice")) != 'undefined') && (typeof($("originalQty")) != 'undefined')) {
    try {
      var returnQuantityRequested = $("returnQuantityRequested");
      var newUnitPrice = $("newUnitPrice");
      var replacementCartPN = $("replacementCartPN");
      var originalQty = $("originalQty");
      var radioValue;
      var authorizedReturnQty = $("quantityReturnAuthorized");
      var newUnitPriceErrCounter = 0;
//      var validReasonDropDown = checkUniqueReasons();
      var validNewFeesDropDown = checkUniqueNewFees();
      var needDateElem = $("replacementRequiredDatetime");
      var promisedDateElem =  $("replacementPromiseDate");
      var quantityShipped = $('quantityShipped');
      
      if ((null != newUnitPrice) && (newUnitPrice.value.trim().length != 0 && (!(isFloat(newUnitPrice.value.trim())) ))) {
        errorMessage += messagesData.newUnitPrice + "\n";
        errorCount = 1;
        newUnitPrice.value = "";
        newUnitPriceErrCounter++;
      }

      for (var j = 0; j < replacementRdBtn.length; j++) {
        if (replacementRdBtn[j].checked == true && replacementRdBtn[j].value == "Y") {
          radioValue = replacementRdBtn[j].value;
        }
        else if (replacementRdBtn[j].checked == true && replacementRdBtn[j].value == "N") {
          radioValue = replacementRdBtn[j].value;
        }
      }
      // Check if new price is entered if Does Customer want replacement is Y 
      if ((radioValue == 'Y') && (newUnitPrice.value.trim().length == 0)) {
        if (newUnitPriceErrCounter == 0) {
          errorMessage += messagesData.newUnitPrice + "\n";
          errorCount = 1;
        }
      }
      // Check if part number is entered if Does Customer want replacement is Y 
      if ((radioValue == 'Y') && (replacementCartPN.value.trim().length == 0)) {
        errorMessage += messagesData.replacementCartPN + "\n";
        errorCount = 1;
      }

      if ((actionDesired == "approve")  || (actionDesired == "submit")) {
        if (reasonGrid.getRowsNum() == 0) {
          errorMessage += messagesData.reason + "\n";
          errorCount = 1;
        }
      }

      if (radioValue == 'Y')
      {
	      if ((null != needDateElem) && (needDateElem.value.trim().length == 0)) {
	          errorMessage += messagesData.needDate + "\n";
	          errorCount = 1;
	      }
	      
	      if ((null != promisedDateElem) && (promisedDateElem.value.trim().length == 0)) {
	          errorMessage += messagesData.promisedDate + "\n";
	          errorCount = 1;
	      }
      // return Qty.
      }
      
      if ((null != returnQuantityRequested) && (returnQuantityRequested.value.trim().length == 0)) {
          errorMessage += messagesData.requestQty + "\n";
          errorCount = 1;
      }
      else if ((null != returnQuantityRequested) && ((returnQuantityRequested.value.trim().length != 0) && (!(isPositiveInteger(returnQuantityRequested.value.trim())) || returnQuantityRequested.value * 1 == 0))) {
          errorMessage += messagesData.requestQty + "\n";
          errorCount = 1;
          returnQuantityRequested.value = "";
      }
     
      if ((null != returnQuantityRequested) && (parseInt(returnQuantityRequested.value.trim()) > parseInt(quantityShipped.value.trim()))) {
        alert(messagesData.originalShippedQtyError + "\n");
        returnQuantityRequested.value = "";       
        return false;
      }
      

      if ((null != authorizedReturnQty) && ((authorizedReturnQty.value.trim().length != 0) && (!(isPositiveInteger(authorizedReturnQty.value.trim())) || authorizedReturnQty.value * 1 == 0 || parseInt(authorizedReturnQty.value.trim()) > parseInt(returnQuantityRequested.value.trim())))) {
          errorMessage += messagesData.authorizedReturnQty + "\n";
          errorCount = 1;
          authorizedReturnQty.value = "";
      }
      else if ((null != authorizedReturnQty) && (parseInt(authorizedReturnQty.value.trim()) > parseInt(originalQty.value.trim()))) {
          alert(messagesData.errorauthorizedReturnQty + "\n");
          authorizedReturnQty.value = "";         
          return false;
      }
        
      if (actionDesired == "approve") {
      	if ((null != authorizedReturnQty) && (authorizedReturnQty.value.trim().length == 0)) {

          errorMessage += messagesData.authorizedReturnQty + "\n";
          errorCount = 1;
          authorizedReturnQty.value = "";
        }
      }
// replace with new validation     
      for( rrow in rowids ) 
      {  
    		  var newFeePriceValue = gridCellValue(newFeesGrid,rrow, "returnPrice");
    		  if( !isSignedFloat(newFeePriceValue,false) || newFeePriceValue * 1 == 0 ) 
    		  {
    			  errorMessage += messagesData.newFeesPrice+ "\n";
    		      errorCount = 1;
    		  }
    		 		 
    		  if( gridCellValue(newFeesGrid,rrow,"itemId") == '' ) {
	    		  if( gridCellValue(newFeesGrid,rrow,"feeDescription") == '' )
	    		  {	  
	    		   errorMessage += messagesData.description+"\n";
	      		   errorCount = 1;
	    		  }
      		  }
      }  
      
      if(reasonGrid.getRowsNum()>0)
      {
    	  var reasonRowsLen = reasonGrid.getRowsNum();
   	
//    	  for (var i=1; i<=reasonRowsLen;  i++)
//    	  {
//    		  var reasonValue = gridCellValue(reasonGrid,i, "returnReasonId");
    		  if(reasonRowsLen == 0)
    		  {
    			  errorMessage += messagesData.reason+  "\n";
    		      errorCount = 1;    		     
    			  
    		  }	  
//    	  }  
      } 
      
      if (validNewFeesDropDown == false) {
        errorMessage += messagesData.noduplicatenewFees + "\n";
        errorCount = 1;
      }
      
/*      if ((validNewFeesDropDown == false) && (validReasonDropDown == true)) {
        errorMessage += messagesData.noduplicatenewFees + "\n";
        errorCount = 1;
      }
      else if ((validNewFeesDropDown == true) && (validReasonDropDown == false)) {
        errorMessage += messagesData.noduplicatereason + "\n";
        errorCount = 1;
      }
      else if ((validNewFeesDropDown == false) && (validReasonDropDown == false)) {
        errorMessage += messagesData.noduplicatenewFees + "\n" + messagesData.noduplicatereason + "\n";
        errorCount = 1;
      }  */
     
    } catch(ex) {
      //alert(ex);
      errorCount++;
    }
  }
  
  var returnItemCount = 0;   
  if(null!=returnItemGrid && returnItemGrid.getRowsNum()!=0) {	   
      for(var i=0;i<returnItemGrid.getRowsNum();i++){
		  returnItemRowid = returnItemGrid.getRowId(i);
		  returnItemCount += returnItemGrid.cells(returnItemRowid,returnItemGrid.getColIndexById("quantity")).getValue()*1;
	  } 
  } 
  if( document.genericForm.correctItemShipped[1].checked && returnItemCount*1 != $v("returnQuantityRequested")*1) {
	  alert(messagesData.sumofactualitemshippedequalreturnquantityrequested);
	  return false;
  }

  if (errorCount > 0) {
    alert(errorMessage);
    return false;
  }

  return true;
}

function showOrHidereturnItemGrid() {
	if (document.genericForm.correctItemShipped[0].checked ) {
		$("returnItemLink").style.display="none";
		$("returnItemDiv").style.display="none";
	}
	else {
		$("returnItemLink").style.display="";
		$("returnItemDiv").style.display="";
	}
}

function showOrHideReplacementDetail() {
	if (document.genericForm.wantReplacement[1].checked ) {
		$("replacementDetail1").style.display="none";
		$("replacementDetail2").style.display="none";
		$("replacementDetail3").style.display="none";
		$("replacementDetail4").style.display="none";
		$("replacementDetail5").style.display="none";
	}
	else {
		$("replacementDetail1").style.display="";
		$("replacementDetail2").style.display="";
		$("replacementDetail3").style.display="";
		$("replacementDetail4").style.display="";
		$("replacementDetail5").style.display="";
	}
}

function setGrids() {
  // reason grids 
  $("reasonDiv").style["display"] = "";

  reasonGrid = new dhtmlXGridObject('reasonDiv');

  initGridWithConfigForPopUp(reasonGrid, reasonConfig, true, true);

  if (showReasonGrid) {
//    initGridWithConfig
    if (typeof(reasonJson) != 'undefined') {
      reasonGrid.parse(reasonJson, "json");
    }
  }
  
  // return item grid
  $("returnItemDiv").style["display"] = "";
  returnItemGrid = new dhtmlXGridObject('returnItemDiv');
  initGridWithConfigForPopUp(returnItemGrid, returnItemConfig, true, true);

//  if (showReturnItemGrid) {
    if (typeof(returnItemJson) != 'undefined') {
      returnItemGrid.parse(returnItemJson, "json");
    }
//  }  
  

  if (null != $("newFeesDiv")) {

    $("newFeesDiv").style["display"] = "";

    newFeesGrid = new dhtmlXGridObject('newFeesDiv');

    initGridWithConfigForPopUp(newFeesGrid, newFeeConfig, true, true,true);
    
    if (showNewFeesGrid) {
      if (typeof(newFeesJson) != 'undefined') {
        newFeesGrid.parse(newFeesJson, "json");

      }
    }
  }
  // alert("originalFeesTotalLines="+$("originalFeesTotalLines").value);
  if ((null!= $("originalFeesTotalLines")) &&( $("originalFeesTotalLines").value > 0)) {
    // original fee grid
    $("originalFeesDiv").style["display"] = "";

    originalFeesGrid = new dhtmlXGridObject('originalFeesDiv');

    initGridWithConfigForPopUp(originalFeesGrid, originalFeeConfig, true, true);

    if (typeof(originalFeeJson) != 'undefined') {
      originalFeesGrid.parse(originalFeeJson, "json");

    }
  }
  else {
    $("originalFeesDiv").style["display"] = "none";

    originalFeesGrid = new dhtmlXGridObject('originalFeesDiv');

  }

}
/*
function validateForm() {
//	try {
//	    if( editing ) {
//		    editing = false;
//			setTimeout("_simpleUpdate()",200);
//			return false;
//	    }
//	}catch(ex){}
	for( rowId in rowids ) {
		var errorMsg = '';
//		alert( cellValue(rowId,"Permission") );
//		if( cellValue(rowId,"Permission") != 'Y' ) continue;
		if( !isSignedFloat($v("price"+rowId),false ) )
			errorMsg += "\n"+messagesData.price;
//		if( cellValue(rowId,"itemId") == '' ) 
//			errorMsg += "\n"+messagesData.description;
		var val = cell(rowId,"itemIdDisplay").getValue();
		var a = '';
		<c:if test="${source eq 'addchargeheader'}">
		if( !val || val.trim().length == 0 || val.trim() == messagesData.pleaseselectorenter ) 
			errorMsg += "\n"+messagesData.description;
		else {
			for( i=0;i < itemIdDisplayArr.length; i++) 
				if( itemIdDisplayArr[i].value == val ) {
					a = itemIdDisplayArr[i].text;
					break;
				}
//			a = cell(rowId,"itemIdDisplay").combo.get(val);
//			a = cell(rowId,"itemIdDisplay").combo.get(val);
		}
		</c:if>
		<c:if test="${source ne 'addchargeheader'}">
		if( !val ) 
			errorMsg += "\n"+messagesData.description;
		else { 
			for( i=0;i < itemIdDisplayArr.length; i++) 
				if( itemIdDisplayArr[i].value == val ) {
					a = itemIdDisplayArr[i].text;
					break;
				}
//			a = $("itemIdDisplay"+rowId)[$("itemIdDisplay"+rowId).selectedIndex].text;
		}
		</c:if>
		if( errorMsg != '' ) {
		   	alert(messagesData.validvalues+errorMsg);
		   	haasGrid.selectRow(haasGrid.getRowIndex(rowId));
		   	return false;
		}
		if( a ) {
			cell( rowId,"itemId").setValue(val);
			cell( rowId,"description").setValue(a);		
		}
		else {
			if( val.length > 100 ) {
				alert( '<fmt:message key="errors.maxlength"/>'.replace(/[{]0[}]/g,'<fmt:message key="label.chargedescription"/>').replace(/[{]1[}]/g,'100') );
				return false;						
			}
			cell( rowId,"itemId").setValue('');
			cell( rowId,"description").setValue(val);		
//			gridCell(newFeesGrid,rowId,"changed").setValue("Y");
		}
//		alert( cellValue( rowId,"itemId") +":"+ cellValue( rowId,"description") );		
	
	}
	return true;
}
*/

function submitCustomerReturnRequest(actionType, approvalStatus) {

  actionDesired = "" + actionType + "";
  var isValid = (actionType=='delete') || (actionType=='deny') || validateUpdateInput();
  var now = new Date();
  var replacementRdBtn = document.getElementsByName("wantReplacement");
  var keepRdBtn = document.getElementsByName("keepMaterialRdBtn");
  var returnMaterial = $("returnMaterial");

  // Enable radio button for form submission for their 
  // values to be copied to the input bean.   
  for (var j = 0; j < keepRdBtn.length; j++) {
    if (keepRdBtn[j].disabled == true) {
      keepRdBtn[j].disabled = false;
    }

    if (keepRdBtn[j].checked == true && keepRdBtn[j].value == "Y") {
      returnMaterial.value = 'N';
    }
    else if (keepRdBtn[j].checked == true && keepRdBtn[j].value == "N") {
      returnMaterial.value = 'Y';
    }
  }

  for (var j = 0; j < replacementRdBtn.length; j++) {
    if (replacementRdBtn[j].disabled == true) {
      replacementRdBtn[j].disabled = false;
    }
  }

  $("startSearchTime").value = now.getTime();

  if (isValid) {
    if (getDenialComments()) {

      userAction = $("action");
      userAction.value = "" + actionType + "";

      if (actionType != 'update')  
      	  $("rmaStatus").value = "" + approvalStatus + "";

      showTransitPage();

      if ((reasonGrid.getRowsNum() > 0) || (submitReasonGridForDeletion == true)) {
          $("copyReasonBean").value = "Yes";
          
	      if(reasonGrid.getRowsNum() > 0)
	      {
	      	reasonGrid._srnd = true;
	      	submitReasonGridForDeletion = false;
	      	$("reasonsDeleteOnly").value = "";
	      }

	      if (submitReasonGridForDeletion == true) 
	      {
	      }
	      else
	      {
	          reasonGrid.parentFormOnSubmit();
	      }
      }
      else if (reasonGrid.getRowsNum() == 0) {
          $("copyReasonBean").value = "No";
      }
  	  
//  	  if (returnItemGrid != null)
	  	returnItemGrid.parentFormOnSubmit();

      if ((newFeesGrid.getRowsNum() > 0) || (submitNewFeesGridForDeletion == true)) {
        $("copyNewFeesBean").value = "Yes";

        if(newFeesGrid.getRowsNum() > 0)
        {
        	newFeesGrid._srnd = true;
        	submitNewFeesGridForDeletion = false;
        	$("newFeesDeleteOnly").value = "";
        }
        
        if (submitNewFeesGridForDeletion == true)
        {
          //  
        }
        else {
          newFeesGrid.parentFormOnSubmit();
        }
      }
      else if (newFeesGrid.getRowsNum() == 0) {
        $("copyNewFeesBean").value = "No";
      }

      document.genericForm.submit();
    }
  }

}

function addReasonLine() {
  var ind = reasonGrid.getRowsNum();
//  var rowid = ind + 1;
  var rowid = (new Date()).valueOf();
  var count = 0;
  var limit = returnReasonId.length; // subtract 1 because Please select is not actual value
  if(limit == ind * 1)
  {
	 return false;
  }	  
/*
  var reasonAddLimitCounter = $("reasonAddLimit");
  var addReason = false;
  if (reasonAddLimitCounter.value > 0) {

    if (((parseInt(rowCounter) <= parseInt(limit)) && (parseInt(rowCounter) <= parseInt(reasonAddLimitCounter.value))) && (parseInt(limit) != parseInt(reasonAddLimitCounter.value))) {
      //alert("inside if");
     
      addReason = true;
    }
    else if ((parseInt(rowCounter) <= parseInt(limit)) && (parseInt(rowid) <= parseInt(limit))) {
      //alert("Inisde sub Else");
      
      addReason = true;
    }

  }
  else if (parseInt(rowCounter) <= parseInt(limit)) {
    //alert("Inisde Else");
    addReason = true;
  }
*/
//  if(addReason)
  {
	    reasonGrid._srnd = true;
	    reasonGrid.addRow(rowid, "", ind);
	    rowCounter++;
	    if (reasonRowRemovalCounter > 0) {
	      reasonRowRemovalCounter--;
	    }
	    reasonGrid.cells(rowid, count++).setValue('Y');
    	reasonGrid.cells(rowid, count++).setValue(returnReasonId[0].value);
  }
  
  if (rowid > 0) {

    submitReasonGridForDeletion = false;
    $("reasonsDeleteOnly").value = "";
  }

  if (reasonGrid.getRowsNum() > 0) {
    $("reasonRemoveLine").style["display"] = "";
  }  

}


function removeReasonLine() {
//  var ind = reasonGrid.getRowsNum(); 
//  var rowid = ind;
  var count = 0;
  var limit = returnReasonId.length;
  var reasonLimitCounter = $("reasonAddLimit");
  var tempCounter = 0;
  
  if(selectedReasonRowId == null){
		alert(messagesData.norowselected);return false;
  } 

  if (reasonLimitCounter.value > 0) {
    if ((parseInt(reasonRowRemovalCounter) <= parseInt(reasonLimitCounter.value)) && (parseInt(reasonGrid.getRowsNum()) > 0)) {
      //alert("sub if");
      reasonRowRemovalCounter++;
      if (rowCounter > 0) {
        rowCounter--;
      }
      reasonGrid._srnd = false;
      reasonGrid.deleteRow(selectedReasonRowId);

      $("reasonsDeleteOnly").value = "delete";
      submitReasonGridForDeletion = true;

    }
    else if (parseInt(reasonGrid.getRowsNum()) > 0) {      

      reasonRowRemovalCounter++;
      if (rowCounter > 0) {
        rowCounter--;
      }
      reasonGrid._srnd = false;
      reasonGrid.deleteRow(selectedReasonRowId);

      $("reasonsDeleteOnly").value = "delete";
      submitReasonGridForDeletion = true;

    }   
  }

  else if (parseInt(reasonGrid.getRowsNum()) > 0) {
    reasonRowRemovalCounter++;
    if (rowCounter > 0) {
      rowCounter--;
    }

    reasonGrid._srnd = false;
    reasonGrid.deleteRow(selectedReasonRowId);
    $("reasonsDeleteOnly").value = "delete";
    submitReasonGridForDeletion = true;

  }

  if (parseInt(reasonGrid.getRowsNum()) >= 0) 
    $("reasonAddLine").style["display"] = "";
    
  if(reasonGrid.getRowsNum() == 0)
    $('reasonRemoveLine').style.display="none";

}

/*
function addNewFeesLine() {
  var ind = newFeesGrid.getRowsNum();
  var rowid = ind + 1;
  var count = 0;
  var newFeeslimit = itemId.length;
  var newFeesAddLimitCounter = $("newFeesAddLimit");

  var add = false;
  if ((null != newFeesAddLimitCounter) && (newFeesAddLimitCounter.value > 0)) {

    if (((parseInt(newFeesGridRowCounter) <= parseInt(newFeeslimit)) && (parseInt(newFeesGridRowCounter) <= parseInt(newFeesGridRowCounter.value))) && (parseInt(newFeeslimit) != parseInt(newFeesGridRowCounter.value))) {
    	add = true;
    }

    else if ((parseInt(newFeesGridRowCounter) <= parseInt(newFeeslimit)) && (parseInt(rowid) <= parseInt(newFeeslimit))) {
    	add = true;
    }

  }
  else if (parseInt(newFeesGridRowCounter) <= parseInt(newFeeslimit)) {
	    add = true;
  }
  
  if( add ) {
	    newFeesGrid._srnd = true;
	      newFeesGrid.addRow(rowid, "", ind);
	      newFeesGridRowCounter++;
	      if (newFeesRowRemovalCounter > 0) {
	        newFeesRowRemovalCounter--;
	      }
	      newFeesGrid.cells(rowid, count++).setValue('Y');
	      newFeesGrid.cells(rowid, count++).setValue(itemId[0].value);
	      newFeesGrid.cells(rowid, count++).setValue("");
	      newFeesGrid.cells(rowid, count++).setValue(selectCurrency);
      


  }

  if (rowid > 0) {
    //$("reasonRemoveLine").style["display"] = "";
    submitNewFeesGridForDeletion = false;
    $("newFeesDeleteOnly").value = "";
  }
//	if(rowid ==newFeeslimit)
//	{
//		$("newFeeAddLine").style["display"] = "none";		
//	}

  if (newFeesGrid.getRowsNum() > 0) {
    $("newFeeRemoveLine").style["display"] = "";
  }
}
*/

/*
function removeNewFeesLine() {
  var ind = newFeesGrid.getRowsNum(); 
  var rowid = ind;
  var limit = itemId.length;
  var newFeesAddLimitCounter = $("newFeesAddLimit")
  var tempCounter = 0;

  if ((null != newFeesAddLimitCounter) && (newFeesAddLimitCounter.value > 0)) {

    if (parseInt(newFeesRowRemovalCounter) <= parseInt(newFeesAddLimitCounter.value)) {

      newFeesRowRemovalCounter++;
      if (newFeesGridRowCounter > 0) {
        newFeesGridRowCounter--;
      }
      newFeesGrid._srnd = false;
      newFeesGrid.deleteRow(rowid);

      $("newFeesDeleteOnly").value = "delete";
      submitNewFeesGridForDeletion = true;
    }
    else if (parseInt(newFeesGrid.getRowsNum()) > 0) {

      var newInd = newFeesGrid.getRowsNum();
      var newRowId = ind + 1;
      newFeesRowRemovalCounter++;
      if (newFeesGridRowCounter > 0) {
        newFeesGridRowCounter--;
      }
      newFeesGrid._srnd = false;
      newFeesGrid.deleteRow(rowid);

      $("newFeesDeleteOnly").value = "delete";
      submitNewFeesGridForDeletion = true;

    }    
  }

  else if (parseInt(newFeesGrid.getRowsNum()) > 0) {

    var newInd = newFeesGrid.getRowsNum();
    var newRowId = ind + 1;
    newFeesRowRemovalCounter++;
    if (newFeesGridRowCounter > 0) {
      newFeesGridRowCounter--;
    }
    newFeesGrid._srnd = false;
    newFeesGrid.deleteRow(rowid);

    $("newFeesDeleteOnly").value = "delete";
    submitNewFeesGridForDeletion = true;

  }

  if (newFeesGrid.getRowsNum() >= 0) {
    $("newFeeAddLine").style["display"] = "";
  }
}
*/

function showTransitPage() {
  resizeFramecount = 1;
  $("resultGridDiv").style["display"] = "none";
  $("transitPage").style["display"] = "";
}

function canAddRemoveReasonLines() {
  // hide add a line 
  var reasonAddLine = $("reasonAddLine");

  var reasonRemoveLine = $("reasonRemoveLine");

  // For Reson Div spans
  if ((null != reasonAddLine) && (parseInt(returnReasonId.length) == parseInt($("reasonAddLimit").value))) {
    reasonAddLine.style["display"] = "none";
  }

  // show remove a line
  if (((parseInt($("reasonAddLimit").value)) > 0) && (null != reasonRemoveLine)) {
    reasonRemoveLine.style["display"] = "";
  }

}
/*
function canAddRemoveFeeLines() {
  // hide add a line 
  var newFeeAddLine = $("newFeeAddLine");
  var newFeeRemoveLine = $("newFeeRemoveLine");

  var newFeesAddLimit = $("newFeesAddLimit");

  // For new Fees Div spans
  if ((null != newFeeAddLine) && (parseInt(itemId.length) == parseInt(newFeesAddLimit.value))) {
    newFeeAddLine.style["display"] = "none";
  }

  // show remove a line
  if ((null != newFeeRemoveLine) && ((parseInt(newFeesAddLimit.value)) > 0)) {
    newFeeRemoveLine.style["display"] = "";
  }

}
*/
function checkNewFeePrice(rowId, cellInd) {
  var newFeePriceValue = gridCellValue(newFeesGrid, rowId, cellInd); 
  var errorMessage = messagesData.validvalues + "\n";
  var errorCount = 0; 
  try {
    if ((newFeePriceValue.length != 0) && (!(isSignedFloat(newFeePriceValue)) || (newFeePriceValue * 1 == 0))) {
      errorMessage += messagesData.newFeesPrice + "\n";
      errorCount = 1;
      newFeesGrid.selectCell(newFeesGrid.getRowById(rowId), newFeesGrid.getColIndexById("returnPrice"), null, false, false, true);
      newFeesGrid.cellById(rowId, newFeesGrid.getColIndexById("returnPrice")).setValue("");
    }
  } catch(ex) {
    errorCount++;
  }
  if (errorCount > 0) {
    alert(errorMessage);
  }
}

// Checks for duplicate  values in the reason Drop Downs.
function checkUniqueReasons() {
  var reasonsTempArr1 = new Array();

  var duplicateCounter = 0;

  for (var i = 0; i < reasonGrid.getRowsNum(); i++) {
    reasonsTempArr1[reasonsTempArr1.length] = gridCellValue(reasonGrid, i + 1, "returnReasonId");
    
  }

  var r = new Array();

  o: for (var i = 0, n = reasonsTempArr1.length; i < n; i++) {
    for (var x = 0, y = r.length; x < y; x++) {
      if (r[x] == reasonsTempArr1[i]) {
        duplicateCounter++;
        continue o;
      }
    }
    r[r.length] = reasonsTempArr1[i];
  }

  if (duplicateCounter > 0) {
    return false;
  }
  else if (duplicateCounter == 0) {
    return true;
  }

}

// Checks for duplicate  values in the new fees  Drop Downs.
function checkUniqueNewFees() {
  return true;
  var newFeesTempArr1 = new Array();

  var duplicateCounter = 0;

  for (var i = 0; i < newFeesGrid.getRowsNum(); i++) {
    newFeesTempArr1[newFeesTempArr1.length] = gridCellValue(newFeesGrid, i + 1, "itemId");
  }

  var r = new Array();

  o: for (var i = 0, n = newFeesTempArr1.length; i < n; i++) {
    for (var x = 0, y = r.length; x < y; x++) {
      if (r[x] == newFeesTempArr1[i]) {
        duplicateCounter++;
        continue o;
      }
    }
    r[r.length] = newFeesTempArr1[i];
  }

  if (duplicateCounter > 0) {
    return false;
  }
  else if (duplicateCounter == 0) {
    return true;
  }

}



function getDenialComments() {
  var denialComments;

  if (actionDesired == 'deny') {
    denialComments = prompt(messagesData.denialcomments, "");
    if (denialComments == null) {
      return false;
    }
    else {
      $("denialComments").value = denialComments;
      return true;
    }
  }
  else {
    return true;
  }

}

function clearReplacementNumber()
{
	 document.getElementById("replacementCartPN").value = "";
	// document.getElementById("supplier").value = "";
}


function lookupReplacePartNum()
{
	var loc1 = "/tcmIS/distribution/partsearchmain.do?uAction=new&customerId="+$v('customerId')+"&companyId="+$v('companyId')+"&shipToLocationId="+$v('facilityId')
				+"&opsEntityId="+$v('opsEntityId')+"&inventoryGroup="+$v('inventoryGroup');
	var loc2 = "&priceGroupId="+$v('priceGroupId')+"&fromCustomerReturn=Yes"+"&currencyId="+$v('currencyId')+"&curpath=Customer Return Request"+$v('rmaId');
	var loc3 = "&orderType=Customer Return Request"+$v('rmaId')+"&prNumber="+$v('prNumber')+"&valElementId=replacementCartPN"+"&valElement2Id=replacementPartDescription";
	var loc4 = "&valElement3Id=newUnitPrice";
	var loc = loc1 + loc2 +loc3+loc4;	
    children[children.length] = openWinGeneric(loc,"supplierssearch","1024","500","yes","50","50")
	
}

function printCustReturnReq()
{
  var loc = "/HaasReports/report/printcustomerreturnrequest.do?rmaId="+$v("rmaId");  
  children[children.length] = openWinGeneric(loc,"PrintCustomerReturnRequest"+$v("rmaId")+"","800","600","yes","50","50","20","20","yes");
}

function returnPartNumber(p)
{
   $("replacementCartPN").value = p.partNumber;
   $("replacementPartDescriptionSpan").innerHTML = p.description;
   $("replacementPartDescription").value = p.description;
	 $("replacementItemId").value = p.itemId;
	$("newUnitPrice").value = p.catalogPrice;
     
   $("specListConcat").value = p.specListConcat;
   $("detailConcat").value = p.detailConcat;
   $("specLibraryList").value = p.specLibraryList;
   $("cocConcat").value = p.cocConcat;
   $("coaConcat").value = p.coaConcat;
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