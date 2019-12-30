
<DIV ID="RESULTSPAGE" STYLE="">

<TABLE BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING="1" CELLPADDING="2" WIDTH="100%">
<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

<c:forEach var="pkgOrderTrackPrOrderTrackBean" items="${pkgOrderTrackPrOrderTrackBeanCollection}" varStatus="status">

<c:if test="${status.index % 10 == 0}">
<c:set var="dataCount" value='${dataCount+1}'/>

<tr align="center">
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.status"/></TH>
<%--<TH width="5%" CLASS="results" height="38"><fmt:message key="label.customerpo"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.requestor"/></TH>--%>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.facility"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.workarea"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.catalog"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.partnum"/></TH>
<TH width="15%" CLASS="results" height="38"><fmt:message key="label.partdesc"/></TH>
<%--<TH width="5%" CLASS="results" height="38"><fmt:message key="label.packaging"/></TH>--%>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.type"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.mrline"/></TH>
<TH width="10%" CLASS="results" height="38"><fmt:message key="label.notes"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="ordertracking.label.released"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.needed"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.picked"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.delivered"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.lastdelivered"/></TH>
<%--<TH width="5%" CLASS="results" height="38"><fmt:message key="label.critical"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.item"/></TH>--%>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.approver"/></TH>
</tr>
</c:if>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value='blue'/>
   <c:set var="invisibleClass" value='INVISIBLEHEADBLUE'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='white'/>
   <c:set var="invisibleClass" value='INVISIBLEHEADWHITE'/>
  </c:otherwise>
</c:choose>

<c:set var="critical" value='${status.current.critical}'/>
<c:set var="requestLineStatus" value='${status.current.requestLineStatus}'/>

<c:if test="${critical == 'Y' || critical == 'y'}">
<c:set var="colorClass" value='red'/>
</c:if>

<c:if test="${critical == 'S' || critical == 's'}">
<c:set var="colorClass" value='pink'/>
</c:if>

<c:if test="${requestLineStatus == 'Pending Cancellation'}">
<c:set var="colorClass" value='yellow'/>
</c:if>

<c:if test="${requestLineStatus == 'Cancelled' || requestLineStatus == 'Rejected'}">
<c:set var="colorClass" value='black'/>
</c:if>

<TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="rowId<c:out value="${status.index}"/>">
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
var rowId  =  document.getElementById("rowId<c:out value="${status.index}"/>");
rowId.attachEvent('onmouseup',function () {
        catchevent('<c:out value="${status.index}"/>');});
// -->
</SCRIPT>

<c:set var="prNumber" value='${status.current.prNumber}'/>
<c:set var="lineItem" value='${status.current.lineItem}'/>
<c:set var="orderType" value='${status.current.orderType}'/>

<INPUT TYPE="hidden" NAME="colorClass<c:out value="${status.index}"/>" value="<c:out value="${colorClass}"/>" >
<INPUT TYPE="hidden" NAME="prNumber<c:out value="${status.index}"/>" value="<c:out value="${prNumber}"/>" >
<INPUT TYPE="hidden" NAME="lineItem<c:out value="${status.index}"/>" value="<c:out value="${lineItem}"/>" >
<INPUT TYPE="hidden" NAME="orderType<c:out value="${status.index}"/>" value="<c:out value="${orderType}"/>" >

<TD width="5%"><c:out value="${status.current.requestLineStatus}"/></TD>
<%--<TD width="5%"><c:out value="${status.current.poNumber}"/></TD>
<TD width="5%"><c:out value="${status.current.requestorName}"/></TD>--%>
<TD width="5%"><c:out value="${status.current.facilityId}"/></TD>
<TD width="5%"><c:out value="${status.current.applicationDesc}"/></TD>
<TD width="5%"><c:out value="${status.current.catalogId}"/></TD>
<TD width="5%"><c:out value="${status.current.facPartNo}"/></TD>
<TD width="15%"><c:out value="${status.current.partDescription}"/></TD>
<%--<TD width="5%"><c:out value="${status.current.packaging}"/></TD>--%>
<TD width="5%"><c:out value="${orderType}"/></TD>
<TD width="5%">
<c:choose>
   <c:when test="${empty prNumber || empty lineItem || prNumber == '0' }" >
     <%--<c:out value="${prNumber} - ${lineItem}"/>--%>
   </c:when>
   <c:otherwise>
     <c:out value="${prNumber} - ${lineItem}"/></A>
   </c:otherwise>
</c:choose>
</TD>

<c:set var="notes" value='${status.current.notes}'/>
 <c:choose>
   <c:when test="${empty notes || notes == ' '}" >
     <TD width="10%" ID="lineNotesTd<c:out value="${status.index}"/>">
   </c:when>
   <c:otherwise>
     <TD width="10%" ID="lineNotesTd<c:out value="${status.index}"/>" onMouseOver=style.cursor="hand">
     <SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
     <!--
     var rowId  =  document.getElementById("lineNotesTd<c:out value="${status.index}"/>");
     rowId.attachEvent('onclick',function () {
        showLineNotes('<c:out value="${status.index}"/>');});
     // -->
     </SCRIPT>
     <P ID="lineNoteslink<c:out value="${status.index}"/>">+</P>
     <DIV ID="lineNotes<c:out value="${status.index}"/>" CLASS="displaynone" onMouseOver=style.cursor="hand">
     <c:out value="${status.current.notes}"/>
     </DIV>
     <INPUT TYPE="hidden" NAME="notesVisible<c:out value="${status.index}"/>" value="No" >
   </c:otherwise>
 </c:choose>
</TD>
<TD width="5%"><c:out value="${status.current.releaseDate}"/></TD>
<TD width="5%"><c:out value="${status.current.requiredDatetime}"/></TD>
<TD width="5%">
<fmt:formatNumber maxFractionDigits="2" minFractionDigits="0"><c:out value="${status.current.totalPicked}"/></fmt:formatNumber> of <c:out value="${status.current.quantity}"/>
</TD>
<TD width="5%">
<fmt:formatNumber maxFractionDigits="2" minFractionDigits="0"><c:out value="${status.current.totalShipped}"/></fmt:formatNumber>
</TD>
<TD width="5%"><c:out value="${status.current.lastShipped}"/></TD>
<%--<TD width="5%"><c:out value="${status.current.critical}"/></TD>
<TD width="5%"><c:out value="${status.current.itemId}"/></TD>--%>
<TD width="5%"><c:out value="${status.current.approverName}"/></TD>
</TR>
</c:forEach>
</table>

<%--<c:if test="${resultCount != 0}">
<TABLE  BORDER="0" CELLSPACING=0 CELLPADDING=0 WIDTH="100%" CLASS="moveup">

<tr>
<TD HEIGHT="25" WIDTH="100%" VALIGN="MIDDLE" BGCOLOR="#000066">&nbsp;

</TD></tr>

</table>
</c:if>
--%>

<c:if test="${empty pkgOrderTrackPrOrderTrackBeanCollection}" >
<c:if test="${pkgOrderTrackPrOrderTrackBeanCollection != null}" >
<TABLE  BORDER="0" CELLSPACING=0 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<tr>
<TD HEIGHT="25" WIDTH="100%" VALIGN="MIDDLE" BGCOLOR="#a2a2a2">
<fmt:message key="main.nodatafound"/>
</TD></tr>

</c:if>
</c:if>
</TABLE>
