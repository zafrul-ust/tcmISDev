<div id="${compSection}.nfpafields">
<fieldset>
	<legend class="msdsSectionHeader">${_nfpa}</legend>
	<div style="padding:3px;">
		<div class="msdsIndexedField">
			<label for="${compSection}.health" class="optionTitle" style="white-space:nowrap">${_health}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.health" id="${compSection}.health" size="20" maxlength="30" value="${item.health}">
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.flammability" class="optionTitle" style="white-space:nowrap">${_flammability}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.flammability" id="${compSection}.flammability" size="20" maxlength="30" value="${item.flammability}">
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.reactivity" class="optionTitle" style="white-space:nowrap">${_reactivity}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.reactivity" id="${compSection}.reactivity" size="20" maxlength="30" value="${item.reactivity}">
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.specificHazard" class="optionTitle" style="white-space:nowrap">${_special}:</label><br/>
				 <select name="${compSection}.specificHazard" id="${compSection}.specificHazard" class="selectBox">
					<option value=""></option>
					<option value="ACID" ${"ACID" eq item.specificHazard?'selected':''}>Acid</option>
					<option value="ALK" ${"ALK" eq item.specificHazard?'selected':''}>Alkali</option>
					<option value="COR" ${"COR" eq item.specificHazard?'selected':''}>Corrosive</option>
					<option value="OXY" ${"OXY" eq item.specificHazard?'selected':''}>Oxidizer</option>
					<option value="RAD" ${"RAD" eq item.specificHazard?'selected':''}>Radiation Hazard</option>
					<option value="SA" ${"SA" eq item.specificHazard?'selected':''}>Simple Asphyxiant Gas</option>
					<option value="W" ${"W" eq item.specificHazard?'selected':''}>Use No Water</option>
				</select>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.nfpaSource" class="optionTitle" style="white-space:nowrap">${_source}:</label><br/>
				<select name="${compSection}.nfpaSource" id="${compSection}.nfpaSource" class="selectBox">    
					<option value=""></option>
					<c:set var="nfpaSrc" value="${empty item.nfpaSource?'MSDS':item.nfpaSource}"/>
					<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status2">
					<option value="${sourceBean.dataSource}" ${sourceBean.dataSource eq nfpaSrc?'selected':''}>${sourceBean.dataSource}</option>
					</c:forEach>
				</select>
		</div>
		<div id="${compSection}.nfpaQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.nfpaQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.nfpaQcErrorType" id="${compSection}.nfpaQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="nfpaErrorType" value="${empty item.qcData.nfpaQcErrorType?'':item.qcData.nfpaQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq nfpaErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
	</div>
</fieldset>
</div>