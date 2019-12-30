<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>

<tcmis:gridFontSizeCss /> 
<%-- Add any other stylesheets you need for the page here --%>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>
<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<%--
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
--%>
<%-- For Calendar support --%>
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>

<%-- Add any other javascript you need for the page here --%>
<script type="text/javascript" src="/js/distribution/quickcustomerviewresults.js"></script>


<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>

<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_filter.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>



<title>
<fmt:message key="label.quickCustomerView"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
mrHistory:"<fmt:message key="mrHistory"/>",
priceQuoteHistory:"<fmt:message key="priceQuoteHistory"/>",
invoice:"<fmt:message key="label.invoice"/>",
contact:"<fmt:message key="label.contact"/>",
createexcel:"<fmt:message key="label.createexcel"/>",
open:"<fmt:message key="label.open"/>",
addline:"<fmt:message key="label.addline"/>",
mrlineallocation:"<fmt:message key="label.mrlineallocation"/>",
createnewquote:"<fmt:message key="label.createnewquote"/>",
createnewmr:"<fmt:message key="label.createnewmr"/>",
createnewquotefornewcustomer:"<fmt:message key="label.createnewquotefornewcustomer"/>",
createnewmrfornewcustomer:"<fmt:message key="label.createnewmrfornewcustomer"/>",
newcontact:"<fmt:message key="label.newcontact"/>",
blank:" | ",
customerdetails:"<fmt:message key="customerdetails.title"/>",
noChange:"<fmt:message key="error.nochange"/>"};


with(milonic=new menuname("mrHistoryMenu")){
   top="offset=2"
   style = contextWideStyle;
   margin=3
   aI("text=<fmt:message key="label.open"/>;url=javascript:openMrHistory();"); 
}

with(milonic=new menuname("quoteHistoryMenu")){
   top="offset=2"
   style = contextWideStyle;
   margin=3
   aI("text=<fmt:message key="label.open"/>;url=javascript:openQuoteHistory();"); 
}

with(milonic=new menuname("invoiceMenu")){
   top="offset=2"
   style = contextWideStyle;
   margin=3
   aI("text=<fmt:message key="label.printinvoice"/>;url=javascript:printInvoice();"); 
}

with(milonic=new menuname("contactMenu")){
   top="offset=2";
   style=submenuStyle;
   itemheight=17;
   aI("text=<fmt:message key="customerdetails.title"/>;url=javascript:showCustomerDetails();");
   aI("text=<fmt:message key="label.email"/>;url=javascript:emailContact();");  
}

with(milonic=new menuname("contactEmptyMenu")){
   top="offset=2";
   style=submenuStyle;
   itemheight=17;
   aI("text=<fmt:message key="customerdetails.title"/>;url=javascript:showCustomerDetails();");
}


drawMenus();

// -->
</script>

</head>

<body bgcolor="#ffffff" onload="loadLayoutWin3E('quickCustomerView');resultOnLoad();" onresize="setTimeout('resizeGrids()',220);" onunload="closeAllchildren();" >
<tcmis:form action="/quickcustomerviewresults.do" onsubmit="return submitOnlyOnce();">
 
 
 <div class="interface" id="mainPage">

<div class="contentArea">

<div id="resultFrameDiv1" style="margin: 0px 0px 0px 0px;">
<table id="resultsMaskTable1" width="300px;"  border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div id="mrBean" style="display:;width:300px;height:800px;"  ></div>

<script type="text/javascript">
<!--
<c:set var="mrDataCount" value='${0}'/>
<c:if test="${!empty mrHistoryColl}" >

