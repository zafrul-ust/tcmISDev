/************************************NEW***************************************************/
var mygrid;
/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

var inputSize= new Array();
inputSize={"receiverName":30,"lotStatus":25,"itemId":15};

var maxInputLength = new Array();
maxInputLength={"receiverName":20,"lotStatus":20,"itemId":10};


//Global variable: specially useful for right-click
var selectedRowId = null;

//Set this to be false if you don't want the grid width to resize based on
//window size.

//Allow different permissions for different columns
var multiplePermissions = true;

//Build up the array for the columns which use different permissions
/*var permissionColumns = new Array();
permissionColumns = {
     "deliveryType" : true,
     "revisedPromisedDate" : true,
     "expediteAge" : true,
     "inventoryGroup" : true,
     "comments" : true
};*/

/*TODO-
      - Block ctrl+mouse wheel on whole page
      -right click to print receipts,BOl,.etc
* */

/*This is really the same as before. Except now there is a call to initialize the grid.*/
function resultOnLoad()
{
 try{
 if (!showUpdateLinks) 
 {
  parent.document.getElementById("mainUpdateLinks").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("mainUpdateLinks").style["display"] = "";
 }
 }
 catch(ex)
 {}

 totalLines = document.getElementById("totalLines").value;
 if (totalLines > 0)
 {
  document.getElementById("transactionTrackingViewBean").style["display"] = "";
 // doInitGrid();
 }
 else
 {
   document.getElementById("transactionTrackingViewBean").style["display"] = "none";
 }


 displayGridSearchDuration();

 setResultFrameSize();
}

/*Set this to be false if you don't want the grid width to resize based on window size.
* You will also need to set the resultsMaskTable width appropriately in the JSP.*/
var resizeGridWithWindow = true;


function selectRightclick(rowId,cellInd){
	beanGrid.selectRowById(rowId,null,false,false);
	// The right click event needs to call selectRow function.
	doOnRowSelected(rowId,cellInd);
	// Show right-click menu
    toggleContextMenu('rightClickMenu');
}


// validate the whole grid
function validationforUpdate() {
	var rowsNum = beanGrid.getRowsNum();

    for (var p = 1; p < (rowsNum+1) ; p ++)
    {
    	if (validateLine(p) == false) {
    		beanGrid.selectRowById(p,null,false,false);	// Make the selected row fall on the one which does pass the validation
			return false;
		}
    }

    return true;
}

/*function doInitGrid(){
	mygrid = new dhtmlXGridObject('transactionTrackingViewBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	// if rowSpan == true, sorting and smart rendering won't work; if
	// rowSpan == false, sorting and smart rendering will work
	// rowSpan == -1 is recommended for the page with update function
	// -1 is for disable rowSpan and smart rendering, but sorting still
	// works; false will disable rowSpan and sorting but smart rendering is
	// enabled
	// set submitDefault to true: Send the data to the server
	// singleClickEdit: this is for type:"txt",
	initGridWithConfig(mygrid, config, -1, true, true);
	if (typeof (jsonMainData) != 'undefined') {		
		mygrid.parse(jsonMainData, "json");
	}

	// set all kinds of event for the grid. refer to http://www.dhtmlx.com
	// for more events
	// mygrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	mygrid.attachEvent("onBeforeSorting",onBeforeSorting);
	mygrid.attachEvent("onRowSelect",doOnRowSelected);
	mygrid.attachEvent("onRightClick",selectRightclick);
	mygrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
}*/

function resultOnLoadWithGrid(gridConfig)
{
 initGridWithGlobal(gridConfig);
 if(mygrid != null)
	 mygrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
 //This dislpays our standard footer message
 resultOnLoad();
}

/*
 * Grid event onBeforeSorting function
 */
function onBeforeSorting(ind,type,direction){
	var columnId = mygrid.getColumnId(ind);
	var colIndex;
 	switch (columnId)
 	{
 	case "dateOfReceipt":
 		colIndex=mygrid.getColIndexById("hiddenDateOfReceipt");
 		break;
 	case "transactionDate":
 		colIndex=mygrid.getColIndexById("hiddenTransactionDate"); 
 		break;
 	case "dateDelivered":
		colIndex=mygrid.getColIndexById("hiddenDateDelivered"); 
		break;
 	default:
 		return true;   // Do not block normal sorting
 	};  
	mygrid.sortRows(colIndex,type,direction);         //sort grid by the column with prepared values
	mygrid.setSortImgState(true,ind,direction);    //set a correct sorting image
	return false; //block default sorting
}

/*
 * Grid event OnBeforeSelect function
 * Save the row class of currently 
 * selected row, before class changes.
 */
