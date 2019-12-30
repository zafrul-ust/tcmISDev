
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="3" WIDTH="100%" CLASS="moveup">

<TR>
<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.requestor"/>:</B>&nbsp;
</TD>

<TD WIDTH="10%">

<c:set var="submitSearch" value='${param.submitSearch}'/>
<c:set var="requestorId" value='${param.requestorId}'/>
<c:set var="requestorName" value='${param.requestorName}'/>

<%--<c:if test="${empty submitSearch && empty buttonCreateExcel}">
 <c:if test="${empty requestorId}">
  <c:set var="requestorId" value='${sessionScope.personnelBean.personnelId}'/>
 </c:if>
 <c:if test="${empty requestorName}">
  <c:set var="requestorName" value='${sessionScope.personnelBean.lastName}, ${sessionScope.personnelBean.firstName} ${sessionScope.personnelBean.midInitial}'/>
 </c:if>
</c:if>--%>

<INPUT CLASS="HEADER" TYPE="text" NAME="requestorName" value="<c:out value="${requestorName}"/>" onChange="invalidateRequestor()" size="20">
<BUTTON CLASS="SUBMIT" onmouseover="className='SUBMITHOVER'" onMouseout="className='SUBMIT'" name="searchsupplierlike" value="..." OnClick="getPersonnel()"><IMG src="/images/search_small.gif" alt="Supplier"></BUTTON>
<INPUT TYPE="hidden" NAME="requestorId" value="<c:out value="${requestorId}"/>" >
</TD>

<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.facility"/>:</B>&nbsp;
</TD>

<TD WIDTH="15%" CLASS="announce">
<c:set var="selectedFacilityId" value='${param.facilityId}'/>
<select name="facilityId" onchange="facilityChanged()">
    <c:choose>
      <c:when test="${selectedFacilityId == 'All'}">
        <option value="All" selected>All</option>
        <c:if test="${empty selectedFacilityId}" >
          <c:set var="selectedFacilityId" value="All"/>
        </c:if>
      </c:when>
      <c:otherwise>
        <option value="All">All</option>
      </c:otherwise>
    </c:choose>

    <c:choose>
      <c:when test="${selectedFacilityId == 'My Facilities'}">
        <option value="My Facilities" selected>My Facilities</option>
        <c:if test="${empty selectedFacilityId}" >
          <c:set var="selectedFacilityId" value="My Facilities"/>
        </c:if>
      </c:when>
      <c:otherwise>
        <option value="My Facilities">My Facilities</option>
      </c:otherwise>
    </c:choose>

  <c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
    <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
    <c:choose>
      <c:when test="${empty selectedFacilityId}" >
        <c:set var="selectedFacilityId" value=""/>
        <%--<c:set var="applicationBeanCollection" value='${status.current.applicationBeanCollection}'/>--%>
      </c:when>
      <c:when test="${currentFacilityId == selectedFacilityId}" >
        <c:set var="applicationSelectBeanCollection" value='${status.current.applicationBeanCollection}'/>
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

<TD WIDTH="10%" CLASS="announce">
<html:radio property="status" value=""/>
<B><fmt:message key="label.any"/></B>
<html:radio property="status" value="DRAFT"/>
<B><fmt:message key="label.pending"/></B>
<html:radio property="status" value="OPEN"/>
<B><fmt:message key="label.open"/></B>
</TD>

</TR>
<TR>

<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<html:checkbox property="needMyApproval" onchange="checkDisabled()" value="Y"/>
&nbsp;
</TD>

<TD  ALIGN="LEFT" WIDTH="10%">
<B><fmt:message key="ordertracking.label.needmyapproval"/></B>
</TD>

<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.workarea"/></B>
</TD>

<TD  ALIGN="LEFT" WIDTH="15%">
<c:set var="selectedApplicationId" value='${param.applicationId}'/>
<c:if test="${empty selectedApplicationId}" >
  <c:set var="selectedApplicationId" value="All"/>
</c:if>
<select name="applicationId">
 <c:choose>
    <c:when test="${selectedApplicationId == 'All'}">
      <option value="All" selected>All</option>
    </c:when>
    <c:otherwise>
      <option value="All">All</option>
    </c:otherwise>
  </c:choose>

  <c:choose>
    <c:when test="${selectedApplicationId == 'My Work Areas' && !selectedApplicationId == 'All'}">
      <option value="My Work Areas" selected>My Facilities</option>
    </c:when>
    <c:when test="${selectedApplicationId != 'My Work Areas' && selectedApplicationId == 'All'}">

     </c:when>
     <c:when test="${selectedApplicationId != 'My Work Areas'}">
      <option value="My Work Areas">My Work Areas</option>
     </c:when>
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

<TD WIDTH="15%" CLASS="announce">
<html:radio property="status" value="DELIVERED"/><B><fmt:message key="ordertracking.label.fullydelivered"/></B>
<INPUT CLASS="HEADER" TYPE="text" NAME="deliveredSinceDays" value="<c:out value="${param.deliveredSinceDays}"/>" size="5"><B><fmt:message key="label.days"/></B>
</TD>

</TR>
<TR>
<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">

<B><fmt:message key="label.searchby"/>:</B>&nbsp;
</TD>

<TD  ALIGN="LEFT" WIDTH="10%">
<html:select property="searchWhat">
  <html:options collection="searchDropDownBeanCollection" name="SearchDropDownBean" labelProperty="displayName" property="databaseAction"/>
</html:select>
</TD>

<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<html:select property="searchType">
  <html:options collection="searchtypeDropDownBeanCollection" name="SearchDropDownBean" labelProperty="displayName" property="databaseAction"/>
</html:select>
</TD>

<TD WIDTH="15%" ALIGN="LEFT" CLASS="announce">
<INPUT CLASS="HEADER" TYPE="text" NAME="searchText" value="<c:out value="${param.searchText}"/>" size="20">
</TD>
<TD WIDTH="15%" ALIGN="LEFT" CLASS="announce">
<html:radio property="status" value="CANCELED"/><B><fmt:message key="ordertracking.label.cancelled"/></B>
</TD>
</TR>
<TR>

<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.showlegend"/>:</B>&nbsp;
</TD>

<TD width="5%" COLSPAN="2"><FONT SIZE="5" COLOR=#FF9999>&diams;</a></FONT>Critical Orders
&nbsp;<FONT SIZE="5" COLOR=#ffff00>&diams;</a></FONT>Pending Cancellation
&nbsp;<FONT SIZE="5" COLOR=#OOOOOO>&diams;</a></FONT>Cancelled/Rejected
</TD>

<TD WIDTH="15%">
<html:checkbox property="critical" value="Y"/><B><fmt:message key="ordertracking.label.criticalonly"/></B>
</TD>

<TD WIDTH="15%" CLASS="announce">
<html:submit property="submitSearch" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
     <fmt:message key="label.search"/>
</html:submit>
&nbsp;&nbsp;&nbsp;
<html:submit property="buttonCreateExcel" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
     <fmt:message key="label.createexcelfile"/>
</html:submit>
</TD>
</TR>

</TABLE>

<c:set var="showResults" value=''/>

<TABLE BORDER="0" CELLSPACING=1 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<TR>
<TD width="10%" CLASS="announce">
</TD>
<TD width="30%" CLASS="announce">
<html:errors/>

 <c:if test="${empty submitSearch && empty buttonCreateExcel}">
  <I><fmt:message key="hub.proceed"/></I>
  <c:set var="showResults" value='showSearchResults'/>
 </c:if>
<BR>
</TD>
</TR>
</TABLE>

</DIV>
