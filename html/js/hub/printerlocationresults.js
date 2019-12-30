var mygrid;
/*Set this to be false if you don't want the grid width to resize based on window size.
* You will also need to set the resultsMaskTable width appropriately in the JSP.*/
var resizeGridWithWindow = false;

var inputSize = new Array();
inputSize = {"printerPath":35,"status":2};

var maxInputLength = new Array();
maxInputLength = {"printerPath":45,"status":2};

var multiplePermissions;
var permissionColumns = new Array();

var selectedRowId = null;

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
  document.getElementById("locationLabelPrinterBean").style["display"] = "";
  doInitGrid();
 }
 else
 {
   document.getElementById("locationLabelPrinterBean").style["display"] = "none";   
 }

/*This displays our standard footer message*/
displayGridSearchDuration();

 /*It is important to call this after all the divs have been turned on or off.
 * This sets all sizes to be a good fit on the screen.*/
 setResultFrameSize();
}

function doInitGrid(){
/*Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates*/
mygrid = new dhtmlXGridObject('locationLabelPrinterBean');
mygrid.setImagePath("/dhtmlxGrid/codebase/imgs/");
/*To internationalize column headers, get the values from messagesData.
To show tooltips in the header you can use spans to define the heading and tooltip.
We would want to show tooltips for Columns that can be updated. Make sure to escape any , in your tool tip by //,*/
mygrid.setHeader("permission,"+
                 "statuspermission," +
                 "altlocpermission," +
                 messagesData.ok+","+
       		     messagesData.labelPrinter+","+
                 messagesData.size+","+
                 messagesData.printerpath+","+
                 "oldPrinterPath," +
                 "hub");

/*Set initial widths for your columns, set the initial widths based on the number of
* characters you want displayed in the column.
* We will proportionally increase the widths based on the user font size.
* Set hidden collums to size 0.
* If you want to use regular widths based on pixels you can set pixel values in setInitWidths()
* and don't call updateColumnWidths() function call.*/
mygrid.setInitWidths("0,0,0,5,15,5,22,0,0");

/*You can set column alingments, all string and date values will be left aligned and numbers will be right aligned.*/
mygrid.setColAlign("left,left,left,left,left,right,left,left,left");

/*Set the type of sort to do on this column.If the gird has rowspans >1 set all columns sotring to be na.
sorting type str is case sensistive (X,Z come before a,b). haasStr is caseinsensitve sorting.
For Date column types you need to write custom sorting funciton which will be triggered by onBeforeSorting event.
For Editable Date column we will not allow sorting, set the sorting to be na.
For hch you have to write a custom sorting function if needed on the page, other wise set sorting to na*/
mygrid.setColSorting("na,na,na,haasHch,haasStr,int,haasStr,haasStr,haasStr");

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

mygrid.setColTypes("ro,ro,ro,hch,ro,ro,hed,ro,ro");

/* Permission columns */
//permissionColumns = {"status":true, "altPrinterLocInput":true};

/*This enables tooltips on a column and on mouseover displays the value of the cell in a tooltip.
* We will enable tooltips only for columns whose width might be less than the text data in that column.
* General rule >30 characters give a tooltip.
* Most likely candidates are packaging, item desc, part desc, any user comments etc.
**/
mygrid.enableTooltips("false,false,false,false,false,false,true,false,false");

/*Set columIds. this will be the id you want your dynabean to be. this is the same as setting id attribute on an input element.*/
mygrid.setColumnIds("permission,statusPermission,altPrinterLocInputPermission,okDoUpdate,printerLocation,labelStock,printerPath,oldPrinterPath,hub");

/*This tells the grid to send all data in the grid to the server in dynabean compatible format.
* You can comment this out if this is a read-only page not sending any data back to the server.*/
mygrid.submitOnlyChanged(false);
mygrid.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
/*Can also define which columns you want submitted to the server, avoid submitting data fields with date time in them
this will cause problems on server side.
* */
mygrid.submitColumns([true,true,true,true,true,true,true,true,true]);

/*****************
*set setColumnsVisibility false shows the column  -According to dhtmlx this is a feature
* or you can use mygrid.setColumnHidden(columnIdex,ture)
* Use this to hide columns which is data you need to do updates/send to server. I usually stick them at the end of the grid.
*/
mygrid.setColumnsVisibility("true,true,true,false,false,false,false,true,true");

/*This keeps the row height the same, true will wrap cell content and height of row will change.*/
mygrid.enableMultiline(false);
/*This is used to tell the grid that we have row height set based on the font size. */
mygrid.setAwaitedRowHeight(gridRowHeight);

/*Set any EventHandlers you want on the grid.
You can use this to call the select row functions and the right click funcitons, and custom sorting
*/
mygrid.attachEvent("onRightClick",selectRightclick);
mygrid.attachEvent("onRowSelect",selectRow);

/*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.
*Needed for or multiline editable text column type txt.*/
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

multiplePermissions = true;

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
	selectedRowId = rowId;
	toggleContextMenu("addMenu");
}

