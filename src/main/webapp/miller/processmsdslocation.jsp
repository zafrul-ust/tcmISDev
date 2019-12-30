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
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<LINK REL="SHORTCUT ICON" HREF="https://www.tcmis.com/images/buttons/tcmIS.ico"></LINK>
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/clientpages.css"></LINK>
<SCRIPT SRC="/js/calendar.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/common/formchek.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/miller/msds.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
var altFacilityId = new Array(
<c:forEach var="facBldgFloorDeptEditOvBean" items="${facBldgFloorDeptEditOvBeanCollection}" varStatus="status">
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

var altDepartment = new Array();
var altDepartmentVariable = new Array();
<c:forEach var="facBldgFloorDeptEditOvBean" items="${facBldgFloorDeptEditOvBeanCollection}" varStatus="status">
<c:set var="currentFacilityId" value='${status.current.facilityId}'/>
<c:set var="deptBldgFloorCollection" value='${status.current.deptBldgFloor}'/>

altDepartmentVariable["<c:out value="${currentFacilityId}"/>"] = new Array(
 <c:forEach var="deptBldgFloorEditOvBean" items="${deptBldgFloorCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
    ,"<c:out value="${status1.current.departmentVariable}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.departmentVariable}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>

  );

altDepartment["<c:out value="${currentFacilityId}"/>"] = new Array(
 <c:forEach var="deptBldgFloorEditOvBean" items="${deptBldgFloorCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
    ,"<c:out value="${status1.current.department}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.department}"/>"
   </c:otherwise>
  </c:choose>
  </c:forEach>

  );
 </c:forEach>


var altBuilding = new Array();
var altBuildingVariable = new Array();
<c:forEach var="facBldgFloorDeptEditOvBean" items="${facBldgFloorDeptEditOvBeanCollection}" varStatus="status">
<c:set var="currentFacilityId" value='${status.current.facilityId}'/>
<c:set var="deptBldgFloorCollection" value='${status.current.deptBldgFloor}'/>
  <c:forEach var="deptBldgFloorEditOvBean" items="${deptBldgFloorCollection}" varStatus="status1">
    <c:set var="currentDepartmentVariable" value='${status1.current.departmentVariable}'/>
    <c:set var="bldgFloorCollection" value='${status1.current.bldgFloor}'/>
    altBuildingVariable["<c:out value="${currentFacilityId}"/><c:out value="${currentDepartmentVariable}"/>"] = new Array(
    <c:forEach var="bldgFloorEditOvBean" items="${bldgFloorCollection}" varStatus="status2">
 <c:choose>
   <c:when test="${status2.index > 0}">
    ,"<c:out value="${status2.current.buildingVariable}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status2.current.buildingVariable}"/>"
   </c:otherwise>
  </c:choose>
    </c:forEach>

    );
    altBuilding["<c:out value="${currentFacilityId}"/><c:out value="${currentDepartmentVariable}"/>"] = new Array(
    <c:forEach var="bldgFloorEditOvBean" items="${bldgFloorCollection}" varStatus="status2">
 <c:choose>
   <c:when test="${status2.index > 0}">
    ,"<c:out value="${status2.current.building}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status2.current.building}"/>"
   </c:otherwise>
  </c:choose>
    </c:forEach>
    );
  </c:forEach>
</c:forEach>


var altFloor = new Array();
var altFloorVariable = new Array();
<c:forEach var="facBldgFloorDeptEditOvBean" items="${facBldgFloorDeptEditOvBeanCollection}" varStatus="status">
  <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
  <c:set var="deptBldgFloorCollection" value='${status.current.deptBldgFloor}'/>
  <c:forEach var="deptBldgFloorEditOvBean" items="${deptBldgFloorCollection}" varStatus="status1">
    <c:set var="currentDepartmentVariable" value='${status1.current.departmentVariable}'/>
    <c:set var="bldgFloorCollection" value='${status1.current.bldgFloor}'/>

    <c:forEach var="bldgFloorEditOvBean" items="${bldgFloorCollection}" varStatus="status2">
      <c:set var="currentBuildingVariable" value='${status2.current.buildingVariable}'/>
      <c:set var="floorCollection" value='${status2.current.floor}'/>

      altFloorVariable["<c:out value="${currentFacilityId}"/><c:out value="${currentDepartmentVariable}"/><c:out
value="${currentBuildingVariable}"/>"] = new Array(
      <c:forEach var="floorEditOvBean" items="${floorCollection}" varStatus="status3">
 <c:choose>
   <c:when test="${status3.index > 0}">
    ,"<c:out value="${status3.current.floorVariable}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status3.current.floorVariable}"/>"
   </c:otherwise>
  </c:choose>
      </c:forEach>
      );
      altFloor["<c:out value="${currentFacilityId}"/><c:out value="${currentDepartmentVariable}"/><c:out
value="${currentBuildingVariable}"/>"] = new Array(
      <c:forEach var="floorEditOvBean" items="${floorCollection}" varStatus="status3">
         <c:choose>
   <c:when test="${status3.index > 0}">
    ,"<c:out value="${status3.current.floor}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status3.current.floor}"/>"
   </c:otherwise>
  </c:choose>
      </c:forEach>
      );

    </c:forEach>

  </c:forEach>
