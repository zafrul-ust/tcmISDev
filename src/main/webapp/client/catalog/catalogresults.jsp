<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="expires" content="-1">
		<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>
		<%@ include file="/common/locale.jsp"%>
		<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
		<tcmis:gridFontSizeCss />
		<!-- Add any other stylesheets you need for the page here -->
		<style>
			.divCatalogItemImages {padding: 3px;}

			.catalogItemThumbnail {display: block;width: 30px;}

			.divCatalogItemImages:hover>.catalogItemImage {display: block;}
       </style>
		

<!--[if !IE]> -->
    <style>
	 .catalogItemImage {display: none;position: fixed;top: 50%;left: 50%;transform: translate(-50%, -50%);}
	</style>
<!-- <![endif]-->


<!--[if IE]>

<!--[if lt IE 9]>
	  <style>
       .catalogItemImage {display: none;position: fixed;top: 25%;left: 25%;}
    </style>
<![endif]-–>

<!--[if gt IE 8]>
  <style>
	 .catalogItemImage {display: none;position: fixed;top: 50%;left: 50%;transform: translate(-50%, -50%);}
	</style>
<![endif]-–>

<![endif]-->



		<script type="text/javascript" src="/js/common/formchek.js"></script>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
		<%--NEW - grid resize--%>
		<script type="text/javascript"
			src="/js/common/grid/resultiframegridresize.js"></script>
		<!-- This handels which key press events are disabeled on this page -->
		<script src="/js/common/disableKeys.js"></script>

		<!-- This handels the menu style and what happens to the right click on the whole page -->
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
		<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
		<%@ include file="/common/rightclickmenudata.jsp"%>

		<!-- Add any other javascript you need for the page here -->
		<script type="text/javascript" src="/js/client/catalog/catalog.js"></script>

		<script type="text/javascript"
			src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

		<!-- These are for the Grid, uncomment if you are going to use the grid -->
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

		<%--Uncomment below if you are providing header menu to switch columns on/off--%>
		<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->

		<%--Uncomment the below if your grid has rwospans >1--%>
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

		<%--This is to allow copy and paste. works only in IE--%>
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_selection.js"></script>
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js"></script>
		<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
		<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

		<c:set var="module">
			<tcmis:module />
		</c:set>

		<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
contextDisabled = true;

var messagesData = new Array();

messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
itemInteger:"<fmt:message key="error.item.integer"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
pleasewait:"<fmt:message key="label.pleasewaitmenu"/>",
partno:"<fmt:message key="label.partno"/>",
partinv:"<fmt:message key="label.partinv"/>",
reorderpoint:"<fmt:message key="label.reorderpoint"/>",
stockinglevel:"<fmt:message key="label.stockinglevel"/>",
iteminv:"<fmt:message key="label.iteminv"/>",
spec:"<fmt:message key="label.specification"/>",
cartcomment:"<fmt:message key="label.cartcomment"/>",
cataddreq:"<fmt:message key="label.cataddreq"/>",
approvedworkareas:"<fmt:message key="approvedworkareas.label.title"/>",
workareacomments:"<fmt:message key="workareacomments.label.title"/>",
partcomments:"<fmt:message key="label.partcomments"/>",
baselinereset:"<fmt:message key="label.baselinereset"/>",
labelquantity:"<fmt:message key="label.labelquantity"/>",
itemid:"<fmt:message key="label.itemid"/>",
inventory:"<fmt:message key="label.inventory"/>",
mfglit:"<fmt:message key="label.mfglit"/>",
image:"<fmt:message key="label.image"/>",
viewmsds:"<fmt:message key="label.viewmsds"/>",
printsecondarylabelinformation:"<fmt:message key="label.printsecondarylabelinformation"/>",
secondarylabelinformation:"<fmt:message key="label.secondarylabelinformation"/>",
addtocart:"<fmt:message key="label.addtocart"/>",
baselineexp:"<fmt:message key="label.baselineexp"/>",
catalog:"<fmt:message key="label.catalog"/>",
part:"<fmt:message key="label.part"/>",
revision:"<fmt:message key="label.revision"/>",
description:"<fmt:message key="label.description"/>",
type:"<fmt:message key="label.type"/>",
price:"<fmt:message key="label.price"/>",
partuom:"<fmt:message key="label.partuom"/>",
qtyOfUomPerItem:"<fmt:message key="catalog.label.qtyofuomperitem"/>",
status:"<fmt:message key="label.status"/>",
shelflife:"<fmt:message key="catalog.label.shelflife"/>",
item:"<fmt:message key="label.item"/>",
componentdescription:"<fmt:message key="inventory.label.componentdescription"/>",
packaging:"<fmt:message key="inventory.label.packaging"/>",
grade:"<fmt:message key="label.grade"/>",
manufacturer:"<fmt:message key="label.manufacturer"/>",
mfgpartno:"<fmt:message key="label.mfgpartno"/>",
comments:"<fmt:message key="label.comments"/>",
leadtimeplotpart1:"<fmt:message key="label.leadtimeplotpart1"/>",
leadtimeplotpart2:"<fmt:message key="label.leadtimeplotpart2"/>",
qualitysummary:"<fmt:message key="label.qualitysummary"/>",
baseline:"<fmt:message key="label.baseline"/>",
modifyQpl:"<fmt:message key="label.modifyqpl"/>",
newPart:"<fmt:message key="label.newpart"/>",
newPartFromExistingPart:"<fmt:message key="label.newpartfromcurrentpart"/>",
waitingforinput:"<fmt:message key="label.waitingforinputfrom"/>",	
directedCharge:"<fmt:message key="label.directedCharge"/>",
secondarylabelinformationformaterialid:"<fmt:message key="label.secondarylabelinformationformaterialid"/>",
addeditsynonym:"<fmt:message key="label.addeditsynonym"/>",
invalidChargeNumbers:"<fmt:message key="label.invalidchargenumbers"/>",
invalidChargeNumberAddTolist:"<fmt:message key="label.invalidchargenumberaddtolist"/>",
editPartDirectedCharge:"<fmt:message key="label.editpartdirectedcharge"/>",
view:"<fmt:message key="label.view"/>",
viewapproval:"<fmt:message key="label.viewapproval"/>",
kitMsds:"<fmt:message key="label.kitmsds"/>",
newPartFromExistingItem:"<fmt:message key="label.newpartfromitem"/>",
newPartFrom:"<fmt:message key="label.newpartfrom"/>",
itemModifyPkg:"<fmt:message key="label.itemmodifypkg"/>",
beginning:"<fmt:message key="label.beginning"/>",        
newWorkAreaApproval:"<fmt:message key="label.newworkareaapproval"/>",
kitMsds:"<fmt:message key="label.kitmsds"/>",
viewPartDirectedCharge:"<fmt:message key="label.viewdirectedcharge"/>",
materialMsds:"<fmt:message key="label.msds"/>",
printGHSLabels:"<fmt:message key="label.printGHSlabels"/>",        
nopriceerror:"<fmt:message key="label.noprice"/>",
endDate:"<fmt:message key="label.enddate"/>",
testDefinition:"<fmt:message key="label.testdefinition"/>",
startDate:"<fmt:message key="label.startDate"/>",
leadtime:"<fmt:message key="label.leadtime"/>",
newApprovalCode:"<fmt:message key="label.newapprovalcode"/>",
editapprovalcodeexpiration:"<fmt:message key="label.editapprovalcodeexpiration"/>",
showlegend:"<fmt:message key="label.legend"/>",
viewMaterialDoc:"<fmt:message key="label.viewmaterialdoc"/>",
costPerVolume:"<fmt:message key="label.costpervolume"/>"
};

var newMsdsViewer = false;

<tcmis:featureReleased feature="NewMsdsViewer" scope="ALL">
   newMsdsViewer = true;
</tcmis:featureReleased>

var printGHSLabels = false;
<tcmis:featureReleased feature="PrintGHSLabels" scope="ALL">
  printGHSLabels = true;
</tcmis:featureReleased>

var hideCatalogCol = false;
<tcmis:featureReleased feature="HideCatalogCol" scope="ALL">
hideCatalogCol = true;
</tcmis:featureReleased>

var showPartRevision = false;
<tcmis:featureReleased feature="ShowPartRevision" scope="${param.facilityId}">
showPartRevision = true;
</tcmis:featureReleased>

var showLeadTime = false;
<tcmis:featureReleased feature="ShowLeadTime" scope="${param.facilityId}">
showLeadTime = true;
</tcmis:featureReleased>

