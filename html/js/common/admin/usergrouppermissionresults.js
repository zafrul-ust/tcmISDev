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
        "userGroupAccess" : true
};

function init() { /*This is to initialize the YUI*/
	this.cfg = new YAHOO.util.Config(this);
	if (this.isSecure) {
		this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
	}
}

function update() {
	document.genericForm.target = '';
	document.getElementById('action').value = 'update';
	parent.showPleaseWait(); // Show "please wait" while updating
	
	if (mygrid != null)
		mygrid.parentFormOnSubmit(); // Got to call this function to send the data from grid back to the server
	
	document.genericForm.submit(); // back to server
}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages() {
	parent.showErrorMessages();
}

function myResultOnload() {
	/*Dont show any update links if the user does not have permissions.
	 *  Remove this section if you don't have any links on the main page*/
	if (!showUpdateLinks) {
		parent.document.getElementById("updateResultLink").style["display"] = "none";
	} else {
		parent.document.getElementById("updateResultLink").style["display"] = "";
	}
	
	parent.document.getElementById("updateResultTreeLink").style["display"] = "none";
	
	var totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		// make result area visible if data exist
		document.getElementById("userGroupAccessInputBean").style["display"] = "";
		// build the grid for display
		doInitGrid();
	} else {
		document.getElementById("userGroupAccessInputBean").style["display"] = "none";
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
	mygrid = new dhtmlXGridObject('userGroupAccessInputBean');

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

function dataChanged(rowId) {
	gridCell(mygrid,rowId,"modified").setValue(true);	   
}

function doCheckAll() {
	var checkAll = document.getElementById('checkAll');
	var totalLines = parseInt(document.getElementById("totalLines").value);
	
	for (var i =1; i <= totalLines; i++) {
		if (gridCell(mygrid,i,"userGroupAccess").getValue() != null) {
			var cid = "userGroupAccess"+i;
			if (!$(cid).disabled && $(cid).checked != checkAll.checked) {
				$(cid).checked = checkAll.checked;
				dataChanged(i);
			}
		}
	}
}