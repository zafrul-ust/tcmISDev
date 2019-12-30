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
<title>
<fmt:message key="searchmsds.title"/>
</title>
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
<B><fmt:message key="searchmsds.title"/></B>
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

<html:form action="/searchmsds.do" enctype="multipart/form-data">
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="3" WIDTH="100%" CLASS="moveup">
<TR VALIGN="MIDDLE">
<TD WIDTH="20%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.facility"/>:</B>&nbsp;
</TD>
<TD WIDTH="20%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<c:set var="selectedFacilityId" value='${requestScope.searchMsdsForm.map.facilityId}'/>
<select name="facilityId" onchange="facilityChanged()">
  <c:forEach var="facBldgFloorDeptEditOvBean" items="${facBldgFloorDeptEditOvBeanCollection}" varStatus="status">
  <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
  <c:choose>
   <c:when test="${selectedFacilityId == null}" >
    <c:set var="selectedFacilityId" value="${facBldgFloorDeptEditOvBeanCollection[0].facilityId}"/>
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
<TD WIDTH="20%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="searchmsds.label.hazardclassification"/>:</B>&nbsp;
</TD>
<TD WIDTH="20%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<html:select property="hazardClassification" styleClass="HEADER" size="1">
  <html:option value="ALL">All</html:option>
  <html:options collection="vvMsdsHazardClassificationBeanCollection" property="hazardClassification" labelProperty="hazardClassificationDesc"/>
</html:select>
</TD>
<TD WIDTH="20%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<%--<tcmis:isLoggedIn indicator="true">
<A HREF="/tcmIS/miller/logout.do"><fmt:message key="label.logout"/></A>
</tcmis:isLoggedIn>
<tcmis:isLoggedIn indicator="false">
<A HREF="/tcmIS/miller/showlogin.do"><fmt:message key="button.login"/></A>
</tcmis:isLoggedIn>--%>
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="20%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="searchmsds.label.department"/>:</B>&nbsp;
</TD>

<TD WIDTH="20%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<c:set var="selectedDepartment" value='${requestScope.searchMsdsForm.map.department}'/>
<select name="department" onchange="departmentChanged()">
  <c:forEach var="deptBldgFloorEditOvBean" items="${deptBldgFloorCollection}" varStatus="status">
    <c:set var="currentDepartment" value='${status.current.departmentVariable}'/>
    <c:choose>
      <c:when test="${empty selectedDepartment}" >
        <c:set var="selectedDepartment" value="${deptBldgFloorCollection[0].departmentVariable}"/>
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

<TD WIDTH="20%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.status"/>:</B>&nbsp;
</TD>
<TD WIDTH="20%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<html:select property="status" styleClass="HEADER" size="1">
  <html:option value="ALL">All</html:option>
  <html:options collection="vvCustomerMsdsStatusBeanCollection" property="status" labelProperty="status"/>
</html:select>
</TD>
<TD WIDTH="20%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<tcmis:isLoggedIn indicator="true">
<tcmis:facilityPermission indicator="true" userGroupId="MillerUpdateMsds" facilityId="All">
<A HREF="/tcmIS/miller/showaddmsds.do"><fmt:message key="searchmsds.label.addmsds"/></A>
</tcmis:facilityPermission>
</tcmis:isLoggedIn>

&nbsp;
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="20%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="searchmsds.label.building"/>:</B>&nbsp;
</TD>
<TD WIDTH="20%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<c:set var="selectedBuilding" value='${requestScope.searchMsdsForm.map.building}'/>
<select name="building" onchange="buildingChanged()">
  <c:forEach var="bldgFloorEditOvBean" items="${bldgFloorCollection}" varStatus="status">
    <c:set var="currentBuilding" value='${status.current.buildingVariable}'/>
    <c:choose>
      <c:when test="${empty selectedBuilding}" >
        <c:set var="selectedBuilding" value="${bldgFloorCollection[0].buildingVariable}"/>
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
<TD CLASS="announce" HEIGHT="35" WIDTH="20%" ALIGN="RIGHT">
<B><fmt:message key="label.sortby"/>:</B>&nbsp;
</TD>
<TD CLASS="announce" HEIGHT="35" WIDTH="20%" ALIGN="LEFT">
<html:select property="sortBy">
  <html:option value="MATERIAL_ID">Material</html:option>
  <html:option value="CAT_PART_NO_STRING">Part Number</html:option>
  <html:option value="TRADE_NAME">Trade Name</html:option>
  <html:option value="MANUFACTURER">Manufacturer</html:option>
