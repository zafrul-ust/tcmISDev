<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>
<%@ include file="/common/locale.jsp" %>


<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<%--<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>--%>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>

<!-- Add any other javascript you need for the page here -->
<%--<script type="text/javascript" src="/js/common/standardGridmain.js"></script>--%>
<script type="text/javascript" src="/js/distribution/scratchpad.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>

<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>

<%--Custom sorting.This custom sorting function implements case insensitive sorting.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script src="/js/jquery/jquery-1.6.4.js"></script>


<title>
${salesQuoteViewCollection.orderType}&nbsp;${salesQuoteViewCollection.prNumber}
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={
alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",  
norowselected:"<fmt:message key="error.norowselected"/>",
norowwasselected:"<fmt:message key="label.norowwasselected"/>",
wanttocontinue:"<fmt:message key="label.wanttocontinue"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
scratchpadlimitexceeded:"<fmt:message key="label.scratchpadlimitexceeded"/>",
linesnotonqualityhold:"<fmt:message key="label.linesnotonqualityhold"/>",
customer:"<fmt:message key="label.customer"/>",
contact:"<fmt:message key="label.contact"/>", 
quotedate:"<fmt:message key="label.quotedate"/>",
inventorygroup:"<fmt:message key="label.inventorygroup"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
csr:"<fmt:message key="label.csr"/>", 
quoteexpires:"<fmt:message key="label.quoteexpires"/>",
orderqty:"<fmt:message key="label.orderqty"/>",
requiredshelflife:"<fmt:message key="label.requiredshelflife"/>",
orderqty:"<fmt:message key="label.orderqty"/>",
orderdate:"<fmt:message key="label.orderdate"/>",  
shippingmethod:"<fmt:message key="label.shippingmethod"/>",
needdate:"<fmt:message key="label.needdate"/>",
promiseddate:"<fmt:message key="label.promiseddate"/>",
externalnotes:"<fmt:message key="label.externalnote"/>",
internalnotes:"<fmt:message key="label.internalnote"/>",
purchasingnotes:"<fmt:message key="label.purchasingnote"/>",
forceholdcomment:"<fmt:message key="label.forceholdcomment"/>",
pricelist:"<fmt:message key="label.pricelist"/>",
currency:"<fmt:message key="label.currency"/>",
revenueentity:"<fmt:message key="label.revenueentity"/>",
scratchpad:"<fmt:message key="label.scratchPad"/>",
quote:"<fmt:message key="label.quote"/>",
selectNumber:"<fmt:message key="msg.selectNumber"/>",
creditreview:"<fmt:message key="creditreview.title"/>",
errors:"<fmt:message key="label.errors"/>", 
pickcustomerid:"<fmt:message key="label.pickcustomerid"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",  
unitprice:"<fmt:message key="label.unitprice"/>",
customerline:"<fmt:message key="label.customerline"/>",
supply:"<fmt:message key="label.supply"/>",
receipt:"<fmt:message key="label.receipt"/>",
beyonagreedtradingterms:"<fmt:message key="label.beyonagreedtradingterms"/>",
lineitemshippednodelete:"<fmt:message key="label.lineitemshippednodelete"/>", 
openorders:"<fmt:message key="label.openorders"/>",
creditlimit:"<fmt:message key="label.creditlimit"/>",
amountover:"<fmt:message key="label.amountover"/>",
creditlimitdays:"<fmt:message key="label.creditlimitdays"/>",
oldestinvoice:"<fmt:message key="label.oldestinvoice"/>",
overduedays:"<fmt:message key="label.overduedays"/>",
review:"<fmt:message key="label.review"/>",  
clear:"<fmt:message key="label.clear"/>",
additionalcharges:"<fmt:message key="label.additional.charges"/>",
customerreturnrequest:"<fmt:message key="label.customerreturnrequest"/>",
autoallocateline:"<fmt:message key="label.autoallocateline"/>",
showallocatableig:"<fmt:message key="label.showallocatableig"/>",
showallocatableregion:"<fmt:message key="label.showallocatableregion"/>",
showallocatableglobal:"<fmt:message key="label.showallocatableglobal"/>",
showpreviousordersforcustomer:"<fmt:message key="label.showpreviousordersforcustomer"/>",
showallpreviousorders:"<fmt:message key="label.showallpreviousorders"/>",
showpohistory:"<fmt:message key="showpohistory.title"/>",
showquotationallhistoryforcustomer:"<fmt:message key="showquotationallhistoryforcustomer.title"/>",
showquotationallhistory:"<fmt:message key="showquotationallhistory.title"/>",
showpricelistinfo:"<fmt:message key="label.showpricelistinfo"/>",
showcurrentsourcinginfo:"<fmt:message key="label.editsourcinginfo"/>",
cancelmrliine:"<fmt:message key="label.cancelmrliine"/>",
releasemrline:"<fmt:message key="label.releasemrline"/>",
marginoutsidelimits:"<fmt:message key="label.marginoutsidelimits"/>",
line:"<fmt:message key="label.line"/>",
pleasewait:"<fmt:message key="label.pleasewait"/>",
pleasewaitmenu:"<fmt:message key="label.pleasewaitmenu"/>",	 
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
customerlookup:"<fmt:message key="label.customerlookup"/>", 
addline:"<fmt:message key="label.addline"/>",
allocation:"<fmt:message key="label.allocation"/>", 
addlheaderchargespr:"<fmt:message key="label.addlheaderchargespr"/>", 
additionalcharges:"<fmt:message key="label.additional.charges"/>", 
specificationlookup:"<fmt:message key="label.specificationlookup"/>", 
shiptoaddress:"<fmt:message key="label.shiptoaddress"/>", 
onetimeaddress:"<fmt:message key="label.onetimeaddress"/>", 
contactsearch:"<fmt:message key="contactsearch.title"/>",
customercarrier:"<fmt:message key="customercarrier.title"/>",
customerref:"<fmt:message key="label.customerref"/>",
customerpo:"<fmt:message key="label.customerpo"/>",
specialinstructions:"<fmt:message key="label.specialinstructionswithnobreak"/>",
leadtimeplotpart1:"<fmt:message key="label.leadtimeplotpart1"/>",
leadtimeplotpart2:"<fmt:message key="label.leadtimeplotpart2"/>",
itemnotes:"<fmt:message key="itemnotes.title"/>",
quickview:"<fmt:message key="quickQuote"/>",
catalogitemnotes:"<fmt:message key="label.catalogitemnotes"/>",
supplieritemnotes:"<fmt:message key="supplieritemnotes.title"/>",	
confirmduplicatecustomerpo:"<fmt:message key="label.confirmduplicatecustomerpo"/>",
duplicatecustomerpo:"<fmt:message key="label.duplicatecustomerpo"/>",
maxlength:"<fmt:message key="errors.maxlength"/>",
totalexceedavailable:"<fmt:message key="label.totalexceedavailable"/>",
orderinternal:"<fmt:message key="label.orderinternal"/>",
orderexternal:"<fmt:message key="label.orderexternal"/>",
cancelmrlinereason:"<fmt:message key="label.cancelmrlinereason"/>",
mrlineallocation:"<fmt:message key="label.mrlineallocation"/>",
billto:"<fmt:message key="label.billto"/>",
shipto:"<fmt:message key="label.shipto"/>",	
holdcomments:"<fmt:message key="label.holdcomments"/>",	
varies:"<fmt:message key="label.varies"/>",	
uosp:"<fmt:message key="label.uosp"/>",
expertreviewmsg1:"<fmt:message key="label.expertreviewmsg1"/>",
expertreviewmsg2:"<fmt:message key="label.expertreviewmsg2"/>",
marginlessthan100:"<fmt:message key="label.marginlessthan100"/>",
orderqtynotlessthanallocatedqty:"<fmt:message key="label.orderqtynotlessthanallocatedqty"/>",
orderqtynotmorethanblanketorderremainingqty:"<fmt:message key="label.orderqtynotmorethanblanketorderremainingqty"/>",
unitpricetolerance:"<fmt:message key="label.unitpricetolerance"/>",
showlegend:"<fmt:message key="label.showlegend"/>",    				
ok:"<fmt:message key="label.ok"/>",
confirEmail:"<fmt:message key="label.confirEmail"/>",
materialrequestsorigin:"<fmt:message key="label.materialrequestorigin"/>"
};


function addLinefromQuickPage() {
	// Now we just use this function as a flag.
	// Add line using ajax here if needed.

}

var preinv = '${param.inventoryGroup}';
<c:set var="igCount" value="0"/>

var inventoryGroupTemp = 
new Array(
<c:forEach var="nouse0" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status">
  <c:if  test="${ status.current.opsEntityId == salesQuoteViewCollection.opsEntityId }"> 
	 <c:forEach var="nouse1" items="${status.current.hubIgCollection}" varStatus="status1">
	  <c:forEach var="nouse2" items="${status1.current.inventoryGroupCollection}" varStatus="status2">
		<tcmis:inventoryGroupPermission indicator="true" userGroupId="GenerateOrders" inventoryGroup="${status2.current.inventoryGroup}">
	 	 <c:if test="${ igCount !=0 }">,</c:if>
		 {
			value:'${status2.current.inventoryGroup}',
			text:'<tcmis:jsReplace value="${status2.current.inventoryGroupName}"/>'
		 }
		 <c:set var="igCount" value="${igCount+1}"/>
		</tcmis:inventoryGroupPermission>
	   </c:forEach>
	 </c:forEach>
  </c:if> 
</c:forEach>
);

var inventoryGroup = new Array();

