<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support for column type hcal-->
<!--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript" src="/js/common/admin/workareaPermission.js"></script>

<title>
</title>

<script language="JavaScript" type="text/javascript">
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData = {
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		recordFound:"<fmt:message key="label.recordFound"/>",
		searchDuration:"<fmt:message key="label.searchDuration"/>",
		minutes:"<fmt:message key="label.minutes"/>",
		seconds:"<fmt:message key="label.seconds"/>",
		validvalues:"<fmt:message key="label.validvalues"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		itemInteger:"<fmt:message key="error.item.integer"/>"
	};
	
	var gridConfig = {
		divName:'UserGroupAccessInputBean', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beangrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
		onRowSelect:null,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:null   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
		//onBeforeSorting:_onBeforeSorting
	};
		
	var config = [
		{
			columnId:"permission"
		},
		{
			columnId:"companyName",
			columnName:'<fmt:message key="label.company"/>',
			width:9,
			submit:true
		},
		{
			columnId:"facilityName",
			columnName:'<fmt:message key="label.facility"/>',
			width:9,
			submit:true
		},
		{
			columnId:"applicationDesc",
			columnName:'<fmt:message key="label.workarea"/>',
			width:13
		},
		{
			columnId:"newApplicationAccessPermission"
		},
		{ 
			columnId:"newApplicationAccess",
			columnName:'<fmt:message key="label.access"/>',
			align:'center',
			type:'hchstatus',
			onChange:accessChanged, 
			permission:true,
			submit:true
		},
		<%-- getting the display name from vvusergroup.properties --%>
		<c:set var="headerCount" value='0'/>
		<fmt:bundle basename="com.tcmis.common.resources.VvUserGroup">
			<c:forEach var="HeaderBean" items="${workareaPermissionsHeaderBeanCollection}">
				<c:set var="headerCount" value='${headerCount+1}'/>
				{
					columnId:"${HeaderBean.userGroupId}Permission"
				},
				{
					columnId:"${HeaderBean.userGroupId}",
					columnName:'<fmt:message key="${HeaderBean.userGroupId}"/>',
					type:'hchstatus',
					align:'center',
					onChange:permissionChanged,  
					permission:true
				},
			</c:forEach>
		</fmt:bundle>
		{
			columnId:"companyId",
			submit:true
		},
		{
			columnId:"facilityId",
			submit:true
		},
		{
			columnId:"application",
			submit:true
		},
		{
			columnId:"userGroupId",
			submit:true
		},
		{
			columnId:"oldApplicationAccess",
			submit:true
		},
		{
			columnId:"userGroupAccess",
			submit:true
		},
		{
			columnId:"modified",
			submit:true
		},
		{
			columnId:"updated",
			submit:true
		},
		{
			columnId:"totalPermissionChecked" //needed for permission logic
		}
	];
</script>
</head>

