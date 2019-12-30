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

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/supplier/confirmresults.js"></script>

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
<fmt:message key="label.packconfirm"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--

var requiredTitle = new Array (
    	"<fmt:message key="label.caseid"/>",
    	"<fmt:message key="label.supplier"/> <fmt:message key="label.deliveryticket"/>",
    	"<fmt:message key="label.carrier"/> <fmt:message key="label.trackingno"/>",
    	"<fmt:message key="label.carrier"/>",
  		"<fmt:message key="label.shipdate"/>",
   		"<fmt:message key="label.deliverydate"/>"
);

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
itemInteger:"<fmt:message key="error.item.integer"/>",
shipltdelivery:"<fmt:message key="label.shipltdelivery"/>",
domltexp:"<fmt:message key="label.domltexp"/>",
fieldsrequired:"<fmt:message key="label.fieldrequired"/>",
casetopallet:"<fmt:message key="label.casetopallet"/>",
casetomr:"<fmt:message key="label.casetomr"/>",
pallettoship:"<fmt:message key="label.pallettoship"/>",
pallettodelivery:"<fmt:message key="label.pallettodelivery"/>",
pallettoticket:"<fmt:message key="label.pallettoticket"/>",
pallettododaac:"<fmt:message key="label.pallettododaac"/>",
tickettotracking:"<fmt:message key="label.tickettotracking"/>",
trackingtocarrier:"<fmt:message key="label.trackingtocarrier"/>",
validValues:"<fmt:message key="msg.errorHeader"/>",
pleaseselectbox:"<fmt:message key="label.pleasemakeselection"/>"
};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">

<tcmis:form action="/confirmresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->

<c:set var="confirmPermission" value=''/>
<tcmis:supplierLocationPermission indicator="true" userGroupId="usGovShipConfirm" supplierId="${param.supplier}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
  /*parent.document.getElementById("createLink").style["display"]="none";*/
  <c:set var="confirmPermission" value='Yes'/>
 //-->
</script>
</tcmis:supplierLocationPermission>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
 ${tcmISError}
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null and empty requestScope['tcmISError']}">
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

<c:if test="${suppPackingViewBeanCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:set var="dodaacColorClass" value='lightgray'/>
<c:set var="dodaacCount" value='${0}'/>
<c:set var="lastDodaac" value=''/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty suppPackingViewBeanCollection}" >

	<c:set var="partDataCount" value='${0}'/>
	<c:set var="prePar" value=""/>
  <c:set var="mrLineCount" value='${0}'/>

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:forEach var="suppPackingViewBean" items="${suppPackingViewBeanCollection}" varStatus="status">

    <c:set var="confirmPermission" value=''/>
    <tcmis:supplierLocationPermission indicator="true" userGroupId="usGovShipConfirm" supplierId="${param.supplier}" locationId="${status.current.shipFromLocationId}">
     <c:set var="confirmPermission" value='Yes'/>
    </tcmis:supplierLocationPermission>

    <c:set var="curPar" value="${status.current.radianPo}^^${status.current.usgovTcn}"/>
<%--    <c:choose>
     <c:when test="${!empty status.current.currentCarrierName}" >
       <c:set var="curPar" value="${status.current.radianPo}^^${status.current.usgovTcn}"/>
     </c:when>
     <c:otherwise>
      <c:set var="curPar" value="${status.current.shipViaLocationId}^^${status.current.ultimateDodaac}^^${status.current.supplierSalesOrderNo}"/>
     </c:otherwise>
    </c:choose>--%>

    <c:choose>
     <c:when test="${!(curPar eq prePar)}">
      <c:set var="partDataCount" value='${0}'/>
       <c:set var="mrLineCount" value='${mrLineCount+1}'/>
     </c:when>
     <c:otherwise>
      <c:set var="partDataCount" value='${partDataCount+1}'/>
     </c:otherwise>
    </c:choose>

    <%--<c:set var="curPar" value="${status.current.shipViaLocationId}^^${status.current.ultimateDodaac}^^${status.current.supplierSalesOrderNo}"/>--%>
    <%--<c:if test="${partDataCount % 5 == 0 && mrLineCount ==0}">--%>
    <c:if test="${(dataCount % 10 == 0 && partDataCount==0) || dataCount ==0}">
    <tr>
      <th width="5%"><fmt:message key="label.supplierlocation"/></th>
    	<th width="5%"><fmt:message key="label.shipto"/></th>
		  <th width="5%"><fmt:message key="label.ultimatedodaac"/></th>
    	<th width="5%"><fmt:message key="label.deliveryticket"/></th>
    	<th width="3%"><fmt:message key="label.haaspo"/></th>
    	<th width="7%"><fmt:message key="label.nsn"/></th>
    	<th width="10%"><fmt:message key="label.shortname"/></th>
      <th width="5%"><fmt:message key="label.tcn"/></th>
      <th width="10%"><fmt:message key="label.carrier"/></th>
      <th width="3%"><fmt:message key="label.qty"/></th>
    	<th width="5%"><fmt:message key="label.boxid"/></th>
    	<th width="5%"><fmt:message key="label.trackingno"/></th>
      <th width="4%"><fmt:message key="label.shipmentid"/></th>
