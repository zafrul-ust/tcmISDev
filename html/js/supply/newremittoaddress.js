windowCloseOnEsc = true;

function myOnload() {	
	if ( showErrorMessage ) 
 		showErrorMessages();
 	
 	if ( done ) {
 		doPostProcess();
 		window.close();
 	}
}

function doPostProcess() {
	if ($v("callerName") == "remitToManagement") {
		try {
			opener.submitSearchForm();
		} catch (ex) {}
	}
}

function remitCountryChanged() {
  var country0 = document.getElementById("remitToCountryAbbrev");
  var state0 = document.getElementById("remitToStateAbbrev");
  var selectedCountry = country0.value;
  for (var i = state0.length; i >= 0; i--) {
    state0[i] = null;
  }

  setZipPlus(country0,'remitToZipPlusSpan','remitToZipPlus');

  remitShowStateOptions(selectedCountry);
  state0.selectedIndex = 0;

}

function remitShowStateOptions(selectedCountry) {
  var stateArray = new Array();
  stateArray = altState[selectedCountry];

  var stateNameArray = new Array();
  stateNameArray = altStateName[selectedCountry];

  if(stateArray.length == 0) {
    setOption(0,messagesData.all,"", "remitToStateAbbrev")
  }

  for (var i=0; i < stateArray.length; i++) {
   var stateRegion = stateArray[i];
   if (stateRegion == 'PQ')
   {
    stateRegion = 'QC';
   }
   setOption(i,stateNameArray[i],stateRegion, "remitToStateAbbrev");
  }
}


function validateInput() {
  var errorMsg = "";
  
  if(document.getElementById("remitToCountryAbbrev") == null ||document.getElementById("remitToCountryAbbrev").value.trim().length == 0) {
    errorMsg += "\n"+messagesData.country;
  }
  if(document.getElementById("remitToAddressLine1") == null ||document.getElementById("remitToAddressLine1").value.trim().length == 0) {
    errorMsg += "\n"+messagesData.address;
  }
  if(document.getElementById("remitToCity") == null ||document.getElementById("remitToCity").value.trim().length == 0) {
    errorMsg += "\n"+messagesData.city;
  }
//  if(document.getElementById("remitToStateAbbrev") == null ||document.getElementById("remitToStateAbbrev").value.trim().length == 0) {
//    errorMsg += "\n"+messagesData.stateProvinceRequired;
//  }
  var c1 = $v("remitToCountryAbbrev");
  var z1 = $v("remitToZip");
  var z2 = $v("remitToZipPlus");
  if( !zipCheck(c1,z1,z2) ) {
    errorMsg += "\n"+messagesData.zip;
  }

  if(document.getElementById("remitToZip") == null ||document.getElementById("remitToZip").value.trim().length == 0) {
    errorMsg += "\n"+messagesData.zip;
  }
 
  if( errorMsg != '' ) {
   	alert(messagesData.validvalues+errorMsg);
   	return false;
  }
  return true;
}

function newAddress() {
	if(validateInput()) {
	   showPleaseWait();
	   document.getElementById("uAction").value = "new";
	   document.genericForm.target='_self';
 	   window.setTimeout("document.genericForm.submit();",500);
	}
}