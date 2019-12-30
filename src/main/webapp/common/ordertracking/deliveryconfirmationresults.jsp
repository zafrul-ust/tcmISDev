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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
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

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/ordertracking/deliveryconfirmationresults.js"></script>

	<!-- These are for the Grid, uncomment if you are going to use the grid -->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
	
	<%--This has the custom cells we built, hcal - the internationalized calendar which we use
	    hlink- this is for any links you want tp provide in the grid, the URL/function to call
	    can be attached based on a even (rowselect etc)
	--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="label.catalogitemsynonymmain"/>
</title>

<script language="JavaScript" type="text/javascript">
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData={
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		recordFound:"<fmt:message key="label.recordFound"/>",
		searchDuration:"<fmt:message key="label.searchDuration"/>",
		minutes:"<fmt:message key="label.minutes"/>",
		seconds:"<fmt:message key="label.seconds"/>",
		validvalues:"<fmt:message key="label.validvalues"/>",
		deliveredDate:"<fmt:message key="label.delivereddate"/>",
		maximum2000:"<fmt:message key="label.maximum2000"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"
	};
	
	<c:set var="isFeatureReleased" value="${tcmis:isFeatureReleased(personnelBean,'DisplayChargeNoOwnerSeqment', 'ALL')}"/>
	<c:if test="${isFeatureReleased == true}">
		var rowSpanMap = new Array();
		var rowSpanClassMap = new Array();
		var rowSpanLvl2Map = new Array();
		<c:if test="${param.showConfirmed == 'Y'}"> 
			var rowSpanCols = [1,2,3,4,5,6,14,15,16,17];
			var rowSpanLvl2Cols = [7,8,9,10,11,12,13];
		</c:if>
		<c:if test="${param.showConfirmed != 'Y'}"> 
			var rowSpanCols = [1,2,3,4,5,6,14,15,16];
			var rowSpanLvl2Cols = [7,8,9,10,11,12,13];
		</c:if>
	</c:if>
	
	var config = [
		{
			columnId:"permission"
		},
		{
			columnId:"shipmentId",
			columnName:'<fmt:message key="label.shipmentid"/>',
			tooltip:"Y",
			width:6
		},
		{
			columnId:"mrLine",
			columnName:'<fmt:message key="label.mrline"/>',
			tooltip:"Y",
			width:7
		},
		{
			columnId:"applicationDesc",
			columnName:'<fmt:message key="label.workarea"/>',
			tooltip:"Y",
			width:7
		},
		{  
			columnId:"confirm",
			columnName:'<fmt:message key="label.ok"/><br><input type="checkbox" value="" onClick="return checkAll(\'confirm\');" name="chkAllConfirm" id="chkAllConfirm">',
			align:"center",
			width:4,
			type:'hchstatus'
		},
		{
			columnId:"partNumber",
			columnName:'<fmt:message key="label.partnumber"/>',
			tooltip:"Y",
			width:13
		},
		{
			columnId:"partDescription",
			columnName:'<fmt:message key="label.description"/>',
			tooltip:"Y",
			width:15
		},
		{
			columnId:"quantityShipped",
			columnName:'<fmt:message key="label.qtyshipped"/>',
			align:"center",
			width:5
		},
		{
			columnId:"receiptId"
			<c:if test="${isFeatureReleased == true}">
				,
				columnName:'<fmt:message key="label.receipt"/>',
				width:8
			</c:if>
		},
		{
			columnId:"mfgLot"
			<c:if test="${isFeatureReleased == true}">
				,
				columnName:'<fmt:message key="label.lot"/>',
				width:8
			</c:if>
		},
		{
			columnId:"ownerSegmentId"
			<c:if test="${isFeatureReleased == true}">
				,
				columnName:'<fmt:message key="label.owner"/>',
				width:8
			</c:if>
		},
		{
			columnId:"programId"
			<c:if test="${isFeatureReleased == true}">
				,
				columnName:'<fmt:message key="label.program"/>',
				width:8
			</c:if>
		},
		{
			columnId:"orignialReceiptId"
			<c:if test="${isFeatureReleased == true}">
				,
				columnName:'<fmt:message key="label.trace"/>',
				width:8
			</c:if>
		},
		{
			columnId:"trackingNumber"
			<c:if test="${isFeatureReleased == true}">
				,
				columnName:'<fmt:message key="label.trackingnumber"/>',
				width:8
			</c:if>
		},
		{
			columnId:"shipDate",
			columnName:'<fmt:message key="label.shipdate"/>',
			width:8
		},
		{
			columnId:"confirmationDate",
			columnName:'<fmt:message key="label.confirmdate"/>',
			type:'hcal',
			width:8
		},
		<c:if test="${param.showConfirmed == 'Y'}">
			{
				columnId:"confirmedByName",
				columnName:'<fmt:message key="label.confirmedby"/>',
				width:8
			},
		</c:if>
		{
			columnId:"comments",
			columnName:'<fmt:message key="label.comments"/>',
			type:"txt",
			width:20
		},
		{ 
			columnId:"isConfirmed"
		},
		{ 
			columnId:"prNumber"
		},
		{
			columnId:"lineItem"
		},
		{ 
			columnId:"facilityId"
		},
		{ 
			columnId:"application"
		},
		{
			columnId:"companyId"
		},
		{
			columnId:"totalQuantity"
		}
	];
	
	<%-- Define the grid options--%>
	var gridConfig = {
		divName: 'deliveryConfirmViewBean',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
		beanData: 'jsonMainData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
		beanGrid: 'beanGrid',		<%--  variable to put the grid object in for later use --%>
		config: config,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
		submitDefault:true, // the fields in grid are defaulted to be submitted or not.
		noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
		variableHeight:false,
		<c:choose>
			<c:when test="${isFeatureReleased == true}">
				rowSpan:true, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
				selectChild: 1
			</c:when>
			<c:otherwise>
				rowSpan:false // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
			</c:otherwise>
		</c:choose>
	};
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid();">
	<tcmis:form action="/deliveryconfirmresults.do" onsubmit="return submitFrameOnlyOnce();">
		<script type="text/javascript">
			<!-- Error Messages Begins -->
			/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
			<c:choose>
				<c:when test="${empty tcmISErrors and empty tcmISError}">
					showErrorMessage = false;
				</c:when>
				<c:otherwise>
					showErrorMessage = true;
				</c:otherwise>
			</c:choose>
			parent.messagesData.errors = "<fmt:message key="label.errors"/>";
			<c:if test="${not empty MaxDataFilled}">
				showErrorMessage = true;
				parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
			</c:if>
		</script>
		<!-- Error Messages Ends -->
		
		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<div id="deliveryConfirmViewBean" style="width:100%;height:500px;" style="display: none;"></div>
				<c:set var="dataCount" value='${0}'/>
				<c:if test="${deliveryConfirmColl != null}">
					<script type="text/javascript">
						<c:set var="nonFeatureReleaseCount" value='0' />
						<c:if test="${!empty deliveryConfirmColl}">
							var jsonMainData = new Array();
							var jsonMainData = {
								rows:[
									<c:forEach var="bean" items="${deliveryConfirmColl}" varStatus="status">
										<c:set var="readonly" value='N'/>
										<c:set var="showUpdateLink" value='N'/>
										<tcmis:facilityPermission indicator="true" userGroupId="deliveryConfirmation" facilityId="${bean.facilityId}">
											<c:if test="${bean.confirmedBy == null}">
												<c:set var="readonly" value='Y'/>
												<c:set var="showUpdateLink" value='Y'/>
											</c:if>
										</tcmis:facilityPermission>
										<fmt:formatDate var="fmtShipDate" value="${status.current.shipDate}" pattern="${dateFormatPattern}"/>
										<c:if test="${status.current.confirmationDate != null}">
											<fmt:formatDate var="fmtConfirmationDate" value="${status.current.confirmationDate}" pattern="${dateTimeFormatPattern}"/>
										</c:if>
										
										<c:choose>
											<c:when test="${isFeatureReleased == true}">
												{
													id:${status.index +1},
													data:[
														'${readonly}',
														'${bean.shipmentId}',
													 	'${bean.prNumber}-${bean.lineItem}',
													 	'<tcmis:jsReplace value="${bean.applicationDesc}" processMultiLines="true"/>',
														false,
														'<tcmis:jsReplace value="${bean.partNumber}" processMultiLines="true"/>',
														'<tcmis:jsReplace value="${bean.partDescription}" processMultiLines="true"/>',
														'${bean.quantityShipped}',
														'${bean.receiptId}',
														'<tcmis:jsReplace value="${bean.mfgLot}" processMultiLines="false"/>',
														'${bean.ownerSegmentId}',
														'${bean.programId}',
														'${bean.originalReceiptId}',
														'<tcmis:jsReplace value="${bean.trackingNumber}" processMultiLines="false"/>',
														'${fmtShipDate}',
														<c:choose>
															<c:when test="${status.current.confirmationDate != null}">
																'${fmtConfirmationDate}',
															</c:when>
															<c:otherwise>
																'<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>',
															</c:otherwise>
														</c:choose>
														<c:if test="${param.showConfirmed == 'Y'}">
															'<tcmis:jsReplace value="${bean.confirmedByName}" processMultiLines="false"/>',
														</c:if>
														'<tcmis:jsReplace value="${bean.comments}" processMultiLines="true"/>',
														
														<%-- hidden columns --%>
														'N',
														'${bean.prNumber}',
														'${bean.lineItem}',
														'${bean.facilityId}',
														'${bean.application}',
														'${bean.companyId}',
														'${bean.totalQuantity}'
													]
												}
												<c:if test="${!status.last}">,</c:if>
												<c:set var="dataCount" value='${dataCount+1}'/>
											</c:when>
											<c:otherwise>
												<c:if test="${bean.totalQuantity != null && bean.totalQuantity != ''}">
													<c:if test="${nonFeatureReleaseCount > 0}">,</c:if>
													{
														id:${nonFeatureReleaseCount +1},
														data:[
															'${readonly}',
															'${bean.shipmentId}',
															'${bean.prNumber}-${bean.lineItem}',
														 	'<tcmis:jsReplace value="${bean.applicationDesc}" processMultiLines="true"/>',
															false,
															'<tcmis:jsReplace value="${bean.partNumber}" processMultiLines="true"/>',
															'<tcmis:jsReplace value="${bean.partDescription}" processMultiLines="true"/>',
															'${bean.totalQuantity}',
															'${bean.receiptId}',
															'<tcmis:jsReplace value="${bean.mfgLot}" processMultiLines="false"/>',
															'${bean.ownerSegmentId}',
															'${bean.programId}',
															'${bean.originalReceiptId}',
															'<tcmis:jsReplace value="${bean.trackingNumber}" processMultiLines="false"/>',
															'${fmtShipDate}',
															<c:choose>
																<c:when test="${status.current.confirmationDate != null}">
																	'${fmtConfirmationDate}',
																</c:when>
																<c:otherwise>
																	'<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>',
																</c:otherwise>
															</c:choose>
															<c:if test="${param.showConfirmed == 'Y'}">
																'<tcmis:jsReplace value="${bean.confirmedByName}" processMultiLines="false"/>',
															</c:if>
															'<tcmis:jsReplace value="${bean.comments}" processMultiLines="true"/>',
															
															<%-- hidden columns --%>
															'N',
															'${bean.prNumber}',
															'${bean.lineItem}',
															'${bean.facilityId}',
															'${bean.application}',
															'${bean.companyId}',
															'${bean.totalQuantity}'
														]
													}
													<c:set var="dataCount" value='${dataCount+1}'/>
													<c:set var="nonFeatureReleaseCount" value='${nonFeatureReleaseCount + 1}' />
												</c:if>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								]
							};
						</c:if>
					</script>
				</c:if>
				
				<!-- If the collection is empty say no data found -->
				<c:if test="${empty deliveryConfirmColl}" >
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
						<tr>
							<td width="100%">
								<fmt:message key="main.nodatafound"/>
							</td>
						</tr>
					</table>
				</c:if>
				
				<!-- Error Messages Begins -->
				<div id="errorMessagesAreaBody" style="display:none;">
					${tcmISError}<br/>
					<c:forEach items="${tcmISErrors}" varStatus="status">
						${status.current}<br/>
					</c:forEach>
					<c:if test="${not empty MaxDataFilled}">
						<fmt:message key="label.maxdata"> 
							<fmt:param value="${dataCount}"/>
						</fmt:message>
					</c:if>
				</div>
				
				<script type="text/javascript">
					<%-- determining rowspan --%>
					<c:if test="${isFeatureReleased == true}">
						<c:set var="rowSpanCount" value='0' />
						<c:forEach var="row" items="${deliveryConfirmColl}" varStatus="status">
					
								<c:set var="currentKey" value='${row.prNumber}' />
									<c:set var="currentKeyLvl2" value='${row.receiptId}' />
									<c:choose>
										<c:when test="${status.first}">
											<c:set var="rowSpanStart" value='0' />
											<c:set var="rowSpanLvl2Start" value='0' />
											<c:set var="rowSpanCount" value='1' />
											rowSpanMap[0] = 1;
											rowSpanLvl2Map[0] = 1;
											rowSpanClassMap[0] = 1;
										</c:when>
										<c:when test="${currentKey != null && currentKey == previousKey}">
											rowSpanMap[${rowSpanStart}]++;
											rowSpanMap[${status.index}] = 0;
											rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
											<c:choose>
												<c:when test="${currentKeyLvl2 == previousKeyLvl2}">
													rowSpanLvl2Map[${rowSpanLvl2Start}]++;
													rowSpanLvl2Map[${status.index}] = 0;
												</c:when>
												<c:otherwise>
													<c:set var="rowSpanLvl2Start" value='${status.index}' />
													rowSpanLvl2Map[${status.index}] = 1;
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
											<c:set var="rowSpanStart" value='${status.index}' />
											<c:set var="rowSpanLvl2Start" value='${status.index}' />
											rowSpanMap[${status.index}] = 1;
											rowSpanLvl2Map[${status.index}] = 1;
											rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
										</c:otherwise>
									</c:choose>
									<c:set var="previousKey" value='${currentKey}' />
									<c:set var="previousKeyLvl2" value='${currentKeyLvl2}' />
						</c:forEach>
					</c:if>
				</script>
				<!-- Search results end -->
				
				<div id="hiddenElements" style="display: none;">
					<input type="hidden" name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>"/>
					<input type="hidden" name="uAction" id="uAction" value=""/>
					<%-- Retrieve all the Search Criteria here for re-search after update--%>
            		<tcmis:setHiddenFields beanName="searchInput"/>
					<input type="hidden" name="blockBefore_confirmationDate" id="blockBefore_confirmationDate" value=""/>
					<input type="hidden" name="blockAfter_confirmationDate" id="blockAfter_confirmationDate" value="<tcmis:getDateTag numberOfDaysFromToday="1" datePattern="${dateFormatPattern}"/>"/>
					<input type="hidden" name="blockBeforeExclude_confirmationDate" id="blockBeforeExclude_confirmationDate" value=""/>
					<input type="hidden" name="blockAfterExclude_confirmationDate" id="blockAfterExclude_confirmationDate" value=""/>
					<input type="hidden" name="inDefinite_confirmationDate" id="inDefinite_confirmationDate" value=""/>
				</div>
			</div>
			<!-- close of backGroundContent -->
		</div>
		<!-- close of interface -->
		
		<c:if test="${showUpdateLink == 'Y'}">
			<script type="text/javascript">
				showUpdateLinks = true;
			</script>
		</c:if>
	</tcmis:form>
</body>
</html:html>