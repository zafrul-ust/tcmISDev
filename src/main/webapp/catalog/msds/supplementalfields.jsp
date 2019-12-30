<div id="${compSection}.supplementalfields">
<fieldset>
	<legend class="msdsSectionHeader">${_supplemental}</legend>
	<div style="padding:3px;">
		<c:if test="${prefix eq 'msds'}">
		<div class="msdsIndexedField">
			<label for="${compSection}.vaporDensity" class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.vapordensity"/>:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.vaporDensity" id="${compSection}.vaporDensity" value="<c:out value="${msds.vaporDensity}" />" onKeyUp="msdsIndex.limitText(this,10);" size="20"/>
		</div>
		<div id="${compSection}.vaporDensityQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.vaporDensityQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.vaporDensityQcErrorType" id="${compSection}.vaporDensityQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="vdErrorType" value="${empty item.qcData.vaporDensityQcErrorType?'':item.qcData.vaporDensityQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq vdErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
		<div class="msdsIndexedField">
			<label for="${compSection}.evaporationRate" class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.evaporationrate"/>:</label><br/>
				<input class="inputBox" type="text" name="${compSection}.evaporationRate" id="${compSection}.evaporationRate" value="<c:out value="${msds.evaporationRate}" />" onKeyUp="msdsIndex.limitText(this,20);" size="20"/>
		</div>
		<div id="${compSection}.evaporationRateQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.evaporationRateQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.evaporationRateQcErrorType" id="${compSection}.evaporationRateQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="erErrorType" value="${empty item.qcData.evaporationRateQcErrorType?'':item.qcData.evaporationRateQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq erErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
		<div class="msdsIndexedField">
			<fieldset style="border:none">
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.sara311312acute"/>:</legend>
					<input name="${compSection}.sara311312Acute" id="${compSection}.sara311312AcuteY" type="radio" class="radioBtns" value="Y"<c:if test="${msds.sara311312Acute eq 'Y'}">checked</c:if>>
				<label for="${compSection}.sara311312AcuteY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.sara311312Acute" id="${compSection}.sara311312AcuteN" type="radio" class="radioBtns" value="N"<c:if test="${msds.sara311312Acute eq 'N'}">checked</c:if>>
				<label for="${compSection}.sara311312AcuteN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.sara311312Acute" id="${compSection}.sara311312AcuteU" type="radio" class="radioBtns" value="U"<c:if test="${msds.sara311312Acute eq 'U'}">checked</c:if>>
				<label for="${compSection}.sara311312AcuteU" class="optionTitleBold">${_na}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('sara311312Acute')" type="button"/>
			</fieldset>
		</div>
		<div class="msdsIndexedField">
			<fieldset style="border:none">
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.sara311312chronic"/>:</legend>
					<input name="${compSection}.sara311312Chronic" id="${compSection}.sara311312ChronicY" type="radio" class="radioBtns" value="Y"<c:if test="${msds.sara311312Chronic eq 'Y'}">checked</c:if>>
				<label for="${compSection}.sara311312ChronicY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.sara311312Chronic" id="${compSection}.sara311312ChronicN" type="radio" class="radioBtns" value="N"<c:if test="${msds.sara311312Chronic eq 'N'}">checked</c:if>>
				<label for="${compSection}.sara311312ChronicN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.sara311312Chronic" id="${compSection}.sara311312ChronicU" type="radio" class="radioBtns" value="U"<c:if test="${msds.sara311312Chronic eq 'U'}">checked</c:if>>
				<label for="${compSection}.sara311312ChronicU" class="optionTitleBold">${_na}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('sara311312Chronic')" type="button"/>
			</fieldset>
		</div>
		<div class="msdsIndexedField">
			<fieldset style="border:none">
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.sara311312fire"/>:</legend>
					<input name="${compSection}.sara311312Fire" id="${compSection}.sara311312FireY" type="radio" class="radioBtns" value="Y"<c:if test="${msds.sara311312Fire eq 'Y'}">checked</c:if>>
				<label for="${compSection}.sara311312FireY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.sara311312Fire" id="${compSection}.sara311312FireN" type="radio" class="radioBtns" value="N"<c:if test="${msds.sara311312Fire eq 'N'}">checked</c:if>>
				<label for="${compSection}.sara311312FireN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.sara311312Fire" id="${compSection}.sara311312FireU" type="radio" class="radioBtns" value="U"<c:if test="${msds.sara311312Fire eq 'U'}">checked</c:if>>
				<label for="${compSection}.sara311312FireU" class="optionTitleBold">${_na}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('sara311312Fire')" type="button"/>
			</fieldset>
		</div>
		<div class="msdsIndexedField">
			<fieldset style="border:none">
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.sara311312pressure"/>:</legend>
					<input name="${compSection}.sara311312Pressure" id="${compSection}.sara311312PressureY" type="radio" class="radioBtns" value="Y"<c:if test="${msds.sara311312Pressure eq 'Y'}">checked</c:if>>
				<label for="${compSection}.sara311312PressureY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.sara311312Pressure" id="${compSection}.sara311312PressureN" type="radio" class="radioBtns" value="N"<c:if test="${msds.sara311312Pressure eq 'N'}">checked</c:if>>
				<label for="${compSection}.sara311312PressureN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.sara311312Pressure" id="${compSection}.sara311312PressureU" type="radio" class="radioBtns" value="U"<c:if test="${msds.sara311312Pressure eq 'U'}">checked</c:if>>
				<label for="${compSection}.sara311312PressureU" class="optionTitleBold">${_na}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('sara311312Pressure')" type="button"/>
			</fieldset>
		</div>
		<div class="msdsIndexedField">
			<fieldset style="border:none">
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.sara311312reactivity"/>:</legend>
					<input name="${compSection}.sara311312Reactivity" id="${compSection}.sara311312ReactivityY" type="radio" class="radioBtns" value="Y"<c:if test="${msds.sara311312Reactivity eq 'Y'}">checked</c:if>>
				<label for="${compSection}.sara311312ReactivityY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.sara311312Reactivity" id="${compSection}.sara311312ReactivityN" type="radio" class="radioBtns" value="N"<c:if test="${msds.sara311312Reactivity eq 'N'}">checked</c:if>>
				<label for="${compSection}.sara311312ReactivityN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.sara311312Reactivity" id="${compSection}.sara311312ReactivityU" type="radio" class="radioBtns" value="U"<c:if test="${msds.sara311312Reactivity eq 'U'}">checked</c:if>>
				<label for="${compSection}.sara311312ReactivityU" class="optionTitleBold">${_na}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('sara311312Reactivity')" type="button"/>
			</fieldset>
		</div>
		<div id="${compSection}.saraQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.saraQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.saraQcErrorType" id="${compSection}.saraQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="saraErrorType" value="${empty item.qcData.saraQcErrorType?'':item.qcData.saraQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq saraErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
		</c:if>
		<c:if test="${prefix eq 'co'}">
			<%--<div class="column"> <-- 1 -->
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.alloy"/>:</legend>
					<input name="${compSection}.alloy" id="${compSection}.alloyY" type="radio" class="radioBtns" value="Y"<c:if test="${item.alloy eq 'Y'}">checked</c:if>>
				<label for="${compSection}.alloyY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.alloy" id="${compSection}.alloyN" type="radio" class="radioBtns" value="N"<c:if test="${item.alloy eq 'N'}">checked</c:if>>
				<label for="${compSection}.alloyN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.alloy" id="${compSection}.alloyNA" type="radio" class="radioBtns" value="NA"<c:if test="${item.alloy eq 'NA'}">checked</c:if>>
				<label for="${compSection}.alloyNA" class="optionTitleBold">${_na}</label>
				<label class="optionTitleBold wrapless"><input name="${compSection}.alloy" id="${compSection}.alloyNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.alloy eq 'NL'}">checked</c:if>>${_notlisted}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('alloy',true);" type="button" />
				<div id="${compSection}.alloyQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
					<label for="${compSection}.alloyQcErrorType" class="optionTitle">${_errorType}:</label><br/>
						<select name="${compSection}.alloyQcErrorType" id="${compSection}.alloyQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
							<option value="">${_noerror}</option>
							<c:set var="alloyErrorType" value="${empty item.qcData.alloyQcErrorType?'':item.qcData.alloyQcErrorType}"/>
							<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
							<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq alloyErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
							</c:forEach>
						</select>
				</div>
			</fieldset>
			</div>--%>
			<%--<div class="column"> <-- 2 -->
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.carcinogen"/>:</legend>
					<input name="${compSection}.carcinogen" id="${compSection}.carcinogenY" type="radio" class="radioBtns" value="Y"<c:if test="${item.carcinogen eq 'Y'}">checked</c:if>>
				<label for="${compSection}.carcinogenY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.carcinogen" id="${compSection}.carcinogenN" type="radio" class="radioBtns" value="N"<c:if test="${item.carcinogen eq 'N'}">checked</c:if>>
				<label for="${compSection}.carcinogenN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.carcinogen" id="${compSection}.carcinogenNA" type="radio" class="radioBtns" value="NA"<c:if test="${item.carcinogen eq 'NA'}">checked</c:if>>
				<label for="${compSection}.carcinogenNA" class="optionTitleBold">${_na}</label>
				<label class="optionTitleBold wrapless"><input name="${compSection}.carcinogen" id="${compSection}.carcinogenNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.carcinogen eq 'NL'}">checked</c:if>>${_notlisted}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('carcinogen',true);" type="button"/>
				<div id="${compSection}.carcinogenQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
					<label for="${compSection}.carcinogenQcErrorType" class="optionTitle">${_errorType}:</label><br/>
						<select name="${compSection}.carcinogenQcErrorType" id="${compSection}.carcinogenQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
							<option value="">${_noerror}</option>
							<c:set var="carcinogenErrorType" value="${empty item.qcData.carcinogenQcErrorType?'':item.qcData.carcinogenQcErrorType}"/>
							<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
							<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq carcinogenErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
							</c:forEach>
						</select>
				</div>
			</fieldset>
			</div>--%>
			<%--<div class="column"> <-- 3 -->
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;">${_chronic}:</legend>
					<input name="${compSection}.chronic" id="${compSection}.chronicY" type="radio" class="radioBtns" value="Y"<c:if test="${item.chronic eq 'Y'}">checked</c:if>>
				<label for="${compSection}.chronicY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.chronic" id="${compSection}.chronicN" type="radio" class="radioBtns" value="N"<c:if test="${item.chronic eq 'N'}">checked</c:if>>
				<label for="${compSection}.chronicN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.chronic" id="${compSection}.chronicNA" type="radio" class="radioBtns" value="NA"<c:if test="${item.chronic eq 'NA'}">checked</c:if>>
				<label for="${compSection}.chronicNA" class="optionTitleBold">${_na}</label>
				<label class="optionTitleBold wrapless"><input name="${compSection}.chronic" id="${compSection}.chronicNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.chronic eq 'NL'}">checked</c:if>>${_notlisted}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('chronic',true);" type="button"/>
				<div id="${compSection}.chronicQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
					<label for="${compSection}.chronicQcErrorType" class="optionTitle">${_errorType}:</label><br/>
						<select name="${compSection}.chronicQcErrorType" id="${compSection}.chronicQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
							<option value="">${_noerror}</option>
							<c:set var="chronicErrorType" value="${empty item.qcData.chronicQcErrorType?'':item.qcData.chronicQcErrorType}"/>
							<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
							<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq chronicErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
							</c:forEach>
						</select>
				</div>
			</fieldset>
			</div>--%>
			<div class="column"> <%-- 4 --%>
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.corrosive"/>:</legend>
					<input name="${compSection}.corrosive" id="${compSection}.corrosiveY" type="radio" class="radioBtns" value="Y"<c:if test="${item.corrosive eq 'Y'}">checked</c:if>>
				<label for="${compSection}.corrosiveY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.corrosive" id="${compSection}.corrosiveN" type="radio" class="radioBtns" value="N"<c:if test="${item.corrosive eq 'N'}">checked</c:if>>
				<label for="${compSection}.corrosiveN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.corrosive" id="${compSection}.corrosiveNA" type="radio" class="radioBtns" value="NA"<c:if test="${item.corrosive eq 'NA'}">checked</c:if>>
				<label for="${compSection}.corrosiveNA" class="optionTitleBold">${_na}</label>
				<label class="optionTitleBold wrapless"><input name="${compSection}.corrosive" id="${compSection}.corrosiveNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.corrosive eq 'NL'}">checked</c:if>>${_notlisted}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('corrosive',true);" type="button"/>
				<div id="${compSection}.corrosiveQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
					<label for="${compSection}.corrosiveQcErrorType" class="optionTitle">${_errorType}:</label><br/>
						<select name="${compSection}.corrosiveQcErrorType" id="${compSection}.corrosiveQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
							<option value="">${_noerror}</option>
							<c:set var="corrosiveErrorType" value="${empty item.qcData.corrosiveQcErrorType?'':item.qcData.corrosiveQcErrorType}"/>
							<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
							<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq corrosiveErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
							</c:forEach>
						</select>
				</div>
			</fieldset>
			</div>
			<div class="column"> <%-- 5 --%>
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.detonable"/>:</legend>
					<input name="${compSection}.detonable" id="${compSection}.detonableY" type="radio" class="radioBtns" value="Y"<c:if test="${item.detonable eq 'Y'}">checked</c:if>>
				<label for="${compSection}.detonableY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.detonable" id="${compSection}.detonableN" type="radio" class="radioBtns" value="N"<c:if test="${item.detonable eq 'N'}">checked</c:if>>
				<label for="${compSection}.detonableN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.detonable" id="${compSection}.detonableNA" type="radio" class="radioBtns" disabled value="X"<c:if test="${item.detonable eq 'X'}">checked</c:if>>
				<label for="${compSection}.detonableNA" class="optionTitleBold">${_na}</label>
				<label class="optionTitleBold wrapless"><input name="${compSection}.detonable" id="${compSection}.detonableNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.detonable eq 'NL'}">checked</c:if>>${_notlisted}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('detonable',true);" type="button"/>
				<div id="${compSection}.detonableQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
					<label for="${compSection}.detonableQcErrorType" class="optionTitle">${_errorType}:</label><br/>
						<select name="${compSection}.detonableQcErrorType" id="${compSection}.detonableQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
							<option value="">${_noerror}</option>
							<c:set var="detonableErrorType" value="${empty item.qcData.detonableQcErrorType?'':item.qcData.detonableQcErrorType}"/>
							<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
							<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq detonableErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
							</c:forEach>
						</select>
				</div>
			</fieldset>
			</div>
			<div class="column"> <%-- 6 --%>
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.fireconditionstoavoid"/>:</legend>
					<input name="${compSection}.fireConditionsToAvoid" id="${compSection}.fireConditionsToAvoidY" type="radio" class="radioBtns" value="Y"<c:if test="${item.fireConditionsToAvoid eq 'Y'}">checked</c:if>>
				<label for="${compSection}.fireConditionsToAvoidY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.fireConditionsToAvoid" id="${compSection}.fireConditionsToAvoidN" type="radio" class="radioBtns" value="N"<c:if test="${item.fireConditionsToAvoid eq 'N'}">checked</c:if>>
				<label for="${compSection}.fireConditionsToAvoidN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.fireConditionsToAvoid" id="${compSection}.fireConditionsToAvoidNA" type="radio" class="radioBtns" value="NA"<c:if test="${item.fireConditionsToAvoid eq 'NA'}">checked</c:if>>
				<label for="${compSection}.fireConditionsToAvoidNA" class="optionTitleBold">${_na}</label>
				<label class="optionTitleBold wrapless"><input name="${compSection}.fireConditionsToAvoid" id="${compSection}.fireConditionsToAvoidNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.fireConditionsToAvoid eq 'NL'}">checked</c:if>>${_notlisted}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('fireConditionsToAvoid',true);" type="button"/>
				<div id="${compSection}.fireConditionsToAvoidQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
					<label for="${compSection}.fireConditionsToAvoidQcErrorType" class="optionTitle">${_errorType}:</label><br/>
						<select name="${compSection}.fireConditionsToAvoidQcErrorType" id="${compSection}.fireConditionsToAvoidQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
							<option value="">${_noerror}</option>
							<c:set var="fireErrorType" value="${empty item.qcData.fireConditionsToAvoidQcErrorType?'':item.qcData.fireConditionsToAvoidQcErrorType}"/>
							<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
							<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq fireErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
							</c:forEach>
						</select>
				</div>
			</fieldset>
			</div>
			<div class="column"> <%-- 7 --%>
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.healtheffects"/>:</legend>
					<input name="${compSection}.healthEffects" id="${compSection}.healthEffectsY" type="radio" class="radioBtns" value="Y"<c:if test="${item.healthEffects eq 'Y'}">checked</c:if>>
				<label for="${compSection}.healthEffectsY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.healthEffects" id="${compSection}.healthEffectsN" type="radio" class="radioBtns" value="N"<c:if test="${item.healthEffects eq 'N'}">checked</c:if>>
				<label for="${compSection}.healthEffectsN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.healthEffects" id="${compSection}.healthEffectsNA" type="radio" class="radioBtns" value="NA"<c:if test="${item.healthEffects eq 'NA'}">checked</c:if>>
				<label for="${compSection}.healthEffectsNA" class="optionTitleBold">${_na}</label>
				<label class="optionTitleBold wrapless"><input name="${compSection}.healthEffects" id="${compSection}.healthEffectsNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.healthEffects eq 'NL'}">checked</c:if>>${_notlisted}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('healthEffects',true);" type="button"/>
				<div id="${compSection}.healthEffectsQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
					<label for="${compSection}.healthEffectsQcErrorType" class="optionTitle">${_errorType}:</label><br/>
						<select name="${compSection}.healthEffectsQcErrorType" id="${compSection}.healthEffectsQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
							<option value="">${_noerror}</option>
							<c:set var="heErrorType" value="${empty item.qcData.healthEffectsQcErrorType?'':item.qcData.healthEffectsQcErrorType}"/>
							<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
							<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq heErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
							</c:forEach>
						</select>
				</div>
			</fieldset>
			</div>
			<div class="column"> <%-- 8 --%>
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.incompatible"/>:</legend>
					<input name="${compSection}.incompatible" id="${compSection}.incompatibleY" type="radio" class="radioBtns" value="Y"<c:if test="${item.incompatible eq 'Y'}">checked</c:if>>
				<label for="${compSection}.incompatibleY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.incompatible" id="${compSection}.incompatibleN" type="radio" class="radioBtns" value="N"<c:if test="${item.incompatible eq 'N'}">checked</c:if>>
				<label for="${compSection}.incompatibleN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.incompatible" id="${compSection}.incompatibleNA" type="radio" class="radioBtns" value="NA"<c:if test="${item.incompatible eq 'NA'}">checked</c:if>>
				<label for="${compSection}.incompatibleNA" class="optionTitleBold">${_na}</label>
				<label class="optionTitleBold wrapless"><input name="${compSection}.incompatible" id="${compSection}.incompatibleNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.incompatible eq 'NL'}">checked</c:if>>${_notlisted}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('incompatible',true);" type="button"/>
				<div id="${compSection}.incompatibleQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
					<label for="${compSection}.incompatibleQcErrorType" class="optionTitle">${_errorType}:</label><br/>
						<select name="${compSection}.incompatibleQcErrorType" id="${compSection}.incompatibleQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
							<option value="">${_noerror}</option>
							<c:set var="incompErrorType" value="${empty item.qcData.incompatibleQcErrorType?'':item.qcData.incompatibleQcErrorType}"/>
							<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
							<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq incompErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
							</c:forEach>
						</select>
				</div>
			</fieldset>
			</div>
			<%-- <div class="column"> <-- 9 -->
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.lowvolumeexempt"/>:</legend>
					<input name="${compSection}.lowVolumeExempt" id="${compSection}.lowVolumeExemptY" type="radio" class="radioBtns" value="Y"<c:if test="${item.lowVolumeExempt eq 'Y'}">checked</c:if>>
				<label for="${compSection}.lowVolumeExemptY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.lowVolumeExempt" id="${compSection}.lowVolumeExemptN" type="radio" class="radioBtns" value="N"<c:if test="${item.lowVolumeExempt eq 'N'}">checked</c:if>>
				<label for="${compSection}.lowVolumeExemptN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.lowVolumeExempt" id="${compSection}.lowVolumeExemptNA" type="radio" class="radioBtns" disabled value="X"<c:if test="${item.lowVolumeExempt eq 'X'}">checked</c:if>>
				<label for="${compSection}.coLowVolumeExemptNA" class="optionTitleBold">${_na}</label>
				<!--<label class="optionTitleBold wrapless"><input name="${compSection}.lowVolumeExempt" id="${compSection}.lowVolumeExemptNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.lowVolumeExempt eq 'NL'}">checked</c:if>>${_notlisted}</label>-->
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('lowVolumeExempt',true);" type="button"/>
			</fieldset>
			</div>
			<div class="column"> <-- 10 -->
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.miscible"/>:</legend>
					<input name="${compSection}.miscible" id="${compSection}.miscibleY" type="radio" class="radioBtns" value="Y"<c:if test="${item.miscible eq 'Y'}">checked</c:if>>
				<label for="${compSection}.miscibleY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.miscible" id="${compSection}.miscibleN" type="radio" class="radioBtns" value="N"<c:if test="${item.miscible eq 'N'}">checked</c:if>>
				<label for="${compSection}.miscibleN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.miscible" id="${compSection}.miscibleNA" type="radio" class="radioBtns" disabled value="X"<c:if test="${item.miscible eq 'X'}">checked</c:if>>
				<label for="${compSection}.miscibleNA" class="optionTitleBold">${_na}</label>
				<!--<label class="optionTitleBold wrapless"><input name="${compSection}.miscible" id="${compSection}.miscibleNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.miscible eq 'NL'}">checked</c:if>>${_notlisted}</label>-->
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('miscible',true);" type="button"/>
			</fieldset>
			</div> --%>
			<div class="column"> <%-- 11 --%>
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.oxidizer"/>:</legend>
					<input name="${compSection}.oxidizer" id="${compSection}.oxidizerY" type="radio" class="radioBtns" value="Y"<c:if test="${item.oxidizer eq 'Y'}">checked</c:if>>
				<label for="${compSection}.oxidizerY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.oxidizer" id="${compSection}.oxidizerN" type="radio" class="radioBtns" value="N"<c:if test="${item.oxidizer eq 'N'}">checked</c:if>>
				<label for="${compSection}.oxidizerN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.oxidizer" id="${compSection}.oxidizerNA" type="radio" class="radioBtns" value="NA"<c:if test="${item.oxidizer eq 'NA'}">checked</c:if>>
				<label for="${compSection}.oxidizerNA" class="optionTitleBold">${_na}</label>
				<label class="optionTitleBold wrapless"><input name="${compSection}.oxidizer" id="${compSection}.oxidizerNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.oxidizer eq 'NL'}">checked</c:if>>${_notlisted}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('oxidizer',true);" type="button"/>
				<div id="${compSection}.oxidizerQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
					<label for="${compSection}.oxidizerQcErrorType" class="optionTitle">${_errorType}:</label><br/>
						<select name="${compSection}.oxidizerQcErrorType" id="${compSection}.oxidizerQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
							<option value="">${_noerror}</option>
							<c:set var="oxErrorType" value="${empty item.qcData.oxidizerQcErrorType?'':item.qcData.oxidizerQcErrorType}"/>
							<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
							<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq oxErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
							</c:forEach>
						</select>
				</div>
			</fieldset>
			</div>
			<%-- <div class="column"> <-- 12 -->
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.ozonedepletingcompound"/>:</legend>
					<input name="${compSection}.ozoneDepletingCompound" id="${compSection}.ozoneDepletingCompoundY" type="radio" class="radioBtns" value="Y"<c:if test="${item.ozoneDepletingCompound eq 'Y'}">checked</c:if>>
				<label for="${compSection}.ozoneDepletingCompoundY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.ozoneDepletingCompound" id="${compSection}.ozoneDepletingCompoundN" type="radio" class="radioBtns" value="N"<c:if test="${item.ozoneDepletingCompound eq 'N'}">checked</c:if>>
				<label for="${compSection}.ozoneDepletingCompoundN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.ozoneDepletingCompound" id="${compSection}.ozoneDepletingCompoundNA" type="radio" class="radioBtns" disabled value="X"<c:if test="${item.ozoneDepletingCompound eq 'X'}">checked</c:if>>
				<label for="${compSection}.ozoneDepletingCompoundNA" class="optionTitleBold">${_na}</label>
				<!--<label class="optionTitleBold wrapless"><input name="${compSection}.ozoneDepletingCompound" id="${compSection}.ozoneDepletingCompoundNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.ozoneDepletingCompound eq 'NL'}">checked</c:if>>${_notlisted}</label>-->
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('ozoneDepletingCompound',true);" type="button"/>
			</fieldset>
			</div> --%>
			<div class="column"> <%-- 13 --%>
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.polymerize"/>:</legend>
					<input name="${compSection}.polymerize" id="${compSection}.polymerizeY" type="radio" class="radioBtns" value="Y"<c:if test="${item.polymerize eq 'Y'}">checked</c:if>>
				<label for="${compSection}.polymerizeY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.polymerize" id="${compSection}.polymerizeN" type="radio" class="radioBtns" value="N"<c:if test="${item.polymerize eq 'N'}">checked</c:if>>
				<label for="${compSection}.polymerizeN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.polymerize" id="${compSection}.polymerizeNA" type="radio" class="radioBtns" value="NA"<c:if test="${item.polymerize eq 'NA'}">checked</c:if>>
				<label for="${compSection}.polymerizeNA" class="optionTitleBold">${_na}</label>
				<label class="optionTitleBold wrapless"><input name="${compSection}.polymerize" id="${compSection}.polymerizeNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.polymerize eq 'NL'}">checked</c:if>>${_notlisted}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('polymerize',true);" type="button"/>
				<div id="${compSection}.polymerizeQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
					<label for="${compSection}.polymerizeQcErrorType" class="optionTitle">${_errorType}:</label><br/>
						<select name="${compSection}.polymerizeQcErrorType" id="${compSection}.polymerizeQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
							<option value="">${_noerror}</option>
							<c:set var="polyErrorType" value="${empty item.qcData.polymerizeQcErrorType?'':item.qcData.polymerizeQcErrorType}"/>
							<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
							<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq polyErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
							</c:forEach>
						</select>
				</div>
			</fieldset>
			</div>
			<%-- <div class="column"> <-- 14 -->
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.pyrophoric"/>:</legend>
					<input name="${compSection}.pyrophoric" id="${compSection}.pyrophoricY" type="radio" class="radioBtns" value="Y"<c:if test="${item.pyrophoric eq 'Y'}">checked</c:if>>
				<label for="${compSection}.pyrophoricY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.pyrophoric" id="${compSection}.pyrophoricN" type="radio" class="radioBtns" value="N"<c:if test="${item.pyrophoric eq 'N'}">checked</c:if>>
				<label for="${compSection}.pyrophoricN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.pyrophoric" id="${compSection}.pyrophoricNA" type="radio" class="radioBtns" disabled value="X"<c:if test="${item.pyrophoric eq 'X'}">checked</c:if>>
				<label for="${compSection}.pyrophoricNA" class="optionTitleBold">${_na}</label>
				<!--<label class="optionTitleBold wrapless"><input name="${compSection}.pyrophoric" id="${compSection}.pyrophoricNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.pyrophoric eq 'NL'}">checked</c:if>>${_notlisted}</label>-->
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('pyrophoric',true);" type="button"/>
			</fieldset>
			</div>
			<div class="column"> <-- 15 -->
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.sara311312acute"/>:</legend>
					<input name="${compSection}.sara311312Acute" id="${compSection}.sara311312AcuteY" type="radio" class="radioBtns" value="Y"<c:if test="${item.sara311312Acute eq 'Y'}">checked</c:if>>
				<label for="${compSection}.sara311312AcuteY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.sara311312Acute" id="${compSection}.sara311312AcuteN" type="radio" class="radioBtns" value="N"<c:if test="${item.sara311312Acute eq 'N'}">checked</c:if>>
				<label for="${compSection}.sara311312AcuteN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.sara311312Acute" id="${compSection}.sara311312AcuteNA" type="radio" class="radioBtns" value="U"<c:if test="${item.sara311312Acute eq 'U'}">checked</c:if>>
				<label for="${compSection}.sara311312AcuteNA" class="optionTitleBold">${_na}</label>
				<!--<label class="optionTitleBold wrapless"><input name="${compSection}.sara311312Acute" id="${compSection}.sara311312AcuteNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.sara311312Acute eq 'NL'}">checked</c:if>>${_notlisted}</label>-->
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('sara311312Acute',true);" type="button"/>
			</fieldset>
			</div>
			<div class="column"> <-- 16 -->
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.sara311312chronic"/>:</legend>
					<input name="${compSection}.sara311312Chronic" id="${compSection}.sara311312ChronicY" type="radio" class="radioBtns" value="Y"<c:if test="${item.sara311312Chronic eq 'Y'}">checked</c:if>>
				<label for="${compSection}.sara311312ChronicY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.sara311312Chronic" id="${compSection}.sara311312ChronicN" type="radio" class="radioBtns" value="N"<c:if test="${item.sara311312Chronic eq 'N'}">checked</c:if>>
				<label for="${compSection}.sara311312ChronicN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.sara311312Chronic" id="${compSection}.sara311312ChronicNA" type="radio" class="radioBtns" value="U"<c:if test="${item.sara311312Chronic eq 'U'}">checked</c:if>>
				<label for="${compSection}.sara311312ChronicNA" class="optionTitleBold">${_na}</label>
				<!--<label class="optionTitleBold wrapless"><input name="${compSection}.sara311312Chronic" id="${compSection}.sara311312ChronicNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.sara311312Chronic eq 'NL'}">checked</c:if>>${_notlisted}</label>-->
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('sara311312Chronic',true);" type="button"/>
			</fieldset>
			</div>
			<div class="column"> <-- 17 -->
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.sara311312fire"/>:</legend>
					<input name="${compSection}.sara311312Fire" id="${compSection}.sara311312FireY" type="radio" class="radioBtns" value="Y"<c:if test="${item.sara311312Fire eq 'Y'}">checked</c:if>>
				<label for="${compSection}.sara311312FireY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.sara311312Fire" id="${compSection}.sara311312FireN" type="radio" class="radioBtns" value="N"<c:if test="${item.sara311312Fire eq 'N'}">checked</c:if>>
				<label for="${compSection}.sara311312FireN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.sara311312Fire" id="${compSection}.sara311312FireNA" type="radio" class="radioBtns" value="U"<c:if test="${item.sara311312Fire eq 'U'}">checked</c:if>>
				<label for="${compSection}.sara311312FireNA" class="optionTitleBold">${_na}</label>
				<!--<label class="optionTitleBold wrapless"><input name="${compSection}.sara311312Fire" id="${compSection}.sara311312FireNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.sara311312Fire eq 'NL'}">checked</c:if>>${_notlisted}</label>-->
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('sara311312Fire',true);" type="button"/>
			</fieldset>
			</div>
			<div class="column"> <-- 18 -->
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.sara311312pressure"/>:</legend>
					<input name="${compSection}.sara311312Pressure" id="${compSection}.sara311312PressureY" type="radio" class="radioBtns" value="Y"<c:if test="${item.sara311312Pressure eq 'Y'}">checked</c:if>>
				<label for="${compSection}.sara311312PressureY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.sara311312Pressure" id="${compSection}.sara311312PressureN" type="radio" class="radioBtns" value="N"<c:if test="${item.sara311312Pressure eq 'N'}">checked</c:if>>
				<label for="${compSection}.sara311312PressureN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.sara311312Pressure" id="${compSection}.sara311312PressureNA" type="radio" class="radioBtns" value="U"<c:if test="${item.sara311312Pressure eq 'U'}">checked</c:if>>
				<label for="${compSection}.sara311312PressureNA" class="optionTitleBold">${_na}</label>
				<!--<label class="optionTitleBold wrapless"><input name="${compSection}.sara311312Pressure" id="${compSection}.sara311312PressureNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.sara311312Pressure eq 'NL'}">checked</c:if>>${_notlisted}</label>-->
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('sara311312Pressure',true);" type="button"/>
			</fieldset>
			</div>
			<div class="column"> <-- 19 -->
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.sara311312reactivity"/>:</legend>
					<input name="${compSection}.sara311312Reactivity" id="${compSection}.sara311312ReactivityY" type="radio" class="radioBtns" value="Y"<c:if test="${item.sara311312Reactivity eq 'Y'}">checked</c:if>>
				<label for="${compSection}.sara311312ReactivityY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.sara311312Reactivity" id="${compSection}.sara311312ReactivityN" type="radio" class="radioBtns" value="N"<c:if test="${item.sara311312Reactivity eq 'N'}">checked</c:if>>
				<label for="${compSection}.sara311312ReactivityN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.sara311312Reactivity" id="${compSection}.sara311312ReactivityNA" type="radio" class="radioBtns" value="U"<c:if test="${item.sara311312Reactivity eq 'U'}">checked</c:if>>
				<label for="${compSection}.sara311312ReactivityNA" class="optionTitleBold">${_na}</label>
				<!--<label class="optionTitleBold wrapless"><input name="${compSection}.sara311312Reactivity" id="${compSection}.sara311312ReactivityNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.sara311312Reactivity eq 'NL'}">checked</c:if>>${_notlisted}</label>-->
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('sara311312Reactivity',true);" type="button"/>
			</fieldset>
			</div>
			<div class="column"> <-- 20 -->
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.spontaneouslycombustible"/>:</legend>
					<input name="${compSection}.spontaneouslyCombustible" id="${compSection}.spontaneouslyCombustibleY" type="radio" class="radioBtns" value="Y"<c:if test="${item.spontaneouslyCombustible eq 'Y'}">checked</c:if>>
				<label for="${compSection}.spontaneouslyCombustibleY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.spontaneouslyCombustible" id="${compSection}.spontaneouslyCombustibleN" type="radio" class="radioBtns" value="N"<c:if test="${item.spontaneouslyCombustible eq 'N'}">checked</c:if>>
				<label for="${compSection}.spontaneouslyCombustibleN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.spontaneouslyCombustible" id="${compSection}.spontaneouslyCombustibleNA" type="radio" class="radioBtns" disabled value="X"<c:if test="${item.spontaneouslyCombustible eq 'X'}">checked</c:if>>
				<label for="${compSection}.spontaneouslyCombustibleNA" class="optionTitleBold">${_na}</label>
				<!--<label class="optionTitleBold wrapless"><input name="${compSection}.spontaneouslyCombustible" id="${compSection}.spontaneouslyCombustibleNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.spontaneouslyCombustible eq 'NL'}">checked</c:if>>${_notlisted}</label>-->
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('spontaneouslyCombustible',true);" type="button"/>
			</fieldset>
			</div> --%>
			<div class="column"> <%-- 21 --%>
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.stable"/>:</legend>
					<input name="${compSection}.stable" id="${compSection}.stableY" type="radio" class="radioBtns" value="Y"<c:if test="${item.stable eq 'Y'}">checked</c:if>>
				<label for="${compSection}.stableY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.stable" id="${compSection}.stableN" type="radio" class="radioBtns" value="N"<c:if test="${item.stable eq 'N'}">checked</c:if>>
				<label for="${compSection}.stableN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.stable" id="${compSection}.stableNA" type="radio" class="radioBtns" value="NA"<c:if test="${item.stable eq 'NA'}">checked</c:if>>
				<label for="${compSection}.stableNA" class="optionTitleBold">${_na}</label>
				<label class="optionTitleBold wrapless"><input name="${compSection}.stable" id="${compSection}.stableNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.stable eq 'NL'}">checked</c:if>>${_notlisted}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('stable',true);" type="button"/>
				<div id="${compSection}.stableQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
					<label for="${compSection}.stableQcErrorType" class="optionTitle">${_errorType}:</label><br/>
						<select name="${compSection}.stableQcErrorType" id="${compSection}.stableQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
							<option value="">${_noerror}</option>
							<c:set var="stableErrorType" value="${empty item.qcData.stableQcErrorType?'':item.qcData.stableQcErrorType}"/>
							<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
							<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq stableErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
							</c:forEach>
						</select>
				</div>
			</fieldset>
			</div>
			<%--<div class="column"> <-- 22 -->
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.tscastatement"/>:</legend>
					<input name="${compSection}.tscaStatement" id="${compSection}.tscaStatementY" type="radio" class="radioBtns" value="Y"<c:if test="${item.tscaStatement eq 'Y'}">checked</c:if>>
				<label for="${compSection}.tscaStatementY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.tscaStatement" id="${compSection}.tscaStatementN" type="radio" class="radioBtns" value="N"<c:if test="${item.tscaStatement eq 'N'}">checked</c:if>>
				<label for="${compSection}.tscaStatementN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.tscaStatement" id="${compSection}.tscaStatementNA" type="radio" class="radioBtns" value="NA"<c:if test="${item.tscaStatement eq 'NA'}">checked</c:if>>
				<label for="${compSection}.tscaStatementNA" class="optionTitleBold">${_na}</label>
				<label class="optionTitleBold wrapless"><input name="${compSection}.tscaStatement" id="${compSection}.tscaStatementNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.tscaStatement eq 'NL'}">checked</c:if>>${_notlisted}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('tscaStatement',true);" type="button"/>
				<div id="${compSection}.tscaQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
					<label for="${compSection}.tscaQcErrorType" class="optionTitle">${_errorType}:</label><br/>
						<select name="${compSection}.tscaQcErrorType" id="${compSection}.tscaQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
							<option value="">${_noerror}</option>
							<c:set var="tscaErrorType" value="${empty item.qcData.tscaQcErrorType?'':item.qcData.tscaQcErrorType}"/>
							<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
							<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq tscaErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
							</c:forEach>
						</select>
				</div>
			</fieldset>
			</div>--%>
			<div class="column"> <%-- 23 --%>
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.waterreactive"/>:</legend>
					<input name="${compSection}.waterReactive" id="${compSection}.waterReactiveY" type="radio" class="radioBtns" value="Y"<c:if test="${item.waterReactive eq 'Y'}">checked</c:if>>
				<label for="${compSection}.waterReactiveY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.waterReactive" id="${compSection}.waterReactiveN" type="radio" class="radioBtns" value="N"<c:if test="${item.waterReactive eq 'N'}">checked</c:if>>
				<label for="${compSection}.waterReactiveN" class="optionTitleBold">${_no}</label>
					<input name="${compSection}.waterReactive" id="${compSection}.waterReactiveNA" type="radio" class="radioBtns" value="NA"<c:if test="${item.waterReactive eq 'NA'}">checked</c:if>>
				<label for="${compSection}.waterReactiveNA" class="optionTitleBold">${_na}</label>
				<label class="optionTitleBold wrapless"><input name="${compSection}.waterReactive" id="${compSection}.waterReactiveNL" type="radio" class="radioBtns" value="NL"<c:if test="${item.waterReactive eq 'NL'}">checked</c:if>>${_notlisted}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('waterReactive',true);" type="button"/>
				<div id="${compSection}.waterReactiveQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
					<label for="${compSection}.waterReactiveQcErrorType" class="optionTitle">${_errorType}:</label><br/>
						<select name="${compSection}.waterReactiveQcErrorType" id="${compSection}.waterReactiveQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
							<option value="">${_noerror}</option>
							<c:set var="waterErrorType" value="${empty item.qcData.waterReactiveQcErrorType?'':item.qcData.waterReactiveQcErrorType}"/>
							<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
							<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq waterErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
							</c:forEach>
						</select>
				</div>
			</fieldset>
			</div>
			<div class="column"> <%-- 24 --%>
			<fieldset style="border:none"> 
				<legend class="optionTitle" style="white-space:nowrap;"><fmt:message key="label.vocasmixed"/>:</legend>
					<input name="${compSection}.asMixed" id="${compSection}.asMixedY" type="radio" class="radioBtns" value="Y"<c:if test="${item.asMixed eq 'Y'}">checked</c:if>>
				<label for="${compSection}.asMixedY" class="optionTitleBold">${_yes}</label>
					<input name="${compSection}.asMixed" id="${compSection}.asMixedN" type="radio" class="radioBtns" value="N"<c:if test="${item.asMixed eq 'N'}">checked</c:if>>
				<label for="${compSection}.asMixedN" class="optionTitleBold">${_no}</label>
				&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="${_clear}" onclick="msdsIndex.clearRadio('asMixed',true);" type="button"/>
			</fieldset>
			</div>
		</c:if>
	</div>
</fieldset>
</div>