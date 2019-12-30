/*Call this if you want to initialize something on load in the search frame.*/
function mySearchOnload()
{
 document.getElementById('transitPage').style["display"] = "none";
 document.getElementById('searchMainPage').style["display"] = "";
 showCompany();
}

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 var flag = validateForm();
  if(flag) {
	document.getElementById('searchMainPage').style["display"] = "none";
	document.getElementById('transitPage').style["display"] = "";
	return true;
  }
  else
  {
    return false;
  }
}

function cancel() {
	window.close();
}

function callClearTemplate() {
	document.getElementById('transitPage').style["display"] = "";
	document.getElementById('searchMainPage').style["display"] = "none";
	document.getElementById('action').value="clearTemplate"; 
	document.costReportForm.target='';
	setTimeout("document.costReportForm.submit()",300);
}

function callSaveTemplate() {
	if (validateForm()) {
		var templateName = document.getElementById('templateName');
		var tmp;
		if (templateName != null) {
			tmp = prompt(messagesData.getTemplateName,templateName.value);
		}else {
			tmp = prompt(messagesData.getTemplateName,'');
		}
		if (tmp != null && tmp.length > 0) {
			document.getElementById('templateLabel').style["display"] = "";
			document.getElementById('templateName').value = tmp;
			document.getElementById('templateLabelSpan').innerHTML = tmp;
			document.getElementById('action').value="saveTemplate";
			document.costReportForm.target='_save_template';
			openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_save_template','400','170','yes');
			setTimeout("document.costReportForm.submit()",300);
		}
	}
}

function callOpenTemplate() {
	window.opener.openTemplate(document.getElementById('templateName').value);
	window.close();
}

function getTemplate() {
	document.getElementById('action').value="getTemplateList";
	document.costReportForm.target='_get_template';
	openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_get_template','400','170','yes');	
	setTimeout("document.costReportForm.submit()",300);
}

function openTemplate(templateName) {
	document.getElementById('transitPage').style["display"] = "";
	document.getElementById('searchMainPage').style["display"] = "none";
	document.getElementById('action').value="openTemplate"; 
	document.getElementById('templateName').value=templateName;
	document.costReportForm.target='';
	setTimeout("document.costReportForm.submit()",300);
}

function validateForm() {
	var result = true;
/*	
	if (document.getElementById("searchByInvoiceNumber") != null) {
		if (document.getElementById("searchByInvoiceNumber").value.length > 0) {
			if(isNaN(document.getElementById("searchByInvoiceNumber").value)) {
				alert(messagesData.invoice);
				result = false;
			}
		}
	}  */
	if (document.getElementById("searchByInvoicePeriod") != null) {
		if (document.getElementById("searchByInvoicePeriod").value.length > 0) {
			if(isNaN(document.getElementById("searchByInvoicePeriod").value)) {
				alert(messagesData.invoicePeriod);
				result = false;
			}
		}
	}

	//make sure at least one report field is checked
	var reportFieldCount = 0;
	var obj = document.getElementsByTagName("input");
	for (var i = 0; i < obj.length;i++) {
		if (obj[i].type == 'checkbox') {
			if (obj[i].checked) {
				reportFieldCount++;
			}
		}
	}
	if (reportFieldCount == 0) {
		alert(messagesData.selectreportfield);
		result = false;
	}

	return result;
}

function clearInvoiceDate() {
	document.getElementById("invoiceDateBegin").value="";
	document.getElementById("invoiceDateEnd").value="";
}

function clearDeliveredDate() {
	document.getElementById("dateDeliveredBegin").value="";
	document.getElementById("dateDeliveredEnd").value="";
}

function invalidateRequestor()
{
 var requestorName  =  document.getElementById("requestorName");
 var requestorId  =  document.getElementById("requestorId");
 if (requestorName.value.length == 0) {
   requestorName.className = "inputBox";
 }else {
   requestorName.className = "inputBox red";
 }
 //set to empty
 requestorId.value = "";
}

