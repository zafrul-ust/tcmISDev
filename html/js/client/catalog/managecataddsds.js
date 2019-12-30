var dhxWins;
var beanGrid;
var children = new Array();
var count = 0;

function doOnLoad() {
	stopPleaseWait();
	popupOnLoadWithGrid(gridConfig);
	$("mainUpdateLinks").style["display"] = showUpdateLinks ? "" : "none";
}

function selectRow(rowId, colId) {
	selectedRowId = rowId;
}

//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function openDoc(URL) {
	children[children.length] = openWinGeneric(URL, "doc" + count, "700", "750", "yes", "50", "50", "20", "20", "no");
	count++;
}

function showErrorMessages() {
	$("errorMessagesArea").style.display = "";
	
	initializeDhxWins();
	if (!dhxWins.window("errorWin")) {
		// create window first time
		var errorWin = dhxWins.createWindow("errorWin", 0, 0, 450, 300);
		errorWin.setText(messagesData.errors);
		errorWin.clearIcon(); // hide icon
		errorWin.denyResize(); // deny resizing
		errorWin.denyPark(); // deny parking
		errorWin.attachObject("errorMessagesArea");
		errorWin.attachEvent("onClose", function(errorWin) {
			$("errorMessagesArea").style.display = "none";
			errorWin.hide();
		});
		errorWin.center(); 
	} else {
		// just show
		dhxWins.window("errorWin").show();
	}
}

function checkAll() {
	var checkall = $("chkAllOkDoUpdate");
	var rowsNum = beanGrid.getRowsNum(); // Get the total rows of the grid
	var columnId = beanGrid.getColIndexById("okDoUpdate");

	for (var rowId = 1; rowId <= rowsNum; rowId++)
		if (getColPermission(beanGrid, rowId, "okDoUpdate") == 'Y')
			cell(rowId, columnId).setChecked(checkall.checked);

	return true;
}

function updateData() {
	showPleaseWait();
	$("uAction").value = 'update';
	if (beanGrid != null)
		beanGrid.parentFormOnSubmit();
	document.genericForm.submit();
}

function addNewSDS() {
	children[children.length] = openWinGeneric("uploadfile.do?fileName=" + $v("requestId") + "&modulePath=catalogAddMsds&localeLocked=N", "uploadfile"  + $v("requestId"), 500, 200, 'yes');
}

function closeTransitWin() {
	showPleaseWait();
	$("uAction").value = '';
	document.genericForm.submit();
}

function doOnUnload() {
	closeAllchildren();
}

function closeAllchildren() {
	try {
		for(var n=0; n<children.length; n++) {
			try {
				children[n].closeAllchildren();
			} catch(ex){}
			children[n].close();
		}
	} catch(ex){}
	children = new Array();
}