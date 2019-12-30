<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<c:set var="page" scope="request" value="${page}"/>
<c:out value='${map}' escapeXml='false'/>
<html:html lang="true">
<head>
<title><c:out value="${chartType}"/></title>
</head>
<BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF" ALINK="" VLINK="#FFFF66" >

<TABLE WIDTH="500" BORDER="0" CLASS="moveup">
<tr>
<td width="100%" ALIGN="LEFT" >
<img src="/charts/<c:out value='${fileName}'/>" width=500 height=300 border=0 usemap="#<c:out value='${fileName}'/>">
</td>
</tr>
</TABLE>
</body>
</html:html>
