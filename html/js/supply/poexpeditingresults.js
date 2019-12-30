var mygrid;
/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

/*TODO- Block ctrl+mouse wheel on whole page      
* */

/*This is really the same as before. Except now there is a call to initialize the grid.*/
function resultOnLoad()
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

 var totalLines = document.getElementById("totalLines").value;
 if (totalLines > 0)
 {
  document.getElementById("poLineExpediteDateBean").style["display"] = "";
  /*this is the new part.*/
  doInitGrid();
 }  
 else
 {
   document.getElementById("poLineExpediteDateBean").style["display"] = "none";
 }

/*This displays our standard footer message*/
displayGridSearchDuration();
    
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}

function doInitGrid(){
/*Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates*/
mygrid = new dhtmlXGridObject('poLineExpediteDateBean');
mygrid.setImagePath("/dhtmlxGrid/codebase/imgs/");
/*To internationalize column headers, get the values from messagesData.
To show tooltips in the header you can use spans to define the heading and tooltip.
We would want to show tooltips for Columns that can be updated. Make sure to escape any , in your tool tip by //,*/
mygrid.setHeader(""+"permission"+","+messagesData.haaspoline+","+messagesData.supplier+","+
        			messagesData.partnumber+","+messagesData.itemid+","+
        			messagesData.itemdescription+","+messagesData.buyer+","+
        			messagesData.supplypath+","+
        			messagesData.dateconfirmed+","+messagesData.needdate+","+
        			messagesData.quantityopen+","+ messagesData.qtyalloctomrs+","+
        			messagesData.qtyreceived+","+ messagesData.unitprice+","+                   
        			messagesData.inventorygroup+","+messagesData.vendorshipdate+","+
        			messagesData.originalpromiseddate+","+messagesData.ok+","+
        			messagesData.revisedpromisedate+","+
        			"<span title='"+messagesData.maximum2000+"'>"+
        			messagesData.comments+"</span>,"+messagesData.credithold+","+ 
        			messagesData.laastupdateddate+","+messagesData.lastupdatedby+","+
        			messagesData.expediteage+","+messagesData.dayslate+","+messagesData.company+","+messagesData.customerpo+","+
        			messagesData.shipto+","+ messagesData.carrier+","+messagesData.hub+",'','','','','','','','','','','',"+messagesData.supplierdateaccepted);


/*Set initial widths for your columns, set the initial widths based on the number of
* characters you want displayed in the column.
* We will proportionally increase the widths based on the user font size.
* Set hidden collums to size 0.
* If you want to use regular widths based on pixels you can set pixel values in setInitWidths()
* and don't call updateColumnWidths() function call.*/
mygrid.setInitWidths("0,7,9,8,6,13,8,6,7,7,6,5,6,7,8,8,9,3,7,20,4,7,7,6,6,7,10,20,7,6,0,0,0,0,0,0,0,0,0,0,0,7");

/*You can set column alingments, all string and date values will be left aligned and numbers will be right aligned.*/
mygrid.setColAlign("left,left,left,left,left,left,left,left,left,left,right,right,right,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left");

/*Set the type of sort to do on this column.If the gird has rowspans >1 set all columns sotring to be na.
sorting type str is case sensistive (X,Z come before a,b). haasStr is caseinsensitve sorting.
For Date column types you need to write custom sorting funciton which will be triggered by onBeforeSorting event.
For Editable Date column we will not allow sorting, set the sorting to be na.
For hch you have to write a custom sorting function if needed on the page, other wise set sorting to na*/
mygrid.setColSorting("na,haasStr,haasStr,int,haasStr,haasStr,haasStr,haasStr,int,int,int,int,int,na,haasStr,int,int,haasHch,int,haasStr,na,int,haasStr,int,int,haasStr,haasStr,haasStr,haasStr,haasStr,int,int,int,int,int,int,int,int,int,int,int,int");

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
/*In this case if I know the user has no permissions to update the text. So I am changing the column type to ro.*/
var commentType = "txt";
var creditHold = "hchstatus";
 if (!showUpdateLinks)
 {  
     commentType = "ro";
 }
mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,hchstatus,hcal,"+commentType+",hchstatus,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");

/*This enables tooltips on a column and on mouseover displays the value of the cell in a tooltip.
* We will enable tooltips only for columns whose width might be less than the text data in that column.
* General rule >30 characters give a tooltip.
* Most likely candidates are packaging, item desc, part desc, any user comments etc.
**/
mygrid.enableTooltips("false,false,true,false,false,true,false,false,false,false,false,false,false,false,false,false,false,false,false,true,false,false,false,false,false,false,false,false,true,false,false,false,false,false,false,false,false,false,false,false,false,false");

/*Set columIds. this will be the id you want your dynabean to be. this is the same as setting id attribute on an input element.*/ 
mygrid.setColumnIds("permission,poLineDisplay,supplierName,partNumber,itemId,itemDesc,buyerName,supplyPath,orderDateDisplay,needDateDisplay,quantityOpen,mrAlloc,qtyReceived,unitPriceDisplay,inventoryGroup,vendorShipDateDisplay,promisedDateDisplay,ok,revisedPromisedDate,comments,creditHold,lastRevisedDisplay,lastRevisedBy,expediteAge,daysLateDisplay,companyId,customerPo,shipToAddress,carrier,hubName,orderDate,needDate,promisedDate,lastRevised,radianPo,poLine,expediteAge,hiddenRevisedPromisedDate,daysLate,hiddenvendorShipDate,supplierDateAcceptedSort,supplierDateAccepted");

/*This tells the grid to send all data in the grid to the server in dynabean compatible format.
* You can comment this out if this is a read-only page not sending any data back to the server.*/
mygrid.submitOnlyChanged(false);
mygrid.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
/*Can also define which columns you want submitted to the server, avoid submitting data fields with date time in them
this will cause problems on server side.
* */  
mygrid.submitColumns([false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,true]);


/*****************
*set setColumnsVisibility false shows the column  -According to dhtmlx this is a feature
* or you can use mygrid.setColumnHidden(columnIdex,ture)
* Use this to hide columns which is data you need to do updates/send to server. I usually stick them at the end of the grid.
*/
mygrid.setColumnsVisibility("true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,true,false");
  
/*This keeps the row height the same, true will wrap cell content and height of row will change.*/
mygrid.enableMultiline(false);
/*This is used to tell the grid that we have row height set based on the font size. */
//mygrid.setAwaitedRowHeight(gridRowHeight); 
   
/*Set any EventHandlers you want on the grid.
You can use this to call the select row functions and the right click funcitons, and custom sorting
*/
mygrid.attachEvent("onRightClick",selectRightclick);
mygrid.attachEvent("onRowSelect",selectRow);
mygrid.attachEvent("onBeforeSorting",sortValues); 

/*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.*/
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

/*This is to copy the ctrl+c to clipboard, and ctrl+v to paste to clipboard.*/
if (_isIE)
mygrid.entBox.onselectstart = function(){ return true; };

setHaasGrid(mygrid);
}

