var children = new Array();

/************************************NEW***************************************************/
var mygrid;

/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

function resultOnLoad()
{
	parent.document.getElementById("selectedCustomerSpan").innerHTML = '';
	
	if($v("popup") == 'Y') {  //alert("here");
		windowCloseOnEsc = true; }
	else
	  	windowCloseOnEsc = false; 
	  	 	
	 totalLines = document.getElementById("totalLines").value;
	 
	 if (totalLines > 0)
	 {
	  document.getElementById("customerAddressSearchViewBean").style["display"] = "";
	  
	  initializeGrid();  
	 }  
	 else
	 {
	   document.getElementById("customerAddressSearchViewBean").style["display"] = "none";   
	 }
	
	 displayGridSearchDuration();
	 
	 setResultFrameSize();
}

var rowSpanCols = [0,1,2,3,4,7,8];
function initializeGrid(){
	initGridWithGlobal(gridConfig);
}  




function beforeSelectRow(rowId,cellInd) {
	var creditStatus = cellValue(rowId,'creditStatus');
  
 	if(creditStatus.toUpperCase() == 'STOP') return false;
 	
 	return true;
}

function selectRow(rowId,cellInd){

    var selectedCustomerId = cellValue(rowId,'customerId');
    var customerName = cellValue(rowId,'customerName');
 	
    var selectedCustomerSpan = parent.document.getElementById("selectedCustomerSpan");
	
//	if(cellValue(rowId,'defaultCurrencyId') != null && cellValue(rowId,'defaultCurrencyId') != '') 
   		selectedCustomerSpan.innerHTML = ' | <a href="#" onclick="selectedCustomerCall(); return false;">' + messagesData.selectedcustomershipto + " ("+cellValue(rowId,'locationDesc')+")</a>";
 //  	else
 //   	selectedCustomerSpan.innerHTML = '';
    	
   parent.document.getElementById("selectedCustomerId").value = selectedCustomerId;
   parent.selectedCustomerId = cellValue(rowId,'customerId');
   parent.selectedCustomerName = cellValue(rowId,'customerName');
   parent.selectedCompanyId = cellValue(rowId,'companyId');
   parent.selectedFacilityId = cellValue(rowId,'facilityId');
   parent.selectedSalesAgentId = cellValue(rowId,'salesAgentId'); 
   parent.selectedSalesAgentName = cellValue(rowId,'salesAgentName'); 
   parent.selectedInventoryGroupDefault = cellValue(rowId,'inventoryGroupDefault'); 
   parent.selectedInventoryGroupName = cellValue(rowId,'inventoryGroupName'); 
   parent.selectedFieldSalesRepId = cellValue(rowId,'fieldSalesRepId'); 
   parent.selectedFieldSalesRepName = cellValue(rowId,'fieldSalesRepName'); 
   parent.selectedLocationShortName = cellValue(rowId,'locationShortName');
   parent.selectedLocationDesc = cellValue(rowId,'locationDesc');
   parent.selectedBillToAddress = cellValue(rowId,'billToAddress');
   parent.selectedPaymentTerms = cellValue(rowId,'paymentTerms');
   parent.selectedCreditLimit = cellValue(rowId,'creditLimit');
   parent.selectedOverdueLimit = cellValue(rowId,'overdueLimit'); 
   parent.selectedOverdueLimitBasis = cellValue(rowId,'overdueLimitBasis');
   parent.selectedCreditStatus= cellValue(rowId,'creditStatus');
   parent.selectedCurrencyId = cellValue(rowId,'defaultCurrencyId'); 
   parent.selectedFixedCurrency = cellValue(rowId,'fixedCurrency');
   parent.selectedPriceGroupId = cellValue(rowId,'priceGroupId');
   parent.selectedShelfLifeRequired = cellValue(rowId,'shelfLifeRequired');
   parent.selectedShipComplete = cellValue(rowId,'shipComplete');
   parent.selectedAccountSysId = cellValue(rowId,'accountSysId');
   parent.selectedChargeType = cellValue(rowId,'chargeType');
   parent.selectedShipToAddress = cellValue(rowId,'shipToAddress');  
   parent.selectedShipToLocationId = cellValue(rowId,'shipToLocationId');
   parent.selectedShipToCompanyId = cellValue(rowId,'shipToCompanyId');
   parent.selectedShipToAddressSearch = cellValue(rowId,'shipToAddressSearch');
   parent.selectedShipToAddressLine1Display = cellValue(rowId,'shipToAddressLine1Display');
   parent.selectedShipToAddressLine2Display = cellValue(rowId,'shipToAddressLine2Display');
   parent.selectedShipToAddressLine3Display = cellValue(rowId,'shipToAddressLine3Display');
   parent.selectedShipToAddressLine4Display = cellValue(rowId,'shipToAddressLine4Display');
   parent.selectedShipToAddressLine5Display = cellValue(rowId,'shipToAddressLine5Display');
   parent.selectedBillToLocationId = cellValue(rowId,'billToLocationId');
   parent.selectedBillToCompanyId = cellValue(rowId,'billToCompanyId');
   parent.selectedAddressLine1Display = cellValue(rowId,'addressLine1Display');
   parent.selectedAddressLine2Display = cellValue(rowId,'addressLine2Display');
   parent.selectedAddressLine3Display = cellValue(rowId,'addressLine3Display');
   parent.selectedAddressLine4Display = cellValue(rowId,'addressLine4Display');
   parent.selectedAddressLine5Display = cellValue(rowId,'addressLine5Display');
   parent.selectedNotes = cellValue(rowId,'notes');
   parent.selectedChargeFreight = cellValue(rowId,'chargeFreight');
   parent.selectedOpsCompanyId = cellValue(rowId,'opsCompanyId');
   parent.selectedOpsEntityId = cellValue(rowId,'opsEntityId');
   parent.selectedOperatingEntityName = cellValue(rowId,'opsEntityName');
   parent.selectedDefaultCustomerContactId = cellValue(rowId,'defaultCustomerContactId');
   parent.selectedDefaultCustomerContactName = cellValue(rowId,'defaultCustomerContactName');
   parent.selectedDefaultCustomerContactPhone = cellValue(rowId,'defaultCustomerContactPhone');
   parent.selectedDefaultCustomerContactFax = cellValue(rowId,'defaultCustomerContactFax');
   parent.selectedDefaultCustomerContactEmail = cellValue(rowId,'defaultCustomerContactEmail');
   parent.selectedAvailableCredit = cellValue(rowId,'availableCredit');
   parent.personnelId = $v("personnelId");
   parent.lastName = $v("lastName");
   parent.firstName = $v("firstName");
}

