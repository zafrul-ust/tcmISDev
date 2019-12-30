lichua<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
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
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>

<tcmis:gridFontSizeCss /> 
<%-- Add any other stylesheets you need for the page here --%>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>
<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<%--
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
--%>
<%-- For Calendar support --%>
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>

<%-- Add any other javascript you need for the page here --%>
<script type="text/javascript" src="/js/distribution/quicksupplierviewresults.js"></script>


<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>

<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_filter.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>



<title>
<fmt:message key="label.quickSupplierView"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
purchaseorder:"<fmt:message key="label.purchaseorder"/>",
pohistory:"<fmt:message key="label.pohistory"/>",
supplierpricelist:"<fmt:message key="supplierPriceList"/>",
supplierinvoicereport:"<fmt:message key="supplierinvoicereport.title"/>",
suppliercontact:"<fmt:message key="label.suppliercontact"/>",
createexcel:"<fmt:message key="label.createexcel"/>",
newcontact:"<fmt:message key="label.newcontact"/>",
blank:" | ",
noChange:"<fmt:message key="error.nochange"/>"};

with(milonic=new menuname("poHistoryMenu")){
   top="offset=2"
   style = contextWideStyle;
   margin=3
   aI("text=<fmt:message key="label.open"/>;url=javascript:openPoHistory();"); 
   aI("text=<fmt:message key="supplieritemnotes.title"/>;url=javascript:showPOSupplierItemNote();");  
   aI("text=<fmt:message key="label.expeditenotes"/>;url=javascript:showExpediteNotes();"); 
   aI("text=<fmt:message key="label.viewreceiptdocument"/>;url=javascript:showReceiptDocuments();");  
}

with(milonic=new menuname("priceListMenu")){
   top="offset=2";
   style=submenuStyle;
   itemheight=17;
   aI("text=<fmt:message key="supplieritemnotes.title"/>;url=javascript:showPLSupplierItemNote();");
   aI("text=<fmt:message key="label.editsourcinginfo"/>;url=javascript:editSourcingInfo();");  
}

with(milonic=new menuname("invoiceMenu")){
   top="offset=2";
   style=submenuStyle;
   itemheight=17;
   aI("text=<fmt:message key="label.showaccountspayableforpo"/>;url=javascript:showAccountsPayableforPO();");
   aI("text=<fmt:message key="label.showaccountspayableforinvoice"/>;url=javascript:showAccountsPayableforInvoice();");  
}

with(milonic=new menuname("contactMenu")){
   top="offset=2";
   style=submenuStyle;
   itemheight=17;
   aI("text=<fmt:message key="label.editcontact"/>;url=javascript:editSupplierContact();");
   aI("text=<fmt:message key="label.email"/>;url=javascript:emailContact();");  
}

with(milonic=new menuname("contactEmptyMenu")){
   top="offset=2";
   style=submenuStyle;
   itemheight=17;
   aI("text=<fmt:message key="label.editcontact"/>;url=javascript:editSupplierContact();");
}


drawMenus();

// -->
</script>

</head>

<body bgcolor="#ffffff" onload="loadLayoutWin3E('quickSupplierView');resultOnLoad();" onresize="setTimeout('resizeGrids()',220);" onunload="closeAllchildren();" >
<tcmis:form action="/quicksupplierviewresults.do" onsubmit="return submitOnlyOnce();">
 
 
 <div class="interface" id="mainPage">

<div class="contentArea">

