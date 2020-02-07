<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico" />
<%@ include file="/common/opshubig.jsp"%>
<%@ include file="/common/locale.jsp"%>

<!--Use this tag to get the correct CSS class.
This looks at what the users preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp"%>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>
<script type="text/javascript" src="/js/common/opshubig.js"></script>
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title><fmt:message key="newCustomerReturns" /></title>

<script language="JavaScript" type="text/javascript">
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = {
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		all:"<fmt:message key="label.all"/>",
		validvalues:"<fmt:message key="label.validvalues"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		prNumber:"<fmt:message key="label.prnumber"/>",
		xMustBeAFloat:"<fmt:message key="errors.float"/>",
		xIsRequired:"<fmt:message key="errors.required"/>",
		receiptId:"<fmt:message key="label.receiptid"/>",
		xOrY:"<fmt:message key="label.xory"/>"
	};

	function validateForm() {
		if (isWhitespace($v("prNumber")) && isWhitespace($v("prNumberByReceiptId"))) {
			alert(messagesData.xIsRequired.replace("{0}", 
													messagesData.xOrY.replace("{0}", messagesData.prNumber)
																		.replace("{1}", messagesData.receiptId)));
		} else if (!isFloat($v("prNumber"), true)) {
			alert(messagesData.xMustBeAFloat.replace("{0}", messagesData.prNumber));
		} else if (!isFloat($v("prNumberByReceiptId"), true)) {
			alert(messagesData.xMustBeAFloat.replace("{0}", messagesData.receiptId));
		} else {
			return true;
		}
		
		return false;
	}

	function submitSearchForm() {
		if (validateForm() && submitSearchOnlyOnce()) {
			var now = new Date();
			$("startSearchTime").value = now.getTime();
			$('uAction').value = 'search';
			showPleaseWait();
			document.genericForm.target = 'resultFrame';
			document.genericForm.submit();
		}
	}
	
	function createXSL() {
		if (validateForm()) {
			//create hidden inputs to store the display values of below and pass them to server
			var dropdowns = new Array(
				'opsEntityId',
				'hub',
				'inventoryGroup',
				'prNumber'
			);
			setDropDownNames(dropdowns);
			
			$('uAction').value = 'createExcel';
			openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', 'CustomerReturnReport', '650', '600', 'yes');
			document.genericForm.target = 'CustomerReturnReport';
			window.setTimeout("document.genericForm.submit();", 50);
		}
	}
</script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout.
If you dont want to use the layout set javascript variable useLayout=false;
It is important to send the pageId if this page is going to open in a tab in the application.
You can also call any functions you need to do your search initialization for drop downs.
-->
<body bgcolor="#ffffff" onload="loadLayoutWin('','newCustomerReturns'); setOps();" onresize="resizeFrames()">
	<div class="interface" id="mainPage" style="">
		<!-- Search div Begins -->
		<div id="searchFrameDiv">
			<!-- start of contentArea -->
			<div class="contentArea">
				<tcmis:form action="/customerreturnsresults.do" onsubmit="return submitSearchForm();" target="resultFrame">
					<!-- Search Option Begins -->
					<%--Change the width to what you want your page to span.--%>
					<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
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
											<!-- Insert all the search option within this div -->
											<table border="0" cellpadding="0" cellspacing="0" class="tableSearch">
												<tr>
													<td nowrap="nowrap" class="optionTitleBoldRight">
														<fmt:message key="label.operatingentity" />:
													</td>
													<td class="optionTitleLeft">
														<select name="opsEntityId" id="opsEntityId" class="selectBox"></select>
													</td>
													<td class="optionTitleBoldRight" nowrap="nowrap">
														<fmt:message key="label.prnumber" />:
													</td>
													<td class="optionTitleLeft">
														<input name="prNumber" id="prNumber" type="text" class="inputBox" value="" size="20"/>
													</td>
												</tr>
												<tr>
													<td nowrap="nowrap" class="optionTitleBoldRight">
														<fmt:message key="label.hub" />:
													</td>
													<td class="optionTitleLeft">
														<select name="hub" id="hub" class="selectBox" />
													</td>
													<td class="optionTitleBoldRight" nowrap="nowrap">
														<fmt:message key="label.xbyy">
															<fmt:param>
																<fmt:message key="label.prnumber"/>
															</fmt:param>
															<fmt:param>
																<fmt:message key="label.receiptid"/>
															</fmt:param>
														</fmt:message>:
													</td>
													<td class="optionTitleLeft">
														<input name="prNumberByReceiptId" id="prNumberByReceiptId" type="text" class="inputBox" value="" size="20"/>
													</td>
												</tr>
												<tr>
													<td nowrap="nowrap" class="optionTitleBoldRight">
														<fmt:message key="label.inventorygroup" />:
													</td>
													<td class="optionTitleLeft">
														<select name="inventoryGroup" id="inventoryGroup" class="selectBox"></select>
													</td>
												</tr>
												<tr>
													<td nowrap="nowrap" class="optionTitleLeft" colspan="2">
														<input name="search" id="search" type="button" value="<fmt:message key="label.search"/>" onclick="submitSearchForm()"
														class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"/>
														<input name="createExl" id="createExl" type="button" value="<fmt:message key="label.createexcelfile"/>" onclick="createXSL()"
														class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"/>
													</td>
												</tr>
											</table>
											<!-- End search options -->
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
						<input name="uAction" id="uAction" type="hidden" value="" />
						<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
					</div>
					<!-- Hidden elements end -->
				</tcmis:form>
			</div>
			<!-- close of contentArea -->
		</div>
		<!-- Search div Ends -->

		<!-- Result Frame Begins -->
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
			<!-- Transit Page Begins -->
			<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				<br/><br/><br/>
				<fmt:message key="label.pleasewait" />
				<br /><br /><br />
				<img src="/images/rel_interstitial_loading.gif" align="middle" />
			</div>
			<!-- Transit Page Ends -->
			<div id="resultGridDiv" style="display: none;">
				<!-- results start -->
				<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
				<table id="resultsMaskTable" width="100%" border="0" cellpadding="0"
					cellspacing="0">
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
											<%-- boxhead Begins --%>
											<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
										          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
										          Please keep the <span></span> on the same line this will avoid extra virtical space.--%>
											<div id="mainUpdateLinks" style="display:">
												<%-- mainUpdateLinks Begins --%>
												<%--<div id="updateResultLink" style="display: "> take a look of this --%>
												<span id="updateResultLink" style="visibility:hidden">
													<a href="#" onclick="call('openCreateReturnPopup');">
														<fmt:message key="label.createreturnrequest" />
													</a>
												</span>
											</div>
										</div>
									</div>
									<%-- boxhead Ends --%>

									<div class="dataContent">
										<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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
						</td>
					</tr>
				</table>
				<!-- results end -->
			</div>
		</div>
		<!-- Result Frame Ends -->
	</div>
	<!-- close of interface -->
	<!-- You can build your error messages below.
     Similar divs would have to be built to show any other messages in a inline pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesArea" class="errorMessages" style="display: none; overflow: auto;"></div>
</body>
</html:html>
