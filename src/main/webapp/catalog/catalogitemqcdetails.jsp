<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class. -->
<%-- Provides CSS for tcmIS L&F --%>
<tcmis:gridFontSizeCss overflow="notHidden"/>
<link rel="stylesheet" type="text/css" href="/css/tabs.css" />
<link rel="stylesheet" type="text/css" href="/css/msdsindexing.css" />

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handles which key press events are disabled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<%@ include file="/common/locale.jsp" %>

<%-- Other Scripts --%>
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>
<script type="text/javascript" src="/js/catalog/catalogitemqcdetails.js"></script>

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

<style>
.field {	
	display:inline-block;
	*display: inline;
	zoom:1;
	vertical-align:top;
	padding-bottom: 5px;
	margin:0 0 5px 5px;
	border-bottom:1px dotted gray;
}
</style>
<title><fmt:message key="label.chemreqqc"/></title>

<script language="JavaScript" type="text/javascript">
var dhxWins = null;
var transitWin = null;
var children = new Array();
var showErrorMessage = false;
var messagesData= { 
	minlength:"<fmt:message key="errors.minlength"/>",
	maxlength:"<fmt:message key="errors.maxlength"/>",
	invalid:"<fmt:message key="errors.invalid"/>",
	mustbeanumber:"<fmt:message key="label.mustbeanumber"/>",
	userpartnr:"<fmt:message key="label.userpartnumber"/>",
	itemid:"<fmt:message key="label.itemid"/>",
	itemcategory:"<fmt:message key="label.category"/>",
	itemtype:"<fmt:message key="label.type"/>",
	materialdesc:"<fmt:message key="label.materialdescription"/>",
	shelflifedays:"<fmt:message key="label.mfgshelflifedays"/>",
	shelflifebasis:"<fmt:message key="label.basis"/>",
	minstoragetemp:"<fmt:message key="label.minstoragetemp"/>",
	maxstoragetemp:"<fmt:message key="label.maxstoragetemp"/>",
	storagetempunit:"<fmt:message key="label.storagetempunit"/>",
	grade:"<fmt:message key="label.grade"/>",
	mfgpartnr:"<fmt:message key="label.mfgpartnr"/>",
	nrcomp:"<fmt:message key="label.nrcomp"/>",
	partsize:"<fmt:message key="label.partsize"/>",
	sizeunit:"<fmt:message key="label.sizeunit"/>",
	pkgstyle:"<fmt:message key="label.packagingstyle"/>",
	positive:"<fmt:message key="label.positivenumber"/>",
	netwt:"<fmt:message key="label.netwt"/>",
	netwtunit:"<fmt:message key="label.netwtunit"/>",
	dimension:"<fmt:message key="label.dimension"/>",
	pleaseselect:"<fmt:message key="errors.selecta"/>",
	pleasewait:"<fmt:message key="label.pleasewait"/>",
	maxlessthanmintemp:"<fmt:message key="label.maxlessthanmintemp"/>",
	itemapproved:"<fmt:message key="label.approved"/>",
	itemsubmitted:"<fmt:message key="label.submitted"/>",
	rejectioncomments:"<fmt:message key="label.comments"/>",
	reversecomments:"<fmt:message key="label.comments"/>",
	reversing:"<fmt:message key="label.reversingrequest"/>",
	pleaseentervaluefor:"<fmt:message key="label.pleaseentervalue"/>"+" "+" <fmt:message key="label.fornext"/>",
	comments:"<fmt:message key="label.comments"/>",
	reversecomperror:"<fmt:message key="label.pleaseselectcomptoreverse"/>"
};

var pkgStyleList = [
<c:forEach var="pkgStyle" items="${vvPkgStyleBeanCollection}" varStatus="status2">
<c:if test="${status2.index > 0}">,</c:if>
"<c:out value="${pkgStyle.pkgStyle}"/>"
</c:forEach>
];
</script>
</head>
<c:set var="module"><tcmis:module/></c:set>
<c:set var="vendorTask" value="${not empty catalogQueueRow}"/>
<c:set var="isWescoModule" value="${module eq 'haas' or module eq 'catalog'}"/>
<c:if test="${vendorTask}">
	<c:set var="vendorOpen" value="${catalogQueueRow.status eq 'Open'}"/>
	<c:set var="vendorAssigned" value="${catalogQueueRow.status eq 'Assigned'}"/>
	<c:set var="vendorQc" value="${catalogQueueRow.status eq 'Pending QC'}"/>
	<c:set var="masterDataApproval" value="${catalogQueueRow.status eq 'Pending Approval'}"/>
	<c:set var="formerlyVendorTask" value="${catalogQueueRow.status eq 'Closed'}"/>
	<c:set var="itemApproved" value="${itemApproved}"/>
	<c:set var="userIsAssignee" value="${catalogQueueRow.assignedTo eq personnelBean.personnelId}"/>
	<c:set var="vendorCanSubmit" value="${vendorAssigned && userIsAssignee}"/>
	<c:set var="vendorCanApprove" value="${vendorQc && not userIsAssignee}"/>
	<c:set var="supplierIsWesco" value="${catalogQueueRow.supplierWesco}"/>
	<c:set var="altSupplier" value="${catalogQueueRow.altSupplier}"/>
	<c:set var="vendorTaskApproved" value="${catalogQueueRow.status eq 'Approved'}"/>
