function $(a) {
	return document.getElementById(a);
}

var prerow = null;
var preClass = null;
var lineMap = new Array();
var selectedRowId = null;
/*
function showNotes(fieldid)
{
   var section = 'div' + fieldid;
   var pgphBlock = 'pgph' + fieldid;
   if( document.getElementById(section) == null ) return;
   var current = (document.getElementById(section).style.display == 'block') ? 'none' : 'block';
   document.getElementById(section).style.display = current;
   document.getElementById(pgphBlock).innerHTML = (current == 'block') ? '' : '+';
}*/

function selectRow(rowId0, contextMenuName)
{
   var localMap = lineMap[rowId0];

   if ( localMap != prerow )
    {
     if( prerow != null ) {
		   for(var j = 0 ; j < prerow.length; j++ )
       {
          var rowClass = document.getElementById("rowClass"+prerow[j]).value;
          document.getElementById("rowId"+prerow[j]).className = rowClass;
       }
     }
	   prerow = localMap;
 	   //preClass = document.getElementById("rowClass"+localMap[0]).value;
     for(var i = 0 ; i < localMap.length; i++ )
     {
      var rowClass = document.getElementById("rowClass"+localMap[i]).value;
      rowClass =  "selected"+rowClass+"";      
      document.getElementById("rowId"+localMap[i]).className =rowClass;
     }
    }
    else {
/* no desecting processc
    	document.getElementById("rowId"+prerow).className = preClass;
		prerow = null;
*/
    }
    selectedRowId = rowId0;
    if (contextMenuName.trim().length > 0)
    {
     toggleContextMenu(""+contextMenuName+"");
    }
}

function printOrderPackingList()
{
 var prNumber = $('prNumber_'+selectedRowId).value;
 var lineItem = $('lineItem_'+selectedRowId).value;
  
 var loc = "printpackinglist.do?userAction=printPackingList&paperSize=46&supplierPackingViewBean[0].ok=ok&supplierPackingViewBean[0].prNumber="+prNumber+"&supplierPackingViewBean[0].lineItem="+lineItem+"";
 openWinGeneric(loc,"showVvHubBins","800","600","yes","80","80");
}

function updatePO()
{
  var poNo = document.getElementById('radianPO_'+selectedRowId).value;
  var loc = "splitpoline.do?action=search&radianPo="+poNo+"";
  openWinGeneric(loc,"showVvHubBins","900","600","yes","80","80");
}

function changeDlaShipTo()
{
  var prNumber = document.getElementById('prNumber_'+selectedRowId).value;
  var lineItem = document.getElementById('lineItem_'+selectedRowId).value;
  var radianPO = document.getElementById('radianPO_'+selectedRowId).value;
//  var loc = "/tcmIS/supply/changedlashipto.do?prNumber="+prNumber+"&lineItem="+lineItem+"";
  var loc = "/tcmIS/supply/changedlashipto.do?uAction=change&prNumber="+prNumber+"&lineItem="+lineItem+"&radianPO="+radianPO+"";
  openWinGeneric(loc,"changeDlaShipTo","900","280","yes","80","80");
}

/*Call this if you want to initialize something on load in the search frame.*/
function submitSearchForm() {
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 var flag = true;
  if(validateSearch()) {
   var now = new Date();
   document.getElementById("startSearchTime").value = now.getTime();
// for auto resubmit search..
   var action = document.getElementById('userAction');
   action.value="search";
   document.supplierLocationForm.target='resultFrame';
   parent.showPleaseWait();
   //document.supplierLocationForm.submit();
  }
  else
	  return false;
}

function validateSearch()
{
  var valid = true;
  var errMsg = '';

  if ($v("supplier") == '')
  {
	  if ($v("status") == '')
	  {
		  errMsg += messagesData.statusAll + "\n";
		  valid = false;
	  }
	  if($v("dateFrom") == '')
	  {
		  errMsg += messagesData.dateRangeAll;
		  valid = false;
	  }
  }
  
  if(!valid)
  {
	  alert(errMsg);
	  return false;
  }
  else
    return true;
}

function showErrorMessages()
{
  parent.showErrorMessages();
}

function myResultOnload()
{
 setResultFrameSize();
 /*Dont show any update links if the user does not have permissions.
 Remove this section if you don't have any links on the main page*/
 if (!showUpdateLinks)
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 } 
 displayFooterMessage();
}

