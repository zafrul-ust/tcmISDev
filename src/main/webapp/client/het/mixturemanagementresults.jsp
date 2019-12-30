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
<script type="text/javascript" src="/js/client/het/mixturemanagementresults.js"></script>

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

<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script>
<script type="text/javascript" src="/js/jquery/livequery.js"></script> 

<script type="text/javascript" src="/js/jquery/dimensions.js"></script>
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />  


<title>
    <fmt:message key="mixtureManagement"/>
</title>
<script language="JavaScript" type="text/javascript">
<!--

var rowSpanMap = new Array();
var rowSpanClassMap = new Array();
var rowSpanCols = [0,1,2];

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
    alert:"<fmt:message key="label.alert"/>",
    validvalues:"<fmt:message key="label.validvalues"/>",
    and:"<fmt:message key="label.and"/>",
    recordFound:"<fmt:message key="label.recordFound"/>",
    searchDuration:"<fmt:message key="label.searchDuration"/>",
    minutes:"<fmt:message key="label.minutes"/>",
    seconds:"<fmt:message key="label.seconds"/>",
    pleaseinput:'<fmt:message key="label.pleaseinput"/>',
    addmoremsds:'<fmt:message key="label.addmoremsds"/>',
    msds:'<fmt:message key="label.msds"/>',
    mixturename:'<fmt:message key="label.mixturename"/>',
    msdsno:'<fmt:message key="label.msdsno"/>',
    deletemixture:'<fmt:message key="label.deletemixture"/>',
    addmsds:'<fmt:message key="searchmsds.label.addmsds"/>',
    deletemsds:'<fmt:message key="label.deletemsds"/>',
    verifyDelete:"<fmt:message key="label.verifydelete"><fmt:param><fmt:message key="label.part"/></fmt:param></fmt:message>",
    submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"
};

var gridConfig = {
	divName:'mixtureManagementBean', // the div id to contain the grid.
	beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid', // the grid's name, as in beanGrid.attachEvent...
	config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:true, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
	submitDefault:true, // the fields in grid are defaulted to be submitted or not.
	noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
	variableHeight:false,
	onRowSelect: selectRow,
	onRightClick:selectRightclick,
	onAfterHaasRenderRow:colorDeletedRow,
	selectChild: 1
};

var config = [
	{columnId:"permission"},
  	{columnId:"mixtureId", submit:true},
  	{columnId:"mixtureName", columnName:'<fmt:message key="label.mixturename"/>', onChange:markChanged, align:'center', width:25, type:'hed', size:25, maxlength:200 },
  	{columnId:"separable", columnName:'<fmt:message key="label.allowseperateusage"/>', onChange:markChanged, type:'hchstatus', align:'center', width:6, submit:true},
  	{columnId:"msdsNoPermission"},
  	{columnId:"msdsNo", columnName:'<fmt:message key="label.msdsno"/>', submit:true},
  	{columnId:"mfgDesc", columnName:'<fmt:message key="label.manufacturer"/>', width:20, tooltip: true, submit:false},
  	{columnId:"materialId"},
	{columnId:"materialDesc", columnName:'<fmt:message key="label.materialdesc"/>', width:25, tooltip: true, submit:false},
	{columnId:"companyId", submit:true},
	{columnId:"facilityId", submit:true},
	{columnId:"originalMixtureId", submit:true},
	{columnId:"status", submit:true}
];	

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/mixturemanagementresults.do" onsubmit="return submitFrameOnlyOnce();">
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

<div id="mixtureManagementBean" style="width:100%;height:400px;" style="display: none;"></div>

 <c:set var="pagePermission" value='N'/>
 <c:set var="pagePermission" value='Y'/>
 <c:set var="showUpdateLink" value='Y'/>


<script language="JavaScript" type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>

  var jsonMainData = {
  rows:[
  <c:forEach var="bean" items="${mixtureColl}" varStatus="status">
			{id:${status.count},
 			 data:[<c:choose><c:when test="${bean.readOnly}">'N'</c:when><c:otherwise>'Y'</c:otherwise></c:choose>,
  				'<tcmis:jsReplace value="${bean.mixtureId}"/>',
  				'<tcmis:jsReplace value="${bean.mixtureName}"/>',
				${bean.separable},
				'N',
				'${bean.msdsNo}',
				'<tcmis:jsReplace value="${bean.mfgDesc}"/>',
				'${bean.materialId}',
				'<tcmis:jsReplace value="${bean.materialDesc}"/>',
				'${bean.companyId}',
				'${bean.facilityId}',
				'${bean.materialId}',''
				]}<c:if test="${!status.last}">,</c:if>
  </c:forEach>
  ]};
  
 <%-- determining rowspan --%>
<c:set var="rowSpanCount" value='0' />
	<%-- determining rowspan --%>
	<c:forEach var="row" items="${mixtureColl}" varStatus="status">
		<c:set var="currentKey" value='${row.facilityId}|${row.mixtureId}' />
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

 <!-- Search results end --> 

    <!-- Hidden element start --> 
    <div id="hiddenElements" style="display: none;">    			
		<input name="totalLines" id="totalLines" value="<c:out value="${rowSpanCount+1}"/>" type="hidden">
        <input name="uAction" id="uAction" value="" type="hidden"> 
        <input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden">
        <input name="searchField" id="searchField" value="${param.searchField}" type="hidden">
        <input name="searchMode" id="searchMode" value="${param.searchMode}" type="hidden">
        <input name="searchArgument" id="searchArgument" value="${param.searchArgument}" type="hidden">
        <input name="companyId" id="companyId" type="hidden" value="${personnelBean.companyId}">
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