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
	
	<!-- This handles the menu style and what happens to the right click on the whole page -->
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>
	
	<!-- For Calendar support for column type hcal-->
	<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
	<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
	<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
	<script type="text/javascript" src="/js/calendar/calendarval.js"></script>
	
	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/catalog/kitindexingqueueresults.js"></script>
	
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
	
	<%--Uncomment the below if your grid has rowspans >--%>
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
		<fmt:message key="kitindexingqueue.title"/>
	</title>
	
<script>
<!--
with(milonic=new menuname("rightClickMenu")){
	top = "offset=2";
	style = contextStyle;
	margin = 3;
	aI("text=<fmt:message key="label.viewmsds"/>;url=javascript:viewMsds();");
}

drawMenus();

<%-- See webroot/dhtmlxgrid/codebase/dhtmlxcommon_haas.js for option explanations of config & gridconfig --%>
var columnConfig = [ 
	{columnId:"permission",submit:false},  
	{columnId:"ok", columnName:'<fmt:message key="label.ok"/>', type:'hchstatus', tooltip:true, align:'center', width: 3},
	{columnId:"companyName", columnName:'<fmt:message key="label.company"/>', align:'center', width:10},
	{columnId:"customerMsdsDb", columnName:'<fmt:message key="label.msdsdatabase"/>', align:'center', width:10},
	{columnId:"customerMixtureNumber", columnName:'<fmt:message key="label.kitmsds"/>', submit:true, tooltip:true, width:5},
	{columnId:"mixtureDesc", columnName:'<fmt:message key="report.label.kitDescription"/>', tooltip:true, width:12},
	{columnId:"mixtureVocDetect", columnName:'<fmt:message key="label.voc"/>', attachHeader:'<fmt:message key="label.detect"/>', type:'hcoro', align:'left', width:4},
	{columnId:"mixtureVoc", columnName:'#cspan', attachHeader:'<fmt:message key="label.value"/>', type: 'hed', align:'left', sorting:'int', width:4, size:3, maxlength:20},
	{columnId:"mixtureVocLower", columnName:'#cspan', attachHeader:'<fmt:message key="label.lower"/>', type: 'hed', align:'left', sorting:'int', width:4, size:3, maxlength:20},
	{columnId:"mixtureVocUpper", columnName:'#cspan', attachHeader:'<fmt:message key="label.upper"/>', type: 'hed', align:'left', sorting:'int', width:4, size:3, maxlength:20},
	{columnId:"mixtureVocUnit",columnName:'#cspan', attachHeader:'<fmt:message key="label.unit"/>', type:'hcoro', align:'left', width:6},
	{columnId:"mixtureVocSource",columnName:'#cspan', attachHeader:'<fmt:message key="label.source"/>', type:'hcoro', align:'left', width:8},
	{columnId:"mixtureVocLwesDetect", columnName:'<fmt:message key="label.voclwes"/>', attachHeader:'<fmt:message key="label.detect"/>', type:'hcoro', align:'left', width:4},
	{columnId:"mixtureVocLwes", columnName:'#cspan', attachHeader:'<fmt:message key="label.value"/>', type: 'hed', align:'left', sorting:'int', width:4, size:3, maxlength:20},
	{columnId:"mixtureVocLwesLower", columnName:'#cspan', attachHeader:'<fmt:message key="label.lower"/>', type: 'hed', align:'left', sorting:'int', width:4, size:3, maxlength:20},
	{columnId:"mixtureVocLwesUpper", columnName:'#cspan', attachHeader:'<fmt:message key="label.upper"/>', type: 'hed', align:'left', sorting:'int', width:4, size:3, maxlength:20},
	{columnId:"mixtureVocLwesUnit",columnName:'#cspan', attachHeader:'<fmt:message key="label.unit"/>', type:'hcoro', align:'left', width:5},
	{columnId:"mixtureVocLwesSource",columnName:'#cspan', attachHeader:'<fmt:message key="label.source"/>', type:'hcoro', align:'left', width:8},
	{columnId:"materialIdDisplay",columnName:'<fmt:message key="label.materialid"/>', tooltip:true,width:6},
	{columnId:"componentRevDate",columnName:'<fmt:message key="label.revisiondate"/>', tooltip:true,width:6,submit:false},
	{columnId:"materialDesc", columnName:'<fmt:message key="label.materialdescription"/>', tooltip:true, width:30},
	{columnId:"mfgDesc", columnName:'<fmt:message key="label.manufacturer"/>', align:'center', width:10},
	{columnId:"tradeName", columnName:'<fmt:message key="label.tradename"/>', align:'center', width:12},
	{columnId:"materialId"},
	{columnId:"newRevisionDate",submit:false},
    {columnId:"asMixed"},
    {columnId:"mixtureId"}    
    ];
       
   var gridConfig = {
       divName:'KitIndexingQueueViewBean', // the div id to contain the grid. ALSO the var name for the submitted data
       beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
       beanGrid:'beangrid', // the grid's name, for later reference/usage
       config:'columnConfig', // the column config var name, as in var config = { [ columnId:..,columnName...
       rowSpan: true, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
       submitDefault: true, // the fields in grid are defaulted to be submitted or not.
       noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
       onRightClick:rightClickRow, // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
       onRowSelect: doOnRowSelected,
       selectChild: 1 // Double select single row
   }; 

