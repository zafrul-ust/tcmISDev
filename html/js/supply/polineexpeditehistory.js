/************************************NEW***************************************************/
var mygrid;
/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

/*This is really the same as before. Except now there is a call to initialize the grid.*/
function myOnLoad()
{  
 try{
 if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
 {
  document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  document.getElementById("updateResultLink").style["display"] = "";
 }
 }
 catch(ex)
 {}

 totalLines = document.getElementById("totalLines").value; 
 if (totalLines > 0)
 {
  document.getElementById("poLineExpediteHistoryViewBean").style["display"] = ""; 
  /*this is the new part.*/
  doInitGrid();  
 }
 else
 {
   document.getElementById("poLineExpediteHistoryViewBean").style["display"] = "none";   
 }

/*This dislpays our standard footer message*/
displayNoSearchSecDuration();

 /*It is important to call this after all the divs have been turned on or off.*/
 setResultSize();
}

function doInitGrid(){
/*Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates*/
mygrid = new dhtmlXGridObject('poLineExpediteHistoryViewBean');
mygrid.setImagePath("/dhtmlxGrid/codebase/imgs/");
/*To internationalize column headers, get the values from messagesData.
To show tooltips in the header you can use spans to define the heading and tooltip.
We would want to show tooltips for Columns that can be updated. Make sure to escape any , in your tool tip by //,*/
mygrid.setHeader(""+messagesData.haaspoline+","+messagesData.itemid+","+
		     	 messagesData.supplier+","+messagesData.inventorygroup+","+
		     	 messagesData.carrier+","+messagesData.confirmdate+","+
		     	 messagesData.unitprice+","+messagesData.quantity+","+
		     	 messagesData.quantityreceived+","+messagesData.quantityopen+","+
		     	 messagesData.originalpromiseddate+","+messagesData.revisedpromisedate+","+
		         messagesData.comments+","+messagesData.credithold+","+
                 messagesData.dateofrevision+","+messagesData.revisedby+",'','','','',''");

/*Set initial widths for your columns, set the initial widths for Font Size smallest.
* We will proportionally increase the widths based on the user font size.
* Set hidden collums to size 0.*/
mygrid.setInitWidths("8,5,10,10,5,7,7,5,5,5,7,7,20,4,7,9,0,0,0,0,0");

/*You can set column alingments, all string and date values will be left aligned and numbers will be right aligned.*/
mygrid.setColAlign("left,right,left,left,left,left,right,right,right,right,left,left,left,left,left,left,left,left,left,left,left");

/*Set the type of sort to do on this column.If the gird has rowspans >1 set all columns sotring to be na.
Sorting for grid with rowspans>1 is not supported correctly yet.
Date read-only sorting is not supported yet. For date columns set the sorting type to na*/
mygrid.setColSorting("haasStr,int,haasStr,haasStr,haasStr,int,int,int,int,int,int,int,haasStr,haasStr,int,haasStr,int,int,int,int,int");
//mygrid.setColSorting("na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na");

/*Set column types, you can define and editable columns here.
* ro -read only
* ed -editable sinlge line text
* date -hcal
* select drop down -
* multiline text -
* link - hlink
* checkbox -*/
mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");

/*This enables tooltips on a column and on mouseover displays the value of the cell in a tooltip.
* We will enable tooltips only for columns whose width might be less than the text data in that column.
* General rule >30 characters give a tooltip.
* Most likely candidates are packaging, item desc, part desc, any user comments etc.
**/
mygrid.enableTooltips("false,false,true,true,false,false,false,false,false,false,false,false,true,false,false,false,false,false,false,false,false");

/*Set columIds. this will be the id you want your dynabean to be. this is the same as setting id attribute on an input element.*/ 
mygrid.setColumnIds("poLine,itemId,supplierName,inventoryGroup,carrier,confirmDateDiplay,unitPrice,quantity,qtyReceived,quantityOpen,promisedDateDiplay,revisedPromisedDateDiplay,comments,creditHold,dateEnteredDisplay,enteredByName,confirmDate,promisedDate,revisedPromisedDate,dateEntered,radianPo");

/*This tells the grid to send all data in the grid to the server in dynabean compatible format.
* You can comment this out if this is a read-only page not sending any data back to the server.*/
//mygrid.submitOnlyChanged(false);
//mygrid.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
/*Can also define which columns you want submitted to the server, avoid submitting data fields with date time in them
this will cause problems on server side.
* */  
// commented by shahzad
//mygrid.submitColumns([false,false,false,false,false,false,false]);

/*****************
*set setColumnsVisibility false shows the column  -According to dhtmlx this is a feature
 Use this to hide columns which is data you need to do updates/send to server. I usually stick them at the end of the grid.
*/
mygrid.setColumnsVisibility("false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true");

/*This keeps the row height the same, true will wrap cell content and height of row will change.*/
mygrid.enableMultiline(false);

/*This is used to tell the grid that we have row height set based on the font size. */
mygrid.setAwaitedRowHeight(gridRowHeight);
  
/*You can use this to call the select row functions and the right click funcitons.*/
mygrid.attachEvent("onBeforeSorting",sortValues); 
mygrid.attachEvent("onRightClick",selectRightclick);

/*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.*/
//mygrid.enableEditEvents(true,false,false);
/*enable/disable light mouse navigation mode (row selection with mouse over, editing with single click), mutual exclusive with enableEditEvents
* Causes some weired mouse behaviour, moves focus to the right most part of the gird for no reason.*/
//mygrid.enableLightMouseNavigation(true);

/*This allows doubleclick on any header (or use dropdown below the grid) to adjust column size according to cell value size.
* If sorting is enabled dblclick sorts also.*/
//mygrid.enableColumnAutoSize(true);
  
/*this enabels smart rendering, which buffers the loading to avoid any huge memory usages.
* this is not compatible with rowspans.*/
mygrid.enableSmartRendering(true);
  
mygrid.setSkin("haas");

mygrid.init();

/*loading from JSON object*/
mygrid.parse(jsonMainData,"json");

/*This is to disable the mouse wheel ctrl page zoom problem in IE.*/
if (_isIE)
 mygrid.entBox.onmousewheel=stop_event;

/*Allows to turn on or off columns in the grid. The menu that showsup needs some work.*/
//mygrid.enableHeaderMenu(true);

/*You can use this to set minwidth on a column in the grid.*/
/*mygrid.setColumnMinWidth(150, 8);*/

/*This will update the column headers widths according to font size.*/
updateColumnWidths(mygrid);

/*This is to copy the ctrl+c to clipboard, and ctrl+v to paste to clipboard.*/
mygrid.entBox.onselectstart = function(){ return true; };

setHaasGrid(mygrid);
}


