<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
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
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut-icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<%-- For Internationalization, copies data from calendarval.js --%>
<%@ include file="/common/locale.jsp" %>
<tcmis:gridFontSizeCss />
<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles which key press events are disabled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>
<script type="text/javascript" src="/js/hub/storagerulesresults.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<title><fmt:message key="hubStorageRules"/></title>
<script language="JavaScript" type="text/javascript"/>
<!--
var messagesData = {
			recordFound:"<fmt:message key="label.recordFound"/>",
    		searchDuration:"<fmt:message key="label.searchDuration"/>",
    		minutes:"<fmt:message key="label.minutes"/>",
    		seconds:"<fmt:message key="label.seconds"/>",
            total:"<fmt:message key="label.total"/>",
			pleaseSelect:"<fmt:message key="error.norowselected"/>",
			none:"<fmt:message key="label.none"/>",
			positiveError:"<fmt:message key="label.positivenumber"/>",
			minSize:"<fmt:message key="label.minsize"/>",
			maxSize:"<fmt:message key="label.maxsize"/>",
			minMaxError:"<fmt:message key="label.minmaxsize"/>",
			minMaxDetectError:"<fmt:message key="label.minmaxdetectrequired"/>",
			storageFamilyError:"<fmt:message key="label.storagefamilyrequired"/>",
			order:"<fmt:message key="label.order"/>",
			reordering:"<fmt:message key="label.reordering"/>",
			reorderingWarning:"<fmt:message key="label.reordernotapplied"/>",
			updateError:"<fmt:message key="label.encounterederror"/>",
			move:"<fmt:message key="label.move"/>"
};

var columnConfig = [
{ columnId:"permission"
},
{ columnId: "updated", submit: true
},
{ columnId: "delete",
  columnName:'<fmt:message key="label.delete"/>',
  width: 4,
  align: "center",
  type:"hch",
  submit:true
},
{ columnId:"ruleOrder",
  columnName:'<fmt:message key="label.ruleorder"/>',
  width:4,
  align:"center",
  submit:true,
  sorting:"int"
},
{ columnId:"moveTo",
  columnName:'<fmt:message key="label.ordering"/>',
  attachHeader:'<fmt:message key="label.order"/>',
  width:5,
  size: 4,
  align:"center",
  type:"hed"
},
{ columnId:"mover",
  columnName:"#cspan",
  attachHeader:'<fmt:message key="label.controls"/>',
  width:8,
  align:"center"
},
{ columnId:"wmsFlammable",
  columnName:'<fmt:message key="label.flammable"/>',
  width:10,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"wmsWaterMiscible",
  columnName:'<fmt:message key="label.watermiscible"/>',
  width:5,
  type:"hcoro",
  align:'center',
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"wmsAerosol",
  columnName:'<fmt:message key="label.aerosol"/>',
  width:7,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"wmsCorrosive",
  columnName:'<fmt:message key="label.corrosive"/>',
  width:8,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"wmsAcidBase",
  columnName:'<fmt:message key="label.acidbase"/>',
  width:6,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"wmsToxic",
  columnName:'<fmt:message key="label.toxic"/>',
  width:9,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"wmsOxidizer",
  columnName:'<fmt:message key="label.oxidizer"/>',
  width:7,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"wmsReactive",
  columnName:'<fmt:message key="label.reactive"/>',
  width:7,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"wmsOrganicPeroxide",
  columnName:'<fmt:message key="label.organicperoxide"/>',
  width:13,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"wmsWaterReactive",
  columnName:'<fmt:message key="label.waterreactive"/>',
  width:6,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"wmsPyrophoric",
  columnName:'<fmt:message key="label.pyrophoric"/>',
  width:6,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"wmsGas",
  columnName:'<fmt:message key="label.gas"/>',
  width:6,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"wmsContainer",
  columnName:'<fmt:message key="label.containermaterial"/>',
  width:8,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"wmsPressureRelieving",
  columnName:'<fmt:message key="label.containerpressure"/>',
  width:5,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"ibc",
  columnName:'<fmt:message key="label.ibc"/>',
  width:5,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"detectMinSize",
  columnName:'<fmt:message key="label.detectminsize"/>',
  width:6,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"minSize",
  columnName:'<fmt:message key="label.minsize"/>',
  width:5,
  size: 5,
  type:"hed",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"detectMaxSize",
  columnName:'<fmt:message key="label.detectmaxsize"/>',
  width:6,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"maxSize",
  columnName:'<fmt:message key="label.maxsize"/>',
  width:5,
  size:5,
  type:"hed",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"sizeUnit",
  columnName:'<fmt:message key="label.units"/>',
  width:6,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"wmsStorageTemp",
  columnName:'<fmt:message key="label.storagetemp"/>',
  width:10,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"storageFamily",
  columnName:'<fmt:message key="label.storagefamily"/>',
  width:10,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"altStorageFamily",
  columnName:'<fmt:message key="label.altstoragefamily"/>',
  width:10,
  type:"hcoro",
  align:"center",
  submit:true,
  onChange: rowFieldChanged
},
{ columnId:"branchPlant", submit:true
},
{ columnId:"hubStorageRuleId", submit: true}
];

