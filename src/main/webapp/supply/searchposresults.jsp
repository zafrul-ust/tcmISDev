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
<!--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/supply/searchporesults.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
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
<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hch -checkbox
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

    
<title>
<fmt:message key="searchpurchaseorders.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
// Added all column names to the messagesData array.
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
po:"<fmt:message key="label.po"/>",
supplier:"<fmt:message key="label.supplier"/>",
buyer:"<fmt:message key="label.buyer"/>",
hub:"<fmt:message key="label.hub"/>",
inventorygroup:"<fmt:message key="label.inventorygroup"/>",
dateconfirmed:"<fmt:message key="label.dateconfirmed"/>",
itemid:"<fmt:message key="label.itemid"/>",
itemdescription:"<fmt:message key="label.itemdescription"/>",
supplypath:"<fmt:message key="label.supplypath"/>",
needdate:"<fmt:message key="label.needdate"/>",
originalpromiseddate:"<fmt:message key="label.originalpromiseddate"/>",
vendorshipdate:"<fmt:message key="label.vendorshipdate"/>",
revisedpromisedate:"<fmt:message key="label.revisedpromisedate"/>",
currencyid:"<fmt:message key="label.currencyid"/>",
unitprice:"<fmt:message key="label.unitprice"/>",
unitprice:"<fmt:message key="label.unitprice"/>",
quantity:"<fmt:message key="label.quantity"/>",
extprice:"<fmt:message key="label.extprice"/>",
qtyreceived:"<fmt:message key="label.qtyreceived"/>",
partnumber:"<fmt:message key="label.partnumber"/>",
mfgpartno:"<fmt:message key="label.mfgpartno"/>",
manufacturername:"<fmt:message key="label.manufacturername"/>",
supplierpartnum:"<fmt:message key="label.supplierpartnum"/>",
customerpo:"<fmt:message key="label.customerpo"/>",   
shiptoaddress:"<fmt:message key="label.shiptoaddress"/>",
purchaseorder:"<fmt:message key="label.purchaseorder"/>",
paymentterms:"<fmt:message key="label.paymentterms"/>"
};

<c:if test="${param.searchFromPopup == 'true'}">
   <c:set var="callFromPopup" value="true"/>
</c:if> 

<c:if test="${callFromPopup != 'true'}">  
with(milonic=new menuname("showPo")){
         top="offset=2"
         style = contextStyle;
         margin=3
         aI("text=<fmt:message key="label.viewpurchaseorder"/>;url=javascript:showPurchOrder();"); 
         //aI("text=<fmt:message key="label.viewaddpodocuments"/>;url=javascript:showPODocuments();");
    }

drawMenus();
</c:if>
// -->
</script>
</head>
<body bgcolor="#ffffff" onload="resultOnLoad();">

<tcmis:form action="/searchposresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
</div>

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null}">
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

<c:if test="${callFromPopup == 'true'}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</c:if>

