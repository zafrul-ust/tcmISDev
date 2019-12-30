var mygrid;

var selectedRowId = null;
var selectedRowInd = null;

// Set this to be false if you don't want the grid width to resize based on window size.
var resizeGridWithWindow = true;

// This works only for popup windows and IE. Close the window after clicking Esc key
var windowCloseOnEsc = true; 

function resultOnLoad()
{
  if ($v("totalLinesNum") == 0)
  	parent.$("updateSpan").style["display"] = "none";
  else 
    parent.$("updateSpan").style["display"] = "";
	
  document.getElementById("chargeNumberBean").style["display"] = "";
  initializeGrid();  

  displaySearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
 
 try{
	 if (!showUpdateLinks) //Dont show any update links if the user does not have permissions
	 {
	  parent.document.getElementById("mainUpdateLinks").style["display"] = "none";
	 }
	 else 
	 {
	  parent.document.getElementById("mainUpdateLinks").style["display"] = "";
	  parent.document.getElementById("deleteRowSpan").style["display"] = "none";
	 }
  }
  catch(ex){}	
  
}

function displaySearchDuration() {
   var totalLines = document.getElementById("totalLinesNum");
   if (totalLines.value != null) {
     if (totalLines.value*1 > 0) {
       var startSearchTime = parent.window.document.getElementById("startSearchTime");
       var now = new Date();
       var minutes = 0;
       var seconds = 0;
       //the duration is in milliseconds
       var searchDuration = now.getTime()-(startSearchTime.value*1);
       if (searchDuration > (1000*60)) {   //calculating minutes
         minutes = Math.round((searchDuration / (1000*60)));
         var remainder = searchDuration % (1000*60);
         if (remainder > 0) {   //calculating seconds
           seconds = Math.round(remainder / 1000);
         }
       }else {  //calculating seconds
         seconds = Math.round(searchDuration / 1000);
       }
       var footer = parent.document.getElementById("footer");
       if (minutes != 0) {
         footer.innerHTML = messagesData.recordFound+": "+totalLines.value+" -- "+messagesData.searchDuration+": "+minutes+" "+messagesData.minutes+" "+seconds+" "+messagesData.seconds;
       }else {
         footer.innerHTML = messagesData.recordFound+": "+totalLines.value+" -- "+messagesData.searchDuration+": "+seconds+" "+messagesData.seconds;
       }
     }else {
       var footer = parent.document.getElementById("footer");
       footer.innerHTML ="";
     }
   }else {
     var footer = parent.document.getElementById("footer");
     footer.innerHTML ="&nbsp;";
   }
}

function initializeGrid(){
	mygrid = new dhtmlXGridObject('chargeNumberBean');
	
	mygrid.enableSmartRendering(false);
	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	initGridWithConfig(mygrid,config,false,true);
	if( typeof( jsonMainData ) != 'undefined' ) {		
		mygrid.parse(jsonMainData,"json");
	}
	
	mygrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	mygrid.attachEvent("onRowSelect",doOnRowSelected);
	mygrid.attachEvent("onRightClick",selectRightclick);
	// mygrid.attachEvent("onAfterHaasRenderRow", setInactiveRowColor);
}

var saveRowClass = null;
function doOnBeforeSelect(rowId,oldRowId) {

	if (oldRowId != 0) {
		setRowClass(oldRowId, saveRowClass);		
	}

	saveRowClass = getRowClass(rowId);
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/i) == -1)
		overrideDefaultSelect(rowId,saveRowClass);
   		
	return true;	
}

