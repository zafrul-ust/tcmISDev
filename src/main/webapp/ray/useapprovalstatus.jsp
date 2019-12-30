<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss currentCss="haasGlobalAjaxGrid.css" />

<link rel="stylesheet" type="text/css" href="/css/useApproval.css"></link>
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridUseApproval.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />

<script src="/js/ray/useapprovalstatus.js" language="JavaScript"></script>
<script src="/js/ray/useapprovalajax.js" language="JavaScript"></script>
<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaasNoMerge.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>

<%--<script src="/dhtmlxGrid/js/dhtmlXCommon.js"></script>
<script src="/dhtmlxGrid/js/dhtmlXGrid.js"></script>
<script src="/dhtmlxGrid/js/dhtmlXGridCell.js"></script>
<script src="/dhtmlxGrid/js/dhtmlXGrid_excell_mro.js"></script>--%>

<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>
<%--<script type="text/javascript" src="/yui/build/connection/connection.js" ></script>

<link rel="stylesheet" type="text/css" href="/yui/build/fonts/fonts.css" />
<link rel="stylesheet" type="text/css" href="/css/example.css" />
--%>

<title>
<fmt:message key="useapprovalstatus.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--

var altfacilityId = new Array(
<c:forEach var="facAppUserGrpOvBean" items="${facAppUserGrpOvBeanColl}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.facilityId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.facilityId}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altapplication = new Array();
var altapplicationdesc = new Array();
<c:forEach var="facAppUserGrpOvBean" items="${facAppUserGrpOvBeanColl}" varStatus="status">
<c:set var="currentFacilityId" value='${status.current.facilityId}'/>
<c:set var="applicationObjBeanCollection" value='${status.current.applicationVar}'/>

<c:set var="applicationCount" value='${0}'/>
altapplication["<c:out value="${currentFacilityId}"/>"] = new Array(
 <c:forEach var="applicationObjBean" items="${applicationObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${applicationCount > 0}">
    ,"<c:out value="${status1.current.application}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.application}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="applicationCount" value='${applicationCount+1}'/>
  </c:forEach>
  );

<c:set var="applicationCount" value='${0}'/>
altapplicationdesc["<c:out value="${currentFacilityId}"/>"] = new Array(
 <c:forEach var="applicationObjBean" items="${applicationObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${applicationCount > 0}">
    ,"<c:out value="${status1.current.applicationDesc}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.applicationDesc}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="applicationCount" value='${applicationCount+1}'/>
  </c:forEach>
  );
 </c:forEach>

var altusergroupid = new Array();
var altusergroupdesc = new Array();
<c:forEach var="facAppUserGrpOvBean" items="${facAppUserGrpOvBeanColl}" varStatus="status">
<c:set var="currentFacilityId" value='${status.current.facilityId}'/>
<c:set var="userGroupObjBeanCollection" value='${status.current.userGroupVar}'/>

<c:set var="userGroupCount" value='${0}'/>
altusergroupid["<c:out value="${currentFacilityId}"/>"] = new Array(
 <c:forEach var="userGroupObjBean" items="${userGroupObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${userGroupCount > 0}">
    ,"<c:out value="${status1.current.userGroupId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.userGroupId}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="userGroupCount" value='${userGroupCount+1}'/>
  </c:forEach>
  );

<c:set var="userGroupCount" value='${0}'/>
altusergroupdesc["<c:out value="${currentFacilityId}"/>"] = new Array(
 <c:forEach var="userGroupObjBean" items="${userGroupObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${userGroupCount > 0}">
    ,"<c:out value="${status1.current.userGroupDesc}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.userGroupDesc}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="userGroupCount" value='${userGroupCount+1}'/>
  </c:forEach>
  );
 </c:forEach>


var altdeliveryPoint = new Array();
var altdeliveryPointDesc = new Array();
<c:forEach var="facAppUserGrpOvBean" items="${facAppUserGrpOvBeanColl}" varStatus="status">
<c:set var="currentFacilityId" value='${status.current.facilityId}'/>
<c:set var="facilityDockObjBeanCollection" value='${status.current.facDockVar}'/>

