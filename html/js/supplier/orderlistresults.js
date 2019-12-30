var mygrid;
/*Set this to be false if you don't want the grid width to resize based on window size.
* You will also need to set the resultsMaskTable width appropriately in the JSP.*/
var resizeGridWithWindow = true;

/*This function is called onload from the page*/
function myResultOnload()
{
 try
 {
 if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
 }
 catch(ex)
 {}

/*If there is data to be shown initialize the grid*/
 var totalLines = document.getElementById("totalLines").value;
  
 if (totalLines > 0)
 {
  document.getElementById("wbuyStatusViewBean").style["display"] = "";
  doInitGrid();
 }
 else
 {
   document.getElementById("wbuyStatusViewBean").style["display"] = "none";   
 }

/*This displays our standard footer message*/
displayGridSearchDuration();

 /*It is important to call this after all the divs have been turned on or off.
 * This sets all sizes to be a good fit on the screen.*/
 setResultFrameSize();
}

function doInitGrid(){
/*Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates*/
mygrid = new dhtmlXGridObject('wbuyStatusViewBean');
mygrid.setImagePath("/dhtmlxGrid/codebase/imgs/");
/*To internationalize column headers, get the values from messagesData.
To show tooltips in the header you can use spans to define the heading and tooltip.
We would want to show tooltips for Columns that can be updated. Make sure to escape any , in your tool tip by //,*/
mygrid.setHeader(""+"permission"+","+messagesData.ponum+","+messagesData.company+","+
					messagesData.shipto+","+messagesData.datecreated+","+messagesData.critical+","+messagesData.status+","+
					messagesData.currentstatus+","+messagesData.firstviewed+","+messagesData.confirmeddate+","+messagesData.shipdate+","+
					messagesData.dockdate+","+messagesData.comments+",'','','','',''");

/*Set initial widths for your columns, set the initial widths based on the number of
* characters you want displayed in the column.
* We will proportionally increase the widths based on the user font size.
* Set hidden collums to size 0.
* If you want to use regular widths based on pixels you can set pixel values in setInitWidths()
* and don't call updateColumnWidths() function call.*/
mygrid.setInitWidths("0,5,10,10,7,7,7,0,7,7,7,7,32,0,0,0,0,0");

/*You can set column alingments, all string and date values will be left aligned and numbers will be right aligned.*/
mygrid.setColAlign("left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left");

/*Set the type of sort to do on this column.If the gird has rowspans >1 set all columns sotring to be na.
sorting type str is case sensistive (X,Z come before a,b). haasStr is caseinsensitve sorting.
For Date column types you need to write custom sorting funciton which will be triggered by onBeforeSorting event.
For Editable Date column we will not allow sorting, set the sorting to be na.
For hch you have to write a custom sorting function if needed on the page, other wise set sorting to na*/
mygrid.setColSorting("na,haasStr,haasStr,haasStr,int,haasStr,haasStr,haasStr,int,int,int,int,haasStr,int,int,int,int,int");

/*Set column types, you can define editable columns here. More documentation is availabe on Nimbus
N:\Tech Center\dhtmlxGrid_Customized_Docs
* ro -read only
* hed -editable sinlge line text
* txt - Multiline edit text (mostly for comments and user input)
* date -hcal
* hcoro -select drop down
* hlink - link
* hch -checkbox
* */
/*For multiline editable text if I know the user has no permissions to update the text. I am changing the column type to ro.*/

mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");

/*This enables tooltips on a column and on mouseover displays the value of the cell in a tooltip.
* We will enable tooltips only for columns whose width might be less than the text data in that column.
* General rule >30 characters give a tooltip.
* Most likely candidates are packaging, item desc, part desc, any user comments etc.
**/
mygrid.enableTooltips("false,false,true,true,false,false,false,false,false,false,false,false,true,false,false,false,false,false");

/*Set columIds. this will be the id you want your dynabean to be. this is the same as setting id attribute on an input element.*/
mygrid.setColumnIds("permission,radianPo,homeCompanyName,shipToLocationId,dateCreatedDisplay,critical,dbuyStatus,daysSinceLastStatus,dateAcknowledgementDisplay,dateConfirmedDisplay,vendorShipDateDisplay,promisedDateDisplay,comments,dateCreated,dateAcknowledgement,dateConfirmed,vendorShipDate,promisedDate");

/*This tells the grid to send all data in the grid to the server in dynabean compatible format.
* You can comment this out if this is a read-only page not sending any data back to the server.*/
mygrid.submitOnlyChanged(false);
mygrid.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
/*Can also define which columns you want submitted to the server, avoid submitting data fields with date time in them
this will cause problems on server side.
* */
mygrid.submitColumns([false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false]);


/*****************
*set setColumnsVisibility false shows the column  -According to dhtmlx this is a feature
* or you can use mygrid.setColumnHidden(columnIdex,ture)
* Use this to hide columns which is data you need to do updates/send to server. I usually stick them at the end of the grid.
*/
mygrid.setColumnsVisibility("true,false,false,false,false,false,false,true,false,false,false,false,false,true,true,true,true,true");

/*This keeps the row height the same, true will wrap cell content and height of row will change.*/
mygrid.enableMultiline(false);
/*This is used to tell the grid that we have row height set based on the font size. */
mygrid.setAwaitedRowHeight(gridRowHeight);

/*Set any EventHandlers you want on the grid.
You can use this to call the select row functions and the right click funcitons, and custom sorting
*/
mygrid.attachEvent("onRightClick",selectRightclick);
mygrid.attachEvent("onBeforeSorting",sortValues);

/*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.
*Needed for or multiline editable text column type txt.*/
mygrid.enableEditEvents(true,false,false);
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

/*This is to allow copy with ctrl+c to clipboard, and ctrl+v to paste to clipboard.*/
mygrid.entBox.onselectstart = function(){ return true; };

setHaasGrid(mygrid);
}

