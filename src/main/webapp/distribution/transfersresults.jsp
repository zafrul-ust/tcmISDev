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
<!--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/distribution/transfers.js"></script>

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
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="label.transfers"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>",
pleasemakeselection:"<fmt:message key="label.pleasemakeselection"/>"};

var gridConfig = {
	divName:'transferRequestReportViewBean', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
	onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:selectRightClick   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
};

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
var config = [
 <%-- {
  	columnId:"sourceOpsCompanyId",
  	columnName:'<fmt:message key="label.source"/>',
  	attachHeader:'<fmt:message key="label.operatingentity"/>',
    tooltip:"Y",
    width:8
  },
  {
  	columnId:"sourceHubName",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.hub"/>',
    tooltip:"Y"
  },
  {
  	columnId:"sourceInventoryGroup",
  	columnName:'#cspan',
  	attachHeader:'<tcmis:jsReplace value="${inventorygroup}"/>',
    tooltip:"Y",
    width:12
  },
  {
  	columnId:"destOperatingEntityName",
  	columnName:'<fmt:message key="label.destination"/>',
  	attachHeader:'<fmt:message key="label.operatingentity"/>',
    tooltip:"Y",
    width:8
  },
  {
  	columnId:"destinationHubName",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.hub"/>',
    tooltip:"Y"
  },--%>
  {
  	columnId:"sourceInvGroupName",
  	columnName:'<fmt:message key="label.source"/>',
  	attachHeader:'<tcmis:jsReplace value="${inventorygroup}"/>',
    tooltip:"Y",
    width:12
  },
  {
  	columnId:"destinationInventoryGroup",
  	columnName:'<fmt:message key="label.destination"/>',
  	attachHeader:'<tcmis:jsReplace value="${inventorygroup}"/>',
    tooltip:"Y",
    width:12
  },
  { columnId:"okPermission"
  },
  { columnId:"ok",
    columnName:'<fmt:message key="label.release"/>',
    permission:true,
    type:'hchstatus',
    width:4
  },
  { columnId:"okClosePermission"
  },
  { columnId:"okClose",
    columnName:'<fmt:message key="label.close"/>',
    permission:true,
    type:'hchstatus',
    width:3
  },
  {
  	columnId:"itemId",
  	columnName:'<fmt:message key="label.item"/>',
    width:5
  },
  {
  	columnId:"itemDesc",
  	columnName:'<fmt:message key="label.desc"/>',
    tooltip:"Y",
    width:20
  },
  {
  	columnId:"transferRequestId",
  	columnName:'<fmt:message key="label.transferrequestid"/>',
    width:6
  },

  {
  	columnId:"rcptQualityHoldSpec",
  	columnName:'<fmt:message key="label.qualityhold"/>',
  	attachHeader:'<fmt:message key="label.spec"/>',
  	tooltip:"Y",
  	width:3
  }
  ,
  {
  	columnId:"rcptQualityHoldShelfLife",
	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.sl"/>',
  	width:3
  },
  
  {
  	columnId:"customerName",
  	columnName:'<fmt:message key="label.customer"/>',
    tooltip:"Y",
    width:10
  },
  {
  	columnId:"distCustomerPartList",
  	columnName:'<fmt:message key="label.customerpartnumber"/>',
    tooltip:"Y",
    width:10
  },
  {
  	columnId:"quantityNeeded",
  	columnName:'<fmt:message key="label.qtyneeded"/>',
    width:4,
    sorting:'int'
  },
  {
	columnId:"totalQuantityShipped"  
  },
  {
  	columnId:"totalQuantityReceived",
  	columnName:'<fmt:message key="label.qtyreceived"/>',
  	width:5,
  	sorting:'int'
  },
  {
  	columnId:"totalQuantityInTransit",
  	columnName:'<fmt:message key="label.qtyintransit"/>',
  	width:4,
  	sorting:'int'
  } ,
  {
  	columnId:"carrier",
  	columnName:'<fmt:message key="label.carrier"/>',
    tooltip:"Y",
    width:10
  },
  {
  	columnId:"trackingNumber",
  	columnName:'<fmt:message key="label.trackingnumber"/>',
    tooltip:"Y",
    width:10
  },
  {
  	columnId:"companyId"
  },
  {
  	columnId:"sourceOpsCompanyId"
  },
  {
  	columnId:"itemDesc"
  },
  {
  	columnId:"sourceInventoryGroup"
  },
  {
  	columnId:"sourceOpsEntityId"
  },
  {
	columnId:"requestDate"
  },
  {
  	columnId:"releasedByName"
  },
  {
	columnId:"rcptQualHoldSlSetDate"
  },
  {
	columnId:"rcptQualHoldSpecSetDate"
  },
  {
	columnId:"releasedBy"
  },
  {
	columnId:"releaseDate"
  },
  {
	columnId:"rcptQualityHoldNote"
  },
  {
	columnId:"catalogId"
  },
  {
	columnId:"catPartNo"
  },
  {
	columnId:"partGroupNo"
  },
  {
	columnId:"catalogCompanyId"
  },
  {
	columnId:"remainingShelfLifePercent"
  },
  {
	columnId:"specList"
  },
  {
	columnId:"specListConcat"
  },
  {
	columnId:"specDetailConcat"
  },
  {
	columnId:"specLibraryConcat"
  },
  {
	columnId:"specCocConcat"
  },
  {
	columnId:"specCoaConcat"
  }  
  ];

