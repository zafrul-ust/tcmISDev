<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/distribution/printinvoiceresults.js"></script>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<%-- 
<script type="text/javascript" src="/js/hub/shipconfirm.js"></script>
--%>

<title>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
noRowChecked:"<fmt:message key="error.norowselected"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
pleasemakeselection:"<fmt:message key="label.pleasemakeselection"/>",    
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
dateexpected:"<fmt:message key="label.dateexpected"/>",
reportname:"<fmt:message key="label.pleaseenterreportname"/>",
notified:"<fmt:message key="label.notified"/>",
mustbeanumberinthisfield:"<fmt:message key="label.mustbeanumberinthisfield"/>",
noteinvoice:"<fmt:message key="error.noteinvoice"/>",
einvoicenotsent:"<fmt:message key="error.einvoicenotsent"/>",
invalidDateFormat:"<fmt:message key="error.date.invalid"/>"};

with ( milonic=new menuname("rcMenu") ) {
	 top="offset=2";
	 style=submenuStyle;
	 itemheight=17;
	// style = contextStyle;
	// margin=3;
<c:set var="showActionLink" value='N'/>
<tcmis:opsEntityPermission indicator="true" userGroupId="InvoiceCorrection" opsEntityId="${param.opsEntityId}">
	<c:set var="showActionLink" value='Y'/>
</tcmis:opsEntityPermission>
<c:if test="${showActionLink eq 'Y' }">	
	 aI( "text=<fmt:message key="label.correctinvoice"/>;url=javascript:correctInvoice();" );
</c:if>
<c:if test="${showActionLink ne 'Y' }">	
	aI("text=<fmt:message key="label.correctinvoice"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
</c:if>
//	 aI( "text=<fmt:message key="label.linecharges"/> ;url=javascript:addLineCharges();" );
	 aI( "text=<fmt:message key="label.showeinvoicehistory"/> ;url=javascript:showEInvoiceHistory();" );
	}

drawMenus();


var gridConfig = {
	divName:'InvoiceSearchBean', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:false,    // the fields in grid aalre defaulted to be submitted or not.
	singleClickEdit:false,
	backgroundRender: true,
	onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:selectRow   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//	onBeforeSorting:_onBeforeSorting
};

// -->
</script>
</head>
<%--TODO - Singl click open remarks.--%>
<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);" onunload="closeAllchildren();">
<tcmis:form action="/printinvoiceresults.do" onsubmit="return submitFrameOnlyOnce();">

<c:set var="showUpdateLink" value='N'/>
<tcmis:opsEntityPermission indicator="true" userGroupId="ViewSalesOrders" opsEntityId="${param.opsEntityId}">
	<c:set var="showUpdateLink" value='Y'/>
</tcmis:opsEntityPermission>
<tcmis:opsEntityPermission indicator="true" userGroupId="InvoiceCorrection" opsEntityId="${param.opsEntityId}">
	<c:set var="showUpdateLink" value='Y'/>
</tcmis:opsEntityPermission>

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<c:set var="hasPermission" value='false'/>
<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty beanColl}" >
<div id="InvoiceSearchBean" style="width:100%;height:400px;"></div>
<!-- Search results start -->
<script type="text/javascript">
<!--
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
showUpdateLinks = false;
var shipmentId = new Array();
/*Storing the data to be displayed in a JSON object array.*/

