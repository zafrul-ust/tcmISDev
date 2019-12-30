<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<html:html lang="true">
<head>
	<jsp:useBean id="today" class="java.util.Date"/>
	<c:set var="javascriptDate"><fmt:message key="javascript.dateformat"/></c:set>
	
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="expires" content="-1">
	<link rel="shortcut icon" href="/images/buttons/tcmIS.ico">
	</link>
	<%@ include file="/common/locale.jsp" %>

	<!--Use this tag to get the correct CSS class.
		This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
	<tcmis:gridFontSizeCss />

	<!-- Add any other stylesheets you need for the page here -->
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>

	<%--NEW - grid resize--%>
	<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
	
	<!-- This handles which key press events are disabled on this page -->
	<script src="/js/common/disableKeys.js"></script>
	
	<!-- This handles the menu style and what happens to the right click on the whole page -->
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	
	<%@ include file="/common/rightclickmenudata.jsp" %>

	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
	<script type="text/javascript" src="/js/common/cabinet/clientcabinetsetupresults.js"></script>
	
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
	 
	<%--This is to allow copy and paste. works only in IE--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_selection.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js"></script>
	

	<c:set var="ableToSetInvGroup" value="false"/>
	<tcmis:facilityPermission indicator="true" userGroupId="cabinetSetupInventoryGroup" facilityId="${input.facilityId}">
		<c:set var="ableToSetInvGroup" value="true"/>
	</tcmis:facilityPermission>
	<script language="JavaScript" type="text/javascript">
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData= {
			alert: "<fmt:message key="label.alert"/>",
			and: "<fmt:message key="label.and"/>", 
			validvalues: "<fmt:message key="label.validvalues"/>",
			submitOnlyOnce: "<fmt:message key="label.submitOnlyOnce"/>",
			itemInteger: "<fmt:message key="error.item.integer"/>",
			recordFound: "<fmt:message key="label.recordFound"/>",
			searchDuration: "<fmt:message key="label.searchDuration"/>",
			minutes: "<fmt:message key="label.minutes"/>",
			seconds: "<fmt:message key="label.seconds"/>",
			selectCabinet : "<fmt:message key="label.pleaseselectacabinet"/>",
			generateBinLabel : "<fmt:message key="cabinetbinlabel.label.generatebinlabel"/>",
			required: "<fmt:message key="errors.required"><fmt:param><fmt:message key="label.cabinetName"/></fmt:param></fmt:message>",
		   cabinetsForWorkarea : "<fmt:message key="label.cabinetsforworkarea"/>",
			pleasewait: "<fmt:message key="label.pleasewaitmenu"/>"
	       };

		<%-- See webroot/dhtmlxgrid/codebase/dhtmlxcommon_haas.js for option explanations of config & gridconfig --%>
	       var config = [ 
			{columnId: "permission"},
			{columnId: "active", columnName:'<fmt:message key="label.active"/>', type:"hchstatus", width:4, align:"center", sort:"haasHch", onChange:toggleColor},
			<c:choose><%-- Only supply Inventory Group pulldown if more than 1 IG && user has permission --%>
				<c:when test="${fn:length(input.facilityInventoryGroups) gt 1 && ableToSetInvGroup}">
					{columnId: "inventoryGroup", columnName:'<fmt:message key="label.inventorygroup"/>', type:"hcoro", align:"center", width:15},
				</c:when>
				<c:otherwise>
					{columnId: "inventoryGroup"},
					{columnId: "inventoryGroupDisplay", columnName:'<fmt:message key="label.inventorygroup"/>', width:15},
				</c:otherwise>
			</c:choose>
			<c:choose><%-- Only supply Dock pulldown if more than 1 Dock --%>
				<c:when test="${fn:length(input.facilityDocks) gt 1}">
					{columnId: "locationId", columnName:'<fmt:message key="label.dock"/>', type:"hcoro", align:"center", width:15},
				</c:when>
				<c:otherwise>
					{columnId: "locationId"},
					{columnId: "locationIdDisplay", columnName:'<fmt:message key="label.dock"/>', width:15},
				</c:otherwise>
			</c:choose>
			{columnId: "cabinetName", columnName:'<fmt:message key="label.cabinetName"/>', type:"hed", width:16, size:30, maxLength:30},
			{columnId: "contactName", columnName:'<fmt:message key="label.contactname"/>', type:"hed", width:16, size:30, maxLength:30},
			{columnId: "contactPhone", columnName:'<fmt:message key="label.phone"/>', type:"hed", width:10, size:15, maxLength:30},
			{columnId: "contactEmail", columnName:'<fmt:message key="label.email"/>', type:"hed", width:16, size:30, maxLength:30},
			{columnId: "building", columnName:'<fmt:message key="label.building"/>', type:"hed", width:16, size:30, maxLength:30},
			{columnId: "room", columnName:'<fmt:message key="label.room"/>', type:"hed", width:16, size:30, maxLength:30},
			{columnId: "location", columnName:'<fmt:message key="label.location"/>', type:"hed", width:16, size:30, maxLength:30},
			{columnId: "customerCabinetId", columnName:'<fmt:message key="label.customercabinetid"/>', type:"hed", width:16, size:30, maxLength:30},
			{columnId: "facilityId"},
			{columnId: "companyId"},
			{columnId: "cabinetId"},
			{columnId: "deliveryPoint"},
			{columnId: "orderingApplication"},
			{columnId: "touched"}
	       ];

		var inventoryGroup = new Array();
		<c:choose>
			<c:when test="${fn:length(input.facilityInventoryGroups) gt 1 && ableToSetInvGroup}">
				inventoryGroup = [
		  		<c:forEach var="invGroup" items="${input.facilityInventoryGroups}" varStatus="status">
		  			{value:'${invGroup.inventoryGroup}', text: '<tcmis:jsReplace value="${invGroup.inventoryGroupName}"/>'}<c:if test="${!status.last}">,</c:if>
		  		</c:forEach>
		  		];
			</c:when>
			<c:otherwise>
				inventoryGroup = [
		  		<c:forEach var="invGroup" items="${input.facilityInventoryGroups}" varStatus="status">
		  			<c:if test="${invGroup.inventoryGroup == input.workArea.inventoryGroup}">
		  				{value:'${invGroup.inventoryGroup}', text: '<tcmis:jsReplace value="${invGroup.inventoryGroupName}"/>'}
		  			</c:if>
		 		</c:forEach>
		  		];
			</c:otherwise>
		</c:choose>
	       
		var locationId = new Array();
		locationId = [
		<c:forEach var="dock" items="${input.facilityDocks}" varStatus="status">
			{value:'${dock.dockLocationId}', text: '<tcmis:jsReplace value="${dock.dockDesc}"/>'}<c:if test="${!status.last}">,</c:if>
		</c:forEach>
		];

		var defaultFacilityId = '${input.workArea.facilityId}';
		var defaultInventoryGroup = '${input.workArea.inventoryGroup}';
		var defaultDockId = '${input.workArea.locationId}';
		       
		var gridConfig = {
			divName:'resultGridDiv', // the div id to contain the grid.
			beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
			beanGrid:'mygrid', // the grid's name, as in beanGrid.attachEvent...
			config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
			rowSpan:false, // true: this page has rowSpan > 1, disables smartrendering & sorting
			submitDefault:true, // the fields in grid are defaulted to be submitted or not.
			onRowSelect: rowSelected
		};

		var newCabinetIds = new Array();
		newCabinetIds = [
		<c:forEach var="cabinetBean" items="${input.newCabinetIdColl}" varStatus="status">
			{
				value:'${cabinetBean.cabinetId}',
				text: '<tcmis:jsReplace value="${cabinetBean.cabinetName}"/>'
			}
			<c:if test="${!status.last}">,</c:if>
		</c:forEach>
		];

       
		</script>
