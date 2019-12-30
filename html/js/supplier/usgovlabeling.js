function $(a) {
	return document.getElementById(a);
}
/*
function showPacking(selectedSupplier,selectedSupplierLocation,selectedPacking) {

  var idArray1 = altPackingId[selectedSupplier];
  var nameArray1 = altPackingName[selectedSupplier]

  var idArray = null;
  if( idArray1 != null )
  	idArray = idArray1[selectedSupplierLocation];
  var nameArray = null;
  if( nameArray1 != null )
  	nameArray = nameArray1[selectedSupplierLocation];
  var selectI = 0;

  var reset = $("picklistId");
  for (var i = reset.length; i > 0; i--) {
    reset[i] = null;
  }

  if( nameArray.length == 1) {
		setOption(0,messagesData.none,"NONE", "picklistId");
  }
  else {
	insertAt = 0;
  	for (var i=0; i < nameArray.length; i++) {
    	setOption(insertAt,nameArray[i],idArray[i], "picklistId");
    	if( selectedPacking == idArray[i] )
    		selectI = insertAt;
    	insertAt++;
  	}
  }
  $("picklistId").selectedIndex = selectI;
}*/

function showSupplierLocation(selectedSupplier,selectedSupplierLocation) {
  var idArray = altSupplierLocationId[selectedSupplier];
  var nameArray = altSupplierLocationName[selectedSupplier];
  var selectI = 0;

  var reset = $("suppLocationIdArray");
  for (var i = reset.length; i > 0; i--) {
    reset[i] = null;
  }

  /*if( nameArray.length == 1) {
		setOption(0,nameArray[0],idArray[0], "suppLocationIdArray");
  }
  else {*/
   	  insertAt = 0;
 	  for (var i=0; i < nameArray.length; i++) {       
      setOption(insertAt,nameArray[i],idArray[i], "suppLocationIdArray");
    	if( selectedSupplierLocation == idArray[i] )
    		selectI = insertAt;
    	insertAt++;
  	  }
  /*}*/

  if( insertAt == 0 )
    	setOption(insertAt,messagesData.all,"", "suppLocationIdArray");
  $("suppLocationIdArray").selectedIndex = selectI;
}

/*
function showSupplier(oldSupplier){
  var idArray = altSupplierId;
  var nameArray = altSupplierName;
  var selectI = 0 ;
  if( nameArray.length == 1) {
		setOption(0,nameArray[0],idArray[0], "supplier");
  }
  else {
  	  insertAt = 0;
	  for (var i=1; i < nameArray.length; i++) {
    	setOption(insertAt,nameArray[i],idArray[i], "supplier");
    	if( oldSupplier == idArray[i] )
    		selectI = insertAt;
    	insertAt++;
	  }
  }
  $("supplier").selectedIndex = selectI;
}
*/

/*function setSupplier() {
 var oldSupplier =  $("oldsupplier").value;
 var oldSupplierLocation =  $("oldshipFromLocationId").value;
 var oldPacking =  $("oldpicklistId").value;
 try {
 showSupplier(oldSupplier);
 showSupplierLocation($("supplier").value,oldSupplierLocation);
 showPacking($("supplier").value,$("suppLocationIdArray").value,oldPacking);
 } catch (ex) {}
}*/

function supplierChanged() {

  var Supplier = $("supplier");
  var SupplierLocation = $("suppLocationIdArray");
  var selectedSupplier = Supplier.value;

  for (var i = SupplierLocation.length; i > 0; i--) {
    SupplierLocation[i] = null;
  }
  showSupplierLocationOptions(selectedSupplier);
}

function supplierLocationChanged() {

  var Supplier = $("supplier");
  var SupplierLocation = $("suppLocationIdArray");
  var Packing = $("picklistId");

  var selectedSupplier = Supplier.value;
  var selectedSupplierLocation = SupplierLocation.value;

  for (var i = Packing.length; i > 0; i--) {
    Packing[i] = null;
  }

  showPackingOptions(selectedSupplier,selectedSupplierLocation);
}

