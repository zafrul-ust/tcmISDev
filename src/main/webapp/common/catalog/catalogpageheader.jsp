<title>
<fmt:message key="label.catalog"/>
</title>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
var altFacilityId = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
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
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
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

var altApplication = new Array();
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<c:set var="applicationBeanCollection" value='${status.current.applicationBeanCollection}'/>

<c:set var="applicationCount" value='${0}'/>
altApplication["<c:out value="${currentFacility}"/>"] = new Array(
 <c:forEach var="facLocAppBean" items="${applicationBeanCollection}" varStatus="status1">
 <c:set var="currentStatus"  value='${status1.current.status}'/>
 <c:if test="${currentStatus == 'A'}">
 <c:set var="applicationCount" value='${applicationCount+1}'/>
 <c:choose>
   <c:when test="${applicationCount > 1}">
    ,"<c:out value="${status1.current.application}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.application}"/>"
   </c:otherwise>
  </c:choose>
 </c:if>
 </c:forEach>
);
 </c:forEach>

var altApplicationDesc = new Array();
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<c:set var="applicationBeanCollection" value='${status.current.applicationBeanCollection}'/>

<c:set var="applicationCount" value='${0}'/>
altApplicationDesc["<c:out value="${currentFacility}"/>"] = new Array(
 <c:forEach var="facLocAppBean" items="${applicationBeanCollection}" varStatus="status1">
 <c:set var="currentStatus"  value='${status1.current.status}'/>
 <c:if test="${currentStatus == 'A'}">
 <c:set var="applicationCount" value='${applicationCount+1}'/>
 <c:choose>
   <c:when test="${applicationCount > 1}">
    ,"<c:out value="${status1.current.applicationDesc}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.applicationDesc}"/>"
   </c:otherwise>
  </c:choose>
 </c:if>
 </c:forEach>
);
 </c:forEach>

var menuskin = "skin1"; // skin0, or skin1
var display_url = 0; // Show URLs in status bar?
// -->
</SCRIPT>