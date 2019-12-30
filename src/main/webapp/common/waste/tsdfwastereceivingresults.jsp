<div class="interface" id="resultsPage">
<div class="backGroundContent">

<c:if test="${tsdfWasteReceivingCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty tsdfWasteReceivingCollection}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>
    <bean:size collection="${tsdfWasteReceivingCollection}" id="resultSize"/>
    <c:forEach var="tsdfWasteReceiving" items="${tsdfWasteReceivingCollection}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr align="center">
      <th width="2%"><fmt:message key="label.ok"/><br><input name="allCheck" id="allCheck" type="checkbox" class="" value="all" onclick="checkAll(<c:out value="${resultSize}"/>);"></th>
      <th width="5%"><fmt:message key="label.shipmentid"/></th>
      <th width="5%"><fmt:message key="label.dateshipped"/></th>
      <th width="5%"><fmt:message key="label.description"/></th>
      <th width="5%"><fmt:message key="label.packaging"/></th>
      <th width="5%"><fmt:message key="label.manifest"/></th>
      <th width="5%"><fmt:message key="label.statewastecode"/></th>
      <th width="5%"><fmt:message key="label.epawastecode"/></th>
      <th width="5%"><fmt:message key="label.generatorprofile"/></th>
      <th width="5%"><fmt:message key="label.tsdfprofile"/></th>
      <th width="5%"><fmt:message key="label.generatorcompany"/></th>
      <th width="5%"><fmt:message key="label.facility"/></th>
      <th width="5%"><fmt:message key="label.generatorlocation"/></th>
      <th width="5%"><fmt:message key="label.dor"/></th>
      <th width="5%"><fmt:message key="label.tsdflocation"/></th>
      <th width="5%"><fmt:message key="label.containerid"/></th>
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
      <td width="2%">
         <input type="checkbox" class="radioBtns" name="tsdfWasteReceivingInputBean[<c:out value="${status.index}"/>].ok" id="ok<c:out value="${status.index}"/>" value="<c:out value="${status.index}"/>">
      </td>
      <td width="5%"><c:out value="${tsdfWasteReceiving.shipmentId}"/></td>
      <td width="5%">
         <fmt:formatDate var="fmtShipDate" value="${tsdfWasteReceiving.actualShipDate}" pattern="MM/dd/yyyy"/>
         <c:out value="${fmtShipDate}"/>
      </td>
      <td width="5%"><c:out value="${tsdfWasteReceiving.profileDescription}"/></td>
      <td width="5%"><c:out value="${tsdfWasteReceiving.packaging}"/></td>
      <td width="5%"><c:out value="${tsdfWasteReceiving.manifestId}"/></td>
      <td width="5%"><c:out value="${tsdfWasteReceiving.stateWasteCodes}"/></td>
      <td width="5%"><c:out value="${tsdfWasteReceiving.epaWasteCodes}"/></td>
      <td width="5%"><c:out value="${tsdfWasteReceiving.wasteItemId}"/></td>
      <td>
         <select name="tsdfWasteReceivingInputBean[<c:out value="${status.index}"/>].tsdfWasteItemId" id="tsdfProfile<c:out value="${status.index}"/>" class="selectBox">
            <c:forEach var="tsdfWasteItemId" items="${tsdfWasteReceiving.wasteItemConvertViewBean}" varStatus="tsdfprofilestatus">
               <option value="<c:out value="${tsdfprofilestatus.current.toWasteItemId}"/>" selected><c:out value="${tsdfprofilestatus.current.toVendorProfileId}"/></option>
            </c:forEach>
         </select>
      </td>
      <td width="5%"><c:out value="${tsdfWasteReceiving.generatorCompanyId}"/></td>
      <td width="5%"><c:out value="${tsdfWasteReceiving.generatorFacilityId}"/></td>
      <td width="5%"><c:out value="${tsdfWasteReceiving.generatorWasteLocationId}"/></td>
      <td width="5%"><input type="text" name="tsdfWasteReceivingInputBean[<c:out value="${status.index}"/>].dateOfReceipt" id="dateOfReceipt<c:out value="${status.index}"/>" value="<tcmis:getDateTag numberOfDaysFromToday="0"/>" size="8" maxlength="10" class="inputBox" readonly><a href="javascript: void(0);" class="optionTitleBold" id="linkdateOfReceipt<c:out value="${status.index}"/>" onclick="return getCalendar(document.genericForm.dateOfReceipt<c:out value="${status.index}"/>);">&diams;</a></td>
      <td>
         <select name="tsdfWasteReceivingInputBean[<c:out value="${status.index}"/>].tsdfLocation" id="tsdfLocation<c:out value="${status.index}"/>" class="selectBox">
            <c:forEach var="wasteLocation" items="${tsdfWasteReceiving.wasteLocationBean}" varStatus="wastelocationstatus">
               <option value="<c:out value="${wastelocationstatus.current.wasteLocationId}"/>" selected><c:out value="${wastelocationstatus.current.wasteLocationId}"/></option>
            </c:forEach>
         </select>
      </td>
      <td width="5%"><c:out value="${tsdfWasteReceiving.containerId}"/></td>
    </tr>

   <!-- hidden columns -->
   <input name="tsdfWasteReceivingInputBean[<c:out value="${status.index}"/>].tsdfFacilityId" id="tsdfWasteReceivingInputBean[<c:out value="${status.index}"/>].tsdfFacilityId" type="hidden" value="<c:out value="${status.current.tsdfFacilityId}"/>">
   <input name="tsdfWasteReceivingInputBean[<c:out value="${status.index}"/>].containerId" id="tsdfWasteReceivingInputBean[<c:out value="${status.index}"/>].containerId" type="hidden" value="<c:out value="${status.current.containerId}"/>">
   <!-- end of hidden columns -->
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty tsdfWasteReceivingCollection}" >

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
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<tcmis:saveRequestParameter/>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->