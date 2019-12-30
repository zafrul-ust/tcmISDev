<div id="${compSection}.physicalstatefields">
<fieldset>
	<legend class="msdsSectionHeader">${_physicalstate}</legend>
	<div style="padding:3px;">
		<div class="msdsIndexedField">
			<label for="${compSection}.physicalState" class="optionTitle" style="white-space:nowrap">${_physicalstate}:</label><br/>
				<select name="${compSection}.physicalState" id="${compSection}.physicalState" class="selectBox" onchange="msdsIndex.setSpecGrav(value,${prefix eq 'co'});">    			
					<option value=""></option>
					<option value="gas" ${"gas" eq item.physicalState?'selected':''}>${_gaslower}</option>
					<option value="liquid" ${"liquid" eq item.physicalState?'selected':''}>${_liquid}</option>
					<option value="liquid/aerosol" ${"liquid/aerosol" eq item.physicalState?'selected':''}>${_aerosol}</option>
					<option value="cryogenic liquid" ${"cryogenic liquid" eq item.physicalState?'selected':''}>${_cryogenicliquid}</option>
					<option value="semi-solid" ${"semi-solid" eq item.physicalState?'selected':''}>${_semisolid}</option>
					<option value="solid" ${"solid" eq item.physicalState?'selected':''}>${_solid}</option>
				</select>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.physicalStateSource" class="optionTitle" style="white-space:nowrap">${_source}:</label><br/>
				<select name="${compSection}.physicalStateSource" id="${compSection}.physicalStateSource" class="selectBox">    
					<option value=""></option>
					<c:set var="psSrc" value="${empty item.physicalStateSource?'MSDS':item.physicalStateSource}"/>
					<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status2">
					<option value="${sourceBean.dataSource}" ${sourceBean.dataSource eq psSrc?'selected':''}>${sourceBean.dataSource}</option>
					</c:forEach>
				</select>
		</div>
		<div id="${compSection}.physicalStateQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.physicalStateQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.physicalStateQcErrorType" id="${compSection}.physicalStateQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="psErrorType" value="${empty item.qcData.physicalStateQcErrorType?'':item.qcData.physicalStateQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq psErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
	</div>
</fieldset>
</div>