<c:forEach var="facilityDockObjBean" items="${facilityDockObjBeanCollection}" varStatus="dockStatus">
<c:set var="currentDockLocationId" value='${dockStatus.current.dockLocationId}'/>
<c:set var="deliveryPointObjBeanCollection" value='${dockStatus.current.deliveryPointVar}'/>

<c:set var="deliveryPointCount" value='${0}'/>
altdeliveryPoint["<c:out value="${currentDockLocationId}"/>"] = new Array(
 <c:forEach var="deliveryPointObjBean" items="${deliveryPointObjBeanCollection}" varStatus="deliveryPointStatus">
  <c:choose>
   <c:when test="${deliveryPointCount > 0}">
    ,"<c:out value="${deliveryPointStatus.current.deliveryPoint}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${deliveryPointStatus.current.deliveryPoint}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="deliveryPointCount" value='${deliveryPointCount+1}'/>
  </c:forEach>
  );

<c:set var="deliveryPointCount" value='${0}'/>
altdeliveryPointDesc["<c:out value="${currentDockLocationId}"/>"] = new Array(
 <c:forEach var="deliveryPointObjBean" items="${deliveryPointObjBeanCollection}" varStatus="deliveryPointStatus">
  <c:choose>
   <c:when test="${deliveryPointCount > 0}">
    ,"<c:out value="${deliveryPointStatus.current.deliveryPointDesc}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${deliveryPointStatus.current.deliveryPointDesc}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="deliveryPointCount" value='${deliveryPointCount+1}'/>
  </c:forEach>
  );
  </c:forEach>
 </c:forEach>

<c:set var="orderQuantityRuleCount" value='${0}'/>
var orderQuantityRule = new Array(
<c:forEach var="vvUseApprovalOrderQtyRuleBean" items="${vvUseApprovalOrderQtyRuleBeanColl}" varStatus="statusPriority">
  <c:choose>
   <c:when test="${orderQuantityRuleCount > 0}">
    ,"<c:out value="${statusPriority.current.orderQuantityRule}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${statusPriority.current.orderQuantityRule}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="orderQuantityRuleCount" value='${orderQuantityRuleCount+1}'/>
</c:forEach>
  );

<c:set var="orderQuantityRuleCount" value='${0}'/>
var orderQuantityRuleDesc = new Array(
<c:forEach var="vvUseApprovalOrderQtyRuleBean" items="${vvUseApprovalOrderQtyRuleBeanColl}" varStatus="statusPriority">
  <c:choose>
   <c:when test="${orderQuantityRuleCount > 0}">
    ,"<c:out value="${statusPriority.current.orderQuantityRuleDesc}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${statusPriority.current.orderQuantityRuleDesc}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="orderQuantityRuleCount" value='${orderQuantityRuleCount+1}'/>
</c:forEach>
  );

// -->
</script>
</head>

<c:set var="submitSearch" value='${param.submitSearch}'/>
<c:set var="submitUpdate" value='${param.submitUpdate}'/>
<c:choose>
  <c:when test="${!empty submitUpdate}" >
    <body bgcolor="#ffffff" onload="buildFirstSetRowsAfterUpdate()" onresize="resizeGrid()">
  </c:when>
  <c:otherwise>
   <body bgcolor="#ffffff" onresize="resizeGrid()">
  </c:otherwise>
</c:choose>

<%--
<script type="text/javascript" src="/test/milonic_src.js"></script>
<div class=milonic><a href="http://www.milonic.com/">JavaScript Menu, DHTML Menu Powered By Milonic</a></div>
<script  type="text/javascript">
if(ns4)_d.write("<scr"+"ipt language=javascript src=/test/mmenuns4.js><\/scr"+"ipt>");
  else _d.write("<scr"+"ipt language=javascript src=/test/mmenudom.js><\/scr"+"ipt>");
</script>

<script type="text/javascript" src="/test/contextmenu.js"></script>
<script type="text/javascript" src="/test/catalog_menu_data.js"></script>
--%>

<tcmis:form action="/useapprovalstatus.do" onsubmit="return SubmitOnlyOnce();">

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
</div>

