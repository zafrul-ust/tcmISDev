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

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/supplier/packresults.js"></script>

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
updatebeforeprint:"<fmt:message key="label.updatebeforeprint"/>",  
pleaseselectbox:"<fmt:message key="label.pleasemakeselection"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>"
};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad();displaySearchDuration();">

<tcmis:form action="/packresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<c:set var="labelingPermission" value=''/>
<tcmis:supplierLocationPermission indicator="true" userGroupId="labeling" supplierId="${param.supplier}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
  <c:set var="labelingPermission" value='Yes'/>
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
	<c:set var="preItem" value=""/>
  <c:set var="mrLineCount" value='${0}'/>

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:forEach var="suppPackingViewBean" items="${suppPackingViewBeanCollection}" varStatus="status">
    
  <c:set var="labelingPermission" value=''/>
  <tcmis:supplierLocationPermission indicator="true" userGroupId="usGovShipmentSelection" supplierId="${param.supplier}" locationId="${status.current.shipFromLocationId}">
   <c:set var="labelingPermission" value='Yes'/>
  </tcmis:supplierLocationPermission>

    <c:set var="curPar" value="${status.current.shipToLocationId}"/>
	  <c:set var="curItem" value="itemId${status.current.ultimateShipToDodaac}"/>
    <c:choose>
     <c:when test="${!(curItem eq preItem)}">
      <c:set var="mrLineCount" value='${0}'/>
     </c:when>
     <c:otherwise>
      <c:set var="mrLineCount" value='${mrLineCount+1}'/>
     </c:otherwise>
    </c:choose>

    <%--<c:if test="${partDataCount % 5 == 0 && mrLineCount ==0}">--%>
    <c:if test="${dataCount % 10 == 0}">
    <tr>
      <th width="5%"><fmt:message key="label.supplierlocation"/></th>
      <th width="10%"><fmt:message key="label.shipto"/></th>
		  <%--<th width="5%"><fmt:message key="label.ultimatedodaac"/></th>--%>
    	<%--<th width="5%"><fmt:message key="label.mrline"/></th>--%>
		  <th width="7%"><fmt:message key="label.haaspoline"/></th>
      <th width="5%"><fmt:message key="label.deliveryticket"/></th>
      <%--<th width="7%"><fmt:message key="label.desireddates"/></th>--%>
    	<th width="7%"><fmt:message key="label.nsn"/><br>(<fmt:message key="label.item"/>)</th>
    	<th width="7%"><fmt:message key="label.shortname"/></th>
    	<th width="15%"><fmt:message key="label.packaging"/></th>
    	<th width="5%"><fmt:message key="label.qty"/></th>
    	<%--<th width="5%"><fmt:message key="label.expdate"/></th>--%>
    	<th width="5%"><fmt:message key="label.externalid"/></th>
		  <th width="5%"><fmt:message key="label.ok"/>
      <c:if test="${status.index == 0 && labelingPermission == 'Yes'}">
       <br><input type="checkbox" value="" onClick="checkAllOkBoxes('ok_')" name="checkAll" id="checkAll">
      </c:if>
      </th>
    	<th width="5%"><fmt:message key="label.caseid"/></th>
    	<th width="13%"><fmt:message key="label.palletid"/></th>
<%--
   		<th width="5%"><fmt:message key="label.shipdate"/><br/>(<fmt:message key="label.dateformat"/>)</th>
   		<th width="5%"><fmt:message key="label.deliverydate"/><br/>(<fmt:message key="label.dateformat"/>)</th>
    	<th width="5%"><fmt:message key="label.supplier"/> <fmt:message key="label.deliveryticket"/></th>
    	<th width="5%"><fmt:message key="label.carrier"/> <fmt:message key="label.trackingno"/></th>
    	<th width="10%"><fmt:message key="label.carrier"/></th>
