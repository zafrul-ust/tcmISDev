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
		<script type="text/javascript" src="/js/seagate/aribarepair.js"></script>

		<title>
		Ariba Repair Results
		</title>

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
						

			var messagesData = new Array();
			messagesData = {alert:"<fmt:message key="label.alert"/>",
					and:"<fmt:message key="label.and"/>",
					validvalues:"<fmt:message key="label.validvalues"/>",
					submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
					recordFound:"<fmt:message key="label.recordFound"/>",
					searchDuration:"<fmt:message key="label.searchDuration"/>",
					minutes:"<fmt:message key="label.minutes"/>",
					seconds:"<fmt:message key="label.seconds"/>",
					itemInteger:"<fmt:message key="error.item.integer"/>",
					ok:"<fmt:message key="label.ok"/>",                       
					dateofrevision:"<fmt:message key="label.dateofrevision"/>",
					expediteage:"<fmt:message key="label.expediteage"/>",
					comments:"<fmt:message key="label.comments"/>",
					maximum2000:"<fmt:message key="label.maximum2000"/>",
					revisedPromisedDate:"<fmt:message key="label.revisedpromisedate"/>"};

			<%-- Define the columns for the result grid --%>
			var columnConfig = [ 
				{columnId:"permission" },			<%-- Update Permission for entire line --%>
				{columnId:"okDoUpdate", columnName:'<fmt:message key="label.ok"/><br><input type="checkbox" value="" onClick="return checkAll(\'okDoUpdate\');" name="chkAllOkDoUpdate" id="chkAllOkDoUpdate">', type:'hchstatus', align:'center', width:3 },
				{columnId:"companyId", columnName:'<fmt:message key="label.company"/>', width:5 },
				{columnId:"prNumber", columnName:'<fmt:message key="label.mrnumber"/>', width:5 },
				{columnId:"lineItem", columnName:'<fmt:message key="label.lineitem"/>',  width:2 },
				{columnId:"requestorName", columnName:'<fmt:message key="label.requestor"/>', width:10 },
				{columnId:"releaseDate", columnName:'<fmt:message key="label.releasedate"/>', hiddenSortingColumn:'hiddenReleaseDateTime', sorting:'int' , width:10 },
				{columnId:"applicationDesc", columnName:'<fmt:message key="label.application"/>', width:10 },
				{columnId:"facPartNo", columnName:'<fmt:message key="label.partno"/>', width:5 },
				{columnId:"partDescription", columnName:'<fmt:message key="label.partdesc"/>',  width:10 },
				{columnId:"quantity", columnName:'<fmt:message key="label.quantity"/>',  width:5 },
				{columnId:"extendedPrice", columnName:'<fmt:message key="label.extendedprice"/>',  width:10 },
				{columnId:"accountSysId", columnName:'<fmt:message key="label.accountsys"/>', width:7 },
				{columnId:"application" },
				{columnId:"hiddenReleaseDateTime", sorting:'int' }



				
			]; 

			<%-- Define the grid options--%>
			var gridConfig = {
				divName: 'aribaRepairViewBean',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
				beanData: 'jsonMainData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
				beanGrid: 'mygrid',		<%--  variable to put the grid object in for later use --%>
				config: columnConfig,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
				singleClickEdit: true,		<%--  Make TXT type field open edit on one click rahter than double click --%>
				submitDefault: true,
				rowSpan: true	 		<%--  true: this page has rowSpan > 1 or not, disables smartrendering & sorting --%>
			};
				

            
		// -->
		</script>
	</head>

	<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">
		<tcmis:form action="/aribarepairresults.do" onsubmit="return submitFrameOnlyOnce();">
			<div id="errorMessagesAreaBody" style="display:none;">
    				<html:errors/>
				${tcmISError}
				<c:forEach var="error" items="${tcmISErrors}" varStatus="status">
					${error}<br/>
				</c:forEach>        
			</div>
		
			<div class="interface" id="resultsPage">
				<div class="backGroundContent">
					<%-- This is where the grid will be inserted.  The div name is ALSO the name of the form values that will be sent to the server on update --%>
					<div id="aribaRepairViewBean" style="width:100%;height:400px;" style=""></div>	
					
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
								<!--
								<c:set var="showUpdateLink" value='N'/>
								var jsonMainData = {
									rows:[
									<c:forEach var="row" items="${searchResults}" varStatus="status">
										<%-- Check row permission here --%>
										{ id:${status.count},
										  data:['Y',
											false,
										    '${row.companyId}',
											'${row.prNumber}',
											'${row.lineItem}',
											'<tcmis:jsReplace value="${row.requestorName}" processMultiLines="false" />',
											'<fmt:formatDate value="${row.releaseDate}" pattern="${dateFormatPattern}"/>',
											'<tcmis:jsReplace value="${row.applicationDesc}" processMultiLines="false" />',
											'${row.facPartNo}',
											'<tcmis:jsReplace value="${row.partDescription}" processMultiLines="true" />',
											'${row.quantity}',
											'<fmt:formatNumber value="${row.extendedPrice}" pattern="${totalcurrencyformat}"/>',
											'${row.accountSysId}',
											'${row.application}',
											'${row.releaseDate.time}'
										  ]}<c:if test="${!status.last}">,</c:if>
	 								</c:forEach>
									]};
								//-->  
							</script>
						</c:otherwise>
					</c:choose>
 
					<div id="hiddenElements" style="display: none;">    			
						<%-- Retrieve all the Search Criteria here for re-search after update--%>

					<tcmis:setHiddenFields beanName="searchInput"/>
					
						    
						<!-- Popup Calendar input options for revisedPromisedDate -->
						<input type="hidden" name="totalLines" id="totalLines" value="${fn:length(searchResults)}"/>
    					<input name="minHeight" id="minHeight" type="hidden" value="100"> 
					</div>
				</div>
			</div>
			    <script type="text/javascript">
			        <!--
			        showUpdateLinks = true;
			        //-->
			    </script>


		</tcmis:form>
	</body>
</html:html>