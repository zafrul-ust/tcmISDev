<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />

<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->

<script type="text/javascript" src="/js/hub/monthlyinventorydetail.js"></script>

<script language="JavaScript" type="text/javascript">
<!--

var altcompanyid = new Array(
<c:forEach var="companyFacInvoiceDateOvBean" items="${companyFacInvoiceDateOvBeanColl}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.companyId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.companyId}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altfacilityid = new Array();
var altfacilityName = new Array();
<c:forEach var="companyFacInvoiceDateOvBean" items="${companyFacInvoiceDateOvBeanColl}" varStatus="status">
<c:set var="currentCompanyId" value='${status.current.companyId}'/>
<c:set var="facilityInvociePeriodObjBeanCollection" value='${status.current.facInvoicePeriodVar}'/>

<c:set var="facilityCount" value='${0}'/>
altfacilityid["<c:out value="${currentCompanyId}"/>"] = new Array(
 <c:forEach var="facilityInvociePeriodObjBean" items="${facilityInvociePeriodObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${facilityCount > 0}">
    ,"<c:out value="${status1.current.facilityId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.facilityId}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="facilityCount" value='${facilityCount+1}'/>
  </c:forEach>
  );

<c:set var="facilityCount" value='${0}'/>
altfacilityName["<c:out value="${currentCompanyId}"/>"] = new Array(
 <c:forEach var="facilityInvociePeriodObjBean" items="${facilityInvociePeriodObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${facilityCount > 0}">
    ,"<c:out value="${status1.current.facilityName}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.facilityName}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="facilityCount" value='${facilityCount+1}'/>
  </c:forEach>
  );
 </c:forEach>


var altinvoiceperiod = new Array();
var altenddate = new Array();
<c:forEach var="companyFacInvoiceDateOvBean" items="${companyFacInvoiceDateOvBeanColl}" varStatus="status">
<c:set var="currentCompanyId" value='${status.current.companyId}'/>
<c:set var="facilityInvociePeriodObjBeanCollection" value='${status.current.facInvoicePeriodVar}'/>

<c:forEach var="facilityInvociePeriodObjBean" items="${facilityInvociePeriodObjBeanCollection}" varStatus="status1">
<c:set var="currentFacilityId" value='${status1.current.facilityId}'/>
<c:set var="inovicePeriodObjBeanCollection" value='${status1.current.invoicePeriodVar}'/>

<c:set var="invoiceCount" value='${0}'/>
altinvoiceperiod["<c:out value="${currentFacilityId}"/>"] = new Array(
 <c:forEach var="inovicePeriodObjBean" items="${inovicePeriodObjBeanCollection}" varStatus="status2">
  <c:choose>
   <c:when test="${invoiceCount > 0}">
    ,"<c:out value="${status2.current.invoicePeriod}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status2.current.invoicePeriod}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="invoiceCount" value='${invoiceCount+1}'/>
  </c:forEach>
  );

<c:set var="invoiceCount" value='${0}'/>
altenddate["<c:out value="${currentFacilityId}"/>"] = new Array(
 <c:forEach var="inovicePeriodObjBean" items="${inovicePeriodObjBeanCollection}" varStatus="status2">
 <fmt:formatDate var="formattedEndDate" value="${status2.current.endDate}" pattern="MM/dd/yyyy"/>

  <c:choose>
   <c:when test="${invoiceCount > 0}">
    ,"<c:out value="${formattedEndDate}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${formattedEndDate}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="invoiceCount" value='${invoiceCount+1}'/>
  </c:forEach>
  );
 </c:forEach>
 </c:forEach>
// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",pleaseselect:"<fmt:message key="label.pleaseselect"/>",
facilityid:"<fmt:message key="label.facilityid"/>",invoicedate:"<fmt:message key="label.invoicedate"/>",validvalues:"<fmt:message key="label.validvalues"/>",
all:"<fmt:message key="label.all"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="invoiceinventorydetail.title"/>
</title>

</head>

<c:set var="userAction" value='${param.userAction}'/>
<c:set var="doexcelpopup" value='${doexcelpopup}'/>
<c:set var="buttonCreateExcel" value='${param.buttonCreateExcel}'/>
<c:choose>
  <c:when test="${!empty doexcelpopup && !empty buttonCreateExcel}" >
    <body bgcolor="#ffffff" text="#000000" onLoad="doexcelpopup()">
  </c:when>
  <c:otherwise>
   <body bgcolor="#ffffff" text="#000000">
  </c:otherwise>
