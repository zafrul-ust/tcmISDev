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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/iframeresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>

<%@ include file="/common/rightclickmenudata.jsp" %>

<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<!-- For Calendar support -->
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>
<script src="/js/catalog/qualitysummary.js" language="JavaScript"></script>



<title>
<fmt:message key="label.preferredsupplierpricing"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
all:"<fmt:message key="label.all"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"
};

windowCloseOnEsc = true;
/*
/// Larry Note, this works on all browsers.
myWindowCloseOnEsc = true;
function myKeyEventHandler(evt) {
   if (myWindowCloseOnEsc == true && evt.keyCode == 27) {
  	 if(top.close)
       top.close();
     else
       window.close();
   }
}

function myAddEvent(obj, evType, fn, useCapture) {
	// General function for adding an event listener
	if (obj.addEventListener) {
		obj.addEventListener(evType, fn, useCapture);
		return true;
	}
	else if (obj.attachEvent) {
		var r = obj.attachEvent("on" + evType, fn);
		return r;
	}
	else {
		alert(evType+" handler could not be attached");
	}
}

	var eee = (document.addEventListener) ? 'keyup' : 'keydown';
	myAddEvent(document,eee,myKeyEventHandler,false);
*/

var lastdbuyRowIdClass = null;
var lastdbuyRowId = null;

function dbuySelectRow(dbuyRowId) {
	if( lastdbuyRowId != dbuyRowId ) {
			try {
				// initial setting and preventing other part javascript error screw this up.
				document.getElementById("dbuyRowId"+lastdbuyRowId).className = lastdbuyRowIdClass;
			} catch( ex ){}
			selectedRow = document.getElementById("dbuyRowId"+dbuyRowId);
			tempClass = selectedRow.className;
	    	selectedRow.className = "selected"+selectedRow.className;
			lastdbuyRowId = dbuyRowId;
			lastdbuyRowIdClass = tempClass;
	}
}

function buyPageSelectRow() {
	
}

 var dbuyConfig = {
			divName:'dbuyViewBean', // the div id to contain the grid of the data.
			beanData:'dbuyData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
			beanGrid:'dbuyGrid',     // the grid's name, as in beanGrid.attachEvent...
			config:'dbuyConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
			rowSpan:false,			 // this page has rowSpan > 1 or not.
			submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
									// remember to call haasGrid.parentFormOnSubmit() before actual submit.
			onRowSelect:dbuySelectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
			onRightClick:null,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
		    singleClickEdit:false //This is for single click edidting. If it is set to true, txt column type will pop a txt editing box on sligne click. 
//			onBeforeSorting:_onBeforeSorting
		};

var buyorderConfig = {
					divName:'buyPageViewBean', // the div id to contain the grid of the data.
					beanData:'buyPageData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
					beanGrid:'buyPageGrid',     // the grid's name, as in beanGrid.attachEvent...
					config:'buyPageConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
					rowSpan:false,			 // this page has rowSpan > 1 or not.
					submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
											// remember to call haasGrid.parentFormOnSubmit() before actual submit.
					onRowSelect:buyPageSelectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
					onRightClick:null,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
				    singleClickEdit:false //This is for single click edidting. If it is set to true, txt column type will pop a txt editing box on sligne click. 
//					onBeforeSorting:_onBeforeSorting
				};

function myInit() {
// for future grid code.
//			initGridWithGlobal(dbuyConfig);
//			initGridWithGlobal(buyorderConfig);
//alert(	window.opener.name );
//alert(  window.opener.parent.name );

poNumber = '${poNumber}';
if( poNumber ) {
		    //loc = "/tcmIS/supply/purchorder?po=" + poNumber + "&Action=searchlike&subUserAction=po";
		    loc = "/tcmIS/supply/purchaseorder.do?po=" + poNumber + "&Action=searchlike";
	openerWin = null;
	try {
		openerWin = opener.parent.parent.openIFrame;
	} catch(ex ) {}
	if( openerWin ) 
		opener.parent.parent.openIFrame("purchaseOrder"+poNumber,loc,"<fmt:message key="label.purchaseorder"/> "+poNumber,"","N");
	else 
		openWinGeneric(loc,"showradianPo","800","600","yes","50","50");
	window.opener.parent.searchFrame.submitSearchForm();
	window.opener.parent.searchFrame.document.buyOrderForm.submit();
	window.close();
	return;
}


	window.name  = "BuyordersSupplierList";
	var orderQuantity = Number('${param.orderQuantity}');
	var count = $('dbuyDataCount').value;
	var select = 0;
	var breakUnitPrice = 0 ;
	for (i=0; i< count; i++) {
		if( $('supplier'+i).value == '${param.supplier}') {
			breakQuantity = $('breakQuantity'+i).value;
			if( !breakQuantity || breakQuantity <= orderQuantity ) {
				breakUnitPrice = Number( $('breakUnitPrice'+i).value );
				select = i ; 
				break;
			}
		}
	}
	currentUnitPrice = breakUnitPrice;
//	alert( "select:"+select);
	$('selectedRow'+select).checked = true;
	document.getElementById('totalQuantityLabel').innerHTML = ""+ orderQuantity;
	document.getElementById('unitPriceLabel').innerHTML = ""+ breakUnitPrice;
	document.getElementById('totalPurchasePriceLabel').innerHTML = ""+orderQuantity*breakUnitPrice;
	dbuySelectRow(select);
}

