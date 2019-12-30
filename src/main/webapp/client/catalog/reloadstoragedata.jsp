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
 <%-- the reason that I have the include here is because the data is set here as well as catalogaddrequest.jsp --%>
 <%@ include file="/client/catalog/catalogaddrequeststoragedata.jsp" %>
 parent.reloadStorageData(storageJsonMainData,'${closeTransitWinflag}');
// -->
</script>
</head>
<body></body>
</html>