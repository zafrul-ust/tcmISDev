<DIV ID="RESULTSPAGE" STYLE="">

<TABLE BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING="1" CELLPADDING="2" WIDTH="100%">
<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

<c:forEach var="pkgInventoryDetailWebPrInventoryBean" items="${pkgInventoryDetailWebPrInventoryBeanCollection}" varStatus="status">

<c:if test="${status.index % 10 == 0}">
<c:set var="dataCount" value='${dataCount+1}'/>

<TR align="center">
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.catalog"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.part"/></TH>
<TH width="15%" CLASS="results" height="38"><fmt:message key="label.description"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.type"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="inventory.label.setpoint"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.invengroup"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="inventory.label.numperpart"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.available"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.held"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.onorder"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.inpurchasing"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.item"/></TH>
<TH width="10%" CLASS="results" height="38"><fmt:message key="inventory.label.componentdescription"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="inventory.label.packaging"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.manufacturer"/></TH>
<TH width="10%" CLASS="results" height="38"><fmt:message key="label.mfgpartno"/></TH>
</tr>
</c:if>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value='blue'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='white'/>
  </c:otherwise>
</c:choose>

<TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="rowId<c:out value="${status.index}"/>">
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
var rowId  =  document.getElementById("rowId<c:out value="${status.index}"/>");
rowId.attachEvent('onmouseup',function () {
        catchevent('<c:out value="${status.index}"/>');});
// -->
</SCRIPT>

<c:set var="itemId" value='${status.current.itemId}'/>
<c:set var="inventoryGroup" value='${status.current.inventoryGroup}'/>
<c:set var="catalogId" value='${status.current.catalogId}'/>
<c:set var="catPartNo" value='${status.current.catPartNo}'/>
<c:set var="partGroupNo" value='${status.current.partGroupNo}'/>

<c:set var="itemCollection"  value='${status.current.itemCollection}'/>
<bean:size id="listSize" name="itemCollection"/>
<c:set var="mainRowSpan" value='${status.current.rowSpan}' />

<INPUT TYPE="hidden" NAME="colorClass<c:out value="${status.index}"/>" value="<c:out value="${colorClass}"/>" >
<INPUT TYPE="hidden" NAME="childRowsSize<c:out value="${status.index}"/>" value="<c:out value="${listSize}"/>" >
<INPUT TYPE="hidden" NAME="itemId<c:out value="${status.index}"/>" value="<c:out value="${itemId}"/>" >
<INPUT TYPE="hidden" NAME="inventoryGroup<c:out value="${status.index}"/>" value="<c:out value="${inventoryGroup}"/>" >
<INPUT TYPE="hidden" NAME="inventoryGroupName<c:out value="${status.index}"/>" value="<c:out value='${status.current.inventoryGroupName}'/>" >
<INPUT TYPE="hidden" NAME="catalogId<c:out value="${status.index}"/>" value="<c:out value="${catalogId}"/>" >
<INPUT TYPE="hidden" NAME="catPartNo<c:out value="${status.index}"/>" value="<c:out value="${catPartNo}"/>" >
<INPUT TYPE="hidden" NAME="partGroupNo<c:out value="${status.index}"/>" value="<c:out value="${partGroupNo}"/>" >
<INPUT TYPE="hidden" NAME="issueGeneration<c:out value="${status.index}"/>" value="<c:out value='${status.current.issueGeneration}'/>" >

<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${catalogId}"/></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${catPartNo}"/></TD>
<TD width="15%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.partDescription}"/></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.stockingMethod}"/></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.setPoints}"/></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.inventoryGroupName}"/></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="0"><c:out value="${status.current.qtyAvailable}"/></fmt:formatNumber></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="0"><c:out value="${status.current.qtyHeld}"/></fmt:formatNumber></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="0"><c:out value="${status.current.qtyOnOrder}"/></fmt:formatNumber></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="0"><c:out value="${status.current.qtyInPurchasing}"/></fmt:formatNumber></TD>

<c:forEach var="pkgInventoryDetailItemBean" items="${itemCollection}" varStatus="status1">
  <c:if test="${status1.index > 0 && listSize > 1 }">
   <TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="childRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/>">
   <SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
   <!--
    var childRowId  =  document.getElementById("childRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/>");
     childRowId.attachEvent('onmouseup',function () {
      catchevent('<c:out value="${status.index}"/>');});
    // -->
    </SCRIPT>
  </c:if>

  <c:set var="componentCollection"  value='${status1.current.componentCollection}'/>
  <bean:size id="componentSize" name="componentCollection"/>
  <INPUT TYPE="hidden" NAME="grandChildRowsSize<c:out value="${status.index}"/><c:out value="${status1.index}"/>" value="<c:out value="${componentSize}"/>" >

<TD width="5%" rowspan="<c:out value="${componentSize}"/>"><c:out value="${status1.current.itemsPerPart}"/></TD>
  <TD width="5%" rowspan="<c:out value="${componentSize}"/>"><c:out value="${status1.current.itemId}"/></TD>

  <c:forEach var="pkgInventoryDetailComponentBean" items="${componentCollection}" varStatus="status2">
    <c:if test="${status2.index > 0 && componentSize > 1 }">
     <TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="grnadChildRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/><c:out value="${status2.index}"/>">
     <SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
     <!--
     var childRowId  =  document.getElementById("grnadChildRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/><c:out value="${status2.index}"/>");
     childRowId.attachEvent('onmouseup',function () {
      catchevent('<c:out value="${status.index}"/>');});
     // -->
     </SCRIPT>
    </c:if>

    <TD width="5%"><c:out value="${status2.current.materialDesc}"/></TD>
    <TD width="5%"><c:out value="${status2.current.packaging}"/></TD>
    <TD width="5%"><c:out value="${status2.current.mfgDesc}"/></TD>
    <TD width="10%"><c:out value="${status2.current.mfgPartNo}"/></TD>

    <c:if test="${status2.index > 0 || componentSize ==1 }">
    </TR>
    </c:if>
  </c:forEach>

  <c:if test="${status1.index > 0 || listSize ==1 }">
   </TR>
  </c:if>
 </c:forEach>

</c:forEach>
</table>

<c:if test="${empty pkgInventoryDetailWebPrInventoryBeanCollection}" >
<c:if test="${pkgInventoryDetailWebPrInventoryBeanCollection != null}" >
<TABLE  BORDER="0" CELLSPACING=0 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<tr>
<TD HEIGHT="25" WIDTH="100%" VALIGN="MIDDLE" BGCOLOR="#a2a2a2">
<fmt:message key="main.nodatafound"/>
</TD>
</tr>
</TABLE>
</c:if>
</c:if>
