function returnInventoryGroup() {	
  if ( window.opener.inventoryGroupSelected ) {
	  	var s = new inventoryGroupInfo();	  	
	  	window.opener.inventoryGroupSelected(s);
	  	window.close();
	  	window.opener.closeTransitWin();
	  	return;
  } 
}

var inventoryGroup = '';
var inventoryGroupName = '';
var opsEntityId = '';
var currencyId = '';

function inventoryGroupInfo() {	
    this.currencyId = $v("currencyId");
	this.opsEntityId = $v("opsEntityId");
	this.inventoryGroup = $v("inventoryGroup");
} 
