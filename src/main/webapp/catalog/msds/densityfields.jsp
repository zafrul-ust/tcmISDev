<div id="${compSection}.densityfields">
<fieldset>
	<legend class="msdsSectionHeader">${_density}</legend>
	<div style="padding:3px;">
		<div class="msdsIndexedField">
			<label for="${compSection}.densityDetect" class="optionTitle" style="white-space:nowrap;">${_detect}:</label><br/>
				<select name="${compSection}.densityDetect" id="${compSection}.densityDetect" class="selectBox" onchange="msdsIndex.blockDisplays('density',value,${prefix eq 'co'})">    
					<option value=""<c:if test="${empty item.densityDetect}"> selected</c:if>></option>			
					<c:forEach var="detectBean" items="${vvDetectBeanCollection}" varStatus="status2">
						<c:if test="${detectBean.flashPointOnly == 'N' || (detectBean.flashPointOnly == 'Y' && isFlashDetect == 'Y')}">
							<option value="${detectBean.detect}" ${detectBean.detect eq item.densityDetect?'selected':''}>${detectBean.description}</option>
						</c:if>
					</c:forEach>
				</select>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.density" class="optionTitle" style="white-space:nowrap;">${_value}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.density" id="${compSection}.density" size="20" maxlength="20" value="${item.density}">
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.densityUpper" class="optionTitle" style="white-space:nowrap;">${_upper}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.densityUpper" id="${compSection}.densityUpper" size="20" maxlength="20" value="${item.densityUpper}">
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.densityUnit" class="optionTitle" style="white-space:nowrap;">${_units}:</label><br/>
				 <select name="${compSection}.densityUnit" id="${compSection}.densityUnit" class="selectBox">    			
					<option value=""></option>
					<option value="g/L" ${"g/L" eq item.densityUnit?'selected':''}>g/L</option>
					<option value="g/cm3" ${"g/cm3" eq item.densityUnit?'selected':''}>g/cm&sup3;</option>
					<option value="g/mL" ${"g/mL" eq item.densityUnit?'selected':''}>g/mL</option>
					<option value="kg/L" ${"kg/L" eq item.densityUnit?'selected':''}>kg/L</option>
					<option value="kg/m3" ${"kg/m3" eq item.densityUnit?'selected':''}>kg/m&sup3;</option>
					<option value="lb/ft3" ${"lb/ft3" eq item.densityUnit?'selected':''}>lb/ft&sup3;</option>
					<option value="lb/gal" ${"lb/gal" eq item.densityUnit?'selected':''}>lb/gal</option>
					<option value="lb/in3" ${"lb/in3" eq item.densityUnit?'selected':''}>lb/in&sup3;</option>
				</select>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.densitySource" class="optionTitle" style="white-space:nowrap;">${_source}:</label><br/>
				<select name="${compSection}.densitySource" id="${compSection}.densitySource" class="selectBox">    
					<option value=""></option>
					<c:set var="dSrc" value="${empty item.densitySource?'MSDS':item.densitySource}"/>
					<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status2">	
					<option value="${sourceBean.dataSource}" ${sourceBean.dataSource eq dSrc?'selected':''}>${sourceBean.dataSource}</option>
					</c:forEach>
				</select>
		</div>
		<div id="${compSection}.densityQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.densityQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.densityQcErrorType" id="${compSection}.densityQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="densityErrorType" value="${empty item.qcData.densityQcErrorType?'':item.qcData.densityQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq densityErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
	</div>
</fieldset>
</div>