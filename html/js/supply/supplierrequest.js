function buildCountry(ele) {
	for (var i = 0; i < altCountry.length; i++) 
		setSelectOption(i, altCountry[i], altCountryId[i], ele);
}

function countryChanged(selectedCountry) {
  var country0 = document.getElementById("countryAbbrev");
  var state0 = document.getElementById("stateAbbrev");
  if( selectedCountry )
	  country0.value = selectedCountry;
  for (var i = state0.length; i > 0; i--) {
    state0[i] = null;
  }
  showStateOptions(country0.value);
  state0.selectedIndex = 0;
  setZipPlus(country0,'zipPlusSpan','zipPlus');
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
		  var stateRegion = stateArray[i];
		  if (stateRegion == 'PQ')
		  {
			  stateRegion = 'QC';
		  }	
		  if ((null != selectedState) && (selectedState == stateRegion )) {
		        selectedIndex = start;
		  }
	    setOption(start++, stateNameArray[i], stateRegion, 'stateAbbrev');
	  }
	  state0.selectedIndex = selectedIndex;
}

function remitCountryChanged(selectedCountry) {
  var country0 = document.getElementById("remitToCountryAbbrev");
  var state0 = document.getElementById("remitToStateAbbrev");
  if( selectedCountry )
	  country0.value = selectedCountry;
  for (var i = state0.length; i > 0; i--) {
    state0[i] = null;
  }

  remitShowStateOptions(country0.value);
  state0.selectedIndex = 0;

  setZipPlus(country0,'remitToZipPlusSpan','remitToZipPlus');

  }

function remitShowStateOptions(selectedCountry, selectedState) {
	  var stateArray = new Array();
	  stateArray = altState[selectedCountry];
	  var state0 = $('remitToStateAbbrev');
	  
	  if (stateArray == null || stateArray.length == 0) {
	    setOption(0, messagesData.pleaseselect, "", 'remitToStateAbbrev');
	    return;
	  }
	  var stateNameArray = new Array();
	  stateNameArray = altStateName[selectedCountry];
	  if (stateArray.length == 1) {
		    setOption(0, stateNameArray[0], stateArray[0], 'remitToStateAbbrev');
		    return;
	  }

	  setOption(0, messagesData.pleaseselect, "", 'remitToStateAbbrev');
	  var selectedIndex = 0;
	  var start = 1;
	  for (var i = 0; i < stateArray.length; i++) {
		  var stateRegion = stateArray[i];
		  if (stateRegion == 'PQ')
		  {
			  stateRegion = 'QC';
		  }	
		  if ((null != selectedState) && (selectedState == stateRegion )) {
		        selectedIndex = start;
		  }
	    setOption(start++, stateNameArray[i], stateRegion, 'remitToStateAbbrev');
	  }
	  state0.selectedIndex = selectedIndex;
}

function returnToList() {
  var status = document.getElementById("status");
  var searchText = document.getElementById("searchText");
  if(status.value != '' || searchText.value != '') {
    var submitSearch = document.getElementById("submitSearch");
    submitSearch.value = "submit";
  }
  submitOnlyOnce();
  document.genericForm.submit();
}

var	currentSupplierRequestId = null;
function linkViewSupplierRequestDetail() {
	viewSupplierRequestDetail(currentSupplierRequestId);
}

function viewSupplierRequestDetail(requestId) {
  /*var supplierRequestId = document.getElementById("supplierRequestId");
  supplierRequestId.value = parseInt(requestId);
  var action = document.getElementById("uAction");
  action.value = "viewrequestdetail";
  openWinGenericViewFile('/tcmIS/common/loadingpleasewait.jsp','_ViewSupplierRequestDetail','650','600','yes');
  document.genericForm.target='_ViewSupplierRequestDetail';
  var a = window.setTimeout("document.genericForm.submit();",500);*/

 if ( requestId != null &&  requestId != 0)
 {
  var loc = "/tcmIS/supply/supplierrequestupdate.do?uAction=viewrequestdetail&supplierRequestId="+requestId;
  try
  {
    parent.parent.openIFrame("newSupplierRequestDetail",loc,""+messagesData.supplierrequestdetail+"","","N");
}
  catch (ex)
  {
    openWinGeneric(loc,"newSupplierRequestDetail"+"","800","600","yes","50","50");
  }
 }
}

