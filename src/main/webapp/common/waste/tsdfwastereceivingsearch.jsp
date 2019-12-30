<!-- Search Option Begins -->
<table id="searchMaskTable" width="450" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <%-- Insert all the search option within this div --%>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
  <%-- tsdf --%>
    <tr>
      <td width="40%" class="optionTitleBoldRight"><fmt:message key="label.tsdf"/>:</td>
      <td width="60%">
         <c:set var="selectedTsdf" value='${param.tsdf}'/>
         <select name="tsdf" onchange="tsdfChanged()" class="selectBox" id="tsdf">
           <c:forEach var="wasteTsdfSourceViewBean" items="${wasteTsdfSourceViewBeanCollection}" varStatus="status">
            <c:set var="currentTsdf" value='${status.current.tsdfEpaId}'/>
            <c:if test="${selectedTsdf == null}">
                <c:set var="selectedTsdf" value=""/>
            </c:if>
            <c:if test="${currentTsdf == selectedTsdf}" >
              <c:set var="generatorCompanyCollection" value='${status.current.generatorCompanyIdBeanCollection}'/>
            </c:if>
            <c:choose>
              <c:when test="${currentTsdf == selectedTsdf}">
                <option value='<c:out value="${status.current.tsdfEpaId}"/>' selected><c:out value="${status.current.tsdfName}"/></option>
              </c:when>
              <c:otherwise>
                <option value='<c:out value="${status.current.tsdfEpaId}"/>'><c:out value="${status.current.tsdfName}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
         </select>
      </td>
     </tr>
    <%-- generator company --%>
    <tr>
      <td width="40%" class="optionTitleBoldRight"><fmt:message key="label.generatorcompany"/>:</td>
      <td width="60%">
         <bean:size collection="${generatorCompanyCollection}" id="generatorCompanySize"/>
         <c:set var="selectedGeneratorCompany" value='${param.generatorCompany}'/>
         <select name="generatorCompany" onchange="generatorCompanyChanged()" class="selectBox" id="generatorCompany">
           <c:if test="${generatorCompanySize > 1}">
              <option value=""><fmt:message key="label.all"/></option>
           </c:if>
           <c:forEach var="generatorCompany" items="${generatorCompanyCollection}" varStatus="status">
            <c:set var="currentGeneratorCompany" value='${status.current.generatorCompanyId}'/>
            <c:if test="${selectedGeneratorCompany == null}">
                <c:set var="selectedGeneratorCompany" value=""/>
                <c:if test="${generatorCompanySize == 1}">
                   <c:set var="tsdfFacilityIdForGeneratorCollection" value='${status.current.tsdfFacilityIdForGeneratorBeanCollection}'/>
                </c:if>
            </c:if>
            <c:if test="${currentGeneratorCompany == selectedGeneratorCompany}" >
              <c:set var="tsdfFacilityIdForGeneratorCollection" value='${status.current.tsdfFacilityIdForGeneratorBeanCollection}'/>
            </c:if>
            <c:choose>
              <c:when test="${currentGeneratorCompany == selectedGeneratorCompany}">
                <option value='<c:out value="${currentGeneratorCompany}"/>' selected><c:out value="${status.current.generatorCompanyId}"/></option>
              </c:when>
              <c:otherwise>
                <option value='<c:out value="${status.current.generatorCompanyId}"/>'><c:out value="${status.current.generatorCompanyId}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
         </select>
      </td>
     </tr>
    <%-- tsdf facility for generator --%>
    <tr>
      <td width="40%" class="optionTitleBoldRight"><fmt:message key="label.generatorlocation"/>:</td>
      <td width="60%">
         <bean:size collection="${tsdfFacilityIdForGeneratorCollection}" id="locationSize"/>
         <c:set var="selectedTsdfFacilityIdForGenerator" value='${param.tsdfFacilityIdForGenerator}'/>
         <select name="tsdfFacilityIdForGenerator" class="selectBox" id="tsdfFacilityIdForGenerator">
           <c:if test="${locationSize > 1}">
              <option value=""><fmt:message key="label.all"/></option>
           </c:if>
           <c:if test="${locationSize == 0}">
              <option value=""><fmt:message key="label.all"/></option>
           </c:if>
           <c:forEach var="tsdfFacilityIdForGenerator" items="${tsdfFacilityIdForGeneratorCollection}" varStatus="status">
            <c:set var="currentTsdfFacilityIdForGenerator" value='${status.current.generatorFacilityStorageLocationId}'/>
            <c:if test="${selectedTsdfFacilityIdForGenerator == null}">
                <c:set var="selectedTsdfFacilityIdForGenerator" value=""/>
            </c:if>
            <c:choose>
              <c:when test="${currentTsdfFacilityIdForGenerator == selectedTsdfFacilityIdForGenerator}">
                <option value='<c:out value="${status.current.generatorFacilityStorageLocationId}"/>' selected><c:out value="${status.current.generatorFacilityStorageLocation}"/></option>
              </c:when>
              <c:otherwise>
                <option value='<c:out value="${status.current.generatorFacilityStorageLocationId}"/>'><c:out value="${status.current.generatorFacilityStorageLocation}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
         </select>
      </td>
     </tr>

    <tr>
     <td class="optionTitleBoldRight">
        <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="button.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
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

<!-- Error Messages Begins -->
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->