var mrHistoryJsonMainData = {
        rows:[
<c:forEach var="bean" items="${mrHistoryColl}" varStatus="status">
		<c:if test="${status.index > 0}">,</c:if>

<fmt:formatDate var="releaseDate" value="${bean.releaseDate}" pattern="${dateFormatPattern}"/> 
<fmt:formatDate var="lastShipDate" value="${bean.lastShipDate}" pattern="${dateFormatPattern}"/>          
        /*The row ID needs to start with 1 per their design.*/
{
 <tcmis:jsReplace var="customerPoNumber" value="${bean.customerPoNumber}" />
 <tcmis:jsReplace var="customerName" value="${bean.customerName}"  processMultiLines="true"/>
<fmt:formatNumber var="unitPriceFinal" value="${bean.unitPrice}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>

<fmt:formatNumber var="margin" value="${bean.margin}"  pattern="${oneDigitformat}"></fmt:formatNumber>
<c:choose>
<c:when test="${!empty bean.margin}">
<c:set var="fmtMargin" value='${margin}%' />
</c:when>
<c:otherwise>
<c:set var="fmtMargin" value='' />
</c:otherwise>
</c:choose>

 id:${status.index +1},
          data:[
 '${releaseDate}',
 '<tcmis:jsReplace value="${bean.inventoryGroupName}" />',
 '${bean.currencyId} ${unitPriceFinal}',
 '${fmtMargin}',
 '${bean.quantity}',
 '${customerPoNumber}',
 '${bean.quantityShipped}',
 '${lastShipDate}',
 '${bean.prNumber}-${bean.lineItem}',
 '<tcmis:jsReplace value="${bean.catPartNo}" />', 
 '<tcmis:jsReplace value="${bean.customerPartNo}" />',
 '<tcmis:jsReplace value="${bean.partDescription}" processMultiLines="true" />',
 '<tcmis:jsReplace value="${bean.specList}" processMultiLines="true" />',
 '${bean.releaseDate.time}',
 '${bean.lastShipDate.time}',
 '${bean.companyId}',
 '${bean.prNumber}','${bean.lineItem}',
 '${bean.unitPrice}','${bean.salesOrder}','${bean.itemId}','${bean.opsEntityId}'
 ]}
 	<c:set var="mrDataCount" value='${mrDataCount+1}'/>
     </c:forEach>
    ]};
</c:if>

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
var mrHistoryConfig = [
{
  	columnId:"releaseDate",
  	columnName:'<fmt:message key="label.orderdate"/>',
  	hiddenSortingColumn:"hReleaseDate",
  	sorting:"int",
  	width:7
},
{
	 columnId:"inventoryGroupName",
	 columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
	 attachHeader:'#text_filter',
     width:12,
     tooltip:true
},
{
  	columnId:"displayUnitPrice",
  	columnName:'<fmt:message key="label.unitprice"/>',
  	align:"right",
  	hiddenSortingColumn:"expectedUnitCost",
  	sorting:"int",
  	width:6
},
{ columnId:"margin",
	  columnName:'<fmt:message key="label.margin"/>',
	  hiddenSortingColumn:"hmargin",
	  sorting:"int",
	  width:3
},
{
  	columnId:"quantity",
  	columnName:'<fmt:message key="label.qty"/>',
  	align:"right",
  	sorting:"int",
  	width:3
},
{
  	columnId:"customerPo",
  	columnName:'<fmt:message key="label.customerpo"/>',
  	attachHeader:'#text_filter',
  	width:5
},
{
  	columnId:"quantityShipped",
  	columnName:'<fmt:message key="label.qtyshipped"/>',
  	align:"right",
  	sorting:"int",
  	width:5
},
{
  	columnId:"lastDateShipped",
  	columnName:'<fmt:message key="label.lastshippeddate"/>',
  	hiddenSortingColumn:"hLastDateShipped",
  	sorting:"int",
  	width:7
},
{
	 columnId:"mrLine",
	 columnName:'<fmt:message key="label.mrline"/>',
	 attachHeader:'#text_filter',
     width:6
},
{ columnId:"catPartNo",
  columnName:'<fmt:message key="label.globalpart"/>',
  attachHeader:'#text_filter',
  width:6
},
{ columnId:"customerPartNo",
  columnName:'<fmt:message key="label.customerpart"/>',
  attachHeader:'#text_filter',
  width:6
},
{ 	columnId:"partDescription",
  	columnName:'<fmt:message key="label.desc"/>',
  	attachHeader:'#text_filter',
  	width:8,
  	tooltip:true
},
{ 	columnId:"specList",
  	columnName:'<fmt:message key="label.specification"/>',
  	attachHeader:'#text_filter',
  	width:8,
  	tooltip:true
},
{
	columnId:"hReleaseDate",
	sorting:"int"
},
{
	columnId:"hLastDateShipped",
	sorting:"int"
},
{
	columnId:"companyId"//,type:"ed"
},
{
  	columnId:"hiddenPrNumber"
},
{
  	columnId:"lineItem"
},
{ columnId:"expectedUnitCost",
  sorting:"int"
},
{ columnId:"salesOrder"
},
{ columnId:"itemId"
},
{ columnId:"opsEntityId"
}    
];
//-->  
</script>
 
