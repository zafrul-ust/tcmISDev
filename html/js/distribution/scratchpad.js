var children = new Array(); 

var mygrid;
var selectedRowId = null;
var saveRowClass = null;
var useLayout = false;
var showErrorMessage = false;
var topSectionHeight = 190;
var resultSectionHeight = 0;
var showEditArea = false;
var showDelete = false;
var showReleaseMR = false; 
var tabName = '';
var dhxFreezeWins = null;
var hideResultTable = false;
var noAddChargePermission = 'N';

var quantity_hedFunction = "onChange= caculateExtPrice";
//var catalogPrice_hedFunction = "onChange= unitPriceChanged";
var requiredShelfLife_hedFunction = "onChange= validateRequiredShelfLife";

var checkDate = false;
//var openSPadCounter = 0;

/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

var multiplePermissions = true;
var permissionColumns = new Array();
permissionColumns={"okDoUpdate":true,"customerPoLine":true,"customerPartNo":true,"quantity":true,
					"taxExempt":true,"shipComplete":true,"consolidateShipment":true,"dropShipOverride":true,
					"requiredShelfLife":true,"deliveryType":true,"requiredDatetime":true,"promisedDate":true,"forceHold":true,
					"critical":true,"inventoryGroup":true,"externalNote":true,"internalNote":true,"purchasingNote":true};


function myResultOnload(){
//alert("ordertype:"+$v("orderType"));
//alert("status:"+$v("status"));
  if ($v("closeTab") == 'Y') { 
  	var tabId = $v("tabId");
  	try{
  		parent.parent.closeTabx(tabId);
  	}catch(ex){}
  }	
  	
  customerIdChanged11();
  selectedRowId = $v("selectedRowId"); 

  displayCashSale($v("billToCompanyId"));
//alert( $v("hideOrShowDiv")); 
  if($v("hideOrShowDiv") == 'show')
    showDiv();
  else
    hideDiv();
    
    
  if ($("customerId") == null || $v("customerId").length == 0) { 
    $('gridUpdateLinks').style.visibility="hidden";
    $('customerIdDisplay').innerHTML="";
  }

  if ( ($v("status").toUpperCase() == 'CONFIRMED' || $v("status").toUpperCase() == 'POSUBMIT') || ($v("orderType") == 'MR' && $v("orderStatus").toUpperCase() != 'DRAFT') ) {
  	try {
    	$('saveSpan').style.display="none";
    } catch (ex){}
    if ($v("orderType") != 'Scratch Pad')
      try {
        $('lookupBtnDisplay').style.display="none";
      } catch (ex){}
      
    if ($v("orderType") == 'Quote' || $v("orderType") == 'Blanket Order')
      $('mainUpdateLinks').style.display="none";
  }

  if ($v("orderType") == 'MR' && ($v("status").toUpperCase() == 'CONFIRMED' || $v("status").toUpperCase() == 'POSUBMIT')) {
      try {
      	$('opsEntityNameSpan').style.display="";  
    	$('opsEntityIdDropDownSpan').style.display="none";
		$('currencyIdSpan').style.display="";  
    	$('currencyIdDropDownSpan').style.display="none";
        $('deleteLineSpan').style.display="none";
      } catch (ex){}
  }
  
  if (($v("status") != 'Draft' && $v("orderStatus") != 'Draft') && ($v("orderType") == 'Quote' || $v("orderType") == 'Blanket Order' || $v("orderType") == 'MR')) {
  	try{
    	$('deleteSpan').style.display="none";
    } catch (ex){}
  }
  
  if (showEditArea != true ) {
    if ($('chkAllOkDoUpdate') != null)
      $('chkAllOkDoUpdate').style.display="none";
    $('mainUpdateLinks').style.display="none";
    if (!($v('creditStatus').toUpperCase() == 'STOP' && $v('orderStatus').toUpperCase() == 'DRAFT'))
      $('gridUpdateLinks').style.display="none";
  }
  
  if (showEditArea != true || ($v("orderType") == 'Quote' && $v("status").toUpperCase() == 'CONFIRMED') || ($v("orderType") == 'Blanket Order' && $v("status").toUpperCase() == 'CONFIRMED')) {
    $('opsEntityNameSpan').style.display="";  
    $('opsEntityIdDropDownSpan').style.display="none";
    $('priceGroupSpan').style.display="";  
    $('priceGroupIdDropDownSpan').style.display="none";
    $('currencyIdSpan').style.display="";  
    $('currencyIdDropDownSpan').style.display="none";
    hideCheckAll();
  }

  if (($v("orderType") == 'Quote' || $v("orderType") == 'Blanket Order') && $("quoteExpireDate") != null && $v("quoteExpireDate").length > 0) {
  	var quoteExpireDate = dateToIntString($v("quoteExpireDate"));
  	var toDate = dateToIntString($v("toDate"));
  	if (quoteExpireDate<toDate)
  	{
  	  try {
  		$('createMRSpan').style.display="none";
  	  } catch (ex) {}
  	}
  }
  
  if (($v("orderType") == 'Quote' || $v("orderType") == 'Blanket Order') && $v("status").toUpperCase() == 'CONFIRMED') {
  		$('cancelquoteSpan').style.display="";
  }
  
  if ($v("totalLines") == 0) {
  	if ($v("orderType") == 'MR' && $v("status").toUpperCase() == 'POSUBMIT') {
    	$('resultGridDiv').style.display="none";
    	if($('headerCharges').innerHTML != '0.00' && $('headerCharges').innerHTML != '' && $('headerCharges').innerHTML != '0') {
  			$('confirmMRSpan').style.display="none";
  			$('printSpan').style.display="none";
  			$('printInvoice').style.display="";
  			noAddChargePermission = 'Y';
  		}
    }
    
    if($("deleteDupLineLink") != null)
      	  $("deleteDupLineLink").style.visibility = "hidden";  
    try{
  		$('noRowSpan11').style.display="none";
  		$('noRowSpan12').style.display="none";
  	}catch(ex){}
 	
  	try{
  	  if($('headerCharges').innerHTML == '0.00' || $('headerCharges').innerHTML == '' || $('headerCharges').innerHTML == '0')
  			$('confirmMRSpan').style.display="none";
  	  $('autoAllocateSpan').style.display="none";
  	}catch(ex){}
  	
/*  	try{
  		$('noRowSpan3').style.display="none";
  	}catch(ex){}  */

  	if ( hideResultTable )
  		$('resultsMaskTable').style.display="none";
  	
  	$('totalSpan').innerHTML = $('headerCharges').innerHTML;
  	if($v("availableCredit")*1 < $("totalSpan").innerHTML*1 && $v("showOverCreditLimit") == 'true' && $v("billToCompanyId") != "CASH_SALES" && done == true) {
		showOverCreditLimitWin();
		done = false;
    }
  	
  }
  else {
    $("deleteDupLineLink").style.visibility = "";
    if ($v("selectedRowId").length >0) {
    	saveRowClass = getRowClass($v("selectedRowId"));
        mygrid.selectRow(mygrid.getRowIndex($v("selectedRowId")),null,false,false);
    }
    else {
        saveRowClass = getRowClass(1); 
    	mygrid.selectRow(mygrid.getRowIndex("1"),null,false,false);
    	$("selectedRowId").value = 1;
    }
    
    if(saveRowClass.search(/grid/) != -1)
    	setRowClass($v("selectedRowId"), ''+saveRowClass+'Selected');		

  	var rowsNum = mygrid.getRowsNum();
  	var flag1 = false;
  	for (var p = 1; p < (rowsNum+1) ; p ++)
  	{
  	  try{ 
  	  	if(cellValue(p,"requestLineStatus").toUpperCase() != 'SHIPPED')
  	  		flag1 = true;
  	  		
  	  	var margin = $v("marginNumber"+p); //cellValue(p,"margin").replace("%","");
	    if (margin > cellValue(p,"maximumGrossMargin")*1 || margin < cellValue(p,"minimumGrossMargin")*1) 
	    	$("marginNumber"+p).style.color="red";
	    else 
	    	$("marginNumber"+p).style.color="black";	
      }catch(ex){}
  	}
  	
  	if ( flag1 ) {
  		try{
  			$('autoAllocateSpan').style.display="";
  		}catch(ex){}
  	}
  	else {
  		try{
  			$('autoAllocateSpan').style.display="none";
  		}catch(ex){}
  	}
  	
  	try{
  		$('noRowSpan11').style.display="";
  		$('noRowSpan12').style.display="";
  	}catch(ex){}
  	
  	try{
  		$('confirmMRSpan').style.display="";
  		$('autoAllocateSpan').style.display="";
  	}catch(ex){}
  	
/*  	try{
  		$('noRowSpan3').style.display="";
  	}catch(ex){} */
  	
  	$('opsEntityNameSpan').style.display="";  
    $('opsEntityIdDropDownSpan').style.display="none";
  	$('priceGroupSpan').style.display="";  
    $('priceGroupIdDropDownSpan').style.display="none";
	$('currencyIdSpan').style.display="";  
    $('currencyIdDropDownSpan').style.display="none";
    
    if ($v("orderType") == 'MR' && showReleaseMR == true) {
      try
      {
        	$('releaseMRSpan').style.display="";
      } catch (ex){}
    }

  } //end of $v("totalLines") != 0

  selectedRowId = $v("selectedRowId");

  if($v('personnelId').length != 0) {
  	$('submittedBy').value=personnelId;
  	$('enteredBySpan').innerHTML=lastName+","+firstName; 
  }
  
  if($v("creditStatus").toUpperCase() == 'STOP') {
	  	try {	
			$("confirmMRSpan").style.display="none";
	  	}catch(ex){};
  }
  
  if( typeof(mygrid) != 'undefined' ) {
   		var rowsNum = mygrid.getRowsNum();
   		$("totalRows").value = rowsNum; 
  } 
  
  try {
    if($v("orderType") == 'MR' && (cellValue(selectedRowId,"radianPo") != "" || cellValue(selectedRowId,"poLine") != "")) {
        $('addLineSpan').style.display="none";
    }
  } catch (ex){}
  
  try {
    if($v("orderType") == 'MR' && $v("originalSalesQuoteType") == 'Blanket Order') {
        $('addLineSpan').style.display="none";
        $('dupLineSpan').style.display="none";
    }
  } catch (ex){}
  
  if ($v("releaseStatus") == 'Pending Acceptance' && $v("orderType") == 'MR') {
  	try{
    	$('deleteSpan').style.display="none";
    } catch (ex){}
    try{
    	$('confirmMRSpan').style.display="none";
    } catch (ex){}
  }

  setUpShipToAddress($v("shipToAddressLine1"),$v("shipToAddressLine2"),$v("shipToAddressLine3"),$v("shipToAddressLine4"),$v("shipToAddressLine5"));
  billToAddressChanged($v("billToAddressLine1"),$v("billToAddressLine2"),$v("billToAddressLine3"),$v("billToAddressLine4"),$v("billToAddressLine5"));
  requestorChanged();
   
  scratchPadResize();
  if (($v("releaseStatus") == 'Pending Expert Review' || 
  		$v("releaseStatus") == 'Expert Review Hold') && 
  		'Y' == $v("afterConfirmMr")) {
  		expertReviewMsg = messagesData.expertreviewmsg1 + '\n' + messagesData.expertreviewmsg2;
  		setTimeout('alert(expertReviewMsg)',100);
  }
  
  if (showErrorMessage){
    setTimeout('showScratchPadErrorMessages()',100); /*Showing error messages if any*/
  }

  try{
    parent.resetTimer();
  }
  catch (ex){}
}

function refresh() {
  $("uAction").value = "searchScratchPadId"; 
  $("scratchPadId").value = $v("prNumber");
  showPageWait();
  document.genericForm.submit();
}

function openOriginalQuote() {
	if($v("originalSalesQuoteType") == 'Blanket Order')
		newScratchPad("N","B",$v("originalSalesQuoteId"),"","","","");
	else
		newScratchPad("N","Q",$v("originalSalesQuoteId"),"","","","");
}

function hideCheckAll() {
	if ($('chkAllTaxExempt') != null)
      $('chkAllTaxExempt').style.display="none";
    if ($('chkAllShipComplete') != null)
      $('chkAllShipComplete').style.display="none";
    if ($('chkAllLinesGroup') != null)
      $('chkAllLinesGroup').style.display="none";
    if ($('needdateAll') != null)
      $('needdateAll').style.display="none";
    if ($('promisedDateAll') != null)
      $('promisedDateAll').style.display="none";
    if ($('chkAllDropship') != null)
      $('chkAllDropship').style.display="none";
    if ($('chkAllforceHold') != null)
      $('chkAllforceHold').style.display="none";
    if ($('chkAllscrap') != null)
      $('chkAllscrap').style.display="none";
}

function setOpsEntityId() {
	$("opsEntityId").value = $v("headerOpsEntityId");
	try {
		$("opsEntityNameSpan").innerHTML = $("headerOpsEntityId").options[$("headerOpsEntityId").selectedIndex].text;
	}
	catch(ex){}
}

function setCurrencyId(opsEntityId,currencyArray) {
	
	if (!opsEntityId || opsEntityId == '')
   	{
      opsEntityId = $("headerOpsEntityId").value;
    }

	homeCurrencyId = '';
	for ( var i=0; i < currencyArray.length; i++) {
	  	if(opsEntityId == currencyArray[i].opsEntityId) {
	    	homeCurrencyId = currencyArray[i].homeCurrencyId;
	    }      
	} 
	if ( homeCurrencyId !=  $v("currencyId")) {
//		$("headerCurrencyId").value = homeCurrencyId;
//		$("currencyId").value = $v("headerCurrencyId");
	  try {
		$("uAction").value  = "save"; 
   		showPageWait();
   		document.genericForm.submit();
   	  } catch(ex) {}
	}
}


function setPriceGroupId() {
	$("priceGroupId").value = $v("headerPriceGroupId");
	try {
		$("priceGroupSpan").innerHTML = $v("headerPriceGroupId");//$("headerPriceGroupId").options[$("headerPriceGroupId").selectedIndex].text;
	}
	catch(ex){}
}

function quoteExpireDateChanged() {
  if (($v("orderType") == 'Quote' || $v("orderType") == 'Blanket Order') && $("quoteExpireDate") != null && $v("quoteExpireDate").length > 0) {
  	var quoteExpireDate = dateToIntString($v("quoteExpireDate"));
  	var toDate = dateToIntString($v("toDate"));
  	if (quoteExpireDate<toDate)
  		$('createMRSpan').style.display="";
  	else
  		$('createMRSpan').style.display="none";
  }
}

function showScratchPadErrorMessages(errMsg)
{
  var resulterrorMessages = $("errorMessagesAreaBody");
  var errorMessagesArea = $("errorMessagesArea");
  if(errMsg == null)
  	errorMessagesArea.innerHTML = resulterrorMessages.innerHTML;
  else
	errorMessagesArea.innerHTML = errMsg;
	
  var errorMessagesArea = $("errorMessagesArea");
  errorMessagesArea.style.display="";

  var dhxWins = new dhtmlXWindows();
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.errors)) {
    // create window first time
    var errorWin = dhxWins.createWindow(messagesData.errors, 0, 0, 450, 300);
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

function scratchPadResize()
{
   setWindowSizes();
   resizeGridHeight();
   setGridWidth();  
}

function resizeGridHeight() {

  try
  {
   var footerDivHeight=8;
   try
   {
    var footerDiv = $("footer");
    footerDivHeight = footerDiv.offsetHeight+8;
    if(footerDivHeight ==0)
    {
     footerDivHeight = 8;
    }
   }
   catch (ex)
   {
    footerDivHeight	=8;
   }

   var mainUpdateLinksHeight=0;
   try
   {
    var mainUpdateLinksDiv = $("mainUpdateLinks");
    mainUpdateLinksHeight = mainUpdateLinksDiv.offsetHeight;
    if(mainUpdateLinksHeight ==0)
    {
      mainUpdateLinksHeight = 8;
    }
    else
    {
        if (_isIE)
        {
            mainUpdateLinksHeight = mainUpdateLinksHeight +5;
        }
        else
        {
            mainUpdateLinksHeight = mainUpdateLinksHeight +8;
        }
    }
   }
   catch (ex)
   {
    mainUpdateLinksHeight=8;
   }

   //bodyOffsetHeight = window.document.body.offsetHeight;   
//   matchResultSectionDivs = true;
   var rowsNum = mygrid.getRowsNum();

// alert("myHeight: "+myHeight+" topSectionHeight: "+topSectionHeight+" mainUpdateLinksHeight "+mainUpdateLinksHeight+" footerDivHeight: "+footerDivHeight+"");
   if (_isIE)
   {
//    resultSectionHeight = myHeight-12-topSectionHeight-mainUpdateLinksHeight-footerDivHeight-30-extraReduction;
      resultSectionHeight = myHeight-12-topSectionHeight-mainUpdateLinksHeight-footerDivHeight-45;
   }
   else
   {
//    resultSectionHeight = myHeight-12-topSectionHeight-mainUpdateLinksHeight-footerDivHeight-30-extraReduction;
      resultSectionHeight = myHeight-12-topSectionHeight-mainUpdateLinksHeight-footerDivHeight-50;
   }
    
   if (rowsNum == 0)
   {
     resultSectionHeight = 25;
   }
//alert("resultSectionHeight: "+resultSectionHeight);
//  if (resultSectionHeight > 150)
//  {
   //frameName.height=resultSectionHeight;
   if (rowsNum != 0)
   {
     try
     {
       setTimeout('setGridHeight('+resultSectionHeight+');',2);
     }
     catch(exGrid)
     {
 //      alert("Here 209");
     }

     try
     {
       setTimeout('mygrid.setSizes();',2);
     }
     catch(exGrid)
     {
       //alert("Here 209");
     }    
   }
// }
 }
 catch (ex)
 {
 //  alert("here error 68 resizeResults()");
 }
}

function setGridHeight(resultGridHeight)
{
  try
  {
   var id=mygrid.entBox.id;
   var griDiv = $(id);
   griDiv.style.height = resultGridHeight-10 + "px";
  }
  catch(ex)
  {
      //alert("THis means the grid was not initialized");
  }
}


function  setGridWidth() {
   if (_isIE)
	   {
		   myWidth -=5;
	   }
	   else
	   {
	    myWidth -=22;
	   } 

  		try
  		{
   			var id=mygrid.entBox.id;
   			var griDiv = $(id);
   			griDiv.style.width = myWidth + "px";   
  		}
  		catch(ex)
  		{
 		     //alert("THis means the grid was not initialized");
  		}
   try
    {  
     window.$("resultsMaskTable").style["width"] = myWidth-30;
     $('salesQuoteLineBean').style.width = myWidth-40;   
   }
   catch (ex)
   {
     //alert("here 112");
   } 
}

function onKeySearch(e,doFunction) {
  var keyCode;
  if(window.event)
  {
    keyCode = window.event.keyCode;     //IE
  }else
  {
    try
    {
      keyCode = e.which;     //firefox
    }
    catch (ex){
      //return false;
    }
  }
  if (keyCode==13) {
    doFunction();
  }
}

function lookupCustomer() {
  var customerName = $("customerName").value.trim();

  getTabName();
  loc = "customersearchmain.do?popup=Y&tabName="+encodeURIComponent(tabName);
  var winId= 'customerlookup'+$v("prNumber");
  showTransitWin(messagesData.customerlookup);
  children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"900","500","yes","50","50","20","20","no");
}

function getTabName() {
	var orderType = $v("orderType");
	if (orderType == "Quote")
        	tabName = 'Q '+$v("prNumber");
        else if (orderType == "MR")
        	tabName = 'MR '+$v("prNumber");
        else if (orderType == "Blanket Order")
        	tabName = 'B '+$v("prNumber");
        else
        	tabName = 'SP '+$v("prNumber");
}

function changeShipTo() {
  var customerId = $v("customerId");
  var companyId = $v("companyId");
  var fieldSalesRepId = $v("fieldSalesRepId");
  var fieldSalesRepName = $("fieldSalesRepNameSpan").innerHTML;
  var priceList = $v("customerPriceGroupId");
  
  if(customerId.length == 0) {
    alert(messagesData.pickcustomerid);
    return false;
  } else {
    getTabName();
    $("shipToUpdatable").value = 'N';
    showTransitWin(messagesData.shiptoaddress);
    loc = "shiptoaddress.do?uAction=search&searchNameArgument=&searchBillToArgument=&searchShipToArgument=&searchNameMode=&searchSynonymMode=&searchBillToMode=&searchShipToMode=&searchSynonymArgument=&searchCustomerIdArgument="+customerId+"&companyId="+companyId+"&fieldSalesRepId="+fieldSalesRepId+"&fieldSalesRepName="+encodeURIComponent(fieldSalesRepName)+"&locationType="+encodeURIComponent("Ship To")+"&tabName="+encodeURIComponent(tabName)+"&priceList="+priceList;
  } 
  var winId= 'shipToAddress'+$v("prNumber");
  children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"700","350","yes","50","50","no"); 
}

function getContact()
{
 var customerId = $v("customerId");
 getTabName();
 showTransitWin(messagesData.contactsearch);
 if(customerId.length == 0) 
 	loc = "contactlookupmain.do?tabName="+encodeURIComponent(tabName);
 else 
 	loc = "contactlookupmain.do?customerId="+customerId+"&searchCustomerIdMode=is&tabName="+encodeURIComponent(tabName)+"&billToCompanyId="+$v("billToCompanyId")+"&customerName="+encodeURIComponent($v("customerName"));
 var winId= 'ContactLookup'+$v("prNumber");
 children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"900","500","yes","50","50");
} 

function clearContact()
{
	$("requestor").value = '';
	$("requestorName").value = '';
	$("requestorPhone").value = '';
	$("requestorFax").value = '';
	$("requestorEmail").value = '';
	requestorChanged();
}

