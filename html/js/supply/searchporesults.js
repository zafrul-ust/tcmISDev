/************************************NEW***************************************************/
var mygrid;
/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

var selectedRowId = null;

/*This is really the same as before. Except now there is a call to initialize the grid.*/
function resultOnLoad()
{	
 try{
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

 totalLines = document.getElementById("totalLines").value;
 if (totalLines > 0)
 {
  document.getElementById("poSearchResultBean").style["display"] = "";
  /*this is the new part.*/
  doInitGrid();
 }
 else
 {
   document.getElementById("poSearchResultBean").style["display"] = "none";
   /*This is to reset the footer message*/
   displayGridSearchDuration();
 }
 
  setResultFrameSize();
}

function doInitGrid(){
/*Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates*/
mygrid = new dhtmlXGridObject('poSearchResultBean');
mygrid.setImagePath("/dhtmlxGrid/codebase/imgs/");
/*To internationalize column headers, get the values from messagesData.
To show tooltips in the header you can use spans to define the heading and tooltip.
We would want to show tooltips for Columns that can be updated. Make sure to escape any , in your tool tip by //,*/
mygrid.setHeader(""+messagesData.po+","+messagesData.supplier+","+messagesData.buyer+","+messagesData.hub+","+messagesData.inventorygroup+
                 ","+messagesData.dateconfirmed+","+messagesData.itemid+","+messagesData.itemdescription+","+messagesData.supplypath+","+messagesData.needdate+","+messagesData.originalpromiseddate+
                 ","+messagesData.quantity+","+messagesData.unitprice+","+messagesData.extprice+","+messagesData.qtyreceived+","+messagesData.paymentterms+
                 ","+messagesData.customerpo+","+messagesData.partnumber+
                 ","+messagesData.mfgpartno+","+messagesData.manufacturername+","+messagesData.supplierpartnum+
                 ","+messagesData.shiptoaddress+","+messagesData.vendorshipdate+","+messagesData.revisedpromisedate+
                 ",hiddenDateCreated,hiddenNeedDate,hiddenPromisedDate,hiddenVendorShipDate,hiddenRevisedPromisedDate,hiddenUnitPrice,hiddenExtPrice,hiddenSupplyPath");

/*Set initial widths for your columns, set the initial widths for Font Size smallest.
* We will proportionally increase the widths based on the user font size.
* Set hidden collums to size 0.*/
mygrid.setInitWidths("5,12,10,8,8,9,5,20,7,7,7,5,10,10,7,7,8,8,8,10,8,20,7,7,0,0,0,0,0,0,0,0");

/*You can set column alingments, all string and date values will be left aligned and numbers will be right aligned.*/
mygrid.setColAlign("left,left,left,left,left,left,left,left,left,left,left,right,right,right,right,left,left,left,left,left,left,left,right,right,right,right,right,right,right,right,right,right");

/*Set the type of sort to do on this column.If the gird has rowspans >1 set all columns sotring to be na.
Sorting for grid with rowspans>1 is not supported correctly yet.
Date read-only sorting is not supported yet. For date columns set the sorting type to na*/
mygrid.setColSorting("haasStr,haasStr,haasStr,haasStr,haasStr,int,haasStr,haasStr,haasStr,int,int,int,int,int,int,haasStr,haasStr,haasStr,haasStr,haasStr,haasStr,haasStr,int,int,int,int,int,int,int,int,int,int");
//mygrid.setColSorting("na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na");

/*Set column types, you can define and editable columns here.
* ro -read only
* ed -editable sinlge line text
* date -
* select drop down -
* multiline text -
* checkbox -*/
mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");

/*This enables tooltips on a column and on mouseover displays the value of the cell in a tooltip.
* We will enable tooltips only for columns whose width might be less than the text data in that column.
* Most likely candidates are packaging, item desc, part desc, any user comments etc.*/
mygrid.enableTooltips("false,true,false,false,false,false,false,true,false,false,false,false,false,false,false,false,false,false,true,true,true,true,false,false,false,false,false,false,false,false");

/*Set columIds. this will be the id you want your dynabean to be. this is the same as setting id attribute on an input element.*/ 
mygrid.setColumnIds("radianPo,supplierName,buyerName,hubName,inventoryGroupName,dateCreated,itemId,itemDesc,supplyPathJsp,needDate,promisedDate,quantity,unitPrice,extPrice,qtyReceived,paymentTerms,customerPo,partNumber,mfgPartNo,manufacturer,supplierPartNo,shipToAddresshiddenDateCreated,vendorShipDate,revisedPromisedDate,hiddenDateCreated,hiddenNeedDate,hiddenPromisedDate,hiddenVendorShipDate,hiddenRevisedPromisedDate,hiddenUnitPrice,hiddenExtPrice,supplyPath");

/*This tells the grid to send all data in the grid to the server in dynabean compatible format.
* You can comment this out if this is a read-only page not sending any data back to the server.*/
mygrid.submitOnlyChanged(false);
mygrid.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
/*Can also define which columns you want submitted to the server, avoid submitting data fields with date time in them
this will cause problems on server side.
* */  
mygrid.submitColumns([false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false]);

/*****************
*set setColumnsVisibility false shows the column  -According to dhtmlx this is a feature
 Use this to hide columns which is data you need to do updates/send to server. I usually stick them at the end of the grid.
*/
mygrid.setColumnsVisibility("false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true");

/*This keeps the row height the same, true will wrap cell content and height of row will change.*/
mygrid.enableMultiline(false);
/*This si used to tell the grid that we have row height set at 30px*/
mygrid.setAwaitedRowHeight(gridRowHeight);
  
/*You can use this to call the select row functions and the right click funcitons.*/
//mygrid.attachEvent("onRowSelect",doOnRowSelected);
//mygrid.attachEvent("onRightClick",onRightClick);
mygrid.attachEvent("onBeforeSorting",onBeforeSorting);
mygrid.attachEvent("onRowSelect",doOnRowSelected);
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

/*This dislpays our standard footer message*/
displayGridSearchDuration();
}

function onBeforeSorting(ind,type,direction){
	var columnId = mygrid.getColumnId(ind);
	var colIndex;
 	switch (columnId)
 	{
 	case "dateCreated":  //actual column id of read-only date
 		colIndex=mygrid.getColIndexById("hiddenDateCreated");
 		break;
 	case "needDate": //actual column id of read-only date
 		colIndex=mygrid.getColIndexById("hiddenNeedDate"); 
 		break;
    case "promisedDate": //actual column id of read-only date
 		colIndex=mygrid.getColIndexById("hiddenPromisedDate"); 
 		break;
 	case "vendorShipDate": //actual column id of read-only date
 		colIndex=mygrid.getColIndexById("hiddenVendorShipDate"); 
 		break;
 	case "revisedPromisedDate": //actual column id of read-only date
 		colIndex=mygrid.getColIndexById("hiddenRevisedPromisedDate"); 
 		break;
 	case "unitPrice": //actual column id of read-only date
 		colIndex=mygrid.getColIndexById("hiddenUnitPrice"); 
 		break;
 	case "extPrice": //actual column id of read-only date
 		colIndex=mygrid.getColIndexById("hiddenExtPrice"); 
 		break; 	 
 	default:
 		return true;   // Do not block normal sorting
 	};  
	mygrid.sortRows(colIndex,type,direction);         //sort grid by the column with prepared values
	mygrid.setSortImgState(true,ind,direction);    //set a correct sorting image
	return false; //block default sorting
}

function selectRightclick(rowId, cellInd) {	
    mygrid.selectRowById(rowId,null,false,false);
    selectedRowId = rowId;
    toggleContextMenu('showPo');
}

function doOnRowSelected(rowId,cellInd) {
	var columnId = mygrid.getColumnId(cellInd);
	selectedRowId = rowId;
 	switch (columnId)
 	{
 	case "radianPo":
 		var po = mygrid.cellById(rowId,cellInd).getValue();
// 		showPurchOrder(po);
 		break;		
 	default:
 	};
 	parent.returnSelctedPO = cellValue(mygrid.getSelectedRowId(),"radianPo");
 	parent.returnSelctedInvGrp = cellValue(mygrid.getSelectedRowId(),"inventoryGroupName"); 	 
}

function showPODocuments()
{ 
	var haasPO = cellValue(mygrid.getSelectedRowId(),"radianPo");
	//var poLine = cellValue(mygrid.getSelectedRowId(),"poLine");
    var inventoryGroup = cellValue(mygrid.getSelectedRowId(),"inventoryGroupName");
 var loc = "/tcmIS/supply/showpodocuments.do?showDocuments=Yes"+
           "&radianPo="+haasPO+"&inventoryGroup="+inventoryGroup+"";
 try {
 	parent.children[parent.children.length] = openWinGeneric(loc,"showAllPoDocuments","450","300","no","80","80");
 } catch (ex){
 	openWinGeneric(loc,"showAllPoDocuments","450","300","no","80","80");
 }
}

function showPurchOrder(po)
{
   var po = mygrid.cellById(selectedRowId, mygrid.getColIndexById("radianPo")).getValue(); 
   var loc = "/tcmIS/supply/purchaseorder.do?po="+po+"&Action=searchlike&subUserAction=po";
   try
  {
    parent.parent.openIFrame("purchaseOrder"+po+"",loc,""+messagesData.purchaseorder+" "+po+"","","N");
  }
  catch (ex)
  {
    openWinGeneric(loc,"showradianPo","800","600","yes","50","50");
  }
   
}

