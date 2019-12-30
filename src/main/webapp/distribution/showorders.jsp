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
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hch -checkbox
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<c:if test="${!empty beanColl}">
	<c:set var="regionName" value="${beanColl[0].opsRegionName}"/>
    <c:set var="inventoryGroup" value="${beanColl[0].inventoryGroup}"/>
</c:if>
    
<title>
<c:if test="${source eq 'showallpreviousorders'}">
	<c:if test="${empty param.scratchPadLineItem}">
		${param.curpath} &gt;
	</c:if>
	<c:choose>
	   <c:when test="${param.searchKey eq 'INVENTORY GROUP'}"> 
	    	<fmt:message key="label.showmrhistoryig"/>: ${inventoryGroup}
	   </c:when>
	   <c:when test="${param.searchKey eq 'REGION'}"> 
	    	<fmt:message key="label.showmrhistoryregion"/>: ${regionName}
	   </c:when>
	   <c:when test="${param.searchKey eq 'GLOBAL'}"> 
	    	<fmt:message key="label.showmrhistoryglobal"/>- <fmt:message key="label.past1year"/>
	   </c:when>
	   <c:otherwise>
		    <fmt:message key="label.showallpreviousorders"/> - <fmt:message key="label.past1year"/>
    	   </c:otherwise>
    </c:choose>
</c:if>
<c:if test="${source ne 'showallpreviousorders'}">
            <c:if test="${empty param.scratchPadLineItem}">
    ${param.curpath} &gt;
	    	</c:if>
	<fmt:message key="label.showpreviousordersforcustomer"/> ${param.customerName} - <fmt:message key="label.past1year"/>
</c:if>

</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
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

windowCloseOnEsc = true;

