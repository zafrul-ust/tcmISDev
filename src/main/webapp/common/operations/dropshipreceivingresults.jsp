
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

<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<script src="/js/common/resultiframeresize.js" language="JavaScript"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script src="/js/common/operations/dropshipreceiving.js" language="JavaScript"></script>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>


<!-- This is for the YUI, uncomment if you will use this -->
<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>
<title>
<fmt:message key="dropshipReceiving"/>
</title>

<script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--

// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--

with(milonic=new menuname("showPurchaseOrder")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="label.viewpurchaseorder"/>;url=javascript:showRadianPo();");
}

drawMenus();

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
selectedUser:"<fmt:message key="label.selecteduser"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
expiredateformat:"<fmt:message key="label.expiredateformat"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
alert:"<fmt:message key="label.alert"/>",
packagedqtyreceived:"<fmt:message key="label.packagedqtyreceived"/>",
checkpackagedsize:"<fmt:message key="label.checkpackagedsize"/>",
qtyreceivednotmatch:"<fmt:message key="label.qtyreceivednotmatch"/>",
qtyreceived:"<fmt:message key="label.qtyreceived"/>",
qtyreceivedgreaterthanopen:"<fmt:message key="label.qtyreceivedgreaterthanopen"/>",
qtybeingreceived:"<fmt:message key="label.qtybeingreceived"/>",
enterpoqtyforqtyreceived:"<fmt:message key="label.enterpoqtyforqtyreceived"/>",
polinecannotreceived:"<fmt:message key="label.polinecannotreceived"/>",
greaterthanqtyopen:"<fmt:message key="label.greaterthanqtyopen"/>",
qtyrecgreaterthanmralloc:"<fmt:message key="label.qtyrecgreaterthanmralloc"/>",    
mfglot:"<fmt:message key="label.mfglot"/>",
actualsuppliershipdate:"<fmt:message key="label.actualsuppliershipdate"/>",
dorformat:"<fmt:message key="label.dorformat"/>",
dor:"<fmt:message key="receivedreceipts.label.dor"/>",
expiredate:"<fmt:message key="label.expiredate"/>",
is:"<fmt:message key="label.is"/>",
actsupshpdate:"<fmt:message key="label.actsupshpdate"/>",
indefinite:"<fmt:message key="label.indefinite"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

// -->
</script>
</head>


<c:choose>
  <c:when test="${!empty receivedReceipts}" >
    <body onLoad="showChemicalReceivedReceipts('<c:out value="${receivedReceipts}"/>')" onunload="parent.closeAllchildren();">
  </c:when>
  <c:otherwise>
    <body bgcolor="#ffffff" onload="myOnload()" onmouseup="toggleContextMenuToNormal()" onunload="parent.closeAllchildren();">
  </c:otherwise>
</c:choose>
<%--
<body bgcolor="#ffffff" onload="myOnload()" onmouseup="toggleContextMenuToNormal()">
--%>

<tcmis:form action="/dropshipreceivingresults.do" onsubmit="return submitFrameOnlyOnce();">

 <c:set var="receivingPermission" value=''/>
 <tcmis:locationPermission indicator="true" userGroupId="DropshipReceiving">
  <c:set var="receivingPermission" value='Yes'/>
 </tcmis:locationPermission>

  <tcmis:inventoryGroupPermission indicator="true" userGroupId="DropshipReceiving">
	  <c:set var="receivingPermission" value='Yes'/>
  </tcmis:inventoryGroupPermission>
 

<!-- Check if the user has permissions and needs to see the update links. Set the variable you use in javascript to true.-->
 <script type="text/javascript">
 <!--
    <c:choose>
      <c:when test="${receivingPermission == 'Yes'}" >
       showUpdateLinks = true;
      </c:when>
      <c:otherwise>
       showUpdateLinks = false;
      </c:otherwise>
    </c:choose>
 //-->
 </script>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
<c:if test="${!empty errorMessage}">
 <fmt:message key="error.db.procedure"/>
</c:if>
 <html:errors/>
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty errorMessage}">
    showErrorMessage = false;
   </c:when>
   <c:when test="${!empty errorMessage}">
    showErrorMessage = true;
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

<c:if test="${receivingViewRelationBeanCollection != null}" >
<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

<c:set var="module">
 <tcmis:module/>
</c:set>
 
