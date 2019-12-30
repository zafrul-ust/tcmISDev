var mygrid;

// Global variable: specially useful for right-click
var selectedRowId = null;

// Set this to be false if you don't want the grid width to resize based on window size.
var resizeGridWithWindow = true;
 
// Allow defferent permissions for defferent columns   

var selectedRowId = null;

/*This is really the same as before. Except now there is a call to initialize the grid.*/
function resultOnLoad()
{
 try
 {
	 if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
	 	 parent.document.getElementById("updateResultLink").style["display"] = "none";
	 else
	 	 parent.document.getElementById("updateResultLink").style["display"] = "";
 }
 catch(ex)
 {}

 var totalLines = document.getElementById("totalLines").value;
 if (totalLines > 0)
 {
   // make result area visible if data exist
   document.getElementById("receivingReportViewBean").style["display"] = "";
   // build the grid for display
   doInitGrid();
 }  
 else
 {
   document.getElementById("receivingReportViewBean").style["display"] = "none";
 }

/*This displays our standard footer message*/
 displayGridSearchDuration();
    
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}

function doInitGrid(){
	mygrid = new dhtmlXGridObject('receivingReportViewBean');
	
    // initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
    // if rowSpan == true, sorting and smart rendering won't work; if rowSpan == false, sorting and smart rendering will work
    // rowSpan == -1 is recommended for the page with update function
    // -1 is for disable rowSpan and smart rendering, but sorting still works; false will disable rowSpan and sorting but smart rendering is enabled
    // set submitDefault to true: Send the data to the server
    // singleClickEdit: this is for type:"txt",
	initGridWithConfig(mygrid,config,-1,true,true); 
	if( typeof( jsonMainData ) != 'undefined' ) {		
		mygrid.parse(jsonMainData,"json");
	}	
	
	// set all kinds of event for the grid. refer to http://www.dhtmlx.com for more events
	mygrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	mygrid.attachEvent("onRowSelect",selectRow);
	mygrid.attachEvent("onRightClick",selectRightclick);
}  

function selectRightclick(rowId,cellInd){
	
	mygrid.selectRowById(rowId,null,false,false);
	selectRow(rowId,cellInd); // The right click event needs to call selectRow function.
	doOnRowSelected(rowId,cellInd);
    toggleContextMenu('rightClickMenu');
}

function selectRow(rowId,cellInd) {	
	selectedRowId = rowId; // set global variable, selectedRowId, here for later use, eg. right click, etc
// Use onChange to do the validation
// This is just an example what we can do here
/*	
	var okValue=  cellValue(rowId,"okDoUpdate");	
	var columnId = mygrid.getColumnId(cellInd);

 	switch (columnId)
 	{
	  case "okDoUpdate": 
	 	if( okValue != "true" ) // If a hchstatus checkbox is checked, its value equals to String "true".
				return;
		else
			validateLine(rowId);
 		break;
 	  default:   
 	};  	  */
}



var saveRowClass = null;
function doOnBeforeSelect(rowId,oldRowId) {
	// set the color for previous row
	if (oldRowId != 0) {
		setRowClass(oldRowId, saveRowClass);		
	}
	saveRowClass = getRowClass(rowId);
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		overrideDefaultSelect(rowId,saveRowClass);
   		
	return true;	
}

function doOnRowSelected(rowId,cellInd) {
	// set the color for selected row
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		setRowClass(rowId,''+saveRowClass+'Selected');
		
	selectedRowId = rowId;
	
	var poLineDisplay = mygrid.cellById(rowId, mygrid.getColIndexById("poLineDisplay")).getValue();
	if (showUpdateLinks)
	{ 
		 parent.$("selectedRow").innerHTML = " | <a href=\"#\" onclick=call('openHaasPO'); return false;>"+messagesData.open+" : "+poLineDisplay+"</a>";
	}	
}




