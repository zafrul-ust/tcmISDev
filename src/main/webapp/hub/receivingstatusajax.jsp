<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<%@page import="org.json.*"%>
<html>
<head>
<script language="JavaScript" type="text/javascript">
<!--
// content of your Javascript goes here
 var assignees =
	[<c:forEach var="assignee" items="${assignees}" varStatus="status">
 		{ 
 			userId: '${assignee.personnelId}',
		  	userName: '<tcmis:jsReplace value="${assignee.userName}" processMultiLines="true" />' 
		}<c:if test="${!status.last}">,</c:if>
	</c:forEach>];
 
 eval('parent.loadAssignees(assignees)');
 
// -->
</script>
</head>
<body></body>
