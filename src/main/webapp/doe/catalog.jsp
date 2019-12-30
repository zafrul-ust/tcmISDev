<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
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
--%>
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
<%--<script type="text/javascript" src="/js/menu/contextmenu.js"></script>--%>

<script src="/js/doe/catalog.js" language="JavaScript"></script>
<script src="/js/rightclick.js" language="JavaScript"></script>

<%-- For Calendar support --%>
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>

<%-- Add any other javascript you need for the page here --%>


<%-- These are for the Grid, uncomment if you are going to use the grid --%>
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<%-- This is for the YUI, uncomment if you will use this --%>
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<%@ include file="/common/catalog/catalogpageheader.jsp" %>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",pleaseselect:"<fmt:message key="label.pleaseselect"/>",and:"<fmt:message key="label.and"/>",
validquantity:"<fmt:message key="labels.label.validquantity"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<c:set var="doexcelpopup" value='${doexcelpopup}'/>
<c:set var="buttonCreateExcel" value='${param.buttonCreateExcel}'/>
<c:choose>
  <c:when test="${!empty doexcelpopup && !empty buttonCreateExcel}" >
    <body bgcolor="#ffffff" onLoad="doexcelpopup()">
  </c:when>
  <c:otherwise>
   <body bgcolor="#ffffff">
  </c:otherwise>
</c:choose>

<tcmis:form action="/catalog.do" onsubmit="return submitOnlyOnce();">

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
</div>

<div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table border=0 width=100% >
  <tr>
  <td width="200">
  <img src="/images/tcmtitlegif.gif" border=0 align="left">
  </td>
  <td>
   <img src="/images/catalog.gif" border=0 align="right">
  </td>
  </tr>
</table>
<%@ include file="/common/clientnavigation.jsp" %>
</div>

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="600" border="0" cellpadding="0" cellspacing="0">
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

<td width="90%" class="optionTitleBoldLeft">
<c:set var="selectedFacilityId" value='${param.facilityId}'/>
<select name="facilityId" id="facilityId" class="selectBox" onchange="facilityChanged()">

  <c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
    <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
    <c:choose>
      <c:when test="${empty selectedFacilityId}" >
        <c:set var="selectedFacilityId" value='${currentFacilityId}' />
        <c:set var="applicationSelectBeanCollection" value='${status.current.applicationBeanCollection}'/>
      </c:when>
      <c:when test="${currentFacilityId == selectedFacilityId}" >
        <c:set var="applicationSelectBeanCollection" value='${status.current.applicationBeanCollection}'/>
      </c:when>
    </c:choose>

    <c:choose>
      <c:when test="${currentFacilityId == selectedFacilityId}">
        <option value="<c:out value="${currentFacilityId}"/>" selected><c:out value="${status.current.facilityId}"/></option>
      </c:when>
      <c:otherwise>
        <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityId}"/></option>
      </c:otherwise>
    </c:choose>
  </c:forEach>
</select>
</td>

<c:if test="${empty facilityOrAllCatalog}">
<c:set var="selectedActiveForFacility" value='${"checked"}' />
</c:if>
</tr>

<tr>

<td width="10%" class="optionTitleBoldRight">
<fmt:message key="label.workarea"/>:
</td>

<td width="90%" class="optionTitleBoldLeft">
<c:set var="selectedApplicationId" value='${param.applicationId}'/>
<c:if test="${empty selectedApplicationId}" >
  <c:set var="selectedApplicationId" value="All"/>
</c:if>
<select name="applicationId" id="applicationId" class="selectBox">
 <c:choose>
    <c:when test="${selectedApplicationId == 'All'}">
      <option value="All" selected>Please Select</option>
    </c:when>
    <c:otherwise>
      <option value="All">Please Select</option>
    </c:otherwise>
  </c:choose>

  <c:set var="applicationCount" value='${0}'/>
  <c:forEach var="facLocAppBean" items="${applicationSelectBeanCollection}" varStatus="status">
    <c:set var="currentApplicationId" value='${status.current.application}'/>
    <c:set var="currentStatus"  value='${status.current.status}'/>
    <c:if test="${currentStatus == 'A'}">
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
</select>
</td>

</tr>

<tr>
<td width="10%"  class="optionTitleBoldRight">
<fmt:message key="label.search"/>:
</td>

