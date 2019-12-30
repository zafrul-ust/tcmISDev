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
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/dailyinventoryreport.js"></script>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",dateformat:"<fmt:message key="label.dateformat"/>",date:"<fmt:message key="label.date"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
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
<fmt:message key="dailyInventoryreport.label.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
var althubid = new Array(
<c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.branchPlant}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.branchPlant}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altinvid = new Array();
var altinvName = new Array();
<c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
<c:set var="currentHub" value='${status.current.branchPlant}'/>
<c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>

altinvid["<c:out value="${currentHub}"/>"] = new Array(
 <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
    ,"<c:out value="${status1.current.inventoryGroup}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.inventoryGroup}"/>"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );

altinvName["<c:out value="${currentHub}"/>"] = new Array(
 <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
    ,"<c:out value="${status1.current.inventoryGroupName}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.inventoryGroupName}"/>"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );
 </c:forEach>
// -->
</script>
</head>

<c:set var="doexcelpopup" value='${doexcelpopup}'/>
<c:choose>
  <c:when test="${!empty doexcelpopup}" >
   <body onLoad="doexcelpopup()" bgcolor="#ffffff">
  </c:when>
  <c:otherwise>
   <body>
  </c:otherwise>
</c:choose>

<c:set var="submitSearch" value='${param.submitSearch}'/>
<c:set var="submitCreateReport" value='${param.buttonCreateExcel}'/>

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
 <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>

<div class="interface" id="mainPage"><!-- start of interface-->

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

<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="70%" class="headingl">
      <fmt:message key="dailyInventoryreport.label.title"/>
    </td>
    <td width="30%" align="right" height="22" class="headingr">

      <html:link style="color:#FFFFFF" forward="home">
        <fmt:message key="label.home"/>
      </html:link>
    </td>
  </tr>
</table>
</div>

<c:set var="dailyinventoryreportPermission" value=''/>
<tcmis:facilityPermission indicator="true" userGroupId="dailyinventoryreport">
 <c:set var="dailyinventoryreportPermission" value='Yes'/>
</tcmis:facilityPermission>

<c:choose>
<c:when test="${dailyinventoryreportPermission == 'Yes'}">
<tcmis:form action="/dailyinventoryreport.do" onsubmit="return submitOnlyOnce();">


<!-- close of topNavigation-->

<div class="contentArea">

  <!-- start of contentArea-->

<!-- Search Option Begins -->
<table id="searchMaskTable" width="750" border="0" cellpadding="0" cellspacing="0">
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
          <fmt:message key="label.hub"/>:
        </td>
        <td width="10%" class="optionTitleBoldLeft">
          <c:set var="selectedHub" value='${param.hub}'/>
          <c:set var="selectedHubName" value=''/>
          <select name="hub" id="hub" onchange="hubchanged()" class="selectBox">
            <c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
              <c:set var="currentHub" value='${status.current.branchPlant}'/>
              <c:choose>
                <c:when test="${empty selectedHub}" >
                  <c:set var="selectedHub" value='${status.current.branchPlant}'/>
                  <c:set var="selectedHubName" value="${status.current.hubName}"/>
                  <c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>
                </c:when>
                <c:when test="${currentHub == selectedHub}" >
                  <c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>
                </c:when>
              </c:choose>

              <c:choose>
                <c:when test="${currentHub == selectedHub}">
    <option value="<c:out value="${currentHub}"/>" selected><c:out value="${status.current.hubName}"/></option>
    <c:set var="selectedHubName" value="${status.current.hubName}"/>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentHub}"/>"><c:out value="${status.current.hubName}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
 </select>
 </td>
 <td width="5%" class="optionTitleBoldRight">
   <fmt:message key="dailyInventoryreport.label.ondate"/>:<br>(<fmt:message key="label.dateformat"/>)
 </td>

<td width="10%" class="optionTitleBoldLeft">
<c:choose>
 <c:when test="${param.dailyDate == null || empty param.dailyDate}">
  <fmt:formatDate var="formattedDailyDate" value="${defaultDailyDate}" pattern="MM/dd/yyyy"/>
 </c:when>
 <c:otherwise>
  <c:set var="formattedDailyDate" value='${param.dailyDate}'/>
 </c:otherwise>
</c:choose>
<input type="text" name="dailyDate" id="dailyDate" value="<c:out value="${formattedDailyDate}"/>" size="8" maxlength="10" class="inputBox"><a href="javascript: void(0);" id="linkdailyDate" onClick="return getCalendar(document.genericForm.dailyDate);" style="color:#0000ff;cursor:pointer;text-decoration:underline">&diams;</a>
</td>

 <td width="5%" class="optionTitleBoldLeft">

<html:submit property="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return search()">
     <fmt:message key="label.search"/>
</html:submit>
 </td>
</tr>

