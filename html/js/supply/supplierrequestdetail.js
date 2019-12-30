function cleanRemit() {

 var below = new Array( "remitToAddressLine1","remitToCity","remitToAddressLine2","remitToAddressLine3","remitToZip","remitToZipPlus");
 for(i=0;i<below.length;i++)
 	$(below[i]).value = '';
 	
 $("remitToCountryAbbrev").selectedIndex = 0 ;
 
 var cv = $("remitToCountryAbbrev").value ;
 
 remitCountryChanged(cv);
 
}

function copyFromAbove() {

 if(! $('sameAsAbove').checked ) return;// cleanRemit();
 
 var above = new Array( "addressLine1","city","addressLine2","addressLine3","zip","zipPlus");
 var below = new Array( "remitToAddressLine1","remitToCity","remitToAddressLine2","remitToAddressLine3","remitToZip","remitToZipPlus");
 for(i=0;i<above.length;i++)
 	$(below[i]).value = $(above[i]).value;
 	
 var cv  =  $("countryAbbrev").value ;
 var cArr=  $("remitToCountryAbbrev").options;
 var len =  $("remitToCountryAbbrev").options.length;

 for(i=0;i<len; i++)
 	if( cArr[i].value == cv ) {
 		$("remitToCountryAbbrev").selectedIndex = i;
 		break;
 	}
 	
 remitShowStateOptions(cv);
 
 cv  =  $("stateAbbrev").value ;
 cArr=  $("remitToStateAbbrev").options;
 len =  $("remitToStateAbbrev").options.length;

 for(i=0;i<len; i++)
 	if( cArr[i].value == cv ) {
 		$("remitToStateAbbrev").selectedIndex = i;
 		break;
 	}
 setZipPlus("remitToCountryAbbrev",'remitToZipPlusSpan');

}
function changePaymentTerms(){
    $("uAction").value = "changePaymentTerms";
    submitOnlyOnce();
    paymentGrid.parentFormOnSubmit(); //prepare grid for data sending
	igExcGrid.parentFormOnSubmit(); //prepare grid for data sending
    document.genericForm.submit();
}
function approvePaymentTermsDirect(){
    $("uAction").value = "approvePaymentTermsDirect";
    submitOnlyOnce();
    paymentGrid.parentFormOnSubmit(); //prepare grid for data sending
	igExcGrid.parentFormOnSubmit(); //prepare grid for data sending
    document.genericForm.submit();
}
/*
function approvePaymentTermsActivation(){
    $("uAction").value = "approvePaymentTermsActivation";
    submitOnlyOnce();
    document.genericForm.submit();
}
function approveSupplierRequestActivation(){
    $("uAction").value = "approveSupplierRequestActivation";
    submitOnlyOnce();
    document.genericForm.submit();
}
*/

