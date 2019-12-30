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
    if (cellValue(beanGrid.getSelectedRowId(),"facilityId") != '')
        vitems[vitems.length] = "text=" + messagesData.editUpdateEdiError + ";url=javascript:openEdiUpdateError();";
    else
        vitems[vitems.length] = "text=;url=javascript:doNothing();";

    replaceMenu('rightClickMenu', vitems);
    toggleContextMenu('rightClickMenu');
} //end of method

function openEdiUpdateError() {
    //showTransitWin();
    children[children.length] = openWinGeneric('ediordertrackingresults.do?userAction=openEditEdiError'+
                                               '&companyId='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"companyId"))+
                                               '&companyName='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"companyName"))+
                                               '&facilityId='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"facilityId"))+
                                               '&facilityName='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"facilityName"))+
                                               '&catalogCompanyId='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"catalogCompanyId"))+
                                               '&catalogId='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"catalogId"))+
                                               '&partShortDesc='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"partShortDesc"))+
                                               '&statusDetail='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"statusDetail"))+
                                               '&customerPoNo='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"customerPoNo"))+
                                               '&customerPoLineNo='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"customerPoLineNo"))+
                                               '&quantity='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"quantity"))+
                                               '&orderPartUom='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"orderPartUom"))+
                                               '&shiptoCity='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"shiptoCity"))+
                                               '&catPartNo='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"catPartNo")), '_editEdiError', '650','440', 'yes');
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

function submitMainUpdate() {
	if (validateForm()) {
		document.getElementById("userAction").value = 'update';
		parent.showPleaseWait();

		if (beanGrid != null) {
			beanGrid.parentFormOnSubmit();
		}
        parent.$("startSearchTime").value = new Date().getTime();
        /* Submit the form in the result frame */
		document.genericForm.submit();
	}
}

function validateForm() {
    var result = true;
    return result;
}

var dhxFreezeWins = null;
function initializeDhxWins() {
if (dhxFreezeWins == null) {
  dhxFreezeWins = new dhtmlXWindows();
  dhxFreezeWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
 }
}

function showTransitWin(message) {
	if (message != null && message.length > 0) {
		$("transitLabel").innerHTML = message;
	}else {
		$("transitLabel").innerHTML = messagesData.pleasewait;
	}

	var transitDialogWin = document.getElementById("transitDialogWin");
	transitDialogWin.style.display="";

	initializeDhxWins();
	if (!dhxFreezeWins.window("transitDialogWin")) {
		// create window first time
		transitWin = dhxFreezeWins.createWindow("transitDialogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDialogWin");
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
		transitWin.setModal(true);
		dhxFreezeWins.window("transitDialogWin").show();
	}
}

function closeTransitWin() {
if (dhxFreezeWins != null) {
 if (dhxFreezeWins.window("transitDialogWin")) {
   dhxFreezeWins.window("transitDialogWin").setModal(false);
   dhxFreezeWins.window("transitDialogWin").hide();
  }
 }
}