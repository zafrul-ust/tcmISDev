<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<script type="text/javascript">
<!--
var jsData = new Array();
<c:set var="selectedHubName" value=''/>
<c:set var="selectedHub" value='${param.sourceHub}'/>
<c:set var="selectedInventoryGroup" value='${param.inventoryGroup}'/>

<c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
 <c:set var="currentHub" value='${status.current.branchPlant}'/>

  <c:choose>
   <c:when test="${empty selectedHub}" >
    <c:set var="selectedHub" value='${status.current.branchPlant}'/>
    <c:set var="selectedHubName" value="${status.current.hubName}"/>
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
 <c:set var="hubItemCountPermission" value='Yes'/>
</tcmis:inventoryGroupPermission>
window.parent.hubItemCountPermission="<c:out value="${hubItemCountPermission}"/>";

headerString = "<fmt:message key="label.inventorygroup"/>,<fmt:message key="label.catalog"/>,<fmt:message key="label.partnumber"/>,";
headerString +="<fmt:message key="label.partdescription"/>,<fmt:message key="label.ok"/><br><input type=\"checkbox\" name=\"updateAll\" ID=\"updateAll\" value=\"Yes\" onclick=\"updateAllRows()\">,<fmt:message key="label.item"/>,";
headerString +="<fmt:message key="label.usage"/>,<fmt:message key="label.countuom"/>,";
headerString +="<fmt:message key="distributedcount.label.usingfacility"/>,<fmt:message key="distributedcount.label.usingworkarea"/>,<fmt:message key="distributedcount.label.distributedusage"/>";
window.parent.headerString = headerString;

<c:set var="dataCount" value='${0}'/>
<c:forEach var="distributedCountUsageViewBean" items="${distributedCountUsageViewRelationBeanCollection}" varStatus="status">
jsData[<c:out value="${dataCount}"/>] = {hub:"<c:out value="${status.current.hub}"/>",inventoryGroup:"<c:out value="${status.current.inventoryGroup}"/>",catalogId:"<c:out value="${status.current.catalogId}"/>",
catPartNo:"<c:out value="${status.current.catPartNo}"/>",partDescription:"<c:out value="${status.current.partDescription}"/>",rowSpan:"<c:out value="${status.current.rowSpan}"/>",
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
  uom:"<c:out value="${status1.current.uom}"/>",active:"<img src=/images/item_chk0.gif border=0 alt=OK onclick=\"javascript:checkImageClicked=true;\" align=>",
  workAreaCollection:[
  <c:forEach var="wistributedCountUsageViewWorkAreaBean" items="${workAreaCollection}" varStatus="status2">
    <c:if test="${status2.index > 0 && workAreaSize > 1 }">
     ,
    </c:if>
    {facilityId:"<c:out value="${status2.current.facilityId}"/>",application:"<c:out value="${status2.current.application}"/>",uomDistributedUsage:"<c:out value="${status2.current.uomDistributedUsage}"/>"}
  </c:forEach>
  ]}
 </c:forEach>
]};
<c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>

window.parent.totalRows = <c:out value="${dataCount}"/>;
window.parent.distributedCountData = jsData;
window.parent.endingRowNumber = 0;
window.parent.rowCount = 0;
window.parent.inputChangedCount=0;
window.parent.lastSearchInventoryGroup="<c:out value="${selectedInventoryGroup}"/>";

<c:set var="errorMessage" value="${errorMessage}"/>
<c:choose>
  <c:when test="${empty errorMessage}">
   window.parent.showErrorMessage = false;
  </c:when>
  <c:otherwise>
   window.parent.showErrorMessage = true;
  </c:otherwise>
</c:choose>

window.parent.buildFirstSetRows();
//-->
</script>