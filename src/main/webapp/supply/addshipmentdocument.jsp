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

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<!-- For Calendar support -->
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>

<!-- Add any other javascript you need for the page here -->
<script src="/js/supply/shipmentdocument.js" language="JavaScript"></script>
<script type="text/javascript" src="/js/common/fileUpload/validatefileextension.js"></script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",validvalues:"<fmt:message key="label.validvalues"/>",
name:"<fmt:message key="label.name"/>",document:"<fmt:message key="label.document"/>",file:"<fmt:message key="label.file"/>",
filetypenotallowed:"<fmt:message key="label.filetypenotallowed"/>",
date:"<fmt:message key="label.date"/>",type:"<fmt:message key="label.type"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>





<title>
 <fmt:message key="mrdocument.add.label.title"><fmt:param><fmt:message key="label.shipment"/></fmt:param></fmt:message> <c:out value="${param.shipmentId}"/>
</title>
</head>
<body bgcolor="#ffffff" onunload="closeAllchildren();">

<c:set var="showdocument" value='${showdocument}'/>
<c:choose>
  <c:when test="${!empty showdocument}" >
   <body onLoad="doShowDocument()">
  </c:when>
  <c:otherwise>
   <body bgcolor="#ffffff">
  </c:otherwise>
</c:choose>

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
 <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>

<div class="interface" id="mainPage">

<div class="contentArea">
<tcmis:form action="/addshipmentdocument.do" onsubmit="return submitOnlyOnce();" enctype="multipart/form-data">
<input type="hidden" name="shipmentId" id="shipmentId" value="<c:out value="${param.shipmentId}"/>">
<input type="hidden" name="newDocumentUrl" id="newDocumentUrl" value="<c:out value="${newDocumentUrl}"/>">
<input type="hidden"  name="companyId" id="companyId" value="<c:out value="HAAS"/>">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">



<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

<tr><td width="100%" class="optionTitleBoldLeft">
<fmt:message key="mrdocument.add.label.title"><fmt:param><fmt:message key="label.shipment"/></fmt:param></fmt:message> <c:out value="${param.shipmentId}"/>
</td>
</tr>
</table>


<c:set var="errorMessage" value='${errorMessage}'/>
<c:if test="${!empty errorMessage}" >
<p color="red"><fmt:message key="receiptdocument.errormessage" /></p>
</c:if>

<c:choose>
<c:when test="${!empty showdocument}" >
	<br><br><fmt:message key="receiptdocument.successmessage"/>
<table>
<tr>
	<td width="5%">
	
	 <html:button property="containerlabels"  styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="doShowDocuments();">
	   <fmt:message key="label.ok"/>
	 </html:button>
	</td>
	</tr>
	</table>
</c:when>
<c:otherwise>


<table>
<%--<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.company"/>:
</td>

<td width="30%" class="optionTitleBoldLeft">
  <select name="companyId"  class="selectBox" id="companyId">
   <c:if test="${empty companyIdsCollection}" >
    <option value="Radian"><fmt:message key="label.haastcm"/></option>
   </c:if>
   <c:forEach var="companyBean" items="${companyIdsCollection}" varStatus="status">
    <option value="<c:out value="${status.current.companyId}"/>"><c:out value="${status.current.companyName}"/></option>
  </c:forEach>
 </select>
</td>
</tr>--%>
<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.docname"/>:
</td>
<td width="30%" class="optionTitleBoldLeft">
 <input name="documentName" id="documentName" type="text" class="inputBox" value="<c:out value="${documentName}"/>" size="30" maxlength="30">
</td>
</tr>

<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.docdate"/>:
</td>
<td width="30%" class="optionTitleBoldLeft">

 <input type="text" readonly name="documentDate" id="documentDate" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}" />" 
   onClick="return getCalendar(document.shipmentDocumentForm.documentDate,null,null,null,blockAfterExclude_documentDate,null);" size="8" maxlength="10" class="inputBox pointer">
<%-- <a href="javascript: void(0);" id="linkdocumentDate" onClick="return getCalendar(document.poDocumentForm.documentDate);">&diams;</a> --%>
</td>

</tr>

<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.file"/>:
</td>
<td width="30%" class="optionTitleBoldLeft">

 <html:file property="theFile" styleId="theFile" value="${requestScope.shipmentDocumentForm.theFile}" size="50" />

 <!--<input type="text" name="documentUrl" value="<c:out value="${documentUrl}"/>" size="30" maxlength="30" Class="HEADER">
 <html:button property="cancelButton" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick= "">
   <fmt:message key="button.browse"/>
 </html:button> -->
</td>
</tr>

<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.doctype"/>:
</td>

<td width="30%" class="optionTitleBoldLeft">

 <select name="documentType"   class="selectBox" id="documentType">
   <c:forEach var="bean" items="${vvShipmentDocTypeBeanColl}" varStatus="status">
    <option value="<c:out value="${bean.documentType}"/>" selected><c:out value="${bean.documentTypeDesc}"/></option>
  </c:forEach>
 </select>
</td>
</tr>

<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.confirmdelivery"/>:
</td>
<td width="30%" class="optionTitleBoldLeft">
 <input name="confirmDelivery" id="confirmDelivery" type="checkbox"  class="radioBtns" value="Y" >
</td>
</tr>

<tr>
<td width="5%" class="optionTitleBoldLeft">
 <html:submit property="submitSave" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return checkInput()">
   <fmt:message key="label.save"/>
 </html:submit>
</td>

<td width="5%" class="optionTitleBoldCenter">
 <html:button property="cancelButton" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "cancel()">
   <fmt:message key="label.cancel"/>
 </html:button>
</td>
</tr>
</table>
</c:otherwise>
</c:choose>
</div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>

</td></tr>
</table>
<!-- Search results end -->

<div id="hiddenElements" style="display: none;">
<input type="hidden" name="companyId" id="companyId" value="${param.companyId}"/>
<input type="hidden" name="blockBefore_documentDate" id="blockBefore_documentDate" value=""/>
<input type="hidden" name="blockAfter_documentDate" id="blockAfter_documentDate" value="<tcmis:getDateTag numberOfDaysFromToday="1" datePattern="${dateFormatPattern}"/>"/>
<input type="hidden" name="blockBeforeExclude_documentDate" id="blockBeforeExclude_documentDate" value=""/>
<input type="hidden" name="blockAfterExclude_documentDate" id="blockAfterExclude_documentDate" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>""/>
<input type="hidden" name="inDefinite_confirmationDate" id="inDefinite_confirmationDate" value=""/>
</div>


</tcmis:form>
</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->
</body>
</html:html>