<%--
  		<th width="5%"><fmt:message key="label.shipdate"/><br/>(<fmt:message key="label.dateformat"/>)</th>
   		<th width="5%"><fmt:message key="label.arrivaldate"/><br/>(<fmt:message key="label.dateformat"/>)</th>
 		<th width="7%"><fmt:message key="label.haaspoline"/></th>
    	<th width="7%"><fmt:message key="label.desireddates"/></th>
    	<th width="7%"><fmt:message key="label.partno"/></th>
    	<th width="5%"><fmt:message key="label.expdate"/></th>
    	<th width="5%"><fmt:message key="label.externalid"/></th>
    	<th width="5%"><fmt:message key="label.caseid"/></th>
 --%>
    </tr>
    </c:if>

    <c:choose>
     <c:when test="${mrLineCount % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>

	<c:if test="${status.index == 0}">
   <c:set var="lastDodaac" value='${status.current.ultimateDodaac}'/>
	</c:if>

	<c:choose>
   <c:when test="${status.current.ultimateDodaac == lastDodaac}" >

   </c:when>
   <c:otherwise>
    <c:set var="lastDodaac" value='${status.current.ultimateDodaac}'/>
    <c:set var="dodaacCount" value='${dodaacCount+1}'/>
    	<c:choose>
       <c:when test="${dodaacCount % 2 == 0}" >
        <c:set var="dodaacColorClass" value='lightgray'/>
       </c:when>
       <c:otherwise>
        <c:set var="dodaacColorClass" value='green'/>
       </c:otherwise>
      </c:choose>
   </c:otherwise>
  </c:choose>
  

       <tr class="${colorClass} alignCenter">
	   <c:if test="${!(curPar eq prePar)}">
      <td width="5%" rowspan="${rowCountPart[curPar]}"> ${status.current.shipFromLocationName} </td>

			<td width="5%" rowspan="${rowCountPart[curPar]}">
			     ${status.current.shipToCityCommaState} ${status.current.shipToZipcode}
			</td>
		    <td width="5%" class="${dodaacColorClass}" rowspan="${rowCountPart[curPar]}">
		    	<input type="hidden" name="supplierPackingViewBean[${status.index}].ultimateDodaac" id="ultimateDodaac_${status.index}" value="${status.current.ultimateDodaac}"/>
		    	${status.current.ultimateDodaac}
		    </td>
			<td width="5%" rowspan="${rowCountPart[curPar]}">
     				<input type="hidden" name="supplierPackingViewBean[${status.index}].supplierSalesOrderNo" id="supplierSalesOrderNo_${status.index}" value="${status.current.supplierSalesOrderNo}"/>
					${status.current.supplierSalesOrderNo}
			</td>
			<td width="3%" rowspan="${rowCountPart[curPar]}">
					${status.current.radianPo}
			</td>
			<td width="5%" rowspan="${rowCountPart[curPar]}">
     				<input type="hidden" name="supplierPackingViewBean[${status.index}].catPartNo" id="catPartNo_${status.index}" value="${status.current.catPartNo}"/>
					${status.current.catPartNo}
			</td>
			<td width="5%" rowspan="${rowCountPart[curPar]}">
   					<input type="hidden" name="supplierPackingViewBean[${status.index}].partShortName" id="partShortName_${status.index}" value="${status.current.partShortName}"/>
   					${status.current.partShortName}
   	  </td>
      <td width="5%" rowspan="${rowCountPart[curPar]}">
   				${status.current.usgovTcn}
   		</td>
      <td width="5%" rowspan="${rowCountPart[curPar]}">
      <c:choose>
       <c:when test="${confirmPermission == 'Yes'}">
        <select class="selectBox" name="supplierPackingViewBean[${status.index}].currentCarrierName" id="currentCarrierName_${status.index}" onchange="valueChanged('currentCarrierName_${status.index}')">
          <option value=""><fmt:message key="label.pleaseselect"/></option>
                <c:forEach var="noUse" items="${carrierBeanColl}" varStatus="carrierStatus">
            <c:set var="selected" value=""/>
          <c:if test="${status.current.currentCarrierName eq carrierStatus.current.carrierName}">
              <c:set var="selected" value="selected='selected'"/>
          </c:if>
            <option value="${carrierStatus.current.carrierName}" ${selected}>${carrierStatus.current.carrierName}</option>
        </c:forEach>
        </select>
      </c:when>
      <c:otherwise>
        <input type="hidden" name="supplierPackingViewBean[${status.index}].currentCarrierName" id="currentCarrierName_${status.index}" value="${status.current.currentCarrierName}"/>
        ${status.current.currentCarrierName}
      </c:otherwise>
      </c:choose>
      </td>
      </c:if>
      <td width="3%">
   				<input type="hidden" name="supplierPackingViewBean[${status.index}].radianPo" id="radianPo_${status.index}" value="${status.current.radianPo}"/>
   				<input type="hidden" name="supplierPackingViewBean[${status.index}].usgovTcn" id="usgovTcn_${status.index}" value="${status.current.usgovTcn}"/>
   				<input type="hidden" name="supplierPackingViewBean[${status.index}].quantity" id="quantity_${status.index}" value="${status.current.quantity}"/>
		      <input type="hidden" name="supplierPackingViewBean[${status.index}].shipViaCompanyId" id="shipViaCompanyId_${status.index}" value="${status.current.shipViaCompanyId}"/>
		    	<input type="hidden" name="supplierPackingViewBean[${status.index}].shipViaLocationId" id="shipViaLocationId_${status.index}" value="${status.current.shipViaLocationId}"/>
          <input type="hidden" name="supplierPackingViewBean[${status.index}].customerPoNo" id="customerPoNo_${status.index}" value="${status.current.customerPoNo}"/>
           ${status.current.quantity}
   			</td>
			<td width="5%">
   				<input type="hidden" name="supplierPackingViewBean[${status.index}].boxId" id="boxId_${status.index}" value="${status.current.boxId}"/>
   				${status.current.boxId}
   			</td>
     		<td width="5%" >
     		<c:choose>
         <c:when test="${confirmPermission == 'Yes'}">
     			<input type="text" class="inputBox" name="supplierPackingViewBean[${status.index}].currentTrackingNumber" id="currentTrackingNumber_${status.index}" value="${status.current.currentTrackingNumber}" size="7" onchange="valueChanged('currentTrackingNumber_${status.index}')"/>
          <input type="hidden" name="supplierPackingViewBean[${status.index}].shipFromLocationId" id="shipFromLocationId_${status.index}" value="${status.current.shipFromLocationId}"/>
         </c:when>
				 <c:otherwise>
     				<input type="hidden" name="supplierPackingViewBean[${status.index}].currentTrackingNumber" id="currentTrackingNumber_${status.index}" value="${status.current.currentTrackingNumber}"/>
            <input type="hidden" name="supplierPackingViewBean[${status.index}].shipFromLocationId" id="shipFromLocationId_${status.index}" value="${status.current.shipFromLocationId}"/>
          ${status.current.currentTrackingNumber}
				 </c:otherwise>
        </c:choose>
     		</td>
         <td width="4%">
         <input type="hidden" name="supplierPackingViewBean[${status.index}].currentShipmentId" id="currentShipmentId_${status.index}" value="${status.current.currentShipmentId}"/>
         ${status.current.currentShipmentId}
         </td>
  </tr>
    <c:set var="prePar" value="${status.current.radianPo}^^${status.current.usgovTcn}"/>
