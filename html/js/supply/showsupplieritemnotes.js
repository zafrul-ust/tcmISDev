// This works only for popup windows and IE. Close the window after clicking Esc key
var windowCloseOnEsc = true; 

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

return true;	
}

function doOnRowSelected(rowId,cellInd) {
// set the color for selected row
selectedRowId = rowId;
}

function selectRightclick(rowId,cellInd){
	beanGrid.selectRowById(rowId,null,false,false);
// The right click event needs to call selectRow function.
doOnRowSelected(rowId,cellInd);
// Show right-click menu

var permission = cellValue(beanGrid.getSelectedRowId(),"permission");
if (permission == "Y")
{
  toggleContextMenu('rightClickMenu');
}
else
{
    toggleContextMenu('contextMenu');
}
}

function addNewComment(NewSupplierOK) {
	
	if( NewSupplierOK == 'NewSupplierOK' )
	{
		var itemId = $v("itemId");
		var supplier = $v("supplier");
		var supplierName = $v("supplierName");
		var itemDesc = $v("itemDesc");
		
		var loc = "newsupplieritemnotes.do?uAction=new&updateSupplier=Y&itemId="+itemId+"&itemDesc="+encodeURIComponent(itemDesc)+
				  "&supplier="+supplier+"&supplierName="+encodeURIComponent(supplierName)+
				  "&opsEntityId="+ $v('opsEntityId');
		openWinGeneric(loc,"addNewCommentWindow","500","300","yes","40","40","no");
	}
	else
	{	
		var itemId = cellValue(beanGrid.getSelectedRowId(),"itemId");
		var supplier = cellValue(beanGrid.getSelectedRowId(),"supplier");
		var supplierName = cellValue(beanGrid.getSelectedRowId(),"supplierName");
		var itemDesc = cellValue(beanGrid.getSelectedRowId(),"itemDesc");
				
	    var loc = "newsupplieritemnotes.do?uAction=new&searchMode=is&searchField=itemId&opsEntityId="+ $v('opsEntityId')+
	              "&itemId="+itemId+
	              "&supplier="+supplier+
	              "&supplierName="+encodeURIComponent(supplierName)+
	              "&itemDesc="+encodeURIComponent(itemDesc);

        openWinGeneric(loc,"addNewCommentWindow","500","300","yes","40","40","no");
	}
}

function checkUpdatePermission(){
	
	totalLines = document.getElementById("totalLines").value; 
/*	if (totalLines > 0)
	{
		if(!showUpdateLinks){
			document.getElementById("addItemSpan").style["display"] = "none";
		}
	}
	if (totalLines == 0)
	{ */
		document.getElementById('mainUpdateLinks').style["display"] = "";	
		if(showUpdateLinks){
			
		    document.getElementById("addItemSpan").style["display"] = "";
		}
	    else{
	    	
			document.getElementById("addItemSpan").style["display"] = "none";
		}
//	}
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






