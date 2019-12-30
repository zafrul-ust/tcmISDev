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
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/clientpages.css"></LINK>

<title><fmt:message key="missingpage.title"/></title>

</head>
<BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF" ALINK="" VLINK="#FFFF66">
<TABLE BORDER=0 WIDTH=100% >
<TR VALIGN="TOP">
<TD WIDTH="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</TD>
<TD ALIGN="right">
<img src="/images/tcmistcmis32.gif" border=0 align="right">
</TD>
</TR>
</Table>

<%@ include file="title.jsp" %>
<html:errors/>

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" CLASS="moveup">
  <tr>
    <TD WIDTH="5%" ALIGN="LEFT" CLASS="announce">
     <fmt:message key="common.index.message"/>
    </TD>
  </tr>
</TABLE>
<BR><BR>
<DIV ALIGN="center"><FONT FACE="Arial" SIZE="3"> <IMG SRC="/images/tcmintro.gif"></FONT></DIV>

</body>

</html:html>