<!-- Search results start -->
 <c:if test="${!empty receivingViewRelationBeanCollection}" >
    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">

    <bean:size collection="${receivingViewRelationBeanCollection}" id="resultSize"/>
    <c:forEach var="custDropShipReceivingViewBean" items="${receivingViewRelationBeanCollection}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
       <!-- Need to print the header every 10 rows-->
       <tr align="center">
         <th width="10%"><fmt:message key="label.mrlineuserpo"/></th>
         <c:if test='${module == "hub"}' >
    		<th width="5%" ><fmt:message key="label.customer"/></th>
    		<th width="5%" ><fmt:message key="label.csr"/></th>
  		 </c:if>
         <th width="5%" ><fmt:message key="label.requestor"/></th>
         <th width="5%" ><fmt:message key="label.deliverto"/></th>
         <th width="5%" ><fmt:message key="label.haas"/>&nbsp;<fmt:message key="label.poline"/></th>
         <th width="5%" ><fmt:message key="label.dateexptd"/></th>
         <th width="7%" ><fmt:message key="label.supplier"/></th>
         <th width="5%" ><fmt:message key="label.openamt(mrqtyopen)"/></th>
         <th width="5%" ><fmt:message key="label.packaging"/></th>
         <th width="12%"><fmt:message key="label.description"/></th>
         <c:if test="${receivingPermission == 'Yes'}">
           <th width="2%"><fmt:message key="label.ok"/></th>
           <th width="8%"><fmt:message key="label.lot"/></th>
           <th width="5%"><fmt:message key="label.actsupshpdate"/></th>
           <th width="5%"><fmt:message key="receivedreceipts.label.dor"/></th>
           <th width="5%"><fmt:message key="label.expdate"/></th>
           <th width="5%"><fmt:message key="receiving.label.qtyreceived"/></th>
           <th width="5%" colspan="2"><fmt:message key="receiving.label.packagedqty"/>
             x <fmt:message key="receiving.label.packagedsize"/>
           </th>
           <th width="5%"><fmt:message key="label.notes"/></th>
           <th width="5%"><fmt:message key="receiving.label.deliveryticket"/></th>
           <th width="5%"><fmt:message key="label.shipfromcountry"/></th>
           <th width="2%"><fmt:message key="receiving.label.duplicateline"/></th>
          <th width="2%"><fmt:message key="receiving.label.duplicatekitpkg"/></th>
          </c:if>
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
   
  <c:set var="redReadOnly" value='N'/>
  <c:if test="${status.current.mrNumber == null || status.current.mrNumber == ''}">
	  <c:set var="redReadOnly" value='Y'/>
  </c:if>

  <c:set var="updateStatus" value='${status.current.updateStatus}'/>
  <c:choose>
  <c:when test="${updateStatus == 'NO' || updateStatus == 'Error'}">
   <c:set var="colorClass" value='error'/>
  </c:when>
  <c:when test="${redReadOnly == 'Y'}">
     <c:set var="colorClass" value='red'/>
  </c:when>
  </c:choose>
  
  <tr align="center" class='<c:out value="${colorClass}"/>' id='rowId<c:out value="${status.index}"/>' onmouseup="selectRow('${status.index}')">
   <input type="hidden" name="colorClass${status.index}" id="colorClass<c:out value="${status.index}"/>" value="${colorClass}" />
   <input type="hidden" id="selectedDataCount<c:out value="${status.index}"/>" value="${dataCount}">
  <fmt:formatDate var="formattedexpectedDate" value="${status.current.expected}" pattern="${dateFormatPattern}"/>
  <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].qtyOpen" id="qtyOpen<c:out value="${dataCount}"/>" value="<c:out value="${status.current.qtyOpen}"/>">
  <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].mrQtyOpen" id="mrQtyOpen<c:out value="${dataCount}"/>" value="<c:out value="${status.current.mrQtyOpen}"/>">    
  <c:set var="kitCollection"  value='${status.current.kitCollection}'/>
  <bean:size id="listSize" name="kitCollection"/>
  <c:set var="mainRowSpan" value='${status.current.rowSpan}' />
  <c:set var="manageKitsAsSingleUnit" value='${status.current.manageKitsAsSingleUnit}'/>
  <c:set var="mvItem" value='${status.current.mvItem}'/>
  <c:set var="transType" value='${status.current.transType}'/>

  <c:set var="locationPermission" value=''/>
  <tcmis:locationPermission indicator="true" userGroupId="DropshipReceiving" locationId="${status.current.shipToLocationId}">
   <c:set var="locationPermission" value='Yes'/>
  </tcmis:locationPermission>

  <c:if test="${status.current.companyId == 'USGOV'}" >
   <tcmis:locationPermission indicator="true" userGroupId="DropshipReceiving">
    <c:set var="locationPermission" value='Yes'/>
   </tcmis:locationPermission>
  </c:if>

  <tcmis:inventoryGroupPermission indicator="true" userGroupId="DropshipReceiving" inventoryGroup="${status.current.inventoryGroup}">
   <c:set var="locationPermission" value='Yes'/>
  </tcmis:inventoryGroupPermission>
  
   <c:if test="${redReadOnly == 'Y'}">
	   	<c:set var="locationPermission" value=''/>
  </c:if>
  
 

  <c:set var="mainLotStatus" value='${status.current.lotStatus}'/>
  <c:if test="${empty mainLotStatus}" >
   <c:set var="mainLotStatus" value='Available'/>
  </c:if>

  <c:set var="customerPo" value='${status.current.customerPo}'/>
  <c:choose>
    <c:when test="${empty customerPo}">
      <td rowspan="<c:out value="${mainRowSpan}"/>" width="5%"><c:out value="${status.current.mrNumber}"/>-<c:out value="${status.current.mrLineItem}"/></td>
    </c:when>
    <c:otherwise>
      <td rowspan="<c:out value="${mainRowSpan}"/>" width="5%"><c:out value="${status.current.mrNumber}"/>-<c:out value="${status.current.mrLineItem}"/>
     (<c:out value="${status.current.customerPo}"/>)</td>
    </c:otherwise>
  </c:choose>
 
  <c:if test='${module == "hub"}' >
    <td rowspan="<c:out value="${mainRowSpan}"/>" width="5%" ><c:out value="${status.current.customerName}"/></td>
    <td rowspan="<c:out value="${mainRowSpan}"/>" width="5%" ><c:out value="${status.current.csrName}"/></td>
  </c:if>
 
  <td rowspan="<c:out value="${mainRowSpan}"/>" width="5%"><c:out value="${status.current.requestorName}"/></td>
  <td rowspan="<c:out value="${mainRowSpan}"/>" width="5%"><c:out value="${status.current.shipToLocationId}"/></td>
	 <%--
  <td rowspan="<c:out value="${mainRowSpan}"/>" width="5%"><c:out value="${status.current.radianPo}"/>-<c:out value="${status.current.lineItem}"/></td>
  --%>
  <td rowspan="<c:out value="${mainRowSpan}"/>" width="5%">
    
	 <c:choose>
		 <c:when test="${status.current.enableLinkToPo == 'Y'}">
		 	<c:out value="${status.current.radianPo}"/>-<c:out value="${status.current.lineItem}"/>
