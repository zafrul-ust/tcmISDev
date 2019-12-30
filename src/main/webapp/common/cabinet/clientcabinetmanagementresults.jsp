<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

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
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS -->
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
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/cabinet/clientcabinetmanagementresults.js"></script>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
    <fmt:message key="clientCabinetManagement"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
            validvalues:"<fmt:message key="label.validvalues"/>",
            submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
            recordFound:"<fmt:message key="label.recordFound"/>",
            searchDuration:"<fmt:message key="label.searchDuration"/>",
            minutes:"<fmt:message key="label.minutes"/>",
            seconds:"<fmt:message key="label.seconds"/>",
            itemInteger:"<fmt:message key="error.item.integer"/>",
            ok:"<fmt:message key="label.ok"/>",                       
            dateofrevision:"<fmt:message key="label.dateofrevision"/>",
            expediteage:"<fmt:message key="label.expediteage"/>",
            comments:"<fmt:message key="label.comments"/>",
            maximum2000:"<fmt:message key="label.maximum2000"/>",
			invalidChargeNumbers:"<fmt:message key="label.invalidchargenumbers"/>",
	 		viewMsds:"<fmt:message key="label.viewmsds"/>",
	        invalidChargeNumberAddTolist:"<fmt:message key="label.invalidchargenumberaddtolist"/>",
			addPart:"<fmt:message key="label.addpart"/>",
			generateLabelsForWorkArea:"<fmt:message key="label.generatelabelsforworkarea"/>",
			generateBinLabelForWorkArea:"<fmt:message key="label.generatebinlabel"/>",
			editWorkAreaDirectedCharge:"<fmt:message key="label.editworkareadirectedcharge"/>",
			setPartLevel:"<fmt:message key="label.setpartlevel"/>",
			editPartDirectedCharge:"<fmt:message key="label.editpartdirectedcharge"/>",
	        waitingforinput:"<fmt:message key="label.waitingforinputfrom"/>",
	        directedCharge:"<fmt:message key="label.directedCharge"/>",
	        pleaseselect:"<fmt:message key="label.pleaseselect"/>",
	        nokanbanreorderqty:"<fmt:message key="label.nokanbanreorderqty"/>",
	        defaultvalusforall:"<fmt:message key="error.defaultvalusforall"/>",
			revisedPromisedDate:"<fmt:message key="label.revisedpromisedate"/>",
			averageAmount:"<fmt:message key="label.averageamount"/>",
			maxAmount:"<fmt:message key="label.maxamount"/>",
			avgGreaterMax:"<fmt:message key="label.avggreatermax"/>",
			tierIITemperature:"<fmt:message key="label.temperature"/>",
			tierIIStorageMethod:"<fmt:message key="label.container"/>",
			sizeUnit:"<fmt:message key="label.unit"/>",
            viewPartDirectedCharge:"<fmt:message key="label.viewdirectedcharge"/>",
            largestContainerSize:"<fmt:message key="label.largestcontainersize"/>",
            viewNonmanagedMatlHist:"<fmt:message key="label.viewnonmanagedmatlhist"/>",
            viewPartHistory:"<fmt:message key="catalogspec.label.viewparthistory"/>",
            changeOwnership:"<fmt:message key="label.changeownership"/>",
            changeBinName:"<fmt:message key="label.change"/> <fmt:message key="label.binname"/>",
            pleaseselectahub:"<fmt:message key="label.pleaseselectahub"/>",
            saveBefore:"<fmt:message key="label.savebeforechangeownership"/>",
            setCabinetCountValues:"<fmt:message key="label.setcabinetcountvalues"/>"
			};
				
var newMsdsViewer = false;

<tcmis:featureReleased feature="NewMsdsViewer" scope="ALL" companyId="${param.companyId}">
   newMsdsViewer = true;
</tcmis:featureReleased>

var tierIIRequired = false;
<tcmis:featureReleased feature="TierIIRequired" scope="ALL" companyId="${param.companyId}">
    tierIIRequired = true;
</tcmis:featureReleased>

<c:set var="homeCompanyOwned" value="false"/>
var showChangeOwnership = false;
<tcmis:featureReleased feature="HomeCompanyOwned" scope="${param.facilityId}" companyId="${param.companyId}">
	<c:set var="homeCompanyOwned" value="true"/>
	<tcmis:facilityPermission indicator="true" userGroupId="HomeCompanyOwned" companyId="${param.companyId}" facilityId="${param.facilityId}">
		<c:if test="${sessionScope.personnelBean.operatingCompanyEmployee}" >
			showChangeOwnership = true;
	   	</c:if>
	</tcmis:facilityPermission>
