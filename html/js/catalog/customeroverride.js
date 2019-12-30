
function blockCODisplays(block,value)
{
	var truncBlock = block.substring(0, block.length-1);
	if(value == "N/L" || value == "N/A" || value ==  "" || value == "N/F")
		{
			var x = getElementsByNameOverride("tr", block + "Value");
			if(x != null)
				for(var i = 0; i < x.length;i++)
					if(x[i] != null)
						x[i].style.display = "none";
			x = getElementsByNameOverride("tr", block + "Limit");
			if(x != null)
				for(var i = 0; i < x.length;i++)
					if(x[i] != null)
						x[i].style.display = "none";
			disableField(truncBlock,true,true);
		}
	else if(value == ">>" || value == ">" || value == ">=" || value == "<" || value == "<=")
		{
			var x = getElementsByNameOverride("tr", block + "Value");
			if(x != null)
				for(var i = 0; i < x.length;i++)
					if(x[i] != null)
						x[i].style.display = "";	
			x = getElementsByNameOverride("tr", block + "Limit");
			if(x != null)
				for(var i = 0; i < x.length;i++)
					if(x[i] != null)
						x[i].style.display = "none";
			
			disableField(truncBlock,false,true);
		}
	else
		{
			var x = getElementsByNameOverride("tr", block + "Value");
			if(x != null)
				for(var i = 0; i < x.length;i++)
					if(x[i] != null)
						x[i].style.display = "";
	
			x = getElementsByNameOverride("tr",block + "Limit");
			if(x != null)
				for(var i = 0; i < x.length;i++)
					if(x[i] != null)
						x[i].style.display = "";
	
			disableField(truncBlock,false,false);
			
			var basisWater = getMSDSfield('coSpecificGravityBasisWater');
			var basisAir = getMSDSfield('coSpecificGravityBasisAir');
			if(basisWater != null && basisAir != null && basisWater.checked == false && basisAir.checked == false)
				basisWater.selected = true;
		}
	
	var s = getMSDSfield(truncBlock +'Source');
	if(s.value == "")
		s.selectedIndex = 1;
}

function clearRadio(radioName) {
	var chk = getMSDSfield(radioName + "Y");
	if(chk != null)
		chk.checked = false;
	chk = getMSDSfield(radioName + "N");
	if(chk != null)
		chk.checked = false;
	chk = getMSDSfield(radioName + "X");
	if(chk != null)
		chk.checked = false;
	chk = getMSDSfield(radioName + "Air");
	if(chk != null)
		chk.checked = false;
	chk = getMSDSfield(radioName + "Water");
	if(chk != null)
		chk.checked = false;
	chk = getMSDSfield(radioName + "U");
	if(chk != null)
		chk.checked = false;
}

function customerCompanyChanged(selectedCompany,newCompanyId) {
	var materialId = getMSDSfield("materialId").value;
    currentRevisionDate = getMSDSfield("revisionDate").value;
  
    if(newCompanyId) {
    	$("companyId").value = getMSDSfield("customerCompanies").value;
    }
    else {
    	if("Y" == $v("RMC") || $v("companyId").length > 0)
    		getMSDSfield("customerCompanies").value = $v("companyId");
    	else
    		$("companyId").value = getMSDSfield("customerCompanies").value; // First load
	}
 	
    selectedCompany = $v("companyId");
    
    if(isInitialLoadedCompany)
	{
		clearCustomerOverride();
		showWait(messagesData.loading);
		var url ="msdsmaintenance.do?uAction=getCustomerOverride&companyId=" + selectedCompany + "&revisionDate=" + currentRevisionDate + "&materialId=" + materialId;
		callToServer(url);

		currentCustomerCompany = selectedCompany;
		isInitialCompany = false;
	}
	else
	{
		if(confirm(messagesData.changes))
		{
			showWait(messagesData.loading);
			clearCustomerOverride();
			var url ="msdsmaintenance.do?uAction=getCustomerOverride&companyId=" + selectedCompany + "&revisionDate=" + currentRevisionDate + "&materialId=" + materialId;
			callToServer(url);
		}			
		else
		{
			getMSDSfield("customerCompanies").value = currentCustomerCompany;
		}
	}
	document.getElementById("companyId").value = currentCustomerCompany;
}

function setCoDataEntryComplete(){
	if(getMSDSfield("coDataEntryComplete").checked)
		getMSDSfield("coDataEntryComplete").value = "Y";
	else
		getMSDSfield("coDataEntryComplete").value = "N";
}

