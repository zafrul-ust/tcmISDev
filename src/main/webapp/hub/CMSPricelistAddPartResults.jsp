<!DOCTYPE html>
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
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<%--This is to allow copy and paste. works only in IE--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_selection.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/js/hub/CMSPricelistAddPart.js"></script>

<title>
<fmt:message key="label.addpart"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		recordFound:"<fmt:message key="label.recordFound"/>",
		searchDuration:"<fmt:message key="label.searchDuration"/>",
		minutes:"<fmt:message key="label.minutes"/>",
		seconds:"<fmt:message key="label.seconds"/>",
		validvalues:"<fmt:message key="label.validvalues"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		itemInteger:"<fmt:message key="error.item.integer"/>",
		selectedPart:"<fmt:message key="label.selectedpart"/>",
		selectedRowMsg:"<fmt:message key="label.returnselecteddata"/>"
		};

var columnConfig = [
	{columnId:"catPartNo", columnName:'<fmt:message key="label.part"/>', width:10},
	{columnId:"itemId",columnName:'<fmt:message key="label.itemid"/>',width:5},
	{columnId:"itemDesc",columnName:'<fmt:message key="label.description"/>',width:25},
	{columnId:"catalogId"},
	{columnId:"partGroupNo"}
  ];
  
<%-- Define the grid options--%>
var gridConfig = {
	divName: 'beanData',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
	beanData: 'jsonMainData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
	beanGrid: 'mygrid',		<%--  variable to put the grid object in for later use --%>
	config: columnConfig,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
	singleClickEdit: true,		<%--  Make TXT type field open edit on one click rahter than double click --%>
	submitDefault: true,
    onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	rowSpan: false	 		<%--  true: this page has rowSpan > 1 or not, disables smartrendering & sorting --%>
};


// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">

<tcmis:form action="/CMSPricelist.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">
<div class="backGroundContent">
<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty searchResults}" >
<div id="beanData" style="width:100%;height:400px;"></div>
<!-- Search results start -->
<script type="text/javascript">
<!--
var jsonMainData = {
	rows:[<c:forEach var="bean" items="${searchResults}" varStatus="status"><c:set var="dataCount" value='${dataCount+1}'/>
		{ 	id:${status.count},
 			data:[
  			'<tcmis:jsReplace value="${bean.catPartNo}"/>',	  
  			'${bean.itemId}',
  			'<tcmis:jsReplace value="${bean.itemDesc}" processMultiLines="true"/>',
  			'<tcmis:jsReplace value="${bean.catalogId}" processMultiLines="true"/>',
  			'${bean.partGroupNo}'
  			]
		}<c:if test="${!status.last}">,</c:if></c:forEach>
  	]
};
//-->
</script>

</div> <!-- end of grid div. -->
</c:if>

<c:if test="${empty searchResults}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>

<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
</div>

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>

showUpdateLinks = true;
//-->
</script>

</body>
</html:html>