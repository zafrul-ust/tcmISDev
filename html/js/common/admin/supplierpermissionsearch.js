/*Call this if you want to initialize something on load in the search frame.*/
function mySearchOnload()
{
 setSearchFrameSize();
}

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 document.genericForm.target='resultFrame';
 var action = document.getElementById("action");
 action.value = 'search';
 var flag = validateForm();
  if(flag) {
   parent.showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function validateForm() {
	return true;
}

function closeWindow() {
	window.parent.close();
}

function generateExcel() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_SupplierPermissionGenerateExcel','800','600','yes');
    document.genericForm.target='_SupplierPermissionGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}

function showUserSupplier(){
	var personnelId = document.getElementById("personnelId");
	var loc = "supplierpermissionaccessadminmain.do?action=search&personnelId="+personnelId.value+"";
  openWinGeneric(loc,"showUserSupplier123","600","500","yes","80","80","no")
}

function companyChanged() {
  var companyIdO = document.getElementById("companyId");
  var supplierIdO = document.getElementById("supplier");
  var selectedCompany = companyIdO.value;

  for (var i = supplierIdO.length; i >= 0;i--) {
    supplierIdO[i] = null;
  }

  showSupplier(selectedCompany);
  supplierIdO.selectedIndex = 0;
}

function showSupplier(selectedCompany) {
  var supplierIdArray = new Array();
  supplierIdArray = altSupplierId[selectedCompany];
  var supplierNameArray = new Array();
  supplierNameArray = altSupplierName[selectedCompany];

  for (var i=0; i < SupplierIdArray.length; i++) {
    setOption(i,supplierNameArray[i],supplierIdArray[i], "supplier")
  }
}

function refresh() {
	window.parent.opener.openSupplierPages();
}