<%-- 			<a href="#" onclick="showRadianPo('<c:out value="${status.current.radianPo}"/>'); return false;">
		 		<c:out value="${status.current.radianPo}"/>-<c:out value="${status.current.lineItem}"/>
			</a> --%>
		 </c:when>
		 <c:otherwise>
			<c:out value="${status.current.radianPo}"/>-<c:out value="${status.current.lineItem}"/> 
		 </c:otherwise>
	 </c:choose>

	</td>

  <fmt:formatDate var="formattedExpectedDate" value="${status.current.expected}" pattern="${dateFormatPattern}"/>
  <td rowspan="<c:out value="${mainRowSpan}"/>" width="5%"><c:out value="${formattedExpectedDate}"/></td>
  <td rowspan="<c:out value="${mainRowSpan}"/>" width="5%"><c:out value="${status.current.lastSupplier}"/></td>

  <c:set var="mrQtyOpen" value='${status.current.mrQtyOpen}'/>
  <c:choose>
    <c:when test="${empty mrQtyOpen}">
      <td rowspan="<c:out value="${mainRowSpan}"/>" width="5%"><fmt:formatNumber maxFractionDigits="4" minFractionDigits="0"><fmt:parseNumber type="number" >${status.current.qtyOpen}</fmt:parseNumber></fmt:formatNumber></td>
    </c:when>
    <c:otherwise>
      <td rowspan="<c:out value="${mainRowSpan}"/>" width="5%"><fmt:formatNumber maxFractionDigits="4" minFractionDigits="0"><fmt:parseNumber type="number" >${status.current.qtyOpen}</fmt:parseNumber></fmt:formatNumber>
     (<fmt:formatNumber maxFractionDigits="2" minFractionDigits="0"><fmt:parseNumber type="number" >${status.current.mrQtyOpen}</fmt:parseNumber></fmt:formatNumber>)</td>
    </c:otherwise>
  </c:choose>

  <c:forEach var="ReceivingKitBean" items="${kitCollection}" varStatus="kitstatus">
   <c:if test="${kitstatus.index > 0 && listSize > 1 }">
     <c:set var="kitUpdateStatus" value='${kitstatus.current.updateStatus}'/>
     <c:if test="${kitUpdateStatus == 'NO' || kitUpdateStatus == 'Error'}">
      <c:set var="colorClass" value='error'/>
     </c:if>
     <tr class="<c:out value="${colorClass}"/>" id="childRowId<c:out value="${status.index}"/><c:out value="${kitstatus.index}"/>">
	  <%--<c:if test="${manageKitsAsSingleUnit != 'N'}">
      <input type="hidden" name="ktiComponent<c:out value="${dataCount}"/>" value="Yes">
     </c:if>--%>
   </c:if>

    <c:choose>
    <c:when test="${listSize > 1 && manageKitsAsSingleUnit == 'N'}">
     <td width="5%" class="alignLeft"><c:out value="${kitstatus.current.packaging}"/></td>
     <td width="12%" class="alignLeft"><c:out value="${kitstatus.current.materialDesc}"/></td>
    </c:when>
    <c:when test="${manageKitsAsSingleUnit != 'N' && kitstatus.index == 0}">
     <td width="5%" class="alignLeft" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.packaging}"/></td>
     <td width="12%" class="alignLeft" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.itemDescription}"/></td>
    </c:when>
   </c:choose>
   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].radianPo" id="radianPo<c:out value="${dataCount}"/>" value="<c:out value="${status.current.radianPo}"/>">
   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].lineItem" id="lineItem<c:out value="${dataCount}"/>" value="<c:out value="${status.current.lineItem}"/>">
   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].itemId" id="itemId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.itemId}"/>">
   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].inventoryGroup" id="inventoryGroup<c:out value="${dataCount}"/>" value="<c:out value="${status.current.inventoryGroup}"/>">
   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].transType" id="transType<c:out value="${dataCount}"/>" value="<c:out value="${status.current.transType}"/>">
   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].rowNumber" id="rowNumber<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>">
   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].branchPlant" id="branchPlant<c:out value="${dataCount}"/>" value="<c:out value="${status.current.branchPlant}"/>">
   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].mvItem" id="mvItem<c:out value="${dataCount}"/>" value="<c:out value="${mvItem}"/>">
   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].purchasingUnitsPerItem" id="purchasingUnitsPerItem<c:out value="${dataCount}"/>" value="<c:out value="${status.current.purchasingUnitsPerItem}"/>">
   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].purchasingUnitOfMeasure" id="purchasingUnitOfMeasure<c:out value="${dataCount}"/>" value="<c:out value="${status.current.purchasingUnitOfMeasure}"/>">
   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].nonintegerReceiving" id="nonintegerReceiving<c:out value="${dataCount}"/>" value="<c:out value="${status.current.nonintegerReceiving}"/>">
   <input type="hidden" id="enableLinkToPo<c:out value="${dataCount}"/>" value="<c:out value="${status.current.enableLinkToPo}"/>">
   <c:choose>
      <c:when test="${locationPermission == 'Yes'}">

        <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].duplicatePkgLine" id="duplicatePkgLine<c:out value="${dataCount}"/>" value="<c:out value="${status.current.duplicatePkgLine}"/>">
        <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].duplicateKitLine" id="duplicateKitLine<c:out value="${dataCount}"/>" value="<c:out value="${status.index}"/>">
        <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].rowSpan" id="rowSpan<c:out value="${dataCount}"/>" value="<c:out value="${listSize}"/>">
        <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].transferRequestId" id="transferRequestId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.transferRequestId}"/>">

        <c:choose>
         <c:when test="${mvItem != 'Y'}">

         <fmt:formatDate var="formattedShipDate" value="${kitstatus.current.supplierShipDate}" pattern="${dateFormatPattern}"/>
         <fmt:formatDate var="formattedDom" value="${kitstatus.current.dom}" pattern="${dateFormatPattern}"/>
         <fmt:formatDate var="formattedDos" value="${kitstatus.current.dos}" pattern="${dateFormatPattern}"/>
         <fmt:formatDate var="localeExpireDate" value="${kitstatus.current.expirationDate}" pattern="${dateFormatPattern}"/>
         
         <fmt:formatDate var="fmtExpireDate" value="${kitstatus.current.expirationDate}" pattern="MM/dd/yyyy"/>
        <c:choose>
          <c:when test='${fmtExpireDate=="01/01/3000"}'>
               <c:set var="formattedExpirationDate">
     	   			<fmt:message key="label.indefinite"/>
     	   	</c:set>
          </c:when>
          <c:otherwise>
             <c:set var="formattedExpirationDate" value="${localeExpireDate}" />
          </c:otherwise>
        </c:choose>        
 <%--       <fmt:formatDate var="formattedExpirationDate" value="${kitstatus.current.expirationDate}" pattern="MM/dd/yyyy"/>
         <c:if test="${(status.current.indefiniteShelfLife == 'y') || (formattedExpirationDate == '01/01/3000')}" >
           <c:set var="formattedExpirationDate" value='Indefinite'/>
         </c:if>  --%>

          <td width="2%">
           <c:if test="${kitstatus.current.ok != null}" >
           <input type="checkbox" class="" name="receivingViewBean[<c:out value="${dataCount}"/>].ok" id="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" checked onclick="checkChemicalReceivingInput(<c:out value="${dataCount}"/>)">
           </c:if>
           <c:if test="${kitstatus.current.ok == null}" >
           <input type="checkbox" class="" name="receivingViewBean[<c:out value="${dataCount}"/>].ok" id="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" onclick="checkChemicalReceivingInput(<c:out value="${dataCount}"/>)">
           </c:if>
          </td>

          <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].bin" id="bin<c:out value="${dataCount}"/>" value="Dropship Receiving">
          <%--<td width="1%">
           <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="addBin<c:out value="${dataCount}"/>" value="+" onclick="showVvHubBins(<c:out value="${dataCount}"/>)" >
          </td>
	       <td width="5%" class="alignLeft">
	        <c:set var="binCollection"  value='${status.current.receiptItemPriorBinViewBeanCollection}'/>
          <c:set var="selectedBin" value='${kitstatus.current.bin}'/>
	        <bean:size id="binSize" name="binCollection"/>
	        <select name="receivingViewBean[<c:out value="${dataCount}"/>].bin" id="bin<c:out value="${dataCount}"/>" class="selectBox">
	         <c:forEach var="receiptItemPriorBinViewBean" items="${binCollection}" varStatus="binstatus">
            <c:set var="currentBin" value='${binstatus.current.bin}'/>
            <c:choose>
             <c:when test="${currentBin == selectedBin}">
              <option value="<c:out value="${currentBin}"/>" selected><c:out value="${currentBin}"/></option>
             </c:when>
             <c:otherwise>
              <option value="<c:out value="${currentBin}"/>"><c:out value="${currentBin}"/></option>
             </c:otherwise>
            </c:choose>
	          <%--<OPTION value="<c:out value="${binstatus.current.bin}"/>"><c:out value="${binstatus.current.bin}"/></OPTION>--%>
	         <%--</c:forEach>
	         <c:if test="${binSize == 0}">
	          <option value="NONE">NONE</option>
	         </c:if>
	        </select>
	       </td>--%>
	       <td width="8%">
           <c:choose>
           <c:when test="${transType == 'IT' && empty submitReceive}">
             <input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].mfgLot" id="mfgLot<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.originalMfgLot}"/>" size="20" maxlength="30" class="inputBox">
           </c:when>
           <c:otherwise>
             <input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].mfgLot" id="mfgLot<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.mfgLot}"/>" size="20" maxlength="30" class="inputBox">
           </c:otherwise>
           </c:choose>
          </td>
	       <%--<td width="4%">
           <c:choose>
           <c:when test="${transType == 'IT' && empty submitReceive}">
            <input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].transferReceiptId" id="transferReceiptId<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.originalReceiptId}"/>" size="6" maxlength="10" class="inputBox">
           </c:when>
           <c:when test="${transType == 'IT'}">
            <input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].transferReceiptId" id="transferReceiptId<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.transferReceiptId}"/>" size="6" maxlength="10" class="inputBox">
           </c:when>
           <c:otherwise>
             &nbsp;
           </c:otherwise>
           </c:choose>
          </td>--%>

	       <td width="5%">
	       	 <input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].supplierShipDate" id="supplierShipDate${dataCount}"
	           onClick="return getCalendar(document.genericForm.supplierShipDate${dataCount},null,null,null,(document.genericForm.dateOfReceipt${dataCount}.value=='')?document.genericForm.today:document.genericForm.dateOfReceipt${dataCount});"
               value="${formattedShipDate}" size="8" maxlength="11" class="inputBox pointer">