<div id="resultFrameDiv1" style="margin: 0px 0px 0px 0px;">
<table id="resultsMaskTable1" width="300px;"  border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div id="poHistoryBean" style="display: ;width:300px;height:1200px;" ></div>	
<script type="text/javascript">
<!--
<c:set var="poHistoryDataCount" value='${0}'/>
<c:if test="${!empty poHistoryColl}" >
var poHistoryJsonMainData = new Array();
var poHistoryJsonMainData = {
rows:[
<c:forEach var="poBean" items="${poHistoryColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<fmt:formatDate var="fmtDateConfirmed" value="${status.current.dateConfirmed}" pattern="${dateFormatPattern}"/>
<c:set var="dateConfirmedSortable" value="${status.current.dateConfirmed.time}"/>

<fmt:formatDate var="fmtDateLastReceived" value="${status.current.dateLastReceived}" pattern="${dateFormatPattern}"/>
<fmt:formatNumber var="unitPriceFinal" value="${status.current.unitPrice}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>
/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['<tcmis:jsReplace value="${status.current.inventoryGroupName}" />',
       '${status.current.itemId}','<tcmis:jsReplace value="${status.current.itemDesc}" processMultiLines="true"/>',
       '<tcmis:jsReplace value="${status.current.specList}" processMultiLines="true"/>',
       '${fmtDateConfirmed}',
 	   '${dateConfirmedSortable}',
 	   '${status.current.quantity}','${status.current.currencyId} ${unitPriceFinal}',
 	   '${status.current.unitPrice}', '${status.current.qtyReceived}', 
 	   '${fmtDateLastReceived}','${status.current.dateLastReceived.time}',
 	   '${status.current.quantityVouchered}',
 	   '${status.current.radianPo} - ${status.current.poLine}', 
 	   '${status.current.radianPo}', '${status.current.poLine}', '${status.current.inventoryGroup}']}

 <c:set var="poHistoryDataCount" value='${poHistoryDataCount+1}'/>
 </c:forEach>
]};
</c:if>


<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
var poHistoryConfig = [
{ columnId:"inventoryGroupName",
  columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
  attachHeader:'#text_filter',
  width:10
},
{ columnId:"itemId",
  columnName:'<fmt:message key="label.item"/>',
  attachHeader:'#text_filter',
  width:5
},
{ columnId:"itemIdDesc",
  columnName:'<fmt:message key="label.desc"/>',
  attachHeader:'#text_filter',
  width:15,
  tooltip:"Y"
},
{ columnId:"specList",
  columnName:'<fmt:message key="label.specification"/>',
  width:10,
  tooltip:"Y"
},
{ columnId:"dateConfirmed",
  columnName:'<fmt:message key="label.dateconfirmed"/>',
  sorting:"int",
  width:7,
  align:"left",
  hiddenSortingColumn:"hPromisedDate"
},
{ columnId:"hPromisedDate", 
  sorting:"int"
},
{ columnId:"quantity",
  columnName:'<fmt:message key="label.qty"/>',
  align:"right",
  sorting:"int",
  width:4
},
{ columnId:"displayUnitPrice",
  columnName:'<fmt:message key="label.unitprice"/>',
  align:"right",
  hiddenSortingColumn:"unitPrice",
  sorting:"int",
  width:8
},
{ columnId:"unitPrice",
  sorting:"int"
},
{ columnId:"totalQuantityReceived",
  columnName:'<fmt:message key="label.qtyreceived"/>',
  align:"right",
  sorting:"int",
  width:5
},
{ columnId:"dateLastReceived",
  columnName:'<fmt:message key="label.lastreceiveddate"/>',
  sorting:"int",
  width:7,
  align:"left",
  hiddenSortingColumn:"hdateLastReceived"
},
{ columnId:"hdateLastReceived", 
  sorting:"int"
},
{ columnId:"qtyVoucher",
  columnName:'<fmt:message key="label.qtyvouchered"/>',
  align:"right",
  sorting:"int",
  width:5
},
{ columnId:"radianPo",
  columnName:'<fmt:message key="label.po"/>',
  attachHeader:'#text_filter',
  width:8
},
{ columnId:"hRadianPo"
},
{ columnId:"poLine"
},
{ columnId:"inventoryGroup"
}
];
//-->  
</script>
 