function saveSupplierRequest() {
	  var flag = validateForm();
	  if(flag) {
	    var action = document.getElementById("uAction");
	    paymentGrid.parentFormOnSubmit(); //prepare grid for data sending
	    igExcGrid.parentFormOnSubmit(); //prepare grid for data sending
	    
	    action.value = "submitdraft";
	    submitOnlyOnce();
	    document.genericForm.submit();
	  }
}

function save() {
	  var flag = validateForm();
	  if(flag) {
	    var action = document.getElementById("uAction");
	    paymentGrid.parentFormOnSubmit(); //prepare grid for data sending
	    igExcGrid.parentFormOnSubmit(); //prepare grid for data sending
	    
	    action.value = "save";
	    submitOnlyOnce();
	    document.genericForm.submit();
	  }
}

function deletedraft() {
	  var flag = true;
	  if(flag) {
			if( !confirm(messagesData.confirmdelete) ) return;
		  
	    var action = document.getElementById("uAction");
//	    paymentGrid.parentFormOnSubmit(); //prepare grid for data sending
//	    igExcGrid.parentFormOnSubmit(); //prepare grid for data sending
	    
	    action.value = "deletedraft";
	    submitOnlyOnce();
	    document.genericForm.submit();
	  }
	}

// reserved for future functionality of deleting from result page.
function deleteRequest(requestId) {
// do confirm.
// do calltoserver.
// do grid delete.	
}


function rejectSupplierRequest() {
  //var flag = validateForm();
  //if(flag) {
    var action = document.getElementById("uAction");
    action.value = "reject";
    var reasonForRejection = document.getElementById("reasonForRejection");
    var rejectComment = "";    
	 rejectComment = prompt(messagesData.commentesrejection ,"");
	 if (rejectComment == null) {	//user hit cancel
		return false;
	 }
	 paymentGrid.parentFormOnSubmit(); //prepare grid for data sending
	 igExcGrid.parentFormOnSubmit(); //prepare grid for data sending
	    
    /*var rejectComment = prompt("Enter Comments for Rejection:" ,"");*/
    reasonForRejection.value = rejectComment;
    submitOnlyOnce();
    document.genericForm.submit();
  //}
}

function approveSupplierRequest() {
  var flag = validateForm();
  if( !$('checknew').checked && $('sapVendorCode').value == '' )  {
  	alert(messagesData.sapcoderequired);
  	return;
  }
  if(flag) {
    var action = document.getElementById("uAction");
    action.value = "approve";
    paymentGrid.parentFormOnSubmit(); //prepare grid for data sending
    igExcGrid.parentFormOnSubmit(); //prepare grid for data sending
    submitOnlyOnce();
    document.genericForm.submit();
  }
}

function approvePaymentTermsSupplierRequest() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("uAction");
    action.value = "approvepaymentterms";
    paymentGrid.parentFormOnSubmit(); //prepare grid for data sending
    igExcGrid.parentFormOnSubmit(); //prepare grid for data sending
    submitOnlyOnce();
    document.genericForm.submit();
  }
}


