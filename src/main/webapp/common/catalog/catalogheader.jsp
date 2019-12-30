<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="3" WIDTH="100%" CLASS="moveup">

<TR>
<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.facility"/>:</B>
</TD>

<TD WIDTH="15%" CLASS="announce">
<c:set var="selectedFacilityId" value='${param.facilityId}'/>
<select name="facilityId" onchange="facilityChanged()">

  <c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
    <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
    <c:choose>
      <c:when test="${empty selectedFacilityId}" >
        <c:set var="selectedFacilityId" value='${currentFacilityId}' />
        <c:set var="applicationSelectBeanCollection" value='${status.current.applicationBeanCollection}'/>
      </c:when>
      <c:when test="${currentFacilityId == selectedFacilityId}" >
        <c:set var="applicationSelectBeanCollection" value='${status.current.applicationBeanCollection}'/>
      </c:when>
    </c:choose>

    <c:choose>
      <c:when test="${currentFacilityId == selectedFacilityId}">
        <option value="<c:out value="${currentFacilityId}"/>" selected><c:out value="${status.current.facilityName}"/></option>
      </c:when>
      <c:otherwise>
        <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityName}"/></option>
      </c:otherwise>
    </c:choose>
  </c:forEach>
</select>
</TD>

<c:if test="${empty facilityOrAllCatalog}">
<c:set var="selectedActiveForFacility" value='${"checked"}' />
</c:if>
<%--<c:out value="${selectedActiveForFacility}"/>--%>

<TD WIDTH="10%" ALIGN="LEFT" CLASS="announce">
<%--
<html:radio property="facilityOrAllCatalog" onchange="facilityOrAllCatalogChanged()" value=""/>
<B><fmt:message key="label.activeforFacility"/></B>
--%>
<html:hidden property="facilityOrAllCatalog" value="All Catalogs"/>
<html:hidden property="activeOnly" value="Y"/>    
&nbsp;
</TD>

<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<%--
<html:checkbox property="workAreaApprovedOnly" onchange="workAreaApprovedChanged()" value="Y"/>
--%>
<html:hidden property="workAreaApprovedOnly" value=""/>
&nbsp;
</TD>

<TD ALIGN="LEFT" WIDTH="15%">
<%--
<B><fmt:message key="catalog.label.workAreaApprovedOnly"/></B>
--%>
&nbsp;
</TD>

</TR>
<TR>

<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.workarea"/>:</B>
</TD>

<TD  ALIGN="LEFT" WIDTH="15%">
<c:set var="selectedApplicationId" value='${param.applicationId}'/>
<c:if test="${empty selectedApplicationId}" >
  <c:set var="selectedApplicationId" value="My Work Areas"/>
</c:if>
<select name="applicationId" onchange="workAreaChanged()">
 <c:choose>
    <c:when test="${selectedApplicationId == 'My Work Areas'}">
      <option value="My Work Areas" selected>Please Select</option>
    </c:when>
    <c:otherwise>
      <option value="My Work Areas">Please Select</option>
    </c:otherwise>
  </c:choose>

  <c:set var="applicationCount" value='${0}'/>
  <c:forEach var="facLocAppBean" items="${applicationSelectBeanCollection}" varStatus="status">
    <c:set var="currentApplicationId" value='${status.current.application}'/>
    <c:set var="currentStatus"  value='${status.current.status}'/>
    <c:if test="${currentStatus == 'A'}">
    <c:set var="applicationCount" value='${applicationCount+1}'/>
    <c:choose>
      <c:when test="${currentApplicationId == selectedApplicationId}">
        <option value="<c:out value="${currentApplicationId}"/>" selected><c:out value="${status.current.applicationDesc}"/></option>
      </c:when>
      <c:otherwise>
        <option value="<c:out value="${currentApplicationId}"/>"><c:out value="${status.current.applicationDesc}"/></option>
      </c:otherwise>
    </c:choose>
   </c:if>
  </c:forEach>
</select>
</TD>

<TD WIDTH="10%" ALIGN="LEFT" CLASS="announce">
<%--<html:radio property="facilityOrAllCatalog" onchange="facilityOrAllCatalogChanged()" value="All Catalogs"/>
<B><fmt:message key="label.allcatalogs"/></B>--%>
</TD>

<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<%--<html:checkbox property="activeOnly" value="Y"/>--%>
</TD>

<TD WIDTH="15%" ALIGN="LEFT" CLASS="announce">
<%--<B><fmt:message key="label.activeOnly"/></B>--%>
</TD>

</TR>

<TR>
<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.search"/>:</B>
</TD>

<TD WIDTH="15%" ALIGN="LEFT" CLASS="announce">
<INPUT CLASS="HEADER" TYPE="text" NAME="searchText" value="<c:out value="${param.searchText}"/>" size="30">
</TD>

<TD WIDTH="15%" COLSPAN="2" CLASS="announce">
<html:submit property="submitSearch" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
     <fmt:message key="label.search"/>
</html:submit>
</TD>

<TD WIDTH="15%" ALIGN="LEFT" CLASS="announce">
<html:submit property="buttonCreateExcel" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
     <fmt:message key="label.createexcelfile"/>
</html:submit>
</TD>
</TR>
</TABLE>

<TABLE BORDER="0" CELLSPACING=1 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<TR>
<TD width="10%" CLASS="announce">
</TD>
<TD width="30%" CLASS="announce">
<html:errors/>
<c:set var="submitSearch" value='${param.submitSearch}'/>
 <c:if test="${empty submitSearch && empty buttonCreateExcel}">
  <I><fmt:message key="hub.proceed"/></I>
 </c:if>
<BR>
</TD>
</TR>
</TABLE>
<input name="catalog" id="catalog" value=" " type="hidden">
</DIV>
