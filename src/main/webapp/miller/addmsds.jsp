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

<!-- For Calendar support -->
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
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
<fmt:message key="addmsds.title"/>
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
<B><fmt:message key="addmsds.title"/></B>
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

<html:form action="/addmsds.do" enctype="multipart/form-data">

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="3" WIDTH="100%" CLASS="moveup">

<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.msds"/>:</B>&nbsp;
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<c:out value="${requestScope.newMsds}"/>
<html:hidden property="msds" value="${requestScope.newMsds}"/>
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
&nbsp;
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
&nbsp;
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.status"/>:</B>&nbsp;
</TD>

<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<html:select property="status" styleClass="HEADER" size="1">
  <html:options collection="vvCustomerMsdsStatusBeanCollection" property="status" labelProperty="status"/>
</html:select>
</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="searchmsds.label.hazardclassification"/>:</B>&nbsp;
</TD>

<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<html:select property="hazardClassification" styleClass="HEADER" size="1">
  <html:option value=""></html:option>
  <html:options collection="vvMsdsHazardClassificationBeanCollection" property="hazardClassification" labelProperty="hazardClassification"/>
</html:select>
</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.tradename"/>:</B>&nbsp;
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<html:text property="tradeName"/>
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
&nbsp;
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
&nbsp;
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.manufacturername"/>:</B>&nbsp;
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<html:text property="manufacturerName"/>
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
&nbsp;
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
&nbsp;
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD colspan="4" WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
&nbsp;
</TD>
</TR>
<%--
<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.revisiondate"/>:</B>&nbsp;
</TD>

<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce"><c:out value='${param.revisionDate}'/>

<SCRIPT LANGUAGE="JavaScript" ID="js17">
var now = new Date();
var cal17 = new CalendarPopup("testdiv1");

</SCRIPT>

<c:choose>
<c:when test="${requestScope.processMsdsForm.revisionDate == null}" >
<INPUT CLASS="HEADER" TYPE="text" NAME="revisionDate" value="<tcmis:getDateTag numberOfDaysFromToday="0"/>" maxlength="10" size="10">
</c:when>
<c:otherwise>
<INPUT CLASS="HEADER" TYPE="text" NAME="revisionDate" value="<c:out value='${requestScope.processMsdsForm.revisionDate}'/>" maxlength="10" size="10">
</c:otherwise>
</c:choose>
<FONT SIZE="4">
<A HREF="#" onClick="cal17.select(document.forms[0].revisionDate,'anchor17','MM/dd/yyyy'); return false;"  NAME="revisionDate" ID="anchor17">&diams;</A>
</FONT>
</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
</TR>
--%>

<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.revisiondate"/>:</B>&nbsp;
</TD>

<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">

<!--<c:choose>
<c:when test="${requestScope.processMsdsForm.revisionDate == null}" >
<INPUT CLASS="HEADER" TYPE="text" NAME="revisionDate" value="<tcmis:getDateTag numberOfDaysFromToday="0"/>" maxlength="10" size="10">
</c:when>
<c:otherwise>
<INPUT CLASS="HEADER" TYPE="text" NAME="revisionDate" value="<c:out value='${requestScope.processMsdsForm.revisionDate}'/>" maxlength="10" size="10">
</c:otherwise>
</c:choose>
<FONT SIZE="4"><a href="javascript: void(0);" ID="revisionDate" onClick="return getCalendar(document.processMsdsForm.revisionDate);">&diams;</a></FONT>

-->
 <input type="text" readonly name="revisionDate" id="revisionDate" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}" />" 
   onClick="return getCalendar(document.processMsdsForm.revisionDate);" size="8" maxlength="10" class="header"></TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
</TR>
<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="processmsds.label.selectfile"/>:</B>&nbsp;
</TD>

<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<html:file property="theFile" value="${requestScope.processMsdsForm.theFile}" size="50"/>
</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.facility"/>:</B>&nbsp;
</TD>

<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<c:set var="selectedFacilityId" value='${requestScope.processMsdsForm.facilityId}'/>
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

</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="processmsds.label.department"/>:</B>&nbsp;
</TD>

<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<c:set var="selectedDepartment" value='${requestScope.processMsdsForm.department}'/>
<select name="department" onchange="departmentChanged()">
<%--
  <option value=""><fmt:message key="label.all"/></option>
--%>
  <c:forEach var="deptBldgFloorEditOvBean" items="${deptBldgFloorCollection}" varStatus="status">
    <c:set var="currentDepartment" value='${status.current.departmentVariable}'/>
    <c:choose>
      <c:when test="${selectedDepartment == null}" >
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

</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="processmsds.label.building"/>:</B>&nbsp;
</TD>

<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<c:set var="selectedBuilding" value='${requestScope.processMsdsForm.building}'/>
<select name="building" onchange="buildingChanged()">
<%--
  <option value=""><fmt:message key="label.all"/></option>
--%>
  <c:forEach var="bldgFloorEditOvBean" items="${bldgFloorCollection}" varStatus="status">
    <c:set var="currentBuilding" value='${status.current.buildingVariable}'/>
    <c:choose>
      <c:when test="${selectedBuilding == null}" >
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
</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="processmsds.label.floor"/>:</B>&nbsp;
</TD>

<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<c:set var="selectedFloor" value='${requestScope.processMsdsForm.floor}'/>
<select name="floor">
<%--
  <option value=""><fmt:message key="label.all"/></option>
--%>
  <c:forEach var="floorEditOvBean" items="${floorCollection}" varStatus="status">
    <c:set var="currentFloor" value='${status.current.floorVariable}'/>
    <c:choose>
      <c:when test="${selectedFloor == null}" >
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
</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
<html:submit property="submitAdd" value="Add"/>
</TD>
<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
</TR>
</TABLE>

<TABLE  BORDER="0" CELLSPACING=0 CELLPADDING=0 WIDTH="100%">
<tr>
<td>
</TD>
</tr>
</table>

</html:form>
</DIV>
<DIV ID="testdiv1" class="drag" STYLE="position:absolute;visibility:hidden;background-color:white;layer-background-color:white;"></DIV>
</body>
</html:html>