<!-- If the collection is empty say no data found -->
<c:if test="${empty poHistoryColl}">
    <table width="300px;" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
        <tr>
            <td width="100%">
                <fmt:message key="main.nodatafound"/>
            </td>
        </tr>
    </table>
</c:if>
</td></tr>
</table>
</div>


<div id="resultFrameDiv2" style="margin: 0px 0px 0px 0px;">
<table id="resultsMaskTable2" width="300px;"  border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div id="priceListBean" style="display:;width:300px;height:800px;"  ></div>

<script type="text/javascript">
<!--

<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty priceListColl}" >

var lineMap3 = new Array();
var map = null;
var lineMap = new Array();
var line2Map = new Array();
var lineIdMap = new Array();
var rowIdMap = new Array();
var partMap1 = new Array();
var partMap2 = new Array();

var rowSpanCols = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21];
var rowSpanLvl2Cols = [22,23];

<c:set var="gridind" value="0"/>
<c:set var="prePar" value=""/>
<c:set var="samePartCount" value="0"/>
<c:set var="colorIndex" value="-1"/>
<c:forEach var="bean" items="${priceListColl}" varStatus="status">
	<c:set var="curPar" value="${bean.dbuyContractId}"/>
	<tcmis:jsReplace value="${curPar}" var="jsCurPar"/>
	<c:if test="${ curPar == prePar }">
		<c:set var="samePartCount" value="${samePartCount+1}"/>
		partMap2['${jsCurPar}'] = partMap1['${jsCurPar}'];
		partMap1['${jsCurPar}'] = ${gridind+1};
	</c:if>
	<c:if test="${ curPar != prePar }">
		<c:set var="samePartCount" value="1"/>
		<c:set var="firstLineIndex" value="${gridind}"/>
		<c:set var="colorIndex" value="${colorIndex+1}"/>
		partMap1['${jsCurPar}'] = ${gridind+1};
	</c:if>
	
	<bean:size collection="${status.current.priceBreakCollection}" id="resultSize"/>
    lineMap3[${gridind}] = ${colorIndex%2} ;
	line2Map[${gridind}] = ${resultSize+1};
//	alert( ${gridind} +":"+"${prePar}"+":"+"${curPar}");
	<c:if test="${ curPar == prePar }">
		lineMap[${firstLineIndex}] += ${resultSize+1};
//		alert( ${firstLineIndex} +":"+lineMap[${firstLineIndex}] );
	</c:if>
	<c:if test="${ curPar != prePar }">
		lineMap[${gridind}] = ${resultSize+1};
		map = new Array();
	</c:if>
    <c:set var="gridind" value="${gridind+1}"/> 
    lineIdMap[""+${gridind}] = map;
	map[map.length] = ${gridind} ;
    rowIdMap[rowIdMap.length] = ${gridind} ;

    <c:forEach var="bean2" items="${status.current.priceBreakCollection}" varStatus="status2">
		lineMap3[${gridind}] = ${colorIndex%2} ;
	<c:set var="gridind" value="${gridind+1}"/> 
		lineIdMap[""+${gridind}] = map;
		map[map.length] = ${gridind} ;
	    rowIdMap[rowIdMap.length] = ${gridind} ;
	</c:forEach>
	<c:set var="prePar" value="${curPar}"/>
</c:forEach>

<c:set var="prePar" value=""/>

<c:set var="todayval">
	<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="yyyy-MM-dd"/>
</c:set>
		
