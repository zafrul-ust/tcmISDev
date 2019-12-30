
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

<c:set var="index" value="${empty index?param.component:index}" />

<script type="text/javascript">
<!--

var compositionConfig = [
   			{columnId:"permission"},
   			{columnId:"percentPermission", submit: false},
   			{columnId:"percentLowerPermission", submit: false},
   			{columnId:"percentUpperPermission", submit: false},
   			{columnId:"casNumber", columnName:'<fmt:message key="label.casnumber"/>', attachHeader:'<fmt:message key="label.casnum"/>', tooltip:true, type:"hed", width:8, align: 'right'},
   			{columnId:"casLookup", columnName:'#cspan', attachHeader:'<fmt:message key="label.lookup"/>', width:6, align:'center', submit: false},
   			{columnId:"msdsChemicalName", columnName:'<fmt:message key="label.msdschemicalname"/>', width:40, type:"hed", tooltip: true, maxlength: 160},
   			{columnId:"percent", columnName:'<fmt:message key="label.percent"/>', type:"hed", width: 5, maxlength: 10, onChange: msdsIndex.checkPercent, permission:true},
   			{columnId:"percentLower", columnName:'<fmt:message key="label.lowerpercent"/>', type:"hed", width: 5, maxlength: 10, onChange: msdsIndex.checkPercent, permission:true},
   			{columnId:"percentUpper", columnName:'<fmt:message key="label.upperpercent"/>', type:"hed", width: 5, maxlength: 10, onChange: msdsIndex.checkPercent, permission:true},
   			{columnId:"trace", columnName:'<fmt:message key="label.trace"/>', type:"hchstatus", width: 3, align:'center', onChange: msdsIndex.traceToggled},
   			{columnId:"sdsSectionId",columnName:'<fmt:message key="label.sdssection"/>', type:"hcoro", width: 21},
   			{columnId:"remark", columnName:'<fmt:message key="label.comment"/>', type:"hed", width: 60, maxlength: 60, tooltip: true}
  		];

var sdsSectionId = [
			{text:'',value:''}
			<c:forEach var="sdsSection" items="${sdsSectionColl}" varStatus="status">
				,{text:'${sdsSection.sdsSectionId}. ${sdsSection.description}',value:'${sdsSection.sdsSectionId}'}
			</c:forEach>
		];

var compositionGridConfig${index} = {
		divName:'compositionData${index}',
		beanData:'jsonCompositionData${index}',
		beanGrid:'compositionGrid${index}',
		config:'compositionConfig',
		rowSpan:false,
		noSmartRender: true,
		onRowSelect: msdsIndex.rowSelected,
		submitDefault:true
};

<c:set var="editable" value="${catalogQueueRow.status ne 'Pending Approval'}"/>
var jsonCompositionData${index} = {
		rows:[<c:forEach var="bean" items="${msds.compositionData}" varStatus="status2">
			{ 	id:${status2.index +1},
				data:[	"${editable?'Y':'N'}",
					'<c:choose><c:when test="${not editable || bean.trace}">N</c:when><c:otherwise>Y</c:otherwise></c:choose>',
					'<c:choose><c:when test="${not editable || bean.trace}">N</c:when><c:otherwise>Y</c:otherwise></c:choose>',
					'<c:choose><c:when test="${not editable || bean.trace}">N</c:when><c:otherwise>Y</c:otherwise></c:choose>',
					'${bean.casNumber}',
					'', //casLookup
					'<tcmis:jsReplace value="${bean.msdsChemicalName}" processMultiLines="true" />',
					'${bean.percent}',
					'${bean.percentLower}',
					'${bean.percentUpper}',
					${bean.trace == 'Y'?true:false},
					'${bean.sdsSectionId}',
					'<tcmis:jsReplace value="${bean.remark}" processMultiLines="true" />'
				]
			},
		</c:forEach>
			{ 	id: ${fn:length(msds.compositionData)+1},
				data:[	"${editable?'Y':'N'}",
				    "${editable?'Y':'N'}",
				    "${editable?'Y':'N'}",
				    "${editable?'Y':'N'}",
					'',
					'', 
					'',
					'',
					'',
					'',
					false,
					'3',
					''
				]
			}
		]};
var customerCompany = [<c:forEach var="coBean" items="${companies}" varStatus="status2">
	<c:if test="${status2.index > 0}">,</c:if>
	{companyId:'<tcmis:jsReplace value="${coBean.companyId}"/>',
	companyName:'<tcmis:jsReplace value="${coBean.companyName}"/>'}</c:forEach>
];