</html:select>
</TD>
<TD WIDTH="20%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
&nbsp;
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="20%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="searchmsds.label.floor"/>:</B>&nbsp;
</TD>
<TD WIDTH="20%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<c:set var="selectedFloor" value='${requestScope.searchMsdsForm.map.floor}'/>
<select name="floor">
  <c:forEach var="floorEditOvBean" items="${floorCollection}" varStatus="status">
    <c:set var="currentFloor" value='${status.current.floorVariable}'/>
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
<TD WIDTH="20%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="searchmsds.label.criteria"/>:</B>&nbsp;
</TD>
<TD WIDTH="20%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<html:text property="search"/>
</TD>
<TD WIDTH="20%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<html:submit property="submitSearch">
  <fmt:message key="label.search"/>
</html:submit>
</TD>
</TR>

</TABLE>
</html:form>
<TABLE  BORDER="0" CELLSPACING=0 CELLPADDING=0 WIDTH="100%">
<tr>
<td>
</TD>
</tr>
</table>

<c:if test="${millerMsdsSearchBeanCollection != null}">
<c:set var="resultCount" value='0'/>

<TABLE BORDER="0" BGCOLOR="#000000" CELLSPACING="1" CELLPADDING="2" WIDTH="100%" CLASS="moveup">
</c:if>
<c:forEach var="millerMsdsSearchBean" items="${millerMsdsSearchBeanCollection}" varStatus="status">
<c:if test="${status.index == 0}">
<tr align="center">
<TH width="10%"  height="38"><fmt:message key="label.msds"/></TH>
<TH width="10%"  height="38"><fmt:message key="searchmsds.label.department"/></TH>
<TH width="10%"  height="38"><fmt:message key="label.status"/></TH>
<TH width="10%"  height="38"><fmt:message key="searchmsds.label.hazardclassification"/></TH>
<TH width="10%"  height="38"><fmt:message key="label.tradename"/></TH>
<TH width="10%"  height="38"><fmt:message key="label.partnumber"/></TH>
<TH width="10%"  height="38"><fmt:message key="label.manufacturer"/></TH>
<TH width="10%"  height="38">&nbsp;</TH>
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

<td <c:out value="${colorClass}"/> width="10%" height="38">
<c:if test="${status.current.onLine =='Y'}">
<A HREF="<c:out value="${status.current.content}"/>" TARGET="NEW">
</c:if>
 <c:out value="${status.current.customerMsdsNo}"/>
<c:if test="${status.current.onLine =='Y'}">
</A>
</c:if>
</td>
<td <c:out value="${colorClass}"/> width="10%" height="38">
  <c:out value="${status.current.department}"/>
</td>
<td <c:out value="${colorClass}"/> width="10%" height="38">
  <c:out value="${status.current.status}"/>
</td>
<td <c:out value="${colorClass}"/> width="10%" height="38">
  <c:out value="${status.current.hazardClassification}"/>
</td>
<td <c:out value="${colorClass}"/> width="10%" height="38">
  <c:out value="${status.current.tradeName}"/>
</td>
<td <c:out value="${colorClass}"/> width="10%" height="38">
  <c:out value="${status.current.catPartNoString}"/>
</td>
<td <c:out value="${colorClass}"/> width="10%" height="38">
  <c:out value="${status.current.manufacturer}"/>
</td>
<td <c:out value="${colorClass}"/> width="10%" height="38">
<tcmis:isLoggedIn indicator="true">
<tcmis:facilityPermission indicator="true" userGroupId="MillerUpdateMsds" facilityId="All">
  <html:form action="/showupdatemsds.do">
    <INPUT TYPE="hidden" name="msds" value="<c:out value="${status.current.customerMsdsNo}"/>"/>
    <html:submit property="submitSearch">
      <fmt:message key="button.update"/>
    </html:submit>
  </html:form>
</tcmis:facilityPermission>
<tcmis:facilityPermission indicator="false" userGroupId="MillerUpdateMsds" facilityId="All">
  <html:form action="/viewmsdslocation.do">
    <INPUT TYPE="hidden" name="msds" value="<c:out value="${status.current.customerMsdsNo}"/>">
    <html:submit property="submit">
      <fmt:message key="searchmsds.button.view"/>
    </html:submit>
  </html:form>
</tcmis:facilityPermission>
</tcmis:isLoggedIn>

<tcmis:isLoggedIn indicator="false">
  <html:form action="/viewmsdslocation.do">
    <INPUT TYPE="hidden" name="msds" value="<c:out value="${status.current.customerMsdsNo}"/>">
    <html:submit property="submit">
      <fmt:message key="searchmsds.button.view"/>
    </html:submit>
  </html:form>
</tcmis:isLoggedIn>
</td>
</tr>

</c:forEach>
<c:if test="${resultCount == 0}">
<tr>
<td <c:out value="${colorClass}"/> width="15%" height="38" CLASS="heading">
<fmt:message key="searchmsds.label.nodata"/>
</td>
</tr>
</c:if>
<c:if test="${millerMsdsSearchBeanCollection != null}">
</table>
</c:if>

</DIV>
</body>
</html:html>
