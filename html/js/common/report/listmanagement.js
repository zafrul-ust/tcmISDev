// This works only for popup windows and IE. Close the window after clicking Esc key
var windowCloseOnEsc = true;
resizeGridWithWindow = true;
var beangrid;
var dhxFreezeWins;
var selectedRowId;


function submitSearchForm() {
	/*
	 * Make sure to not set the target of the form to anything other than
	 * resultFrame
	 */
	var now = new Date();
	document.getElementById("startSearchTime").value = now.getTime();

	if (validateSearchForm()) {
		$('uAction').value = 'search';
		document.genericForm.target = 'resultFrame';
		showPleaseWait();
		return true;
	} else {
		return false;
	}
}

function validateSearchForm() {
	return true;
}

function newlist() {
	//document.getElementById("submitSearch").disabled="disabled";
		
	children[children.length] = openWinGeneric("newlist.do?uAction=new","shownewlist","450","250","yes","80","80");
	showTransitWin(messagesData.pleasewait);

}


function deleteList() {
	parent.showTransitWin("Please Wait");
	callToServer("listmanagementresults.do?uAction=deleteList&listId=" + cellValue(selectedRowId, 'listId')+"&companyId="+$v("companyId"));
}

function listManagementRemoveLine(err)
{
	parent.stopShowingWait();
	  if(err != null && err != undefined && err != '')
	  {
		  parent.showUpdateMessages(err);
	  }
	  else
	  {
		  beangrid.deleteRow(selectedRowId);
	  }
}

function selectRow(rowId, cellInd) {
    rightClick = false;
    rowId0 = arguments[0];
    colId0 = arguments[1];
    ee     = arguments[2];

    if( ee != null ) {
        if( (ee.button != null && ee.button == 2) || ee.which == 3) {
            rightClick = true;
        }
    }
    selectedRowId = rowId0;
    if( !rightClick ) return;
    
    /*var menuItems = new Array();
    menuItems[menuItems.length] = 'text='+messagesData.manageList+';url=javascript:openManageList();';
    if (cellValue(selectedRowId,'permission') == 'Y' && cellValue(selectedRowId,'insertBy') == $v("personnelId")) {
        menuItems[menuItems.length] = 'text='+messagesData.deleteList+';url=javascript:deleteList();';
    }*/
    
    //replaceMenu('rightClickMenu',menuItems);
    //alert(cellValue(selectedRowId,'isAddLine'));
    
    if(cellValue(selectedRowId,'isAddLine') != 'true')
    	{
    	if(cellValue(selectedRowId,'permission')  == 'Y')
    		toggleContextMenu('rightClickMenuWithDelete');
    	else
    		toggleContextMenu('rightClickMenu');
    	}
    else
    	toggleContextMenu('');

} //end of method

// all same level, at least one item
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

function onRightClick(rowId, cellInd) {
}

function rowChanged(rowId) {
	if(cellValue(rowId,'isAddLine') != 'true')
		beangrid.cellById(rowId, beangrid.getColIndexById("updated")).setValue("true");
}

function submitUpdate() {
		 
	if(validateLines())
	{
	  $("uAction").value = 'updateListofLists';
	  parent.showPleaseWait();
	  beangrid.parentFormOnSubmit();
	// submitOnlyOnce();
	 document.genericForm.submit();
	}
}

