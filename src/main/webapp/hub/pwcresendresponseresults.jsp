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

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script> 
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

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
<script type="text/javascript" src="/js/hub/pwcresendresponseresults.js"></script>
    
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
<fmt:message key="pwcresendresponse.title"/>
</title>
  
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
ok:"<fmt:message key="label.ok"/>",
needdate:"<fmt:message key="label.needdate"/>",
confirmeddate:"<fmt:message key="label.date"/> <fmt:message key="label.poconfirmed"/>",
customerpo:"<fmt:message key="label.customerpo"/>",
customerpoline:"<fmt:message key="label.mrline"/>",
facpartnumber:"<fmt:message key="label.partnumber"/>",
status:"<fmt:message key="label.status"/>",
detail:"<fmt:message key="label.detail"/>",
itemid:"<fmt:message key="label.itemid"/>",
itemdescription:"<fmt:message key="label.itemdescription"/>",
errors:"<fmt:message key="label.errors"/>",  
orderqty:"<fmt:message key="label.orderqty"/>",
orderprice:"<fmt:message key="label.orderprice"/>",
catalogprice:"<fmt:message key="label.catalogprice"/>",
allocatedquantity:"<fmt:message key="label.allocatedqty"/>",
allocationype:"<fmt:message key="label.allocationtype"/>",
allocation:"<fmt:message key="label.allocation"/>",
po:"<fmt:message key="label.ponumber"/>",
poline:"<fmt:message key="label.lineitem"/>",
mrnumber:"<fmt:message key="label.mrnumber"/>",
currency:"<fmt:message key="label.currency"/>",
currentexchangerate:"<fmt:message key="label.currentexchangerate"/>",
markuppercent:"<fmt:message key="label.markup"/>",
unitprice:"<fmt:message key="label.unitprice"/>",
ordertype:"<fmt:message key="label.ordertype"/>",
dropshipmarkup:"<fmt:message key="label.dropshipmarkup"/>",
oormarkup:"<fmt:message key="label.oormarkup"/>",
minmaxmarkup:"<fmt:message key="label.minmaxmarkup"/>",
ackncode:"<fmt:message key="label.acknowledgementcode"/>",
prnumber:"<fmt:message key="label.prnumber"/>",
insertdate:"<fmt:message key="label.datereceived"/>",
documenttype:"<fmt:message key="label.doctype"/>",
documentnumber:"<fmt:message key="label.documentno"/>",
documentctrlno:"<fmt:message key="label.documentctrlno"/>",
allocationstatus:"<fmt:message key="label.allocationstatus"/>",
allocatedqty:"<fmt:message key="label.allocatedqty"/>",
unitofsale:"<fmt:message key="label.unitofsale"/>",
changeordersequence:"<fmt:message key="label.changeordersequence"/>",
changeorderinfo:"<fmt:message key="label.changeorderinfo"/>",
datetodeliver:"<fmt:message key="label.deliverydate"/>",
batch:"<fmt:message key="label.batch"/>",
poprice:"<fmt:message key="label.poprice"/>",
markupperccent:"<fmt:message key="label.markuppct"/>",
currentprice:"<fmt:message key="label.currentprice"/>",
acknowledgedprice:"<fmt:message key="label.ackprice"/>",
acknowledgeddate:"<fmt:message key="label.ackdate"/>",
ackpriceexchangerate:"<fmt:message key="label.ackexchangerate"/>",
acksent:"<fmt:message key="label.acksent"/>",
purchaseorder:"<fmt:message key="label.purchaseorder"/>",
priceeditable:"<fmt:message key="label.priceeditable"/>",
ackstatus:"<fmt:message key="label.ackstatus"/>",
pocurrency:"<fmt:message key="label.pocurrency"/>",
purchaseorder:"<fmt:message key="label.purchaseorder"/>",
poqty:"<fmt:message key="label.poqty"/>"
};

var pwcResendResponseConfig = [];

