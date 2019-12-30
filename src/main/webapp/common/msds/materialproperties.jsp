<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>

<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />

<%@ include file="/common/locale.jsp" %>
<script LANGUAGE = "JavaScript">
<!--

// -->
</script>
</head>
<body>
<TABLE BORDER="0" CELLPADDING="5" align="center" WIDTH="80%" >

<tr>
	<th colspan=2 align="center" bgcolor="#000066">
		<FONT FACE="Arial" SIZE="2" COLOR="#ffffff"><B><fmt:message key="label.materialproperties"/></B></FONT>
	</th>
</tr>

<c:set var="rowCount" value="0" />
<%--
<c:if test="${!empty materialProperties[0].alloy}">  --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.alloy"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].alloy}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.alloy"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].alloy}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%-- </c:if> 

<c:if test="${!empty materialProperties[0].boilingPoint}">  --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.boilingpoint"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].boilingPoint}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.boilingpoint"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].boilingPoint}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%-- </c:if>

<c:if test="${!empty materialProperties[0].carcinogen}">   --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.carcinogen"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].carcinogen}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.carcinogen"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].carcinogen}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%-- </c:if>

<c:if test="${!empty materialProperties[0].chronic}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.chronic"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].chronic}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.chronic"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].chronic}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%-- </c:if>

<c:if test="${!empty materialProperties[0].compatibility}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.compatibility"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].compatibility}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.compatibility"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].compatibility}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%-- </c:if>

<c:if test="${!empty materialProperties[0].corrosive}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.corrosive"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].corrosive}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.corrosive"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].corrosive}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%-- </c:if>


<c:if test="${!empty materialProperties[0].density}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.density"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].density} ${materialProperties[0].densityUnit}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.density"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].density} ${materialProperties[0].densityUnit}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%-- </c:if>

<c:if test="${!empty materialProperties[0].detonable}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.detonable"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].detonable}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.detonable"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].detonable}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%-- </c:if>

<c:if test="${!empty materialProperties[0].evaporationRate}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.evaporationrate"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].evaporationRate}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.evaporationrate"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].evaporationRate}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%-- </c:if>

<c:if test="${!empty materialProperties[0].eyes}">
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left"><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.eyes"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].eyes}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.eyes"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].eyes}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
</c:if>

<c:if test="${!empty materialProperties[0].federalHazardClass}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.federalhazardclass"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].federalHazardClass}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.federalhazardclass"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].federalHazardClass}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%-- </c:if>

<c:if test="${!empty materialProperties[0].fireConditionsToAvoid}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.fireconditionstoavoid"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].fireConditionsToAvoid}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.fireconditionstoavoid"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].fireConditionsToAvoid}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%-- </c:if>

<c:if test="${!empty materialProperties[0].flashPoint}">  --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.flashpoint"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].flashPoint}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.flashpoint"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].flashPoint}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%-- </c:if>

<c:if test="${!empty materialProperties[0].freezingPoint}">  --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.freezingpoint"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].freezingPoint}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.freezingpoint"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].freezingPoint}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%-- </c:if>

<c:if test="${!empty materialProperties[0].healthEffects}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.healtheffects"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].healthEffects}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.healtheffects"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].healthEffects}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].hydrocarbon}"> --%>
<%--<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.hydrocarbon"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].hydrocarbon}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.hydrocarbon"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].hydrocarbon}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>--%>
<%--</c:if>

<c:if test="${!empty materialProperties[0].incompatible}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.incompatible"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].incompatible}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.incompatible"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].incompatible}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].ingestion}"> --%>
<%--<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.ingestion"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].ingestion}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.ingestion"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].ingestion}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>--%>
<%--</c:if>

<c:if test="${!empty materialProperties[0].inhalation}">
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left"><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.inhalation"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].inhalation}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.inhalation"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].inhalation}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
</c:if>

<c:if test="${!empty materialProperties[0].injection}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.injection"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].injection}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.injection"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].injection}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].lfcCode}">
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left"><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.lfccode"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].lfcCode}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.lfccode"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].lfcCode}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
</c:if>

