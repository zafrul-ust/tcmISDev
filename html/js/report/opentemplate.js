var submitcount=0;
var updatecount=0;
function SubmitOnlyOnce() {
    if (submitcount == 0)
    {
        submitcount++;
        try
        {
         var target = document.all.item("TRANSITPAGE");
         target.style["display"] = "";
         var target1 = document.all.item("MAINPAGE");
         target1.style["display"] = "none";
        }
        catch (ex)
        {
        }

        return true;
    }
    else
    {
        alert(messagesData.submitOnlyOnce);
        return false;
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

function auditOpen() {
	var templateName = document.getElementById("templateId");
	if(templateName == null || templateName.value.trim().length == 0) {
		alert(messagesData.pleaseselectatemplate);
		return;
	}
	if ($v("reportType") == 'cost') {
		opener.$("action").value = "openTemplate";
	}else {
		opener.$("submitValue").value = "open";
	}
	opener.$("templateId").value = templateName.value;
	opener.document.forms[0].submit();

	try {
		var target = opener.document.all.item("transitPage").style["display"] = "";
		if ($v("reportType") == 'cost') {
			opener.document.all.item("searchMainPage").style["display"] = "none";
		}else {
			opener.document.all.item("MAINPAGE").style["display"] = "none";
		}
	}catch (ex){
	}

	closeWindow();
}

String.prototype.trim = function() {
   // skip leading and trailing whitespace
   // and return everything in between
   return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function closeWindow() {
	 window.close();
}

function deleteTemplate() {
	document.getElementById("action").value = "deleteTemplate";
	var reportTemplate = document.getElementById("templateId");
	document.getElementById("reportTemplateId").value = reportTemplate[reportTemplate.selectedIndex].value;
	parent.showPleaseWait();
}

function cancelAndClose() {
	closeWindow();
}

function myOnLoad() {
	if (document.getElementById("action") != null && document.getElementById("action").value.length > 0) {
		if (document.getElementById("noDataFound") != null) {
			cancelAndClose();	
		}
	}
	//select opened template
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
	}
	setTemplateDesc();
}

function templateChanged() {
	setTemplateDesc();
}

function setTemplateDesc() {
	var reportTemplate = document.getElementById("templateId");
	if (reportTemplate != null) {
		$("templateDescription").value = altTemplateDesc[reportTemplate.selectedIndex];
	}
}