function validateForm() {
  var errorMsg = "";
  if(document.getElementById("supplierName") == null ||document.getElementById("supplierName").value.trim().length == 0) {
    errorMsg += "\n"+messagesData.legalCompanyNameRequired;
  }
  if(document.getElementById("countryAbbrev") == null ||document.getElementById("countryAbbrev").value.trim().length == 0) {
    errorMsg += "\n"+messagesData.countryRequired;
  }
  if(document.getElementById("addressLine1") == null ||document.getElementById("addressLine1").value.trim().length == 0) {
    errorMsg += "\n"+messagesData.addressRequired;
  }
  if(document.getElementById("city") == null ||document.getElementById("city").value.trim().length == 0) {
    errorMsg += "\n"+messagesData.cityRequired;
  }
  var c1 = $v("countryAbbrev");
  if( c1 == 'USA' || c1 == 'CAN' )
  if(document.getElementById("stateAbbrev") == null ||document.getElementById("stateAbbrev").value.trim().length == 0) {
    errorMsg += "\n"+messagesData.stateProvinceRequired;
  }
  var z1 = $v("zip");
  var z2 = $v("zipPlus");
  if( !zipCheck(c1,z1,z2) ) {
    errorMsg += "\n"+messagesData.zipRequired;
  }
  if( checkRemitTo ) {
  if(document.getElementById("remitToCountryAbbrev") == null ||document.getElementById("remitToCountryAbbrev").value.trim().length == 0) {
    errorMsg += "\n"+messagesData.remitto + messagesData.countryRequired;
  }
  if(document.getElementById("remitToAddressLine1") == null ||document.getElementById("remitToAddressLine1").value.trim().length == 0) {
    errorMsg += "\n"+messagesData.remitto+messagesData.addressRequired;
  }
  if(document.getElementById("remitToCity") == null ||document.getElementById("remitToCity").value.trim().length == 0) {
    errorMsg += "\n"+messagesData.remitto+messagesData.cityRequired;
  }
  c1 = $v("remitToCountryAbbrev");
  if( c1 == 'USA' || c1 == 'CAN' )

  if(document.getElementById("remitToStateAbbrev") == null ||document.getElementById("remitToStateAbbrev").value.trim().length == 0) {
    errorMsg += "\n"+messagesData.remitto+messagesData.stateProvinceRequired;
  }

  z1 = $v("remitToZip");
  z2 = $v("remitToZipPlus");
  
  if( !zipCheck(c1,z1,z2) ) {
    errorMsg += "\n"+messagesData.remitto+messagesData.zipRequired;
  }
  }
  if(document.getElementById("supplierMainPhone") == null ||document.getElementById("supplierMainPhone").value.trim().length == 0) {
    errorMsg += "\n"+messagesData.supplierMainPhoneRequired;
  }
//  if(document.getElementById("defaultPaymentTerms") == null ||document.getElementById("defaultPaymentTerms").value.trim().length == 0) {
//    errorMsg += "\n"+messagesData.paymentTermsRequired;
//  }
  if( paymentGrid.getRowsNum() == 0 )
	  errorMsg += "\n"+messagesData.paymentTermsRequired;

  var errorOps = "";
  var opsInvgrpArray = new Array();
  if( igExcGrid.getRowsNum() > 0 ) {
  	for( rowidx in igExcrowids ) {
  		var flag = false;
  		if( paymentGrid.getRowsNum() > 0 ) {
	  		for( rowidy in paymentrowids ) {
//	 alert("paymentGrid:!!"+ paymentGrid.cells(rowidy,paymentGrid.getColIndexById("opsEntityId")).getValue());
	  			if( gridCellValue(paymentGrid,rowidy,"opsEntityId") == gridCellValue(igExcGrid,rowidx,"excOpsEntityId")){
	  				flag = true;
	  				break;
	  			}
	  		}
	  		if( flag ) {
	  			continue;
	  		} else {
	  			for (var i=0; i < excOpsEntityIdArr.length;i++) {
					if (gridCellValue(igExcGrid,rowidx,"excOpsEntityId") == excOpsEntityIdArr[i]["value"]) {
						optText =  excOpsEntityIdArr[i]["text"];
					}
				}
	  			errorOps += "\n"+messagesData.entervalidentity.replace(/[{]0[}]/g,optText);
	  		}
	  	} else {
	  		for (var i=0; i < excOpsEntityIdArr.length;i++) {
					if (gridCellValue(igExcGrid,rowidx,"excOpsEntityId") == excOpsEntityIdArr[i]["value"]) {
						optText =  excOpsEntityIdArr[i]["text"];
					}
			}
	  		errorOps += "\n"+messagesData.entervalidentity.replace(/[{]0[}]/g,optText);
	  	}
	  	
	  	var exceptionFlag = false;
	  	var opsInvgrpString = gridCellValue(igExcGrid,rowidx,"excOpsEntityId") + "-" + gridCellValue(igExcGrid,rowidx,"inventoryGroup");
	  	if(opsInvgrpArray.length == 0)
	  		opsInvgrpArray[0] = opsInvgrpString;
	  	else {
	  		for ( i=0; i < opsInvgrpArray.length; i++ ) {
	  			if(opsInvgrpString == opsInvgrpArray[i]) {
	  				errorOps += "\n"+messagesData.nosameinvgrpforpaymenttermsexceptions;
	  				exceptionFlag = true;
	  				break;
	  			}
	  		}
	  		if(exceptionFlag == false)
	  			opsInvgrpArray[opsInvgrpArray.length++] = opsInvgrpString;
	  	}
 	
    }
  }

  flag = false;
  if( paymentGrid.getRowsNum() > 1 ) {
	  for( rowidx in paymentrowids ) 
		  	if( !flag )
	  		for( rowidy in paymentrowids ) 
	  			if( rowidx != rowidy ) 
	  				if( gridCellValue(paymentGrid,rowidy,"opsEntityId") == gridCellValue(paymentGrid,rowidx,"opsEntityId")) {
	  					errorMsg += "\n"+messagesData.operatingentity + "["+messagesData.dupentity+"]";
	  					flag = true;
	  			  		break;
	  			  	}
  }

  if(document.getElementById("typeOfPurchase") == null ||document.getElementById("typeOfPurchase").value.trim().length == 0) {
    errorMsg += "\n"+messagesData.typeOfPurchaseRequired;
  }
  if(document.getElementById("qualificationLevel") == null ||document.getElementById("qualificationLevel").value.trim().length == 0) {
    errorMsg += "\n"+messagesData.qualificationLevelRequired;
  }
  var country = document.getElementById("countryAbbrev");
    var federalTaxId = document.getElementById("federalTaxId");
    var vatRegistrationNumber = document.getElementById("vatRegistrationNumber");
    var fedvalue = $v("federalTaxId");
    // if exist and not equal to predefined value.
    if ($("ssnFlag").checked)
    	$("ssnFlag").value = "Y";
    else
    	$("ssnFlag").value = "";
    	
    if( $('ssnFlag').checked && !fedvalue.match(/\d{3}-\d{2}-\d{4}/) )
    {
    	alert( messagesData.federaltaxid +":\n"+ messagesData.format );
    	$("federalTaxId").focus();
    	return false;
    }
    if( $v("federalTaxId").length !=0 && sapTaxCode1[$v("countryAbbrev")] != undefined && $v("federalTaxId").length > sapTaxCode1[$v("countryAbbrev")] )
    {
    	alert( messagesData.federaltaxid +":\n"+messagesData.maximumallowedtext.replace(/[{]0[}]/,sapTaxCode1[$v("countryAbbrev")]) );
    	$("federalTaxId").focus();
    	return false;
    }
    if( $v("vatRegistrationNumber").length !=0 && sapTaxCode1[$v("countryAbbrev")] != undefined && $v("vatRegistrationNumber").length > sapTaxCode2[$v("countryAbbrev")] )
    {
    	alert( messagesData.vatregistration +":\n"+messagesData.maximumallowedtext.replace(/[{]0[}]/,sapTaxCode2[$v("countryAbbrev")]) );
    	$("vatRegistrationNumber").focus();
    	return false;
    }
    if( (federalTaxId == null || federalTaxId.value.trim().length == 0 || federalTaxId.value.trim() == 'NA') &&
	    (vatRegistrationNumber == null || vatRegistrationNumber.value.trim().length == 0) ) 
	{
    errorMsg += "\n"+messagesData.federalTaxIdRequired;
    }

//  if( (suppReqType == 'New' || suppReqType == 'Activate' || suppReqType == 'Modify') && !$v("opsEntityId")) {
//    errorMsg += "\n"+messagesData.operatingentity;
//  }

  if( errorMsg != '' || errorOps != '' ) {
   	alert(messagesData.validvalues+errorMsg+errorOps);
   	return false;
  }
  return true;
}

