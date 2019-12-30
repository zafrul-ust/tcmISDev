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
<script type="text/javascript" src="/js/hub/transactions.js"></script>

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

<title><fmt:message key="transactions.title" /></title>


<script language="JavaScript" type="text/javascript">

//add all the javascript messages here, this for internationalization of client side javascript messages
// Added all column names to the messagesData array.
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"
};
var gridConfig = {
		divName:'transactionTrackingViewBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'mygrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not. Set this to -1 to disable rowSpan and smart rendering, but the sorting will still work
		submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:false,     // this will open the txt cell type to enter notes by single click
	    onRowSelect:doOnRowSelected,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:selectRightclick,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
		onBeforeSorting:onBeforeSorting,			 // the onBeforeSorting event function to be attached, as in beanGrid.attachEvent("onBeforeSorting",selectRow));
	    noSmartRender:false // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
	};

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set> 
<c:set var="quantity"><fmt:message key="label.quantity"/></c:set>
<c:set var="cost"><fmt:message key="label.cost"/></c:set>
<c:set var="landedcost"><fmt:message key="label.landedcost"/></c:set>
var config = [
  {
  	columnId:"permission"
  },
    {
  	columnId:"transactionType",
  	columnName:'<fmt:message key="label.type"/>',
  	tooltip:"Y",  
  	width:5
  },
  {
  	columnId:"inventoryGroupName",
  	tooltip:"Y",
  	columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
  	width:10
  },
  {
	  	columnId:"receiptId",
	  	columnName:'<fmt:message key="label.receiptid"/>',
	  	type:'hlink',  
	    align:'center',
	  	width:8
	    //onChange:getReceiptNotes
},
{
  	columnId:"receiverName",
  	columnName:'<fmt:message key="transactions.receivername"/>',
  	tooltip:"Y",
    width:8

},
{
  	columnId:"lotStatus",
  	columnName:'<fmt:message key="label.lotstatus"/>',
  	tooltip:"Y",
    width:10

},
{
  	columnId:"picklistId",
  	columnName:'<fmt:message key="transactions.picklistid"/>', 
  	tooltip:"Y"

},
{
  	columnId:"issueId",
  	columnName:'<fmt:message key="label.issueid"/>', 
  	tooltip:"Y"
},
{
  	columnId:"issuerName",
  	columnName:'<fmt:message key="transactions.issuername"/>', 
  	tooltip:"Y"
},
{
  	columnId:"itemId",
  	columnName:'<fmt:message key="label.itemid"/>', 
  	tooltip:"Y",
    width:6
},
{
  	columnId:"itemDescription",
  	columnName:'<fmt:message key="label.itemdesc"/>', 
  	tooltip:"Y",
    align:'left'
},
{
  	columnId:"packaging"
},
{
  	columnId:"quantity",
  	columnName:'<tcmis:jsReplace value="${quantity}"/>',
  	tooltip:"Y",
    sorting:'int',
    align:'right',
    width:5
},
{
  	columnId:"homecurrency",
  	columnName:'<fmt:message key="label.currency"/>',
  	tooltip:"Y",
    width:6
},

{
  	columnId:"cost",
  	columnName:'<tcmis:jsReplace value="${cost}"/>',
  	tooltip:"Y",
    sorting:'int'
},
{
  	columnId:"landedcost",
  	columnName:'<tcmis:jsReplace value="${landedcost}"/>',
  	tooltip:"Y",
    sorting:'int'
},

{
  	columnId:"mfgLot",
  	columnName:'<fmt:message key="label.mfglot"/>', 
  	tooltip:"Y",
    width:8
},
{
  	columnId:"bin",
  	columnName:'<fmt:message key="label.bin"/>',  
  	tooltip:"Y",
    width:8
},
{
  	columnId:"dateOfReceipt",
  	columnName:'<fmt:message key="receivedreceipts.label.dor"/>',   
  	tooltip:"Y",
  	align:'center',
    sorting:'int'
},
{
  	columnId:"deliveryTicket",
  	columnName:'<fmt:message key="label.deliveryticket"/>',
  	tooltip:"Y"
},
{
  	columnId:"labelStorageTemp",
  	columnName:'<fmt:message key="transactions.storagetemp"/>',  
  	tooltip:"Y",  
  	align:'center'
},
{
  	columnId:"transactionDate",
  	columnName:'<fmt:message key="transactions.receivedpicked"/>', 
  	align:'center',
  	tooltip:"Y",
    sorting:'int'
},
{
  	columnId:"sourceInvGroupName",
  	columnName:'<fmt:message key="transactions.source"/>',
  	tooltip:"Y"
},
{
  	columnId:"destinationInvGroupName",
  	columnName:'<fmt:message key="transactions.transferdestination"/>',
  	tooltip:"Y"
},
{
  	columnId:"dateDelivered",
  	columnName:'<fmt:message key="label.datedelivered"/>',   
  	align:'center',
  	tooltip:"Y",
    sorting:'int'
},
{
  	columnId:"poLine1",
  	columnName:'<fmt:message key="label.haaspoline"/>',
  	tooltip:"Y",
    width:10
},
{
  	columnId:"supplierName",
  	columnName:'<fmt:message key="label.supplier"/>',
  	tooltip:"Y",
    width:10
},
{
  	columnId:"lineItem1",
  	columnName:'<fmt:message key="label.mrline"/>',
  	  	type:'hlink',  
  	    align:'center',
  	  	tooltip:"Y",
  	    onChange:doPrintBol
},
{
  	columnId:"customerName",
  	columnName:'<fmt:message key="label.customer"/>',
  	tooltip:"Y",
    width:10
},
{
  	columnId:"trackingNumber",
  	columnName:'<fmt:message key="label.trackingnumber"/>',   
  	tooltip:"Y"
},
{
  	columnId:"receiptNotes",
  	columnName:'<fmt:message key="transactions.receiptnotes"/>',
  	tooltip:"Y",
  	width:20
},
{
 	columnId:"hiddenDateOfReceipt",
 	sorting:'int'
},
{
  	columnId:"hiddenTransactionDate"
},
{
  	columnId:"batch"
},
{
  	columnId:"hiddenDateDelivered"
},
{
  	columnId:"inventoryGroup"
},
{
  	columnId:"shipmentId"
},
{
  	columnId:"distributorOps"
}
];
</script>
</head>
<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig)">
<tcmis:form action="/transactionsresults.do"
	onsubmit="return submitFrameOnlyOnce();">

	<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
	<c:set var="pickingPermission" value='' />
	<tcmis:facilityPermission indicator="true" userGroupId="Picking">
		<script type="text/javascript">
 <!--
  showUpdateLinks = true;
  <c:set var="pickingPermission" value='Yes'/>
 //-->
 </script>
	</tcmis:facilityPermission>


	<script type="text/javascript">
 <!--
  var showFinancialData = false;
 <tcmis:opsEntityPermission indicator="true" userGroupId="financialData" opsEntityId="${param.opsEntityId}">
 showFinancialData = true;
 </tcmis:opsEntityPermission>
 //-->
 </script>


	<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesAreaBody" style="display: none;"><html:errors />
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
	<div class="backGroundContent"><%--NEW - there is no results table anymore--%>
	<div id="transactionTrackingViewBean"
		style="width: 100%; height: 600px;" style="display: none;"></div>

	<c:set var="transactionsPermission" value="Y" /> <tcmis:permission
		indicator="true" userGroupId="Inventory">
		<c:set var="transactionsPermission" value="Y" />
	</tcmis:permission> <c:if test="${transactionsColl != null}">
		<script type="text/javascript">

