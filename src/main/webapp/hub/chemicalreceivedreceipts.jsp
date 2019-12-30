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
<tcmis:fontSizeCss/>
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

<script type="text/javascript" src="/js/hub/receiving.js"></script>

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>

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
// -->
</script>
</head>

<body bgcolor="#ffffff">

<!--Uncomment for production-->
<%--<tcmis:form action="/pagename.do" onsubmit="return submitOnlyOnce();">--%>

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
<!-- no home link for popup
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
-->
</tr>
</table>
</div>
<!-- close of topNavigation-->

<div class="contentArea">

<html:form action="/showchemicalreceivedreceipts.do" onsubmit="return SubmitOnlyOnce();">

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
  <td width="6%" class="optionTitle"><input id="option1" type="radio" name="paperSizeRadio" onClick="assignpaperSize('31')" value="31" checked="checked" class="radioBtns">
  	<fmt:message key="labels.label.size35"/></td>
  <td width="8%" class="optionTitle"><input id="option2" type="radio" name="paperSizeRadio" onClick="assignpaperSize('811')" value="811" class="radioBtns">
	<fmt:message key="labels.label.size811"/></td>

  <td width="5%" class="alignLeft">
   <html:button property="containerlabels" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "printContainerLabels()">
     <fmt:message key="receivedreceipts.button.containerlabels"/>
   </html:button>
  </td>

  <td width="5%" class="alignLeft">
   <html:button property="containerlabels" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "printRecDocumentLabels()">
     <fmt:message key="label.documentlabels" />
   </html:button>
  </td>

  <td width="5%" class="alignLeft">
   <html:button property="binlabels" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "printBinLabels()">
     <fmt:message key="receivedreceipts.button.binlabels"/>
   </html:button>
  </td>

  <td width="5%" class="alignLeft">
   <html:button property="receivinglabels" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="printReceivingBoxLabels()">
     <fmt:message key="receivedreceipts.button.receivinglabels"/>
   </html:button>
  </td>

  <td width="5%" class="alignLeft">
  <html:button property="printthispage" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="javascript: if (window.print) window.print();">
     <fmt:message key="label.printthispage"/>
   </html:button>
  </td>
</tr>

<tr>
<td width="5%" colspan="7" class="alignLeft">
<input type="checkbox" name="skipKitLabels" id="skipKitLabels" value="Yes">&nbsp;<fmt:message key="receivedreceipts.label.skipkitlabels"/>
&nbsp; <html:button property="containerlabels" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "cancel()">
   <fmt:message key="label.close"/>
 </html:button>
</td>
</tr>
</table>
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->

