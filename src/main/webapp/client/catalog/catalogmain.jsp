<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"/>

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />

<link rel="stylesheet" type="text/css" href="/css/clientpages.css"/>
<style type="text/css">
html {
  height: 100%;
  max-height:100%;
  margin-bottom: 1px;
  overflow: hidden;
}
</style>


<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

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
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/client/catalog/catalogmain.js"></script>
<script type="text/javascript" src="/js/client/catalog/showpreviousorderengevaldetail.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<!-- Include this so I can submit grid thru form -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />  


<script language="JavaScript" type="text/javascript">
<!--
var showcart = true;

<c:set var="minimumCharLength" value='${28}'/>
<c:set var="maxFacilityDescLength" value='${0}'/>
<c:set var="maxApplicationDescLength" value='${0}'/>

//add all the javascript messages here, this for internationalization of client side javascript messages
<c:set var="facilityCount" value='${0}'/>
var altFacilityId = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
    <c:if test="${myWorkareaFacilityViewBean.active == 'y'}">
        <c:if test="${facilityCount > 0}">
        ,
        </c:if>
        '<tcmis:jsReplace value="${status.current.facilityId}" processMultiLines="true"/>'
        <c:set var="facilityCount" value='${facilityCount+1}'/>
   </c:if>
</c:forEach>
);

<c:set var="facilityCount" value='${0}'/>
var altFacilityName = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
   <c:if test="${myWorkareaFacilityViewBean.active == 'y'}">
       <c:if test="${facilityCount > 0}">
        ,
       </c:if>
       '<tcmis:jsReplace value="${status.current.facilityName}" processMultiLines="true"/>'
        <c:if test="${maxFacilityDescLength < fn:length(status.current.facilityName)}">
            <c:set var="maxFacilityDescLength" value='${fn:length(status.current.facilityName)}'/>
        </c:if>
        <c:set var="facilityCount" value='${facilityCount+1}'/>
    </c:if>
</c:forEach>
);

<c:set var="facilityCount" value='${0}'/>
var altFacilityEcommerce = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
   <c:if test="${myWorkareaFacilityViewBean.active == 'y'}">
       <c:if test="${facilityCount > 0}">
        ,
       </c:if>
       '${status.current.ecommerce}'
       <c:set var="facilityCount" value='${facilityCount+1}'/>
   </c:if>
</c:forEach>
);

<c:set var="module">
 <tcmis:module/>
</c:set>

var altApplication = new Array();
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
    <c:if test="${myWorkareaFacilityViewBean.active == 'y'}">
        <c:set var="applicationCount" value='${0}'/>
        altApplication['<tcmis:jsReplace value="${status.current.facilityId}" processMultiLines="true"/>'] = new Array(
         <c:forEach items="${status.current.applicationBeanCollection}" varStatus="status1">
             <c:if test="${status1.current.status == 'A' && status1.current.manualMrCreation == 'Y'}">
                <c:if test="${applicationCount > 0}">
                ,
                </c:if>
                '<tcmis:jsReplace value="${status1.current.application}" processMultiLines="true"/>'
                <c:set var="applicationCount" value='${applicationCount+1}'/>
             </c:if>
         </c:forEach>
        );
    </c:if>
</c:forEach>

var altApplicationDesc = new Array();
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
    <c:if test="${myWorkareaFacilityViewBean.active == 'y'}">
        <c:set var="applicationCount" value='${0}'/>
        altApplicationDesc['<tcmis:jsReplace value="${status.current.facilityId}"/>'] = new Array(
         <c:forEach items="${status.current.applicationBeanCollection}" varStatus="status1">
            <c:if test="${status1.current.status == 'A' && status1.current.manualMrCreation == 'Y'}">
                <c:if test="${applicationCount > 0}">
                ,
                </c:if>
                '<tcmis:jsReplace value="${status1.current.applicationDesc}" processMultiLines="true"/>'
                <c:set var="applicationCount" value='${applicationCount+1}'/>
                <c:if test="${maxApplicationDescLength < fn:length(status1.current.applicationDesc)}">
                    <c:set var="maxApplicationDescLength" value='${fn:length(status1.current.applicationDesc)}'/>
                </c:if>
            </c:if>
         </c:forEach>
        );
    </c:if>
</c:forEach>

var altMyFacPosIg = new Array(
<c:forEach var="myFacPosIgViewBean" items="${myFacilityPointOfSaleIgColl}" varStatus="status">
   <c:if test="${status.index > 0}">,</c:if>
   {
		facilityId:'<tcmis:jsReplace value="${status.current.facilityId}" processMultiLines="true"/>',
		inventoryGroup:'<tcmis:jsReplace value="${status.current.pointOfSaleIg}" processMultiLines="true"/>',
		inventoryGroupName:'<tcmis:jsReplace value="${status.current.inventoryGroupName}" processMultiLines="true"/>',
		createMr:'<tcmis:jsReplace value="${status.current.createMr}" processMultiLines="true"/>'
	}
</c:forEach>
);

