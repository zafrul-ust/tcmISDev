<div id="msds[${index}].ghspictograms">
<fieldset>
	<legend class="msdsSectionHeader"><fmt:message key="label.selectpictograms"/></legend>
		<c:forEach var="pictogramBean" items="${msds.pictograms}" varStatus="picStatus">
			<c:set var="picSelected" value="${empty picStatus.current.materialId}"/>
			<c:set var="picOnSds" value="${empty picStatus.current.materialId || picStatus.current.onSds}"/>
			
			<%--<c:if test="${picStatus.index == 4 || picStatus.index == 5}"><c:set var="picOnSds" value="${false}"/></c:if>--%>
			
			
			<c:if test="${picStatus.current.fileName eq '0'}">
		<div style="text-align:left;width:96%;float:left;padding-bottom:2px;">
			<input class="radioBtns" type="checkbox" name="msdspictogram${index}[0].pictogramId" id="msds[${index}].${picStatus.current.pictogramId}Pic" value="${picStatus.current.pictogramId}" <c:out value="${empty picStatus.current.materialId?'':'checked'}"/> onclick="return msdsIndex.selectPictogram('${picStatus.current.pictogramId}', event);"/>
			<label for="msds[${index}].${picStatus.current.pictogramId}Pic" class="optionTitleBold">${picStatus.current.pictogramDescription}</label>
			<input type="checkbox" style="display:none" disabled id="msds[${index}].${picStatus.current.pictogramId}Original" <c:out value="${empty picStatus.current.materialId?'':'checked'}"/> />
			<input type="button" id="msds[${index}].pictogramReset" onclick="msdsIndex.resetPictograms()" value="<fmt:message key="label.reset"/>" class="smallBtns" style="margin-left:40px;"/>
		</div>
			</c:if>
			<c:if test="${picStatus.current.fileName ne '0'}">
		<div class="pictogram"  onclick="return msdsIndex.selectPictogram('${picStatus.current.pictogramId}', event);">
			<input class="radioBtns" type="checkbox" name="msdspictogram${index}[${picStatus.index}].pictogramId" id="msds[${index}].${picStatus.current.pictogramId}Pic" value="${picStatus.current.pictogramId}" <c:out value="${empty picStatus.current.materialId?'':'checked'}"/> style="position:absolute;bottom:0px;left:0px"/>
			<img src="/images/ghs/${picStatus.current.fileName}" alt="${picStatus.current.pictogramDescription}" title="${picStatus.current.pictogramDescription}" height="64" width="64" style="position:absolute;bottom:0px;right:0px"/>
			<label for="msds[${index}].${picStatus.current.pictogramId}Pic" class="optionTitleBold">${picStatus.current.pictogramDescription}</label>
			<label for="msds[${index}].${picStatus.current.pictogramId}Pic" id="msds[${index}].${picStatus.current.pictogramId}OnSdsMsg" class="maxcomEstimate" style="<c:out value="${picOnSds?'display:none':'display:inline'}" />" title="${_maxcomestimate}">(${_estimate})</label>
			<input type="checkbox" style="display:none" name="msdspictogram${index}[${picStatus.index}].onSds" id="msds[${index}].${picStatus.current.pictogramId}OnSds" <c:out value="${picOnSds?'checked':''}"/> />
			<input type="checkbox" style="display:none" disabled id="msds[${index}].${picStatus.current.pictogramId}OriginalOnSds" <c:out value="${picOnSds?'checked':''}"/> />
			<input type="checkbox" style="display:none" disabled id="msds[${index}].${picStatus.current.pictogramId}Sticky" <c:out value="${empty picStatus.current.materialId?'':'checked'}"/> />
			<input type="checkbox" style="display:none" disabled id="msds[${index}].${picStatus.current.pictogramId}Original" <c:out value="${empty picStatus.current.materialId?'':'checked'}"/> />
		</div>
			</c:if>
		</c:forEach>
		<p style="clear:both"></p>
		<div id="${compSection}.ghsPictogramsQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.ghsPictogramsQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.ghsPictogramsQcErrorType" id="${compSection}.ghsPictogramsQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="picErrorType" value="${empty item.qcData.ghsPictogramsQcErrorType?'':item.qcData.ghsPictogramsQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq picErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
</fieldset>
</div>