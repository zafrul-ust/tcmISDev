<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

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
    <!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS -->
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
<!--<script type="text/javascript" src="/js/common/cabinet/msdssusedatlocationsresults.js"></script> -->

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
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
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
    <fmt:message key="clientCabinetManagement"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
            validvalues:"<fmt:message key="label.validvalues"/>",
            submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
            recordFound:"<fmt:message key="label.recordFound"/>",
            searchDuration:"<fmt:message key="label.searchDuration"/>",
            minutes:"<fmt:message key="label.minutes"/>",
            seconds:"<fmt:message key="label.seconds"/>",
            itemInteger:"<fmt:message key="error.item.integer"/>",
            ok:"<fmt:message key="label.ok"/>",                       
            dateofrevision:"<fmt:message key="label.dateofrevision"/>",
            expediteage:"<fmt:message key="label.expediteage"/>",
            comments:"<fmt:message key="label.comments"/>",
            maximum2000:"<fmt:message key="label.maximum2000"/>",
	        waitingforinput:"<fmt:message key="label.waitingforinputfrom"/>"
			};

var countType = new  Array(
		{text:'<fmt:message key="label.notcounted"/>',value:'Not Counted'},
		{text:'<fmt:message key="label.nonmanaged"/>',value:'Non Managed'}, 
	    {text:'<fmt:message key="label.counted"/>',value:'Counted'}
	);
				             
var config = [
  {
  	columnId:"permission"
  },
  {
	    columnId:"facilityName",
	    columnName:'<fmt:message key="label.facility"/>'
  },
  {
	    columnId:"areaName",
	    columnName:'<fmt:message key="label.area"/>',
	    width:5
		    
  },
  {
	    columnId:"buildingName",
	    columnName:'<fmt:message key="label.building"/>',
	    width:5
  },
  {
	    columnId:"applicationDesc",
	    columnName:'<fmt:message key="label.workarea"/>',
	    tooltip:"Y",  
	    align:'left',
	  	width:15
  },
  {
	    columnId:"deptName",
	    columnName:'<fmt:message key="label.department"/>',
	    width:5
  },
  {
	  	columnId:"countType",
	  	columnName:'<fmt:message key="label.counttype"/>',
	  	type:'hcoro',
	  	width:8
  },
  {
	    columnId:"activationDate",
	    columnName:'<fmt:message key="label.activationdate"/>', 
	    hiddenSortingColumn:'hiddenActivationDateTime', 
	    sorting:'int' 
  },
  {
	    columnId:"inactivationDate",
	    columnName:'<fmt:message key="label.inactivationdate"/>',
	    hiddenSortingColumn:'hiddenInactivationDateTime', 
	    sorting:'int'
  },
  {
	  	columnId:"catalogDesc",
	  	columnName:'<fmt:message key="label.catalog"/>'
  },
  {
	  	columnId:"catPartNo",
	  	columnName:'<fmt:message key="label.partnumber"/>'
  },
  {
	  	columnId:"itemId",
	  	columnName:'<fmt:message key="label.item"/>',
	  	sorting:'int',
	    width:5
  },
  {
	    columnId:"containerSize",
	    columnName:'<fmt:message key="label.containersize"/>',
	    width:5
  },
  {
	    columnId:"averageRp",
	    columnName:'<fmt:message key="label.averagerp"/>'
  },
  {
	    columnId:"maxSl",
	    columnName:'<fmt:message key="label.maxsl"/>',
	    width:5
  },
  {
	  	columnId:"msdsString",
	  	columnName:'<fmt:message key="label.msdsnumber"/>'
  },
  {
	  	columnId:"materialDesc",
	  	columnName:'<fmt:message key="label.materialdescription"/>',
	  	tooltip:"Y",  
	    width:15
  },
  {
	  	columnId:"avgAmount",
	  	columnName:'<fmt:message key="label.averageamount"/>',
	  	width:6
  },
  {
	  	columnId:"maxAmount",
	  	columnName:'<fmt:message key="label.maxamount"/>',
	  	width:6
  },
  {
	  	columnId:"unit",
	  	columnName:'<fmt:message key="label.unit"/>',
	  	width:5
  },
  {columnId:"hiddenActivationDateTime", sorting:'int' },
  {columnId:"hiddenInactivationDateTime", sorting:'int' }

];

<%-- Define the grid options--%>
var gridConfig = {
	divName: 'mSDSsUsedAtLocationsBean',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
	beanData: 'jsonMainData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
	beanGrid: 'mygrid',		<%--  variable to put the grid object in for later use --%>
	config: config,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
	singleClickEdit: true,		<%--  Make TXT type field open edit on one click rahter than double click --%>
	submitDefault: true,
	rowSpan: false	 		<%--  true: this page has rowSpan > 1 or not, disables smartrendering & sorting --%>
};