function validateLines()
{
	var rowsNum = beangrid.getRowsNum();
	var err = '';
	var oneChanged = false;
	for(var i = 1; i < rowsNum + 1; i++)
		{
			if(cellValue(i,'permission') == 'Y')
				{		
				var listThresholdName = cellValue(i,'listThresholdName');
				var listThresholdUnit = cellValue(i,'listThresholdUnit');
				var listThreshold = cellValue(i,'listThreshold');
				
				if (listThresholdName != '' && listThresholdUnit == '')
					err += messagesData.listThresholdUnit + ' Row ' + i + '\n';
				if (listThresholdName != '' && listThreshold == '')
					err += messagesData.listThreshold + ' Row ' + i + '\n'; 
				if (listThresholdUnit != '' && listThresholdName == '') 	
					err += messagesData.listThresholdName  +  ' Row ' + i + '\n';
				if(listThresholdUnit != '' &&  listThreshold == '')
					err +=  messagesData.listThreshold +  ' Row ' + i + '\n'
				if (listThreshold != '' && listThresholdUnit == '')
					err += messagesData.listThresholdUnit  + ' Row ' + i + '\n'; 
				if (listThreshold != '' && listThresholdName == '')
					err += messagesData.listThresholdName + ' Row ' + i + '\n'; 
				if(listThreshold != '' && !isFloat(listThreshold))
					err += messagesData.positivenumber.replace('{0}', messagesData.listThreshold + ' Row ' + i) + '\n';
				if((cellValue(i,'thresholdName') != '' && cellValue(i,'thresholdUnit') == '') || (cellValue(i,'thresholdName') == '' && cellValue(i,'thresholdUnit') != ''))
						err += messagesData.thresholdunit1 + ' Row ' + i + '\n';
				if((cellValue(i,'thresholdName2') != '' && cellValue(i,'thresholdUnit2') == '') || (cellValue(i,'thresholdName2') == '' && cellValue(i,'thresholdUnit2') != ''))
						err += messagesData.thresholdunit2 + ' Row ' + i + '\n';
				if((cellValue(i,'thresholdName3') != '' && cellValue(i,'thresholdUnit3') == '') || (cellValue(i,'thresholdName3') == '' && cellValue(i,'thresholdUnit3') != ''))
					err += messagesData.thresholdunit3 + ' Row ' + i + '\n';
				if(cellValue(i,'updated') == 'true' || cellValue(i,'isAddLine') == 'true')
					oneChanged = true;
				}
		}
	if(err != '')
		{
			alert(messagesData.validValues + '\n\n' + err);
			return false;
		}
	else if(!oneChanged)
		{
			alert(messagesData.nothingchanged);
			return false;
		}
	else 
		return true; 
}

function updateListDropDown(jsonObj,newListId)
{
	
	var list = document.getElementById("listId");
	list.options.length = 0;

	for (var i = 0; i < jsonObj.length; i++) 
	{	
		list.options[i] = new Option(jsonObj[i].listName,jsonObj[i].listId);
		list.options[i].title = jsonObj[i].listDescription;
	}
	
	
	if(newListId != "")
	{
		list.value = newListId;
		var now = new Date();
		document.getElementById("startSearchTime").value = now.getTime();
		document.getElementById("uAction").value = 'search';
		document.genericForm.target='resultFrame';
		showPleaseWait();
		document.genericForm.submit();
	}

		
}

function addNewList(casNumber, rptChemical)
{
	parent.stopShowingWait();
	
   $("listManagementViewBean").style["display"] = "";
   
   if(beangrid == null) {
	   initializeGrid(); 
   }  
   
    var ind = beangrid.getRowsNum();  
    var rowid = ind*1+1;
    
	var thisrow = beangrid.addRow(rowid,"",ind);
	beangrid.selectRow(beangrid.getRowIndex(rowid),null,false,false);

	beangrid.cells(rowid,beangrid.getColIndexById("permission")).setValue('Y');
	beangrid.cells(rowid,beangrid.getColIndexById("listName")).setValue('');
	beangrid.cells(rowid,beangrid.getColIndexById("listDescription")).setValue('');
	beangrid.cells(rowid,beangrid.getColIndexById("reference")).setValue('');
	beangrid.cells(rowid,beangrid.getColIndexById("author")).setValue('');
	beangrid.cells(rowid,beangrid.getColIndexById("insertDate")).setValue('');
	beangrid.cells(rowid,beangrid.getColIndexById("listId")).setValue('');
	beangrid.cells(rowid,beangrid.getColIndexById("isAddLine")).setValue(true);
	beangrid.cells(rowid,beangrid.getColIndexById("updated")).setValue(false);
	beangrid.cells(rowid,beangrid.getColIndexById("thresholdName")).setValue('');	
	beangrid.cells(rowid,beangrid.getColIndexById("thresholdUnit")).setValue('');	
	beangrid.cells(rowid,beangrid.getColIndexById("thresholdName2")).setValue('');	
	beangrid.cells(rowid,beangrid.getColIndexById("thresholdUnit2")).setValue('');	
	beangrid.cells(rowid,beangrid.getColIndexById("thresholdName3")).setValue('');	
	beangrid.cells(rowid,beangrid.getColIndexById("thresholdUnit3")).setValue('');
	beangrid.cells(rowid,beangrid.getColIndexById("listThresholdName")).setValue('');
	beangrid.cells(rowid,beangrid.getColIndexById("listThreshold")).setValue('');	
	beangrid.cells(rowid,beangrid.getColIndexById("listThresholdUnit")).setValue('');	
	
	beangrid.scrollPage(thisrow.offsetTop);

}


