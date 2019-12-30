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
<c:set var="group" value=""/>
<c:set var="count" value="0"/>
<c:forEach var="chartBean" items="${fileNames}" varStatus="status">
<c:if test="${group == '' || group != status.current.inventoryGroupCollection}">
<tr>
<th colspan="4">
<c:out value="${status.current.inventoryGroupCollection}"/>
</th>
</tr>
<c:set var="count" value="0"/>
</c:if>
<c:if test="${count != 0 && count % 4 == 0}">
</tr>
<tr>
</c:if>
<td width="100%" ALIGN="LEFT" >
<img src="/charts/<c:out value="${status.current.fileName}"/>" width=180 height=350 border=0>
</td>
<c:set var="count" value="${count + 1}"/>
<c:set var="group" value="${status.current.inventoryGroupCollection}"/>
</c:forEach>

</tr>
</TABLE>
</body>
</html:html>