function changeSupplier(selectedSupplier, selectedSupplierName) {

	$('selectedSupplierForPo').value = selectedSupplier;
	$('poSupplierName').value = 		selectedSupplierName;
}


function updateSupplier(selectedRowId) {
	if(	$('selectedSupplierForPo').value != $('oldSupplier').value ) {
		window.opener.setSupplierId('${param.selectedRowId}',$('selectedSupplierForPo').value);
		window.close();
//		window.opener.setSupplierId(selectedRowId,$('selectedSupplierForPo').value);
	}
}



function createPo() {
//	buyorderDataCount = $('buyorderDataCount').value;
	allthesame = true;
	selectedSupplier = $('selectedSupplierForPo').value;
	if(	selectedSupplier != $('oldSupplier').value ) 
		allthesame = false;
	else {
		buyorderDataCount = $('buyorderDataCount').value;
		for(i=0;i<buyorderDataCount;i++) {
			if( selectedSupplier != $('buyOrdersInputBean['+i+'].poSupplierId').value ) {
				allthesame = false;
				break;
			}
		}
	}
	if(	!allthesame ) {
		var loc = '/tcmIS/supply/loadChangeSupplierReason.do?callback=loadChangeSupplierReasonCallback&selectedRowId=-1';
		openWinGeneric(loc,"selectreason","500","100","yes","100","100");
		return;
	}

	$('uAction').value = 'createPo';
	document.genericForm.submit();
}

function loadChangeSupplierReasonCallback(selectedRowId,reasonId) {
//  This callback only happened when create po
// 	selectedRowId is not used, update all the buy orders that have different supplier 
	selectedSupplier = $('selectedSupplierForPo').value;
	if(	selectedSupplier != $('oldSupplier').value ) 
		$("reasonId").value = reasonId;
	buyorderDataCount = $('buyorderDataCount').value;
	for(i=0;i<buyorderDataCount;i++) {
			if( selectedSupplier != $('buyOrdersInputBean['+i+'].poSupplierId').value ) {
				$('buyOrdersInputBean['+i+'].poSupplierId').value = selectedSupplier;
				$("reasonId"+i).value = reasonId;
			}
	}
	$('uAction').value = 'createPo';
	document.genericForm.submit();
}

function checkRowSelection() {
	
// get total quantity  
	var orderQuantity = Number('${param.orderQuantity}');
	buyorderDataCount = $('buyorderDataCount').value;
	total = Number('${param.orderQuantity}');
	for(i=0;i<buyorderDataCount;i++) {
	if( $('rowNumber'+i ).checked ) 
		total += Number( $('orderQuantity'+i ).value );
	}
	
// get best price	
	var count = $('dbuyDataCount').value;
	var select = 0;
	var price = 0 ;
	for (i=0; i< count; i++) {
		breakUnitPrice = Number( $('breakUnitPrice'+i).value );
		{
			breakQuantity = $('breakQuantity'+i).value;
			if( !breakQuantity || breakQuantity <= total ) {
				if( price ==0 || breakUnitPrice < price ) {
					select = i ;
					price = breakUnitPrice;
				}
			}
		}
	}

	$('selectedRow'+select).checked = true;
	changeSupplier($('supplier'+select).value,$('supplierName'+select).value);
	document.getElementById('totalQuantityLabel').innerHTML = ""+ total;
	document.getElementById('unitPriceLabel').innerHTML = ""+ price;
	document.getElementById('totalPurchasePriceLabel').innerHTML = ""+total*price;
	dbuySelectRow(select);	   
}


