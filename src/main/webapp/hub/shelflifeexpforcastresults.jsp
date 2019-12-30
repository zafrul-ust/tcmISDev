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
<script type="text/javascript" src="/js/hub/inventoryreport.js"></script>

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
<fmt:message key="label.shelfLifeForcast"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};

var gridConfig = {
	divName:'InventoryReportViewBean', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	onRowSelect:null,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:null   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
};

<c:set var="lookAheadDateString">
  <tcmis:getDateTag numberOfDaysFromToday="0"/>
</c:set>
var datess = '<tcmis:usageTrendHeadingTag reportStartDate="${lookAheadDateString}"/>';
datess = datess.replace(/<TH[^>]*>/g,"");
var datearr = datess.split("</TH>");

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
var config = [
  {
  	columnId:"opsCompanyId",
  	columnName:' ',
  	attachHeader:'<fmt:message key="label.operatingentity"/>'
  },
  {
  	columnId:"hub",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.hub"/>'
  },
  {
  	columnId:"inventoryGroup",
  	columnName:'#cspan',
  	attachHeader:'<tcmis:jsReplace value="${inventorygroup}"/>'
  },
  {
  	columnId:"itemId",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.item"/>',
  	width:6
  },
  {
  	columnId:"clientPartNos",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.partno"/>'
  },
  {
  	columnId:"partShortName",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.description"/>',
  	width:20
  },
  {
  	columnId:"specs",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.specs"/>'
  },
  {
  	columnId:"receiptId",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.receiptid"/>'
  },
  {
  	columnId:"lotStatus",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.lotstatus"/>',
  	width:10
  },
  {
  	columnId:"bin",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.bin"/>'
  },
  {
  	columnId:"totalShelfLife",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.totallife"/>',
  	width:12,
	sorting:'int',
	hiddenSortingColumn:"htotal"
  },
  {
  	columnId:"expireDate",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.expiredate"/>',
	sorting:'int',
	hiddenSortingColumn:"hexpired"
  },
  {
  	columnId:"quantity",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.onhand"/>',
  	width:6,
	sorting:'int'
  },
  {
  	columnId:"currencyId",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.currencyid"/>',
  	align:'right'
  },
  {
  	columnId:"unitCost",
  	columnName:'<fmt:message key="label.total"/>:',
  	attachHeader:'<fmt:message key="label.cost"/>',
  	sorting:'int'
  },
  {
  	columnId:"expired",
  	columnName:'--',
  	attachHeader:'<fmt:message key="label.expired"/>',
	sorting:'int'
  },
  {
  	columnId:"notes1",
  	columnName:'--',
  	attachHeader:datearr[0],
	sorting:'int'
  },
  {
  	columnId:"notes2",
  	columnName:'--',
  	attachHeader:datearr[1],
	sorting:'int'
  },
  {
  	columnId:"notes3",
  	columnName:'--',
  	attachHeader:datearr[2],
	sorting:'int'
  },
  {
  	columnId:"notes4",
  	columnName:'--',
  	attachHeader:datearr[3],
	sorting:'int'
  },
  {
  	columnId:"notes5",
  	columnName:'--',
  	attachHeader:datearr[4],
	sorting:'int'
  },
  {
  	columnId:"notes6",
  	columnName:'--',
  	attachHeader:datearr[5],
	sorting:'int'
  },
  {
  	columnId:"hexpired"
  },
  {
  	columnId:"htotal"
  }
  ];

