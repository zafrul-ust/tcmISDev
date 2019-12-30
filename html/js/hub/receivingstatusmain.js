// This works only for popup windows and IE. Close the window after clicking Esc key
var windowCloseOnEsc = true;

function submitSearchForm() {
	getInventoryGroupList()
	$("uAction").value = 'search';
	document.genericForm.target = 'resultFrame';
	showPleaseWait();
	document.getElementById("startSearchTime").value = new Date().getTime();
	return true;
}

function createXSL() {
	$('uAction').value = 'createExcel';
	document.genericForm.target = '_DisplayOnlyExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_DisplayOnlyExcel', '650', '600', 'yes');
	window.setTimeout("document.genericForm.submit();", 50);
}

var legendArea = "showLegendArea";
var legendSize = 180;

function setLegendArea(areaName, areaSize) {
	legendArea = areaName;
	legendSize = areaSize;
}

function showLegend() {
	var showLegendArea = document.getElementById(legendArea);
	showLegendArea.style.display = "";

	var dhxWins = new dhtmlXWindows();
	dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, legendSize);
	legendWin.setText(messagesData.showlegend);
	legendWin.clearIcon(); // hide icon
	legendWin.denyResize(); // deny resizing
	legendWin.denyPark(); // deny parking
	legendWin.attachObject(legendArea);
	legendWin.attachEvent("onClose", function(legendWin) {
		legendWin.hide();
	});
	legendWin.center();
}

function checkReceivingStatus() {
	var status = $v("receivingStatus");
	var assignedTo = $("assignedTo");
	for ( var i = assignedTo.length; i > 0; i--) {
		assignedTo[i] = null;
	}
	if ("QC Ready" == status) {
		var hub = $v("hub");
		url = "receivingstatusmain.do?uAction=userSearch&hub="+hub;
		callToServer(url);
	}
	else {
		  $("assignedTo").style["display"] = "none";
		  $("assignedToLabel").style["display"] = "none";
	}
}

function loadAssignees(assignees) {
	setOption(0, "All", "", "assignedTo");
	setOption(1, "Unassigned", "-1", "assignedTo");
	for (var i = 0; i < assignees.length; i++) {
		setOption(i + 2, assignees[i].userName, assignees[i].userId, "assignedTo");
	}
	$("assignedTo").style["display"] = "";
	$("assignedToLabel").style["display"] = "";
}

function getInventoryGroupList() {
	var inventoryGroupListString = "";
	if ($("inventoryGroup").length == 1) {
		inventoryGroupListString = $("inventoryGroup").options[0].value;
	}
	else {
		for (i = 1; i < $("inventoryGroup").length; i++) {
			if ($("inventoryGroup").options[0].selected || $("inventoryGroup").options[i].selected) {
				if (inventoryGroupListString == "")
					inventoryGroupListString = $("inventoryGroup").options[i].value;
				else
					inventoryGroupListString += "|" + $("inventoryGroup").options[i].value;
			}
		}
	}
	$("inventoryGroupList").value = inventoryGroupListString;
}
