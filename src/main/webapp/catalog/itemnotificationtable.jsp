<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<div id="${categoryId}ItemGridData">
<script type="text/javascript">
<!--
<c:set var="itemTablePermission" value="Y"/>

var ${categoryId}ItemRowSpanMap = [];
var ${categoryId}ItemRowSpanClassMap = [];
var ${categoryId}ItemRowSpanLvl2Map = [];

var ${categoryId}ItemGridData = {
rows: [

<fmt:formatDate var="year" value="${now}" pattern="yyyy" />
<c:set var="itemCount" value="${0}"/>
<c:forEach var="currentItem" items="${request.currentItems}" varStatus="itemStatus">
<c:set var="newItem" value="${request.items[itemStatus.index]}"/>

<c:set var="newMfgPartNo" value="${newItem.mfgPartNo}"/>
<c:set var="newLocaleMfgPartNo" value="${newItem.localeMfgPartNo}"/>

<c:set var="obsoleteMsg" value="OBSOLETE (${year})"/>
<c:set var="sizePkgMsg" value="OBSOLETE SIZE/PKG (${year})"/>

<c:if test="${categoryId eq formulationchange || categoryId eq materialdiscontinuation}">
	<c:set var="newMfgPartNo" value="${empty newItem.mfgPartNo?obsoleteMsg:newItem.mfgPartNo}"/>
	<c:set var="newLocaleMfgPartNo" value="${empty newItem.localeMfgPartNo?obsoleteMsg:newItem.localeMfgPartNo}"/>
</c:if>
<c:if test="${categoryId eq itemdiscontinuation}">
	<c:set var="newMfgPartNo" value="${empty newItem.mfgPartNo?sizePkgMsg:newItem.mfgPartNo}"/>
	<c:set var="newLocaleMfgPartNo" value="${empty newItem.localeMfgPartNo?sizePkgMsg:newItem.localeMfgPartNo}"/>
</c:if>

<c:if test="${itemStatus.index gt 0}">,</c:if>
{ id:<c:out value="${itemStatus.count}"/>,
	data: [
	       "${itemTablePermission}",
	       "",
	       "${currentItem.itemId}",
	       "<tcmis:jsReplace value="${currentItem.itemDesc}" processMultiLines="true"/>",
	       "<tcmis:jsReplace value="${currentItem.packaging}" processMultiLines="true"/>",
	       "<tcmis:jsReplace value="${newItem.revisionComments}" processMultiLines="true"/>",
	       "<tcmis:jsReplace value="${currentItem.revisionComments}" processMultiLines="true"/>",
	       "<tcmis:jsReplace value="${empty newItem.stockingType?'O':newItem.stockingType}" processMultiLines="false"/>",
	       "<tcmis:jsReplace value="${currentItem.stockingType}" processMultiLines="false"/>",
	       "<tcmis:jsReplace value="${empty newItem.itemType?'OB':newItem.itemType}" processMultiLines="false"/>",
	       "<tcmis:jsReplace value="${currentItem.itemType}" processMultiLines="false"/>",
	       "${currentItem.partId}",
	       "${currentItem.materialId}",
	       "${currentItem.mfgId}",
	       "<tcmis:jsReplace value="${currentItem.pkgStyle}" processMultiLines="true"/>",
	       "<tcmis:jsReplace value="${newMfgPartNo}" processMultiLines="true"/>",
	       "<tcmis:jsReplace value="${currentItem.mfgPartNo}" processMultiLines="true"/>",
	       "<tcmis:jsReplace value="${newItem.grade}" processMultiLines="true"/>",
	       "<tcmis:jsReplace value="${currentItem.grade}" processMultiLines="true"/>",
	       "${newItem.minTemp}",
	       "${currentItem.minTemp}",
	       "${newItem.maxTemp}",
	       "${currentItem.maxTemp}",
	       <c:set var="newTempUnits" value="${empty newItem.tempUnits?currentItem.tempUnits:newItem.tempUnits}"/>
	       "<tcmis:jsReplace value="${newTempUnits}" processMultiLines="false"/>",
	       "<tcmis:jsReplace value="${currentItem.tempUnits}" processMultiLines="false"/>",
	       "${empty newItem.shelfLifeDays?currentItem.shelfLifeDays:newItem.shelfLifeDays}",
	       "${currentItem.shelfLifeDays}",
	       <c:set var="newBasis" value="${empty newItem.shelfLifeBasis?currentItem.shelfLifeBasis:newItem.shelfLifeBasis}"/>
	       "<tcmis:jsReplace value="${newBasis}" processMultiLines="false"/>",
	       <c:choose><c:when test="${empty currentItem.shelfLifeBasisDispLabel}">"",</c:when><c:otherwise>
	       "<fmt:message key="${currentItem.shelfLifeBasisDispLabel}"/>",</c:otherwise></c:choose>
	       "<tcmis:jsReplace value="${currentItem.localeCode}" processMultiLines="false"/>",
	       "<tcmis:jsReplace value="${currentItem.localeDisplay}" processMultiLines="false"/>",
	       "${empty currentItem.localeCode?'N':'Y'}",
	       "<tcmis:jsReplace value="${empty currentItem.localeCode?'&nbsp;':newLocaleMfgPartNo}" processMultiLines="true"/>",
	       "<tcmis:jsReplace value="${currentItem.localeMfgPartNo}" processMultiLines="true"/>",
	       "${empty currentItem.localeCode?'N':'Y'}",
	       "<tcmis:jsReplace value="${empty currentItem.localeCode?'&nbsp;':newItem.localeGrade}" processMultiLines="true"/>",
	       "<tcmis:jsReplace value="${currentItem.localeGrade}" processMultiLines="true"/>",
	       "${empty currentItem.localeCode?'N':'Y'}",
	       "${newItem.localeMinTemp}",
	       "${currentItem.localeMinTemp}",
	       "${empty currentItem.localeCode?'N':'Y'}",
	       "${newItem.localeMaxTemp}",
	       "${currentItem.localeMaxTemp}",
	       "${empty currentItem.localeCode?'N':'Y'}",
	       <c:set var="newLocTempUnits" value="${empty newItem.localeTempUnits?currentItem.localeTempUnits:newItem.localeTempUnits}"/>
	       "<tcmis:jsReplace value="${empty currentItem.localeCode?'&nbsp;':newLocTempUnits}" processMultiLines="false"/>",
	       "<tcmis:jsReplace value="${currentItem.localeTempUnits}" processMultiLines="false"/>"
	]
}
</c:forEach>
]
};