</c:choose>
<c:if test="org.apache.struts.action.MESSAGE == null">
  <div class="errorMessages">
    <fmt:message key="error.resourcesnotloaded"/>
  </div>
</c:if>

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>

 <div class="interface" id="mainPage">

<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->

<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</td>
<td>
<img src="/images/tcmistcmis32.gif" border=0 align="right">
</td>
</tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
   <td width="70%" class="headingl">
<fmt:message key="invoiceinventorydetail.title"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#ffffff" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
</tr>
</table>
</div>

<tcmis:form action="/monthlyinventorydetail.do" onsubmit="return submitOnlyOnce();">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="640" border="0" cellpadding="0" cellspacing="0">
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
        <td width="5%" class="optionTitleBoldRight">
          <fmt:message key="label.company"/>:
        </td>
<td width="15%" class="optionTitleBoldLeft">
<c:set var="selectedCompanyId" value='${param.companyId}'/>
<select name="companyId" id="companyId" class="selectBox" onchange="companyIdchanged()">
  <c:forEach var="companyFacInvoiceDateOvBean" items="${companyFacInvoiceDateOvBeanColl}" varStatus="status">
  <c:set var="currentCompanyId" value='${status.current.companyId}'/>

  <c:choose>
   <c:when test="${empty selectedCompanyId}" >
    <c:set var="selectedCompanyId" value='${status.current.companyId}'/>
    <c:set var="facilityInvociePeriodObjBeanJspCollection" value='${status.current.facInvoicePeriodVar}'/>
   </c:when>
   <c:when test="${currentCompanyId == selectedCompanyId}" >
    <c:set var="facilityInvociePeriodObjBeanJspCollection" value='${status.current.facInvoicePeriodVar}'/>
   </c:when>
  </c:choose>

  <c:choose>
   <c:when test="${currentCompanyId == selectedCompanyId}">
    <option value="<c:out value="${currentCompanyId}"/>" selected><c:out value="${status.current.companyName}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentCompanyId}"/>"><c:out value="${status.current.companyName}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</td>

<td width="10%" class="optionTitleBoldLeft">
<c:set var="selectedUnitsOrDollars" value='${param.unitsOrDollars}'/>
<c:if test="${empty selectedUnitsOrDollars}">
<c:set var="selectedUnitsOrDollars" value='units'/>
</c:if>

<input type="radio" class="radioBtns" name="unitsOrDollars" id="unitsOrDollars" value="units" <c:if test="${selectedUnitsOrDollars == 'units'}">checked</c:if>><fmt:message key="monthlyinventorydetail.label.units"/>

<input type="radio" class="radioBtns" name="unitsOrDollars" id="unitsOrDollars" value="dollars" <c:if test="${selectedUnitsOrDollars == 'dollars'}">checked</c:if>><fmt:message key="monthlyinventorydetail.label.dollars"/>

</td>
</tr>

<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.facility"/>:
</td>

<td width="15%" class="optionTitleBoldLeft">
<c:set var="selectedFacilityId" value='${param.facilityId}'/>
<c:set var="facilityIdCount" value='${0}'/>
<select name="facilityId" id="facilityId" class="selectBox" onchange="facilityIdChanged()">
 <option value="All"><fmt:message key="label.pleaseselect"/></option>
  <c:forEach var="facilityInvociePeriodObjBean" items="${facilityInvociePeriodObjBeanJspCollection}" varStatus="status">
  <c:set var="facilityIdCount" value='${facilityIdCount+1}'/>
  <c:set var="currentFacilityId" value='${status.current.facilityId}'/>

  <c:choose>
   <c:when test="${empty selectedFacilityId}" >
    <%--<c:set var="selectedFacilityId" value='${status.current.facilityId}'/>
    <c:set var="inovicePeriodObjBeanJspCollection" value='${status.current.invoicePeriodVar}'/>--%>
   </c:when>
   <c:when test="${currentFacilityId == selectedFacilityId}" >
    <c:set var="inovicePeriodObjBeanJspCollection" value='${status.current.invoicePeriodVar}'/>
   </c:when>
  </c:choose>

  <c:choose>
   <c:when test="${selectedFacilityId == currentFacilityId}">
    <option value="<c:out value="${currentFacilityId}"/>" selected><c:out value="${status.current.facilityName}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityName}"/></option>
   </c:otherwise>
  </c:choose>

  </c:forEach>
<%--  <c:if test="${facilityIdCount == 0}">
   <option value="All"><fmt:message key="label.pleaseselect"/></option>
  </c:if>--%>