function doOnRowSelected(rowId,cellInd) {
	selectedRowId = rowId;
	selectedRowInd = cellInd;
	
	var columnId = mygrid.getColumnId(cellInd);

	if(columnId == "status" && $("status"+rowId).checked == true && statusChange == 'Y') {
		statusChange = 'N';
		if ( rowId % 2 == 0) {
			setRowClass(rowId, 'grid_lightblue');
		}else {
			setRowClass(rowId, 'grid_white');
		}
		saveRowClass = getRowClass(rowId);
		
		if(cellValue(rowId, "originalStatus") != 'A')
			mygrid.cells(rowId,mygrid.getColIndexById("changed")).setValue("Y");
			
	}
	else if(columnId == "status" && $("status"+rowId).checked == false && statusChange == 'Y') {
		statusChange = 'N';
		if(cellValue(rowId, "changed") == 'New' && !validateLine(rowId)) {
			mygrid.cells(rowId,mygrid.getColIndexById("status")).setValue(true);
			$("status"+rowId).checked = true;
			updateHchStatusA("status"+rowId);
			if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		  		setRowClass(rowId,''+saveRowClass+'Selected'); 
			return false;
		}
		var chargeNumbers = "";
	    try {
			if (typeof( cellValue(rowId, "chargeNumber1") ) != 'undefined' && cellValue(rowId, "chargeNumber1").trim().length > 0) 
				chargeNumbers = "&chargeNumber"+cellValue(rowId, "chargeNo1Alias")+"="+cellValue(rowId, "chargeNumber1");
			if (typeof( cellValue(rowId, "chargeNumber2") ) != 'undefined' && cellValue(rowId, "chargeNumber2").trim().length > 0) 
				chargeNumbers += "&chargeNumber"+cellValue(rowId, "chargeNo2Alias")+"="+cellValue(rowId, "chargeNumber2");
			if (typeof( cellValue(rowId, "chargeNumber3") ) != 'undefined' && cellValue(rowId, "chargeNumber3").trim().length > 0) 
				chargeNumbers += "&chargeNumber"+cellValue(rowId, "chargeNo3Alias")+"="+cellValue(rowId, "chargeNumber3");
			if (typeof( cellValue(rowId, "chargeNumber4") ) != 'undefined' && cellValue(rowId, "chargeNumber4").trim().length > 0) 
				chargeNumbers += "&chargeNumber"+cellValue(rowId, "chargeNo4Alias")+"="+cellValue(rowId, "chargeNumber4");
	    }
	    catch (ex) {}
		
		callToServer("chargenumbersetupresults.do?uAction=getUseCount&facilityId="+$v("facilityId")+"&accountSysId="+$v("accountSysId")+"&chargeType="+$v("chargeType")+chargeNumbers);
    }

	if( getNewRowColor == 'Y' ) {
		if (totalRows % 2 == 1) {
			setRowClass(rowId, 'grid_lightblue');
		}else {
			setRowClass(rowId, 'grid_white');
		}
		saveRowClass = getRowClass(rowId);
		getNewRowColor = 'N'; 	
	}

	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		  setRowClass(rowId,''+saveRowClass+'Selected'); 
	  
	if(cellValue(rowId, "changed") == 'New')
		parent.$("deleteRowSpan").style["display"] = "";
	else
		parent.$("deleteRowSpan").style["display"] = "none"; 
	
}

function alertInUseOrNot(count) {
  if (count > 0*1)  {
     var answer = confirm (messagesData.chargenumberinuse1+"\n"+messagesData.chargenumberinuse2);
     if (!answer) {
       if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		  setRowClass(selectedRowId,''+saveRowClass+'Selected'); 
		
	   mygrid.cells(selectedRowId,mygrid.getColIndexById("status")).setValue(true);	  
	   $("status"+selectedRowId).checked == true;
	   updateHchStatusA("status"+selectedRowId);
		  
       return false;
     }  
  }
  
  setRowClass(selectedRowId, 'grid_black');
  saveRowClass = getRowClass(selectedRowId);

  if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
	setRowClass(selectedRowId,''+saveRowClass+'Selected'); 

}

function selectRightclick(rowId,cellInd){
	doOnRowSelected(rowId,cellInd);
	if(cellValue(rowId, "changed") == 'New')
		toggleContextMenu('rightClickMenu');
	else
		toggleContextMenu('contextMenu');
}

