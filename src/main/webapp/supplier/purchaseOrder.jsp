<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

<!-- Use this tag to get the correct CSS class.
This looks at what the users preffered font size and which application he is viewing to set the correct CSS. -->
<%@ include file="/common/locale.jsp" %>
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
<%-- 
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>
This handels the menu style and what happens to the right click on the whole page 
--%>
<!--  -->
<script type="text/javascript" src="/js/supplier/purchaseOrder.js"></script>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

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
<fmt:message key="label.haastcm"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
<c:set var="invalidShipDate"><fmt:message key="label.invalidShipDate"/></c:set>
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
invalidPrice:'<fmt:message key="label.invalidPrice"/>',invalidId:'<fmt:message key="label.invalidId"/>',
invalidShipDate:'<tcmis:jsReplace value="${invalidShipDate}"/>',
invalidDockDate:'<fmt:message key="label.invalidDockDate"/>',
invalidTotal:'<fmt:message key="label.invalidTotal"/>',
dateError:'<fmt:message key="msg.dockDateShipDateOrder"/>',
label1:"<fmt:message key='label.itemid'/>",
label2:"<fmt:message key="label.itemdescription"/>",
label3:"<fmt:message key="label.quantity"/>",
label4:"<fmt:message key="label.unitprice"/>",
label5:"<fmt:message key="label.remove"/>",
rejectpomsg:"<fmt:message key="label.rejectpomsg"/>",
confirmPO:"<fmt:message key="label.confirmPO"/> "
}
;

// -->
</script>
<script src="/js/calendar/util.js"></script>
<script src="/js/calendar/rolloverImages.js"></script>
<!-- 
-->

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
<fmt:message key="label.web.dbuy.po.num"/><c:out value="${POHeaderBean.radianPo}"/>
</td>
<td width="30%" class="headingr" align="right">
<!--
<html:link style="color:#FFFFFF" href="orderList.jsp">
 <fmt:message key="label.order.list"/>
</html:link>
--><%--
&nbsp;&nbsp;&nbsp;&nbsp;
<html:link style="color:#FFFFFF" forward="logout">
 <fmt:message key="label.log.off"/>
