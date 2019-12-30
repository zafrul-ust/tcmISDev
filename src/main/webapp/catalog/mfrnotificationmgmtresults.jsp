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

<script type="text/javascript" src="/js/catalog/mfrnotificationmgmtresults.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<title><fmt:message key="mfrNotificationMgmt"/></title>

<script language="JavaScript" type="text/javascript"/>
<!--
var resizeGridWithWindow = true;
var children = [];

with(milonic=new menuname("rightClickMenu")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="label.viewrequest"/>;url=javascript:viewNotificationRequest();");
}

drawMenus();

//add all the javascript messages here, this for internationalization of client side javascript messages

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
recordFound:'<fmt:message key="label.recordFound"/>',
searchDuration:'<fmt:message key="label.searchDuration"/>',
minutes:'<fmt:message key="label.minutes"/>',
seconds:'<fmt:message key="label.seconds"/>'
};


var gridConfig = {
	divName:'mfrNotificationMgmtBean', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'columnConfig',	     // the column config var name, 
	rowSpan:true,			 // this page has rowSpan > 1 or not.
	submitDefault:false,    // the fields in grid are defaulted to be submitted or not.,
	noSmartRender: false,
	singleClickEdit:true,
	selectChild: 1,
	onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:rightClickRow   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	//onBeforeSorting:_onBeforeSorting
  }; 



  
 <tcmis:inventoryGroupPermission indicator="true" userGroupId="ReceivingQC" inventoryGroup="${param.inventoryGroup}">
   <c:set var="receivingQcPermission" value='Yes'/>
 </tcmis:inventoryGroupPermission>

 
<c:if test="${!empty receivingQcViewRelationBeanCollection}">  
	<c:if test="${receivingQcPermission == 'Yes'}">
	    showUpdateLinks = true;
	 </c:if>
</c:if>

var qcAssignedTo = [
	{value: '', text: 'Unassigned'}
	<c:forEach var="approver" items="${chemicalApprovers}" varStatus="status">
		,{value: "${approver.personnelId}", text: "${approver.lastName}, ${approver.firstName}" }
	</c:forEach>
];

var columnConfig = [
{ columnId:"permission"},
{ columnId:"notificationId",columnName:'<fmt:message key="label.notificationrequest"/>',width:8,submit:true, align:"center"},
{ columnId:"status",columnName:'<fmt:message key="label.status"/>',width:10, align:"center"},
{ columnId:"category",columnName:'<fmt:message key="label.category"/>',width:20},
{ columnId:"comments", columnName:'<fmt:message key="label.comments"/>',width:20, tooltip:"Y"},
{ columnId:"qcAssignedTo",columnName:'<fmt:message key="label.approver"/>', type:'hcoro', width:15,submit:true, align:"center"},
{ columnId:"requester",columnName:'<fmt:message key="label.requestor"/>',width:10},
{ columnId:"dateSubmitted", columnName:'<fmt:message key="label.datesubmitted"/>', width:8},
{ columnId:"internalComments", columnName:'<fmt:message key="label.internalcomments"/>',width:20, tooltip:"Y"}
];

var rowSpanMap = new Array();
var rowSpanClassMap = new Array();	
var rowSpanCols = [0,1,2,5,6,7,8];

function sizeFrame()
{
     parent.document.getElementById("resultFrame").height = "400";
}
//-->
</script>
</head>
<body onload="resultOnLoad();">
	<tcmis:form action="/mfrnotificationmgmtresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
	${tcmISError}<br/>
	<c:forEach var="tcmisError" items="${tcmISErrors}">
		${tcmisError}<br/>
	</c:forEach>
