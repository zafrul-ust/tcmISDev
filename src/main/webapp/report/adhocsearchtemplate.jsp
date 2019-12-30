<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script>
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />

<script language="JavaScript" type="text/javascript">
<!--

	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesDataTemplate = new Array();
messagesDataTemplate = {
		facility:"<fmt:message key="label.facility"/>",
		ph:"<fmt:message key="label.ph"/>",
	    flashpoint:"<fmt:message key="label.flashpoint"/>",
	    vaporpressure:"<fmt:message key="label.vaporpressure"/>",
	    vaporpressureunit:"<fmt:message key="label.vaporpressureunit"/>",
	    vocLwesPercent:"<fmt:message key="label.voclwes"/>",
	    voc:"<fmt:message key="label.voc"/>",
	    solids:"<fmt:message key="label.solids"/>",
	    nfpa:"<fmt:message key="label.nfpa"/>",
	    hmis:"<fmt:message key="label.hmis"/>",
	    health:"<fmt:message key="label.health"/>",
	    flammability:"<fmt:message key="label.flammability"/>",
	    reactivity:"<fmt:message key="label.reactivity"/>",
	    manufacturer:"<fmt:message key="label.manufacturer"/>"
		};

function loadFields()
{
	document.body.onkeydown =
		function (e) {
		  var keyCode;
		  if(window.event)
		  {
		    keyCode = window.event.keyCode;     //IE
		  }else
		  {
		    try
		    {
		      keyCode = e.which;     //firefox
		    }
		    catch (ex){
		      //return false;
		    }
		  }

		  if (keyCode==13) {
			  if (pageId == 'msdsAnalysis')
			  {
				  var ret = submitSearchForm();
				  return ret;
			  }
			else
		    	return false;
		  }
	}

	var blankCheck = '${templateBean.phCompare}';
	if(blankCheck != '')
		$("phCompare").value = blankCheck;

	blankCheck = '${templateBean.flashPointCompare}';
	if(blankCheck != '')
		$("flashPointCompare").value = blankCheck;

	blankCheck = '${templateBean.vaporPressureCompare}';
	if(blankCheck != '')
		$("vaporPressureCompare").value = blankCheck;

	blankCheck = '${templateBean.vocPercentCompare}';
	if(blankCheck != '')
		$("vocPercentCompare").value = blankCheck;

	blankCheck = '${templateBean.vocLwesPercentCompare}';
	if(blankCheck != '')
		$("vocLwesPercentCompare").value = blankCheck;

	blankCheck = '${templateBean.solidsPercentCompare}';
	if(blankCheck != '')
		$("solidsPercentCompare").value = blankCheck;

	blankCheck = '${templateBean.healthCompare}';
	if(blankCheck != '')
		$("healthCompare").value = blankCheck;

	blankCheck = '${templateBean.flammabilityCompare}';
	if(blankCheck != '')
		$("flammabilityCompare").value = blankCheck;

	blankCheck = '${templateBean.reactivityCompare}';
	if(blankCheck != '')
		$("reactivityCompare").value = blankCheck;

	blankCheck = '${templateBean.hmisHealthCompare}';
	if(blankCheck != '')
		$("hmisHealthCompare").value = blankCheck;

	blankCheck = '${templateBean.hmisFlammabilityCompare}';
	if(blankCheck != '')
		$("hmisFlammabilityCompare").value = blankCheck;

	blankCheck = '${templateBean.hmisReactivityCompare}';
	if(blankCheck != '')
		$("hmisReactivityCompare").value = blankCheck;

	blankCheck = '${templateBean.physicalState}';
	if(blankCheck != '')
		$("physicalState").value = blankCheck;

	blankCheck = '${templateBean.temperatureUnit}';
	if(blankCheck != '')
		$("temperatureUnit").value = blankCheck;

	blankCheck = '${templateBean.vaporPressureUnit}';
	if(blankCheck != '')
		$("vaporPressureUnit").value = blankCheck;

	blankCheck = '${templateBean.specificHazard}';
	if(blankCheck != '')
		$("specificHazard").value = blankCheck;

	blankCheck = '${templateBean.personalProtection}';
	if(blankCheck != '')
		$("personalProtection").value = blankCheck;

	blankCheck = '${templateBean.searchField}';
	if(blankCheck != '')
		$("searchField").value = blankCheck;

	blankCheck = '${templateBean.matchType}';
	if(blankCheck != '')
		$("matchType").value = blankCheck;

}

