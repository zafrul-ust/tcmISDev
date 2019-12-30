windowCloseOnEsc = true;
var currentPart = '';
var currentPartGroupNo = '';
function selectRow()
{
	rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

	currentPart = cellValue(rowId0,"catPartNo");
	currentPartGroupNo = cellValue(rowId0,"partGroupNo");
	var selectedPart = parent.document.getElementById("updateResultLink");
	selectedPart.style["display"] = "";
	selectedPart.innerHTML = '<a href="#" onclick="call(\'selectData\'); return false;">'+messagesData.selectedPart+' : '+ currentPart +'</a>';
}

function selectData() {
	try {
        parent.opener.replacementPartChanged(htmlDencode(currentPart),currentPartGroupNo,cellValue(rowId0,"customerTemperatureId"));
  	} catch(exx) {}
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

	beangrid.enableSmartRendering(true);
	beangrid._haas_row_span = true;
	beangrid._haas_row_span_map = lineMap;
	beangrid._haas_row_span_class_map = lineMap3;
	beangrid._haas_row_span_cols = [0,1];

	if (lineMap2) {
		beangrid._haas_row_span_lvl2 = true;
		beangrid._haas_row_span_lvl2_map = lineMap2;
		beangrid._haas_row_span_lvl2_cols = [3,4];
	}
	if( typeof( jsonMainData ) != 'undefined' ) {
		beangrid.parse(jsonMainData,"json");
	}
	beangrid.attachEvent("onRowSelect",selectRow);
	beangrid.attachEvent("onRightClick",selectRow);
}