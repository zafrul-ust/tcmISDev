windowCloseOnEsc = true;
var dhxWins = null;
var selectedName = null;
var selectedDescription = null;
var orderCount = 1;

function resultOnLoadWithGrid(gridConfig)
{
 totalLines = document.getElementById("totalLines").value; 
 if (totalLines > 0)
 {
  document.getElementById("reportBaseFieldBean").style["display"] = ""; 
  /*this is the new part.*/
  initGridWithGlobal(gridConfig); 
 }
 else
 {
   document.getElementById("reportBaseFieldBean").style["display"] = "none";   
 }

 /*This dislpays our standard footer message*/
 displayNoSearchSecDuration();

 /*It is important to call this after all the divs have been turned on or off.*/
 setResultSize();
 
 var fieldArray=$v("baseFieldIdString").split("|");
 var rowsNum = beanGrid.getRowsNum();
 for ( var i=0; i < fieldArray.length; i++) {
  	for ( var rowId = 1; rowId <= rowsNum; rowId++) {
		if (fieldArray[i] == cellValue(rowId, "baseFieldId")) { 
			var curCheckBox = 'ok' + rowId;
			$(curCheckBox).checked = true;
			updateHchStatusA(curCheckBox);
			beanGrid.cells(rowId, beanGrid.getColIndexById("order")).setValue(orderCount++);
			break;
		}
  	}
 }
 
}

function selectRow(rowId,cellInd){
 
   rowId0 = arguments[0];
   colId0 = arguments[1];
    
   $("selectedFieldsSpan").innerHTML = '<a href="#" onclick="selectedFields(); return false;">' + messagesData.selectedfields + '</a> | ';
	  
   selectedName = cellValue(rowId,'name');
   selectedDescription = cellValue(rowId,'description');
  
}

function selectedFields() {
    var selected = new Array();
	var j = 0;
	var oneSelected = false;
	if (!checkChemicalRequiredSelected()) 
		return false;
	
	for(var i = 1; i <= beanGrid.getRowsNum(); i++) {
		if(document.getElementById('ok'+i).checked == true) {
            selected[j++]= {
                            baseFieldName:cellValue(i,'name'),
                            description:cellValue(i,'description'),
                            baseFieldId:cellValue(i,'baseFieldId')};
            oneSelected = true;
        }
	}
	
    if(!oneSelected) {
  		alert(messagesData.norowselected);
  		return false;
  	}
  	window.opener.populateReportFieldGrid(selected);
  	window.close();
}

var selectFromRequiredList = new Array();

function checkChemicalRequiredSelected() {
	var oneParentSelected = false;
	var oneChildSelected = false;
	for(var i = 1; i <= beanGrid.getRowsNum(); i++) {
		if(document.getElementById('ok'+i).checked == true && 
				cellValue(i,'chemicalIdRequired') == 'Y' ) {
			oneParentSelected = true;
			break;
        }
	}
	
	if (oneParentSelected) {
		for(var i = 1; i <= beanGrid.getRowsNum(); i++) {
			if(document.getElementById('ok'+i).checked == true && 
					cellValue(i,'chemicalId') == 'Y' ) {
				oneChildSelected = true;
				break;
	        }
		}		
		
		if (!oneChildSelected) {
			var j = 0;
			//alert(messagesData.basefieldselectmsg);
			for(var i = 1; i <= beanGrid.getRowsNum(); i++) {
				var rowId = beanGrid.getRowId(i);
				if(cellValue(i,'chemicalId') == 'Y' ) {		
					selectFromRequiredList[j++]= {
                            baseFieldName:cellValue(i,'name'),
                            description:cellValue(i,'description'),
                            baseFieldId:cellValue(i,'baseFieldId')};
					setRowClass(i,'grid_red'); /// check if required ?????
		        }
			}
			if (selectFromRequiredList.length > 0)
				popupRequiredFields(selectFromRequiredList);			
			return false;
		}
	}
	return true;
}

