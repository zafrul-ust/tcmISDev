windowCloseOnEsc = true;
var selectedRow = 0;

function selectRow(rowId,cellInd) {
	selectedRow = rowId;
	var selectedPart = parent.document.getElementById("updateResultLink");
	selectedPart.style["display"] = "";
	selectedPart.innerHTML = '<a href="#" onclick="call(\'returnSelectedRow\'); return false;">'+messagesData.selectedPart+' : '+ cellValue(rowId,"catPartNo") +'</a>';
}

function returnSelectedRow() {
	try {
		var catPartNo = cellValue(selectedRow,"catPartNo");
		var itemId = cellValue(selectedRow,"itemId");
		var itemDesc = cellValue(selectedRow,"itemDesc");
		var catalogId = cellValue(selectedRow,"catalogId");
		var partGroupNo = cellValue(selectedRow,"partGroupNo");
		var opener = parent.opener;
        opener.partAdded(catPartNo,itemId,itemDesc,catalogId,partGroupNo);
  	} catch(exx) {
  		//alert("Unable to add part -> " + exx + " : " + exx.stack);
  	}
  parent.close();
}

var beangrid;
var resizeGridWithWindow = true;

function resultOnLoad(){
 totalLines = document.getElementById("totalLines").value;
 if (totalLines > 0) {
  document.getElementById("beanData").style["display"] = "";
  initializeGrid();
 }
 displayGridSearchDuration();
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('beanData');
	initGridWithConfig(beangrid,config,true,true);
	beangrid.attachEvent("onRowSelect",selectRow);
	beangrid.attachEvent("onRightClick",selectRow);
}