<%--NEW - storing the data to be displayed in the grid in a JSON. notice the ID, this will be the id of the cell in the grid.--%>
<%--Right click to show links for receipt labels, print BOL, transactions history.--%>
with(milonic=new menuname("transCompleteMenu")){
	 top="offset=2";
	 style = contextStyle;
	 margin=3;

    aI("text=<fmt:message key="transactions.label.binhistory"/>;url=javascript:showBinHistory();");
    <c:if test="${transactionsPermission == 'Y'}">
    aI("text=<fmt:message key="transactions.label.receiptnotes"/>;url=javascript:getReceiptNotes();");
    </c:if>
    aI("text=<fmt:message key="label.printlabels"/>;url=javascript:doPrintrelabel();");
    aI("text=<fmt:message key="transactions.label.printbol"/>;url=javascript:doPrintBol();");
    aI("text=<fmt:message key="label.deliverylabels"/>;url=javascript:printBoxLabels();");
}

with(milonic=new menuname("transCompleteBatchMenu")){
	 top="offset=2";
	 style = contextStyle;
	 margin=3;

    aI("text=<fmt:message key="transactions.label.binhistory"/>;url=javascript:showBinHistory();");
    <c:if test="${transactionsPermission == 'Y'}">
    aI("text=<fmt:message key="transactions.label.receiptnotes"/>;url=javascript:getReceiptNotes();");
    </c:if>
    aI("text=<fmt:message key="label.printlabels"/>;url=javascript:doPrintrelabel();");
    aI("text=<fmt:message key="transactions.label.printbol"/>;url=javascript:doPrintBol();");
    aI("text=<fmt:message key="label.printpackinglist"/>;url=javascript:printPackingList();");
    aI("text=<fmt:message key="label.deliverylabels"/>;url=javascript:printBoxLabels();");
}

