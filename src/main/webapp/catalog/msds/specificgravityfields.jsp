<div id="${compSection}.specificgravityfields">
<fieldset>
	<legend class="msdsSectionHeader">${_specificgravity}</legend>
	<div style="padding:3px;">
		<div class="msdsIndexedField">
			<label for="${compSection}.specificGravityDetect" class="optionTitle">${_detect}:</label><br/>
				<c:set var="isFlashDetect" value='N'/>
				<select name="${compSection}.specificGravityDetect" id="${compSection}.specificGravityDetect" class="selectBox" onchange="msdsIndex.blockDisplays('specificGravity',value, ${prefix eq 'co'})">    
					<option value=""<c:if test="${empty item.specificGravityDetect}"> selected</c:if>></option>	
					<c:forEach var="detectBean" items="${vvDetectBeanCollection}" varStatus="status2">
						<c:if test="${detectBean.flashPointOnly == 'N' || (detectBean.flashPointOnly == 'Y' && isFlashDetect == 'Y')}">
							<option value="${detectBean.detect}" ${detectBean.detect eq item.specificGravityDetect?'selected':''}>${detectBean.description}</option>
						</c:if>
					</c:forEach>
				</select>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.specificGravityLower" class="optionTitle">${_value}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.specificGravityLower" id="${compSection}.specificGravityLower" size="20" maxlength="20" value="${item.specificGravityLower}">
		</div>			
		<div class="msdsIndexedField">
			<label for="${compSection}.specificGravityUpper" class="optionTitle">${_upper}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.specificGravityUpper" id="${compSection}.specificGravityUpper" size="20" maxlength="20" value="${item.specificGravityUpper}">
		</div>
		<div class="msdsIndexedField">
			<fieldset style="border:none">
				<legend class="optionTitle">${_basis}:</legend>
					<input name="${compSection}.specificGravityBasis" id="${compSection}.specificGravityBasisAir" type="radio" class="radioBtns" value="A" <c:if test="${item.specificGravityBasis eq 'A'}">checked</c:if>>
				<label for="${compSection}.specificGravityBasisAir" class="optionTitleBold">${_air}</label>
					<input name="${compSection}.specificGravityBasis" id="${compSection}.specificGravityBasisWater" type="radio" class="radioBtns" value="W" <c:if test="${item.specificGravityBasis eq 'W'}">checked</c:if>>
				<label for="${compSection}.specificGravityBasisWater" class="optionTitleBold">${_water}</label>
			</fieldset>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.specificGravitySource" class="optionTitle">${_source}:</label><br/>
				<select name="${compSection}.specificGravitySource" id="${compSection}.specificGravitySource" class="selectBox">    
					<option value=""></option>
					<c:set var="sgSrc" value="${empty item.specificGravitySource?'MSDS':item.specificGravitySource}"/>
					<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status2">
					<option value="${sourceBean.dataSource}" ${sourceBean.dataSource eq sgSrc?'selected':''}>${sourceBean.dataSource}</option>
					</c:forEach>
				</select>
		</div>
		<div id="${compSection}.specificGravityQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.specificGravityQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.specificGravityQcErrorType" id="${compSection}.specificGravityQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="sgErrorType" value="${empty item.qcData.specificGravityQcErrorType?'':item.qcData.specificGravityQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq sgErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
	</div>
</fieldset>
</div>