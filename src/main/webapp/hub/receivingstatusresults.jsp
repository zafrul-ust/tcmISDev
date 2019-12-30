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
		
		<%-- Grid support --%>
		<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

		<%-- Page specific javascript --%>
		<script type="text/javascript" src="/js/hub/receivingstatusresults.js"></script>
	
		<title>
			<fmt:message key="projectdocument.label.customerDisplay"/>
		</title>
		
		<%-- NOTE: The only javascript here rather than in a page specific js file is javascript that contains values from JSP --%>
		<script language="JavaScript" type="text/javascript">
   	 		showUpdateLinks = true;
		
			<%-- Standard var for Internationalized messages--%>
			var messagesData = new Array();
			messagesData = {
				alert:"<fmt:message key="label.alert"/>",
				and:"<fmt:message key="label.and"/>",
				recordFound:"<fmt:message key="label.recordFound"/>",
				searchDuration:"<fmt:message key="label.searchDuration"/>",
				minutes:"<fmt:message key="label.minutes"/>",
				seconds:"<fmt:message key="label.seconds"/>",
				submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
	
			<%-- Define the right click menus --%>
			with(milonic=new menuname("rightClickMenu")){
				top="offset=2"
				style = contextStyle;
				margin=3
				aI("text=<fmt:message key="receivingQcCheckList"/>;url=javascript:openChecklist();");
			}

			<%-- Initialize the RCMenus --%>
			drawMenus();

			<%-- Setup the pulldowns for the array --%>
			var assignedTo = new Array();
			
			<%-- Define the columns for the result grid --%>
			var columnConfig = [
				<c:if test="${param.receivingStatus == 'QC Ready'}">{columnId:"permission"},</c:if>
				{columnId:"status",columnName:'<fmt:message key="label.status"/>'},
				<c:if test="${param.receivingStatus == 'QC Ready'}">{columnId:"assignedTo",columnName:'<fmt:message key="label.assignedto"/>', type: 'hcoro', tooltip:"Y", width:7, submit: true, onChange : touchUpdated},</c:if>
				{columnId:"visualReceiptId",columnName:'<fmt:message key="label.receipt"/>', tooltip:"Y", width:7},
				{columnId:"item", columnName:'<fmt:message key="label.item"/>', width:7, sorting:'int'},
 				{columnId:"itemDesc", columnName:'<fmt:message key="label.itemdescription"/>',  width:20, tooltip: 'Y'},
				{columnId:"quantity", columnName:'<fmt:message key="label.quantity"/>', sorting:'int', width:4 },
				{columnId:"bin", columnName:'<fmt:message key="label.bin"/>', width:4 },
				{columnId:"transaction", columnName:'<fmt:message key="label.transactionnumber"/>', width:8 },
				{columnId:"supplier", columnName:'<fmt:message key="label.supplier"/>', width:15, tooltip: 'Y'},
				{columnId:"carrier", columnName:'<fmt:message key="label.carrier"/>', width:10, tooltip: 'Y'},
				{columnId:"trackingNumber", columnName:'<fmt:message key="label.trackingnumber"/>', width:20, tooltip: 'Y'},
				{columnId:"inventoryGroup", columnName:'<fmt:message key="label.inventorygroup"/>', width:10, tooltip: 'Y'},
				{columnId:"arrivalScanDate", columnName:'<fmt:message key="label.arrivalscandate"/>', hiddenSortingColumn:'hiddenArrivalScanDate', sorting:'int', width: 12},
				{columnId:"hiddenArrivalScanDate", sorting:'int' },
				{columnId:"statusDate", columnName:'<fmt:message key="label.statusdate"/>', hiddenSortingColumn:'hiddenStatusDate', sorting:'int', width: 12},
				{columnId:"hiddenStatusDate", sorting:'int' },
				{columnId:"internalReceiptNote", columnName:'<fmt:message key="label.internalreceiptnotes"/>', tooltip:"Y", width:20 },
				{columnId:"receivingNote", columnName:'<fmt:message key="label.receivingnotes"/>', tooltip:"Y", width:20 },
				{columnId:"trackingNote", columnName:'<fmt:message key="label.trackingnotes"/>', tooltip:"Y", width:20 },
                {columnId:"receiptId", submit: true},
                {columnId:"updated", submit: true}
            ];

			<%-- Define the grid options--%>
			var gridConfig = {
				divName: 'receivingStatusDisplay',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
				beanData: 'jsonMainData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
				beanGrid: 'mygrid',		<%--  variable to put the grid object in for later use --%>
				config: columnConfig,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
				rowSpan: false, 		<%--  true: this page has rowSpan > 1 or not, disables smartrendering & sorting --%>
				onRightClick: onRightclick	<%--  a javascript function to be called on right click with rowId & cellId as args --%>
			};
					

			<%-- Check for passed error message that will require an error inline popup --%>
			<c:choose>
				<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
					showErrorMessage = false;
				</c:when>
				<c:otherwise>
					showErrorMessage = true;
				</c:otherwise>
			</c:choose>   
	
			parent.setLegendArea(<c:choose><c:when test="${param.receivingStatus == 'QC Complete'}">"showLegendAreaBinning", 100</c:when><c:otherwise>"showLegendArea", 225</c:otherwise></c:choose>);

			<c:choose>
				<c:when test="${param.receivingStatus == 'QC Ready'}">
					parent.document.getElementById("updateAssigneeLink").style["display"] = "";
				</c:when>
				<c:otherwise>
					parent.document.getElementById("updateAssigneeLink").style["display"] = "none";
				</c:otherwise>
			</c:choose>
			//-->
		</script>
	</head>

	<body bgcolor="#ffffff" onload="myResultOnLoadWithGrid(gridConfig);">
		<tcmis:form action="/receivingstatusresults.do" onsubmit="return submitFrameOnlyOnce();">
			<div id="errorMessagesAreaBody" style="display:none;">
    				<html:errors/>
    				${tcmISError}
    				<c:forEach var="tcmisError" items="${tcmISErrors}" >
  					${tcmisError}<br/>
    				</c:forEach>        
			</div>

			<div class="interface" id="resultsPage">
				<div class="backGroundContent">
					<div id="receivingStatusDisplay" style="width:100%;height:600px;" style="display: none;"></div>
					<c:if test="${empty searchResults }">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
							<tr>
								<td width="100%">
									<fmt:message key="main.nodatafound"/>
								</td>
							</tr>
						</table>
					</c:if>				
					<script type="text/javascript">
						<c:forEach var="row" items="${searchResults}" varStatus="status">
							assignedTo[${status.count}] = [
								{value:'-1', text: 'Unassigned'}
								<c:forEach var="assignee" items="${row.assignees}">,{value: '${assignee.personnelId}', text: '<tcmis:jsReplace value="${assignee.userName}" processMultiLines="true" />'}</c:forEach>
							];
						</c:forEach>
						<!--
						var jsonMainData = new Array();
						jsonMainData = {
							rows:[ <c:forEach var="row" items="${searchResults}" varStatus="status">
                                { id:${status.count}, 
                                	<c:choose>
	    	                    		<c:when test="${param.receivingStatus == 'QC Complete'}">
		                                	<c:choose>
		                                		<c:when test="${row.demandTypeOOR}">'class':"grid_pink",</c:when>
		                                		<c:when test="${row.lotStatusNotReadyForPick}">'class':"grid_blue",</c:when>
		                                	</c:choose>
										</c:when>
										<c:otherwise>
	    	                    			<c:choose>
		                                		<c:when test="${row.statusSuperCritical}">'class':"grid_pink",</c:when>
		                                		<c:when test="${row.statusCritical}">'class':"grid_red",</c:when>
		                                		<c:when test="${row.statusExcess}">'class':"grid_orange",</c:when>
		                                		<c:when test="${row.statusAllocatedToOpenPO}">'class':"grid_yellow",</c:when>
		                                		<c:when test="${row.statusML}">'class':"grid_green",</c:when>
		                                	</c:choose>
										</c:otherwise>
    	                        	</c:choose>                                	
								 data:[
									<c:if test="${param.receivingStatus == 'QC Ready'}">'Y',</c:if>
									'${row.receivingStatus}',
										<c:if test="${param.receivingStatus == 'QC Ready'}">${row.assignedTo},</c:if>
									 '${row.receiptId}<c:if test="${row.docsExists}"> <img src="/images/buttons/<c:choose><c:when test="${row.shipmentDocsOnline}">document.gif</c:when><c:otherwise>hourglass.png</c:otherwise></c:choose>"/></c:if>',
									 '${row.itemId}',
									 '<tcmis:jsReplace value="${row.itemDesc}" processMultiLines="true" />', 
									 '${row.quantityReceived}',
									 '${row.bin}',
									 '${row.transactionNumber}',
									 '<tcmis:jsReplace value="${row.supplierName}" processMultiLines="true" />', 
									 '<tcmis:jsReplace value="${row.carrier}" processMultiLines="true" />', 
									 '<tcmis:jsReplace value="${row.trackingNumber}" processMultiLines="true" />', 
									 '<tcmis:jsReplace value="${row.inventoryGroupName}" processMultiLines="true" />', 
									 '<fmt:formatDate value="${row.initialScanDate}" pattern="${dateTimeFormatPattern}"/>',
									 '${row.initialScanDate.time}', <%-- This is the hidden column used for sorting by date, so use the timestamp value --%>
									 '<fmt:formatDate value="${row.receivingStatusDate}" pattern="${dateTimeFormatPattern}"/>',
									 '${row.receivingStatusDate.time}', <%-- This is the hidden column used for sorting by date, so use the timestamp value --%>
									 '<tcmis:jsReplace value="${row.internalReceiptNotes}" processMultiLines="true" />',
									 '<tcmis:jsReplace value="${row.receivingNotes}" processMultiLines="true" />',
									 '<tcmis:jsReplace value="${row.trackingNotes}" processMultiLines="true" />'  ,
                                     '${row.receiptId}',
                                     false
                                    ]}<c:if test="${!status.last}">,</c:if>
									</c:forEach>
							]};
						//-->   
					</script>
					<div id="hiddenElements" style="display: none;">    			
						<tcmis:setHiddenFields beanName="input" />
						<input name="minHeight" id="minHeight" type="hidden" value="10"/>
					</div>
				</div>	
			</div>
		</tcmis:form>
	</body>
</html:html>