<c:set var="extprice"><fmt:message key="label.extprice"/></c:set>
<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
var config = [
  {
  	columnId:"permission"
  },
  {
  	columnId:"inventoryGroupPermission"
  },
  {
  	columnId:"okDoUpdatePermission"
  }, 
  {
  	columnId:"quantityPermission"
  },
  {
  	columnId:"customerPartNoPermission"
  },
  {
  	columnId:"catalogPricePermission"
  },
  {
  	columnId:"taxExemptPermission"
  },
  {
  	columnId:"shipCompletePermission"
  },
  {
  	columnId:"consolidateShipmentPermission"
  },
  {
  	columnId:"dropShipOverridePermission"
  },
  {
  	columnId:"customerPoLinePermission"
  },
  {
  	columnId:"requiredShelfLifePermission"
  },
  {
  	columnId:"deliveryTypePermission"
  },
  {
  	columnId:"requiredDatetimePermission"
  },
  {
  	columnId:"promisedDatePermission"
  },
  {
  	columnId:"forceHoldPermission"
  },
  {
  	columnId:"criticalPermission"
  },
  {
  	columnId:"externalNotePermission"
  },
  {
  	columnId:"internalNotePermission"
  },
  {
  	columnId:"purchasingNotePermission"
  },
  {
  	columnId:"okDoUpdate",
  	columnName:'<fmt:message key="label.grab"/><br><input type="checkbox" value="" onClick="return checkAll(\'okDoUpdate\');" name="chkAllOkDoUpdate" id="chkAllOkDoUpdate">',
  	type:'hchstatus',
//  	sorting:"haasHch",
    align:'center',
    width:3
  },
  {
  	columnId:"lineItem",
  	columnName:'<fmt:message key="mrallocationreport.label.mrline"/>',
    align:'right',
    sorting:'int',
    width:3
  },
  {
  	columnId:"customerPoLine",
  	type:'hed',
  	columnName:'<fmt:message key="label.customerline"/>',
  	align:'center',
  	maxlength:30,
  	width:6
  },
  {
  	columnId:"labelSpec",
  	columnName:'<fmt:message key="label.catalogitem"/>',
  	tooltip:"Y",
  	width:7
  },
  {
  	columnId:"partDescription",
  	columnName:'<fmt:message key="label.description"/>',
  	tooltip:"Y",
  	width:15
  },
  {
  	columnId:"specList",
  	columnName:'<fmt:message key="label.certification"/>',
  	attachHeader:'<fmt:message key="label.value"/>',
  	tooltip:"Y",
  	width:8
  }
  ,
  {
  	columnId:"specification",
	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.lookup"/>',
  	width:6
  },
  {
  	columnId:"hazardous",
  	columnName:'<fmt:message key="label.haz"/>',
    align:'center',
    width:3
  },
  {
  	columnId:"quantity",
  	columnName:'<fmt:message key="label.orderqty"/>',
  	type:'hed',
  	sorting:'int',
  	onChange:'function',
  	align:'right',
  	width:5,
  	size:3,
    maxlength:7
  },
  {
  	columnId:"currencyId",
  	columnName:' ',
  	align:'center',
  	width:3
  },
  {
  	columnId:"catalogPriceDisplayXX",
  	columnName:'<fmt:message key="label.unitprice"/>',
  	align:'right',
  	width:6
  },
  {
  	columnId:"priceBreakAvailable",
  	columnName:'<fmt:message key="label.pricebreak"/>',
  	width:3
  },
<c:choose>
  <c:when test="${salesQuoteViewCollection.mvItem == 'Y'}">
  {
  	columnId:"totalUnitOfSaleQuantity",
  	columnName:'<fmt:message key="label.variablesize"/>',
  	attachHeader:'<fmt:message key="label.nominalqty"/>',
  	align:'right',
  	width:5
  },
  {
  	columnId:"unitOfSale",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.uos"/>',
  	width:5
  },
  {
  	columnId:"unitOfSalePriceDisplayXX",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.uosp"/>',
  	width:6
  },
  </c:when>
  <c:otherwise>
  {
  	columnId:"totalUnitOfSaleQuantity"
  },
  { 
  	columnId:"unitOfSale"
  },
  {
  	columnId:"unitOfSalePriceDisplayXX"
  },
  </c:otherwise>
</c:choose>
  {
  	columnId:"extPrice",
  	columnName:'<tcmis:jsReplace value="${extprice}"/>',
  	width:6,
  	sorting:'int'
  },
  {
  	columnId:"margin",
  	columnName:'<fmt:message key="label.margin"/>',
  	align:'right',
  	width:5
  },
  {
  	columnId:"extraCharges",
  	columnName:'<fmt:message key="label.addlcharges"/>',
  	width:5,
  	align:'right',
  	sorting:'int'
  },
  {
  	columnId:"requiredDatetime",
  	columnName:'<fmt:message key="label.needdate"/> <br> <input readonly class="inputBox" name="needdateAll" id="needdateAll" type="text" value="" size=9 onclick="return getDate(document.genericForm.needdateAll);" onChange="setCalendarValue(\'requiredDatetime\');"/>',
  	type:'hcal',
  	align:'center',
    width:7
  },
  {
  	columnId:"promisedDate",
  	columnName:'<fmt:message key="label.promised.ship.date"/> <br> <input readonly class="inputBox" name="promisedDateAll" id="promisedDateAll" type="text" value="" size=9 onclick="return getDate(document.genericForm.promisedDateAll);" onChange="setCalendarValue(\'promisedDate\');"/>',
  	type:'hcal',
  	align:'center',
    width:8
  },
  {
  	columnId:"shipped",
  	columnName:'<fmt:message key="label.allocatedqty"/>',
  	attachHeader:'<fmt:message key="label.shipped"/>',
  	align:'right',
  	width:5
  },
  {
  	columnId:"pickList",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.picklist"/>',
  	align:'right',
  	width:5
  },
  {
  	columnId:"receipt",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.receipt"/>',
  	align:'right',
  	width:4
  },
  {
  	columnId:"supply",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.supply"/>',
  	align:'right',
  	width:4
  },   
  {
  	columnId:"requiredShelfLife",
  	columnName:'<fmt:message key="label.requiredshelflife"/>',
  	type:'hed',
  	align:'right',
  	onChange:'function',
  	width:6
  },
  {
  	columnId:"taxExempt",
  	columnName:'<fmt:message key="label.taxexempt"/><input type="checkbox" value="" onClick="return checkAll(\'taxExempt\');" name="chkAllTaxExempt" id="chkAllTaxExempt">',
  	type:'hchstatus',
    align:'center',
    width:4
  },
  {
  	columnId:"shipComplete",
  	columnName:'<fmt:message key="label.linecomplete"/><input type="checkbox" value="" onClick="return checkAll(\'shipComplete\');" name="chkAllShipComplete" id="chkAllShipComplete">',
  	type:'hchstatus',
  	onChange:uncheckLinesGroup,
    align:'center',
    width:5
  },
  {
  	columnId:"consolidateShipment",
  	columnName:'<fmt:message key="label.linesgroup"/> <br> <input type="checkbox" value="" onClick="return checkAll(\'consolidateShipment\');" name="chkAllLinesGroup" id="chkAllLinesGroup">',
  	type:'hchstatus',
  	onChange:checkLineComplete,
    align:'center',
    width:4
  },
  {
  	columnId:"deliveryType",
  	columnName:'<fmt:message key="label.deliverytype"/>',
  	type:'hcoro',
  	align:'center',
  	width:5
  },
  {
  	columnId:"critical",
  	columnName:'<fmt:message key="label.priority"/>',
  	type:'hcoro',
  	align:'center',
  	width:6
  },
  {
	  	columnId:"inventoryGroup",
	  	columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
	  	type:'hcoro',
	  	align:'center',
	  	width:11
  },
<c:choose>
  <c:when test="${salesQuoteViewCollection.orderType == 'MR'}">
  {
  	columnId:"dropShipOverride",
  	columnName:'<fmt:message key="label.dropship"/> <br> <input type="checkbox" value="" onClick="return checkAll(\'dropShipOverride\');" name="chkAllDropship" id="chkAllDropship">',
  	type:'hchstatus',
    align:'center',
    width:4
  },
  </c:when>
  <c:otherwise>
  {
  	columnId:"dropShipOverride",
  	columnName:''
  },
  </c:otherwise>
</c:choose>
<c:choose>
  <c:when test="${salesQuoteViewCollection.orderType == 'Scratch Pad'}">
  {
  	columnId:"externalNote"
  },
  {
  	columnId:"internalNote"
  },
  {
  	columnId:"purchasingNote"
  },
  </c:when>
  <c:otherwise>
  {
  	columnId:"externalNote",
  	columnName:'<fmt:message key="label.externalnote"/>',
  	type:"txt",
    tooltip:"Y",
    width:10
  },
  {
  	columnId:"internalNote",
  	columnName:'<fmt:message key="label.internalnote"/>',
  	type:"txt",
    tooltip:"Y",
    width:10
  },
  {
  	columnId:"purchasingNote",
  	columnName:'<fmt:message key="label.purchasingnote"/>',
  	type:"txt",
    tooltip:"Y",
    width:10
  },
  </c:otherwise>
</c:choose>
<c:choose>
  <c:when test="${salesQuoteViewCollection.orderType == 'MR'}">
  {
  	columnId:"rcptQualityHoldSpec",
  	columnName:'<fmt:message key="label.qualityhold"/>',
  	attachHeader:'<fmt:message key="label.spec"/>',
  	align:'center',
  	width:4
  },
  {
  	columnId:"rcptQualityHoldShelfLife",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.sl"/>',
  	align:'center',
  	width:3
  },
  </c:when>
  <c:otherwise>
  {
  	columnId:"rcptQualityHoldSpec"
  },
  {
  	columnId:"rcptQualityHoldShelfLife"
  },
  </c:otherwise>
</c:choose>
  {
  	columnId:"customerPartNo",
  	type:'hed',
  	columnName:'<fmt:message key="label.customerpart"/>',
  	align:'center',
  	maxlength:30,
  	width:6
  },
  {
  	columnId:"companyId"
  },
  {
  	columnId:"prNumber"
  },
  {
  	columnId:"catalogCompanyId"
  },
  {
  	columnId:"catalogId"
  },
  {
  	columnId:"partGroupNo"
  },
  {
  	columnId:"unitOfSaleNotUsed"
  },
  {
  	columnId:"unitOfSaleQuantityPerEach"
  },
  {
  	columnId:"catPartNo"
  },
  {
  	columnId:"application"
  },
  {
  	columnId:"qtyInvoiced"
  },
  {
  	columnId:"requestLineStatus"
  },
  {
  	columnId:"itemId"
  },
  {
  	columnId:"expectedUnitCost"
  },
  {
  	columnId:"specListConcat"
  },
  {
  	columnId:"specDetailConcat"
  },
  {
  	columnId:"specLibraryConcat"
  },
  {
  	columnId:"specCocConcat"
  },
  {
  	columnId:"specCoaConcat"
  },
  {
  	columnId:"minimumGrossMargin"
  },
  {
  	columnId:"maximumGrossMargin"
  },
  {
  	columnId:"medianSupplyLeadTime"
  },
  {
  	columnId:"inventoryGroupName"
  },
  {
  	columnId:"originalOrderQuantity"
  },
  {
  	columnId:"quantityReturnAuthorized"
  },
  {
  	columnId:"catalogPriceAvailable"
  },
  {
  	columnId:"mvItem"
  },
  {
  	columnId:"catalogPrice"
  },
  { 
  	columnId:"unitOfSalePrice"
  },
  { 
  	columnId:"originalSalesQuoteType"
  },
  { 
    columnName:'<fmt:message key="label.remainingblanketqty"/>',
  	columnId:"blanketOrderRemainingQty",
  	align:'right'
  },
<c:choose>
  <c:when test="${salesQuoteViewCollection.orderType == 'MR'}">
  {
  	columnId:"shippingReference",
  	type:'hed',
  	columnName:'<fmt:message key="label.shippingreferenece"/>',
  	align:'center',
  	maxlength:100,
  	width:6
  },
  {
  	columnId:"forceHold",
  	columnName:'<fmt:message key="label.forcehold"/> <br> <input type="checkbox" value="" onClick="return checkAll(\'forceHold\');" name="chkAllforceHold" id="chkAllforceHold">',
  	type:'hchstatus',
    align:'center',
    width:4
  },
  {
  	columnId:"forceHoldComment",
  	columnName:'<fmt:message key="label.forceholdcomment"/>',
  	type:"txt",
    tooltip:"Y",
    width:10
  },
  {
  	columnId:"scrap",
  	columnName:'<fmt:message key="label.scrap"/> <br> <input type="checkbox" value="" onClick="return checkAll(\'scrap\');" name="chkAllscrap" id="chkAllscrap">',
  	type:'hchstatus',
    align:'center',
    width:4
  },
  </c:when>
  <c:otherwise>
  {
  	columnId:"shippingReference"
  },
    {
  	columnId:"forceHold"
  },
  {
  	columnId:"forceHoldComment"
  },
  {
  	columnId:"scrap"
  },
  </c:otherwise>
</c:choose>
  { 
  	columnId:"radianPo"
  },
  { 
  	columnId:"poLine"
  },
  {
  	columnId:"salesOrder"
  },
  {
	columnId:"interCoUnitPricePctTol"
  },
  {
	columnId:"interCoExtPriceAmtTol"
  }
];

function getDate(dateColumn) {
	return getCalendar(dateColumn,document.genericForm.blockBeforeExcludeDate);
}

var critical = new  Array({text:'<fmt:message key="label.none"/>',value:''},
  							{text:'<fmt:message key="label.aog"/>',value:'S'},
  							{text:'<fmt:message key="label.critical"/>',value:'Y'},
  							{text:'<fmt:message key="label.ld"/>',value:'L'});
  							
var deliveryType = new  Array({text:'<fmt:message key="label.by"/>',value:'Deliver by'},
        {text:'<fmt:message key="label.on"/>',value:'Schedule'});
  							

var done = true;
var currentWindow = null;
var windowRoot = null;

