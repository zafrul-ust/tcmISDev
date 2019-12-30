<div id="${compSection}.voclwesfields">
<fieldset>
	<legend class="msdsSectionHeader">${_voclesswaterandexempt}</legend>
	<div style="padding:3px;">
		<div style="padding:2px;margin-bottom:5px;">
			<label for="${compSection}.vocLessH2oExempt" class="optionTitle">${_value}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.vocLessH2oExempt" id="${compSection}.vocLessH2oExempt" size="20" maxlength="20" value="${item.vocLessH2oExempt}">
		</div>
		<div style="padding:2px;margin-bottom:5px;">
			<label for="${compSection}.vocLessH2oExemptLower" class="optionTitle">${_lower}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.vocLessH2oExemptLower" id="${compSection}.vocLessH2oExemptLower" size="20" maxlength="20" value="${item.vocLessH2oExemptLower}">
		</div>
		<div style="padding:2px;margin-bottom:5px;">
			<label for="${compSection}.vocLessH2oExemptUpper" class="optionTitle">${_upper}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.vocLessH2oExemptUpper" id="${compSection}.vocLessH2oExemptUpper" size="20" maxlength="20" value="${item.vocLessH2oExemptUpper}">
		</div>
		<div style="padding:2px;margin-bottom:5px;">
			<label for="${compSection}.vocLessH2oExemptUnit" class="optionTitle">${_units}:</label><br/>
				 <select name="${compSection}.vocLessH2oExemptUnit" id="${compSection}.vocLessH2oExemptUnit" class="selectBox">
					<option value=""></option>
					<option value="g/L" ${"g/L" eq item.vocLessH2oExemptUnit?'selected':''}>g/L</option>
					<option value="lb/gal" ${"lb/gal" eq item.vocLessH2oExemptUnit?'selected':''}>lb/gal</option>
				</select>
		</div>
		<div style="padding:2px;margin-bottom:5px;">
			<label for="${compSection}.vocLessH2oExemptSource" class="optionTitle">${_source}:</label><br/>
				<select name="${compSection}.vocLessH2oExemptSource" id="${compSection}.vocLessH2oExemptSource" class="selectBox">
					<option value=""></option>
					<c:set var="voclSrc" value="${empty item.vocLessH2oExemptSource?'MSDS':item.vocLessH2oExemptSource}"/>
					<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status2">
					<option value="${sourceBean.dataSource}" ${sourceBean.dataSource eq voclSrc?'selected':''}>${sourceBean.dataSource}</option>
					</c:forEach>
				</select>
		</div>
		<div id="${compSection}.vocLessH2oExemptQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.vocLessH2oExemptQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.vocLessH2oExemptQcErrorType" id="${compSection}.vocLessH2oExemptQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="voclErrorType" value="${empty item.qcData.vocLessH2oExemptQcErrorType?'':item.qcData.vocLessH2oExemptQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq voclErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
	</div>
</fieldset>
</div>