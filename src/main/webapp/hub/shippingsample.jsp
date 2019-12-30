<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="nested" uri="/WEB-INF/struts-nested.tld"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld"%>
<%@ taglib prefix="tcmis" uri="/WEB-INF/tcmis.tld"%>
<html:html lang="true">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="expires" content="-1" />
	<link rel="shortcut-icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
	<%-- For Internationalization, copies data from calendarval.js --%>
	<%@ include file="/common/locale.jsp"%>
	<tcmis:gridFontSizeCss />
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<!-- This handles which key press events are disabled on this page -->
	<script type="text/javascript" src="/js/common/disableKeys.js"></script>
	
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
	
	<script type="text/javascript" src="/js/hub/shippingsample.js"></script>
	
	<title>
		<fmt:message key="label.addshippingsample"/>
	</title>
	<script language="JavaScript" type="text/javascript">
		var messagesData = {
			quantity:"<fmt:message key="label.quantity"/>",
            errors:"<fmt:message key="label.errors"/>",
			positiveIntegerError: "<fmt:message key="label.xxpositiveinteger"/>",
			xNotSelected: "<fmt:message key="errors.selecta"/>",
			shipTo: "<fmt:message key="label.shipto"/>",
			company: "<fmt:message key="label.company"/>",
			facility: "<fmt:message key="label.facility"/>"
		};
		
		var altCompany = new Array(
			{
				id:'',
				name:'<fmt:message key="label.pleaseselect"/>'
			}
		);
		var facilityColl = new Array();
    	var curFacility = new Array(
			{
				id:'',
				name:'<fmt:message key="label.pleaseselect"/>'
			}
		);
		<c:set var="lastCompanyId" value=""/>
		<c:forEach var="company" items="${companyCollection}" varStatus="status">
			//if new companyId found, conclude the previous companyId and move to the next one
			<c:if test="${status.first or lastCompanyId ne company.companyId}">
				facilityColl["${lastCompanyId}"] = curFacility;
				altCompany.push(
		        	{
						id:'<tcmis:jsReplace value="${company.companyId}"/>',
						name:'<tcmis:jsReplace value="${company.companyName}"/>'
					}
		        );
				curFacility = new Array(
					{
						id:'',
						name:'<fmt:message key="label.pleaseselect"/>'
					}
				);
				<c:set var="lastCompanyId" value="${company.companyId}"/>
			</c:if>
			curFacility.push(
				{
					id:'<tcmis:jsReplace value="${company.facilityId}"/>',
					name:'<tcmis:jsReplace value="${company.facilityName}"/>'
				}
			);
			<c:if test="${status.last}">
				facilityColl["${lastCompanyId}"] = curFacility;
			</c:if>
		</c:forEach>
	</script>
