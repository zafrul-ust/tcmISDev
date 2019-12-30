<script type="text/javascript">
<!--
var territoryList = msdsIndex.territoryList || [];
<c:forEach var="countryBean" items="${vvCountryBeanCollection}" varStatus="cStatus">
	territoryList["${countryBean.countryAbbrev}"] = [
		<c:forEach var="stateBean" items="${countryBean.stateCollection}" varStatus="sStatus">
			<c:if test="${sStatus.index > 0}">,</c:if>
			{ stateAbbrev: '<tcmis:jsReplace value="${sStatus.current.stateAbbrev}"/>',
				state: '<tcmis:jsReplace value="${sStatus.current.state}"/>'
			}
		</c:forEach>
	];
</c:forEach>
//-->
</script>
<div id="msds[${index}].ghsfields">
<fieldset>
	<legend class="msdsSectionHeader"><fmt:message key="label.ghs"/></legend>
	<div style="padding:3px;">
	<div class="msdsIndexedField">
		<label for="msds[${index}].labelCompanyName" class="optionTitle"><fmt:message key="label.companyname"/>:</label><br/>
			<input class="inputBox" style="width:96%;" type="text" name="msds[${index}].labelCompanyName" id="msds[${index}].labelCompanyName" value="${msds.labelCompanyName}"/>
	</div>
	<div class="msdsIndexedField">
		<label for="msds[${index}].labelPhone" class="optionTitle"><fmt:message key="label.emergencynumber"/>:</label><br/>
			<input class="inputBox" type="text" name="msds[${index}].labelPhone" id="msds[${index}].labelPhone" value="${msds.labelPhone}"/>
	</div>
	<div class="msdsIndexedField">
		<label for="msds[${index}].labelAddressLine1" class="optionTitle"><fmt:message key="label.address"/>:</label><br/>
		<input class="inputBox" style="width:96%;" type="text" name="msds[${index}].labelAddressLine1" id="msds[${index}].labelAddressLine1" value="${msds.labelAddressLine1}" maxlength="50"/>
	</div>
	<div class="msdsIndexedField">
		<input class="inputBox" style="width:96%;" type="text" name="msds[${index}].labelAddressLine2" id="msds[${index}].labelAddressLine2" value="${msds.labelAddressLine2}" maxlength="50"/>
	</div>
	<div class="msdsIndexedField">
		<input class="inputBox" style="width:96%;" type="text" name="msds[${index}].labelAddressLine3" id="msds[${index}].labelAddressLine3" value="${msds.labelAddressLine3}" maxlength="50"/>
	</div>
	<div class="msdsIndexedField">
		<input class="inputBox" style="width:96%;" type="text" name="msds[${index}].labelAddressLine4" id="msds[${index}].labelAddressLine4" value="${msds.labelAddressLine4}" maxlength="50"/>
	</div>
	<div class="msdsIndexedField">
		<label for="msds[${index}].labelCountryAbbrev" class="optionTitle"><fmt:message key="label.country"/>:</label><br/>
		<select name="msds[${index}].labelCountryAbbrev" id="msds[${index}].labelCountryAbbrev" class="selectBox" style="width:96%;" onChange="msdsIndex.countryChanged()">
			<c:set var="selectedCtry" value="${msds.labelCountryAbbrev}" />
			<c:if test="${selectedCtry == null}">
				<c:set var="selectedCtry" value="USA" />
			</c:if>
			<c:forEach var="countryBean" items="${vvCountryBeanCollection}" varStatus="cStatus">
				<option value="${countryBean.countryAbbrev}" <c:if test="${selectedCtry == countryBean.countryAbbrev}">selected</c:if>>${countryBean.country}</option>
			</c:forEach>
		</select>
	</div>
	<div class="msdsIndexedField">
		<label for="msds[${index}].labelCity" class="optionTitle"><fmt:message key="label.city"/>:</label><br/>
		<input class="inputBox" style="width:96%;" type="text" name="msds[${index}].labelCity" id="msds[${index}].labelCity" value="${msds.labelCity}"/>
	</div>
	<div class="msdsIndexedField">
		<label for="msds[${index}].labelStateAbbrev" class="optionTitle"><fmt:message key="label.state"/>:</label><br/>
		<select name="msds[${index}].labelStateAbbrev" id="msds[${index}].labelStateAbbrev" class="selectBox"></select>
		<input type="hidden" name="msds[${index}].selectedState" id="msds[${index}].selectedState" value="${msds.labelStateAbbrev}"/>
	</div>
	<div class="msdsIndexedField">
		<label for="msds[${index}].labelZip" class="optionTitle"><fmt:message key="label.zip"/>:</label><br/>
		<input class="inputBox" type="text" name="msds[${index}].labelZip" id="msds[${index}].labelZip" value="${msds.labelZip}" size="10"/>
	</div>
	<div id="${compSection}.labelAddressQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
		<label for="${compSection}.labelAddressQcErrorType" class="optionTitle">${_errorType}:</label><br/>
			<select name="${compSection}.labelAddressQcErrorType" id="${compSection}.labelAddressQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
				<option value="">${_noerror}</option>
				<c:set var="addrErrorType" value="${empty item.qcData.labelAddressQcErrorType?'':item.qcData.labelAddressQcErrorType}"/>
				<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
				<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq addrErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
				</c:forEach>
			</select>
	</div>
	<div class="msdsIndexedField">
			<input class="radioBtns" type="checkbox" name="msds[${index}].ghsHazard" id="msds[${index}].ghsHazard" value="Y" <c:out value="${msds.ghsHazard ? '':'checked'}"/>/>
		<label for="msds[${index}].ghsHazard" class="optionTitle"><fmt:message key="label.nonhazardous"/></label>
	</div>
	<div class="msdsIndexedField">
		<label for="msds[${index}].signalWord" class="optionTitle"><fmt:message key="label.signalword"/>:</label><br/>
		<select name="msds[${index}].signalWord" id="msds[${index}].signalWord" class="selectBox" style="width:120px">
			<option value="">NONE</option>
			<c:forEach var="signalWord" items="${signalWordColl}" varStatus="swStatus">
				<option value="${signalWord}" <c:if test="${msds.signalWord eq signalWord}">selected</c:if>>${signalWord}</option>
			</c:forEach>
		</select>
	</div>
	<div id="${compSection}.signalWordQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
		<label for="${compSection}.signalWordQcErrorType" class="optionTitle">${_errorType}:</label><br/>
			<select name="${compSection}.signalWordQcErrorType" id="${compSection}.signalWordQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
				<option value="">${_noerror}</option>
				<c:set var="signalErrorType" value="${empty item.qcData.signalWordQcErrorType?'':item.qcData.signalWordQcErrorType}"/>
				<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
				<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq signalErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
				</c:forEach>
			</select>
	</div>
	</div>
</fieldset>
</div>