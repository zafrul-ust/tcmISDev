<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<%@ include file="/common/locale.jsp" %>
<tcmis:fontSizeCss currentCss="haasGlobal.css"/>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script src="/js/common/disableKeys.js" language="JavaScript"></script>
<%-- Add any other stylesheets you need for the page here --%>


<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>

<%-- For Calendar support --%>

<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>

<script language="JavaScript" type="text/javascript">
<!--
var selectedRowId = null;
var accountNumber = new Array();
var accountOwner = new Array();
var altModeOfTransport = new Array();
var altIncoterm = new Array();
var altIncotermDesc = new Array();

var showPrintInvoice = '';
<c:if test="${showPrintInvoice == 'Y'}">
	showPrintInvoice = 'Y';
</c:if>

<c:forEach var="shipmentBean" items="${shipmentBeanCollection}" varStatus="status">
  <c:set var="currentShipmentId" value='${status.current.shipmentId}'/>
  <c:set var="carrierCollection" value='${status.current.carrierInfoBeanCollection}'/>

  <c:forEach var="carrierInfoBean" items="${shipmentBean.carrierInfoBeanCollection}" varStatus="status1">
    accountNumber["<c:out value="${currentShipmentId}"/>-<c:out value="${status1.current.carrierCode}"/>"] = "<c:out value="${status1.current.account}"/>";
    <c:if test="${!empty status.current.defaultTxCarrierCode}">
	    accountNumber["<c:out value="${currentShipmentId}"/>-<c:out value="${status.current.defaultTxCarrierCode}"/>"] = "<c:out value="${status.current.defaultTxCarrierAcct}"/>";
	</c:if>
  </c:forEach>

  <c:forEach var="carrierInfoBean" items="${shipmentBean.carrierInfoBeanCollection}" varStatus="status1">
  <c:set var="accname" value="${status1.current.companyId}"/>
  <c:if test="${status1.current.companyId == 'Radian'}">
  	<c:set var="accname" value="Haas TCM"/>
  </c:if>
    accountOwner["<c:out value="${currentShipmentId}"/>-<c:out value="${status1.current.carrierCode}"/>"] = "${accname}";

  <c:if test="${!empty status.current.defaultTxCarrierCode}">
  	<c:set var="accname" value="${status.current.defaultTxCarrierComp}"/>
      <c:if test="${status.current.defaultTxCarrierComp == 'Radian'}">
  		<c:set var="accname" value="Haas TCM"/>
	  </c:if>
	accountOwner["<c:out value="${currentShipmentId}"/>-<c:out value="${status.current.defaultTxCarrierCode}"/>"] = "${accname}";
  </c:if>
  </c:forEach>

</c:forEach>

<c:forEach var="motKey" items="${motIncotermCollection}" varStatus="motStatus">		 	
	altModeOfTransport[${motStatus.index}] = '<tcmis:jsReplace value="${motKey.modeOfTransport}"/>';   //add mode of transport	
				
	altIncoterm['<tcmis:jsReplace value="${motKey.modeOfTransport}"/>'] = new Array();     // add incoterms for this mode of transport
	altIncotermDesc['<tcmis:jsReplace value="${motKey.modeOfTransport}"/>'] = new Array();	   // add incoterms for this mode of transport
	<c:forEach var="incKey" items="${motKey.vvIncotermList}" varStatus="incStatus">
		altIncoterm['<tcmis:jsReplace value="${motKey.modeOfTransport}"/>'][${incStatus.index}]='<tcmis:jsReplace value="${incKey.incoterm}"/>';
		altIncotermDesc['<tcmis:jsReplace value="${motKey.modeOfTransport}"/>'][${incStatus.index}]='<tcmis:jsReplace value="${incKey.incotermShortDesc}"/>';
	</c:forEach>
</c:forEach>

function confirmNotAutoShipment() {
	  var flag = validateForm();
	  
	  if (flag)
		  flag = validateMotIncData();
	  
	  if(flag) {
	    var action = document.getElementById("uAction");
	    action.value = 'confirmNotAutoShipment';
	    submitOnlyOnce();
	    document.genericForm.submit();
	  }
	}

function generateInvoice() {
  var flag = validateForm();
  if (flag)
	  flag = validateMotIncData();
  if(flag) {
    var action = document.getElementById("uAction");
    action.value = 'printInvoice';    
    var totallines = document.getElementById("rowCount").value;
	totallines = totallines*1;
	for (var p = 0 ; p < totallines ; p++)  {
		var linecheck = document.getElementById("checkbox"+(p*1)+"");
		if(linecheck != null && linecheck.checked) {
			var invoiceBy = document.getElementById("invoiceBy"+(p*1)+"").value;
			var invoiceAtShipping = document.getElementById("invoiceAtShipping"+(p*1)+"").value;
			if( invoiceAtShipping != 'Y' || (invoiceAtShipping == 'Y' && invoiceBy != 'Shipment' && invoiceBy != 'Order')) {				
				linecheck.checked = false;
			}
		}
	}
    submitOnlyOnce();
    document.genericForm.submit();
  }
}

