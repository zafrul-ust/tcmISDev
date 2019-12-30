<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<!-- Search Frame Begins -->
<div id="${categoryId}Div" style="display:none">
<!-- open contentArea -->
<div class="contentArea">
<!-- Search Option Begins -->
<table id="${categoryId}SearchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
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
						<%-- boxhead Begins --%>
						<div class="boxhead">
							<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
								Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself. --%>
							<fmt:message var="documentLabel" key="label.documents"/>
							<c:if test="${qcComplete eq 'N'}">
								<fmt:message var="documentLabel" key="label.viewuploaddocuments"/>
							</c:if>
							<span id="${categoryId}uploadDocuments"><a href="#" onclick="mfrNotification.openUploadWindow();"><c:out value="${documentLabel}"/></a></span>
						</div>
						<label for="${categoryId}[0].comments" class="optionTitleBoldRight" style="float:left"><fmt:message key="label.comments"/>:</label>&nbsp;
						<textarea id="${categoryId}[0].comments" name="${categoryId}[0].comments" rows="4" class="inputBox" style="width:50%" ${qcComplete eq 'Y'?'readonly':''}><c:out value="${request.mfr.comments}"/></textarea>
						<br/><br/>
						<c:if test="${qcComplete eq 'N'}">
						<c:set var="materialButton" value="${false}"/>
						<c:set var="itemButton" value="${false}"/>
						<c:if test="${category.searchCriteria eq 'material'}"><c:set var="materialButton" value="${true}"/></c:if>
						<c:if test="${category.searchCriteria eq 'item'}"><c:set var="materialButton" value="${true}"/><c:set var="itemButton" value="${true}"/></c:if>
						<input name="${categoryId}[0].mfrLookupButton" id="${categoryId}[0].mfrLookupButton" type="button" value="<fmt:message key="label.manufacturer"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return mfrNotification.lookupManufacturer();"/>
						<input name="${categoryId}[0].mfrLookupSelection" id="${categoryId}[0].mfrLookupSelection" type="hidden" value="${empty request.currentMfr?'':request.currentMfr.mfgId}"/>
						<input name="${categoryId}[0].materialLookupButton" id="${categoryId}[0].materialLookupButton" type="button" value="<fmt:message key="label.material"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return mfrNotification.lookupMaterial();" ${materialButton?'':'style="visibility:hidden"'}/>
						<input name="${categoryId}[0].itemLookupButton" id="${categoryId}[0].itemLookupButton" type="button" value="<fmt:message key="label.item"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return mfrNotification.lookupItem();" ${itemButton?'':'style="visibility:hidden"'}/>
						<input type="hidden" id="${categoryId}[0].materialSearchAvailable" name="${categoryId}[0].materialSearchAvailable" value="${materialButton}" />
						<input type="hidden" id="${categoryId}[0].itemSearchAvailable" name="${categoryId}[0].itemSearchAvailable" value="${itemButton}" />
						<div id="${categoryId}MaterialsToAdd" style="display:none">
							<label for="${categoryId}[0].materialsToAdd"><fmt:message key="label.materialstoadd"/></label>
							<input type="text" id="${categoryId}[0].materialsToAdd" name="${categoryId}[0].materialsToAdd" value="" readonly/>
						</div>
						<div id="${categoryId}ItemsToAdd" style="display:none">
							<label for="${categoryId}[0].itemsToAdd"><fmt:message key="label.itemstoadd"/></label>
							<input type="text" id="${categoryId}[0].itemsToAdd" name="${categoryId}[0].itemsToAdd" value="" readonly/>
						</div>
						</c:if>
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
<!-- Search Option Ends -->
</div>
<!-- close of contentArea -->
</div>
<!-- Search Frame Ends -->
