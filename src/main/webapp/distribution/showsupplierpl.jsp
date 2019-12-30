<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
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
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srndRowSpan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
    
<title>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
	messagesData={alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	errors:"<fmt:message key="label.errors"/>",
	recordFound:"<fmt:message key="label.recordFound"/>",
	searchDuration:"<fmt:message key="label.searchDuration"/>",
	minutes:"<fmt:message key="label.minutes"/>",
	seconds:"<fmt:message key="label.seconds"/>",
	pleaseselect:"<fmt:message key="label.pleaseselect"/>",  
	totallinecharges:"<fmt:message key="label.totallinecharges"/>", 
	alreadyselected:"<fmt:message key="label.alreadyselected"/>",
	validvalues:"<fmt:message key="label.validvalues"/>",
	price:'<fmt:message key="label.price"/>',
	description:'<fmt:message key="label.feedescription"/>'
};

<c:set var="module">
	<tcmis:module/>
</c:set>

windowCloseOnEsc = true;
var lineMap3 = new Array();

function priceChanged(rowid,columnid) {
	if( cellValue(rowid,"isParent") != "Y" ) 
		cell(rowid,"changed").setValue("Y");
	else if( cellValue(rowid,"level2Changed") != "New" ) 
		cell(rowid,"level2Changed").setValue("Y");
}

// parent level.
function parentChanged(rowid,columnid) {
	if( cellValue(rowid,"isParent") != "Y" ) return ; 
	if( cellValue(rowid,"level2Changed") != "New" ) 
		cell(rowid,"level2Changed").setValue("Y");
}

function grandChanged(rowid,columnid) {
//	alert( rowid+":"+columnid+":"+ cellValue(rowid,"isGrand") );
	if( cellValue(rowid,"isGrand") != "Y" ) return ; 
	if( cellValue(rowid,"level1Changed") != "New" ) 
		cell(rowid,"level1Changed").setValue("Y");
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="if(window['gridConfig']){popupOnLoadWithGrid(gridConfig);}"  onresize="resizeFrames()">

<tcmis:form action="/showpricelist.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
    showErrorMessage = false;
<c:if test="${!empty tcmISError}">
	showErrorMessage = true;
</c:if>
//-->
</script>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display:">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
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
          Since this page is read-only I don't need to hide the mainUpdateLinks div, I am showing the link Close to all.
&gt;
<a href="#" onclick="alert('show add line url??');return false;"><fmt:message key="label.addline"/></a>	
<a href="#" onclick="alert('show all previous orders url??');return false;"><fmt:message key="label.showpreviousordersforcustomer"/></a>
<a href="#" onclick="alert('show previous orders url??');return false;"><fmt:message key="label.showallpreviousorders"/> --NEED CUSTOMER NAME--</a>
      --%>      
      &nbsp;
      <div id="headerline"><span id="mainUpdateLinks" style="display:"></span> 
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
<!--  result page section start -->

<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty beanCollection}" >
<div id="SupperPriceListBean" style="height:400px;"></div>
<!-- Search results start -->
<script type="text/javascript">
<!--
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
<c:set var="showUpdateLink" value='N'/>
/*Storing the data to be displayed in a JSON object array.*/

var rowSpanCols = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21];
var rowSpanLvl2Cols = [22,23];



var gridConfig = {
		divName:'SupperPriceListBean', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not.
		noSmartRender:false,
		submitDefault:true    // the fields in grid are defaulted to be submitted or not.
	};

