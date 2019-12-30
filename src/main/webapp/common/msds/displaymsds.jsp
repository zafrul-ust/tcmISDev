<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<script LANGUAGE = "JavaScript">
<!--
<c:set var="module">
	<tcmis:module/>
</c:set>

<c:choose>
	<c:when test="${module == 'lmco'}">
	window.location = '/HaasReports/lmco/viewmsds.do?materialId=<tcmis:jsReplace value="${param.materialId}"/>&facilityId=<tcmis:jsReplace value="${param.facility}"/>&localeCode='+
			encodeURIComponent('<tcmis:jsReplace value="${param.localeCode}"/>') + '&date=<tcmis:jsReplace value="${param.revisionDate}"/>';
	</c:when>
	<c:otherwise>
	window.location = '${msdsUrl}';
	</c:otherwise>
</c:choose>
// -->
</script>
</head>
<body></body>
</html:html>


