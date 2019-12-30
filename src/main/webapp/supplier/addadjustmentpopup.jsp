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
	<!-- This handels which key press events are disabeled on this page -->
	<script type="text/javascript" src="/js/common/disableKeys.js"></script>
	
	<!-- Add any other javascript you need for the page here -->

	<title><fmt:message key="label.popupwindow"/></title>
	
	<script language="JavaScript" type="text/javascript">
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = {
			genericError : "<fmt:message key="generic.error"/>"
		};

		//Initialize page layout based on input from parent page
		//returned obj of opener.getInitialEditInventoryPopupData:
		//		Key					Value
		//	-	popupTitle			String - title of the popup
		//	-	id of elements		Object - indicate how element is displayed differently from the default
		//objects of elements:
		//		Key					Value
		//	-	fixedElement		Boolean - whether element is showned as read-only text or not, default is false
		//	-	data				single value or Object - data to be displayed; Object, whose keys are name and id, is used if displayed data is different from pass-back data
		function initializeData() {
			try {
				var obj = opener.getInitialAddAdjustmentPopupData(window.name, $v("callFromResultFrame"));
				for ( var elementId in obj) {
					if (elementId === "popupTitle")
						document.title = obj[elementId];
					else {
						var curElement = obj[elementId];
						if (typeof curElement.data != "undefined") {
							var dataArr;
							if (isArray(curElement.data))
								dataArr = curElement.data;
							else {
								dataArr = new Array(
									{
										name: curElement.data,
										id: curElement.data
									}
								);
							}
							
							if (curElement.fixedElement)
								changeElementToSpan(elementId, dataArr[0].name, dataArr[0].id);
							else if ($(elementId).tagName.toLowerCase() === "select") {
								buildDropDown(dataArr, elementId, curElement.selectedId);
							} else
								$(elementId).value = dataArr[0].id;
						}
					}
				}
			} catch (e) {
				alert(messagesData.genericError);
			}
		}

		function submitPopup() {
			opener.submitAddAdjustmentPopup(window,  $v("locationId"), $v("catPartNo"), $v("inventoryId"), $v("adjustedQuantity"), $v("status"), $v("notes"));
		}

		function buildDropDown(arr, eleName, selectedId) {
			var obj = $(eleName);
			for (var i = obj.length; i > 0; i--)
				obj[i] = null;
			if (arr == null || arr.length == 0) {
				
			} else {
				var selectedIndex = 0;
				var start = 0;
				for (var i = 0; i < arr.length; i++) {
					if (selectedId && selectedId == arr[i].id)
						selectedIndex = i;
					setOption(start++, arr[i].name, arr[i].id, eleName);
				}
			}
			obj.selectedIndex = selectedIndex;
		}
	</script>
</head>

<body bgcolor="#ffffff"	onload="initializeData();" onunload="opener.closeTransitWin(window.name);">
	<div class="interface" id="mainPage" style="">
		<!-- Search Frame Begins -->
		<div id="searchFrameDiv" class="contentArea">
			<div class="contentArea">
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
												<td class="optionTitleBoldRight"><fmt:message key="label.location" />:</td>
												<td class="optionTitleLeft">
													<select class="selectBox" name="locationId" id="locationId"></select>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight"><fmt:message key="label.partno" />:</td>
												<td class="optionTitleLeft">
													<input class="inputBox" type="text" name="catPartNo" id="catPartNo" value="" size="10"/>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.inventoryid" />:</td>
												<td class="optionTitleLeft">
													<input class="inputBox" type="text" name="inventoryId" id="inventoryId" value="" size="10"/>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.adjustedquantity" />:</td>
												<td class="optionTitleLeft">
													<input class="inputBox" type="text" name="adjustedQuantity" id="adjustedQuantity" value="" size="10"/>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight"><fmt:message key="label.code" />:</td>
												<td class="optionTitleLeft">
													<select class="selectBox" name="status" id="status"></select>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.notes" />:</td>
												<td class="optionTitleLeft">
													<textarea name="notes" id="notes" cols="30" rows="3" class="inputBox"></textarea>
												</td>
											</tr>
											<tr>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td class="optionTitleLeft" colspan="2">
													<input onclick="submitPopup();" type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.submit"/>" name="submitAddAdjustmentBtn" id="submitAddAdjustmentBtn"/>
													&nbsp;
													<input onclick="window.close();" type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.cancel"/>" name="cancelBtn" id="cancelBtn"/>
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
				
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<input type="hidden" name="callFromResultFrame" id="callFromResultFrame" value="<tcmis:jsReplace value="${param.callFromResultFrame}"/>" />
				</div>
				<!-- Hidden elements end -->
			</div>
		</div>
	</div>
</body>
</html:html>