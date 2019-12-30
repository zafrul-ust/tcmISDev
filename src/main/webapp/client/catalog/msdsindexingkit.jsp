
<c:if test="${kitData.allowMixture}">
	<input id="allowMixture" name="allowMixture" type="checkbox" checked style="display:none"/> 
	<div class="roundcont filterContainer">
		<div class="roundright">
			<div class="roundtop">
				<div class="roundtopright">
					<img src="/images/rndBoxes/borderTL_filter.gif" alt=""
						width="15" height="10" class="corner_filter"
						style="display: none" />
				</div>
			</div>
			<div class="roundContent">
				<table border="0">
				<tr>
					<td width="1%" class="optionTitleBoldCenter">
						<fmt:message key="label.kit" />
					</td>
					<TD width="5%" class="optionTitleLeft" nowrap>
						<FIELDSET>
							<table width="100%" class="tableSearch" id="kitDataTable2" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="40%" class="optionTitleBoldRight"><fmt:message key="label.kitsdsno"/>&nbsp;:</td>
									<td width="60%" class="optionTitleLeft"><input class="inputBox" type="text" name="customerMixtureNumber" id="customerMixtureNumber" size="30" maxlength="30" value="${kitData.customerMixtureNumber}">
										&nbsp;
										<c:if test="${generateSdsFromSequence == 'true'}">
											<input name="generateKitNumberButton" id="generateKitNumberButton" type="button" value="<fmt:message key="label.generatesds"/>"
												   class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
												   onclick="msdsIndex.generateSdsNumber(true); return false;">
										</c:if>
									</td>
								</tr>
								<tr>
									<td width="40%" class="optionTitleBoldRight"><fmt:message
											key="label.description" />&nbsp;:</td>
									<td width="60%" class="optionTitleLeft"><input
										class="inputBox" type="text" name="mixtureDesc"
										id="mixtureDesc" size="60" maxlength="30"
										value="${kitData.mixtureDesc}"></td>
								</tr>
								<tr>
									<td class="optionTitleBoldRight"><fmt:message
											key="label.manufacturer" />:</td>
									<td class="optionTitleLeft"><input
										class="inputBox" type="text"
										name="mixtureManufacturer" id="mixtureManufacturer"
										size="60" maxlength="60"
										value="${kitData.mixtureManufacturer}"></td>
								</tr>
								<tr>
									<td class="optionTitleBoldRight"><fmt:message
											key="label.id" />&nbsp;:</td>
									<td><input class="inputBox" type="hidden"
										name="mixtureMfgId" id="mixtureMfgId"
										value="${kitData.mixtureMfgId}">
										<table width="50%" border="0" cellpadding="0"
											cellspacing="0">
											<tr>
												<td width="50%" align="right"><span
													id="mixtureMfgId_disp">${kitData.mixtureMfgId}</span></td>
												<td align="left"><input name="searchForMfg"
													id="searchForMfg" type="button" value="..."
													class="lookupBtn"
													onmouseover="this.className='lookupBtn lookupBtnOver'"
													onmouseout="this.className='lookupBtn'"
													onclick="msdsIndex.lookupManufacturer(true);"></td>
											</tr>
										</table></td>
								</tr>
								<tr>
									<TD width="40%" class="optionTitleBoldRight"><fmt:message
											key="report.label.productCode" />&nbsp;:</TD>
									<TD width="60%" class="optionTitleLeft"><input
										class="inputBox" type="text"
										name="mixtureProductCode" id="mixtureProductCode"
										size="20" maxlength="20"
										value="${kitData.mixtureProductCode}"></TD>
								</tr>
								<tr>
									<TD width="40%" class="optionTitleBoldRight"><fmt:message
											key="label.physicalstate" />&nbsp;:</TD>
									<TD width="60%" class="optionTitleLeft"><select
										name="mixturePhysicalState"
										id="mixturePhysicalState" class="selectBox"
										onchange="checkDataEntryComplete()">
											<option value=""></option>
											<option value="gas"
												<c:if test='${"gas" eq kitData.mixturePhysicalState}'> selected</c:if>><fmt:message
													key="label.gaslower" /></option>
											<OPTION VALUE="liquid"
												<c:if test='${empty kitData.mixturePhysicalState || "liquid" eq kitData.mixturePhysicalState}'> selected</c:if>><fmt:message
													key="label.liquid" /></OPTION>
											<OPTION VALUE="semi-solid"
												<c:if test='${"semi-solid" eq kitData.mixturePhysicalState}'> selected</c:if>><fmt:message
													key="label.semi-solid" /></OPTION>
											<OPTION VALUE="solid"
												<c:if test='${"solid" eq kitData.mixturePhysicalState}'> selected</c:if>><fmt:message
													key="label.solid" /></OPTION>
									</select></TD>
								</tr>
								<tr>
									<TD width="40%" class="optionTitleBoldRight"><fmt:message
											key="label.source" />&nbsp;:</TD>
									<TD width="60%" class="optionTitleLeft"><select
										name="mixturePhysicalStateSource"
										id="mixturePhysicalStateSource" class="selectBox">
											<option value=""></option>
											<c:forEach var="sourceBean"
												items="${vvDataSourceBeanCollection}"
												varStatus="status2">
												<c:choose>
													<c:when
														test="${!empty kitData.mixturePhysicalStateSource}">
														<c:choose>
															<c:when
																test="${sourceBean.dataSource eq kitData.mixturePhysicalStateSource}">
																<option value="${sourceBean.dataSource}"
																	selected>${sourceBean.dataSource}</option>
															</c:when>
															<c:otherwise>
																<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when
																test="${sourceBean.dataSource eq 'MSDS'}">
																<option value="${sourceBean.dataSource}"
																	selected>${sourceBean.dataSource}</option>
															</c:when>
															<c:otherwise>
																<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</c:forEach>
									</select></TD>
								</tr>
								<TR>
									<TD width="30%" class="optionTitleBoldRight">&nbsp;</TD>
									<TD width="70%" class="optionTitleLeft"><input
										name="mixtureViewFile" id="mixtureViewFile"
										type="button"
										value="<fmt:message key="label.viewfile"/>"
										class="inputBtns"
										onmouseover="this.className='inputBtns inputBtnsOver'"
										onmouseout="this.className='inputBtns'"
										onclick="msdsIndex.viewKitDocs(); return false;"> <input
										name="mixtureUploadFile" id="mixtureUploadFile"
										type="button"
										value="<fmt:message key="label.uploadfiles"/>"
										class="inputBtns"
										onmouseover="this.className='inputBtns inputBtnsOver'"
										onmouseout="this.className='inputBtns'"
										onclick="msdsIndex.uploadKitDocs(); return false;"></TD>
								</tr>
							</table>
						</FIELDSET>
					</TD>
					<TD width="5%" class="optionTitleLeft" " nowrap>
						<FIELDSET>
							<LEGEND class="optionTitleBold">
								<fmt:message key="label.voc" />
								&nbsp;
							</LEGEND>
							<table width="100%" class="tableSearch"
								id="kitDataTable3" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<TD width="40%" class="optionTitleBoldRight"><fmt:message
											key="label.value" />&nbsp;:</TD>
									<TD width="60%" class="optionTitleLeft"><input
										class="inputBox" type="text" name="mixtureVoc"
										id="mixtureVoc" size="20" maxlength="20"
										value="${kitData.mixtureVoc}"></TD>
								</tr>
								<tr>
									<TD width="40%" class="optionTitleBoldRight"><fmt:message
											key="label.lower" />&nbsp;:</TD>
									<TD width="60%" class="optionTitleLeft"><input
										class="inputBox" type="text" name="mixtureVocLower"
										id="mixtureVocLower" size="20" maxlength="20"
										value="${kitData.mixtureVocLower}"></TD>
								</tr>
								<tr>
									<TD width="40%" class="optionTitleBoldRight"><fmt:message
											key="label.upper" />&nbsp;:</TD>
									<TD width="60%" class="optionTitleLeft"><input
										class="inputBox" type="text" name="mixtureVocUpper"
										id="mixtureVocUpper" size="20" maxlength="20"
										value="${kitData.mixtureVocUpper}"></TD>
								</tr>
								<tr>
									<TD width="40%" class="optionTitleBoldRight"><fmt:message
											key="label.units" />&nbsp;:</TD>
									<TD width="60%" class="optionTitleLeft"><select
										name="mixtureVocUnit" id="mixtureVocUnit"
										class="selectBox">
											<option value=""
												<c:if test="${empty kitData.mixtureVocUnit}"> selected</c:if>></option>
											<option value="%(w/w)"
												<c:if test='${"%(w/w)" eq kitData.mixtureVocUnit}'> selected</c:if>>%(w/w)</option>
											<option value="g/L"
												<c:if test='${"g/L" eq kitData.mixtureVocUnit}'> selected</c:if>>g/L</option>
											<option value="lb/gal"
												<c:if test='${"lb/gal" eq kitData.mixtureVocUnit}'> selected</c:if>>lb/gal</option>
									</select></TD>
								</tr>
								<tr>
									<TD width="40%" class="optionTitleBoldRight"><fmt:message
											key="label.source" />&nbsp;:</TD>
									<TD width="60%" class="optionTitleLeft"><select
										name="mixtureVocSource" id="mixtureVocSource"
										class="selectBox">
											<option value=""></option>
											<c:forEach var="sourceBean"
												items="${vvDataSourceBeanCollection}"
												varStatus="status2">
												<c:if test="${sourceBean.mixtureSource == 'Y'}">
													<c:choose>
														<c:when
															test="${!empty kitData.mixtureVocSource}">
															<c:choose>
																<c:when
																	test="${sourceBean.dataSource eq kitData.mixtureVocSource}">
																	<option value="${sourceBean.dataSource}"
																		selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when
																	test="${sourceBean.dataSource eq 'MSDS'}">
																	<option value="${sourceBean.dataSource}"
																		selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>
												</c:if>
											</c:forEach>
									</select></TD>
								</tr>
							</table>
						</FIELDSET>
					</TD>
					<td width="6%" class="optionTitleLeft">
						<fieldset>
							<LEGEND class="optionTitleBold">
								<fmt:message key="label.voclesswaterandexempt" />
							</LEGEND>
							<table width="100%" class="tableSearch"
								id="kitDataTable4" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<TD width="40%" class="optionTitleBoldRight"><fmt:message
											key="label.value" />&nbsp;:</TD>
									<TD width="60%" class="optionTitleLeft"><input
										class="inputBox" type="text" name="mixtureVocLwes"
										id="mixtureVocLwes" size="20" maxlength="20"
										value="${kitData.mixtureVocLwes}"></TD>
								</tr>
								<tr>
									<TD width="40%" class="optionTitleBoldRight"><fmt:message
											key="label.lower" />&nbsp;:</TD>
									<TD width="60%" class="optionTitleLeft"><input
										class="inputBox" type="text"
										name="mixtureVocLwesLower" id="mixtureVocLwesLower"
										size="20" maxlength="20"
										value="${kitData.mixtureVocLwesLower}"></TD>
								</tr>
								<tr>
									<TD width="40%" class="optionTitleBoldRight"><fmt:message
											key="label.upper" />&nbsp;:</TD>
									<TD width="60%" class="optionTitleLeft"><input
										class="inputBox" type="text"
										name="mixtureVocLwesUpper" id="mixtureVocLwesUpper"
										size="20" maxlength="20"
										value="${kitData.mixtureVocLwesUpper}"></TD>
								</tr>
								<tr>
									<TD width="40%" class="optionTitleBoldRight"><fmt:message
											key="label.units" />&nbsp;:</TD>
									<TD width="60%" class="optionTitleLeft"><select
										name="mixtureVocLwesUnit" id="mixtureVocLwesUnit"
										class="selectBox">
											<option value=""
												<c:if test="${empty kitData.mixtureVocLwesUnit}"> selected</c:if>></option>
											<option value="g/L"
												<c:if test='${"g/L" eq kitData.mixtureVocLwesUnit}'> selected</c:if>>g/L</option>
											<option value="lb/gal"
												<c:if test='${"lb/gal" eq kitData.mixtureVocLwesUnit}'> selected</c:if>>lb/gal</option>
									</select></TD>
								</tr>
								<tr>
									<TD width="40%" class="optionTitleBoldRight"><fmt:message
											key="label.source" />&nbsp;:</TD>
									<td width="60%" class="optionTitleLeft"><select
										name="mixtureVocLwesSource"
										id="mixtureVocLwesSource" class="selectBox">
											<option value=""
												<c:if test="${empty kitData.mixtureVocLwesSource}"> selected</c:if>></option>
											<c:forEach var="sourceBean"
												items="${vvDataSourceBeanCollection}"
												varStatus="status2">
												<c:if test="${sourceBean.mixtureSource == 'Y'}">
													<c:choose>
														<c:when
															test="${!empty kitData.mixtureVocLwesSource}">
															<c:choose>
																<c:when
																	test="${sourceBean.dataSource eq kitData.mixtureVocLwesSource}">
																	<option value="${sourceBean.dataSource}"
																		selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when
																	test="${sourceBean.dataSource eq 'MSDS'}">
																	<option value="${sourceBean.dataSource}"
																		selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>
												</c:if>
											</c:forEach>
									</select></td>
								</tr>
							</table>
						</fieldset>
					</td>
				</tr>
			</table>
			</div>
			<div class="roundbottom">
				<div class="roundbottomright">
					<img src="/images/rndBoxes/borderBL.gif" alt="" width="15"
						height="15" class="corner" style="display: none" />
				</div>
			</div>
		</div>
	</div>
</c:if>