<td width="90%"  class="optionTitleBoldLeft">
<input name="searchText" id="searchText" type="text" class="inputBox" value="<c:out value="${param.searchText}"/>" size="30">
</td>
</tr>

<tr>
<td width="10%" class="optionTitleBoldRight">
<html:submit property="submitSearch" styleId="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="document.genericForm.target='';document.genericForm.action='/tcmIS/doe/catalog.do'">
     <fmt:message key="label.search"/>
</html:submit>
</td>

<td width="90%"  class="optionTitleBoldLeft">
<html:submit property="buttonCreateExcel" styleId="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="document.genericForm.target='';document.genericForm.action='/tcmIS/doe/catalog.do'">
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

<!-- Error Messages Begins -->
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages" style="">
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->


<c:if test="${prCatalogScreenSearchBeanCollection != null}" >
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your mail table will be 100% -->

<c:set var="secondaryLabelPermission" value=''/>
<tcmis:facilityPermission indicator="true" userGroupId="secondaryLabel" facilityId="${selectedFacilityId}">
 <c:set var="secondaryLabelPermission" value='Yes'/>
</tcmis:facilityPermission>

<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead">
     <c:if test="${secondaryLabelPermission == 'Yes'}">
      <a href="#" onclick="printLabels(); return false;"><fmt:message key="label.printlabels"/></a>
     </c:if>
   </div>
  <div class="dataContent">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

<c:forEach var="prCatalogScreenSearchBean" items="${prCatalogScreenSearchBeanCollection}" varStatus="status">
<c:set var="dataCount" value='${dataCount+1}'/>

<c:if test="${status.index % 10 == 0}">
<tr>
<th width="2%"  height="38"><fmt:message key="label.print"/>
<c:if test="${secondaryLabelPermission == 'Yes' && status.index == 0}">
 <br><input type="checkbox" value="" onClick="checkAllCheckBoxes('ok')" name="checkAll" id="checkAll">
</c:if>
</th>
<th width="5%" height="38"><fmt:message key="label.catalog"/></th>
<th width="5%" height="38"><fmt:message key="label.part"/></th>
<th width="15%" height="38"><fmt:message key="label.description"/></th>
<th width="5%" height="38"><fmt:message key="label.type"/></th>
<th width="5%" height="38"><fmt:message key="label.price"/></th>
<th width="5%" height="38"><fmt:message key="catalog.label.shelflife"/></th>
<th width="5%" height="38"><fmt:message key="catalog.label.unitOfSaleQuantityPerEach"/></th>
<th width="5%" height="38"><fmt:message key="label.partuom"/></th>
<th width="5%" height="38"><fmt:message key="label.status"/></th>
<%--<th width="5%" height="38"><fmt:message key="catalog.label.numperpart"/></th>--%>
<th width="5%" height="38"><fmt:message key="label.item"/></th>
<th width="15%" height="38"><fmt:message key="inventory.label.componentdescription"/></th>
<th width="5%" height="38"><fmt:message key="inventory.label.packaging"/></th>
<th width="5%" height="38"><fmt:message key="label.grade"/></th>
<th width="10%" height="38"><fmt:message key="label.manufacturer"/></th>
<th width="10%" height="38"><fmt:message key="label.mfgpartno"/></th>
</tr>
</c:if>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value='alt'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value=''/>
  </c:otherwise>
</c:choose>

<tr class="<c:out value="${colorClass}"/>" ID="rowId<c:out value="${status.index}"/>">
<script language="JavaScript" type="text/javascript">
<!--
var rowId  =  document.getElementById("rowId<c:out value="${status.index}"/>");
rowId.attachEvent('onmouseup',function () {
        catchevent('<c:out value="${status.index}"/>');});
// -->
</script>

<c:set var="itemCollection"  value='${status.current.itemCollection}'/>
<bean:size id="listSize" name="itemCollection"/>
<c:set var="mainRowSpan" value='${status.current.rowSpan}' />

<input type="hidden" name="colorClass<c:out value="${status.index}"/>" id="colorClass<c:out value="${status.index}"/>" value="<c:out value="${colorClass}"/>" >
<input type="hidden" name="childRowsSize<c:out value="${status.index}"/>" id="childRowsSize<c:out value="${status.index}"/>" value="<c:out value="${listSize}"/>" >