</c:forEach>

// -->
</SCRIPT>
<title>
<fmt:message key="processmsdslocation.title"/>
</title>
</HEAD>

<BODY>

<DIV ID="TRANSITPAGE" STYLE="display: none;">

<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B><fmt:message key="label.pleasewait"/></B></FONT><center>

</DIV>
<DIV ID="MAINPAGE" STYLE="">

<TABLE BORDER=0 WIDTH=100% CLASS="moveupmore">
<TR VALIGN="TOP">
<TD WIDTH="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</TD>
<TD ALIGN="right">
<img src="/images/tcmistcmis32.gif" border=0 align="right">
</TD>
</TR>
</Table>
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" CLASS="moveup">
<TR><TD WIDTH="70%" ALIGN="LEFT" HEIGHT="22" CLASS="heading">
<B><fmt:message key="processmsdslocation.title"/></B>
</TD>
<TD WIDTH="30%" ALIGN="RIGHT" HEIGHT="22" CLASS="headingr">
<A HREF="/tcmIS/miller/showsearchmsds.do" STYLE="color:#FFFFFF"><fmt:message key="label.home"/></A>
</TD>
</TR>
</TABLE>
<TABLE BORDER="0" CELLSPACING=1 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<TR>
<TD width="10%" CLASS="announce">
</TD>
<TD width="30%" CLASS="announce">
<html:errors/>
<logic:present name="errorBean" scope="request">
  <font face=Verdana size=2 color="red">
    <b><bean:message key="excel.label.oracleerror"/>: <c:out value="${requestScope.errorBean.cause}"/></b>
  </font>
</logic:present>

<BR>
</TD>
</TR>
</TABLE>


<TABLE  BORDER="0" CELLSPACING=0 CELLPADDING=0 WIDTH="100%">
<tr>
<td>
</TD>
</tr>
</table>

<c:if test="${currentMsdsLoadViewBeanCollection != null}">
<c:set var="resultCount" value='0'/>
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" CLASS="moveup">
<TR><TD WIDTH="100%" ALIGN="LEFT" HEIGHT="22" CLASS="heading">
<B><fmt:message key="processmsds.label.currentlocations"/> <c:out value="${requestScope.currentMsdsLoadViewBeanCollection[0].customerMsdsNo}"/></B>
</TD>
</TR>
</TABLE>
<TABLE BORDER="0" BGCOLOR="#000000" CELLSPACING="1" CELLPADDING="2" WIDTH="100%" CLASS="moveup">
</c:if>
<c:forEach var="currentMsdsLoadViewBean" items="${currentMsdsLoadViewBeanCollection}" varStatus="status">
<c:if test="${status.current.facilityId != null}">
<c:if test="${status.index == 0}">
<tr align="center">
<TH width="15%"  height="38"><fmt:message key="label.facility"/></TH>
<TH width="15%"  height="38"><fmt:message key="processmsds.label.department"/></TH>
<TH width="15%"  height="38"><fmt:message key="processmsds.label.building"/></TH>
<TH width="15%"  height="38"><fmt:message key="processmsds.label.floor"/></TH>
<TH width="15%"  height="38">&nbsp;</TH>
</tr>
</c:if>

<c:set var="resultCount" value='${resultCount+1}'/>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value='CLASS=blue'/>
   <c:set var="invisibleClass" value='CLASS=INVISIBLEHEADBLUE'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='CLASS=white'/>
   <c:set var="invisibleClass" value='CLASS=INVISIBLEHEADWHITE'/>
  </c:otherwise>
</c:choose>

<TR align="center">

<td <c:out value="${colorClass}"/> width="15%" height="38">
 <c:out value="${status.current.facilityId}"/>
</td>
<td <c:out value="${colorClass}"/> width="15%" height="38">
  <c:out value="${status.current.department}"/>
