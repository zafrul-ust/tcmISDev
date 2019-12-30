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
<script type="text/javascript" src="/js/supplier/splitpoline.js"></script>

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
<fmt:message key="label.haastcm"/> <fmt:message key="label.po"/> ${POHeaderBean.radianPo}
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
invalidShipDate:'<fmt:message key="label.invalidShipDate"/>',invalidDockDate:'<fmt:message key="label.invalidDockDate"/>',
invalidTotal:'<fmt:message key="label.invalidTotal"/>',
dateError:'<fmt:message key="msg.dockDateShipDateOrder"/>',
label1:"<fmt:message key='label.itemid'/>",
label2:"<fmt:message key="label.itemdescription"/>",
label3:"<fmt:message key="label.quantity"/>",
label4:"<fmt:message key="label.unitprice"/>",
label5:"<fmt:message key="label.remove"/>",
selectNumber:"<fmt:message key="msg.selectNumber"/>",
qtyReceivedErrorMsg:"<fmt:message key="splitpoline.errormsg1"/>",
totalQtyErrorMsg:"<fmt:message key="splitpoline.errormsg2"/>",   
confirmPO:"<fmt:message key="label.confirmPO"/> "
};
// -->
</script>

</head>

<body bgcolor="#ffffff">

<tcmis:form action="/splitpoline.do" onsubmit="return submitPoOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>
 <div class="interface" id="mainPage">

<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
<div class="topNavigation" id="topNavigation">
<table width="100%" border="1" cellpadding="0" cellspacing="0">
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
<fmt:message key="label.haastcm"/> <fmt:message key="label.po"/>${POHeaderBean.radianPo}
</td>
<td width="30%" class="headingr" align="right">
&nbsp;
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
   <!-- Error Messages Begins -->
   <div id="errorMessagesArea" class="errorMessages">
     <html:errors/>
     ${tcmISError}
   </div>
   <!-- Error Messages Ends -->

   <c:set var="dbuyLockStatus" value=''/>
   <c:if test="${POHeaderBean.dbuyLockStatus == 'Y' || POHeaderBean.dbuyLockStatus == 'y'}" >
     <c:set var="dbuyLockStatus" value='Yes'/>
   </c:if>

   <c:set var="everConfirmed" value=''/>
   <c:if test="${POHeaderBean.everConfirmed == 'Y' || POHeaderBean.everConfirmed == 'y'}" >
     <c:set var="everConfirmed" value='Yes'/>
   </c:if>
         
    <!-- Insert all the search option within this div -->
   <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
     <tr>
       <td rowspan="2" class="optionTitleBoldRight" valign="top"><fmt:message key="label.supplier"/>:</td>
       <td rowspan="2" class="optionTitleLeft" valign="top" width="25%">
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
         ${POHeaderBean.supplierCity},${POHeaderBean.supplierStateAbbrev}&nbsp;${POHeaderBean.supplierZip}<br/>
         <fmt:message key="label.phoneno"/>:&nbsp;${POHeaderBean.supplierPhone}<br/>
         <fmt:message key="label.email"/>:&nbsp;${POHeaderBean.supplierEmail}
         <img src="/images/minwidth.gif" width="100" height="0">
       </td>

       <td rowspan="2" class="optionTitleBoldRight" valign="top"><fmt:message key="label.shipto"/>:</td>
       <td rowspan="2" class="optionTitleLeft" valign="top" width="25%">
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
         ${POHeaderBean.shiptoCity},${POHeaderBean.shiptoStateAbbrev}&nbsp;${POHeaderBean.shiptoZip}<br/>
         <img src="/images/minwidth.gif" width="100" height="0">
       </td>

       <td class="optionTitleBoldRight" valign="top"><fmt:message key="label.po"/>:</td>
       <td class="optionTitleLeft" valign="top"  width="10%">${POHeaderBean.radianPo}</td>
     </tr>
    
     <tr>
       <td class="optionTitleBoldRight" valign="top"><%--<fmt:message key="label.bo"/>:--%></td>
       <td class="optionTitleLeft" valign="top" width="10%">&nbsp;<%--${POHeaderBean.bo}--%></td>
     </tr>
