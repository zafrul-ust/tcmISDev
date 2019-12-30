// This works only for popup windows and IE. Close the window after clicking Esc key
var windowCloseOnEsc = true;
resizeGridWithWindow = true;
var beangrid;
var dhxFreezeWins;

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
		
	openWinGeneric("newlist.do?uAction=new","shownewlist","450","250","yes","80","80");
	showTransitWin(messagesData.pleasewait);

}


function selectRow(rowId, cellInd) {
	var columnId = beangrid.getColumnId(cellInd);
	
	if (columnId == "rptChemical")
	{
		rowChanged(rowId)
	}
}


function rowChanged(rowId) {
	beangrid.cellById(rowId, beangrid.getColIndexById("updated")).setValue("true");
}

function submitUpdate() {
	
	 $("uAction").value = 'update';
	 parent.showPleaseWait();
	 parent.$("startSearchTime").value = new Date().getTime();
	 beangrid.parentFormOnSubmit();
	 document.genericForm.submit();
}


function submitRefreshAdd() {
	
	 $("uAction").value = 'search';
	 parent.showPleaseWait();
     parent.$("startSearchTime").value = new Date().getTime();
	 beangrid.parentFormOnSubmit();
	 document.genericForm.submit();
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

function addListChemical(){
	openWinGeneric("casnumbersearchmain.do?fromListManagement=Yes","chemicalcassearch","600","400","yes","80","80");
	parent.showTransitWin(messagesData.pleasewait);
}

function addNewListchemical(casNumber, rptChemical)
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
	beangrid.cells(rowid,beangrid.getColIndexById("casNumber")).setValue(casNumber);
	beangrid.cells(rowid,beangrid.getColIndexById("rptChemical")).setValue(rptChemical);
	beangrid.cells(rowid,beangrid.getColIndexById("ok")).setValue(false);
	beangrid.cells(rowid,beangrid.getColIndexById("listId")).setValue(document.getElementById("listId").value);
	beangrid.cells(rowid,beangrid.getColIndexById("companyId")).setValue(document.getElementById("companyId").value);
	beangrid.cells(rowid,beangrid.getColIndexById("isAddLine")).setValue(true);
	
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

	openWinGeneric("newlist.do?uAction=showUploadList","uploadList","450","170","yes","80","80");
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

function displayGridSearchDuration() {
	   var totalLines = document.getElementById("totalLines");
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
	         footer.innerHTML = messagesData.recordFound+": "+(parseInt(totalLines.value)-1)+" -- "+messagesData.searchDuration+": "+minutes+" "+messagesData.minutes+" "+seconds+" "+messagesData.seconds;
	       }else {
	         footer.innerHTML = messagesData.recordFound+": "+(parseInt(totalLines.value)-1)+" -- "+messagesData.searchDuration+": "+seconds+" "+messagesData.seconds;
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