var statusChange = 'N';
function changedOrNot(rowId,cellInd) {

	var columnId = mygrid.getColumnId(mygrid.getColIndexById(cellInd));
	var originalValueColumnId = mygrid.getColumnId(mygrid.getColIndexById(cellInd)+1);

	if(cellValue(rowId, "changed") != 'New' && 
		mygrid.cellById(rowId, mygrid.getColIndexById(columnId)).getValue() !=  mygrid.cellById(rowId, mygrid.getColIndexById(originalValueColumnId)).getValue()) {
		mygrid.cells(rowId,mygrid.getColIndexById("changed")).setValue("Y");
	} 
	
	if(cellInd == 'status')
		statusChange = 'Y';
	
/*	
	if(cellInd == 'status' && $("status"+rowId).checked == true) {
		setActiveRowColor(rowId)
	} 
	else if(cellInd == 'status' && $("status"+rowId).checked == false) {
		setInactiveRowColor(rowId)
	}
*/
}
/*
function setActiveRowColor(rowId) {
	colorStyle = mygrid.rowsAr[rowId].className;
	try {
			mygrid.setCellTextStyle(rowId, 1, colorStyle);
			mygrid.setCellTextStyle(rowId, 3, colorStyle);
			mygrid.setCellTextStyle(rowId, 5, colorStyle);
			mygrid.setCellTextStyle(rowId, 7, colorStyle);
			mygrid.setCellTextStyle(rowId, 9, colorStyle);
	} catch (ex) {}
}

function setInactiveRowColor(rowId) {
	rowIndex = mygrid.getRowIndex(rowId);
	// Check JSON data because grid cell may not have been rendered yet
	if ($("status"+rowId) != null && $("status"+rowId).checked == false) {
		var colorStyle = "background-color: #727272;color: #ffffff";
		//grid black
		try {
			mygrid.setCellTextStyle(rowId, 1, colorStyle);
			mygrid.setCellTextStyle(rowId, 3, colorStyle);
			mygrid.setCellTextStyle(rowId, 5, colorStyle);
			mygrid.setCellTextStyle(rowId, 7, colorStyle);
			mygrid.setCellTextStyle(rowId, 9, colorStyle);
		} catch (ex) {}
	}
}
*/
function checkAll(checkboxname) {
	
	var checkall = $("chkAll");
	var rowsNum = mygrid.getRowsNum(); // Get the total rows of the grid

	if (checkall.checked) {
		for ( var i = 0; i < mygrid.getRowsNum(); i++) {
			p = mygrid.getRowId(i);
			var cid = checkboxname + p;
			if (!$(cid).disabled && !$(cid).checked) {
				$(cid).checked = true;
				updateHchStatusA(cid); // This function is to
							// update the global
							// variable for
							// hchstatus
			}
			
			if(cellValue(p, "changed") != 'New' && 
				mygrid.cellById(p, mygrid.getColIndexById("status")).getValue() !=  mygrid.cellById(p, mygrid.getColIndexById("originalStatus")).getValue()) 
			mygrid.cells(p,mygrid.getColIndexById("changed")).setValue("Y");
		}
	}
	else {
		for ( var i = 0; i < mygrid.getRowsNum(); i++) {
			p = mygrid.getRowId(i);
			var cid = checkboxname + p;
			if (!$(cid).disabled && $(cid).checked) {
				$(cid).checked = false;
				updateHchStatusA(cid); // This function is to
							// update the global
							// variable for
							// hchstatus
			}
			
			if(cellValue(p, "changed") != 'New' && 
				mygrid.cellById(p, mygrid.getColIndexById("status")).getValue() !=  mygrid.cellById(p, mygrid.getColIndexById("originalStatus")).getValue()) 
			mygrid.cells(p,mygrid.getColIndexById("changed")).setValue("Y");
		}
	}
	
	return true;
}

var totalRows = null;
var getNewRowColor = "N";
function addNewRow() {
   
    totalRows = mygrid.getRowsNum(); 
    var rowid = (new Date()).valueOf();
     
   var thisrow = mygrid.addRow(rowid,"",totalRows);
   
   mygrid.selectRow(mygrid.getRowIndex(rowid),null,false,true);
   mygrid.cells(rowid,mygrid.getColIndexById("permission")).setValue('Y');
   
   mygrid.cells(rowid,mygrid.getColIndexById("status")).setValue(true);
   mygrid.cells(rowid,mygrid.getColIndexById("originalStatus")).setValue(true);  
   if(typeof( mygrid.getColIndexById("chargeNumber1") ) != 'undefined') {
   	 mygrid.cells(rowid,mygrid.getColIndexById("chargeNumber1")).setValue("");
   	 mygrid.cells(rowid,mygrid.getColIndexById("originalChargeNo1")).setValue("");
  	 mygrid.cells(rowid,mygrid.getColIndexById("chargeNo1Alias")).setValue($v('chargeLabel1Alias'));
   }
   
   if(typeof( mygrid.getColIndexById("chargeNumber2") ) != 'undefined') {
   	 mygrid.cells(rowid,mygrid.getColIndexById("chargeNumber2")).setValue("");
   	 mygrid.cells(rowid,mygrid.getColIndexById("originalChargeNo2")).setValue("");
  	 mygrid.cells(rowid,mygrid.getColIndexById("chargeNo2Alias")).setValue($v('chargeLabel2Alias'));
   }
   
   if(typeof( mygrid.getColIndexById("chargeNumber3") ) != 'undefined') {
   	 mygrid.cells(rowid,mygrid.getColIndexById("chargeNumber3")).setValue("");
   	 mygrid.cells(rowid,mygrid.getColIndexById("originalChargeNo3")).setValue("");
  	 mygrid.cells(rowid,mygrid.getColIndexById("chargeNo3Alias")).setValue($v('chargeLabel3Alias'));
   }
   
   if(typeof( mygrid.getColIndexById("chargeNumber4") ) != 'undefined') {
   	 mygrid.cells(rowid,mygrid.getColIndexById("chargeNumber4")).setValue("");
   	 mygrid.cells(rowid,mygrid.getColIndexById("originalChargeNo4")).setValue("");
  	 mygrid.cells(rowid,mygrid.getColIndexById("chargeNo4Alias")).setValue($v('chargeLabel4Alias'));
   }
   
   mygrid.cells(rowid,mygrid.getColIndexById("chargeId1")).setValue("");  
   mygrid.cells(rowid,mygrid.getColIndexById("changed")).setValue("New");
   
   if(typeof( mygrid.getColIndexById("description") ) != 'undefined') {
	   	 mygrid.cells(rowid,mygrid.getColIndexById("description")).setValue("");
	   	 mygrid.cells(rowid,mygrid.getColIndexById("originalDescription")).setValue("");
	}
   
   selectedRowId = rowid; 
  
   parent.$("deleteRowSpan").style["display"] = "";
   parent.$("updateSpan").style["display"] = "";
  
   document.getElementById("status"+rowid).focus();
   
   getNewRowColor = 'Y';
   doOnBeforeSelect(rowid,selectedRowId);
   doOnRowSelected(rowid);
   
}

