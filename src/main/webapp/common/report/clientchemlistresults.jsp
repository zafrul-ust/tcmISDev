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
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support for column type hcal-->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/report/clientchemlistresults.js"></script>

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
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />

<title>
<fmt:message key="label.listmanagement"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

var rowSpanMap = new Array();
var rowSpanClassMap = new Array();
var rowSpanCols = [0,1,2,3,4,5,6,10,11,12,13,14,15,16];

function addNewListchemical(chemInfo) {
    var ind = beangrid.getRowsNum();
    var rowid = ind*1+1;

	var thisrow = beangrid.addRow(rowid,"",ind);
	beangrid.selectRow(beangrid.getRowIndex(rowid),null,false,false);
	beangrid.cells(rowid,beangrid.getColIndexById("permission")).setValue('Y');
	beangrid.cells(rowid,beangrid.getColIndexById("delete")).setValue('N');
	beangrid.cells(rowid,beangrid.getColIndexById("partNo")).setValue(chemInfo.partNo);
    beangrid.cells(rowid,beangrid.getColIndexById("tradeName")).setValue(chemInfo.tradeName);
    beangrid.cells(rowid,beangrid.getColIndexById("customerMsdsOrMixtureNo")).setValue(chemInfo.customerMsdsOrMixtureNo);
	beangrid.cells(rowid,beangrid.getColIndexById("msdsUnitOfMeasure")).setValue(chemInfo.msdsUnitOfMeasure);
	beangrid.cells(rowid,beangrid.getColIndexById("partAmount")).setValue(chemInfo.partAmount);
	beangrid.cells(rowid,beangrid.getColIndexById("usageDate")).setValue(chemInfo.usageDate);
	beangrid.cells(rowid,beangrid.getColIndexById("totUsageInLbs")).setValue(''); //new column
	beangrid.cells(rowid,beangrid.getColIndexById("workAreaName")).setValue(chemInfo.workAreaName);
	beangrid.cells(rowid,beangrid.getColIndexById("purchaseSource")).setValue(chemInfo.purchaseSource);
	beangrid.cells(rowid,beangrid.getColIndexById("insertDate")).setValue(chemInfo.insertDate);
	beangrid.cells(rowid,beangrid.getColIndexById("insertedByName")).setValue(chemInfo.insertedByName);
	beangrid.cells(rowid,beangrid.getColIndexById("entryType")).setValue("Manual");//chemInfo.entryType);
	beangrid.cells(rowid,beangrid.getColIndexById("uploadSeqId")).setValue(chemInfo.uploadSeqId);
	beangrid.cells(rowid,beangrid.getColIndexById("dataLoadRowId")).setValue(chemInfo.dataLoadRowId);
	parent.$('uploadId').value = chemInfo.uploadSeqId;
	beangrid.scrollPage(thisrow.offsetTop);
}

function addListChemical(){
	openWinGeneric("clientchemadd.do?uAction=initial"+
			"&facilityId="+$v('facilityId')+
			"&application="+$v('workArea')+
			"&purchaseSource="+$v('source')+
			"&workAreaGroup="+$v('workAreaGroup')
			,
			"chemicaladd","700","200","yes","80","80");
}

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
open:"<fmt:message key="label.open"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

var config = [
 {   columnId:"permission"}
,{   columnId:"delete",  columnName:'<fmt:message key="label.delete"/>',type:"hchstatus", width:4 }
,{   columnId:"partNo",  columnName:'<fmt:message key="label.partno"/>' }
,{   columnId:"customerMsdsOrMixtureNo",  columnName:'<fmt:message key="label.msds"/>' }
,{   columnId:"usageDate",  columnName:'<fmt:message key="label.usagedate"/>' }
,{   columnId:"partAmount",  columnName:'<fmt:message key="label.quantity"/>' }
,{   columnId:"msdsUnitOfMeasure",  columnName:'<fmt:message key="label.unitofmeasure"/>' }
,{   columnId:"customerMsdsNumber",  columnName:'<fmt:message key="label.compmsds"/>' }
,{   columnId:"totUsageInLbs",  columnName:'<fmt:message key="label.totalusagepounds"/>',width:6 }
,{   columnId:"tradeName",  columnName:'<fmt:message key="label.tradename"/>' }
,{   columnId:"workAreaName",  columnName:'<fmt:message key="label.workarea"/>' }
,{   columnId:"purchaseSource",  columnName:'<fmt:message key="label.purchasesource"/>' }
,{   columnId:"insertDate",  columnName:'<fmt:message key="label.entrydate"/>' }
,{   columnId:"insertedByName",  columnName:'<fmt:message key="label.enteredby"/>' }
,{   columnId:"entryType",  columnName:'<fmt:message key="label.entrytype"/>' }
,{   columnId:"uploadSeqId",  columnName:'<fmt:message key="cyclecountresults.label.uploadid"/>' }
,{   columnId:"dataLoadRowId" }
];

