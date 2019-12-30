<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<tcmis:loggedIn indicator="true" forwardPage="/ray/index.jsp"/>
<html:html lang="true">
<head>
<title><bean:message key="label.haastcm"/></title>
</head>
<body bgcolor="white">
<html:errors/>
<h3>
  <html:img src="/images/logo_s.gif" border="1" align="top"/>
  <font face=Verdana size=2>
    <bean:message key="label.haastcm"/>
  </font>
</h3>
<table border="0">
  <tr>
    <th width="100%" align="right">
      <font face=Verdana size=2>
      </font>
    </th>
  </tr>
  <tr>
    <td>
      <font face=Verdana size=2>
        This application allows you to run your own reports in "free form". Type in your SQL statement and comment and click "Get Excel Report" and the application will create an excel report for you and display it in the browser.
     </font>
    </td>
  </tr>
  <tr>
    <td>
      <font face=Verdana size=2>
        <b>Some basic syntax:</b><br>
        <font face=Verdana size=2 color="blue"><i>select * from MY_TABLE</i></font><br>
        returns all columns and all data in the table MY_TABLE. <br>
        <font face=Verdana size=2 color="blue"><i>select MY_COLUMN1, MY_COLUMN2 from MY_TABLE where MY_STRING_COLUMN1='foo'</i></font><br>
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
