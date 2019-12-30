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

<title><fmt:message key="label.haastcm"/></title>

</head>

<BODY BGCOLOR="#FFFFFF" TEXT="#000000">



<TABLE BORDER=0 WIDTH=100% CLASS="moveupmore">
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
<fmt:message key="label.haastcm"/>
</TD>
<TD WIDTH="30%" ALIGN="RIGHT" HEIGHT="22" CLASS="headingr">
        <html:link forward="logout">
          <bean:message key="label.logout"/>
        </html:link>
</TD>
</TR>
</TABLE>
<table border="0" CLASS="moveup">

  <tr>
    <td colspan="2">
      <font face=Verdana size=2>
        <bean:message key="passwordsuccess.label.message"/>
      </font>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <font face=Verdana size=2>
        <html:link forward="internalreporthome">
          <bean:message key="passwordsuccess.link.message"/>
        </html:link>
      </font>
    </td>
  </tr>
</table>
</body>
</html:html>