<%--	         <input type="text" readonly name="receivingViewBean[${dataCount}].supplierShipDate" id="supplierShipDate${dataCount}" value="${formattedShipDate}" 
	           onClick="return getCalendar(document.genericForm.supplierShipDate${dataCount},null,null,null,document.genericForm.dateOfReceipt${dataCount});"size="8" maxlength="10" class="inputBox pointer">
 	         <a href="javascript: void(0);" class="optionTitleBold" id="linksupplierShipDate<c:out value="${dataCount}"/>" onclick="return getCalendar(document.genericForm.supplierShipDate<c:out value="${dataCount}"/>);">&diams;</a>--%>
	       </td>
	       	 <input name='receivingViewBean[${dataCount}].beforeSystemDate'
							    id='beforeSystemDate${dataCount}' type="hidden"
							    value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'  /> 
	         
               <c:set var="tmpDateOfReceipt" value="${kitstatus.current.dateOfReceipt}"/>
               <c:choose>
                 <c:when test="${empty tmpDateOfReceipt}">
                   <td width="5%">
                     <input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].dateOfReceipt" id="dateOfReceipt${dataCount}"
	           onClick="return getCalendar(document.genericForm.dateOfReceipt${dataCount},document.genericForm.date60,null,null,document.genericForm.today);"
	           value="<tcmis:getDateTag numberOfDaysFromToday="0"  datePattern="${dateFormatPattern}"/>"  size="8" maxlength="11" class="inputBox pointer">
<%--                      <input type="text" readonly name="receivingViewBean[${dataCount}].dateOfReceipt" id="dateOfReceipt${dataCount}" value="<tcmis:getDateTag numberOfDaysFromToday="0"  datePattern="${dateFormatPattern}"/>" 
                       onClick="return getCalendar(document.genericForm.dateOfReceipt${dataCount},null,null,null,document.genericForm.beforeSystemDate${dataCount});" size="8" maxlength="10" class="inputBox pointer"> 
                     <a href="javascript: void(0);" class="optionTitleBold" id="linkdateOfReceipt<c:out value="${dataCount}"/>" onclick="return getCalendar(document.genericForm.dateOfReceipt<c:out value="${dataCount}"/>);">&diams;</a> --%>
                   </td>
                 </c:when>
                 <c:otherwise>
                   <fmt:formatDate var="formattedDateOfReceipt" value="${kitstatus.current.dateOfReceipt}" pattern="${dateFormatPattern}"/>
                   <td width="5%">
                     <input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].dateOfReceipt" id="dateOfReceipt${dataCount}"
	           onClick="return getCalendar(document.genericForm.dateOfReceipt${dataCount},document.genericForm.date60,null,null,document.genericForm.today);"
	           value="${formattedDateOfReceipt}" size="8" maxlength="11" class="inputBox pointer">
<%--                      <input type="text" readonly name="receivingViewBean[${dataCount}].dateOfReceipt" id="dateOfReceipt${dataCount}" value="${formattedDateOfReceipt}"
                       onClick="return getCalendar(document.genericForm.dateOfReceipt${dataCount},null,null,null,document.genericForm.beforeSystemDate${dataCount});" size="8" maxlength="10" class="inputBox pointer"> 
                     <a href="javascript: void(0);" class="optionTitleBold" id="linkdateOfReceipt<c:out value="${dataCount}"/>" onclick="return getCalendar(document.genericForm.dateOfReceipt<c:out value="${dataCount}"/>);">&diams;</a> --%>                 
                    </td>
                 </c:otherwise>
               </c:choose>
   
	       <%--<td width="5%"><input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].dom" id="dom<c:out value="${dataCount}"/>" value="<c:out value="${formattedDom}"/>" size="8" maxlength="10" class="inputBox"><a href="javascript: void(0);" class="optionTitleBold" id="linkdom<c:out value="${dataCount}"/>" onclick="return getCalendar(document.genericForm.dom<c:out value="${dataCount}"/>);">&diams;</a></td>
	       <td width="5%"><input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].dos" id="dos<c:out value="${dataCount}"/>" value="<c:out value="${formattedDos}"/>" size="8" maxlength="10" class="inputBox"><a href="javascript: void(0);" class="optionTitleBold" id="linkdos<c:out value="${dataCount}"/>" onclick="return getCalendar(document.genericForm.dos<c:out value="${dataCount}"/>);">&diams;</a></td>
	       --%>
         <td width="5%">
            <input type="hidden" name="receivingViewBean[${dataCount}].expirationDate" id="expirationDate${dataCount}" value="${localeExpireDate}"/> 
         	<input type="text" name="receivingViewBean[${dataCount}].expirationDateString" id="expirationDateString${dataCount}" value="${formattedExpirationDate}"
	           onClick="return getCalendar(document.genericForm.expirationDateString${dataCount},null,null,document.genericForm.todayoneyear,null,'Y');"
	           onchange="expiredDateChanged(${dataCount});"  size="8" maxlength="11" class="inputBox pointer">