////////// search area scripts.
function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = true;
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  
  if(isValidSearchForm) {
   $('uAction').value = 'search';
   document.genericForm.target='resultFrame';
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

///////////////

function createXSL(){
  var flag = true;//validateForm();
  if(flag) {
	$('uAction').value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_SupplierRequestExcel','650','600','yes');
    document.genericForm.target='_SupplierRequestExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}


function subnew() {
	/*$('uAction').value = "new";
  	document.genericForm.target='newviewrequestdetail';
  	
  	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp'
                 ,"newviewrequestdetail","800","600","yes","25","25","no");
  	setTimeout("document.genericForm.submit()",300);*/

  var loc = "/tcmIS/supply/supplierrequestupdate.do?uAction=new";
  try
  {
    parent.parent.openIFrame("newSupplierRequestNew",loc,""+messagesData.newsupplierrequest+"","","N");
  }
  catch (ex)
  {
    openWinGeneric(loc,"newSupplierRequestDetail"+"","800","600","yes","50","50");
  }

  	return false;
}

function getVendorCode()
{
   loc = "vendorcodesearchmain.do"
   openWinGeneric(loc,"_vendorcodesearch","800","600","yes","50","50","no")
}

function clearSapVendorCode() {
	if( $('checknew').checked ) {
		$('sapSpan').innerHTML='      ';
		$('sapVendorCode').value='';
	}
}

function vendorCodeChanged(code,name) {
	$('sapSpan').innerHTML=code;
	$('sapVendorCode').value=code;
	$('checknew').checked = false;
}
