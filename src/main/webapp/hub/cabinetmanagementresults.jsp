
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

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/cabinetmanagementresults.js"></script>

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
    <fmt:message key="cabinetmanagement.title"/>
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
				invalidChargeNumbers:"<fmt:message key="label.invalidchargenumbers"/>",
	 			viewMsds:"<fmt:message key="label.viewmsds"/>",
				revisedPromisedDate:"<fmt:message key="label.revisedpromisedate"/>"};

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set> 
<c:set var="itemid"><fmt:message key="label.itemid"/></c:set>
<c:set var="itemdescription"><fmt:message key="label.itemdescription"/></c:set>  
<c:set var="expediteage"><fmt:message key="label.expediteage"/></c:set>                
var config = [
  {
  	columnId:"permission"
  },
  {
    columnId:"applicationDesc",
	 columnName:'<fmt:message key="label.workarea"/>',
	 tooltip:"Y"
  },
  {
  	columnId:"cabinetName",
  	columnName:'<fmt:message key="label.cabinet"/>',
  	tooltip:"Y"
  },
  {
  	columnId:"binName",
  	columnName:'<fmt:message key="label.bin"/>',
  	tooltip:"Y"
  },
  {
  	columnId:"countType",
  	columnName:'<fmt:message key="label.counttype"/>',
  	type:'hcoro',
  	width:8
  },
  {
  	columnId:"showBinPartStatus",
  	columnName:'<fmt:message key="label.active"/>',
  	type:'hchstatus',
  	onChange:changeBinPartStatus,
    align:'center',
    width:4
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
  	columnId:"reorderPointStockingLevel",
  	columnName:'<fmt:message key="label.rpsl"/>'
  },
  {
    columnId:"leadTimeInDays",
    columnName:'<fmt:message key="label.leadtimeindays"/>',
    align:'right'
  },
  {
  	columnId:"itemId",
  	columnName:'<fmt:message key="label.item"/>',
  	sorting:'int',
    width:5
  },
  {
  	columnId:"materialDesc",
  	columnName:'<fmt:message key="label.description"/>',
  	tooltip:"Y",  
    width:15
  },
  {
  	columnId:"packaging",
  	columnName:'<fmt:message key="label.containersize"/>',
  	tooltip:"Y",
    width:15
  },
  {
  	columnId:"materialIdString",
  	columnName:'<fmt:message key="label.msdsnumber"/>'
  },
  {
    columnId:"oldCountType" 
  },
  {
    columnId:"applicationId" 
  },
  {
    columnId:"oldStatus" 
  },
  {
    columnId:"binId"
  },
  {
    columnId:"companyId" 
  },
  {
    columnId:"partGroupNo"
  },
  {
    columnId:"catalogCompanyId" 
  },
  {
    columnId:"status"
  },
  {
    columnId:"catalogId"
  },
  {
    columnId:"facilityId"
  },
  {
    columnId:"facilityName"
  },
  {
    columnId:"application"
  },
  {
    columnId:"useApplication"
  },
  {
    columnId:"binPartStatus"
  },
  {
    columnId:"oldBinPartStatus"
  },
  {
    columnId:"inventoryGroup"
  }
];

var countType = new  Array(
	{text:'<fmt:message key="label.receiptid"/>',value:'RECEIPT_ID'},
   {text:'<fmt:message key="label.itemid"/>',value:'ITEM_ID'}
);

