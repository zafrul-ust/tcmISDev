
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>-->
    <%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)--%>
    <script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
    <%--This file has our custom sorting and other utility methos for the grid.--%>
    <script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
    
    <!-- Add any other javascript you need for the page here -->
    <script type="text/javascript" src="/js/common/admin/usergrouppermissionresults.js"></script>
    
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
				columnId:"userGroupAccessPermission"
			},
			{
				columnId:"userGroups",
				columnName:'<fmt:message key="label.usergroups"/>',
				width:37
			},
			{
				columnId:"userGroupAccess",
				columnName:'<fmt:message key="label.member"/><br><input type="checkbox" name="checkAll" id="checkAll" value="" onclick="doCheckAll()" disabled="true"/>',
				type:'hchstatus',
				align:'center',
				onChange:dataChanged,
				width:10
			},
			{
				columnId:"modified"
			},
			{
				columnId:"companyId"
			},
			{
				columnId:"facilityId"
			},
			{
				columnId:"personnelId"
			},
			{
				columnId:"userGroupId"
			}
		];
    </script>
</head>

<body bgcolor="#ffffff" onload="myResultOnload();">
	<tcmis:form action="/usergrouppermissionresults.do" onsubmit="return submitFrameOnlyOnce();">
		<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
		The default value of showUpdateLinks is false.-->
		<c:set var="isAdmin" value=""/>
		<tcmis:facilityPermission indicator="true" userGroupId="Administrator" facilityId="${facilityId}" companyId="${companyId}">
			<script type="text/javascript">
				showUpdateLinks = true;
				<c:set var="isAdmin" value="Yes"/>
				config[2].columnName = '<fmt:message key="label.member"/><br><input type="checkbox" name="checkAll" id="checkAll" value="" onclick="doCheckAll()"/>';
			</script>
		</tcmis:facilityPermission>
		
		<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
		So this is just used to feed the YUI pop-up in the main page.
		Similar divs would have to be built to show any other messages in a pop-up.-->
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
				<div id="userGroupAccessInputBean" style="width:100%;height:400px;" style="display: none;"></div>
				
				<script type="text/javascript">
					<c:if test="${aprovalUserGroupUserViewBeanCollection != null}" >
						<c:set var="showUpdateLink" value='N'/>
						<c:set var="approvalGroupMember" value=""/>
						
						<!-- Search results start -->						
						<c:if test="${!empty aprovalUserGroupUserViewBeanCollection}" >
							var jsonMainData = new Array();
							var jsonMainData = {
								rows:[
									<c:forEach var="userGroupAccessInputBean" items="${aprovalUserGroupUserViewBeanCollection}" varStatus="status">
										<c:if test="${status.index > 0}">,</c:if>
										
										<c:set var="userGroupAccessPermission" value='N'/>
										<c:set var="userGroupAccessChecked" value='false'/>
										
										<c:if test="${isAdmin == 'Yes'}" >
											<c:set var="userGroupAccessPermission" value='Y'/>
										</c:if>
										<c:if test="${status.current.approvalGroupMember == 'Y'}" >
											<c:set var="userGroupAccessChecked" value='true'/>
										</c:if>
										
										/* Please use this tag, tcmis:jsReplace, to escape special characters */
										<tcmis:jsReplace var="userDesc" value="${status.current.userGroupDesc}" processMultiLines="true" />
										
										/*The row ID needs to start with 1 per their design.*/
										{
											id:${status.index +1},
											data:[
												'${userGroupAccessPermission}',
												'${userDesc}',
												${userGroupAccessChecked},
												'',
												'${status.current.companyId}',
												'${status.current.facilityId}',
												'${status.current.personnelId}',
												'${status.current.userGroupId}'
											]
										}
									</c:forEach>
								]
							};
						</c:if>
						
						<!-- If the collection is empty say no data found -->
						<c:if test="${empty aprovalUserGroupUserViewBeanCollection}" >
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
								<tr>
									<td width="100%">
										<fmt:message key="main.nodatafound"/>
									</td>
								</tr>
							</table>
						</c:if>
					</c:if>
				</script>
				<!-- Search results end -->
				
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<input name="minHeight" id="minHeight" type="hidden" value="100"/>
					<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
					<tcmis:setHiddenFields beanName="searchInput"/>
				</div>
				<!-- Hidden elements end -->
			</div>
			<!-- close of backGroundContent -->
		</div>
		<!-- close of interface -->
	</tcmis:form>
</body>
</html:html>