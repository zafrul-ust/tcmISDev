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
<%@ page import="java.util.Vector" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.tcmis.client.catalog.beans.KitManagementBean" %>

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
<script type="text/javascript" src="/js/client/catalog/kitmanagementresults.js"></script>
	
		<%-- Grid support --%>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
		<%--Uncomment below if you are providing header menu to switch columns on/off--%>
		<%--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>--%>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />  


<title>
    <fmt:message key="kitManagement"/>
</title>
<script language="JavaScript" type="text/javascript">
<!--

var rowSpanMap = new Array();
var rowSpanClassMap = new Array();
var rowSpanCols = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,26,28];

var vitems = new Array();
vitems[vitems.length] = "text=<fmt:message key='label.viewrevisions'/>;url=javascript:rightClickOptions(1);";
vitems[vitems.length] = "showmenu=historyDates;text=<fmt:message key='label.viewhistory'/>;image=";
vitems[vitems.length] = "showmenu=summaryDates;text=<fmt:message key='label.viewkitsummary'/>;image=";
vitems[vitems.length] = "text=<fmt:message key='label.viewuploaddocuments'/>;url=javascript:rightClickOptions(4);";

<%-- Define the right click menus --%>
with(milonic=new menuname("rightClickMenu")){
	top="offset=2"
	style = contextStyle;
	margin=3
	aI("text=rcm;");
}

with ( milonic=new menuname("historyDates") ) {
	 top="offset=2";
	 style=submenuStyle;
	 itemheight=17;
	// style = contextStyle;
	// margin=3;
	 aI("text=<font color='gray'>"+"<fmt:message key="main.nodatafound"/>"+"</font>;url=javascript:doNothing();");

	}
	
with ( milonic=new menuname("summaryDates") ) {
	 top="offset=2";
	 style=submenuStyle;
	 itemheight=17;
	// style = contextStyle;
	// margin=3;
	 aI("text=<font color='gray'>"+"<fmt:message key="main.nodatafound"/>"+"</font>;url=javascript:doNothing();");
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
    submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
    percentVolWeight:"<fmt:message key="error.percentvolweight"/>",
    pleasemakeselection:"<fmt:message key="label.pleasemakeselection"/>",
    revision:"<fmt:message key="label.revision"/>",
    revision:"<fmt:message key="label.revision"/>",
    nodatafound:"<fmt:message key="main.nodatafound"/>",
    percentVolWeightUnitCount:"<fmt:message key="error.percentvolweightunitcount"/>"
};
 

var gridConfig = {
	divName:'kitManagementBean', // the div id to contain the grid.
	beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid', // the grid's name, as in beanGrid.attachEvent...
	config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:true, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
	submitDefault:true, // the fields in grid are defaulted to be submitted or not.
	noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
	variableHeight:false,
	onRightClick:handleRightClick,
	selectChild: 1
};

var sizeUnit = new  Array(
		{text:'<fmt:message key="label.select"/>',value:''},
		{text:'<fmt:message key="report.label.percentByVolume"/>',value:'%(v/v)'},
		{text:'<fmt:message key="report.label.percentByWeight"/>',value:'%(w/w)'}
	);

var vocDetect = new  Array(
				{text:'',value:''},
				{text:'>',value:'>'},
				{text:'>=',value:'>='},
				{text:'<=',value:'<='},
				{text:'<',value:'<'},
				{text:'=',value:'='}	
			);
var vocLwesDetect = new Array(
		{text:'',value:''},
		{text:'>',value:'>'},
		{text:'>=',value:'>='},
		{text:'<=',value:'<='},
		{text:'<',value:'<'},
		{text:'=',value:'='}
);

var densityDetect = new Array(
		{text:'',value:''},
		{text:'>',value:'>'},
		{text:'>=',value:'>='},
		{text:'<=',value:'<='},
		{text:'<',value:'<'},
		{text:'=',value:'='}
);

var vocUnit = new  Array(
		{text:'',value:''},
		{text:'%(w/w)',value:'%(w/w)'},
		{text:'g/L',value:'g/L'},
		{text:'lb/gal',value:'lb/gal'}
	);
var vocLwesUnit = new Array(
		{text:'',value:''},
{text:'g/L',value:'g/L'},
{text:'lb/gal',value:'lb/gal'}
);

var densityUnit = new Array(
		{text:'',value:''},
{value:'W',text:'<fmt:message key="label.water"/>'},
{value:'A',text:'<fmt:message key="label.air"/>'}
);