function getCarrier()
{
 var inventoryGroup = $("inventoryGroup").value;
 var customerId = $("customerId").value;
 var customerName = $("customerName").value;
 getTabName();
 showTransitWin(messagesData.customercarrier);
 var loc = "/tcmIS/distribution/customercarriermain.do?showCustomerCarriersOnly=Yes&displayElementId=carrierName&valueElementId=carrierAccountId&displayAccount=carrierAccountNumber&inventoryGroup="+inventoryGroup+"&customerId="+customerId+"&customerName="+encodeURIComponent(customerName)+"&tabName="+encodeURIComponent(tabName);
 var winId= 'Carrier'+$v("prNumber");
 children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"700","400","yes","50","50");
}

function clearCarrier() 
{
	$("carrierAccountId").value = '';
	$("carrierName").value = '';
	$("carrierAccountNumber").value = ''; 
}

function showDiv() {
  for(var i=6; i<=16; i++){//alert(i);
   $('row'+i+'').style.display="";
  }
  $('hide').style.display="";
  $('show').style.display="none";
//  alert("topSectionHeight"+ $("searchMaskTable").offsetHeight);
  topSectionHeight = 478;
  $("hideOrShowDiv").value = 'show';
  scratchPadResize();
//  setSearchFrameSize();
}

function hideDiv() {
  for(var i=6; i<=16; i++)
   $('row'+i+'').style.display="none";

  $('hide').style.display="none";
  $('show').style.display="";
//  alert("topSectionHeight"+ $("searchMaskTable").offsetHeight);
  topSectionHeight = 190;
  $("hideOrShowDiv").value = 'hide';
  scratchPadResize();  
//  setSearchFrameSize();
}


function confirmQuote()
{
 var errorCount = 0;
 var errorMessage = messagesData.validvalues+"\n\n";
 
 if($v("customerId").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.customer;
 	errorCount = 1;
 }
/* 
 if($v("requestor").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.contact;
 	errorCount = 1;
 }
*/ 

 if($v("inventoryGroup").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.inventorygroup;
 	errorCount = 1;
 }

 if($v("customerServiceRepId").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.csr;
 	errorCount = 1;
 }

 if($v("quoteExpireDate").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.quoteexpires;
 	errorCount = 1;
 }
 
 if($v("poNumber").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.customerref;
 	errorCount = 1;
 }
 
 if (errorCount > 0)
 {
     alert(errorMessage);
//	 mygrid.cells(rowId,1).setValue(null);
	 return false;
 }  

  var rowsNum = mygrid.getRowsNum();
 
  for (var p = 1 ; p < (rowsNum+1) ; p ++)
  {	
	if(!checkInputValues(p,"okDoUpdate","Q")) return false;
  }

  $("marginOutSideLimits").value = "N";
  for (var j = 1 ; j < (rowsNum+1) ; j ++)
  {	
	if(!checkMargin(j)) {
		return;
	}
  }
   
  $("uAction").value = "confirmQuote";
  showPageWait();
  
  if (mygrid != null) {
  	copyDisplayValuesToHiddenColumns();
    mygrid.parentFormOnSubmit();
  }
  closeAllchildren();
  
  document.genericForm.submit();  
}

function confirmBlanket()
{
 var errorCount = 0;
 var errorMessage = messagesData.validvalues+"\n\n";
 
 if($v("customerId").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.customer;
 	errorCount = 1;
 }
/* 
 if($v("requestor").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.contact;
 	errorCount = 1;
 }
*/ 

 if($v("inventoryGroup").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.inventorygroup;
 	errorCount = 1;
 }
 
 if($v("poNumber").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.customerpo;
 	errorCount = 1;
 }

 if($v("customerServiceRepId").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.csr;
 	errorCount = 1;
 }

 if($v("quoteExpireDate").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.quoteexpires;
 	errorCount = 1;
 }
 
 if (errorCount > 0)
 {
     alert(errorMessage);
//	 mygrid.cells(rowId,1).setValue(null);
	 return false;
 }  

  var rowsNum = mygrid.getRowsNum();
 
  var flag = true;
  for (var p = 1 ; p < (rowsNum+1) ; p ++)
  {	
	if(!checkInputValues(p,"okDoUpdate","B")) return false;
  }

  $("marginOutSideLimits").value = "N";
  for (var j = 1 ; j < (rowsNum+1) ; j ++)
  {	
	if(!checkMargin(j)) {
		return;
	}
  }
   
  $("uAction").value = "confirmQuote";
  showPageWait();
  
  if (mygrid != null) {
  	copyDisplayValuesToHiddenColumns();
    mygrid.parentFormOnSubmit();
  }
  closeAllchildren();
  
  document.genericForm.submit();  
}

function checkMargin(p) {
	var margin = $v("marginNumber"+p); //cellValue(p,"margin").replace('%','');
    if (cellValue(p,"requestLineStatus").toUpperCase() !='CANCELED' && cellValue(p,"requestLineStatus").toUpperCase() !='CANCELLED' && margin != null && margin.length !=0 && (margin > cellValue(p,"maximumGrossMargin")*1 || margin < cellValue(p,"minimumGrossMargin")*1)) {
		var continueOrNot = confirm(messagesData.line + " " + p + ": " + messagesData.marginoutsidelimits.replace(/[{]0[}]/g,$v("marginNumber"+p)));
		if (continueOrNot) {
		  $("marginOutSideLimits").value = "Y";
		  mygrid.selectRow(mygrid.getRowIndex(p),null,false,false);
	      return true;
	    }
	    else {
	      mygrid.selectRow(mygrid.getRowIndex(p),null,false,false);
	      return false;
	    }  
  	}
  	return true;
}

function setSentConfirmEmail() {
	try {
		if( $v("status").toUpperCase() == 'DRAFT' )
			$("emailOrderAck").value = "Y";
		else if( $v("emailOrderAck") == "Y" && $v("requestorEmail") ) {
			ask = confirm(messagesData.confirEmail);
			if( !ask )
				$("emailOrderAck").value = "N";
		}
	}catch(ex){}
}

function confirmMR()
{
 setSentConfirmEmail();
 var text = messagesData.totalexceedavailable;
 if($("totalSpan").innerHTML*1 > $v("availableCredit")*1 && $v("billToCompanyId") != "CASH_SALES") {
 	var answer = confirm (text)
    if (!answer)
       return false;
 }
       
 var errorCount = 0;
 var errorMessage = messagesData.validvalues+"\n\n";

 if($v("customerId").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.customer;
 	errorCount = 1;
 }

 var shipToUpdatable = $v("shipToUpdatable");

 if(shipToUpdatable == "N" && $v("shipToLocationId").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.shipto;
 	errorCount = 1;
 }
/* 
 if($v("requestorName").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.contact;
 	errorCount = 1;
 }
*/ 
 if($v("submittedDate").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.orderdate;
 	errorCount = 1;
 }

 if($v("inventoryGroup").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.inventorygroup;
 	errorCount = 1;
 }

 if($v("customerServiceRepId").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.csr;
 	errorCount = 1;
 }
 
 if($v("carrierServiceType").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.shippingmethod;
 	errorCount = 1;
 }
 
 if($v("poNumber").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.customerpo;
 	errorCount = 1;
 }
 
 if (errorCount > 0)
 {
     alert(errorMessage);
//	 mygrid.cells(rowId,1).setValue(null);
	 return false;
 }
// setTimeout('closeTransitWin()',300);


 var custPoNumber = $v("poNumber");
 custPoNumber = custPoNumber.replace(/&/gi, "%26");
 custPoNumber = custPoNumber.replace(/#/gi, "%23");
 custPoNumber = custPoNumber.replace(/\+/gi, "%2b");
   
 if($v("originalSalesQuoteType") == 'Blanket Order')
 	confirmMRAfterDupCheck(0);
 else {
 	showTransitWin("");
 	callToServer("scratchpadmain.do?uAction=customerPoDupCheck&companyId="+$v("companyId")+"&poNumber="+custPoNumber+"&prNumber="+$v("prNumber")+"&callBack=confirmMRAfterDupCheck");
 }
}

function confirmMRAfterDupCheck(count)
{
	var poNumber = $v("poNumber");

   if (count > 0*1)  {
//   	 if($v("originalSalesQuoteType") == 'Blanket Order') {
//   	 	var text = messagesData.duplicatecustomerpo.replace(/[{]0[}]/g,poNumber);
//   	 	alert(text);
//   	 	return false;
//   	 }
   	 
   	 var text = messagesData.confirmduplicatecustomerpo.replace(/[{]0[}]/g,poNumber);
     var answer = confirm (text)
     if (!answer) {
       closeTransitWin();
       return false;
     }  
  }
  
  try {
  	closeTransitWin();
  } catch (ex) {} 
  
  var rowsNum = 0;
  if( mygrid != null ) {
   rowsNum = mygrid.getRowsNum();
  }
 
  for (var p = 1 ; p < (rowsNum+1) ; p ++)
  {	
    checkDate = true;
	if(!checkInputValues(p,"okDoUpdate","M")) return false;
	
  }
  
  $("marginOutSideLimits").value = "N";
  for (var p = 1 ; p < (rowsNum+1) ; p ++)
  {	
	if(!checkMargin(p)) {
		return;
	}
  }
/*
  if((typeof(mygrid) == 'undefined' || mygrid == null || mygrid.getRowsNum() == 0) && $("headerCharges").innerHTML.length > 0 && $("headerCharges").innerHTML.trim() != "0.00") {
  	loc = "../distribution/inputdatedelivered.jsp";  
	openWinGeneric(loc,"inputDateDelivered","300","220","yes","50","50","no");  
  }
  else { 
	submitConfirmMr();
  }
}

function submitConfirmMr() {
  try {
  	closeTransitWin();
  } catch (ex) {} 
*/  
  $("uAction").value = "confirmMR";
  showPageWait();
  
  if (mygrid != null) {
  	copyDisplayValuesToHiddenColumns();
    mygrid.parentFormOnSubmit();
  }
    
  closeAllchildren();  
  document.genericForm.submit();  
}



function acceptMR()
{
 var text = messagesData.totalexceedavailable;
 if($("totalSpan").innerHTML*1 > $v("availableCredit")*1 && $v("billToCompanyId") != "CASH_SALES") {
 	var answer = confirm (text)
    if (!answer)
       return false;
 }
       
 var errorCount = 0;
 var errorMessage = messagesData.validvalues+"\n\n";

 if($v("customerId").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.customer;
 	errorCount = 1;
 }

 var shipToUpdatable = $v("shipToUpdatable");

 if(shipToUpdatable == "N" && $v("shipToLocationId").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.shipto;
 	errorCount = 1;
 }
/* 
 if($v("requestorName").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.contact;
 	errorCount = 1;
 }
*/ 
 if($v("submittedDate").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.orderdate;
 	errorCount = 1;
 }

 if($v("inventoryGroup").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.inventorygroup;
 	errorCount = 1;
 }

 if($v("customerServiceRepId").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.csr;
 	errorCount = 1;
 }
 
 if($v("carrierServiceType").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.shippingmethod;
 	errorCount = 1;
 }
 
 if($v("poNumber").length == 0)
 {
 	errorMessage = errorMessage +"\n"+ messagesData.customerpo;
 	errorCount = 1;
 }
 
 if (errorCount > 0)
 {
     alert(errorMessage);
//	 mygrid.cells(rowId,1).setValue(null);
	 return false;
 }
// setTimeout('closeTransitWin()',300);


 var custPoNumber = $v("poNumber");
 custPoNumber = custPoNumber.replace(/&/gi, "%26");
 custPoNumber = custPoNumber.replace(/#/gi, "%23");
 custPoNumber = custPoNumber.replace(/\+/gi, "%2b");
   
 if($v("originalSalesQuoteType") == 'Blanket Order')
 	acceptMRAfterDupCheck(0);
 else {
 	showTransitWin("");
 	callToServer("scratchpadmain.do?uAction=customerPoDupCheck&companyId="+$v("companyId")+"&poNumber="+custPoNumber+"&prNumber="+$v("prNumber")+"&callBack=acceptMRAfterDupCheck");
 }
}

function acceptMRAfterDupCheck(count)
{
	var poNumber = $v("poNumber");

   if (count > 0*1)  {
   	 var text = messagesData.confirmduplicatecustomerpo.replace(/[{]0[}]/g,poNumber);
     var answer = confirm (text)
     if (!answer) {
       closeTransitWin();
       return false;
     }  
  }
  
  try {
  	closeTransitWin();
  } catch (ex) {} 
  
  var rowsNum = 0;
  if( mygrid != null ) {
   rowsNum = mygrid.getRowsNum();
  }
 
  for (var p = 1 ; p < (rowsNum+1) ; p ++)
  {	
    checkDate = true;
	if(!checkInputValues(p,"okDoUpdate","M")) return false;
	
  }
  
  $("marginOutSideLimits").value = "N";
  for (var p = 1 ; p < (rowsNum+1) ; p ++)
  {	
	if(!checkMargin(p)) {
		return;
	}
  }

  $("uAction").value = "acceptMR";
  showPageWait();
  
  if (mygrid != null) {
  	copyDisplayValuesToHiddenColumns();
    mygrid.parentFormOnSubmit();
  }
    
  closeAllchildren();  
  document.genericForm.submit();  
}

function addLine() {

  var errorMessage = messagesData.validvalues+"\n\n";
  var count = 0;
/* 
  if ($v("priceGroupId").length == 0)
  {  
   	   errorMessage = errorMessage +"\n"+ messagesData.pricelist;
	   count = 1;
  }   */

  if ($v("currencyId").length == 0)
  {  
   	   errorMessage = errorMessage +"\n"+ messagesData.currency;
	   count = 1;
  }
 
   if ($v("opsEntityId").length == 0)
  {  
   	   errorMessage = errorMessage +"\n"+ messagesData.revenueentity;
	   count = 1;
  }

  if ( count > 0) {
  	alert(errorMessage);
  	return;
  }
  
  showTransitWin(messagesData.addline);
  
  if(mygrid == null) {
     doInitGrid(); 
  }
  
  var ind = mygrid.getRowsNum();  
  var rowid = ind*1+1;
	
  var loc = "partsearchmain.do?uAction=new&companyId=" + $v("companyId")+ 
		"&lineItem=" + rowid + 
		"&shipToCompanyId=" + $v("shipToCompanyId") + 
		"&shipToLocationId=" + $v("shipToLocationId") + 
		"&facilityId=" + $v("facilityId") +
		"&inventoryGroup=" + $v("inventoryGroup") + 
		"&priceGroupId=" + $v("priceGroupId") +
		"&currencyId=" + $v("currencyId") +
		"&curpath="+encodeURIComponent(parent.parent.getTabName($v('tabId')))+
		"&orderType="+pp('orderType')+
		"&customerId=" + $v("customerId") + 
		"&opsEntityId=" + encodeURIComponent($v("opsEntityId")) +
		"&hub=" + encodeURIComponent($v("hub")) +
		"&opsCompanyId=" + encodeURIComponent($v("opsCompanyId")) + 
		"&poNumber=" + encodeURIComponent($v("poNumber")) +
		"&shipComplete=" + $v("shipComplete") +
		"&requiredShelfLife=" + $v("shelfLifeRequired") +
		"&mvItem=" + $v("mvItem") +
		"&prNumber=" + $v("prNumber");
	var winId= 'partsearch'+$v("prNumber");
	children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"1024","600","yes","50","50","20","20","no"); 
	//children[children.length] = openWinGenericDefault(loc,winId.replace('.','a'),"yes","50","50","20","20","no"); 
}

function checkAll(checkboxname)
{
  renderAllRows();
  
  var checkall = null;
  switch (checkboxname)
  {
        case "taxExempt":
 			checkall = $("chkAllTaxExempt");
 		 	break;
  		case "shipComplete":
 			checkall = $("chkAllShipComplete");
 		 	break;
 	    case "consolidateShipment":
 			checkall = $("chkAllLinesGroup");
 			break;
 		case "dropShipOverride":
 			checkall = $("chkAllDropship");
 		 	break;
 		case "forceHold":
 			checkall = $("chkAllforceHold");
 		 	break;
 		case "scrap":
 			checkall = $("chkAllscrap");
 		 	break;
 		default:
 			checkall = $("chkAllOkDoUpdate");
 			break;
  }
  var rowsNum = mygrid.getRowsNum();

  rowsNum = rowsNum*1;

  if( checkall.checked ) {
		for(var p = 1 ; p < (rowsNum+1) ; p ++) {
			var cid = checkboxname+p;
			if( ! $(cid).disabled && !$(cid).checked) {
				$(cid).checked = true;
				updateHchStatusA(cid);
			}
		}
		if(checkboxname == "consolidateShipment") {
			$("chkAllShipComplete").checked = true;
			for(var p = 1 ; p < (rowsNum+1) ; p ++) {
				var cid = "shipComplete"+p;
				if( ! $(cid).disabled && !$(cid).checked) {
					$(cid).checked = true;
					updateHchStatusA(cid);
				}
			}
		}
  }
  else {
		for(var p = 1 ; p < (rowsNum+1) ; p ++) {
			var cid = checkboxname+p;
			if( !$(cid).disabled && $(cid).checked) {
				$(cid).checked = false;
				updateHchStatusA(cid);
			}
		}
		if(checkboxname == "shipComplete") {
			$("chkAllLinesGroup").checked = false;
			for(var p = 1 ; p < (rowsNum+1) ; p ++) {
				var cid = "consolidateShipment"+p;
				if( ! $(cid).disabled && $(cid).checked) {
					$(cid).checked = false;
					updateHchStatusA(cid);
				}
			}
		}
  }
/*
  for (var p = 1 ; p < (rowsNum+1) ; p ++)
  {
	try 
	{
	  $(checkboxname+p+'').checked = checkall.checked; 
	}
	catch (ex1)
	{}
  }  */
  return true;  
}

function setCalendarValue(hcalName)
{
  var setHcalAll = null;
  switch (hcalName)
  {
  		case "requiredDatetime":
 			setHcalAll = $("needdateAll");
 		 	break;
 	    case "promisedDate":
 	    	setHcalAll = $("promisedDateAll");
 			break;
 		default:
 			break;
  }
  var rowsNum = mygrid.getRowsNum();
  var result;

  rowsNum = rowsNum*1;
  if (setHcalAll.value.trim().length != 0)
  {
    result = setHcalAll.value.trim();
  }
  else
  {
    result = '';
  }
  
  for (var p = 1 ; p < (rowsNum+1) ; p ++)
  {
	try 
	{
	  if(mygrid.cellById(p, mygrid.getColIndexById("requestLineStatus")).getValue() != 'Shipped' &&
	   		mygrid.cellById(p, mygrid.getColIndexById("requestLineStatus")).getValue() != 'Delivered' &&
	   		mygrid.cellById(p, mygrid.getColIndexById("requestLineStatus")).getValue() != 'Invoiced')
	  	mygrid.cells(p, mygrid.getColIndexById(hcalName)).setValue(result);
	}
	catch (ex1)
	{}
  }
  return true;
  
}

function customerIdChanged(c,personnelId,lastName,firstName)
{
  closeTransitWin();
  
  if( $('customerId') == null || $v('customerId').length == 0)  {
     displayCustomerInfo(c,personnelId,lastName,firstName,'Y');
  }
  else {
  	try{
  		currentWindow.close();
  	}
  	catch (ex1){}	
  	
    var currentCustomerId = $v("customerId");
/*
   if (currentCustomerId.trim() != "" && currentCustomerId != c.customerId)  {
     var answer = confirm ("Do you really want to switch to another customer?")
     if (!answer)
       return false;
   }   */
    
	$("customerIdDisplay").innerHTML = "("+c.customerId+")";
	$("customerId").value = c.customerId; 
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
//	$("locationType").value = c.locationType;
	$("billToAddress").value = c.billToAddress;
	$("paymentTerms").value = c.paymentTerms;
	$("creditLimit").value = c.creditLimit;
	$("overdueLimit").value = c.overdueLimit;
	$("overdueLimitBasis").value = c.overdueLimitBasis;
    
		$("currencyId").value = c.currencyId;
		$("headerCurrencyId").value = c.currencyId;
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
	$("headerPriceGroupId").value = c.priceGroupId;   
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
/*		
		try{
			for(var i=0;i< priceListArray.length;i++) {
     	  		if( c.priceGroupId == priceListArray[i].id ) {
     			 	$("priceGroupSpan").innerHTML = priceListArray[i].name;
     			 	break;
     	  		}
	    	}  
		}
		catch(ex){} 
*/	
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
  //  	document.getElementById("opsEntityId").value = c.opsEntityId;  
  		buildCsrDropDown(c.opsEntityId , csrArray);
  		buildPriceListDropDown(c.opsEntityId, priceGroupArray);
	}
	catch(ex){}
	
/*	try{
		for(var i=0;i< document.getElementById("opsEntityId").length;i++) {
     	  	if( c.opsEntityId == document.getElementById("opsEntityId").options[i].value ) {
     			 document.getElementById("opsEntityId").selectedIndex = i;
     			 break;
     	  	}
	    }  
	}
	catch(ex){}  */

	$("availableCreditSpan").innerHTML = c.availableCredit;
	$("shipToUpdatable").value = 'N'; 
	$("billToUpdatable").value = 'N'; 
	$("billToCompanyId").value = c.billToCompanyId;
	$("billToLocationId").value = c.billToLocationId;
	billToAddressChanged(c.addressLine1Display,c.addressLine2Display,c.addressLine3Display,c.addressLine4Display,c.addressLine5Display);
	inputAddressChanged(c.shipToAddressLine1Display,c.shipToAddressLine2Display,c.shipToAddressLine3Display,c.shipToAddressLine4Display,c.shipToAddressLine5Display);

	$("requestor").value = c.defaultCustomerContactId;  
	$("requestorName").value = c.defaultCustomerContactName; 
	if ($("requestorNameDisplay") != null)
	  $("requestorNameDisplay").innerHTML = c.defaultCustomerContactName; 
	$("requestorPhone").value = c.defaultCustomerContactPhone;
	$("requestorFax").value = c.defaultCustomerContactFax;
	$("requestorEmail").value = c.defaultCustomerContactEmail;
	
	requestorChanged();

//	$('submittedBy').value=personnelId;
//    $('enteredBySpan').innerHTML=lastName+","+firstName; 
        
	$('uAction').value="searchCustomer";  // extra statement here ?????!!! overwritten later!!
	$("salesQuoteLineBean").style["display"] = "";

	if(c.creditStatus.toUpperCase() == 'STOP') {
		$("custCreditOverLimit").innerHTML = '<label class="red">(!)</label>';
		try {	
			$("confirmMRSpan").style.display="none";
	  	}catch(ex){}
	}
	else {
		$("custCreditOverLimit").innerHTML = '&nbsp;';
		try {	
			$("confirmMRSpan").style.display="";
	  	}catch(ex){}
	}
  
    displayCashSale(c.billToCompanyId);
//	clearContact();
	
// alert("    creditStatus:"+c.creditStatus); 
	document.getElementById("resultGridDiv").style["display"] = ""; 
	$("showOverCreditLimit").value="true";
	done=true;
//	customerIdChanged11();	

	$("uAction").value  = "changeCustomer"; 

   showPageWait();
   
   if (mygrid != null) {
  	copyDisplayValuesToHiddenColumns();
    mygrid.parentFormOnSubmit();
   		var rowsNum = mygrid.getRowsNum();
   		$("totalRows").value = rowsNum;
   }
   
   document.genericForm.submit();
 
//    document.genericForm.target= "_self";
//	document.genericForm.submit();
  }
}

