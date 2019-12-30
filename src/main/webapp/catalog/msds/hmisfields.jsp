<div id="${compSection}.hmisfields">
<fieldset>
	<legend class="msdsSectionHeader">${_hmis}</legend>
	<div style="padding:3px;">
		<div class="msdsIndexedField">
			<label for="${compSection}.hmisHealth" class="optionTitle" style="white-space:nowrap">${_health}:&nbsp;</label><br/>
				<input class="inputBox" type="text" name="${compSection}.hmisHealth" id="${compSection}.hmisHealth" size="20" maxlength="30" value="${item.hmisHealth}" >
		</div>
		<div class="msdsIndexedField">
			<fieldset style="border:none">
				<legend class="optionTitle" style="white-space:nowrap">${_chronic}:</legend>
					<input name="${compSection}.hmisChronic" id="${compSection}.hmisChronicY" type="radio" class="radioBtns" value="Y"<c:if test="${item.hmisChronic eq 'Y'}">checked</c:if>>
				<label for="${compSection}.hmisChronicY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.hmisChronic" id="${compSection}.hmisChronicN" type="radio" class="radioBtns" value="N"<c:if test="${item.hmisChronic eq 'N'}">checked</c:if>>
				<label for="${compSection}.hmisChronicN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.hmisChronic" id="${compSection}.hmisChronicX" type="radio" class="radioBtns" value="X"<c:if test="${item.hmisChronic eq 'X'}">checked</c:if>>
				<label for="${compSection}.hmisChronicX" class="optionTitleBold">${_na}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('hmisChronic',${prefix eq 'co'})" type="button"/>
			</fieldset>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.hmisFlammability" class="optionTitle" style="white-space:nowrap">${_flammability}:&nbsp;</label><br/>
				<input class="inputBox" type="text" name="${compSection}.hmisFlammability" id="${compSection}.hmisFlammability" size="20" maxlength="30" value="${item.hmisFlammability}">
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.hmisReactivity" class="optionTitle" style="white-space:nowrap">${_reactivity}:&nbsp;</label><br/>
				<input class="inputBox" type="text" name="${compSection}.hmisReactivity" id="${compSection}.hmisReactivity" size="20" maxlength="30" value="${item.hmisReactivity}">
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.personalProtection" class="optionTitle" style="white-space:nowrap">${_personalprotection}:&nbsp;</label><br/>
				 <select name="${compSection}.personalProtection" id="${compSection}.personalProtection" class="selectBox">    			
					<option value=""></option>
					<option  value="A" ${"A" eq item.personalProtection?'selected':''}>A</option>
					<option  value="B" ${"B" eq item.personalProtection?'selected':''}>B</option>
					<option  value="C" ${"C" eq item.personalProtection?'selected':''}>C</option>
					<option  value="D" ${"D" eq item.personalProtection?'selected':''}>D</option>
					<option  value="E" ${"E" eq item.personalProtection?'selected':''}>E</option>
					<option  value="F" ${"F" eq item.personalProtection?'selected':''}>F</option>
					<option  value="G" ${"G" eq item.personalProtection?'selected':''}>G</option>
					<option  value="H" ${"H" eq item.personalProtection?'selected':''}>H</option>
					<option  value="I" ${"I" eq item.personalProtection?'selected':''}>I</option>
					<option  value="J" ${"J" eq item.personalProtection?'selected':''}>J</option>
					<option  value="K" ${"K" eq item.personalProtection?'selected':''}>K</option>
					<option  value="X" ${"X" eq item.personalProtection?'selected':''}>X</option>
				</select>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.hmisSource" class="optionTitle" style="white-space:nowrap">${_source}:&nbsp;</label><br/>
				<select name="${compSection}.hmisSource" id="${compSection}.hmisSource" class="selectBox">    
					<option value=""></option>
					<c:set var="hmisSrc" value="${empty item.hmisSource?'MSDS':item.hmisSource}"/>
					<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status2">
					<option value="${sourceBean.dataSource}" ${sourceBean.dataSource eq hmisSrc?'selected':''}>${sourceBean.dataSource}</option>
					</c:forEach>
				</select>
		</div>
		<div id="${compSection}.hmisQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.hmisQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.hmisQcErrorType" id="${compSection}.hmisQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="hmisErrorType" value="${empty item.qcData.hmisQcErrorType?'':item.qcData.hmisQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq hmisErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
	</div>
</fieldset>
</div>