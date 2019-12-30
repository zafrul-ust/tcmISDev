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

<tcmis:fontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<!-- This handles the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<!-- This handles which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<script src="/js/accountspayable/invoicereport.js" language="JavaScript"></script>

<%-- Calendar --%>
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>

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

<body bgcolor="#ffffff" onload="freezeInvoiceStatus()">


<tcmis:form action="/supplierinvoicereport.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" style="display: none;text-align:center;">
  <p><br><br><br><b><fmt:message key="label.pleasewait"/></b></p>
 </div>
 <div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table border=0 width="100%">
<tr valign="top">
<td width="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</td>
<td align="right">
<img src="/images/tcmistcmis32.gif" border=0 align="right">
</td>
</tr>
</table>

<table width="100%">
<tr><td width="70%" align="left" height="22" class="headingl">
<b><fmt:message key="supplierinvoicereport.title"/></b>
</td>
<td width="30%" align="right" height="22" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
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
    <div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td class="optionTitleBoldRight" width="5%"><fmt:message key="label.invoice"/>:</td>
        <td width="10%"><input name="supplierInvoiceId" type="text" class="inputBox" id="supplierInvoiceId" size="25" value='<c:out value="${param.supplierInvoiceId}"/>'></td>
        <td class="optionTitleBoldRight" width="5%"><fmt:message key="label.status"/>:</td>
        <td width="10%">
          <select name="status" class="selectBox" id="status">
             <option value="All">All</option>
           <c:forEach items="${voucherStatusColl}" var="vstatus">
            <c:set var="currentStatus" value='${vstatus.voucherStatus}'/>
            <c:choose>
             <c:when test="${param.status == currentStatus}">
              <option value='<c:out value="${currentStatus}"/>' selected><c:out value="${currentStatus}"/></option>
             </c:when>
             <c:otherwise>
              <option value='<c:out value="${currentStatus}"/>'><c:out value="${currentStatus}"/></option>
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
        <td class="optionTitleBoldRight" width="5%"><fmt:message key="label.supplier"/>:</td>
        <td width="10%"><input name="supplierName" type="text" class="inputBox" id="supplierName" size="25" value='<c:out value="${param.supplierName}"/>'></td>
        <td class="optionTitleBoldRight" width="5%"><fmt:message key="label.invoiceage"/>:</td>
        <td width="10%">
          <c:choose>
           <c:when test="${param.voucherAge != null}">
            <input name="voucherAge" class="inputBox" type="text" value='<c:out value="${param.voucherAge}"/>' id="voucherAge" size="5">Days</td>
           </c:when>
           <c:otherwise>
            <input name="voucherAge" class="inputBox" type="text" value="100" id="voucherAge" size="5">Days</td>
           </c:otherwise>
          </c:choose>
        </td>
        <td width="20%">
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
        <td class="optionTitleBoldRight" width="5%"><fmt:message key="label.name"/>:</td>
        <td width="10%">
          <select name="apUserId" class="selectBox" id="apUserId">
            <option value="">All</option>
           <c:forEach items="${personnelColl}" var="personnel">
             <c:choose>
               <c:when test="${param.apUserId == personnel.personnelId}">
                 <option value='<c:out value="${personnel.personnelId}"/>' selected><c:out value="${personnel.name}"/></option>
               </c:when>
               <c:otherwise>
                 <option value='<c:out value="${personnel.personnelId}"/>'><c:out value="${personnel.name}"/></option>
               </c:otherwise>
             </c:choose>
           </c:forEach>
          </select>
        </td>
        <td class="optionTitleBoldRight" width="5%"><fmt:message key="label.sortby"/>:</td>
        <td width="10%">
          <html:select property="sortBy" styleClass="selectBox" styleId="sortBy">
           <html:option value="radianPo,supplierInvoiceId" key="supplierinvoicereport.sortby.poinvoice"/>
           <html:option value="supplierInvoiceId" key="label.invoice"/>
           <html:option value="supplierName,supplierInvoiceId" key="supplierinvoicereport.sortby.supplierinvoice"/>
           <html:option value="invoiceDate" key="label.invoicedate"/>
           <html:option value="approvedDate" key="label.approveddate"/>
          </html:select>
        </td>
        <td width="20%">
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
        <td width="5%">&nbsp;</td>
        <td width="10%">&nbsp;</td>
        <td width="5%">&nbsp;</td>
        <td width="10%">&nbsp;</td>
        <td width="20%">
          <div id="approvedDates" style="display: none;">
            <fmt:message key="label.approvedfrom"/>:&nbsp;
            <input type="text" name="approvedDateBegin" id="approvedDateBegin" class="inputBox" value='<c:out value="${param.approvedDateBegin}"/>' maxlength="10" size="8"><a href="javascript: void(0);" ID="linkapprovedDateBegin" onclick="return getCalendar(document.genericForm.approvedDateBegin);">&diams;</a>
            &nbsp;<fmt:message key="label.to"/>:&nbsp;
            <input type="text" name="approvedDateEnd" id="approvedDateEnd" class="inputBox" value='<c:out value="${param.approvedDateEnd}"/>' maxlength="10" size="8"><a href="javascript: void(0);" ID="linkapprovedDateEnd" onclick="return getCalendar(document.genericForm.approvedDateEnd);">&diams;</a>
          </div>
        </td>
      </tr>
      <tr>
      <td colspan="5" class="optionTitleBoldLeft">
          <input name="submitSearch" type="submit" class="inputBtns" value="Search" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="search()">
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
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->