function getCurrencyId() {
	$("currencyId").value = $v("headerCurrencyId");
	try {
		$("currencyIdSpan").innerHTML = $v("headerCurrencyId");
	}
	catch(ex){}
	
	$("uAction").value  = "save"; 

   showPageWait();

   document.genericForm.submit();
	
}

function getPaymentTerms() {
  $("paymentTerms").value = $v("paymentTermsDisplay")
}

function buildCsrDropDown(opsEntityId , csrArray)
{
   if (!opsEntityId || opsEntityId == '')
   {
      opsEntityId = $("opsEntityId").value;
   }

   var obj = $("customerServiceRepId");
   for (var i = obj.length; i >= 0;i--)
     obj[i] = null;

//  if( arr == null || arr.length == 0 ) 
//   return ;//	  setOption(0,defaultObj.name,defaultObj.id, eleName); 
//  else if( arr.length == 1 )
//	  setOption(0,arr[0].name,arr[0].id, eleName);
//  else {
  	  setOption(0,messagesData.pleaseselect,'',"customerServiceRepId"); 
      var offset = 1 ;
	  for ( var i=0; i < csrArray.length; i++) {
	  	if(opsEntityId == csrArray[i].opsEntityId) {
	    	setOption(offset,csrArray[i].name,csrArray[i].id,"customerServiceRepId");
	    	offset++;	
	    }      
	  }
//  }
  obj.selectedIndex = 0;
}

function buildPriceListDropDown(opsEntityId , priceGroupArray)
{
   if (!opsEntityId || opsEntityId == '')
   {
      opsEntityId = $("opsEntityId").value;
   }

   var obj = $("headerPriceGroupId");
   for (var i = obj.length; i >= 0;i--)
     obj[i] = null;

//  if( arr == null || arr.length == 0 ) 
//   return ;//	  setOption(0,defaultObj.name,defaultObj.id, eleName); 
//  else if( arr.length == 1 )
//	  setOption(0,arr[0].name,arr[0].id, eleName);
//  else {
  	  setOption(0,messagesData.pleaseselect,'',"headerPriceGroupId"); 
      var offset = 1 ;
	  for ( var i=0; i < priceGroupArray.length; i++) {
	  	if(opsEntityId == priceGroupArray[i].opsEntityId) {
	    	setOption(offset,priceGroupArray[i].name,priceGroupArray[i].id,"headerPriceGroupId");
	    	offset++;	
	    }      
	  }
//  }
  obj.selectedIndex = 0;
}

function displayCashSale(billToCompanyId) {
  	if ($('contactLookupBtnDisplay') != null) { 
	  if(billToCompanyId == 'CASH_SALES')
      	$('contactLookupBtnDisplay').style.display = "none";
      else
      	$('contactLookupBtnDisplay').style.display = "";
    }
    
    if ($('shipToBtnDisplay') != null) {
      if(billToCompanyId == 'CASH_SALES')
        $('shipToBtnDisplay').style.display = "none";
      else
      	$('shipToBtnDisplay').style.display = "";
    }
    try {
      if(billToCompanyId == 'CASH_SALES') {
        $('inputBillToBtnSpan').style.display = "";
      }
      else
      	$('inputBillToBtnSpan').style.display = "none";
    } catch(ex) {}
 
    if(billToCompanyId == 'CASH_SALES')
    {		$("requestor").value="0";
      		$("requestorName").className="inputBox";
      		$("requestorPhone").className="inputBox";
      		$("requestorFax").className="inputBox";
      		$("requestorEmail").className="inputBox";
      		$("requestorName").readOnly=false;
      		$("requestorPhone").readOnly=false;
      		$("requestorFax").readOnly=false;
      		$("requestorEmail").readOnly=false;
    }
    else
    {
    		$("requestorName").className="invGreyInputText";
      		$("requestorPhone").className="invGreyInputText";
      		$("requestorFax").className="invGreyInputText";
      		$("requestorEmail").className="invGreyInputText";
      		$("requestorName").readOnly=true;
      		$("requestorPhone").readOnly=true;
      		$("requestorFax").readOnly=true;
      		$("requestorEmail").readOnly=true;

    }
}

function contactChanged(a)
{
	closeTransitWin();
	var selectedCustomerId = a.customerId;
	
	billToAddressChanged(a.billToAddressLine1,a.billToAddressLine2,a.billToAddressLine3,a.billToAddressLine4,a.billToAddressLine5);
	$("billToCompanyId").value = a.billToCompanyId;
	$("billToLocationId").value = a.billToLocationId;
	
//Double check here
	if (a.billToCompanyId == 'CASH_SALES') {
		$("requestor").value = 0;
	}
	else {
		$("requestor").value = a.contactId;
	}
	
	$("requestorName").value = a.contactName; 	
	$("requestorPhone").value = a.phone;
	$("requestorFax").value = a.fax;
	$("requestorEmail").value = a.email;
	try {
		$("emailLink").innerHTML = "<a href='mailto:" + a.email + "'>"+ a.email + "</a>";
	} catch(ex) {}
	
	
	requestorChanged();

	if($v("customerId").length == 0)
	{
	  callToServer("scratchpadmain.do?uAction=searchCustomer&customerId="+selectedCustomerId); 
	}
}

function addressChanged(s)
{
	closeTransitWin();
//	$("locationType").value = s.locationType;
//	$("billToAddress").value = s.billToAddress;
	$("shipToLocationId").value = s.shipToLocationId; 
	$("shipToCompanyId").value = s.shipToCompanyId; 
	$("locationDescDisplay").innerHTML = s.locationDesc;
	
	$("fieldSalesRepId").value = s.fieldSalesRepId;
	$("fieldSalesRepNameSpan").innerHTML = s.fieldSalesRepName;
	 
	if($v("orderType") == 'MR') {
		$("orderShiptoNoteDivAreaUSE").value = s.shiptoNotes; 
		$("orderShiptoNote").value = s.shiptoNotes;
	}
	
	inputAddressChanged(s.shipToAddressLine1Display,s.shipToAddressLine2Display,s.shipToAddressLine3Display,s.shipToAddressLine4Display,s.shipToAddressLine5Display);
	setDefaultPGINV();

}

function inputAddress(ShipToOrBillTo) 
{
  var loc = '';
  getTabName();
  var billToUpdatable = $v("billToUpdatable");
  var shipToUpdatable = $v("shipToUpdatable");
  showTransitWin(messagesData.onetimeaddress);
  if(ShipToOrBillTo == 'S') {
  	if (shipToUpdatable == 'Y')
  		loc = "inputaddress.do?ShipToOrBillTo="+ShipToOrBillTo+"&tabName="+encodeURIComponent(tabName)
  							+"&addressLine1="+encodeURIComponent($v("shipToAddressLine1"))+"&addressLine2="+encodeURIComponent($v("shipToAddressLine2"))+"&addressLine3="+encodeURIComponent($v("shipToAddressLine3"))+"&addressLine4="+encodeURIComponent($v("shipToAddressLine4"))+"&addressLine5="+encodeURIComponent($v("shipToAddressLine5"))
  							+"&country="+encodeURIComponent($v("shipToCountry"))+"&state="+encodeURIComponent($v("shipToState"))+"&city="+encodeURIComponent($v("shipToCity"))+"&zip="+encodeURIComponent($v("shipToZip"));
  	else {
  		loc = "inputaddress.do?ShipToOrBillTo="+ShipToOrBillTo+"&tabName="+encodeURIComponent(tabName);	
  	}
  }
  else {      
      if (billToUpdatable == 'Y')
    	loc = "inputaddress.do?ShipToOrBillTo="+ShipToOrBillTo+"&tabName="+encodeURIComponent(tabName)
  							+"&addressLine1="+encodeURIComponent($v("billToAddressLine1"))+"&addressLine2="+encodeURIComponent($v("billToAddressLine2"))+"&addressLine3="+encodeURIComponent($v("billToAddressLine3"))+"&addressLine4="+encodeURIComponent($v("billToAddressLine4"))+"&addressLine5="+encodeURIComponent($v("billToAddressLine5"))
  							+"&country="+encodeURIComponent($v("billToCountry"))+"&state="+encodeURIComponent($v("billToState"))+"&city="+encodeURIComponent($v("billToCity"))+"&zip="+encodeURIComponent($v("billToZip"));
  	else {
  		loc = "inputaddress.do?ShipToOrBillTo="+ShipToOrBillTo+"&tabName="+encodeURIComponent(tabName);	
  	}
  }
  var winId= 'inputAddress'+$v("prNumber");
  children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"700","250","yes","50","50","20","20","no");
}

function fillAddress(a)
{
  closeTransitWin();
  if(a.ShipToOrBillTo == 'S') {
	inputAddressChanged(a.addressLine1,a.addressLine2,a.addressLine3,a.addressLine4,a.addressLine5);
	if ($("shipToUpdatable").value == 'N')
    {
      $("shipToLocationId").value = '';
    }
    $("shipToCountry").value = a.countryAbbrev;
	$("shipToState").value = a.stateAbbrev;
	$("shipToCity").value = a.city;
	$("shipToZip").value = a.zip;
	$("shipToUpdatable").value = 'Y';
  }
  if(a.ShipToOrBillTo == 'B') {
    billToAddressChanged(a.addressLine1,a.addressLine2,a.addressLine3,a.addressLine4,a.addressLine5);
    
	if ($("billToUpdatable").value == 'N')
    {
        $("billToLocationId").value = '';
    }
	$("billToCountry").value = a.countryAbbrev; 
	$("billToState").value = a.stateAbbrev;
	$("billToCity").value = a.city;
	$("billToZip").value = a.zip;
	$("billToUpdatable").value = 'Y';
  }
}

function billToAddressChanged(addressLine1,addressLine2,addressLine3,addressLine4,addressLine5) {
	$("billToAddressLine1").value = addressLine1;
	$("billToAddressLine2").value = addressLine2;
	$("billToAddressLine3").value = addressLine3;
	$("billToAddressLine4").value = addressLine4;
	$("billToAddressLine5").value = addressLine5; 

	$("billToAddressLine1Span").innerHTML = addressLine1.substr(0,24);
	$("billToAddressLine2Span").innerHTML = addressLine2.substr(0,24);
	$("billToAddressLine3Span").innerHTML = addressLine3.substr(0,24);
	$("billToAddressLine4Span").innerHTML = addressLine4.substr(0,24);
	$("billToAddressLine5Span").innerHTML = addressLine5.substr(0,24);
	$("billToAddressLine1Span").title = addressLine1+" \n" +addressLine2+" \n" +addressLine3+" \n" +addressLine4+" \n" +addressLine5;
	$("billToAddressLine2Span").title = addressLine1+" \n" +addressLine2+" \n" +addressLine3+" \n" +addressLine4+" \n" +addressLine5;
	$("billToAddressLine3Span").title = addressLine1+" \n" +addressLine2+" \n" +addressLine3+" \n" +addressLine4+" \n" +addressLine5;
	$("billToAddressLine4Span").title = addressLine1+" \n" +addressLine2+" \n" +addressLine3+" \n" +addressLine4+" \n" +addressLine5;
	$("billToAddressLine5Span").title = addressLine1+" \n" +addressLine2+" \n" +addressLine3+" \n" +addressLine4+" \n" +addressLine5;
}

function doInitGrid(){
	mygrid = new dhtmlXGridObject('salesQuoteLineBean');
	
    // initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	initGridWithConfig(mygrid,config,-1,true,true);
	if( typeof( jsonMainData ) != 'undefined' ) {		
		mygrid.parse(jsonMainData,"json");
	}	
	
	mygrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	mygrid.attachEvent("onRowSelect",selectRow);
	mygrid.attachEvent("onRightClick",selectRightclick);
//	mygrid.attachEvent("onCheck",checkInputValues);
	
//	setTimeout('loadRowSpans()',100); 
  var lineTotal = 0;	
  var rowsNum = 0;
  try{
	rowsNum = mygrid.getRowsNum()*1;
  } catch(ex){}

  for (var p = 1; p <= rowsNum ; p ++)
  { 
  	var a = 0 ;
  	try{ 
  	  if (cellValue(p,"requestLineStatus").trim() != 'Cancelled' && cellValue(p,"requestLineStatus").trim() != 'Canceled')
  		a = cellValue(p,"extraCharges")*1;
    }catch(ex){}
	lineTotal = lineTotal*1 + a;
/*
	if (cellValue(p,"requestLineStatus") == 'Cancelled') {
		for(var j=0;j<=61;j++) {
	  		mygrid.rowsAr[p].cells[j].style.background = "black";
	  		mygrid.rowsAr[p].cells[j].style.color = "white";
	    }
    }  */
  }
  
//  try {
  var headerChargesTotal = $v("totalHeaderCharge")*1 + lineTotal*1; 
  $("headerCharges").innerHTML = headerChargesTotal.toFixed(2);	
  var totalForTotalSpan = $("materialPriceSpan").innerHTML*1 + $("headerCharges").innerHTML*1
  $("totalSpan").innerHTML = totalForTotalSpan.toFixed(2);
  showHeaderTotal(); 
//  }catch(ex){};

}  