<c:if test="${receivedReceiptsCollection != null}" >
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

 <c:forEach var="receiptDescriptionViewBean" items="${receivedReceiptsCollection}" varStatus="status">
  <c:if test="${status.index % 10 == 0}">
   <tr align="center">
   <th width="4%"><fmt:message key="label.po"/><br><fmt:message key="label.poline"/><br>(<fmt:message key="label.customerpo"/>)</th>
   <th width="5%"><fmt:message key="label.supplier"/></th>
   <th width="5%"><fmt:message key="label.item"/><BR>(<fmt:message key="label.inventorygroup"/>)</th>
   <th width="12%"><fmt:message key="label.description"/></th>
   <th width="4%"><fmt:message key="receiving.label.qtyreceived"/></th>
   <th width="4%"><fmt:message key="label.packaging"/></th>
   <th width="4%"><fmt:message key="label.receiptid"/></th>
   <th width="5%" colspan="2"><fmt:message key="receiving.label.packagedqty"/>
     x <fmt:message key="receiving.label.packagedsize"/></th>
   <th width="4%"><fmt:message key="label.lot"/><br/><fmt:message key="label.bin"/></th>
   <th width="4%"><fmt:message key="label.dates"/></th>
   <th width="12%"><fmt:message key="label.notes" /></th>
   <th width="4%"><fmt:message key="receiving.label.deliveryticket" /></th>
   <th width="4%"><fmt:message key="label.qastatement" /></th>       
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

  <c:set var="critical" value='${status.current.critical}'/>
  <c:if test="${critical == 'Y' || critical == 'y'}">
   <c:set var="colorClass" value='red'/>
  </c:if>

  <c:if test="${critical == 'S' || critical == 's'}">
   <c:set var="colorClass" value='pink'/>
  </c:if>

  <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">

  <c:set var="kitCollection"  value='${status.current.kitCollection}'/>
  <bean:size id="listSize" name="kitCollection"/>
  <c:set var="mainRowSpan" value='${status.current.rowSpan}' />
  <c:set var="manageKitsAsSingleUnit" value='${status.current.manageKitsAsSingleUnit}'/>
  <c:set var="mvItem" value='${status.current.mvItem}'/>
  <%--<c:set var="docType" value='${status.current.docType}'/>
  <c:set var="qualityControlItem" value='${status.current.qualityControlItem}'/>--%>

  <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.radianPo}"/>
    <br><c:out value="${status.current.poLine}"/>
    <c:if test="${!empty status.current.poNumber}">
     <br>(<c:out value="${status.current.poNumber}"/>)
    </c:if>
  </td>
  <td width="7%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.supplierName}"/></td>
  <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.itemId}"/><br>(<c:out value="${status.current.inventoryGroupName}"/>)</td>

  <c:forEach var="receiptDescriptionViewBean" items="${kitCollection}" varStatus="kitstatus">
   <c:if test="${kitstatus.index > 0 && listSize > 1 }">
     <tr class="<c:out value="${colorClass}"/>" id="childRowId<c:out value="${status.index}"/><c:out value="${kitstatus.index}"/>">
   </c:if>

    <c:choose>
    <c:when test="${listSize > 1 && manageKitsAsSingleUnit == 'N'}">
     <td width="12%" class="alignLeft"><c:out value="${kitstatus.current.materialDesc}"/></td>
    </c:when>
    <c:when test="${manageKitsAsSingleUnit != 'N' && kitstatus.index == 0}">
     <td width="12%" class="alignLeft" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.lineDesc}"/></td>
    </c:when>
   </c:choose>

  <%--<fmt:formatDate var="formattedShipDate" value="${status.current.vendorShipDate}" pattern="MM/dd/yyyy"/>--%>
  <fmt:formatDate var="formattedDateOfReceipt" value="${status.current.dateOfReceipt}" pattern="MM/dd/yyyy"/>
  <fmt:formatDate var="formattedDom" value="${status.current.dateOfManufacture}" pattern="MM/dd/yyyy"/>
  <fmt:formatDate var="formattedDos" value="${status.current.dateOfShipment}" pattern="MM/dd/yyyy"/>
  <fmt:formatDate var="formattedExpirationDate" value="${status.current.expireDate}" pattern="MM/dd/yyyy"/>
  <c:if test="${formattedExpirationDate == '01/01/3000'}" >
    <c:set var="formattedExpirationDate" value='Indefinite'/>
  </c:if>

   <c:choose>
    <c:when test="${mvItem != 'Y'}">
         <c:choose>
	       <c:when test="${kitstatus.index == 0 && manageKitsAsSingleUnit == 'N'}">
          <td width="4%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${kitstatus.current.quantityReceived}"/></td>
          <td width="4%"><c:out value="${kitstatus.current.packaging}"/></td>
          <td width="4%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${kitstatus.current.receiptId}"/></td>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>" class="alignLeft" colspan="2">&nbsp;</td>
	       </c:when>
         <c:when test="${kitstatus.index > 0 && manageKitsAsSingleUnit == 'N'}">
          <td width="4%"><c:out value="${kitstatus.current.packaging}"/></td>
	       </c:when>
         <c:when test="${manageKitsAsSingleUnit != 'N'}">
          <td width="5%"><c:out value="${kitstatus.current.quantityReceived}"/></td>
          <td width="4%"><c:out value="${status.current.packaging}"/></td>
          <td width="5%"><c:out value="${kitstatus.current.receiptId}"/></td>
          <td width="5%" class="alignLeft" colspan="2">&nbsp;</td>
          </c:when>
          </c:choose>
          <td width="8%"><c:out value="${kitstatus.current.mfgLot}"/><br><c:out value="${kitstatus.current.bin}"/></td>
          <td width="5%" class="alignLeft"><fmt:message key="receivedreceipts.label.dom"/>:<c:out value="${formattedDom}"/><br>
           <fmt:message key="receivedreceipts.label.dor"/>:<c:out value="${formattedDateOfReceipt}"/><br>
           <fmt:message key="receivedreceipts.label.manufacturerdos"/>:<c:out value="${formattedDos}"/><br>
           <fmt:message key="receivedreceipts.label.expdate"/>:<c:out value="${formattedExpirationDate}"/><br>
           <%--<fmt:message key="receivedreceipts.label.suppliershipdate"/>:<c:out value="${formattedShipDate}"/><br>--%>
           &nbsp;
          </td>
	      </c:when>
        <c:otherwise>   <%--Here starts the variable packaging stuff--%>
         <c:if test="${kitstatus.index == 0}">
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.totalMvQuantityReceived}"/></td>
          <td width="4%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.packaging}"/></td>
         </c:if>
          <td width="5%"><c:out value="${kitstatus.current.receiptId}"/></td>
          <td width="5%" class="alignLeft" colspan="2"><c:out value="${kitstatus.current.quantityReceived}"/> X <c:out value="${kitstatus.current.costFactor}"/>
        <c:out value="${kitstatus.current.purchasingUnitOfMeasure}"/> <c:out value="${kitstatus.current.displayPkgStyle}"/>
        <br/><font class="invisible<c:out value="${colorClass}"/>">_____________</font></td>
        <c:if test="${kitstatus.index == 0}">
         <td width="8%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${kitstatus.current.mfgLot}"/><br/><c:out value="${kitstatus.current.bin}"/></td>
         <td width="5%" class="alignLeft" rowspan="<c:out value="${mainRowSpan}"/>"><fmt:message key="receivedreceipts.label.dom"/>:<c:out value="${formattedDom}"/><br/>
           <fmt:message key="receivedreceipts.label.dor"/>:<c:out value="${formattedDateOfReceipt}"/><br>
           <fmt:message key="receivedreceipts.label.manufacturerdos"/>:<c:out value="${formattedDos}"/><br>
           <fmt:message key="receivedreceipts.label.expdate"/>:<c:out value="${formattedExpirationDate}"/><br>
           <%--<fmt:message key="receivedreceipts.label.suppliershipdate"/>:<c:out value="${formattedShipDate}"/><br>--%>
           &nbsp;
          </td>
          <c:if test="${kitstatus.index == 0}">
            <td width="12%" rowspan="<c:out value="${mainRowSpan}"/>" class="alignLeft">
                ${status.current.notes}
            </td>
            <td rowspan="<c:out value="${mainRowSpan}"/>" class="alignLeft">
                ${status.current.deliveryTicket}
            </td>
            <td rowspan="<c:out value="${mainRowSpan}"/>" class="alignLeft">
                ${status.current.qaStatement}
            </td>
            </c:if>
        </c:if>


	      <%--<TD width="5%">
          <INPUT TYPE="text" name="receivingQcViewBean[<c:out value="${dataCount}"/>].labelQuantity" ID="labelQuantity<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.labelQuantity}"/>" size="4" maxlength="10" Class="DETAILS">
        </TD>
        <TD width="5%"><c:out value="${kitstatus.current.pendingQuantityReceived}"/></TD>
        <TD width="5%"><c:out value="${kitstatus.current.pendingActualPartSize}"/> <c:out value="${kitstatus.current.pendingSizeUnit}"/></TD>
        <c:if test="${kitstatus.index == 0}">
          <TD width="5%"  rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.pendingNominalPackaging}"/></TD>
          <TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.notes}"/></TD>
          <TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.deliveryTicket}"/></TD>
          <TD width="2%" rowspan="<c:out value="${mainRowSpan}"/>">&nbsp;</TD>
         </c:if>--%>
        </c:otherwise>
	    </c:choose>

<%--  <TD width="12%"><c:out value="${status.current.lineDesc}"/></TD>
  <TD width="8%"><c:out value="${status.current.mfgLot}"/><br><c:out value="${status.current.bin}"/><br><c:out value="${status.current.receiptId}"/></TD>

<%--


  <TD width="7%"><c:out value="${status.current.quantityReceived}"/></TD>
--%>
<c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>
 </tr>
</c:forEach>
</table>

  <!-- If the collection is empty say no data found -->
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
</c:if>
<!-- Search results end -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<!--<tcmis:saveRequestParameter/>-->
<input type="hidden" name="labelReceipts" id="labelReceipts" value="<c:out value="${labelReceipts}"/>">
<input type="hidden" name="paperSize" id="paperSize" value="31">
<input type="hidden" name="hubNumber" id="hubNumber" value="<c:out value="${hubNumber}"/>">
<input type="hidden" name="justReceived" id="justReceived" value="${justReceived}">
<input type="hidden" name="sourceHub" id="sourceHub" value="${sourceHub}">
<input type="hidden" name="inventoryGroup" id="inventoryGroup" value="${inventoryGroup}">

</div>
<!-- Hidden elements end -->

</html:form>
</div>
</div>
</body>
</html:html>