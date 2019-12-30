function partinfo() {
	  this.itemId = $v('itemId');
	  this.itemDesc = $v('itemDesc');
	  this.supplier = $v('supplier');
	  this.supplierName = $v('supplierName');
	  this.buyer = $v('buyer');
	  this.buyerName = $v('buyerName');
	  this.shipToLocationId = $v('shipToLocationId');
	  this.shipToLocationName = $v('shipToLocationName');
	  this.shipToCompanyId = $v('shipToCompanyId');
	  this.inventoryGroup = $('allInventoryGroups').checked ? 'All' : $v('inventoryGroup');
	  this.inventoryGroupName = $('allInventoryGroups').checked ? 'All' : $('inventoryGroup').options[$('inventoryGroup').selectedIndex].text;
	  this.inventoryGroupHub = $v('hub');
	  this.currencyId = $v('currencyId');
	  this.buyTypeFlag = $('allInventoryGroups').checked ? 'N' : $v('buyTypeFlag');
}

function validateForm() {
	var errorMsg = '';
	if( !$v('inventoryGroup') )
		errorMsg += "\n"+messagesData.inventorygroup;
	if( !$v('itemId') )
		errorMsg += "\n"+messagesData.itemid;
	if( !$v('supplier') )
		errorMsg += "\n"+messagesData.supplier;
//	if( !$v('buyer') )
//		errorMsg += "\n"+messagesData.buyer;
	if( errorMsg != '' ) {
		  alert(messagesData.validvalues+errorMsg);
		  return false;
	}
	return true;
}

function newItem() {
	if( window['validateForm'] && !validateForm() ) return false;
	try {
		opener.additem( new partinfo ) ;
	} catch(ex){}
	window.close();
}

var children = new Array();

function getItem() {
	if($v("inventoryGroup").length == 0) {
		alert(messagesData.missinginventorygroup);
		return false;
	}
		
	var shipid = $v('shipToLocationId');
	if( shipid == 'All' ) shipid = '';
	// new item no shipto.
//	var loc = "/tcmIS/supply/poitemsearchmain.do?uAction=new&shipToId=" + shipid+ 
	var loc = "/tcmIS/supply/poitemsearchmain.do?uAction=new&"+ 
	"&inventoryGroup=" + $v("inventoryGroup")+
	"&companyId=Radian";// + gg("inventoryGroup");
	var winId= 'poitemsearchmain';//+$v("prNumber");
	children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"1024","600","yes","50","50","20","20","no");
	
}

function itemChanged(itemId,itemDesc) {
	$('itemDesc').value = itemDesc;
	$('itemId').value = itemId;
}

function getSuppliers()
{
 //  loc = "/tcmIS/supply/posuppliermain.do?fromSupplierPriceList=Y&displayElementId=supplierName&valueElementId=supplier&statusFlag=true";
 //  openWinGeneric(loc,"supplierssearch","800","500","yes","50","50");
   
   loc = "/tcmIS/distribution/entitysuppliersearchmain.do?fromSupplierPriceList=Y&valueElementId=supplierid&statusFlag=true&displayElementId=supplierName&opsEntityId="+$v("opsEntityId");
/*	
	var suppid  =  document.getElementById("secondarysupplier"+rowNumber+"");

    var supplierrep = suppid.value;
    supplierrep = supplierrep.replace(/&/gi, "%26");
    supplierrep = supplierrep.replace(/#/gi, "%23");

    loc = loc + supplierrep;
    //loc = loc + suppid.value;  */
 //   loc = loc + "&secondarySupplier=Y";
 //   loc = loc + "&rowNumber="+rowNumber;
    loc = loc + "&inventoryGroup="+$v("inventoryGroup");
    loc = loc + "&opsEntityId=" + $v("opsEntityId");
    openWinGeneric(loc,"supplierssearch","800","600","yes","50","50");
}

function getShipToId(dispElemId, dispElemValue) {

	  loc = "/tcmIS/supply/poshiptomain.do?source=dbuycontract";
	  openWinGeneric(loc,"getShipToId","600","500","yes","50","50");

}

function shiptochanged(itemId,itemDesc,shipToCompanyId) {
	$('shipToLocationName').value = itemDesc;
	$('shipToLocationId').value = itemId;
	$('shipToCompanyId').value = shipToCompanyId;
}

function getBuyer() {
	  loc = "/tcmIS/haas/searchpersonnelmain.do?fixedCompanyId=Y";
	  openWinGeneric(loc,"getBuyer","600","400","yes","50","50","20","20","no");
}

function setBuyer(rowId, buyer, buyerName ) {
	$("buyer").value = buyer;
	$("buyerName").value = buyerName;
}

function clearBuyer() {
	$("buyer").value = "";
	$("buyerName").value = "";
}


function closeAllchildren() {
	// do nothing now.
}

function clearShipToId()
{
    document.getElementById("shipToLocationName").value = "All";
    document.getElementById("shipToLocationId").value = "All";
    document.getElementById("shipToCompanyId").value = "All";
}

function clearItem()
{
    document.getElementById("itemDesc").value = "";
    document.getElementById("itemId").value = "";
}

function clearSuppliers()
{
    document.getElementById("supplierName").value = "";
    document.getElementById("supplier").value = "";
}

function checkPermission() {
	  var opsEntityId = $v("opsEntityId");
	  var found = false; 

	  for(var i=0; i < OpsEntityPermissionArray.length; i++) {
	    if(OpsEntityPermissionArray[i] == opsEntityId) {
	      found = true;
	    }
	  }
	  if(found) {	    
	    $('okBtn').style.display = '';
	  }
	  else {	    
	    $('okBtn').style.display = 'none';
	  }
	  
	  setCurrencyDropdown( OpsCurrencyIdArray, opsEntityId, 'currencyId' );
}

function setCurrencyDropdown( arr, opsEntityId, eleName ) {
	var obj = $(eleName);
	for (var i = obj.length; i > 0;i--)
		obj[i] = null;

	var selectedCurrency = 0; 
	var start = 0; 
	for ( var j=0; j < arr.length; j++) {
		if (arr[j].opsEntityId == opsEntityId){
			if (arr[j].id == OpsCurrencyDefaultArray[opsEntityId]) {
				selectedCurrency = start;
			}
			setOption(start++,arr[j].name,arr[j].id,eleName);
		}
	}

	obj.selectedIndex = selectedCurrency;
}