/*Example function to deal with selecting a row. Depending on which column was
clicked on we can do the appropriate thing.*/
function selectRow(rowId,cellInd) {
	selectedRowId = rowId;
}

// Function for updating records or submiting form back to the server
function submitUpdate()
{
    /*Set any variables you want to send to the server*/
    document.genericForm.target='';
    document.getElementById('action').value = 'update';
    parent.showPleaseWait();
    /*prepare grid for data sending, this is important to get the data from the grid to the server.*/
    mygrid.parentFormOnSubmit();
    document.genericForm.submit();
}

function setHlink(pHub) {
   if ( typeof( jsonMainData ) == 'undefined' ) return;
   var rows =jsonMainData.rows;
   for( var i = 0; i < rows.length; i++ )
   {
      if(rows[i].data[0]=='Y' && rows[i].data[2]=='Y')
      {
         rows[i].data[10] ="<input class='inputBox' type='text' id='altPrinterLocation" + (i+1) +  "' value='" + rows[i].data[11] + "' size='17' readonly/>&nbsp;&nbsp;<input type='button' class='lookupBtn'  onmouseover=\"this.className='lookupBtn lookupBtnOver'\"   onmouseout=\"this.className='lookupBtn'\" name=\"printerLocSelector"+(i+1)+"\" value=\"...\" align=\"right\" onClick=\"getPrinterLoc($('altPrinterLocation" + (i+1) + "')," + (i+1) + ",'" + pHub + "');\"/>";
      }
   }
}

var newprinterloc;
var changeprinterrowId;

function getPrinterLoc(elementName, i,pHub)
{
   var rows = jsonMainData.rows;
   var pManagedQueue = rows[i-1].data[8];
   var pLabelStock = rows[i-1].data[5];
   var pPrinterLocation = rows[i-1].data[4];
   loc = "altprinterlocresults.do?" + "hub=" + pHub + "&managedQueue=" + pManagedQueue + "&labelStock=" + pLabelStock + "&printerLocation=" + pPrinterLocation;
   openWinGeneric(loc,"_altprinterlocsearch","400","600","yes","50","50","no")
   newprinterloc=elementName;
   changeprinterrowId=i;
   elementCounter=i;
}

function setNewAltPrinterLoc(newAltPrinterLoc) 
{
   newprinterloc.value = newAltPrinterLoc;
   cell(changeprinterrowId,"alternatePrinterLocation").setValue(newAltPrinterLoc);	   
   cell(changeprinterrowId,"okDoUpdate").setValue(1);
   cell(changeprinterrowId,"okDoUpdate").setChecked(true);	
}

function addPrinterPath()
{
	 try
    {
	if(mygrid == null || mygrid == null) {
	     return false; 
	   } 
            
    var ind = mygrid.getRowIndex(selectedRowId)*1 + 1; //mygrid.getRowsNum(); 
    
    var rowid =  mygrid.getRowsNum()*1+1; //selectedRowId*1+1;
    var thisrow = mygrid.addRow(rowid,"",ind);
    
   
    mygrid.cells(rowid,mygrid.getColIndexById("permission")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("permission")).getValue());
    mygrid.cells(rowid,mygrid.getColIndexById("statusPermission")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("statusPermission")).getValue());
    mygrid.cells(rowid,mygrid.getColIndexById("altPrinterLocInputPermission")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("altPrinterLocInputPermission")).getValue());
    mygrid.cells(rowid,mygrid.getColIndexById("okDoUpdate")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("okDoUpdate")).getValue());
    mygrid.cells(rowid,mygrid.getColIndexById("printerLocation")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("printerLocation")).getValue());
    mygrid.cells(rowid,mygrid.getColIndexById("labelStock")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("labelStock")).getValue());
    mygrid.cells(rowid,mygrid.getColIndexById("printerPath")).setValue('');
    mygrid.cells(rowid,mygrid.getColIndexById("oldPrinterPath")).setValue('');
    mygrid.cells(rowid,mygrid.getColIndexById("hub")).setValue(mygrid.cells(selectedRowId,mygrid.getColIndexById("hub")).getValue());
        
    selectedRowId = rowid;
    
    var rowsNum = mygrid.getRowsNum();
//    alert(rowsNum);
    
   
    //showPageWait();
    } catch(ex){
        alert(messagesData.norowselected);
    }
}