<c:if test="${!empty materialProperties[0].mixture}">  --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.mixture"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].mixture}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.mixture"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].mixture}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].miscible}">  --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.miscible"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].miscible}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.miscible"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].miscible}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].nanoMaterial}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.nanomaterial"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].nanoMaterial}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.nanomaterial"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].nanoMaterial}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].nfpaFromCustomer}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.nfpafromcustomer"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].nfpaFromCustomer}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.nfpafromcustomer"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].nfpaFromCustomer}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].oshaHazard}"> --%>
<%--<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.oshahazard"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].oshaHazard}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.oshahazard"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].oshaHazard}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>--%>
<%--</c:if>

<c:if test="${!empty materialProperties[0].oxidizer}">  --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.oxidizer"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].oxidizer}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.oxidizer"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].oxidizer}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].personalProtection}"> 
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.personalProtection"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].personalProtection}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.personalProtection"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].personalProtection}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
</c:if>

<c:if test="${!empty materialProperties[0].ph}">  --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.ph"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].ph}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.ph"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].ph}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].photoreactive}">  --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.photoreactive"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].photoreactive}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.photoreactive"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].photoreactive}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].physicalState}">   --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.physicalstate"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].physicalState}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.physicalstate"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].physicalState}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].polymerize}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.polymerize"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].polymerize}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.polymerize"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].polymerize}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].ppe}"> 
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left"><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.personalProtectiveEquip"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].ppe}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.personalProtectiveEquip"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].ppe}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
</c:if>

<c:if test="${!empty materialProperties[0].productCode}">  --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="report.label.productCode"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].productCode}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="report.label.productCode"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].productCode}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].pyrophoric}">  --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.pyrophoric"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].pyrophoric}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.pyrophoric"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].pyrophoric}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].radioactive}">  --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.radioactive"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].radioactive}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.radioactive"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].radioactive}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].radiaactiveCuries}">  --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.radioactivecuries"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].radiaactiveCuries}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.radioactivecuries"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].radiaactiveCuries}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].reactivity}">  --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.reactivity"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].reactivity}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.reactivity"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].reactivity}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].routeOfEntry}">
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left"><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.routeofentry"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].routeOfEntry}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.routeofentry"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].routeOfEntry}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
</c:if>

<c:if test="${!empty materialProperties[0].sara311312Acute}">  --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.sara311312acute"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].sara311312Acute}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.sara311312acute"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].sara311312Acute}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].sara311312Chronic}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.sara311312chronic"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].sara311312Chronic}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.sara311312chronic"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].sara311312Chronic}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].sara311312Fire}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.sara311312fire"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].sara311312Fire}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.sara311312fire"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].sara311312Fire}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].sara311312Pressure}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.sara311312pressure"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].sara311312Pressure}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.sara311312pressure"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].sara311312Pressure}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].sara311312Reactivity}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.sara311312reactivity"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].sara311312Reactivity}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.sara311312reactivity"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].sara311312Reactivity}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].signalWord}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.signalWord"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].signalWord}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.signalWord"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].signalWord}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].skin}">
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left"><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.skin"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].skin}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.skin"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].skin}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
</c:if>

<c:if test="${!empty materialProperties[0].solids}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.solids"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].solids}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.solids"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].solids}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>

<c:set var="tmpVal" value="${materialProperties[0].specificGravity}" />
<c:if test="${fn:startsWith(tmpVal, ' (') && tmpVal != ' (no response)' && tmpVal != ' (client)'}">
    <c:set var="tmpVal" value="" />
</c:if>
<c:if test="${tmpVal == '* (no response)' || tmpVal == 'N/L (no response)' || tmpVal == 'N/L (Water) (no response)'}">
    <c:set var="tmpVal" value=" (no response)" />
</c:if>
<c:if test="${tmpVal == '* (client)'}">
    <c:set var="tmpVal" value=" (client)" />
</c:if>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.specificgravity"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${tmpVal}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.specificgravity"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${tmpVal}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].specificHazard}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.specifichazard"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].specificHazard}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.specifichazard"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].specificHazard}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].spontaneouslyCombustible}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.spontaneouslycombustible"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].spontaneouslyCombustible}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.spontaneouslycombustible"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].spontaneouslyCombustible}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].stable}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.stable"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].stable}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.stable"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].stable}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].targetOrgan}"> --%>
<%--<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.targetorgan"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].targetOrgan}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.targetorgan"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].targetOrgan}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>--%>
<%--</c:if>

