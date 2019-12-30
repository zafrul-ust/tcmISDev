<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>

<tcmis:gridFontSizeCss /> 
<%-- Add any other stylesheets you need for the page here --%>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>
<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<%--
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
--%>
<%-- For Calendar support --%>
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>


<%-- Add any other javascript you need for the page here --%>
<script src="/js/common/cabinet/clientcabinetlevel.js" language="JavaScript"></script>


<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>

<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>



<title>
<fmt:message key="label.partlevel"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
reorderPointInteger:"<fmt:message key="error.reorderpoint.integer"/>",
stockingLevelInteger:"<fmt:message key="error.stockinglevel.integer"/>",
leadTimeDaysInteger:"<fmt:message key="error.leadtimedays.integer"/>",
reorderPointRequired:"<fmt:message key="error.reorderpoint.required"/>",
stockingLevelRequired:"<fmt:message key="error.stockinglevel.required"/>",
reorderQuantityRequired:"<fmt:message key="error.reorderquantity.required"/>",
reorderQuantityInteger:"<fmt:message key="error.reorderquantity.integer"/>",
kanbanReorderQuantityInteger:"<fmt:message key="error.kanbanreorderquantity.integer"/>",
leadTimeDaysRequired:"<fmt:message key="error.leadtimedays.required"/>",
reorderPointGreaterThanZero:"<fmt:message key="label.reorderpointgreaterthanzero"/>",
reorderPointLessThanStockingLevel:"<fmt:message key="error.reorderpoint.lessthanstockinglevel"/>",
noChange:"<fmt:message key="error.nochange"/>"};
// -->
</script>

</head>

<body bgcolor="#ffffff" onload="loadLayoutWin3E('clientLevel');resultOnLoad();" onresize="resizeGrids();" onunload="closeThisWindow('${param.uAction}')" >
<tcmis:form action="/clientcabinetlevel.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<div id="searchFrameDiv" style="margin: 4px 4px 4px 4px;">
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td width="20%" class="optionTitleBoldRight" nowrap><fmt:message key="label.facility"/>:&nbsp;</td>
        <td width="80%" class="optionTitleLeft"><c:out value="${param.facilityName}" escapeXml="false"/></td>
      </tr>
      <tr>
        <td width="20%" class="optionTitleBoldRight" nowrap><fmt:message key="label.workarea"/>:&nbsp;</td>
        <td width="80%" class="optionTitleLeft"><c:out value="${param.applicationDesc}"/></td>
      </tr>
      <tr>
        <td width="20%" class="optionTitleBoldRight" nowrap><fmt:message key="label.cabinet"/>:&nbsp;</td>
        <td width="80%" class="optionTitleLeft"><c:out value="${param.cabinetName}"/></td>
      </tr>
    </table>
   <!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
</div>
<!-- searchFrameDiv Ends -->

<!-- Search results start -->

<div id="resultFrameDiv1" style="margin: 4px 4px 4px 4px;">
<!-- Error Messages Begins -->
<div class="spacerY">
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
<c:if test="${param.stockingLevel != null && param.reorderPoint != null && param.leadTimeDays != null&& requestScope['org.apache.struts.action.ERROR'] == null}">
<div id="successMessageArea" class="successMessages">
  <fmt:message key="cabinetlevel.label.successfulupdate"/>
</div>
</c:if>
</div>
</div>
<!-- Error Messages Ends -->
<table id="resultsMaskTable1" width="100%"  border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">    
    <div class="boxhead"> 
    <%-- boxhead Begins --%>
	  <div id="mainUpdateLinks" style="display: "> <%-- mainUpdateLinks Begins --%>
		<a href="#" onclick="submitUpdate(); "><fmt:message key="label.update"/></a>
      </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
	</div> <%-- boxhead Ends --%>
<div id="cabinetPartLevelViewBean" style="display: none;height:800px;"  ></div>

<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty cabinetPartLevelViewBeanCollection}" >

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="cBean" items="${cabinetPartLevelViewBeanCollection}" varStatus="status">

<c:if test="${status.index > 0}">,</c:if>

