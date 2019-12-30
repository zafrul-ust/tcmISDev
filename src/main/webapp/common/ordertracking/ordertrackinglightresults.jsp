<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/ordertracking/ordertrackinglightresults.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
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
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="ordertracking.label.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
<c:set var="homeCompanyLogin" value="${personnelBean.companyId == 'Radian'}"/>
homeCompanyLogin = ${homeCompanyLogin};

<c:set var="module">
 <tcmis:module/>
</c:set>

<c:set var="intcmIsApplication" value='N'/>
<c:set var="menuItemOvBeanCollection" value='${sessionScope.personnelBean.menuItemOvBeanCollection}'/>
<c:if test="${menuItemOvBeanCollection != null && !empty menuItemOvBeanCollection }">
    <c:set var="intcmIsApplication" value='Y'/>
</c:if>

with ( milonic=new menuname("orderTrackingMenu") ) {
 top="offset=2";
 style=contextStyle;
 margin=3
 aI("text=<fmt:message key="ordertracking.label.title"/>;url=javascript:doNothing();");
}

drawMenus();
// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
materialrequest:"<fmt:message key="label.materialrequest"/>",
engineeringevaluation:"<fmt:message key="buyorders.legend.engineeringevaluation"/>",
mrallocation:"<fmt:message key="ordertracking.label.mrallocation"/>",
mrlineallocation:"<fmt:message key="label.mrlineallocation"/>",
viewmr:"<fmt:message key="label.viewmr"/>",
mrlineschedule:"<fmt:message key="label.mrlineschedule"/>",
approvaldetail:"<fmt:message key="label.approvaldetail"/>",
vieweval:"<fmt:message key="label.vieweval"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
truncatemr:"<fmt:message key="label.truncatemr"/>"};

<%--
<c:set var="ShowQtyInCustomerUom" value="${tcmis:isFeatureReleased(personnelBean,'ShowQtyInCustomerUom', param.facilityId)}"/>
--%>
<c:set var="ShowQtyInCustomerUom" value="false"/>

<c:set var="workArea">
 <fmt:message key="label.workarea"/>
</c:set>

