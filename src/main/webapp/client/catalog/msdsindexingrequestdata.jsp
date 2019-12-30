		<div class="contentArea">
		<!-- Search Frame Begins -->
		<table id="SearchMaskTable" width="98%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
		<div class="roundcont filterContainer">
			<div class="roundright">
				<div class="roundtop">
					<div class="msdsroundtopright">
						<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
					</div>
				</div>
				<div class="roundContent">
					<div class="boxhead">
						<div id="gridUpdateLinks">
							<tcmis:catalogPermission indicator="true" userGroupId="catalogAddMSDS">
							<span id="requestSaveButton"><a href="#" onclick="msdsIndex.submitSave();">Save</a> |</span>
							<span id="requestApprovalButton"><a href="#" onclick="msdsIndex.submitApprove();">Approve MSDS</a> |</span>
							<c:if test="${not empty chemRequest && param.approvalRole eq 'Material QC'}">
							<span id="sourcingApprovalButton"><a href="#" onclick="msdsIndex.submitSourcingApprove();">Approve SDS Sourcing</a> |</span>
							<a href="#" onclick="msdsIndex.viewUploadedSDS();"><fmt:message key="label.viewuploadedsds"/></a> |
							</c:if>
							
							<div id="rejectButton" style="position:relative;display:inline;z-index:1">
								<a id="rejectCommentBtn" href="#" onclick="msdsIndex.toggleRejectionPopup();"><fmt:message key="label.reject"/></a> |
								<div id="rejectCommentPopup" style="position:absolute;width:400px;z-index:1;display:none;background-color:#e5e5e5;padding:5px;border:1px solid gray;">
									<label for="rejectionComments"><fmt:message key="label.comments"/>:</label>
									<span style="float:right;cursor:pointer" onclick="msdsIndex.toggleRejectionPopup();">x</span>
									<textarea id="rejectionComments" name="rejectionComments" rows="10" class="inputBox" style="width:95%;position:relative"></textarea>
									<br/>
									<input type="button" id="rejectConfirmButton" name="rejectConfirmButton" onclick="msdsIndex.submitRejectCannotFulfill();" value="<fmt:message key="label.reject"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
								</div>
							</div>
							
							</tcmis:catalogPermission>
							<c:if test="${hasOutOfScopeFeature == 'Y'}">
							    <a href="#" onclick="msdsIndex.submitRejectOutOfScope();">Reject: Out of Scope</a> | 
							</c:if>
							<a href="#" onclick="msdsIndex.viewRequest();">View Request</a> |
							<a href="#" onclick="msdsIndex.createViewContactLog();"><fmt:message key="label.createviewcontactlog"/></a>  
							<span class="optionTitleLeft" id ="customerOverrideRule" style="display: none;padding-left:400px; overflow: auto;color:red;font-weight:bold"><fmt:message key="label.customeroverriderule"/></span>	
						</div>
					</div>
					<table width="100%" id="detailsDataTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
						<tr>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.requestid" />:&nbsp;</td>
							<td width="15%"  class="optionTitleLeft">
								${chemRequest.requestId}
								<input type="hidden" name="requestId" id="requestId" value="${chemRequest.requestId}"/>
								<input type="hidden" name="lineItem" id="lineItem" value="${param.lineItem}"/>
							</td>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.phone" />:&nbsp;</td>
							<td width="15%" class="optionTitleLeft">${chemRequest.phone}</td>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.company" />:&nbsp;</td>
							<td width="15%" class="optionTitleLeft">
								<c:out value="${chemRequest.companyName}"/>
								<input type="hidden" name="companyId" id="companyId" value="<c:out value="${chemRequest.companyId}" />"/>
								<input type="hidden" name="companyMsds" id="companyMsds" value="<c:out value="${companyMsds}" />"/>
							</td>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.datesubmitted" />:&nbsp;</td>
							<td width="15%" class="optionTitleLeft">${chemRequest.requestDate}</td>
						</tr>
						<tr>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.requestor" />:&nbsp;</td>
							<td width="15%"  class="optionTitleLeft"><c:out value="${chemRequest.fullName}"/></td>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.email" />:&nbsp;</td>
							<td width="15%" class="optionTitleLeft"><a href="mailto:${chemRequest.email}">${chemRequest.email}</a></td>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.catalogid" />:&nbsp;</td>
							<td width="15%" class="optionTitleLeft">
								<c:out value="${chemRequest.catalogId}"/>
								<input type="hidden" name="catalogId" id="catalogId" value="<c:out value="${chemRequest.catalogId}"/>"/>
							</td>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.evaluation" />:&nbsp;</td>
							<td width="15%" class="optionTitleLeft">${chemRequest.engineeringEvaluation}</td>
						</tr>
						<tr>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.userpartnumber" />:&nbsp;</td>
							<td width="15%"  class="optionTitleLeft"><c:out value="${chemRequest.catPartNo}"/></td>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.partdescription" />:&nbsp;</td>
							<td width="15%" class="optionTitleLeft"><input id="partDescription" name="partDescription" type="text" style="width:100%" class="inputBox" value="<c:out value="${chemRequest.partDescription}"/>"/></td>
							<td width="10%" class="optionTitleBoldRight">&nbsp;</td>
							<td width="15%" class="optionTitleLeft">&nbsp;</td>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.startingview" />:&nbsp;</td>
							<td width="15%" class="optionTitleLeft"><c:out value="${chemRequest.requestStatusDesc}"/></td>
						</tr>
					</table>
				</div>
				<div class="roundbottom">
					<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
				</div>
			</div>
		</div>
		</td>
		</tr>
		</table>
</div>