</td>
<td <c:out value="${colorClass}"/> width="15%" height="38">
  <c:out value="${status.current.building}"/>
</td>
<td <c:out value="${colorClass}"/> width="15%" height="38">
  <c:out value="${status.current.floor}"/>
</td>

<td <c:out value="${colorClass}"/> width="5%" height="38">


<html:form action="/deletemsdslocation.do">
<INPUT TYPE="hidden" name="msds" value="<c:out value="${requestScope.currentMsdsLoadViewBeanCollection[0].customerMsdsNo}"/>"/>
<INPUT TYPE="hidden" name="facilityIdToDelete" value="<c:out value="${status.current.facilityId}"/>"/>
<INPUT TYPE="hidden" name="departmentToDelete" value="<c:out value="${status.current.department}"/>"/>
<INPUT TYPE="hidden" name="buildingToDelete" value="<c:out value="${status.current.building}"/>"/>
<INPUT TYPE="hidden" name="floorToDelete" value="<c:out value="${status.current.floor}"/>"/>
<html:submit property="submitDelete">
<fmt:message key="label.delete"/>
</html:submit>
</html:form>

</td>

</tr>
</c:if>
</c:forEach>
<c:if test="${resultCount == 0}">

<tr>
<td colspan="5" <c:out value="${colorClass}"/> width="15%" height="38" CLASS="heading">
<fmt:message key="processmsds.label.nolocations"/>
</td>
</tr>
</c:if>

<tr>
<td <c:out value="${colorClass}"/> width="15%" height="38" CLASS="heading">
<html:form action="/addmsdslocation.do">
<INPUT TYPE="hidden" name="msds" value="<c:out value="${requestScope.currentMsdsLoadViewBeanCollection[0].customerMsdsNo}"/>"/>
<INPUT TYPE="hidden" name="status" value="ACTIVE"/>
<INPUT TYPE="hidden" name="tradeName" value="<c:out value="${requestScope.currentMsdsLoadViewBeanCollection[0].tradeName}"/>"/>
<INPUT TYPE="hidden" name="manufacturer" value="<c:out value="${requestScope.currentMsdsLoadViewBeanCollection[0].manufacturerName}"/>"/>
<INPUT TYPE="hidden" name="onLine" value="<c:out value="${requestScope.currentMsdsLoadViewBeanCollection[0].onLine}"/>"/>
<INPUT TYPE="hidden" name="fileName" value="<c:out value="${requestScope.currentMsdsLoadViewBeanCollection[0].content}"/>"/>
<INPUT TYPE="hidden" name="revisionDate" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${requestScope.currentMsdsLoadViewBeanCollection[0].revisionDate}"/>"/>
<select name="facilityId" onchange="facilityChanged()">

<c:forEach var="facBldgFloorDeptEditOvBean" items="${facBldgFloorDeptEditOvBeanCollection}" varStatus="status">
<c:if test="${status.index == 0}">
  <c:set var="departmentCollection" value='${status.current.deptBldgFloor}'/>
</c:if>
    <option value="<c:out value="${status.current.facilityId}"/>"><c:out value="${status.current.facilityId}"/></option>

  </c:forEach>
</select>

<%--
<c:set var="selectedFacilityId" value=''/>
<select name="facilityId" onchange="facilityChanged()">
  <c:forEach var="facBldgFloorDeptEditOvBean" items="${facBldgFloorDeptEditOvBeanCollection}" varStatus="status">
  <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
  <c:choose>
   <c:when test="${empty selectedFacilityId}" >
    <c:set var="selectedFacilityId" value='${status.current.facilityId}'/>
    <c:set var="deptBldgFloorCollection" value='${status.current.deptBldgFloor}'/>
   </c:when>
   <c:when test="${currentFacilityId == selectedFacilityId}" >
    <c:set var="deptBldgFloorCollection" value='${status.current.deptBldgFloor}'/>
   </c:when>
  </c:choose>

  <c:choose>
   <c:when test="${currentFacilityId == selectedFacilityId}">
    <option value="<c:out value="${currentFacilityId}"/>" selected><c:out value="${status.current.facilityId}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityId}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
--%>
</td>
<td <c:out value="${colorClass}"/> width="15%" height="38" CLASS="heading">
<select name="department" onchange="departmentChanged()">
  <c:forEach var="deptBldgFloorEditOvBean" items="${departmentCollection}" varStatus="status">
<c:if test="${status.index == 0}">
  <c:set var="buildingCollection" value='${status.current.bldgFloor}'/>
</c:if>
        <option value="<c:out value="${status.current.departmentVariable}"/>"><c:out value="${status.current.department}"/></option>

  </c:forEach>