function selectRightclick(rowId,cellInd){
	mygrid.selectRowById(rowId,null,false,false);	
	var  contextMenuName ="history";	
	toggleContextMenu(""+contextMenuName+"");
}

function selectRow(rowId,cellInd) {	
	var errorMessage = messagesData.validvalues+"\n\n";	
	var okValue=  cellValue (rowId,"ok");	
	var columnId = mygrid.getColumnId(cellInd);
	var revisedPromisedDate;	
	var commentval;
 	switch (columnId)
 	{
 	// check the revised promise date field value.
 	case "ok": 
 		if( okValue == false ) 
			return;
	else
	{
		revisedPromisedDate =  cellValue(rowId,"revisedPromisedDate");		
		commentval = cellValue(rowId,"comments");
	  if (revisedPromisedDate.trim() == "")
	  {    alert(errorMessage + messagesData.revisedPromisedDate );
	  	   $("ok"+rowId).checked = false; 
		   hchstatusA["ok"+rowId] = false;
//	       mygrid.cellById(rowId,cellInd).setValue('false');
	  }
	  
	  if(commentval.length >2000)
	  {
		  alert( messagesData.maximum2000);
		  $("ok"+rowId).checked = false; 
		   hchstatusA["ok"+rowId] = false;
//	       mygrid.cellById(rowId,cellInd).setValue('false');
	  }	  
	 
	}  
 		
 		break;
 	
 	default:   
 	};  	  
}

