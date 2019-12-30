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
<c:forEach var="countDate" items="${countDateColl}" varStatus="status">
	<fmt:formatDate var="fmtDate" value="${status.current.countDatetime}" pattern="MMM dd yyyy HH:mm a"/>
	jsonobj[${status.index}]={
		   countDateId:   		'${status.current.countDatetime.time}',		   
		   countDateDateValue: 			'${fmtDate}',
		   countId:		'${status.current.countId}',
		   countType:	'${status.current.countType}',
		   countStartedByName: '${status.current.countStartedByName}',
		   room: '${status.current.room}'
	};
</c:forEach>

<c:if test="${param.callback != null}">
eval('parent.${param.callback}(jsonobj)');
</c:if>

try{
  parent.updateCountDateDropDown(jsonobj,'${deletedCountId}');
}catch(ex){}
-->
</script>
</head>
<body></body>
</html>