//TODO: finish this function !!!
function getContainerLabels() {
  var flag = validateForm();
  if(flag) {
  
//    callToServer("confirmshipment.do?uAction=customerPoDupCheck&receipIdList="+$v("companyId")+"&prNumber="+$v("prNumber")+"&callBack=confirmMRAfterDupCheck");
  }
}

function print(){
	var action = document.getElementById("uAction").value;
	
	if(action == "printInvoice")
		printInvoice();
	else if(action == "printProForma")
		printProForma();
}

function printProForma() {
	var selectedShipments = document.getElementById("selectedShipments").value;
	
	if (selectedShipments.trim().length > 0 ) {
    	var loc = "/HaasReports/report/printshipmentproformainvoice.do?shipmentIds="+selectedShipments+"&personnelId="+$v("personnelId");
    	openWinGeneric(loc,"printDistributionInvoiceDocument","800","600","yes","50","50","20","20","no");
	}
}

function printInvoice() {
   
   var confirmedShipments = document.getElementById("confirmedShipments").value;
   var cmsConfirmedShipments = document.getElementById("cmsConfirmedShipments").value;

   if (cmsConfirmedShipments.trim().length > 0 ||  confirmedShipments.trim().length > 0 ) {
	   	var confirmMsg = "You can generate Invoice for the following shipment IDs - ";
		if (confirmedShipments.trim().length > 0 ) {
			confirmMsg =  confirmMsg + confirmedShipments;	
		}
		
		if (cmsConfirmedShipments.trim().length > 0 ) {
			confirmMsg =  confirmMsg + cmsConfirmedShipments;	
		}
	
		var response = confirm(confirmMsg + "\n Do you want to continue?");
		
		if (response == false) {
	        return;
	    } else {
			if (confirmedShipments.trim().length > 0 ) {
    	    	var loc = "/HaasReports/report/printdistributioninvoice.do?confirmedShipments="+confirmedShipments+"&personnelId="+$v("personnelId");
    	    	openWinGeneric(loc,"printDistributionInvoiceDocument","800","600","yes","50","50","20","20","no");
			}
			if (cmsConfirmedShipments.trim().length > 0 ) {
				var loc = "/HaasReports/report/printshipmentinvoice.do?shipmentIds="+cmsConfirmedShipments+"&fromShipConfirm=Y";
    	    	openWinGeneric(loc,"printCMSInvoiceDocument","800","600","yes","50","50","20","20","no");
			}
	    }
   }   
}

function printSelectedInvoice() {
	
	if (selectedRowId == null) {
		alert("Please select a row to continue");
		return;
	}
		
	var confirmedShipments = document.getElementById("confirmedShipments").value;   
	var shipmentId = document.getElementById("shipmentId"+selectedRowId).value;
	var invoiceAtShipping = document.getElementById("invoiceAtShipping"+selectedRowId).value;
	var invoiceBy = document.getElementById("invoiceBy"+selectedRowId).value;
	var shipConfirmDate= document.getElementById("shipConfirmDate"+selectedRowId).value;
  
	//alert("selected - " + invoiceAtShipping + " shipmentId - " + shipmentId + " invoiceBy - " + invoiceBy);
	if (invoiceAtShipping != 'Y')
		return;
	//alert(shipConfirmDate);
	if (shipConfirmDate != null && shipConfirmDate.trim().length > 0)	{
		if (invoiceBy == 'Order') {
			var loc = "/HaasReports/report/printdistributioninvoice.do?confirmedShipments="+shipmentId+"&personnelId="+$v("personnelId");
			openWinGeneric(loc,"printInvoiceDocument","800","600","yes","50","50","20","20","no");	
		} else if (invoiceBy == 'Shipment'){
			var loc = "/HaasReports/report/printshipmentinvoice.do?shipmentIds="+shipmentId+"&fromShipConfirm=Y";
			openWinGeneric(loc,"printCMSInvoiceDocument","800","600","yes","50","50","20","20","no");
		}		
	}
}

