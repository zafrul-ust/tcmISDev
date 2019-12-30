/*
function cleanShip() {

 var below = new Array( "shipToAddressLine1","shipToCity","shipToAddressLine2","shipToAddressLine3","shipToZip","shipToZipPlus");
 for(i=0;i<below.length;i++)
 	$(below[i]).value = '';
 	
 $("shipToCountryAbbrev").selectedIndex = 0 ;
 
 var cv = $("shipToCountryAbbrev").value ;
 
 shipCountryChanged(cv);
 
}
function copyFromAbove() {

 if(! $('sameAsAbove').checked ) return;// cleanShip();
 
 var above = new Array( "addressLine1","city","addressLine2","addressLine3","zip","zipPlus");
 var below = new Array( "shipToAddressLine1","shipToCity","shipToAddressLine2","shipToAddressLine3","shipToZip","shipToZipPlus");
 for(i=0;i<above.length;i++)
 	$(below[i]).value = $(above[i]).value;
 	
 var cv  =  $("countryAbbrev").value ;
 var cArr=  $("shipToCountryAbbrev").options;
 var len =  $("shipToCountryAbbrev").options.length;

 for(i=0;i<len; i++)
 	if( cArr[i].value == cv ) {
 		$("shipToCountryAbbrev").selectedIndex = i;
 		break;
 	}
 	
 shipShowStateOptions(cv);
 
 cv  =  $("stateAbbrev").value ;
 cArr=  $("shipToStateAbbrev").options;
 len =  $("shipToStateAbbrev").options.length;

 for(i=0;i<len; i++)
 	if( cArr[i].value == cv ) {
 		$("shipToStateAbbrev").selectedIndex = i;
 		break;
 	}
}
*/
function changeNewCompany(){
    $("uAction").value = "changennewcompany";
    submitOnlyOnce();
    document.genericForm.submit();
}
function approveNewCompanyDirect(){
    $("uAction").value = "approveNewCompanyDirect";
    submitOnlyOnce();
    document.genericForm.submit();
}
function approveNewCompanyActivation(){
    $("uAction").value = "approveNewCompanyActivation";
    submitOnlyOnce();
    document.genericForm.submit();
}
function approveCustomerRequestActivation(){
    $("uAction").value = "approveCustomerRequestActivation";
    submitOnlyOnce();
    document.genericForm.submit();
}

function shipCountryChanged(rowId,columnId,selectedState) {
	
	  var selectedCountry = gridCellValue(shiptoGrid,rowId,columnId);
	  var state0 = $("shipToStateAbbrev"+rowId);
	  for (var i = state0.length; i > 0; i--) {
	    state0[i] = null;
	  }
	  var selectedIndex = 0 ;
	  var stateArray = altState[selectedCountry];
	  var stateNameArray = altStateName[selectedCountry];
	  
	  if (stateArray == null || stateArray.length == 0) {
		    setOption(0, messagesData.pleaseselect, "", 'shipToStateAbbrev'+rowId);
		    return;
		  }
		  var stateNameArray = new Array();
		  stateNameArray = altStateName[selectedCountry];
		  if (stateArray.length == 1) {
			    setOption(0, stateNameArray[0], stateArray[0], 'shipToStateAbbrev'+rowId);
			    return;
		  }

		  setOption(0, messagesData.pleaseselect, "", 'shipToStateAbbrev'+rowId);
		  var selectedIndex = 0;
		  var start = 1;
		  for (var i = 0; i < stateArray.length; i++) {
			  if ((null != selectedState) && (selectedState == stateArray[i])) {
			        selectedIndex = start;
			  }
		    setOption(start++, stateNameArray[i], stateArray[i], 'shipToStateAbbrev'+rowId);
		  }
		  state0.selectedIndex = selectedIndex;
}

function showStateOptions(selectedCountry, selectedState) {
	  var stateArray = new Array();
	  stateArray = altState[selectedCountry];
	  var state0 = $('stateAbbrev');
	  
	  if (stateArray == null || stateArray.length == 0) {
	    setOption(0, messagesData.pleaseselect, "", 'stateAbbrev');
	    return;
	  }
	  var stateNameArray = new Array();
	  stateNameArray = altStateName[selectedCountry];
	  if (stateArray.length == 1) {
		    setOption(0, stateNameArray[0], stateArray[0], 'stateAbbrev');
		    return;
	  }

	  setOption(0, messagesData.pleaseselect, "", 'stateAbbrev');
	  var selectedIndex = 0;
	  var start = 1;
	  for (var i = 0; i < stateArray.length; i++) {
		  if ((null != selectedState) && (selectedState == stateArray[i])) {
		        selectedIndex = start;
		  }
	    setOption(start++, stateNameArray[i], stateArray[i], 'stateAbbrev');
	  }
	  state0.selectedIndex = selectedIndex;
	}