/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['Y',
  '${status.current.catPartNo}',
  '<tcmis:jsReplace value="${status.current.partDescription}" processMultiLines="true" />',
  '${status.current.reorderPoint}','${status.current.stockingLevel}','${status.current.reorderQuantity}',
  '${status.current.kanbanReorderQuantity}','${status.current.leadTimeDays}',
  '<fmt:formatDate value="${status.current.levelHoldEndDate}" pattern="${dateFormatPattern}"/>',
  '','${status.current.reorderPoint}','${status.current.stockingLevel}','${status.current.reorderQuantity}',
  '${status.current.kanbanReorderQuantity}','${status.current.leadTimeDays}',
  '<fmt:formatDate value="${status.current.levelHoldEndDate}" pattern="${dateFormatPattern}"/>',
  ,'${status.current.orderingApplication}' 	  
  ]}  
 <c:set var="dataCount" value='${dataCount+1}'/> 

 </c:forEach>
]};
</c:if>

var config = [
{ columnId:"permission"
},
{ columnId:"catPartNo",
  columnName:'<fmt:message key="label.partnumber"/>',
  tooltip:"Y",
  width:7
},	 
{ columnId:"partDescription",
  columnName:'<fmt:message key="label.description"/>',
  tooltip:"Y",
  width:25
},
{ columnId:"reorderPoint",
  columnName:'<fmt:message key="label.reorderpoint"/>',
  type:'hed',   
  align:'right',
  sorting:'int',
  width:5, // the width of this cell
  size:5,  // the size of the input field
  maxlength:16
},
{ columnId:"stockingLevel",
  columnName:'<fmt:message key="label.stockinglevel"/>',
  type:'hed',   
  align:'right',
  sorting:'int',
  width:5, // the width of this cell
  size:5,  // the size of the input field
  maxlength:16
},
{ columnId:"reorderQuantity",
  columnName:'<fmt:message key="label.reorderquantity"/>',
  type:'hed',
  align:'right',
  sorting:'int',
  width:5, // the width of this cell
  size:5,  // the size of the input field
  maxlength:16
},
{ columnId:"kanbanReorderQuantity",
  columnName:'<fmt:message key="label.kanbanreorderqty"/>',
  type:'hed',
  align:'right',
  sorting:'int',
  width:5, // the width of this cell
  size:5,  // the size of the input field
  maxlength:16
},
{
  columnId:"leadTimeDays",
  columnName:'<fmt:message key="label.leadtimeindays"/>',
  type:'hed',   
  align:'right',
  sorting:'int',
  width:5, // the width of this cell
  size:5,  // the size of the input field
  maxlength:16
},
{
  columnId:"levelHoldEndDate",
  columnName:'<fmt:message key="label.levelholdenddate"/>',
  type:'hcal',   
  align:'right',
  sorting:'int',
  width:8, // the width of this cell
  size:10,  // the size of the input field
  maxlength:16
},
{
  columnId:"remarks",
  columnName:'<fmt:message key="label.remarks"/>',
  type:'hed',   
  align:'right',
  sorting:'int',
  width:15, // the width of this cell
  size:35,  // the size of the input field
  maxlength:200
},
{
  columnId:"oldReorderPoint"
},
{
  columnId:"oldStockingLevel"
},
{
  columnId:"oldReorderQuantity"
},
{
  columnId:"oldKanbanReorderQuantity"
},
{
  columnId:"oldLeadTimeDays"
},
{
  columnId:"oldLevelHoldEndDate"
},
{
  columnId:"orderingApplication"
}


];

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

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>


<div id="resultFrameDiv2" style="margin: 4px 4px 4px 4px;">

<table id="resultsMaskTable2" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">    
    <div class="boxhead"> 
    <%-- boxhead Begins --%>
	  <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
		<span id="showlegendLink"><a href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a></span>
      </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
	</div> <%-- boxhead Ends --%>
<div id="cabinetPartLevelLogViewBean" style="display: none;height:1200px;" ></div>	
<script type="text/javascript">
<!--
<c:set var="hdataCount" value='${0}'/>
<c:if test="${!empty cabinetPartLevelLogViewBeanCollection}" >
var hjsonMainData = new Array();
var hjsonMainData = {
rows:[
<c:forEach var="cBean" items="${cabinetPartLevelLogViewBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

/* Please use this tag, fmt:formatDate, to formate your date to locale pattern */
<fmt:formatDate var="fmtdateModified" pattern="${dateTimeFormatPattern}" value="${status.current.dateModified}"/>

/* Please use this tag, tcmis:jsReplace, to escape special characters */
<tcmis:jsReplace var="modifiedByName" value="${status.current.modifiedByName}"  />

<fmt:formatDate var="oldLevelHoldEndDat" value="${status.current.oldLevelHoldEndDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="newLevelHoldEndDate" value="${status.current.newLevelHoldEndDate}" pattern="${dateFormatPattern}"/>

/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:[
  '${status.current.catPartNo}',
  '${fmtdateModified}','${modifiedByName}',
  '${status.current.oldReorderPoint}','${status.current.oldStockingLevel}','${status.current.oldReorderQuantity}',
  '${status.current.oldKanbanReorderQuantity}','${status.current.oldLeadTimeDays}','${oldLevelHoldEndDat}',
  '${status.current.newReorderPoint}','${status.current.newStockingLevel}','${status.current.newReorderQuantity}',
  '${status.current.newKanbanReorderQuantity}','${status.current.newLeadTimeDays}','${newLevelHoldEndDate}',
  '<tcmis:jsReplace value="${status.current.remarks}" processMultiLines="true" />','${status.current.dateModified.time}'
  ]}  
 <c:set var="hdataCount" value='${hdataCount+1}'/> 
 </c:forEach>
]};
</c:if>