<%--    <c:choose>
     <c:when test="${!empty status.current.currentCarrierName}" >
       <c:set var="prePar" value="${status.current.shipViaLocationId}^^${status.current.ultimateDodaac}^^${status.current.supplierSalesOrderNo}${status.current.currentCarrierName}"/>
     </c:when>
     <c:otherwise>
      <c:set var="prePar" value="${status.current.shipViaLocationId}^^${status.current.ultimateDodaac}^^${status.current.supplierSalesOrderNo}"/>
     </c:otherwise>
    </c:choose>--%>
    <c:set var="dataCount" value='${dataCount+1}'/>

   </c:forEach>
   </table>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty suppPackingViewBeanCollection}" >

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
<input name="userAction" id="userAction" value="" type="hidden"/>
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden"/>
<input type="hidden" name="dataChanged" id="dataChanged" value=""/>
<input name="supplier" id="supplier" type="hidden" value="${param.supplier}">
<input name="oldshipFromLocationId" id="oldshipFromLocationId" type="hidden" value="${param.oldshipFromLocationId}">
<input name="oldsupplier" id="oldsupplier" type="hidden" value="${param.oldsupplier}">
<input name="transportationMode" id="transportationMode" type="hidden" value="${param.transportationMode}">
<input name="searchArgument" id="searchArgument" type="hidden" value="${param.searchArgument}">
<input name="searchMode" id="searchMode" type="hidden" value="${param.searchMode}">
<input name="searchField" id="searchField" type="hidden" value="${param.searchField}">
<c:set var="selectedsuppLocationIdArray" value='${paramValues.suppLocationIdArray}'/>
<select name="suppLocationIdArray" id="suppLocationIdArray" class="selectBox" multiple size="5">
<c:forEach items="${selectedsuppLocationIdArray}" varStatus="status1">
 <c:set var="selectedLocation" value='${selectedsuppLocationIdArray[status1.index]}'/>
  <option value="<c:out value="${selectedLocation}"/>" selected><c:out value="${selectedLocation}" escapeXml="false"/></option>
</c:forEach>  
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>