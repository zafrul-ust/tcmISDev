
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/het/mixturepermissionresults.js"></script>


<title>
    <fmt:message key="mixturePermissions"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData = {
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		validvalues:"<fmt:message key="label.validvalues"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		recordFound:"<fmt:message key="label.recordFound"/>",
		searchDuration:"<fmt:message key="label.searchDuration"/>",
		minutes:"<fmt:message key="label.minutes"/>",
		seconds:"<fmt:message key="label.seconds"/>",
		ok:"<fmt:message key="label.ok"/>",
		error:"<fmt:message key="error.db.update"/>",
		required:"<fmt:message key="label.pleaseadd"/>",
		addarea:"<fmt:message key="label.addarea"/>",
		addbuilding:"<fmt:message key="label.addbuilding"/>",
		addallbuildings:"<fmt:message key="label.addallbuildings"/>",
		removebuilding:"<fmt:message key="label.removebuilding"/>",
		addworkarea:"<fmt:message key="label.addworkarea"/>",
		addallworkareass:"<fmt:message key="label.addallworkareass"/>",
		removeworkarea:"<fmt:message key="label.removeworkarea"/>",
		removeallpermissions:"<fmt:message key="label.removeallpermissions"/>",
		deleteandcreatenewpermission:"<fmt:message key="label.deleteandcreatenewpermission"/>",
		workarea:"<fmt:message key="label.workarea"/>",
		fornext:"<fmt:message key="label.fornext"/>",
		all:"<fmt:message key="label.all"/>",
		none:"<fmt:message key="label.none"/>",
		exists: "<fmt:message key="errors.alreadyexists"/>",
		area:"<fmt:message key="label.area"/>",
		building:"<fmt:message key="label.building"/>",
		workArea:"<fmt:message key="label.workarea"/>",
		duplicateValue:"<fmt:message key="error.duplicatevalue"/>",
		verifyDelete:"<fmt:message key="label.verifydelete"><fmt:param><fmt:message key="label.mixture"/></fmt:param></fmt:message>"
	};    

with(milonic=new menuname("rightClickMenu")){
	top="offset=2"
	style = contextStyle;
	margin=3
	aI("text=<fmt:message key="label.addarea"/>;url=javascript:addArea();");
	//aI("text=<fmt:message key="label.deletesubstrate"/>;url=javascript:deleteSubstrate();");
}

with(milonic=new menuname("nothingMenu")){
	top="offset=2";
   style=submenuStyle;
   itemheight=17;
	aI("text=");
}

with(milonic=new menuname("rightClickMenuOneBuildingNoWorkArea")){
	top="offset=2"
	style = contextStyle;
	margin=3
	//aI("text=<fmt:message key="label.addarea"/>;url=javascript:addArea();");
	aI("text=<fmt:message key="label.addbuilding"/>;url=javascript:addBuilding();");
	aI("text=<fmt:message key="label.addallbuildings"/>;url=javascript:addAllBuildings();");
	aI("text=<fmt:message key="label.removeallpermissions"/>;url=javascript:deleteAllPermissions();");
}
with(milonic=new menuname("rightClickMenuOneBuildingOneWorkArea")){
	top="offset=2"
	style = contextStyle;
	margin=3
	//aI("text=<fmt:message key="label.addarea"/>;url=javascript:addArea();");
	aI("text=<fmt:message key="label.addbuilding"/>;url=javascript:addBuilding();");
	aI("text=<fmt:message key="label.addallbuildings"/>;url=javascript:addAllBuildings();");
	aI("text=<fmt:message key="label.addworkarea"/>;url=javascript:addWorkArea();");
	aI("text=<fmt:message key="label.addallworkareass"/>;url=javascript:addAllWorkAreas();");
	aI("text=<fmt:message key="label.removeallpermissions"/>;url=javascript:deleteAllPermissions();");
}
with(milonic=new menuname("rightClickMenuOneBuilding")){
	top="offset=2"
	style = contextStyle;
	margin=3
	//aI("text=<fmt:message key="label.addarea"/>;url=javascript:addArea();");
	aI("text=<fmt:message key="label.addbuilding"/>;url=javascript:addBuilding();");
	aI("text=<fmt:message key="label.addallbuildings"/>;url=javascript:addAllBuildings();");
	aI("text=<fmt:message key="label.addworkarea"/>;url=javascript:addWorkArea();");
	aI("text=<fmt:message key="label.addallworkareass"/>;url=javascript:addAllWorkAreas();");
    aI("text=<fmt:message key="label.removeworkarea"/>;url=javascript:removeWorkArea();");
	aI("text=<fmt:message key="label.removeallpermissions"/>;url=javascript:deleteAllPermissions();");
}
with(milonic=new menuname("rightClickMenuOneWorkArea")){
	top="offset=2"
	style = contextStyle;
	margin=3
	//aI("text=<fmt:message key="label.addarea"/>;url=javascript:addArea();");
	aI("text=<fmt:message key="label.addbuilding"/>;url=javascript:addBuilding();");
	aI("text=<fmt:message key="label.addallbuildings"/>;url=javascript:addAllBuildings();");
	aI("text=<fmt:message key="label.removebuilding"/>;url=javascript:removeBuilding();");
	aI("text=<fmt:message key="label.addworkarea"/>;url=javascript:addWorkArea();");
	aI("text=<fmt:message key="label.addallworkareass"/>;url=javascript:addAllWorkAreas();");
	aI("text=<fmt:message key="label.removeallpermissions"/>;url=javascript:deleteAllPermissions();");
}
with(milonic=new menuname("fullRightClickMenu")){
	top="offset=2"
	style = contextStyle;
	margin=3
	//aI("text=<fmt:message key="label.addarea"/>;url=javascript:addArea();");
	aI("text=<fmt:message key="label.addbuilding"/>;url=javascript:addBuilding();");
	aI("text=<fmt:message key="label.addallbuildings"/>;url=javascript:addAllBuildings();");
	aI("text=<fmt:message key="label.removebuilding"/>;url=javascript:removeBuilding();");
	aI("text=<fmt:message key="label.addworkarea"/>;url=javascript:addWorkArea();");
	aI("text=<fmt:message key="label.addallworkareass"/>;url=javascript:addAllWorkAreas();");
    aI("text=<fmt:message key="label.removeworkarea"/>;url=javascript:removeWorkArea();");
	aI("text=<fmt:message key="label.removeallpermissions"/>;url=javascript:deleteAllPermissions();");
}

