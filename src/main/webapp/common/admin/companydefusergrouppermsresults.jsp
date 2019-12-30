
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

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

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

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/admin/companydefusergrouppermissionresults.js"></script>

<title>
<fmt:message key="label.usergroupperms"/>
</title>

<script language="JavaScript" type="text/javascript">
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData={
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
	
	var config = [
		{
			columnId:"checkedAdminDisplayPermission"
		},
		{
			columnId:"checkedPublishDisplayPermission"
		},
		{
			columnId:"checkedViewDisplayPermission"
		},
		{
			columnId:"userGroups",
			columnName:'<fmt:message key="label.usergroups"/>',
			width:32
		},
		{
			columnId:"checkedAdminDisplay",
			columnName:'<fmt:message key="label.admin"/><br><input type="checkbox" class="" id="checkAdminAllBox" value="" disabled="true"/>',
			type:'hchstatus',
			onChange:dataAdminChanged,
			align:'center',
			width:5
		},
		{
			columnId:"checkedPublishDisplay",
			columnName:'<fmt:message key="label.publish"/><br><input type="checkbox" class="" id="checkPublishAllBox" value="" disabled="true"/>',
			type:'hchstatus',
			onChange:dataPublishChanged,
			align:'center',
			width:5
		},
		{
			columnId:"checkedViewDisplay",
			columnName:'<fmt:message key="label.accessmember"/><br><input type="checkbox" class=""  id="checkViewAllBox" value="" disabled="true"/>',
			type:'hchstatus',
			onChange:dataViewChanged,
			align:'center',
			width:10
		},
		{
			columnId:"userGroupAdmin",
			columnName:''
		},
		{
			columnId:"userGroupPublish",
			columnName:''
		},
		{
			columnId:"userGroupAccess",
			columnName:''
		},
		{
			columnId:"modified",
			columnName:''
		},
		{
			columnId:"userGroupId",
			columnName:''
		},
		{
			columnId:"companyId",
			columnName:''
		},
		{
			columnId:"personnelId",
			columnName:''
		},
		{
			columnId:"userGroupType",
			columnName:''
		}
	];
</script>
</head>

<body bgcolor="#ffffff" onload="myResultOnload();">
	<tcmis:form action="/companydefusergrouppermissionresults.do" onsubmit="return submitFrameOnlyOnce();">
		<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
		The default value of showUpdateLinks is false.-->
		
		<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
		So this is just used to feed the YUI pop-up in the main page.Similar divs would have to be built to show any other messages in a pop-up.-->
		
		<!-- Error Messages Begins -->
		<div id="errorMessagesAreaBody" style="display:none;">
			<html:errors/>
			${tcmISError}
		</div>
		
		<script type="text/javascript">
			/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
			<c:choose>
				<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null and empty requestScope['tcmISError']}">
					showErrorMessage = false;
				</c:when>
				<c:otherwise>
					showErrorMessage = true;
				</c:otherwise>
			</c:choose>
		</script>
		<!-- Error Messages Ends -->
		
		<!-- start of interface -->
		<div class="interface" id="resultsPage" >
			<!-- start of backGroundContent -->
			<div class="backGroundContent" >
				<!-- Search results start -->
				<c:if test="${aprovalUserGroupUserViewBeanCollection != null}" >
					<c:set var="tmpUserGroupType" value="${param.userGroupType}"/>
					
					<div id="companydefusergrouppermsViewBean" style="width:100%;height:400px;" style="display: none;"></div>
					
					<script type="text/javascript">						
						<c:choose>
							<c:when test="${!empty aprovalUserGroupUserViewBeanCollection}" >
								var jsonMainData = new Array();
								var jsonMainData = {
									rows:[
										<c:set var="isAdminGroupCount" value="${0}"/>
										<c:set var="showUpdateLinks" value="false"/>
										<c:forEach var="usergrouppermsViewBean" items="${aprovalUserGroupUserViewBeanCollection}" varStatus="status">
											<c:if test="${status.index > 0}">,</c:if>
											<c:set var="userGroupAdminPermission" value='N'/>
											<c:set var="userGroupAdminChecked" value='false'/>
											<c:set var="userGroupPublishPermission" value='N'/>
											<c:set var="userGroupPublishChecked" value='false'/>
											<c:set var="userGroupViewPermission" value='N'/>
											<c:set var="userGroupViewChecked" value='false'/>
											
											<c:if test="${tmpUserGroupType == 'ReportPublish'}" >
												<c:forEach var="coDefUserGroupBean" items="${allUserGroupUserIsAdminCollection}" varStatus="status2">
													<c:if test="${coDefUserGroupBean.userGroupId == usergrouppermsViewBean.userGroupId}">
														<c:set var="userGroupAdminPermission" value='Y'/>
														<c:set var="userGroupPublishPermission" value='Y'/>
														<c:set var="userGroupViewPermission" value='Y'/>
														<c:set var="isAdminGroupCount" value="${isAdminGroupCount + 1}"/>
														<c:set var="showUpdateLinks" value="true"/>
													</c:if>
												</c:forEach>
											</c:if>
											
											<c:if test="${status.current.adminRole == 'Y'}" >
												<c:set var="userGroupAdminChecked" value='true'/>
											</c:if>
											<c:if test="${status.current.publishRole == 'Y'}" >
												<c:set var="userGroupPublishChecked" value='true'/>
											</c:if>
											<c:if test="${status.current.viewRole == 'Y'}" >
												<c:set var="userGroupViewChecked" value='true'/>
											</c:if>
											
											/* Please use this tag, tcmis:jsReplace, to escape special characters */
											<tcmis:jsReplace var="userDesc" value="${status.current.userGroupDesc}" processMultiLines="true" />
											
											/*The row ID needs to start with 1 per their design.*/
											{
												id:${status.index +1},
												data:[												
													'${userGroupAdminPermission}',
													'${userGroupPublishPermission}',
													'${userGroupViewPermission}',
													'${userDesc}',
													${userGroupAdminChecked},
													${userGroupPublishChecked},
													${userGroupViewChecked},
													'${usergrouppermsViewBean.adminRole}',
													'${usergrouppermsViewBean.publishRole}',
													'${usergrouppermsViewBean.viewRole}',
													'',
													'${usergrouppermsViewBean.userGroupId}',
													'${usergrouppermsViewBean.companyId}',
													'${usergrouppermsViewBean.personnelId}',
													'${usergrouppermsViewBean.userGroupType}'
												]
											}
										</c:forEach>
									]
								};
								showUpdateLinks = false;
								<c:if test="${showUpdateLinks == 'true'}">
									showUpdateLinks = true;
								</c:if>
								<%-- Logic to give change permission: 
									if there's at least one usergroup where user is admin is shown, turn on update links.
									if user is admin to all groups being shown, enable check all option.--%>
								<c:if test="${isAdminGroupCount == fn:length(aprovalUserGroupUserViewBeanCollection)}">
									config[4].columnName  = '<fmt:message key="label.admin"/><br><input type="checkbox" id="checkAdminAllBox" value="" onclick="checkAdminAll()"/>';
									config[5].columnName  = '<fmt:message key="label.publish"/><br><input type="checkbox" id="checkPublishAllBox" value="" onclick="checkPublishAll()"/>';
									config[6].columnName  = '<fmt:message key="label.accessmember"/><br><input type="checkbox" id="checkViewAllBox" value="" onclick="checkViewAll()"/>';
								</c:if>
							</c:when>
							<c:otherwise>
								<!-- If the collection is empty say no data found -->
								<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
									<tr>
										<td width="100%">
											<fmt:message key="main.nodatafound"/>
										</td>
									</tr>
								</table>
							</c:otherwise>
						</c:choose>
					</script>
				</c:if>
				<!-- Search results end -->
				
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<input type="hidden" name="action" id="action" value=""/>	
					<input type="hidden" name="totalLines" id="totalLines" value="${fn:length(aprovalUserGroupUserViewBeanCollection)}"/>
					<input type="hidden" name="userGroupType" id="userGroupType" value="${param.userGroupType}" />
					<input type="hidden" name="companyId" id="companyId" value="${param.companyId}" />
					<input type="hidden" name="personnelId" id="personnelId" value="${param.personnelId}" />
					<input name="minHeight" id="minHeight" type="hidden" value="100"/>
					<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
				</div>
				<!-- Hidden elements end -->
			<!-- close of backGroundContent -->
			</div>
		<!-- close of interface -->
		</div>
	</tcmis:form>
</body>
</html:html>