j$().ready(function() {

	j$("#manufacturer").autocomplete("manufacturersearchmain.do?loginNeeded=N&uAction=autoCompleteSearch",{
			width: 528,
			max: 60,  // default value is 10
			cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
			scrollHeight: 150,
			formatItem: function(data, i, n, value) {
				return  value.split(":")[0]+" : "+value.split(":")[1].substring(0,240);
			},
			formatResult: function(data, value) {
				return value.split(":")[1];
			}
	});

	j$('#manufacturer').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);

		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateManufacturerInput();
	}));

	j$("#manufacturer").result(mfgLog).next().click(function() {
		j$(this).prev().search();
	});

	function mfgLog(event, data, formatted) {
		$("manufacturer").className = "inputBox";
		$('mfgId').value = formatted.split(":")[0];
		$('manufacturer').title = formatted.split(":")[1];
	}

});

function invalidateManufacturerInput()
{
 if ($v("manufacturer").length == 0) {
   $("manufacturer").className = "inputBox";
 }else {
   $("manufacturer").className = "inputBox red";
//   $("manufacturer").className = "inputBox red";
 }
 //set to empty
 $('mfgId').value = "";
}
// -->
</script>

<table id="searchMaskTable" width="100%" border="0" cellpadding="0" >
	<tr>
		<td  class="optionTitleBoldLeft"><fmt:message key="label.search"/>:&nbsp;
		        	<select name="searchField" id="searchField" class="selectBox" onchange="showOrHideMsdsNoSpan();">
	                	 <c:if test="${pageId != 'adHocInv'}"><option value="item_id"><fmt:message key="label.itemid"/></option></c:if>
	                	 <option value="material_id"><fmt:message key="label.materialid"/></option>
	                	 <option value="material_desc" selected><fmt:message key="label.materialdesc"/></option>
	                	 <option value="customer_msds_number"><fmt:message key="label.msds"/></option>
	                	 <c:if test="${pageId != 'adHocInv'}"><option value="cat_part_no"><fmt:message key="label.partnumber"/></option></c:if>
	                	 <option value="SPEC_ID"><fmt:message key="label.specification"/></option>
	                	 <option value="Synonym"><fmt:message key="label.synonym"/></option>
	                	 <option value="trade_name"><fmt:message key="label.tradename"/></option>
	                </select>
			        <select name="matchType" id="matchType" class="selectBox" onchange="openMsdsNoTextArea(value);">
			               <option value="="> <fmt:message key="label.is"/>  </option>
			               <option value="like" selected> <fmt:message key="label.contains"/>  </option>
			               <option value="starts with"><fmt:message key="label.startswith"/> </option>
			               <option value="ends with"><fmt:message key="label.endswith" /></option>
			               <option value="in csv list"><fmt:message key="label.incsvlist" /></option>
			               <option value="create list"><fmt:message key="label.createlist" /></option>
			        </select>
		            <input name="searchText" id="searchText" type="text" class="inputBox" size="20" value="${templateBean.searchText}">&nbsp;
		            <span id="msdsNoSpan" style="display:none;font-size:160%;vertical-align:bottom;color:blue;"  onclick="showTooltip('hiddenDesc', 'searchText', -50, 23);">
						*
		   			</span>
	           		<fmt:message key="label.manufacturer"/>:&nbsp;
	            	<input class="inputBox" type="text" name="manufacturer" id="manufacturer" value="${templateBean.mfgDesc}" size="20" maxlength="240" />
	 		 		<input name="mfgId" id="mfgId" type="hidden" value="${templateBean.mfgId}">
	
	               	<fmt:message key="label.physicalstate"/>:&nbsp;
		        	<select name="physicalState" id="physicalState" class="selectBox">
	                	 <option value=""><fmt:message key="label.all"/></option>
	                     <c:forEach var="physicalStateBean" items="${physicalStateColl}" varStatus="status">
	                            <option value="${physicalStateBean.physicalState}"><c:out value="${physicalStateBean.physicalStateDesc}"/></option>
	                     </c:forEach>
	                </select>
			</td>
		</tr>