<!-- If the collection is empty say no data found -->
<c:if test="${empty mrHistoryColl}">
    <table width="300px;" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
        <tr>
            <td width="100%">
                <fmt:message key="main.nodatafound"/>
            </td>
        </tr>
    </table>
</c:if>
</td></tr>
</table>
</div>


<div id="resultFrameDiv2" style="margin: 0px 0px 0px 0px;">
<table id="resultsMaskTable2" width="300px;"  border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div id="quoteHistoryBean" style="display:;width:300px;height:800px;"  ></div>

<script type="text/javascript">
<!--
<c:set var="quoteTotalDataCount" value='${0}'/>
<c:if test="${!empty quoteHistoryColl}" >

var quoteHistoryJsonMainData = new Array();
var quoteHistoryJsonMainData = {
rows:[
<c:forEach var="quoteHistoryBean" items="${quoteHistoryColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<fmt:formatDate var="fmtSubmittedDate" value="${status.current.submittedDate}" pattern="${dateFormatPattern}"/>
<c:set var="submittedDateSortable" value="${status.current.submittedDate.time}"/>

<tcmis:jsReplace var="partDescription" value="${status.current.partDescription}"  processMultiLines="true"/>
<tcmis:jsReplace var="customerName" value="${status.current.customerName}"  processMultiLines="true"/>

<fmt:formatNumber var="unitPriceFinal" value="${status.current.unitPrice}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>


/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:[
 'N',
 '${fmtSubmittedDate}','${submittedDateSortable}',
 '<tcmis:jsReplace value="${status.current.inventoryGroupName}" />',
 '${status.current.quantity}','${status.current.currencyId} ${unitPriceFinal}',
 '${status.current.prNumber}-${status.current.lineItem}','${status.current.prNumber}','${status.current.lineItem}',
 '${status.current.origSalesQuoteCount}',
 '<tcmis:jsReplace value="${status.current.catPartNo}" />',
 '<tcmis:jsReplace value="${status.current.customerPartNo}" />',
 '<tcmis:jsReplace value="${status.current.partDescription}" processMultiLines="true" />',
 '<tcmis:jsReplace value="${status.current.specList}" processMultiLines="true" />',
 '${status.current.unitPrice}',
 '${status.current.opsEntityId}','${status.current.itemId}'
 ]}
 <c:set var="quoteTotalDataCount" value='${quoteTotalDataCount+1}'/>
 </c:forEach>
]};
</c:if>

