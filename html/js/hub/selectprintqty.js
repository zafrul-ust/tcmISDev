
function selectPrintQty() {
	var printQty = $v("printQty");
	try {
		parent.opener.printQtyChanged(printQty);
	}
	catch (exx) {
	}
	parent.close();
}