/*An example rightclik handler. This is mostly used to set the correct context menu.*/
function selectRightclick(rowId,cellInd){
	mygrid.selectRowById(rowId,null,false,false);	
	
	  var dbuyStatus = cellValue(mygrid.getSelectedRowId(),"dbuyStatus");	  
	    if (dbuyStatus == "REJECTED")
	    {
	    toggleContextMenu('showDetails');
	    } else {
	    toggleContextMenu('showPurchaseOrder');
	    } 
}

/*Example function to deal with selecting a row. Depending on which column was
clicked on we can do the appropriate thing.*/
function selectRow(rowId,cellInd) {

}


function showPO(){	
	   var radianPo = cellValue(mygrid.getSelectedRowId(),"radianPo");		   	  
	   loc = "purchaseorder.do?po="+ radianPo;
	   //openWinGeneric(loc,"PurchaseOrder","800","450","yes","50","50","yes")  ;
       try
       {
        parent.parent.openIFrame("purchaseOrder"+radianPo+"",loc,""+messagesData.purchaseorder+" "+radianPo+"","","N");
       }
       catch (ex)
       {
         openWinGeneric(loc,"PurchaseOrder_"+radianPo+"","800","600","yes","50","50","yes");
       }
    }
     
/*
 * 
 *  old
 * function showDetails() 
  {
	  var radianPo = cellValue(mygrid.getSelectedRowId(),"radianPo");	
	   loc = "poproblemdetailmain.do?radianPo="+ radianPo;
	   openWinGeneric(loc,"PurchaseOrder","800","450","yes","50","50","no")  ;
   
  }
*/
 

function showDetails() 
{
	  var radianPo = cellValue(mygrid.getSelectedRowId(),"radianPo");	
	   loc = "poproblemdetail.do?radianPo="+ radianPo;
	   openWinGeneric(loc,"PurchaseOrder","800","450","yes","50","50","no")  ;
 
}

//Function to sort date  and other fields on the grid.
function sortValues(ind,type,direction)
{
	
	var columnId = mygrid.getColumnId(ind);
	var colIndex;
	switch (columnId)
 	{
 	case "dateCreatedDisplay":
 		colIndex=mygrid.getColIndexById("dateCreated");
 		break;
 	case "dateAcknowledgementDisplay":
 		colIndex=mygrid.getColIndexById("dateAcknowledgement"); 
 		break;
 	case "dateConfirmedDisplay":
 		colIndex=mygrid.getColIndexById("dateConfirmed"); 
 		break;	
	case "vendorShipDateDisplay":
 		colIndex=mygrid.getColIndexById("vendorShipDate"); 
 		break;
	case "promisedDateDisplay":
 		colIndex=mygrid.getColIndexById("promisedDate"); 
 		break;	
 	default:
 		return true;   // Do not block normal sorting
 	};  
	mygrid.sortRows(colIndex,type,direction);         //sort grid by the column with prepared values
	mygrid.setSortImgState(true,ind,direction);    //set a correct sorting image
	return false; //block default sorting	
	
	lastRevised
}



