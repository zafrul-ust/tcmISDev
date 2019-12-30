<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

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
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="expires" content="-1">
		<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
		
		<%@ include file="/common/locale.jsp" %> 						<%-- Sets locale --%>
		
		<tcmis:gridFontSizeCss /> <%-- Sets CSS based on the user's preffered font size and which application he is viewing --%>
	
		<script type="text/javascript" src="/js/common/formchek.js"></script>			<%-- Validation support --%>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
	
		<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script> <%-- This handles all the resizing of the page and frames --%>
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>

		<%-- Right Mouse Click (RMC) Menu support, keep in all pages  --%>
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
		<%@ include file="/common/rightclickmenudata.jsp" %>
	
		<%-- Form popup Calendar support --%>
		<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
		<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
		<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
		<script type="text/javascript" src="/js/calendar/date.js"></script>
	
		<!-- Popup window support -->
		<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>	
		
		<%-- Grid support --%>
		<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
		<%--Uncomment below if you are providing header menu to switch columns on/off--%>
		<%--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>--%>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

		<%-- Page specific javascript --%>
		<script type="text/javascript" src="/js/common/admin/editbulletinresults.js"></script>

		<title><fmt:message key="projectdocument.label.editbulletin"/>		<%-- Internationalized page title --%></title>

		<script language="JavaScript" type="text/javascript">
			<%-- Check for errors to display --%>
			<c:choose>
				<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
			     		showErrorMessage = false;
				</c:when>
				<c:otherwise>
					showErrorMessage = true;
				</c:otherwise>
			</c:choose>   
						
			with(milonic=new menuname("rightClickmenu")){
				 top="offset=2"
				 style = contextStyle;
				 margin=3
				 aI("text=<fmt:message key="label.clonemessage"/>;url=javascript:cloneMessage();");
				}

			drawMenus();
				
			var messagesData = new Array();
			messagesData = {alert:"<fmt:message key="label.alert"/>",
					and:"<fmt:message key="label.and"/>",
					validvalues:"<fmt:message key="label.validvalues"/>",
					submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
					recordFound:"<fmt:message key="label.recordFound"/>",
					searchDuration:"<fmt:message key="label.searchDuration"/>",
					minutes:"<fmt:message key="label.minutes"/>",
					seconds:"<fmt:message key="label.seconds"/>",
					ok:"<fmt:message key="label.ok"/>",
					messagetitle:"<fmt:message key="label.messagetitle"/>",
					messageexpirationdate:"<fmt:message key="label.messageexpirationdate"/>",
					messagesortorder:"<fmt:message key="label.messagesortorder"/>",
					messagetext:"<fmt:message key="label.messagetext"/>",
					maximum2000:"<fmt:message key="label.maximum2000"/>",				
					waitingFor:"<fmt:message key="label.waitingforinputfrom"/>",
					cloneMessage:"<fmt:message key="label.clonemessage"/>"
			};

			<%-- Define the columns for the result grid --%>
			var columnConfig = [ 
				{columnId:"permission" },	
				{columnId:"okDoUpdate"}, 
				{columnId:"addNew"}, 
				{columnId:"connectionPool", type:'hcoro', columnName:'<fmt:message key="label.connectionpool"/>', sorting:'haasCoro', tooltip:"Y", width:25},
				{columnId:"connectionPoolOrig"},
				{columnId:"messageSortOrder", type:'hed', columnName:'<fmt:message key="label.messagesortorder"/>', align:'right', sorting:'int', width:8, size:3, maxlength:5},
				{columnId:"messageSortOrderOrig"},
				{columnId:"messageTitle", type:'hed', columnName:'<fmt:message key="label.messagetitle"/>', sorting:'haasStr',  tooltip:"Y", width:30, size:55},
				{columnId:"messageTitleOrig"},
				{columnId:"messageExpirationDate", type:'hcal', columnName:'<fmt:message key="label.messageexpirationdate"/>', align:'center', hiddenSortingColumn:'hiddenDateSort', sorting:'int'},
				{columnId:"hiddenDateSort", sorting:'int'},
				{columnId:"messageExpirationDateOrig"},
				{columnId:"messageText", type:'txt', columnName:'<fmt:message key="label.messagetext"/>', sorting:'haasStr', tooltip:"Y", width:55},
				{columnId:"messageTextOrig"}
			]; 

			<%-- Define the grid options--%>
			var gridConfig = {
				divName: 'connPoolMsgBean',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
				beanData: 'jsonMainData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
				beanGrid: 'mygrid',		<%--  variable to put the grid object in for later use --%>
				config: columnConfig,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
				singleClickEdit: true,		<%--  Make TXT type field open edit on one click rahter than double click --%>
				variableHeight: true,		<%-- Wrap cell content and change the row height --%>
				rowSpan: false,	 		<%--  true: this page has rowSpan > 1 or not, disables smartrendering & sorting --%>
				onRowSelect: markUpdate, <%-- mark the row to update if selected --%>
				onRightClick: selectRightClick,
				submitDefault: true      <%-- submit all columns --%>			
			};
	
			<%--  This is the value array for type:'hcoro' pulldown 'companies'  --%>
			var connectionPool = new Array(
				<%-- Manually create an entry for TCM_OPS --%>
				{value:'', text:''},
				{value:'TCM_OPS', text:'<fmt:message key="label.haasinternal"/>'},
				<c:forEach var="company" items="${searchInput.companyCollection}" varStatus="status">
		 			{value:'${company.companyId}', text:'<tcmis:jsReplace value="${company.companyName}"/>'}
		 			<c:if test="${!status.last}">,</c:if>
	   			</c:forEach>
			);
			
			var jsonConnectionPoolData = {
					rows:[
					      {id:1,
					    	data:[ 'Y',
					    	       false,
					    	       'TCM_OPS',
					    	       '<fmt:message key="label.haasinternal"/>'
					    	       ]  
					      
					      },
						<c:forEach var="row" items="${searchInput.companyCollection}" varStatus="status">																	
								{ id:${status.count+1},										
								  data:[
								        'Y',
								        false,
								        '${row.companyId}',
										'<tcmis:jsReplace value="${row.companyName}"/>'
								  ]}<c:if test="${!status.last}">,</c:if>
							</c:forEach>
							]};
		</script>

	</head>

	<body bgcolor="#ffffff" onload="myResultOnLoadWithGrid(gridConfig);">
		<tcmis:form action="/editbulletinresults.do" onsubmit="return submitFrameOnlyOnce();">
			<div id="errorMessagesAreaBody" style="display:none;">
    			<html:errors/>
				${tcmISError}
				<c:forEach var="error" items="${tcmISErrors}" varStatus="status">
					${error}<br/>
				</c:forEach>        
			</div>
		
			<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
				<table width="100%" border="0" cellpadding="2" cellspacing="1">
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td align="center" id="transitLabel">
						</td>
					</tr>
					<tr>
						<td align="center">
							<img src="/images/rel_interstitial_loading.gif" align="middle">
						</td>
					</tr>
				</table>
			</div>
			<div class="interface" id="resultsPage">
				<div class="backGroundContent">
					<%-- This is where the grid will be inserted.  The div name is ALSO the name of the form values that will be sent to the server on update --%>
					<div id="connPoolMsgBean" style="width:100%;height:400px;" style="display: none;"></div>	
					<c:set var="showUpdateLinks" value='Y'/>
					<%-- Check row permission here --%>
					<c:set var="updatePermision" value='Y'/>
					<c:forEach var="opsEntityBean" items="${personnelBean.permissionBean.userGroupMemberOpsEntityBeanCollection}" varStatus="status">
					  <c:if test="${(status.current.userGroupId  == 'developers' || status.current.userGroupId  == 'operationsSupport')}">
					  	<c:set var="updatePermision" value='Y'/> 
					  </c:if>
					</c:forEach>
					<c:choose>
						<%-- If the collection is empty say no data found --%>
						<c:when test="${empty searchResults}">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
								<tr>
						       			<td width="100%">
						          			<fmt:message key="main.nodatafound"/>
						       			</td>
						    		</tr>
						  	</table>
						</c:when>
						<c:otherwise>
							<script type="text/javascript">
								var jsonMainData = {
									rows:[
									<c:forEach var="row" items="${searchResults}" varStatus="status">																	
										{ id:${status.count},										
										  data:[
											'${updatePermision}',
											false,
											false,
											'${row.connectionPool}',
											'${row.connectionPool}',
											'${row.messageSortOrder}',
											'${row.messageSortOrder}',
											'<tcmis:jsReplace value="${row.messageTitle}" processMultiLines="true" />',
											'<tcmis:jsReplace value="${row.messageTitle}" processMultiLines="true" />',
											'<fmt:formatDate value="${row.messageExpirationDate}" pattern="${dateFormatPattern}"/>',
											'${row.messageExpirationDate.time}',
											'<fmt:formatDate value="${row.messageExpirationDate}" pattern="${dateFormatPattern}"/>',
											'<tcmis:jsReplace value="${row.messageText}" processMultiLines="true" />',
											'<tcmis:jsReplace value="${row.messageText}" processMultiLines="true" />'
										  ]}<c:if test="${!status.last}">,</c:if>
	 								</c:forEach>
									]};
								 
							</script>
						</c:otherwise>
					</c:choose>
 
					<div id="hiddenElements" style="display: none;">    			
						<%-- Retrieve all the Search Criteria here for re-search after update--%>
						<tcmis:setHiddenFields beanName="searchInput"/>

						<!-- Popup Calendar input options for messageExpirationDate -->
						<input type="hidden" name="blockBefore_messageExpirationDate" id="blockBefore_messageExpirationDate"  value="<tcmis:getDateTag numberOfDaysFromToday="-1" datePattern="${dateFormatPattern}"/>"/>
						<input type="hidden" name="blockAfter_messageExpirationDate" id="blockAfter_messageExpirationDate" value=""/>
						<input type="hidden" name="blockBeforeExclude_messageExpirationDate" id="blockBeforeExclude_messageExpirationDate" value=""/>
						<input type="hidden" name="blockAfterExclude_messageExpirationDate" id="blockAfterExclude_messageExpirationDate" value=""/>
						<input type="hidden" name="inDefinite_messageExpirationDate" id="inDefinite_messageExpirationDate" value=""/>
						
						
						 
    						<input name="minHeight" id="minHeight" type="hidden" value="100">
					</div>
					<c:if test="${updatePermision == 'Y'}">
					    <script type="text/javascript">
					        <!--
					        showUpdateLinks = true;
					        //-->
					    </script>
					</c:if>
				</div>
			</div>


		</tcmis:form>
	</body>
</html:html>