function buildShiptoStateAbbrev(selectedCountry) {
	  var stateArray = altState[selectedCountry];
	  var stateNameArray = altStateName[selectedCountry];
	  var stateGridArray = new Array();
	  
	  if( stateArray.length == 0) {
	    stateGridArray[0] = {text:messagesData.all,value:''};
	  }
	
	  for (var i=0; i < stateArray.length; i++) {
		    stateGridArray[i] = {text:stateNameArray[i],value:stateArray[i]};
	  }
	  return stateGridArray;
}

var checkShipTo = false;

function showremovelines() {
   try
   {
	if(	shiptoGrid.getRowsNum() != 0 )
		$('newShiptoRemoveLine').style["display"] = "";
	if( carrierGrid.getRowsNum() != 0 ) 
		$('newCarrierRemoveLine').style["display"] = "";
	if( haasGrid.getRowsNum() != 0 ) 
		$('newContactRemoveLine').style["display"] = "";
	if( taxGrid.getRowsNum() != 0 ) 
		$('newTaxRemoveLine').style["display"] = "";
	if( billGrid.getRowsNum() != 0 ) 
		$('newbilltoRemoveLine').style["display"] = "";
    }
    catch (ex2)
    {

    }
}


var shipConfig = {
		divName:'beanData', // the div id to contain the grid of the data.
		beanData:'shiptoData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'shiptoGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'shiptoconfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
								// remember to call haasGrid.parentFormOnSubmit() before actual submit.
		onRowSelect:null,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:null,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	    singleClickEdit:false //This is for single click edidting. If it is set to true, txt column type will pop a txt editing box on sligne click. 
//		onBeforeSorting:_onBeforeSorting
	};

function ShiptoUpdate(act,val) {
	  if( !act ) act = 'uAction';
	  if( !val ) val = 'saveShipto';
    $(act).value = val;
	  //showPleaseWait();
    shiptoGrid.parentFormOnSubmit(); //prepare grid for data sending
    document.genericForm.submit();
    return false;
}

function ShiptoPrepareForm(act,val) {
	  if( !act ) act = 'uAction';
	  if( !val ) val = 'saveShipto';
  $(act).value = val;
	  //showPleaseWait();
  shiptoGrid.parentFormOnSubmit(); //prepare grid for data sending
  //document.genericForm.submit();
  return false;
}

var shiptoSelectedRowId = null;
var shiptorowids = new Array();

function loadshiptorowids() {
	var up = shiptoData.rows.length
	for(i=1;i<= up; i++)
		shiptorowids[""+i] = i;
}

function showgridstates() {
	//return ;
	if( pageReadOnly ) return ; // value already shown.
	for( rowId in shiptorowids)
		shipCountryChanged(rowId,"shipToCountryAbbrev",gridCellValue(shiptoGrid,rowId,"shipToStateAbbrev"));
}

function removeShiptoLine() {
	shiptoGrid.deleteRow(shiptoSelectedRowId);
	delete( shiptorowids[shiptoSelectedRowId] ) ;
	if( shiptoGrid.getRowsNum() == 0 ) 
		$('newShiptoRemoveLine').style["display"] = "none";
	return ;
}

function addNewShiptosLine(rowKey,orderQuantityRule) {

// if( !$v('opsEntityId') ) {
//	  alert(messagesData.pleaseselect + " : " + messagesData.operatingentity );
//	  return ;
// }
 var rowid = (new Date()).valueOf();
 ind = shiptoGrid.getRowsNum();
//	charges[rowKey].rowidd = thisrow.idd;
	  
	  count = 0 ;
	  shipToStateAbbrev[ rowid ] = buildShiptoStateAbbrev('USA');
	  defaultInventoryGroup[ rowid ] = buildNewIgValudset(defaultops);
	  priceGroupId[ rowid ] = billbuildNewpriceGroupId(defaultops);
	var thisrow = shiptoGrid.addRow(rowid,"",ind);
    alertthis = true;
	  shiptoGrid.cells(rowid, count++).setValue('Y');
	  shiptoGrid.cells(rowid, count++).setValue('');
	  shiptoGrid.cells(rowid, count++).setValue('');
	  shiptoGrid.cells(rowid, count++).setValue('');
	  shiptoGrid.cells(rowid, count++).setValue('');
	  shiptoGrid.cells(rowid, count++).setValue('');
	  shiptoGrid.cells(rowid, count++).setValue('');
	  shiptoGrid.cells(rowid, count++).setValue('');
	  shiptoGrid.cells(rowid, count++).setValue('AL');
	  shiptoGrid.cells(rowid, count++).setValue('USA');
	  shiptoGrid.cells(rowid, count++).setValue('');
// opsentityid
	  shiptoGrid.cells(rowid, count++).setValue(defaultops);
	  shiptoGrid.cells(rowid, count++).setValue(0);

	  var sname = '<input readonly class="inputBox" size="15" id="salesAgentName_'+rowid+'" value=""/><input type="button" class="lookupBtn" onmouseover="this.className='+"'"+'lookupBtn lookupBtnOver'+"'"+'" onmouseout="this.className='+"'"+'lookupBtn'+"'"+'" id="sLookupButton" value="" onclick="getShipToSalesAgent('+rowid+')"/>' +
	  '<input type="button" class="smallBtns" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" name="None" value="'+messagesData.clear+'"  OnClick="clearAgent('+rowid+')"/>';
	  var fname = '<input readonly class="inputBox" size="15" id="fieldSalesRepName_'+rowid+'" value=""/><input type="button" class="lookupBtn" onmouseover="this.className='+"'"+'lookupBtn lookupBtnOver'+"'"+'" onmouseout="this.className='+"'"+'lookupBtn'+"'"+'" id="fLookupButton" value="" onclick="getShipToFieldSalesRep('+rowid+')"/>'+
	  '<input type="button" class="smallBtns" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" name="None" value="'+messagesData.clear+'"  OnClick="clearField('+rowid+')"/>';


	  shiptoGrid.cells(rowid, count++).setValue(0);
	  shiptoGrid.cells(rowid, count++).setValue(0);
	  
	  shiptoGrid.cells(rowid, count++).setCValue(sname);
	  shiptoGrid.cells(rowid, count++).setCValue(fname);

	// price grouop id
	  shiptoGrid.cells(rowid, count++).setValue('Y');

	  shiptoGrid.cells(rowid, count++).setValue('');
	  shiptoGrid.cells(rowid, count++).setValue($v('customerRequestId'));
	  shiptoGrid.cells(rowid, count++).setValue($v('customerId'));
	  shiptoGrid.cells(rowid, count++).setValue($v('companyId'));
	  shiptoGrid.cells(rowid, count++).setValue('New'); // new
	  shiptoGrid.cells(rowid, count++).setValue(''); // note
	  shiptoGrid.cells(rowid, count++).setValue(''); // msds
	  shiptoGrid.cells(rowid, count++).setValue(''); // jdeShipTo
	  shiptorowids[""+rowid] = rowid;
	  shiptoSelectedRowId = rowid;
	  shiptoGrid.selectRow(shiptoGrid.getRowIndex(rowid));
	  $('newShiptoRemoveLine').style.display="";
	  shipCountryChanged(rowid,"shipToCountryAbbrev");
}

