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
 var workAreas =
 {	areas:
	[<c:forEach var="workArea" items="${workAreasForTransfer}" varStatus="status">
 		{ 
 			applicationId: '${workArea.applicationId}',
		  	applicationDesc: '${workArea.applicationDesc}' 
		}<c:if test="${!status.last}">,</c:if>
	</c:forEach>]
 };
 
 eval('parent.loadWorkArea(workAreas, "${param.rowId}")');
 
// -->
</script>
</head>
<body></body>