// RowSpan variables:
// rowSpanMap contains an entry for each row with its associated rowspan 1, 2, ... or 0 for child row 
// rowSpanClassMap contains the color (odd, even) for the row
// rowSpanCols contains the indexes of the columns that span
   var rowSpanMap = new Array();
   var rowSpanClassMap = new Array();	
   var rowSpanCols = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17];
   
   var messagesData = new Array();
		messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
   		recordFound:"<fmt:message key="label.recordFound"/>",
   		searchDuration:"<fmt:message key="label.searchDuration"/>",
   		minutes:"<fmt:message key="label.minutes"/>",
   		seconds:"<fmt:message key="label.seconds"/>",
   		validvalues:"<fmt:message key="label.validvalues"/>",
   		msdsMaintenance:"<fmt:message key="msdsMaintenance"/>",
   		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
		
	var sizeUnit = new  Array(
			{text:'<fmt:message key="report.label.percentByVolume"/>',value:'%(v/v)'},
			{text:'<fmt:message key="report.label.percentByWeight"/>',value:'%(w/w)'}
		);

	var mixtureVocDetect = new  Array(
			{text:'',value:''},
			{text:'>',value:'>'},
			{text:'>=',value:'>='},
			{text:'<=',value:'<='},
			{text:'<',value:'<'},
			{text:'=',value:'='}
		);
	var mixtureVocLwesDetect = new  Array(
			{text:'',value:''},
			{text:'>',value:'>'},
			{text:'>=',value:'>='},
			{text:'<=',value:'<='},
			{text:'<',value:'<'},
			{text:'=',value:'='}
		);
	var mixtureVocUnit = new  Array(
			{text:'',value:''},
			{text:'%(w/w)',value:'%(w/w)'},
			{text:'g/L',value:'g/L'},
			{text:'lb/gal',value:'lb/gal'}
		);
	var mixtureVocLwesUnit = new Array(
			{text:'',value:''},
			{text:'g/L',value:'g/L'},
			{text:'lb/gal',value:'lb/gal'}
		);
	var mixtureVocSource = new Array(
			{text:'',value:''},
			<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status">		
					{text:'${sourceBean.dataSource}',value:'${sourceBean.dataSource}'}<c:if test="${!status.last}">,</c:if>
			</c:forEach>
		);
	var mixtureVocLwesSource = new Array(
			{text:'',value:''},
			<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status">		
					{text:'${sourceBean.dataSource}',value:'${sourceBean.dataSource}'}<c:if test="${!status.last}">,</c:if>
			</c:forEach>
		);
-->			
</script>