var quoteHistoryConfig = [
{
  columnId:"permission"
},
{ columnId:"submittedDate",
	  columnName:'<fmt:message key="label.quotedate"/>',
	  sorting:'int',
	  width:7,
	  hiddenSortingColumn:"hSubmittedDate"
},
{
 	columnId:"hSubmittedDate",
 	sorting:'int'
},
{
  columnId:"inventoryGroupName",
  columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
  attachHeader:'#text_filter',
  width:12,
  tooltip:true
},
{ columnId:"quantity",
  columnName:'<fmt:message key="label.qty"/>',
  align:"right",
  sorting:"int",
  width:3
},
{ columnId:"displayUnitCost",
  columnName:'<fmt:message key="label.quotedprice"/>',
  align:"right",
  hiddenSortingColumn:"expectedUnitCost",
  sorting:"int",
  width:7
},
{ columnId:"prNumber",
  columnName:'<fmt:message key="label.quote"/>',
  width:6,
  sorting:'int',
  hiddenSortingColumn:"hiddenPrNumber"
},
{
 columnId:"hiddenPrNumber",
 sorting:'int'
},
{
 columnId:"lineItem",
 sorting:'int'
},
{
 columnId:"origSales",
 columnName:'<fmt:message key="label.ordered"/>',
 align:'right',
 width:5	
},
{ columnId:"catPartNo",
  columnName:'<fmt:message key="label.globalpart"/>',
  attachHeader:'#text_filter',
  width:6
},
{ columnId:"customerPartNo",
  columnName:'<fmt:message key="label.customerpart"/>',
  attachHeader:'#text_filter',
  width:6
},
{ 	columnId:"partDescription",
  	columnName:'<fmt:message key="label.desc"/>',
  	attachHeader:'#text_filter',
  	width:8,
  	tooltip:true
},
{ columnId:"specList",
  columnName:'<fmt:message key="label.specification"/>',
  attachHeader:'#text_filter',
  width:8,
  tooltip:true
},
{ columnId:"expectedUnitCost",
  sorting:"int"
},  
 {
 columnId:"opsEntityId"
},
{
 columnId:"itemId"
}
];
//-->  
</script>
 
<!-- If the collection is empty say no data found -->
<c:if test="${empty quoteHistoryColl}">
    <table width="300px;" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
        <tr>
            <td width="100%">
                <fmt:message key="main.nodatafound"/>
            </td>
        </tr>
    </table>
</c:if>
</td></tr>
</table>
</div>


<div id="resultFrameDiv3" style="margin: 0px 0px 0px 0px;">

<table id="resultsMaskTable3" width="300px;" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div id="InvoiceSearchBean" style="display: ;width:300px;height:1200px;" ></div>	
<script type="text/javascript">
<!--
<c:set var="invoiceDataCount" value='${0}'/>
<c:if test="${!empty invoiceColl}" >
var invoiceJsonMainData = new Array();
var invoiceJsonMainData = {
rows:[
<c:forEach var="bean" items="${invoiceColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<fmt:formatDate var="dateConfirmed" value="${status.current.dateConfirmed}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="billingPrintDate" value="${status.current.billingPrintDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="shippingPrintDate" value="${status.current.shippingPrintDate}" pattern="${dateFormatPattern}"/>
/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},

 data:[
  '${bean.poNumber}',
  '${bean.invoice}',
  '${bean.customerInvoice}',
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
  '${bean.customerId}',
  '${bean.landedCost}']}

 <c:set var="invoiceDataCount" value='${invoiceDataCount+1}'/>
 </c:forEach>
]};
</c:if>

