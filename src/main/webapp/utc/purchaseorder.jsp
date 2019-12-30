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
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/clientpages.css"></LINK>
<SCRIPT SRC="/js/supply/customerpurchaseOrders.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/common/formchek.js" LANGUAGE="JavaScript"></SCRIPT>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<!--
<link rel="stylesheet" type="text/css" href="/css/clientpages.css"></link>
 -->
 <script type="text/javascript" src="/js/supply/customerpurchaseOrders.js"></script>

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
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->


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
<fmt:message key="purchaseorders.label.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
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

<tcmis:form action="/purchaseorders.do" onsubmit="return submitOnlyOnce();">
<c:set var="submitSearch" value='${param.submitSearch}'/>
<c:set var="submitUpdate" value='${param.submitUpdate}'/>
<c:set var="submitCreatePo" value='${param.submitCreatePo}'/>
<c:set var="submitCreateReport" value='${param.submitCreateReport}'/>

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
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td>
<img src="/images/purchaseorders.gif" align="right">
</td>
</tr>
</table>

<%@ include file="/common/clientnavigation.jsp" %>
</div>
<c:set var="purchaserequestsLinkPermission" value=''/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="customerPurchasing">
 <c:set var="purchaserequestsLinkPermission" value='Yes'/>
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="readonlyCustomerPurchasing">
 <c:set var="purchaserequestsLinkPermission" value='Yes'/>
</tcmis:inventoryGroupPermission>
<c:choose>

 <c:when test="${purchaserequestsLinkPermission == 'Yes'}">

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
<tr class="">
 <td width="5%" class="optionTitleBoldRight">
  <fmt:message key="label.buyer"/>:
 </td>
 <td width="10%" class="optionTitleLeft">
  <select name="buyerId" id="buyerId" class="selectBox">
  <c:set var="selectedBuyerId" value='${param.buyerId}'/>
  <c:if test="${empty submitSearch && empty submitUpdate && empty submitCreatePo && empty selectedBuyerId}">
   <c:set var="selectedBuyerId" value='${sessionScope.personnelBean.personnelId}'/>
  </c:if>

  <option value=""><fmt:message key="purchaserequests.label.anybuyer"/></option>

  <c:forEach var="personnelNameUserGroupViewBean" items="${vvBuyerCollection}" varStatus="statusPersonnel">
  <c:set var="currentBuyerId" value='${statusPersonnel.current.personnelId}'/>
  <c:choose>
   <c:when test="${currentBuyerId == selectedBuyerId}">
    <option value="${currentBuyerId}" selected>${statusPersonnel.current.name}</option>
   </c:when>
   <c:otherwise>
    <option value="${currentBuyerId}">${statusPersonnel.current.name}</option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
  </select>
 </td>

 <td width="5%" class="optionTitleBoldRight">
   <fmt:message key="label.search"/>:
 </td>

 <td width="10%" class="optionTitleLeft">
  <html:select property="searchWhat" styleId="searchWhat" styleClass="selectBox">
   <html:option value="RAYTHEON_PO" key="label.customerpo"/>
   <%--<html:option value="RADIAN_PO" key="label.haaspo"/>--%>
  </html:select>
   <html:select property="searchType" styleId="searchType" styleClass="selectBox">
      <html:option value="is" key="label.is"/>
      <html:option value="contains" key="label.contain"/>
    </html:select>
 </td>
 <td width="5%" colspan="2" class="optionTitleBoldLeft">
  <fmt:message key="label.For"/><html:text property="searchText" styleId="searchText" size="20" styleClass="inputBox"/>
 </td>

 <td width="5%" class="optionTitleLeft">
  <html:submit property="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
   <fmt:message key="label.search"/>
  </html:submit>
 </td>
</tr>

<tr class="">
<td width="5%" class="optionTitleBoldRight">
 	<fmt:message key="label.inventorygroup"/>:
 </td>
 <td width="10%" class="optionTitleLeft">
 <c:set var="selectedIg" value='${param.inventoryGroup}'/>
 <c:set var="invenGroupCount" value='${0}'/>

 <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
  <%-- <option value=""><fmt:message key="label.all"/></option> --%>
  <c:forEach var="personnelNameUserGroupViewBean" items="${vvInventoryGroupCollection}" varStatus="status">
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
        <option value="${currentIg}" selected>${status.current.inventoryGroup}</option>
      </c:when>
      <c:otherwise>
        <option value="${currentIg}">${status.current.inventoryGroup}</option>
      </c:otherwise>
    </c:choose>
  </c:forEach>
  <c:if test="${invenGroupCount == 0}">
   <option value=""><fmt:message key="label.all"/></option>
  </c:if>
 </select>
 </td>

 <td width="5%" colspan="4" class="optionTitleBoldRight">
 &nbsp;

 </td>
<input type="hidden" name="sort" value="raytheonPo">

<c:set var="purchaserequestsPermission" value=''/>
 <tcmis:inventoryGroupPermission indicator="true" userGroupId="customerPurchasing">
  <c:set var="purchaserequestsPermission" value='Yes'/>
 </tcmis:inventoryGroupPermission>

 <td width="5%" class="optionTitleLeft">
   <c:if test="${purchaserequestsPermission == 'Yes'}">
    <html:submit property="submitUpdate" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
      <fmt:message key="label.update"/>
    </html:submit>
   </c:if>
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

