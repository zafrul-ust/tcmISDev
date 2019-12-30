function showBaseFields(type) {
    openWinGeneric("basefielddescription.do?type="+type,"basefielddescription","600","400","yes","80","80");
}

function getListSearch()
{

    var listID = document.getElementById("chemicalListName");
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

function facilityChanged() {
  var facilityO = document.getElementById("facilityId");
  //var reportingEntityO = document.getElementById("reportingEntity");
  var applicationIdO = document.getElementById("application");

  var selectedFacility = facilityO.value;
/*
  for (var i = reportingEntityO.length; i > 0;i--) {
    reportingEntityO[i] = null;
  }
*/
  for (var i = applicationIdO.length; i > 0;i--) {
    applicationIdO[i] = null;
  }
/*
  if (facilityO.value == "All") {
   setReportingEntity(0,"All","")
   setApplication(0,"All","")
  }
  else if (facilityO.value == "myfacilities") {
    setReportingEntity(0,"All","")
    setReportingEntity(1,"My Reporting Entities","myreportingentities")
    setApplication(0,"All","")
    setApplication(1,"My Work Areas","myworkareas")

  }
  else {
  */
  //showReportingEntityLinks(selectedFacility);
showApplicationLinks(selectedFacility);
  //}
  //reportingEntityO.selectedIndex = 0;
}
/*
function reportingEntityChanged() {
  var facilityO = document.getElementById("facilityId");
  var reportingEntityO = document.getElementById("reportingEntity");
  var applicationIdO = document.getElementById("application");
  var selectedFacility = facilityO.value;
  var selectedReportingEntity = reportingEntityO.value;

  for (var i = applicationIdO.length; i > 0;i--) {
    applicationIdO[i] = null;
  }

//  if (reportingEntityO.value == "All") {
//   setReportingEntity(0,"All","")
//   setApplication(0,"All","")
//  }
//  else if (reportingEntityO.value == "myreportingentities") {
//    setReportingEntity(0,"All","")
//    setReportingEntity(1,"My Reporting Entities","My Reporting Entities")
//    setApplication(0,"All","")
//    setApplication(1,"My Work Areas","myworkareas")
//  }
//  else {
//  showReportingEntityLinks(selectedFacility);
  showApplicationLinks(selectedFacility,selectedReportingEntity);
//  }
//  reportingEntityO.selectedIndex = 0;
  applicationIdO.selectedIndex = 0;
}



function showReportingEntityLinks(selectedFacility)
{
  var reportingEntityArray = new Array();
  reportingEntityArray = altReportingEntity[selectedFacility];

  var reportingEntityDescArray = new Array();
  reportingEntityDescArray = altReportingEntityDesc[selectedFacility];

  for (var i=0; i < reportingEntityArray.length; i++) {
    setReportingEntity(i,reportingEntityDescArray[i],reportingEntityArray[i])
  }
}

function setReportingEntity(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var reportingEntityO = document.getElementById("reportingEntity");
  reportingEntityO[href] = optionName;
}
*/
function showApplicationLinks(selectedFacility) {
  var applicationIdArray = new Array();
  applicationIdArray = altApplication[selectedFacility];

  var applicationDescArray = new Array();
  applicationDescArray = altApplicationDesc[selectedFacility];
/*
  if(applicationIdArray.length == 0) {
    setApplication(0,"All","")
  }
*/
  for (var i=0; i < applicationIdArray.length; i++) {
    setApplication(i,applicationDescArray[i],applicationIdArray[i])
  }
}

function setApplication(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var applicationIdO = document.getElementById("application");
  applicationIdO[href] = optionName;
}




function generateUsageReportAudit() {
      var beginDate = document.getElementById("beginDate");
   if(beginDate == null || beginDate.value.length < 1){
       alert(messagesData.begindaterequired);
        return;
   }
    var endDate = document.getElementById("endDate");
    if(endDate == null || endDate.value.length < 1){
       alert(messagesData.enddaterequired);
        return;
   }
   if(document.genericForm.reportType[1].checked==true) {
     var casNumber = document.getElementById("casNumber");
     if(casNumber == null || casNumber.value == "") {
        alert(messagesData.youmustentercasnumber);
        return;
     }
   }

  var submitValue = document.getElementById("submitValue");
  submitValue.value = "submit";

      var randomNumber=Math.floor(Math.random()*1001);
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_formattedUsageExcel'+randomNumber,'650','600','yes');
    document.genericForm.target='_formattedUsageExcel'+randomNumber;
    var a = window.setTimeout("document.genericForm.submit();",500);
}

function generateVocUsageReportAudit() {
      var beginDate = document.getElementById("beginDate");
   if(beginDate == null || beginDate.value.length < 1){
       alert(messagesData.begindaterequired);
        return;
   }
    var endDate = document.getElementById("endDate");
    if(endDate == null || endDate.value.length < 1){
       alert(messagesData.enddaterequired);
        return;
   }
  var submitValue = document.getElementById("submitValue");
  submitValue.value = "submit";
var randomNumber=Math.floor(Math.random()*1001);
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_formattedVocUsageExcel'+randomNumber,'650','600','yes');
    document.genericForm.target='_formattedVocUsageExcel'+randomNumber;
    var a = window.setTimeout("document.genericForm.submit();",500);
}

function generateMsdsReportAudit() {

  var submitValue = document.getElementById("submitValue");
  submitValue.value = "submit";
var randomNumber=Math.floor(Math.random()*1001);
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_formattedMsdsExcel'+randomNumber,'650','600','yes');
    document.genericForm.target='_formattedMsdsExcel'+randomNumber;
    var a = window.setTimeout("document.genericForm.submit();",500);
}

function generateMonthlyVocReportAudit() {
  var beginDate = document.getElementById("beginDate");
  if(beginDate == null || beginDate.value == null || beginDate.value=='') {
      alert(messagesData.daterequired);
      return false;
  }
   if(document.genericForm.reportGenerationType[1].checked==true) {
     var reportName = document.getElementById("reportName");
     if(reportName == null || reportName.value == "") {
        alert(messagesData.pleaseenterreportname);
        return;
     }
   }
  var submitValue = document.getElementById("submitValue");
  submitValue.value = "submit";
var randomNumber=Math.floor(Math.random()*1001);
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_formattedMonthlyVocExcel'+randomNumber,'650','600','yes');
    document.genericForm.target='_formattedMonthlyVocExcel'+randomNumber;
    var a = window.setTimeout("document.genericForm.submit();",500);
}

function selectAllItems(obj) {
   for(i=0;i<obj.length;i++) {
      obj.options[i].selected=true;
   }
}

function getProfileSearch()
{
    openWinGeneric("profilesearch.do","","600","400","yes","80","80");
}

function getManagementOptionSearch()
{
    openWinGeneric("managementoptionsearch.do","","600","400","yes","80","80");
}



function removeInitialMessage(obj) {
  for(i=0;i<obj.length;i++) {
    if(obj.options[i].value == '') {
      removeItem(obj);
    }
  }
}

function loadForm() {

}
/*
var formFieldName;
var cal5 = new CalendarPopup();
cal5.setDisplayType("month");
cal5.setReturnMonthFunction("monthReturn");
cal5.showYearNavigation();

function getMonthCal(name) {
formFieldName = name;
    alert(name);
cal5.showCalendar(name);
}

*/
function monthReturn(y,m) {
    var formField = document.getElementById(formFieldName);
    formField.value = m+"/"+y;
  //document.genericForm.beginDate.value=m+"/"+y;
}

function onFormattedUsageLoad() {
    //alert('load');
    document.genericForm.reportType[2].checked = true;
}

function onFormattedVocUsageLoad() {

}

function onFormattedMonthlyVocLoad() {

document.genericForm.criteria[0].checked = true;
document.genericForm.reportGenerationType[0].checked = true;
}