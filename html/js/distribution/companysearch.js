var selectedrow=null;
var currentId = '';
var currentName = '';
var currentShortName = '';
windowCloseOnEsc = true;

function selectRow()
{  
// to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];
//
   if( ee != null ) {
   		if( ee.button != null && ee.button == 2 ) rightClick = true;
   		else if( ee.which == 3  ) rightClick = true;
   }
   haasGrid.selectRowById(rowId0,null,false,false);	
 //  selectRow(rowId,cellInd);

//   if( !rightClick ) return;
	
   selectedrow = rowId0;
   currentId = cellValue(rowId0,"companyId")
   currentName = cellValue(rowId0,"companyName");
   currentShortName = cellValue(rowId0,"companyShortName");
   var selectedUser = parent.document.getElementById("updateResultLink");
   selectedUser.innerHTML = '<a href="#" onclick="call(\'selectData\'); return false;">'+messagesData.selectedRowMsg+' : '+ currentId +'</a>';
   
}

function selectData() {

 try {
 if( parent.opener.companyChanged ) {
  	parent.opener.companyChanged(currentId,currentShortName,currentName);
  }
  } catch(exx) {}
  parent.close();

}