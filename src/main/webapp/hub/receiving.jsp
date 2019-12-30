<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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
<%@ include file="/common/opshubig.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss overflow="notHidden"/>
<!-- CSS for YUI -->
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />

<%-- Add any other stylesheets you need for the page here --%>

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/iframeresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>

<%@ include file="/common/rightclickmenudata.jsp" %>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>

<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/js/hub/receiving.js"></script>
<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js" ></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js" ></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js" ></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>

<title>
<fmt:message key="label.receiving"/>
</title>

<script language="JavaScript" type="text/javascript">
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData={
		alert:"<fmt:message key="label.alert"/>",
		all:"<fmt:message key="label.all"/>",
		validvalues:"<fmt:message key="label.validvalues"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		incoming:"<fmt:message key="label.incoming"/>",
		transferreceiptid:"<fmt:message key="label.transferreceiptid"/>",
		mfglot:"<fmt:message key="label.mfglot"/>",
		bin:"<fmt:message key="label.bin"/>",
		is:"<fmt:message key="label.is"/>",
		qtyreceivedgreaterthanopen:"<fmt:message key="label.qtyreceivedgreaterthanopen"/>",
		greaterthanqtyopen:"<fmt:message key="label.greaterthanqtyopen"/>",
		polinecannotreceived:"<fmt:message key="label.polinecannotreceived"/>",
		qtybeingreceived:"<fmt:message key="label.qtybeingreceived"/>",
		quantityreceived:"<fmt:message key="label.quantityreceived"/>",
		qtyreceivednotmatch:"<fmt:message key="label.qtyreceivednotmatch"/>",
		packagedqtyreceived:"<fmt:message key="label.packagedqtyreceived"/>",
		checkpackagedsize:"<fmt:message key="label.checkpackagedsize"/>",
		dom:"<fmt:message key="receivedreceipts.label.dom"/>",
		dor:"<fmt:message key="receivedreceipts.label.dor"/>",
		dos:"<fmt:message key="label.manufacturerdos"/>",
		expiredate:"<fmt:message key="label.expiredate"/>",
		lotstatus:"<fmt:message key="label.lotstatus"/>",
		cannotbe:"<fmt:message key="label.cannotbe"/>",
		incoming:"<fmt:message key="label.incoming"/>",
		mustBeInteger:"<fmt:message key="label.errorinteger"/>",
		mustbeanumberinthisfield:"<fmt:message key="label.mustbeanumberinthisfield"/>",
		actsupshpdate:"<fmt:message key="label.actsupshpdate"/>",
		titlebar:"<fmt:message key="transfertransactions.titlebar"/>",
		receivingchecklist:"<fmt:message key="label.receivingchecklist"/>",
		itemtitle:"<fmt:message key="receivinghistory.label.itemtitle"/>",
		viewpurchaseorder:"<fmt:message key="label.viewpurchaseorder"/>",
		potitle:"<fmt:message key="receivinghistory.label.potitle"/>",
		viewpurchaseorder:"<fmt:message key="label.viewpurchaseorder"/>",
		viewcustomerreturnrequest:"<fmt:message key="label.viewcustomerreturnrequest"/>",
		viewrma:"<fmt:message key="label.viewrma"/>",
		customerreturnrequest:"<fmt:message key="customerreturnrequest.title"/>",
		itemnotes:"<fmt:message key="itemnotes.title"/>",
		indefinite:"<fmt:message key="label.indefinite"/>",
		qastatement:"<fmt:message key="label.qastatement"/>",
		inventorywarning:"<fmt:message key="label.inventorywarning"/>",
		invalidreceiptiderror:"<fmt:message key="label.invalidreceiptiderror"/>",
		showlegend:"<fmt:message key="label.showlegend"/>",
		nopermissiontochangestatus:"<fmt:message key="label.nopermissiontochangestatus"/>",
		printGHSLabels:"<fmt:message key="label.printGHSlabels"/>"
	};
	
	defaults.ops.nodefault = true;
	defaults.hub.nodefault = true;
	
	var printGHSLabels = false;
	<tcmis:featureReleased feature="PrintGHSLabels" scope="ALL">
		printGHSLabels = true;
	</tcmis:featureReleased>
	
	var disabledPoLink = false;
	<tcmis:featureReleased feature="DisabledPOLink" scope="ALL"  companyId="${personnelBean.companyId}">
		disabledPoLink = true;
	</tcmis:featureReleased>
	
	var disabledItemNotes = false;
	<tcmis:featureReleased feature="DisabledItemNotes" scope="ALL"  companyId="${personnelBean.companyId}">
		disabledItemNotes = true;
	</tcmis:featureReleased>
	
	with(milonic=new menuname("receivingMenu")){
		top="offset=2"
		style = contextStyle;
		margin=3
		aI("text=<fmt:message key="transfertransactions.titlebar"/>;url=javascript:showrecforinvtransfr();");
		aI("text=<fmt:message key="label.receivingchecklist"/>;url=javascript:showReceivingJacket();");
		aI("text=<fmt:message key="receivinghistory.label.itemtitle"/>;url=javascript:showPreviousrec();");
	}
	
	with(milonic=new menuname("transTypeNotIT")){
		top="offset=2"
		style = contextStyle;
		margin=3
		disabledPoLink ? "" : aI("text=<fmt:message key="label.viewpurchaseorder"/>;url=javascript:showRadianPo();");
		aI("text=<fmt:message key="receivinghistory.label.potitle"/>;url=javascript:showPreviousPoLineReceipts();");
		aI("text=<fmt:message key="label.receivingchecklist"/>;url=javascript:showReceivingJacket();");
		aI("text=<fmt:message key="receivinghistory.label.itemtitle"/>;url=javascript:showPreviousrec();");
	}
	
	drawMenus();
	
	<c:set var="itemid"><fmt:message key="label.itemid"/></c:set>
	var searchWhatChemArray = [
		{
			id:'radianPo',
			text:'<fmt:message key="label.po"/>'
		},
		{
			id:'customerPo',
			text:'<fmt:message key="label.customerpo"/>'
		},
		{
			id:'itemId',
			text:'<tcmis:jsReplace value="${itemid}"/>'
		},
		{
			id:'itemDescription',
			text:'<fmt:message key="label.itemdesc"/>'
		},
		{
			id:'lastSupplier',
			text:'<fmt:message key="label.supplier"/>'
		},
		{
			id:'transferRequestId',
			text:'<fmt:message key="transferrequest.title"/>'
		},
		{
			id:'customerRmaId',
			text:'<fmt:message key="label.rma"/>'
		}
	];
	
	var searchWhatNonChemArray = [
		{
			id:'radianPo',
			text:'<fmt:message key="label.po"/>'
		},
		{
			id:'customerPo',
			text:'<fmt:message key="label.customerpo"/>'
		},
		{
			id:'itemId',
			text:'<tcmis:jsReplace value="${itemid}"/>'
		},
		{
			id:'itemDescription',
			text:'<fmt:message key="label.itemdesc"/>'
		},
		{
			id:'lastSupplier',
			text:'<fmt:message key="label.supplier"/>'
		}
	];
	
	var searchMyArr = [
		{
			value:'is',
			text:'<fmt:message key="label.is"/>'
		},
		{
			value:'contains',
			text:'<fmt:message key="label.contains"/>'
		},
		{
			value:'startsWith',
			text:'<fmt:message key="label.startswith"/>'
		},
		{
			value:'endsWith',
			text:'<fmt:message key="label.endswith"/>'
		}
	];
</script>
</head>

<c:set var="doexcelpopup" value='${doexcelpopup}'/>
<c:choose>
	<c:when test="${!empty doexcelpopup}" >
		<body onload="doexcelpopup();setOps();setDefault();setSearchWhat(searchWhatChemArray);"  onunload="closeAllchildren();">
	</c:when>
	<c:when test="${!empty receivedReceipts}" >
		<body onload="showChemicalReceivedReceipts('<c:out value="${receivedReceipts}"/>');setOps();setDefault();setSearchWhat(searchWhatChemArray);" onunload="closeAllchildren();">
	</c:when>
	<c:otherwise>
		<body onload="setOps();setDefault();setSearchWhat(searchWhatChemArray);" onunload="closeAllchildren();">
	</c:otherwise>
</c:choose>

<c:set var="duplicateLine" value='${duplicateLine}'/>
<c:set var="submitSearch" value='${param.submitSearch}'/>
<c:set var="submitReceive" value='${param.submitReceive}'/>
<c:set var="submitCreateReport" value='${param.submitCreateReport}'/>