function addPartNumber(p)
{
	
   //closeTransitWin();
   $("salesQuoteLineBean").style["display"] = "";

   if(mygrid == null) {
     doInitGrid(); 
   }  
  
   var ind = mygrid.getRowsNum();  
   var rowid = ind*1+1;
   
   if (ind == 0) {
     $('opsEntityNameSpan').style.display="";  
     $('opsEntityIdDropDownSpan').style.display="none";
     $('priceGroupSpan').style.display="";  
     $('priceGroupIdDropDownSpan').style.display="none";
     $('currencyIdSpan').style.display="";  
     $('currencyIdDropDownSpan').style.display="none";
   }
     
   var thisrow = mygrid.addRow(rowid,"",ind);
 
   mygrid.selectRow(mygrid.getRowIndex(rowid),null,false,false);
     
   mygrid.cells(rowid,mygrid.getColIndexById("permission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("okDoUpdatePermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("quantityPermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("customerPartNoPermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("catalogPricePermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("taxExemptPermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("shipCompletePermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("consolidateShipmentPermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("dropShipOverridePermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("customerPoLinePermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("requiredShelfLifePermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("deliveryTypePermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("requiredDatetimePermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("promisedDatePermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("forceHoldPermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("criticalPermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("inventoryGroupPermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("externalNotePermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("internalNotePermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("purchasingNotePermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("okDoUpdate")).setValue(false);

   mygrid.cells(rowid,mygrid.getColIndexById("lineItem")).setValue(rowid); 
   mygrid.cells(rowid,mygrid.getColIndexById("customerPoLine")).setValue(p.customerPoLine);
   mygrid.cells(rowid,mygrid.getColIndexById("labelSpec")).setValue(p.alternateName);
   mygrid.cells(rowid,mygrid.getColIndexById("partDescription")).setValue(p.partDescription);
   mygrid.cells(rowid,mygrid.getColIndexById("specList")).setValue(p.specList);
   var specCell = '<input type="button" class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'" id="fLookupButton" value="" onclick="getSpecList('+rowid+')"/><input type="button" class="smallBtns" onmouseover="this.className=\'smallBtns smallBtnsOver\'" onMouseout="this.className=\'smallBtns\'" name="None" value="'+messagesData.clear+'" OnClick="clearSpecList('+rowid+')"/>';
   mygrid.cells(rowid,mygrid.getColIndexById("specification")).setValue(specCell);
   mygrid.cells(rowid,mygrid.getColIndexById("hazardous")).setValue(p.hazardous);
   
   mygrid.cells(rowid,mygrid.getColIndexById("quantity")).setValue(p.quantity);
   mygrid.cells(rowid,mygrid.getColIndexById("currencyId")).setValue($v("currencyId"));
   
   var realIndexToServer = rowid-1;
   if(p.mvItem != "Y") {
   	  catalogPriceDisplay = '<input class="inputBox" id="catalogPriceDisplay'+rowid+'" type="text" value="'+p.catalogPrice+'" size="5"  maxlength="11" onchange="unitPriceChanged('+rowid+');" />';
   }
   else
   	  catalogPriceDisplay = messagesData.varies+'<input id="catalogPriceDisplay'+rowid+'" type="hidden" value="'+p.catalogPrice+'" />';
   mygrid.cells(rowid,mygrid.getColIndexById("catalogPriceDisplayXX")).setCValue(catalogPriceDisplay);
   
   mygrid.cells(rowid,mygrid.getColIndexById("priceBreakAvailable")).setValue(p.priceBreakAvailable);  //TODO Add priceBreak here
   	
   if(p.mvItem == 'Y') {
   	   var unitOfSalePriceInput = '<input class="inputBox" id="unitOfSalePriceDisplay'+rowid+'" type="text" value="'+p.unitOfSalePrice+'" size="5"  maxlength="11" onchange="unitOfSalePriceChanged('+rowid+');" />';
	   mygrid.cells(rowid,mygrid.getColIndexById("totalUnitOfSaleQuantity")).setValue(p.totalUnitOfSaleQuantity); 
	   mygrid.cells(rowid,mygrid.getColIndexById("unitOfSale")).setValue(p.unitOfSale); 
	   mygrid.cells(rowid,mygrid.getColIndexById("unitOfSalePriceDisplayXX")).setCValue(unitOfSalePriceInput); 
   } else {
   	   mygrid.cells(rowid,mygrid.getColIndexById("totalUnitOfSaleQuantity")).setValue(''); 
	   mygrid.cells(rowid,mygrid.getColIndexById("unitOfSale")).setValue(''); 
	   mygrid.cells(rowid,mygrid.getColIndexById("unitOfSalePriceDisplayXX")).setCValue(''); 
   }	
   	
   var extPrice = p.catalogPrice*p.quantity;
   mygrid.cells(rowid,mygrid.getColIndexById("extPrice")).setValue(extPrice.toFixed(2));
   
   var margin = "";
   if(p.expectedUnitCost != null && p.expectedUnitCost.trim().length > 0 && p.expectedUnitCost != 0){
       if(p.mvItem == 'N' && p.catalogPrice != null && p.catalogPrice.trim().length > 0) {
		 var marginValue = (p.catalogPrice-p.expectedUnitCost)/p.catalogPrice*100; 
	   	 margin = '<input class="inputBox pointer" id="marginNumber'+rowid+'" type="text" value="'+marginValue.toFixed(1)+'" size="2" onchange="caculateUnitPrice('+rowid+');" />%';
	   }
	   else if(p.mvItem == 'Y' && p.unitOfSalePrice != null && p.unitOfSalePrice.trim().length > 0) {
		 var marginValue = ((p.unitOfSalePrice * p.unitOfSaleQuantityPerEach)-p.expectedUnitCost)/(p.unitOfSalePrice * p.unitOfSaleQuantityPerEach)*100; 
	   	 margin = '<input class="inputBox pointer" id="marginNumber'+rowid+'" type="text" value="'+marginValue.toFixed(1)+'" size="2" onchange="caculateUnitPrice('+rowid+');" />%';
	   }
	   else
	   	 margin = '<input class="inputBox pointer" id="marginNumber'+rowid+'" type="text" value="" size="2" onchange="caculateUnitPrice('+rowid+');" />%';
   }
  
   
   mygrid.cells(rowid,mygrid.getColIndexById("margin")).setValue(margin); //margin
   
   mygrid.cells(rowid,mygrid.getColIndexById("extraCharges")).setValue(p.lineAddCharges);  //addl Charges
   mygrid.cells(rowid,mygrid.getColIndexById("requiredDatetime")).setValue("");  //Need Date
   mygrid.cells(rowid,mygrid.getColIndexById("promisedDate")).setValue("");  //Promised Date
   mygrid.cells(rowid,mygrid.getColIndexById("shipped")).setValue(null);  //Shipped
   mygrid.cells(rowid,mygrid.getColIndexById("pickList")).setValue(null);  //Pick List
   mygrid.cells(rowid,mygrid.getColIndexById("receipt")).setValue(p.qtyOnhand);  //Receipt
   mygrid.cells(rowid,mygrid.getColIndexById("supply")).setValue(p.qtyInpurchasing);  //Supply
   
   mygrid.cells(rowid,mygrid.getColIndexById("requiredShelfLife")).setValue(p.requiredShelfLife); //requiredShelfLife 
   mygrid.cells(rowid,mygrid.getColIndexById("taxExempt")).setValue(""); //tax exempt TODO
   
   mygrid.cells(rowid,mygrid.getColIndexById("shipComplete")).setValue("");  //lineComplete 
   mygrid.cells(rowid,mygrid.getColIndexById("consolidateShipment")).setValue(""); //consolidateShipment 
   
   if( $v("shipComplete") == 'ORDER' ) {
   		 $("shipComplete"+rowid).checked = true; 
	   	 hchstatusA["shipComplete"+rowid] = true;
		 $("consolidateShipment"+rowid).checked = true;
   	   	 hchstatusA["consolidateShipment"+rowid] = true;
   }
   else if( $v("shipComplete") == 'LINE' ) {
		 $("shipComplete"+rowid).checked = true; 
	   	 hchstatusA["shipComplete"+rowid] = true;
   }
   
   mygrid.cells(rowid,mygrid.getColIndexById("deliveryType")).setValue(p.deliveryType); //deliveryType - default is By TODO
   mygrid.cells(rowid,mygrid.getColIndexById("critical")).setValue(p.critical);  //Priority
   
   inventoryGroup[rowid] = inventoryGroupTemp;
   mygrid.cells(rowid,mygrid.getColIndexById("inventoryGroup")).setValue("");
   $("inventoryGroup"+rowid).value = p.inventoryGroup;
   mygrid.cells(rowid,mygrid.getColIndexById("dropShipOverride")).setValue("");  //dropship
   
   if( $v("orderType") == 'MR') {
	    mygrid.cells(rowid,mygrid.getColIndexById("dropShipOverride")).setValue(p.rcptQualityHoldSpec);
	    mygrid.cells(rowid,mygrid.getColIndexById("dropShipOverride")).setValue(p.rcptQualityHoldShelfLife);
   } else {
	   	mygrid.cells(rowid,mygrid.getColIndexById("dropShipOverride")).setValue("");
	    mygrid.cells(rowid,mygrid.getColIndexById("dropShipOverride")).setValue("");
   } 
   
   mygrid.cells(rowid,mygrid.getColIndexById("externalNote")).setValue("");  //Notes
   mygrid.cells(rowid,mygrid.getColIndexById("internalNote")).setValue("");
   mygrid.cells(rowid,mygrid.getColIndexById("purchasingNote")).setValue("");
   mygrid.cells(rowid,mygrid.getColIndexById("customerPartNo")).setValue(p.customerPartNo);   //p.customerPart
   mygrid.cells(rowid,mygrid.getColIndexById("companyId")).setValue($v("companyId")); //company id from header 
   mygrid.cells(rowid,mygrid.getColIndexById("prNumber")).setValue($v("prNumber")); //prNumber id from header
   mygrid.cells(rowid,mygrid.getColIndexById("catalogCompanyId")).setValue("HAAS"); //TODO make this dynamic from add line
   mygrid.cells(rowid,mygrid.getColIndexById("catalogId")).setValue("Global"); //TODO make this dynamic from add line
   mygrid.cells(rowid,mygrid.getColIndexById("partGroupNo")).setValue("1"); //partGroupNo TODO make this dynamic from add line
   mygrid.cells(rowid,mygrid.getColIndexById("unitOfSale")).setValue(p.unitOfSale);
   mygrid.cells(rowid,mygrid.getColIndexById("unitOfSaleQuantityPerEach")).setValue(p.unitOfSaleQuantityPerEach);
   mygrid.cells(rowid,mygrid.getColIndexById("catPartNo")).setValue(p.catPartNo);
   mygrid.cells(rowid,mygrid.getColIndexById("application")).setValue(p.application);
   
   mygrid.cells(rowid,mygrid.getColIndexById("qtyInvoiced")).setValue(p.qtyInvoiced);
   mygrid.cells(rowid,mygrid.getColIndexById("requestLineStatus")).setValue(p.requestLineStatus);
   mygrid.cells(rowid,mygrid.getColIndexById("itemId")).setValue(p.itemId);
   if(p.expectedUnitCost == null || p.expectedUnitCost.trim().length == 0)
     mygrid.cells(rowid,mygrid.getColIndexById("expectedUnitCost")).setValue(null);
   else
     mygrid.cells(rowid,mygrid.getColIndexById("expectedUnitCost")).setValue(p.expectedUnitCost); //TODO ADD INPUT BOX HERE

   mygrid.cells(rowid,mygrid.getColIndexById("specListConcat")).setValue(p.specListConcat); //specListConcat
   mygrid.cells(rowid,mygrid.getColIndexById("specDetailConcat")).setValue(p.specDetailConcat); //specDetailConcat
   mygrid.cells(rowid,mygrid.getColIndexById("specLibraryConcat")).setValue(p.specLibraryConcat);  
   mygrid.cells(rowid,mygrid.getColIndexById("specCocConcat")).setValue(p.specCocConcat); //specCocConcat
   mygrid.cells(rowid,mygrid.getColIndexById("specCoaConcat")).setValue(p.specCoaConcat); //specCoaConcat
   mygrid.cells(rowid,mygrid.getColIndexById("minimumGrossMargin")).setValue(p.minimumGrossMargin); //minimumGrossMargin
   mygrid.cells(rowid,mygrid.getColIndexById("maximumGrossMargin")).setValue(p.maximumGrossMargin); //maximumGrossMargin
   mygrid.cells(rowid,mygrid.getColIndexById("medianSupplyLeadTime")).setValue(p.medianSupplyLeadTime); //medianSupplyLeadTime
   mygrid.cells(rowid,mygrid.getColIndexById("inventoryGroupName")).setValue(p.inventoryGroupName); //inventoryGroupName
   mygrid.cells(rowid,mygrid.getColIndexById("originalOrderQuantity")).setValue(p.quantity); //originalOrderQuantity
   mygrid.cells(rowid,mygrid.getColIndexById("quantityReturnAuthorized")).setValue(p.quantityReturnAuthorized);
   mygrid.cells(rowid,mygrid.getColIndexById("catalogPriceAvailable")).setValue(p.catalogPriceAvailable);
   mygrid.cells(rowid,mygrid.getColIndexById("mvItem")).setValue(p.mvItem);
   mygrid.cells(rowid,mygrid.getColIndexById("catalogPrice")).setValue(''); //catalogPrice
   mygrid.cells(rowid,mygrid.getColIndexById("unitOfSalePrice")).setValue(''); //unitOfSalePrice
   mygrid.cells(rowid,mygrid.getColIndexById("originalSalesQuoteType")).setValue(p.originalSalesQuoteType); //originalSalesQuoteType
   mygrid.cells(rowid,mygrid.getColIndexById("blanketOrderRemainingQty")).setValue(p.blanketOrderRemainingQty);
   mygrid.cells(rowid,mygrid.getColIndexById("shippingReference")).setValue(p.shippingReference); //shippingReference
   mygrid.cells(rowid,mygrid.getColIndexById("forceHold")).setValue(''); //forceHold
   mygrid.cells(rowid,mygrid.getColIndexById("forceHoldComment")).setValue(''); //forceHoldComment
   mygrid.cells(rowid,mygrid.getColIndexById("scrap")).setValue(''); //scrap
   mygrid.cells(rowid,mygrid.getColIndexById("radianPo")).setValue(p.radianPo); //radianPo
   mygrid.cells(rowid,mygrid.getColIndexById("poLine")).setValue(p.poLine); //poLine
   mygrid.cells(rowid,mygrid.getColIndexById("salesOrder")).setValue(p.salesOrder); //salesOrder
   
   $("selectedRowId").value = rowid;  
   selectedRowId = rowid;
//   $("selectedLineItem").value = rowid;  

   if ($v("orderType") == 'MR' && $v("status").toUpperCase() == 'POSUBMIT') {
    	setRowClass(rowid, ''+"grid_yellow"+'Selected');
    	saveRowClass = "grid_yellow";
//    	mygrid.selectRowById(rowid, null, false, false); 
    	$('deleteLineSpan').style.display=""; 
    	showDelete = true;
   }
 
   $("deleteDupLineLink").style.visibility="";
 
   	try{
  		$('noRowSpan11').style.display="";
  		$('noRowSpan12').style.display="";
  	}catch(ex){}
  	
  	try{
  		$('confirmMRSpan').style.display="";
  		$('autoAllocateSpan').style.display="";
  	}catch(ex){}
  	
   showHeaderTotal(); 
   scratchPadResize();
/*
   $("uAction").value  = "save"; 

   showPageWait();
   
   if (mygrid != null) {
   	   copyDisplayValuesToHiddenColumns();
       mygrid.parentFormOnSubmit();
    	
	   var rowsNum = mygrid.getRowsNum();
	   $("totalRows").value = rowsNum;
   }
   document.genericForm.submit();
 
   scratchPadResize();  */
}

function showPageWait() {
  var mainPage = $("mainPage");
    mainPage.style["display"] = "none";
  var transitPage = $("transitPage"); 
    transitPage.style["display"] = "";
}

function showResultWait() {
  var resultGridDiv = $("resultGridDiv");
    resultGridDiv.style["display"] = "none";
  var resultTransitPage = $("resultTransitPage"); 
    resultTransitPage.style["display"] = "";
}

function searchByScratchPadId() {
  showResultWait();
  var quote = $("prNumber").value;
  var scratchPadId = $("scratchPadId").value;

  if ((scratchPadId.length > 0) && (quote != scratchPadId)) {
    var answer = confirm ("Do you really want to switch to another Scratch Pad?")
    if (answer) {
      $("uAction").value = "searchScratchPadId"; 
      $("scratchPadId").value = quote; 
      showPageWait();
      document.genericForm.submit();
    }
    else {
      return false;
    }  
  } 
  else {
    $("uAction").value = "searchScratchPadId";
    $("scratchPadId").value = quote;
    showPageWait();
    document.genericForm.submit();   
  }
}

function searchByPrNumber(prNumber) {
  
  var scratchPadId = $("scratchPadId").value;

  $("uAction").value = "searchScratchPadId";
  $("scratchPadId").value = prNumber;
  showPageWait();
  document.genericForm.submit();  

}

function requestorChanged() {
	if (($("requestor") == null || $v("requestor") == '' || $v("requestor") == 0 || $v("requestor") == '0') &&
       ( $v("billToCompanyId") == 'CASH_SALES')) {
		$("requestorToProcedure").value = '0';
		$("requestorNameToProcedure").value = $v("requestorName");
		$("requestorPhoneToProcedure").value = $v("requestorPhone");
		$("requestorFaxToProcedure").value = $v("requestorFax");
		$("requestorEmailToProcedure").value = $v("requestorEmail");
	}
	else {
		$("requestorToProcedure").value = $v("requestor");
		$("requestorNameToProcedure").value = '';
		$("requestorPhoneToProcedure").value = '';
		$("requestorFaxToProcedure").value = '';
		$("requestorEmailToProcedure").value = '';
	}
}

function inputAddressChanged(shipToAddressLine1,shipToAddressLine2,shipToAddressLine3,shipToAddressLine4,shipToAddressLine5) {
	$("shipToAddressLine1").value = shipToAddressLine1;  
	$("shipToAddressLine2").value = shipToAddressLine2;
	$("shipToAddressLine3").value = shipToAddressLine3;
	$("shipToAddressLine4").value = shipToAddressLine4;
	$("shipToAddressLine5").value = shipToAddressLine5;
	
	$("shipToAddressLine1ToProcedure").value = shipToAddressLine1;
	$("shipToAddressLine2ToProcedure").value = shipToAddressLine2;
	$("shipToAddressLine3ToProcedure").value = shipToAddressLine3;
	$("shipToAddressLine4ToProcedure").value = shipToAddressLine4;
	$("shipToAddressLine5ToProcedure").value = shipToAddressLine5;
	
	setUpShipToAddress(shipToAddressLine1,shipToAddressLine2,shipToAddressLine3,shipToAddressLine4,shipToAddressLine5);
}

function setUpShipToAddress(shipToAddressLine1,shipToAddressLine2,shipToAddressLine3,shipToAddressLine4,shipToAddressLine5) {
	$("shipToAddressLine1Display").innerHTML = shipToAddressLine1.substr(0,24);
	$("shipToAddressLine2Display").innerHTML = shipToAddressLine2.substr(0,24);
	$("shipToAddressLine3Display").innerHTML = shipToAddressLine3.substr(0,24);
	$("shipToAddressLine4Display").innerHTML = shipToAddressLine4.substr(0,24);
	$("shipToAddressLine5Display").innerHTML = shipToAddressLine5.substr(0,24);
	$("shipToAddressLine1Display").title = shipToAddressLine1 +" \n" +shipToAddressLine2 +" \n" +shipToAddressLine3 +" \n" +shipToAddressLine4 +" \n" +shipToAddressLine5;
	$("shipToAddressLine2Display").title = shipToAddressLine1 +" \n" +shipToAddressLine2 +" \n" +shipToAddressLine3 +" \n" +shipToAddressLine4 +" \n" +shipToAddressLine5;
	$("shipToAddressLine3Display").title = shipToAddressLine1 +" \n" +shipToAddressLine2 +" \n" +shipToAddressLine3 +" \n" +shipToAddressLine4 +" \n" +shipToAddressLine5;
	$("shipToAddressLine4Display").title = shipToAddressLine1 +" \n" +shipToAddressLine2 +" \n" +shipToAddressLine3 +" \n" +shipToAddressLine4 +" \n" +shipToAddressLine5;
	$("shipToAddressLine5Display").title = shipToAddressLine1 +" \n" +shipToAddressLine2 +" \n" +shipToAddressLine3 +" \n" +shipToAddressLine4 +" \n" +shipToAddressLine5;
}


function submitSave() { 
  closeTransitWin();
  $("uAction").value = "save";
  
  if($v("inventoryGroup").length == 0)
  {
    alert(messagesData.validvalues+"\n\n"+messagesData.inventorygroup);
    return false;
  }

  if (mygrid != null)
  {  
  	var rowsNum = mygrid.getRowsNum();
  	$("totalRows").value = rowsNum;
  	
  	if ( $v("orderType") == "MR" ) checkDate = true;

  	for (var p = 1 ; p < (rowsNum+1) ; p ++)
  	{
			if(!checkInputValuesEmptyOK(p,"okDoUpdate")) return false;
  	}

  	
   showPageWait();
  	
  	copyDisplayValuesToHiddenColumns();
    mygrid.parentFormOnSubmit();
  }
  
  requestorChanged();
  closeAllchildren();
//  closeAllchildrenNotSelf();

  document.genericForm.submit();
}

function validateRequiredShelfLife(rowId,cellInd) {
	var requiredShelfLife = mygrid.cellById(rowId, mygrid.getColIndexById("requiredShelfLife")).getValue();
    var qtyShipped = mygrid.cellById(rowId, mygrid.getColIndexById("shipped")).getValue().trim();
    if (qtyShipped*1 == 0 && (!(isSignedFloat(requiredShelfLife.trim(),true)) || requiredShelfLife  > 100*1))
	{
	   	   errorMessage = messagesData.validvalues +"\n\n"+ messagesData.requiredshelflife;
		   mygrid.cells(rowId, mygrid.getColIndexById("requiredShelfLife")).setValue("");
		   alert(errorMessage);
	}
}

function showErrors()
{  
  var resulterrorMessages = $("errorMessagesAreaBody");
  var errorMessagesArea = $("errorMessagesArea");
  errorMessagesArea.innerHTML = resulterrorMessages.innerHTML;
  errorMessagesArea.style.display="";

  var dhxWins = new dhtmlXWindows();
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.errors)) {
  // create window first time
  var wrroWin = dhxWins.createWindow(messagesData.errors, 0, 0, 450, 300);
  wrroWin.setText(messagesData.errors);
  wrroWin.clearIcon();  // hide icon
  wrroWin.denyResize(); // deny resizing
  wrroWin.denyPark();   // deny parking
  wrroWin.attachObject("errorMessagesArea");
  wrroWin.attachEvent("onClose", function(wrroWin){wrroWin.hide();});
  wrroWin.center();
  }
  else
  {
    // just show
    dhxWins.window("wrroWin").show();
  }
}


function checkInputValues(rowId,columnId,processConfirm)
{
  if(mygrid.cellById(rowId, mygrid.getColIndexById("requestLineStatus")).getValue() != 'Cancelled' && mygrid.cellById(rowId, mygrid.getColIndexById("requestLineStatus")).getValue() != 'Canceled') {
	 var errorCount = 0;
	 var errorConfirmMRQtyCount = 0;
	 var errorMessage = messagesData.validvalues+"\n";
	 var errorConfirmMRQtyMessage = "";
	 var okValue=  $("okDoUpdate"+rowId).checked;
	 
	 // if( okValue == false ) 
	//	return true;
	
	  var customerPoLine = mygrid.cellById(rowId, mygrid.getColIndexById("customerPoLine")).getValue().trim();
	  if((customerPoLine == "" || customerPoLine == null) && processConfirm == 'B')
	  { 
	   	   errorMessage = errorMessage +"\n"+ messagesData.customerline;
	   	   mygrid.cells(rowId, mygrid.getColIndexById("customerPoLine")).setValue("");
		   errorCount = 1;
	  }
	
	  var quantity = mygrid.cellById(rowId, mygrid.getColIndexById("quantity")).getValue().trim();
	  var qtyShipped = mygrid.cellById(rowId, mygrid.getColIndexById("shipped")).getValue().trim();
	  if (quantity == "" || (!(isPositiveInteger(quantity,false)) && quantity != 0) || quantity*1 < qtyShipped*1)
	  {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.orderqty;
	   	   mygrid.cells(rowId, mygrid.getColIndexById("quantity")).setValue("");
		   errorCount = 1;
	  }
   
	  var pickList = mygrid.cellById(rowId, mygrid.getColIndexById("pickList")).getValue().trim();
	  var receipt = mygrid.cellById(rowId, mygrid.getColIndexById("receipt")).getValue().trim();
	  var supply = mygrid.cellById(rowId, mygrid.getColIndexById("supply")).getValue().trim();
	  var totalQty = qtyShipped*1 + pickList*1 + receipt*1 + supply*1; 
	  var originalOrderQuantity = mygrid.cellById(rowId, mygrid.getColIndexById("originalOrderQuantity")).getValue().trim();
	  if(processConfirm == 'M' && quantity*1 < totalQty*1)
	  { 
	   	   errorConfirmMRQtyMessage = messagesData.orderqtynotlessthanallocatedqty +"\n";
	   	   mygrid.cells(rowId, mygrid.getColIndexById("quantity")).setValue(originalOrderQuantity);
		   errorConfirmMRQtyCount = 1;
	  }
	  
	  var blanketOrderRemainingQty = mygrid.cellById(rowId, mygrid.getColIndexById("blanketOrderRemainingQty")).getValue().trim();
	  if(processConfirm == 'M' && $v("originalSalesQuoteType") == 'Blanket Order' && quantity*1 > blanketOrderRemainingQty*1 + originalOrderQuantity*1)
	  { 
	   	   errorConfirmMRQtyMessage = messagesData.orderqtynotmorethanblanketorderremainingqty +"\n";
	   	   mygrid.cells(rowId, mygrid.getColIndexById("quantity")).setValue(originalOrderQuantity);
		   errorConfirmMRQtyCount = 1;
	  }
	  
	  var requiredShelfLife = mygrid.cellById(rowId, mygrid.getColIndexById("requiredShelfLife")).getValue();
	  if (qtyShipped*1 == 00 && !(isSignedFloat(requiredShelfLife.trim(),false)) || requiredShelfLife  > 100*1)
	   {
	   	   errorMessage = errorMessage +"\n"+ messagesData.requiredshelflife;
		   mygrid.cells(rowId, mygrid.getColIndexById("requiredShelfLife")).setValue("");
		   errorCount = 1;
	   }

	   var externalNote = mygrid.cellById(rowId, mygrid.getColIndexById("externalNote")).getValue().trim();
	   if (externalNote.length > 4000)
	   {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.externalnotes;
	   	   mygrid.cells(rowId, mygrid.getColIndexById("externalNote")).setValue(externalNote.substring(0,4000));
		   errorCount = 1;
	   }
	   
	   var internalNote = mygrid.cellById(rowId, mygrid.getColIndexById("internalNote")).getValue().trim();
	   if (internalNote.length > 4000)
	   {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.internalnotes;
	   	   mygrid.cells(rowId, mygrid.getColIndexById("internalNote")).setValue(internalNote.substring(0,4000));
		   errorCount = 1;
	   }
	   
	   var purchasingNote = mygrid.cellById(rowId, mygrid.getColIndexById("purchasingNote")).getValue().trim();
	   if (purchasingNote.length > 4000)
	   {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.purchasingnotes;
	   	   mygrid.cells(rowId, mygrid.getColIndexById("purchasingNote")).setValue(purchasingNote.substring(0,4000));
		   errorCount = 1;
	   }
	   
	   var forceHoldComment = mygrid.cellById(rowId, mygrid.getColIndexById("forceHoldComment")).getValue().trim();
	   if (forceHoldComment.length > 512)
	   {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.forceholdcomment;
	   	   mygrid.cells(rowId, mygrid.getColIndexById("forceHoldComment")).setValue(forceHoldComment.substring(0,512));
		   errorCount = 1;
	   }
	
	   var needDate = mygrid.cellById(rowId, mygrid.getColIndexById("requiredDatetime")).getValue().trim(); //$v("requiredDatetime"+rowId);
	   var promisedDate = mygrid.cellById(rowId, mygrid.getColIndexById("promisedDate")).getValue().trim(); //$v("promisedDate"+rowId);
       var deliveryType = mygrid.cellById(rowId, mygrid.getColIndexById("deliveryType")).getValue().trim();
           
       if ((checkDate || deliveryType == 'Schedule') && needDate.length == 0)	   
	   {   
	   	   		errorMessage = errorMessage +"\n"+ messagesData.needdate;
		   		errorCount = 1;
	   }
	   
	   if (checkDate && promisedDate.length == 0)		
	   {  
	   	   		errorMessage = errorMessage +"\n"+ messagesData.promiseddate;
		   		errorCount = 1;
	   }  
	   
	   var catalogPrice = $v("catalogPriceDisplay"+rowId);
	   var catalogPriceAvailable = mygrid.cellById(rowId, mygrid.getColIndexById("catalogPriceAvailable")).getValue().trim();
	   var mvItem = mygrid.cellById(rowId, mygrid.getColIndexById("mvItem")).getValue().trim();
//	   var catalogPrice = mygrid.cellById(rowId, mygrid.getColIndexById("catalogPrice")).getValue().trim();
	   if (($v("orderType") != 'Scratch Pad') && mvItem != 'Y' && (catalogPrice == "" || !isFloat(catalogPrice,false)))
	   {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.unitprice;
	   	   $("catalogPriceDisplay"+rowId).value = "";
//	   	   mygrid.cells(rowId, mygrid.getColIndexById("catalogPrice")).setValue("");
		   errorCount = 1;
	   }
	   
	   var unitOfSalePrice = $v("unitOfSalePriceDisplay"+rowId);
	   if (($v("orderType") == 'Blanket Order' || catalogPriceAvailable == 'Y') && mvItem == 'Y' && (unitOfSalePrice == "" || !isFloat(unitOfSalePrice,false)))
	   {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.uosp;
	   	   $("unitOfSalePriceDisplay"+rowId).value = "";
//	   	   mygrid.cells(rowId, mygrid.getColIndexById("catalogPrice")).setValue("");
		   errorCount = 1;
	   }
	   
	   checkDate = false;

	   if (errorCount > 0 || errorConfirmMRQtyCount > 0 )
	   {
	   	 if (errorCount > 0 && errorConfirmMRQtyCount > 0)
		     alert(errorConfirmMRQtyMessage +"\n"+ errorMessage);
		 else if (errorCount > 0)
		 	 alert(errorMessage);
		 else if (errorConfirmMRQtyCount > 0)
		 	 alert(errorConfirmMRQtyMessage);
		 	 
		 mygrid.selectRowById(rowId,null,false,false);
		 return false;
	   }  
   }
   return true;
}

function checkInputValuesEmptyOK(rowId,columnId) 
{
  if(mygrid.cellById(rowId, mygrid.getColIndexById("requestLineStatus")).getValue() != 'Cancelled' && mygrid.cellById(rowId, mygrid.getColIndexById("requestLineStatus")).getValue() != 'Canceled') {
	 var errorCount = 0;
	 var errorMessage = messagesData.validvalues+"\n\n";
	 
	 var okValue=  $("okDoUpdate"+rowId).checked;
	
	// if( okValue == false ) 
	//	return true;
	  var quantity = mygrid.cellById(rowId, mygrid.getColIndexById("quantity")).getValue().trim();
	  var qtyShipped = mygrid.cellById(rowId, mygrid.getColIndexById("shipped")).getValue().trim();
	  if ((!(isPositiveInteger(quantity,true)) && quantity != 0) || quantity*1 < qtyShipped*1)
	   {
	   	   errorMessage = errorMessage +"\n"+ messagesData.orderqty;
	   	   mygrid.cells(rowId, mygrid.getColIndexById("quantity")).setValue("");
		   errorCount = 1;
	   }
	
/*	   var catalogPrice = $v("catalogPrice"+rowId);  
//	   var catalogPrice = mygrid.cellById(rowId, mygrid.getColIndexById("catalogPrice")).getValue().trim();
	   if (!(isFloat(catalogPrice,true)))
	   {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.unitprice;
	   	   $("catalogPrice"+rowId) = "";
//	   	   mygrid.cells(rowId, mygrid.getColIndexById("catalogPrice")).setValue("");
		   errorCount = 1;
	   }  */
	   
	   var catalogPrice = $v("catalogPriceDisplay"+rowId);
	   var catalogPriceAvailable = mygrid.cellById(rowId, mygrid.getColIndexById("catalogPriceAvailable")).getValue().trim();
	   var mvItem = mygrid.cellById(rowId, mygrid.getColIndexById("mvItem")).getValue().trim();
//	   var catalogPrice = mygrid.cellById(rowId, mygrid.getColIndexById("catalogPrice")).getValue().trim();
	   if (catalogPriceAvailable == 'Y' && mvItem != 'Y' && !isFloat(catalogPrice,false))
	   {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.unitprice;
	   	   $("catalogPriceDisplay"+rowId).value = "";
//	   	   mygrid.cells(rowId, mygrid.getColIndexById("catalogPrice")).setValue("");
		   errorCount = 1;
	   }
  
	   var unitOfSalePrice = $v("unitOfSalePriceDisplay"+rowId);
	   if (catalogPriceAvailable == 'Y' && mvItem == 'Y' && !isFloat(unitOfSalePrice,false))
	   {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.uosp;
	   	   $("unitOfSalePriceDisplay"+rowId).value = "";
//	   	   mygrid.cells(rowId, mygrid.getColIndexById("catalogPrice")).setValue("");
		   errorCount = 1;
	   }

	  var requiredShelfLife = mygrid.cellById(rowId, mygrid.getColIndexById("requiredShelfLife")).getValue();
	  if (qtyShipped*1 == 00 && !(isSignedFloat(requiredShelfLife.trim(),true)) || requiredShelfLife  > 100*1)
	   {
	   	   errorMessage = errorMessage +"\n"+ messagesData.requiredshelflife;
		    mygrid.cells(rowId, mygrid.getColIndexById("requiredShelfLife")).setValue("");
		   errorCount = 1;
	   }
	   
	   var externalNote = mygrid.cellById(rowId, mygrid.getColIndexById("externalNote")).getValue().trim();
	   if (externalNote.length > 4000)
	   {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.externalnotes;
	   	   mygrid.cells(rowId, mygrid.getColIndexById("externalNote")).setValue(externalNote.substring(0,4000));
		   errorCount = 1;
	   }
	   
	   var internalNote = mygrid.cellById(rowId, mygrid.getColIndexById("internalNote")).getValue().trim();
	   if (internalNote.length > 4000)
	   {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.internalnotes;
	   	   mygrid.cells(rowId, mygrid.getColIndexById("internalNote")).setValue(internalNote.substring(0,4000));
		   errorCount = 1;
	   }
	   
	   var purchasingNote = mygrid.cellById(rowId, mygrid.getColIndexById("purchasingNote")).getValue().trim();
	   if (purchasingNote.length > 4000)
	   {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.purchasingnotes;
	   	   mygrid.cells(rowId, mygrid.getColIndexById("purchasingNote")).setValue(purchasingNote.substring(0,4000));
		   errorCount = 1;
	   }
	   
	   var forceHoldComment = mygrid.cellById(rowId, mygrid.getColIndexById("forceHoldComment")).getValue().trim();
	   if (forceHoldComment.length > 512)
	   {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.forceholdcomment;
	   	   mygrid.cells(rowId, mygrid.getColIndexById("forceHoldComment")).setValue(forceHoldComment.substring(0,512));
		   errorCount = 1;
	   }
	 
	   var needDate = mygrid.cellById(rowId, mygrid.getColIndexById("requiredDatetime")).getValue().trim(); //$v("requiredDatetime"+rowId);
	   var promisedDate = mygrid.cellById(rowId, mygrid.getColIndexById("promisedDate")).getValue().trim(); //$v("promisedDate"+rowId);
       var deliveryType = mygrid.cellById(rowId, mygrid.getColIndexById("deliveryType")).getValue().trim();
       if (deliveryType == 'Schedule' && needDate.length == 0)
	   {   
	   	   	errorMessage = errorMessage +"\n"+ messagesData.needdate;
		   	errorCount = 1;
	   }
/*	   if (needDate.length > 0 && promisedDate.length > 0 && dateToIntString(needDate) >  dateToIntString(promisedDate))
	   {  
	   	   	errorMessage = errorMessage +"\n"+ messagesData.promiseddate;
		   	errorCount = 1;
	   }  */
	   checkDate = false;
	
	   if (errorCount > 0)
	   {
	     alert(errorMessage);
		 mygrid.selectRowById(rowId,null,false,false);
		 return false;
	   }  
   }
   return true;
}


function displayGridSearchDuration1() {
   var totalLines = $("totalLines");
   if (totalLines.value != null) {
     if (totalLines.value*1 > 0) {
       var startSearchTime = $("startSearchTime");
       var now = new Date();
       var minutes = 0;
       var seconds = 0;
       //the duration is in milliseconds
       var searchDuration = now.getTime()-(startSearchTime.value*1);
       if (searchDuration > (1000*60)) {   //calculating minutes
         minutes = Math.round((searchDuration / (1000*60)));
         var remainder = searchDuration % (1000*60);
         if (remainder > 0) {   //calculating seconds
           seconds = Math.round(remainder / 1000);
         }
       }else {  //calculating seconds
         seconds = Math.round(searchDuration / 1000);
       }  
       var footer = $("footer");
       if (minutes != 0) {
         footer.innerHTML = messagesData.recordFound+": "+totalLines.value+" -- "+messagesData.searchDuration+":"+minutes+" "+messagesData.minutes+" "+seconds+" "+messagesData.seconds;
       }else {
         footer.innerHTML = messagesData.recordFound+": "+totalLines.value+" -- "+messagesData.searchDuration+":"+seconds+" "+messagesData.seconds;
       }
     }else {
       var footer = $("footer");
       footer.innerHTML ="";
     }
   }else {
     var footer = $("footer");
     footer.innerHTML ="&nbsp;";
   }
}


function changeUpdatableColumnHdr()
{
	mygrid.hdr.rows[1].cells[3].style.background = '#585fa0';
}

function selectRightclick(rowId,cellInd,ee){
 selectRow(rowId, cellInd);
 vitems = new Array();
 var quantity = gg("quantity");
 
 if ($v("orderType") == 'MR' && (cellValue(rowId,"requestLineStatus") == 'Cancelled' || cellValue(rowId,"requestLineStatus") == 'Canceled')) 
 	vitems[vitems.length ] = "text="+messagesData.additionalcharges+";offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;";
 else if (!(isPositiveInteger(quantity,false) && quantity != '&nbsp;') || $v("orderType") == 'Blanket Order')
 {
    vitems[vitems.length ] = "text="+messagesData.additionalcharges+";offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;";     
 }
 else
 {
     vitems[vitems.length ] = "text="+messagesData.additionalcharges+";url=javascript:addLineCharges();";
 }

 if ($v("orderType") == 'MR' && (cellValue(rowId,"requestLineStatus") == 'Shipped' || cellValue(rowId,"requestLineStatus") == 'Delivered'  || cellValue(rowId,"requestLineStatus") == 'Invoiced' || cellValue(rowId,"requestLineStatus") == 'Partial Del.') && cellValue(rowId,"quantityReturnAuthorized")*1 < cellValue(rowId,"shipped")*1 && cellValue(rowId,"salesOrder").length == 0) 
 	vitems[vitems.length ] = "text="+messagesData.customerreturnrequest+";url=javascript:showCustomerReturnRequest();";
 else
 	vitems[vitems.length ] = "text="+messagesData.customerreturnrequest+";offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;";
 
 if ($v("orderType") == 'MR' && cellValue(rowId,"quantity") > 0*1 && cellValue(rowId,"requiredShelfLife").length > 0 && cellValue(rowId,"quantity") != cellValue(rowId,"shipped")  && cellValue(rowId,"requestLineStatus") != 'Cancelled' && cellValue(rowId,"requestLineStatus") != 'Canceled' && cellValue(rowId,"salesOrder").length == 0) 
 	vitems[vitems.length ] = "text="+messagesData.autoallocateline+";url=javascript:saveBeforeAutoAllocateLine();";
 else
 	vitems[vitems.length ] = "text="+messagesData.autoallocateline+";offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;";
 
 if ($v("orderType") == 'MR' && (cellValue(rowId,"quantity")*1 <= cellValue(rowId,"shipped")*1 || cellValue(rowId,"requestLineStatus") == 'Cancelled' || cellValue(rowId,"requestLineStatus") == 'Canceled' || cellValue(rowId,"salesOrder").length > 0))
 	vitems[vitems.length ] = "text="+messagesData.showallocatableig+" (F6)"+";offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;";
 else if ($v("orderType") == 'Blanket Order')
 	vitems[vitems.length ] = "text="+messagesData.showallocatableig+" (F6)"+";offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;"; 
 else
 	vitems[vitems.length ] = "text="+messagesData.showallocatableig+" (F6)"+";url=javascript:allocationPopup('IG');";
 	
 if ($v("orderType") == 'MR' && (cellValue(rowId,"quantity") == cellValue(rowId,"shipped") || cellValue(rowId,"requestLineStatus") == 'Cancelled' || cellValue(rowId,"requestLineStatus") == 'Canceled' || cellValue(rowId,"salesOrder").length > 0))
 	vitems[vitems.length ] = "text="+messagesData.showallocatableregion+";offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;";
 else if ($v("orderType") == 'Blanket Order') 
 	vitems[vitems.length ] = "text="+messagesData.showallocatableregion+";offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;";
 else
 	vitems[vitems.length ] = "text="+messagesData.showallocatableregion+";url=javascript:allocationPopup('REGION');";
 
 if ($v("orderType") == 'MR' && (cellValue(rowId,"quantity") == cellValue(rowId,"shipped") || cellValue(rowId,"requestLineStatus") == 'Cancelled' || cellValue(rowId,"requestLineStatus") == 'Canceled' || cellValue(rowId,"salesOrder").length > 0)) 
 	vitems[vitems.length ] = "text="+messagesData.showallocatableglobal+";offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;";
 else if ($v("orderType") == 'Blanket Order') 
 	vitems[vitems.length ] = "text="+messagesData.showallocatableglobal+";offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;";
 else
 	vitems[vitems.length ] = "text="+messagesData.showallocatableglobal+";url=javascript:allocationPopup('GLOBAL');";
 
 vitems[vitems.length ] = "text="+messagesData.showpreviousordersforcustomer+";url=javascript:showPreHistory();";
 vitems[vitems.length ] = "text="+messagesData.showallpreviousorders+";url=javascript:showAllPreHistory();";
 vitems[vitems.length ] = "text="+messagesData.showpohistory+";url=javascript:showPoHistory();";
 vitems[vitems.length ] = "text="+messagesData.showquotationallhistoryforcustomer+";url=javascript:showQuotationHistory();";
 vitems[vitems.length ] = "text="+messagesData.showquotationallhistory+";url=javascript:showAllQuotationHistory();";
 
 vitems[vitems.length ] = "text="+messagesData.showpricelistinfo+";url=javascript:showPriceList();";
 vitems[vitems.length ] = "text="+messagesData.showcurrentsourcinginfo+";url=javascript:showSourcingInfo();";
// alert(cellValue(rowId,"quantity") +"    shipped: " + cellValue(rowId,"shipped").length);
 if ($v("orderType") == 'MR' && ($v("status").toUpperCase() == 'POSUBMIT' || $v("status").toUpperCase() == 'CONFIRMED') && cellValue(rowId,"requestLineStatus") != 'Cancelled' && cellValue(rowId,"requestLineStatus") != 'Canceled') {
// 	if (cellValue(rowId,"quantity") > cellValue(rowId,"shipped") || cellValue(rowId,"shipped").length == 0)
	if ((cellValue(rowId,"shipped").length == 0 || cellValue(rowId,"shipped") == 0) && (cellValue(rowId,"pickList").length == 0 || cellValue(rowId,"pickList") == 0))
 		vitems[vitems.length ] = "text="+messagesData.cancelmrliine+";url=javascript:cancelMRLine();";
 	else
 		vitems[vitems.length ] = "text="+messagesData.cancelmrliine+";offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;";
 }
//alert( $v("orderType")+"   "+cellValue(rowId,"rcptQualityHoldSpec")+"     "+cellValue(rowId,"rcptQualityHoldShelfLife"));
 if ($v("orderType") == 'MR') {
 	if (cellValue(rowId,"rcptQualityHoldSpec") == 'X' || cellValue(rowId,"rcptQualityHoldShelfLife") == 'X')
 		vitems[vitems.length ] = "text="+messagesData.releasemrline+";url=javascript:releaseMRLine();";
 	else
 		vitems[vitems.length ] = "text="+messagesData.releasemrline+";offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;";
 	
 	vitems[vitems.length ] = "text="+messagesData.mrlineallocation+" (F8)"+";url=javascript:showMrLineAllocationReport();";
 }

 var text = messagesData.leadtimeplotpart1;
 if (cellValue(selectedRowId,"medianSupplyLeadTime").length > 0)
  	text +=  " " + messagesData.leadtimeplotpart2.replace(/[{]0[}]/g,cellValue(selectedRowId,"medianSupplyLeadTime"));
 
 vitems[vitems.length ] = "text="+text+";url=javascript:leadTimePlots();";
 
 if (cellValue(selectedRowId,"itemId").length > 0) {
	 vitems[vitems.length ] = "text="+messagesData.itemnotes+";url=javascript:showItemNotes();";
	 vitems[vitems.length ] = "text="+messagesData.catalogitemnotes+";url=javascript:showCatalogItemNotes();";
	 vitems[vitems.length ] = "text="+messagesData.supplieritemnotes+";url=javascript:supplierItemNotes();";
	 vitems[vitems.length ] = "text="+messagesData.quickview+" (F10)"+";url=javascript:showQuickView();";
 } else {
 	 vitems[vitems.length ] = "text="+messagesData.itemnotes+";offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;";
 	 vitems[vitems.length ] = "text="+messagesData.catalogitemnotes+";offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;";
 	 vitems[vitems.length ] = "text="+messagesData.supplieritemnotes+";offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;";
 }
 
 replaceMenu('addCharges',vitems);  
   
 toggleContextMenu('addCharges');
}

function clearRightclick() {
	toggleContextMenu('contextMenu');
} 

function replaceMenu(menuname,menus){
	  var i = mm_returnMenuItemCount(menuname);

	  for(;i> 1; i-- )
			mm_deleteItem(menuname,i);

	  for( i = 0; i < menus.length; ){
 		var str = menus[i];

 		i++;
		mm_insertItem(menuname,i,str);
		// delete original first item.
    	if( i == 1 ) mm_deleteItem(menuname,1);
      }
}

function releaseMRLine() {
  $("uAction").value  = "releaseMRLine"; 
    
  showPageWait();
  
  if (mygrid != null) {
  	copyDisplayValuesToHiddenColumns();
    mygrid.parentFormOnSubmit();
  }

  document.genericForm.submit();

}

function releaseMR() {
  $("uAction").value  = "releaseMR"; 
  
  var count = 0;
  var warningCount = 0;
  var warningMsg = messagesData.linesnotonqualityhold;
  var lines = "";
  var rowsNum = mygrid.getRowsNum();
 
  for (var p = 1; p < (rowsNum+1) ; p ++)
  {
		var cid = "okDoUpdate"+p;
		if( ! $(cid).disabled && $(cid).checked && cellValue(p,"rcptQualityHoldSpec") != "X" && cellValue(p,"rcptQualityHoldShelfLife") != "X") {
			$(cid).checked = false;
			updateHchStatusA(cid);
			$("chkAllOkDoUpdate").checked = false;
			lines += ", "+cellValue(p,"lineItem");
			warningCount++;
		} else if( ! $(cid).disabled && $(cid).checked && (cellValue(p,"rcptQualityHoldSpec") == "X" || cellValue(p,"rcptQualityHoldShelfLife") == "X")) {
	      	count++;
	    }
  }
  
  if(warningCount > 0)
  	alert(warningMsg.replace(/[{]0[}]/g,lines));
  	
  if(count > 0) {  
	  showPageWait();
	  
	  if (mygrid != null) {
  		copyDisplayValuesToHiddenColumns();
    	mygrid.parentFormOnSubmit();
   	  }
	
	  document.genericForm.submit();
  }
  else if (warningCount == 0)
  	alert(messagesData.norowselected);

}

function showMrLineAllocationReport() {
	var companyId  = pp('companyId');   
	var mrNumber  =  $v("prNumber");    	
	var lineItem  =  cellValue(selectedRowId,'lineItem'); 
	if (companyId.length>0 )
	{
		var loc = "mrallocationreportmain.do?fromCustomerOrdertracking=Y&companyId="+companyId+"&mrNumber="+mrNumber+"&lineItem="+lineItem+"";
		children[children.length] = openWinGeneric(loc,"showMrAllocationReport","800","550","yes","80","80","no");
		//children[children.length] = openWinGenericDefault(loc,"showMrAllocationReport","yes","80","80","no");
	}
}

function showQuickView() {
	var inventoryGroup  =  cellValue(selectedRowId,'inventoryGroup');
	var inventoryGroupName  =  cellValue(selectedRowId,'inventoryGroupName'); 
	var itemId  =  cellValue(selectedRowId,'itemId');
	
	var loc = "quickquote.do?readonly=Y&opsEntityId="+pp('opsEntityId')+"&inventoryGroupName="+inventoryGroupName+
			  "&itemDesc="+gg("partDescription")+"&currencyId="+$v("currencyId")+
			  "&inventoryGroup="+inventoryGroup+"&itemId="+itemId;
	children[children.length] = openWinGeneric(loc,"quickView","1100","800","yes","80","80","no");
	//children[children.length] = openWinGenericDefault(loc,"quickView","yes","80","80","no");
}

function leadTimePlots() {
    children[children.length] = openWinGeneric('createsupplyleadtimechart.do?catPartNo='+ encodeURIComponent( cellValue(selectedRowId,"catPartNo") ) +
            '&inventoryGroup='+ cellValue(selectedRowId,"inventoryGroup") +
            '&catalogId='+cellValue(selectedRowId,"catalogId")+
            '&catalogCompanyId='+cellValue(selectedRowId,"catalogCompanyId") +
            '&partGroupNo='+ cellValue(selectedRowId,"partGroupNo") +
            '&inventoryGroupName=' + cellValue(selectedRowId,"inventoryGroupName")
            ,"LeadTimePlots","800","400",'yes' );
	/*children[children.length] = openWinGenericDefault('createsupplyleadtimechart.do?catPartNo='+ encodeURIComponent( cellValue(selectedRowId,"catPartNo") ) +
            '&inventoryGroup='+ cellValue(selectedRowId,"inventoryGroup") +
            '&catalogId='+cellValue(selectedRowId,"catalogId")+
            '&catalogCompanyId='+cellValue(selectedRowId,"catalogCompanyId") +
            '&partGroupNo='+ cellValue(selectedRowId,"partGroupNo") +
            '&inventoryGroupName=' + cellValue(selectedRowId,"inventoryGroupName")
            ,"LeadTimePlots",'yes' );*/
}

function showSourcingInfo() {
//	  loc = "/tcmIS/distribution/showsupplierpl.do?itemId=" + gg('itemId');
	  var loc = "/tcmIS/distribution/editpricelist.do?uAction=search&searchField=itemId|number&searchMode=is&searchArgument=" + gg('itemId');
	  loc = loc + "&itemId=" +gg('itemId');
	  loc = loc + "&inventoryGroup=" +gg('inventoryGroup');
	  loc = loc + "&showExpired=Y";// +inventoryGroup +
      loc = loc + "&opsEntityId="+pp('opsEntityId');
//	  openWinGeneric(loc,"showsupplierpl","800","600","yes","50","50")

      children[children.length] = openWinGeneric(loc, "showSourcingInfo"+pp('prNumber').replace(/[.]/,"_"), "1024", "600", "yes", "50", "50");
      //children[children.length] = openWinGenericDefault(loc, "showSourcingInfo"+pp('prNumber').replace(/[.]/,"_"), "yes", "50", "50");
}

function showPriceList() {
	var loc = 
		  "/tcmIS/distribution/showpricelist.do?uAction=search" +
		  "&searchField=partName&searchMode=is&searchArgument=" + gg('labelSpec') +
		  "&priceGroupId="+pp('priceGroupId')+
		  "&opsEntityId="+pp('opsEntityId');
   	var winId= 'showPriceList'+$v("prNumber");
   	
	children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"900","600","yes","50","50","20","20","no");
   	//children[children.length] = openWinGenericDefault(loc,winId.replace('.','a'),"yes","50","50","20","20","no");
}

function showItemNotes() {
	var loc = "/tcmIS/supply/edititemnotes.do?itemId=" + gg('itemId');
   	var winId= 'showItemNotes'+$v("prNumber");
   	
	children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"800","600","yes","50","50","20","20","no");
   	//children[children.length] = openWinGenericDefault(loc,winId.replace('.','a'),"yes","50","50","20","20","no");
}

function showCatalogItemNotes() {
	var loc = "/tcmIS/distribution/catalogitemnotesmain.do?itemId=" + gg('itemId');
   	var winId= 'showCatalogItemNotes'+$v("prNumber");
   	
	children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"900","600","yes","50","50","20","20","no");
   	//children[children.length] = openWinGenericDefault(loc,winId.replace('.','a'),"yes","50","50","20","20","no");
}

function supplierItemNotes() {
  var itemId = gg('itemId');
  var itemDesc = gg('partDescription');
  var opsEntityId = pp('opsEntityId');
  
  loc = "/tcmIS/supply/showsupplieritemnotes.do?uAction=search&searchMode=is&searchField=itemId&searchArgument="+ itemId+
  		"&itemId="+itemId+ "&itemDesc="+itemDesc+
  		"&opsEntityId="+opsEntityId;
  openWinGeneric(loc,"editItemNotes","1000","300","yes","50","50");
  //openWinGenericDefault(loc,"editItemNotes","yes","50","50");
}

function allocationPopup(searchKey,auto) {
  popdown(); // This prevents menu to popup again unnecessarily
  
  var errorCount = 0;
  var errorMessage = messagesData.validvalues+"\n\n";
  var quantity = mygrid.cellById(selectedRowId, mygrid.getColIndexById("quantity")).getValue();
  if (!(isPositiveInteger(quantity.trim(),false)))
   {  
   	   errorMessage = errorMessage +"\n"+ messagesData.orderqty;
   	   mygrid.cells(selectedRowId, mygrid.getColIndexById("quantity")).setValue("");
	   errorCount = 1;
   }
  
  var requiredShelfLife = mygrid.cellById(selectedRowId, mygrid.getColIndexById("requiredShelfLife")).getValue(); 
  if (!(isSignedFloat(requiredShelfLife.trim(),false)))
   {
   	   errorMessage = errorMessage +"\n"+ messagesData.requiredshelflife;
	    mygrid.cells(selectedRowId, mygrid.getColIndexById("requiredShelfLife")).setValue("");
	   errorCount = 1;
   }
  
   if (errorCount > 0)
   {
   	 alert(errorMessage);
//	 mygrid.selectRowById(selectedRowId,null,false,false);
	 return;
   }  
   
	  var autoAllocate = "";
	  if( auto ) 
		  autoAllocate = "&uAction=autoAllocate";
	  var bcShipComplete = pp('shipComplete');
	  var shipComplete= 'N';
	  var consolidateShipment = 'N';
	  if( bcShipComplete == 'ORDER' ) {
		  shipComplete = 'Y';
		  consolidateShipment = 'Y';
	  }
	  else if( bcShipComplete == 'LINE' ) {
		  shipComplete = 'Y';
	  }

    var scrap = 'n';    
    if( pp('orderType') == 'MR' && !$("scrap"+selectedRowId).disabled && $("scrap"+selectedRowId).checked) {
     scrap = 'y';
    }
	  showTransitWin(messagesData.allocation);
	  
	  var loc = 
		  "/tcmIS/distribution/allocation.do"+
		  "?companyId=" +pp('companyId')+
		  "&facilityId="+pp('facilityId')+
		  "&itemId="+ gg('itemId')+
//		  "&refInventoryGroup="+pp('inventoryGroup')+
		  "&inventoryGroup="+gg('inventoryGroup')+
		  "&specList="+ gg('specListConcat')+
		  "&specification="+ gg('specList')+
		  "&orderPrNumber="+pp('prNumber')+
          "&scrap="+scrap+
		  "&shipToCompanyId="+pp('shipToCompanyId')+
		  "&shipToLocationId="+pp('shipToLocationId')+
		  "&billToCompanyId="+pp('billToCompanyId')+
		  "&billToLocationId="+pp('billToLocationId')+
		  "&curpath="+getcurpath()+
		  "&opsEntityId="+pp('opsEntityId')+
		  "&priceGroupId="+pp('priceGroupId')+
          "&needDateString="+cellValue(selectedRowId,'requiredDatetime')+
          "&labelSpec="+gg('labelSpec')+
		  "&incoTerms="+pp('incoTerms')+
		  "&searchKey="+searchKey+
		  autoAllocate+
		  "&opsCompanyId="+pp('opsCompanyId');
          if (gg('requiredShelfLife') > 0)
          {
          loc = loc+ "&remainingShelfLifePercent="+gg('requiredShelfLife');
          }
          else
          {
            loc = loc+ "&remainingShelfLifePercent="+pp('shelfLifeRequired');
          }
          
          if(gg('deliveryType') == 'Schedule') {
          	loc = loc+"&promisedDate="+gg('promisedDate');
          }
          
          loc = loc+"&shipComplete="+ shipComplete +
		  "&consolidateShipment="+consolidateShipment+
		  "&specDetailList="+ gg('specDetailConcat')+
		  "&specLibraryList="+ gg('specLibraryConcat')+
		  "&specCocList="+ gg('specCocConcat')+
		  "&specCoaList="+ gg('specCoaConcat')+
		  "&currencyId="+gg('currencyId')+
		  "&scratchPadLineItem="+gg('lineItem')+
		  "&orderQuantity="+gg('quantity')+
		  "&orderType="+pp('orderType');
          if (gg('mvItem') == "Y")
          {
          loc = loc+"&itemType=MV";
          }
          else
          {
          loc = loc+ "&itemType=";
          }
          loc = loc+"&unitOfMeasure="+ gg('unitOfSale')+
          "&shipped="+ gg('shipped')+
          "&pickList="+ gg('pickList')+
          "&previousPage="+encodeURIComponent('<fmt:message key="label.addline"/>')+
		  "&callbackparam="+encodeURIComponent(""+selectedRowId)+
		  "&partDesc="+gg('partDescription')+
		  "&isSpecHold="+cellValue(selectedRowId,'rcptQualityHoldSpec')+
		  "&isSlHold="+cellValue(selectedRowId,'rcptQualityHoldShelfLife')+		  
		  "&application="+cellValue(selectedRowId,'application')+
		  "&catPartNo="+cellValue(selectedRowId,'catPartNo')+
		  "&catalogCompanyId="+cellValue(selectedRowId,'catalogCompanyId')+
		  "&catalogId="+cellValue(selectedRowId,'catalogId')+
		  "&partGroupNo="+cellValue(selectedRowId,'partGroupNo')
		  ;

	  children[children.length] = openWinGeneric(loc, "AllocationDetail_"+pp('prNumber').replace(/[.]/,"_")+"_"+gg('lineItem'), "1024", "500", "yes", "50", "50");
      //children[children.length] = openWinGenericDefault(loc, "AllocationDetail_"+pp('prNumber').replace(/[.]/,"_")+"_"+gg('lineItem'), "yes", "50", "50");
}

function cancelMRLine() {
	
  showNotes(messagesData.cancelmrlinereason,'cancelMRLineReasonDiv');
	
}

function showCustomerReturnRequest()
{
        var prNumber  =  cellValue(mygrid.getSelectedRowId(),"prNumber");     
        var lineItem  =  cellValue(mygrid.getSelectedRowId(),"lineItem");
        var companyId  = cellValue(mygrid.getSelectedRowId(),"companyId");
        var catPartNo  = cellValue(mygrid.getSelectedRowId(),"catPartNo");
        var catalogCompanyId = cellValue(selectedRowId,"catalogCompanyId");

        var opsEntityId  = $v("opsEntityId");

        if ((prNumber!= null && lineItem != null && prNumber!= 0)  )
        {
                var loc = "/tcmIS/distribution/customerreturnrequest.do?prNumber="+prNumber+"&lineItem="+lineItem+"&replacementCatPartNo="+encodeURIComponent(catPartNo)+"&action=insert&companyId="+companyId+"&opsEntityId="+opsEntityId+"&customerServiceRepId="+$v('customerServiceRepId')+"&catalogCompanyId="+catalogCompanyId; 
                try
                {
                        parent.parent.openIFrame("showcustomerreturnrequest",loc,""+messagesData.customerreturnrequest+"","","N");
                }
                catch (ex)
                {
                	var winId= 'showcustomerreturnrequest'+$v("prNumber");
                    children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"900","600","yes","80","80","yes");
                	//children[children.length] = openWinGenericDefault(loc,winId.replace('.','a'),"yes","80","80","yes");
                }       
        }
}

/*
 * Grid event OnBeforeSelect function
 * Save the row class of currently 
 * selected row, before class changes.
 */
function doOnBeforeSelect(rowId,oldRowId) {
	if ($("marginNumber"+oldRowId) != null) {
		
		var margin = $v("marginNumber"+oldRowId); //cellValue(oldRowId,"margin").replace("%","");
		if (margin > cellValue(oldRowId,"maximumGrossMargin")*1 || margin < cellValue(oldRowId,"minimumGrossMargin")*1)
			$("marginNumber"+oldRowId).style.color="red"; 
//	   		haasGrid.rowsAr[ oldRowId ].cells[26].style.color="red";
	    else 
	    	$("marginNumber"+oldRowId).style.color="black"; 
//	   		haasGrid.rowsAr[ oldRowId ].cells[26].style.color="black";		
	}
		   		
	return true;	
}

useexternalevent = true;
var externalevent = null;

function selectRow(rowId,cellInd){


   $("selectedRowId").value = rowId;  
   selectedRowId = rowId;
   var rowsNum = mygrid.getRowsNum();
   $("totalRows").value = rowsNum;
   
   if ($v("orderType") == 'MR' && ($v("status").toUpperCase() == 'CONFIRMED' || $v("status").toUpperCase() == 'POSUBMIT')) {
      try
      {
    	if((cellValue(selectedRowId,"requestLineStatus") == '' || cellValue(selectedRowId,"requestLineStatus").toUpperCase() == 'DRAFT')&& showDelete == true)
        	$('deleteLineSpan').style.display="";
        else
        	$('deleteLineSpan').style.display="none";
      } catch (ex){}
   }
   
   try {
	    if($v("orderType") == 'MR' && (cellValue(selectedRowId,"radianPo") != "" || cellValue(selectedRowId,"poLine") != ""))
	        $('addLineSpan').style.display="none";
	    else if(!($v("orderType") == 'MR' && $v("originalSalesQuoteType") == 'Blanket Order'))
	    	$('addLineSpan').style.display="";
   } catch (ex){}

   return true;
}

function doDup() {
   if(mygrid == null) {
     return false; 
   }  

   if( selectedRowId == "") {
    alert(messagesData.norowselected);
    return false;
  } 
  
   var ind = mygrid.getRowsNum();  
   var rowid = ind*1+1;
   
   var thisrow = mygrid.addRow(rowid,"",ind);
 
   mygrid.selectRow(mygrid.getRowIndex(rowid),null,false,false);
   
/*
  var ind = mygrid.getRowsNum();
  var rowid = ind*1+1;
  var selectedIndex = mygrid.getRowIndex(selectedRowId)*1 + 1;
  var thisrow = mygrid.addRow(rowid,"",selectedIndex);
  
   mygrid.selectRow(mygrid.getRowIndex(rowid),false,false,true);  */
 
    mygrid.cells(rowid,mygrid.getColIndexById("permission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("okDoUpdatePermission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("quantityPermission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("customerPartNoPermission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("catalogPricePermission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("taxExemptPermission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("shipCompletePermission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("consolidateShipmentPermission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("dropShipOverridePermission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("customerPoLinePermission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("requiredShelfLifePermission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("deliveryTypePermission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("requiredDatetimePermission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("promisedDatePermission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("forceHoldPermission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("criticalPermission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("inventoryGroupPermission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("externalNotePermission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("internalNotePermission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("purchasingNotePermission")).setValue('Y');
    mygrid.cells(rowid,mygrid.getColIndexById("okDoUpdate")).setValue(false);
 
    mygrid.cells(rowid,mygrid.getColIndexById("lineItem")).setValue(rowid);
    mygrid.cells(rowid,mygrid.getColIndexById("customerPoLine")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("customerPoLine")).getValue());
    mygrid.cells(rowid,mygrid.getColIndexById("labelSpec")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("labelSpec")).getValue());
    mygrid.cells(rowid,mygrid.getColIndexById("partDescription")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("partDescription")).getValue());
    mygrid.cells(rowid,mygrid.getColIndexById("specList")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("specList")).getValue());
    var lookupCell = '<input type="button" class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'" id="fLookupButton" value="" onclick="getSpecList('+rowid+')"/><input type="button" class="smallBtns" onmouseover="this.className=\'smallBtns smallBtnsOver\'" onMouseout="this.className=\'smallBtns\'" name="None" value="'+messagesData.clear+'" OnClick="clearSpecList('+rowid+')"/>';
    mygrid.cells(rowid,mygrid.getColIndexById("specification")).setValue(lookupCell);
    mygrid.cells(rowid,mygrid.getColIndexById("hazardous")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("hazardous")).getValue());
    mygrid.cells(rowid,mygrid.getColIndexById("quantity")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("quantity")).getValue());
    mygrid.cells(rowid,mygrid.getColIndexById("currencyId")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("currencyId")).getValue());
    
    var catalogPriceDisplay = "";
    var realIndexToServer = rowid-1;
    if(cellValue(selectedRowId,"mvItem") != "Y") {
   	  catalogPriceDisplay = '<input class="inputBox pointer" id="catalogPriceDisplay'+rowid+'" type="text" value="'+$v("catalogPriceDisplay"+selectedRowId)+'" size="5"  maxlength="11" onchange="unitPriceChanged('+rowid+');" />';
   	}
   	else
   	  catalogPriceDisplay = messagesData.varies+'<input id="catalogPriceDisplay'+rowid+'" type="hidden" value="'+$v("catalogPriceDisplay"+selectedRowId)+'" />';
    mygrid.cells(rowid,mygrid.getColIndexById("catalogPriceDisplayXX")).setCValue(catalogPriceDisplay);

    mygrid.cells(rowid,mygrid.getColIndexById("extPrice")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("extPrice")).getValue());
    
	mygrid.cells(rowid,mygrid.getColIndexById("priceBreakAvailable")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("priceBreakAvailable")).getValue());

	var margin = "";
    if($v("marginNumber"+selectedRowId) != null && cellValue(selectedRowId,"mvItem") != "Y")
   	  margin = '<input class="inputBox pointer" id="marginNumber'+rowid+'" type="text" value="'+$v("marginNumber"+selectedRowId)+'" size="2" onchange="caculateUnitPrice('+rowid+');" />%';
   	if($v("marginNumber"+selectedRowId) != null && cellValue(selectedRowId,"mvItem") == "Y")
   	  margin = '<input class="inputBox pointer" id="marginNumber'+rowid+'" type="text" value="'+$v("marginNumber"+selectedRowId)+'" size="2" onchange="caculateUnitOfSalePrice('+rowid+');" />%';
   	  
    mygrid.cells(rowid,mygrid.getColIndexById("margin")).setCValue(margin);
    mygrid.cells(rowid,mygrid.getColIndexById("extraCharges")).setValue('');
    
    mygrid.cells(rowid,mygrid.getColIndexById("requiredDatetime")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("requiredDatetime")).getValue());
    mygrid.cells(rowid,mygrid.getColIndexById("promisedDate")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("promisedDate")).getValue());
    mygrid.cells(rowid,mygrid.getColIndexById("requiredShelfLife")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("requiredShelfLife")).getValue());
/*
  	columnId:"shipped",
  	columnId:"pickList",
  	columnId:"receipt",
  	columnId:"supply", */

	mygrid.cells(rowid,mygrid.getColIndexById("taxExempt")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("taxExempt")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("shipComplete")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("shipComplete")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("consolidateShipment")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("consolidateShipment")).getValue());
	   
	if($("taxExempt"+selectedRowId).checked) {
	   $("taxExempt"+rowid).checked = true; 
	   hchstatusA["taxExempt"+rowid] = true;
    }
    if($("shipComplete"+selectedRowId).checked) {
	   $("shipComplete"+rowid).checked = true; 
	   hchstatusA["shipComplete"+rowid] = true;
    }
    if($("consolidateShipment"+selectedRowId).checked) {
	   $("consolidateShipment"+rowid).checked = true;
   	   hchstatusA["consolidateShipment"+rowid] = true;
    } 

	mygrid.cells(rowid,mygrid.getColIndexById("deliveryType")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("deliveryType")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("critical")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("critical")).getValue());
	
	
	inventoryGroup[rowid] = inventoryGroup[selectedRowId];
	mygrid.cells(rowid,mygrid.getColIndexById("inventoryGroup")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("inventoryGroup")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("dropShipOverride")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("dropShipOverride")).getValue());
	
	if($v("orderType") == 'MR' && $("dropShipOverride"+selectedRowId).checked) {
	   $("dropShipOverride"+rowid).checked = true;
   	   hchstatusA["dropShipOverride"+rowid] = true;
    } 
    
	mygrid.cells(rowid,mygrid.getColIndexById("externalNote")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("externalNote")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("internalNote")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("internalNote")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("purchasingNote")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("purchasingNote")).getValue());