</html:link>--%>
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
       <td rowspan="4" class="optionTitleBoldRight" valign="top"><fmt:message key="label.supplier"/>:</td>
       <td rowspan="4" class="optionTitleLeft" valign="top" width="25%">
         ${POHeaderBean.supplierName}<br/>
         <c:if test="${! empty POHeaderBean.supplierAddressLine1}">
     				${POHeaderBean.supplierAddressLine1}<br/>
			   </c:if>
         <c:if test="${! empty POHeaderBean.supplierAddressLine2}">
     				${POHeaderBean.supplierAddressLine2}<br/>
			   </c:if>
         <c:if test="${! empty POHeaderBean.supplierAddressLine3}">
     				${POHeaderBean.supplierAddressLine3}<br/>
			   </c:if>
         ${POHeaderBean.supplierCity},${POHeaderBean.supplierStateAbbrev}&nbsp;${POHeaderBean.supplierZip}&nbsp;${POHeaderBean.supplierCountryAbbrev}<br/>
         <fmt:message key="label.phoneno"/>:&nbsp;${POHeaderBean.supplierPhone}<br/>
         <fmt:message key="label.email"/>:&nbsp;${POHeaderBean.supplierEmail}
         <img src="/images/minwidth.gif" width="100" height="0">
       </td>

       <td rowspan="4" class="optionTitleBoldRight" valign="top" nowrap><fmt:message key="label.shipto"/>:</td>
       <td rowspan="4" class="optionTitleLeft" valign="top" width="25%">
         ${POHeaderBean.shiptoLocationDesc}<br/>
         <c:if test="${! empty POHeaderBean.shiptoAddressLine1}">
     				${POHeaderBean.shiptoAddressLine1}<br/>
			   </c:if>
         <c:if test="${! empty POHeaderBean.shiptoAddressLine2}">
     				${POHeaderBean.shiptoAddressLine2}<br/>
			   </c:if>
         <c:if test="${! empty POHeaderBean.shiptoAddressLine3}">
     				${POHeaderBean.shiptoAddressLine3}<br/>
			   </c:if>
         ${POHeaderBean.shiptoCity},${POHeaderBean.shiptoStateAbbrev}&nbsp;${POHeaderBean.shiptoZip}&nbsp;${POHeaderBean.shiptoCountryAbbrev}<br/>
         <img src="/images/minwidth.gif" width="100" height="0">
       </td>

       <td class="optionTitleBoldRight" valign="top"><fmt:message key="label.po"/>:</td>
       <td class="optionTitleLeft" valign="top"  width="10%">${POHeaderBean.radianPo}</td>
     </tr>
     <tr>
       <td class="optionTitleBoldRight" valign="top">&nbsp;<%--<fmt:message key="label.bo"/>:--%></td>
       <td class="optionTitleLeft" valign="top" width="10%">&nbsp;<%--${POHeaderBean.bo}--%></td>
     </tr>
     <tr>
        <td class="optionTitleBoldRight" valign="top"><fmt:message key="label.critical"/>:</td>
        <td class="optionTitleLeft" valign="top" width="10%">&nbsp;${POHeaderBean.critical}</td>
     </tr>

     <tr>
        <td class="optionTitleBoldRight" valign="top"><fmt:message key="label.customerpo"/>:<br/><img src="/images/minwidth.gif" width="80" height="0"></td>
        <td class="optionTitleLeft" valign="top" width="10%">&nbsp;${POHeaderBean.customerPo}</td>
     </tr>

     <tr>
         <td class="optionTitleBoldRight" valign="top"><fmt:message key="label.order.taker"/>:<br/><img src="/images/minwidth.gif" width="80" height="0"></td>
         <td class="optionTitleLeft" valign="top" width="25%">${POHeaderBean.supplierContactName}<br/>
         <fmt:message key="label.phoneno"/>:&nbsp;${POHeaderBean.supplierContactPhone}<br/>
         <fmt:message key="label.email"/>:&nbsp;<a href='mailto:${POHeaderBean.email}'>${POHeaderBean.email}</a><br/>
         <fmt:message key="label.fax"/>:&nbsp;${POHeaderBean.supplierContactFax}
         <img src="/images/minwidth.gif" width="100" height="0">
         </td>

        <td class="optionTitleBoldRight" valign="top"><fmt:message key="label.shipping"/>:</td>
        <td class="optionTitleLeft" valign="top" width="25%">
          <c:if test="${!empty POHeaderBean.carrierHub}">
     				${POHeaderBean.carrierHub}/
	      </c:if>
         <c:choose>
          <c:when test="${status.current.carrierCompanyId == 'Radian'}">
			      Haas TCM<br/>
          </c:when>
          <c:otherwise>
           ${POHeaderBean.carrierCompanyId}<br/>
          </c:otherwise>
         </c:choose>
         ${POHeaderBean.carrierName}
         <c:if test="${! empty POHeaderBean.carrierAccount}">
     				/${POHeaderBean.carrierAccount}
	      </c:if>
	      <img src="/images/minwidth.gif" width="100" height="0">
         </td>

        <td class="optionTitleBoldRight" valign="top"><fmt:message key="label.buyer"/>:</td>
        <td class="optionTitleLeft" valign="top" width="10%">${POHeaderBean.buyerName}<br/>
        <fmt:message key="label.phoneno"/>:&nbsp;${POHeaderBean.buyerPhone}<br/>
        <fmt:message key="label.email"/>:&nbsp;<a href='mailto:${POHeaderBean.buyerEmail}'>${POHeaderBean.buyerEmail}</a>
        <img src="/images/minwidth.gif" width="100" height="0">
        </td>
      </tr>

      <tr>
        <td class="optionTitleBoldRight"valign="top"><fmt:message key="label.tradeTerms"/>:<br/><img src="/images/minwidth.gif" width="80" height="0"></td>
        <td class="optionTitleLeft" valign="top" width="25%">${POHeaderBean.freightOnBoard}</td>

        <td class="optionTitleBoldRight" valign="top" nowrap><fmt:message key="label.hub"/>:</td>
        <td class="optionTitleLeft" valign="top" width="25%">${POHeaderBean.hubName}</td>

        <td class="optionTitleBoldRight"valign="top"><fmt:message key="label.inventorygroup"/>:<br/><img src="/images/minwidth.gif" width="90" height="0"></td>
        <td class="optionTitleLeft" valign="top" width="25%">${POHeaderBean.inventoryGroup}</td>

      </tr>

      <%--<tr>

       <td class="optionTitleBoldRight" valign="top">&nbsp;--%><%--<fmt:message key="label.consignedpo"/>:<br/><img src="/images/minwidth.gif" width="90" height="0">--%><%--</td>
       <td class="optionTitleLeft" valign="top" width="10%">&nbsp;--%><%--${POHeaderBean.consignedPo}--%><%--</td>
     </tr>
