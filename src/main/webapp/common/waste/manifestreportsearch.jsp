<!-- Search Option Begins -->
<table id="searchMaskTable" width="300" border="0" cellpadding="0" cellspacing="0">
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
       <td width="20%" class="optionTitleBoldRight">
          <fmt:message key="label.tsdf"/>:&nbsp;
       </td>
       <c:set var="selectedTsdf" value='${param.tsdf}'/>
       <td width="80%" class="optionTitleBoldLeft"><select name="tsdf" id="tsdf" class="selectBox">
          <c:forEach var="wasteLocationBean" items="${manifestReportDropdownCollection}" varStatus="status">
            <c:set var="currentTsdf" value='${status.current.wasteLocationId}'/>
            <c:choose>
              <c:when test="${empty selectedTsdf}" >
                <c:set var="selectedTsdf" value='${status.current.wasteLocationId}'/>
              </c:when>
            </c:choose>
            <c:choose>
              <c:when test="${currentTsdf == selectedTsdf}">
                <option value="<c:out value="${currentTsdf}"/>" selected><c:out value="${status.current.wasteLocationId}"/></option>
              </c:when>
              <c:otherwise>
                <option value="<c:out value="${currentTsdf}"/>"><c:out value="${status.current.wasteLocationId}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          </select></td>
    </tr>
    <%-- row 2 --%>
    <tr>
      <td width="20%" class="optionTitleBoldRight"><fmt:message key="label.manifest"/>:</td>
      <td width="80%">
         <input class="inputBox" type="text" name="manifest" id="manifest" value="<c:out value='${param.manifest}'/>" >
         <input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="lookupButton" value="" onclick="return searchManifest();">
      </td>
    </tr>
     <%-- buttons --%>
     <tr>
     <td class="optionTitleBoldRight">
        <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="button.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
     </td>
     <td class="optionTitleBoldLeft">
       <html:button property="buttonCreateExcel" styleId="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generatePdf(); return false;">
        <fmt:message key="label.createpdffile"/>
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

<!-- Error Messages Begins -->
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->