
function initializeGrid(){
	beangrid = new dhtmlXGridObject('beanCollDiv');

// initGridWithConfig(inputGrid,config,rowSpan,submitDefault) require at least first two args.
// initGridWithConfig(inputGrid,config) is the same as initGridWithConfig(inputGrid,config,false,false), 
// means no multiple row span and the default submitsetting is false( readonly page for example.)

	initGridWithConfig(beangrid,config,false,true);
	if( typeof( beanData ) != 'undefined' ) {
		beangrid.parse(beanData,"json");
	}
/*
You can attach event handlers here. E.g.
	beangrid.attachEvent("onRowSelect",selectRow);  */
	//beangrid.attachEvent("onRightClick",selectRightclick);
}

function selectRightclick(rowId, cellInd) {
	beangrid.selectRowById(rowId, null, false, false);
	toggleContextMenu('showInventoryDetail');
}

function viewInventoryDetail() {
	var hub = beangrid.cellById(beangrid.getSelectedRowId(),
			beangrid.getColIndexById("hub")).getValue();

	var itemId = beangrid.cellById(beangrid.getSelectedRowId(),
			beangrid.getColIndexById("itemId")).getValue();

	if ((hub != '') && (itemId != '')) {
		loc = "inventorydetails.do?itemId=" + itemId + "&hub=" + hub;
		openWinGeneric(loc, "inventorydetailspage", "800", "450", "yes", "50","50", "no");
	}
}

function updatePO()
{ 
      $('Action').value = 'update';
	  showPleaseWait();
      beangrid.parentFormOnSubmit(); //prepare grid for data sending
      document.genericForm.submit();
      return true;
}

// this one should be in some common file. Since all involved here are standard vars...
function gridPopupOnLoad()
{
// stopPleaseWait();
 try{
 if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
 {
  $("updateResultLink").style["display"] = "none";
 }
 else
 {
 
  $("updateResultLink").style["display"] = "";
//  $("mainUpdateLinks").style["display"] = "";
 }
 }
 catch(ex)
 {}

 totalLines = $("totalLines").value; 
 if (totalLines > 0)
 { 
  $("beanCollDiv").style["display"] = "";
  initializeGrid();

 }
 else
 {
   $("beanCollDiv").style["display"] = "none";   
 }
 if ( showErrorMessage ) 
 	showErrorMessages();

 // displayNoSearchSecDuration();
 displayGridSearchDuration();

 setResultFrameSize();
}





