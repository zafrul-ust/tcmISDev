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
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<LINK REL="SHORTCUT ICON" HREF="https://www.tcmis.com/images/buttons/tcmIS.ico"></LINK>
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/global.css"></LINK>

<title><fmt:message key="miller.home"/></title>
<html:base/>
</head>
<body BGCOLOR="#FFFFFF" TEXT="#000000">


<TABLE BORDER=0 WIDTH=100% CLASS="moveupmore">
<TR VALIGN="TOP">
<TD WIDTH="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</TD>
<TD ALIGN="right">
<img src="/images/tcmistcmis32.gif" border=0 align="right">
</TD>
</TR>
</TABLE>
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" CLASS="moveup">
<TR>
<TD WIDTH="70%" ALIGN="LEFT" HEIGHT="22" CLASS="heading">
 <fmt:message key="miller.home"/>
</TD>
<TD WIDTH="30%" ALIGN="RIGHT" HEIGHT="22" CLASS="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="miller.link.home"/>
</html:link>
</TD>
</TR>
</TABLE>

<html:form action="/login.do">

<TABLE WIDTH="800" BORDER="0" CLASS="moveup">
<TR VALIGN="TOP">
<TD BGCOLOR="#E6E8FA" HEIGHT="400">
    <TABLE WIDTH="150" BORDER="0" CLASS="moveup">
    <TR>
      <TD>&nbsp;</TD>
    </TR>
    <TR>
      <TD>&nbsp;</TD>
    </TR>
    <TR><TD>&nbsp;</TD></TR>
    <TR><TD>&nbsp;</TD></TR>
    <TR><TD CLASS="bluel"></TD></TR>
    </TABLE>
</html:form>
</TD>

<TD WIDTH="650" HEIGHT="400" VALIGN="TOP">
<TABLE  BORDER="0" CELLSPACING=1 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<TR>
<TD WIDTH="550" CLASS="announce" COLSPAN="3">
<html:errors/><BR>
<c:set var="firstname" value='${sessionScope.personnelBean.firstName}'/>
<c:set var="lastname" value='${sessionScope.personnelBean.lastName}'/>
<c:set var="companyid" value='${sessionScope.personnelBean.companyId}'/>

<fmt:message key="miller.home.loggedinas"/>: <B><c:out value="${firstname}" />&nbsp;<c:out value="${lastname}" /></B>
<fmt:message key="miller.home.forcompany"/>
<B>
<c:choose>
  <c:when test="${companyid == 'Radian'}" >
    Haas TCM
  </c:when>
  <c:otherwise>
    <c:out value="${companyid}" />
  </c:otherwise>
</c:choose>
</B>
</TD>


</TABLE>
</TD>
</TR>
</TABLE>

</body>
</html:html>
