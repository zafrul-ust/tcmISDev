	    <c:if test="${resultsCollection[0].allowMixture && fn:length(resultsCollection) > 1}">
	    	<c:set var="kitData" value="${resultsCollection[0]}"/>
				<table id="kitTable" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr><td><div class="roundcont filterContainer"><div class="roundright"><div class="roundtop"><div class="roundtopright">
											<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
										</div></div><div class="roundContent">
										<div class="boxhead"><fmt:message key="label.kit"/></div>
										<table width="100%" class="tableSearch" id="kitTable1" border="0" cellpadding="2" cellspacing="0">
										<tr><td width="0%" class="optionTitleLeft" valign="top">
											<table width="100%" class="tableSearch" HEIGHT="145" id="kitDataTable1" border="0" cellpadding="0" cellspacing="0">
												<tr><td><table><tr><TD width="5%" class="optionTitleLeft"" nowrap>
													<FIELDSET><table width="100%" class="tableSearch" id="kitDataTable2" border="0" cellpadding="0" cellspacing="0">
																<tr>
                                                                    <TD width="40%" class="optionTitleBoldRight"><fmt:message key="label.description" />&nbsp;:</TD>
																	<TD width="60%" class="optionTitleLeft"><input class="inputBox" type="text" name="msds[0].mixtureDesc" id="msds[0].mixtureDesc" size="60" maxlength="30" value="${kitData.mixtureDesc}"></TD>
																</tr><tr>
																	<td class="optionTitleBoldRight"><fmt:message key="label.manufacturer"/>:</td>
																	<td  class="optionTitleLeft"><input class="inputBox" type="text" name="msds[0].mixtureManufacturer" id="msds[0].mixtureManufacturer" size="60" maxlength="60" value="${kitData.mixtureManufacturer}" ></td>
																</tr><tr>
																	<td class="optionTitleBoldRight" ><fmt:message key="label.id"/>:</td>
																	<td><input class="inputBox" type="hidden" name="msds[0].mixtureMfgId" id="msds[0].mixtureMfgId" value="${kitData.mixtureMfgId}">
																		<table width="50%" border="0" cellpadding="0" cellspacing="0">
																			<tr>
																				<td width="50%" align="right"><span id="msds[0].mixtureMfgId_disp">${kitData.mixtureMfgId}</span></td>
																				<td align="left"><input name="searchForMfg" id="searchForMfg" type="button" value="..." class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'"  onclick="lookupManufacturer(true);"></td>
																			</tr></table></td>
																</tr><tr>
																	<TD width="40%" class="optionTitleBoldRight"><fmt:message key="report.label.productCode" />&nbsp;:</TD>
																	<TD width="60%" class="optionTitleLeft"><input class="inputBox" type="text" name="msds[0].mixtureProductCode" id="msds[0].mixtureProductCode" size="20" maxlength="20" value="${kitData.mixtureProductCode}"></TD>
																</tr><tr>
																	<TD width="40%" class="optionTitleBoldRight"><fmt:message key="label.physicalstate" />&nbsp;:</TD>
																	<TD width="60%" class="optionTitleLeft">
																		<select name="msds[0].mixturePhysicalState" id="msds[0].mixturePhysicalState" class="selectBox" onchange="checkDataEntryComplete()">    			
																		<option value=""></option>
																		<option value="gas"<c:if test='${"gas" eq kitData.mixturePhysicalState}'> selected</c:if>><fmt:message key="label.gaslower"/></option>
																		<OPTION VALUE="liquid"<c:if test='${"liquid" eq kitData.mixturePhysicalState}'> selected</c:if>><fmt:message key="label.liquid"/></OPTION>
																		<OPTION VALUE="semi-solid"<c:if test='${"semi-solid" eq kitData.mixturePhysicalState}'> selected</c:if>><fmt:message key="label.semi-solid"/></OPTION>
																		<OPTION VALUE="solid"<c:if test='${"solid" eq kitData.mixturePhysicalState}'> selected</c:if>><fmt:message key="label.solid"/></OPTION>
																	</select></TD>
															</tr><tr>
																	<TD width="40%" class="optionTitleBoldRight"><fmt:message key="label.source" />&nbsp;:</TD>
																	<TD width="60%" class="optionTitleLeft">																										
																			<select name="msds[0].mixturePhysicalState" id="msds[0].mixturePhysicalState" class="selectBox">    
																				<option value=""></option>			
																				<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status2">
																					<c:choose>
																						<c:when test="${!empty kitData.mixturePhysicalStateSource}">
																							<c:choose><c:when test="${sourceBean.dataSource eq kitData.mixturePhysicalStateSource}">
																									<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																								</c:when><c:otherwise>
																									<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																								</c:otherwise>
																							</c:choose></c:when><c:otherwise><c:choose>
																								<c:when test="${sourceBean.dataSource eq 'MSDS'}">
																									<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																								</c:when><c:otherwise>
																									<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																								</c:otherwise></c:choose></c:otherwise></c:choose></c:forEach></select></TD></tr>
															<TR><TD width="30%" class="optionTitleBoldRight">&nbsp;</TD>
																<TD width="70%" class="optionTitleLeft" >
																	<input name="msds[0].mixtureViewFile" id="msds[0].mixtureViewFile" type="button" value="<fmt:message key="label.viewfile"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="viewKitDocs(); return false;">
																	<input name="msds[0].mixtureUploadFile" id="msds[0].mixtureUploadFile" type="button" value="<fmt:message key="label.uploadfiles"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="uploadKitDocs(); return false;">								
																</TD></tr></table></FIELDSET></TD>
													<TD width="5%" class="optionTitleLeft"" nowrap>
														<FIELDSET>
																<LEGEND class="optionTitleBold"><fmt:message key="label.voc" /> &nbsp;</LEGEND>
																<table width="100%" class="tableSearch" id="kitDataTable3" border="0" cellpadding="0" cellspacing="0">
																	<tr>
																		<TD width="40%" class="optionTitleBoldRight"><fmt:message key="label.value" />&nbsp;:</TD>
																		<TD width="60%" class="optionTitleLeft"><input class="inputBox" type="text" name="msds[0].mixtureVoc" id="msds[0].mixtureVoc" size="20" maxlength="20" value="${kitData.mixtureVoc}"></TD>
																	</tr><tr>
																		<TD width="40%" class="optionTitleBoldRight"><fmt:message key="label.lower" />&nbsp;:</TD>
																		<TD width="60%" class="optionTitleLeft"><input class="inputBox" type="text" name="msds[0].mixtureVocLower" id="msds[0].mixtureVocLower" size="20" maxlength="20" value="${kitData.mixtureVocLower}"></TD>
																	</tr><tr>
																		<TD width="40%" class="optionTitleBoldRight"><fmt:message key="label.upper" />&nbsp;:</TD>
																		<TD width="60%" class="optionTitleLeft"><input class="inputBox" type="text" name="msds[0].mixtureVocUpper" id="msds[0].mixtureVocUpper" size="20" maxlength="20" value="${kitData.mixtureVocUpper}"></TD>
																	</tr><tr>
																		<TD width="40%" class="optionTitleBoldRight"><fmt:message key="label.units" />&nbsp;:</TD>
																		<TD width="60%" class="optionTitleLeft">
																			<select name="msds[0].mixtureVocUnit" id="msds[0].mixtureVocUnit" class="selectBox">
																				<option value="" <c:if test="${empty kitData.mixtureVocUnit}"> selected</c:if>></option>
																				<option value="%(w/w)" <c:if test='${"%(w/w)" eq kitData.mixtureVocUnit}'> selected</c:if>>%(w/w)</option>
																				<option value="g/L" <c:if test='${"g/L" eq kitData.mixtureVocUnit}'> selected</c:if>>g/L</option>
																				<option value="lb/gal" <c:if test='${"lb/gal" eq kitData.mixtureVocUnit}'> selected</c:if>>lb/gal</option>
																		</select></TD></tr><tr>
																		<TD width="40%" class="optionTitleBoldRight"><fmt:message key="label.source" />&nbsp;:</TD>
																<TD width="60%" class="optionTitleLeft">
																<select name="msds[0].mixtureVocSource" id="msds[0].mixtureVocSource" class="selectBox">
																	<option value=""></option>
																	<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status2">
																	<c:if test="${sourceBean.mixtureSource == 'Y'}">
																			<c:choose><c:when test="${!empty kitData.mixtureVocSource}">
																					<c:choose><c:when test="${sourceBean.dataSource eq kitData.mixtureVocSource}">
																							<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																						</c:when><c:otherwise>
																							<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																						</c:otherwise></c:choose>
																				</c:when><c:otherwise><c:choose>
																						<c:when test="${sourceBean.dataSource eq 'MSDS'}">
																							<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																						</c:when><c:otherwise>
																							<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																						</c:otherwise></c:choose></c:otherwise></c:choose>
																	</c:if></c:forEach></select></TD></tr></table></FIELDSET></TD>
													<td width="6%" class="optionTitleLeft">
														<FIELDSET>
														<LEGEND class="optionTitleBold"><fmt:message key="label.voclesswaterandexempt"/></LEGEND>
														<table width="100%" class="tableSearch" id="kitDataTable4" border="0" cellpadding="0" cellspacing="0">
															<tr>
																<TD width="40%" class="optionTitleBoldRight"><fmt:message key="label.value"/>&nbsp;:</TD>
																<TD width="60%" class="optionTitleLeft" ><input class="inputBox" type="text" name="msds[0].mixtureVocLwes" id="msds[0].mixtureVocLwes" size="20" maxlength="20" value="${kitData.mixtureVocLwes}"></TD>
															</tr><tr>
																<TD width="40%" class="optionTitleBoldRight"><fmt:message key="label.lower"/>&nbsp;:</TD>
																<TD width="60%" class="optionTitleLeft" ><input class="inputBox" type="text" name="msds[0].mixtureVocLwesLower" id="msds[0].mixtureVocLwesLower" size="20" maxlength="20" value="${kitData.mixtureVocLwesLower}"></TD>
															</tr><tr>
																<TD width="40%" class="optionTitleBoldRight"><fmt:message key="label.upper"/>&nbsp;:</TD>
																<TD width="60%" class="optionTitleLeft" ><input class="inputBox" type="text" name="msds[0].mixtureVocLwesUpper" id="msds[0].mixtureVocLwesUpper" size="20" maxlength="20" value="${kitData.mixtureVocLwesUpper}"></TD>
															</tr><tr>
																<TD width="40%" class="optionTitleBoldRight"><fmt:message key="label.units"/>&nbsp;:</TD>
																<TD width="60%" class="optionTitleLeft" >
																	 <select name="msds[0].mixtureVocLwesUnit" id="msds[0].mixtureVocLwesUnit" class="selectBox">    			
																		<option value=""<c:if test="${empty kitData.mixtureVocLwesUnit}"> selected</c:if>></option>
																		<option value="g/L"<c:if test='${"g/L" eq kitData.mixtureVocLwesUnit}'> selected</c:if>>g/L</option>
																		<option value="lb/gal"<c:if test='${"lb/gal" eq kitData.mixtureVocLwesUnit}'> selected</c:if>>lb/gal</option>
																	</select></TD>
															</tr><tr>
																<TD width="40%" class="optionTitleBoldRight"><fmt:message key="label.source"/>&nbsp;:</TD>
																<TD width="60%" class="optionTitleLeft" >
																	 <select name="msds[0].mixtureVocLwesSource" id="msds[0].mixtureVocLwesSource" class="selectBox">    			
																		<option value=""<c:if test="${empty kitData.mixtureVocLwesSource}"> selected</c:if>></option>			
																			<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="status2">
																			<c:if test="${sourceBean.mixtureSource == 'Y'}">
																					<c:choose><c:when test="${!empty kitData.mixtureVocLwesSource}">
																							<c:choose><c:when test="${sourceBean.dataSource eq kitData.mixtureVocLwesSource}">
																									<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																								</c:when><c:otherwise>
																									<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																								</c:otherwise></c:choose>
																						</c:when><c:otherwise><c:choose>
																								<c:when test="${sourceBean.dataSource eq 'MSDS'}">
																									<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																								</c:when><c:otherwise>
																									<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																								</c:otherwise></c:choose></c:otherwise></c:choose>
																			</c:if>								
																			</c:forEach></select></TD></tr></table></FIELDSET></td><td width="10%"></td>
													</tr></table></td></tr></table></td></tr></table>
								</div><div class="roundbottom"><div class="roundbottomright">
										<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
									</div></div></div></div></td></tr></table></c:if>