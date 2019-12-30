var children = new Array();

var mygrid;

/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;
windowCloseOnEsc = false;

//var selectedRowId;
function selectRow(rowId,cellInd){
//	selectedRowId = rowId;
}

function selectRightclick(rowId,cellInd){
	mygrid.selectRowById(rowId,null,false,false);	
	selectRow(rowId,cellInd);
	toggleContextMenu("poMenu");
}

function closeAllchildren() 
{
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
//	if (document.getElementById("uAction").value != 'new') {
		try {
			for(var n=0;n<children.length;n++) {
				try {
				children[n].closeAllchildren(); 
				} catch(ex){}
			children[n].close();
			}
		} catch(ex){}
		children = new Array(); 
//	} 
}



