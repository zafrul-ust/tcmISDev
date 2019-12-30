
function submitSearchForm() {
	var now = new Date();
	$("startSearchTime").value = now.getTime();
	$("action").value = "search";
	document.genericForm.submit();
}

function generatePickableUnitExcel() {
	$("action").value = "createExcel";
	document.genericForm.submit();
}