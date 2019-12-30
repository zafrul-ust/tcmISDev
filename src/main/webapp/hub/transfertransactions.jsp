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

<%--
<script SRC="/js/common/formchek.js" language="JavaScript"></script>
<script SRC="/js/common/commonutil.js" language="JavaScript"></script>
--%>
<%-- Add any other javascript you need for the page here --%>
<%--
<script SRC="/js/hub/transactions.js" language="JavaScript"></script>
--%>
<!-- This handles the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<!-- This handles which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<title>
<fmt:message key="transfertransactions.titlebar"/>: <c:out value="${param.transferRequestId}"/>
</title>

<script language="JavaScript" type="text/javascript">
   //add all the javascript messages here, this for internationalization of client side javascript messages
   var messagesData = new Array();
   messagesData={alert:"<fmt:message key="label.alert"/>",
                   and:"<fmt:message key="label.and"/>",
        submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
</script>

</head>

<body bgcolor="#ffffff">


 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;text-align:center;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="transfertransactions.titlebar"/>: <c:out value="${param.transferRequestId}"/>
</td>
</tr>
</table>
</div>

<div class="spacerY">&nbsp;</div>

<div class="contentArea">
<c:if test="${transferTransactionsColl != null}" >
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

    <c:forEach var="transaction" items="${transferTransactionsColl}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr align="center">
      <th width="5%"><fmt:message key="label.type"/></th>
      <th width="5%"><fmt:message key="label.itemid"/></th>
      <th width="5%"><fmt:message key="label.receiptid"/></th>
      <th width="5%"><fmt:message key="transfertransactions.originalreceiptid"/></th>
      <th width="5%"><fmt:message key="label.quantity"/></th>
      <th width="5%"><fmt:message key="label.mfglot"/></th>
      <th width="5%"><fmt:message key="label.bin"/></th>
      <th width="5%"><fmt:message key="label.delivered"/></th>
      <th width="5%"><fmt:message key="label.qcdate"/></th>
      <th width="5%"><fmt:message key="label.inventorygroup"/></th>
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
      <td width="5%"><c:out value="${transaction.docType}"/></td>
      <td width="5%"><c:out value="${transaction.itemId}"/></td>
      <td width="5%"><c:out value="${transaction.receiptId}"/></td>
      <td width="5%"><c:out value="${transaction.originalReceiptId}"/></td>
      <td width="5%"><c:out value="${transaction.quantity}"/></td>
      <td width="5%"><c:out value="${transaction.mfgLot}"/></td>
      <td width="5%"><c:out value="${transaction.bin}"/></td>
      <td width="5%">
         <fmt:formatDate var="fmtDelivDate" value="${transaction.dateDelivered}" pattern="MM/dd/yyyy"/>
         <c:out value="${fmtDelivDate}"/>
      </td>
      <td width="5%">
         <fmt:formatDate var="fmtQCDate" value="${transaction.qcDate}" pattern="MM/dd/yyyy kk:mm"/>
         <c:out value="${fmtQCDate}"/>
      </td>
      <td width="5%"><c:out value="${transaction.inventoryGroup}"/></td>
    </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty transferTransactionsColl}" >

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

</body>
</html:html>

