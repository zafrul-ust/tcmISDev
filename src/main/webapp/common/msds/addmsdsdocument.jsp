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
<script src="/js/common/msds/msdsdocument.js" language="JavaScript"></script>
<script src="/js/common/fileUpload/validatefileextension.js" language="JavaScript"></script>

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


<c:set var="pageTitle" value='${param.materialId}'/>
<c:if test="${!empty param.customerMixtureNumber}" >
	<c:set var="pageTitle" value='${param.customerMixtureNumber}'/>
</c:if>

<title>
 <fmt:message key="label.documentsfor"/> <c:out value="${pageTitle}"/>
</title>
</head>
<body bgcolor="#ffffff" onunload="closeAllchildren();">

<c:set var="showdocument" value='${showdocument}'/>

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br/><br/><br/><fmt:message key="label.pleasewait"/>
 <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>

<div class="interface" id="mainPage">

<div class="contentArea">
<tcmis:form action="/showaddmsdsdocument.do" onsubmit="return submitOnlyOnce();" enctype="multipart/form-data">
<input type="hidden" name="materialId" id="materialId" value="<tcmis:jsReplace value="${param.materialId}"/>"/>
<input type="hidden" name="newDocumentUrl" id="newDocumentUrl" value="<c:out value="${newDocumentUrl}"/>"/>
<input type="hidden"  name="companyId" id="companyId" value="<tcmis:jsReplace value="${param.companyId}"/>"/>
<input type="hidden"  name="calledFrom" id="calledFrom" value="<tcmis:jsReplace value="${param.calledFrom}"/>"/>
<input type="hidden"  name="mixtureRevisionDate" id="mixtureRevisionDate" value="<tcmis:jsReplace value="${param.mixtureRevisionDate}"/>"/>
<input type="hidden"  name="mixtureId" id="mixtureId" value="<tcmis:jsReplace value="${param.mixtureId}"/>"/>
<input type="hidden"  name="customerMixtureNumber" id="customerMixtureNumber" value="<tcmis:jsReplace value="${param.customerMixtureNumber}"/>"/>
<input type="hidden"  name="documentTypeSource" id="documentTypeSource" value="<tcmis:jsReplace value="${param.documentTypeSource}"/>"/>
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
<fmt:message key="label.documentsfor"/>: <c:out value="${pageTitle}"/>
</td>
</tr>
</table>


<c:set var="errorMessage" value='${errorMessage}'/>
<c:if test="${!empty errorMessage}" >
<p color="red"><fmt:message key="receiptdocument.errormessage" /></p>
</c:if>

<c:choose>
<c:when test="${!empty showdocument}" >
	<br/><br/><fmt:message key="receiptdocument.successmessage"/>
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

<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.docname"/>:
</td>
<td width="30%" class="optionTitleBoldLeft">
 <input name="documentName" id="documentName" type="text" class="inputBox" value="<c:out value="${documentName}"/>" size="30" maxlength="30"/>
</td>
</tr>

<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.docdate"/>:
</td>
<td width="30%" class="optionTitleBoldLeft">

 <input type="text" readonly name="documentDate" id="documentDate" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}" />" 
   onclick="return getCalendar(document.msdsDocumentForm.documentDate);" size="8" maxlength="10" class="inputBox pointer"/>
</td>

</tr>

<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.file"/>:
</td>
<td width="30%" class="optionTitleBoldLeft">
 <html:file property="theFile" styleId="theFile" value="${requestScope.msdsDocumentForm.theFile}" size="50" />
</td>
</tr>

<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.doctype"/>:
</td>

<td width="30%" class="optionTitleBoldLeft">

 <select name="documentType"   class="selectBox" id="documentType">
   <c:forEach var="vvMsdsDocumentTypeBean" items="${vvMsdsDocumentTypeBeanCollection}" varStatus="status">
    <option value="<c:out value="${status.current.documentType}"/>" selected><c:out value="${status.current.documentTypeDesc}"/></option>
  </c:forEach>
 </select>
</td>
</tr>

<tr>
<td width="5%" class="optionTitleBoldLeft">
 <html:submit property="submitSave" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return (validateFileExtension() && checkInput())">
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


</tcmis:form>
</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->
</body>
</html:html>
