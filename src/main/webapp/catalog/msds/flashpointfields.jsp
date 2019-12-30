<div id="${compSection}.flashpointfields">
<fieldset>
	<legend class="msdsSectionHeader">${_flashpoint}</legend>
	<div style="padding:3px;">
		<div class="msdsIndexedField">
			<label for="${compSection}.flashPointDetect" class="optionTitle">${_detect}:</label><br/>
				<c:set var="isFlashDetect" value='Y'/>
				<select name="${compSection}.flashPointDetect" id="${compSection}.flashPointDetect" class="selectBox" onchange="msdsIndex.blockDisplays('flashPoint',value, ${prefix eq 'co'});">    
					<option value=""<c:if test="${empty item.flashPointDetect}"> selected</c:if>></option>			
					<c:forEach var="detectBean" items="${vvDetectBeanCollection}" varStatus="status2">
						<c:if test="${detectBean.flashPointOnly == 'N' || (detectBean.flashPointOnly == 'Y' && isFlashDetect == 'Y')}">
							<option value="${detectBean.detect}" ${detectBean.detect eq item.flashPointDetect?'selected':''}>${detectBean.description}</option>
						</c:if>
					</c:forEach>
				</select>
				<c:set var="isFlashDetect" value='N'/>				
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.flashPointLower" class="optionTitle">${_value}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.flashPointLower" id="${compSection}.flashPointLower" size="20" maxlength="20" value="${item.flashPointLower}">
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.flashPointUpper" class="optionTitle">${_upper}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.flashPointUpper" id="${compSection}.flashPointUpper" size="20" maxlength="20" value="${item.flashPointUpper}">
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.flashPointUnit" class="optionTitle">${_units}:</label><br/>
				 <select name="${compSection}.flashPointUnit" id="${compSection}.flashPointUnit" class="selectBox">    			
					<option value=""></option>
					<option value="C" ${"C" eq item.flashPointUnit?'selected':''}>&deg;${_cforcelsius}</option>
					<option value="F" ${"F" eq item.flashPointUnit?'selected':''}>&deg;${_fforfahrenheit}</option>
				</select>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.flashPointMethod" class="optionTitle">${_method}:</label><br/>
				<select name="${compSection}.flashPointMethod" id="${compSection}.flashPointMethod" class="selectBox">
					<option value=""></option>
					<c:forEach var="detectBean" items="${vvFlashPointMethodCollection}" varStatus="status3">
					<option value="${detectBean.flashPointMethod}" ${detectBean.flashPointMethod eq item.flashPointMethod?'selected':''}>${detectBean.methodName}</option>
					</c:forEach>
				</select>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.flashPointSource" class="optionTitle">${_source}:</label><br/>
				<select name="${compSection}.flashPointSource" id="${compSection}.flashPointSource" class="selectBox">
					<option value=""></option>
					<c:set var="fpSrc" value="${empty item.flashPointSource?'MSDS':item.flashPointSource}"/>
					<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status2">
					<option value="${sourceBean.dataSource}" ${sourceBean.dataSource eq fpSrc?'selected':''}>${sourceBean.dataSource}</option>
					</c:forEach>
				</select>
		</div>
		<div id="${compSection}.flashPointQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.flashPointQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.flashPointQcErrorType" id="${compSection}.flashPointQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="fpErrorType" value="${empty item.qcData.flashPointQcErrorType?'':item.qcData.flashPointQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq fpErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
	</div>
</fieldset>
</div>