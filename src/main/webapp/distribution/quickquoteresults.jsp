<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
<script type="text/javascript" src="/js/distribution/quickquoteresults.js"></script>


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


<script type="text/javascript" src="/js/dhtmlxcontainer.js"></script>
<title>
<fmt:message key="label.quickQuote"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
purchaseorder:"<fmt:message key="label.purchaseorder"/>",
mrHistory:"<fmt:message key="mrHistory"/>",
priceQuoteHistory:"<fmt:message key="priceQuoteHistory"/>",
pohistory:"<fmt:message key="label.pohistory"/>",
inventory:"<fmt:message key="label.inventory"/>",
createexcel:"<fmt:message key="label.createexcel"/>",
open:"<fmt:message key="label.open"/>",
addline:"<fmt:message key="label.addlineto"/>",
mrlineallocation:"<fmt:message key="label.mrlineallocation"/>",
createnewquote:"<fmt:message key="label.createnewquote"/>",
createnewmr:"<fmt:message key="label.createnewmr"/>",
createnewquotefornewcustomer:"<fmt:message key="label.createnewquotefornewcustomer"/>",
createnewmrfornewcustomer:"<fmt:message key="label.createnewmrfornewcustomer"/>",
blank:" | ",
editsourcinginfo:"<fmt:message key="label.editsourcinginfo"/>",
supplieritemnotes:"<fmt:message key="supplieritemnotes.title"/>",
itemnotes:"<fmt:message key="itemnotes.title"/>",
shippinginfo:"<fmt:message key="label.shipinfo"/>",
hideintercompany:"<fmt:message key="label.hideintercompany"/>",
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

with(milonic=new menuname("poHistoryMenu")){
   top="offset=2"
   style = contextWideStyle;
   margin=3
   aI("text=<fmt:message key="label.open"/>;url=javascript:openPoHistory();"); 
   aI("text=<fmt:message key="label.expeditenotes"/>;url=javascript:showExpediteNotes();"); 
   aI("text=<fmt:message key="pickingqc.viewaddreceipts"/>;url=javascript:showReceiptDocuments();");
}

with(milonic=new menuname("inventoryMenu")){
   top="offset=2"
   style = contextWideStyle;
   margin=3
   aI("text=<fmt:message key="label.showdetails"/>;url=javascript:showDetails();"); 
}

drawMenus();

// -->
</script>

</head>

