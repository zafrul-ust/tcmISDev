var dhxWins = null;
var facilityGateKeepingPerm = false; 

function loadForm() {
	showFacilityGroupOptions();
	preSelectSavedValuesOnload = true;
	//check if a template is opened or saved
	if(document.getElementById("templateName").value.length > 0) {
	//	var foo = document.getElementById("foo");
	//	removeReportFieldFromBaseField(foo);
  	} else {
    	//document.adHocUsageReportForm.reportType[2].checked = true;
    	document.adHocUsageReportForm.reportGenerationType[0].checked = true;
  	}
	//var reportFieldList = document.getElementById("reportFieldList");
	//removeInitialMessage(reportFieldList);
  	//reportTypeClicked();
}
/*
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
	var dockO = document.getElementById("dockId");
	var selectedFacility = facilityO.value;

	for (var i = reportingEntityO.length; i > 0;i--) {
		reportingEntityO[i] = null;
	}

	for (var i = dockO.length; i > 0;i--) {
		dockO[i] = null;
	}
	facilityHasReportingEntity(selectedFacility);
	showReportingEntityLinks(selectedFacility);
	showDockLinks(selectedFacility);
}
*/

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

function showDockLinks(selectedFacility) {
	var dockArray = altDock[selectedFacility];
	var dockDescArray = altDockDesc[selectedFacility];

	var selectedDockIndex = 0;
	if (dockArray != null && dockArray.length > 0) {
		var needLoad = true;
		var count = 0;
		if (dockArray.length > 1 || module == 'lmco') {
			setDock(count++,messagesData.all,"");
		}else {
			if (dockArray[0] == '') {
				setDock(count++,messagesData.all,"");
				needLoad = false;
			}
		}
		if (needLoad) {
			for (var i=0; i < dockArray.length; i++) {
				setDock(i+count,dockDescArray[i],dockArray[i]);
				//selected dock
				if ($v("templateDockId") != null) {
					if ($v("templateDockId").length > 0) {
						if (dockArray[i] == $v("templateDockId")) {
							selectedDockIndex = i+count;
						}
					}
				} //end of if selected dock is not null
			}
		}
	}else {
		setDock(0,messagesData.all,"");
	}
	$("dockId").selectedIndex = selectedDockIndex;
	dockChanged();
}

function setDock(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var dockO = document.getElementById("dockId");
  dockO[href] = optionName;
}

function dockChanged() {
	var deliveryPointO = document.getElementById("deliveryPoint");
	for (var i = deliveryPointO.length; i > 0;i--) {
		deliveryPointO[i] = null;
	}
	showDeliveryPointLinks($v("facilityId")+$v("dockId"));
}

function showDeliveryPointLinks(selectedDock) {
	var deliveryPointArray = altDeliveryPoint[selectedDock];
	var deliveryPointDescArray = altDeliveryPointDesc[selectedDock];

	var selectedDeliveryPointIndex = 0;
	if (deliveryPointArray != null && deliveryPointArray.length > 0) {
		var needLoad = true;
		var count = 0;
		if (deliveryPointArray.length > 1) {
			setDeliveryPoint(count++,messagesData.all,"");
		}else {
			if (deliveryPointArray[0] == '') {
				setDeliveryPoint(count++,messagesData.all,"");
				needLoad = false;
			}
		}
		if (needLoad) {
			for (var i=0; i < deliveryPointArray.length; i++) {
				setDeliveryPoint(i+count,deliveryPointDescArray[i],deliveryPointArray[i]);
				//selected delivery point
				if ($v("templateDeliveryPoint") != null) {
					if ($v("templateDeliveryPoint").length > 0) {
						if (deliveryPointArray[i] == $v("templateDeliveryPoint")) {
							selectedDeliveryPointIndex = i+count;
						}
					}
				} //end of if selected delivery point is not null
			}
		}
	}else {
		setDeliveryPoint(0,messagesData.all,"");
	}
	$("deliveryPoint").selectedIndex = selectedDeliveryPointIndex;
}