function carrierChanged(rowNumber) {
	  var shipmentId = document.getElementById("shipmentId"+rowNumber);
	  var carrierCode = document.getElementById("carrierCode"+rowNumber);
	  var account = document.getElementById("account"+rowNumber);
	  var companyId = document.getElementById("companyId"+rowNumber);
	  account.value = accountNumber[shipmentId.value+"-"+carrierCode.value];
	  $("accountDisplay"+rowNumber).innerHTML  = accountNumber[shipmentId.value+"-"+carrierCode.value];
	  companyId.value = accountOwner[shipmentId.value+"-"+carrierCode.value];
	  $("companyIdDisplay"+rowNumber).innerHTML = accountOwner[shipmentId.value+"-"+carrierCode.value];
	//alert(accountNumber[shipmentId.value+"-"+carrierCode.value]);
	//alert(shipmentId.value + "-" + carrierCode.value);
	}

	function validateForm() {
	  if(!isAnyRowChecked()) {
	    alert(messagesData.noRowChecked);
	    return false;
	  }
	  return true;
	}

	function isAnyRowChecked() {
	  var rowCount = document.getElementById("rowCount");
	  var rowChecked = false;
	  for(var i=0; i<rowCount.value && !rowChecked; i++) {
	    try
        {
		    var checkbox = document.getElementById("checkbox" + i);
		    if(checkbox != null) {
		      rowChecked = checkbox.checked;
		    }
	    }
        catch (ex)
        {            
        }
      }
	  return rowChecked;
	}

	function validateMotIncData() 
	{
	   var totallines = document.getElementById("rowCount").value;
	   totallines = totallines*1;	   
	   var error = "";
	   for (var p = 0 ; p < totallines ; p++)  {
		   var linecheck = document.getElementById("checkbox"+(p*1)+"");
		   if(linecheck != null && linecheck.checked) {
			   if(document.getElementById("incotermRequired"+(p*1)+"").value == 'Y' 
				   && ( document.getElementById("modeoftransport"+(p*1)+"").value == '' 
						|| document.getElementById("incoterm"+(p*1)+"").value == '') ) 
			   {
				   if (error != "") {
					   error = error + ", ";
				   }
				   error = error + document.getElementById("shipmentId"+(p*1)+"").value;				   				   
			   }
		   }
	   }
	   if (error != "") {
		   alert(messagesData.selectmotandincoterms + " - " + error);
		   return false;
	   }
	   return true;
	}
	
	function checkAll(rowCount) {
	  var allCheck = document.getElementById("allCheck");
	  var check = true;
	  if(!allCheck.checked) {
	    check = false;
	  }
	  for(var i=0; i<rowCount; i++) {
	    var checkbox = document.getElementById("checkbox" + i);
	    if (checkbox != null) {
	    	checkbox.checked = check;
	    }
	  }
	}

	function checkBox(rowNumber, originalShipmentId) {
	  var shipmentId = document.getElementById("shipmentId" + rowNumber);
	  var check = true;
	  if(shipmentId != null && shipmentId.value == originalShipmentId) {
	    check = false;
	  }
	  var checkbox = document.getElementById("checkbox" + rowNumber);
	  if (checkbox != null) {
	  	checkbox.checked = check;
	  }
	}

	function consolidatedBol() {
	  var action = document.getElementById("uAction");
	  action.value = 'consolidatedBol';
	  submitOnlyOnce();
	  document.genericForm.submit();
	}
	function cancel() {
	  window.close();
	}

	function returnToMain() {
	 try {	opener.refreshme();
	 }catch(ex){};
//		var action = opener.document.getElementById("uAction");
//	  action.value = 'submitSearch';
//		opener.parent.showPleaseWait();
//		opener.document.genericForm.submit();	
	  //opener.window.frames["searchFrame"].search();
	  window.close();
	}
	function showErrorMessages()
	{
			parent.showErrorMessages();
	}

	
	function showIncotermOptions(selectedMot, index) {		
		var incotermArray = altIncoterm[selectedMot];
		var incotermDescArray = altIncotermDesc[selectedMot];
		var selectedIncotermIndex = 0;
		var defaultIncoterm = $v("hincoterm"+index);
		
		if(incotermArray != null && incotermArray.length > 0) {
			var count = 0;
	        if(incotermArray.length > 1) {
		   	    setOption(count++, messagesData.pleaseselect, "", "incoterm"+index);
			}
			for (var i=0; i < incotermArray.length; i++) {
				setOption(i+count,incotermDescArray[i], incotermArray[i], "incoterm"+index);
				//set default incoterm or selected incoterm
				if (defaultIncoterm != null && incotermArray[i] == defaultIncoterm) 
					selectedIncotermIndex = i+count;
			}
		} else {
			  setOption(0,messagesData.pleaseselect,"", "incoterm"+index);
		}		
	    $("incoterm"+index).selectedIndex = selectedIncotermIndex;
	}

	function modeOfTransportChanged(index) {		
		var selectedMot = $v("modeOfTransport"+index);
		var off = $('incoterm'+index);
		// clear incoterm drop down
		var incotermO = document.getElementById("incoterm"+index);
		for (var i = incotermO.length; i >= 0;i--) {
			incotermO[i] = null;
		}		
		showIncotermOptions(selectedMot, index);		
	}

	function showMotOptions(index) {		
		var modeOfTransportArray = altModeOfTransport;		
		var selectedMotIndex = 0;
		var defaultModeOfTransport = $v("hmodeOfTransport"+index);
		if(modeOfTransportArray != null ) {
			var count = 0;
			if(modeOfTransportArray.length > 1) {
		   	    setOption(count++, messagesData.pleaseselect, "", "modeOfTransport"+index);
			}
			for (var i=0; i < modeOfTransportArray.length; i++) {
				setOption(i+count,modeOfTransportArray[i], modeOfTransportArray[i], "modeOfTransport"+index);
				//set default modeOfTransport or selected modeOfTransport
				if (defaultModeOfTransport != null && modeOfTransportArray[i] == defaultModeOfTransport) 
					selectedMotIndex = i+count;
			}
			
			if(modeOfTransportArray.length <= 1){
				// hide mot drop down 
				var off = $('modeOfTransport'+index);
				off.style['display'] = 'none';
			}
		}		
	    $("modeOfTransport"+index).selectedIndex = selectedMotIndex;
	    modeOfTransportChanged(index);
	}
	
	function setIncMotValues() {
		
		var rowCount = document.getElementById("rowCount").value;
		for(var i = 0; i < rowCount; i++ 	) {
			var incReq = document.getElementById('incotermRequired'+i).value;
			if (incReq != null && incReq == "Y") 
				showMotOptions(i);	
		} 
	}
	
	var displayPrintInvoiceLinkForCheckBox = false;
	function checkInvoiceLink(rowId) {	
		if (displayPrintInvoiceLinkForCheckBox) 
			return;
		
		var rowCount = document.getElementById("rowCount");
		var rowChecked = false;		  
		for(var i=0; i<rowCount.value && !rowChecked; i++) {
			try {
				if (i == rowId)
		    		continue;
				
		    	var checkbox = document.getElementById("checkbox" + i);
		    	if(checkbox != null) {
		      		rowChecked = checkbox.checked;
			    }
			} catch (ex) {}
		}
		if (rowChecked) {
			return;
		} else {
			var checkbox = document.getElementById("checkbox" + rowId);
			var invoiceAtShipping = document.getElementById("invoiceAtShipping" + rowId);
	    	if(checkbox != null && checkbox.checked && showPrintInvoice == 'Y' && !displayPrintInvoiceLinkForCheckBox) {
	    		if (invoiceAtShipping == 'Y' ) {
					displayPrintInvoiceLinkForCheckBox = true;
					$("printInvoiceSpan").style.display = "";
				} else {
					displayPrintInvoiceLinkForCheckBox = false;
					$("printInvoiceSpan").style.display = "none";
				}
	    	}
		}			
	}
	
	function generateProForma() {
		  var flag = validateForm();
		  
		  if (flag)
			  flag = validateMotIncData();
		  
		  if(flag) {
		    var action = document.getElementById("uAction");
		    action.value = 'printProForma';
		    submitOnlyOnce();
		    document.genericForm.submit();
		  }
		}