function getPersonnel()
{
   var searchText = "";
   var requestorName  =  document.getElementById("requestorName");
   if (requestorName != null) {
     var requestorId  =  document.getElementById("requestorId");
     if(requestorName.value.length > 0 && requestorId != null) {
       if(requestorId.value.length == 0) {
         searchText = requestorName.value;
       }
     }
   }
   loc = "searchpersonnelmain.do?displayElementId=requestorName&valueElementId=requestorId&searchText="+searchText;
   openWinGeneric(loc,"searchpersonnel","600","450","yes","50","50","no")
}

function generateReport() {
	document.getElementById("companyName").value = document.getElementById('companyId').options[document.getElementById("companyId").selectedIndex].text;
	document.getElementById("facilityName").value = document.getElementById('facilityId').options[document.getElementById("facilityId").selectedIndex].text;
	document.getElementById("applicationName").value = document.getElementById('application').options[document.getElementById("application").selectedIndex].text;
	document.getElementById("accountSysName").value = document.getElementById('accountSysId').options[document.getElementById("accountSysId").selectedIndex].text;
	document.getElementById("uomName").value = document.getElementById('uom').options[document.getElementById("uom").selectedIndex].text;
	document.getElementById("searchByName").value = document.getElementById('searchBy').options[document.getElementById("searchBy").selectedIndex].text;
	document.getElementById("itemTypeName").value = document.getElementById('searchByItemType').options[document.getElementById("searchByItemType").selectedIndex].text;
	document.getElementById("invoiceTypeName").value = document.getElementById('searchByInvoiceType').options[document.getElementById("searchByInvoiceType").selectedIndex].text;
	var flag = validateForm();
   if (flag) {
		document.getElementById('action').value="generateReport";
		if (document.getElementById('byHtml').checked) {
		   document.costReportForm.target='_cost_report_file_html';
			openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_cost_report_file_html','1024','600','yes');
		}else {
			document.costReportForm.target='_cost_report_file_xls';
			openWinGenericViewFile('/tcmIS/common/generatingreport.jsp','_cost_report_file_xls','1024','600','yes');
		}
		setTimeout("document.costReportForm.submit()",300);
	 }
}

function createExcel() {
	var flag = validateForm();
   if (flag) {
		document.getElementById('action').value="createExcel";
		document.costReportForm.target='_cost_report_excel';
		openWinGenericViewFile('/tcmIS/common/loadingpleasewait.jsp','_cost_report_excel','650','600','yes');
		setTimeout("document.costReportForm.submit()",300);
	 }
}


function showCompany() {
  var companyIdArray = altCompanyId;
  var companyNameArray = altCompanyName;
  var selectedCompanyId = document.getElementById("selectedCompanyId");
  var selectedIndex = 0;
  for (var i=0; i < companyIdArray.length; i++) {
    setOption(i,companyNameArray[i],companyIdArray[i], "companyId");
	 if (selectedCompanyId != null) {
		 if(selectedCompanyId.value == companyIdArray[i]) {
			 selectedIndex = i;
		 }
	 }
  }
  document.getElementById("companyId").selectedIndex = selectedIndex;
  companyChanged();
}

function companyChanged() {
  var companyIdO = document.getElementById("companyId");
  var reportGroup0 = document.getElementById("reportingGroup");
  var selectedCompany = companyIdO.value;

  for (var i = reportGroup0.length; i >= 0;i--) {
    reportGroup0[i] = null;
  }

  showCostReportGroup(selectedCompany);
  costReportGroupChanged();
}

function showCostReportGroup(selectedCompany) {
  var reportGroupArray = altReportGroup[selectedCompany];
  var selectedReportingGroup = document.getElementById("selectedReportingGroup");
  var selectedIndex = 0;
	for (var i=0; i < reportGroupArray.length; i++) {
    setOption(i,reportGroupArray[i],reportGroupArray[i], "reportingGroup");
	 if (selectedReportingGroup != null) {
		 if(selectedReportingGroup.value == reportGroupArray[i]) {
			 selectedIndex = i;
		 }
	 }
  }
  document.getElementById("reportingGroup").selectedIndex = selectedIndex;
}

