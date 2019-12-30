var beanGrid = null;
var selectedRowId = null;

var resizeGridWithWindow = true;

function resultOnLoad() {
	//Dont show any update links if the user does not have permissions
	if (!showUpdateLinks)  {
		parent.document.getElementById("updateResultLink").style["display"] = "none";
	}
	else {
		parent.document.getElementById("updateResultLink").style["display"] = "";
	}
	
	totalLines = document.getElementById("totalLines").value;
    if (totalLines > 0) {
        document.getElementById("vocetMsdsBean").style["display"] = "";
       try {
	        initGridWithGlobal(gridConfig);
       }
       catch(ex) {}
         
    }else {
        document.getElementById("vocetMsdsBean").style["display"] = "none";
    }

	displayGridSearchDuration();
	/*It is important to call this after all the divs have been turned on or off.*/
 	setResultFrameSize();
}

function selectRow(rowId,cellInd) {
	selectedRowId = rowId;
}

function selectRightclick(rowId,cellInd) {
//  beanGrid.selectRowById(rowId,null,false,false);
  
  selectRow(rowId,cellInd);
 /* 
  if("TCMIS" == cellValue(rowId,"status")) {
  	toggleContextMenu('emptyMenu');
  	return false;
  }
  
  var columnId = beanGrid.getColumnId(cellInd);
  
  vitems = new Array();
  
  vitems[vitems.length ] = "text="+messagesData.deletepart+";url=javascript:deletePart('deleteByPart')";
  
  if("deleteByPart" != cellValue(rowId, "status") && cellInd > beanGrid.getColIndexById("partNumber"))
  	vitems[vitems.length ] = "text="+messagesData.addmsds+";url=javascript:addMSDS()";
  
  if(cellInd > beanGrid.getColIndexById("partNumber")) 
  	vitems[vitems.length ] = "text="+messagesData.deletemsds+";url=javascript:deletePart('deleteByMsds')";
 
  replaceMenu('rightClickMenu',vitems);  
  toggleContextMenu('rightClickMenu');
*/
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
}

function setChanged(rowId,cellInd) {
	beanGrid.cellById(rowId, beanGrid.getColIndexById("changed")).setValue("Y");
}

function updateVocetMsds() {
//	if (validationforUpdate()) {
		document.genericForm.target = ''; 
		$('uAction').value = 'update';		
		parent.showPleaseWait(); 
		
		// Reset search time for update
		var now = new Date();
		var startSearchTime = parent.document.getElementById("startSearchTime");
		startSearchTime.value = now.getTime();	

		if (beanGrid != null) {
			// This function prepares the grid dat for submitting top the server
			beanGrid.parentFormOnSubmit();
		}

		document.genericForm.submit(); // Submit the form
//	}  
}

// validate the whole grid
function validationforUpdate() {
	var rowsNum = beanGrid.getRowsNum();

	// This reflects the rowId we put in the JSON data 
	for ( var rowId = 1; rowId <= rowsNum; rowId++) {
		if (!validateLine(rowId)) {
			//Select the failing line
			beanGrid.selectRowById(rowId, null, false, false); 
			return false;
		}
	}

	return true;
}

// validate one line here
function validateLine(rowId,cellInd) {
	var errorMessage = messagesData.validvalues+"\n\n";
	var errorCount = 0;
/*
	if("TCMIS" == cellValue(rowId,"status") || "deleteByMsds" == cellValue(rowId,"status") || "deleteByPart" == cellValue(rowId,"status")) return true;
	
	var partNumber = cellValue(rowId, "partNumber");
	if (partNumber.length == 0) { 
		errorMessage = errorMessage +"\n"+ messagesData.partno;
		errorCount = 1;
	}
	
	var customerMsdsOrMixtureNo = cellValue(rowId, "customerMsdsOrMixtureNo");
	if (customerMsdsOrMixtureNo.length == 0) { 
		errorMessage = errorMessage +"\n"+ messagesData.msds;
		errorCount = 1;
	}

	var poundsPerUnit = cellValue(rowId, "poundsPerUnit");
	if (!isFloat(poundsPerUnit, false)) { 
		errorMessage = errorMessage +"\n"+ messagesData.lbsuom;
		errorCount = 1;
	}
	
	if (errorCount > 0)
 	{
	     alert(errorMessage);
		 return false;
	}
*/
	return true;
}

