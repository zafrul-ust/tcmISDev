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
<script type="text/javascript" src="/js/distribution/nosales.js"></script>

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
<fmt:message key="label.nosales"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};

var gridConfig = {
	divName:'noSalesViewBean', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	onRowSelect:null,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:selectRightclick   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//	onBeforeSorting:_onBeforeSorting
};

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
<c:set var="itemid"><fmt:message key="label.itemid"/></c:set>
var config = [
  {
  	columnId:"opsCompanyId",
  	columnName:'<fmt:message key="label.operatingentity"/>'
  },
  {
  	columnId:"hub",
  	columnName:'<fmt:message key="label.hub"/>'
  },
  {
  	columnId:"hubId"
  },
  {
  	columnId:"inventorygroup",
  	columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
  	width:10
  },
  {
  	columnId:"itemId",
  	columnName:'<tcmis:jsReplace value="${itemid}"/>'
  },
  {
  	columnId:"partNo",
  	columnName:'<fmt:message key="label.partno"/>',
  	width:8
  },
  {
  	columnId:"partShortName",
  	columnName:'<fmt:message key="label.description"/>',
  	width:20
  },
  {
  	columnId:"onHand",
  	columnName:'<fmt:message key="label.onhand"/>',
  	width:4,
  	sorting:'int'
  },
  {
  	columnId:"currencyId",
  	columnName:'<fmt:message key="label.currencyid"/>',
  	width:6
  },
  {
  	columnId:"cost",
  	columnName:'<fmt:message key="label.averagecost"/>',
  	sorting:'int',
  	width:6
  },
  {
  	columnId:"value",
  	columnName:'<fmt:message key="label.value"/>',
  	sorting:'int',
  	width:8
  },
  {
  	columnId:"lastSaleDate",
  	columnName:'<fmt:message key="label.lastsaledate"/>',
	sorting:'int',
	hiddenSortingColumn:"hLastSaleDate"
  },
  {
  	columnId:"hLastSaleDate"
  }
  ];

with(milonic=new menuname("showInventoryDetail")){
	 top="offset=2"
	 style = contextStyle;
	 margin=3
	 aI("text=<fmt:message key="label.showinventorydetail"/>;url=javascript:viewInventoryDetail();");
}

drawMenus(); 		

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig)">

<tcmis:form action="/nosalesresults.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">
<div class="backGroundContent">
<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty noSalesViewCollection}" >
<div id="noSalesViewBean" style="width:100%;height:400px;"></div>
<!-- Search results start -->
<script type="text/javascript">
<!--
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
<c:set var="showUpdateLink" value='N'/>
/*Storing the data to be displayed in a JSON object array.*/
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${noSalesViewCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<c:set var="currencyId" value='${bean.currencyId}' />
<c:set var="averageCost" value='${bean.averageCost}' />
<c:set var="fmtTotal" value='' />

<c:choose>
   <c:when test="${bean.averageCost eq null}">
     <c:set var="currencyId" value='' />
   </c:when>
   <c:otherwise>
  	 <fmt:formatNumber var="averageCost" value="${bean.averageCost}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>
     <c:set var="currencyId" value='${bean.currencyId}' />
   </c:otherwise>
</c:choose>

<c:choose>
   <c:when test="${bean.onHand eq null || bean.averageCost eq null}">
     <c:set var="total" value='' />
   </c:when>
   <c:otherwise>
   	 <c:set var="total" value='${bean.onHand * bean.averageCost}' />
     <fmt:formatNumber var="fmtTotal" maxFractionDigits="2" minFractionDigits="2" value="${total}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
   </c:otherwise>
</c:choose>

<fmt:formatDate var="lastSaleDate" value="${bean.lastSaleDate}" pattern="${dateFormatPattern}"/>
{ id:${status.index +1},
 data:[
  '<tcmis:jsReplace value="${bean.operatingEntityName}" />',
  '<tcmis:jsReplace value="${bean.hubName}" />','${bean.hub}',
  '<tcmis:jsReplace value="${bean.inventoryGroupName}" />',
  '${bean.itemId}',
  '<tcmis:jsReplace value="${bean.partNo}" />',
  '<tcmis:jsReplace value="${bean.partShortName}" processMultiLines="true"/>',
  '${bean.onHand}',
  '${currencyId}',
  '${averageCost}',
  '${fmtTotal}',
  '${lastSaleDate}',
  '${bean.lastSaleDate.time}'
  ]
}

 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
// -->
</script>

</c:if>

<c:if test="${empty noSalesViewCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>

<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
</div>

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>

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

<!-- permission stuff here --!>
showUpdateLinks = true;
//-->
</script>

</body>
</html:html>