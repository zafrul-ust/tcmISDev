
<DIV ID="RESULTSPAGE" STYLE="">

<TABLE BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING="1" CELLPADDING="2" WIDTH="100%">
<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

<c:forEach var="prCatalogScreenSearchBean" items="${prCatalogScreenSearchBeanCollection}" varStatus="status">

<c:if test="${status.index % 10 == 0}">
<c:set var="dataCount" value='${dataCount+1}'/>

<tr align="center">
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.catalog"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.part"/></TH>
<TH width="15%" CLASS="results" height="38"><fmt:message key="label.description"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.type"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.price"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="catalog.label.shelflife"/></TH>
<TH width="8%" CLASS="results" height="38"><fmt:message key="catalog.label.qtyofuomperitem"/></TH>
<%--<TH width="5%" CLASS="results" height="38"><fmt:message key="label.partuom"/></TH>--%>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.status"/></TH>
<%--<TH width="5%" CLASS="results" height="38"><fmt:message key="catalog.label.numperpart"/></TH>--%>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.item"/></TH>
<TH width="15%" CLASS="results" height="38"><fmt:message key="inventory.label.componentdescription"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="inventory.label.packaging"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.grade"/></TH>
<TH width="10%" CLASS="results" height="38"><fmt:message key="label.manufacturer"/></TH>
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

<c:set var="itemCollection"  value='${status.current.itemCollection}'/>
<bean:size id="listSize" name="itemCollection"/>
<c:set var="mainRowSpan" value='${status.current.rowSpan}' />


<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.catalogId}"/>
<INPUT TYPE="hidden" NAME="colorClass<c:out value="${status.index}"/>" value="<c:out value="${colorClass}"/>" >
<INPUT TYPE="hidden" NAME="childRowsSize<c:out value="${status.index}"/>" value="<c:out value="${listSize}"/>" >
</TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.catPartNo}"/></TD>
<TD width="15%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.partDescription}"/></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.stockingMethod}"/></TD>

<c:set var="finalPrice" value='${0}'/>
<c:set var="facilityOrAllCatalog" value='${param.facilityOrAllCatalog}'/>
<c:set var="minCatalogPrice" value='${status.current.minCatalogPrice}'/>
<c:set var="maxCatalogPrice" value='${status.current.maxCatalogPrice}'/>
<c:choose>
 <c:when test="${facilityOrAllCatalog == 'All Catalog'}" >
  <c:choose>
   <c:when test="${!empty minCatalogPrice && !empty maxCatalogPrice}">
     <c:set var="finalPrice" value='${maxCatalogPrice}'/>
   </c:when>
   <c:otherwise>
     <c:if test="${!empty minCatalogPrice}">
        <c:set var="finalPrice" value='${minCatalogPrice}'/>
     </c:if>
     <c:if test="${!empty maxCatalogPrice}">
        <c:set var="finalPrice" value='${maxCatalogPrice}'/>
     </c:if>
   </c:otherwise>
  </c:choose>
 </c:when>
 <c:otherwise>
  <c:set var="finalPrice" value='${minCatalogPrice}'/>
 </c:otherwise>
</c:choose>

<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
<c:choose>
   <c:when test="${!empty finalPrice}">
     <tcmis:emptyIfZero value="${finalPrice}"><fmt:formatNumber maxFractionDigits="4" minFractionDigits="4"><c:out value="${finalPrice}"/></fmt:formatNumber></tcmis:emptyIfZero>
     <c:out value="${status.current.currencyId}"/>
   </c:when>
   <c:otherwise>
   &nbsp;
   </c:otherwise>
</c:choose>
</TD>

<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
<c:set var="storageTemp" value='${status.current.storageTemp}'/>
<c:set var="shelfLife" value='${status.current.shelfLife}'/>

<c:choose>
 <c:when test="${empty storageTemp || storageTemp == ' '}" >
 &nbsp;
 </c:when>
 <c:otherwise>
   <c:choose>
    <c:when test="${shelfLife != 'Indefinite'}" >
       <c:set var="shelfLifeBasis" value='${status.current.shelfLifeBasis}'/>
       <c:if test="${!empty shelfLifeBasis}">
         <c:out value="${status.current.shelfLife}"/> <c:out value="${shelfLifeBasis}"/> @ <c:out value="${storageTemp}"/>
       </c:if>
      <c:if test="${empty shelfLifeBasis}">
         <c:out value="${status.current.shelfLife}"/> @ <c:out value="${storageTemp}"/>
       </c:if>
    </c:when>
    <c:otherwise>
     <c:out value="${status.current.shelfLife}"/> @ <c:out value="${storageTemp}"/>
    </c:otherwise>
   </c:choose>
 </c:otherwise>
</c:choose>
</TD>

<%--
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
<c:choose>
   <c:when test="${!empty status.current.unitOfSaleQuantityPerEach}">
     <tcmis:emptyIfZero value="${status.current.unitOfSaleQuantityPerEach}"><fmt:formatNumber maxFractionDigits="4" minFractionDigits="4"><c:out value="${status.current.unitOfSaleQuantityPerEach}"/></fmt:formatNumber></tcmis:emptyIfZero>
   </c:when>
   <c:otherwise>
   &nbsp;
   </c:otherwise>
</c:choose>
</TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.unitOfSale}"/></TD>
--%>

<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.qtyOfUomPerItem}" escapeXml="false"/></TD>


<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
<c:choose>
 <c:when test="${empty status.current.approvalStatus}" >
  &nbsp;
 </c:when>
 <c:otherwise>
  <c:out value="${status.current.approvalStatus}"/>
 </c:otherwise>
</c:choose>
</TD>

<c:forEach var="prCatalogScreenSearchItemBean" items="${itemCollection}" varStatus="status1">
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

  <%--<TD width="5%" rowspan="<c:out value="${componentSize}"/>"><c:out value="${status1.current.bundle}"/></TD>--%>
  <TD width="5%" rowspan="<c:out value="${componentSize}"/>"><c:out value="${status1.current.itemId}"/>
 <INPUT TYPE="hidden" NAME="grandChildRowsSize<c:out value="${status.index}"/><c:out value="${status1.index}"/>" value="<c:out value="${componentSize}"/>" >
</TD>

  <c:forEach var="prCatalogScreenSearchComponentBean" items="${componentCollection}" varStatus="status2">
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

    <TD width="15%"><c:out value="${status2.current.materialDesc}"/></TD>
    <TD width="5%"><c:out value="${status2.current.packaging}"/></TD>
    <TD width="5%"><c:out value="${status2.current.grade}"/></TD>
    <TD width="10%"><c:out value="${status2.current.mfgDesc}"/></TD>
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
</TABLE>

<c:if test="${empty prCatalogScreenSearchBeanCollection}" >
<c:if test="${prCatalogScreenSearchBeanCollection != null}" >
<TABLE  BORDER="0" CELLSPACING=0 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<tr>
<TD HEIGHT="25" WIDTH="100%" VALIGN="MIDDLE" BGCOLOR="#a2a2a2">
<fmt:message key="main.nodatafound"/>
</TD>
</tr>
</TABLE>
</c:if>
</c:if>
