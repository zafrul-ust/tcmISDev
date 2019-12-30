<div id="${compSection}.boilingpointfields">
<fieldset>
	<legend class="msdsSectionHeader">${_boilingpoint}</legend>
	<div style="padding:3px;">
		<div class="msdsIndexedField">
			<label for="${compSection}.boilingPointDetect" class="optionTitle">${_detect}:</label><br/>
				<select name="${compSection}.boilingPointDetect" id="${compSection}.boilingPointDetect" class="selectBox" onchange="msdsIndex.blockDisplays('boilingPoint',value, ${prefix eq 'co'})">    
					<option value=""<c:if test="${empty item.boilingPointDetect}"> selected</c:if>></option>
					<c:forEach var="detectBean" items="${vvDetectBeanCollection}" varStatus="status2">
						<c:if test="${detectBean.flashPointOnly == 'N' || (detectBean.flashPointOnly == 'Y' && isFlashDetect == 'Y')}">
							<option value="${detectBean.detect}" ${detectBean.detect eq item.boilingPointDetect?'selected':''}>${detectBean.description}</option>
						</c:if>
					</c:forEach>			
				</select>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.boilingPointLower" class="optionTitle">${_value}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.boilingPointLower" id="${compSection}.boilingPointLower" size="20" maxlength="20" value="${item.boilingPointLower}">
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.boilingPointUpper" class="optionTitle">${_upper}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.boilingPointUpper" id="${compSection}.boilingPointUpper" size="20" maxlength="20" value="${item.boilingPointUpper}">
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.boilingPointUnit" class="optionTitle">${_units}:</label><br/>
				 <select name="${compSection}.boilingPointUnit" id="${compSection}.boilingPointUnit" class="selectBox">    			
					<option value=""></option>
					<option value="C" ${"C" eq item.boilingPointUnit?'selected':''}>&deg;${_cforcelsius}</option>
					<option value="F" ${"F" eq item.boilingPointUnit?'selected':''}>&deg;${_fforfahrenheit}</option>
				</select>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.boilingPointDetail" class="optionTitle">${_detail}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.boilingPointDetail" id="${compSection}.boilingPointDetail" size="20" maxlength="20" value="${item.boilingPointDetail}">
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.boilingPointSource" class="optionTitle">${_source}:</label><br/>
				<select name="${compSection}.boilingPointSource" id="${compSection}.boilingPointSource" class="selectBox">    
					<option value=""></option>
					<c:set var="bpSrc" value="${empty item.boilingPointSource?'MSDS':item.boilingPointSource}"/>
					<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status2">
					<option value="${sourceBean.dataSource}" ${sourceBean.dataSource eq bpSrc?'selected':''}>${sourceBean.dataSource}</option>
					</c:forEach>
				</select>
		</div>
		<div id="${compSection}.boilingPointQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.boilingPointQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.boilingPointQcErrorType" id="${compSection}.boilingPointQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="bpErrorType" value="${empty item.qcData.boilingPointQcErrorType?'':item.qcData.boilingPointQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq bpErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
	</div>
</fieldset>
</div>