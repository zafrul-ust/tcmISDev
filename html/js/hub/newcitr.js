var windowCloseOnEsc = true; 
function setup() {
   var obj = $("inventoryGroup");
   for (var i = obj.length; i > 0;i--)
     obj[i] = null;
   var inventoryGroupArray = altInventoryGroup;
   var selectedIndex =0;
   if (inventoryGroupArray != null) {
       for(var i=0;i< inventoryGroupArray.length;i++) {
            setOption(i,inventoryGroupArray[i].inventoryGroupName,inventoryGroupArray[i].inventoryGroup, "inventoryGroup");
            if( inventoryGroupArray[i].value == $v("defaultInventoryGroup") )
                selectedIndex = i;
       }
   }else {
      setOption(0,messagesData.pleaseselect,"", "inventoryGroup");
   }
   obj.selectedIndex = selectedIndex;
   loadCompany();
}

function inventoryGroupChanged() {
    loadCompany();
}

function loadCompany() {
    var selectedInventoryGroup = $v("inventoryGroup");
    var companyArray = altCompany[selectedInventoryGroup];
    //clear current data
    var inv = $("ownerCompanyId");
    for (var i = inv.length; i > 0; i--) {
        inv[i] = null;
    }
    //load new data
    if( companyArray != null ) {
        var startingIndex = 0;
        if (companyArray.length == 0 || companyArray.length > 1) {
            setOption(0,messagesData.pleaseselect,"", "ownerCompanyId");
            startingIndex = 1;
        }
        for (var i=0; i < companyArray.length; i++) {
            setOption(i+startingIndex,companyArray[i].companyName,companyArray[i].companyId, "ownerCompanyId");
        }
    }else {
        setOption(0,messagesData.pleaseselect,"", "ownerCompanyId");
    }
    companyChanged();
}

function companyChanged() {
    var selectedInventoryGroup = $v("inventoryGroup");
    var selectedCompanyId = $v("ownerCompanyId");
    var ownerSegmentArray = altOwnerSegment[selectedInventoryGroup+selectedCompanyId];

    var inv = $("ownerSegmentId");
    for (var i = inv.length; i > 0; i--) {
        inv[i] = null;
    }

    var selectedIndex = 0;
    if( ownerSegmentArray != null ) {
        var startingIndex = 0;
        if (ownerSegmentArray.length == 0 || ownerSegmentArray.length > 1) {
            setOption(0,messagesData.pleaseselect,"", "ownerSegmentId");
            startingIndex = 1;
        }
        for (var i=0; i < ownerSegmentArray.length; i++) {
            setOption(i+startingIndex,ownerSegmentArray[i].ownerSegmentDesc,ownerSegmentArray[i].ownerSegmentId, "ownerSegmentId");
        }
    }else {
        setOption(0,messagesData.pleaseselect,"", "ownerSegmentId");
    }
}


function returnCatalogInfo(p)
{
   $("catalogCompanyId").value = p.companyId;
   $("catalogId").value = p.catalogId;
   $("catPartNo").value = p.catPartNo;
   $("partGroupNo").value = p.partGroupNo;
   if(!p.unitOfSale == '')
   {
    $('uos').style["display"] = "";
    $("unitOfSale").value = p.unitOfSale;
   }
}

function validateForm() {
	
	var errorMsg = '';
	if( !$v("inventoryGroup"))
		errorMsg += "\n"+messagesData.inventorygroup;
	if( !$v('itemId'))
		errorMsg += "\n"+messagesData.itemid;
	if( !$v('ownerCompanyId') )
		errorMsg += "\n"+messagesData.ownerCompanyId;
	if( !$v('quantityToReceive') )
		errorMsg += "\n"+messagesData.quantityToReceive;
	if($v('quantityToReceive') && !isInteger($v('quantityToReceive',true)))
		errorMsg += "\n"+messagesData.quantityToReceive + messagesData.integer;	
	if( !$v('expectedDeliveryDate') )
		errorMsg += "\n"+messagesData.expectedDeliveryDate;
	if( !$v('poNumber') )
		errorMsg += "\n"+messagesData.poNumber;
	if( errorMsg != '' ) {
		  alert(messagesData.validvalues+errorMsg);
		  return false;
	}
	return true;
}

function newCitrWinClose()
{
	if(closeNewCitrWin)
	{ 
		window.close();	
		window.opener.document.getElementById("uAction").value = 'search';
		window.opener.document.genericForm.target='resultFrame';
		window.opener.showPleaseWait();
		window.opener.document.genericForm.submit();
	}
}

function newItem() {
	document.genericForm.target='';
	document.getElementById("uAction").value = "addnewitem";
	var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");    
	startSearchTime.value = now.getTime();
   var flag = validateForm();
	
	if(flag) 
	{
		showPleaseWait();
		document.genericForm.submit();  	   
   		return true;
  	}
  	else
  	{
    	return false;
  	}
		
}

var children = new Array();

function getItem() {
	if($v("inventoryGroup").length == 0) {
		alert(messagesData.missinginventorygroup);
		return false;
	}
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

/*
function getCompanyId()
{
   loc = "/tcmIS/distribution/companysearchmain.do"
   openWinGeneric(loc,"_companysearch","600","500","yes","50","50","no")
}

function clearCompanyId() {
	$('companyId').value='';
	$('companyName').value='';						
	
}

function companyChanged(code,name) {
	
	clearCompanyId();
	$('companyId').value=code;

	$('companyName').value=name;						


}
*/

function closeAllchildren() {
	// do nothing now.
}


function clearItem()
{
    document.getElementById("itemDesc").value = "";
    document.getElementById("itemId").value = "";
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
}