if ("${param.lookupClass}" == "poLookup") {
	pwcResendResponseConfig = [
		{columnId:"permission"},
		{
			columnId:"ok", 
			//columnName:messagesData.ok, 
			width:3, 
			align:"center", 
			type:"hchstatus",
   			onChange: selectRow
		},
		{
			columnId:"radianPoNoDisplay", 
			columnName:messagesData.po, 
			width:10, 
			align:"left"
		},
		{
			columnId:"editPoPrice", 
			columnName:messagesData.priceeditable, 
			width:7, 
			align:"center"
		},
		{
			columnId:"customerPoNoDisplay",
			columnName:messagesData.customerpo,
			width:10,
			align:"left",
			sorting:"int",
			tooltip:"Y"
		},
		{
			columnId:"confirmedDate", 
			columnName:messagesData.confirmeddate, 
			width:8, 
			align:"left", 
			sorting:"int"
		},
		{
			columnId:"itemId", 
			columnName:messagesData.itemid, 
			width:6,
			align:'right', 
			sorting:"int"
		},
		{
			columnId:"orderQuantity", 
			columnName:messagesData.poqty, 
			width:6, 
			align:"right", 
			sorting:"int"
		},
		{
			columnId:"unitPrice", 
			columnName:messagesData.unitprice, 
			width:7, 
			align:"left", 
			sorting:"int"
		},
		{
			columnId:"currency",
			columnName:messagesData.currency,
			width:6,
			align:"left"
		},
		{
			columnId:"priceExchangeRate",
			columnName:messagesData.ackpriceexchangerate,
			width:6,
			align:"right"
		},
		{
			columnId:"currentExchangeRate",
			columnName:messagesData.currentexchangerate,
			width:6,
			align:"right"
		},
		{
			columnId:"orderType",
			columnName:messagesData.ordertype,
			width:6,
			align:"center"
		},
		{
			columnId:"dropshipMarkup",
			columnName:messagesData.dropshipmarkup,
			width:6,
			align:"right"
		},
		{
			columnId:"oorMarkup",
			columnName:messagesData.oormarkup,
			width:6,
			align:"right"
		},
		{
			columnId:"mmMarkup",
			columnName:messagesData.minmaxmarkup,
			width:6,
			align:"right"
		},
		{
			columnId:"ackSent",
			columnName:messagesData.acksent,
			width:6,
			align:"left"
		}
	];
}
else if ("${param.lookupClass}" == "orderLookup") {
	pwcResendResponseConfig = [
   		{columnId:"permission"},
   		{
   			columnId:"ok", 
   			columnName:messagesData.ok, 
   			width:3, 
   			align:"center", 
   			type:"hchstatus",
   			onChange: selectRow,
   			submit:true
   		},
   		{
   			columnId:"acknowledgementCode", 
   			columnName:messagesData.acknowledgementcode, 
   			width:6, 
   			align:"left", 
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"customerPoNoDisplay",
   			columnName:messagesData.customerpo,
   			width:10,
   			align:"left",
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"prNumberDisplay",
   			columnName:messagesData.mrnumber,
   			width:6,
   			align:"left",
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"dateInserted",
   			columnName:messagesData.insertdate,
   			width:10,
   			align:"left",
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"allocation",
   			columnName:messagesData.allocation,
   			width:10,
   			align:"left",
   			submit:false
   		},
   		{
   			columnId:"documentControlNumber",
   			columnName:messagesData.documentctrlnumber,
   			width:10,
   			align:"right",
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"itemId", 
   			columnName:messagesData.itemid, 
   			width:6,
   			align:'right', 
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"status", 
   			columnName:messagesData.allocationstatus, 
   			width:7,
   			align:"left", 
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"orderQuantity", 
   			columnName:messagesData.orderqty, 
   			width:6, 
   			align:"right", 
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"allocatedQuantity", 
   			columnName:messagesData.allocatedqty, 
   			width:6, 
   			align:"right", 
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"unitOfSale", 
   			columnName:messagesData.unitofsale, 
   			width:6, 
   			align:"right", 
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"catalogPrice", 
   			columnName:messagesData.catalogprice, 
   			width:7, 
   			align:"left", 
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"orderPrice", 
   			columnName:messagesData.orderprice, 
   			width:7, 
   			align:"left", 
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"changeOrderSequence", 
   			columnName:messagesData.changeordersequence, 
   			width:6, 
   			align:"right", 
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"dateToDeliver", 
   			columnName:messagesData.datetodeliver, 
   			width:8, 
   			align:"left", 
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"requestedDeliveryDate", 
   			columnName:messagesData.needdate, 
   			width:8, 
   			align:"left", 
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"facPartNo", 
   			columnName:messagesData.facpartnumber, 
   			width:8, 
   			align:"left", 
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"itemDesc",
   			columnName:messagesData.itemdescription,
   			width:15,
   			align:"left",
   			submit:false,
   			tooltip:"Y"
   		},
   		{
   			columnId:"allocationBatch",
   			width:6,
   			align:"right",
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"statusDetail",
   			width:10,
   			align:"left",
   			submit:false
   		},
   		{
   			columnId:"radianPo", 
   			columnName:messagesData.purchaseorder, 
   			width:8, 
   			align:"left", 
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"poPrice", 
   			columnName:messagesData.poprice, 
   			width:8, 
   			align:"left", 
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"poCurrency",
   			columnName:messagesData.currency,
   			width:6,
   			align:"left",
   			submit:false
   		},
   		{
   			columnId:"datePoConfirmed", 
   			columnName:messagesData.confirmeddate, 
   			width:8, 
   			align:"left", 
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"markupPercent",
   			columnName:messagesData.markuppercent,
   			width:6,
   			align:"right",
   			submit:false
   		},
   		{
   			columnId:"currentMarkupPrice",
   			columnName:messagesData.currentmarkupprice,
   			width:6,
   			align:"right",
   			submit:false
   		},
   		{
   			columnId:"exchangeRate",
   			columnName:messagesData.currentexchangerate,
   			width:6,
   			align:"right",
   			submit:false
   		},
   		{
   			columnId:"orderPriceToday",
   			columnName:messagesData.currentprice,
   			width:6,
   			align:"left",
   			submit:false
   		},
   		{
   			columnId:"acknowledgedPrice",
   			columnName:messagesData.acknowledgedprice,
   			width:6,
   			align:"left",
   			submit:false
   		},
   		{
   			columnId:"acknowledgedDate",
   			columnName:messagesData.acknowledgeddate,
   			width:8,
   			align:"left",
   			submit:false
   		},
   		{
   			columnId:"ackPriceExchangeRate",
   			columnName:messagesData.ackpriceexchangerate,
   			width:6,
   			align:"right",
   			submit:false
   		},
   		{
   			columnId:"ackSent",
   			columnName:messagesData.acksent,
   			width:6,
   			align:"center",
   			submit:false
   		},
   		{columnId:"prNumber",submit:true},
   		{columnId:"customerPoLineNo",submit:true},
   		{columnId:"lineItem",submit:true},
   		{columnId:"customerPoNo",submit:true}
   	];
}