// -->
</script>

</head>

<body bgcolor="#ffffff" onload="myInit()"><%-- has IE JS bug onresize="myResize()"> --%>
<tcmis:form action="/buyordersupplierpricelist.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="" onmousedown="toggleContextMenu('contextMenu');">
<div id="searchFrameDiv" class="contentArea">

<div class="roundcont filterContainer" style="width:100%;">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
<c:if test="${empty requestScope.poNumber}" >			
	<span id="update"> <a href="#" onclick="updateSupplier();"><fmt:message key="label.updatesupplier"/></a> </span>
<div id="SupplierAddPaymentTermsBean" style="height:150px;width:99%;display: none;" ></div>			
		   <c:choose>
		   <c:when test="${empty dbuyViewBeanCollection}" >
			    <table width="95%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
			     <tr>
			      <td width="100%">
			       <fmt:message key="main.nodatafound"/>
			      </td>
			     </tr>
			    </table>
		   </c:when>
		   <c:otherwise>
			     <table width="95%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
		     
			     <tr>
				    <td width="5%" colspan="2" nowrap><b><fmt:message key="label.prnumber"/>: </b>${param.prNumber}&emsp;&emsp;&emsp;&emsp;&emsp;<b><fmt:message key="label.catalogitem"/>: </b>${param.itemId}&emsp;&emsp;&emsp;&emsp;&emsp;<b><fmt:message key="label.orderquantity"/>: </b>${param.orderQuantity}</td>
			     </tr>
			     <tr><td width="5%" nowrap><b><fmt:message key="label.description"/>:</b></td><td>&nbsp;${dbuyViewBeanCollection[0].itemDesc}</td></tr>
			     <tr><td width="5%" nowrap><b><fmt:message key="label.shipto"/>:</b></td><td>&nbsp;${param.shipToLocationId}</td></tr>
			     <tr><td width="5%" nowrap><b><fmt:message key="label.inventorygroup"/>:</b></td><td>&nbsp;${param.inventoryGroup}</td></tr>
			     <tr><td width="5%" colspan="2" align="center"><b><fmt:message key="supplierPriceList"/></b></td></tr> 
			    </table>
		    <c:set var="colorClass" value='alt'/>
		   </c:otherwise>
		  </c:choose>
			
			 <c:if test="${empty dbuyViewBeanCollection}" >
			   </c:if>
		  <c:if test="${!empty dbuyViewBeanCollection}" >	  
          <table width="95%" border="0" cellpadding="0" cellspacing="0" class="tableResults">

          <c:set var="colorClass" value=''/>
          <c:set var="dbuyDataCount" value='${0}'/>
          
		   <tr>
		   <th width="5%"><fmt:message key="label.ok"/></th>
		   <th width="5%"><fmt:message key="label.supplier"/></th>
		   <th width="5%"><fmt:message key="label.supplierpartnum"/></th>
		   <th width="5%"><fmt:message key="label.priority"/></th>
		   <th width="5%"><fmt:message key="label.fromquantity"/></th>
		   <th width="10%"><fmt:message key="label.unitprice"/></th>
		   <th width="5%"><fmt:message key="label.multipleOf"/></th>
		   <th width="10%"><fmt:message key="label.minorderquantity"/></th>
		   <th width="10%"><fmt:message key="label.minordervalue"/></th>
		   <th width="10%"><fmt:message key="label.supplypath"/></th>
		   <th width="5%"><fmt:message key="label.leadtimeindays"/></th>
		   <th width="5%"><fmt:message key="label.remainingShelfLife"/></th>
		   <th width="5%"><fmt:message key="label.priceLastUpdatedBy"/></th>
		   <th width="5%"><fmt:message key="label.priceLastUpdatedDate"/></th>
		   </tr>
		   
		   <c:forEach var="dbuybean" items="${dbuyViewBeanCollection}" varStatus="status">
		   <c:set var="dbuyDataCount" value='${dbuyDataCount+1}'/>
		   
		   <c:choose>
		   <c:when test="${status.index % 2 == 0}" >
		    <c:set var="colorClass" value=''/>
		   </c:when>
		   <c:otherwise>
		    <c:set var="colorClass" value='alt'/>
		   </c:otherwise>
		  </c:choose>
