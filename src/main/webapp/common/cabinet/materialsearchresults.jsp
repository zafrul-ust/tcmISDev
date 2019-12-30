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

	<script type="text/javascript" src="/js/common/cabinet/materialsearch.js"></script>

	<title>
		<fmt:message key="label.material"/>
	</title>

	<script language="JavaScript" type="text/javascript">
		<!--
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
			selectedRowMsg:"<fmt:message key="label.selectedmaterial"/>"
		};

		var config = [
			{columnId:"materialId", columnName:'<fmt:message key="label.materialid"/>', width:6, align:"left"},
			{columnId:"msdsNumber", columnName:'<fmt:message key="label.msdsnumber"/>', width:6, align:"left"},
			{columnId:"description", columnName:'<fmt:message key="label.materialdescription"/>', width:20, tooltip:true},
			{columnId:"tradeName", columnName:'<fmt:message key="label.tradename"/>', width:20, tooltip:true},
			{columnId:"online", columnName:'<fmt:message key="label.viewonline"/>', width:5},
			{columnId:"mfgDesc", columnName:'<fmt:message key="label.manufacturer"/>', width:20, tooltip:true},
			{columnId:"companyId"},
			{columnId:"catalogCompanyId"},
			{columnId:"catalogId"},
			{columnId:"facilityId"}
			];

		_gridConfig.onRowSelect = selectRow;
		_gridConfig.onRightClick= selectRow;

		// -->
	</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(_gridConfig)<c:if test="${!empty materialBeanCollection}" >;haasGrid.selectRow(0);selectRow(1);</c:if>">
	<tcmis:form action="/materialsearchresults.do" onsubmit="return submitFrameOnlyOnce();">
		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<c:set var="dataCount" value='${0}'/>

				<c:if test="${!empty materialBeanCollection}" >
					<div id="beanData" style="width:100%;height:400px;"></div>
						<script type="text/javascript">
							<!--
							var jsonData = {
									rows:[<c:forEach var="bean" items="${materialBeanCollection}" varStatus="status">
										{ 	id:${status.index +1},
											data:['${bean.materialId}',
											      '${bean.msdsNumber}',
												  '<tcmis:jsReplace value="${bean.materialDesc}" processMultiLines="true" />',
												  '<tcmis:jsReplace value="${bean.tradeName}" processMultiLines="true" />',
												  '${bean.msdsOnLine}',
												  '<tcmis:jsReplace value="${bean.mfgDesc}" processMultiLines="true" />',
												  '${bean.companyId}','${bean.catalogCompanyId}','${bean.catalogId}','${bean.facilityId}'
											]
										}<c:if test="${!status.last}">,</c:if> <c:set var="dataCount" value='${dataCount+1}'/>
									</c:forEach>]};
							//-->
						</script>
					</div>
				</c:if>

				<c:if test="${empty materialBeanCollection}" >
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
						<tr>
							<td width="100%">
								<fmt:message key="main.nodatafound"/>
							</td>
						</tr>
					</table>
				</c:if>

				<div id="hiddenElements" style="display: none;">
					<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
					<input name="companyId" id="companyId" value="${param.companyId}" type="hidden"/>
					<input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden"/>
					<input name="application" id="application" type="hidden" value="${param.application}" />
					<input name="applicationDesc" id="applicationDesc" type="hidden" value="${param.applicationDesc}" />
					<input name="catalogIdCatalogCompanyIdString" id="catalogIdCatalogCompanyIdString" value="${param.catalogIdCatalogCompanyIdString}" type="hidden" />
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
<!--
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
//-->
</script>

</body>
</html:html>