<c:set var="p" value="Y"/>

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${beanColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<fmt:formatDate var="dateConfirmed" value="${status.current.dateConfirmed}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="billingPrintDate" value="${status.current.billingPrintDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="shippingPrintDate" value="${status.current.shippingPrintDate}" pattern="${dateFormatPattern}"/>
<%--
<tcmis:inventoryGroupPermission indicator="true" userGroupId="shipConfirm" inventoryGroup="${status.current.inventoryGroup}">
<c:set var="hasPermission" value='true'/>
<c:set var="p" value="Y"/>
</tcmis:inventoryGroupPermission>
--%>

{ id:${status.count}, 
 data:[
  '${p}',
  false,
  '${bean.customerId}',
  '<tcmis:jsReplace value="${bean.customerName}"/>',
  '${bean.poNumber}',
  '${bean.invoice}',
  '${bean.customerInvoice}',
  '${bean.prNumber}<c:if test="${!empty bean.lineItem}">-${bean.lineItem}</c:if>',
  '${dateConfirmed}',
  <c:choose>
   <c:when test="${empty bean.totalGoods}">
    '',
   </c:when>
   <c:otherwise>
    '${bean.currencyId} <fmt:formatNumber value="${bean.totalGoods}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>',
   </c:otherwise>
  </c:choose>
  <c:choose>
   <c:when test="${empty bean.total}">
    '',
   </c:when>
   <c:otherwise>
    '${bean.currencyId} <fmt:formatNumber value="${bean.total}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>',
   </c:otherwise>
  </c:choose>
  <c:choose>
   <c:when test="${empty bean.margin}">
    '',
   </c:when>
   <c:otherwise>
    '<fmt:formatNumber value="${bean.margin}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>%',
   </c:otherwise>
  </c:choose>
  '${shippingPrintDate}',
  '<tcmis:jsReplace value="${bean.shippingPrintName}"/>',
  '${billingPrintDate}',
  '<tcmis:jsReplace value="${bean.billingPrintName}"/>',
  '${bean.dateConfirmed.time}',
  '${bean.shippingPrintDate.time}',
  '${bean.billingPrintDate.time}',
  '${bean.totalGoods}',
  '${bean.total}',
  '${bean.margin}',
  '${bean.landedCost}',
  '${bean.autoEmailStatus}'
  ]
}

 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};

var config = [
	{columnId:"permission" },
	{columnId:"selected", columnName:'<fmt:message key="label.ok"/><br><input type="checkbox" value="" onClick="return checkAll();" name="checkAllSelected" id="checkAllSelected">', type:'hchstatus', width:2, submit: true},
	{columnId:"customerId", columnName:'<fmt:message key="label.customerid"/>', tooltip:"Y", width:8, submit: true },
	{columnId:"customerName", columnName:'<fmt:message key="label.customername"/>', tooltip:"Y", width:16 },
	{columnId:"poNumber", columnName:'<fmt:message key="label.customerpo"/>', width:7 },
	{columnId:"invoice", columnName:'<fmt:message key="label.invoice"/>', width:6, submit: true },
	{columnId:"customerInvoice", columnName:'<fmt:message key="label.customerinvoice"/>', width:6, submit: true },
	{columnId:"mrNumber", columnName:'<fmt:message key="label.mrnumber"/>', width:6 },
	{columnId:"dateConfirmed", columnName:'<fmt:message key="label.shipconfirmdate"/>', sorting:'int', hiddenSortingColumn:"hDateConfirmed", width:7 },
	{columnId:"displayTotalGoods", columnName:'<fmt:message key="label.goodsvalue"/>', hiddenSortingColumn:'totalGoods', align:'right', sorting:'int', width:8 },
	{columnId:"displayTotal", columnName:'<fmt:message key="label.total"/>', hiddenSortingColumn:'total', align:'right', sorting:'int', width:10 },
	{columnId:"displayMargin", columnName:'<fmt:message key="label.materialmargin"/>', hiddenSortingColumn:'margin', align:'right', sorting:'int', width:6 },
	{columnId:"shippingPrintDate", columnName:'<fmt:message key="label.processprintdate"/>', sorting:'int', hiddenSortingColumn:"hShippingPrintDate", width:7 },
	{columnId:"shippingPrintName", columnName:'<fmt:message key="label.processprintedby"/>', width:10 },
	{columnId:"billingPrintDate", columnName:'<fmt:message key="label.reprintdate"/>', sorting:'int', hiddenSortingColumn:"hBillingPrintDate", width:7 },
	{columnId:"billingPrintName", columnName:'<fmt:message key="label.reprintedby"/>', width:10 },
	{columnId:"hDateConfirmed" },
	{columnId:"hShippingPrintDate" },
	{columnId:"hBillingPrintDate" },
	{columnId:"totalGoods" },
	{columnId:"total" },
	{columnId:"margin" },
	{columnId:"landedCost" },
	{columnId:"autoEmailStatus", columnName:'<fmt:message key="label.einvoicestatus"/>', width:10 }
	];

//-->
</script>

</c:if>

<c:if test="${empty beanColl}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>

<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="uAction" id="uAction" value="" type="hidden"/>
<input name="hub" id="hub" type="hidden" value="${param.hub}"/>
<input name="branchPlant" id="branchPlant" type="hidden" value="${param.hub}"/>
<input name="inventoryGroup" id="inventoryGroup" type="hidden" value="${param.inventoryGroup}"/>
<input name="sortBy" id="sortBy" type="hidden" value="${param.sortBy}"/>
<input name="personnelId" id="personnelId" type="hidden" value="${personnelBean.personnelId}"/>
<input name="opsEntityId" id="opsEntityId" type="hidden" value="${param.opsEntityId}"/>
<input name="customerName" id="customerName" type="hidden" value="${param.customerName}"/>
<input name="customerId" id="customerId" type="hidden" value="${param.customerId}"/>
<input name="customerServiceRepId" id="customerServiceRepId" type="hidden" value="${param.customerServiceRepId}"/>
<input name="searchField" id="searchField" type="hidden" value="${param.searchField}"/>
<input name="searchMode" id="searchMode" type="hidden" value="${param.searchMode}"/>
<input name="searchArgument" id="searchArgument" type="hidden" value="${param.searchArgument}"/>
<input name="maxData" id="maxData" type="hidden" value="${param.maxData}"/>
<input name="showNotReprintedOnly" id="showNotReprintedOnly" type="hidden" value="${param.showNotReprintedOnly}"/>
<input name="showEInvoices" id="showEInvoices" type="hidden" value="${param.showEInvoices}"/>
<input name="showNeverPrinted" id="showNeverPrinted" type="hidden" value="${param.showNeverPrinted}"/>
<input name="showFailedEInvoices" id="showFailedEInvoices" type="hidden" value="${param.showFailedEInvoices}"/>
<input name="shipConfirmDate" id="shipConfirmDate" type="hidden" value="${param.shipConfirmDate}"/>
<input name="deliveredDate" id="deliveredDate" type="hidden" value="${param.deliveredDate}"/>
<input name="documentType" id="documentType" type="hidden" value="${param.documentType}"/>
<input name="currencyId" id="currencyId" type="hidden" value="${param.currencyId}"/>
</div>

</div>
</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
<c:if test="${param.maxData == fn:length(beanColl)}">
 <fmt:message key="label.maxdata">
  <fmt:param value="${param.maxData}"/>
 </fmt:message>
</c:if>
</div>


<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
 <c:choose>
 <c:when test="${empty tcmISErrors and empty tcmISError}">
  <c:choose>
   <c:when test="${param.maxData == fn:length(beanColl)}">
     showErrorMessage = true;
     parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
   </c:when>
   <c:otherwise>
     showErrorMessage = false;
   </c:otherwise>
  </c:choose>
 </c:when>
 <c:otherwise>
   parent.messagesData.errors = "<fmt:message key="label.errors"/>";
   showErrorMessage = true;
   </c:otherwise>
 </c:choose>

function closeTransitWin() {
	parent.closeTransitWin();
}
//-->
</script>

<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>

</body>
</html>