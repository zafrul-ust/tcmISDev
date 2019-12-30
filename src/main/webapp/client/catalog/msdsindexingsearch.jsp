<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<c:if test="${empty index}">
<%@ include file="/common/locale.jsp" %>
</c:if>
<!-- Variables can't be shared, so need to duplicate here -->
<c:set var="module"><tcmis:module/></c:set>
<c:set var="isWescoModule" value="${module eq 'haas' or module eq 'catalog'}"/>
<c:set var="masterData" value="${not empty chemRequest}"/>
<c:set var="vendorTask" value="${not empty catalogQueueRow}"/>
<c:set var="maintenance" value="${not vendorTask && not masterData}"/>
<c:if test="${vendorTask}">
	<c:set var="vendorAssigned" value="${catalogQueueRow.status eq 'Assigned'}" />
	<c:set var="vendorQc" value="${catalogQueueRow.status eq 'Pending QC'}" />
	<c:set var="masterDataReview" value="${catalogQueueRow.status eq 'Pending Approval'}" />
	<c:set var="formerlyVendorTask" value="${catalogQueueRow.status eq 'Closed'}"/>
	<c:set var="sourcing" value="${catalogQueueRow.task eq 'SDS Sourcing'}" />
	<c:set var="authoring" value="${catalogQueueRow.task eq 'SDS Authoring'}" />
	<c:set var="indexing" value="${catalogQueueRow.task eq 'SDS Indexing'}" />
	<c:set var="userIsAssignee" value="${catalogQueueRow.assignedTo eq personnelBean.personnelId}"/>
	<c:set var="vendorCanSubmit" value="${vendorAssigned && userIsAssignee}"/>
	<c:set var="vendorCanApprove" value="${vendorQc && not userIsAssignee}"/>
	<c:set var="supplierIsWesco" value="${catalogQueueRow.supplierWesco}"/>
