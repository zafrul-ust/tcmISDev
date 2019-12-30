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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/resultiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/distributedcountresults.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="distributedcount.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
partno:"<fmt:message key="label.partno"/>",inventoryGroup:"<fmt:message key="label.inventorygroup"/>",item:"<fmt:message key="label.item"/>",validValues:"<fmt:message key="label.validvalues"/>",hubMustBeSelected:"<fmt:message key="label.hubmustbeselected"/>",
dataChanged:"<fmt:message key="label.datachanged"/>",changesLost:"<fmt:message key="label.changeslost"/>",clickCancel:"<fmt:message key="label.clickcancel"/>",
distributedUsage:"<fmt:message key="distributedcount.label.distributedusage"/>",distributedUsageGreater:"<fmt:message key="distributedcount.label.distributedusagegreater"/>"};
// -->
</script>

<script type="text/javascript">
<!--
var distributedCountData = new Array();
<c:set var="dataCountJ" value='${0}'/>
<c:forEach var="distributedCountUsageViewBean" items="${distributedCountUsageViewRelationBeanCollection}" varStatus="status">
distributedCountData[<c:out value="${dataCountJ}"/>] = {inventoryGroup:"<c:out value="${status.current.inventoryGroup}"/>",catalogId:"<c:out value="${status.current.catalogId}"/>",
catPartNo:"<c:out value="${status.current.catPartNo}"/>",rowSpan:"<c:out value="${status.current.rowSpan}"/>",
<c:set var="itemCollection"  value='${status.current.itemCollection}'/>
<bean:size id="listSize" name="itemCollection"/>
itemcollection:[
<c:forEach var="distributedCountUsageViewItemBean" items="${itemCollection}" varStatus="status1">
  <c:set var="workAreaCollection"  value='${status1.current.workAreaCollection}'/>
  <bean:size id="workAreaSize" name="workAreaCollection"/>
   <c:if test="${status1.index > 0 && listSize > 1 }">
   ,
   </c:if>
  {workAreaSize:"<c:out value="${workAreaSize}"/>",itemId:"<c:out value="${status1.current.itemId}"/>",usage:"<c:out value="${status1.current.usage}"/>",
   workAreaCollection:[
  <c:forEach var="wistributedCountUsageViewWorkAreaBean" items="${workAreaCollection}" varStatus="status2">
    <c:if test="${status2.index > 0 && workAreaSize > 1 }">
     ,
    </c:if>
    {uomDistributedUsage:"<c:out value="${status2.current.uomDistributedUsage}"/>"}
  </c:forEach>
  ]}
 </c:forEach>
]};
<c:set var="dataCountJ" value='${dataCountJ+1}'/>
</c:forEach>
//-->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnload()">

<tcmis:form action="/distributedcountresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->

<c:set var="selectedHub" value='${param.sourceHub}'/>
 <c:set var="selectedHubName" value=''/>
 <c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:choose>
   <c:when test="${empty selectedHub}" >
    <c:set var="selectedHub" value='${status.current.branchPlant}'/>
    <c:set var="selectedHubName" value="${status.current.hubName}"/>
   </c:when>
   <c:when test="${currentHub == selectedHub}" >
   </c:when>
  </c:choose>

  <c:choose>
   <c:when test="${currentHub == selectedHub}">
    <c:set var="selectedHubName" value="${status.current.hubName}"/>
   </c:when>
  </c:choose>
 </c:forEach>

<c:set var="hubItemCountPermission" value=''/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="Hub ItemCount" facilityId="${selectedHubName}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
  <c:set var="hubItemCountPermission" value='Yes'/>
 //-->
 </script>
</tcmis:inventoryGroupPermission>

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <c:set var="errorMessage" value="${errorMessage}"/>
 <c:out value="${errorMessage}"/>
 <html:errors/>
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty errorMessage}">
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

<c:if test="${distributedCountUsageViewRelationBeanCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty distributedCountUsageViewRelationBeanCollection}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:forEach var="distributedCountUsageViewBean" items="${distributedCountUsageViewRelationBeanCollection}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
     <th width="5%"><fmt:message key="label.inventorygroup"/></th>
     <th width="5%"><fmt:message key="label.catalog"/></th>
     <th width="5%"><fmt:message key="label.partnumber"/></th>
     <th width="15%"><fmt:message key="label.partdescription"/></th>
     <th width="2%"><fmt:message key="label.ok"/>
      <c:if test="${hubItemCountPermission == 'Yes'}">
        <br><input type="checkbox" name="checkAll" id="checkAll" value="Yes" onclick="updateAllRows('ok')">
      </c:if>
     </th>
     <th width="5%"><fmt:message key="label.item"/></th>
     <th width="5%"><fmt:message key="label.usage"/></th>
     <th width="5%"><fmt:message key="label.countuom"/></th>
     <th width="10%"><fmt:message key="distributedcount.label.usingfacility"/></th>
     <th width="10%"><fmt:message key="distributedcount.label.usingworkarea"/></th>
     <th width="5%"><fmt:message key="distributedcount.label.distributedusage"/></th>
    </tr>
    </c:if>

    <c:set var="itemCollection"  value='${status.current.itemCollection}'/>
     <bean:size id="itemSize" name="itemCollection"/>
    <c:set var="mainRowSpan" value='${status.current.rowSpan}' />

    <c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>

    <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">

   <%-- <input type="hidden" name="colorClass<c:out value="${status.index}"/>" id="colorClass<c:out value="${status.index}"/>" value="<c:out value="${colorClass}"/>" >
    <input type="hidden" name="childRowsSize<c:out value="${status.index}"/>" id="childRowsSize<c:out value="${status.index}"/>" value="<c:out value="${itemSize}"/>" >