function checkDataEntryComplete() {
    /* p_set_data_entry_complete will determine this now
    var physicalState = getMSDSfield("physicalState").value != "";
	var flashPointDetect = getMSDSfield("flashPointDetect").value != "";
	var vaporPressureDetect = getMSDSfield("vaporPressureDetect").value != "";
	var dataEntryComplete = getMSDSfield("dataEntryComplete");
	
	if(physicalState && flashPointDetect && vaporPressureDetect) {
		dataEntryComplete.checked = true;
		dataEntryComplete.value = "Y";
	}
	else {
		dataEntryComplete.checked = false;
		dataEntryComplete.value = "N";
	}
	
	checkCODataEntryComplete();
	*/
}

function checkCODataEntryComplete() {
    /* p_set_data_entry_complete will determine this now
    var coHealth = getMSDSfield("coHealth").value != "";
	var coFlammability = getMSDSfield("coFlammability").value != "";
	var coReactivity = getMSDSfield("coReactivity").value != "";
	var coHmisHealth = getMSDSfield("coHmisHealth").value != "";
	var coHmisFlammability = getMSDSfield("coHmisFlammability".value) != "";
	var coHmisReactivity = getMSDSfield("coHmisReactivity").value != "";
	
	var dataEntryComplete = getMSDSfield("dataEntryComplete")
	var coDataEntryComplete = getMSDSfield("coDataEntryComplete");

	if(dataEntryComplete != null)
		dataEntryComplete = dataEntryComplete.checked;
	else
		dataEntryComplete = true;
	
	if(dataEntryComplete && coHealth && coFlammability && coReactivity && coHmisHealth && coHmisFlammability && coHmisReactivity) {
		coDataEntryComplete.checked = true;
		coDataEntryComplete.value = "Y";
	}
	else {
		coDataEntryComplete.checked = false;
		coDataEntryComplete.value = "N";
	}
	*/
}