drawMenus();

var rowSpanMap = new Array();
var rowSpanClassMap = new Array();
var rowSpanCols = [0,1,2];
var rowSpanLvl2Map = new Array();
var rowSpanLvl2Cols = [3];

var config = [
        {columnId:"permission"},
  		{columnId:"mixtureName", columnName:'<fmt:message key="label.mixturename"/>', width:21},
  		{columnId:"area", columnName:'<fmt:message key="label.area"/>', width:15, tooltip: true, submit: false},
  		{columnId:"building", columnName:'<fmt:message key="label.building"/>', width:15, tooltip: true, submit: false},
  		{columnId:"applicationDesc", columnName:'<fmt:message key="label.workarea"/>', width:15, tooltip: true, submit: false},
		{columnId:"mixtureId"},
		{columnId:"companyId"},
		{columnId:"facilityId"},
		{columnId:"areaId"},
		{columnId:"buildingId"},
		{columnId:"applicationId"},
		{columnId:"originalAreaId"},
		{columnId:"originalBuildingId"},
		{columnId:"originalApplicationId"},
		{columnId:"status"}
	];

var gridConfig = {
	       divName:'mixturePermission', // the div id to contain the grid. ALSO the var name for the submitted data
	       beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	       beanGrid:'mygrid', // the grid's name, for later reference/usage
	       config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
	       rowSpan: true, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
	       submitDefault: true, // the fields in grid are defaulted to be submitted or not.
	       noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
		   variableHeight:false,
	       onRowSelect: selectRow,
	       onRightClick: selectRightclick,
	       onAfterHaasRenderRow:colorDeletedRow,
	       selectChild: 2
 };
 
// -->
</script>

</head>

<body bgcolor="#ffffff" onload="myResultOnLoadWithGrid(gridConfig);parent.document.getElementById('mainUpdateLinks').style['display'] = ''">
<tcmis:form action="/mixturepermissionresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
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
		<div id="mixturePermission" style="width:100%;height:400px;" style="display: none;"></div>

