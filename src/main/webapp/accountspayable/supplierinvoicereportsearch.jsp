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
<script type="text/javascript" src="/js/common/searchiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here -->
<script src="/js/accountspayable/invoicereport.js" language="JavaScript"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
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
<fmt:message key="supplierinvoicereport.title"/>
</title>

<script language="JavaScript" type="text/javascript">
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
                and:"<fmt:message key="label.and"/>",
     submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<!--Uncomment for production-->
<tcmis:form action="/supplierinvoicereportresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage"> <!-- Start of interface-->

<div class="contentArea"> <!-- Start of contentArea-->

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
    <!-- Search Option Table Start -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td class="optionTitleBoldRight" width="1%"><fmt:message key="label.invoice"/>:</td>
        <td width="10%"><input name="supplierInvoiceId" type="text" class="inputBox" id="supplierInvoiceId" size="25" value='${param.supplierInvoiceId}'></td>
        <td class="optionTitleBoldRight" width="5%"><fmt:message key="label.status"/>:</td>
        <td width="10%">
          <select name="status" class="selectBox" id="status">
             <option value="All"><fmt:message key="label.all"/></option>
           <c:forEach items="${voucherStatusColl}" var="vstatus">
            <c:set var="currentStatus" value='${vstatus.voucherStatus}'/>
            <c:choose>
             <c:when test="${param.status == currentStatus}">
              <option value='${currentStatus}' selected>${currentStatus}</option>
             </c:when>
             <c:otherwise>
              <option value='${currentStatus}'>${currentStatus}</option>
             </c:otherwise>
            </c:choose>
           </c:forEach>
          </select>
        </td>
        <td width="20%">
          &nbsp;
        </td>
      </tr>
      <tr>
        <td class="optionTitleBoldRight" width="1%"><fmt:message key="label.supplier"/>:</td>
        <td width="10%"><input name="supplierName" type="text" class="inputBox" id="supplierName" size="25" value='${param.supplierName}'></td>
        <td class="optionTitleBoldRight" width="5%" nowrap>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <fmt:message key="label.invoiceage"/>:</td>
        <td width="10%" class="optionTitleBoldLeft">
          <c:choose>
           <c:when test="${param.voucherAge != null}">
            <input name="voucherAge" class="inputBox" type="text" value='${param.voucherAge}' id="voucherAg" size="5">Days
           </c:when>
           <c:otherwise>
            <input name="voucherAge" class="inputBox" type="text" value="100" id="voucherAge" size="5">Days
           </c:otherwise>
          </c:choose>
        </td>
        <td width="20%" class="optionTitleBoldLeft" nowrap>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <c:choose>
           <c:when test='${param.showOnlyWithReceipts == "Yes"}'>
            <input name="showOnlyWithReceipts" type="checkbox" class="radioBtns" value="Yes" id="showOnlyWithReceipts" checked>
           </c:when>
           <c:otherwise>
            <input name="showOnlyWithReceipts" type="checkbox" class="radioBtns" value="Yes" id="showOnlyWithReceipts">
           </c:otherwise>
          </c:choose>
          &nbsp;<fmt:message key="supplierinvoicereport.showonlywithreceipts"/>
        </td>
      </tr>
      <tr>
        <td class="optionTitleBoldRight" width="1%"><fmt:message key="label.name"/>:</td>
        <td width="10%">
          <select name="apUserId" class="selectBox" id="apUserId">
            <option value=""><fmt:message key="label.all"/></option>
           <c:forEach items="${personnelColl}" var="personnel">
             <c:choose>
               <c:when test="${param.apUserId == personnel.personnelId}">
                 <option value='${personnel.personnelId}' selected>${personnel.name}</option>
               </c:when>
               <c:otherwise>
                 <option value='${personnel.personnelId}'>${personnel.name}</option>
               </c:otherwise>
             </c:choose>
           </c:forEach>
          </select>
        </td>
        <td class="optionTitleBoldRight" width="5%" nowrap><fmt:message key="label.sortby"/>:</td>
        <td width="10%">
          <html:select property="sortBy" styleClass="selectBox" styleId="sortBy">
           <html:option value="radianPo,supplierInvoiceId" key="supplierinvoicereport.sortby.poinvoice"/>
           <html:option value="supplierInvoiceId" key="label.invoice"/>
           <html:option value="supplierName,supplierInvoiceId" key="supplierinvoicereport.sortby.supplierinvoice"/>
           <html:option value="invoiceDate" key="label.invoicedate"/>
           <html:option value="approvedDate" key="label.approveddate"/>
          </html:select>
        </td>
        <td class="optionTitleBoldLeft" width="20%">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <c:choose>
           <c:when test='${param.showOnlyToBeQced == "Yes"}'>
            <input type="checkbox" name="showOnlyToBeQced" value="Yes"  onclick="freezeInvoiceStatus()" id="showOnlyToBeQced" checked>
           </c:when>
           <c:otherwise>
            <input type="checkbox" name="showOnlyToBeQced" value="Yes"  onclick="freezeInvoiceStatus()" id="showOnlyToBeQced">
           </c:otherwise>
          </c:choose>
          &nbsp;<fmt:message key="supplierinvoicereport.showonlyinvoicesqced"/>
        </td>
      </tr>
      <tr>
        <td width="1%">&nbsp;</td>
        <td width="10%">&nbsp;</td>
        <td width="5%">&nbsp;</td>
        <td width="10%">&nbsp;</td>
        <td width="20%" class="optionTitleBoldRight" nowrap>
          <div id="approvedDates" style="display: none;">
            <fmt:message key="label.approvedfrom"/>:&nbsp;
            <input type="text" readonly name="approvedDateBegin" id="approvedDateBegin" onclick="return getCalendar(document.genericForm.approvedDateBegin);" class="inputBox pointer" value='${param.approvedDateBegin}' maxlength="10" size="8">
            <%-- <a href="javascript: void(0);" ID="linkapprovedDateBegin" onclick="return getCalendar(document.genericForm.approvedDateBegin);">&diams;</a> --%>
            &nbsp;<fmt:message key="label.to"/>:&nbsp;
            <input type="text" readonly name="approvedDateEnd" id="approvedDateEnd" onclick="return getCalendar(document.genericForm.approvedDateEnd);" class="inputBox pointer" value='${param.approvedDateEnd}' maxlength="10" size="8">
            <%-- <a href="javascript: void(0);" ID="linkapprovedDateEnd" onclick="return getCalendar(document.genericForm.approvedDateEnd);">&diams;</a>  --%>
          </div>
        </td>
      </tr>
      <tr>
      <td class="optionTitleBoldLeft">
        <%--  <input name="submitSearch" type="submit" class="inputBtns" value="Search" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return search()">
        </td>
      <td>--%>
			<input name="submitSearch" id="submitSearch" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="search()"/>
			</td>
      <td>
			<input name="submitCreateexcel" id="submitCreateexcel" type="submit" value="<fmt:message key="label.createexcel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="createExcel()"/>
			</td>    
      </tr>
    </table>
    <!-- Search Option Table end -->
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
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

</div> <!-- close of contentArea -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
    <input type="hidden" name="startSearchTime" id="startSearchTime" value=""/>
    <input type="hidden" name="userAction" id="userAction" value=""/> 

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>