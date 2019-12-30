<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/distribution/partsearch.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
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
</title>

<script type="text/javascript">
    <!--
   	 showUpdateLinks = false;
    
    //-->
</script>

<script language="JavaScript" type="text/javascript">
<!--

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
selectedpartnumber:"<fmt:message key="label.addcatalogitem"/>", 
returnSelected:"<fmt:message key='label.return'/> <fmt:message key='label.partnumber'/>", 
newcontact:"<fmt:message key="label.newcontact"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

function priceChanged(rowId,columnId) {
	var val = cellValue(rowId,columnId);
	var expectedCost = cellValue(rowId,"expectedCost");
	var orderQty = cellValue(rowId,"orderQty");
	if( val && expectedCost && expectedCost != 0) {
//	   		var total = quantity*catalogPrice;
//	   		cell(selectedRowId,"extPrice").setValue(total.toFixed(2));
//	   		if (expectedUnitCost.length != 0 || expectedUnitCost != '') 
			{
	   			var margin = (val-expectedCost)/expectedCost*100;
	   			cell(rowId,"margin").setValue(margin.toFixed(0)+"%");
	   		}
	   }
}

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
var config = [
{ columnId:"permission"
},
{
  	columnId:"okDoUpdate"
  	<c:if test="${param.fromCustomerReturn ne 'Yes' }"> 
  	,
  	columnName:'<fmt:message key="label.ok"/><br><input type="checkbox" value="" onClick="return checkAll();" name="chkAllOkDoUpdate" id="chkAllOkDoUpdate">',
  	type:'hchstatus',
//  	sorting:"haasHch",
    align:'center',
    onChange:updateChanged,
    width:3
    </c:if>
  },
{ columnId:"labelSpec",
  columnName:'<fmt:message key="label.catalogitem"/>',
  sorting:"haasStr",
  tooltip:true,
  width:7
},
{ columnId:"description",
  columnName:'<fmt:message key="label.description"/>',
  sorting:"haasStr",
  tooltip:true,
  width:35
},
{
    columnId:"customerPart",
    columnName:'<fmt:message key="label.customerpart"/>',
    tooltip:true,
    width:6
},
{ columnId:"hazardous",
  columnName:'<fmt:message key="label.haz"/>',
  sorting:"haasStr",
  align:"center",
  width:3
},
{
  	columnId:"specList",
  	columnName:'<fmt:message key="label.specification"/>',
  	attachHeader:'<fmt:message key="label.value"/>',
  	tooltip:true,
  	width:10
}
,
{
  	columnId:"specLookup",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.lookup"/>',
  	width:6
},
{ columnId:"orderQty"  
},
{ columnId:"catalogPrice"
},
{ columnId:"currencyId",
	  columnName:'<fmt:message key="label.currencyid"/>',
	  sorting:"int",
	  width:5,
     align:"center"
},
{ columnId:"expectedCostDisplay",
  columnName:'<fmt:message key="label.expectedcost"/>',
  sorting:"int",
  width:5
},
{ columnId:"margin"
},
{ columnId:"warehouse",
  columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
  sorting:"int",
  width:5
},
{ columnId:"region",
  columnName:'<fmt:message key="label.region"/>',
  sorting:"int",
  width:5
},
{ columnId:"global",
  columnName:'<fmt:message key="label.global"/>',
  sorting:"int",
  width:5
},
{ columnId:"numberOfOrders",
  columnName:'<fmt:message key="label.numberofquotes"/> /<br># <fmt:message key="label.orders"/>',
  sorting:"int",
  width:6,
  align:"center",
  hiddenSortingColumn:"hnumberOfOrders"
},
{ columnId:"hnumberOfOrders"
},
{ columnId:"lastOrdered",
  columnName:'<fmt:message key="label.lastquoted"/> / <fmt:message key="label.ordered"/>',
  sorting:"int",
  width:8,
  align:"center",
  hiddenSortingColumn:"hlastOrdered"
},
{ columnId:"hlastOrdered"
},
{ columnId:"lastPrice",
  columnName:'<fmt:message key="label.lastpricequoted"/> / <fmt:message key="label.ordered"/>',
  sorting:"int",
  width:8,
  align:"center",    
  hiddenSortingColumn:"hlastPrice"
},
{ columnId:"hlastPrice"
},
{ columnId:"itemId"
},
{ columnId:"inventoryGroup"
},
{
  columnId:"specListConcat"
},
{
 columnId:"specVersionConcat"
},
{
 columnId:"specAmendmentConcat"
},
{
 columnId:"specDetailConcat"
},
{
 columnId:"specLibraryDescConcat"
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
 columnId:"specNameConcat"
},
{
 columnId:"itemType"
},
{
 columnId:"unitOfMeasure"
},
{
 columnId:"unitsPerItem"
},
{ columnId:"expectedCost"
}
];

