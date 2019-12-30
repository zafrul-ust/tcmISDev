<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon"
	href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp"%> <!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss /> <!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script> <script
	type="text/javascript" src="/js/common/commonutil.js"></script> <%--NEW - grid resize--%>
<script type="text/javascript"
	src="/js/common/grid/resultiframegridresize.js"></script> <!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script> <!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script> <script
	type="text/javascript" src="/js/menu/mmenudom.js"></script> <script
	type="text/javascript" src="/js/menu/mainmenudata.js"></script> <script
	type="text/javascript" src="/js/menu/contextmenu.js"></script> <%@ include
	file="/common/rightclickmenudata.jsp"%> <!-- For Calendar support -->
<%--<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>--%>


<!-- Add any other javascript you need for the page here --> <script
	type="text/javascript" src="/js/distribution/customersearch.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%> <script type="text/javascript"
	src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script> <script
	type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript"
	src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script> <script
	type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript"
	src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script> <script
	type="text/javascript"
	src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script> <script
	type="text/javascript"
	src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script> <%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%> <script
	type="text/javascript"
	src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script> <script
	type="text/javascript"
	src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script> <%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%> <script type="text/javascript"
	src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript"
	src="/js/common/grid/resultiframegridresize.js"></script>

<title><fmt:message key="customersearch.title" /></title>

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
selectedcustomershipto:"<fmt:message key="label.selectedcustomershipto"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
type:"<fmt:message key="label.type"/>"};



var gridConfig = {
	divName:'customerAddressSearchViewBean', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'mygrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:true,			 // this page has rowSpan > 1 or not.
	selectChild: 1,
	submitDefault:false,    // the fields in grid are defaulted to be submitted or not.,
	noSmartRender: false,
	onBeforeSelect:beforeSelectRow,
	onRowSelect:selectRow   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	//onRightClick:null   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	//onBeforeSorting:_onBeforeSorting
};  
var lineMap3 = new Array();

var config = [
{ columnId:"customerId",
  columnName:'<fmt:message key="label.id"/>',
  width:4
},
{ columnId:"customerName",
  columnName:'<fmt:message key="label.customer"/>',
  tooltip:true,
  width:15
},
{ 
  columnId:"customerOrigin",
  columnName:'<fmt:message key="label.customerorigin"/>',
  tooltip:true,
  width:8
},
{ columnId:"abcClassification",
  columnName:'<fmt:message key="label.abcclassification"/>',
  width:8
},
{
   columnId:"billToAddress",
   columnName:'<fmt:message key="label.billto"/>',
   tooltip:true,
   width:15
},
{
    columnId:"locationDesc",
    columnName:'<fmt:message key="label.shiptoname"/>',
    tooltip:true,
    width:15
},
{ columnId:"shipToAddress",
  columnName:'<fmt:message key="label.shipto"/>',
  tooltip:true,
  width:50
},
{ columnId:"notes",
  columnName:'<fmt:message key="label.notes"/>',
  tooltip:true,
  width:10
},
{  columnId:"synonym",
   columnName:'<fmt:message key="label.synonym"/>'
},
{  columnId:"companyId"
},
{  columnId:"creditStatus"
},
{  columnId:"facilityId"
},
{  columnId:"accountSysId"
},
{  columnId:"chargeType"
},
{  columnId:"shipToLocationId"
},
{  columnId:"shipToCompanyId"
},
{  columnId:"paymentTerms"
},
{  columnId:"shelfLifeRequired"
},
{  columnId:"shipComplete"
},
{  columnId:"currencyId"
},
{  columnId:"creditLimit"
},
{  columnId:"overdueLimit"
},
{  columnId:"shipToAddressSearch"
},
{ columnId:"shipToAddressLine1Display"
},
{ columnId:"shipToAddressLine2Display"
},
{ columnId:"shipToAddressLine3Display"
},
{ columnId:"shipToAddressLine4Display"
},
{ columnId:"shipToAddressLine5Display"
},
{ columnId:"billToLocationId"
},
{ columnId:"billToCompanyId"
},
{ columnId:"addressLine1Display"
},
{ columnId:"addressLine2Display"
},
{ columnId:"addressLine3Display"
},
{ columnId:"addressLine4Display"
},
{ columnId:"addressLine5Display"
},
{ columnId:"salesAgentId"
},
{ columnId:"salesAgentName"
},
{ columnId:"inventoryGroupDefault"
},
{ columnId:"inventoryGroupName"
},
{ columnId:"fieldSalesRepId"
},
{ columnId:"fieldSalesRepName"
},
{ columnId:"overdueLimitBasis"
},
{ columnId:"fixedCurrency"
},
{ columnId:"priceGroupId"
},
{ columnId:"chargeFreight"
},
{ columnId:"opsCompanyId"
},
{ columnId:"opsEntityId"
},
{ columnId:"opsEntityName"
},
{ columnId:"availableCredit"
},
{ columnId:"defaultCustomerContactId"
},
{
  columnId:"defaultCustomerContactName"
},
{ columnId:"defaultCustomerContactPhone"
},
{ columnId:"defaultCustomerContactFax"
},
{ columnId:"defaultCustomerContactEmail"
},
{columnId:"locationShortName"
},
{columnId:"defaultCurrencyId"
}
];