var saveRowClass = null;
function doOnBeforeSelect(rowId,oldRowId) {
	// set the color for previous row
	if (oldRowId != 0) {
		setRowClass(oldRowId, saveRowClass);
	}
	saveRowClass = getRowClass(rowId);
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		overrideDefaultSelect(rowId,saveRowClass);

	return true;
}
var oldRowId = null;
function doOnRowSelected(rowId,cellInd) {
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		setRowClass(rowId,''+saveRowClass+'Selected');
	var columnId = mygrid.getColumnId(cellInd);
 	switch (columnId)
 	{
 	case "receiptId":
 		doPrintrelabel();
 		break;
    case "lineItem1":
 		doPrintBol();
 		break;
    case "batch":
 		printPackingList();
 		break;     
     /*case "dateDelivered":
 		var batch = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("batch")).getValue();
 		if (batch.length > 0) {
 			printPackingList(batch);
 		}
 		break;	 */		
 	default:
 	};  	
}

/*
 * Grid event onRightClick function
 */
function selectRightclick(rowId,cellInd){
	mygrid.selectRowById(rowId,null,false,false);	
	var  contextMenuName ="transShortMenu";
	var mrline = cellValue(rowId,"lineItem1");
    var batch = cellValue(rowId,"batch");    
    var distributorOps = cellValue(rowId,"distributorOps");
    var shipid = cellValue(rowId,"shipmentId");
    
    if (mrline.length > 0 && mrline != '&nbsp;' ) {
    	if( distributorOps == 'Y' && shipid )
            contextMenuName= "transCompleteBatchMenuMiami";
    	else if (batch.length > 0 && batch != '&nbsp;')
        {
        		contextMenuName= "transCompleteBatchMenu";
        }
        else
        {
            contextMenuName= "transCompleteMenu";
        }
    }
    
    mm_deleteItemByText(messagesData.receivingQcCheckList);
    if (cellValue(rowId,'receivingStatus').toLowerCase() == 'binned' )
		mm_insertItem(contextMenuName,mm_returnMenuItemCount(contextMenuName),"text="+messagesData.receivingQcCheckList+";url=javascript:openChecklist();");
  
    mm_deleteItemByText(messagesData.printRTKLabel);
    mm_insertItem(contextMenuName,mm_returnMenuItemCount(contextMenuName),"text="+messagesData.printRTKLabel+";url=javascript:printRTKLabel();");    
    
	toggleContextMenu(""+contextMenuName+"");
}

function openChecklist() {
	var receiptId = cellValue(mygrid.getSelectedRowId(), "receiptId");
	var now = new Date();
	var loc = "/tcmIS/hub/receivingqcchecklist.do?openerPage=rStatus&searchType=is&sort=Bin&searchWhat=Receipt%20Id&search=" + receiptId;
	loc += "&sourceHub=" + $v("hub");
	loc += "&opsEntityId=" + $v("opsEntityId");
	loc += "&startSearchTime=" + now.getTime();
	openWinGeneric(loc, "receivingQcCheckList" + receiptId, "1000", "950", "yes", "80", "80");

}

/************************************NEW***************************************************/

/************************************MODIFIED***************************************************/

function doPrintrelabel()
{
   var receiptId = cellValue(mygrid.getSelectedRowId(),"receiptId");
   var reLabelUrl = "/tcmIS/Hub/reprintnoinvenlabels?session=Active&generate_bols=yes";
   var paperSize  =  "";
   reLabelUrl += "&boldetails=" + paperSize ;
   reLabelUrl += "&receiptId=" + receiptId ;

   var branchPlant = document.getElementById("hub");
   reLabelUrl += "&HubNoforlabel=" + branchPlant.value ;

   openWinGeneric(reLabelUrl,"Generate_relabels","640","600","yes")
}

// *********************************************************
// showBinHistory()
//
function showBinHistory()
{
	var receiptId = cellValue(mygrid.getSelectedRowId(),"receiptId");
    // var testurl3 = "/tcmIS/Hub/Transactions?session=Active&UserAction=binHistory";
    var binHistoryurl = "receiptbinhistory.do"
    binHistoryurl += "?receiptId=" + receiptId ;

    openWinGeneric(binHistoryurl,"showBinHistory","450","400","yes")
}

// *********************************************************
// doPrintBol()
//
function doPrintBol()
{	
	//Get mygrid MR line value
	var mrline = cellValue(mygrid.getSelectedRowId(),"lineItem1");
	if (mrline.length > 0) {
		var pr = mrline.split('-');
		var picklistId = cellValue(mygrid.getSelectedRowId(),"picklistId");
		var batch = cellValue(mygrid.getSelectedRowId(),"batch");
		var prbatch='';
		if (picklistId.length > 0 && batch.length == 0) {
			prbatch=picklistId;
		} else if (batch.length > 0 && picklistId.length == 0) {
			prbatch=batch;
		}
	    if (pr[0].length > 0 && pr[1].length > 0 && prbatch.length > 0)
	    {
		    var printBolUrl = "/tcmIS/Hub/Transactions?session=Active&useraction=generatebol";
		    var paperSize  =  "";
		    printBolUrl += "&prnumber=" + pr[0] ;
		    printBolUrl += "&prlineitem=" + pr[1] ;
		    printBolUrl += "&prbatch=" + prbatch ;
		
		    openWinGeneric(printBolUrl,"Generate_rebols","640","600","yes")
	    }
	}
}