</tcmis:featureReleased>

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set> 
<c:set var="itemid"><fmt:message key="label.itemid"/></c:set>
<c:set var="itemdescription"><fmt:message key="label.itemdescription"/></c:set>  
<c:set var="expediteage"><fmt:message key="label.expediteage"/></c:set>                
var config = [
  {
  	columnId:"permission"
  },
  {
  	columnId:"countTypePermission"
  },
  {
  	columnId:"cabinetIdPermission"
  },
  {
  	columnId:"showBinPartStatusPermission"
  },
  {
    columnId:"cabinetId",
	tooltip:"Y",
  	align:'left',
  	width:15
  },
  {
    columnId:"applicationDesc",
    columnName:'<fmt:message key="label.workarea"/>',
    tooltip:"Y",  
    align:'left',
  	width:15
  },
  {
  	columnId:"countType",
  	columnName:'<fmt:message key="label.counttype"/>',
  	onChange:countTypeChanged,
  	type:'hcoro',
  	width:8
  },
  {
  	columnId:"showBinPartStatus",
  	columnName:'<fmt:message key="label.active"/>',
  	type:'hchstatus',
  	onChange:changeBinPartStatus,
    align:'center',
    width:4
  },
  {
  	columnId:"catalogDesc",
  	columnName:'<fmt:message key="label.catalog"/>'
  },
  {
  	columnId:"catPartNo",
  	columnName:'<fmt:message key="label.partnumber"/>'
  },
  {
  	columnId:"partDescription",
  	columnName:'<fmt:message key="label.partdescription"/>',
  	tooltip:"Y",  
    width:15
  },
  {
  	columnId:"reorderPointStockingLevel",
  	columnName:'<fmt:message key="label.rpslrq"/>',
	align:'center'  
  },
  {
  	columnId:"kanbanReorderQuantity",
  	columnName:'<fmt:message key="label.kanbanreorderqty"/>',
	align:'right',
	 width:4  
  },
  {
    columnId:"leadTimeInDays",
    columnName:'<fmt:message key="label.leadtimeindays"/>',
    align:'right'
  },
  {
  	columnId:"itemId",
  	columnName:'<fmt:message key="label.item"/>',
  	sorting:'int',
    width:5
  },
  {
  	columnId:"materialDesc",
  	columnName:'<fmt:message key="label.description"/>',
  	tooltip:"Y",  
    width:15
  },
  {
  	columnId:"packaging",
  	columnName:'<fmt:message key="label.containersize"/>',
  	tooltip:"Y",
    width:15
  },
  {
  	columnId:"mfgDesc",
  	columnName:'<fmt:message key="label.manufacturer"/>',
    tooltip:"Y",
    width:15  
  },
  {
  	columnId:"msdsString",
  	columnName:'<fmt:message key="label.msdsnumber"/>'
  },
  {
	columnId:"avgAmountPermission"
  },
  {
  	columnId:"maxAmountPermission"
  },
  {
	 columnId:"largestContainerSizePermission"
  },
  {
  	columnId:"sizeUnitPermission"
  },
  {
  	columnId:"avgAmount",
  	columnName:'<fmt:message key="label.averageamount"/>',
  	type:'hed',
  	onChange:setChanged,
  	width:6, // the width of this cell
  	size:6  // the size of the input field
  },
  {
  	columnId:"maxAmount",
  	columnName:'<fmt:message key="label.maxamount"/>',
  	type:'hed',
  	onChange:setChanged,
  	width:6, // the width of this cell
  	size:6  // the size of the input field
  },
  {
  	columnId:"largestContainerSize",
  	columnName:'<fmt:message key="label.largestcontainersize"/>',
  	type:'hed',
  	onChange:setChanged,
  	width:6, // the width of this cell
  	size:6  // the size of the input field
  },
  {
  	columnId:"sizeUnit",
  	columnName:'<fmt:message key="label.unit"/>',
  	type:'hcoro',
  	onChange:setChanged,
  	width:10
  },
  {
  	columnId:"tierIIStoragePermission"
  },
  {
	columnId:"tierIITemperaturePermission"
  },

  {columnId: "tierIIStorage", columnName:'<fmt:message key="label.tieriistoragemethod"/>', attachHeader:'<fmt:message key="label.container"/>', type:"hcoro", width:10, onChange:setChanged},

  {columnId: "tierIIPressure", columnName:'#cspan', attachHeader:'<fmt:message key="label.pressure"/>', align:"center",width:10},
  
  {columnId: "tierIITemperature", columnName:'#cspan', attachHeader:'<fmt:message key="label.temperature"/>', type:"hcoro", align:"center",width:10, onChange:setChanged},
  {
  	columnId:"defaultPartTypePermission"
  },
  {
  	columnId:"defaultPermitIdPermission"
  },
   {
  	columnId:"defaultApplicationMethodCodPermission"
  },
   {
  	columnId:"defaultSubstrateCodePermission"
  },
  {columnId:"defaultPartType"
  <c:if test="${'Y' == showDefault}">
  , columnName:'<fmt:message key="label.defaultparttype"/>', type:"hcoro", align:"center", width:10, onChange:setChanged
  </c:if>
  },
  {columnId:"defaultPermitId"
  <c:if test="${'Y' == showDefault}">
	, columnName:'<fmt:message key="label.defaultpermit"/>', type:"hcoro", onChange:setChanged, align:"center", width:12
  </c:if>
  },
  {columnId:"defaultApplicationMethodCod"
 <c:if test="${'Y' == showDefault}">
  	,columnName:'<fmt:message key="label.defaultapplicationmethod"/>', type:"hcoro", onChange:setChanged, align:"center", width:18
  </c:if>
  },
  {columnId:"defaultSubstrateCode"
  <c:if test="${'Y' == showDefault}">
  	,columnName:'<fmt:message key="label.defaultsubstrate"/>', type:"hcoro", onChange:setChanged, align:"center", width:18
  </c:if>
  },
  {
    columnId:"oldCountType" 
  },
  {
    columnId:"applicationId"
  },
  {
    columnId:"oldStatus" 
  },
  {
    columnId:"binId"
  },
  {
    columnId:"binName"
  },
  {
    columnId:"companyId" 
  },
  {
    columnId:"partGroupNo"
  },
  {
    columnId:"catalogCompanyId" 
  },
  {
    columnId:"status"
  },
  {
    columnId:"catalogId"
  },
  {
    columnId:"facilityId"
  },
  {
    columnId:"facilityName"
  },
  {
    columnId:"application"
  },
  {
    columnId:"binPartStatus"
  },
  {
    columnId:"oldBinPartStatus"
  },
  {
    columnId:"inventoryGroup"
  },
  {
    columnId:"haasMaterialIdString"
  },
  {
    columnId:"materialId"
  },
  {
    columnId:"nonManaged"
  },
  {
    columnId:"changed"
  },
  {
	columnId:"materialIdString"
  },
  {
	columnId:"ownershipName",
    <c:if test="${homeCompanyOwned}">
        columnName:'<fmt:message key="label.ownership"/>',
    </c:if>
    tooltip:"Y",
    width:15
  },
  {
	columnId:"hcoFlag"
  },

  {
	columnId:"lastCountType"
  },
  {
  	columnId:"dropShipOverridePermission"
  },
  {
    columnId:"dropShipOverride",
    columnName:'<fmt:message key="label.dropship"/>',
    type:"hchstatus",
    align:"center",
    onChange:setChanged,  
    width:4
  },
  {
	columnId:"binSizeUnitPermission"  
  },
  {
	columnId:"binSizeUnit",
	<tcmis:featureReleased feature="ShowCountTypeLevel" scope="West Point">
		columnName:'<fmt:message key="label.levelunit"/>',
	</tcmis:featureReleased>
	type:"hcoro",
	onChange:setChanged,
	align:"center",
	width:8
  },
  {
	  columnId:"haasMaterialIdString"
  }
];

