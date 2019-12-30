var mygrid;

// Global variable: specially useful for right-click
var selectedRowId = null;

// Set this to be false if you don't want the grid width to resize based on window size.
var resizeGridWithWindow = true;
 
// Allow defferent permissions for defferent columns   
var multiplePermissions = true; 

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
   document.getElementById("printPoListbean").style["display"] = "";
   // build the grid for display
   doInitGrid();
 }  
 else
 {
   document.getElementById("printPoListbean").style["display"] = "none";
 }

/*This displays our standard footer message*/
 displayGridSearchDuration();
    
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}

function doInitGrid(){
	mygrid = new dhtmlXGridObject('printPoListbean');
	
    // initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
    // if rowSpan == true, sorting and smart rendering won't work; if rowSpan == false, sorting and smart rendering will work
    // rowSpan == -1 is recommended for the page with update function
    // -1 is for disable rowSpan and smart rendering, but sorting still works; false will disable rowSpan and sorting but smart rendering is enabled
    // set submitDefault to true: Send the data to the server
    // singleClickEdit: this is for type:"txt",
	initGridWithConfig(mygrid,config,false,true,true); 
	if( typeof( jsonMainData ) != 'undefined' ) {		
		mygrid.parse(jsonMainData,"json");
	}	
	
	// set all kinds of event for the grid. refer to http://www.dhtmlx.com for more events
	mygrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	mygrid.attachEvent("onRowSelect",selectRow);
	mygrid.attachEvent("onRightClick",selectRightclick);
}  

function selectRightclick(rowId,cellInd){
	
	//mygrid.selectRowById(rowId,null,false,false);
	selectRow(rowId,cellInd); // The right click event needs to call selectRow function.
	doOnRowSelected(rowId,cellInd);
	toggleContextMenu('rightClickMenu');
}

function selectRow(rowId,cellInd) {	
	selectedRowId = rowId; // set global variable, selectedRowId, here for later use, eg. right click, etc
// Use onChange to do the validation
// This is just an example what we can do here
	
	//var okValue=  cellValue(rowId,"okDoUpdate");	
/*	var columnId = mygrid.getColumnId(cellInd);

 	switch (columnId)
 	{
	  case "poBpo": 
		  getPOBO();
 		break;
 	  default:   
 	};  	   */
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
	
	//var poLineDisplay = mygrid.cellById(rowId, mygrid.getColIndexById("poLineDisplay")).getValue();
	if (showUpdateLinks)
	{ 
		 parent.$("selectedRow").innerHTML = " | <a href=\"#\" onclick=call('openHaasPO'); return false;>"+messagesData.open+" : "+poLineDisplay+"</a>";
	}	
}


function getCurrentRowVal(name) {
	var value = null;
	value = cellValue(selectedRowId, name);
	return encodeURIComponent(value);
}
function getCurPath() {
	return encodeURIComponent("Item Lookup");
}
function openHaasPO(searchKey) {
	var itemId = getCurrentRowVal('itemId');
	var title = "showPoHistory_" + itemId.replace(/[.]/, "_");
	var loc = "/tcmIS/distribution/showpohistory.do?" +
		"region=" +
		"&curpath=" + getCurPath() +
		"&inventoryGroup=" + $v('inventoryGroup') +
		"&searchKey=" + searchKey +
		"&itemId=" + itemId;
	openWinGeneric(loc, title, "1024", "500", "yes", "50", "50");	
}

var winCount = 0;
function viewDocument()
{
	var poBpo=cellValue(mygrid.getSelectedRowId(),"poBpo");
	var fileUrl=cellValue(mygrid.getSelectedRowId(),"fileUrl");
	var winName="doc"+winCount;
    if (poBpo.length > 0)
    {
    	openWinGeneric(fileUrl,winName,'800','600','yes');	
    	winCount++;
    }
}




