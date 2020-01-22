<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

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

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the users preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support for column type hcal-->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>

<!-- Add any other javascript you need for the page here -->
<!-- These are for the Grid-->

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<script type="text/javascript" src="/js/hub/customerreturnsresults.js"></script>

<title></title>

<script language="JavaScript" type="text/javascript">
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = {
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		recordFound:"<fmt:message key="label.recordFound"/>",
		searchDuration:"<fmt:message key="label.searchDuration"/>",
		minutes:"<fmt:message key="label.minutes"/>",
		seconds:"<fmt:message key="label.seconds"/>",
		validvalues:"<fmt:message key="label.validvalues"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		createReturnRequest:"<fmt:message key="label.createreturnrequest"/>",
		xIsRequired:"<fmt:message key="errors.required"/>",
		returnQty:"<fmt:message key="label.returnqty"/>",
		xxPositiveInteger:"<fmt:message key="label.xxpositiveinteger"/>",
		xCannotBeMoreThanY:"<fmt:message key="label.cannotbemore"/>",
		availableQty:"<fmt:message key="label.availableqty"/>"
	};
	
	with(milonic=new menuname("customerReturnRmcMenu")) {
		top="offset=2"
		style = contextStyle;
		margin=3
		aI("text=<fmt:message key="label.viewcustomerreturnrequest"/>;url=javascript:openCustomerReturnTracking();");
	}
	
	drawMenus();
	
	var config = [
		{
			columnId:"permission"
		},
		{
			columnId:"mrLine",
			columnName:'<fmt:message key="label.mrline"/>',
			width:8,
			align:'center'
		},
		{
			columnId:"inventoryGroupName",
			columnName:'<fmt:message key="label.inventorygroup"/>',
			width:8,
			align:'center'
		},
		{
			columnId:"companyName",
			columnName:'<fmt:message key="label.company"/>',
			width:8,
			align:'center'
		},
		{
			columnId:"receiptId",
			columnName:'<fmt:message key="label.receiptid"/>',
			width:8,
			align:'center'
		},
		{
			columnId:"lotStatus",
			columnName:'<fmt:message key="label.lotstatus"/>',
			width:8,
			align:'center'
		},
		{
			columnId:"mfgLot",
			columnName:'<fmt:message key="label.mfglot"/>',
			width:8,
			align:'center'
		},
		{
			columnId:"expireDate",
			columnName:'<fmt:message key="label.expiredate"/>',
			width:10,
			align:'center'
		},
		{
			columnId:"itemId",
			columnName:'<fmt:message key="label.item"/>',
			width:8,
			align:'center'
		},
		{
			columnId:"catPartNo",
			columnName:'<fmt:message key="label.catpartno"/>',
			width:10,
			align:'center'
		},
		{
			columnId:"totalShipped",
			columnName:'<fmt:message key="label.quantity"/>',
			attachHeader:'<fmt:message key="label.shipped"/>',
			width:8,
			align:'center'
		},
		{
			columnId:"totalReturned",
			columnName:'#cspan',
			attachHeader:'<fmt:message key="label.returned"/>',
			width:10,
			align:'center'
		},
		{
			columnId:"totalPendingReturn",
			columnName:'#cspan',
			attachHeader:'<fmt:message key="label.pendingreturn"/>',
			width:10,
			align:'center'
		},
		{
			columnId:"totalAvailable",
			columnName:'#cspan',
			attachHeader:'<fmt:message key="label.availabletorequest"/>',
			width:10,
			align:'center'
		},
		{
			columnId:"prNumber"
		},
		{
			columnId:"lineItem"
		},
		{
			columnId:"companyId"
		},
		{
			columnId:"sourceHub"
		},
		{
			columnId:"inventoryGroup"
		},
		{
			columnId:"prTotalAvailable"
		}
	];
	
	var gridConfig = {
		divName: 'mrIssueReceiptDetailViewBean',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
		beanData: 'jsonMainData',			<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
		beanGrid: 'beanGrid',				<%--  variable to put the grid object in for later use --%>
		config: 'config',					<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
		onRowSelect: selectRow,
		onRightClick: selectRow,			<%--  a javascript function to be called on right click with rowId & cellId as args --%>
		submitDefault: true,
		rowSpan: true,						<%--  this page has rowSpan > 1 or not. --%>
		noSmartRender: false
	};
	
	//set up the rows to be merged, and the variables needed
	var rowSpanCols = getRowSpanColsArr("mrLine, inventoryGroupName, companyName");
	var lineMap = new Array();
	var lineMap3 = new Array();
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid();">
	<tcmis:form action="/customerreturnsresults.do" onsubmit="return submitFrameOnlyOnce();">
		<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
		So this is just used to feed the pop-up in the main page.
		Similar divs would have to be built to show any other messages in a pop-up.-->
		<!-- Error Messages Begins -->
		<div id="errorMessagesAreaBody" style="display:none;">
			<c:if test="${not empty tcmISError}">
				${tcmISError}<br/>
			</c:if>
			<c:forEach items="${tcmISErrors}" varStatus="status">
				${status.current}<br/>
			</c:forEach>
		</div>
		
		<script type="text/javascript">
			/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
			showErrorMessage = false;
			<c:if test="${not empty tcmISError or not empty tcmISErrors}"> 
				showErrorMessage = true;
			</c:if>
		</script>
		<!-- Error Messages Ends -->
		
		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<!-- Search results start -->
				<c:choose>
					<c:when test="${empty mrIssueReceiptDetailViewBeanColl}">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
							<tr>
								<td width="100%">
									<fmt:message key="main.nodatafound"/>
								</td>
							</tr>
						</table>
					</c:when>
					<c:otherwise>
						<!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
						<div id="mrIssueReceiptDetailViewBean" style="width:100%;height:600px;" style="display: none;"></div>
						<script type="text/javascript">
							/*This is to keep track of whether to show any update links.
							If the user has any update permisions for any row then we show update links.*/
							// set up bin array .
							var jsonMainData = {
								rows:[
									<c:forEach var="receipt" items="${mrIssueReceiptDetailViewBeanColl}" varStatus="status">
										<c:if test="${status.index > 0}">,</c:if>
										{
											id:${status.count},
											data:[
												'Y',
												'${receipt.prNumber}-${receipt.lineItem}',
												'<tcmis:jsReplace value="${receipt.inventoryGroupName}" processMultiLines="true"/>',
												'<tcmis:jsReplace value="${receipt.companyName}" processMultiLines="true"/>',
												'${receipt.receiptId}',
												'<tcmis:jsReplace value="${receipt.lotStatus}" processMultiLines="true"/>',
												'<tcmis:jsReplace value="${receipt.mfgLot}" processMultiLines="true"/>',
												'<fmt:formatDate value="${receipt.expireDate}" pattern="${dateFormatPattern}"/>',
												'${receipt.itemId}',
												'<tcmis:jsReplace value="${receipt.catPartNo}" processMultiLines="true"/>',
												'${receipt.totalShipped}',
												'${receipt.totalReturned}',
												'${receipt.totalPendingReturn}',
												'${receipt.totalAvailable}',
												'${receipt.prNumber}',
												'${receipt.lineItem}',
												'<tcmis:jsReplace value="${receipt.companyId}"/>',
												'<tcmis:jsReplace value="${receipt.sourceHub}"/>',
												'<tcmis:jsReplace value="${receipt.inventoryGroup}"/>',
												''
											]
										}
									</c:forEach>
								]
							};
							
					    	showUpdateLinks = true;
					    	
		                	<c:set var="prevKey" value=''/>
							<c:set var="dataCount" value='0'/>
							<c:forEach var="bean" items="${mrIssueReceiptDetailViewBeanColl}" varStatus="status">
								<c:set var="curKey" value='${bean.prNumber}-${bean.lineItem}'/>
								<c:choose>
									<c:when test="${curKey != prevKey}">
										lineMap[${status.index}] = 1;
										<c:set var="prevKey" value='${curKey}'/>
										<c:set var="dataCount" value="${dataCount + 1}"/>
										<c:set var="parent" value="${status.index}"/>
									</c:when>
									<c:otherwise>
										lineMap[${parent}]++;
									</c:otherwise>
								</c:choose>
								lineMap3[${status.index}] = ${dataCount % 2};
							</c:forEach>
						</script>
					</c:otherwise>
				</c:choose>
				<!-- Search results end -->
				
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<%-- Retrieve all the Search Criteria here for re-search after update--%>
					<tcmis:setHiddenFields beanName="searchInput"/>
				</div>
				<!-- Hidden elements end -->
				
				<%-- result count and time --%>
				<div id="footer" class="messageBar"></div>
			</div>
			<!-- backGroundContent -->
		</div>
		<!-- close of interface -->
		
		<div id="inWindowPopupDiv" style="display: none;">
			<table id="inWindowPopup" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<div class="roundcont filterContainer">
							<div class="roundright">
								<div class="roundtop">
									<div class="roundtopright">
										<img src="/images/rndBoxes/borderTL_filter.gif" alt=""
											width="15" height="10" class="corner_filter" style="display: none" />
									</div>
								</div>
								<div class="roundContent">
									<!-- Insert all the search option within this div -->
									<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
										<tr>
											<td nowrap="nowrap" class="optionTitleBoldLeft" colspan="4">
												<fmt:message key="msg.pleaseselectreturntype" />:
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap" colspan="4">
												<input name="returnType" id="returnTypeCO" value="CO" checked="checked"
													type="radio" class="radioBtns" /><fmt:message key="label.customerowned"/>
											</td>
										</tr>
										<tr>
											<td>
												<input name="returnType" id="returnTypeCR" value="CR"
													type="radio" class="radioBtns" /><fmt:message key="label.forcredit"/>
											</td>
										</tr>
										<tr>
											<td>
												<input name="Ok" id="Ok" type="button" value="<fmt:message key="label.ok"/>"
													onclick="openCmsCustomerReturnRequestPage()" class="inputBtns"
													onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"/>
												<input name="cancel" id="cancel" type="button" value="<fmt:message key="label.cancel"/>"
													onclick="closeInWindowPopup()" class="inputBtns"
													onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"/>
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
		</div>
	</tcmis:form>
</body>
</html:html>