showUpdateLinks = true;

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
<c:set var="leadtimeindays"><fmt:message key="label.leadtimeindays"/></c:set>
var config = [
              {
             	columnId:"permission"
             },
             {
             	columnId:"inventoryGroup"
             },
             { columnId:"itemId",
           	  columnName:'<fmt:message key="label.catalogitem"/>',
           	  sorting:"haasStr",
           	  tooltip:true,
           	  width:6
           	},
           	{	columnId:"itemDesc",
           		columnName:'<fmt:message key="label.description"/>',
           		sorting:"haasStr",
           		tooltip:true,
           		width:20
           	},
           	{	columnId:"shipToLocationId",
           		columnName:'<fmt:message key="label.shipto"/>'
           	},
           	  {
           	  	columnId:"inventoryGroupName",
           		columnName:'<tcmis:jsReplace value="${inventorygroup}"/>'
           	  },
           	{	columnId:"priority",
           		columnName:'<fmt:message key="label.priority"/>',
//           		type:'hcoro',
           		width:4,
           		onChange:grandChanged
           	},
           	{	columnId:"supplier"
           	},
           	{	columnId:"supplierName",
           		columnName:'<fmt:message key="label.supplier"/>'
           	},
           	{	columnId:"supplierPartNo",
           		columnName:'<fmt:message key="label.supplierpartnum"/>',
           		type:'hed',
           		onChange:grandChanged
           	},
           	{	columnId:"leadTimeDays",
           		columnName:'<tcmis:jsReplace value="${leadtimeindays}"/>',
           		type:'hed',
           		width:4,
           		size:2,
           		onChange:grandChanged
           	},
           	{	columnId:"carrierName",
           		columnName:'<fmt:message key="label.carrier"/>',
//           	  	attachHeader:'<fmt:message key="label.name"/>',
           	  	tooltip:true,
           	  	width:6
           	},
           	{
           	  	columnId:"carrierLookup"
//           	  	,
//           	  	columnName:'#cspan',
//           	  	attachHeader:'<fmt:message key="label.lookup"/>',
//           	  	width:6
           	},
           	{	columnId:"freightOnBoard",
           		columnName:'<fmt:message key="label.incoterms"/>',
//           		type:'hcoro',
           		width:15,
           		onChange:grandChanged
           	},
           	{	columnId:"consignment",
           		columnName:'<fmt:message key="label.consignment"/>',
           		type:'hchstatus',
           	  	width:5,
           		onChange:grandChanged
           	},
           	{	columnId:"multipleOf",
           		columnName:'<fmt:message key="label.multipleOf"/>',
           		type:'hed',
           	  	width:4,
           		onChange:grandChanged
           	},
           	{	columnId:"roundToMultiple",
           		columnName:'<fmt:message key="label.rounding"/>',	
//           		type:'hcoro',
           		width:7,
           		onChange:grandChanged
           	},
           	{	columnId:"status",
           		columnName:'<fmt:message key="label.status"/>',
//           		type:'hcoro',
           		width:7,
           		onChange:grandChanged
           	},
           	{
		  		columnId:"minBuyQuantity",
		  		columnName:'<fmt:message key="label.minimumbuyqty"/>',
		  		type:'hed',
			  	width:6
		  	},
			{
		  		columnId:"minBuyValue",
		  		columnName:'<fmt:message key="label.minimumordervalue"/>',
		  		type:'hed',
			  	width:6
		  	},
			{
		  		columnId:"updateByName",
		  		columnName:'<fmt:message key="label.lastUpdatedBy"/>'
		  	},
		  	
		  	{
		  		columnId:"updatedDate",
		  		columnName:'<fmt:message key="label.laastUpdatedDate"/>'
		  	},
           	{
             		columnId:"startDateDisplay",
             		columnName:'<fmt:message key="label.startDate"/>'
             	},
           	{
           		columnId:"currencyId",
             		columnName:'<fmt:message key="label.currencyid"/>',
//             		type:'hcoro',
             	  	onChange:parentChanged
           	},
             {
             	columnId:"breakQuantityPermission"
             },
             {
             	columnId:"breakQuantity",
             	columnName:'<fmt:message key="label.quantity"/>',
             	type:"hed",
             	permission:true,
             	onChange:priceChanged
             },
             {
             	columnId:"unitPrice",
             	columnName:'<fmt:message key="label.unitprice"/>',
             	type:"hed",
             	onChange:priceChanged
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
           	  {
           		  	columnId:"isParent"
           	  },
           	  {
           		  	columnId:"isGrand"
           	  },
           		{
           			columnId:"level1Changed"
           		},
           		{
           			columnId:"level2Changed"
           		},
           		{
           			columnId:"changed"
           		}
           	
           	
               ];


var map = null;
var lineMap = new Array();
var line2Map = new Array();
var lineIdMap = new Array();
var rowIdMap = new Array();
var partMap1 = new Array();
var partMap2 = new Array();

<c:set var="gridind" value="0"/>
<c:set var="prePar" value=""/>
<c:set var="samePartCount" value="0"/>
<c:set var="colorIndex" value="-1"/>
<c:forEach var="bean" items="${beanCollection}" varStatus="status">
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
<%--
		<c:if test="${status2.index==0}">
			line2Map[${gridind}] = ${resultSize};
		</c:if>
--%>
	<c:set var="gridind" value="${gridind+1}"/> 
		lineIdMap[""+${gridind}] = map;
		map[map.length] = ${gridind} ;
	    rowIdMap[rowIdMap.length] = ${gridind} ;
	</c:forEach>
//    alert( ${gridind} +":"+${firstLineIndex}+":"+lineMap[${firstLineIndex}]);
	<c:set var="prePar" value="${curPar}"/>
</c:forEach>

<c:set var="prePar" value=""/>

<c:set var="todayval">
	<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="yyyy-MM-dd"/>
</c:set>
		
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${beanCollection}" varStatus="status">
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
<%--
<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="editButton" value="<fmt:message key="receiving.label.duplicateline"/>" onclick="dupline(${dataCount})" />
--%>
<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="editButton" value="Add CPP" onclick="dupline(${dataCount})" />
</c:set>

<fmt:formatDate var="startDateVal" value="${bean.startDate}" pattern="yyyy-MM-dd"/>
<%--
<c:if test="${ startDateVal > todayval }">
	<c:set var="startDate">
		<input class="inputBox pointer" id="startDateDisplay${dataCount}" type="text" value="${startDate}" size="8" readonly onClick="return myGetCalendar(${dataCount})"/>
	</c:set>
</c:if>
--%>

<tcmis:jsReplace value="${dup}" var="dup"/>

<c:set var="carrierDisplay">
	<input class="lookupBtn" type="button" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" value="" onclick="getCarrier(${dataCount})"/>
	<input class="smallBtns" type="button" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" value="<fmt:message key="label.clear"/>" onclick="clearCarrier(${dataCount})"/> 
</c:set>
<tcmis:jsReplace var="carrierDisplay" value="${carrierDisplay}"  processMultiLines="true" />

<c:set var="consignment" value='false'/>
<c:if test="${ bean.consignment == 'Y' }">
	<c:set var="consignment" value='true'/>
</c:if>
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
	   '<tcmis:jsReplace value="${bean.supplier}"/>',
	   '<tcmis:jsReplace value="${bean.supplierName}"/>',
	   '<tcmis:jsReplace value="${bean.supplierPartNo}"/>',
	   '${bean.leadTimeDays}',
	   '<tcmis:jsReplace value="${bean.carrierName}"/>',
	   '${carrierDisplay}',
	   '${bean.freightOnBoard}',
	   ${consignment},
	   '${bean.multipleOf}',
	   '${bean.roundToMultiple}',
       <c:choose>
       <c:when test="${bean.status == 'DBUY' && !empty bean.status}">
        'Active',
       </c:when>
       <c:otherwise>
        'Inactive',
       </c:otherwise>
       </c:choose>
       '${bean.minBuyQuantity}',
	   '${bean.minBuyValue}',
	   '<tcmis:jsReplace value="${bean.updateByName}"/>',
	   '${updatedDate}',
	   '${startDate}',
	   '${bean.currencyId}',
	   'N',
	   '1',
	   '${bean.unitPrice}',
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
	   'Y',
	   '${isGrand}',
	   '',
	   '',
	   ''
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
	   '<tcmis:jsReplace value="${bean.supplier}" />',
	   '',
	   '<tcmis:jsReplace value="${bean.supplierPartNo}" />',
	   '${bean.leadTimeDays}',
	   '<tcmis:jsReplace value="${bean.carrierName}"/>',
	   '${carrierDisplay}',
	   '${bean.freightOnBoard}',
	   ${consignment},
	   '',
	   '',
	   '${bean.status}',
	   '${bean.minBuyQuantity}',
	   '${bean.minBuyValue}',
	   '<tcmis:jsReplace value="${bean.updateByName}"/>',
	   '${updatedDate}',
	   '${startDate}',
	   '${bean.currencyId}',
	   'N',
	   '${bean2.breakQuantity}',
	   '${bean2.unitPrice}',
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
	   'N',
	   'N',
	   '',
	   '',
	   ''
	   ]
	 }

  </c:forEach>