//	mygrid.cells(rowid,mygrid.getColIndexById("rcptQualityHoldSpec")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("rcptQualityHoldSpec")).getValue());
//	mygrid.cells(rowid,mygrid.getColIndexById("rcptQualityHoldShelfLife")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("rcptQualityHoldShelfLife")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("customerPartNo")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("customerPartNo")).getValue());

	mygrid.cells(rowid,mygrid.getColIndexById("companyId")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("companyId")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("prNumber")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("prNumber")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("catalogCompanyId")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("catalogCompanyId")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("catalogId")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("catalogId")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("partGroupNo")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("partGroupNo")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("unitOfSale")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("unitOfSale")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("unitOfSaleQuantityPerEach")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("unitOfSaleQuantityPerEach")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("catPartNo")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("catPartNo")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("application")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("application")).getValue());
	
	mygrid.cells(rowid,mygrid.getColIndexById("qtyInvoiced")).setValue('');
	mygrid.cells(rowid,mygrid.getColIndexById("requestLineStatus")).setValue('');
	
	mygrid.cells(rowid,mygrid.getColIndexById("itemId")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("itemId")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("expectedUnitCost")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("expectedUnitCost")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("specListConcat")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("specListConcat")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("specDetailConcat")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("specDetailConcat")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("specLibraryConcat")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("specLibraryConcat")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("specCocConcat")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("specCocConcat")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("specCoaConcat")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("specCoaConcat")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("minimumGrossMargin")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("minimumGrossMargin")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("maximumGrossMargin")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("maximumGrossMargin")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("medianSupplyLeadTime")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("medianSupplyLeadTime")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("inventoryGroupName")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("inventoryGroupName")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("originalOrderQuantity")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("originalOrderQuantity")).getValue());
	
	mygrid.cells(rowid,mygrid.getColIndexById("quantityReturnAuthorized")).setValue('');
	mygrid.cells(rowid,mygrid.getColIndexById("catalogPriceAvailable")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("catalogPriceAvailable")).getValue());
	mygrid.cells(rowid,mygrid.getColIndexById("mvItem")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("mvItem")).getValue());
    mygrid.cells(rowid,mygrid.getColIndexById("totalUnitOfSaleQuantity")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("totalUnitOfSaleQuantity")).getValue());
    
    mygrid.cells(rowid,mygrid.getColIndexById("catalogPrice")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("catalogPrice")).getValue());
    mygrid.cells(rowid,mygrid.getColIndexById("unitOfSalePrice")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("unitOfSalePrice")).getValue());
    mygrid.cells(rowid,mygrid.getColIndexById("originalSalesQuoteType")).setValue("");
    
    var unitOfSalePriceInput = "";
    if($v("unitOfSalePriceDisplay"+selectedRowId) != null) {
   	  unitOfSalePriceInput = '<input class="inputBox pointer" id="unitOfSalePriceDisplay'+rowid+'" type="text" value="'+$v("unitOfSalePriceDisplay"+selectedRowId)+'" size="5" onchange="unitOfSalePriceChanged('+rowid+');" />';
      mygrid.cells(rowid,mygrid.getColIndexById("unitOfSalePriceDisplayXX")).setCValue(unitOfSalePriceInput);
    }
  
    mygrid.cells(rowid,mygrid.getColIndexById("shippingReference")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("shippingReference")).getValue());
    
    mygrid.cells(rowid,mygrid.getColIndexById("forceHold")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("forceHold")).getValue()); //forceHold
    mygrid.cells(rowid,mygrid.getColIndexById("forceHoldComment")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("forceHoldComment")).getValue()); //forceHoldComment
    mygrid.cells(rowid,mygrid.getColIndexById("scrap")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("scrap")).getValue()); //scrap
    
    if($("forceHold"+selectedRowId) != null && $("forceHold"+selectedRowId).checked) {
	   $("forceHold"+rowid).checked = true; 
	   hchstatusA["forceHold"+rowid] = true;
    }
    
    if($("scrap"+selectedRowId) != null && $("scrap"+selectedRowId).checked) {
	   $("scrap"+rowid).checked = true; 
	   hchstatusA["scrap"+rowid] = true;
    }
    
    mygrid.cells(rowid,mygrid.getColIndexById("radianPo")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("radianPo")).getValue());
    mygrid.cells(rowid,mygrid.getColIndexById("poLine")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("poLine")).getValue());
    mygrid.cells(rowid,mygrid.getColIndexById("salesOrder")).setValue('');