</c:if>

<c:set var="masterData" value="${((vendorCanSubmit && supplierIsWesco) || not vendorTask) && isWescoModule}" />

<body onload="myOnLoad();">
	<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
		<table width="100%" border="0" cellpadding="2" cellspacing="1">
			<tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td align="center" id="transitLabel"></td></tr>
			<tr><td align="center"><img src="/images/rel_interstitial_loading.gif" align="middle"></td></tr>
		</table>
	</div>
	<!-- open contentArea -->
	<div id="mainPage" class="contentArea">
		<tcmis:form action="/catalogitemqcdetails.do" onsubmit="return submitSearchOnlyOnce();">
			<div class="roundcont filterContainer">
			<div class="roundright">
				<div class="roundtop">
					<div class="roundtopright">
						<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
					</div>
				</div>
				<div class="roundContent">
					<div class="boxhead">
						<c:if test="${masterData || vendorCanSubmit || (vendorCanApprove && not supplierIsWesco)}">
						<span id="saveLink">
							<a href="#" onclick="saveItem();"><fmt:message key="label.save"/></a>
						</span>
						</c:if>
						<c:if test="${vendorCanSubmit && not masterData}">
						<span id="submitLink">
							| <a href="#" onclick="submitItem();"><fmt:message key="label.submit"/></a>
						</span>
						</c:if>
						<c:if test="${vendorCanApprove && not masterData}">
						<span id="approveLink">
							| <a href="#" onclick="submitItem();"><fmt:message key="label.approve"/></a>
						</span>
						</c:if>
						<c:if test="${masterData}">
						<span id="approveLink">
							| <a href="#" onclick="approveItem();"><fmt:message key="label.approve"/></a>
						</span>
						<div id="rejectLink" style="position:relative;display:inline;z-index:1">
							| <a id="rejectCommentBtn" href="#" onclick="toggleRejectionPopup();"><fmt:message key="label.reject"/></a>
							<div id="rejectCommentPopup" style="position:absolute;left:0px;top:20px;width:400px;z-index:200;display:none;background-color:#e5e5e5;padding:5px;border:1px solid gray;">
								<label for="rejectionComments"><fmt:message key="label.comments"/>:</label>
								<span style="float:right;cursor:pointer" onclick="toggleRejectionPopup();">x</span>
								<textarea id="rejectionComments" name="rejectionComments" rows="10" class="inputBox" style="width:95%;position:relative"></textarea>
								<br/>
								<input type="button" id="rejectConfirmButton" name="rejectConfirmButton" onclick="submitRejectCannotFulfill();" value="<fmt:message key="label.reject"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
							</div>
						</div>
						<div id="reverseLink" style="position:relative;display:inline;z-index:1">
							| <a id="reverseCommentBtn" href="#" onclick="toggleReversalPopup();" style="z-index:1"><fmt:message key="label.reverse"/></a>
							<div id="reverseCommentPopup" style="position:absolute;left:0px;top:20px;width:500px;z-index:200;display:none;background-color:#e5e5e5;padding:5px;border:1px solid gray;">
								<span style="float:right;cursor:pointer" onclick="toggleReversalPopup();">x</span>
								<label for="reverseComments"><fmt:message key="label.comments"/>:</label>
								<textarea id="reverseComments" name="reverseComments" rows="10" class="inputBox" style="width:95%;position:relative"></textarea>
								<br/>
								<table style="border: none">
									<c:forEach var="item" items="${resultsCollection}" varStatus="status">
										<c:if test="${status.first && not status.last}">
									<tr>
										<td>&nbsp;</td>
										<td style="text-align: center">
											<a href="#" onclick="selectAllNoFault(true);"><fmt:message key="label.all"/></a> |
											<a href="#" onclick="selectAllNoFault(false);"><fmt:message key="label.none"/></a>
										</td>
										<td style="text-align: center">
											<a href="#" onclick="selectAllVendorError(true);"><fmt:message key="label.all"/></a> |
											<a href="#" onclick="selectAllVendorError(false);"><fmt:message key="label.none"/></a>
										</td>
										<td>&nbsp;</td>
									</tr>
										</c:if>
									<tr>
										<td>
											<label><fmt:message key="label.component"/> <c:out value="${status.count}"/>: </label>
										</td>
										<td>
											<input type="radio" id="part[${status.index}].noFaultCheckbox" name="part[${status.index}].componentReversed" value="NO_FAULT" class="radioBtns" ${status.first && status.last?'checked':''}/>
											<label for="part[${status.index}].noFaultCheckbox"><fmt:message key="label.reversefaultyrequest"/></label>
										</td>
										<td>
											<input type="radio" id="part[${status.index}].vendorErrorCheckbox" name="part[${status.index}].componentReversed" value="VENDOR_ERROR" class="radioBtns"/>
											<label for="part[${status.index}].vendorErrorCheckbox"><fmt:message key="label.reversevendorerror"/></label>
										</td>
										<td>
											<input type="button" id="part[${status.index}].clearReverse" name="part[${status.index}].clearReverse" value="<fmt:message key="label.clear"/>" ${status.first && status.last?'disabled':''} onclick="clearReverse(${status.index});" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'"/>
										</td>
									</tr>
									</c:forEach>
								</table>
								<input type="button" id="reverseConfirmButton" name="reverseConfirmButton" onclick="submitReverse();" value="<fmt:message key="label.reverse"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"/>
							</div>
						</div>
						</c:if>
						<c:if test="${masterData && not empty altSupplier}">
						<span id="sendBackToVendorLink">
							| <a href="#" onclick="reportProblem('vendor');"><fmt:message key="label.sendbacktovendor"/></a>
						</span>
						</c:if>
						<c:if test="${(not masterData && (vendorCanSubmit || vendorCanApprove)) || (isWescoModule && masterDataApproval)}">
						<span id="reportProblemLink">
							| <a href="#" onclick="reportProblem();"><fmt:message key="label.reportasproblem"/></a>
						</span>
						</c:if>
						<span id="requestLink">
							| <a href="#" onclick="viewRequest();"><fmt:message key="label.viewrequest"/></a>
						</span>
						<c:if test="${vendorTask}">
						<span id="contactLogLink">
							| <a href="#" onclick="viewContactLog();"><fmt:message key="label.createviewcontactlog"/></a>
						</span>
						</c:if>
						<c:if test="${vendorTask || formerlyVendorTask}">
							<span>| <a href="#" onclick="viewProblemHistory();"><fmt:message key="label.viewproblemhistory"/></a></span>
						</c:if>
						</div>
						<br />
						<span class="optionTitleBold"><fmt:message key="label.catalogitemqc"/></span>
					</div>
				    <c:if test="${vendorTask && not isWescoModule}">
					<div class="field" style="width:96%">
						<label for="qId" class="optionTitleBold"><fmt:message key="label.queueid"/>:</label>
							<input type="text" id="qId" name="qId" style="width:70%" class="msdsReadOnlyField" readonly tabindex="-1" value="${catalogQueueRow.qId}"></input>
						<br />
						<label for="task" class="optionTitleBoldRight"><fmt:message key="label.task"/>:&nbsp;</label>
							<input class="msdsReadOnlyField" style="width:70%" type="text" name="task" id="task" value="${catalogQueueRow.task}"/>
					</div>
					</c:if>
					<c:if test="${masterData || isWescoModule}">
					<div style="width:20%;" class="field">
						<label for="requestIdDisp" class="optionTitle"><fmt:message key="label.requestid"/>:</label><br/>
						<input type="text" id="requestIdDisp" name="requestIdDisp" class="optionTitleBold msdsReadOnlyField" readonly tabindex="-1" value="${requestHeader.requestId} - ${requestHeader.lineItem}"></input>
						<input type="hidden" id="requestId" name="requestId" value="${requestHeader.requestId}"/>
						<input name="lineItem" id="lineItem" type="hidden" value="${requestHeader.lineItem}"/>

					</div>
					<div style="width:20%;" class="field">
						<label for="requestorPhone" class="optionTitle"><fmt:message key="label.phone"/>:</label><br/>
						<input type="text" id="requestorPhone" name="requestorPhone" class="optionTitleBold msdsReadOnlyField" readonly tabindex="-1" value="<c:out value="${requestHeader.phone}"/>"></input>
					</div>
					<div style="width:20%;" class="field">
						<label for="companyId" class="optionTitle"><fmt:message key="label.company"/>:</label><br/>
						<input type="text" id="companyId" name="companyId" class="optionTitleBold msdsReadOnlyField" readonly tabindex="-1" value="<c:out value="${requestHeader.companyId}"/>"></input>
					</div>
					<div style="width:20%;" class="field">
						<label for="submitdate" class="optionTitle"><fmt:message key="label.submitdate"/>:</label><br/>
						<input type="text" id="submitdate" name="submitdate" class="optionTitleBold msdsReadOnlyField" readonly tabindex="-1" value="${requestHeader.submitdate}"></input>
					</div>
					<div style="width:20%;" class="field">
						<label for="requestor" class="optionTitle"><fmt:message key="label.requestor"/>:</label><br/>
						<input type="text" id="requestor" name="requestor" class="optionTitleBold msdsReadOnlyField" readonly tabindex="-1" value="<c:out value="${requestHeader.fullName}"/>"></input>
					</div>
					<div style="width:20%;" class="field">
						<label for="requestorEmail" class="optionTitle"><fmt:message key="label.email"/>:</label><br/>
						<input type="text" id="requestorEmail" name="requestorEmail" class="optionTitleBold msdsReadOnlyField" readonly tabindex="-1" value="${requestHeader.email}"></input>
					</div>
					<div style="width:20%;" class="field">
						<label for="catalogId" class="optionTitle"><fmt:message key="label.catalogid"/>:</label><br/>
						<input type="text" id="catalogId" name="catalogId" class="optionTitleBold msdsReadOnlyField" readonly tabindex="-1" value="<c:out value="${requestHeader.catalogId}"/>"></input>
					</div>
					<div style="width:20%;" class="field">
						<label for="engineeringEvaluation" class="optionTitle"><fmt:message key="label.evaluation"/>:</label><br/>
						<input type="text" id="engineeringEvaluation" name="engineeringEvaluation" class="optionTitleBold msdsReadOnlyField" readonly tabindex="-1" value="${requestHeader.engineeringEvaluation}"></input>
					</div>
					<div style="width:60%;" class="field">
					<div style="float:left">
						<label for="catPartNo" class="optionTitle"><fmt:message key="label.userpartnumber"/>:</label><br/>
						<input type="text" id="catPartNo" name="catPartNo" class="inputBox" value="<c:out value="${requestHeader.catPartNo}"/>"/>
						<input type="button" id="userPartNo" name="userPartNo" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" value="..." onclick="showExistingItems()"></input>
					</div>
					<div style="float:right">
						<c:if test="${vendorTask}">
							<label for="qId" class="optionTitleBold"><fmt:message key="label.queueid"/>:</label><br/>
							<input type="text" id="qId" name="qId" style="width:70%" class="msdsReadOnlyField" readonly tabindex="-1" value="${catalogQueueRow.qId}"/>
							<input type="hidden" id="task" name="task" value="${catalogQueueRow.task}"/>
						</c:if>
					</div>
					</div>
					<div style="width:20%;" class="field">
						<label for="startingView" class="optionTitle"><fmt:message key="label.startingview"/>:</label><br/>
						<input type="text" id="startingView" name="startingView" class="optionTitleBold msdsReadOnlyField" readonly tabindex="-1" value="${requestHeader.startingView}"></input>
					</div>
					<div style="width:90%;" class="field">
						<label for="partDescription" class="optionTitle"><fmt:message key="label.userpartdesc"/>:</label><br/>
						<input type="text" id="partDescription" name="partDescription" class="inputBox" style="width:100%" value="<c:out value="${requestHeader.partDescription}"/>"></input>
					</div>
					</c:if>
				</div>
				<div class="roundbottom">
					<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
				</div>
			</div>
			</div>
			
			<div class="roundcont contentContainer">
			<div class="roundright">
				<div class="roundtop">
					<div class="roundtopright">
						<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
					</div>
				</div>
				<div class="roundContent">
					<div class="optionTitleBold"><fmt:message key="label.itemdata"/></div>
					<div style="width:15%;" class="field">
						<label for="itemId" class="optionTitle"><fmt:message key="label.itemid"/>:</label><br/>
						<input type="text" id="itemId" name="itemId" class="optionTitleBold msdsReadOnlyField" style="width:60%;" readonly tabindex="-1" value="${itemHeader.itemId}"></input>
						<input type="button" id="userPartNo" name="userPartNo" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" onclick="getItemId();" value="..."></input>
					</div>
					<div style="width:35%;" class="field">
						<label for="categoryId" class="optionTitle"><fmt:message key="label.category"/>:</label><br/>
						<select id="categoryId" name="categoryId" class="selectBox">
							<option value=""><fmt:message key="label.pleaseselect"/></option>
							<c:forEach var="category" items="${vvCategoryBeanCollection}" varStatus="status">
							<option value="${category.categoryId}" ${itemHeader.categoryId eq category.categoryId?'selected':''}><c:out value="${category.categoryDesc}"/></option>
							</c:forEach>
						</select>
					</div>
					<div style="width:10%;" class="field">
						<label for="itemType" class="optionTitle"><fmt:message key="label.type"/>:</label><br/>
						<select id="itemType" name="itemType" class="selectBox">
							<c:set var="selectedType" value="${empty itemHeader.itemType?'MA':itemHeader.itemType}"/>
							<c:forEach var="type" items="${vvItemTypeBeanCollection}" varStatus="status">
							<option value="<c:out value="${type.itemType}"/>" ${selectedType eq type.itemType?'selected':''}><c:out value="${type.itemType}"/></option>
							</c:forEach>
						</select>
					</div>
					<c:if test="${masterData}">
					<div style="width:10%;" class="field">
						<label for="itemVerifiedSelect" class="optionTitle"><fmt:message key="label.itemverified"/>:</label><br/>
						<select id="itemVerifiedSelect" name="itemVerifiedSelect" class="selectBox" onchange="selectItemVerified();">
							<option value="N">N</option>
							<option value="Y">Y</option>
						</select>
					</div>
					</c:if>
					<div style="width:10%;" class="field">
						<label for="sampleSize" class="optionTitle"><fmt:message key="label.samplesize"/>:</label><br/>
						<select id="sampleSize" name="sampleSize" class="selectBox">
							<option value="N">N</option>
							<option value="Y" ${itemHeader.sampleOnly eq 'Y'?'selected':'' }>Y</option>
						</select>
					</div>
					<div style="width:15%;" class="field">
						<label class="optionTitle">
							<input type="checkbox" id="inseparableKit" name="inseparableKit" class="radioBtns" ${itemHeader.inseparableKit?'checked':''}></input>
							<fmt:message key="label.inseparablekit"/>
						</label>
					</div>
					<c:if test="${masterData}">
					<div>
						<input type="button" id="itemNotes" name="itemNotes" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="showItemNotes()" value="<fmt:message key="label.itemnotes"/>"></input>
					</div>
					</c:if>
				</div>
				<div class="roundbottom">
					<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
				</div>
			</div>
			</div>
			
			<div style="width:100%;clear:both;"></div>
			
			<div id="itemTabs" class="haasTabs">
			<ul>
			<c:set var="compCount" value="${0}"/>
				<c:forEach var="item" items="${resultsCollection}" varStatus="status">
					<c:set var="compCount" value="${status.count}"/>
					<li>
						<a id="itemLink${status.index}" class="<c:choose><c:when test="${status.first}">selectedTab</c:when><c:otherwise>tabLeftSide</c:otherwise></c:choose>" href="javascript: toggleItem('${status.index}');">
							<span class="<c:choose><c:when test="${status.first}">selectedSpanTab</c:when><c:otherwise>tabRightSide</c:otherwise></c:choose>" id="itemLinkSpan${status.index}">
								<img class="iconImage" src="/images/spacer14.gif">
								<fmt:message key="label.component"/> ${status.count}
							</span>
						</a>
					</li>
				</c:forEach>
			</ul>
			</div>
			<c:forEach var="item" items="${resultsCollection}" varStatus="status">
				<div id="itemDiv${status.index}" <c:if test="${!status.first}">style="display:none"</c:if>>				
				<div class="roundcont filterContainer">
					<div class="roundright">
						<div class="roundtop">
							<div class="roundtopright">
								<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
							</div>
						</div>
						<div class="roundContent">
							<div class="boxhead"></div>
							<div id="itemData${status.index}">
				<div style="width:40%;" class="field">
					<label for="part[${status.index}].mfgId" class="optionTitle"><fmt:message key="label.mfgid"/>:</label><br/>
					<input type="text" id="part[${status.index}].mfgId" name="part[${status.index}].mfgId" class="optionTitleBold msdsReadOnlyField" readonly tabindex="-1" value="${item.mfgId}"></input>
				</div>
				<div style="width:40%;" class="field">
					<label for="part[${status.index}].manufacturer" class="optionTitle"><fmt:message key="label.manufacturer"/>:</label><br/>
					<input type="text" id="part[${status.index}].manufacturer" name="part[${status.index}].manufacturer" class="optionTitleBold msdsReadOnlyField" readonly tabindex="-1" value="<c:out value="${empty item.mfgDesc?item.manufacturer:item.mfgDesc}"/>"></input>				
				</div>
				<div style="width:40%;" class="field">
					<label for="part[${status.index}].mfgPhone" class="optionTitle"><fmt:message key="label.mfgphone"/>:</label><br/>
					<input type="text" id="part[${status.index}].mfgPhone" name="part[${status.index}].mfgPhone" class="optionTitleBold msdsReadOnlyField" readonly tabindex="-1" value="<c:out value="${item.phone}"/>"></input>
				</div>
				<div style="width:40%;" class="field">
					<label for="part[${status.index}].mfgContact" class="optionTitle"><fmt:message key="label.mfgcontact"/>:</label><br/>
					<span class="optionTitleBold" style="float:left"><a href="${item.mfgUrl}" target="_blank"><c:out value="${item.contact}"/></a></span>
					<div style="float:right"></div>
				</div>
				<div style="width:40%;" class="field">
					<label for="part[${status.index}].mfgEmail" class="optionTitle"><fmt:message key="label.mfgemail"/>:</label><br/>
					<input type="text" id="part[${status.index}].mfgEmail" name="part[${status.index}].mfgEmail" class="optionTitleBold msdsReadOnlyField" readonly tabindex="-1" value="${item.email}"></input>
				</div>
				<div style="width:40%;" class="field">
					<label for="part[${status.index}].mfgNotes" class="optionTitle"><fmt:message key="label.mfgnotes"/>:</label><br/>
					<div id="part[${status.index}].mfgNotes" class="optionTitleBold"><c:out value="${item.notes}"/></div>
					<div style="float:right"></div>
				</div>
				<div style="width:40%;" class="field">
					<label for="part[${status.index}].materialId" class="optionTitle"><fmt:message key="label.materialid"/>:</label><br/>
					<input type="text" id="part[${status.index}].materialId" name="part[${status.index}].materialId" class="optionTitleBold msdsReadOnlyField" style="width:25%;float:left" readonly tabindex="-1" value="${item.materialId}"></input>
					<input type="button" id="part[${status.index}].msdsViewer" name="part[${status.index}].msdsViewer" class="inputBtns" style="float:right" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.msds"/>" onclick="showMsds();"/>
				</div>
				<div style="width:40%;" class="field">
					<label for="part[${status.index}].materialDesc" class="optionTitle"><fmt:message key="label.materialdescription"/>:</label><br/>
					<input type="text" id="part[${status.index}].materialDesc" name="part[${status.index}].materialDesc" class="inputBox" style="width:100%;float:left" value="<c:out value="${item.materialDesc}"/>"></input>
				</div>
				<div style="width:40%;" class="field">
					<div style="float:left">
					<label for="part[${status.index}].shelfLifeDays" class="optionTitle"><fmt:message key="label.mfgshelflifedays"/>:</label><br/>
					<input type="text" id="part[${status.index}].shelfLifeDays" name="part[${status.index}].shelfLifeDays" class="inputBox" value="${item.shelfLifeDays}"></input>
					</div>
					<div style="float:right"></div>
				</div>
				<div style="width:40%;" class="field">
					<label for="part[${status.index}].mfgTradeName" class="optionTitle"><fmt:message key="label.tradename"/>:</label><br/>
					<input type="text" id="part[${status.index}].mfgTradeName" name="part[${status.index}].mfgTradeName" class="optionTitleBold msdsReadOnlyField" readonly tabindex="-1" value="<c:out value="${item.mfgTradeName}"/>"></input>
				</div>
				<div style="width:40%;" class="field">
					<div style="float:left">
					<label for="part[${status.index}].shelfLifeBasis" class="optionTitle"><fmt:message key="label.basis"/>:</label><br/>
					<select id="part[${status.index}].shelfLifeBasis" name="part[${status.index}].shelfLifeBasis" class="selectBox">
						<option value=""><fmt:message key="label.pleaseselect"/></option>
						<c:forEach var="basis" items="${vvShelfLifeBasisBeanCollection}" varStatus="status2">
						<option value="${basis.shelfLifeBasis}" ${item.shelfLifeBasis eq basis.shelfLifeBasis?'selected':''}><fmt:message key="${basis.jspLabel}"/></option>
						</c:forEach>
					</select>
					</div>
					<div style="float:right"></div>
				</div>
				<div style="width:50%;" class="field">
				<div style="float:left">
					<span class="optionTitle"><fmt:message key="label.mfgstoragetemp"/>:</span>
					<div style="display:inline-block;*display:inline;zoom:1;width:15%;margin-right:5px;">
						<label for="part[${status.index}].minTemp" class="optionTitle"><fmt:message key="label.min"/>:</label><br/>
						<input type="text" id="part[${status.index}].minTemp" name="part[${status.index}].minTemp" class="inputBox" size="4" value="${item.minTemp}"></input>
					</div>
					<div style="display:inline-block;*display:inline;zoom:1;width:15%;margin-right:5px;">
						<label for="part[${status.index}].maxTemp" class="optionTitle"><fmt:message key="label.max"/>:</label><br/>
						<input type="text" id="part[${status.index}].maxTemp" name="part[${status.index}].maxTemp" class="inputBox" size="4" value="${item.maxTemp}"></input>
					</div>
					<div style="display:inline-block;*display:inline;zoom:1;width:15%">
						<label for="part[${status.index}].tempUnits" class="optionTitle"><fmt:message key="label.units"/>:</label><br/>
						<select id="part[${status.index}].tempUnits" name="part[${status.index}].tempUnits" class="selectBox">
							<option value=""><fmt:message key="label.none"/></option>
							<option value="F" ${item.tempUnits eq 'F'?'selected':''}><fmt:message key="label.fforfahrenheit"/>&deg;</option>
							<option value="C" ${item.tempUnits eq 'C'?'selected':''}><fmt:message key="label.cforcelsius"/>&deg;</option>
						</select>
					</div>
				</div>
				<%-- <input type="button" id="part[${status.index}].msdsViewer" name="part[${status.index}].msdsViewer" class="inputBtns" style="float:left" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.msds"/>" onclick="showMsds();"/> --%>
				<div style="float:right"></div>
				</div>
				<div style="width:40%;" class="field">
				<div style="float:left">
					<label for="part[${status.index}}].grade" class="optionTitle"><fmt:message key="label.grade"/>:</label><br/>
					<input type="text" id="part[${status.index}].grade" name="part[${status.index}].grade" class="inputBox" style="width:95%" value="<c:out value="${item.grade}"/>"></input>
				</div>
				<div style="float:right"></div>
				</div>
				<div style="width:40%;" class="field">
				<div style="float:left">
					<label for="part[${status.index}].mfgPartNo" class="optionTitle"><fmt:message key="label.mfgpartnr"/>:</label><br/>
					<input type="text" id="part[${status.index}].mfgPartNo" name="part[${status.index}].mfgPartNo" class="inputBox" value="<c:out value="${item.mfgPartNo}"/>"></input>
				</div>
				<div style="float:right"></div>
				</div>
				<div style="width:10%"></div>
				<%-- <div style="width:20%;" class="field">
					<label for="part[${status.index}].labelColor" class="optionTitle"><fmt:message key="label.labelcolor"/>:</label><br/>
					<input type="text" id="part[${status.index}].labelColor" name="part[${status.index}].labelColor" class="optionTitleBold msdsReadOnlyField" readonly value="${item.labelColor}"></input>
				</div> --%>
				<div style="width:10%;" class="field">
				<div style="float:left">
					<label for="part[${status.index}].componentsPerItem" class="optionTitle"><fmt:message key="label.nrcomp"/>:</label><br/>
					<input type="text" id="part[${status.index}].componentsPerItem" name="part[${status.index}].componentsPerItem" class="inputBox" size="4" value="${item.componentsPerItem}"></input>
				</div>
				<div style="float:right"></div>
				</div>
				<div style="width:10%;" class="field">
				<div style="float:left">
					<label for="part[${status.index}].partSize" class="optionTitle"><fmt:message key="label.partsize"/>:</label><br/>
					<input type="text" id="part[${status.index}].partSize" name="part[${status.index}].partSize" class="inputBox" size="5" value="${item.partSize}"></input>
				</div>
				<div style="float:right"></div>
				</div>
				<div style="width:20%;" class="field">
				<div style="float:left">
					<label for="part[${status.index}].sizeUnit" class="optionTitle"><fmt:message key="label.sizeunit"/>:</label><br/>
					<select id="part[${status.index}].sizeUnit" name="part[${status.index}].sizeUnit" class="selectBox">
						<option value=""><fmt:message key="label.pleaseselect"/></option>
						<c:forEach var="sizeUnit" items="${vvSizeUnitBeanCollection}" varStatus="status2">
						<option value="<c:out value="${sizeUnit.sizeUnit}"/>" ${sizeUnit.sizeUnit eq item.sizeUnit?'selected':''}><c:out value="${sizeUnit.sizeUnit}"/></option>
						</c:forEach>
					</select>
				</div>
				<div style="float:right"></div>
				</div>
				<div style="width:40%;position:relative" class="field">
				<div style="float:left">
					<label for="part[${status.index}].pkgStyleSearch" class="optionTitle"><fmt:message key="label.packagingstyle"/>:</label><br/>
					<input type="text" id="part[${status.index}].pkgStyleSearch" name="part[${status.index}].pkgStyleSearch" class="inputBox" style="width:90%;"value="<c:out value="${item.pkgStyle}"/>"/>
					<input type="hidden" id="part[${status.index}].pkgStyle" name="part[${status.index}].pkgStyle" value="<c:out value="${item.pkgStyle}"/>"/>
					<div id="part[${status.index}].pkgStylePopup" style="position:absolute;display:none;left:2px;top:32px;width:90%;height:300%;overflow-y:scroll;background-color:snow;border:2px solid gray;"></div>
					<%-- <select id="part[${status.index}].pkgStyle" name="part[${status.index}].pkgStyle" class="selectBox">
						<option value=""><fmt:message key="label.pleaseselect"/></option>
						<c:forEach var="pkgStyle" items="${vvPkgStyleBeanCollection}" varStatus="status2">
						<option value="<c:out value="${pkgStyle.pkgStyle}"/>" ${pkgStyle.pkgStyle eq item.pkgStyle?'selected':''}><c:out value="${pkgStyle.pkgStyle}"/></option>
						</c:forEach>
					</select> --%>
				</div>
				<div style="float:right"></div>
				</div>
				<div style="width:20%;" class="field">
				<div style="float:left">
					<label for="part[${status.index}].dimension" class="optionTitle"><fmt:message key="label.dimension"/>:</label><br/>
					<input type="text" id="part[${status.index}].dimension" name="part[${status.index}].dimension" class="inputBox" value="<c:out value="${item.dimension}"/>"></input>
				</div>
				<div style="float:right"></div>
				</div>
				<div style="width:10%;" class="field">
				<div style="float:left">
					<label for="part[${status.index}].netwt" class="optionTitle"><fmt:message key="label.netwt"/>:</label><br/>
					<input type="text" id="part[${status.index}].netwt" name="part[${status.index}].netwt" class="inputBox" size="4" value="${item.netwt}"></input>
				</div>
				<div style="float:right"></div>
				</div>
				<div style="width:30%;" class="field">
				<div style="float:left">
					<label for="part[${status.index}].netwtUnit" class="optionTitle"><fmt:message key="label.netwtunit"/>:</label><br/>
					<select id="part[${status.index}].netwtUnit" name="part[${status.index}].netwtUnit" class="selectBox">
						<option value=""><fmt:message key="label.pleaseselect"/></option>
						<c:forEach var="netwtUnit" items="${vvNetwtUnitCollection}" varStatus="status2">
						<option value="<c:out value="${netwtUnit.sizeUnit}"/>" ${item.netwtUnit eq netwtUnit.sizeUnit?'selected':''}><c:out value="${netwtUnit.sizeUnit}"/></option>
						</c:forEach>
					</select>
				</div>
				<div style="float:right"></div>
				</div>
				<%-- <div style="width:10%;" class="field">
				<div style="float:left">
					<label for="part[${status.index}].sampleSize" class="optionTitle"><fmt:message key="label.samplesize"/>:</label><br/>
					<input type="text" id="part[${status.index}].sampleSize" name="part[${status.index}].sampleSize" class="optionTitleBold msdsReadOnlyField" readonly tabindex="-1" value="${item.sampleSize}"></input>
				</div>
				<div style="float:right"></div>
				</div> --%>
				<div>
					<label for="part[${status.index}].comments" class="optionTitle"><fmt:message key="label.comments"/>:</label><br/>
					<textarea id="part[${status.index}].comments" name="part[${status.index}].comments" class="inputBox" rows="3" cols="100"><c:out value="${item.comments}"/></textarea>
				</div>
							</div>
						</div>
						<div class="roundbottom">
							<div class="roundbottomright">
								<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
							</div>
						</div>
					</div>
				</div>
				<div style="display:none">
					<input type="hidden" id="part[${status.index}].itemVerified" name="part[${status.index}].itemVerified" value="${item.itemVerified}"/>
				</div>
				</div>
			</c:forEach>
			<!-- Hidden element start -->
			<div id="hiddenElements" style="display: none;">
				<input name="uAction" id="uAction" type="hidden"/>
				<c:if test="${vendorTask}">
				<input name="requestId" id="requestId" type="hidden" value='<tcmis:jsReplace value="${catalogQueueRow.catalogAddRequestId}"/>'/>
				<input name="lineItem" id="lineItem" type="hidden" value='<tcmis:jsReplace value="${catalogQueueRow.lineItem}"/>'/>
				</c:if>
				<input name="totalComps" id="totalComps" type="hidden" value="${compCount}" />
				<input name="itemVerified" id="itemVerified" type="hidden" value="" />
				<input name="itemApproved" id="itemApproved" type="hidden" value="${itemApproved}" />
				<input name="itemRejected" id="itemRejected" type="hidden" value="${itemRejected}" />
				<input name="approvalRole" id="approvalRole" type="hidden" value="<tcmis:jsReplace value="${param.approvalRole}"/>" />
				<input name="myErr" id="myErr" value="${tcmisError}" type="hidden"/>
				<input type="hidden" name="catalogQueueRowStatus" id="catalogQueueRowStatus" value="${vendorTask?catalogQueueRow.status:''}"/>
				<input name="catalogAddItemId" id="catalogAddItemId" type="hidden" value='<tcmis:jsReplace value="${param.catalogAddItemId}"/>' />
				<input name="vendorTask" id="vendorTask" type="hidden" value="${vendorTask?'Y':'N'}" />
				<input name="qAssignee" id="qAssignee" type="hidden" value="${empty catalogQueueRow?'':catalogQueueRow.assignedTo}"/>
				<input name="currentUser" id="currentUser" type="hidden" value="${personnelBean.personnelId}"/>
			</div>
			<!-- Hidden elements end -->
			
		</tcmis:form>
	</div>
	<!-- close of contentArea -->

	<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesArea" class="errorMessages" style="display:none;z-index:5;">
		<div id="errorMessagesAreaTitle" class="hd"><fmt:message key="label.errors"/></div>
		<div id="errorMessagesAreaBody" class="bd">
			<html:errors /> 
			${tcmisError} 
			<c:forEach items="${tcmISErrors}" varStatus="status">
		  		${status.current}<br />
			</c:forEach>
		</div>
		<script type="text/javascript">
			<c:choose>
				<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmisError }">showErrorMessage = false;</c:when>
				<c:otherwise>showErrorMessage = true;</c:otherwise>
			</c:choose>   
		</script>
	</div>
</body>
</html:html>
