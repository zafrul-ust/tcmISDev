function validateVOCVLessDensity(prefixV,prefixD,message)
{
	var vocUnit = getMSDSfield(prefixV + "ocUnit").value;
	var vocLessH2oExemptUnit = getMSDSfield(prefixV + "ocLessH2oExemptUnit").value;
	var densityUnit;
	var checkDensity = false;
	if(getMSDSfield(prefixD + "ensityUnit").disabled == false)
		{
			densityUnit = getMSDSfield(prefixD + "ensityUnit").value;
			checkDensity = true;
		}
	if((vocUnit != ""  && vocLessH2oExemptUnit != "" && vocUnit == vocLessH2oExemptUnit) ||
	(checkDensity && densityUnit != "" && vocUnit != "" && vocUnit == densityUnit) ||
	(checkDensity && densityUnit != "" && vocLessH2oExemptUnit != "" && vocLessH2oExemptUnit == densityUnit))
		{
			var vocLessH2oExempt = null;
			var voc = null;
			var density = null;

			if(checkDensity)
				{
					if(getMSDSfield(prefixD + "ensityUpper").value != "")
						density = getMSDSfield(prefixD + "ensityUpper").value;
					else if(getMSDSfield(prefixD + "ensity").value != "")
						density = getMSDSfield(prefixD + "ensity").value;
				}

			if(getMSDSfield(prefixV + "ocLessH2oExemptUpper").value != "")
				vocLessH2oExempt = getMSDSfield(prefixV + "ocLessH2oExemptUpper").value;
			else if(getMSDSfield(prefixV + "ocLessH2oExempt").value != "")
				vocLessH2oExempt = getMSDSfield(prefixV + "ocLessH2oExempt").value;
			else if(getMSDSfield(prefixV + "ocLessH2oExemptLower").value != "")
				vocLessH2oExempt = getMSDSfield(prefixV + "ocLessH2oExemptLower").value;

			if(getMSDSfield(prefixV + "ocUpper").value != "")
				voc = getMSDSfield(prefixV + "ocUpper").value;
			else if(getMSDSfield(prefixV + "oc").value != "")
				voc = getMSDSfield(prefixV + "oc").value;
			else if(getMSDSfield(prefixV + "ocLower").value != "")
				voc = getMSDSfield(prefixV + "ocLower").value;

			if(checkDensity && density != "")
				{
					var densityDetect = getMSDSfield(prefixD + "ensityDetect").value;
					if(densityDetect == ">" || densityDetect == ">=" || densityDetect == "=" || densityDetect == ">>")
						{
							if(densityUnit == vocUnit && voc != "" && parseInt(density) < parseInt(voc))
								message += messagesData.compare.replace('{0}',messagesData.voc).replace('{1}',messagesData.density) + '\n';
							if(densityUnit == vocLessH2oExemptUnit && vocLessH2oExempt != "" && parseInt(density) < parseInt(vocLessH2oExempt) )
								message += messagesData.compare.replace('{0}',messagesData.vocLessH2oExempt).replace('{1}',messagesData.density) + '\n';
						}
				}

			if(vocUnit == vocLessH2oExemptUnit && vocLessH2oExempt != "" && voc != "" && parseInt(vocLessH2oExempt) < parseInt(voc))
				message += messagesData.compare.replace('{0}',messagesData.voc).replace('{1}',messagesData.vocLessH2oExempt) + '\n';
		}
	return message;
}

function validateRange(block)
{
	var val = getMSDSfield(block);
	var upper = getMSDSfield(block + 'Upper');
	var lower = getMSDSfield(block + 'Lower');

	if(val != null && val.disabled != true &&
	   lower != null && lower.disabled != true &&
	   parseInt(lower.value) > parseInt(val.value))
	   return true;
	else if (val != null && val.disabled != true &&
			upper != null && upper.disabled != true &&
			parseInt(val.value) > parseInt(upper.value))
		return true;
	else if(lower != null && lower.disabled != true &&
			upper != null && upper.disabled != true &&
			parseInt(lower.value) > parseInt(upper.value))
		return true;
	else
		return false;
}

function validateUnits(block)
{
	var val = getMSDSfield(block);
	var lower = getMSDSfield(block + 'Upper');
	var upper = getMSDSfield(block + 'Lower');
	var unit = getMSDSfield(block + 'Unit');

	if(val != null && val.disabled != true &&
	   val.value != '' && unit.value == '')
	   return true;
	else if(lower != null && lower.disabled != true &&
			lower.value != '' && unit.value == '')
		return true;
	else if (upper != null && upper.disabled != true &&
			upper.value != '' && unit.value == '')
		return true;
	else
		return false;
}

function phCheck(ph)
{
	if(ph != '' && (parseFloat(ph.value) > 14 || parseFloat(ph.value) < 1))
		return messagesData.phRange.replace('{0}', messagesData.ph).replace('{1}', '1').replace('{2}', '14') + '\n';
	else
		return "";
}

function checkFloat(field, name, low, high) {
	var value = getMSDSfieldValue(field);
	if (!isFloat(value, true)) {
		return formatMessage(messagesData.floatError, name) + "\n";
	}
	else if (low != null) {
		value = value * 1.0;
		if (value < low || value > high) {
			return formatMessage(messagesData.rangeError, name, low, high) + "\n";
		}
	}
	return "";
}

function checkSignedFloat(field, name, low, high) {
	var value = getMSDSfieldValue(field);
	if (!isSignedFloat(value, true)) {
		return formatMessage(messagesData.floatError, name) + "\n";
	}
	else if (low != null) {
		value = value * 1.0;
		if (value < low || value > high) {
			return formatMessage(messagesData.rangeError, name, low, high) + "\n";
		}
	}
	return "";
}

function checkPositiveFloat(field, name) {
	var value = getMSDSfieldValue(field);
	if (!isFloat(value, true) || !isEmpty(value) && !(value * 1.0 > 0.0)) {
		return formatMessage(messagesData.floatError, name) + "\n";
	}
	return "";
}

function checkInteger(field, name, low, high,ignoreString) {
	var value = getMSDSfieldValue(field);
	if (ignoreString != null) {
		value = value.replace(ignoreString, "");
	}
	if (!isInteger(value, true)) {
		return formatMessage(messagesData.integerError, name) + "\n";
	}
	else if (low != null) {
		value = value * 1;
		if (value < low || value > high) {
			return formatMessage(messagesData.rangeError, name, low, high) + "\n";
		}
	}
	return "";
}

