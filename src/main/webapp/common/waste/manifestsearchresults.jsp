<div class="interface" id="resultsPage">
<div class="backGroundContent">

<c:if test="${manifestSearchCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty manifestSearchCollection}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>
    <bean:size collection="${manifestSearchCollection}" id="resultSize"/>
    <c:forEach var="manifestsearch" items="${manifestSearchCollection}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr align="center">
      <th width="5%"><fmt:message key="label.manifest"/></th>
      <th width="5%"><fmt:message key="label.shipdate"/></th>
      <th width="5%"><fmt:message key="label.shippedto"/></th>
    </tr>
    </c:if>

    <c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>

    <tr align="center" class='<c:out value="${colorClass}"/>' id='rowId<c:out value="${status.index}"/>'>

    <SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
    <!--
       var rowId  =  document.getElementById("rowId<c:out value="${status.index}"/>");
       rowId.attachEvent('onmouseup',function () {
        catchevent('<c:out value="${status.index}"/>');});
    // -->
    </SCRIPT>

      <td width="5%"><c:out value="${manifestsearch.manifestId}"/></td>
      <td width="5%">
         <fmt:formatDate var="fmtShipDate" value="${manifestsearch.actualShipDate}" pattern="MM/dd/yyyy"/>
         <c:out value="${fmtShipDate}"/>
      </td>
      <td width="5%"><c:out value="${manifestsearch.vendorName}"/></td>
    </tr>

   <!-- hidden columns -->
   <input type="hidden" id="colorClass<c:out value="${status.index}"/>" value="<c:out value="${colorClass}"/>" >
   <input type="hidden" id="manifest<c:out value="${status.index}"/>" value="<c:out value="${manifestsearch.manifestId}"/>" >
   <!-- end of hidden columns -->
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty manifestSearchCollection}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>

<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
<input name="action" id="action" type="hidden" value="createPdf">
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<tcmis:saveRequestParameter/>

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->