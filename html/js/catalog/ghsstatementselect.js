windowCloseOnEsc = true;
var dhxWins = null;
var selectedCode = null;
var selectedStatement = null;
var orderCount = 1;
var selectionCt = 0;

function resultOnLoadWithGrid(gridConfig)
{
 totalLines = document.getElementById("totalLines").value; 
 if (totalLines > 0)
 {
  document.getElementById("statementBean").style["display"] = ""; 
  /*this is the new part.*/
  initGridWithGlobal(gridConfig); 
 }
 else
 {
   document.getElementById("statementBean").style["display"] = "none";   
 }

 /*This dislpays our standard footer message*/
 displayNoSearchSecDuration();

 /*It is important to call this after all the divs have been turned on or off.*/
 setResultSize();
 
 /*
 var fieldArray=$v("statementString").split("|");
 var rowsNum = beanGrid.getRowsNum();
 for ( var i=0; i < fieldArray.length; i++) {
  	for ( var rowId = 1; rowId <= rowsNum; rowId++) {
		if (fieldArray[i] == cellValue(rowId, "id")) { 
			var curCheckBox = 'ok' + rowId;
			$(curCheckBox).checked = true;
			updateHchStatusA(curCheckBox);
			beanGrid.cells(rowId, beanGrid.getColIndexById("order")).setValue(orderCount++);
			break;
		}
  	}
 }*/
 
}

function selectRow(rowId,cellInd){
 
   rowId0 = arguments[0];
   colId0 = arguments[1];
    
   $("selectedFieldsSpan").innerHTML = '<a href="#" onclick="selectedFields(); return false;">' + messagesData.selectedfields + '</a> | ';
	  
   selectedCode = cellValue(rowId,'code');
   selectedStatement = cellValue(rowId,'statement');
  
}

function selectedFields() {
    var selected = new Array();
	var j = 0;
	
	for(var i = 1; i <= beanGrid.getRowsNum(); i++) {
		if(document.getElementById('ok'+i).checked == true) {
            selected[j++]= {
            				id:cellValue(i, 'ghsStatementId'),
                            code:cellValue(i,'code'),
                            statement:((cellValue(i,'isFromMsds')==0)?" (MAXCOM Estimate) ":"")+cellValue(i,'statement')};
        }
	}
	// if there are none selected, we still need to send a code so that window.opener 
	// knows whether the hazard statements or precautionary statements are being modified
	if (selected.length == 0) {
		// keep this comented in case we ever want to prevent returning no statements
	//	if ($v("ghsCompliant") == "true" && $v("ghsHazard") == "true") {
	//		return false;
	//	}
	//	else {
			selected[0] = {
					id:null,
					code:cellValue(1, 'code'),
					statement:null
			};
	//	}
	}
	document.genericForm.target = '_self';
	$('uAction').value = "updateStatements";
  	window.opener.populateStatementField(selected);
	beanGrid.parentFormOnSubmit();
	document.genericForm.submit();
}

function msdsCallback(msdsId) {
	window.opener.stopShowingWait();
	window.opener.getMSDSfield("msdsId").value = msdsId;
	window.close();
}

// removed because the Non-hazardous checkbox is deemed sufficient and this functionality is obsolete
/*function submitNotRequired() {
	document.getElementById("statementNotRequired").value = "true";
	$('uAction').value = "updateStatements";

	var selected = [{
		id:document.getElementById("statementNotRequiredId"),
		code:document.getElementById("codeAbbrev").value+" NR" + document.getElementById("codeAbbrev"),
		statement:"Not required"
	}];
	window.opener.populateStatementField(selected);
	document.genericForm.submit();
}*/

function cancel() {
	window.opener.stopShowingWait();
	window.close();
}

var selectFromRequiredList = new Array();