/*
function setHlink() {
	 if( typeof( jsonMainData ) == 'undefined' ) return; 
	 	 
	  var rows =jsonMainData.rows;
	  for( var i = 0; i < rows.length; i++ ) 
	 {			
		if(rows[i].data[0]=='Y')
	   {	
	  	rows[i].data[9] ="<input class='inputBox' type='text' id='sapvendorcode" + (i+1) +  "' value='' size='10' readonly/>&nbsp;&nbsp;<input type='button' class='lookupBtn'  onmouseover=\"this.className='lookupBtn lookupBtnOver'\"   onmouseout=\"this.className='lookupBtn'\" name=\"sapvendorSelector"+(i+1)+"\" value=\"...\" align=\"right\" onClick=\"getVendorCode($('sapvendorcode"+(i+1)+"'),"+(i+1)+");\"/>";	   
	   }	
	 }	 	 	 
}
*/

function addPopUrl() {}

with(milonic=new menuname("partSearchMenu")){
         top="offset=2"
         style = contextWideStyle;
         margin=3
         aI("text=<fmt:message key="label.showallocatableig"/> (F6);url=javascript:allocationPopup('IG');");
         aI("text=<fmt:message key="label.showallocatableregion"/>;url=javascript:allocationPopup('REGION');");
         aI("text=<fmt:message key="label.showallocatableglobal"/>;url=javascript:allocationPopup('GLOBAL');");
         aI("text=<fmt:message key="label.showpreviousordersforcustomer"/>;url=javascript:showPreHistory();"); 
         aI("text=<fmt:message key="label.showallpreviousorders"/>;url=javascript:showAllPreHistory();"); 
         aI("text=<fmt:message key="showpohistory.title"/>;url=javascript:showPoHistory();"); 
         aI("text=<fmt:message key="showquotationallhistoryforcustomer.title"/>;url=javascript:showQuotationHistory();"); 
         aI("text=<fmt:message key="showquotationallhistory.title"/>;url=javascript:showAllQuotationHistory();"); 
         aI("text=<fmt:message key="label.showpricelistinfo"/>;url=javascript:showPriceList();");
         aI("text=<fmt:message key="label.editsourcinginfo"/>;url=javascript:showSourcingInfo();");
         aI("text=<fmt:message key="quickQuote"/> (F10);url=javascript:showQuickView();");
         //aI("text=<fmt:message key="label.showiteminfo"/>;url=javascript:addPopUrl();"); 
}

with(milonic=new menuname("partSearchMenuWithNoAllocation")){
         top="offset=2"
         style = contextWideStyle;
         margin=3
         aI("text=<fmt:message key="label.showpreviousordersforcustomer"/>;url=javascript:showPreHistory();"); 
         aI("text=<fmt:message key="label.showallpreviousorders"/>;url=javascript:showAllPreHistory();"); 
         aI("text=<fmt:message key="showpohistory.title"/>;url=javascript:showPoHistory();"); 
         aI("text=<fmt:message key="showquotationallhistoryforcustomer.title"/>;url=javascript:showQuotationHistory();"); 
         aI("text=<fmt:message key="showquotationallhistory.title"/>;url=javascript:showAllQuotationHistory();"); 
         aI("text=<fmt:message key="label.showpricelistinfo"/>;url=javascript:showPriceList();");
         aI("text=<fmt:message key="label.editsourcinginfo"/>;url=javascript:showSourcingInfo();");
         aI("text=<fmt:message key="quickQuote"/> (F10);url=javascript:showQuickView();");
         //aI("text=<fmt:message key="label.showiteminfo"/>;url=javascript:addPopUrl();"); 
}

drawMenus();

function pp(name) {
	var value = parent.opener.$v(name);
//	alert( value );
	return encodeURIComponent(value);
}

function gg(name) {
	var value = null;
	value = cellValue(selectedRowId,name);
//	alert( value );
	return encodeURIComponent(value);
}

function getcurpath (){
	return encodeURIComponent($v('curpath'));
}