<div class="interface" id="mainPage" style="overflow:hidden;">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table border=0 width=100% >
  <tr>
  <td width="200">
  <img src="/images/tcmtitlegif.gif" border=0 align="left">
  </td>
  <td>
   <img src="/images/workareamanagement.gif" border=0 align="right">
  </td>
  </tr>
</table>
<%@ include file="title.jsp" %>
</div>

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
<td width="10%" class="optionTitleBoldRight">
<fmt:message key="label.facility"/>:
</td>

<td width="30%" class="optionTitleBoldLeft">
<c:set var="selectedFacilityId" value='${param.facilityId}'/>
<select name="facilityId" id="facilityId" class="selectBox" onchange="facilityIdChanged()">
  <c:forEach var="facAppUserGrpOvBean" items="${facAppUserGrpOvBeanColl}" varStatus="status">
  <c:set var="currentFacilityId" value='${status.current.facilityId}'/>

  <c:choose>
   <c:when test="${empty selectedFacilityId}" >
    <c:set var="selectedFacilityId" value='${status.current.facilityId}'/>
    <c:set var="applicationObjBeanJspCollection" value='${status.current.applicationVar}'/>
    <c:set var="userGroupObjBeanJspCollection" value='${status.current.userGroupVar}'/>
    <c:set var="facilityDockObjBeanJspCollection" value='${status.current.facDockVar}'/>
   </c:when>
   <c:when test="${currentFacilityId == selectedFacilityId}" >
    <c:set var="applicationObjBeanJspCollection" value='${status.current.applicationVar}'/>
    <c:set var="userGroupObjBeanJspCollection" value='${status.current.userGroupVar}'/>
    <c:set var="facilityDockObjBeanJspCollection" value='${status.current.facDockVar}'/>
   </c:when>
  </c:choose>

  <c:choose>
   <c:when test="${currentFacilityId == selectedFacilityId}">
    <option value="<c:out value="${currentFacilityId}"/>" selected><c:out value="${status.current.facilityName}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityName}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</td>


<td width="10%" class="optionTitleBoldRight">&nbsp;
   <%--<fmt:message key="label.sortorder"/>: --%>
 </td>
 <td width="20%" class="optionTitleBoldLeft">
   <%--<html:select property="sortBy" styleClass="selectBox" styleId="sortBy">
     <html:option value="USER_GROUP_ID,FAC_PART_NO" key="useapprovalstatus.label.usergroupsort"/>
     <html:option value="FAC_PART_NO,USER_GROUP_ID" key="useapprovalstatus.label.partsort"/>
   </html:select>--%>
&nbsp;
<input type="hidden" name="sortBy" id="sortBy" value="FAC_PART_NO">
 </td>

<td width="20%" class="optionTitleBoldLeft">
&nbsp;
</td>

</tr>

<tr>
<td width="10%" class="optionTitleBoldRight">
<fmt:message key="label.workarea"/>:
<br><a href="javascript:showUseApprovers()"><fmt:message key="useapprovalstatus.label.showapprovers"/></a>
</td>

<td width="30%" class="optionTitleBoldLeft">
<c:set var="selectedApplication" value='${param.application}'/>
<c:set var="applicationCount" value='${0}'/>
<select name="application" id="application" class="selectBox" onchange="applicationChanged()">
 <option value="All"><fmt:message key="label.pleaseselect"/></option>
  <c:forEach var="applicationObjBean" items="${applicationObjBeanJspCollection}" varStatus="status">
  <c:set var="applicationCount" value='${applicationCount+1}'/>
  <c:set var="currentApplication" value='${status.current.application}'/>

  <c:choose>
   <c:when test="${selectedApplication == currentApplication}">
    <option value="<c:out value="${currentApplication}"/>" selected><c:out value="${status.current.applicationDesc}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentApplication}"/>"><c:out value="${status.current.applicationDesc}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</td>

<td width="10%" class="optionTitleBoldRight">
<c:set var="showActiveOnly" value='${param.showActiveOnly}'/>
<%--<c:if test="${empty submitSearch && empty submitUpdate}">
 <c:set var="showApprovedOnly" value='Y'/>
</c:if>--%>