<body bgcolor="#ffffff" onload="loadLayoutWin3E('quickItemView');resultOnLoad();" onresize="setTimeout('resizeGrids()',220);" onunload="closeAllchildren();" >
<tcmis:form action="/quickquoteresults.do" onsubmit="return submitOnlyOnce();">
 
 
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
 '${releaseDate}','${bean.releaseDate.time}',
 '<tcmis:jsReplace value="${bean.inventoryGroupName}" />',
 '${customerName}',
 '${bean.currencyId} ${unitPriceFinal}',
 '${fmtMargin}','${bean.quantity}',
 '${customerPoNumber}',
 '${bean.quantityShipped}',
 '${lastShipDate}',
 '${bean.prNumber}-${bean.lineItem}',
 '<tcmis:jsReplace value="${bean.catPartNo}" />',
 '<tcmis:jsReplace value="${bean.customerPartNo}" />',
 '<tcmis:jsReplace value="${bean.specList}" processMultiLines="true" />',
 '${bean.lastShipDate.time}',
 '${bean.companyId}',
 '${bean.prNumber}','${bean.lineItem}',
 '${bean.unitPrice}','${bean.salesOrder}','${bean.margin}','${bean.intercompanyCustomer}'
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
	columnId:"hReleaseDate",
	sorting:"int"
},
{
	 columnId:"inventoryGroupName",
	 columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
	 attachHeader:'#text_filter',
     width:12,
     tooltip:true
},
{
	columnId:"customerName",
	columnName:'<fmt:message key="label.customer"/>',
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
{ 	columnId:"margin",
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
  	width:7
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
{ 	columnId:"specList",
  	columnName:'<fmt:message key="label.specification"/>',
  	attachHeader:'#text_filter',
  	width:8,
  	tooltip:true
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
{ columnId:"hmargin", 
  sorting:"int"
},
{
  	columnId:"intercompanyCustomer"
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
 'N','${fmtSubmittedDate}','${customerName}','${status.current.quantity}',
 '${status.current.currencyId} ${unitPriceFinal}',
 '${status.current.prNumber}-${status.current.lineItem}','${status.current.prNumber}','${status.current.lineItem}',
 '${status.current.origSalesQuoteCount}',
 '<tcmis:jsReplace value="${status.current.catPartNo}" />',
 '<tcmis:jsReplace value="${status.current.customerPartNo}" />',
 '<tcmis:jsReplace value="${status.current.specList}" processMultiLines="true" />',
 '${status.current.unitPrice}','${submittedDateSortable}'
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
{ columnId:"customerName",
  columnName:'<fmt:message key="label.customer"/>',
  attachHeader:'#text_filter',
  width:12,
  tooltip:"Y"
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
	  width:6
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
	width:4	
},
{ columnId:"catPartNo",
  columnName:'<fmt:message key="label.globalpart"/>',
  attachHeader:'#text_filter',
  width:6
},
{ columnId:"catPartNo",
  columnName:'<fmt:message key="label.customerpart"/>',
  attachHeader:'#text_filter',
  width:6
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
 columnId:"hSubmittedDate",
 sorting:'int'
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
<div id="poHistoryBean" style="display: ;width:300px;height:1200px;" ></div>	
<script type="text/javascript">
<!--
<c:set var="poHistoryDataCount" value='${0}'/>
<c:if test="${!empty poHistoryColl}" >
var poHistoryJsonMainData = new Array();
var poHistoryJsonMainData = {
rows:[
<c:forEach var="poBean" items="${poHistoryColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<fmt:formatDate var="fmtDateConfirmed" value="${status.current.dateConfirmed}" pattern="${dateFormatPattern}"/>
<c:set var="dateConfirmedSortable" value="${status.current.dateConfirmed.time}"/>

<tcmis:jsReplace var="supplierName" value="${status.current.supplierName}"  processMultiLines="true"/>

<fmt:formatNumber var="unitPriceFinal" value="${status.current.unitPrice}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>
/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['${fmtDateConfirmed}',
 	   '${dateConfirmedSortable}',
       '${supplierName}',
       '${status.current.currencyId} ${unitPriceFinal}',
 	   '${status.current.quantity}',
 	   '${status.current.totalQuantityReceived}',
 	   '<fmt:formatDate value="${status.current.dateLastReceived}" pattern="${dateFormatPattern}"/>', 
 	   '${status.current.dateLastReceived.time}',
 	   '${status.current.unitPrice}', 
 	   '${status.current.radianPo} - ${status.current.poLine}',
 	   '${status.current.radianPo}','${status.current.poLine}','${status.current.inventoryGroup}'
 	   ]}

 <c:set var="poHistoryDataCount" value='${poHistoryDataCount+1}'/>
 </c:forEach>
]};
</c:if>

var poHistoryConfig = [
{ columnId:"dateConfirmed",
	  columnName:'<fmt:message key="label.dateconfirmed"/>',
	  sorting:"int",
	  width:7,
	  align:"left",
	  hiddenSortingColumn:"hPromisedDate"
},
{ columnId:"hPromisedDate", 
	  sorting:"int"
},
{ columnId:"supplierName",
  columnName:'<fmt:message key="label.supplier"/>',
  attachHeader:'#text_filter',
  width:12,
  tooltip:"Y"
},
{ columnId:"displayUnitPrice",
	  columnName:'<fmt:message key="label.unitprice"/>',
	  align:"right",
	  hiddenSortingColumn:"unitPrice",
	  sorting:"int",
	  width:7
},
{ columnId:"quantity",
  columnName:'<fmt:message key="label.qty"/>',
  align:"right",
  sorting:"int",
  width:3
},
{ columnId:"totalQuantityReceived",
  columnName:'<fmt:message key="label.qtyreceived"/>',
  align:"right",
  sorting:"int",
  width:5
},

{
  	columnId:"dateLastReceived",
  	columnName:'<fmt:message key="label.lastreceiveddate"/>',
  	hiddenSortingColumn:"hDateLastReceived",
  	sorting:"int",
  	width:7
},
{
	columnId:"hDateLastReceived",
	sorting:"int"
},

{ columnId:"unitPrice",
  sorting:"int"
},
{ columnId:"radianPo",
	  columnName:'<fmt:message key="label.po"/>',
	  attachHeader:'#text_filter',
	  width:8
},
{ columnId:"hRadianPo"
},
{ columnId:"poLine"
},
{ columnId:"inventoryGroup"
}
];
//-->  
</script>
 
<!-- If the collection is empty say no data found -->
<c:if test="${empty poHistoryColl}">
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
<div id="inventoryBean" style="display:;width:300px;height:800px;"  ></div>

<script type="text/javascript">
<!--
<c:set var="inventoryDataCount" value='${0}'/>
<c:if test="${!empty inventoryColl}" >

var inventoryJsonMainData = {
        rows:[
<c:forEach var="bean" items="${inventoryColl}" varStatus="status">
 <c:if test="${!status.first}">,</c:if>{
 <c:set var="consigned" value=''/>
 <c:if test="${bean.inventoryGroupType == 'CONSIGNMENT'}"><c:set var="consigned" value='Y'/></c:if>
 id:${status.count},
 data:[
  '${bean.qtyOnHand}',
  '${bean.currencyId} ${bean.unitCost}',
  '${bean.unitCost}',
  '${bean.purchaseCurrencyId} ${bean.purchaseUnitCost}',
  '<tcmis:jsReplace value="${bean.inventoryGroupName}" />',
  '<fmt:formatDate value="${bean.expireDate}" pattern="${dateFormatPattern}"/>',
  '${bean.expireDate.time}',
  '<tcmis:jsReplace value="${bean.specs}" />',
  '${bean.mfgLot}',
  '${consigned}','${bean.inventoryGroup}'
  ]} <c:set var="inventoryDataCount" value='${inventoryDataCount+1}'/>
 </c:forEach>
 ]
};
</c:if>

var inventoryConfig = [
{
	columnId:"qtyOnHand",
	columnName:'<fmt:message key="label.qtyonhand"/>',
    width:4,
    align:"right",
    sorting:"int",
    tooltip:true
},
{ 
  columnId:"displayCost",
  columnName:'<fmt:message key="label.cost"/>',
  align:"right",
  hiddenSortingColumn:"unitCost",
  sorting:"int",
  width:6
},
{ columnId:"unitCost",sorting:"int"},  
{ columnId:"purchaseCost", columnName:'<fmt:message key="label.purchasecost"/>', align:"right", sorting:"int", width:6},
{
	columnId:"inventoryGroupName",
	columnName:'<fmt:message key="label.inventorygroup"/>',
	attachHeader:'#text_filter',
    width:12,
    tooltip:true
},
{ 
	  columnId:"expireDate",
	  columnName:'<fmt:message key="label.expiredate"/>',
	  sorting:"int",
	  width:7,
	  align:"left",
	  hiddenSortingColumn:"hExpireDate"
},
{	  columnId:"hExpireDate", 
	  sorting:"int"
}, 
{
	  columnId:"specs",
	  columnName:'<fmt:message key="label.specs"/>',
	  width:8,
	  tooltip:true
}, 
{
  columnId:"mfgLot",
  columnName:'<fmt:message key="label.mfglot"/>',
  width:6
},

{
  	columnId:"consigned",
  	columnName:'<fmt:message key="label.consigned"/>',
  	width:6
},
{ 
	columnId:"inventoryGroup"
} 
];
//-->  
</script>
 
<!-- If the collection is empty say no data found -->
<c:if test="${empty inventoryColl}">
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
 <input name="poHistoryTotalLines" id="poHistoryTotalLines" value="<c:out value="${poHistoryDataCount}"/>" type="hidden"/>
 <input name="quoteTotalLines" id="quoteTotalLines" value="<c:out value="${quoteTotalDataCount}"/>" type="hidden"/>
 <input name="mrTotalLines" id="mrTotalLines" value="<c:out value="${mrDataCount}"/>" type="hidden"/>
 <input name="inventoryTotalLines" id="inventoryTotalLines" value="<c:out value="${inventoryDataCount}"/>" type="hidden"/>
 
 <input name="opsEntityId" id="opsEntityId" value="<c:out value="${input.opsEntityId}"/>" type="hidden"/>
 <input name="inventoryGroup" id="inventoryGroup" value="<c:out value="${input.inventoryGroup}"/>" type="hidden"/>
 <input name="itemId" id="itemId" value="<c:out value="${input.itemId}"/>" type="hidden"/>
 <input name="customerId" id="customerId" value="<c:out value="${input.customerId}"/>" type="hidden"/>
 <input name="searchKey" id="searchKey" value="INVENTORY GROUP" type="hidden"/>
 <input name="catalogCompanyId" id="catalogCompanyId" type="text" value="HAAS" />
 <input name="catalogId" id="catalogId" type="text" value="Global" />
 <input name="hideInterCompany" id="hideInterCompany" type="text" value="<c:out value="${input.hideInterCompany}"/>" />
    
 <input name="days" id="days"  value="<c:out value="${input.days}"/>" type="hidden"/>
 <input name="today" id="today" value="<c:out value="${input.today}"/>" type="hidden"/>
 
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