function showSourcingInfo() {
//	  loc = "/tcmIS/distribution/showsupplierpl.do?itemId=" + gg('itemId');
	  var loc = "/tcmIS/distribution/editpricelist.do?uAction=search&searchField=itemId|number&searchMode=is&searchArgument=" + gg('itemId');
	  loc = loc + "&inventoryGroup=" +gg('inventoryGroup');
	  loc = loc + "&itemId="+gg('itemId');
	  loc = loc + "&showExpired=Y";// +inventoryGroup +
      loc = loc + "&opsEntityId="+pp('opsEntityId');
      loc = loc + "&hub="+pp('hub');
     parent.children[parent.children.length] =
	 openWinGeneric(loc, "showSourcingInfo"+pp('prNumber').replace(/[.]/,"_"), "1024", "600", "yes", "50", "50");
     //openWinGenericDefault(loc, "showSourcingInfo"+pp('prNumber').replace(/[.]/,"_"), "yes", "50", "50");
}

function allocationPopup(searchKey) {
	parent.showTransitWin();
	  var bcShipComplete = pp('shipComplete');
	  var shipComplete= 'N';
	  var consolidateShipment = 'N';
	  	  if( bcShipComplete == 'ORDER' ) {
		  	  	shipComplete = 'Y';
		  	  	consolidateShipment = 'Y';
	  	  }
	  	  else if( bcShipComplete == 'LINE' ) {
		  	  	shipComplete = 'Y';
	  	  }
	  	  
	  var specIdList = gg('specListConcat').replace(/%2C%20/g,'|');
	  var specification = gg('specList');
	  
/*  The right click from Scratch Pad does not do this. That's why I comment it out.    
      if (specIdList == 'Std%20Mfg%20Cert')
			specIdList = '';
*/		

	  var loc = 
		  "/tcmIS/distribution/allocation.do"+
		  "?companyId=" +pp('companyId')+
		  "&facilityId="+pp('facilityId')+
		  "&itemId="+ gg('itemId')+
//		  "&refInventoryGroup="+pp('inventoryGroup')+
		  "&orderType="+pp('orderType')+
		  "&inventoryGroup="+gg('inventoryGroup')+
		  "&specList="+ specIdList + 
		  "&specification="+ specification+
		  "&curpath="+getcurpath()+
		  "&orderPrNumber="+pp('prNumber')+
		  "&shipToCompanyId="+pp('shipToCompanyId')+
		  "&shipToLocationId="+pp('shipToLocationId')+
		  "&billToCompanyId="+pp('billToCompanyId')+
		  "&billToLocationId="+pp('billToLocationId')+
		  "&opsEntityId="+pp('opsEntityId')+
		  "&opsCompanyId="+pp('opsCompanyId')+
		  "&priceGroupId="+pp('priceGroupId')+
		  "&incoTerms="+pp('incoTerms')+
		  "&searchKey="+searchKey+
		  "&remainingShelfLifePercent="+pp('shelfLifeRequired')+
		  "&shipComplete="+ shipComplete +
		  "&consolidateShipment="+consolidateShipment+
		  "&specDetailList="+ gg('specDetailConcat')+
		  "&specLibraryList="+ gg('specLibraryConcat')+
		  "&specCocList="+ gg('specCocConcat')+
		  "&specCoaList="+ gg('specCoaConcat')+
        "&itemType="+ gg('itemType')+
        "&unitOfMeasure="+ gg('unitOfMeasure')+
        "&labelSpec="+ gg('labelSpec')+
		  "&partDesc="+gg('description')+
		  "&currencyId="+gg('currencyId');

		  parent.children[parent.children.length] = 
	 	  openWinGeneric(loc, "AllocationDetail_"+pp('prNumber').replace(/[.]/,"_"), "1024", "600", "yes", "50", "50");
		//openWinGenericDefault(loc, "AllocationDetail_"+pp('prNumber').replace(/[.]/,"_"), "yes", "50", "50");
}

function showPoHistory() {
	if (gg('specList') == 'Std%20Mfg%20Cert')
		var specList = '';
	else
		var specList = gg('specList');
	
	var loc = "/tcmIS/distribution/showpohistory.do"+
		"?region=" +
		"&specList="+  // specList+
		"&curpath="+getcurpath()+
        "&inventoryGroup="+ gg('inventoryGroup') +
        "&itemId="+ gg('itemId');
	parent.children[parent.children.length] = 
	openWinGeneric(loc, "showPoHistory_"+pp('prNumber').replace(/[.]/,"_"), "1024", "500", "yes", "50", "50");
	//openWinGenericDefault(loc, "showPoHistory_"+pp('prNumber').replace(/[.]/,"_"), "yes", "50", "50");
}