<c:if test="${maxFacilityDescLength > minimumCharLength}">
	<c:set var="minimumCharLength" value='${maxFacilityDescLength}'/>
</c:if>

<c:if test="${maxApplicationDescLength > minimumCharLength}">
	<c:set var="minimumCharLength" value='${maxApplicationDescLength}'/>
</c:if>

var cartMinWidth = 500+((50-${minimumCharLength})*7);

//feature release checks.

hideLeadTime = false;
<tcmis:featureReleased feature="HideLeadTime" scope="ALL">
	hideLeadTime = true;
</tcmis:featureReleased>


var altActiveFeatureRelease = new Array(
<c:forEach var="featureReleaseBean" items="${activeFeatureReleaseColl}" varStatus="status">
   <c:if test="${status.index > 0}">,</c:if>
   {
	    facilityId:'<tcmis:jsReplace value="${featureReleaseBean.scope}"/>',
        feature:'<tcmis:jsReplace value="${featureReleaseBean.feature}"/>'
   }
</c:forEach>
);

//user group member checks.
var altUserGroupMemberForCatalogScreen = new Array(
<c:forEach var="userGroupMemberBean" items="${userGroupMemberForCatalogScreenColl}" varStatus="status">
   <c:if test="${status.index > 0}">,</c:if>
   {
	    facilityId:'<tcmis:jsReplace value="${userGroupMemberBean.facilityId}"/>',
        userGroupId:'<tcmis:jsReplace value="${userGroupMemberBean.userGroupId}"/>'
   }
</c:forEach>
);

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleaseSelect:"<fmt:message key="msg.selectWorkarea"/>",
all:"<fmt:message key="label.all"/>",
myworkareas:"<fmt:message key="label.selectOne"/>",
myFacilities:"<fmt:message key="label.myfacilities"/>",	
countData:"<fmt:message key="label.itemcountmsg"/>",
hworkarea:"<fmt:message key="label.workarea"/>",
hpart:"<fmt:message key="label.part"/>",
hdesc:"<fmt:message key="label.description"/>",
hquantity:"<fmt:message key="label.quantity"/>",
hextprice:"<fmt:message key="label.extprice"/>",
hexamplepackaging:"<fmt:message key="label.pkg"/>",
hleadtimeindays:"<fmt:message key="label.projectedleadtimeindays"/>",	
hdateneeded:"<fmt:message key="label.dateneeded"/>",
hnotes:"<fmt:message key="label.notes"/>",
hcritical:"<fmt:message key="label.critical"/>",	
hdeleteline:"<fmt:message key="label.deleteline"/>",
confirmdeletepart:"<fmt:message key="msg.confirmdeletepart"/>",
errors:"<fmt:message key="label.errors"/>",
formyworkareas:"<fmt:message key="label.formyworkareas"/>",
accountsysteminputdialog:"<fmt:message key="label.catalogaccountsysIdInput"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
pleaseSelectaCatalog:"<fmt:message key="label.pleaseselectacatalog"/>",	
materialrequest:"<fmt:message key="label.materialrequest"/>",
pleasewait:"<fmt:message key="label.pleasewait"/>",
pleasewaitmenu:"<fmt:message key="label.pleasewaitmenu"/>",
cartcomment:"<fmt:message key="label.cartcomment"/>",
engineeringevaluation:"<fmt:message key="buyorders.legend.engineeringevaluation"/>",
searchmessage:"<fmt:message key="receiptdocumentviewer.searchmessage"/>",
cataddreq:"<fmt:message key="label.cataddreq"/>",
partCatalog:"<fmt:message key="label.partcatalog"/>",
itemCatalog:"<fmt:message key="label.itemcatalog"/>",
materialCatalog:"<fmt:message key="label.materialcatalog"/>",
orderingLimit:"<fmt:message key="label.orderinglimit"/>",
remainingFinancialLimit:"<fmt:message key="label.remainingfinanciallimit"/>",	 
invalidChargeNumbers:"<fmt:message key="label.invalidchargenumbers"/>",
mrExceededFinancialLimit:"<fmt:message key="label.mrexceededfinanciallimit"/>",
forthisworkarea:"<fmt:message key="label.forthisworkarea"/>",
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
aribarepair:"<fmt:message key="label.aribarepair"/>",
checkoutinprocess:"<fmt:message key="label.checkoutinprocess"/>",
cancelcheckout:"<fmt:message key="label.cancelcheckout"/>"
};

function takeitoff() {
 var inv = $("applicationId");
 inv[inv.length-1] = null;
}

