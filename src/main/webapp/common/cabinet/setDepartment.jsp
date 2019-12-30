<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="expires" content="-1"/>
	<%@ include file="/common/locale.jsp" %>

<script>
	<!--
		var deptName = new Array(
				<c:forEach var="deptN" items="${deptCollection}" varStatus="nameStatus">	   
					'<tcmis:jsReplace value="${deptN.deptName}"/>'<c:if test="${!nameStatus.last}">,</c:if>
				</c:forEach>
			);
		var deptId = new Array(
				<c:forEach var="deptI" items="${deptCollection}" varStatus="idStatus">	   
					'<tcmis:jsReplace value="${deptI.deptId}"/>'<c:if test="${!idStatus.last}">,</c:if>
				</c:forEach>
			);
		function doCallback() {
			parent.deptCallBack(deptName,deptId);
		}
//-->
</script>	

</head>

<body onload="doCallback();">
</body>
</html>