var wmsAcidBase = [
	{text:'', value:''}
	<c:forEach var="acidBase" items="${acidBaseChoices}" varStatus="status">
		<tcmis:jsReplace var="acidBaseJs" value="${acidBase}" processMultiLines="false"/>
		,{text:'${acidBaseJs}', value:'${acidBaseJs}'}
	</c:forEach>
];

var wmsFlammable = [
	{text:'', value:''}
	<c:forEach var="flammable" items="${flammableChoices}" varStatus="status">
		<tcmis:jsReplace var="flammableJs" value="${flammable}" processMultiLines="false"/>
		,{text:'${flammableJs}', value:'${flammableJs}'}
	</c:forEach>
];

var wmsAerosol = [
	{text:'', value:''}
	<c:forEach var="aerosol" items="${aerosolChoices}" varStatus="status">
		<tcmis:jsReplace var="aerosolJs" value="${aerosol}" processMultiLines="false"/>
		,{text:'${aerosolJs}', value:'${aerosolJs}'}
	</c:forEach>
];

var wmsCorrosive = [
	{text:'', value:''}
	<c:forEach var="corrosive" items="${corrosiveChoices}" varStatus="status">
		<tcmis:jsReplace var="corrosiveJs" value="${corrosive}" processMultiLines="false"/>
		,{text:'${corrosiveJs}', value:'${corrosiveJs}'}
	</c:forEach>
];

var wmsToxic = [
	{text:'', value:''}
	<c:forEach var="toxic" items="${toxicChoices}" varStatus="status">
		<tcmis:jsReplace var="toxicJs" value="${toxic}" processMultiLines="false"/>
		,{text:'${toxicJs}', value:'${toxicJs}'}
	</c:forEach>
];

var wmsOxidizer = [
	{text:'', value:''}
	<c:forEach var="oxidizer" items="${oxidizerChoices}" varStatus="status">
		<tcmis:jsReplace var="oxidizerJs" value="${oxidizer}" processMultiLines="false"/>
		,{text:'${oxidizerJs}', value:'${oxidizerJs}'}
	</c:forEach>
];

var wmsReactive = [
	{text:'', value:''}
	<c:forEach var="reactive" items="${reactiveChoices}" varStatus="status">
		<tcmis:jsReplace var="reactiveJs" value="${reactive}" processMultiLines="false"/>
		,{text:'${reactiveJs}', value:'${reactiveJs}'}
	</c:forEach>
];

var wmsWaterReactive = [
	{text:'', value:''}
	<c:forEach var="waterReactive" items="${waterReactiveChoices}" varStatus="status">
		<tcmis:jsReplace var="waterReactiveJs" value="${waterReactive}" processMultiLines="false"/>
		,{text:'${waterReactiveJs}', value:'${waterReactiveJs}'}
	</c:forEach>
];

