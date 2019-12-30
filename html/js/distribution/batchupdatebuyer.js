windowCloseOnEsc = true;
window.onresize= resizeFrames;
var showErrorMessage = false;

function searchCarrier()
{
   loc = "/tcmIS/supply/pocarriermain.do?displayArea=carrierName&valueElementId=carrier";
   openWinGeneric(loc,"searchCarrier","600","450","yes","50","50","20","20","no");
}


function clearCarrier()
{
    document.getElementById("carrierName").value = "";
    document.getElementById("carrier").value = "";
}

function searchCriticalOrderCarrier()
{
   loc = "/tcmIS/supply/pocarriermain.do?displayArea=criticalOrderCarrierName&valueElementId=criticalOrderCarrier";
   openWinGeneric(loc,"searchCarrier","600","450","yes","50","50","20","20","no");
}


function clearCriticalOrderCarrier()
{
    document.getElementById("criticalOrderCarrierName").value = "";
    document.getElementById("criticalOrderCarrier").value = "";
}

function getSupplierContact() {
	if($v("supplier").length == 0) {
		alert(messagesData.choosesupplierb4choosecontact);
		return false;
	}
	
    loc = "/tcmIS/supply/posuppliercontact.do?action=search&supplier="+$v("supplier")+"&displayElementId=supplierContactName&valueElementId=supplierContactId&fromSupplierPriceList=Y";
    openWinGeneric(loc,"shoSupplierContacts","800","450","yes","50","50","no");
}

function clearSupplierContact() {
	$("supplierContactName").value = '';
	$("supplierContactId").value = '';
}

function validateForm() {
	if( $v('inventoryGroup') == '' && !$v('itemId') && !$v('supplier')) {
		  alert(messagesData.eitheritemorsupplier);
		  return false;
	}
	
	if( !$v('buyer') && !$v('carrier') && !$v('criticalOrderCarrier') && !$v('supplierContactId') 
		&& !$v('remainingShelfLifePercent') && !$v('priceType') && !$v('startDate') && !$v('unitPrice')) {
		  alert(messagesData.inputnewvalues);
		  return false;
	}
	
	if( (!$v('startDate') && $v('unitPrice')) || ($v('startDate') && !$v('unitPrice'))) {
		  alert("Please enter both Start Date and Unit Price");
		  return false;
	}
	
	return true;
}

function transferInventoryGroup(objFrom,objTo) {

     if($v(objFrom) == "") {
     	if($(objFrom).length > 1) {
	        for (j=1;j<$(objFrom).length;j++) {
	           if ($(objTo).options[0].value == "xxblankxx") 
					setOption(0,$(objFrom).options[j].text,$(objFrom).options[j].value,objTo);
				else {
					if($(objTo).length == 2 && $(objTo).options[1].value.length == 0)
						setOption(1,$(objFrom).options[j].text,$(objFrom).options[j].value,objTo);
					else 
						setOption($(objTo).length,$(objFrom).options[j].text,$(objFrom).options[j].value,objTo);
				}
	        }
        }
     } else {
        for (j=0;j<$(objTo).length;j++) {
           if($(objTo).options[j].value == $v(objFrom)){
              return;
           }
        }
       
		//removing space holder from drop down
		if ($(objTo).options[0].value == "xxblankxx") 
			setOption(0,$(objFrom).options[$(objFrom).selectedIndex].text,$v(objFrom),objTo);
		else {
			if($(objTo).length == 2 && $(objTo).options[1].value.length == 0)
				setOption(1,$(objFrom).options[$(objFrom).selectedIndex].text,$v(objFrom),objTo);
			else 
				setOption($(objTo).length,$(objFrom).options[$(objFrom).selectedIndex].text,$v(objFrom),objTo);
		}
	 }
	 
	 if($("inventoryGroups").length != 0 && $("inventoryGroups").options[0].value != 'xxblankxx') 
	 	$("deleteBtn").style.display="";
}

function clearList(obj1) {
	var obj = $(obj1);
	
	for (var i = obj.length; i >= 0;i--)
      obj[i] = null;
      
    $("deleteBtn").style.display="none";  
      
    setOption(0,"                    ","xxblankxx", obj1);
    
}

function doBatchUpdateBuyer() {
	if( window['validateForm'] && !validateForm() ) return false;
	
	$("inventoryGroupList").value = ""; 
	
	if($("inventoryGroups").length != 0 && $("inventoryGroups").options[0].value != 'xxblankxx') {
		for (var i = $("inventoryGroups").length-1; i >= 0;i--){
			$("inventoryGroupList").value = $v("inventoryGroupList")+",'"+$("inventoryGroups").options[i].value+"'";
		}
		$("inventoryGroupList").value = $v("inventoryGroupList").substring(1);
	}
	else
		$("inventoryGroupList").value = "'"+$v("inventoryGroup")+"'";
	
    $('uAction').value = 'batchUpdateBuyer';
	document.genericForm.submit();
	
//	window.close();
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

var children = new Array();

function getItem() {
	if( $v('inventoryGroup') == '') {
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
   loc = "/tcmIS/supply/posuppliermain.do?popUp=Y&fromSupplierPriceList=Y&displayElementId=supplierName&valueElementId=supplier&statusFlag=true&opsEntityId="+$v("opsEntityId");
   openWinGeneric(loc,"supplierssearch","800","500","yes","50","50")
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

function showError() {
  if (showErrorMessage)
  {
  	setTimeout('showUpdateErrorMessages()',50); /*Showing error messages if any*/
  }
}

function showUpdateErrorMessages()
{
  var resulterrorMessages = document.getElementById("errorMessagesAreaBody");
  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.innerHTML = resulterrorMessages.innerHTML;

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";

  var dhxWins = new dhtmlXWindows();
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window("errorWin")) {
  // create window first time
  var errorWin = dhxWins.createWindow("errorWin", 0, 0, 320, 150);
  if($v("done") == 'Y')
  	errorWin.setText(messagesData.update);
  else
    errorWin.setText(messagesData.errors);
  errorWin.clearIcon();  // hide icon
  errorWin.denyResize(); // deny resizing
  errorWin.denyPark();   // deny parking
  errorWin.attachObject("errorMessagesArea");
  errorWin.attachEvent("onClose", function(errorWin){errorWin.hide();});
  errorWin.center();
  }
  else
  {
    // just show
    dhxWins.window("errorWin").show();
  }
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
	  
	  clearList('inventoryGroups');
}

function inventoryDelete(rList)
{
	for(var i =  rList.length - 1 ; i > -1; i--)
		if(rList[i].selected)
			rList[i] = null;
	 var inventoryGroups = $('inventoryGroups').options;
	 if(inventoryGroups.length == 0)
	 {
		$('deleteBtn').style['display'] = "none";
		setOption(0, '', 'xxblankxx', "inventoryGroups");
	 }
}

