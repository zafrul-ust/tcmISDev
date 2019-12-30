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

		<%-- Page specific javascript --%>
		<script type="text/javascript" src="/js/common/ordertracking/dlainventoryadjustment.js"></script>

		<title>
		    <fmt:message key="dlaInventoryAdjustments"/>
		</title>

		<script language="JavaScript" type="text/javascript">
			<%-- Check for errors to display --%>
			<%-- Define the right click menus --%>
			with(milonic=new menuname("rightClickMenu")){
				top="offset=2"
				style = contextStyle;
				margin=3
				aI("text=<fmt:message key="label.removeLine"/>;url=javascript:removeRow();");
			}
			<%-- Initialize the RCMenus --%>
			drawMenus();

			<c:choose>
			 <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors && empty tcmISError}">
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

			<%--  This is the value array for type:'hcoro' pulldown 'deliveryType' --%> 
			var docIdCode = new  Array(
				{text:'D9A',value:'D9A'},
			    {text:'D8A',value:'D8A'},
			    {text:'DAC',value:'DAC'}
			);

			var uom = new  Array(
					{text:'EA',value:'EA'},
				    {text:'CY',value:'CY'}
				);

			<%--  This is the value array for type:'hcoro' pulldown 'deliveryType' --%> 
			var supplyConditionCode = new  Array(
				{text:'A',value:'A'},
			    {text:'F',value:'F'}
			);		

			var messagesData = new Array();
			messagesData = {alert:"<fmt:message key="label.alert"/>",
					and:"<fmt:message key="label.and"/>",
					validvalues:"<fmt:message key="label.validvalues"/>",
					submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
					recordFound:"<fmt:message key="label.recordFound"/>",
					searchDuration:"<fmt:message key="label.searchDuration"/>",
					minutes:"<fmt:message key="label.minutes"/>",
					seconds:"<fmt:message key="label.seconds"/>",
					positiveInt:"<fmt:message key="label.xxpositiveinteger"/>",
					quantity:"<fmt:message key="label.quantity"/>",
					nothingchanged:"<fmt:message key="label.nothing"/>"
					};
		// -->
		</script>

	</head>

	<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">
		<tcmis:form action="/dlainventoryadjustmentresults.do" onsubmit="return submitFrameOnlyOnce();">
			
		
			<div class="interface" id="resultsPage">
				<div class="backGroundContent">
					<%-- This is where the grid will be inserted.  The div name is ALSO the name of the form values that will be sent to the server on update --%>
					<div id="DlaInventoryAdjustmentBean" style="width:100%;height:400px;"></div>	
						<%-- If the collection is empty say no data found --%>
							<script type="text/javascript">
								<!--
								var jsonMainData = {
									rows:[
									<c:forEach var="row" items="${searchResults}" varStatus="status">
									<fmt:formatDate var="sent" value="${row.dateSent}" pattern="${dateFormatPattern}"/>
										{ id:${status.count},
										  data:['N',
										        'N',
										      	'${row.catPartNo}',
										     	'${row.status}',
										    	'${row.quantity}',
										    	'${row.supplyConditionCode}',
										     	'${row.previousConditionCode}',
										     	'${row.docIdCode}',
										    	'${row.loadLine}',
										    	'${row.previousConditionCode}',
										    	'${row.uom}',
										    	'current',
										    	'${sent}',
										    	'${row.transactionRefNum}'
												
										  ]}<c:if test="${!status.last}">,</c:if>
										  <c:set var="dataCount" value='${dataCount+1}'/> 
	 								</c:forEach>
									]};
								//-->  
							</script>
 
					<div id="hiddenElements" style="display: none;">    			
						<%-- Retrieve all the Search Criteria here for re-search after update--%>
    					<input name="minHeight" id="minHeight" type="hidden" value="100">
    					
    					<!-- Popup Calendar input options for revisedPromisedDate -->
    					<input name="totalLines" id="totalLines" value="<c:out value="${fn:length(searchResults) + 1}"/>" type="hidden"/>
						<input name="uAction" id="uAction" value="" type="hidden">
						<input name="dateInsertedFromDate" id="dateInsertedFromDate" type="hidden" value="<tcmis:jsReplace value="${param.dateInsertedFromDate}" />">
						<input name="dateInsertedToDate" id="dateInsertedToDate" type="hidden" value="<tcmis:jsReplace value="${param.dateInsertedToDate}" />">
						<input name="unitOfSale" id="unitOfSale" value="${unitOfSale}" type="hidden">
						<input name="catPartNo" id="catPartNo" type="hidden" value="<tcmis:jsReplace value="${param.catPartNo}" />">
						<input name="maxData" id="maxData" type="hidden" value="<tcmis:jsReplace value="${param.maxData}" />"> 	 
					</div>
				</div>
			</div>
		<script type="text/javascript">
			<%-- Define the columns for the result grid --%>
			var columnConfig = [ 
				{columnId:"permission" },	<%-- Update Permission for entire line --%>
				{columnId:"uomPermission" },	<%-- Update Permission for entire line --%>
				{columnId:"catPartNo", columnName:'<fmt:message key="label.nsn"/>', width:10},
				{columnId:"status", columnName:'<fmt:message key="label.status"/>', width:8},
				{columnId:"quantity", columnName:'<fmt:message key="label.quantity"/>', width:8, type:'hed', align:'center'},
				{columnId:"supplyConditionCode", columnName:'<fmt:message key="label.supplyconditioncode"/>', align:'center', type:'hcoro', align:'center' ,width:5, onChange:checkDocIdCode},
				{columnId:"previousConditionCode", columnName:'<fmt:message key="label.previousconditioncode"/>', align:'center', width:5},
				{columnId:"docIdCode", columnName:'<fmt:message key="label.docidcode"/>', type:'hcoro', align:'center' ,width:5, onChange:checkDocIdCode},
				{columnId:"loadLine", columnName:'<fmt:message key="label.loadline"/>', width:4, align:'center'},
				{columnId:"oldPreviousConditionCode"},
				{columnId:"uom", columnName:'<fmt:message key="label.uom"/>', type:'hcoro', width:4, align:'center',permission:true},
				{columnId:"lineStatus"},
				{columnId:"dateSent", columnName:'<fmt:message key="label.datesent"/>', width:8},
				{columnId:"transactionRefNum", columnName:'<fmt:message key="label.transactionreferencenumber"/>', width:10}
				
							
			]; 
			<%-- Define the grid options--%>
			var gridConfig = {
				divName: 'DlaInventoryAdjustmentBean',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
				beanData: 'jsonMainData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
				beanGrid: 'mygrid',		<%--  variable to put the grid object in for later use --%>
				config: columnConfig,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
				singleClickEdit: true,		<%--  Make TXT type field open edit on one click rahter than double click --%>
				rowSpan: false, 		<%--  true: this page has rowSpan > 1 or not, disables smartrendering & sorting --%>
				submitDefault: true,
			    onRightClick: onRightClick,
				onRowSelect: onRowSelect	
			}; 


		</script>
		


		</tcmis:form>
		
		<div id="errorMessagesAreaBody" style="display:none;">
			 ${tcmISError}<br/>
			 <c:forEach var="tcmisError" items="${tcmISErrors}">
			  ${tcmisError}<br/>
			 </c:forEach>
			 <c:if test="${param.maxData == fn:length(searchResults)}">
			  <fmt:message key="label.maxdata"> 
			   <fmt:param value="${param.maxData}"/>
			  </fmt:message>
			 </c:if>
		</div>
	</body>
</html:html>