with(milonic=new menuname("workAreaRightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
	 aI("text=<fmt:message key="label.createnewbin"/>;url=javascript:addBin();");
}

with(milonic=new menuname("partRightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    aI("text=<fmt:message key="label.setbinlevel"/>;url=javascript:changeMinMaxCabinet();");
}

with(milonic=new menuname("showEmpty")){
	style=submenuStyle;
	itemheight=17;
	//has to have at least one menu item, this is a place holder.
	//we will dynamically add items in javascript
	aI("text=");
}

drawMenus();

var map = null;
var lineMap = new Array();
var lineMap2 = new Array();
var lineMap3 = new Array();
            
// -->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">
<tcmis:form action="/cabinetmanagementresults.do" onsubmit="return submitFrameOnlyOnce();">

<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError }">
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

<div id="cabinetPartLevelViewBean" style="width:100%;height:400px;" style="display: none;"></div>

<script type="text/javascript">
<!--
<c:set var="showUpdateLink" value='Y'/>
<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty cabinetPartLevelViewBeanCollection}" >  
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="cabinetPartLevelViewBean" items="${cabinetPartLevelViewBeanCollection}" varStatus="status1">
<c:if test="${status1.index > 0}">,</c:if>

/* Please use this tag, tcmis:jsReplace, to escape special characters */
<tcmis:jsReplace var="materialDesc" value="${cabinetPartLevelViewBean.materialDesc}" processMultiLines="true" />
<tcmis:jsReplace var="packaging" value="${cabinetPartLevelViewBean.packaging}" processMultiLines="true" /> 


<c:set var="showBinPartStatus" value="false"/>
<c:if test="${cabinetPartLevelViewBean.binPartStatus == 'A'}">
	<c:set var="showBinPartStatus" value="true"/>
</c:if>

/*The row ID needs to start with 1 per their design.*/
{ id:${status1.index +1},
  data:['Y',
  '${cabinetPartLevelViewBean.useApplicationDesc}',
  '${cabinetPartLevelViewBean.cabinetName}','${cabinetPartLevelViewBean.binName}','${cabinetPartLevelViewBean.countType}',
   ${showBinPartStatus},'<tcmis:jsReplace value="${cabinetPartLevelViewBean.catalogDesc}" />','<tcmis:jsReplace value="${cabinetPartLevelViewBean.catPartNo}" processAmpersand="true" />',
  '${cabinetPartLevelViewBean.reorderPoint} / ${cabinetPartLevelViewBean.stockingLevel}','${cabinetPartLevelViewBean.leadTimeDays}',
  '${cabinetPartLevelViewBean.itemId}','${materialDesc}',
  '<tcmis:jsReplace value="${cabinetPartLevelViewBean.packaging}" processMultiLines="true"/>','${cabinetPartLevelViewBean.materialIdString}',
  '${cabinetPartLevelViewBean.countType}','${cabinetPartLevelViewBean.cabinetId}',
  '${cabinetPartLevelViewBean.binPartStatus}','${cabinetPartLevelViewBean.binId}',
  '${cabinetPartLevelViewBean.companyId}','${cabinetPartLevelViewBean.partGroupNo}','${cabinetPartLevelViewBean.catalogCompanyId}',
  '${cabinetPartLevelViewBean.binPartStatus}','${cabinetPartLevelViewBean.catalogId}','<tcmis:jsReplace value="${cabinetPartLevelViewBean.facilityId}" processAmpersand="true" />','<tcmis:jsReplace value="${cabinetPartLevelViewBean.facilityName}" processAmpersand="true" />',
  '${cabinetPartLevelViewBean.orderingApplication}','${cabinetPartLevelViewBean.useApplication}','${cabinetPartLevelViewBean.binPartStatus}',
  '${cabinetPartLevelViewBean.binPartStatus}','${cabinetPartLevelViewBean.inventoryGroup}'
  ]}  
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>

