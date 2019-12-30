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
<script type="text/javascript" src="/js/supplier/vendorpayment.js"></script>

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
<fmt:message key="vendorpayment.title"/>
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

<body bgcolor="#ffffff">

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
 <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>

<div class="interface" id="mainPage">

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

    <table width="50%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.vendorname"/>:</td>
        <td>
<c:out value="${supplierPaymentHeaderViewBeanCollection[0].payeeName}"/>
        </td>
      </tr>
      <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.paymentnumber"/>:</td>
        <td>
 <c:out value="${supplierPaymentHeaderViewBeanCollection[0].paymentNum}"/>
        </td>
      </tr>
      <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.paymentdate"/>:</td>
        <td>
<fmt:formatDate pattern="MM/dd/yyyy" value="${supplierPaymentHeaderViewBeanCollection[0].checkDate}"/>
        </td>
      </tr>
      <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.paymentamount"/>:</td>
        <td>
<c:out value="${supplierPaymentHeaderViewBeanCollection[0].currencyCode}"/>&nbsp;
<fmt:formatNumber value="${supplierPaymentHeaderViewBeanCollection[0].amtNet}" type="currency" currencySymbol=""/>
        </td>
      </tr>
      <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.comment"/>:</td>
        <td>
<c:out value="${supplierPaymentHeaderViewBeanCollection[0].docDesc}"/>
        </td>
      </tr>
<tcmis:form action="/vendorpaymentresult.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">
      <tr>
        <td>
<input name="createExcelFile" id="createExcelFile" type="submit" value="<fmt:message key="label.createexcelfile"/>" class="inputBtns"  onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcelDetail(); return false;">
<input name="paymentNum" id="paymentNum" type="hidden" value="<c:out value="${supplierPaymentHeaderViewBeanCollection[0].paymentNum}"/>">
<input name="action" id="action" type="hidden" value="createExcelDetail">
</td>
        <td>
&nbsp;
        </td>
      </tr>
</tcmis:form>
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

<c:if test="${supplierPaymentHeaderViewBeanCollection != null}" >
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
    <div class="boxhead"> 
<!--
<a href="#" onclick="doSomeThing(); return false;"><fmt:message key="label.createexcelfile"/></a>
-->
<%--
<fmt:message key="label.vendorname"/>: <c:out value="${supplierPaymentHeaderViewBeanCollection[0].payeeName}"/><br>
<fmt:message key="label.paymentnumber"/>: <c:out value="${supplierPaymentHeaderViewBeanCollection[0].paymentNum}"/><br>
<fmt:message key="label.paymentdate"/>: <fmt:formatDate pattern="MM/dd/yyyy" value="${supplierPaymentHeaderViewBeanCollection[0].checkDate}"/><br>
<fmt:message key="label.paymentamount"/>: <c:out value="${supplierPaymentHeaderViewBeanCollection[0].amtNet}"/><br>
<fmt:message key="label.comment"/>: <c:out value="${supplierPaymentHeaderViewBeanCollection[0].docDesc}"/><br>
--%>
    </div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>
<c:forEach var="supplierPaymentHeaderViewBean" items="${supplierPaymentHeaderViewBeanCollection}" varStatus="status0">
    <c:forEach var="supplierPaymentDetailViewBean" items="${supplierPaymentHeaderViewBean.supplierPaymentDetailViewBeanCollection}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
    <th width="5%"><fmt:message key="label.invoice"/></th>
    <th width="5%"><fmt:message key="label.invoicedate"/></th>
    <th width="5%"><fmt:message key="label.invoiceamount"/></th>
    <th width="5%"><fmt:message key="label.discountamount"/></th>
    <th width="5%"><fmt:message key="label.amountpaid"/></th>
    <th width="5%"><fmt:message key="label.comment"/></th>
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
     <td width="5%"><c:out value="${status.current.invoiceNum}"/></td>
     <td width="5%">
<fmt:formatDate pattern="MM/dd/yyyy" value="${status.current.invoiceDate}"/>
     </td>
     <td class="optionTitleRight" width="5%"><c:out value="${supplierPaymentHeaderViewBeanCollection[0].currencyCode}"/>&nbsp;<fmt:formatNumber value="${status.current.invoiceAmt}" type="currency" currencySymbol=""/></td>
     <td class="optionTitleRight" width="5%"><c:out value="${supplierPaymentHeaderViewBeanCollection[0].currencyCode}"/>&nbsp;<fmt:formatNumber value="${status.current.amtDiscTaken}" type="currency" currencySymbol=""/></td>
     <td class="optionTitleRight" width="5%"><c:out value="${supplierPaymentHeaderViewBeanCollection[0].currencyCode}"/>&nbsp;<fmt:formatNumber value="${status.current.amtApplied}" type="currency" currencySymbol=""/></td>
     <td width="5%"><c:out value="${status.current.lineDesc}"/></td>
   </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty supplierPaymentHeaderViewBeanCollection}" >

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

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</body>
</html:html>