var config = [
{ columnId:"requestLineStatus",
  columnName:'<fmt:message key="label.status"/>',
  align:"left",
  width:6,
  tooltip:"Y"
},
{ columnId:"poNumber",
  columnName:'<fmt:message key="label.customerpo"/>',
  width:6,
  tooltip:"Y"
},
<tcmis:featureReleased feature="DisplayChargeNoOwnerSeqment" scope="ALL">
{ columnId:"program",
  columnName:'<fmt:message key="label.program"/>',
  width:6,
  tooltip:"Y"
},
</tcmis:featureReleased>

{ columnId:"requestorName",
  columnName:'<fmt:message key="label.requestor"/>',
  tooltip:"Y"
},
{ columnId:"inventoryGroup"
},
<c:choose>
	<c:when test="${homeCompanyLogin}">
		{ columnId:"companyName",
		  columnName:'<fmt:message key="label.company"/>',
		  width:6,
		  tooltip:"Y"
		},{ columnId:"inventoryGroupName",
		  columnName:'<fmt:message key="label.inventorygroup"/>',
		  width:6,
		  tooltip:"Y"
		},
	</c:when>
	<c:otherwise>
		{ columnId:"companyName",
		  columnName:'<fmt:message key="label.company"/>',
		  width:6,
		  tooltip:"Y"
		},
	</c:otherwise>
</c:choose>
{ columnId:"facilityName",
  columnName:'<fmt:message key="label.facility"/>',
  width:5,
  tooltip:"Y"
}, 
{ columnId:"applicationDesc",
  columnName:'<tcmis:jsReplace value="${workArea}"/>',
  tooltip:"Y"
},
{ columnId:"catalogDesc",
  columnName:'<fmt:message key="label.catalog"/>',
  width:5,
  tooltip:"Y"
},
{ columnId:"facPartNo",
  columnName:'<fmt:message key="label.partnum"/>',
  tooltip:"Y"
},
{ columnId:"partDescription",
  columnName:'<fmt:message key="label.partdesc"/>',
  width:16,
  tooltip:"Y"
},
{ columnId:"packaging",
  columnName:'<fmt:message key="label.packaging"/>',
  width:7,
  tooltip:"Y"
},
{ columnId:"orderType",
  columnName:'<fmt:message key="label.type"/>',
  align:'center',
  width:6
},
{ columnId:"mrLine",
  columnName:'<fmt:message key="label.mrline"/>',
  width:6
},
{ columnId:"notes",
  columnName:'<fmt:message key="label.notes"/>',
  width:5,
  tooltip:"Y"
},
{ columnId:"releaseDate",
  columnName:'<fmt:message key="label.released"/>',
  sorting:"int",
  hiddenSortingColumn:"hreleased",
  width:7
},
{ columnId:"requiredDatetime",
  columnName:'<fmt:message key="label.needed"/>',
  sorting:"int",
  hiddenSortingColumn:"hrequiredDatetime",
  width:7
},
{ columnId:"totalPickedDisplay",
  columnName:'<fmt:message key="label.picked"/>',
  sorting:"int",
  width:6,
  hiddenSortingColumn:"totalPicked"
},
{ columnId:"totalShipped",
  columnName:'<fmt:message key="label.delivered"/>',
  width:6,
  sorting:"int"
 
},
{ columnId:"pickedInUOM"	
	<c:if test="${ShowQtyInCustomerUom}">  
	,
	  columnName:'<fmt:message key="label.pickedinuom"/>',
	  width:6,
	  sorting:"int"
	</c:if> 
},
{ columnId:"uomQuantity"
	<c:if test="${ShowQtyInCustomerUom}">  
	,
	  columnName:'<fmt:message key="label.deliveredinuom"/>',
	  width:6,
	  sorting:"int"
	</c:if>
},
{ columnId:"unitOfSale"
	<c:if test="${ShowQtyInCustomerUom}">  
	  ,
	  columnName:'<fmt:message key="label.uom"/>',
	  width:6,
	  sorting:"int"
	</c:if>
},
{ columnId:"lastShipped",
  columnName:'<fmt:message key="label.lastdelivered"/>',
  sorting:"int",
  hiddenSortingColumn:"hlastShipped",
  width:7
 
},
{ columnId:"locationDesc",
  columnName:'<fmt:message key="label.shipto"/>',
  tooltip:"Y"
},
{ columnId:"deliveryPointDesc",
  columnName:'<fmt:message key="label.deliverto"/>',
  tooltip:"Y"
},
{ columnId:"approverName",
  columnName:'<fmt:message key="label.approver"/>',
  tooltip:"Y"
},
{ columnId:"hreleased"
}
,
{ columnId:"hrequiredDatetime"
}
,
{ columnId:"hlastShipped",
  sorting:"int"
},
{ columnId:"companyId"
},
{ columnId:"prNumber"
},
{ columnId:"lineItem"
},
{ columnId:"orderType"
}
,
{ columnId:"totalPicked",
  sorting:"int"
}
,
{ columnId:"requestId",
  sorting:"int"
},
{ columnId:"itemId",
  <tcmis:featureReleasedForMyFacilities feature="OTAllowSearchByItem" companyId="${personnelBean.companyId}">
    columnName:'<fmt:message key="label.itemid"/>',
  </tcmis:featureReleasedForMyFacilities>
  tooltip:"N"
},
{ columnId: "selected"},
{ columnId:"allowTruncateMR"
}

];
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">

<tcmis:form action="/haasordertrackingresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
    <c:if test="${MaxDataFilled eq 'True'}">
    	<fmt:message key="label.maxdata"> 
    		<fmt:param value="${param.maxData}"/>
    	</fmt:message>
    </c:if>
    
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors }">
     showErrorMessage = false;
    </c:when>
    <c:otherwise>
     showErrorMessage = true;
    </c:otherwise>
    </c:choose>
    parent.messagesData.errors = "<fmt:message key="label.errors"/>";  
    <!-- Larry Note: If both error and limit messages are there, the title will be 'Notice' -->
    <c:if test="${MaxDataFilled eq 'True'}">
    	showErrorMessage = true;
    	parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
    </c:if>
	//parent.showErrorMessages();
    //-->       
