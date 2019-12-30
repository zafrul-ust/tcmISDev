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
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/receiptdocument.js"></script>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>


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
 <fmt:message key="receiptdocument.label.showdocuments"/> <c:out value="${param.receiptId}"/>
</title>
</head>

<body bgcolor="#ffffff" onunload="closeAllchildren();">
<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
 <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>

<div class="interface" id="mainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->


<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr><td width="100%">
<fmt:message key="receiptdocument.label.showdocuments"/> <c:out value="${param.receiptId}"/>
</td>
</tr>
</table>

<tcmis:form action="/showreceiptdocuments.do" onsubmit="return submitOnlyOnce();">
<input type="hidden" name="receiptId" id="receiptId" value="<c:out value="${param.receiptId}"/>"> 
<input type="hidden" name="documentId" id="documentId" value="<c:out value="${param.documentId}"/>"> 
<input type="hidden" name="inventoryGroup" id="inventoryGroup" value="<c:out value="${param.inventoryGroup}"/>">
<input type="hidden" name="secureDocViewer" id="secureDocViewer" value='${tcmis:isCompanyFeatureReleased(personnelBean,'SecureDocViewer','',personnelBean.companyId)}'/>
<input type="hidden" name="docCompanyId" id="docCompanyId" value="${personnelBean.companyId}"/>

<c:set var="inventoryGroupPermission" value=''/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="Receiving" inventoryGroup="${param.inventoryGroup}">
  <c:set var="inventoryGroupPermission" value='Yes'/>
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="Inventory" inventoryGroup="${param.inventoryGroup}">
  <c:set var="inventoryGroupPermission" value='Yes'/>
</tcmis:inventoryGroupPermission>

<tcmis:locationPermission indicator="true" userGroupId="DropshipReceiving" companyId="${param.companyId}" locationId="${param.shipToLocationId}">
   <c:set var="inventoryGroupPermission" value='Yes'/>
</tcmis:locationPermission>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
 <c:set var="colorClass" value=''/>
 <c:set var="dataCount" value='${0}'/>

 <c:forEach var="receiptDocumentViewBean" items="${receiptDocumentViewBeanCollection}" varStatus="status">
  <c:if test="${status.index % 10 == 0}">
   <tr align="center">
   <th width="2%" class="optionTitleBold"><fmt:message key="label.delete"/></th>
   <th width="5%" class="optionTitleBold"><fmt:message key="label.type"/></th>
   <th width="5%" class="optionTitleBold"><fmt:message key="label.company"/></th>
   <th width="15%" class="optionTitleBold"><fmt:message key="label.name"/></th>
   <th width="5%" class="optionTitleBold"><fmt:message key="label.date"/></th>
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

  <input type="hidden" name="receiptDocumentViewBean[<c:out value="${dataCount}"/>].documentUrl" id="documentUrl<c:out value="${dataCount}"/>" value="<c:out value="${status.current.documentUrl}"/>">
  <input type="hidden" name="receiptDocumentViewBean[<c:out value="${dataCount}"/>].documentId" id="documentId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.documentId}"/>">
  <input type="hidden" name="receiptDocumentViewBean[<c:out value="${dataCount}"/>].receiptId" id="receiptId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.receiptId}"/>">

  <tr align="center" class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
  <td width="2%">
   <c:choose>
   <c:when test="${inventoryGroupPermission == 'Yes'}" >
    <input type="checkbox" name="receiptDocumentViewBean[<c:out value="${dataCount}"/>].ok" id="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>">
   </c:when>
   <c:otherwise>
    &nbsp;
   </c:otherwise>
  </c:choose>
  </td>
  <td width="5%"><c:out value="${status.current.documentTypeDesc}"/></td>
  <td width="5%">
  <c:choose>
   <c:when test="${status.current.companyId == 'Radian'}">
    HAAS
   </c:when>
   <c:otherwise>
    <c:out value="${status.current.companyId}"/>
   </c:otherwise>
  </c:choose>
  </td>
  <td width="15%">
  <a href="#" onclick="openReceiptDoc(${status.current.receiptId},${status.current.documentId},'${status.current.documentUrl}')"><c:out value="${status.current.documentName}"/></a>
  <%--<a href="<c:out value="${status.current.documentUrl}"/>" target="receiptDocument<c:out value="${dataCount}"/>"><c:out value="${status.current.documentName}"/></a>--%>
  </td>
  <fmt:formatDate var="formattedDocumentDate" value="${status.current.documentDate}" pattern="${dateFormatPattern}"/>
  <td width="5%" nowrap><c:out value="${formattedDocumentDate}"/></td>
  </tr>

  <c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>
</table>

<input type="hidden" name="totallines" id="totallines" value="<c:out value="${dataCount}"/>">
<c:if test="${empty receiptDocumentViewBeanCollection}" >
<c:if test="${receiptDocumentViewBeanCollection != null}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
<td width="100%" class="optionTitle">
<fmt:message key="main.nodatafound"/>
</td>
</tr>
</table>
</c:if>
</c:if>
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
<%--<td width="5%" ALIGN="CENTER" CLASS="announce">
 <html:button property="closeWindow" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick= "cancel()">
   <fmt:message key="label.open"/>
 </html:button>
</td>--%>

<td width="5%">
<html:button property="closeWindow" styleId="closeWindow" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "cancel()">
   <fmt:message key="label.close"/>
 </html:button>
</td>
<c:if test="${inventoryGroupPermission == 'Yes'}">
<td width="5%">
 <html:submit property="updateDocuments" styleId="updateDocuments" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
   <fmt:message key="button.update"/>
 </html:submit>
</td>

<td width="5%">

<html:button property="addNewDocument" styleId="addNewDocument" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "addNewReceiptDocument()">
   <fmt:message key="receiptdocument.button.addnewdocument"/>
 </html:button>
</td>

</c:if>
</tr>
</table>

<!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->

</tcmis:form>

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->
</div>

</div> <!-- close of interface -->

</body>
</html:html>