</select>
</td>

<td width="10%" class="optionTitleBoldLeft">

<html:submit property="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return search()">
     <fmt:message key="label.search"/>
</html:submit>
</td>
</tr>

<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="monthlyinventorydetail.label.invoicedate"/>
</td>

<td width="15%" class="optionTitleBoldLeft">
<c:set var="selectedInvoiceDate" value='${param.invoiceDate}'/>
<%--
 This variable is named InvocieDate, but is actually a invoice period which correspnds to a invoice Date
  --%>
<select name="invoiceDate" id="invoiceDate" class="selectBox">
 <option value="All"><fmt:message key="label.pleaseselect"/></option>
 <c:forEach var="inovicePeriodObjBean" items="${inovicePeriodObjBeanJspCollection}" varStatus="status">
 <c:set var="currentInvoiceDate" value='${status.current.invoicePeriod}'/>
  <fmt:formatDate var="formattedDate" value="${status.current.endDate}" pattern="MM/dd/yyyy"/>

  <c:choose>
   <c:when test="${selectedInvoiceDate == currentInvoiceDate}">
    <option value="<c:out value="${currentInvoiceDate}"/>" selected><c:out value="${formattedDate}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentInvoiceDate}"/>"><c:out value="${formattedDate}"/></option>
   </c:otherwise>
  </c:choose>

<%--  <c:choose>
   <c:when test="${selectedInvoiceDate == 'All'}">
    <option value="<c:out value="${currentInvoiceDate}"/>"><c:out value="${formattedDate}"/></option>
   </c:when>
   <c:when test="${selectedInvoiceDate == currentInvoiceDate}">
     <c:choose>
      <c:when test="${userAction == 'getinvociedates'}">
       <option value="<c:out value="${currentInvoiceDate}"/>"><c:out value="${formattedDate}"/></option>
      </c:when>
      <c:otherwise>
       <option value="<c:out value="${currentInvoiceDate}"/>" selected><c:out value="${formattedDate}"/></option>
      </c:otherwise>
     </c:choose>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentInvoiceDate}"/>"><c:out value="${formattedDate}"/></option>
   </c:otherwise>
  </c:choose>--%>
  </c:forEach>
</select>
</td>

<td width="10%" class="optionTitleBoldLeft">

<html:submit property="buttonCreateExcel" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return createxlsreport()">
     <fmt:message key="label.createexcelfile"/>
</html:submit>
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

<div class="spacerY">&nbsp;

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->

<!-- Search results start -->
<c:if test="${monthlyInventoryDetailViewBeanCollection != null}" >
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
    <div class="boxhead"></div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

<c:set var="dataCount" value='${0}'/>
<c:set var="biInventoryValueTotal"  value='${0}'/>
<c:set var="biHaasOwnedValueTotal"  value='${0}'/>
<c:set var="biConsignedValueTotal"  value='${0}'/>
<c:set var="biCustomerOwnedValueTotal"  value='${0}'/>
<c:set var="recInventoryValueTotal"  value='${0}'/>
<c:set var="recHaasOwnedValueTotal"  value='${0}'/>
<c:set var="recConsignedValueTotal"  value='${0}'/>
<c:set var="recCustomerOwnedValueTotal"  value='${0}'/>
<c:set var="eiInventoryValueTotal"  value='${0}'/>
<c:set var="eiHaasOwnedValueTotal"  value='${0}'/>
<c:set var="eiConsignedValueTotal"  value='${0}'/>
<c:set var="eiCustomerOwnedValueTotal"  value='${0}'/>
<c:set var="netUsageValueTotal"  value='${0}'/>
<c:set var="usageHaasOwnedValueTotal"  value='${0}'/>
<c:set var="usageConsignedValueTotal"  value='${0}'/>
<c:set var="usageCustomerOwnedValueTotal"  value='${0}'/>

<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">

<c:forEach var="monthlyInventoryDetailViewBean" items="${monthlyInventoryDetailViewBeanCollection}" varStatus="status">

<c:if test="${status.index % 10 == 0}">
<c:set var="dataCount" value='${dataCount+1}'/>

<div class="dataContent">
    <table width="100%" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