<tr align="center" class="<c:out value="${colorClass}"/>" id="dbuyRowId<c:out value="${status.index}"/>" onmouseup="dbuySelectRow('${status.index}')">
		    <td class="optionTitle"><input name="selectedRow" id="selectedRow<c:out value="${status.index}"/>" type="radio" class="radioBtns" value="<c:out value="${status.index}"/>" onclick="changeSupplier(<c:out value="${status.current.supplier}"/>,'<c:out value="${status.current.supplierName}"/>')"/></td>
		    <td width="10%"><c:out value="${dbuybean.supplierName}"/></td>
		    <td width="5%"><c:out value="${dbuybean.supplierPartNo}"/></td>
		    <td width="5%"><c:out value="${dbuybean.priority}"/></td>
		    <td width="10%"><c:out value="${dbuybean.breakQuantity}"/></td>
		    <td width="10%" nowrap><c:out value="${dbuybean.breakUnitPrice}"/> <c:out value="${dbuybean.currencyId}"/></td>
		    <td width="5%"><c:out value="${dbuybean.multipleOf}"/></td>
		    <td width="10%"><c:out value="${dbuybean.minBuyQuantity}"/></td>
		    <td width="10%"><c:out value="${dbuybean.minBuyValue}"/></td>
		    <td width="5%"><c:out value="${dbuybean.supplyPath}"/></td>
		    <td width="10%"><c:out value="${dbuybean.leadTimeDays}"/></td>
		    <td width="5%"><c:out value="${dbuybean.remainingShelfLifePercent}"/></td>
		    <%-- using break price updated columns. --%>
		    <td width="5%"><c:out value="${dbuybean.breakPriceUpdatedBy}"/></td>
		    <fmt:formatDate var="formattedpriceUpdatedDate" value="${dbuybean.breakPriceUpdatedDate}" pattern="${dateTimeFormatPattern}"/>
		    <td width="5%"><c:out value="${formattedpriceUpdatedDate}"/></td>
		   </tr>
			<input name="dbuyContractId<c:out value="${status.index}"/>" id="dbuyContractId<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.dbuyContractId}"/>"/>
		    <input name="dbuybean[<c:out value="${status.index}"/>].supplier" id="supplier<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.supplier}"/>"/>
		    <input name="dbuybean[<c:out value="${status.index}"/>].breakQuantity" id="breakQuantity<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.breakQuantity}"/>"/>
		    <input name="dbuybean[<c:out value="${status.index}"/>].breakUnitPrice" id="breakUnitPrice<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.breakUnitPrice}"/>"/>
		    <input name="dbuybean[<c:out value="${status.index}"/>].supplierName" id="supplierName<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.supplierName}"/>"/>
		    
		 </c:forEach>
		 <input name="dbuyDataCount" id="dbuyDataCount" type="hidden" value="${dbuyDataCount}"/>
	      
		   </table>
		   </c:if>
	</c:if>
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>


