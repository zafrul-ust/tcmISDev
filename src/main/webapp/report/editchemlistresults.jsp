<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="expires" content="-1">
		<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
		
		<%@ include file="/common/locale.jsp" %>
		
		<tcmis:gridFontSizeCss /> <%-- Sets CSS based on the user's preffered font size and which application he is viewing --%>
	
		<script type="text/javascript" src="/js/common/formchek.js"></script>			<%-- Validation support --%>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
	
		<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script> <%-- This handles all the resizing of the page and frames --%>
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>

		<%-- Right Mouse Click (RMC) Menu support  --%>
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
		<%@ include file="/common/rightclickmenudata.jsp" %>
	
		<%-- Form popup Calendar support --%>
		<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
		<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
		<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
	
		<%-- Grid support --%>
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
		
		<link href="/css/demos.css" rel="stylesheet" type="text/css" />  

		<script type="text/javascript" src="/js/report/editchemlist.js"></script>
	
		<title>
			<fmt:message key="projectdocument.label.customerDisplay"/>
		</title>
		
		<%-- NOTE: The only javascript here rather than in a page specific js file is javascript that contains values from JSP --%>
		<script language="JavaScript" type="text/javascript">
			<!-- <%-- Use the InventoryGroupPermission taglib to determine whether the user has permission to see/use the update links for THIS inventory group --%>
		
			<%-- Standard var for Internationalized messages--%>
			var messagesData = new Array();
			messagesData = {
				alert:"<fmt:message key="label.alert"/>",
				and:"<fmt:message key="label.and"/>",
				recordFound:"<fmt:message key="label.recordFound"/>",
				searchDuration:"<fmt:message key="label.searchDuration"/>",
				minutes:"<fmt:message key="label.minutes"/>",
				seconds:"<fmt:message key="label.seconds"/>",
				customerreturnrequest:"<fmt:message key="customerreturnrequest.title"/>",
				mrallocationreport:"<fmt:message key="mrallocationreport.label.title"/>",
				cancelmrreason:"<fmt:message key="label.cancelmrreason"/>",
				validvalues:"<fmt:message key="label.validvalues"/>",
				open:"<fmt:message key="label.open"/>",
				submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};


			<%-- Initialize the RCMenus --%>
			drawMenus();

			<%-- Define the columns for the result grid --%>
			var columnConfig = [
			    {columnId:"permission"},
				{columnId:"select", columnName:'<fmt:message key="label.select"/>', width:4, type:'hchstatus',align:'center'},
				{columnId:"listName",columnName:'<fmt:message key="label.listname"/>',tooltip:"Y",width:20},
				{columnId:"listDescription",columnName:'<fmt:message key="label.listdescription"/>', tooltip:"Y", width:40},
				{columnId:"hasThreshold"},
				{columnId:"hasAmountThreshold"},
				{columnId:"listId"},
				{columnId:"responsibleParty",columnName:'<fmt:message key="label.responsibleparty"/>', tooltip:"Y", width:10},
				{columnId:"maintenanceSchedule",columnName:'<fmt:message key="label.maintenanceschedule"/>', tooltip:"Y", width:10}
			];

			<%-- Define the grid options--%>
			var gridConfig = {
				divName: 'displayOnlyGridDiv',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
				beanData: 'jsonMainData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
				beanGrid: 'mygrid',		<%--  variable to put the grid object in for later use --%>
				config: columnConfig,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
				rowSpan: false, 		<%--  true: this page has rowSpan > 1 or not, disables smartrendering & sorting --%>
				noSmartRender:true,
				onAfterHaasRenderRow:checkPreSelect
				};
					

			<%-- Check for passed error message that will require an error inline popup --%>
			<c:choose>
				<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
					  <c:choose>
						   <c:when test="${param.maxData == fn:length(searchResults)}">
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
	</head>

	<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">
		<tcmis:form action="/editchemlistresults.do" onsubmit="return submitFrameOnlyOnce();">
			<div id="errorMessagesAreaBody" style="display:none;">
    				<html:errors/>
    				${tcmISError}
    				<c:forEach var="tcmisError" items="${tcmISErrors}" >
  					${tcmisError}<br/>
    				</c:forEach>    
    				<c:if test="${param.maxData == fn:length(searchResults)}">
					  <fmt:message key="label.maxdata"> 
					   <fmt:param value="${param.maxData}"/>
					  </fmt:message>
		 			</c:if>  
			</div>
			

			<div class="interface" id="resultsPage">
				<div class="backGroundContent">
					<div id="displayOnlyGridDiv" style="width:100%;height:400px;display: none;"></div>
					<c:choose>
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
								<%-- Loop through the results and output each row, formatting the results as necessary 
								     use tcmis:jsReplace to escape special characters for ANY user entered text
								     use fmt:formatNumber to format numbers (existing patterns: unitpricecurrencyformat, totalcurrencyformat, oneDigitformat)
								     use mt:formatDate to format dates (existing patterns: dateFormatPattern, dateTimeFormatPattern)--%>
								<!--
								var jsonMainData = new Array();
								jsonMainData = {
									rows:[ <c:forEach var="row" items="${searchResults}" varStatus="status">
										{ id:${status.count}, <%-- use status.count rather than status.index because the grid requires a 1 based index rather than 0 based --%>
										<%--  Set the color to the rows according to their releaseStatus --%>
										 data:[
											 'Y',
											 false,
											 '<tcmis:jsReplace value="${row.listName}" processMultiLines="true" />',
											 '<tcmis:jsReplace value="${row.listDescription}" processMultiLines="true" />',
											 '${row.hasThreshold}',
											 '${row.hasAmountThreshold}',
											 '${row.listId}',
											 '<tcmis:jsReplace value="${row.responsibleParty}" processMultiLines="true" />',
											 '<tcmis:jsReplace value="${row.maintenanceSchedule}" processMultiLines="true" />'
											]}<c:if test="${!status.last}">,</c:if>
	 									</c:forEach>
									]};
								//-->   
							</script>
						</c:otherwise>
					</c:choose>
	
	
					<div id="hiddenElements" style="display: none;">    			
						<input name="totalLines" id="totalLines" value="<c:out value="${fn:length(searchResults)}"/>" type="hidden"/>
						<input name="minHeight" id="minHeight" type="hidden" value="100"/>	 
						 <input name="maxData" id="maxData" type="hidden" value="${param.maxData}">
						 <input name="callerId" id="callerId" type="hidden" value="${param.callerId}">
					</div>
				</div>
			</div>
		</tcmis:form>
	</body>
</html:html>