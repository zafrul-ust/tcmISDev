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
<script type="text/javascript" src="/js/client/catalog/usageimportinforesults.js"></script>

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
    <fmt:message key="usageImportInfo"/>
</title>
<script language="JavaScript" type="text/javascript">
<!--

var rowSpanMap = new Array();
var rowSpanClassMap = new Array();
var rowSpanCols = [0,1,2,3,4,5,6,7,8,11,12,13];
var rowSpanLvl2Map = new Array();
var rowSpanLvl2Cols = [19,20];
<c:set var="showEmissionPoint" value="${tcmis:isFeatureReleased(personnelBean,'ShowFugitiveCategory', param.facilityId)}"/>

<c:if test="${showEmissionPoint}">
rowSpanCols = [0,1,2,3,4,5,6,7,8,9,11,12,13];
</c:if>

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
    partno:'<fmt:message key="label.partno"/>',
    msds:'<fmt:message key="label.msds"/>',
    lbsuom:'<fmt:message key="label.lbsuom"/>',
    mustbeanumber:'<fmt:message key="label.mustbeanumber"/>',
    deletepart:'<fmt:message key="label.deletepart"/>',
    addmsds:'<fmt:message key="searchmsds.label.addmsds"/>',
    deletemsds:'<fmt:message key="label.deletemsds"/>',
    partnoandmsdsexist:'<fmt:message key="label.partnoandmsdsexist"/>',
    verifyDelete:"<fmt:message key="label.verifydelete"><fmt:param><fmt:message key="label.part"/></fmt:param></fmt:message>",
    decimal:'<fmt:message key="errors.float"/>',
    submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"
};

var unitOfMeasure = [
    <c:if test="${empty uomColl}">
    {
          text:  '',
          value: ''
    }
    </c:if>
    <c:forEach var="uomBean" items="${uomColl}" varStatus="status">
    <c:if test="${ status.index !=0 }">,</c:if>
        {
          text:   '${status.current.unitOfMeasure}',
          value: '${status.current.unitOfMeasure}'
        }
    </c:forEach>
]; 

var vocetFugitiveCatId = [
	{
          text:  '',
          value: ''
    }
    <c:forEach var="vFCBean" items="${vocetFugitiveCategoryColl}" varStatus="status">
 <%--   <c:if test="${ status.index !=0 }">,</c:if> --%>
        ,{
          text:   '${status.current.vocetFugitiveCatName}',
          value: '${status.current.vocetFugitiveCatId}'
        }
    </c:forEach>
]; 