var wmsOrganicPeroxide = [
	{text:'', value:''}
	<c:forEach var="organicPeroxide" items="${organicPeroxideChoices}" varStatus="status">
		<tcmis:jsReplace var="organicPeroxideJs" value="${organicPeroxide}" processMultiLines="false"/>
		,{text:'${organicPeroxideJs}', value:'${organicPeroxideJs}'}
	</c:forEach>
];

var wmsGas = [
	{text:'', value:''}
	<c:forEach var="gas" items="${gasChoices}" varStatus="status">
		<tcmis:jsReplace var="gasJs" value="${gas}" processMultiLines="false"/>
		,{text:'${gasJs}', value:'${gasJs}'}
	</c:forEach>
];

var wmsContainer = [
	{text:'', value:''}
	<c:forEach var="container" items="${containerChoices}" varStatus="status">
		<tcmis:jsReplace var="containerJs" value="${container}" processMultiLines="false"/>
		,{text:'${containerJs}', value:'${containerJs}'}
	</c:forEach>
];

var wmsWaterMiscible = [
	{text:'', value:''},
	{text:'Y', value:'Y'},
	{text:'N', value:'N'}
];

var wmsPyrophoric = [
	{text:'', value:''},
	{text:'Y', value:'Y'},
	{text:'N', value:'N'}
];

var wmsPressureRelieving = [
	{text:'', value:''},
	{text:'Y', value:'Y'},
	{text:'N', value:'N'}
];

var ibc = [
	{text:'', value:''},
	{text:'Y', value:'Y'},
	{text:'N', value:'N'}
];

var detectMinSize = [
	{text:'', value:''},
	{text:'>', value:'>'},
	{text:'>=', value:'>='}
];

var detectMaxSize = [
	{text:'', value:''},
	{text:'<', value:'<'},
	{text:'<=', value:'<='}
];

var sizeUnit = [
	{text:'', value:''}
	<c:forEach var="units" items="${sizeUnitChoices}" varStatus="status">
		<tcmis:jsReplace var="unitsJs" value="${units}" processMultiLines="false"/>
		,{text:'${unitsJs}', value:'${unitsJs}'}
	</c:forEach>
];
	
var wmsStorageTemp = [
	{text:'', value:''}
	<c:forEach var="storageTemp" items="${storageTempChoices}" varStatus="status">
		<tcmis:jsReplace var="storageTempJs" value="${storageTemp}" processMultiLines="false"/>
		,{text:'${storageTempJs}', value:'${storageTempJs}'}
	</c:forEach>
];

<fmt:message var="pleaseSelect" key="label.pleaseselect" />
var storageFamily = [
	{text:'${pleaseSelect}', value:''}
	<c:forEach var="storageFamily" items="${storageFamilyChoices}" varStatus="status">
		<tcmis:jsReplace var="storageFamilyJs" value="${storageFamily}" processMultiLines="false"/>
		,{text:'${storageFamilyJs}', value:'${storageFamilyJs}'}
	</c:forEach>
];

var altStorageFamily = [
	{text:'', value:''}
	<c:forEach var="storageFamily" items="${storageFamilyChoices}" varStatus="status">
		<tcmis:jsReplace var="storageFamilyJs" value="${storageFamily}" processMultiLines="false"/>
		,{text:'${storageFamilyJs}', value:'${storageFamilyJs}'}
	</c:forEach>
];

var gridConfig = {
		divName:'storageRulesData',
		beanData:'jsonMainData',
		beanGrid:'beangrid',
		config:'columnConfig',
		rowSpan:false,
		noSmartRender: false,
		submitDefault:false
};


var shippingOverridePermission = false;
<tcmis:facilityPermission indicator="true" userGroupId="overrideShippingInformation">
	shippingOverridePermission = true;