var done = "N";
function selectedCustomerCall()
{  
  if("Y" == $v("fromQuickPage")) {
  	window.opener.createScratchPadforNewCustomer(selectedCustomerId, selectedCompanyId, selectedShipToLocationId, $v("copyLineItem")); 
  	done = 'Y';
  }  
  else {
	  var c = new customerInfo();
	  try {
	  	window.opener.customerIdChanged(c,personnelId,lastName,firstName);
	  } catch(ex){};
	 
  }
 
  window.close();
}

function customerInfo()
{
	this.customerId = selectedCustomerId;
	this.customerName = selectedCustomerName; 
	this.locationShortName = selectedLocationShortName; 
	this.locationDesc = selectedLocationDesc; 
	this.billToAddress = selectedBillToAddress; 
	this.shipToAddress = selectedShipToAddress;
	this.companyId = selectedCompanyId;
	this.creditStatus = selectedCreditStatus;
	this.facilityId = selectedFacilityId;
	this.accountSysId = selectedAccountSysId;
	this.chargeType = selectedChargeType;
	this.shipToLocationId = selectedShipToLocationId;
	this.shipToCompanyId = selectedShipToCompanyId;
	this.salesAgentId =  selectedSalesAgentId;
	this.salesAgentName = selectedSalesAgentName;
	this.inventoryGroupDefault = selectedInventoryGroupDefault; 
	this.inventoryGroupName = selectedInventoryGroupName;
	this.fieldSalesRepId = selectedFieldSalesRepId;
	this.fieldSalesRepName = selectedFieldSalesRepName;
	this.paymentTerms = selectedPaymentTerms;
	this.shelfLifeRequired = selectedShelfLifeRequired;
	this.shipComplete = selectedShipComplete;
	this.currencyId = selectedCurrencyId;
	this.creditLimit = selectedCreditLimit;
	this.overdueLimit = selectedOverdueLimit;
	this.overdueLimitBasis = selectedOverdueLimitBasis;
	this.fixedCurrency = selectedFixedCurrency;
	this.priceGroupId = selectedPriceGroupId;
	this.shipToAddressSearch = selectedShipToAddressSearch;
	this.shipToAddressLine1Display = selectedShipToAddressLine1Display;
	this.shipToAddressLine2Display = selectedShipToAddressLine2Display;
	this.shipToAddressLine3Display = selectedShipToAddressLine3Display;
	this.shipToAddressLine4Display = selectedShipToAddressLine4Display;
	this.shipToAddressLine5Display = selectedShipToAddressLine5Display;
	this.billToLocationId = selectedBillToLocationId;
	this.billToCompanyId = selectedBillToCompanyId;
	this.addressLine1Display = selectedAddressLine1Display;
	this.addressLine2Display = selectedAddressLine2Display;
	this.addressLine3Display = selectedAddressLine3Display;
	this.addressLine4Display = selectedAddressLine4Display;
	this.addressLine5Display = selectedAddressLine5Display;
	this.notes = selectedNotes;
	this.chargeFreight = selectedChargeFreight;
	this.opsCompanyId = selectedOpsCompanyId;
	this.opsEntityId = selectedOpsEntityId;
	this.operatingEntityName = selectedOperatingEntityName;
	this.defaultCustomerContactId =  parent.selectedDefaultCustomerContactId
	this.defaultCustomerContactName =  parent.selectedDefaultCustomerContactName
    this.defaultCustomerContactPhone = selectedDefaultCustomerContactPhone
    this.defaultCustomerContactFax = selectedDefaultCustomerContactFax
    this.defaultCustomerContactEmail = selectedDefaultCustomerContactEmail
	this.availableCredit = selectedAvailableCredit;
}