<%--<INPUT TYPE="hidden" NAME="supplyPath" value="Customer">--%>
<c:if test="${!empty errorMessage}">
 ${errorMessage}
</c:if>

<c:if test="${empty errorMessage && !empty submitUpdate}">
 	<fmt:message key="purchaserequests.label.successmessag"/>
</c:if>
<!-- error template -->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->

<c:if test="${poHeaderViewBeanCollection != null}" >
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

 <c:forEach var="poHeaderViewBean" items="${poHeaderViewBeanCollection}" varStatus="status">
  <c:if test="${status.index % 10 == 0}">
   <tr class="">
   <th width="2%"><fmt:message key="label.po"/></th>
   <th width="2%"><fmt:message key="label.inventorygroup"/></th>
   <th width="2%"><fmt:message key="label.buyer"/></th>
   <th width="5%"><fmt:message key="label.itemid"/></th>
   <th width="15%"><fmt:message key="label.itemdesc"/></th>
   <th width="5%"><fmt:message key="label.totalqty"/></th>
   <th width="5%"><fmt:message key="label.qtyreceived"/></th>
   <th width="5%"><fmt:message key="label.pkg"/></th>
   <th width="2%"><fmt:message key="label.dateconfirmed"/></th>
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

  <c:set var="createPoPermission" value=''/>
  <tcmis:inventoryGroupPermission indicator="true" userGroupId="customerPurchasing" inventoryGroup="${status.current.inventoryGroup}">
   <c:set var="createPoPermission" value='Yes'/>
  </tcmis:inventoryGroupPermission>

  <c:set var="critical" value='${status.current.critical}'/>
  <c:if test="${critical == 'Y' || critical == 'y'}">
   <c:set var="colorClass" value='red'/>
  </c:if>

  <c:if test="${critical == 'S' || critical == 's'}">
   <c:set var="colorClass" value='pink'/>
  </c:if>

  <c:set var="poLineCollection"  value='${status.current.poLineCollection}'/>
  <bean:size id="poLineSize" name="poLineCollection"/>

  <c:if test="${poLineSize > 0 || !empty poLineCollection}">
  <tr class="${colorClass}" id="rowId${status.index}">

  <td width="5%" rowspan="${poLineSize}">${status.current.customerPo}</td>
  <td width="5%" rowspan="${poLineSize}">${status.current.inventoryGroup}</td>
  <td width="5%" rowspan="${poLineSize}">${status.current.buyerName}</td>

  <c:forEach var="poLineDetailViewBean" items="${poLineCollection}" varStatus="lineStatus">
   <c:if test="${lineStatus.index > 0 && poLineSize > 1 }">
     <tr align="center" CLASS="${colorClass}" id="childRowId${status.index}${lineStatus.index}">
   </c:if>

   <input type="hidden" name="poLineDetailViewBean[${dataCount}].radianPo" id="radianPo${dataCount}" value="${lineStatus.current.radianPo}">
   <input type="hidden" name="poLineDetailViewBean[${dataCount}].poLine" id="poLine${dataCount}" value="${lineStatus.current.poLine}">
   <input type="hidden" name="poLineDetailViewBean[${dataCount}].amendment" id="amendment${dataCount}" value="${lineStatus.current.amendment}">
   <input type="hidden" name="poLineDetailViewBean[${dataCount}].itemId" id="itemId${dataCount}" value="${lineStatus.current.itemId}">
   <input type="hidden" name="poLineDetailViewBean[${dataCount}].poLineStatus" id="poLineStatus${dataCount}" value="${lineStatus.current.poLineStatus}">
   <input type="hidden" name="poLineDetailViewBean[${dataCount}].quantityReceived" id="quantityReceived${dataCount}" value="${lineStatus.current.quantityReceived}">
   <input type="hidden" name="poLineDetailViewBean[${dataCount}].lineChangeStatus" id="lineChangeStatus${dataCount}" value="${lineStatus.current.lineChangeStatus}">

   <td width="5%">${lineStatus.current.itemId}</td>
   <td width="5%">${lineStatus.current.itemDesc}</td>
   <td width="5%">
   <c:choose>
   <c:when test="${createPoPermission == 'Yes'}">
    <input type="text" name="poLineDetailViewBean[${dataCount}].quantity" ID="quantity${dataCount}" value="${lineStatus.current.quantity}" size="6" maxlength="30" Class="DETAILS">
   </c:when>
   <c:otherwise>
    ${lineStatus.current.quantity}
   </c:otherwise>
   </c:choose>
   </td>
   <td width="5%">${lineStatus.current.quantityReceived}</td>
   <td width="5%">${lineStatus.current.packaging}</td>
   <fmt:formatDate var="formattedDateConfirmed" value="${lineStatus.current.dateConfirmed}" pattern="MM/dd/yyyy"/>
   <td width="5%">${formattedDateConfirmed}</td>
   <c:set var="dataCount" value='${dataCount+1}'/>
  </c:forEach>
  </tr>
  </c:if>
</c:forEach>
</table>

<input type="hidden" name="totallines" id="totallines" value="${dataCount}">
   <!-- If the collection is empty say no data found -->
<c:if test="${empty poHeaderViewBeanCollection && !(empty submitSearch && empty submitUpdate)}" >
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
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

</c:when>
<c:otherwise>
 <fmt:message key="error.noaccesstopage"/>
</c:otherwise>
</c:choose>

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>