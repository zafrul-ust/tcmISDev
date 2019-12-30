windowCloseOnEsc = true;
var children = new Array();

var dontChangElemCss = true;
function myOnload() {
	try {
		$("priceGroupId").value = $v("defaultPriceGroupId");
	} catch(ex) {}
	
	if ( showErrorMessage ) 
 		showErrorMessages();
 	
 	if ( done ) {
 		
 		
 		if(fromShipToAddress)
 		{	
			 			
 			 			
 			var s = new addressInfo()
 			opener.opener.addressChanged(s); 			
 			
 			opener.opener.$("inventoryGroup").value = $v("selectedInventoryGroup");
 			opener.opener.$("inventoryGroupNameSpan").innerHTML = $v("selectedInventoryGroupName");
 			
 			opener.opener.$("salesAgentNameSpan").innerHTML = $v("salesAgent");
 			opener.opener.$("salesAgentName").value = $v("salesAgent");
 			opener.opener.$("salesAgentId").value = $v("salesAgentId");
 			
 			opener.opener.$("fieldSalesRepNameSpan").innerHTML = $v("fieldSalesRepName");
 			//opener.opener.$("salesAgentName").value = $v("inventoryGroup");
 			opener.opener.$("fieldSalesRepId").value = $v("fieldSalesRepId");
 			
 			//opener.opener.$("priceGroupId").value = $v("priceGroupId"); 			
 			//opener.opener.$("priceGroupSpan").innerHTML = $v("priceGroupId"); 			
 			
 			opener.opener.setPriceListDropDown($v("priceGroupId"));
			opener.window.close();
 		}
 		 
 		if(fromCustomerDetail)
 		{	
		var cusId = document.getElementById("customerId").value;	 
		var loc = "/tcmIS/distribution/customerupdate.do?customerId="+cusId;	  
		window.opener.showTransitWin();
	   try
	   {
		   opener.parent.parent.openIFrame("customerDetails"+cusId+"",loc,""+messagesData.customerdetails+" "+cusId+"","","N");
	   }	
	   catch (ex)
	   {
		   children[children.length] = openWinGeneric(loc,"customerDetails"+cusId,"900","600","yes","80","80","yes");	
	   }
 		}
		window.close();
	}
}

function shipCountryChanged() {
  var country0 = document.getElementById("remitToCountryAbbrev");
  var state0 = document.getElementById("remitToStateAbbrev");
  var selectedCountry = country0.value;
  for (var i = state0.length; i >= 0; i--) {
    state0[i] = null;
  }

  if( country0.value == 'USA' ) 
  	$('shipToZipPlusSpan').style.display="";
  else
  	$('shipToZipPlusSpan').style.display="none";
  	
  shipShowStateOptions(selectedCountry);
  state0.selectedIndex = 0;

}

function shipShowStateOptions(selectedCountry) {
  var stateArray = new Array();
  stateArray = altState[selectedCountry];

  var stateNameArray = new Array();
  stateNameArray = altStateName[selectedCountry];
  stateEle = "remitToStateAbbrev";
  
  if (stateArray == null || stateArray.length == 0) {
	  setOption(0,messagesData.pleaseselect,"", stateEle);
	  return;
  }
  if (stateArray.length == 1 || !stateArray[0] ) {
	    setOption(0,stateNameArray[0],stateArray[0], stateEle);
	    return;
  }
  
  var selectedIndex = 0;
  var start = 1;
  setOption(0, messagesData.pleaseselect, "", stateEle);
  for (var i=0; i < stateArray.length; i++) {
    	setOption(start++,stateNameArray[i],stateArray[i], stateEle);
  }
  $('remitToStateAbbrev').selectedIndex = selectedIndex;
  
}

