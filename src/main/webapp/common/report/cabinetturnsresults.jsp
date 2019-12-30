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
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/report/cabinetturnsresults.js"></script>

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
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="label.cabinetturns"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
open:"<fmt:message key="label.open"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};


var config = [
{ 
  columnId:"companyName",
  columnName:'<fmt:message key="label.company"/>',
  tooltip:true,
  width:12
}, 
{ 
  columnId:"divisionDescription",
  columnName:'<fmt:message key="label.division"/>',
  width:6
},
{ 
  columnId:"facilityGroupDescription",
  columnName:'<fmt:message key="label.facilitygroup"/>',
  tooltip:true,
  width:12
},
{ 
  columnId:"facilityName",
  columnName:'<fmt:message key="label.facility"/>',
  tooltip:true,
  width:12
},
{
  columnId:"reportingEntityDescription",
  columnName:'<fmt:message key="label.workareagroup"/>',
  tooltip:true,
  width:12
},
{
  columnId:"applicationDesc",
  columnName:'<fmt:message key="label.workarea"/>',
  tooltip:true,
  width:12
},
{
  columnId:"catalogDesc", 
  columnName:'<fmt:message key="label.catalog"/>', 
  tooltip:true,
  width:12
},
{
  columnId:"catPartNo", 
  columnName:'<fmt:message key="label.partnumber"/>',
  tooltip:true, 
  width:6
},
{
  columnId:"reorderPointStockingLevel", 
  columnName:'<fmt:message key="label.rpslrq"/>', 
  width:6
},
{
  columnId:"kanbanReorderQuantity", 
  columnName:'<fmt:message key="label.kanbanreorderqty"/>', 
  align:"right", 
  width:6
},
{
  columnId:"partDescription", 
  columnName:'<fmt:message key="label.description"/>',
  tooltip:true, 
  width:15
},
{
  columnId:"lastCounted", 
  columnName:'<fmt:message key="label.lastscan"/>', 
  hiddenSortingColumn:'lastCountedDateTime',
  sorting:'int',
  width:7
},
{ 
  columnId:"lastCountedDateTime",
  sorting:'int'
},
{
  columnId:"lastUsedQty", 
  columnName:'<fmt:message key="label.lastusedqty"/>', 
  align:"right",
  width:6
},
{
  columnId:"usedMonth1", 
  columnName:'<fmt:message key="label.m1"/>', 
  align:"right",
  width:5
},
{
  columnId:"usedMonth2", 
  columnName:'<fmt:message key="label.m2"/>',
  align:"right", 
  width:5
},
{
  columnId:"usedMonth3", 
  columnName:'<fmt:message key="label.m3"/>', 
  align:"right",
  width:5
},
{
  columnId:"usedMonth4", 
  columnName:'<fmt:message key="label.m4"/>', 
  align:"right",
  width:5
},
{
  columnId:"usedMonth5", 
  columnName:'<fmt:message key="label.m5"/>',
  align:"right", 
  width:5
},
{
  columnId:"usedMonth6", 
  columnName:'<fmt:message key="label.m6"/>',
  align:"right", 
  width:5
},
{
  columnId:"usedHalfYear", 
  columnName:'<fmt:message key="label.halfyeartotal"/>',
  align:"right", 
  width:6
},
{
  columnId:"avgUsePerWeek", 
  columnName:'<fmt:message key="label.avguseperweek"/>',
  align:"right", 
  width:6
},
{
  columnId:"turnsLastYear", 
  columnName:'<fmt:message key="label.turns"/>', 
  align:"right",
  width:6
}
];

//-->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/cabinetturnsresults.do" onsubmit="return submitFrameOnlyOnce();">


   <c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
   </c:if>
   
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

<div id="inventoryTurnReportBean" style="width:100%;height:500px;" style="display: none;"></div>
<c:set var="dataCount" value='${0}'/>
<c:if test="${inventoryTurnReportColl != null}">
<script type="text/javascript">
<!--
<c:if test="${!empty inventoryTurnReportColl}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${inventoryTurnReportColl}" varStatus="status">

<c:set var="rpSlRq" value="${bean.reorderPoint} / ${bean.stockingLevel} / ${bean.reorderQuantity}"/>

<fmt:formatDate var="fmtLastCounted" value="${bean.lastCounted}" pattern="${dateFormatPattern}"/>

<fmt:formatNumber var="avgUsePerWeek" value="${bean.avgUsePerWeek}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
<fmt:formatNumber var="turnsLastYear" value="${bean.turnsLastYear}" pattern="${totalcurrencyformat}"></fmt:formatNumber>

<c:if test="${status.index > 0}">,</c:if>
{id:${status.index +1},
 data:[
  '<tcmis:jsReplace value="${bean.companyName}" />',
  '<tcmis:jsReplace value="${bean.divisionDescription}" />',
  '<tcmis:jsReplace value="${bean.facilityGroupDescription}"/>',
  '<tcmis:jsReplace value="${bean.facilityName}"/>',
  '<tcmis:jsReplace value="${bean.reportingEntityDescription}" />',
  '<tcmis:jsReplace value="${bean.applicationDesc}" />',
  '<tcmis:jsReplace value="${bean.catalogDesc}" />',
  '<tcmis:jsReplace value="${bean.catPartNo}" />',  
  '${rpSlRq}',
  '${bean.kanbanReorderQuantity}',
  '<tcmis:jsReplace value="${bean.partDescription}"  processMultiLines="true"/>',
  '${fmtLastCounted}',
  '${bean.lastCounted.time}',
  '${bean.lastUsedQty}',
  '${bean.usedMonth1}',
  '${bean.usedMonth2}',
  '${bean.usedMonth3}',
  '${bean.usedMonth4}',
  '${bean.usedMonth5}',
  '${bean.usedMonth6}',
  '${bean.usedHalfYear}',
  '${avgUsePerWeek}',
  '${turnsLastYear}'
  ]}
  
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>
 //-->
</script>
</c:if>
<!-- If the collection is empty say no data found -->
<c:if test="${empty inventoryTurnReportColl}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>



<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input name="division" id="division" value="${param.division}" type="hidden"/>
<input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden"/>
<input name="facilityGroupId" id="facilityGroupId" value="${param.facilityGroupId}" type="hidden"/>
<input type="hidden" name="companyId" id="companyId" value="${param.companyId}"/>
</div>

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>