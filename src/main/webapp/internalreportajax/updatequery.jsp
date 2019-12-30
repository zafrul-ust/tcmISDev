<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<tcmis:loggedIn indicator="true" forwardPage="/internalreportajax/index.jsp"/>
<html:html lang="true">
<head>
<title>
  <bean:message key="excel.title"/>
</title>
</head>
<body bgcolor="white">

<html:errors/>
<h3>
  <html:img src="/images/logo_s.gif" border="1" align="top"/>
  <font face=Verdana size=2>
    <bean:message key="label.haastcm"/>
  </font>
</h3>
<html:form action="/updatequery.do" onsubmit="return validateSaveQueryForm(this);">
<table border="0">
  <tr>
    <th >
      <bean:message key="updatequery.header"/>
    </th>
    <th>
      <font face=Verdana size=2>
        <html:link forward="logout">
          <bean:message key="label.logout"/>
        </html:link>
      </font>
    </th>
  </tr>
  <logic:notPresent name="usersSavedQueriesBean" scope="request">
  <tr>
    <td colspan="2">
      <font face=Verdana size=2>
        <bean:message key="savequery.label.missing"/>:
      </font>
    </td>
  </tr>
  </logic:notPresent>
  <logic:present name="usersSavedQueriesBean" scope="request">
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="savequery.label.queryname"/>:
      </font>
    </td>
    <td >
      <font face=Verdana size=2>
        <html:hidden name="usersSavedQueriesBean" property="queryNameOld"/>
        <html:text name="usersSavedQueriesBean" property="queryName" size="30" maxlength="50"/>
      </font>
    </td
  </tr>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="updatequery.label.querytext"/>:
      </font>
    </td>
    <td >
      <font face=Verdana size=2>
          <html:textarea name="usersSavedQueriesBean" property="query" cols="60" rows="10"/>
      </font>
    </td
  </tr>
  <tr>
    <td colspan="2">
      <font face=Verdana size=2>
        <html:submit property="submit">
          <bean:message key="label.save"/>
        </html:submit>
      </font>
    </td>
  </tr>
  </logic:present>
</table>
</html:form>
</body>
<%--
<html:javascript formName="saveQueryForm" dynamicJavascript="true" staticJavascript="true"/>
--%>
</html:html>





