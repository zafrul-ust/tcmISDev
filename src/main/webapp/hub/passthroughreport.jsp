<!DOCtype html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
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
<script type="text/javascript" src="/js/hub/passthroughreport.js"></script>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",dateformat:"<fmt:message key="label.dateformat"/>",date:"<fmt:message key="label.date"/>",
usedsince:"<fmt:message key="passthroughreport.label.usedsince"/>",validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
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
<fmt:message key="passthroughreport.label.title"/>
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
   <body onLoad="doexcelpopup()">
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

<tcmis:form action="/passthroughreport.do" onsubmit="return submitOnlyOnce();">

 <div class="interface" id="mainPage">

   <!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
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

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="passthroughreport.label.title"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
</tr>
</table>
</div>

<div class="contentArea">


<!-- Search Option Begins -->
<table id="searchMaskTable" width="750" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">

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
   <fmt:message key="passthroughreport.label.usedsince"/>:<br>(<fmt:message key="label.dateformat"/>)
 </td>
 <td width="10%" class="optionTitleBoldLeft">
  <c:set var="formattedIssueConfirmed" value='${param.dateDelivered}'/>
  <input type="text" name="dateDelivered" id="dateDelivered" value="<c:out value="${formattedIssueConfirmed}"/>" size="8" maxlength="10" class="inputBox"><a href="javascript: void(0);" id="linkdateDelivered" onClick="return getCalendar(document.genericForm.dateDelivered);" style="color:#0000ff;cursor:pointer;text-decoration:underline">&diams;</a>
 </td>

 <td width="5%" class="optionTitleBoldRight">
   <html:submit property="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return search()">
     <fmt:message key="label.search"/>
   </html:submit>
 </td>
</tr>

<tr">
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
   <fmt:message key="label.sortby"/>:
 </td>

 <td width="10%" class="optionTitleBoldLeft">
  <html:select property="sort" styleClass="selectBox">
  <html:option value="DATE_DELIVERED,CUSTOMER_PO" key="passthroughreport.sort.usecustomerpo"/>
  <html:option value="ITEM_ID,INVOICE_DATE" key="passthroughreport.sort.itemincoice"/>
  <html:option value="ITEM_ID,DATE_DELIVERED" key="passthroughreport.sort.itemusedate"/>
  <html:option value="FAC_PART_NO,INVOICE_DATE" key="passthroughreport.sort.partinvoice"/>
  <html:option value="FAC_PART_NO,DATE_DELIVERED" key="passthroughreport.sort.partusedate"/>
  </html:select>
 </td>
 <td width="10%" class="optionTitleBoldRight">
<html:submit property="buttonCreateExcel" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return search()">
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

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->

<input type="hidden" name="billingMethod" id="billingMethod" value="Pass Through">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->

<c:if test="${igBilledIssuesViewBeanCollection != null}" >
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

 <c:forEach var="igBilledIssuesViewBean" items="${igBilledIssuesViewBeanCollection}" varStatus="status">
  <c:if test="${status.index % 10 == 0}">
   <tr>
   <th width="5%" height="38"><fmt:message key="label.facility"/></th>
   <th width="5%" height="38"><fmt:message key="label.workarea"/></th>
   <th width="5%" height="38"><fmt:message key="label.partnumber"/></th>
   <th width="15%" height="38"><fmt:message key="label.description"/></th>
   <th width="4%" height="38"><fmt:message key="label.itemid"/></th>
   <th width="4%" height="38"><fmt:message key="label.qty"/></th>
   <th width="8%" height="38"><fmt:message key="label.pkg"/></th>
   <th width="4%" height="38"><fmt:message key="label.unitprice"/></th>
   <th width="4%" height="38"><fmt:message key="label.extprice"/></th>
   <th width="4%" height="38"><fmt:message key="label.mrnumber"/></th>
   <th width="4%" height="38"><fmt:message key="label.customerpo"/></th>
   <th width="4%" height="38"><fmt:message key="label.releaseno"/></th>
   <th width="4%" height="38"><fmt:message key="passthroughreport.label.usedate"/></th>
   <th width="4%" height="38"><fmt:message key="label.invoicedate"/></th>
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

<fmt:formatDate var="formattedDateDelivered" value="${status.current.dateDelivered}" pattern="MM/dd/yyyy"/>
<fmt:formatDate var="formattedInvoiceDate" value="${status.current.invoiceDate}" pattern="MM/dd/yyyy"/>

 <tr align="center" class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
  <td width="5%"><c:out value="${status.current.inventoryGroup}"/></td>
  <td width="5%"><c:out value="${status.current.facilityId}"/></td>
  <td width="5%"><c:out value="${status.current.facPartNo}"/></td>
  <td width="15%"><c:out value="${status.current.partDescription}"/></td>
  <td width="4%"><c:out value="${status.current.itemId}"/></td>
  <td width="4%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.quantity}"/></fmt:formatNumber></td>
  <td width="8%"><c:out value="${status.current.packaging}"/></td>
  <td width="4%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.unitPrice}"/></fmt:formatNumber></td>
  <td width="4%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.extPrice}"/></fmt:formatNumber></td>
  <td width="4%"><c:out value="${status.current.prNumber}"/>-<c:out value="${status.current.lineItem}"/></td>
  <td width="4%"><c:out value="${status.current.customerPo}"/></td>
  <td width="4%"><c:out value="${status.current.releaseNo}"/></td>
  <td width="4%"><c:out value="${formattedDateDelivered}"/></td>
  <td width="4%"><c:out value="${formattedInvoiceDate}"/></td>
 </tr>
 <c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>
</table>

 <!-- If the collection is empty say no data found -->
   <c:if test="${empty igBilledIssuesViewBeanCollection}" >
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



<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
 <input type="hidden" name="totallines" id="totallines" value="<c:out value="${dataCount}"/>">
</div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->
</div>
<!-- close of interface -->
</tcmis:form>
</body>
</html:html>
