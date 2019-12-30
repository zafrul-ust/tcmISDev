var mygrid;

// Global variable: specially useful for right-click
var rowId = null;

// Set this to be false if you don't want the grid width to resize based on
// window size.
var resizeGridWithWindow = true;

// Allow different permissions for different columns
var multiplePermissions = true;

//Build up the array for the columns which use different permissions
var permissionColumns = new Array();
permissionColumns = {
        "userGroups" : true,
        "checkedAdminDisplay" : true,
        "checkedPublishDisplay" : true,
        "checkedViewDisplay" : true
};

function init() { /*This is to initialize the YUI*/
 this.cfg = new YAHOO.util.Config(this);
 if (this.isSecure)
 {
  this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 }
}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function myResultOnload()
{
 /*Dont show any update links if the user does not have permissions.
 Remove this section if you don't have any links on the main page*/

 if (!showUpdateLinks)
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
 parent.document.getElementById("updateResultTreeLink").style["display"] = "none";
 
	var totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		// make result area visible if data exist
		document.getElementById("companydefusergrouppermsViewBean").style["display"] = "";
		// build the grid for display
		doInitGrid();
	}
	else {
		document.getElementById("companydefusergrouppermsViewBean").style["display"] = "none";
	}

	/* This displays our standard footer message */
	displayGridSearchDuration();

	/*
	 * It is important to call this after all the divs have been turned on
	 * or off.
	 */
	setResultFrameSize();
}