function clearCustomerOverride(){
	getMSDSfield("saveCustomerOverride").value = false;
	
	getMSDSfield("coDataEntryComplete").value = "N";
	getMSDSfield("coDataEntryComplete").checked = false;
	getMSDSfield("coHealth").value = "";
	getMSDSfield("coFlammability").value = "";
	getMSDSfield("coReactivity").value = "";
	getMSDSfield("coHmisHealth").value = "";
	getMSDSfield("coHmisFlammability").value = "";
	getMSDSfield("coHmisReactivity").value = "";
	getMSDSfield("coSpecificGravityLower").value = "";
	getMSDSfield("coDensity").value = "";
	getMSDSfield("coFlashPointLower").value = "";
	getMSDSfield("coVoc").value = "";
	getMSDSfield("coVocLower").value = "";
	getMSDSfield("coVocUpper").value = "";
	getMSDSfield("coVocLessH2oExempt").value = "";
	getMSDSfield("coVocLessH2oExemptLower").value = "";
	getMSDSfield("coVocLessH2oExemptUpper").value = "";
	getMSDSfield("coSolids").value = "";
	getMSDSfield("coSolidsLower").value = "";
	getMSDSfield("coSolidsUpper").value = "";
	getMSDSfield("coVaporPressure").value = "";
	getMSDSfield("coVaporPressureUpper").value = "";
	getMSDSfield("coVaporPressureTemp").value = "";
	getMSDSfield("coSpecificGravityLower").value = "";
	getMSDSfield("coSpecificGravityUpper").value = "";
	getMSDSfield("coDensityUpper").value = "";
	getMSDSfield("coFlashPointUpper").value = "";
	getMSDSfield("coBoilingPointLower").value = "";
	getMSDSfield("coBoilingPointUpper").value = "";
	getMSDSfield("coPh").value = "";
	getMSDSfield("coPhUpper").value = "";
	getMSDSfield("coBoilingPointDetail").value = "";
	getMSDSfield("coPhDetail").value = "";

    setPullDown("coNfpaSource", "");
	setPullDown("coHmisSource", "");
	setPullDown("coSpecificHazard", "");
	setPullDown("coPersonalProtection", "");
	setPullDown("coDensityUnit", "");
	setPullDown("coFlashPointUnit", "");
	setPullDown("coVocUnit", "");
	setPullDown("coVocSource", "");
	setPullDown("coVocLessH2oExemptSource", "");
	setPullDown("coVocLessH2oExemptUnit", "");
	setPullDown("coSolidsUnit", "");
	setPullDown("coSolidsSource", "");
	setPullDown("coVaporPressureUnit", "");
	setPullDown("coVaporPressureSource", "");
	setPullDown("coVaporPressureTempUnit", "");
	setPullDown("coVaporPressureDetect", "");
	setPullDown("coSpecificGravityDetect", "");
	setPullDown("coSpecificGravitySource", "");
	setPullDown("coDensityDetect", "");
	setPullDown("coDensitySource", "");
	setPullDown("coDensityUnit", "");
	setPullDown("coFlashPointDetect", "");
	setPullDown("coFlashPointSource", "");
	setPullDown("coFlashPointUnit", "");
	setPullDown("coBoilingPointDetect", "");
	setPullDown("coBoilingPointSource", "");
	setPullDown("coBoilingPointUnit", "");
	setPullDown("coPhDetect", "");
	setPullDown("coPhSource", "");
	setPullDown("coFlashPointMethod", "");
	setPullDown("coPhysicalState", "");
	setPullDown("coPhysicalStateSource", "");
	
	setRadio("coHmisChronic", "");
	setRadio("coChronic", "");
	setRadio("coPolymerize", "");
	setRadio("coIncompatible", "");
	setRadio("coFireConditionsToAvoid", "");
	setRadio("coCorrosive", "");
	setRadio("coStable", "");
	setRadio("coWaterReactive", "");
	setRadio("coOxidizer", "");
	setRadio("coCarcinogen", "");
	setRadio("coSpecificGravityBasis", "");
	clearRadio("coSara311312Acute");
	clearRadio("coSara311312Chronic");
	clearRadio("coSara311312Fire");
	clearRadio("coSara311312Pressure");
	clearRadio("coSara311312Reactivity");
	
	setRadio("chronic", "");
	setRadio("polymerize", "");
	setRadio("incompatible", "");
	setRadio("fireConditionsToAvoid", "");
	setRadio("corrosive", "");
	setRadio("stable", "");
	setRadio("waterReactive", "");
	setRadio("oxidizer", "");
	setRadio("carcinogen", "");
	//setRadio("specificGravityBasis", "");
	setRadio("alloy", "");
	setRadio("ozoneDepletingCompound", "");
	setRadio("lowVolumeExempt", "");
	setRadio("detonable", "");
	setRadio("miscible", "");
	setRadio("pyrophoric", "");
	setRadio("spontaneouslyCombustible", "");
	setRadio("tscaStatement", "");
}

function customerOverrideChanged(){
	getMSDSfield("saveCustomerOverride").value = true;
}

function disableField(block,value, limit)
{
		var val = getMSDSfield(block);
		if(val != null)
			val.disabled = value;
		var units = getMSDSfield(block + 'Unit');
		if(units != null)
			units.disabled =  value;
		var basis = getMSDSfield(block + 'BasisAir');
		if(basis != null)	
			basis.disabled= value;
		basis = getMSDSfield(block + 'BasisWater');
		if(basis != null)	
			basis.disabled = value;	
		var temp = getMSDSfield(block + 'Temp');
		if(temp != null)
			temp.disabled = value;
		var tempUnit = getMSDSfield(block + 'TempUnit');
		if(tempUnit != null)
			tempUnit.disabled = value;

		var upper = getMSDSfield(block + 'Upper');
		if(upper != null)
			upper.disabled = limit;
		var lower = getMSDSfield(block + 'Lower');
		if(val == null && lower != null)
			lower.disabled = value;
		else if(lower != null)
			lower.disabled = limit;
}

function getElementsByNameOverride(tag, name) {
    
    var elem = document.getElementsByTagName(tag);
    var arr = new Array();
    for(i = 0,iarr = 0; i < elem.length; i++) {
         att = elem[i].getAttribute("name");
         if(att == name) {
              arr[iarr] = elem[i];
              iarr++;
         }
    }
    return arr;
}

function getMSDSfield(fieldName) {
	//return $("msds[0]." + fieldName);
	return $("msds[" + (selectedItemTab - 1) + "]." + fieldName);
}

function getMSDSfieldValue(fieldName) {
	var value = "";
	var field = getMSDSfield(fieldName);
	if (field) {
		value = field.value;
	}
	return value.trim();
}


function readonly (obj) {
	obj.checked = true;
	return false;
}

