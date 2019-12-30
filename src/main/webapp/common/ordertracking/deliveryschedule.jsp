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
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
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
<fmt:message key="deliveryschedule.label.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff">

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
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="deliveryschedule.label.title"/>
</td>
<td width="30%" class="headingr"/>&nbsp;</td>
</tr>
</table>
</div>

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="800" border="0" cellpadding="0" cellspacing="0">
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
<td width="7%" class="optionTitleBoldRight">
<fmt:message key="deliveryschedule.label.requestnum"/>:
</td>

<td width="15%" class="alignLeft">
<c:out value='${scheduleDeliveryHeaderViewBean.prNumber}'/>
</td>

<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.status"/>:
</td>

<td width="10%" class="alignLeft">
<c:out value='${scheduleDeliveryHeaderViewBean.statusDesc}'/>
</td>

<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.type"/>:
</td>

<td width="10%" class="alignLeft">
<c:out value='${scheduleDeliveryHeaderViewBean.itemType}'/>
</td>

<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.itemid"/>:
</td>

<td width="10%" class="alignLeft">
<c:out value='${scheduleDeliveryHeaderViewBean.itemId}'/>
</td>

</tr>

<tr>

<td width="7%" class="optionTitleBoldRight">
<fmt:message key="label.partnumber"/>:
</td>

<td width="15%" class="alignLeft">
<c:out value='${scheduleDeliveryHeaderViewBean.facPartNo}'/>
</td>

<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.orderedqty"/>:
</td>

<td width="10%" class="alignLeft">
<c:out value='${scheduleDeliveryHeaderViewBean.quantity}'/>
</td>

<td width="5%" class="optionTitleBoldRight">
<fmt:message key="deliveryschedule.label.qtyscheduled"/>:
</td>

<td width="10%" class="alignLeft">
<c:out value='${scheduleDeliveryHeaderViewBean.quantity}'/>
</td>

</tr>

<tr>

<td width="7%" class="optionTitleBoldRight">
<fmt:message key="label.description"/>:
</td>

<td colspan="7" align="alignLeft">
<c:out value='${scheduleDeliveryHeaderViewBean.itemDesc}'/>
</td>
</tr>

<tr>
<td colspan="8" align="alignLeft">
<html:button property="buttonCancel" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "closeWindow()">
     <fmt:message key="label.close"/>
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
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->

<c:if test="${deliverySummaryBeanCollection != null}" >
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
    <div class="dataContent">

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">

<c:forEach var="deliverySummaryBean" items="${deliverySummaryBeanCollection}" varStatus="status">

<c:set var="dataCount" value='${dataCount+1}'/>

<c:if test="${status.index % 10 == 0}">
<tr>
<th width="20%"><fmt:message key="deliveryschedule.label.scheduled"/></th>
<th width="20%"><fmt:message key="deliveryschedule.label.currentqty"/></th>
<th width="20%"><fmt:message key="deliveryschedule.label.revisedqty"/></th>
<th width="20%"><fmt:message key="label.datedelivered"/></th>
<th width="20%"><fmt:message key="deliveryschedule.label.deliveredqty"/></th>
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
  <fmt:formatDate var="formattedrequestedDate" value="${status.current.requestedDateToDeliver}" pattern="MMM dd, yyyy"/>
  <td width="20%"><c:out value="${formattedrequestedDate}"/></td>
  <td width="20%"><c:out value="${status.current.requestedQty}"/></td>
  <td width="20%"></td>
  <fmt:formatDate var="formatteddateDelivered" value="${status.current.dateDelivered}" pattern="MMM dd, yyyy"/>
  <td width="20%"><c:out value="${formatteddateDelivered}"/></td>
  <td width="20%"><c:out value="${status.current.deliveredQty}"/></td>
</tr>

</c:forEach>
</table>

   <!-- If the collection is empty say no data found -->
   <c:if test="${empty deliverySummaryBeanCollection}" >
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
</c:if>

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</body>
</html:html>