function doInitGrid() {
	mygrid = new dhtmlXGridObject('companydefusergrouppermsViewBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	// if rowSpan == true, sorting and smart rendering won't work; if
	// rowSpan == false, sorting and smart rendering will work
	// rowSpan == -1 is recommended for the page with update function
	// -1 is for disable rowSpan and smart rendering, but sorting still
	// works; false will disable rowSpan and sorting but smart rendering is
	// enabled
	// set submitDefault to true: Send the data to the server
	// singleClickEdit: this is for type:"txt",
	initGridWithConfig(mygrid, config, -1, true, true);
	if (typeof (jsonMainData) != 'undefined') {
		mygrid.parse(jsonMainData, "json");
	}

	// set all kinds of event for the grid. refer to http://www.dhtmlx.com
	// for more events
	// mygrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	mygrid.attachEvent("onRowSelect", selectRow);
	mygrid.attachEvent("onRightClick", selectRightclick);
}

function selectRightclick(rowId, cellInd) {

	mygrid.selectRowById(rowId, null, false, false);
	selectRow(rowId, cellInd); // The right click event needs to call
					// selectRow function.
}

function selectRow(rowId, cellInd) {
	//this.rowId = rowId; // set global variable, selectedRowId, here for
				// later use, eg. right click, etc
	// Use onChange to do the validation
	// This is just an example what we can do here
	/*
	 * var okValue= cellValue(rowId,"okDoUpdate"); var columnId =
	 * mygrid.getColumnId(cellInd);
	 * 
	 * switch (columnId) { case "okDoUpdate": if( okValue != "true" ) // If
	 * a hchstatus checkbox is checked, its value equals to String "true".
	 * return; else validateLine(rowId); break; default: };
	 */

}

function dataAdminChanged(rowId) {
  mygrid.cells(rowId,mygrid.getColIndexById("modified")).setValue("modified");
  if(cellValue(rowId,"checkedAdminDisplay") == 'true') {
    mygrid.cells(rowId,mygrid.getColIndexById("userGroupAdmin")).setValue("Y");
  }else {
    mygrid.cells(rowId,mygrid.getColIndexById("userGroupAdmin")).setValue("N");
  }
}

function dataPublishChanged(rowId) {
  mygrid.cells(rowId,mygrid.getColIndexById("modified")).setValue("modified");
  if(cellValue(rowId,"checkedPublishDisplay") == 'true') {
    mygrid.cells(rowId,mygrid.getColIndexById("userGroupPublish")).setValue("Y");
  }else {
    mygrid.cells(rowId,mygrid.getColIndexById("userGroupPublish")).setValue("N");
  }
}

function dataViewChanged(rowId) {
  mygrid.cells(rowId,mygrid.getColIndexById("modified")).setValue("modified");
  if(cellValue(rowId,"checkedViewDisplay") == 'true') {
    mygrid.cells(rowId,mygrid.getColIndexById("userGroupAccess")).setValue("Y");
  }else {
    mygrid.cells(rowId,mygrid.getColIndexById("userGroupAccess")).setValue("N");
  }
}

function checkAdminAll() {
	if(document.getElementById('checkAdminAllBox').checked) {
		for (var i = 0; i < mygrid.getRowsNum(); i++) {
			var tmpRowId = mygrid.getRowId(i);
			if(cellValue(tmpRowId,"checkedAdminDisplayPermission") == 'Y') {
				if (!document.getElementById('checkedAdminDisplay'+tmpRowId).checked) {
					document.getElementById('checkedAdminDisplay'+tmpRowId).checked = true;
					mygrid.cells(tmpRowId,mygrid.getColIndexById("userGroupAdmin")).setValue("Y");
					mygrid.cells(tmpRowId,mygrid.getColIndexById("modified")).setValue("modified");
				}
			}
		}
	}else {
		for (var i = 0; i < mygrid.getRowsNum(); i++) {
			var tmpRowId = mygrid.getRowId(i);
			if(cellValue(tmpRowId,"checkedAdminDisplayPermission") == 'Y') {
				if (document.getElementById('checkedAdminDisplay'+tmpRowId).checked) {
					document.getElementById('checkedAdminDisplay'+tmpRowId).checked = false;
					mygrid.cells(tmpRowId,mygrid.getColIndexById("userGroupAdmin")).setValue("N");
					mygrid.cells(tmpRowId,mygrid.getColIndexById("modified")).setValue("modified");
				}
			}
		}
	}
}

function checkPublishAll() {
	if(document.getElementById('checkPublishAllBox').checked) {
		for (var i = 0; i < mygrid.getRowsNum(); i++) {
			var tmpRowId = mygrid.getRowId(i);
			if(cellValue(tmpRowId,"checkedPublishDisplayPermission") == 'Y') {
				if (!document.getElementById('checkedPublishDisplay'+tmpRowId).checked) {
					document.getElementById('checkedPublishDisplay'+tmpRowId).checked = true;
					mygrid.cells(tmpRowId,mygrid.getColIndexById("userGroupPublish")).setValue("Y");
					mygrid.cells(tmpRowId,mygrid.getColIndexById("modified")).setValue("modified");
				}
			}
		}
	}else {
		for (var i = 0; i < mygrid.getRowsNum(); i++) {
			var tmpRowId = mygrid.getRowId(i);
			if(cellValue(tmpRowId,"checkedPublishDisplayPermission") == 'Y') {
				if (document.getElementById('checkedPublishDisplay'+tmpRowId).checked) {
					document.getElementById('checkedPublishDisplay'+tmpRowId).checked = false;
					mygrid.cells(tmpRowId,mygrid.getColIndexById("userGroupPublish")).setValue("N");
					mygrid.cells(tmpRowId,mygrid.getColIndexById("modified")).setValue("modified");
				}
			}
		}
	}
}

function checkViewAll() {
	var totalLines = mygrid.getRowsNum();
	if(document.getElementById('checkViewAllBox').checked) {
		for (var i = 0; i < mygrid.getRowsNum(); i++) {
			var tmpRowId = mygrid.getRowId(i);
			if(cellValue(tmpRowId,"checkedViewDisplayPermission") == 'Y') {
				if (!document.getElementById('checkedViewDisplay'+tmpRowId).checked) {
					document.getElementById('checkedViewDisplay'+tmpRowId).checked = true;
					mygrid.cells(tmpRowId,mygrid.getColIndexById("userGroupAccess")).setValue("Y");
					mygrid.cells(tmpRowId,mygrid.getColIndexById("modified")).setValue("modified");
				}
			}
		}
	}else {
		for (var i = 0; i < mygrid.getRowsNum(); i++) {
			var tmpRowId = mygrid.getRowId(i);
			if(cellValue(tmpRowId,"checkedViewDisplayPermission") == 'Y') {
				if (document.getElementById('checkedViewDisplay'+tmpRowId).checked) {
					document.getElementById('checkedViewDisplay'+tmpRowId).checked = false;
					mygrid.cells(tmpRowId,mygrid.getColIndexById("userGroupAccess")).setValue("N");
					mygrid.cells(tmpRowId,mygrid.getColIndexById("modified")).setValue("modified");
				}
			}
		}
	}
}