<tr>
<th rowspan="2" width="5%"><fmt:message key="label.item"/></th>
<th rowspan="2" width="5%"><fmt:message key="label.partnumber"/></th>
<th rowspan="2" width="5%"><fmt:message key="label.productname"/></th>
<th rowspan="2" width="5%"><fmt:message key="label.packaging"/></th>
<th rowspan="2" width="5%"><fmt:message key="label.countuom"/></th>
<c:choose>
  <c:when test="${selectedUnitsOrDollars == 'units'}" >
    <th width="4%"  height="15" colspan="4"><fmt:message key="label.startinventory"/></th>
    <th width="4%"  height="15" colspan="4"><fmt:message key="label.received"/></th>
    <th width="4%"  height="15" colspan="4"><fmt:message key="label.endinventory"/></th>
    <th width="4%"  height="15" colspan="4"><fmt:message key="label.usage"/></th>
 </c:when>
  <c:otherwise>
    <th rowspan="2" width="5%"><fmt:message key="label.currency"/></th>
    <th width="4%"  height="15" colspan="4"><fmt:message key="label.startinventoryvalue"/></th>
    <th width="4%"  height="15" colspan="4"><fmt:message key="label.receivedvalue"/></th>
    <th width="4%"  height="15" colspan="4"><fmt:message key="label.endinventoryvalue"/></th>
    <th width="4%"  height="15" colspan="4"><fmt:message key="label.usagevalue"/></th>
 </c:otherwise>
</c:choose>
</tr>

<tr align="center">
<th width="5%"><fmt:message key="label.haas"/></th>
<th width="5%"><fmt:message key="label.consigned"/></th>
<th width="5%"><fmt:message key="label.customer"/></th>
<th width="5%"><fmt:message key="label.total"/></th>

<th width="5%"><fmt:message key="label.haas"/></th>
<th width="5%"><fmt:message key="label.consigned"/></th>
<th width="5%"><fmt:message key="label.customer"/></th>
<th width="5%"><fmt:message key="label.total"/></th>

<th width="5%"><fmt:message key="label.haas"/></th>
<th width="5%"><fmt:message key="label.consigned"/></th>
<th width="5%"><fmt:message key="label.customer"/></th>
<th width="5%"><fmt:message key="label.total"/></th>

<th width="5%"><fmt:message key="label.haas"/></th>
<th width="5%"><fmt:message key="label.consigned"/></th>
<th width="5%"><fmt:message key="label.customer"/></th>
<th width="5%"><fmt:message key="label.total"/></th>
</tr>

</c:if>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value=''/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='alt'/>
  </c:otherwise>
</c:choose>

<tr class="<c:out value="${colorClass}"/>">
  <td width="5%"><c:out value="${status.current.itemId}"/></td>
  <td width="5%"><c:out value="${status.current.catPartNo}"/></td>
  <td width="5%"><c:out value="${status.current.itemDesc}"/></td>
  <td width="5%"><c:out value="${status.current.packaging}"/></td>
  <td width="5%"><b><c:out value="${status.current.countUom}"/></b></td>

<c:choose>
  <c:when test="${selectedUnitsOrDollars == 'units'}" >
<%--
<c:set var="biConsignedQuantity" value='${status.current.biConsignedQuantity}'/>
<tcmis:facilityPermission indicator="false" userGroupId="Inventory" facilityId="${status.current.lineItem}">
<c:out value="${status.current.biHaasOwnedQuantity}"/>
--%>

<%--

<c:set var="biConsignedQuantity">
<fmt:formatNumber maxFractionDigits="2"><c:out value="${status.current.biConsignedQuantity}"/></fmt:formatNumber>
</c:set>

<tcmis:blankIfZero>
<fmt:formatNumber maxFractionDigits="2"><c:out value="${status.current.biConsignedQuantity}"/></fmt:formatNumber>
</tcmis:blankIfZero>

<tcmis:emptyIfZeroTag value="${status.current.recHaasOwnedQuantity}">
<fmt:formatNumber maxFractionDigits="2"><c:out value="${status.current.recHaasOwnedQuantity}"/></fmt:formatNumber>
</tcmis:emptyIfZeroTag>
--%>

  <td width="5%"><tcmis:emptyIfZero value="${status.current.biHaasOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.biHaasOwnedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.biConsignedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.biConsignedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.biCustomerOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.biCustomerOwnedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.biInventory}"/></fmt:formatNumber></b></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.recHaasOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.recHaasOwnedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.recConsignedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.recConsignedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.recHaasOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.recCustomerOwnedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.quantityReceived}"/></fmt:formatNumber></b></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.eiHaasOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.eiHaasOwnedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.eiConsignedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.eiConsignedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.eiCustomerOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.eiCustomerOwnedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.eiInventory}"/></fmt:formatNumber></b></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.usageHaasOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.usageHaasOwnedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.usageConsignedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.usageConsignedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.usageCustomerOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.usageCustomerOwnedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.netUsageQuantity}"/></fmt:formatNumber></b></td>
  </c:when>
  <c:otherwise>

