function buildDropDown( arr, eleName ) {
   var obj = $(eleName);
   for (var i = obj.length; i > 0;i--)
     obj[i] = null;
  // if size = 1 or 2 show last one, first one is all, second one is the only choice.
  if( arr == null || arr.length == 0 ) 
	  setOption(0,'ALL','', eleName);
  else if( arr.length == 1 )
	  setOption(0,arr[0].name,arr[0].id, eleName);
  else {
      var start = 0;
	  for ( var i=0; i < arr.length; i++) {
	    	setOption(start++,arr[i].name,arr[i].id,eleName);
	  }
  }
  obj.selectedIndex = 0;
}

function setCompany() {
 	buildDropDown(altCompany,"companyId");
 	$('companyId').onchange = companyChanged;
    companyChanged();
}

function companyChanged()
{
   var companyA = $("companyId");
   buildDropDown(altFacility[companyA.value],"facilityId");
}


function validateSearchForm() {
    var result = true;
    //todo validations go here
    return result;
}

function submitSearch()
{
    //set start search time
    var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
    startSearchTime.value = now.getTime();
    if(validateSearchForm()) {
        showPleaseWait();
        document.genericForm.target='resultFrame';
        document.getElementById("userAction").value = 'search';
        document.genericForm.submit();
    }
}

function createExcel()
{
    if (validateSearchForm()) {
        var tmpCompany = $('companyId');
        $('companyName').value = tmpCompany.options[tmpCompany.selectedIndex].text;
        var tmpFacility = $('facilityId');
        $('facilityName').value = tmpFacility.options[tmpFacility.selectedIndex].text;
        var tmpSearchField = $('searchField');
        $('searchFieldName').value = tmpSearchField.options[tmpSearchField.selectedIndex].text;
        var tmpSearchMode = $('searchMode');
        $('searchModeName').value = tmpSearchMode.options[tmpSearchMode.selectedIndex].text;
        document.getElementById("userAction").value = "createExcel";
        document.genericForm.target='_ReceivingQcExcel';
        openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_ReceivingQcExcel','650','600','yes');
        setTimeout("document.genericForm.submit()",300);
    }
}

function checkDisplayOptionCatAdd() {
    var displayOptionCatAdd  =  document.getElementById("onlyDisplayCatalogAdd");
    if (displayOptionCatAdd.checked){
        document.getElementById("onlyDisplayEdiError").checked = false;
    }
}

function checkDisplayOptionEdiError() {
    var displayOptionEdiError  =  document.getElementById("onlyDisplayEdiError");
    if (displayOptionEdiError.checked){
        document.getElementById("onlyDisplayCatalogAdd").checked = false;
    }
}


