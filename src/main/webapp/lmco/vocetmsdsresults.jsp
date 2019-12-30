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
    <link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
    <%@ include file="/common/locale.jsp" %>
    
<c:set var="module">
	 <tcmis:module/>
</c:set>
    
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
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/lmco/vocetmsdsresults.js"></script>

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
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>


<title>
    <fmt:message key="label.vocetmsds"/>
</title>
<script language="JavaScript" type="text/javascript">
<!--

var rowSpanMap = new Array();
var rowSpanClassMap = new Array();
var rowSpanCols = [0,1,2,3,5,6,9,10];

with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    aI("text=YYY;url=javascript:doNothing();");
}

with(milonic=new menuname("emptyMenu")){
   top="offset=2";
   style=submenuStyle;
   itemheight=17;
   aI("text=;");
}


drawMenus();

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {
    and:"<fmt:message key="label.and"/>",
	validvalues:"<fmt:message key="label.validvalues"/>",
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
	recordFound:"<fmt:message key="label.recordFound"/>",
	searchDuration:"<fmt:message key="label.searchDuration"/>",
	minutes:"<fmt:message key="label.minutes"/>",
	seconds:"<fmt:message key="label.seconds"/>",
	itemInteger:"<fmt:message key="error.item.integer"/>",
	ok:"<fmt:message key="label.ok"/>"
};

var vocetStatus = parent.vocetStatusArr['${param.facilityId}'];

var vocetCategoryId = parent.vocetCoatCategoryArr['${param.facilityId}'];


var gridConfig = {
	divName:'vocetMsdsBean', // the div id to contain the grid.
	beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid', // the grid's name, as in beanGrid.attachEvent...
	config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:true, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
	submitDefault:true, // the fields in grid are defaulted to be submitted or not.
	noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
	variableHeight:false,
//	onRowSelect: selectRow,
//	onRightClick:selectRightclick,
//	onAfterHaasRenderRow:colorDeletedRow,
	selectChild: 1
};

var config = [
    {columnId:"permission"},
    {columnId:"customerMsdsOrMixtureNo"},
    {columnId:"customerMixtureNumber", columnName:'<fmt:message key="label.kitmsds"/>', width:10 },
    {columnId:"mixtureDesc", columnName:'<fmt:message key="report.label.kitDescription"/>', tooltips:'Y', width:20 },
    {columnId:"customerMsdsNumber", columnName:'<fmt:message key="label.msds"/>', width:10 },
    {columnId:"vocetStatus", columnName:'<fmt:message key="label.status"/>', type:'hcoro', onChange:setChanged},
    {columnId:"vocetCategoryId", columnName:'<fmt:message key="label.category"/>', type:'hcoro', onChange:setChanged},
    {columnId:"materialId", columnName:'<fmt:message key="label.materialid"/>', width:10 },
    {columnId:"tradeName", columnName:'<fmt:message key="label.tradename"/>', tooltips:'Y', width:32 },
    {columnId:"updatedByName", columnName:'<fmt:message key="label.lastUpdatedBy"/>'},
    {columnId:"updatedOn", columnName:'<fmt:message key="label.laastUpdatedDate"/>' },
    {columnId:"customerMsdsDb"},
    {columnId:"companyId"},
    {columnId:"changed"}
];	

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/vocetmsdsresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
 ${tcmISError}<br/>
<c:forEach var="tcmisError" items="${tcmISErrors}">
  ${tcmisError}<br/>
</c:forEach>
<c:if test="${param.maxData == fn:length(searchResults)}">
 <fmt:message key="label.maxdata">
  <fmt:param value="${param.maxData}"/>
 </fmt:message>
</c:if>
</div>