<%--
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
          <c:if test="${! empty POHeaderBean.carrierHub}">
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
        <td class="optionTitleBoldRight" valign="top"><fmt:message key="label.paymentterms"/>:<br/><img src="/images/minwidth.gif" width="90" height="0"></td>
        <td class="optionTitleLeft" valign="top" width="25%">${POHeaderBean.paymentTerms}</td>

        <td class="optionTitleBoldRight" valign="top"><fmt:message key="label.hub"/>:</td>
        <td class="optionTitleLeft" valign="top" width="25%">${POHeaderBean.hubName}</td>

        <td class="optionTitleBoldRight" valign="top"><fmt:message key="label.customerpo"/>:<br/><img src="/images/minwidth.gif" width="80" height="0"></td>
        <td class="optionTitleLeft" valign="top" width="10%">&nbsp;${POHeaderBean.customerPo}</td>
      </tr>

      <tr>
        <td class="optionTitleBoldRight"valign="top"><fmt:message key="label.tradeTerms"/>:<br/><img src="/images/minwidth.gif" width="80" height="0"></td>
        <td class="optionTitleLeft" valign="top" width="25%">${POHeaderBean.freightOnBoard}</td>

        <td class="optionTitleBoldRight"valign="top"><fmt:message key="label.inventorygroup"/>:<br/><img src="/images/minwidth.gif" width="90" height="0"></td>
        <td class="optionTitleLeft" valign="top" width="25%">${POHeaderBean.inventoryGroup}</td>

       <td class="optionTitleBoldRight" valign="top">--%><%--<fmt:message key="label.consignedpo"/>:<br/><img src="/images/minwidth.gif" width="90" height="0">--%><%--</td>
       <td class="optionTitleLeft" valign="top" width="10%">&nbsp;--%><%--${POHeaderBean.consignedPo}--%><%--</td>
     </tr>

     <tr>
         <td class="optionTitleBoldRight" valign="top"><fmt:message key="label.totalamount"/>:<br/><img src="/images/minwidth.gif" width="80" height="0"></td>
         <td class="optionTitleLeft" valign="top" width="25%">&nbsp;${POHeaderBean.poPrice}</td>

         <td class="optionTitleBoldRight" valign="top"><fmt:message key="label.currency"/>:</td>
         <td width="25%">&nbsp;</td>

         <td class="optionTitleBoldRight" valign="top">--%><%--<fmt:message key="label.critical"/>:--%><%--</td>
         <td class="optionTitleLeft" valign="top" width="10%">&nbsp;--%><%--${POHeaderBean.critical}--%><%--</td>
      </tr>

     <tr>
       <td class="optionTitleBoldRight" valign="top">
          <fmt:message key="label.date.created"/>:<br/><img src="/images/minwidth.gif" width="80" height="0">
       </td>
       <td class="optionTitleLeft" valign="top" width="25%" colspan="5">
         <fmt:formatDate var="fmtDateCreated" value="${POHeaderBean.dateSent}" pattern="MM/dd/yyyy"/>
         ${fmtDateCreated}
       </td>

       <!--<td>&nbsp;</td>
       <td width="25%">&nbsp;</td>

       <td>&nbsp;</td>
       <td width="10%">&nbsp;</td>-->
    </tr>   --%>
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

<c:set var="poLineCollection"  value='${POHeaderBean.poLineCollection}'/>
<script language="JavaScript" type="text/javascript">
<!--
var validUnitPrice = new Array(
<c:forEach var="poLine" items="${poLineCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"${poLine.unitPrice}"
   </c:when>
   <c:otherwise>
    "${poLine.unitPrice}"
   </c:otherwise>
  </c:choose>
</c:forEach>
);
// -->
</script>