with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    	aI("text=<fmt:message key="label.showallocatableig"/>;url=javascript:allocationPopup('IG');");
	    aI("text=<fmt:message key="label.showallocatableregion"/>;url=javascript:allocationPopup('REGION');");
	    aI("text=<fmt:message key="label.showallocatableglobal"/>;url=javascript:allocationPopup('GLOBAL');");
}

drawMenus();

var showReleaseLinks = false;
var showCloseLinks = false;
function displayUpdateLinks() {
	if(showReleaseLinks && showCloseLinks) {
		parent.$("mainUpdateLinks").style["display"] = "";
		parent.$("updateResultLink").style["display"] = "";
		parent.$("updatePipeLine").style["display"] = "";
		parent.$("updateResultLink2").style["display"] = "";
	} else if(showReleaseLinks) {
		parent.$("mainUpdateLinks").style["display"] = "";
		parent.$("updateResultLink").style["display"] = "";
		parent.$("updatePipeLine").style["display"] = "none";
		parent.$("updateResultLink2").style["display"] = "none";
	} else if(showCloseLinks) {
		parent.$("mainUpdateLinks").style["display"] = "";
		parent.$("updateResultLink").style["display"] = "none";
		parent.$("updatePipeLine").style["display"] = "none";
		parent.$("updateResultLink2").style["display"] = "";
	} else {
		parent.$("mainUpdateLinks").style["display"] = "none";
	}
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig); displayUpdateLinks();">

<tcmis:form action="/transfersresults.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">
<div class="backGroundContent">
<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty transferRequestReportViewCollection}" >
<div id="transferRequestReportViewBean" style="width:100%;height:400px;" style="display: none;"></div>
<!-- Search results start -->
<script type="text/javascript">
<!--
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
<c:set var="oneHoldReleaseAvailable" value='N' />
<c:set var="toBeClosesTransferExist" value='N' />
/*Storing the data to be displayed in a JSON object array.*/

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${transferRequestReportViewCollection}" varStatus="status">
<fmt:formatDate var="fmtRequiredDate" value="${bean.requestDate}" pattern="${dateFormatPattern}"/>
<c:set var="rcptQualityHoldSpec" value='' />
<c:set var="rcptQualityHoldShelfLife" value='' />

<c:set var="okPermission" value='N' />
<c:set var="okClosePermission" value='N' />

<tcmis:inventoryGroupPermission indicator="true" userGroupId="releaseOrder" inventoryGroup="${bean.sourceInventoryGroup}">
<c:if test="${bean.rcptQualityHoldSpec eq 'Y' || bean.rcptQualityHoldShelfLife eq 'Y'}">
	<c:set var="okPermission" value='Y' />
	<c:set var="oneHoldReleaseAvailable" value='Y' />
</c:if>
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="OrderMaintenance" inventoryGroup="${bean.sourceInventoryGroup}">
<c:if test="${bean.status eq 'Open'}">
	<c:set var="okClosePermission" value='Y'/>
	<c:set var="toBeClosesTransferExist" value='Y' />
</c:if>
</tcmis:inventoryGroupPermission>