<%-- Spec div start --%>
<c:if test="${empty requestScope.poNumber}" >
<div class="roundcont filterContainer" style="width:100%;">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
	<div class="roundContent">
		     <table width="95%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
			     <tr><td width="5%" nowrap><a href="#" onclick="createPo(); return false;"><fmt:message key="label.createpo"/></a></td><td/></tr>
			     <tr><td/><td nowrap><b>&emsp;&emsp;&emsp;&emsp;<b><%--<fmt:message key="label.openbuyorders"/>: --%><fmt:message key="label.totalquantity"/>: <label id="totalQuantityLabel">${param.orderQuantity}</label>&emsp;<fmt:message key="label.unitprice"/>: <label id="unitPriceLabel"></label>&emsp;<fmt:message key="label.totalpurchaseprice"/>: <label id="totalPurchasePriceLabel"></label></b></td></tr>
		    </table>
		  <c:if test="${empty buyPageViewBeanCollection}" >	  
			    <table width="95%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
			     <tr>
			      <td width="100%">
			       <fmt:message key="main.nodatafound"/>
			      </td>
			     </tr>
			    </table>
		  </c:if>
   	      <c:set var="colorClass" value='alt'/>
          <c:set var="buyorderDataCount" value='${0}'/>
		     <input name="rowNumber" id="rowNumber" type="hidden" value="-1"/>
		     <input name="selectedSupplierForPo" id="selectedSupplierForPo" type="hidden" value="${param.supplier}"/>
		     <input name="oldSupplier" id="oldSupplier" type="hidden" value="${param.supplier}"/>
		     <input name="companyId" id="companyId" type="hidden" value="${param.companyId}"/>
		     <input name="inventoryGroup" id="inventoryGroup" type="hidden" value="${param.inventoryGroup}"/>
		     <input name="customerPoNumber" id="customerPoNumber" type="hidden" value="${param.customerPoNumber}"/>
		   	<input name="prNumber" id="prNumber" type="hidden" value="${param.prNumber}"/>
		   	<input name="orderQuantity" id="orderQuant" type="hidden" value="${param.orderQuantity}"/>
		   	<input name="needDate" id="needDate" type="hidden" value="${param.needDate}" />
		   	<input name="oldPoSupplierName" id="oldPoSupplierName" type="hidden" value="${param.oldPoSupplierName}" />
		   	<input name="poSupplierName" id="poSupplierName" type="hidden" value="${param.poSupplierName}" />
		   	<input name="buyerId" id="buyerId" type="hidden" value="${param.buyerId}" />
		   	<input name="oldBuyerId" id="oldBuyerId" type="hidden" value="${param.oldBuyerId}" />
		   	<input name="comments" id="comments" type="hidden" value="${param.comments}" />
		   	<input name="oldComments" id="oldComments" type="hidden" value="${param.oldComments}" />
		   	<input name="supplyPath" id="supplyPath" type="hidden" value="${param.supplyPath}" />
		   	<input name="reasonId" id="reasonId" type="hidden" value="${param.reasonId}"/>

		  <c:if test="${!empty buyPageViewBeanCollection}" >	  
          <table width="95%" border="0" cellpadding="0" cellspacing="0" class="tableResults">

          <c:set var="colorClass" value=''/>
<%-- para for ??customerPo preferredSupplier --%>          
		   <tr>
		   <th width="5%"><fmt:message key="label.ok"/></th>
		   <th width="5%"><fmt:message key="label.prnumber"/></th>
		   <th width="5%"><fmt:message key="label.partnumber"/></th>
		   <th width="5%"><fmt:message key="label.type"/></th>
		   <th width="10%"><fmt:message key="label.buyquantity"/></th>
		   <th width="5%"><fmt:message key="label.needed"/></th>
		   <th width="10%"><fmt:message key="label.preferredsupplier"/><br/>(<fmt:message key="label.selectsupplier"/>)</th>
		   <th width="10%"><fmt:message key="label.company"/></th>
<%-- 
		   <th width="10%"><fmt:message key="label.mrquantity"/></th>
--%>
		   <th width="5%"><fmt:message key="label.requestor"/></th>
		   <th width="5%"><fmt:message key="label.externallinenote"/></th>
		   <th width="5%"><fmt:message key="label.internallinenote"/></th>
		   <th width="5%" nowrap><fmt:message key="label.shiptonote"/></th>
		   </tr>
		   
		   <c:forEach var="buyorderbean" items="${buyPageViewBeanCollection}" varStatus="status">
		   <c:choose>
		   <c:when test="${status.index % 2 == 0}" >
		    <c:set var="colorClass" value=''/>
		   </c:when>
		   <c:otherwise>
		    <c:set var="colorClass" value='alt'/>
		   </c:otherwise>
		  </c:choose>