function costReportGroupChanged() {
  var companyIdO = document.getElementById("companyId");
  var reportGroup0 = document.getElementById("reportingGroup");
  var facilityO = document.getElementById("facilityId");
  var selectedCompany = companyIdO.value;
  var selectedReportGroup = reportGroup0.value;

  for (var i = facilityO.length; i >= 0;i--) {
    facilityO[i] = null;
  }

  if (selectedReportGroup == 'My Reporting Groups') {
  	showFacility(selectedReportGroup);
  }else {
  	showFacility(selectedCompany+selectedReportGroup);
  }
  facilityChanged();
}


function showFacility(selectedCompanyReportGroup) {
  var facilityIdArray = altFacilityId[selectedCompanyReportGroup];
  var facilityNameArray = altFacilityName[selectedCompanyReportGroup];
  var selectedFacilityId = document.getElementById("selectedFacilityId");
  var selectedIndex = 0;
  for (var i=0; i < facilityIdArray.length; i++) {
    setOption(i,facilityNameArray[i],facilityIdArray[i], "facilityId");
	 if (selectedFacilityId != null) {
		 if(selectedFacilityId.value == facilityIdArray[i]) {
			 selectedIndex = i;
		 }
	 }
  }
  document.getElementById("facilityId").selectedIndex = selectedIndex;
}

function facilityChanged() {
  var companyIdO = document.getElementById("companyId");
  var reportGroup0 = document.getElementById("reportingGroup");
  var facilityO = document.getElementById("facilityId");
  var applicationIdO = document.getElementById("application");
  var accountSysIdO = document.getElementById("accountSysId");
  var selectedCompany = companyIdO.value;
  var selectedReportGroup = reportGroup0.value;
  var selectedFacility = facilityO.value;

  for (var i = applicationIdO.length; i >= 0;i--) {
    applicationIdO[i] = null;
  }

  for (var i = accountSysIdO.length; i >= 0;i--) {
    accountSysIdO[i] = null;
  }

  if (selectedFacility == 'My Facilities') {
	showApplicationLinks(selectedFacility);
	showAccountSys(selectedFacility);
  }else {
  	showApplicationLinks(selectedCompany+selectedReportGroup+selectedFacility);
  	showAccountSys(selectedCompany+selectedReportGroup+selectedFacility);
  }
  accountSysChanged();
}

function showApplicationLinks(selectedCompanyGroupFacility)
{
  var applicationIdArray = altApplication[selectedCompanyGroupFacility];
  var applicationDescArray = altApplicationDesc[selectedCompanyGroupFacility];

  var selectedApplication = document.getElementById("selectedApplication");
  var selectedIndex = 0;
	if (applicationIdArray.length == 0) {
		applicationIdArray[0] = "My Work Areas";
		applicationDescArray[0] = "My Work Areas";
	}

  for (var i=0; i < applicationIdArray.length; i++) {
    setApplication(i,htmlDencode(applicationDescArray[i]),applicationIdArray[i]);
	 if (selectedApplication != null) {
		 if(selectedApplication.value == applicationIdArray[i]) {
			 selectedIndex = i;
		 }
	 }
  }
  document.getElementById("application").selectedIndex = selectedIndex;
}

function setApplication(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var applicationIdO = document.getElementById("application");
  applicationIdO[href] = optionName;
}

