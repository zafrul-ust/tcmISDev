var beangrid;
var selectedRowId = null;

var resizeGridWithWindow = true;

function resultOnLoad()
{
/*try{
 if (!showUpdateLinks) Dont show any update links if the user does not have permissions
 {
  document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  document.getElementById("updateResultLink").style["display"] = "";
 }
 }
 catch(ex)
 {}*/  
	if( (typeof( "materialTransferHistoryBean" ) != 'undefined') && (document.getElementById("materialTransferHistoryBean")!=null))
	{	
		 totalLines = document.getElementById("totalLines").value;
		 
		 if (totalLines > 0)
		 {
		  document.getElementById("materialTransferHistoryBean").style["display"] = "";
		  initializeGrid();  
		 }  
		 else
		 {
		   document.getElementById("materialTransferHistoryBean").style["display"] = "none";   
		 }
	
		 displayGridSearchDuration();
	 
	 	/*It is important to call this after all the divs have been turned on or off.*/
		setResultFrameSize();
	
	}
}


function initializeGrid(){
 beangrid = new dhtmlXGridObject('materialTransferHistoryBean');

 initGridWithConfig(beangrid,config,false,true);
 if( typeof( jsonMainData ) != 'undefined' ) 
 {
 beangrid.parse(jsonMainData,"json");

 }
 beangrid.attachEvent("onRowSelect",selectRow);
 beangrid.attachEvent("onRightClick",selectRightclick);
}

function selectRow(rowId,cellInd){
	selectedRowId = rowId;
}

function selectRightclick(rowId,cellInd) {
    colId0 = arguments[1];
    if (colId0 < beangrid.getColIndexById("fromMrline")) {
        toggleContextMenu("viewMr");
    }else {
        toggleContextMenu("viewEmpty");
    }
}

function viewMr() {
    var mrNumber  =  cellValue(beangrid.getSelectedRowId(),"toPrNumber");
    if ( mrNumber != null &&  mrNumber != 0) {
        var loc = "materialrequest.do?action=int&prNumber="+mrNumber;
        try{
            parent.parent.openIFrame("materialrequest"+mrNumber,loc,""+messagesData.materialrequest+" "+mrNumber,"","N");
        }catch (ex){
            openWinGeneric(loc,"materialrequest"+mrNumber,"800","600","yes","50","50");
        }
    }
}


