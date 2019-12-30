<div id="msds[${index}].mfgfields">
<fieldset>
	<legend class="msdsSectionHeader">${_manufacturer}</legend>
	<div class="msdsSectionShell">
		<div class="msdsIndexedField">
			<label for="msds[${index}].mfgId" class="optionTitle wrapless">${_id}:</label><br/>
				<input type="text" class="optionTitleBold msdsReadOnlyField" readonly name="msds[${index}].mfgId" id="msds[${index}].mfgId" value="${msds.mfg.mfgId}" tabindex="-1">
		</div>
		<div class="msdsIndexedField">
			<label for="msds[${index}].manufacturer" class="optionTitle wrapless">${_manufacturer}:</label><br/>
				<input class="${maintenance || masterData?'inputBox':'optionTitleBold msdsReadOnlyField'}" style="width:96%;" type="text" ${maintenance || masterData?'':'readonly'} name="msds[${index}].manufacturer" id="msds[${index}].manufacturer" maxlength="60" value="${msds.mfg.mfgDesc}">
		</div>
		<div class="msdsIndexedField">
			<label for="msds[${index}].mfgShortName" class="optionTitle wrapless"><fmt:message key="label.shortname"/>:</label><br/>
				<input class="${maintenance || masterData?'inputBox':'optionTitleBold msdsReadOnlyField'}" style="width:96%;" type="text" ${maintenance || masterData?'':'readonly'} name="msds[${index}].mfgShortName" id="msds[${index}].mfgShortName" size="25" value="${msds.mfg.mfgShortName}" >
		</div>
		<div class="msdsIndexedField">
			<label for="msds[${index}].mfgUrl" class="optionTitle wrapless"><fmt:message key="label.url"/>:</label><br/>
				<input class="inputBox" style="width:96%;" type="text" name="msds[${index}].mfgUrl" id="msds[${index}].mfgUrl" maxlength="200" value="<c:out value="${msds.mfg.mfgUrl}"/>">
		</div>
		<div class="msdsIndexedField">
			<label for="msds[${index}].contact" class="optionTitle wrapless"><fmt:message key="label.contact"/>:</label><br/>
				<c:if test="${ ! empty msds.mfg.mfgUrl}"><a href="${msds.mfg.mfgUrl}" target="_blank">${msds.mfg.contact}</a></c:if>
				<c:if test="${empty msds.mfg.mfgUrl}"><span id="msds[${index}].contactSpan" class="optionTitleBold">${msds.mfg.contact}</span></c:if>
				<input type="hidden" id="msds[${index}].contact" name="msds[${index}].contact" value="${msds.mfg.contact}" tabindex="-1"/>
		</div>
		<div class="msdsIndexedField">
			<label for="msds[${index}].phone" class="optionTitle wrapless"><fmt:message key="label.phone"/>:</label><br/>
				<input type="text" class="optionTitleBold msdsReadOnlyField" readonly id="msds[${index}].phone" name="msds[${index}].phone" value="${msds.mfg.phone}" tabindex="-1"/>
		</div>
		<div class="msdsIndexedField">
			<label for="msds[${index}].email" class="optionTitle wrapless"><fmt:message key="label.email"/>:</label><br/>
				<input type="text" class="optionTitleBold msdsReadOnlyField" readonly id="msds[${index}].email" name="msds[${index}].email" value="${msds.mfg.email}" tabindex="-1"/>
		</div>
		<%-- value wrapped in <c:out /> to escape HTML characters --%>
		<div class="msdsIndexedField">
			<label for="msds[${index}].notes" class="optionTitle wrapless">${_notes}:</label><br/>
				<textarea class="msdsReadOnlyArea optionTitleBold" rows="3" readonly id="msds[${index}].notes" name="msds[${index}].notes" tabindex="-1"><c:out value="${msds.mfg.notes}"/></textarea>
		</div>
		<div id="${compSection}.manufacturerQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.manufacturerQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.manufacturerQcErrorType" id="${compSection}.manufacturerQcErrorType" class="selectBox" ${qcStatus eq 'complete'  || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="mfgErrorType" value="${empty item.qcData.manufacturerQcErrorType?'':item.qcData.manufacturerQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq mfgErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
	</div>
</fieldset>
</div>