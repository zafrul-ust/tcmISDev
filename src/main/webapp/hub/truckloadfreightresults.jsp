<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/truckloadfreight.js"></script>

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
<fmt:message key="label.truckloadfreight"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validValues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
mustBeAnInteger:"<fmt:message key="errors.integer"/>",
qty:"<fmt:message key="label.qty"/>",
trackingNumber:"<fmt:message key="label.trackingnumber"/>",
mustBeADate:"<fmt:message key="errors.date"/>",
scheduledShipDate:"<fmt:message key="label.shipdate"/>",
selectConsolidation:"<fmt:message key="label.selectconsolidation"/>",
consolidationNumber:"<fmt:message key="label.consolidationno"/>",
totalQtyNotEqual:"<fmt:message key="label.totalqtynotequal"/>",	
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
pleasewait:"<fmt:message key="label.pleasewaitmenu"/>",
noRowSelected:"<fmt:message key="error.norowselected"/>",
pleaseSelectRowForUpdate:"<fmt:message key="label.pleaseselectarowforupdate"/>",
pleaseselectonlyonerow:"<fmt:message key="label.pleaseselectonlyonerow"/>",
selectCarrier:"<fmt:message key="label.selectacarrier"/>",
newLabel:"<fmt:message key="label.new"/>"
};

with ( milonic=new menuname("splitLine") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=<fmt:message key="label.splitline"/> ;url=javascript:splitLine();" );
}

with ( milonic=new menuname("hazmatInfo") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=<fmt:message key="label.viewhazmatdetail"/> ;url=javascript:showHazmatInfo();" );
}

with ( milonic=new menuname("splitLineHazmatInfo") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=<fmt:message key="label.viewhazmatdetail"/> ;url=javascript:showHazmatInfo();" );
 aI( "text=<fmt:message key="label.splitline"/> ;url=javascript:splitLine();" );
}

with ( milonic=new menuname("empty") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=" );
}
drawMenus();

<c:set var="shipdate"><fmt:message key="label.shipdate"/></c:set>
<c:set var="itemweights"><fmt:message key="label.itemweights"/></c:set>
<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
var config = [
{ columnId:"permission",
  columnName:''
},
{ columnId:"ok",
  columnName:'<fmt:message key="label.ok"/>',
  width:3,
  type:"hch",
  sorting:"haasHch",
  align:"center"
}
,
{ columnId:"consolidationNumber",
  columnName:'<fmt:message key="label.consolidationno"/>',
  width:7
}
,
{ columnId:"carrierNameDisplay",
  columnName:'<fmt:message key="label.carrier"/> / <fmt:message key="label.account"/> / <fmt:message key="label.mode"/>',
  submit:false,	
  width:20
}
,
{ columnId:"carrierName",
  columnName:'<fmt:message key="label.carrier"/>',
  width:0
}
,
{ columnId:"carrierSearch",
  columnName:' ',
  submit:false,
  width:3
}
,
{ columnId:"carrierCode",
  columnName:'<fmt:message key="label.carrier"/>',
  width:0
}
,
{ columnId:"trackingNumber",
  columnName:'<fmt:message key="label.trackingnumber"/>',
  type:"hed",
  size:11,
  width:8
}
,
{ columnId:"scheduledShipDate",
  columnName:'<tcmis:jsReplace value="${shipdate}"/>',
  type:"hcal",
  width:7
}
,
{ columnId:"requiredDatetime",
  columnName:'<fmt:message key="label.requireddate"/>'
}
,
{ columnId:"shipFrom",
  columnName:'<fmt:message key="label.shipfrom"/>',
  submit:false,
  width:14
}
,
{ columnId:"shipTo",
  columnName:'<fmt:message key="label.shipto"/>',
  submit:false,
  width:14
}
,
{ columnId:"shippingWeight",
  columnName:'<fmt:message key="label.shippingweight"/>',
  submit:false,
  align:'right',	
  width:8
}
,
{ columnId:"palletCount",
  columnName:'<fmt:message key="label.palletcount"/>',
  submit:false,
  align:'right',
  width:5
}
,
{ columnId:"export",
  columnName:'<fmt:message key="label.oconus"/>',
  submit:false,
  width:5
}
,
{ columnId:"transportationMode",
  columnName:'<fmt:message key="label.transportationmode"/>',
  width:0
}
,
{ columnId:"transportationPriority",
  columnName:'<fmt:message key="label.transportationpriority"/>',
  submit:false,
  align:'right',
  width:5
}
,
{ columnId:"hazardousFlag",
  columnName:'<fmt:message key="label.haz"/>',
  submit:false,
  width:5
}
,
{ columnId:"itemsPerPallet",
  columnName:'<fmt:message key="label.qtyperpallet"/>',
  submit:false,
  align:'right',
  width:5
}
,
{
  columnId:"quantityPermission"
}
,
{ columnId:"quantity",
  columnName:'<fmt:message key="label.qty"/>',
  type:"hed",
  width:5,
  align:'right',	
  permission:true
}
,
{ columnId:"orderCount",
  columnName:'<fmt:message key="label.ordertype"/>',
  width:5
}
,
{ columnId:"originalConsolidationNumber",
  columnName:'<fmt:message key="label.consolidationno"/>',
  width:0
}
,
{ columnId:"originalQuantity",
  columnName:'<fmt:message key="label.qty"/>',
  width:0
}
,
{ columnId:"unitPalletWeight",
  columnName:'<fmt:message key="label.palletweight"/>',
  width:0
}
,
{ columnId:"weightUnit",
  columnName:'<fmt:message key="label.weightunit"/>',
  submit:false,
  width:0
}
,
{ columnId:"itemWeight",
  columnName:'<tcmis:jsReplace value="${itemweights}"/>',
  width:0
}
,
{ columnId:"inventoryGroup",
  columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
  width:0
}
,
{ columnId:"originalShippingWeight",
  columnName:'<fmt:message key="label.shippingweight"/>',
  width:0
}
,
{ columnId:"splitLine",
  columnName:'<fmt:message key="label.splitline"/>',
  width:0
}
,
{ columnId:"originalPalletCount",
  columnName:'<fmt:message key="label.palletcount"/>',
  width:0
}
,
{ columnId:"shippingUnits",
  columnName:'<fmt:message key="label.unit"/>',
  width:0
}
];