var gridConfig = {
	divName:'usageImportConversionBean', // the div id to contain the grid.
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

var tierIIStorage = new Array(
{value:'', text: '<fmt:message key="label.pleaseselect"/>'}
<c:forEach var="bean" items="${vvUnidocsStorageContainerBeanColl}" varStatus="unidocsStorageStatus">
	, {value:'${bean.unidocsStorageContainer}', text: '<tcmis:jsReplace value="${bean.description}" processMultiLines="false" />'}
</c:forEach>
);

var tierIITemperature = new Array(
{value:'', text: '<fmt:message key="label.pleaseselect"/>'}
<c:forEach var="bean" items="${vvTierIITemperatureCodeBeanColl}" varStatus="vvTierIITemperatureCodeStatus">
, {value:'${bean.tierIITemperatureCode}', text: '<tcmis:jsReplace value="${bean.tierIITemperature}" processMultiLines="false" />'}
</c:forEach>
);

var purchasingMethodId = new Array(
		{value:'', text: '<fmt:message key="label.pleaseselect"/>'}
		<c:forEach var="purchasingMethodBean" items="${purchasingMethodBeanColl}" varStatus="purchasingMethodStatus">
		, {value:'${purchasingMethodBean.purchasingMethodId}', text: '${purchasingMethodBean.purchasingMethodName}'}
		</c:forEach>
		);

var config = [
{
  	columnId:"permission"
},
{
  	columnId:"partNumberPermission"
},
{
  	columnId:"unitOfMeasurePermission"
},
{
  	columnId:"poundsPerUnitPermission"
},
{
  	columnId:"commentsPermission"
},
{
  	columnId:"purchasingMethodIdPermission"
},
{
  	columnId:"vocetFugitiveCatIdPermission"
},
{ columnId:"customerMsdsDb"
},
{ columnId:"partNumber",
  columnName:'<fmt:message key="label.partno"/>',
  onChange:checkUnique,
  type:'hed',    // input field
  align:'left',
  sorting:'int',
  width:8, // the width of this cell
  size:6,  // the size of the input field
  maxlength:20,
  permission:true
},
{ columnId:"vocetFugitiveCatId"
  <c:if test="${showEmissionPoint}">,
  columnName:'<fmt:message key="label.fugitivecategory"/>',
  onChange:fugitiveCategoryChanged,
  type:'hcoro',
  tooltip:true,
  width:10
  </c:if>
},
{
  	columnId:"customerMsdsOrMixtureNoDisplayPermission"
},
{ columnId:"customerMsdsOrMixtureNoDisplay",
  columnName:'<fmt:message key="label.msds"/>',
//  onChange:checkUnique,
  type:'hed',    // input field
  align:'left',
  sorting:'int',
  width:6, // the width of this cell
  size:4,  // the size of the input field
  maxlength:10,
  permission:true
},
{ columnId:"unitOfMeasure",
  columnName:'<fmt:message key="label.uom"/>',
  onChange:markChanged,
  type:'hcoro',
  tooltip:true,
  width:6
},
{ columnId:"poundsPerUnit",
  columnName:'<fmt:message key="label.lbsuom"/>',
  onChange:markChanged,
  type:'hed',    // input field
  align:'right',
  sorting:'int',
  width:7, // the width of this cell
  size:4,  // the size of the input field
  maxlength:10 // the maxlength of the input field 
},
{ columnId:"packaging"
 <c:if test="${param.includeTcmisCatalogParts == 'Y'}">
  ,
  columnName:'<fmt:message key="label.packaging"/>',
  width:10
  </c:if>
},
{ columnId:"customerMsdsNumber",
  columnName:'<fmt:message key="label.compmsds"/>',
  width:5
},
{ columnId:"materialId",
  columnName:'<fmt:message key="label.materialid"/>',
  width:5
},
{ columnId:"materialDesc",
  columnName:'<fmt:message key="label.tradename"/>',
  width:15,
  tooltip:true
},
{ columnId:"approvalCode",
  columnName:"<fmt:message key="label.approvalcode"/>"
},
{ columnId:"tierIIStoragePermission"
},
{ columnId:"tierIITemperaturePermission"
},
{ columnId: "tierIIStorage", 
  columnName:'<fmt:message key="label.tieriistoragemethod"/>', 
  attachHeader:'<fmt:message key="label.container"/>', 
  type:"hcoro", 
  width:10, 
  onChange:markChanged
},
{ columnId: "tierIIPressure",
  columnName:'#cspan', 
  attachHeader:'<fmt:message key="label.pressure"/>', 
  align:"center",width:10
},
{ columnId: "tierIITemperature", 
  columnName:'#cspan', 
  attachHeader:'<fmt:message key="label.temperature"/>', 
  type:"hcoro", 
  align:"center",
  width:10, 
  onChange:markChanged
},
{
  	columnId:"comments",
  	columnName:'<fmt:message key="label.comments"/>',
  	type:"txt",
    tooltip:"Y",
    width:10
},
{ columnId:"lastModifiedByName",
  columnName:'<fmt:message key="label.lastUpdatedBy"/>',
  width:10
},
{ columnId:"createdByName",
  columnName:'<fmt:message key="label.createdby"/>',
  width:10,
  tooltip:true
},
{ columnId:"purchasingMethodId",
  columnName:'<fmt:message key="label.purchasingmethod"/>',
  type:"hcoro", 
  onChange:markChanged,
  width:10
},
{ columnId:"companyId"
},
{ columnId:"facilityId"
},
{ columnId:"originalPartNumber"
},
{ columnId:"originalCustomerMsdsOrMixtureNo"
},
{ columnId:"originalComments"
},
{ columnId:"customerMsdsOrMixtureNo"
},
{ columnId:"status"
},
{ columnId:"catalogCompanyId"
},
{ columnId:"catalogId"
},
{ columnId:"updateStatus"
},
{ columnId:"fugitiveCategoryStatus"
}
];	

function checkUnique(passedBackRowId) {
	row = selectedRowId; 
	if(row == null || typeof(row) == 'undefined')
		row = passedBackRowId;
	if(cellValue(row, "partNumber").trim().length > 0) {
		var loc = "usageimportinforesults.do?uAction=dataDupCheck&facilityId="+$v("facilityId")+
			"&rowId="+row+
			"&customerMsdsDb="+cellValue(row, "customerMsdsDb")+
			"&customerMsdsOrMixtureNo="+cellValue(row, "customerMsdsOrMixtureNoDisplay")+
			"&partNumber="+cellValue(row, "partNumber")+"&callBack=showAlert";
		callToServer(loc);
	}
}

function showAlert(rowId,count)
{
   if (count > 0*1)  {
   	 alert(messagesData.partnoandmsdsexist);
   	 
   	 beanGrid.selectRowById(rowId,null,false,false);
   	 
   	 selectedRowId = rowId;

	 $("customerMsdsOrMixtureNoDisplay"+rowId).value = "";
   	 beanGrid.cells(rowId,beanGrid.getColIndexById("customerMsdsOrMixtureNo")).setValue("");
	 beanGrid.cells(rowId,beanGrid.getColIndexById("materialId")).setValue("");
	 beanGrid.cells(rowId,beanGrid.getColIndexById("tradeName")).setValue("");  
  }
}
   

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/usageimportinforesults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
 ${tcmISError}<br/>
<c:forEach var="tcmisError" items="${tcmISErrors}">
  ${tcmisError}<br/>
</c:forEach>
<c:if test="${param.maxData == fn:length(usageImportConversionBeanColl)}">
 <fmt:message key="label.maxdata">
  <fmt:param value="${param.maxData}"/>
 </fmt:message>
</c:if>
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${empty tcmISErrors and empty tcmISError}">
     <c:choose>
      <c:when test="${param.maxData == fn:length(usageImportConversionBeanColl)}">
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

<div id="usageImportConversionBean" style="width:100%;height:400px;" style="display: none;"></div>

 <c:set var="pagePermission" value='N'/>
 <c:set var="showUpdateLink" value='N'/>
<tcmis:facilityPermission indicator="true" userGroupId="ExternalDataImport" facilityId="${param.facilityId}">
 <c:set var="pagePermission" value='Y'/>
 <c:set var="showUpdateLink" value='Y'/>
</tcmis:facilityPermission>


<script language="JavaScript" type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>

  var jsonMainData = {
  rows:[
  <c:forEach var="p" items="${usageImportConversionBeanColl}" varStatus="status">
	    
	    <c:set var="tierIIPermission" value='N'/>
	    <c:set var="unitOfMeasure" value=''/>
	    <c:set var="poundsPerUnit" value=''/>

		<c:if test="${p.status != 'TCMIS'}"> 
		    <c:set var="unitOfMeasure" value='${p.unitOfMeasure}'/>
		    <c:set var="poundsPerUnit"><fmt:formatNumber value="${p.poundsPerUnit}"  pattern="${totalcurrencyformat}"></fmt:formatNumber></c:set>
			<c:if test="${pagePermission == 'Y'}"> 
				<c:set var="tierIIPermission" value='Y'/>
			</c:if>
		</c:if>
	    
		<c:if test="${status.index != 0 }">,</c:if>

        <c:set var="tmpKitMsds" value='${p.customerMsdsOrMixtureNo}'/>
	    <c:if test="${fn:contains(p.customerMsdsNumber,p.customerMsdsOrMixtureNo)}">
            <c:set var="tmpKitMsds" value=''/>
        </c:if>

        <c:set var="modifiedByName" value='${p.lastModifiedByName}'/>
		<c:if test="${p.status == 'TCMIS' }">
        	<c:set var="modifiedByName" value='${p.updatedByName}'/>
		</c:if>

        { id:${status.index +1},
	        data:[
	        		<c:if test="${p.status != 'TCMIS'}">'${pagePermission}','N','${pagePermission}','${pagePermission}','${pagePermission}','${pagePermission}','${pagePermission}',</c:if>
	        		<c:if test="${p.status == 'TCMIS'}">'Y','N','N','N','N','N','${pagePermission}',</c:if>
	                '${p.customerMsdsDb}','<tcmis:jsReplace value="${p.partNumber}"/>',
					'${p.vocetFugitiveCatId}',
	                'N',
                    '${tmpKitMsds}',                
					'${unitOfMeasure}',
					'${poundsPerUnit}',
                    '<tcmis:jsReplace value="${p.packaging}"/>',
	                '${p.customerMsdsNumber}',
	                '${p.materialId}',
                    '<tcmis:jsReplace value="${p.tradeName}"/>',
	                '<tcmis:jsReplace value="${p.approvalCode}" />',
	                '${tierIIPermission}','${tierIIPermission}',
	                '<c:out value="${fn:substring(p.tierIIStorage,0,1)}"/>',
	                '<c:out value="${fn:substring(p.tierIIPressure,2,fn:length(p.tierIIPressure))}"/>',
	                '<c:out value="${fn:substring(p.tierIITemperature,0,1)}"/>',
	                '<tcmis:jsReplace value="${p.comments}" processMultiLines="true"/>',
	                '<tcmis:jsReplace value="${modifiedByName}" />',
	                '<tcmis:jsReplace value="${p.createdByName}" />',
	                '${p.purchasingMethodId}',            
	                '${p.companyId}',
	                '${p.facilityId}','${p.partNumber}',
	                '<tcmis:jsReplace value="${p.customerMsdsOrMixtureNo}"/>',
	                '<tcmis:jsReplace value="${p.comments}" processMultiLines="true"/>',
	                '<tcmis:jsReplace value="${p.customerMsdsOrMixtureNo}"/>','${p.status}','${p.catalogCompanyId}','${p.catalogId}','',''
	        ]}
	 <c:set var="dataCount" value='${dataCount+1}'/> 
  </c:forEach>
  ]};

  
  <%-- determining rowspan --%>
  <c:set var="rowSpanCount" value='0' />
  	<%-- determining rowspan --%>
  	<c:forEach var="row" items="${usageImportConversionBeanColl}" varStatus="status">
  		<c:set var="currentKey" value='${row.companyId}|${row.facilityId}|${row.customerMsdsDb}|${row.partNumber}' />
  		<c:set var="currentLvl2Key" value='${row.customerMsdsOrMixtureNo}' />
  		<c:choose>
  			<c:when test="${status.first}">
  				<c:set var="rowSpanStart" value='0' />
  				<c:set var="rowSpanCount" value='1' />
  				<c:set var="prevSpanCount" value="${rowSpanCount}"/>
  				rowSpanMap[0] = 1;
  				rowSpanClassMap[0] = 1;
  				
  				rowSpanLvl2Map[0] = 1;
  				<c:set var="rowSpan2Start" value='0' />
  				<c:set var="rowSpan2Count" value='1' />
  				<c:set var="prevSpan2Count" value="${rowSpan2Count}"/>
  			</c:when>
  			<c:when test="${currentKey == previousKey}">
  				rowSpanMap[${rowSpanStart}]++;
  				rowSpanMap[${status.index}] = 0;
  				rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
  				
  					<c:choose>
  						<c:when test="${currentLvl2Key eq previousLvl2Key}">
  							//${status.index} eq
  							rowSpanLvl2Map[${status.index}] = 0;
  							rowSpanLvl2Map[${rowSpan2Start}]++;
  						</c:when>
  						<c:otherwise>
  							//${status.index} nq
  			  				<c:set var="rowSpan2Count" value='${rowSpan2Count + 1}' />
  			  				<c:set var="rowSpan2Start" value='${status.index}' />
  							rowSpanLvl2Map[${status.index}]=1;
  						</c:otherwise>
  					</c:choose>
  			</c:when>
  			<c:otherwise>
  				<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
  				<c:set var="rowSpanStart" value='${status.index}' />
  				rowSpanMap[${status.index}] = 1;
  				rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};


  				<c:set var="rowSpan2Count" value='${rowSpan2Count + 1}' />
  				<c:set var="rowSpan2Start" value='${status.index}' />
  				//${status.index} nq
				rowSpanLvl2Map[${status.index}]=1;
  			</c:otherwise>
  		</c:choose>
		<c:set var="previousLvl2Key" value='${currentLvl2Key}' />
  		<c:set var="previousKey" value='${currentKey}' />
  	</c:forEach> 
																							