else {
	pwcResendResponseConfig = [
   		{columnId:"permission"},
   		{
   			columnId:"ok", 
   			columnName:messagesData.ok, 
   			width:3, 
   			align:"center", 
   			type:"hchstatus",
   			onChange: selectRow,
   			submit:true
   		},
   		{
   			columnId:"customerPoNoDisplay",
   			columnName:messagesData.customerpo,
   			width:10,
   			align:"left",
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"customerPoLineNoDisplay",
   			columnName:messagesData.customerpoline,
   			width:7,
   			align:"left",
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"needDate",
   			columnName:messagesData.needdate,
   			width:7,
   			align:"left",
   			sorting:"int",
   			hiddenSortingColumn:'hiddenNeedDateSort',
   			submit:false
   		},
   		{
   			columnId:"status",
   			columnName:messagesData.status,
   			width:5,
   			align:"left",
   			submit:false
   		},
   		{
   			columnId:"statusDetail",
   			columnName:messagesData.detail,
   			width:20,
   			align:"left"
   		},
   		{
   			columnId:"facPartNo",
   			columnName:messagesData.facpartnumber,
   			width:6,
   			align:"left",
   			submit:false
   		},
   		{
   			columnId:"itemId",
   			columnName:messagesData.itemid,
   			width:6,
   			align:"right",
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"itemDesc",
   			columnName:messagesData.itemdescription,
   			width:18,
   			align:"left",
   			submit:false,
   			tooltip:"Y"
   		},
   		{
   			columnId:"orderQuantity",
   			columnName:messagesData.orderqty,
   			width:6,
   			align:"right",
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"orderPrice",
   			columnName:messagesData.orderprice,
   			width:7,
   			align:"right",
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"catalogPrice",
   			columnName:messagesData.catalogprice,
   			width:7,
   			align:"right",
   			sorting:"int",
   			submit:false
   		},
   		{
   			columnId:"allocatedQuantity",
   			columnName:messagesData.allocatedquantity,
   			width:7,
   			align:"left",
   			submit:false
   		},
   		{
   			columnId:"docNum",
   			columnName:messagesData.allocation,
   			width:17,
   			align:"left",
   			sorting:"int",
   			submit:false
   		},
   		{columnId:"hiddenNeedDateSort",
   			submit:false},
   		{columnId:"prNumber",
   			submit:true},
   		{columnId:"customerPoLineNo",
   	   		submit:true},
   		{columnId:"lineItem",
   	   		submit:true},
   		{columnId:"customerPoNo",
   	   		submit:true}
   	];
}