// -->
</script>
</head>
<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/customersearchresults.do"
	onsubmit="return submitFrameOnlyOnce();">

	<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
	<%--
<c:set var="pickingPermission" value=''/>
<tcmis:facilityPermission indicator="true" userGroupId="Picking" >
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
  <c:set var="pickingPermission" value='Yes'/>
 //-->
 </script>
</tcmis:facilityPermission>
--%>

	<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesAreaBody" style="display: none;"><html:errors />
	</div>

	<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

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
	<div id="customerAddressSearchViewBean"
		style="width: 100%; %; height: 400px;" style="display: none;"></div>

	<c:if test="${CustomerAddressSearchViewCollection != null}">
		<script type="text/javascript">
<!--

<%--NEW - storing the data to be displayed in the grid in a JSON. notice the ID, this will be the id of the cell in the grid.--%>
<%--TODO - Right click to show links for receipt labels, print BOL, transactions history.--%>
<c:set var="dataCount" value='${0}'/>
<c:set var="preCustomerId" value=''/>

<bean:size id="listSize" name="CustomerAddressSearchViewCollection"/>
<c:if test="${!empty CustomerAddressSearchViewCollection}" >

var lineMap = new Array();
var lineMap3 = new Array();
//  map will change per 'PO'

<c:set var="idcount" value="0"/>
<c:set var="preCustomerId" value=''/>
<c:forEach var="customer" items="${CustomerAddressSearchViewCollection}" varStatus="status">
	<c:choose>
		<c:when test="${customer.customerId != preCustomerId }">
			lineMap[${status.index}] = 1;
			<c:set var="preCustomerId" value='${customer.customerId}'/>
			<c:set var="idcount" value="${idcount + 1}"/>
			<c:set var="parent" value="${status.index}"/>
		</c:when>
		<c:otherwise>
			lineMap[${parent}]++;
		</c:otherwise>
	</c:choose>
	lineMap3[${status.index}] = ${idcount % 2};