with(milonic=new menuname("openMRMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
       aI("text=<fmt:message key="label.open"/>;url=javascript:openMRTab();");
      
}

drawMenus();

function openMRTab() {
	var prNumber = cellValue(ordersGrid.getSelectedRowId(),"hiddenPrNumber");
//    var type = cellValue(beangrid.getSelectedRowId(),"type");
    var tabId = 'scratchPad'+prNumber+'';
    var loc = "/tcmIS/distribution/scratchpadmain.do?uAction=searchScratchPadId&scratchPadId="+prNumber+"&tabId="+encodeURIComponent(tabId);
 	
 	try
	{
		opener.parent.openIFrame(tabId,loc,'MR '+prNumber+'','','N');
    }
	catch (ex){
		try
		{
			opener.parent.opener.parent.openIFrame(tabId,loc,'MR '+prNumber+'','','N');
	    }
		catch (ex)
		{
			openWinGeneric(loc,"scratchPad"+prNumber,"900","600","yes","80","80","yes");
		}
	}
}


// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="popupOnLoadWithGrid()" onresize="resizeFrames()">

<tcmis:form action="/${source}.do" onsubmit="return submitFrameOnlyOnce();">

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
<c:forEach var="errorMsg" items="${tcmISError}">
	<c:if test="${!empty errorMsg}">
		showErrorMessage = true;
	</c:if>
</c:forEach>

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

<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<div id="resultGridDiv" style="display: none;">
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      <span id="updateResultLink" style="display: none">
      <fmt:message key="label.catalogitem"/>: <span id="spanid1"></span><br/>
      <fmt:message key="label.description"/>: <span id="spanid2"></span>
	  </span>
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
<!--  result page section start -->
<c:if test="${!empty beanColl}" >

<div id="OrdersBean" style="height:400px;display: none;" ></div>
<script type="text/javascript">
<!--

function selectRow()
{
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

   oldRowId = rowId0;

   if( ee != null ) {
   		if( ee.button != null && ee.button == 2 ) rightClick = true;
   		else if( ee.which == 3  ) rightClick = true;
   }

   selectedRowId = rowId0;
   
} //end of method

function selectRightclick(rowId,cellInd){
	ordersGrid.selectRowById(rowId,null,false,false);
	selectRow(rowId,cellInd);
	toggleContextMenu("openMRMenu");

}

_gridConfig.submitDefault = true;
_gridConfig.divName = 'OrdersBean';
_gridConfig.rowSpan = false;
_gridConfig.beanGrid = 'ordersGrid';
_gridConfig.onRowSelect = selectRow;
_gridConfig.onRightClick = selectRightclick;


<%--NEW - storing the data to be displayed in the grid in a JSON.--%>
<c:set var="dataCount" value='${0}'/>

var jsonData = {
        rows:[
<c:forEach var="bean" items="${beanColl}" varStatus="status">
		<c:if test="${status.index > 0}">,</c:if>

		<c:if test="${status.index eq 0}">
        <c:set var="alternateName" value="${bean.alternateName}"/>
        <c:set var="partDescription" value="${bean.partDescription}"/>
		</c:if>

		<fmt:formatDate var="releaseDate" value="${bean.releaseDate}" pattern="${dateFormatPattern}"/>       
        <c:set var="remainingShelfLife" value="${bean.remainingShelfLifePercent}"/>
        <c:if test="${! empty remainingShelfLife}">
			<c:set var="remainingShelfLife" value="${remainingShelfLife}%"/>
	    </c:if>
        /*The row ID needs to start with 1 per their design.*/
{
 <tcmis:jsReplace var="customerPoNumber" value="${bean.customerPoNumber}"  processMultiLines="true"/>
 <tcmis:jsReplace var="customerName" value="${bean.customerName}"  processMultiLines="true"/>
<fmt:formatNumber var="unitPriceFinal" value="${bean.unitPrice}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>
 id:${status.index },
          data:[
 '${p}',
 <c:if test="${source eq 'showallpreviousorders'}">
	'${customerName}',
</c:if>
 '<tcmis:jsReplace value="${bean.customerPartNo}" />',
<c:if test="${param.searchKey eq 'GLOBAL'}"> 
	'<tcmis:jsReplace value="${bean.opsRegionName}" />',
</c:if>
<c:if test="${param.searchKey eq 'REGION' or param.searchKey eq 'GLOBAL'}"> 
	'<tcmis:jsReplace value="${bean.inventoryGroup}" />',
</c:if>
 '${bean.specList}',
 '${bean.prNumber}-${bean.lineItem}',
 '${customerPoNumber}',
 '${bean.currencyId} ${unitPriceFinal}',
 '${bean.quantity}',
 '${releaseDate}',
 '${remainingShelfLife}',
 '${bean.companyId}',
 '${bean.itemId}',
 '${bean.prNumber}',
 '${bean.unitPrice}'
 ]}
    <c:set var="dataCount" value='${dataCount+1}'/>
     </c:forEach>
    ]};

<c:if test="${!empty beanColl}">
	selectCurrency='${beanColl[0].currencyId}';
</c:if>

var config = [
  {
  	columnId:"permission",
  	columnName:''
  },
/*  { columnId:"catPartNo",
	  columnName:'<fmt:message key="label.partnumber"/>',
	  width:10
	},
{ columnId:"partDescription",
  columnName:'<fmt:message key="label.description"/>',
  width:20
},
*/
<c:if test="${source eq 'showallpreviousorders'}">  
{
	columnId:"customerName",
	columnName:'<fmt:message key="label.customer"/>',
    width:15,
    tooltip:true
 },  
</c:if>
 {
		columnId:"customerPartNo",
		columnName:'<fmt:message key="label.customerpartnumber"/>'
  },
<c:if test="${param.searchKey eq 'GLOBAL'}">
	{columnId:"region",
	columnName:'<fmt:message key="label.region"/>',
	tooltip:"Y",
	width:4},
</c:if>
<c:if test="${param.searchKey eq 'REGION' or param.searchKey eq 'GLOBAL'}"> 
	{columnId:"iinventoryGroup",
	columnName:'<fmt:message key="label.inventorygroup"/>',
	tooltip:"Y"},
</c:if>
  {
  	columnId:"specList",
  	columnName:'<fmt:message key="label.spec"/>',
  	width:10,
    tooltip:true
  },
  {
	  	columnId:"mrLine",
	  	columnName:'<fmt:message key="label.mrline"/>',
        width:6
},
{
  	columnId:"prNumber",
  	columnName:'<fmt:message key="label.customerpo"/>'
},
{
  	columnId:"displayUnitPrice",
  	columnName:'<fmt:message key="label.unitprice"/>',
  	align:"right",
  	hiddenSortingColumn:"expectedUnitCost",
  	sorting:"int"
},
{
  	columnId:"quantity",
  	columnName:'<fmt:message key="label.quantity"/>',
  	align:"right"
},
{
  	columnId:"releaseDate",
  	columnName:'<fmt:message key="label.date"/>'
},
  {
	  	columnId:"bean.remainingShelfLifePercent",
	  	columnName:'<fmt:message key="label.requiredshelflife"/> %',
	  	align:"right"
  },
  {
	  	columnId:"companyId"//,type:"ed"
  },
  {
	  	columnId:"itemId",
	  	type:"ed"
},
{
  	columnId:"hiddenPrNumber"
},
{ columnId:"expectedUnitCost",
  sorting:"int"
}  
];


var chargeRecurrence = new Array( 
//		{text:'<fmt:message key="label.pleaseselect"/>',value:''}
		<c:forEach var="vvBean" items="${addChargeRecurrenceDropDown}" varStatus="status"> 
		<c:if test="${status.index > 0}">,</c:if>
		<c:set var="rlabel"><fmt:message key="${vvBean.jspLabel}"/></c:set>
		{text:'${rlabel}',value:'${vvBean.chargeRecurrence}'}
		</c:forEach>  	
		);  

// -->
</script>
</c:if>

<!-- If the collection is empty say no data found -->
<c:if test="${empty beanColl}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td>
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

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="uAction" id="uAction" value="" type="hidden"/>
<input type="hidden" name="prNumber" id="prNumber" value="${param.prNumber}"/>
<input type="hidden" name="companyId" id="companyId" value="${param.companyId}"/>
<input type="hidden" name="lineItem" id="lineItem" value="${param.lineItem}"/> 
<input type="hidden" name="totalLineCharge" id="totalLineCharge" value="${param.totalLineCharge}"/>
<input type="hidden" name="source" id="source" value="${source}"/>  
<input type="hidden" name="alternateName" id="alternateName" value="${alternateName}"/>  
<input type="hidden" name="partDescription" id="partDescription" value="${partDescription}"/>  
<!--This sets the start time for time elapesed.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

<script type="text/javascript">
<!--
  showUpdateLinks = true;
$('spanid1').innerHTML = $('alternateName').value;
$('spanid2').innerHTML = $('partDescription').value;
//-->
</script>

</body>
</html>