//   if ($v("orderType") == 'MR' && $v("status").toUpperCase() == 'POSUBMIT' && cellValue(selectedRowId,"requestLineStatus") == 'Cancelled') {
   if ($v("orderType") == 'MR' && $v("status").toUpperCase() == 'POSUBMIT') {
    	setRowClass(rowid, ''+"grid_yellow"+'Selected');
    	saveRowClass = "grid_yellow";
    	showDelete = true;
   }
   
   var margin = $v("marginNumber"+rowid);
   if ($v("marginNumber"+rowid) != null && (margin > cellValue(rowid,"maximumGrossMargin")*1 || margin < cellValue(rowid,"minimumGrossMargin")*1)) 
   		$("marginNumber"+rowid).style.color="red";
   else if ($v("marginNumber"+rowid) != null)
   		$("marginNumber"+rowid).style.color="black";
 
   extraChargeChanged($v("totalHeaderCharge"), 'Y');
   showHeaderTotal();
   
   var rowsNum = mygrid.getRowsNum();
   $("totalRows").value = rowsNum;
   
   $("uAction").value = "save";
  	showPageWait();
  	
  	if (mygrid != null) {
  	  copyDisplayValuesToHiddenColumns();
      mygrid.parentFormOnSubmit();
    }
    	
  	closeAllchildren();
  	document.genericForm.submit(); 
}

