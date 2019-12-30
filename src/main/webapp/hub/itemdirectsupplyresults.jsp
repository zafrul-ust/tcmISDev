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
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/itemdirectsupply.js"></script>

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
    <fmt:message key="label.itemdirectsupply"/>
</title>
<tcmis:opsEntityPermission indicator="true" userGroupId="itemDirectSupply" opsEntityId="${param.opsEntityId}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</tcmis:opsEntityPermission>


<script language="JavaScript" type="text/javascript"><!--

//add all the javascript messages here, this for internationalization of client side javascript messages

var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
norowselected:"<fmt:message key="error.norowselected"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
qty:"<fmt:message key="label.qtylessqtyreceived"/>",
norowselected:"<fmt:message key="error.norowselected"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

var config = [
{ 
  columnId:"permission"
},
{
  columnId:"okDoUpdate",
  columnName:'<fmt:message key="label.ok"/>',
  width:3,
  type:'hch'
},
{ columnId:"inventoryGroupName",
  columnName:'<fmt:message key="label.destinationig"/>',
  tooltip:"Y",
  width:14
},
{ 
  columnId:"itemId",
  columnName:'<fmt:message key="label.itemid"/>',
  width:6
},
{ 
  columnId:"itemDesc",
  columnName:'<fmt:message key="label.itemdesc"/>',
  tooltip:"Y",
  width:15
},
{ 
  columnId:"packaging",
  columnName:'<fmt:message key="label.packaging"/>',
  tooltip:"Y",
  width:15
},
{ 
  columnId:"sourceInventoryGroup",
  columnName:'<fmt:message key="label.sourceinventorygroup"/>',
  tooltip:"Y",
  width:12
},
{ 
  columnId:"enteredByName",
  columnName:'<fmt:message key="label.enteredby"/>',
  width:12
}
,
{ columnId:"enteredDate",
  columnName:'<fmt:message key="label.entereddate"/>',
  hiddenSortingColumn:"hentereddate",
  sorting:"int",
  width:7
},
{ columnId:"hentereddate",
  sorting:"int"
},
{ 
  columnId:"opsEntityId"
}
,
{ 
  columnId:"branchPlant"
}
,
{ 
  columnId:"sourceHub"
}
,
{ 
  columnId:"inventoryGroup"
}

]; 		

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/itemdirectsupplyresults.do" onsubmit="return submitFrameOnlyOnce();">
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

<div id="itemDirectSupplyBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:if test="${ItemDirectSupplyBeanColl != null}">
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty ItemDirectSupplyBeanColl}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${ItemDirectSupplyBeanColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<tcmis:jsReplace var="itemDesc" value="${status.current.itemDesc}"  processMultiLines="true"/>
<fmt:formatDate var="fmtEnterDate" value="${status.current.enteredDate}" pattern="${dateFormatPattern}"/>
<c:set var="enteredDateTime" value="${status.current.enteredDate.time}"/>
<tcmis:jsReplace var="packaging" value="${status.current.packaging}"  processMultiLines="true"/>

<tcmis:opsEntityPermission indicator="true" opsEntityId="${status.current.opsEntityId}"  userGroupId="itemDirectSupply">
 <c:set var="hasPerm" value="Y"/>
</tcmis:opsEntityPermission>

 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:[ '${hasPerm}','','${status.current.inventoryGroupName}','${status.current.itemId}','${itemDesc}','${packaging}','${status.current.sourceInventoryGroupName}',
 '${status.current.enteredByName}','${fmtEnterDate}','${enteredDateTime}',
 '${status.current.opsEntityId}','${status.current.branchPlant}','${status.current.sourceHub}','${status.current.inventoryGroup}']}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${empty ItemDirectSupplyBeanColl}">
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
        <input name="uAction" id="uAction" value="" type="hidden"> 
        <input name="startSearchTime" id="startSearchTime" type="hidden"value="">
        <input name="minHeight" id="minHeight" type="hidden" value="100"> 
        <input name="opsEntityId" id="opsEntityId" type="hidden" value="${param.opsEntityId}">
	    <input name="hub" id="hub" type="hidden" value="${param.hub}">
	    <input name="inventoryGroup" id="inventoryGroup" type="hidden" value="${param.inventoryGroup}">
	    <input name="searchField" id="searchField" type="hidden" value="${param.searchField}">
	    <input name="searchMode" id="searchMode" type="hidden" value="${param.searchMode}">
	    <input name="searchArgument" id="searchArgument" type="hidden" value="${param.searchArgument}">
    </div>
    <!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>

</body>
</html:html>