var priceListJsonMainData = new Array();
var priceListJsonMainData = {
rows:[
<c:forEach var="bean" items="${priceListColl}" varStatus="status">
<c:set var="isGrand" value="N"/>
<tcmis:jsReplace value="${curPar}" var="jsCurPar"/>
<c:set var="curPar" value="${bean.dbuyContractId}"/>
<c:if test="${ curPar != prePar }">
<c:set var="isGrand" value="Y"/>
</c:if>
<fmt:formatDate var="startDatev" value="${bean.startDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="startDate" value="${bean.startDate}" pattern="${dateFormatPattern}"/>
<bean:size collection="${status.current.priceBreakCollection}" id="resultSize"/>

<c:if test="${dataCount > 0}">,</c:if>
<c:set var="dataCount" value='${dataCount+1}'/>
<c:set var="dup">
<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="editButton" value="Add CPP" onclick="dupline(${dataCount})" />
</c:set>

<fmt:formatDate var="startDateVal" value="${bean.startDate}" pattern="yyyy-MM-dd"/>

<tcmis:jsReplace value="${dup}" var="dup"/>

<fmt:formatDate var="updatedDate" value="${bean.updatedDate}" pattern="${dateFormatPattern}"/>
<tcmis:jsReplace value="${bean.itemDesc}" var="itemDesc"/>

{ id:${dataCount},
	  data:[
	   'N',
	   '${bean.inventoryGroup}',
	   '${bean.itemId}', 
	   '${itemDesc}',
	   '${bean.shipToLocationId}',
	   '<tcmis:jsReplace value="${bean.inventoryGroupName}" />',
	   '${bean.priority}',
	   '${startDate}','${bean.startDate.time}',
	   '${bean.currencyId} ${bean.unitPrice}',
	   '${bean.unitPrice}',
	   '1',
	   '<tcmis:jsReplace value="${bean.supplierPartNo}"/>',
       '${bean.minBuyQuantity}',
	   '${bean.minBuyValue}',
	   '${startDatev}',
	   '${startDatev}',
	   '${endDatev}',
	   '1',
	   '${bean.unitPrice}',
	   '${bean.carrier}',
	   '${bean.dbuyContractId}',
	   '${bean.shipToCompanyId}',
	   '${bean.sourcer}',
	   '<tcmis:jsReplace var="carrierDisplay" value="${bean.loadingComments}" processMultiLines="true" />',
	   '${bean.supplyPath}',
	   '${bean.opsEntityId}',
	   '${bean.inventoryGroupHub}'
	   ]
	 }

  <c:forEach var="bean2" items="${status.current.priceBreakCollection}" varStatus="status2">
  <c:if test="${dataCount > 0}">,</c:if>
  <c:set var="dataCount" value='${dataCount+1}'/>
  <c:set var="dup">
  <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="editButton" value="Add CPP" onclick="dupline(${dataCount})" />
  </c:set>
  <tcmis:jsReplace value="${dup}" var="dup"/>
  <fmt:formatDate var="updatedDate" value="${bean.updatedDate}" pattern="${dateFormatPattern}"/>
  { id:${dataCount},
	  data:[
	   'N',
	   '${bean.inventoryGroup}',
	   '${bean.itemId}',  
	   '${itemDesc}',
	   '${bean.shipToLocationId}',
	   '<tcmis:jsReplace value="${bean.inventoryGroupName}" />',
	   '${bean.priority}',
	   '${startDate}','${bean.startDate.time}',
	   '${bean.currencyId} ${bean2.unitPrice}',
	   '${bean2.unitPrice}',
	   '${bean2.breakQuantity}',
	   '<tcmis:jsReplace value="${bean.supplierPartNo}" />',
	   '${bean.minBuyQuantity}',
	   '${bean.minBuyValue}',
	   '${startDatev}',
	   '${startDatev}',
	   '${endDatev}',
	   '${bean2.breakQuantity}',
	   '${bean2.unitPrice}',
	   '${bean.carrier}',
	   '${bean.dbuyContractId}',
	   '',
	   '',
	   '',
	   '${bean.opsEntityId}',
	   '${bean.inventoryGroupHub}'
	   ]
	 }

  </c:forEach>

<c:set var="prePar" value="${curPar}"/>
</c:forEach>
]};

