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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/resultiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/opsinvoiceinventorydetail.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="invoiceinventorydetail.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",pleaseselect:"<fmt:message key="label.pleaseselect"/>",
facilityid:"<fmt:message key="label.facilityid"/>",invoicedate:"<fmt:message key="label.invoicedate"/>",validvalues:"<fmt:message key="label.validvalues"/>",
all:"<fmt:message key="label.all"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setResultFrameSize();">

<tcmis:form action="/opsinvoiceinventorydetailresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<c:if test="${monthlyInventoryDetailViewBeanCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty monthlyInventoryDetailViewBeanCollection}" >

<table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
<c:set var="colorClass" value=''/>
<c:set var="biInventoryValueTotal"  value='${0}'/>
<c:set var="biHaasOwnedValueTotal"  value='${0}'/>
<c:set var="biConsignedValueTotal"  value='${0}'/>
<c:set var="biCustomerOwnedValueTotal"  value='${0}'/>
<c:set var="recInventoryValueTotal"  value='${0}'/>
<c:set var="recHaasOwnedValueTotal"  value='${0}'/>
<c:set var="recConsignedValueTotal"  value='${0}'/>
<c:set var="recCustomerOwnedValueTotal"  value='${0}'/>
<c:set var="eiInventoryValueTotal"  value='${0}'/>
<c:set var="eiHaasOwnedValueTotal"  value='${0}'/>
<c:set var="eiConsignedValueTotal"  value='${0}'/>
<c:set var="eiCustomerOwnedValueTotal"  value='${0}'/>
<c:set var="netUsageValueTotal"  value='${0}'/>
<c:set var="usageHaasOwnedValueTotal"  value='${0}'/>
<c:set var="usageConsignedValueTotal"  value='${0}'/>
<c:set var="usageCustomerOwnedValueTotal"  value='${0}'/>

<c:forEach var="monthlyInventoryDetailViewBean" items="${monthlyInventoryDetailViewBeanCollection}" varStatus="status">
<c:set var="dataCount" value='${dataCount+1}'/>
<c:if test="${status.index % 10 == 0}">
<tr>
<th rowspan="2" width="5%"><fmt:message key="label.item"/></th>
<th rowspan="2" width="5%"><fmt:message key="label.partnumber"/></th>
<th rowspan="2" width="5%"><fmt:message key="label.productname"/></th>
<th rowspan="2" width="5%"><fmt:message key="label.packaging"/></th>
<th rowspan="2" width="5%"><fmt:message key="label.countuom"/></th>
<c:choose>
  <c:when test="${param.unitsOrDollars == 'units'}" >
    <th width="4%"  height="15" colspan="4"><fmt:message key="label.startinventory"/></th>
    <th width="4%"  height="15" colspan="4"><fmt:message key="label.received"/></th>
    <th width="4%"  height="15" colspan="4"><fmt:message key="label.endinventory"/></th>
    <th width="4%"  height="15" colspan="4"><fmt:message key="label.usage"/></th>
 </c:when>
  <c:otherwise>
    <th rowspan="2" width="5%"><fmt:message key="label.currency"/></th>
    <th width="4%"  height="15" colspan="4"><fmt:message key="label.startinventoryvalue"/></th>
    <th width="4%"  height="15" colspan="4"><fmt:message key="label.receivedvalue"/></th>
    <th width="4%"  height="15" colspan="4"><fmt:message key="label.endinventoryvalue"/></th>
    <th width="4%"  height="15" colspan="4"><fmt:message key="label.usagevalue"/></th>
 </c:otherwise>
</c:choose>
</tr>

<tr align="center">
<th width="5%"><fmt:message key="label.haas"/></th>
<th width="5%"><fmt:message key="label.consigned"/></th>
<th width="5%"><fmt:message key="label.customer"/></th>
<th width="5%"><fmt:message key="label.total"/></th>

<th width="5%"><fmt:message key="label.haas"/></th>
<th width="5%"><fmt:message key="label.consigned"/></th>
<th width="5%"><fmt:message key="label.customer"/></th>
<th width="5%"><fmt:message key="label.total"/></th>

<th width="5%"><fmt:message key="label.haas"/></th>
<th width="5%"><fmt:message key="label.consigned"/></th>
<th width="5%"><fmt:message key="label.customer"/></th>
<th width="5%"><fmt:message key="label.total"/></th>

<th width="5%"><fmt:message key="label.haas"/></th>
<th width="5%"><fmt:message key="label.consigned"/></th>
<th width="5%"><fmt:message key="label.customer"/></th>
<th width="5%"><fmt:message key="label.total"/></th>
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

<tr class="${colorClass}">
  <td width="5%">${status.current.itemId}</td>
  <td width="5%">${status.current.catPartNo}</td>
  <td width="5%">${status.current.itemDesc}</td>
  <td width="5%">${status.current.packaging}</td>
  <td width="5%"><b>${status.current.countUom}</b></td>

