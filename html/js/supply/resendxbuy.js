var mygrid;
/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

var inputSize= new Array();
inputSize={"supplierAccountNumber":8,
		   "supplierSalesOrderNo":8};

function resultOnLoad()
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
  document.getElementById("resendxBuyViewBean").style["display"] = "";
  doInitGrid();  
 }  
 else
 {
   document.getElementById("resendxBuyViewBean").style["display"] = "none";   
 }

/*This dislpays our standard footer message*/
displayNoSearchSecDuration();

 /*It is important to call this after all the divs have been turned on or off.*/
 setResultSize();
}

function doInitGrid(){
/*Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates*/
mygrid = new dhtmlXGridObject('resendxBuyViewBean');
mygrid.setImagePath("/dhtmlxGrid/codebase/imgs/");
/*To internationalize column headers, get the values from messagesData.
To show tooltips in the header you can use spans to define the heading and tooltip.
We would want to show tooltips for Columns that can be updated. Make sure to escape any , in your tool tip by //,*/

mygrid.setHeader(""+"permission"+","+messagesData.haaspoline+","+messagesData.itemid+","+
				messagesData.partnumber+","+messagesData.itemdescription+","+
				messagesData.shipto+","+
				messagesData.needdate+","+messagesData.qty+","+
				messagesData.ok+","+messagesData.shipfrom+","+
				messagesData.accountnumber+",'','','',"+
				messagesData.salesorderno); 
/*Set initial widths for your columns, set the initial widths for Font Size smallest.
* We will proportionally increase the widths based on the user font size.
* Set hidden collums to size 0.*/

mygrid.setInitWidths("0,8,5,8,15,22,7,5,3,18,14,0,0,0,8");

/*You can set column alingments, all string and date values will be left aligned and numbers will be right aligned.*/
mygrid.setColAlign("left,left,right,left,left,left,left,right,left,left,left,left,left,left,left");

/*Set the type of sort to do on this column.If the gird has rowspans >1 set all columns sotring to be na.
Sorting for grid with rowspans>1 is not supported correctly yet.
Date read-only sorting is not supported yet. For date columns set the sorting type to na*/
mygrid.setColSorting("na,haasStr,int,haasStr,haasStr,haasStr,int,int,na,hcoro,haasStr,int,na,na,na");

/*Set column types, you can define and editable columns here.
* ro -read only
* ed -editable sinlge line text
* date -hcal
* select drop down -
* multiline text -
* link - hlink       
* checkbox -*/    
mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,hch,hcoro,hed,ro,ro,ro,hed");   

/*This enables tooltips on a column and on mouseover displays the value of the cell in a tooltip.
* We will enable tooltips only for columns whose width might be less than the text data in that column.
* General rule >30 characters give a tooltip.
* Most likely candidates are packaging, item desc, part desc, any user comments etc.
**/
mygrid.enableTooltips("false,false,false,false,true,true,false,false,false,false,false,false,false,false,false");

/*Set columIds. this will be the id you want your dynabean to be. this is the same as setting id attribute on an input element.*/ 
mygrid.setColumnIds("permission,poLineDisplay,itemId,supplierPartNo,itemDesc,shipToAddress,displayNeedDate,quantity,ok,shipFromLocationId,supplierAccountNumber,needDate,radianPo,poLine,supplierSalesOrderNo");

/*This tells the grid to send all data in the grid to the server in dynabean compatible format.
* You can comment this out if this is a read-only page not sending any data back to the server.*/
mygrid.submitOnlyChanged(false);
mygrid.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");  
/*Can also define which columns you want submitted to the server, avoid submitting data fields with date time in them
this will cause problems on server side.  
* */  
mygrid.submitColumns([false,false,false,false,false,false,false,true,true,true,true,false,true,true,true]);

/*****************
*set setColumnsVisibility false shows the column  -According to dhtmlx this is a feature
 Use this to hide columns which is data you need to do updates/send to server. I usually stick them at the end of the grid.
*/
mygrid.setColumnsVisibility("true,false,false,false,false,false,false,false,false,false,false,true,true,true,false");

/*This keeps the row height the same, true will wrap cell content and height of row will change.*/
mygrid.enableMultiline(false);  
/*This is used to tell the grid that we have row height set based on the font size. */
//mygrid.setAwaitedRowHeight(gridRowHeight); 
   
mygrid.attachEvent("onBeforeSorting",sortValues); 
mygrid.attachEvent("onRowSelect",selectRow);
if( hasConfirm ) {
	mygrid.attachEvent("onRightClick",selectRowRightClick);
}

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

if (_isIE)
mygrid.entBox.onselectstart = function(){ return true; };

setHaasGrid(mygrid);
}

function selectRow(rowId,cellInd){
	   
	var columnId = mygrid.getColumnId(cellInd);
   if(columnId!="shipFromLocationId")
   {
	   var errorMessage = messagesData.validvalues+"\n\n";
	   var errorCount = 0;
		var okValue=  cellValue (rowId,"ok");
		
		var accntNum;	
		var shipFrom;
		
	 	switch (columnId)
	 	{
	 	// check the revised promise date field value.
	 	case "ok":
	 		if( okValue == false ) 
				return;
		else{
			accntNum =  cellValue(rowId,"supplierAccountNumber");		
		  
		  if (accntNum.trim() == "")
		  {    errorMessage += messagesData.accountnumber +" \n";
		  		errorCount++;
		       mygrid.cellById(rowId,cellInd).setValue('false'); 
		  }
		  
		  
		  shipFrom = cellValue(rowId,"shipFromLocationId");
		  if (shipFrom.trim() == "" || shipFrom.trim().length==0)
		  {    errorMessage += messagesData.shipfrom +" \n";
		       errorCount++;
		       mygrid.cellById(rowId,cellInd).setValue('false');
		  }
		  
		  if (errorCount >0)
		  {
		    alert(errorMessage);		   
		  }
		}  
	 		break;	
	 	// Disable text area for comments and other text fields if use doesn't have permission to edit them. 	
	 	default:   
	 	};  		   
   }  
}

function selectRowRightClick(rowId,cellInd){
	   
	 selectRow(rowId,cellInd);
	 accntNum =  cellValue(rowId,"supplierSalesOrderNo");
	 $("radianPo").value = cellValue(rowId,"radianPo");
	 $("airgasCu").value = accntNum;

	 if( accntNum )
		 toggleContextMenu("confirm");
		
}

function submitSearchForm()
{
 	var flag = validateForm();
  	if(flag) 
  	{
   		showPleaseWait();
   		return true;
  	}
  	else
  	{
    	return false;
  	}
}

function validateForm()
{
	return true;
}

//Function to sort date  and other fields on the grid.
function sortValues(ind,type,direction)
{
if (ind == 4){        // if sorting for problematic column
this.sortRows(8,type,direction);         //sort grid by the column with prepared values
this.setSortImgState(true,ind,direction);    //set a correct sorting image
return false; //block default sorting
 }
else if (ind == 10){        // if sorting for problematic column
	   this.sortRows(26,type,direction);         //sort grid by the column with prepared values
	   this.setSortImgState(true,ind,direction);    //set a correct sorting image
	   return false; //block default sorting
	    } 

return true;      
}

//Function for updating records.
function update()
{
    /*Set any variables you want to send to the server*/
	document.genericForm.target='';
	document.getElementById('action').value = 'update';
	showPleaseWait();
    mygrid.parentFormOnSubmit(); //prepare grid for data sending	 
    document.genericForm.submit(); 

}




