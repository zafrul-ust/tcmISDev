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
	
	<!-- For Calendar support -->
	<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
	<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
	<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
	
	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/distribution/orderentrylookupresults.js"></script>
	
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
	<%--This has the custom cells we built, hcal - the internationalized calendar which we use
	hlink- this is for any links you want tp provide in the grid, the URL/function to call
	can be attached based on a even (rowselect etc)--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
	
	<title>
		<fmt:message key="orderentrylookup.title"/>
	</title>
	
	<tcmis:inventoryGroupPermission indicator="true" userGroupId="GenerateOrders">
		<c:set var="showUpdateLink" value='Y'/>
	</tcmis:inventoryGroupPermission>
	<c:set var="checkBoxPermission" value='N'/>
	<c:set var="showCheckBox" value='N'/>
	<c:if test="${showUpdateLink == 'Y'}">
		<script type="text/javascript">
			showUpdateLinks = true;
		</script>
	</c:if>
	
	<script language="JavaScript" type="text/javascript">
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData = {
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			recordFound:"<fmt:message key="label.recordFound"/>",
			searchDuration:"<fmt:message key="label.searchDuration"/>",
			minutes:"<fmt:message key="label.minutes"/>",
			seconds:"<fmt:message key="label.seconds"/>",
			customerreturnrequest:"<fmt:message key="customerreturnrequest.title"/>",
			mrallocationreport:"<fmt:message key="mrallocationreport.label.title"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			open:"<fmt:message key="label.open"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"
		};

		with(milonic=new menuname("openscratchpad")){
			top="offset=2"
			style = contextStyle;
			margin=3
			aI("text=<fmt:message key="label.open"/>;url=javascript:openScratchPadsOnRightClick();");
		}

		drawMenus();

		<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
		var config = [
			{
				columnId:"prNumber",
				columnName:'<fmt:message key="label.ourreferrence"/>',
				width:6
			},
			{
				columnId:"date",
				columnName:'<fmt:message key="label.date"/>',
				sorting:'int',
				hiddenSortingColumn:"hiddenDate",
				width:7
			},
			{
				columnId:"type",
				columnName:'<fmt:message key="label.type"/>',
				width:4
			},
			{
				columnId:"customerName",
				columnName:'<fmt:message key="label.customer"/>',
				tooltip:"Y",
				width:15
			},
			{
				columnId:"customerref",
				columnName:'<fmt:message key="label.customerreference"/>',
				tooltip:"Y"
			},
			{
				columnId:"shipto",
				columnName:'<fmt:message key="label.shipto"/>',
				tooltip:"Y",
				width:10
			},
			{
				columnId:"personnelName",
				columnName:'<fmt:message key="label.enteredby"/>',
				tooltip:"Y",
				width:5
			},
			{
				columnId:"status",
				columnName:'<fmt:message key="label.status"/>'
			},
			{
				columnId:"contact",
				columnName:'<fmt:message key="label.contact"/>'
			},
			{
				columnId:"nooflines",
				columnName:'<fmt:message key="label.numberoflines"/>',
				align:'right',
				width:4
			},
			{
				columnId:"opsentity",
				columnName:'<fmt:message key="label.operatingentity"/>'
			},
			{
				columnId:"hubName",
				columnName:'<fmt:message key="label.hub"/>'
			},
			{
				columnId:"inventoryGroup",
				columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
				tooltip:"Y"
			},
			{
				columnId:"hiddenDate"
			},
			{
				columnId:"customerid"
			}
		];
	</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">
	<tcmis:form action="/orderentrylookupresults.do" onsubmit="return submitFrameOnlyOnce();">
		<div id="errorMessagesAreaBody" style="display:none;">
			<html:errors/>
			${tcmISError}
			<c:forEach items="${tcmISErrors}" varStatus="status">
				${status.current}<br/>
			</c:forEach>
		</div>

		<script type="text/javascript">
			<c:choose>
				<c:when test="${empty tcmISErrors and empty tcmISError}">
					showErrorMessage = false;
				</c:when>
				<c:otherwise>
					parent.messagesData.errors = "<fmt:message key="label.errors"/>";
					showErrorMessage = true;
				</c:otherwise>
			</c:choose>
		</script>
		<!-- Error Messages Ends -->

		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<div id="orderEntityViewBean" style="width:100%;height:400px;" style="display: none;"></div>
				<c:if test="${orderEntityViewBeanColl != null}">
					<script type="text/javascript">
						<c:set var="dataCount" value='${0}'/>
						<c:set var="creditHoldCount" value='${0}'/>
						<c:if test="${!empty orderEntityViewBeanColl}" >
							var jsonMainData = new Array();
							var jsonMainData = {
								rows:[
									<c:forEach var="orderEntityViewBean" items="${orderEntityViewBeanColl}" varStatus="status">
										<c:if test="${status.index > 0}">,</c:if>
										<tcmis:jsReplace var="shipToAddressLine1" value="${status.current.shipToAddressLine1}" processMultiLines="true" />
										<tcmis:jsReplace var="shipToAddressLine2" value="${status.current.shipToAddressLine2}" processMultiLines="true" />
										<tcmis:jsReplace var="shipToAddressLine3" value="${status.current.shipToAddressLine3}" processMultiLines="true" />
										<tcmis:jsReplace var="shipToAddressLine4" value="${status.current.shipToAddressLine4}" processMultiLines="true" />
										<tcmis:jsReplace var="shipToAddressLine5" value="${status.current.shipToAddressLine5}" processMultiLines="true" />
										<tcmis:jsReplace var="poNumber" value="${status.current.poNumber}" processMultiLines="true" />
										<tcmis:jsReplace var="customerName" value="${status.current.customerName}" processMultiLines="true" />
										<tcmis:jsReplace var="submittedByName" value="${status.current.submittedByName}" processMultiLines="true" />
										<tcmis:jsReplace var="requestorName" value="${status.current.requestorName}" processMultiLines="true" />
										<fmt:formatDate var="submittedDate" value="${status.current.submittedDate}" pattern="${dateFormatPattern}"/>
										
										/*The row ID needs to start with 1 per their design.*/
										{
											id:${status.index +1},
											data:[
												'${status.current.prNumber}',
												'${submittedDate}',
												'${status.current.orderType}',
												'${customerName}',
												'${poNumber}',
												'${shipToAddressLine1} ${shipToAddressLine2} ${shipToAddressLine3} ${shipToAddressLine4} ${shipToAddressLine5}',
												'${submittedByName}',
												'${status.current.orderStatus}',
												'${requestorName}',
												'${status.current.numberOfLines}',
												'<tcmis:jsReplace value="${status.current.operatingEntityName}" />',
												'<tcmis:jsReplace value="${status.current.hubName}" />',
												'<tcmis:jsReplace value="${status.current.inventoryGroupName}" />',
												'${status.current.submittedDate.time}',
												'${status.current.customerId}'
											]
										}
										<c:set var="dataCount" value='${dataCount+1}'/>
									</c:forEach>
								]
							};
						</c:if>
					</script>
					
					<!-- If the collection is empty say no data found -->
					<c:if test="${empty orderEntityViewBeanColl}">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
							<tr>
								<td width="100%">
									<fmt:message key="main.nodatafound"/>
								</td>
							</tr>
						</table>
					</c:if>
					<!-- Search results end -->
				</c:if>
				
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
					<input name="creditHoldCount" id="creditHoldCount" value="<c:out value="${creditHoldCount}"/>" type="hidden">
					<input name="uAction" id="uAction" value="" type="hidden"/>
					<input name="minHeight" id="minHeight" type="hidden" value="100">
				</div>
				<!-- Hidden elements end -->
			</div>
			<!-- close of backGroundContent -->
		</div>
		<!-- close of interface -->
	</tcmis:form>
</body>
</html:html>