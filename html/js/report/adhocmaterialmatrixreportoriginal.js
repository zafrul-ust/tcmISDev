function showBaseFields(type) {
    openWinGeneric("basefielddescription.do?type="+type,"basefielddescription","600","400","yes","80","80");
}

function getListSearch()
{
    var listID = document.getElementById("listOption");
    var selecelemet = listID.selectedIndex;
    var listName = listID.options[selecelemet].text;
    listName = listName.replace(/&/gi, "%26");
    listName = listName.replace(/#/gi, "%23");
    listName = listName.replace(/\+/gi, "%2b");
    openWinGeneric("chemicallistsearch.do?Action=New&listId="+listID.value+"&listName="+listName,"chemicallistsearch","600","400","yes","80","80");
}

function getCasSearch()
{
    openWinGeneric("chemicalcassearch.do?Action=New","chemicalcassearch","600","400","yes","80","80");
}

function loadForm() {
	showFacilityOptions();
  //check if a template is opened or saved
  if(document.getElementById("templateName").value.length > 0) {
	 var bar = document.getElementById("bar");
    var foo = document.getElementById("foo");
	 removeReportFieldFromBaseField(bar,"Yes");
    removeReportFieldFromBaseField(foo,"No");
	 //removing space holder
	 var chemicalFieldList = document.getElementById("chemicalFieldList");
	 var reportFieldList = document.getElementById("reportFieldList");
	 removeInitialMessage(chemicalFieldList);
	 removeInitialMessage(reportFieldList);

  } else {
    document.adHocMaterialMatrixReportForm.reportCriteria[2].checked = true;
    document.adHocMaterialMatrixReportForm.reportGenerationType[0].checked = true;
  }

  reportCriteriaClicked();
}
function showFacilityOptions() {
	var facilityArray = altFacilityId;
	var facilityNameArray = altFacilityName;

	var selectedFacilityIndex = 0;
	if(facilityArray != null) {
		var count = 0;
		if(facilityArray.length > 1) {
	   	setOption(count++,messagesData.myFacilities,"All", "facilityId");
		}
		for (var i=0; i < facilityArray.length; i++) {
			setOption(i+count,facilityNameArray[i],facilityArray[i], "facilityId");
			//selected facility
			if ($v("templateFacilityId") != null) {
				if ($v("templateFacilityId").length > 0) {
					if (facilityArray[i] == $v("templateFacilityId")) {
						selectedFacilityIndex = i+count;
					}
				}
			} //end of if selected facility is not null
		}
	}else {
		setOption(0,messagesData.myFacilities,"All", "facilityId");
	}

	$("facilityId").selectedIndex = selectedFacilityIndex;
	facilityChanged();
}

function facilityChanged() {
	var facilityO = document.getElementById("facilityId");
	var reportingEntityO = document.getElementById("reportingEntityId");
	var selectedFacility = facilityO.value;

	for (var i = reportingEntityO.length; i > 0;i--) {
		reportingEntityO[i] = null;
	}

	facilityHasReportingEntity(selectedFacility);
	showReportingEntityLinks(selectedFacility);
}

function facilityHasReportingEntity(selectedFacility) {
	//reportingEntityLabelSpan
	if (selectedFacility == "All") {
		if (hasAtLeastOneReportEntity()) {
			$("reportingEntityLabelSpan").style["display"] = "";
			$("reportingEntityDropdownSpan").style["display"] = "";
		}else {
			$("reportingEntityLabelSpan").style["display"] = "none";
			$("reportingEntityDropdownSpan").style["display"] = "none";
		}
	}else {
		var tmpSelectedIndex = 0;
		if (altFacilityId.length == 1) {
			tmpSelectedIndex = $("facilityId").selectedIndex;
		}else {
			tmpSelectedIndex = $("facilityId").selectedIndex - 1;
		}
		//
		if (altReportingEntityLabel[tmpSelectedIndex].length == 0) {
			$("reportingEntityLabelSpan").style["display"] = "none";
			$("reportingEntityDropdownSpan").style["display"] = "none";
		}else {
			$("reportingEntityLabelSpan").style["display"] = "";
			$("reportingEntityDropdownSpan").style["display"] = "";
		}
	}
}

function hasAtLeastOneReportEntity() {
	var dataFound = false;
	for (var i = 0; i < altReportingEntityLabel.length; i++) {
		if (altReportingEntityLabel[i].length > 0) {
			dataFound = true;
			break;
		}
	}
	return dataFound;
}

function showReportingEntityLinks(selectedFacility) {
	var reportingEntityArray = altReportingEntity[selectedFacility];
	var reportingEntityDescArray = altReportingEntityDesc[selectedFacility];

	var selectedReportingEntityIndex = 0;
	if (reportingEntityArray != null) {
		var count = 0;
		if (reportingEntityArray.length > 1) {
			setReportingEntity(count++,messagesData.all,"");
		}
		for (var i=0; i < reportingEntityArray.length; i++) {
			setReportingEntity(i+count,reportingEntityDescArray[i],reportingEntityArray[i]);
			//selected reporting Entity
			if ($v("templateReportingEntityId") != null) {
				if ($v("templateReportingEntityId").length > 0) {
					if (reportingEntityArray[i] == $v("templateReportingEntityId")) {
						selectedReportingEntityIndex = i+count;
					}
				}
			} //end of if selected reporting Entity is not null
		}
	}else {
		setReportingEntity(0,messagesData.all,"");
	}
	$("reportingEntityId").selectedIndex = selectedReportingEntityIndex;
	reportingEntityChanged();
}

function setReportingEntity(href,text,id) {
	var optionName = new Option(text, id, false, false)
	var reportingEntityO = document.getElementById("reportingEntityId");
	reportingEntityO[href] = optionName;
}

function reportingEntityChanged() {
	var applicationIdO = document.getElementById("application");
	for (var i = applicationIdO.length; i > 0;i--) {
		applicationIdO[i] = null;
	}

	showApplicationLinks($v("facilityId")+$v("reportingEntityId"));
}

function showApplicationLinks(selectedFacility) {
	var applicationIdArray = altApplication[selectedFacility];
	var applicationDescArray = altApplicationDesc[selectedFacility];

	var selectedApplicationIndex = 0;
	if (applicationIdArray != null) {
		var count = 0;
		if (applicationIdArray.length > 1) {
			setApplication(count++,messagesData.myworkareas,"");
		}
		for (var i=0; i < applicationIdArray.length; i++) {
			setApplication(i+count,applicationDescArray[i],applicationIdArray[i]);
			//selected application
			if ($v("templateApplication") != null) {
				if ($v("templateApplication").length > 0) {
					if (applicationIdArray[i] == $v("templateApplication")) {
						selectedApplicationIndex = i+count;
					}
				}
			} //end of if selected application is not null
		}
	}else {
		setApplication(0,messagesData.myworkareas,"");
	}
	$("application").selectedIndex = selectedApplicationIndex;
}

function setApplication(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var applicationIdO = document.getElementById("application");
  applicationIdO[href] = optionName;
}



function addItem(obj,value,text) {
     index = obj.length;
     obj.options[index]=new Option(text,value);
     obj.options[index].selected = true;
}

function removeItem(obj) {
     if(obj.length <= 0)
          return;
     var index = obj.selectedIndex;
     if(index != -1)
          obj.options[index]=null;
}

function transferItem(objFrom,objTo) {
     if(objFrom.length <= 0) {
        alert(messagesData.thereisnoitemtomove);
     }else {
        //make at least one item is selected from table
        var count = 0;
        for (j=0;j<objFrom.length;j++) {
           if(objFrom.options[j].selected){
              count++;
           }
        }
        if (count > 0) {
			  //removing space holder from drop down
			  if (objTo.options[0].value == "xxblankxx") {
				 objTo.options[0] = null;
			  }
			  //don't move space holder
			  if (objFrom.options[0].value == "xxblankxx") {
				  return;
			  }
			  for(i=0;i<objFrom.length;i++) {
             if(objFrom.options[i].selected){
                 //alert('remove:'+objFrom.options[i].text);
                 addItem(objTo,objFrom.options[i].value,objFrom.options[i].text);
                 removeItem(objFrom,i);
                 i--;
              }
           }
			  //adding space holder to drop down
			  if (objFrom.length == 0){
				  var tmpBlank = "                                    ";
				  addItem(objFrom,"xxblankxx",tmpBlank);
			  }
		  }else {
            alert(messagesData.selectanitemthatyouwanttomove);
        }
     }
}


//This function is called by the buttons generated above and is used to move the selected item in the listbox up or down.
function move(objMove,bDir) {
   if(objMove.length <= 0) {
      alert(messagesData.thereisnoitemtomove);
      return;
   }
   var idx = objMove.selectedIndex
   if (idx==-1)
      alert(messagesData.youmustfirstselectitemtoreorder)
   else {
      var nxidx = idx+( bDir? -1 : 1)
      if (nxidx<0) nxidx=objMove.length-1
      if (nxidx>=objMove.length) nxidx=0
      var oldVal = objMove[idx].value
      var oldText = objMove[idx].text
      objMove[idx].value = objMove[nxidx].value
      objMove[idx].text = objMove[nxidx].text
      objMove[nxidx].value = oldVal
      objMove[nxidx].text = oldText
      objMove.selectedIndex = nxidx
   }
}

function generateReportAudit() {
   var groupByOptionList = document.getElementById("groupByOptionList");
   selectAllItems(groupByOptionList);
   var groupByList = document.getElementById("groupByList");
   selectAllItems(groupByList);
}

function generateAdHocReportAudit() {
	var facilityId = document.getElementById("facilityId");
	document.getElementById("facilityName").value = facilityId[facilityId.selectedIndex].text;
	var application = document.getElementById("application");
	document.getElementById("applicationDesc").value = application[application.selectedIndex].text;
	
	var reportFieldList = document.getElementById("reportFieldList");
   selectAllItems(reportFieldList);
   if(reportFieldList.length <= 0 || reportFieldList.options[0].value == "xxblankxx") {
      alert(messagesData.youmustselectreportfield);
      return;
   }

   var chemicalFieldList = document.getElementById("chemicalFieldList");
   selectAllItems(chemicalFieldList);
    /*
   if(chemicalFieldList.length <= 0 || chemicalFieldList.options[0].value == "xxblankxx") {
      alert(messagesData.youmustselectchemicalfield);
      return;
   }
   */


   if(document.adHocMaterialMatrixReportForm.reportGenerationType[1].checked==true) {
     var reportName = document.getElementById("reportName");
     if(reportName == null || reportName.value == "") {
        alert(messagesData.pleaseenterreportname);
        return;
     }
   }

   if(document.adHocMaterialMatrixReportForm.reportCriteria[1].checked == true) {
     var beginDate = document.getElementById("beginDateJsp");
     if(beginDate == null || beginDate.value.length < 1){
       alert(messagesData.begindaterequired);
        return;
     }
     var endDate = document.getElementById("endDateJsp");
     if(endDate == null || endDate.value.length < 1){
       alert(messagesData.enddaterequired);
       return;
     }
   }

    if(document.adHocMaterialMatrixReportForm.reportCriteria[0].checked == true) {
       var partNumber = document.getElementById("partNumber");
       if(partNumber == null || partNumber.value.length < 1){
           alert(messagesData.partnumberrequired);
            return;
       }
    }
	
	 if(document.getElementById("showPartsInInventory").value == 'true') {
	 	if(document.adHocMaterialMatrixReportForm.reportCriteria[3].checked == true) {
			 var inventoryDate = document.getElementById("inventoryDate");
			 if(inventoryDate == null || inventoryDate.value.length < 1){
				  alert(messagesData.inventorydaterequired);
					return;
			 }
		 }
	 }

  var submitValue = document.getElementById("submitValue");
  submitValue.value = "submit";
   //win = window.open('','myWin','toolbars=0');
  var submitValue = document.getElementById("submitValue");
  var randomNumber = Math.floor(Math.random()*1001);
  var myAction = document.adHocMaterialMatrixReportForm.action;
  document.adHocMaterialMatrixReportForm.action = myAction.substring(0,myAction.length-3) + randomNumber + ".do";
  openWinGenericViewFile("/tcmIS/common/generatingreport.jsp","myWin"+randomNumber,"800","600","yes","80","80");
   document.adHocMaterialMatrixReportForm.target='myWin'+randomNumber;
   addHiddenVariable(document.adHocMaterialMatrixReportForm, 'alternateDb', 'Report');
	setTimeout("document.adHocMaterialMatrixReportForm.submit()",300);
}

function selectAllItems(obj) {
   for(i=0;i<obj.length;i++) {
      obj.options[i].selected=true;
   }
}

function saveTemplateAudit() {
//  var reportName = document.getElementById("reportName");
//alert(reportName.value);
   var reportFieldList = document.getElementById("reportFieldList");
   selectAllItems(reportFieldList);
   if(reportFieldList.length <= 0) {
      alert(messagesData.youmustselectreportfield);
      return;
   }
   var chemicalFieldList = document.getElementById("chemicalFieldList");
   selectAllItems(chemicalFieldList);
   if(chemicalFieldList.length <= 0) {
      alert(messagesData.youmustselectchemicalfield);
      return;
   }
  document.adHocMaterialMatrixReportForm.target = '';
  openWinGeneric("savetemplate.do?reportType=materialmatrix&templateId="+$v("templateId"),"savetemplate","600","400","yes","80","80");
}

function openTemplateAudit() {
  document.adHocMaterialMatrixReportForm.target = '';
  openWinGeneric("opentemplate.do?reportType=materialmatrix","opentemplate","600","400","yes","80","80");
}

function removeInitialMessage(obj) {
  if (obj.length == 1 && obj.options[0].value == "xxblankxx") {
     //do nothing
	  //leaving the space holder alone
  }else {
	  for(i=0;i<obj.length;i++) {
		 if(obj.options[i].value == '' || obj.options[i].value == "xxblankxx") {
			obj.options[i] = null;
		 }
	  }
  }
}

function subAdHocForm() {
    var reportFieldList = document.getElementById("reportFieldList");
    selectAllItems(reportFieldList);
    var chemicalFieldList = document.getElementById("chemicalFieldList");
    selectAllItems(chemicalFieldList);
    document.adHocMaterialMatrixReportForm.target='';
    document.adHocMaterialMatrixReportForm.submit();
}

function removeReportFieldFromBaseField(objFrom,saveOld) {

	  if(objFrom.length > 0) {
        //make at least one item is selected from table
        var count = 0;
        for (j=0;j<objFrom.length;j++) {
           if(objFrom.options[j].selected){
              count++;
           }
        }
        if (count > 0) {
			  var oldidarr = new Array();
			  var oldnamearr = new Array();
			  for(i=0;i<objFrom.length;i++) {
              if(objFrom.options[i].selected){
					  if (saveOld == "Yes") {
						  oldidarr[oldidarr.length] = objFrom.options[i].value;
					  	  oldnamearr[oldnamearr.length] = objFrom.options[i].text;
					  }
					  removeItem(objFrom,i);
                 i--;
              }
           }
			  //creating report list
			  //if (saveOld == "Yes") {
			  //	 loadReportList(oldidarr,oldnamearr);
			  //}
		  }
     }
}

/*
function loadReportList(oldIdArr, oldNameArr) {
	var chemicalFieldList = document.getElementById("chemicalFieldList");
	for(var i = 0; i < altReportList.length; i++) {
		var currentId =  altReportList[i];
		for(var j = 0; j < oldIdArr.length;j++) {
			if (currentId == oldIdArr[j]) {
				addItem(chemicalFieldList,oldIdArr[j],oldNameArr[j]);
				break;
			}
		}
	}
}*/

function reportCriteriaClicked() {
    var listFormat0 = document.getElementById("listFormat");
	 var showPartsInInventory = document.getElementById("showPartsInInventory").value;
	 if(document.adHocMaterialMatrixReportForm.reportCriteria[0].checked == true) {
		 document.getElementById("partNumberCriteria").disabled="";
		 document.getElementById("partNumber").disabled="";
		 document.getElementById("beginDateJsp").disabled=true;
		 document.getElementById("endDateJsp").disabled=true;
		 if (showPartsInInventory == 'true') {
		 	document.getElementById("inventoryDate").disabled=true;
		 }
		 listFormat0.remove(3);
	 }else if(document.adHocMaterialMatrixReportForm.reportCriteria[1].checked == true) {
		 document.getElementById("partNumberCriteria").disabled=true;
		 document.getElementById("partNumber").disabled=true;
		 document.getElementById("beginDateJsp").disabled="";
		 document.getElementById("endDateJsp").disabled="";
		 if (showPartsInInventory == 'true') {
		 	document.getElementById("inventoryDate").disabled=true;
		 }
		if(listFormat0.length == 3) {
          var optionName = new Option("lbs", "lbs", false, false)
          listFormat0.add(optionName);
      }
	 }else if(document.adHocMaterialMatrixReportForm.reportCriteria[2].checked == true) {
		 document.getElementById("partNumberCriteria").disabled=true;
		 document.getElementById("partNumber").disabled=true;
		 document.getElementById("beginDateJsp").disabled=true;
		 document.getElementById("endDateJsp").disabled=true;
		 if (showPartsInInventory == 'true') {
		 	document.getElementById("inventoryDate").disabled=true;
		 }
		 listFormat0.remove(3);
	 }else {
		 document.getElementById("partNumberCriteria").disabled=true;
		 document.getElementById("partNumber").disabled=true;
		 document.getElementById("beginDateJsp").disabled=true;
		 document.getElementById("endDateJsp").disabled=true;
		 if (showPartsInInventory == 'true') {
		 	document.getElementById("inventoryDate").disabled="";
		 }
		 listFormat0.remove(3);
    }
}

/*
function reportTypeClicked() {
	if (document.adHocMaterialMatrixReportForm.reportCriteria[0].checked) {
		document.getElementById("chemicalListName").disabled="";
		document.getElementById("listSearch").disabled="";
		document.getElementById("casNumber").disabled=true;
		document.getElementById("casSearch").disabled=true;
	}else if (document.adHocUsageReportForm.reportType[1].checked) {
		document.getElementById("chemicalListName").disabled=true;
		document.getElementById("listSearch").disabled=true;
		document.getElementById("casNumber").disabled="";
		document.getElementById("casSearch").disabled="";
	}else {
		document.getElementById("chemicalListName").disabled=true;
		document.getElementById("listSearch").disabled=true;
		document.getElementById("casNumber").disabled=true;
		document.getElementById("casSearch").disabled=true;
	}
}
 */

function clearTemplate()
{
	document.adHocMaterialMatrixReportForm.target = '';
	document.getElementById("submitValue").value = "clearTemplate";
}

function publishTemplateAudit() {
   var reportFieldList = document.getElementById("reportFieldList");
   selectAllItems(reportFieldList);
   if(reportFieldList.length <= 0) {
      alert(messagesData.youmustselectreportfield);
      return;
   }
   document.adHocMaterialMatrixReportForm.target='';
	var tmpTemplateName = $v("globalizationLabelLetter")+$v("templateId")+"-"+$v("templateName");
	openWinGeneric("publishtemplate.do?action=publish&reportType=materialmatrix&calledFrom=adhocMaterialMatrix&templateId="+$v("templateId")+"&templateName="+tmpTemplateName
		            ,"publishTemplate","650","275","yes","80","80");
}

function unpublishTemplateAudit() {
   var reportFieldList = document.getElementById("reportFieldList");
   selectAllItems(reportFieldList);
   if(reportFieldList.length <= 0) {
      alert(messagesData.youmustselectreportfield);
      return;
   }
   document.adHocMaterialMatrixReportForm.target='';
	var tmpTemplateName = $v("globalizationLabelLetter")+$v("templateId")+"-"+$v("templateName");
	openWinGeneric("publishtemplate.do?action=unpublish&reportType=materialmatrix&calledFrom=adhocMaterialMatrix&templateId="+$v("templateId")+"&templateName="+tmpTemplateName
		            ,"publishTemplate","600","360","yes","80","80");
}