<!-- Search results start -->
<c:if test="${voucherReportColl != null}">
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
      <c:set var="rowcount" value="${0}"/>
      <c:forEach items="${voucherReportColl}" var="voucher">
        <c:set var="rowcount" value='${rowcount+1}'/>

        <c:if test="${rowcount % 10 == 1}">
        <tr>
          <th width="3%"><fmt:message key="label.po"/></th>
          <th width="5%"><fmt:message key="label.buyer"/></th>
          <th width="5%"><fmt:message key="label.email"/></th>
          <th width="8%"><fmt:message key="label.phone"/></th>
          <th width="10%"><fmt:message key="label.supplier"/></th>
          <th width="5%"><fmt:message key="label.invoice"/></th>
          <th width="5%"><fmt:message key="label.invoicedate"/></th>
          <th width="5%"><fmt:message key="label.ourterms"/></th>
          <th width="5%"><fmt:message key="label.supplierterms"/></th>
          <th width="5%"><fmt:message key="label.amount"/></th>
          <th width="5%"><fmt:message key="label.status"/></th>
          <th width="5%"><fmt:message key="label.approvedbydate"/></th>
          <th width="5%"><fmt:message key="label.qcedbydate"/></th>
          <th width="5%"><fmt:message key="label.availablereceipts"/></th>
          <th width="5%"><fmt:message key="label.apuser"/></th>
          <th width="15%"><fmt:message key="label.comment"/></th>
        </tr>
        </c:if>

       <c:if test="${rowcount % 2 != 0}">
        <tr>
       </c:if>
       <c:if test="${rowcount % 2 == 0}">
        <tr class="alt">
       </c:if>
          <td>
            <a href='/tcmIS/Invoice/AccountsPayable?po=<c:out value="${voucher.radianPo}"/>&Action=searchlike&subUserAction=po' target="_blank<c:out value="${voucher.radianPo}"/>"><c:out value="${voucher.radianPo}"/></a>
          </td>
          <td><c:out value="${voucher.buyerName}"/></td>
          <td><c:out value="${voucher.buyerEmail}"/></td>
          <td><c:out value="${voucher.buyerPhone}"/></td>
          <td><c:out value="${voucher.supplierName}"/></td>
          <td>
             <a href='/tcmIS/Invoice/AccountsPayable?po=<c:out value="${voucher.radianPo}"/>&selectedInvoiceId=<c:out value="${voucher.voucherId}"/>&Action=searchlike&subUserAction=po' target="_blank<c:out value="${voucher.radianPo}"/>"><c:out value="${voucher.supplierInvoiceId}"/></a>
          </td>
          <td>
             <fmt:formatDate var="fmtInvDate" value="${voucher.invoiceDate}" pattern="MM/dd/yyyy"/>
             <c:out value="${fmtInvDate}"/>
          </td>
          <td><c:out value="${voucher.poTerms}"/></td>
          <td><c:out value="${voucher.supplierTerms}"/></td>
          <td><c:out value="${voucher.netInvoiceAmount}"/></td>
          <td><c:out value="${voucher.status}"/></td>
          <td>
            <c:choose>
              <c:when test="${voucher.apApproverName != null}">
                <fmt:formatDate var="fmtApproveDate" value="${voucher.approvedDate}" pattern="MM/dd/yyyy"/>
                <c:out value="${voucher.apApproverName}"/> (<c:out value="${fmtApproveDate}"/>)
              </c:when>
              <c:otherwise>
                &nbsp;
              </c:otherwise>
            </c:choose>
          </td>
          <td>
             <c:choose>
              <c:when test="${voucher.apQcUserName != null}">
                <fmt:formatDate var="fmtQcDate" value="${voucher.qcDate}" pattern="MM/dd/yyyy"/>
                <c:out value="${voucher.apQcUserName}"/> (<c:out value="${fmtQcDate}"/>)
              </c:when>
              <c:otherwise>
                &nbsp;
              </c:otherwise>
            </c:choose>
          </td>
          <td><c:out value="${voucher.availableReceipts}"/></td>
          <td><c:out value="${voucher.apUserName}"/></td>
          <td><c:out value="${voucher.apNote}"/></td>
        </tr>
      </c:forEach>
      </table>
      <!-- If the collection is empty say no data found -->
      <c:if test="${empty voucherReportColl}" >
        <c:if test="${voucherReportColl != null}" >
          <table border="0" cellspacing=0 cellpadding=0 width="100%">
          <tr>
            <td width="100%">
              <fmt:message key="main.nodatafound"/>
            </td>
          </tr>
          </table>
        </c:if>
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
</c:if>
<!-- Search results end -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>

