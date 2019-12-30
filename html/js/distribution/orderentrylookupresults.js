var beangrid;
var selectedRowId = null;
var windowCloseOnEsc = true;

var resizeGridWithWindow = true;

function resultOnLoad()
{
try{

parent.document.getElementById("selectedRow").innerHTML="";

 var creditHoldCount = $("creditHoldCount").value;
 

if (!showUpdateLinks || (creditHoldCount*1 == 0)) //Dont show any update links if the user does not have permissions
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else if (creditHoldCount*1 > 0)
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
 
 }
 catch(ex)
 {}	
	
 totalLines = document.getElementById("totalLines").value;
 
 if (totalLines > 0)
 {
  document.getElementById("orderEntityViewBean").style["display"] = "";
  
  initializeGrid();  
 }  
 else
 {
   document.getElementById("orderEntityViewBean").style["display"] = "none";   
 }

 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('orderEntityViewBean');

	initGridWithConfig(beangrid,config,false,true);
	if( typeof( jsonMainData ) != 'undefined' ) {		
		
		beangrid.parse(jsonMainData,"json");
	}
	
	beangrid.attachEvent("onRightClick",selectRightclick);
	beangrid.attachEvent("onRowSelect",doOnRowSelected);
	
}





function validateForm()
{
	return true;
}




function selectRightclick(rowId,cellInd){
	beangrid.selectRowById(rowId,null,false,false);
	doOnRowSelected(rowId,cellInd);
	toggleContextMenu("openscratchpad");

}



function doOnRowSelected(rowId,cellInd) {
	 var columnId = beangrid.getColumnId(cellInd);  
	 selectedRowId = rowId;
	 var mrNumber = beangrid.cellById(rowId, beangrid.getColIndexById("prNumber")).getValue();
	 var orderType = beangrid.cellById(rowId, beangrid.getColIndexById("type")).getValue();
	 var selectedMR = parent.window.document.getElementById("selectedRow");
	 if (showUpdateLinks)
	 { 
		 selectedMR.innerHTML = "<a href=\"#\" onclick=call('openScratchPadsOnRightClick'); return false;>"+messagesData.open+' '+orderType+" : "+mrNumber+"</a>";
	 }	
	 
}

function openScratchPadsOnRightClick() {
	var prNumber = cellValue(beangrid.getSelectedRowId(),"prNumber");
    var type = cellValue(beangrid.getSelectedRowId(),"type");
    var tabId = 'scratchPad'+prNumber+'';
    var loc = "/tcmIS/distribution/scratchpadmain.do?uAction=searchScratchPadId&scratchPadId="+prNumber+"&tabId="+encodeURIComponent(tabId);
    
	try
	{
		if (type == "Quote")
        {
            parent.parent.openIFrame(tabId,loc,'Q '+prNumber+'','','N');
        }
        else if (type == "Blanket Order")
        {
            parent.parent.openIFrame(tabId,loc,'B '+prNumber+'','','N');
        }
        else if (type == "MR")
        {
            parent.parent.openIFrame(tabId,loc,'MR '+prNumber+'','','N');
        }
        if (type == "Scratch Pad")
        {
            parent.parent.openIFrame(tabId,loc,'SP '+prNumber+'','','N');
        }
    }
	catch (ex)
	{
		openWinGeneric(loc,"scratchPad"+prNumber,"900","600","yes","80","80","yes");
	}
}