<tr>
 <td width="5%" class="optionTitleBoldRight">
 <fmt:message key="label.inventorygroup"/>:
 </td>
 <td width="10%" class="optionTitleBoldLeft">
 <c:set var="selectedIg" value='${param.inventoryGroup}'/>
 <c:set var="invenGroupCount" value='${0}'/>
 <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
  <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status">
    <c:set var="invenGroupCount" value='${invenGroupCount+1}'/>
    <c:set var="currentIg" value='${status.current.inventoryGroup}'/>
    <c:choose>
      <c:when test="${empty selectedIg}" >
        <c:set var="selectedIg" value=""/>
      </c:when>
      <c:when test="${currentIg == selectedIg}" >
      </c:when>
    </c:choose>

    <c:choose>
      <c:when test="${currentIg == selectedIg}">
        <option value="<c:out value="${currentIg}"/>" selected><c:out value="${status.current.inventoryGroupName}"/></option>
      </c:when>
      <c:otherwise>
        <option value="<c:out value="${currentIg}"/>"><c:out value="${status.current.inventoryGroupName}"/></option>
      </c:otherwise>
    </c:choose>
  </c:forEach>
  <c:if test="${invenGroupCount == 0}">
   <option value=""><fmt:message key="label.all"/></option>
  </c:if>
 </select>
 </td>
 <td width="5%" class="optionTitleBoldRight">
   <%--<fmt:message key="label.sortby"/>: --%>
 </td>

 <td width="10%" class="optionTitleBoldRight">
  <%--<html:select property="sort" styleClass="HEADER">
  <html:option value="DATE_DELIVERED,CUSTOMER_PO" key="dailyInventoryreport.sort.usecustomerpo"/>
  <html:option value="ITEM_ID,INVOICE_DATE" key="dailyInventoryreport.sort.itemincoice"/>
  <html:option value="ITEM_ID,DATE_DELIVERED" key="dailyInventoryreport.sort.itemusedate"/>
  <html:option value="FAC_PART_NO,INVOICE_DATE" key="dailyInventoryreport.sort.partinvoice"/>
  <html:option value="FAC_PART_NO,DATE_DELIVERED" key="dailyInventoryreport.sort.partusedate"/>
  </html:select> --%>
 </td>
 <td height="35" class="optionTitleBoldLeft">


<html:submit property="buttonCreateExcel" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return search()">
     <fmt:message key="label.createexcelfile"/>
</html:submit>
</td>
</tr>
</table>
<!-- Search Option Table Ends -->
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
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>

<c:if test="${!empty errorMessage}">
 <c:out value="${errorMessage}"/>
</c:if>
</div>
<!-- Error Messages Ends -->

<c:if test="${dailyInventoryItemViewBeanCollection != null}" >
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->

<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
  <td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
    </div>
    <div class="roundContent">
      <div class="boxhead"> </div>
      <div class="dataContent">
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
          <c:set var="colorClass" value=''/>
          <c:set var="dataCount" value='${0}'/>

          <c:forEach var="dailyInventoryItemViewBean" items="${dailyInventoryItemViewBeanCollection}" varStatus="status">

               <c:if test="${status.index % 10 == 0}">
   <tr align="center">
   <th rowspan="2" width="5%" height="38"><fmt:message key="label.partnumber"/></th>
   <th rowspan="2" width="4%" height="38"><fmt:message key="label.itemid"/></th>
   <th rowspan="2" width="15%" height="38"><fmt:message key="label.description"/></th>
   <th rowspan="2" width="4%" height="38"><fmt:message key="label.qty"/></th>
   <th rowspan="2" width="10%" height="38"><fmt:message key="label.pkg"/></th>
   <th width="20%" colspan="4"><fmt:message key="dailyInventoryreport.label.materialvalue"/></th>
   <th width="20%" colspan="4"><fmt:message key="dailyInventoryreport.label.fullvalue"/></th>
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


        <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
          <td width="5%"><c:out value="${status.current.catPartNo}"/></td>
          <td width="4%"><c:out value="${status.current.itemId}"/></td>
          <td width="15%"><c:out value="${status.current.itemDesc}"/></td>
          <td width="4%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="0"><c:out value="${status.current.quantity}"/></fmt:formatNumber></td>
          <td width="8%"><c:out value="${status.current.packaging}"/></td>
          <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.haasMaterialValue}"/></fmt:formatNumber></td>
          <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.consMaterialValue}"/></fmt:formatNumber></td>
          <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.custMaterialValue}"/></fmt:formatNumber></td>
          <td width="5%"><fmt:formatNumber maxFractionDigits="4" minFractionDigits="2"><c:out value="${status.current.sumMaterialValue}"/></fmt:formatNumber></td>
          <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.haasFullValue}"/></fmt:formatNumber></td>
          <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.consignedFullValue}"/></fmt:formatNumber></td>
          <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.customerFullValue}"/></fmt:formatNumber></td>
          <td width="5%"><fmt:formatNumber maxFractionDigits="4" minFractionDigits="2"><c:out value="${status.current.sumFullValue}"/></fmt:formatNumber></td>
       </tr>
       <c:set var="dataCount" value='${dataCount+1}'/>
       </c:forEach>

<c:if test="${dataCount > 0}">
  <tcmis:dailyInventoryDetailTotalsTag indicator="hubpage"/>
</c:if>

</table>

<!-- If the collection is empty say no data found -->
   <c:if test="${empty dailyInventoryItemViewBeanCollection}" >
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
</div>

</c:when>
<c:otherwise>
 <fmt:message key="error.noaccesstopage"/>
</c:otherwise>
</c:choose>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="totallines" id="totallines" value="<c:out value="${dataCount}"/>">
<input type="hidden" name="dailyInventoryItemViewBean" id="dailyInventoryItemViewBean" value="Pass Through">
</div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</body>
</html:html>
