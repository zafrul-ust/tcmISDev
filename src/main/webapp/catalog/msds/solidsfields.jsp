<div id="${compSection}.solidsfields">
<fieldset>
	<legend class="msdsSectionHeader">${_solids}</legend>
	<div style="padding:3px;">
		<div class="msdsIndexedField">
			<label for="${compSection}.solids" class="optionTitle">${_value}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.solids" id="${compSection}.solids" size="20" maxlength="20" value="${item.solids}">
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.solidsLower" class="optionTitle">${_lower}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.solidsLower" id="${compSection}.solidsLower" size="20" maxlength="20" value="${item.solidsLower}">
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.solidsUpper" class="optionTitle">${_upper}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.solidsUpper" id="${compSection}.solidsUpper" size="20" maxlength="20" value="${item.solidsUpper}">
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.solidsUnit" class="optionTitle">${_units}:</label><br/>
				 <select name="${compSection}.solidsUnit" id="${compSection}.solidsUnit" class="selectBox">    			
					<option value=""></option>
					<option value="%(w/w)" ${"%(w/w)" eq item.solidsUnit?'selected':''}>%(w/w)</option>
					<option value="g/L" ${"g/L" eq item.solidsUnit?'selected':''}>g/L</option>
					<option value="lb/gal" ${"lb/gal" eq item.solidsUnit?'selected':''}>lb/gal</option>
				</select>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.solidsSource" class="optionTitle">${_source}:</label><br/>
				<select name="${compSection}.solidsSource" id="${compSection}.solidsSource" class="selectBox">    			
					<option value=""></option>
					<c:set var="sSrc" value="${empty item.solidsSource?'MSDS':item.solidsSource}"/>
					<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status2">
					<option value="${sourceBean.dataSource}" ${sourceBean.dataSource eq sSrc?'selected':''}>${sourceBean.dataSource}</option>
					</c:forEach>
				</select>
		</div>
		<div id="${compSection}.solidsQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.solidsQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.solidsQcErrorType" id="${compSection}.solidsQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="solidsErrorType" value="${empty item.qcData.solidsQcErrorType?'':item.qcData.solidsQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq solidsErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
	</div>
</fieldset>
</div>