var hconfig = [
{ columnId:"catPartNo",
  columnName:'<fmt:message key="label.changehistory"/>',
  attachHeader:'<fmt:message key="label.partnumber"/>',	
  tooltip:"Y",
  width:7
},
{ columnId:"dateModified",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.updateddate"/>',
  hiddenSortingColumn:'hiddendateModified',
  width:11,
  sorting:'int'
},
{ columnId:"modifiedByName",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.updatedby"/>',
  tooltip:"Y",
  width:8
},
{ columnId:"oldReorderPoint",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="cabinetlevel.label.oldreorderpoint"/>',
  sorting:'int',
  width:6
},
{ columnId:"oldStockingLevel",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="cabinetlevel.label.oldstockinglevel"/>',
  sorting:'int',
  width:6
},
{ columnId:"oldReorderQuantity",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.oldreorderquantity"/>',
  sorting:'int',
  width:6
},
{ columnId:"oldKanBanReorderQuantity",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.oldkanbanreorderquantity"/>',
  sorting:'int',
  width:6
},
{
  columnId:"oldLeadTimeDays",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="cabinetlevel.label.oldleadtimeindays"/>',
  sorting:'int',
  width:6
},
{
  columnId:"oldLevelHoldEndDate",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="cabinetlevel.label.oldlevelholdenddate"/>',
  sorting:'int',
  width:8
},
{ columnId:"newReorderPoint",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="cabinetlevel.label.newreorderpoint"/>',
  sorting:'int',
  width:6
},
{ columnId:"newStockingLevel",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="cabinetlevel.label.newstockinglevel"/>',
  sorting:'int',
  width:6
},
{ columnId:"newReorderQuantity",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.newreorderquantity"/>',
  sorting:'int',
  width:6
},
{ columnId:"newKanbanReorderQuantity",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.newkanbanreorderquantity"/>',
  sorting:'int',
  width:6
},
{
  columnId:"newLeadTimeDays",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="cabinetlevel.label.newleadtimeindays"/>',
  sorting:'int',
  width:6
},
{
  columnId:"newLevelHoldEndDate",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="cabinetlevel.label.newlevelholdenddate"/>',
  sorting:'int',
  width:8
},
{
  columnId:"remarks",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.remarks"/>',
  tooltip:"Y",
  width:10
},
{ 
  columnId:"hiddendateModified",
  sorting:'int'
}

];
//-->  
</script>
 
<!-- If the collection is empty say no data found -->
<c:if test="${empty cabinetPartLevelLogViewBeanCollection}">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
        <tr>
            <td width="100%">
                <fmt:message key="main.nodatafound"/>
            </td>
        </tr>
    </table>
</c:if>

  	  <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>