function setDeliveryPoint(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var deliveryPointO = document.getElementById("deliveryPoint");
  deliveryPointO[href] = optionName;
}

/*
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
*/
/*
function transferItem(objFrom,objTo,orderBy) {
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

function moveBaseFieldIntoRightPosition(selectedValue) {
	var foo = document.getElementById("foo");
	var index = -1;
	for (i = 0; i < altBaseFieldId.length; i++) {
		if (altBaseFieldId[i] == selectedValue) {
			index = altBaseFieldOrder[i];
			break;
		}
	}
	move(foo,false);
}
*/
//This function is called by the buttons generated above and is used to move the selected item in the listbox up or down.
/*
function move(bDir) {
   var objMove = $('reportFieldList');

   if(objMove.length <= 0) {
      alert(messagesData.thereisnoitemtomove);
      return;
   }
   var idx = objMove.selectedIndex;
   if (idx==-1)
      alert(messagesData.youmustfirstselectitemtoreorder);
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
*/
function generateReportAudit() {
	var groupByOptionList = document.getElementById("groupByOptionList");
   //selectAllItems(groupByOptionList);
   var groupByList = document.getElementById("groupByList");
   //selectAllItems(groupByList);
}

function generateAdHocReportAudit() {
	/*
	var facilityId = document.getElementById("facilityId");
	document.getElementById("facilityName").value = facilityId[facilityId.selectedIndex].text;
	var application = document.getElementById("application");
	document.getElementById("applicationDesc").value = application[application.selectedIndex].text;
	*/
	var dockId = document.getElementById("dockId");
	if (dockId != null)
	   document.getElementById("dockDesc").value = dockId[dockId.selectedIndex].text;
	var deliveryPoint = document.getElementById("deliveryPoint");
	if (deliveryPoint != null)
		document.getElementById("deliveryPointDesc").value = deliveryPoint[deliveryPoint.selectedIndex].text;
	/*
    var materialCategory = document.getElementById("materialCategory");
	document.getElementById("categoryDesc").value = materialCategory[materialCategory.selectedIndex].text;
	*/
    document.getElementById("categoryDesc").value = "";

/*	var chemicalListName = document.getElementById("chemicalListName");
	document.getElementById("listDesc").value = $v("chemicalListName"); //chemicalListName[chemicalListName.selectedIndex].text;*/

	//var what = document.getElementById("listDesc").value;

	/*
	var reportFieldList = document.getElementById("reportFieldList");
   selectAllItems(reportFieldList);
   if(reportFieldList.length <= 0 || reportFieldList.options[0].value == "xxblankxx") {
      alert(messagesData.youmustselectreportfield);
      return;
   }*/

	if(!validateReportFieldsGrid()) {
		alert(messagesData.youmustselectreportfield);
	    return;
	}

/*
   var reportType = document.getElementById("reportType");
   if(document.adHocUsageReportForm.reportType[1].checked == true){
       var casNumber = document.getElementById("casNumber");
       if(casNumber == null || casNumber.value.length == 0){
           alert(messagesData.youmustentercasnumber);
           return;
       }
   }
*/
	if(!validateDatePopulated()) {		
	    return;
	}
	
	/*
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
   */
	
/*
   if(document.adHocUsageReportForm.reportGenerationType[1].checked==true) {
     var reportName = document.getElementById("reportName");
     if(reportName == null || reportName.value == "") {
        alert(messagesData.pleaseenterreportname);
        return;
     }
   }
*/
	//if (!reportFieldIsCompatible()) {
	//	alert(messagesData.adhocusagecompatibilityerror);
	//	return;
	//}

      if (!validateListFormatAndContraint())
		  return;

	  if(!validateGrid())
		  return;
	  else
		  {
		  	$('gridSubmit').value = gridSubmit;
		  	$('gridDesc').value = gridDesc;
		  	$('listFormat').value = listFormat;
		  	$('chemicalFieldListId').value = chemicalFieldListId;
		  }

  var submitValue = document.getElementById("submitValue");
  submitValue.value = "submit";
  var randomNumber = Math.floor(Math.random()*1001);
  var myAction = document.adHocUsageReportForm.action;
  document.adHocUsageReportForm.action = myAction.substring(0,myAction.length-3) + randomNumber + ".do";
  openWinGenericViewFile("/tcmIS/common/generatingreport.jsp","myWin"+randomNumber,"800","600","yes","80","80");
  document.adHocUsageReportForm.target='myWin'+randomNumber;
  addHiddenVariable(document.adHocUsageReportForm, 'alternateDb', 'Report');
  setTimeout("document.adHocUsageReportForm.submit()",300);
}