// -->
</script>

 <!-- Search results end --> 

    <!-- Hidden element start --> 
    <div id="hiddenElements" style="display: none;">    			
		<input name="totalLines" id="totalLines" value="<c:out value="${dataCount+1}"/>" type="hidden">
        <input name="uAction" id="uAction" value="" type="hidden"> 
        <input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden">
        <input name="createdBy" id="createdBy" value="${param.createdBy}" type="hidden">
        <input name="lastModifiedBy" id="lastModifiedBy" value="${param.lastModifiedBy}" type="hidden">
        <input name="searchField" id="searchField" value="${param.searchField}" type="hidden">
        <input name="searchMode" id="searchMode" value="${param.searchMode}" type="hidden">
        <input name="searchArgument" id="searchArgument" value="${param.searchArgument}" type="hidden">
        <input name="searchField" id="searchField" value="${param.searchField}" type="hidden">
        <input name="createdFromDate" id="createdFromDate" value="${param.createdFromDate}" type="hidden">
        <input name="createdToDate" id="createdToDate" value="${param.createdToDate}" type="hidden">
        <input name="includeTcmisCatalogParts" id="includeTcmisCatalogParts" value="${param.includeTcmisCatalogParts}" type="hidden">
        <input name="selectedCompanyId" id="selectedCompanyId" value="" type="hidden"> 
        <input name="selectedCustomerMsdsDb" id="selectedCustomerMsdsDb" value="" type="hidden"> 
        <input name="selectedPartNumber" id="selectedPartNumber" value="" type="hidden"> 
        <input name="selectedMsdsNo" id="selectedMsdsNo" value="" type="hidden"> 
        <input name="minHeight" id="minHeight" type="hidden" value="100">
        <input name="maxData" id="maxData" type="hidden" value="${param.maxData}">
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