var invoiceConfig = [
	{columnId:"poNumber", columnName:'<fmt:message key="label.customerpo"/>', attachHeader:'#text_filter', width:5 },
	{columnId:"invoice", columnName:'<fmt:message key="label.invoice"/>', attachHeader:'#text_filter', width:5, submit: true },
	{columnId:"customerInvoice", columnName:'<fmt:message key="label.customerinvoice"/>', attachHeader:'#text_filter', width:5, submit: true },
	{columnId:"dateConfirmed", columnName:'<fmt:message key="label.shipconfirmdate"/>', sorting:'int', hiddenSortingColumn:"hDateConfirmed", width:7 },
	{columnId:"displayTotalGoods", columnName:'<fmt:message key="label.goodsvalue"/>', hiddenSortingColumn:'totalGoods', align:'right', sorting:'int', width:6 },
	{columnId:"displayTotal", columnName:'<fmt:message key="label.total"/>', hiddenSortingColumn:'total', align:'right', sorting:'int', width:6 },
	{columnId:"displayMargin", columnName:'<fmt:message key="label.materialmargin"/>', hiddenSortingColumn:'margin', align:'right', sorting:'int', width:4 },
	{columnId:"shippingPrintDate" },
	{columnId:"shippingPrintName" },
	{columnId:"billingPrintDate" },
	{columnId:"billingPrintName" },
	{columnId:"hDateConfirmed" },
	{columnId:"hShippingPrintDate" },
	{columnId:"hBillingPrintDate" },
	{columnId:"totalGoods" },
	{columnId:"total" },
	{columnId:"margin" },
	{columnId:"customerId" },
	{columnId:"landedCost" } 
];
//-->  
</script>
 
<!-- If the collection is empty say no data found -->
<c:if test="${empty invoiceColl}">
    <table width="300px;" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
        <tr>
            <td width="100%">
                <fmt:message key="main.nodatafound"/>
            </td>
        </tr>
    </table>
</c:if>
</td></tr>
</table>
</div>


<div id="resultFrameDiv4" style="margin: 0px 0px 0px 0px;">
<table id="resultsMaskTable4" width="300px;"  border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div id="contactBean" style="display:;width:300px;height:800px;"  ></div>

<script type="text/javascript">
<!--
<c:set var="contactDataCount" value='${0}'/>
<c:if test="${!empty contactColl}" >

var contactJsonMainData = {
        rows:[
<c:forEach var="bean" items="${contactColl}" varStatus="status">
	<c:if test="${status.index > 0}">,</c:if>
		
	<c:set var="other" value=""/>
	<c:if test="${bean.purchasing == 'Y'}">
		<c:set var="other">${other},<fmt:message key="label.purchasing"/></c:set>
	</c:if>
	<c:if test="${bean.accountsPayable == 'Y'}">
		<c:set var="other">${other},<fmt:message key="label.accountspayable"/></c:set>
	</c:if>
	<c:if test="${bean.receiving == 'Y'}">
		<c:set var="other">${other},<fmt:message key="label.receiving"/></c:set>
	</c:if>
	<c:if test="${bean.qualityAssurance == 'Y'}">
		<c:set var="other">${other},<fmt:message key="label.qualityassurance"/></c:set>
	</c:if>
	<c:if test="${bean.management == 'Y'}">
		<c:set var="other">${other},<fmt:message key="label.management"/></c:set>
	</c:if>
	<c:if test="${other != ''}">
		<c:set var="other">${fn:substringAfter(other,",")}</c:set>
	</c:if>
	
	<c:set var="conStatus" value="false"/>
	<c:if test="${bean.status eq 'ACTIVE'}"><c:set var="conStatus" value="true"/></c:if>
	
	<c:set var="defaultContact" value="false"/>
	<c:if test="${bean.defaultContact eq 'Y'}"><c:set var="defaultContact" value="true"/></c:if> 
	
	<c:if test="${!empty bean.email}"><c:set var="email"><a href="mailto:<tcmis:jsReplace value="${bean.email}"/>"><tcmis:jsReplace value="${bean.email}"/></a></c:set></c:if>  
	<c:if test="${empty bean.email}"><c:set var="email" value=" " /></c:if>  
{

 id:${status.index +1},
          data:[
  'N',
  '<tcmis:jsReplace value="${bean.lastName}"/>, <tcmis:jsReplace value="${bean.firstName}"/>',
  '<tcmis:jsReplace value="${bean.nickname}"/>',
  '<tcmis:jsReplace value="${bean.contactType}"/>',
  '${bean.phone}',
  '${bean.altPhone}',
  '${bean.fax}',
  '${email}','<tcmis:jsReplace value="${bean.email}"/>',
  '${other}',
  ${conStatus},${defaultContact},
  '${bean.contactPersonnelId}',
  '${bean.customerId}'
 ]}
 	<c:set var="contactDataCount" value='${contactDataCount+1}'/>
     </c:forEach>
    ]};
