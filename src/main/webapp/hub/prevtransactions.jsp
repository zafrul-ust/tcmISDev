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
<%@ include file="/common/locale.jsp" %>
<tcmis:fontSizeCss />

<!-- This handles the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<!-- This handles which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<%-- Add any other javascript you need for the page here --%>
<!--<script SRC="/js/supply/searchPOs.js" language="JavaScript"></script>-->
<script SRC="/js/hub/transactions.js" language="JavaScript"></script>

<title>
<fmt:message key="prevtransactions.title"/>
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

   altinvid["<c:out value="${currentHub}"/>"] = new Array(""
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

<!--<tcmis:form action="/previoustransactions.do" onsubmit="if (checkTransactionsInput()) return submitOnlyOnce(); else return false;">
-->
 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>

 <div id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<%--
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="prevtransactions.title"/>
</td>
<td width="30%" class="headingr">
</td>
</tr>
</table>
</div>
--%>

<!-- Error Messages Begins -->
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->

<div class="contentArea">
<!-- Search Option Begins -->
<!-- Search Option Ends -->
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
      <th width="5%"><fmt:message key="receivedreceipts.label.dor"/></th>
      <th width="5%"><fmt:message key="transactions.storagetemp"/></th>
      <th width="5%"><fmt:message key="transactions.receivedpicked"/></th>
      <th width="5%"><fmt:message key="label.delivered"/></th>
      <th width="5%"><fmt:message key="label.poline"/></th>
      <th width="5%"><fmt:message key="label.mrline"/></th>
      <th width="5%"><fmt:message key="transactions.receiptnotes"/></th>
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
      <td width="5%"><c:out value="${transaction.receiptId}"/></td>
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
      <td width="5%"><c:out value="${transaction.bin}"/></td>
      <td width="5%">
      	 <fmt:formatDate var="fmtReceiptDate" value="${transaction.dateOfReceipt}" pattern="${dateFormatPattern}"/>        
         <c:out value="${fmtReceiptDate}"/>
      </td>
      <td width="5%"><c:out value="${transaction.labelStorageTemp}"/></td>
      <td width="5%">
      	 <fmt:formatDate var="fmtTxnDate" value="${transaction.transactionDate}" pattern="${dateFormatPattern}"/>         
         <c:out value="${fmtTxnDate}"/>
      </td>
      <td width="5%">
      	 <fmt:formatDate var="fmtDelivDate" value="${transaction.dateDelivered}" pattern="${dateFormatPattern}"/>         
         <c:out value="${fmtDelivDate}"/>
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
            <c:out value="${transaction.prNumber}"/>-
             <c:out value="${transaction.lineItem}"/>
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
      <td width="10%" onmouseover='style.cursor="pointer"' onclick='showNotes("TxnNote<c:out value="${dataCount}"/>");'>
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

<!-- </tcmis:form> -->
</body>
</html:html>