var tierIIStorage = new Array(
{value:'', text: '<fmt:message key="label.pleaseselect"/>'}
<c:forEach var="bean" items="${vvUnidocsStorageContainerBeanColl}" varStatus="unidocsStorageStatus">
	, {value:'${bean.unidocsStorageContainer}', text: '<tcmis:jsReplace value="${bean.description}" processMultiLines="false" />'}
</c:forEach>
);

var tierIITemperature = new Array(
{value:'', text: '<fmt:message key="label.pleaseselect"/>'}
<c:forEach var="bean" items="${vvTierIITemperatureCodeBeanColl}" varStatus="vvTierIITemperatureCodeStatus">
, {value:'${bean.tierIITemperatureCode}', text: '<tcmis:jsReplace value="${bean.tierIITemperature}" processMultiLines="false" />'}
</c:forEach>
);

var binSizeUnit= new Array(
{value:'', text: ''}
<c:forEach var="bean" items="${vvSizeUnitBeanCollection}" varStatus="levelUnitStatus">
	<c:if test="${bean.unitType eq 'length e' or bean.unitType eq 'length m' or 
		bean.unitType eq 'volume e' or bean.unitType eq 'volume m' or 
		bean.unitType eq 'weight e' or bean.unitType eq 'weight m'}">
		, {value:'${bean.sizeUnit}', text: '<tcmis:jsReplace value="${bean.sizeUnit}" processMultiLines="false" />'}
	</c:if>
</c:forEach>
);

<c:set var="prevFacilityId" value=''/>
var userFacilityHubArr = {};
<c:forEach var="bean" items="${userFacilityHub}" varStatus="userFacilityHubStatus">
	<c:choose>
		<c:when test="${prevFacilityId != bean.facilityId}">
				userFacilityHubArr["${bean.facilityId}"] = new Array("${bean.hubName}");
		</c:when>
		<c:otherwise>
				userFacilityHubArr["${bean.facilityId}"].push("${bean.hubName}");
		</c:otherwise>
	</c:choose>
	<c:set var="prevFacilityId" value='${bean.facilityId}'/>
</c:forEach>

 			
var sizeUnit = new Array();
var countType = new Array();
var defaultPartType = new Array();
var defaultPermitId = new Array();
var defaultApplicationMethodCod = new Array();
var defaultSubstrateCode = new Array();
var nonStockedPermissionForWorkArea = false;
var inventoryPartMgmt = false;

function buildsizeUnitDropdownArray(sizeUnitOption) {
	  var splitResult = sizeUnitOption.split("; ");
	  var sizeUnitArray = new Array();
	  
	  if(sizeUnitOption == null || sizeUnitOption.length == 0) {
	    sizeUnitArray[0] = {text:'',value:''};
	  }
	  else {
	  	  sizeUnitArray[0] = {text:messagesData.pleaseselect,value:''};
		  for(i = 0; i < splitResult.length; i++){
		  	var j = i + 1;
			sizeUnitArray[j] = {text:splitResult[i],value:splitResult[i]};
	  	  }
	  }
	 
	  return sizeUnitArray;
}

with ( milonic=new menuname("workAreaPartRightClickMenu") ) {
 top="offset=2";
 style=contextStyle;
 margin=3
 aI("text");
}

with(milonic=new menuname("itemRightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
	 aI("showmenu=showMsdsSub;text=<fmt:message key="label.viewmsds"/>;");
}


with(milonic=new menuname("showMsdsSub")){
	style=submenuStyle;
	itemheight=17;
	//has to have at least one menu item, this is a place holder.
	//we will dynamically add items in javascript
	aI("text=test;");
}

with(milonic=new menuname("showMsds")){
	style=submenuStyle;
	itemheight=17;
	//has to have at least one menu item, this is a place holder.
	//we will dynamically add items in javascript
	aI("text=test;");
}

with(milonic=new menuname("showEmpty")){
	style=submenuStyle;
	itemheight=17;
	//has to have at least one menu item, this is a place holder.
	//we will dynamically add items in javascript
	aI("text=");
}

with(milonic=new menuname("componentMSDS")){
	style=submenuStyle;
	itemheight=17;
	//has to have at least one menu item, this is a place holder.
	//we will dynamically add items in javascript
	aI("text=");
}

drawMenus();

var map = null;
var lineMap = new Array();
var lineMap2 = new Array();
var lineMap3 = new Array();

// -->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/clientcabinetmanagementresults.do" onsubmit="return submitFrameOnlyOnce();">

		<%-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
		     The default value of showUpdateLinks is false.--%>
		<c:set var="userHasPermission" value='N'/>
	<tcmis:applicationPermission indicator="true" userGroupId="StockedPartMgmt" companyId="${param.companyId}" facilityId="${param.facilityId}">
			<c:set var="userHasPermission" value='Y'/>
			<script type="text/javascript">
				<!--
                    showUpdateLinks = true;
                    <c:if test="${hasStockPartMgmtPermission eq 'Y'}">
                        inventoryPartMgmt = true;
                    </c:if>
				 //-->
			</script>
	</tcmis:applicationPermission>
	<tcmis:applicationPermission indicator="true" userGroupId="NonStockedPartMgmt" companyId="${param.companyId}" facilityId="${param.facilityId}">
			<c:set var="userHasPermission" value='Y'/>
			<script type="text/javascript">
				<!--
                    showUpdateLinks = true;
                    <c:if test="${hasNonStockPartMgmtPermission eq 'Y'}">
                        nonStockedPermissionForWorkArea = true;
                    </c:if>
				//-->
			</script>
	</tcmis:applicationPermission>

		<tcmis:facilityPermission indicator="true" userGroupId="ChargeNumberSetup" companyId="${param.companyId}" facilityId="${param.facilityId}">
			<script type="text/javascript">
				<!--
				chargeNumberSetup = true;
 				//-->
			</script>
		</tcmis:facilityPermission>

		<tcmis:facilityPermission indicator="true" userGroupId="DirectedChargeAssignment" companyId="${param.companyId}" facilityId="${param.facilityId}">
			<script type="text/javascript">
				<!--
				directedChargeAssignment = true;
 				//-->
			</script>
		</tcmis:facilityPermission>

<div id="errorMessagesAreaBody" style="display:none;">
     ${tcmISError}<br/>
    <c:forEach var="tcmisError" items="${tcmISErrors}">
      ${tcmisError}<br/>
    </c:forEach>
    <c:if test="${param.maxData == fn:length(cabinetPartLevelViewBeanCollection)}">
     <fmt:message key="label.maxdata">
       <fmt:param value="${param.maxData}"/>
     </fmt:message>
     &nbsp;<fmt:message key="label.clickcreateexcelforcompleteresult"/>
    </c:if>
</div>


<script type="text/javascript">
    <c:choose>
    <c:when test="${empty tcmISErrors and empty tcmISError}">
     <c:choose>
      <c:when test="${param.maxData == fn:length(cabinetPartLevelViewBeanCollection)}">
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

<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="cabinetPartLevelViewBean" style="width:100%;height:400px;" style="display: none;"></div>

<script type="text/javascript">
<!--

<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty cabinetPartLevelViewBeanCollection}" >