function selectRightclick(rowId,cellInd){
	mygrid.selectRowById(rowId,null,false,false);	
	var  contextMenuName ="purchaseHistory";	
	toggleContextMenu(""+contextMenuName+"");
}

//Function to sort date  and other fields on the grid.
function sortValues(ind,type,direction)
{
	
	var columnId = mygrid.getColumnId(ind);
	var colIndex;
	switch (columnId)
 	{
 	case "confirmDateDiplay":
 		colIndex=mygrid.getColIndexById("confirmDate");
 		break; 
 	case "promisedDateDiplay":
 		colIndex=mygrid.getColIndexById("promisedDate");
 		break;
 	case "revisedPromisedDateDiplay":
 		colIndex=mygrid.getColIndexById("revisedPromisedDate"); 
 		break;
 	case "dateEnteredDisplay":
 		colIndex=mygrid.getColIndexById("dateEntered"); 
 		break;	
 	default:
 		return true;   // Do not block normal sorting
 	};  
	mygrid.sortRows(colIndex,type,direction);         //sort grid by the column with prepared values
	mygrid.setSortImgState(true,ind,direction);    //set a correct sorting image
	return false; //block default sorting	
	
	
}




function showPurchOrder()
{
   var HaasPO = cellValue(mygrid.getSelectedRowId(),"radianPo");
   var loc = "/tcmIS/supply/purchaseorder.do?po="+HaasPO+"&Action=searchlike&subUserAction=po";
   //openWinGeneric(loc,"showradianPo","800","600","yes","50","50");

  try
  {
    parent.parent.openIFrame("purchaseOrder"+HaasPO+"",loc,""+messagesData.purchaseorder+" "+HaasPO+"","","N");
  }
  catch (ex)
  {
    openWinGeneric(loc,"showradianPo"+HaasPO+"","800","600","yes","50","50");
  }
}


