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

<!-- This handles the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<!-- This handles which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<script SRC="/js/common/formchek.js" language="JavaScript"></script>
<script SRC="/js/common/commonutil.js" language="JavaScript"></script>

<%-- For Calendar support --%>
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>

<%-- Add any other javascript you need for the page here --%>
<script SRC="/js/supply/searchPOs.js" language="JavaScript"></script>
<script SRC="/js/hub/transactions.js" language="JavaScript"></script>

<title>
<fmt:message key="transactions.title"/>
</title>

<script language="JavaScript" type="text/javascript">
   //add all the javascript messages here, this for internationalization of client side javascript messages
   var messagesData = new Array();
   messagesData={alert:"<fmt:message key="label.alert"/>",
                   and:"<fmt:message key="label.and"/>",
        submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
           validValues:"<fmt:message key="transactions.validvalues"/>",
          onDateFormat:"<fmt:message key="transactions.ondateformat"/>",
             receiptId:"<fmt:message key="label.receiptid"/>",
              mrNumber:"<fmt:message key="label.mrnumber"/>",
                    po:"<fmt:message key="label.po"/>",
           withinXDays:"<fmt:message key="transactions.withinxedays"/>",
                itemId:"<fmt:message key="label.itemid"/>"};

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

   altinvid["<c:out value="${currentHub}"/>"] = new Array("All"
     <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
    ,"<c:out value="${status1.current.inventoryGroup}"/>"
      </c:forEach>
   );

   altinvName["<c:out value="${currentHub}"/>"] = new Array("All"
     <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
    ,"<c:out value="${status1.current.inventoryGroupName}"/>"
      </c:forEach>
   );
   </c:forEach>
</script>

</head>

<body bgcolor="#ffffff">

<c:set var="inventoryGroupPermission" value=""/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="Inventory" inventoryGroup="${status.current.inventoryGroup}">
  <c:set var="inventoryGroupPermission" value="Yes"/>
</tcmis:inventoryGroupPermission>

<tcmis:form action="/transactions.do" onsubmit="if (checkTransactionsInput()) return submitOnlyOnce(); else return false;">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;text-align:center;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr valign="top">
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td align="right">
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="transactions.title"/>
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
    <tr>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
      <td width="10%">
         <c:set var="selectedHub" value='${param.branchPlant}'/>
         <c:set var="selectedHubName" value=''/>
         <c:set var="inventoryGroupDefinitions" value='${null}'/>
         <select name="branchPlant" onchange="hubChanged()" class="selectBox" id="branchPlant">
           <c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
            <c:set var="currentHub" value='${status.current.branchPlant}'/>
            <c:if test="${selectedHub == null}">
                <c:set var="selectedHub" value="${currentHub}"/>
            </c:if>
            <c:if test="${currentHub == selectedHub}" >
              <c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>
            </c:if>
            <c:choose>
              <c:when test="${currentHub == selectedHub}">
                <option value='<c:out value="${currentHub}"/>' selected><c:out value="${status.current.hubName}"/></option>
                <c:set var="selectedHubName" value="${status.current.hubName}"/>
              </c:when>
              <c:otherwise>
                <option value='<c:out value="${currentHub}"/>'><c:out value="${status.current.hubName}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
         </select>
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.receiptid"/>:</td>
      <td width="10%" class="optionTitleBoldLeftt">
         <input class="inputBox" type="text" name="receiptId" id="receiptId" value='<c:out value="${param.receiptId}"/>' size="15">
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.mr"/>:</td>
      <td width="10%" class="optionTitleBoldLeftt">
         <input class="inputBox" type="text" name="mrNumber" id="mrNumber" value='<c:out value="${param.mrNumber}"/>' size="15">
      </td>
      <td width="5%">&nbsp;</td>
      <td width="10%" class="optionTitleBoldLeft">
        &nbsp;
      </td>
    </tr>
    <tr>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.invgrp"/>:&nbsp;</td>
      <td width="10%">
       <c:set var="selectedIg" value='${param.inventoryGroup}'/>
       <c:set var="invenGroupCount" value='${0}'/>
       <select name="inventoryGroup" id="inventoryGroup" class="selectBox" size="1">
        <option value="All"><fmt:message key="label.all"/></option>
        <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status">
          <c:set var="invenGroupCount" value='${invenGroupCount+1}'/>
          <c:set var="currentIg" value='${status.current.inventoryGroup}'/>
          <c:if test="${empty selectedIg}" >
              <c:set var="selectedIg" value=""/>
          </c:if>
          <c:choose>
            <c:when test="${currentIg == selectedIg}">
              <option value='<c:out value="${currentIg}"/>' selected><c:out value="${status.current.inventoryGroupName}"/></option>
            </c:when>
            <c:otherwise>
              <option value='<c:out value="${currentIg}"/>'><c:out value="${status.current.inventoryGroupName}"/></option>
            </c:otherwise>
          </c:choose>
        </c:forEach>
       </select>
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.itemid"/>:</td>
      <td width="10%" class="optionTitleBoldLeft">
         <input class="inputBox" type="text" name="itemId" id="itemId" value='<c:out value="${param.itemId}"/>' size="15">
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.mfglot"/>:</td>
      <td width="10%" class="optionTitleBoldLeft">
         <input class="inputBox" type="text" name="mfgLot" id="mfgLot" value='<c:out value="${param.mfgLot}"/>' size="25">
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.po"/>:</td>
      <td width="10%" class="optionTitleBoldLeft">
         <input class="inputBox" type="text" name="radianPo" id="radianPo" value='<c:out value="${param.radianPo}"/>' size="15">
      </td>
    </tr>
    <tr>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="transactions.transtype"/>:&nbsp;</td>
      <td width="10%">
        <html:select property="transType" styleClass="selectBox" styleId="transType">
          <html:option value="ALL" key="label.all"/>
          <html:option value="OV" key="graphs.label.receipts"/>
          <html:option value="IT" key="label.transfers"/>
          <html:option value="RI" key="graphs.label.issues"/>
        </html:select>
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="transactions.ondate"/>:<br>(<fmt:message key="label.dateformat"/>)</td>
      <td width="10%">
        <input type="text" name="txnOnDate" id="txnOnDate" class="inputBox" value='<c:out value="${param.txnOnDate}"/>' maxlength="10" size="8">
        <a href="javascript: void(0);" ID="linktxnOnDate" onclick="return getCalendar(document.genericForm.txnOnDate);">&diams;</a>
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.within"/></td>
      <td width="10%" class="optionTitleBoldLeft">
         <c:choose>
          <c:when test="${param.daysOld == null}" >
              <c:set var="numDaysOld" value="60"/>
          </c:when>
          <c:otherwise>
              <c:set var="numDaysOld" value="${param.daysOld}"/>
          </c:otherwise>
         </c:choose>
         <input class="inputBox" type="text" name="daysOld" id="daysOld" value='<c:out value="${numDaysOld}"/>' size="5">&nbsp;<fmt:message key="label.days"/>
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.sortby"/>:&nbsp;</td>
      <td width="10%">
        <html:select property="sortBy" styleClass="selectBox" styleId="sortBy">
          <html:option value="dateOfReceipt" key="inventorydetail.label.dor"/>
          <html:option value="expireDate" key="transactions.expiredate"/>
          <html:option value="itemId" key="label.itemid"/>
          <html:option value="prNumber" key="label.mrnumber"/>
          <html:option value="radianPo" key="label.po"/>
          <html:option value="receiptId" key="label.receiptid"/>
          <html:option value="transactionDate" key="transactions.transactiondate"/>
        </html:select>
      </td>
    </tr>
    <tr>
     <td colspan="8" class="optionTitleBoldLeft">
          <input name="submitSearch" type="submit" class="inputBtns" value="Search" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'">
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

