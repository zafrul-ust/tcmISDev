// This works only for popup windows and IE. Close the window after clicking Esc key
var windowCloseOnEsc = true;

var children = new Array();

function closeAllchildren()
{
	// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
	//	if (document.getElementById("uAction").value != 'new') {
	try {
		for(var n=0;n<children.length;n++) {
			try {
				children[n].closeAllchildren();
			} catch(ex){}
			children[n].close();
		}
	} catch(ex){}
	children = new Array();
}

function createXSL() {
	$('uAction').value = 'createExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_KitManagementExcel', '650', '600', 'yes');
	document.forms[0].target = '_KitManagementExcel';
	var a = window.setTimeout("document.genericForm.submit();", 50);
}


function submitSearchForm() {
	/*
	 * Make sure to not set the target of the form to anything other than
	 * resultFrame
	 */

	document.genericForm.target = 'resultFrame';
	document.getElementById("uAction").value = 'search';
	// set start search time
	var now = new Date();
	var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
   	showPleaseWait();
    document.genericForm.submit(); 
	return true;

}