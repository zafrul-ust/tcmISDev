<div id="msds[${index}].precautionarystatements">
<fieldset>
	<legend class="msdsSectionHeader"><fmt:message key="label.precautionarystatement"/></legend>
	<div style="padding:5px;margin-bottom:5px;">
		<input name="msds[${index}].editPrecautions" id="msds[${index}].editPrecautions" type="button" value="${_edit}" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="msdsIndex.editPrecautions(true);">
		<input name="msds[${index}].finishEditPrecautions" id="msds[${index}].finishEditPrecautions" type="button" value="${_finish}" style="display:none" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="msdsIndex.editPrecautions(false);">
		<input name="msds[${index}].clearPrecautions" id="msds[${index}].clearPrecautions" type="button" value="${_clear}" style="display:none" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="msdsIndex.clearPrecautions();">
		<input name="msds[${index}].resetPrecautions" id="msds[${index}].resetPrecautions" type="button" value="${_cancel}" style="display:none" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="msdsIndex.resetPrecautions();">
		<div style="overflow:auto;max-height:450px;">
		<ul id="msds[${index}].ghsPrecautions" style="list-style-type:none">
			<c:forEach var="precautionBean" items="${msds.precautionaryStmts}" varStatus="pStatus">
			<c:set var="precautionChecked" value="${empty precautionBean.msdsId?'':'checked'}"/>
			<c:set var="preComp" value="msdsprecautions${index}[${pStatus.index+1}]"/>
			<li style="border-top:1px solid darkgray;display:${precautionChecked eq 'checked'?'block':'none'};">
				<input type="checkbox" name="${preComp}.ghsStatementId" id="${preComp}.selectPrecaution" value="${precautionBean.id}" class="radio" style="visibility:hidden" <c:out value="${precautionChecked}"/>/>
				<input type="hidden" name="${preComp}.code" id="${preComp}.code" value="${precautionaryBean.code}"/>
				<input type="checkbox" disabled id="${preComp}.original" class="radio" style="display:none;" <c:out value="${precautionChecked}"/>/>
				<label for="${preComp}.selectPrecaution" class="optionTitleBold">${precautionBean.code}:</label>
				<label id="${preComp}.isFromMsdsMsg" for="${preComp}.selectPrecaution" class="maxcomEstimate" style="<c:out value="${precautionBean.isFromMsds == 1?'display:none':'display:inline'}" />" title="${_maxcomestimate}">(${_estimate})</label>
				<input type="hidden" name="${preComp}.isFromMsds" id="${preComp}.isFromMsds" value="${precautionBean.isFromMsds}"/>
				<input type="hidden" disabled id="${preComp}.originalIsFromMsds" value="${precautionBean.isFromMsds}"/><br/>
				<div style="margin:0 0 5px 24px;width:90%;" id="${preComp}.statement" title="${precautionBean.statement}">${precautionBean.statement}</div>
			</li>
			</c:forEach>
		</ul>
		</div>
		<div id="${compSection}.precautionaryStatementsQcBlock" class="msdsIndexedField ${qcStatus eq 'na' || qcStatus eq 'incomplete'?'invisible':''}">
			<label for="${compSection}.precautionaryStatementsQcErrorType" class="optionTitle">${_errorType}:</label><br/>
				<select name="${compSection}.precautionaryStatementsQcErrorType" id="${compSection}.precautionaryStatementsQcErrorType" class="selectBox" ${qcStatus eq 'complete' || qcStatus eq 'pendingQc'?'disabled':''}>    
					<option value="">${_noerror}</option>
					<c:set var="precautionErrorType" value="${empty item.qcData.precautionaryStatementsQcErrorType?'':item.qcData.precautionaryStatementsQcErrorType}"/>
					<c:forEach var="qcErrorBean" items="${vvQcErrorTypeCollection}" varStatus="status2">
					<option value="${qcErrorBean.qcErrorTypeId}" ${qcErrorBean.qcErrorTypeId eq precautionErrorType?'selected':''}>${qcErrorBean.qcErrorType}</option>
					</c:forEach>
				</select>
		</div>
	</div>
</fieldset>
</div>