</script>




<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="prOrderTrackBean" style="width:100%;height:400px;" style="display: none;"></div>
<c:set var="colorClass" value=''/>
<c:if test="${pkgOrderTrackPrOrderTrackBeanCollection != null}">
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty pkgOrderTrackPrOrderTrackBeanCollection}" >  
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="pkgOrderTrackPrOrderTrackBean" items="${pkgOrderTrackPrOrderTrackBeanCollection}" varStatus="status">

<c:if test="${status.index > 0}">,</c:if>

<fmt:formatDate var="fmtRequiredDatetime" value="${status.current.requiredDatetime}" pattern="${dateFormatPattern}"/>
<c:set var="requiredDatetimeSortable" value="${status.current.requiredDatetime.time}"/>
<fmt:formatDate var="fmtReleaseDate" value="${status.current.releaseDate}" pattern="${dateFormatPattern}"/>
<c:set var="releaseDateSortable" value="${status.current.releaseDate.time}"/>
<fmt:formatDate var="fmtLastShipped" value="${status.current.lastShipped}" pattern="${dateFormatPattern}"/>
<c:set var="lastShippedSortable" value="${status.current.lastShipped.time}"/>

<tcmis:jsReplace var="partDescription" value="${status.current.partDescription}"  processMultiLines="true"/>
<tcmis:jsReplace var="notes" value="${status.current.notes}"  processMultiLines="true"/>
<tcmis:jsReplace var="facPartNo" value="${status.current.facPartNo}"  processMultiLines="true"/>
<tcmis:jsReplace var="packaging" value="${status.current.packaging}"  processMultiLines="true"/>

<tcmis:jsReplace var="requestorName" value="${status.current.requestorName}"/>
<tcmis:jsReplace var="approverName" value="${status.current.approverName}"/>
<tcmis:jsReplace var="companyName" value="${status.current.companyName}"/>
<tcmis:jsReplace var="facilityName" value="${status.current.facilityName}"/>
<tcmis:jsReplace var="applicationDesc" value="${status.current.applicationDesc}"/>
<tcmis:jsReplace var="catalogDesc" value="${status.current.catalogDesc}"/>
<tcmis:jsReplace var="poNumber" value="${status.current.poNumber}"/>

<tcmis:jsReplace var="locationDesc" value="${status.current.locationDesc}"/>
<tcmis:jsReplace var="deliveryPointDesc" value="${status.current.deliveryPointDesc}"/>
<tcmis:jsReplace var="inventoryGroupName" value="${status.current.inventoryGroupName}"/>

<%--c:choose>
 <c:when test="${status.index % 2 == 0}" >
    <c:set var="colorClass" value='grid_white'/>
  </c:when>
  <c:otherwise>
     <c:set var="colorClass" value='grid_lightblue'/>
  </c:otherwise>
</c:choose--%>

<c:set var="colorClass" value=''/>

<c:set var="critical" value='${status.current.critical}'/>
<c:set var="requestLineStatus" value='${status.current.requestLineStatus}'/>

<c:if test="${critical == 'Y' || critical == 'y'}">
  <c:set var="colorClass" value='grid_red'/>
</c:if>

<c:if test="${critical == 'S' || critical == 's'}">
  <c:set var="colorClass" value='grid_pink'/>
</c:if>

<c:if test="${requestLineStatus == 'Pending Cancellation'}">
  <c:set var="colorClass" value='grid_yellow'/>
</c:if>

<c:if test="${requestLineStatus == 'Cancelled' || requestLineStatus == 'Rejected'}">
  <c:set var="colorClass" value='grid_black'/>
</c:if>


<c:set var="pickedInUOM"></c:set>
<c:if test="${!empty status.current.totalUosPicked}">
<c:set var="pickedInUOM">${status.current.totalUosPicked} <fmt:message key="label.of"/>  ${status.current.totalQuantity}</c:set>
</c:if>