function setCoSpecGrav(value)
{
	if(value == "gas")
		getMSDSfield('coSpecificGravityBasisAir').checked = true;
	else if(value == "liquid")
		getMSDSfield('coSpecificGravityBasisWater').checked = true;
}

function setRadio(radioName, value) {
	var chk = getMSDSfield(radioName + "Y");
	if(chk != null)
		chk.checked = (value == 'Y');
	chk = getMSDSfield(radioName + "N");
	if(chk != null)
		chk.checked = (value == 'N');
	chk = getMSDSfield(radioName + "X");
	if(chk != null)
		chk.checked = (value == 'X');
	chk = getMSDSfield(radioName + "Air");
	if(chk != null)
		chk.checked = (value == 'A');
	chk = getMSDSfield(radioName + "Water");
	if(chk != null)
		chk.checked = (value == 'W');
	chk = getMSDSfield(radioName + "U");
	if(chk != null)
		chk.checked = (value == 'U');
}

function setPullDown(name, value){
	var pulldown = getMSDSfield(name);
	pulldown.selectedIndex = 0;
	for (var index = 0; index < pulldown.options.length; index++) {
		if (pulldown.options[index].value == value) {
			pulldown.selectedIndex = index;
			break;
		}
	}
}

function updateCustomerOverride(){
	blockCODisplays('coSpecificGravity',document.getElementById("msds[" + (selectedItemTab - 1) + "].coSpecificGravityDetect").value);
	blockCODisplays('coDensity',document.getElementById("msds[" + (selectedItemTab - 1) + "].coDensityDetect").value);
	blockCODisplays('coFlashPoint',document.getElementById("msds[" + (selectedItemTab - 1) + "].coFlashPointDetect").value);
	blockCODisplays('coBoilingPoint',document.getElementById("msds[" + (selectedItemTab - 1) + "].coBoilingPointDetect").value);
	blockCODisplays('coPh',document.getElementById("msds[" + (selectedItemTab - 1) + "].coPhDetect").value);
	blockCODisplays('coVaporPressure',document.getElementById("msds[" + (selectedItemTab - 1) + "].coVaporPressureDetect").value);
}