drawMenus();

// -->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">
<tcmis:form action="/msdssusedatlocationsresults.do" onsubmit="return submitFrameOnlyOnce();">



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

<div id="mSDSsUsedAtLocationsBean" style="width:100%;height:400px;" style="display: none;"></div>

<script type="text/javascript">
<!--

<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty searchResults}" >  

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${searchResults}" varStatus="status1">
<c:if test="${status1.index > 0}">,</c:if>
<fmt:formatNumber var="reorderPoint" maxFractionDigits="2" minFractionDigits="2">${bean.reorderPoint}</fmt:formatNumber>
<fmt:formatNumber var="avgAmount" maxFractionDigits="2" minFractionDigits="2">${bean.avgAmount}</fmt:formatNumber>
/*The row ID needs to start with 1 per their design.*/
{ id:${status1.index +1},
  data:['N',
        '<tcmis:jsReplace value="${bean.facilityName}" processAmpersand="true" />',
        '<tcmis:jsReplace value="${bean.areaName}" processAmpersand="true"/>',
        '<tcmis:jsReplace value="${bean.buildingName}" processAmpersand="true"/>',
        '<tcmis:jsReplace value="${bean.applicationDesc}" processMultiLines="true"/>',
        '<tcmis:jsReplace value="${bean.deptName}" processAmpersand="true"/>',
        '${bean.countType}',
        '<fmt:formatDate value="${bean.activationDate}" pattern="${dateFormatPattern}"/>',
        '<fmt:formatDate value="${bean.inactivationDate}" pattern="${dateFormatPattern}"/>',
        '<tcmis:jsReplace value="${bean.catalogDesc}" processAmpersand="true" />',
        '<tcmis:jsReplace value="${bean.catPartNo}" processAmpersand="true" />',
        '${bean.itemId}',
        '<tcmis:jsReplace value="${bean.containerSize}" processMultiLines="true"/>',
     	'${reorderPoint}',
        '${bean.stockingLevel}',
        '${bean.msdsId}',
        '<tcmis:jsReplace value="${bean.materialDesc}" processAmpersand="true" />',
   	 	'${avgAmount}',
        '${bean.maxAmount}',
        '${bean.sizeUnit}',
        '${bean.activationDate.time}',
        '${bean.inactivationDate.time}'
        
  ]}  
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>

//-->  
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty searchResults}">
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
	<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden">
    <input name="uAction" id="uAction" value="" type="hidden">
    <input name="minHeight" id="minHeight" type="hidden" value="100">
      
  <!-- Put all the Search Criteria here for re-search after update-->
  
	<input name="applicationId" id="applicationId" value="${param.applicationId}" type="hidden"/>
	<input name="branchPlant" id="branchPlant" value="<c:out value="${param.branchPlant}"/>" type="hidden">
	<input name="criterion" id="criterion" value="<c:out value="${param.criterion}"/>" type="hidden">
	<input name="criteria" id="criteria" value="<c:out value="${param.criteria}"/>" type="hidden">
	<input name="companyId" id="companyId" value="<c:out value="${param.companyId}"/>" type="hidden">
	<input name="facilityId" id="facilityId" value="<c:out value="${param.facilityId}"/>" type="hidden">
	<input name="facilityName" id="facilityName" value="<c:out value="${param.facilityName}"/>" type="hidden">
	<input name="cabinetId" id="cabinetId" value="" type="hidden">
	<input name="materialId" id="materialId" value="" type="hidden">
	<input name="msdsNumber" id="msdsNumber" value="" type="hidden">	
	<input name="sortBy" id="sortBy" value="<c:out value="${param.sortBy}"/>" type="hidden">
	<input name="reportingEntityId" id="reportingEntityId" value="<c:out value="${param.reportingEntityId}"/>" type="hidden">  
	<input name="workAreaSelectCount" id="workAreaSelectCount" type="hidden" value="${param.workAreaSelectCount}">
    <input name="deptId" id="deptId" value="${param.deptId}" type="hidden">
    <input name="areaId" id="areaId" value="${param.areaId}" type="hidden">
    <input name="buildingId" id="buildingId" value="${param.buildingId}" type="hidden">
    <input name="maxData" id="maxData" type="hidden" value="${param.maxData}">
    <input name="wtf" id="wtf" type="hidden" value="${fn:length(searchResults)}">
	  
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>

</body>
</html:html>