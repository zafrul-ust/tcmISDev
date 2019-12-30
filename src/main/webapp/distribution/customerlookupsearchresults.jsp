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
<%--<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>--%>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/distribution/customerlookupsearchresults.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>

<title>
<fmt:message key="customersearch.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--

with(milonic=new menuname("opendetails")){
    top="offset=2"
    style = contextStyle;
    margin=3
       aI("text=<fmt:message key="label.showdetails"/>;url=javascript:showDetails();");
       aI("text=<fmt:message key="creditreview.title"/>;url=javascript:showCreditReview();");
}

drawMenus();


//add all the javascript messages here, this for internationalization of client side javascript messages
// Added all column names to the messagesData array.
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
selectedcustomer:"<fmt:message key="label.selectedcustomer"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
customerdetails:"<fmt:message key="customerdetails.title"/>",
type:"<fmt:message key="label.type"/>"};

var gridConfig = {
		divName:'customerAddressSearchViewBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,//true,			 // this page has rowSpan > 1 or not. Set this to -1 to disable rowSpan and smart rendering, but the sorting will still work
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click
	    onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:selectRightclick,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//	    onBeforeSorting:doOnBeforeSelect, // the onBeforeSorting event function to be attached, as in beanGrid.attachEvent("onBeforeSorting",selectRow));
	    noSmartRender:false// set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
	};

function resultOnLoad()
{
 if($v("popup") == 'Y') {  //alert("here");
	windowCloseOnEsc = true; }
 else
  	windowCloseOnEsc = false;  	

 resultOnLoadWithGrid(gridConfig);
 mygrid = haasGrid;
 
 if (parent.valueElementId.length>0 && parent.displayElementId.length>0 )
 {
	 mygrid.attachEvent("onRightClick",simplyRightClick);
 }
 else
 {	 
 mygrid.attachEvent("onRightClick",selectRightclick);
 }
}

var config = [
{  columnId:"customerId",
   columnName:'<fmt:message key="label.customerid"/>',
   width:6
},
{  columnId:"customerName",
   columnName:'<fmt:message key="label.customer"/>',//&nbsp;<input class="inputBox" id="customerName_" type="text" value="" size=9 onKeyUP="filterBy(\'customerName\');"/>',
//   filterBox:true, use this setting to enable a fileter box at column header
   width:20
},
{  columnId:"customerOrigin",
   columnName:'<fmt:message key="label.customerorigin"/>',
   width:6
},
{
   columnId:"billToAddress",
   columnName:'<fmt:message key="label.billtoaddress"/>',
   width:32
},
{ columnId:"creditStatus",
  columnName:'<fmt:message key="label.creditstatus"/>'
},
{
   columnId:"notes",
   columnName:'<fmt:message key="label.notes"/>',
   width:32
},
{  columnId:"synonym",
   columnName:'<fmt:message key="label.synonym"/>'
		  
},
{  columnId:"opsEntityId"
},
{  columnId:"abcClassification"
}
];

// -->
</script>
</head>
<body bgcolor="#ffffff" onload="resultOnLoad();"> 
<tcmis:form action="/customerlookupsearchresults.do" onsubmit="return submitFrameOnlyOnce();">

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
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
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
<div class="backGroundContent">

<%--NEW - there is no results table anymore--%>  
<div id="customerAddressSearchViewBean" style="width:100%;%;height:400px;" style="display: none;"></div>

<c:if test="${CustomerAddressSearchViewCollection != null}" >
<script type="text/javascript">
<!--

<%--NEW - storing the data to be displayed in the grid in a JSON. notice the ID, this will be the id of the cell in the grid.--%>
<%--TODO - Right click to show links for receipt labels, print BOL, transactions history.--%>
<c:set var="dataCount" value='${0}'/>
<c:set var="preCustomerId" value=''/>

<bean:size id="listSize" name="CustomerAddressSearchViewCollection"/>
<c:if test="${!empty CustomerAddressSearchViewCollection}" >

var lineMap = new Array();
//  map will change per 'PO'

<c:forEach var="customerBean" items="${CustomerAddressSearchViewCollection}" varStatus="status">
 <c:set var="currentCustomerId" value='${status.current.customerId}'/>
 <c:if test="${ currentCustomerId != preCustomerId }">
  lineMap[${status.index}] = ${rowCountPart[currentCustomerId]};
 </c:if>
 <c:set var="preCustomerId" value='${status.current.customerId}'/>
</c:forEach>

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="customerBean" items="${CustomerAddressSearchViewCollection}" varStatus="status">

  <tcmis:jsReplace var="customerName" value='${customerBean.customerName}' processMultiLines="false" />
   
  <tcmis:jsReplace var="billToAddress" value='${customerBean.billToAddress}' processMultiLines="true" />  


  <tcmis:jsReplace var="notes" value='${customerBean.notes}' processMultiLines="true"/>

{ id:${status.index +1},
	<c:if test="${customerBean.creditStatus eq 'Stop'}">'class':"grid_black",</c:if>
 data:[
  '${customerBean.customerId}',
  '${customerName}',
  '${customerBean.customerOrigin}',
  '${billToAddress}','${customerBean.creditStatus}',
  '${notes}',
  '${customerBean.legacyCustomerId}','${customerBean.opsEntityId}', '${customerBean.abcClassification}']}
<c:if test="${status.index+1 < listSize}">,</c:if>  
<c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
// -->
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty CustomerAddressSearchViewCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden">

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<%-- Larry Note: currently not used. --%>
<input type="hidden" name="selectedCustomerId" id="selectedCustomerId" value="${param.selectedCustomerId}"/>
<input type="hidden" name="currentCustomerId" id="currentCustomerId" value="${param.currentCustomerId}"/>
<input type="hidden" name="customerId" id="customerId" value="${param.currentCustomerId}"/>
<input type="hidden" name="customerName" id="customerName" value="${param.customerName}"/>
<input type="hidden" name="billToAddress" id="billToAddress" value="${param.billToAddress}"/>
<input type="hidden" name="shipToAddress" id="shipToAddress" value="${param.shipToAddress}"/>
<input type="hidden" name="popup" id="popup" value="${param.popup}"/>

<input name="minHeight" id="minHeight" type="hidden" value="100"/>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>