</c:if>

var contactConfig = [
  {
  	columnId:"permission"
  },       
  {
    columnId:"fullName",
    columnName:'<fmt:message key="label.name"/>',
    width:10,
    tooltip:"Y"
  },
  {
  	columnId:"nickName",
    columnName:'<fmt:message key="label.nickname"/>',
    width:5
  },
  {
  	columnId:"contactType",
    columnName:'<fmt:message key="label.jobfunction"/>',
    width:10,
    tooltip:"Y"
  },
  {
  	columnId:"phone",
    columnName:'<fmt:message key="label.phone"/>'
  },
  {  
    columnId:"altPhone",
    columnName:'<fmt:message key="label.mobile"/>'
  },
  {
    columnId:"fax",
    columnName:'<fmt:message key="label.fax"/>'
  },
  {
  	columnId:"emailDisplay",
    columnName:'<fmt:message key="label.email"/>',
  	width:10,
  	tooltip:"Y"
  },
  {
  	columnId:"email"
  },
  {
  	columnId:"otherJobFunctions",
  	columnName:'<fmt:message key="label.otherjobfunctions"/>',
  	width:10,
  	tooltip:"Y"
  },
  {
	columnId:"status",
	columnName:'<fmt:message key="label.active"/>',
	type:"hchstatus",
	align:'center',
	width:5	
  },
  {
  	columnId:"defaultContact",
	columnName:"<fmt:message key="label.defaultcontact"/>",
  	type:'hchstatus', 
    align:'center',
    width:8		
  },
  {
	columnId:"contactPersonnelId"
  },  
  { columnId:"customerId"
  }
  
];
//-->  
</script>
 
<!-- If the collection is empty say no data found -->
<c:if test="${empty contactColl}">
    <table width="300px;" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
        <tr>
            <td width="100%">
                <fmt:message key="main.nodatafound"/>
            </td>
        </tr>
    </table>
</c:if>
</td></tr>
</table>
</div>


<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
<div id="hiddenElements" >
 <input name="uAction" id="uAction" type="hidden" value="">
 <input name="personnelId" id="personnelId" value="${personnelId}" type="hidden"/>
 <input name="invoiceTotalLines" id="invoiceTotalLines" value="<c:out value="${invoiceDataCount}"/>" type="hidden"/>
 <input name="quoteTotalLines" id="quoteTotalLines" value="<c:out value="${quoteTotalDataCount}"/>" type="hidden"/>
 <input name="mrTotalLines" id="mrTotalLines" value="<c:out value="${mrDataCount}"/>" type="hidden"/>
 <input name="contactTotalLines" id="contactTotalLines" value="<c:out value="${contactDataCount}"/>" type="hidden"/>
 
 <input name="opsEntityId" id="opsEntityId" value="<c:out value="${input.opsEntityId}"/>" type="hidden"/>
 <input name="inventoryGroup" id="inventoryGroup" value="<c:out value="${input.inventoryGroup}"/>" type="hidden"/>
 <input name="itemId" id="itemId" value="<c:out value="${input.itemId}"/>" type="hidden"/>
 <input name="customerId" id="customerId" value="<c:out value="${input.customerId}"/>" type="hidden"/>
 <input name="searchKey" id="searchKey" value="INVENTORY GROUP" type="hidden"/>
 <input name="days" id="days" value="<c:out value="${input.days}"/>" type="hidden"/>
 
 <input name="oldPrNumber" id="oldPrNumber" value="" type="hidden"/>
 <input name="lineItem" id="lineItem" value="" type="hidden"/>
 <input name="fromMRorQuote" id="fromMRorQuote" value="" type="hidden"/>
</div>
<!-- Hidden element Ends -->

</div> <!-- close of contentArea -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>