var vocLwesSource = new Array(
		{text:'',value:''},
<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status">
	<c:if test="${sourceBean.mixtureSource == 'Y'}">
		<c:if test="${status.index > 0}">,</c:if>
		{text:'${sourceBean.dataSource}',value:'${sourceBean.dataSource}'}
	</c:if>
</c:forEach>
);

var vocSource = new Array(
		{text:'',value:''},
		<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status">	
			<c:if test="${sourceBean.mixtureSource == 'Y'}">
				<c:if test="${status.index > 0}">,</c:if>
				{text:'${sourceBean.dataSource}',value:'${sourceBean.dataSource}'}
			</c:if>
		</c:forEach>
		);

var densitySource = new Array(
		{text:'',value:''},
		<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status">	
			<c:if test="${sourceBean.mixtureSource == 'Y'}">
				<c:if test="${status.index > 0}">,</c:if>
				{text:'${sourceBean.dataSource}',value:'${sourceBean.dataSource}'}
			</c:if>
		</c:forEach>
		);

var physicalState = new Array(
		{text:'<fmt:message key="label.selectOne"/>',value:''},
		{value:'gas',text:'<fmt:message key="label.gaslower"/>'},
		{value:'liquid',text:'<fmt:message key="label.liquid"/>'},
		{value:'semi-solid',text:'<fmt:message key="label.semi-solid"/>'},
		{value:'solid',text:'<fmt:message key="label.solid"/>'}
);

var physicalStateSource = new Array(
		{text:'',value:''},
		<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status">	
			<c:if test="${sourceBean.mixtureSource == 'Y'}">
				<c:if test="${status.index > 0}">,</c:if>
				{text:'${sourceBean.dataSource}',value:'${sourceBean.dataSource}'}
			</c:if>
		</c:forEach>
		);