function printGrid()
{
	mygrid.printView();
}

function closeonEsc()
{ 
if($v("popup") == 'Y') { 
	windowCloseOnEsc = true; }
else
  	windowCloseOnEsc = false;  	
}

function loadStartTime()
{
  showPleaseWait();
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
}

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = checkInput();
  var now = new Date();
  document.getElementById("uAction").value = 'search';
  document.getElementById("startSearchTime").value = now.getTime();
  
  if(isValidSearchForm) {
   document.genericForm.target='resultFrame';
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function validateSearchFormXSL()
{
  if( checkInput() ) {
      document.getElementById("uAction").value = "createExcel"; 
		document.genericForm.target='_CustomerLookUpExcel';
	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_CustomerLookUpExcel','650','600','yes');
		setTimeout("document.genericForm.submit()",300);
	}
}

function checkInput()
{
var errorMessage = messagesData.validvalues + "\n\n";
var errorCount = 0;

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

 return true;
}

function newCustomer() {
  loc = "customerrequestupdate.do?uAction=new&customerRequestType=New&source=scratchPad";
  children[children.length] = openWinGeneric(loc,"newCustomer","900","800","yes","50","50","20","20","no");
}

/*
function displayCustomerInfo(c,personnelId,lastName,firstName,doClearContact) 
{
	if ($v("customerId").length == 0) {
		$('submittedBy').value=personnelId;
        $('enteredBySpan').innerHTML=lastName+","+firstName; 
    }
 
		$("customerId").value = c.customerId;
		$("customerIdDisplay").innerHTML = "("+c.customerId+")";
		$('gridUpdateLinks').style.visibility="";
		$("customerName").value = c.customerName;
		$("companyId").value = c.companyId;
		$("facilityId").value = c.facilityId;
		$("shipToLocationId").value = c.shipToLocationId; 
		$("shipToCompanyId").value = c.shipToCompanyId;  
		$("salesAgentId").value = c.salesAgentId; 
		$("salesAgentName").value = c.salesAgentName;  
		$("salesAgentNameSpan").innerHTML = c.salesAgentName; 
		$("inventoryGroup").value = c.inventoryGroupDefault;
		$("inventoryGroupNameSpan").innerHTML = c.inventoryGroupName;
		$("fieldSalesRepId").value = c.fieldSalesRepId; 
		$("fieldSalesRepNameSpan").innerHTML = c.fieldSalesRepName;
		$("locationShortName").value = c.locationShortName;
		$("locationDescDisplay").innerHTML = c.locationDesc;
//		$("locationType").value = c.locationType;
		$("billToAddress").value = c.billToAddress; 
		$("paymentTerms").value = c.paymentTerms;
		$("creditLimit").value = c.creditLimit;
		$("overdueLimit").value = c.overdueLimit;
		$("overdueLimitBasis").value = c.overdueLimitBasis;
		
		$("currencyId").value = c.currencyId;
    	try{
			for(var i=0;i< document.getElementById("headerCurrencyId").length;i++) {
     	  		if( c.currencyId == document.getElementById("headerCurrencyId").options[i].value ) {
     			  document.getElementById("headerCurrencyId").selectedIndex = i;
     			  $("currencyIdSpan").innerHTML = $v("headerCurrencyId");
     			  break;
     	  		}
	    	}  
		}
		catch(ex){}   

		$("fixedCurrency").value = c.fixedCurrency;
		
		$("priceGroupId").value = c.priceGroupId; 
		try{
			for(var i=0;i< document.getElementById("headerPriceGroupId").length;i++) {
     	  		if( c.priceGroupId == document.getElementById("headerPriceGroupId").options[i].value ) {
     			 	document.getElementById("headerPriceGroupId").selectedIndex = i;
     			 	$("priceGroupSpan").innerHTML = $v("headerPriceGroupId");//$("headerPriceGroupId").options[$("headerPriceGroupId").selectedIndex].text;
  //   			 	$("priceGroupSpan").innerHTML = priceListArray[i].name;
     			 	break;
     	  		}
	    	}  
		}
		catch(ex){}  
	
		$("shelfLifeRequired").value = c.shelfLifeRequired;
		$("shipComplete").value = c.shipComplete; 
		$("shipCompleteDisplay").innerHTML = c.shipComplete;
		$("accountSysId").value = c.accountSysId;
		$("chargeType").value = c.chargeType; 
		$("creditStatus").value = c.creditStatus;
		if( c.chargeFreight == 'Y')
			$("chargeFreight").checked = true;
		$("opsCompanyId").value = c.opsCompanyId;
		
		try{
			$("opsEntityId").value = c.opsEntityId;
			$("headerOpsEntityId").value = c.opsEntityId;  
     		$("opsEntityNameSpan").innerHTML = c.operatingEntityName;
		}
		catch(ex){}
		
		try{
  //   		document.getElementById("opsEntityId").value = c.opsEntityId;
     		buildCsrDropDown(c.opsEntityId, csrArray);
     		buildPriceListDropDown(c.opsEntityId, priceGroupArray);
		}
		catch(ex){}
	
		$("availableCreditSpan").innerHTML = c.availableCredit;
		$("shipToUpdatable").value = 'N'; 
		$("billToUpdatable").value = 'N'; 
		$("billToCompanyId").value = c.billToCompanyId;
		$("billToLocationId").value = c.billToLocationId;
		billToAddressChanged(c.addressLine1Display,c.addressLine2Display,c.addressLine3Display,c.addressLine4Display,c.addressLine5Display);
		
		inputAddressChanged(c.shipToAddressLine1Display,c.shipToAddressLine2Display,c.shipToAddressLine3Display,c.shipToAddressLine4Display,c.shipToAddressLine5Display);
//		$('uAction').value="searchCustomer";
		$("salesQuoteLineBean").style["display"] = "";

		if(c.creditStatus.toUpperCase() == 'STOP') {
			$("custCreditOverLimit").innerHTML = '<label class="red">(!)</label>';
			try {	
				$("confirmMRSpan").style.display="none";
		  	}catch(ex){}
		}
		else {
			$("custCreditOverLimit").innerHTML = '&nbsp;';
		}
        
        displayCashSale(c.billToCompanyId);

        if(doClearContact == 'Y')
        	clearContact();

        document.getElementById("resultGridDiv").style["display"] = "";
        $("showOverCreditLimit").value="true";
        done=true;
        customerIdChanged11();
        
  		$("prNumber").value = $v("scratchPadId");
  		$("orderType").value = 'Scratch Pad';   
       
        $("uAction").value = "saveNew";
  		showPageWait();
 
 		if (mygrid != null) {
		  	copyDisplayValuesToHiddenColumns();
		    mygrid.parentFormOnSubmit();
		}

  		document.genericForm.submit();
}
*/


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
//	} 
}

function showLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows()
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 100);
  legendWin.setText(messagesData.showlegend);
  legendWin.clearIcon();  // hide icon
  legendWin.denyResize(); // deny resizing
  legendWin.denyPark();   // deny parking
  legendWin.attachObject("showLegendArea");
  legendWin.attachEvent("onClose", function(legendWin){legendWin.hide();});
  legendWin.center();
  }
  else
  {
    // just show
    dhxWins.window("legendwin").show();
  }
}