function showPreHistory() {
	if (gg('specList') == 'Std%20Mfg%20Cert')
		var specList = '';
	else
		var specList = gg('specList');
		
	var loc = "/tcmIS/distribution/showpreviousorders.do"+
	    "?companyId=" +pp('companyId')+
		"&region=" +
		"&specList=" +specList+
		"&itemId="+ gg('itemId') +
		"&curpath="+getcurpath()+
		"&inventoryGroup="+ gg('inventoryGroup') + 
		"&customerName="+ pp('customerName') +
		"&customerId="+ pp('customerId');
	parent.children[parent.children.length] = 	 
	openWinGeneric(loc, "showPreHistory_"+pp('prNumber').replace(/[.]/,"_"), "1024", "500", "yes", "50", "50");
	//openWinGenericDefault(loc, "showPreHistory_"+pp('prNumber').replace(/[.]/,"_"), "yes", "50", "50");
}

function showAllPreHistory() {
	if (gg('specList') == 'Std%20Mfg%20Cert')
		var specList = '';
	else
		var specList = gg('specList');

	var loc = "/tcmIS/distribution/showallpreviousorders.do"+
	    //"?companyId=" +pp('companyId')+
		"?region=" +
		"&specList=" +specList+
		"&itemId="+ gg('itemId') +
		"&curpath="+getcurpath()+
		"&inventoryGroup="+ gg('inventoryGroup');
	parent.children[parent.children.length] = 
	openWinGeneric(loc, "showAllPreHistory_"+pp('prNumber').replace(/[.]/,"_"), "1024", "500", "yes", "50", "50");
	//openWinGenericDefault(loc, "showAllPreHistory_"+pp('prNumber').replace(/[.]/,"_"), "yes", "50", "50");
}

function showQuotationHistory() {
	if (gg('specList') == 'Std%20Mfg%20Cert')
		var specList = '';
	else
		var specList = gg('specList');
		
	var loc = "/tcmIS/distribution/quotationhistory.do"+
	"?region=" +
	"&specList=" +specList+
	"&companyId=" +pp('companyId')+
	"&itemId="+ gg('itemId') +
	"&curpath="+getcurpath()+
	"&inventoryGroup="+ gg('inventoryGroup') +
	"&customerName="+ pp('customerName') +
	"&customerId="+ pp('customerId');
	parent.children[parent.children.length] = 
	openWinGeneric(loc, "showQuotationHistory_"+pp('prNumber').replace(/[.]/,"_"), "1024", "500", "yes", "50", "50");
	//openWinGenericDefault(loc, "showQuotationHistory_"+pp('prNumber').replace(/[.]/,"_"), "yes", "50", "50");
}

function showAllQuotationHistory() {
	if (gg('specList') == 'Std%20Mfg%20Cert')
		var specList = '';
	else
		var specList = gg('specList');
	
	var loc = "/tcmIS/distribution/quotationhistory.do"+
	"?region=" +//gg('region')+
	"&companyId=" +//pp('companyId')+
	"&specList=" +specList+
	"&curpath="+getcurpath()+
	"&inventoryGroup="+ gg('inventoryGroup') +
	"&itemId="+ gg('itemId');
	parent.children[parent.children.length] = 
	openWinGeneric(loc, "showAllQuotationHistory_"+pp('prNumber').replace(/[.]/,"_"), "1024", "500", "yes", "50", "50");
	//openWinGenericDefault(loc, "showAllQuotationHistory_"+pp('prNumber').replace(/[.]/,"_"), "yes", "50", "50");
}

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">
<tcmis:form action="/partsearchresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors }">
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

<div id="partViewBean" style="width:100%;%;height:400px;" style="display: none;"></div>
<c:set var="dataCount" value='${0}'/>	

<c:if test="${!empty partSearchCollection }" >
<script type="text/javascript">
<!--