function auditCustomerData() {
    var message = "";
    message += checkInteger("coHealth", messagesData.nfpaHealth);
    message += checkInteger("coFlammability", messagesData.nfpaFlammability);
    message += checkInteger("coReactivity", messagesData.nfpaReactivity);

    message += checkInteger("coHmisHealth", messagesData.hmisHealth,null,null,'*');
    message += checkInteger("coHmisFlammability", messagesData.hmisFlammability);
    message += checkInteger("coHmisReactivity", messagesData.hmisReactivity);

    if(!getMSDSfield('coSpecificGravityLower').disabled)
        message += checkPositiveFloat("coSpecificGravityLower", messagesData.specificGravity);
    if(!getMSDSfield('coSpecificGravityUpper').disabled)
        message += checkPositiveFloat("coSpecificGravityUpper", messagesData.specificGravity);
    if(validateRange('coSpecificGravity'))
        message += messagesData.range.replace('{0}',messagesData.specificGravity) + '\n';

    if(!getMSDSfield('coDensity').disabled)
        message += checkPositiveFloat("coDensity", messagesData.density);
    if(!getMSDSfield('coDensityUpper').disabled)
        message += checkPositiveFloat("coDensityUpper", messagesData.density);
    if(validateRange('coDensity'))
        message += messagesData.range.replace('{0}',messagesData.density) + '\n';
    if(validateUnits('coDensity'))
        message += messagesData.nounit.replace('{0}',messagesData.density) + '\n';

    if(!getMSDSfield('coFlashPointLower').disabled)
        message += checkSignedFloat("coFlashPointLower", messagesData.flashPoint);
    if(!getMSDSfield('coFlashPointUpper').disabled)
        message += checkSignedFloat("coFlashPointUpper", messagesData.flashPoint);
    if(validateRange('coFlashPoint'))
        message += messagesData.range.replace('{0}',messagesData.flashPoint) + '\n';
    if(validateUnits('coFlashPoint'))
        message += messagesData.nounit.replace('{0}',messagesData.flashPoint) + '\n';

    if(!getMSDSfield('coBoilingPointLower').disabled)
        message += checkSignedFloat("coBoilingPointLower", messagesData.boilingPoint);
    if(!getMSDSfield('coBoilingPointUpper').disabled)
        message += checkSignedFloat("coBoilingPointUpper", messagesData.boilingPoint);
    if(validateRange('coBoilingPoint'))
        message += messagesData.range.replace('{0}',messagesData.boilingPoint) + '\n';
    if(validateUnits('coBoilingPoint'))
        message += messagesData.nounit.replace('{0}',messagesData.boilingPoint) + '\n';

    var coPh = getMSDSfield('coPh');
    var coPhUpper = getMSDSfield('coPhUpper');
    if(!coPh.disabled)
        {
            message += checkPositiveFloat("coPh", messagesData.ph);
            message += phCheck(coPh);
        }
    if(!coPhUpper.disabled)
        {
            message += checkPositiveFloat("coPhUpper", messagesData.ph);
            message += phCheck(coPhUpper);
        }
    if(validateRange('coPh'))
        message += messagesData.range.replace('{0}',messagesData.ph) + '\n';

    if(!getMSDSfield('coVaporPressure').disabled)
        message += checkFloat("coVaporPressure", messagesData.vaporPressure);
    if(!getMSDSfield('coVaporPressureUpper').disabled)
        message += checkFloat("coVaporPressureUpper", messagesData.vaporPressureUpper);
    if(!getMSDSfield('coVaporPressureTemp').disabled)
        message += checkFloat("coVaporPressureTemp", messagesData.vaporPressureTemp);
    if(validateRange('coVaporPressure'))
        message += messagesData.range.replace('{0}',messagesData.vaporPressure) + '\n';
    if(validateUnits('coVaporPressure'))
        message += messagesData.nounit.replace('{0}',messagesData.vaporPressure) + '\n';
    if(validateUnits('coVaporPressureTemp'))
        message += messagesData.nounit.replace('{0}',messagesData.vaporPressureTemp) + '\n';

    message += checkFloat("coVoc", messagesData.voc);
    message += checkFloat("coVocLower", messagesData.vocLower);
    message += checkFloat("coVocUpper", messagesData.vocUpper);
    if(validateRange('coVoc'))
        message += messagesData.range.replace('{0}',messagesData.voc) + '\n';
    if(validateUnits('coVoc'))
        message += messagesData.nounit.replace('{0}',messagesData.voc) + '\n';

    message += checkFloat("coVocLessH2oExempt", messagesData.vocLessH2oExempt);
    message += checkFloat("coVocLessH2oExemptLower", messagesData.vocLessH2oExemptLower);
    message += checkFloat("coVocLessH2oExemptUpper", messagesData.vocLessH2oExemptUpper);
    if(validateRange('coVocLessH2oExempt'))
        message += messagesData.range.replace('{0}',messagesData.vocLessH2oExempt) + '\n';
    if(validateUnits('coVocLessH2oExempt'))
        message += messagesData.nounit.replace('{0}',messagesData.vocLessH2oExempt) + '\n';

    message += checkFloat("coSolids", messagesData.solids);
    message += checkFloat("coSolidsLower", messagesData.solidsLower);
    message += checkFloat("coSolidsUpper", messagesData.solidsUpper);
    if(validateRange('coSolids'))
        message += messagesData.range.replace('{0}',messagesData.solids) + '\n';
    if(validateUnits('coSolids'))
        message += messagesData.nounit.replace('{0}',messagesData.solids) + '\n';

    message = validateVOCVLessDensity('coV','coD', message);

    if (message.length > 1) {
        alert(message);
        return false;
    }
    return true;
}

function showCoMsdsDocuments() {
    openWinGeneric('showmsdsdocuments.do?showDocuments=Yes&materialId='+ getMSDSfieldValue("materialId")+'&companyId='+document.getElementById("companyId").value,"showMsdsDocuments","800","350",'yes' );
}

function toggleCoBlocks(i)
{
	blockCODisplays('coSpecificGravity' + i,document.getElementById('msds[' + i + '].coSpecificGravityDetect').value);
	blockCODisplays('coDensity' + i,document.getElementById('msds[' + i + '].coDensityDetect').value);
	blockCODisplays('coFlashPoint' + i,document.getElementById('msds[' + i + '].coFlashPointDetect').value);
	blockCODisplays('coBoilingPoint' + i,document.getElementById('msds[' + i + '].coBoilingPointDetect').value);
	blockCODisplays('coPh' + i,document.getElementById('msds[' + i + '].coPhDetect').value);
	blockCODisplays('coVaporPressure' + i,document.getElementById('msds[' + i + '].coVaporPressureDetect').value);
}