function getScratchPadId() {
  callToServer("scratchpadmain.do?uAction=newxxx"); 
}


function deleteLine() 
{
	if( selectedRowId == null) {
    	alert(messagesData.norowselected);
    	return false;
  	} 
  	if( typeof(mygrid) != 'undefined' ) {
   		var rowsNum = mygrid.getRowsNum();
   		$("totalRows").value = rowsNum; 
  	}
    
  if ($v("orderType") == 'Quote' || $v("orderType") == 'Blanket Order' || $v("orderType") == 'Scratch Pad')
  { 
    $("uAction").value  = "deleteQuoteLine"; 
  }
  else if ($v("orderType") == 'MR') 
  {
  	if (cellValue(selectedRowId,"requestLineStatus").toUpperCase() == 'SHIPPED') {
  		alert(messagesData.lineitemshippednodelete);
  		return false;
  	} 
  	
  	$("uAction").value  = "deleteMRLine"; 
  }
    
  showPageWait();
  
  if (mygrid != null) {
  	copyDisplayValuesToHiddenColumns();
    mygrid.parentFormOnSubmit();
  }
  document.genericForm.submit();
}

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
	
/*		try{
			for(var i=0;i< document.getElementById("opsEntityId").length;i++) {
     	  		if( c.opsEntityId == document.getElementById("opsEntityId").options[i].value ) {
     			 	document.getElementById("opsEntityId").selectedIndex = i;
     			 	break;
     	  		}
	    	}  
		}
		catch(ex){}  */
	
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

function unitPriceChanged(rowId) {
	selectedRowId = rowId;
	caculateExtPrice(rowId);

	var interCoUnitPricePctTol = cellValue(rowId,"interCoUnitPricePctTol").trim();
	var interCoExtPriceAmtTol = cellValue(rowId,"interCoExtPriceAmtTol").trim();

	var originalOrderQuantity = cellValue(rowId,"originalOrderQuantity").trim();
	var quantity = cellValue(rowId,"quantity").trim();

	if( quantity == originalOrderQuantity && (interCoUnitPricePctTol != '' || interCoExtPriceAmtTol != '') ) {
		var callArgs = new Object();
		callArgs.radianPo = cellValue(rowId,"radianPo").trim();
		callArgs.poLine = cellValue(rowId,"poLine").trim();
	
		j$.post("/tcmIS/supply/findpoline.do", j$.param(callArgs), checkTolerance);  
	}
}

function checkTolerance(data,status){
    var poLineQuantity = data.split(":")[0];
    var poLineUnitPrice = data.split(":")[1];

    var extPrice = cellValue(selectedRowId,"extPrice").trim();

    var interCoUnitPricePctTol = cellValue(selectedRowId,"interCoUnitPricePctTol").trim();
	var interCoExtPriceAmtTol = cellValue(selectedRowId,"interCoExtPriceAmtTol").trim();
    
    var difference = Math.abs(poLineQuantity * poLineUnitPrice - extPrice);

    if(difference > interCoExtPriceAmtTol || difference*100/(poLineQuantity * poLineUnitPrice) > interCoUnitPricePctTol) {
		alert(messagesData.unitpricetolerance);
		$("catalogPriceDisplay"+selectedRowId).focus();
    } 
}

function caculateExtPrice(rowId,cellInd)
{
	var errorCount = 0;
    var errorMessage = messagesData.validvalues+"\n\n";
 
	var quantity = cellValue(rowId,"quantity").trim();
	var shipped = cellValue(rowId,"shipped").trim();
	var pickList = cellValue(rowId,"pickList").trim();
	var receipt = cellValue(rowId,"receipt").trim();
	var supply = cellValue(rowId,"supply").trim();

  	if ((!(isPositiveInteger(quantity,true)) && quantity != 0 ) || quantity < (shipped*1 + pickList*1) || quantity < (receipt*1 + supply*1))
   	{  
   	   errorMessage = errorMessage +"\n"+ messagesData.orderqty;
   	   mygrid.cells(rowId, mygrid.getColIndexById("quantity")).setValue(cellValue(rowId,"originalOrderQuantity"));
	   errorCount = 1;
   	}
   	
  	if (quantity == "" || quantity == null) {
  	   mygrid.cells(rowId, mygrid.getColIndexById("extPrice")).setValue(null);	
  	}
   
   	   var catalogPrice = $v("catalogPriceDisplay"+rowId);
	   var catalogPriceAvailable = mygrid.cellById(rowId, mygrid.getColIndexById("catalogPriceAvailable")).getValue().trim();
	   var mvItem = mygrid.cellById(rowId, mygrid.getColIndexById("mvItem")).getValue().trim();
//	   var catalogPrice = mygrid.cellById(rowId, mygrid.getColIndexById("catalogPrice")).getValue().trim();
	   if (catalogPriceAvailable == 'Y' && mvItem != 'Y' && !isFloat(catalogPrice,true))
	   {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.unitprice;
	   	   $("catalogPriceDisplay"+rowId).value = "";
//	   	   mygrid.cells(rowId, mygrid.getColIndexById("catalogPrice")).setValue("");
		   errorCount = 1;
	   }
	   
	   var unitOfSalePrice = $v("unitOfSalePriceDisplay"+rowId);
	   if (catalogPriceAvailable == 'Y' && mvItem == 'Y' && !isFloat(unitOfSalePrice,true))
	   {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.uosp;
	   	   $("unitOfSalePriceDisplay"+rowId).value = "";
//	   	   mygrid.cells(rowId, mygrid.getColIndexById("catalogPrice")).setValue("");
		   errorCount = 1;
	   }

   var qtyShipped = mygrid.cellById(rowId, mygrid.getColIndexById("shipped")).getValue().trim();
   var requiredShelfLife = mygrid.cellById(rowId, mygrid.getColIndexById("requiredShelfLife")).getValue();
   if (qtyShipped*1 == 0 && (!(isSignedFloat(requiredShelfLife.trim(),true)) || requiredShelfLife  > 100*1))
   {
	   	   errorMessage = errorMessage +"\n"+ messagesData.requiredshelflife;
		    mygrid.cells(rowId, mygrid.getColIndexById("requiredShelfLife")).setValue("");
		   errorCount = 1;
   }
   
   
   if (errorCount > 0)
   {
     alert(errorMessage);
//	 mygrid.cells(rowId,1).setValue(null);
	 return false;
   } 
   
   var expectedUnitCost = cellValue(rowId,"expectedUnitCost");
   var requestLineStatus = cellValue(rowId,"requestLineStatus");
   var salesOrder = cellValue(rowId,"salesOrder");
//alert("catalogPrice:"+catalogPrice);
//alert("expectedUnitCost:"+expectedUnitCost);
   if (requestLineStatus != 'Cancelled' && requestLineStatus != 'Canceled' && catalogPrice.length != 0 && catalogPrice != 0 && salesOrder.length == 0) {
   		if (expectedUnitCost.length != 0 && expectedUnitCost != '' && expectedUnitCost != 0) {
   			var margin = (catalogPrice-expectedUnitCost)/catalogPrice*100;
   			try {
   	 			$("marginNumber"+rowId).value = margin.toFixed(1);
   			
	   			if (margin > cellValue(rowId,"maximumGrossMargin")*1 || margin < cellValue(rowId,"minimumGrossMargin")*1) 
	   				$("marginNumber"+rowId).style.color="red";
	   			else 
	   				$("marginNumber"+rowId).style.color="black";
   			} catch (ex) {}
   			
   		}
   		if (quantity.length != 0) {
	   		var total = quantity*catalogPrice;
	   		cell(rowId,"extPrice").setValue(total.toFixed(2));
	   	}
   		
   }  
   else {
   		cell(rowId,"extPrice").setValue("");
   		//cell(rowId,"margin").setCValue("");
   }
   
   showHeaderTotal();

   var mvItem = cellValue(rowId,"mvItem").trim();
   var unitOfSaleQuantityPerEach = cellValue(rowId,"unitOfSaleQuantityPerEach").trim();
   if (mvItem == 'Y')
   {
       cell(rowId,"totalUnitOfSaleQuantity").setValue(quantity*unitOfSaleQuantityPerEach); 
   }
   return true;	
}

function unitOfSalePriceChanged(rowId) {
// We wanted to make sure the user save MR before they confirm MR. Now we save MR before confirm MR already.
/*    try {
		$("confirmMRSpan").style.display="none";
	}catch(ex){};  */
	caculateUnitOfSaleExtPrice(rowId)
}

function caculateUnitOfSaleExtPrice(rowId)
{
	var errorCount = 0;
    var errorMessage = messagesData.validvalues+"\n\n";

	var quantity = cellValue(rowId,"quantity").trim();
	var shipped = cellValue(rowId,"shipped").trim();
	var pickList = cellValue(rowId,"pickList").trim();
	var receipt = cellValue(rowId,"receipt").trim();
	var supply = cellValue(rowId,"supply").trim();
  	if ((!(isPositiveInteger(quantity,true)) && quantity != 0 )|| quantity < (shipped*1 + pickList*1) || quantity < (receipt*1 + supply*1))
   	{
   	   errorMessage = errorMessage +"\n"+ messagesData.orderqty;
   	   mygrid.cells(rowId, mygrid.getColIndexById("quantity")).setValue(cellValue(rowId,"originalOrderQuantity"));
	   errorCount = 1;
   	}
/*
    var unitOfSalePrice = $v("unitOfSalePriceDisplay"+rowId);
//   var catalogPrice = cellValue(rowId,"catalogPrice").trim();
   if (!(isFloat(unitOfSalePrice,true)))
   {
   	   errorMessage = errorMessage +"\n"+ messagesData.unitprice;
   	   $("unitOfSalePriceDisplay"+rowId).value = "";
//   	   mygrid.cells(rowId, mygrid.getColIndexById("catalogPrice")).setValue("");
	   errorCount = 1;
   } */
   
       var catalogPrice = $v("catalogPriceDisplay"+rowId);
	   var catalogPriceAvailable = mygrid.cellById(rowId, mygrid.getColIndexById("catalogPriceAvailable")).getValue().trim();
	   var mvItem = mygrid.cellById(rowId, mygrid.getColIndexById("mvItem")).getValue().trim();
//	   var catalogPrice = mygrid.cellById(rowId, mygrid.getColIndexById("catalogPrice")).getValue().trim();
	   if (catalogPriceAvailable == 'Y' && mvItem != 'Y' && !isFloat(catalogPrice,true))
	   {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.unitprice;
	   	   $("catalogPriceDisplay"+rowId).value = "";
//	   	   mygrid.cells(rowId, mygrid.getColIndexById("catalogPrice")).setValue("");
		   errorCount = 1;
	   }
	   
	   var unitOfSalePrice = $v("unitOfSalePriceDisplay"+rowId);
	   if (catalogPriceAvailable == 'Y' && mvItem == 'Y' && !isFloat(unitOfSalePrice,true))
	   {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.uosp;
	   	   $("unitOfSalePriceDisplay"+rowId).value = "";
//	   	   mygrid.cells(rowId, mygrid.getColIndexById("catalogPrice")).setValue("");
		   errorCount = 1;
	   }

   var qtyShipped = mygrid.cellById(rowId, mygrid.getColIndexById("shipped")).getValue().trim();
   var requiredShelfLife = mygrid.cellById(rowId, mygrid.getColIndexById("requiredShelfLife")).getValue();
   if (qtyShipped*1 == 0 && (!(isSignedFloat(requiredShelfLife.trim(),true)) || requiredShelfLife  > 100*1))
   {
	   	   errorMessage = errorMessage +"\n"+ messagesData.requiredshelflife;
		    mygrid.cells(rowId, mygrid.getColIndexById("requiredShelfLife")).setValue("");
		   errorCount = 1;
   }

   if (errorCount > 0)
   {
     alert(errorMessage);
//	 mygrid.cells(rowId,1).setValue(null);
	 return false;
   }
  var expectedUnitCost = cellValue(rowId,"expectedUnitCost");
  var unitOfSaleQuantityPerEach = cellValue(rowId,"unitOfSaleQuantityPerEach").trim();
//alert("catalogPrice:"+catalogPrice);
//alert("expectedUnitCost:"+expectedUnitCost);
   if (unitOfSalePrice.length != 0 && unitOfSalePrice != 0) {
   		if (expectedUnitCost.length != 0 && expectedUnitCost != '' && expectedUnitCost != 0) {

               var margin = ((unitOfSalePrice*unitOfSaleQuantityPerEach)-expectedUnitCost)/(unitOfSalePrice*unitOfSaleQuantityPerEach)*100;

   	 		$("marginNumber"+rowId).value = margin.toFixed(1);

   			if (margin > cellValue(rowId,"maximumGrossMargin")*1 || margin < cellValue(rowId,"minimumGrossMargin")*1)
   				$("marginNumber"+rowId).style.color="red";
   			else
   				$("marginNumber"+rowId).style.color="black";

   		}
   		if (quantity.length != 0) {
               var total = quantity*unitOfSalePrice*unitOfSaleQuantityPerEach;
	   		   cell(rowId,"extPrice").setValue(total.toFixed(2));
               $("catalogPriceDisplay"+rowId).value = unitOfSalePrice*unitOfSaleQuantityPerEach;
           }

   }
   else {
   		cell(rowId,"extPrice").setValue("");
   		//cell(rowId,"margin").setCValue("");
   }

   showHeaderTotal();

   return true;
}

function showHeaderTotal() {
   		
   	  var priceTotal = 0;
	  var costTotal = 0;
	  var validPriceTotal = 0;
	  var rowsNum = mygrid.getRowsNum();
 
	  for (var p = 1; p < (rowsNum+1) ; p ++)
	  {
		  if(cellValue(p,"requestLineStatus") != 'Cancelled' && cellValue(p,"requestLineStatus") != 'Canceled' && cellValue(p,"extPrice").length != 0) {
		  	priceTotal = priceTotal*1 + cellValue(p,"extPrice")*1;
		  	if(cellValue(p,"expectedUnitCost").length != 0 && cellValue(p,"quantity").length != 0) {
		  		costTotal = costTotal*1 + cellValue(p,"expectedUnitCost")*cellValue(p,"quantity");
		  		validPriceTotal = validPriceTotal*1 + cellValue(p,"extPrice")*1;
		  	}
		  }
	  }

	  $("materialPriceSpan").innerHTML = priceTotal.toFixed(2);
	  var total = priceTotal*1 + $("headerCharges").innerHTML*1;
	  $("totalSpan").innerHTML = total.toFixed(2);
//alert("availableCredit:"+$v("availableCredit")+"    totalSpan"+$("totalSpan").innerHTML +" done:"+done+"    showOverCreditLimit:"+$v("showOverCreditLimit"));
	  if($v("availableCredit") < $("totalSpan").innerHTML*1 && $v("showOverCreditLimit") == 'true' && $v("billToCompanyId") != "CASH_SALES" && done == true) {
		showOverCreditLimitWin();
		done = false;
      }
	  
	  if (costTotal != 0 && priceTotal != 0) {
//alert("priceTotal"+priceTotal+"      costTotal:"+costTotal);
		  var headerMargin = (priceTotal-costTotal)/priceTotal*100;
		  $("expectedMarginSpan").innerHTML = headerMargin.toFixed(1)+"%";
      }
      else
      	$("expectedMarginSpan").innerHTML = "";
      
}

function newScratchPadTab(title,action) {

//	openSPadCounter = openSPadCounter + 1; 
//    var loc = "/tcmIS/distribution/scratchpadmain.do?uAction="+action+"&scratchPadId="+$v("prNumber")+"&tabId=scratchPad"+openSPadCounter;	
//    var loc = "/tcmIS/distribution/scratchpadmain.do?uAction="+action+"&scratchPadId="+$v("prNumber")+"&orderType="+title;
/*    if( typeof(mygrid) != 'undefined' ) {	
	  var rowsNum = mygrid.getRowsNum()*1;
      var flag = false;
  	  for (var p = 1 ; p < (rowsNum+1) ; p ++)
  	  {
	    if($("okDoUpdate"+p).checked)
	    {	
//	  	  loc = loc + "&lineItem=" + cellValue(p,"lineItem"); 
	  	  flag = true; 
	    }
  	  }
 	
      if(!flag)
   	  {
    	alert(messagesData.norowselected);
    	return false;
      }   
    }  */
    
    if($v("inventoryGroup").length == 0)
    {
      alert(messagesData.validvalues+"\n\n"+messagesData.inventorygroup);
      return false;
    }
    
    if( typeof(mygrid) != 'undefined' ) {
   		var rowsNum = mygrid.getRowsNum();
   		$("totalRows").value = rowsNum; 
  	} 
    
    $("newOrderType").value = title;

    $("uAction").value = action;
  	showPageWait();
  	
  	if (mygrid != null) {
  	  copyDisplayValuesToHiddenColumns();
      mygrid.parentFormOnSubmit();
    }
    	
    closeAllchildren();
  	document.genericForm.submit();
    
}

