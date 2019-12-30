<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/resultiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

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
<script type="text/javascript" src="/js/common/ordertracking/dlagasordertracking.js"></script>

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

<c:set var="module">
  <tcmis:module/>
</c:set>
<c:set var="openOrdersOnly" value='${param.openOrdersOnly}'/>
<c:set var="updatePermission" value='N'/>

<tcmis:facilityPermission indicator="true" userGroupId="changeMrShipTo" >
 <c:set var="updatePermission" value='Y'/>
</tcmis:facilityPermission>

<script language="JavaScript" type="text/javascript">
<!--

with(milonic=new menuname("printMenu")){
 top="offset=2";
 style = contextStyle;
 margin=3;

 // aI("text=<fmt:message key="label.printpalletmsl"/>;url=javascript:printPalletMSL();");
 //aI("text=<fmt:message key="label.printorderpackinglist"/>;url=javascript:printOrderPackingList();");
 aI("text=<fmt:message key="label.viewpurchaseorder"/>;url=javascript:updatePO();");
 aI("text=<fmt:message key="label.markforaddress"/>;url=javascript:getShipToAddress();");
 aI("text=<fmt:message key="changedlashipto.title"/>;url=javascript:changeDlaShipTo();");
 //aI("text=<fmt:message key="label.viewtrackinginformation"/>;url=javascript:showTrackingInformation();");
 <c:if test="${module=='haas'}">
 aI("text=ELLIS Page;url=javascript:showEllisDetail();");
 </c:if>
 <c:if test="${module=='supplier'}">  
	 aI("text=<fmt:message key="label.showpalletdetail"/>;url=javascript:showDetail();");
 </c:if>
 aI("text=<fmt:message key="label.addviewpod"/>;url=javascript:addViewPOD();");
}

with(milonic=new menuname("canceledOrderMenu")){
 top="offset=2";
 style = contextStyle;
 margin=3;
 <c:if test="${module=='haas'}">
 aI("text=ELLIS Page;url=javascript:showEllisDetail();");
 </c:if>
 aI("text=Print;url=javascript:window.print();image=");
}

/*Permission to cancle orders for Nawaz, Sujata and Jason.
* */
with(milonic=new menuname("cancelOrderMenu")){
 top="offset=2";
 style = contextStyle;
 margin=3;
 <c:if test="${module=='haas' && empty openOrdersOnly && updatePermission == 'Y'}">
 aI("text=<fmt:message key="label.changesupplier"/>;url=javascript:changeSupplier();");
 aI("text=<fmt:message key="label.cancelorder"/>;url=javascript:cancelOrder();");
 aI("text=<fmt:message key="changedlashipto.title"/>;url=javascript:changeDlaShipTo();");
 </c:if>  
 aI("text=<fmt:message key="label.viewpurchaseorder"/>;url=javascript:updatePO();");
 aI("text=<fmt:message key="label.markforaddress"/>;url=javascript:getShipToAddress();");
 <c:if test="${module=='haas'}">
 aI("text=ELLIS Page;url=javascript:showEllisDetail();");
 </c:if>
  //aI("text=<fmt:message key="label.viewtrackinginformation"/>;url=javascript:showTrackingInformation();"); 
}

drawMenus();

var map = null;

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",po:"<fmt:message key="label.po"/>",
shipped:"<fmt:message key="label.shipped"/>",percent:"<fmt:message key="label.percent"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};

//set this if you want to result table with FIXED width
//parent.resultWidthResize=false;
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myResultOnload()">

<c:set var="module">
 <tcmis:module/>
</c:set>

