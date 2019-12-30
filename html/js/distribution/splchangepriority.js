//var children = new Array();
windowCloseOnEsc = true;

function resultOnLoadWithGrid(gridConfig)
{
 totalLines = document.getElementById("totalLines").value; 
 if (totalLines > 0)
 {
  document.getElementById("mainUpdateLinks").style["display"] = ""; 
  /*this is the new part.*/
  initGridWithGlobal(gridConfig); 
 }
 else
 {
   document.getElementById("mainUpdateLinks").style["display"] = "none";   
 }

 /*This dislpays our standard footer message*/
 displayNoSearchSecDuration();

 /*It is important to call this after all the divs have been turned on or off.*/
 setResultSize();
}

function submitUpdate() {
	if (validationforUpdate()) {
		document.genericForm.target = '';
		document.getElementById('uAction').value = 'update';

		showPleaseWait(); // Show "please wait" 

		if (beanGrid != null)
			beanGrid.parentFormOnSubmit(); // Got to call this
							// function to send the
							// data from grid back
							// to the server

		document.genericForm.submit(); // back to server
	}
	else 
		return false;
}

var priorityArr = new Array(); 
function validationforUpdate(){
	priorityArr = new Array(); 
	var rowNum = beanGrid.getRowsNum();	
	var flag = true;
	for (var i=0; i<rowNum; i++)
	{
		rowId = beanGrid.getRowId(i);
		if (contains(cellValue(rowId,'priority'))) {
			alert(messagesData.allprioritiesunique);
			flag =  false;
		}
	}
	
	return flag;
}

function contains(priority) {
	if (priorityArr.length == 0) {
		priorityArr[priorityArr.length] = priority;
	}
	else {	
		var flag = false;
		for (j=0;j<priorityArr.length;j++){
			if (priorityArr[j]== priority) 
				flag =  true;
		}
		
		if (flag)
			return true;
		else
			priorityArr[priorityArr.length] = priority;
	}
	return false;
}

function selectRow(rowId,cellInd){
/* 
    rowId0 = arguments[0];
    colId0 = arguments[1];

    beanGrid.selectRowById(rowId0);
    
 */
}