function showAccountSys(selectedCompanyGroupFacility)
{
	var accountSysIdArray;
	var accountSysDescArray;

	if (document.getElementById("facilityId").value == 'My Facilities') {
		if (document.getElementById("reportingGroup").value == 'My Reporting Groups') {
			accountSysIdArray = altCompanyAccountSysId[document.getElementById("companyId").value];
	   	accountSysDescArray = altCompanyAccountSysDesc[document.getElementById("companyId").value];
		}else {
			accountSysIdArray = altGroupAccountSysId[document.getElementById("companyId").value+document.getElementById("reportingGroup").value];
	   	accountSysDescArray = altGroupAccountSysDesc[document.getElementById("companyId").value+document.getElementById("reportingGroup").value];
		}
	}else {
	  accountSysIdArray = altAccountSysId[selectedCompanyGroupFacility];
	  accountSysDescArray = altAccountSysDesc[selectedCompanyGroupFacility];
	}
  var selectedAccountSysId = document.getElementById("selectedAccountSysId");
  var selectedIndex = 0;
	if (accountSysIdArray.length == 0) {
		accountSysIdArray[0] = "My Accounting Systems";
		accountSysDescArray[0] = "My Accounting Systems";
	}

  for (var i=0; i < accountSysIdArray.length; i++) {
	 setAccountSystem(i,htmlDencode(accountSysDescArray[i]),accountSysIdArray[i]);
	 if (selectedAccountSysId != null) {
		 if(selectedAccountSysId.value == accountSysIdArray[i]) {
			 selectedIndex = i;
		 }
	 }
  }
  document.getElementById("accountSysId").selectedIndex = selectedIndex;
}

function setAccountSystem(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var accountSysIdO = document.getElementById("accountSysId");
  accountSysIdO[href] = optionName;
}

function accountSysChanged() {
   var companyIdO = document.getElementById("companyId");
   var reportGroup0 = document.getElementById("reportingGroup");
   var facilityO = document.getElementById("facilityId");
   var accountSysIdO = document.getElementById("accountSysId");

   var selectedCompany = companyIdO.value;
   var selectedReportGroup = reportGroup0.value;
   var selectedFacility = facilityO.value;
   var selectedAccountSys = accountSysIdO.value;

	var chargeTypeArray;
	if (document.getElementById("facilityId").value == 'My Facilities') {
		if (document.getElementById("reportingGroup").value == 'My Reporting Groups') {
			chargeTypeArray =  altCompanyChargeType[selectedCompany+selectedAccountSys];
		}else {
			chargeTypeArray =  altGroupChargeType[selectedCompany+selectedReportGroup+selectedAccountSys];
		}
	}else {
		chargeTypeArray =  altChargeType[selectedCompany+selectedReportGroup+selectedFacility+selectedAccountSys];
	}
	if (chargeTypeArray != null) {
		document.getElementById("chargeTypeDirect").checked="true";
		document.getElementById("chargeTypeIndirect").checked="";
		if (chargeTypeArray.length == 1) {
			document.getElementById("chargeTypeTd").style.display="none";
			document.getElementById("chargeTypeTdBlank").style.display="none";
			document.getElementById("blank5").style.display="";
			showChargeNumber("d");
		}else {
			document.getElementById("chargeTypeTd").style.display="";
			document.getElementById("chargeTypeTdBlank").style.display="";
			document.getElementById("blank5").style.display="none";
			showChargeNumber("d");
		}
	}else {
		//don't show charge type and numbers
		document.getElementById("chargeTypeTd").style.display="none";
		document.getElementById("chargeTypeTdBlank").style.display="none";
		document.getElementById("poLabelTd").style.display="none";
		document.getElementById("poTd").style.display="none";
		document.getElementById("chargeLabelTd").style.display="none";
		document.getElementById("chargeTd").style.display="none";
		document.getElementById("chargeLabel2Td").style.display="none";
		document.getElementById("charge2Td").style.display="none";
		document.getElementById("chargeLabel3Td").style.display="none";
		document.getElementById("charge3Td").style.display="none";
		document.getElementById("chargeLabel4Td").style.display="none";
		document.getElementById("charge4Td").style.display="none";
		//
		document.getElementById("blank1").style.display="";
		document.getElementById("blank2").style.display="";
		document.getElementById("blank3").style.display="";
		document.getElementById("blank4").style.display="";
		document.getElementById("blank5").style.display="";
	}
}

