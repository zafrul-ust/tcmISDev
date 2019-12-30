var beangrid;

// Global variable: specially useful for right-click
var selectedRowId = null;

// Set this to be false if you don't want the grid width to resize based on window size.
var resizeGridWithWindow = true;

function resultOnLoad()
{/*
 try{
	 if (!showUpdateLinks) //Dont show any update links if the user does not have permissions
	 {
	  parent.document.getElementById("updateResultLink").style["display"] = "none";
	 }
	 else 
	 {
	  parent.document.getElementById("updateResultLink").style["display"] = "";
	 }
 }
 catch(ex){}	 */
	
 totalLines = document.getElementById("totalLines").value;
 
 if (totalLines > 0)
 {
  document.getElementById("logisticsViewBean").style["display"] = "";
  initializeGrid();  
 }  
 else
 {
   document.getElementById("logisticsViewBean").style["display"] = "none";   
 }
 
 // clear selectedRow span after re-search
 // parent.$("selectedRow").innerHTML = "";

 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('logisticsViewBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
    // -1 is recommended for the page with update function
    // -1 is for disable rowSpan and smart rendering, but sorting still works; false will disable rowSpan and sorting but smart rendering is enabled
    // set submitDefault to true: Send the data to the server
    // singleClickEdit: this is for type:"txt",
	initGridWithConfig(beangrid,config,false,true);
	if( typeof( jsonMainData ) != 'undefined' ) {		
		
		beangrid.parse(jsonMainData,"json");
	}
	
	beangrid.attachEvent("onRightClick",selectRightclick);
	beangrid.attachEvent("onRowSelect",doOnRowSelected);
}

function doOnRowSelected(rowId,cellInd) {
	selectedRowId = rowId;

}

function selectRightclick(rowId,cellInd){
	// The right click event needs to call selectRow function.
	doOnRowSelected(rowId,cellInd);
}