with(milonic=new menuname("transCompleteBatchMenuMiami")){
	 top="offset=2";
	 style = contextStyle;
	 margin=3;

   aI("text=<fmt:message key="transactions.label.binhistory"/>;url=javascript:showBinHistory();");
   <c:if test="${transactionsPermission == 'Y'}">
   aI("text=<fmt:message key="transactions.label.receiptnotes"/>;url=javascript:getReceiptNotes();");
   </c:if>
   aI("text=<fmt:message key="label.printlabels"/>;url=javascript:doPrintrelabel();");
   aI("text=<fmt:message key="transactions.label.printbol"/>;url=javascript:doPrintBol();");
   aI("text=<fmt:message key="label.printpackinglist"/>;url=javascript:printPackingListMiami();");
    aI("text=<fmt:message key="label.deliverylabels"/>;url=javascript:printBoxLabels();");
}

with(milonic=new menuname("transShortMenu")){
	 top="offset=2";
	 style = contextStyle;
	 margin=3;

	 aI("text=<fmt:message key="transactions.label.binhistory"/>;url=javascript:showBinHistory();");
	 <c:if test="${transactionsPermission == 'Y'}">
     aI("text=<fmt:message key="transactions.label.receiptnotes"/>;url=javascript:getReceiptNotes();");
     </c:if>
     aI("text=<fmt:message key="label.printlabels"/>;url=javascript:doPrintrelabel();");
}
drawMenus();
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty transactionsColl}" >

// For options not from database example
//var lotStatus = new Array({text:'Available',value:'Available'},{text:'Client Review',value:'Client Review'},{text:'Cert/Not Pickable',value:'Cert/Not Pickable'});
var lotStatus= new Array(
<c:forEach var="vLotStatus" items="${vvLotStatusBeanCollection}" varStatus="status">
	<c:if test="${status.index > 0}">,</c:if>	
	{text:'${vLotStatus.lotStatus}',value:'${vLotStatus.lotStatus}'}
</c:forEach>
);

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="transaction" items="${transactionsColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<fmt:formatDate var="fmtReceiptDate" value="${transaction.dateOfReceipt}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtTxnDate" value="${transaction.transactionDate}" pattern="${dateTimeFormatPattern}"/>
<fmt:formatDate var="fmtDelivDate" value="${transaction.dateDelivered}" pattern="${dateFormatPattern}"/>
<fmt:formatNumber var="fmtLandedCost" value="${transaction.quantity * transaction.landedCost}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
<fmt:formatNumber var="fmtCost" value="${transaction.quantity * transaction.cost}" pattern="${totalcurrencyformat}"></fmt:formatNumber>

  <c:set var="inventoryGroupPermission" value=""/>
  <tcmis:inventoryGroupPermission indicator="true" userGroupId="Inventory" inventoryGroup="${transaction.inventoryGroup}">
  <c:set var="inventoryGroupPermission" value="Yes"/>
  </tcmis:inventoryGroupPermission>

  <c:set var="dateOfReceiptTime" value="${transaction.dateOfReceipt.time}"/>

  <c:set var="transactionDateTime" value="${transaction.transactionDate.time}"/>
  <c:set var="deliveredDateTime" value="${transaction.dateDelivered.time}"/>
 
    