j$().ready(function() {
	function log(event, data, formatted) {
		var f = formatted.split(":");
		personnelChanged(f[0],f[1],"");
		$("posRequestorName").className = "inputBox"; 
		
	} 
	
	j$("#posRequestorName").autocomplete("getpersonneldata.do",{
			extraParams: {activeOnly: function() { return j$('#personnelActiveOnly').is(':checked'); },
						  companyId: function() { return j$("#companyId").val(); } },
			width: 175,
			max: 10,  // default value is 10
			cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
			scrollHeight: 200,
			formatItem: function(data, i, n, value) {
				return  value.split(":")[1].substring(0,40);
			},
			formatResult: function(data, value) {
				return value.split(":")[1];
			}
	});
	
	j$('#posRequestorName').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);

		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateRequestor();
	}));
	
	
	j$("#posRequestorName").result(log).next().click(function() {
		j$(this).prev().search();
	});
	
}); 

// -->
</script>
</head>

<body bgcolor="#ffffff" onresize="catalogReSize()" onload="displayWorkAreaOptions();reloadCatalogType();setCartDivSize();loadMrInCart('${param.action}');closeAllchildren();displaySearchOption();catalogPriceOption();" onunload="closeAllchildren();">
<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
<c:if test="${ecommerceLogon == 'N' || (ecommerceLogon == 'Y' && ecommerceSource == 'SEAGATEIP')}">
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td>
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>
 <%@ include file="/common/clientnavigation.jsp" %>
</c:if>
</div>
<!-- end of Top Navigation -->

<table width="100%" border="0" cellpadding="0" cellspacing="0" id="outTable">
<tr>
<td>
<tcmis:form action="/catalogresults.do"  onsubmit="return false;" target="partCatalogResultFrame">
<table  width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="${480-(50-minimumCharLength)*7}px" valign="top" nowrap="nowrap">
<div id="searchFrameDiv" class="contentArea">
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent" id="searchTableDiv" >
    <!-- Insert all the search option within this div -->
    <table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
<td width="30%" class="optionTitleBoldRight">
<fmt:message key="label.facility"/>:
</td>

<td width="70%" class="optionTitleBoldLeft">
<c:set var="selectedFacilityId" value="${personnelBean.myDefaultFacilityId}"/>
<select name="facilityId" id="facilityId" class="selectBox" onchange="facilityChanged()">

  <c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
    <c:if test="${myWorkareaFacilityViewBean.active == 'y'}">
        <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
          <c:if test="${empty selectedFacilityId}" >
            <c:set var="selectedFacilityId" value='${currentFacilityId}' />
          </c:if>
          <c:if test="${currentFacilityId == selectedFacilityId}" >
            <c:set var="applicationSelectBeanCollection" value='${status.current.applicationBeanCollection}'/>
          </c:if>

        <c:choose>
          <c:when test="${currentFacilityId == selectedFacilityId}">
            <option value="<c:out value="${currentFacilityId}"/>" selected><c:out value="${status.current.facilityName}"/></option>
          </c:when>
          <c:otherwise>
            <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityName}"/></option>
          </c:otherwise>
        </c:choose>
    </c:if>
  </c:forEach>
</select>
</td>

</tr>

<tr>

<td width="30%" class="optionTitleBoldRight" nowrap="true">
<fmt:message key="label.workarea"/>:
</td>

<td width="70%" class="optionTitleBoldLeft">
<c:set var="selectedApplicationId" value='${param.applicationId}'/>
<c:if test="${empty selectedApplicationId}" >
  <c:set var="selectedApplicationId" value="All"/>
</c:if>

<c:set var="applicationCount" value='${0}'/>
<c:forEach var="facLocAppBean" items="${applicationSelectBeanCollection}" varStatus="status">
    <c:if test="${facLocAppBean.status == 'A' && facLocAppBean.manualMrCreation == 'Y'}">
        <c:set var="applicationCount" value='${applicationCount+1}'/>
    </c:if>
</c:forEach>

<select name="applicationId" id="applicationId" class="selectBox" onchange="workareaChanged()">
	<c:choose>
	  <c:when test="${applicationCount == 0}">
		 <option value="My Work Areas"><fmt:message key="label.selectOne"/></option>
	  </c:when>
	  <c:otherwise>
		 <c:if test="${applicationCount > 1}">
			<option value="My Work Areas" selected><fmt:message key="label.selectOne"/></option>
		 </c:if>
		  <c:set var="applicationCount" value='${0}'/>
		  <c:forEach var="facLocAppBean" items="${applicationSelectBeanCollection}" varStatus="status">
			 <c:set var="currentApplicationId" value='${status.current.application}'/>
			 <c:set var="currentStatus"  value='${status.current.status}'/>
             <c:if test="${currentStatus == 'A' && status.current.manualMrCreation == 'Y'}">
                 <c:set var="applicationCount" value='${applicationCount+1}'/>
                 <c:choose>
                    <c:when test="${currentApplicationId == selectedApplicationId}">
                      <option value="<c:out value="${currentApplicationId}"/>" selected><c:out value="${status.current.applicationDesc}"/></option>
                    </c:when>
                    <c:otherwise>
                      <option value="<c:out value="${currentApplicationId}"/>"><c:out value="${status.current.applicationDesc}"/></option>
                    </c:otherwise>
                 </c:choose>
             </c:if>
          </c:forEach>
		</c:otherwise>
	</c:choose>
