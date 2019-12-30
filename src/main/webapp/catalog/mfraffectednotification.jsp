<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<c:if test="${empty qcComplete}">
<c:set var="qcComplete" value="N"/>
</c:if>

<!-- Result Frame Begins -->
<div id="${categoryId}MfrResultDiv" style="display:none">
<!-- open contentArea -->
<div class="contentArea">
<table id="${categoryId}MfrTable" width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<div class="roundcont contentContainer">
				<div class="roundright">
					<div class="roundtop">
						<div class="roundtopright">
							<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
						</div>
					</div>
					<div class="roundContent">
					<fmt:formatDate var="year" value="${now}" pattern="yyyy" />
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
					  <tr>
					    <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.manufacturer"/>:</td>
					    <td width="40%">
					    	<span id="${categoryId}[0].mfrIdDisplay" class="optionTitleBoldLeft">${request.currentMfr.mfgId}</span>
					    	<input type="hidden" id="${categoryId}[0].mfgId" name="${categoryId}[0].mfgId" value="${request.currentMfr.mfgId}" snap="snap"/>
					    </td>
					    <td width="50%" colspan="2">&nbsp;</td>
					  </tr>
				      <tr>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.extendeddescription"/>:</td>
				        <c:set var="newMfgDesc" value="${request.mfr.mfgDesc}"/>
				        <c:if test="${categoryId eq mfroutofbusiness}">
				        	<c:set var="oobMsg" value="OUT OF BUSINESS (${year})"/>
				        	<c:set var="newMfgDesc" value="${empty request.mfr.mfgDesc?oobMsg:request.mfr.mfgDesc}"/>
				        </c:if>
				        <td width="40%" class="optionTitleBoldLeft"><input type="text" id="${categoryId}[0].mfgDesc" name="${categoryId}[0].mfgDesc" value="<tcmis:jsReplace value="${newMfgDesc}" processMultiLines="false"/>" snap="snap" size="40" title="<tcmis:jsReplace value="${newMfgDesc}" processMultiLines="false"/>"/></td>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.currentdescription"/>:</td>
				        <td width="40%" class="optionTitleBoldLeft"><input type="text" id="${categoryId}[0].currentMfgDesc" name="${categoryId}[0].currentMfgDesc" disabled="disabled" value="<tcmis:jsReplace value="${request.currentMfr.mfgDesc}" processMultiLines="false"/>" size="40" title="<tcmis:jsReplace value="${request.currentMfr.mfgDesc}" processMultiLines="false"/>"/></td>
				      </tr>
				      <c:if test="${categoryId eq mfracquisition || categoryId eq mfrlocationchange}">
				      <tr>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.manufacturerurl"/>:</td>
				        <td width="40%" class="optionTitleBoldLeft"><input type="text" id="${categoryId}[0].mfgUrl" name="${categoryId}[0].mfgUrl" value="<tcmis:jsReplace value="${request.mfr.mfgUrl}" processMultiLines="false"/>" snap="snap" size="40"/></td>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.currentmanufacturerurl"/>:</td>
				        <td width="40%" class="optionTitleBoldLeft"><input type="text" id="${categoryId}[0].currentMfgUrl" name="${categoryId}[0].currentMfgUrl" disabled="disabled" value="<tcmis:jsReplace value="${request.currentMfr.mfgUrl}" processMultiLines="false"/>" size="40"/></td>
				      </tr>
				      <tr>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.phone"/>:</td>
				        <td width="40%" class="optionTitleBoldLeft"><input type="text" id="${categoryId}[0].phone" name="${categoryId}[0].phone" value="<tcmis:jsReplace value="${request.mfr.phone}" processMultiLines="false"/>" snap="snap" size="40"/></td>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.currentphone"/>:</td>
				        <td width="40%" class="optionTitleBoldLeft"><input type="text" id="${categoryId}[0].currentPhone" name="${categoryId}[0].currentPhone" disabled="disabled" value="<tcmis:jsReplace value="${request.currentMfr.phone}" processMultiLines="false"/>" size="40"/></td>
				      </tr>
				      <tr>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.manufacturercontact"/>:</td>
				        <td width="40%" class="optionTitleBoldLeft"><input type="text" id="${categoryId}[0].contact" name="${categoryId}[0].contact" value="<tcmis:jsReplace value="${request.mfr.contact}" processMultiLines="false"/>" snap="snap" size="40" title="<tcmis:jsReplace value="${request.mfr.contact}" processMultiLines="false"/>"/></td>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.currentmanufacturercontact"/>:</td>
				        <td width="40%" class="optionTitleBoldLeft"><input type="text" id="${categoryId}[0].currentContact" name="${categoryId}[0].currentContact" disabled="disabled" value="<tcmis:jsReplace value="${request.currentMfr.contact}" processMultiLines="false"/>" size="40" title="<tcmis:jsReplace value="${request.mfr.contact}" processMultiLines="false"/>"/></td>
				      </tr>
				      <tr>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.email"/>:</td>
				        <td width="40%" class="optionTitleBoldLeft"><input type="text" id="${categoryId}[0].email" name="${categoryId}[0].email" value="<tcmis:jsReplace value="${request.mfr.email}" processMultiLines="false"/>" snap="snap" size="40"/></td>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.currentemail"/>:</td>
				        <td width="40%" class="optionTitleBoldLeft"><input type="text" id="${categoryId}[0].currentEmail" name="${categoryId}[0].currentEmail" disabled="disabled" value="<tcmis:jsReplace value="${request.currentMfr.email}" processMultiLines="false"/>" size="40"/></td>
				      </tr>
				      <tr>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.cagecode"/>:</td>
				        <td width="40%" class="optionTitleBoldLeft"><input type="text" id="${categoryId}[0].cageCode" name="${categoryId}[0].cageCode" value="<tcmis:jsReplace value="${request.mfr.cageCode}" processMultiLines="false"/>" snap="snap" size="40"/></td>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.currentcagecode"/>:</td>
				        <td width="40%" class="optionTitleBoldLeft"><input type="text" id="${categoryId}[0].currentCageCode" name="${categoryId}[0].currentCageCode" disabled="disabled" value="<tcmis:jsReplace value="${request.currentMfr.cageCode}" processMultiLines="false"/>" size="40"/></td>
				      </tr>
				      </c:if>
				      <c:if test="${categoryId eq mfrlocationchange}">
				      	<td width="10%" class="optionTitleBoldRight" nowrap><label for="${categoryId}[0].equipmentChange"><fmt:message key="label.equipmentchanged"/>?</label></td>
				        <td width="90%" class="optionTitleBoldLeft" colspan="3"><input type="checkbox" id="${categoryId}[0].equipmentChange" name="${categoryId}[0].equipmentChange" value="Y" ${request.mfr.equipmentChange?'checked="checked"':''} snap="snap"/></td>
				      </c:if>
				      <tr>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.extendednotes"/>:</td>
				        <td width="40%" class="optionTitleBoldLeft"><input type="text" id="${categoryId}[0].notes" name="${categoryId}[0].notes" value="<tcmis:jsReplace value="${request.mfr.notes}" processMultiLines="false"/>" snap="snap" size="40" title="<tcmis:jsReplace value="${request.mfr.notes}" processMultiLines="false"/>"/></td>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.currentmanufacturernotes"/>:</td>
				        <td width="40%" class="optionTitleBoldLeft"><input type="text" id="${categoryId}[0].currentNotes" name="${categoryId}[0].currentNotes" disabled="disabled" value="<tcmis:jsReplace value="${request.currentMfr.notes}" processMultiLines="false"/>" size="40"  title="<tcmis:jsReplace value="${request.currentMfr.notes}" processMultiLines="false"/>"/></td>
				      </tr>
				      <c:if test="${categoryId eq mfracquisition}">
				      <tr>
				      	<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.acquisitiontype"/></td> 
				        <td width="90%" colspan="3">
				        	<label for="${categoryId}[0].companyAcquired"><input type="radio" class="radioBtns" id="${categoryId}[0].companyAcquired" name="${categoryId}[0].acquisitionType" value="WHOLE_COMPANY" onclick="mfrNotification.enableMaterialGrab(false);" ${request.mfr.acquisitionType ne 'DIVISION'?'checked="checked"':''} snap="snap"/><fmt:message key="label.wholecompany"/></label>
				        	<label for="${categoryId}[0].divisionAcquired"><input type="radio" class="radioBtns" id="${categoryId}[0].divisionAcquired" name="${categoryId}[0].acquisitionType" value="DIVISION" onclick="mfrNotification.enableMaterialGrab(true);" ${request.mfr.acquisitionType eq 'DIVISION'?'checked="checked"':''} snap="snap"/><fmt:message key="label.division"/></label>
				        </td>
				      </tr>
				      <tr>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.acquiredmanufacturer"/>:</td>
				        <td width="40%" class="optionTitleBoldLeft">
				        	<input type="text" id="${categoryId}[0].acquiredMfrId" name="${categoryId}[0].acquiredMfrId" readonly="readonly" value="${request.mfr.acquiredMfrId}" snap="snap"/>
				        	<c:if test="${qcComplete ne 'Y'}">
				        	<input type="button" id="${categoryId}[0].acquiredMfrLookup" name="${categoryId}[0].acquiredMfrLookup" value="..." class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" onclick="mfrNotification.lookupManufacturer(true);"/>
				        	</c:if>
				        </td>
				        <td width="50%" colspan="2">&nbsp;</td>
				      </tr>
				      <tr>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.description"/>:</td>
				        <td width="40%" class="optionTitleBoldLeft"><input type="text" id="${categoryId}[0].acquiredMfrDesc" name="${categoryId}[0].acquiredMfrDesc" disabled="disabled" value="<tcmis:jsReplace value="${request.mfr.acquiredMfrDesc}" processMultiLines="false"/>" snap="snap" size="40"/></td>
				        <td width="50%" colspan="2">&nbsp;</td>
				      </tr>
				      </c:if>
				    </table>
					</div>
					<div class="roundbottom">
						<div class="roundbottomright">
							<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
						</div>
					</div>
				</div>
			</div>
		</td>
	</tr>
</table>
</div>
<!-- close of contentArea -->
<c:if test="${category.materialData}">
<%@ include file="/catalog/materialnotificationtable.jsp" %>
</c:if>
</div>
<!-- Result Frame Ends -->
