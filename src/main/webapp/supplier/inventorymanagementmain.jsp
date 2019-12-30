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
	<script type="text/javascript" src="/js/supplier/inventorymanagementmain.js"></script>
	<script src="/js/jquery/jquery-1.6.4.js"></script>
	<script type="text/javascript" src="/js/jquery/autocomplete.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />

	<title><fmt:message key="inventoryManagement"/></title>

	<script language="JavaScript" type="text/javascript">
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = {
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			searchInput:"<fmt:message key="label.inputSearchText"/>",
			errors:"<fmt:message key="label.errors"/>",
			pleasewait:"<fmt:message key="label.pleasewait"/>",
			addPartEditMinMax:"<fmt:message key="label.addparteditminmax"/>",
			addInventory:"<fmt:message key="label.addinventory"/>",
			addPO:"<fmt:message key="label.addpo"/>",
			addAdjustment:"<fmt:message key="label.addadjustment"/>",
			editInventory:"<fmt:message key="label.editinventory"/>",
			editPO:"<fmt:message key="label.editpo"/>",
			receivePOInventory:"<fmt:message key="label.receivepoinventory"/>",
			genericError:"<fmt:message key="generic.error"/>",
			valueNotFoundInDatabase:"<fmt:message key="error.valuenotfoundindatabase"/>",
			xxPositiveInteger:"<fmt:message key="label.xxpositiveinteger"/>",
			xxNegativeInteger:"<fmt:message key="label.xxnegativeinteger"/>",
			xxNonNegativeInteger:"<fmt:message key="label.xxnonnegativeinteger"/>",
			xxInteger:"<fmt:message key="errors.integer"/>",
			nsn:"<fmt:message key="label.nsn"/>",
			reorderPoint:"<fmt:message key="label.reorderpoint"/>",
			stockingLevel:"<fmt:message key="label.stockinglevel"/>",
			quantity:"<fmt:message key="label.quantity"/>",
			all:"<fmt:message key="label.all"/>",
			exceedMaxLength:"<fmt:message key="errors.maxlength"/>",
			notes:"<fmt:message key="label.notes"/>",
			reorderPointMoreThanStockingLevel:"<fmt:message key="error.reorderpoint.lessthanstockinglevel"/>",
			valueRequired:"<fmt:message key="errors.required"/>",
			dateOfManufacture:"<fmt:message key="label.dateofmanufacture"/>",
			expirationDate:"<fmt:message key="label.expirationdate"/>",
			partNo:"<fmt:message key="label.partno"/>",
			poNumber:"<fmt:message key="label.ponumber"/>",
			mfgLot:"<fmt:message key="label.lot"/>",
			dateRangeAll:"<fmt:message key="error.daterangeall"/>",
			pleaseSelectAValidValueFor:"<fmt:message key="error.pleaseselectavalidvaluefor"/>",
			transactionDate:"<fmt:message key="label.transactiondate"/>",
			manageStorageLocations:"<fmt:message key="label.managestoragelocations"/>",
			storageLocation:"<fmt:message key="label.storagelocation"/>",
			addVMIInventoryCheck:"<fmt:message key="msg.addvmiinventorycheck"/>",
			convertTo:"<fmt:message key="label.convertto"/>",
			convertPartWithQty:"<fmt:message key="msg.convertpartwithqty"/>",
			convertPO:"<fmt:message key="label.convertpo"/>",
			convertInventory:"<fmt:message key="label.convertinventory"/>",
			qtyToConvert:"<fmt:message key="label.qtytoconvert"/>",
			availableQty:"<fmt:message key="label.availableqty"/>",
			xCannotBeMoreThanY:"<fmt:message key="msg.xcannotbemorethany"/>"
		};
		
		var supplierArr = new Array();
		var locationArr = new Array();
		<c:set var="prevSupplier" value=""/>
		<c:set var="prevSupplierName" value=""/>
		var curLocation;
		<c:forEach var="dropdownBean" items="${locationBeanCollection}" varStatus="status">
			<c:if test="${prevSupplier ne dropdownBean.supplier}">
				<c:if test="${not empty prevSupplier}">
					locationArr["<tcmis:jsReplace value='${prevSupplier}'/>"] = curLocation;
					supplierArr.push(
						{
							id : "<tcmis:jsReplace value='${prevSupplier}'/>",
							name : "<tcmis:jsReplace value='${prevSupplierName}'/>"
						}
					);
				</c:if>
				curLocation = new Array();
				<c:set var="prevSupplier" value="${dropdownBean.supplier}"/>
				<c:set var="prevSupplierName" value="${dropdownBean.supplierName}"/>
			</c:if>
			
			curLocation.push(
				{
					id:"<tcmis:jsReplace value='${dropdownBean.shipFromLocationId}'/>",
					name:"<tcmis:jsReplace value='${dropdownBean.locationDesc}'/>",
					editMinMax:"<tcmis:jsReplace value='${dropdownBean.editMinMaxPermission}'/>"
				}
			);
			<c:if test="${status.last}">
				locationArr["<tcmis:jsReplace value='${prevSupplier}'/>"] = curLocation;
				supplierArr.push(
					{
						id : "<tcmis:jsReplace value='${prevSupplier}'/>",
						name : "<tcmis:jsReplace value='${prevSupplierName}'/>"
					}
				);
			</c:if>
		</c:forEach>
		
		var transactionTypeArr = new Array(
			{
				id : "",
				name : "<fmt:message key="label.all"/>"
			}
		);
		var transactionTypeStatusArr = new Array();
		transactionTypeStatusArr[""] = new Array();
		<c:set var="prevTransactionType" value=""/>
		var curStatus;
		<c:forEach var="dropdownBean" items="${transactionTypeStatusBeanCollection}" varStatus="status">
			<c:if test="${prevTransactionType ne dropdownBean.transactionType}">
				<c:if test="${not empty prevTransactionType}">
					transactionTypeStatusArr["<tcmis:jsReplace value='${prevTransactionType}'/>"] = curStatus;
					transactionTypeArr.push(
						{
							id : "<tcmis:jsReplace value='${prevTransactionType}'/>",
							name : "<tcmis:jsReplace value='${prevTransactionType}'/>"
						}
					);
				</c:if>
				curStatus = new Array();
				<c:set var="prevTransactionType" value="${dropdownBean.transactionType}"/>
			</c:if>
			
			curStatus.push(
				{
					id:"<tcmis:jsReplace value='${dropdownBean.status}'/>",
					name:"<tcmis:jsReplace value='${dropdownBean.status}'/>"
				}
			);
			<c:if test="${status.last}">
				transactionTypeStatusArr["<tcmis:jsReplace value='${prevTransactionType}'/>"] = curStatus;
				transactionTypeArr.push(
					{
						id : "<tcmis:jsReplace value='${prevTransactionType}'/>",
						name : "<tcmis:jsReplace value='${prevTransactionType}'/>"
					}
				);
			</c:if>
		</c:forEach>
    </script>
