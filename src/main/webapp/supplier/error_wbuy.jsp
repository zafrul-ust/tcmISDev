<%@page contentType="text/html"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html>
<head>
  <title>Haas TCM</title>
  <link rel="STYLESHEET" type="text/css" href="/css/global.css">
</head>

<body>

<table width="100%"> <%-- Banner --%>
<tr>
   <td><img src="/images/tcmtitlegif.gif"></td>
   <td class="pagetitle" align="right" width="10%">Error</td>
</tr>
<tr>
   <td class="heading">Web DBuy</td>
   <td class="headingr2" align="right" width="10%"><a class="tbar" href="orderList.jsp">Order&nbsp;List</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="tbar" href="logout.do">Log&nbsp;Off</a></td>
</tr>
</table>

<br>
<h2><i>tcm</i>IS Error</h2>

<center>

<br>
<logic:present name="POErrorMsgBean">
   <br>
   <bean:write name="POErrorMsgBean"/>
</logic:present>

</center>
</body>
</html:html>