<script type="text/javascript">
   /*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
    <c:choose>
    <c:when test="${empty tcmISErrors and empty tcmISError}">
     <c:choose>
      <c:when test="${param.maxData == fn:length(searchResults)}">
        showErrorMessage = true;
        parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
      </c:when>
      <c:otherwise>
        showErrorMessage = false;
      </c:otherwise>
     </c:choose>
    </c:when>
    <c:otherwise>
      parent.messagesData.errors = "<fmt:message key="label.errors"/>";
      showErrorMessage = true;
      </c:otherwise>
    </c:choose>
    //-->
</script>

<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="vocetMsdsBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:set var="pagePermission" value="N" />
<tcmis:facilityPermission indicator="true" userGroupId="VocetProperties" facilityId="${param.facilityId}">
	<c:set var="pagePermission" value="Y" />
	<c:set var="showUpdateLink" value='Y'/>
</tcmis:facilityPermission> 
<c:if test="${empty searchResults}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>
<c:if test="${!empty searchResults}" >
<script language="JavaScript" type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>

  var jsonMainData = {
  rows:[
  <c:forEach var="row" items="${searchResults}" varStatus="status">
	    <c:set var="vocetStatus" value="${row.vocetStatus}" />
	    <c:if test="${pagePermission == 'N' && row.vocetStatus == null}" ><c:set var="vocetStatus" value="&nbsp;"/></c:if>
	    <c:set var="vocetCategoryId" value="${row.vocetCategoryId}" />
	    <c:if test="${pagePermission == 'N' && row.vocetCategoryId == null}" ><c:set var="vocetCategoryId" value="&nbsp;"/></c:if>
	    
		<c:if test="${status.index != 0 }">,</c:if>
	
	    { id:${status.index +1},
	        data:[
	        		'${pagePermission}',
	        		'${row.customerMsdsOrMixtureNo}',
                    '${row.customerMixtureNumber}',
                    '<tcmis:jsReplace value="${row.mixtureDesc}" />',
                    '${row.customerMsdsNumber}',
                    '${vocetStatus}',
					'${vocetCategoryId}',
                    '${row.materialId}',
					'<tcmis:jsReplace value="${row.tradeName}" />',
					'<tcmis:jsReplace value="${row.updatedByName}"/>',
	   				'<fmt:formatDate value="${row.updatedOn}" pattern="${dateFormatPattern}"/>',
					'${row.customerMsdsDb}',
					'${row.companyId}',
					''
	        ]}
	 <c:set var="dataCount" value='${dataCount+1}'/> 
  </c:forEach>
  ]};
  
 <%-- determining rowspan --%>
<c:set var="rowSpanCount" value='0' />
	<%-- determining rowspan --%>
	<c:forEach var="row" items="${searchResults}" varStatus="status">
		<c:set var="currentKey" value='${row.customerMsdsOrMixtureNo}' />
		<c:choose>
			<c:when test="${status.first}">
				<c:set var="rowSpanStart" value='0' />
				<c:set var="rowSpanCount" value='1' />
				rowSpanMap[0] = 1;
				rowSpanClassMap[0] = 1;
			</c:when>
			<c:when test="${currentKey == previousKey}">
				rowSpanMap[${rowSpanStart}]++;
				rowSpanMap[${status.index}] = 0;
				rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
			</c:when>
			<c:otherwise>
				<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
				<c:set var="rowSpanStart" value='${status.index}' />
				rowSpanMap[${status.index}] = 1;
				rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
			</c:otherwise>
		</c:choose>
		<c:set var="previousKey" value='${currentKey}' />
	</c:forEach> 
																							

// -->
</script>
</c:if>

 <!-- Search results end --> 

    <!-- Hidden element start --> 
    <div id="hiddenElements" style="display: none;">    			
		<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
		<input name="uAction" id="uAction" type="hidden" value="">
        <input name="facilityId" id="facilityId" type="hidden" value="${param.facilityId}">
    	<input name="searchField" id="searchField" type="hidden" value="${param.searchField}">
    	<input name="matchType" id="matchType" type="hidden" value="${param.matchType}">
    	<input name="searchText" id="searchText" type="hidden" value="${param.searchText}">
    	<input name="vocetStatus" id="vocetStatus" type="hidden" value="${param.vocetStatus}">
    	<input name="vocetCategoryId" id="vocetCategoryId" type="hidden" value="${param.vocetCategoryId}">
        <input name="updatedBy" id="updatedBy" type="hidden" value="${param.updatedBy}">
        <input name="maxData" id="maxData" type="hidden" value="${param.maxData}">
        <input name="minHeight" id="minHeight" type="hidden" value="100">
    </div>
    <!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->

<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>
</tcmis:form>

</body>
</html:html>