<%--             <input type="text" readonly name="receivingViewBean[${dataCount}].expirationDateString" id="expirationDateString${dataCount}" 
               onClick="return getCalendar(document.genericForm.expirationDateString${dataCount},null,null,document.genericForm.dateOfReceipt${dataCount},null,'Y');"  value="${formattedExpirationDate}" size="8" maxlength="10" class="inputBox pointer">
             <a href="javascript: void(0);" class="optionTitleBold" id="linkexpirationDate<c:out value="${dataCount}"/>" onclick="return getCalendar(document.genericForm.expirationDate<c:out value="${dataCount}"/>);">&diams;</a>--%>
         </td>
         <c:choose>
	       <c:when test="${kitstatus.index == 0 && manageKitsAsSingleUnit == 'N'}">
         <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
          <input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].quantityReceived" id="quantityReceived<c:out value="${dataCount}"/>" value="<c:out value="${status.current.quantityReceived}"/>" size="4" maxlength="15" class="inputBox">
         </td>
         
         <%--<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
         <c:choose>
          <c:when test="${status.current.skipReceivingQc == 'Y' && (status.current.orderQtyUpdateOnReceipt == 'y' || status.current.orderQtyUpdateOnReceipt == 'Y')}" >
           <c:if test="${status.current.closePoLine != null}" >
           <c:set var="checkCloseBoxChecked" value='checked'/>
           </c:if>
           <input type="checkbox" class="radioBtns" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].closePoLine" id="closePoLine<c:out value="${dataCount}"/>" value="<c:out value="${status.current.orderQtyUpdateOnReceipt}"/>" <c:out value="${checkCloseBoxChecked}"/> onclick="checkClosePoLine(<c:out value="${dataCount}"/>)">
          </c:when>
          <c:otherwise>
           &nbsp;
          </c:otherwise>
         </c:choose>
         </td>--%>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>" colspan="2">&nbsp;</td>
	        <%--<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
           <%--
             Receiving page currently does not handle quality control items and the certification prcoess if a quality control inventory group skips QC.
           --%>
	         <%--<c:if test="${status.current.skipReceivingQc == 'Y'}">
                <select name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].lotStatus" id="lotStatus<c:out value="${dataCount}"/>" class="selectBox" onchange="checkReceiptStatus(<c:out value="${dataCount}"/>)">
                <c:forEach var="vvLotStatusBean" items="${vvLotStatusBeanCollection}" varStatus="vvlotstatus">
                <c:set var="lotStatusValue" value='${vvlotstatus.current.lotStatus}'/>
                <c:choose>
                 <c:when test="${(statusUpdatePermission == 'Yes'  || qualityControlItem !='Y' )&& onlynonPickableStatusPermission != 'Yes'}">
                 </c:when>
                 <c:otherwise>
                  <c:if test="${vvlotstatus.current.certified == 'Y' || (vvlotstatus.current.pickable == 'Y' && vvlotstatus.current.allocationOrder !=0)}">
                     <c:set var="lotStatusValue" value=''/>
                  </c:if>
                 </c:otherwise>
                </c:choose>
                <c:choose>
                <c:when test="${mainLotStatus == vvlotstatus.current.lotStatus}" >
                 <option value="<c:out value="${vvlotstatus.current.lotStatus}"/>" selected><c:out value="${vvlotstatus.current.lotStatus}"/></option>
                </c:when>
                <c:otherwise>
                 <option value="<c:out value="${vvlotstatus.current.lotStatus}"/>"><c:out value="${vvlotstatus.current.lotStatus}"/></option>
                </c:otherwise>
               </c:choose>
               </c:forEach>

             <%--<select name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].lotStatus" id="lotStatus<c:out value="${dataCount}"/>" class="selectBox" onchange="lotStatusChanged()">
	           <c:forEach var="vvLotStatusBean" items="${vvLotStatusBeanCollection}" varStatus="vvlotstatus">
	            <option value="<c:out value="${vvlotstatus.current.lotStatus}"/>"><c:out value="${vvlotstatus.current.lotStatus}"/></option>
	           </c:forEach>
	          </select>--%>
	        <%-- </c:if>
	        </td>--%>
	        <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
            <textarea name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].receiptNotes" cols="25" rows="3" class="inputBox"><c:out value="${status.current.receiptNotes}"/></textarea>
           </td>
	        <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
            <input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].deliveryTicket" id="deliveryTicket<c:out value="${dataCount}"/>" value="<c:out value="${status.current.deliveryTicket}"/>" size="6" maxlength="30" class="inputBox">
           </td>
           <td rowspan="<c:out value="${mainRowSpan}"/>" width="5%">
           <select name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].countryAbbrev"
                                    class="selectBox"
                                    id="countryAbbrev<c:out value="${dataCount}"/>">
                       <option value=""><fmt:message key="label.pleaseselect"/></option>             
                                <c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}"
                                           varStatus="countryStatus">
                                 <option value="<c:out value="${countryStatus.current.countryAbbrev}"/>"><c:out value="${countryStatus.current.country}"/></option>
                                </c:forEach>
                            </select>
            </td>
	        <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">&nbsp;</td>
            <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
               <c:if test="${manageKitsAsSingleUnit == 'N'}">
                <input type="submit" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick= " return duplkit(<c:out value="${dataCount+listSize-1}"/>)" value="Dup Kit" name="duplicateButton">
               </c:if>
                <input type="hidden" name="kitSize<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${listSize}"/>">
            </td>
	       </c:when>
         <c:when test="${manageKitsAsSingleUnit != 'N'}">
            <td width="5%">
             <input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].quantityReceived" id="quantityReceived<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.quantityReceived}"/>" size="4" maxlength="15" class="inputBox">
            </td>
            
            <c:if test="${kitstatus.index == 0}">
            <%--<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
            <c:choose>
             <c:when test="${status.current.skipReceivingQc == 'Y' && (status.current.orderQtyUpdateOnReceipt == 'y' || status.current.orderQtyUpdateOnReceipt == 'Y')}" >
             <c:if test="${status.current.closePoLine != null}" >
             <c:set var="checkCloseBoxChecked" value='checked'/>
             </c:if>
             <input type="checkbox" class="radioBtns" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].closePoLine" id="closePoLine<c:out value="${dataCount}"/>" value="<c:out value="${status.current.orderQtyUpdateOnReceipt}"/>" <c:out value="${checkCloseBoxChecked}"/> onclick="checkClosePoLine(<c:out value="${dataCount}"/>)">
             </c:when>
             <c:otherwise>
              &nbsp;
             </c:otherwise>
            </c:choose>
           </td>--%>
            <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>" colspan="2">&nbsp;</td>
            </c:if>
            <%--<td width="5%">
               <c:if test="${status.current.skipReceivingQc == 'Y'}">
                <select name="receivingViewBean[<c:out value="${dataCount}"/>].lotStatus" id="lotStatus<c:out value="${dataCount}"/>" class="selectBox" onchange="checkReceiptStatus(<c:out value="${dataCount}"/>)">
                <c:forEach var="vvLotStatusBean" items="${vvLotStatusBeanCollection}" varStatus="vvlotstatus">
                <c:set var="lotStatusValue" value='${vvlotstatus.current.lotStatus}'/>
                <c:choose>
                 <c:when test="${(statusUpdatePermission == 'Yes'  || qualityControlItem !='Y' )&& onlynonPickableStatusPermission != 'Yes'}">
                 </c:when>
                 <c:otherwise>
                  <c:if test="${vvlotstatus.current.certified == 'Y' || (vvlotstatus.current.pickable == 'Y' && vvlotstatus.current.allocationOrder !=0)}">
                     <c:set var="lotStatusValue" value=''/>
                  </c:if>
                 </c:otherwise>
                </c:choose>
                <c:choose>
                <c:when test="${mainLotStatus == vvlotstatus.current.lotStatus}" >
                 <option value="<c:out value="${vvlotstatus.current.lotStatus}"/>" selected><c:out value="${vvlotstatus.current.lotStatus}"/></option>
                </c:when>
                <c:otherwise>
                 <option value="<c:out value="${vvlotstatus.current.lotStatus}"/>"><c:out value="${vvlotstatus.current.lotStatus}"/></option>
                </c:otherwise>
               </c:choose>
               </c:forEach>
               </select>
               </c:if>
             </td>--%>
             <td width="5%">
             <textarea name="receivingViewBean[<c:out value="${dataCount}"/>].receiptNotes" cols="25" rows="3" class="inputBox"><c:out value="${kitstatus.current.receiptNotes}"/></textarea>
             </td>
             <td width="5%">
              <input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].deliveryTicket" id="deliveryTicket<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.deliveryTicket}"/>" size="6" maxlength="30" class="inputBox">
             </td>
             <td>
             <select name="receivingViewBean[<c:out value="${dataCount}"/>].countryAbbrev"
                                    class="selectBox"
                                    id="countryAbbrev<c:out value="${dataCount}"/>">
                       <option value=""><fmt:message key="label.pleaseselect"/></option>             
                                <c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}"
                                           varStatus="countryStatus">
                                 <option value="<c:out value="${countryStatus.current.countryAbbrev}"/>"><c:out value="${countryStatus.current.country}"/></option>
                                </c:forEach>
                            </select>
             </td>
             <c:if test="${kitstatus.index == 0}">
              <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
               <c:if test="${listSize < 2 || manageKitsAsSingleUnit != 'N'}">
                <input type="submit" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick= " return duplLine(<c:out value="${dataCount}"/>)" value="<fmt:message key="label.dupl"/>" name="duplicateButton">
               </c:if>
              </td>
              <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">&nbsp;</td>
             </c:if>
          </c:when>
          </c:choose>
	      </c:when>
        <c:otherwise>   <%--Here starts the variable packaging stuff--%>
         <fmt:formatDate var="formattedShipDate" value="${status.current.supplierShipDate}" pattern="${dateFormatPattern}"/>
         <fmt:formatDate var="formattedDom" value="${status.current.dom}" pattern="${dateFormatPattern}"/>
         <fmt:formatDate var="formattedDos" value="${status.current.dos}" pattern="${dateFormatPattern}"/>
         <fmt:formatDate var="localeExpireDate" value="${status.current.expirationDate}" pattern="${dateFormatPattern}"/>
         <fmt:formatDate var="fmtExpireDate" value="${status.current.expirationDate}" pattern="MM/dd/yyyy"/>
        <c:choose>
          <c:when test='${fmtExpireDate=="01/01/3000"}'>
               <c:set var="formattedExpirationDate">
     	   			<fmt:message key="label.indefinite"/>
     	   	</c:set>
          </c:when>
          <c:otherwise>
             <c:set var="formattedExpirationDate" value="${localeExpireDate}" />
          </c:otherwise>
        </c:choose>        