// -->
</script>

<%-- These are for the Grid, uncomment if you are going to use the grid --%>
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<%-- This is for the YUI, uncomment if you will use this --%>
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="shipmentconfirm.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
noRowChecked:"<fmt:message key="error.norowselected"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
selectmotandincoterms:"<fmt:message key="label.selectmotandincoterms"/>",
invalidDateFormat:"<fmt:message key="error.date.invalid"/>"};


if( '${confirmed}' == 'confirmed' ) {
	try{ opener.refreshme();
	}catch(ex){}
}

with(milonic=new menuname("showMenu")){
    top="offset=2"
        style = contextStyle;
        margin=3
        aI("text=<fmt:message key="label.printinvoice"/>;url=javascript:printSelectedInvoice();");
}

drawMenus();
	
function nowShowRightClickMenu(rowNum) {	

	var invoiceAtShipping = document.getElementById('invoiceAtShipping'+rowNum).value;	
	
	if (invoiceAtShipping == 'Y') {	
	    toggleContextMenu('showMenu');
	} else {
		toggleContextMenu('contextMenu');
	}		
}


function selectRow(e, rowId) {
	
	var selectedRow = document.getElementById("rowId" + rowId + "");
	var selectedRowClass = document.getElementById("colorClass" + rowId + "");
	selectedRow.className = "selected" + selectedRowClass.value + "";

	if (selectedRowId != null && rowId != selectedRowId) {
		var previouslySelectedRow = document.getElementById("rowId" + selectedRowId + "");
		var previouslySelectedRowClass = document.getElementById("colorClass" + selectedRowId + "");
		previouslySelectedRow.className = "" + previouslySelectedRowClass.value + "";
	}  
	selectedRowId = rowId;  

	var isRightMB;
	e = e || window.event;	
	if ("which" in e)  // Gecko (Firefox), WebKit (Safari/Chrome) & Opera
	    isRightMB = e.which == 3; 
	else if ("button" in e)  // IE, Opera 
	    isRightMB = e.button == 2; 
		
	if(isRightMB) {		
		nowShowRightClickMenu(rowId);
	}
} 

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setIncMotValues();print();">

<tcmis:form action="/confirmshipment.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">

<div class="contentArea">
<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->

<c:if test="${shipmentBeanCollection != null}" >
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your mail table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead">
<c:set var="hasPermission" value='false'/>
  <tcmis:inventoryGroupPermission indicator="true" userGroupId="shipConfirm">
    <c:set var="hasPermission" value='true'/>  
  </tcmis:inventoryGroupPermission>
<c:if test="${hasPermission == 'true'}">
<c:set var="deliveredDate"><tcmis:jsReplace value="${param.deliveredDate}"/></c:set>
<c:set var="uAction"><tcmis:jsReplace value="${param.uAction}"/></c:set>
<c:if test="${empty deliveredDate}">
  <c:set var="deliveredDate">
    <tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>
  </c:set>

