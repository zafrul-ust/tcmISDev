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
<script type="text/javascript" src="/js/supplier/usgovlabeling.js"></script>

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
<fmt:message key="label.usgovlabeling"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
pleasemakeselection:"<fmt:message key="label.pleasemakeselection"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>",
snpalletcasecapture:"<fmt:message key="error.snpalletcasecapture"/>",
};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">

<tcmis:form action="/printunitboxlabels.do" target="printUnitBoxLabels">

<c:set var="labelingPermission" value=''/>
<c:set var="supplierIdParam"><tcmis:jsReplace value="${param.supplier}" /></c:set>
<c:set var="shipFromLocationIdParam"><tcmis:jsReplace value="${param.shipFromLocationId}" /></c:set>
<tcmis:supplierLocationPermission indicator="true" userGroupId="labeling" supplierId="${supplierIdParam}" locationId="${shipFromLocationIdParam}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
  <c:set var="labelingPermission" value='Yes'/>
 //-->
</script>
</tcmis:supplierLocationPermission>

<c:set var="suppLocReversePermission" value=''/>
<tcmis:supplierLocationPermission indicator="true" userGroupId="usGovShipmentSelection" supplierId="${supplierIdParam}" locationId="${shipFromLocationIdParam}">
 <c:set var="suppLocReversePermission" value='Yes'/>
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
    <c:when test="${empty tcmISErrors and empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage"> <!-- start of interface -->
<div class="backGroundContent"> <!-- start of backGroundContent -->