<input type="hidden" name="catPartHazardViewBean[<c:out value="${status.index}"/>].catPartNo" id="catPartNo<c:out value="${status.index}"/>" value="<c:out value="${status.current.catPartNo}"/>" >
<input type="hidden" name="catPartHazardViewBean[<c:out value="${status.index}"/>].catalogId" id="catalogId<c:out value="${status.index}"/>" value="<c:out value="${status.current.catalogId}"/>" >
<%--<input type="hidden" name="partDescription<c:out value="${status.index}"/>" id="partDescription<c:out value="${status.index}"/>" value="<c:out value="${status.current.partDescription}"/>" >
<input type="hidden" name="partGroupNo<c:out value="${status.index}"/>" id="partGroupNo<c:out value="${status.index}"/>" value="<c:out value="${status.current.partGroupNo}"/>" >
--%>
<input type="hidden" name="catPartHazardViewBean[<c:out value="${status.index}"/>].inventoryGroup" id="inventoryGroup<c:out value="${status.index}"/>" value="<c:out value="${status.current.inventoryGroup}"/>" >

<td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
  <%-- Currenlty don't allow to print secondary labels for kits --%>
  <c:choose>
   <c:when test="${mainRowSpan == 1 && secondaryLabelPermission == 'Yes'}">
   <%-- <c:when test="${mainRowSpan == 1}"> --%>
    <input type="checkbox" name="catPartHazardViewBean[<c:out value="${status.index}"/>].ok" id="ok<c:out value="${status.index}"/>" value="<c:out value="${status.current.catPartNo}"/>">
   </c:when>
   <c:otherwise>
    &nbsp;
    </c:otherwise>
  </c:choose>
</td>
<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.catalogId}"/></td>
<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.catPartNo}"/></td>
<td width="15%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.partDescription}"/></td>
<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.stockingMethod}"/></td>

<c:set var="finalPrice" value='${0}'/>
<c:set var="facilityOrAllCatalog" value='${param.facilityOrAllCatalog}'/>
<c:set var="minCatalogPrice" value='${status.current.minCatalogPrice}'/>
<c:set var="maxCatalogPrice" value='${status.current.maxCatalogPrice}'/>
<c:choose>
 <c:when test="${facilityOrAllCatalog == 'All Catalog'}" >
  <c:choose>
   <c:when test="${!empty minCatalogPrice && !empty maxCatalogPrice}">
     <c:set var="finalPrice" value='${maxCatalogPrice}'/>
   </c:when>
   <c:otherwise>
     <c:if test="${!empty minCatalogPrice}">
        <c:set var="finalPrice" value='${minCatalogPrice}'/>
     </c:if>
     <c:if test="${!empty maxCatalogPrice}">
        <c:set var="finalPrice" value='${maxCatalogPrice}'/>
     </c:if>
   </c:otherwise>
  </c:choose>
 </c:when>
 <c:otherwise>
  <c:set var="finalPrice" value='${minCatalogPrice}'/>
 </c:otherwise>
</c:choose>

<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
<c:choose>
   <c:when test="${!empty finalPrice}">
     <tcmis:emptyIfZero value="${finalPrice}"><fmt:formatNumber maxFractionDigits="4" minFractionDigits="4"><c:out value="${finalPrice}"/></fmt:formatNumber></tcmis:emptyIfZero>
     <c:out value="${status.current.currencyId}"/>
   </c:when>
   <c:otherwise>
   &nbsp;
   </c:otherwise>
</c:choose>
</td>

<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
<c:set var="storageTemp" value='${status.current.storageTemp}'/>
<c:set var="shelfLife" value='${status.current.shelfLife}'/>

<c:choose>
 <c:when test="${empty storageTemp || storageTemp == ' '}" >
 &nbsp;
 </c:when>
 <c:otherwise>
   <c:choose>
    <c:when test="${shelfLife != 'Indefinite'}" >
       <c:set var="shelfLifeBasis" value='${status.current.shelfLifeBasis}'/>
       <c:if test="${!empty shelfLifeBasis}">
         <c:out value="${status.current.shelfLife}"/> <c:out value="${shelfLifeBasis}"/> @ <c:out value="${storageTemp}"/>
       </c:if>
      <c:if test="${empty shelfLifeBasis}">
         <c:out value="${status.current.shelfLife}"/> @ <c:out value="${storageTemp}"/>
       </c:if>
    </c:when>
    <c:otherwise>
     <c:out value="${status.current.shelfLife}"/> @ <c:out value="${storageTemp}"/>
    </c:otherwise>
   </c:choose>
 </c:otherwise>
