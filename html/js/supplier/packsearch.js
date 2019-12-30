function $(a) {
	return document.getElementById(a);
}

function showSupplierLocation(selectedSupplier,selectedSupplierLocation) {
  var idArray = altSupplierLocationId[selectedSupplier];
  var nameArray = altSupplierLocationName[selectedSupplier];
  var selectI = 0;
  var inserted = 0 ;

  var reset = $("suppLocationIdArray");
  for (var i = reset.length; i > 0; i--) {
    reset[i] = null;
  }
	  for (var i=0; i < nameArray.length; i++) {
    	//if( idArray[i] != "" )      
    	setOption(inserted,nameArray[i],idArray[i], "suppLocationIdArray");
    	if( selectedSupplierLocation == idArray[i] )
    			selectI = inserted;
    		inserted++;
  	  }
  	if( inserted == 0 )
    	setOption(inserted,messagesData.all,"", "suppLocationIdArray");
  	$("suppLocationIdArray").selectedIndex = selectI;
}

function showSupplier(oldSupplier){
  var idArray = altSupplierId;
  var nameArray = altSupplierName;
  var selectI = 0 ;
  var inserted =0;

	  for (var i=0; i < nameArray.length; i++) {
    	if( idArray[i] != "" ) {
    		setOption(inserted,nameArray[i],idArray[i], "supplier");
    		if( oldSupplier == idArray[i] )
    			selectI = inserted;
    		inserted++;
    	}
	  }
  $("supplier").selectedIndex = selectI;
}

/*
function setSupplier() {
 var oldSupplier =  $("oldsupplier").value;
 var oldSupplierLocation =  $("oldshipFromLocationId").value;
 try {
 showSupplier(oldSupplier);
 showSupplierLocation($("supplier").value,oldSupplierLocation);
 } catch (ex) {}
}
*/

function supplierChanged() {

  var Supplier = $("supplier");
  var SupplierLocation = $("suppLocationIdArray");
  var selectedSupplier = Supplier.value;
  showSupplierLocation(selectedSupplier,null);
}

function dosearch() {
	if( $('supplier').value == "") {
		alert(messagesData.required);
		return false;
	}

  //radianPo
  //itemId
  //mrNumber
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  var searchOn = $('searchField').value;
  var searchVal = $('searchArgument').value.trim();
	if( ( searchOn == "radianPo" || searchOn == "itemId" || searchOn == "mrNumber"  ) &&
	    ( (searchVal != null && searchVal != "" && !isInteger( searchVal )) || parseInt(searchVal) < 1) ){
//	    alert( titleRequired[searchOn] + " "+messagesData.errorInteger);
	    alert(messagesData.errorInteger);
	    $('searchArgument').focus();
	    return false;
	}

     var userAction = document.getElementById("userAction");
	  userAction.value = 'search';

   	document.supplierLocationForm.target='resultFrame';
   	parent.showPleaseWait();
   	document.supplierLocationForm.submit();
}

function createExcel() {
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', 'show_excel_report_file','800','600','yes');
		document.supplierLocationForm.target='show_excel_report_file';
  	var userAction = document.getElementById("userAction");
 		userAction.value = 'createExcel';
    var a = window.setTimeout("document.supplierLocationForm.submit();",1000);
    //document.supplierLocationForm.submit();
   	return false;
}

function onLoadSearchCheck()
{
   setSearchFrameSize();
   var pageDo = document.getElementById("pageDo").value;
   if (pageDo.trim().length >0 && pageDo == 'searchLoad')
	   dosearch();

}