</div>
<script type="text/javascript">
	<c:choose>
		<c:when test="${empty tcmISErrors and empty tcmISError}">
			<c:choose>
				<c:when test="${param.maxData == fn:length(mfrNotificationBeanCollection)}">
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
				<div id="mfrNotificationMgmtBean" style="width:100%;height:400px;" style="display:none;"></div>
				<c:if test="${mfrNotificationBeanCollection != null}">
				<script type="text/javascript">
					<c:set var="dataCount" value="${0}"/>
						<c:if test="${!empty mfrNotificationBeanCollection}" >
							var jsonMainData = new Array();
							jsonMainData = {
								rows:[
							<c:forEach var="bean" items="${mfrNotificationBeanCollection}" varStatus="status">
							<fmt:formatDate var="submitDate" value="${bean.dateSubmitted}" pattern="${dateFormatPattern}"/>
							
								<c:if test="${status.index > 0}">,</c:if>
								{ id:${status.index +1},<c:if test="${!empty colorClass}">"class":"${colorClass}",</c:if>
									data:[
									'Y',
									'${bean.notificationId}',
									'<tcmis:jsReplace value="${bean.status}" processMultiLines="false"/>',
									'<tcmis:jsReplace value="${bean.mfrReqCategoryDesc}" processMultiLines="false"/>',
									'<tcmis:jsReplace value="${bean.comments}" processMultiLines="true"/>',
									'<tcmis:jsReplace value="${bean.qcAssignedTo}" processMultiLines="false"/>',
									'<tcmis:jsReplace value="${bean.requester}" processMultiLines="false"/>',
									'${submitDate}',
									'<tcmis:jsReplace value="${bean.internalComments}" processMultiLines="true"/>'
									]
								}
								<c:set var="dataCount" value="${dataCount+1}"/>
							</c:forEach>
								]
							};
						<c:forEach var="bean" items="${mfrNotificationBeanCollection}" varStatus="status">
						<c:set var="currentItem" value="${bean.notificationId}" />
						<c:choose>
							<c:when test="${status.first}">
								<c:set var="rowSpanStart" value="0" />
								<c:set var="rowSpanCount" value="1" />
								rowSpanMap[0] = 1;
								rowSpanClassMap[0] = 1;
							</c:when>
							<c:when test="${currentItem == previousItem}">
								rowSpanMap[${rowSpanStart}]++;
								rowSpanMap[${status.index}] = 0;
								rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
							</c:when>
							<c:otherwise>
								<c:set var="rowSpanCount" value="${rowSpanCount + 1}" />
								<c:set var="rowSpanStart" value="${status.index}" />
								rowSpanMap[${status.index}] = 1;
								rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
							</c:otherwise>
						</c:choose>
						<c:set var="previousItem" value="${currentItem}" />
						</c:forEach>
					</c:if>
				</script>
				<!-- If the collection is empty say no data found -->
				<c:if test="${empty mfrNotificationBeanCollection}">
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
					<input name="totalLines" id="totalLines" type="hidden" value="${dataCount}"/>
					<input name="minHeight" id="minHeight" type="hidden" value="100"/>
					<input type="hidden" id="requester" name="requester" value="<tcmis:jsReplace value="${param.requester}" processMultiLines="false"/>"/>
					<input type="hidden" id="assignedTo" name="assignedTo" value="<tcmis:jsReplace value="${param.assignedTo}" processMultiLines="false"/>"/>
					<input type="hidden" id="selectedCategories" name="selectedCategories" value="<tcmis:jsReplace value="${param.selectedCategories}" processMultiLines="false"/>"/>
					<input type="hidden" id="draftStatus" name="draftStatus" value="<tcmis:jsReplace value="${param.draftStatus}" processMultiLines="false"/>"/>
					<input type="hidden" id="pendingApprovalStatus" name="pendingApprovalStatus" value="<tcmis:jsReplace value="${param.pendingApprovalStatus}" processMultiLines="false"/>"/>
					<input type="hidden" id="approvedStatus" name="approvedStatus" value="<tcmis:jsReplace value="${param.approvedStatus}" processMultiLines="false"/>"/>
					<input type="hidden" id="approvedWindow" name="approvedWindow" value="<tcmis:jsReplace value="${param.approvedWindow}" processMultiLines="false"/>"/>
					<input type="hidden" id="rejectedStatus" name="rejectedStatus" value="<tcmis:jsReplace value="${param.rejectedStatus}" processMultiLines="false"/>"/>
					<input type="hidden" id="searchWhat" name="searchWhat" value="<tcmis:jsReplace value="${param.searchWhat}" processMultiLines="false"/>"/>
					<input type="hidden" id="searchType" name="searchType" value="<tcmis:jsReplace value="${param.searchType}" processMultiLines="false"/>"/>
					<input type="hidden" id="searchText" name="searchText" value="<tcmis:jsReplace value="${param.searchText}" processMultiLines="false"/>"/>
				</div>
			</div>
			<!-- close of backgroundContent -->
		</div>
		<!-- close of interface -->
	</tcmis:form>
</body>
</html:html>