var jsonMainData = {
rows:[
<c:forEach var="partSearchBean" items="${partSearchCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<%--  <c:set var="transactionsPermission" value="Y"/>
  <tcmis:permission indicator="true" userGroupId="transactions">
  	<c:set var="transactionsPermission" value="Y"/> 
  </tcmis:permission>  --%>
  <c:set var="specLookup">
    	<input type="button" class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'" id="fLookupButton" value="" onclick="getSpecList(${status.index+1})"/><input type="button" class="smallBtns" onmouseover="this.className=\'smallBtns smallBtnsOver\'" onMouseout="this.className=\'smallBtns\'" name="None" value="<fmt:message key="label.clear"/>" OnClick="clearSpecList(${status.index+1})"/> 
  </c:set>

  <c:set var="partDescription">
   <tcmis:replace value="${partSearchBean.partDescription}" from="\n" to="<br>"/>
  </c:set>
  <tcmis:jsReplace var="partDescription" value="${partDescription}" processMultiLines="true" />
  <fmt:formatDate var="fmtLastQuoted" value="${partSearchBean.lastQuoted}" pattern="${dateShortFormatPattern}"/>
  <fmt:formatDate var="fmtLastOrdered" value="${partSearchBean.lastOrdered}" pattern="${dateShortFormatPattern}"/>
  <fmt:formatNumber var="lastQuotedPrice" value="${partSearchBean.lastQuotedPrice}"  pattern="${totalcurrencyformat}"/>
  <fmt:formatNumber var="lastPrice" value="${partSearchBean.lastPrice}" pattern="${totalcurrencyformat}"/>
  <fmt:formatNumber var="expectedCost" value="${partSearchBean.expectedCost}" pattern="${totalcurrencyformat}"/>
<c:if test="${empty partSearchBean.lastQuoted }" >
 <c:set var="fmtLastQuoted" value='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'/>
</c:if>
<c:if test="${empty partSearchBean.lastOrdered }" >
 <c:set var="fmtLastOrdered" value='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'/>
</c:if>

<c:if test="${empty lastQuotedPrice }" >
 <c:set var="lastQuotedPrice" value='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'/>
</c:if>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
<c:if test="${empty lastPrice }" >
 <c:set var="lastPrice" value='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'/>
</c:if>

<c:choose>
    <c:when test="${partSearchBean.specId != 'No Specification'}">
     <c:set var="specList" value='Std Mfg Cert'/>
     <c:set var="coc" value='Y'/>
    </c:when>
    <c:otherwise>
     <c:set var="specList" value='No Specification'/>
     <c:set var="coc" value='N'/>
    </c:otherwise>
</c:choose>   

/*The row ID needs to start with 1 per their design. Use sinlge quotes for column data seperators.*/
{ id:${status.index +1},
 data:['Y','N',
  '<tcmis:jsReplace value="${partSearchBean.alternateName}" />','${partDescription}','<tcmis:jsReplace value="${partSearchBean.specificCustPartNo}" />',
  '${partSearchBean.hazardous}','${specList}','${specLookup}',
  '','${partSearchBean.catalogPrice}','${partSearchBean.currencyId}','${expectedCost}',
  '','${partSearchBean.warehouseAvailable}','${partSearchBean.regionAvailable}','${partSearchBean.globalAvailable}',
  '${partSearchBean.quoteCount} / ${partSearchBean.orderCount}',
  '${partSearchBean.orderCount}','${fmtLastQuoted} / ${fmtLastOrdered}','${partSearchBean.lastOrdered.time}',
  '${lastQuotedPrice} / ${lastPrice}','${partSearchBean.lastPrice}', '${partSearchBean.itemId}','${param.selectedInventoryGroup}',
  '${specList}','','','','','global','${coc}','N','','${partSearchBean.itemType}','${partSearchBean.unitOfMeasure}','${partSearchBean.unitsPerItem}','${partSearchBean.expectedCost}']}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};

// -->
</script>   
</c:if>   


<!-- If the collection is empty say no data found -->
<c:if test="${empty partSearchCollection}">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
        <tr>
            <td width="100%">
                <fmt:message key="main.nodatafound"/>
            </td>
        </tr>
    </table>
</c:if>
<!-- Search results end -->

<!-- Hidden element start --> 
<div id="hiddenElements" style="display: none;">    			
	<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
	<input name="customerId" id="customerId" type="hidden" value="${param.customerId}"/>
	<input name="displayElementId" id="displayElementId" type="hidden" value="${param.displayElementId}"/>
	<input name="displayArea" id="displayArea" type="hidden" value="${param.displayArea}"/>
	<input name="valueElementId" id="valueElementId" type="hidden" value="${param.valueElementId}"/>
	<input name="minHeight" id="minHeight" type="hidden" value="100"/> 
	<input name="curpath" id="curpath" type="hidden" value="${param.curpath}"/>
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>
</body>
</html:html>