var countTypeContainer = [
			{value:'ADVRECEIPT', text: '<fmt:message key="label.Advancedreceipt"/>'},
			{value:'CONTAINER', text: '<fmt:message key="label.containerid"/>'},
			{value:'ITEM_ID', text: '<fmt:message key="label.itemid"/>'},
			{value:'NotCounted', text: '<fmt:message key="label.notcounted"/>'}
	];

var countTypePart = [
            {value:'PART', text: '<fmt:message key="label.partno"/>'}
         ];

var countTypeAdvReceipt = [
            {value:'ADVRECEIPT', text: '<fmt:message key="label.Advancedreceipt"/>'},
			{value:'CONTAINER', text: '<fmt:message key="label.containerid"/>'},
			{value:'ITEM_ID', text: '<fmt:message key="label.itemid"/>'},
            {value:'NotCounted', text: '<fmt:message key="label.notcounted"/>'}
	];
         
var countTypeReceipt = [
            {value:'RECEIPT', text: '<fmt:message key="label.receipt"/>'}
         ];

var countTypeDisbursement = [
            {value:'DISBURSEMENT', text: '<fmt:message key="label.disbursement"/>'}
         ];

var countTypeLevel = [
            {value:'LEVEL', text: '<fmt:message key="label.level"/>'},
            {value:'NotCounted', text: '<fmt:message key="label.notcounted"/>'}
         ];


var countTypeNotCounted = [
			{value:'ADVRECEIPT', text: '<fmt:message key="label.Advancedreceipt"/>'},
			{value:'CONTAINER', text: '<fmt:message key="label.containerid"/>'},
			{value:'ITEM_ID', text: '<fmt:message key="label.itemid"/>'},
            {value:'RECEIPT', text: '<fmt:message key="label.receipt"/>'},
			{value:'NotCounted', text: '<fmt:message key="label.notcounted"/>'}
	];
	