</c:forEach>

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="customerBean" items="${CustomerAddressSearchViewCollection}" varStatus="status">

  <tcmis:jsReplace var="customerName" value='${customerBean.customerName}' processMultiLines="false" />
  <tcmis:jsReplace var="billToAddress" value='${customerBean.billToAddress}' processMultiLines="true" />
  <tcmis:jsReplace var="locationDesc" value='${customerBean.locationDesc}' processMultiLines="true"/>
  <tcmis:jsReplace var="shipToAddressSearch" value='${customerBean.shipToAddressSearch}' processMultiLines="true"/>
  <tcmis:jsReplace var="shipToAddressLine1" value='${customerBean.shipToAddressLine1Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="shipToAddressLine2" value='${customerBean.shipToAddressLine2Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="shipToAddressLine3" value='${customerBean.shipToAddressLine3Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="shipToAddressLine4" value='${customerBean.shipToAddressLine4Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="shipToAddressLine5" value='${customerBean.shipToAddressLine5Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="addressLine1Display" value='${customerBean.addressLine1Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="addressLine2Display" value='${customerBean.addressLine2Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="addressLine3Display" value='${customerBean.addressLine3Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="addressLine4Display" value='${customerBean.addressLine4Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="addressLine5Display" value='${customerBean.addressLine5Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="notes" value='${customerBean.notes}' processMultiLines="true"/>

  <tcmis:jsReplace var="locationShortName" value='${customerBean.locationShortName}' processMultiLines="false"/>
  <tcmis:jsReplace var="opsEntityName" value='${customerBean.opsEntityName}' processMultiLines="false"/>
  <tcmis:jsReplace var="salesAgentName" value='${customerBean.salesAgentName}' processMultiLines="false"/>
  <tcmis:jsReplace var="fieldSalesRepName" value='${customerBean.fieldSalesRepName}' processMultiLines="false"/>

  <c:set var="customerCurrencyId" value='${customerBean.currencyId}'/>
  <c:set var="defOpsEntityId" value='${customerBean.opsEntityId}'/>
  <c:set var="defOpsCompanyId" value='${customerBean.opsCompanyId}'/>
  <c:set var="defaultCurrencyId" value='${customerBean.defaultCurrencyId}'/>
  <c:set var="inventoryGroupDefault" value='${customerBean.inventoryGroupDefault}'/>

  <tcmis:opsEntityPermission indicator="false" opsEntityId="${customerBean.opsEntityId}"  userGroupId="GenerateOrders">
   <c:set var="customerCurrencyId" value=''/>
   <c:set var="defOpsEntityId" value=''/>
   <c:set var="defOpsCompanyId" value=''/>
   <c:set var="defaultCurrencyId" value=''/>
   <c:set var="inventoryGroupDefault" value=''/>
  </tcmis:opsEntityPermission>

{ id:${status.index +1},
 <c:if test="${fn:toUpperCase( customerBean.creditStatus) eq 'STOP'}">'class':"grid_black",</c:if>
 data:[
  '${customerBean.customerId}','${customerName}',
  '${customerBean.customerOrigin}',
  '${customerBean.abcClassification}','${billToAddress}','${locationDesc}',
  '${shipToAddressLine1} ${shipToAddressLine2} ${shipToAddressLine3} ${shipToAddressLine4} ${shipToAddressLine5}',
  '${notes}',
  '${customerBean.legacyCustomerId}',
  '${customerBean.companyId}',
  '${customerBean.creditStatus}','${customerBean.facilityId}','${customerBean.accountSysId}',
  '${customerBean.chargeType}','${customerBean.shipToLocationId}','${customerBean.shipToCompanyId}','${customerBean.paymentTerms}',
  '${customerBean.shelfLifeRequired}','${customerBean.shipComplete}','${customerCurrencyId}',
  '${customerBean.creditLimit}','${customerBean.overdueLimit}','${shipToAddressSearch}','${shipToAddressLine1}',
  '${shipToAddressLine2}','${shipToAddressLine3}','${shipToAddressLine4}','${shipToAddressLine5}',
  '${customerBean.billToLocationId}','${customerBean.billToCompanyId}',
  '${addressLine1Display}','${addressLine2Display}','${addressLine3Display}','${addressLine4Display}','${addressLine5Display}',
  '${customerBean.salesAgentId}','${salesAgentName}','${inventoryGroupDefault}',
  '<tcmis:jsReplace value="${customerBean.inventoryGroupName}" />','${customerBean.fieldSalesRepId}','${fieldSalesRepName}',
  '${customerBean.overdueLimitBasis}','${customerBean.fixedCurrency}','${customerBean.priceGroupId}','${customerBean.chargeFreight}',
  '${defOpsCompanyId}','${defOpsEntityId}','${opsEntityName}','${customerBean.availableCredit}',
  '${customerBean.defaultCustomerContactId}',
  '<tcmis:jsReplace value="${customerBean.defaultCustomerContactName}"  /> ',
  '${customerBean.defaultCustomerContactPhone}',
  '${customerBean.defaultCustomerContactFax}',
  '<tcmis:jsReplace value="${customerBean.defaultCustomerContactEmail}" /> ',
  '${locationShortName}','${defaultCurrencyId}']}
<c:if test="${status.index+1 < listSize}">,</c:if>  
<c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
// -->
</script>

		<!-- If the collection is empty say no data found -->
		<c:if test="${empty CustomerAddressSearchViewCollection}">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="tableNoData" id="resultsPageTable">
				<tr>
					<td width="100%"><fmt:message key="main.nodatafound" /></td>
				</tr>
			</table>
		</c:if>
	</c:if> <!-- Hidden element start -->
	<div id="hiddenElements" style="display: none;"><input
		name="totalLines" id="totalLines" value="${dataCount}" type="hidden">

	<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
	<%-- Larry Note: currently not used. --%> <input type="hidden"
		name="selectedCustomerId" id="selectedCustomerId"
		value="${param.selectedCustomerId}" /> <input type="hidden"
		name="currentCustomerId" id="currentCustomerId"
		value="${param.currentCustomerId}" /> <input type="hidden"
		name="customerId" id="customerId" value="${param.currentCustomerId}" />
	<input type="hidden" name="customerName" id="customerName"
		value="${param.customerName}" /> <input type="hidden"
		name="billToAddress" id="billToAddress" value="${param.billToAddress}" />
	<input type="hidden" name="shipToAddress" id="shipToAddress"
		value="${param.shipToAddress}" /> <input type="hidden" name="popup"
		id="popup" value="${param.popup}" /> <input type="hidden"
		name="personnelId" id="personnelId" value="${personnelId}" /> <input
		type="hidden" name="lastName" id="lastName" value="${lastName}" /> <input
		type="hidden" name="firstName" id="firstName" value="${firstName}" />

	<input name="minHeight" id="minHeight" type="hidden" value="100"></div>
	<!-- Hidden elements end --></div>
	<!-- close of backGroundContent --></div>
	<!-- close of interface -->

</tcmis:form>
</body>
</html:html>