var beanGrid;
var selectedRowId = null;
var showMsdsUrl= false;

var resizeGridWithWindow = true;

function resultOnLoad() {

  	totalLines = document.getElementById("totalLines").value;
    if (totalLines > 0) {
        document.getElementById("msdsSearchBeanDiv").style["display"] = "";
        try {
	        initializeGrid();
        }
        catch(ex) {}
         
    }else {
        document.getElementById("msdsSearchBeanDiv").style["display"] = "none";
    }
	
	displayGridSearchDuration();
        /*It is important to call this after all the divs have been turned on or off.*/
    setResultFrameSize();
	
}

function initializeGrid(){
	//initialize grid
	beanGrid = new dhtmlXGridObject('msdsSearchBeanDiv');

	initGridWithConfig(beanGrid,config,false,true,true);
	//parse data
	if( typeof( jsonMainData ) != 'undefined' ) {
		beanGrid.parse(jsonMainData,"json");
 	}
 	
 	beanGrid.attachEvent("onRowSelect",selectRow);
 	beanGrid.attachEvent("onRightClick",selectRightclick);
}



function selectRow(rowId,cellInd) {
	selectedRowId = rowId;
	if(showMsdsUrl) {
		showMsds();
		showMsdsUrl = false;
	}
}

function selectRightclick(rowId,cellInd) {
	selectRow(rowId,cellInd);
	toggleContextMenu('rightClickMenu');
} 

function showMsds()
{

  var msdsUrl = beanGrid.cells(selectedRowId,beanGrid.getColIndexById("msdsUrl")).getValue();
   openWinGeneric(msdsUrl,"ViewMSDS","1000","800",'yes' );

}