function shiptoSelectRow()
{
 rightClick = false;
 rowId0 = arguments[0];
 colId0 = arguments[1];
 ee     = arguments[2];

 oldRowId = rowId0;

 if( ee != null ) {
 		if( ee.button != null && ee.button == 2 ) rightClick = true;
 		else if( ee.which == 3  ) rightClick = true;
 }

 shiptoSelectedRowId = rowId0;
 
} //end of method

shipConfig.submitDefault = true;
shipConfig.divName = 'CustomerShiptoAddRequestBean';
shipConfig.rowSpan = true;
shipConfig.beanGrid = 'shiptoGrid';
shipConfig.onRowSelect = shiptoSelectRow;

function ContactUpdate(act,val) {
	  if( !act ) act = 'uAction';
	  if( !val ) val = 'saveContact';
    $(act).value = val;
	  //showPleaseWait();
    haasGrid.parentFormOnSubmit(); //prepare grid for data sending
    ShiptoPrepareForm();
    document.genericForm.submit();
    return false;
}

var selectedRowId = null;
var rowids = new Array();

function loadrowids() {
	var up = jsonData.rows.length
	for(i=1;i<= up; i++)
		rowids[""+i] = i;
}

function removeNewContactsLine() {
	haasGrid.deleteRow(selectedRowId);
	delete( rowids[selectedRowId] ) ;
	if( haasGrid.getRowsNum() == 0 ) 
		$('newContactRemoveLine').style["display"] = "none";
	return ;
}

function addNewContactsLine(rowKey,orderQuantityRule) {

 var rowid = (new Date()).valueOf();
 ind = haasGrid.getRowsNum();
//	charges[rowKey].rowidd = thisrow.idd;
	  
	  count = 0 ;
    var thisrow = haasGrid.addRow(rowid,"",ind);
    alertthis = true;
	  haasGrid.cells(rowid, count++).setValue('Y');
	  haasGrid.cells(rowid, count++).setValue('');
	  haasGrid.cells(rowid, count++).setValue('');
	  haasGrid.cells(rowid, count++).setValue('purchasing');
	  haasGrid.cells(rowid, count++).setValue('');
	  haasGrid.cells(rowid, count++).setValue('');
	  haasGrid.cells(rowid, count++).setValue('');
	  haasGrid.cells(rowid, count++).setValue('');
	  haasGrid.cells(rowid, count++).setValue('');
//
	  haasGrid.cells(rowid, count++).setValue(false);
	  haasGrid.cells(rowid, count++).setValue(false);
	  haasGrid.cells(rowid, count++).setValue(false);
	  haasGrid.cells(rowid, count++).setValue(false);
	  haasGrid.cells(rowid, count++).setValue(false);
	  haasGrid.cells(rowid, count++).setValue(false);
	  haasGrid.cells(rowid, count++).setValue($v('customerRequestId'));
	  haasGrid.cells(rowid, count++).setValue($v('customerId'));
	  haasGrid.cells(rowid, count++).setValue('0');
	  haasGrid.cells(rowid, count++).setValue($v('companyId'));
	  haasGrid.cells(rowid, count++).setValue('New'); // new
	  rowids[""+rowid] = rowid;
	  selectedRowId = rowid;
	  haasGrid.selectRow(haasGrid.getRowIndex(rowid));
	  $('newContactRemoveLine').style.display="";
}