function newScratchPad(blank,orderType,scratchPadId,personnelId,lastName,firstName,tcmISErrors) {
  	var loc = '';

	if (blank == 'Y')
    	loc = "/tcmIS/distribution/scratchpadmain.do?blank=Y&scratchPadId="+scratchPadId+"&tabId="+encodeURIComponent('scratchPad'+scratchPadId+'')+"&personnelId="+personnelId+"&lastName="+lastName+"&firstName="+firstName+"&tcmISError="+encodeURIComponent(tcmISErrors);	
    else {
      if ($v("closeOldTab") == 'Y')
        loc = "/tcmIS/distribution/scratchpadmain.do?uAction=searchScratchPadId&callbackparam=delete&callback=deletescratchpad&caller="+encodeURIComponent(window.name)+"&scratchPadId="+scratchPadId+"&tabId="+encodeURIComponent('scratchPad'+scratchPadId+'')+"&personnelId="+personnelId+lastName+"&firstName="+firstName+"&tcmISError="+encodeURIComponent(tcmISErrors);
      else
    	loc = "/tcmIS/distribution/scratchpadmain.do?uAction=searchScratchPadId&scratchPadId="+scratchPadId+"&tabId="+encodeURIComponent('scratchPad'+scratchPadId+'')+"&personnelId="+personnelId+"&lastName="+lastName+"&firstName="+firstName+"&tcmISError="+encodeURIComponent(tcmISErrors);
    }

	try
	{
		if (orderType == "Scratch Pad")
            parent.openIFrame("scratchPad"+scratchPadId+"",loc,"SP "+scratchPadId+"","","N");
        else if (orderType == "Blanket Order")
            parent.openIFrame("scratchPad"+scratchPadId+"",loc,"B "+scratchPadId+"","","N");
        else
			parent.openIFrame("scratchPad"+scratchPadId+"",loc,orderType+" "+scratchPadId+"","","N");

	}
	catch (ex)
	{
		//alert(ex);
		openWinGeneric(loc,"scratchPad","900","600","yes","80","80","yes");
		//openWinGenericDefault(loc,"scratchPad","yes","80","80","yes");
	}
}

function createQuoteOrMRDeleteScratchPad(title,action) {

	var rowsNum = mygrid.getRowsNum()*1;
    var flag = false;
  	for (var p = 1 ; p < (rowsNum+1) ; p ++)
  	{
	  if($("okDoUpdate"+p).checked)
	  {	
	  	flag = true; 
	  }
  	}
  	if(flag == false) {
  		var continueOrNot = confirm(messagesData.norowwasselected +"\n"+ messagesData.wanttocontinue);
		if (continueOrNot == false) {
	      return false;
	    }  
  	}

	$("closeOldTab").value = 'Y';
	newScratchPadTab(title,action);
}

function cancelQuote() {
  $("uAction").value = "cancelQuote";
  showPageWait();
  closeAllchildren();
  document.genericForm.submit();
}

function deleteQuote() {
  $("uAction").value = "deleteQuote";
  showPageWait();
  closeAllchildren();
  document.genericForm.submit();
  return false;
}


function deleteMR() {
  $("uAction").value = "deleteMR";
  showPageWait();
  closeAllchildren();
  document.genericForm.submit();
}


function addHeaderCharges() {
  var lineTotal = 0;
  if (mygrid)
  {
	  var rowsNum = mygrid.getRowsNum()*1;
	  for (var p = 1; p < (rowsNum+1) ; p ++)
	  {
		  lineTotal = lineTotal + cellValue(p,"extraCharges")*1;
	  }
  }
  showTransitWin(messagesData.addlheaderchargespr);
  loc = "addchargeheader.do?orderType="+$v("orderType")+"&status="+$v("status")+"&prNumber="+$v("prNumber")+"&companyId="+$v("companyId")+"&totalLineCharge="+lineTotal+"&currencyId="+$v("currencyId")+"&inventoryGroup="+encodeURIComponent($v("inventoryGroup"))+"&creditStatus="+$v("creditStatus")+"&orderStatus="+$v("orderStatus")+"&noAddChargePermission="+noAddChargePermission+"&opsEntityId="+$v("opsEntityId");
  var winId= 'addHeaderCharge'+$v("prNumber");
  children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"820","400","yes","50","50","20","20","no");
}

function addLineCharges() {
 var lineQuantity = gg("quantity")
 if(isPositiveInteger(lineQuantity,false) && lineQuantity != '&nbsp;')
 {
  showTransitWin(messagesData.additionalcharges);
  loc = "addchargeline.do?orderType="+pp('orderType')+"&status="+$v("status")+
  						"&prNumber="+cellValue(selectedRowId,"prNumber")+
  						"&companyId="+gg("companyId")+
  						"&lineItem="+gg("lineItem")+
  						"&inventoryGroup="+gg("inventoryGroup")+
  					    "&curpath="+getcurpath()+
  					    "&currencyId="+$v("currencyId")+
  					    "&creditStatus="+$v("creditStatus")+
  					    "&opsEntityId="+$v("opsEntityId")+
                        "&lineQuantity="+lineQuantity+
                        "&orderStatus="+$v("orderStatus");
  var winId= 'addHeaderCharge'+$v("prNumber");
  children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"820","400","yes","50","50","20","20","no");
  //children[children.length] = openWinGenericDefault(loc,winId.replace('.','a'),"yes","50","50","20","20","no");
 }
 else
 {
   alert(messagesData.validvalues+"  "+messagesData.orderqty);
 }
}

/*
function getTwoDegit( total ) {
	total = Math.round(total*100)/100;
		var strs = (''+total).split(/[.]/);
		if( strs.length == 1 ) total = '' + total + ".00";
		else if( strs[1].length == 1) total = '' + total + "0";
	return total;
}
*/
function extraChargeChanged(total, headerOrNot) {
  var lineTotal = 0;	
  try{
    var rowsNum = mygrid.getRowsNum()*1;
  } catch(ex){}
  
  if (headerOrNot == 'N')
    cell(selectedRowId,"extraCharges").setValue(total);
  
  for (var p = 1; p < (rowsNum+1) ; p ++)
  {
	  var a = 0 ;
  	  try{ 
  	  	if (cellValue(p,"requestLineStatus").trim().toUpperCase() != 'CANCELED' && cellValue(p,"requestLineStatus").trim().toUpperCase() != 'CANCELLED')
  	  		a = cellValue(p,"extraCharges")*1;
      }catch(ex){}

	  lineTotal = lineTotal*1 + a*1;	
  }
  
  try {
  	if (headerOrNot == 'Y') {
    	$("totalHeaderCharge").value = total;
    	var headerChargesTotal = total*1 + lineTotal*1;
		$("headerCharges").innerHTML = 	headerChargesTotal.toFixed(2);
		try {
			if($v("totalLines") == 0 &&($('headerCharges').innerHTML == '0.00' || $('headerCharges').innerHTML == '' || $('headerCharges').innerHTML == '0'))
	  			$('confirmMRSpan').style.display="none";
	  		else
	  			$('confirmMRSpan').style.display="";
	  	}catch(ex){}; 
  	}
  	else {  
  		var headerChargesTotal =  $v("totalHeaderCharge")*1 + lineTotal*1
		$("headerCharges").innerHTML = headerChargesTotal.toFixed(2);	
  	}
    var totalSpanTotal = $("materialPriceSpan").innerHTML*1 + $("headerCharges").innerHTML*1;
  	$("totalSpan").innerHTML = totalSpanTotal.toFixed(2);
  	if($v("availableCredit") < $("totalSpan").innerHTML*1 && $v("showOverCreditLimit") == 'true' && $v("billToCompanyId") != "CASH_SALES" && done == true) {
		showOverCreditLimitWin();
		done = false;
    }
  }catch(ex){}; 
   
}


function getSpecList(rowId) {
	$("selectedRowId").value = rowId;
    selectedRowId = rowId;
    if (cellValue(rowId,'itemId').trim().length > 0)
    {
    	showTransitWin(messagesData.specificationlookup);
    	var winId= 'specList'+$v("prNumber");
    	
    	if(cellValue(rowId,'catPartNo') == null || cellValue(rowId,'catPartNo').trim().length == 0) {
    		loc = "speclist.do?uAction=showDetails&save=Y&itemId="+cellValue(rowId,'itemId')+"&radianPo="+cellValue(rowId,'radianPo')+"&poLine="+cellValue(rowId,'poLine');
    		children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"680","550","yes","50","50","20","20","no");
    		//children[children.length] = openWinGenericDefault(loc,winId.replace('.','a'),"yes","50","50","20","20","no");
    	}
    	else {
    		loc = "speclist.do?save=Y&itemId="+cellValue(rowId,'itemId')+"&catPartNo="+cellValue(rowId,'catPartNo');
    		children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"680","450","yes","50","50","20","20","no");
    		//children[children.length] = openWinGenericDefault(loc,winId.replace('.','a'),"yes","50","50","20","20","no");
		}
    }
    else
    {
     alert("No item Id on line");        
    }
}

function getList(specListDisplay, list, specVersion, specAmendment, detail, library, coc, coa, libraryDesc) {
  closeTransitWin();
  if (selectedRowId != null)
  {
	mygrid.cells(selectedRowId,mygrid.getColIndexById("specList")).setValue(specListDisplay);
    mygrid.cells(selectedRowId,mygrid.getColIndexById("specListConcat")).setValue(list);
    mygrid.cells(selectedRowId,mygrid.getColIndexById("specDetailConcat")).setValue(detail);
    mygrid.cells(selectedRowId,mygrid.getColIndexById("specLibraryConcat")).setValue(library);
    mygrid.cells(selectedRowId,mygrid.getColIndexById("specCocConcat")).setValue(coc);
    mygrid.cells(selectedRowId,mygrid.getColIndexById("specCoaConcat")).setValue(coa);
 
  	$("uAction").value = "save";
  	showPageWait();
  	
  	if (mygrid != null) {
  	  copyDisplayValuesToHiddenColumns();
      mygrid.parentFormOnSubmit();
    }
// alert( "specListDisplay:"+specListDisplay +"  library:"+library);	
  	closeAllchildren();
  	document.genericForm.submit(); 
      
//  	mygrid.selectRowById(selectedRowId,null,false,false);
  }
}

function clearSpecList( rowId ) {
	mygrid.cells(rowId,mygrid.getColIndexById("specList")).setValue('');
	mygrid.cells(rowId,mygrid.getColIndexById("specListConcat")).setValue('');
	mygrid.cells(rowId,mygrid.getColIndexById("specDetailConcat")).setValue('');
	mygrid.cells(rowId,mygrid.getColIndexById("specLibraryConcat")).setValue('');
	mygrid.cells(rowId,mygrid.getColIndexById("specCocConcat")).setValue('');
	mygrid.cells(rowId,mygrid.getColIndexById("specCoaConcat")).setValue('');
//    $("specList"+selectedRowId).value = '';
	//parent.resizeFrames();
}

function setPriceListDropDown(value){
	setSelect("priceGroupId",value);		
}

function autoAllocate(orderType) {
  var rowsNum = mygrid.getRowsNum();
  
 	var flag = false;
	  for (var p = 1; p < (rowsNum+1) ; p ++)
	  {
	  	var quantity = mygrid.cellById(p, mygrid.getColIndexById("quantity")).getValue().trim();
		  if(isPositiveInteger(quantity,false) && quantity != '&nbsp;' && cellValue(p,"requestLineStatus").trim().toUpperCase() != 'CANCELED' && cellValue(p,"requestLineStatus").trim().toUpperCase() != 'CANCELLED') {
		  	flag = true;
		  }
	  }

	if (flag == false) {
		alert(messagesData.validvalues+"  "+messagesData.orderqty);
		return false;
	}
	
  if(orderType == 'MR')
  	$("uAction").value = "autoAllocateForMR";
  else
  	$("uAction").value = "autoAllocateForSP";
  showPageWait();
  
  if (mygrid != null) {
  	copyDisplayValuesToHiddenColumns();
    mygrid.parentFormOnSubmit();
  }
    
  closeAllchildren();
  document.genericForm.submit();
}
	
function closeAllchildren()
{
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

function printQuote()
{    
	var id = document.getElementById("user_id").value;
	var loc = "/HaasReports/report/printsalesquote.do?prNumber="+$v("prNumber")+"&personnelId="+id+
	"&billToCountry="+$v("billToCountry")+"&opsEntityId="+$v("opsEntityId");
	openWinGeneric(loc,"PrintSalesQuote"+$v("prNumber").replace('.','a')+"","800","600","yes","50","50","20","20","yes");
}

function printMR(prNumber)
{
	var id = document.getElementById("user_id").value;
    var loc = "/HaasReports/report/printsalesorders.do?prNumber="+$v("prNumber")+"&personnelId="+id+
                "&billToCountry="+$v("billToCountry")+"&opsEntityId="+$v("opsEntityId")+"&customerId="+$v("customerId");
       openWinGeneric(loc,"PrintSalesorder"+$v("prNumber").replace('.','a'),"800","600","yes","80","80","yes");
}

function printProForma(prNumber)
{
	   var id = document.getElementById("user_id").value;
       var loc = "/HaasReports/report/printdistproformainvoice.do?prNumber="+$v("prNumber")+"&personnelId="+id+
                 "&billToCountry="+$v("billToCountry")+"&opsEntityId="+$v("opsEntityId");
       openWinGeneric(loc,"PrintSalesorder"+$v("prNumber").replace('.','a'),"800","600","yes","80","80","yes");
}

function printQuoteProForma(prNumber)
{
	   var id = document.getElementById("user_id").value;
       var loc = "/HaasReports/report/printquoteproformainvoice.do?prNumber="+$v("prNumber")+"&personnelId="+id+
                 "&billToCountry="+$v("billToCountry")+"&opsEntityId="+$v("opsEntityId");
       openWinGeneric(loc,"PrintSalesorder"+$v("prNumber").replace('.','a'),"800","600","yes","80","80","yes");
}

function printInvoice()
{
	var loc = "/HaasReports/report/printdistributioninvoice.do?prNumber="+$v("prNumber")+
	          "&billToCountry="+$v("billToCountry")+"&opsEntityId="+$v("opsEntityId");
    openWinGeneric(loc,"PrintSalesorder"+$v("prNumber").replace('.','a'),"800","600","yes","80","80","yes");
}


function limitText(id, label, limitNum) {
    var limitCount;
    var limitField  =  document.getElementById(id);
// alert(limitField.value.length);
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
		var lengthMsg = messagesData.maxlength;
		lengthMsg = lengthMsg.replace(/[{]0[}]/g,label);
		lengthMsg = lengthMsg.replace(/[{]1[}]/g,limitNum);
		alert(lengthMsg);
	} 
}

function showOverCreditLimitWin() {
  if (currentWindow == null) {
	showWin(messagesData.creditreview,"overCreditLimitArea");
  }
  else
	currentWindow.show();
}

function initializeDhxWins() {
	if (dhxFreezeWins == null) {
		dhxFreezeWins = new dhtmlXWindows();
		dhxFreezeWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showTransitWin(messageType)
{
	var waitingMsg = messagesData.waitingforinputfrom+"...";
	$("transitLabel").innerHTML = waitingMsg.replace(/[{]0[}]/g,messageType);

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.innerHTML = document.getElementById("transitDailogWinBody").innerHTML;
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxFreezeWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxFreezeWins.createWindow("transitDailogWin",0,0, 400, 200);
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
		dhxFreezeWins.window("transitDailogWin").show();
	}
}

function closeTransitWin() {
	if (dhxFreezeWins != null) {
		if (dhxFreezeWins.window("transitDailogWin")) {
			dhxFreezeWins.window("transitDailogWin").setModal(false);
			dhxFreezeWins.window("transitDailogWin").hide();
		}
	}
}

function checkLineComplete(rowId) {
	if( ! $("shipComplete"+rowId).disabled && $("consolidateShipment"+rowId).checked && !$("shipComplete"+rowId).checked) {
		$("shipComplete"+rowId).checked = true;
		updateHchStatusA("shipComplete"+rowId);
	}
}

function uncheckLinesGroup(rowId) {
	if( !$("consolidateShipment"+rowId).disabled && !$("shipComplete"+rowId).checked && $("consolidateShipment"+rowId).checked) {
		$("consolidateShipment"+rowId).checked = false;
		updateHchStatusA("consolidateShipment"+rowId);
	}
}

function submitReview() {
 
 if(null!=$v('opsEntityId'))
 loc = "creditreviewdetails.do?customerId="+$v("customerId")+"&opsEntityId="+$v('opsEntityId');
 else
 loc = "creditreviewdetails.do?customerId="+$v("customerId");	 
  var winId= 'customerReview'+$v("customerId");
//  showTransitWin("");
  children[children.length] = openWinGeneric(loc,winId,"900","600","yes","50","50","20","20","no");
}

function caculateUnitPrice(rowId) {

	var expectedUnitCost = cellValue(rowId,"expectedUnitCost");
	var margin = $v("marginNumber"+rowId);
	if (margin*1 >= 100) {
    	alert(messagesData.marginlessthan100);
    	return false;
    }
    else	
		var unitPrice = 100*expectedUnitCost/(100-margin);
	
	catalogPrice = unitPrice.toFixed(4);
	$("catalogPriceDisplay"+rowId).value = unitPrice.toFixed(4);
	
	var quantity = cellValue(rowId,"quantity");	
	if (quantity.length != 0) {
	   		var total = quantity*catalogPrice;
	   		cell(rowId,"extPrice").setValue(total.toFixed(2));
	}
	
	showHeaderTotal();
}

function caculateUnitOfSalePrice(rowId) {

	var expectedUnitCost = cellValue(rowId,"expectedUnitCost");
    var unitOfSaleQuantityPerEach = cellValue(rowId,"unitOfSaleQuantityPerEach").trim();
    var margin = $v("marginNumber"+rowId);
    if (margin*1 >= 100) {
    	alert(messagesData.marginlessthan100);
    	return false;
    }
    else
		var unitOfSalePrice = 100*expectedUnitCost/((100-margin)*unitOfSaleQuantityPerEach);

    $("unitOfSalePriceDisplay"+rowId).value = unitOfSalePrice.toFixed(4);
    $("catalogPriceDisplay"+rowId).value = (unitOfSalePrice*unitOfSaleQuantityPerEach).toFixed(4);

	var quantity = cellValue(rowId,"quantity");
	if (quantity.length != 0) {
	   		var total = quantity*(unitOfSalePrice*unitOfSaleQuantityPerEach);
	   		cell(rowId,"extPrice").setValue(total.toFixed(2));
	}

	showHeaderTotal();
}


function okClose(obj1,obj2,msg) {
   $(obj1).value = $v(obj2);
   dhxNoteWins.window(msg).setModal(false);
   dhxNoteWins.window(msg).hide();
}

function clearNotes(obj1,obj2) {
   $(obj1).value = '';
   $(obj2).value = '';
}

function cancelMRLineReasonOkClose() {
   $("cancelMRLineReason").value = $v("cancelMRLineReasonDivAreaUSE");
   dhxNoteWins.window(messagesData.cancelmrlinereason).setModal(false);
   dhxNoteWins.window(messagesData.cancelmrlinereason).hide();
   
  $("uAction").value  = "cancelMRLine"; 
  
  showPageWait();
  
  if (mygrid != null) {
  	copyDisplayValuesToHiddenColumns();
    mygrid.parentFormOnSubmit();
  }
  document.genericForm.submit();
 
}

function cancelMRLineReasonClear() {
   $("cancelMRLineReasonDivAreaUSE").value = '';
   $("cancelMRLineReason").value = '';
}

function showLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows()
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 180);
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

function copyDisplayValuesToHiddenColumns() {
	for(var i=0;i<mygrid.getRowsNum();i++){
	    rowid = mygrid.getRowId(i);
		mygrid.cells(rowid,mygrid.getColIndexById("catalogPrice")).setValue($v("catalogPriceDisplay"+rowid));
	    mygrid.cells(rowid,mygrid.getColIndexById("unitOfSalePrice")).setValue($v("unitOfSalePriceDisplay"+rowid));
	}  
}

function setDefaultInvByEntity(opsEntityId,currencyArray) {
	// use price group id to send customer id
	   var url="/tcmIS/distribution/shiptoentitypg.do?uAction=loaddata"+
	            "&opsEntityId="+$v('opsEntityId')+
	            "&customerId="+$v('customerId')+
	            "&priceGroupId="+$v('priceGroupId')+
	            "&companyId="+$v('shipToCompanyId')+
	            "&facilityId="+$v('shipToLocationId')+
	            "&inventoryGroupDefault="+$v('inventoryGroup');

	   callToServer(url+"&callback=processReqChangeJSON");
}

function setCurrencyIdAfterOpsChange(opsEntityId) {

	if (!opsEntityId || opsEntityId == '')
   	{
      opsEntityId = $("headerOpsEntityId").value;
    }

	homeCurrencyId = '';
	for ( var i=0; i < currencyArray.length; i++) {
	  	if(opsEntityId == currencyArray[i].opsEntityId) {
	    	homeCurrencyId = currencyArray[i].homeCurrencyId;
	    }
	}
	
	if ( homeCurrencyId !=  $v("currencyId")) {
//		$("headerCurrencyId").value = homeCurrencyId;
		$("currencyId").value = homeCurrencyId;
	  try {
		$("uAction").value  = "save";
   		showPageWait();
   		document.genericForm.submit();
   	  } catch(ex) {}
	}
}

function setDefaultPGINV() {
	// use price group id to send customer id
	   var url="/tcmIS/distribution/shiptoentitypg.do?uAction=loaddata"+
	            "&opsEntityId="+$v('opsEntityId')+
	            "&customerId="+$v('customerId')+ 
	            "&priceGroupId="+$v('priceGroupId')+ 
	            "&companyId="+$v('shipToCompanyId')+
	            "&facilityId="+$v('shipToLocationId')+
	            "&inventoryGroupDefault="+$v('inventoryGroup');
	   
	   callToServer(url+"&callback=processReqChangeJSON");
}

function processReqChangeJSON(xmldoc)
{   	
    $('inventoryGroup').value = xmldoc.inv;
	$('inventoryGroupNameSpan').innerHTML = xmldoc.invName;
	$('priceGroupId').value = xmldoc.pg;
	$('headerPriceGroupNameSpan').innerHTML = xmldoc.pgName;
    setCurrencyIdAfterOpsChange();
}



