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
	<script type="text/javascript" src="/js/common/cabinet/clientworkareasetupresults.js"></script>
	
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
		<!-- Popup window support -->
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>	
	 
	<%--This is to allow copy and paste. works only in IE--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_selection.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js"></script>
	

	<c:set var="ableToSetInvGroup" value="false"/>
	<tcmis:facilityPermission indicator="true" userGroupId="InventoryGroupMgmt" companyId="${param.companyId}" facilityId="${input.facilityId}">
		<c:set var="ableToSetInvGroup" value="true"/>
	</tcmis:facilityPermission>

    <c:set var="directedChargeAssignment" value="N"/>
    <tcmis:facilityPermission indicator="true" userGroupId="DirectedChargeAssignment" companyId="${param.companyId}" facilityId="${input.facilityId}">
        <c:set var="directedChargeAssignment" value="Y"/>
        <script type="text/javascript">
            <!--
            directedChargeAssignment = true;
            //-->
        </script>
    </tcmis:facilityPermission>


    <c:set var="hasUpdatePerm" value="false"/>
	<c:set var="updateFlag" value="N"/>
	<script type="text/javascript">
		<!-- 
		<tcmis:facilityPermission indicator="true" userGroupId="CreateEditWorkArea" companyId="${param.companyId}" facilityId="${input.facilityId}">
			<c:set var="hasUpdatePerm" value="true"/> <c:set var="updateFlag" value="Y"/>
			showUpdateLinks = true;
		</tcmis:facilityPermission>
		<tcmis:facilityPermission indicator="true" userGroupId="ChargeNumberSetup" companyId="${param.companyId}" facilityId="${input.facilityId}">
			chargeNumberSetup = true;
		</tcmis:facilityPermission>
 		//-->
	</script>

        <title>
		<fmt:message key="clientCabinetDefinition"/>
		</title>


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
			descRequired: "<fmt:message key="errors.required"><fmt:param><fmt:message key="label.workarea"/></fmt:param></fmt:message>",
			dockRequired: "<fmt:message key="errors.required"><fmt:param><fmt:message key="label.dock"/></fmt:param></fmt:message>",
			deliveryRequired: "<fmt:message key="errors.required"><fmt:param><fmt:message key="label.deliverypoint"/></fmt:param></fmt:message>",
		  	cabinetsForWorkarea : "<fmt:message key="label.workareasforgroup"/>",
			waitingforinput:"<fmt:message key="label.waitingforinputfrom"/>",
			deliverypoint:"<fmt:message key="label.deliverypoint"/>",
			workareagroup:"<fmt:message key="label.workareagroup"/>",
			area:"<fmt:message key="label.area"/>",
			directedCharge:"<fmt:message key="label.directedCharge"/>",
			invalidChargeNumbers:"<fmt:message key="label.invalidchargenumbers"/>",
			invalidChargeNumberAddTolist:"<fmt:message key="label.invalidchargenumberaddtolist"/>",
			pleasewait: "<fmt:message key="label.pleasewaitmenu"/>",
			integerErr:"<fmt:message key="label.positivenumber"/>",
			pullWithinDaysToExpiration:"<fmt:message key="label.pulledwithindays"/>",
			approvalCode:"<fmt:message key="label.approvalcode"/>",
			department:"<fmt:message key="label.department"/>",
			daysBetweenScan:"<fmt:message key="label.daysbetweenscan"/>",
			lookupForMoreDetails:"<fmt:message key="msg.lookupformoredetails"/>"
	       };

		var isAllowSplitKitsColView = '${isAllowSplitKitsColView}';
		var isUseCodeRequired = '${isUseCodeRequired}';
		var showChargeTypeDefault = '${showChargeTypeDefault}';  
		var showHmrbFeatures = ${showHmrbFeatures};
		<c:set var="showIncludeExpired" value="${tcmis:isCompanyFeatureReleased(personnelBean,'IncludeExpired', param.facilityId, param.companyId)}"/>
   		var showIncludeExpired = ${showIncludeExpired};
   		<c:set var="showAlternateContact" value="${tcmis:isCompanyFeatureReleased(personnelBean,'ShowAlternateContact', param.facilityId, param.companyId)}"/>
   		var showAlternateContact = ${showAlternateContact};
		<c:set var="showLocSection" value="${tcmis:isCompanyFeatureReleased(personnelBean,'ShowLocSection', param.facilityId, param.companyId)}"/>
   		var showLocSection = ${showLocSection};
		<c:set var="showEmissionPoint" value="${tcmis:isCompanyFeatureReleased(personnelBean,'ShowEmissionPoint', param.facilityId, param.companyId)}"/>
   		var showEmissionPoint = ${showEmissionPoint};
		<c:set var="showReportUsage" value="${tcmis:isCompanyFeatureReleased(personnelBean,'ShowReportUsage', param.facilityId, param.companyId)}"/>
   		var showReportUsage = ${showReportUsage};
		<c:set var="showControlledColInWADefiniton" value="${tcmis:isCompanyFeatureReleased(personnelBean,'ShowControlledColInWADefiniton', param.facilityId, param.companyId)}"/>
   		var showControlledColInWADefiniton = ${showControlledColInWADefiniton};
        <c:set var="ShowHETColumns" value="${tcmis:isCompanyFeatureReleased(personnelBean,'ShowHETColumns', param.facilityId, param.companyId)}"/>
   		var ShowHETColumns = ${ShowHETColumns};
   		<c:set var="showVocZone" value="${tcmis:isCompanyFeatureReleased(personnelBean,'ShowVocZone', param.facilityId, param.companyId)}"/>
        var showVocZone = ${showVocZone};
        <c:set var="showFlammabilityControlZone" value="${tcmis:isCompanyFeatureReleased(personnelBean,'ShowFlammabilityControlZone', param.facilityId, param.companyId)}"/>
        var showFlammabilityControlZone = ${showFlammabilityControlZone};
        <c:set var="showReportInventory" value="${tcmis:isCompanyFeatureReleased(personnelBean,'ShowReportInventory', param.facilityId, param.companyId)}"/>
        var showReportInventory = ${showReportInventory};
        <c:set var="showEmissionPoints" value="${tcmis:isCompanyFeatureReleased(personnelBean,'ShowEmissionPoints', param.facilityId, param.companyId)}"/>
        var showEmissionPoints = ${showEmissionPoints};
        <%-- See webroot/dhtmlxgrid/codebase/dhtmlxcommon_haas.js for option explanations of config & gridconfig --%>
	       var config = [ 
			{columnId: "permission"},
			{columnId: "active", columnName:'<fmt:message key="label.active"/>', type:"hchstatus", width:4, align:"center", sort:"haasHch", onChange:toggleColor},
			{columnId: "applicationDesc", columnName:'<fmt:message key="label.workarea"/>', type:"hed", width:16, size:30, maxLength:30, onChange:valueChanged},
			{columnId: "deptId"},
			{columnId: "deptName", columnName:'<fmt:message key="label.department"/>', attachHeader:'<fmt:message key="label.name"/>', width:10, submit:false},
			{columnId: "departmentLookup"<c:if test="${hasUpdatePerm}">, columnName:'#cspan', attachHeader:'<fmt:message key="label.lookup"/>', width:4, align:'center', submit:false</c:if>},
			{columnId: "dropShip", columnName:'<fmt:message key="label.dropship"/>', type:"hchstatus", width:3, align:"center", sort:"haasHch"},
			{columnId: "offsite", columnName:'<fmt:message key="label.offsite"/>', type:"hchstatus", width:3, align:"center", sort:"haasHch"},
			{columnId: "workAreaGroupDesc", columnName:'<fmt:message key="label.workareagroup"/>', <c:if test="${hasUpdatePerm}">attachHeader:'<fmt:message key="label.group"/>',</c:if> width:10, submit:false},
			{columnId: "workAreaGroupLookup"<c:if test="${hasUpdatePerm}">, columnName:'#cspan', attachHeader:'<fmt:message key="label.lookup"/>', width:4, align:'center', submit:false</c:if>},
			{columnId: "orderingMethod", columnName:'<fmt:message key="label.orderingmethodforstockedparts"/>', type:"hcoro", align:"center", width:13, onChange:orderingMethodChanged},
			{columnId: "pullWithinDaysToExpiration", columnName:'<fmt:message key="label.pulledwithindays"/>', type:"hed", width:5, size:30, maxLength:30, onChange:valueChanged},
			{columnId: "daysBetweenScan", columnName:'<fmt:message key="label.daysbetweenscan"/>', type:"hed", width:5, size:30, maxLength:30, onChange:valueChanged},
			{columnId: "areaDesc", columnName:'<fmt:message key="label.location"/>', attachHeader:'<fmt:message key="label.area"/>', align:"center",width:8, tooltip: true, submit:false},
			{columnId: "buildingDesc", columnName:'#cspan', attachHeader:'<fmt:message key="label.building"/>', align:"center",width:6, tooltip: true, submit:false},
			{columnId: "floor",columnName:'#cspan', attachHeader:'<fmt:message key="label.floor"/>', align:"center",width:6, tooltip: true, submit:false},
			{columnId: "roomDesc", columnName:'#cspan', attachHeader:'<fmt:message key="label.room"/>', align:"center",width:8, tooltip: true, submit:false},
			{columnId: "interior", columnName:'#cspan', attachHeader:'<fmt:message key="label.interiorexterior"/>', align:"center",width:4, submit:false},
            {columnId: "buildingLookup"<c:if test="${hasUpdatePerm}">, columnName:'#cspan', attachHeader:'<fmt:message key="label.lookup"/>', width:4, align:'center', submit:false</c:if>},
			<c:if test="${showHmrbFeatures}">
				{columnId: "compassPointPermission"},
				{columnId: "compassPoint", columnName:'<fmt:message key="label.compasspoint"/>', type:"hcoro", width:5, permission: true},
				{columnId: "locColumnPermission"},
                		{columnId: "locColumn", columnName:'<fmt:message key="label.column"/>', type:"hed", width:6, size:6, maxLength:12, permission: true},
			</c:if>
       			<c:if test="${showLocSection}">
                		{columnId: "locSection", columnName:'<fmt:message key="label.section"/>', type:"hed", width:8, size:8, maxLength:30},
			</c:if>
			<c:if test="${showEmissionPoint}">
				{columnId: "emissionPoint", columnName:'<fmt:message key="label.emissionpoint"/>', type:"hed", width:10, size:12, maxLength:30, tooltip: true},
			</c:if>			
				{columnId: "reportUsage" <c:if test="${showReportUsage}">, columnName:'<fmt:message key="label.reportusage"/>', type:"hchstatus", width:5, align:"center", sort:"haasHch" </c:if>},
				
			<c:if test="${showControlledColInWADefiniton}">
				{columnId: "specificUseApprovalRequired", columnName:'<fmt:message key="label.controlled"/>', type:"hchstatus", width:5, align:"center", sort:"haasHch"},
			</c:if>			
                {columnId: "reportInventory" <c:if test="${showReportInventory}">, columnName:'<fmt:message key="label.reportinventory"/>', type:"hchstatus", width:5, align:"center", sort:"haasHch" </c:if>},            		
			<c:choose><%-- Only supply Inventory Group pulldown if more than 1 IG && user has permission --%>
				<c:when test="${fn:length(input.facilityInventoryGroups) gt 1 && ableToSetInvGroup}">
					{columnId: "inventoryGroup", columnName:'<fmt:message key="label.inventorygroup"/>', type:"hcoro", align:"center", width:15},
				</c:when>
				<c:otherwise>
					{columnId: "inventoryGroup"},
					{columnId: "inventoryGroupDisplay", columnName:'<fmt:message key="label.inventorygroup"/>', width:15},
				</c:otherwise>
			</c:choose>
			{columnId: "stockingAccountSysId", columnName:'<fmt:message key="label.accountsys"/>', type:"hcoro", align:"center", width:13},
			<c:if test="${showChargeTypeDefault == 'Y'}">
				{columnId: "chargeTypeDefault", columnName:'<fmt:message key="label.defaultchargetype"/>', width:6<c:if test="${hasUpdatePerm}">, type:"hcoro", align:"center"</c:if>},
			</c:if>
			<c:choose><%-- Only supply Dock pulldown if more than 1 Dock --%>
				<c:when test="${fn:length(input.facilityDocks) gt 1}">
					{columnId: "locationId", columnName:'<fmt:message key="label.dock"/>', attachHeader:'<fmt:message key="label.name"/>', type:"hcoro", align:"center", width:15, onChange: clearDeliveryPoint},
				</c:when>
				<c:otherwise>
					{columnId: "locationId"},
					{columnId: "locationIdDisplay", columnName:'<fmt:message key="label.dock"/>', attachHeader:'<fmt:message key="label.name"/>', width:15, tooltip:true},
				</c:otherwise>
			</c:choose>
			{columnId: "dockFixed", columnName:'#cspan', attachHeader:'<fmt:message key="label.fixed"/>', type:"hchstatus", width:3, align:"center", sort:"haasHch"},
			{columnId: "deliveryPointDesc", columnName:'<fmt:message key="label.deliverypoint"/>', attachHeader:'<fmt:message key="label.name"/>', width:10, submit:false, tooltip: true},
			{columnId: "deliveryPointFixed", columnName:'#cspan', attachHeader:'<fmt:message key="label.fixed"/>', type:"hchstatus", width:3, align:"center", sort:"haasHch"},
			{columnId: "deliveryPointLookup"<c:if test="${hasUpdatePerm}">, columnName:'#cspan', attachHeader:'<fmt:message key="label.lookup"/>', width:4, align:'center', submit:false</c:if>},
			<c:if test= "${isUseCodeRequired == 'Y'}" >
				{columnId: "useCodeName", columnName:'<fmt:message key="label.approvalcode"/>', attachHeader:'<fmt:message key="label.approvalcode"/>', width:11, submit:false, tooltip: true},
				{columnId: "approvalCodeLookup", columnName:'#cspan', attachHeader:'<fmt:message key="label.lookup"/>', width:4, align:'center', submit:false},	
				{columnId: "useCodeIdString",submit:true},
			</c:if>
			<c:if test= "${showEmissionPoints}" >
				{columnId: "emissionPointsName", columnName:'<fmt:message key="label.emissionpoint"/>', attachHeader:'<fmt:message key="label.emissionpoint"/>', width:14, submit:false, tooltip: true},
				{columnId: "emissionPointsLookup", columnName:'#cspan', attachHeader:'<fmt:message key="label.lookup"/>', width:4, align:'center', submit:false},
			</c:if>
			<c:if test= "${isAllowSplitKitsColView == 'Y'}" >
				{columnId:"allowSplitKits",columnName:'<fmt:message key="label.splitkits"/>',type:'hchstatus', align:'center',width:4},
			</c:if>
			<c:if test="${ShowHETColumns}">
				{columnId:"overrideUsageLogging",columnName:'<fmt:message key="label.dailyusagelogging"/>',type:'hchstatus', align:'center',width:4},
				{columnId:"allowSplitKits",columnName:'<fmt:message key="label.allowseparablemixture"/>',type:'hchstatus', align:'center',width:4},
				{columnId:"hetMultipleBuildingUsage",columnName:'<fmt:message key="label.multiplebuildingusage"/>',type:'hchstatus', align:'center',width:4},
			</c:if>
			<c:if test="${showIncludeExpired}">
				{columnId:"includeExpiredMaterial",columnName:'<fmt:message key="label.includeexpiredmaterial"/>',type:'hchstatus', align:'center',width:4},
		    </c:if>
			{columnId: "locationDetail", columnName:'<fmt:message key="label.description"/>', type:"hed", width:16, size:30, maxLength:30, onChange:valueChanged},
			{columnId: "contactName", columnName:'<fmt:message key="label.contactname"/>', type:"hed", width:16, size:30, maxLength:30, onChange:valueChanged},
			{columnId: "contactPhone", columnName:'<fmt:message key="label.phone"/>', type:"hed", width:10, size:15, maxLength:30, onChange:valueChanged},
			{columnId: "contactEmail", columnName:'<fmt:message key="label.email"/>', type:"hed", width:16, size:30, maxLength:30, onChange:valueChanged},
			<c:if test="${showAlternateContact}">
				{columnId: "contact2Name", columnName:'<fmt:message key="label.secondarycontactname"/>', type:"hed", width:16, size:30, maxLength:30, onChange:valueChanged},
				{columnId: "contact2Phone", columnName:'<fmt:message key="label.secondaryphone"/>', type:"hed", width:10, size:15, maxLength:30, onChange:valueChanged},
				{columnId: "contact2Email", columnName:'<fmt:message key="label.secondaryemail"/>', type:"hed", width:16, size:30, maxLength:30, onChange:valueChanged},
		    </c:if>
			{columnId: "customerCabinetId", columnName:'<fmt:message key="label.customworkareaid"/>', type:"hed", width:16, size:30, maxLength:30, onChange:valueChanged},
			{columnId: "facilityId"},
			{columnId: "companyId"},
			{columnId: "applicationId"},
			{columnId: "deliveryPoint"},
			{columnId: "reportingEntityId"},
			{columnId: "areaId"},
			{columnId: "buildingId"},
			{columnId: "floorId"},
			{columnId: "roomId"},
			{columnId: "application"},			
			<c:if test="${showFlammabilityControlZone}">
	            {columnId: "flammabilityControlZoneId"},
	            {columnId: "flammabilityControlZoneDesc", columnName:'<fmt:message key="label.flammabilitycontrolzone"/>', attachHeader:'<fmt:message key="label.name"/>', width:10, submit:false},
	            {columnId: "flammabilityControlZoneLookup"<c:if test="${hasUpdatePerm}">, columnName:'#cspan', attachHeader:'<fmt:message key="label.lookup"/>', width:4, align:'center', submit:false</c:if>},
            </c:if>
            <c:if test="${showVocZone}">
	            {columnId: "vocZoneId"},
	            {columnId: "vocZoneDescription", columnName:'<fmt:message key="label.voczone"/>', attachHeader:'<fmt:message key="label.name"/>', width:10, submit:false},
	            {columnId: "vocZoneLookup"<c:if test="${hasUpdatePerm}">, columnName:'#cspan', attachHeader:'<fmt:message key="label.lookup"/>', width:4, align:'center', submit:false</c:if>},
            </c:if>
			{columnId: "touched"}
	       ];

	       var orderingMethod = [
	                             {value: 'MANUAL', text: '<fmt:message key="label.manualordering"/>'},
	                             {value: 'STOCKLEVEL', text: '<fmt:message key="label.stocklevelordering"/>'},
	                             {value: 'BOTH', text: '<fmt:message key="label.manualandstocklevelordering"/>'}
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
		  			<c:if test="${invGroup.inventoryGroup == input.defaultValues.inventoryGroup}">
		  				{value:'${invGroup.inventoryGroup}', text: '<tcmis:jsReplace value="${invGroup.inventoryGroupName}"/>'}
		  			</c:if>
		 		</c:forEach>
		  		];
			</c:otherwise>
		</c:choose>

		var stockingAccountSysId = new Array();
		stockingAccountSysId = [<c:forEach var="prRulesViewBean" items="${prRulesViewCollection}" varStatus="status">
			{value:'${prRulesViewBean.accountSysId}', text: '<tcmis:jsReplace value="${prRulesViewBean.accountSysId}"/>'}<c:if test="${!status.last}">,</c:if>
		</c:forEach>];
		
		var chargeTypeDefault = new Array();
		chargeTypeDefault = [
			{value:'', text: ''},
			{value:'d', text: '<fmt:message key="label.direct"/>'},
			{value:'i', text: '<fmt:message key="label.indirect"/>'}
		];
		
		var locationId = new Array();
		locationId = [<c:forEach var="dock" items="${input.facilityDocks}" varStatus="status">
			{value:'${dock.dockLocationId}', text: '<tcmis:jsReplace value="${dock.dockDesc}"/>'}<c:if test="${!status.last}">,</c:if>
		</c:forEach>];

		var compassPoint = [
		        {value:'', text: ''},
			<c:forEach var="point" items="${compassPoints}" varStatus="status">
				{value:'${point.shortName}', text: '${point.shortName}'}<c:if test="${!status.last}">,</c:if>
			</c:forEach>		    		
		    	];
		
		var defaultCompanyId = '${param.companyId}';
		var defaultFacilityId = '${input.defaultValues.facilityId}';
		var defaultInventoryGroup = '${input.defaultValues.inventoryGroup}';
		var defaultDockId = '${input.defaultValues.locationId}';
		var defaultWorkAreaGroup ='${input.defaultValues.reportingEntityId}';
		var defaultWorkAreaGroupDesc ='<tcmis:jsReplace value="${input.defaultValues.workAreaGroupDesc}"/>';
		
		var gridConfig = {
			divName:'resultGridDiv', // the div id to contain the grid.
			beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
			beanGrid:'mygrid', // the grid's name, as in beanGrid.attachEvent...
			config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
			rowSpan:false, // true: this page has rowSpan > 1, disables smartrendering & sorting
			submitDefault:true, // the fields in grid are defaulted to be submitted or not.
			onRightClick: showRightClickMenu,
			onRowSelect: rowSelected
		};

		with(milonic=new menuname("rightClickMenu")){
		    top="offset=2"
		    style = contextWideStyle;
		    margin=3
            <c:forEach var="prRulesViewBean" items="${prRulesViewCollection}" varStatus="status2">
                aI("text=<c:choose><c:when test="${directedChargeAssignment == 'Y'}"><fmt:message key="label.editworkareadirectedcharge"/></c:when><c:otherwise><fmt:message key="label.viewdirectedcharge"/></c:otherwise></c:choose> : <c:choose><c:when test="${not empty prRulesViewBean.accountSysId}">${prRulesViewBean.accountSysId}</c:when><c:otherwise>${prRulesViewBean.accountSysId}</c:otherwise></c:choose>;url=javascript:editDirectedCharge('${prRulesViewBean.accountSysId}');");
            </c:forEach>
            aI("text=<fmt:message key="label.changehistory"/>;url=javascript:showHistory();");
		}

		drawMenus();
       
		</script>
