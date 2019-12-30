// This works only for popup windows and IE. Close the window after clicking Esc key
var windowCloseOnEsc = true;
var resizeGridWithWindow = true;
var beangrid;
var dhxFreezeWins;
var selectedRowId;
var multiplePermissions = true;
var children = new Array(); 

var permissionColumns = new Array();
permissionColumns = {
        "ok" : true
};

function myOnLoad()
{

	 if( !gridConfig ) gridConfig = _gridConfig;
	 initGridWithGlobal(gridConfig);
	 displayNoSearchSecDurationLocal();
	 if (typeof( extraReduction ) != 'undefined')
	 	setResultSize(extraReduction);
	 else
	 	setResultSize();
	if(hasPermission && $v("totalLines") > 1)
	{
		$('updateResultLink').style.display = "";
		$('addResultLink').style.display = "";
		$('excelResultLink').style.display = "";
	}
	else if(hasPermission && $v("totalLines") == 1)
		$('addResultLinkSolo').style.display = "";
	else
		{
			$('excelResultLink').style.display = "";
		}
}

function displayNoSearchSecDurationLocal() {
	   var totalLines = document.getElementById("totalLines");
	   if (totalLines.value != null) {
	     if (totalLines.value*1 > 0) {
	       var startSearchTime = document.getElementById("startSearchTime");
	       var endSearchTime = document.getElementById("endSearchTime");
	       var minutes = 0;
	       var seconds = 0;
	       //the duration is in milliseconds
	       var searchDuration = (endSearchTime.value*1)-(startSearchTime.value*1);
	       if (searchDuration > (1000*60)) {   //calculating minutes
	         minutes = Math.round((searchDuration / (1000*60)));
	         var remainder = searchDuration % (1000*60);
	         if (remainder > 0) {   //calculating seconds
	           seconds = Math.round(remainder / 1000);
	         }
	       }else {  //calculating seconds
	         seconds = Math.round(searchDuration / 1000);
	       }
	       var footer = document.getElementById("footer");
	       if (minutes != 0) {
	         footer.innerHTML = messagesData.recordFound+": "+(totalLines.value-1)+" -- "+messagesData.searchDuration+": "+minutes+" "+messagesData.minutes+" "+seconds+" "+messagesData.seconds;
	       }else {
	         footer.innerHTML = messagesData.recordFound+": "+(totalLines.value-1)+" -- "+messagesData.searchDuration+": "+seconds+" "+messagesData.seconds;
	       }
	       
	       footer.innerHTML = footer.innerHTML + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*" + messagesData.maxrecordsdisplayed.replace('{0}', maxData);
	     }else {
	       var footer = document.getElementById("footer");
	       footer.innerHTML ="";
	     }
	   }else {
	     var footer = document.getElementById("footer");
	     footer.innerHTML ="&nbsp;";
	   }
	}

function validateSearchForm() {
	return true;
}

function initializeGrid(){
    beangrid = new dhtmlXGridObject('listManagementViewBean');
    initGridWithConfig(beangrid,config,-1,true,true);
    if( typeof( jsonMainData ) != 'undefined' ) {
        beangrid.parse(jsonMainData,"json");
    }
    if ($v("totalLines") == 1) {

    }else {

    }
    /*It is important to call this after all the divs have been turned on or off.*/
    //setResultFrameSize();
}


function rowChanged(rowId) {
	if(cellValue(rowId,'isAddLine') != 'true')
		beangrid.cellById(rowId, beangrid.getColIndexById("updated")).setValue("true");
}

function submitUpdate() {
	
	if(validTreshold())
	{
	 $("uAction").value = 'updateSpecificList';
	 parent.showPleaseWait();
	 beangrid.parentFormOnSubmit();
	 document.genericForm.submit();
	}
}

function validTreshold() {
	
	var err = '';
	var rowsNum = beangrid.getRowsNum();
	for(var i = 1; i < rowsNum + 1; i++)
	{
		if(cellValue(i,'permission') == 'Y')
			{	
				if(thresholdColName1Exists && cellValue(i,'threshold') != '' && !isFloat(cellValue(i,'threshold') ))
					err += messagesData.positivenumber.replace('{0}', ' ' + thresholdColName1 + ' Row ' + i) + '\n';
				if(thresholdColName2Exists && cellValue(i,'threshold2') != '' && !isFloat(cellValue(i,'threshold2') ))
					err += messagesData.positivenumber.replace('{0}', ' ' + thresholdColName2 + ' Row ' + i) + '\n';
				if(thresholdColName3Exists && cellValue(i,'threshold3') != '' && !isFloat(cellValue(i,'threshold3') ))
					err += messagesData.positivenumber.replace('{0}', ' ' + thresholdColName3 + ' Row ' + i) + '\n';
			}
	}
	
	if(err != '')
		{
			alert(err);
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

function addListChemical(){
	children[children.length] = openWinGeneric("casnumbersearchmain.do?fromListManagement=Yes","chemicalcassearch","600","400","yes","80","80");
	parent.showTransitWin(messagesData.pleasewait);
}


function addNewListchemical(casNumber, rptChemical)
{
	parent.stopShowingWait();
	
   $("listManagementViewBean").style["display"] = "";
   
	$('updateResultLink').style.display = "";
	$('addResultLink').style.display = "";
	$('excelResultLink').style.display = "";
	
	$('addResultLinkSolo').style.display = "none";
   
   if(beangrid == null) {
	   initializeGrid(); 
   }  
   
    var ind = beangrid.getRowsNum();  
    var rowid = ind*1+1;
	//var thisrow = beangrid.addRow(rowid,['Y',casNumber,rptChemical,false,document.getElementById("listId").value,document.getElementById("companyId").value,true,'N'],ind);
    var thisrow = beangrid.addRow(rowid,"",ind);
    beangrid.selectRow(beangrid.getRowIndex(rowid),null,false,false);

   
	beangrid.cells(rowid,beangrid.getColIndexById("permission")).setValue('Y');
	beangrid.cells(rowid,beangrid.getColIndexById("casNumber")).setValue(casNumber);
	beangrid.cells(rowid,beangrid.getColIndexById("rptChemical")).setValue(rptChemical);
	beangrid.cells(rowid,beangrid.getColIndexById("ok")).setValue(false);
	beangrid.cells(rowid,beangrid.getColIndexById("listId")).setValue(document.getElementById("listId").value);
	beangrid.cells(rowid,beangrid.getColIndexById("companyId")).setValue(document.getElementById("companyId").value);
	beangrid.cells(rowid,beangrid.getColIndexById("isAddLine")).setValue(true);
	beangrid.cells(rowid,beangrid.getColIndexById("okPermission")).setValue('N');
	
	if(thresholdColName1Exists)
		beangrid.cells(rowid,beangrid.getColIndexById("threshold")).setValue('');
	if(thresholdColName2Exists)
		beangrid.cells(rowid,beangrid.getColIndexById("threshold2")).setValue('');
	if(thresholdColName3Exists)
		beangrid.cells(rowid,beangrid.getColIndexById("threshold3")).setValue('');
	
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
function isClose()
{
	var uAction = $("uAction").value;
	if( uAction != 'updateSpecificList')
		{opener.parent.stopShowingWait();
		closeAllchildren();
		}	
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
 
