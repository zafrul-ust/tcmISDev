windowCloseOnEsc = true;

function resultOnLoadWithGrid(gridConfig) {
    if ($v("userAction") == 'submitSubcategory' && !showErrorMessage && $v("submitAndClose") == 'Y') {
		window.close();
	}else {
	    if (showErrorMessage) {
            alert($v("errorMsg"));
        }
        totalLines = document.getElementById("totalLines").value;
        if (totalLines > 0) {
            document.getElementById("cylinderSubcategoryDiv").style["display"] = "";
            initGridWithGlobal(gridConfig);
        }else {
            document.getElementById("cylinderSubcategoryDiv").style["display"] = "none";
        }

        /*This dislpays our standard footer message*/
        displayNoSearchSecDuration();
        /*It is important to call this after all the divs have been turned on or off.*/
        setResultSize();
        //preselect first row
        beanGrid.setselect
        beanGrid.setSelectedRow(1);
    }
}  //end of method

function submitUpdate() {
	if (validateForm()) {
	    $("submitAndClose").value = 'N';
		$("userAction").value = 'submitSubcategory';
		beanGrid.parentFormOnSubmit();
        document.genericForm.submit();
	}
}

function submitUpdateAndClose() {
	if (validateForm()) {
	    $("submitAndClose").value = 'Y';
		$("userAction").value = 'submitSubcategory';
		beanGrid.parentFormOnSubmit();
        document.genericForm.submit();
	}
}

function validateForm() {
    var noRowSelected = true;
    var errorMsg = "";
    for (var i = 1; i <= beanGrid.getRowsNum(); i++) {
        var tmpVal = cellValue(i,"selectedRow");
        if(tmpVal == 'true') {
            noRowSelected = false;
            tmpVal = cellValue(i,"dateServiced");
            if (tmpVal == null || tmpVal.length == 0) {
                if (errorMsg.length > 0)
                    errorMsg += "\n";
                errorMsg += messagesData.dateServiced+' '+messagesData.is+' '+messagesData.missing+' '+messagesData.forX+' '+cellValue(i,"refurbSubcategory");
            }
        }
    }

    if (noRowSelected) {
        alert(messagesData.noRowSelected);
        return false;
    }else {
        if (errorMsg.length > 0) {
            alert(errorMsg);
            return false;
        }
    }
    return true;
}

function cancel() {
	window.close();
}

function updateDateServiced() {
    if (beanGrid.getSelectedRowId() != null && beanGrid.getSelectedRowId() > 0) {
        if(cellValue(beanGrid.getSelectedRowId(),"selectedRow") == 'true') {
            if (cellValue(beanGrid.getSelectedRowId(),"dateServiced") == '')
                beanGrid.cells(beanGrid.getSelectedRowId(),beanGrid.getColIndexById("dateServiced")).setValue($v("todayDate"));
        }else
             beanGrid.cells(beanGrid.getSelectedRowId(),beanGrid.getColIndexById("dateServiced")).setValue("");
    }
}


