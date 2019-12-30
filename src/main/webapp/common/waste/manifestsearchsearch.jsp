<!-- Search Option Begins -->
<table id="searchMaskTable" width="400" border="0" cellpadding="0" cellspacing="0">
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
      <td width="35%" class="optionTitleBoldRight"><fmt:message key="label.manifest"/>:</td>
      <td width="65%">
         <!-- search type -->
         <html:select property="manifestSearchType" styleClass="selectBox" styleId="manifestSearchType">
         <html:option value="LIKE" key="label.contain"/>
         <html:option value="STARTS_WITH" key="label.startswith"/>
         <html:option value="ENDS_WITH" key="label.endswith"/>
         <html:option value="=" key="label.is"/>
         </html:select>
         <input class="inputBox" type="text" name="manifest" id="manifest" value="<c:out value='${param.manifest}'/>" >
      </td>
    </tr>

    <%-- row 2 --%>
    <tr>
      <td width="35%" class="optionTitleBoldRight" id="shipDateLabel"><fmt:message key="label.shipdatebetween"/>:</td>
      <td width="65%" colspan="2">
         <input class="inputBox" type="text" name="shippedToStartDate" id="shippedToStartDate" value="<c:out value='${param.shippedToStartDate}'/>" maxlength="10" size="10">
         <font size="4"><a href="javascript: void(0);" id="linkshippedToStartDate" onClick="return getManifestSearchCalendar(document.genericForm.shippedToStartDate);">&diams;</a></font>
         <b id="shipDateAndLabel"><fmt:message key="label.and"/></b>
         <input class="inputBox" type="text" name="shippedToEndDate" id="shippedToEndDate" value="<c:out value='${param.shippedToEndDate}'/>" maxlength="10" size="10">
         <font size="4"><a href="javascript: void(0);" id="linkshippedToEndDate" onClick="return getManifestSearchCalendar(document.genericForm.shippedToEndDate);">&diams;</a></font>
      </td>
    </tr>

    <%-- row 3 --%>
    <tr>
       <td>&nbsp;</td>
       <td class="optionTitleBoldLeft" colspan="2">
          <input name="notShip" id="notShip" type="checkbox" onclick="notShipClicked()" class="radioBtns" value="notShip'/>"
             <c:if test="${!empty param.notShip}">
                checked
             </c:if>
          > <%-- end on input --%>
          <fmt:message key="label.notship"/>
       </td>
    </tr>

    <%-- row 4 --%>
    <tr>
      <td width="35%" class="optionTitleBoldRight"><fmt:message key="label.shippedto"/>:</td>
      <td width="65%">
         <!-- search type -->
         <html:select property="shippedToSearchType" styleClass="selectBox" styleId="shippedToSearchType">
         <html:option value="LIKE" key="label.contain"/>
         <html:option value="STARTS_WITH" key="label.startswith"/>
         <html:option value="ENDS_WITH" key="label.endswith"/>
         <html:option value="=" key="label.is"/>
         </html:select>
         <input class="inputBox" type="text" name="shippedTo" id="shippedTo" value="<c:out value='${param.shippedTo}'/>" >
      </td>
    </tr>
     <%-- buttons --%>
     <tr>
     <td class="optionTitleBoldRight">
        <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="button.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
     </td>
     <td class="optionTitleBoldLeft">
        <input name="cancelButton" type="submit" class="inputBtns" value="<fmt:message key="button.cancel"/>" id="cancelButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return cancel();">
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