/*
function selectAllItems(obj) {
   for(i=0;i<obj.length;i++) {
      obj.options[i].selected=true;
   }
}
*/

function getProfileSearch()
{
    document.adHocUsageReportForm.target='';
    openWinGeneric("profilesearch.do","profilesearch","600","400","yes","80","80");
}

function getManagementOptionSearch()
{
    document.adHocUsageReportForm.target='';
    openWinGeneric("managementoptionsearch.do","managementsearch","600","400","yes","80","80");
}

function saveTemplateAudit() {
   //var reportFieldList = document.getElementById("reportFieldList");
   //selectAllItems(reportFieldList);
   //if(reportFieldList.length <= 0) {
   //   alert(messagesData.youmustselectreportfield);
   //   return;
   //}

   if(!validateReportFieldsGrid()) {
      alert(messagesData.youmustselectreportfield);
      return false;
   }
   
   var gateKeeping = false;   
   if(document.getElementById("gateKeeping") != null && document.getElementById("gateKeeping").checked == true) {
		 gateKeeping = true;
   }
   
   document.adHocUsageReportForm.target='';
   openWinGeneric("savetemplate.do?reportType=usage&templateId="+$v("templateId")+"&gateKeeping="+gateKeeping+"&facilityId="+$v("facilityId"),"savetemplate","600","370","yes","80","80");
}

