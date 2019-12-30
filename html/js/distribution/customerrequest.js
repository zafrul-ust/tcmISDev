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

//  if( country0.value == 'USA' ) 
//  	$('zipPlusSpan').style.display="";
//  else
//  	$('zipPlusSpan').style.display="none";

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

var	currentCustomerRequestId = null;
function linkViewCustomerRequestDetail() {
	viewCustomerRequestDetail(currentCustomerRequestId);
}

function viewCustomerRequestDetail(requestId) {

 if ( requestId != null &&  requestId != 0)
 {
  var loc = "/tcmIS/distribution/customerrequestupdate.do?uAction=viewrequestdetail&customerRequestId="+requestId;
  try
  {
    parent.parent.openIFrame("newCustomerRequestDetail"+requestId,loc,""+messagesData.customerrequestdetail+"","","N");
}
  catch (ex)
  {
    openWinGeneric(loc,"newCustomerRequestDetail"+requestId,"800","600","yes","50","50");
  }
 }
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
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_CustomerRequestExcel','650','600','yes');
    document.genericForm.target='_CustomerRequestExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}


function subnew() {
	/*$('uAction').value = "new";
  	document.genericForm.target='newviewrequestdetail';
  	
  	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp'
                 ,"newviewrequestdetail","800","600","yes","25","25","no");
  	setTimeout("document.genericForm.submit()",300);*/

  var loc = "/tcmIS/distribution/customerrequestupdate.do?uAction=new&customerRequestType=New";
  try
  {
    parent.parent.openIFrame("newCustomerRequestDetail",loc,""+messagesData.newcustomerrequest+"","","N");
  }
  catch (ex)
  {                     
    openWinGeneric(loc,"newCustomerRequestDetail"+"","800","600","yes","50","50");
  }

  	return false;
}

function getVendorCode()
{	
   loc = "/tcmIS/supply/vendorcodesearchmain.do?searchType=sapCustomerCode"
   openWinGeneric(loc,"_customercodesearch","800","600","yes","50","50","no")
}

function clearSapCustomerCode() {
	if( $('checknew').checked ) {
		$('sapSpan').innerHTML='      ';
		$('sapCustomerCode').value='';
	}
}

function vendorCodeChanged(code,name) {
	
//	$('sapSpan').innerHTML=code;
	$('sapCustomerCode').value=code;
	$('checknew').checked = false;
}

function getCompanyId()
{
   loc = "/tcmIS/distribution/companysearchmain.do"
   openWinGeneric(loc,"_companysearch","600","500","yes","50","50","no")
}

function toIdValue(s) {
	s = s.replace(/[_-]/g,' ');
	s = s.replace(/[^0-9A-Za-z ]/g,'');
	s = s.replace(/[\s]+/g,'_');
	s = s.toUpperCase();
	return s;
}

function clearCompanyId() {
	$('companyId').value='';
	$('companyShortName').value='';
	$('companyName').value='';						
	if( $('newCompanyId').checked ) {
		$('companyIdMsgSpan').style.display="";
		$('companyId').className= "inputBox";
//		$('companyIdDisplay').value='';
//		$('companyId').className='inputBox';
//		$('companyId').readonly = false;
				
		$('companyShortName').className='inputBox';
		$('companyName').className='inputBox';
		$('companyId').readOnly = false;
		$('cLookupButton').style.display="none";
	}
	else {
		$('companyIdMsgSpan').style.display="none";
		$('companyId').className= "invGreyInputText";
		$('companyId').readOnly = "readonly";
		$('cLookupButton').style.display="";
	}
}

function companyChanged(code,shortname,name) {
	$('newCompanyId').checked = false;
	clearCompanyId();
	$('companyId').value=code;
//	$('companyIdDisplay').value=code;
	$('companyShortName').value=shortname;
	$('companyName').value=name;						


}

function getSalesRep() {
	  loc = "searchpersonnelmain.do?fixedCompanyId=Y";
	  openWinGeneric(loc,"changepersonnel","600","450","yes","50","50","20","20","no");
}

function cleargetSalesRep() {
	$('fieldSalesRepId').value = '';
	$('fieldSalesRepName').value = '';
	return false;
}

function personnelChanged(pid,name,callbackparam) {
    if( !callbackparam ) {
    	$('fieldSalesRepId').value = pid;
    	$('fieldSalesRepName').value = name;
    }
    else {
    	try {
    	var srr = callbackparam.split('_');
    	if( srr[0] == 'agent' ) {
    		gridCell(shiptoGrid,srr[1],"salesAgentId").setValue(pid);
    		$('salesAgentName_'+srr[1]).value = name;
    	}
    	if( srr[0] == 'field' ) {
    		gridCell(shiptoGrid,srr[1],"fieldSalesRepId").setValue(pid);
    		$('fieldSalesRepName_'+srr[1]).value = name;
    	}
    	} catch(ex) {}
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
	  if( haasGrid.getRowsNum() == 0 ) { 
		    errorMsg += "\n"+messagesData.contact+" : "+messagesData.atleastone;
	  }
	  if($v("customerName") == '') {
		    errorMsg += "\n"+messagesData.customername;
		  }
	  if( $v("companyId") == '' ) {// && !$("newCompanyId").checked ) {
		    errorMsg += "\n"+messagesData.companyid;
		  }
// Larry Note:	  
// field sales rep not required any more	  
//	  if( $v("fieldSalesRepId") == '' ) {// && !$("newCompanyId").checked ) {
//		    errorMsg += "\n"+messagesData.salesrep;
//		  }
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
	  if(  $("stateAbbrev").options.length > 1 && ( document.getElementById("stateAbbrev") == null || document.getElementById("stateAbbrev").value.trim().length == 0 )) {
		  errorMsg += "\n"+messagesData.stateProvinceRequired;
	  }

	  var z2 = $v("zipPlus");
	  if($v("city").toUpperCase().indexOf('HONG') == -1 && $v("city").toUpperCase().indexOf('KONG') == -1)
		   
	  {
		  if($v("city").toUpperCase() != 'MACAU')
		  if( !zipCheck(c1,z1,z2) ) {
		    errorMsg += "\n"+messagesData.zipRequired;
		  }
	  }
	  if( checkShipTo ) {
	  if(document.getElementById("shipToCountryAbbrev") == null ||document.getElementById("shipToCountryAbbrev").value.trim().length == 0) {
	    errorMsg += "\n"+messagesData.shipto + messagesData.countryRequired;
	  }
	  if(document.getElementById("shipToAddressLine1") == null ||document.getElementById("shipToAddressLine1").value.trim().length == 0) {
	    errorMsg += "\n"+messagesData.shipto+messagesData.addressRequired;
	  }
	  if(document.getElementById("shipToCity") == null ||document.getElementById("shipToCity").value.trim().length == 0) {
	    errorMsg += "\n"+messagesData.shipto+messagesData.cityRequired;
	  }
	  if(  $("shipToStateAbbrev").options.length > 1 && ( document.getElementById("shipToStateAbbrev") == null ||document.getElementById("shipToStateAbbrev").value.trim().length == 0 )) {
		  errorMsg += "\n"+messagesData.stateProvinceRequired;
	  }

	  c1 = $v("shipToCountryAbbrev");
	  z1 = $v("shipToZip");
	  z2 = $v("shipToZipPlus");
	  
	  if($v("shipToCity").toUpperCase().indexOf('HONG') == -1 && $v("shipToCity").toUpperCase().indexOf('KONG') == -1) 
		  {
		      if($v("shipToCity").toUpperCase() != 'MACAU')
			  if( !zipCheck(c1,z1,z2) ) {
			    errorMsg += "\n"+messagesData.shipto+messagesData.zipRequired;
			   }
		  }
	  }
	//  if( isEmptyV('phone')) {
//	    errorMsg += "\n"+messagesData.phoneRequired;
	//  }
	  if( isEmptyV("paymentTerms") ){
	    errorMsg += "\n"+messagesData.paymentTermsRequired;
	  }

	  if( !$v("taxRegistrationNumber") || $('taxRegistrationType').selectedIndex == 0)
		  errorMsg += "\n"+messagesData.taxregistrationnum;
		  
//	  if( ( custReqType == 'New' || custReqType == 'Activate') && !$v("opsEntityId")) {
//	    errorMsg += "\n"+messagesData.operatingentity;
//	  }
	  var billcountry = $v("countryAbbrev");
	  var billstate   = $v("stateAbbrev");
	  var countryText = $("countryAbbrev").options[$("countryAbbrev").selectedIndex].text;
	  var stateText = $("stateAbbrev").options[$("stateAbbrev").selectedIndex].text;
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
		  
//	  if( ( custReqType == 'New' || custReqType == 'Activate') && !$v("opsEntityId")) {
//	    errorMsg += "\n"+messagesData.operatingentity;
//	  }
	  if( $v('customerRequestStatus') != "Draft" ) {
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
	  }
	
	  if( "approvestage" == approvestage ) {
		  if( $v('creditLimit') == '' ) 
			    errorMsg += "\n"+messagesData.creditlimit;
		  if( $v('overdueLimit') == '' ) 
			    errorMsg += "\n"+messagesData.overduelimit;
//		  if( $v('priceGroupId') == '' ) 
//			    errorMsg += "\n"+messagesData.pricelist;
	  }

	// grid validating
	  if(	shiptoGrid.getRowsNum() == 0 ) {
		    errorMsg += "\n"+messagesData.shipto+": "+messagesData.atleastone;
	  }
//	  if(	shiptoGrid.getRowsNum() != 0 ) {
	  else {
		  var hasloc = true;
		  var hasadd = true;
		  var hascity = true;
		  var hasinv = true;
		  for( rowId in shiptorowids ) {
			  if( !gridCellValue(shiptoGrid,rowId,"locationDesc") )
				  hasloc = false;
			  if( !gridCellValue(shiptoGrid,rowId,"shipToAddressLine1") )
				  hasadd = false;
			  if( !gridCellValue(shiptoGrid,rowId,"shipToCity") ) 
				  hascity = false;
			  if( !gridCellValue(shiptoGrid,rowId,"defaultInventoryGroup") ) 
				  hasinv = false;
		  }
		  var shiptof = '';
		  var sep = '';
		  if( !hasloc ) {
//			  label.locationdesc			
			  shiptof += sep + messagesData.locationdesc ;
			  sep = ',';
		  }
		  if( !hasadd ) {
//			  label.addressline1			
			  shiptof += sep + messagesData.addressline1 ;
			  sep = ',';
		  }
		  if( !hascity ) {
//			  label.city			
			  shiptof += sep + messagesData.cityRequired ;
		  }
		  if( !hasinv ) {
//			  label.city			
			  shiptof += sep + messagesData.invRequired ;
		  }

		  if( shiptof )
//			  label.shipto
			  errorMsg += "\n"+messagesData.shipto +":" +shiptof;
	  }

	  if( haasGrid.getRowsNum() != 0 ) { 
		  var hasloc = true;
		  var hascity = true;
		  var hasadd = true;
		  for( rowId in rowids ) {
			  if( !gridCellValue(haasGrid,rowId,"lastName") ) {
				  hasloc = false;
			  }
			  if( !gridCellValue(haasGrid,rowId,"firstName") ) {
				  hascity = false;
			  }
			  if( !gridCellValue(haasGrid,rowId,"phone") )
				  hasadd = false;
		  }
		  var contactf = '';
		  var sep = '';
		  if( !hasloc ) {
//			  label.lastname			
			  contactf += sep + messagesData.lastname ;
			  sep = ',';
		  }
		  if( !hascity ) {
//			  label.phone			
			  contactf += sep + messagesData.firstname ;
			  sep = ',';
		  }
		  if( !hasadd ) {
//			  label.phone			
			  contactf += sep + messagesData.phoneRequired ;
			  sep = ',';
		  }

		  if( contactf ) {
//			  label.contact
			  errorMsg += "\n"+messagesData.contact +":" + contactf;
		  }

	  }

	  if( carrierGrid.getRowsNum() != 0 ) {
		  var hasloc = true;
		  var hascity = true;
		  for( rowId in carrierrowids ) {
			  if( !gridCellValue(carrierGrid,rowId,"carrierName") ) {
				  hasloc = false;
			  }
			  if( !gridCellValue(carrierGrid,rowId,"carrierAccount") ) {
				  hascity = false;
			  }
		  }
		  var carrierf = '';
		  var sep = '';
		  if( !hasloc ) {
			  carrierf += sep + messagesData.carriername ;
			  sep = ',';
		  }
		  if( !hascity ) {
			  carrierf += sep + messagesData.carrieraccount ;
			  sep = ',';
		  }
		  if( carrierf )
//			  label.shipto
			  errorMsg += "\n"+messagesData.carrier +":" +carrierf;

	  }
	  if( taxGrid.getRowsNum() != 0 ) {
		  var hasloc = true;
		  var hascity = true;
		  for( rowId in taxrowids ) {
			  if( !gridCellValue(taxGrid,rowId,"taxExemptionCertificate") ) {
				  hasloc = false;
			  }
//			  if( !gridCellValue(carrierGrid,rowId,"carrierAccount") ) {
//				  hascity = false;
//			  }
		  }
		  var taxf = '';
		  var sep = '';
		  if( !hasloc ) {
			  taxf += sep + messagesData.taxexemptioncode ;
			  sep = ',';
		  }
//		  if( !hascity ) {
//			  taxf += sep + messagesData.carrieraccount ;
//			  sep = ',';
//		  }
		  if( taxf )
//			  label.shipto
			  errorMsg += "\n"+messagesData.taxexemptioncode;// +":" +taxf;

	  }
	  if( !$v('taxRegistrationNumber2') ) {
		  $('taxRegistrationType2').selectedIndex = 0;
	  }
	  if( !$v('taxRegistrationNumber3') ) {
		  $('taxRegistrationType3').selectedIndex = 0;
	  }
	  if( !$v('taxRegistrationNumber4') ) {
		  $('taxRegistrationType4').selectedIndex = 0;
	  }
	  
	if($("shelfLifeRequired") != null && !isFloat($v("shelfLifeRequired"),true)) {
		errorMsg += "\n"+messagesData.defaultshelflife;
	}
	
	if($("creditLimit") != null && !isFloat($v("creditLimit"),true)) {
		errorMsg += "\n"+messagesData.creditlimit;
	}
	
	if($("overdueLimit") != null && !isFloat($v("overdueLimit"),true)) {
		errorMsg += "\n"+messagesData.graceperiod;
	}
	
	var autoEmailAddresses = $v('autoEmailAddress');	
	var listOfEmails = autoEmailAddresses.split(',');
	for (var i = 0; i < listOfEmails.length;++i)
	{	
		if( !isEmail(listOfEmails[i].trim(),true)) {
			errorMsg += "\n"+ messagesData.einvoiceemailaddressinvalid;
			$("autoEmailAddress").focus();
			break;
		}
	}

	  if( errorMsg != '' ) {
		  alert(messagesData.validvalues+errorMsg);
		  return false;
	  }
	  $('newCompanyId').disabled = false;
	  $('companyId').value = toIdValue($v('companyId'));
	  return true;
}



