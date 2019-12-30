// function submitSearchForm() {} Overridden in iframegridresizemain.js
// function createXSL() {} Overridden in iframegridresizemain.js

function validateForm(){
	if(document.genericForm.searchField.value == 'ITEM_ID') {
		    if(!isInteger(document.genericForm.searchArgument.value.trim(), true)) {
		      alert(messagesData.itemInteger);
		      return false;
		    }
		}
	
	
		document.getElementById("startSearchTime").value = new Date().getTime();
		return true;
	
}

function selectOnlyOne(theBox) {
	if (theBox.checked) {
		if (theBox.name == "headerChargesOnly") {
			document.getElementById('lineChargesOnly').checked=false;
		}
		else {
			document.getElementById('headerChargesOnly').checked=false;
		}
	}
	return true;
}