function chargeTypeChanged(selectedChargeType) {
	showChargeNumber(selectedChargeType);
}

function showChargeNumber(selectedChargeType) {
   var companyIdO = document.getElementById("companyId");
   var reportGroup0 = document.getElementById("reportingGroup");
   var facilityO = document.getElementById("facilityId");
   var accountSysIdO = document.getElementById("accountSysId");

	var selectedCompany = companyIdO.value;
   var selectedReportGroup = reportGroup0.value;
   var selectedFacility = facilityO.value;
   var selectedAccountSys = accountSysIdO.value;

	var chargeTypeArray =  altChargeType[selectedCompany+selectedReportGroup+selectedFacility+selectedAccountSys];
	var poArray =  altPoRequired[selectedCompany+selectedReportGroup+selectedFacility+selectedAccountSys];
	var prAccountArray =  altPrAccountRequired[selectedCompany+selectedReportGroup+selectedFacility+selectedAccountSys];
	var labelArray =  altChargeLabel1[selectedCompany+selectedReportGroup+selectedFacility+selectedAccountSys];
	var label2Array =  altChargeLabel2[selectedCompany+selectedReportGroup+selectedFacility+selectedAccountSys];
	var label3Array =  altChargeLabel3[selectedCompany+selectedReportGroup+selectedFacility+selectedAccountSys];
	var label4Array =  altChargeLabel4[selectedCompany+selectedReportGroup+selectedFacility+selectedAccountSys];
	if (document.getElementById("facilityId").value == 'My Facilities') {
		if (document.getElementById("reportingGroup").value == 'My Reporting Groups') {
			chargeTypeArray =  altCompanyChargeType[selectedCompany+selectedAccountSys];
			poArray =  altCompanyPoRequired[selectedCompany+selectedAccountSys];
	   	prAccountArray =  altCompanyPrAccountRequired[selectedCompany+selectedAccountSys];
	   	labelArray =  altCompanyChargeLabel1[selectedCompany+selectedAccountSys];
	   	label2Array =  altCompanyChargeLabel2[selectedCompany+selectedAccountSys];
			label3Array =  altCompanyChargeLabel3[selectedCompany+selectedAccountSys];
			label4Array =  altCompanyChargeLabel4[selectedCompany+selectedAccountSys];
		}else {
			chargeTypeArray =  altGroupChargeType[selectedCompany+selectedReportGroup+selectedAccountSys];
			poArray =  altGroupPoRequired[selectedCompany+selectedReportGroup+selectedAccountSys];
	   	prAccountArray =  altGroupPrAccountRequired[selectedCompany+selectedReportGroup+selectedAccountSys];
	   	labelArray =  altGroupChargeLabel1[selectedCompany+selectedReportGroup+selectedAccountSys];
	   	label2Array =  altGroupChargeLabel2[selectedCompany+selectedReportGroup+selectedAccountSys];
			label3Array =  altGroupChargeLabel3[selectedCompany+selectedReportGroup+selectedAccountSys];
			label4Array =  altGroupChargeLabel4[selectedCompany+selectedReportGroup+selectedAccountSys];
		}

	}else {
		chargeTypeArray =  altChargeType[selectedCompany+selectedReportGroup+selectedFacility+selectedAccountSys];
		poArray =  altPoRequired[selectedCompany+selectedReportGroup+selectedFacility+selectedAccountSys];
	   prAccountArray =  altPrAccountRequired[selectedCompany+selectedReportGroup+selectedFacility+selectedAccountSys];
	   labelArray =  altChargeLabel1[selectedCompany+selectedReportGroup+selectedFacility+selectedAccountSys];
	   label2Array =  altChargeLabel2[selectedCompany+selectedReportGroup+selectedFacility+selectedAccountSys];
		label3Array =  altChargeLabel3[selectedCompany+selectedReportGroup+selectedFacility+selectedAccountSys];
		label4Array =  altChargeLabel4[selectedCompany+selectedReportGroup+selectedFacility+selectedAccountSys];
	}

	var dataIndex = 0;
	for (var i = 0; i < chargeTypeArray.length; i++) {
		if (selectedChargeType == chargeTypeArray[i]) {
			dataIndex = i;
			break;
		}
	}
	//show both po and charge number
	if (poArray[dataIndex] == 'p' && prAccountArray[dataIndex] == 'y') {
		document.getElementById("poLabelTd").style.display="";
		document.getElementById("poLabel").innerHTML = messagesData.poLabel;
		document.getElementById("poTd").style.display="";
		document.getElementById("blank5").style.display="none";
		if (labelArray[dataIndex] != 'Charge Number 1') {
			document.getElementById("chargeLabelTd").style.display="";
			document.getElementById("label1").innerHTML = labelArray[dataIndex];
			document.getElementById("chargeTd").style.display="";
			document.getElementById("blank4").style.display="none";
		}else {
			document.getElementById("chargeLabelTd").style.display="none";
			document.getElementById("chargeTd").style.display="none";
			document.getElementById("blank5").style.display="";
		}
		if (label2Array[dataIndex] != 'Charge Number 2') {
			document.getElementById("chargeLabel2Td").style.display="";
			document.getElementById("label2").innerHTML = label2Array[dataIndex];
			document.getElementById("charge2Td").style.display="";
			document.getElementById("blank3").style.display="none";
		}else {
			document.getElementById("chargeLabel2Td").style.display="none";
			document.getElementById("charge2Td").style.display="none";
			document.getElementById("blank3").style.display="";
		}
		if (label3Array[dataIndex] != 'Charge Number 3') {
			document.getElementById("chargeLabel3Td").style.display="";
			document.getElementById("label3").innerHTML = label3Array[dataIndex];
			document.getElementById("charge3Td").style.display="";
			document.getElementById("blank2").style.display="none";
		}else {
			document.getElementById("chargeLabel3Td").style.display="none";
			document.getElementById("charge3Td").style.display="none";
			document.getElementById("blank2").style.display="";
		}
		if (label4Array[dataIndex] != 'Charge Number 4') {
			document.getElementById("chargeLabel4Td").style.display="";
			document.getElementById("label4").innerHTML = label4Array[dataIndex];
			document.getElementById("charge4Td").style.display="";
			document.getElementById("blank1").style.display="none";
		}else {
			document.getElementById("chargeLabel4Td").style.display="none";
			document.getElementById("charge4Td").style.display="none";
			document.getElementById("blank1").style.display="";
		}
	}else {
		//show only po
		if (poArray[dataIndex] == 'p') {
			document.getElementById("poLabelTd").style.display="";
			document.getElementById("poLabel").innerHTML = messagesData.poLabel;
			document.getElementById("poTd").style.display="";
			document.getElementById("chargeTd").style.display="none";
			document.getElementById("charge2Td").style.display="none";
			document.getElementById("charge3Td").style.display="none";
			document.getElementById("charge4Td").style.display="none";
			document.getElementById("blank5").style.display="none";
			document.getElementById("blank4").style.display="none";
			document.getElementById("blank3").style.display="none";
			document.getElementById("blank2").style.display="";
			document.getElementById("blank1").style.display="";

		}else {
			document.getElementById("poLabelTd").style.display="none";
			document.getElementById("poTd").style.display="none";
			document.getElementById("blank5").style.display="";
			document.getElementById("blank4").style.display="";
			document.getElementById("blank3").style.display="";
		}
		//show only charge number
		if (prAccountArray[dataIndex] == 'y') {
			document.getElementById("poLabelTd").style.display="none";
			document.getElementById("poTd").style.display="none";
			document.getElementById("blank5").style.display="";
			if (labelArray[dataIndex] != 'Charge Number 1') {
				document.getElementById("chargeLabelTd").style.display="";
				document.getElementById("label1").innerHTML = labelArray[dataIndex];
				document.getElementById("chargeTd").style.display="";
				document.getElementById("blank4").style.display="none";
			}else {
				document.getElementById("chargeLabelTd").style.display="none";
				document.getElementById("chargeTd").style.display="none";
				document.getElementById("blank4").style.display="";
			}
			if (label2Array[dataIndex] != 'Charge Number 2') {
				document.getElementById("chargeLabel2Td").style.display="";
				document.getElementById("label2").innerHTML = label2Array[dataIndex];
				document.getElementById("charge2Td").style.display="";
				document.getElementById("blank3").style.display="none";
			}else {
				document.getElementById("chargeLabel2Td").style.display="none";
				document.getElementById("charge2Td").style.display="none";
				document.getElementById("blank3").style.display="";
			}
			if (label3Array[dataIndex] != 'Charge Number 3') {
				document.getElementById("chargeLabel3Td").style.display="";
				document.getElementById("label3").innerHTML = label3Array[dataIndex];
				document.getElementById("charge3Td").style.display="";
				document.getElementById("blank2").style.display="none";
			}else {
				document.getElementById("chargeLabel3Td").style.display="none";
				document.getElementById("charge3Td").style.display="none";
				document.getElementById("blank2").style.display="";
			}
			if (label4Array[dataIndex] != 'Charge Number 4') {
				document.getElementById("chargeLabel4Td").style.display="";
				document.getElementById("label4").innerHTML = label4Array[dataIndex];
				document.getElementById("charge4Td").style.display="";
				document.getElementById("blank1").style.display="none";
			}else {
				document.getElementById("chargeLabel4Td").style.display="none";
				document.getElementById("charge4Td").style.display="none";
				document.getElementById("blank1").style.display="";
			}
		}else {
			document.getElementById("chargeLabelTd").style.display="none";
			document.getElementById("chargeTd").style.display="none";
			document.getElementById("chargeLabel2Td").style.display="none";
			document.getElementById("charge2Td").style.display="none";
			document.getElementById("chargeLabel3Td").style.display="none";
			document.getElementById("charge3Td").style.display="none";
			document.getElementById("chargeLabel4Td").style.display="none";
			document.getElementById("charge4Td").style.display="none";
			//
			document.getElementById("blank2").style.display="";
			document.getElementById("blank1").style.display="";
		}
	}
}

