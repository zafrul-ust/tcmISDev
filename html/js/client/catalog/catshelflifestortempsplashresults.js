var mygrid;
var resizeGridWithWindow = true;


function newinit() {
	totalLines = document.getElementById("totalLines").value;

	if (totalLines > 0) {
		document.getElementById("prCatalogScreenSearchBean").style["display"] = "";
		parent.document.getElementById("footer").style["display"] = "";
		/*this is the new part.*/
		doInitGrid();
	}else {
		document.getElementById("prCatalogScreenSearchBean").style["display"] = "none";
		parent.document.getElementById("footer").style["display"] = "none";
	}

	parent.myResultFrameId = "resultFrame";

	setResultFrameSize();

	
} //end of method

function doInitGrid(){
	/*Give the div name that holds the grdi the same name as your dynabean*/
	mygrid = new dhtmlXGridObject('prCatalogScreenSearchBean');
	mygrid.setImagePath("/dhtmlxGrid/codebase/imgs/");
	/*To internationalize column headers, get the values from messagesData*/
	var colVAlign = "middle,middle,middle,middle,middle,middle,middle,middle";

	var header = messagesData.part + ","+messagesData.description+ ","+messagesData.shelflife + ","+messagesData.componentdescription
	+ ","+messagesData.manufacturer + ","+messagesData.mfgpartno + ","+messagesData.spec + ","+messagesData.source
	var colAlign = "left,left,left,left,left,left,left,left";
	var colTypes = "ro,ro,ro,ro,ro,ro,ro,ro";
	var toolTips = "false,true,false,true,true,false,false,false";

	mygrid.setHeader(header);
	mygrid.setColVAlign(colVAlign);
	mygrid.setColAlign(colAlign);
	mygrid.setColTypes(colTypes);
	mygrid.enableTooltips(toolTips);

	var colIds = "catPartNo,partDescription,shelfLifeDisplay,materialDesc,mfgDesc,mfgPartNo,spec,source";
	mygrid.setColumnIds(colIds);

	var initWidths = "10,20,15,20,20,15,15,15,10";
	mygrid.setInitWidths(initWidths);

	mygrid._haas_row_span = true;
	mygrid._haas_row_span_map = rowSpanMap;
	mygrid._haas_row_span_class_map = rowSpanClassMap;		
	mygrid._haas_row_span_cols = [0,1,2];

	if (rowSpanLvl2Map) {
		mygrid._haas_row_span_lvl2 = true;
		mygrid._haas_row_span_lvl2_map = rowSpanLvl2Map;
		mygrid._haas_row_span_lvl2_cols = [6];
	}


	mygrid.enableMultiline(false);
	mygrid.attachEvent("onAfterHaasRenderRow",colorSLCell);
	mygrid.attachEvent("onRightClick",selectRow);
	mygrid.enableSmartRendering(true);
	mygrid.setSkin("haas");

	mygrid.init();
	updateColumnWidths(mygrid);
	setHaasGrid(mygrid);

	mygrid.parse(jsonMainData,"json");
	mygrid.entBox.onselectstart = function(){ return true; };


	if (_isIE)
		mygrid.entBox.onmousewheel=stop_event;

	displayGridSearchDuration();
}

function colorSLCell(rowId)
{
	 var shelfLifeDisplayCell = gridCell(mygrid, rowId, 'shelfLifeDisplay');
	 shelfLifeDisplayCell.cell.bgColor = 'yellow';
}

function selectRow() {

	rowId0 = arguments[0];
	colId0 = arguments[1];
	ee = arguments[2];

	if (ee != null) {
		if ((ee.button != null && ee.button == 2) || ee.which == 3) {
			 popup('rightClickMenu',1);
		}
	}
}