--%>
     
     <tr>
         <td class="optionTitleBoldRight" valign="top"><fmt:message key="label.totalamount"/>:<br/><img src="/images/minwidth.gif" width="80" height="0"></td>
         <td class="optionTitleLeft" valign="top" width="25%">&nbsp;${POHeaderBean.poPrice}</td>

         <td class="optionTitleBoldRight" valign="top"><fmt:message key="label.currency"/>:</td>
         <td width="25%" valign="top">
         <c:set var="materialdataCount" value='${0}'/>
          <c:forEach items="${MaterialBeans}" var="line">
            <c:if test="${materialdataCount == 0}">
             ${line.currencyId}
            </c:if>
            <c:set var="materialdataCount" value="${materialdataCount+1}"/>
          </c:forEach>
         </td>

        <td class="optionTitleBoldRight" valign="top"><fmt:message key="label.paymentterms"/>:<br/><img src="/images/minwidth.gif" width="120" height="0"></td>
        <td class="optionTitleLeft" valign="top" width="25%">${POHeaderBean.paymentTerms}</td>

      </tr>

     <tr>
       <td class="optionTitleBoldRight" valign="top">
          <fmt:message key="label.date.created"/>:<br/><img src="/images/minwidth.gif" width="80" height="0">
       </td>
       <td class="optionTitleLeft" valign="top" width="25%">
         <fmt:formatDate var="fmtDateCreated" value="${POHeaderBean.dateSent}" pattern="${dateFormatPattern}"/>
         ${fmtDateCreated}
       </td>

       <td colspan="4" align="center">
         <c:if test="${!empty StatusBean}">
         <c:choose>
            <c:when  test="${(StatusBean eq 'CONFIRMED')}">
              <fmt:message key="label.to.see.pdf"/>
              <input type='image' name='view' id='view' src='/images/pdf_logo_trefoil.gif' align="center" onClick="printpo('2')">
              <input type='hidden' name='po' id='po' value='<c:out value="${POHeaderBean.radianPo}"/>'>
            </c:when>
            <c:otherwise>
              <fmt:message key="label.to.see.pdf"/>
              <input type='image' name='view' id='view' src='/images/pdf_logo_trefoil.gif' align="center" onClick="printpo('0')">
              <input type='hidden' name='po' id='po' value='<c:out value="${POHeaderBean.radianPo}"/>'>
            </c:otherwise>
         </c:choose>
         </c:if>
      </td>
    </tr>             
          
   <c:if test="${!empty ConfirmedBean}">
   <tr>    
      <td class="optionTitleBoldRight">    
         <fmt:message key="label.po.status"/>: 
      </td>
      <td class="optionTitleBoldLeft" colspan="5">
         <c:out value="${ConfirmedBean}"/>
         <c:if test="${!empty CommentsBean}">
           <c:out value="${CommentsBean}"/>
         </c:if>
      </td>
   </tr>
   </c:if>         
   </table>
 <!--  template starts here -->
 
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
<form name='goConfirmForm' action='goconfirm.do'>
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
          <c:set var="everConfirmed" value="${POHeaderBean.everConfirmed}"/>
            <c:if test="${empty ConfirmedBean && everConfirmed == 'N'}">
                    <input type='hidden' name='radianpo' id='radianpo' value='<c:out value="${POHeaderBean.radianPo}"/>'>
                    <input type="hidden" name="po_action" id="po_action" value='save'>
                    <span id="comfirmlink"><a href="#" onclick="confirmPO();"><fmt:message key="label.confirm"/></a>| </span>
                  <span id="savelink"><a href="#" onclick="savePO();"><fmt:message key="label.save"/></a>| </span>
                    <a href="#" onclick="problemPO();"><fmt:message key="label.problem"/></a>
                  | <a href="#" onclick="rejectPO();"><fmt:message key="label.reject"/></a>                    
            </c:if>