var showCostPerVolume = false;
<tcmis:featureReleased feature="ShowCostPerVolume" scope="${param.facilityId}">
showCostPerVolume = true;
</tcmis:featureReleased>

var map = null;
var map2 = null;
var prerow = null;
var preClass = null;
var rowSpanMap = new Array();
var rowSpanLvl2Map = new Array();
var rowSpanClassMap = new Array();
var lineIdMap = new Array();
var lineIdMap2 = new Array();

var selectedRowId = null;
var selectedColId = null;

var isCompanyUsesCustomerMSDS = '${isCompanyUsesCustomerMSDS}';

with ( milonic=new menuname("addToCartMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
// style = contextStyle;
// margin=3;
 aI( "text=<b>Part No</b>: $$ ;url=javascript:doNothing();" );
 aI( "text=Part Inventory: $$ ;url=javascript:doNothing();" );
 aI( "text=Reorder Point: $$ ;url=javascript:doNothing();" );
 aI( "text=Stocking Level: $$ ;url=javascript:doNothing();" );
 aI( "showmenu=itemInventory;text=Item Inventory;image=" );
 aI( "showmenu=spec;text=Specification;image=" );
 }

with ( milonic=new menuname("partInventory") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
// style = contextStyle;
// margin=3;
 aI( "text=$$: ?? ;url=javascript:callItemInventory();" );
 aI( "text=$$: ?? ;url=javascript:callItemInventory();" );
 aI( "text=$$: ?? ;url=javascript:callItemInventory();" );
 aI( "text=$$: ?? ;url=javascript:callItemInventory();" );
}

with(milonic=new menuname("showDirectedChargeAccountSys")){
	style=submenuStyle;
	itemheight=17;
	//has to have at least one menu item, this is a place holder.
	//we will dynamically add items in javascript
	aI("text=test;");
}

with(milonic=new menuname("requestId")){
	style=submenuStyle;
	itemheight=17;
	//has to have at least one menu item, this is a place holder.
	//we will dynamically add items in javascript
	aI("text=test;");
}

with ( milonic=new menuname("itemInventory") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
// style = contextStyle;
// margin=3;
 aI( "text=$$: ?? ;url=javascript:callItemInventory();" );
}

with ( milonic=new menuname("spec") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
// style = contextStyle;
// margin=3;
 aI( "text=$$ (Purchase To);url=javascript:showSpec();" );
}

with ( milonic=new menuname("comments") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
// style = contextStyle;
// margin=3;
 aI( "text=$$ (Purchase To);url=javascript:showSpec();" );
 aI( "text=$$ (Purchase To);url=javascript:showSpec();" );
 aI( "text=$$ (Purchase To);url=javascript:showSpec();" );
 aI( "text=$$ (Purchase To);url=javascript:showSpec();" );
}

with ( milonic=new menuname("baseline") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
// style = contextStyle;
// margin=3;
 aI( "text=$$ (Purchase To);url=javascript:showSpec();" );
 aI( "text=$$ (Purchase To);url=javascript:showSpec();" );
}



with ( milonic=new menuname("addToCartMenu2") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
// style = contextStyle;
// margin=3;
 aI( "text=<b>Item Id: $$ ;url=javascript:doNothing();" );
 aI( "text=Inventory: $$ ;url=javascript:doNothing();" );
 aI( 'text=<font color="gray">Mfg Lot</font>;url=javascript:doNothing();' );
 aI( 'text=<font color="gray">Image</font>;url=javascript:doNothing();' );
 aI( "text=Approved Work Areas;url=javascript:approvedWorkAreas();" );
 if (printGHSLabels)
	    aI( "text="+messagesData.printGHSLabels+";url=javascript:printGHSLabels();" );
}
	
with ( milonic=new menuname("addToCartMenu3") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text="+messagesData.viewmsds+";url=javascript:viewMSDS();" );
 aI( "text="+messagesData.viewMaterialDoc+";url=javascript:viewMaterialDoc();" );
 aI( "text="+messagesData.addeditsynonym+";url=javascript:addEditSynonym();" );
 <c:if test="${module == 'lmco'}">
    aI( "text="+messagesData.printsecondarylabelinformation+";url=javascript:printSecondaryLabelInformation();" );
    aI( "text="+messagesData.secondarylabelinformation+";url=javascript:updateSecondaryLabelInformation();" );
 </c:if>

}

with ( milonic=new menuname("pleasewait") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text="+messagesData.viewmsds+";url=javascript:viewMSDS();" );
 aI( "text="+messagesData.addeditsynonym+";url=javascript:addEditSynonym();" );
 <c:if test="${module == 'lmco'}">
    aI( "text="+messagesData.printsecondarylabelinformation+";url=javascript:printSecondaryLabelInformation();" );
    aI( "text="+messagesData.secondarylabelinformation+";url=javascript:updateSecondaryLabelInformation();" );
 </c:if>
  if (printGHSLabels)
    aI( "text="+messagesData.printGHSLabels+";url=javascript:printGHSLabels();" );   
}

drawMenus();

var curcursor = null;
var preContextMenuName = null
var disableMenu = parent.disableMenu;


// ajax request function.
var req;

var mygrid;

// if '--Hide--' no show
var qualityIdLabelColumnHeader = '${qualityIdLabelColumnHeader}';;
var catPartAttrColumnHeader = '${catPartAttrColumnHeader}';

<c:set var="dataCount" value='${0}'/>
<c:set var="prePar" value=""/>

 // only reytheon sees baseline.
var buildbaseline = false;
<c:if test="${personnelBean.companyId == 'RAYTHEON' }">
buildbaseline = true;
</c:if>




// -->
</script>
</head>

<body bgcolor="#ffffff" onload="newinit();">
	<tcmis:form action="/catalogresults.do"
		onsubmit="return submitFrameOnlyOnce();">

		<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
		<c:set var="catalogAddPermission" value="N" />
		<c:if test="${isNewCatalogAddProcessReadyForFacility == 'Y'}">
			<tcmis:facilityPermission indicator="true"
				userGroupId="CreateNewChemical"
				companyId="${personnelBean.companyId}"
				facilityId="${param.facilityId}">
				<c:set var="catalogAddPermission" value="Y" />
			</tcmis:facilityPermission>
		</c:if>

		<tcmis:facilityPermission indicator="true"
			userGroupId="ChargeNumberSetup"
			companyId="${personnelBean.companyId}"
			facilityId="${param.facilityId}">
			<script type="text/javascript">
		<!--
		chargeNumberSetup = true;
		//-->
	</script>
		</tcmis:facilityPermission>

		<tcmis:facilityPermission indicator="true"
			userGroupId="DirectedChargeAssignment"
			companyId="${personnelBean.companyId}"
			facilityId="${param.facilityId}">
			<script type="text/javascript">
		<!--
		directedChargeAssignment = true;
		//-->
	</script>
		</tcmis:facilityPermission>

		<script type="text/javascript">
 <!--
  showUpdateLinks = false;  
 //-->
 </script>

		<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
		<!-- Error Messages Begins -->
		<div id="errorMessagesAreaBody" style="display: none;">
			${tcmisError}</div>

		<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmisError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
		<!-- Error Messages Ends -->

		<script type="text/javascript">
<!--
<c:if test="${prCatalogScreenSearchBeanCollection != null}" >
 <c:if test="${!empty prCatalogScreenSearchBeanCollection}" >
//  var jsonMainData = new Array();
//  The color stuff is not working at this moment, I will use
//  javascript to fix it.
  var jsonMainData = {
  rows:[
  <c:forEach var="p" items="${prCatalogScreenSearchBeanCollection}" varStatus="status">

	  <c:if test="${status.index != 0 }">,</c:if>
	<c:set var="curPar" value="${status.current.catPartNo}${status.current.catalogId}${status.current.catalogCompanyId}${status.current.partGroupNo}"/>
	<c:if test="${!(curPar eq prePar)}">
		<c:set var="dataCount" value='${dataCount+1}'/>
		</c:if>

	  <fmt:formatDate var="fmtDate1" value="${status.current.minPriceStartDate}" pattern="${dateFormatPattern}"/>
	  <fmt:formatDate var="fmtDate2" value="${status.current.maxPriceStartDate}" pattern="${dateFormatPattern}"/>
	  <fmt:formatDate var="fmtDate3" value="${status.current.minPriceEndDate}" pattern="${dateFormatPattern}"/>
	  <fmt:formatDate var="fmtDate4" value="${status.current.maxPriceEndDate}" pattern="${dateFormatPattern}"/>

      <c:set var="applicationPermission" value="N"/>
	  <tcmis:applicationPermission indicator="true" userGroupId="GenerateOrders" facilityId="${param.facilityId}" application="${param.applicationId}">
	  	<c:set var="applicationPermission" value="Y"/>
      </tcmis:applicationPermission>

      <c:set var="showOnHoldForProgram" value="${tcmis:isFeatureReleased(personnelBean,'ShowHoldForProgram', param.facilityId)}"/>
      <c:if test="${showOnHoldForProgram == 'true'}">
          <c:if test="${facLocAppHoldAsCustomerOwned == 'Y'}">
            <c:set var="applicationPermission" value="N"/>
            <tcmis:facilityPermission indicator="true" userGroupId="SetMrOnHoldForProgram" companyId="${personnelBean.companyId}" facilityId="${param.facilityId}">
                <c:set var="applicationPermission" value="Y"/>
            </tcmis:facilityPermission>
          </c:if>
      </c:if>

      <c:set var="obsoletePartDesc" value=""/>
		<c:choose>
			<c:when test="${p.partStatus == 'O'}">
		      	<c:set var="obsoletePartDesc">(<fmt:message key="label.obsolete"/>)</c:set>
			</c:when>
			<c:when test="${p.partStatus == 'D'}">
		      	<c:set var="obsoletePartDesc">(<fmt:message key="label.fadeout"/>)</c:set>
			</c:when>
		</c:choose>      
 

      <%-- display medianMrLeadTime unless projectedLeadTime is set --%>    
      <c:set var="leadTimeValue" value="${p.medianMrLeadTime}"/>
      <c:if test="${!empty p.projectedLeadTime}">
        <c:set var="leadTimeValue" value="${p.projectedLeadTime}"/>
      </c:if>

      <tcmis:featureReleased feature="ShowLeadTime" scope="${param.facilityId}">
          <c:set var="leadTimeValueDisplay" value="${leadTimeValue}"/>
	      <c:if test="${!empty leadTimeValue}">                  
	          <c:set var="leadTimeValueDisplay" value="${leadTimeValue} days"/>           
	      </c:if>
	      <c:set var="showDefaultLeadTime" value=""/>
          <c:if test="${empty leadTimeValue && defaultLeadTime > 0 && !empty defaultLeadTime}">                  
              <c:set var="showDefaultLeadTime" value="${defaultLeadTime} days"/>           
          </c:if>
	   </tcmis:featureReleased>
	   
	   
	   <c:set var="pricePerUnitVolumeDisp" value=""/>
	   <c:if test="${!empty p.pricePerUnitVolume}">
	     <fmt:formatNumber var="pricePerVolumeFormat" maxFractionDigits="4" minFractionDigits="4">${p.pricePerUnitVolume}</fmt:formatNumber>
	   	 <c:set var="pricePerUnitVolumeDisp" value="${pricePerVolumeFormat} ${p.pricePerUnitVolumeUnit}"/>
	   </c:if>
	   
      { id:${status.index +1},
        <c:if test="${p.serviceFeeRow == 'Y' && !empty p.availableQtyForFee}">
            'class':"grid_green",
        </c:if>
            
        <c:set var="catalogImage" value="<div class=\"divCatalogItemImages\"><img class=\"catalogItemThumbnail\" src=\"https://via.placeholder.com/500.png?text=No%20image\"/><img class=\"catalogItemImage\" src=\"https://via.placeholder.com/500.png?text=No%20image\"/></div>" />         	 
       	<c:if test="${!empty p.image_content}">
       		<c:set var="catalogImage" value="<div class=\"divCatalogItemImages\"><img class=\"catalogItemThumbnail\" src=\"${p.image_content}\"/><img class=\"catalogItemImage\" src=\"${p.image_content}\"/></div>" />
    	</c:if>
        
        
        data:[
              {value:'${p.catalogDesc}'},
              {value:'${catalogImage}'},
              {value:'<tcmis:jsReplace value="${p.catPartNo}" processMultiLines="true" processSpaces="true"/>'},
              {value:'<tcmis:jsReplace value="${p.customerPartRevision}" processMultiLines="true"/>'},
              {value:'<tcmis:jsReplace value="${p.partDescription}" processMultiLines="true"/>'},
              {value:'${p.stockingMethod}'},
              {value:'<c:choose><c:when test="${!empty leadTimeValue}">${leadTimeValueDisplay}</c:when><c:otherwise>${showDefaultLeadTime} <c:if test="${!empty showDefaultLeadTime}"><a href="javascript:showLegend()" style="text-decoration: none;"><sup>*</sup></a></c:if></c:otherwise></c:choose>'},
              {value:''},
              {value:'${p.unitOfSale}'},
              {value:'${p.qtyOfUomPerItem}'},
              {value:'${pricePerUnitVolumeDisp}'},
              {value:''},
              {value:'${p.itemId} ${obsoletePartDesc}'},
              {value:'${p.kitMsdsNumber}'},
              {value:''},
              {value:'<tcmis:jsReplace value="${p.grade}" processMultiLines="true"/>'},
              {value:'<tcmis:jsReplace value="${p.packaging}" processMultiLines="true"/>'},
              {value:'<tcmis:jsReplace value="${p.mfgDesc}" processMultiLines="true"/>'},
              {value:'<tcmis:jsReplace value="${p.mfgPartNo}"  processMultiLines="true"/>'},
		      {value:'${p.approvalStatus}'},
			  {value:'${p.componentMsdsNumber}'},
	 		  {value:'${p.catalogCompanyId}'},
              {value:'${p.partGroupNo}'},
              {value:'${p.inventoryGroup}'},
              {value:'${p.materialId}'},
              {value:'${applicationPermission}'},
              {value:'${fmtDate1}'},
              {value:'${fmtDate2}'},
              {value:'${fmtDate3}'},
              {value:'${fmtDate4}'},
              {value:'<tcmis:jsReplace value="${p.inventoryGroupName}"/>'},
              {value:''},
              {value:'${p.medianSupplyLeadTime}'},
              {value:'${p.currencyId}'},
              {value:'<tcmis:jsReplace value="${p.comments}" processMultiLines="true"/>'},
			  {value:'${p.orderQuantity}'},
			  {value:'${p.orderQuantityRule}'},
			  {value:'${p.minBuy}'},
			  {value:''},
			  {value:'${p.availableQtyForFee}'},
			  {value:'${p.serviceFeeRow}'},
			  {value:'${leadTimeValue}'},
			  {value:'${p.unitConversionOption}'},
			  {value:'${p.itemType}'},
			  {value:'${p.itemId}'},
			  {value:'${p.catalogId}'},
			  {value:'${p.qualityId}'},
			  {value:'${p.catPartAttribute}'},
			  {value:'<tcmis:jsReplace value="${p.prop65Warning}" processMultiLines="true"/>'}
			]}

		<c:set var="prePar" value="${status.current.catPartNo}${status.current.catalogId}${status.current.catalogCompanyId}${status.current.partGroupNo}"/>


	  </c:forEach>
  ]};

  </c:if>
 </c:if>
//-->
</script>
		<!-- json data Ends -->

		<div class="interface" id="resultsPage">
			<div class="backGroundContent">

				<%--NEW - there is no results table anymore--%>
				<div id="prCatalogScreenSearchBean"
					style="width: 100%; %; height: 400px;" style="display: none;"></div>


				<c:if test="${prCatalogScreenSearchBeanCollection != null}">
					<!-- If the collection is empty say no data found -->
					<c:if test="${empty prCatalogScreenSearchBeanCollection}">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="tableNoData" id="resultsPageTable">
							<tr>
								<td width="100%"><fmt:message key="main.nodatafound" /></td>
							</tr>
						</table>
					</c:if>
				</c:if>

				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<input name="totalLines" id="totalLines" value="${dataCount}"
						type="hidden" /> <input name="uAction" id="uAction" value=""
						type="hidden" /> <input name="facilityId" id="facilityId"
						value="${param.facilityId}" type="hidden" /> <input
						name="applicationId" id="applicationId"
						value="${param.applicationId}" type="hidden" /> <input
						name="companyId" id="companyId" value="${personnelBean.companyId}"
						type="hidden" /> <input name="catalogAddPermission"
						id="catalogAddPermission" value="${catalogAddPermission}"
						type="hidden" /> <input type="hidden" name="showCatalogPrice"
						id="showCatalogPrice" value="${param.showCatalogPrice}" /> <input
						type="hidden" name="secureDocViewer" id="secureDocViewer"
						value="${tcmis:isCompanyFeatureReleased(personnelBean,'SecureDocViewer','', personnelBean.companyId)}" />
					<input type="hidden" name="personnelId" id="personnelId"
						value="${personnelBean.personnelId}"> <input type="hidden"
						name="printerLocation" id="printerLocation"
						value="${personnelBean.printerLocation}"> <input
							type="hidden" name="hasHmrbTab" id="hasHmrbTab"
							value="${tcmis:isFeatureReleased(personnelBean,'HmrbTab',param.facilityId)}" />
							<input type="hidden" name="showProp65Warning"
							id="showProp65Warning" value="${showProp65Warning}" />
				</div>
				<!-- Hidden elements end -->

			</div>
			<!-- close of backGroundContent -->
		</div>
		<!-- close of interface -->

		<tcmis:featureReleased feature="ShowQualitySummary" scope="ALL">
			<script type="text/javascript">
  showQualitySummary = true;
</script>
		</tcmis:featureReleased>

	</tcmis:form>

	<c:set var="prePar" value="" />
	<c:set var="parCount" value="1" />

	<c:forEach var="p" items="${prCatalogScreenSearchBeanCollection}"
		varStatus="status">
		<c:set var="finalPrice" value='' />
		<c:set var="finalUnitPrice" value='' />
		<c:set var="facilityOrAllCatalog"
			value='${param.facilityOrAllCatalog}' />
		<c:set var="minCatalogPrice" value='${status.current.minCatalogPrice}' />
		<c:set var="maxCatalogPrice" value='${status.current.maxCatalogPrice}' />
		<c:set var="minUnitPrice" value='${status.current.minUnitPrice}' />
		<c:set var="maxUnitPrice" value='${status.current.maxUnitPrice}' />
		<c:choose>
			<c:when test="${facilityOrAllCatalog == 'All Catalog'}">
				<c:choose>
					<c:when test="${!empty minCatalogPrice && !empty maxCatalogPrice}">
						<c:set var="finalPrice" value='${maxCatalogPrice}' />
					</c:when>
					<c:otherwise>
						<c:if test="${!empty minCatalogPrice}">
							<c:set var="finalPrice" value='${minCatalogPrice}' />
						</c:if>
						<c:if test="${!empty maxCatalogPrice}">
							<c:set var="finalPrice" value='${maxCatalogPrice}' />
						</c:if>
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test="${!empty minUnitPrice && !empty maxUnitPrice}">
						<c:set var="finalUnitPrice" value='${maxUnitPrice}' />
					</c:when>
					<c:otherwise>
						<c:if test="${!empty minUnitPrice}">
							<c:set var="finalUnitPrice" value='${minUnitPrice}' />
						</c:if>
						<c:if test="${!empty maxUnitPrice}">
							<c:set var="finalUnitPrice" value='${maxUnitPrice}' />
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:set var="finalPrice" value='${minCatalogPrice}' />
				<c:set var="finalUnitPrice" value='${minUnitPrice}' />
			</c:otherwise>
		</c:choose>

		<c:set var="finalPriceDisplay" value="" />
		<c:if test="${!empty finalPrice}">
			<fmt:formatNumber var="finalPriceDisplay" maxFractionDigits="4"
				minFractionDigits="4">${finalPrice}</fmt:formatNumber>
			<c:set var="finalPriceDisplay"
				value="${finalPriceDisplay} ${status.current.currencyId}" />
		</c:if>


		<c:set var="storageTemp" value='${status.current.storageTemp}' />
		<c:set var="shelfLife" value='${status.current.shelfLife}' />
		<c:set var="shelfLifeDisplay" value="" />
		<c:choose>
			<c:when test="${empty storageTemp || storageTemp == ' '}">
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${shelfLife != 'Indefinite'}">
						<c:set var="shelfLifeBasis"
							value='${status.current.shelfLifeBasis}' />
						<c:if test="${!empty shelfLifeBasis}">
							<c:set var="shelfLifeDisplay"
								value="${status.current.shelfLife} ${shelfLifeBasis} @ ${storageTemp}" />
						</c:if>
						<c:if test="${empty shelfLifeBasis}">
							<c:set var="shelfLifeDisplay"
								value="${status.current.shelfLife} @ ${storageTemp}" />
						</c:if>
					</c:when>
					<c:otherwise>
						<c:set var="shelfLifeDisplay"
							value="${status.current.shelfLife} @ ${storageTemp}" />
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>

		<tcmis:jsReplace var="shelfLifeDisplay" value="${shelfLifeDisplay}"
			processMultiLines="true" />
		<tcmis:jsReplace var="materialDesc" value="${p.materialDesc}"
			processMultiLines="true" />

		<c:set var="curPar"
			value="${status.current.catPartNo}${status.current.catalogId}${status.current.catalogCompanyId}${status.current.partGroupNo}" />
		<c:set var="curItem" value="itemId${status.current.itemId}" />


		<script language="JavaScript" type="text/javascript">
<!--

<c:set var="finalPriceDispIdx" value="${6}"/>
<c:set var="slstIdx" value="${10}"/>
<c:set var="matlDescIdx" value="${13}"/>
<c:set var="finalPriceIdx" value="${30}"/>
<c:set var="finalUnitPriceIdx" value="${37}"/>

var finalPriceDispData = jsonMainData.rows[${status.index}].data[${finalPriceDispIdx}];
if (finalPriceDispData) {
	finalPriceDispData.value = '${finalPriceDisplay}';
}

var slstData = jsonMainData.rows[${status.index}].data[${slstIdx}];
if (slstData) {
	slstData.value = '${shelfLifeDisplay}';
}

var matlDescData = jsonMainData.rows[${status.index}].data[${matlDescIdx}];
if (matlDescData) {
	matlDescData.value = '${materialDesc}';
}

var finalPriceData = jsonMainData.rows[${status.index}].data[${finalPriceIdx}];
if (finalPriceData) {
	finalPriceData.value = '${finalPrice}';
}

var finalUnitPriceData = jsonMainData.rows[${status.index}].data[${finalUnitPriceIdx}];
if (finalUnitPriceData) {
	finalUnitPriceData.value = '${finalUnitPrice}';
}


	<c:if test="${!(curPar eq prePar)}">
	   	<c:set var="parCount" value="${parCount+1}"/>
  			<c:set var="preItem" value=""/>
	   	rowSpanMap[${status.index}] = ${rowCountPart[curPar]};
	</c:if>
	<c:if test="${ !( curItem eq preItem)}">
		rowSpanLvl2Map[${status.index}] = ${rowCountItem[curPar][curItem]};
	</c:if>
  	rowSpanClassMap[${status.index}] = ${parCount % 2} ;
// -->
</script>

		<c:set var="prePar"
			value="${status.current.catPartNo}${status.current.catalogId}${status.current.catalogCompanyId}${status.current.partGroupNo}" />
		<c:set var="preItem" value="itemId${status.current.itemId}" />

	</c:forEach>

	<script language="JavaScript" type="text/javascript">
<!--
var altAccountSysId = new Array(
<c:forEach var="prRulesViewBean" items="${prRulesViewCollection}" varStatus="status2">
   <c:if test="${status2.index > 0}">,</c:if>
   {
		id:'<tcmis:jsReplace value="${status2.current.accountSysId}"/>',
        desc:'<tcmis:jsReplace value="${status2.current.accountSysDesc}"/>',
        label:'<tcmis:jsReplace value="${status2.current.accountSysLabel}"/>'
	}
</c:forEach>
);

var altCatalogFacility = new Array(
<c:forEach var="catalogFacilityBean" items="${catalogFacilityCollection}" varStatus="status2">
   <c:if test="${status2.index > 0}">,</c:if>
   {
		catalogCompanyId:'<tcmis:jsReplace value="${status2.current.catalogCompanyId}"/>',
		catalogId:'<tcmis:jsReplace value="${status2.current.catalogId}"/>',
		catalogDesc:'<tcmis:jsReplace value="${status2.current.catalogDesc}"/>',
		catalogAddAllowed:'<tcmis:jsReplace value="${status2.current.catalogAddAllowed}"/>'
	}
</c:forEach>
);

// -->
</script>

	<%-- show legend Begins --%>
	<div id="showLegendAreaDisclaimer"
		style="display: none; overflow: auto;">
		<table width=100% class="tableResults" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="legendText"><fmt:message
						key="label.leadtimedisclaimer" /></td>
			</tr>
		</table>
	</div>

</body>
</html>
