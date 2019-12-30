<div id="${compSection}.vocfields">
<fieldset>
	<legend class="msdsSectionHeader">${_voc}</legend>
	<div style="padding:3px;">
		<div style="padding:2px;margin-bottom:5px;">
			<label for="${compSection}.voc" class="optionTitle">${_value}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.voc" id="${compSection}.voc" size="20" maxlength="20" value="${item.voc}">
		</div>
		<div style="padding:2px;margin-bottom:5px;">
			<label for="${compSection}.vocLower" class="optionTitle">${_lower}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.vocLower" id="${compSection}.vocLower" size="20" maxlength="20" value="${item.vocLower}">
		</div>
		<div style="padding:2px;margin-bottom:5px;">
			<label for="${compSection}.vocUpper" class="optionTitle">${_upper}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.vocUpper" id="${compSection}.vocUpper" size="20" maxlength="20" value="${item.vocUpper}">
		</div>
		<div style="padding:2px;margin-bottom:5px;">
			<label for="${compSection}.vocUnit" class="optionTitle">${_units}:</label><br/>
				 <select name="${compSection}.vocUnit" id="${compSection}.vocUnit" class="selectBox">    			
					<option value=""></option>
					<option value="%(w/w)" ${"%(w/w)" eq item.vocUnit?'selected':''}>%(w/w)</option>
					<option value="g/L" ${"g/L" eq item.vocUnit?'selected':''}>g/L</option>
					<option value="lb/gal" ${"lb/gal" eq item.vocUnit?'selected':''}>lb/gal</option>
					<option value="%(v/v)" ${"%(v/v)" eq item.vocUnit?'selected':''}>%(v/v)</option>
				</select>
		</div>
		<div style="padding:2px;margin-bottom:5px;">
			<label for="${compSection}.vocSource" class="optionTitle">${_source}:</label><br/>
				<select name="${compSection}.vocSource" id="${compSection}.vocSource" class="selectBox">    			
					<option value=""></option>
					<c:set var="vocSrc" value="${empty item.vocSource?'MSDS':item.vocSource}"/>
					<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status2">
					<option value="${sourceBean.dataSource}" ${sourceBean.dataSource eq vocSrc?'selected':''}>${sourceBean.dataSource}</option>
					</c:forEach>
				</select>
		</div>
		<div id="${compSection}.vocQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.vocQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.vocQcErrorType" id="${compSection}.vocQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="vocErrorType" value="${empty item.qcData.vocQcErrorType?'':item.qcData.vocQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq vocErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
	</div>
</fieldset>
</div>