function validateInput() {
  var errorMsg = "";
  
  var addval = "";
  for(i=1;i<=5;i++){
	  addval += $v('remitToAddressLine'+i).toUpperCase();}
  var z1 = $v("shipToZip").toUpperCase();
  if( z1 && addval.indexOf(z1) == -1 ) {
	  alert(messagesData.fulladdressnozip);
	  return;
  }
  
  var c1 = $v("shipToCity").toUpperCase();
  if( c1 && addval.indexOf(c1) == -1 ) {
	  alert(messagesData.fulladdressnocity);
	  return;
  }
  
  
  if( isEmptyV("remitToCountryAbbrev") ) {
    errorMsg += "\n"+messagesData.countryRequired;
  }
  if( isEmptyV("remitToAddressLine1") ) {
    errorMsg += "\n"+messagesData.addressRequired;
  }
  if( isEmptyV("shipToCity") ) {
    errorMsg += "\n"+messagesData.cityRequired;
  }
  var c1 = $v("remitToCountryAbbrev");
  if( c1 == 'USA' || c1 == 'CAN' )
  if(  $("remitToStateAbbrev").options.length > 1 && ( document.getElementById("remitToStateAbbrev") == null ||document.getElementById("remitToStateAbbrev").value.trim().length == 0 )) {
	errorMsg += "\n"+messagesData.stateProvinceRequired;
  }
  if( isEmptyV("customerName") ) 
  {
    errorMsg += "\n"+messagesData.shipToNameRequired;
  }

  var c1 = $v("remitToCountryAbbrev");
  var z1 = $v("shipToZip");
  var z2 = $v("shipToZipPlus");
  if( !zipCheck(c1,z1,z2) ) {
	    errorMsg += "\n"+messagesData.zipRequired;
	  }
  var numRows = haasGrid.getRowsNum(); 
  {
  for( i=0;i< numRows; i++) {
	  rowid = haasGrid.getRowId(i);
	  if( cellValue(rowid, 'inventoryGroupDefault') == '' ) {
		    errorMsg += "\n"+messagesData.invRequired;
		    break;
	  }
  }
  }
  
  if( errorMsg != '' ) {
   	alert(messagesData.validvalues+errorMsg);
   	return false;
  }
  if( isEmptyV("inventoryGroup") && !numRows) {
	    alert( messagesData.atleastoneinvgroup );
	    return false;
  }
  return true;
}

/*function shipinfo() {
	this.customerName = $v('customerName');
	this.shipToCity = $v('shipToCity');
	this.shipToAddressLine1 = $v('shipToAddressLine1');
	this.shipToAddressLine2 = $v('shipToAddressLine2');
	this.shipToZip = $v('shipToZip');
	this.shipToZipPlus = $v('shipToZipPlus');
	this.shipToAddressLine3 = $v('shipToAddressLine3');
	this.shipToCountryAbbrev = $v('shipToCountryAbbrev');
	this.shipToStateAbbrev = $v('shipToStateAbbrev');
}*/

function newAddress() {
	if(validateInput()) {
		
	   if(fromShipToAddress)
	   {
		   document.getElementById("uAction").value = "addAndShow";
	   }   
	   else
	   {	   
	   document.getElementById("uAction").value = "new";
	   }
	   haasGrid.parentFormOnSubmit();
	   document.genericForm.target='_self';
	   showPleaseWait();
 	   window.setTimeout("document.genericForm.submit();",500); 	  
	}
}

function setDropDowns()
{
	$buildDropDown(opshubig,defaults.ops,"opsEntityId");
 	$('opsEntityId').onchange = setInv;    
	setInv();
	shipCountryChanged();
}

function buildNewIgValudset(opsid) {
//	var opsid = $v('opsEntityId');
	var newInvArray = new Array();
	if( !opsid ) return newInvArray; 
	for( i=0;i < opshubig.length; i++) {
		if( opshubig[i].id == opsid ) {
			var hubcoll = opshubig[i].coll;
			for( j = 0;j< hubcoll.length;j++ ){
				var igcoll = hubcoll[j].coll;
				for( k=0;k< igcoll.length;k++ ){
					newInvArray[newInvArray.length] = {name:igcoll[k].name,id:igcoll[k].id};
				}
			}	
		}
	}
	return newInvArray;
}

