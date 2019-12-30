<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/cabinet/materialsearchmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>


	<title><fmt:message key="label.addnonmanagedmatl"/></title>

	<script language="JavaScript" type="text/javascript">
		<!--
		windowCloseOnEsc = true;
		var originalOpener = null;
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData={
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			all:"<fmt:message key="label.all"/>",
			pleasewait:"<fmt:message key="label.pleasewait"/>",
			showlegend:"<fmt:message key="label.showlegend"/>",
			errors:"<fmt:message key="label.errors"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			itemInteger:"<fmt:message key="error.item.integer"/>"
		};
	
		// -->
	</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','materialSearch');" onunload="try{opener.parent.closeTransitWin();}catch(ex){}"  onresize="resizeFrames()">
	<div class="interface" id="mainPage" style="">
		<div id="searchFrameDiv">
			<div class="contentArea">
				<tcmis:form action="/materialsearchresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
					<table id="searchMaskTable" width="600" border="0" cellpadding="0" cellspacing="0">
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
												<tr style="display:;">
														<td width="20%" class="optionTitleBoldRight">
															<fmt:message key="label.catalog"/>:
														</td>
														<td width="40%" class="optionTitleLeft">
															<select name="catalogIdCatalogCompanyIdString" id="catalogIdCatalogCompanyIdString" class="selectBox">
																<c:forEach var="cbean" items="${catalogCollection}" varStatus="status">
											  						<c:if test="${status.current.display == 'Y'}"><option value="${status.current.catalogId},${status.current.catalogCompanyId}">${status.current.catalogDesc}</option>
											  						</c:if>
											    				</c:forEach>
       														</select>
														</td>
														<td width="5%"/>
												</tr>
												<tr>
													<td width="20%" class="optionTitleBoldRight">
															<fmt:message key="label.search"/>:
													</td>
													<td width="45%" class="optionTitleBoldLeft" colspan="2" nowrap>
														<select name="searchField" id="searchField" class="selectBox">
															<option value="material_id"><fmt:message key="label.materialid"/></option>
															<option value="material_desc" selected><fmt:message key="label.materialdescription"/></option>
															<option value="mfg_desc"><fmt:message key="label.manufacturer"/></option>
															<option value="cust_msds_no"><fmt:message key="label.msdsnumber"/></option>
       													</select>
       													<select name="searchMode" id="searchMode" class="selectBox">
															<option value="is"><fmt:message key="label.is"/></option>
															<option value="contains" selected><fmt:message key="label.contains"/></option>
       													</select>
       													<input type="text" name="searchArgument" id="searchArgument" size="40" class="inputBox" onkeypress="return onKeyPress()"></input>
													</td>
												</tr>
												<tr>
													<td colspan="3">
													  <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
															 onclick="return submitSearchForm()">
													  <input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 											onclick= "return createXSL()"/>
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
						<input type="hidden" name="userAction" id="userAction" value="search"/>
						<input type="hidden" name="uAction" id="uAction" value="search"/>
						<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
						<input name="companyId" id="companyId" type="hidden" value="${param.companyId}" />
						<input name="facilityId" id="facilityId" type="hidden" value="${param.facilityId}" />
						<input name="application" id="application" type="hidden" value="${param.application}" />
						<input name="applicationDesc" id="applicationDesc" type="hidden" value="${param.applicationDesc}" />
					</div>
				</tcmis:form>
			</div> 
		</div>
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
			<%-- Transit Page Starts --%>
			<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				<br><br><br><fmt:message key="label.pleasewait"/>
				<br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
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
											<span id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
												<span id="returnResultLink"></span>
											</span> <%-- mainUpdateLinks Ends --%>
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

<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" id="transitLabel">
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</td>
		</tr>
	</table>
</div>
<%-- Error Messages Begins --%>
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

</body>
</html:html>