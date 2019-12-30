// function submitSearchForm() {} Overridden in iframegridresizemain.js
// function createXSL() {} Overridden in iframegridresizemain.js

function validateForm(){
	if ($v("searchText").length < 1) {
		alert(messagesData.missingSearchText);
		return false;
	}
	else {
		return true;
	}
}