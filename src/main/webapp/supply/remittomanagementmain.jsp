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
	<script type="text/javascript" src="/js/supply/remittomanagement.js"></script>

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
		
	<title><fmt:message key="supplierRemitToAddressManagement"/></title>

	<script language="JavaScript" type="text/javascript">
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
			itemInteger:"<fmt:message key="error.item.integer"/>",
			noCheckboxSelected:"<fmt:message key="error.nocheckboxselected"/>",
			includeLocation:"<fmt:message key="label.includelocation"/>"
		};
	</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','remitToManagement');submitSearchForm();" onunload="closeAllchildren()"  onresize="resizeFrames()">
	<div class="interface" id="mainPage" style="">
		<div id="searchFrameDiv">
			<div class="contentArea">
				<tcmis:form action="/remittomanagementresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
														<fmt:message key="label.supplier" />:
													</td>
													<td class="optionTitleLeft" nowrap>
														${supplierName}
													</td>
												</tr>
												<tr>
													<td class="optionTitleBoldRight"nowrap>
														<fmt:message key="label.includelocation" />:
													</td>
													<td class="optionTitleLeft" nowrap>
														<input type="checkbox" name="statusCheckbox" value="All" id="statusAll" onchange="statusCheckboxChanged(this);"/><fmt:message key="label.all" />&nbsp;
														<input type="checkbox" name="statusCheckbox" value="Approved" id="statusApproved" onchange="statusCheckboxChanged(this);" checked/><fmt:message key="label.approved" />&nbsp;
														<input type="checkbox" name="statusCheckbox" value="Inactive" id="statusInactive" onchange="statusCheckboxChanged(this);"/><fmt:message key="label.inactive" />&nbsp;
														<input type="checkbox" name="statusCheckbox" value="Other" id="statusOther" onchange="statusCheckboxChanged(this);"/><fmt:message key="label.other" />&nbsp;
														<input type="checkbox" name="statusCheckbox" value="Rejected" id="statusRejected" onchange="statusCheckboxChanged(this);"/><fmt:message key="label.rejected" />
													</td>
												</tr>
												<tr>
													<td colspan="3" nowrap>
													  <input name="refreshButton" type="button" class="inputBtns" value="<fmt:message key="label.refresh"/>" id="refreshButton" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="submitSearchForm()"/>&nbsp;
													  <input name="newRemitToAddressButton" type="button" class="inputBtns" value="<fmt:message key="label.newremittoaddress"/>" id="newRemitToAddressButton" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="createNewRemitToAddress();"/>
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
						<input name="status" id="status" type="hidden" value="" />
						<input name="supplier" id="supplier" type="hidden" value="<tcmis:jsReplace value="${supplier}"/>" />
						<input name="supplierName" id="supplierName" type="hidden" value="<tcmis:jsReplace value="${supplierName}"/>" />
						<input name="callerName" id="callerName" type="hidden" value="<tcmis:jsReplace value="${callerName}"/>" />
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
												<span id="changeResultStatusLink"></span>
												<span id="returnResultLink"></span>
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