<tcmis:form action="/dlagasordertrackingresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  <html:errors/>
	<logic:present name="errorBean" scope="request">
		<bean:write name="errorBean" property="cause"/>
	</logic:present>
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null and empty requestScope['errorBean']}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<c:if test="${DLAOrderViewBeanCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:set var="preRadianPo" value=''/>
<c:set var="partDataCount" value='${0}'/>
<c:set var="mrLineCount" value='${0}'/>
<c:set var="shippedCount" value='${0}'/>
<c:set var="validPoCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty DLAOrderViewBeanCollection}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:forEach var="DLAOrderViewBean" items="${DLAOrderViewBeanCollection}" varStatus="status">

    <c:set var="currentPer" value='${status.current.radianPo}${status.current.poNumber}'/>
    <c:choose>
     <c:when test="${!(currentPer eq preRadianPo)}">
      <c:set var="partDataCount" value='${0}'/>
      <c:set var="mrLineCount" value='${mrLineCount+1}'/>
      <c:if test="${empty status.current.cancelStatus}">
        <c:set var="validPoCount" value='${validPoCount+1}'/>
      </c:if>
      <c:if test="${!empty status.current.openQuantity && status.current.openQuantity == 0 && !empty status.current.shipmentId}">
        <c:set var="shippedCount" value='${shippedCount+1}'/>
      </c:if>      
 <script type="text/javascript">
 <!--
//  map will change per 'PO'
	map = new Array();
 //-->
 </script>
     </c:when>
     <c:otherwise>
      <c:set var="partDataCount" value='${partDataCount+1}'/>
     </c:otherwise>
    </c:choose>

    <%--<c:if test="${partDataCount % 5 == 0 && mrLineCount ==0}">--%>
    <c:if test="${(dataCount % 10 == 0 && partDataCount==0) || dataCount ==0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
    <th width="5%"><fmt:message key="label.haaspo"/></th>
    <c:if test='${module == "haas"}' >
     <th width="5%"><fmt:message key="label.mrnumber"/></th>
     <th width="5%"><fmt:message key="label.dlapriority"/></th>
    </c:if>
    <th width="5%"><fmt:message key="label.supplierpriority"/></th>
    <th width="5%"><fmt:message key="label.order.type"/></th> 
    <%--Show these for Haas version--%>
    <th width="2%"><fmt:message key="label.usgovorderdate"/></th>
    <c:if test='${module == "haas"}' >
      <th width="2%"><fmt:message key="label.date.created"/></th>
    </c:if> 
    <th width="2%"><fmt:message key="label.datesent"/></th>
    <%--<th width="2%"><fmt:message key="dbuystatus.dateacknowledged"/>(997)</th>--%>
    <%--<th width="2%"><fmt:message key="label.dateconfirmed"/></th>--%>
    <th width="2%"><fmt:message key="label.dlametricdate"/></th>
    <th width="5%"><fmt:message key="label.orderqty"/></th>
    <th width="10%"><fmt:message key="label.status"/></th>
    <th width="10%"><fmt:message key="label.shippingsupplierlocation"/></th>
    <th width="10%"><fmt:message key="label.originalsupplierlocation"/></th>
    <c:set var="canSearchHubs" value='false'/>
    <c:choose>
   	<c:when test="${sessionScope.personnelBean.companyId == 'Radian'}">
   	 	<c:set var="canSearchHubs" value='true'/>
   	</c:when>
   	<c:otherwise>
   		<tcmis:supplierPermission indicator="true" userGroupId="SupplierAndHubOrders" supplier="" companyId="">
   			 	<c:set var="canSearchHubs" value='true'/>
 			</tcmis:supplierPermission>
   	</c:otherwise>        
   </c:choose>
   <c:if test="${canSearchHubs == 'true'}">  
    <th width="10%"><fmt:message key="label.hub"/></th>
    <th width="10%"><fmt:message key="label.inventorygroup"/></th>
    </c:if>
    <th width="5%"><fmt:message key="label.estimatedshipdate"/></th>
    <th width="5%"><fmt:message key="label.actualshipdate"/></th>
    <th width="2%"><fmt:message key="label.orderage"/></th>
    <th width="5%"><fmt:message key="label.quantityopen"/></th>
    <th width="5%"><fmt:message key="label.qtyshipped"/></th>
    <th width="5%"><fmt:message key="label.shipto"/></th>
    <th width="5%"><fmt:message key="label.customerpo"/></th>
    <th width="5%"><fmt:message key="label.milstrip"/></th>
    <th width="5%"><fmt:message key="label.shiptolocationid"/></th>
     <th width="5%"><fmt:message key="label.shiptododaac"/></th>
    <th width="5%"><fmt:message key="label.ultimatedodaac"/></th>
    <th width="15%"><fmt:message key="label.nsn"/> <br/> <fmt:message key="label.part"/> <fmt:message key="label.shortname"/></th>
    <%--<th width="5%"><fmt:message key="label.ordernumber"/></th>--%>

    <%--<th width="5%"><fmt:message key="label.milstrip"/></th>--%>

    <th width="5%"><fmt:message key="label.ordercomments"/></th>      

    <%--<th width="5%"><fmt:message key="label.tcn"/></th>--%>
    <th width="5%"><fmt:message key="label.carrier"/></th>
    <th width="5%"><fmt:message key="label.carrier"/> <fmt:message key="label.trackingno"/></th>
    <th width="5%"><fmt:message key="label.shippedby"/></th>
    <th width="10%"><fmt:message key="label.deliverycoments"/></th>
    <th width="5%"><fmt:message key="label.origininspectionrequired"/></th>
    <th width="10%"><fmt:message key="label.datesenttowawf"/></th>
    </tr>
    </c:if>

