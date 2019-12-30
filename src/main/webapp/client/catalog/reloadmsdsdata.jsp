<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ include file="/common/locale.jsp" %>
<%@page import="org.json.*"%>
<html>
<head>
<script language="JavaScript" type="text/javascript">
<!--
 <%-- the reason that I have the include here is because the data is set here as well as catalogaddmsdsrequest.jsp --%>
 <c:set var="callParent" value='Y'/>
 <%@ include file="/client/catalog/catalogaddrequestmsdsdata.jsp" %>
 parent.reloadMsdsData(msdsConfig,msdsJsonMainData,lineMap,lineIdMap,lineMap3,'${closeTransitWinflag}'); 
// -->
</script>
</head>
<body></body>
</html>