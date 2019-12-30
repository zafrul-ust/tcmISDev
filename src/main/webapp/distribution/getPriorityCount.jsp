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
<c:choose>
	<c:when test="${not empty maxPriority}">
		parent.setNewRowPriority(${maxPriority});
	</c:when>
	<c:otherwise>
	if('${count}' > 1*1)
  		parent.changePriority('Y');
  	else
  		parent.changePriority('N');
	</c:otherwise>
</c:choose>
// -->
</script>
</head>
<body></body>
</html>