</head>

<body bgcolor="#ffffff"	onload="loadLayoutWin('','inventoryManagement');initializeDropDowns();" onunload="closeAllchildren();" onresize="resizePage();">
	<div class="interface" id="mainPage" style="">
		<!-- Search Frame Begins -->
		<div id="searchFrameDiv" class="contentArea">
			<tcmis:form action="/inventorymanagementresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
													<td class="optionTitleBoldRight"><fmt:message key="label.supplier" />:</td>
													<td class="optionTitleLeft">
														<select name="supplier" id="supplier" class="selectBox" onchange="supplierChanged();"></select>
													</td>
													<td class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.transactiondate" />:</td>
													<td class="optionTitleBoldLeft" nowrap="nowrap">
														<fmt:message key="label.from" />&nbsp;
														<input class="inputBox pointer" readonly="readonly" type="text" name="transactionFromDate" id="transactionFromDate" value="" onclick="return getCalendar(document.genericForm.transactionFromDate,null,document.genericForm.transactionToDate);" maxlength="10" size="9" />
														&nbsp;<fmt:message key="label.to" />&nbsp;
														<input class="inputBox pointer" readonly="readonly" type="text" name="transactionToDate" id="transactionToDate" value="" onclick="return getCalendar(document.genericForm.transactionToDate,document.genericForm.transactionFromDate);" maxlength="10" size="9" />
													</td>
												</tr>
												<tr>
													<td class="optionTitleBoldRight"><fmt:message key="label.location" />:</td>
													<td class="optionTitleLeft">
														<select name="shipFromLocationId" id="shipFromLocationId" class="selectBox" onchange="locationChanged();"></select>
													</td>
													<td class="optionTitleBoldRight"><fmt:message key="label.search" />:</td>
													<td class="optionTitleBoldLeft" nowrap="nowrap">
														<select name="searchField" class="selectBox" id="searchField">
															<option value="cat_part_no"><fmt:message key="label.partno" /></option>
															<option value="mfg_lot"><fmt:message key="label.lot" /></option>
															<option value="supplier_po_number"><fmt:message key="label.vendorpo" /></option>
															<option value="bol_tracking_num"><fmt:message key="label.boltrackingnumber" /></option>
															<option value="inventory_id"><fmt:message key="label.inventoryid" /></option>
															<option value="conversion_group"><fmt:message key="label.conversiongroup" /></option>
														</select>&nbsp;
														<select name="searchMode" class="selectBox" id="searchMode">
															<option value="is"><fmt:message key="label.is" /></option>
															<option value="contains"><fmt:message key="label.contains" /></option>
															<option value="startsWith"><fmt:message key="label.startswith" /></option>
															<option value="endsWith"><fmt:message key="label.endswith" /></option>
														</select>&nbsp;
														<input class="inputBox" type="text" name="searchArgument" id="searchArgument" value="" size="15"/>
													</td>
												</tr>
												<tr>
													<td class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="tablet.transactiontype" />:</td>
													<td class="optionTitleBoldLeft">
														<select name="transactionType" id="transactionType" class="selectBox" onchange="transactionTypeChanged()"></select>
													</td>
													<td class="optionTitleBoldRight"><fmt:message key="label.status" />:</td>
													<td class="optionTitleBoldLeft">
														<select name="status" id="status" class="selectBox" onchange="statusChanged()"></select>
													</td>
												</tr>
												<tr>
													<td nowrap="nowrap" colspan="2">
														<input type="checkbox" name="includeHistory" value="Y" id="includeHistory" />&nbsp;<span class="optionTitleBoldLeft"><fmt:message key="label.includehistory" /></span>
													</td>
													<td nowrap="nowrap" colspan="2">
														<input type="checkbox" name="gfpPartsOnly" value="Y" id="gfpPartsOnly" />&nbsp;<span class="optionTitleBoldLeft"><fmt:message key="label.gfppartsonly" /></span>
													</td>
												</tr>
												<tr>
													<td>&nbsp;</td>
												</tr>
												<tr>
													<td nowrap="nowrap" class="optionTitleLeft" colspan="4">
														<input onclick="submitSearch();" type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.search"/>" name="searchBtn" id="searchBtn"/>
														<div id="addPartEditMinMaxBtnDiv">
															&nbsp;
															<input onclick="openUpdateInsertPartPopup();" type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.addparteditminmax"/>" name="addPartEditMinMaxBtn" id="addPartEditMinMaxBtn"/>
														</div>
														&nbsp;
														<input onclick="openInventoryPopup('N', 'addInventory');" type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.addinventory"/>" name="addInventoryBtn" id="addInventoryBtn"/>
														&nbsp;
														<input onclick="openPOPopup('N', 'addPO');" type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.addpo"/>" name="addPOBtn" id="addPOBtn"/>
														&nbsp;
														<input onclick="createExcel();" type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.createexcelfile"/>" name="createExlBtn" id="createExlBtn"/>
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
								<td align="center" id="transitLabel"></td>
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
						<input type="hidden" name="supplierName" id="supplierName" value=""/>
						<input type="hidden" name="locationDesc" id="locationDesc" value=""/>
						<input type="hidden" name="searchFieldName" id="searchFieldName" value=""/>
						<input type="hidden" name="searchModeName" id="searchModeName" value=""/>
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
												<span id="updateResultLink" style="display: none"></span>
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