
/************************************NEW***************************************************/
var mygrid;
/*TODO- Allow copy and paste in the grid with ctrl+c and ctrl+v
      - Block ctrl+mouse wheel on whole page
      -right click to print receipts,BOl,.etc
* */

/*This is really the same as before. Except now there is a call to initialize the grid.*/
function resultOnLoad()
{
 setResultFrameSize();

// try{ // don't show error please...
// if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
// {
//  parent.document.getElementById("updateResultLink").style["display"] = "none";
// }
// else
// {
//  parent.document.getElementById("updateResultLink").style["display"] = "";
// }
// } catch(ex) {}
document.getElementById("customerAddressSearchViewBean").style["display"] = "";
doInitGrid();
/*
 totalLines = document.getElementById("totalLines").value;
 if (totalLines > 0)
 {
  document.getElementById("customerAddressSearchViewBean").style["display"] = "";
  doInitGrid();
 }
 else
 {
   document.getElementById("customerAddressSearchViewBean").style["display"] = "none";
 }  */
}

function doInitGrid(){
/*Give the div name that holds the grdi the same name as your dynabean*/
mygrid = new dhtmlXGridObject('customerAddressSearchViewBean');
mygrid.setImagePath("/dhtmlxGrid/codebase/imgs/");
/*To internationalize column headers, get the values from messagesData*/
mygrid.setHeader(""+messagesData.partnumber+","+messagesData.description+","+messagesData.specification+","+messagesData.qty+","+messagesData.priceeach+","+messagesData.requiredshelflife+"");
/*Set initial widths for your columns, play this by the eye.*/
mygrid.setInitWidths("200,250,120,90,100,150");
/*You can set column alingments*/
mygrid.setColAlign("left,left,left,left,left,left");
/*Set the type of sort to do on this column.
Date read-only sorting is not supported yet. For date columns set the sorting type to na*/
mygrid.setColSorting("str,str,str,str,str,str");
//mygrid.setColSorting("na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na");
/*Set column types, you can define and editable columns here.*/
mygrid.setColTypes("ro,ed,ro,ro,rotxt,ro");
/*Set columIds. this will be the id you want your dynabean to be. this is the same as setting id attribute on an input element.*/ 
mygrid.setColumnIds("partNumber,description,specification,qty,unitPrice,shelfLifeRequired");
/*This tells the grid to send all data in the grid to the server in dynabean compatible format*/
mygrid.submitOnlyChanged(false);
mygrid.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
  
/*****************
* This mehtod calls are optional.
 very strange that set setColumnsVisibility false shows the column  -According to dhtmlx this is a feature
 Use this to hide columns which is data you need to do updates/send to server. I usually stick them at the end of the grid.
*/
mygrid.setColumnsVisibility("false,false,false,false,false,false");

/*This keeps the row height the same, true will wrap cell content and height of row will change.*/
mygrid.enableMultiline(false);
/*This si used to tell the grid that we have row height set at 30px*/
mygrid.setAwaitedRowHeight(30);
/*You can use this to call the select row functions and the right click funcitons.*/
//mygrid.attachEvent("onRowSelect",selectRow);
//mygrid.attachEvent("onRightClick",selectRow);

/*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.*/
//mygrid.enableEditEvents(true,false,false);
/*enable/disable light mouse navigation mode (row selection with mouse over, editing with single click), mutual exclusive with enableEditEvents
* Causes some weired mouse behaviour, moves focus to the right most part of the gird for no reason.*/
//mygrid.enableLightMouseNavigation(true);

/*This allows doubleclick on any header (or use dropdown below the grid) to adjust column size according to cell value size.
* If sorting is enabled dblclick sorts also.*/
//mygrid.enableColumnAutoSize(true);
  
/*this enabels smart rendering, which buffers the loading to avoid any huge memory usages.*/
mygrid.enableSmartRendering(true);
mygrid.setSkin("haas");
mygrid.init();

/*loading from JSON object*/
mygrid.parse(jsonMainData,"json");

/*This is to disable the mouse wheel scroll problem in IE.*/
if (_isIE) 
 mygrid.entBox.onmousewheel=stop_event;

/*Allows to turn on or off columns in the grid. The menu that showsup needs some work.*/
//mygrid.enableHeaderMenu(true);

/*You can use this to set minwidth on a column in the grid.*/
/*mygrid.setColumnMinWidth(150, 8);*/

/*This dislpays our standard footer message*/  
displayGridSearchDuration();
}

function stop_event(e){
var key;
var isCtrl;

if(window.event)
{
  key = window.event.keyCode;     //IE
  if(window.event.ctrlKey)
  	isCtrl = true;
  else
  	isCtrl = false;
}
else
{
  key = e.which;     //firefox
  if(e.ctrlKey)
  	isCtrl = true;
  else
  	isCtrl = false;
}

//if ctrl is pressed check if other key is in forbidenKeys array
if(isCtrl)
{
  e=e||event;
  if (e.preventDefault) e.preventDefault();
  e.cancelBubble=true;
  return false;
}
}


function selectRightclick(rowId,cellInd){
 mygrid.selectRowById(rowId,null,false,false);
 selectRow(rowId,cellInd);
}

function selectRow(rowId,cellInd){
 //   alert("User clicked on row with id "+rowId+" and cell index "+cellInd);
    rowId0 = arguments[0];
    colId0 = arguments[1];
//    alert("rowId0:"+rowId0+"\ncolId0:"+colId0);
    mygrid.selectRowById(rowId0,null,false,false);	

    var currentCustomerId = mygrid.cells(rowId0,0).getValue();
    var addressId = mygrid.cells(rowId0,2).getValue();
    alert("currentCustomerId"+currentCustomerId);
    var selectedCustomerSpan = parent.document.getElementById("selectedCustomerSpan");

   selectedCustomerSpan.innerHTML = '<a href="#" onclick="selectedCustomerCall(); return false;">' + messagesData.selectedcustomer + ' : ' + currentCustomerId + '/' + addressId +'</a>';
   
   parent.returnSelectedCustomerId = currentCustomerId;
   parent.returnSelectedAddressId = addressId;
   
   var  contextMenuName ="print";
   /*var shipmentCell = mygrid.cellById(rowId, mygrid.getColIndexById("shipmentId")).getValue();
   var cancelStatus = mygrid.cellById(rowId, mygrid.getColIndexById("cancelStatus")).getValue();
   if (cancelStatus.trim().length > 0 && cancelStatus == 'canceled')
   {
     contextMenuName = 'canceled';
   }
   else if (shipmentCell.trim().length == 0)
   {
     contextMenuName = 'cancelOrderMenu';
   }
   else
   {
     contextMenuName = 'printMenu';
   }
    */
    if (contextMenuName.trim().length > 0)
    {
     toggleContextMenu(""+contextMenuName+"");
    }
}

function selectedCustomerCall()
{ 
  window.opener.customerIdChanged(returnSelectedCustomerId);
  window.close();
}


/*This is called from the main page to set the grid height*/
function setGridHeight(resultFrameHeight)
{
  var griDiv = document.getElementById("customerAddressSearchViewBean");      
  griDiv.style.height = resultFrameHeight-3 + "px";
}

/*This is called from the main page to set the correct sizes*/
function setGridSize()
{
  mygrid.setSizes();
}

function printGrid()
{
 mygrid.printView();
}




