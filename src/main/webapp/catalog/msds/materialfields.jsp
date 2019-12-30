<div id="msds[${index}].materialfields">
<c:set var="englishQueueRow" value="${empty catalogQueueRow || empty catalogQueueRow.localeCode || catalogQueueRow.localeCode eq 'en_US'}"/>
<c:set var="englishImg" value="${empty item || empty item.localeCode || item.localeCode eq 'en_US'}"/>
<fieldset>
	<legend class="msdsSectionHeader"><fmt:message key="label.material"/></legend>
	<div style="padding:3px;">
		<div class="msdsIndexedField">
			<label for="msds[${index}].materialId" class="optionTitle" style="white-space:nowrap">${_id}:&nbsp;</label><br/>
				<input style="border:none;background:none;" type="text" class="optionTitleBold" readonly name="msds[${index}].materialId" id="msds[${index}].materialId" value="${msds.material.materialId}" tabindex="-1">
		</div>
		<%-- Using <c:out /> for customer msds no, material, trade name, product codes, and comments. 
			These all may have a " character, and thus need to be escaped. 
			The <c:out /> tag will do this automatically. --%>
		<c:if test="${masterData || (vendorTask && isWescoModule && supplierIsWesco)}">
		<div class="msdsIndexedField" style="display:none;">
			<label for="msds[${index}].componentMsds" class="optionTitle" style="white-space:nowrap"><fmt:message key="label.customermsdsno"/>:</label><br/>
				<input class="inputBox" type="text" name="msds[${index}].componentMsds" id="msds[${index}].componentMsds" maxlength="40" value="<c:out value="${msds.componentMsds}"/>" >
				<c:if test="${generateSdsFromSequence == 'true'}">
					<input name="msds[${index}].generateSdsNumberButton" id="msds[${index}].generateSdsNumberButton" type="button" value="<fmt:message key="label.generatesds"/>"
						   class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
						   onclick="msdsIndex.generateSdsNumber(false); return false;">
				</c:if>
				<input type="hidden" name="generateSdsFromSequence" id="generateSdsFromSequence" value="${generateSdsFromSequence}"/>
		</div>
		</c:if>
		<fieldset>
		<legend class="optionTitleBold">English (US)</legend>
		<div class="msdsIndexedField">
			<label for="msds[${index}].materialDesc" class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.description"/>:</label><br/>
				<input class="inputBox" style="width:96%;" type="text" name="msds[${index}].materialDesc" id="msds[${index}].materialDesc" maxlength="250"	value="<c:out value="${msds.material.materialDesc}"/>" >
		</div>
		<div id="${compSection}.materialDescriptionQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.materialDescriptionQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.materialDescriptionQcErrorType" id="${compSection}.materialDescriptionQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="descErrorType" value="${empty item.qcData.materialDescriptionQcErrorType?'':item.qcData.materialDescriptionQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq descErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
		<div class="msdsIndexedField">
			<label for="msds[${index}].mfgTradeName" class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.tradename"/>:</label><br/>
				<input class="inputBox" style="width:96%;" type="text" name="msds[${index}].mfgTradeName" id="msds[${index}].mfgTradeName" maxlength="600" value="<c:out value="${msds.material.tradeName}"/>" >
		</div>
		<div id="${compSection}.tradeNameQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.tradeNameQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.tradeNameQcErrorType" id="${compSection}.tradeNameQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="tradeNameErrorType" value="${empty item.qcData.tradeNameQcErrorType?'':item.qcData.tradeNameQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq tradeNameErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.productCode" class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.productcodes"/>:</label><br/>
				<input class="inputBox" style="width:96%;" type="text" name="msds[${index}].productCode" id="msds[${index}].productCode" maxlength="200" value="<c:out value="${msds.material.productCode}"/>" >
		</div>
		<div id="${compSection}.productCodeQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.productCodeQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.productCodeQcErrorType" id="${compSection}.productCodeQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="pcErrorType" value="${empty item.qcData.productCodeQcErrorType?'':item.qcData.productCodeQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq pcErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
		</fieldset>
		<fieldset id="${compSection}.materialLocaleProperties" style="${not englishImg || not englishQueueRow?'':'display:none'}">
		<legend class="optionTitleBold">${empty catalogQueueRow?(empty item.localeDisplay?item.localeCode:item.localeDisplay):catalogQueueRow.localeDisplay}</legend>
		<div class="msdsIndexedField">
			<label for="${compSection}.localizedMaterialDesc" class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.description"/>:</label><br/>
				<input class="inputBox" style="width:96%;" type="text" name="${compSection}.localizedMaterialDesc" id="${compSection}.localizedMaterialDesc" maxlength="250"	value="<c:out value="${msds.localizedMaterial.materialDesc}"/>" >
		</div>
		<div id="${compSection}.localizedMaterialDescriptionQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.localizedMaterialDescriptionQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.localizedMaterialDescQcErrorType" id="${compSection}.localizedMaterialDescQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="localizedDescErrorType" value="${empty item.qcData.localizedMaterialDescQcErrorType?'':item.qcData.localizedMaterialDescQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq localizedDescErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.localizedMfgTradeName" class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.tradename"/>:</label><br/>
				<input class="inputBox" style="width:96%;" type="text" name="${compSection}.localizedMfgTradeName" id="${compSection}.localizedMfgTradeName" maxlength="600" value="<c:out value="${msds.localizedMaterial.tradeName}"/>" >
		</div>
		<div id="${compSection}.localizedTradeNameQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.localizedTradeNameQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.localizedTradeNameQcErrorType" id="${compSection}.localizedTradeNameQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="localizedTradeNameErrorType" value="${empty item.qcData.localizedTradeNameQcErrorType?'':item.qcData.localizedTradeNameQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq localizedTradeNameErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
		</fieldset>
		<c:if test="${masterData || (vendorTask && isWescoModule && supplierIsWesco)}">
		<div class="msdsIndexedField">
				<input class="radioBtns" disabled type="checkbox" name="msds[${index}].customerOnlyMsdsDisp" id="msds[${index}].customerOnlyMsdsDisp" <c:if test="${msds.material.customerOnlyMsds == 'Y'}">checked</c:if>/>
				<input type="hidden" name="msds[${index}].customerOnlyMsds" id="msds[${index}].customerOnlyMsds" value="${msds.material.customerOnlyMsds}"/>
			<label for="msds[${index}].customerOnlyMsds" class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.customeronlymsds"/></label>
		</div>
		</c:if>
		<div class="msdsIndexedField" style="display:none;">
			<label for="msds[${index}].comments" class="optionTitle" style="white-space:nowrap">${_comments}:</label><br/>
				<textarea name="msds[${index}].comments" id="msds[${index}].comments" rows="3" class="inputBox" style="width:96%" onKeyDown="msdsIndex.limitText(this,4000);"><c:out value="${msds.comments}"/></textarea>
		</div>
	</div>
</fieldset>
</div>