<c:forEach var="bean" items="${cabinetPartLevelViewBeanCollection}" varStatus="status">
	<c:choose>
	    <c:when test="${bean.countType == 'CONTAINER'}" >
	         countType[${status.count}] = countTypeContainer;
	    </c:when>
	    <c:when test="${bean.countType == 'PART'}" >
	         countType[${status.count}] = countTypePart;
	    </c:when>
	    <c:when test="${bean.countType == 'ADVRECEIPT'}" >
    	    countType[${status.count}] = countTypeAdvReceipt;
	   </c:when>
	    <c:when test="${bean.countType == 'RECEIPT'}" >
	         countType[${status.count}] = countTypeReceipt;
	    </c:when>
	    <c:when test="${bean.countType == 'LEVEL'}" >
	         countType[${status.count}] = countTypeLevel;
	    </c:when>
	    <c:when test="${bean.countType == 'DISBURSEMENT'}" >
    	    countType[${status.count}] = countTypeDisbursement; 
	   </c:when>
	   <c:when test="${bean.countType == 'NotCounted'}" >
			countType[${status.count}] = countTypeNotCounted; 
	   </c:when>
	    <c:otherwise>
	        countType[${status.count}] = [
	            <c:if test="${bean.allowStocking == 'Y'}" >
	                <c:set var="tmpCount" value='${0}'/>
	                <c:forEach var="countTypeBean" items="${countTypeDropDownList}" varStatus="status2">
	                    <c:if test="${countTypeBean.value == 'CONTAINER'}" >
	                        <c:if test="${param.facilityId == 'STL Prod' && (bean.countType == 'ITEM_ID' || bean.countType == 'CONTAINER' || bean.countType == 'NotCounted')}">
	                            <c:if test="${tmpCount > 0}">,</c:if>
	                            {value:'${countTypeBean.value}', text: '<tcmis:jsReplace value="${countTypeBean.label}" />'}
	                            <c:set var="tmpCount" value='${tmpCount+1}'/>
	                        </c:if>
	                    </c:if>
	                    <c:if test="${countTypeBean.value == 'ADVRECEIPT'}" >
	                    	// bean.countType == '${bean.countType}'
		                    <c:if test="${bean.countType == 'ITEM_ID' || bean.countType == 'CONTAINER' || bean.countType == 'NotCounted'}">
			                    <c:if test="${tmpCount > 0}">,</c:if>
			                    {value:'${countTypeBean.value}', text: '<tcmis:jsReplace value="${countTypeBean.label}" />'}
			                    <c:set var="tmpCount" value='${tmpCount+1}'/>
			                </c:if>
	                    </c:if>
	                    <c:if test="${countTypeBean.value != 'CONTAINER' && countTypeBean.value != 'PART' && countTypeBean.value != 'RECEIPT' && countTypeBean.value != 'LEVEL' && countTypeBean.value != 'ADVRECEIPT'}">
	                        <c:choose>
	                            <c:when test="${bean.hcoFlag == 'Y'}">
	                                <c:if test="${countTypeBean.value != 'KanBan'}">
	                                    <c:if test="${tmpCount > 0}">,</c:if>
	                                    {value:'${countTypeBean.value}', text: '<tcmis:jsReplace value="${countTypeBean.label}"/>'}
	                                    <c:set var="tmpCount" value='${tmpCount+1}'/>
	                                </c:if>
	                            </c:when>
	                            <c:otherwise>
	                                <c:if test="${tmpCount > 0}">,</c:if>
	                                {value:'${countTypeBean.value}', text: '<tcmis:jsReplace value="${countTypeBean.label}"/>'}
	                                <c:set var="tmpCount" value='${tmpCount+1}'/>
	                            </c:otherwise>
	                        </c:choose>
	                    </c:if>
	                </c:forEach>
	            </c:if>
	            <c:if test="${bean.allowStocking != 'Y'}" >
	                <c:forEach var="countTypeBean" items="${countTypeDropDownList}" varStatus="status2">
	                    <c:if test="${countTypeBean.value == 'NotCounted'}" >
	                       {value:'${countTypeBean.value}', text:'<tcmis:jsReplace value="${countTypeBean.label}"/>'}
	                    </c:if>
	                </c:forEach>
	            </c:if>
	        ];
	    </c:otherwise>
    </c:choose>

    sizeUnit[${status.count}] = buildsizeUnitDropdownArray('${bean.sizeUnitOption}');
	<c:if test="${'Y' == showDefault}">
		defaultPartType[${status.count}] = [
				{value:'', text: '<fmt:message key="label.pleaseselect"/>'},
				{value:'F', text: '<fmt:message key="label.aerospace"/>'},
				{value:'R', text: '<fmt:message key="label.aircraftrework"/>'},
				{value:'N', text: '<fmt:message key="label.nonaerospace"/>'}
			];
		defaultPermitId[${status.count}] = [
				{value:'', text: '<fmt:message key="label.pleaseselect"/>'},
				<c:forEach var="permit" items="${bean.permits}" varStatus="status1">
					{value:'<tcmis:jsReplace value="${permit.permitId}" />', text: '<tcmis:jsReplace value="${permit.permitName}" />'},
				</c:forEach>
				{value:'NONE', text: '<fmt:message key="label.none"/>'}
			]; 
			
		defaultApplicationMethodCod[${status.count}] = [
	 			{value:'', text: '<fmt:message key="label.pleaseselect"/>'}
				<c:forEach var="method" items="${bean.applicationMethods}" varStatus="status2">
					<c:choose>
					<c:when test="${bean.solvent && method.forSolvent}">
						,{value:'<tcmis:jsReplace value="${method.methodCode}" />', text: '<tcmis:jsReplace value="${method.method}" />'}
					</c:when>
					<c:when test="${!bean.solvent}">
						,{value:'<tcmis:jsReplace value="${method.methodCode}" />', text: '<tcmis:jsReplace value="${method.method}" />'}
					</c:when>
					<c:otherwise></c:otherwise>
				</c:choose>

				</c:forEach>
			];
			
		defaultSubstrateCode[${status.count}] = [
				{value:'', text: '<fmt:message key="label.pleaseselect"/>'}
	 			<c:forEach var="substrate" items="${bean.substrates}" varStatus="status3">
	 				,{value:'<tcmis:jsReplace value="${substrate.substrateCode}" />', text: '<tcmis:jsReplace value="${substrate.substrate}" />'}
	 			</c:forEach>
	 		];
		
	</c:if>
</c:forEach>

<c:set var="stockedPermission" value='N'/>
<c:set var="nonStockedPermission" value='N'/>
<c:set var="lastWorkArea" value=""/>
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="cabinetPartLevelViewBean" items="${cabinetPartLevelViewBeanCollection}" varStatus="status1">
<c:if test="${status1.index > 0}">,</c:if>

<c:set var="rowPermission" value="N"/>
<c:if test="${userHasPermission == 'Y'}">
	<c:if test="${(!empty cabinetPartLevelViewBean.binId && cabinetPartLevelViewBean.nonManaged == 'N') || cabinetPartLevelViewBean.nonManaged == 'Y'}">
		<c:set var="rowPermission" value="Y"/>
	</c:if>
</c:if>

/* Please use this tag, tcmis:jsReplace, to escape special characters */
<tcmis:jsReplace var="materialDesc" value="${cabinetPartLevelViewBean.materialDesc}" processMultiLines="true" />
<tcmis:jsReplace var="packaging" value="${cabinetPartLevelViewBean.packaging}" processMultiLines="true" />
<tcmis:jsReplace var="tmpWorkArea" value="${cabinetPartLevelViewBean.cabinetName}"/>

<c:if test="${lastWorkArea != cabinetPartLevelViewBean.orderingApplication}">
    <c:set var="stockedPermission" value='N'/>
    <c:set var="nonStockedPermission" value='N'/>    
    <tcmis:applicationPermission indicator="true" userGroupId="StockedPartMgmt" companyId="${param.companyId}" facilityId="${param.facilityId}" application="${cabinetPartLevelViewBean.orderingApplication}">
    <c:set var="stockedPermission" value='Y'/>
    </tcmis:applicationPermission>

    <tcmis:applicationPermission indicator="true" userGroupId="NonStockedPartMgmt" companyId="${param.companyId}" facilityId="${param.facilityId}" application="${cabinetPartLevelViewBean.orderingApplication}">
    <c:set var="nonStockedPermission" value='Y'/>
    </tcmis:applicationPermission>
</c:if>
<c:set var="lastWorkArea" value='${cabinetPartLevelViewBean.orderingApplication}'/>