function openTemplateAudit() {
    document.adHocUsageReportForm.target='';
  openWinGeneric("opentemplate.do?reportType=usage","opentemplate","600","210","yes","80","80");
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
/*
function removeReportFieldFromBaseField(objFrom) {
     if(objFrom.length > 0) {
        //make at least one item is selected from table
        var count = 0;
        for (j=0;j<objFrom.length;j++) {
           if(objFrom.options[j].selected){
              count++;
           }
        }
        if (count > 0) {
           for(i=0;i<objFrom.length;i++) {
              if(objFrom.options[i].selected){
                 removeItem(objFrom,i);
                 i--;
              }
           }
        }
     }
}
*/
function clearTemplate()
{
	document.adHocUsageReportForm.target = '';
	document.getElementById("submitValue").value = "clearTemplate";
}

function reportTypeClicked() {
	if (document.adHocUsageReportForm.reportType[0].checked) {
		document.getElementById("listDesc").disabled="";
		document.getElementById("listSearch").disabled="";
		document.getElementById("casNumber").disabled=true;
		document.getElementById("casSearch").disabled=true;
	}else if (document.adHocUsageReportForm.reportType[1].checked) {
		document.getElementById("listDesc").disabled=true;
		document.getElementById("listSearch").disabled=true;
		document.getElementById("casNumber").disabled="";
		document.getElementById("casSearch").disabled="";
	}else {
		document.getElementById("listDesc").disabled=true;
		document.getElementById("listSearch").disabled=true;
		document.getElementById("casNumber").disabled=true;
		document.getElementById("casSearch").disabled=true;
	}
}

/* TODO: Check the logic here
function reportFieldIsCompatible() {
	var result = true;
	var reportFieldList = document.getElementById("reportFieldList");
	var containSpeciated = false;
	var containCasOrChemicalName = false;
	for (i = 0; i < reportFieldList.length; i++) {
		//figure out whether report field is speciated
		for (j = 0; j < altBaseFieldId.length; j++) {
			if (altBaseFieldId[j] == reportFieldList.options[i].value) {
				//contains speciated fields
				if (altBaseFieldIdCompatibility[j] == "S") {
				  containSpeciated = true;
				  break;
				}
			}
		}
		if (!containCasOrChemicalName) {
			//contains CAS Number (base_field_id = 130) or Chemical Name (base_field_id = 226)
			if (reportFieldList.options[i].value == "130" || reportFieldList.options[i].value == "226" || reportFieldList.options[i].value == "265") {
				containCasOrChemicalName = true;
			}
		}
	} //end of for each report field
	//if report if contains speciated field then CAS Number or Chemical Name is needed
	if (containSpeciated && !containCasOrChemicalName) {
		result = false;
	}
	return result;
}
*/
/*
function showBaseFields(type) {
    openWinGeneric("basefielddescription.do?type="+type,"basefielddescription","600","400","yes","80","80");
}

function getListSearch()
{
	document.adHocUsageReportForm.target='';
    var listID = $v("chemicalListName");
//    var selecelemet = listID.selectedIndex;
    var listName = $v("listDesc");
    listName = listName.replace(/&/gi, "%26");
    listName = listName.replace(/#/gi, "%23");
    listName = listName.replace(/\+/gi, "%2b");
    openWinGeneric("chemicallistsearch.do?Action=New&listId="+listID+"&listName="+listName,"chemicallistsearch","600","400","yes","80","80");
}

function getCasSearch()
{
    document.adHocUsageReportForm.target='';
    openWinGeneric("chemicalcassearch.do?Action=New","chemicalcassearch","600","400","yes","80","80");
}
*/

function publishTemplateAudit() {
    /*
   var reportFieldList = document.getElementById("reportFieldList");
   //selectAllItems(reportFieldList);
   if(reportFieldList.length <= 0) {
      alert(messagesData.youmustselectreportfield);
      return;
   }*/
	document.adHocUsageReportForm.target='';
	var tmpTemplateName = $v("globalizationLabelLetter")+$v("templateId")+"-"+$v("templateName");
	openWinGeneric("publishtemplate.do?action=publish&reportType=usage&calledFrom=adhocUsage&templateId="+$v("templateId")+"&templateName="+tmpTemplateName
		 				,"publishTemplate","650","275","yes","80","80");
}

function unpublishTemplateAudit() {
    /*
   var reportFieldList = document.getElementById("reportFieldList");
   //selectAllItems(reportFieldList);
   if(reportFieldList.length <= 0) {
      alert(messagesData.youmustselectreportfield);
      return;
   }*/
   document.adHocUsageReportForm.target='';
	var tmpTemplateName = $v("globalizationLabelLetter")+$v("templateId")+"-"+$v("templateName");
	openWinGeneric("publishtemplate.do?action=unpublish&reportType=usage&calledFrom=adhocUsage&templateId="+$v("templateId")+"&templateName="+tmpTemplateName
		 				,"publishTemplate","600","360","yes","80","80");
}
/*
function popReportBaseFields(reportType) {
	var baseFieldIdString = '';
	var obj = $('reportFieldList');
	if(obj.length > 0) {
		for ( var i=0; i < obj.length; i++) {
			if(baseFieldIdString == '')
				baseFieldIdString = obj.options[i].value;
			else
				baseFieldIdString += "|"+obj.options[i].value;
		}
	}

	var loc = "reportbasefields.do?reportType="+reportType+"&baseFieldIdString="+baseFieldIdString;
	openWinGeneric(loc,'ReportBaseFields',"720","550","yes","50","50","20","20","no");
}

*/
//function setFields(fieldsString) {
//	var fieldArray=fieldsString.split("|");

	// Set up the hidden report fields
//	var obj = $('reportFieldList');
   	//for (var i = obj.length; i >= 0;i--)
     //	obj[i] = null;

//  	for ( var i=0; i < fieldArray.length; i++) {
//  		var fieldInfo = fieldArray[i].split(":");
//	    setOption(i,fieldInfo[1],fieldInfo[0],'reportFieldList');
//	}

	// Set up the displayed report fields
/*	for(var i=mygrid.getRowsNum();i >= 0;i--){
		mygrid.deleteRow(i);
	}

	var addedRowId = 0;
	for ( var i=0; i < fieldArray.length; i++) {
  		var fieldInfo = fieldArray[i].split(":");

	    var thisrow = mygrid.addRow(addedRowId,"",addedRowId);

   	//	mygrid.selectRow(mygrid.getRowIndex(addedRowId),null,false,false);

   		mygrid.cells(addedRowId,mygrid.getColIndexById("name")).setValue(fieldInfo[1]);
   		mygrid.cells(addedRowId,mygrid.getColIndexById("baseFieldId")).setValue(fieldInfo[0]);
   		addedRowId++;
	}
*/
//}
/*
var mygrid = null;
function doInitGrid() {
	mygrid = new dhtmlXGridObject('fieldBean');

	initGridWithConfig(mygrid, config, false, false, false);

	if (typeof (jsonMainData) != 'undefined') {
		mygrid.parse(jsonMainData, "json");
	}

} //end of method
*/


var gridSubmit = '';
var gridDesc = '';
var listFormat = '';
var baseFieldId = '';
var baseFieldName = '';
var baseDescription = '';
var chemicalFieldListId = '';
function subAdHocForm()
{
	var validVals = true;
	var validReportFieldsVals = true;
	var validDate = validateDate(); // validate number of days field and the day of year field
	validVals = validateGrid();
	validReportFieldsVals = validateReportFieldsGrid();
	if(validVals && validReportFieldsVals && validDate)
	{
		try{
			var target = document.all.item("TRANSITPAGE");
			target.style["display"] = "";
			var target1 = document.all.item("MAINPAGE");
			target1.style["display"] = "none";
		}catch (ex){
		}
		//var reportFieldList = document.getElementById("reportFieldList");
		//selectAllItems(reportFieldList);
		document.adHocUsageReportForm.target='';
		$('gridSubmit').value = gridSubmit;
		$('gridDesc').value = gridDesc;
		$('listFormat').value= listFormat;
		$('baseFieldId').value = baseFieldId;
		$('baseFieldName').value = baseFieldName;
		$('baseDescription').value = baseDescription;
		$('chemicalFieldListId').value = chemicalFieldListId;
		document.adHocUsageReportForm.submit();
	}
	else
		alert('Please enter valid values in the grid');
}


var children = new Array();

function closeAllchildren() {
	try {
		for ( var n = 0; n < children.length; n++) {
			try {
				children[n].closeAllchildren();
			}
			catch (ex) {
			}
			children[n].close();
		}
	}
	catch (ex) {
	}
	children = new Array();
}
//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
}

function showTransitWin(message) {
	if (message != null && message.length > 0) {
		$("transitLabel").innerHTML = message;
	}else {
		$("transitLabel").innerHTML = messagesData.pleasewait;
	}

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);
		dhxWins.window("transitDailogWin").show();
	}
}

function checkFacilityPermission(selFacility) {
    var found = false;
	for (var i = 0; i < facilityPermArr.length; i++ ) {
		if(facilityPermArr[i] == selFacility) {
    		found = true; 
		}
    }
    if(found) {
      try {
    	  document.getElementById("showHideOpt").style.display="";
    	  facilityGateKeepingPerm = true;
      } catch(ex) {}    
    }
    else {
      try {
    	  document.getElementById("showHideOpt").style.display="none";
    	  facilityGateKeepingPerm = false;
      } catch(ex) {}
    }
  }