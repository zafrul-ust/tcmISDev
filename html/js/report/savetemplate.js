var submitcount=0;
var updatecount=0;
var homeCompanyLogin;

function SubmitOnlyOnce() {
    if(!auditSave()){
        return false;
    }
    else {
        submitOnlyOnce();
    }
}

function clearSelection() {
   var myVar = document.getElementById("selectedValue");
   myVar.value = "";
}

function selectValue(selectedValue, selectedValueDesc) {
   var myVar = document.getElementById("selectedValue");
   var myVarDesc = document.getElementById("selectedValueDesc");
   myVar.value = selectedValue;
   myVarDesc.value = selectedValueDesc;
}

function auditSave() {
	if (validateData()) {
		if ($v("reportType") == 'cost') {
			opener.saveTemplate();
		}else {
	        opener.$("submitValue").value = "save";
	        var url = opener.window.location.href;
	        if(url.indexOf('adhocinventory') != -1 || url.indexOf('adhocmaterial') != -1 || url.indexOf('adhocusage') != -1)	
	        	opener.subAdHocForm();
	        else
	        	opener.document.forms[0].submit();
		}
		closeWindow();
	}	
}

function validateData() {
    var updateExisting = document.getElementById("updateExisting");
    //users have templates
	if (updateExisting != null) {
        //update template
		if (updateExisting.checked) {
			var templateName = $("existingTemplateName");
	 		if(templateName == null || templateName.value.trim().length == 0) {
				alert(messagesData.pleaseentertemplatename);
				return false;
	 		}
			var templateId = document.getElementById("templateId");
			opener.$("templateId").value = templateId.value;
			opener.$("templateName").value = $v("existingTemplateName");
			opener.$("templateDescription").value = $v("existingTemplateDescription");
			if (!validateGateKeepingDetails()) {
				alert(messagesData.pleaseenterresultdetails);
				return false;				
			}			
		}else {
			//new template
			var templateName = document.getElementById("newExistingTemplateName");
	 		if(templateName == null || templateName.value.trim().length == 0) {
				alert(messagesData.pleaseentertemplatename);
				return false;				
	 		}else {
				var reportTemplate = document.getElementById("templateId");
				if (reportTemplate != null) {
					for (var i = 0; i < reportTemplate.length; i++ ) {
						if(reportTemplate.options[i].text == templateName.value) {
							var answer = confirm(messagesData.templateexistwarning)
							if (answer){
								//overwriting
							}else{
								//cancel
								return false;								
							}
						}
					}
				}
			}
			opener.$("templateId").value = "";
			opener.$("templateName").value = templateName.value;
			opener.$("templateDescription").value = $v("newExistingTemplateDescription");
			if (!validateGateKeepingDetails()) {
				alert(messagesData.pleaseenterresultdetails);
				return false;				
			}
		}
	}else {
        var templateName = document.getElementById("newTemplateName");
	 	if(templateName == null || templateName.value.trim().length == 0) {
			alert(messagesData.pleaseentertemplatename);
			return false;			
	 	}
		opener.$("templateId").value = "";
		opener.$("templateName").value = templateName.value;
		opener.$("templateDescription").value = $v("newTemplateDescription");
		if (!validateGateKeepingDetails()) {
			alert(messagesData.pleaseenterresultdetails);
			return false;
		}
	}
	return true;
}

function validateGateKeepingDetails() {	
	if ($v("gateKeeping") == 'true') {
		if ( $v("emailSubject") == "" || $v("emailMessage") == "" ||  $v("emailUserGroupId") == "" ) {			
			return false;			
		}	
		opener.$("includeOpenOrders").value = $("includeOpenOrders").checked;
		opener.$("gateKeeping").value = $v("gateKeeping");
		opener.$("emailSubjectNeg").value = $v("emailSubjectNeg");
		opener.$("emailSubject").value = $v("emailSubject");
		opener.$("emailMessageNeg").value = $v("emailMessageNeg");
		opener.$("emailMessage").value = $v("emailMessage");
		opener.$("emailUserGroupIdNeg").value = $v("emailUserGroupIdNeg");
		opener.$("emailUserGroupId").value = $v("emailUserGroupId");
	}

	return true;
}