</c:choose>
</td>

<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
<c:choose>
   <c:when test="${!empty status.current.unitOfSaleQuantityPerEach}">
     <tcmis:emptyIfZero value="${status.current.unitOfSaleQuantityPerEach}"><fmt:formatNumber maxFractionDigits="4" minFractionDigits="4"><c:out value="${status.current.unitOfSaleQuantityPerEach}"/></fmt:formatNumber></tcmis:emptyIfZero>
   </c:when>
   <c:otherwise>
   &nbsp;
   </c:otherwise>
</c:choose>
</td>
<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.unitOfSale}"/></td>

<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
<c:choose>
 <c:when test="${empty status.current.approvalStatus}" >
  &nbsp;
 </c:when>
 <c:otherwise>
  <c:out value="${status.current.approvalStatus}"/>
 </c:otherwise>
</c:choose>
</td>

<c:forEach var="prCatalogScreenSearchItemBean" items="${itemCollection}" varStatus="status1">
  <c:if test="${status1.index > 0 && listSize > 1 }">
   <tr align="center" class="<c:out value="${colorClass}"/>" ID="childRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/>">
   <script language="JavaScript" type="text/javascript">
   <!--
    var childRowId  =  document.getElementById("childRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/>");
     childRowId.attachEvent('onmouseup',function () {
      catchevent('<c:out value="${status.index}"/>');});
    // -->
    </script>
  </c:if>

  <c:set var="componentCollection"  value='${status1.current.componentCollection}'/>
  <bean:size id="componentSize" name="componentCollection"/>
  <input type="hidden" name="grandChildRowsSize<c:out value="${status.index}"/><c:out value="${status1.index}"/>" value="<c:out value="${componentSize}"/>" >

  <%--<td width="5%" rowspan="<c:out value="${componentSize}"/>"><c:out value="${status1.current.bundle}"/></td>--%>
  <td width="5%" rowspan="<c:out value="${componentSize}"/>"><c:out value="${status1.current.itemId}"/></td>

  <c:forEach var="prCatalogScreenSearchComponentBean" items="${componentCollection}" varStatus="status2">
    <c:if test="${status2.index > 0 && componentSize > 1 }">
     <tr align="center" class="<c:out value="${colorClass}"/>" ID="grnadChildRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/><c:out value="${status2.index}"/>">
     <script language="JavaScript" type="text/javascript">
     <!--
     var childRowId  =  document.getElementById("grnadChildRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/><c:out value="${status2.index}"/>");
     childRowId.attachEvent('onmouseup',function () {
      catchevent('<c:out value="${status.index}"/>');});
     // -->
     </script>
    </c:if>

    <td width="15%"><c:out value="${status2.current.materialDesc}"/></td>
    <td width="5%"><c:out value="${status2.current.packaging}"/></td>
    <td width="5%"><c:out value="${status2.current.grade}"/></td>
    <td width="10%"><c:out value="${status2.current.mfgDesc}"/></td>
    <td width="10%"><c:out value="${status2.current.mfgPartNo}"/></td>

    <c:if test="${status2.index > 0 || componentSize ==1 }">
    </tr>
    </c:if>
  </c:forEach>

  <c:if test="${status1.index > 0 || listSize ==1 }">
   </tr>
  </c:if>
 </c:forEach>

</c:forEach>

   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty prCatalogScreenSearchBeanCollection}" >

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
</td></tr>
</table>
<!-- Search results end -->
</c:if>

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
<html:hidden property="facilityOrAllCatalog" value="true"/>
<html:hidden property="workAreaApprovedOnly" value=""/>
<input type="hidden" name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>">
</div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->
</div> <!-- close of interface -->

</tcmis:form>

<div id="ie5menu" class="skin0" onMouseover="highlightie5()" onMouseout="lowlightie5()" onClick="jumptoie5();">
</div>

</body>
</html:html>