<script type="text/javascript">
	<!--
	var jsonMainData = {
		rows:[<c:forEach var="bean" items="${substrates}" varStatus="status">
			{id:${status.count},
 			 data:['Y',
  				'<tcmis:jsReplace value="${bean.mixtureName}"/>',
  				<c:choose><c:when test="${empty bean.areaId}">'<fmt:message key="label.none"/>'</c:when><c:otherwise>'<tcmis:jsReplace value="${bean.areaName}"/>'</c:otherwise></c:choose>,
  				<c:choose><c:when test="${bean.buildingId == -1}">'<fmt:message key="label.all"/>'</c:when><c:when test="${empty bean.buildingId}">'<fmt:message key="label.none"/>'</c:when><c:otherwise>'<tcmis:jsReplace value="${bean.buildingName}"/>'</c:otherwise></c:choose>,
  				<c:choose><c:when test="${bean.applicationId == -1}">'<fmt:message key="label.all"/>'</c:when><c:when test="${empty bean.applicationId}">'<fmt:message key="label.none"/>'</c:when><c:otherwise>'<tcmis:jsReplace value="${bean.applicationDesc}"/>'</c:otherwise></c:choose>,
				'${bean.mixtureId}',
				'${bean.companyId}',
				'${bean.facilityId}',
				'${bean.areaId}',
				'${bean.buildingId}',
				'${bean.applicationId}',
				'${bean.areaId}',
				'${bean.buildingId}',
				'${bean.applicationId}',
				<c:choose><c:when test="${!empty bean.areaId}">''</c:when><c:otherwise>'new'</c:otherwise></c:choose>
				]}<c:if test="${!status.last}">,</c:if>
		      </c:forEach>]};
<c:set var="rowSpanCount" value='0' />
	<%-- determining rowspan --%>
	<c:forEach var="row" items="${substrates}" varStatus="status">
		<c:set var="currentKey" value='${row.mixtureId}-${row.areaId}' />
		<c:set var="currentKeyLvl2" value='${row.mixtureId}-${row.areaId}|${row.buildingId}' />
		<c:choose>
			<c:when test="${status.first}">
				<c:set var="rowSpanStart" value='0' />
				<c:set var="rowSpanLvl2Start" value='0' />
				<c:set var="rowSpanCount" value='1' />
				rowSpanMap[0] = 1;
				rowSpanLvl2Map[0] = 1;
				rowSpanClassMap[0] = 1;
			</c:when>
			<c:when test="${empty currentKey}">
				<c:choose>
					<c:when test="${currentKeyLvl2 == previousKeyLvl2}">
						rowSpanMap[${rowSpanStart}]++;
						rowSpanMap[${status.index}] = 0;
						rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
						rowSpanLvl2Map[${rowSpanLvl2Start}]++;
						rowSpanLvl2Map[${status.index}] = 0;
					</c:when>
					<c:otherwise>
						<c:set var="rowSpanStart" value='${status.index}' />
						<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
						<c:set var="rowSpanLvl2Start" value='${status.index}' />
						rowSpanMap[${status.index}] = 1;
						rowSpanLvl2Map[${status.index}] = 1;
						rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
						rowSpanLvl2Map[${status.index}] = 1;
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:when test="${!empty currentKey && currentKey == previousKey}">
				rowSpanMap[${rowSpanStart}]++;
				rowSpanMap[${status.index}] = 0;
				rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
				<c:choose>
					<c:when test="${currentKeyLvl2 == previousKeyLvl2}">
						rowSpanLvl2Map[${rowSpanLvl2Start}]++;
						rowSpanLvl2Map[${status.index}] = 0;
					</c:when>
					<c:otherwise>
						<c:set var="rowSpanLvl2Start" value='${status.index}' />
						rowSpanLvl2Map[${status.index}] = 1;
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:set var="rowSpanStart" value='${status.index}' />
				<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
				<c:set var="rowSpanLvl2Start" value='${status.index}' />
				rowSpanMap[${status.index}] = 1;
				rowSpanLvl2Map[${status.index}] = 1;
				rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
			</c:otherwise>
		</c:choose>
		<c:set var="previousKey" value='${currentKey}' />
		<c:set var="previousKeyLvl2" value='${currentKeyLvl2}' />
	</c:forEach>						

//-->  
</script>
        
<!-- Search results end -->
</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->

<script type="text/javascript">
    <!--
    showUpdateLinks = true;
    //-->
</script>

<!-- Hidden element start --> 
<div id="hiddenElements" style="display: none;">    			
	<input name="totalLines" id="totalLines" value="<c:out value="${rowSpanCount}"/>" type="hidden">
	<input name="uAction" id="uAction" value="" type="hidden">     
	<input name="minHeight" id="minHeight" type="hidden" value="100">
	<input name="companyId" id="companyId" type="hidden" value="${personnelBean.companyId}">
	<input name="facilityId" id="facilityId" type="hidden" value="${param.facilityId}">
	<input name="application" id="application" type="hidden" value="${param.application}">
	<input name="workAreaGroup" id="workAreaGroup" type="hidden" value="${param.workAreaGroup}">        
	<input name="workArea" id="workArea" type="hidden" value="${param.workArea}"> 
	<input name="areaId" id="areaId" type="hidden" value="${param.areaId}">
	<input name="buildingId" id="buildingId" type="hidden" value="${param.buildingId}">               
</div>
<!-- Hidden elements end -->
</tcmis:form>
</body>
</html:html>