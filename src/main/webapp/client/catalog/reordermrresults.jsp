<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html:html lang="true">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="expires" content="-1"/>
	<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
	<%@ include file="/common/locale.jsp"%>
	<tcmis:gridFontSizeCss />

	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script	type="text/javascript" src="/js/common/commonutil.js"></script>
	<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
	<script src="/js/common/disableKeys.js"></script>

	<!-- For Calendar support -->
	<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
	<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
	<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

	<!-- These are for the Grid-->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script	type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
	<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
	<%--Uncomment the below if your grid has row span > 1--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

	<!-- Add any other javascript you need for the page here -->
	<script	type="text/javascript" src="/js/client/catalog/reordermrresults.js"></script>
	<script src="/js/jquery/jquery-1.6.4.js"></script>

	<title></title>

	<script language="JavaScript" type="text/javascript">
		var messagesData = {
			errors:"<fmt:message key="label.errors"/>",
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			recordFound:"<fmt:message key="label.recordFound"/>",
			searchDuration:"<fmt:message key="label.searchDuration"/>",
			minutes:"<fmt:message key="label.minutes"/>",
			seconds:"<fmt:message key="label.seconds"/>",
			forVal:"<fmt:message key="label.for"/>",
			selectA:"<fmt:message key="errors.selecta"/>",
			workArea:"<fmt:message key="label.workarea" />",
			accountSystem:"<fmt:message key="label.accountsystem" />",
			noRowSelected:"<fmt:message key="error.norowselected"/>",
			xxPositiveInteger:"<fmt:message key="label.xxpositiveinteger"/>",
			quantity:"<fmt:message key="label.quantity" />",
			genericError:"<fmt:message key="generic.error"/>"
		};
		
		<%-- Define the grid options--%>
		var gridConfig = {
			divName: 'shoppingCartBean',			<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
			beanData: 'jsonMainData',			<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
			beanGrid: 'beanGrid',				<%--  variable to put the grid object in for later use --%>
			config: 'config',					<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
			submitDefault: true
		};
		
		<%-- Define the columns for the result grid --%>
		var config = [
			{
				columnId: "permission"
			},
			{
				columnId:"okDoUpdate",
				columnName:'<fmt:message key="label.ok"/><br><input type="checkbox" value="" onClick="return checkAll(\'okDoUpdate\');" name="chkAllOkDoUpdate" id="chkAllOkDoUpdate">',
				type:'hchstatus',
				align:'center',
				width:3
			},
			{
				columnId:"applicationDisplay",
				columnName:'<fmt:message key="label.workarea"/>',
				tooltip:"Y",
				align:'center',
				width:12
			},
			{
				columnId:"catPartNo",
				columnName:'<fmt:message key="label.part"/>',
				tooltip:"Y",
				align:'center',
				width:10
			},
			{
				columnId:"partDescription",
				columnName:'<fmt:message key="label.description"/>',
				tooltip:"Y",
				width:15
			},
			{
				columnId:"quantity",
				columnName:'<fmt:message key="label.quantity"/>',
				tooltip:"Y",
				type:'hed',
				align:'center',
				onChange: quantityChanged,
				width:6
			},
			{
				columnId:"examplePackaging",
				columnName:'<fmt:message key="label.pkg"/>',
				tooltip:"Y",
				align:'center',
				width:15
			},
			{
				columnId:"extPrice",
				columnName:'<fmt:message key="label.extprice"/>',
				tooltip:"Y",
				align:'center',
				width:7
			},
			{
				columnId:"dateNeeded",
				columnName:'<fmt:message key="label.dateneeded"/>',
				tooltip:"Y",
				type:'hcal',
				align:'center',
				width:8
			},
			{
				columnId:"projectedLeadTime",
				columnName:'<fmt:message key="label.leadtimeindays"/>',
				align:'center',
				width:6
			},
			{
				columnId:"notes",
				columnName:'<fmt:message key="label.notes"/>',
				tooltip:"Y",
				type:'txt',
				width:15
			},
			{
				columnId:"critical",
				columnName:'<fmt:message key="label.critical"/>',
				type:'hchstatus',
				align:'center',
				width:4
			},
			{
				columnId:"facilityId"
			},
			{
				columnId:"catalogId"
			},
			{
				columnId:"catalogCompanyId"
			},
			{
				columnId:"partGroupNo"
			},
			{
				columnId:"catalogPrice"
			},
			{
				columnId:"baselinePrice"
			},
			{
				columnId:"currencyId"
			},
			{
				columnId:"orderQuantityRule"
			},
			{
				columnId:"inventoryGroup"
			},
			{
				columnId:"itemType"
			},
			{
				columnId:"stockingMethod"
			},
			{
				columnId:"partApproved"
			},
			{
				columnId:"originalQuantity"
			},
			{
				columnId:"originalApplication"
			},
			{
				columnId:"application"
			}

		];
		
		var showErrorMessage = false;
		<c:choose>
			<c:when test="${not empty tcmISErrors or not empty tcmISError}">
				showErrorMessage = true;
			</c:when>
		</c:choose>
	</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid();">
	<tcmis:form action="/reordermrresults.do" onsubmit="return submitFrameOnlyOnce();">
		<div class="interface" id="resultsPage">
	        <div class="backGroundContent">
	            <div id="shoppingCartBean" style="width: 100%; height: 400px;" style="display: none;"></div>
				<!-- Error Messages Begins -->
				<div id="errorMessagesAreaBody" style="display:none;">
					<html:errors/>${tcmISError}
					<c:forEach var="error" items="${tcmISErrors}">
						${error}<br/>
					</c:forEach>
				</div>
	            <c:choose>
	            	<%-- If the collection is empty say no data found --%>
	            	<c:when test="${empty shoppingCartBeanCollection}" >
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
	                	         use fmt:formatDate to format dates (existing patterns: dateFormatPattern, dateTimeFormatPattern)--%>
							var jsonMainData = {
									rows:[
										<c:forEach var="myBean" items="${shoppingCartBeanCollection}" varStatus="status">
											<c:if test="${!status.first}">,</c:if>
											<c:set var="partApproved" value="false"/>
											<c:if test="${myBean.partApproved == 'y' or myBean.partApproved == 'Y'}">
												<c:set var="partApproved" value="true"/>
											</c:if>
											{
												id:${status.count},
												<c:if test="${!partApproved}">'class': 'grid_lightgray',</c:if>
												data:[
													<c:choose>
														<c:when test="${partApproved}">
															'Y', true
														</c:when>
														<c:otherwise>
															'N', false
														</c:otherwise>
													</c:choose>,
													'<tcmis:jsReplace value="${myBean.applicationDisplay}"/>',
													'<tcmis:jsReplace value="${myBean.catPartNo}"/>',
													'<tcmis:jsReplace value="${myBean.partDescription}" processMultiLines="true"/>',
													'${myBean.quantity}',
													'<tcmis:jsReplace value="${myBean.examplePackaging}"/>',
													'${myBean.quantity * myBean.catalogPrice}',
													'<fmt:formatDate value="${myBean.dateNeeded}" pattern="${dateFormatPattern}"/>',
													'${myBean.projectedLeadTime}',
													'<tcmis:jsReplace value="${myBean.notes}" processMultiLines="true"/>',
													<c:choose>
														<c:when test="${myBean.critical eq 'y' or myBean.critical eq 'Y'}">
															true
														</c:when>
														<c:otherwise>
															false
														</c:otherwise>
													</c:choose>,
													'<tcmis:jsReplace value="${myBean.facilityId}"/>',
													'<tcmis:jsReplace value="${myBean.catalogId}"/>',
													'<tcmis:jsReplace value="${myBean.catalogCompanyId}"/>',
													'<tcmis:jsReplace value="${myBean.partGroupNo}"/>',
													'${myBean.catalogPrice}',
													'${myBean.baselinePrice}',
													'${myBean.currencyId}',
													'<tcmis:jsReplace value="${myBean.orderQuantityRule}"/>',
													'<tcmis:jsReplace value="${myBean.inventoryGroup}"/>',
													'<tcmis:jsReplace value="${myBean.itemType}"/>',
													'<tcmis:jsReplace value="${myBean.stockingMethod}"/>',
													<c:choose>
														<c:when test="${partApproved}">
															true
														</c:when>
														<c:otherwise>
															false
														</c:otherwise>
													</c:choose>,
													'${myBean.quantity}',
													'<tcmis:jsReplace value="${myBean.originalApplication}"/>',
													'<tcmis:jsReplace value="${myBean.application}"/>'
												]
											}
										</c:forEach>
									]
								};
								
								showUpdateLinks = true;
	                	</script>
	            	</c:otherwise>
	            </c:choose>
	            
	            <!-- Hidden element start -->
	            <div id="hiddenElements" style="display: none;">
	            	<%-- Retrieve all the Search Criteria here for re-search after update--%>
	            	<tcmis:setHiddenFields beanName="searchInput"/>
					<input type="hidden" name="blockBefore_dateNeeded" id="blockBefore_dateNeeded" value=""/>
					<input type="hidden" name="blockAfter_dateNeeded" id="blockAfter_dateNeeded" value=""/>
					<input type="hidden" name="blockBeforeExclude_dateNeeded" id="blockBeforeExclude_dateNeeded" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>"/>
					<input type="hidden" name="blockAfterExclude_dateNeeded" id="blockAfterExclude_dateNeeded" value=""/>
					<input type="hidden" name="inDefinite_dateNeeded" id="inDefinite_dateNeeded" value=""/>
				</div>
	            <!-- Hidden elements end -->
		    </div>
		</div>
	</tcmis:form>
</body>
</html:html>