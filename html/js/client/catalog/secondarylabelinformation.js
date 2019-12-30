windowCloseOnEsc = true;

var selectedRowId = null;
var dhxFreezeWins = null;

function myInit() {
	// build the grid for display
	doInitGrid();
		
	//	document.getElementById("updateResultLink").style["display"] = "";
	
	if ($v("iconCount") > 0) {
		document.getElementById("iconBean").style["display"] = "";
		// build the grid for display
		doInitIconGrid();
	}
	
	myResize();
}

function myResize() {
	setWindowSizes();
	if (_isIE)
		myWidth -=35;
	else
	    myWidth -=60;
	   
	try {
   		var id=beanGrid.entBox.id;
   		var griDiv = $(id);
   		griDiv.style.width = myWidth + "px";   
  	}
  	catch(ex)
  	{
 		     //alert("THis means the grid was not initialized");
  	}
  	
  	try {  
     	$('secondaryLabelDataBean').style.width = myWidth-40;   
    }
    catch (ex)
    {
     //alert("here 112");
    } 
}

function doInitGrid() {
	beanGrid = new dhtmlXGridObject('secondaryLabelDataBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	initGridWithConfig(beanGrid, secondaryLabelDataConfig, false, true, true);
	if (typeof (jsonMainData) != 'undefined') {
		beanGrid.parse(jsonMainData, "json");
	}

	// set all kinds of event for the grid. refer to http://www.dhtmlx.com
	// beanGrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	beanGrid.attachEvent("onRowSelect", selectRow);
	beanGrid.attachEvent("onRightClick", selectRightclick);
}

function doInitIconGrid() {
	iconGrid = new dhtmlXGridObject('iconBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	initGridWithConfig(iconGrid, iconConfig, false, true, true);
	if (typeof (jsonIconData) != 'undefined') {
		iconGrid.parse(jsonIconData, "json");
	}
	
}

function buildNameDropdownArray(selectedType) {
	  var nameIdArray = altNameId[selectedType];
	  var nameArray = altName[selectedType];
	  var nameGridArray = new Array();
	  
	  if(typeof (nameIdArray) == 'undefined' || nameIdArray.length == 0) {
	    nameGridArray[0] = {text:'',value:''};
	  }
	  
	  if(typeof (nameIdArray) != 'undefined') {
		  for (var i=0; i < nameIdArray.length; i++) {
			    nameGridArray[i] = {text:nameIdArray[i],value:nameIdArray[i]};
		  }
	  }
	  return nameGridArray;
}

function typeChanged(rowId,columnId,fromPopup) {
	  if ('Y' != fromPopup )
	  	beanGrid.cells(rowId,beanGrid.getColIndexById("commentAltTxt")).setValue("");
	  
	  var selectedType = gridCellValue(beanGrid,rowId,"typeId");
	  var originalName = gridCellValue(beanGrid,rowId,"commentId");
	  
	  var commentId = $("commentId"+rowId);
	  for (var i = commentId.length; i > 0; i--) {
	    commentId[i] = null;
	  }
	  var selectedIndex = 0 ;
	  var nameIdArray = altNameId[selectedType];
	  var nameArray = altName[selectedType];
	  
	  if (nameIdArray == null || nameIdArray.length == 0) {
	  	  if(selectedType == "")
		    setOption(0, "", "", 'commentId'+rowId);
		  else
		    setOption(0, messagesData.pleasecreatenewname, "", 'commentId'+rowId);
		  return;
	  }
	  
	  if (nameIdArray.length == 1) {
	  		 setOption(0, messagesData.pleaseselect, "", 'commentId'+rowId);
			 setOption(1, nameIdArray[0], nameIdArray[0], 'commentId'+rowId);
			 return;
	  }

	  setOption(0, messagesData.pleaseselect, "", 'commentId'+rowId);
	  var selectedIndex = 0;
	  var start = 1;
	  for (var i = 0; i < nameArray.length; i++) {
			if ('Y' == fromPopup && originalName != null && originalName == nameIdArray[i]) {
			        selectedIndex = start;
			}  
		    setOption(start++, nameIdArray[i], nameIdArray[i], 'commentId'+rowId);
	  }
		  
	  $('commentId'+rowId).selectedIndex = selectedIndex;
	  
	  if ('Y' == fromPopup && originalName != null && originalName.length > 0){
	    nameChanged(rowId,columnId);
	  } 
}

function nameChanged(rowId,columnId) {
	var selectedType = gridCellValue(beanGrid,rowId,"typeId");
	var selectedNameId = gridCellValue(beanGrid,rowId,"commentId"); 
	var nameIdArray = altNameId[selectedType];
	var nameArray = altName[selectedType];
	
	beanGrid.cells(rowId,beanGrid.getColIndexById("commentAltTxt")).setValue("");
	for (var i = 0; i < nameIdArray.length; i++) {
	  	if(selectedNameId == nameIdArray[i])
			beanGrid.cells(rowId,beanGrid.getColIndexById("commentAltTxt")).setValue(nameArray[i]);
	}  
}

function selectRow(rowId, cellInd) {
	selectedRowId = rowId; 
}

function selectRightclick(rowId, cellInd) {

	beanGrid.selectRowById(rowId, null, false, false);
	selectRow(rowId, cellInd); // The right click event needs to call selectRow function.
	if(showRightClick) {
		var columnId = beanGrid.getColumnId(cellInd);
		if( gridCellValue(beanGrid,selectedRowId, "permission") == "Y" && columnId == "typeId") {
			toggleContextMenu('editTypeMenu');
		}
		
		if( gridCellValue(beanGrid,selectedRowId, "permission") == "Y" && columnId == "commentId") {
			toggleContextMenu('editNameMenu');
		}
	}
}

function addRow() {

 	var rowId = (new Date()).valueOf();
 	ind = beanGrid.getRowsNum();
	  
	count = 0 ;
	  
	var thisrow = beanGrid.addRow(rowId,"",ind);
//    alertthis = true;
	beanGrid.cells(rowId, count++).setValue('Y');
	beanGrid.cells(rowId, count++).setValue('');
	  
	$("typeId"+rowId).selectedIndex = 0;
	commentId[rowId] = buildNameDropdownArray($v("typeId"+rowId));
// opsentityid
	beanGrid.cells(rowId, count++).setValue('');
	beanGrid.cells(rowId, count++).setValue('');

	selectedRowId = rowId;
	beanGrid.selectRow(beanGrid.getRowIndex(rowId));  
}

function deleteRow(){
	beanGrid.deleteRow(selectedRowId);
}

function editType() {
	showTransitWin(messagesData.edittype);
	openWinGeneric('edittype.do?facilityId='+ $v("facilityId"),"EditType","600","400",'yes' );
}

function editName() {
	if(gridCellValue(beanGrid,selectedRowId, "typeId") != null && gridCellValue(beanGrid,selectedRowId, "typeId") != "") {
		showTransitWin(messagesData.editname);
		openWinGeneric('editname.do?facilityId='+ $v("facilityId")+"&typeId="+gridCellValue(beanGrid,selectedRowId, "typeId"),"EditName","800","400",'yes' );
	}
	else {
		alert(messagesData.choosetypeb4editname);
	}
}

function rebuildAllArray(typeIdfromPopup,altNameIdfromPopup,altNamefromPopup) {
	var typeIds = new Array();
	typeId = new Array();
	altNameId = new Array();
	altName = new Array();
	
	typeId[0] = {text:messagesData.pleaseselect,
				 value:""
				};
	for (var i = 0; i < typeIdfromPopup.length; i++) {
		typeId[i+1] = {text:""+typeIdfromPopup[i].text,
					 value:""+typeIdfromPopup[i].value
					 };
	
		altNameId[""+typeIdfromPopup[i].value] = new Array();
		for (var k = 0; k < altNameIdfromPopup[""+typeIdfromPopup[i].value].length; k++) {
			altNameId[""+typeIdfromPopup[i].value][k] = altNameIdfromPopup[""+typeIdfromPopup[i].value][k];
		}
		
		altName[""+typeIdfromPopup[i].value] = new Array();
		for (var m = 0; m < altNamefromPopup[""+typeIdfromPopup[i].value].length; m++) {
			altName[""+typeIdfromPopup[i].value][m] = altNamefromPopup[""+typeIdfromPopup[i].value][m];
		}
	}

}

function rebuildTypeDropdown(rowId) {
	  var originalType = gridCellValue(beanGrid,rowId,"typeId");
	  
	  var typeIddropdown = $("typeId"+rowId);
	  for (var i = typeIddropdown.length-1 ; i >= 0; i--) {
	    typeIddropdown[i] = null;
	  }
	  
	  if (typeId == null || typeId.length == 0) {
		    setOption(0, messagesData.pleasecreatenewtype, "", 'typeId'+rowId);
		    return;
	  }
	  
	  if (typeId.length == 1) {
			 setOption(0, typeId[0].text, typeId[0].value, 'typeId'+rowId);
			 return;
	  }

	  var selectedIndex = 0;
	  var start = 0;
	  for (var i = 0; i < typeId.length; i++) {
			if ((originalType != null) && (originalType == typeId[i].value)) {
			        selectedIndex = start;
			}
		    setOption(start++, typeId[i].text, typeId[i].value, 'typeId'+rowId);
	  }
		  
	  $('typeId'+rowId).selectedIndex = selectedIndex;
} 

function updatePage() {
	if (validationforUpdate()) {
		document.genericForm.target = '';
		document.getElementById('uAction').value = 'update';

		showPleaseWait(); // Show "please wait" while updating

		if ( typeof (beanGrid) != 'undefined' && beanGrid != null)
			beanGrid.parentFormOnSubmit(); // Got to call this function to send the
										// data from grid back to the server
		if (typeof (iconGrid) != 'undefined' && iconGrid != null)
			iconGrid.parentFormOnSubmit();

		document.genericForm.submit(); // back to server
	}
}

// validate the whole grid
function validationforUpdate() {
	var rowsNum = beanGrid.getRowsNum();

	for ( var p = 0; p < rowsNum; p++) {
		rowId = beanGrid.getRowId(p);
		if (validateLine(rowId) == false) {
			beanGrid.selectRowById(rowId, null, false, false); // Make the selected row fall on the one which does pass the validation
			return false;
		}
	}

	return true;
}

// validate one line here
function validateLine(rowId) {
	var errorMessage = messagesData.validvalues + "\n\n";
	var count = 0;

		var typeId = gridCellValue(beanGrid,rowId, "typeId");
		if (typeId.trim() == "") {
			errorMessage += messagesData.type;
			count = 1;
		}

		var commentId = gridCellValue(beanGrid,rowId, "commentId");
		if (commentId.trim() == "" ) { 
			errorMessage += "\n" + messagesData.name;
			count = 1;
		} 

		var desc = gridCellValue(beanGrid,rowId, "commentAltTxt");
		if (desc.length > 200) { // Limit the txt field to 200 characters
			errorMessage += "\n" + messagesData.maximum200;
			beanGrid.cellById(rowId, beanGrid.getColIndexById("commentAltTxt")).setValue(desc.substring(0, 200));
			count = 1;
		}

		if (count > 0) {
			alert(errorMessage);
			return false;
		}
	
	return true;
}

function initializeDhxWins() {
	if (dhxFreezeWins == null) {
		dhxFreezeWins = new dhtmlXWindows();
		dhxFreezeWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}


function showTransitWin(messageType)
{
	var waitingMsg = messagesData.waitingforinputfrom+"...";
	$("transitLabel").innerHTML = waitingMsg.replace(/[{]0[}]/g,messageType);

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.innerHTML = document.getElementById("transitDailogWinBody").innerHTML;
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxFreezeWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxFreezeWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
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
		transitWin.setModal(true);  // freeze the window here
		dhxFreezeWins.window("transitDailogWin").show();
	}
}

function closeTransitWin() {
	if (dhxFreezeWins != null) {
		if (dhxFreezeWins.window("transitDailogWin")) {
			dhxFreezeWins.window("transitDailogWin").setModal(false);
			dhxFreezeWins.window("transitDailogWin").hide();
		}
	}
}


