var beangrid;
var showUpdateLinks = false;
var resizeGridWithWindow = true;

var inputSize= new Array();
inputSize={"startPageOrder":7};

/*This function is called onload from the page*/
function myResultOnload()
{
 try
 {
 if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
 {
  document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  document.getElementById("updateResultLink").style["display"] = "";
 }
 
 if (showErrorMessage)
 {
  setTimeout('showErrorMeessages()',50); /*Showing error messages if any*/
 }
 } 
 
 catch(ex)
 {}

/*If there is data to be shown initialize the grid*/
 var totalLines = document.getElementById("totalLines").value;
 if (totalLines > 0)
 {
  document.getElementById("UserPageAdminViewBean").style["display"] = "";
  doInitGrid();
 }
 else
 {
   document.getElementById("UserPageAdminViewBean").style["display"] = "none";
 }
 if ( showErrorMessage ) 
	 	showErrorMessages();

 /*This displays our standard footer message*/
 displayNoSearchSecDuration();

 /*It is important to call this after all the divs have been turned on or off.
 * This sets all sizes to be a good fit on the screen.*/
 setResultSize();
}

function doInitGrid(){
	beangrid = new dhtmlXGridObject('UserPageAdminViewBean');

	initGridWithConfig(beangrid,config,false,false);
	if( typeof( jsonMainData ) != 'undefined' ) {		
		beangrid.parse(jsonMainData,"json");
	}	
	beangrid.attachEvent("onRowSelect",doOnRowSelected);
	beangrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
}

/*
 * Grid event OnBeforeSelect function
 * Save the row class of currently 
 * selected row, before class changes.
 */
var saveRowClass = null;
function doOnBeforeSelect(rowId,oldRowId) {	
	if (oldRowId != 0) {
		setRowClass(oldRowId, saveRowClass);		
	}
	saveRowClass = getRowClass(rowId);
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		overrideDefaultSelect(rowId,saveRowClass);
	return true;	
}

/*
 * Grid event onRowSelect function
 * Change the row class of selected row 
 * and process selection.
 */
function doOnRowSelected(rowId,cellInd) {
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		setRowClass(rowId,''+saveRowClass+'Selected');
	var columnId = beangrid.getColumnId(cellInd);	
 	switch (columnId)
 	{
	 	case "bStartPage":
	 		var bCheck = cellValue(rowId,"bStartPage");
	 		//alert("bCheck:"+bCheck);
	 		if(bCheck == 'true')
	 		{ 		   
	 		    beangrid.cellById(rowId,beangrid.getColIndexById("startPage")).setValue("Y");	 			
	 		}
	 		else if(bCheck == 'false')
	 		{
	 			  beangrid.cellById(rowId,beangrid.getColIndexById("startPage")).setValue("N");
	 		}
	 		//alert("aaa:"+cellValue (rowId,"startPage"));
	 		break;	
	 	default:
 	};  	
}

function updatePersonalInfo() {
	document.getElementById('action').value="update";
	showPleaseWait();
	beangrid.parentFormOnSubmit(); //prepare grid for data sending	
	document.genericForm.submit();
	return true;
}

function createXSL() {
		document.getElementById('action').value="createXSL";
		document.genericForm.target='_excel_report_file';
	    openWinGenericExcel('/tcmIS/common/loadingfile.jsp','_excel_report_file','650','600','yes');
		setTimeout("document.genericForm.submit()",300);
}



function showErrorMeessages()
{
  var resulterrorMessages = document.getElementById("errorMessagesAreaBody");
  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.innerHTML = resulterrorMessages.innerHTML;

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";

  var dhxWins = new dhtmlXWindows();
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window("errorWin")) {
  // create window first time
  var errorWin = dhxWins.createWindow("errorWin", 0, 0, 400, 250);
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