<%--    <c:if test="${dataCount ==0}">
    <fmt:formatDate var="reportDate" value="${status.current.reportDate}" pattern="EEE MMM dd yyyy HH:mm a zzz" />
    <script type="text/javascript">
    <!--
     setReportTimeMessage('<fmt:message key="dlagasordertracking.reporttime"/> ${reportDate}');
    //-->
    </script>
    </c:if>--%>


  <c:set var="supplierLocPermission" value=''/>
  <tcmis:supplierLocationPermission indicator="true" userGroupId="usGovShipConfirm" supplierId="${status.current.supplier}" locationId="${status.current.shipFromLocationId}">
   <c:set var="supplierLocPermission" value='Yes'/>
  </tcmis:supplierLocationPermission>

  <tcmis:supplierLocationPermission indicator="true" userGroupId="usGovShipmentSelection" supplierId="${status.current.supplier}" locationId="${status.current.shipFromLocationId}">
   <c:set var="supplierLocPermission" value='Yes'/>
  </tcmis:supplierLocationPermission>

  <tcmis:inventoryGroupPermission indicator="true" userGroupId="BuyOrder" inventoryGroup="${status.current.inventoryGroup}">
   <c:set var="supplierLocPermission" value='Yes'/>
  </tcmis:inventoryGroupPermission>

    <c:choose>
     <c:when test="${mrLineCount % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>

    <c:if test="${(!empty status.current.openQuantity && status.current.openQuantity > 0) || empty status.current.shipmentId }" >
    <c:choose>
     <c:when test="${!empty status.current.orderAge && status.current.orderAge > 0}" >
      <c:set var="colorClass" value='red'/>
     </c:when>
     <c:when test="${!empty status.current.cancelStatus && status.current.cancelStatus== 'canceled'}" >
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='yellow'/>
     </c:otherwise>
    </c:choose>          
    </c:if>

   <script type="text/javascript">
   <!--
    lineMap[${status.index}] = map;
      map[map.length] = ${status.index};
   //-->
   </script>

    <c:choose>
     <c:when test="${!empty status.current.cancelStatus && status.current.cancelStatus== 'canceled'}">
       <tr class="${colorClass}" id="rowId${status.index}" onmouseup="selectRow('${status.index}','canceledOrderMenu');">
     </c:when>
     <c:when test="${empty status.current.shipmentId}">
       <tr class="${colorClass}" id="rowId${status.index}" onmouseup="selectRow('${status.index}','cancelOrderMenu');">
     </c:when>
     <c:otherwise>
       <tr class="${colorClass}" id="rowId${status.index}"onmouseup="selectRow('${status.index}','printMenu');">
     </c:otherwise>
    </c:choose>
    
	<input name="radianPO_${status.index}" id="radianPO_${status.index}" value="${status.current.radianPo}" type="hidden"/>   
	<input name="supplier_${status.index}" id="supplier_${status.index}" value="${status.current.supplier}" type="hidden"/>   
  <input name="docNumWithSuffix_${status.index}" id="docNumWithSuffix_${status.index}" value="${status.current.transactionRefNum}" type="hidden"/>
	<input name="prNumber_${status.index}" id="prNumber_${status.index}" value="${status.current.prNumber}" type="hidden"/>
	<input name="lineItem_${status.index}" id="lineItem_${status.index}" value="${status.current.lineItem}" type="hidden"/>
	<input name="markForLocationId_${status.index}" id="markForLocationId_${status.index}" value="${status.current.markForLocationId}" type="hidden"/>
	<input name="shipToDodaac_${status.index}" id="shipToDodaac_${status.index}" value="${status.current.shipToDodaac}" type="hidden"/>
  <input name="rowClass${status.index}" id="rowClass${status.index}" value="${colorClass}" type="hidden"/> 
  <input name="shipFrom_${status.index}" id="shipFrom_${status.index}" value="${status.current.shipFromLocationId}" type="hidden"/>
  <input name="carrierName_${status.index}" id="carrierName_${status.index}" value="${status.current.carrierName}" type="hidden"/>
  <input name="trackingNumber_${status.index}" id="trackingNumber_${status.index}" value="${status.current.trackingNumber}" type="hidden"/>
  <input name="customerHaasContractId_${status.index}" id="customerHaasContractId_${status.index}" value="${status.current.customerHaasContractId}" type="hidden"/>
  <input name="releaseNum_${status.index}" id="releaseNum_${status.index}" value="${status.current.releaseNum}" type="hidden"/>
  <input name="milstripCode_${status.index}" id="milstripCode_${status.index}" value="${status.current.milstripCode}" type="hidden"/>
  <input name="poNumber_${status.index}" id="poNumber_${status.index}" value="${status.current.poNumber}" type="hidden"/>

  <c:if test="${ currentPer != preRadianPo }">
     <td width="5%" rowspan="${rowCountPart[currentPer]}">${status.current.radianPo} </td>
     <c:if test='${module == "haas"}' >
       <td width="5%" rowspan="${rowCountPart[currentPer]}">${status.current.prNumber}-${status.current.lineItem}</td>
       <td width="5%" rowspan="${rowCountPart[currentPer]}">${status.current.ipg}</td>
     </c:if>
     <td width="5%" rowspan="${rowCountPart[currentPer]}">${status.current.airgasIpg}</td>
     
   <td width="5%" rowspan="${rowCountPart[currentPer]}">
       <c:choose>
         <c:when test="${status.current.originalTransactionType == '850'}">
           <fmt:message key="label.commercial"/>&nbsp;
         </c:when>
         <c:when test="${status.current.originalTransactionType == '940'}">
           <fmt:message key="label.vmi"/>&nbsp;
         </c:when>
         <c:otherwise>
           &nbsp;
         </c:otherwise>
      </c:choose>
     </td>

     <fmt:formatDate var="formattedOrderDate" value="${status.current.orderDate}" pattern="${dateTimeFormatPattern}"/>
     <td width="5%" rowspan="${rowCountPart[currentPer]}">${formattedOrderDate}</td>
     <c:if test='${module == "haas"}' >
       <fmt:formatDate var="formattedDateCreated" value="${status.current.dateCreated}" pattern="${dateTimeFormatPattern}"/>
       <td width="5%" rowspan="${rowCountPart[currentPer]}">${formattedDateCreated}</td>
     </c:if>
     <fmt:formatDate var="formatteddateSentToAirgas" value="${status.current.dateSent}" pattern="${dateTimeFormatPattern}"/>
     <td width="5%" rowspan="${rowCountPart[currentPer]}">${formatteddateSentToAirgas}</td>
