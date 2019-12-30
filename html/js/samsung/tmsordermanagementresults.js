var children = new Array();

/************************************NEW***************************************************/
var beanGrid;
var selectedRowId;
windowCloseOnEsc = true;

/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

function selectRow(rowId0,colId0,ee) {
    // to show menu directly
    rightClick = false;
    selectedRowId = rowId0;
    if( ee != null ) {
        if( ee.button != null && ee.button == 2 ) rightClick = true;
        else if( ee.which == 3  ) rightClick = true;
    }
    columnId = beanGrid.getColumnId(colId0);

    //do right mouse click
    if (rightClick) {
        buildRightClickOption(colId0);
    } //end of right mouse click
}   //end of method


function buildRightClickOption(colId0) {
    var vitems = new Array();
    if (colId0 > beanGrid.getColIndexById("prNumber") && cellValue(selectedRowId,"permission") == 'Y') {
        vitems[vitems.length] = "text=" + messagesData.cancelReservation + ";url=javascript:cancelReservation();";
    }else
        vitems[vitems.length] = "";
    replaceMenu('rightClickMenu', vitems);
    toggleContextMenu('rightClickMenu');
} //end of method

/*function cancelLineReservation() {
    $("userAction").value = 'cancelLineReservation';
    $("prNumber").value = cellValue(selectedRowId,"prNumber");
    $("lineItem").value = cellValue(selectedRowId,"lineItem");
    parent.showPleaseWait();
    parent.$("startSearchTime").value = new Date().getTime();
    //Submit the form in the result frame 
    document.genericForm.submit();
}*/

function cancelReservation() {
    $("userAction").value = 'cancelReservation';
    $("prNumber").value = cellValue(selectedRowId,"prNumber");
    parent.showPleaseWait();
    parent.$("startSearchTime").value = new Date().getTime();
    /* Submit the form in the result frame */
    document.genericForm.submit();
}

function replaceMenu(menuname,menus){

      var i = mm_returnMenuItemCount(menuname);

      for(;i> 1; i-- )
            mm_deleteItem(menuname,i);

      for( i = 0; i < menus.length; ){
        var str = menus[i];

        i++;
        mm_insertItem(menuname,i,str);
        // delete original first item.
        if( i == 1 ) mm_deleteItem(menuname,1);
      }
}   //end of method

function doNothing() {}

function submitMainUpdate() {
	if (validateForm()) {
		$("userAction").value = 'update';
		parent.showPleaseWait();

		if (beanGrid != null) {
			beanGrid.parentFormOnSubmit();
		}
        parent.$("startSearchTime").value = new Date().getTime();
        /* Submit the form in the result frame */
		document.genericForm.submit();
	}
}

function submitMainUpdateAndReprocess() {
	if (validateForm()) {
		$("userAction").value = 'updateAndReprocess';
		parent.showPleaseWait();

		if (beanGrid != null) {
			beanGrid.parentFormOnSubmit();
		}
        parent.$("startSearchTime").value = new Date().getTime();
        /* Submit the form in the result frame */
		document.genericForm.submit();
	}
}

function validateForm() {
    var result = true;
    var hasSelectedRow = false;
    var errorMsg = "";
    for (var i = 1; i <= beanGrid.getRowsNum(); i++) {
        if (cellValue(i,"rowWithUpdate") == 'true') {
            hasSelectedRow = true;
            var tmpVal = cellValue(i,"catPartNo");
            var tmpMessageTxt = messagesData.forVal+" "+cellValue(i,"prNumber")+"-"+cellValue(i,"lineItem");
            if (tmpVal == null || tmpVal.length == 0) {
                if (errorMsg.length > 0)
                    errorMsg += "\n\t";
                errorMsg += messagesData.partNumber+" "+tmpMessageTxt;
            }
            tmpVal = cellValue(i,"quantity");
            if (tmpVal == null || tmpVal.length == 0) {
                if (errorMsg.length > 0)
                    errorMsg += "\n\t";
                errorMsg += messagesData.quantity+" "+tmpMessageTxt;
            }else {
                if (!isFloat(tmpVal)) {
                    if (errorMsg.length > 0)
                        errorMsg += "\n\t";
                    errorMsg += messagesData.quantity+" "+tmpMessageTxt;
                }
            }
            tmpVal = cellValue(i,"requiredDatetime");
            if (tmpVal == null || tmpVal.length == 0) {
                if (errorMsg.length > 0)
                    errorMsg += "\n\t";
                errorMsg += messagesData.requiredDatetime+" "+tmpMessageTxt;
            }
            tmpVal = cellValue(i,"application");
            if (tmpVal == null || tmpVal.length == 0) {
                if (errorMsg.length > 0)
                    errorMsg += "\n\t";
                errorMsg += messagesData.application+" "+tmpMessageTxt;
            }
            tmpVal = cellValue(i,"allocateByMfgLot");
            if (tmpVal == null || tmpVal.length == 0) {
                if (errorMsg.length > 0)
                    errorMsg += "\n\t";
                errorMsg += messagesData.allocateByMfgLot+" "+tmpMessageTxt;
            }
        }
    }
    if (!hasSelectedRow) {
        result = false;
        alert(messagesData.noRowWasSelected);
    }else {
        if (errorMsg.length > 0) {
            result = false;
            alert(messagesData.validvalues+"\n\t"+errorMsg);
        }
    }

    return result;
}

var dhxFreezeWins = null;
function initializeDhxWins() {
if (dhxFreezeWins == null) {
  dhxFreezeWins = new dhtmlXWindows();
  dhxFreezeWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
 }
}

function showTransitWin(message) {
	if (message != null && message.length > 0) {
		$("transitLabel").innerHTML = message;
	}else {
		$("transitLabel").innerHTML = messagesData.pleasewait;
	}

	var transitDialogWin = document.getElementById("transitDialogWin");
	transitDialogWin.style.display="";

	initializeDhxWins();
	if (!dhxFreezeWins.window("transitDialogWin")) {
		// create window first time
		transitWin = dhxFreezeWins.createWindow("transitDialogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDialogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);
		dhxFreezeWins.window("transitDialogWin").show();
	}
}

function closeTransitWin() {
if (dhxFreezeWins != null) {
 if (dhxFreezeWins.window("transitDialogWin")) {
   dhxFreezeWins.window("transitDialogWin").setModal(false);
   dhxFreezeWins.window("transitDialogWin").hide();
  }
 }
}