function setInv()
{
	 var opsO = $("opsEntityId");
	 var arr = buildNewIgValudset(opsO.value);
	 
	 $buildDropDown(arr,defaults.inv,"inventoryGroup");

	   var arr = null;
	   if( opsO.value != '' ) {
	   	   for(i=0;i< opspg.length;i++)
	   	   		if( opspg[i].id == opsO.value ) {
	   	   			arr = opspg[i].coll;
	   	   			break;
	   	   		}
	   }
	   $buildDropDown(arr,defaults.pg,"priceGroupId");
}

function buildDropDown( arr, defaultObj, eleName ) {
	   var obj = $(eleName);
	   for (var i = obj.length; i > 0;i--)
	     obj[i] = null;
	  // if size = 1 or 2 show last one, first one is all, second one is the only choice.
	  if( arr == null || arr.length == 0 ) 
		  setOption(0,defaultObj.name,defaultObj.id, eleName); 
	  else if( arr.length == 1 )
		  setOption(0,arr[0].name,arr[0].id, eleName);
	  else {
	      var start = 0;	  	  
		  for ( var i=0; i < arr.length; i++) {
		    	setOption(start++,arr[i].name,arr[i].id,eleName);
		  }
	  }
	  obj.selectedIndex = 0;
}

function $buildDropDown( arr, defaultObj, eleName ) {
	   var obj = $(eleName);
	   for (var i = obj.length; i > 0;i--)
	     obj[i] = null;
	  // if size = 1 or 2 show last one, first one is all, second one is the only choice.
	  if( arr == null || arr.length == 0 ) 
		  setOption(0,defaultObj.name,defaultObj.id, eleName); 
//	  else if( arr.length == 1 )
//		  setOption(0,arr[0].name,arr[0].id, eleName);
	  else {
	      var start = 0;
	  	  if( defaultObj.nodefault )
	  	  	start = 0 ; // will do something??
	  	  else 
	  	  {
		  setOption(0,defaultObj.name,defaultObj.id, eleName); 
			  start = 1;
		  }
		  for ( var i=0; i < arr.length; i++) {
		    	setOption(start++,arr[i].name,arr[i].id,eleName);
		  }
	  }
	  obj.selectedIndex = 0;
}

function getSalesRep() {
	  loc = "searchpersonnelmain.do?fixedCompanyId=Y&displayElementId=fieldSalesRepName&valueElementId=fieldSalesRepId";
	  children[children.length] = openWinGeneric(loc,"changepersonnel1","600","450","yes","50","50","20","20","no");
	  
	}

function getSalesAgent() {
	  loc = "searchpersonnelmain.do?fixedCompanyId=Y&displayElementId=salesAgent&valueElementId=salesAgentId";
	  children[children.length] = openWinGeneric(loc,"changepersonnel2","600","450","yes","50","50","20","20","no"); 
	}


function clearFieldSalesRep()
{
    document.getElementById("fieldSalesRepName").value = "";
    
}

function clearSalesAgent()
{
      document.getElementById("salesAgent").value = "";
}

function addressInfo()
{	
	this.locationType = $v('hlocationType');; 
	this.locationShortName = $v('hlocationShortName');;
	this.locationDesc = $v('hlocationDesc');;
	this.shipToLocationId = $v('hshipToLocationId');
	this.shipToCompanyId = $v('hshipToCompanyId');
	
	this.shipToAddressLine1Display = $v('remitToAddressLine1'); 
	this.shipToAddressLine2Display = $v('remitToAddressLine2');
	this.shipToAddressLine3Display = $v('remitToAddressLine3');
	this.shipToAddressLine4Display = $v('remitToAddressLine4');
	this.shipToAddressLine5Display = $v('remitToAddressLine5');
	this.fieldSalesRepId = $v('fieldSalesRepId');
	this.fieldSalesRepName = $v('fieldSalesRepName');
}

function closeAllchildren() 
{ 
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
//	if (document.getElementById("uAction").value != "search") {

			for(var n=0;n<children.length;n++) {
				try {
					children[n].closeAllchildren(); 
				} catch(ex){}
				children[n].close();
			}
		children = new Array();
} 