<%--     <fmt:formatDate var="formattedAirgas997Received" value="${status.current.airgas997Received}" pattern="MM/dd/yyyy HH:mm a"/>
     <td width="5%" rowspan="${rowCountPart[currentPer]}">${formattedAirgas997Received}</td>--%javascript:showDetail()>
<%--     <fmt:formatDate var="formattedPoConfirmDate" value="${status.current.dateFirstConfirmed}" pattern="MM/dd/yyyy"/>
     <td width="5%" nowrap rowspan="${rowCountPart[currentPer]}">${formattedPoConfirmDate}</td>--%>
     <fmt:formatDate var="formattedDesiredShipDate" value="${status.current.desiredShipDate}" pattern="${dateFormatPattern}"/>
     <td width="5%" rowspan="${rowCountPart[currentPer]}">${formattedDesiredShipDate}</td>
     <td width="5%" rowspan="${rowCountPart[currentPer]}">${status.current.orderQuantity}</td>
     </c:if>
    <td width="5%">
<%--       <c:choose>
       <c:when test="${!empty status.current.asnStatus && status.current.asnStatus == 'SENT'}">
          ASN SENT
       </c:when>
       <c:otherwise> --%>
          ${status.current.orderStatus}
         <%--${status.current.asnStatus}--%>
    <%--   </c:otherwise>
      </c:choose>--%>
     </td>
     <td width="5%">${status.current.shipFrom}</td>
     <td width="5%">${status.current.originalShipFrom}</td>
     <c:if test="${canSearchHubs == 'true'}">  
     <td width="5%">${status.current.hubName}</td>
     <td width="5%">${status.current.inventoryGroup}</td>
     </c:if>
     <fmt:formatDate var="formattedVendorShipDate" value="${status.current.vendorShipDate}" pattern="${dateFormatPattern}"/>
     <fmt:formatDate var="formattedExpediteDate" value="${status.current.expediteDate}" pattern="${dateFormatPattern}"/>
     <td width="5%" nowrap>
      <c:choose>
       <c:when test="${empty status.current.shipmentId}">
         <c:choose>
          <c:when test="${!empty status.current.expediteDate}">
            ${formattedExpediteDate}
          </c:when>
          <c:otherwise>
            ${formattedVendorShipDate}
          </c:otherwise>
         </c:choose>
       </c:when>
       <c:otherwise>
         &nbsp;
      </c:otherwise>
      </c:choose>
     </td>
     <fmt:formatDate var="formattedDateShipped" value="${status.current.dateShipped}" pattern="${dateFormatPattern}"/>
     <td width="5%" nowrap>
       <c:choose>
       <c:when test="${!empty status.current.shipmentId}">
         ${formattedDateShipped}
       </c:when>
       <c:otherwise>
         &nbsp;
      </c:otherwise>
      </c:choose>
     </td>     
     <td width="5%">
      <c:choose>
       <c:when test="${empty status.current.orderAge}">
        &nbsp;
       </c:when>
       <c:when test="${!empty status.current.orderAge && status.current.orderAge <=0}">
        &nbsp;
       </c:when>
       <c:otherwise>
         ${status.current.orderAge}&nbsp;<fmt:message key="label.days"/>
       </c:otherwise>
      </c:choose>           
     </td>
     <td width="5%">
      <c:choose>
       <c:when test="${!empty status.current.issueQuantity && empty status.current.shipmentId}">
         ${status.current.issueQuantity}
       </c:when>
       <c:when test="${empty status.current.issueQuantity}">
         ${status.current.openQuantity}
       </c:when>
       <c:otherwise>
         &nbsp;
      </c:otherwise>
      </c:choose>
     </td>
     <td width="5%">
      <c:choose>
       <c:when test="${empty status.current.shipmentId}">
         &nbsp;
       </c:when>
       <c:otherwise>
         ${status.current.issueQuantity}
      </c:otherwise>
      </c:choose>
     </td>
     <c:if test="${ currentPer != preRadianPo }">
     <td width="5%" rowspan="${rowCountPart[currentPer]}">${status.current.shipToInfo}</td>
     <td width="5%" rowspan="${rowCountPart[currentPer]}">${status.current.poNumber}</td>
     <td width="5%" rowspan="${rowCountPart[currentPer]}">${status.current.milstripCode}</td>       
     <td width="5%" rowspan="${rowCountPart[currentPer]}">USGOV.${status.current.shipToLocationId}</td>
          <td width="5%" rowspan="${rowCountPart[currentPer]}">${status.current.shipViaDodaac}</td>   
     <td width="5%" rowspan="${rowCountPart[currentPer]}">
       <c:if test="${status.current.markForLocationId != null && status.current.markForLocationId !=''}">         
         ${status.current.shipToDodaac}
       </c:if>
       <c:if test="${!empty status.current.oconus && status.current.oconus=='Y'}" >
         <b>(<fmt:message key="label.oconus"/>)</b>
        </c:if>
     </td>     
     <td width="5%" rowspan="${rowCountPart[currentPer]}">${status.current.facPartNo} <br/> ${status.current.partShortName}</td>
     <%--<td width="5%" rowspan="${rowCountPart[currentPer]}">${status.current.contractNumber}</td>--%>

     <%--<td width="5%" rowspan="${rowCountPart[currentPer]}">${status.current.milstripCode}</td>--%>
     <c:set var="notes" value='${status.current.deliveryComments}'/>
     <c:choose>
      <c:when test="${empty notes || notes == ' '}" >
        <td width="5%" rowspan="${rowCountPart[currentPer]}" id="lineNotesTd<c:out value="${status.index}"/>">&nbsp;</td>
      </c:when>
      <c:otherwise>
        <c:set var="notes">
          <c:out value="${status.current.deliveryComments}" escapeXml="false"/>
        </c:set>

        <c:set var="notes">
          <tcmis:replace value="${notes}" from="'" to="\\'"/>
        </c:set>

        <c:set var="notes">
          <tcmis:replace value="${notes}" from="\r" to=""/>
        </c:set>

        <c:set var="notes">
          <tcmis:replace value="${notes}" from="\n" to="<br>"/>
        </c:set>
        <td width="5%" rowspan="${rowCountPart[currentPer]}" id="lineNotesTd<c:out value="${status.index}"/>" style="cursor:pointer;" onclick="parent.showDeliveryComments('<c:out value="${notes}"/>')">
        +
      </c:otherwise>
     </c:choose>