<%--          <fmt:formatDate var="formattedExpirationDate" value="${status.current.expirationDate}" pattern="MM/dd/yyyy"/>
         <c:if test="${(status.current.indefiniteShelfLife == 'y') || (formattedExpirationDate == '01/01/3000')}" >
           <c:set var="formattedExpirationDate" value='Indefinite'/>
         </c:if>  --%>

           <c:if test="${kitstatus.index == 0}">
            <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
             <c:if test="${status.current.ok != null}" >
              <input type="checkbox" class="radioBtns" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].ok" id="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" checked onclick="checkChemicalReceivingInput(<c:out value="${dataCount}"/>)">
             </c:if>
             <c:if test="${status.current.ok == null}" >
              <input type="checkbox" class="radioBtns" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].ok" id="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" onclick="checkChemicalReceivingInput(<c:out value="${dataCount}"/>)">
             </c:if>
            </td>

            <input type="hidden" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].bin" id="bin<c:out value="${dataCount}"/>" value="Dropship Receiving">
            <%--<td width="1%" rowspan="<c:out value="${mainRowSpan}"/>">
             <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="addBin<c:out value="${dataCount}"/>" value="+" onclick="showVvHubBins(<c:out value="${dataCount}"/>)" >
            </td>
            <td width="5%"class="alignLeft" rowspan="<c:out value="${mainRowSpan}"/>">
              <c:set var="binCollection"  value='${status.current.receiptItemPriorBinViewBeanCollection}'/>
              <c:set var="selectedBin" value='${status.current.bin}'/>
              <bean:size id="binSize" name="binCollection"/>
              <select name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].bin" id="bin<c:out value="${dataCount}"/>" class="selectBox">
               <c:forEach var="receiptItemPriorBinViewBean" items="${binCollection}" varStatus="binstatus">
                <c:set var="currentBin" value='${binstatus.current.bin}'/>
                 <c:choose>
                  <c:when test="${currentBin == selectedBin}">
                   <option value="<c:out value="${currentBin}"/>" selected><c:out value="${currentBin}"/></option>
                  </c:when>
                  <c:otherwise>
                   <option value="<c:out value="${currentBin}"/>"><c:out value="${currentBin}"/></option>
                  </c:otherwise>
                 </c:choose>
                <%--<option value="<c:out value="${binstatus.current.bin}"/>"><c:out value="${binstatus.current.bin}"/></option>--%>
               <%--</c:forEach>
               <c:if test="${binSize == 0}">
                <option value="NONE">NONE</option>
               </c:if>
              </select>
             </td>--%>

             <td width="8%" rowspan="<c:out value="${mainRowSpan}"/>">
             <c:choose>
              <c:when test="${transType == 'IT' && empty submitReceive}">
               <input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].mfgLot" id="mfgLot<c:out value="${dataCount}"/>" value="<c:out value="${status.current.originalMfgLot}"/>" size="20" maxlength="30" class="inputBox">
              </c:when>
              <c:otherwise>
               <input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].mfgLot" id="mfgLot<c:out value="${dataCount}"/>" value="<c:out value="${status.current.mfgLot}"/>" size="20" maxlength="30" class="inputBox">
              </c:otherwise>
             </c:choose>
             </td>
             <%--<td width="4%" rowspan="<c:out value="${mainRowSpan}"/>">
             <c:choose>
              <c:when test="${transType == 'IT' && empty submitReceive}">
               <input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].transferReceiptId" id="transferReceiptId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.originalReceiptId}"/>" size="6" maxlength="10" class="inputBox">
              </c:when>
              <c:when test="${transType == 'IT'}">
               <input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].transferReceiptId" id="transferReceiptId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.transferReceiptId}"/>" size="6" maxlength="10" class="inputBox">
              </c:when>
              <c:otherwise>
               &nbsp;
              </c:otherwise>
             </c:choose>
             </td>--%>

               <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
               		<fmt:formatDate var="formattedShipDate" value="${status.current.supplierShipDate}" pattern="${dateFormatPattern}"/>
               		<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].supplierShipDate" id="supplierShipDate${dataCount}"
			           onClick="return getCalendar(document.genericForm.supplierShipDate${dataCount},null,null,null,(document.genericForm.dateOfReceipt${dataCount}.value=='')?document.genericForm.today:document.genericForm.dateOfReceipt${dataCount});"
		               value="${formattedShipDate}" size="8" maxlength="11" class="inputBox pointer">
                 
               <%--		<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].supplierShipDate" id="supplierShipDate<c:out value="${dataCount}"/>" value="<c:out value="${formattedShipDate}"/>" size="8" maxlength="10" class="inputBox">
               		<a href="javascript: void(0);" class="optionTitleBold" id="linksupplierShipDate<c:out value="${dataCount}"/>" onclick="return getCalendar(document.genericForm.supplierShipDate<c:out value="${dataCount}"/>);">&diams;</a>  --%>
               </td>
               <fmt:formatDate var="fmtDateOfReceipt" value="${status.current.dateOfReceipt}" pattern="${dateFormatPattern}"/>
               <c:set var="tmpDateOfReceipt" value="${status.current.dateOfReceipt}"/>
               <c:choose>
                 <c:when test="${empty tmpDateOfReceipt}">
                   <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
                    <input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].dateOfReceipt" id="dateOfReceipt${dataCount}"
			           onClick="return getCalendar(document.genericForm.dateOfReceipt${dataCount},document.genericForm.date60,null,null,document.genericForm.today);"
			           value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>" size="8" maxlength="11" class="inputBox pointer">
                   <%--	<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].dateOfReceipt" id="dateOfReceipt<c:out value="${dataCount}"/>" value="<tcmis:getDateTag numberOfDaysFromToday="0"/>" size="8" maxlength="10" class="inputBox"><a href="javascript: void(0);" class="optionTitleBold" id="linkdateOfReceipt<c:out value="${dataCount}"/>" onclick="return getCalendar(document.genericForm.dateOfReceipt<c:out value="${dataCount}"/>);">&diams;</a> --%>
                   </td>
                 </c:when>
                 <c:otherwise>
                 <%--  <fmt:formatDate var="formattedDateOfReceipt" value="${status.current.dateOfReceipt}" pattern="MM/dd/yyyy"/> --%>
                   <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
                    <input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].dateOfReceipt" id="dateOfReceipt${dataCount}"
			           onClick="return getCalendar(document.genericForm.dateOfReceipt${dataCount},document.genericForm.date60,null,null,document.genericForm.today);"
			           value="${fmtDateOfReceipt}" size="8" maxlength="11" class="inputBox pointer">
                   <%--  	<input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].dateOfReceipt" id="dateOfReceipt<c:out value="${dataCount}"/>" value="<c:out value="${fmtDateOfReceipt}"/>" size="8" maxlength="10" class="inputBox"><a href="javascript: void(0);" class="optionTitleBold" id="linkdateOfReceipt<c:out value="${dataCount}"/>" onclick="return getCalendar(document.genericForm.dateOfReceipt<c:out value="${dataCount}"/>);">&diams;</a>  --%>
                   </td>
                 </c:otherwise>
               </c:choose>
               <%--<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].dom" id="dom<c:out value="${dataCount}"/>" value="<c:out value="${formattedDom}"/>" size="8" maxlength="10" class="inputBox"><a href="javascript: void(0);" class="optionTitleBold" id="linkdom<c:out value="${dataCount}"/>" onclick="return getCalendar(document.genericForm.dom<c:out value="${dataCount}"/>);">&diams;</a></td>
               <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].dos" id="dos<c:out value="${dataCount}"/>" value="<c:out value="${formattedDos}"/>" size="8" maxlength="10" class="inputBox"><a href="javascript: void(0);" class="optionTitleBold" id="linkdos<c:out value="${dataCount}"/>" onclick="return getCalendar(document.genericForm.dos<c:out value="${dataCount}"/>);">&diams;</a></td>
               --%>
               <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
                <fmt:formatDate var="localeExpireDate" value="${status.current.expirationDate}" pattern="${dateFormatPattern}"/>
		        <fmt:formatDate var="fmtExpireDate" value="${status.current.expirationDate}" pattern="MM/dd/yyyy"/>
		        <c:choose>
		          <c:when test='${fmtExpireDate=="01/01/3000"}'>
		               <c:set var="formattedExpirationDate">
		     	   			<fmt:message key="label.indefinite"/>
		     	   	</c:set>
		          </c:when>
		          <c:otherwise>
		             <c:set var="formattedExpirationDate" value="${localeExpireDate}" />
		          </c:otherwise>
		        </c:choose>        
               	<fmt:formatDate var="localeExpireDate" value="${status.current.expirationDate}" pattern="${dateFormatPattern}"/>
               
               	<input type="hidden" name="receivingViewBean[${dataCount+listSize-1}].expirationDate" id="expirationDate${dataCount}" value="${localeExpireDate}"/> 
	         	<input type="text" name="receivingViewBean[${dataCount+listSize-1}].expirationDateString" id="expirationDateString${dataCount}" value="${formattedExpirationDate}"
		           onClick="return getCalendar(document.genericForm.expirationDateString${dataCount},null,null,document.genericForm.todayoneyear,null,'Y');"
		           onchange="expiredDateChanged(${dataCount});"  size="8" maxlength="11" class="inputBox pointer">
