<div class="interface" id="resultsPage">
<div class="backGroundContent">

<c:if test="${tsdfReportCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty tsdfReportCollection}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>
    <bean:size collection="${tsdfReportCollection}" id="resultSize"/>
    <c:forEach var="tsdfreport" items="${tsdfReportCollection}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr align="center">
      <th width="5%" colspan="6"><fmt:message key="tsdfcontainerreport.tsdfinformation"/></th>
      <th width="5%" colspan="11"><fmt:message key="tsdfcontainerreport.geninformation"/></th>
    </tr>
    <tr align="center">
      <th width="5%"><fmt:message key="label.vendor"/></th>
      <th width="5%"><fmt:message key="label.manifest"/></th>
      <th width="5%"><fmt:message key="label.shipdate"/></th>
      <th width="5%"><fmt:message key="label.profile"/></th>
      <th width="5%"><fmt:message key="label.packaging"/></th>
      <th width="5%"><fmt:message key="label.currentcontainerid"/></th>
      <th width="5%"><fmt:message key="label.manifest"/></th>
      <th width="5%"><fmt:message key="label.shipdate"/></th>
      <th width="5%"><fmt:message key="label.profile"/></th>
      <th width="5%"><fmt:message key="label.packaging"/></th>
      <th width="5%"><fmt:message key="label.company"/></th>
      <th width="5%"><fmt:message key="label.facility"/></th>
      <th width="5%"><fmt:message key="label.generationpoint"/></th>
      <th width="5%"><fmt:message key="label.wasterequestline"/></th>
      <th width="5%"><fmt:message key="label.wastetag"/></th>
      <th width="5%"><fmt:message key="label.requestor"/></th>
      <th width="5%"><fmt:message key="label.originalcontainerid"/></th>
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
      <td width="5%"><c:out value="${tsdfreport.tsdfVendorDesc}"/></td>
      <td width="5%"><c:out value="${tsdfreport.tsdfManifest}"/></td>
      <td width="5%">
         <fmt:formatDate var="fmtShipDate" value="${tsdfreport.tsdfShipDate}" pattern="MM/dd/yyyy"/>
         <c:out value="${fmtShipDate}"/>
      </td>
      <c:set var="tsdfProfileUrl" value="${status.current.tsdfProfileUrl}"/>
      <c:choose>
         <c:when test="${empty tsdfProfileUrl}" >
            <td width="5%"><c:out value="${status.current.tsdfVendorProfileId}"/></td>
         </c:when>
         <c:otherwise>
            <td width="5%"><a href="javascript:openWinGeneric('<c:out value="${status.current.tsdfProfileUrl}"/>','<c:out value="${status.current.tsdfVendorProfileId}"/>','800','600','yes')"><c:out value="${status.current.tsdfVendorProfileId}"/></a>
            </td>
         </c:otherwise>
      </c:choose>
      <td width="5%"><c:out value="${tsdfreport.tsdfPackaging}"/></td>
      <td width="5%"><c:out value="${tsdfreport.tsdfContainerId}"/></td>
      <td width="5%"><c:out value="${tsdfreport.genManifest}"/></td>
      <td width="5%">
         <fmt:formatDate var="fmtShipDate" value="${tsdfreport.genShipDate}" pattern="MM/dd/yyyy"/>
         <c:out value="${fmtShipDate}"/>
      </td>
      <c:set var="genProfileUrl" value="${status.current.genProfileUrl}"/>
      <c:choose>
         <c:when test="${empty genProfileUrl}" >
            <td width="5%"><c:out value="${status.current.genVendorProfileId}"/></td>
         </c:when>
         <c:otherwise>
            <td width="5%"><a href="javascript:openWinGeneric('<c:out value="${status.current.genProfileUrl}"/>','<c:out value="${status.current.genVendorProfileId}"/>','800','600','yes')"><c:out value="${status.current.genVendorProfileId}"/></a>
            </td>
         </c:otherwise>
      </c:choose>
      <td width="5%"><c:out value="${tsdfreport.genPackaging}"/></td>
      <td width="5%"><c:out value="${tsdfreport.genCompanyId}"/></td>
      <td width="5%"><c:out value="${tsdfreport.genFacilityId}"/></td>
      <td width="5%"><c:out value="${tsdfreport.generationPoint}"/></td>
      <td width="5%"><a href="javascript:updateWasteTagNumber('<c:out value="${tsdfreport.genWrLineItem}"/>')" style=\"color:#0000ff\"><c:out value="${tsdfreport.genWrLineItem}"/></td></a>
      <td width="5%"><c:out value="${tsdfreport.tagNumber}"/></td>
      <td width="5%"><c:out value="${tsdfreport.requestor}"/></td>
      <td width="5%"><c:out value="${tsdfreport.genContainerId}"/></td>
    </tr>

   <!-- hidden columns -->
   <!-- end of hidden columns -->
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty tsdfReportCollection}" >

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
<input name="newWasteTagNumber" id="newWasteTagNumber" type="hidden" value="">
<input name="wasteRequestIdLine" id="wasteRequestIdLine" type="hidden" value="">
<input name="action" id="action" type="hidden" value="">
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<tcmis:saveRequestParameter/>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->