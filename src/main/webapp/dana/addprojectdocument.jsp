<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<LINK REL="SHORTCUT ICON" HREF="https://www.tcmis.com/images/buttons/tcmIS.ico"></LINK>
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/global.css"></LINK>
<SCRIPT SRC="/js/common/projectdocument.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/calendar/newcalendar.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/calendar/AnchorPosition.js" LANGUAGE="JavaScript"></SCRIPT>
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
 <fmt:message key="projectdocument.add.label.title"/> <c:out value="${param.projectId}"/>
</title>
</HEAD>

<c:set var="showdocument" value='${showdocument}'/>
<c:choose>
  <c:when test="${!empty showdocument}" >
   <BODY onLoad="doShowDocument()">
  </c:when>
  <c:otherwise>
   <BODY>
  </c:otherwise>
</c:choose>

<DIV ID="TRANSITPAGE" STYLE="display: none;">
<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B><fmt:message key="label.pleasewait"/></B></FONT></center>
</DIV>

<DIV ID="MAINPAGE" STYLE="">
<html:form action="/addprojectdocument.do" onsubmit="return SubmitOnlyOnce();" enctype="multipart/form-data">
<INPUT TYPE="hidden" NAME="projectId" value="<c:out value="${param.projectId}"/>">
<INPUT TYPE="hidden" NAME="newDocumentUrl" value="<c:out value="${newDocumentUrl}"/>">

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" CLASS="moveup">
<TR><TD WIDTH="100%" ALIGN="LEFT" HEIGHT="22" CLASS="heading">
<B><fmt:message key="projectdocument.add.label.title"/> <c:out value="${param.projectId}"/></B>
</TD>
</TR>
</TABLE>
<BR>
<html:errors/>
<c:set var="errorMessage" value='${errorMessage}'/>
<c:if test="${!empty errorMessage}" >
<fmt:message key="projectdocument.errormessage"/>
</c:if>

<c:choose>
<c:when test="${!empty showdocument}" >
<BR><BR><fmt:message key="projectdocument.successmessage"/>
<INPUT TYPE="hidden" NAME="documentName" value="<c:out value="${documentName}"/>">
<INPUT TYPE="hidden" NAME="documentId" value="<c:out value="${documentId}"/>">

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="4" WIDTH="100%" CLASS="moveup">
<TR>
<TD WIDTH="5%" ALIGN="CENTER" CLASS="announce">
 <html:button property="closeWindow" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick= "cancel()">
   <fmt:message key="label.ok"/>
 </html:button>
</TD>
</TR>
</TABLE>
</c:when>
<c:otherwise>
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="2" WIDTH="100%" CLASS="moveup">

<%--<TR VALIGN="MIDDLE">
<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
 <B><fmt:message key="label.company"/>:</B>
</TD>
<TD WIDTH="30%" ALIGN="LEFT" CLASS="announce">
 <SELECT name="companyId" Class="HEADER">
   <c:forEach var="companyBean" items="${companyIdsCollection}" varStatus="status">
    <option value="<c:out value="${status.current.companyId}"/>" selected><c:out value="${status.current.companyName}"/></option>
  </c:forEach>
 </SELECT>
</TD>
</TR>
--%>

<TR VALIGN="MIDDLE">
<TD HEIGHT=45 WIDTH="5%" VALIGN="MIDDLE" ALIGN="RIGHT" CLASS="announce">
 <B><fmt:message key="label.docname"/>:</B>
</TD>
<TD WIDTH="30%" ALIGN="LEFT">
 <INPUT TYPE="text" name="documentName" value="<c:out value="${documentName}"/>" size="30" maxlength="30" Class="HEADER">
</TD>
</TR>

<%--
<TR VALIGN="MIDDLE">
<TD HEIGHT=45 WIDTH="5%" VALIGN="MIDDLE" ALIGN="RIGHT" CLASS="announce">
 <B><fmt:message key="projectdocument.label.docdate"/>:</B>
</TD>
<TD WIDTH="30%" ALIGN="LEFT">
 <INPUT TYPE="text" name="documentDate" ID="documentDate" value="<c:out value="${documentDate}"/>" size="8" maxlength="10" Class="HEADER">
 <FONT SIZE="4"><a href="javascript: void(0);" ID="linkdocumentDate" onClick="return getCalendar(document.projectDocumentForm.documentDate);">&diams;</a></FONT></TD>
</TD>
</TR>
--%>

<TR VALIGN="MIDDLE">
<TD HEIGHT=45 WIDTH="5%" VALIGN="MIDDLE" ALIGN="RIGHT" CLASS="announce">
 <B><fmt:message key="label.file"/>:</B>
</TD>
<TD WIDTH="30%" ALIGN="LEFT">
 <html:file property="theFile" value="${requestScope.projectDocumentForm.theFile}" size="50" styleClass="HEADER"/>

 <!--<INPUT TYPE="text" name="documentUrl" value="<c:out value="${documentUrl}"/>" size="30" maxlength="30" Class="HEADER">
 <html:button property="cancelButton" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick= "">
   <fmt:message key="button.browse"/>
 </html:button> -->
</TD>
</TR>

<%--
<TR VALIGN="MIDDLE">
<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.doctype"/>:</B>
</TD>

<TD WIDTH="30%" ALIGN="LEFT" CLASS="announce">
 <SELECT name="documentType" Class="HEADER">
   <c:forEach var="vvReceiptDocumentTypeBean" items="${vvReceiptDocumentTypeBeanCollection}" varStatus="status">
    <option value="<c:out value="${status.current.documentType}"/>" selected><c:out value="${status.current.documentTypeDesc}"/></option>
  </c:forEach>
 </SELECT>
</TD>
</TR>
--%>

<TR>
<TD WIDTH="5%" ALIGN="CENTER" CLASS="announce">
 <html:submit property="submitSave" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick= "return checkInput()">
   <fmt:message key="label.save"/>
 </html:submit>
</TD>

<TD WIDTH="5%" ALIGN="CENTER" CLASS="announce">
 <html:button property="cancelButton" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick= "cancel()">
   <fmt:message key="label.cancel"/>
 </html:button>
</TD>
</TR>

</TABLE>

</c:otherwise>
</c:choose>

</html:form>
</DIV>
</BODY>
</html:html>