<c:set var="nonStockedEditPermission" value='N'/>
<c:if test="${nonStockedPermission == 'Y' && cabinetPartLevelViewBean.nonManaged == 'Y'}">
	<c:set var="nonStockedEditPermission" value='Y'/>
</c:if>

<c:set var="tierTempPermission" value='N'/>
<c:if test="${(stockedPermission == 'Y' && cabinetPartLevelViewBean.nonManaged == 'N') || (nonStockedPermission == 'Y' && cabinetPartLevelViewBean.nonManaged == 'Y')}">
	<c:set var="tierTempPermission" value='Y'/>
</c:if>

<c:set var="countType" value="${cabinetPartLevelViewBean.countType}"/>
<c:if test="${empty cabinetPartLevelViewBean.countType}">
	<c:set var="countType" value=" "/>
</c:if>

<c:set var="notCountedEditPermission" value='${nonStockedEditPermission}'/>
<c:if test="${countType eq 'NotCounted' and stockedPermission eq 'Y'}">
	<c:set var="notCountedEditPermission" value="Y"/>
</c:if>

<c:set var="showBinPartStatus" value="false"/>
<c:if test="${cabinetPartLevelViewBean.binPartStatus == 'A'}">
	<c:set var="showBinPartStatus" value="true"/>
</c:if>

<c:set var="rpSlRq" value="${cabinetPartLevelViewBean.reorderPoint} / ${cabinetPartLevelViewBean.stockingLevel} / ${cabinetPartLevelViewBean.reorderQuantity}"/>
<c:if test="${cabinetPartLevelViewBean.nonManaged == 'Y'}">
	<c:set var="rpSlRq" value=""/>
</c:if>

<c:set var="defaultPartType" value="SELECT"/>
<c:if test="${cabinetPartLevelViewBean.defaultPartType != null}">
	<c:set var="defaultPartType" value="${cabinetPartLevelViewBean.defaultPartType}"/>
</c:if>

<c:set var="defaultPermitId" value="SELECT"/>
<c:if test="${cabinetPartLevelViewBean.defaultPermitId != null}">
	<c:set var="defaultPermitId" value="${cabinetPartLevelViewBean.defaultPermitId}"/>
</c:if>

<c:set var="defaultApplicationMethodCod" value="SELECT"/>
<c:if test="${cabinetPartLevelViewBean.defaultApplicationMethodCod != null}">
	<c:set var="defaultApplicationMethodCod" value="${cabinetPartLevelViewBean.defaultApplicationMethodCod}"/>
</c:if>

<c:set var="defaultSubstrateCode" value="SELECT"/>
<c:if test="${cabinetPartLevelViewBean.defaultSubstrateCode != null}">
	<c:set var="defaultSubstrateCode" value="${cabinetPartLevelViewBean.defaultSubstrateCode}"/>
</c:if>

<c:set var="dropShipPermission" value='N'/>
<c:if test="${stockedPermission eq 'Y' and cabinetPartLevelViewBean.hcoFlag == 'N' and cabinetPartLevelViewBean.nonManaged == 'N'}">
	<c:set var="dropShipPermission" value="Y"/>
</c:if>
<c:set var="showDropShipOverride" value="false"/>
<c:if test="${cabinetPartLevelViewBean.dropShipOverride == 'Y'}">
	<c:set var="showDropShipOverride" value="true"/>
</c:if>
<c:set var="binSizeUnitPermission" value="N"/>
<c:if test="${rowPermission eq 'Y' and countType eq 'LEVEL'}">
	<c:set var="binSizeUnitPermission" value="Y"/>
</c:if>

