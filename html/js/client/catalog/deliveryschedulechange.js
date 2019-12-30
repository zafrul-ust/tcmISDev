/*Set this to be false if you don't want the grid width to resize based on window size.
* You will also need to set the resultsMaskTable width appropriately in the JSP.*/
var resizeGridWithWindow = true;
var beanGrid;
var selectedRowId;

// Function for updating records or submiting form back to the server
function submitApprove()
{
    /*Set any variables you want to send to the server*/
	document.getElementById('uAction').value = 'approve';
	document.genericForm.target = '_self';
	showPleaseWait();
    /*prepare grid for data sending, this is important to get the data from the grid to the server.*/
    beanGrid.parentFormOnSubmit();
    document.genericForm.submit();
}

// to perform the check all function in the header.
function checkAll(checkboxname)
{
  var checkall = $("chkAllOkDoUpdate");
  var rowsNum = beanGrid.getRowsNum(); // Get the total rows of the grid

  if( checkall.checked ) {
		for(var p = 1 ; p < (rowsNum*1+1) ; p ++) {
			var cid = checkboxname+p;
			if( ! $(cid).disabled && !$(cid).checked) {
				$(cid).checked = true;
				updateHchStatusA(cid); //This function is to update the global variable for hchstatus
			}
		}
  }
  else {
		for(var p = 1 ; p < (rowsNum+1) ; p ++) {
			var cid = checkboxname+p;
			if( !$(cid).disabled && $(cid).checked) {
				$(cid).checked = false;
				updateHchStatusA(cid); //This function is to update the global variable for hchstatus
			}
		}
  }
  return true;
}

function setRowStatusColors(rowId) {
	if (rowId) {
		var newQ = beanGrid.cells(rowId,beanGrid.getColIndexById("revisedQty")).getValue();
	    var oldQ = beanGrid.cells(rowId,beanGrid.getColIndexById("originalQty")).getValue();
	    if (newQ != 0 || oldQ != 0) {
	    	var myColor;
		    //if (oldQ == newQ) {
		    	//myColor = " bgcolor=\"#dddddd\"";
		    //}
		    if (newQ == 0) {
			   	myColor = "lightGray";
			} 
		    else if (oldQ == 0) {
			   	myColor = "lightGreen";
			} 
		    else if (oldQ != newQ) {
		    	myColor = "lightYellow";
			}
		    
		    if (myColor != null) {
		    	beanGrid.haasSetColSpanStyle(rowId, 0, 1, "background-color: "+myColor+";");
		    }
	    }
	}
}

function showFinalSchedule() {
	var loc = "deliveryschedulechange.do?uAction=finalSchedule&prNumber="+
			$v("prNumber")+"&lineItem="+$v("lineItem")+"&quantity="+$v("totalQty");
	
	var winId= "deliveryScheduleFinal";//+$v("prNumber")+"-"+$v("lineItem");
    openWinGeneric(loc,winId,"300","400","yes","80","80","yes");
}

function showTransit() {
	document.getElementById("transitPage").style.display = "";
	document.getElementById("resultGridDiv").style.display = "none";
}

function hideTransit() {
	document.getElementById("transitPage").style.display = "none";
	document.getElementById("resultGridDiv").style.display = "";
}

function resultOnLoadWithGrid(gridConfig)
{
  var totalLines = document.getElementById("totalLines").value;
  var callback = document.getElementById("callback").value;
  showTransit();
  
  if (totalLines > 0)
  { 
    initGridWithGlobal(gridConfig);
    hideTransit();
    //document.getElementById("deliveryScheduleChangeBean").style["display"] = "";
  }
  else if (callback == "callback")
  {
    document.getElementById("deliveryScheduleChangeBean").style["display"] = "none";   
    
    /*Set any variables you want to send to the server*/
	document.getElementById('uAction').value = 'viewDeliveryCallback';
    document.genericForm.submit();
    //showPleaseWait();
  }

  /*It is important to call this after all the divs have been turned on or off.*/
}

function doShowAll() {
	parent.parent.openIFrame('deliveryScheduleView','deliveryscheduleviewmain.do'
			,messagesData.deliveryScheduleView,"","N" );
}
