var beanGrid;
windowCloseOnEsc = true;

var selectedRowId = null;
var dhxFreezeWins = null;
var children = new Array();
var showRightClick = false;
var multiplePermissions = true;

// Build up the array for the columns which use different permissions
var permissionColumns = new Array();
permissionColumns = {
        "testId" : true
};

function myInit() {
	// build the grid for display
	doInitGrid();
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
     	$('labTestBean').style.width = myWidth-40;   
    }
    catch (ex)
    {
     //alert("here 112");
    } 
}

function doInitGrid() {
    initGridWithGlobal(gridConfig);
    /*
    beanGrid = new dhtmlXGridObject('labTestBean');

    // initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	initGridWithConfig(beanGrid, labTestConfig, true, true, true);
	if (typeof (jsonMainData) != 'undefined') {
		beanGrid.parse(jsonMainData, "json");
	}

	// set all kinds of event for the grid. refer to http://www.dhtmlx.com
	// beanGrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	beanGrid.attachEvent("onRowSelect", selectRow);
	beanGrid.attachEvent("onRightClick", selectRightclick);
	*/
}

function selectRow(rowId, cellInd) {
	selectedRowId = rowId; 
	try{
		$("editSpan").style["display"] = "";
	} catch(ex) {}     
}

function selectRightclick(rowId, cellInd) {

	beanGrid.selectRowById(rowId, null, false, false);
	selectRow(rowId, cellInd); // The right click event needs to call selectRow function.
	if(showRightClick) {
		var columnId = beanGrid.getColumnId(cellInd);
		var status = cellValue(rowId, "status");
		var menuItems = new Array();		
		if( gridCellValue(beanGrid, selectedRowId, "permission") == "Y" && columnId == "testId") {			
			menuItems[menuItems.length] = 'text='+messagesData.edittest+';url=javascript:editTest();';			
			if (status == "new")
				menuItems[menuItems.length] = 'text='+messagesData.deletetest+';url=javascript:deleteTest();';
			
		    replaceMenu('editTestMenu',menuItems);
		    toggleContextMenu('editTestMenu');
		} else if ( gridCellValue(beanGrid, selectedRowId, "permission") == "Y" && status == "new") {
			menuItems[menuItems.length] = 'text='+messagesData.deletetest+';url=javascript:deleteTest();';
		    replaceMenu('editTestMenu',menuItems);
		    toggleContextMenu('editTestMenu');
		} else {		
			toggleContextMenu('contextMenu');
		}
	}
}

function replaceMenu(menuname, menus) {
	var i = mm_returnMenuItemCount(menuname);

	for (; i > 1; i--)
		mm_deleteItem(menuname, i);

	for (i = 0; i < menus.length;) {
		var str = menus[i];

		i++;
		mm_insertItem(menuname, i, str);
		// delete original first item.
		if (i == 1)
			mm_deleteItem(menuname, 1);
	}
}


function deleteTest() {
	if(cellValue(selectedRowId,"status") == 'new')
		beanGrid.deleteRow(selectedRowId);
}

function callChangeStatus(rowId) {
	if(cellValue(rowId,"status") != 'new')
		beanGrid.cellById(rowId, beanGrid.getColIndexById("status")).setValue("updated");
}

function frequencyChanged(rowId) {
    var tempVal = cellValue(rowId,"frequency");
    for ( var p = 1; p <= beanGrid.getRowsNum(); p++) {
        beanGrid.cellById(p,beanGrid.getColIndexById("frequency")).setValue(tempVal);
        callChangeStatus(p);
    }
}

function frequencyUnitChanged(rowId) {
    var tempVal = cellValue(rowId,"frequencyUnit");
	for ( var p = 1; p <= beanGrid.getRowsNum(); p++) {
        beanGrid.cellById(p, beanGrid.getColIndexById("frequencyUnit")).setValue(tempVal);
        callChangeStatus(p);
        frequencyTypeOption(p,tempVal);
    }
}

