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
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
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
<script type="text/javascript" src="/js/distribution/salesorder.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
    <fmt:message key="salesorder.title"/>
</title>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="GenerateOrders">
<c:set var="showUpdateLink" value='Y'/>
</tcmis:inventoryGroupPermission>

<c:set var="checkBoxPermission" value='N'/>
<c:set var="showCheckBox" value='N'/>
<c:if test="${showUpdateLink == 'Y'}">
<script type="text/javascript">
    <!--
   	 showUpdateLinks = true;
    
    //-->
</script>
</c:if>
<script language="JavaScript" type="text/javascript">
<!--

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
customerreturnrequest:"<fmt:message key="customerreturnrequest.title"/>",
mrallocationreport:"<fmt:message key="mrallocationreport.label.title"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
open:"<fmt:message key="label.open"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
<c:set var="extprice"><fmt:message key="label.extprice"/></c:set>
var config = [
{ columnId:"permission",
  columnName:''
},
{ columnId:"operatingEntity",
  columnName:'<fmt:message key="label.operatingentity"/>',
  width:9
},
{ columnId:"hub",
  columnName:'<fmt:message key="label.hub"/>',
  width:7
},
{ columnId:"inventoryGroup",
  columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"customerName",
  columnName:'<fmt:message key="label.customer"/>',
  tooltip:"Y",
  width:15
},
{ columnId:"customerId",
  columnName:'<fmt:message key="label.customerid"/>',
  width:5
},
{ columnId:"customerPoLineNoDisplay",
  columnName:'<fmt:message key="label.customerpoline"/>',
  width:8
},
{ columnId:"salesOrder",
  columnName:'<fmt:message key="label.mr"/>',
  sorting:"int",
  width:6
},
{ columnId:"submitterName",
  columnName:'<fmt:message key="label.submittedby"/>',
  tooltip:"Y",
  width:12
},
{ columnId:"requestor",
  columnName:'<fmt:message key="label.requestor"/>',
  tooltip:"Y",
  width:12
},
{ columnId:"part",
  columnName:'<fmt:message key="label.part"/>',
  width:10
},
{ columnId:"partDesc",
  columnName:'<fmt:message key="label.partdesc"/>',
  tooltip:"Y",
  width:15
},
{ columnId:"quantity",
  columnName:'<fmt:message key="label.quantityordered"/>',
  sorting:"int",
  width:6
},
{ columnId:"picking",
  columnName:'<fmt:message key="picking"/>',
  sorting:"int",
  width:6
},
{ columnId:"shipped",
  columnName:'<fmt:message key="label.shipped"/>', 
  sorting:"int",
  width:5
},
{ columnId:"InProgress",
  columnName:'<fmt:message key="label.inprogress"/>',
  sorting:"int",
  width:5 
},
{ columnId:"backordered",
  columnName:'<fmt:message key="label.backordered"/>',
  sorting:"int",
  width:6 
},
{ columnId:"currency",
  columnName:'<fmt:message key="label.currency"/>',
  width:5  
},
{ columnId:"price",
  columnName:'<fmt:message key="label.price"/>',
  sorting:"int",
  width:6
},
{ columnId:"extPrice",
  columnName:'<fmt:message key="label.extprice"/>',
  sorting:"int",
  width:6
},
{ columnId:"qtyOpened",
  columnName:'<fmt:message key="label.qtyOpened"/>',
  sorting:"int",
  width:6 
},
{ columnId:"openedExtPrice",
  columnName:'<fmt:message key="label.openedExtPrice"/>',
  sorting:"int",
  width:6
},
{ columnId:"margin",
  columnName:'<fmt:message key="label.margin"/>',
  hiddenSortingColumn:"hmargin",
  sorting:"int",
  width:5
},
{ 
  columnId:"needDatezzz"
},
{ columnId:"needDate",
  columnName:'<fmt:message key="label.needed"/>',
  hiddenSortingColumn:'hiddenNeedDate',
  sorting:"int",
  width:7
},
{ columnId:"promisedShipDate",
  columnName:'<fmt:message key="label.promised.ship.date"/>',
  hiddenSortingColumn:'hiddenPromisedShipDate',
  sorting:"int",
  width:7
},
{ columnId:"status",
  columnName:'<fmt:message key="label.status"/>',
  width:6
},
{ columnId:"orderDate",
  columnName:'<fmt:message key="label.orderdate"/>',
  hiddenSortingColumn:'hiddenOrderDate',
  sorting:"int",
  width:7
},
{ columnId:"daysLate",
  columnName:'<fmt:message key="label.daysLate"/>',
  sorting:"int",
  width:4
},
{ columnId:"comments",
  columnName:'<fmt:message key="label.externallinenote"/>',
  tooltip:"Y",
  width:20
},
{ columnId:"internalNote",
  columnName:'<fmt:message key="label.internallinenote"/>',
  tooltip:"Y",
  width:20
},
{ columnId:"orderInternalNote",
  columnName:'<fmt:message key="label.orderinternalnote"/>',
  tooltip:"Y",
  width:20
},
{ columnId:"specialInstructions",
  columnName:'<fmt:message key="label.orderexternal"/>',
  tooltip:"Y",
  width:20
},
{ columnId:"shipToAddress",
  columnName:'<fmt:message key="label.shiptoaddress"/>',
  tooltip:true,
  width:15
},
{ columnId:"hiddenNeedDate"
},
{ columnId:"hiddenPromisedShipDate"
},
{ columnId:"hiddenOrderDate"
},
{ columnId:"hmargin", 
  sorting:"int"
},
{ columnId:"prNumber",
  submit:true
 
},
{ columnId:"lineItem",
  submit:true
 
},
{ columnId:"companyId"
 
},
{ columnId:"unitPriceCurrenty"
	 
},
{ columnId:"opsEntityId"	 
}
];

