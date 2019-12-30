<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support for column type hcal-->
<!--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<!-- These are for the Grid-->
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>


<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hch -checkbox
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="label.changehistory"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
errors:"<fmt:message key="label.errors"/>",itemInteger:"<fmt:message key="error.item.integer"/>"};

var gridConfig = {
	divName:'facLocAppViewBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:'false',			 // this page has rowSpan > 1 or not.
	submitDefault:'true',    // the fields in grid are defaulted to be submitted or not.
    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	noSmartRender:false // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
};

<c:set var="showIncludeExpired" value="${tcmis:isCompanyFeatureReleased(personnelBean,'IncludeExpired', param.facilityId, param.companyId)}"/>
<c:set var="showAlternateContact" value="${tcmis:isCompanyFeatureReleased(personnelBean,'ShowAlternateContact', param.facilityId, param.companyId)}"/>
<c:set var="showLocSection" value="${tcmis:isCompanyFeatureReleased(personnelBean,'ShowLocSection', param.facilityId, param.companyId)}"/>
<c:set var="showEmissionPoint" value="${tcmis:isCompanyFeatureReleased(personnelBean,'ShowEmissionPoint', param.facilityId, param.companyId)}"/>
<c:set var="showReportUsage" value="${tcmis:isCompanyFeatureReleased(personnelBean,'ShowReportUsage', param.facilityId, param.companyId)}"/>


