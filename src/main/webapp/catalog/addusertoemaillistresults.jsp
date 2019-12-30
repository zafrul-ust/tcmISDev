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

	<script type="text/javascript" src="/js/catalog/addusertoemaillist.js"></script>

	<title>
		<fmt:message key="label.material"/>
	</title>

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
			deleteselect:"<fmt:message key="label.deleteselecteduser"/>"
		};
		
		var config = [
			{//0
				columnId:"fullName",
				columnName:'<fmt:message key="label.name"/>',
				width:15, 
				align:"center"
			},
			{
				columnId:"email",
				columnName:'<fmt:message key="label.email"/>',
				width:20,
				align:"center"
			},
			{
				columnId:"personnelId",
				submit:true
			},
			<c:if test="${levelOfControl == 'catalog'}">
				{
					columnId:"facilityId",
					submit:true
				},
				{
					columnId:"admin",
					submit:true
				},
				{//5
					columnId:"catalogId",
					submit:true
				},
				{
					columnId:"catalogCompanyId",
					submit:true
				},
			</c:if>
			{//7
				columnId:"update",
				submit:true
			}
		];
		
		<%-- Define the grid options--%>
		var gridConfig = {
			divName: 'addUserToEmailListBean',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
			beanData: 'jsonMainData',			<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
			beanGrid: 'beanGrid',				<%--  variable to put the grid object in for later use --%>
			config: 'config',					<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
			onRowSelect: selectRow,
			submitDefault : false
		};
	</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);parent.$('deleteResultLink').innerHTML = '';">
	<tcmis:form action="/addusertoemaillistresults.do" onsubmit="return submitFrameOnlyOnce();">
		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<c:set var="dataCount" value='${0}'/>
				<c:choose>
					<c:when test="${!empty addUserToEmailListBeanCollection}">
						<div id="addUserToEmailListBean" style="width:100%;height:400px;"></div>
						<script type="text/javascript">
							var jsonMainData = {
								rows:[
									<c:forEach var="bean" items="${addUserToEmailListBeanCollection}" varStatus="status">
										{
											id: ${status.index +1},
											data: [
												'<tcmis:jsReplace value="${bean.name}" />',
												'<tcmis:jsReplace value="${bean.email}" />',
												'<tcmis:jsReplace value="${bean.personnelId}" />',
												<c:if test="${levelOfControl == 'catalog'}">
													'<tcmis:jsReplace value="${bean.facilityId}" />',
													'<tcmis:jsReplace value="${bean.admin}" />',
													'<tcmis:jsReplace value="${bean.catalogId}" />',
													'<tcmis:jsReplace value="${bean.catalogCompanyId}" />',
												</c:if>
												false
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
					<input name="personnelCompanyId"  id="personnelCompanyId" value="<tcmis:jsReplace value="${personnelBean.companyId}" />" type="hidden" />
					<input name="levelOfControl"  id="levelOfControl" value="<tcmis:jsReplace value="${levelOfControl}" />" type="hidden" />
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