<c:if test="${!empty materialProperties[0].tsca12b}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.tsca12b"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].tsca12b}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.tsca12b"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].tsca12b}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].tscaList}">  --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.tsca8b"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].tscaList}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.tsca8b"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].tscaList}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].vaporDensity}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.vapordensity"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].vaporDensity}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.vapordensity"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].vaporDensity}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].vaporPressure}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.vaporpressure"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].vaporPressure}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.vaporpressure"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].vaporPressure}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].vocCompVaporPressureMmhg}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.voccompvaporpressuremmhg"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].vocCompVaporPressureMmhg}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.voccompvaporpressuremmhg"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].vocCompVaporPressureMmhg}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].vocLbPerSolidLb}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.voclbpersolidlb"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].vocLbPerSolidLb}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.voclbpersolidlb"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].vocLbPerSolidLb}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].vocLbPerSolidLbLow}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.voclbpersolidlblow"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].vocLbPerSolidLbLow}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.voclbpersolidlblow"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].vocLbPerSolidLbLow}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].vocLbPerSolidLbUp}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.voclbpersolidlbup"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].vocLbPerSolidLbUp}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.voclbpersolidlbup"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].vocLbPerSolidLbUp}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>

<c:set var="tmpVal" value="${materialProperties[0].vocLessH2oExempt}" />
<c:if test="${fn:startsWith(tmpVal, ' (') && tmpVal != ' (no response)' && tmpVal != ' (client)'}">
    <c:set var="tmpVal" value="" />
</c:if>
<c:if test="${tmpVal == '* (no response)' || 'N/L (no response)' || tmpVal == 'N/L (Water) (no response)'}">
    <c:set var="tmpVal" value=" (no response)" />
</c:if>
<c:if test="${tmpVal == '* (client)'}">
    <c:set var="tmpVal" value=" (client)" />
</c:if>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.voclesswaterandexempt"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${tmpVal}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.voclesswaterandexempt"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${tmpVal}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>

<c:set var="tmpVal" value="${materialProperties[0].voc}" />
<c:if test="${fn:startsWith(tmpVal, ' (') && tmpVal != ' (no response)' && tmpVal != ' (client)'}">
    <c:set var="tmpVal" value="" />
</c:if>
<c:if test="${tmpVal == '* (no response)' || 'N/L (no response)' || tmpVal == 'N/L (Water) (no response)'}">
    <c:set var="tmpVal" value=" (no response)" />
</c:if>
<c:if test="${tmpVal == '* (client)'}">
    <c:set var="tmpVal" value=" (client)" />
</c:if>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.voc"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${tmpVal}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.voc"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${tmpVal}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].vocPercent}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.vocpercent"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].vocPercent}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.vocpercent"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].vocPercent}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].vocLowerPercent}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.voclower(%)"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].vocLowerPercent}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.voclower(%)"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].vocLowerPercent}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].vocUpperPercent}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.vocupper(%)"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].vocUpperPercent}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.vocupper(%)"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].vocUpperPercent}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if>

<c:if test="${!empty materialProperties[0].waterReactive}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.waterreactive"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].waterReactive}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.waterreactive"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].waterReactive}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%--</c:if> 

<c:if test="${!empty materialProperties[0].dateEntered}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.dateentered"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2"><fmt:formatDate value="${materialProperties[0].dateEntered}" pattern="${dateFormatPattern}"/></FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.dateentered"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2"><fmt:formatDate value="${materialProperties[0].dateEntered}" pattern="${dateFormatPattern}"/></FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%-- </c:if>

<c:if test="${!empty materialProperties[0].dataEntryComplete}"> --%>
<tr>
<c:choose>
   <c:when test="${rowCount % 2 == 0}" >
    <td align="left" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.dataentrycomplete"/></B></FONT></td>
	<td align="left"><FONT FACE="Arial" SIZE="2">${materialProperties[0].dataEntryComplete}</FONT></td>
   </c:when>
   <c:otherwise>
    <td align="left" BGCOLOR="#E6E8FA" nowrap><FONT FACE="Arial" SIZE="2" COLOR="#000066"><B><fmt:message key="label.dataentrycomplete"/></B></FONT></td>
	<td align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${materialProperties[0].dataEntryComplete}</FONT></td>
   </c:otherwise>
</c:choose>
<c:set var="rowCount" value="${rowCount+1}" />
</tr>
<%-- </c:if>  --%>

</TABLE>

<center><BR>*<fmt:message key="label.notfrommsdsbymanufacturer"/><BR>
<center>
</body>
</html:html>


