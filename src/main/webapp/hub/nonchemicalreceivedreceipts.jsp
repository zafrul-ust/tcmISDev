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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %> 
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
<script type="text/javascript" src="/js/hub/receiving.js"></script>

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
<c:choose>
  <c:when test="${empty receivedReceipts}" >
   <fmt:message key="unconfirmedreceipts.title"/>
  </c:when>
  <c:otherwise>
  <fmt:message key="receivedreceipts.title"/>
  </c:otherwise>
</c:choose>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

windowCloseOnEsc = true;
// -->
</script>
</head>

<body bgcolor="#ffffff" onLoad="" onunload="closeAllchildren();">

<tcmis:form action="/shownonchemicalreceivedreceipts.do" onsubmit="return SubmitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>

 <div class="interface" id="mainPage">

<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<c:choose>
  <c:when test="${empty receivedReceipts}" >
   <fmt:message key="unconfirmedreceipts.title"/>
  </c:when>
  <c:otherwise>
  <fmt:message key="receivedreceipts.title"/>
  </c:otherwise>
</c:choose>
</td>
</tr>
</table>
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

   <table width="100%" border="0" cellpadding="3" cellspacing="0">
   <tr align="middle">
   <td width="100%" class="alignLeft" >
     <html:button property="printthispage" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="javascript: if (window.print) window.print();">
      <fmt:message key="label.printthispage"/>
     </html:button>
     
     <html:button property="containerlabels" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "cancel()">
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

<c:if test="${receivedReceiptsCollection != null}" >
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

 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
 <!-- <TABLE BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING="1" CELLPADDING="2" WIDTH="100%" CLASS="moveup">  -->
 <c:set var="colorClass" value=''/>
 <c:set var="dataCount" value='${0}'/>

 <c:forEach var="ReceiptDescriptionViewBean" items="${receivedReceiptsCollection}" varStatus="status">
  <c:if test="${status.index % 10 == 0}">
   <tr align="center">
   <th width="5%"><fmt:message key="label.po"/><br/><fmt:message key="label.poline"/><br>(<fmt:message key="label.customerpo"/>)</th>
   <th width="8%"><fmt:message key="label.supplier"/></th>
   <th width="5%"><fmt:message key="label.item"/><br/>(<fmt:message key="label.inventorygroup"/>)</th>
   <th width="15%"><fmt:message key="label.description"/></th>
   <th width="4%"><fmt:message key="label.lot"/><br/><fmt:message key="label.bin"/><br/><fmt:message key="label.receiptid"/></th>
   <th width="4%"><fmt:message key="label.dates"/></th>
   <th width="5%"><fmt:message key="receiving.label.qtyreceived"/></th>
   <th width="8%"><fmt:message key="label.packaging"/></th>
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
  <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.radianPo}"/>
    <br><c:out value="${status.current.poLine}"/>
    <c:if test="${!empty status.current.poNumber}">
     <br>(<c:out value="${status.current.poNumber}"/>)
    </c:if>
  </td>
  <td width="8%"><c:out value="${status.current.supplierName}"/></td>
  <td width="5%"><c:out value="${status.current.itemId}"/><br/>(<c:out value="${status.current.inventoryGroupName}"/>)</td>
  <td width="15%"><c:out value="${status.current.lineDesc}"/></td>
  <td width="8%"><c:out value="${status.current.mfgLot}"/><br/><c:out value="${status.current.bin}"/><br/><c:out value="${status.current.receiptId}"/></td>

<%--<fmt:formatDate var="formattedShipDate" value="${status.current.vendorShipDate}" pattern="MM/dd/yyyy"/>--%>
  <fmt:formatDate var="formattedDateOfReceipt" value="${status.current.dateOfReceipt}" pattern="${dateFormatPattern}"/>
  <fmt:formatDate var="formattedDom" value="${status.current.dateOfManufacture}" pattern="${dateFormatPattern}"/>
  <fmt:formatDate var="formattedDos" value="${status.current.dateOfShipment}" pattern="${dateFormatPattern}"/>
<%--  <fmt:formatDate var="formattedExpirationDate" value="${status.current.expireDate}" pattern="MM/dd/yyyy"/>
  <c:if test="${formattedExpirationDate == '01/01/3000'}" >
    <c:set var="formattedExpirationDate" value='Indefinite'/> 
  </c:if>  --%>

  <td width="5%" nowrap class="alignLeft">
    <fmt:message key="receivedreceipts.label.dom"/>:<c:out value="${formattedDom}"/><br/>
    <fmt:message key="receivedreceipts.label.dor"/>:<c:out value="${formattedDateOfReceipt}"/><br/>
    <fmt:message key="receivedreceipts.label.manufacturerdos"/>:<c:out value="${formattedDos}"/><br/>
    <fmt:formatDate var="fmtExpireDate" value="${status.current.expireDate}" pattern="MM/dd/yyyy"/>
        <c:choose>
          <c:when test='${fmtExpireDate=="01/01/3000"}'>
             <fmt:message key="receivedreceipts.label.expdate"/>:<fmt:message key="label.indefinite"/><br/>
          </c:when>
          <c:otherwise>
            <fmt:formatDate var="localeExpireDate" value="${status.current.expireDate}" pattern="${dateFormatPattern}"/>
             <fmt:message key="receivedreceipts.label.expdate"/>:<c:out value="${localeExpireDate}"/><br/>
          </c:otherwise>
        </c:choose>
 <%--   <fmt:message key="receivedreceipts.label.expdate"/>:<c:out value="${formattedExpirationDate}"/><br/> --%>
  </td>
  <td width="5%"><c:out value="${status.current.quantityReceived}"/></td>
  <td width="8%"><c:out value="${status.current.packaging}"/></td>
 </tr>
</c:forEach>
</table>

   <c:if test="${empty receivedReceiptsCollection}" >
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

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
  <input type="hidden" name="totallines" value="<c:out value="${dataCount}"/>">
  <input TYPE="hidden" name="labelReceipts" value="<c:out value="${labelReceipts}"/>">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>