var config = [
{
	 columnId:"permission",
	 submit:false
},
{
	 columnId:"okDoUpdate", 
	 columnName:'<fmt:message key="label.ok"/>', 
	 type:'hchstatus', 
	 align:'center', 
	 width:3
},
{
  	columnId:"customerMixtureNumber",
  	columnName:'<fmt:message key="label.kitmsds"/>',
  	submit:false,
	width:5
},
{
  	columnId:"mixtureDesc",
  	columnName:'<fmt:message key="report.label.kitDescription"/>',
    size:20, 
    width: 12,
    tooltip:"Y"
   
},
{
	 columnId:"status", 
	 columnName:'<fmt:message key="label.status"/>', 
	 type:'hchstatus', 
	 align:'center', 
	 width:3
},
{
	 columnId:"mixtureRevisionDate", 
	 columnName:'<fmt:message key="label.kitrevisiondate"/>', 
	 align:'center', 
	 width:7
},
{
	 columnId:"mixtureProductCode", 
	 columnName:'<fmt:message key="report.label.productCode"/>', 
	 type:'hed', 
	 align:'center', 
	 width:6
},
{ 	  columnId:"vocDetect",
	  columnName:'<fmt:message key="label.voc"/>',
	  attachHeader:'<fmt:message key="label.detect"/>', 
	  type:'hcoro',   
	  align:'left',
	  width:4
	  
},
{ columnId:"voc", 
	columnName:'#cspan', 
	attachHeader:'<fmt:message key="label.value"/>', 
    type:'hed',    // input field
    align:'left',
    sorting:'int',
    width:4, // the width of this cell
    size:4,  // the size of the input field
    maxlength:20
    
},
{ columnId:"vocUpper", 
	columnName:'#cspan', 
	attachHeader:'<fmt:message key="label.upper"/>', 
    type:'hed',    // input field
    align:'left',
    sorting:'int',
    width:4, // the width of this cell
    size:4,  // the size of the input field
    maxlength:20
},
{ columnId:"vocUnit", 
	columnName:'#cspan', 
	attachHeader:'<fmt:message key="label.unit"/>', 
	type:'hcoro', 
	align:'left', 
	width:5
},
{ columnId:"vocSource", 
	columnName:'#cspan', 
	attachHeader:'<fmt:message key="label.source"/>', 
	type:'hcoro',
    align:'left',
    width:8
},
{ 	  columnId:"vocLwesDetect",
	  columnName:'<fmt:message key="label.voclwes"/>',
	  attachHeader:'<fmt:message key="label.detect"/>', 
	  type:'hcoro',   
	  align:'left',
	  width:4
},
{ columnId:"vocLwes", 
	columnName:'#cspan', 
	attachHeader:'<fmt:message key="label.value"/>', 
  type:'hed',    // input field
  align:'left',
  sorting:'int',
  width:4, // the width of this cell
  size:4,  // the size of the input field
  maxlength:20
},
{ columnId:"vocLwesUpper", 
	columnName:'#cspan', 
	attachHeader:'<fmt:message key="label.upper"/>', 
  type:'hed',    // input field
  align:'left',
  sorting:'int',
  width:4, // the width of this cell
  size:4,  // the size of the input field
  maxlength:20
},
{ columnId:"vocLwesUnit", 
	columnName:'#cspan', 
	attachHeader:'<fmt:message key="label.unit"/>', 
	type:'hcoro', 
	align:'left', 
	width:5
},
{ columnId:"vocLwesSource", 
	columnName:'#cspan', 
	attachHeader:'<fmt:message key="label.source"/>', 
	type:'hcoro',
    align:'left',
    width:8
},
{ columnId:"specificGravity", 
	columnName: '<fmt:message key="label.specificgravity"/>', 
    align:'left',
    width:8
},
{ columnId:"density", 
	columnName:'<fmt:message key="label.density"/>', 
    align:'left',
    width:8
},
{   columnId:"physicalState", 
	columnName:'<fmt:message key="label.physicalstate"/>', 
	attachHeader:'<fmt:message key="label.value"/>', 
	type:'hcoro', 
	align:'left', 
	width:7
},
{ columnId:"physicalStateSource", 
	columnName:'#cspan', 
	attachHeader:'<fmt:message key="label.source"/>', 
	type:'hcoro', 
	align:'left', 
	width:7
},
{
  	columnId:"revisionComments",
  	columnName:'<fmt:message key="label.comments"/>',
  	type:'hed',
    size:20, 
    width: 12,
    tooltip:"Y"
},
{ columnId:"mixtureId"
},
{
  	columnId:"componentMsds",
  	columnName:'<fmt:message key="label.compmsds"/>',
	 submit:false, 
	width:5
},
{
	 columnId:"componentRevisionDate", 
	 columnName:'<fmt:message key="label.componentrevisiondate"/>', 
	 align:'center', 
	 width:7
},
{
	columnId:"amountPermission",
	submit:true
},
{
	columnId:"sizeUnitPermission"
},
{ columnId:"amount",
  columnName:'<fmt:message key="label.mixratio"/>',
  attachHeader:'<fmt:message key="label.amount"/>',
  type: 'hed',
  width: 4,
  size:10,
  permission:true,
  submit:true
},

{ columnId:"sizeUnit",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.unit"/>',
  type: 'hcoro',
  width: 7,
  tooltip:"Y",
  permission:true,
  submit:true
},
{
  	columnId:"materialDescription",
  	columnName:'<fmt:message key="label.materialdescription"/>',
  	width: 30,
	submit:false,
	tooltip:"Y"
  	  	
},
{ columnId:"materialId"
},
{ columnId:"customerMsdsDb"
}
];	

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">
<tcmis:form action="/kitmanagementresults.do" onsubmit="return submitFrameOnlyOnce();">
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

<div id="kitManagementBean" style="width:100%;height:400px;" style="display: none;"></div>
<c:choose>
<c:when test="${empty searchResults}">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
		<tr>
			<td width="100%">
				<fmt:message key="main.nodatafound"/>
			</td>
		</tr>
	</table>
</c:when>				
<c:otherwise>

 <c:set var="showUpdateLink" value='N'/>
<tcmis:facilityPermission indicator="true" userGroupId="KitManagement" facilityId="${param.facilityId}">
 <c:set var="showUpdateLink" value='Y'/>
</tcmis:facilityPermission>

<c:if test="${showUpdateLink == 'Y'}">
	<%
		Vector<KitManagementBean> list = (Vector<KitManagementBean>)request.getAttribute("searchResults");
		BigDecimal currMixId = list.firstElement().getMixtureId();
		Vector<String> mixPermVec = new Vector<String>();
		int kitSize = 0;
		String currMixPerm = "N";
		for(KitManagementBean bean : list)
		{
			if(currMixId.compareTo(bean.getMixtureId()) == 0)
			{
				if(bean.getAmount() == null)
					currMixPerm = "Y";
				kitSize++;
			}
			else
			{
				for(int i = 0; i < kitSize; i++)
					mixPermVec.add(currMixPerm);

				currMixPerm = "N";
				kitSize = 1;
				currMixId = bean.getMixtureId();
			}
		}
		
		for(int i = 0; i < kitSize; i++)
			mixPermVec.add(currMixPerm);
	
	 	pageContext.setAttribute("mixPerm", mixPermVec);
	%>
