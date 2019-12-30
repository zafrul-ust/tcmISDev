<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		
		<tcmis:gridFontSizeCss /> <%-- Sets CSS based on the user's preffered font size and which application he is viewing --%>
	
		<script type="text/javascript" src="/js/common/formchek.js"></script>			<%-- Validation support --%>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
	
		<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script> <%-- This handles all the resizing of the page and frames --%>
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>

		<%-- Right Mouse Click (RMC) Menu support  --%>
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
		
		
		<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/common/clientinventory/inventory.js"></script>

<title>
<fmt:message key="label.inventory"/>
</title>

<script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
with(milonic=new menuname("showAll")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="label.inventorydetails"/>;url=javascript:showInventoryDetails();");
 aI("text=<fmt:message key="label.plots"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
 aI("text=&nbsp;&nbsp;<fmt:message key="graphs.label.inventorytwomonths"/>;url=javascript:showInventoryPlot(2);");
 aI("text=&nbsp;&nbsp;<fmt:message key="graphs.label.inventoryoneyear"/>;url=javascript:showInventoryPlot(12);");
 aI("text=&nbsp;&nbsp;<fmt:message key="graphs.label.mrleadtime"/>;url=javascript:showMrLeadTimePlot();");
 aI("text=&nbsp;&nbsp;<fmt:message key="graphs.label.supplyleadtime"/>;url=javascript:showSupplyLeadTimePlot();");
 aI("text=&nbsp;&nbsp;<fmt:message key="graphs.label.issues"/>;url=javascript:showIssues();");
 aI("text=&nbsp;&nbsp;<fmt:message key="graphs.label.receipts"/>;url=javascript:showReceipts();");
}

with(milonic=new menuname("showInventoryDetail")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="label.inventorydetails"/>;url=javascript:showInventoryDetails();");
}

drawMenus();
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
entervalidintegerforexpireswithin:"<fmt:message key="label.entervalidintegerforexpireswithin"/>",
entervalidintegerforexpiresafter:"<fmt:message key="label.entervalidintegerforexpiresafter"/>",
entervalidintegerforarriveswithin:"<fmt:message key="label.entervalidintegerforarriveswithin"/>",
novalidlineselected:"<fmt:message key="label.novalidlineselected"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};


<%-- Define the columns for the result grid --%>
var columnConfig = [
    {columnId:"permission"},
	{columnId:"catalogId"},
    {columnId:"catalogDesc", columnName:'<fmt:message key="label.catalog"/>', tooltip:"Y"},
    {columnId:"catPartNo",columnName:'<fmt:message key="label.part"/>',tooltip:"Y"},
	{columnId:"partDescription",columnName:'<fmt:message key="label.description"/>', tooltip:"Y", width:25},
	{columnId:"stockingMethod", columnName:'<fmt:message key="label.type"/>', tooltip:"Y", width:5},
	{columnId:"setPoints", columnName:'<fmt:message key="inventory.label.setpoint"/><br><fmt:message key="inventory.label.setpointlabel"/>', sorting:'int', align:"right"},
	{columnId:"inventoryGroupName", columnName:'<fmt:message key="label.invengroup"/>', sorting:'int', align:"right" },
	{columnId:"qtyAvailable", columnName:'<fmt:message key="label.available"/><br><fmt:message key="inventory.label.lastcounted"/>', width:5},
	{columnId:"qtyHeld", columnName:'<fmt:message key="label.held"/>', width:4},
	{columnId:"qtyOnOrder", columnName:'<fmt:message key="label.onorder"/>', width:4},
	{columnId:"qtyInPurchasing", columnName:'<fmt:message key="label.inpurchasing"/>', width:6},
	{columnId:"itemPackaging", columnName:'<fmt:message key="inventory.label.inventoryuom"/>'},
	{columnId:"materialDesc", columnName:'<fmt:message key="label.item"/>'},
	{columnId:"materialDesc", columnName:'<fmt:message key="inventory.label.componentdescription"/>', tooltip:"Y", width:25},
	{columnId:"packaging", columnName:'<fmt:message key="inventory.label.packaging"/>'},
	{columnId:"mfgDesc", columnName:'<fmt:message key="label.manufacturer"/>', tooltip:"Y"},
	{columnId:"mfgPartNo", columnName:'<fmt:message key="label.mfgpartno"/>'},
	{columnId:"itemId"},
	{columnId:"inventoryGroup"},
	{columnId:"inventoryGroupName"},	
	{columnId:"catalogId"},
	{columnId:"catPartNo"},
	{columnId:"partGroupNo"},
	{columnId:"issueGeneration"},
	{columnId:"catalogCompanyId"}
	
];

drawMenus();

<%-- Define the grid options--%>
var gridConfig = {
	divName: 'inventoryGridDiv',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
	beanData: 'jsonMainData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
	beanGrid: 'mygrid',		<%--  variable to put the grid object in for later use --%>
	config: columnConfig,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
    rowSpan: true, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
    submitDefault: true, // the fields in grid are defaulted to be submitted or not.
    noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
	variableHeight:false,
	onRightClick: onRightclick
	};

var rowSpanMap = new Array();
var rowSpanClassMap = new Array();	
var rowSpanCols = [0,1,2,3,4,5,6,7,8,9,10,11,12];
var rowSpanLvl2Map = new Array();
var rowSpanLvl2Cols = [13];


<%-- Check for passed error message that will require an error inline popup --%>
<c:choose>
	<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
		showErrorMessage = false;
	</c:when>
	<c:otherwise>
		showErrorMessage = true;
	</c:otherwise>
</c:choose>   
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">

<tcmis:form action="/inventoryresults.do" onsubmit="return submitFrameOnlyOnce();">

	<div id="errorMessagesAreaBody" style="display:none;">
 				<html:errors/>
 				${tcmISError}
 				<c:forEach var="tcmisError" items="${tcmISErrors}" >
				${tcmisError}<br/>
 				</c:forEach>        
	</div>

<div class="interface" id="resultsPage">
	<div class="backGroundContent">
		<div id="inventoryGridDiv" style="width:100%;height:400px;display: none;"></div>
		<c:choose>
			<c:when test="${empty pkgInventoryDetailWebPrInventoryBeanCollection || pkgInventoryDetailWebPrInventoryBeanCollection == null}">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
					<tr>
						<td width="100%">
							<fmt:message key="main.nodatafound"/>
						</td>
					</tr>
				</table>
			</c:when>				
			<c:otherwise>
		<script type="text/javascript">
		<!--
        <c:set var="dataCount" value='1' />
    	 <fmt:formatDate var="formattedLastCountDate" value="${pkgInventoryDetailWebPrInventoryBean.lastCountDate}" pattern="${dateFormatPattern}"/>
			var jsonMainData = new Array();
			jsonMainData = {
				rows:[
				<c:forEach var="pkgInventoryDetailWebPrInventoryBean" items="${pkgInventoryDetailWebPrInventoryBeanCollection}" varStatus="pkgInventoryDetailWebPrInventoryBeanStatus">
					<c:forEach var="pkgInventoryDetailItemBean" items="${pkgInventoryDetailWebPrInventoryBean.itemCollection}" varStatus="pkgInventoryDetailItemBeanStatus">
						<c:forEach var="pkgInventoryDetailComponentBean" items="${pkgInventoryDetailItemBean.componentCollection}" varStatus="pkgInventoryDetailComponentBeanStatus">
						<%-- Loop through the results and output each row, formatting the results as necessary 
					     use tcmis:jsReplace to escape special characters for ANY user entered text
					     use fmt:formatNumber to format numbers (existing patterns: unitpricecurrencyformat, totalcurrencyformat, oneDigitformat)
					     use mt:formatDate to format dates (existing patterns: dateFormatPattern, dateTimeFormatPattern)--%>
						<c:if test="${dataCount > 1}">,</c:if>
						{id:${dataCount}, <%-- use status.count rather than status.index because the grid requires a 1 based index rather than 0 based --%>
						data:[
						'Y',
						'${pkgInventoryDetailWebPrInventoryBean.catalogId}',
                        '<tcmis:jsReplace value="${pkgInventoryDetailWebPrInventoryBean.catalogDesc}" />',
                        '${pkgInventoryDetailWebPrInventoryBean.catPartNo}',
						'<tcmis:jsReplace value="${pkgInventoryDetailWebPrInventoryBean.partDescription}" processMultiLines="true"/>',
						'${pkgInventoryDetailWebPrInventoryBean.stockingMethod}',
						'${pkgInventoryDetailWebPrInventoryBean.setPoints}',
						'${pkgInventoryDetailWebPrInventoryBean.inventoryGroupName}',
						'<fmt:formatNumber maxFractionDigits="2" minFractionDigits="0">${pkgInventoryDetailWebPrInventoryBean.qtyAvailable}</fmt:formatNumber> ${formattedLastCountDate}',
						'<fmt:formatNumber maxFractionDigits="2" minFractionDigits="0">${pkgInventoryDetailWebPrInventoryBean.qtyHeld}</fmt:formatNumber>',
						'<fmt:formatNumber maxFractionDigits="2" minFractionDigits="0">${pkgInventoryDetailWebPrInventoryBean.qtyOnOrder}</fmt:formatNumber>',
						'<fmt:formatNumber maxFractionDigits="2" minFractionDigits="0">${pkgInventoryDetailWebPrInventoryBean.qtyInPurchasing}</fmt:formatNumber>',
					 	'${pkgInventoryDetailWebPrInventoryBean.itemPackaging}',
						'${pkgInventoryDetailItemBean.itemId}',
						'<tcmis:jsReplace value="${pkgInventoryDetailComponentBean.materialDesc}" />',
						'${pkgInventoryDetailComponentBean.packaging}',
						'<tcmis:jsReplace value="${pkgInventoryDetailComponentBean.mfgDesc}" />',
						'<tcmis:jsReplace value="${pkgInventoryDetailComponentBean.mfgPartNo}" />',
						'${pkgInventoryDetailWebPrInventoryBean.itemId}',
						'${pkgInventoryDetailWebPrInventoryBean.inventoryGroup}',
						'${pkgInventoryDetailWebPrInventoryBean.inventoryGroupName}',
						'${pkgInventoryDetailWebPrInventoryBean.catalogId}',
						'${pkgInventoryDetailWebPrInventoryBean.catPartNo}',
						'${pkgInventoryDetailWebPrInventoryBean.partGroupNo}',
						'${pkgInventoryDetailWebPrInventoryBean.issueGeneration}',
						'${pkgInventoryDetailWebPrInventoryBean.catalogCompanyId}'
						]}
						<c:set var="dataCount" value='${dataCount + 1}' />
						</c:forEach>
					</c:forEach>
		    	</c:forEach>
				]};

			<c:set var="lineCount" value='0' />
			<c:set var="rowSpanCount" value='0' />
			<c:forEach var="pkgInventoryDetailWebPrInventoryBean2" items="${pkgInventoryDetailWebPrInventoryBeanCollection}" varStatus="pkgInventoryDetailWebPrInventoryBeanStatus2">
				<c:set var="rowSpanStart" value='${lineCount}' />
				<c:set var="rowSpanSize" value='0' />
				<c:forEach var="pkgInventoryDetailItemBean2" items="${pkgInventoryDetailWebPrInventoryBean2.itemCollection}" varStatus="pkgInventoryDetailItemBeanStatus2">
					<c:set var="rowSpanStart2" value='${lineCount}' />
					<c:set var="rowSpanSize2" value='0' />
					<c:forEach var="pkgInventoryDetailComponentBean2" items="${pkgInventoryDetailItemBean2.componentCollection}" varStatus="pkgInventoryDetailComponentBeanStatus2">
						<c:set var="rowSpanSize" value='${rowSpanSize + 1}' />
						rowSpanMap[${lineCount}] =  0;
						<c:set var="rowSpanSize2" value='${rowSpanSize2 + 1}' />
						rowSpanLvl2Map[${lineCount}] =  0;
				         rowSpanClassMap[${lineCount}] = ${rowSpanCount % 2};	
				         <c:set var="lineCount" value='${lineCount+1}'/>
					</c:forEach>
					rowSpanLvl2Map[${rowSpanStart2}] =  ${rowSpanSize2};
				 </c:forEach>
					rowSpanMap[${rowSpanStart}] =  ${rowSpanSize};
					<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
			</c:forEach>
			 
			// -->
			</script>
		</c:otherwise>
	</c:choose>

<!-- Search results end -->


<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${fn:length(pkgInventoryDetailWebPrInventoryBeanCollection)}"/>" type="hidden"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>	 
<input name="showPlots" id="showPlots" value="<c:out value="${showPlots}"/>" type="hidden"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<tcmis:saveRequestParameter/> 
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>