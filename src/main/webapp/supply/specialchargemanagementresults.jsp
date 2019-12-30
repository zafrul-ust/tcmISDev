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
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="expires" content="-1">
	<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
	<%@ include file="/common/locale.jsp"%>
	<tcmis:gridFontSizeCss />

	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script	type="text/javascript" src="/js/common/commonutil.js"></script>
	<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
	<script src="/js/common/disableKeys.js"></script>

	<!-- This handels the menu style and what happens to the right click on the whole page -->
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>

	<!-- These are for the Grid-->
	<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
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

	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/supply/specialchargemanagement.js"></script>

	<title><fmt:message key="specialChargeManagement" /></title>

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

    	<%-- Define the right click menus --%>
    	with(milonic=new menuname("modifyMenu")){
			top="offset=2"
			style = contextStyle;
			margin=3
	        aI("text=;url=;");
    	}

    	<%-- Initialize the Right Mouse Click Menus --%>
    	drawMenus();

    	<%-- Define the columns for the result grid --%>
    	var config = [
			{
				columnId: "permission"
			},
			{
				columnId:"radianPo",
				columnName:'<fmt:message key="label.po"/>',
				width:6
			},
			{
				columnId:"supplier",
				columnName:'<fmt:message key="label.supplier"/>',
				width:7
			},
			{
				columnId:"supplierName",
				columnName:'<fmt:message key="label.suppliername"/>',
				tooltip:"Y",
				width:20
			},
			{
				columnId:"dateInserted",
				columnName:'<fmt:message key="label.dateinserted"/>',
				width:7
			},
			{
				columnId:"insertedByName",
				columnName:'<fmt:message key="label.insertedby"/>',
				width:8,
				tooltip:"Y"
			},
			{
				columnId:"lastUpdatedDate",
				columnName:'<fmt:message key="label.laastUpdatedDate"/>',
				width:10
			},
			{
				columnId:"lastUpdatedByName",
				columnName:'<fmt:message key="label.lastUpdatedBy"/>',
				width:8,
				tooltip:"Y"
			},
			{
				columnId:"statusDisplay",
				columnName:'<fmt:message key="label.status"/>',
				width:8
			},
			{
				columnId:"insertedBy"
			},
			{
				columnId:"lastUpdatedBy"
			},
			{
				columnId:"status"
			},
			{
				columnId:"toModify"
			}
		];

		<%-- Define the grid options--%>
		var gridConfig = {
			divName: 'specialChargePOBean',			<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
			beanData: 'jsonMainData',			<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
			beanGrid: 'beanGrid',				<%--  variable to put the grid object in for later use --%>
			config: 'config',					<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
			onRightClick: buildRightClickOption,<%--  a javascript function to be called on right click with rowId & cellId as args --%>
			onRowSelect: selectRow,
			submitDefault: true
		};

		<%-- Standard var for Internationalized messages--%>
		var messagesData={
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			recordFound:"<fmt:message key="label.recordFound"/>",
			searchDuration:"<fmt:message key="label.searchDuration"/>",
			minutes:"<fmt:message key="label.minutes"/>",
			seconds:"<fmt:message key="label.seconds"/>",
			forVal:"<fmt:message key="label.for"/>",
			pleasewait:"<fmt:message key="label.pleasewait"/>",
			itemInteger:"<fmt:message key="error.item.integer"/>",
			activate:"<fmt:message key="label.activate"/>",
			inactivate:"<fmt:message key="label.inactivate"/>",
			viewPurchaseOrder:"<fmt:message key="label.viewpurchaseorder"/>",
			purchaseOrder:"<fmt:message key="purchaseOrder" />"
		};
	</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">
	<tcmis:form action="/specialchargemanagementresults.do" onsubmit="return submitFrameOnlyOnce();">
		<div class="interface" id="resultsPage">
	        <div class="backGroundContent">
	            <div id="specialChargePOBean" style="width: 100%; height: 400px;" style="display: none;"></div>
				<!-- Error Messages Begins -->
				<div id="errorMessagesAreaBody" style="display:none;">
					<html:errors/>${tcmISError}
					<c:forEach var="error" items="${tcmISErrors}">
						${error}<br/>
					</c:forEach>
				</div>
	            <c:choose>
	            	<%-- If the collection is empty say no data found --%>
	            	<c:when test="${empty specialChargePoColl}" >
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
	                	    var jsonMainData = {
	                	    rows:[
	                	        <c:forEach var="myBean" items="${specialChargePoColl}" varStatus="status">
	                	        	<fmt:formatDate var="fmtInsertedDate" value="${myBean.dateInserted}" pattern="${dateFormatPattern}"/>
		                	        <fmt:formatDate var="fmtlastUpdatedDate" value="${myBean.lastUpdatedDate}" pattern="${dateFormatPattern}"/>
	                	        	<c:choose>
	                	        		<c:when test="${myBean.status == 'A'}">
	                	        			<c:set var="statusDisplay"><fmt:message key="label.active"/></c:set>
	                	        		</c:when>
	                	        		<c:otherwise>
                	        				<c:set var="statusDisplay"><fmt:message key="label.inactive"/></c:set>
	                	        		</c:otherwise>
	                	        	</c:choose>
	                            	<c:if test="${!status.first}">,</c:if>
	                            	{
	                            		id:${status.count},
	                                	data:[
	                                	    'Y',
	                                	    '<tcmis:jsReplace value="${myBean.radianPo}"/>',
	                                	    '<tcmis:jsReplace value="${myBean.supplier}"/>',
	                                	    '<tcmis:jsReplace value="${myBean.supplierName}" processMultiLines="true" />',
	                                	    '${fmtInsertedDate}',
	                                	    '<tcmis:jsReplace value="${myBean.insertedByName}"/>',
	                                	    '${fmtlastUpdatedDate}',
	                                	    '<tcmis:jsReplace value="${myBean.lastUpdatedByName}"/>',
	                                	    '${statusDisplay}',
	                                	    '<tcmis:jsReplace value="${myBean.insertedBy}"/>',
	                                	    '<tcmis:jsReplace value="${myBean.lastUpdatedBy}"/>',
	                                	    '<tcmis:jsReplace value="${myBean.status}"/>',
	                                	    false
										]
	                            	}
	                        	</c:forEach>
							]};
	                	</script>
	            	</c:otherwise>
	            </c:choose>
	
	            <!-- Hidden element start -->
	            <div id="hiddenElements" style="display: none;">
	            	<%-- Retrieve all the Search Criteria here for re-search after update--%>
	            	<tcmis:setHiddenFields beanName="searchInput"/>
				</div>
	            <!-- Hidden elements end -->
		    </div>
		</div>
	</tcmis:form>
</body>
</html:html>