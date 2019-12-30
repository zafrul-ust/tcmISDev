function finishPrinting() {
	if (!printed) {
		printed = true;
		try{
			parent.closeGeneratingWin();
		}catch(ex){}
		var x=document.getElementById("hiddenPrintTxtFrame");
		var y=(x.contentWindow || x.contentDocument);
		y.focus();
		y.print();
	}
}

function checkHiddenIFrameState() {
	if (!printed) {
		if (hiddenPrintIframe != null && hiddenPrintIframe.readyState && "complete" == hiddenPrintIframe.readyState) {
			finishPrinting();
		}
		else {
			setTimeout("checkHiddenIFrameState()", 500);
		}
	}
}

var hiddenPrintIframe = null;
var printed = false;

function initPage() {
	hiddenPrintIframe = document.getElementById("hiddenPrintTxtFrame");
	setTimeout("checkHiddenIFrameState()", 1000);
}
