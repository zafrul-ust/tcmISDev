<div id="msds[${index}].hazardstatements">
<fieldset>
	<legend class="msdsSectionHeader"><fmt:message key="label.hazardstatement"/></legend>
	<div style="padding:5px;margin-bottom:5px;">
		<input name="msds[${index}].editHazards" id="msds[${index}].editHazards" type="button" value="${_edit}" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="msdsIndex.editHazards(true);">
		<input name="msds[${index}].finishEditHazards" id="msds[${index}].finishEditHazards" type="button" value="${_finish}" style="display:none" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="msdsIndex.editHazards(false);">
		<input name="msds[${index}].clearHazards" id="msds[${index}].clearHazards" type="button" value="${_clear}" class="inputBtns" style="display:none" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="msdsIndex.clearHazards();">
		<input name="msds[${index}].resetHazards" id="msds[${index}].resetHazards" type="button" value="${_cancel}" class="inputBtns" style="display:none" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="msdsIndex.resetHazards();">
		<div style="overflow:auto;max-height:450px;">
		<ul id="msds[${index}].ghsHazards" style="list-style-type:none">
			<c:forEach var="hazardBean" items="${msds.hazardStmts}" varStatus="hStatus">
			<c:set var="hazardChecked" value="${empty hazardBean.msdsId?'':'checked'}"/>
			<c:set var="hazardComp" value="msdshazards${index}[${hStatus.index+1}]"/>
			<li style="border-top:1px solid darkgray;display:${hazardChecked eq 'checked'?'block':'none'};">
				<input type="checkbox" name="${hazardComp}.ghsStatementId" id="${hazardComp}.selectHazard" value="${hazardBean.id}" class="radio" style="visibility:hidden" <c:out value="${hazardChecked}"/> />
				<input type="hidden" name="${hazardComp}.code" id="${hazardComp}.code" value="${hazardBean.code}" />
				<input type="checkbox" disabled id="${hazardComp}.original" class="radio" style="display:none;" <c:out value="${hazardChecked}"/>/>
				<label for="${hazardComp}.selectHazard" class="optionTitleBold">${hazardBean.code}:</label>
				<label id="${hazardComp}.isFromMsdsMsg" for="${hazardComp}.selectHazard" class="maxcomEstimate" style="<c:out value="${hazardBean.isFromMsds == 1?'display:none':'display:inline'}" />" title="${_maxcomestimate}">(${_estimate})</label>
				<input type="hidden" name="${hazardComp}.isFromMsds" id="${hazardComp}.isFromMsds" value="${hazardBean.isFromMsds}"/>
				<input type="hidden" disabled id="${hazardComp}.originalIsFromMsds" value="${hazardBean.isFromMsds}"/><br/>
				<div style="margin:0 0 5px 24px;width:90%;" id="${hazardComp}.statement" title="${hazardBean.statement}">${hazardBean.statement}</div>
			</li>
			</c:forEach>
		</ul>
		</div>
		<div id="${compSection}.hazardStatementsQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.hazardStatementsQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.hazardStatementsQcErrorType" id="${compSection}.hazardStatementsQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="hazardErrorType" value="${empty item.qcData.hazardStatementsQcErrorType?'':item.qcData.hazardStatementsQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq hazardErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
	</div>
</fieldset>
</div>