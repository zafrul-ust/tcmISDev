<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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
	<!--Use this tag to get the correct CSS class.
    This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
	<tcmis:gridFontSizeCss />
	<%-- Add any other stylesheets you need for the page here --%>

	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<!-- This handles all the resizing of the page and frames -->
	<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
	<!-- This handels which key press events are disabeled on this page -->
	<script type="text/javascript" src="/js/common/disableKeys.js"></script>
	
	<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
	<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
	<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
	<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
	
	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/supply/specialchargemanagement.js"></script>

	<title><fmt:message key="specialChargeManagement" /></title>
	
	<script language="JavaScript" type="text/javascript">
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData= {
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			searchInput:"<fmt:message key="label.inputSearchText"/>",
			emptySearch:"<fmt:message key="label.searchtextororderdate"/>",
			errors:"<fmt:message key="label.errors"/>",
			itemInteger:"<fmt:message key="error.item.integer"/>",
			purchaseOrder:"<fmt:message key="purchaseOrderNew" />"
		};
		
		var readOnly = true;
		<c:set var="readOnly" value="true"/>
		<tcmis:facilityPermission indicator="true" userGroupId="POConfirmationApprover">
			readOnly = false;
			<c:set var="readOnly" value="false"/>
		</tcmis:facilityPermission>
    </script>
</head>

<body bgcolor="#ffffff"	onload="loadLayoutWin('','specialChargeManagement');" onresize="resizeFrames();">
	<div class="interface" id="mainPage" style="">
		<!-- Search Frame Begins -->
		<div id="searchFrameDiv" class="contentArea">
			<tcmis:form action="/specialchargemanagementresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
				<div class="contentArea">
					<!-- Search Option Begins -->
					<table id="searchMaskTable" border="0" cellpadding="0"	cellspacing="0">
						<tr>
							<td>
								<div class="roundcont filterContainer">
									<div class="roundright">
										<div class="roundtop">
											<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter"	style="display: none" /></div>
										</div>
										<div class="roundContent" id="searchTableDiv">
											<!-- Insert all the search option within this div -->
											<!-- Search Option Table Start -->
											<table id="searchTable" width="100%" border="0" cellpadding="0"	cellspacing="0" class="tableSearch">
												<tr>
													<td class="optionTitleBoldRight"><fmt:message key="label.dateinserted" />:</td>
													<td class="optionTitleBoldLeft" nowrap>
														<fmt:message key="label.from" />&nbsp;
														<input class="inputBox pointer" readonly type="text" name="fromDate" id="fromDate" value="" onclick="return getCalendar(document.genericForm.fromDate,null,document.genericForm.toDate);" maxlength="10" size="9" />
														&nbsp;<fmt:message key="label.to" />&nbsp;
														<input class="inputBox pointer" readonly type="text" name="toDate" id="toDate" value="" onclick="return getCalendar(document.genericForm.toDate,document.genericForm.fromDate);" maxlength="10" size="9" />
													</td>
												</tr>
												<tr>
													<td class="optionTitleBoldRight"><fmt:message key="label.search" />:</td>
													<td nowrap class="optionTitleBoldLeft">
														<select name="searchField" class="selectBox" id="searchField">
															<option value="radian_po"><fmt:message key="label.po" /></option>
															<option value="supplier"><fmt:message key="label.supplier" /></option>
														</select>&nbsp;
															<select name="searchMode" class="selectBox" id="searchMode">
															<option value="is"><fmt:message key="label.is" /></option>
															<option value="contains"><fmt:message key="label.contains" /></option>
															<option value="startsWith"><fmt:message key="label.startswith" /></option>
															<option value="endsWith"><fmt:message key="label.endswith" /></option>
														</select>&nbsp;
														<input class="inputBox" type="text" name="searchArgument" id="searchArgument" value="" size="10"/>
													</td>
												</tr>
												<tr>
													<td class="optionTitleLeft" colspan="2">
														<input onclick="_submitSearchForm();" type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.search"/>" name="search" id="search"/>
														<c:if test="${!readOnly}">
															&nbsp;
															<input onclick="viewPurchaseOrder(false);" type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.addspecialchargeposhort"/>" name="addSpecialCharge" id="addSpecialCharge"/>
														</c:if>
													</td>
												</tr>
											</table>
											<!-- Search Option Table end -->
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
					<!-- Search Option Ends -->
					<!-- Error Messages Begins -->
					<!-- Build this section only if there is an error message to display.
		   			For the search section, we show the error messages within its frame-->
					<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
						<div class="spacerY">
							&nbsp;
							<div id="searchErrorMessagesArea" class="errorMessages">
								<html:errors />
							</div>
						</div>
					</c:if>
					<!-- Error Messages Ends -->
					<!-- Hidden element start -->
					<div id="hiddenElements" style="display: none;">
						<input type="hidden" name="uAction" id="uAction" value="" />
						<input type="hidden" name="startSearchTime" id="startSearchTime" value="" />
					</div>
					<!-- Hidden elements end -->
				</div>
			</tcmis:form>
		</div>
		<!-- Search Frame Ends -->
		
		<!-- Result Frame Begins -->
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
			<!-- Transit Page Begins -->
			<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				<br/><br/><br/><fmt:message key="label.pleasewait"/><br/><br/><br/>
				<img src="/images/rel_interstitial_loading.gif" align="middle"/>
			</div>
			<!-- Transit Page Ends -->
			<div id="resultGridDiv" style="display: none;">
				<!-- Search results start -->
				<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
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
											<div id="mainUpdateLinks" style="display: none">
											</div> <%-- mainUpdateLinks Ends --%>
										</div> <%-- boxhead Ends --%>
										<div class="dataContent">
											<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
										</div>
									
										<%-- result count and time --%>
										<div id="footer" class="messageBar"></div>
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
		<!-- Result Frame Ends -->
	</div>
	<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesArea" class="errorMessages" style="display: none; overflow: auto;"></div>
</body>
</html:html>