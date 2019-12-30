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

		<%-- Right Mouse Click (RMC) Menu support, keep in all pages  --%>
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
		<%@ include file="/common/rightclickmenudata.jsp" %>
	
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
				validvalues:"<fmt:message key="label.validvalues"/>",
				open:"<fmt:message key="label.open"/>",
				submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
				purchaseorder:"<fmt:message key="label.purchaseorder"/>"
				};
	
			<%-- Define the right click menus --%>
			with(milonic=new menuname("rightClickMenu")){
				top="offset=2"
				style = contextStyle;
				margin=3
				 aI("text=<fmt:message key="label.sendnewcorrectedpol"/>;url=javascript:resendRadianPo();");
			}

			<%-- Initialize the RCMenus --%>
			drawMenus();
			
			function resendRadianPo() {
				    
					document.genericForm.target = ''; // Form name "genericForm" comes from struts config file

					$('uAction').value = 'resendCorrectedPo';
					
					$('resendPoNo').value = cellValue(mygrid.getSelectedRowId(),"radianPo");

					// Reset search time for update
					var now = new Date();
					var startSearchTime = parent.document.getElementById("startSearchTime");
					startSearchTime.value = now.getTime();	

					parent.showPleaseWait(); // Show "please wait" while updating

					if (mygrid != null) {
						// This function prepares the grid dat for submitting top the server
						mygrid.parentFormOnSubmit();
					}

					document.genericForm.submit(); // Submit the form*/
				}
			
			function openPoPage()
			{
				var poNumber = $v('poToOpen');

			    loc = "/tcmIS/supply/purchorder?po=" + poNumber + "&Action=searchlike&subUserAction=po";

			    try
			    {
			      parent.parent.openIFrame("purchaseOrder"+poNumber+"",loc,""+messagesData.purchaseorder+" "+poNumber+"","","N");
			    }
			    catch (ex)
			    {
			      openWinGeneric(loc,"showradianPo_"+poNumber+"","800","600","yes","50","50");
			    }
			    $('poToOpen').value = '';
				
			}
			
			function onRightclick(rowId, cellId) {
				// Show right-click menu
				if(cellValue(rowId,'done') != 'Y' && cellValue(rowId,'matched') != 'Y')
					toggleContextMenu('rightClickMenu');
			}
			

			<%-- Define the columns for the result grid --%>
			var columnConfig = [
			    {columnId:"permission"},
			    {columnId:"supplierName", columnName:'<fmt:message key="label.supplier"/>', width:7},
			    {columnId:"itemId", columnName:'<fmt:message key="label.itemid"/>', width:5},
				{columnId:"radianPo", columnName:'<fmt:message key="label.po"/>', width:5},
				{columnId:"poLine", columnName:'<fmt:message key="label.poline"/>', width:5},
				{columnId:"poLineConfirmedDate"},
				{columnId:"shiptoAddress", columnName:'<fmt:message key="label.shiptoaddress"/>', tooltip:"Y", width:24},
				{columnId:"supplierShipToCode", columnName:'<fmt:message key="label.suppliershiptocode"/>', width:8},
				{columnId:"ediAcknowledgementCode", columnName:'<fmt:message key="label.ediacknowledgementcode"/>', width:6},
				{columnId:"acceptedEdiDateCode", columnName:'<fmt:message key="label.acceptededidatecode"/>', width:6},
				{columnId:"acceptedQuantity", columnName:'<fmt:message key="label.acceptedquantity"/>', width:6},
				{columnId:"ediQuantity", columnName:'<fmt:message key="label.quantityordered"/>', tooltip:"Y", width:4},
				{columnId:"acceptedUnitPrice", columnName:'<fmt:message key="label.acceptedunitprice"/>', width:6},
				{columnId:"transactionDate", columnName:'<fmt:message key="label.transactiondate"/>',type:'hcal', hiddenSortingColumn:'hiddenTransactionDate', sorting:'int' },
				{columnId:"hiddenTransactionDate", sorting:'int' },
				{columnId:"promisedDate", columnName:'<fmt:message key="label.promiseddate"/>',type:'hcal', hiddenSortingColumn:'hiddenPromisedDate', sorting:'int' },
				{columnId:"hiddenPromisedDate", sorting:'int' },
				{columnId:"dockDate", columnName:'<fmt:message key="label.dock.date"/>',type:'hcal', hiddenSortingColumn:'hiddenDockDate', sorting:'int' },
				{columnId:"hiddenDockDate", sorting:'int' },
				{columnId:"reviewerId", columnName:'<fmt:message key="deliveryschedule.label.reviewer"/>',  width:6},
				{columnId:"reviewComments", columnName:'<fmt:message key="label.reviewcomments"/>', tooltip:"Y", width:24},
				{columnId:"mismatchComments", columnName:'<fmt:message key="label.mismatchcomments"/>', tooltip:"Y", width:24},
				{columnId:"supplierPartNo", columnName:'<fmt:message key="label.supplierpartnum"/>', tooltip:"Y", width:12},
				{columnId:"supplierPartDesc", columnName:'<fmt:message key="label.supplierpartdesc"/>', tooltip:"Y", width:24},
				{columnId:"supplierSalesOrderNo", columnName:'<fmt:message key="label.suppliersalesorderno"/>', width:12},
				{columnId:"supplierSalesOrderDate", columnName:'<fmt:message key="label.suppliersalesorderdate"/>',type:'hcal', hiddenSortingColumn:'hiddenSupplierSalesOrderDate', sorting:'int' },
				{columnId:"hiddenSupplierSalesOrderDate", sorting:'int' },
				{columnId:"datePoCreated", columnName:'<fmt:message key="label.podate"/>',type:'hcal', hiddenSortingColumn:'hiddenDatePoCreated', sorting:'int' },
				{columnId:"hiddenDatePoCreated", sorting:'int' },
				{columnId:"poNotes", columnName:'<fmt:message key="label.ponotes"/>', tooltip:"Y", width:24},
				{columnId:"poLineNotes", columnName:'<fmt:message key="label.polinenotes"/>', tooltip:"Y", width:24},
				{columnId:"done"},
				{columnId:"matched"}
			];

			<%-- Define the grid options--%>
			var gridConfig = {
				divName: 'ediSupplierStatusGridDiv',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
				beanData: 'jsonMainData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
				beanGrid: 'mygrid',		<%--  variable to put the grid object in for later use --%>
				config: columnConfig,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
				rowSpan: false, 		<%--  true: this page has rowSpan > 1 or not, disables smartrendering & sorting --%>
				onRightClick:onRightclick,
				noSmartRender:false
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
	
			//-->
		</script>
	</head>

	<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);if($v('poToOpen') != ''){openPoPage();}">
		<tcmis:form action="/edisupplierstatusresults.do" onsubmit="return submitFrameOnlyOnce();">
			<div id="errorMessagesAreaBody" style="display:none;">
    				<html:errors/>
    				${tcmISError}
    				<c:forEach var="tcmisError" items="${tcmISErrors}" >
  					${tcmisError}<br/>
    				</c:forEach>        
			</div>

			<div class="interface" id="resultsPage">
				<div class="backGroundContent">
					<div id="ediSupplierStatusGridDiv" style="width:100%;height:400px;"></div>
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
									<c:set var="addressLine">
									${row.shiptoAddressLine1} ${row.shiptoAddressLine2} ${row.shiptoAddressLine3} ${row.shiptoCity},${row.shiptoStateAbbrev} ${row.shiptoZip} ${row.shiptoCountryAbbrev}
									</c:set>
									
										{ id:${status.count}, <%-- use status.count rather than status.index because the grid requires a 1 based index rather than 0 based --%>
										<%--  Set the color to the rows according to their releaseStatus --%>
										 data:[
											 	'N',
											 	'<tcmis:jsReplace value="${row.supplierName}" processMultiLines="false" />',
											 	'${row.itemId}',
												'${row.radianPo}',
												'${row.poLine}',
												'<tcmis:jsReplace value="${addressLine}" processMultiLines="false" />',
												'${row.supplierShipToCode}',
												'${row.ediAcknowledgementCode}',
												'${row.acceptedEdiDateCode}',
												'${row.acceptedQuantity} ${row.acceptedUom}',
												'${row.orderQuantity} ${row.ediUom}',
												'<fmt:formatNumber value="${row.acceptedUnitPrice}" pattern="${totalcurrencyformat}"></fmt:formatNumber>',
												'<fmt:formatDate value="${row.transactionDate}" pattern="${dateFormatPattern}"/>',
												 '${row.transactionDate.time}',
												'<fmt:formatDate value="${row.promisedDate}" pattern="${dateFormatPattern}"/>',
												 '${row.promisedDate.time}',
												 '<fmt:formatDate value="${row.dockDate}" pattern="${dateFormatPattern}"/>',
												 '${row.dockDate.time}',
												 '${row.reviewerId}',
												  '<tcmis:jsReplace value="${row.reviewComments}" processMultiLines="true" />',
												  '<tcmis:jsReplace value="${row.mismatchComments}" processMultiLines="true" />',
												 '<tcmis:jsReplace value="${row.supplierPartNo}" processMultiLines="false" />',
												'<tcmis:jsReplace value="${row.supplierPartDesc}" processMultiLines="true" />',
												'${row.supplierSalesOrderNo}',
												'<fmt:formatDate value="${row.supplierSalesOrderDate}" pattern="${dateFormatPattern}"/>',
												 '${row.supplierSalesOrderDate.time}',
												 '<fmt:formatDate value="${row.datePoCreated}" pattern="${dateFormatPattern}"/>',
												 '${row.datePoCreated.time}',
												 '<tcmis:jsReplace value="${row.poNotes}" processMultiLines="true" />',
												 '<tcmis:jsReplace value="${row.poLineNotes}" processMultiLines="true" />',
												 '${row.done}',
												 '${row.matched}'
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
					    <input name="uAction" id="uAction" type="hidden" value=""/>
						<input name="resendPoNo" id="resendPoNo" type="hidden" value=""/>
						<input name="poToOpen" id="poToOpen" type="hidden" value="${poToOpen}"/>
						<input name="companyId" id="companyId" type="hidden" value="<tcmis:jsReplace value="${param.companyId}" />" />
						<input name="searchField" id="searchField" type="hidden" value="<tcmis:jsReplace value="${param.searchField}" />" />	
						<input name="searchMode" id="searchMode" type="hidden" value="<tcmis:jsReplace value="${param.searchMode}" />" />	
						<input name="searchArgument" id="searchArgument" type="hidden" value="<tcmis:jsReplace value="${param.searchArgument}" />" />	
						<input name="dateInsertedFromDate" id="dateInsertedFromDate" type="hidden" value="<tcmis:jsReplace value="${param.dateInsertedFromDate}" />" />	
						<input name="dateInsertedToDate" id="dateInsertedToDate" type="hidden" value="<tcmis:jsReplace value="${param.dateInsertedToDate}" />" />	
						<input name="showConfirmedPOs" id="showConfirmedPOs" type="hidden" value="<tcmis:jsReplace value="${param.resendPoNo}" />" />										 
					</div>
				</div>
			</div>
		</tcmis:form>
	</body>
</html:html>