String.prototype.trim = function() {
   // skip leading and trailing whitespace
   // and return everything in between
   return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function closeWindow() {
    window.close();
}

function onMyLoad() {
	var updateExisting = document.getElementById("updateExisting");
	//users have templates
	if (updateExisting != null) {
		var templateFromParent = opener.document.getElementById("templateId");
		if (templateFromParent != null && templateFromParent.value.length >0) {
			var reportTemplate = document.getElementById("templateId");
			if (reportTemplate != null) {
				for (var i = 0; i < reportTemplate.length; i++ ) {
					if(reportTemplate.options[i].value == templateFromParent.value) {
						reportTemplate.options[i].selected = true;
						break;
					}
				}
			}
			updateExisting.checked = true;
			document.getElementById("existingTemplateName").focus();
			document.getElementById("newExistingTemplateName").disabled = true;
			document.getElementById("newExistingTemplateDescription").disabled = true;
		}else {
			document.getElementById("createNew").checked = true;
			document.getElementById("newExistingTemplateName").focus();
			document.getElementById("newExistingTemplateDescription").disabled = "";
			document.getElementById("templateId").disabled=true;
			document.getElementById("existingTemplateName").disabled = true;
			document.getElementById("existingTemplateDescription").disabled = true;
		}
		setTemplateDesc();
		setGateKeepingDetails();
	} else {
		document.getElementById("newTemplateName").focus();
		document.getElementById("newTemplateDescription").disabled = "";
	}
} //end of method

function templateChanged() {
	setTemplateDesc();
}

function setTemplateDesc() {
	var reportTemplate = document.getElementById("templateId");
	if (reportTemplate != null) {
		$("existingTemplateDescription").value = altTemplateDesc[reportTemplate.selectedIndex];
		$("existingTemplateName").value = altTemplateName[reportTemplate.selectedIndex];
	}
}

function setGateKeepingDetails() {
	var gateKeeping = document.getElementById("gateKeeping").value;
	if (gateKeeping != null && gateKeeping == 'true') {
		if (opener.$("includeOpenOrders").value == "Y")
			$("includeOpenOrders").checked = true;
		//$("includeOpenOrders").value = opener.$("includeOpenOrders").value;
		$("emailSubjectNeg").value = opener.$("emailSubjectNeg").value;
		$("emailSubject").value = opener.$("emailSubject").value;
		$("emailMessageNeg").value = opener.$("emailMessageNeg").value;
		$("emailMessage").value = opener.$("emailMessage").value;
		var emailUserGroupIdNegSel = opener.$("emailUserGroupIdNeg");
		var emailUserGroupIdSel = opener.$("emailUserGroupId");
		var emailUserGroupIdNegDd = $("emailUserGroupIdNeg");
		var emailUserGroupIdDd = $("emailUserGroupId");
		if (emailUserGroupIdNegSel != null) {
			for (var ineg = 0; ineg < emailUserGroupIdNegDd.length; ineg++ ) {
				if(emailUserGroupIdNegDd.options[ineg].value == emailUserGroupIdNegSel.value) {
					emailUserGroupIdNegDd.options[ineg].selected = true;
					break;
				}
			}
		}
		if (emailUserGroupIdSel != null) {
			for (var ipos = 0; ipos < emailUserGroupIdDd.length; ipos++ ) {
				if(emailUserGroupIdDd.options[ipos].value == emailUserGroupIdSel.value) {
					emailUserGroupIdDd.options[ipos].selected = true;
					break;
				}
			}
		}
	}
}


function statusChanged() {
	var updateExisting = document.getElementById("updateExisting");
	if (updateExisting.checked) {
        document.getElementById("newExistingTemplateName").disabled=true;
		document.getElementById("newExistingTemplateDescription").disabled = true;
		document.getElementById("templateId").disabled="";
		document.getElementById("existingTemplateName").disabled = "";
		document.getElementById("existingTemplateDescription").disabled = "";
	}else {
        document.getElementById("newExistingTemplateName").disabled="";
		document.getElementById("newExistingTemplateDescription").disabled = "";
		document.getElementById("templateId").disabled=true;
		document.getElementById("existingTemplateName").disabled = true;
		document.getElementById("existingTemplateDescription").disabled = true;
	}
}


