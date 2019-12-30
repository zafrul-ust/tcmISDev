<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here --> <script
	type="text/javascript" src="/js/hub/receivingreportresults.js"></script>

<!-- These are for the Grid--> <script type="text/javascript"
	src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script> <script
	type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript"
	src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script> <%--This is for HTML form integration.--%>
<script type="text/javascript"
	src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script> <%--This is for smart rendering.--%>
<script type="text/javascript"
	src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script> <%--This is to suppoert loading by JSON.--%>
<script type="text/javascript"
	src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script> <%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%> <!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
--> <%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%> <script type="text/javascript"
	src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>
<script type="text/javascript"
	src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<title><fmt:message key="label.receivingreport" /></title>

<script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--

with(milonic=new menuname("rightClickMenu")){
    /*top="offset=2"
    style = contextStyle;
    margin=3
    	aI("text=<fmt:message key="label.open"/> <fmt:message key="label.haaspoline"/>;url=javascript:openHaasPO();");
	*/
	top="offset=2";
	style=submenuStyle;
	margin=3;
    aI("text=<fmt:message key="label.print"/>;url=javascript:print();");
}

drawMenus();
<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
<c:set var="iUom"><fmt:message key="label.itemuom"/></c:set>
<tcmis:jsReplace var="itemUom" value="${iUom}" processMultiLines="false" />
var config = [
     {
    	  columnId:"inventoryGroup",
    		  columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
    		  tooltip:"Y",
    		  width:12
     },
     {
    	columnId:"haasPoLineDisplay",
    	columnName:'<fmt:message key="label.haaspoline"/>',
    	width:10
    },
    {
        columnId:"poLineDisplay",
        columnName:'<fmt:message key="label.poline"/>',
        width:10 
    },
    {
        columnId:"customerPoLineDisplay",
        columnName:'<fmt:message key="label.customerpo"/>',
        width:10 
    },
    {
        columnId:"releaseNo",
        columnName:'<fmt:message key="label.releaseno"/>',
        width:10 
    },
    {
        columnId:"partNo",
        columnName:'<fmt:message key="label.part"/>',
        width:10 
    },
    {
        columnId:"partDescription",
        columnName:'<fmt:message key="label.partdescription"/>',
        width:20 
    },
    {
        columnId:"itemNo",
        columnName:'<fmt:message key="label.item"/>',
        width:10 
    },
    {
        columnId:"itemDescription",
        columnName:'<fmt:message key="label.itemdescription"/>',
        width:20 
    },
    {
        columnId:"poQuantity",
        columnName:'<fmt:message key="label.poquantity"/>',
	    sorting:'int',
        width:10 
    },
	{
    	<c:choose>
		<c:when test="${whichUnit == 'uos'}" >
	        columnId:"uos",
	        columnName:'<fmt:message key="label.uos"/>',
		</c:when>
		<c:otherwise>
	        columnId:"itemUom",
	        columnName:'${itemUom}',
		</c:otherwise>
		</c:choose>
	    width:10 
	},
    {
        columnId:"qtyReceived",
        columnName:'<fmt:message key="label.qtyreceived"/>',
	    sorting:'int',
        width:10 
    },
    {
        columnId:"dor",
        columnName:'<fmt:message key="label.dor"/>',
        hiddenSortingColumn:'hiddenDorDateTime',
        sorting:'int',
        width:10 
    },
    { 
    	  columnId:"hiddenDorDateTime",
    	  sorting:'int'
    }

  ];
// -->

</script>

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
itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">

<tcmis:form action="/receivingreportresults.do"
	onsubmit="return submitFrameOnlyOnce();">

	<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->

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
	<div class="backGroundContent">
	<div id="receivingReportViewBean" style="width: 100%; height: 400px;"
		style="display: none;"></div>

	<script type="text/javascript">
<!-- Search results start -->

<c:set var="showUpdateLink" value='N'/>
<c:set var="dataCount" value='${0}'/>


 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty receivingReportCollection}" >
	var jsonMainData = new Array();
	var jsonMainData = {
	rows:[
    	<c:forEach var="receivingReportViewBean" items="${receivingReportCollection}" varStatus="status">
		<c:if test="${status.index > 0}">,</c:if>
		
			<fmt:formatDate var="fmtDor" value="${status.current.dateOfReceipt}" pattern="${dateFormatPattern}"/>
			<tcmis:jsReplace var="itemDesc" value="${status.current.itemDesc}" processMultiLines="true" />
			<tcmis:jsReplace var="partDesc" value="${status.current.partDescription}" processMultiLines="true" />
			/* Get time for hidden date column. This is for sorting purpose. */
			<c:set var="dorDateTime" value="${status.current.dateOfReceipt.time}"/>
			<c:set var="readonly" value='N'/>
			<tcmis:inventoryGroupPermission indicator="true" userGroupId="BuyOrder" inventoryGroup="${status.current.inventoryGroup}">
 				<c:set var="readonly" value='Y'/>
				<c:set var="showUpdateLink" value='Y'/>
			</tcmis:inventoryGroupPermission>

			{id:${status.index +1},
 			data:[
  			'${status.current.inventoryGroupName}',
  			'${status.current.radianPo}',
  			'${status.current.poLine}',
  			'${status.current.customerPoNo}', 
  			'${status.current.releaseNo}',
  			'${status.current.catPartNo}',
  			'${partDesc}',
  			'${status.current.itemId}',
			'${itemDesc}',
			'${status.current.poLineQuantity}', 
			'${status.current.uom}',
			'${status.current.quantityReceived}',
			'${fmtDor}',
			'${dorDateTime}'
    		]} 
		
  			<c:set var="dataCount" value='${dataCount+1}'/>
   		</c:forEach> 
  ]};   
</c:if>
</script> <!-- If the collection is empty say no data found --> <c:if
		test="${empty receivingReportCollection}">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="tableNoData" id="resultsPageTable">
			<tr>
				<td width="100%"><fmt:message key="main.nodatafound" /></td>
			</tr>
		</table>
	</c:if> <!-- Search results end --> <!-- Hidden element start -->
	<div id="hiddenElements" style="display: none;"><input
		name="totalLines" id="totalLines"
		value="<c:out value="${dataCount}"/>" type="hidden"> <!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
	<tcmis:saveRequestParameter /></div>
	<!-- Hidden elements end --></div>
	<!-- close of backGroundContent --></div>
	<!-- close of interface -->

</tcmis:form>
</body>
</html:html>