<c:if test="${transactionsColl != null}" >
<!-- Search results start -->
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
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <c:forEach var="transaction" items="${transactionsColl}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr align="center">
      <th width="5%"><fmt:message key="label.type"/></th>
      <th width="5%"><fmt:message key="label.inventorygroup"/></th>
      <th width="5%"><fmt:message key="label.receiptid"/></th>
      <th width="5%"><fmt:message key="transactions.receivername"/></th>
      <th width="5%"><fmt:message key="transactions.source"/></th>
      <th width="5%"><fmt:message key="transactions.picklistid"/></th>
      <th width="5%"><fmt:message key="transactions.issuername"/></th>
      <th width="5%"><fmt:message key="transactions.transferdestination"/></th>
      <th width="5%"><fmt:message key="label.itemid"/></th>
      <th width="5%"><fmt:message key="label.quantity"/></th>
      <th width="5%"><fmt:message key="label.lotstatus"/></th>
      <th width="5%"><fmt:message key="label.mfglot"/></th>
      <th width="5%"><fmt:message key="label.bin"/></th>
      <th width="5%"><fmt:message key="receivedreceipts.label.dor"/><br>(<fmt:message key="label.deliveryticket"/>)</th>
      <th width="5%"><fmt:message key="transactions.storagetemp"/></th>
      <th width="5%"><fmt:message key="transactions.receivedpicked"/></th>
      <th width="5%"><fmt:message key="label.delivered"/></th>
      <th width="5%"><fmt:message key="label.poline"/></th>
      <th width="5%"><fmt:message key="label.mrline"/></th>
      <th width="5%" colspan=2><fmt:message key="transactions.receiptnotes"/></th>
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

    <tr align="center" class='<c:out value="${colorClass}"/>' id='rowId<c:out value="${status.index}"/>'>
      <td width="5%"><c:out value="${transaction.transactionType}"/></td>
      <td width="5%"><c:out value="${transaction.inventoryGroupName}"/></td>
      <td width="5%">
        <a href="javascript:doPrintrelabel('<c:out value="${transaction.receiptId}"/>');">
          <c:out value="${transaction.receiptId}"/>
        </a>
      </td>
      <td width="5%"><c:out value="${transaction.receiverName}"/></td>
      <td width="5%"><c:out value="${transaction.transferredFrom}"/></td>
      <td width="5%">
       <c:if test="${transaction.picklistId > 0}">
        PL <c:out value="${transaction.picklistId}"/>
       </c:if>
       &nbsp;
      </td>
      <td width="5%"><c:out value="${transaction.issuerName}"/></td>
      <td width="5%"><c:out value="${transaction.transferredTo}"/></td>
      <td width="5%"><c:out value="${transaction.itemId}"/></td>
      <td width="5%"><c:out value="${transaction.quantity}"/></td>
      <td width="5%"><c:out value="${transaction.lotStatus}"/></td>
      <td width="5%"><c:out value="${transaction.mfgLot}"/></td>
      <td width="5%">
        <a href="javascript:showBinHistory('<c:out value="${transaction.receiptId}"/>');">
          <c:out value="${transaction.bin}"/>
        </a>
      </td>
      <td width="5%">
         <fmt:formatDate var="fmtReceiptDate" value="${transaction.dateOfReceipt}" pattern="MM/dd/yyyy"/>
         <c:out value="${fmtReceiptDate}"/>
         <c:if test="${!empty transaction.deliveryTicket}">
            <br>(<c:out value="${transaction.deliveryTicket}"/>)
         </c:if>
      </td>
      <td width="5%"><c:out value="${transaction.labelStorageTemp}"/></td>
      <td width="5%">
          <fmt:formatDate var="fmtTxnDate" value="${transaction.transactionDate}" pattern="MM/dd/yyyy kk:mm"/>
         <c:out value="${fmtTxnDate}"/>
      </td>
      <td width="5%">
        <fmt:formatDate var="fmtDelivDate" value="${transaction.dateDelivered}" pattern="MM/dd/yyyy"/>
        <c:choose>
           <c:when test="${!empty transaction.batch}">
            <a href="javascript:printPackingList('${transaction.batch}');">
              <c:out value="${fmtDelivDate}"/>
            </a>
           </c:when>
           <c:otherwise>
              <c:out value="${fmtDelivDate}"/>
           </c:otherwise>
         </c:choose>
      </td>
      <td width="5%">
        <c:choose>
          <c:when test="${transaction.radianPo != null}">
           <c:out value="${transaction.radianPo}"/>-<c:out value="${transaction.poLine}"/>
          </c:when>
          <c:otherwise>
             &nbsp;
          </c:otherwise>
        </c:choose>
      </td>
      <td width="5%">
        <c:choose>
          <c:when test="${transaction.prNumber != null && transaction.lineItem != null}">
          <c:choose>
           <c:when test="${empty transaction.batch && !empty transaction.picklistId}">
            <a href="javascript:doPrintBol('<c:out value="${transaction.prNumber}"/>','<c:out value="${transaction.lineItem}"/>','<c:out value="${transaction.picklistId}"/>');">
              <c:out value="${transaction.prNumber}"/>-<c:out value="${transaction.lineItem}"/>
            </a>
           </c:when>
           <c:when test="${!empty transaction.batch && empty transaction.picklistId}">
            <a href="javascript:doPrintBol('<c:out value="${transaction.prNumber}"/>','<c:out value="${transaction.lineItem}"/>','<c:out value="${transaction.batch}"/>');">
              <c:out value="${transaction.prNumber}"/>-<c:out value="${transaction.lineItem}"/>
            </a>
           </c:when>
           <c:otherwise>
              <c:out value="${transaction.prNumber}"/>-<c:out value="${transaction.lineItem}"/>
           </c:otherwise>
          </c:choose>
         </c:when>
         <c:otherwise>
            &nbsp;
         </c:otherwise>
        </c:choose>
      </td>
      <c:choose>
      <c:when test="${inventoryGroupPermission == 'Yes'}">
      <td width="10%" onmouseover='style.cursor="pointer"' onclick='showNotes("TxnNote<c:out value="${dataCount}"/>");'>
      </c:when>
      <c:otherwise>
      <td width="10%" colspan=2 onmouseover='style.cursor="pointer"' onclick='showNotes("TxnNote<c:out value="${dataCount}"/>");'>
      </c:otherwise>
      </c:choose>
        <c:choose>
        <c:when test='${! empty transaction.notes}'>
          <div id='spanTxnNote<c:out value="${dataCount}"/>'>
            <p id='pgphTxnNote<c:out value="${dataCount}"/>'>+</p>
            <div id='divTxnNote<c:out value="${dataCount}"/>' style='display:none'>
              <c:out value="${transaction.notes}"/>
            </div>
          </div>
        </c:when>
        <c:otherwise>
           &nbsp;
        </c:otherwise>
        </c:choose>
      </td>
      <c:if test="${inventoryGroupPermission == 'Yes'}">
       <td width="5%">
         <input type="image" name="receiptNotes" src="/images/ponotes.gif" alt='<fmt:message key="transactions.receiptnotes"/>' onclick="getReceiptNotes('<c:out value="${transaction.receiptId}"/>'); return false;" border=1>
       </td>
      </c:if>
    </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty transactionsColl}" >

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

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of contentarea -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>

