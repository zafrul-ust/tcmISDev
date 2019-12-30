<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

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

<html:form action="/reportlogin.do" onsubmit="return validateLoginForm(this);">
  <tr>
    <td >
    <font face=Verdana size=2>
      <bean:message key="index.label.logonid"/>:
    </font>
    </td>
    <td >
      <html:text property="logonId" size="15" maxlength="15"/>
    </td
  </tr>
  <tr>
    <td >
    <font face=Verdana size=2>
      <bean:message key="index.label.password"/>:
    </font>
    </td>
    <td >
      <html:password property="password" size="15" maxlength="15"/>
      <html:hidden property="client" value="internalreport"/>
    </td
  </tr>
  <tr>
    <td colspan="2">
    <font face=Verdana size=2>
      <html:submit>
        <bean:message key="index.label.submit"/>
      </html:submit>
    </font>
    </td>
  </tr>
</table>

</html:form>

</body>
<%--
<html:javascript formName="loginForm" dynamicJavascript="true" staticJavascript="true"/>
--%>
</html:html>