function showWin(name,objName) {
	var overCreditLimitMessagesArea = document.getElementById("overCreditLimitArea");
	    var customerId = document.getElementById("customerId").value;
	    var customerName = document.getElementById("customerName").value;
	    if($v("openArAmount").length != 0)
	    	var arOutstanding = $v("creditReviewCurrencyId")+" "+$v("openArAmount").replace(".0000","");
	    else
	    	var arOutstanding = $v("openArAmount");
	    
	    if($v("openOrderAmount").length != 0)
	    	var openOrders = $v("creditReviewCurrencyId")+" "+$v("openOrderAmount").replace(".0000","");
	    else
	    	var openOrders = $v("openOrderAmount");
	    	
	    if($v("creditLimitPopup").length != 0)
	    	var creditLimit = $v("creditReviewCurrencyId")+" "+$v("creditLimitPopup").replace(".0000","");
	    else
	    	var creditLimit = $v("creditLimitPopup");
	    	
	    if($v("availableCreditPopup").length != 0)
	    	var availableCredit = $v("creditReviewCurrencyId")+" "+$v("availableCreditPopup").replace(".0000","");
	    else
	    	var availableCredit = $v("availableCreditPopup");
	   
	    var gracePeriod = $v("gracePeriodDays");
	    var greatestOverdueDays = $v("greatestOverdueDays");
	    if($v("pastDueAmount030Days").length != 0)
	    	var pastDue = $v("creditReviewCurrencyId")+" "+$v("pastDueAmount030Days").replace(".0000","");
	    else
	    	var pastDue = $v("pastDueAmount030Days");
	    
	    if($v("pastDueAmount3160Days").length != 0)
	    	var pastDue30 = $v("creditReviewCurrencyId")+" "+$v("pastDueAmount3160Days").replace(".0000","");
	    else
	    	var pastDue30 = $v("pastDueAmount3160Days");
	    	
	    if($v("pastDueAmount6190Days").length != 0)
	    	var pastDue60 = $v("creditReviewCurrencyId")+" "+$v("pastDueAmount6190Days").replace(".0000","");
	    else
	    	var pastDue60 = $v("pastDueAmount6190Days");
	    	
	    if($v("pastDueAmount90Days").length != 0)
	    	var pastDue90 = $v("creditReviewCurrencyId")+" "+$v("pastDueAmount90Days").replace(".0000","");
	    else
	    	var pastDue90 = $v("pastDueAmount90Days");
	    	
	    var inner = document.getElementById("hiddenOverCreditLimitDiv").innerHTML;
	        inner = inner.replace(/%arOutstanding%/,arOutstanding);
	        inner = inner.replace(/%openOrders%/,openOrders);
	        inner = inner.replace(/%creditLimit%/,creditLimit);
	        inner = inner.replace(/%availableCredit%/,availableCredit);
	        inner = inner.replace(/%gracePeriod%/,gracePeriod);
	        inner = inner.replace(/%greatestOverdueDays%/,greatestOverdueDays);
	        inner = inner.replace(/%pastDue%/,pastDue);
	        inner = inner.replace(/%pastDue30%/,pastDue30);
	        inner = inner.replace(/%pastDue60%/,pastDue60);
	        inner = inner.replace(/%pastDue90%/,pastDue90);
        overCreditLimitMessagesArea.innerHTML = inner;
        overCreditLimitMessagesArea.style.display="";
        
	   var dhxWins = null;//alert("windowRoot:"+windowRoot +"    currentWindow"+currentWindow );
       if( windowRoot == null || currentWindow == null) {
	       	dhxWins = new dhtmlXWindows();
	       	windowRoot = dhxWins;
       }
       
// alert("dhxWins"+dhxWins);
       if (dhxWins == null || !dhxWins.window(name)) {
       // create window first time
       var popupWin = dhxWins.createWindow(name,0, 0, 332, 433 ); 
       popupWin.setText(name);
       popupWin.clearIcon();  // hide icon
       popupWin.denyResize(); // deny resizing
       popupWin.denyPark();   // deny parking
       popupWin.attachObject(objName);
       popupWin.attachEvent("onClose", function(popupWin){popupWin.hide();});
       popupWin.center();
       currentWindow = popupWin;
       }
       else {
	       currentWindow = dhxWins.window(name);
    	   currentWindow.show();
       }
}

var dhxNoteWins = null;
function showNotes(name,divName,readOnlyOrNot) {
	var noteArea = document.getElementById("noteArea"+divName);
	if(readOnlyOrNot != 'readOnly') 
		noteArea.innerHTML = noteArea.innerHTML.replace(/readOnly/gi,''); 
//		noteArea.innerHTML = noteArea.innerHTML.replace(/readonly/g,'');} //readOnly for IE, readonly for FF
	$("externalNoteDivAreaUSE").value = $v("specialInstructions"); // for FF, FF doesn't hold the value
	$("internalNoteDivAreaUSE").value = $v("internalNote"); // for FF
	$("orderShiptoNoteDivAreaUSE").value = $v("orderShiptoNote"); // for FF
	$("holdCommentsDivAreaUSE").value = $v("holdComments"); // for FF
//	var hiddenDiv = document.getElementById(divName).innerHTML.replace(/NOUSE/g,'USE');
//  noteArea.innerHTML = hiddenDiv;
    noteArea.style.display="";
        
       if( dhxNoteWins == null) {
	       	dhxNoteWins = new dhtmlXWindows();
	       	dhxNoteWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
       }
       
       if (!dhxNoteWins.window(name)) {
       	   if (_isIE)
	       	 var popupWin = dhxNoteWins.createWindow(name,0, 0, 410, 245);
	       else
	         var popupWin = dhxNoteWins.createWindow(name,0, 0, 438, 260);
	       popupWin.setText(name);
	       popupWin.clearIcon();  // hide icon
	       popupWin.denyResize(); // deny resizing
	       popupWin.denyPark();   // deny parking
	       popupWin.attachObject("noteArea"+divName);
	       popupWin.attachEvent("onClose", function(popupWin){popupWin.hide();});
	       popupWin.center();
	       currentWindow = popupWin;
       }
       else {
			dhxNoteWins.window(name).show();
       } 
   
       try {
       		document.getElementById(divName+"AreaUSE").focus();
       } catch(ex) {}
}

function customerIdChanged11() {
	  if ($v("totalLines") >0)
      {	
      	  doInitGrid();
          $("salesQuoteLineBean").style["display"] = "";
      }
// alert("creditStatus:"+$v("creditStatus")+"    showOverCreditLimit:"+$v("showOverCreditLimit")+"       done:"+done); 
//	  if($v("creditStatus").toUpperCase() == 'STOP' && $v("status") != 'Confirmed' && $v("showOverCreditLimit") == 'true' && done) {
	  if(($v("availableCredit") < 0 || ($("withinTerms") != null && $v("withinTerms").toUpperCase() == 'N')) && ($v("status").toUpperCase() != 'POSUBMIT' || $v("orderType") != 'MR') && $v("status") != 'Confirmed' && $v("showOverCreditLimit") == 'true' && $v("billToCompanyId") != "CASH_SALES" && done ) {
		showWin(messagesData.creditreview,"overCreditLimitArea");
	    done = false; 
     }
  
     try{ 
//       alert('${param.caller}'+":"+'${param.callback}');
	       if( parent.name == '_HaasTcmisApplication' ) 
		       parent.frames['${param.caller}']['${param.callback}'](window.name);
		   else
		       opener['${param.callback}'](window.name);
       } catch(ex){ //alert( "message is:"+ex.message ) ;
       } 
}