//-->
</script>
<c:set var="masterData" value="${not empty chemRequest}"/>
<c:set var="vendorTask" value="${not empty catalogQueueRow}"/>
<c:set var="maintenance" value="${not vendorTask && not masterData}"/>
<c:if test="${vendorTask}">
	<c:set var="vendorAssigned" value="${catalogQueueRow.status eq 'Assigned'}" />
	<c:set var="vendorQc" value="${catalogQueueRow.status eq 'Pending QC'}" />
	<c:set var="masterDataReview" value="${catalogQueueRow.status eq 'Pending Approval'}" />
	<c:set var="formerlyVendorTask" value="${catalogQueueRow.status eq 'Closed'}"/>
	<c:set var="sourcing" value="${catalogQueueRow.task eq 'SDS Sourcing'}" />
	<c:set var="authoring" value="${catalogQueueRow.task eq 'SDS Authoring'}" />
	<c:set var="indexing" value="${catalogQueueRow.task eq 'SDS Indexing'}" />
	<c:set var="userIsAssignee" value="${catalogQueueRow.assignedTo eq personnelBean.personnelId}"/>
	<c:set var="vendorCanSubmit" value="${vendorAssigned && userIsAssignee}"/>
	<c:set var="vendorCanApprove" value="${vendorQc && not userIsAssignee}"/>

</c:if>
<c:set var="readwriteRequest" value="${(vendorTask && (sourcing || authoring || indexing) && (vendorCanSubmit || vendorCanApprove)) || maintenance || masterData}"/>
<%-- set messages --%>
<fmt:message var="_aerosol" key="label.liquid/aerosol"/>
<fmt:message var="_air" key="label.air"/>
<fmt:message var="_basis" key="label.basis"/>
<fmt:message var="_boilingpoint" key="label.boilingpoint"/>
<fmt:message var="_cancel" key="label.cancel"/>
<fmt:message var="_cforcelsius" key="label.cforcelsius"/>
<fmt:message var="_chronic" key="label.chronic"/>
<fmt:message var="_clear" key="label.clear"/>
<fmt:message var="_comments" key="label.comments"/>
<fmt:message var="_cryogenicliquid" key="label.cryogenicliquid"/>
<fmt:message var="_density" key="label.density"/>
<fmt:message var="_detail" key="label.detail"/>
<fmt:message var="_detect" key="label.detect"/>
<fmt:message var="_edit" key="label.edit"/>
<fmt:message var="_errorType" key="label.msdsqcerrortype"/>
<fmt:message var="_estimate" key="label.estimate"/>
<fmt:message var="_fforfahrenheit" key="label.fforfahrenheit"/>
<fmt:message var="_finish" key="label.finish"/>
<fmt:message var="_flashpoint" key="label.flashpoint"/>
<fmt:message var="_flammability" key="label.flammability"/>
<fmt:message var="_gaslower" key="label.gaslower"/>
<fmt:message var="_health" key="label.health"/>
<fmt:message var="_hmis" key="label.hmis"/>
<fmt:message var="_id" key="label.id"/>
<fmt:message var="_liquid" key="label.liquid"/>
<fmt:message var="_lower" key="label.lower"/>
<fmt:message var="_manufacturer" key="label.manufacturer"/>
<fmt:message var="_maxcomestimate" key="label.maxcomestimate"/>
<fmt:message var="_method" key="label.method"/>
<fmt:message var="_msds" key="label.msds"/>
<fmt:message var="_na" key="label.na"/>
<fmt:message var="_nfpa" key="label.nfpa"/>
<fmt:message var="_no" key="label.no"/>
<fmt:message var="_noerror" key="label.none"/>
<fmt:message var="_notes" key="label.notes"/>
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

