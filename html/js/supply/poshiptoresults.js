windowCloseOnEsc = true;

var beangrid;

/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

/*This function is called onload from the page*/
function myResultOnLoad()
{
 /*If there is data to be shown initialize the grid*/
 var totalLines = document.getElementById("totalLines").value;
 if (totalLines > 0)
 {
   document.getElementById("shipToLocationViewBean").style["display"] = ""; 
   initializeGrid();
 }
 else
 {
   document.getElementById("shipToLocationViewBean").style["display"] = "none"; 
 }

 displayGridSearchDuration();

 /*It is important to call this after all the divs have been turned on or off.
 * This sets all sizes to be a good fit on the screen.*/
 setResultFrameSize();
}

function initializeGrid(){
        beangrid = new dhtmlXGridObject('shipToLocationViewBean');

        initGridWithConfig(beangrid,config,false,false);
        if( typeof( jsonMainData ) != 'undefined' ) {
                beangrid.parse(jsonMainData,"json");
        }
        beangrid.attachEvent("onRowSelect",selectRow);
        beangrid.attachEvent("onRightClick",selectRow);
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

var selectedrow=null;

function selectRow()
{     
   //alert('poshiptoresults::selectRow');
// to show menu directly
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
   parent.locationId = cellValue(rowId0,"locationId");
   parent.locationDesc = cellValue(rowId0,"locationDesc");   
   parent.shipToCompanyId = cellValue(rowId0,"companyId");
   parent.facilityId = cellValue(rowId0,"facilityId");
   parent.addressLine1 = cellValue(rowId0,"addressLine1");
   parent.addressLine2 = cellValue(rowId0,"addressLine2");
   parent.addressLine3 = cellValue(rowId0,"addressLine3"); 
   parent.city = cellValue(rowId0,"city");
   parent.stateAbbrev = cellValue(rowId0,"stateAbbrev");
   parent.zip = cellValue(rowId0,"zip");
   parent.countryAbbrev = cellValue(rowId0,"countryAbbrev");
   
   var selectedUser = parent.document.getElementById("selectedRow");
   selectedUser.innerHTML = '<a href="#" onclick="returnData(\'' + cellValue(rowId0,"locationId") +'\',\'' + cellValue(rowId0,"locationDesc") +'\',\'' + cellValue(rowId0,"companyId") + '\'); return false;">'+messagesData.selectedRowMsg+' : '+ cellValue(rowId0,"locationId") +'</a>';   
}
