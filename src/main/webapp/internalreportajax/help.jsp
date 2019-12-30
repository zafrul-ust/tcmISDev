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
<title>
  <bean:message key="excel.title"/>
</title>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<LINK REL="SHORTCUT ICON" HREF="https://www.tcmis.com/images/buttons/tcmIS.ico"></LINK>
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/global.css"></LINK>
</head>
<body bgcolor="white">
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
</TD>
</TR>
</TABLE>
<table border="0"  CLASS="moveup">
  <tr>
    <td>
      <font face=Verdana size=2>
        This application allows you to run your own reports in "free form". Type in your SQL statement and comment and click "Get
Excel Report" and the application will create an excel report for you and display it in the browser.
     </font>
    </td>
  </tr>
  <tr>
    <td>
      <font face=Verdana size=2>
        <b>Some basic syntax:</b><br>
        <font face=Verdana size=2 color="blue"><i>select * from MY_TABLE</i></font><br>
        returns all columns and all data in the table MY_TABLE. <br>
        <font face=Verdana size=2 color="blue"><i>select MY_COLUMN1, MY_COLUMN2 from MY_TABLE where
MY_STRING_COLUMN1='foo'</i></font><br>
        returns the data in MY_COLUMN1 and MY_COLUMN2 where the data in MY_COLUMN1 is equal to "foo". <br>
        When specifying a date or string in your search criteria you must enclose them with single quote characters, e.g., <br>
        <font face=Verdana size=2 color="blue"><i>select * from MY_TABLE where MY_DATE_COLUMN='1-JAN-2004'</i></font><br>
        Remember that queries are case sensitive.<br>
        For additional help contact Haas Tcm.
      </font>
    </td>
  </tr>

</table>

</body>
</html:html>
