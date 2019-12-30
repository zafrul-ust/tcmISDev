<div id="${compSection}.phfields">
<fieldset>
	<legend class="msdsSectionHeader">${_ph}</legend>
	<div style="padding:3px;">
		<div class="msdsIndexedField">
			<label for="${compSection}.phDetect" class="optionTitle" style="white-space:nowrap">${_detect}:&nbsp;</label><br/>
				<select name="${compSection}.phDetect" id="${compSection}.phDetect" class="selectBox" onchange="msdsIndex.blockDisplays('ph',value,${prefix eq 'co'})">    
					<option value=""></option>			
					<c:forEach var="detectBean" items="${vvDetectBeanCollection}" varStatus="status2">
						<c:if test="${detectBean.flashPointOnly == 'N' || (detectBean.flashPointOnly == 'Y' && isFlashDetect == 'Y')}">
							<option value="${detectBean.detect}" ${detectBean.detect eq item.phDetect?'selected':''}>${detectBean.description}</option>
						</c:if>
					</c:forEach>
				</select>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.ph" class="optionTitle" style="white-space:nowrap">${_value}:&nbsp;</label><br/>
				<input class="inputBox" type="text" name="${compSection}.ph" id="${compSection}.ph" size="20" maxlength="20" value="${item.ph}">
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.phUpper" class="optionTitle" style="white-space:nowrap">${_upper}:&nbsp;</label><br/>
				<input class="inputBox" type="text" name="${compSection}.phUpper" id="${compSection}.phUpper" size="20" maxlength="20" value="${item.phUpper}">
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.phDetail" class="optionTitle" style="white-space:nowrap">${_detail}:&nbsp;</label><br/>
				<input class="inputBox" type="text" name="${compSection}.phDetail" id="${compSection}.phDetail" size="20" onKeyUp="msdsIndex.limitText(this,50);" value="${item.phDetail}">
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.phSource" class="optionTitle" style="white-space:nowrap">${_source}:&nbsp;</label><br/>
				<select name="${compSection}.phSource" id="${compSection}.phSource" class="selectBox">    
					<option value=""></option>
					<c:set var="phSrc" value="${empty item.phSource?'MSDS':item.phSource}"/>
					<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status2">		
					<option value="${sourceBean.dataSource}" ${sourceBean.dataSource eq phSrc?'selected':''}>${sourceBean.dataSource}</option>
					</c:forEach>
				</select>
		</div>
		<div id="${compSection}.phQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.phQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.phQcErrorType" id="${compSection}.phQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="phErrorType" value="${empty item.qcData.phQcErrorType?'':item.qcData.phQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq phErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
	</div>
</fieldset>
</div>