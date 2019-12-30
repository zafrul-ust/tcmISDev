function returnData(locationId,locationDesc,shipToCompanyId) {	
	
  if ( window.parent.shipToChanged ) {	 
	  	var s = new shipToInfo();	  	
	  	window.parent.shipToChanged(s);
	  	window.close();
	  	return;
  } else if( source == 'dbuycontract' ) {
	    window.parent.opener.shiptochanged(locationId,locationDesc,shipToCompanyId);
		window.close();
		 return;			
  } else if ( displayElementId ) {
    var dispElemId = parent.opener.document.getElementById( displayElementId );
    if (dispElemId) dispElemId.value = locationId;
    var valElemId = parent.opener.document.getElementById( valueElementId );
    if (valElemId) valElemId.value = locationDesc;
 } 

 if (inventoryGroup==null || inventoryGroup=='') {
    getInventoryGroup(locationId);
 }

 // * we cannot close the window here. Allow the child window ('getInventoryGroup') to close the window.
 // window.close();
}

var locationId = '';
var locationDesc = '';
var shipToCompanyId = '';
var currentData = '';
var facilityId = '';
var addressLine1 = '';
var addressLine2 = '';
var addressLine3 = '';
var city = '';
var stateAbbrev = '';
var zip = '';
var countryAbbrev = '';

function shipToInfo() {	
    this.locationId = locationId;
	this.locationDesc = locationDesc;
	this.shipToCompanyId = shipToCompanyId; 
	this.currentData = currentData; 
	this.addressLine1 = addressLine1;
	this.addressLine2 = addressLine2;
	this.addressLine3 = addressLine3;
	this.city = city;
	this.stateAbbrev = stateAbbrev; 
	this.zip = zip; 
	this.countryAbbrev = countryAbbrev;
	this.facilityId = facilityId;
} 

function getInventoryGroup(locationId) 
{
  loc = "/tcmIS/supply/selectinvngroup.do?locationId=" + locationId;
  return openWinGeneric(loc,"InventoryGroup","600","500","yes","50","50");
}


function updateInventoryGroup(newInventoryGroup) 
{
   inventoryGroup = newInventoryGroup;

   // **************
   // **TODO: make sure the inventory group element used is the correct one! 
   // **
   // **************

   // var igValElemId = parent.opener.document.getElementById( igValueElementId );
   // if (igValElemId) igValElemId.value = newInventoryGroup;
}