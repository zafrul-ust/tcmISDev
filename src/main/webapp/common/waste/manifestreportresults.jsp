<div class="interface" id="resultsPage">
<div class="backGroundContent">

<c:if test="${manifestReportCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="rowCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty manifestReportCollection}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:set var="colorClass" value=''/>
    <c:set var="rowCount" value='${0}'/>
    <c:set var="columnCount" value='${0}'/>
    <c:set var="dataColumnCount" value='${0}'/>
    <bean:size collection="${manifestReportCollection}" id="resultSize"/>
    <c:forEach var="manifestreport" items="${manifestReportCollection}" varStatus="status">

    <c:if test="${status.index % 50 == 0}">
       <!-- Need to print the header every 10 rows-->
       <tr align="center">
          <c:choose>
             <c:when test="${resultSize >= 5}">
                <th width="5%"><fmt:message key="label.manifest"/></th>
                <th width="5%"><fmt:message key="label.manifest"/></th>
                <th width="5%"><fmt:message key="label.manifest"/></th>
                <th width="5%"><fmt:message key="label.manifest"/></th>
                <th width="5%"><fmt:message key="label.manifest"/></th>
                <c:set var="columnCount" value='${5}'/>
             </c:when>
          <c:otherwise>
             <c:if test="${resultSize == 4}">
                <th width="5%"><fmt:message key="label.manifest"/></th>
                <th width="5%"><fmt:message key="label.manifest"/></th>
                <th width="5%"><fmt:message key="label.manifest"/></th>
                <th width="5%"><fmt:message key="label.manifest"/></th>
                <c:set var="columnCount" value='${4}'/>
             </c:if>
             <c:if test="${resultSize == 3}">
                <th width="5%"><fmt:message key="label.manifest"/></th>
                <th width="5%"><fmt:message key="label.manifest"/></th>
                <th width="5%"><fmt:message key="label.manifest"/></th>
                <c:set var="columnCount" value='${3}'/>
             </c:if>
             <c:if test="${resultSize == 2}">
                <th width="5%"><fmt:message key="label.manifest"/></th>
                <th width="5%"><fmt:message key="label.manifest"/></th>
                <c:set var="columnCount" value='${2}'/>
             </c:if>
             <c:if test="${resultSize == 1}">
                <th width="5%"><fmt:message key="label.manifest"/></th>
                <c:set var="columnCount" value='${1}'/>
             </c:if>
           </c:otherwise>
         </c:choose>
       </tr>
    </c:if>

    <c:choose>
     <c:when test="${rowCount % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>

    <c:if test="${dataColumnCount == 0}">
       <tr align="center" class='<c:out value="${colorClass}"/>' id='rowId<c:out value="${rowCount}"/>'>
    </c:if>

    <td width="5%"><c:out value="${manifestreport.manifestId}"/></td>

    <c:choose>
     <c:when test="${dataColumnCount == 4}" >
       </tr>
       <c:set var="dataColumnCount" value='${0}'/>
       <c:set var="rowCount" value='${rowCount+1}'/>
     </c:when>
     <c:otherwise>
       <c:set var="dataColumnCount" value='${dataColumnCount+1}'/>
     </c:otherwise>
    </c:choose>

   <!-- hidden columns -->
   <!-- end of hidden columns -->
   </c:forEach>
   </table>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty manifestReportCollection}" >

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
<input name="totalLines" id="totalLines" value="<c:out value="${rowCount}"/>" type="hidden">
<input name="action" id="action" type="hidden" value="createPdf">
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<tcmis:saveRequestParameter/>

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->