--%>
    </tr>
    </c:if>
	   <c:if test="${!(status.current.shipToLocationId eq prePar)}">
   			<c:set var="partDataCount" value='${partDataCount+1}'/>
	   </c:if>
    <c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value='alt'/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value=''/>
     </c:otherwise>
    </c:choose>

	<c:if test="${status.index == 0}">
   <c:set var="lastDodaac" value='${status.current.ultimateShipToDodaac}'/>
	</c:if>

	<c:choose>
   <c:when test="${status.current.ultimateShipToDodaac == lastDodaac}" >

   </c:when>
   <c:otherwise>
    <c:set var="lastDodaac" value='${status.current.ultimateShipToDodaac}'/>
    <c:set var="dodaacCount" value='${dodaacCount+1}'/>
    	<c:choose>
       <c:when test="${dodaacCount % 2 == 0}" >
        <c:set var="dodaacColorClass" value='lightgray'/>
       </c:when>
       <c:otherwise>
        <c:set var="dodaacColorClass" value='green'/>
       </c:otherwise>
      </c:choose>
     <tr align="center">
      <td colspan="15" height="4"class="black"></td>
    </tr>
   </c:otherwise>    
  </c:choose>


       <tr class="${colorClass} alignCenter">
	   <c:if test="${!(status.current.shipToLocationId eq prePar)}">
   			<c:set var="preItem" value=""/>
	     		<%--<td width="10%" rowspan="${rowCountPart[curPar]}" >${status.current.shipToCityCommaState} ${status.current.shipToZipcode}</td>--%>
   	 </c:if>
     <td width="5%">${status.current.shipFromLocationName}</td>
     <td width="10%">${status.current.shipToCityCommaState} ${status.current.shipToZipcode}</td>
     <c:if test="${!(curItem eq preItem)}">
     			<%--<td width="5%" class="${dodaacColorClass}" rowspan="${rowCountItem[curPar][curItem]}" >${status.current.ultimateShipToDodaac}</td>--%>
	   </c:if>
     <%--<td width="5%" class="${dodaacColorClass}">
      <a title="<fmt:message key="label.viewaddress"/>" href="#" onclick="getShipToAddress('${status.current.rliShiptoLocId}', '${status.current.ultimateShipToDodaac}');">
       ${status.current.ultimateShipToDodaac}
      </a>                 
      <c:if test="${!empty status.current.oconus && status.current.oconus=='Y'}" >
        <b>(OCONUS)</b>
      </c:if>
     </td>--%>
           <%--<td width="5%" >${status.current.prNumber}-${status.current.lineItem}</td>--%>
     			 <td width="7%" >${status.current.radianPo}-${status.current.poLine}</td>
           <td width="5%" >${status.current.supplierSalesOrderNo}</td>
           <%--<td width="7%" >
		     	   	<fmt:formatDate var="desireShipDate" value="${status.current.desiredShipDate}" pattern="MM/dd/yyyy" />
     			   	<fmt:formatDate var="desireDeliveryDate" value="${status.current.desiredDeliveryDate}" pattern="MM/dd/yyyy" />
     				${desireShipDate}
					<c:if test="${! empty status.current.desiredDeliveryDate }">
     					(${desireDeliveryDate})
					</c:if>
     			</td>--%>
     			<td width="7%" >
     				${status.current.catPartNo}<br/>(${status.current.itemId})
     			</td>
     			<td width="7%" class="alignLeft">${status.current.partShortName}</td>
     			<td width="15%" class="alignLeft">${status.current.packaging}</td>
     			<td width="5%" >${status.current.quantity}</td>
     			<%--<td width="5%">${status.current.expireDate}</td>--%>
     			<td width="5%" >
     				<input type="hidden" name="supplierPackingViewBean[${status.index}].boxLabelId" id="boxLabelId_${status.index}" value="${status.current.boxLabelId}"/>
     				${status.current.boxLabelId}
     			</td>

           <td width="5%">
           <c:choose>
           <c:when test="${labelingPermission == 'Yes'}">
             <input type="checkbox" name="supplierPackingViewBean[${status.index}].ok" id="ok_${status.index}" value="ok" onClick="okCheck(${status.index})"/>
           </c:when>
           <c:otherwise>
             <input type="checkbox" name="supplierPackingViewBean[${status.index}].ok" id="ok_${status.index}" value="" onClick="okCheck(${status.index})"  style="display: none;"/>
           </c:otherwise>
           </c:choose>
            <input type="hidden" name="supplierPackingViewBean[${status.index}].ultimateShipToDodaac" id="ultimateShipToDodaac_${status.index}" value="${status.current.ultimateShipToDodaac}"/>
 			   		<input type="hidden" name="supplierPackingViewBean[${status.index}].quantity" id="quantity_${status.index}" value="${status.current.quantity}"/>
 			   		<input type="hidden" name="supplierPackingViewBean[${status.index}].packaging" id="packaging_${status.index}" value="${status.current.packaging}"/>
 			   		<input type="hidden" name="supplierPackingViewBean[${status.index}].prNumber_lineItem" id="prNumber_lineItem_${status.index}" value="${status.current.prNumber}-${status.current.lineItem}"/>
            <input type="hidden" name="supplierPackingViewBean[${status.index}].prNumber" id="supplierPackingViewBean[${status.index}].prNumber" value="${status.current.prNumber}"/>
            <input type="hidden" name="supplierPackingViewBean[${status.index}].lineItem" id="supplierPackingViewBean[${status.index}].lineItem" value="${status.current.lineItem}"/>
           </td>

           <td width="5%" >
             <input type="hidden" class="inputBox" name="supplierPackingViewBean[${status.index}].boxId" id="boxId_${status.index}" value="${status.current.boxId}" size="7" onchange="getSequnceId('boxId_${status.index}')"/>
             ${status.current.boxId}
     			 </td>
     			 <td width="13%" >
              <input type="hidden" class="inputBox" name="supplierPackingViewBean[${status.index}].palletId" id="palletId_${status.index}" value="${status.current.palletId}" size="7" onchange="getSequnceId('palletId_${status.index}')"/>
            ${status.current.palletId}
				   </td>

      <fmt:formatDate var="formattedDateShipped" value="${status.current.dateShipped}" pattern="${dateFormatPattern}"/>
      <fmt:formatDate var="formattedDateDelivered" value="${status.current.dateDelivered}" pattern="${dateFormatPattern}"/>
		<input type="hidden" name="supplierPackingViewBean[${status.index}].dateShipped" id="dateShipped_${status.index}" value="${formattedDateShipped}"/>
 	  	<input type="hidden" name="supplierPackingViewBean[${status.index}].dateDelivered" id="dateDelivered_${status.index}" value="${formattedDateDelivered}"/>
		<input type="hidden" name="supplierPackingViewBean[${status.index}].deliveryTicket" id="deliveryTicket_${status.index}" value="${status.current.deliveryTicket}"/>
     	<input type="hidden" name="supplierPackingViewBean[${status.index}].trackingNumber" id="trackingNumber_${status.index}" value="${status.current.trackingNumber}"/>
     	<input type="hidden" name="supplierPackingViewBean[${status.index}].carrierName" id="carrierName_${status.index}" value="${status.current.carrierName}"/>