<c:if test="${showActiveOnly != null}" >
 <c:set var="checkBoxChecked" value='checked'/>
</c:if>
<input type="checkbox" name="showActiveOnly" id="showActiveOnly" class="radioBtns" onchange="disableGridData()" value="Y" <c:out value="${checkBoxChecked}"/>>
</td>

<td width="20%" class="optionTitleBoldLeft">
<fmt:message key="useapprovalstatus.label.showactiveonly"/>
</td>

<td width="20%" class="optionTitleBoldLeft">
&nbsp;
</td>

</tr>

<tr>
<td width="10%" class="optionTitleBoldRight">
<fmt:message key="label.usergroup"/>:
</td>

<td width="30%" class="optionTitleBoldLeft">
<c:set var="selectedUserGroupId" value='${param.userGroupId}'/>
<c:if test="${empty selectedUserGroupId}">
 <c:set var="selectedUserGroupId" value='All'/>
</c:if>

<select name="userGroupId" id="userGroupId" class="selectBox" onchange="disableGridData()">
 <%--<option value="All"><fmt:message key="label.pleaseselect"/></option>--%>
 <c:forEach var="userGroupObjBean" items="${userGroupObjBeanJspCollection}" varStatus="status">
 <c:set var="currentUserGroupId" value='${status.current.userGroupId}'/>

  <c:choose>
   <c:when test="${selectedUserGroupId == currentUserGroupId}">
    <option value="<c:out value="${currentUserGroupId}"/>" selected><c:out value="${status.current.userGroupDesc}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentUserGroupId}"/>"><c:out value="${status.current.userGroupDesc}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</td>

<td width="10%" class="optionTitleBoldRight">
   <fmt:message key="label.keywords"/>:
 </td>

<td width="20%" class="optionTitleBoldLeft">
<html:text property="searchText" styleId="searchText" styleClass="inputBox" size="40" onchange="disableGridData()" onkeypress="return !(window.event && window.event.keyCode == 13);" onkeydown="return !(window.event && window.event.keyCode == 13);"/>
</td>

<td width="20%" class="optionTitleBoldLeft">
<c:set var="showOnlyWithLimits" value='${param.showOnlyWithLimits}'/>
<c:if test="${showOnlyWithLimits != null}" >
 <c:set var="checkBoxChecked" value='checked'/>
</c:if>

<input type="checkbox" name="showOnlyWithLimits" id="showOnlyWithLimits" class="radioBtns" onchange="disableGridData()" value="Y" <c:out value="${checkBoxChecked}"/>>
<fmt:message key="useapprovalstatus.label.showonlywithlimits"/>
</td>
<%--<div id="managedWorkAreaFlag" style="display:none">
<INPUT TYPE="checkbox" name="managedWorkArea" ID="managedWorkArea" value="Y" onClick="updateManagedWorkArea()">
<B><fmt:message key="useapprovalstatus.label.workareaoverride"/></B>
</div>--%>
</tr>

<tr>
<td width="15%" colspan="5" class="optionTitleBoldLeft">
<html:button property="submitSearch" styleId="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return search()">
     <fmt:message key="label.search"/>
</html:button>

</td>
</tr>
</table>

<!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->

<div class="spacerY">&nbsp;</div>
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display:none;z-index:3;">
<div id="errorMessagesAreaTitle" class="hd"><fmt:message key="label.errors"/></div>
<div id="errorMessagesAreaBody" class="bd">
<c:set var="errorMessage" value="${errorMessage}"/>
<c:out value="${errorMessage}"/>
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->

<!-- Search results start -->
<table id="resultsMaskTable" style="display:none;" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
 <div class="boxhead">
  <div id="managedWorkAreaUpdates" style="display: none">
  <a id="managedWorkAreaLink" class="" href="javascript:updateManagedWorkArea()"></a>
  |
  <a id="submitUpdateButton" href="javascript:update()"><fmt:message key="label.update"/></a>
  |
  <a id="submitUpdateAddAll" href="javascript:updateActivateAll()"><fmt:message key="useapprovalstatus.label.activateall"/></a>
  |
  <a id="submitDeleteAll" href="javascript:updateDeleteAll()"><fmt:message key="useapprovalstatus.label.deactivateall"/></a>
  |
  </div>
  <a id="submitCreatExcelReport" href="javascript:creatExcelReport()"><fmt:message key="label.createexcelfile"/></a>
  |
  <a id="showLegendDiv" href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a>
 </div>
  <div class="dataContent">