function showSupplierLocationOptions(selectedSupplier) {
  showSupplierLocation(selectedSupplier,null);
  //showPackingOptions(selectedSupplier, $("suppLocationIdArray").value ) ;
}

/*function showPackingOptions(selectedSupplier,selectedSupplierLocation) {
	showPacking(selectedSupplier,selectedSupplierLocation,null);
}*/

function searchOnLoad()
{
   setSearchFrameSize();
   //setSupplier();

	 var picklistId = document.getElementById("picklistId");
   if (picklistId.value != "NONE" && picklistId.value.trim().length >0)
   {
    search();
   }
}

function search() {
	if( $('supplier').value == "") {
		alert(messagesData.required);
		return false;
	}

/*	if($('picklistId').value == "NONE") {
		alert(messagesData.noopenpackingids);
		return false;
	}*/

  	var userAction = document.getElementById("_action");
 		userAction.value = 'search';

   	document.supplierLocationForm.target='resultFrame';
   	parent.showPleaseWait();
   	document.supplierLocationForm.submit();
  	return true;
}

function showErrorMessages()
{
		parent.showErrorMessages();
}

/*function createExcel() {
    openWinGeneric('/tcmIS/common/loadingfile.jsp', 'show_excel_report_file','800','600','yes');
		document.supplierLocationForm.target='show_excel_report_file';
		document.supplierLocationForm.action='/usgovlabelingresults.do';
  	var userAction = document.getElementById("_action");
 		userAction.value = 'createExcel';
   	var a = window.setTimeout("document.supplierLocationForm.submit();",1000);

   	return false;
}*/

function printLabels()
{
 var totalLines = document.getElementById("totalLines").value;
 totalLines = totalLines*1;
 var checkedCount = 0;
 var isValidRow = true;

 for ( var p = 0 ; p < totalLines ; p ++)
 {
	 try
	 {
		 var lineCheck = document.getElementById("ok"+p+"");
		 if (lineCheck.checked)
		 {
			 checkedCount++;

			 if($v('trackSerialNumber'+p) == 'Y' && ($v('quantity'+p) != $v('numOfSerialNumber'+ p) ||  ($v('numOfPalletId'+ p) == '0' && $v('numberOfBoxes'+ p) == '0')))
			 {
				 isValidRow = false;
				 break;
			 }
		 }
	 }
	 catch (ex1)
	 {
	 }
 }

 if (checkedCount >0 && isValidRow)
 {
  openWinGeneric('/tcmIS/common/loadingfile.jsp', 'printLabels','800','600','yes');
 	document.supplierLocationForm.target='printLabels';
  var a = window.setTimeout("document.supplierLocationForm.submit();",1000);
 }
 else if (!isValidRow)
	 alert(messagesData.snpalletcasecapture);
  else
 	alert(messagesData.pleasemakeselection);

}

function undoMrLine(rowNumber) {
  var radianPo = document.getElementById("radianPo"+rowNumber+"");
  var poLine = document.getElementById("poLine"+rowNumber+"");
  if (confirm("Do you Want to reverse "+radianPo.value.trim()+"-"+poLine.value.trim()+"?\n\n"))
	{
    var ok = document.getElementById("ok"+rowNumber+"");
 		ok.checked = true;
    document.supplierLocationForm.target='';
		document.supplierLocationForm.action='usgovlabelingresults.do';
  	var userAction = document.getElementById("_action");
 		userAction.value = 'undoMrLine';
   	var a = window.setTimeout("document.supplierLocationForm.submit();",100);
   	parent.showPleaseWait();
   }
}

function resultOnLoad()
{
 setResultFrameSize();
 if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
}

function showLegend(){
  openWinGeneric("/tcmIS/help/usgovshippinglegend.html","usgovshippinglegend","290","350","no","50","50")
}

function getShipToAddress(locationId, dodaac) {
openWinGeneric("/tcmIS/supplier/viewaddress.do?locationId="+locationId+"&ultimateDodaac="+dodaac,'_viewaddress','500','300','yes');
}