// -->
</script>
</head>


<body bgcolor="#ffffff" onload="myOnLoad();">
<tcmis:form action="/truckloadfreightresults.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">
<div class="backGroundContent">
<c:set var="dataCount" value='${0}'/>

<div id="freightTlTrackingViewBean" style="width:100%;height:400px;"></div>
<c:if test="${!empty truckLoadFreightColl}" >
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
		<c:forEach var="freightTlTrackingViewBean" items="${truckLoadFreightColl}" varStatus="status">
		 	<fmt:formatDate var="formattedScheduledDate" value="${status.current.scheduledShipDate}" pattern="${dateFormatPattern}"/>
		 	<fmt:formatDate var="formattedRequiredDatetime" value="${status.current.requiredDatetime}" pattern="${dateFormatPattern}"/>
			<c:if test="${status.index > 0}">,</c:if>
			<c:set var="currentPermission" value='N'/>
		   <c:set var="carrierSearchB" value=''/>
			<c:if test="${param.hasEditPermission == 'true'}">
				<c:set var="currentPermission" value='Y'/>
		 		<c:set var="carrierSearchB">
   				<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onMouseout="this.className='lookupBtn'" name="carrierSearchBtn" name="carrierSearchBtn" value="" OnClick="searchCarrier('${status.current.inventoryGroup}')"/>
				</c:set>
 				<tcmis:jsReplace value="${carrierSearchB}" var="carrierSearchB"/>
			</c:if>
			<c:set var="editQty" value='N'/>
		 	<c:if test="${status.current.orderCount == 'Single'}">
		 		<c:set var="editQty" value='Y'/>
		 	</c:if>
			<fmt:formatNumber var="shippingWeightFormat" value="${status.current.shippingWeight}"  pattern="${totalcurrencyformat}"/>
         <fmt:formatNumber var="palletCountFormat" value="${status.current.palletCount}"  pattern="${totalcurrencyformat}"/>

			{ id:${status.index +1},
			 data:['${currentPermission}','','${status.current.consolidationNumber}','${status.current.carrierName} / ${status.current.carrierCode} / ${status.current.transportationMode}',
				   '${status.current.carrierName}','${carrierSearchB}','${status.current.carrierCode}','${status.current.trackingNumber}',
				   '${formattedScheduledDate}','${formattedRequiredDatetime}','${status.current.shipFrom}','${status.current.shipTo}','${shippingWeightFormat} ${status.current.weightUnit}','${palletCountFormat}','${status.current.export}',
				   '${status.current.transportationMode}','${status.current.transportationPriority}','${status.current.hazardousFlag}','${status.current.itemsPerPallet}','${editQty}',
				   '${status.current.quantity}','${status.current.orderCount}','','${status.current.quantity}','${status.current.unitPalletWeight}','${status.current.weightUnit}',
			      '${status.current.itemWeight}','${status.current.inventoryGroup}','${status.current.shippingWeight}','N','${status.current.palletCount}','${status.current.shippingUnits}']}   
			 <c:set var="dataCount" value='${dataCount+1}'/>
		 </c:forEach>
	]};
//-->
</script>

</div> <!-- end of grid div. -->
</c:if>

<c:if test="${empty truckLoadFreightColl}" >
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
<input name="uAction" id="uAction" value="" type="hidden"/>
<input name="hasEditPermission" id="hasEditPermission" value="${param.hasEditPermission}" type="hidden"/>
<input name="hub" id="hub" value="${param.hub}" type="hidden"/>
<!-- Popup Calendar input options -->
<input type="hidden" name="blockBefore_scheduledShipDate" id="blockBefore_scheduledShipDate" value="<tcmis:getDateTag numberOfDaysFromToday="-1" datePattern="${dateFormatPattern}"/>"/>
<input type="hidden" name="blockAfter_scheduledShipDate" id="blockAfter_scheduledShipDate" value=""/>
<input type="hidden" name="blockBeforeExclude_scheduledShipDate" id="blockBeforeExclude_scheduledShipDate" value=""/>
<input type="hidden" name="blockAfterExclude_scheduledShipDate" id="blockAfterExclude_scheduledShipDate" value=""/>
<input type="hidden" name="inDefinite_scheduledShipDate" id="inDefinite_scheduledShipDate" value=""/>
<input name="minHeight" id="minHeight" type="hidden" value="50"/>
</div>

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

showUpdateLinks = true;
//-->
</script>

</body>
</html:html>