/*This array defines the grid and properties about the columns in the grid.
* more explanation of the grid config can be found in /dhtmlxGrid/codebase/dhtmlxcommon_haas.js initGridWithConfig()
* */
var config = [
      		{columnId: "permission"},
      		{columnId: "active", columnName:'<fmt:message key="label.active"/>', type:"hchstatus", width:4, align:"center", sort:"haasHch"},
      		{columnId: "applicationDesc", columnName:'<fmt:message key="label.workarea"/>', type:"hed", width:16, size:30, maxLength:30},
      		{columnId: "deptId"},
      		{columnId: "deptName", columnName:'<fmt:message key="label.department"/>', width:10, submit:false},
      		//6
      		{columnId: "dropShip", columnName:'<fmt:message key="label.dropship"/>', type:"hchstatus", width:3, align:"center", sort:"haasHch"},
      		{columnId: "offsite", columnName:'<fmt:message key="label.offsite"/>', type:"hchstatus", width:3, align:"center", sort:"haasHch"},
      		{columnId: "workAreaGroupDesc", columnName:'<fmt:message key="label.workareagroup"/>', <c:if test="${hasUpdatePerm}">attachHeader:'<fmt:message key="label.group"/>',</c:if> width:10, submit:false},
      		//10
      		{columnId: "orderingMethod", columnName:'<fmt:message key="label.orderingmethodforstockedparts"/>', type:"hcoro", align:"center", width:13},
      		{columnId: "pullWithinDaysToExpiration", columnName:'<fmt:message key="label.pulledwithindays"/>', type:"hed", width:5, size:30, maxLength:30},
      		{columnId: "daysBetweenScan", columnName:'<fmt:message key="label.daysbetweenscan"/>', type:"hed", width:5, size:30, maxLength:30},
      		{columnId: "areaDesc", columnName:'<fmt:message key="label.location"/>', attachHeader:'<fmt:message key="label.area"/>', align:"center",width:8, tooltip: true, submit:false},
      		{columnId: "buildingDesc", columnName:'#cspan', attachHeader:'<fmt:message key="label.building"/>', align:"center",width:6, tooltip: true, submit:false},
      		{columnId: "floor",columnName:'#cspan', attachHeader:'<fmt:message key="label.floor"/>', align:"center",width:6, tooltip: true, submit:false},
      		{columnId: "roomDesc", columnName:'#cspan', attachHeader:'<fmt:message key="label.room"/>', align:"center",width:8, tooltip: true, submit:false},
      		{columnId: "interior", columnName:'#cspan', attachHeader:'<fmt:message key="label.interiorexterior"/>', align:"center",width:4, submit:false},
      		//19
      		<c:if test="${showHmrbFeatures}">
      			{columnId: "compassPointPermission"},
      			{columnId: "compassPoint", columnName:'<fmt:message key="label.compasspoint"/>', type:"hcoro", width:5, permission: true},
      			{columnId: "locColumnPermission"},
      		    		{columnId: "locColumn", columnName:'<fmt:message key="label.column"/>', type:"hed", width:6, size:12, maxLength:12, permission: true},
      		</c:if>
		<c:if test="${showLocSection}">
               		{columnId: "locSection", columnName:'<fmt:message key="label.section"/>', type:"hed", width:8, size:8, maxLength:30},
		</c:if>
		<c:if test="${showEmissionPoint}">
			{columnId: "emissionPoint", columnName:'<fmt:message key="label.emissionpoint"/>', type:"hed", width:10, size:12, maxLength:30, tooltip: true},
		</c:if>
		<c:if test="${showReportUsage}">
			{columnId: "reportUsage", columnName:'<fmt:message key="label.reportusage"/>', type:"hchstatus", width:3, align:"center", sort:"haasHch"},
		</c:if>
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
				{columnId: "chargeTypeDefault", columnName:'<fmt:message key="label.defaultchargetype"/>', width:6, type:"hcoro", align:"center"},
			</c:if>
            <c:choose><%-- Only supply Dock pulldown if more than 1 Dock --%>
      			<c:when test="${fn:length(input.facilityDocks) gt 1}">
      				{columnId: "locationId", columnName:'<fmt:message key="label.dock"/>', attachHeader:'<fmt:message key="label.name"/>', type:"hcoro", align:"center", width:15},
      			</c:when>
      			<c:otherwise>
      				{columnId: "locationId"},
      				{columnId: "locationIdDisplay", columnName:'<fmt:message key="label.dock"/>', attachHeader:'<fmt:message key="label.name"/>', width:15, tooltip:true},
      			</c:otherwise>
      		</c:choose>
      		{columnId: "dockFixed", columnName:'#cspan', attachHeader:'<fmt:message key="label.fixed"/>', type:"hchstatus", width:3, align:"center", sort:"haasHch"},
      		{columnId: "deliveryPointDesc", columnName:'<fmt:message key="label.deliverypoint"/>', attachHeader:'<fmt:message key="label.name"/>', width:10, submit:false, tooltip: true},
      		{columnId: "deliveryPointFixed", columnName:'#cspan', attachHeader:'<fmt:message key="label.fixed"/>', type:"hchstatus", width:3, align:"center", sort:"haasHch"},
      		//4
      		<c:if test= "${isUseCodeRequired == 'Y'}" >
      		{columnId: "useCodeName", columnName:'<fmt:message key="label.approvalcode"/>', attachHeader:'<fmt:message key="label.approvalcode"/>', width:11, submit:false, tooltip: true},
      		{columnId: "useCodeIdString",submit:true},
      		</c:if>
      		<c:if test= "${isAllowSplitKitsColView == 'Y'}" >{columnId:"allowSplitKits",columnName:'<fmt:message key="label.splitkits"/>',type:'hchstatus', align:'center',width:4},</c:if>
      		<c:if test="${showAllowSplitKits}">{columnId:"allowSplitKits",columnName:'<fmt:message key="label.splitkits"/>',type:'hchstatus', align:'center',width:4},</c:if>
      		<c:if test="${showHmrbFeatures}">
      		{columnId:"overrideUsageLogging",columnName:'<fmt:message key="label.dailyusagelogging"/>',type:'hchstatus', align:'center',width:4},
      		{columnId:"allowSplitKits",columnName:'<fmt:message key="label.allowseparablemixture"/>',type:'hchstatus', align:'center',width:4},
      		</c:if>
		<c:if test="${showIncludeExpired}">
			{columnId:"includeExpiredMaterial",columnName:'<fmt:message key="label.includeexpiredmaterial"/>',type:'hchstatus', align:'center',width:4},
		</c:if>

      		{columnId: "locationDetail", columnName:'<fmt:message key="label.description"/>', type:"hed", width:16, size:30, maxLength:30},
      		{columnId: "contactName", columnName:'<fmt:message key="label.contactname"/>', type:"hed", width:16, size:30, maxLength:30},
      		{columnId: "contactPhone", columnName:'<fmt:message key="label.phone"/>', type:"hed", width:10, size:15, maxLength:30},
      		{columnId: "contactEmail", columnName:'<fmt:message key="label.email"/>', type:"hed", width:16, size:30, maxLength:30},
      		<c:if test="${showAlternateContact}">
      			{columnId: "contact2Name", columnName:'<fmt:message key="label.secondarycontactname"/>', type:"hed", width:16, size:30, maxLength:30},
      			{columnId: "contact2Phone", columnName:'<fmt:message key="label.secondaryphone"/>', type:"hed", width:10, size:15, maxLength:30},
      			{columnId: "contact2Email", columnName:'<fmt:message key="label.secondaryemail"/>', type:"hed", width:16, size:30, maxLength:30},
		    </c:if>
      		{columnId: "customerCabinetId", columnName:'<fmt:message key="label.customworkareaid"/>', type:"hed", width:16, size:30, maxLength:30},
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
      		{columnId: "updatedBy", columnName:'<fmt:message key="label.updatedby"/>', width:10},
      		{columnId: "updatedOn", columnName:'<fmt:message key="label.updatedon"/>', width:10}
      ];

	var orderingMethod = [
                      {value: 'MANUAL', text: '<fmt:message key="label.manualordering"/>'},
                      {value: 'STOCKLEVEL', text: '<fmt:message key="label.stocklevelordering"/>'},
                      {value: 'BOTH', text: '<fmt:message key="label.manualandstocklevelordering"/>'}
       	       ];

	var compassPoint = [
    		        {value:'', text: ''},
    			<c:forEach var="point" items="${compassPoints}" varStatus="status">
    				{value:'${point.shortName}', text: '${point.shortName}'}<c:if test="${!status.last}">,</c:if>
    			</c:forEach>		    		
    		    	];

	var stockingAccountSysId = new Array();
	stockingAccountSysId = [<c:forEach var="prRulesViewBean" items="${prRulesViewCollection}" varStatus="status">
		{value:'${prRulesViewBean.accountSysId}', text: '<tcmis:jsReplace value="${prRulesViewBean.accountSysId}"/>'}<c:if test="${!status.last}">,</c:if>
	</c:forEach>];

	var locationId = new Array();
	locationId = [<c:forEach var="dock" items="${input.facilityDocks}" varStatus="status">
		{value:'${dock.dockLocationId}', text: '<tcmis:jsReplace value="${dock.dockDesc}"/>'}<c:if test="${!status.last}">,</c:if>
	</c:forEach>];

