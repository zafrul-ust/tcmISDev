<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html:html lang="true">
<head>

<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<script LANGUAGE = "JavaScript">
<!--

// -->
</script>
</head>
<body>
<TABLE BORDER="0" CELLPADDING="5" align="center" WIDTH="85%" >
	<tr>
		<th width="15%" align="center" bgcolor="#000066">
			<FONT FACE="Arial" SIZE="2" COLOR="#ffffff"><B><fmt:message key="label.regulation"/></B></FONT>
		</th>
		<th width="85%" align="center" bgcolor="#000066">
			<FONT FACE="Arial" SIZE="2" COLOR="#ffffff"><B><fmt:message key="label.declaration"/></B></FONT>
		</th>
	</tr>

<c:set var="rowCount" value="0" />
<c:forEach var="shippingBean" items="${shippingInfo}" varStatus="status">
	<tr>
	<c:choose>
	   <c:when test="${rowCount % 2 == 0}" >
	    <td width="15%" align="left"><FONT FACE="Arial" SIZE="2">${shippingBean.shippingRegulation}</FONT></td>
		<td width="85%" align="left"><FONT FACE="Arial" SIZE="2">${shippingBean.shippingDeclaration}</FONT></td>
	   </c:when>
	   <c:otherwise>
	    <td width="15%" align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${shippingBean.shippingRegulation}</FONT></td>
		<td width="55%" align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${shippingBean.shippingDeclaration}</FONT></td>
	   </c:otherwise>
	</c:choose>
	<c:set var="rowCount" value="${rowCount+1}" />
	</tr>
</c:forEach>
</TABLE>
</body>
</html:html>


