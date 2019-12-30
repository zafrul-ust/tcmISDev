<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

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

<!-- For Calendar support for column type hcal-->
<!--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/supplier/orderlistresults.js"></script>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.--%>
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
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="label.purchase.orders"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
with(milonic=new menuname("showPurchaseOrder")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="label.viewpurchaseorder"/>;url=javascript:showPO();");
}
with(milonic=new menuname("showDetails")){
 top="offset=2"
 style = contextStyle;
 margin=3
 //aI("text=<fmt:message key="label.viewpurchaseorder"/>;url=javascript:showPO();");
 aI("text=<fmt:message key="label.showdetails"/>;url=javascript:showDetails();");
}

drawMenus();

//-->

<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
ponum:"<fmt:message key="label.po.num"/>",
purchaseorder:"<fmt:message key="label.purchaseorder"/>",    
company:"<fmt:message key="label.company"/>",
shipto:"<fmt:message key="label.shipto"/>",
datecreated:"<fmt:message key="label.date.created"/>",
critical:"<fmt:message key="label.critical"/>",
status:"<fmt:message key="label.status"/>",
currentstatus:"<fmt:message key="label.current.status"/>",
firstviewed:"<fmt:message key="label.first.viewed"/>",
confirmeddate:"<fmt:message key="label.confirmed"/>",
shipdate:"<fmt:message key="label.promised.ship.date"/>",
dockdate:"<fmt:message key="label.est.dock.date"/>",
comments:"<fmt:message key="label.comments"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myResultOnload();">

<tcmis:form action="/orderlistresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->


<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>

<script type="text/javascript">
<!--
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

<div class="interface" id="resultsPage">
<div class="backGroundContent">
    
<!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
<div id="wbuyStatusViewBean" style="width:100%;height:400px;" style="display: none;"></div>
<!-- Search results start -->
<c:if test="${WbuyStatusViewBean != null}" >
<script type="text/javascript">
<!--
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
<c:set var="showUpdateLink" value='N'/>
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty WbuyStatusViewBean}" >
/*Storing the data to be displayed in a JSON object array.*/
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="orderList" items="${WbuyStatusViewBean}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
/*Do any formatting you need to do before entering ti into the JSON. This grid will display what is in the JSON.*/
<fmt:formatDate var="fmtDateCreated" value="${status.current.dateCreated}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtDateAcknowledgement" value="${status.current.dateAcknowledgement}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtDateConfirmed" value="${status.current.dateConfirmed}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtVendorShipDate" value="${status.current.vendorShipDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtPromisedDate" value="${status.current.promisedDate}" pattern="${dateFormatPattern}"/>
<c:set var="dateCreatedSortable" value="${status.current.dateCreated.time}"/>
<c:set var="dateAcknowledgementSortable" value="${status.current.dateAcknowledgement.time}"/>
<c:set var="dateConfirmedSortable" value="${status.current.dateConfirmed.time}"/>
<c:set var="vendorShipDateSortable" value="${status.current.vendorShipDate.time}"/>
<c:set var="promisedDateSortable" value="${status.current.promisedDate.time}"/>

/*Use this to replace any special characters in the data, this is mostly done to data that includes
  user input and strings with posibilites of having special characters.*/
<tcmis:jsReplace var="comments" value="${status.current.comments}" processMultiLines="true" />
/* Check if the user has permissions to update the specific facility/inventory group etc.
   If your page has no updates or no custom cells you don't need this.
*/
<c:set var="readonly" value='Y'/> 
 
/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},	
 data:[
  '${readonly}',  
  '${status.current.radianPo}','${status.current.operatingEntityName}','${status.current.shipToLocationId}',
  '${fmtDateCreated}', '${status.current.critical}','${status.current.dbuyStatus}','${status.current.daysSinceLastStatus}',
  '${fmtDateAcknowledgement}','${fmtDateConfirmed}','${fmtVendorShipDate}','${fmtPromisedDate}',
  '${comments}', '${dateCreatedSortable}', '${dateAcknowledgementSortable}', '${dateConfirmedSortable}', '${vendorShipDateSortable}', 
  '${promisedDateSortable}']}
  <c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>
]};
</c:if>
//-->
</script>
<!-- If the collection is empty say no data found -->
   <c:if test="${empty WbuyStatusViewBean}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>

</c:if>
<!-- Search results end -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">

<%-- can use on read-only pages only. Store all search criteria in hidden elements, need this to requery the database after updates
Do not use <tcmis:saveRequestParameter/> on pages with updates
The purpose of this tag was to save the search criteria on the result page.
When there is a form in the result section and gets submitted to the server there is no difference between
the search parameters and the result parameters. This causes duplicates of the result section parameters.
This can potentially cause lot of mix-ups.
 --%>
<%--<tcmis:saveRequestParameter/>--%>

<%--Need to store search input options here. This is used to re-do the original search upon updates etc.--%>    
<input name="action" id="action" value="" type="hidden">
<!-- Popup Calendar input options for hcal column Type in the grid-->
<!--
<input type="hidden" name="blockBefore_columnId" id="blockBefore_columnId" value=""/>
<input type="hidden" name="blockAfter_columnId" id="blockAfter_columnId" value=""/>
<input type="hidden" name="blockBeforeExclude_columnId" id="blockBeforeExclude_columnId" value=""/>
<input type="hidden" name="blockAfterExclude_columnId" id="blockAfterExclude_columnId" value=""/>
<input type="hidden" name="inDefinite_columnId" id="inDefinite_columnId" value=""/>
-->

<%--This is the minimum height of the result section you want to display--%>
<input name="minHeight" id="minHeight" type="hidden" value="0">
<input name="minHeight" id="minHeight" type="hidden" value="0">
<input name="radianPo" id="radianPo" type="hidden" value="<c:out value="${param.radianPo}"/>">
<input name="searchPo" id="searchPo" type="hidden" value="<c:out value="${param.searchPo}"/>">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

<!-- If the user has permissions and needs to see the update links,set the variable showUpdateLinks javascript to true.
     The default value of showUpdateLinks is false.
-->
<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>
</tcmis:form>
</body>
</html:html>
