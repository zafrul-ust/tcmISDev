function initializeDropDowns() {
	buildDropDown(companyColl, "companyId", $v("selectedCompanyId"));
	companyChanged($v("selectedFacilityId"), $v("selectedInvoiceDate"));
}

function companyChanged(selectedFacilityId, selectedInvoiceDate) {
	buildDropDown(facilityColl[$v("companyId")], "facilityId", selectedFacilityId);
	facilityChanged(selectedInvoiceDate);
}

function facilityChanged(selectedInvoiceDate) {
	buildDropDown(endDateColl[$v("companyId")][$v("facilityId")], "invoiceDate", selectedInvoiceDate);
}

function buildDropDown(arr, elementName, selectedId) {
	var obj = $(elementName);
	for (var i = obj.length; i > 0; i--)
		obj[i] = null;
	
	var selectedIndex = 0;
	if (arr && arr.length != 0) {
		for (var i = 0; i < arr.length; i++) {
			if (selectedId && arr[i].id == selectedId)
				selectedIndex = i;
			setOption(i, arr[i].name, arr[i].id, elementName);
		}
	}
	obj.selectedIndex = selectedIndex;
}

function validateCriteria()
{
var errorMessage = messagesData.validvalues +"\n\n";
var errorCount = 0;

var invoiceDate  =  document.getElementById("invoiceDate");
var facilityId  =  document.getElementById("facilityId");

if (facilityId.value.length==0)
{
  errorMessage = errorMessage + messagesData.facilityid + ".\n" ;
  errorCount = 1;
}

if (invoiceDate.value.length==0)
{
 errorMessage = errorMessage + messagesData.invoicedate + ".\n" ;
 errorCount = 1;
}

if (errorCount >0)
{
  alert(errorMessage);
  return false;
}
else
{
  return true;
}
}

function doexcelpopup() {

	if(!validateCriteria())
		return;	
	var userAction = document.getElementById("userAction");
	userAction.value = 'buttonCreateExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',"show_excel_report_file","610","600","yes");  
	document.genericForm.target='show_excel_report_file';
	var a = window.setTimeout("document.genericForm.submit();",500);
}

function search() {
    var now = new Date();
    document.getElementById("startSearchTime").value = now.getTime();
  if(!validateCriteria()) 
	return false;
  document.genericForm.target='resultFrame';
  var userAction  =  document.getElementById("userAction");
  userAction.value = "search";
  parent.showPleaseWait();
  return true;
}

function showErrorMessages()
{
		parent.showErrorMessages();
}
function resultOnLoad()
{
 displaySearchDuration();
 setResultFrameSize();
}