function checkOne(rowId) {
	for( row in rowids ) {
		if( !$("defaultContact"+rowId).disabled && $("defaultContact"+rowId).checked && row != rowId && $("defaultContact"+row).checked) {
			$("defaultContact"+row).checked = false;
			updateHchStatusA("defaultContact"+row);
		}
	}  
}

function selectRow()
{
 rightClick = false;
 rowId0 = arguments[0];
 colId0 = arguments[1];
 ee     = arguments[2];

 oldRowId = rowId0;

 if( ee != null ) {
 		if( ee.button != null && ee.button == 2 ) rightClick = true;
 		else if( ee.which == 3  ) rightClick = true;
 }

 selectedRowId = rowId0;
 
} //end of method

_gridConfig.submitDefault = true;
_gridConfig.divName = 'CustomerContactAddRequestBean';
_gridConfig.rowSpan = true;
//_gridConfig.beanGrid = 'newContactsGrid';
_gridConfig.onRowSelect = selectRow;


var carrierConfig = {
		divName:'beanData', // the div id to contain the grid of the data.
		beanData:'carrierData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'carrierGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'carrierconfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
								// remember to call haasGrid.parentFormOnSubmit() before actual submit.
		onRowSelect:null,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:null,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	    singleClickEdit:false //This is for single click edidting. If it is set to true, txt column type will pop a txt editing box on sligne click. 
//		onBeforeSorting:_onBeforeSorting
	};

function CarrierUpdate(act,val) {
	  if( !act ) act = 'uAction';
	  if( !val ) val = 'saveCarrier';
    $(act).value = val;
	  //showPleaseWait();
    carrierGrid.parentFormOnSubmit(); //prepare grid for data sending
    document.genericForm.submit();
    return false;
}

function CarrierPrepareForm(act,val) {
	  if( !act ) act = 'uAction';
	  if( !val ) val = 'saveCarrier';
  $(act).value = val;
	  //showPleaseWait();
  carrierGrid.parentFormOnSubmit(); //prepare grid for data sending
  //document.genericForm.submit();
  return false;
}

function BillToPrepareForm(act,val) {
	  if( !act ) act = 'uAction';
	  if( !val ) val = 'saveBill';
$(act).value = val;
	  //showPleaseWait();
billGrid.parentFormOnSubmit(); //prepare grid for data sending
//document.genericForm.submit();
return false;
}

var carrierSelectedRowId = null;
var carrierrowids = new Array();

function loadcarrierrowids() {
	var up = carrierData.rows.length;
	for(i=1;i<= up; i++)
		carrierrowids[""+i] = i;
}

function removeCarrierLine() {
	carrierGrid.deleteRow(carrierSelectedRowId);
	delete( carrierrowids[carrierSelectedRowId] ) ;
	if( carrierGrid.getRowsNum() == 0 ) 
		$('newCarrierRemoveLine').style["display"] = "none";
	return ;
}

function addNewCarriersLine(rowKey,orderQuantityRule) {

//	 if( !$v('opsEntityId') ) {
//		  alert(messagesData.pleaseselect + " : " + messagesData.operatingentity );
//		  return ;
//	 }

 var rowid = (new Date()).valueOf();
 ind = carrierGrid.getRowsNum();
//	charges[rowKey].rowidd = thisrow.idd;
	  
	  count = 0 ;
    var thisrow = carrierGrid.addRow(rowid,"",ind);
    alertthis = true;
	  carrierGrid.cells(rowid, count++).setValue('Y');
	  carrierGrid.cells(rowid, count++).setValue('');
	  var sname = '<input readonly class="inputBox" size="15" id="carrier_'+rowid+'" value=""/><input type="button" class="lookupBtn" onmouseover="this.className='+"'"+'lookupBtn lookupBtnOver'+"'"+'" onmouseout="this.className='+"'"+'lookupBtn'+"'"+'" id="carrierLookupButton" value="" onclick="getCarrier('+rowid+')"/>';
	  carrierGrid.cells(rowid, count++).setCValue(sname);
	  carrierGrid.cells(rowid, count++).setValue('');
	  carrierGrid.cells(rowid, count++).setValue('');
	  carrierGrid.cells(rowid, count++).setValue('');
	  carrierGrid.cells(rowid, count++).setValue('ALL');
	  carrierGrid.cells(rowid, count++).setValue('');
	  carrierGrid.cells(rowid, count++).setValue($v('customerRequestId'));
	  carrierGrid.cells(rowid, count++).setValue($v('customerId'));
	  carrierGrid.cells(rowid, count++).setValue($v('companyId'));
	  carrierGrid.cells(rowid, count++).setValue('New'); // new
	  carrierrowids[""+rowid] = rowid;
	  carrierSelectedRowId = rowid;
	  carrierGrid.selectRow(carrierGrid.getRowIndex(rowid));
	  $('newCarrierRemoveLine').style.display="";
}