</div>
     <div class="optionTitleBoldLeft"><fmt:message key="label.material.line"/></div>
 <div class="dataContent">
<input name='quantity_0' id='quantity_0' value='<c:out value="${TotalQuantity}"/>' type='hidden'/>
<c:set var="dataCount" value="${0}"/>
<table name="materialTable" id="materialTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
<c:forEach items="${MaterialBeans}" var="line">
    <c:set var="colorClass" value=""/>
	<c:set var="dataCount" value="${dataCount+1}"/>
  <c:if test="${dataCount == 1}">
    <tr>
      <th width="2%"><fmt:message key="label.line"/></th>
      <th width="5%"><fmt:message key="label.item"/></th>
      <th width="20%"><fmt:message key="label.itemdescription"/></th>
      <th width="2%"><fmt:message key="label.rem.shelf.life"/></th>
      <th><fmt:message key="label.needed"/></th>
      <th width="5%"><fmt:message key="label.quantity"/></th>
      <th><fmt:message key="label.packaging"/></th>
      <th width="5%"><fmt:message key="label.unitprice"/></th>
      <th width="5%"><fmt:message key="label.line.total.price"/></th>
      <th width="5%"><fmt:message key="label.shipdate"/></th>
      <th width="5%"><fmt:message key="label.dock.date"/></th>
      <th width="5%"><fmt:message key="label.supplierpartnum"/></th>
      <%--<th width="5%"><fmt:message key="label.mfgpartno"/></th>--%>
      <th><fmt:message key="label.notes"/></th>
    </tr>    
  </c:if>

   <c:choose>
   <c:when test="${dataCount % 2 == 0}" >
    <c:set var="colorClass" value=''/>
   </c:when>
   <c:otherwise>
    <c:set var="colorClass" value='alt'/>
   </c:otherwise>
  </c:choose>

    <tr class='<c:out value="${colorClass}"/>'>
      <td width="2%"><input type='hidden' name='po_line_<c:out value="${dataCount}"/>' id='po_line_<c:out value="${dataCount}"/>' value='<c:out value="${line.poLine}"/>'><span name="label_po_line_<c:out value="${dataCount}"/>" id="label_po_line_<c:out value="${dataCount}"/>" /><c:out value="${line.poLine}"/></span></td>
      <td width="5%"><input type='hidden' name='item_id_<c:out value="${dataCount}"/>' id='item_id_<c:out value="${dataCount}"/>' value='<c:out value="${line.itemId}"/>'><c:out value="${line.itemId}"/></td>
      <td width="20%" title="${line.itemDesc}"><c:out value="${line.itemDescShort}"/>

      <td width="2%"><c:out value="${line.remainingShelfLifePercent}"/>&nbsp;%</td>      
      <td width="5%" nowrap>
        <fmt:formatDate var="fmtNeedDate" value="${line.needDate}" pattern="${dateFormatPattern}"/>
        <c:out value="${fmtNeedDate}"/>          
      </td>
      <td width="5%">${line.quantity}
          <input type='hidden' size='2' name='quantity_<c:out value="${dataCount}"/>' id='quantity_<c:out value="${dataCount}"/>' value='<c:out value="${line.quantity}"/>' <c:if test="${!empty ConfirmedBean}">disabled</c:if>/>
      </td>
      <td width="10%" title="${line.packaging}"><c:out value="${line.packagingShort}"/>

      <td width="5%">
        <input type='hidden' size='4' name='unit_price_<c:out value="${dataCount}"/>' value='<c:out value="${line.unitPrice}"/>' <c:if test="${!empty ConfirmedBean}">disabled</c:if> />
        ${line.unitPrice}&nbsp;${line.currencyId}
      </td>
      <td width="5%"><c:out value="${line.extendedPrice}"/>&nbsp;${line.currencyId}</td>

      <td width="5%">
        <fmt:formatDate var="fmtShipDate" value='${line.vendorShipDate}' pattern="${dateFormatPattern}"/>
             <input class="inputBox pointer" readonly type="text" name='vendor_ship_date_<c:out value="${dataCount}"/>' id='vendor_ship_date_<c:out value="${dataCount}"/>' 
                    <c:if test="${empty ConfirmedBean}">
                    onClick='getCalendar(document.goConfirmForm.vendor_ship_date_<c:out value="${dataCount}"/>);'
                    </c:if>
                    size="8" value='<c:out value="${fmtShipDate}"/>'/>
      </td>
      <td width="5%">
        <fmt:formatDate var="fmtDockDate" value='${line.promisedDate}' pattern="${dateFormatPattern}"/>
             <input class="inputBox pointer" readonly type="text" name='promised_date_<c:out value="${dataCount}"/>' id='promised_date_<c:out value="${dataCount}"/>'
                    <c:if test="${empty ConfirmedBean}">
                    onClick='getCalendar(document.goConfirmForm.promised_date_<c:out value="${dataCount}"/>,null,null,document.goConfirmForm.vendor_ship_date_<c:out value="${dataCount}"/>);'
                    </c:if>
                    size="8" value='<c:out value="${fmtDockDate}"/>'/>
      </td>
     <td width="5%"><c:out value="${line.supplierPartNo}"/></td>
     <%--<td width="5%"><c:out value="${line.mfgPartNo}"/></td>--%>
     <td  width="10%" title="${line.poLineNote}"><c:out value="${line.notesShort}"/>
    </tr>

    <%--TODO Test concept of allowing split lines and release.--%>
     <%--<c:if test="${empty ConfirmedBean and !(StatusBean eq 'PROBLEM')}">
     <span id="newMaterialspan" onclick="addMaterialLine()" style="texareaannounce"><img src="/images/plus.jpg" name="MaterialAdd" id="1">&nbsp;<fmt:message key="label.split"/></span>
	 </c:if>--%>

   <tr>
       <td valign='top' colspan="6">
        <div class="optionTitleBoldLeft"><fmt:message key="label.specs"/> </div>
    <table width="100%" name="specTable" id="specTable" border="0" cellpadding="0" cellspacing="0" class="tableResults">
         <c:set var="specs" value="${line.specs}"/>
         <c:if test="${!empty specs}">
         <tr>
             <th width="2%"><fmt:message key="label.line"/></th>
             <th><fmt:message key="label.item"/></th>
             <th><fmt:message key="label.spec"/></th>
             <th><fmt:message key="label.cofc"/></th>
             <th><fmt:message key="label.cofa"/></th>
         </tr>
         </c:if>
         <c:if test="${empty specs}">
          <tr><td colspan='4'><fmt:message key="label.no.spec"/></td></tr>
         </c:if>
         <c:set var="specColorClass" value="alt"/>
         <c:set var="specDataCount" value="${0}"/>
         <c:forEach items="${specs}" var="specline">
           <c:set var="specDataCount" value="${specDataCount+1}"/>
           <c:choose>
           <c:when test="${specDataCount % 2 == 0}" >
            <c:set var="specColorClass" value=''/>
           </c:when>
           <c:otherwise>
            <c:set var="specColorClass" value='alt'/>
           </c:otherwise>
          </c:choose>
          <tr class='<c:out value="${specColorClass}"/>'>
             <td><c:out value="${line.poLine}"/></td>
             <td><c:out value="${line.itemId}"/></td>
             <td><c:out value="${specline.specIdDisplay}"/></td>
             <td><c:out value="${specline.savedCoc}"/></td>
             <td><c:out value="${specline.savedCoa}"/></td>          
          </tr>
         </c:forEach>
        </table>
       </td>    
       <td valign='top' colspan="7">
        <div class="optionTitleBoldLeft"><fmt:message key="label.flow.down"/></div>
    <table border="0" name="flowTable" id="flowTable" cellpadding="0" cellspacing="0" class="tableResults">
         <c:set var="flowdowns" value="${line.flowdowns}"/>
         <c:if test="${!empty flowdowns}">
         <tr>
             <th width="2%"><fmt:message key="label.line"/></th>
             <th><fmt:message key="label.item"/></th>
             <th><fmt:message key="label.flow.down"/></th>
             <th><fmt:message key="label.flow.down.desc"/></th>
             <th><fmt:message key="label.rev.date"/></th>
         </tr>
         </c:if>
         <c:if test="${empty flowdowns}">
          <tr><td colspan='4'><fmt:message key="label.no.flowdowns"/></td></tr>
         </c:if>
        <c:set var="flowColorClass" value="alt"/>
        <c:set var="flowDataCount" value="${0}"/>
         <c:forEach items="${flowdowns}" var="flowline">
             <c:if test="${fn:toLowerCase(flowline.flowDownType) != 'receiving'}">
		          <c:set var="flowDataCount" value="${flowDataCount+1}"/>
		          <c:choose>
		           <c:when test="${flowDataCount % 2 == 0}" >
		            <c:set var="flowColorClass" value=''/>
		           </c:when>
		           <c:otherwise>
		            <c:set var="flowColorClass" value='alt'/>
		           </c:otherwise>
		          </c:choose>
		          <tr class='<c:out value="${flowColorClass}"/>'>
		             <td><c:out value="${line.poLine}"/></td>
		             <td><c:out value="${line.itemId}"/></td>
		             <td><c:out value="${flowline.flowDown}"/></td>
		             <td><c:out value="${flowline.flowDownDesc}"/></td>
		             <td><c:out value="${flowline.revisionDate}"/></td>          
		          </tr>
         	</c:if>
         </c:forEach>
        </table>       
       </td>
   </tr>   
