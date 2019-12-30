<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<title><fmt:message key="label.haastcm"/></title>
</head>
<body bgcolor="green">
<table>
<tr>
<td>
TOMCAT TEST SUCCESSFUL! <tcmis:environment/>
</td>
</tr>
</table>
</body>
</html>
