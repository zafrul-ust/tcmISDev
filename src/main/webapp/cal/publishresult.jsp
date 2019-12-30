<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html lang="true">
<head>
<title>
  <bean:message key="tiertwo.title"/>
</title>
</head>
<body bgcolor="white">

<html:errors/>
<h3>
  <html:img src="/images/logo_s.gif" border="1" align="top"/>
  <font face=Verdana size=2>
    <bean:message key="tiertwo.title"/>
  </font>
</h3>
<table border="0">
  <tr>
    <th >
      <bean:message key="tiertwo.header"/>
    </th>
    <th>
      <font face=Verdana size=2>
        <html:link forward="logout">
          <bean:message key="label.logout"/>
        </html:link>
      </font>
    </th>
  </tr>
  <tr>
    <td colspan="2">
      <font face=Verdana size=2>
        <bean:message key="publishresult.label.message"/>
      </font>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <font face=Verdana size=2>
        <html:link forward="tiertworeporthome">
          <bean:message key="label.continue"/>
        </html:link>
      </font>
    </td>
  </tr>
</table>
</html:form>
</body>
</html:html>