/*
This is used to display a message on the footer section of the main page
*/
function displayFooterMessage() {
   var totalLines = document.getElementById("totalLines");
   var shippedCount = document.getElementById("shippedCount");
   var prcentShipped = 0;
   if (totalLines.value != null) {
     if (totalLines.value*1 > 0) {
       var startSearchTime = parent.window.frames["searchFrame"].document.getElementById("startSearchTime");
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
       var footer = parent.document.getElementById("footer");
       prcentShipped =Math.round((shippedCount.value/totalLines.value) *100);

       if (minutes != 0) {
         footer.innerHTML = messagesData.po+ " " +messagesData.recordFound+": "+totalLines.value+" -- " +messagesData.shipped+": "+shippedCount.value+" -- " +messagesData.percent+": "+prcentShipped+"% -- " +messagesData.searchDuration+": "+minutes+" "+messagesData.minutes+" "+seconds+" "+messagesData.seconds;
       }else {
         footer.innerHTML = messagesData.po+ " " +messagesData.recordFound+": "+totalLines.value+" -- " +messagesData.shipped+": "+shippedCount.value+" -- " +messagesData.percent+": "+prcentShipped+"% -- " +messagesData.searchDuration+": "+seconds+" "+messagesData.seconds;
       }
     }else {
       var footer = parent.document.getElementById("footer");
       footer.innerHTML ="";
     }
   }else {
     var footer = parent.document.getElementById("footer");
     footer.innerHTML ="&nbsp;";
   }
}

function generateExcel() {
  var flag = true;
  if(validateSearch()) {
    var action = document.getElementById("userAction");
    action.value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_DLAGasExcel','650','600','yes');
    document.supplierLocationForm.target='_DLAGasExcel';
    var a = window.setTimeout("document.supplierLocationForm.submit();",1000);
    return false;    
  }
}

/*function checkOpenOrdersOnly()
{
  var openOrdersOnly = document.getElementById("openOrdersOnly");
  if (openOrdersOnly.checked)
  {
  //var searchArgument = document.getElementById("searchArgument");
  //searchArgument.value = "";

  var statusSearch = document.getElementById("statusSearch");
  statusSearch.style["display"] = "";
  var statusDropSearch = document.getElementById("statusDropSearch");
  statusDropSearch.style["display"] = "";

  var approvedDates = document.getElementById("approvedDates");
  approvedDates.style["display"] = "";
  }
  else
  {
  var statusSearch = document.getElementById("statusSearch");
  statusSearch.style["display"] = "none";
  var statusDropSearch = document.getElementById("statusDropSearch");
  statusDropSearch.style["display"] = "none";
  var approvedDates = document.getElementById("approvedDates");
  approvedDates.style["display"] = "none";
  }      
}*/