</head>
<body bgcolor="#ffffff" onload="resultOnLoad();">
	<tcmis:form action="/kitindexingqueueresults.do" onsubmit="return submitFrameOnlyOnce();">
		<div id="errorMessagesAreaBody" style="display: none;">
			<html:errors /> 
			${tcmISError} 
			<c:forEach items="${tcmISErrors}" varStatus="status">
		  		${catalogRequest}<br />
			</c:forEach>
		</div>
		<script type="text/javascript">
			<c:choose>
				<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors }">showErrorMessage = false;</c:when>
				<c:otherwise>showErrorMessage = true;</c:otherwise>
			</c:choose>   
		</script>
	
	
		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
				<div id="KitIndexingQueueViewBean" style="width: 100%; height: 600px;" style="display: none;"></div>

				<c:choose>
					<c:when test="${empty kitCollection}">
						<%-- If the collection is empty say no data found --%> 
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
							<tr>
                                <c:if test="${param.uAction == 'getNewKitNumber'}">
                                     <td width="100%"><fmt:message key="label.newkitnumber"/>: <b>${newKitNumber}</b></td>    
                                </c:if>
                                <c:if test="${param.uAction == 'search'}">
                                    <td width="100%"><fmt:message key="main.nodatafound" /></td>
                                </c:if>
							</tr>
						</table>
					</c:when> 
					<c:otherwise>
						<script type="text/javascript">
							<c:set var="lineCount" value='0'/>
							var jsonMainData = new Array();
							var jsonMainData = {
								rows:[
								<c:forEach var="kitBean" items="${kitCollection}" varStatus="status">
									<c:set var="lineCount" value='${lineCount+1}'/>
									<c:set var="materialIdDisplay" value="${kitBean.materialId}"/>
									<c:if test="${not empty kitBean.newRevisionDate}">
										<c:set var="materialIdDisplay">${kitBean.materialId} <img onclick="showMsds=true;" src="/images/buttons/document.gif"/></c:set>
									</c:if>
									<c:if test="${lineCount > 1}">,</c:if>
									{id:${lineCount},
									 data:['Y',
									  '${kitBean.ok}',
									  '<tcmis:jsReplace value="${kitBean.companyName}"/>',
									  '<tcmis:jsReplace value="${kitBean.customerMsdsDb}"/>',
									  '${kitBean.customerMixtureNumber}',
									  '<tcmis:jsReplace value="${kitBean.mixtureDesc}"/>',
									  '<tcmis:jsReplace value="${kitBean.mixtureVocDetect}"/>',
									  '${kitBean.mixtureVoc}',
									  '<tcmis:jsReplace value="${kitBean.mixtureVocLower}"/>',
									  '<tcmis:jsReplace value="${kitBean.mixtureVocUpper}"/>',
									  '<tcmis:jsReplace value="${kitBean.mixtureVocUnit}"/>',
									  '<tcmis:jsReplace value="${kitBean.mixtureVocSource}"/>',
									  '<tcmis:jsReplace value="${kitBean.mixtureVocLwesDetect}"/>',
									  '${kitBean.mixtureVocLwes}',
									  '<tcmis:jsReplace value="${kitBean.mixtureVocLwesLower}"/>',
									  '<tcmis:jsReplace value="${kitBean.mixtureVocLwesUpper}"/>',
									  '<tcmis:jsReplace value="${kitBean.mixtureVocLwesUnit}"/>',
									  '<tcmis:jsReplace value="${kitBean.mixtureVocLwesSource}"/>',
									  '${materialIdDisplay}',
									  '<fmt:formatDate value="${kitBean.componentRevDate}" pattern="MM/dd/yyyy"/>',
									  '<tcmis:jsReplace value="${kitBean.materialDesc}"/>',
									  '<tcmis:jsReplace value="${kitBean.mfgDesc}"/>',
									  '<tcmis:jsReplace value="${kitBean.tradeName}"/>',
									  '${kitBean.materialId}',
									  '<fmt:formatDate value="${kitBean.newRevisionDate}" pattern="MM/dd/yyyy"/>',
									  '${kitBean.asMixed}',
                                      '${kitBean.mixtureId}'
                                      ]}
								 </c:forEach>
								]};
						</script>
					</c:otherwise>				
				</c:choose>
				<!-- Search results end -->

				<%-- determining rowspan --%>
				<script language="JavaScript" type="text/javascript">
					<c:forEach var="kitBean" items="${kitCollection}" varStatus="status">
					<c:set var="currentKey" value='${kitBean.mixtureId}' />
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
				</script>

				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<%--This is the minimum height of the result section you want to display--%> 
					<%--<input name="minHeight" id="minHeight" type="hidden" value="100" />--%>
					<input name="totalLines" id="totalLines" type="hidden" value="<c:out value="${lineCount}"/>">
					<input name="uAction" id="uAction" value="search" type="hidden">
			        <input name="searchField" id="searchField" value="${param.searchField}" type="hidden">
			        <input name="companyId" id="companyId" value="${param.companyId}" type="hidden">
			        <input name="catalogId" id="catalogId" value="${param.catalogId}" type="hidden">
			        <input name="minHeight" id="minHeight" type="hidden" value="100"> 
			        <input name="maxData" id="maxData" type="hidden" value="${param.maxData}"> 
				</div>
			
			</div>
		<!-- close of backGroundContent -->
		</div>
		<!-- interface End -->
	
	</tcmis:form>
</body>
</html:html>