<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<c:set var="page" scope="request" value="${page}"/>
<html:html lang="true">
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<LINK REL="SHORTCUT ICON" HREF="https://www.tcmis.com/images/buttons/tcmIS.ico"></LINK>
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/global.css"></LINK>

<title><fmt:message key="login.title"/></title>
</head>

<BODY BGCOLOR="#FFFFFF" TEXT="#000000">



<TABLE BORDER=0 WIDTH='100%' CLASS="moveupmore">
<TR VALIGN="TOP">
<TD WIDTH="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</TD>
<TD ALIGN="right">
<img src="/images/tcmistcmis32.gif" border=0 align="right">
</TD>
</TR>
</Table>

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" CLASS="moveup">
<TR>
<TD WIDTH="70%" ALIGN="LEFT" HEIGHT="22" CLASS="heading">
<fmt:message key="login.header"/>
</TD>
<TD WIDTH="30%" ALIGN="RIGHT" HEIGHT="22" CLASS="headingr">
</TD>
</TR>
</TABLE>

<html:form action="/login.do">

<TABLE WIDTH="800" BORDER="0" CLASS="moveup">
<TR VALIGN="TOP"><TD BGCOLOR="#E6E8FA" HEIGHT="400">
<TABLE WIDTH="150" BORDER="0" CLASS="moveup">
<tr>
<td width="5%" CLASS="announce" ALIGN="RIGHT">
<B><fmt:message key="label.logonid"/>:</B>
</td>
<td width="95%" ALIGN="LEFT">
<html:text styleClass="HEADER" property="logonId" size="10" maxlength="15"/>
</td>
</tr>
<tr>
<td width="5%" CLASS="announce" ALIGN="RIGHT">
<B><fmt:message key="label.password"/>:</B>
</td>
<td width="95%" ALIGN="LEFT" >
<html:password styleClass="HEADER" property="password" size="10" maxlength="15"/>
</td>
</tr>

<tr>
<td width="5%" align="right">
<html:submit styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
     <fmt:message key="label.login"/>
</html:submit>
</td>
<td width="95%" align="left">
<html:reset styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
   <fmt:message key="label.reset"/>
</html:reset>
</td>
</tr>

<html:hidden property="client" value="SUPPLIER"/>
<html:hidden property="companyId" value=""/>
<html:hidden property="supplierHome" value="wbuyorderlist"/>
<c:set var="requestedPage" value='${requestScope.requestedPage}'/>
<c:if test="${empty requestedPage}" >
  <c:set var="requestedPage" value='wbuyorderlist'/>
</c:if>
<html:hidden property="requestedPage" value="${requestedPage}"/>
<html:hidden property="requestedURLWithParameters" value="${requestScope.requestedURLWithParameters}"/>

<TR><TD>&nbsp;</TD></TR>
<TR><TD>&nbsp;</TD></TR>
<TR><TD COLSPAN="2" CLASS="bluel"><html:errors/></TD></TR>
</TABLE>
</TD>
<TD WIDTH="650" HEIGHT="400" VALIGN="TOP">
<TABLE  BORDER="0" CELLSPACING=1 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<TR>
<TD WIDTH="550" COLSPAN="3">
<fmt:message key="main.loginmessage"/>
<TD>
</TR>
</TABLE>
</TD>
</TR></TABLE>
</html:form>

</body>
</html:html>
