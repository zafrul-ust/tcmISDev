
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<c:if test="${empty index}">
<%@ include file="/common/locale.jsp" %>
</c:if>

<c:set var="index" value="${empty index?empty param.component?0:param.component:index}" />
<c:if test="${not empty msdsCollection}"><c:set var="msds" value="${msdsCollection[param.component]}" /></c:if>
<fmt:message var="_aerosol" key="label.liquid/aerosol"/>
<fmt:message var="_air" key="label.air"/>
<fmt:message var="_basis" key="label.basis"/>
<fmt:message var="_boilingpoint" key="label.boilingpoint"/>
<fmt:message var="_cforcelsius" key="label.cforcelsius"/>
<fmt:message var="_chronic" key="label.chronic"/>
<fmt:message var="_clear" key="label.clear"/>
<fmt:message var="_cryogenicliquid" key="label.cryogenicliquid"/>
<fmt:message var="_density" key="label.density"/>
<fmt:message var="_detail" key="label.detail"/>
<fmt:message var="_detect" key="label.detect"/>
<fmt:message var="_errorType" key="label.msdsqcerrortype"/>
<fmt:message var="_fforfahrenheit" key="label.fforfahrenheit"/>
<fmt:message var="_flashpoint" key="label.flashpoint"/>
<fmt:message var="_flammability" key="label.flammability"/>
<fmt:message var="_gaslower" key="label.gaslower"/>
<fmt:message var="_health" key="label.health"/>
<fmt:message var="_hmis" key="label.hmis"/>
<fmt:message var="_liquid" key="label.liquid"/>
<fmt:message var="_lower" key="label.lower"/>
<fmt:message var="_method" key="label.method"/>
<fmt:message var="_msds" key="label.msds"/>
<fmt:message var="_na" key="label.na"/>
<fmt:message var="_nfpa" key="label.nfpa"/>
<fmt:message var="_no" key="label.no"/>
<fmt:message var="_noerror" key="label.none"/>
<fmt:message var="_notlisted" key="label.notlisted"/>
<fmt:message var="_personalprotection" key="label.personalProtection"/>
<fmt:message var="_ph" key="label.ph"/>
<fmt:message var="_physicalstate" key="label.physicalstate"/>
<fmt:message var="_reactivity" key="label.reactivity"/>
<fmt:message var="_semisolid" key="label.semi-solid"/>
<fmt:message var="_solid" key="label.solid"/>
<fmt:message var="_solids" key="label.solids"/>
<fmt:message var="_source" key="label.source"/>
<fmt:message var="_special" key="label.special"/>
<fmt:message var="_specificgravity" key="label.specificgravity"/>
<fmt:message var="_supplemental" key="label.supplemental"/>
<fmt:message var="_temperature" key="label.temperature"/>
<fmt:message var="_units" key="label.units"/>
<fmt:message var="_upper" key="label.upper"/>
<fmt:message var="_value" key="label.value"/>
<fmt:message var="_vaporpressure" key="label.vaporpressure"/>
<fmt:message var="_voc" key="label.voc"/>
<fmt:message var="_voclesswaterandexempt" key="label.voclesswaterandexempt"/>
<fmt:message var="_water" key="label.water"/>
<fmt:message var="_yes" key="label.yes"/>

<c:set var="maintenance" value="${empty catalogQueueRow && empty param.requesetId}" />
<c:set var="masterData" value="${not maintenance && catalogQueueRow.status eq 'Closed'}"/>
<c:set var="vendor" value="${not maintenance && (catalogQueueRow.status eq 'Assigned' || catalogQueueRow.status eq 'Pending QC')}" />
<c:set var="masterDataReview" value="${not maintenance && catalogQueueRow.status eq 'Pending Approval'}" />
<c:set var="sourcing" value="${not maintenance && not masterData && catalogQueueRow.task eq 'SDS Sourcing'}" />
<c:set var="authoring" value="${not maintenance && not masterData && catalogQueueRow.task eq 'SDS Authoring'}" />
<c:set var="indexing" value="${not maintenance && not masterData && catalogQueueRow.task eq 'SDS Indexing'}" />

<div class="sdsColumnHeader"><fmt:message key="label.customsdsproperties"/></div>
	<c:set var="prefix" value="co"/>
	<c:set var="item" value="${msds.coData}"/>
	<c:set var="userCoApprover" value="${userCoApprover && item.qcData.submitBy ne personnelBean.personnelId}" />
	<c:set var="qcStatus" value="${empty item.qcData.insertDate?'na':(empty item.qcData.submitDate?'incomplete':( ! empty item.qcData.approveDate?'complete':(userCoApprover?'unapproved':'pendingQc')))}"/>
	<%--<c:set var="qcStatus" value="${empty item.qcData.insertDate?'na':(empty item.qcData.submitDate?'incomplete':(empty item.qcData.approveDate && userCoApprover?'unapproved':'complete'))}"/>--%>
	<c:set var="compSection" value="${prefix}[${index}]" />
	<c:set var="itemDataStandard" value="${coDataStandard}" />
	<%@ include file="/catalog/msds/msdsfields.jsp" %>
	<%@ include file="/catalog/msds/nfpafields.jsp" %>
	<%@ include file="/catalog/msds/hmisfields.jsp" %>
	<%@ include file="/catalog/msds/phfields.jsp" %>
	<%@ include file="/catalog/msds/physicalstatefields.jsp" %>
	<%@ include file="/catalog/msds/densityfields.jsp" %>
	<%@ include file="/catalog/msds/flashpointfields.jsp" %>
	<%@ include file="/catalog/msds/boilingpointfields.jsp" %>
	<%@ include file="/catalog/msds/specificgravityfields.jsp" %>
	<%@ include file="/catalog/msds/vaporpressurefields.jsp" %>
	<%@ include file="/catalog/msds/solidsfields.jsp" %>
	<%@ include file="/catalog/msds/vocfields.jsp" %>
	<%@ include file="/catalog/msds/voclwesfields.jsp" %>
	<%@ include file="/catalog/msds/supplementalfields.jsp" %>
	<input type="hidden" id="co[${index}].qcStatus" name="co[${index}].qcStatus" value="${qcStatus}"/>
	
	<c:set var="coDataUpToStandard" value="${(item.msdsDataEntryStandard == coDataStandard)?true:false}" />
	<script type="text/javascript">
		var coDataEntryComplete = '${item.dataEntryComplete}';
		var coDataEntryStandard = "${coDataUpToStandard?'Up to Standard':'Not up to Standard'}";
		var coDataUpToStandard = ${coDataUpToStandard};
	</script>
	<input type="hidden" name="msds[${index}].saveCustomerOverride" id="msds[${index}].saveCustomerOverride" />