</select>
<%-- prove of concept
<input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchWorkArea" value="..." onclick="getWorkArea()" type="button"/>
--%>
</td>

</tr>

<tr>
<td width="30%" class="optionTitleBoldRight" nowrap="true">
<fmt:message key="label.searchtype"/>:
</td>
<td width="70%" class="optionTitleBoldLeft">
<select name="catalog" id="catalog" class="selectBox" onchange="catalogTypeChanged()">
	<option id="partCatalog" value="partCatalog">
		<fmt:message key="label.partcatalog"/>
	</option>
	<option id="itemCatalog" value="itemCatalog">
		<fmt:message key="label.itemcatalog"/>
	</option>
</select>
</td>
</tr>

<tr>
	<td width="50%" class="optionTitleBoldLeft" colspan="2">
		<div id="partCatalogCriteriaDiv">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="optionTitleBoldLeft" nowrap="true">
                        <input type="radio" name="facilityOrAllCatalog" id="activeForFacility" value="Active for Facility" onclick="searchOptionClicked()" class="radioBtns" checked="checked"/><fmt:message key="label.activeforFacility"/>
                    </td>
                    <td class="optionTitleBoldLeft" nowrap="true" id="fullFacilityCatalogDisplay" style="display: none">
                        <input type="radio" name="facilityOrAllCatalog" id="fullFacilityCatalogs" value="Full Facility Catalogs" onclick="searchOptionClicked()" class="radioBtns" /><fmt:message key="label.fullfacilitycatalogs"/>
                    </td>
                </tr>
                <tr>
                    <td class="optionTitleBoldLeft" nowrap="true">
                        <input type="radio" name="facilityOrAllCatalog" id="workAreaApproved" value="Work Area Approved" onclick="searchOptionClicked()" class="radioBtns" /><fmt:message key="catalog.label.workAreaApprovedOnly"/>
                    </td>
                    <td class="optionTitleBoldLeft" nowrap="true">
                        <c:set var="allCatalogSearch" value="${tcmis:isFeatureReleased(personnelBean,'AllCatalogSearch', 'ALL')}"/>
                        <c:choose>
                            <c:when test="${allCatalogSearch}">
                                <input type="radio" name="facilityOrAllCatalog" id="allCatalogs" value="All Catalogs" onclick="searchOptionClicked()" class="radioBtns" /><fmt:message key="label.allcatalogs"/>
                            </c:when>
                            <c:otherwise>
                                &nbsp;
                                <input name="facilityOrAllCatalog" id="allCatalogs" type="hidden" value="" />
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <c:set var="showIncludeObsoleteParts" value='N'/>
                <tcmis:featureReleased feature="IncludeObsoleteParts" scope="ALL">
                    <tr>
                        <td class="optionTitleBoldLeft" nowrap="true" colspan="2">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="includeObsoleteParts" id="includeObsoleteParts" class="radioBtns"/><fmt:message key="label.includeobsoleteparts"/>
                        </td>
                    </tr>
                    <c:set var="showIncludeObsoleteParts" value='Y'/>
                </tcmis:featureReleased>
                <c:if test="${showIncludeObsoleteParts == 'N'}">
                    <input name="includeObsoleteParts" id="includeObsoleteParts" type="hidden" value="" />
                </c:if>
            </table>

        </div>
		<div id="itemCatalogCriteriaDiv" style="display: none;<tcmis:featureReleased feature='IncludeObsoleteParts' scope='ALL'>margin:18.5px;</tcmis:featureReleased>"> <%--Spacing for Item Catalog when Include Obsolete Parts featue available in Part Catalog --%>
			<input type="checkbox" name="previousEvalOrder" id="previousEvalOrder" onclick="previousEvalOrderClicked();" value="previousEvalOrder" class="radioBtns"><span id="evalOrdersSpan"><fmt:message key="label.previousevalorders"/></span>
			<br>
			<input type="checkbox" name="evalRequestor" id="evalRequestor" value="evalRequestor" class="radioBtns" disabled="disabled"><span id="evalRequestorSpan" style="color:gray"><fmt:message key="label.byme"/></span>
			&nbsp;&nbsp;
			<input type="checkbox" name="evalWorkArea" id="evalWorkArea" value="evalWorkArea" class="radioBtns" disabled="disabled"><span id="evalWorkAreaSpan" style="color:gray"><fmt:message key="label.formyworkareas"/></span>
		</div>

		<div id="materialCatalogCriteriaDiv" style="display: none">
			<input type="radio" name="facilityOrFullMsdsDatabase" id="facilityOrFullMsdsDatabase" onClick="" value="Active for Facility" class="radioBtns" checked="checked"/><fmt:message key="label.activeforFacility"/>
			&nbsp;&nbsp;
			<input type="radio" name="facilityOrFullMsdsDatabase" id="facilityOrFullMsdsDatabase" onClick="" value="Full MSDS Database" class="radioBtns"/><fmt:message key="label.fullmaterialdatabase"/>
			<br>
			&nbsp;&nbsp;
		</div>
		<div id="posCatalogCriteriaDiv" style="display: none">
			<fmt:message key="label.customer"/>:&nbsp;
			<input name="posRequestorName" id="posRequestorName" type="text" class="inputBox" value="" size="30"/>
			 <input name="personnelActiveOnly" id="personnelActiveOnly" onclick="" type="checkbox" checked class="radioBtns" value="Y"><fmt:message key="label.activeOnly"/>
			<br>
			<input type="checkbox" name="workAreaApprovedOnlyPos" id="workAreaApprovedOnlyPos" value="workAreaApprovedOnly" checked="checked" class="radioBtns"><fmt:message key="label.approvedonly"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<span id="orderLimitSpan"></span>
		</div>
	</td>