</head>
<body bgcolor="#ffffff"	onload="doOnLoad();" onresize="resizeFrames();">
	<div class="interface" id="mainPage" style="">
		<div id="dataFrameDiv" class="contentArea">
			<tcmis:form action="/shippingsample.do" onsubmit="return submitFrameOnlyOnce();">
				<div class="contentArea">
					<!-- Search Option Begins -->
					<table id="searchMaskTable" border="0" cellpadding="0"	cellspacing="0">
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
											<div class="boxhead">
												<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
												Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself. --%>
												<div id="mainUpdateLinks">
													<%-- mainUpdateLinks Begins --%>
												</div>
											</div>
											<table width="100%" border="0" cellpadding="0"	cellspacing="0" class="tableSearch">
												<tr>
													<td class="optionTitleBoldRight"><fmt:message key="label.quantity" />:</td>
													<td nowrap class="optionTitleLeft">
														<input class="inputBox" type="text" name="quantity" id="quantity" value="${shippingSampleInputBean.quantity}" size="10"/>
													</td>
												</tr>
												<tr>
													<td class="optionTitleBoldRight"><fmt:message key="label.company" />:</td>
													<td class="optionTitleLeft">
														<select name="companyIdSelect" id="companyIdSelect" onchange="companyChanged();" class="selectBox"></select>
													</td>
												</tr>
												<tr>
													<td class="optionTitleBoldRight"><fmt:message key="label.facility" />:</td>
													<td class="optionTitleLeft">
														<select name="facilityIdSelect" id="facilityIdSelect" onchange="facilityChanged();" class="selectBox"></select>
													</td>
												</tr>
												<tr>
													<td class="optionTitleBoldRight" ><fmt:message key="label.shipto"/>:</td>
													<td class="optionTitleLeft">
														<input name="shipToLocationIdDisplay" id="shipToLocationIdDisplay" class="inputBox" value="${shippingSampleInputBean.shipToLocationId}"/>
														<input name="searchShipToBtn" id="searchShipToBtn" onclick="getShipTo();" value="..." type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" align="left" />
													</td>
												</tr>
												<tr>
													<td></td>
													<td><span id="shipToLocationAddressSpan">${shippingSampleInputBean.shipToLocationAddressDisplay}</span></td>
												</tr>
												<tr>
													<td colspan="2">
														<input name="printShippingSampleBtn" id="printShippingSampleBtn" onclick="printSampleDeliveryLabel();" value="<fmt:message key="label.printdeliverylabel"/>" type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" />
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
				</div>
				<!-- Transit Page Begins -->
				<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
					<br/><br/><br/><fmt:message key="label.pleasewait"/><br/><br/><br/>
					<img src="/images/rel_interstitial_loading.gif" align="middle"/>
				</div>
				<!-- Transit Page Ends -->
				
		
				<!-- Hidden Element start -->
				<div id="hiddenElements" style="display: none">
					<input type="hidden" name="uAction" id="uAction" value="${shippingSampleInputBean.uAction}" />
					<input type="hidden" name="minHeight" id="minHeight" value="100" />
					<input type="hidden" name="shipToLocationId" id="shipToLocationId" value="${shippingSampleInputBean.shipToLocationId}" />
					<input type="hidden" name="hub" id="hub" value="${shippingSampleInputBean.hub}"/>
					<input type="hidden" name="receiptId" id="receiptId" value="${shippingSampleInputBean.receiptId}"/>
					<input type="hidden" name="companyId" id="companyId" value="${shippingSampleInputBean.companyId}"/>
					<input type="hidden" name="facilityId" id="facilityId" value="${shippingSampleInputBean.facilityId}"/>
					<input type="hidden" name="shipToLocationAddressDisplay" id="shipToLocationAddressDisplay" value="${shippingSampleInputBean.shipToLocationAddressDisplay}"/>
					<input type="hidden" name="labelQuantity" id="labelQuantity" value="1"/>
					<input type="hidden" name="labelType" id="labelType" value=""/>
					<input type="hidden" name="paperSize" id="paperSize" value="31"/>
				</div>
			</tcmis:form>
		</div>
	</div>
	<script type="text/javascript">
		var showMessage = false;
		<c:choose>
			<c:when test="${not empty tcmISErrors or not empty tcmISError}">
				messagesData.messageWinHeader = "<fmt:message key="label.errors"/>";
				showMessage = true;
			</c:when>
			<c:when test="${not empty tcmISMessages or not empty tcmISMessage}">
				messagesData.messageWinHeader = "<fmt:message key="label.noticewindowtitle"/>";
				showMessage = true;
			</c:when>
		</c:choose>
	</script>
	<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
	<!-- Error Messages Begins -->
	<div id="messagesArea" class="errorMessages" style="display: none; overflow: auto;">
		<c:choose>
			<c:when test="${not empty tcmISErrors or not empty tcmISError}">
				${tcmISError}<br />
				<c:forEach var="tcmisError" items="${tcmISErrors}">
					${tcmisError}<br />
				</c:forEach>
			</c:when>
			<c:when test="${not empty tcmISMessages or not empty tcmISMessage}">
				${tcmISMessage}<br />
				<c:forEach var="tcmisMessage" items="${tcmISMessages}">
					${tcmisMessage}<br />
				</c:forEach>
			</c:when>
		</c:choose>
	</div>
</body>
</html:html>