<c:choose>
  <c:when test="${param.unitsOrDollars == 'units'}" >
  <td width="5%"><tcmis:emptyIfZero value="${status.current.biHaasOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${status.current.biHaasOwnedQuantity}" /></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.biConsignedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${status.current.biConsignedQuantity}" /></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.biCustomerOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${status.current.biCustomerOwnedQuantity}" /></tcmis:emptyIfZero></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${status.current.biInventory}" /></b></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.recHaasOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${status.current.recHaasOwnedQuantity}" /></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.recConsignedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${status.current.recConsignedQuantity}" /></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.recHaasOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${status.current.recCustomerOwnedQuantity}" /></tcmis:emptyIfZero></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${status.current.quantityReceived}" /></b></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.eiHaasOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${status.current.eiHaasOwnedQuantity}" /></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.eiConsignedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${status.current.eiConsignedQuantity}" /></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.eiCustomerOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${status.current.eiCustomerOwnedQuantity}" /></tcmis:emptyIfZero></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${status.current.eiInventory}" /></b></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.usageHaasOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${status.current.usageHaasOwnedQuantity}" /></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.usageConsignedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${status.current.usageConsignedQuantity}" /></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.usageCustomerOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${status.current.usageCustomerOwnedQuantity}" /></tcmis:emptyIfZero></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${status.current.netUsageQuantity}" /></b></td>
  </c:when>
  <c:otherwise>

<c:set var="biInventoryValue"  value='${status.current.biInventoryValue}'/>
<c:set var="biHaasOwnedValue"  value='${status.current.biHaasOwnedValue}'/>
<c:set var="biConsignedValue"  value='${status.current.biConsignedValue}'/>
<c:set var="biCustomerOwnedValue"  value='${status.current.biCustomerOwnedValue}'/>
<c:set var="recInventoryValue"  value='${status.current.recInventoryValue}'/>
<c:set var="recHaasOwnedValue"  value='${status.current.recHaasOwnedValue}'/>
<c:set var="recConsignedValue"  value='${status.current.recConsignedValue}'/>
<c:set var="recCustomerOwnedValue"  value='${status.current.recCustomerOwnedValue}'/>
<c:set var="eiInventoryValue"  value='${status.current.eiInventoryValue}'/>
<c:set var="eiHaasOwnedValue"  value='${status.current.eiHaasOwnedValue}'/>
<c:set var="eiConsignedValue"  value='${status.current.eiConsignedValue}'/>
<c:set var="eiCustomerOwnedValue"  value='${status.current.eiCustomerOwnedValue}'/>
<c:set var="netUsageValue"  value='${status.current.netUsageValue}'/>
<c:set var="usageHaasOwnedValue"  value='${status.current.usageHaasOwnedValue}'/>
<c:set var="usageConsignedValue"  value='${status.current.usageConsignedValue}'/>
<c:set var="usageCustomerOwnedValue"  value='${status.current.usageCustomerOwnedValue}'/>

  <td width="5%">${status.current.currencyId}</td>
  <td width="5%"><tcmis:emptyIfZero value="${biHaasOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${biHaasOwnedValue}" /></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${biConsignedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${biConsignedValue}" /></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${biCustomerOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${biCustomerOwnedValue}" /></tcmis:emptyIfZero></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${biInventoryValue}" /></b></td>
  <td width="5%"><tcmis:emptyIfZero value="${recHaasOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${recHaasOwnedValue}" /></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${recConsignedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${recConsignedValue}" /></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${recCustomerOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${recCustomerOwnedValue}" /></tcmis:emptyIfZero></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${recInventoryValue}" /></b></td>
  <td width="5%"><tcmis:emptyIfZero value="${eiHaasOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${eiHaasOwnedValue}" /></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${eiConsignedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${eiConsignedValue}" /></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${eiCustomerOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${eiCustomerOwnedValue}" /></tcmis:emptyIfZero></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${eiInventoryValue}" /></b></td>
  <td width="5%"><tcmis:emptyIfZero value="${usageHaasOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${usageHaasOwnedValue}" /></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${usageConsignedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${usageConsignedValue}" /></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${usageCustomerOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${usageCustomerOwnedValue}" /></tcmis:emptyIfZero></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${netUsageValue}" /></b></td>
  </c:otherwise>
</c:choose>
</tr>
</c:forEach>

<c:if test="${dataCount > 0}">
<c:choose>
  <c:when test="${param.unitsOrDollars == 'dollars'}" >
  <tcmis:monthlyInventoryDetailTotalsTag indicator="hubpage"/>
  </c:when>
</c:choose>
</c:if>
</table>
</c:if>
<!-- If the collection is empty say no data found -->
<c:if test="${empty monthlyInventoryDetailViewBeanCollection}" >

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>
<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden">

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<tcmis:saveRequestParameter/>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>