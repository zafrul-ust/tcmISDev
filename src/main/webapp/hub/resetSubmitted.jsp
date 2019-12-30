<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head><title>Reset Submitted</title></head>
<body>

<center>
<h2>Status Reset has been submitted. Check the status later.</h2>
<a href='edistatus.do?edicompany=<bean:write name="CompanyIdBean"/>'>Status Page</a>
</body>
</html>