</c:if>
  <input name='datebb' id='datebb' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="-60" datePattern="${dateFormatPattern}"/>'  /> 
  <input name='dateff' id='dateff' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="1" datePattern="${dateFormatPattern}"/>'  />
<fmt:message key="label.datedelivered"/>:
<input class="inputBox pointer" name="deliveredDate" id="deliveredDate" type="text" value="${deliveredDate}" size="9" onclick="return getCalendar(document.genericForm.deliveredDate,document.genericForm.datebb,null,null,document.genericForm.dateff);"/>

<c:choose>
	<c:when test="${uAction == 'showPrintProForma' || uAction == 'printProForma'}">
		<a href="#" onclick="generateProForma(); return false;"><fmt:message key="label.printproforma"/></a> |
	</c:when>
	<c:otherwise>
		<a href="#" onclick="confirmNotAutoShipment(); return false;"><fmt:message key="label.confirmshipment"/></a> |
	</c:otherwise>
</c:choose>

<%--<a href="#" onclick="consolidatedBol(); return false;"><fmt:message key="label.consolidatedbol"/></a> |--%>
 <span id="printInvoiceSpan" style="display:none;"><a href="#" onclick="generateInvoice(); return false;"><fmt:message key="label.printinvoice"/></a> | </span>
 <span id="containerLabelsSpan" style="display:none;"><a href="#" onclick="getContainerLabels(); return false;"><fmt:message key="label.containerlabels"/></a> | </span>
</c:if>
<a href="#" onclick="returnToMain(); return false;"><fmt:message key="label.returntomain"/></a> |
<a href="#" onclick="cancel(); return false;"><fmt:message key="label.cancel"/></a> 

</div>
    <div class="dataContent">
    <table width="100%" border="1" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>
    <c:set var="showPrintInvoice" value='N'/>
<bean:size collection="${shipmentBeanCollection}" id="resultSize"/>
    <c:forEach var="shipmentBean" items="${shipmentBeanCollection}" varStatus="status">
    <c:set var="dataCount" value='${dataCount+1}'/>

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
    <th width="2%"><fmt:message key="label.ok"/>
<c:if test="${status.index == 0 && hasPermission == 'true'}">
<br/><input name="allCheck" id="allCheck" type="checkbox" value="all" onclick="checkAll(${resultSize});"/>
</c:if>
</th>
    <th width="5%"><fmt:message key="label.shipmentid"/></th>
    <th width="5%"><fmt:message key="label.inventorygroup"/></th>
    <th width="5%"><fmt:message key="label.carrier"/></th>
    <th width="5%"><fmt:message key="label.trackingnumber"/></th>
    <th width="5%"><fmt:message key="label.accountowner"/></th>
    <th width="5%"><fmt:message key="label.accountnumber"/></th>
    <th width="5%"><fmt:message key="label.freightcharge"/></th>
    <th width="5%"><fmt:message key="label.modeoftransport"/></th>
	<th width="5%"><fmt:message key="label.incoterms"/></th>
	<th width="5%"><fmt:message key="label.proposedexportdate"/></th>
    </tr>
    </c:if>

    <c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>

  <c:set var="hasPermission" value='false'/>
  <tcmis:inventoryGroupPermission indicator="true" userGroupId="shipConfirm" inventoryGroup="${status.current.inventoryGroup}">
    <c:set var="hasPermission" value='true'/>  
  </tcmis:inventoryGroupPermission>

    <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>" onmouseup="selectRow(event, '${status.index}')">
    <input type="hidden" name="colorClass${status.index}" id="colorClass<c:out value="${status.index}"/>" value="${colorClass}" />
     <td width="2%">
