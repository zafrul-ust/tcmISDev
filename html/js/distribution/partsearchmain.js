windowCloseOnEsc = true;

var children = new Array(); 

function submitSearchForm()
{
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  
  //document.getElementById("selectedPart").innerHTML = "";

  if(validateSearchForm()) { 
   $('uAction').value = 'search';
   document.genericForm.target='resultFrame';
   closeAllchildren();
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function validateSearchForm()
{
$("selectedInventoryGroup").value = $("inventoryGroup").options[$("inventoryGroup").selectedIndex].value;

var errorMessage = messagesData.validvalues + "\n\n";
var errorCount = 0;
/*
try
{
 var searchCustomerIdArgument = document.getElementById("searchCustomerIdArgument");

 if ( searchCustomerIdArgument.value.trim().length != 0 && (!(isSignedInteger(searchCustomerIdArgument.value.trim())) || searchCustomerIdArgument.value*1 == 0 ) )
 {
    errorMessage +=  messagesData.customerid + "\n";
    errorCount = 1;
    receiptId.value = "";
 }
 } catch (ex) {
  errorCount++;
}

 if (errorCount >0)
 {
    alert(errorMessage);
    return false;
 }
*/
 return true;
}

var selectedpartNumber = "";
var selecteddescription = "";
var selectedcustomerPart = "";
var selectedhazardous = "";
var selectedspecification = "";
var selectednumberOfOrders = "";
var selectedlastOrdered = "";
var selectedlastPrice = "";
var selectedorderQty = "";
var selectedcatalogPrice = "";
var selectedexpectedCost = "";
var selectedmargin = "";
var selectedwarehouse = "";
var selectedregion = "";
var selectedglobal = "";
var selectedItemId = "";
var selectedExpectedCost = "";
var selectedInventoryGroup = "";
var selectedSpecListConcat = "";
var selectedDetailConcat = "";
var selectedSpecLibraryList = "";
var selectedCocConcat = "";
var selectedCoaConcat = "";
var selectedUnitOfMeasure = "";
var selectedUnitsPerItem = "";

var parts = new Array();
var windowAction;

function selectedPartNumberCall()
{ 	
  try {
	  var openervalueElementId = opener.document.getElementById(valueElementId);
	  if (openervalueElementId)
		  openervalueElementId.value = selectedpartNumber;
  } catch( ex ) {}
  try {
	  var openerdisplayElementId = opener.document.getElementById(displayElementId);
	  if (openerdisplayElementId) {
		  openerdisplayElementId.className = "inputBox";
		  openerdisplayElementId.value = selectedpartNumber;
	  }
  } catch( ex ) {}
  try {
	  var openerdisplayArea = opener.document.getElementById($('displayArea').value); 
	  if (openerdisplayArea) {
		  openerdisplayArea.value = selectedpartNumber;
	  }
  } catch( ex ) {}
 
  try {
	  if( window.opener.addPartNumber ) {
	    addNewPartToCart('Y');
	  }
	  if( window.opener.returnPartNumber ) {
	    var p = new partInfo();
	  	window.opener.returnPartNumber(p); 
	    window.close();
	  }
  } catch(exx) {}
 
//  window.close();
}

/*
function returnSelectedPartNumber()
{ 	
	 var elemValue = $v('valElementId');	
	 var elem2Value = $v('valElement2Id');
	 var elem3Value = $v('valElement3Id');
	 
	 var openerElemen = window.opener.document.getElementById(elemValue);
	 openerElemen.value = returnSelectedValue;	  
	 
	 var openerElemen2 = window.opener.document.getElementById(elem2Value);
	 openerElemen2.value = returnSelectedDesc;
	 
	 var openerElemen3 = window.opener.document.getElementById(elem3Value);
	 if(returnSelectedPrice.length>0)
	 openerElemen3.value = returnSelectedPrice;
	 
	 window.close();
}
*/

function add(selectedWindowAction)
{ 	
	windowAction = "close";
	if (selectedWindowAction != null )
		windowAction = selectedWindowAction;
	
	try {
		  var openervalueElementId = opener.document.getElementById(valueElementId);
		  if (openervalueElementId)
			  openervalueElementId.value = selectedpartNumber;
	  } catch( ex ) {}
	  try {
		  var openerdisplayElementId = opener.document.getElementById(displayElementId);
		  if (openerdisplayElementId) {
			  openerdisplayElementId.className = "inputBox";
			  openerdisplayElementId.value = selectedpartNumber;
		  }
	  } catch( ex ) {}
	  try {
		  var openerdisplayArea = opener.document.getElementById($('displayArea').value); 
		  if (openerdisplayArea) {
			  openerdisplayArea.value = selectedpartNumber;
		  }
	  } catch( ex ) {}
	 
	  try {
		  if( window.opener.addPartNumber ) {
			document.getElementById('resultFrame').contentWindow.getParts();
			if(parts.length < 1)
				alert(messagesData.pleaseSelect);
			else
				addNewPartsToCart('Y');    
		  }
		  if( window.opener.returnPartNumber ) {
		    var p = new partInfo();
		  	window.opener.returnPartNumber(p); 
		    window.close();
		  }
	  } catch(exx) {}
}

function addNewPartsToCart(toServer)
{
	try {
		if(parts.length > 0) {
			showTransitWin();
			var lineItem = $v('lineItem');
			//for(var i=0; i<parts.length; i++) { 
				
			var p = parts[parts.length-1];
		  	
		  	var extPrice = p.catalogPrice*p.orderQty;
		  	extPrice = extPrice.toFixed(2);
		  	var shipComplete = false;
		  	var consolidateShipment = false;
		  	if($v('shipComplete') == 'ORDER') {
		  		shipComplete = true;
		  		consolidateShipment = true;
		  	}
		  	if($v('shipComplete') == 'LINE') {
		  		shipComplete = true;
		  	}  
		  	
		  	callToServer("scratchpadmain.do?uAction=addNewLine&prNumber="+$v('prNumber')+"&companyId="+$v('companyId')+"&toServer="+toServer+
								"&lineItem="+lineItem+"&orderType="+$v('orderType')+"&mvItem="+$v('mvItem')+"&customerPoLine="+
			  					"&labelSpec="+p.partNumber+"&application="+"&facilityId="+$v('facilityId')+"&poNumber="+encodeURIComponent($v('poNumber'))+ 
			  					"&hazardous="+p.hazardous+"&quantity="+p.orderQty+"&currencyId="+$v('currencyId')+"&extPrice="+extPrice+
			  					"&shipToCompanyId="+$v('shipToCompanyId')+"&shipToLocationId="+encodeURIComponent($v('shipToLocationId'))+
			  					"&opsEntityId="+$v('opsEntityId')+"&opsCompanyId="+encodeURIComponent($v('opsCompanyId'))+
			  					"&priceBreakAvailable="+"&margin="+p.margin+"&requiredDatetime="+"&promisedDate="+
			  					"&requiredShelfLife="+$v('requiredShelfLife')+"&taxExempt="+"&shipComplete="+shipComplete+"&consolidateShipment="+consolidateShipment+
			  					"&deliveryType=Deliver%20by"+"&critical="+"&inventoryGroup="+p.inventoryGroup+"&dropShipOverride="+
			  					"&externalNote="+"&internalNote="+"&purchasingNote="+"&customerPartNo="+"&catalogCompanyId=HAAS&catalogId=Global&partGroupNo=1"+
			  					"&unitOfSale="+p.unitOfMeasure+"&unitOfSaleQuantityPerEach="+p.unitsPerItem+"&itemId="+p.itemId+"&expectedUnitCost="+p.expectedCost+
			  					"&specListConcat="+p.specListConcat+"&specDetailConcat="+p.detailConcat+"&specLibraryConcat="+p.specLibraryList+
			  					"&specCocConcat="+p.cocConcat+"&specCoaConcat="+p.coaConcat+
			  					"&catalogPrice="+p.catalogPrice+"&shippingReference=&requestLineStatus=");  
			  	
		  	lineItem++;

		  	document.getElementById('lineItem').value = lineItem;
			parts.pop();		
		}
		else{
			closeTransitWin();
			if(windowAction == "close"){
			    window.opener.closeTransitWin();
				window.close();
			}
			else {
				//var checkall = $("chkAllOkDoUpdate");
				//checkall.checked = false;
				// calling checkall with the check all box set to false unchecks all boxes
				document.getElementById('resultFrame').contentWindow.clearAll();
			}
		}
		
  	} catch(exx) {}
  	

}

function addNewPartToCart(toServer)
{
	showTransitWin();
	try {
		var p = new partInfo();
	  	
	  	var extPrice = p.catalogPrice*p.orderQty;
	  	extPrice = extPrice.toFixed(2);
	  	var shipComplete = false;
	  	var consolidateShipment = false;
	  	if($v('shipComplete') == 'ORDER') {
	  		shipComplete = true;
	  		consolidateShipment = true;
	  	}
	  	if($v('shipComplete') == 'LINE') {
	  		shipComplete = true;
	  	}  

	  	callToServer("scratchpadmain.do?uAction=addNewLine&prNumber="+$v('prNumber')+"&companyId="+$v('companyId')+"&toServer="+toServer+
						"&lineItem="+$v('lineItem')+"&orderType="+$v('orderType')+"&mvItem="+$v('mvItem')+"&customerPoLine="+
	  					"&labelSpec="+p.partNumber+"&application="+"&facilityId="+$v('facilityId')+"&poNumber="+encodeURIComponent($v('poNumber'))+ 
	  					"&hazardous="+p.hazardous+"&quantity="+p.orderQty+"&currencyId="+$v('currencyId')+"&extPrice="+extPrice+
	  					"&shipToCompanyId="+$v('shipToCompanyId')+"&shipToLocationId="+encodeURIComponent($v('shipToLocationId'))+
	  					"&opsEntityId="+$v('opsEntityId')+"&opsCompanyId="+encodeURIComponent($v('opsCompanyId'))+
	  					"&priceBreakAvailable="+"&margin="+p.margin+"&requiredDatetime="+"&promisedDate="+
	  					"&requiredShelfLife="+$v('requiredShelfLife')+"&taxExempt="+"&shipComplete="+shipComplete+"&consolidateShipment="+consolidateShipment+
	  					"&deliveryType=Deliver%20by"+"&critical="+"&inventoryGroup="+p.inventoryGroup+"&dropShipOverride="+
	  					"&externalNote="+"&internalNote="+"&purchasingNote="+"&customerPartNo="+"&catalogCompanyId=HAAS&catalogId=Global&partGroupNo=1"+
	  					"&unitOfSale="+p.unitOfMeasure+"&unitOfSaleQuantityPerEach="+p.unitsPerItem+"&itemId="+p.itemId+"&expectedUnitCost="+p.expectedCost+
	  					"&specListConcat="+p.specListConcat+"&specDetailConcat="+p.detailConcat+"&specLibraryConcat="+p.specLibraryList+
	  					"&specCocConcat="+p.cocConcat+"&specCoaConcat="+p.coaConcat+
	  					"&catalogPrice="+p.catalogPrice+"&shippingReference=&requestLineStatus=");  

 /* 		if( window.opener.addPartNumber ) {
			var p = new partInfo();
			window.opener.addPartNumber(p);  
    		closeAllchildren();
    		window.close();
  		} */
  	} catch(exx) {}
}

function partInfo() {
//    call('doOnRowSelected');
    this.partNumber = selectedpartNumber;
	this.description = selecteddescription;
	this.customerPart = selectedcustomerPart; 
	this.hazardous = selectedhazardous; 
	this.specification = selectedspecification;
	this.numberOfOrders = selectednumberOfOrders; 
	this.lastOrdered = selectedlastOrdered; 
	this.lastPrice = selectedlastPrice;
	this.orderQty = selectedorderQty; 
	this.catalogPrice = selectedcatalogPrice; 
	this.expectedCost = selectedexpectedCost;
	this.margin = selectedmargin; 
	this.warehouse = selectedwarehouse; 
	this.region = selectedregion;
	this.global = selectedglobal;
    this.itemId = selectedItemId;
    this.expectedCost = selectedExpectedCost;
    this.inventoryGroup = selectedInventoryGroup;
    this.specListConcat = selectedSpecListConcat;
    this.detailConcat = selectedDetailConcat;
    this.specLibraryList = selectedSpecLibraryList;
    this.cocConcat = selectedCocConcat;
    this.coaConcat = selectedCoaConcat;
    this.unitOfMeasure = selectedUnitOfMeasure;
    this.unitsPerItem = selectedUnitsPerItem;
}

function validateSearchFormXSL()
{
  if( validateSearchForm() ) {
      document.getElementById("uAction").value = "createExcel"; 
		document.genericForm.target='_ContactLookUpExcel';
	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_ContactLookUpExcel','650','600','yes');
		setTimeout("document.genericForm.submit()",300);
	}
}

function closeAllchildren() 
{
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
//	if (document.getElementById("uAction").value != 'new') {
		try {
			for(var n=0;n<children.length;n++) {
				try {
				children[n].closeAllchildren(); 
				} catch(ex){}
			children[n].close();
			}
		} catch(ex){}
		children = new Array(); 
}

function allocatedone() {
	try {
/*		var orderType = opener.$v('orderType'); 
		if( orderType =='Quote' )
			opener.submitSave();
		else if( orderType =='MR' )
			opener.saveMR();
		else  */
//			opener.submitSave();
		addNewPartToCart('N');
		closeAllchildren();
		
	}catch(ex){}
}

/*
function newMaterial() {
	var loc = "newcatalogitem.do?uAction=new&inventoryGroup=" + $v("inventoryGroup");
	var winId= 'newCatalogItem'+$v("prNumber");
	children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"900","590","yes","50","50","20","20","no"); 
}

function newPackaging() {
	var loc = "newcatalogitem.do?uAction=new&inventoryGroup=" + $v("inventoryGroup")+"&itemId="+selectedItemId;
	var winId= 'newCatalogItem'+$v("prNumber");
	children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"900","590","yes","50","50","20","20","no");
}
*/
function searchGlobalCatalog() {
	showTransitWin();
	var tmpArray = new Array;
	var count = 0;
	var tmpText = $v("partNumber");
	if (tmpText.length > 0) {
		tmpArray[count++] = tmpText;
	}
	tmpText = $v("customerPartNumber");
	if (tmpText.length > 0) {
		tmpArray[count++] = tmpText;
	}
	tmpText = $v("partDesc");
	if (tmpText.length > 0) {
		tmpArray[count++] = tmpText;
	}
	tmpText = $v("text");
	if (tmpText.length > 0) {
		tmpArray[count++] = tmpText;
	}
	tmpText = $v("specification");
	if (tmpText.length > 0) {
		tmpArray[count++] = tmpText;
	}
	tmpText = $v("alternate");
	if (tmpText.length > 0) {
		tmpArray[count++] = tmpText;
	}
	//loop thru array
	var searchText = "";
	for (var i=0; i < tmpArray.length; i++) {
		if(i == 0) {
			searchText = tmpArray[i];
		}else {
			searchText += " or "+ tmpArray[i];
		}
	}

	var loc = "searchglobalitem.do?uAction=search&calledFrom=distribution&inventoryGroup="+$v("inventoryGroup")+"&searchText="+searchText;
	var winId= 'searchGlobalCatalog112121';
	children[children.length] = openWinGeneric(loc,winId,"900","590","yes","50","50","20","20","no");
}

var dhxWins = null;
//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
}

function showTransitWin()
{
	document.getElementById("transitLabel").innerHTML = messagesData.pleasewait;

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);
		dhxWins.window("transitDailogWin").show();
	}
}

