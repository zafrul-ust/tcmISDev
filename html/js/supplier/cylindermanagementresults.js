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

function userHasLocation() {
    var result = false;
    var locationArr = parent.altLocation[cellValue(beanGrid.getSelectedRowId(),"supplier")];
    if( locationArr != null ) {
        var selectedRowLocationId = cellValue(beanGrid.getSelectedRowId(),"locationId");
        for (var i = 0; i < locationArr.length; i++) {
            if (locationArr[i].id == selectedRowLocationId) {
                result = true;
                break;
            }
        }
    }
    return result;
}  //end of method

function buildRightClickOption() {
    var vitems = new Array();
    if (cellValue(beanGrid.getSelectedRowId(),"cylinderStatus") != "Out" && cellValue(beanGrid.getSelectedRowId(),"cylinderStatus") != "Disposed" && userHasLocation()) {
        vitems[vitems.length] = "text=" + messagesData.editCylinder + ";url=javascript:submitEditCylinder();";
        vitems[vitems.length] = "text=" + messagesData.updateRefurbSubcategory + ";url=javascript:openEditCategory();";
    }
    vitems[vitems.length] = "text=" + messagesData.viewHistory + ";url=javascript:openViewHistory();";
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

function openViewHistory() {
    children[children.length] = openWinGeneric('cylindermanagementresults.do?userAction=viewCylinderDetails'+
                                           '&serialNumber='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"serialNumber"))+
                                           '&manufacturerIdNo='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"manufacturerIdNo"))
                                           , '_viewCylinderDetails', '800','600', 'yes');
}

function openEditCategory() {
    children[children.length] = openWinGeneric('cylindermanagementresults.do?userAction=showEditCategory'+
                                           '&cylinderTrackingId='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"cylinderTrackingId"))+
                                           '&supplier='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"supplier"))+
                                           '&supplierName='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"supplierName"))+
                                           '&serialNumber='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"serialNumber"))+
                                           '&manufacturerIdNo='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"manufacturerIdNo"))+
                                           '&manufacturerName='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"manufacturerName"))+
                                           '&vendorPartNo='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"vendorPartNo"))+
                                           '&refurbCategory='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"refurbCategory"))
                                           , '_newEditCylinderSubcategory', '610','400', 'yes');
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

function submitMainPrintBarcode() {
    var hasPrintData = false;
    //rowId start with 1 not 0 (zero)
    for (var i = 1; i <= beanGrid.getRowsNum(); i++) {
        if (cellValue(i,"printBarcode") == 'true') {
            hasPrintData = true;
            break;
        }
    }
    if (!hasPrintData)
        alert(messagesData.noRowWasSelected);
    else
        alert("Printing Barcode");
}

function submitEditCylinder() {
    children[children.length] = openWinGeneric('cylindermanagementresults.do?userAction=showNewEditCylinder&calledFrom=result'+
                                           '&cylinderTrackingId='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"cylinderTrackingId"))+
                                           '&supplier='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"supplier"))+
                                           '&supplierName='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"supplierName"))+
                                           '&serialNumber='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"serialNumber"))+
                                           '&manufacturerIdNo='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"manufacturerIdNo"))+
                                           '&manufacturerName='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"manufacturerName"))+
                                           '&vendorPartNo='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"vendorPartNo"))+
                                           '&correspondingNsn='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"correspondingNsn"))+
                                           '&refurbCategory='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"refurbCategory"))+
                                           '&conversionGroup='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"conversionGroup"))+
                                           '&cylinderStatus='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"cylinderStatus"))+
                                           '&cylinderConditionCode='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"cylinderConditionCode"))+
                                           '&returnFromLocation='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"returnFromLocation"))+
                                           '&lastShippedDodaac='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"lastShippedDodaac"))+
                                           '&latestHydroTestDate='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"latestHydroTestDate"))+
                                           '&status='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"status"))+
                                           '&companyId='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"companyId"))+
                                           '&locationId='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"locationId"))+
                                           '&documentNumber='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"documentNumber"))+
                                           '&notes='+encodeURIComponent(cellValue(beanGrid.getSelectedRowId(),"notes"))
                                           , '_newEditCylinder', '470','500', 'yes');
}