// -->
 </script>
</head>

<body bgcolor="#ffffff"  onload="popupOnLoadWithGrid(gridConfig);" onresize="resizeFrames()">

<tcmis:form action="/clientcabinetsetupresults.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;">
<!-- results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Please keep the <span></span> on the same line this will avoid extra virtical space.
      --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      </div>
     </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
	</div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
<div id="facLocAppViewBean" style="width:100%;height:600px;" style="display: none;"></div>
<!-- Search results start -->
<c:if test="${workAreaCollection != null}" >
<script type="text/javascript">
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
<c:set var="showUpdateLink" value='N'/>
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty workAreaCollection}" >
/*Storing the data to be displayed in a JSON object array.*/
var jsonMainData = new Array();
var jsonMainData =  {
		rows: [	<c:forEach var= "row" items= "${workAreaCollection}" varStatus= "status" >
		{ id: ${status.count},
		  <c:if test="${row.status == 'I'}">'class': 'grid_lightgray',</c:if>
		  data: ['N',
		  	 <c:choose><c:when test="${row.status == 'A'}">true</c:when><c:otherwise>false</c:otherwise></c:choose>,
		  	 '<tcmis:jsReplace value="${row.applicationDesc}" processMultiLines="true"/>',
			 '<tcmis:jsReplace value="${row.deptId}"/>',
			 '<tcmis:jsReplace value="${row.deptName}"/>',

             <c:choose><c:when test="${row.dropShip == 'Y'}">true</c:when><c:otherwise>false</c:otherwise></c:choose>,
             <c:choose><c:when test="${row.offsite == 'Y'}">true</c:when><c:otherwise>false</c:otherwise></c:choose>,
		  	 '<tcmis:jsReplace value="${row.workAreaGroupDesc}" processMultiLines="true"/>',

		  	 '<c:choose><c:when test="${row.allowStocking == 'Y'}"><c:choose><c:when test="${row.manualMrCreation == 'Y'}">BOTH</c:when><c:otherwise>STOCKLEVEL</c:otherwise></c:choose></c:when><c:otherwise>MANUAL</c:otherwise></c:choose>',
		  	 '${row.pullWithinDaysToExpiration}',
		  	 '${row.daysBetweenScan}',
		  	 '<tcmis:jsReplace value="${row.areaName}" processMultiLines="true"/>',
		  	 '<tcmis:jsReplace value="${row.buildingName}" processMultiLines="true"/>',
		  	 '<tcmis:jsReplace value="${row.floor}" />',
		  	 '<tcmis:jsReplace value="${row.roomName}" processMultiLines="true"/><c:if test="${not empty row.roomDesc}"> - <tcmis:jsReplace value="${row.roomDesc}" processMultiLines="true"/></c:if>',
		  	 '<c:choose><c:when test="${empty row.roomId}"></c:when><c:when test="${row.interior}">I</c:when><c:otherwise>E</c:otherwise></c:choose>',
		  	<c:if test="${showHmrbFeatures}">
		  		'N',
			  	'${row.compassPoint}',
                'N',
                '${row.locColumn}',
		  	</c:if>
		  	<c:if test="${showLocSection}">
				'${row.locSection}',
		  	</c:if>
			<c:if test="${showEmissionPoint}">
				'${row.emissionPoint}',
			</c:if>
			<c:if test="${showReportUsage}">
				${row.reportUsage},
			</c:if>
		  	 '${row.inventoryGroup}',
		  	 <c:if test="${fn:length(input.facilityInventoryGroups) le 1 || !ableToSetInvGroup}">'<tcmis:jsReplace value="${row.inventoryGroup}" processMultiLines="true"/>',</c:if>
		  	 '${row.stockingAccountSysId}',
             <c:if test="${showChargeTypeDefault == 'Y'}">'${row.chargeTypeDefault}',</c:if>     
             '${row.locationId}',
			 <c:if test="${fn:length(input.facilityDocks) le 1}">'<tcmis:jsReplace value="${input.facilityDocks[0].dockDesc}" processMultiLines="true"/>',</c:if>
		  	 <c:choose><c:when test="${row.dockSelectionRule == 'FIXED'}">true</c:when><c:otherwise>false</c:otherwise></c:choose>,
		  	 '<tcmis:jsReplace value="${row.deliveryPointDesc}" processMultiLines="true"/>',
		  	 <c:choose><c:when test="${row.deliveryPointSelectionRule == 'FIXED'}">true</c:when><c:otherwise>false</c:otherwise></c:choose>,
			 <c:if test= "${isUseCodeRequired == 'Y'}" >
			 	'${row.useCodeName}',
			 	'${row.useCodeIdString}',
			 </c:if>
			 <c:if test= "${isAllowSplitKitsColView == 'Y'}" >${row.allowSplitKits},</c:if>
			 <c:if test="${showAllowSplitKits}">${row.allowSplitKits},</c:if>
			 <c:if test="${showHmrbFeatures}">
				<c:choose><c:when test="${row.overrideUsageLogging == 'Y'}">true</c:when><c:otherwise>false</c:otherwise></c:choose>,
				<c:choose><c:when test="${row.allowSplitKits == 'Y'}">true</c:when><c:otherwise>false</c:otherwise></c:choose>,
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
		  	 '${row.updatedByName}',
		  	 '${row.updatedOn}'
		  ]}<c:if test="${!status.last}">,</c:if>  
		  <c:set var="dataCount" value='${dataCount+1}'/>
	 </c:forEach>
]};
</c:if>
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty workAreaCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>

</c:if>
<!-- Search results end -->
   </div>
   <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>
  </div>

  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td>
</tr>
</table>
<!-- results end -->
</div>
</div>
<!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>

<%--Store the search parameters.--%>
<input name="action" id="action" value="" type="hidden"/>

<!--This sets the start time for time elapesed from the action.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
<!--This sets the end time for time elapesed from the action.-->
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">

<input name="minHeight" id="minHeight" type="hidden" value="100">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<!-- If the user has permissions and needs to see the update links,set the variable showUpdateLinks javascript to true.
     The default value of showUpdateLinks is false.
-->
<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>

</tcmis:form>

<!-- You can build your error messages below.
     Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

</body>
</html:html>