function carrierSelectRow()
{
 rightClick = false;
 rowId0 = arguments[0];
 colId0 = arguments[1];
 ee     = arguments[2];

 oldRowId = rowId0;

 if( ee != null ) {
 		if( ee.button != null && ee.button == 2 ) rightClick = true;
 		else if( ee.which == 3  ) rightClick = true;
 }

 carrierSelectedRowId = rowId0;
 
} //end of method

carrierConfig.submitDefault = true;
carrierConfig.divName = 'CustomerCarrierAddRequestBean';
carrierConfig.rowSpan = true;
carrierConfig.beanGrid = 'carrierGrid';
carrierConfig.onRowSelect = carrierSelectRow;

var transportationMode = new Array(
		{text:'Parcel',value:'Parcel'},
		{text:'LTL',value:'LTL'},
		{text:'TL',value:'TL'},
		{text:'ML',value:'ML'}
		);

var carrierMethod = new Array(
		{text:'Ground',value:'Ground'},
		{text:'Sea',value:'Sea'},
		{text:'Air',value:'Air'}
		);

var inventoryGroup = new Array(
		{text:'Please select',value:''}
		);


function myResize() {
//	$('CustomerCarrierAddRequestBean').style.width = ( top.offsetHeight -60 ) +"px";//
//	$('CustomerShiptoAddRequestBean').style.width = ( top.offsetHeight -60 ) +"px";//
//	$('CustomerContactAddRequestBean').style.width = ( top.offsetHeight -60 ) +"px";//
}

function gridValidate() {
//	grid validating
	var errorMsg = "";
	
	if(!isFloat($v("shelfLifeRequired"),true)) {
		errorMsg += "\n"+messagesData.defaultshelflife;
	}
	
	if(!isFloat($v("creditLimit"),true)) {
		errorMsg += "\n"+messagesData.creditlimit;
	}
	
	if(!isFloat($v("overdueLimit"),true)) {
		errorMsg += "\n"+messagesData.graceperiod;
	}
	
	if( !isFloat($v('jdeCustomerBillTo'),true)) {
    	alert( messagesData.jdecustomerbillto +":\n"+messagesData.mustbeanumber );
    	$("jdeCustomerBillTo").focus();
    	return false;
	}
	
	if(	shiptoGrid.getRowsNum() != 0 ) {
		var hasloc = true;
		var hasadd = true;
		var hascity = true;
		var hasinv = true;
		var hasstate = true;
		for( rowId in shiptorowids ) {
			if( !gridCellValue(shiptoGrid,rowId,"locationDesc") )
				hasloc = false;
			if( !gridCellValue(shiptoGrid,rowId,"shipToAddressLine1") )
				hasadd = false;
			if( !gridCellValue(shiptoGrid,rowId,"shipToCity") ) 
				hascity = false;
			var st = $("shipToStateAbbrev"+rowId);
			var stv = gridCellValue(shiptoGrid,rowId,"shipToStateAbbrev");
			var c1 = $("shipToCountryAbbrev"+rowId);
			if( c1 == 'USA' || c1 == 'CAN' )
			if(  st.options.length > 1 && ( stv == null || st.value.trim().length == 0 ) ) {
				hasstate = false;
				  //errorMsg += "\n"+messagesData.stateProvinceRequired;
			}
			if( !gridCellValue(shiptoGrid,rowId,"defaultInventoryGroup") ) 
				hasinv = false;
		}
		var shiptof = '';
		var sep = '';
		if( !hasloc ) {
//			label.locationdesc			
			shiptof += sep + messagesData.locationdesc ;
			sep = ',';
		}
		if( !hasadd ) {
//			label.addressline1			
			shiptof += sep + messagesData.addressline1 ;
			sep = ',';
		}
		if( !hascity ) {
//			label.city			
			shiptof += sep + messagesData.cityRequired ;
		}
		if( !hasstate ) {
//			  label.city			
			shiptof += sep + messagesData.stateProvinceRequired ;
		}
		if( !hasinv ) {
//			  label.city			
			shiptof += sep + messagesData.invRequired ;
		}

		if( shiptof )
//			label.shipto
			errorMsg += "\n"+messagesData.shipto +":" +shiptof;
		
	}

	if( haasGrid.getRowsNum() != 0 ) { 
		var hasloc = true;
		var hasadd = true;
		for( rowId in rowids ) {
			if( !gridCellValue(haasGrid,rowId,"lastName") ) {
				hasloc = false;
			}
			if( !gridCellValue(haasGrid,rowId,"phone") )
				hasadd = false;
		}
		var contactf = '';
		var sep = '';
		if( !hasloc ) {
//			label.lastname			
			contactf += sep + messagesData.lastname ;
			sep = ',';
		}
		if( !hasadd ) {
//			label.phone			
			contactf += sep + messagesData.phoneRequired ;
			sep = ',';
		}

		if( contactf ) {
//			label.contact
			errorMsg += "\n"+messagesData.contact +":" + contactf;
		}

	}

	if( carrierGrid.getRowsNum() != 0 ) {
		var hasloc = true;
		for( rowId in carrierrowids ) 
			if( !gridCellValue(carrierGrid,rowId,"carrierName") )
			{ hasloc = false; break; }
		if( !hasloc ) {
//			label.carrier
//			label.carriername
			errorMsg += "\n"+messagesData.carrier +":" + messagesData.carriername;
		}
	}
	if( errorMsg != '' ) {
		  alert(messagesData.validvalues+errorMsg);
		  return false;
	}
	return true;
}

