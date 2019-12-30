var beanGrid;

function resultOnLoadWithGrid() {
	try{
		if (!showUpdateLinks) {//Dont show any update links if the user does not have permissions
			document.getElementById("updateResultLink").style["display"] = "none";
		} else {
			document.getElementById("updateResultLink").style["display"] = "";
		}
	} catch(ex) {
	}	
	
	initGridWithGlobal(gridConfig);
	
	totalLines = document.getElementById("totalLines").value;

	if (totalLines > 0) {
		document.getElementById(gridConfig.divName).style["display"] = "";
	} else {
		document.getElementById(gridConfig.divName).style["display"] = "none";   
	}
	
	if (showErrorMessage)
		showErrorMessages();

	displayNoSearchSecDuration();
 
	/*It is important to call this after all the divs have been turned on or off.*/
	setResultSize();	
}

function preUpdateAudit() {
	var totalCount = document.getElementById("totalLines").value;
	var hasLimit = false;
	
	for(var i=1; i <= totalCount; i++) {
		if (gridCellValue(beanGrid,i,"status")) {
			//checking mr limit
			if (gridCell(beanGrid,i,"mrLimit") != null) {
				var cellVal = gridCellValue(beanGrid,i,"mrLimit");
				if(cellVal.length > 0) {
					if (isNaN(cellVal)) {
						alert(messagesData.line+" "+i+": "+messagesData.mrLimitWrongFormat);
						return false;
					}
					hasLimit = true;
				}
			}
			//checking ytd limit
			if (gridCell(beanGrid,i,"ytdLimit") != null) {
				var cellVal = gridCellValue(beanGrid,i,"ytdLimit");
				if(cellVal.length > 0) {
					if (isNaN(cellVal)) {
						alert(messagesData.line+" "+i+": "+messagesData.ytdLimitWrongFormat);
						return false;
					}
					hasLimit = true;
				}
			}
			//checking mr limit
			if (gridCell(beanGrid,i,"periodLimit") != null) {
				var cellVal = gridCellValue(beanGrid,i,"periodLimit");
				if(cellVal.length > 0) {
					if (isNaN(cellVal)) {
						alert(messagesData.line+" "+i+": "+messagesData.periodLimitWrongFormat);
						return false;
					}
					if (gridCell(beanGrid,i,"periodDays") != null) {
						cellVal = gridCellValue(beanGrid,i,"periodDays");
						if(cellVal.length > 0) {
							if (isNaN(cellVal)) {
								alert(messagesData.line+" "+i+": "+messagesData.missingPeriodDays);
								return false;
							}
						}else {
							alert(messagesData.line+" "+i+": "+messagesData.missingPeriodDays);
							return false;
						}
					}else {
						alert(messagesData.line+" "+i+": "+messagesData.missingPeriodDays);
						return false;
					}
					hasLimit = true;
				}
			}

			if (!hasLimit) {
				alert(messagesData.line+" "+i+": "+messagesData.setAtLeastOneLimit);
				return false;
			}
		} //end of if active is checked
	} //end of for loop
	return true;
}

function submitUpdate() {
	if (preUpdateAudit()) {
		/*Set any variables you want to send to the server*/
		var action = document.getElementById("action");
		action.value = 'update';
		document.genericForm.target = "";
		showPleaseWait();
		/*Submit the form in the result frame*/
		beanGrid.parentFormOnSubmit();
		document.genericForm.submit();
	}
}

function createXSL () {
	document.getElementById("action").value='createXSL';
	document.genericForm.target='_edit_list_excel_report_file';
	openWinGenericExcel('/tcmIS/common/loadingfile.jsp','_edit_list_excel_report_file','650','600','yes');
	document.genericForm.submit();
}