<%--
<input type="hidden" name="shipConfirmInputBean[<c:out value="${status.index}"/>].prNumber" value="<c:out value="${status.current.prNumber}"/>"/>
<input type="hidden" name="shipConfirmInputBean[<c:out value="${status.index}"/>].lineItem" value="<c:out value="${status.current.lineItem}"/>"/>
<input type="hidden" name="shipConfirmInputBean[<c:out value="${status.index}"/>].picklistId" value="<c:out value="${status.current.batch}"/>"/>
<input type="hidden" name="shipConfirmInputBean[<c:out value="${status.index}"/>].shipmentId" value="<c:out value="${status.current.shipmentId}"/>"/>
<input type="hidden" name="shipConfirmInputBean[<c:out value="${status.index}"/>].carrierCode" value="<c:out value="${status.current.carrierCode}"/>"/>
<input type="hidden" name="shipConfirmInputBean[<c:out value="${status.index}"/>].trackingNumber" value="<c:out value="${status.current.trackingNumber}"/>"/>
<input type="hidden" name="shipConfirmInputBean[<c:out value="${status.index}"/>].deliveredDate" value="<c:out value="${status.current.deliveredDate}"/>"/>
<input type="hidden" name="shipConfirmInputBean[<c:out value="${status.index}"/>].personnelId" value="<c:out value="${personnelBean.personnelId}"/>"/>
--%>
<input type="hidden" name="shipConfirmInputBean[<c:out value="${status.index}"/>].inventoryGroup" id="inventoryGroup<c:out value="${status.index}"/>" value="<c:out value="${status.current.inventoryGroup}"/>"/>
<input type="hidden" name="shipConfirmInputBean[<c:out value="${status.index}"/>].shipmentId" id="shipmentId<c:out value="${status.index}"/>" value="<c:out value="${status.current.shipmentId}"/>"/>
<input type="hidden" name="shipConfirmInputBean[<c:out value="${status.index}"/>].autoShipConfirm" id="autoShipConfirm<c:out value="${status.index}"/>" value="N"/>
<fmt:formatDate var="formattedShipConfirmDate" value="${status.current.shipConfirmDate}" pattern="${dateFormatPattern}"/>
<input type="hidden" name="shipConfirmInputBean[<c:out value="${status.index}"/>].shipConfirmDate" id="shipConfirmDate<c:out value="${status.index}"/>" value="<c:out value="${formattedShipConfirmDate}"/>"/>
<input type="hidden" name="shipConfirmInputBean[${status.index}].invoiceAtShipping" id="invoiceAtShipping${status.index}" value="${status.current.invoiceAtShipping}"/>
<input type="hidden" name="shipConfirmInputBean[${status.index}].incotermRequired" id="incotermRequired${status.index}" value="${status.current.incotermRequired}"/>
<input type="hidden" name="shipConfirmInputBean[${status.index}].invoiceBy" id="invoiceBy${status.index}" value="${status.current.invoiceBy}"/>
<input type="hidden" name="shipConfirmInputBean[${status.index}].proFormaRequired" id="proFormaRequired${status.index}" value="${status.current.proFormaRequired}"/>
<fmt:formatDate var="fmtLastProFormaPrintDate" value="${status.current.lastProFormaPrintDate}" pattern="${dateFormatPattern}"/>
<input type="hidden" name="shipConfirmInputBean[${status.index}].lastProFormaPrintDate" id="lastProFormaPrintDate${status.index}" value="${fmtLastProFormaPrintDate}"/>

  
<c:if test="${hasPermission == 'true'}">
<input name="shipConfirmInputBean[${status.index}].selected" id="checkbox${status.index}" type="checkbox" value="Y" onclick="checkInvoiceLink(${status.index})"/>
</c:if>
     </td>

  <c:if test="${!empty status.current.shipConfirmDate}">
    <c:set var="hasPermission" value='false'/>
    <c:set var="showPrintInvoice" value='Y'/>
  </c:if>
        
     <td width="5%"><c:out value="${status.current.shipmentId}"/></td>
     <td width="5%"><c:out value="${status.current.inventoryGroup}"/></td>
    <c:choose>
     <c:when test="${hasPermission == 'true'}">
      <td width="5%">
      <c:if test="${ empty status.current.defaultTxCarrierCode}">
        <select name="shipConfirmInputBean[<c:out value="${status.index}"/>].carrierCode" id="carrierCode<c:out value="${status.index}"/>" class="selectBox" onchange="carrierChanged('<c:out value="${status.index}"/>');">
            <option value=""></option>
       <c:forEach var="carrierInfoBean" items="${shipmentBean.carrierInfoBeanCollection}" varStatus="status1">
         <option value="<c:out value="${status1.current.carrierCode}"/>"
            <c:if test="${status0.current.carrierCode == status1.current.carrierCode}">
            selected
            </c:if>
            ><c:out value="${status1.current.carrierName}"/> | <c:out value="${status1.current.carrierMethod}"/> | <c:out value="${status1.current.carrierCode}"/></option>
        </c:forEach>
        </select>
        </c:if>
      <c:if test="${ !empty status.current.defaultTxCarrierCode}">
        <select name="shipConfirmInputBean[<c:out value="${status.index}"/>].carrierCode" id="carrierCode<c:out value="${status.index}"/>" class="selectBox" onchange="carrierChanged('<c:out value="${status.index}"/>');">
			<option value="<c:out value="${status.current.defaultTxCarrierCode}"/>" selected><c:out value="${status.current.defaultTxCarrierName}"/> | <c:out value="${status.current.defaultTxCarrierMthd}"/> | <c:out value="${status.current.defaultTxCarrierCode}"/></option>
       <c:forEach var="carrierInfoBean" items="${shipmentBean.carrierInfoBeanCollection}" varStatus="status1">
       		<c:if test="${status.current.defaultTxCarrierCode ne status1.current.carrierCode}">
        	 <option value="<c:out value="${status1.current.carrierCode}"/>"><c:out value="${status1.current.carrierName}"/> | <c:out value="${status1.current.carrierMethod}"/> | <c:out value="${status1.current.carrierCode}"/></option>
         	</c:if>
        </c:forEach>
        </select>
        </c:if>
     </td>
     <td width="5%"><input name="shipConfirmInputBean[${status.index}].trackingNumber" id="trackingNumber${status.index}" type="text" class="inputBox" value="${status.current.trackingNumber}" size="15"/></td>
     <td width="5%">
     	<span id="companyIdDisplay${status.index}">
     	   <c:if test="${ ! empty status.current.defaultTxCarrierCode}">
				<c:out value="${status.current.defaultTxCarrierComp}"/>
		   </c:if>
     	</span>
     </td>
     <td width="5%"><span id="accountDisplay${status.index}">
     	   <c:if test="${ ! empty status.current.defaultTxCarrierCode}">
				<c:out value="${status.current.defaultTxCarrierAcct}"/>
		   </c:if>
     </span></td>
     <td width="5%">
     	<c:choose>
		     <c:when test="${status.current.inventoryGroup == 'St Louis Kilfrost'}" >
		       <input name="shipConfirmInputBean[${status.index}].freightCharge" id="freightCharge${status.index}" type="text" class="inputBox" size="15"/>
		       <c:if test="${!empty status.current.currencyId}">(${status.current.currencyId})</c:if>   
		     </c:when>
		     <c:otherwise>
		      	&nbsp;
		     </c:otherwise>
		</c:choose>
     			    <input name="shipConfirmInputBean[${status.index}].companyId" id="companyId${status.index}" type="hidden" class="inputBox" size="10" readonly="readonly"/>
     			    <input name="shipConfirmInputBean[${status.index}].account" id="account${status.index}" type="hidden" class="inputBox" size="15" readonly="readonly"/>
     				<input name="shipConfirmInputBean[${status.index}].currencyId" id="currencyId${status.index}" type="hidden" value="${status.current.currencyId}"/>
     </td>
     <td width="5%">	
			<c:if test="${status.current.incotermRequired != null && status.current.incotermRequired == 'Y'}">
				<input type="hidden" name="hmodeOfTransport${status.index}" id="hmodeOfTransport${status.index}" value="${status.current.modeOfTransport}" />
				<select name="shipConfirmInputBean[${status.index}].modeOfTransport" id="modeOfTransport${status.index}" onChange="modeOfTransportChanged(${status.index});">		 	
				</select>&nbsp;	
			</c:if>			
	</td>
	<td width="5%">	
			<c:if test="${status.current.incotermRequired != null && status.current.incotermRequired == 'Y'}">
				<input type="hidden" name="hincoterm${status.index}" id="hincoterm${status.index}" value="${status.current.incoterm}" />
				<select name="shipConfirmInputBean[${status.index}].incoterm" id="incoterm${status.index}">			 	
				</select>&nbsp;	
			</c:if>		
	</td>
	<td width="8%">	
		<fmt:formatDate var="formattedProposedExportDate" value="${status.current.proposedExportDate}" pattern="${dateFormatPattern}"/>

		<c:choose>
			<c:when test="${(uAction == 'showPrintProForma' || uAction == 'printProForma') && status.current.proFormaRequired != null && status.current.proFormaRequired == 'Y'}">
				<input type="text" class="inputBox pointer" name="shipConfirmInputBean[${status.index}].proposedExportDate" id="proposedExportDate${status.index}"  
					value="${formattedProposedExportDate}" size="15" onClick="return getCalendar(document.getElementById('proposedExportDate${status.index}'));" 
					onchange="valueChanged('proposedExportDate${status.index}')"/>		
			</c:when>
			<c:otherwise>						
				${formattedProposedExportDate}
			</c:otherwise>	
		</c:choose>
	</td>
     </c:when>
     <c:otherwise>
         <td width="5%">${status.current.carrierCode}
		     <input name="shipConfirmInputBean[${status.index}].carrierCode" id="carrierCode${status.index}" type="hidden" value="${status.current.carrierCode}"/>
		   <c:if test="${ empty status.current.defaultTxCarrierCode}">
		       <c:forEach var="carrierInfoBean" items="${shipmentBean.carrierInfoBeanCollection}" varStatus="status1">
		        	<c:if test="${status.current.carrierCode == status1.current.carrierCode}">
							<c:out value="${status1.current.carrierName}"/> | <c:out value="${status1.current.carrierMethod}"/> | <c:out value="${status1.current.carrierCode}"/>		            
					</c:if>
		       </c:forEach>
		   </c:if>
		   <c:if test="${ !empty status.current.defaultTxCarrierCode}">
				<c:out value="${status.current.defaultTxCarrierName}"/> | <c:out value="${status.current.defaultTxCarrierMthd}"/> | <c:out value="${status.current.defaultTxCarrierCode}"/>		            
		   </c:if>
		 </td>
         <td width="5%">${status.current.trackingNumber}
             <input name="shipConfirmInputBean[${status.index}].trackingNumber" id="trackingNumber${status.index}" type="hidden" value="${status.current.trackingNumber}"/>
         </td>
         <td width="5%">
		   <c:if test="${ empty status.current.defaultTxCarrierCode}">
           <c:forEach var="carrierInfoBean" items="${shipmentBean.carrierInfoBeanCollection}" varStatus="status1">
  <c:set var="accname" value="${status1.current.companyId}"/>
  <c:if test="${accname == 'Radian'}">
  	<c:set var="accname" value="Haas TCM"/>
  </c:if>
		        	<c:if test="${status.current.carrierCode == status1.current.carrierCode}">
    						<c:out value="${accname}"/>
    				</c:if>
		   </c:forEach>
		   </c:if>
		   <c:if test="${ !empty status.current.defaultTxCarrierCode}">
  <c:set var="accname" value="${status.current.defaultTxCarrierComp}"/>
  <c:if test="${accname == 'Radian'}">
  	<c:set var="accname" value="Haas TCM"/>
  </c:if>
			<c:out value="${accname}"/>
			</c:if>
         </td>
         <td width="5%">
		   <c:if test="${ empty status.current.defaultTxCarrierCode}">
           <c:forEach var="carrierInfoBean" items="${shipmentBean.carrierInfoBeanCollection}" varStatus="status1">
		        	<c:if test="${status.current.carrierCode == status1.current.carrierCode}">
    						<c:out value="${status1.current.account}"/>
    				</c:if>
		   </c:forEach>
		   </c:if>
		   <c:if test="${ ! empty status.current.defaultTxCarrierCode}">
				<c:out value="${status.current.defaultTxCarrierAcct}"/>
		   </c:if>
         </td>
         <td width="5%">
         	<c:choose>
		     <c:when test="${status.current.inventoryGroup == 'St Louis Kilfrost'}" >
		       ${status.current.carrierTotalCharges}
		     </c:when>
		     <c:otherwise>
		      	&nbsp;
		     </c:otherwise>
		    </c:choose>
         </td>
         <td width="5%">	
			<c:if test="${status.current.incotermRequired != null && status.current.incotermRequired == 'Y'}">
			<input type="hidden" name="hmodeOfTransport${status.index}" id="hmodeOfTransport${status.index}" value="${status.current.modeOfTransport}" />
				<select name="shipConfirmInputBean[${status.index}].modeOfTransport" id="modeOfTransport${status.index}" style="display:none"></select>	
				<c:out value="${status.current.modeOfTransport}"/>
			</c:if>
		</td>
		<td width="5%">	
			<c:if test="${status.current.incotermRequired != null && status.current.incotermRequired == 'Y'}">
			<input type="hidden" name="hincoterm${status.index}" id="hincoterm${status.index}" value="${status.current.incoterm}" />
					<select name="shipConfirmInputBean[${status.index}].incoterm" id="incoterm${status.index}" style="display:none"></select>
					<c:forEach var="motKey" items="${motIncotermCollection}" varStatus="motStatus">
						<c:if test="${status.current.modeOfTransport == motKey.modeOfTransport }">
							<c:forEach var="incKey" items="${motKey.vvIncotermList}" varStatus="incStatus">
								<c:if test="${status.current.incoterm == incKey.incoterm }">
									<c:out value ="${incKey.incotermShortDesc }"/>							
								</c:if>
							</c:forEach>
						</c:if>
					</c:forEach>
			</c:if>
		</td>
		<td width="8%">	
			<input type = hidden name="shipConfirmInputBean[${status.index}].proposedExportDate" id=proposedExportDate${status.index}" value="${status.current.proposedExportDate}" />
			<fmt:formatDate pattern="${dateFormatPattern}" value="${status.current.proposedExportDate}"/>
		</td>
     </c:otherwise>
    </c:choose>
    
   </tr>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty shipmentBeanCollection}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>

    <c:if test="${showPrintInvoice == 'Y'}">
	 <script type="text/javascript">
	 <!--
	    $("printInvoiceSpan").style.display = "";
	 //-->
	 </script>
	</c:if>
	<c:if test="${showPrintInvoice == 'Y' && param.consignmentTransferCount > 1*0}">
	 <script type="text/javascript">
	 <!--
	    //$("containerLabelsSpan").style.display = "";
	 //-->
	 </script>
	</c:if>

  </div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
  <input name="rowCount" id="rowCount" type="hidden" value="${resultSize}"/>
  <input type="hidden" name="totallines" id="totallines" value="${dataCount}"/>
  <input type="hidden" name="branchPlant" id="branchPlant" value='<tcmis:jsReplace value="${param.branchPlant}"/>' />
  <input type="hidden" name="inventoryGroup" id="inventoryGroup" value='<tcmis:jsReplace value="${param.inventoryGroup}"/>'/>
  <input name="uAction" id="uAction" type="hidden" value='<tcmis:jsReplace value="${param.uAction}"/>'/>
  <input name="personnelId" id="personnelId" type="hidden" value="${personnelBean.personnelId}"/>
  <input name="confirmedShipments" id="confirmedShipments" type="hidden" value="${confirmedShipments}"/>
  <input name="cmsConfirmedShipments" id="cmsConfirmedShipments" type="hidden" value="${cmsConfirmedShipments}"/>  
  <input name="distributionCount" id="distributionCount" type="hidden" value='<tcmis:jsReplace value="${param.distributionCount}"/>'/> 
  <input name="consignmentTransferCount" id="consignmentTransferCount" type="hidden" value='<tcmis:jsReplace value="${param.consignmentTransferCount}"/>'/>    
  <input type="hidden" name="pageid"  id="pageid"  value='<tcmis:jsReplace value="${param.pageid}"/>'/>         
  <input name="selectedShipments" id="selectedShipments" type="hidden" value="${selectedShipments}"/>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>