var rowSpanLvl2Map = line2Map;
</c:if>

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
<c:set var="leadtimeindays"><fmt:message key="label.leadtimeindays"/></c:set>
var priceListConfig = [
{
             	columnId:"permission"
             },
             {
             	columnId:"inventoryGroup"
             },
             {  columnId:"itemId",
           	    columnName:'<fmt:message key="label.item"/>',
           	    attachHeader:'#text_filter',
           	    width:4
           	},
           	{	columnId:"itemDesc",
           		columnName:'<fmt:message key="label.description"/>',
           		attachHeader:'#text_filter',
           		tooltip:true,
           		width:10
           	},
           	{	columnId:"shipToLocationId",
           		columnName:'<fmt:message key="label.shipto"/>',
           		attachHeader:'#text_filter',
           		width:5
           	},
           	{
           	  	columnId:"inventoryGroupName",
           		columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
           		attachHeader:'#text_filter',
           		width:8
           	},
           	{	columnId:"priority",
           		columnName:'<fmt:message key="label.priority"/>',
           		width:4
           	},
           	{
             	columnId:"startDateDisplay",
             	columnName:'<fmt:message key="label.startDate"/>',
             	hiddenSortingColumn:"hStartDate",
             	sorting:"int",
           		width:6
            },
            {
				columnId:"hStartDate",
				sorting:"int"
			},
           	{
             	columnId:"unitPriceDisplay",
             	columnName:'<fmt:message key="label.unitprice"/>',
             	hiddenSortingColumn:"unitPrice",
             	sorting:'int',
             	align:'right',
           	  	width:7
             },
             {
				columnId:"unitPrice",
				sorting:"int"
			 },
             {
             	columnId:"breakQuantity",
             	columnName:'<fmt:message key="label.qty"/>',
             	sorting:'int',
             	align:'right',
           	  	width:3
             },
           	{	columnId:"supplierPartNo",
           		columnName:'<fmt:message key="label.supplierpartnum"/>',
           		attachHeader:'#text_filter',
           	  	width:8
           	},
           	{
		  		columnId:"minBuyQuantity",
		  		columnName:'<fmt:message key="label.minimumbuyqty"/>',
		  		align:'right',
		  		sorting:'int',
			  	width:6
		  	},
			{
		  		columnId:"minBuyValue",
		  		columnName:'<fmt:message key="label.minimumordervalue"/>',
		  		sorting:'int',
			  	width:6
		  	},
           	{
           		columnId:"startDate"
           	},
           	{
           		columnId:"oldStartDate"
           	},
           	{
           		columnId:"endDate"
           	},
           	  {
           	  	columnId:"oldbreakQuantity"
           	  },
           	  {
           	  	columnId:"oldUnitPrice"
           	  },
           		{	columnId:"carrier"
           		},
           		{	columnId:"dbuyContractId"
           		},
           		{	columnId:"shipToCompanyId"
           		},
           		{	columnId:"sourcer"
           		},
           		{	columnId:"loadingComments"
           		},
           		{	columnId:"supplyPath"
           		},
           		{	columnId:"opsEntityId"
           		},
           		{	columnId:"inventoryGroupHub"
           		}
];
//-->  
</script>
 
<!-- If the collection is empty say no data found -->
<c:if test="${empty priceListColl}">
    <table width="300px;" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
        <tr>
            <td width="100%">
                <fmt:message key="main.nodatafound"/>
            </td>
        </tr>
    </table>
</c:if>
</td></tr>
</table>
</div>


<div id="resultFrameDiv3" style="margin: 0px 0px 0px 0px;">