function checkUserSelected() {
	var selectedId = "";
	var userselected = "";
	var rowsNum = beanGrid.getRowsNum();
	for ( var i=0; i < selectFromRequiredList.length; i++) {
		for ( var rowId = 1; rowId <= rowsNum; rowId++) {
			selectedId = $v("selectid"+i);
			userselected = $("selectreq"+i);
			if (selectedId == cellValue(rowId, "baseFieldId") && userselected.checked) {
				var curCheckBox = 'ok' + rowId;
				$(curCheckBox).checked = true;
				updateHchStatusA(curCheckBox);
				break;
			}
			else if (selectedId == cellValue(rowId, "baseFieldId") && 0 == cellValue(rowId, "isFromMsds")) {
				var override = confirm("???Override???");
				if (override) {
					var curCheckBox = 'ok' + rowId;
					$(curCheckBox).checked = true;
					updateHchStatusA(curCheckBox);
					beanGrid.cells(rowId, beanGrid.getColIndexById("isFromMsds")).setValue(1);
					beanGrid.haasSetColSpanStyle(rowId, 0, 3, "background-color: lightGray;");
					break;
				}
			}
	  	}
	}
}

function selectStmt(rowId) {
	if (0 == cellValue(rowId, "isFromMsds")) {
		var curCheckBox = 'ok' + rowId;
		var override = confirm(messagesData.maxcomestimatestmtalert);
		if (override) {
			$(curCheckBox).checked = false;
			selectionCt--;
			beanGrid.cells(rowId, beanGrid.getColIndexById("isFromMsds")).setValue(1);
		}
		else {
			$(curCheckBox).checked = true;
		}
	}
	else {
		if ($("ok"+rowId).checked) {
			selectionCt++;
		}
		else {
			selectionCt--;
		}
	}

	// saving this in case we decide to go back to preventing removal of all statements
	//if (selectionCt > 0) {
		$("selectedFieldsSpan").innerHTML = '<a href="#" onclick="selectedFields(); return false;">' + messagesData.selectedfields + '</a> | ';
	//}
	//else {
	//	$("selectedFieldsSpan").innerHTML = "";
	//}
}


function setOrder(rowId) {
	beanGrid.cells(rowId, beanGrid.getColIndexById("order")).setValue(orderCount++);
}

function clearAllCheckedBox() {

	var rowsNum = beanGrid.getRowsNum();
	var columnId = beanGrid.getColIndexById('ok');
	
	var maxcomEstimates = false;
	for ( var rowId = 1; rowId <= rowsNum; rowId++) {
		var curCheckBox = 'ok' + rowId;
		if (cellValue(rowId,"isFromMsds") == 0) {
			maxcomEstimates = true;
		}
		else {
			$(curCheckBox).checked = false;
			updateHchStatusA(curCheckBox);
			beanGrid.cells(rowId, beanGrid.getColIndexById("order")).setValue("");
			orderCount = 1;
		}
	}
	
	if (maxcomEstimates) {
		alert(messagesData.maxcomclearstmtalert);
	}
	
	// saving this in case we decide to go back to preventing removal of all statements
	//if ($v("ghsCompliant") == "false" || $v("ghsHazard") == "false") {
		$("selectedFieldsSpan").innerHTML = '<a href="#" onclick="selectedFields(); return false;">' + messagesData.selectedfields + '</a> | ';
	//}
	//else {
	//	$("selectedFieldsSpan").innerHTML = "";
	//}
}

function setRowStatusColors(rowId) {
	if (rowId) {
		var msdsId = beanGrid.cells(rowId,beanGrid.getColIndexById("msdsId")).getValue();
		// query uses an outer join; other statements may have msds_id associated with them in the DB, 
		// but the join is only on those where the statements match up with the msds_id of this material and revision
		// hence, the only rows with msds_id != "" are those that should be checked
		if (beanGrid.cells(rowId,beanGrid.getColIndexById("msdsId")).getValue() != "") {
			var curCheckBox = 'ok' + rowId;
			$(curCheckBox).checked = true;
			selectionCt++;
		}
	    var onSds = beanGrid.cells(rowId,beanGrid.getColIndexById("isFromMsds")).getValue();
	    if (onSds == 0) {
	    	beanGrid.haasSetColSpanStyle(rowId, 0, 3, "background-color: darkGray;");
	    }
	}
}

function showLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows()
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 64);
  legendWin.setText(messagesData.showlegend);
  legendWin.clearIcon();  // hide icon
  legendWin.denyResize(); // deny resizing
  legendWin.denyPark();   // deny parking
  legendWin.attachObject("showLegendArea");
  legendWin.attachEvent("onClose", function(legendWin){legendWin.hide();});
  legendWin.center();
  }
  else
  {
    // just show
    dhxWins.window("legendwin").show();
  }
}
