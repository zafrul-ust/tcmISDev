/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;
var map = null;
var map2 = null;
//var prerow = null;
var preRowId = null;
var preClass = null;
var lineMap = new Array();
var lineMap2 = new Array();
var lineMap3 = new Array();
var lineIdMap = new Array();
var lineIdMap2 = new Array();

var selectedRowId = null;
var selectedColId = null;

function submitUpdate() {
	parent.showTransitWin();
	//reset start search time
	var now = new Date();
	parent.$("startSearchTime").value = now.getTime();
	//prepare grid for data sending
	mygrid.parentFormOnSubmit();
	$("uAction").value = 'update';
	document.genericForm.submit();
}

function fixRowColor(thisrow,rowind){
	var cname = "ev_haas rowselected";
	thisrow.className = cname;
}

function selectRow()
{
	// to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];
   if( ee != null ) {
   	if( ee.button != null && ee.button == 2 ) rightClick = true;
   	else if( ee.which == 3  ) rightClick = true;
   }

	if( preRowId != null) {
		if(lineMap3[preRowId-1] == 0 )
			preClass="odd_haas";
		else
			preClass="ev_haas";
		mymap = lineIdMap[preRowId-1];
		for(var j = 0;j < mymap.length; j++) {
		    var rrid = mymap[j];
			mygrid.rowsAr[ rrid*1+1 ].className = preClass;
		}
	}

	mymap = lineIdMap[rowId0-1];
	for(var j = 0;j < mymap.length; j++) {
	    var rrid = mymap[j];
		fixRowColor( mygrid.rowsAr[ rrid*1+1 ] );
	}

   preRowId      = rowId0;
   selectedRowId = rowId0;
   selectedColId = colId0;

	//stop here if it's not a right mouse click
	if( !rightClick ) return;
	var menuItems = new Array();
	if (mygrid.cells(selectedRowId,mygrid.getColIndexById("permission")).getValue() == 'Y') {
		menuItems[menuItems.length] = 'text='+messagesData.addApprover+';url=javascript:searchForApprover();';
	}else {
		menuItems[menuItems.length] = '';
	}
	replaceMenu('myRightClickMenu',menuItems);
	toggleContextMenu('myRightClickMenu');
}  //end of method

// all same level, at least one item
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
}

function doNothing() {
}

function searchForApprover() {
	//parent.showTransitWin();
	loc = "searchpersonnelmain.do?";
   parent.children[parent.children.length] = openWinGeneric(loc,"searchpersonnel","600","450","yes","50","50","no");
}

function activeChanged() {
	mygrid.cells(arguments[0],mygrid.getColIndexById("statusChanged")).setValue('Y');
}

function personnelChanged(pid,fullName,callbackparam) {
	insertDataIntoGrid(pid,fullName);
	submitUpdate();
	//parent.closeTransitWin();
}

function insertDataIntoGrid(personnelId,approverName) {
	var tmpNewLineNumber = mygrid.getRowsNum();
	var previousColorIndex = lineMap3[tmpNewLineNumber-1];
	lineMap[tmpNewLineNumber] = 1;
	var map = new Array();
	var i = 0;
	var ind = mygrid.getRowsNum();
	var rowid = ind*1+1;							//the reason for +1 is because grid id starts with 1 instead of zero
	var thisrow = mygrid.addRow(rowid,"",ind);
	var count = 0;
	mygrid.cells(rowid,count++).setValue('Y');
	mygrid.cells(rowid,count++).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("companyName")).getValue());
	mygrid.cells(rowid,count++).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("facilityName")).getValue());
	mygrid.cells(rowid,count++).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("approvalRole")).getValue());
	mygrid.cells(rowid,count++).setValue(true);
	mygrid.cells(rowid,count++).setValue(approverName);
	mygrid.cells(rowid,count++).setValue(personnelId);
	mygrid.cells(rowid,count++).setValue('Y');
	mygrid.cells(rowid,count++).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("companyId")).getValue());
	mygrid.cells(rowid,count++).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("catalogCompanyId")).getValue());
	mygrid.cells(rowid,count++).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("catalogId")).getValue());
	mygrid.cells(rowid,count++).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("facilityId")).getValue());
	//setting data for merge cells
	lineMap3[(tmpNewLineNumber+i)] = (previousColorIndex+1) % 2;
	map[map.length] = (tmpNewLineNumber+i);
	lineIdMap[(tmpNewLineNumber+i)] = map;
	loadRowSpans();
} //end of method

function resultOnLoad() {
	if ($v("totalLines") > 0) {
		loadRowSpans();
		if ($v("allowUpdate") == 'Y') {
			parent.document.getElementById('updateResultLink').style["display"] = "";
		}else {
			parent.document.getElementById('mainUpdateLinks').style["display"] = "none";
		}
	}else {
		parent.document.getElementById('mainUpdateLinks').style["display"] = "none";
	}
	parent.closeTransitWin();
 }

function loadRowSpans()
{
  for(var i=0;i<mygrid.getRowsNum();i++){
	  try
	  {
		var rowSpan = lineMap[i];
		if( rowSpan == null || rowSpan == 1 || rowSpan == 0 || rowSpan == -1 ) continue;
		mygrid.setRowspan(i+1,0,rowSpan*1);
		mygrid.setRowspan(i+1,1,rowSpan*1);
		mygrid.setRowspan(i+1,2,rowSpan*1);
		mygrid.setRowspan(i+1,3,rowSpan*1);
	  }
	  catch (ex)
	  {
	  //alert("here 269");
	  }
	}

  for(var i=0;i<mygrid.getRowsNum();i++){
		if(lineMap3[i] == 0)
			preClass="odd_haas";
		else
			preClass="ev_haas";
		mygrid.rowsAr[ i+1 ].className = preClass;
  }
} //end of method