</tr>

<tr>
    <td width="30%"  class="optionTitleBoldRight">
    <fmt:message key="label.search"/>:
    </td>

    <td width="70%" class="optionTitleBoldLeft" nowrap>
        <input name="searchText" id="searchText" type="text" class="inputBox" value="${param.searchText}" size="30" onkeypress=""/>
        <c:if test="${minimumCharLength > 25}">
            <img src="/images/minwidth.gif" width="${(minimumCharLength-25)*7}" height="0">
        </c:if>
    </td>
</tr>

<tr>
<td  class="optionTitleBoldLeft" colspan="2">
    <input name="submitSearch" id="submitSearch" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"  onclick="return submitSearchForm();">
    <input name="createexcel"  id="createexcel"  type="button" value="<fmt:message key="label.createexcel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="createXSL()"/>
    <c:if test="${ecommerceLogon == 'Y' && ecommerceSource == 'SEAGATEIP'}">
        <tcmis:facilityPermission indicator="true" userGroupId="Ariba Repair" facilityId="All">
                <input name="aribaRepair"  id="aribaRepair"  type="button" value="<fmt:message key="label.aribarepair"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="launchAribaRepair()"/>
        </tcmis:facilityPermission >
        <input name="cancelReturnSeagate"  id="cancelReturnSeagate"  type="button" value="<fmt:message key="label.cancelandreturn"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="cancelSeagateCheckout();"/>
    </c:if>
</td>
</tr>

</table>

<div id="hiddenSearchElements">
<input name="uAction" id="uAction" value="${param.uAction}" type="hidden" />
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="facilityName" id="facilityName" type="hidden" value="" />
<input name="applicationDesc" id="applicationDesc" type="hidden" value="" />
<input name="searchTypeName" id="searchTypeName" type="hidden" value="" />
<input name="ecommerceFacility" id="ecommerceFacility" type="hidden" value="" />
<input name="ecommerceLogon" id="ecommerceLogon" type="hidden" value="${ecommerceLogon}" />
<input name="ecommerceSource" id="ecommerceSource" type="hidden" value="${ecommerceSource}" />
<input name="partCatalogLastSearchDurationMsg" id="partCatalogLastSearchDurationMsg" type="hidden" value="" />
<input name="itemCatalogLastSearchDurationMsg" id="itemCatalogLastSearchDurationMsg" type="hidden" value="" />
<input name="materialCatalogLastSearchDurationMsg" id="materialCatalogLastSearchDurationMsg" type="hidden" value="" />
<input name="lastSearchFacilityId" id="lastSearchFacilityId" type="hidden" value="" />
<input name="lastSearchFacilityDesc" id="lastSearchFacilityDesc" type="hidden" value="" />
<input name="lastSearchApplication" id="lastSearchApplication" type="hidden" value="" />
<input name="lastSearchApplicationDesc" id="lastSearchApplicationDesc" type="hidden" value="" />
<input name="itemCatalogLastSearchFacilityId" id="itemCatalogLastSearchFacilityId" type="hidden" value="" />
<input name="itemCatalogLastSearchApplication" id="itemCatalogLastSearchApplication" type="hidden" value="" />
<input name="itemCatalogLastSearchApplicationDesc" id="itemCatalogLastSearchApplicationDesc" type="hidden" value="" />

