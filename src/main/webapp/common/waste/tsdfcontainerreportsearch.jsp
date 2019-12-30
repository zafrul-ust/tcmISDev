<!-- Search Option Begins -->
<table id="searchMaskTable" width="1024" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <%-- Insert all the search option within this div --%>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

    <%-- row 1 --%>
    <tr>
      <td width="10%" colspan="4" class="optionTitleBoldCenter"><u><fmt:message key="tsdfcontainerreport.tsdfsearchinformation"/></u></td>
      <td width="10%" colspan="4" class="optionTitleBoldCenter"><u><fmt:message key="tsdfcontainerreport.gensearchinformation"/></u></td>
    </tr>

    <%-- row 2 --%>
    <tr>
      <%-- tsdf --%>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.tsdf"/>:</td>
      <td width="10%">
         <c:set var="selectedTsdf" value='${param.tsdf}'/>
         <select name="tsdf" onchange="tsdfChanged()" class="selectBox" id="tsdf">
           <c:forEach var="wasteTsdfReportDropdownOvBean" items="${wasteTsdfReportDropdownOvBeanCollection}" varStatus="status">
            <c:set var="currentTsdf" value='${status.current.tsdfFacilityId}'/>

            <c:choose>
              <c:when test="${empty selectedTsdf}" >
                <c:set var="selectedTsdf" value='${status.current.tsdfFacilityId}'/>
                <c:set var="toVendorListCollection" value='${status.current.toVendorList}'/>
                <c:set var="wasteGenerationCompanyListCollection" value='${status.current.wasteGenerationCompanyList}'/>
              </c:when>
              <c:when test="${currentTsdf == selectedTsdf}" >
                <c:set var="toVendorListCollection" value='${status.current.toVendorList}'/>
                <c:set var="wasteGenerationCompanyListCollection" value='${status.current.wasteGenerationCompanyList}'/>
              </c:when>
            </c:choose>

            <c:choose>
              <c:when test="${currentTsdf == selectedTsdf}">
                <option value='<c:out value="${status.current.tsdfFacilityId}"/>' selected><c:out value="${status.current.tsdfFacilityName}"/></option>
              </c:when>
              <c:otherwise>
                <option value='<c:out value="${status.current.tsdfFacilityId}"/>'><c:out value="${status.current.tsdfFacilityName}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
         </select>
      </td>
      <%-- blank column --%>
      <td>&nbsp;</td>
      <%-- blank column --%>
      <td>&nbsp;</td>
      <%-- gen company --%>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.company"/>:</td>
      <td width="10%">
         <c:set var="selectedGenCompany" value='${param.genCompany}'/>
         <select name="genCompany" onchange="genCompanyChanged()" class="selectBox" id="genCompany">
           <c:forEach var="wasteGenerationCompanyObjBean" items="${wasteGenerationCompanyListCollection}" varStatus="status">
            <c:set var="currentCompanyId" value='${status.current.companyId}'/>

            <c:choose>
              <c:when test="${empty selectedGenCompany}" >
                <c:set var="selectedGenCompany" value='${status.current.companyId}'/>
                <c:set var="genFacilityListCollection" value='${status.current.genFacilityList}'/>
              </c:when>
              <c:when test="${currentCompanyId == selectedGenCompany}" >
                <c:set var="genFacilityListCollection" value='${status.current.genFacilityList}'/>
              </c:when>
            </c:choose>

            <c:choose>
              <c:when test="${currentCompanyId == selectedGenCompany}">
                <option value='<c:out value="${status.current.companyId}"/>' selected><c:out value="${status.current.companyName}"/></option>
              </c:when>
              <c:otherwise>
                <option value='<c:out value="${status.current.companyId}"/>'><c:out value="${status.current.companyName}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
         </select>
      </td>
      <%-- gen profile --%>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.profile"/>:</td>
      <td width="10%">
         <input class="inputBox" type="text" name="genProfile" id="genProfile" value="<c:out value='${param.genProfile}'/>" size="10">
      </td>
     </tr>

    <%-- row 3 --%>
    <tr>
       <%-- tsdf vendor --%>
       <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.vendor"/>:</td>
       <td width="10%">
          <bean:size collection="${toVendorListCollection}" id="toVendorSize"/>
          <c:set var="selectedTsdfVendor" value='${param.tsdfVendor}'/>
          <select name="tsdfVendor" class="selectBox" id="tsdfVendor">
           <c:if test="${toVendorSize > 1}">
              <option value=""><fmt:message key="label.all"/></option>
           </c:if>
           <c:forEach var="toVendorObjBean" items="${toVendorListCollection}" varStatus="status">
            <c:set var="currentVendorId" value='${status.current.vendorId}'/>

            <c:choose>
              <c:when test="${currentVendorId == selectedTsdfVendor}">
                <option value='<c:out value="${status.current.vendorId}"/>' selected><c:out value="${status.current.vendorName}"/></option>
              </c:when>
              <c:otherwise>
                <option value='<c:out value="${status.current.vendorId}"/>'><c:out value="${status.current.vendorName}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          </select>
       </td>
      <%-- tsdf profile --%>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.profile"/>:</td>
      <td width="10%">
         <input class="inputBox" type="text" name="tsdfProfile" id="tsdfProfile" value="<c:out value='${param.tsdfProfile}'/>" size="10">
      </td>
       <%-- gen facility --%>
       <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
       <td width="10%">
          <bean:size collection="${genFacilityListCollection}" id="genFacilitySize"/>
          <c:set var="selectedGenFacilityId" value='${param.genFacilityId}'/>
          <select name="genFacilityId" onchange="genFacilityChanged()" class="selectBox" id="genFacilityId">
           <c:if test="${genFacilitySize > 1 || genFacilitySize == 0 }">
              <option value=""><fmt:message key="label.all"/></option>
           </c:if>
           <c:forEach var="genFacilityObjBean" items="${genFacilityListCollection}" varStatus="status">
            <c:set var="currentFacilityId" value='${status.current.facilityId}'/>

            <c:if test="${currentFacilityId == selectedGenFacilityId}" >
               <c:set var="wasteLocationCollection" value='${status.current.wasteLocationList}'/>
            </c:if>

            <c:choose>
              <c:when test="${currentFacilityId == selectedGenFacilityId}">
                <option value='<c:out value="${status.current.facilityId}"/>' selected><c:out value="${status.current.facilityName}"/></option>
              </c:when>
              <c:otherwise>
                <option value='<c:out value="${status.current.facilityId}"/>'><c:out value="${status.current.facilityName}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          </select>
       </td>
      <%-- gen container id --%>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.containerid"/>:</td>
      <td width="10%">
         <input class="inputBox" type="text" name="genContainerId" id="genContainerId" value="<c:out value='${param.genContainerId}'/>" size="10">
      </td>
     </tr>

    <%-- row 4 --%>
    <tr>
      <%-- tsdf manifest --%>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.manifest"/>:</td>
      <td width="10%">
         <input class="inputBox" type="text" name="tsdfManifest" id="tsdfManifest" value="<c:out value='${param.tsdfManifest}'/>" size="10">
      </td>
      <%-- tsdf container id --%>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.containerid"/>:</td>
      <td width="10%">
         <input class="inputBox" type="text" name="tsdfContainerId" id="tsdfContainerId" value="<c:out value='${param.tsdfContainerId}'/>" size="10">
      </td>
      <%-- generation point --%>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.generationpoint"/>:</td>
      <td width="10%">
          <bean:size collection="${wasteLocationCollection}" id="generationPointSize"/>
          <c:set var="selectedGenerationPoint" value='${param.generationPoint}'/>
          <select name="generationPoint" class="selectBox" id="generationPoint">
           <c:if test="${generationPointSize > 1 || generationPointSize == 0  }">
              <option value=""><fmt:message key="label.all"/></option>
           </c:if>
           <c:forEach var="wasteLocationObjBean" items="${wasteLocationCollection}" varStatus="status">
            <c:set var="currentGenerationPointId" value='${status.current.refColumn}'/>

            <c:choose>
              <c:when test="${currentGenerationPointId == selectedGenerationPoint}">
                <option value='<c:out value="${status.current.refColumn}"/>' selected><c:out value="${status.current.locationGroupWithLocation}" escapeXml="false"/></option>
              </c:when>
              <c:otherwise>
                <option value='<c:out value="${status.current.refColumn}"/>'><c:out value="${status.current.locationGroupWithLocation}" escapeXml="false"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          </select>
      </td>
      <%-- gen waste tag # --%>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.wastetag"/>:</td>
      <td width="10%">
         <input class="inputBox" type="text" name="wasteTag" id="wasteTag" value="<c:out value='${param.wasteTag}'/>" size="10">
      </td>
    </tr>

    <%-- row 5 --%>
    <tr>
      <%-- tsdf ship date --%>
      <td width="10%" class="optionTitleBoldRight" id="tsdfShipDateLabel"><fmt:message key="label.shipdatebetween"/>:</td>
      <td width="10%" colspan="2">
         <input class="inputBox" type="text" name="tsdfStartShipDate" id="tsdfStartShipDate" value="<c:out value='${param.tsdfStartShipDate}'/>" maxlength="10" size="10">
         <font size="4"><a href="javascript: void(0);" id="linktsdfStartShipDate" onClick="return getTsdfCalendar(document.genericForm.tsdfStartShipDate);">&diams;</a></font>
         <b id="tsdfShipDateAndLabel"><fmt:message key="label.and"/></b>
         <input class="inputBox" type="text" name="tsdfEndShipDate" id="tsdfEndShipDate" value="<c:out value='${param.tsdfEndShipDate}'/>" maxlength="10" size="10">
         <font size="4"><a href="javascript: void(0);" id="linktsdfEndShipDate" onClick="return getTsdfCalendar(document.genericForm.tsdfEndShipDate);">&diams;</a></font>
      </td>
      <%-- blank column --%>
      <td>&nbsp;</td>
      <%-- gen ship date --%>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.shipdatebetween"/>:</td>
      <td width="10%" colspan="3">
         <input class="inputBox" type="text" name="genStartShipDate" id="genStartShipDate" value="<c:out value='${param.genStartShipDate}'/>" maxlength="10" size="10">
         <font size="4"><a href="javascript: void(0);" id="linkgenStartShipDate" onClick="return getCalendar(document.genericForm.genStartShipDate);">&diams;</a></font>
         <b><fmt:message key="label.and"/></b>
         <input class="inputBox" type="text" name="genEndShipDate" id="genEndShipDate" value="<c:out value='${param.genEndShipDate}'/>" maxlength="10" size="10">
         <font size="4"><a href="javascript: void(0);" id="linkgenEndShipDate" onClick="return getCalendar(document.genericForm.genEndShipDate);">&diams;</a></font>
      </td>
    </tr>

    <%-- row 6 --%>
    <tr>
       <%-- not shipped check box --%>
       <td class="optionTitleBoldCenter" colspan="2">
          <input name="notShip" id="notShip" type="checkbox" onclick="notShipClicked()" class="radioBtns" value="notShip'/>"
             <c:if test="${!empty param.notShip}">
                checked
             </c:if>
          > <%-- end on input --%>
          <fmt:message key="label.notwasteship"/>
       </td>
      <%-- blank column --%>
      <td>&nbsp;</td>
      <%-- blank column --%>
      <td>&nbsp;</td>
      <%-- gen manifest --%>
      <td width="10%" class="optionTitleBoldRight" id="genManifestLabel"><fmt:message key="label.manifest"/>:</td>
      <td width="15%" colspan="3">
         <input class="inputBox" type="text" name="genManifest" id="genManifest" value="<c:out value='${param.genManifest}'/>" size="10">
      <%-- </td> --%>
      <%-- no manifest check box --%>
      <%-- <td class="optionTitleBoldLeft" colspan="3"> --%>
          <input name="noManifest" id="noManifest" type="checkbox" onclick="notManifest()" class="radioBtns" value="noManifest'/>"
             <c:if test="${!empty param.noManifest}">
                checked
             </c:if>
          > <%-- end on input --%>
          <b><fmt:message key="label.nomanifest"/></b>
       </td>
    </tr>

     <%-- buttons --%>
     <tr>
     <td class="optionTitleBoldRight">
        <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="button.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
     </td>
     <td class="optionTitleBoldLeft">
       <html:button property="buttonCreateExcel" styleId="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;">
        <fmt:message key="label.createexcelfile"/>
       </html:button>
     </td>
    </tr>
    </table>
   <!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="action" id="action" type="hidden" value="">
 </div>
<!-- Hidden elements end -->

<!-- Error Messages Begins -->
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->
