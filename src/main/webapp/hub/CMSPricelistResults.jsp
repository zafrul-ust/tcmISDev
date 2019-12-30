<!DOCTYPE html>
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
		<meta http-equiv="X-UA-Compatible" content="IE=11" /> <%-- Disable compatibility mode for this page --%>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="expires" content="-1">
		<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
		
		<%@ include file="/common/locale.jsp" %>
		
		<tcmis:gridFontSizeCss /> <%-- Sets CSS based on the user's preffered font size and which application he is viewing --%>
	
		<script type="text/javascript" src="/js/common/formchek.js"></script>			<%-- Validation support --%>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
		<script type="text/javascript" src="/js/common/json2.js"></script>

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

		<%-- Right Mouse Click (RMC) Menu support, keep in all pages  --%>
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
		<%@ include file="/common/rightclickmenudata.jsp" %>

		<%-- Page specific javascript --%>
		<script type="text/javascript" src="/js/hub/CMSPricelist.js"></script>

		<title><fmt:message key="CMSPricelist"/></title>

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
					mustBeADate:"<fmt:message key="errors.date"/>",
					mustBeAFloat:"<fmt:message key="errors.float"/>",
					startDate:"<fmt:message key="label.startingDate"/>",
					endDate:"<fmt:message key="label.enddate"/>",
					catalogPrice:"<fmt:message key="label.catalogprice"/>",
					comments:"<fmt:message key="label.comments"/>",
					indefinite:"<fmt:message key="label.indefinite"/>",
					maximum2000:"<fmt:message key="label.maximum2000"/>",
					noteRequired:"<fmt:message key="label.auditnoterequired"/>",
					daterange:"<fmt:message key="error.enddatemustebeafterstartdate"/>"
					};
			
			<%-- Define the right click menus --%>
			with(milonic=new menuname("rightClickMenu")){
				top="offset=2"
				style = contextStyle;
				margin=3
				aI("text=<fmt:message key="label.showpricehistory"/>;url=javascript:showHistory();");
			}

			<%-- Initialize the RCMenus --%>
			drawMenus();			

			<%-- Define the columns for the result grid --%>
			var columnConfig = [ 
				{columnId:"permission"},
				{columnId:"okDoUpdate"<c:if test="${canEditThisPricegroup}">, columnName:'<fmt:message key="label.ok"/>', type:'hch', sorting:"haasHch", align:'center', onChange:doValidateLine, width:3 </c:if>},
				{columnId:"catPartNo", columnName:'<fmt:message key="label.partno"/>', width:10},
				{columnId:"itemId", columnName:'<fmt:message key="label.itemid"/>', sorting:'int', tooltip:"Y", width:10 },
				{columnId:"itemDesc", columnName:'<fmt:message key="label.itemdescription"/>', tooltip:"Y", width:20 },
				{columnId:"startDate", columnName:'<fmt:message key="label.startingdate"/>', align:'center', width:8, hiddenSortingColumn: "startDateSort"},
				{columnId:"startDateSort", sorting: "int"}, 	
				{columnId:"endDate", type:'hcal', columnName:'<fmt:message key="label.enddate"/>', align:'center', width:8, onChange:onChange, hiddenSortingColumn: "endDateSort"},
				{columnId:"endDateSort", sorting: "int"}, 	
				{columnId:"currencyId", columnName:'<fmt:message key="label.currency"/>', type:'hcoro', width:5, onChange:onChange},
				{columnId:"catalogPrice", columnName:'<fmt:message key="label.catalogprice"/>', type: 'hed', sorting:'int', width:8, onChange:onChange},
				{columnId:"loadingComments", columnName:'<fmt:message key="label.auditnotes"/>', type: 'hed', tooltip:"Y", width:20, onChange:onChange},
				{columnId:"catalogId" }, 			<%-- columns without names are hidden columns--%>
				{columnId:"partGroupNo" }, 	
				{columnId:"originalStartDate" }, 	
				{columnId:"originalEndDate" },
				{columnId:"originalCatalogPrice" },
				{columnId:"originalCurrencyId" },
				{columnId:"expired" }
			]; 

			<%-- Define the grid options--%>
			var gridConfig = {
				divName: 'CMSPricelistBean',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
				beanData: 'jsonMainData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
				beanGrid: 'mygrid',		<%--  variable to put the grid object in for later use --%>
				config: columnConfig,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
				singleClickEdit: true,		<%--  Make TXT type field open edit on one click rahter than double click --%>
				submitDefault: true,
				backgroundRender: true,
			    onRowSelect:onRowSelected,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
			    onRightClick: onRightclick,	<%--  a javascript function to be called on right click with rowId & cellId as args --%>
				rowSpan: false	 		<%--  true: this page has rowSpan > 1 or not, disables smartrendering & sorting --%>
			};

			<%--  This is the value array for type:'hcoro' pulldown 'currencyId' --%> 
			var currencyId = new  Array(<c:forEach var= "cur" items= "${vvCurrencyCollection}" varStatus= "status" >		
				{text:'${cur.currencyDisplay}',value:'${cur.currencyId}'}<c:if test= "${!status.last}">,</c:if></c:forEach>);
			
			var hasEditPermission = <c:choose><c:when test="${canEditThisPricegroup}">true</c:when><c:otherwise>false</c:otherwise></c:choose>;
           
		// -->
		</script>

	</head>

	<body bgcolor="#ffffff" onload='myResultOnLoadWithGrid(gridConfig);'>
		<tcmis:form action="/CMSPricelist.do" onsubmit="return submitFrameOnlyOnce();">
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
					<div id="CMSPricelistBean" style="width:100%;height:400px;" style="display: none;"></div>	

					<script type="text/javascript">
						<!--
						<c:choose>
							<c:when test="${empty searchResults}">
							var jsonMainData = {
									rows:[
										{ id:1,
										  data:['<c:choose><c:when test="${canEditThisPricegroup}">Y</c:when><c:otherwise>N</c:otherwise></c:choose>',
											false,
											'',
											'',
											'',
											'',
											'',
											'${defaultCurrencyId}',
											'',
											'',
											'',
											'',
											'',
											'',
											'',
											'',
											''
										  ]}
									]};
							</c:when>
							<c:otherwise>
								var jsonMainData = {
									rows:[<c:forEach var="row" items="${searchResults}" varStatus="status">
										{ id:${status.count},
										  data:['<c:choose><c:when test="${canEditThisPricegroup && !row.expired}">Y</c:when><c:otherwise>N</c:otherwise></c:choose>',
											false,
											'<tcmis:jsReplace value="${row.catPartNo}" processMultiLines="true" />',
											'<tcmis:jsReplace value="${row.itemId}" processMultiLines="true" />',
											'<tcmis:jsReplace value="${row.itemDesc}" processMultiLines="true" />',
											'<fmt:formatDate value="${row.startDate}" pattern="${dateFormatPattern}"/>',
											'${row.startDate.time}',
								            <fmt:formatDate var="formattedEndDate" value="${row.endDate}" pattern="${dateFormatPattern}"/>
											<c:if test="${formattedEndDate == '01-Jan-3000'}"><c:set var="formattedEndDate"><fmt:message key="label.indefinite"/></c:set></c:if>											
											'${formattedEndDate}',
											'${row.endDate.time}',
											'${row.currencyId}',
									        '<fmt:formatNumber value="${row.catalogPrice}"  pattern="${totalcurrencyformat}"/>',
											'',
											'<tcmis:jsReplace value="${row.catalogId}" processMultiLines="true" />',
											'${row.partGroupNo}',
											'<fmt:formatDate value="${row.startDate}" pattern="${dateFormatPattern}"/>',
											'${formattedEndDate}',
									        '<fmt:formatNumber value="${row.catalogPrice}"  pattern="${totalcurrencyformat}"/>',
											'${row.currencyId}',
											'${row.expired}'											
										  ]}<c:if test="${!status.last}">,</c:if>
										</c:forEach>
									]};
							</c:otherwise>
						</c:choose>
						//-->  
					</script>

					<div id="hiddenElements" style="display: none;">    			
						<%-- Retrieve all the Search Criteria here for re-search after update--%>
						<tcmis:setHiddenFields beanName="searchInput"/>
						    
						<!-- Popup Calendar input options for startDate -->
						<input type="hidden" name="blockBefore_startDate" id="blockBefore_startDate" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>"/>
						<input type="hidden" name="blockAfter_startDate" id="blockAfter_startDate" value=""/>
						<input type="hidden" name="blockBeforeExclude_startDate" id="blockBeforeExclude_startDate" value=""/>
						<input type="hidden" name="blockAfterExclude_startDate" id="blockAfterExclude_startDate" value=""/>
						<input type="hidden" name="inDefinite_startDate" id="inDefinite_startDate" value=""/>
						
						<!-- Popup Calendar input options for endDate -->
						<input type="hidden" name="blockBefore_endDate" id="blockBefore_endDate" value="<tcmis:getDateTag numberOfDaysFromToday="+1" datePattern="${dateFormatPattern}"/>"/>
						<input type="hidden" name="blockAfter_endDate" id="blockAfter_endDate" value=""/>
						<input type="hidden" name="blockBeforeExclude_endDate" id="blockBeforeExclude_endDate" value=""/>
						<input type="hidden" name="blockAfterExclude_endDate" id="blockAfterExclude_endDate" value=""/>
						<input type="hidden" name="inDefinite_endDate" id="inDefinite_endDate" value="Y"/>

   						<input name="minHeight" id="minHeight" type="hidden" value="100">
					</div>
				</div>
			</div>
			<c:if test="${canEditThisPricegroup}">
			    <script type="text/javascript">
			        <!--
			        showUpdateLinks = true;
			        //-->
			    </script>
			</c:if>

		</tcmis:form>
		<form name="getCSVform" id="getCSV" method="post" action="/tcmIS/hub/CMSPricelist.do" >
			<input type="hidden" name="uAction" id="uAction" value="getCSV"/>
			<input type="hidden" name="companyId" id="companyId" value="<tcmis:jsReplace value="${param.companyId}" processMultiLines="true" />"/>
			<input type="hidden" name="priceGroupId" id="priceGroupId" value="<tcmis:jsReplace value="${param.priceGroupId}" processMultiLines="true" />"/>
			<input type="hidden" name="searchArgument" id="searchArgument" value="<tcmis:jsReplace value="${param.searchArgument}" processMultiLines="true" />"/>
			<input type="hidden" name="searchField" id="searchField" value="<tcmis:jsReplace value="${param.searchField}" processMultiLines="true" />"/>
			<input type="hidden" name="searchMode" id="searchMode" value="<tcmis:jsReplace value="${param.searchMode}" processMultiLines="true" />"/>
		</form>
	</body>
</html:html>