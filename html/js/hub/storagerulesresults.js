
function myonload() {
	resultOnLoadWithGrid(gridConfig);
	showErrors();
	for (var i = 1; i <= beangrid.getRowsNum(); i++) {
		addMoverToRow(i);
	}
}

function addMoverToRow(rowId) {
	var moverhtml = '<input name="moveRule'+rowId+'" id="moveRule'+rowId+'" type="button" value="'+messagesData.move+'" class="smallBtns" onmouseover="this.className=\'smallBtns smallBtnsOver\'" onmouseout="this.className=\'smallBtns\'"  onclick="moveRule('+rowId+');">' +
		'&nbsp;<input name="moveRuleUp'+rowId+'" id="moveRuleUp'+rowId+'" type="button" value=" &uarr; " class="smallBtns" onmouseover="this.className=\'smallBtns smallBtnsOver\'" onmouseout="this.className=\'smallBtns\'"  onclick="moveRuleUp('+rowId+');">' +
		'&nbsp;<input name="moveRuleDown'+rowId+'" id="moveRuleDown'+rowId+'" type="button" value=" &darr; " class="smallBtns" onmouseover="this.className=\'smallBtns smallBtnsOver\'" onmouseout="this.className=\'smallBtns\'"  onclick="moveRuleDown('+rowId+');">';
	gridCell(beangrid, rowId, "mover").setCValue(moverhtml);
}

function swapRowValues(column, row1, row2) {
	var val1 = getCellValueByRowCol(row1, column);
	setCellValueByRowCol(row1, column, getCellValueByRowCol(row2, column));
	setCellValueByRowCol(row2, column, val1);
	addMoverToRow(row1);
	addMoverToRow(row2);
}

function getCellValueByRowCol(row, column) {
	return gridCell(beangrid,row,column).getValue();
}

function setCellValueByRowCol(row, column, value) {
	gridCell(beangrid, row, column).setValue(value);
}

function moveRuleUp(row) {
	if (row > 1) {
		for (var i = 0; i < columnConfig.length; i++) {
			var colId = columnConfig[i].columnId;
			if (colId != "ruleOrder") {
				swapRowValues(colId, row, row-1);
			}
		}
		rowFieldChanged(row);
		rowFieldChanged(row-1);

		beangrid.selectRow(beangrid.getRowIndex(row-1),null,false,false);
	}
}

function moveRuleDown(row) {
	if (row < beangrid.getRowsNum()) {
		for (var i = 0; i < columnConfig.length; i++) {
			var colId = columnConfig[i].columnId;
			if ( ! (colId == "ruleOrder" || colId == "mover")) {
				swapRowValues(colId, row, row+1);
			}
		}
		rowFieldChanged(row);
		rowFieldChanged(row+1);
		
		beangrid.selectRow(beangrid.getRowIndex(row+1),null,false,false);
	}
}

function moveRule(row) {
	var moveTo = "";
	var element = $("moveTo"+row);
	if (element) {
		moveTo = element.value;
		element.value = "";
	}
	if (moveTo.length == 0) {
		return;
	}
	else if (isNaN(moveTo)) {
		alert(formatMessage(messagesData.positiveError, messagesData.order));
	}
	else if (1*moveTo < 1) {
		moveTo = 1;
	}
	else if (1*moveTo > beangrid.getRowsNum()) {
		moveTo = beangrid.getRowsNum();
	}
	else {
		moveTo = 1*moveTo;
	}
	
	var moveFrom = 1*getCellValueByRowCol(row,"ruleOrder");
	
	try {
		while (moveFrom != moveTo) {
			if (moveFrom > moveTo) {
				moveRuleUp(row--);
				moveFrom--;
			}
			else {
				moveRuleDown(row++);
				moveFrom++;
			}
		}
	} catch(e) {
		alert(e);
	}
}

function rowFieldChanged(row) {
	setCellValueByRowCol(row, "updated", "Y");
}

