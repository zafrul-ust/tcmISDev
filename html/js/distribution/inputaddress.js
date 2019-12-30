
windowCloseOnEsc = true;

function myOnload() {
	countryChanged();
	if ( showErrorMessage ) 
 		showErrorMessages();
}

function countryChanged() {
  var country0 = document.getElementById("countryAbbrev");
  var state0 = document.getElementById("stateAbbrev");
  var selectedCountry = country0.value;
  for (var i = state0.length; i > 0; i--) {
    state0[i] = null;
  }

  showStateOptions(selectedCountry);
  state0.selectedIndex = 0;

//  if( country0.value == 'USA' ) 
//  	$('zipPlusSpan').style.display="";
//  else
//  	$('zipPlusSpan').style.display="none";

}

function showStateOptions(selectedCountry) {
  var stateArray = new Array();
  stateArray = altState[selectedCountry];

  var stateNameArray = new Array();
  stateNameArray = altStateName[selectedCountry];
  stateEle = "stateAbbrev";
  
  if (stateArray == null || stateArray.length == 0) {
    setOption(0,messagesData.pleaseselect,"", stateEle);
    return;
  }
  if (stateArray.length == 1 || !stateArray[0] ) {
    setOption(0,stateNameArray[0],stateArray[0], stateEle);
    return;
  }
  setOption(0,messagesData.pleaseselect,"", stateEle);
  start = 1
  for (var i=0; i < stateArray.length; i++) {
	  setOption(start++,stateNameArray[i],stateArray[i], stateEle);
  }
}

function returnAddress()
{ 	
  if(validateForm()){
  	try {
  	  if( window.opener.fillAddress ) {
    	var a = new addressInfo();
  		window.opener.fillAddress(a);
  	  }
    } catch(exx) {}
 
    window.close();
  }
}

function addressInfo() {
  this.ShipToOrBillTo=$v("ShipToOrBillTo");
  this.addressLine1=$v("addressLine1");
  this.addressLine2=$v("addressLine2");
  this.addressLine3=$v("addressLine3");
  this.addressLine4=$v("addressLine4");
  this.addressLine5=$v("addressLine5");
  this.countryAbbrev=$v("countryAbbrev");
  this.stateAbbrev=$v("stateAbbrev");
  this.city=$v("city");
  this.zip=$v("zip");
//  if($("zipPlus") != null)
//  	this.zipPlus=$v("zipPlus");
}

function validateForm() {
	  var errorMsg = "";
	  var addval = "";
	  for(i=1;i<=5;i++)
		  addval += $v('addressLine'+i).toUpperCase();   	
	  var z1 = $v("zip");
	  
	  if( z1 && addval.indexOf(z1) == -1 ) {
		  alert(messagesData.fulladdressnozip);
		  return false;
	  }
	 
	  var c1 = $v("city").toUpperCase();
	  if( c1 && addval.indexOf(c1) == -1 ) {
		  alert(messagesData.fulladdressnocity);
		  return false;
	  }
	 
	  var c1 = $v("countryAbbrev");
	  var z2 = $v("zipPlus");
	  if( !zipCheck(c1,z1,z2) )
	    errorMsg += "\n"+messagesData.zipRequired;
	  
	    if(document.getElementById("addressLine1") == null ||document.getElementById("addressLine1").value.trim().length == 0) {
	    	errorMsg += "\n"+messagesData.addressRequired;
	  	}
	  	if(document.getElementById("countryAbbrev") == null ||document.getElementById("countryAbbrev").value.trim().length == 0) {
	    	errorMsg += "\n"+messagesData.countryRequired;
	  	}
	  	if( c1 == 'USA' || c1 == 'CAN' )
	  	if(  $("stateAbbrev").options.length > 1 && ( document.getElementById("stateAbbrev") == null ||document.getElementById("stateAbbrev").value.trim().length == 0 )) {
	    	errorMsg += "\n"+messagesData.stateProvinceRequired;
	  	}
	  	if(document.getElementById("city") == null ||document.getElementById("city").value.trim().length == 0) {
	    	errorMsg += "\n"+messagesData.cityRequired;
	  	}

	  if( errorMsg != '' ) {
		  alert(messagesData.validvalues+errorMsg);
		  return false;
	  }

	  return true;
}
