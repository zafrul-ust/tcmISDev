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
<script src="/js/hub/nopicklistpicking.js" language="JavaScript"></script>

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
<fmt:message key="nopicklistpicking.title"/>
</title>

<script language="JavaScript" type="text/javascript">
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
                and:"<fmt:message key="label.and"/>",
     submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
         qtyInteger:"<fmt:message key="error.quantity.integer"/>",
           diffQty1:"<fmt:message key="pickingqc.confirm.diffpickqty1"/>",
           diffQty2:"<fmt:message key="pickingqc.confirm.diffpickqty2"/>",
           diffQty3:"<fmt:message key="pickingqc.confirm.diffpickqty3"/>"};
</script>
</head>

<body bgcolor="#ffffff" onload="myOnload()">

<tcmis:form action="/nopicklistpickingresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links. Set the variable you use in javascript to true.-->
<tcmis:permission indicator="true" userGroupId="Issuing" facilityId="">
 <script type="text/javascript">
  showUpdateLinks = true;
 </script>
</tcmis:permission>

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
 <c:if test="${!empty pickingErrorColl}">
  <c:forEach items="${pickingErrorColl}" var="pickingError">
     <c:out value="${pickingError}"/><br>
  </c:forEach>
</c:if>
</div>

<script type="text/javascript">
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty pickingErrorColl}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
</script>
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<c:if test="${pickColl != null}" >
<!-- Search results start -->
 <c:if test="${!empty pickColl}" >
    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:set var="colorClass" value=''/>
    <c:set var="legendColorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>
    <c:forEach var="pickBean" items="${pickColl}" varStatus="status">
    <c:if test="${status.index % 10 == 0}">
    <tr>
      <th width="5%"><fmt:message key="label.facility"/></th>
      <th width="5%"><fmt:message key="label.workarea"/></th>
      <th width="5%"><fmt:message key="label.deliverypoint"/></th>
      <th width="5%"><fmt:message key="label.requestor"/></th>
      <th width="5%"><fmt:message key="label.mrline"/></th>
      <%--<th width="5%"><fmt:message key="label.#labels"/></th>--%>
      <th width="2%"><fmt:message key="label.totalqty"/></th>
      <th width="2%"><fmt:message key="label.item"/></th>
      <th width="5%"><fmt:message key="label.csmnumber"/></th>
      <th width="9%"><fmt:message key="label.partdescription"/></th>
      <th width="5%"><fmt:message key="label.packaging"/></th>
      <th width="4%"><fmt:message key="label.bin"/></th>
      <th width="4%"><fmt:message key="label.receiptid"/></th>
      <th width="4%"><fmt:message key="label.mfglot"/></th>
      <th width="4%"><fmt:message key="label.expdate"/></th>
      <th width="2%"><fmt:message key="pickingqc.pickqty"/></th>
      <th width="5%"><fmt:message key="pickingqc.qtypicked"/></th>
    </tr>
    </c:if>
    <c:choose>
      <c:when test="${status.index % 2 == 0}">
        <c:set var="colorClass" value=''/>
      </c:when>
      <c:otherwise>
        <c:set var="colorClass" value="alt"/>
      </c:otherwise>
    </c:choose>
    <tr class='<c:out value="${colorClass}"/>' align="center">
      <c:set var="firstItemRow" value="${true}"/>
      <td width="5%" rowspan='<c:out value="${pickBean.itemRowspan}"/>'><c:out value="${pickBean.facilityId}"/>&nbsp;</td>
      <td width="5%" rowspan='<c:out value="${pickBean.itemRowspan}"/>'><c:out value="${pickBean.application}"/>&nbsp;</td>
      <td width="5%" rowspan='<c:out value="${pickBean.itemRowspan}"/>'><c:out value="${pickBean.deliveryPoint}"/>&nbsp;</td>
      <td width="5%" rowspan='<c:out value="${pickBean.itemRowspan}"/>'><c:out value="${pickBean.requestor}"/>&nbsp;</td>
      <td width="5%" rowspan='<c:out value="${pickBean.itemRowspan}"/>'><c:out value="${pickBean.mrLine}"/>&nbsp;</td>
      <%--<td width="5%" rowspan='<c:out value="${pickBean.itemRowspan}"/>'><input type="text" name='labelquantity<c:out value="${dataCount}"/>' id='labelquantity<c:out value="${dataCount}"/>' value='' class="inputBox" size=2 onchange=""></td>--%>
      <td width="2%" rowspan='<c:out value="${pickBean.itemRowspan}"/>'><c:out value="${pickBean.totalQty}"/>&nbsp;</td>
    <c:forEach var="itemBean" items="${pickBean.items}">
     <c:if test='${! firstItemRow}'>
   <tr class='<c:out value="${colorClass}"/>' align="center">
     </c:if>
      <td width="2%" rowspan='<c:out value="${itemBean.receiptRowspan}"/>'><c:out value="${itemBean.itemId}"/>&nbsp;</td>
      <td width="5%" rowspan='<c:out value="${itemBean.receiptRowspan}"/>'><c:out value="${itemBean.facPartNo}"/>&nbsp;</td>
      <td width="9%" rowspan='<c:out value="${itemBean.receiptRowspan}"/>'><c:out value="${itemBean.partDescription}"/>&nbsp;</td>
      <td width="5%" rowspan='<c:out value="${itemBean.receiptRowspan}"/>'><c:out value="${itemBean.packaging}"/>&nbsp;</td>
      <c:set var="firstReceiptRow" value="${true}"/>
    <c:forEach var="receiptBean" items="${itemBean.receipts}">
     <c:if test='${receiptBean.pickable != "Y" && receiptBean.pickable != "y"}'>
       <c:set var="colorClass" value='yellow'/>
     </c:if>
     <c:if test='${! firstReceiptRow}'>
   <tr class='<c:out value="${colorClass}"/>' align="center">
     </c:if>
      <td width="4%"><c:out value="${receiptBean.bin}"/>&nbsp;</td>
      <td width="4%">
        <c:out value="${receiptBean.receiptId}"/>&nbsp;<br>
      </td>
      <td width="4%"><c:out value="${receiptBean.mfgLot}"/>&nbsp;</td>
      <td width="4%">
        <fmt:formatDate var="fmtExpireDate" value="${receiptBean.expireDate}" pattern="MM/dd/yyyy"/>
        <c:choose>
          <c:when test='${fmtExpireDate=="01/01/3000"}'>
             <fmt:message key="label.indefinite"/>&nbsp;
          </c:when>
          <c:otherwise>
             <c:out value="${fmtExpireDate}"/>&nbsp;
          </c:otherwise>
        </c:choose>
      </td>
      <td width="2%"><c:out value="${receiptBean.pickQty}"/>&nbsp;</td>
      <td width="2%">
         <input type="text" name='picklistBean[<c:out value="${dataCount}"/>].quantityPicked' id='picklistBean[<c:out value="${dataCount}"/>].quantityPicked' value='<c:out value="${receiptBean.quantityPicked}"/>' class="inputBox" size=3 onchange="pickQtyChg(this,'<c:out value="${receiptBean.pickQty}"/>');">
      </td>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].companyId' id='picklistBean[<c:out value="${dataCount}"/>].companyId' value='<c:out value="${pickBean.companyId}"/>'>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].prNumber' id='picklistBean[<c:out value="${dataCount}"/>].prNumber' value='<c:out value="${pickBean.prNumber}"/>'>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].lineItem' id='picklistBean[<c:out value="${dataCount}"/>].lineItem' value='<c:out value="${pickBean.lineItem}"/>'>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].hub' id='picklistBean[<c:out value="${dataCount}"/>].hub' value='<c:out value="${pickBean.hub}"/>'>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].receiptId' id='picklistBean[<c:out value="${dataCount}"/>].receiptId' value='<c:out value="${receiptBean.receiptId}"/>'>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].itemId' id='picklistBean[<c:out value="${dataCount}"/>].itemId' value='<c:out value="${itemBean.itemId}"/>'>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].transferRequestId' id='picklistBean[<c:out value="${dataCount}"/>].transferRequestId' value='<c:out value="${receiptBean.transferRequestId}"/>'>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].consignedFlag' id='picklistBean[<c:out value="${dataCount}"/>].consignedFlag' value='<c:out value="${receiptBean.consignedFlag}"/>'>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].mrLine' id='picklistBean[<c:out value="${dataCount}"/>].mrLine' value='<c:out value="${pickBean.mrLine}"/>'>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].pickQty' id='picklistBean[<c:out value="${dataCount}"/>].pickQty' value='<c:out value="${receiptBean.pickQty}"/>'>
      <c:set var="dataCount" value='${dataCount+1}'/>
    </tr>
    <c:set var="firstReceiptRow" value="${false}"/>
    </c:forEach>
    <c:set var="firstItemRow" value="${false}"/>
    </c:forEach>
   </tr>
   </c:forEach>
   </table>
  </c:if>

   <!-- If the collection is empty say no data found -->
   <c:if test="${empty pickColl}" >
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
<input name="totalLines" id="totalLines" value='<c:out value="${dataCount}"/>' type="hidden">

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<input name="confirm" id="confirm" value='' type="hidden">