<%--  <tcmis:jsReplace var="packaging" value="${transaction.packaging}" processMultiLines="true" />

  <tcmis:jsReplace var="itemDesc" value="${transaction.itemDesc}" processMultiLines="true" />
 --%>
  <tcmis:jsReplace var="notes" value="${transaction.notes}" processMultiLines="true" />
  <tcmis:jsReplace var="itemDescription" value="${transaction.itemDescription}" processMultiLines="true" />
  <tcmis:jsReplace var="tmpTrackingNumber" value="${transaction.trackingNumber}" />
  <tcmis:jsReplace var="tmpMfgLot" value="${transaction.mfgLot}" />
  <tcmis:jsReplace var="supplierName" value="${transaction.supplierName}" />
  <tcmis:jsReplace var="customerName" value="${transaction.customerName}" />
        
/*The row ID needs to start with 1 per their design. Use sinlge quotes for column data seperators.*/
{ id:${status.index +1},
 data:['${transactionsPermission}',
 <c:choose>
 <c:when test="${transaction.transactionType  == 'OV'}">
 '<fmt:message key="label.receipt"/>'
 </c:when>
 <c:when test="${transaction.issueId != null && transaction.transactionType == 'RI'}">
 '<fmt:message key="label.issue"/>'
 </c:when>
 <c:when test="${transaction.issueId == null && transaction.transactionType == 'IT'}">
 '<fmt:message key="label.transferreceipt"/>'
 </c:when>
<c:when test="${transaction.issueId != null && transaction.transactionType == 'IT'}">
 '<fmt:message key="label.transfer"/>'
 </c:when>
 <c:when test="${transaction.transactionType  == 'LC'}">
 '<fmt:message key="label.lotchange"/>'
 </c:when>
 <c:when test="${transaction.transactionType  == 'IC'}">
 '<fmt:message key="label.itemconversion"/>'
 </c:when>
 <c:when test="${transaction.transactionType == 'IA'}">
 '<fmt:message key="inventoryAdjustments"/>'
 </c:when>
 <c:when test="${transaction.transactionType == 'WO'}">
 '<fmt:message key="label.writeoff"/>'
 </c:when>
 <c:when test="${transaction.transactionType == 'SF'}">
 '<fmt:message key="label.servicefee"/>'
 </c:when>
 <c:otherwise>
 '${transaction.transactionType}'
 </c:otherwise>
 </c:choose>
 ,
  '${transaction.inventoryGroupName}','${transaction.receiptId}',
  '${transaction.receiverName}','${transaction.lotStatus}','${transaction.picklistId}','${transaction.issueId}',
  '<tcmis:jsReplace value="${transaction.issuerName}" />','${transaction.itemId}','${itemDescription}','',
  '${transaction.quantity}' ,'${transaction.homeCurrencyId}' ,
  <c:choose>
  <c:when test="${transaction.transactionType == 'RI'}">
   ''
  </c:when>
  <c:otherwise>
   '${fmtCost}'
  </c:otherwise>
  </c:choose>
  ,'${fmtLandedCost}','${tmpMfgLot}',
  '${transaction.bin}','${fmtReceiptDate}','<tcmis:jsReplace value="${transaction.deliveryTicket}" />',
  '${transaction.labelStorageTemp}','${fmtTxnDate}','<tcmis:jsReplace value="${transaction.sourceInvGroupName}" />',
  '<tcmis:jsReplace value="${transaction.destinationInvGroupName}" />','${fmtDelivDate}','${transaction.radianPo}-${transaction.poLine}','${supplierName}',
  <c:choose>
  <c:when test="${transaction.prNumber != null && transaction.lineItem != null}">
      '${transaction.prNumber}-${transaction.lineItem}'
  </c:when>
  <c:otherwise>
    ''
  </c:otherwise>
  </c:choose>
  ,'${customerName}','${tmpTrackingNumber}','${notes}','${dateOfReceiptTime}','${transactionDateTime}','${transaction.batch}',
  '${deliveredDateTime}','${transaction.inventoryGroup}','${transaction.shipmentId}','${transaction.distributorOps}']}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