function deleteRow() {
   mygrid.deleteRow(selectedRowId);
   parent.$("deleteRowSpan").style["display"] = "none";
}

function updateData() {
	if (validationforUpdate()) 
	{
		document.genericForm.target = '';
		document.getElementById('uAction').value = 'update';

		parent.showPleaseWait(); // Show "please wait" while updating

		if (mygrid != null)
			mygrid.parentFormOnSubmit(); // Got to call this function to send the data from grid back to the server
			
		var now = new Date();
  		parent.$("startSearchTime").value = now.getTime();

		document.genericForm.submit(); // back to server
	}
}

// validate the whole grid
function validationforUpdate() {
	var rowsNum = mygrid.getRowsNum();
	
	for(var p=0;p<mygrid.getRowsNum();p++) {
		var rowId = mygrid.getRowId(p);
		if (validateLine(rowId) == false) {
			mygrid.selectRowById(rowId, null, false, true); // Make the selected row fall on the one which does pass the validation
			doOnBeforeSelect(rowId,selectedRowId);
   			doOnRowSelected(rowId);
			return false;
		}
	}

	return true;
}

// validate one line here
function validateLine(rowId) {
  var errorMessage = messagesData.validvalues;

  try {
	if (typeof( cellValue(rowId, "chargeNumber1") ) != 'undefined') {
			var chargeNumber1 = cellValue(rowId, "chargeNumber1").trim();
			if(chargeNumber1.length == 0)
			{
				alert(errorMessage + '\n' + $v('chargeNumber1Label'));
				return false;
			}
			else{
				
				$('chargeNumber1'+rowId).value = chargeNumber1;
				var patt=new RegExp($v('chargeValidation1'),"i");
				if(!patt.test(chargeNumber1))
					{
						alert(chargeNumber1 + " " + messagesData.notCorrectFormat);
						return false;
					}
			}
	}
	
	if (typeof( cellValue(rowId, "chargeNumber2") ) != 'undefined') { 
		var chargeNumber2 = cellValue(rowId, "chargeNumber2");
		if(chargeNumber2.trim().length == 0)
		{
			alert(errorMessage + '\n' + $v('chargeNumber2Label'));
			return false;
		}
		var patt=new RegExp($v('chargeValidation2'),"i");
		if(!patt.test(chargeNumber2))
			{
				alert(chargeNumber2 + " " + messagesData.notCorrectFormat);
				return false;
			}
	}
	
	if (typeof( cellValue(rowId, "chargeNumber3") ) != 'undefined') { 
		var chargeNumber3 = cellValue(rowId, "chargeNumber3");
		if(chargeNumber3.trim().length == 0)
		{
			alert(errorMessage + '\n' + $v('chargeNumber3Label'));
			return false;
		}
		var patt=new RegExp($v('chargeValidation3'),"i");
		if(!patt.test(chargeNumber3))
			{
				alert(chargeNumber3 + " " + messagesData.notCorrectFormat);
				return false;
			}
	}
	
	if (typeof( cellValue(rowId, "chargeNumber4") ) != 'undefined') { 
		var chargeNumber4 = cellValue(rowId, "chargeNumber4");
		if(chargeNumber4.trim().length == 0)
		{
			alert(errorMessage + '\n' + $v('chargeNumber4Label'));
			return false;
		}
		var patt=new RegExp($v('chargeValidation4'),"i");
		if(!patt.test(chargeNumber4))
			{
				alert(chargeNumber4 + " " + messagesData.notCorrectFormat);
				return false;
			}
	}

	return true;
  }
  catch(ex) {}
  
}