<c:set var="biInventoryValue"  value='${status.current.biInventoryValue}'/>
<c:set var="biHaasOwnedValue"  value='${status.current.biHaasOwnedValue}'/>
<c:set var="biConsignedValue"  value='${status.current.biConsignedValue}'/>
<c:set var="biCustomerOwnedValue"  value='${status.current.biCustomerOwnedValue}'/>
<c:set var="recInventoryValue"  value='${status.current.recInventoryValue}'/>
<c:set var="recHaasOwnedValue"  value='${status.current.recHaasOwnedValue}'/>
<c:set var="recConsignedValue"  value='${status.current.recConsignedValue}'/>
<c:set var="recCustomerOwnedValue"  value='${status.current.recCustomerOwnedValue}'/>
<c:set var="eiInventoryValue"  value='${status.current.eiInventoryValue}'/>
<c:set var="eiHaasOwnedValue"  value='${status.current.eiHaasOwnedValue}'/>
<c:set var="eiConsignedValue"  value='${status.current.eiConsignedValue}'/>
<c:set var="eiCustomerOwnedValue"  value='${status.current.eiCustomerOwnedValue}'/>
<c:set var="netUsageValue"  value='${status.current.netUsageValue}'/>
<c:set var="usageHaasOwnedValue"  value='${status.current.usageHaasOwnedValue}'/>
<c:set var="usageConsignedValue"  value='${status.current.usageConsignedValue}'/>
<c:set var="usageCustomerOwnedValue"  value='${status.current.usageCustomerOwnedValue}'/>


<%--<c:set var="biInventoryValueTotal"><tcmis:addBigDecimalsTag bigDecimalNumber1="${biInventoryValue}" bigDecimalNumber2="${biInventoryValueTotal}" /></c:set>
<c:set var="biHaasOwnedValueTotal"><tcmis:addBigDecimalsTag bigDecimalNumber1="${biHaasOwnedValue}" bigDecimalNumber2="${biHaasOwnedValueTotal}" /></c:set>
<c:set var="biConsignedValueTotal"><tcmis:addBigDecimalsTag bigDecimalNumber1="${biConsignedValue}" bigDecimalNumber2="${biConsignedValueTotal}" /></c:set>
<c:set var="biCustomerOwnedValueTotal"><tcmis:addBigDecimalsTag bigDecimalNumber1="${biCustomerOwnedValue}" bigDecimalNumber2="${biCustomerOwnedValueTotal}" /></c:set>
<c:set var="recInventoryValueTotal"><tcmis:addBigDecimalsTag bigDecimalNumber1="${recInventoryValue}" bigDecimalNumber2="${recInventoryValueTotal}" /></c:set>
<c:set var="recHaasOwnedValueTotal"><tcmis:addBigDecimalsTag bigDecimalNumber1="${recHaasOwnedValue}" bigDecimalNumber2="${recHaasOwnedValueTotal}" /></c:set>
<c:set var="recConsignedValueTotal"><tcmis:addBigDecimalsTag bigDecimalNumber1="${recConsignedValue}" bigDecimalNumber2="${recConsignedValueTotal}" /></c:set>
<c:set var="recCustomerOwnedValueTotal"><tcmis:addBigDecimalsTag bigDecimalNumber1="${recCustomerOwnedValue}" bigDecimalNumber2="${recCustomerOwnedValueTotal}" /></c:set>
<c:set var="eiInventoryValueTotal"><tcmis:addBigDecimalsTag bigDecimalNumber1="${eiInventoryValue}" bigDecimalNumber2="${eiInventoryValueTotal}" /></c:set>
<c:set var="eiHaasOwnedValueTotal"><tcmis:addBigDecimalsTag bigDecimalNumber1="${eiHaasOwnedValue}" bigDecimalNumber2="${eiHaasOwnedValueTotal}" /></c:set>
<c:set var="eiConsignedValueTotal"><tcmis:addBigDecimalsTag bigDecimalNumber1="${eiConsignedValue}" bigDecimalNumber2="${eiConsignedValueTotal}" /></c:set>
<c:set var="eiCustomerOwnedValueTotal"><tcmis:addBigDecimalsTag bigDecimalNumber1="${eiCustomerOwnedValue}" bigDecimalNumber2="${eiCustomerOwnedValueTotal}" /></c:set>
<c:set var="netUsageValueTotal"><tcmis:addBigDecimalsTag bigDecimalNumber1="${netUsageValue}" bigDecimalNumber2="${netUsageValueTotal}" /></c:set>
<c:set var="usageHaasOwnedValueTotal"><tcmis:addBigDecimalsTag bigDecimalNumber1="${usageHaasOwnedValue}" bigDecimalNumber2="${usageHaasOwnedValueTotal}" /></c:set>
<c:set var="usageConsignedValueTotal"><tcmis:addBigDecimalsTag bigDecimalNumber1="${usageConsignedValue}" bigDecimalNumber2="${usageConsignedValueTotal}" /></c:set>
<c:set var="usageCustomerOwnedValueTotal"><tcmis:addBigDecimalsTag bigDecimalNumber1="${usageCustomerOwnedValue}" bigDecimalNumber2="${usageCustomerOwnedValueTotal}" /></c:set>
--%>