<c:if test="${suppPackSummaryViewBeanCollection != null}" >

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:set var="dodaacColorClass" value='lightgray'/>
<c:set var="dodaacCount" value='${0}'/>
<c:set var="lastDodaac" value=''/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<c:if test="${!empty suppPackSummaryViewBeanCollection}" >

	<c:set var="partDataCount" value='${0}'/>
	<c:set var="prePar" value=""/>
	<c:set var="preItem" value=""/>
  <c:set var="mrLineCount" value='${0}'/>

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:forEach var="pageNameViewBean" items="${suppPackSummaryViewBeanCollection}" varStatus="status">

   <c:set var="curPar" value="${status.current.shipToLocationId}${status.current.picklistId}"/>
	 <c:set var="curItem" value="itemId${status.current.ultimateShipToDodaac}"/>
    <c:choose>
     <c:when test="${(curPar eq prePar)}">
       <c:set var="mrLineCount" value='${mrLineCount+1}'/>
     </c:when>
     <c:otherwise>
      <c:set var="mrLineCount" value='${0}'/>
     </c:otherwise>
    </c:choose>

    <c:if test="${partDataCount % 5 == 0 && mrLineCount ==0}">
    <tr>
        <th width="5%"><fmt:message key="label.supplierlocation"/></th>
        <th width="10%"><fmt:message key="label.shipto"/></th>
        <th width="7%"><fmt:message key="label.packingid"/></th>
 			  <th width="5%"><fmt:message key="label.ultimatedodaac"/></th>
    		<th width="2%"><fmt:message key="label.print"/></th>
    		  <th width="2%"><fmt:message key="label.skipunitlabels"/></th>
        <th width="2%"><fmt:message key="label.priority"/></th>
    		<%--<th width="5%"><fmt:message key="label.mrline"/></th>--%>
 			  <th width="5%"><fmt:message key="label.haaspoline"/></th>
        <th width="5%"><fmt:message key="label.deliveryticket"/></th>
        <th width="5%"><fmt:message key="label.nsn"/><br>(<fmt:message key="label.item"/>)</th>
    		<th width="25%"><fmt:message key="label.description"/></th>
    		<th width="3%"><fmt:message key="label.qty"/></th>
    		<th width="3%"><fmt:message key="label.numofboxes"/></th>
    		<th width="3%"><fmt:message key="label.numofpallets"/></th>
    		<th width="2%"><fmt:message key="label.numofserialnumberscaptured"/></th>
        <th width="2%"><fmt:message key="label.serialnumbersrequired"/></th>

    </tr>
    </c:if>

    <c:choose>
     <c:when test="${partDataCount % 2 == 0}" >
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
   </c:otherwise>
  </c:choose>


       <tr class="${colorClass} alignCenter">
	   <c:if test="${!(curPar eq prePar)}">
   			<c:set var="partDataCount" value='${partDataCount+1}'/>
   			<c:set var="preItem" value=""/>
         <td width="5%" rowspan="${rowCountPart[curPar]}">${status.current.shipFromLocationName}</td>
         <td width="10%" rowspan="${rowCountPart[curPar]}">${status.current.shipToCityCommaState} ${status.current.shipToZipcode}</td>
        <td width="7%" rowspan="${rowCountPart[curPar]}">
          <fmt:formatDate var="picklistPrintDate" value="${status.current.picklistPrintDate}" pattern="EEE MMM dd yyyy HH:mm a zzz" />
    			${picklistPrintDate}
        </td>
   	   </c:if>
	   <c:if test="${ !( curItem eq preItem)}">
     			<td width="5%" class="${dodaacColorClass}" rowspan="${rowCountItem[curPar][curItem]}" >
     			 <a title="<fmt:message key="label.viewaddress"/>" href="#" onclick="getShipToAddress('${status.current.rliShiptoLocId}', '${status.current.ultimateShipToDodaac}');">
            ${status.current.ultimateShipToDodaac}
           </a>
           <c:if test="${!empty status.current.oconus && status.current.oconus=='Y'}" >
            <b>(OCONUS)</b> 
           </c:if>
          </td>
         <c:set var="showItem" value=""/>
	   </c:if>
   			   	<td>
              <c:choose>
               <c:when test="${labelingPermission == 'Yes'}">
                <input type="checkbox" name="labelBean[${status.index}].ok" id="ok${status.index}" value="ok"/>
               </c:when>
               <c:otherwise>
                  &nbsp;
               </c:otherwise>
              </c:choose>
              <input type="hidden" name="labelBean[${status.index}].prNumber" id="labelBean[${status.index}].prNumber" value="${status.current.prNumber}"/>
   			   		<input type="hidden" name="labelBean[${status.index}].lineItem" id="labelBean[${status.index}].lineItem" value="${status.current.lineItem}"/>
   			   		<input type="hidden" name="labelBean[${status.index}].picklistId" id="labelBean[${status.index}].picklistId" value="${status.current.picklistId}"/>
              <input type="hidden" name="labelBean[${status.index}].receiptId" id="labelBean[${status.index}].receiptId" value="${status.current.receiptId}"/>
              <input type="hidden" name="labelBean[${status.index}].radianPo" id="radianPo${status.index}" value="${status.current.radianPo}"/>
              <input type="hidden" name="labelBean[${status.index}].poLine" id="poLine${status.index}" value="${status.current.poLine}"/>
              </td>
		 <td width="2%">
           <c:set var="checked" value=""/>
           <c:if test="${status.current.shippedAsSingle != null && status.current.shippedAsSingle == 'Y'}" >
            <c:set var="checked" value="checked"/>
           </c:if>
           <c:choose>
           <c:when test="${labelingPermission == 'Yes'}">
            <input type="checkbox" name="labelBean[${status.index}].skipUnitLabels" id="skipUnitLabels${status.index}" value="skipUnitLabels" ${checked}/>
           </c:when>
           <c:otherwise>
              &nbsp;
           </c:otherwise>
          </c:choose>            
         </td>
          <td width="2%" >${status.current.priorityRating}</td>
          <%--<td width="5%" >${status.current.prNumber}-${status.current.lineItem}</td>--%>
          <td width="5%" >
           <c:choose>
             <c:when test="${suppLocReversePermission == 'Yes' && status.current.inOutboundAsn != 'Y'}">
              <a href=javascript:undoMrLine(${status.index})>${status.current.radianPo}-${status.current.poLine}</a>
             </c:when>
             <c:otherwise>
               ${status.current.radianPo}-${status.current.poLine}
             </c:otherwise>
          </c:choose>           
          </td>
          <td width="5%" >${status.current.supplierSalesOrderNo}<input type="hidden" name="labelBean[${status.index}].supplierSalesOrderNo" id="supplierSalesOrderNo${status.index}" value="${status.current.supplierSalesOrderNo}"/></td>
          <td width="5%" >${status.current.catPartNo}<br/>(${status.current.itemId})</td>
     			<td width="25%" class="alignLeft">${status.current.itemDesc}</td>
     			<td>${status.current.quantity}</td><input type="hidden" id="quantity${status.index}" value="${status.current.quantity}"/>
     			<td>${status.current.numberOfBoxes}<input type="hidden" id="numberOfBoxes${status.index}" value="${status.current.numberOfBoxes}"/></td>
     			<td>${status.current.numOfPalletId}<input type="hidden" id="numOfPalletId${status.index}" value="${status.current.numOfPalletId}"/></td>
     			<td>${status.current.numOfSerialNumber}<input type="hidden" id="numOfSerialNumber${status.index}" value="${status.current.numOfSerialNumber}"/></td>
                  <td class="alignCenter"><input type="checkbox" disabled="disabled"  <c:if test="${status.current.trackSerialNumber == 'Y'}">checked</c:if> />
         <input type="hidden" name="labelBean[${status.index}].trackSerialNumber" id="trackSerialNumber${status.index}" value="${status.current.trackSerialNumber}"/></td>
           <%-- ${status.current.expireDate}externalRfid,palletRfid,trackingNumber,carrierName
     			--%>
	</tr>
	<c:set var="prePar" value="${status.current.shipToLocationId}${status.current.picklistId}"/>
	<c:set var="preItem" value="itemId${status.current.ultimateShipToDodaac}"/>

   <c:set var="dataCount" value='${dataCount+1}'/>

   </c:forEach>
   </table>
   </c:if>
   <c:if test="${empty suppPackSummaryViewBeanCollection}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
    </c:if>
    </c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
 <script type="text/javascript">
 <!--
 	var totalRow = <c:out value="${dataCount}"/> ;
 //-->
 </script>
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden">
<input type="hidden" name="_action" id="_action" value="update"/>
<input type="hidden" name="paperSize" id="paperSize" value="33"/>

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<%--<tcmis:saveRequestParameter/>  --%>
  <input name="supplier" id="supplier" type="hidden" value="${supplierIdParam}">
  <input name="picklistId" id="picklistId" type="hidden" value="<tcmis:jsReplace value="${param.picklistId}" />" />
  <input name="shipFromLocationId" id="shipFromLocationId" type="hidden" value="${shipFromLocationIdParam}" />" />
  <input name="shpSelpicklistId" id="shpSelpicklistId" type="hidden" value="<tcmis:jsReplace value="${param.shpSelpicklistId}" />" />
  <input name="oldshipFromLocationId" id="oldshipFromLocationId" type="hidden" value="<tcmis:jsReplace value="${param.oldshipFromLocationId}" />" />
  <input name="oldpicklistId" id="oldpicklistId" type="hidden" value="<tcmis:jsReplace value="${param.oldpicklistId}" />" />
  <input name="oldsupplier" id="oldsupplier" type="hidden" value="<tcmis:jsReplace value="${param.oldsupplier}" />" />
  <input name="searchArgument" id="searchArgument" type="hidden" value="<tcmis:jsReplace value="${param.searchArgument}" />" />
  <input name="searchMode" id="searchMode" type="hidden" value="<tcmis:jsReplace value="${param.searchMode}" />" />
  <input name="searchField" id="searchField" type="hidden" value="<tcmis:jsReplace value="${param.searchField}" />" />
  <c:set var="selectedsuppLocationIdArray" value='${paramValues.suppLocationIdArray}'/>
  <select name="suppLocationIdArray" id="suppLocationIdArray" class="selectBox" multiple size="5">
  <c:forEach items="${selectedsuppLocationIdArray}" varStatus="status1">
   <c:set var="selectedLocation" value='${selectedsuppLocationIdArray[status1.index]}'/>
    <option value="<c:out value="${selectedLocation}"/>" selected><tcmis:jsReplace value="${selectedLocation}"/></option>
  </c:forEach>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>
