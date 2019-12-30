<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<tcmis:loggedIn indicator="true" forwardPage="/internalreport/index.jsp"/>
<html:html lang="true">
<head>

<title>
  <bean:message key="excel.title"/>
</title>
</head>
<body bgcolor="white">

<html:errors/>
<logic:present name="errorBean" scope="request">
  <font face=Verdana size=2 color="red">
    <b><bean:message key="excel.label.oracleerror"/>: <bean:write name="errorBean" property="cause"/></b>
  </font>
</logic:present>

</body>
<%--
<html:javascript formName="excelForm" dynamicJavascript="true" staticJavascript="true"/>
--%>
</html:html>