/*The row ID needs to start with 1 per their design.*/
{ id:${status1.index +1},
  data:['${rowPermission}',
   <c:choose>
    <c:when test="${cabinetPartLevelViewBean.nonManaged == 'Y'}">
	    <c:choose>
		    <c:when test="${nonStockedPermission == 'Y'}">
		     	'N','N','Y',
		     </c:when>
		     <c:otherwise>
		    	'N','N','N',
		     </c:otherwise>
	     </c:choose> 
    </c:when>
    <c:otherwise>
		  <c:choose>
		    <c:when test="${stockedPermission == 'Y'}">
	     		'Y','N','Y',
		     </c:when>
		     <c:otherwise>
		    	'N','N','N',
		     </c:otherwise>
		 </c:choose> 

    </c:otherwise>
  </c:choose> 
  '${cabinetPartLevelViewBean.cabinetId}','${tmpWorkArea}',
  '${countType}',
   ${showBinPartStatus},'<tcmis:jsReplace value="${cabinetPartLevelViewBean.catalogDesc}" processAmpersand="true" />',
  '<tcmis:jsReplace value="${cabinetPartLevelViewBean.catPartNo}" processAmpersand="true" />',
  '<tcmis:jsReplace value="${cabinetPartLevelViewBean.partDescription}" processAmpersand="true" processMultiLines="true" />',
  '${rpSlRq}',
  '${cabinetPartLevelViewBean.kanbanReorderQuantity}',		
  '${cabinetPartLevelViewBean.leadTimeDays}',
  '${cabinetPartLevelViewBean.itemId}','${materialDesc}',
  '<tcmis:jsReplace value="${cabinetPartLevelViewBean.packaging}" processMultiLines="true" />',
  '<tcmis:jsReplace value="${cabinetPartLevelViewBean.mfgDesc}" processMultiLines="true"/>',        
  '${cabinetPartLevelViewBean.msdsString}',
  '${notCountedEditPermission}',
  '${notCountedEditPermission}',
  '${nonStockedEditPermission}',
  '${nonStockedEditPermission}',
  '${cabinetPartLevelViewBean.avgAmount}',
  '${cabinetPartLevelViewBean.maxAmount}',
  '${cabinetPartLevelViewBean.largestContainerSize}',
  '${cabinetPartLevelViewBean.sizeUnit}',
  '${nonStockedEditPermission}',
  '${tierTempPermission}',
  '<c:out value="${fn:substring(cabinetPartLevelViewBean.tierIIStorage,0,1)}"/>',
  '<c:out value="${fn:substring(cabinetPartLevelViewBean.tierIIPressure,2,fn:length(cabinetPartLevelViewBean.tierIIPressure))}"/>',
  '<c:out value="${fn:substring(cabinetPartLevelViewBean.tierIITemperature,0,1)}"/>',
  <c:choose>
	<c:when test="${'Y' == showDefault && cabinetPartLevelViewBean.nonManaged == 'Y'}">
	  'N','N','N','N',
	  '',
	  '',
	  '',
	  '',
	</c:when>
	<c:when test="${'Y' == showDefault && cabinetPartLevelViewBean.nonManaged != 'Y' && stockedPermission == 'Y'}">
	  'Y','Y','Y','Y',
	  '${defaultPartType}',
	  '${defaultPermitId}',
	  '${defaultApplicationMethodCod}',
	  '${defaultSubstrateCode}',
	</c:when> 
	<c:when test="${'Y' == showDefault && cabinetPartLevelViewBean.nonManaged != 'Y' && stockedPermission == 'N'}">
	  'N','N','N','N',
	  '${defaultPartType}',
	  '${defaultPermitId}',
	  '${defaultApplicationMethodCod}',
	  '${defaultSubstrateCode}',
	</c:when>   
	<c:otherwise>
	  'N','N','N','N',
	  '',
	  '',
	  '',
	  '',
	</c:otherwise>
</c:choose>
  '${countType}','${cabinetPartLevelViewBean.cabinetId}',
  '${cabinetPartLevelViewBean.status}',
  '${cabinetPartLevelViewBean.binId}',//binId
  '${cabinetPartLevelViewBean.binName}',
  '${cabinetPartLevelViewBean.companyId}',
  '${cabinetPartLevelViewBean.partGroupNo}',
  '${cabinetPartLevelViewBean.catalogCompanyId}',
  '${cabinetPartLevelViewBean.status}',
  '${cabinetPartLevelViewBean.catalogId}',
  '<tcmis:jsReplace value="${cabinetPartLevelViewBean.facilityId}" processAmpersand="true" />',
  '<tcmis:jsReplace value="${cabinetPartLevelViewBean.facilityName}" processAmpersand="true" />',
  '${cabinetPartLevelViewBean.orderingApplication}',
  '${cabinetPartLevelViewBean.binPartStatus}',
  '${cabinetPartLevelViewBean.binPartStatus}',
  '${cabinetPartLevelViewBean.inventoryGroup}',
  '${cabinetPartLevelViewBean.haasMaterialIdString}',
  '${cabinetPartLevelViewBean.materialId}',
  '${cabinetPartLevelViewBean.nonManaged}',
  'N',
  '${cabinetPartLevelViewBean.materialIdString}',
  '${cabinetPartLevelViewBean.ownershipName}',
  '${cabinetPartLevelViewBean.hcoFlag}',
  '${countType}',
  '${dropShipPermission}',
  ${showDropShipOverride},
  '${binSizeUnitPermission}',
  '${cabinetPartLevelViewBean.binSizeUnit}',
  '${cabinetPartLevelViewBean.haasMaterialIdString}'
  ]}
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>

//-->  
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty cabinetPartLevelViewBeanCollection}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
    <tr>
       <td width="100%">
          <fmt:message key="main.nodatafound"/>
       </td>
    </tr>
  </table>
</c:if>
        
<!-- Search results end -->

<!-- Hidden element start -->

<div id="ShowHubsWinArea" class="optionTitleBoldCenter" style="display: none;width:100%; height: 100%; overflow: none;">
<table  border="0" cellpadding="0" cellspacing="0"  style="margin: 0px auto;">
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
            <td>
                &nbsp;<fmt:message key="label.pleaseselectahub"/>:
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;<select id="hubSel" styleClass="selectBox"/>
            </td>
        </tr>
         <tr>
			<td>
				&nbsp;
            </td>
		</tr>
        <tr>
            <td>
                <button name="submitSearch" id="submitSearch" type="button" class="inputBtns" value="select" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="showHubNamesWin.setModal(false);showHubNamesWin.hide();hubNotChoosen=false;createWorkAreaLabels(null,$v('hubSel'));"><fmt:message key="label.ok"/></button>
            </td>
        </tr>
	</table>
</div>
	
 
<div id="hiddenElements" style="display: none;">    			
	<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden">
    <input name="uAction" id="uAction" value="" type="hidden">
    <input name="minHeight" id="minHeight" type="hidden" value="100">
      
  <!-- Put all the Search Criteria here for re-search after update-->
  
	<input name="applicationId" id="applicationId" value="${param.applicationId}" type="hidden"/>
	<input name="branchPlant" id="branchPlant" value="<c:out value="${param.branchPlant}"/>" type="hidden">
	<input name="itemOrPart" id="itemOrPart" value="<c:out value="${param.itemOrPart}"/>" type="hidden">
	<input name="criterion" id="criterion" value="<c:out value="${param.criterion}"/>" type="hidden">
	<input name="criteria" id="criteria" value="<c:out value="${param.criteria}"/>" type="hidden">
	<input name="companyId" id="companyId" value="<c:out value="${param.companyId}"/>" type="hidden">
	<input name="facilityId" id="facilityId" value="<c:out value="${param.facilityId}"/>" type="hidden">
	<input name="facilityName" id="facilityName" value="<c:out value="${param.facilityName}"/>" type="hidden">
	<input name="cabinetId" id="cabinetId" value="" type="hidden">
	<input name="materialId" id="materialId" value="" type="hidden">
	<input name="catalogId" id="catalogId" value="" type="hidden">
	<input name="catalogCompanyId" id="catalogCompanyId" value="" type="hidden">
	<input name="msdsNumber" id="msdsNumber" value="" type="hidden">
	<input name="avgAmount" id="avgAmount" value="" type="hidden">
	<input name="maxAmount" id="maxAmount" value="" type="hidden">
	<input name="sizeUnit" id="sizeUnit" value="" type="hidden">
	<input name="tierIIStorage" id="tierIIStorage" value="" type="hidden">
	<input name="tierIITemperature" id="tierIITemperature" value="" type="hidden">
	<input name="binPartStatus" id="binPartStatus" value="" type="hidden">		
	<input name="sortBy" id="sortBy" value="<c:out value="${param.sortBy}"/>" type="hidden">
	<input name="reportingEntityId" id="reportingEntityId" value="<c:out value="${param.reportingEntityId}"/>" type="hidden">
	<input name="inactive" id="inactive" value="<c:out value="${param.inactive}"/>" type="hidden">
	<input name="sourcePage" id="sourcePage" type="hidden" value="clientCabinetManagement">
	<input name="showDefault" id="showDefault" type="hidden" value="${showDefault}">  
	<input name="workAreaSelectCount" id="workAreaSelectCount" type="hidden" value="${param.workAreaSelectCount}">
	<input name="countTypeArray" id="countTypeArray" type="hidden" value="${param.countTypeArray}">
    <input name="largestContainerSize" id="largestContainerSize" value="" type="hidden">
    <input name="deptId" id="deptId" value="${param.deptId}" type="hidden">
    <input name="areaId" id="areaId" value="${param.areaId}" type="hidden">
    <input name="buildingId" id="buildingId" value="${param.buildingId}" type="hidden">
    <input name="maxData" id="maxData" type="hidden" value="${param.maxData}">
	  
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>