function frequencyTypeChanged(rowId) {
    var tempVal = cellValue(rowId,"frequencyType");
	for ( var p = 1; p <= beanGrid.getRowsNum(); p++) {
        beanGrid.cellById(p, beanGrid.getColIndexById("frequencyType")).setValue(tempVal);
        callChangeStatus(p);
    }
}

function frequencyTypeOption(rowId,frequencyUnit) {
    if (frequencyUnit == 'Receipts') {
        beanGrid.cellById(rowId, beanGrid.getColIndexById("frequencyTypePermission")).setValue('N');
        beanGrid.cellById(rowId, beanGrid.getColIndexById("frequencyType")).setValue('Part');
    }else {
        beanGrid.cellById(rowId, beanGrid.getColIndexById("frequencyTypePermission")).setValue('Y');
        beanGrid.cellById(rowId, beanGrid.getColIndexById("frequencyType")).setValue('');
    }
}

function requiredCustomerResponseChange(rowId) {
    var tempVal = cellValue(rowId,"requireCustomerResponse");
    if (tempVal == 'true')
        tempVal = true;
    else
        tempVal = false;
    for ( var p = 1; p <= beanGrid.getRowsNum(); p++) {
        beanGrid.cellById(p,beanGrid.getColIndexById("requireCustomerResponse")).setValue(tempVal);
        callChangeStatus(p);
    }    
}


function addRow() {

 	//var rowId = (new Date()).valueOf();
 	var ind = beanGrid.getRowsNum();
    var rowId = ind+1;

    //get frequency data from first row
    var freqVal = cellValue(1,"frequency");
    var freqUnitVal = cellValue(1,"frequencyUnit");
    var freqTypeVal = cellValue(1,"frequencyType");
    var freqTypePerm = cellValue(1,"frequencyTypePermission");
    if (freqUnitVal == 'Receipts') {
        freqTypePerm = 'N';
        freqTypeVal = 'Part';
    }
    var reqCustRespVal = cellValue(1,"requireCustomerResponse");
    if (reqCustRespVal == 'true')
        reqCustRespVal = true;
    else
        reqCustRespVal = false;

    count = 0 ;
	  
	var thisrow = beanGrid.addRow(rowId,"",ind);
	beanGrid.cells(rowId, count++).setValue('Y');   //row permission
    beanGrid.cells(rowId, count++).setValue(freqTypePerm);   //frequency type permission
    beanGrid.cells(rowId, count++).setValue('Y');   //test permission
	beanGrid.cells(rowId, count++).setValue('');    //test
    beanGrid.cells(rowId, count++).setValue('Y');   //active
    beanGrid.cells(rowId, count++).setValue('');    //unsuccessful tests for skip
	beanGrid.cells(rowId, count++).setValue('');    //test allowed skip
	beanGrid.cells(rowId, count++).setValue('');    //test type
	beanGrid.cells(rowId, count++).setValue(freqVal);    //frequency
    beanGrid.cells(rowId, count++).setValue(freqUnitVal);    //frequency unit
    beanGrid.cells(rowId, count++).setValue(freqTypeVal);    //frequency type
    beanGrid.cells(rowId, count++).setValue(reqCustRespVal);    //require customer response
    beanGrid.cells(rowId, count++).setValue('');    //last updated
    beanGrid.cells(rowId, count++).setValue('');    //last updated date (formatted)
    beanGrid.cells(rowId, count++).setValue('');    //last updated date
    beanGrid.cells(rowId, count++).setValue('new');

    /*
    beanGrid.selectRow(beanGrid.getRowIndex(rowId));
	selectedRowId = rowId;
	try{
		$("noSuccessTestReqForSkip"+selectedRowId).focus();
	} catch(ex) {
		setTimeout('$("noSuccessTestReqForSkip"+selectedRowId).focus()',100);
	}
	*/
}  //end of method

