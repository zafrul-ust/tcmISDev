function showBaseFields(type) {
    document.adHocWasteReportForm.target='';
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
    document.adHocWasteReportForm.target='';
    openWinGeneric("chemicallistsearch.do?Action=New&listId="+listID.value+"&listName="+listName,"chemicallistsearch","600","400","yes","80","80");
}

function getCasSearch()
{
    document.adHocWasteReportForm.target='';
    openWinGeneric("chemicalcassearch.do?Action=New","chemicalcassearch","600","400","yes","80","80");
}
/*
function openWinGeneric(destination,WinName,WinWidth, WinHeight,Resizable,topCoor,leftCoor )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top="+topCoor+",left="+leftCoor+",width="+WinWidth+",height="+WinHeight+",resizable="+ Resizable;
    preview = window.open(destination, WinName,windowprops);
}
*/
function facilityChanged() {
  var facilityO = document.getElementById("facilityId");
  var applicationIdO = document.getElementById("application");
  var vendorIdO = document.getElementById("vendor");
  var selectedFacility = facilityO.value;

  for (var i = applicationIdO.length; i > 0;i--) {
    applicationIdO[i] = null;
  }

  for (var i = vendorIdO.length; i > 0;i--) {
    vendorIdO[i] = null;
  }
 /*
  if (facilityO.value == "All")
  {
   setApplication(0,"All","")
   setVendor(0,"All","")
  }

  else if (facilityO.value == "My Facilities")
  {
    setApplication(0,"All","")
    setApplication(1,"My Work Areas","My Work Areas")
    setVendor(0,"All","")
    setVendor(1,"My Vendors","My Vendors")
  }
  */
  //else
  //{
  showApplicationLinks(selectedFacility);
  showVendorLinks(selectedFacility);
  //}
  applicationIdO.selectedIndex = 0;
  vendorIdO.selectedIndex = 0;
  applicationChanged();
}

function showApplicationLinks(selectedFacility)
{
  var applicationIdArray = new Array();
  applicationIdArray = altApplication[selectedFacility];

  var applicationDescArray = new Array();
  applicationDescArray = altApplicationDesc[selectedFacility];
/*
  if(applicationIdArray.length == 0) {
    setApplication(0,"All","")
  }
*/

   // setApplication(0,messagesData.myworkareas,"My Work Areas")
  for (var i=0; i < applicationIdArray.length; i++) {
    setApplication(i,applicationDescArray[i],applicationIdArray[i])
  }
}

function showVendorLinks(selectedFacility)
{
  var vendorIdArray = new Array();
  vendorIdArray = altVendor[selectedFacility];

  var vendorDescArray = new Array();
  vendorDescArray = altVendorDesc[selectedFacility];
/*
  if(vendorIdArray.length == 0) {
    setVendor(0,"All","")
  }
*/
    setVendor(0,"All","All")
  for (var i=0; i < vendorIdArray.length; i++) {
    setVendor(i+1,vendorDescArray[i],vendorIdArray[i])
  }
}

function setApplication(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var applicationIdO = document.getElementById("application");
  applicationIdO[href] = optionName;
}

function setVendor(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var vendorIdO = document.getElementById("vendor");
  vendorIdO[href] = optionName;
}

function applicationChanged() {
  var facilityO = document.getElementById("facilityId");
  var applicationO = document.getElementById("application");
  var wasteLocationIdO = document.getElementById("accumulationPoint");
  var selectedFacility = facilityO.value;
  var selectedApplication = applicationO.value;
//alert(selectedApplication);
  for (var i = wasteLocationIdO.length; i > 0;i--) {
    wasteLocationIdO[i] = null;
  }
  /*
  if (applicationO.value == "All")
  {
   setWasteLocation(0,"All","")
  }

  else if (applicationO.value == "My Work Areas")
  {
    setWasteLocation(0,"All","")
    setWasteLocation(1,"My Locations","My Locations")
  }
  */
  //else
  //{
  showWasteLocationLinks(selectedFacility,selectedApplication);
  //}
  wasteLocationIdO.selectedIndex = 0;
}