<c:if test="${bean.rcptQualityHoldSpec eq 'Y'}"><c:set var="rcptQualityHoldSpec" value='X' /></c:if>
<c:if test="${bean.rcptQualityHoldShelfLife eq 'Y'}"><c:set var="rcptQualityHoldShelfLife" value='X' /></c:if>
<c:if test="${status.index > 0}">,</c:if>
{ id:${status.index +1},
 data:[
  '<tcmis:jsReplace value="${bean.sourceInvGroupName}" />',
  '<tcmis:jsReplace value="${bean.destinationInvGroupName}" />',  
  '${okPermission}',
  '',
  '${okClosePermission}',
  '',
  '${bean.itemId}',
  '<tcmis:jsReplace value="${bean.itemDesc}" processMultiLines="true"/>',
  '${bean.transferRequestId}',
  '${rcptQualityHoldSpec}',
  '${rcptQualityHoldShelfLife}',
  '<tcmis:jsReplace value="${bean.customerName}" />',  
  '<tcmis:jsReplace value="${bean.distCustomerPartList}" processMultiLines="true"/>',
  '${bean.quantityNeeded}',
  '${bean.quantityShipped}',
  '${bean.quantityReceived}',
  '${bean.quantityInTransit}',
  '<tcmis:jsReplace value="${bean.carrierName}" processMultiLines="true"/>',
  '${bean.trackingNumber}','${bean.companyId}','${bean.sourceOpsCompanyId}',
  '<tcmis:jsReplace value="${bean.itemDesc}" processMultiLines="true"/>','${bean.sourceInventoryGroup}','${bean.sourceOpsEntityId}','${fmtRequiredDate}',
  '${bean.releasedByName}',
  '<fmt:formatDate value="${bean.rcptQualHoldSlSetDate}" pattern="${dateFormatPattern}"/>',
  '<fmt:formatDate value="${bean.rcptQualHoldSpecSetDate}" pattern="${dateFormatPattern}"/>',
  '${bean.releasedBy}',
  '<fmt:formatDate value="${bean.releaseDate}" pattern="${dateFormatPattern}"/>',
  '${bean.rcptQualityHoldNote}',
  '${bean.catalogId}',
  '${bean.catPartNo}',
  '${bean.partGroupNo}',
  '${bean.catalogCompanyId}',
  '${bean.remainingShelfLifePercent}',
  '${bean.specList}',
  '${bean.specListConcat}',
  '${bean.specDetailConcat}',
  '${bean.specLibraryConcat}',
  '${bean.specCocConcat}',
  '${bean.specCoaConcat}'
  ]
}

 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
//-->
</script>

</c:if>

<c:if test="${empty transferRequestReportViewCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>

<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input type="hidden" name="uAction" id="uAction" value=""/>
<input type="hidden" name="sourceEntities" id="sourceEntities" value="${param.sourceEntities}"/>
<input type="hidden" name="destinationEntities" id="destinationEntities" value="${param.destinationEntities}"/>
<input type="hidden" name="sourceOpsEntityId" id="sourceOpsEntityId" value="${param.sourceOpsEntityId}"/>
<input type="hidden" name="destinationOpsEntityId" id="destinationOpsEntityId" value="${param.destinationOpsEntityId}"/>
<input type="hidden" name="searchField" id="searchField" value="${param.searchField}"/>
<input type="hidden" name="searchMode" id="searchMode" value="${param.searchMode}"/>
<input type="hidden" name="searchArgument" id="searchArgument" value="${param.searchArgument}"/>
<input type="hidden" name="sourceHub" id="sourceHub" value="${param.sourceHub}"/>
<input type="hidden" name="destinationHub" id="destinationHub" value="${param.destinationHub}"/>
<input type="hidden" name="toDate" id="toDate" value="${param.toDate}"/>
<input type="hidden" name="fromDate" id="fromDate" value="${param.fromDate}"/>
<input type="hidden" name="sourceInventoryGroup" id="sourceInventoryGroup" value="${param.sourceInventoryGroup}"/>
<input type="hidden" name="destinationInventoryGroup" id="destinationInventoryGroup" value="${param.destinationInventoryGroup}"/>
<input type="hidden" name="status" id="status" value="${param.status}"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
</div>

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
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

<c:if test="${oneHoldReleaseAvailable == 'Y'}">
	showReleaseLinks = true;
</c:if>

<c:if test="${toBeClosesTransferExist == 'Y'}">
	showCloseLinks = true;
</c:if>

//-->
</script>

</body>
</html:html>