function editTest() {
	showTransitWin(messagesData.edittest);
	children[children.length] = openWinGeneric('edittest.do?companyId='+$v("companyId")+
                                               '&facilityId='+encodeURIComponent($v("facilityId"))+
                                               '&catalogCompanyId='+$v("catalogCompanyId")+
                                               '&catalogId='+encodeURIComponent($v("catalogId")),"EditTest","930","630",'yes' );
}

function rebuildTestArray(testIdfromPopup) {
	testId = new Array();
	
	testId[0] = {text:messagesData.pleaseselect, value:""};
	for (var i = 0; i < testIdfromPopup.length; i++) { 
		testId[i+1] = {text:""+testIdfromPopup[i].text, value:""+testIdfromPopup[i].value};
	}
}

function rebuildTestDropdown(rowId) { 
  if('new' == gridCellValue(beanGrid,rowId,"status")) {
	  var originalTest = gridCellValue(beanGrid,rowId,"testId");
	  
	  var testIddropdown = $("testId"+rowId);
	  for (var i = testIddropdown.length-1 ; i >= 0; i--) {
	      testIddropdown[i] = null;
	  }
	  
	  if (testId == null || testId.length == 0) {
		  setOption(0, messagesData.pleasecreatenewtest, "", 'testId'+rowId);
		  return;
	  }
	  
	  if (testId.length == 1) {
		  setOption(0, testId[0].text, testId[0].value, 'testId'+rowId);
		  return;
	  }

	  var selectedIndex = 0;
	  var start = 0;
	  for (var i = 0; i < testId.length; i++) {
		  if ((originalTest != null) && (originalTest == testId[i].value)) {
			 selectedIndex = start;
		  }
		  setOption(start++, testId[i].text, testId[i].value, 'testId'+rowId);
	  }
		  
	  $('testId'+rowId).selectedIndex = selectedIndex;
  }
}

function applyDefaultTests() {
		document.getElementById('uAction').value = 'applyDefaultTests';
		showPleaseWait(); // Show "please wait" while updating
		document.genericForm.submit(); // back to server
}

function updatePage() {
	if (validationforUpdate()) {
		document.genericForm.target = '';
		document.getElementById('uAction').value = 'update';

		showPleaseWait(); // Show "please wait" while updating

		if (beanGrid != null)
			beanGrid.parentFormOnSubmit(); // Got to call this function to send the
										// data from grid back to the server

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
	var errorMessage = messagesData.validvalues + "\n";
	var count = 0;
    //make sure there is test ID
    var tmpVal = gridCellValue(beanGrid,rowId, "testId");
    if (tmpVal.trim() == "") {
        errorMessage += "\t"+messagesData.test+"\n";
        count++;
    }
    //make sure that user entered valid data type for the following
    //noSuccessTestReqForSkip
    tmpVal = gridCellValue(beanGrid,rowId, "noSuccessTestReqForSkip");
    if (tmpVal != null && tmpVal.length > 0) {
        if (!isInteger(tmpVal)) {
            errorMessage += "\t"+messagesData.successTestForSkip+"\n";
            count++;
        }
    }
    //frequency
    tmpVal = gridCellValue(beanGrid,rowId, "frequency");
    if (tmpVal != null && tmpVal.length > 0) {
        if (!isInteger(tmpVal)) {
            errorMessage += "\t"+messagesData.frequency+"\n";
            count++;
        }
    }

    if (count > 0) {
        alert(errorMessage);
        return false;
    }
	return true;
}

var dhxFreezeWins = null;
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
		transitWin.setModal(true);
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

function closeAllchildren()
{
    try {
        for(var n=0;n<children.length;n++) {
            try {
            children[n].closeAllchildren();
            } catch(ex){}
        children[n].close();
        }
    } catch(ex){}
    children = new Array();
} //end of method

function refreshTests()
{	
	showTransitWin("page to refresh"); // Show "please wait" while refreshing
	document.genericForm.submit(); // back to server
	
}