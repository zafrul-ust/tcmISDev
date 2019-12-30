function submitSearchForm() {
	/*
	 * Make sure to not set the target of the form to anything other than
	 * resultFrame
	 */
	document.genericForm.target = 'resultFrame';
	$("uAction").value = 'search';
	// set start search time
	 $("startSearchTime").value = new Date().getTime();
} 