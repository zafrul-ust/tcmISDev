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
		
		<%-- Sets CSS based on the user's preffered font size and which application he is viewing --%>
		<tcmis:gridFontSizeCss />
		
		<%-- Validation support --%>
		<script type="text/javascript" src="/js/common/formchek.js"></script>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
		
		<%-- This handles all the resizing of the page and frames --%>
		<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
		<%-- This disables various key press events --%>
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>

		<!-- This handels the menu style and what happens to the right click on the whole page -->
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
		<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
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
		<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
		<script type="text/javascript" src="/js/usgov/usgovdlagasordertracking.js"></script>

		<title>
			<fmt:message key="ordertracking.label.title"/>
		</title>
		<%-- NOTE: The only javascript here rather than in a page specific js file is javascript that contains values from JSP --%>
		<script language="JavaScript" type="text/javascript">		
			<%-- Standard var for Internationalized messages--%>
			var messagesData = new Array();	
			messagesData = {
				alert:"<fmt:message key="label.alert"/>",
				and:"<fmt:message key="label.and"/>",
				validvalues:"<fmt:message key="label.validvalues"/>",
				submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
				recordFound:"<fmt:message key="label.recordFound"/>",
				searchDuration:"<fmt:message key="label.searchDuration"/>",
				minutes:"<fmt:message key="label.minutes"/>",
				seconds:"<fmt:message key="label.seconds"/>",
				viewMsds:"<fmt:message key="label.viewmsds"/>",
				viewShipToDodaac:"<fmt:message key="label.viewshiptododaac"/>",
				viewUltimateDodaac:"<fmt:message key="label.viewultimatedodaac"/>",
				noTrackingLinkAvailable:"<fmt:message key="label.notrackinglinkavailable"/>",
				viewNist:"<fmt:message key="label.viewnist"/>",
				viewTrackingInformation:"<fmt:message key="label.viewtrackinginformation"/>",
				deliveryComments:"<fmt:message key="label.deliverycoments"/>",
				orderDocs:"<fmt:message key="label.orderdocs"/>"
			};
			
			var rowSpanMap = new Array();
			var rowSpanClassMap = new Array();
			var rowSpanCols = [0,1,2,3,4,5,6,7,8,9,10,11];
			
			with(milonic=new menuname("rcMenu")){
				 top="offset=2";
				 style=submenuStyle;
				 itemheight=17;
			 aI( 'text=<font color="gray"></font>;url=javascript:doNothing();' );
			}

			drawMenus();

			<%-- Define the columns for the result grid --%>
			var columnConfig = [
				{//0
					columnId:"permission"
				},
				{
					columnId:"docNum",
					columnName:'<fmt:message key="label.docnumbermillstrip"/>',
					width:10
				},
   				{
					columnId:"priorityRating",
					columnName:'<fmt:message key="label.priority"/>',
					width:4
				},
   				{
					columnId:"shipToDodaac",
					columnName:'<fmt:message key="label.shiptododaac"/>',
					width:5
				},
   				{
					columnId:"markForDodaac",
					columnName:'<fmt:message key="label.ultimatedodaac"/>',
					width:5
				},
   				{//5
					columnId:"contractNumber",
					columnName:'<fmt:message key="label.ordernumber"/>',
					width:12
				},
   				{
					columnId:"catPartNo",
					columnName:'<fmt:message key="label.nsn"/>',
					width:9
				},
   				{
					columnId:"partShortName",
					columnName:'<fmt:message key="label.nomenclature"/>',
					tooltip:"Y",
					width:15
				},
   				{
					columnId:"date997",
					columnName:'<fmt:message key="label.usgovorderdate"/>',
					width:7
				},
   				{
					columnId:"shipToOkDate",
					columnName:'<fmt:message key="label.dlametricdate"/>',
					width:7
				},
   				{//10
					columnId:"quantity",
					columnName:'<fmt:message key="label.quantityordered"/>',
					width:4
				},
   				{
					columnId:"uom",
					columnName:'<fmt:message key="label.uom"/>',
					width:4
				},
   				{
					columnId:"displayStatus",
					columnName:'<fmt:message key="label.status"/>',
					width:7
				},
   				{
					columnId:"qtyShipped",
					columnName:'<fmt:message key="label.transactionquantity"/>',
					width:6
				},
   				{
					columnId:"tcn",
					columnName:'<fmt:message key="label.tcn"/>',
					width:12
				},
   				{//15
					columnId:"estimatedShipDate",
					columnName:'<fmt:message key="label.estimatedshipdate"/>',
					width:7
				},
   				{
					columnId:"actualShipDate",
					columnName:'<fmt:message key="label.actualshipdate"/>',
					width:7
				},
   				{
					columnId:"carrier",
					columnName:'<fmt:message key="label.carrier"/>',
					width:7
				},
   				{
					columnId:"trackingNo",
					columnName:'<fmt:message key="label.trackingnumber"/>',
					width:10
				},
   				{
					columnId:"comments",
					columnName:'<fmt:message key="label.comments"/>',
					type:"txt",
					tooltip:"Y",
					width:20
				},
   				{//20
					columnId:"mfgLiteratureContent"
				},
   				{
					columnId:"transportationControlNo"
				},
   				{
					columnId:"shipmentId"
				},
   				{
					columnId:"prNumber"
				},
   				{
					columnId:"lineItem"
				},
   				{//25
					columnId:"shipViaLocationId"
				},
   				{//26
					columnId:"shipToLocationId"
				}
			];

			<%-- Define the grid options--%>
			var gridConfig = {
				divName: 'usGovDlaGasOrderTrackingGridDiv',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
				beanData: 'jsonMainData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
				beanGrid: 'mygrid',		<%--  variable to put the grid object in for later use --%>
				config: columnConfig,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
				rowSpan: true, 		<%--  true: this page has rowSpan > 1 or not, disables smartrendering & sorting --%>
				onRightClick: onRightclick,	<%--  a javascript function to be called on right click with rowId & cellId as args --%>
				noSmartRender:false,
				submitDefault:false, // the fields in grid are defaulted to be submitted or not.
				variableHeight:false,
				selectChild: 1
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
		</script>
	</head>
	
	<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">
		<tcmis:form action="/usgovdlagasordertrackingresults.do" onsubmit="return submitFrameOnlyOnce();">
			<div id="errorMessagesAreaBody" style="display:none;">
    				<html:errors/>
    				${tcmISError}
    				<c:forEach var="tcmisError" items="${tcmISErrors}" >
  					${tcmisError}<br/>
    				</c:forEach>        
			</div>

			<div class="interface" id="resultsPage">
				<div class="backGroundContent">
					<div id="usGovDlaGasOrderTrackingGridDiv" style="display: none;"></div>
					<c:choose>
						<c:when test="${empty dlaClientOrderTrackingViewBeanCollection}">
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
								var jsonMainData = new Array();
								jsonMainData = {
									rows:[
										<c:forEach var="row" items="${dlaClientOrderTrackingViewBeanCollection}" varStatus="status">
											{
												id:${status.count}, <%-- use status.count rather than status.index because the grid requires a 1 based index rather than 0 based --%>
												<%--  Set the color to the rows according to their releaseStatus --%>
												data:[
													'N',
													'${row.docNum}',
													'${row.priorityRating}', 
													'${row.shipToDodaac}',
													'${row.markForDodaac}',
													'${row.contractNumber}',
													'${row.catPartNo}',
													'<tcmis:jsReplace value="${row.partShortName}" processMultiLines="true" />',
													'<fmt:formatDate value="${row.orderDate}" pattern="${dateFormatPattern}"/>',
												    '<fmt:formatDate value="${row.shipToOkDate}" pattern="${dateFormatPattern}"/>',
												    '<fmt:formatNumber value="${row.quantity}" maxFractionDigits="0" />', 											 
													'${row.uom}',									 
													'${row.displayStatus}',
													'<fmt:formatNumber value="${row.qtyShipped}" maxFractionDigits="0" />',
													'${row.transportationControlNo}',	
													<c:choose>
														<c:when test="${empty row.actualShipDate}">
															<c:choose>
																<c:when test="${!empty row.expediteDate}">
																	'<fmt:formatDate value="${row.expediteDate}" pattern="${dateFormatPattern}"/>',
																</c:when>
																<c:otherwise>
																	'<fmt:formatDate value="${row.projectedDateShipped}" pattern="${dateFormatPattern}"/>',
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
														'',
														</c:otherwise>
													</c:choose>										    	 
													'<fmt:formatDate value="${row.actualShipDate}" pattern="${dateFormatPattern}"/>',										
													'${row.carrier}',
													'${row.trackingNo}',
													'<tcmis:jsReplace value="${row.comments}" processMultiLines="true" />',
													
													<%-- Hidden cols start--%>
													'${row.mfgLiteratureContent}',
													'${row.transportationControlNo}',
													'${row.shipmentId}',
													'${row.prNumber}',
													'${row.lineItem}',
													'${row.shipViaLocationId}',
													'${row.shipToLocationId}'
												]
											}
											<c:if test="${!status.last}">,</c:if>
	 									</c:forEach>
									]};
							</script>
						</c:otherwise>
					</c:choose>

					<script type="text/javascript">
						<%-- determining rowspan --%>
						<c:set var="rowSpanCount" value='0' />
						<%-- determining rowspan --%>
						<c:forEach var="row" items="${dlaClientOrderTrackingViewBeanCollection}" varStatus="status">
							<c:set var="currentKey" value='${row.contractNumber}' />
							<c:choose>
								<c:when test="${status.first}">
									<c:set var="rowSpanStart" value='0' />
									<c:set var="rowSpanCount" value='1' />
									<c:set var="prevSpanCount" value="${rowSpanCount}"/>
									rowSpanMap[0] = 1;
									rowSpanClassMap[0] = 1;
								</c:when>
								<c:when test="${currentKey == previousKey}">
									rowSpanMap[${rowSpanStart}]++;
									rowSpanMap[${status.index}] = 0;
									rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
								</c:when>
								<c:otherwise>
									<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
									<c:set var="rowSpanStart" value='${status.index}' />
									rowSpanMap[${status.index}] = 1;
									rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
								</c:otherwise>
							</c:choose>
							<c:set var="previousKey" value='${currentKey}' />
						</c:forEach> 
					</script>
					
					<div id="hiddenElements" style="display: none;">    			
						<input name="totalLines" id="totalLines" value="<c:out value="${fn:length(dlaClientOrderTrackingViewBeanCollection)}"/>" type="hidden"/>
						<input name="minHeight" id="minHeight" type="hidden" value="100"/>	 
					</div>
				</div>
			</div>
		</tcmis:form>
	</body>
</html:html>