<%--                <input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].expirationDate" id="expirationDate<c:out value="${dataCount}"/>" value="<c:out value="${formattedExpirationDate}"/>" size="8" maxlength="10" class="inputBox"><a href="javascript: void(0);" class="optionTitleBold" id="linkexpirationDate<c:out value="${dataCount}"/>" onclick="return getCalendar(document.genericForm.expirationDate<c:out value="${dataCount}"/>);">&diams;</a> --%>
               </td>

                <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
            <input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].quantityReceived" id="quantityReceived<c:out value="${dataCount}"/>" value="<c:out value="${status.current.quantityReceived}"/>" size="4" maxlength="15" class="inputBox">
           </td>
           <%--<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
            <c:choose>
             <c:when test="${status.current.skipReceivingQc == 'Y' && (status.current.orderQtyUpdateOnReceipt == 'y' || status.current.orderQtyUpdateOnReceipt == 'Y')}" >
              <c:if test="${status.current.closePoLine != null}" >
              <c:set var="checkCloseBoxChecked" value='checked'/>
              </c:if>
              <input type="checkbox" class="radioBtns" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].closePoLine" id="closePoLine<c:out value="${dataCount}"/>" value="<c:out value="${status.current.orderQtyUpdateOnReceipt}"/>" <c:out value="${checkCloseBoxChecked}"/> onclick="checkClosePoLine(<c:out value="${dataCount}"/>)">
             </c:when>
             <c:otherwise>
              &nbsp;
             </c:otherwise>
            </c:choose>
           </td>---%>
          </c:if>
          <%--<c:if test="${kitstatus.index > 0 && kitstatus.index < listSize}">
           <input type="hidden" name="receivingViewBean[<c:out value="${dataCount-1}"/>].ok" id="ok<c:out value="${dataCount-1}"/>" value="<c:out value="${dataCount-1}"/>">
          </c:if>--%>

                <td width="5%" class="alignLeft" colspan="2">
                <input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].packagedQty" id="packagedQty<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.packagedQty}"/>" size="3" maxlength="15" class="inputBox" style="width:25px;">
                X <input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].packagedSize" id="packagedSize<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.packagedSize}"/>" size="3" maxlength="10" class="inputBox" style="width:35px;">&nbsp;<c:out value="${status.current.purchasingUnitOfMeasure}"/> <c:out value="${status.current.displayPkgStyle}"/>
                <br><font class="invisible<c:out value="${colorClass}"/>">____________________</font></td>

                 <c:if test="${kitstatus.index == 0}">
                 <%--<td width="5%"  rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.displayPkgStyle}"/></td>--%>
                <%--<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
                <c:if test="${status.current.skipReceivingQc == 'Y'}">
                <select name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].lotStatus" id="lotStatus<c:out value="${dataCount}"/>" class="selectBox" onchange="checkReceiptStatus(<c:out value="${dataCount}"/>)">
                <c:forEach var="vvLotStatusBean" items="${vvLotStatusBeanCollection}" varStatus="vvlotstatus">
                <c:set var="lotStatusValue" value='${vvlotstatus.current.lotStatus}'/>
                <c:choose>
                 <c:when test="${(statusUpdatePermission == 'Yes'  || qualityControlItem !='Y' )&& onlynonPickableStatusPermission != 'Yes'}">
                 </c:when>
                 <c:otherwise>
                  <c:if test="${vvlotstatus.current.certified == 'Y' || (vvlotstatus.current.pickable == 'Y' && vvlotstatus.current.allocationOrder !=0)}">
                     <c:set var="lotStatusValue" value=''/>
                  </c:if>
                 </c:otherwise>
                </c:choose>
                <c:choose>
                <c:when test="${mainLotStatus == vvlotstatus.current.lotStatus}" >
                 <option value="<c:out value="${vvlotstatus.current.lotStatus}"/>" selected><c:out value="${vvlotstatus.current.lotStatus}"/></option>
                </c:when>
                <c:otherwise>
                 <option value="<c:out value="${vvlotstatus.current.lotStatus}"/>"><c:out value="${vvlotstatus.current.lotStatus}"/></option>
                </c:otherwise>
               </c:choose>
               </c:forEach>
               </select>
               </c:if>
               </td>--%>
               <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
            <textarea name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].receiptNotes" cols="25" rows="3" class="inputBox"><c:out value="${status.current.receiptNotes}"/></textarea>
           </td>
                <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
            <input type="text" name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].deliveryTicket" id="deliveryTicket<c:out value="${dataCount}"/>" value="<c:out value="${status.current.deliveryTicket}"/>" size="6" maxlength="30" class="inputBox">
           </td>
           <td rowspan="<c:out value="${mainRowSpan}"/>" width="5%">
           <select name="receivingViewBean[<c:out value="${dataCount+listSize-1}"/>].countryAbbrev"
                                    class="selectBox"
                                    id="countryAbbrev<c:out value="${dataCount}"/>">
                       <option value=""><fmt:message key="label.pleaseselect"/></option>             
                                <c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}"
                                           varStatus="countryStatus">
                                 <option value="<c:out value="${countryStatus.current.countryAbbrev}"/>"><c:out value="${countryStatus.current.country}"/></option>
                                </c:forEach>
                            </select>
            </td>
                <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
                 <c:if test="${listSize < 2 }">
                  <input type="submit" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick= " return duplpkg(<c:out value="${dataCount}"/>)" value="<fmt:message key="receiving.label.duplicateline"/>" name="duplicateButton">
                 </c:if>
                </td>
                <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
                 <c:if test="${listSize < 2 || manageKitsAsSingleUnit != 'N'}">
                  <input type="submit" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" onclick= " return duplLine(<c:out value="${dataCount}"/>)" value="<fmt:message key="receiving.label.duplicatekitpkg"/>" name="duplicateButton">
                 </c:if>
                </td>
               </c:if>
          </c:otherwise>
	     </c:choose>
	   </c:when>
	   <c:when test="${receivingPermission == 'Yes'}">
             <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].updateStatus" value="readOnly">
             <td width="2%">&nbsp;</td>
             <td width="8%">&nbsp;</td>
	     <td width="5%">&nbsp;</td>
	     <td width="5%">&nbsp;</td>
	     <td width="5%">&nbsp;</td>
	     <td width="5%">&nbsp;</td>
             <td width="5%">&nbsp;</td>
	     <td width="5%">&nbsp;</td>
	     <td width="5%">&nbsp;</td>
	     <td width="5%">&nbsp;</td>
	     <td width="5%">&nbsp;</td>
	  </c:when>
	  </c:choose>
     <c:set var="dataCount" value='${dataCount+1}'/>
	</c:forEach>
 </tr>
  </c:forEach>
