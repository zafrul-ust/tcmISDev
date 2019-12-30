<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html lang="true">
<head>
  <title>Haas TCM</title>
  <meta http-equiv="Refresh" content='20; URL=ebpcomplete.do'>
  <link rel="STYLESHEET" type="text/css" href="css/global.css">
</head>

<body>
<h3>Opening file ...</h3>

<logic:present name="JNLPFileBean">
<script>
  window.open('<bean:write name="JNLPFileBean"/>');
</script>
</logic:present>

</html:html>