<c:set var="javaScriptUpdatePermission" value=''/>
<c:set var="javaScriptConfirmPermission" value=''/>

<c:if test="${poLineCollection != null}" >
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
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
      <div id="mainUpdateLinks" style=""> <%-- mainUpdateLinks Begins --%>
      <c:if test="${!empty poLineCollection}" >
       <span id="poConfirmLink" style="">
        <a href="#" onclick="confirmPO(); return false;"><fmt:message key="label.confirm"/></a>
       </span>
       <span id="poUpdateLink" style="display:none">
        <a href="#" onclick="updatePO(); return false;"><fmt:message key="label.update"/>&nbsp;<fmt:message key="label.expeditenotes"/></a>
       </span>
       <span id="addLineLink" style="">
        | <a href="#" onclick="addMaterialLine(); return false;"><fmt:message key="label.addline"/></a>
       </span>
      </c:if>
      &nbsp;
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>      
    <div class="dataContent">
    <table name="materialTable" id="materialTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>
    <c:set var="totalLineQty" value='${0}'/>
    <c:set var="totalQtyReceived" value='${0}'/>

    <c:forEach var="line" items="${poLineCollection}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
      <th width="2%"><fmt:message key="label.line"/></th>
      <th width="2%"><fmt:message key="label.haas"/> <fmt:message key="label.item"/></th>
      <th width="5%"><fmt:message key="label.partnumber"/></th>
      <th width="5%"><fmt:message key="label.itemdescription"/></th>
      <%--<th width="2%"><fmt:message key="label.rem.shelf.life"/></th>--%>
      <th width="5%"><fmt:message key="label.needed"/></th>
      <th width="3%"><fmt:message key="label.qty"/></th>
      <%--<th width="10%"><fmt:message key="label.packaging"/></th>--%>
      <th width="5%"><fmt:message key="label.unitprice"/></th>
      <th width="5%"><fmt:message key="label.line.total.price"/></th>
      <th width="8%"><fmt:message key="label.projectedshipdate"/></th>
      <th width="8%"><fmt:message key="label.projecteddeliverydate"/></th>
      <th width="2%"><fmt:message key="label.expeditenotes"/></th>
      <th width="2%"><fmt:message key="label.status"/></th>
      <th width="2%"><fmt:message key="label.shipfrom"/></th>
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

    <c:set var="totalLineQty" value='${totalLineQty+line.quantity}'/>
    <c:set var="totalQtyReceived" value='${totalQtyReceived+line.quantityReceived}'/>

    <c:set var="updatePermission" value=''/>
    <tcmis:supplierLocationPermission indicator="true" userGroupId="usGovShipConfirm" locationId="${line.shipFromLocationId}" supplierId="${line.supplier}">  
    <c:if test="${line.quantity >= line.quantityReceived }" >
      <c:set var="updatePermission" value='Yes'/>
      <c:set var="javaScriptUpdatePermission" value='Yes'/>
      <c:set var="javaScriptConfirmPermission" value='Yes'/>
    </c:if>
    </tcmis:supplierLocationPermission>

    <tcmis:supplierLocationPermission indicator="true" userGroupId="usGovShipmentSelection" locationId="${line.shipFromLocationId}" supplierId="${line.supplier}">
    <c:if test="${line.quantity >= line.quantityReceived }" >
      <c:set var="updatePermission" value='Yes'/>
      <c:set var="javaScriptUpdatePermission" value='Yes'/>       
    </c:if>     
    </tcmis:supplierLocationPermission>

    <tcmis:inventoryGroupPermission indicator="true" userGroupId="BuyOrder" inventoryGroup="${POHeaderBean.inventoryGroup}">
     <c:set var="updatePermission" value='Yes'/>
     <c:set var="javaScriptUpdatePermission" value='Yes'/>     
    </tcmis:inventoryGroupPermission>
    
    <fmt:formatDate var="fmtNeedDate" value="${line.needDate}" pattern="${dateFormatPattern}"/>
    <fmt:formatDate var="fmtShipDate" value='${line.vendorShipDate}' pattern="${dateFormatPattern}"/>
    <fmt:formatDate var="fmtDockDate" value='${line.promisedDate}' pattern="${dateFormatPattern}"/>
    <fmt:formatDate var="formattedExpediteDate" value='${line.expediteDate}' pattern="${dateFormatPattern}"/>
    
    <c:choose>
      <c:when test="${!empty line.expediteDate}">
         <c:set var="fmtShipDate" value='${formattedExpediteDate}'/>
      </c:when>
    </c:choose>

    <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
      <td width="2%">
        <span name="label_poLine${dataCount}" id="label_poLine${dataCount}">
        ${line.poLine}
        </span>

         <input type='hidden' name="poLineDetailViewBean[${dataCount}].radianPo" id='radianPo${dataCount}' value='${line.radianPo}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].poLine" id='poLine${dataCount}' value='${line.poLine}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].amendment" id='amendment${dataCount}' value='${line.amendment}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].itemId" id='itemId${dataCount}' value='${line.itemId}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].needDate" id='needDate${dataCount}' value='${fmtNeedDate}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].mfgPartNo" id='mfgPartNo${dataCount}' value='${line.mfgPartNo}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].supplierPartNo" id='poLine${supplierPartNo}' value='${line.supplierPartNo}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].dpasRating" id='dpasRating${dataCount}' value='${line.dpasRating}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].poLineNote" id='poLineNote${dataCount}' value='${line.poLineNote}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].oldExpediteComments" id='oldExpediteComments${dataCount}' value='${line.expediteComments}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].oldVvendorShipDate" id='oldVendorShipDate${dataCount}' value='${fmtShipDate}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].oldPromisedDate" id='oldPromisedDate${dataCount}' value='${fmtDockDate}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].oldQuantity" id='oldQuantity${dataCount}' value='${line.quantity}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].oldUnitPrice" id='oldUnitPrice${dataCount}' value='${line.unitPrice}'>                
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].ammendmentcomments" id='ammendmentcomments${dataCount}' value='${line.ammendmentcomments}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].remainingShelfLifePercent" id='remainingShelfLifePercent${dataCount}' value='${line.remainingShelfLifePercent}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].currencyId" id='currencyId${dataCount}' value='${line.currencyId}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].supplier" id='supplier${dataCount}' value='${line.supplier}'>
         <%--<input type='hidden' name="poLineDetailViewBean[${dataCount}].shipFromLocationId" id='shipFromLocationId${dataCount}' value='${line.shipFromLocationId}'>--%>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].supplierSalesOrderNo" id='supplierSalesOrderNo${dataCount}' value='${line.supplierSalesOrderNo}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].quantityReceived" id='quantityReceived${dataCount}' value='${line.quantityReceived}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].quantityVouchered" id='quantityVouchered${dataCount}' value='${line.quantityVouchered}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].quantityReturned" id='quantityReturned${dataCount}' value='${line.quantityReturned}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].poLineStatus" id='poLineStatus${dataCount}' value='${line.poLineStatus}'>                  
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].lineChangeStatus" id='lineChangeStatus${dataCount}' value=''>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].expediteNoteChangeStatus" id='expediteNoteChangeStatus${dataCount}' value='${line.expediteNoteChangeStatus}'>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].deliveryComments" id='deliveryComments${dataCount}' value='${line.deliveryComments}'>        
      </td>
      <td width="2%">${line.itemId}</td>
      <td width="5%">${line.supplierPartNo}</td>
      <td width="5%">${line.itemDesc}</td>
      <%--<td width="2%">${line.remainingShelfLifePercent}</td>--%>
      <td width="3%" nowrap>
        ${fmtNeedDate}
      </td>

     <c:choose>
     <c:when test="${updatePermission == 'Yes' && (dbuyLockStatus != 'Yes' || everConfirmed =='Yes') }" >
      <td width="5%"><input type='text' class="inputBox" size='4' name='poLineDetailViewBean[${dataCount}].quantity' id='quantity${dataCount}' onChange='checkQuantity(${dataCount})' value='${line.quantity}'></td>
      <%--<td width="10%">${line.packaging}</td>--%>
      <td width="5%">
        <select name='poLineDetailViewBean[${dataCount}].unitPrice' id="unitPrice${dataCount}" onChange='setLineChanged(${dataCount})' class="selectBox">
          <option value="${line.unitPrice}" selected>${line.unitPrice}</option>
        </select>
       ${line.currencyId}
      </td>
      <td width="5%"><span id="extendedPrice${dataCount}">${line.extendedPrice} ${line.currencyId}</span></td>
      <td width="8%">
        <input type="text" class="inputBox pointer" readonly name="poLineDetailViewBean[${status.index}].vendorShipDate" id="vendorShipDate${dataCount}" onChange='setExpediteNoteChanged(${dataCount},"Yes")' value="${fmtShipDate}" onClick="return getCalendar(document.getElementById('vendorShipDate${dataCount}'),null,document.getElementById('promisedDate${dataCount}'));" size="10">
      </td>

      <td width="8%">       
        <input type="text" class="inputBox pointer" readonly name="poLineDetailViewBean[${status.index}].promisedDate" id="promisedDate${dataCount}" onChange='setExpediteNoteChanged(${dataCount},"Yes")' value="${fmtDockDate}" onClick="return getCalendar(document.getElementById('promisedDate${dataCount}'),document.getElementById('vendorShipDate${dataCount}'));" size="10">
      </td>

      <td width="2%" id="poLineNote${dataCount}">
       <c:if test="${currentSupplier == '10139081' || line.supplier == '10022037' ||  line.supplier == '10022038' || line.supplier == '10033480' || currentSupplier == '10086683' || currentSupplier == '10127524'}" >
       <select name='backOrderCategory${dataCount}' id="backOrderCategory${dataCount}" onChange='setExpediteNoteCatChanged(${dataCount})' class="selectBox">
         <option value=""><fmt:message key="label.pleaseselect"/></option>        
         <option value="Commercial Product Backorder">Commercial Product Backorder</option>
         <option value="GFP Not Available">GFP Not Available</option>
         <option value="Origin Inspection">Origin Inspection</option>
         <option value="Seavan">Seavan</option>
         <option value="Misc.">Misc.</option>         
       </select>
       </c:if>
       <textarea name="poLineDetailViewBean[${status.index}].expediteComments" id="expediteComments${dataCount}" cols="30" rows="7" onChange='setExpediteNoteChanged(${dataCount},"")' class="inputBox"><c:out value="${line.expediteComments}"/></textarea>
      </td>

      <%--<c:choose>
       <c:when test="${! empty line.poLineNote}" >
        <td width="2%" id="poLineNote${dataCount}" onclick='showNotes("poLineNote<c:out value="${status.index}"/>");' onmouseover='style.cursor="hand"'>
         <span id='spanpoLineNote<c:out value="${status.index}"/>'>
         <p id='pgphpoLineNote<c:out value="${status.index}"/>'>+&nbsp;&nbsp;</p>
         <div id='divpoLineNote<c:out value="${status.index}"/>' style='display:none' onmouseover='style.cursor="hand"'>
           <c:out value="${line.poLineNote}"/>
         </div>
         </span>
       </td>
       </c:when>
       <c:otherwise>
       <td width="2%" id="poLineNote${dataCount}">&nbsp;</td>
       </c:otherwise>
      </c:choose>  --%>

       <td width="2%">
        <span name="poLineStatusSpan${dataCount}" id="poLineStatusSpan${dataCount}">
         ${line.poLineStatus}
        </span>
       </td>

       <td width="2%">
       <c:set var="selectedSupplier" value='${line.supplier}'/>
       <c:forEach var="SupplierLocationOvBean" items="${supplierLocationOvBeanCollection}" varStatus="suppstatus">
        <c:set var="currentSupplier" value='${suppstatus.current.supplier}'/>
          <c:choose>
            <c:when test="${empty selectedSupplier}" >
              <c:set var="selectedSupplier" value='${currentSupplier}'/>
              <c:set var="initSuppLocBeanCollection" value='${suppstatus.current.supplierLocations}'/>
            </c:when>
            <c:when test="${currentSupplier == selectedSupplier}" >
              <c:set var="initSuppLocBeanCollection" value='${suppstatus.current.supplierLocations}'/>
            </c:when>
          </c:choose>
         </c:forEach>

         <c:set var="suppLocationCount" value='${0}'/>
         <select name="poLineDetailViewBean[${status.index}].shipFromLocationId" id="shipFromLocationId${dataCount}" onChange='setLineChanged(${dataCount})' class="selectBox">
           <c:choose>
            <c:when test="${empty line.shipFromLocationId}" >
              <option value=""><fmt:message key="label.pleaseselect"/></option>
            </c:when>
          </c:choose>
           <c:forEach var="LocationObjBean" items="${initSuppLocBeanCollection}" varStatus="supplocstatus">
            <c:set var="currentLocation" value='${supplocstatus.current.locationId}'/>
             <c:set var="selectedFlag" value=''/>
             <c:set var="selectedLocation" value='${line.shipFromLocationId}'/>
             <c:if test="${currentLocation == selectedLocation}">
              <c:set var="selectedFlag" value='selected'/>
              <c:set var="suppLocationCount" value='${suppLocationCount+1}'/>
             </c:if>
             <option value="<c:out value="${currentLocation}"/>" <c:out value="${selectedFlag}"/>><c:out value="${supplocstatus.current.locationShortName}" escapeXml="false"/></option>
            </c:forEach>
           <c:choose>
            <c:when test="${suppLocationCount == 0 && !empty line.shipFromLocationId}" >
              <option value="<c:out value="${line.shipFromLocationId}"/>" selected><c:out value="${line.shipFromLocationId}" escapeXml="false"/></option>
            </c:when>
           </c:choose>
          </select>
         </td>
     </c:when>
     <c:otherwise>
      <td width="5%">${line.quantity}
        <input type='hidden' class="inputBox" name='poLineDetailViewBean[${dataCount}].quantity' id='quantity${dataCount}' value='${line.quantity}'>
      </td>
      <%--<td width="10%">${line.packaging}</td>--%>
      <td width="5%">
        ${line.unitPrice}&nbsp;${line.currencyId}
      </td>
      <td width="5%"><span id="extendedPrice${dataCount}">${line.extendedPrice} ${line.currencyId}</span></td>
      <td width="8%">
        ${fmtShipDate}
        <input type="hidden" class="inputBox" name="poLineDetailViewBean[${status.index}].vendorShipDate" id="vendorShipDate${dataCount}" value='${fmtShipDate}'>
      </td>
      
      <td width="8%">        
        ${fmtDockDate}
        <input type="hidden" class="inputBox" name="poLineDetailViewBean[${status.index}].promisedDate" id="promisedDate${dataCount}" value='${fmtDockDate}'>
		  </td>

     <td width="2%" id="poLineNote${dataCount}">
      <c:choose>
        <c:when test="${everConfirmed =='Yes' }" >
         <c:if test="${currentSupplier == '10139081' || line.supplier == '10022037' ||  line.supplier == '10022038' || currentSupplier == '10033480' || currentSupplier == '10086683' || currentSupplier == '10127524'}" >
         <select name='backOrderCategory${dataCount}' id="backOrderCategory${dataCount}" onChange='setExpediteNoteCatChanged(${dataCount})' class="selectBox">
          <option value=""><fmt:message key="label.pleaseselect"/></option>
          <option value="Backorder (VMI/GFP)">Backorder (VMI/GFP)</option>
          <option value="Backorder (Materials Unavailable)">Backorder (Materials Unavailable)</option>
          <option value="Sea Van">Sea Van</option>
          <option value="Origin Inspection">Origin Inspection</option>
          <option value="Misc.">Misc.</option>         
         </select>
         </c:if>
         <textarea name="poLineDetailViewBean[${status.index}].expediteComments" id="expediteComments${dataCount}" cols="30" rows="7" onChange='setExpediteNoteChanged(${dataCount},"")' class="inputBox"><c:out value="${line.expediteComments}"/></textarea>
        </c:when>
        <c:otherwise>
         <c:out value="${line.expediteComments}"/>
        </c:otherwise>
       </c:choose>
      </td>

      <td width="2%">
        <span name="poLineStatusSpan${dataCount}" id="poLineStatusSpan${dataCount}">
         ${line.poLineStatus}
        </span>
      </td>

      <td width="2%">
       <c:set var="selectedSupplier" value='${line.supplier}'/>
       <c:forEach var="SupplierLocationOvBean" items="${supplierLocationOvBeanCollection}" varStatus="status">
        <c:set var="currentSupplier" value='${status.current.supplier}'/>
          <c:choose>
            <c:when test="${empty selectedSupplier}" >
              <c:set var="selectedSupplier" value='${currentSupplier}'/>
              <c:set var="initSuppLocBeanCollection" value='${status.current.supplierLocations}'/>
            </c:when>
            <c:when test="${currentSupplier == selectedSupplier}" >
              <c:set var="initSuppLocBeanCollection" value='${status.current.supplierLocations}'/>
            </c:when>
          </c:choose>
         </c:forEach>
        <c:set var="suppLocationCount" value='${0}'/>
        <c:forEach var="LocationObjBean" items="${initSuppLocBeanCollection}" varStatus="status">
          <c:set var="currentLocation" value='${status.current.locationId}'/>
              <c:set var="selectedFlag" value=''/>
              <c:forEach items="${suppLocationIdArray}" varStatus="status1">
                <c:set var="selectedLocation" value='${line.shipFromLocationId}'/>
                <c:if test="${currentLocation == selectedLocation}">
                  ${status.current.locationShortName}
                  <c:set var="suppLocationCount" value='${suppLocationCount+1}'/>
                </c:if>
              </c:forEach>
         </c:forEach>
         <c:choose>
          <c:when test="${suppLocationCount == 0 && !empty line.shipFromLocationId}" >
           ${line.shipFromLocationId}
          </c:when>
         </c:choose>
         <input type='hidden' name="poLineDetailViewBean[${dataCount}].shipFromLocationId" id='shipFromLocationId${dataCount}' value='${line.shipFromLocationId}'>
        </td>
     </c:otherwise>
     </c:choose>

   </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty poLineCollection}" >

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
  <input type="hidden" name="totalLines" id="totalLines" value="${dataCount}" />
  <input type="hidden" name="totalLineQty" id="totalLineQty" value="${totalLineQty}" />
  <input type="hidden" name="totalQtyReceived" id="totalQtyReceived" value="${totalQtyReceived}" />
  <input type="hidden" name="dbuyLockStatus" id="dbuyLockStatus" value="${dbuyLockStatus}" />   
  <input type="hidden" name="javaScriptConfirmPermission" id="javaScriptConfirmPermission" value="${javaScriptConfirmPermission}" />
  <input type="hidden" name="javaScriptUpdatePermission" id="javaScriptUpdatePermission" value="${javaScriptUpdatePermission}" />
  <input type="hidden" name="everConfirmed" id="everConfirmed" value="${everConfirmed}" />

  <script type="text/javascript">
  <!--
   showUpdateLinks();
  //-->
  </script>
  <input type="hidden" name="action" id="action" value=""/>
  <input type="hidden" name="radianPo" id="radianPo" value="${param.radianPo}"/>
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