<table id="resultsMaskTable3" width="300px;" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div id="InvoiceSearchBean" style="display: ;width:300px;height:1200px;" ></div>	
<script type="text/javascript">
<!--
<c:set var="invoiceDataCount" value='${0}'/>
<c:if test="${!empty voucherReportColl}" >
var invoiceJsonMainData = new Array();
var invoiceJsonMainData = {
rows:[
<c:forEach var="bean" items="${voucherReportColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
<fmt:formatNumber var="fmtNetInvoiceAmount" value="${status.current.netInvoiceAmount}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>
 data:[
  '${bean.radianPo}',
  '<tcmis:jsReplace value="${bean.buyerName}"/>',
  '${bean.buyerPhone}',
  '${bean.supplierInvoiceId}',
  '<fmt:formatDate value="${status.current.invoiceDate}" pattern="${dateFormatPattern}"/>','${bean.invoiceDate.time}',
  '${status.current.currencyId} ${fmtNetInvoiceAmount}', '${status.current.netInvoiceAmount}', 
  '${bean.status}',
  '<tcmis:jsReplace value="${bean.apApproverName}"/>',
  '<fmt:formatDate value="${status.current.approvedDate}" pattern="${dateFormatPattern}"/>','${bean.approvedDate.time}',
  '<tcmis:jsReplace value="${bean.apNote}" processMultiLines="true"/>',
  '${bean.radianPo}', '${bean.voucherId}']}

 <c:set var="invoiceDataCount" value='${invoiceDataCount+1}'/>
 </c:forEach>
]};
</c:if>
var invoiceConfig = [
	{columnId:"displayradianPo", columnName:'<fmt:message key="label.po"/>', attachHeader:'#text_filter', width:5 },
	{columnId:"buyerName", columnName:'<fmt:message key="label.buyer"/>', attachHeader:'#text_filter', tooltip:true, width:8 },
	{columnId:"buyerPhone", columnName:'<fmt:message key="label.phone"/>', width:7 },
	{columnId:"supplierInvoiceId", columnName:'<fmt:message key="label.invoice"/>', attachHeader:'#text_filter', align:'left', sorting:'int', width:8 },
	{columnId:"invoiceDate", columnName:'<fmt:message key="label.invoicedate"/>', hiddenSortingColumn:'hInvoiceDate', align:'right', sorting:'int', width:6 },
	{columnId:"hInvoiceDate", sorting:'int'} ,
	{columnId:"netInvoiceAmount", columnName:'<fmt:message key="label.amount"/>',  hiddenSortingColumn:'hNetInvoiceAmount', align:'right', sorting:'int', width:6 },
	{columnId:"hNetInvoiceAmount", sorting:'int'} ,
	{columnId:"status", columnName:'<fmt:message key="label.status"/>', width:6 },
	{columnId:"apApproverName", columnName:'<fmt:message key="label.approvedby"/>', width:7 },
	{columnId:"approvedDate", columnName:'<fmt:message key="label.approveddate"/>', sorting:'int', hiddenSortingColumn:"hApprovedDate", width:6 },
	{columnId:"hApprovedDate", sorting:'int' } ,
	{columnId:"apNote", columnName:'<fmt:message key="label.comment"/>' },
	{columnId:"radianPo"}, 
	{columnId:"voucherId" } 
];
//-->  
</script>
 
<!-- If the collection is empty say no data found -->
<c:if test="${empty voucherReportColl}">
    <table width="300px;" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
        <tr>
            <td width="100%">
                <fmt:message key="main.nodatafound"/>
            </td>
        </tr>
    </table>
</c:if>

</td></tr>
</table>
</div>


<div id="resultFrameDiv4" style="margin: 0px 0px 0px 0px;">
<table id="resultsMaskTable4" width="300px;"  border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div id="contactBean" style="display:;width:300px;height:800px;"  ></div>

<script type="text/javascript">
<!--
<c:set var="contactDataCount" value='${0}'/>
<c:if test="${!empty pOSupplierContactBeanCollection}" >

