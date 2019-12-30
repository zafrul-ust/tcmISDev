var children = new Array();

/************************************NEW***************************************************/
var beanGrid;
var selectedRowId;
windowCloseOnEsc = true;

/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

function selectRow() {
    // to show menu directly
    rightClick = false;
    rowId0 = arguments[0];
    colId0 = arguments[1];
    ee     = arguments[2];

    selectedRowId = rowId0;
    if( ee != null ) {
        if( ee.button != null && ee.button == 2 ) rightClick = true;
        else if( ee.which == 3  ) rightClick = true;
    }
    
    var ig = cellValue(rowId0,"inventoryGroup");
    var itemid = cellValue(rowId0,"itemId");
    $("inventoryGroup").value = ig;
    $("itemId").value = itemid;

    //do right mouse click
    if (rightClick && colId0 > 2) {
        buildRightClickOption();
        setTimeout('toggleContextMenuToNormal()',100);
    }//end of right mouse click
}   //end of method

function updateShelfLifeStorageTemp(slst) {
	parent.submitSearchForm();
	parent.genericForm.submit();
}

function buildRightClickOption() {
    toggleContextMenu('updateshelflifeMenu');
} //end of method

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
}   //end of method

	function updateShelfLife() {
		selectedRowId = beanGrid.getSelectedRowId();
		var itemId = $v("itemId");
		var inventoryGroup = encodeURIComponent($v("inventoryGroup"));
		//var catPartNo = encodeURIComponent(gridCellValue(beanGrid,selectedRowId,"catPartNo"));
		var hub = $v("hub");
		
		children[children.length] = openWinGeneric(
				'catalogrqceditshelflife.do?itemId='+itemId+'&hub='+hub+'&inventoryGroup='+inventoryGroup+'&caller=CIG'
	                   ,"EditShelfLife","500","360",'yes' );
	}

	function resultOnLoad()
	{	
		 totalLines = document.getElementById("totalLines").value;
		 if (totalLines > 0)
		 {
		  	document.getElementById("updateItemShelfLifeBean").style["display"] = "";
		  	initGridWithGlobal(gridConfig); 
		 }
		 else
		 {
		   document.getElementById("updateItemShelfLifeBean").style["display"] = "none";   
		 }
		 displayGridSearchDuration(); 
		 setResultFrameSize(); 
	}