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
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<%-- Add any other stylesheets you need for the page here --%>

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
<script type="text/javascript" src="/js/common/projectdocument.js"></script>
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


<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<%--<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--
<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>
--%>



<title>
 <fmt:message key="projectdocument.add.label.title"/> <c:out value="${param.projectId}"/>
</title>
</head>

<c:set var="showdocument" value='${showdocument}'/>
<c:choose>
  <c:when test="${!empty showdocument}" >
   <body onLoad="doShowDocument()">
  </c:when>
  <c:otherwise>
   <body>
  </c:otherwise>
</c:choose>

<!-- Transit Page Begins -->
<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<tcmis:form action="/addprojectdocument.do" onsubmit="return submitOnlyOnce();" enctype="multipart/form-data">
<input type="hidden" name="projectId" id="projectId"value="<c:out value="${param.projectId}"/>">
<input type="hidden" name="newDocumentUrl" id="newDocumentUrl" value="<c:out value="${newDocumentUrl}"/>">
<input type="hidden" name="companyId" id="companyId" value="<c:out value="${param.companyId}"/>">

<div class="interface" id="mainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
<fmt:message key="projectdocument.add.label.title"/> <c:out value="${param.projectId}"/>

<html:errors/>
<c:set var="errorMessage" value='${errorMessage}'/>
<c:if test="${!empty errorMessage}" >
<fmt:message key="projectdocument.errormessage"/>
</c:if>

<c:choose>
<c:when test="${!empty showdocument}" >
<br><br><fmt:message key="projectdocument.successmessage"/>
<input type="hidden" name="documentName" id="documentName" value="<c:out value="${documentName}"/>">
<input type="hidden" name="documentId" id="documentId" value="<c:out value="${documentId}"/>">
<input type="hidden" name="companyId" id="companyId" value="<c:out value="${param.companyId}"/>">

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>

<td width="5%" >

 <html:button property="closeWindow" styleId="closeWindow" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "cancel()">
   <fmt:message key="label.ok"/>
 </html:button>
</td>
</tr>
</table>
</c:when>
<c:otherwise>


<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">


<%--<TR VALIGN="MIDDLE">
<td width="5%" ALIGN="RIGHT" CLASS="announce">
 <fmt:message key="label.company"/>:
</td>
<td width="30%" ALIGN="LEFT" CLASS="announce">
 <c:set var="selectedCompanyId" value='${param.companyId}'/>
 <c:choose>
  <c:when test="${empty selectedCompanyId}" >
   <c:set var="selectedCompanyId" value='Radian'/>
  </c:when>
 </c:choose>

 <select name="companyId" onchange="companychanged()" Class="HEADER">
 <c:forEach var="facilitiesForCompanyObjBean" items="${facilitiesForCompanyObjBeanCollection}" varStatus="status">
 <c:set var="currentCompanyId" value='${status.current.companyId}'/>

  <c:choose>
   <c:when test="${currentCompanyId == selectedCompanyId}">
    <option value="<c:out value="${currentCompanyId}"/>" selected><c:out value="${status.current.companyName}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentCompanyId}"/>"><c:out value="${status.current.companyName}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
 </select>
 </td>
</tr>
--%>

<tr>
<td width="5%" class="optionTitleBoldRight">
 <fmt:message key="label.docname"/>:
</td>
<td width="30%">
 <input type="text" class="inputBox" name="documentName" id="documentName" value="<c:out value="${documentName}"/>" size="30" maxlength="29">
</td>
</tr>

<%--
<TR VALIGN="MIDDLE">
<td HEIGHT=45 width="5%" VALIGN="MIDDLE" ALIGN="RIGHT" CLASS="announce">
 <fmt:message key="projectdocument.label.docdate"/>:
</td>
<td width="30%" ALIGN="LEFT">
 <input type="text" name="documentDate" ID="documentDate" value="<c:out value="${documentDate}"/>" size="8" maxlength="10" Class="HEADER">
 <FONT SIZE="4"><a href="javascript: void(0);" id="linkdocumentDate" onClick="return getCalendar(document.projectDocumentForm.documentDate);">&diams;</a></FONT></td>
</td>
</tr>
--%>

<tr>
<td width="5%" class="optionTitleBoldRight">
 <fmt:message key="label.file"/>:
</td>
<td width="30%" class="optionTitleBoldLeft">
 <html:file property="theFile" styleId="theFile" value="${requestScope.projectDocumentForm.theFile}" size="50" />

 <!--<input type="text" name="documentUrl" value="<c:out value="${documentUrl}"/>" size="30" maxlength="30" Class="HEADER">
 <html:button property="cancelButton" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick= "">
   <fmt:message key="button.browse"/>
 </html:button> -->
</td>
</tr>

<%--
<TR VALIGN="MidDLE">
<td width="5%" ALIGN="RIGHT" CLASS="announce">
<fmt:message key="label.doctype"/>:
</td>

<td width="30%" ALIGN="LEFT" CLASS="announce">
 <SELECT name="documentType" Class="HEADER">
   <c:forEach var="vvReceiptDocumentTypeBean" items="${vvReceiptDocumentTypeBeanCollection}" varStatus="status">
    <option value="<c:out value="${status.current.documentType}"/>" selected><c:out value="${status.current.documentTypeDesc}"/></option>
  </c:forEach>
 </SELECT>
</td>
</tr>
--%>

<tr>
  <td width="5%" class="optionTitleBoldRight">
    <input type="checkbox" name="customerDisplay" id="customerDisplay" class="radioBtns" value="N">
    </td>
    <td width="30%" class="optionTitleBoldLeft">
      <fmt:message key="projectdocument.label.customerDisplay"/>
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

</c:otherwise>
</c:choose>


</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>