function popupRequiredFields(selectFromRequiredList) {
	var strTable = "</br>";
	strTable += '<div class="optionTitleBoldLeft" style="color:red">'+messagesData.basefieldselectmsg+'</div>';
	strTable += '</br></br>';
	strTable += '<table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0" >';	
	strTable += '<tr><th>Select</th>';
	strTable += '<th>Base Fields</th>';
	strTable += '<th>Description</th></tr>';	
	for ( var i=0; i < selectFromRequiredList.length; i++) { 
		strTable += '<tr><td class="optionTitleBoldRight" width="8px">';
		strTable += '<input type="checkbox" id="selectreq'+i+'" name="selectreq'+i+'" ><input type="hidden" id="selectid'+i+'" name="selectid'+i+'" value='+selectFromRequiredList[i].baseFieldId+'>';
		strTable +=	'</td><td class="optionTitleBoldLeft" >';
		strTable += selectFromRequiredList[i].baseFieldName;		
		strTable +=	'</td><td class="optionTitleBoldLeft" >';
		strTable += selectFromRequiredList[i].description;
		strTable += '</td></tr>';
	}
	strTable += '</table>';
	strTable += '</br></br>';
	strTable += '<table width=100% class="tableSearch" border="0" cellpadding="0" cellspacing="0">';	
	strTable += '<tr><td colspan="3" align="center">';
	strTable += '<button class="smallBtns" id="btnok" name="btnok" onclick="checkUserSelected();dhxWins.window(messagesData.selectrequiredfields).hide();" value="">OK</button>&nbsp;&nbsp;';
	strTable += '<button class="smallBtns" id="btncancel" name="btncancel" onclick="dhxWins.window(messagesData.selectrequiredfields).hide();" value="">Cancel</button>';
	strTable += '</td></tr>';
	strTable += '</table>';
	document.getElementById("showRequiredFieldsArea").innerHTML = strTable;
	showRequiredFields();
}


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
	  	}
	}
}


function showRequiredFields()
{
  var showRequiredFieldsArea = document.getElementById("showRequiredFieldsArea");
  showRequiredFieldsArea.style.display="";

  dhxWins = new dhtmlXWindows();
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.selectrequiredfields)) {
	  var requiredFieldsWin = dhxWins.createWindow(messagesData.selectrequiredfields, 0, 0, 500, 350);
	  requiredFieldsWin.setText(messagesData.selectrequiredfields);
	  requiredFieldsWin.clearIcon();  // hide icon
	  requiredFieldsWin.denyResize(); // deny resizing
	  requiredFieldsWin.denyPark();   // deny parking
	  requiredFieldsWin.attachObject("showRequiredFieldsArea");
	  requiredFieldsWin.attachEvent("onClose", function(requiredFieldsWin){checkUserSelected();requiredFieldsWin.hide();});
	  requiredFieldsWin.center();
  } else {
	  dhxWins.window("requiredFieldsWin").show();
  }
}

/*
function selectedFields() 
{ 
	//beanGrid.sortRows(4,"int","asc");
	var fieldsString = '';
	var rowsNum = beanGrid.getRowsNum();
  	for ( var i = 0; i < rowsNum; i++) {
  		rowId = beanGrid.getRowId(i);
		var curCheckBox = 'ok' + rowId;
		
		if ($(curCheckBox) && $(curCheckBox).checked == true) { // Make sure the row has been rendered and the element exists
			if(fieldsString == '') 
				fieldsString = cellValue(rowId, "baseFieldId") + ':' + cellValue(rowId, "name");
			else
				fieldsString += "|" + cellValue(rowId, "baseFieldId") + ':' + cellValue(rowId, "name");
		}
  	}
	
  	if(fieldsString.length == 0) {
  		alert(messagesData.norowselected);
  		return false;
  	}
  	
  	window.opener.setFields(fieldsString);
  	window.close();
}*/

function setOrder(rowId) {
	beanGrid.cells(rowId, beanGrid.getColIndexById("order")).setValue(orderCount++);
}

function clearAllCheckedBox() {

	var rowsNum = beanGrid.getRowsNum();
	var columnId = beanGrid.getColIndexById('ok');
	
	for ( var rowId = 1; rowId <= rowsNum; rowId++) {
		var curCheckBox = 'ok' + rowId;
//		if ($(curCheckBox)) {  // Make sure the row has been rendered and the element exists
			$(curCheckBox).checked = false;
			updateHchStatusA(curCheckBox);
			beanGrid.cells(rowId, beanGrid.getColIndexById("order")).setValue("");
			orderCount = 1;
/*		}
		else { // The HTML element hasn't been drawn yet, update the JSON data directly
			// Basically we don't need this here because the smart rendering is disable
			beanGrid._haas_json_data.rows[beanGrid.getRowIndex(rowId)].data[columnId] = false;
		}   */
	}
}