// -->
</script>

		<!-- If the collection is empty say no data found -->
		<c:if test="${empty transactionsColl}">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="tableNoData" id="resultsPageTable">
				<tr>
					<td width="100%"><fmt:message key="main.nodatafound" /></td>
				</tr>
			</table>
		</c:if>
	</c:if> <!-- Hidden element start -->
	<div id="hiddenElements" style="display: none;"><input
		name="totalLines" id="totalLines"
		value="<c:out value="${dataCount}"/>" type="hidden"> <!-- Popup Calendar input options for hcal column Type in the grid-->
	<input type="hidden" name="blockBefore_dateDelivered"
		id="blockBefore_dateDelivered" value="" /> <input type="hidden"
		name="blockAfter_dateDelivered" id="blockAfter_dateDelivered" value="" />
	<input type="hidden" name="blockBeforeExclude_dateDelivered"
		id="blockBeforeExclude_dateDelivered" value="" /> <input
		type="hidden" name="blockAfterExclude_dateDelivered"
		id="blockAfterExclude_dateDelivered" value="" /> <input type="hidden"
		name="inDefinite_dateDelivered" id="inDefinite_dateDelivered" value="" />

	<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
	<%-- Larry Note: currently not used. --%> <input type="hidden"
		name="receiptId" id="receiptId" value="${param.receiptId}" /> <input
		type="hidden" name="mrNumber" id="mrNumber" value="${param.mrNumber}" />
	<input type="hidden" name="trackingNumber" id="trackingNumber"
		value="${param.trackingNumber}" /> <input type="hidden" name="itemId"
		id="itemId" value="${param.itemId}" /> <input type="hidden"
		name="mfgLot" id="mfgLot" value="${param.mfgLot}" /> <input
		type="hidden" name="radianPo" id="radianPo" value="" ${param.radianPo}"/>
	<input type="hidden" name="txnOnDate" id="txnOnDate"
		value="${param.txnOnDate}" /> <input type="hidden" name="daysOld"
		id="daysOld" value="${param.daysOld}" /> <input type="hidden"
		name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}" /> <input
		type="hidden" name="hub" id="hub" value="${param.hub}" /> <input
		type="hidden" name="inventoryGroup" id="inventoryGroup"
		value="${param.inventoryGroup}" /> <input type="hidden"
		name="transType" id="transType" value="${param.transType}" /> <input
		type="hidden" name="sortBy" id="sortBy" value="${param.sortBy}" /> <input
		type="hidden" name="uAction" id="uAction" value="" /> <input
		type="hidden" name="hubName" id="hubName" value="${param.hubName}" />

	<input name="minHeight" id="minHeight" type="hidden" value="100"></div>
	<!-- Hidden elements end --></div>
	<!-- close of backGroundContent --></div>
	<!-- close of interface -->

</tcmis:form>

<FORM method="POST" NAME="generateLabelsForm" ACTION="/tcmIS/hub/picklistreprintresults.do" target="_blank">
    <input name="picklistId" id="picklistId" type="hidden" value="">
    <input name="action" id="action" type="hidden" value="">
</FORM>

</body>
</html:html>