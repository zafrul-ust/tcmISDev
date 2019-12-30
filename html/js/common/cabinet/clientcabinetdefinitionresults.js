var beanGrid;
var resizeGridWithWindow = true;

function myOnload()
{   
 	if ($v("totalLines") > 0) {
		$("cabinetDefinitionDiv").style["display"] = "";
  		initializeGrid();
 	}else {
		$("cabinetDefinitionDiv").style["display"] = "none";
 	}
	displayGridSearchDuration();
	setResultFrameSize();
}

function initializeGrid(){
	beanGrid = new dhtmlXGridObject('cabinetDefinitionDiv');
	initGridWithConfig(beanGrid,config,true,true);

	beanGrid.enableSmartRendering(true);
	beanGrid._haas_row_span = true;
	beanGrid._haas_row_span_map = lineMap;
	beanGrid._haas_row_span_class_map = lineMap3;
	beanGrid._haas_row_span_cols = [0,1,2,3,4,5];

	if (lineMap2) {
		beanGrid._haas_row_span_lvl2 = true;
		beanGrid._haas_row_span_lvl2_map = lineMap2;
		beanGrid._haas_row_span_lvl2_cols = [6,7,8];
	}

	if( typeof( jsonMainData ) != 'undefined' ) {
		beanGrid.parse(jsonMainData,"json");
	}
	//beanGrid.attachEvent("onRowSelect",selectRow);
	//beanGrid.attachEvent("onRightClick",selectRow);
}