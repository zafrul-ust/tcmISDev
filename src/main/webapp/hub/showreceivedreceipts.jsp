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
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />

<%-- Add any other stylesheets you need for the page here --%>

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

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
<%@ include file="/common/rightclickmenudata.jsp" %>

<script type="text/javascript" src="/js/hub/showreceivedreceipts.js"></script>

<c:set var="itemId" value='${param.itemId}'/>
<c:set var="radianPo" value='${param.radianPo}'/>

<title>
<c:choose>
  <c:when test="${!empty itemId}" >
   <fmt:message key="receivinghistory.label.itemtitle"/>&nbsp;:&nbsp;<c:out value="${param.itemId}"/> in <c:out value="${param.inventoryGroup}"/>
  </c:when>
  <c:when test="${!empty radianPo}" >
   <fmt:message key="receivinghistory.label.potitle"/>&nbsp;:&nbsp;<c:out value="${param.radianPo}"/>-<c:out value="${param.poLine}"/> in <c:out value="${param.inventoryGroup}"/>
  </c:when>
</c:choose>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff">

<!-- Transit Page Begins -->
<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
 <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div class="interface" id="mainPage"> <!-- start of interface-->

<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="100%" class="headingl">
<c:choose>
  <c:when test="${!empty itemId}" >
   <fmt:message key="receivinghistory.label.itemtitle"/>&nbsp;:&nbsp;<c:out value="${param.itemId}"/> in <c:out value="${param.inventoryGroup}"/>
  </c:when>
  <c:when test="${!empty radianPo}" >
   <fmt:message key="receivinghistory.label.potitle"/>&nbsp;:&nbsp;<c:out value="${param.radianPo}"/>-<c:out value="${param.poLine}"/> in <c:out value="${param.inventoryGroup}"/>
  </c:when>
</c:choose>
</td>
</tr>
</table>
</div>
<!-- close of topNavigation-->

<div class="contentArea"> <!-- start of contentArea-->

<!-- Search Option Begins -->
<!-- Search Option Ends -->

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->

<c:if test="${receivingHistoryViewBeanCollection != null}" >

<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> 
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td class="alignLeft" width="5%"><fmt:message key="label.rctLegend"/>&nbsp;</td>
<!--  for black color
 -->
<td class="black" width="5%">&nbsp;</td>
<td class="alignLeft" width="100%">&nbsp;<fmt:message key="label.rctvtrRMA"/>
  | <a href="#" onclick="window.close()"><fmt:message key="label.close"/></a>
</td>
</tr>
</table>
    </div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">

    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

 <c:forEach var="receivingHistoryViewBean" items="${receivingHistoryViewBeanCollection}" varStatus="status">
  <c:if test="${status.index % 10 == 0}">
   <tr class="alignCenter">
   <th width="5%"><fmt:message key="label.poline"/></th>
   <th width="10%"><fmt:message key="label.supplier"/></th>
   <th width="5%"><fmt:message key="label.item"/></th>
   <th width="5%"><fmt:message key="label.bin"/></th>
   <th width="5%"><fmt:message key="label.receiptid"/></th>
   <c:choose>
    <c:when test="${!empty itemId}" >
     <th width="5%"><fmt:message key="label.mfglot"/></th>
    </c:when>
   <c:when test="${!empty radianPo}" >
     <th width="5%"><fmt:message key="receivinghistory.label.supplierref"/></th>
   </c:when>
   </c:choose>
   <th width="5%"><fmt:message key="label.qty"/></th>
   <th width="5%"><fmt:message key="receivedreceipts.label.dor"/></th>
   <th width="5%"><fmt:message key="receivinghistory.label.dateqced"/></th>
   <th width="12%"><fmt:message key="label.notes" /></th> 
   <th width="4%"><fmt:message key="receiving.label.deliveryticket" /></th>
   <!--  <th width="4%"><fmt:message key="label.qastatement" /></th>-->
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

  <c:set var="virtualRma" value='${status.current.virtualRma}'/>
  <c:if test="${critical == 'Y' || critical == 'y'}">
   <c:set var="colorClass" value='black'/>
  </c:if>

  <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>" name="rowId<c:out value="${status.index}"/>">
  <td width="5%"><c:out value="${status.current.radianPo}"/>-<c:out value="${status.current.poLine}"/></td>
  <td width="10%"><c:out value="${status.current.supplierName}"/></td>
  <td width="5%"><c:out value="${status.current.itemId}"/></td>
  <td width="5%"><c:out value="${status.current.bin}"/></td>
  <td width="5%"><c:out value="${status.current.receiptId}"/></td>
  <td width="5%"><c:out value="${status.current.mfgLot}"/></td>
  <td width="5%"><c:out value="${status.current.quantityReceived}"/></td>
  <fmt:formatDate var="formattedDateOfReceipt" value="${status.current.dateOfReceipt}" pattern="${dateFormatPattern}"/>
  <td width="5%" nowrap><c:out value="${formattedDateOfReceipt}"/></td>
  <fmt:formatDate var="formattedQcDate" value="${status.current.qcDate}" pattern="${dateFormatPattern}"/>
  <td width="5%" nowrap><c:out value="${formattedQcDate}"/></td>
   <td width="12%" class="alignLeft">${status.current.notes}</td>
  <td class="alignLeft">${status.current.deliveryTicket}</td>
  <!--  <td class="alignLeft">${status.current.qaStatement}</td>     -->
  </tr>

  <c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>

</table>
<!-- If the collection is empty say no data found -->
<c:if test="${empty receivingHistoryViewBeanCollection}" >
<table border="0" cellpadding="0" cellspacing="0" width="100%" class="tableNoData">
<tr>
<td width="100%">
<fmt:message key="main.nodatafound"/>
</td>
</tr>
</table>
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
   <input type="hidden" name="totallines" id="totallines" value="<c:out value="${dataCount}"/>">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

<%--</tcmis:form>--%>
</body>
</html:html>