/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},<c:if test='${!empty colorClass}'>'class':"${colorClass}",</c:if>
 data:[
 '${status.current.requestLineStatus}',
 '${poNumber}',
 <tcmis:featureReleased feature="DisplayChargeNoOwnerSeqment" scope="ALL">
 '${status.current.programId}',
 </tcmis:featureReleased>
 '${requestorName}',
 '${inventoryGroup}',
 <c:choose>
 	<c:when test="${homeCompanyLogin}">
 		'${companyName}',
 		'${inventoryGroupName}',
 	</c:when>
 	<c:otherwise>
 		'${companyName}',
 	</c:otherwise>
 </c:choose>
 '${facilityName}',
 '${applicationDesc}',
 '${catalogDesc}',
 '${facPartNo}',
 '${partDescription}',
 '${packaging}',
 '${status.current.itemType}',
 '${status.current.prNumber}-${status.current.lineItem}',
 '${notes}',
 '${fmtReleaseDate}',
 '${fmtRequiredDatetime}',
 '${status.current.totalPicked}&nbsp;<fmt:message key="label.of"/>&nbsp;${status.current.quantity}',
 '${status.current.totalShipped}',
 '${pickedInUOM}',
 '${status.current.totalUosShipped}',
 '${status.current.unitOfSale}',
 '${fmtLastShipped}',
 '${locationDesc}',
 '${deliveryPointDesc}',
 '${approverName}',
 '${releaseDateSortable}',
 '${requiredDatetimeSortable}',
 '${lastShippedSortable}',
 '${status.current.companyId}',
 '${status.current.prNumber}',
 '${status.current.lineItem}',
 '${status.current.orderType}',
 '${status.current.totalPicked}',
 '${status.current.requestId}',
 '${status.current.bestItemId}',
 false,
 <c:choose>
	<c:when test="${tcmis:isCompanyFeatureReleased(personnelBean,'TruncateMR', status.current.facilityId, status.current.companyId)}">
 	true
	</c:when>
	<c:otherwise>
 	false
	</c:otherwise>
</c:choose>
 ]}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${empty pkgOrderTrackPrOrderTrackBeanCollection}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
    <tr>
    <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr> 
 </table> 
</c:if> 
 <!-- Search results end --> 
</c:if>



    <!-- Hidden element start --> 
    <div id="hiddenElements" style="display: none;">    			
		<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
        <input name="action" id="action" value="" type="hidden">     
        <input name="minHeight" id="minHeight" type="hidden" value="100"> 
        <input name="inventoryGroup" id="dodaacType" type="hidden" value="${param.inventoryGroup} ">
        <input name="searchField" id="searchField" type="hidden" value="${param.searchField}">
        <input name="buyerId" id="buyerId" type="hidden" value="${param.buyerId}">
        <input name="supplier" id="supplier" type="hidden" value="${param.supplier}">
        <input name="searchMode" id="searchMode" type="hidden" value="${param.searchMode}">
        <input name="searchArgument" id="searchArgument" type="hidden" value="${param.searchArgument}">
        <input name="shipFromDate" id="shipFromDate" type="hidden" value="${param.shipFromDate}">
        <input name="shipToDate" id="shipToDate" type="hidden" value="${param.shipToDate}">
        <input name="sortBy" id="sortBy" type="hidden" value="${param.sortBy}">      
		<input name="intcmIsApplication" id="intcmIsApplication" type="hidden" value="${intcmIsApplication}">
		<input name="module" id="module" type="hidden" value="${module}">
		
		<!-- Search frame parameters are saved so the results can be refreshed by calling the search again -->
		<input name="applicationId" id="applicationId" type="hidden" value="${param.applicationId}">
		<input name="companyId" id="companyId" type="hidden" value="${param.companyId}">		
		<input name="facilityId" id="facilityId" type="hidden" value="${param.facilityId}">
		<input name="searchText" id="searchText" type="hidden" value="${param.searchText}">
		<input name="searchType" id="searchType" type="hidden" value="${param.searchType}">
		<input name="searchWhat" id="searchWhat" type="hidden" value="${param.searchWhat}">
		<input name="status" id="status" type="hidden" value="${param.status}">
	 </div>
    <!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->

</tcmis:form>
</body>
</html:html>