<body bgcolor="#ffffff" onload="myResultOnLoad();">
	<tcmis:form action="/workareapermissionresults.do" onsubmit="return submitFrameOnlyOnce();">
		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<c:set var="dataCount" value='${0}'/>
				<c:if test="${!empty ApplicationAdminBeanCollection}" >
					<div id="UserGroupAccessInputBean" style="width:100%;height:400px;"></div>
					<!-- Search results start -->
					<script type="text/javascript">
						/*This is to keep track of whether to show any update links.
						If the user has any update permisions for any row then we show update links.*/
						<c:set var="showUpdateLink" value='N'/>
						/*Storing the data to be displayed in a JSON object array.*/
						var jsonMainData = new Array();
						var jsonMainData = {
							rows:[
								<c:forEach var="bean" items="${ApplicationAdminBeanCollection}" varStatus="status">
									<c:if test="${status.index > 0}">,</c:if>
									<c:set var="readonly" value="N"/>
									<c:if test="${status.current.userAccess == 'Admin' || status.current.userAccess == 'Grant Admin'}">
										<c:set var="readonly" value="Y"/>
										<c:set var="adminCount" value='${1}'/>
									</c:if>
									{
										id:${status.index +1},
										<c:if test="${status.current.application  == 'All'}">'class':"grid_green",</c:if>
										data:[
										      'Y',
										      '${bean.companyName}',
										      '${bean.facilityName}',
										      '<tcmis:jsReplace value="${bean.applicationDesc}"/>',
										      <c:choose>
										      	<c:when test="${status.current.applicationAccess  == 'Y'}">
										      		'${readonly}',
										      		true,
										      	</c:when>
										      	<c:otherwise>
										      		'${readonly}',
										      		false,
										      	</c:otherwise>
										      </c:choose>
										      <c:set var="userGroupId" value=''/>
										      <c:set var="oldApplicationAccess" value=''/>
										      <c:set var="permissionCount" value='${0}'/>
										      <c:forEach var="contentBean" items="${status.current.userGroupAccess}" varStatus="statusContent">
										      	<c:set var="adminreadonly" value='N'/>
										      	<c:if test="${!(statusContent.current.userGroupId == 'xxDummyXX') && (status.current.userAccess == 'Grant Admin' || status.current.userAccess == 'Admin')}">
										      		<c:set var="adminreadonly" value='Y'/>
										      	</c:if>
										      	<c:set var="userGroupId" value='${userGroupId}${statusContent.current.userGroupId}='/>
										      	<c:set var="oldApplicationAccess" value='${oldApplicationAccess}${statusContent.current.userGroupAccess}='/>
										      	<c:choose>
										      		<c:when test="${statusContent.current.userGroupId == 'xxDummyXX'}">
										      			'${adminreadonly}',
										      			false,
										      		</c:when>
										      		<c:otherwise>
										      			<c:choose>
										      				<c:when test="${status.current.userGroupAccess[statusContent.index].userGroupAccess == '1'}">
											      				'${adminreadonly}',
											      				true,
											      				<c:set var="permissionCount" value='${permissionCount + 1}'/>
										      				</c:when>
										      				<c:otherwise>
											      				'${adminreadonly}',
											      				false,
										      				</c:otherwise>
										      			</c:choose>
										      		</c:otherwise>
										      	</c:choose>
										      </c:forEach>
										      '${bean.companyId}',
										      '${bean.facilityId}',
										      '${bean.application}',
										      '<tcmis:jsReplace value="${userGroupId}"/>',
										      '<tcmis:jsReplace value="${oldApplicationAccess}"/>',
										      '','','',
										      '${permissionCount}'
										]
									}
									<c:set var="dataCount" value='${dataCount+1}'/>
								</c:forEach>
						]};
					</script>
				</c:if>
			</div>
			<!-- end of grid div. -->

			<c:if test="${empty ApplicationAdminBeanCollection}" >
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
					<tr>
						<td width="100%">
							<fmt:message key="main.nodatafound"/>
						</td>
					</tr>
				</table>
			</c:if>

			<div id="hiddenElements" style="display: none;">
				<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
				<input name="uAction" id="uAction" value="" type="hidden"/>
				<input name="personnelId" id="personnelId" value="${param.personnelId}" type="hidden"/>
				<input name="companyId" id="companyId" value="${param.companyId}" type="hidden"/>
				<input name="fullName" id="fullName" value="${fullName}" type="hidden"/>
				<input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden"/>
				<input name="hide" id="hide" value="${param.hide}" type="hidden"/>
				<input name="headerCount" id="headerCount" value="${headerCount}" type="hidden"/>
				<input name="maxData" id="maxData" value="${param.maxData}" type="hidden"/>
			</div>
		</div>
		<!-- close of backGroundContent -->
	</tcmis:form>
	
	<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
	So this is just used to feed the pop-up in the main page.
	Similar divs would have to be built to show any other messages in a pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesAreaBody" style="display:none;">
		${tcmISError}<br/>
		<c:forEach var="tcmisError" items="${tcmISErrors}">
			${tcmisError}<br/>
		</c:forEach>
		<c:if test="${param.maxData <= fn:length(ApplicationAdminBeanCollection)}">
			<fmt:message key="label.maxdata">
				<fmt:param value="${param.maxData}"/>
			</fmt:message>
			&nbsp;<fmt:message key="label.clickcreateexcelforcompleteresult"/>
		</c:if>
	</div>
	
	<script type="text/javascript">
		/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
		<c:choose>
			<c:when test="${empty tcmISErrors and empty tcmISError}">
				showErrorMessage = false;
				<c:if test="${param.maxData <= fn:length(ApplicationAdminBeanCollection)}">
					showErrorMessage = true;
					parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
				</c:if>
			</c:when>
			<c:otherwise>
				parent.messagesData.errors = "<fmt:message key="label.errors"/>";
				showErrorMessage = true;
			</c:otherwise>
		</c:choose>
		
		<%-- permission stuff here --%>
		<c:if test="${adminCount > 0}">
			showUpdateLinks = true;
		</c:if>		
	</script>
</body>
</html:html>