</tcmis:facilityPermission>
// -->
</script>
</head>
<body onload="myonload();" onunload="closeAllchildren();">
	<tcmis:form action="/storagerulesresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
	${tcmISError}<br/>
	<c:forEach var="tcmisError" items="${tcmISErrors}">
		${tcmisError}<br/>
	</c:forEach>
	<c:if test="${param.maxData == fn:length(storageRules)}">
		<fmt:message key="label.maxdata">
			<fmt:param value="${param.maxData}"/>
		</fmt:message>
		&nbsp;<fmt:message key="label.clickcreateexcelforcompleteresult"/>
	</c:if>
</div>
<script type="text/javascript">
	<c:choose>
		<c:when test="${empty tcmISErrors and empty tcmISError}">
			<c:choose>
				<c:when test="${param.maxData == fn:length(storageRules)}">
					showErrorMessage = true;
					parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
				</c:when>
				<c:otherwise>
					showErrorMessage = false;
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			parent.messagesData.errors = "<fmt:message key="label.errors"/>";
			showErrorMessage = true;
		</c:otherwise>
	</c:choose>
	//-->l
</script>
		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<div id="storageRulesData" style="width:100%;height:400px;" style="display:none;"></div>
				<c:if test="${storageRules != null}">
				<script type="text/javascript">
					<!--
					<c:set var="dataCount" value="${0}"/>
						<c:if test="${!empty storageRules}" >
							var jsonMainData = {
								rows:[]};
							<c:forEach var="rule" items="${storageRules}" varStatus="status">
							<c:set var="updated" value="${status.count eq rule.ruleOrder?'N':'Y'}" />
							<c:set var="dataCount" value="${dataCount + 1}"/>
							jsonMainData.rows[${status.index}]={
								id:${status.count},
								data:[	'Y',
										'${updated}',
										'',
								      	'${status.count}',
								      	'',
								      	'',
								      	'${rule.wmsFlammable}',
								      	'${rule.wmsWaterMiscible}',
								      	'${rule.wmsAerosol}',
								      	'${rule.wmsCorrosive}',
								      	'${rule.wmsAcidBase}',
								      	'${rule.wmsToxic}',
								      	'${rule.wmsOxidizer}',
								      	'${rule.wmsReactive}',
								      	'${rule.wmsOrganicPeroxide}',
								      	'${rule.wmsWaterReactive}',
								      	'${rule.wmsPyrophoric}',
								      	'${rule.wmsGas}',
								      	'${rule.wmsContainer}',
								      	'${rule.wmsPressureRelieving}',
								      	'${rule.ibc}',
								      	'${rule.detectMinSize}',
								      	'${rule.minSize}',
								      	'${rule.detectMaxSize}',
								      	'${rule.maxSize}',
								      	'${rule.sizeUnit}',
								      	'${rule.wmsStorageTemp}',
								      	'${rule.storageFamily}',
								      	'${rule.altStorageFamily}',
								      	'${rule.branchPlant}',
								      	'${rule.hubStorageRuleId}'
								]
							};
							</c:forEach>
						</c:if>
					//-->
				</script>
				<!-- If the collection is empty say no data found -->
				<c:if test="${empty storageRules}">
					<c:set var="dataCount" value="${1}"/>
					<script type="text/javascript">
					<!--
						var jsonMainData = {
								rows:[
									{
										id:${1},
										data:[	'Y',
												'Y',
												'',
										      	'${1}',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'',
										      	'<tcmis:jsReplace value="${param.branchPlant}" processMultiLines="false"/>',
										      	''
										]
									}
								]};
					//-->
					</script>
				</c:if>
				
				<!-- Search results end -->
				</c:if>
				<!-- Hidden Element start -->
				<div id="hiddenElements" style="display:none">
					<input type="hidden" name="userAction" id="userAction" value=""/>
					<input name="totalLines" id="totalLines" type="hidden" value="${dataCount}"/>
					<input name="minHeight" id="minHeight" type="hidden" value="100"/>
					<input name="branchPlant" id="branchPlant" type="hidden" value="<tcmis:jsReplace value="${param.branchPlant}" processMultiLines="false"/>"/>
				</div>
			</div>
			<!-- close of backgroundContent -->
		</div>
		<!-- close of interface -->
	</tcmis:form>
</body>
</html:html>