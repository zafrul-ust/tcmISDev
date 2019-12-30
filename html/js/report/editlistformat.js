windowCloseOnEsc = true;

var selectedId = null;
var selectedDescription = null;
var orderCount = 1;

function resultOnLoadWithGrid(gridConfig)
{
	 totalLines = document.getElementById("totalLines").value; 
	 if (totalLines > 0) {
		  document.getElementById("listFormatBean").style["display"] = ""; 
		  /*this is the new part.*/
		  initGridWithGlobal(gridConfig); 
	 } else {
	   document.getElementById("listFormatBean").style["display"] = "none";   
	 }
	
	 /*This dislpays our standard footer message*/
	 displayNoSearchSecDuration();
	
	 /*It is important to call this after all the divs have been turned on or off.*/
	 setResultSize();
	 var lastSelectedRow = '';
	 var fieldArray=$v("baseListFormatIdString").split("|");	 
	 var rowsNum = beanGrid.getRowsNum();	 
	 for ( var i=0; i < fieldArray.length; i++) {		 
	  	for ( var rowId = 1; rowId <= rowsNum; rowId++) {
	  		//alert((fieldArray[i].trim() == cellValue(rowId, "Id")) + " and '" + fieldArray[i].trim() +"' and '"+ cellValue(rowId, "Id") +"'");
			if (fieldArray[i].trim() == cellValue(rowId, "Id")) {				
				var curCheckBox = 'ok' + rowId;				
				$(curCheckBox).checked = true;
				updateHchStatusA(curCheckBox);
				beanGrid.cells(rowId, beanGrid.getColIndexById("order")).setValue(orderCount++);
				lastSelectedRow = rowId;
				break;
			}
	  	}
	 }
	 if (lastSelectedRow != '') {
		 selectRow(lastSelectedRow,2);
		 beanGrid.selectRowById(lastSelectedRow,null,false,false);
	 }
}

function selectRow(rowId,cellInd){
 
   rowId0 = arguments[0];
   colId0 = arguments[1];    
   $("selectedFieldsSpan").innerHTML = '<a href="#" onclick="selectedFields(); return false;">' + messagesData.selectedfields + '</a> | ';	  
   selectedId = cellValue(rowId,'Id');
   selectedDescription = cellValue(rowId,'Description');  
}

function selectedFields() {
    var selected = new Array();
	var j = 0;
	var oneSelected = false;
	for(var i = 1; i <= beanGrid.getRowsNum(); i++) {
		if(document.getElementById('ok'+i).checked == true) {
            selected[j++]= {
                            formatId:cellValue(i,'Id'),
                            formatDesc:cellValue(i,'Description')};
            oneSelected = true;
        }
	}
    if(!oneSelected) {
  		alert(messagesData.norowselected);
  		return false;
  	}    
  	window.opener.populateListFormatField(selected, $v("selectedListId"));
  	window.close();
}

function setOrder(rowId) {
	beanGrid.cells(rowId, beanGrid.getColIndexById("order")).setValue(orderCount++);
	var okvalue= cellValue(rowId,'ok'); 
	if (cellValue(rowId,'Id') == 'No Display') {
		if(okvalue == 'true') {
			clearAllExceptNoDisplayCheck(rowId);			
		} 
	} else {
		clearNoDisplayCheck();
	}
}

function clearAllCheckedBox() {
	var rowsNum = beanGrid.getRowsNum();
	var columnId = beanGrid.getColIndexById('ok');	
	for ( var rowId = 1; rowId <= rowsNum; rowId++) {
		var curCheckBox = 'ok' + rowId;
			$(curCheckBox).checked = false;
			updateHchStatusA(curCheckBox);
			beanGrid.cells(rowId, beanGrid.getColIndexById("order")).setValue("");
			orderCount = 1;
	}
}

function clearAllExceptNoDisplayCheck(rowId) {
	var rowsNum = beanGrid.getRowsNum();
	var columnId = beanGrid.getColIndexById('ok');	
	for ( var iCheck = 1; iCheck <= rowsNum; iCheck++) {
		if(rowId != iCheck) {
			var curCheckBox = 'ok' + iCheck;
			$(curCheckBox).checked = false;
			updateHchStatusA(curCheckBox);		
		}
	}
}

function clearNoDisplayCheck() {
	var rowsNum = beanGrid.getRowsNum();
	var columnId = beanGrid.getColIndexById('ok');	
	for ( var iCheck = 1; iCheck <= rowsNum; iCheck++) {
		if (cellValue(iCheck,'Id') == 'No Display') {
			var curCheckBox = 'ok' + iCheck;
			$(curCheckBox).checked = false;
			updateHchStatusA(curCheckBox);		
		}
	}
}