// different validate on diff stage.
function saveCustomerRequest() {
	var flag = validateForm();
	if(flag) {
		haasGrid.parentFormOnSubmit(); //prepare contact grid for data sending
		ShiptoPrepareForm();
		CarrierPrepareForm();
		BillToPrepareForm();
		TaxPrepareForm();
		var action = document.getElementById("uAction");
		action.value = "save";
		submitOnlyOnce();
		document.genericForm.submit();
	}
}

function rejectCustomerRequest() {
	//var flag = validateForm();
	//if(flag) {
	var action = document.getElementById("uAction");

	var reasonForRejection = document.getElementById("reasonForRejection");
	var rejectComment = "";    
	rejectComment = prompt(messagesData.commentesrejection ,"");
	if (rejectComment == null) {	//user hit cancel
		return false;
	}

	/*var rejectComment = prompt("Enter Comments for Rejection:" ,"");*/
	reasonForRejection.value = rejectComment;

	haasGrid.parentFormOnSubmit(); //prepare contact grid for data sending
	ShiptoPrepareForm();
	CarrierPrepareForm();
	BillToPrepareForm();
	TaxPrepareForm();
	
	action.value = "reject";
	submitOnlyOnce();
	document.genericForm.submit();
	//}
}

function approveCustomerRequest() {
	var flag = validateForm("approvestage");
	if( !$('checknew').checked && $('sapCustomerCode').value == '' )  {
		alert(messagesData.sapcoderequired);
		return;
	}

	if(flag) {
		var action = document.getElementById("uAction");

		haasGrid.parentFormOnSubmit(); //prepare contact grid for data sending
		ShiptoPrepareForm();
		CarrierPrepareForm();
		BillToPrepareForm();
		TaxPrepareForm();
		
		action.value = "approve";
		submitOnlyOnce();
		document.genericForm.submit();
	}
}

function approveNewCompanyCustomerRequest() {
	var flag = validateForm();
	if(flag) {
		var action = document.getElementById("uAction");

		haasGrid.parentFormOnSubmit(); //prepare contact grid for data sending
		ShiptoPrepareForm();
		CarrierPrepareForm();
		BillToPrepareForm();
		TaxPrepareForm();
		
		action.value = "approvenewcompany";
		submitOnlyOnce();
		document.genericForm.submit();
	}
}

//different validate on diff stage.
function save() {
	var flag = gridValidate();
	
	if(flag == true)
	{
		var autoEmailAddresses = $v('autoEmailAddress');	
		var listOfEmails = autoEmailAddresses.split(',');
		for (var i = 0;i < listOfEmails.length;++i)
		{	
			if( !isEmail(listOfEmails[i].trim(),true)) {
				alert(messagesData.einvoiceemailaddressinvalid);
				$("autoEmailAddress").focus();
				flag = false;
				break;
			}
		}
	}
	
	if(flag) {
		haasGrid.parentFormOnSubmit(); //prepare contact grid for data sending
		ShiptoPrepareForm();
		CarrierPrepareForm();
		BillToPrepareForm();
		TaxPrepareForm();
		
		var action = document.getElementById("uAction");
		action.value = "save";
		try { $('newCompanyId').disabled = false; } catch(ex){}
		$('companyId').value = toIdValue($v('companyId'));
		submitOnlyOnce();
		document.genericForm.submit();
	}
}

function submitdraft() {
	var flag = validateForm();
	if(flag) {
		haasGrid.parentFormOnSubmit(); //prepare contact grid for data sending
		ShiptoPrepareForm();
		CarrierPrepareForm();
		BillToPrepareForm();
		TaxPrepareForm();
		
		var action = document.getElementById("uAction");
		action.value = "submitdraft";
		$('newCompanyId').disabled = false;
		submitOnlyOnce();
		document.genericForm.submit();
	}
}

function deletedraft() {
	var flag = true;// no need to check
	if(flag) {
		if( !confirm(messagesData.confirmdelete) ) return;
		
		haasGrid.parentFormOnSubmit(); //prepare contact grid for data sending
		ShiptoPrepareForm();
		CarrierPrepareForm();
		BillToPrepareForm();
		TaxPrepareForm();
		
		var action = document.getElementById("uAction");
		action.value = "deletedraft";
		submitOnlyOnce();
		document.genericForm.submit();
	}
}

///// opsentity->inventory stuff here.
var ddddInventoryGroup = new Array(
		{text:'Please select',value:''}
		);
