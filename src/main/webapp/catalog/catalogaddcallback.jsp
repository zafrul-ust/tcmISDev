<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="expires" content="-1"/>
	<%@ include file="/common/locale.jsp" %>

	<script>
		function doCallback() {	
				<c:choose>
					<c:when test="${not empty availableRevisionDates}">
						var revDateSelectBox = parent.getMSDSfield("revisionDate");
						var last = revDateSelectBox.options.length - 1;
						var index = 0;
						revDateSelectBox.options.length= 0; 
						//revDateSelectBox.options[index++] = new Option("", "", true, true);
						<c:forEach var="availRevDate" items="${availableRevisionDates}">
							revDateSelectBox.options[index++] = new Option("<fmt:formatDate value="${availRevDate.revisionDate}" pattern="${dateTimeFormatPattern}"/>", "<fmt:formatDate value="${availRevDate.revisionDate}" pattern="${dateTimeFormatPattern}"/>", false, false);
							if ('<fmt:formatDate value="${revisionDateRMC}" pattern="${dateTimeFormatPattern}"/>' == revDateSelectBox.options[index-1].value) {
								revDateSelectBox.options[index-1].selected = true;
							}
							else if (parent.genericForm.revisionDate) 
							{
								if (parent.genericForm.revisionDate.value == revDateSelectBox.options[index-1].value) {
									revDateSelectBox.options[index-1].selected = true;
								}
							}

						</c:forEach>
						revDateSelectBox.options[index++] = new Option("New", "NEW", false, false);
						parent.revisionDateChanged(revDateSelectBox.value, false);
						parent.stopShowingWait();
					</c:when>
					<c:when test="${not empty manufacturer}">
						parent.getMSDSfield("mfgId").value = '${manufacturer.mfgId}';
						parent.getMSDSfield("mfgId_disp").innerHTML = '${manufacturer.mfgId}';
						parent.getMSDSfield("manufacturer").value = '<tcmis:jsReplace value="${manufacturer.mfgDesc}" processMultiLines="true" />';
						parent.getMSDSfield("phoneSpan").innerHTML = '<tcmis:jsReplace value="${manufacturer.phone}" processMultiLines="true" />';
						parent.getMSDSfield("phone").value = '${manufacturer.phone}';
						parent.getMSDSfield("emailSpan").innerHTML = '<a href="mailto:<tcmis:jsReplace value="${manufacturer.email}" processMultiLines="true" />"><tcmis:jsReplace value="${manufacturer.email}" processMultiLines="true" /></a>';
						parent.getMSDSfield("email").value = '<tcmis:jsReplace value="${manufacturer.email}" processMultiLines="true" />';
						/* parent.getMSDSfield("mfgCountry").value = '${manufacturer.countryAbbrev}'; */
						
						<c:choose>
							<c:when test="${not empty manufacturer.mfgUrl}">
									parent.getMSDSfield("contactSpan").innerHTML = '<a href="${manufacturer.mfgUrl}" target="_blank"><tcmis:jsReplace value="${manufacturer.contact}" processMultiLines="true" /></a>';
							</c:when>
							<c:otherwise>
									parent.getMSDSfield("contactSpan").innerHTML = '<tcmis:jsReplace value="${manufacturer.contact}" processMultiLines="true" />';
							</c:otherwise>
						</c:choose>
						parent.getMSDSfield("contact").value = '<tcmis:jsReplace value="${manufacturer.contact}" processMultiLines="true" />';
						parent.getMSDSfield("mfgUrl").value = '<tcmis:jsReplace value="${manufacturer.mfgUrl}" processMultiLines="true" />';
						parent.getMSDSfield("notesSpan").innerHTML = '<tcmis:jsReplace value="${manufacturer.notes}" processMultiLines="true" />';
						parent.getMSDSfield("notes").value = '<tcmis:jsReplace value="${manufacturer.notes}" processMultiLines="true" />';
						parent.getMSDSfield("mfgShortName").value = '<tcmis:jsReplace value="${manufacturer.mfgShortName}" processMultiLines="true" />';
						// parent.getMSDSfield("countryAbbrevSpan").innerHTML = '${manufacturer.countryAbbrev}';
						// parent.getMSDSfield("countryAbbrev").value = '${manufacturer.countryAbbrev}'; 
						parent.loadMSDS(parent.getMSDSfieldValue("materialId"), parent.getMSDSfieldValue("revisionDate"));
					</c:when>
					<c:when test="${not empty msds}">
							parent.getMSDSfield("materialId").value = "${msds.materialId}";
							parent.getMSDSfield("msdsAuthorId").value = "${msds.msdsAuthorId}";
							parent.getMSDSfield("msdsAuthorIdSpan").innerHTML = '${msdsAuthor.msdsAuthorId}';
							parent.getMSDSfield("msdsAuthorDescSpan").innerHTML = '<tcmis:jsReplace value="${msdsAuthor.msdsAuthorDesc}" processMultiLines="true" />';
							parent.getMSDSfield("msdsAuthorCountryAbbrevSpan").innerHTML = '<tcmis:jsReplace value="${msdsAuthor.countryAbbrev}" processMultiLines="true" />';
							parent.getMSDSfield("msdsAuthorNotesSpan").innerHTML = '<tcmis:jsReplace value="${msdsAuthor.notes}" processMultiLines="true" />';
							
							parent.getMSDSfield("content").value = "${msds.content}";
							parent.getMSDSfield("health").value = "${msds.health}";
							parent.getMSDSfield("flammability").value = "${msds.flammability}";
							parent.getMSDSfield("reactivity").value = "${msds.reactivity}";
							parent.getMSDSfield("hmisHealth").value = "${msds.hmisHealth}";
							parent.getMSDSfield("hmisFlammability").value = "${msds.hmisFlammability}";
							parent.getMSDSfield("hmisReactivity").value = "${msds.hmisReactivity}";
							parent.getMSDSfield("hmisSource").value = "${msds.hmisSource}";
							parent.getMSDSfield("specificGravityLower").value = "${msds.specificGravityLower}";
							parent.getMSDSfield("density").value = "${msds.density}";
							parent.getMSDSfield("flashPointLower").value = "${msds.flashPointLower}";
							parent.getMSDSfield("voc").value = "${msds.voc}";
							parent.getMSDSfield("vocLower").value = "${msds.vocLower}";
							parent.getMSDSfield("vocUpper").value = "${msds.vocUpper}";
							parent.getMSDSfield("vocLessH2oExempt").value = "${msds.vocLessH2oExempt}";
							parent.getMSDSfield("vocLessH2oExemptLower").value = "${msds.vocLessH2oExemptLower}";
							parent.getMSDSfield("vocLessH2oExemptUpper").value = "${msds.vocLessH2oExemptUpper}";
							parent.getMSDSfield("solids").value = "${msds.solids}";
							parent.getMSDSfield("solidsLower").value = "${msds.solidsLower}";
							parent.getMSDSfield("solidsUpper").value = "${msds.solidsUpper}";
							parent.getMSDSfield("vaporPressure").value = "${msds.vaporPressure}";
							parent.getMSDSfield("vaporPressureUpper").value = "${msds.vaporPressureUpper}";
							parent.getMSDSfield("vaporPressureTemp").value = "${msds.vaporPressureTemp}";
							parent.getMSDSfield("remark").value = "<tcmis:jsReplace value="${msds.remark}" processMultiLines="true" />";
							parent.getMSDSfield("altDataSource").value = "<tcmis:jsReplace value="${msds.altDataSource}" processMultiLines="true" />";
							parent.getMSDSfield("vaporDensity").value = "<tcmis:jsReplace value="${msds.vaporDensity}" processMultiLines="true" />";
							parent.getMSDSfield("evaporationRate").value = "<tcmis:jsReplace value="${msds.evaporationRate}" processMultiLines="true" />";
							parent.getMSDSfield("specificGravityUpper").value = "${msds.specificGravityUpper}";
							parent.getMSDSfield("densityUpper").value = "${msds.densityUpper}";
							parent.getMSDSfield("flashPointUpper").value = "${msds.flashPointUpper}";
							parent.getMSDSfield("boilingPointLower").value = "${msds.boilingPointLower}";
							parent.getMSDSfield("boilingPointUpper").value = "${msds.boilingPointUpper}";
							parent.getMSDSfield("ph").value = "${msds.ph}";
							parent.getMSDSfield("phUpper").value = "${msds.phUpper}";
							parent.getMSDSfield("productCode").value = "${msds.productCode}";
							parent.getMSDSfield("boilingPointDetail").value = "${msds.boilingPointDetail}";
							parent.getMSDSfield("phDetail").value = "${msds.phDetail}";
							parent.getMSDSfield("labelCompanyName").value = "${msds.labelCompanyName}";
							parent.getMSDSfield("labelAddressLine1").value = "${msds.labelAddressLine1}";
							parent.getMSDSfield("labelAddressLine2").value = "${msds.labelAddressLine2}";
							parent.getMSDSfield("labelAddressLine3").value = "${msds.labelAddressLine3}";
							parent.getMSDSfield("labelAddressLine4").value = "${msds.labelAddressLine4}";
							parent.getMSDSfield("labelCity").value = "${msds.labelCity}";
							parent.getMSDSfield("labelZip").value = "${msds.labelZip}";
							parent.getMSDSfield("labelPhone").value = "${msds.labelPhone}";
							parent.getMSDSfield("msdsId").value = "${msds.msdsId}";
							parent.getMSDSfield("selectedState").value = "${msds.labelStateAbbrev}";
							parent.getMSDSfield("hazardStatements").value = "";
							parent.getMSDSfield("precautionaryStatements").value = "";
							var hazardStmts = "";
							<c:forEach var="hazardBean" items="${msds.hazardStmts}" varStatus="hStatus">
								if (hazardStmts.length > 0) {
									hazardStmts = hazardStmts + "; ";
								}
								<c:choose>
								<c:when test="${hazardBean.code ne 'NRH'}">
									hazardStmts = hazardStmts+"${hazardBean.code}|" +
											"${hazardBean.isFromMsds == 1 ? '':' (MAXCOM Estimate) '}" + "${hazardBean.statement}";
								</c:when>
								<c:otherwise>
									hazardStmts = hazardStmts+"${hazardBean.statement}";
								</c:otherwise>
								</c:choose>
							</c:forEach>
							parent.getMSDSfield("hazardStatements").value = hazardStmts;
							
							var precautionaryStmts = "";
							<c:forEach var="precautionaryBean" items="${msds.precautionaryStmts}" varStatus="pStatus">
								if (precautionaryStmts.length > 0) {
									precautionaryStmts = precautionaryStmts + "; ";
								}
								<c:choose>
								<c:when test="${precautionaryBean.code ne 'NRP'}">
									precautionaryStmts = precautionaryStmts+"${precautionaryBean.code}|" +
											"${precautionaryBean.isFromMsds == 1 ? '':' (MAXCOM Estimate) '}" + "${precautionaryBean.statement}";
								</c:when>
								<c:otherwise>
									precautionaryStmts = precautionaryStmts+"${precautionaryBean.statement}";
								</c:otherwise>
								</c:choose>
							</c:forEach>
							parent.getMSDSfield("precautionaryStatements").value = precautionaryStmts;
							
							var pictogramIds = ["not required","explosive","toxic","flammable","oxidizing","health","corrosive","environmental","gas","irritant"];
							
							for (idx in pictogramIds) {
								var pid = pictogramIds[idx];
								parent.getMSDSfield(pid+"Pic").checked = false;
								parent.getMSDSfield(pid+"Pic").readOnly = "";
								if (idx > 0) {
									parent.getMSDSfield(pid+"OnSds").value = "true";
									parent.getMSDSfield(pid+"OriginalOnSds").value = "true";
									parent.getMSDSfield(pid+"OnSdsMsg").style.display = "none";
								}
								//parent.getMSDSfield(pid+"Override").style.visibility = "hidden";
							}
							var pictoIds = new Array();
							<c:forEach var="picto" items="${msds.pictograms}" varStatus="picStatus">
								<c:if test="${picto.pictogramId ne '' && picto.pictogramId ne 'not required'}">
									pictoIds.push("${picto.pictogramId}");
									var isOnSds = "${picto.onSds}";
									parent.getMSDSfield("${picto.pictogramId}OriginalOnSds").value = isOnSds;
								</c:if>
							</c:forEach>
							if (pictoIds.length > 0) {
								parent.getMSDSfield("originalPictograms").value = pictoIds.join("|");
							}
							else {
								parent.getMSDSfield("originalPictograms").value = "";
							}
							
							// Set the selectbox pulldown values
							parent.setPullDown("nfpaSource", "${msds.nfpaSource}");
							parent.setPullDown("specificHazard", "${msds.specificHazard}");
							parent.setPullDown("personalProtection", "${msds.personalProtection}");
							parent.setPullDown("densityUnit", "${msds.densityUnit}");
							parent.setPullDown("flashPointUnit", "${msds.flashPointUnit}");
							parent.setPullDown("physicalState", "${msds.physicalState}");
							parent.setPullDown("vocUnit", "${msds.vocUnit}");
							parent.setPullDown("vocSource", "${msds.vocSource}");
							parent.setPullDown("vocLessH2oExemptSource", "${msds.vocLessH2oExemptSource}");
							parent.setPullDown("vocLessH2oExemptUnit", "${msds.vocLessH2oExemptUnit}");
							parent.setPullDown("solidsUnit", "${msds.solidsUnit}");
							parent.setPullDown("solidsSource", "${msds.solidsSource}");
							parent.setPullDown("vaporPressureUnit", "${msds.vaporPressureUnit}");
							parent.setPullDown("vaporPressureSource", "${msds.vaporPressureSource}");
							parent.setPullDown("vaporPressureTempUnit", "${msds.vaporPressureTempUnit}");
							parent.setPullDown("vaporPressureDetect", "${msds.vaporPressureDetect}");
							parent.setPullDown("specificGravityDetect", "${msds.specificGravityDetect}");
							parent.setPullDown("specificGravitySource", "${msds.specificGravitySource}");
							parent.setPullDown("densityDetect", "${msds.densityDetect}");
							parent.setPullDown("densitySource", "${msds.densitySource}");
							parent.setPullDown("densityUnit", "${msds.densityUnit}");
							parent.setPullDown("flashPointDetect", "${msds.flashPointDetect}");
							parent.setPullDown("flashPointSource", "${msds.flashPointSource}");
							parent.setPullDown("flashPointUnit", "${msds.flashPointUnit}");
							parent.setPullDown("boilingPointDetect", "${msds.boilingPointDetect}");
							parent.setPullDown("boilingPointSource", "${msds.boilingPointSource}");
							parent.setPullDown("boilingPointUnit", "${msds.boilingPointUnit}");
							parent.setPullDown("phDetect", "${msds.phDetect}");
							parent.setPullDown("phSource", "${msds.phSource}");
							parent.setPullDown("physicalStateSource", "${msds.physicalStateSource}");
							parent.setPullDown("flashPointMethod", "${msds.flashPointMethod}");	
							parent.setPullDown("signalWord", "${msds.signalWord}");
							parent.setPullDown("idOnly", "${msds.idOnly}");
							<c:choose>
								<%--set the value of the country drop-down to what was stored--%>
								<c:when test="${not empty msds.labelCountryAbbrev}">
									parent.setPullDown("labelCountryAbbrev", "${msds.labelCountryAbbrev}");
								</c:when>
								<%--if no country was ever selected for this record, default to USA--%>
								<c:otherwise>
									parent.setPullDown("labelCountryAbbrev", "USA");
								</c:otherwise>
							</c:choose>
				
							// Set the radio buttons
							parent.setRadio("hmisChronic", "${msds.hmisChronic}");
							parent.setRadio("healthEffects", "${msds.healthEffects}");
							parent.setRadio("specificGravityBasis", "${msds.specificGravityBasis}");
							parent.setRadio("sara311312Acute", "${msds.sara311312Acute}");
							parent.setRadio("sara311312Chronic", "${msds.sara311312Chronic}");
							parent.setRadio("sara311312Fire", "${msds.sara311312Fire}");
							parent.setRadio("sara311312Pressure", "${msds.sara311312Pressure}");
							parent.setRadio("sara311312Reactivity", "${msds.sara311312Reactivity}");
							
							var idOnly = parent.getMSDSfield("idOnly");
							<c:choose>
						        <c:when test="${msds.content != null}">
							        idOnly.disabled=false;
								</c:when>
								<c:otherwise>
									idOnly.disabled=true;
								</c:otherwise>
							</c:choose>
					        
							var dataEntryComplete = parent.getMSDSfield("dataEntryComplete");
							<c:choose>
						        <c:when test="${msds.dataEntryComplete}">
							        dataEntryComplete.disabled=false;
							        dataEntryComplete.checked=true;	
							        dataEntryComplete.value="Y";
								</c:when>
								<c:otherwise>
									dataEntryComplete.disabled=true;
									dataEntryComplete.checked=false;
									dataEntryComplete.value="N";
								</c:otherwise>
							</c:choose>
							
							var ghsCompliantImage = parent.getMSDSfield("ghsCompliantImage");
							<c:choose>
						        <c:when test="${msds.ghsCompliantImage}">
						        	ghsCompliantImage.disabled=false;
						        	ghsCompliantImage.checked=true;	
						        	ghsCompliantImage.value="Y";
								</c:when>
								<c:otherwise>
									ghsCompliantImage.disabled=true;
									ghsCompliantImage.checked=false;
									ghsCompliantImage.value="N";
								</c:otherwise>
							</c:choose>
							
							var ghsHazard = parent.getMSDSfield("ghsHazard");
							<c:choose>
						        <c:when test="${msds.ghsHazard}">
						        	ghsHazard.checked=false;	
								</c:when>
								<c:otherwise>
									ghsHazard.checked=true;
								</c:otherwise>
							</c:choose>
							
							 		
							// Customer override							
							<c:choose>	
								<c:when test="${not empty customerCompanies}">
								// For MSDS Maintenance
									parent.isCustomerOverrideMsds("Y");
									
									var companySelectBox = parent.getMSDSfield("customerCompanies");
									var last = companySelectBox.options.length - 1;
									var index = 0;
									companySelectBox.options.length= 0;
									<c:forEach var="customerCompany" items="${customerCompanies}" varStatus="coStatus">
										companySelectBox.options[index++] = new Option("${customerCompany.companyName}", "${customerCompany.companyId}", false, false);
									</c:forEach>
								//	companySelectBox[0].selected = 1;
									parent.customerCompanyChanged(companySelectBox.options[0].value, false);
								</c:when>
								<c:when test="${not empty companyMsds}">
								// For Catalog Add QC
									<c:choose>
										<c:when test="${companyMsds}">
											parent.getCustomerOverride();
											parent.isCustomerOverrideMsds(true);
										</c:when>
										<c:otherwise>
											parent.isCustomerOverrideMsds(false);
										</c:otherwise>
									</c:choose>
								</c:when>							
								<c:otherwise>
								// For Catalog Add QC and MSDS Maintenance
									parent.isCustomerOverrideMsds(false);							
								</c:otherwise>
							</c:choose>
							// And now. the composition data for the grid
							var jsonRevDateCompositionData = {
									rows:[<c:forEach var="bean" items="${composition}" varStatus="status">
										{ 	id:${status.count},
											data:[	'Y',
												'<c:choose><c:when test="${bean.trace == 'Y'}">N</c:when><c:otherwise>Y</c:otherwise></c:choose>',
												'<c:choose><c:when test="${bean.trace == 'Y'}">N</c:when><c:otherwise>Y</c:otherwise></c:choose>',
												'<c:choose><c:when test="${bean.trace == 'Y'}">N</c:when><c:otherwise>Y</c:otherwise></c:choose>',
												'${bean.casNumber}',
												'', //casLookup
												'<tcmis:jsReplace value="${bean.msdsChemicalName}" processMultiLines="true" />',
												'${bean.percent}',
												'${bean.percentLower}',
												'${bean.percentUpper}',
												<c:choose><c:when test="${bean.trace == 'Y'}">true</c:when><c:otherwise>false</c:otherwise></c:choose>,
												'${bean.sdsSectionId}',
												'<tcmis:jsReplace value="${bean.remark}" processMultiLines="true" />'
											]
										},
									</c:forEach>
										{ 	id:${fn:length(composition)+1},
											data:[	'Y',
												'Y',
												'Y',
												'Y',
												'',
												'', 
												'',
												'',
												'',
												'',
												false,
												'',
												''
											]
										}
									]};
				
							parent.updateGrid(jsonRevDateCompositionData);
							parent.loadGhs();
							parent.stopShowingWait();
					</c:when>
					<c:when test="${not empty customerOverride}">
						<c:choose>
					        <c:when test="${customerOverride.dataEntryComplete}">
								parent.getMSDSfield("coDataEntryComplete").checked=true;	
								parent.getMSDSfield("coDataEntryComplete").value="Y";
							</c:when>
							<c:otherwise>
								parent.getMSDSfield("coDataEntryComplete").checked=false;
								parent.getMSDSfield("coDataEntryComplete").value="N";
							</c:otherwise>
						</c:choose>
						parent.getMSDSfield("coPhDetail").value = "${customerOverride.phDetail}";
						parent.getMSDSfield("coBoilingPointDetail").value = "${customerOverride.boilingPointDetail}";
						parent.getMSDSfield("coHealth").value = "${customerOverride.health}";
						parent.getMSDSfield("coFlammability").value = "${customerOverride.flammability}";
						parent.getMSDSfield("coReactivity").value = "${customerOverride.reactivity}";
						parent.getMSDSfield("coHmisHealth").value = "${customerOverride.hmisHealth}";
						parent.getMSDSfield("coHmisFlammability").value = "${customerOverride.hmisFlammability}";
						parent.getMSDSfield("coHmisReactivity").value = "${customerOverride.hmisReactivity}";
						parent.getMSDSfield("coDensity").value = "${customerOverride.density}";
						parent.getMSDSfield("coFlashPointLower").value = "${customerOverride.flashPointLower}";
						parent.getMSDSfield("coVoc").value = "${customerOverride.voc}";
						parent.getMSDSfield("coVocLower").value = "${customerOverride.vocLower}";
						parent.getMSDSfield("coVocUpper").value = "${customerOverride.vocUpper}";
						parent.getMSDSfield("coVocLessH2oExempt").value = "${customerOverride.vocLessH2oExempt}";
						parent.getMSDSfield("coVocLessH2oExemptLower").value = "${msdcustomerOverrides.vocLessH2oExemptLower}";
						parent.getMSDSfield("coVocLessH2oExemptUpper").value = "${customerOverride.vocLessH2oExemptUpper}";
						parent.getMSDSfield("coSolids").value = "${customerOverride.solids}";
						parent.getMSDSfield("coSolidsLower").value = "${customerOverride.solidsLower}";
						parent.getMSDSfield("coSolidsUpper").value = "${customerOverride.solidsUpper}";
						parent.getMSDSfield("coVaporPressure").value = "${customerOverride.vaporPressure}";
						parent.getMSDSfield("coVaporPressureUpper").value = "${customerOverride.vaporPressureUpper}";
						parent.getMSDSfield("coVaporPressureTemp").value = "${customerOverride.vaporPressureTemp}";
						parent.getMSDSfield("coSpecificGravityLower").value = "${customerOverride.specificGravityLower}";
						parent.getMSDSfield("coSpecificGravityUpper").value = "${customerOverride.specificGravityUpper}";
						parent.getMSDSfield("coDensityUpper").value = "${customerOverride.densityUpper}";
						parent.getMSDSfield("coFlashPointUpper").value = "${customerOverride.flashPointUpper}";
						parent.getMSDSfield("coBoilingPointLower").value = "${customerOverride.boilingPointLower}";
						parent.getMSDSfield("coBoilingPointUpper").value = "${customerOverride.boilingPointUpper}";
						parent.getMSDSfield("coPh").value = "${customerOverride.ph}";
						parent.getMSDSfield("coPhUpper").value = "${customerOverride.phUpper}";
						
						// Set the select box pulldown values
						parent.setPullDown("coHmisSource", "${customerOverride.hmisSource}");
						parent.setPullDown("coNfpaSource", "${customerOverride.nfpaSource}");
						parent.setPullDown("coSpecificHazard", "${customerOverride.specificHazard}");
						parent.setPullDown("coPersonalProtection", "${customerOverride.personalProtection}");
						parent.setPullDown("coDensityUnit", "${customerOverride.densityUnit}");
						parent.setPullDown("coFlashPointUnit", "${customerOverride.flashPointUnit}");
						parent.setPullDown("coVocUnit", "${customerOverride.vocUnit}");
						parent.setPullDown("coVocSource", "${customerOverride.vocSource}");
						parent.setPullDown("coVocLessH2oExemptSource", "${customerOverride.vocLessH2oExemptSource}");
						parent.setPullDown("coVocLessH2oExemptUnit", "${customerOverride.vocLessH2oExemptUnit}");
						parent.setPullDown("coSolidsUnit", "${customerOverride.solidsUnit}");
						parent.setPullDown("coSolidsSource", "${customerOverride.solidsSource}");
						parent.setPullDown("coVaporPressureUnit", "${customerOverride.vaporPressureUnit}");
						parent.setPullDown("coVaporPressureSource", "${customerOverride.vaporPressureSource}");
						parent.setPullDown("coVaporPressureTempUnit", "${customerOverride.vaporPressureTempUnit}");
						parent.setPullDown("coVaporPressureDetect", "${customerOverride.vaporPressureDetect}");
						parent.setPullDown("coSpecificGravityDetect", "${customerOverride.specificGravityDetect}");
						parent.setPullDown("coSpecificGravitySource", "${customerOverride.specificGravitySource}");
						parent.setPullDown("coDensityDetect", "${customerOverride.densityDetect}");
						parent.setPullDown("coDensitySource", "${customerOverride.densitySource}");
						parent.setPullDown("coDensityUnit", "${customerOverride.densityUnit}");
						parent.setPullDown("coFlashPointDetect", "${customerOverride.flashPointDetect}");
						parent.setPullDown("coFlashPointSource", "${customerOverride.flashPointSource}");
						parent.setPullDown("coFlashPointUnit", "${customerOverride.flashPointUnit}");
						parent.setPullDown("coBoilingPointDetect", "${customerOverride.boilingPointDetect}");
						parent.setPullDown("coBoilingPointSource", "${customerOverride.boilingPointSource}");
						parent.setPullDown("coBoilingPointUnit", "${customerOverride.boilingPointUnit}");
						parent.setPullDown("coPhDetect", "${customerOverride.phDetect}");
						parent.setPullDown("coPhSource", "${customerOverride.phSource}");
						parent.setPullDown("coFlashPointMethod", "${customerOverride.flashPointMethod}");
						parent.setPullDown("coPhysicalState", "${customerOverride.physicalState}");
						parent.setPullDown("coPhysicalStateSource", "${customerOverride.physicalStateSource}");
							
						// Set the radio buttons
						parent.setRadio("coHmisChronic", "${customerOverride.hmisChronic}");
						parent.setRadio("coSpecificGravityBasis", "${customerOverride.specificGravityBasis}");
						parent.setRadio("coAlloy", "${customerOverride.alloy}");
						parent.setRadio("coChronic", "${customerOverride.chronic}");
						parent.setRadio("coCarcinogen", "${customerOverride.carcinogen}");
						parent.setRadio("coCorrosive", "${customerOverride.corrosive}");
						parent.setRadio("coFireConditionsToAvoid", "${customerOverride.fireConditionsToAvoid}");
						parent.setRadio("coIncompatible", "${customerOverride.incompatible}");
						parent.setRadio("coOxidizer", "${customerOverride.oxidizer}");
						parent.setRadio("coPolymerize", "${customerOverride.polymerize}");
						parent.setRadio("coStable", "${customerOverride.stable}");
						parent.setRadio("coWaterReactive", "${customerOverride.waterReactive}");
						parent.setRadio("coOzoneDepletingCompound", "${customerOverride.ozoneDepletingCompound}");
						parent.setRadio("coLowvolumeExempt", "${customerOverride.lowVolumeExempt}");
						parent.setRadio("coDetonable", "${customerOverride.detonable}");
						parent.setRadio("coMiscible", "${customerOverride.miscible}");
						parent.setRadio("coPyrophoric", "${customerOverride.pyrophoric}");
						parent.setRadio("coSpontaneouslyCombustible", "${customerOverride.spontaneouslyCombustible}");
						parent.setRadio("coTscaStatement", "${customerOverride.tscaStatement}");
						parent.setRadio("coSara311312Acute", "${customerOverride.sara311312Acute}");
						parent.setRadio("coSara311312Chronic", "${customerOverride.sara311312Chronic}");
						parent.setRadio("coSara311312Fire", "${customerOverride.sara311312Fire}");
						parent.setRadio("coSara311312Pressure", "${customerOverride.sara311312Pressure}");
						parent.setRadio("coSara311312Reactivity", "${customerOverride.sara311312Reactivity}");
						parent.setRadio("asMixed", "${customerOverride.asMixed}");

                        //
                        <c:if test="${not empty allowMixture}">
						    parent.allowMixture = ${allowMixture};
				        </c:if>

                        parent.stopShowingWait();
					</c:when>
					<c:otherwise>
                        parent.stopShowingWait();
					</c:otherwise>	
				</c:choose>
		}
	</script>	

</head>

<body onload="doCallback();">
</body>
</html>