<c:set var="preFirstLevel" value=""/>
<c:set var="parCount" value="1"/>
<c:forEach var="tmpBean" items="${cabinetPartLevelViewBeanCollection}" varStatus="status">
	<c:set var="curFirstLevel">${status.current.cabinetId}</c:set>
	<c:set var="curSecondLevel">secondLevel${status.current.cabinetId}</c:set>

    <c:choose>
        <c:when test="${!empty status.current.catPartNo}">
            <c:if test="${!empty status.current.binId}">
                <c:set var="curFirstLevel">${status.current.cabinetId}${status.current.binId}</c:set>
                <c:set var="curSecondLevel">secondLevel${status.current.cabinetId}${status.current.binId}</c:set>
            </c:if>
            <c:if test="${!empty status.current.catPartNo}">
                <c:set var="curFirstLevel">${status.current.cabinetId}${status.current.binId}${status.current.catPartNo}</c:set>
                <c:set var="curSecondLevel">secondLevel${status.current.cabinetId}${status.current.binId}${status.current.catPartNo}</c:set>
            </c:if>
            <c:if test="${!empty status.current.itemId}">
                <c:set var="curSecondLevel">secondLevel${status.current.cabinetId}${status.current.binId}${status.current.catPartNo}${status.current.itemId}</c:set>
            </c:if>

              <script language="JavaScript" type="text/javascript">
                <!--
                    <c:if test="${!(curFirstLevel eq preFirstLevel)}">
                        <c:set var="parCount" value="${parCount+1}"/>
                        <c:set var="preSecondLevel" value=""/>
                        lineMap[${status.index}] = ${rowCountFirstLevel[curFirstLevel]} ;
                        map = new Array();
                    </c:if>
                    <c:if test="${ !( curSecondLevel eq preSecondLevel)}">
                        <c:set var="componentSize" value='${rowCountSecondLevel[curFirstLevel][curSecondLevel]}' />
                        lineMap2[${status.index}] = ${componentSize} ;
                    </c:if>
                    lineMap3[${status.index}] = ${parCount}%2 ;
                    map[map.length] =  ${status.index} ;
                // -->
                </script>

                <c:set var="preFirstLevel" value="${curFirstLevel}"/>
                <c:set var="preSecondLevel" value="${curSecondLevel}"/>
        </c:when>
        <c:otherwise>
            <%-- non managed material - no merging cells--%>
            <script language="JavaScript" type="text/javascript">
            <!--
               lineMap[${status.index}] = 1;
               map = new Array();
               lineMap2[${status.index}] = 1;
               lineMap3[${status.index}] = ${status.index} % 2;
               map[map.length] = ${status.index} ;
            // -->
            </script>

        </c:otherwise>
    </c:choose>

</c:forEach>


<script language="JavaScript" type="text/javascript">
<!--
var altAccountSysId = new Array(
<c:forEach var="prRulesViewBean" items="${prRulesViewCollection}" varStatus="status2">
   <c:if test="${status2.index > 0}">,</c:if>
   {
		id:'<tcmis:jsReplace value="${prRulesViewBean.accountSysId}"/>',
		label:'<tcmis:jsReplace value="${prRulesViewBean.accountSysLabel}"/>'
	}
</c:forEach>
);

// Allow different permissions for different columns
var multiplePermissions = true;

// Build up the array for the columns which use different permissions
var permissionColumns = new Array();
if('Y' == $v("showDefault")) {
	permissionColumns = {
        "countType" : true,
        "cabinetId" : true,
        "avgAmount" : true,
        "maxAmount" : true,
        "sizeUnit" : true,
        "showBinPartStatus" : true,
        "defaultPartType" : true,
        "defaultPermitId" : true,
        "defaultApplicationMethodCod" : true,
        "defaultSubstrateCode" : true,
        "tierIIStorage" : true,
        "tierIITemperature" : true,
        "largestContainerSize" : true,
        "dropShipOverride" : true,
        "binSizeUnit" : true
    };
} else {
	permissionColumns = {
        "countType" : true,
        "cabinetId" : true,
        "avgAmount" : true,
        "maxAmount" : true,
        "sizeUnit" : true,
        "showBinPartStatus" : true,
        "tierIIStorage" : true,
        "tierIITemperature" : true,
        "largestContainerSize" : true,
        "dropShipOverride" : true,
        "binSizeUnit" : true
    };
}
// -->
</script>

	<iframe id="LabelPrintFrame" src="/blank.html" name="LabelPrintFrame" style="border:0px; width:0px; height:0px;"></iframe>
	<form name="LabelPrintForm" id="LabelPrintForm" method="post" action="/tcmIS/Hub/Cabinetlabel" target="LabelPrintFrame">
	</form>	

</body>
</html:html>