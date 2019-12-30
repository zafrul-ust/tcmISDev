function submitSearchForm() {

	document.genericForm.target = 'resultFrame';
	$("uAction").value = 'search';
	// set start search time
	$("startSearchTime").value =  new Date().getTime();
	if (validateForm()) {
		showPleaseWait();
		return true;
	}
	else {
		return false;
	}
}

var defaultSelection = {id: "", name: "", nodefault: true};

function companyChanged() {
   var curCompany = $v("companyId");
	   for(i=0; i < priceGroupCompanies.length; i++) {
	   		if( priceGroupCompanies[i].id == curCompany ) {
			   buildDropDown(priceGroupCompanies[i].priceGroups, defaultSelection, "priceGroupId");
	   			break;
	   		}
	   }

}

function loadCSV(data) {
	try {
	    document.getElementById("uploadedCSV").value = "";
	    document.getElementById("csvForm").reset();
		document.resultFrame.importData(data);
	}
	catch (ex) {
		alert(ex);
	}
}

function setCompanies() {
 	buildDropDown(priceGroupCompanies, defaultSelection,"companyId");
 	$('companyId').onchange = companyChanged;
   
 	companyChanged();
}

// Validate the search form
function validateForm() {
	var errorMessage = "";
	var errorCount = 0;

	try {
		var searchArgument = $v("searchArgument"); 
		var searchField = $v("searchField");
		
		// Please refer to /js/common/formchek.js for more validation functions
		if ((searchField.length != 0) && (searchField == "itemId") ) {
			if ((searchArgument.length != 0) && (!(isInteger(searchArgument, true)) || (searchArgument * 1 == 0))) {
				errorMessage += messagesData.searchInput + "\n";
				errorCount = 1;
				searchInput.value = "";
			}
		}

	}
	catch (ex) {
		errorCount++;
	}
	if (errorCount > 0) {
		alert(errorMessage);
		return false;
	}
	return true;
}

function toggleExpireDates(){
	
}