</head>
<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);myResultOnLoad(); setTimeout('parent.resizeFrame(12)',5);">
	<tcmis:form action="/clientcabinetsetupresults.do" onsubmit="return submitFrameOnlyOnce();">
		
		<script type="text/javascript">
		   <c:choose>
		    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
			    <c:choose>
		     		<c:when test="${param.maxData == fn:length(workAreaCollection)}">
		        		showErrorMessage = true;
		        		parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
		      		</c:when>
		      		<c:otherwise>
		        		showErrorMessage = false;
		      		</c:otherwise>
		     	</c:choose>
		    </c:when>
		    <c:otherwise>
		     parent.messagesData.errors = "<fmt:message key="label.errors"/>";
		     showErrorMessage = true;
		    </c:otherwise>
		   </c:choose>   
		    //-->       
		</script>		
		
		<script  language="JavaScript" type="text/javascript">
			<c:if test= "${!empty workAreaCollection}" >
				var jsonMainData = {
					rows: [	<c:forEach var= "row" items= "${workAreaCollection}" varStatus= "status" >
				     		<c:set var="applicationPermission" value="false"/>
				     		<c:if test="${row.locationDetail != null && row.locationDetail != ''}"><c:set var="applicationPermission" value="true"/></c:if>
							{ id: ${status.count},
							  <c:if test="${row.status == 'I'}">'class': 'grid_lightgray',</c:if>
							  data: ['${updateFlag}',
							  	 <c:choose><c:when test="${row.status == 'A'}">true</c:when><c:otherwise>false</c:otherwise></c:choose>,
							  	 '<tcmis:jsReplace value="${row.applicationDesc}" processMultiLines="true"/>',
								 '<tcmis:jsReplace value="${row.deptId}"/>',
								 '<tcmis:jsReplace value="${row.deptName}"/>',
								 '<input name="searchForDept${status.count}" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="lookupDept(${status.count});">', // Lookup
							  	 ${row.dropShip},
							  	 ${row.offsite},
							  	 '<tcmis:jsReplace value="${row.workAreaGroupDesc}" processMultiLines="true"/>',
							  	 '<input name="searchForWAG${status.count}" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="lookupWorkAreaGroup(${status.count});">', // Lookup
							  	 '<c:choose><c:when test="${row.allowStocking == 'Y'}"><c:choose><c:when test="${row.manualMrCreation == 'Y'}">BOTH</c:when><c:otherwise>STOCKLEVEL</c:otherwise></c:choose></c:when><c:otherwise>MANUAL</c:otherwise></c:choose>',
							  	 '${row.pullWithinDaysToExpiration}',
							  	 '${row.daysBetweenScan}',
							  	 '<tcmis:jsReplace value="${row.areaName}" processMultiLines="true"/>',
							  	 '<tcmis:jsReplace value="${row.buildingName}" processMultiLines="true"/>',
							  	 '<tcmis:jsReplace value="${row.floor}" />',
							  	 '<tcmis:jsReplace value="${row.roomName}" processMultiLines="true"/><c:if test="${not empty row.roomDesc}"> - <tcmis:jsReplace value="${row.roomDesc}" processMultiLines="true"/></c:if>',
							  	 '<c:choose><c:when test="${empty row.roomId}"></c:when><c:when test="${row.interior}">I</c:when><c:otherwise>E</c:otherwise></c:choose>',
				                                 '<input name="searchForBuilding${status.count}" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="lookupBuilding(${status.count});">', // Lookup
							  	<c:if test="${showHmrbFeatures}">
							  		<c:choose><c:when test="${empty row.roomId}">'N',</c:when><c:otherwise>'Y',</c:otherwise></c:choose>
								  	'${row.compassPoint}',
                                    					<c:choose><c:when test="${empty row.roomId}">'N',</c:when><c:otherwise>'Y',</c:otherwise></c:choose>  
                                    					'${row.locColumn}',
							  	</c:if>
       							  	<c:if test="${showLocSection}">
                                    					'${row.locSection}',
							  	</c:if>
								<c:if test="${showEmissionPoint}">
	                            					'${row.emissionPoint}',
                            					</c:if>
	                            					${row.reportUsage},
	                            				<c:if test="${showControlledColInWADefiniton}">
	                            					${row.specificUseApprovalRequired},
           		                       			</c:if>
                                                    ${row.reportInventory},
							  	 '${row.inventoryGroup}',
							  	 <c:if test="${fn:length(input.facilityInventoryGroups) le 1 || !ableToSetInvGroup}">'<tcmis:jsReplace value="${row.inventoryGroupName}" processMultiLines="true"/>',</c:if>
							  	 '${row.stockingAccountSysId}',
							  	 <c:if test="${showChargeTypeDefault == 'Y'}">'${row.chargeTypeDefault}',</c:if>
								 '${row.locationId}',
								 <c:if test="${fn:length(input.facilityDocks) le 1}">'<tcmis:jsReplace value="${input.facilityDocks[0].dockDesc}" processMultiLines="true"/>',</c:if>
							  	 ${row.dockSelectionFixed},
							  	 '<tcmis:jsReplace value="${row.deliveryPointDesc}" processMultiLines="true"/>',
							  	 ${row.deliveryPointSelectionFixed},
							  	 '<input name="searchForDeliveryPoint${status.count}" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="lookupDeliveryPoint(${status.count});">', // Lookup
								 <c:if test= "${isUseCodeRequired == 'Y'}" >
								 	'${row.useCodeName}',
								  	'<input name="searchApprovalCode${status.count}" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="lookupApprovalCode(${status.count});">', // Lookup
								 	'${row.useCodeIdString}',
								 </c:if>
								 <c:if test= "${showEmissionPoints}" >
								 	'<fmt:message key="msg.lookupformoredetails"/>',
								 	'<input name="searchEmissionPoints${status.count}" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="lookupEmissionPoints(${status.count});">', // Lookup
								 </c:if>
								 <c:if test= "${isAllowSplitKitsColView == 'Y'}" >${row.allowSplitKits},</c:if>
								 <c:if test="${showAllowSplitKits}">${row.allowSplitKits},</c:if>
								 <c:if test="${ShowHETColumns}">
									 ${row.overrideUsageLogging},
									 ${row.allowSplitKits},
									 ${row.hetMultipleBuildingUsage},
								 </c:if>
								 <c:if test="${showIncludeExpired}">
									${row.includeExpiredMaterial},
							     </c:if>
								 '<tcmis:jsReplace value="${row.locationDetail}" processMultiLines="true"/>',
								 '<tcmis:jsReplace value="${row.contactName}" processMultiLines="true"/>',
							  	 '<tcmis:jsReplace value="${row.contactPhone}" processMultiLines="true"/>',
							  	 '<tcmis:jsReplace value="${row.contactEmail}" processMultiLines="true"/>',
							  	 <c:if test="${showAlternateContact}">
							  	 	'<tcmis:jsReplace value="${row.contact2Name}" processMultiLines="true"/>',
							  	 	'<tcmis:jsReplace value="${row.contact2Phone}" processMultiLines="true"/>',
							  	 	'<tcmis:jsReplace value="${row.contact2Email}" processMultiLines="true"/>',
		    					 </c:if>
							  	 '<tcmis:jsReplace value="${row.customerCabinetId}" processMultiLines="true"/>',
							  	 '${row.facilityId}',
							  	 '${row.companyId}',
							  	 '${row.applicationId}',
							  	 '${row.deliveryPoint}',
							  	 '${row.reportingEntityId}',
								 '${row.areaId}',
							  	 '${row.buildingId}',
							  	 '${row.floorId}',
							  	 '${row.roomId}',
								 '<tcmis:jsReplace value="${row.application}"/>',
								 <c:if test="${showFlammabilityControlZone}">
								    '<tcmis:jsReplace value="${row.flammabilityControlZoneId}"/>',
								    '<tcmis:jsReplace value="${row.flammabilityControlZoneDesc}"/>',
								    '<input name="searchForFlamCtrlZn${status.count}" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="lookupFlamCtrlZn(${status.count});">', // Lookup
								 </c:if>
								 <c:if test="${showVocZone}">
	                                 '<tcmis:jsReplace value="${row.vocZoneId}"/>',
	                                 '<tcmis:jsReplace value="${row.vocZoneDescription}"/>',
	                                 '<input name="searchForVocZone${status.count}" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="lookupVocZone(${status.count});">', // Lookup
	                             </c:if>
								 false
							  	] 
							}<c:if test= "${!status.last}" >,</c:if>
						</c:forEach>
					]
				};
			</c:if>
		</script>		
		<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
		So this is just used to feed the YUI pop-up in the main page.
			Similar divs would have to be built to show any other messages in a pop-up.-->
		<div id="errorMessagesAreaBody" style="display:none;">
		    <html:errors/>
		    ${tcmISError}
		    <c:forEach items="${tcmISErrors}" varStatus="status">
		  	${status.current}<br/>
		    </c:forEach>        
		    
		    <c:if test="${param.maxData == fn:length(workAreaCollection)}">
			     <fmt:message key="label.maxdata">
			       <fmt:param value="${param.maxData}"/>
			     </fmt:message>
			     &nbsp;<fmt:message key="label.clickcreateexcelforcompleteresult"/>
		    </c:if>
		</div>
		
		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<div id="resultGridDiv" style="width:100%;height:400px;" style="display: none;" >
				</div>
				<!-- If the collection is empty say no data found -->
				<c:if test="${empty workAreaCollection}" >
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

