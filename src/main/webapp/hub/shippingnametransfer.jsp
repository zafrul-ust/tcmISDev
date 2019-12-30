<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script language="JavaScript" type="text/javascript">
<!--
var shipNamesColl = new Array();
<c:forEach var="name" items="${shippingNames}" varStatus="status">
shipNamesColl[${status.index}] = '<tcmis:jsReplace value="${status.current.properShippingName}" processMultiLines="true"/>';
</c:forEach>
parent.ifMultiShipName(shipNamesColl);
//-->
</script>
</head>
<body></body>
</html>