var opsEntityId = new Array();
var defaultInventoryGroup = new Array()
var priceGroupId = new Array();

/*
function buildNewpriceGroupId(opsid) {
//	var opsid = $v('opsEntityId');
	var newInvArray = new Array();
	if( !opsid ) newInvArray = ddddInventoryGroup;
	for( i=0;i < opspg.length; i++) {
		if( opspg[i].id == opsid ) {
			var hubcoll = opspg[i].coll;
			for( j = 0;j< hubcoll.length;j++ ){
				newInvArray[newInvArray.length] = {text:hubcoll[j].name,value:hubcoll[j].id};
			}	
		}
	}
	return newInvArray;
}
*/
function buildNewIgValudset(opsid) {
//	var opsid = $v('opsEntityId');
   	var newInvArray = new Array();
   		newInvArray[newInvArray.length] = {text:messagesData.pleaseselect,value:''};
   	if( !opsid ) return newInvArray;

	for( i=0;i < opshubig.length; i++) {
		if( opshubig[i].id == opsid ) {
			var hubcoll = opshubig[i].coll;
			for( j = 0;j< hubcoll.length;j++ ){
				var igcoll = hubcoll[j].coll;
				for( k=0;k< igcoll.length;k++ ){
					newInvArray[newInvArray.length] = {text:igcoll[k].name,value:igcoll[k].id};
				}
			}	
		}
	}
	return newInvArray;
	//defaultInventoryGroup = newInvArray;
	//inventoryGroup = newInvArray;
	if( !beginning ) { 
		if(	shiptoGrid.getRowsNum() != 0 || carrierGrid.getRowsNum() != 0 ) { 
			for( rowId in shiptorowids ) {
					  var elename =	"defaultInventoryGroup"+rowId;
					  var inv = $(elename);
					  for (var i = inv.length; i > 0; i--) {
						  inv[i] = null;
					  }
					  if(defaultInventoryGroup.length == 0) {
					    setOption(0,messagesData.pleaseselect,"", elename);
					  }
					  for (var i=0; i < defaultInventoryGroup.length; i++) {
					    setOption(i,defaultInventoryGroup[i].text,defaultInventoryGroup[i].value, elename);
					  }
			}
//			for( rowId in carrierrowids ) {
//				  var elename =	"inventoryGroup"+rowId;
//				  var inv = $(elename);
//				  for (var i = inv.length; i > 0; i--) {
//					  inv[i] = null;
//				  }
//				  if(inventoryGroup.length == 0) {
//				    setOption(0,messagesData.pleaseselect,"", elename);
//				  }
//				  for (var i=0; i < inventoryGroup.length; i++) {
//				    setOption(i,inventoryGroup[i].text,inventoryGroup[i].value, elename);
//				  }
//			}			
		}
	}
}

function showgridinv() {
	return ;
	if( pageReadOnly ) return ; // value already shown.
	for( rowId in shiptorowids) 
		shipOpsChanged(rowId,"opsEntityId",gridCellValue(shiptoGrid,rowId,"defaultInventoryGroup"),gridCellValue(shiptoGrid,rowId,"priceGroupId"));
}

var defaultops = '';

function setdefaultops() {

	for( i=0;i < opshubig.length; i++) { 
		defaultops = opshubig[i].id;
		break;
	}
	for( i=0;i < opshubig.length; i++) { 
		opsEntityId[i] = {text:opshubig[i].name,value:opshubig[i].id}
	}
	
}

function getShipToSalesAgent( rowId ) {
	  loc = "searchpersonnelmain.do?fixedCompanyId=Y&callbackparam=agent_"+rowId;   
	  openWinGeneric(loc,"getShipToSalesAgent","600","450","yes","50","50","20","20","no");
}
function getShipToFieldSalesRep( rowId ) {
	  loc = "searchpersonnelmain.do?fixedCompanyId=Y&callbackparam=field_"+rowId;
	  openWinGeneric(loc,"getShipToFieldSalesRep","600","450","yes","50","50","20","20","no");
}
function clearAgent( rowId ) {
	gridCell(shiptoGrid,rowId,"salesAgentId").setValue('');
	$('salesAgentName_'+rowId).value = '';
	return false;
}
function clearField( rowId ) {
	gridCell(shiptoGrid,rowId,"fieldSalesRepId").setValue('');
	$('fieldSalesRepName_'+rowId).value = '';
}

function getValidCountryAbbrev() {
	var cArray = new Array();
	for(i=0;i<shipToCountryAbbrev.length;i++) {
		for(j=0;j<validTaxCountryAbbrev.length;j++) {
			if( shipToCountryAbbrev[i].value == validTaxCountryAbbrev[j] ) {
				cArray[cArray.length] = {text:shipToCountryAbbrev[i].text,value:shipToCountryAbbrev[i].value};
			}
		}
	}
	return cArray;
}


