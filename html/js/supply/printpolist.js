function checkSearchPo() 
{
   var searchPo = document.getElementById("searchText");
   if ( !(isInteger(searchPo.value.trim()))) {
      alert(messagesData.searchInput);
      return false;
   }else {
      return true;
   }
}

function showErrorMessages()
{
  parent.showErrorMessages();
}

//This works only for popup windows and IE. Close the window after clicking Esc key
var windowCloseOnEsc = true; 


function submitSearchForm() {
	
// Make sure to not set the target of the form to anything other than resultFrame
	 
	document.genericForm.target = 'resultFrame';
	document.getElementById("uAction").value = 'search';
	// set start search time
	var now = new Date();
	var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	var flag = validateSearchCriteriaInput();
	if (flag) {
		showPleaseWait();
		return true;
	}
	else {
		return false;
	}
}


//Validate the search form
function validateSearchCriteriaInput() {
	var errorMessage = "";
	var errorCount = 0;

	try {
		var searchArgument = $v("searchText"); // same as document.getElementById("searchArgument").value.trim(). Using this will make the codes more readable
			
		// Please refer to /js/common/formchek.js for more validation functions
		if (searchArgument.length == 0 || !isInteger(searchArgument,true)) {
				errorMessage += messagesData.searchInput + "\n";
				errorCount = 1;
				searchInput.value = "";
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


