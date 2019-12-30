windowCloseOnEsc = true;
resizeGridWithWindow = true;
var beangrid = null;

var multiplePermissions = true;

//Build up the array for the columns which use different permissions
var permissionColumns = new Array();
permissionColumns = {
     "comments" : false
};

function resultOnLoad()
{
	
	totalLines = document.getElementById("totalLines").value; 
	
	if (totalLines > 0)
	{
		try {
			if (!showUpdateLinks) /*
						 * Dont show any update links if the
						 * user does not have permissions
						 */
				{
				parent.document.getElementById("updateResultLink").style["display"] = "none";
				parent.document.getElementById("deleteResultLink").style["display"] = "none";
				}
			else
				{
				parent.document.getElementById("updateResultLink").style["display"] = "";
				parent.document.getElementById("deleteResultLink").style["display"] = "";
				}
		}
		catch (ex) {
		}
		document.getElementById("supplierItemNotesBean").style["display"] = "";
		
		initializeGrid();
		/*if(!showUpdateLinks){
		parent.document.getElementById("addItemSpan").style["display"] = "none";
		}*/
	}  
	else
	{
		document.getElementById("supplierItemNotesBean").style["display"] = "none";   
	}

	/*This dislpays our standard footer message*/
	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();

	if(showUpdateLinks){
		parent.document.getElementById("mainUpdateLinks").style["display"] = "";
		if (totalLines == 0)
		{
			parent.document.getElementById("updateResultLink").style["display"] = "none";
			parent.document.getElementById("deleteResultLink").style["display"] = "none";
			parent.document.getElementById("addItemSpan").style["display"] = "";
	    }
	    else {
	    	parent.document.getElementById("updateResultLink").style["display"] = "";
			parent.document.getElementById("deleteResultLink").style["display"] = "";
			parent.document.getElementById("addItemSpan").style["display"] = "";
	    }
	}
	else  
		parent.document.getElementById("mainUpdateLinks").style["display"] = "none";
	  
}

function initializeGrid(){
   totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0)
	{
		beangrid = new dhtmlXGridObject('supplierItemNotesBean');
		initGridWithConfig(beangrid,config,false,true,true);
		if( typeof( jsonMainData ) != 'undefined' ) {
				beangrid.parse(jsonMainData,"json");
				
	    beangrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	    beangrid.attachEvent("onRightClick",selectRightclick);
	    beangrid.attachEvent("onRowSelect",doOnRowSelected);			
        }
	}
}



/*
 * Grid event OnBeforeSelect function
 * Save the row class of currently 
 * selected row, before class changes.
 */
var saveRowClass = null;
function doOnBeforeSelect(rowId,oldRowId) {
	// set the color for previous row
	if (oldRowId != 0) {
		setRowClass(oldRowId, saveRowClass);		
	}
	saveRowClass = getRowClass(rowId);
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		overrideDefaultSelect(rowId,saveRowClass);
   		
	return true;	
}

function doOnRowSelected(rowId,cellInd) {
	// set the color for selected row
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		setRowClass(rowId,''+saveRowClass+'Selected');
		
	selectedRowId = rowId;
}

function selectRightclick(rowId,cellInd){
	beangrid.selectRowById(rowId,null,false,false);
	// The right click event needs to call selectRow function.
	doOnRowSelected(rowId,cellInd);
	// Show right-click menu
    var permission = cellValue(beangrid.getSelectedRowId(),"permission");
    if (permission == "Y")
    {
        toggleContextMenu('rightClickMenu');
    }
}

function updateExpedite() {
	if(validateForm()) {
		document.genericForm.target = '';
		document.getElementById('uAction').value = 'update';

		parent.showPleaseWait(); // Show "please wait" while
						// updating
		if (beangrid != null)
			beangrid.parentFormOnSubmit(); // Got to call this
							// function to send the
							// data from grid back
							// to the server
		document.genericForm.submit(); // back to server
	}
    else
    	return false;
}

function deleteRow() {
	if(validateForm()) {
		document.genericForm.target = '';
		document.getElementById('uAction').value = 'delete';
	
		parent.showPleaseWait(); // Show "please wait" while
						// updating
		if (beangrid != null)
			beangrid.parentFormOnSubmit(); // Got to call this
							// function to send the
							// data from grid back
							// to the server
		document.genericForm.submit(); // back to server
	}
    else
    	return false;
}

function validateForm() {
	var count = 0;

	for(var i = 1;i <= beangrid.getRowsNum();i++){
		if(cellValue(i,"okDoUpdate")+'' == 'true')	{
			count++;
			break;
		}
	}

	if (count == 0) {
		alert(messagesData.pleaseselectarowforupdate);
		return false;
	}else 
		return true;
}


function addNewComment(NewItemOK) {
	
	if( NewItemOK == 'NewItemOK' )
	{
		var loc = "newsupplieritemnotes.do?uAction=new&opsEntityId="+ $v('opsEntityId');
		openWinGeneric(loc,"addNewCommentWindow","500","300","yes","40","40","no");
	}
	else
	{	
		var itemId = cellValue(beangrid.getSelectedRowId(),"itemId");
		var supplier = cellValue(beangrid.getSelectedRowId(),"supplier");
		var supplierName = cellValue(beangrid.getSelectedRowId(),"supplierName");
		var itemDesc = cellValue(beangrid.getSelectedRowId(),"itemDesc");
				
	    var loc = "newsupplieritemnotes.do?uAction=new&searchMode=is&searchField=itemId&opsEntityId="+ $v('opsEntityId')+
	              "&itemId="+itemId+
	              "&supplier="+supplier+
	              "&supplierName="+encodeURIComponent(supplierName)+
	              "&itemDesc="+encodeURIComponent(itemDesc);

        openWinGeneric(loc,"addNewCommentWindow","500","300","yes","40","40","no");
	}
}

function refreshPage()
{			
	document.genericForm.target='';
	var action = document.getElementById("uAction");
	action.value="search";
	var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	document.genericForm.submit();
	
}