<div id="parentGridBox" width="100%">
<div id="gridbox" width="100%" height="300px">
</div>
</div>

</div>
  </div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</div>
</td></tr>
</table>
<!-- Search results end -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
 <div id="invisibleElements" style="display: none;"></div>
<input type="hidden" name="updateAllRows" id="updateAllRows" value="">
<input type="hidden" name="selectedFacilityId" id="selectedFacilityId" value="<c:out value="${selectedFacilityId}"/>">
<input type="hidden" name="selectedApplication" id="selectedApplication" value="<c:out value="${param.application}"/>">
<input type="hidden" name="showApprovedOnly" id="showApprovedOnly" value="Y">
<input type="hidden" name="submitUpdate" id="submitUpdate" value="">
<input type="hidden" name="managedWorkArea" id="managedWorkArea" value="">
</div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->
<!-- Footer message start -->
<div id="footer" class="messageBar"></div>
<!-- Footer message end -->
</div> <!-- close of interface -->

</tcmis:form>

<script language="javascript">
YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);
</script>

<div id="win" style="z-index:3;display:none">
<div class="hd">Results Legend</div>
<div class="bd">
<table class="avoidCssSelectors">
<tr><td class="catalogData">&nbsp;</td><td class="legendText">Basic catalog information</td></tr>
<tr><td class="useApprovalData">&nbsp;</td><td class="legendText">Use approval information</td></tr>
<tr><td class="overRideData">&nbsp;</td><td class="legendText">Work Area manager override</td></tr>
<tr><td class="kanBanData">&nbsp;</td><td class="legendText">Automated ordering information</td></tr>
<tr><td class="grayData">&nbsp;</td><td class="legendText">inactive ordering restrictions</td></tr>
</table>
</div>
</div>

<div id="showApprovers" style="z-index:3;display:none">
<div id="showApproversTitle" class="hd"><fmt:message key="showapprovers.label.title"/></div>
<div id="showApproversBody" class="bd">

</div>
</div>

<c:if test="${!empty submitUpdate}">
 <script>
 //showWaitDialog();
 </script>