<tcmis:form action="/receiving.do" onsubmit="return SubmitOnlyOnce();">
	<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
		<br/><br/><br/><fmt:message key="label.pleasewait"/>
	</div>
	<div class="interface" id="mainPage">
		<div class="contentArea">
			<!-- Search Option Begins -->
			<table id="searchMaskTable" width="880" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<div class="roundcont filterContainer">
							<div class="roundright">
								<div class="roundtop">
									<div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
								</div>
								<div class="roundContent">
									<!-- Insert all the search option within this div -->
									<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
										<tr>
											<td width="5%" class="optionTitleBoldRight">
												<fmt:message key="label.category"/>:
											</td>
											<td width="10%" class="optionTitleBoldLeft">
												<html:select property="category" styleId="category" onchange="checkDropShip();" styleClass="selectBox">
													<html:option value="Chemicals"/>
													<html:option value="Non-Chemicals"/>
												</html:select>
											</td>
											<td width="5%" class="optionTitleBoldRight">
												<fmt:message key="label.search"/>:
											</td>
											<td width="10%" class="optionTitleBoldLeft" nowrap>
												<select name="searchWhat" id="searchWhat"  onchange="getSelectedSearchWhat(this.value);" class="selectBox"></select>
												<input type="hidden" name="selectedSearchWhat" id="selectedSearchWhat" value="${param.searchWhat}"/>
												<html:select property="searchType" styleId="searchType" styleClass="selectBox">
													<html:option value="is" ><fmt:message key="label.is"/></html:option>
													<html:option value="startsWith" key="label.startswith"/>
												</html:select>
												<input class="inputBox" type="text" name="search" id="search"  value="${param.search}" size="20" maxlength="50"/>
											</td>
										</tr>
										<tr>
											<td width="5%" class="optionTitleBoldRight">
												<fmt:message key="label.operatingentity"/>:
											</td>
											<td width="10%" class="optionTitleBoldLeft">
												<select name="opsEntityId" id="opsEntityId" class="selectBox"></select>
												<input type="hidden" name="selectedOpsEntityId" id="selectedOpsEntityId" value="${param.opsEntityId}"/>
											</td>
											<td width="5%" class="optionTitleBoldRight">
												<fmt:message key="receiving.label.expected"/>
											</td>
											<td width="10%" class="optionTitleBoldLeft">
												<c:set var="expected" value='${param.expected}'/>
												<c:if test="${empty expected && (empty submitSearch && empty duplicateLine && empty submitCreateReport && empty submitReceive)}">
													<c:set var="expected" value='${30}'/>
												</c:if>
												<input class="inputBox" type="text" name="expected" value="<c:out value="${expected}"/>" size="1" maxlength="4"/>
												<fmt:message key="label.days"/>
											</td>
										</tr>
										<c:set var="displayPartNumber" value='No'/>
										<tr>
											<td width="5%" class="optionTitleBoldRight">
												<fmt:message key="label.hub"/>:
											</td>
											<td width="10%" class="optionTitleBoldLeft">
												<select name="sourceHub" id="sourceHub" class="selectBox"></select>
												<input type="hidden" name="selectedHub" id="selectedHub" value="${param.sourceHub}"/>
												<c:set var="selectedHubName" value='${param.sourceHubName}'/>
												<c:set var="receivingPermission" value=''/>
												<c:set var="isAutoPutHubFromSearch"><tcmis:jsReplace value="${param.isAutoPutHub}"/></c:set>
												<c:if test="${isAutoPutHubFromSearch == 'N'}">   
													<tcmis:inventoryGroupPermission indicator="true" userGroupId="Receiving" facilityId="${selectedHubName}">
															<c:set var="receivingPermission" value='Yes'/>
													</tcmis:inventoryGroupPermission>
												</c:if>
												<c:set var="selectedHub" value='${param.sourceHub}'/>
												<c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
													<c:set var="currentHub" value='${status.current.branchPlant}'/>
													<c:choose>
														<c:when test="${empty selectedHub}" >
															<c:set var="selectedHub" value='${status.current.branchPlant}'/>
															<c:set var="selectedHubName" value="${status.current.hubName}"/>
															<c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>
														</c:when>
														<c:when test="${currentHub == selectedHub}" >
															<c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>
														</c:when>
													</c:choose>
												</c:forEach>
											</td>
											<td width="5%" class="optionTitleBoldRight">
												&nbsp;
											</td>
											<td width="10%" class="optionTitleBoldLeft">
												<div id="showdropshiponly"><html:checkbox property="dropship" styleId="dropship" value="Y" styleClass="radioBtns"/><fmt:message key="receiving.label.showdropshiponly"/></div>
											</td>
										</tr>
										<tr>
											<td width="5%" class="optionTitleBoldRight">
												<fmt:message key="label.inventorygroup"/>:
											</td>
											<td width="10%" class="optionTitleBoldLeft">
												<select name="inventoryGroup" id="inventoryGroup" class="selectBox"></select>
												<input type="hidden" name="selectedInventoryGroup" id="selectedInventoryGroup" value="${param.inventoryGroup}"/>
												<c:set var="selectedIg" value='${param.inventoryGroup}'/>
												<c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status">
													<c:set var="currentIg" value='${status.current.inventoryGroup}'/>    
													<c:choose>
														<c:when test="${currentIg == selectedIg}">
															<c:if test="${status.current.issueGeneration == 'Item Counting'}">
																<c:set var="displayPartNumber" value='Yes'/>
															</c:if>
															<c:if test="${status.current.partNoAssignedAtReceipt == 'Y'}">
																<c:set var="displayPartNumber" value='Yes'/>
															</c:if>
														</c:when>
														<c:otherwise>
															<c:if test="${empty selectedIg || selectedIg == 'All'}">
																<c:if test="${status.current.issueGeneration == 'Item Counting'}">
																	<c:set var="displayPartNumber" value='Yes'/>
																</c:if>
																<c:if test="${status.current.partNoAssignedAtReceipt == 'Y'}">
																	<c:set var="displayPartNumber" value='Yes'/>
																</c:if>
															</c:if>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</td>
											<td width="5%" class="optionTitleBoldRight" nowrap>
												<fmt:message key="label.sortby"/>:
											</td>
											<td width="10%" class="optionTitleBoldLeft">
												<html:select property="sort" styleId="sort" styleClass="selectBox">
													<html:option value="PO/POLine"/>
													<html:option value="Date Exptd/Supplier"/>
													<html:option value="Supplier/Date Exptd"/>
													<html:option value="Item Id/Date Exptd"/>
												</html:select>
											</td>
										</tr>
										<tr>
											<td width="5%" colspan="4" class="optionTitleBoldLeft">
												<html:submit property="submitSearch" onclick="return validateForm();" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
													<fmt:message key="label.search"/>
												</html:submit>
												<html:submit property="submitCreateReport" styleId="submitCreateReport" onclick="return doexcelpopup()" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
													<fmt:message key="label.createexcelfile"/>
												</html:submit>
												<html:button property="submitReprint" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "reprint()">
													<fmt:message key="label.reprint"/>
												</html:button>
												<tcmis:inventoryGroupPermission indicator="true" userGroupId="addNewBin" facilityId="${selectedHubName}">
													<html:button property="submitNewBin" onclick="addnewBin()" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
														<fmt:message key="receiving.button.newbin"/>
													</html:button>
												</tcmis:inventoryGroupPermission>
											</td>
										</tr>
									</table>
									<!-- End search options -->
								</div>
								<div class="roundbottom">
									<div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
								</div>
							</div>
						</div>
					</td>
				</tr>
			</table>
			<!-- Search Option Ends -->
			
			<div class="spacerY">&nbsp;</div>
			<div id="transitDailogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;"></div>
			
			<div id="transitDailogWinBody" class="errorMessages" style="display: none;">
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
			
			<!-- Error Messages Begins -->
			<div id="errorMessagesArea" class="errorMessages">
				<c:if test="${!empty errorMessage && errorMessage !=null}">
					<c:out value="${errorMessage}"/>
				</c:if>
				<html:errors/>
			</div>
			<!-- Error Messages Ends -->
			
			<c:if test="${empty errorMessage && !empty submitReceive}">
				<div class="optionTitleBoldCenter"><fmt:message key="receiving.successmessage"/></div>
			</c:if>
			
			<c:if test="${receivingViewRelationBeanCollection != null}" >
				<!-- Search results start -->
				<!-- If you want your results section to span only 50% set this to 50% and your mail table will be 100% -->
				<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<div class="roundcont contentContainer">
								<div class="roundright">
									<div class="roundtop">
										<div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
									</div>
									<div class="roundContent">
										<div class="boxhead">
											<c:if test="${!empty receivingViewRelationBeanCollection}" >
												<a href="javascript:showLegend()"><fmt:message key="label.Legend"/></a>
												<c:if test="${receivingPermission == 'Yes'}">
													<span id="receivingLink" style="">
													| <a href="#" onclick="submitReceiveAction('submitReceive'); return false;"><fmt:message key="receiving.button.receive"/></a> 
													</span>
												</c:if> 
											</c:if>
										</div>
										<div class="dataContent">
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
												<c:set var="colorClass" value=''/>
												<c:set var="dataCount" value='${0}'/>
												<c:forEach var="receivingViewBean" items="${receivingViewRelationBeanCollection}" varStatus="status">
													<c:if test="${status.index % 10 == 0}">
														<tr>
														<th width="5%"><fmt:message key="label.po"/><br/>(<fmt:message key="label.customerpo"/>)</th>
														<th width="5%"><fmt:message key="label.poline"/></th>
														<th width="5%"><fmt:message key="receiving.label.dateexpected"/></th>
														<th width="7%"><fmt:message key="label.supplier"/></th>
														<c:if test="${displayPartNumber == 'Yes'}">
															<th width="7%"><fmt:message key="label.partnumber"/></th>
														</c:if>
														<th width="5%"><fmt:message key="label.item"/><br/>(<fmt:message key="label.inventorygroup"/>)</th>
														<th width="5%"><fmt:message key="receiving.label.openamount"/></th>
														<th width="5%"><fmt:message key="cyclecountresults.label.expectedqty"/></th>
														<th width="5%"><fmt:message key="label.packaging"/></th>
														<th width="12%"><fmt:message key="label.description"/></th>
														<c:if test="${receivingPermission == 'Yes'}">
															<th width="2%"><fmt:message key="label.ok"/></th>
															<th width="4%" colspan="2"><fmt:message key="label.bin"/></th>
															<th width="8%"><fmt:message key="label.lot"/></th>
															<th width="4%"><fmt:message key="label.receiptid"/></th>
															<th width="5%"><fmt:message key="label.actsupshpdate"/></th>
															<th width="5%"><fmt:message key="receivedreceipts.label.dor"/></th>
															<th width="5%"><fmt:message key="receivedreceipts.label.dom"/></th>
															<th width="5%"><fmt:message key="label.dateofrepackaging"/></th>
															<th width="5%"><fmt:message key="label.manufacturerdos"/></th>
															<th width="5%"><fmt:message key="label.expdate"/></th>
															<th width="5%"><fmt:message key="receiving.label.qtyreceived"/></th>
															<th width="2%"><fmt:message key="receiving.label.closepoline"/></th>
															<th width="5%" colspan="2">
																<fmt:message key="receiving.label.packagedqty"/> x <fmt:message key="receiving.label.packagedsize"/>
															</th>
															<th width="5%"><fmt:message key="receiving.label.receiptstatus"/></th>
															<th width="5%"><fmt:message key="label.notes"/></th>
															<th width="5%"><fmt:message key="receiving.label.deliveryticket"/></th>
															<th width="2%"><fmt:message key="receiving.label.duplicateline"/></th>
															<th width="2%"><fmt:message key="receiving.label.duplicatekitpkg"/></th>
														</c:if>
													</tr>
												</c:if>
												
												<c:choose>
													<c:when test="${status.index % 2 == 0}" >
														<c:set var="colorClass" value=''/>
													</c:when>
													<c:otherwise>
														<c:set var="colorClass" value='alt'/>
													</c:otherwise>
												</c:choose>
												
												<c:set var="critical" value='${status.current.critical}'/>
												<c:if test="${critical == 'Y' || critical == 'y'}">
													<c:set var="colorClass" value='red'/>
												</c:if>
												<c:if test="${critical == 'S' || critical == 's'}">
													<c:set var="colorClass" value='pink'/>
												</c:if>
												
												<c:set var="updateStatus" value='${status.current.updateStatus}'/>
												<c:if test="${updateStatus == 'NO' || updateStatus == 'Error'}">
													<c:set var="colorClass" value='error'/>
												</c:if>
												
												<c:set var="transType" value='${status.current.transType}'/>
												<c:set var="originalReceiptId" value='${status.current.originalReceiptId}'/>
												<c:if test="${transType == 'IT' && empty originalReceiptId}">
													<c:set var="colorClass" value='error'/>
												</c:if>
												
												<tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>" onmouseup="selectRow('${status.index}')">
													<input type="hidden" name="colorClass${status.index}" id="colorClass<c:out value="${status.index}"/>" value="${colorClass}" />
													<input type="hidden" id="selectedDataCount<c:out value="${status.index}"/>" value="${dataCount}"/>
													<fmt:formatDate var="formattedexpectedDate" value="${status.current.expected}" pattern="${dateFormatPattern}"/>
													<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].qtyOpen" id="qtyOpen<c:out value="${dataCount}"/>" value="<c:out value="${status.current.qtyOpen}"/>"/>
													<c:set var="kitCollection"  value='${status.current.kitCollection}'/>
													<bean:size id="listSize" name="kitCollection"/>
													<c:set var="mainRowSpan" value='${status.current.rowSpan}' />
													<c:set var="manageKitsAsSingleUnit" value='${status.current.manageKitsAsSingleUnit}'/>
													<c:set var="mvItem" value='${status.current.mvItem}'/>
													
													<c:set var="inventoryGroupPermission" value=''/>
													<c:if test="${isAutoPutHubFromSearch == 'N'}">   
														<tcmis:inventoryGroupPermission indicator="true" userGroupId="Receiving" inventoryGroup="${status.current.inventoryGroup}">
																<c:set var="inventoryGroupPermission" value='Yes'/>
														</tcmis:inventoryGroupPermission>
													</c:if>
													
													<c:if test="${transType == 'IT' && empty originalReceiptId}">
														<c:set var="inventoryGroupPermission" value=''/>
													</c:if>
													
													<c:set var="mainLotStatus" value='${status.current.lotStatus}'/>
													<c:if test="${empty mainLotStatus}" >
														<c:set var="mainLotStatus" value='Available'/>
													</c:if>
													<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
														<c:choose>
															<c:when test="${transType == 'IT' && ! empty status.current.customerRmaId}" >
																RMA <c:out value="${status.current.customerRmaId}"/>
															</c:when>
															<c:when test="${transType == 'IT'}" >
																TR <c:out value="${status.current.transferRequestId}"/>
															</c:when>
															<c:when test="${!empty status.current.intercompanyPo && !empty status.current.intercompanyPoLine}" >
																<c:out value="${status.current.intercompanyPo}"/>
																<br/>(<c:out value="${status.current.radianPo}"/>)
															</c:when>
															<c:otherwise>
																<c:out value="${status.current.radianPo}"/>
																<c:if test="${!empty status.current.customerPo}">
																	<br/>(<c:out value="${status.current.customerPo}"/>)
																</c:if>
															</c:otherwise>
														</c:choose>
													</td>
													<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
														<c:choose>
															<c:when test="${transType == 'IT'}" >
																<c:out value="${status.current.lineItem}"/>
															</c:when>
															<c:when test="${!empty status.current.intercompanyPo && !empty status.current.intercompanyPoLine}" >
																<c:out value="${status.current.intercompanyPoLine}"/>
																<br/>(<c:out value="${status.current.lineItem}"/>)
															</c:when>
															<c:otherwise>
																<c:out value="${status.current.lineItem}"/>
															</c:otherwise>
														</c:choose>
													</td>
													<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
														<c:choose>
															<c:when test="${!empty status.current.radianPo}" >
																<c:out value="${formattedexpectedDate}"/>
															</c:when>
															<c:otherwise>
																<c:out value="${formattedexpectedDate}"/>
															</c:otherwise>
														</c:choose>
													</td>
													<td width="7%" rowspan="<c:out value="${mainRowSpan}"/>">
														<c:choose>
															<c:when test="${!empty status.current.intercompanyPo && !empty status.current.intercompanyPoLine}" >
																<c:out value="${status.current.intercompanySupplierName}"/>
																<br/>(<c:out value="${status.current.lastSupplier}"/>)
															</c:when>
															<c:otherwise>
																<c:out value="${status.current.lastSupplier}"/>
															</c:otherwise>
														</c:choose>
													</td>
													
													<c:if test="${displayPartNumber == 'Yes'}">
														<td width="7%" class="alignLeft" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.catPartNo}"/></td>
													</c:if>
													
													<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
														<c:out value="${status.current.itemId}"/>
														<br/>(<c:out value="${status.current.inventoryGroupName}"/>)
													</td>
													<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
														<c:out value="${status.current.qtyOpen}"/>
													</td>
													
													<c:forEach var="ReceivingKitBean" items="${kitCollection}" varStatus="kitstatus">
														<c:if test="${kitstatus.index > 0 && listSize > 1 }">
															<c:set var="kitUpdateStatus" value='${kitstatus.current.updateStatus}'/>
															<c:if test="${kitUpdateStatus == 'NO' || kitUpdateStatus == 'Error'}">
																<c:set var="colorClass" value='error'/>
															</c:if>
															<tr class="<c:out value="${colorClass}"/>" id="childRowId<c:out value="${status.index}"/><c:out value="${kitstatus.index}"/>">
														</c:if>
														
														<td width="5%"><c:out value="${kitstatus.current.originalQty}"/></td>
														<c:choose>
															<c:when test="${listSize > 1 && manageKitsAsSingleUnit == 'N'}">
																<td width="5%" class="alignLeft"><c:out value="${kitstatus.current.packaging}"/></td>
																<td width="12%" class="alignLeft"><c:out value="${kitstatus.current.materialDesc}"/></td>
															</c:when>
															<c:when test="${manageKitsAsSingleUnit != 'N' && kitstatus.index == 0}">
																<td width="5%" class="alignLeft" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.packaging}"/></td>
																<td width="12%" class="alignLeft" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.itemDescription}"/></td>
															</c:when>
														</c:choose>
														
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].radianPo" id="radianPo<c:out value="${dataCount}"/>" value="<c:out value="${status.current.radianPo}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].lineItem" id="lineItem<c:out value="${dataCount}"/>" value="<c:out value="${status.current.lineItem}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].itemId" id="itemId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.itemId}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].inventoryGroup" id="inventoryGroup<c:out value="${dataCount}"/>" value="<c:out value="${status.current.inventoryGroup}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].transType" id="transType<c:out value="${dataCount}"/>" value="<c:out value="${status.current.transType}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].rowNumber" id="rowNumber<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].branchPlant" id="branchPlant<c:out value="${dataCount}"/>" value="<c:out value="${status.current.branchPlant}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].mvItem" id="mvItem<c:out value="${dataCount}"/>" value="<c:out value="${mvItem}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].purchasingUnitsPerItem" id="purchasingUnitsPerItem<c:out value="${dataCount}"/>" value="<c:out value="${status.current.purchasingUnitsPerItem}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].purchasingUnitOfMeasure" id="purchasingUnitOfMeasure<c:out value="${dataCount}"/>" value="<c:out value="${status.current.purchasingUnitOfMeasure}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].nonintegerReceiving" id="nonintegerReceiving<c:out value="${dataCount}"/>" value="<c:out value="${status.current.nonintegerReceiving}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].customerPo" id="customerPo<c:out value="${dataCount}"/>" value="<c:out value="${status.current.customerPo}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].customerRmaId" id="customerRmaId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.customerRmaId}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].transferRequestId" id="transferRequestId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.transferRequestId}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].originalQty" id="originalQty<c:out value="${dataCount}"/>" value="<c:out value="${status.current.originalQty}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].intercompanyPo" id="intercompanyPo<c:out value="${dataCount}"/>" value="<c:out value="${status.current.intercompanyPo}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].intercompanyPoLine" id="intercompanyPoLine<c:out value="${dataCount}"/>" value="<c:out value="${status.current.intercompanyPoLine}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].ownerSegmentId" id="ownerSegmentId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.ownerSegmentId}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].accountNumber" id="accountNumber<c:out value="${dataCount}"/>" value="<c:out value="${status.current.accountNumber}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].accountNumber2" id="accountNumber2<c:out value="${dataCount}"/>" value="<c:out value="${status.current.accountNumber2}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].accountNumber3" id="accountNumber3<c:out value="${dataCount}"/>" value="<c:out value="${status.current.accountNumber3}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].accountNumber4" id="accountNumber4<c:out value="${dataCount}"/>" value="<c:out value="${status.current.accountNumber4}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].customerReceiptId" id="customerReceiptId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.customerReceiptId}"/>"/>
														<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].qualityTrackingNumber" id="qualityTrackingNumber<c:out value="${dataCount}"/>" value="<c:out value="${status.current.qualityTrackingNumber}"/>"/>
														<c:choose>
															<c:when test="${inventoryGroupPermission == 'Yes'}">
																<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].duplicatePkgLine" id="duplicatePkgLine<c:out value="${dataCount}"/>" value="<c:out value="${status.current.duplicatePkgLine}"/>"/>
																<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].duplicateKitLine" id="duplicateKitLine<c:out value="${dataCount}"/>" value="<c:out value="${status.index}"/>"/>
																<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].rowSpan" id="rowSpan<c:out value="${dataCount}"/>" value="<c:out value="${listSize}"/>"/>
																<c:choose>
																	<c:when test="${mvItem != 'Y'}">
																		<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].definedShelfLifeItem" id="definedShelfLifeItem<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.definedShelfLifeItem}"/>"/>
																		<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].definedShelfLifeBasis" id="definedShelfLifeBasis<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.definedShelfLifeBasis}"/>"/>
																		<fmt:formatDate var="formattedShipDate" value="${kitstatus.current.supplierShipDate}" pattern="${dateFormatPattern}"/>
																		<fmt:formatDate var="formattedDateOfReceipt" value="${kitstatus.current.dateOfReceipt}" pattern="${dateFormatPattern}"/>
																		<fmt:formatDate var="formattedDop" value="${kitstatus.current.dateOfRepackaging}" pattern="${dateFormatPattern}"/>
																		<fmt:formatDate var="formattedDom" value="${kitstatus.current.dom}" pattern="${dateFormatPattern}"/>
																		<fmt:formatDate var="formattedDos" value="${kitstatus.current.dos}" pattern="${dateFormatPattern}"/>
																		<fmt:formatDate var="localeExpireDate" value="${kitstatus.current.expirationDate}" pattern="${dateFormatPattern}"/>
																		<fmt:formatDate var="fmtExpireDate" value="${kitstatus.current.expirationDate}" pattern="MM/dd/yyyy"/>
																		<c:choose>
																			<c:when test="${(status.current.indefiniteShelfLife == 'y') || (fmtExpireDate == '01/01/3000')}" >
																				<c:set var="formattedExpirationDate">
																					<fmt:message key="label.indefinite"/>
																				</c:set>
																			</c:when>
																			<c:otherwise>
																				<c:set var="formattedExpirationDate" value="${localeExpireDate}"/>
																			</c:otherwise>
																		</c:choose>
																		
																		<td width="2%">
																			<c:if test="${kitstatus.current.ok != null}" >
																				<input type="checkbox" name="receivingViewBean[<c:out value="${dataCount}"/>].ok" id="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" checked onclick="checkChemicalReceivingInput(<c:out value="${dataCount}"/>,'Y')"/>
																			</c:if>
																			<c:if test="${kitstatus.current.ok == null}" >
																				<input type="checkbox" name="receivingViewBean[<c:out value="${dataCount}"/>].ok" id="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" onclick="checkChemicalReceivingInput(<c:out value="${dataCount}"/>,'Y')"/>
																			</c:if>
																		</td>
																		<td width="1%">
																			<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="addBin<c:out value="${dataCount}"/>" value="+" onclick="showVvHubBins(<c:out value="${dataCount}"/>)" />
																		</td>
																		<td width="5%" class="alignLeft">
																			<c:set var="binCollection"  value='${status.current.receiptItemPriorBinViewBeanCollection}'/>
																			<c:set var="selectedBin" value='${kitstatus.current.bin}'/>
																			<bean:size id="binSize" name="binCollection"/>
																			<select name="receivingViewBean[<c:out value="${dataCount}"/>].bin" id="bin<c:out value="${dataCount}"/>" class="selectBox">
																				<c:forEach var="receiptItemPriorBinViewBean" items="${binCollection}" varStatus="binstatus">
																					<c:set var="currentBin" value='${binstatus.current.bin}'/>
																					<c:choose>
																						<c:when test="${currentBin == selectedBin}">
																							<option value="<c:out value="${currentBin}"/>" selected><c:out value="${currentBin}"/></option>
																						</c:when>
																						<c:otherwise>
																							<option value="<c:out value="${currentBin}"/>"><c:out value="${currentBin}"/></option>
																						</c:otherwise>
																					</c:choose>
																				</c:forEach>
																				
																				<%-- Larry Note: add receiving only beans  --%>
																				<c:if test="${binSize == 0}">
																					<c:set var="hasAnyBin" value="N"/>
																					<c:forEach var="receivingOnlyBean" items="${receivingOnlyBins}" varStatus="rbin">
																						<c:set var="hasAnyBin" value="Y"/>
																						<option value="${receivingOnlyBean.bin}">${receivingOnlyBean.bin}</option>
																					</c:forEach>
																				</c:if>
																				<c:if test="${binSize != 0}">
																					<c:set var="hasAnyBin" value="Y"/>
																					<c:forEach var="receivingOnlyBean" items="${receivingOnlyBins}" varStatus="rbin">
																						<c:set var="hasBin" value="N"/>
																						<c:forEach var="receiptItemPriorBinViewBean" items="${binCollection}" varStatus="binstatus">
																							<c:if test="${receiptItemPriorBinViewBean.bin eq receivingOnlyBean.bin}">
																								<c:set var="hasBin" value="Y"/>
																							</c:if>
																						</c:forEach>
																						<c:if test="${hasBin ne 'Y'}">
																							<option value="${receivingOnlyBean.bin}">${receivingOnlyBean.bin}</option>
																						</c:if>
																					</c:forEach>
																				</c:if>
																				<c:if test="${hasAnyBin ne 'Y'}">
																					<option value="NONE"><fmt:message key="label.none"/></option>
																				</c:if>
																			</select>
																		</td>
																		<td width="8%">
																			<c:choose>
																				<c:when test="${transType == 'IT' && empty submitReceive}">
																					<input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].mfgLot" id="mfgLot<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.originalMfgLot}"/>" size="20" maxlength="36" class="inputBox"/>
																				</c:when>
																				<c:when test="${! empty kitstatus.current.originalMfgLot && empty submitReceive}">
																					<input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].mfgLot" id="mfgLot<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.originalMfgLot}"/>" size="20" maxlength="36" class="inputBox"/>
																				</c:when>
																				<c:otherwise>
																					<input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].mfgLot" id="mfgLot<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.mfgLot}"/>" size="20" maxlength="36" class="inputBox"/>
																				</c:otherwise>
																			</c:choose>
																		</td>
																		<td width="4%">
																			<c:choose>
																				<c:when test="${transType == 'IT' && empty submitReceive}">
																					<input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].transferReceiptId" id="transferReceiptId<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.originalReceiptId}"/>" onchange="checkReceiptId(<c:out value="${dataCount}"/>);" size="6" maxlength="10" class="inputBox"/>
																				</c:when>
																				<c:when test="${transType == 'IT'}">
																					<input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].transferReceiptId" id="transferReceiptId<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.transferReceiptId}"/>" onchange="checkReceiptId(<c:out value="${dataCount}"/>);" size="6" maxlength="10" class="inputBox"/>
																				</c:when>
																				<c:otherwise>
																					<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].transferReceiptId" id="transferReceiptId<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.originalReceiptId}"/>"/>
																					${kitstatus.current.originalReceiptId}
																				</c:otherwise>
																			</c:choose>
																			<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].originalReceiptId" id="originalReceiptId<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.originalReceiptId}"/>"/>
																		</td>
																		<td width="5%" nowrap>
																			<input type="text" readonly name="receivingViewBean[<c:out value="${dataCount}"/>].supplierShipDate" id="supplierShipDate${dataCount}" onclick="return getCalendar(document.genericForm.supplierShipDate${dataCount},null,null,null,(document.genericForm.dateOfReceipt${dataCount}.value=='')?document.genericForm.today:document.genericForm.dateOfReceipt${dataCount});" value="${formattedShipDate}" size="8" maxlength="10" class="inputBox pointer"/>
																		</td>
																		<td width="5%" nowrap>
																			<input name='receivingViewBean[${dataCount}].beforeSystemDate' id='beforeSystemDate${dataCount}' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'  /> 
																			<input type="text" readonly name="receivingViewBean[<c:out value="${dataCount}"/>].dateOfReceipt" id="dateOfReceipt${dataCount}" onclick="return getCalendar(document.genericForm.dateOfReceipt${dataCount},document.genericForm.date60,null,null,document.genericForm.today);" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>" size="8" maxlength="10" class="inputBox pointer"/>
																		</td>
																		<td width="5%" nowrap>
																			<input type="text" readonly name="receivingViewBean[<c:out value="${dataCount}"/>].dom" id="dom${dataCount}" value="${formattedDom}"  onclick="return getCalendar(document.genericForm.dom<c:out value="${dataCount}"/>,null,null,null,(document.genericForm.dos${dataCount}.value=='')?document.genericForm.today:document.genericForm.dos${dataCount});" size="8" maxlength="10" class="inputBox pointer"/>	         
																		</td>
																		<td width="5%" nowrap>
																			<input type="text" readonly name="receivingViewBean[<c:out value="${dataCount}"/>].dateOfRepackaging" id="dateOfRepackaging${dataCount}" value="${formattedDop}" onclick="return getCalendar(document.genericForm.dateOfRepackaging<c:out value="${dataCount}"/>,document.genericForm.dom${dataCount},null,null,document.genericForm.dateOfReceipt${dataCount});" size="8" maxlength="10" class="inputBox pointer"/>	         
																		</td>
																		<td width="5%" nowrap>
																			<input type="text" readonly name="receivingViewBean[<c:out value="${dataCount}"/>].dos" id="dos${dataCount}" value="${formattedDos}" onclick="return getCalendar(document.genericForm.dos${dataCount},null,null,document.genericForm.dom${dataCount},document.genericForm.today);" size="8" maxlength="10" class="inputBox pointer"/>
																		</td>
																		<td width="5%" nowrap>	
																			<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].expirationDate" id="expirationDate${dataCount}" value="${localeExpireDate}"/>        
																			<c:if test="${status.current.definedShelfLifeItem != null && status.current.definedShelfLifeItem != 'Y'}">       
																				<input type="text" readonly name="receivingViewBean[<c:out value="${dataCount}"/>].expirationDateString" id="expirationDateString${dataCount}" value="${formattedExpirationDate}" onclick="return getCalendar(document.genericForm.expirationDateString${dataCount},null,null,document.genericForm.todayoneyear,null,'Y');"   onchange="expiredDateChanged(${dataCount});" size="8" maxlength="10" class="inputBox pointer"/>
																			</c:if>
																		</td>
																		<c:choose>
																			<c:when test="${kitstatus.index == 0 && manageKitsAsSingleUnit == 'N'}">
																				<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
																					<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].quantityReceived" id="quantityReceived<c:out value="${dataCount}"/>" value="<c:out value="${status.current.quantityReceived}"/>" size="4" maxlength="15" class="inputBox"/>
																				</td>
																				<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
																					<c:choose>
																						<c:when test="${status.current.skipReceivingQc == 'Y' && (status.current.orderQtyUpdateOnReceipt == 'y' || status.current.orderQtyUpdateOnReceipt == 'Y')}" >
																							<c:if test="${status.current.closePoLine != null}" >
																								<c:set var="checkCloseBoxChecked" value='checked'/>
																							</c:if>
																							<input type="checkbox" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].closePoLine" id="closePoLine<c:out value="${dataCount}"/>" value="<c:out value="${status.current.orderQtyUpdateOnReceipt}"/>" <c:out value="${checkCloseBoxChecked}"/> onclick="checkClosePoLine(<c:out value="${dataCount}"/>)"/>
																						</c:when>
																						<c:otherwise>
																							&nbsp;
																						</c:otherwise>
																					</c:choose>
																				</td>
																				<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>" colspan="2">&nbsp;</td>
																				<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
																					<%--Receiving page currently does not handle quality control items and the certification prcoess if a quality control inventory group skips QC.--%>
																					<c:if test="${status.current.skipReceivingQc == 'Y'}">
																						<select name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].lotStatus" id="lotStatus<c:out value="${dataCount}"/>" class="selectBox" onchange="checkReceiptStatus(<c:out value="${dataCount}"/>)"/>
																						<c:forEach var="vvLotStatusBean" items="${vvLotStatusBeanCollection}" varStatus="vvlotstatus">
																							<c:set var="lotStatusValue" value='${vvlotstatus.current.lotStatus}'/>
																							<c:choose>
																								<c:when test="${(statusUpdatePermission == 'Yes'  || qualityControlItem !='Y' )&& onlynonPickableStatusPermission != 'Yes'}"></c:when>
																								<c:otherwise>
																									<c:if test="${vvlotstatus.current.certified == 'Y' || (vvlotstatus.current.pickable == 'Y' && vvlotstatus.current.allocationOrder !=0)}">
																										<c:set var="lotStatusValue" value=''/>
																									</c:if>
																								</c:otherwise>
																							</c:choose>
																							<c:set var="jspLabel" value=""/>
																							<c:if test="${fn:length(vvlotstatus.current.jspLabel) > 0}">
																								<c:set var="jspLabel">${vvlotstatus.current.jspLabel}</c:set>
																							</c:if>
																							<c:choose>
																								<c:when test="${mainLotStatus == vvlotstatus.current.lotStatus}" >    
																									<option value="${vvlotstatus.current.lotStatus}" selected><fmt:message key="${jspLabel}"/></option>
																								</c:when>
																								<c:otherwise>
																									<option value="${vvlotstatus.current.lotStatus}"><fmt:message key="${jspLabel}"/></option>
																								</c:otherwise>
																							</c:choose>
																						</c:forEach>
																					</c:if>
																				</td>
																				<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
																					<textarea name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].receiptNotes" cols="25" rows="3" class="inputBox"><c:out value="${status.current.receiptNotes}"/></textarea>
																				</td>
																				<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
																					<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].deliveryTicket" id="deliveryTicket<c:out value="${dataCount}"/>" value="<c:out value="${status.current.deliveryTicket}"/>" size="6" maxlength="30" class="inputBox"/>
																				</td>
																				<!--   <td width="5%" align="center" rowspan="<c:out value="${mainRowSpan}"/>">
																				<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].qaStatement" id="qaStatement<c:out value="${dataCount}"/>" value="<c:out value="${status.current.qaStatement}"/>" size="1" maxlength="1" class="inputBox">
																				</td> -->
																				<td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">&nbsp;</td>
																				<td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
																					<c:if test="${manageKitsAsSingleUnit == 'N'}">
																						<input type="submit" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick= " return duplkit(<c:out value="${dataCount+listSize-1}"/>)" value="Dup Kit" name="duplicateButton"/>
																					</c:if>
																					<input type="hidden" name="kitSize<c:out value="${dataCount+listSize-1}"/>" id="kitSize<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${listSize}"/>"/>
																				</td>
																			</c:when>
																			<c:when test="${manageKitsAsSingleUnit != 'N'}">
																				<td width="5%">
																					<input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].quantityReceived" id="quantityReceived<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.quantityReceived}"/>" size="4" maxlength="15" class="inputBox"/>
																				</td>
																				<c:if test="${kitstatus.index == 0}">
																					<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
																						<c:choose>
																							<c:when test="${status.current.skipReceivingQc == 'Y' && (status.current.orderQtyUpdateOnReceipt == 'y' || status.current.orderQtyUpdateOnReceipt == 'Y')}" >
																								<c:if test="${status.current.closePoLine != null}" >
																									<c:set var="checkCloseBoxChecked" value='checked'/>
																								</c:if>
																								<input type="checkbox" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].closePoLine" id="closePoLine<c:out value="${dataCount}"/>" value="<c:out value="${status.current.orderQtyUpdateOnReceipt}"/>" <c:out value="${checkCloseBoxChecked}"/> onclick="checkClosePoLine(<c:out value="${dataCount}"/>)"/>
																							</c:when>
																							<c:otherwise>
																								&nbsp;
																							</c:otherwise>
																						</c:choose>
																					</td>
																					<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>" colspan="2">&nbsp;</td>
																				</c:if>
																				<td width="5%">
																					<c:if test="${status.current.skipReceivingQc == 'Y'}">
																						<select name="receivingViewBean[<c:out value="${dataCount}"/>].lotStatus" id="lotStatus<c:out value="${dataCount}"/>" class="selectBox" onchange="checkReceiptStatus(<c:out value="${dataCount}"/>)">
																							<c:forEach var="vvLotStatusBean" items="${vvLotStatusBeanCollection}" varStatus="vvlotstatus">
																								<c:set var="jspLabel" value=""/>
																								<c:if test="${fn:length(vvlotstatus.current.jspLabel) > 0}">
																									<c:set var="jspLabel">${vvlotstatus.current.jspLabel}</c:set>
																								</c:if>
																								<c:set var="lotStatusValue" value='${vvlotstatus.current.lotStatus}'/>
																								<c:choose>
																									<c:when test="${(statusUpdatePermission == 'Yes'  || qualityControlItem !='Y' )&& onlynonPickableStatusPermission != 'Yes'}"></c:when>
																									<c:otherwise>
																										<c:if test="${vvlotstatus.current.certified == 'Y' || (vvlotstatus.current.pickable == 'Y' && vvlotstatus.current.allocationOrder !=0)}">
																											<c:set var="lotStatusValue" value=''/>
																										</c:if>
																									</c:otherwise>
																								</c:choose>
																								<c:choose>
																									<c:when test="${mainLotStatus == vvlotstatus.current.lotStatus}" >
																										<option value="<c:out value="${vvlotstatus.current.lotStatus}"/>" selected><fmt:message key="${jspLabel}"/></option>
																									</c:when>
																									<c:otherwise>
																										<option value="<c:out value="${vvlotstatus.current.lotStatus}"/>"><fmt:message key="${jspLabel}"/></option>
																									</c:otherwise>
																								</c:choose>
																							</c:forEach>
																						</select>
																					</c:if>
																				</td>
																				<td width="5%">
																					<textarea name="receivingViewBean[<c:out value="${dataCount}"/>].receiptNotes" cols="25" rows="3" class="inputBox"><c:out value="${kitstatus.current.receiptNotes}"/></textarea>
																				</td>
																				<td width="5%">
																					<input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].deliveryTicket" id="deliveryTicket<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.deliveryTicket}"/>" size="6" maxlength="30" class="inputBox"/>
																				</td>
																				<!--  <td width="5%" align="center">
																				<input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].qaStatement" id="qaStatement<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.qaStatement}"/>" size="1" maxlength="1" class="inputBox">
																				</td>-->
																				<c:if test="${kitstatus.index == 0}">
																					<td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
																						<c:if test="${listSize < 2 || manageKitsAsSingleUnit != 'N'}">
																							<input type="submit" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick= "return duplLine(<c:out value="${dataCount}"/>)" value="Dupl" name="duplicateButton"/>
																						</c:if>
																					</td>
																					<td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">&nbsp;</td>
																				</c:if>
																			</c:when>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<%--Here starts the variable packaging stuff--%>
																		<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].definedShelfLifeItem" id="definedShelfLifeItem<c:out value="${dataCount}"/>" value="<c:out value="${status.current.definedShelfLifeItem}"/>"/>
																		<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].definedShelfLifeBasis" id="definedShelfLifeBasis<c:out value="${dataCount}"/>" value="<c:out value="${status.current.definedShelfLifeBasis}"/>"/>
																		<fmt:formatDate var="formattedShipDate" value="${status.current.supplierShipDate}" pattern="${dateFormatPattern}"/>
																		<fmt:formatDate var="formattedDateOfReceipt" value="${status.current.dateOfReceipt}" pattern="${dateFormatPattern}"/>
																		<fmt:formatDate var="formattedDom" value="${status.current.dom}" pattern="${dateFormatPattern}"/>
																		<fmt:formatDate var="formattedDop" value="${kitstatus.current.dateOfRepackaging}" pattern="${dateFormatPattern}"/>
																		<fmt:formatDate var="formattedDos" value="${status.current.dos}" pattern="${dateFormatPattern}"/>
																		<fmt:formatDate var="localeExpireDate" value="${status.current.expirationDate}" pattern="${dateFormatPattern}"/>
																		<fmt:formatDate var="fmtExpireDate" value="${status.current.expirationDate}" pattern="MM/dd/yyyy"/>
																		<c:choose>
																			<c:when test="${(status.current.indefiniteShelfLife == 'y') || (fmtExpireDate == '01/01/3000')}" >
																				<c:set var="formattedExpirationDate">
																					<fmt:message key="label.indefinite"/>
																				</c:set>
																			</c:when>
																			<c:otherwise>
																				<c:set var="formattedExpirationDate" value="${localeExpireDate}"/>
																			</c:otherwise>
																		</c:choose>
																		<c:if test="${kitstatus.index == 0}">
																			<td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
																				<c:if test="${status.current.ok != null}" >
																					<input type="checkbox" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].ok" id="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" checked onclick="checkChemicalReceivingInput(<c:out value="${dataCount}"/>)"/>
																				</c:if>
																				<c:if test="${status.current.ok == null}" >
																					<input type="checkbox" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].ok" id="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" onclick="checkChemicalReceivingInput(<c:out value="${dataCount}"/>)"/>
																				</c:if>
																			</td>
																			<td width="1%" rowspan="<c:out value="${mainRowSpan}"/>">
																				<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="addBin<c:out value="${dataCount}"/>" value="+" onclick="showVvHubBins(<c:out value="${dataCount}"/>)" />
																			</td>
																			<td width="5%"class="alignLeft" rowspan="<c:out value="${mainRowSpan}"/>">
																				<c:set var="binCollection"  value='${status.current.receiptItemPriorBinViewBeanCollection}'/>
																				<c:set var="selectedBin" value='${status.current.bin}'/>
																				<bean:size id="binSize" name="binCollection"/>
																				<select name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].bin" id="bin<c:out value="${dataCount}"/>" class="selectBox">
																					<c:forEach var="receiptItemPriorBinViewBean" items="${binCollection}" varStatus="binstatus">
																						<c:set var="currentBin" value='${binstatus.current.bin}'/>
																						<c:choose>
																							<c:when test="${currentBin == selectedBin}">
																								<option value="<c:out value="${currentBin}"/>" selected><c:out value="${currentBin}"/></option>
																							</c:when>
																							<c:otherwise>
																								<option value="<c:out value="${currentBin}"/>"><c:out value="${currentBin}"/></option>
																							</c:otherwise>
																						</c:choose>
																					</c:forEach>
																					
																					<%-- Larry Note: add receiving only beans  --%>
																					<c:if test="${binSize == 0}">
																						<c:set var="hasAnyBin" value="N"/>
																						<c:forEach var="receivingOnlyBean" items="${receivingOnlyBins}" varStatus="rbin">
																							<c:set var="hasAnyBin" value="Y"/>
																							<option value="${receivingOnlyBean.bin}">${receivingOnlyBean.bin}</option>
																						</c:forEach>
																					</c:if>
																					<c:if test="${binSize != 0}">
																						<c:set var="hasAnyBin" value="Y"/>
																						<c:forEach var="receivingOnlyBean" items="${receivingOnlyBins}" varStatus="rbin">
																							<c:set var="hasBin" value="N"/>
																							<c:forEach var="receiptItemPriorBinViewBean" items="${binCollection}" varStatus="binstatus">
																								<c:if test="${receiptItemPriorBinViewBean.bin eq receivingOnlyBean.bin}">
																									<c:set var="hasBin" value="Y"/>
																								</c:if>
																							</c:forEach>
																							<c:if test="${hasBin ne 'Y'}">
																								<option value="${receivingOnlyBean.bin}">${receivingOnlyBean.bin}</option>
																							</c:if>
																						</c:forEach>
																					</c:if>
																					<c:if test="${hasAnyBin ne 'Y'}">
																						<option value="NONE"><fmt:message key="label.none"/></option>
																					</c:if>
																				</select>
																			</td>
																			<td width="8%" rowspan="<c:out value="${mainRowSpan}"/>">
																				<c:choose>
																					<c:when test="${transType == 'IT' && empty submitReceive}">
																						<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].mfgLot" id="mfgLot<c:out value="${dataCount}"/>" value="<c:out value="${status.current.originalMfgLot}"/>" size="20" maxlength="36" class="inputBox"/>
																					</c:when>
																					<c:when test="${!empty status.current.originalMfgLot && empty submitReceive}">
																						<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].mfgLot" id="mfgLot<c:out value="${dataCount}"/>" value="<c:out value="${status.current.originalMfgLot}"/>" size="20" maxlength="36" class="inputBox"/>
																					</c:when>
																					<c:otherwise>
																						<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].mfgLot" id="mfgLot<c:out value="${dataCount}"/>" value="<c:out value="${status.current.mfgLot}"/>" size="20" maxlength="36" class="inputBox"/>
																					</c:otherwise>
																				</c:choose>
																			</td>
																			<td width="4%" rowspan="<c:out value="${mainRowSpan}"/>">
																				<c:choose>
																					<c:when test="${transType == 'IT' && empty submitReceive}">
																						<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].transferReceiptId" id="transferReceiptId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.originalReceiptId}"/>" size="6" maxlength="10" class="inputBox"/>
																					</c:when>
																					<c:when test="${transType == 'IT'}">
																						<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].transferReceiptId" id="transferReceiptId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.transferReceiptId}"/>" size="6" maxlength="10" class="inputBox"/>
																					</c:when>
																					<c:otherwise>
																						<input type="hidden" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].transferReceiptId" id="transferReceiptId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.originalReceiptId}"/>"/>
																						${status.current.originalReceiptId}
																					</c:otherwise>
																				</c:choose>
																			</td>
																			<td width="5%" nowrap rowspan="<c:out value="${mainRowSpan}"/>">
																				<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].supplierShipDate" id="supplierShipDate<c:out value="${dataCount}"/>"  onclick="return getCalendar(document.genericForm.supplierShipDate${dataCount},null,null,null,(document.genericForm.dateOfReceipt${dataCount}.value=='')?document.genericForm.today:document.genericForm.dateOfReceipt${dataCount+listSize-1});" value="${formattedShipDate}" size="8" maxlength="10" class="inputBox pointer"/>
																			</td>
																			<td width="5%" nowrap rowspan="<c:out value="${mainRowSpan}"/>">
																				<input name='receivingViewBean[${dataCount}].beforeSystemDate' id='beforeSystemDate${dataCount}' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'  /> 
																				<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].dateOfReceipt" id="dateOfReceipt<c:out value="${dataCount}"/>"  onclick="return getCalendar(document.genericForm.dateOfReceipt${dataCount},document.genericForm.date60,null,null,document.genericForm.today);"  value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>" size="8" maxlength="10" class="inputBox pointer"/>
																			</td>
																			<td width="5%" nowrap rowspan="<c:out value="${mainRowSpan}"/>">
																				<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].dom" id="dom<c:out value="${dataCount}"/>"  onclick="return getCalendar(document.genericForm.dom<c:out value="${dataCount}"/>,null,null,null,(document.genericForm.dos${dataCount}.value=='')?document.genericForm.today:document.genericForm.dos${dataCount+listSize-1});" value="<c:out value="${formattedDom}"/>" size="8" maxlength="10" class="inputBox pointer"/>
																			</td>
																			<td width="5%" nowrap rowspan="<c:out value="${mainRowSpan}"/>">
																				<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].dateOfRepackaging" id="dateOfRepackaging<c:out value="${dataCount}"/>"  onclick="return getCalendar(document.genericForm.dateOfRepackaging<c:out value="${dataCount}"/>,document.genericForm.dom${dataCount},null,null,document.genericForm.dateOfReceipt${dataCount});" value="<c:out value="${formattedDop}"/>" size="8" maxlength="10" class="inputBox pointer"/>
																			</td>      
																			<td width="5%" nowrap rowspan="<c:out value="${mainRowSpan}"/>">
																				<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].dos" id="dos<c:out value="${dataCount}"/>"  onclick="return getCalendar(document.genericForm.dos${dataCount},null,null,document.genericForm.dom${dataCount},document.genericForm.today);" value="${formattedDos}" size="8" maxlength="10" class="inputBox pointer"/>
																			</td>
																			<td width="5%" nowrap rowspan="<c:out value="${mainRowSpan}"/>">
																				<input type="hidden" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].expirationDate" id="expirationDate${dataCount}" value="${localeExpireDate}"/>        
																				<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].expirationDateString" id="expirationDateString<c:out value="${dataCount}"/>"  onclick="return getCalendar(document.genericForm.expirationDateString${dataCount},null,null,document.genericForm.todayoneyear,null,'Y');"  onchange="expiredDateChanged(${dataCount});" value="${formattedExpirationDate}" size="8" maxlength="10" class="inputBox pointer"/>
																			</td>
																			<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
																				<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].quantityReceived" id="quantityReceived<c:out value="${dataCount}"/>" value="<c:out value="${status.current.quantityReceived}"/>" size="4" maxlength="15" class="inputBox"/>
																			</td>
																			<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
																				<c:choose>
																					<c:when test="${status.current.skipReceivingQc == 'Y' && (status.current.orderQtyUpdateOnReceipt == 'y' || status.current.orderQtyUpdateOnReceipt == 'Y')}" >
																						<c:if test="${status.current.closePoLine != null}" >
																							<c:set var="checkCloseBoxChecked" value='checked'/>
																						</c:if>
																						<input type="checkbox" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].closePoLine" id="closePoLine<c:out value="${dataCount}"/>" value="<c:out value="${status.current.orderQtyUpdateOnReceipt}"/>" <c:out value="${checkCloseBoxChecked}"/> onclick="checkClosePoLine(<c:out value="${dataCount}"/>)"/>
																					</c:when>
																					<c:otherwise>
																						&nbsp;
																					</c:otherwise>
																				</c:choose>
																			</td>
																		</c:if>
																		<td width="5%" class="alignLeft" colspan="2">
																			<input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].packagedQty" id="packagedQty<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.packagedQty}"/>" size="3" maxlength="15" class="inputBox" style="width:25px;"/>
																			X
																			<c:choose>
																				<c:when test="${! empty status.current.receivingPagePackagedSize}">
																					<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].packagedSize" id="packagedSize<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.packagedSize}"/>"/> ${kitstatus.current.packagedSize}
																				</c:when>
																				<c:otherwise>
																					<input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].packagedSize" id="packagedSize<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.packagedSize}"/>" size="3" maxlength="10" class="inputBox" style="width:35px;"/>
																				</c:otherwise>
																			</c:choose>
																			&nbsp;<c:out value="${status.current.purchasingUnitOfMeasure}"/> <c:out value="${status.current.displayPkgStyle}"/>
																			<br/><font class="invisible<c:out value="${colorClass}"/>">____________________</font></td>
																			<c:if test="${kitstatus.index == 0}">
																				<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
																					<c:if test="${status.current.skipReceivingQc == 'Y'}">
																						<select name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].lotStatus" id="lotStatus<c:out value="${dataCount}"/>" class="selectBox" onchange="checkReceiptStatus(<c:out value="${dataCount}"/>)">
																							<c:forEach var="vvLotStatusBean" items="${vvLotStatusBeanCollection}" varStatus="vvlotstatus">
																								<c:set var="jspLabel" value=""/>
																								<c:if test="${fn:length(vvlotstatus.current.jspLabel) > 0}"><c:set var="jspLabel">${vvlotstatus.current.jspLabel}</c:set></c:if>
																								<c:set var="lotStatusValue" value='${vvlotstatus.current.lotStatus}'/>
																								<c:choose>
																									<c:when test="${(statusUpdatePermission == 'Yes'  || qualityControlItem !='Y' )&& onlynonPickableStatusPermission != 'Yes'}"></c:when>
																									<c:otherwise>
																										<c:if test="${vvlotstatus.current.certified == 'Y' || (vvlotstatus.current.pickable == 'Y' && vvlotstatus.current.allocationOrder !=0)}">
																											<c:set var="lotStatusValue" value=''/>
																										</c:if>
																									</c:otherwise>
																								</c:choose>
																								<c:choose>
																									<c:when test="${mainLotStatus == vvlotstatus.current.lotStatus}" >
																										<option value="<c:out value="${vvlotstatus.current.lotStatus}"/>" selected><fmt:message key="${jspLabel}"/></option>
																									</c:when>
																									<c:otherwise>
																										<option value="<c:out value="${vvlotstatus.current.lotStatus}"/>"><fmt:message key="${jspLabel}"/></option>
																									</c:otherwise>
																								</c:choose>
																							</c:forEach>
																						</select>
																					</c:if>
																				</td>
																				<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
																					<textarea name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].receiptNotes" cols="25" rows="3" class="inputBox"><c:out value="${status.current.receiptNotes}"/></textarea>
																				</td>
																				<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
																					<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].deliveryTicket" id="deliveryTicket<c:out value="${dataCount}"/>" value="<c:out value="${status.current.deliveryTicket}"/>" size="6" maxlength="30" class="inputBox"/>
																				</td>
																				<!--  <td width="5%" align="center" rowspan="<c:out value="${mainRowSpan}"/>">
																				<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].qaStatement" id="qaStatement<c:out value="${dataCount}"/>" value="<c:out value="${status.current.qaStatement}"/>" size="1" maxlength="1" class="inputBox">
																				</td>-->
																				<td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
																					<c:if test="${listSize < 2 }">
																						<input type="submit" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick= " return duplpkg(<c:out value="${dataCount}"/>)" value="Dup Line" name="duplicateButton"/>
																					</c:if>
																				</td>
																				<td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
																					<c:if test="${listSize < 2 || manageKitsAsSingleUnit != 'N'}">
																						<input type="submit" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick= " return duplLine(<c:out value="${dataCount}"/>)" value="Dup Pkg" name="duplicateButton"/>
																					</c:if>
																				</td>
																			</c:if>
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:when test="${receivingPermission == 'Yes'}">
																	<input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].updateStatus" value="readOnly"/>
																	<td width="2%">&nbsp;</td>
																	<td width="4%" colspan="2">&nbsp;</td>
																	<td width="8%">&nbsp;</td>
																	<td width="4%">&nbsp;</td>
																	<td width="5%">&nbsp;</td>
																	<td width="5%">&nbsp;</td>
																	<td width="5%">&nbsp;</td>
																	<td width="5%">&nbsp;</td>
																	<td width="5%">&nbsp;</td>
																	<td width="5%">&nbsp;</td>
																	<td width="5%">&nbsp;</td>
																	<td width="5%">&nbsp;</td>
																	<td width="5%">&nbsp;</td>
																	<td width="5%">&nbsp;</td>
																	<td width="5%">&nbsp;</td>
																	<td width="5%">&nbsp;</td>
																	<td width="5%">&nbsp;</td>
																	<td width="2%">&nbsp;</td>
																</c:when>
															</c:choose>
															<c:set var="dataCount" value='${dataCount+1}'/>
														</c:forEach>
													</tr>
												</c:forEach>
											</table>
											<input type="hidden" name="totallines" id="totallines" value="<c:out value="${dataCount}"/>"/>
											
											<!-- If the collection is empty say no data found -->
											<c:if test="${empty receivingViewRelationBeanCollection}" >
												<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
													<tr>
														<td width="100%">
															<fmt:message key="main.nodatafound"/>
														</td>
													</tr>
												</table>
											</c:if>
										</div>
									</div>
									<div class="roundbottom">
										<div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
									</div>
								</div>
							</div>
						</td>
					</tr>
				</table>
				<!-- Search results end -->
			</c:if>
			
			<!-- Hidden element start -->
			<div id="hiddenElements" style="display: none;">
				<input type="hidden" name="userAction" id="userAction" value=""/>
				<input type="hidden" name="searchTypeSelected" id="searchTypeSelected" value="${param.searchType}"/>
				<input type="hidden" name="duplicateLine" id="duplicateLine" value=""/>
				<input type="hidden" name="duplicatePkgLine" id="duplicatePkgLine" value=""/>
				<input type="hidden" name="duplicateKitLine" id="duplicateKitLine" value=""/>
				<input type="hidden" name="sourceHubName"  id="sourceHubName" value="<c:out value="${selectedHubName}"/>"/>
				<input type="hidden" name="sourceHub"  id="sourceHub" value="${param.sourceHub}"/>"/>
				<input name="todayDate" id="todayDate" type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'  />
				<input type="hidden" name="submitPrint" id="submitPrint" value=""/>
				<input type="hidden" name="submitReceive" id="submitReceive" value=""/>
				<input type="hidden" name="displayPartNumber" id="displayPartNumber" value="${displayPartNumber}"/>
				<input name='date60' id='date60' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="-60" datePattern="${dateFormatPattern}"/>'  />
				<input name='today' id='today' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'  />
				<input name='todayoneyear' id='todayoneyear' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="-365" datePattern="${dateFormatPattern}"/>'  />
				<input name='indefiniteDate' id='indefiniteDate' type="hidden" value=''  />
				<input type="hidden" name="personnelId" id="personnelId" value="${personnelBean.personnelId}" />
				<input type="hidden" name="printerLocation" id="printerLocation" value="${personnelBean.printerLocation}" />
				<input type="hidden" name="pageid"  id="pageid" value="${param.pageid }"/>
				<input type="hidden" name="personnelCompanyId"  id="personnelCompanyId" value="${personnelBean.companyId}"/>
				<input type="hidden" name="isAutoPutHub"  id="isAutoPutHub" value=""/>
			</div>
			<!-- Hidden elements end -->
		</div>
		<!-- close of contentArea -->
		
		<!-- Footer message start -->
		<div class="messageBar">&nbsp;</div>
		<!-- Footer message end -->
		
		<%-- show legend Begins --%>
		<div id="showLegendArea" style="display: none;overflow: auto;">
			<table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
				<tr><td width="100px" class="pink">&nbsp;</td><td class="legendText"><fmt:message key="label.aogsupercriticalpo"/></td></tr>
				<tr><td width="100px" class="red">&nbsp;</td><td class="legendText"><fmt:message key="label.criticalpo"/></td></tr>
				<tr><td width="100px" class="orange">&nbsp;</td><td class="legendText"><fmt:message key="label.receiptforexcessmaterialreceivedonpo"/></td></tr>
				<tr><td width="100px" class="green">&nbsp;</td><td class="legendText"><fmt:message key="label.receiptformlitem"/></td></tr>
				<tr><td width="100px" class="error">&nbsp;</td><td class="legendText"><fmt:message key="label.errorprocessingreceipt"/></td></tr>
			</table>
		</div>
		<%-- show legend Ends --%>
	</div>
	<!-- close of interface -->
</tcmis:form>
</body>
</html:html>
