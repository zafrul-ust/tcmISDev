var beangrid;

var resizeGridWithWindow = true;

function resultOnLoad()
{
 totalLines = document.getElementById("totalLines").value;
 
 if (totalLines > 0)
 {
  document.getElementById("searchPersonnelViewBean").style["display"] = "";
  
  initializeGrid();  
 }  
 else
 {
   document.getElementById("searchPersonnelViewBean").style["display"] = "none";   
 }

 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('searchPersonnelViewBean');

	initGridWithConfig(beangrid,config,false,false);
	if( typeof( jsonMainData ) != 'undefined' ) {		
		beangrid.parse(jsonMainData,"json");
	}	
	beangrid.attachEvent("onRowSelect",doOnRowSelected);
	beangrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
}

function validateForm()
{
	return true;
}

/*
 * Grid event OnBeforeSelect function
 * Save the row class of currently 
 * selected row, before class changes.
 */
var saveRowClass = null;
function doOnBeforeSelect(rowId,oldRowId) {	
	if (oldRowId != 0) {
		setRowClass(oldRowId, saveRowClass);		
	}
	saveRowClass = getRowClass(rowId);
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		overrideDefaultSelect(rowId,saveRowClass);
	return true;	
}

/*
 * Grid event onRowSelect function
 * Change the row class of selected row 
 * and process selection.
 */
function doOnRowSelected(rowId,cellInd) {
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		setRowClass(rowId,''+saveRowClass+'Selected');
	// Get the parent selected user
	var selectedUser = parent.document.getElementById("selectedUser");
	// Get the value of current user from selected grid row
	var currentUserName = cellValue(rowId,'fullName');
	parent.returnSelectedUserName = currentUserName;
	parent.returnSelectedUserId = cellValue(rowId,'hiddenUserId');
	selectedUser.innerHTML = '| <a href="#" onclick="selectedUser(); return false;">'+messagesData.selectedUser+' : '+currentUserName+'</a>';
    parent.displayElementId = document.getElementById("displayElementId").value;
    parent.valueElementId = document.getElementById("valueElementId").value;
}

windowCloseOnEsc = true;