<%--
     	<td width="5%" nowrap="nowrap">
	     	<c:if test="${empty readonly}">
    	 		<input type="text" class="inputBox" name="supplierPackingViewBean[${status.index}].dateShipped" id="dateShipped_${status.index}" value="${formattedDateShipped}" size="8" onchange="valueChanged('dateShipped_${status.index}')"/>
	    	    <a href="#" id="linkdateShipped_${status.index}" onClick="return getCalendar(document.getElementById('dateShipped_${status.index}'),null,null,null,document.getElementById('dateDelivered_${status.index}'));" style="color:#0000ff;cursor:pointer;text-decoration:underline">&diams;</a>
     		</c:if>
			<c:if test="${!empty readonly}">
				${status.current.vendorShipDate}
			</c:if>
	    </td>
     	<td width="5%" nowrap="nowrap">
 	     	<c:if test="${empty readonly}">
	   			<input type="text" class="inputBox" name="supplierPackingViewBean[${status.index}].dateDelivered" id="dateDelivered_${status.index}" value="${formattedDateDelivered}" size="8" onchange="valueChanged('dateDelivered_${status.index}')"/>
		        <a href="#" id="linkdateDelivered_${status.index}" onClick="return getCalendar(document.getElementById('dateDelivered_${status.index}'),null,null,document.getElementById('dateShipped_${status.index}'),null);" style="color:#0000ff;cursor:pointer;text-decoration:underline">&diams;</a>
     		</c:if>
			<c:if test="${!empty readonly}">
				${status.current.receiptDate}
			</c:if>

     	</td>

     			<td width="5%" >

     	<c:if test="${empty readonly}">
     				<input type="text" class="inputBox" name="supplierPackingViewBean[${status.index}].deliveryTicket" id="deliveryTicket_${status.index}" value="${status.current.deliveryTicket}" size="7" onchange="valueChanged('deliveryTicket_${status.index}')"/>
		</c:if>
		<c:if test="${!empty readonly}">
					${status.current.deliveryTicket}
		</c:if>

     	</td>
     			<td width="5%" >
     	<c:if test="${empty readonly}">
     				<input type="text" class="inputBox" name="supplierPackingViewBean[${status.index}].trackingNumber" id="trackingNumber_${status.index}" value="${status.current.trackingNumber}" size="7" onchange="valueChanged('trackingNumber_${status.index}')"/>
		</c:if>
		<c:if test="${!empty readonly}">
					${status.current.trackingNumber}
		</c:if>
     	</td>
     			<td width="10%" >
     				<select class="selectBox" name="supplierPackingViewBean[${status.index}].carrierName" id="carrierName_${status.index}" onchange="valueChanged('carrierName_${status.index}')">
					    <option value=""><fmt:message key="label.pleaseselect"/></option>
               <c:forEach var="noUse" items="${carrierBeanColl}" varStatus="carrierStatus">
					    	<c:set var="selected" value=""/>
							<c:if test="${status.current.carrierName eq carrierStatus.current.carrierName}">
						    	<c:set var="selected" value="selected='selected'"/>
							</c:if>
    						<option value="${carrierStatus.current.carrierName}" ${selected}>${carrierStatus.current.carrierName}</option>
						</c:forEach>
     				</select>
     			</td>
--%>
	</tr>
	<c:set var="prePar" value="${status.current.shipToLocationId}"/>
	<c:set var="preItem" value="itemId${status.current.ultimateShipToDodaac}"/>

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
<input type="hidden" name="paperSize" id="paperSize" value="33"/>
<input type="hidden" name="dataChanged" id="dataChanged" value=""/>
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<%--<tcmis:saveRequestParameter/>--%>
<input name="supplier" id="supplier" type="hidden" value="${param.supplier}">
<input name="shipFromLocationId" id="shipFromLocationId" type="hidden" value="${param.shipFromLocationId}">
<input name="oldshipFromLocationId" id="oldshipFromLocationId" type="hidden" value="${param.oldshipFromLocationId}">
<input name="oldsupplier" id="oldsupplier" type="hidden" value="${param.oldsupplier}">
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