with(milonic=new menuname("salesOrderRightClick")){
	 top="offset=2"
	 style = contextStyle;
	 margin=3
	 aI("text=<fmt:message key="label.open"/>;url=javascript:openMR();");
	 aI("text=<fmt:message key="ordertracking.label.mrallocation"/>;url=javascript:showMrAllocationReport();");
     aI("text=<fmt:message key="label.mrlineallocation"/>;url=javascript:showMrLineAllocationReport();");	 
//	 aI("text=<fmt:message key="label.mrlineschedule"/>;url=javascript:showMrLineAllocationReport();");
//	 aI("text=<fmt:message key="label.customerreturnrequest"/>;url=javascript:showCustomerReturnRequest();");
     aI("text=<fmt:message key="label.viewaddmrdocuments"/>;url=javascript:showMRDocuments();");		 
	}

drawMenus();

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">
<tcmis:form action="/salesordersearchresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors }">
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

<div id="salesOrderViewBean" style="width:100%;height:400px;" style="display: none;"></div>
<c:set var="colorClass" value=''/>
<c:if test="${salesOrderViewBeanColl != null}">
<script type="text/javascript">

<!--
<c:set var="dataCount" value='${0}'/>
<c:set var="creditHoldCount" value='${0}'/>
<c:if test="${!empty salesOrderViewBeanColl}" >

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="salesOrderViewBean" items="${salesOrderViewBeanColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<c:choose>
<c:when test="${salesOrderViewBean.orderStatus eq 'Credit Hold' and showUpdateLink eq 'Y'}">
<c:set var="showCheckBox" value='Y'/>
<c:set var="checkBoxPermission" value='Y'/>
<c:set var="creditHoldCount" value='${creditHoldCount+1}'/> 
</c:when>
<c:otherwise>
<c:set var="checkBoxPermission" value='N'/>
</c:otherwise>
</c:choose>  

<c:set var="extPrice" value='' />

<fmt:formatNumber var="unitPriceFinal" value="${salesOrderViewBean.catalogPrice}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>

<c:choose>
<c:when test="${(!empty salesOrderViewBean.catalogPrice) and  (!empty salesOrderViewBean.quantity )}">
<c:set var="extPrice" value='${salesOrderViewBean.catalogPrice * salesOrderViewBean.quantity }' />
</c:when>
<c:otherwise>
<c:set var="extPrice" value='' />
</c:otherwise>
</c:choose>
<fmt:formatNumber var="extPriceFinal" value="${extPrice}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>


<fmt:formatDate var="fmtNeededDate" value="${salesOrderViewBean.needDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtPromisedShipDate" value="${salesOrderViewBean.promiseDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtOrderDate" value="${salesOrderViewBean.submittedDate}" pattern="${dateFormatPattern}"/>
<c:set var="needDateTime" value="${salesOrderViewBean.needDate.time}"/>
<c:set var="promisedShipDateTime" value="${salesOrderViewBean.promiseDate.time}"/>
<c:set var="orderDateTime" value="${salesOrderViewBean.submittedDate.time}"/>

<tcmis:jsReplace var="partDesc" value="${salesOrderViewBean.partDescription}" processMultiLines="true" />
<tcmis:jsReplace var="comments" value="${salesOrderViewBean.comments}" processMultiLines="true" />

<tcmis:jsReplace var="addressLine1Display" value='${salesOrderViewBean.addressLine1Display}' processMultiLines="true"/>
<tcmis:jsReplace var="addressLine2Display" value='${salesOrderViewBean.addressLine2Display}' processMultiLines="true"/>
<tcmis:jsReplace var="addressLine3Display" value='${salesOrderViewBean.addressLine3Display}' processMultiLines="true"/>
<tcmis:jsReplace var="addressLine4Display" value='${salesOrderViewBean.addressLine4Display}' processMultiLines="true"/>
<tcmis:jsReplace var="addressLine5Display" value='${salesOrderViewBean.addressLine5Display}' processMultiLines="true"/>


<c:choose>
<c:when test="${status.index % 2 == 0}" >
  <c:set var="colorClass" value='grid_white'/>
</c:when>
<c:otherwise>
   <c:set var="colorClass" value='grid_lightblue'/>
</c:otherwise>
</c:choose>

<c:set var="critical" value='${salesOrderViewBean.critical}'/>
<c:set var="requestLineStatus" value='${salesOrderViewBean.requestLineStatus}'/>