var contactJsonMainData = {
        rows:[
<c:forEach var="bean" items="${pOSupplierContactBeanCollection}" varStatus="status">
	<c:if test="${status.index > 0}">,</c:if>
	
	<c:if test="${!empty status.current.email}"><c:set var="email"><a href="mailto:<tcmis:jsReplace value="${status.current.email}"/>"><tcmis:jsReplace value="${status.current.email}"/></a></c:set></c:if>  
	<c:if test="${empty status.current.email}"><c:set var="email" value=" " /></c:if>  
		
{

 id:${status.index +1},
          data:[
  	   'N',
       '<tcmis:jsReplace value="${status.current.lastName}" /> <tcmis:jsReplace value="${status.current.firstName}" />',
       '<tcmis:jsReplace value="${status.current.nickname}" />','N',
       '<tcmis:jsReplace value="${status.current.contactType}" />','${status.current.phone} ${status.current.phoneExtension}','${status.current.fax}',
  	   '${email}','<tcmis:jsReplace value="${status.current.email}" />',
  	   '${status.current.contactId}',
  	 	'<tcmis:jsReplace value="${status.current.supplier}" />'
  	 
 ]}
 	<c:set var="contactDataCount" value='${contactDataCount+1}'/>
     </c:forEach>
    ]};
</c:if>

var contactConfig = [
{ columnId:"permission"
},
{ columnId:"wholeName",
  columnName:'<fmt:message key="label.contactname"/>',
  tooltip:"Y",
  width:8
},
{ columnId:"nickname",
  columnName:'<fmt:message key="label.nickname"/>',
  width:6
},
{ columnId:"contactTypePermission"
},
{     columnId:"contactType",
	  columnName:'<fmt:message key="label.contacttype"/>',
	  tooltip:"Y",
	  width:7
},
{ columnId:"phone",
  columnName:'<fmt:message key="label.phone"/> - <fmt:message key="label.ext"/>'
},
{ columnId:"fax",
  columnName:'<fmt:message key="label.fax"/>'
},
{ columnId:"emailDisplay",
  columnName:'<fmt:message key="label.email"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"email"
},
{ columnId:"contactId"
},
{ columnId:"supplier"
}
];
//-->  
</script>
 
<!-- If the collection is empty say no data found -->
<c:if test="${empty pOSupplierContactBeanCollection}">
    <table width="300px;" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
        <tr>
            <td width="100%">
                <fmt:message key="main.nodatafound"/>
            </td>
        </tr>
    </table>
</c:if>
</td></tr>
</table>
</div>


<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
<div id="hiddenElements" >
 <input name="uAction" id="uAction" type="hidden" value="">
 <input name="personnelId" id="personnelId" value="${personnelId}" type="hidden"/>
 <input name="poHistoryTotalLines" id="poHistoryTotalLines" value="<c:out value="${poHistoryDataCount}"/>" type="hidden"/>
 <input name="priceListTotalLines" id="priceListTotalLines" value="<c:out value="${dataCount}"/>" type="hidden"/>
 <input name="invoiceTotalLines" id="invoiceTotalLines" value="<c:out value="${invoiceDataCount}"/>" type="hidden"/>
 <input name="contactTotalLines" id="contactTotalLines" value="<c:out value="${contactDataCount}"/>" type="hidden"/>
 
 <input name="opsEntityId" id="opsEntityId" value="<c:out value="${input.opsEntityId}"/>" type="hidden"/>
 <input name="inventoryGroup" id="inventoryGroup" value="<c:out value="${input.inventoryGroup}"/>" type="hidden"/>
 <input name="supplier" id="supplier" value="<c:out value="${input.supplier}"/>" type="hidden"/>
 <input name="supplierName" id="supplierName"" value="<c:out value="${input.supplierName}"/>" type="hidden"/>
 <input name="hub" id="hub"" value="<c:out value="${input.hub}"/>" type="hidden"/>
 <input name="customerId" id="customerId" value="<c:out value="${input.customerId}"/>" type="hidden"/>
 <input name="searchKey" id="searchKey" value="INVENTORY GROUP" type="hidden"/>
 <input name="days" id="days"  value="<c:out value="${input.days}"/>" type="hidden"/>
</div>
<!-- Hidden element Ends -->

</div> <!-- close of contentArea -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>