function addStorageRule() {
	var id = beangrid.getRowsNum() + 1;
	var branchPlant = $v("branchPlant");
	var ruleOrder = 1*getCellValueByRowCol(id-1, "ruleOrder");
	beangrid.addRow(id, ['Y', 'Y', '', ruleOrder+1, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', branchPlant, ''], id - 1);
	addMoverToRow(id);
}

function isNonNegativeNumber(number) {
	return ! (isNaN(number) || number < 0);
}

function validateStorageRules() {
	// Rule 1: Max Size > Min Size >= 0; Max Size and Min Size are Integers
	// Rule 2: Max/Min Size Detect and Units must be present if Max/Min Size is present
	// Rule 3: storageFamily is not empty
	// Rule 4: Warn user about unapplied reordering
	var rule1msg = "";
	var rule2msg = "";
	var rule3msg = "";
	var warnMsg = "";
	for (var i = 1; i <= beangrid.getRowsNum(); i++) {
		// Rule 1
		var minSize = getCellValueByRowCol(i, "minSize");
		var maxSize = getCellValueByRowCol(i, "maxSize");
		if (rule1msg.length == 0) {
			if ( ! isNonNegativeNumber(minSize)) {
				rule1msg += formatMessage(messagesData.positiveError, messagesData.minSize)+"\n";
			}
			if ( ! isNonNegativeNumber(maxSize)) {
				rule1msg += formatMessage(messagesData.positiveError, messagesData.maxSize)+"\n";
			}
			if (maxSize.length > 0 && minSize.length > 0 && 1*maxSize <= 1*minSize) {
				rule1msg += messagesData.minMaxError+"\n";
			}
		}
		
		// Rule 2
		var detectMinSize = getCellValueByRowCol(i, "detectMinSize");
		var detectMaxSize = getCellValueByRowCol(i, "detectMaxSize");
		if (rule2msg.length == 0) {
			if (maxSize.length > 0 && detectMaxSize.length == 0 ||
					minSize.length > 0 && detectMinSize.length == 0) {
				rule2msg += messagesData.minMaxDetectError+"\n";
			}
		}
		
		// Rule 3
		var storageFamily = getCellValueByRowCol(i, "storageFamily");
		if (storageFamily.length == 0 && rule3msg.length==0) {
			rule3msg += messagesData.storageFamilyError+"\n";
		}
		
		// Rule 4
		var move = getCellValueByRowCol(i, "moveTo");
		if (move.length > 0 && warnMsg.length == 0) {
			warnMsg+=messagesData.reorderingWarning+"\n";
		}
	}
	
	var valid = true;
	if (rule1msg.length > 0 || rule2msg.length > 0 || rule3msg.length > 0) {
		alert(rule1msg+rule2msg+rule3msg);
		valid = false;
	}
	else if (warnMsg.length > 0) {
		if (confirm(warnMsg)) {
			valid = true;
		}
		else {
			valid = false;
		}
	}
	
	return valid;
	
}

function updateStorageRules() {
	var valid = false;
	try {
		valid = validateStorageRules();
	} catch(e) {
		if (console) {
			console.log(e);
		}
		alert(messagesData.updateError);
		valid = false;
	}
	if (valid) {
		parent.showPleaseWait();
		setTimeout(function() {
			$("userAction").value = "update";
			beangrid.parentFormOnSubmit();
			
			document.genericForm.submit();	
		},10);
	}
}

function showErrors() {
	if (showErrorMessage) {
		var errorMessagesArea = $("errorMessagesArea");
		errorMessagesArea.style.display="";

		if (dhxWins == null) {
			dhxWins = new dhtmlXWindows();
			dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
		}

		if (!dhxWins.window("errorMessagesArea")) {
			// create window first time
			errorWin = dhxWins.createWindow("errorMessagesArea",0,0, 400, 200);
			errorWin.setText("");
			errorWin.clearIcon();  // hide icon
			errorWin.denyResize(); // deny resizing
			errorWin.denyPark();   // deny parking

			errorWin.attachObject("errorMessagesArea");
			// errorWin.attachEvent("onClose",
			// function(inputWin){inputWin.hide();});
			errorWin.center();
			// setting window position as default x,y position doesn't seem
			// to work, also hidding buttons.
			errorWin.setPosition(328, 131);
			errorWin.button("minmax1").hide();
			errorWin.button("park").hide();
			// errorWin.button("close").hide();
			errorWin.setModal(true);
		}else{
			// just show
			errorWin.setModal(true);
			dhxWins.window("errorMessagesArea").show();
		}
	}
}