function showSupplierLocation(selectedSupplier,selectedInv) {
  var idArray = altSupplierLocationId[selectedSupplier];
  var nameArray = altSupplierLocationName[selectedSupplier];
  var selectI = 0;
  var inserted = 0 ;

  var inv = $("suppLocationIdArray");
  for (var i = inv.length; i > 0; i--) {
    inv[i] = null;
  }
   if (selectedSupplier.trim().length > 0)
   {
    for (var i=0; i < nameArray.length; i++) {
//    	if( idArray[i] != "" )
    	{
    	setOption(inserted,nameArray[i],idArray[i], "suppLocationIdArray");
    	if( selectedInv == idArray[i] )
    			selectI = inserted;
    		inserted++;
    	}
  	  }
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

function supplierChanged() {
  var Supplier = $("supplier");
  var inv = $("suppLocationIdArray");
  var selectedSupplier = Supplier.value;
  showSupplierLocation(selectedSupplier,null);
}
/*

function setReportTimeMessage(message)
{
  parent.document.getElementById("reportTimeMessageLink").innerHTML=message;
  parent.document.getElementById("reportTimeMessageLink").style["display"] = ""; 
}
*/

function getShipToAddress(locationId, dodaac) {
var dodaac = document.getElementById('shipToDodaac_'+selectedRowId).value;
var locationId = document.getElementById('markForLocationId_'+selectedRowId).value;
openWinGeneric("viewaddress.do?locationId="+locationId+"&ultimateDodaac="+dodaac,'_viewaddress','500','300','yes');
}

function showDetail() {
var radianPo = document.getElementById('radianPO_'+selectedRowId).value;
var supplier = document.getElementById('supplier_'+selectedRowId).value;  
var suppLocationIdArray = document.getElementById('shipFrom_'+selectedRowId).value;
var loc = "/tcmIS/supplier/packhistorymain.do?searchField=radianPo&searchMode=is&radianPo="+radianPo+"&searchAction=Auto&supplier="+supplier+"&suppLocationIdArray="+suppLocationIdArray+"";

openWinGeneric(loc,'_Show_pack_history_Detail','800','600','yes','','','no');
} 

function showTrackingInformation() {
  var carrierName = document.getElementById('carrierName_'+selectedRowId).value;
  var trackingNumber = document.getElementById('trackingNumber_'+selectedRowId).value;
  var loc = null;
      
      if(carrierName == 'UPS') {
        loc = "http://wwwapps.ups.com/WebTracking/processInputRequest?HTMLVersion=5.0&loc=en_US&Requester=UPSHome&AgreeToTermsAndConditions=yes&ignore=&track.x=32&track.y=6&tracknum=" + trackingNumber;   
        window.open(loc,'trackingInfo','width=800,height=800');
      }
      if(carrierName == 'Old Dominion') {
        loc = "http://www.odfl.com/trace/Trace.jsp?action=Status&Type=P&pronum=" + trackingNumber;   
        window.open(loc,'trackingInfo','width=800,height=800');
      }
      if(carrierName == 'Fedex') {
        loc = "http://www.fedex.com/Tracking?ascend_header=1&clienttype=dotcom&cntry_code=us&language=english&tracknumbers=" + trackingNumber;   
        window.open(loc,'trackingInfo','width=800,height=800');
      }
      if(carrierName == 'ABF') {
        loc = "https://www.abfs.com/tools/trace/default.asp?hidSubmitted=Y&hidNotifyBy=S&DefaultView=S&reftype0=a&refno0=" + trackingNumber;   
        window.open(loc,'trackingInfo','width=800,height=800');
      }
      if(carrierName == 'Southeast') {
        loc = "https://www.sefl.com/seflWebsite/servlet?GUID=&externalMenu=true&action=Tracing_Trace_by_pro&Type=PN&RefNum=" + trackingNumber;   
        window.open(loc,'trackingInfo','width=800,height=800');
      }
}

function changeSupplier()
{
  var action = document.getElementById("userAction");
  action.value = 'changeSupplier';
  var radianPO = document.getElementById("radianPO");
  radianPO.value = $('radianPO_'+selectedRowId).value;
  if (radianPO.value.trim().length > 0)
  {
    openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_ChangeSupplier','400','200','yes',"50","50","no");
    document.supplierLocationForm.target='_ChangeSupplier';
    var a = window.setTimeout("document.supplierLocationForm.submit();",500);
  }
}

function cancelOrder()
{
  var action = document.getElementById("userAction");
  action.value = 'cancelOrder';
  var docNumWithSuffix = document.getElementById("docNumWithSuffix");
  docNumWithSuffix.value = $('docNumWithSuffix_'+selectedRowId).value;
  if (docNumWithSuffix.value.trim().length > 0)
  {
    openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_CancelOrder','400','200','yes',"50","50","no");
    document.supplierLocationForm.target='_CancelOrder';
    var a = window.setTimeout("document.supplierLocationForm.submit();",500);
  }
}

function reSubmit()
{
	var action = document.getElementById("userAction");
  	action.value = 'search';
   	parent.showPleaseWait();
	document.supplierLocationForm.target='resultFrame';
   	document.supplierLocationForm.submit();
}

function showEllisDetail() {
var customerHaasContractId = document.getElementById('customerHaasContractId_'+selectedRowId).value;
var releaseNum = document.getElementById('releaseNum_'+selectedRowId).value;
var milstripCode = document.getElementById('milstripCode_'+selectedRowId).value;

var loc ="";
if (releaseNum.trim().length > 0)
{
 loc = "http://ellis.aviation.dla.mil/detail.asp?type=C&contract=SPM4AR07D0100&call="+releaseNum;
}
else
{
 loc = "http://ellis.aviation.dla.mil/detail.asp?type=R&requisition="+milstripCode;
}

openWinGeneric(loc,'_Show_pack_history_Detail','800','600','yes');
}

function addRemContains(val)
{
	var searchMode = $('searchMode');
	if(val=='serialNumber' && searchMode.options[0].value == 'is')
	{
		searchMode.remove(0);
		var option = document.createElement("option");
		option.text = messagesData.contains;
		option.value='contains';
		searchMode.add(option);
	}
	else if(searchMode.options[0].value == 'contains')
	{
		searchMode.remove(0);
		var option = document.createElement("option");
		option.text = messagesData.is;
		option.value='is';
		searchMode.add(option);
	}		
}

function addViewPOD()
{

	var prNumber = document.getElementById("prNumber_"+selectedRowId).value;
	var poNumber = document.getElementById("poNumber_"+selectedRowId).value;

	 var loc = "/tcmIS/distribution/showmrdocuments.do?showDocuments=Yes&prNumber="+prNumber+"&custPoNumber="+poNumber+"&launchedFrom=supplier";
	 try {
	 	parent.children[parent.children.length] = openWinGeneric(loc,"showAllMrDocuments","450","300","no","80","80");
	 } catch (ex){
	 	openWinGeneric(loc,"showAllMrDocuments","450","300","no","80","80");
	 }
}

