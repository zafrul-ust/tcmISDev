<script type="text/javascript" src="/js/catalog/customeroverride.js"></script>
<script type="text/javascript">
	/*
		Use selectedItemTab for pages with multiple MSDS tabs
		It is initialized to 1 and will remain as 1 for pages that display only one MSDS
	*/
	if (selectedItemTab == undefined)
		var selectedItemTab = 1;
</script>
<div id="customerOverrideGridDiv${index}" class="roundcont contentContainer" style="width=100%;">
		<div class="roundright">
			<div class="roundtop">
				<div class="roundtopright">
					<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
				</div>
			</div>
			<div class="roundContent">
				<div class="boxhead"> 
					<c:choose>
						<c:when test="${'Y' == param.fromMsdsInfo}">
							<A HREF="#" onClick="updateByCustomer();"><U><fmt:message key="label.update"/></U></A>
						</c:when>
						<c:otherwise>
							<fmt:message key="label.customeroverridemsdsqc"/>
						</c:otherwise>
					</c:choose>
				</div>
				<table width="100%" class="tableSearch" id="coMsdsDataTable${index+1}" border="0" cellpadding="0" cellspacing="0"   style="float: left;">
					<tr>
						<td width="25%" class="optionTitleLeft" valign="top">
							<FIELDSET>
								<LEGEND class="optionTitleBold"><fmt:message key="label.customer"/></LEGEND>
								<table width="100%" class="tableSearch" id="msdsDataTable" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="30%" class="optionTitleBoldRight">
											<fmt:message key="label.company"/>&nbsp;:
										</td>
										<td width="70%" class="optionTitleLeft" >
											<c:choose>
												<c:when test="${not empty chemRequest}">
													${chemRequest.companyName}
                                                </c:when>
												<c:when test="${'Y' == param.fromMsdsInfo}">
													${personnelBean.companyName}
                                                </c:when>
												<c:otherwise>
													<select name="msds[${index}].customerCompanies" id="msds[${index}].customerCompanies" class="selectBox" onchange="customerCompanyChanged(this.options[this.selectedIndex].value, true);">    			
														<c:forEach var="customerCompany" items="${customerCompanies}" varStatus="coStatus">
															<OPTION  VALUE="${customerCompany.companyId}"<c:if test='${customerCompany.companyId eq curCustCompany}'> selected</c:if>>${customerCompany.companyName}</OPTION>
														</c:forEach>
													</select>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td width="100%" class="optionTitleBoldLeft" colspan="2">

                                            <c:set var="disabledThisField" value=""/>
                                            <c:if test="${disabledCoDataEntryComplete == 'Y'}">
                                                <c:set var="disabledThisField">disabled="disabled"</c:set>
                                            </c:if>

                                            <c:choose>
												<c:when test="${item.coDataEntryComplete}">
													<input class="radio" ${disabledThisField} type="checkbox" name="msds[${index}].coDataEntryComplete" id="msds[${index}].coDataEntryComplete" checked="checked" value="Y" onclick="setCoDataEntryComplete()" onchange="customerOverrideChanged()"/>
												</c:when>
												<c:otherwise>
													<input class="radio" ${disabledThisField} type="checkbox" name="msds[${index}].coDataEntryComplete" id="msds[${index}].coDataEntryComplete"  value="N" onclick="setCoDataEntryComplete()" onchange="customerOverrideChanged()">					                                            
												</c:otherwise>
											</c:choose>
                                            <fmt:message key="label.dataentrycomplete"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="100%" class="optionTitleBoldLeft" colspan="2">
                                            <input name="attachedCoDocuments" id="attachedCoDocuments" type="button" value="<fmt:message key="label.additionaldocuments"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="showCoMsdsDocuments(); return false;">
                                        </td>   
                                    </tr>
                                </table>
							</FIELDSET>
							<c:if test="${'Y' == param.fromMsdsInfo}">
								<FIELDSET>
								<LEGEND class="optionTitleBold"><fmt:message key="label.material"/></LEGEND>
	                                <table width="100%" class="tableSearch" id="msdsDataTable" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="40%" class="optionTitleBoldRight" nowrap>
												<fmt:message key="report.label.productCode"/>&nbsp;:
	                                        </td>
	                                        <td width="60%" class="optionTitleLeft" >
	                                        	<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coProductCode" id="msds[${index}].coProductCode" size="20" maxlength="30" value="${item.coProductCode}">
											</td>
	                                   </tr>
	                                </table>
	                            </FIELDSET>
							</c:if>                            
                        </td>
						<td width="25%" class="optionTitleLeft" valign="top">
							<FIELDSET>
								<LEGEND class="optionTitleBold"><fmt:message key="label.nfpa"/></LEGEND>
								<table width="100%" class="tableSearch" id="msdsDataTable" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.health"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="checkCODataEntryComplete(); customerOverrideChanged()" type="text" name="msds[${index}].coHealth" id="msds[${index}].coHealth" size="20" maxlength="30" value="${item.coHealth}">
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.flammability"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="checkCODataEntryComplete(); customerOverrideChanged()" type="text" name="msds[${index}].coFlammability" id="msds[${index}].coFlammability" size="20" maxlength="30" value="${item.coFlammability}">
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.reactivity"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="checkCODataEntryComplete(); customerOverrideChanged()" type="text" name="msds[${index}].coReactivity" id="msds[${index}].coReactivity" size="20" maxlength="30" value="${item.coReactivity}">
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.special"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											 <select onchange="customerOverrideChanged()" name="msds[${index}].coSpecificHazard" id="msds[${index}].coSpecificHazard" class="selectBox">    			
												<option value=""<c:if test="${empty item.coSpecificHazard}"> selected</c:if>></option>
												<OPTION  VALUE="ACID"<c:if test='${"ACID" eq item.coSpecificHazard}'> selected</c:if>>Acid</OPTION>
												<OPTION  VALUE="ALK"<c:if test='${"ALK" eq item.coSpecificHazard}'> selected</c:if>>Alkali</OPTION>
												<OPTION  VALUE="COR"<c:if test='${"COR" eq item.coSpecificHazard}'> selected</c:if>>Corrosive</OPTION>
												<OPTION  VALUE="OXY"<c:if test='${"OXY" eq item.coSpecificHazard}'> selected</c:if>>Oxidizer</OPTION>
												<OPTION  VALUE="RAD"<c:if test='${"RAD" eq item.coSpecificHazard}'> selected</c:if>>Radiation Hazard</OPTION>
												<OPTION  VALUE="SA"<c:if test='${"SA" eq item.coSpecificHazard}'> selected</c:if>>Simple Asphyxiant Gas</OPTION>
												<OPTION  VALUE="W"<c:if test='${"W" eq item.coSpecificHazard}'> selected</c:if>>Use No Water</OPTION>
											</select>
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.source"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
										<select onchange="customerOverrideChanged()" name="msds[${index}].coNfpaSource" id="msds[${index}].coNfpaSource" class="selectBox">    
												<option value=""></option>			
												<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="coStatus">
													<c:choose>
														<c:when test="${!empty item.coNfpaSource}">
															<c:choose>
																<c:when test="${sourceBean.dataSource eq item.coNfpaSource}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${sourceBean.dataSource eq 'MSDS'}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>										
												</c:forEach>
										</select>				
										</td>
									</tr>
								</table>
							</FIELDSET>
						</td>
						<td width="25%" class="optionTitleLeft" valign="top">
							<FIELDSET>
								<LEGEND class="optionTitleBold"><fmt:message key="label.hmis"/></LEGEND>
								<table width="100%" class="tableSearch" id="msdsDataTable" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.health"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="checkCODataEntryComplete(); customerOverrideChanged()" type="text" name="msds[${index}].coHmisHealth" id="msds[${index}].coHmisHealth" size="20" maxlength="30" value="${item.coHmisHealth}">
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.flammability"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="checkCODataEntryComplete(); customerOverrideChanged()" type="text" name="msds[${index}].coHmisFlammability" id="msds[${index}].coHmisFlammability" size="20" maxlength="30" value="${item.coHmisFlammability}">
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.reactivity"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="checkCODataEntryComplete(); customerOverrideChanged()" type="text" name="msds[${index}].coHmisReactivity" id="msds[${index}].coHmisReactivity" size="20" maxlength="30" value="${item.coHmisReactivity}">
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.personalProtection"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											 <select onchange="customerOverrideChanged()" name="msds[${index}].coPersonalProtection" id="msds[${index}].coPersonalProtection" class="selectBox">    			
												<option value=""<c:if test="${empty item.coPersonalProtection}"> selected</c:if>></option>
												<OPTION  VALUE="A"<c:if test='${"A" eq item.coPersonalProtection}'> selected</c:if>>A</OPTION>
												<OPTION  VALUE="B"<c:if test='${"B" eq item.coPersonalProtection}'> selected</c:if>>B</OPTION>
												<OPTION  VALUE="C"<c:if test='${"C" eq item.coPersonalProtection}'> selected</c:if>>C</OPTION>
												<OPTION  VALUE="D"<c:if test='${"D" eq item.coPersonalProtection}'> selected</c:if>>D</OPTION>
												<OPTION  VALUE="E"<c:if test='${"E" eq item.coPersonalProtection}'> selected</c:if>>E</OPTION>
												<OPTION  VALUE="F"<c:if test='${"F" eq item.coPersonalProtection}'> selected</c:if>>F</OPTION>
												<OPTION  VALUE="G"<c:if test='${"G" eq item.coPersonalProtection}'> selected</c:if>>G</OPTION>
												<OPTION  VALUE="H"<c:if test='${"H" eq item.coPersonalProtection}'> selected</c:if>>H</OPTION>
												<OPTION  VALUE="I"<c:if test='${"I" eq item.coPersonalProtection}'> selected</c:if>>I</OPTION>
												<OPTION  VALUE="J"<c:if test='${"J" eq item.coPersonalProtection}'> selected</c:if>>J</OPTION>
												<OPTION  VALUE="K"<c:if test='${"K" eq item.coPersonalProtection}'> selected</c:if>>K</OPTION>
												<OPTION  VALUE="X"<c:if test='${"X" eq item.coPersonalProtection}'> selected</c:if>>X</OPTION>
											</select>
										</td>
									</tr>
									<TR>
										<td width="30%" class="optionTitleBoldRight">
											<fmt:message key="label.chronic"/>&nbsp;:
										</td>
										<td width="70%" class="optionTitleLeft" >
											<input onclick="customerOverrideChanged()" name="msds[${index}].coHmisChronic" id="msds[${index}].coHmisChronicY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coHmisChronic eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
											<input onclick="customerOverrideChanged()" name="msds[${index}].coHmisChronic" id="msds[${index}].coHmisChronicN" type="radio" class="radioBtns" value="N"<c:if test="${item.coHmisChronic eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
											<input onclick="customerOverrideChanged()" name="msds[${index}].coHmisChronic" id="msds[${index}].coHmisChronicX" type="radio" class="radioBtns" value="X"<c:if test="${item.coHmisChronic eq 'X'}">checked</c:if>> <fmt:message key="label.na"/>
											&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coHmisChronic'); customerOverrideChanged()" type="button"/>
										</td>
									</TR>
											<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.source"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
										<select onchange="customerOverrideChanged()" name="msds[${index}].coHmisSource" id="msds[${index}].coHmisSource" class="selectBox">    
												<option value=""<c:if test="${empty item.coHmisSource}"> selected</c:if>></option>
												<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="coStatus">
													<c:choose>
														<c:when test="${!empty item.coHmisSource}">
															<c:choose>
																<c:when test="${sourceBean.dataSource eq item.coHmisSource}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${sourceBean.dataSource eq 'MSDS'}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>						
												</c:forEach>
										</select>				
										</td>
									</tr>
								</table>
							</FIELDSET>
						</td>
						<td valign="top">
						<table width="100%" class="tableSearch" border="0" cellpadding="0" cellspacing="0">
						<tr>
						<td width="25%" class="optionTitleLeft" valign="top">
							<FIELDSET>
								<LEGEND class="optionTitleBold"><fmt:message key="label.ph"/>&nbsp;</LEGEND>
								<table width="100%" class="tableSearch" id="msdsDataTable" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.detect"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
										<select name="msds[${index}].coPhDetect" id="msds[${index}].coPhDetect" class="selectBox" onchange="blockCODisplays('coPh'+'${index}',value);customerOverrideChanged()">    
												<option value=""<c:if test="${empty item.coPhDetect}"> selected</c:if>></option>			
												<c:forEach var="detectBean" items="${vvDetectBeanCollection}" varStatus="coStatus">
													<c:if test="${detectBean.flashPointOnly == 'N'}">
															<c:choose>
																<c:when test="${detectBean.detect eq item.coPhDetect}">
																	<option value="${detectBean.detect}" selected>${detectBean.description}</option>
																</c:when>
																<c:otherwise>
																	<option value="${detectBean.detect}">${detectBean.description}</option>
																</c:otherwise>
															</c:choose>	
														</c:if>
														<c:if test="${detectBean.flashPointOnly == 'Y' && isFlashDetect == 'Y'}">
															<c:choose>
																<c:when test="${detectBean.detect eq item.coPhDetect}">
																	<option value="${detectBean.detect}" selected>${detectBean.description}</option>
																</c:when>
																<c:otherwise>
																	<option value="${detectBean.detect}">${detectBean.description}</option>
																</c:otherwise>
															</c:choose>	
														</c:if>
												</c:forEach>
										</select>				
										</td>
									</tr>					
									<tr name="coPh${index}Value" style="display: none" overflow: auto;>
										<td width="40%" class="optionTitleBoldRight">
									
											<fmt:message key="label.value"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coPh" id="msds[${index}].coPh" size="20" maxlength="20" value="${item.coPh}">
										</td>
									</tr>
									<tr name="coPh${index}Limit" style="display: none" overflow: auto;>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.upper"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coPhUpper" id="msds[${index}].coPhUpper" size="20" maxlength="20" value="${item.coPhUpper}">
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.detail"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coPhDetail" id="msds[${index}].coPhDetail" size="20" onKeyUp="limitText(this,50);" value="${item.coPhDetail}">
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.source"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
										<select onchange="customerOverrideChanged()" name="msds[${index}].coPhSource" id="msds[${index}].coPhSource" class="selectBox">    
												<option value=""<c:if test="${empty item.coPhSource}"> selected</c:if>></option>			
												<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="coStatus">
													<c:choose>
														<c:when test="${!empty item.coPhSource}">
															<c:choose>
																<c:when test="${sourceBean.dataSource eq item.coPhSource}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${sourceBean.dataSource eq 'MSDS'}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>											
												</c:forEach>
										</select>				
										</td>
									</tr>
								</table>
							</FIELDSET>
						</td>
					</tr>
					<tr> 
					<td width="25%" class="optionTitleLeft" valign="top">
							<FIELDSET>
								<LEGEND class="optionTitleBold"><fmt:message key="label.physicalstate"/>&nbsp;</LEGEND>
								<table width="100%" class="tableSearch" id="msdsDataTable" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.physicalstate"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<select name="msds[${index}].coPhysicalState" id="msds[${index}].coPhysicalState" class="selectBox" onchange="setCoSpecGrav(value); customerOverrideChanged()">    			
												<option value=""<c:if test="${empty item.coPhysicalState}"> selected</c:if>></option>
												<option value="gas"<c:if test='${"gas" eq item.coPhysicalState}'> selected</c:if>><fmt:message key="label.gaslower"/></option>
												<OPTION VALUE="liquid"<c:if test='${"liquid" eq item.coPhysicalState}'> selected</c:if>><fmt:message key="label.liquid"/></OPTION>
												<OPTION VALUE="liquid/aerosol"<c:if test='${"liquid/aerosol" eq item.coPhysicalState}'> selected</c:if>><fmt:message key="label.liquid/aerosol"/></OPTION>
												<OPTION VALUE="cryogenic liquid"<c:if test='${"cryogenic liquid" eq item.coPhysicalState}'> selected</c:if>><fmt:message key="label.cryogenicliquid"/></OPTION>
												<OPTION VALUE="semi-solid"<c:if test='${"semi-solid" eq item.coPhysicalState}'> selected</c:if>><fmt:message key="label.semi-solid"/></OPTION>
												<OPTION VALUE="solid"<c:if test='${"solid" eq item.coPhysicalState}'> selected</c:if>><fmt:message key="label.solid"/></OPTION>
											</select>
										</td>
									</tr>
									<tr>
										<TD width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.source"/>&nbsp;:
										</TD>
										<TD width="60%" class="optionTitleLeft" >
										<select name="msds[${index}].coPhysicalStateSource" id="msds[${index}].coPhysicalStateSource" class="selectBox">    
												<option value=""></option>			
												<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="coStatus">
													<c:choose>
														<c:when test="${!empty item.coPhysicalStateSource}">
															<c:choose>
																<c:when test="${sourceBean.dataSource eq item.coPhysicalStateSource}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${sourceBean.dataSource eq 'MSDS'}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>									
												</c:forEach>
										</select>				
										</TD>
									</tr>
								</table>
							</FIELDSET>
						</td>
				</tr>
				</table>
				</td>
				</tr>
					<tr>
					<td width="25%" class="optionTitleLeft" valign="top">
							<FIELDSET>
								<LEGEND class="optionTitleBold"><fmt:message key="label.density"/>&nbsp;</LEGEND>
								<table width="100%" class="tableSearch" id="msdsDataTable" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.detect"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
										<select name="msds[${index}].coDensityDetect" id="msds[${index}].coDensityDetect" class="selectBox" onchange="blockCODisplays('coDensity'+'${index}',value);customerOverrideChanged()">    
												<option value=""<c:if test="${empty item.coDensityDetect}"> selected</c:if>></option>			
												<c:forEach var="detectBean" items="${vvDetectBeanCollection}" varStatus="coStatus">
													<c:if test="${detectBean.flashPointOnly == 'N'}">
															<c:choose>
																<c:when test="${detectBean.detect eq item.coDensityDetect}">
																	<option value="${detectBean.detect}" selected>${detectBean.description}</option>
																</c:when>
																<c:otherwise>
																	<option value="${detectBean.detect}">${detectBean.description}</option>
																</c:otherwise>
															</c:choose>	
														</c:if>
														<c:if test="${detectBean.flashPointOnly == 'Y' && isFlashDetect == 'Y'}">
															<c:choose>
																<c:when test="${detectBean.detect eq item.coDensityDetect}">
																	<option value="${detectBean.detect}" selected>${detectBean.description}</option>
																</c:when>
																<c:otherwise>
																	<option value="${detectBean.detect}">${detectBean.description}</option>
																</c:otherwise>
															</c:choose>	
														</c:if>	
												</c:forEach>
										</select>				
										</td>
									</tr>		
									<tr name="coDensity${index}Value" style="display: none" overflow: auto;>
										<td width="40%" class="optionTitleBoldRight">
												<fmt:message key="label.value"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coDensity" id="msds[${index}].coDensity" size="20" maxlength="20" value="${item.coDensity}">
										</td>
									</tr>	
									<tr name="coDensity${index}Limit" style="display: none" overflow: auto;>	
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.upper"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coDensityUpper" id="msds[${index}].coDensityUpper" size="20" maxlength="20" value="${item.coDensityUpper}">
										</td>
									</tr>
									<tr name="coDensity${index}Value" style="display: none" overflow: auto;>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.units"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											 <select onchange="customerOverrideChanged()" name="msds[${index}].coDensityUnit" id="msds[${index}].coDensityUnit" class="selectBox">    			
												<option value=""<c:if test="${empty item.coDensityUnit}"> selected</c:if>></option>			
												<option value="g/L"<c:if test='${"g/L" eq item.coDensityUnit}'> selected</c:if>>g/L</option>
												<OPTION VALUE="g/cm3"<c:if test='${"g/cm3" eq item.coDensityUnit}'> selected</c:if>>g/cm&sup3;</OPTION>
												<OPTION VALUE="g/mL"<c:if test='${"g/mL" eq item.coDensityUnit}'> selected</c:if>>g/mL</OPTION>
												<OPTION VALUE="kg/L"<c:if test='${"kg/L" eq item.coDensityUnit}'> selected</c:if>>kg/L</OPTION>
												<OPTION VALUE="kg/m3"<c:if test='${"kg/m3" eq item.coDensityUnit}'> selected</c:if>>kg/m&sup3;</OPTION>
												<OPTION VALUE="lb/ft3"<c:if test='${"lb/ft3" eq item.coDensityUnit}'> selected</c:if>>lb/ft&sup3;</OPTION>
												<OPTION VALUE="lb/gal"<c:if test='${"lb/gal" eq item.coDensityUnit}'> selected</c:if>>lb/gal</OPTION>
												<OPTION VALUE="lb/in3"<c:if test='${"lb/in3" eq item.coDensityUnit}'> selected</c:if>>lb/in&sup3;</OPTION>
											</select>
										</td>
									</tr>	
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.source"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
										<select onchange="customerOverrideChanged()" name="msds[${index}].coDensitySource" id="msds[${index}].coDensitySource" class="selectBox">    
												<option value=""<c:if test="${empty item.coDensitySource}"> selected</c:if>></option>			
												<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="coStatus">
													<c:choose>
														<c:when test="${!empty item.coDensitySource}">
															<c:choose>
																<c:when test="${sourceBean.dataSource eq item.coDensitySource}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${sourceBean.dataSource eq 'MSDS'}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>										
												</c:forEach>
										</select>				
										</td>
									</tr>	
								</table>
							</FIELDSET>
						</td>
						<td width="25%" class="optionTitleLeft" valign="top">
							<FIELDSET>
								<LEGEND class="optionTitleBold"><fmt:message key="label.flashpoint"/>&nbsp;</LEGEND>
								<table width="100%" class="tableSearch" id="msdsDataTable" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.detect"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
										<c:set var="isFlashDetect" value='Y'/>
										<select name="msds[${index}].coFlashPointDetect" id="msds[${index}].coFlashPointDetect" class="selectBox" onchange="blockCODisplays('coFlashPoint'+'${index}',value); customerOverrideChanged()">    
												<option value=""<c:if test="${empty item.coFlashPointDetect}"> selected</c:if>></option>			
												<c:forEach var="detectBean" items="${vvDetectBeanCollection}" varStatus="coStatus">
													<c:if test="${detectBean.flashPointOnly == 'N'}">
															<c:choose>
																<c:when test="${detectBean.detect eq item.coFlashPointDetect}">
																	<option value="${detectBean.detect}" selected>${detectBean.description}</option>
																</c:when>
																<c:otherwise>
																	<option value="${detectBean.detect}">${detectBean.description}</option>
																</c:otherwise>
															</c:choose>	
														</c:if>
														<c:if test="${detectBean.flashPointOnly == 'Y' && isFlashDetect == 'Y'}">
															<c:choose>
																<c:when test="${detectBean.detect eq item.coFlashPointDetect}">
																	<option value="${detectBean.detect}" selected>${detectBean.description}</option>
																</c:when>
																<c:otherwise>
																	<option value="${detectBean.detect}">${detectBean.description}</option>
																</c:otherwise>
															</c:choose>	
														</c:if>
												</c:forEach>
										</select>	
										<c:set var="isFlashDetect" value='N'/>			
										</td>
									</tr>
									<tr name="coFlashPoint${index}Value" style="display: none" overflow: auto;>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.value"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coFlashPointLower" id="msds[${index}].coFlashPointLower" size="20" maxlength="20" value="${item.coFlashPointLower}">
										</td>
									</tr>		
									<tr name="coFlashPoint${index}Limit" style="display: none" overflow: auto;>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.upper"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coFlashPointUpper" id="msds[${index}].coFlashPointUpper" size="20" maxlength="20" value="${item.coFlashPointUpper}">
										</td>
									</tr>
									<tr name="coFlashPoint${index}Value" style="display: none" overflow: auto;>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.units"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											 <select onchange="customerOverrideChanged()" name="msds[${index}].coFlashPointUnit" id="msds[${index}].coFlashPointUnit" class="selectBox">
												<option value=""<c:if test="${empty item.coFlashPointUnit}"> selected</c:if>></option>
												<option value="C"<c:if test='${"C" eq item.coFlashPointUnit}'> selected</c:if>>&deg;<fmt:message key="label.cforcelsius"/></option>
												<option value="F"<c:if test='${"F" eq item.coFlashPointUnit}'> selected</c:if>>&deg;<fmt:message key="label.fforfahrenheit"/></option>
											</select>
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.method"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
										<select onchange="customerOverrideChanged()" name="msds[${index}].coFlashPointMethod" id="msds[${index}].coFlashPointMethod" class="selectBox">    
											<option value=""<c:if test="${empty item.coFlashPointMethod}"> selected</c:if>></option>
												<c:forEach var="detectBean" items="${vvFlashPointMethodCollection}" varStatus="coStatus">
													<c:choose>
														<c:when test="${detectBean.flashPointMethod eq item.coFlashPointMethod}">
															<option value="${detectBean.flashPointMethod}" selected>${detectBean.methodName}</option>
														</c:when>
														<c:otherwise>
															<option value="${detectBean.flashPointMethod}">${detectBean.methodName}</option>
														</c:otherwise>
													</c:choose>										
												</c:forEach>			
										</select>			
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.source"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
										<select onchange="customerOverrideChanged()" name="msds[${index}].coFlashPointSource" id="msds[${index}].coFlashPointSource" class="selectBox">    
												<option value=""<c:if test="${empty item.coFlashPointSource}"> selected</c:if>></option>			
												<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="coStatus">
													<c:choose>
														<c:when test="${!empty item.coFlashPointSource}">
															<c:choose>
																<c:when test="${sourceBean.dataSource eq item.coFlashPointSource}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${sourceBean.dataSource eq 'MSDS'}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>										
												</c:forEach>
										</select>				
										</td>
									</tr>
								</table>
							</FIELDSET>
						</td>
						<td width="25%" class="optionTitleLeft" valign="top">
							<FIELDSET>
								<LEGEND class="optionTitleBold"><fmt:message key="label.boilingpoint"/>&nbsp;</LEGEND>
								<table width="100%" class="tableSearch" id="msdsDataTable" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.detect"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
										<select name="msds[${index}].coBoilingPointDetect" id="msds[${index}].coBoilingPointDetect" class="selectBox" onchange="blockCODisplays('coBoilingPoint'+'${index}',value);customerOverrideChanged()">    
												<option value=""<c:if test="${empty item.coBoilingPointDetect}"> selected</c:if>></option>
												<c:forEach var="detectBean" items="${vvDetectBeanCollection}" varStatus="coStatus">
													<c:if test="${detectBean.flashPointOnly == 'N'}">
															<c:choose>
																<c:when test="${detectBean.detect eq item.coBoilingPointDetect}">
																	<option value="${detectBean.detect}" selected>${detectBean.description}</option>
																</c:when>
																<c:otherwise>
																	<option value="${detectBean.detect}">${detectBean.description}</option>
																</c:otherwise>
															</c:choose>	
														</c:if>
														<c:if test="${detectBean.flashPointOnly == 'Y' && isFlashDetect == 'Y'}">
															<c:choose>
																<c:when test="${detectBean.detect eq item.coBoilingPointDetect}">
																	<option value="${detectBean.detect}" selected>${detectBean.description}</option>
																</c:when>
																<c:otherwise>
																	<option value="${detectBean.detect}">${detectBean.description}</option>
																</c:otherwise>
															</c:choose>	
														</c:if>
												</c:forEach>			
										</select>				
										</td>
									</tr>
									<tr name="coBoilingPoint${index}Value" style="display: none" overflow: auto;>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.value"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coBoilingPointLower" id="msds[${index}].coBoilingPointLower" size="20" maxlength="20" value="${item.coBoilingPointLower}">
										</td>
									</tr>					
									<tr name="coBoilingPoint${index}Limit" style="display: none" overflow: auto;>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.upper"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coBoilingPointUpper" id="msds[${index}].coBoilingPointUpper" size="20" maxlength="20" value="${item.coBoilingPointUpper}">
										</td>
									</tr>
									<tr name="coBoilingPoint${index}Value" style="display: none" overflow: auto;>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.units"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											 <select onchange="customerOverrideChanged()" name="msds[${index}].coBoilingPointUnit" id="msds[${index}].coBoilingPointUnit" class="selectBox">    			
												<option value=""<c:if test="${empty item.coBoilingPointUnit}"> selected</c:if>></option>
												<option value="C"<c:if test='${"C" eq item.coBoilingPointUnit}'> selected</c:if>>&deg;<fmt:message key="label.cforcelsius"/></option>
												<option value="F"<c:if test='${"F" eq item.coBoilingPointUnit}'> selected</c:if>>&deg;<fmt:message key="label.fforfahrenheit"/></option>
											</select>
										</td>
									</tr>	
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.detail"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input onchange="customerOverrideChanged()" class="inputBox" type="text" name="msds[${index}].coBoilingPointDetail" id="msds[${index}].coBoilingPointDetail" size="20" maxlength="20" value="${item.coBoilingPointDetail}">
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.source"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
										<select onchange="customerOverrideChanged()" name="msds[${index}].coBoilingPointSource" id="msds[${index}].coBoilingPointSource" class="selectBox">    
												<option value=""<c:if test="${empty item.coBoilingPointSource}"> selected</c:if>></option>			
												<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="coStatus">
													<c:choose>
														<c:when test="${!empty item.coBoilingPointSource}">
															<c:choose>
																<c:when test="${sourceBean.dataSource eq item.coBoilingPointSource}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${sourceBean.dataSource eq 'MSDS'}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>										
												</c:forEach>
										</select>				
										</td>
									</tr>										
								</table>
							</FIELDSET>
						</td>
						<td width="25%" class="optionTitleLeft" valign="top">
							<FIELDSET>
								<LEGEND class="optionTitleBold"><fmt:message key="label.voc"/>&nbsp;</LEGEND>
								<table width="100%" class="tableSearch" id="msdsDataTable" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.value"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coVoc" id="msds[${index}].coVoc" size="20" maxlength="20" value="${item.coVoc}">
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.lower"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coVocLower" id="msds[${index}].coVocLower" size="20" maxlength="20" value="${item.coVocLower}">
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.upper"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coVocUpper" id="msds[${index}].coVocUpper" size="20" maxlength="20" value="${item.coVocUpper}">
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.units"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											 <select onchange="customerOverrideChanged()" name="msds[${index}].coVocUnit" id="msds[${index}].coVocUnit" class="selectBox">    			
												<option value=""<c:if test="${empty item.coVocUnit}"> selected</c:if>></option>
												<option value="%(w/w)"<c:if test='${"%(w/w)" eq item.coVocUnit}'> selected</c:if>>%(w/w)</option>
												<option value="g/L"<c:if test='${"g/L" eq item.coVocUnit}'> selected</c:if>>g/L</option>
												<option value="lb/gal"<c:if test='${"lb/gal" eq item.coVocUnit}'> selected</c:if>>lb/gal</option>
											</select>
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.source"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
										<select onchange="customerOverrideChanged()" name="msds[${index}].coVocSource" id="msds[${index}].coVocSource" class="selectBox">    
												<option value=""<c:if test="${empty item.coVocSource}"> selected</c:if>></option>			
												<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="coStatus">
													<c:choose>
														<c:when test="${!empty item.coVocSource}">
															<c:choose>
																<c:when test="${sourceBean.dataSource eq item.coVocSource}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${sourceBean.dataSource eq 'MSDS'}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>									
												</c:forEach>
										</select>				
										</td>
									</tr>
								</table>
							</FIELDSET>
						</td>
					</tr>
					<tr>    
						<td width="25%" class="optionTitleLeft" valign="top">
							<FIELDSET>
								<LEGEND class="optionTitleBold"><fmt:message key="label.specificgravity"/></LEGEND>
								<table width="100%" class="tableSearch" id="msdsDataTable" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.detect"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
										<select name="msds[${index}].coSpecificGravityDetect" id="msds[${index}].coSpecificGravityDetect" class="selectBox" onchange="blockCODisplays('coSpecificGravity'+'${index}',value); customerOverrideChanged()">    
												<option value=""<c:if test="${empty item.coSpecificGravityDetect}"> selected</c:if>></option>			
												<c:forEach var="detectBean" items="${vvDetectBeanCollection}" varStatus="coStatus">
														<c:if test="${detectBean.flashPointOnly == 'N'}">
															<c:choose>
																<c:when test="${detectBean.detect eq item.coSpecificGravityDetect}">
																	<option value="${detectBean.detect}" selected>${detectBean.description}</option>
																</c:when>
																<c:otherwise>
																	<option value="${detectBean.detect}">${detectBean.description}</option>
																</c:otherwise>
															</c:choose>	
														</c:if>
														<c:if test="${detectBean.flashPointOnly == 'Y' && isFlashDetect == 'Y'}">
																<c:choose>
																<c:when test="${detectBean.detect eq item.coSpecificGravityDetect}">
																	<option value="${detectBean.detect}" selected>${detectBean.description}</option>
																</c:when>
																<c:otherwise>
																	<option value="${detectBean.detect}">${detectBean.description}</option>
																</c:otherwise>
															</c:choose>	
														</c:if>
												</c:forEach>
										</select>												
										</td>
									</tr>				
									<tr name="coSpecificGravity${index}Value" style="display: none" overflow: auto;>						
										<td width="40%" class="optionTitleBoldRight">
												<fmt:message key="label.value"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coSpecificGravityLower" id="msds[${index}].coSpecificGravityLower" size="20" maxlength="20" value="${item.coSpecificGravity}">
										</td>
									</tr>
									<tr name="coSpecificGravity${index}Limit" style="display: none" overflow: auto;>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.upper"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coSpecificGravityUpper" id="msds[${index}].coSpecificGravityUpper" size="20" maxlength="20" value="${item.coSpecificGravityUpper}">
										</td>
									</tr>
									<tr name="coSpecificGravity${index}Value" style="display: none" overflow: auto;>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.basis"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
										<c:choose>
											<c:when test="${item.coSpecificGravityBasis eq 'air'}">
												<input onclick="customerOverrideChanged()" name="msds[${index}].coSpecificGravityBasis" id="msds[${index}].coSpecificGravityBasisAir" type="radio" class="radioBtns" value="A" checked> <fmt:message key="label.air"/>
												<input onclick="customerOverrideChanged()" name="msds[${index}].coSpecificGravityBasis" id="msds[${index}].coSpecificGravityBasisWater" type="radio" class="radioBtns" value="W"> <fmt:message key="label.water"/>
											</c:when>
											<c:otherwise>
												<input onclick="customerOverrideChanged()"  name="msds[${index}].coSpecificGravityBasis" id="msds[${index}].coSpecificGravityBasisAir" type="radio" class="radioBtns" value="A"> <fmt:message key="label.air"/>
												<input onclick="customerOverrideChanged()" name="msds[${index}].coSpecificGravityBasis" id="msds[${index}].coSpecificGravityBasisWater" type="radio" class="radioBtns" value="W" checked> <fmt:message key="label.water"/>			
											</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.source"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
										<select onchange="customerOverrideChanged()" name="msds[${index}].coSpecificGravitySource" id="msds[${index}].coSpecificGravitySource" class="selectBox">    
												<option value=""<c:if test="${empty item.coSpecificGravitySource}"> selected</c:if>></option>			
												<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="coStatus">	
													<c:choose>
														<c:when test="${!empty item.coSpecificGravitySource}">
															<c:choose>
																<c:when test="${sourceBean.dataSource eq item.coSpecificGravitySource}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${sourceBean.dataSource eq 'MSDS'}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>										
												</c:forEach>
										</select>				
										</td>
									</tr>
								</table>
							</FIELDSET>
						</td>
						<td width="25%" class="optionTitleLeft"  valign="top" rowspan="2">
							<FIELDSET>
								<LEGEND class="optionTitleBold"><fmt:message key="label.vaporpressure"/></LEGEND>
								<table width="100%" class="tableSearch" id="msdsDataTable" border="0" cellpadding="0" cellspacing="0">
										<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.detect"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											 <select name="msds[${index}].coVaporPressureDetect" id="msds[${index}].coVaporPressureDetect" class="selectBox"  onchange="blockCODisplays('coVaporPressure'+'${index}',value); customerOverrideChanged()">    			
												<option value=""<c:if test="${empty item.coVaporPressureDetect}"> selected</c:if>></option>
												<c:forEach var="detectBean" items="${vvDetectBeanCollection}" varStatus="coStatus">
													<c:if test="${detectBean.flashPointOnly == 'N'}">
															<c:choose>
																<c:when test="${detectBean.detect eq item.coVaporPressureDetect}">
																	<option value="${detectBean.detect}" selected>${detectBean.description}</option>
																</c:when>
																<c:otherwise>
																	<option value="${detectBean.detect}">${detectBean.description}</option>
																</c:otherwise>
															</c:choose>	
														</c:if>
														<c:if test="${detectBean.flashPointOnly == 'Y' && isFlashDetect == 'Y'}">
																<c:choose>
																<c:when test="${detectBean.detect eq item.coVaporPressureDetect}">
																	<option value="${detectBean.detect}" selected>${detectBean.description}</option>
																</c:when>
																<c:otherwise>
																	<option value="${detectBean.detect}">${detectBean.description}</option>
																</c:otherwise>
															</c:choose>	
														</c:if>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr name="coVaporPressure${index}Value" style="display: none" overflow: auto;>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.value"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coVaporPressure" id="msds[${index}].coVaporPressure" size="20" maxlength="20" value="${item.coVaporPressure}">
										</td>
									</tr>
									<tr name="coVaporPressure${index}Limit" style="display: none" overflow: auto;>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.upper"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coVaporPressureUpper" id="msds[${index}].coVaporPressureUpper" size="20" maxlength="20" value="${item.coVaporPressureUpper}">
										</td>
									</tr>
									<tr name="coVaporPressure${index}Value" style="display: none" overflow: auto;>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.units"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											 <select onchange="customerOverrideChanged()" name="msds[${index}].coVaporPressureUnit" id="msds[${index}].coVaporPressureUnit" class="selectBox">    			
												<option value=""<c:if test="${empty item.coVaporPressureUnit}"> selected</c:if>></option>
									    			<option value="MPa"<c:if test='${"MPa" eq item.coVaporPressureUnit}'> selected</c:if>>MPa</option>
									    			<option value="atm"<c:if test='${"atm" eq item.coVaporPressureUnit}'> selected</c:if>>atm</option>
									    			<option value="hPa"<c:if test='${"hPa" eq item.coVaporPressureUnit}'> selected</c:if>>hPa</option>
									    			<option value="in Hg"<c:if test='${"in Hg" eq item.coVaporPressureUnit}'> selected</c:if>>in Hg</option>
									    			<option value="kPa"<c:if test='${"kPa" eq item.coVaporPressureUnit}'> selected</c:if>>kPa</option>
									    			<option value="mbar"<c:if test='${"mbar" eq item.coVaporPressureUnit}'> selected</c:if>>mbar</option>
									    			<option value="mm Hg"<c:if test='${"mm Hg" eq item.coVaporPressureUnit}'> selected</c:if>>mm Hg</option>
									    			<option value="psia"<c:if test='${"psia" eq item.coVaporPressureUnit}'> selected</c:if>>psia</option>
									    			<option value="psig"<c:if test='${"psig" eq item.coVaporPressureUnit}'> selected</c:if>>psig</option>
									    			<option value="torr"<c:if test='${"torr" eq item.coVaporPressureUnit}'> selected</c:if>>torr</option>
											</select>
										</td>
									</tr>
									<tr name="coVaporPressure${index}Value" style="display: none" overflow: auto;>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.temperature"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" type="text" name="msds[${index}].coVaporPressureTemp" id="msds[${index}].coVaporPressureTemp" size="5" maxlength="10" value="${item.coVaporPressureTemp}">
											 <select onchange="customerOverrideChanged()" name="msds[${index}].coVaporPressureTempUnit" id="msds[${index}].coVaporPressureTempUnit" class="selectBox">    			
												<option value=""<c:if test="${empty item.coVaporPressureTempUnit}"> selected</c:if>></option>
												<option value="C"<c:if test='${"C" eq item.coVaporPressureTempUnit}'> selected</c:if>>&deg;<fmt:message key="label.cforcelsius"/></option>
												<option value="F"<c:if test='${"F" eq item.coVaporPressureTempUnit}'> selected</c:if>>&deg;<fmt:message key="label.fforfahrenheit"/></option>
											</select>
										</td>
									</tr>						
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.source"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<select onchange="customerOverrideChanged()" name="msds[${index}].coVaporPressureSource" id="msds[${index}].coVaporPressureSource" class="selectBox">    
												<option value=""<c:if test="${empty item.coVaporPressureSource}"> selected</c:if>></option>			
												<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="coStatus">
													<c:choose>
														<c:when test="${!empty item.coVaporPressureSource}">
															<c:choose>
																<c:when test="${sourceBean.dataSource eq item.coVaporPressureSource}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${sourceBean.dataSource eq 'MSDS'}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>									
												</c:forEach>
										</select>
										</td>
									</tr>
								</table>
						</td>
						<td width="25%" class="optionTitleLeft"  valign="top">
							<FIELDSET>
								<LEGEND class="optionTitleBold"><fmt:message key="label.solids"/></LEGEND>
								<table width="100%" class="tableSearch" id="msdsDataTable" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.value"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coSolids" id="msds[${index}].coSolids" size="20" maxlength="20" value="${item.coSolids}">
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.lower"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coSolidsLower" id="msds[${index}].coSolidsLower" size="20" maxlength="20" value="${item.coSolidsLower}">
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.upper"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coSolidsUpper" id="msds[${index}].coSolidsUpper" size="20" maxlength="20" value="${item.coSolidsUpper}">
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.units"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											 <select onchange="customerOverrideChanged()" name="msds[${index}].coSolidsUnit" id="msds[${index}].coSolidsUnit" class="selectBox">    			
												<option value=""<c:if test="${empty item.coSolidsUnit}"> selected</c:if>></option>
												<option value="%(w/w)"<c:if test='${"%(w/w)" eq item.coSolidsUnit}'> selected</c:if>>%(w/w)</option>
												<option value="g/L"<c:if test='${"g/L" eq item.coSolidsUnit}'> selected</c:if>>g/L</option>
												<option value="lb/gal"<c:if test='${"lb/gal" eq item.coSolidsUnit}'> selected</c:if>>lb/gal</option>
											</select>
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.source"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
										<select onchange="customerOverrideChanged()" name="msds[${index}].coSolidsSource" id="msds[${index}].coSolidsSource" class="selectBox">    
												<option value=""<c:if test="${empty item.coSolidsSource}"> selected</c:if>></option>			
												<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="coStatus">	
													<c:choose>
														<c:when test="${!empty item.coSolidsSource}">
															<c:choose>
																<c:when test="${sourceBean.dataSource eq item.coSolidsSource}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${sourceBean.dataSource eq 'MSDS'}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>										
												</c:forEach>
										</select>
										</td>
									</tr>
								</table>
							</FIELDSET>
						</td>
		     			<td width="25%" class="optionTitleLeft"  valign="top">
							<FIELDSET>
								<LEGEND class="optionTitleBold"><fmt:message key="label.voclesswaterandexempt"/></LEGEND>
								<table width="100%" class="tableSearch" id="msdsDataTable" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.value"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coVocLessH2oExempt" id="msds[${index}].coVocLessH2oExempt" size="20" maxlength="20" value="${item.coVocLessH2oExempt}">
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.lower"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coVocLessH2oExemptLower" id="msds[${index}].coVocLessH2oExemptLower" size="20" maxlength="20" value="${item.coVocLessH2oExemptLower}">
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.upper"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											<input class="inputBox" onchange="customerOverrideChanged()" type="text" name="msds[${index}].coVocLessH2oExemptUpper" id="msds[${index}].coVocLessH2oExemptUpper" size="20" maxlength="20" value="${item.coVocLessH2oExemptUpper}">
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.units"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
											 <select onchange="customerOverrideChanged()" name="msds[${index}].coVocLessH2oExemptUnit" id="msds[${index}].coVocLessH2oExemptUnit" class="selectBox">    			
												<option value=""<c:if test="${empty item.coVocLessH2oExemptUnit}"> selected</c:if>></option>
												<option value="g/L"<c:if test='${"g/L" eq item.coVocLessH2oExemptUnit}'> selected</c:if>>g/L</option>
												<option value="lb/gal"<c:if test='${"lb/gal" eq item.coVocLessH2oExemptUnit}'> selected</c:if>>lb/gal</option>
											</select>
										</td>
									</tr>
									<tr>
										<td width="40%" class="optionTitleBoldRight">
											<fmt:message key="label.source"/>&nbsp;:
										</td>
										<td width="60%" class="optionTitleLeft" >
										 <select onchange="customerOverrideChanged()" name="msds[${index}].coVocLessH2oExemptSource" id="msds[${index}].coVocLessH2oExemptSource" class="selectBox">    
												<option value=""<c:if test="${empty item.coVocLessH2oExemptSource}"> selected</c:if>></option>			
												<c:forEach var="sourceBean" items="${vvDataSourceBeanCollection}" varStatus="coStatus">
													<c:choose>
														<c:when test="${!empty item.coVocLessH2oExemptSource}">
															<c:choose>
																<c:when test="${sourceBean.dataSource eq item.coVocLessH2oExemptSource}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${sourceBean.dataSource eq 'MSDS'}">
																	<option value="${sourceBean.dataSource}" selected>${sourceBean.dataSource}</option>
																</c:when>
																<c:otherwise>
																	<option value="${sourceBean.dataSource}">${sourceBean.dataSource}</option>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>									
												</c:forEach>
										</select>		
										</td>
									</tr>
								</table>
							</FIELDSET>
						</td>
					</tr>
					<tr>
						<td  class="optionTitleLeft" valign="top" colspan="4">
							<FIELDSET>
								<LEGEND class="optionTitleBold"><fmt:message key="label.supplemental"/></LEGEND>
								<table width="64%" class="tableSearch" id="supplementalDataTable1"  border="0" cellpadding="0" cellspacing="0"  style="float: left;">
												<TR>
													<TD width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.alloy"/>&nbsp;:
													</TD>
													<TD width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coAlloy" id="msds[${index}].coAlloyY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coAlloy eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coAlloy" id="msds[${index}].coAlloyN" type="radio" class="radioBtns" value="N"<c:if test="${item.coAlloy eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coAlloy" id="msds[${index}].coAlloyX" type="radio" class="radioBtns" value="X"<c:if test="${item.coAlloy eq 'X'}">checked</c:if>> <fmt:message key="label.na"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coAlloy'); customerOverrideChanged()" type="button" />
													</TD>
												</TR>
												<TR>
													<TD width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.carcinogen"/>&nbsp;:
													</TD>
													<TD width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coCarcinogen" id="msds[${index}].coCarcinogenY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coCarcinogen eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coCarcinogen" id="msds[${index}].coCarcinogenN" type="radio" class="radioBtns" value="N"<c:if test="${item.coCarcinogen eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coCarcinogen" id="msds[${index}].coCarcinogenX" type="radio" class="radioBtns" value="X"<c:if test="${item.coCarcinogen eq 'X'}">checked</c:if>> <fmt:message key="label.na"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coCarcinogen'); customerOverrideChanged()" type="button"/>
													</TD>
												</TR>
												<TR>
													<TD width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.chronic"/>&nbsp;:
													</TD>
													<TD width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coChronic" id="msds[${index}].coChronicY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coChronic eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coChronic" id="msds[${index}].coChronicN" type="radio" class="radioBtns" value="N"<c:if test="${item.coChronic eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coChronic" id="msds[${index}].coChronicX" type="radio" class="radioBtns" value="X"<c:if test="${item.coChronic eq 'X'}">checked</c:if>> <fmt:message key="label.na"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coChronic'); customerOverrideChanged()" type="button"/>
													</TD>
												</TR>
												<TR>
													<TD width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.corrosive"/>&nbsp;:
													</TD>
													<TD width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coCorrosive" id="msds[${index}].coCorrosiveY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coCorrosive eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coCorrosive" id="msds[${index}].coCorrosiveN" type="radio" class="radioBtns" value="N"<c:if test="${item.coCorrosive eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coCorrosive" id="msds[${index}].coCorrosiveX" type="radio" class="radioBtns" value="X"<c:if test="${item.coCorrosive eq 'X'}">checked</c:if>> <fmt:message key="label.na"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coCorrosive'); customerOverrideChanged()" type="button"/>
													</TD>
												</TR>
												
													<TD width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.fireconditionstoavoid"/>&nbsp;:
													</TD>
													<TD width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coFireConditionsToAvoid" id="msds[${index}].coFireConditionsToAvoidY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coFireConditionsToAvoid eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coFireConditionsToAvoid" id="msds[${index}].coFireConditionsToAvoidN" type="radio" class="radioBtns" value="N"<c:if test="${item.coFireConditionsToAvoid eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coFireConditionsToAvoid" id="msds[${index}].coFireConditionsToAvoidX" type="radio" class="radioBtns" value="X"<c:if test="${item.coFireConditionsToAvoid eq 'X'}">checked</c:if>> <fmt:message key="label.na"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coFireConditionsToAvoid'); customerOverrideChanged()" type="button"/>
													</TD>
												</TR>
												<TR>
													<TD width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.incompatible"/>&nbsp;:
													</TD>
													<TD width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coIncompatible" id="msds[${index}].coIncompatibleY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coIncompatible eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coIncompatible" id="msds[${index}].coIncompatibleN" type="radio" class="radioBtns" value="N"<c:if test="${item.coIncompatible eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coIncompatible" id="msds[${index}].coIncompatibleX" type="radio" class="radioBtns" value="X"<c:if test="${item.coIncompatible eq 'X'}">checked</c:if>> <fmt:message key="label.na"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coIncompatible'); customerOverrideChanged()" type="button"/>
													</TD>
												</TR>
												
												<TR>
													<TD width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.oxidizer"/>&nbsp;:
													</TD>
													<TD width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coOxidizer" id="msds[${index}].coOxidizerY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coOxidizer eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coOxidizer" id="msds[${index}].coOxidizerN" type="radio" class="radioBtns" value="N"<c:if test="${item.coOxidizer eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coOxidizer" id="msds[${index}].coOxidizerX" type="radio" class="radioBtns" value="X"<c:if test="${item.coOxidizer eq 'X'}">checked</c:if>> <fmt:message key="label.na"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coOxidizer'); customerOverrideChanged()" type="button"/>
													</TD>
												</TR>
												<TR>
													<TD width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.polymerize"/>&nbsp;:
													</TD>
													<TD width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coPolymerize" id="msds[${index}].coPolymerizeY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coPolymerize eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coPolymerize" id="msds[${index}].coPolymerizeN" type="radio" class="radioBtns" value="N"<c:if test="${item.coPolymerize eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coPolymerize" id="msds[${index}].coPolymerizeX" type="radio" class="radioBtns" value="X"<c:if test="${item.coPolymerize eq 'X'}">checked</c:if>> <fmt:message key="label.na"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coPolymerize'); customerOverrideChanged()" type="button"/>
													</TD>
												</TR>
												<TR>
													<TD width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.stable"/>&nbsp;:
													</TD>
													<TD width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coStable" id="msds[${index}].coStableY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coStable eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coStable" id="msds[${index}].coStableN" type="radio" class="radioBtns" value="N"<c:if test="${item.coStable eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coStable" id="msds[${index}].coStableX" type="radio" class="radioBtns" value="X"<c:if test="${item.coStable eq 'X'}">checked</c:if>> <fmt:message key="label.na"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coStable'); customerOverrideChanged()" type="button"/>
													</TD>
												</TR>
												<TR>
													<TD width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.waterreactive"/>&nbsp;:
													</TD>
													<TD width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coWaterReactive" id="msds[${index}].coWaterReactiveY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coWaterReactive eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coWaterReactive" id="msds[${index}].coWaterReactiveN" type="radio" class="radioBtns" value="N"<c:if test="${item.coWaterReactive eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coWaterReactive" id="msds[${index}].coWaterReactiveX" type="radio" class="radioBtns" value="X"<c:if test="${item.coWaterReactive eq 'X'}">checked</c:if>> <fmt:message key="label.na"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coWaterReactive'); customerOverrideChanged()" type="button"/>
													</TD>
												</TR>
												<TR>
													<td width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.sara311312acute"/>&nbsp;:
													</td>
													<td width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coSara311312Acute" id="msds[${index}].coSara311312AcuteY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coSara311312Acute eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coSara311312Acute" id="msds[${index}].coSara311312AcuteN" type="radio" class="radioBtns" value="N"<c:if test="${item.coSara311312Acute eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coSara311312Acute" id="msds[${index}].coSara311312AcuteU" type="radio" class="radioBtns" value="U"<c:if test="${item.coSara311312Acute eq 'U'}">checked</c:if>> <fmt:message key="label.na"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coSara311312Acute'); customerOverrideChanged()" type="button"/>
													</td>
												</TR>
												<TR>
													<td width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.sara311312chronic"/>&nbsp;:
													</td>
													<td width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coSara311312Chronic" id="msds[${index}].coSara311312ChronicY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coSara311312Chronic eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coSara311312Chronic" id="msds[${index}].coSara311312ChronicN" type="radio" class="radioBtns" value="N"<c:if test="${item.coSara311312Chronic eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coSara311312Chronic" id="msds[${index}].coSara311312ChronicU" type="radio" class="radioBtns" value="U"<c:if test="${item.coSara311312Chronic eq 'U'}">checked</c:if>> <fmt:message key="label.na"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coSara311312Chronic'); customerOverrideChanged()" type="button"/>
													</td>
												</TR>
												<TR>
													<td width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.sara311312fire"/>&nbsp;:
													</td>
													<td width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coSara311312Fire" id="msds[${index}].coSara311312FireY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coSara311312Fire eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coSara311312Fire" id="msds[${index}].coSara311312FireN" type="radio" class="radioBtns" value="N"<c:if test="${item.coSara311312Fire eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coSara311312Fire" id="msds[${index}].coSara311312FireU" type="radio" class="radioBtns" value="U"<c:if test="${item.coSara311312Fire eq 'U'}">checked</c:if>> <fmt:message key="label.na"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coSara311312Fire'); customerOverrideChanged()" type="button"/>
													</td>
												</TR>
												<TR>
													<td width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.sara311312pressure"/>&nbsp;:
													</td>
													<td width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coSara311312Pressure" id="msds[${index}].coSara311312PressureY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coSara311312Pressure eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coSara311312Pressure" id="msds[${index}].coSara311312PressureN" type="radio" class="radioBtns" value="N"<c:if test="${item.coSara311312Pressure eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coSara311312Pressure" id="msds[${index}].coSara311312PressureU" type="radio" class="radioBtns" value="U"<c:if test="${item.coSara311312Pressure eq 'U'}">checked</c:if>> <fmt:message key="label.na"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coSara311312Pressure'); customerOverrideChanged()" type="button"/>
													</td>
												</TR>
												<TR>
													<td width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.sara311312reactivity"/>&nbsp;:
													</td>
													<td width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coSara311312Reactivity" id="msds[${index}].coSara311312ReactivityY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coSara311312Reactivity eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coSara311312Reactivity" id="msds[${index}].coSara311312ReactivityN" type="radio" class="radioBtns" value="N"<c:if test="${item.coSara311312Reactivity eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coSara311312Reactivity" id="msds[${index}].coSara311312ReactivityU" type="radio" class="radioBtns" value="U"<c:if test="${item.coSara311312Reactivity eq 'U'}">checked</c:if>> <fmt:message key="label.na"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coSara311312Reactivity'); customerOverrideChanged()" type="button"/>
													</td>
												</TR>
											</table>
											<table width="34%" class="tableSearch" id="supplementalDataTable2" border="0" cellpadding="0" cellspacing="0"  style="float: right;">	
												<TR>
													<TD width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.detonable"/>&nbsp;:
													</TD>
													<TD width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coDetonable" id="msds[${index}].coDetonableY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coDetonable eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coDetonable" id="msds[${index}].coDetonableN" type="radio" class="radioBtns" value="N"<c:if test="${item.coDetonable eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coDetonable'); customerOverrideChanged()" type="button"/>
													</TD>
												</TR>
												<TR>
													<TD width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.healtheffects"/>&nbsp;:
													</TD>
													<TD width="70%" class="optionTitleLeft">
														<input onchange="customerOverrideChanged()" name="msds[${index}].coHealthEffects" id="msds[${index}].coHealthEffectsY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coHealthEffects eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coHealthEffects" id="msds[${index}].coHealthEffectsN" type="radio" class="radioBtns" value="N"<c:if test="${item.coHealthEffects eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coHealthEffects'); customerOverrideChanged()" type="button"/>
													</TD> 
												</TR>
												<TR>
													<TD width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.lowvolumeexempt"/>&nbsp;:
													</TD>
													<TD width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coLowVolumeExempt" id="msds[${index}].coLowVolumeExemptY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coLowVolumeExempt eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coLowVolumeExempt" id="msds[${index}].coLowVolumeExemptN" type="radio" class="radioBtns" value="N"<c:if test="${item.coLowVolumeExempt eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coLowVolumeExempt'); customerOverrideChanged()" type="button"/>
													</TD>
												</TR>
												<TR>
													<TD width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.miscible"/>&nbsp;:
													</TD>
													<TD width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coMiscible" id="msds[${index}].coMiscibleY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coMiscible eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coMiscible" id="msds[${index}].coMiscibleN" type="radio" class="radioBtns" value="N"<c:if test="${item.coMiscible eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coMiscible'); customerOverrideChanged()" type="button"/>
													</TD>
												</TR>
												<TR>
													<TD width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.ozonedepletingcompound"/>&nbsp;:
													</TD>
													<TD width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coOzoneDepletingCompound" id="msds[${index}].coOzoneDepletingCompoundY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coOzoneDepletingCompound eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coOzoneDepletingCompound" id="msds[${index}].coOzoneDepletingCompoundN" type="radio" class="radioBtns" value="N"<c:if test="${item.coOzoneDepletingCompound eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coOzoneDepletingCompound'); customerOverrideChanged()" type="button"/>
													</TD>
												</TR>
												<TR>
													<TD width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.pyrophoric"/>&nbsp;:
													</TD>
													<TD width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coPyrophoric" id="msds[${index}].coPyrophoricY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coPyrophoric eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coPyrophoric" id="msds[${index}].coPyrophoricN" type="radio" class="radioBtns" value="N"<c:if test="${item.coPyrophoric eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coPyrophoric'); customerOverrideChanged()" type="button"/>
													</TD>
												</TR>
												<TR>
													<TD width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.spontaneouslycombustible"/>&nbsp;:
													</TD>
													<TD width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coSpontaneouslyCombustible" id="msds[${index}].coSpontaneouslyCombustibleY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coSpontaneouslyCombustible eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coSpontaneouslyCombustible" id="msds[${index}].coSpontaneouslyCombustibleN" type="radio" class="radioBtns" value="N"<c:if test="${item.coSpontaneouslyCombustible eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coSpontaneouslyCombustible'); customerOverrideChanged()" type="button"/>
													</TD>
												</TR>
												<TR>
													<TD width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.tscastatement"/>&nbsp;:
													</TD>
													<TD width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].coTscaStatement" id="msds[${index}].coTscaStatementY" type="radio" class="radioBtns" value="Y"<c:if test="${item.coTscaStatement eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].coTscaStatement" id="msds[${index}].coTscaStatementN" type="radio" class="radioBtns" value="N"<c:if test="${item.coTscaStatement eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('coTscaStatement'); customerOverrideChanged()" type="button"/>
													</TD>
												</TR>
												<TR>
													<TD width="30%" class="optionTitleBoldRight">
														<fmt:message key="label.vocasmixed"/>&nbsp;:
													</TD>
													<TD width="70%" class="optionTitleLeft" >
														<input onchange="customerOverrideChanged()" name="msds[${index}].asMixed" id="msds[${index}].asMixedY" type="radio" class="radioBtns" value="Y"<c:if test="${item.asMixed eq 'Y'}">checked</c:if>> <fmt:message key="label.yes"/>
														<input onchange="customerOverrideChanged()" name="msds[${index}].asMixed" id="msds[${index}].asMixedN" type="radio" class="radioBtns" value="N"<c:if test="${item.asMixed eq 'N'}">checked</c:if>> <fmt:message key="label.no"/>
														&nbsp;&nbsp;<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearradio1" value="<fmt:message key="label.clear"/>" onclick="clearRadio('asMixed'); customerOverrideChanged()" type="button"/>
													</TD>
												</TR>		
											</table>
									</FIELDSET>	
						</td>
					</tr>
				</table>	
			</div> 
			<div class="roundbottom">
							<div class="roundbottomright">
								<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
							</div>
						</div>
		</div>
</div>
<div id="hiddenElements" style="display: none;">
	<input type="hidden" name="msds[${index}].saveCustomerOverride" id="msds[${index}].saveCustomerOverride" value=false/>
</div>
<script language="javascript">
	toggleCoBlocks("0");
</script>