<div id="${compSection}.vaporpressurefields">
<fieldset>
	<legend class="msdsSectionHeader">${_vaporpressure}</legend>
	<div style="padding:3px;">
		<div style="padding:2px;margin-bottom:5px;">
			<label for="${compSection}.vaporPressureDetect" class="optionTitle">${_detect}:</label><br/>
				 <select name="${compSection}.vaporPressureDetect" id="${compSection}.vaporPressureDetect" class="selectBox" onchange="msdsIndex.blockDisplays('vaporPressure',value,${prefix eq 'co'});">    			
					<option value=""></option>
					<c:forEach var="detectBean" items="${vvDetectBeanCollection}" varStatus="status2">
						<c:if test="${detectBean.flashPointOnly == 'N' || (detectBean.flashPointOnly == 'Y' && isFlashDetect == 'Y')}">
							<option value="${detectBean.detect}" ${detectBean.detect eq item.vaporPressureDetect?'selected':''}>${detectBean.description}</option>
						</c:if>
					</c:forEach>
				</select>
		</div>
		<div style="padding:2px;margin-bottom:5px;">
			<label for="${compSection}.vaporPressure" class="optionTitle">${_value}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.vaporPressure" id="${compSection}.vaporPressure" size="20" maxlength="20" value="${item.vaporPressure}">
		</div>
		<div style="padding:2px;margin-bottom:5px;">
			<label for="${compSection}.vaporPressureUpper" class="optionTitle">${_upper}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.vaporPressureUpper" id="${compSection}.vaporPressureUpper" size="20" maxlength="20" value="${item.vaporPressureUpper}">
		</div>
		<div style="padding:2px;margin-bottom:5px;">
			<label for="${compSection}.vaporPressureUnit" class="optionTitle">${_units}:</label><br/>
				<select name="${compSection}.vaporPressureUnit" id="${compSection}.vaporPressureUnit" class="selectBox">    			
					<option value=""></option>
					<option value="MPa" ${"MPa" eq item.vaporPressureUnit?'selected':''}>MPa</option>
	    			<option value="atm" ${"atm" eq item.vaporPressureUnit?'selected':''}>atm</option>
	    			<option value="hPa" ${"hPa" eq item.vaporPressureUnit?'selected':''}>hPa</option>
	    			<option value="in Hg" ${"in Hg" eq item.vaporPressureUnit?'selected':''}>in Hg</option>
	    			<option value="kPa" ${"kPa" eq item.vaporPressureUnit?'selected':''}>kPa</option>
	    			<option value="mbar" ${"mbar" eq item.vaporPressureUnit?'selected':''}>mbar</option>
	    			<option value="mm Hg" ${"mm Hg" eq item.vaporPressureUnit?'selected':''}>mm Hg</option>
	    			<option value="psia" ${"psia" eq item.vaporPressureUnit?'selected':''}>psia</option>
	    			<option value="psig" ${"psig" eq item.vaporPressureUnit?'selected':''}>psig</option>
	    			<option value="torr" ${"torr" eq item.vaporPressureUnit?'selected':''}>torr</option>
				</select>
		</div>
		<div style="padding:2px;margin-bottom:5px;">
			<label for="${compSection}.vaporPressureTemp" class="optionTitle">${_temperature}:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.vaporPressureTemp" id="${compSection}.vaporPressureTemp" size="5" maxlength="10" value="${item.vaporPressureTemp}">
				<select name="${compSection}.vaporPressureTempUnit" id="${compSection}.vaporPressureTempUnit" class="selectBox">    			
					<option value=""></option>
					<option value="C" ${"C" eq item.vaporPressureTempUnit?'selected':''}>&deg;${_cforcelsius}</option>
					<option value="F" ${"F" eq item.vaporPressureTempUnit?'selected':''}>&deg;${_fforfahrenheit}</option>
				</select>
		</div>
		<div style="padding:2px;margin-bottom:5px;">
			<label for="${compSection}.vaporPressureSource" class="optionTitle">${_source}:</label><br/>
				<select name="${compSection}.vaporPressureSource" id="${compSection}.vaporPressureSource" class="selectBox">    
					<option value=""></option>
					<c:set var="vpSrc" value="${empty item.vaporPressureSource?'MSDS':item.vaporPressureSource}"/>
					<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status2">
					<option value="${sourceBean.dataSource}" ${sourceBean.dataSource eq vpSrc?'selected':''}>${sourceBean.dataSource}</option>
					</c:forEach>
				</select>
		</div>
		<div id="${compSection}.vaporPressureQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.vaporPressureQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.vaporPressureQcErrorType" id="${compSection}.vaporPressureQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="vpErrorType" value="${empty item.qcData.vaporPressureQcErrorType?'':item.qcData.vaporPressureQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq vpErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
	</div>
</fieldset>
</div>