--%>

   <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.inventoryGroup}"/></td>
   <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.catalogId}"/></td>
   <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.catPartNo}"/></td>
   <td width="15%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.partDescription}"/></td>

  <c:forEach var="distributedCountUsageViewItemBean" items="${itemCollection}" varStatus="status1">
  <c:if test="${status1.index > 0 && itemSize > 1 }">
   <tr class="<c:out value="${colorClass}"/>" id="childRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/>">
  </c:if>

   <c:set var="workAreaCollection"  value='${status1.current.workAreaCollection}'/>
   <bean:size id="workAreaSize" name="workAreaCollection"/>
<%--   <input type="hidden" name="grandChildRowsSize<c:out value="${status.index}"/><c:out value="${status1.index}"/>"
   						  id="grandChildRowsSize<c:out value="${status.index}"/><c:out value="${status1.index}"/>"
   			value="<c:out value="${workAreaSize}"/>" >
--%>

  <td width="2%" rowspan="<c:out value="${workAreaSize}"/>">
    <c:choose>
     <c:when test="${hubItemCountPermission == 'Yes'}">
      <input type="checkbox" name="distributedCountUsageViewBean[<c:out value="${dataCount}"/>].ok" id="ok<c:out value="${dataCount}"/>" value="Y">
     </c:when>
     <c:otherwise>
     &nbsp;
     </c:otherwise>
    </c:choose>
  </td>

  <td width="5%" rowspan="<c:out value="${workAreaSize}"/>"><c:out value="${status1.current.itemId}"/></td>
  <td width="5%" rowspan="<c:out value="${workAreaSize}"/>"><c:out value="${status1.current.usage}"/></td>
  <td width="5%" rowspan="<c:out value="${workAreaSize}"/>"><c:out value="${status1.current.uom}"/></td>

    <c:forEach var="distributedCountUsageViewWorkAreaBean" items="${workAreaCollection}" varStatus="status2">

    <input type="hidden" name="distributedCountUsageViewBean[<c:out value="${dataCount}"/>].catPartNo" id="catPartNo<c:out value="${dataCount}"/>" value="<c:out value="${status2.current.catPartNo}"/>">
    <input type="hidden" name="distributedCountUsageViewBean[<c:out value="${dataCount}"/>].usageChanged" id="usageChanged<c:out value="${dataCount}"/>" value="">

    <c:if test="${status2.index > 0 && workAreaSize > 1 }">
     <tr class="<c:out value="${colorClass}"/>" id="grnadChildRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/><c:out value="${status2.index}"/>">
    </c:if>

     <td width="10%"><c:out value="${status2.current.facilityId}"/></td>
     <td width="10%"><c:out value="${status2.current.application}"/></td>
     <td width="5%">
    <c:choose>
     <c:when test="${hubItemCountPermission == 'Yes'}">
      <input type="text" class="inputBox" name="distributedCountUsageViewBean[<c:out value="${dataCount}"/>].uomDistributedUsage" id="uomDistributedUsage<c:out value="${dataCount}"/>" value="<c:out value="${status2.current.uomDistributedUsage}"/>" size="5" maxlength="10" onchange="distributedCountChanged(<c:out value="${dataCount}"/>)">
     </c:when>
     <c:otherwise>
     <c:out value="${status2.current.uomDistributedUsage}"/>
     </c:otherwise>
    </c:choose>
    </td>

     <c:if test="${status2.index > 0 || workAreaSize ==1 }">
      </tr>
     </c:if>

    <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>

  <c:if test="${status1.index > 0 || itemSize ==1 }">
   </tr>
  </c:if>
 </c:forEach>

 </c:forEach>
   </table>
   </c:if>

   <!-- If the collection is empty say no data found -->
   <c:if test="${empty distributedCountUsageViewRelationBeanCollection}" >
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
<input name="totalMainLines" id="totalMainLines" value="<c:out value="${dataCountJ}"/>" type="hidden">

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<%--<tcmis:saveRequestParameter/>--%>
<input name="searchType" id="searchType" type="hidden" value="${param.searchType}">
<input name="sourceHub" id="sourceHub" type="hidden" value="${param.sourceHub}">
<input name="searchText" id="searchText" type="hidden" value="${param.searchText}">
<input name="searchWhat" id="searchWhat" type="hidden" value="${param.searchWhat}">
<input name="inventoryGroup" id="inventoryGroup" type="hidden" value="${param.inventoryGroup}">

<input type="hidden" name="submitUpdate" id="submitUpdate" value="">
<input type="hidden" name="submitSave" id="submitSave" value="">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>