
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
<script type="text/javascript" src="/js/client/het/permitmanagementresults.js"></script>


<title>
    Update Example
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData = {
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		selectSomething:"<fmt:message key="error.norowselected"/>",
		validvalues:"<fmt:message key="label.validvalues"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		recordFound:"<fmt:message key="label.recordFound"/>",
		searchDuration:"<fmt:message key="label.searchDuration"/>",
		minutes:"<fmt:message key="label.minutes"/>",
		seconds:"<fmt:message key="label.seconds"/>",
		ok:"<fmt:message key="label.ok"/>",
		error:"<fmt:message key="error.db.update"/>",
		required:"<fmt:message key="label.pleaseadd"/>",
		permitName:"<fmt:message key="label.permitname"/>",
		workarea:"<fmt:message key="label.workarea"/>",
		fornext:"<fmt:message key="label.fornext"/>",
		exists: "<fmt:message key="errors.alreadyexists"/>",
		all:"<fmt:message key="label.all"/>",
		area:"<fmt:message key="label.area"/>",
		building:"<fmt:message key="label.building"/>",
		workArea:"<fmt:message key="label.workarea"/>",
		verifyDelete:"<fmt:message key="label.verifydelete"><fmt:param><fmt:message key="label.permit"/></fmt:param></fmt:message>"
	};    



var config = [
		{columnId:"permission"},
		{columnId:"updated", columnName:'<fmt:message key="label.ok"/>', type:'hchstatus', align:'center', width:3, sort: 'na', sorting: 'na'},
  		{columnId:"areaId", columnName:'<fmt:message key="label.area"/>', width:10, type: 'hcoro', onChange: setBuildingsforArea, submit: false, sort: 'na', sorting: 'na'},
  		{columnId:"buildingId", columnName:'<fmt:message key="label.building"/>', width:10, type: 'hcoro', sort: 'na', sorting: 'na'},
  		{columnId:"permitName", columnName:'<fmt:message key="label.permit"/>', align:'left', width:10, type:'hed', size:10,  maxlength:10, sort: 'na' , sorting: 'na'},
		{columnId:"controlDevice", columnName:'<fmt:message key="label.controldevice"/>', align:'left', width:10, type:'hed', size:10,  maxlength:10, sort: 'na', sorting: 'na'},
		{columnId:"displayStatus", columnName:'<fmt:message key="label.active"/>', type:'hchstatus', align:'center', width:4, sort: 'na'},
		{columnId:"description", columnName:'<fmt:message key="label.description"/>', align:'left', width:25, type:'hed', size:25, tooltip: true, maxlength:200, sort: 'na', sorting: 'na'},
		{columnId:"permitId"},
		{columnId:"companyId"},
		{columnId:"facilityId"}
		];

var gridConfig = {
	       divName:'PermitManagement', // the div id to contain the grid. ALSO the var name for the submitted data
	       beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	       beanGrid:'mygrid', // the grid's name, for later reference/usage
	       config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
	       onAfterHaasRenderRow : setBuildingsforArea,
	       rowSpan: false, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
	       submitDefault: true, // the fields in grid are defaulted to be submitted or not.
	       noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
		   variableHeight:false
 };
	       
var areaId = new Array(
		<c:forEach var="area" items="${areas}" varStatus="status">
			 {value:'${area.areaId}',text:'<tcmis:jsReplace value="${area.areaName}"/> - <tcmis:jsReplace value="${area.areaDescription}"/>'}<c:if test="${!status.last}">,</c:if>
		</c:forEach>
	);
 
var buildingId = new Array(
		<c:forEach var="building" items="${buildings}" varStatus="status">
			 {value:'${building.buildingId}',text:'<tcmis:jsReplace value="${building.buildingDisplay}"/>'}<c:if test="${!status.last}">,</c:if>
		</c:forEach>
	);

// -->
</script>

</head>

<body bgcolor="#ffffff" onload="myResultOnLoadWithGrid(gridConfig);parent.document.getElementById('mainUpdateLinks').style['display'] = ''">
<tcmis:form action="/permitmanagementresults.do" onsubmit="return submitFrameOnlyOnce();">
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
		<div id="PermitManagement" style="width:100%;height:400px;" style="display: none;"></div>

<script type="text/javascript">
	<!--<c:set var="lastPermit" value="-1"/>
	var jsonMainData = {
		rows:[<c:forEach var="bean" items="${permits}" varStatus="status">
			{id:${status.count},
 			 data:[	'Y',
					false,
	  				'<tcmis:jsReplace value="${bean.areaId}"/>',
  					'<tcmis:jsReplace value="${bean.buildingId}"/>',
	  				'<tcmis:jsReplace value="${bean.permitName}"/>',
	  				'<tcmis:jsReplace value="${bean.controlDevice}"/>',
	  				${bean.displayStatus},
	  				'<tcmis:jsReplace value="${bean.description}"/>',
				'${bean.permitId}',
				'${bean.companyId}',
				'${bean.facilityId}'
				]}<c:if test="${!status.last}">,</c:if>
		      </c:forEach>]};

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
	<input name="totalLines" id="totalLines" value="${fn:length(permits)}" type="hidden">
	<input name="uAction" id="uAction" value="" type="hidden">     
	<input name="minHeight" id="minHeight" type="hidden" value="100">
	<input name="companyId" id="companyId" type="hidden" value="${personnelBean.companyId}">
	<input name="facilityId" id="facilityId" type="hidden" value="${param.facilityId}">
	<input name="areaId" id="areaId" type="hidden" value="${param.areaId}">
	<input name="buildingId" id="buildingId" type="hidden" value="${param.buildingId}">        
	<input name="workArea" id="workArea" type="hidden" value="${param.workArea}">        
</div>
<!-- Hidden elements end -->
</tcmis:form>
</body>
</html:html>