<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/common/locale.jsp" %>
<%@page import="org.json.*"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<html>
<head>
<script language="JavaScript" type="text/javascript">
<!--
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${storageAreaCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
{
    <tcmis:jsReplace var="tmpId" value="${bean.storageAreaId}"/>
    <c:if test="${param.reportType == 'waStkMgmt'}">
        <c:set var="tmpId" value="${bean.applicationId}"/>
    </c:if>
    id:'${tmpId}',
    desc:'<tcmis:jsReplace value="${bean.storageAreaDesc}" processMultiLines="true"/>'
}
<c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
parent.showWorkArea(jsonMainData);
// -->
</script>
</head>
<body></body>
</html>