// *********************************************************
// getReceiptNotes()
//
function getReceiptNotes()
{
	var receiptId=cellValue(mygrid.getSelectedRowId(),"receiptId");
    // var loc = "/tcmIS/Hub/Logistics?session=Active&UserAction=addReceiptNotes";
    var loc = "/tcmIS/hub/updatereceiptnotes.do";

    if (receiptId.length > 0)
    {
       loc = loc + "?receiptId=" + receiptId;
       openWinGeneric(loc,"ReceiptNotes","550","250","yes")
    }
}

function printPackingList()
{    
    var batch=cellValue(mygrid.getSelectedRowId(),"batch");
    if (batch.length > 0)
    {
     var launchUrl = "nopicklistpackinglist.do?userAction=nopicklistpackinglist&batchNumber="+batch;
     openWinGeneric(launchUrl,"Generate_Boxlabels","800","600","yes")
    }
}

function printPackingListMiami()
{    
    var batch=cellValue(mygrid.getSelectedRowId(),"shipmentId");
    if (batch.length > 0)
    {
//    	http://192.168.18.176/HaasReports/report/printdeliverydocument.do?documentType=mr&idField=1104306
     var launchUrl = "/HaasReports/report/printdeliverydocument.do?documentType=shipmentId&idField="+batch;
     openWinGeneric(launchUrl,"Generate_Boxlabels","800","600","yes")
    }
}

function printBoxLabels() {
 var mrline = cellValue(mygrid.getSelectedRowId(),"lineItem1");
 var picklistId = cellValue(mygrid.getSelectedRowId(),"picklistId");
 if (mrline.length > 0) {
  var pr = mrline.split('-');
  if (pr[0].length > 0 && pr[1].length > 0)
  {
   openWinGeneric('/tcmIS/common/loadingfile.jsp', '_GenerateLabels','800','600','yes',"80","80");
    var a = window.setTimeout("generatelabelsCallback("+pr[0]+","+pr[1]+","+picklistId+")", 1000);
  }
 }
}

function generatelabelsCallback(prNumber,lineItem,pickListId) {
	var generatingForm = document.generateLabelsForm;
    var receiptIdFieldName = "picklistBean[0].prNumber";
    var quantityReceivedFieldName = "picklistBean[0].lineItem";
    var printOkName = "picklistBean[0].ok";
    removeFieldFromFormIfExtant(generatingForm, receiptIdFieldName);
    addFieldToForm(generatingForm, receiptIdFieldName, prNumber);
    removeFieldFromFormIfExtant(generatingForm, quantityReceivedFieldName);
    addFieldToForm(generatingForm, quantityReceivedFieldName,lineItem);
    removeFieldFromFormIfExtant(generatingForm, printOkName);
    addFieldToForm(generatingForm, printOkName,'0');

	generatingForm.picklistId.value = pickListId;
    generatingForm.action.value = 'printBoxLabels';
    generatingForm.target = "_GenerateLabels";
	generatingForm.submit();
}

function addFieldToForm(form, field, value) {
	var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", field);
        hiddenField.setAttribute("id", field);
        hiddenField.setAttribute("value", value);
        form.appendChild(hiddenField);
}

function removeFieldFromFormIfExtant(form, fieldId) {
	var field = document.getElementById(fieldId);
	if (field != null) {
		form.removeChild(field);
	}
}

function showPickingDocuments() {
	var issueId = cellValue(mygrid.getSelectedRowId(),"issueId");
	var receiptId = cellValue(mygrid.getSelectedRowId(),"receiptId");
	
	var loc = "pickingunitdocuments.do?issueId="+issueId+"&receiptId="+receiptId;
    openWinGeneric(loc,"Picking_Unit_Documents","800","400","yes")
}

function printRTKLabel() {
	var itemId =  cellValue(mygrid.getSelectedRowId(),"itemId");
	var labelQuantity =  cellValue(mygrid.getSelectedRowId(),"quantity");

	var reportLoc = "printrtklabels.do"
    	+ "?itemId="+itemId
		+ "&labelQuantity="+labelQuantity;	
	openWinGeneric(reportLoc,"printRTKLabels","300","200","yes","200","200");
}