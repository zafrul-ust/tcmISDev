var children = new Array();

/************************************NEW***************************************************/
var beanGrid;
var selectedRowId;
windowCloseOnEsc = true;

/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

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
        buildRightClickOption();
    } //end of right mouse click
}   //end of method

function buildRightClickOption() {
    var vitems = new Array();
    if (cellValue(beanGrid.getSelectedRowId(),"documentId") != '')
        vitems[vitems.length] = "text=" + messagesData.viewDoc + ";url=javascript:openReceiptDoc();";
    else
        vitems[vitems.length] = "text=;url=javascript:doNothing();";

    replaceMenu('receivingDocMenu', vitems);
    toggleContextMenu('receivingDocMenu');
} //end of method

function openReceiptDoc() {
    var tmpUrl = "";
    if ($v("secureDocViewer") == 'true')
        tmpUrl = "/DocViewer/client/";
    openWinGeneric(tmpUrl + "receiptdocviewer.do?uAction=viewReceiptDoc&documentId="+cellValue(beanGrid.getSelectedRowId(),"documentId")+
                            "&receiptId="+cellValue(beanGrid.getSelectedRowId(),"receiptId")+
                            "&companyId="+cellValue(beanGrid.getSelectedRowId(),"companyId")
        		,'_ShowReceiptDocument','650','600','yes');
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