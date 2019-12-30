<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="index" value="${empty param.component?0:param.component}" />
<div id="itemDiv${index}">
<jsp:include page="msdsindexingsearch.jsp" />
</div>