function showWasteLocationLinks(selectedFacility,selectedApplication)
{
  var wasteLocationIdArray = new Array();
  wasteLocationIdArray = altWasteLoc[selectedFacility+'-'+selectedApplication];

  var wasteLocationDescArray = new Array();
  wasteLocationDescArray = altWasteLocDesc[selectedFacility+'-'+selectedApplication];
/*
  if(wasteLocationIdArray == null || wasteLocationIdArray.length == 0) {
    setWasteLocation(0,"All","")
  }
*/
    setWasteLocation(0,"All","All")
  if(wasteLocationIdArray != null) {
    for (var i=0; i < wasteLocationIdArray.length; i++) {
      setWasteLocation(i+1,wasteLocationDescArray[i],wasteLocationIdArray[i])
    }
  }
}

function setWasteLocation(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var wasteLocationIdO = document.getElementById("accumulationPoint");
  wasteLocationIdO[href] = optionName;
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

function saveTemplateAudit() {

	var reportFieldList = document.getElementById("reportFieldList");
   selectAllItems(reportFieldList);
	if(reportFieldList.length <= 0) {
      alert(messagesData.youmustselectreportfield);
      return;
   }
	
  document.adHocWasteReportForm.target='';
  openWinGeneric("savetemplate.do?reportType=waste&templateId="+$v("templateId"),"savetemplate","600","400","yes","80","80");
}

function openTemplateAudit() {
  document.adHocWasteReportForm.target='';
  openWinGeneric("opentemplate.do?reportType=waste","opentemplate","600","400","yes","80","80");
}
function generateAdHocReportAudit() {
	var facilityId = document.getElementById("facilityId");
	document.getElementById("facilityName").value = facilityId[facilityId.selectedIndex].text;
	var application = document.getElementById("application");
	document.getElementById("applicationDesc").value = application[application.selectedIndex].text;
	var accumulationPoint = document.getElementById("accumulationPoint");
	document.getElementById("accumulationPointDesc").value = accumulationPoint[accumulationPoint.selectedIndex].text;
	var vendor = document.getElementById("vendor");
	document.getElementById("vendorDesc").value = vendor[vendor.selectedIndex].text;
	
	var reportFieldList = document.getElementById("reportFieldList");
   selectAllItems(reportFieldList);
   if(reportFieldList.length <= 0 || reportFieldList.options[0].value == "xxblankxx") {
      alert(messagesData.youmustselectreportfield);
      return;
   }
   if(document.adHocWasteReportForm.reportGenerationType[1].checked==true) {
     var reportName = document.getElementById("reportName");
     if(reportName == null || reportName.value == "") {
        alert(messagesData.pleaseenterreportname);
        return;
     }
   }

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

	/*
	 if(document.adHocWasteReportForm.profileFlag[0].checked == true) {
    var profile = document.getElementById("profileDesc");
    if(profile == null || profile.value.length < 1){
       alert(messagesData.profilerequired);
        return;
   }
    }
	*/
	if (!reportFieldIsCompatible()) {
		alert(messagesData.adhocwastecompatibilityerror);
		return;
	}

  var submitValue = document.getElementById("submitValue");
  submitValue.value = "submit";
   //win = window.open('','myWin','toolbars=0');
  var submitValue = document.getElementById("submitValue");
  var randomNumber = Math.floor(Math.random()*1001);
  var myAction = document.adHocWasteReportForm.action;
    //alert(myAction);
  document.adHocWasteReportForm.action = myAction.substring(0,myAction.length-3) + randomNumber + ".do";
  openWinGenericViewFile("/tcmIS/common/generatingreport.jsp","myWin"+randomNumber,"","","yes","80","80");
   document.adHocWasteReportForm.target='myWin'+randomNumber;
	setTimeout("document.adHocWasteReportForm.submit()",300);
}

function selectAllItems(obj) {
   for(i=0;i<obj.length;i++) {
      obj.options[i].selected=true;
   }
}

function getProfileSearch(selectedVendor) {
    var vendor = document.getElementById("vendor");

    if(vendor == null || vendor.length < 1 || vendor.value == 'All' || vendor.value == 'My Vendors') {
      alert(messagesData.pleaseselectvendor);
      return;
    }
    document.adHocWasteReportForm.target='';
    openWinGeneric("profilesearch.do?vendorId=" + selectedVendor.value ,"selectvendor","600","400","yes","80","80");
}

function getManagementOptionSearch()
{
    document.adHocWasteReportForm.target='';
    openWinGeneric("managementoptionsearch.do","managementsearch","600","400","yes","80","80");
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

function loadForm() {
  //var id = document.getElementById("id");
  //var facility = document.getElementById("facilityId");
  //var application = document.getElementById("application");
	//check if a template is opened or saved
   if(document.getElementById("templateName").value.length > 0) {
	  var foo = document.getElementById("foo");
	  removeReportFieldFromBaseField(foo);
   }else {
     document.adHocWasteReportForm.excludeWaste.checked = true;
     //document.adHocWasteReportForm.profileFlag[1].checked = true;
     document.adHocWasteReportForm.reportGenerationType[0].checked = true;
     facilityChanged();
   }
	var reportFieldList = document.getElementById("reportFieldList");
	removeInitialMessage(reportFieldList);
}

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

function clearTemplate()
{
	document.adHocWasteReportForm.target = '';
	document.getElementById("submitValue").value = "clearTemplate";
	parent.showPleaseWait();
}

function reportFieldIsCompatible() {
	var result = true;
	var reportFieldList = document.getElementById("reportFieldList");
	var containSpeciated = false;
	var containWeightOrCost = false;
	for (i = 0; i < reportFieldList.length; i++) {
		//figure out whether report field is speciated
		for (j = 0; j < altBaseFieldId.length; j++) {
			if (altBaseFieldId[j] == reportFieldList.options[i].value) {
				//contains speciated fields
				if (altBaseFieldIdCompatibility[j] == "b") {
				  containSpeciated = true;
				  break;
				}
			}
		}
		if (!containWeightOrCost) {
			//contains Cost (base_field_id = 229) or Weight (lb.) (base_field_id = 247)
			if (reportFieldList.options[i].value == "229" || reportFieldList.options[i].value == "247") {
				containWeightOrCost = true;
			}
		}
	} //end of for each report field
	//if report if contains speciated field then CAS Number or Chemical Name is needed
	if (containSpeciated && containWeightOrCost) {
		result = false;
	}
	return result;
}

function publishTemplateAudit() {
   var reportFieldList = document.getElementById("reportFieldList");
   selectAllItems(reportFieldList);
   if(reportFieldList.length <= 0) {
      alert(messagesData.youmustselectreportfield);
      return;
   }
   document.adHocWasteReportForm.target='';
	var tmpTemplateName = $v("globalizationLabelLetter")+$v("templateId")+"-"+$v("templateName");
	openWinGeneric("publishtemplate.do?action=publish&reportType=waste&calledFrom=adhocWaste&templateId="+$v("templateId")+"&templateName="+tmpTemplateName,
		            "publishTemplate","650","275","yes","80","80");
}

function unpublishTemplateAudit() {
   var reportFieldList = document.getElementById("reportFieldList");
   selectAllItems(reportFieldList);
   if(reportFieldList.length <= 0) {
      alert(messagesData.youmustselectreportfield);
      return;
   }
   document.adHocWasteReportForm.target='';
	var tmpTemplateName = $v("globalizationLabelLetter")+$v("templateId")+"-"+$v("templateName");
	openWinGeneric("publishtemplate.do?action=unpublish&reportType=waste&calledFrom=adhocWaste&templateId="+$v("templateId")+"&templateName="+tmpTemplateName,
		            "publishTemplate","600","360","yes","80","80");
}