<%--
     <c:choose>
      <c:when test='${! empty status.current.deliveryComments}'>
        <td width="10%" rowspan="${rowCountPart[currentPer]}" onclick='showNotes("deliveryComments<c:out value="${status.index}"/>");' style="cursor:pointer;">
        <span id='spandeliveryComments<c:out value="${status.index}"/>'>
         <p id='pgphdeliveryComments<c:out value="${status.index}"/>'>&nbsp;&nbsp;+&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;</p>
         <div id='divdeliveryComments<c:out value="${status.index}"/>' style='display:none' onmouseover='style.cursor="hand"'>
          <c:out value="${status.current.deliveryComments}"/>
         </div>
        </span>
        </td>
      </c:when>
      <c:otherwise>
        <td width="10%" rowspan="${rowCountPart[currentPer]}">
         &nbsp;
        </td>
      </c:otherwise>
     </c:choose>    --%>
    </c:if>

     <%--<td width="5%">${status.current.transportationControlNum}</td>--%>
     <td width="5%">${status.current.carrierName}</td>
     <td width="5%">
     <c:choose>
      <c:when test="${status.current.carrierName == 'UPS Ground' || status.current.carrierName == 'UPS' }">
        <a title="<fmt:message key="label.viewtrackinginformation"/>" href="http://wwwapps.ups.com/WebTracking/processInputRequest?HTMLVersion=5.0&loc=en_US&Requester=UPSHome&AgreeToTermsAndConditions=yes&ignore=&track.x=32&track.y=6&tracknum=${status.current.trackingNumber}" target="_new">
      </c:when>
      <c:when test="${status.current.carrierName == 'Old Dominion'}">
        <a title="<fmt:message key="label.viewtrackinginformation"/>" href="http://www.odfl.com/trace/Trace.jsp?action=Status&Type=P&pronum=${status.current.trackingNumber}" target="_new">
      </c:when>
      <c:when test="${status.current.carrierName == 'Fedex'}">
        <a title="<fmt:message key="label.viewtrackinginformation"/>" href="http://www.fedex.com/Tracking?ascend_header=1&clienttype=dotcom&cntry_code=us&language=english&tracknumbers=${status.current.trackingNumber}" target="_new">
      </c:when>
      <c:when test="${status.current.carrierName == 'ABF'}">
        <a title="<fmt:message key="label.viewtrackinginformation"/>" href="https://www.abfs.com/tools/trace/default.asp?hidSubmitted=Y&hidNotifyBy=S&DefaultView=S&reftype0=a&refno0=${status.current.trackingNumber}" target="_new">
      </c:when>
      <c:when test="${status.current.carrierName == 'Southeast'}">
        <a title="<fmt:message key="label.viewtrackinginformation"/>" href="https://www.sefl.com/seflWebsite/servlet?GUID=&externalMenu=true&action=Tracing_Trace_by_pro&Type=PN&RefNum=${status.current.trackingNumber}" target="_new">
      </c:when>
    </c:choose>
    ${status.current.trackingNumber}
    <c:if test="${status.current.carrierName == 'UPS' || status.current.carrierName == 'UPS Ground' || status.current.carrierName == 'Old Dominion' || status.current.carrierName == 'Fedex' || status.current.carrierName == 'ABF' || status.current.carrierName == 'Southeast'}">
      </a>
    </c:if>
    </td>
    <td width="5%">${status.current.issuer}</td>

    <c:set var="notes" value='${status.current.comments}'/>
    <c:choose>
      <c:when test="${empty notes || notes == ' '}" >
        <td width="5%" id="lineNotesTd<c:out value="${status.index}"/>">&nbsp;</td>
      </c:when>
      <c:otherwise>
        <c:set var="notes">
          <c:out value="${status.current.comments}" escapeXml="false"/>
        </c:set>

        <c:set var="notes">
          <tcmis:replace value="${notes}" from="'" to="\\'"/>
        </c:set>

        <c:set var="notes">
          <tcmis:replace value="${notes}" from="\r" to=""/>
        </c:set>

        <c:set var="notes">
          <tcmis:replace value="${notes}" from="\n" to="<br>"/>
        </c:set>
        <td width="5%" id="lineNotesTd<c:out value="${status.index}"/>" style="cursor:pointer;" onclick="parent.showDeliveryComments('<c:out value="${notes}"/>')">
        +
      </c:otherwise>
    </c:choose>
    <td width="5%"><input class="radio" disabled="disabled" type="checkbox" name="inpsectionRequired${status.index}" id="inpsectionRequired${status.index}"  value="" <c:if test="${status.current.originInspectionRequired =='Y'}">checked</c:if>/></td>
    <td width="10%"><fmt:formatDate var="dateSentWawf" value="${status.current.dateSentWawf}" pattern="${dateTimeFormatPattern}"/>${dateSentWawf}</td>
   </tr>

   <c:set var="preRadianPo" value='${status.current.radianPo}${status.current.poNumber}'/>
   <c:set var="dataCount" value='${dataCount+1}'/>

   </c:forEach>
   </table>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty DLAOrderViewBeanCollection}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>

