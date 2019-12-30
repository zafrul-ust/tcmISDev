<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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
	<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
	<%-- This handels which key press events are disabeled on this page --%>
	<script type="text/javascript" src="/js/common/disableKeys.js"></script>

	<%-- This handels the menu style and what happens to the right click on the whole page --%>
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>

	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
	<script type="text/javascript" src="/js/catalog/addusertoemaillist.js"></script>

	<!-- These are for the Grid, uncomment if you are going to use the grid -->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
		
	<title><fmt:message key="addUserToEmailList"/></title>

	<script language="JavaScript" type="text/javascript">
		windowCloseOnEsc = true;

		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData={
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			all:"<fmt:message key="label.all"/>",
			showlegend:"<fmt:message key="label.showlegend"/>",
			errors:"<fmt:message key="label.errors"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			itemInteger:"<fmt:message key="error.item.integer"/>"
		};
		
		var companyColl = new Array();
		var catalogColl = new Array();
		<c:forEach var="company" items="${companyCollection}" varStatus="status">
			if (typeof(catalogColl["<tcmis:jsReplace value='${company.companyId}'/>"]) == 'undefined') {
				companyColl.push({
					id:"<tcmis:jsReplace value="${company.companyId}"/>",
					name:"<tcmis:jsReplace value="${company.companyName}"/>",
					mfrNotificationLevel:"<tcmis:jsReplace value="${company.mfrNotificationLevel}"/>",
					selected:${not empty param.company && param.company eq company.companyId}
				});
				catalogColl["<tcmis:jsReplace value="${company.companyId}"/>"] = new Array();
			}
			catalogColl["<tcmis:jsReplace value="${company.companyId}"/>"].push({
				id:"<tcmis:jsReplace value="${company.catalogId}"/>",
				name:"<tcmis:jsReplace value="${company.catalogDesc}"/>",
				catalogCompanyId:"<tcmis:jsReplace value="${company.catalogCompanyId}"/>"
			});
		</c:forEach>
	</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','addUserToEmailList');initializeSearchInputs();" onunload="closeAllchildren()"  onresize="resizeFrames()">
	<div class="interface" id="mainPage" style="">
		<div id="searchFrameDiv">
			<div class="contentArea">
				<tcmis:form action="/addusertoemaillistresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
					<table id="searchMaskTable" width="400" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<div class="roundcont filterContainer">
									<div class="roundright">
										<div class="roundtop">
											<div class="roundtopright"> 
												<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
											</div>
										</div>
										<div class="roundContent">
											<table width="100%" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
												<tr>
													<td class="optionTitleBoldRight">
														<fmt:message key="label.usergroup" />:
													</td>
													<td class="optionTitleLeft">
														<tcmis:jsReplace value="${userGroupName}"/>
													</td>
												</tr>
												<tr>
													<td class="optionTitleBoldRight">
														<fmt:message key="label.company" />:
													</td>
													<td class="optionTitleLeft">
														<select name="companyId" id="companyId" class="selectBox" onchange="companyChanged();"></select>
													</td>
												</tr>
												<tr id="catalogRow" style="display: ">
													<td class="optionTitleBoldRight">
														<fmt:message key="label.catalog" />:
													</td>
													<td class="optionTitleLeft">
														<select name="catalogId" id="catalogId" class="selectBox" onchange="catalogChanged();"></select>
													</td>
												</tr>
												<tr>
													<td colspan="2">
													  <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="button.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return submitSearchForm()"/>
													  &nbsp;
													  <input name="addUserButton" type="button" class="inputBtns" value="<fmt:message key="label.addusertogroup" />" id="addUserButton" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="addUser();"/>
													</td>
												</tr>
											</table>
										</div>
										<div class="roundbottom">
											<div class="roundbottomright">
												<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
											</div>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</table>
					<div id="hiddenElements" style="display: none;">
						<input name="uAction" id="uAction" type="hidden" value="" />
						<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
						<input name="userGroupId" id="userGroupId" type="hidden" value="<tcmis:jsReplace value="${userGroupId}"/>" />
						<input name="catalogCompanyId" id="catalogCompanyId" type="hidden" value="" />
						<input name="mfrNotificationLevel" id="mfrNotificationLevel" type="hidden" value="" />
						<input name="levelOfControl" id="levelOfControl" type="hidden" value="<tcmis:jsReplace value="${levelOfControl}"/>" />
						<input name="personnelCompanyId" id="personnelCompanyId" type="hidden" value="<tcmis:jsReplace value="${personnelCompanyId}"/>" />
						<input name="selectedPersonnelId" id="selectedPersonnelId" value="" type="hidden"/>
					</div>
				</tcmis:form>
			</div> 
		</div>
		
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
			<%-- Transit Page Starts --%>
			<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				<br/><br/><br/><fmt:message key="label.pleasewait"/>
				<br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
			</div>

			<div id="resultGridDiv" style="display: none;">
				<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<div class="roundcont contentContainer">
								<div class="roundright">
									<div class="roundtop">
										<div class="roundtopright">
											<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
										</div>
									</div>
									<div class="roundContent">
										<div class="boxhead">
											<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
											Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.--%>
											<%-- mainUpdateLinks Begins --%>
											<span id="mainUpdateLinks" style="display: none">
												<span id="deleteResultLink"></span>
											</span>
											<%-- mainUpdateLinks Ends --%>
										</div>
										<div class="dataContent">
											<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
										</div>
										<div id="footer" class="messageBar">
										</div>
									</div>
									<div class="roundbottom">
										<div class="roundbottomright">
											<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> 
										</div>
									</div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<%-- Error Messages Begins --%>
	<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;"></div>
</body>
</html:html>