<c:set var="prePar" value="${curPar}"/>
</c:forEach>
]};

var rowSpanLvl2Map = line2Map;
//-->
</script>

<!-- end of grid div. -->
</c:if>

<!-- If the collection is empty say no data found -->
<c:if test="${empty beanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>
<!-- Search results end -->
</div>

 <%-- result count and time --%>
 <div id="footer" class="messageBar"></div>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>


</div><!-- Result Frame Ends -->

<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input type="hidden" name="uAction" id="uAction" value="update"/>
<input type="hidden" name="searchField" id="searchField" value="${param.searchField}"/>
<input type="hidden" name="searchMode" id="searchMode" value="${param.searchMode}"/>
<input type="hidden" name="searchArgument" id="searchArgument" value="${param.searchArgument}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
<input type="hidden" name="checkbox" id="checkbox" value="${param.checkbox}"/>
<input name='today' id='today' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'  />
<input type="hidden" name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}"/>
<input type="hidden" name="priceGroupId" id="priceGroupId" value="${param.priceGroupId}"/>
<input type="hidden" name="tmpdate" id="tmpdate" value=""/>
<input name="minHeight" id="minHeight" type="hidden" value="210"/>

</div>

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

<c:if test="${hasApprovalPermission == 'Y'}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
</script>
</c:if>

</body>
</html>
