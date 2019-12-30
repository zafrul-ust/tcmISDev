<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="org.json.*"%>
<html>
<head>
<script language="JavaScript" type="text/javascript">
<!--
// content of your Javascript goes here
 var jsonobj =
 {
 	index:   '${param.index}',
 	itemId:  '${param.itemId}',
	bins: 
	[
		<c:forEach var="bin" items="${bins}" varStatus="status">
			<c:if test="${ status.index  != 0}">
			,
			</c:if>
			'${bin.bin}'
		</c:forEach>
	]
 };
 eval('parent.${param.callback}(jsonobj)');
 
// -->
</script>
</head>
<body></body>
