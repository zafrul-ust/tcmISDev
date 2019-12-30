var windowCloseOnEsc = true;
var selectedRow;
var beanGrid;
var children = new Array();

function selectRow(rowid) {
	selectedRow = rowid;
	
	var selectedUser = parent.document.getElementById("deleteResultLink");
	selectedUser.innerHTML = '<a href="#" onclick="call(\'deleteUser\'); return false;">' 
		+ messagesData.deleteselect 
		+ ' ' + cellValue(rowid, "fullName") + '</a>';
}

function closeAllchildren() {
	try {
		for (var n = 0; n < children.length; n++) {
			try {
				children[n].closeAllchildren();
			} catch (ex) {}
			children[n].close();
		}
	} catch (ex) {}
	children = new Array();
}

function buildDropDown(arr, eleName, selectedIndex) {
	var obj = $(eleName);
	for (var i = obj.length; i > 0; i--)
		obj[i] = null;

	var start = 0;
	for (var i = 0; i < arr.length; i++) {
		setOption(start++, arr[i].name, arr[i].id, eleName);
		selectedIndex = arr[i].selected?i:selectedIndex;
	}

	if (selectedIndex)
		obj.selectedIndex = selectedIndex;
	else
		obj.selectedIndex = 0;
}

//Initialize drop downs
function initializeSearchInputs() {
	buildDropDown(companyColl, "companyId");
	companyChanged();
}

function addUser() {
	loc = "searchpersonnelmain.do?allowUserCreate=Y&companyId="+encodeURIComponent($v("companyId"));
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/haas/" + loc;
	
	children[children.length] = openWinGeneric(loc,"changepersonnel","600","450","yes","50","50","20","20","no");
}

//function to receive data from searchpersonnelmain.do
function personnelChanged(personnelId) {
	$("selectedPersonnelId").value = personnelId;
	submitMyForm('insert');
}

function deleteUser() {
	setGridCellValue(beanGrid, selectedRow, "update", true);
	submitMyForm('delete');
}

function submitMyForm(action) {
	var now = new Date();
	if ($("startSearchTime"))
		$("startSearchTime").value = now.getTime();
	else {
		try {
			parent.$("startSearchTime").value = now.getTime();
		} catch (ex) {}
	}
	$("uAction").value = action;
	document.genericForm.target = 'resultFrame';
	try {
		showPleaseWait();
	} catch (ex) {
		parent.showPleaseWait();
	}
	
	if (beanGrid != null) {
		beanGrid.parentFormOnSubmit();
	}
	var a = window.setTimeout("document.genericForm.submit();", 500);	
}

function companyChanged() {
	$("mfrNotificationLevel").value = companyColl[$("companyId").selectedIndex].mfrNotificationLevel;
	if ($v("mfrNotificationLevel") == "Catalog" && $v("levelOfControl") == "catalog") {
		$("catalogRow").style.display = "";
		buildDropDown(catalogColl[companyColl[$("companyId").selectedIndex].id], "catalogId");
	} else {
		$("catalogRow").style.display = "none";
		buildDropDown(new Array({id:"", name:""}), "catalogId");
	}
	catalogChanged();
}

function catalogChanged() {
	if ($v("mfrNotificationLevel") == "Catalog") {
		$("catalogCompanyId").value = catalogColl[companyColl[$("companyId").selectedIndex].id][$("catalogId").selectedIndex].catalogCompanyId;
	} else {
		$("catalogCompanyId").value = "";
	}
}