var gridConfig = {
		divName:'listManagementViewBean', // the div id to contain the grid.
		beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beangrid', // the grid's name, as in beanGrid.attachEvent...
		config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
		submitDefault:true, // the fields in grid are defaulted to be submitted or not.
		noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
		variableHeight:false,
		onRowSelect: selectRow
	};

function submitUpdate() {

	 $("uAction").value = 'update';
	 parent.showPleaseWait();
	// if (beangrid != null)
	       beangrid.parentFormOnSubmit();
	// submitOnlyOnce();
	 document.genericForm.submit();
}


//-->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">
<tcmis:form action="/clientchemlistresults.do" onsubmit="return submitFrameOnlyOnce();">

<c:set var="hasPermission" value='N'/>
<tcmis:facilityPermission indicator="true" userGroupId="ExternalDataImport" facilityId="${param.facilityId}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
 <c:set var="hasPermission" value='Y'/>
</tcmis:facilityPermission>

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
//-->
</script>
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="listManagementViewBean" style="width:100%;height:500px;" style="display: none;"></div>
<c:set var="dataCount" value='${0}'/>
<script type="text/javascript">
<!--

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${listChemicalColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<fmt:formatDate var="usageDate" value="${bean.usageDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="insertDate" value="${bean.insertDate}" pattern="${dateFormatPattern}"/>
<c:set var="entryType">${bean.entryType}</c:set>
<c:if test="${bean.entryType == 'I'}">
	<c:set var="entryType"><fmt:message key="label.import"/></c:set>
</c:if>
<c:if test="${bean.entryType == 'M'}">
	<c:set var="entryType"><fmt:message key="label.manual"/></c:set>
</c:if>
<c:set var="tmpQuantity" value='${bean.msdsUnitAmount}'/>
<c:if test="${!empty bean.partAmount}">
    <c:set var="tmpQuantity" value='${bean.partAmount}'/>
</c:if>
<fmt:formatNumber var="usageLbDisplay" maxFractionDigits="6" minFractionDigits="2">${bean.usageLb}</fmt:formatNumber>
<c:set var="tmpKitMsds" value='${bean.customerMsdsOrMixtureNo}'/>
<c:if test="${bean.customerMsdsOrMixtureNo == bean.customerMsdsNumber}">
    <c:set var="tmpKitMsds" value=''/>
</c:if>
{id:${status.index +1},
 data:['${hasPermission}',
       'N',
       '${bean.partNo}',
       '${tmpKitMsds}',
       '${usageDate}',
       '${tmpQuantity}',
       '${bean.msdsUnitOfMeasure}',
       '${bean.customerMsdsNumber}',
       '${usageLbDisplay}',
       '${bean.tradeName}',
       '${bean.workAreaName}',
       '${bean.purchaseSource}',
       '${insertDate}',
       '${bean.insertedByName}',
       '${entryType}',
       '${bean.uploadSeqId}',
       '${bean.dataLoadRowId}'
]}

 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};

<%-- determining rowspan --%>
<c:set var="rowSpanCount" value='0' />
	<%-- determining rowspan --%>
	<c:forEach var="row" items="${listChemicalColl}" varStatus="status">
		<c:set var="currentKey" value='${row.partNo}|${row.customerMsdsOrMixtureNo}|${row.uploadSeqId}' />
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
 //-->
</script>

<!-- If the collection is empty say no data found -->
<%--<c:if test="${empty listChemicalColl}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>--%>

<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount+1}"/>" type="hidden"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input name="facilityId" id="facilityId" type="hidden" value="${param.facilityId}"/>
<input name="type" id="type" type="hidden" value="${param.type}"/>
<input name="workAreaGroup" id="workAreaGroup" type="hidden" value="${param.workAreaGroup}"/>
<input name="source" id="source" type="hidden" value="${param.source}"/>
<input name="workArea" id="workArea" type="hidden" value="${param.workArea}"/>
<input name="workAreaName" id="workAreaName" type="hidden" value="${param.workAreaName}"/>
<input name="uploadId" id="uploadId" type="hidden" value="${param.uploadId}"/>
<input name="partNo" id="partNo" type="hidden" value="${param.partNo}"/>
<input name="entryStartDate" id="entryStartDate" type="hidden" value="${param.entryStartDate}"/>
<input name="entryEndDate" id="entryEndDate" type="hidden" value="${param.entryEndDate}"/>
<input name="usageStartDate" id="usageStartDate" type="hidden" value="${param.usageStartDate}"/>
<input name="usageEndDate" id="usageEndDate" type="hidden" value="${param.usageEndDate}"/>
<input name="msdsNo" id="msdsNo" type="hidden" value="${param.msdsNo}"/>

<input name="listName" id="listName" type="hidden" value="${param.listName}"/>
<input name="listDescription" id="listDescription" type="hidden" value="${param.listDescription}"/>
<input type="hidden" name="companyId" id="companyId" value="${personnelBean.companyId}"/>
</div>

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>