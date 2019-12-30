windowCloseOnEsc = true;

var beangrid;
var resizeGridWithWindow = true;
var selectedRowId = null;

function resultOnLoad()
{
 totalLines = document.getElementById("totalLines").value;
 
 if (totalLines > 0)
 {
  document.getElementById("partViewBean").style["display"] = "";

  initializeGrid();  
 }  
 else
 {
	document.getElementById("partViewBean").style["display"] = "none";
 }
      
 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();

  if (totalLines == 0) {
		parent.document.getElementById('mainUpdateLinks').style["display"] = "";
  }
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('partViewBean');

	initGridWithConfig(beangrid,config,false,false);
	if( typeof( jsonMainData ) != 'undefined' ) {		
		beangrid.parse(jsonMainData,"json");
	}	
	beangrid.attachEvent("onRowSelect",doOnRowSelected);
	beangrid.attachEvent("onRightClick",selectRightclick);
//	setTimeout('loadRowSpans()',100); 
}

function doOnRowSelected(rowId,cellInd) {

	selectedRowId = rowId;
	var selectedPart = parent.document.getElementById("selectedPart");

	selectedPart.style["display"] = "";
 
	selectedPart.innerHTML = '<a href="#" onclick="selectedPartNumberCall(); return false;">'+messagesData.returncatalogitem+' : '+cellValue(rowId,'alternateName')+'</a> ';

	parent.selecteddescription = cellValue(rowId,'description'); 
    parent.selectedItemId = cellValue(rowId,'itemId');

    setResultFrameSize();
}


function selectRightclick(rowId,cellInd){
	selectedRowId = rowId;
	beangrid.selectRowById(rowId,null,false,false);	
	doOnRowSelected(rowId,cellInd);
}

