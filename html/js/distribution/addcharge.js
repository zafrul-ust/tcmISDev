
windowCloseOnEsc = true;

var selectCurrency="USD";
var selectedRowId = null;

function removeNewFeesLine() {
	haasGrid.deleteRow(selectedRowId);
	delete( rowids[selectedRowId] ) ;
	if( haasGrid.getRowsNum() == 0 ) 
		$('newFeeRemoveLine').style["display"] = "none";
	return ;
}

function getTotal()
{
	var rowsNum = haasGrid.getRowsNum()*1;
    var total = 0;
    try {
  	if ( window.opener.extraChargeChanged )
  	{
  	  if ( $v("source") == 'addchargeheader') {
  	    for (var p = 1 ; p < (rowsNum+1) ; p ++)
  		{
	  		total = total + cellValue(p,"price")*1;
  		}
  		window.opener.extraChargeChanged(total.toFixed(2),'Y');
  	  }
  	  else {
  	    for (var p = 1; p < (rowsNum+1) ; p ++)
  		{
              var chargeRecurrence = cellValue(p,"chargeRecurrence");
              var lineQuantity = $v('lineQuantity');
              if (chargeRecurrence == "per unit" )
              {
                total = total + cellValue(p,"price")*1*(lineQuantity*1);
              }
              else
              {
                total = total + cellValue(p,"price")*1;
              }
          }
  		window.opener.extraChargeChanged(total.toFixed(2),'N');
	  }
	}
    }catch(ex){}
}

function selectRow(rowId0, colId0) {
	rightClick = false;
	ee = arguments[2];

	oldRowId = rowId0;

	if (ee != null) {
		if (ee.button != null && ee.button == 2)
			rightClick = true;
		else
			if (ee.which == 3)
				rightClick = true;
	}
	//   if( colId0 == haasGrid.getColIndexById("itemIdDisplay") && 
	//		   cellValue( rowId0,"itemIdDisplay" ) == messagesData.pleaseselectorenter )
	//	   cell( rowId0,"itemIdDisplay" ).setValue('');
	selectedRowId = rowId0;
	if (cellValue(selectedRowId, "permission") == "Y")
		$('newFeeRemoveLine').style["display"] = "";
	else
		$('newFeeRemoveLine').style["display"] = "none";
	if (!rightClick)
		return true;
	haasGrid.selectRowById(rowId0, null, false, false);
	if (cellValue(selectedRowId, "permission") != "Y")
		return true;
	toggleContextMenu('deleteLine');

} //end of method

