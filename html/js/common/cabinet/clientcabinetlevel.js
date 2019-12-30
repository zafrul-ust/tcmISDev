windowCloseOnEsc = true;

function closeThisWindow(uAction) {
	opener.parent.closeTransitWin();
	if (uAction == 'update') {
		opener.parent.search();
	}
}

function resultOnLoad() {
	var totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		// make result area visible if data exist
		document.getElementById("cabinetPartLevelViewBean").style["display"] = "";
		// build the grid for display
		doInitGrid();
	}
	else {
		document.getElementById("cabinetPartLevelViewBean").style["display"] = "none";
	}

	var htotalLines = document.getElementById("htotalLines").value;
	if (htotalLines > 0) {
		// make result area visible if data exist
		document.getElementById("cabinetPartLevelLogViewBean").style["display"] = "";
		// build the grid for display
		doHistoryInitGrid();
	}
	else {
		document.getElementById("cabinetPartLevelLogViewBean").style["display"] = "none";
	}

	resizeGrids();
}

function doInitGrid() {
	updateGrid = new dhtmlXGridObject('cabinetPartLevelViewBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	// Set submitDefault = false here because I copy the values to hidden inputs 
	initGridWithConfig(updateGrid, config, false, false, false);
	if (typeof (jsonMainData) != 'undefined') {
		updateGrid.parse(jsonMainData, "json");
	}

	// mygrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	// mygrid.attachEvent("onRowSelect", selectRow);
	// mygrid.attachEvent("onRightClick", selectRightclick);
}

function doHistoryInitGrid() {
	hgrid = new dhtmlXGridObject('cabinetPartLevelLogViewBean');

	initGridWithConfig(hgrid, hconfig, false, false, false);
	if (typeof (hjsonMainData) != 'undefined') {
		hgrid.parse(hjsonMainData, "json");
	}

	// mygrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	// mygrid.attachEvent("onRowSelect", selectRow);
	// mygrid.attachEvent("onRightClick", selectRightclick);
}


function submitUpdate() {
  var flag = validateForm();
  if(flag) {
	 window.onunload = "";
	 $("uAction").value = 'update';
    $("reorderPoint").value = gridCellValue(updateGrid,1,"reorderPoint");
    $("stockingLevel").value = gridCellValue(updateGrid,1,"stockingLevel");
	 $("reorderQuantity").value = gridCellValue(updateGrid,1,"reorderQuantity");
	 $("kanbanReorderQuantity").value = gridCellValue(updateGrid,1,"kanbanReorderQuantity");
	 $("leadTimeDays").value = gridCellValue(updateGrid,1,"leadTimeDays");
	 $("levelHoldEndDate").value = gridCellValue(updateGrid,1,"levelHoldEndDate");
    $("remarks").value = gridCellValue(updateGrid,1,"remarks");
    document.genericForm.submit();
  }
  return flag;
}


function validateForm() {
	var errorMsg = "";
	var count = 0;
	
    if(gridCellValue(updateGrid,1,"reorderPoint") == null || gridCellValue(updateGrid,1,"reorderPoint").trim().length == 0) {
      errorMsg += messagesData.reorderPointRequired + "\n";
      count = 1;
    }
    if(!isInteger(gridCellValue(updateGrid,1,"reorderPoint").trim(), true)) {
      errorMsg += messagesData.reorderPointInteger + "\n";
      count = 1;
    }
    if(gridCellValue(updateGrid,1,"stockingLevel") == null || gridCellValue(updateGrid,1,"stockingLevel").trim().length == 0) {
      errorMsg += messagesData.stockingLevelRequired + "\n";
      count = 1;
    }
    if(!isInteger(gridCellValue(updateGrid,1,"stockingLevel").trim(), true)) {
      errorMsg += messagesData.stockingLevelInteger + "\n";
      count = 1;
    }
	 if(gridCellValue(updateGrid,1,"reorderQuantity") != null && gridCellValue(updateGrid,1,"reorderQuantity").trim().length > 0) {
		 if(!isInteger(gridCellValue(updateGrid,1,"reorderQuantity").trim(), true)) {
			errorMsg += messagesData.reorderQuantityInteger + "\n";
			count = 1;
		 }
	 }
	 if(gridCellValue(updateGrid,1,"kanbanReorderQuantity") != null && gridCellValue(updateGrid,1,"kanbanReorderQuantity").trim().length > 0) {
		 if(!isInteger(gridCellValue(updateGrid,1,"kanbanReorderQuantity").trim(), true)) {
			errorMsg += messagesData.kanbanReorderQuantityInteger + "\n";
			count = 1;
		 }
	 }
	 if(gridCellValue(updateGrid,1,"leadTimeDays") == null || gridCellValue(updateGrid,1,"leadTimeDays").trim().length == 0) {
      errorMsg += messagesData.leadTimeDaysRequired + "\n";
      count = 1;
    }
    if(!isInteger(gridCellValue(updateGrid,1,"leadTimeDays").trim(), true)) {
      errorMsg += messagesData.leadTimeDaysInteger + "\n";
      count = 1;
    }
    if(parseInt(gridCellValue(updateGrid,1,"reorderPoint").trim()) > parseInt(gridCellValue(updateGrid,1,"stockingLevel").trim())) {
      errorMsg += messagesData.reorderPointLessThanStockingLevel + "\n";
      count = 1;
    }
	 if (parseInt(gridCellValue(updateGrid,1,"reorderPoint").trim()) == 0 && parseInt(gridCellValue(updateGrid,1,"stockingLevel").trim()) != 0) {
		errorMsg += messagesData.reorderPointGreaterThanZero + "\n";
		count = 1;
	 }


	 if(!isFormChanged()) {
      errorMsg += messagesData.noChange + "\n";
      count = 1;
    }  
    
  if(count == 1) {
  	alert(errorMsg);
  	return false;
  }
  
  return true;
}

function isFormChanged() {
  if(gridCellValue(updateGrid,1,"reorderPoint").trim() != gridCellValue(updateGrid,1,"oldReorderPoint")) {
    return true;
  }
  if(gridCellValue(updateGrid,1,"stockingLevel").trim() != gridCellValue(updateGrid,1,"oldStockingLevel")) {
    return true;
  }
  if(gridCellValue(updateGrid,1,"reorderQuantity").trim() != gridCellValue(updateGrid,1,"oldReorderQuantity")) {
    return true;
  }
  if(gridCellValue(updateGrid,1,"kanbanReorderQuantity").trim() != gridCellValue(updateGrid,1,"oldKanbanReorderQuantity")) {
    return true;
  }
  if(gridCellValue(updateGrid,1,"leadTimeDays").trim() != gridCellValue(updateGrid,1,"oldLeadTimeDays")) {
    return true;
  }
  if(gridCellValue(updateGrid,1,"levelHoldEndDate").trim() != gridCellValue(updateGrid,1,"oldLevelHoldEndDate")) {
	return true;
  }
  return false;
}

var searchSectionHeight=0;
var resizeGridWithWindow=true; /*If this is set to false, I don't change the width of the result table upon resize.*/
var layouInitialized=false; /*This gets set to true after the layout is initialized. This is to load the layout in tabs.*/
var lastWindowWidth=0;
var myWidth = 0;
var myHeight = 0;

function loadLayoutWin3E(pageName)
{
  setWindowSizes();
  var searchFrameHeight=0;
  searchFrameHeight = window.document.getElementById("searchFrameDiv").offsetHeight;

  var dhxWins = new dhtmlXWindows();
  var win = dhxWins.createWindow(pageName, 0, 0, 800, 600);
  win.setText(pageName);
  win.clearIcon();  // hide icon
  win.denyResize(); // deny resizing
  win.denyMove();   // deny moving
  win.denyPark();   // deny parking
  win.button("close").disable(); // disable "close" button
  win.setToFullScreen(true);
  win.hideHeader();

  dhxLayout = new dhtmlXLayoutObject(win, "3E");
  dhxLayout.cells("a").attachObject("searchFrameDiv");
  dhxLayout.cells("b").attachObject("resultFrameDiv1");
  dhxLayout.cells("c").attachObject("resultFrameDiv2");
  dhxLayout.cells("a").hideHeader();
  dhxLayout.cells("b").hideHeader();
  dhxLayout.cells("c").hideHeader();

  dhxLayout.cells("a").setHeight(searchFrameHeight+15);
  dhxLayout.cells("a").setText("");
     
  dhxLayout.cells("b").setHeight(170);
  dhxLayout.cells("c").setHeight(myHeight-searchFrameHeight-200);

  // attaching onExpand event handler
  dhxLayout.attachEvent("onExpand", function(){
        resizeGrids();
  });
  // attaching onCollapse event handler
   dhxLayout.attachEvent("onCollapse", function(){
        resizeGrids();
  });

  // attaching onPanelResizeFinish event handler
  dhxLayout.attachEvent("onPanelResizeFinish", function(){
        resizeGrids();
  });      
}

function resizeGrids() {
	setTimeout('setWindowSizes();',0);
	setTimeout('setGridHeight();',0);
	setTimeout('setGridWidth();',0);
}

function setGridHeight()
{
  try
  {
   var id=updateGrid.entBox.id;
   var griDiv = $(id);
   griDiv.style.height = dhxLayout.cells("b").getHeight()-70 + "px";
   updateGrid.setSizes();  
  }
  catch(ex)
  {
      //alert("THis means the grid was not initialized");
  }
  try
  {
   var id=hgrid.entBox.id;
   var griDiv = $(id);
   griDiv.style.height = myHeight - dhxLayout.cells("a").getHeight() - dhxLayout.cells("b").getHeight() - 60 + "px";
   hgrid.setSizes(); 
  }
  catch(ex)
  {
      //alert("THis means the grid was not initialized");
  }
}


function  setGridWidth() {
   	   setWindowSizes(); 		
  	   if (_isIE)
		   myWidth -= 25;
	  
	    document.getElementById('resultsMaskTable1').width = myWidth-10;
        document.getElementById('resultsMaskTable2').width = myWidth-10;
        $('cabinetPartLevelViewBean').style.width = myWidth-10 +"px"; 
        $('cabinetPartLevelLogViewBean').style.width = myWidth-10 +"px"; 

  		try
  		{
   			var id=updateGrid.entBox.id;
   			var griDiv = $(id);
   			griDiv.style.width = myWidth - 10+"px"; 
   			reSizeCoLumnWidths(updateGrid); 
  		}
  		catch(ex)
  		{
 		     //alert("THis means the grid was not initialized");
  		}
  		lastWindowWidth = myWidth;
  		try
  		{
   			var id=hgrid.entBox.id;
   			var griDiv = $(id);
   			if (_isIE)
   				griDiv.style.width = myWidth - 30 + "px"; 
   			else
   				griDiv.style.width = myWidth - 10 + "px"; 
   			reSizeCoLumnWidths(hgrid); 
  		}
  		catch(ex)
  		{
 		     //alert("THis means the grid was not initialized");
  		}

}

  