</table>
<table id="searchMaskTable" width="100%" border="0" cellpadding="0">
<tr>
	<td width ="1%" align="right">
                <table class="tableSearch"  border="0" cellpadding="0">

						<tr>
							<TD  class="optionTitleBoldRight">
								<fmt:message key="label.ph"/>:
							</TD>
						</tr>
						<tr>
							<TD  class="optionTitleBoldRight">
								<fmt:message key="label.flashpoint"/>:
							</TD>
						</tr>
						<tr>
							<TD  class="optionTitleBoldRight" nowrap>
								<fmt:message key="label.vaporpressure"/>:
							</TD>
						</tr>
						<tr>
							<TD class="optionTitleBoldRight" nowrap>
								<fmt:message key="label.voc"/>:
							</TD>
						</tr>
						<tr>
							<TD class="optionTitleBoldRight" nowrap>
								<fmt:message key="label.voclwes"/>:
							</TD>
						</tr>
						<tr>
							<TD class="optionTitleBoldRight">
								%<fmt:message key="label.solids"/>:
							</TD>
						</tr>
					</table>
            </td>
   <td colspan="2" class="optionTitleBoldLeft" nowrap>
       <table width="1%" border="0" >
       <tr><td>
                <table width="33%" class="tableSearch"  border="0" cellpadding="0" cellspacing="0">
						<tr>
							<TD width="40%" class="optionTitleBoldLeft">

								<select name="phCompare" id="phCompare" class="selectBox">
									<option value=">">&gt;</option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
								<input class="inputBox" type="text" name="ph" id="ph" size="5" maxlength="10" value="${templateBean.ph}">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldLeft" nowrap>
								<select name="flashPointCompare" id="flashPointCompare" class="selectBox" >
									<option value="&lt;"><</option>
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
								</select>
								<input class="inputBox" type="text" name="flashPoint" id="flashPoint" size="5" maxlength="10" value="${templateBean.flashPoint}">
								<select name="temperatureUnit" id="temperatureUnit" class="selectBox">
									<option value="C" <c:if test="${templateBean.temperatureUnit == 'C'}">selected</c:if> >C</option>
									<option value="F" <c:if test="${templateBean.temperatureUnit != 'C'}">selected</c:if> >F</option>
								</select>
							</TD>
						</tr>
						<tr>
								<TD width="40%" class="optionTitleBoldLeft" nowrap>
								<select name="vaporPressureCompare" id="vaporPressureCompare" class="selectBox">
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
								<input class="inputBox" type="text" name="vaporPressure" id="vaporPressure" size="5" maxlength="10" value="${templateBean.vaporPressure}">
								<select name="vaporPressureUnit" id="vaporPressureUnit" class="selectBox">
									<option value=""><fmt:message key="label.pleaseselect"/></option>
                     				<c:forEach var="vaporPressureUnitBean" items="${vaporPressureUnitColl}" varStatus="status">
										<option value="${vaporPressureUnitBean.vaporPressureUnit}">${vaporPressureUnitBean.vaporPressureUnit}</option>
									</c:forEach>
								</select>
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldLeft" nowrap>
							<select name="vocPercentCompare" id="vocPercentCompare" class="selectBox">
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
							<input class="inputBox" type="text" name="vocPercent" id="vocPercent" size="5" maxlength="10" value="${templateBean.vocPercent}">
							<select name="vocSearchSelect" id="vocSearchSelect" class="selectBox" style="width:103px">
                     				<c:forEach var="vvVocUnitBean" items="${vocUnitColl}" varStatus="status">
                                        <c:if test="${!empty vvVocUnitBean.vocUnit}">
                                        	<c:choose>
                                        		<c:when test="${vvVocUnitBean.vocUnit == templateBean.vocSearchSelect}">
                                        			 <option selected="selected" value="${vvVocUnitBean.vocUnit}">${vvVocUnitBean.vocUnit}</option>
                                        		</c:when>
                                        		<c:otherwise>
                                        			 <option value="${vvVocUnitBean.vocUnit}">${vvVocUnitBean.vocUnit}</option>
                                        		</c:otherwise>
                                        	</c:choose>
                                        </c:if>
                                    </c:forEach>
							</select>
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldLeft" nowrap>
							<select name="vocLwesPercentCompare" id="vocLwesPercentCompare" class="selectBox">
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
							<input class="inputBox" type="text" name="vocLwesPercent" id="vocLwesPercent" size="5" maxlength="10" value="${templateBean.vocLwesPercent}">
							<select name="vocLwesSearchSelect" id="vocLwesSearchSelect" class="selectBox" style="width:103px">
                                <option value="g/L" <c:if test="${templateBean.vocLwesSearchSelect != 'lb/gal'}">selected</c:if>>g/L</option>
                                <option value="lb/gal" <c:if test="${templateBean.vocLwesSearchSelect == 'lb/gal'}">selected</c:if>>lb/gal</option>
							</select>

							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldLeft">
								<select name="solidsPercentCompare" id="solidsPercentCompare" class="selectBox">
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
								<input class="inputBox" type="text" name="solidsPercent" id="solidsPercent" size="5" maxlength="10" value="${templateBean.solidsPercent}">
							</TD>
						</tr>
					</table>
		 </td>
		 <td>
				<fieldset>
					<legend class="optionTitleBold"><fmt:message key="label.nfpa"/></legend>
					<table width="33%" class="tableSearch" id="msdsDataTable" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.health"/>:
							</TD>
							<TD width="60%" class="optionTitleLeft" nowrap>
								<select name="healthCompare" id="healthCompare" class="selectBox">
									<option value="=">=</option>
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
								<input class="inputBox" type="text" name="health" id="health" size="5" maxlength="10" value="${templateBean.health}">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight" nowrap>
								<fmt:message key="label.flammability"/>:
							</TD>
							<TD width="60%" class="optionTitleLeft" >
								<select name="flammabilityCompare" id="flammabilityCompare" class="selectBox">
									<option value="=">=</option>
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
								<input class="inputBox" type="text" name="flammability" id="flammability" size="5" maxlength="10" value="${templateBean.flammability}">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.reactivity"/>:
							</TD>
							<TD width="60%" class="optionTitleLeft" nowrap>
								<select name="reactivityCompare" id="reactivityCompare" class="selectBox">
									<option value="=">=</option>
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
								<input class="inputBox" type="text" name="reactivity" id="reactivity" size="5" maxlength="10" value="${templateBean.reactivity}">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.hazard"/>:
							</TD>
							<TD width="60%" class="optionTitleLeft" >
								<select name="specificHazard" id="specificHazard" class="selectBox">
									<option value=""><fmt:message key="label.all"/></option>
                     				<c:forEach var="specificHazardBean" items="${specificHazardColl}" varStatus="status">
										<option value="${specificHazardBean.specificHazard}">${specificHazardBean.specificHazardDescription}</option>
									</c:forEach>
								</select>
							</TD>
						</tr>
					</table>
				</fieldset>
		</td>
		<td>
				<fieldset>
					<legend class="optionTitleBold"><fmt:message key="label.hmis"/></legend>
					<table width="33%" class="tableSearch"  border="0" cellpadding="0" cellspacing="0">
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.health"/>:
							</TD>
							<TD width="60%" class="optionTitleLeft" nowrap>
								<select name="hmisHealthCompare" id="hmisHealthCompare" class="selectBox">
									<option value="=">=</option>
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
								<input class="inputBox" type="text" name="hmisHealth" id="hmisHealth" size="5" maxlength="10" value="${templateBean.hmisHealth}">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.flammability"/>:
							</TD>
							<TD width="60%" class="optionTitleLeft" nowrap>
								<select name="hmisFlammabilityCompare" id="hmisFlammabilityCompare" class="selectBox">
									<option value="=">=</option>
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
								<input class="inputBox" type="text" name="hmisFlammability" id="hmisFlammability" size="5" maxlength="10" value="${templateBean.hmisFlammability}">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.reactivity"/>:
							</TD>
							<TD width="60%" class="optionTitleLeft" nowrap>
								<select name="hmisReactivityCompare" id="hmisReactivityCompare" class="selectBox">
									<option value="=">=</option>
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
								<input class="inputBox" type="text" name="hmisReactivity" id="hmisReactivity" size="5" maxlength="10" value="${templateBean.hmisReactivity}">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.personalProtection"/>:
							</TD>
							<TD width="60%" class="optionTitleLeft" colspan="3" >
								<select name="personalProtection" id="personalProtection" class="selectBox">
					                <option value=""><fmt:message key="label.all"/></option>
					            	 <c:forEach var="ppBean" items="${ppColl}" varStatus="status">
					                      <option value="${ppBean.personalProtectCode}"><c:out value="${ppBean.personalProtectCode}: ${ppBean.personalProtectDesc}"/></option>
					                </c:forEach>
								</select>
							</TD>
						</tr>
					</table>
				</fieldset>
			</td></tr></table>
          </td>
		</tr>
		</table>
		<!-- add here  -->


<input type="hidden" name="storageAreaId" id="storageAreaId" value=""/>
<input type="hidden" name="storageAreaDesc" id="storageAreaDesc" value=""/>
<input type="hidden" name="searchFieldDesc" id="searchFieldDesc" value=""/>
<input type="hidden" name="matchTypeDesc" id="matchTypeDesc" value=""/>
<input type="hidden" name="specificHazardDesc" id="specificHazardDesc" value=""/>
<input type="hidden" name="personalProtectionDesc" id="personalProtectionDesc" value=""/>
<input name="mfgDesc" id="mfgDesc" type="hidden" value="">
