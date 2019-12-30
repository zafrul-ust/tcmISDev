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
	<!-- This handles all the resizing of the page and frames -->
	<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
	
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script	type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
	<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
	
	<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
	<script type="text/javascript" src="/js/supplier/allocateorder.js"></script>
	
	<title>
		<fmt:message key="label.allocateorder"/>
	</title>
	<script language="JavaScript" type="text/javascript">
		var messagesData = {
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			recordFound:"<fmt:message key="label.recordFound"/>",
			searchDuration:"<fmt:message key="label.searchDuration"/>",
			minutes:"<fmt:message key="label.minutes"/>",
			seconds:"<fmt:message key="label.seconds"/>",
			forVal:"<fmt:message key="label.for"/>",
			xxNonnegativeInteger:"<fmt:message key="label.xxnonnegativeinteger"/>",
			qty:"<fmt:message key="label.qty"/>",
			shipMoreThanQtyToPick:"<fmt:message key="error.shipmorethanqtytopick"/>",
			partialShipOIOrder:"<fmt:message key="error.partialshipoiorder"/>",
			valueRequiredOnRow:"<fmt:message key="errors.requiredonrow"/>",
			dateOfManufacture:"<fmt:message key="label.dateofmanufacture"/>",
			genericError:"<fmt:message key="generic.error"/>"
		};
		
		<%-- Define the grid options--%>
		var gridConfig = {
			divName: 'supplierInventoryBean',			<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
			beanData: 'jsonMainData',			<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
			beanGrid: 'beanGrid',				<%--  variable to put the grid object in for later use --%>
			config: 'config',					<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
			submitDefault: true
		};
		
		<%-- Define the columns for the result grid --%>
		var config = [
			{
				columnId: "permission"
			},
			{
				columnId:"inventoryId",
				columnName:'<fmt:message key="label.inventoryid"/>',
				width:4,
				align:'center'
			},
			{
				columnId:"bolTrackingNum",
				columnName:'<fmt:message key="label.boltrackingnumber"/>',
				width:6,
				align:'center'
			},
			{
				columnId:"bin",
				columnName:'<fmt:message key="label.storagelocation"/>',
				width:6,
				align:'center'
			},
			{
				columnId:"mfgLot",
				columnName:'<fmt:message key="label.lot"/>',
				width:4,
				align:'center'
			},
			{
				columnId:"availableQty",
				columnName:'<fmt:message key="label.availableqty"/><br/>(<span id="totalAvailableQty"></span>)',
				width:4,
				align:'center'
			},
			{
				columnId:"pickedQty",
				columnName:'<fmt:message key="label.pickqty"/><br/>(<span id="totalQty"></span>)',
				type:"hed",
				onChange: qtyChanged,
				width:3,
				align:'center'
			},
			{
				columnId:"expireDate",
				columnName:'<fmt:message key="label.expdate"/>',
				width:4,
				align:'center'
			},
			{
				columnId:"dateOfManufacture",
				columnName:'<fmt:message key="label.dateofmanufacture"/>',
				width:4,
				align:'center'
			},
			{//used to calculate overhead picked qty count
				columnId:"oldQty"
			},
			{//control what row will be used for confirm procedure
				columnId:"allocated"
			}
		];
		
		//hides the contract number when displaying on popup and print. Since customerPo is used by confirm procedure, can't modify it
		<c:choose>
			<c:when test="${fn:contains(param.customerPo, '-')}">
				<c:set var="displayedCustomerPo" value="${fn:substring(param.customerPo, fn:indexOf(param.customerPo, '-') + 1, fn:length(param.customerPo) + 1)}"/>
			</c:when>
			<c:otherwise>
				<c:set var="displayedCustomerPo" value="${param.customerPo}"/>
			</c:otherwise>
		</c:choose>
	</script>