var pwcResendResponseGridConfig = {
		divName:"pwcResendResponseViewBean",
		beanData:"jsonMainData",
		beanGrid:"mygrid",
		config:"pwcResendResponseConfig",
		rowSpan: false,
		noSmartRender: true,
		submitDefault:true,
		onRowSelect:selectRow,
		onAfterHaasRenderRow:setRowStatusColors
};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad();" >

<tcmis:form action="/pwcresendresponseresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>   
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>

<div class="interface" id="resultsPage" style="">

<div class="backGroundContent">

 <!--  result page section start -->
<div id="pwcResendResponseViewBean" style="height:400px;" style="display: none;"></div>

<c:if test="${pwcResendResponseViewBeanCollection != null}" >

<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty pwcResendResponseViewBeanCollection}" >
<script type="text/javascript">
<!-- 
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="pwcResendResponse" items="${pwcResendResponseViewBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<%--<tcmis:jsReplace var="itemDesc" value="${status.current.itemDesc}" processMultiLines="true" />--%>  

<c:set var="readonly" value='N'/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="Inventory" inventoryGroup="${status.current.inventoryGroup}">
<c:set var="showUpdateLink" value='Y'/>
</tcmis:inventoryGroupPermission>

/*The row ID needs to start with 1 per their design.*/
<%--
{ id:${status.index +1},	
 data:[
  '${readonly}',  '',  
  '<a href="tcmIS/supply/purchaseorder.do?po=${status.current.customerPoNo}&Action=searchlike&subUserAction=po">${status.current.customerPoNo}-${status.current.customerPoLineNo}</a>', 
  '${status.current.prNumber}-${status.current.lineItem}','${fmtNeedDate}' ,
  '${status.current.status}','<tcmis:jsReplace value="${status.current.statusDetail}"/>', '<tcmis:jsReplace value="${status.current.facPartNo}"/>',
  '${status.current.itemId}','${itemDesc}',
  '${status.current.orderQuantity}','${status.current.orderPrice}',
  '${status.current.catalogPrice}',
  '${status.current.allocatedQuantity}',
  <c:choose>
  <c:when test="${status.current.docType != null && status.current.docType eq 'OV'}">
      'Receipt - ${status.current.docNum}'
  </c:when>
  <c:otherwise>
    '${status.current.docNum}'
  </c:otherwise>
  </c:choose>
 ,'${needDateSortable}','${status.current.prNumber}','${status.current.customerPoLineNo}','${status.current.lineItem}','${status.current.customerPoNo}']}  
 --%>
{ id:${status.index +1},
 <c:choose>
 <c:when test="${param.lookupClass eq 'poLookup'}">
 	 <fmt:formatDate var="fmtConfDate" value="${status.current.dateConfirmed}" pattern="${dateFormatPattern}"/>
 data:[
	  '${readonly}', '', 
	  '<a href="#" onclick="showRadianPo(\'${status.current.radianPo}\')">${status.current.radianPo}-${status.current.poLine}</a>',
	  '${status.current.editPoPrice}',
	  '${status.current.customerPoNo}',
	  '${fmtConfDate}', '${status.current.itemId}',
	  '${status.current.quantity}','${status.current.unitPrice}',
	  '${status.current.currencyId}','${status.current.priceExchangeRate}',
	  '${status.current.currentExchangeRate}', '${status.current.orderType}',
	  '${status.current.dropshipMarkup}','${status.current.oorMarkup}','${status.current.mmMarkup}',
	  '${status.current.ackSent}']
 </c:when>
 <c:when test="${param.lookupClass eq 'orderLookup'}">
 	 <fmt:formatDate var="fmtInsertDate" value="${status.current.dateInserted}" pattern="${dateFormatPattern}"/>
	 <fmt:formatDate var="fmtDeliverDate" value="${status.current.dateToDeliver}" pattern="${dateFormatPattern}"/>
	 <fmt:formatDate var="fmtReqDeliverDate" value="${status.current.requestedDeliveryDate}" pattern="${dateFormatPattern}"/>
	 <fmt:formatDate var="fmtDatePoConfirmed" value="${status.current.datePoConfirmed}" pattern="${dateFormatPattern}"/>
	 <fmt:formatDate var="fmtAckDate" value="${status.current.acknowledgedDate}" pattern="${dateFormatPattern}"/>
	 <c:if test="${status.current.status ne 'BILLED'}">
	 	<c:set var="readonly" value='Y'/>
	 </c:if>
 data:[
 	  '${readonly}', '', '${status.current.acknowledgementCode}',
 	  '${status.current.customerPoNo}-${status.current.customerPoLineNo}',
 	  '${status.current.prNumber}-${status.current.lineItem}',
 	  '${fmtInsertDate}', '${status.current.docType} - ${status.current.docNum}',
 	  '${status.current.documentControlNumber}',
 	  '${status.current.itemId}','${status.current.status}',
 	  '${status.current.orderQuantity}','${status.current.allocatedQuantity}',
 	  '${status.current.unitOfSale}','${status.current.catalogPrice}',
 	  '${status.current.orderPrice}','${status.current.changeOrderSequence}',
 	  '${fmtDeliverDate}','${fmtReqDeliverDate}',
 	  '${status.current.facPartNo}','<tcmis:jsReplace value="${status.current.itemDesc}" processMultiLines="true" />',
 	  '${status.current.allocationBatch}',
 	  '${status.current.statusDetail}','${status.current.radianPo}',
 	  '${status.current.poPrice}','${status.current.poCurrency}',
 	  '${fmtDatePoConfirmed}','${status.current.markupPercent}',
 	  '${status.current.currentMarkupPrice}', '${status.current.exchangeRate}',
 	  '${status.current.orderPriceToday}',
 	  '${status.current.acknowledgedPrice}','${fmtAckDate}',
 	  '${status.current.ackPriceExchangeRate}','${status.current.ackSent}',
 	  '${status.current.prNumber}','${status.current.customerPoLineNo}','${status.current.lineItem}','${status.current.customerPoNo}']
 </c:when>
 <c:otherwise>
	  <fmt:formatDate var="fmtNeedDate" value="${status.current.requestedDeliveryDate}" pattern="${dateFormatPattern}"/>
 data:[
       'Y',  '',  '${status.current.customerPoNo}-${status.current.customerPoLineNo}', 
       '${status.current.prNumber}-${status.current.lineItem}','${fmtNeedDate}' ,
       '${status.current.status}','<tcmis:jsReplace value="${status.current.statusDetail}"/>', 
       '<tcmis:jsReplace value="${status.current.facPartNo}"/>',
       '${status.current.itemId}','<tcmis:jsReplace value="${status.current.itemDesc}" processMultiLines="true" />',
       '${status.current.orderQuantity}','${status.current.orderPrice}',
       '${status.current.catalogPrice}',
       '${status.current.allocatedQuantity}',
       <c:choose>
       <c:when test="${status.current.docType != null && status.current.docType eq 'OV'}">
           'Receipt - ${status.current.docNum}'
       </c:when>
       <c:otherwise>
         '${status.current.docNum}'
       </c:otherwise>
       </c:choose>
      ,'${needDateSortable}','${status.current.prNumber}','${status.current.customerPoLineNo}','${status.current.lineItem}','${status.current.customerPoNo}']
 </c:otherwise>
 </c:choose>
}
	  
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach> 
]};
//-->
</script>
</c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty pwcResendResponseViewBeanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td>
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>

<!-- Search results end -->
</c:if>


<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<!--This sets the start time for time elapesed.-->
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
<input name="lookupAction" id="lookupAction" type="hidden" value="${param.lookupAction}"/>
<input name="action" id="action" type="hidden" value=""/>
<input type="hidden" id="orderNumberSearch" name="orderNumberSearch" value="${param.orderNumberSearch}"/>
<input name="lookupClass" id="lookupClass" value="${param.lookupClass}" type="hidden"/>

</div>
<!-- Hidden elements end -->

</div>
<!-- close of background content -->

</div> <!-- close of interface -->

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