<input name="materialCatalogLastSearchFacilityId" id="materialCatalogLastSearchFacilityId" type="hidden" value="" />
<input name="materialCatalogLastSearchFacilityId" id="materialCatalogLastSearchFacilityDesc" type="hidden" value="" />    
<input name="materialCatalogLastSearchApplication" id="materialCatalogLastSearchApplication" type="hidden" value="" />
<input name="materialCatalogLastSearchApplicationDesc" id="materialCatalogLastSearchApplicationDesc" type="hidden" value="" />    
<input name="companyId" id="companyId" type="hidden" value="${myWorkareaFacilityViewBeanCollection[0].companyId}" />
<input name="accountSysId" id="accountSysId" type="hidden" value="" />
<input name="selectedItemId" id="selectedItemId" type="hidden" value="" />
<input name="payloadId" id="payloadId" type="hidden" value="${param.payloadId}" />
<input name="posRequestorId" id="posRequestorId" type="hidden" value="" />	
<input type="hidden" name="blockBefore_dateNeeded" id="blockBefore_dateNeeded" value="<tcmis:getDateTag numberOfDaysFromToday="-1" datePattern="${dateFormatPattern}"/>"/>
<input type="hidden" name="blockAfter_dateNeeded" id="blockAfter_dateNeeded" value=""/>
<input type="hidden" name="blockBeforeExclude_dateNeeded" id="blockBeforeExclude_dateNeeded" value=""/>
<input type="hidden" name="blockAfterExclude_dateNeeded" id="blockAfterExclude_dateNeeded" value=""/>
<input type="hidden" name="inDefinite_dateNeeded" id="inDefinite_dateNeeded" value=""/>
<input name="prNumber" id="prNumber" type="hidden" value="${param.prNumber}">
<input name="searchHeight" id="searchHeight" type="hidden" value="193">
<%-- the following fields are for catalog adds --%>
<input type="hidden" name="catalogAddCatalogCompanyId" id="catalogAddCatalogCompanyId" value=""/>
<input type="hidden" name="catalogAddCatalogId" id="catalogAddCatalogId" value=""/>
<input type="hidden" name="catalogAddInventoryGroup" id="catalogAddInventoryGroup" value=""/>
<input type="hidden" name="catalogAddCatPartNo" id="catalogAddCatPartNo" value=""/>
<input type="hidden" name="catalogAddPartDesc" id="catalogAddPartDesc" value=""/>	
<input type="hidden" name="catalogAddPartGroupNo" id="catalogAddPartGroupNo" value=""/>
<input type="hidden" name="catalogAddItemId" id="catalogAddItemId" value=""/>    
<input type="hidden" name="catalogAddStocked" id="catalogAddStocked" value=""/>
<input type="hidden" name="myDefaultFacilityId" id="myDefaultFacilityId" value="${personnelBean.myDefaultFacilityId}"/>
<input type="hidden" name="materialId" id="materialId" value=""/>
<input type="hidden" name="custMsdsNo" id="custMsdsNo" value=""/>
<input type="hidden" name="showCatalogPrice" id="showCatalogPrice" value=""/>

<%--
//USE fac_loc_app.charge_type default first unless it's overrides by vv_account_sys.fac_item_charge_type_override
//here's the logic for overriding fac_loc_app.charge_type_default
//vv_account_sys.fac_item_charge_type_override == fac_item.part_charge_type OR vv_accoount_sys.fac_item_charge_type_override = a
//then USE fac_item.part_charge_type
//vv_account_sys.fac_item_charge_type_override:
// d - and fac_item.part_charge_type = d then USE fac_item.part_charge_type (in this case it's d)
// i - and fac_item.part_charge_type = i then USE fac_item.part_charge_type (in this case it's i)
// a - always USE fac_item.part_charge_type
// n - never override => USE fla.charge_type_default
--%>
<input type="hidden" name="engEvalPartTypeRequired" id="engEvalPartTypeRequired" value=""/>
<input type="hidden" name="engEvalPartType" id="engEvalPartType" value=""/>
<input type="hidden" name="facLocAppChargeTypeDefault" id="facLocAppChargeTypeDefault" value=""/>
<input type="hidden" name="facItemChargeTypeOverride" id="facItemChargeTypeOverride" value=""/>    
<input type="hidden" name="aribaRepairMRsString" id="aribaRepairMRsString" value=""/>    

  
</div>

   <!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
</div>
<!-- Search Frame Ends -->
</td><td width="*" valign="top" align="left">
<!-- Hidden cart div starts -->
<c:set var="cartD" value="hidden"/>
<c:if test="${showcart}">
	<c:set var="cartD" value="visible"/>
</c:if>
<div id="cartDiv" class="contentArea">
	<div class="roundcont filterContainer">
 	<div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   	</div>
		<div class="roundContent">
			 <div class="boxhead"> <%-- boxhead Begins --%>
			  <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
					 Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
				--%>
				<div id="mainCartLinks"> <%-- mainUpdateLinks Begins --%>
					<span id="checkoutspan" style="display:none" ><a href="javascript:Checkout()"><fmt:message key="label.checkout"/></a></span>
					<span id="delspan" style="display:none"> | <a href="javascript:checkDeletePart()"><fmt:message key="label.delete"/></a></span>
					<span id="spaceHolder" style="visibility:hidden">H</span>
			  </div> <%-- mainUpdateLinks Ends --%>
			 </div> <%-- boxhead Ends --%>

			 <div class="dataContent">
			  <div id="cartTableDiv"></div>
			 </div>
		  </div>
		  <div class="roundbottom">
			 <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
		  </div>
		</div>
	</div>
</div>
<!-- Hidden cart div ends -->

