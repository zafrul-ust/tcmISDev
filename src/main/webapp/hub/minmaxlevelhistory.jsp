<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<tcmis:gridFontSizeCss />

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
    
<title>
<fmt:message key="minmaxlevel.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
		recordFound:"<fmt:message key="label.recordFound"/>",
		searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

windowCloseOnEsc = true;
window.onresize = resizeFrames;

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="popupOnLoadWithGrid()">
<tcmis:form action="/minmaxlevelhistory.do" onsubmit="return submitFrameOnlyOnce();">
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
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
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
	  </span>
       &nbsp;
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
<c:set var="dataCount" value='0'/>
<!--  result page section start -->
<c:set var="beanColl" value='${minMaxLevelLogViewBeanCollection}'/>
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

_gridConfig.submitDefault = true;
_gridConfig.divName = 'OrdersBean';
_gridConfig.rowSpan = false;
_gridConfig.beanGrid = 'ordersGrid';
_gridConfig.onRowSelect = selectRow;


<%--NEW - storing the data to be displayed in the grid in a JSON.--%>

var jsonData = {
        rows:[
<c:forEach var="bean" items="${beanColl}" varStatus="status">
		<c:if test="${status.index > 0}">,</c:if>
{
	<fmt:formatDate var="dateModified" value="${bean.dateModified}" pattern="${dateFormatPattern}"/>
    <tcmis:jsReplace var="notes" value="${bean.remarks}" processMultiLines="true" />
    <fmt:formatDate var="oldLevelHoldEndDate" value="${bean.oldLevelHoldEndDate}" pattern="${dateFormatPattern}"/>
    <fmt:formatDate var="newLevelHoldEndDate" value="${bean.newLevelHoldEndDate}" pattern="${dateFormatPattern}"/>
        

 id:${status.index+1 },
          data:[
 'Y',
 '${dateModified}',
 '${bean.modifiedByName}',
 '${bean.oldStocked}',
 '${bean.newStocked}',
 '${bean.oldOrderPoint}',
 '${bean.newOrderPoint}',
 '${bean.oldStockingLevel}',
 '${bean.newStockingLevel}',
 '${bean.oldReorderQuantity}',
 '${bean.newReorderQuantity}',
 '${bean.oldLookAhead}',
 '${bean.newLookAhead}',
 '${bean.oldReceiptProcessingMethod}',
 '${bean.newReceiptProcessingMethod}',
 '${bean.oldBillingMethod}',
 '${bean.newBillingMethod}',
 '${bean.oldDropshipOverride}',
 '${bean.newDropshipOverride}',
 '${oldLevelHoldEndDate}',
 '${newLevelHoldEndDate}',
 '${bean.oldProjectedLeadTime}',
 '${bean.newProjectedLeadTime}',
 '${notes}',
 '${bean.dateModified}'
 ]}
    <c:set var="dataCount" value='${dataCount+1}'/>
     </c:forEach>
    ]};

var config = [
{ columnId:"permission"
},
  {
  	columnId:"changedOn",
  	columnName:'<fmt:message key="label.changedon"/>',
    width:7,
    hiddenSortingColumn:"changedOnDate",
    sorting:"int"
  },
  {
  	columnId:"changedby",
  	columnName:'<fmt:message key="label.changedby"/>',
    tooltip:"Y"
  },
  {
  	columnId:"oldstockingmethod",
  	columnName:'<fmt:message key="label.oldstockingmethod"/>',
    width:5
  },
  {
  	columnId:"newstockingmethod",
  	columnName:'<fmt:message key="label.newstockingmethod"/>',
    width:5
  },
  {
  	columnId:"oldreorderpoint",
  	columnName:'<fmt:message key="label.oldreorderpoint"/>',
    align:"right",
    width:5
  },
  {
  	columnId:"newreorderpoint",
  	columnName:'<fmt:message key="label.newreorderpoint"/>',
    align:"right",
    width:5
  },
  {
  	columnId:"oldstockinglevel",
  	columnName:'<fmt:message key="label.oldstockinglevel"/>',
    align:"right",
    width:5
  },
  {
  	columnId:"newstockinglevel",
  	columnName:'<fmt:message key="label.newstockinglevel"/>',
    align:"right",
    width:5
  },
  {
  	columnId:"oldreorderquantity",
  	columnName:'<fmt:message key="label.oldreorderquantity"/>',
    align:"right",
    width:5
},
{
	columnId:"newreorderquantity",
	columnName:'<fmt:message key="label.newreorderquantity"/>',
    align:"right",
    width:5
},
{
  	columnId:"oldlookahead",
  	columnName:'<fmt:message key="label.oldlookahead"/>',
    align:"right",
    width:4
},
{
	columnId:"newlookahead",
	columnName:'<fmt:message key="label.newlookahead"/>',
    align:"right",
    width:4
},
{
  	columnId:"oldreceiptprocessingmethod",
  	columnName:'<fmt:message key="label.oldreceiptprocessingmethod"/>',
    width:7,
    tooltip:"Y"
},
{
	columnId:"newreceiptprocessingmethod",
	columnName:'<fmt:message key="label.newreceiptprocessingmethod"/>',
    width:7,
    tooltip:"Y"
},
{
  	columnId:"oldbillingmethod",
  	columnName:'<fmt:message key="label.oldbillingmethod"/>',
    width:7,
    tooltip:"Y"
},
{
	columnId:"newbillingmethod",
	columnName:'<fmt:message key="label.newbillingmethod"/>',
    width:7,
    tooltip:"Y"
},
{
  	columnId:"oldDropshipOverride",
  	columnName:'<fmt:message key="label.oldDropshipOverride"/>',
    width:5
    
},
{
	columnId:"newDropshipOverride",
	columnName:'<fmt:message key="label.newDropshipOverride"/>',
    width:5
    
},
{
    columnId:"oldLevelHoldEndDate",
    columnName:'<fmt:message key="label.oldlevelholdenddate"/>',
    align:"right",
    width:10
},
{
    columnId:"newLevelHoldEndDate",
    columnName:'<fmt:message key="label.newlevelholdenddate"/>',
    align:"right",
    width:10
},
{
    columnId:"oldProjectedLeadTime",
    columnName:'<fmt:message key="label.oldprojectedleadtimeindays"/>',
    align:"right",
    width:4
},
{
    columnId:"newProjectedLeadTime",
    columnName:'<fmt:message key="label.newprojectedleadtimeindays"/>',
    align:"right",
    width:4
},
{
  	columnId:"remarks",
  	columnName:'<fmt:message key="label.remarks"/>',
    width:40,
    tooltip:"Y"
},
{
  	columnId:"changedOnDate"
}
  ];



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
<c:if test="${!empty beanColl}" >
showUpdateLinks = true;
<c:set var="bean" value="${beanColl[0]}"/>
<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
$('updateResultLink').innerHTML = 	
	'<tcmis:jsReplace value="${inventorygroup}"/>: '  + '${bean.inventoryGroupName}; ' +
	'<fmt:message key="label.company"/>: '	       + '${bean.companyName}; ' +
	'<fmt:message key="label.catalog"/>: '         + '${bean.catalogDesc}; ' +
	'<br/><fmt:message key="label.partnumber"/>: ' + '${bean.catPartNo}; ' +
	'<fmt:message key="label.partgroupnumber"/>: ' + '${bean.partGroupNo}; ' +
	'<fmt:message key="label.specification"/>: '   + '${bean.specList} ';			
</c:if>
//-->
</script>

</body>
</html>
