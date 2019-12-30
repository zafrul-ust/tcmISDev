<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss/>
<!-- CSS for YUI -->
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

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
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/supplier/usgovshpsel.js"></script>

<title>
<fmt:message key="label.usgovselection"/>
</title>

<script language="JavaScript" type="text/javascript">
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = {
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		validvalues:"<fmt:message key="label.validvalue"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		itemInteger:"<fmt:message key="label.errorinteger"/>",
		dateformat:"<fmt:message key="label.dateformat"/>",
		required:"<fmt:message key="label.validvalues"/>",
		recordFound:"<fmt:message key="label.recordFound"/>",
		searchDuration:"<fmt:message key="label.searchDuration"/>",
		minutes:"<fmt:message key="label.minutes"/>",
		seconds:"<fmt:message key="label.seconds"/>"
	};
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">

<tcmis:form action="/usgovshpselresults.do" onsubmit="return submitFrameOnlyOnce();">
<c:set var="receivingPermission" value=''/>
<c:set var="supplierParamRep"><tcmis:jsReplace value='${param.supplier}' /></c:set>
<tcmis:supplierLocationPermission indicator="true" userGroupId="usGovShipmentSelection" supplierId="${supplierParamRep}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
  <c:set var="receivingPermission" value='Yes'/>
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
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null and empty requestScope['tcmISError']}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
</script>
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage"> <!-- start of interface -->
<div class="backGroundContent"> <!-- start of backGroundContent -->

<c:if test="${suppliershippingViewBeanCollection != null}" >