<c:if test="${critical == 'Y' || critical == 'y'}">
  <c:set var="colorClass" value='grid_red'/>
</c:if>

<c:if test="${critical == 'S' || critical == 's'}">
  <c:set var="colorClass" value='grid_pink'/>
</c:if>

<c:if test="${requestLineStatus == 'Pending Cancellation'}">
  <c:set var="colorClass" value='grid_yellow'/>
</c:if>

<c:if test="${requestLineStatus == 'Cancelled' || requestLineStatus == 'Rejected'}">
  <c:set var="colorClass" value='grid_black'/>
</c:if>

<fmt:formatNumber var="margin" value="${salesOrderViewBean.margin}"  pattern="${oneDigitformat}"></fmt:formatNumber>
<c:choose>
<c:when test="${!empty salesOrderViewBean.margin}">
<c:set var="fmtMargin" value='${margin}%' />
</c:when>
<c:otherwise>
<c:set var="fmtMargin" value='' />
</c:otherwise>
</c:choose>

<c:choose>
<c:when test="${!empty salesOrderViewBean.customerPoLine}">
<c:set var="poLine" value='${salesOrderViewBean.poNumber}-${salesOrderViewBean.customerPoLine}' />
</c:when>
<c:otherwise>
<c:set var="poLine" value='${salesOrderViewBean.poNumber}' />
</c:otherwise>
</c:choose>

<c:choose>
<c:when test="${!empty salesOrderViewBean.qtyShipped}">
<c:set var="qtyOpened" value='${salesOrderViewBean.quantity-salesOrderViewBean.qtyShipped}' />
</c:when>
<c:otherwise>
<c:set var="qtyOpened" value='${salesOrderViewBean.quantity}' />
</c:otherwise>
</c:choose>
<fmt:formatNumber var="qtyOpenedPrice" value="${qtyOpened*salesOrderViewBean.catalogPrice}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>
<fmt:formatNumber var="fmtDaysLate" value="${salesOrderViewBean.daysLate}"  pattern="${oneDigitformat}"></fmt:formatNumber>

/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},'class':"${colorClass}",
 data:['${checkBoxPermission}',
  '<tcmis:jsReplace value="${salesOrderViewBean.operatingEntityName}" />',
  '<tcmis:jsReplace value="${salesOrderViewBean.hubName}" />',
  '<tcmis:jsReplace value="${salesOrderViewBean.inventoryGroupName}" />',
  '<tcmis:jsReplace value="${salesOrderViewBean.customerName}" processMultiLines="true"/>',
  '${salesOrderViewBean.customerId}',
  '<tcmis:jsReplace value="${poLine}" processMultiLines="true"/>',
  '${salesOrderViewBean.prNumber}-${salesOrderViewBean.lineItem}',
  '<tcmis:jsReplace value="${salesOrderViewBean.submitterName}" processMultiLines="true" />',
  '<tcmis:jsReplace value="${salesOrderViewBean.requestorName}"/>',
  '<tcmis:jsReplace value="${salesOrderViewBean.facPartNo}" />',
  '${partDesc}',
  '${salesOrderViewBean.quantity}',
  '${salesOrderViewBean.qtyPicked}',
  '${salesOrderViewBean.qtyShipped}',
  '${salesOrderViewBean.qtyAllocated}',
  '${salesOrderViewBean.qtyInPurchasing}',
  '${salesOrderViewBean.currencyId}',
  '${unitPriceFinal}',
  '${extPriceFinal}',
  '${qtyOpened}',
  '${qtyOpenedPrice}',
  '${fmtMargin}','YY${fmtNeededDate}',
  '${fmtNeededDate}','${fmtPromisedShipDate}',
  '${salesOrderViewBean.orderStatus}',
  '${fmtOrderDate}',
  '${fmtDaysLate}','${comments}',
  '<tcmis:jsReplace value="${salesOrderViewBean.internalNote}" processMultiLines="true" />',
  '<tcmis:jsReplace value="${salesOrderViewBean.orderInternalNote}" processMultiLines="true" />',
  '<tcmis:jsReplace value="${salesOrderViewBean.specialInstructions}" processMultiLines="true" />',
  '${addressLine1Display} ${addressLine2Display} ${addressLine3Display} ${addressLine4Display} ${addressLine5Display}',
  '${needDateTime}',
  '${promisedShipDateTime}','${orderDateTime}','${salesOrderViewBean.margin}','${salesOrderViewBean.prNumber}',
  '${salesOrderViewBean.lineItem}','${salesOrderViewBean.companyId}','${salesOrderViewBean.currencyId}',
  '${salesOrderViewBean.opsEntityId}']}
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>
//-->   
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty salesOrderViewBeanColl}">
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
	<input name="creditHoldCount" id="creditHoldCount" value="<c:out value="${creditHoldCount}"/>" type="hidden">
	<input name="uAction" id="uAction" value="" type="hidden"/>   
	<input name="minHeight" id="minHeight" type="hidden" value="100"> 
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>
</body>
</html:html>