<input name="hub" id="hub" value='<c:out value="${param.hub}"/>' type="hidden">
<input name="inventoryGroup" id="inventoryGroup" value='<c:out value="${param.inventoryGroup}"/>' type="hidden">
<input name="facilityId" id="facilityId" value='<c:out value="${param.facilityId}"/>' type="hidden">
<input name="prNumber" id="prNumber" value='<c:out value="${param.prNumber}"/>' type="hidden">
<input name="sortBy" id="sortBy" value='<c:out value="${param.sortBy}"/>' type="hidden">

<c:set var="pickedMrCount" value='${0}'/>
<c:set var="batchNumber" value=''/>
<c:forEach var="pickBean" items="${pickedMrColl}" varStatus="status">
<c:set var="pickedMrCount" value='${pickedMrCount+1}'/>
<c:set var="batchNumber" value='${pickBean.batchNumber}'/>
<%--<input type="hidden" name='picklistBean[<c:out value="${pickedMrCount}"/>].prNumber' id='picklistBean[<c:out value="${pickedMrCount}"/>].prNumber' value='<c:out value="${pickBean.prNumber}"/>'>
<input type="hidden" name='picklistBean[<c:out value="${pickedMrCount}"/>].lineItem' id='picklistBean[<c:out value="${pickedMrCount}"/>].lineItem' value='<c:out value="${pickBean.lineItem}"/>'>
--%>
</c:forEach>
<input name="pickedMrCount" id="pickedMrCount" value='<c:out value="${pickedMrCount}"/>' type="hidden">
<input name="batchNumber" id="batchNumber" value='<c:out value="${batchNumber}"/>' type="hidden">
</div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>