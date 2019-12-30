windowCloseOnEsc = true;

var beangrid;

/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

/*This function is called onload from the page*/
function myResultOnLoad()
{
 /*If there is data to be shown initialize the grid*/
 var totalLines = document.getElementById("totalLines").value;
 var now = new Date();
 parent.document.getElementById("startSearchTime").value = now.getTime();
 if (totalLines > 0)
 {
   document.getElementById("pOCannedCommentsBean").style["display"] = ""; 
   initGridWithGlobal(gridConfig);
 }
 else
 {
   document.getElementById("pOCannedCommentsBean").style["display"] = "none"; 
 }

 displayGridSearchDuration();

 /*It is important to call this after all the divs have been turned on or off.
 * This sets all sizes to be a good fit on the screen.*/
 setResultFrameSize();
}

/*The reason this is here because you might want a different width/proprties for the pop-up div depending on the page*/
function showErrorMessages()
{
  var resulterrorMessages = window.frames["resultFrame"].document.getElementById("errorMessagesAreaBody");
  var errorMessagesAreaBody = document.getElementById("errorMessagesAreaBody");
  errorMessagesAreaBody.innerHTML = resulterrorMessages.innerHTML;

  showErrorMessagesWin.show();

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";
}



function selectRow()
{ 
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

   if( ee != null ) {
	if( ee.button != null && ee.button == 2 ) rightClick = true;
	else if( ee.which == 3  ) rightClick = true;
   }
   var rows = jsonMainData.rows;
   currentData = rows[rowId0-1].data[colId0];
   
   beangrid.selectRowById(rowId0,null,false,false);	
	
   selectedrow = rowId0;
   parent.commentId = cellValue(rowId0,"commentId");
   parent.comment = cellValue(rowId0,"comments");
   parent.commentName = cellValue(rowId0,"commentName");
   var selectedcmt = "";
   if (cellValue(rowId0,"comments").length != "" && cellValue(rowId0,"comments").length > 50) {
	   selectedcmt = cellValue(rowId0,"comments").substring(0,50) + "\.\.\.";
	   selectedcmt.replace(/"/g, '&quot;');	   
   } else {
	   selectedcmt = cellValue(rowId0,"comments");
	   selectedcmt.replace(/"/g, '&quot;');
   } 

   var selectedUser = parent.document.getElementById("selectedRow");
   selectedUser.innerHTML = '<a href="#" onclick="returnData(); return false;">'+messagesData.selectedRowMsg+' : '+ selectedcmt  + '</a>';   
}