</div>
<!-- Search results end -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
<div id="hiddenElements" >
 <input name="uAction" id="uAction" type="hidden" value="">
 <input name="htotalLines" id="htotalLines" value="<c:out value="${hdataCount}"/>" type="hidden"/>
 <input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden"/>
 <input name="facilityName" id="facilityName" type="hidden" value="<c:out value="${param.facilityName}" escapeXml="false"/>">
 <input name="application" id="application" type="hidden" value="<c:out value="${param.application}"/>">
 <input name="applicationDesc" id="applicationDesc" type="hidden" value="<c:out value="${param.applicationDesc}"/>">
 <input name="cabinetName" id="cabinetName" type="hidden" value="<c:out value="${param.cabinetName}"/>">
 <input name="companyId" id="companyId" type="hidden" value="<c:out value="${param.companyId}"/>">
 <input name="catalogId" id="catalogId" type="hidden" value="<c:out value="${param.catalogId}"/>">
 <input name="partGroupNo" id="partGroupNo" type="hidden" value="<c:out value="${param.partGroupNo}"/>">
 <input name="catPartNo" id="catPartNo" type="hidden" value="<c:out value="${param.catPartNo}"/>">
 <input name="facilityId" id="facilityId" type="hidden" value="<c:out value="${param.facilityId}" escapeXml="false"/>">
 <input name="status" id="status" type="hidden" value="<c:out value="${param.status}"/>">
 <input name="branchPlant" id="branchPlant" type="hidden" value="<c:out value="${param.branchPlant}"/>">
 <input name="cabinetId" id="cabinetId" type="hidden" value="<c:out value="${param.cabinetId}"/>">
 <input name="reorderPoint" id="reorderPoint" type="hidden" value="">
 <input name="stockingLevel" id="stockingLevel" type="hidden" value="">
 <input name="reorderQuantity" id="reorderQuantity" type="hidden" value="">
 <input name="kanbanReorderQuantity" id="kanbanReorderQuantity" type="hidden" value="">
 <input name="leadTimeDays" id="leadTimeDays" type="hidden" value="">
 <input name="levelHoldEndDate" id="levelHoldEndDate" type="hidden" value="">
 <input name="remarks" id="remarks" type="hidden" value="">
 <input name="inventoryGroup" id="inventoryGroup" type="hidden" value="<c:out value="${param.inventoryGroup}"/>">
 <input name="itemId" id="itemId" type="hidden" value="<c:out value="${param.itemId}"/>">
 <input name="binId" id="binId" type="hidden" value="<c:out value="${param.binId}"/>">
<c:if test="${!empty cabinetPartLevelViewBeanCollection}" >
	<input name="orderingApplication" id="orderingApplication" type="hidden" value="${cabinetPartLevelViewBeanCollection[0].orderingApplication}">
	<input name="catalogCompanyId" id="catalogCompanyId" type="hidden" value="${cabinetPartLevelViewBeanCollection[0].catalogCompanyId}">
	<input name="oldReorderPoint" id="oldReorderPoint" type="hidden" value="${cabinetPartLevelViewBeanCollection[0].reorderPoint}">
	<input name="oldStockingLevel" id="oldStockingLevel" type="hidden" value="${cabinetPartLevelViewBeanCollection[0].stockingLevel}">
	<input name="oldReorderQuantity" id="oldReorderQuantity" type="hidden" value="${cabinetPartLevelViewBeanCollection[0].reorderQuantity}">
	<input name="oldKanbanReorderQuantity" id="oldKanbanReorderQuantity" type="hidden" value="${cabinetPartLevelViewBeanCollection[0].kanbanReorderQuantity}">
	<input name="oldLeadTimeDays" id="oldLeadTimeDays" type="hidden" value="${cabinetPartLevelViewBeanCollection[0].leadTimeDays}">
	<input name="oldLevelHoldEndDate" id="oldLevelHoldEndDate" type="hidden" value="<fmt:formatDate value="${cabinetPartLevelViewBeanCollection[0].levelHoldEndDate}" pattern="${dateFormatPattern}"/>">
    <input name="putAwayMethodOverride" id="putAwayMethodOverride" type="hidden" value="${cabinetPartLevelViewBeanCollection[0].putAwayMethodOverride}">
    <input name="dropShipOverride" id="dropShipOverride" type="hidden" value="${cabinetPartLevelViewBeanCollection[0].dropShipOverride}">
</c:if>
<!-- Popup Calendar input options for hcal column Type in the grid-->
<input type="hidden" name="blockBefore_levelHoldEndDate" id="blockBefore_levelHoldEndDate" value="<tcmis:getDateTag numberOfDaysFromToday="-1" datePattern="${dateFormatPattern}"/>"/>
<input type="hidden" name="blockAfter_levelHoldEndDate" id="blockAfter_levelHoldEndDate" value=""/>
<input type="hidden" name="blockBeforeExclude_levelHoldEndDate" id="blockBeforeExclude_levelHoldEndDate" value=""/>
<input type="hidden" name="blockAfterExclude_levelHoldEndDate" id="blockAfterExclude_levelHoldEndDate" value=""/>
<input type="hidden" name="inDefinite_levelHoldEndDate" id="inDefinite_levelHoldEndDate" value=""/>
</div>
<!-- Hidden element Ends -->

</div> <!-- close of contentArea -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>

