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
	<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="expires" content="-1"/>
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
	
	<!-- For Calendar support -->
	<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
	<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
	<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
	
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
	<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
	
	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/client/catalog/reordermrmain.js"></script>

	<title><fmt:message key="label.reorderfrommr"/> <tcmis:jsReplace value="${param.prNumber}"/></title>

	<script language="JavaScript" type="text/javascript">
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = {
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			searchInput:"<fmt:message key="label.inputSearchText"/>",
			errors:"<fmt:message key="label.errors"/>",
			pleaseSelect:"<fmt:message key="label.pleaseselect"/>",
			xxPositiveInteger:"<fmt:message key="label.xxpositiveinteger"/>",
			multiplier:"<fmt:message key="label.multiplier"/>",
			legend:"<fmt:message key="label.legend"/>"
		};
		
		var facilityArr = new Array();
		var applicationArr = new Array();
		var accountSysArr = new Array();
		applicationArr[""] = new Array();
		accountSysArr[""] = new Array();
		<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
			<c:if test="${myWorkareaFacilityViewBean.active == 'y'}">
				<c:set var="facilityId"><tcmis:jsReplace value="${myWorkareaFacilityViewBean.facilityId}"/></c:set>
				facilityArr.push(
					{
						id: '${facilityId}',
						name: '<tcmis:jsReplace value="${myWorkareaFacilityViewBean.facilityName}"/>'
					}
				);
				
				applicationArr['${facilityId}'] = new Array(
					<c:forEach var="applicationBean" items="${myWorkareaFacilityViewBean.applicationBeanCollection}" varStatus="status1">
						{
							id: '<tcmis:jsReplace value="${applicationBean.application}"/>',
							name:  '<tcmis:jsReplace value="${applicationBean.applicationDesc}"/>'
						}
						<c:if test="${not status1.last}">,</c:if>
					</c:forEach>
				);
				
				accountSysArr['${facilityId}'] = new Array(
					<c:forEach var="prRulesViewBean" items="${myWorkareaFacilityViewBean.prRulesViewBeanCollection}" varStatus="status1">
						{
							id: '<tcmis:jsReplace value="${prRulesViewBean.accountSysId}"/>',
							name:  '<tcmis:jsReplace value="${prRulesViewBean.accountSysDesc}"/>'
						}
						<c:if test="${not status1.last}">,</c:if>
					</c:forEach>
				);
			</c:if>
		</c:forEach>
    </script>
</head>

<body bgcolor="#ffffff"	onload="loadLayoutWin('','reorderMr'); initializeDropDowns();" onresize="resizePage();">
	<div class="interface" id="mainPage" style="">
		<!-- Search Frame Begins -->
		<div id="searchFrameDiv" class="contentArea">
			<tcmis:form action="/reordermrresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
													<td class="optionTitleBoldLeft" style="border-bottom:1px solid gray" colspan="2">
														<fmt:message key="label.originalrequest" />:&nbsp;<tcmis:jsReplace value="${param.prNumber}"/>
													</td>
												</tr>
												<tr>
													<td style="padding: 6px 3px 0px" class="optionTitleBoldRight"><fmt:message key="label.facility" />:</td>
													<td style="padding: 6px 3px 0px" class="optionTitleLeft">
														<select name="facilityId" id="facilityId" class="selectBox" onchange="facilityChanged()"></select>
													</td>
												</tr>
												<tr>
													<td class="optionTitleBoldRight"><fmt:message key="label.workarea" />:</td>
													<td class="optionTitleLeft">
														<select name="applicationId" id="applicationId" class="selectBox" onchange="applicationChanged()"></select>
													</td>
												</tr>
												<tr>
													<td class="optionTitleBoldRight"><fmt:message key="label.accountsystem" />:</td>
													<td class="optionTitleLeft">
														<select name="accountSysId" id="accountSysId" class="selectBox" onchange="accountSysChanged()"></select>
													</td>
												</tr>
												<tr>
													<td class="optionTitleBoldRight"><fmt:message key="label.multiplyquantitiesby" />:</td>
													<td class="optionTitleBoldLeft">
														<input class="inputBox" type="text" name="multiplier" id="multiplier" value="" size="5"/>
														&nbsp;<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="multiplyButton" value="<fmt:message key="label.apply"/>" onclick="multiplyQuantities();" />
														&nbsp;<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="multiplyButton" value="<fmt:message key="label.reset"/>" onclick="resetQuantityMultiplication();" />
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
					
					<div id="transitDialogWin" class="errorMessages" style="display: none;overflow: auto;">
						<table width="100%" border="0" cellpadding="2" cellspacing="1">
							<tr><td>&nbsp;</td></tr>
							<tr><td>&nbsp;</td></tr>
							<tr><td>&nbsp;</td></tr>
							<tr>
								<td align="center" id="transitLabel"><fmt:message key="label.pleasewait"/></td>
							</tr>
							<tr>
								<td align="center">
									<img src="/images/rel_interstitial_loading.gif" align="middle"/>
								</td>
							</tr>
						</table>
					</div>
					
					<!-- Hidden element start -->
					<div id="hiddenElements" style="display: none;">
						<input type="hidden" name="uAction" id="uAction" value="" />
						<input type="hidden" name="startSearchTime" id="startSearchTime" value="" />
						<input type="hidden" name="prNumber" id="prNumber" value="<tcmis:jsReplace value="${param.prNumber}"/>"/>
						<input type="hidden" name="isFirstLoad" id="isFirstLoad" value="Y"/>
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
												<span id="updateResultLink" style="display: none">
													<span><a href="#" onclick="resultFrame.checkOut();"><fmt:message key="label.checkout"/></a></span>
													| <span><a href="#" onclick="showLegends();"><fmt:message key="label.showlegend"/></a></span>
													| <span><a href="#" onclick="window.close();"><fmt:message key="label.cancel"/></a></span>
												</span>
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

	<%-- show legend Begins --%>
	<div id="showLegendArea" style="display: none; overflow: auto;">
		<table width="100%" class="tableResults" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="100px" class="lightgray">&nbsp;</td>
				<td class="legendText"><fmt:message key="msg.partnotworkareaapproved" /></td>
			</tr>
		</table>
	</div>
	<%-- show legend Ends --%>
</body>
</html:html>