</head>
<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);myResultOnLoad();">
	<tcmis:form action="/clientcabinetsetupresults.do" onsubmit="return submitFrameOnlyOnce();">
		<script  language="JavaScript" type="text/javascript">
			<c:if test= "${!empty cabinetsCollection}" >
				var jsonMainData = {
					rows: [	<c:forEach var= "row" items= "${cabinetsCollection}" varStatus= "status" >
							{ id: ${status.index + 1},
							  <c:if test="${row.status == 'I'}">'class': 'grid_lightgray',</c:if>
							  data: ['Y',
							  	 <c:choose><c:when test="${row.status == 'A'}">true</c:when><c:otherwise>false</c:otherwise></c:choose>,
							  	 '${row.inventoryGroup}',
							  	 <c:if test="${fn:length(input.facilityInventoryGroups) le 1 || !ableToSetInvGroup}">'<tcmis:jsReplace value="${row.inventoryGroupName}" processMultiLines="true"/>',</c:if>
							  	 '${row.locationId}',
								 <c:if test="${fn:length(input.facilityDocks) le 1}">'<tcmis:jsReplace value="${row.locationId}" processMultiLines="true"/>',</c:if>
							  	 '<tcmis:jsReplace value="${row.cabinetName}" processMultiLines="true"/>',
							  	 '<tcmis:jsReplace value="${row.contactName}" processMultiLines="true"/>',
							  	 '<tcmis:jsReplace value="${row.contactPhone}" processMultiLines="true"/>',
							  	 '<tcmis:jsReplace value="${row.contactEmail}" processMultiLines="true"/>',
							  	 '<tcmis:jsReplace value="${row.building}" processMultiLines="true"/>',
							  	 '<tcmis:jsReplace value="${row.room}" processMultiLines="true"/>',
							  	 '<tcmis:jsReplace value="${row.location}" processMultiLines="true"/>',
							  	 '<tcmis:jsReplace value="${row.customerCabinetId}" processMultiLines="true"/>',
							  	 '${row.facilityId}',
							  	 '${row.companyId}',
							  	 '${row.cabinetId}',
							  	 '${row.deliveryPoint}',
							  	 '${row.orderingApplication}',
							  	 false
							  	] 
							}<c:if test= "${!status.last}" >,</c:if>
						</c:forEach>
					]
				};
			</c:if>
		</script>		
			
		
		<script type="text/javascript">
			showUpdateLinks = true;
		</script>
		
		<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
			So this is just used to feed the YUI pop-up in the main page.
			Similar divs would have to be built to show any other messages in a pop-up.-->
		<!-- Error Messages Begins -->
		<div id="errorMessagesAreaBody" style="display:none;">
			${tcmisError}
		</div>
		<script type="text/javascript">
			<!--
			<c:choose> 
				<c:when test= "${empty tcmisError}" >
					showErrorMessage = false;
				</c:when>
				<c:otherwise>
					showErrorMessage = true;
				</c:otherwise> 
			</c:choose>//-->
		</script>
		<!-- Error Messages Ends -->

		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<div id="resultGridDiv" style="width:100%;height:400px;" style="display: none;" >
				</div>
				<!-- If the collection is empty say no data found -->
				<c:if test="${empty cabinetsCollection}" >
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
						<tr>
							<td width="100%">
								<fmt:message key="main.nodatafound" />
							</td>
						</tr>
					</table>
				</c:if>
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<tcmis:setHiddenFields beanName="input"/>
				</div>
				<!-- Hidden elements end -->
			</div>
			<!-- close of backGroundContent -->
		</div>
		<!-- close of interface -->
	</tcmis:form>
</html:html>	