</head>
<body bgcolor="#ffffff"	onload="doOnLoad();" onresize="resizeFrames()">
	<tcmis:form action="/allocateorder.do" onsubmit="return submitFrameOnlyOnce();">
		<div class="interface" id="resultsPage">
			<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
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
											<div class="boxhead" id="overheadSection">
												<table>
													<tr>
														<td colspan="4">
															<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
															Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself. --%>
															<div id="mainUpdateLinks">
																<%-- mainUpdateLinks Begins --%>
																<a href="#" onclick="confirm();"><fmt:message key="label.confirm"/></a> |
																<a href="#" onclick="openPrintPicklistPopup();"><fmt:message key="picklistreprint.printpicklist"/></a> |
																<a href="#" onclick="window.close();"><fmt:message key="label.cancel"/></a>
															</div>
														</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>
													<tr>
														<td class="optionTitleBoldRight"><fmt:message key="label.dono" />:</td>
														<td class="optionTitleLeft"><tcmis:jsReplace value="${displayedCustomerPo}" /></td>
														<td class="optionTitleBoldRight"><fmt:message key="label.mrline" />:</td>
														<td class="optionTitleLeft"><tcmis:jsReplace value="${param.mrNumber}" />&nbsp;-&nbsp;<tcmis:jsReplace value="${param.mrLineItem}" /></td>
													</tr>
													<tr>
														<td class="optionTitleBoldRight"><fmt:message key="label.poline" />:</td>
														<td class="optionTitleLeft"><tcmis:jsReplace value="${param.radianPo}" />&nbsp;-&nbsp;<tcmis:jsReplace value="${param.poLine}" /></td>
														<td class="optionTitleBoldRight"><fmt:message key="label.vendorshipdate" />:</td>
														<td class="optionTitleLeft"><tcmis:jsReplace value="${param.vendorShipDate}" /></td>
													</tr>
													<tr>
														<td class="optionTitleBoldRight"><fmt:message key="label.partno" />:</td>
														<td class="optionTitleLeft"><tcmis:jsReplace value="${param.catPartNo}" /></td>
														<td class="optionTitleBoldRight"><fmt:message key="label.shiptoaddress" />:</td>
														<td class="optionTitleLeft"><tcmis:jsReplace value="${param.shiptoAddress}" /></td>
													</tr>
													<tr>
														<td class="optionTitleBoldRight"><fmt:message key="label.orderquantity" />:</td>
														<td class="optionTitleLeft"><tcmis:jsReplace value="${param.qtyToPick}" /></td>
														<td class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.origininspectionrequired" />:</td>
														<td class="optionTitleLeft">
															<c:choose>
																<c:when test="${empty param.originInspectionRequired}">
																	<tcmis:jsReplace value="N" />
																</c:when>
																<c:otherwise>
																	<tcmis:jsReplace value="${param.originInspectionRequired}" />
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td class="optionTitleBoldRight"><fmt:message key="label.domrequired" />:</td>
														<td class="optionTitleLeft">
															<c:choose>
																<c:when test="${empty param.mfgDateRequired}">
																	<tcmis:jsReplace value="N" />
																</c:when>
																<c:otherwise>
																	<tcmis:jsReplace value="${param.mfgDateRequired}" />
																</c:otherwise>
															</c:choose>
														</td>
														<td class="optionTitleBoldRight"><fmt:message key="label.oconus" />:</td>
														<td class="optionTitleLeft">
															<c:choose>
																<c:when test="${empty param.oconus}">
																	<tcmis:jsReplace value="N" />
																</c:when>
																<c:otherwise>
																	<tcmis:jsReplace value="${param.oconus}" />
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td class="optionTitleBoldRight"><fmt:message key="label.vmi" />:</td>
														<td class="optionTitleLeft">
															<c:choose>
																<c:when test="${empty param.vmi}">
																	<tcmis:jsReplace value="N" />
																</c:when>
																<c:otherwise>
																	<tcmis:jsReplace value="${param.vmi}" />
																</c:otherwise>
															</c:choose>
														</td>
														<td class="optionTitleBoldRight"><fmt:message key="receiving.label.deliveryticket" />:</td>
														<td class="optionTitleLeft">
															<input class="inputBox" type="text" name="supplierSalesOrderNo" id="supplierSalesOrderNo" value="<tcmis:jsReplace value="${param.supplierSalesOrderNo}" />" size="15" maxlength="50"/>
														</td>
													</tr>
												</table>
											</div>
											<div class="dataContent">
												<c:choose>
													<%-- If the collection is empty say no data found --%>
													<c:when test="${empty supplierInventoryBeanCollection}" >
														<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
															<tr>
																<td width="100%">
																	<fmt:message key="main.nodatafound"/>
																</td>
															</tr>
														</table>
													</c:when>
													<c:otherwise>
														<div id="supplierInventoryBean" style="width: 100%; height: 100px" style="display: none;"/>
														<script type="text/javascript">
															<%-- Loop through the results and output each row, formatting the results as necessary
																use tcmis:jsReplace to escape special characters for ANY user entered text
																use fmt:formatNumber to format numbers (existing patterns: unitpricecurrencyformat, totalcurrencyformat, oneDigitformat)
																use fmt:formatDate to format dates (existing patterns: dateFormatPattern, dateTimeFormatPattern)--%>
															var jsonMainData = {
																rows:[
																	<c:forEach var="myBean" items="${supplierInventoryBeanCollection}" varStatus="status">
																		<c:if test="${!status.first}">,</c:if>
																		{
																			id : ${status.count},
																			data : [
																				'Y',
																				'<tcmis:jsReplace value="${myBean.inventoryId}"/>',
																				'<tcmis:jsReplace value="${myBean.bolTrackingNum}"/>',
																				'<tcmis:jsReplace value="${myBean.bin}"/>',
																				'<tcmis:jsReplace value="${myBean.mfgLot}"/>',
																				'${myBean.availableQty}',
																				'${myBean.pickedQty}',
																				'<fmt:formatDate value="${myBean.expireDate}" pattern="${dateFormatPattern}"/>',
																				'<fmt:formatDate value="${myBean.dateOfManufacture}" pattern="${dateFormatPattern}"/>',
																				'${myBean.pickedQty}',
																				<c:choose>
																					<c:when test="${not empty myBean.pickedQty}">
																						true
																					</c:when>
																					<c:otherwise>
																						false
																					</c:otherwise>
																				</c:choose>
																			]
																		}
																	</c:forEach>
																]
															};
															
															showUpdateLinks = true;
														</script>
													</c:otherwise>
												</c:choose>
											</div>
											<!-- Footer message start -->
											<div id="footer" class="messageBar"></div>
											<!-- Footer message end -->
										</div>
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
				</div>
				<!-- Transit Page Begins -->
				<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
					<br/><br/><br/><fmt:message key="label.pleasewait"/><br/><br/><br/>
					<img src="/images/rel_interstitial_loading.gif" align="middle"/>
				</div>
				<!-- Transit Page Ends -->
			</div>
				
			<!-- Hidden Element start -->
			<div id="hiddenElements" style="display: none">
	            <%-- Retrieve all the Search Criteria here for re-search after update--%>
	            <input type="hidden" name="batchId" id="batchId" value="${batchId}"/>
	            <input type="hidden" name="startSearchTime" id="startSearchTime" value="${startSearchTime}"/>
	            <input type="hidden" name="endSearchTime" id="endSearchTime" value="${endSearchTime}"/>
	            <input type="hidden" name="displayedCustomerPo" id="displayedCustomerPo" value="${displayedCustomerPo}"/>
	            <tcmis:setHiddenFields beanName="searchInput"/>
			</div>
		</div>
	</tcmis:form>
	<script type="text/javascript">
		var showMessage = false;
		<c:choose>
			<c:when test="${not empty tcmISErrors or not empty tcmISError}">
				messagesData.messageWinHeader = "<fmt:message key="label.errors"/>";
				showMessage = true;
			</c:when>
		</c:choose>
	</script>
	<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
	<!-- Error Messages Begins -->
	<div id="messagesArea" class="errorMessages" style="display: none; overflow: auto;">
		<c:if test="${not empty tcmISErrors or not empty tcmISError}">
			${tcmISError}<br />
			<c:forEach var="tcmisError" items="${tcmISErrors}">
				${tcmisError}<br />
			</c:forEach>
		</c:if>
	</div>
</body>
</html:html>