function setTotal() {
  if( typeof( jsonMainData ) == 'undefined' ) return; 
  var rows = jsonMainData.rows;
  var expTotal = 0 ;
  var mon1Total = 0 ;
  var mon2Total = 0 ;
  var mon3Total = 0 ;
  var mon4Total = 0 ;
  var mon5Total = 0 ;
  var mon6Total = 0 ;
  var SSplit = 15;
  
  for( var i = 0; i < rows.length; i++ ) {
	startsplit = SSplit;
//	alert(rows[i].data[startsplit] +":"+rows[i].data[startsplit]*1);
	expTotal = expTotal*1 + rows[i].data[startsplit++]*1;
  	mon1Total = mon1Total*1 + rows[i].data[startsplit++]*1;
  	mon2Total = mon2Total*1 + rows[i].data[startsplit++]*1;
  	mon3Total = mon3Total*1 + rows[i].data[startsplit++]*1;
  	mon4Total = mon4Total*1 + rows[i].data[startsplit++]*1;
  	mon5Total = mon5Total*1 + rows[i].data[startsplit++]*1;
  	mon6Total = mon6Total*1 + rows[i].data[startsplit++]*1;
  }
  startsplit = SSplit;
  config[startsplit++].columnName = ""+Math.round(expTotal*100)/100;
  config[startsplit++].columnName = ""+Math.round(mon1Total*100)/100;
  config[startsplit++].columnName = ""+Math.round(mon2Total*100)/100;
  config[startsplit++].columnName = ""+Math.round(mon3Total*100)/100;
  config[startsplit++].columnName = ""+Math.round(mon4Total*100)/100;
  config[startsplit++].columnName = ""+Math.round(mon5Total*100)/100;
  config[startsplit++].columnName = ""+Math.round(mon6Total*100)/100;

}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setTotal();resultOnLoadWithGrid(gridConfig)">

<tcmis:form action="/shelflifeexpforcastresults.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">
<div class="backGroundContent">
<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty beanCollection}" >
<div id="InventoryReportViewBean" style="width:100%;height:400px;"></div>
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
<c:forEach var="bean" items="${beanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<fmt:formatDate var="expireDate" value="${bean.expireDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="expireYear" value="${bean.expireDate}" pattern="yyyy"/>
<c:if test="${expireYear == '3000'}">
	<c:set var="expireDate"><fmt:message key="label.indefinite"/></c:set>
</c:if>

<c:set var="totalShelfLife"><fmt:message key="label.indefinite"/></c:set>
<c:if test="${empty bean.shelfLifeDays}">
<c:set var="totalShelfLife" value=""/>
</c:if>
<c:if test="${!empty bean.shelfLifeDays and bean.shelfLifeDays ne -1}">
<c:set var="totalShelfLife">
<fmt:message key="format.totalshelfdaysfrom">
	<fmt:param>${bean.shelfLifeDays}</fmt:param>
	<fmt:param>${bean.shelfLifeBasis}</fmt:param>
</fmt:message>
</c:set>
</c:if>

<c:set var="currencyId" value='${bean.currencyId}' />
<c:choose>
   <c:when test="${bean.unitCost eq null}">
     <c:set var="currencyId" value='' />
     <c:set var="fmtUnitCost" value='' />
   </c:when>
   <c:otherwise>
  	 <fmt:formatNumber var="fmtUnitCost" value="${bean.unitCost}" pattern="${unitpricecurrencyformat}"></fmt:formatNumber>
     <c:set var="currencyId" value='${bean.currencyId}' />
   </c:otherwise>
</c:choose>


<fmt:formatNumber var="expired" value="${bean.expired}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
<fmt:formatNumber var="month1" value="${bean.month1}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
<fmt:formatNumber var="month2" value="${bean.month2}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
<fmt:formatNumber var="month3" value="${bean.month3}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
<fmt:formatNumber var="month4" value="${bean.month4}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
<fmt:formatNumber var="month5" value="${bean.month5}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
<fmt:formatNumber var="month6" value="${bean.month6}" pattern="${totalcurrencyformat}"></fmt:formatNumber>

{ id:${status.index +1},
 data:[
  '${bean.operatingEntityName}',
  '<tcmis:jsReplace value="${bean.hubName}" />',
  '<tcmis:jsReplace value="${bean.inventoryGroupName}" />',
  '${bean.itemId}',
  '${bean.clientPartNos}',
  '<tcmis:jsReplace value="${bean.partShortName}" processMultiLines="true"/>',
  '<tcmis:jsReplace value="${bean.specs}" />',
  '${bean.receiptId}',
  '${bean.lotStatus}',
  '${bean.bin}',
  '${totalShelfLife}',
  '${expireDate}',
  '${bean.quantity}',
  '${currencyId}',
  '${fmtUnitCost}',
  '${expired}',
  '${month1}',
  '${month2}',
  '${month3}',
  '${month4}',
  '${month5}',
  '${month6}',
  '${bean.expireDate.time}',
  '${bean.shelfLifeDays}'
  ]
}

 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
//-->
</script>

</div> <!-- end of grid div. -->
</c:if>

<c:if test="${empty beanCollection}" >
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