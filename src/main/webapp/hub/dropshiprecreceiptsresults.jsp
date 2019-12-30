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
<script type="text/javascript" src="/js/hub/receivedreceipts.js"></script>

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
<c:choose>
  <c:when test="${empty receivedReceipts}" >
   <fmt:message key="unconfirmedreceipts.title"/>
  </c:when>
  <c:otherwise>
  <fmt:message key="receivedreceipts.title"/>
  </c:otherwise>
</c:choose>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myResultOnload()">

<tcmis:form action="/showchemreceivedreceiptsresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<tcmis:inventoryGroupPermission indicator="true" userGroupId="ReceivingQC">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</tcmis:inventoryGroupPermission>

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

<c:if test="${receivedReceiptsCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty receivedReceiptsCollection}" >

 <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">

 <c:forEach var="receiptDescDropshipViewBean" items="${receivedReceiptsCollection}" varStatus="status">
  <c:if test="${status.index % 10 == 0}">
   <tr>
   <th width="4%"><fmt:message key="label.po"/><br/><fmt:message key="label.poline"/></th>
   <th width="5%"><fmt:message key="label.supplier"/></th>
   <th width="5%"><fmt:message key="label.item"/><br/>(<fmt:message key="label.packaging"/>)</th>
   <th width="12%"><fmt:message key="label.description"/></th>
   <th width="4%"><fmt:message key="receiving.label.qtyreceived"/></th>
   <th width="4%"><fmt:message key="label.receiptid"/></th>
   <th width="4%"><fmt:message key="label.lot"/><br/><fmt:message key="label.bin"/></th>
   <th width="4%"><fmt:message key="label.dates"/></th>
   </tr>
  </c:if>

  <c:if test="${status.index == 0}">
   <input type="hidden" name="hubNumber" id="hubNumber" value="<c:out value="${status.current.branchPlant}"/>">
  </c:if>

  <c:choose>
   <c:when test="${status.index % 2 == 0}" >
    <c:set var="colorClass" value=''/>
   </c:when>
   <c:otherwise>
    <c:set var="colorClass" value='alt'/>
   </c:otherwise>
  </c:choose>

  <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">

  <c:set var="manageKitsAsSingleUnit" value='${status.current.manageKitsAsSingleUnit}'/>

  <td width="5%"><c:out value="${status.current.radianPo}"/><br/><c:out value="${status.current.poLine}"/></td>
  <td width="7%"><c:out value="${status.current.supplierName}"/></td>
  <td width="5%"><c:out value="${status.current.itemId}"/><br/>(<c:out value="${status.current.packaging}"/>)</td>
  <td width="12%" class="optionTitleLeft"><c:out value="${status.current.lineDesc}"/></td>

  <fmt:formatDate var="formattedDom" value="${status.current.dateOfManufacture}" pattern="MM/dd/yyyy"/>
  <fmt:formatDate var="formattedDos" value="${status.current.dateOfShipment}" pattern="MM/dd/yyyy"/>
  <fmt:formatDate var="formattedExpirationDate" value="${status.current.expireDate}" pattern="MM/dd/yyyy"/>
  <c:if test="${formattedExpirationDate == '01/01/3000'}" >
    <c:set var="formattedExpirationDate" value='Indefinite'/>
  </c:if>

   <td width="5%"><c:out value="${status.current.quantityReceived}"/></td>
   <td width="5%"><c:out value="${status.current.receiptId}"/>
   <br/>
     <%--<c:choose>
     <c:when test="${(status.current.receiptDocumentAvailable == 'y' || status.current.receiptDocumentAvailable == 'Y')}" >--%>
      <img src="/images/buttons/document.gif" alt="<fmt:message key="pickingqc.viewaddreceipts"/>" title="<fmt:message key="pickingqc.viewaddreceipts"/>" onMouseOver="style.cursor='hand'" onclick="javascript:showReceiptDocuments('${status.current.receiptId}','${status.current.inventoryGroup}','${status.current.shipToCompanyId}','${status.current.shipToLocationId}')">
     <%--</c:when>
     <c:otherwise>
       <img src="/images/buttons/plus.gif" alt="<fmt:message key="pickingqc.addreceipts"/>" title="<fmt:message key="pickingqc.addreceipts"/>" onMouseOver="style.cursor='hand'" onclick="javascript:showReceiptDocuments('${status.current.receiptId}','${status.current.inventoryGroup}','${status.current.shipToCompanyId}','${status.current.shipToLocationId}')">
     </c:otherwise>
    </c:choose>--%>
   </td>
   <td width="8%"><c:out value="${status.current.mfgLot}"/><br/><c:out value="${status.current.bin}"/></td>
   <td width="5%" class="optionTitleLeft"><fmt:message key="receivedreceipts.label.dom"/>:<c:out value="${formattedDom}"/><br/>
    <fmt:message key="receivedreceipts.label.dor"/>:<c:out value="${status.current.dateOfReceipt}"/><br/>
    <fmt:message key="receivedreceipts.label.manufacturerdos"/>:<c:out value="${formattedDos}"/><br/>
    <fmt:message key="receivedreceipts.label.expdate"/>:<c:out value="${formattedExpirationDate}"/><br/>
   </td>

   </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   
   </table>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty receivedReceiptsCollection}" >

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
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
<input type="hidden" name="labelReceipts" id="labelReceipts" value="<c:out value="${labelReceipts}"/>">

<input type="hidden" name="selectedItem" id="selectedItem" value="">
<input type="hidden" name="selectedInventoryGroup" id="selectedInventoryGroup" value="">
<input type="hidden" name="selectedCatalogId" id="selectedCatalogId" value="">
<input type="hidden" name="selectedCatPartNo" id="selectedCatPartNo" value="">
<input type="hidden" name="selectedCatalogCompanyId" id="selectedCatalogCompanyId" value="">
<input type="hidden" name="userAction" id="userAction" value="">

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<%--<tcmis:saveRequestParameter/>--%>
<input name="paperSize" id="paperSize" type="hidden" value="${param.paperSize}">
<input name="justReceived" id="justReceived" type="hidden" value="${param.justReceived}">
<input name="sourceHub" id="sourceHub" type="hidden" value="${param.sourceHub}">
<input name="userAction" id="userAction" type="hidden" value="${param.userAction}">
<input name="receivedReceipts" id="receivedReceipts" type="hidden" value="${receivedReceipts}">
<input name="paperSizeRadio" id="paperSizeRadio" type="hidden" value="${param.paperSizeRadio}">
<input name="hubNumber" id="hubNumber" type="hidden" value="${param.hubNumber}">
<input name="inventoryGroup" id="inventoryGroup" type="hidden" value="${param.inventoryGroup}">

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>