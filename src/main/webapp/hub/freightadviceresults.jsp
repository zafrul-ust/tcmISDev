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

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/freightadviceresults.js"></script>

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
<fmt:message key="freightadvice.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
with(milonic=new menuname("freightAdvice")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="label.changefreightadvice"/>;url=javascript:freightAdvice();");
}

drawMenus();

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
<c:set var="markforaddress"><fmt:message key="label.markforaddress"/></c:set> 
<c:set var="packinggroupid"><fmt:message key="label.packinggroupid"/></c:set>
<c:set var="projectedshipdate"><fmt:message key="label.projectedshipdate"/></c:set>
<c:set var="actualshipdate"><fmt:message key="label.actualshipdate"/></c:set>
var config = [
{ columnId:"permission",
  columnName:''
},              
{ columnId:"shipVia",
  columnName:'<fmt:message key="label.shiptoaddress"/>',
  width:15,
  tooltip:"Y"
},
{ columnId:"ultimateShipTo",
  columnName:'<tcmis:jsReplace value="${markforaddress}"/>',
  width:15,
  tooltip:"Y"
},
{ columnId:"packingGroupId",
  columnName:'<tcmis:jsReplace value="${packinggroupid}"/>',
  width:6
  
},
{ columnId:"inventoryGroup",
  columnName:'<tcmis:jsReplace value="${inventorygroup}"/>'
},
{ columnId:"consolidationNumber",
  columnName:'<fmt:message key="label.consolidationno"/>'
},
{ columnId:"carrierName",
  columnName:'<fmt:message key="label.carrier"/>'
},
{ columnId:"transportationMode",
  columnName:'<fmt:message key="label.mode"/>',
  width:6
  
},
{ columnId:"carrierTrackingNumber",
  columnName:'<fmt:message key="label.trackingnumber"/>',
  tooltip:"Y"
},
{ columnId:"mrLine",
  columnName:'<fmt:message key="label.mrline"/>',
  width:7
},
{ columnId:"milstripCode",
  columnName:'<fmt:message key="label.milstrip"/>',
  tooltip:"Y"
},
{ columnId:"facPartNo",
  columnName:'<fmt:message key="label.partnumber"/>',
  width:9,
  tooltip:"Y"
},
{ columnId:"partShortName",
  columnName:'<fmt:message key="label.shortname"/>',
  width:11,
  tooltip:"Y"
},
{ columnId:"quantity",
  columnName:'<fmt:message key="label.quantity"/>',
  width:11,
  tooltip:"Y"
  
},
{ columnId:"weight",
  columnName:'<fmt:message key="label.weight"/>',
  hiddenSortingColumn:"hWeight",
  width:5,
  sorting:"int"
},
{ columnId:"hWeight",
  sorting:"int"
},
{ columnId:"cube",
  columnName:'<fmt:message key="label.cube"/>',
  hiddenSortingColumn:"hCube",
  width:5,
  sorting:"int"
},
{ columnId:"hCube",
  sorting:"int"
},
{ columnId:"status",
  columnName:'<fmt:message key="label.status"/>',
  width:5
},
{ columnId:"notes",
  columnName:'<fmt:message key="label.notes"/>',
  tooltip:"Y"
  
},
{ columnId:"scheduledShipDate",
  columnName:'<tcmis:jsReplace value="${projectedshipdate}"/>',
  hiddenSortingColumn:"hScheduledShipDate",
  sorting:"int"
},
{ columnId:"hScheduledShipDate",
  sorting:"int"
},
{ columnId:"shipConfirmDate",
  columnName:'<tcmis:jsReplace value="${actualshipdate}"/>',
  hiddenSortingColumn:"hShipConfirmDate",
  sorting:"int"
},
{ columnId:"hShipConfirmDate",
  sorting:"int"
}
];
// -->
</script>
</head>
<body bgcolor="#ffffff" onload="resultOnLoad();">

<tcmis:form action="/freightadviceresults.do" onsubmit="return submitFrameOnlyOnce();">

<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
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
    //-->       
</script>

<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="freightAdviceViewBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:if test="${freightAdviceViewBeanCollection != null}">
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty freightAdviceViewBeanCollection}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="freightAdviceViewBeanCollection" items="${freightAdviceViewBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<fmt:formatDate var="fmtScheduledShipDate" value="${status.current.scheduledShipDate}" pattern="${dateFormatPattern}"/>
<c:set var="scheduledShipDateSortable" value="${status.current.scheduledShipDate.time}"/>

<fmt:formatDate var="fmtShipConfirmDateSortable" value="${status.current.dateShipped}" pattern="${dateFormatPattern}"/>
<c:set var="shipConfirmDateSortable" value="${status.current.dateShipped.time}"/>

<tcmis:jsReplace var="shipVia" value="${status.current.shipVia}"  processMultiLines="true"/>
<tcmis:jsReplace var="ultimateShipTo" value="${status.current.ultimateShipTo}"  processMultiLines="true"/>
<tcmis:jsReplace var="quantity" value="${status.current.quantity}"  processMultiLines="true"/>
<tcmis:jsReplace var="notes" value="${status.current.notes}"  processMultiLines="true"/>

<c:set var="showUpdateLink" value='N'/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="chgFreightAdvice" inventoryGroup="${status.current.inventoryGroup}">
<c:set var="showUpdateLink" value='Y'/>
</tcmis:inventoryGroupPermission>

/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:[ '${showUpdateLink}',
 '${shipVia}','${ultimateShipTo}',
 '${status.current.packingGroupId}','${status.current.inventoryGroup}','${status.current.consolidationNumber}',
 '<tcmis:jsReplace value="${status.current.carrierName}" />','${status.current.transportationMode}',
 '<tcmis:jsReplace value="${status.current.carrierTrackingNumber}"  processMultiLines="true"/>',
 '${status.current.mrLine}','${status.current.milstripCode}','<tcmis:jsReplace value="${status.current.facPartNo}" />',
 '<tcmis:jsReplace value="${status.current.partShortName}"  processMultiLines="true" />',
 '${quantity}','${status.current.weight}','${status.current.weight}',
 '${status.current.cube}','${status.current.cube}',
 '${status.current.status}',
 '${notes}','${fmtScheduledShipDate}','${scheduledShipDateSortable}',
 '${fmtShipConfirmDateSortable}','${shipConfirmDateSortable}']}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${empty freightAdviceViewBeanCollection}">
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
        <input name="packingGroupId" id="packingGroupId" type="hidden">  
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