var taxConfig = {
		divName:'CustomerTaxAddRequestBean', // the div id to contain the grid of the data.
		beanData:'taxData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'taxGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'taxconfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not, disable smart rendering.
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
								// remember to call haasGrid.parentFormOnSubmit() before actual submit.
		onRowSelect:taxSelectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:null,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	    singleClickEdit:false //This is for single click edidting. If it is set to true, txt column type will pop a txt editing box on sligne click. 
//		onBeforeSorting:_onBeforeSorting
	};

function TaxPrepareForm(act,val) {
		  if( !act ) act = 'uAction';
		  if( !val ) val = 'saveTax';
	  $(act).value = val;
		  //showPleaseWait();
	  taxGrid.parentFormOnSubmit(); //prepare grid for data sending
	  //document.genericForm.submit();
	  return false;
	}

var taxSelectedRowId = null;
var taxrowids = new Array();

function loadtaxrowids() {
	var up = taxData.rows.length
	for(i=1;i<= up; i++)
		taxrowids[""+i] = i;
}

function showtaxgridstates() {
	//return ;
	if( pageReadOnly ) return ; // value already shown.
	for( rowId in taxrowids)
		taxCountryChanged(rowId,"countryAbbrev",gridCellValue(taxGrid,rowId,"stateAbbrev"));
}

function removeTaxLine() {
	taxGrid.deleteRow(taxSelectedRowId);
	delete( taxrowids[taxSelectedRowId] ) ;
	if( taxGrid.getRowsNum() == 0 ) 
		$('newTaxRemoveLine').style["display"] = "none";
	return ;
}

function addNewTaxLine(rowKey,orderQuantityRule) {

// if( !$v('opsEntityId') ) {
//	  alert(messagesData.pleaseselect + " : " + messagesData.operatingentity );
//	  return ;
// }
 var rowid = (new Date()).valueOf();
 ind = taxGrid.getRowsNum();
//	charges[rowKey].rowidd = thisrow.idd;
	  
	  count = 0 ;
	  stateAbbrev[ rowid ] = buildStateAbbrev('USA');
	var thisrow = taxGrid.addRow(rowid,"",ind);
	  taxGrid.cells(rowid, count++).setValue('Y');
	  taxGrid.cells(rowid, count++).setValue('USA');
	  taxGrid.cells(rowid, count++).setValue('AL');
	  taxGrid.cells(rowid, count++).setValue('');
	  taxGrid.cells(rowid, count++).setValue('');
	  taxGrid.cells(rowid, count++).setValue($v('customerRequestId'));
	  taxGrid.cells(rowid, count++).setValue($v('customerId'));
	  taxGrid.cells(rowid, count++).setValue($v('companyId'));
	  taxGrid.cells(rowid, count++).setValue('New'); // new
	  taxrowids[""+rowid] = rowid;
	  taxSelectedRowId = rowid;
	  taxGrid.selectRow(taxGrid.getRowIndex(rowid));
	  $('newTaxRemoveLine').style.display="";
	  taxCountryChanged(rowid,"countryAbbrev");
}

function taxSelectRow()
{
 rightClick = false;
 rowId0 = arguments[0];
 colId0 = arguments[1];
 ee     = arguments[2];

 oldRowId = rowId0;

 if( ee != null ) {
 		if( ee.button != null && ee.button == 2 ) rightClick = true;
 		else if( ee.which == 3  ) rightClick = true;
 }

 taxSelectedRowId = rowId0;
 
} //end of method

function taxCountryChanged(rowId,columnId,stateval) {
	
	  var selectedCountry = gridCellValue(taxGrid,rowId,columnId);
	  var state0 = $("stateAbbrev"+rowId);
	  for (var i = state0.length; i > 0; i--) {
	    state0[i] = null;
	  }
	  var selectedIndex = 0 ;
	  var stateArray = altState[selectedCountry];
	  var stateNameArray = altStateName[selectedCountry];
	  
	  if (selectedCountry == "USA" || selectedCountry == "CAN") {
		  if(stateArray.length == 0) {
		    setOption(0,messagesData.all,"", "stateAbbrev"+rowId)
		  }
		
		  for (var i=0; i < stateArray.length; i++) {
		    setOption(i,stateNameArray[i],stateArray[i], "stateAbbrev"+rowId);
		    if( stateArray[i] == stateval ) selectedIndex = i;
		  }
		  state0.selectedIndex = selectedIndex;
		  return;
	  }
	  
	  setOption(0,messagesData.all,"All", "stateAbbrev"+rowId);
}

function buildStateAbbrev(selectedCountry) {
	  var stateArray = altState[selectedCountry];
	  var stateNameArray = altStateName[selectedCountry];
	  var stateGridArray = new Array();
	  
	  if( stateArray.length == 0) {
	    stateGridArray[0] = {text:messagesData.all,value:''};
	  }
	  
	  if (stateArray.length != 1 || stateArray[0] ) 
		  stateGridArray[0] = {text:messagesData.pleaseselect,value:''};
		  
	  for (var i=0; i < stateArray.length; i++) {
		  stateGridArray[stateGridArray.length] = {text:stateNameArray[i],value:stateArray[i]};
	  }
	  
	  return stateGridArray;
}