</c:if>
<c:set var="readwriteRequest" value="${(vendorTask && (sourcing || authoring) && (vendorCanSubmit || vendorCanApprove)) || maintenance || masterData}"/>
<c:set var="index" value="${empty param.component?0:param.component}" />
		<div class="contentArea">
		<!-- Search Frame Begins -->
				<!-- Search Option Begins -->
				<div class="roundcont contentContainer">
					<div class="roundright">
						<div class="roundtop">
							<div class="msdsroundtopright">
								<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
							</div>
						</div>
						<div class="roundContent">
							<div class="boxhead"><%-- boxhead Begins --%>
								<c:if test="${maintenance || (masterData && not vendorTask && not formerlyVendorTask)}">
								<div id="customeroverriderule" style="width:100%;text-align:center;display:inline-block;color:red;visibility:${empty customerCompanies?'hidden':'visible'};"><fmt:message key="label.customeroverriderule"/></div>
								</c:if>
								<div id="gridUpdateLinks${index}" style="display: none;">
									<span id="msds[${index}].saveButton">
										<a href="#" onclick="msdsIndex.submitSave();"><fmt:message key="label.save"/></a> |
									</span>
									<span id="msds[${index}].submitQcButton" style="display:none;">
										<c:choose>
											<c:when test="${isWescoModule && supplierIsWesco}">
												<a href="#" onclick="msdsIndex.submitApprove('Y');"><fmt:message key="label.submit"/></a> |
											</c:when>
											<c:otherwise>
												<a href="#" onclick="msdsIndex.submitForQC();"><fmt:message key="label.submit"/></a> |
											</c:otherwise>
										</c:choose>
									</span>
									<span id="msds[${index}].approveQcButton" style="display:none;">
										<a href="#" onclick="msdsIndex.submitApprove();"><fmt:message key="label.approve"/></a> |
									</span>
									<div id="msds[${index}].rejectButton" style="position:relative;display:none;z-index:1">
										<a id="rejectCommentBtn" href="#" onclick="msdsIndex.toggleRejectionPopup();"><fmt:message key="label.reject"/></a> |
										<div id="rejectCommentPopup" style="position:absolute;width:400px;z-index:1;display:none;background-color:#e5e5e5;padding:5px;border:1px solid gray;">
											<label for="rejectionComments"><fmt:message key="label.comments"/>:</label>
											<span style="float:right;cursor:pointer" onclick="msdsIndex.toggleRejectionPopup();">x</span>
											<textarea id="rejectionComments" name="rejectionComments" rows="10" class="inputBox" style="width:95%;position:relative"></textarea>
											<br/>
											<input type="button" id="rejectConfirmButton" name="rejectConfirmButton" onclick="msdsIndex.submitRejectCannotFulfill();" value="<fmt:message key="label.reject"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
										</div>
									</div>
									<span id="msds[${index}].rejectOutOfScopeButton" style="display:none;">
										<a href="#" onclick="msdsIndex.submitRejectOutOfScope();">Reject: Out of Scope</a> |
									</span>
									<span id="msds[${index}].submitCustomerQcButton" style="display:none;">
										<a href="#" onclick="msdsIndex.submitForQC();"><fmt:message key="label.submit"/> <fmt:message key="label.customer"/></a> |
									</span>
									<span id="msds[${index}].approveGlobalQcButton" style="display:none;">
										<a href="#" onclick="msdsIndex.submitApprove();"><fmt:message key="label.approve"/> <fmt:message key="label.global"/></a> |
									</span>
									<span id="msds[${index}].approveCustomerQcButton" style="display:none;">
										<a href="#" onclick="msdsIndex.submitApprove();"><fmt:message key="label.approve"/> <fmt:message key="label.customer"/></a> |
									</span>
									<span id="msds[${index}].reportProblemButton" style="display:none;">
										<a href="#" onclick="msdsIndex.reportProblem();"><fmt:message key="label.reportasproblem"/></a> |
									</span>
									<span id="msds[${index}].reAssignToVendorButton" style="display:none;">
										<a href="#" onclick="msdsIndex.reportProblem('vendor');"><fmt:message key="label.sendbacktovendor"/></a> |
									</span>
									<span id="msds[${index}].authorSdsButton" style="display:none">
										<a href="#" onclick="msdsIndex.authorSds();"><fmt:message key="label.authorinnewlanguage"/></a> |
									</span>
									<c:if test="${vendorTask && supplierIsWesco && userIsAssignee}">
										<div style="position:relative;display:inline;z-index:1">
											<a id="msds[${index}].changeLocaleBtn" href="#" onclick="msdsIndex.toggleChangeLocale();"><fmt:message key="label.changelocale"/></a> |
											<div id="msds[${index}].changeLocalePopup" style="top:20px;left:0%;position:absolute;width:200px;z-index:1;display:none;background-color:#e5e5e5;padding:5px;border:1px solid gray;">
												<label><fmt:message key="label.changelocale"/></label>
												<ul>
												<c:forEach var="facilityLocale" items="${facilityLocales}" varStatus="locStatus">
												<li style="list-style-type: none;">
												<label for="${facilityLocale.localeCode}">
												<input type="radio" id="${facilityLocale.localeCode}" name="localeOverride" value="${facilityLocale.localeCode}"/>
												<c:out value="${facilityLocale.localeDisplay}"/>
												</label>
												</li>
												</c:forEach>
												</ul>
												<input type="button" onclick="msdsIndex.submitChangeLocale()" value="OK" class="smallBtns"/>
												&nbsp;&nbsp;
												<input type="button" onclick="msdsIndex.toggleChangeLocale()" value="Cancel" class="smallBtns"/>
											</div>
										</div>
									</c:if>
									<c:if test="${not maintenance && (not masterData || vendorTask || formerlyVendorTask)}">
										<span id="msds[${index}].viewRequest">
											<a href="#" onclick="msdsIndex.viewRequest();">View Request</a> |
										</span>
									</c:if>
									<span id="msds[${index}].contactLogButton">
										<c:choose>
											<c:when test="${maintenance}">
											<a href="#" onclick="msdsIndex.createViewContactLog();"><fmt:message key="label.createviewcontactlog"/></a>
											</c:when>
											<c:otherwise>
											<a href="#" onclick="msdsIndex.createViewWorkQueueContactLog();"><fmt:message key="label.createviewcontactlog"/></a>
											</c:otherwise>
										</c:choose>
									</span>
									<c:if test="${not maintenance && (not masterData || vendorTask || formerlyVendorTask)}">
										| <a href="#" onclick="msdsIndex.viewProblemHistory();"><fmt:message key="label.viewproblemhistory"/></a>
									</c:if>
									<c:if test="${vendorTask && sourcing}">
										| <a href="#" onclick="msdsIndex.viewUploadedSDS();"><fmt:message key="label.viewuploadedsds"/></a>
									</c:if>
								</div>
							</div>
							<div id="msdsContent${index}" class="dataContent">
							<c:if test="${(vendorTask || formerlyVendorTask) && not masterData}">
							<div id="msds[${index}].qInfo" class="fullWidth column">
								<div class="msdsIndexedField">
									<label for="msds[${index}].qId" class="optionTitleBoldRight" style="width:20%;white-space:nowrap"><fmt:message key="label.queueid"/>:&nbsp;</label>
										<input class="msdsReadOnlyField" style="width:70%" type="text" name="msds[${index}].qId" id="msds[${index}].qId" value="${catalogQueueRow.qId}" readonly/>
								</div>
								<div class="msdsIndexedField">
									<label for="msds[${index}].task" class="optionTitleBoldRight" style="width:20%;white-space:nowrap"><fmt:message key="label.task"/>:&nbsp;</label>
										<input class="msdsReadOnlyField" style="width:70%" type="text" name="msds[${index}].task" id="msds[${index}].task" value="${catalogQueueRow.task}" readonly/>
								</div>
								<div class="msdsIndexedField">
									<label class="optionTitleBoldRight" style="width:20%;white-space:nowrap"><fmt:message key="label.date.created"/>:&nbsp;</label>
										<fmt:formatDate type="date" value="${catalogQueueRow.insertDate}" pattern="${dateTimeFormatPattern}"/>
								</div>
							</div>
							</c:if>
							
							<c:set var="showCustom" value="${not empty customerCompanies && (maintenance || masterData)}"/>
							<c:set var="columnWidth" value="${showCustom || sourcing || authoring?'thirdWidth':'halfWidth'}"/>
							<c:set var="preSourced" value="${catalogQueueRow.materialIdSourced eq 'X' || catalogQueueRow.materialIdSourced eq 'Y'}"/>
							<c:choose>
							<c:when test="${maintenance || sourcing || (masterData && not vendorTask && not formerlyVendorTask)}">
								<div class="${columnWidth} column">
								<div class="msdsIndexedField">
								<label for="msds[${index}].mfrSearch" class="optionTitleBold" style="width:20%"><fmt:message key="label.manufacturer"/>:</label>
									<input type="text" class="msdsReadOnlyField" style="width:70%;font-weight:normal" readonly name="msds[${index}].mfrSearch" id="msds[${index}].mfrSearch" value="${msds.mfg.mfgDesc}"/>
									<input type="button" class="lookupBtn" id="msds[${index}].mfrLookupButton" name="msds[${index}].mfrLookupButton" ${preSourced?'disabled':''} onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" onclick="msdsIndex.lookupManufacturer(false);" />
									<input type="hidden" name="msds[${index}].mfgIdSearch" id="msds[${index}].mfgIdSearch" value="${msds.mfg.mfgId}"/>
								</div>
								<c:if test="${not maintenance && (not masterData || vendorTask || formerlyVendorTask || masterDataReview)}">
								</div>
								<div class="${columnWidth} column">
								</c:if>
								<div class="msdsIndexedField">
								<label for="msds[${index}].materialSearch" class="optionTitleBold" style="width:20%"><fmt:message key="label.material"/>:</label>
									<input type="text" class="msdsReadOnlyField" style="width:70%;font-weight:normal" readonly name="msds[${index}].materialSearch" id="msds[${index}].materialSearch" value="${msds.material.materialDesc}"/>
									<input type="button" class="lookupBtn" name="msds[${index}].materialLookupBtn" id="msds[${index}].materialLookupBtn" ${preSourced?'disabled':''} onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" onclick="msdsIndex.lookupMaterial();"/> 
									<input type="hidden" name="msds[${index}].materialIdSearch" id="msds[${index}].materialIdSearch" value="${msds.materialId}"/>
								</div>
								</div>
								<div class="${columnWidth} column">
								<div class="msdsIndexedField">
								<span>
									<label for="msds[${index}].revisionDateSearch" class="optionTitleBoldRight" style="width:100px;"><fmt:message key="label.revisiondate"/>:&nbsp;</label>
									<select name="msds[${index}].revisionDateSearch" id="msds[${index}].revisionDateSearch" class="selectBox" onchange="msdsIndex.revisionDateChanged(this.options[this.selectedIndex].value, true);">
										<fmt:message var="materialIdReqdMsg" key="errors.required"><fmt:param><fmt:message key="label.materialid"/></fmt:param></fmt:message>
										<c:if test="${empty revisions}"><option value=""><c:out value="${empty msds.materialId?materialIdReqdMsg:''}"/></option></c:if>
										<c:forEach var="revBean" items="${revisions}" varStatus="status2">
											<fmt:formatDate var="revDateVal" type="date" value="${revBean.revisionDate}" pattern="${dateTimeFormatPattern}"/>
											<option value="${revDateVal}" <c:out value="${revDateVal eq param.revisionDate||revBean.revisionDate eq msds.revisionDate?'selected':''}"/> >${empty revBean.revisionDateDisplay?revDateVal:revBean.revisionDateDisplay}</option>
										</c:forEach>
										<c:if test="${not empty msds.materialId && (empty catalogQueueRow || catalogQueueRow.task eq 'SDS Sourcing')}">
											<c:if test="${empty revisions}"><option value="NOT_REQUIRED"><fmt:message key="label.na"/></option></c:if>
										</c:if>
									</select>
								</span>
								<c:if test="${readwriteRequest}">
								<span>
									<a id="msds[${index}].revisionDateNew" href="#" onclick="msdsIndex.openRevisionDateCalendar(${index});" class="${empty msds.materialId?'invisible':''}"><fmt:message key="label.new"/></a>
								</span>
								</c:if>
								</div>
								</div>
								<c:if test="${showCustom && (maintenance || (masterData && not vendorTask && not formerlyVendorTask))}">
								<div class="${columnWidth} column">
								<div class="msdsIndexedField">
								<label for="msds[${index}].companyId" class="optionTitleBold"><fmt:message key="label.company"/>:</label>
									<select name="msds[${index}].companyId" id="msds[${index}].companyId" class="selectBox" onchange="msdsIndex.changeCustomerCompany()">
									<c:forEach var="compBean" items="${customerCompanies}" varStatus="status3">
										<option value="${compBean.companyId}" <c:if test="${msds.companyId eq compBean.companyId}">selected</c:if>>${compBean.companyName}</option>
									</c:forEach>
									</select>
								</div>
								</div>
								</c:if>
							</c:when>
							<c:otherwise>
								<div class="fullWidth column">
									<input class="inputBox" type="hidden" name="msds[${index}].mfgIdSearch" id="msds[${index}].mfgIdSearch" value="${msds.mfg.mfgId}"/>
									<input class="inputBox" type="hidden" name="msds[${index}].materialIdSearch" id="msds[${index}].materialIdSearch" value="${msds.materialId}"/>
									<fmt:formatDate var="revDateVal" type="date" value="${msds.revisionDate}" pattern="${dateTimeFormatPattern}"/>
									<input class="inputBox" type="hidden" name="msds[${index}].revisionDateSearch" id="msds[${index}].revisionDateSearch" value="${revDateVal}"/>
								</div>
							</c:otherwise>
							</c:choose>
							
							<div id="msdsDetailDiv${index}">
							<c:if test="${not empty msds}">
							<%@ include file="/client/catalog/msdsindexingresults.jsp" %>
							</c:if>
							</div>
							</div>
							<div id="footer" class="messageBar"></div>
						</div>
						<div class="roundbottom" style="width:100%;">
							<div class="roundbottomright">
								<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
							</div>
						</div>
					</div>
				</div>
				<!-- Search Option Ends -->
		</div>
		<!-- Content area ends -->