<%--
<c:set var="biHaasOwnedValueTotal">
<tcmis:addBigDecimalsTag bigDecimalNumber1="${status.current.recInventoryValue}" bigDecimalNumber1="${biHaasOwnedValueTotal}" />
</c:set>

<c:out value="${biHaasOwnedValueTotal}" />
<c:set var="biHaasOwnedValueTotal" value='${biHaasOwnedValueTotal} + ${status.current.recInventoryValue}'/>
<c:set var="biHaasOwnedValueTotal">
<tcmis:addBigDecimalsTag valueToCheck="${status.current.recCustomerOwnedValue}" valueToCheck1="${biHaasOwnedValueTotal}" />
</c:set>
--%>

  <td width="5%"><c:out value="${status.current.currencyId}"/></td>
  <td width="5%"><tcmis:emptyIfZero value="${biHaasOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${biHaasOwnedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${biConsignedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${biConsignedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${biCustomerOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${biCustomerOwnedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${biInventoryValue}"/></fmt:formatNumber></b></td>
  <td width="5%"><tcmis:emptyIfZero value="${recHaasOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${recHaasOwnedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${recConsignedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${recConsignedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${recCustomerOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${recCustomerOwnedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${recInventoryValue}"/></fmt:formatNumber></b></td>
  <td width="5%"><tcmis:emptyIfZero value="${eiHaasOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${eiHaasOwnedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${eiConsignedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${eiConsignedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${eiCustomerOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${eiCustomerOwnedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${eiInventoryValue}"/></fmt:formatNumber></b></td>
  <td width="5%"><tcmis:emptyIfZero value="${usageHaasOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${usageHaasOwnedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${usageConsignedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${usageConsignedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${usageCustomerOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${usageCustomerOwnedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${netUsageValue}"/></fmt:formatNumber></b></td>
  </c:otherwise>
</c:choose>
</tr>
</c:forEach>

<c:if test="${dataCount > 0}">
<c:choose>
  <c:when test="${selectedUnitsOrDollars == 'dollars'}" >
  <tcmis:monthlyInventoryDetailTotalsTag indicator="hubpage"/>

  <%--<tr align="center">
  <td height="40" COLSPAN="4" width="5%"></td>
  <td width="5%">&nbsp;</td>
  <td width="5%"><b>Total:</b></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${biHaasOwnedValueTotal}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${biConsignedValueTotal}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${biCustomerOwnedValueTotal}"/></fmt:formatNumber></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${biInventoryValueTotal}"/></fmt:formatNumber></b></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${recHaasOwnedValueTotal}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${recConsignedValueTotal}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${recCustomerOwnedValueTotal}"/></fmt:formatNumber></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${recInventoryValueTotal}"/></fmt:formatNumber></b></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${eiHaasOwnedValueTotal}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${eiConsignedValueTotal}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${eiCustomerOwnedValueTotal}"/></fmt:formatNumber></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${eiInventoryValueTotal}"/></fmt:formatNumber></b></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${usageHaasOwnedValueTotal}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${usageConsignedValueTotal}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${usageCustomerOwnedValueTotal}"/></fmt:formatNumber></td>
  <td width="5%"><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${netUsageValueTotal}"/></fmt:formatNumber></b></td>
  </tr>
  --%>
  </c:when>
</c:choose>
</c:if>
</table>

 <!-- If the collection is empty say no data found -->
   <c:if test="${empty monthlyInventoryDetailViewBeanCollection}" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>

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
</c:if>
</tcmis:form>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="userAction" id="userAction" value="">
</div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->



</div>
</body>
</html:html>