</c:if>

<script language="JavaScript" type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>

  var jsonMainData = {
  rows:[
  <c:forEach var="bean" items="${searchResults}" varStatus="status">
  <c:set var="mixStatus" value='false'/>
  <c:if test="${bean.status == 'A'}">
  	<c:set var="mixStatus" value='true'/>
  </c:if>
        { id:${status.index +1},
	        data:[
	        		'${showUpdateLink}',
	        		false,
					'${bean.customerMixtureNumber}',
					'<tcmis:jsReplace value="${bean.mixtureDesc}" processMultiLines="true" />',
					${mixStatus},
					'<fmt:formatDate value="${bean.mixtureRevisionDate}" pattern="${dateFormatPattern}"/>',
					'${bean.mixtureProductCode}',
					'${bean.vocDetect}',
					'${bean.voc}',
					'${bean.vocUpper}',
					'${bean.vocUnit}',
					'${bean.vocSource}',
					'${bean.vocLwesDetect}',
					'${bean.vocLwes}',
					'${bean.vocLwesUpper}',
					'${bean.vocLwesUnit}',
					'${bean.vocLwesSource}',
			        '${bean.specificGravityDisplay}',
			        '${bean.densityDisplay}',
					'${bean.physicalState}',
					'${bean.physicalStateSource}',
					'${bean.revisionComments}',
					'${bean.mixtureId}',
					'${bean.customerMsdsNumber}',
					'<fmt:formatDate value="${bean.componentRevisionDate}" pattern="${dateFormatPattern}"/>',
					'${mixPerm[status.index]}',
					'${mixPerm[status.index]}',
					'${bean.amount}',
					'${bean.sizeUnit}',
					'<tcmis:jsReplace value="${bean.materialDesc}" processMultiLines="true" />',
					'${bean.materialId}',
					'${bean.customerMsdsDb}'
	        ]}<c:if test="${!status.last}">,</c:if>
	 <c:set var="dataCount" value='${dataCount+1}'/> 
  </c:forEach>
  ]};

  
  	<%-- determining rowspan --%>
  	<c:set var="rowSpanCount" value='0' />
  		<%-- determining rowspan --%>
  		<c:forEach var="row" items="${searchResults}" varStatus="status">
  		<c:set var="currentKey" value='${row.mixtureId}' />
  			<c:choose>
  				<c:when test="${status.first}">
  					<c:set var="rowSpanStart" value='0' />
  					<c:set var="rowSpanCount" value='1' />
  					<c:set var="prevSpanCount" value="${rowSpanCount}"/>
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
	</c:otherwise>
</c:choose>

 <!-- Search results end --> 

    <!-- Hidden element start --> 
    <div id="hiddenElements" style="display: none;">    			
		<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
        <input name="uAction" id="uAction" value="" type="hidden"> 
        <input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden">
        <input name="companyId" id="companyId" value="${personnelBean.companyId}" type="hidden">
        <input name="createdBy" id="createdBy" value="${param.createdBy}" type="hidden">
        <input name="lastModifiedBy" id="lastModifiedBy" value="${param.lastModifiedBy}" type="hidden">
        <input name="searchField" id="searchField" value="${param.searchField}" type="hidden">
        <input name="searchMode" id="searchMode" value="${param.searchMode}" type="hidden">
        <input name="searchArgument" id="searchArgument" value="${param.searchArgument}" type="hidden">
        <input name="searchField" id="searchField" value="${param.searchField}" type="hidden">
        <input name="minHeight" id="minHeight" type="hidden" value="100">
        <input name="maxData" id="maxData" type="hidden" value="${param.maxData}">
        <input name="startSearchTime" id="startSearchTime" type="hidden" value="">
        <input name="showActive" id="showActive" type="hidden" value="${param.showActive}">
        <input name="inDefinite_mixtureRevisionDate" id="inDefinite_mixtureRevisionDate" type="hidden" value="N">
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