<c:if test="${ ! empty msds}">
	<c:set var="prefix" value="msds"/>
	<c:set var="item" value="${msds}"/>
	<c:set var="userApprover" value="${userApprover && item.qcData.submitBy ne personnelBean.personnelId}" />
	<c:set var="globalQcStatus" value="${empty item.qcData.insertDate?'na':(empty item.qcData.submitDate?'incomplete':( ! empty item.qcData.approveDate?'complete':(userApprover?'unapproved':'pendingQc')))}"/>
	<c:set var="qcStatus" value="${empty catalogQueueRow?globalQcStatus:'na'}" />
	<c:if test="${empty catalogQueueRow && not empty item.coData}">
		<c:set var="userCoApprover" value="${userCoApprover && item.coData.qcData.submitBy ne personnelBean.personnelId}" />
		<c:if test="${qcStatus eq 'na'}">
			<c:set var="qcStatus" value="${empty item.coData.qcData.insertDate?'na':(empty item.coData.qcData.submitDate?'incomplete':( ! empty item.coData.qcData.approveDate?'complete':(userCoApprover?'unapproved':'pendingQc')))}"/>
		</c:if>
	</c:if>
	<c:set var="compSection" value="${prefix}[${index}]" />
	<c:set var="showCustom" value="${not empty customerCompanies && (maintenance || masterData)}"/>
	<c:set var="columnWidth" value="${showCustom || sourcing || authoring?'thirdWidth':'halfWidth'}"/>
	
	<c:choose>
	<c:when test="${not indexing && not maintenance && (not masterData || vendorTask || masterDataReview || formerlyVendorTask)}">
	<div class="${columnWidth} column">
	<%@ include file="/catalog/msds/mfgfields.jsp" %>
	</div>
	<div class="${columnWidth} column">
	<%@ include file="/catalog/msds/materialfields.jsp" %>
	</div>
	<div class="${columnWidth} column">
	<%@ include file="/catalog/msds/msdsfields.jsp" %>
	</div>
	</c:when>
	<c:otherwise>
	<div id="msds[${index}].sdsIdentificationColumn" class="${columnWidth} column">
	<div class="sdsColumnHeader"><fmt:message key="label.identification"/></div>
	<%@ include file="/catalog/msds/mfgfields.jsp" %>
	<%@ include file="/catalog/msds/materialfields.jsp" %>
	<%@ include file="/catalog/msds/ghsfields.jsp" %>
	<%@ include file="/catalog/msds/hazardstatements.jsp" %>
	<%@ include file="/catalog/msds/precautionarystatements.jsp" %>
	<%@ include file="/catalog/msds/ghspictograms.jsp" %>
	</div>
	<div id="msds[${index}].propertiesColumn" class="${columnWidth} column">
	<div class="sdsColumnHeader"><fmt:message key="label.sdsproperties"/></div>
	<c:set var="itemDataStandard" value="${globalDataStandard}" />
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
	<input type="hidden" id="msds[${index}].qcStatus" name="msds[${index}].qcStatus" value="${qcStatus}"/>
	</div>
	<c:if test="${showCustom}">
	<div id="co[${index}].propertiesColumn" class="${columnWidth} column">
	<jsp:include page="/client/catalog/msdsindexingcomsdsdetail.jsp" />
	</div>
	</c:if>

	<div id="msds[${index}].remarksColumn" class="halfWidth column">
		<label class="optionTitleLeft">${_comments}:</label><br/>
			<input class="inputBox" type="text" name="msds[${index}].remark" id="msds[${index}].remark" value="<c:out value="${msds.remark}"/>" onKeyUp="msdsIndex.limitText(this,300);" style="width:96%"/>
	</div>
	<div id="msds[${index}].altDataSrcColumn" class="halfWidth column">
		<label class="optionTitleRight">Alt. Data Source:</label><br/>
			<textarea name="msds[${index}].altDataSource" id="msds[${index}].altDataSource" rows="2" style="width:96%"class="inputBox" onKeyUp="msdsIndex.limitText(this,200);"><c:out value="${msds.altDataSource}"/></textarea><br/>
                </div>
	<p style="clear:both"></p>
	</c:otherwise>
	</c:choose>
	
	<%-- Composition --%>
	<c:if test="${indexing || maintenance || (masterData && not vendorTask && not masterDataReview && not formerlyVendorTask)}">
	<div id="msds[${index}].compositionColumn" class="fullWidth column">
	<div class="sdsColumnHeader"><fmt:message key="label.composition"/></div>
		<c:if test="${masterData || maintenance || (vendorTask && indexing && (vendorCanSubmit || vendorCanApprove))}">
		<div class="optionTitleBold"><%-- boxhead Begins --%>
			<div style="display:inline-block;">
				<a href="javascript:msdsIndex.addCompositionRow()"><fmt:message key="catalogspec.label.newrow"/></a>
			</div>
		</div>
		</c:if>
		<div id="compositionData${index}" style="height:400px;width:100%;"></div>
		<c:set var="qcStatus" value="${globalQcStatus eq 'na'?qcStatus:globalQcStatus}" />
		<div id="msds[${index}].compositionQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="msds[${index}].compositionQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="msds[${index}].compositionQcErrorType" id="msds[${index}].compositionQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="compErrorType" value="${empty msds.qcData.compositionQcErrorType?'':msds.qcData.compositionQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq compErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
	</div>
	</c:if>
	<input type="hidden" id="msds[${index}].msdsId" name="msds[${index}].msdsId" value="${msds.msdsId}"/>
</c:if>