function generateExcel() {

	if (validateSearchForm()) {
		$('uAction').value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',
				'_List_Chemical_Excel', '650', '600', 'yes');
		document.genericForm.target = '_List_Chemical_Excel';
		var a = window.setTimeout("document.genericForm.submit();", 50);
	}
}

function uploadList() {

	children[children.length] = openWinGeneric("newlist.do?uAction=showUploadList","uploadList","550","550","yes","80","80");
	showTransitWin(messagesData.pleasewait);

//	if (validateSearchForm()) {
//		$('uAction').value = 'uploadList';
//		openWinGeneric('/tcmIS/common/uploadmanagementlist.jsp',
//				'_Upload_Management_List', '650', '600', 'yes');
//		document.genericForm.target = '_Upload_Management_List';
//		var a = window.setTimeout("document.genericForm.submit();", 50);
//	}
}


function getCasSearchString() {
	return false;
}



function initializeDhxWins() {
	if (dhxFreezeWins == null) {
		dhxFreezeWins = new dhtmlXWindows();
		dhxFreezeWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showTransitWin(messageType)
{
	var waitingMsg = messagesData.pleasewaitmenu+"...";
	$("transitLabel").innerHTML = waitingMsg;

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

function stopShowingWait() {
	if (dhxFreezeWins != null) {
		if (dhxFreezeWins.window("transitDailogWin")) {
			dhxFreezeWins.window("transitDailogWin").setModal(false);
			dhxFreezeWins.window("transitDailogWin").hide();
		}
	}
	return true;
}

function openManageList() {
	//document.getElementById("submitSearch").disabled="disabled";
	parent.showTransitWin(parent.messagesData.pleasewait);
	parent.children[parent.children.length] = openWinGeneric("specificlistmanagementsearchresults.do?uAction=search&maxData=5000&listId="+encodeURIComponent(cellValue(selectedRowId,'listId')) +
                                                             "&listName="+encodeURIComponent(cellValue(selectedRowId,'listName')) +
                                                             "&listDescription="+encodeURIComponent(cellValue(selectedRowId,'listDescription')) +
                                                             "&reference="+encodeURIComponent(cellValue(selectedRowId,'reference')) +
                                                             "&permission="+cellValue(selectedRowId,'permission') ,"shownewlist","930","500","yes","80","80");
}

function closeAllchildren() 
{ 
	{
		try {
			for(var n=0;n<children.length;n++) {
				children[n].closeAllchildren(); 
			}
		} catch(ex)
		{
		}
		if(!window.closed)
			window.close();
	} 
} 


function showUpdateMessages(msg)
{
  //var resulterrorMessages = window.frames["resultFrame"].document.getElementById("errorMessagesAreaBody");
  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.innerHTML = msg;//resulterrorMessages.innerHTML;

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";

  var dhxWins = new dhtmlXWindows();
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window("errorWin")) {
  // create window first time
  var errorWin = dhxWins.createWindow("errorWin", 0, 0, 450, 300);
  errorWin.setText(messagesData.errors);
  errorWin.clearIcon();  // hide icon
  errorWin.denyResize(); // deny resizing
  errorWin.denyPark();   // deny parking
  errorWin.attachObject("errorMessagesArea");
  errorWin.attachEvent("onClose", function(errorWin){errorWin.hide();});
  errorWin.center();
  }
  else
  {
    // just show
    dhxWins.window("errorWin").show();
  }
}
