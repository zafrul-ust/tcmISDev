windowCloseOnEsc = true;
var children = new Array();

function resultOnLoadWithGrid(gridConfig) {

    totalLines = document.getElementById("totalLines").value;
    if (totalLines > 0) {
        document.getElementById("cylinderDetailsDiv").style["display"] = "";
        initGridWithGlobal(gridConfig);
    }else {
        document.getElementById("cylinderDetailsDiv").style["display"] = "none";
    }

    /*This dislpays our standard footer message*/
    displayNoSearchSecDuration();
    /*It is important to call this after all the divs have been turned on or off.*/
    setResultSize();
}  //end of method

function selectRow(rowId0,colId0,ee) {
    // to show menu directly
    rightClick = false;
    selectedRowId = rowId0;
    if( ee != null ) {
        if( ee.button != null && ee.button == 2 ) rightClick = true;
        else if( ee.which == 3  ) rightClick = true;
    }
    columnId = beanGrid.getColumnId(colId0);

    //do right mouse click
    if (rightClick) {
        //buildRightClickOption();
    } //end of right mouse click
}   //end of method

function buildRightClickOption() {
    var vitems = new Array();
    vitems[vitems.length] = "text=" + messagesData.manageDocumentsImages + ";url=javascript:openDocumentManager();";
    replaceMenu('rightClickMenu', vitems);
    toggleContextMenu('rightClickMenu');
} //end of method

function openDocumentManager() {
    children[children.length] = openWinGeneric('cylinderdocumentmanager.do?userAction=viewCylinderDocumentManager'+
                                           '&serialNumber='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"serialNumber"))+
                                           '&manufacturerName='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"manufacturerName"))+
                                           '&cylinderTrackingId='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"cylinderTrackingId"))+
                                           '&supplier='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"supplier"))
                                           , 'viewCylinderDocumentManager', '900','350', 'yes');
}

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

function doNothing() {}

function closeAllchildren()
{
    try {
        for(var n=0;n<children.length;n++) {
            try {
            children[n].closeAllchildren();
            } catch(ex){}
        children[n].close();
        }
    } catch(ex){}
    children = new Array();
}