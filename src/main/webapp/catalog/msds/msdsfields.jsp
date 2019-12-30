<div id="${compSection}.msdsfields">
<fieldset>
	<legend class="msdsSectionHeader">${_msds}</legend>
	<div style="padding:3px;">
	<c:if test="${prefix eq 'msds'}">
		<input type="hidden" id="${compSection}.msdsId" name="msds[${index}].msdsId" value="${msds.msdsId}"/>
		<div class="msdsIndexedField">
			<label for="${compSection}.revisionDateDisp" class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.revisiondate"/>:</label><br/>
				<fmt:formatDate var="curRevDate" type="date" value="${item.revisionDate}" pattern="${dateTimeFormatPattern}"/>
				<%-- if indexing or authoring --%>
				<input type="text" class="optionTitleBold msdsReadOnlyField" readonly name="${compSection}.revisionDateDisp" id="${compSection}.revisionDateDisp" value="${empty item.revisionDateDisplay?curRevDate:item.revisionDateDisplay}" tabindex="-1"/>
				<input type="hidden" name="${compSection}.revisionDate" id="${compSection}.revisionDate" value="${curRevDate}"/>
				<input type="hidden" name="${compSection}.imageLocaleCode" id="${compSection}.imageLocaleCode" tabindex="-1" value="${item.localeCode}"/>
				<c:if test="${authoring && (vendorCanSubmit || vendorCanApprove)}">
				<span>
					<a id="msds[${index}].revisionDateNew" href="#" onclick="msdsIndex.openRevisionDateCalendar(${index});"><fmt:message key="label.new"/></a>
				</span>
				</c:if>
		</div>
		<c:if test="${authoring}">
		<div class="msdsIndexedField">
			<label for="${compSection}.srcRevisionDateDisp" class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.sourcerevisiondate"/>:</label><br/>
				<fmt:formatDate var="curRevDate" type="date" value="${item.sourceRevisionDate}" pattern="${dateTimeFormatPattern}"/>
				<%-- if indexing or authoring --%>
				<input type="text" class="optionTitleBold msdsReadOnlyField" readonly name="${compSection}.srcRevisionDateDisp" id="${compSection}.srcRevisionDateDisp" value="${empty item.sourceRevisionDateDisplay?'':item.sourceRevisionDateDisplay}" tabindex="-1"/>
				<input type="hidden" name="${compSection}.srcRevisionDate" id="${compSection}.revisionDate" value="${curRevDate}"/><br/>
				<input name="${compSection}.viewSrcFile" id="${compSection}.viewSrcFile" type="button" value="<fmt:message key="label.viewsourcefile"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="msdsIndex.launchSrcFileView(); return false;">
				<%--<input type="hidden" name="${compSection}.srcLocaleCode" id="${compSection}.srcLocaleCode" tabindex="-1" value="${item.sourceLocaleCode}"/> --%>
		</div>
		</c:if>
		<div id="${compSection}.revisionDateQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.revisionDateQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.revisionDateQcErrorType" id="${compSection}.revisionDateQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="revDateErrorType" value="${empty item.qcData.revisionDateQcErrorType?'':item.qcData.revisionDateQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq revDateErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.msdsAuthorId" class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.msdsauthorid"/>:</label><br/>
				<input type="text" class="optionTitleBold msdsReadOnlyField" readonly name="${compSection}.msdsAuthorId" id="${compSection}.msdsAuthorId" value="${item.msdsAuthorId}" tabindex="-1"/>
				<input type="hidden" name="${compSection}.mfrAuthorId" id="${compSection}.mfrAuthorId" value="${mfrAuthor.msdsAuthorId}"/>
		</div>
		<%-- value wrapped in <c:out /> to escape HTML characters --%>
		<div class="msdsIndexedField">
			<label for="${compSection}.msdsAuthorDesc" class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.msdsauthor"/>:</label><br/>
				<input type="text" class="${(readwriteRequest && (not indexing || empty item.msdsAuthorId))?'inputBox':'optionTitleBold msdsReadOnlyField'}" ${readwriteRequest?'':'readonly'} style="width:96%" name="${compSection}.msdsAuthorDesc" id="${compSection}.msdsAuthorDesc" value="<c:out value="${item.msdsAuthorDesc}"/>" tabindex="-1"/>
				<input type="hidden" name="${compSection}.mfrAuthorDesc" id="${compSection}.mfrAuthorDesc" value="<tcmis:jsReplace value="${mfrAuthor.msdsAuthorDesc}" processMultiLines="false"/>"/>
			<c:if test="${readwriteRequest && (not indexing || empty item.msdsAuthorId)}">
			<br />
			<a href="#" id="${compSection}.newAuthor" onclick="msdsIndex.createAuthor();"><fmt:message key="label.new"/></a> |
			<a href="#" id="${compSection}.mfrAuthor" onclick="msdsIndex.setAuthorToMfr();"><fmt:message key="label.sameasmfg"/></a>
			</c:if>
		</div>
		<div id="${compSection}.msdsAuthorQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.msdsAuthorQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.msdsAuthorQcErrorType" id="${compSection}.msdsAuthorQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="authErrorType" value="${empty item.qcData.msdsAuthorQcErrorType?'':item.qcData.msdsAuthorQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq authErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.msdsAuthorCountryAbbrevSpan" class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.countryabbrev"/>:</label><br/>
				<input type="text" class="optionTitleBold msdsReadOnlyField" readonly name="${compSection}.msdsAuthorCountryAbbrev" id="${compSection}.msdsAuthorCountryAbbrev" value="${item.msdsAuthorCountryAbbrev}" tabindex="-1"/>
				<input type="hidden" name="${compSection}.mfrAuthorCountryAbbrev" id="${compSection}.mfrAuthorCountryAbbrev" value="<tcmis:jsReplace value="${mfrAuthor.countryAbbrev}" processMultiLines="false" />"/>
		</div>
		<%-- value wrapped in <c:out /> to escape HTML characters --%>
		<div class="msdsIndexedField">
			<label for="${compSection}.msdsAuthorNotesSpan" class="optionTitle" style="white-space:nowrap;">${_notes}:</label><br/>
				<textarea rows="3" class="optionTitleBold msdsReadOnlyArea" readonly name="${compSection}.msdsAuthorNotes" id="${compSection}.msdsAuthorNotes" tabindex="-1"><c:out value="${item.msdsAuthorNotes}" /></textarea>
			<input type="hidden" name="${compSection}.mfrAuthorNotes" id="${compSection}.mfrAuthorNotes" value="<tcmis:jsReplace value="${mfrAuthor.notes}" processMultiLines="true"/>"/>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.content" class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.content"/>:</label><br/>
				<input type="text" class="optionTitleBold msdsReadOnlyField" readonly name="${compSection}.content" id="${compSection}.content" value="${item.content}" maxlength="80" tabindex="-1"/><br/>
				<input type="hidden" name="${compSection}.sourceContent" id="${compSection}.sourceContent" value="${authoring?item.sourceContent:''}"/>
				<input name="viewFile" id="${compSection}.viewFile" type="button" value="<fmt:message key="label.viewfile"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="msdsIndex.launchFileView(); return false;">
				<c:choose><c:when test="${authoring}">
				<input name="uploadSrcFile" id="${compSection}.uploadSrcFile" type="button" value="<fmt:message key="label.uploadmfrsds"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="msdsIndex.uploadMsds(false); return false;">
				<input name="uploadAuthFile" id="${compSection}.uploadAuthFile" type="button" value="<fmt:message key="label.uploadauthoredsds"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="msdsIndex.uploadMsds(true); return false;">
				<input name="${compSection}.authoringType" id="${compSection}.authoringType" type="hidden" value="${empty item.authoringType?'Authoring':item.authoringType}"/>
				</c:when><c:otherwise>
				<input name="uploadFile" id="${compSection}.uploadFile" type="button" value="<fmt:message key="label.uploadfile"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="msdsIndex.uploadMsds(); return false;" ${indexing?'disabled':''}>								
				</c:otherwise></c:choose>
				<input type="hidden" id="${compSection}.sdsRequired" name="${compSection}.sdsRequired" value="${empty item.sdsRequired?'Y':item.sdsRequired}" />
		</div>
		<div id="${compSection}.contentQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.contentQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.contentQcErrorType" id="${compSection}.contentQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="imgErrorType" value="${empty item.qcData.contentQcErrorType?'':item.qcData.contentQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq imgErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
		<c:set var="tmpPriorityVal" value='${msds.msdsIndexingPriorityId}'/>
		<c:if test="${empty tmpPriorityVal}">
			<c:set var="tmpPriorityVal" value='${catalogFacilityMsdsIndexingPriorityId}'/>
		</c:if>
		<div class="msdsIndexedField" style="${maintenance || enableMaxcomIndexing eq 'Y'?'':'display:none'}">
			<label for="${compSection}.idOnly" class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.idonly"/>:</label><br/>
				<select name="${compSection}.idOnly" id="${compSection}.idOnly" onchange="msdsIndex.setIdOnly()" class="selectBox">
					<c:forEach var="idOnlyCode" items="${idOnlyColl}" varStatus="idOnlyStatus">
					<option value="${idOnlyCode}" <c:if test="${(empty msds.idOnly && idOnlyCode eq 'N') || (msds.idOnly eq idOnlyCode)}">selected</c:if>>${idOnlyCode}</option>
					</c:forEach>
				</select>
				&nbsp;&nbsp;&nbsp;
				<c:set var="tmpPriorityVal" value='${msds.msdsIndexingPriorityId}'/>
				<c:if test="${empty tmpPriorityVal}">
					<c:set var="tmpPriorityVal" value='${catalogFacilityMsdsIndexingPriorityId}'/>
				</c:if>
				<select name="${compSection}.msdsIndexingPriorityId" id="${compSection}.msdsIndexingPriorityId" class="selectBox" style="display:none">
					<option value=""></option>
					<c:forEach var="msdsIndexingPriorityBean" items="${msdsIndexingPriorityColl}" varStatus="msdsIndexingPriorityStatus">
					<option value="${msdsIndexingPriorityBean.msdsIndexingPriorityId}"<c:if test='${tmpPriorityVal eq msdsIndexingPriorityBean.msdsIndexingPriorityId}'> selected</c:if>>${msdsIndexingPriorityBean.msdsIndexingPriorityDesc}</OPTION>
					</c:forEach>
				</select>
		</div>
		<c:if test="${sourcing || indexing || maintenance || (masterData && not formerlyVendorTask)}">
		<div class="msdsIndexedField column fullWidth">
			<input name="${compSection}.attachedDocuments" id="${compSection}.attachedDocuments" type="button" value="<fmt:message key="label.additionaldocuments"/>" class="inputBtns ${empty msds.materialId?'invisible':''}" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="msdsIndex.showMsdsDocuments(); return false;"/>
		</div>
		</c:if>
		</c:if>
		<c:if test="${prefix eq 'co'}">
		<c:if test="${indexing || maintenance || (masterData && not formerlyVendorTask)}">
		<div class="msdsIndexedField column fullWidth">
			<input name="${compSection}.attachedDocuments" id="${compSection}.attachedDocuments" type="button" value="<fmt:message key="label.customerdocuments"/>" class="inputBtns ${empty msds.materialId?'invisible':''}" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="msdsIndex.showCoMsdsDocuments(); return false;">
		</div>
		</c:if>
		</c:if>
		<c:if test="${indexing || maintenance || (masterData && not vendorTask && not formerlyVendorTask)}">
		<div class="msdsIndexedField column halfWidth">
			<input class="optionTitleBold msdsReadOnlyField" readonly id="${compSection}.dataEntryStandard" name="${compSection}.dataEntryStandard" value="${(item.msdsDataEntryStandard == itemDataStandard)?'Up to standard':'Not up to standard'}"/>
		</div>
		<div class="msdsIndexedField column halfWidth">
					<input type="checkbox" name="${compSection}.dataEntryComplete" id="${compSection}.dataEntryComplete" class="radioBtns" ${item.dataEntryComplete?'checked':''} disabled="disabled"/>
			<label for="${compSection}.dataEntryComplete" class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.dataentrycomplete"/></label>
		</div>
		<c:if test="${masterData || (vendorTask && isWescoModule && supplierIsWesco)}">
		<div class="msdsIndexedField column halfWidth">
					<input type="checkbox" name="${compSection}.qcApproved" id="${compSection}.qcApproved" class="radioBtns" ${qcStatus eq 'complete'?'checked':''} disabled="disabled"/>
			<label for="${compSection}.qcApproved" class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.qcapproved"/></label>
		</div>
		</c:if>
		<c:if test="${prefix eq 'msds'}">
		<div class="msdsIndexedField column halfWidth">
				<input onclick="readonly(this)" disabled="disabled" type="checkbox" name="${compSection}.ghsCompliantImage" id="${compSection}.ghsCompliantImage" class="radioBtns" ${item.ghsCompliantImage?'checked':''} value="Y"/>
			<label for="${compSection}.ghsCompliantImage" class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.ghscompliant"/></label>
		</div>
		</c:if>
		</c:if>
	</div>
</fieldset>
</div>