var resizeGridWithWindow = true;

function myResultOnLoad() {
	if (window['mrGridConfig']) {
		initPopupGridWithGlobal(mrGridConfig);
		 $('mrFooter').innerHTML = "&nbsp;&nbsp;<b>"+messagesData.recordFound+":</b> "+$('totalLines').value;
	}
	if (window['allocationGridConfig']) {
		initPopupGridWithGlobal(allocationGridConfig);
		 $('allocationFooter').innerHTML = "&nbsp;&nbsp;<b>"+messagesData.recordFound+":</b> "+$('totalLines2').value;
	}
}

function showMrDiv() {
	 document.getElementById("mrDiv").style["display"] = "";
	 document.getElementById("showMrRow").style["display"] = "none";
	 document.getElementById("hideMrRow").style["display"] = "";
}

function hideMrDiv() {
	 document.getElementById("mrDiv").style["display"] = "none";
	 document.getElementById("showMrRow").style["display"] = "";
	 document.getElementById("hideMrRow").style["display"] = "none";
}


function showAllocationDiv() {
	 document.getElementById("allocationDiv").style["display"] = "";
	 document.getElementById("showAllocationRow").style["display"] = "none";
	 document.getElementById("hideAllocationRow").style["display"] = "";
}

function hideAllocationDiv() {
	 document.getElementById("allocationDiv").style["display"] = "none";
	 document.getElementById("showAllocationRow").style["display"] = "";
	 document.getElementById("hideAllocationRow").style["display"] = "none";
}

function doRefresh() {
	window.location.reload();
}