<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${validPoCount}" type="hidden">
<input name="shippedCount" id="shippedCount" value="${shippedCount}" type="hidden">    
<input name="radianPO" id="radianPO" value="" type="hidden">
<input name="docNumWithSuffix" id="docNumWithSuffix" value="" type="hidden">
<input name="userAction" id="userAction" value="" type="hidden">

<input name="supplier" id="supplier" value="<tcmis:jsReplace value="${param.supplier}" /> " type="hidden">
<input name="searchArgument" id="searchArgument" value="<tcmis:jsReplace value="${param.searchArgument}" /> " type="hidden">
 <c:set var="suppLocationIdArrayVar"> <tcmis:jsReplace value="${paramValues.suppLocationIdArray}" /></c:set>
	<c:forEach var="suppLocationId" items="${suppLocationIdArrayVar}" varStatus="status2">
		<input name="suppLocationIdArray" id="suppLocationIdArray_${status2.index}" value="${suppLocationId}" type="hidden"/>
	</c:forEach>
<input name="openOrdersOnly" id="openOrdersOnly" value="<tcmis:jsReplace value="${param.openOrdersOnly}" /> " type="hidden">
<input name="supplier" id="supplier" value="<tcmis:jsReplace value="${param.supplier}" /> " type="hidden">
<input name="searchField" id="searchField" value="<tcmis:jsReplace value="${param.searchField}" /> " type="hidden">
<input name="searchMode" id="searchMode" value="<tcmis:jsReplace value="${param.searchMode}" /> " type="hidden">
<input name="status" id="status" value="<tcmis:jsReplace value="${param.status}"/>" type="hidden">
<input name="searchArgument" id="searchArgument" value="<tcmis:jsReplace value="${param.searchArgument}" /> " type="hidden">
<input name="dateSentBegin" id="dateSentBegin" value="<tcmis:jsReplace value="${param.dateSentBegin}" /> " type="hidden">
<input name="dateSentEnd" id="dateSentEnd" value="<tcmis:jsReplace value="${param.dateSentEnd}" /> " type="hidden">
<input name="companyId" id="companyId" value="${sessionScope.personnelBean.companyId}" type="hidden" />

<input name="minHeight" id="minHeight" type="hidden" value="200">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>
