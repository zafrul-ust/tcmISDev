<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<%@ include file="/common/locale.jsp" %>
<%@page import="org.json.*"%>
<html>
<head>
<script language="JavaScript" type="text/javascript">
<!--
// content of your Javascript goes here
var jsonobj = new Array();
<c:forEach var="pickList" items="${pickListIdData}" varStatus="status">
	<fmt:formatDate var="fmtDate" value="${status.current.picklistPrintDate}" pattern="EEE MMM dd yyyy HH:mm a zzz"/>
	jsonobj[${status.index}]={
		   picklistId:   		'${status.current.picklistId}',		   
		   picklistPrintDate: 			'${fmtDate}'		
	};
</c:forEach>
 parent.updatePickListDropDown(jsonobj); 
// -->
</script>
</head>
<body></body>
</html>