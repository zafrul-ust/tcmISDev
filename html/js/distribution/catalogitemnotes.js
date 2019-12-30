windowCloseOnEsc = true;
resizeGridWithWindow = true;

var beangrid = null;

function submitUpdate() {
 $("uAction").value = 'update';
 
// if (beangrid != null) 
       beangrid.parentFormOnSubmit();
// submitOnlyOnce();
 document.genericForm.submit();
}

function validateSearchForm() {
 var itemId = document.getElementById("itemId");
 if (!isInteger(itemId.value.trim())) {
    alert(messagesData.itemInteger);
    return false;
 }
 
  document.genericForm.target='resultFrame';
  showPleaseWait();
  document.genericForm.submit();
 return true;
}

function initializeGrid(){
	$("catalogItemNotesBean").style["display"] = "";
	
	beangrid = new dhtmlXGridObject('catalogItemNotesBean');

	initGridWithConfig(beangrid,config,false,true,true);
//	if( typeof( jsonMainData ) != 'undefined' ) {		
		beangrid.parse(jsonMainData,"json");
//	}	
	
//	beangrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
//	beangrid.attachEvent("onRowSelect",doOnRowSelected);
}
