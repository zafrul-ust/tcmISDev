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
<script type="text/javascript" src="/js/hub/pickinggroupmgmtresults.js"></script>
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
<title>ThisIsATest</title>
<script language="JavaScript" type="text/javascript"/>
<!--
var messagesData = {
			recordFound:"<fmt:message key="label.recordFound"/>",
    		searchDuration:"<fmt:message key="label.searchDuration"/>",
    		minutes:"<fmt:message key="label.minutes"/>",
    		seconds:"<fmt:message key="label.seconds"/>",
            total:"<fmt:message key="label.total"/>",
            pickinggroupnameerror:"<fmt:message key="label.invalidpickinggroupname"/>",
            pickinggroupselecthub:"<fmt:message key="label.pickinggroupselecthub"/>"
};
var columnConfig = [
		{columnId:"permission"},
		{columnId:"pickingGroupId", columnName:'<fmt:message key="label.id"/>', width:4, align:'center'},
		{columnId:"hub", columnName:'<fmt:message key="label.hub"/>', width:6, align:'center'},
		{columnId:"pickingGroupName", columnName:'<fmt:message key="label.pickinggroup"/>', type:"hed", size:30, width:15},
		{columnId:"active", columnName:'<fmt:message key="label.active"/>', align:'center', type:'hchstatus', width:4},
		{columnId:"lastUpdated", columnName:'<fmt:message key="label.lastupdated"/>', width:10, submit:false},
		{columnId:"lastUpdatedBy"},
		{columnId:"lastUpdatedByName", columnName:'<fmt:message key="label.lastupdatedby"/>', width:10, submit:false}
];

var autoState = [
	{text:'<fmt:message key="label.none"/>',value:''}
	<c:forEach var="ps" items="${vvPickingStateColl}" varStatus="status">
		,{text:'<tcmis:jsReplace value="${ps.pickingStateDesc}" processMultiLines="false"/>',
				value:'<tcmis:jsReplace value="${ps.pickingState}" processMultiLines="false"/>'}
	</c:forEach>
];

var gridConfig = {
		divName:'pickingGroupData',
		beanData:'jsonMainData',
		beanGrid:'beanGrid',
		config:'columnConfig',
		rowSpan:false,
		noSmartRender: true,
		submitDefault:true,
		onAfterHaasRenderRow:setRowStatusColors
};
// -->
</script>
</head>
<body onload="resultOnLoad();">
	<tcmis:form action="/pickinggroupmgmtresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
	${tcmISError}<br/>
	<c:forEach var="tcmisError" items="${tcmISErrors}">
		${tcmisError}<br/>
	</c:forEach>
	<c:if test="${param.maxData == fn:length(pickingGroupColl)}">
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
				<c:when test="${param.maxData == fn:length(pickingGroupColl)}">
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
	//-->
</script>
		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<div id="pickingGroupData" style="width:100%;height:400px;" style="display:none;"></div>
				<c:if test="${pickingGroupColl != null}">
				<script type="text/javascript">
					<!--
					<c:set var="dataCount" value="${0}"/>
						<c:if test="${!empty pickingGroupColl}" >
							var jsonMainData = {
								rows:[<c:forEach var="bean" items="${pickingGroupColl}" varStatus="status">
									<c:set var="dataCount" value="${dataCount + 1}"/>
									<c:if test="${dataCount > 1}">,</c:if>
									<fmt:formatDate var="lastUpdated" value="${bean.lastUpdated}" pattern="${dateTimeFormatPattern}"/>
									{ 	id:${status.index +1},
										data:[	'Y',
											'${bean.pickingGroupId}',
											'<tcmis:jsReplace value="${bean.hub}" processMultiLines="false"/>',
											'<tcmis:jsReplace value="${bean.pickingGroupName}" processMultiLines="false" />',
										    ${bean.status eq "A"},
											'${lastUpdated}',
											'${bean.lastUpdatedBy}',
											'<tcmis:jsReplace value="${bean.lastUpdatedByName}" processMultiLines="false" />'
										]
									}
								</c:forEach>
								]};
						</c:if>
					//-->
				</script>
				<!-- If the collection is empty say no data found -->
				<c:if test="${empty pickingGroupColl}">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
						<tr>
							<td width="100%">
								<fmt:message key="main.nodatafound"/>
							</td>
						</tr>
					</table>
				</c:if>
				<!-- Search results end -->
				</c:if>
				<!-- Hidden Element start -->
				<div id="hiddenElements" style="display:none">
					<input type="hidden" name="uAction" id="uAction" value=""/>
					<input type="hidden" name="sourceHub" id="sourceHub" value="${param.sourceHub}"/>
					<input type="hidden" name="companyId" id="companyId" value="${hubCompanyId}"/>
					<input name="totalLines" id="totalLines" type="hidden" value="${dataCount}"/>
					<input name="activeOnly" id="activeOnly" type="hidden" value="${param.activeOnly}"/>
					<input name="minHeight" id="minHeight" type="hidden" value="100"/>
				</div>
			</div>
			<!-- close of backgroundContent -->
		</div>
		<!-- close of interface -->
	</tcmis:form>
</body>
</html:html>