<%-- Eng Eval div start --%>
<div id="engEvalDiv" class="contentArea" style="display:none">
	<div class="roundcont filterContainer">
 	<div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   	</div>
		<div class="roundContent">
			 <div class="dataContent">
			  <div id="engEvalTableDiv">
				  <table id="engEvalTable" width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#f0fbee" >
                      <tr><td width="10%">&nbsp;</td><td  width="80%">&nbsp;</td><td width="10%">&nbsp;</td></tr>
                      <tr><td colspan="3" class="optionTitleBoldCenter">
						  <fmt:message key="label.engevaltitle"/>
					  </td></tr>
					  <tr>
						  <td width="10%">&nbsp;</td>
						  <td width="80%" class="optionTitleLeft">
							  <fmt:message key="label.engevalmessage"/>
						  </td>
						  <td width="10%">&nbsp;</td>
					  </tr>
					  <tr><td width="10%">&nbsp;</td><td  width="80%">&nbsp;</td><td width="10%">&nbsp;</td></tr>
					  <tr><td colspan="3" class="optionTitleBoldCenter">
						  <fmt:message key="catalogAdd"/>
					  </td></tr>
					  <tr>
						  <td width="10%">&nbsp;</td>
						  <td width="80%" class="optionTitleLeft">
							  <fmt:message key="label.catalogaddmessage"/>
						  </td>
						  <td width="10%">&nbsp;</td>
					  </tr>
                      <tr><td width="10%">&nbsp;</td><td  width="80%">&nbsp;</td><td width="10%">&nbsp;</td></tr>
                      <tr><td width="10%">&nbsp;</td><td  width="80%">&nbsp;</td><td width="10%">&nbsp;</td></tr>
                  </table>
			  </div>
			 </div>
		  </div>
		  <div class="roundbottom">
			 <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
		  </div>
		</div>
	</div>
</div>
<%-- Eng Eval div end --%>

<%-- MSDS Msg div start --%>
<div id="msdsMsgDiv" class="contentArea" style="display:none">
	<div class="roundcont filterContainer">
 	<div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   	</div>
		<div class="roundContent">
			 <div class="dataContent">
			  <div id="msdsMsgTableDiv">
				  <table id="msdsMsgTable" width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#f0fbee" >
                      <tr><td width="10%">&nbsp;</td><td  width="80%">&nbsp;</td><td width="10%">&nbsp;</td></tr>
                      <tr><td width="10%">&nbsp;</td><td  width="80%">&nbsp;</td><td width="10%">&nbsp;</td></tr>
                      <tr><td width="10%">&nbsp;</td><td  width="80%">&nbsp;</td><td width="10%">&nbsp;</td></tr>
					  <tr><td colspan="3" class="optionTitleBoldCenter">
                          <fmt:message key="label.materialcatalog"/>
                      </td></tr>
					  <tr><td width="10%">&nbsp;</td><td  width="80%">&nbsp;</td><td width="10%">&nbsp;</td></tr>
					  <tr>
						  <td width="10%">&nbsp;</td>
						  <td width="80%" class="optionTitleCenter">
                            <fmt:message key="label.materialcatalogmessage"/>    
                          </td>
						  <td width="10%">&nbsp;</td>
					  </tr>
                      <tr><td width="10%">&nbsp;</td><td  width="80%">&nbsp;</td><td width="10%">&nbsp;</td></tr>
                      <tr><td width="10%">&nbsp;</td><td  width="80%">&nbsp;</td><td width="10%">&nbsp;</td></tr>
					  <tr><td width="10%">&nbsp;</td><td  width="80%">&nbsp;</td><td width="10%">&nbsp;</td></tr>
					  <tr><td width="10%">&nbsp;</td><td  width="80%">&nbsp;</td><td width="10%">&nbsp;</td></tr>
				  </table>
			  </div>
			 </div>
		  </div>
		  <div class="roundbottom">
			 <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
		  </div>
		</div>
	</div>
</div>
<%-- MSDS Msg div end --%>

</td></tr>
</table>
</tcmis:form>
</td>
</tr>

<tr><td valign="top">
<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>

	 <%-- transition --%>
<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" id="transitLabel"> 
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</td>
		</tr>
	</table>
</div>	
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display: none">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
	   <span id="updateResultLink" style="display: none">
		  <a href="javascript:resultFrameAddToCart()"><fmt:message key="label.addtocart"/></a>
		</span>
	   <span id="catalogAddLink" style="display: none">
		</span>
		<span id="newEvalLink" style="display:none"> <a href="javascript:newEval()"><fmt:message key="label.neweval"/></a></span>
		<span id="reorderEvalLink" style="display:none"> | <a href="javascript:reorderEval()"><fmt:message key="label.placeevalfromexitingitem"/></a></span>
        <span id="itemCatalogAddLink" style="display: none"></span>  
        <span id="newMsdsLink" style="display:none"> <a href="javascript:newMsds()"><fmt:message key="label.newmaterial"/></a></span>
		<span id="newApprovalCodeLink" style="display:none"></span>
		  &nbsp;
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent" id="partCatalogDiv" style="display: none">
     <iframe scrolling="no" id="partCatalogResultFrame" name="partCatalogResultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
   </div>
	<div class="dataContent" id="itemCatalogDiv" style="display: none">
     <iframe scrolling="no" id="itemCatalogResultFrame" name="itemCatalogResultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
   </div>
   <div class="dataContent" id="materialCatalogDiv" style="display: none">
     <iframe scrolling="no" id="materialCatalogResultFrame" name="materialCatalogResultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
   </div>

  	  <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>