<c:set var="colorClass" value=''/>
<c:set var="dodaacColorClass" value='lightgray'/>
<c:set var="dodaacCount" value='${0}'/>
<c:set var="lastDodaac" value=''/>
<c:set var="dataCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<c:if test="${!empty suppliershippingViewBeanCollection}" >

 <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
	<c:forEach var="suppliershippingViewBean" items="${suppliershippingViewBeanCollection}" varStatus="status">

  <c:set var="supplierLocPermission" value=''/>
  <tcmis:supplierLocationPermission indicator="true" userGroupId="usGovShipmentSelection" supplierId="${supplierParamRep}" locationId="${status.current.shipFromLocationId}">
   <c:set var="supplierLocPermission" value='Yes'/>
  </tcmis:supplierLocationPermission>

	<c:if test="${status.current.shipToLocationId == '10023092'}">
    <c:set var="supplierLocPermission" value=''/>
  </c:if>

  <c:if test="${status.index % 2 == 0}">
		<tr>
 			<th width="5%"><fmt:message key="label.supplierlocation"/></th>
    		<th width="12%"><fmt:message key="label.shipto"/></th>
    		<th width="5%"><fmt:message key="label.ultimatedodaac"/></th>
        	<th width="2%"><fmt:message key="label.priority"/></th>
 			<th nowrap width="5%"><fmt:message key="label.haaspoline"/></th>
    		<th width="5%"><fmt:message key="label.desireddates"/></th>
    		<th width="5%"><fmt:message key="label.nsn"/><br/>(<fmt:message key="label.item"/>)<br/>(<fmt:message key="label.milstrip"/>)</th>
    		<th width="20%"><fmt:message key="label.description"/></th>
    		<th width="7%"><fmt:message key="label.openqty"/></th>
    		<th width="7%"><fmt:message key="label.availableqty"/></th>
        <th width="3%"><fmt:message key="label.unitprice"/></th>
        <th width="12%"><fmt:message key="label.packaging"/></th>
	<c:if test="${receivingPermission == 'Yes'}">
    		<th width="5%"><fmt:message key="label.partsperbox"/></th>
    		<th width="5%"><fmt:message key="label.allocateorder"/></th>
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

  <c:if test="${status.index == 0}">
   <c:set var="lastDodaac" value='${status.current.application}'/>
	</c:if>

	<c:choose>
   <c:when test="${status.current.application == lastDodaac}" >

   </c:when>
   <c:otherwise>
    <c:set var="lastDodaac" value='${status.current.application}'/>
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

    <tr class="${colorClass} alignCenter" id="rowId${status.index}">

     	<td width="5%">${status.current.shipFromLocationName}</td>
     	<td width="12%">${status.current.locationDesc}<br/>${status.current.city}, ${status.current.stateAbbrev} ${status.current.zip}</td>
     	<td class="${dodaacColorClass}" width="5%">
         <a title="<fmt:message key="label.viewaddress"/>" href="#" onclick="getShipToAddress('${status.current.rliShiptoLocId}', '${status.current.application}');">      
         ${status.current.application}
         </a>
        <c:if test="${!empty status.current.oconus && status.current.oconus=='Y'}" >
         <b>(OCONUS)</b>
        </c:if>
      </td>
     	<td width="2%">${status.current.priorityRating}</td>
     	<td width="5%">
        <c:choose>
         <c:when test="${supplierLocPermission == 'Yes'}">
          <a href="javascript:updatePO(${status.current.radianPo},${status.current.lineItem})">${status.current.radianPo}-${status.current.lineItem}</a>
         </c:when>
         <c:otherwise>
           ${status.current.radianPo}-${status.current.lineItem}
         </c:otherwise>
        </c:choose>
         <br/>
         <c:choose>
         <c:when test="${status.current.originalTransactionType == '850'}">
           <fmt:message key="label.commercial"/>
         </c:when>
         <c:when test="${status.current.originalTransactionType == '940'}">
           <fmt:message key="label.vmi"/>
         </c:when>
         <c:otherwise>
           &nbsp;
         </c:otherwise>
      </c:choose>
      <c:if test="${status.current.originInspectionRequired == 'Y'}">
         <br/>(<fmt:message key="label.origininspectionrequired"/>)
      </c:if>
      </td>
     	<td nowrap width="5%">
     	   	<fmt:formatDate var="desireShipDate" value="${status.current.desiredShipDate}" pattern="${dateFormatPattern}" />
     	   	<fmt:formatDate var="desireDeliveryDate" value="${status.current.desiredDeliveryDate}" pattern="${dateFormatPattern}" />
     		${desireShipDate}
     	</td>
     	<td width="5%">${status.current.catPartNo}<br/>(${status.current.itemId})
      <c:if test="${!empty status.current.customerPo}">
		   (${status.current.customerPo})
      </c:if>
      </td>
     	<td width="20%" class="alignLeft">${status.current.itemDescription}</td>
     	<td width="7%">${status.current.qtyOpen}</td>
     	<td width="7%">${status.current.availableInventoryQty}</td>
      <td width="3%">${status.current.unitPrice}&nbsp;${status.current.currencyId}</td>
       <td width="12%" class="alignLeft">${status.current.packaging}</td>

     <c:choose>
      <c:when test="${supplierLocPermission == 'Yes'}">
     	<td width="5%">
         <c:choose>
         	<c:when test="${fn:toUpperCase(status.current.trackSerialNumber) != 'Y'}">
            	<input class="inputBox" type="text" name="supplierShipmentBean[${status.index}].partsPerBox" id="partsPerBox_${status.index}" value="1" size="3"/>
            </c:when>
            <c:otherwise>
            	1<input type="hidden" name="supplierShipmentBean[${status.index}].partsPerBox" id="partsPerBox_${status.index}" value="1"/>
            </c:otherwise>
         </c:choose>
       </td>
     	<td width="5%">
     		<c:if test="${status.current.availableInventoryQty ne '0'}">
         		<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="allocateOrder_${status.index}" value="<fmt:message key="label.confirmallocation"/>" onclick="allocateOrder(${status.index})" />
         	</c:if>
     		<input type="hidden" name="supplierShipmentBean[${status.index}].radianPo" id="radianPo_${status.index}" value="${status.current.radianPo}"/>
     		<input type="hidden" name="supplierShipmentBean[${status.index}].poLine" id="poLine_${status.index}" value="${status.current.poLine}"/>
     		<input type="hidden" name="supplierShipmentBean[${status.index}].itemId" id="itemId_${status.index}" value="${status.current.itemId}"/>
     		<input type="hidden" name="supplierShipmentBean[${status.index}].branchPlant" id="branchPlant_${status.index}" value="${status.current.branchPlant}"/>
     		<input type="hidden" name="supplierShipmentBean[${status.index}].mrNumber" id="mrNumber_${status.index}" value="${status.current.mrNumber}"/>
     		<input type="hidden" name="supplierShipmentBean[${status.index}].mrLineItem" id="mrLineItem_${status.index}" value="${status.current.mrLineItem}"/>
     		<input type="hidden" name="supplierShipmentBean[${status.index}].mfgDateRequired" id="mfgDateRequired_${status.index}" value="${status.current.mfgDateRequired}"/>
     		<input type="hidden" name="supplierShipmentBean[${status.index}].qtyOpen" id="qtyOpen_${status.index}" value="${status.current.qtyOpen}"/>
           <fmt:formatDate var="expectedDate" value="${status.current.expected}" pattern="${dateFormatPattern}" />
           <input type="hidden" name="supplierShipmentBean[${status.index}].vendorShipDate" id="vendorShipDate_${status.index}" value="${expectedDate}"/>
		    <input type="hidden" name="supplierShipmentBean[${status.index}].receiptDate" id="receiptDate_${status.index}" value="${desireDeliveryDate}"/>
         	<input type="hidden" name="supplierShipmentBean[${status.index}].originInspectionRequired" id="originInspectionRequired_${status.index}" value="${status.current.originInspectionRequired}"/>
     		<input type="hidden" name="supplierShipmentBean[${status.index}].catPartNo" id="catPartNo_${status.index}" value="${status.current.catPartNo}"/>
     		<input type="hidden" name="supplierShipmentBean[${status.index}].supplier" id="supplier_${status.index}" value="${status.current.supplier}"/>
     		<input type="hidden" name="supplierShipmentBean[${status.index}].shipFromLocationId" id="shipFromLocationId_${status.index}" value="${status.current.shipFromLocationId}"/>
     		<input type="hidden" name="supplierShipmentBean[${status.index}].supplierSalesOrderNo" id="supplierSalesOrderNo_${status.index}" value="${status.current.supplierSalesOrderNo}"/>
     		<input type="hidden" name="supplierShipmentBean[${status.index}].customerPo" id="customerPo_${status.index}" value="${status.current.customerPo}"/>
     		<input type="hidden" name="supplierShipmentBean[${status.index}].shipToAddress" id="shipToAddress_${status.index}" value="${status.current.locationDesc} ${status.current.city}, ${status.current.stateAbbrev} ${status.current.zip}"/>
     		<input type="hidden" name="supplierShipmentBean[${status.index}].oconus" id="oconus_${status.index}" value="${status.current.oconus}"/>
     		<input type="hidden" name="supplierShipmentBean[${status.index}].originalTransactionType" id="originalTransactionType_${status.index}" value="${status.current.originalTransactionType}"/>
		</td>
	</c:when>
  <c:when test="${receivingPermission == 'Yes'}">
   <td width="5%">&nbsp;</td>
   <td width="5%">&nbsp;</td>
  </c:when>
  </c:choose>
    </tr>

   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   </c:if>
   <c:if test="${empty suppliershippingViewBeanCollection}" >

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
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input type="hidden" name="_action" id="_action" value="update"/>

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<%--<tcmis:saveRequestParameter/>--%>
<input name="searchArgument" id="searchArgument" type="hidden" value="<tcmis:jsReplace value='${param.searchArgument}' />"/>
<input name="supplier" id="supplier" type="hidden" value="<tcmis:jsReplace value='${param.supplier}' />"/>
<input name="sort" id="sort" type="hidden" value="<tcmis:jsReplace value='${param.sort}' />"/>
<input name="searchMode" id="searchMode" type="hidden" value="<tcmis:jsReplace value='${param.searchMode}' />"/>
<input name="searchField" id="searchField" type="hidden" value="<tcmis:jsReplace value='${param.searchField}' />"/>
<input name="shipFromLocationId" id="shipFromLocationId" type="hidden" value="<tcmis:jsReplace value='${param.shipFromLocationId}' />"/>
<input name="oldshipFromLocationId" id="oldshipFromLocationId" type="hidden" value="<tcmis:jsReplace value='${param.oldshipFromLocationId}' />"/>
<input name="oldsupplier" id="oldsupplier" type="hidden" value="<tcmis:jsReplace value='${param.oldsupplier}' />"/>
<c:set var="selectedsuppLocationIdArray" value='${paramValues.suppLocationIdArray}'/>
<select name="suppLocationIdArray" id="suppLocationIdArray" class="selectBox" multiple size="5"/>
<c:forEach items="${selectedsuppLocationIdArray}" varStatus="status1">
 <c:set var="selectedLocation" value='${selectedsuppLocationIdArray[status1.index]}'/>
  <option value="<c:out value="${selectedLocation}"/>" selected><tcmis:jsReplace value="${selectedLocation}" /></option>
</c:forEach>

<input name="minHeight" id="minHeight" type="hidden" value="0"/>  
<input name="picklistId" id="picklistId" type="hidden" value="${picklistId}"/>
<input name="orderStatus" id="orderStatus" type="hidden" value="<tcmis:jsReplace value='${param.orderStatus}' />"/>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>