<script type="text/javascript">
<!--
<c:set var="dockLocationIdCount" value='${0}'/>
dockLocationId = new Array(
<c:forEach var="facilityDockObjBean" items="${facilityDockObjBeanJspCollection}" varStatus="dockLocationIdStatus">
  <c:choose>
   <c:when test="${dockLocationIdCount > 0}">
    ,"<c:out value="${dockLocationIdStatus.current.dockLocationId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${dockLocationIdStatus.current.dockLocationId}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="dockLocationIdCount" value='${dockLocationIdCount+1}'/>
 </c:forEach>
  );

<c:set var="dockLocationIdCount" value='${0}'/>
dockDesc = new Array(
<c:forEach var="facilityDockObjBean" items="${facilityDockObjBeanJspCollection}" varStatus="dockLocationIdStatus">
  <c:choose>
   <c:when test="${dockLocationIdCount > 0}">
    ,"<c:out value="${dockLocationIdStatus.current.dockDesc}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${dockLocationIdStatus.current.dockDesc}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="dockLocationIdCount" value='${dockLocationIdCount+1}'/>
 </c:forEach>
  );

<c:set var="useApprovalPermission" value=''/>
<tcmis:facilityPermission indicator="true" userGroupId="useapprovalupdate" facilityId="${selectedFacilityId}">
  <c:set var="useApprovalPermission" value='Yes'/>
</tcmis:facilityPermission>
useApprovalPermission = "<c:out value="${useApprovalPermission}"/>";

<c:set var="useApprovalStatusPermission" value=''/>
<tcmis:facilityPermission indicator="true" userGroupId="useapprovalstatusupdate" facilityId="${selectedFacilityId}">
  <c:set var="useApprovalStatusPermission" value='Yes'/>
</tcmis:facilityPermission>
useApprovalStatusPermission = "<c:out value="${useApprovalStatusPermission}"/>";

<c:set var="managedWorkArea" value='${managedWorkArea}'/>
isItmanagedWorkArea = "<c:out value="${managedWorkArea}"/>";

headerString = "<fmt:message key="label.catalog"/>,<fmt:message key="label.partnumber"/>,";
headerString +="<fmt:message key="label.partdescription"/>,<fmt:message key="useapprovalstatus.label.approvedstatus"/>,";
headerString +="<fmt:message key="useapprovalstatus.label.limit1"/>,<fmt:message key="useapprovalstatus.label.limit2"/>,";
headerString +="<fmt:message key="useapprovalstatus.label.processdesc"/>,<fmt:message key="label.active"/>,<fmt:message key="useapprovalstatus.label.limit1"/>,";
headerString +="<fmt:message key="useapprovalstatus.label.limit2"/>,<fmt:message key="useapprovalstatus.label.processdesc"/>,";
headerString +="<fmt:message key="label.orderqty"/>,<fmt:message key="useapprovalstatus.label.packaging"/>,";
headerString +="<fmt:message key="useapprovalstatus.label.estimatedcovereage"/>,<fmt:message key="label.orderqtytype"/>,";
headerString +="<fmt:message key="useapprovalstatus.label.customershiptocode"/>,";
headerString +="<fmt:message key="useapprovalstatus.label.tcmisdock"/>,<fmt:message key="useapprovalstatus.label.customerdeliverto"/>,";
headerString +="<fmt:message key="useapprovalstatus.label.tcmisdeliverto"/>,<fmt:message key="useapprovalstatus.label.requestor"/>";

<%--<BR><input type=\"checkbox\" name=\"makeAllActive\" id=\"makeAllActive\" value=\"Yes\" onclick=\"makeAllRowsActive()\">&nbsp;All--%>

<c:set var="dataCount" value='${0}'/>
<c:forEach var="useApprovalStatusViewBean" items="${useApprovalStatusViewBeanCollection}" varStatus="status">
<c:set var="limitQuantityPeriod1" value='${status.current.limitQuantityPeriod1}'/>
<c:set var="limitQuantityPeriod2" value='${status.current.limitQuantityPeriod2}'/>
<c:set var="mwLimitQuantityPeriod1" value='${status.current.mwLimitQuantityPeriod1}'/>
<c:set var="mwLimitQuantityPeriod2" value='${status.current.mwLimitQuantityPeriod2}'/>
<c:set var="mwApprovalId" value='${status.current.mwApprovalId}'/>

workAreaManagementData[<c:out value="${status.index}"/>] = {facPartNo:"<c:out value="${status.current.facPartNo}"/>",approvalStatus:"<c:out value="${status.current.approvalStatus}"/>",
catalogId:"<c:out value="${status.current.catalogId}"/>",partDescription:"<c:out value="${status.current.partDescription}"/>",
<c:if test="${dataCount == 0}" >
facilityId:"<c:out value="${status.current.facilityId}"/>",application:"<c:out value="${status.current.application}"/>",userGroupId:"<c:out value="${status.current.userGroupId}"/>",
</c:if>
limitQuantityPeriod1:"<c:out value="${limitQuantityPeriod1}"/>",daysPeriod1:"<c:out value="${status.current.daysPeriod1}"/>",
limitQuantityPeriod2:"<c:out value="${limitQuantityPeriod2}"/>",daysPeriod2:"<c:out value="${status.current.daysPeriod2}"/>",
<c:choose>
   <c:when test="${empty limitQuantityPeriod1}">
    limitQuantityPeriod1Display:"",
   </c:when>
   <c:otherwise>
    limitQuantityPeriod1Display:"<c:out value="${status.current.limitQuantityPeriod1}"/> <fmt:message key="useapprovalstatus.label.every"/> <c:out value="${status.current.daysPeriod1}"/> <fmt:message key="label.days"/>",
   </c:otherwise>
</c:choose>
<c:choose>
<c:when test="${empty limitQuantityPeriod2}">
  limitQuantityPeriod2Display:"",
 </c:when>
 <c:otherwise>
   limitQuantityPeriod2Display:"<c:out value="${status.current.limitQuantityPeriod2}"/> <fmt:message key="useapprovalstatus.label.every"/> <c:out value="${status.current.daysPeriod2}"/> <fmt:message key="label.days"/>",
  </c:otherwise>
</c:choose>
packaging:"<c:out value="${status.current.packaging}"/>",customerDeliverTo:"<c:out value="${status.current.customerDeliverTo}"/>",
dockLocationId:"<c:out value="${status.current.dockLocationId}"/>",deliveryPointBarcode:"<c:out value="${status.current.deliveryPointBarcode}"/>",
dockDeliveryPoint:"<c:out value="${status.current.dockDeliveryPoint}"/>",barcodeRequester:"<c:out value="${status.current.barcodeRequester}"/>",
barcodeRequesterName:"<c:out value="${status.current.barcodeRequesterName}"/>",processDesc:"<c:out value="${status.current.processDesc}"/>",
mwEstimateOrderQuantityPrd:"<c:out value="${status.current.mwEstimateOrderQuantityPrd}"/>",mwDaysPeriod1:"<c:out value="${status.current.mwDaysPeriod1}"/>",
<c:choose>
  <c:when test="${empty mwEstimateOrderQuantityPrd}">
   mwEstimateOrderQuantityPrdDisplay:"",
  </c:when>
  <c:otherwise>
   mwEstimateOrderQuantityPrdDisplay:"<c:out value="${status.current.mwEstimateOrderQuantityPrd}"/> <fmt:message key="label.days"/>",
  </c:otherwise>
</c:choose>
mwLimitQuantityPeriod1:"<c:out value="${mwLimitQuantityPeriod1}"/>",mwDaysPeriod2:"<c:out value="${status.current.mwDaysPeriod2}"/>",
mwLimitQuantityPeriod2:"<c:out value="${mwLimitQuantityPeriod2}"/>",mwOrderQuantityRule:"<c:out value="${status.current.mwOrderQuantityRule}"/>",
<c:choose>
  <c:when test="${empty mwLimitQuantityPeriod1}">
   mwLimitQuantityPeriod1Display:"",
  </c:when>
  <c:otherwise>
   mwLimitQuantityPeriod1Display:"<c:out value="${status.current.mwLimitQuantityPeriod1}"/> <fmt:message key="useapprovalstatus.label.every"/> <c:out value="${status.current.mwDaysPeriod1}"/> <fmt:message key="label.days"/>",
  </c:otherwise>
</c:choose>
<c:choose>
<c:when test="${empty mwLimitQuantityPeriod2}">
  mwLimitQuantityPeriod2Display:"",
 </c:when>
 <c:otherwise>
   mwLimitQuantityPeriod2Display:"<c:out value="${status.current.mwLimitQuantityPeriod2}"/> <fmt:message key="useapprovalstatus.label.every"/> <c:out value="${status.current.mwDaysPeriod2}"/> <fmt:message key="label.days"/>",
 </c:otherwise>
</c:choose>
<c:choose>
<c:when test="${empty mwApprovalId}">
  active:"<img src=/images/item_chk0.gif border=0 alt=Active align=>",
 </c:when>
 <c:otherwise>
   active:"<img src=/images/item_chk1.gif border=0 alt=Active align=>",
 </c:otherwise>
</c:choose>
mwApprovalId:"<c:out value="${mwApprovalId}"/>",mwProcessDesc:"<c:out value="${status.current.mwProcessDesc}"/>",mwOrderQuantity:"<c:out value="${status.current.mwOrderQuantity}"/>"};
<c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>

totalRows = <c:out value="${dataCount}"/>;

//clearGridCount = 0;
endingRowNumber = 0;
rowCount = 0;
//buildFirstSetRows();
//showWaitDialog();

lastSearchText = "<c:out value="${param.searchText}"/>";
lastShowActiveOnly = "<c:out value="${param.showActiveOnly}"/>";

<c:choose>
   <c:when test="${empty errorMessage}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>

</c:if>
</body>
</html:html>

