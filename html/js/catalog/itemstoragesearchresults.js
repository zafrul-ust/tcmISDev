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
		buildRightClickOption(colId0);
	} //end of right mouse click
}   //end of method


function buildRightClickOption(colId0) {
	var vitems = new Array();
	vitems[vitems.length] = "text=" + messagesData.editItemInfo + ";url=javascript:editItemInfo();";
	replaceMenu('rightClickMenu', vitems);
	toggleContextMenu('rightClickMenu');
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

function doNothing() {}

function editItemInfo() {
    var itemId = cellValue(beanGrid.getSelectedRowId(),"itemId");
    var loc = "/tcmIS/hub/shippinginfo.do?uAction=search&itemId="+itemId;
    try{
        parent.parent.openIFrame("shippingInfo"+itemId,loc,""+messagesData.shippingInfo+" "+itemId,"","N");
    }catch (ex) {
        openWinGeneric(loc,"shippingInfo"+itemId+"","800","600","yes","50","50");
    }
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
		$("transitLabel").innerHTML = messagesData.pleaseWait;
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