</table>
</c:if>

   <!-- If the collection is empty say no data found -->
   <c:if test="${empty receivingViewRelationBeanCollection}" >
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
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<%--<tcmis:saveRequestParameter/>--%>  
<input name="companyId" id="companyId" type="hidden" value="${param.companyId}">
<input name="action" id="action" type="hidden" value="${param.action}">
<input name="facilityId" id="facilityId" type="hidden" value="${param.facilityId}">
<input name="searchText" id="searchText" type="hidden" value="${param.searchText}">
<input name="searchType" id="searchType" type="hidden" value="${param.searchType}">
<input name="dockId" id="dockId" type="hidden" value="${param.dockId}">
<input name="sortBy" id="sortBy" type="hidden" value="${param.sortBy}">
<input name="searchWhat" id="searchWhat" type="hidden" value="${param.searchWhat}">
<input name="expectedWithin" id="expectedWithin" type="hidden" value="${param.expectedWithin}">
<input name='date60' id='date60' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="-60" datePattern="${dateFormatPattern}"/>'  /> 
<input name='today' id='today' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'  />
<input name='todayoneyear' id='todayoneyear' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="-365" datePattern="${dateFormatPattern}"/>'  />
<input name='indefiniteDate' id='indefiniteDate' type="hidden" value=''  />

<input type="hidden" name="duplicateLine" id="duplicateLine" value="">
<input type="hidden" name="duplicatePkgLine" id="duplicatePkgLine" value="">
<input type="hidden" name="duplicateKitLine" id="duplicateKitLine" value="">
<%--
<input type="hidden" name="receivedReceipts" id="receivedReceipts" value="<c:out value="${param.receivedReceipts}"/>">
--%>

 </div>
<!-- Hidden elements end -->
</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->
</tcmis:form>

</body>
</html:html>