</c:forEach>
</table>
<!-- merged into the add charge form </form> -->
<c:if test="${!empty AddChargeBeans}">
<!--  merged into above form  <form name='goAddtlChargesForm' action='goremove.do'>  -->
<!--  Already got the po    <input type='hidden' name='radianpo' value='<c:out value="${POHeaderBean.radianPo}"/>'>  -->
     <input type='hidden' name='line' value=''>
 <div class="optionTitleBoldLeft"><fmt:message key="label.additional.charges"/></div>
  <table border="0" name="addChargesTable" id="addChargesTable" cellpadding="0" cellspacing="0" class="tableResults">  
  <tr>
     <td colspan="2">
	 <table width="100%" name="oriAddChargeTable" id="oriAddChargeTable" border="0" cellpadding="0" cellspacing="0" class="tableResults">
        <tr>
            <th><fmt:message key="label.line"/></th>
            <th><fmt:message key="label.item"/></th>
            <th><fmt:message key="label.itemdescription"/></th>
            <th><fmt:message key="label.quantity"/></th>
            <th><fmt:message key="label.unitprice"/></th>
            <th><fmt:message key="label.line.total.price"/></th>
        </tr>
        <c:set var="dataCount" value="${0}"/>        
        <c:forEach items="${AddChargeBeans}" var="addcharges">
	    <input type="hidden" name='AddLineNum_<c:out value="${dataCount}"/>' id='AddLineNum_<c:out value="${dataCount}"/>' value='<c:out value="${addcharges.poLine}"/>'>
	    <input type="hidden" name='addItemID_<c:out value="${dataCount}"/>' id='addItemID_<c:out value="${dataCount}"/>' value='<c:out value="${addcharges.itemId}"/>'>
	    <input type="hidden" name='addDescription_<c:out value="${dataCount}"/>' id='addDescription_<c:out value="${dataCount}"/>' value='<c:out value="${addcharges.itemDesc}"/>'>
        <tr class="alt">
           <td><c:out value="${addcharges.poLine}"/></td>
           <td><c:out value="${addcharges.itemId}"/></td>
           <td><c:out value="${addcharges.itemDesc}"/></td>
           <td>
             <input type="text" size='3' name='addQuantity_<c:out value="${dataCount}"/>' id='addQuantity_<c:out value="${dataCount}"/>' class="inputBox" value='<c:out value="${addcharges.quantity}"/>' <logic:present name="ConfirmedBean"><fmt:message key="label.disabled"/></logic:present>>
           </td>
           <td>
             <input type="text" size='6' name='addUnitPrice_<c:out value="${dataCount}"/>' id='addUnitPrice_<c:out value="${dataCount}"/>' class="inputBox" value='<c:out value="${addcharges.unitPrice}"/>' <logic:present name="ConfirmedBean"><fmt:message key="label.disabled"/></logic:present>>            
           </td>
           <td><c:out value="${addcharges.extendedPrice}"/></td>
        </tr>
	    <c:set var="dataCount" value="${dataCount+1}"/>
        </c:forEach>
        </table>
     </td>
  </tr>
  <tr>
  </tr>
 </table>
