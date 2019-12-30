<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<div id="${categoryId}MaterialGridDataDiv">
<script type="text/javascript">
<!--

var ${categoryId}MaterialRowSpanMap = [];
var ${categoryId}MaterialRowSpanClassMap = [];

var ${categoryId}MaterialGridData = {
rows: [
<c:set var="materialTablePermission" value="N"/>
<c:if test="${empty request.mfr || empty request.mfr.acquisitionType || request.mfr.acquisitionType eq 'DIVISION'}">
	<c:set var="materialTablePermission" value="Y"/>
</c:if>

<fmt:formatDate var="year" value="${now}" pattern="yyyy" />
<c:set var="materialCount" value="${0}"/>
<c:forEach var="currentMaterial" items="${request.currentMaterials}" varStatus="matStatus">
<c:set var="newMaterial" value="${request.materials[matStatus.index]}"/>

<c:set var="newMaterialDesc" value="${newMaterial.materialDesc}"/>
<c:set var="newTradeName" value="${newMaterial.tradeName}"/>
<c:set var="newLocaleDesc" value="${newMaterial.localeMaterialDesc}"/>
<c:set var="newLocaleTradeName" value="${newMaterial.localeTradeName}"/>

<c:set var="oobMsg" value="OUT OF BUSINESS (${year})"/>
<c:set var="rebrandMsg" value="(frmly ${currentMaterial.materialDesc})"/>
<c:set var="localeRebrandMsg" value="(frmly ${currentMaterial.localeMaterialDesc})"/>
<c:set var="oldFormMsg" value="(Old Formulation ${year})"/>
<c:set var="discontMsg" value="(Discontinued ${year})"/>
<c:if test="${categoryId eq mfroutofbusiness}">
	<c:set var="newMaterialDesc" value="${empty newMaterial.materialDesc?oobMsg:newMaterial.materialDesc}"/>
	<c:set var="newTradeName" value="${empty newMaterial.tradeName?oobMsg:newMaterial.tradeName}"/>
	<c:set var="newLocaleDesc" value="${empty newMaterial.localeMaterialDesc?oobMsg:newMaterial.localeMaterialDesc}"/>
	<c:set var="newLocaleTradeName" value="${empty newMaterial.localeTradeName?oobMsg:newMaterial.localeTradeName}"/>
</c:if>
<c:if test="${categoryId eq rebrandedproduct}">
	<c:set var="newMaterialDesc" value="${empty newMaterial.materialDesc?rebrandMsg:newMaterial.materialDesc}"/>
	<c:set var="newLocaleDesc" value="${empty newMaterial.localeMaterialDesc?localeRebrandMsg:newMaterial.localeMaterialDesc}"/>
</c:if>
<c:if test="${categoryId eq formulationchange}">
	<c:set var="newMaterialDesc" value="${empty newMaterial.materialDesc?oldFormMsg:newMaterial.materialDesc}"/>
	<c:set var="newTradeName" value="${empty newMaterial.tradeName?oldFormMsg:newMaterial.tradeName}"/>
	<c:set var="newLocaleDesc" value="${empty newMaterial.localeMaterialDesc?oldFormMsg:newMaterial.localeMaterialDesc}"/>
	<c:set var="newLocaleTradeName" value="${empty newMaterial.localeTradeName?oldFormMsg:newMaterial.localeTradeName}"/>
</c:if>
<c:if test="${categoryId eq materialdiscontinuation}">
	<c:set var="newMaterialDesc" value="${empty newMaterial.materialDesc?discontMsg:newMaterial.materialDesc}"/>
	<c:set var="newTradeName" value="${empty newMaterial.tradeName?discontMsg:newMaterial.tradeName}"/>
	<c:set var="newLocaleDesc" value="${empty newMaterial.localeMaterialDesc?discontMsg:newMaterial.localeMaterialDesc}"/>
	<c:set var="newLocaleTradeName" value="${empty newMaterial.localeTradeName?discontMsg:newMaterial.localeTradeName}"/>
</c:if>

{ "id":<c:out value="${matStatus.count}"/>,
	"data":[
	       "${materialTablePermission}",
	       ${newMaterial.grab},
	       "${currentMaterial.materialId}",
	       "${currentMaterial.mfgId}",
	       "<tcmis:jsReplace value="${newMaterialDesc}" processMultiLines="true"/>",
	       "<tcmis:jsReplace value="${currentMaterial.materialDesc}" processMultiLines="true"/>",
	       "<tcmis:jsReplace value="${newTradeName}" processMultiLines="true"/>",
	       "<tcmis:jsReplace value="${currentMaterial.tradeName}" processMultiLines="true"/>",
	       "<tcmis:jsReplace value="${newMaterial.productCode}" processMultiLines="true"/>",
	       "<tcmis:jsReplace value="${currentMaterial.productCode}" processMultiLines="true"/>",
	       "${currentMaterial.localeCode}",
	       "<tcmis:jsReplace value="${currentMaterial.localeDisplay}" processMultiLines="false"/>",
	       "${empty currentMaterial.localeCode?'N':'Y'}",
	       "<tcmis:jsReplace value="${empty currentMaterial.localeCode?'&nbsp;':newLocaleDesc}" processMultiLines="true"/>",
	       "<tcmis:jsReplace value="${currentMaterial.localeMaterialDesc}" processMultiLines="true"/>",
	       "${empty currentMaterial.localeCode?'N':'Y'}",
	       "<tcmis:jsReplace value="${empty currentMaterial.localeCode?'&nbsp;':newLocaleTradeName}" processMultiLines="true"/>",
	       "<tcmis:jsReplace value="${currentMaterial.localeTradeName}" processMultiLines="true"/>",
	       "<tcmis:jsReplace value="${newMaterial.pageUploadCode}" processMultiLines="true"/>"
	]}<c:if test="${not matStatus.last}">,</c:if>
</c:forEach>
]
};

<c:forEach var="currentMaterial" items="${request.currentMaterials}" varStatus="matStatus">
<c:set var="currentKey" value='${currentMaterial.materialId}' />
<c:choose>
	<c:when test="${matStatus.first}">
		<c:set var="rowSpanStart" value='0' />
		<c:set var="rowSpanCount" value='1' />
		${categoryId}MaterialRowSpanMap[0] = 1;
		${categoryId}MaterialRowSpanClassMap[0] = 1;
		<c:set var="materialCount" value="${materialCount+1}"/>
	</c:when>
	<c:when test="${currentKey == previousKey}">
		${categoryId}MaterialRowSpanMap[${rowSpanStart}]++;
		${categoryId}MaterialRowSpanMap[${matStatus.index}] = 0;
		${categoryId}MaterialRowSpanClassMap[${matStatus.index}] = ${rowSpanCount % 2};
	</c:when>
	<c:otherwise>
		<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
		<c:set var="rowSpanStart" value='${matStatus.index}' />
		${categoryId}MaterialRowSpanMap[${matStatus.index}] = 1;
		${categoryId}MaterialRowSpanClassMap[${matStatus.index}] = ${rowSpanCount % 2};
		<c:set var="materialCount" value="${materialCount+1}"/>
	</c:otherwise>
</c:choose>
<c:set var="previousKey" value='${currentKey}' />
</c:forEach>
//-->
</script>
<input type="hidden" id="${categoryId}MaterialCount" name="${categoryId}MaterialCount" value="${materialCount}"/>
<c:set var="materialCount" value="${0}"/>
</div>