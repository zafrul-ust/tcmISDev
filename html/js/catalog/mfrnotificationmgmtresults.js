var selectedRow = -1;

function resultOnLoad() {	
	 totalLines = document.getElementById("totalLines").value;
	 if (totalLines > 0)
	 {
	  	document.getElementById("mfrNotificationMgmtBean").style["display"] = "";
	  	initGridWithGlobal(gridConfig); 
	 }
	 else
	 {
	   document.getElementById("mfrNotificationMgmtBean").style["display"] = "none";
	 }
	 displayGridSearchDuration(); 
	 setResultFrameSize(); 
}

function updateQcAssignees() {
	document.genericForm.target='';
	document.getElementById('uAction').value = 'update';

	parent.showPleaseWait();

	// Call this function to send the data from grid back to the server
	if (beanGrid != null) {
		beanGrid.parentFormOnSubmit();
	}

	document.genericForm.submit();
}

function selectRow(row, cell) {
	selectedRow = row;
}

function rightClickRow(rowId, cellInd) {
	selectedRow = rowId;
	toggleContextMenu('rightClickMenu');
}

function viewNotificationRequest() {
	var notificationId = gridCellValue(beanGrid, selectedRow, "notificationId");
	var loc = "/tcmIS/catalog/mfrnotificationmain.do?uAction=reload&notificationId="
		+ notificationId;
	
	try {
		parent.parent.openIFrame("mfrnotification"+notificationId,loc,"Manufacturer Notification " + notificationId,"","N");
	}
	catch (ex) {
		children[children.length] = openWinGeneric(loc,"mfrnotification","700","500","yes","50","50","20","20","no");
	}
}

function closeAllchildren() {
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