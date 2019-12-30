<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

	<%--Use this tag to get the correct CSS class.
	This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. --%>
	<tcmis:gridFontSizeCss />

	<%-- Add any other stylesheets you need for the page here --%>

	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
	<script src="/js/common/disableKeys.js"></script>

	<%-- Right click menu support --%>
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>

	<!-- These are for the Grid-->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>

	<script type="text/javascript" src="/js/supply/remittomanagement.js"></script>

	<script language="JavaScript" type="text/javascript">
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData = {
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			recordFound:"<fmt:message key="label.recordFound"/>",
			searchDuration:"<fmt:message key="label.searchDuration"/>",
			minutes:"<fmt:message key="label.minutes"/>",
			seconds:"<fmt:message key="label.seconds"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			itemInteger:"<fmt:message key="error.item.integer"/>",
			noRowSelected:"<fmt:message key="error.norowselected"/>",
			cityStateZipCountry:"<fmt:message key="label.citystatezipcountry"/>",
			unapprovedSelected:"<fmt:message key="error.unapprovedbillinglocation"/>",
			returnSelectedLocation:"<fmt:message key="label.returnselectedlocation"/>",
			activateLocation:"<fmt:message key="label.activatelocation"/>",
			inactivateLocation:"<fmt:message key="label.inactivatelocation"/>"
		};

		var config = [
			{
				columnId:"locationDesc",
				columnName:'<fmt:message key="label.locationdesc"/>',
				tooltip: true,
				width:14
			},
			{
            	columnId:"billingLocationId",
            	columnName:'<fmt:message key="label.remittolocation"/>',
            	tooltip: true,
            	submit: true
            },
			{
				columnId:"addressLine1",
				columnName:'<fmt:message key="label.addressline1"/>',
				tooltip: true,
				width:18
			},
			{
				columnId:"addressLine2",
				columnName:'<fmt:message key="label.addressline2"/>',
				tooltip: true,
				width:18
			},
			{
				columnId:"cityStateZipCountry",
				columnName:messagesData.cityStateZipCountry.replace(/,/g, "-"), //config reader confuses comma within string with that separating columns, which mess up column names' display
				tooltip: true,
				width:17
			},
			{
				columnId:"status",
				columnName:'<fmt:message key="label.status"/>',
				tooltip: true,
				submit: true,
				align:'center',
				width:6
			},
			{//5
				columnId:"sapVendorCode",
				columnName:'<fmt:message key="label.erpvendorcode"/>',
				submit: true,
				width:9
			},
			{
				columnId:"supplier",
				submit: true
			},
			{//7
				columnId:"updated",
				submit: true
			}
		];
		
		<%-- Define the grid options--%>
		var gridConfig = {
			divName: 'SupplierBillingLocationViewBean',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
			beanData: 'jsonMainData',			<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
			beanGrid: 'beanGrid',				<%--  variable to put the grid object in for later use --%>
			config: 'config',					<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
			onRowSelect: selectRow
		};
	</script>
</head>

<body bgcolor="#ffffff" onload="myResultOnLoadWithGrid(gridConfig)">
	<tcmis:form action="/remittomanagementresults.do" onsubmit="return submitFrameOnlyOnce();">
		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<c:set var="dataCount" value='${0}'/>
				<c:choose>
					<c:when test="${!empty supplierBillingLocationViewBeanCollection}">
						<div id="SupplierBillingLocationViewBean" style="width:100%;height:400px;"></div>
						<script type="text/javascript">
							var jsonMainData = {
								rows:[
									<c:forEach var="bean" items="${supplierBillingLocationViewBeanCollection}" varStatus="status">
										{
											id: ${status.index +1},
											data: [
												'<tcmis:jsReplace value="${bean.locationDesc}"/>',
												'<tcmis:jsReplace value="${bean.billingLocationId}"/>',
												'<tcmis:jsReplace value="${bean.addressLine1}" processMultiLines="true"/>',
												'<tcmis:jsReplace value="${bean.addressLine2}" processMultiLines="true"/>',
												'<tcmis:jsReplace value="${bean.city}"/>, <tcmis:jsReplace value="${bean.stateAbbrev}"/> <tcmis:jsReplace value="${bean.zip}"/> <tcmis:jsReplace value="${bean.countryAbbrev}"/>',
												'<tcmis:jsReplace value="${bean.status}"/>',
												'<tcmis:jsReplace value="${bean.sapVendorCode}"/>',
												'<tcmis:jsReplace value="${bean.supplier}"/>',
												'false'
											]
										}
										<c:if test="${!status.last}">,</c:if>
										<c:set var="dataCount" value='${dataCount+1}'/>
									</c:forEach>
								]
							};
						</script>
					</c:when>
					<c:otherwise>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
							<tr>
								<td width="100%">
									<fmt:message key="main.nodatafound"/>
								</td>
							</tr>
						</table>
					</c:otherwise>
				</c:choose>

				<div id="hiddenElements" style="display: none;">
					<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
					<input type="hidden" name="uAction" id="uAction" value="" />
					<input name="supplier" id="supplier" type="hidden" value="<tcmis:jsReplace value="${supplier}"/>" />
					<input name="supplierName" id="supplierName" type="hidden" value="<tcmis:jsReplace value="${supplierName}"/>" />
	            	<%-- Retrieve all the Search Criteria here for re-search after update--%>
	            	<tcmis:setHiddenFields beanName="searchInput"/>
				</div>
			</div>
		</div>
	</tcmis:form>

	<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
	So this is just used to feed the pop-up in the main page.
	Similar divs would have to be built to show any other messages in a pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesAreaBody" style="display:none;">
		${tcmISError}<br/>
		<c:forEach items="${tcmISErrors}" varStatus="status">
			${status.current}<br/>
		</c:forEach>
	</div>
	
	<script type="text/javascript">
		/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
		<c:choose>
			<c:when test="${empty tcmISErrors and empty tcmISError}">
				showErrorMessage = false;
			</c:when>
			<c:otherwise>
				showErrorMessage = true;
			</c:otherwise>
		</c:choose>
		
		showUpdateLinks = true;
	</script>
</body>
</html:html>