with(milonic=new menuname("addCharges")){
         top="offset=2"
         style = contextWideStyle;
         margin=3
         aI("text=<fmt:message key="label.additional.charges"/>;url=javascript:addLineCharges();");
         aI("type=header;text=<fmt:message key="label.customerreturnrequest"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
         aI("text=<fmt:message key="label.autoallocateline"/>;url=javascript:saveBeforeAutoAllocateLine();");
         aI("text=<fmt:message key="label.showallocatableig"/>;url=javascript:allocationPopup('IG');");
         aI("text=<fmt:message key="label.showallocatableregion"/>;url=javascript:allocationPopup('REGION');");
         aI("text=<fmt:message key="label.showallocatableglobal"/>;url=javascript:allocationPopup('GLOBAL');");
         aI("text=<fmt:message key="label.showpreviousordersforcustomer"/>;url=javascript:showPreHistory();");
         aI("text=<fmt:message key="label.showallpreviousorders"/>;url=javascript:showAllPreHistory();");
         aI("text=<fmt:message key="showpohistory.title"/>;url=javascript:showPoHistory();");
         aI("text=<fmt:message key="showquotationallhistoryforcustomer.title"/>;url=javascript:showQuotationHistory();");
         aI("text=<fmt:message key="showquotationallhistory.title"/>;url=javascript:showAllQuotationHistory();");
         aI("type=header;text=<fmt:message key="label.showpricelistinfo"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
         aI("type=header;text=<fmt:message key="label.showcurrentsourcinginfo"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
         //aI("text=<fmt:message key="label.showpricelistinfo"/>;url=javascript:showPriceListInfo();");
         //aI("text=<fmt:message key="label.showcurrentsourcinginfo"/>;url=javascript:addPopUrl();");
         //aI("text=<fmt:message key="label.showiteminfo"/>;url=javascript:addPopUrl();");
}

drawMenus();

function pp(name) {
	var value = $v(name);
//	alert( value );
	return encodeURIComponent(value);
}

function gg(name) {
	var value = null;
	value = cellValue(selectedRowId,name);
		
//	alert( value );
	return encodeURIComponent(value);
}

function savealloline(rowId,allocated) {
	if( allocated )
		cell(rowId,"quantity").setValue(allocated); 
}

function getcurpath (){
	return encodeURIComponent(parent.parent.getTabName($v('tabId')))+'-'+cellValue(selectedRowId,'lineItem');
}

function saveBeforeAutoAllocateLine() {
    $("uAction").value  = "saveLine"; 
 
   showPageWait();
   
   if (mygrid != null) {
  	copyDisplayValuesToHiddenColumns();
    mygrid.parentFormOnSubmit();
   }
    	
   var rowsNum = mygrid.getRowsNum();
   $("totalRows").value = rowsNum;
   
   document.genericForm.submit();
}


function showPoHistory() {
	if (gg('specListConcat') == 'Std%20Mfg%20Cert')
		var specList = '';
	else
		var specList = gg('specList');
		
	var loc = "/tcmIS/distribution/showpohistory.do"+
		"?region=" +
		"&specList="+   //specList+
		"&inventoryGroup="+ gg('inventoryGroup') +
		"&curpath="+getcurpath()+
		"&itemId="+ gg('itemId');
	children[children.length] = openWinGeneric(loc, "showPoHistory"+pp('prNumber').replace(/[.]/,"_")+"_"+gg('lineItem'), "1024", "500", "yes", "50", "50");
	//children[children.length] = openWinGenericDefault(loc, "showPoHistory"+pp('prNumber').replace(/[.]/,"_")+"_"+gg('lineItem'), "yes", "50", "50");
}

function showPreHistory() {
	if (gg('specListConcat') == 'Std%20Mfg%20Cert')
		var specList = '';
	else
		var specList = gg('specList');
		
	var loc = "/tcmIS/distribution/showpreviousorders.do"+
	    "?companyId=" +pp('companyId')+
		"&region=" +
		"&specList=" + //specList+
		"&itemId="+ gg('itemId') +
		"&inventoryGroup="+ gg('inventoryGroup') +
		"&customerName="+ pp('customerName') +
		"&customerId="+ pp('customerId');

	children[children.length] = openWinGeneric(loc, "showPreHistory"+pp('prNumber').replace(/[.]/,"_")+"_"+gg('lineItem'), "1024", "500", "yes", "50", "50");
	//children[children.length] = openWinGenericDefault(loc, "showPreHistory"+pp('prNumber').replace(/[.]/,"_")+"_"+gg('lineItem'), "yes", "50", "50");
}

function showAllPreHistory() {
	if (gg('specListConcat') == 'Std%20Mfg%20Cert')
		var specList = '';
	else
		var specList = gg('specList');

	var loc = "/tcmIS/distribution/showallpreviousorders.do"+
	    //"?companyId=" +pp('companyId')+
		"?region=" +
		//"&companyId=" +pp('companyId')+
		"&specList=" +  //specList+
		"&curpath="+getcurpath()+
		"&itemId="+ gg('itemId') +
		"&inventoryGroup="+ gg('inventoryGroup');

	children[children.length] = openWinGeneric(loc, "showAllPreHistory"+pp('prNumber').replace(/[.]/,"_")+"_"+gg('lineItem'), "1024", "500", "yes", "50", "50");
	//children[children.length] = openWinGenericDefault(loc, "showAllPreHistory"+pp('prNumber').replace(/[.]/,"_")+"_"+gg('lineItem'), "yes", "50", "50");
}

function showQuotationHistory() {
	if (gg('specListConcat') == 'Std%20Mfg%20Cert')
		var specList = '';
	else
		var specList = gg('specList');
		
	var loc = "/tcmIS/distribution/quotationhistory.do"+
	"?region=" +
	"&specList=" +specList+
	"&companyId=" +pp('companyId')+
	"&curpath="+getcurpath()+
	"&itemId="+ gg('itemId') +
	"&customerName="+ pp('customerName') +
	"&inventoryGroup="+ gg('inventoryGroup') +
	"&customerId="+ pp('customerId');

	children[children.length] = openWinGeneric(loc, "showQuotationHistory"+pp('prNumber').replace(/[.]/,"_")+"_"+gg('lineItem'), "1024", "500", "yes", "50", "50");
	//children[children.length] = openWinGenericDefault(loc, "showQuotationHistory"+pp('prNumber').replace(/[.]/,"_")+"_"+gg('lineItem'), "yes", "50", "50");

}

function showAllQuotationHistory() {
	if (gg('specListConcat') == 'Std%20Mfg%20Cert')
		var specList = '';
	else
		var specList = gg('specList');
		
	var loc = "/tcmIS/distribution/quotationhistory.do"+
	"?region=" +//gg('region')+
	//"&companyId=" +pp('companyId')+
	"&specList=" +specList+
	"&curpath="+getcurpath()+
	"&inventoryGroup="+ gg('inventoryGroup') +
	"&itemId="+ gg('itemId');

	children[children.length] = openWinGeneric(loc, "showAllQuotationHistory"+pp('prNumber').replace(/[.]/,"_")+"_"+gg('lineItem'), "1024", "500", "yes", "50", "50");
	//children[children.length] = openWinGenericDefault(loc, "showAllQuotationHistory"+pp('prNumber').replace(/[.]/,"_")+"_"+gg('lineItem'), "yes", "50", "50");
}

function showPriceListInfo() {

	var loc = "/tcmIS/distribution/showpricelist.do"+
	"?companyId=" +pp('companyId')+
	"&searchArgument="+gg('labelSpec')+
	"&curpath="+getcurpath()+
	"&priceGroupId="+pp('priceGroupId')+
	"&searchMode=is"+
	"&opsEntityId="+pp('opsEntityId')+
	"&searchField=catPartNo"+
	"&uAction=search";
	children[children.length] = openWinGeneric(loc, "showPriceListInfo"+pp('prNumber').replace(/[.]/,"_")+"_"+gg('lineItem'), "1024", "500", "yes", "50", "50");
	//children[children.length] = openWinGenericDefault(loc, "showPriceListInfo"+pp('prNumber').replace(/[.]/,"_")+"_"+gg('lineItem'), "yes", "50", "50");
}

function onloadcallback() {
  <tcmis:jsReplace var="errorString" value="${tcmISError}" processMultiLines="true" />
  if($v("newTab") == 'Y') {
   	newScratchPad('',$v('newOrderType'),$v('newPrNumber'),$v('lastName'),$v('firstName'),'','${errorString}');
  }

  try {
  <c:if  test="${param.callbackparam == 'delete' && tcmISErrors == null}" >
		parent.frames['${param.caller}'].deleteQuote();
  </c:if>
  } catch(ex){}

}

function timeoutOnloadcallback() {
	setTimeout("onloadcallback();", 200);

	if ($v("popUpAutoAllocate") == 'Y') 
  		allocationPopup('IG',true);
}

function doF6() {
	if ($v("orderType") == 'MR' && (cellValue(selectedRowId,"quantity")*1 <= cellValue(selectedRowId,"shipped")*1 || cellValue(selectedRowId,"requestLineStatus") == 'Cancelled' || cellValue(selectedRowId,"requestLineStatus") == 'Canceled' || cellValue(selectedRowId,"salesOrder").length > 0))
 	{}
 	else if ($v("orderType") == 'Blanket Order')
 	{}
 	else
 		allocationPopup('IG');
}

function doF8() {
	if ($v("orderType") == 'MR') 
		showMrLineAllocationReport();
}

function doF10() {
	if (cellValue(selectedRowId,"itemId").length > 0) 
		showQuickView();
}

var unitOfSaleQuantityPerEachMissing = false;



// -->
</script>
</head>

<body bgcolor="#ffffff" onresize="scratchPadResize();" onload="myResultOnload();closeAllchildren();timeoutOnloadcallback();itarAjaxCall();" onunload="closeAllchildren();">

<tcmis:form action="/scratchpadmain.do" onsubmit="" target="_self">
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>


<c:set var="scratchPadPermission" value='N' />
	<tcmis:inventoryGroupPermission indicator="true" userGroupId="GenerateOrders" inventoryGroup="${bean.inventoryGroup}">
		<c:set var="scratchPadPermission" value='Y' />

		<c:if test="${param.blank == 'Y' || blank == 'Y'}" >
	     <c:set var="newPermission" value='Y' />
		 <c:set var="scratchPadPermission" value='N' />
		</c:if> 
	</tcmis:inventoryGroupPermission> 

<div class="interface" id="mainPage" style="">

<!-- Search div Begins -->
<div id="searchFrameDiv" style="margin: 4px 4px 4px 4px;">
<%@ include file="/distribution/scratchpadsearch.jsp" %>
</div>
<!-- Search div Ends -->

<div class="spacerY">&nbsp;</div>

<!-- Result Frame Begins -->
<c:choose>
  <c:when test="${salesQuoteViewCollection == null}" >
    <div id="resultGridDiv" style="display: none;margin: 4px 4px 0px 4px;"> 
  </c:when>
  <c:otherwise>
    <div id="resultGridDiv" style="display: ;margin: 4px 4px 0px 4px;">  
  </c:otherwise>
</c:choose>

<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50%.
     You also need to set a variable resultWidthResize to false in pagenameresults.jsp. -->
     
<%--NEw -Transit Page Starts --%>
<div id="resultTransitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->     

<div id="unitOfSaleQtyPerEachMessageArea" class="roundcont filterContainer" style="display:none;">
<table id="searchMaskTable" width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
   <!-- Insert all the search option within this div -->
      <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
        <tr>
          <td id="unitOfSaleQtyPerEachMessage" class="optionTitleBold" align="center"><fmt:message key="error.missingunitofsaleqtypereach" /></td>
        </tr>
        <tr>
          <td class="optionTitleBold" align="center">
             <input name="itOK" id="itOK" type="button" value="<fmt:message key="label.ok"/>" class="inputBtns" 
                onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" 
                onclick="popupUnitOfSaleWinHandle.close();"/>
          </td>
        </tr>
      </table>  
   <!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
</div>


<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Please keep the <span></span> on the same line this will avoid extra virtical space. 
      --%>
      <div id="mainUpdateLinks" style="display:"> <%-- mainUpdateLinks Begins --%>
      <span id="showlegendLink"><a href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a></span>
      <span id="showAddLineLink"><span id="addLineSpan"> | <a href="javascript:addLine()"><fmt:message key="label.addline"/></a></span><span id="deleteDupLineLink"><span id="dupLineSpan"> | <a href="#" onclick="doDup();"><fmt:message key="receiving.label.duplicateline"/></a></span><span id="deleteLineSpan"> | <a href="#" onclick="deleteLine();"><fmt:message key="label.deleteline"/></a></span></span></span>
     </div> <%-- mainUpdateLinks Ends --%>
	 </div> <%-- boxhead Ends --%>

	<c:if test="${scratchPadPermission == 'Y' && blank != 'Y'}">
	 <script type="text/javascript">
	 <!--
	  showEditArea = true;
	 //-->
	 </script>
	</c:if>

<div id="salesQuoteLineBean" style="display:none;margin: 4px 4px 0px 4px;"  onmouseover="clearRightclick();"></div>
   
<c:if test="${salesQuoteLineViewCollection != null}" >
<c:forEach var="salesQuoteLine" items="${salesQuoteLineViewCollection}" varStatus="status">
<c:set var="dataCount" value='${dataCount+1}'/>
 <c:choose>
   <c:when test="${salesQuoteLine.mvItem == 'Y' && (salesQuoteLine.requestLineStatus == 'Invoiced' || scratchPadPermission == 'N')}">
     <input class="inputBox" id="unitOfSalePrice${dataCount}"  type="hidden" value="${unitOfSalePrice}" />
   </c:when>
 </c:choose>
 <c:if test="${salesQuoteLine.mvItem == 'Y' && (empty salesQuoteLine.unitOfSaleQuantityPerEach || salesQuoteLine.unitOfSaleQuantityPerEach == 0)}">
    <script type="text/javascript">
     <!--
     unitOfSaleQuantityPerEachMissing = true;     
     //-->
     </script>
    
 </c:if>
 </c:forEach>

<script type="text/javascript">
<!--

<c:forEach var="salesQuoteLine" items="${salesQuoteLineViewCollection}" varStatus="status">
	<c:set var="inventoryGroupPermission" value='N' />
 	<tcmis:inventoryGroupPermission indicator="true" userGroupId="GenerateOrders" inventoryGroup="${salesQuoteLine.inventoryGroup}">
		<c:if test="${scratchPadPermission == 'Y'}"><c:set var="inventoryGroupPermission" value='Y' /> </c:if>
 	</tcmis:inventoryGroupPermission> 
	<c:if test="${'Y' == inventoryGroupPermission}">
		inventoryGroup[${status.count}] = inventoryGroupTemp;
	</c:if>
	<c:if test="${'N' == inventoryGroupPermission}">
		inventoryGroup[${status.count}] = [
	 		<c:forEach var="nouse0" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status">
			  <c:if  test="${ status.current.opsEntityId == salesQuoteViewCollection.opsEntityId }"> 
				 <c:forEach var="nouse1" items="${status.current.hubIgCollection}" varStatus="status1">
				  <c:forEach var="nouse2" items="${status1.current.inventoryGroupCollection}" varStatus="status2">
				  	<c:if  test="${ status2.current.inventoryGroup == salesQuoteLine.inventoryGroup }"> 
					 {
						value:'${status2.current.inventoryGroup}',
						text:'<tcmis:jsReplace value="${status2.current.inventoryGroupName}"/>'
					 }
					</c:if> 
				   </c:forEach>
				 </c:forEach>
			  </c:if> 
			</c:forEach>
			];
	</c:if>
</c:forEach>

<c:set var="dataCount" value='${0}'/>
<c:set var="showDelete" value="N" />
<c:set var="showReleaseMR" value="N" />
<c:set var="hasQualityControlItem" value="false"/>
var jsonMainData = {
rows:[
<c:forEach var="salesQuoteLine" items="${salesQuoteLineViewCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<c:set var="dataCount" value='${dataCount+1}'/>
<fmt:formatDate var="fmtRequiredDatetime" value="${salesQuoteLine.requiredDatetime}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtPromisedDate" value="${salesQuoteLine.promisedDate}" pattern="${dateFormatPattern}"/>

  <c:set var="hasQualityControlItem" value="false"/>
  <c:if test="${!hasQualityControlItem && salesQuoteLine.isQualityControlItem}">
    <c:set var="hasQualityControlItem" value="true"/>
  	<c:set var="qualityControlItemId" value="${salesQuoteLine.itemId}"/>
    <c:set var="qualityControlInventoryGroup" value="${salesQuoteLine.inventoryGroup}"/>
  </c:if>
  
  <c:set var="taxExempt" value="false"/>
  <c:if test="${salesQuoteLine.taxExempt == 'Y'}"><c:set var="taxExempt" value="true"/></c:if>
  
  <c:set var="shipComplete" value="false"/>
  <c:if test="${salesQuoteLine.shipComplete == 'Y'}"><c:set var="shipComplete" value="true"/></c:if>
  
  <c:set var="linesGroup" value="false"/>
  <c:if test="${salesQuoteLine.consolidateShipment == 'Y'}"><c:set var="linesGroup" value="true"/></c:if>
  
  <c:set var="dropShipOverride" value="false"/>
  <c:if test="${salesQuoteLine.dropShipOverride == 'Y'}"><c:set var="dropShipOverride" value="true"/></c:if>
  
  <c:set var="forceHold" value="false"/>
  <c:if test="${salesQuoteLine.forceHold == 'Y' || salesQuoteLine.forceHold == 'y'}"><c:set var="forceHold" value="true"/></c:if>
  
  <c:set var="scrap" value="false"/>
  <c:if test="${salesQuoteLine.scrap == 'Y' || salesQuoteLine.scrap == 'y'}"><c:set var="scrap" value="true"/></c:if>
 
 <c:set var="currencyId" value='${salesQuoteLine.currencyId}' />
 
 <fmt:setLocale value="en_US"/>
	 <fmt:formatNumber var="catalogPrice" value="${salesQuoteLine.catalogPrice}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>
	 <fmt:formatNumber var="unitOfSalePrice" value="${salesQuoteLine.unitOfSalePrice}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>
 <fmt:setLocale value="${pageLocale}" scope="page"/>
 
 <c:choose>
   <c:when test="${salesQuoteLine.mvItem == 'Y'}">
     <c:set var="catalogPriceInput"><fmt:message key="label.varies"/><input id="catalogPriceDisplay${dataCount}" type="hidden" value="${catalogPrice}"/></c:set>
   </c:when> 
   <c:when test="${salesQuoteLine.requestLineStatus == 'Invoiced' || scratchPadPermission == 'N'}">
     <c:set var="catalogPriceInput" value='${catalogPrice}<input id="catalogPriceDisplay${dataCount}" type="hidden" value="${catalogPrice}"/>' />
   </c:when>
   <c:when test="${fn:toUpperCase(salesQuoteViewCollection.status) == 'CONFIRMED' || (salesQuoteViewCollection.orderType == 'MR' && salesQuoteViewCollection.originalSalesQuoteType == 'Blanket Order')}">
     <c:set var="catalogPriceInput" value='${catalogPrice}<input id="catalogPriceDisplay${dataCount}" type="hidden" value="${catalogPrice}"/>' />
   </c:when>
   <c:otherwise>
     <c:set var="catalogPriceInput"><input class="inputBox" id="catalogPriceDisplay${dataCount}" type="text" value="${catalogPrice}" size="5"  maxlength="11" onchange="unitPriceChanged(${dataCount});" /></c:set>
   </c:otherwise>
 </c:choose>

 <c:choose>
   <c:when test="${salesQuoteLine.mvItem == 'Y' && (salesQuoteLine.requestLineStatus == 'Invoiced' || scratchPadPermission == 'N')}">
     <c:set var="unitOfSalePriceInput" value='${unitOfSalePrice}' />
   </c:when>
   <c:when test="${fn:toUpperCase(salesQuoteViewCollection.status) == 'CONFIRMED' || (salesQuoteViewCollection.orderType == 'MR' && salesQuoteViewCollection.originalSalesQuoteType == 'Blanket Order')}">
     <c:set var="unitOfSalePriceInput" value='${unitOfSalePrice}' />
   </c:when>
   <c:otherwise>
     <c:set var="unitOfSalePriceInput"><input class="inputBox" id="unitOfSalePriceDisplay${dataCount}" type="text" value="${unitOfSalePrice}" size="5"  maxlength="11" onchange="unitOfSalePriceChanged(${dataCount});" /></c:set>
   </c:otherwise>
 </c:choose>

 <c:choose>
   <c:when test="${salesQuoteLine.mvItem == 'N' && ((!empty salesQuoteLine.catalogPrice) and  (!empty salesQuoteLine.quantity ))}">
     <c:set var="extPrice" value='${salesQuoteLine.catalogPrice * salesQuoteLine.quantity }' />
   </c:when>
   <c:when test="${salesQuoteLine.mvItem == 'Y' && ((!empty salesQuoteLine.unitOfSalePrice) and  (!empty salesQuoteLine.quantity ))}">
     <c:set var="extPrice" value='${salesQuoteLine.unitOfSalePrice * salesQuoteLine.unitOfSaleQuantityPerEach *salesQuoteLine.quantity }' />
   </c:when>
   <c:otherwise>
     <c:set var="extPrice" value='' />
  </c:otherwise>
 </c:choose>
 
 <c:choose>
   <c:when test="${!empty salesQuoteLine.expectedUnitCost && salesQuoteLine.expectedUnitCost != 0}">
	    <c:choose>
		   <c:when test="${salesQuoteLine.mvItem == 'N'}">
		       <c:choose>
				   <c:when test="${(empty salesQuoteLine.catalogPrice || salesQuoteLine.catalogPrice == 0) && scratchPadPermission == 'Y' && fn:toUpperCase(salesQuoteViewCollection.status) != 'CONFIRMED' && salesQuoteLine.requestLineStatus != 'Invoiced'}">
				     <c:set var="margin"><input class="inputBox" id="marginNumber${dataCount}" type="text" value="" size="2" onchange="caculateUnitPrice(${dataCount});" />%</c:set>
				   </c:when>
				   <c:when test="${(empty salesQuoteLine.catalogPrice || salesQuoteLine.catalogPrice == 0) && (scratchPadPermission == 'N' || fn:toUpperCase(salesQuoteViewCollection.status) == 'CONFIRMED' || salesQuoteLine.requestLineStatus == 'Invoiced')}">
				     <c:set var="margin"></c:set>
				   </c:when>
				   <c:when test="${scratchPadPermission == 'N' || fn:toUpperCase(salesQuoteViewCollection.status) == 'CONFIRMED' || salesQuoteLine.requestLineStatus == 'Invoiced'}">
				   		<c:set var="marginValue" value='${(catalogPrice-salesQuoteLine.expectedUnitCost)/catalogPrice*100}' />
				   		<fmt:setLocale value="en_US"/>
	         		    	<fmt:formatNumber var="fmtMargin" value="${marginValue}"  pattern="${oneDigitformat}"></fmt:formatNumber>
	         		    <fmt:setLocale value="${pageLocale}" scope="page"/>
		     			<c:set var="margin">${fmtMargin}%</c:set>
		   		   </c:when>
		   		   <c:when test="${salesQuoteViewCollection.orderType == 'MR' && salesQuoteLine.originalSalesQuoteType == 'Blanket Order'}">
				   		<c:set var="marginValue" value='${(catalogPrice-salesQuoteLine.expectedUnitCost)/catalogPrice*100}' />
				   		<fmt:setLocale value="en_US"/>
	         		    	<fmt:formatNumber var="fmtMargin" value="${marginValue}"  pattern="${oneDigitformat}"></fmt:formatNumber>
	         		    <fmt:setLocale value="${pageLocale}" scope="page"/>
		     			<c:set var="margin">${fmtMargin}%</c:set>
		   		   </c:when>
				   <c:otherwise>
				     <c:set var="marginValue" value='${(catalogPrice-salesQuoteLine.expectedUnitCost)/catalogPrice*100}' />
				     <fmt:setLocale value="en_US"/>
	         		 	<fmt:formatNumber var="fmtMargin" value="${marginValue}"  pattern="${oneDigitformat}"></fmt:formatNumber>
	         		 <fmt:setLocale value="${pageLocale}" scope="page"/>
		     		 <c:set var="margin"><input class="inputBox" id="marginNumber${dataCount}" type="text" value="${fmtMargin}" size="2" onchange="caculateUnitPrice(${dataCount});" />%</c:set>
				  </c:otherwise>
				</c:choose>
		   </c:when>
           <c:when test="${salesQuoteLine.mvItem == 'Y'}">
		       <c:choose>
				   <c:when test="${(empty salesQuoteLine.unitOfSalePrice) || (salesQuoteLine.unitOfSalePrice == 0) && scratchPadPermission == 'Y' && fn:toUpperCase(salesQuoteViewCollection.status) != 'CONFIRMED' && salesQuoteLine.requestLineStatus != 'Invoiced'}">
				     <c:set var="margin"><input class="inputBox" id="marginNumber${dataCount}" type="text" value="" size="2" onchange="caculateUnitOfSalePrice(${dataCount});" />%</c:set>
				   </c:when>
				   <c:when test="${(empty salesQuoteLine.unitOfSalePrice || salesQuoteLine.unitOfSalePrice == 0) && (scratchPadPermission == 'N' || fn:toUpperCase(salesQuoteViewCollection.status) == 'CONFIRMED' || salesQuoteLine.requestLineStatus == 'Invoiced')}">
				     <c:set var="margin"></c:set>
				   </c:when>
				   <c:when test="${scratchPadPermission == 'N' || fn:toUpperCase(salesQuoteViewCollection.status) == 'CONFIRMED' || salesQuoteLine.requestLineStatus == 'Invoiced'}">
				      <c:if test="${empty salesQuoteLine.unitOfSaleQuantityPerEach || salesQuoteLine.unitOfSaleQuantityPerEach == 0}">
				           <c:set var="margin"></c:set>
				      </c:if>		
				      <c:if test="${!empty salesQuoteLine.unitOfSaleQuantityPerEach && salesQuoteLine.unitOfSaleQuantityPerEach != 0}">      		      
					   	  <c:set var="marginValue" value='${((salesQuoteLine.unitOfSalePrice * salesQuoteLine.unitOfSaleQuantityPerEach)-salesQuoteLine.expectedUnitCost)/(salesQuoteLine.unitOfSalePrice * salesQuoteLine.unitOfSaleQuantityPerEach)*100}' />
		         		  <fmt:setLocale value="en_US"/>
		         		 	 <fmt:formatNumber var="fmtMargin" value="${marginValue}"  pattern="${oneDigitformat}"></fmt:formatNumber>
			     		  <fmt:setLocale value="${pageLocale}" scope="page"/>
			     		  <c:set var="margin">${fmtMargin}%</c:set>
		     		  </c:if>		     		 
		     	   </c:when>				    
		   		   <c:when test="${salesQuoteViewCollection.orderType == 'MR' && salesQuoteLine.originalSalesQuoteType == 'Blanket Order'}">
		   		     <c:if test="${empty salesQuoteLine.unitOfSaleQuantityPerEach || salesQuoteLine.unitOfSaleQuantityPerEach == 0}">
		   		       <c:set var="margin"></c:set>		   		    	   		       
                     </c:if>       
                     <c:if test="${!empty salesQuoteLine.unitOfSaleQuantityPerEach && salesQuoteLine.unitOfSaleQuantityPerEach != 0}">				   		
				   		<c:set var="marginValue" value='${((salesQuoteLine.unitOfSalePrice * salesQuoteLine.unitOfSaleQuantityPerEach)-salesQuoteLine.expectedUnitCost)/(salesQuoteLine.unitOfSalePrice * salesQuoteLine.unitOfSaleQuantityPerEach)*100}' />
	         		 	<fmt:setLocale value="en_US"/>	
	         		 		<fmt:formatNumber var="fmtMargin" value="${marginValue}"  pattern="${oneDigitformat}"></fmt:formatNumber>
		     			<fmt:setLocale value="${pageLocale}" scope="page"/>
		     			<c:set var="margin">${fmtMargin}%</c:set>
		     		 </c:if>
		   		   </c:when>
				   <c:otherwise>
	                 <c:if test="${empty salesQuoteLine.unitOfSaleQuantityPerEach || salesQuoteLine.unitOfSaleQuantityPerEach == 0}">
	                     <c:set var="margin"></c:set>
                     </c:if>       
                     <c:if test="${!empty salesQuoteLine.unitOfSaleQuantityPerEach && salesQuoteLine.unitOfSaleQuantityPerEach != 0}">					   
					     <c:set var="marginValue" value='${((salesQuoteLine.unitOfSalePrice * salesQuoteLine.unitOfSaleQuantityPerEach)-salesQuoteLine.expectedUnitCost)/(salesQuoteLine.unitOfSalePrice * salesQuoteLine.unitOfSaleQuantityPerEach)*100}' />
		         		 <fmt:setLocale value="en_US"/>	
		         		 	<fmt:formatNumber var="fmtMargin" value="${marginValue}"  pattern="${oneDigitformat}"></fmt:formatNumber>
			     		 <fmt:setLocale value="${pageLocale}" scope="page"/>
			     		 <c:set var="margin"><input class="inputBox" id="marginNumber${dataCount}" type="text" value="${fmtMargin}" size="2" onchange="caculateUnitOfSalePrice(${dataCount});" />%</c:set>
		     		 </c:if>
				  </c:otherwise>
				</c:choose>
		   </c:when>
           <c:otherwise>
		     <c:set var="margin"></c:set>
		  </c:otherwise>
		</c:choose>
   </c:when>
   <c:otherwise>
		<c:set var="margin" value='' />
  </c:otherwise>
 </c:choose>
 
 <fmt:setLocale value="en_US"/>
 	<fmt:formatNumber var="value" value="${extPrice}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>
 <fmt:setLocale value="${pageLocale}" scope="page"/>
  <c:set var="rcptQualityHoldSpec" value='' />
  <c:set var="rcptQualityHoldShelfLife" value='' />
  <c:if test="${salesQuoteLine.rcptQualityHoldSpec eq 'Y'}"><c:set var="rcptQualityHoldSpec" value='X' /></c:if>
  <c:if test="${salesQuoteLine.rcptQualityHoldShelfLife eq 'Y'}"><c:set var="rcptQualityHoldShelfLife" value='X' /></c:if>
  
  <tcmis:jsReplace var="catPartNo" value="${salesQuoteLine.catPartNo}" processMultiLines="false" />
  
  <tcmis:jsReplace var="specListConcat" value="${salesQuoteLine.specListConcat}" processMultiLines="true" />
  <tcmis:jsReplace var="specDetailConcat" value="${salesQuoteLine.specDetailConcat}" processMultiLines="true" />
  <tcmis:jsReplace var="specLibraryConcat" value="${salesQuoteLine.specLibraryConcat}" processMultiLines="true" />
  <tcmis:jsReplace var="specCocConcat" value="${salesQuoteLine.specCocConcat}" processMultiLines="false" />
  <tcmis:jsReplace var="specCoaConcat" value="${salesQuoteLine.specCoaConcat}" processMultiLines="false" />
  
 <c:choose>
  <c:when test="${scratchPadPermission == 'N' || salesQuoteLine.qtyShipped > 0 || salesQuoteLine.qtyPicked > 0 || fn:toUpperCase(salesQuoteLine.requestLineStatus) == 'CANCELED' || fn:toUpperCase(salesQuoteLine.requestLineStatus) == 'CANCELLED'}">
    <c:set var="specList">
    	<c:set var="specList" value="" />
  	</c:set>
  </c:when>
  <c:when test="${ fn:toUpperCase(salesQuoteViewCollection.status) == 'CONFIRMED' || (salesQuoteViewCollection.orderType == 'MR' && salesQuoteLine.originalSalesQuoteType == 'Blanket Order')}">
    <c:set var="specList">
    	<c:set var="specList" value="" />
  	</c:set>
  </c:when>
  
<%--  <c:when test="${scratchPadPermission == 'N' || fn:toUpperCase(salesQuoteLine.requestLineStatus) == 'CANCELED' || fn:toUpperCase(salesQuoteLine.requestLineStatus) == 'CANCELLED' || (salesQuoteViewCollection.orderType == 'Quote' && salesQuoteViewCollection.status == 'Confirmed')}">
      <c:set var="specList" value="" />
  </c:when>
  <c:when test="${salesQuoteViewCollection.orderType == 'MR' && fn:toUpperCase(salesQuoteViewCollection.status) == 'POSUBMIT' && !(salesQuoteLine.requestLineStatus eq null || salesQuoteLine.requestLineStatus eq '' || salesQuoteLine.requestLineStatus eq 'Draft')}">
    <c:set var="specList">
    	<c:set var="specList" value="" />
  	</c:set>
  </c:when>   --%>
  <c:otherwise>
  	<c:set var="specList">
    	<input type="button" class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'" id="fLookupButton" value="" onclick="getSpecList(${status.index+1})"/><input type="button" class="smallBtns" onmouseover="this.className=\'smallBtns smallBtnsOver\'" onMouseout="this.className=\'smallBtns\'" name="None" value="<fmt:message key="label.clear"/>" OnClick="clearSpecList(${status.index+1})"/>
  	</c:set>
  </c:otherwise>
 </c:choose>
 
 <c:if test="${salesQuoteViewCollection.orderType == 'MR' && (salesQuoteLine.rcptQualityHoldSpec == 'Y' || salesQuoteLine.rcptQualityHoldShelfLife == 'Y')}">
	<c:set var="showReleaseMR" value="Y" />
 </c:if>
 
 <c:set var="inventoryGroupPermission" value='N' />
 <tcmis:inventoryGroupPermission indicator="true" userGroupId="GenerateOrders" inventoryGroup="${salesQuoteLine.inventoryGroup}">
		<c:if test="${scratchPadPermission == 'Y'}"><c:set var="inventoryGroupPermission" value='Y' /> </c:if>
 </tcmis:inventoryGroupPermission> 
 <c:set var="rowClass" value=""/>
 <c:choose>
 	<c:when test="${dataCount % 2 == 1}"><c:set var="rowState" value="odd_haas"/></c:when>
 	<c:otherwise><c:set var="rowState" value="ev_haas"/></c:otherwise>
 </c:choose>
/*The row ID needs to start with 1 per their design. Use sinlge quotes for column data seperators.*/
{ id:${status.index +1},
<c:if test="${salesQuoteLine.cancelStatus eq 'rqcancel'}"><c:set var="rowClass" value="grid_lightgray "/></c:if>	
<c:if test="${salesQuoteLine.requestLineStatus eq 'Cancelled' || salesQuoteLine.requestLineStatus eq 'Canceled'}"><c:set var="rowClass" value="grid_black "/></c:if>
<c:if test="${salesQuoteViewCollection.orderType == 'MR' && fn:toUpperCase(salesQuoteViewCollection.status) == 'POSUBMIT' && (salesQuoteLine.requestLineStatus eq null || salesQuoteLine.requestLineStatus eq '' || salesQuoteLine.requestLineStatus eq 'Draft')}">
<c:set var="rowClass" value="grid_yellow "/>
	<c:set var="showDelete" value="Y" />
</c:if>
 "class": '${rowClass}${rowState}',
 data:['${scratchPadPermission}','${inventoryGroupPermission}',
  <c:choose>
	  <c:when test="${scratchPadPermission == 'N' || (salesQuoteViewCollection.orderType == 'Blanket Order' && salesQuoteViewCollection.status == 'Confirmed' && salesQuoteLine.blanketOrderRemainingQty <= 0)}">
	      'N',
	  </c:when>
	  <c:otherwise>
	      'Y',
	  </c:otherwise>
  </c:choose>	  
  <c:choose>
	  <c:when test="${scratchPadPermission == 'N' || not empty salesQuoteLine.salesOrder || fn:toUpperCase(salesQuoteLine.requestLineStatus) == 'CANCELED' || fn:toUpperCase(salesQuoteLine.requestLineStatus) == 'CANCELLED' || salesQuoteViewCollection.status == 'Confirmed'}">
	      'N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N'
	  </c:when>
	  <c:when test="${salesQuoteViewCollection.orderType == 'MR'}">
	  	<c:choose>
	  		<c:when test="${salesQuoteLine.requestLineStatus == 'Invoiced' && salesQuoteLine.quantity <= salesQuoteLine.qtyInvoiced}">
	      		'N'
	  		</c:when>
	  		<c:otherwise>
	    		'Y'
	  		</c:otherwise>
	  	</c:choose>,
	  	<c:choose>
	  		<c:when test="${salesQuoteLine.requestLineStatus == 'Invoiced'}">
	      		'N','N','N'
	  		</c:when>
	  		<c:otherwise>
	    		'Y','Y','Y'
	  		</c:otherwise>
	  	</c:choose>,
	  	<c:choose>
	  		<c:when test="${salesQuoteLine.qtyShipped > 0 || salesQuoteLine.qtyPicked > 0 }">
	      		'N'
	  		</c:when>
	  		<c:otherwise>
	    		'Y'
	  		</c:otherwise>
	  	</c:choose>,
	  	<c:choose>
	  		<c:when test="${(salesQuoteLine.qtyShipped > 0 || salesQuoteLine.qtyPicked > 0) && salesQuoteLine.consolidateShipment != 'Y'}">
	      		'N'
	  		</c:when>
	  		<c:otherwise>
	    		'Y'
	  		</c:otherwise>
	  	</c:choose>,
	  	<c:choose>
	  		<c:when test="${salesQuoteLine.requestLineStatus == 'Shipped' || salesQuoteLine.requestLineStatus == 'Delivered'|| salesQuoteLine.requestLineStatus == 'Invoiced'}">
	      		'N',
	  		</c:when>
	  		<c:otherwise>
	    		'Y',
	  		</c:otherwise>
	  	</c:choose>
	  	<c:choose>
	  		<c:when test="${salesQuoteLine.requestLineStatus == 'Shipped' || salesQuoteLine.requestLineStatus == 'Delivered'|| salesQuoteLine.requestLineStatus == 'Invoiced'}">
	      		'N','N',
	  		</c:when>
	  		<c:when test="${salesQuoteViewCollection.orderType == 'MR' && salesQuoteLine.originalSalesQuoteType == 'Blanket Order'}">
	      		'N','N',
	  		</c:when>
	  		<c:otherwise>
	    		'Y','Y',
	  		</c:otherwise>
	  	</c:choose>
	  	<c:choose>
	  		<c:when test="${salesQuoteLine.requestLineStatus == 'Shipped' || salesQuoteLine.requestLineStatus == 'Delivered'|| salesQuoteLine.requestLineStatus == 'Invoiced'}">
	      		'N','N','N','N','Y','Y','Y','Y'
	  		</c:when>
	  		<c:otherwise>
	    		'Y','Y','Y','Y','Y','Y','Y','Y'
	  		</c:otherwise>
	  	</c:choose>
	  </c:when>
	  <c:otherwise>
	  	'Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y'
	  </c:otherwise>
  </c:choose>,
  false,
  '${salesQuoteLine.lineItem}','${salesQuoteLine.customerPoLine}','${salesQuoteLine.alternateName}','<tcmis:jsReplace value="${salesQuoteLine.partDescription}" processMultiLines="true"/>',
  '${salesQuoteLine.specList}','${specList}','${salesQuoteLine.hazardous}',
  '${salesQuoteLine.quantity}',
  '${currencyId}','${catalogPriceInput}','${salesQuoteLine.priceBreakAvailable}',
  <c:choose>
  <c:when test="${salesQuoteLine.mvItem == 'Y'}">
  '${salesQuoteLine.totalUnitOfSaleQuantity}','${salesQuoteLine.unitOfSale}','${unitOfSalePriceInput}',
  </c:when>
  <c:otherwise>
  '','','',
  </c:otherwise>
</c:choose>
  '${value}','${margin}','${salesQuoteLine.lineAddCharges}',
  '${fmtRequiredDatetime}','${fmtPromisedDate}',
  '${salesQuoteLine.qtyShipped}','${salesQuoteLine.qtyPicked}','${salesQuoteLine.qtyOnhand}','${salesQuoteLine.qtyInpurchasing}',
  '${salesQuoteLine.requiredShelfLife}',${taxExempt},${shipComplete},${linesGroup},
  '${salesQuoteLine.deliveryType}','${salesQuoteLine.critical}','${salesQuoteLine.inventoryGroup}',${dropShipOverride},
  '<tcmis:jsReplace value="${salesQuoteLine.notes}" processMultiLines="true" />',
  '<tcmis:jsReplace value="${salesQuoteLine.internalNote}" processMultiLines="true" />',
  '<tcmis:jsReplace value="${salesQuoteLine.purchasingNote}" processMultiLines="true" />',
  <c:choose>
  <c:when test="${salesQuoteViewCollection.orderType == 'MR'}">
  '${rcptQualityHoldSpec}','${rcptQualityHoldShelfLife}',
  </c:when>
  <c:otherwise>
  '','',
  </c:otherwise>
</c:choose>
  '<tcmis:jsReplace value="${salesQuoteLine.customerPartNo}" processMultiLines="false"/>',
  '${salesQuoteLine.companyId}','${salesQuoteLine.prNumber}','${salesQuoteLine.catalogCompanyId}','${salesQuoteLine.catalogId}',
  '${salesQuoteLine.partGroupNo}','${salesQuoteLine.unitOfSale}','${salesQuoteLine.unitOfSaleQuantityPerEach}',
  '${catPartNo}','${salesQuoteLine.application}','${salesQuoteLine.qtyInvoiced}','${salesQuoteLine.requestLineStatus}',
  '${salesQuoteLine.itemId}','${salesQuoteLine.expectedUnitCost}',
  '${specListConcat}','${specDetailConcat}','${specLibraryConcat}','${specCocConcat}','${specCoaConcat}',
  '${salesQuoteLine.minimumGrossMargin}','${salesQuoteLine.maximumGrossMargin}',
  '${salesQuoteLine.medianSupplyLeadTime}','<tcmis:jsReplace value="${salesQuoteLine.inventoryGroupName}" processMultiLines="false"/>','${salesQuoteLine.quantity}',
  '${salesQuoteLine.quantityReturnAuthorized}','${salesQuoteLine.catalogPriceAvailable}','${salesQuoteLine.mvItem}',
  '','','${salesQuoteLine.originalSalesQuoteType}','${salesQuoteLine.blanketOrderRemainingQty}',
  '${salesQuoteLine.shippingReference}',${forceHold},'<tcmis:jsReplace value="${salesQuoteLine.forceHoldComment}" processMultiLines="true"/>',
  ${scrap},'${salesQuoteLine.radianPo}','${salesQuoteLine.poLine}','${salesQuoteLine.salesOrder}',
  '${salesQuoteLine.interCoUnitPricePctTol}','${salesQuoteLine.interCoExtPriceAmtTol}']}
 </c:forEach>
]};
dhxQualityControlItemWinHandle = null;
function itarAjaxCall()
{
	if (!(($v("status").toUpperCase() == 'CONFIRMED' || $v("status").toUpperCase() == 'POSUBMIT') || ($v("orderType") == 'MR' && $v("orderStatus").toUpperCase() != 'DRAFT')) && ${hasQualityControlItem})
	{
		var xmlhttp;
	    var qualityControlItemId = '${qualityControlItemId}';
	    var qualityControlInventoryGroup = '${qualityControlInventoryGroup}';
		if (window.XMLHttpRequest)
		  {// code for IE7+, Firefox, Chrome, Opera, Safari
		  	xmlhttp=new XMLHttpRequest();
		  }
		else
		  {// code for IE6, IE5
		  	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		  }
		  
		xmlhttp.onreadystatechange=function()
		  {
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    {
			  var results = jQuery.parseJSON(xmlhttp.responseText);
			  var qualityControlItemLabel = results.QualityControlItemLabel;
			  if(qualityControlItemLabel && qualityControlItemLabel.length > 0) 
			  {
				  var qualityControlItemMessage = document.getElementById("qualityControlItemMessage");
				  qualityControlItemMessage.innerHTML = qualityControlItemLabel; 
				  var dhxQualityControlItemWin = new dhtmlXWindows();
				  dhxQualityControlItemWin.setImagePath("/dhtmlxWindows/codebase/imgs/");
			      dhxQualityControlItemWinHandle = dhxQualityControlItemWin.createWindow(name,0, 0, 400, 120);
			      dhxQualityControlItemWinHandle.setText('');
			      dhxQualityControlItemWinHandle.clearIcon();  // hide icon
			      dhxQualityControlItemWinHandle.denyResize(); // deny resizing
			      dhxQualityControlItemWinHandle.denyPark();   // deny parking
			      dhxQualityControlItemWinHandle.attachObject($('qualityControlItemMessageArea'));
			      dhxQualityControlItemWinHandle.center();
			  }				   				        
		    }
		  }
		xmlhttp.open("POST","/tcmIS/haas/tabletQualityControlItemLabel.do?itemId="+qualityControlItemId+"&inventoryGroup="+qualityControlInventoryGroup,true);
		xmlhttp.send();
	}
}

var dhxUnitOfSaleWins = null;
var popupUnitOfSaleWinHandle = null;
if (unitOfSaleQuantityPerEachMissing == true) {
	showUnitOfSaleQuantityPerEachMissing();
}
function showUnitOfSaleQuantityPerEachMissing() {
    var name = "Missing UnitOfSaleQuantityPerEach"; 
    dhxUnitOfSaleWins = new dhtmlXWindows();
    dhxUnitOfSaleWins.setImagePath("/dhtmlxWindows/codebase/imgs/");          
    popupUnitOfSaleWinHandle = dhxUnitOfSaleWins.createWindow(name,0, 0, 400, 110);           
    popupUnitOfSaleWinHandle.setText(name);
    popupUnitOfSaleWinHandle.clearIcon();  // hide icon
    popupUnitOfSaleWinHandle.denyResize(); // deny resizing
    popupUnitOfSaleWinHandle.denyPark();   // deny parking    
    popupUnitOfSaleWinHandle.attachObject($('unitOfSaleQtyPerEachMessageArea'));    
    popupUnitOfSaleWinHandle.center();
    currentWindow = popupUnitOfSaleWinHandle;
}

// -->
</script>
</c:if>   

<c:if test="${showDelete == 'Y'}">
<script language="JavaScript" type="text/javascript">
<!--
	showDelete = true;
// -->    	
</script>		
</c:if>

<c:if test="${showReleaseMR == 'Y'}">
<script language="JavaScript" type="text/javascript">
<!--
	showReleaseMR = true;
// -->    	
</script>		
</c:if>

   <!-- Footer message start -->
    <div id="footer" class="messageBar"></div>
   <!-- Footer message end -->    
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div><!-- Result Div Ends -->
</div>


<!-- Hidden element start -->
<div id="hiddenElements" style="display:;">
<input type="hidden" name="uAction" id="uAction" value=""/>
<input type="hidden" name="startSearchTime" id="startSearchTime" value=""/>
<input type="hidden" name="totalLines" id="totalLines" value="${dataCount}" />  
<input type="hidden" name="totalRows" id="totalRows" value="${0}" />
<c:choose>
  <c:when test="${param.scratchPadId != null && not empty param.scratchPadId}">
   <input type="hidden" name="scratchPadId" id="scratchPadId" value="${param.scratchPadId}"/>
  </c:when>
  <c:otherwise>
   <input type="hidden" name="scratchPadId" id="scratchPadId" value="${salesQuoteViewCollection.prNumber}"/>
  </c:otherwise>
</c:choose>   
<c:choose>
  <c:when test="${empty salesQuoteViewCollection.prNumber}">
   <input type="hidden" name="prNumber" id="prNumber" value="${param.scratchPadId}"/>
  </c:when>
  <c:otherwise>
    <input type="hidden" name="prNumber" id="prNumber" value="${salesQuoteViewCollection.prNumber}"/>
  </c:otherwise>
</c:choose>      
<input type="hidden" name="companyId" id="companyId" value="${salesQuoteViewCollection.companyId}"/>
<input type="hidden" name="customerId" id="customerId" value="${salesQuoteViewCollection.customerId}"/>
<%--<input type="hidden" name="opsEntityId" id="opsEntityId" value="${salesQuoteViewCollection.opsEntityId}"/>--%>
<input type="hidden" name="opsCompanyId" id="opsCompanyId" value="${salesQuoteViewCollection.opsCompanyId}"/>
<input type="hidden" name="fieldSalesRepId" id="fieldSalesRepId" value="${salesQuoteViewCollection.fieldSalesRepId}"/>  
<input type="hidden" name="submittedBy" id="submittedBy" value="${salesQuoteViewCollection.submittedBy}"/>
<input type="hidden" name="salesAgentId" id="salesAgentId" value="${salesQuoteViewCollection.salesAgentId}"/>  
<input type="hidden" name="salesAgentName" id="salesAgentName" value="${salesQuoteViewCollection.salesAgentName}"/>
<input type="hidden" name="priceGroupId" id="priceGroupId" value="${salesQuoteViewCollection.priceGroupId}"/> 
<input type="hidden" name="facilityId" id="facilityId" value="${salesQuoteViewCollection.facilityId}"/>
<input type="hidden" name="accountSysId" id="accountSysId" value="${salesQuoteViewCollection.accountSysId}"/>
<input type="hidden" name="chargeType" id="chargeType" value="${salesQuoteViewCollection.chargeType}"/>
<input type="hidden" name="shelfLifeRequired" id="shelfLifeRequired" value="${salesQuoteViewCollection.shelfLifeRequired}"/>  
<input type="hidden" name="shipComplete" id="shipComplete" value="${salesQuoteViewCollection.shipComplete}"/> 
<input type="hidden" name="originalSalesQuoteId" id="originalSalesQuoteId" value="${salesQuoteViewCollection.originalSalesQuoteId}"/> 
<input type="hidden" name="releaseStatus" id="releaseStatus" value="${salesQuoteViewCollection.releaseStatus}"/> 
<input type="hidden" name="afterConfirmMr" id="afterConfirmMr" value="${afterConfirmMr}"/>
<input type="hidden" name="customerPriceGroupId" id="customerPriceGroupId" value="${salesQuoteViewCollection.customerPriceGroupId}"/>
<input type="hidden" name="withinTerms" id="withinTerms" value="${salesQuoteViewCollection.withinTerms}"/>
<input type="hidden" name="shipmentId" id="shipmentId" value="${salesQuoteViewCollection.shipmentId}"/>

<input type="hidden" name="creditReviewCurrencyId" id="creditReviewCurrencyId" value="${customerCreditColl.currencyId}"/>
<input type="hidden" name="openOrderAmount" id="openOrderAmount" value="<fmt:formatNumber value='${customerCreditColl.openOrderAmount}'  pattern='${unitpricecurrencyformat}'></fmt:formatNumber>"/>
<input type="hidden" name="openArAmount" id="openArAmount" value="<fmt:formatNumber value='${customerCreditColl.openArAmount}'  pattern='${unitpricecurrencyformat}'></fmt:formatNumber>"/>
<input type="hidden" name="creditLimitPopup" id="creditLimitPopup" value="<fmt:formatNumber value='${customerCreditColl.creditLimit}'  pattern='${unitpricecurrencyformat}'></fmt:formatNumber>"/>
<input type="hidden" name="availableCreditPopup" id="availableCreditPopup" value="<fmt:formatNumber value='${customerCreditColl.availableCredit}'  pattern='${unitpricecurrencyformat}'></fmt:formatNumber>"/>
<input type="hidden" name="greatestOverdueDays" id="greatestOverdueDays" value="${customerCreditColl.greatestOverdueDays}"/>
<input type="hidden" name="gracePeriodDays" id="gracePeriodDays" value="${customerCreditColl.gracePeriodDays}"/>
<input type="hidden" name="pastDueAmount030Days" id="pastDueAmount030Days" value="<fmt:formatNumber value='${customerCreditColl.pastDueAmount030Days}'  pattern='${unitpricecurrencyformat}'></fmt:formatNumber>"/>
<input type="hidden" name="pastDueAmount3160Days" id="pastDueAmount3160Days" value="<fmt:formatNumber value='${customerCreditColl.pastDueAmount3160Days}'  pattern='${unitpricecurrencyformat}'></fmt:formatNumber>"/>
<input type="hidden" name="pastDueAmount6190Days" id="pastDueAmount6190Days" value="<fmt:formatNumber value='${customerCreditColl.pastDueAmount6190Days}'  pattern='${unitpricecurrencyformat}'></fmt:formatNumber>"/>
<input type="hidden" name="pastDueAmount90Days" id="pastDueAmount90Days" value="<fmt:formatNumber value='${customerCreditColl.pastDueAmount90Days}'  pattern='${unitpricecurrencyformat}'></fmt:formatNumber>"/>

<input type="hidden" name="billToAddress" id="billToAddress" value=""/>
<input type="hidden" name="locationShortName" id="locationShortName" value=""/> 
<input type="hidden" name="overdueLimitBasis" id="overdueLimitBasis" value=""/> 
<input type="hidden" name="fixedCurrency" id="fixedCurrency" value=""/>

<input type="hidden" name="tabId" id="tabId" value="${param.tabId}"/> 
<input type="hidden" name="personnelId" id="personnelId" value="${param.personnelId}"/> 
<input type="hidden" name="lastName" id="lastName" value="${param.lastName}"/> 
<input type="hidden" name="firstName" id="firstName" value="${param.firstName}"/> 
<input type="hidden" name="newTab" id="newTab" value="${newTab}"/>
<input type="hidden" name="newPrNumber" id="newPrNumber" value="${newPrNumber}"/>
<input type="hidden" name="newOrderType" id="newOrderType" value="${newOrderType}"/>
<input type="hidden" name="closeTab" id="closeTab" value="${closeTab}"/>  
<input type="hidden" name="closeOldTab" id="closeOldTab" value="${closeOldTab}"/>  
<input type="hidden" name="newPermission" id="newPermission" value="${newPermission}"/> 
<input type="hidden" name="hideOrShowDiv" id="hideOrShowDiv" value="${hideOrShowDiv}"/>
<input type="hidden" name="selectedRowId" id="selectedRowId" value="${selectedRowId}"/> 
<input type="hidden" name="popUpAutoAllocate" id="popUpAutoAllocate" value="${popUpAutoAllocate}"/> 

<input type="hidden" name="requestorToProcedure" id="requestorToProcedure" value=""/>
<input type="hidden" name="requestorNameToProcedure" id="requestorNameToProcedure" value=""/>  
<input type="hidden" name="requestorPhoneToProcedure" id="requestorPhoneToProcedure" value=""/>
<input type="hidden" name="requestorFaxToProcedure" id="requestorFaxToProcedure" value=""/>
<input type="hidden" name="requestorEmailToProcedure" id="requestorEmailToProcedure" value=""/>

<input type="hidden" name="shipToAddressLine1ToProcedure" id="shipToAddressLine1ToProcedure" value=""/>
<input type="hidden" name="shipToAddressLine2ToProcedure" id="shipToAddressLine2ToProcedure" value=""/>
<input type="hidden" name="shipToAddressLine3ToProcedure" id="shipToAddressLine3ToProcedure" value=""/>
<input type="hidden" name="shipToAddressLine4ToProcedure" id="shipToAddressLine4ToProcedure" value=""/> 
<input type="hidden" name="shipToAddressLine5ToProcedure" id="shipToAddressLine5ToProcedure" value=""/> 
<%-- 
<input type="hidden" name="selectedLineItem" id="selectedLineItem" value="1"/>--%>

<input type="hidden" name="status" id="status" value="${salesQuoteViewCollection.status}"/>
<input type="hidden" name="orderStatus" id="orderStatus" value="${salesQuoteViewCollection.orderStatus}"/>
<input type="hidden" name="orderType" id="orderType" value="${salesQuoteViewCollection.orderType}"/>
<input type="hidden" name="creditStatus" id="creditStatus" value="${salesQuoteViewCollection.creditStatus}"/>
<input type="hidden" name="mvItem" id="mvItem" value="${salesQuoteViewCollection.mvItem}"/>
<input type="hidden" name="showOverCreditLimit" id="showOverCreditLimit" value="${showOverCreditLimit}"/>

<input type="hidden" name="tcmISError" id="tcmISError" value="${param.tcmISError}"/>
<input type="hidden" name="locationType" id="locationType" value="${param.locationType}"/>

<%--
<input type="hidden" name="paymentTerms" id="paymentTerms" value="${param.paymentTerms}"/>
--%>

<input type="hidden" name="creditLimit" id="creditLimit" value="${salesQuoteViewCollection.creditLimit}"/>
<input type="hidden" name="overdueLimit" id="overdueLimit" value="${param.overdueLimit}"/>

<%--<input type="hidden" name="priority" id="priority" value="${param.priority}"/>
<input type="hidden" name="partNumber" id="partNumber" value="${param.partNumber}"/> --%>

<input type="hidden" name="internalNote" id="internalNote" value="<tcmis:inputTextReplace value='${salesQuoteViewCollection.internalNote}'/>"/>
<input type="hidden" name="specialInstructions" id="specialInstructions" value="<tcmis:inputTextReplace value='${salesQuoteViewCollection.specialInstructions}'/>"/>
<input type="hidden" name="orderShiptoNote" id="orderShiptoNote" value="<tcmis:inputTextReplace value='${salesQuoteViewCollection.orderShiptoNote}'/>"/>
<input type="hidden" name="holdComments" id="holdComments" value="<tcmis:inputTextReplace value='${salesQuoteViewCollection.holdComments}'/>"/>
<input type="hidden" name="originalHoldComments" id="originalHoldComments" value="<tcmis:inputTextReplace value='${salesQuoteViewCollection.holdComments}'/>"/>
<input type="hidden" name="cancelMRLineReason" id="cancelMRLineReason" value=""/>

<input type="hidden" name="originalSalesQuoteType" id="originalSalesQuoteType" value="${salesQuoteViewCollection.originalSalesQuoteType}"/>
<input type="hidden" name="marginOutSideLimits" id="marginOutSideLimits" value=""/>
<input type="hidden" name="dateDelivered" id="dateDelivered" value=""/>
<input type="hidden" name="hub" id="hub" value="${salesQuoteViewCollection.hub}"/>
<input type="hidden" name="hubName" id="hubName" value="${salesQuoteViewCollection.hubName}"/>

<!-- Popup Calendar input options -->
<input type="hidden" name="blockBefore_requiredDatetime" id="blockBefore_requiredDatetime" value=""/>
<input type="hidden" name="blockAfter_requiredDatetime" id="blockAfter_requiredDatetime" value=""/>
<input type="hidden" name="blockBeforeExclude_requiredDatetime" id="blockBeforeExclude_requiredDatetime" value=""/>
<input type="hidden" name="blockAfterExclude_requiredDatetime" id="blockAfterExclude_requiredDatetime" value=""/>
<input type="hidden" name="inDefinite_requiredDatetime" id="inDefinite_requiredDatetime" value=""/>
<input type="hidden" name="blockBefore_promisedDate" id="blockBefore_promisedDate" value=""/>
<input type="hidden" name="blockAfter_promisedDate" id="blockAfter_promisedDate" value=""/>
<input type="hidden" name="blockBeforeExclude_promisedDate" id="blockBeforeExclude_promisedDate" value=""/>
<input type="hidden" name="blockAfterExclude_promisedDate" id="blockAfterExclude_promisedDate" value=""/>
<input type="hidden" name="inDefinite_promisedDate" id="inDefinite_promisedDate" value=""/>
<input type="hidden" name="blockBeforeExcludeDate" id="blockBeforeExcludeDate" value=""/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui 

pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display:none;z-index:10;">
</div>

<div id="overCreditLimitArea" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
</div>

<div id="noteAreainternalNoteDiv" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
<table id="searchMaskTable" width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
  	  <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
        <tr>
          <td class="optionTitleBoldLeft">
            <TEXTAREA name="internalNoteDivAreaUSE" id="internalNoteDivAreaUSE" readOnly onKeyDown='limitText("internalNoteDivAreaUSE", "<fmt:message key="label.orderinternal"/>", 1000);' onKeyUp='limitText("internalNoteDivAreaUSE", "<fmt:message key="label.orderinternal"/>", 1000);' class="inputBox" COLS=70 ROWS=10><tcmis:inputTextReplace value="${salesQuoteViewCollection.internalNote}"/></TEXTAREA></td>
        </tr> 
        <tr>
          <td align="center">
            <input name="internalNoteOk"  id="internalNoteOk"  type="button" value="<fmt:message key="label.ok(done)"/>" onClick='okClose("internalNote","internalNoteDivAreaUSE","<fmt:message key="label.orderinternal"/>");' class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" />
            <input name="internalNoteCleae"  id="internalNoteClear"  type="button" value="<fmt:message key="label.clear"/>" onClick='clearNotes("internalNoteDivAreaUSE","internalNote");' class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" />
          </td>
        </tr> 
      </table>  
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table></div>

<div id="noteAreaexternalNoteDiv" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
<table id="searchMaskTable" width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
  	  <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
        <tr>
          <td class="optionTitleBoldLeft">
          	<TEXTAREA name="externalNoteDivAreaUSE" id="externalNoteDivAreaUSE" readOnly onKeyDown='limitText("externalNoteDivAreaUSE", "<fmt:message key="label.orderexternal"/>", 1000);' onKeyUp='limitText("externalNoteDivAreaUSE", "<fmt:message key="label.orderexternal"/>", 1000);' class="inputBox" COLS=70 ROWS=10><tcmis:inputTextReplace value="${salesQuoteViewCollection.specialInstructions}"/></TEXTAREA></td>
        </tr> 
        <tr>
          <td align="center">
            <input name="externalNoteOk"  id="externalNoteOk"  type="button" value="<fmt:message key="label.ok(done)"/>" onClick='okClose("specialInstructions","externalNoteDivAreaUSE","<fmt:message key="label.orderexternal"/>");' class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" />
            <input name="externalNoteCleae"  id="externalNoteClear"  type="button" value="<fmt:message key="label.clear"/>" onClick='clearNotes("externalNoteDivAreaUSE","specialInstructions");' class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" />
          </td>
        </tr> 
      </table>  
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
</div>

<div id="noteAreabilltoNoteDiv" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
<table id="searchMaskTable" width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
   	<TEXTAREA name="customerNoteUSE" id="customerNoteUSE" readOnly class="inputBox" COLS=70 ROWS=12><tcmis:inputTextReplace value="${salesQuoteViewCollection.customerNote}"/></TEXTAREA>
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
</div>

<div id="noteAreashiptoNoteDiv" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
<table id="searchMaskTable" width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
   	<TEXTAREA name="shiptoNoteAreaUSE" id="shiptoNoteAreaUSE" readOnly class="inputBox" COLS=70 ROWS=12><tcmis:inputTextReplace value="${salesQuoteViewCollection.shiptoNote}"/></TEXTAREA>
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
</div>

<div id="noteAreaorderShiptoNoteDiv" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
<table id="searchMaskTable" width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
  	  <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
        <tr>
          <td class="optionTitleBoldLeft">
            <TEXTAREA name="orderShiptoNoteDivAreaUSE" id="orderShiptoNoteDivAreaUSE" readOnly onKeyDown='limitText("orderShiptoNoteDivAreaUSE", "<fmt:message key="label.orderinternal"/>", 1000);' onKeyUp='limitText("orderShiptoNoteDivAreaUSE", "<fmt:message key="label.orderinternal"/>", 1000);' class="inputBox" COLS=70 ROWS=10><tcmis:inputTextReplace value="${salesQuoteViewCollection.orderShiptoNote}"/></TEXTAREA></td>
        </tr> 
        <tr>
          <td align="center">
            <input name="orderShiptoNoteOk"  id="orderShiptoNoteOk"  type="button" value="<fmt:message key="label.ok(done)"/>" onClick='okClose("orderShiptoNote","orderShiptoNoteDivAreaUSE","<fmt:message key="label.shipto"/>");' class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" />
            <input name="orderShiptoNoteCleae"  id="orderShiptoNoteClear"  type="button" value="<fmt:message key="label.clear"/>" onClick='clearNotes("orderShiptoNoteDivAreaUSE","orderShiptoNote");' class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" />
          </td>
        </tr> 
      </table>  
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table></div>


<div id="noteAreaholdCommentsDiv" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
<table id="searchMaskTable" width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
  	  <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
        <tr>
          <td class="optionTitleBoldLeft">
            <TEXTAREA name="holdCommentsDivAreaUSE" id="holdCommentsDivAreaUSE" readOnly onKeyDown='limitText("holdCommentsDivAreaUSE", "<fmt:message key="label.holdcomments"/>", 2000);' onKeyUp='limitText("holdCommentsDivAreaUSE", "<fmt:message key="label.holdcomments"/>", 2000);' class="inputBox" COLS=70 ROWS=10><tcmis:inputTextReplace value="${salesQuoteViewCollection.holdComments}"/></TEXTAREA></td>
        </tr> 
        <tr>
          <td align="center">
            <input name="orderShiptoNoteOk"  id="orderShiptoNoteOk"  type="button" value="<fmt:message key="label.ok(done)"/>" onClick='okClose("holdComments","holdCommentsDivAreaUSE","<fmt:message key="label.holdcomments"/>");' class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" />
            <input name="orderShiptoNoteCleae"  id="orderShiptoNoteClear"  type="button" value="<fmt:message key="label.clear"/>" onClick='clearNotes("holdCommentsDivAreaUSE","holdComments");' class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" />
          </td>
        </tr> 
      </table>  
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table></div>



<div id="noteAreacancelMRLineReasonDiv" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
<table id="searchMaskTable" width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
  	  <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
        <tr>
          <td class="optionTitleBoldLeft">
          	<TEXTAREA name="cancelMRLineReasonDivAreaUSE" id="cancelMRLineReasonDivAreaUSE" readOnly onKeyDown='limitText("cancelMRLineReasonDivAreaUSE", "<fmt:message key="label.cancelmrlinereason"/>", 1000);' onKeyUp='limitText("cancelMRLineReasonDivAreaUSE", "<fmt:message key="label.cancelmrlinereason"/>", 1000);' class="inputBox" COLS=70 ROWS=10></TEXTAREA></td>
        </tr> 
        <tr>
          <td align="center">
            <input name="cancelMRLineReasonOk"  id="cancelMRLineReasonOk"  type="button" value="<fmt:message key="label.ok"/>" onClick="cancelMRLineReasonOkClose();" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" />
            <input name="cancelMRLineReasonClear"  id="cancelMRLineReasonClear"  type="button" value="<fmt:message key="label.clear"/>" onClick="cancelMRLineReasonClear();" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" />
          </td>
        </tr> 
      </table>  
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
</div>

<div id="transitDailogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
</div>

<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

	 <%-- freeze --%>
<div id="transitDailogWinBody" class="errorMessages" style="display: none;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" id="transitLabel">
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</td>
		</tr>
	</table>
</div>

<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
    <tr><td width="100px" class="lightgray legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.requestcancel"/></td></tr>
    <tr><td width="100px" class="black legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.cancelled"/></td></tr>
    <tr><td width="100px" class="yellow legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.draft"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors &&  empty tcmISError}">
     showErrorMessage = false;
    </c:when>
    <c:otherwise>
     showErrorMessage = true;
    </c:otherwise>
   </c:choose>   
    //-->       
</script>

	<!-- Error Messages Ends -->
</tcmis:form>
<div id="hiddenOverCreditLimitDiv" class="roundcont filterContainer" style="display:none;">
 <table id="searchMaskTable" width="330" height="100%" border="0" cellpadding="1" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
     <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
       <tr>
         <td width="330" class="optionTitleBoldLeft"><fmt:message key="label.beyonagreedtradingterms"/></td>
       </tr> 
     </table>  
    <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="7" class="tableSearch">
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.outstandingar"/>:</td>
        <td width="33%" class="optionTitleBoldRight">
          %arOutstanding%</td>
<%--         <td width="34%"><fmt:message key="label.gbp"/></td> --%>
      </tr>
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.openorders"/>:</td>
        <td width="33%" class="optionTitleBoldRight">
          %openOrders%</td>
      </tr>
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.creditlimit"/>:</td>
        <td width="33%" class="optionTitleBoldRight">
          %creditLimit%</td>
      </tr>
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.availablecredit"/>:</td>
        <td width="33%" class="optionTitleBoldRight" nowrap>
          %availableCredit%</td>
      </tr>
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.graceperiod"/>:</td>
        <td width="33%" class="optionTitleBoldLeft">
          %gracePeriod% <fmt:message key="label.days"/></td>
      </tr>
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.greatestoverduedays"/>:</td>
        <td width="33%" class=optionTitleBoldLeft>
         %greatestOverdueDays%</td>
      </tr>
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.amtpastdue0-30"/>:</td>
        <td width="33%" class="optionTitleBoldRight">
          %pastDue%</td>
        <td width="4%">&nbsp;</td>
      </tr>
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.amtpastdue31-60"/>:</td>
        <td width="33%" class="optionTitleBoldRight">
          %pastDue30%</td>
      </tr>
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.amtpastdue61-90"/>:</td>
        <td width="33%" class="optionTitleBoldRight">
          %pastDue60%</td>
      </tr>
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.amtpastdue>90"/>:</td>
        <td width="33%" class="optionTitleBoldRight">
          %pastDue90%</td>
      </tr>
      <tr>
      	<td colspan="2">
      		<input name="review" id="review" type="submit" value="<fmt:message key="label.review"/>" class="inputBtns" 
				onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" 
				onclick="submitReview();">
		</td>
	  </tr>
    </table>
   <!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
</div>

<div id="qualityControlItemMessageArea" class="roundcont filterContainer" style="display:none;">
<table id="searchMaskTable" width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
   <!-- Insert all the search option within this div -->
  	  <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
        <tr>
          <td id="qualityControlItemMessage" class="optionTitleBold" align="center"/>
        </tr>
        <tr>
          <td id="qualityControlItemMessage" class="optionTitleBold" align="center">
                        		<input name="itarOK" id="itarOK" type="submit" value="<fmt:message key="label.ok"/>" class="inputBtns" 
				onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" 
				onclick="dhxQualityControlItemWinHandle.close();"/>
		  </td>
        </tr> 

      </table>  
   <!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
</div>

<div id="hiddenIframdConterdiv" style="display:none">
<iframe id="hiddenIFrametest" name="hiddenIFrametest">
</iframe>
</div>
</body>
</html:html>