function showHistory(){
 
   var HaasPO = cellValue(mygrid.getSelectedRowId(),"radianPo");	
   var POLine = cellValue(mygrid.getSelectedRowId(), "poLine");	  
   var now = new Date();   
   loc = "polineexpeditehistory.do?radianPo="+ HaasPO + "&poLine=" + POLine + "&action=search";
   openWinGeneric(loc,"POLineHistoryPage"+HaasPO.replace(/[.]/,"_")+"","800","450","yes","50","50","no")  ;
}

function showItemHistory(){	
   
   var HaasPO = cellValue(mygrid.getSelectedRowId(),"radianPo");	 
   var POLine = cellValue(mygrid.getSelectedRowId(), "poLine");	   	
   var itemId = cellValue(mygrid.getSelectedRowId(), "itemId");  
   var now = new Date();   
   loc = "polineexpeditehistory.do?itemId="+ itemId + "&action=search"+ "&opsEntityId="+$v('opsEntity');
   openWinGeneric(loc,"POLineItemHistoryPage"+HaasPO.replace(/[.]/,"_")+"","800","450","yes","50","50","no") ;
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
    openWinGeneric(loc,"showradianPo"+HaasPO.replace(/[.]/,"_")+"","800","600","yes","50","50");
  }
}




// Function to sort date  and other fields on the grid.
function sortValues(ind,type,direction)
{
	
	var columnId = mygrid.getColumnId(ind);
	var colIndex;
	switch (columnId)
 	{
 	case "orderDateDisplay":
 		colIndex=mygrid.getColIndexById("orderDate");
 		break;
 	case "needDateDisplay":
 		colIndex=mygrid.getColIndexById("needDate"); 
 		break;
 	case "promisedDateDisplay":
 		colIndex=mygrid.getColIndexById("promisedDate"); 
 		break;	
	case "lastRevisedDisplay":
 		colIndex=mygrid.getColIndexById("lastRevised"); 
 		break;
	case "revisedPromisedDate":
 		colIndex=mygrid.getColIndexById("hiddenRevisedPromisedDate"); 
 		break;
	case "daysLateDisplay":
 		colIndex=mygrid.getColIndexById("daysLate"); 
 		break;
	case "vendorShipDateDisplay":
 		colIndex=mygrid.getColIndexById("hiddenvendorShipDate"); 
 		break;
	case "supplierDateAccepted":
 		colIndex=mygrid.getColIndexById("supplierDateAcceptedSort"); 
 		break;
 	default:
 		return true;   // Do not block normal sorting
 	};  
	mygrid.sortRows(colIndex,type,direction);         //sort grid by the column with prepared values
	mygrid.setSortImgState(true,ind,direction);    //set a correct sorting image
	return false; //block default sorting	
	
	//lastRevised
}

// Function for updating records.
function updateExpedite()
{
    /*Set any variables you want to send to the server*/
    if(validateForm()) {
		document.poExpeditingForm.target='';
		document.getElementById('action').value = 'update';
		parent.showPleaseWait();
	    mygrid.parentFormOnSubmit(); //prepare grid for data sending	 
	    document.poExpeditingForm.submit(); 
    }
    else
    	return false;
}

function validateForm() {
	var count = 0;

	for(var i = 1;i <= mygrid.getRowsNum();i++){
		if(cellValue(i,"ok")+'' == 'true')	{
			count++;
			break;
		}
	}

	if (count == 0) {
		alert(messagesData.pleaseselectarowforupdate);
		return false;
	}else 
		return true;
}

function showPODocuments()
{ 
	var haasPO = cellValue(mygrid.getSelectedRowId(),"radianPo");
	//var poLine = cellValue(mygrid.getSelectedRowId(),"poLine");
	var inventoryGroup = cellValue(mygrid.getSelectedRowId(),"inventoryGroup");
 var loc = "/tcmIS/supply/showpodocuments.do?showDocuments=Yes"+
           "&radianPo="+haasPO+"&inventoryGroup="+inventoryGroup+"";
 try {
 	parent.children[parent.children.length] = openWinGeneric(loc,"showAllPoDocuments","450","300","no","80","80");
 } catch (ex){
 	openWinGeneric(loc,"showAllPoDocuments","450","300","no","80","80");
 }
}