<tr align="center" class="<c:out value="${colorClass}"/>" ID="rowId<c:out value="${status.index}"/>">
		   <td width="2%">
	   		<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].rowNumber" id="rowNumber<c:out value="${status.index}"/>" type="checkbox" value="<c:out value="${status.index}"/>" onclick="checkRowSelection(<c:out value="${status.index}"/>);" />
			<fmt:formatDate var="formattedNeedDate" value="${status.current.needDate}" pattern="${dateFormatPattern}"/>
		   	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].needDate" id="needDate<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${formattedNeedDate}"/>" />
		   	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].prNumber" id="prNumber<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.prNumber}"/>"/>
		   	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].customerPoNumber" id="customerPoNumber<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.customerPoNumber}"/>" />
		    <input name="buyOrdersInputBean[<c:out value="${status.index}"/>].inventoryGroup" id="inventoryGroup<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.inventoryGroup}"/>" />
		    <input name="buyOrdersInputBean[<c:out value="${status.index}"/>].companyId" id="companyId<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.companyId}"/>" />
		   	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].orderQuantity" id="orderQuantity<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.orderQuantity}"/>"/>
		   	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].oldComments" id="oldComments<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.comments}"/>"/>
		   	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].comments" id="comments<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.comments}"/>"/>
		   	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].oldBuyerId" id="oldBuyerId<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.buyerId}"/>"/>
		   	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].buyerId" id="buyerId<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.buyerId}"/>"/>
		   	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].oldStatus" id="oldStatus<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.status}"/>"/>
		   	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].status" id="status<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.status}"/>"/>
		   	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].supplyPath" id="supplyPath<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.supplyPath}"/>"/>
		   	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].reasonId" id="reasonId<c:out value="${status.index}"/>" type="hidden" value="-1"/>
		   	
					<c:choose>
						<c:when test="${status.current.preferredSupplier != null}">
							<c:choose>
							<c:when test="${status.current.selectedSupplier == null || status.current.preferredSupplier eq status.current.selectedSupplier}">
								<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierId" id="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierId" value="${status.current.preferredSupplier}" type="hidden"/>
								<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierName" id="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierName" value="${status.current.preferredSupplierName}" type="hidden"/>
								<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].oldPoSupplierName" id="buyOrdersInputBean[<c:out value="${status.index}"/>].oldPoSupplierName" value="${status.current.preferredSupplierName}" type="hidden"/>
							</c:when>
							<c:otherwise>
								<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierId" id="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierId" value="${status.current.selectedSupplier}" type="hidden"/>
								<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierName" id="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierName" value="${status.current.selectedSupplierName}" type="hidden"/>
								<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].oldPoSupplierName" id="buyOrdersInputBean[<c:out value="${status.index}"/>].oldPoSupplierName" value="${status.current.selectedSupplierName}" type="hidden"/>
							</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
								<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierId" id="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierId" value="${status.current.selectedSupplier}" type="hidden"/>
								<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierName" id="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierName" value="${status.current.selectedSupplierName}" type="hidden"/>
								<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].oldPoSupplierName" id="buyOrdersInputBean[<c:out value="${status.index}"/>].oldPoSupplierName" value="${status.current.selectedSupplierName}" type="hidden"/>
						</c:otherwise>
					</c:choose>

		   	
		   </td>
		   <td width="2%">
		   	<c:out value="${status.current.prNumber}"/>
		   </td>
		   <td width="2%">
				<c:out value="${status.current.partId}"/>		   
		   </td>
		   <td width="2%">
				<c:out value="${status.current.itemType}"/>		   
		   </td>
		   <td width="2%">
			<c:out value="${status.current.orderQuantity}"/>		   
		   </td>
		   <td width="2%">
			<c:out value="${formattedNeedDate}"/>		   
		   </td>
		   <td width="2%">
<%-- para for ??customerPo preferredSupplier, lastSupplierName not used. --%>
								<c:out value="${status.current.preferredSupplierName}"/>
								<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierId" id="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierId" value="${status.current.preferredSupplier}" type="hidden"/>
		   </td>
		   <td width="5%">
		   		<c:out value="${status.current.companyId}"/>
		   </td>
<%-- 
		   <td width="5%">
		   		<c:out value="${status.current.mrQuantity}"/>
		   </td>
--%>		   
		   <td width="8%">
		   	<c:out value="${status.current.requestorFirstName}"/> <c:out value="${status.current.requestorLastName}"/><br><c:out value="${status.current.phone}"/><br><c:out value="${status.current.email}"/>
		   </td>
		   <td width="3%"><c:out value="${status.current.notes}"/></td>
		   <td width="3%"><c:out value="${status.current.lineInternalNote}"/></td>
		   <td width="3%"><c:out value="${status.current.shiptoNote}"/></td>
		   </tr>         
		 <c:set var="buyorderDataCount" value='${buyorderDataCount+1}'/>
		 </c:forEach>
	      
		   </table>
		   </c:if>
		  <input name="buyorderDataCount" id="buyorderDataCount" type="hidden" value="${buyorderDataCount}"/>
	 </div>
  	<div class="roundbottom">
	 <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
	</div>
</div>
</div>
</c:if>
<%-- Spec div end --%>


</table>
</div>
</div>
   <div style="display: none">
     <iframe id="noUse" name="noUse" src="/blank.html"></iframe>
   </div>
<input name="uAction" id="uAction" value='${param.uAction}' type="hidden"/>   
</tcmis:form>
</body>
</html:html>