<%--NEW - there is no results table anymore--%>
<div id="poSearchResultBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:if test="${poSearchColl != null}" >
<script type="text/javascript">
<!--
<%--NEW - storing the data to be displayed in the grid in a JSON. notice the ID, this will be the id of the cell in the grid.--%>
<%--TODO - Right click to show links for receipt labels, print BOL, transactions history.--%>
<c:set var="dataCount" value='${0}'/>
<bean:size id="listSize" name="poSearchColl"/>
<c:if test="${!empty poSearchColl}" >
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="po" items="${poSearchColl}" varStatus="status">
  <fmt:formatDate var="fmtDateConfirmed" value="${po.dateConfirmed}" pattern="${dateFormatPattern}"/>
  <fmt:formatDate var="fmtNeedDate" value="${po.needDate}" pattern="${dateFormatPattern}"/>
  <fmt:formatDate var="fmtPromisedDate" value="${po.promisedDate}" pattern="${dateFormatPattern}"/>
  <fmt:formatDate var="fmtVendorShipDate" value="${po.vendorShipDate}" pattern="${dateFormatPattern}"/>
  <fmt:formatDate var="fmtRevisedPromisedDate" value="${po.revisedPromisedDate}" pattern="${dateFormatPattern}"/>
  
  <tcmis:jsReplace var="supplierName" value="${po.supplierName}" processMultiLines="false" />
  <tcmis:jsReplace var="buyerName" value="${po.buyerName}" processMultiLines="false" />
  <tcmis:jsReplace var="itemDesc" value="${po.itemDesc}" processMultiLines="true" />
  <tcmis:jsReplace var="customerPo" value="${po.customerPo}" processMultiLines="false" />
  <tcmis:jsReplace var="mfgPartNo" value="${po.mfgPartNo}" processMultiLines="true" />
  <tcmis:jsReplace var="manufacturer" value="${po.manufacturer}" processMultiLines="false" />
  <tcmis:jsReplace var="supplierPartNo" value="${po.supplierPartNo}" processMultiLines="false" />
  <tcmis:jsReplace var="shipToAddress" value="${po.shipToAddress}" processMultiLines="true" />
  <tcmis:jsReplace var="partNumber" value="${po.partNumber}" processMultiLines="false" />

  <c:set var="dateCreatedTime" value="${po.dateCreated.time}"/>
  <c:set var="needTime" value="${po.needDate.time}"/>
  <c:set var="promisedTime" value="${po.promisedDate.time}"/>
  <c:set var="vendorShipTime" value="${po.vendorShipDate.time}"/>
  <c:set var="revisedPromisedTime" value="${po.revisedPromisedDate.time}"/>
  <c:choose>
   <c:when test="${po.unitPrice eq null}">
     <c:set var="currencyId1" value='' />
   </c:when>
   <c:otherwise>
     <c:set var="currencyId1" value='${po.currencyId}' />
   </c:otherwise>
   </c:choose>
   <fmt:formatNumber var="unitPrice" value="${po.unitPrice}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>
   
   <c:choose>
   <c:when test="${po.extPrice eq null}">
     <c:set var="currencyId2" value='' />
   </c:when>
   <c:otherwise>
     <c:set var="currencyId2" value='${po.currencyId}' />
   </c:otherwise>
   </c:choose>
   <fmt:formatNumber var="extPrice" value="${po.extPrice}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>
   <c:set var="supplyPathDesc" value=""/>
   <c:forEach var="supplyPathBean" items="${vvSupplyPathBeanCollection}" varStatus="supplyPathStatus">
   		<c:if test="${supplyPathBean.supplyPath == po.supplyPath}"><c:set var="supplyPathDesc">${supplyPathBean.jspLabel}</c:set></c:if>
   </c:forEach>

   <c:set var="supplyPathDescEsc">
   <fmt:message key="${supplyPathDesc}"/>
   </c:set>
   
{ id:${status.index +1},
 data:[ 
  '${po.radianPo}','${supplierName}','${buyerName}','${po.hubName}',
  '${po.inventoryGroupName}','${fmtDateConfirmed}','${po.itemId}',
  '${itemDesc}',
  '<tcmis:jsReplace value="${supplyPathDescEsc}" processMultiLines="false" />',
  '${fmtNeedDate}','${fmtPromisedDate}',
  '${po.quantity}','${currencyId1} ${unitPrice}','${currencyId2} ${extPrice}',
  '${po.qtyReceived}','${po.paymentTerms}','${customerPo}','${partNumber}',
  '${mfgPartNo}','${manufacturer}','${supplierPartNo}','${shipToAddress}',
  '${fmtVendorShipDate}','${fmtRevisedPromisedDate}','${dateCreatedTime}','${needTime}','${promisedTime}','${vendorShipTime}','${revisedPromisedTime}',
  '${po.unitPrice}','${po.extPrice}','${po.supplyPath}']}<c:if test="${status.index+1 < listSize}">,</c:if>
<c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
// -->
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty poSearchColl || poSearchColl == null}" >
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
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<input type="hidden" name="uAction" id="uAction" value=""/>
<input name="minHeight" id="minHeight" type="hidden" value="100">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>