//-->  
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty cabinetPartLevelViewBeanCollection}">
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
    <c:set var="selectedCabinetIdArray" value='${paramValues.cabinetIdArray}'/>
    <c:forEach var="cabinetidarray" items="${paramValues.cabinetIdArray}">
			<input name="cabinetIdArray" id="cabinetIdArray" value="${cabinetidarray}" type="hidden"/>
    </c:forEach>
	<input name="branchPlant" id="branchPlant" value="<c:out value="${param.branchPlant}"/>" type="hidden">
	<input name="itemOrPart" id="itemOrPart" value="<c:out value="${param.itemOrPart}"/>" type="hidden">
	<input name="criterion" id="criterion" value="<c:out value="${param.criterion}"/>" type="hidden">
	<input name="criteria" id="criteria" value="<c:out value="${param.criteria}"/>" type="hidden">
	<input name="facilityId" id="facilityId" value="<c:out value="${param.facilityId}"/>" type="hidden">
	<input name="facilityName" id="facilityName" value="<c:out value="${param.facilityName}"/>" type="hidden">
	<input name="sortBy" id="sortBy" value="<c:out value="${param.sortBy}"/>" type="hidden">
	<input name="useApplication" id="useApplication" value="<c:out value="${param.useApplication}"/>" type="hidden">
	<input name="inactive" id="inactive" value="<c:out value="${param.inactive}"/>" type="hidden">
	<input name="sourcePage" id="sourcePage" type="hidden" value="cabinetManagement">
	<input name="cabinetId" id="cabinetId" type="hidden" value="">
	<input name="cabinetName" id="cabinetName" type="hidden" value="">
	<input name="companyId" id="companyId" type="hidden" value="">
	<input name="application" id="application" type="hidden" value="">
	<input name="catalogId" id="catalogId" type="hidden" value="">
	<input name="catalogDesc" id="catalogDesc" type="hidden" value="">
	<input name="catalogCompanyId" id="catalogCompanyId" type="hidden" value="">
	        
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->

    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>

</tcmis:form>

<c:set var="preFirstLevel" value=""/>
<c:set var="parCount" value="1"/>
<c:forEach var="tmpBean" items="${cabinetPartLevelViewBeanCollection}" varStatus="status">
	<c:set var="curFirstLevel">${status.current.cabinetId}${status.current.binId}${status.current.catPartNo}</c:set>
	<c:set var="curSecondLevel">secondLevel${status.current.cabinetId}${status.current.binId}${status.current.catPartNo}${status.current.itemId}</c:set>

	<script language="JavaScript" type="text/javascript">
	<!--
        <c:if test="${!(curFirstLevel eq preFirstLevel)}">
			<c:set var="parCount" value="${parCount+1}"/>
			<c:set var="preSecondLevel" value=""/>
			lineMap[${status.index}] = ${rowCountFirstLevel[curFirstLevel]} ;
			map = new Array();
		</c:if>
		<c:if test="${ !( curSecondLevel eq preSecondLevel)}">
			<c:set var="componentSize" value='${rowCountSecondLevel[curFirstLevel][curSecondLevel]}' />
			lineMap2[${status.index}] = ${componentSize} ;
		</c:if>
		lineMap3[${status.index}] = ${parCount}%2 ;
		map[map.length] =  ${status.index} ;
	// -->
	</script>

	<c:set var="preFirstLevel" value="${curFirstLevel}"/>
	<c:set var="preSecondLevel" value="${curSecondLevel}"/>
</c:forEach>

<script language="JavaScript" type="text/javascript">
<!--
var altAccountSysId = new Array(
<c:forEach var="prRulesViewBean" items="${prRulesViewCollection}" varStatus="status2">
   <c:if test="${status2.index > 0}">,</c:if>
   {
		id:'<tcmis:jsReplace value="${prRulesViewBean.accountSysId}"/>',
		label:'<tcmis:jsReplace value="${prRulesViewBean.accountSysLabel}"/>'
	}
</c:forEach>
);
// -->
</script>

	<iframe id="LabelPrintFrame" src="/blank.html" name="LabelPrintFrame" style="border:0px; width:0px; height:0px;"></iframe>
	<form name="LabelPrintForm" id="LabelPrintForm" method="post" action="/tcmIS/Hub/Cabinetlabel" target="LabelPrintFrame">
	</form>	

</body>
</html:html>