<!--   </form> -->
</c:if>

<c:if test="${empty ConfirmedBean}">
  <table>
  <tr>
     <td>
     <div class="optionTitleBoldLeft"><span id="newlinespan" onclick="addFirstLine();expand('AddLineBlock');" class="texareaannounce pointer"><img src="/images/plus.jpg" name="LineAdd" id="1">&nbsp;<fmt:message key="label.addALine"/></span></div>
     <div id="AddLineBlock" style="display:none">
     <div name="addItemDiv" id="addItemDiv">
    <!--  merge into above form  <form name="AddLineForm" action="addchargeline.do"> -->
      <input name="addItemID" id="addItemID" type="hidden" />
      <input name="addDescription" id="addDescription" type="hidden" />
      <div name='taildiv' id='taildiv'>
          <%--TODO Allow adding multiple charges at the same time. Needs change to Haas PO Process (couple of days process)--%>
          <%--<input type="button" name="Add Line Item" id="Add Line Item" value="<fmt:message key="label.newCharge"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick='addNewLine();'/>--%>
          <input type='hidden' name='radianpo' id='radianpo' value='<c:out value="${POHeaderBean.radianPo}"/>'/>
      </div>
      </div>
      </div>
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
</form><!-- end of goConfirmForm  -->

<form name='SearchItemForm' action='addcharge.do' target="_blank">
 <input type='hidden' name='invgrp' value='<c:out value="${POHeaderBean.inventoryGroup}"/>'>
</form>

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