</select>
<%--
<c:set var="selectedDepartment" value=''/>
<select name="department" onchange="departmentChanged()">
  <c:forEach var="deptBldgFloorEditOvBean" items="${deptBldgFloorCollection}" varStatus="status">
    <c:set var="currentDepartment" value='${status.current.department}'/>
    <c:choose>
      <c:when test="${empty selectedDepartment}" >
        <c:set var="selectedDepartment" value=""/>
        <c:set var="bldgFloorCollection" value='${status.current.bldgFloor}'/>
      </c:when>
      <c:when test="${currentDepartment == selectedDepartment}" >
        <c:set var="bldgFloorCollection" value='${status.current.bldgFloor}'/>
      </c:when>
    </c:choose>

    <c:choose>
      <c:when test="${currentDepartment == selectedDepartment}">
        <option value="<c:out value="${currentDepartment}"/>" selected><c:out value="${status.current.department}"/></option>
      </c:when>
      <c:otherwise>
        <option value="<c:out value="${currentDepartment}"/>"><c:out value="${status.current.department}"/></option>
      </c:otherwise>
    </c:choose>
  </c:forEach>
</select>
--%>
</td>
<td <c:out value="${colorClass}"/> width="15%" height="38" CLASS="heading">
<select name="building" onchange="buildingChanged()">

  <c:forEach var="bldgFloorEditOvBean" items="${buildingCollection}" varStatus="status">
<c:if test="${status.index == 0}">
  <c:set var="floorCollection" value='${status.current.floor}'/>
</c:if>
        <option value="<c:out value="${status.current.buildingVariable}"/>"><c:out value="${status.current.building}"/></option>

  </c:forEach>

</select>
<%--
<c:set var="selectedBuilding" value=''/>
<select name="building" onchange="buildingChanged()">

  <c:forEach var="bldgFloorEditOvBean" items="${bldgFloorCollection}" varStatus="status">
    <c:set var="currentBuilding" value='${status.current.building}'/>
    <c:choose>
      <c:when test="${empty selectedBuilding}" >
        <c:set var="selectedBuilding" value=""/>
        <c:set var="floorCollection" value='${status.current.floor}'/>
      </c:when>
      <c:when test="${currentBuilding == selectedBuilding}" >
        <c:set var="floorCollection" value='${status.current.floor}'/>
      </c:when>
    </c:choose>

    <c:choose>
      <c:when test="${currentBuilding == selectedBuilding}">
        <option value="<c:out value="${currentBuilding}"/>" selected><c:out value="${status.current.building}"/></option>
      </c:when>
      <c:otherwise>
        <option value="<c:out value="${currentBuilding}"/>"><c:out value="${status.current.building}"/></option>
      </c:otherwise>
    </c:choose>
  </c:forEach>

</select>
--%>
</td>
<td <c:out value="${colorClass}"/> width="15%" height="38" CLASS="heading">
<select name="floor">
  <c:forEach var="floorEditOvBean" items="${floorCollection}" varStatus="status">

        <option value="<c:out value="${status.current.floorVariable}"/>"><c:out value="${status.current.floor}"/></option>

  </c:forEach>
</select>
<%--
<c:set var="selectedFloor" value=''/>
<select name="floor">
  <c:forEach var="floorEditOvBean" items="${floorCollection}" varStatus="status">
    <c:set var="currentFloor" value='${status.current.floor}'/>
    <c:choose>
      <c:when test="${empty selectedFloor}" >
        <c:set var="selectedFloor" value=""/>
      </c:when>
      <c:when test="${currentBuilding == selectedBuilding}" >
        <c:set var="floorCollection" value='${status.current.floor}'/>
      </c:when>
    </c:choose>

    <c:choose>
      <c:when test="${currentFloor == selectedFloor}">
        <option value="<c:out value="${currentFloor}"/>" selected><c:out value="${status.current.floor}"/></option>
      </c:when>
      <c:otherwise>
        <option value="<c:out value="${currentFloor}"/>"><c:out value="${status.current.floor}"/></option>
      </c:otherwise>
    </c:choose>
  </c:forEach>
</select>
--%>
</td>
<td <c:out value="${colorClass}"/> width="15%" height="38" CLASS="heading">
<html:submit property="submitAdd">
<fmt:message key="label.add"/>
</html:submit>
</html:form>
</td>
</tr>

<c:if test="${currentMsdsLoadViewBeanCollection != null}">
</table>
</c:if>
</DIV>
</body>
</html:html>

