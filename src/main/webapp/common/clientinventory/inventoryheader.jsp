<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="3" WIDTH="100%" CLASS="moveup">
<TR>

<TD WIDTH="8%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.searchtext"/>:</B>&nbsp;
</TD>

<TD WIDTH="20%">
<INPUT CLASS="HEADER" TYPE="text" NAME="searchText" value="<c:out value="${param.searchText}"/>" maxlength="20" size="30">
</TD>

<%--<TD WIDTH="10%">
<html:checkbox property="noOor"/>
<B><fmt:message key="inventory.label.donotshowoor"/></B>
</TD>

<TD WIDTH="13%" ALIGN="LEFT" ROWSPAN="2" CLASS="announce">
<html:checkbox property="onHand"/>
<B><fmt:message key="inventory.label.onhand"/></B>
</TD>
--%>

<TD WIDTH="10%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="inventory.label.expireswithin"/></B>&nbsp;
</TD>

<TD WIDTH="10%" ALIGN="LEFT">
<INPUT CLASS="HEADER" TYPE="text" NAME="expiresWithin" value="<c:out value="${param.expiresWithin}"/>" onChange="checkexpiresWithin()" maxlength="4" size="3">
<fmt:message key="label.days"/>
</TD>

<TD WIDTH="10%" CLASS="announce">
&nbsp;
</TD>

</TR>
<TR>

<TD WIDTH="8%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.facility"/></B>&nbsp;
</TD>
<TD  ALIGN="LEFT" WIDTH="20%">
<c:set var="selectedFacility" value='${param.facilityId}'/>
<select name="facilityId" onchange="facilityChanged()">
  <c:forEach var="facilityIgViewOvBean" items="${facilityIgViewOvBeanCollection}" varStatus="status">
    <c:set var="currentFacility" value='${status.current.facilityId}'/>
    <c:choose>
      <c:when test="${empty selectedFacility}" >
        <c:set var="selectedFacility" value=""/>
        <c:set var="inventoryGroupNameOvBeanCollection" value='${status.current.inventoryGroups}'/>
      </c:when>
      <c:when test="${currentFacility == selectedFacility}" >
        <c:set var="inventoryGroupNameOvBeanCollection" value='${status.current.inventoryGroups}'/>
      </c:when>
    </c:choose>

    <c:choose>
      <c:when test="${currentFacility == selectedFacility}">
        <option value="<c:out value="${currentFacility}"/>" selected><c:out value="${status.current.facilityName}"/></option>
      </c:when>
      <c:otherwise>
        <option value="<c:out value="${currentFacility}"/>"><c:out value="${status.current.facilityName}"/></option>
      </c:otherwise>
    </c:choose>
  </c:forEach>
</select>
</TD>

<TD WIDTH="15%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="inventory.label.expiresafter"/></B>
</TD>

<TD WIDTH="10%" ALIGN="LEFT" CLASS="announce">
<INPUT CLASS="HEADER" TYPE="text" NAME="expiresAfter" value="<c:out value="${param.expiresAfter}"/>" onChange="checkexpiresAfter()" maxlength="4" size="3">
<fmt:message key="label.days"/>
</TD>

<TD WIDTH="10%" ALIGN="LEFT" CLASS="announce">
<html:submit property="submitSearch" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
     <fmt:message key="label.search"/>
</html:submit>
</TD>

</TR>
<TR>

<TD WIDTH="8%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.inventory"/></B>&nbsp;
</TD>
<TD ALIGN="LEFT" WIDTH="20%">
<select name="inventory">
<c:set var="selectedInventory" value='${param.inventory}'/>
  <c:forEach var="inventoryGroupNameOvBean" items="${inventoryGroupNameOvBeanCollection}" varStatus="status">
    <c:set var="currentSelectedInventory" value='${status.current.inventoryGroup}'/>
    <c:choose>
      <c:when test="${selectedInventory == currentSelectedInventory}">
        <option value="<c:out value="${currentSelectedInventory}"/>" selected><c:out value="${status.current.inventoryGroupName}"/></option>
      </c:when>
      <c:otherwise>
        <option value="<c:out value="${currentSelectedInventory}"/>"><c:out value="${status.current.inventoryGroupName}"/></option>
      </c:otherwise>
    </c:choose>
  </c:forEach>
</select>
</TD>

<%--<TD WIDTH="13%" ALIGN="LEFT" CLASS="announce">
<html:checkbox property="onOrder"/>
<B><fmt:message key="label.onorder"/></B>
--%>

</TD>
<TD WIDTH="15%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="inventory.label.arriveswithin"/></B>
</TD>

<TD WIDTH="10%" CLASS="announce">
<INPUT CLASS="HEADER" TYPE="text" NAME="arrivesWithin" value="<c:out value="${param.arrivesWithin}"/>" onChange="checkarrivesWithin()" maxlength="4" size="3">
<fmt:message key="label.days"/>
</TD>

<TD WIDTH="10%" ALIGN="LEFT" CLASS="announce">
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
  <c:set var="showResults" value='showSearchResults'/>
 </c:if>
<BR>
</TD>
</TR>
</TABLE>

</DIV>