</div><!-- Result Frame Ends -->

</td>
</tr>
</table>

<div id="checkoutHiddenDiv" style="display:none">
<form name="returnForm" id="returnForm" action="${param.HOOK_URL}" target="${param.returntarget}" method="post">
<div id="cartHiddenFormDiv">
</div>
</form>
</div>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

<!-- Input Dialog Window Begins -->
<div id="accountSysDailogWin" class="errorMessages" style="display: none;overflow: auto;">
<table width="100%" border="0" cellpadding="2" cellspacing="1">
<tr><td>&nbsp;</td></tr>
<tr>
<td align="center"  width="100%">
<select name="accountSystemSelectBox" id="accountSystemSelectBox" class="selectBox"> 
</select>
</td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
<td align="center" width="100%">
<input name="accountSysOk"  id="accountSysOk"  type="button" value="<fmt:message key="label.ok"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="accountSysOkClose();"/>
</td>
</tr>
</table>
</div>

<div id="engEvalPartTypeDailogWin" class="errorMessages" style="display: none;overflow: auto;">
<table width="100%" border="0" cellpadding="2" cellspacing="1">
<tr><td>&nbsp;</td></tr>
<tr>
<td align="center"  width="100%">
    <fmt:message key="label.productionmaterial"/>:<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input title='<fmt:message key="label.productionmaterialdefinition"/>'
           type="radio" name="productionMaterial" id="productionMaterialYes" value="Y" class="radio"/><fmt:message key="label.yes"/>
    &nbsp;<input title='<fmt:message key="label.nonproductionmaterialdefinition"/>'
           type="radio" name="productionMaterial" id="productionMaterialNo" value="N" class="radio"/><fmt:message key="label.no"/>
</td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
<td align="center" width="100%">
<input name="engEvalPartTypeOk"  id="engEvalPartTypeOk"  type="button" value="<fmt:message key="label.ok"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="engEvalPartTypeOkClose();"/>
</td>
</tr>
</table>
</div>

<div id="catalogFacilityDailogWin" class="errorMessages" style="display: none;overflow: auto;">
<table width="100%" border="0" cellpadding="2" cellspacing="1">
<tr><td>&nbsp;</td></tr>
<tr>
<td align="center"  width="100%">
<select name="catalogFacilitySelectBox" id="catalogFacilitySelectBox" class="selectBox">
</select>
</td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
<td align="center" width="100%">
<input name="catalogFacilityOk"  id="catalogFacilityOk"  type="button" value="<fmt:message key="label.ok"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="catalogFacilityOkClose();"/>
</td>
</tr>
</table>
</div>

<div id="cartCommentDailogWin" class="successMessages" style="display: none;overflow: hidden;vertical-align: top;">
<table width="100%" border="0" cellpadding="2" cellspacing="1">
<tr>
<td align="center" width="100%">
	<!-- Text area replaced with divs that scroll.
		<textarea cols="70" rows="5" class="inputBox" readonly="true" name="cartCommentText" id="cartCommentText"></textarea> 
	-->
	<div id="cartCommentDialogTextArea" name="cartCommentDialogTextArea" style="height:140px;  overflow:auto;">
		<div id="prop65Warning">
			<!-- 
				Warning symbol can be written and styled. An image was used due to the requirement that the symbol be yellow.
				<div id="prop65WarningSymbol" style="color: red; font-size: 30px;vertical-align: top;grid-column:1;width:30px;">&#x26A0;</div> 
			-->
			<img src="/images/warning.png" style="float:left; margin-right:5px;"/>
			<div id="prop65WarningText" class="optionTitleLeft" style="font-weight:bold;"></div>
	      	<br style="clear:both"/>
	      	<br/>
		</div>
		<div name="cartCommentText" id="cartCommentText" class="optionTitleLeft" ></div>
	</div>
</td>
</tr>
<tr>
<td align="center" width="100%">
<input name="cartCommentOk"  id="cartCommentOk"  type="button" value="<fmt:message key="label.continue"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="cartCommentOk();"/>
&nbsp;<input name="cartCommentCancel"  id="cartCommentCancel"  type="button" value="<fmt:message key="label.cancel"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="cartCommentCancel();"/>
</td>
</tr>
</table>
</div>
<!--  Input Dialog End -->

<iframe scrolling="no" style="display: none" id="checkOutCartFrame" name="checkOutCartFrame" width="100%" height="10" frameborder="0" marginwidth="0" src="/blank.html"></iframe>


</body>
</html:html>