<c:forEach var="currentItem" items="${request.currentItems}" varStatus="itemStatus">
<c:set var="itemKey" value="${currentItem.itemId}" />
<c:set var="partKey" value="${currentItem.materialId}" />
<c:choose>
	<c:when test="${itemStatus.first}">
		<c:set var="rowSpanStart" value="${0}"/>
		<c:set var="rowSpanLvl2Start" value="${0}"/>
		<c:set var="rowSpanCount" value="${1}"/>
		${categoryId}ItemRowSpanMap[0]= 1;
		${categoryId}ItemRowSpanLvl2Map[0] = 1;
		<c:set var="itemCount" value="${itemCount+1}"/>
	</c:when>
	<c:when test="${itemKey ne previousItem}">
		${categoryId}ItemRowSpanMap[${itemStatus.index}] = 1;
		${categoryId}ItemRowSpanLvl2Map[${itemStatus.index}] = 1;
		<c:set var="rowSpanCount" value="${rowSpanCount + 1}" />
		<c:set var="rowSpanStart" value='${itemStatus.index}' />
		<c:set var="rowSpanLvl2Start" value='${itemStatus.index}' />
		<c:set var="itemCount" value="${itemCount+1}"/>
	</c:when>
	<c:when test="${partKey ne previousPart}">
		${categoryId}ItemRowSpanMap[${itemStatus.index}] = 0;
		${categoryId}ItemRowSpanMap[${rowSpanStart}]++;
		${categoryId}ItemRowSpanLvl2Map[${itemStatus.index}] = 1;
		<c:set var="rowSpanCount" value="${rowSpanCount + 1}" />
		<c:set var="rowSpanLvl2Start" value='${itemStatus.index}' />
	</c:when>
	<c:otherwise>
		${categoryId}ItemRowSpanMap[${itemStatus.index}] = 0;
		${categoryId}ItemRowSpanMap[${rowSpanStart}]++;
		${categoryId}ItemRowSpanLvl2Map[${itemStatus.index}] = 0;
		${categoryId}ItemRowSpanLvl2Map[${rowSpanLvl2Start}]++;
	</c:otherwise>
</c:choose>
<c:set var="previousItem" value="${itemKey}" />
<c:set var="previousPart" value="${partKey}" /> // lineCount == ${itemCount}, status.count == ${itemStatus.count}
${categoryId}ItemRowSpanClassMap[${itemStatus.index}]= ${itemCount % 2};
</c:forEach>

//-->
</script>
<input type="hidden" id="${categoryId}ItemCount" name="${categoryId}ItemCount" value="${itemCount}"/>
<c:set var="itemCount" value="${0}"/>
</div>