function saveTemplateAudit() {
   if(!validateForm()){
      return;
   }
   document.costReportForm.target='';
   openWinGeneric("savetemplate.do?reportType=cost&templateId="+$v("templateId"),"savetemplate","600","370","yes","80","80");
}

function saveTemplate() {
	$('action').value="saveTemplate";
	document.costReportForm.target='';
	setTimeout("document.costReportForm.submit()",300);
}

function openTemplateAudit() {
   document.costReportForm.target='';
   openWinGeneric("opentemplate.do?reportType=cost","opentemplate","600","210","yes","80","80");
}

function publishTemplateAudit() {
   if(!validateForm()){
      return;
   }
   document.costReportForm.target='';
	var tmpTemplateName = $v("globalizationLabelLetter")+$v("templateId")+"-"+$v("templateName");
	openWinGeneric("publishtemplate.do?action=publish&reportType=cost&calledFrom=costReport&templateId="+$v("templateId")+"&templateName="+tmpTemplateName
		 				,"publishTemplate","650","275","yes","80","80");
}

function unpublishTemplateAudit() {
   if(!validateForm()){
      return;
   }
   document.costReportForm.target='';
	var tmpTemplateName = $v("globalizationLabelLetter")+$v("templateId")+"-"+$v("templateName");
	openWinGeneric("publishtemplate.do?action=unpublish&reportType=cost&calledFrom=costReport&templateId="+$v("templateId")+"&templateName="+tmpTemplateName
		 				,"publishTemplate","600","360","yes","80","80");
}