<title>
<fmt:message key="label.inventory"/>
</title>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
var altFacilityId = new Array(
<c:forEach var="facilityIgViewOvBean" items="${facilityIgViewOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.facilityId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.facilityId}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altFacilityName = new Array(
<c:forEach var="facilityIgViewOvBean" items="${facilityIgViewOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.facilityName}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.facilityName}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altInventoryGroup = new Array();
var altInventoryGroupName = new Array();
<c:forEach var="facilityIgViewOvBean" items="${facilityIgViewOvBeanCollection}" varStatus="status">
  <c:set var="currentFacility" value='${status.current.facilityId}'/>
  <c:set var="inventoryGroups" value='${status.current.inventoryGroups}'/>

  altInventoryGroup["<c:out value="${currentFacility}"/>"] = new Array(
  <c:forEach var="inventoryGroupDefinitionOvBean" items="${inventoryGroups}" varStatus="status1">
   <c:choose>
   <c:when test="${status1.index > 0}">
    ,"<c:out value="${status1.current.inventoryGroup}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.inventoryGroup}"/>"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );

  altInventoryGroupName["<c:out value="${currentFacility}"/>"] = new Array(
  <c:forEach var="inventoryGroupDefinitionOvBean" items="${inventoryGroups}" varStatus="status1">
   <c:choose>
   <c:when test="${status1.index > 0}">
    ,"<c:out value="${status1.current.inventoryGroupName}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.inventoryGroupName}"/>"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );
</c:forEach>

var menuskin = "skin1"; // skin0, or skin1
var display_url = 0; // Show URLs in status bar?
// -->
</SCRIPT>
