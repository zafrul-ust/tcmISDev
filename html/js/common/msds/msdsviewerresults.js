var beanGrid;
var selectedRowId = null;

var resizeGridWithWindow = true;
var showMsds = false;

function resultOnLoad() {
/*    
    if (parent.$v("hideOrShowDiv") == 'show') {
		topSectionHeight = 445;
    }
    else {
    	topSectionHeight = 102;
    }	
    
	parent.$("searchFrameDiv").style.height = topSectionHeight;
  	parent.$("searchHeight").value = topSectionHeight;
 */
  	totalLines = document.getElementById("totalLines").value;
    if (totalLines > 0) {
        document.getElementById("msdsViewerBeanDiv").style["display"] = "";
       try {
	        if($v("showMixture") == 'Y') 
	            initializeLockHeedRowSpanGrid();
	        else 
	            initializeNonLockheedRowSpanGrid();
	            
       }
       catch(ex) {}
         
    }else {
        document.getElementById("msdsViewerBeanDiv").style["display"] = "none";
    }
	
	displayGridSearchDuration();
        /*It is important to call this after all the divs have been turned on or off.*/
    setResultFrameSize();
    
    try {
	    if($v("showFacilityUseCode") == 'Y') 
		     parent.$('mainUpdateLinks').style["display"] = "";
		else 
		     parent.$('mainUpdateLinks').style["display"] = "none";
	} catch(ex) {}
	
}

function initializeLockHeedRowSpanGrid(){
	//initialize grid
	beanGrid = new dhtmlXGridObject('msdsViewerBeanDiv');

	//setting smart rendering
	beanGrid.enableSmartRendering(true);
	beanGrid._haas_row_span = true;
	beanGrid._haas_row_span_map = rowSpanMap;
	beanGrid._haas_row_span_class_map = rowSpanClassMap;
	beanGrid._haas_row_span_cols = rowSpanCols;
	beanGrid._haas_row_span_lvl2 = true;
	beanGrid._haas_row_span_lvl2_map = rowSpanLvl2Map;
	beanGrid._haas_row_span_lvl2_cols = rowSpanLvl2Cols;
	beanGrid._haas_row_span_lvl3 = true;
	beanGrid._haas_row_span_lvl3_map = rowSpanLvl3Map;
	beanGrid._haas_row_span_lvl3_cols = rowSpanLvl3Cols;
	
	beanGrid._haas_row_span_lvl3_child_select = true;
	
	initGridWithConfig(beanGrid,config,true,true);
	//parse data
	if( typeof( jsonMainData ) != 'undefined' ) {
		beanGrid.parse(jsonMainData,"json");
 	}
 	
 	beanGrid.attachEvent("onRowSelect",selectRow);
    beanGrid.attachEvent("onRightClick",selectRightclick);
}

function initializeNonLockheedRowSpanGrid(){
	//initialize grid
	beanGrid = new dhtmlXGridObject('msdsViewerBeanDiv');

	//setting smart rendering
	beanGrid.enableSmartRendering(true);
	beanGrid._haas_row_span = true;
	beanGrid._haas_row_span_map = rowSpanMap;
	beanGrid._haas_row_span_class_map = rowSpanClassMap;
	beanGrid._haas_row_span_cols = rowSpanCols;
	beanGrid._haas_row_span_lvl2 = true;
	beanGrid._haas_row_span_lvl2_map = rowSpanLvl2Map;
	beanGrid._haas_row_span_lvl2_cols = rowSpanLvl2Cols;
	
	beanGrid._haas_row_span_lvl2_child_select = true;
	
	initGridWithConfig(beanGrid,config,true,true);
//	beanGrid._haas_row_span_lvl3 = false;
	//parse data
	if( typeof( jsonMainData ) != 'undefined' ) {
		beanGrid.parse(jsonMainData,"json");
 	}
 	
 	beanGrid.attachEvent("onRowSelect",selectRow);
    beanGrid.attachEvent("onRightClick",selectRightclick);
}

function selectRow(rowId,cellInd) {
	selectedRowId = rowId;
	
	if(showMsds) {
		viewMsds();
		showMsds = false;
	}
}

function selectRightclick(rowId,cellInd) {
  beanGrid.selectRowById(rowId,null,false,false);
  selectRow(rowId,cellInd);
  
  var columnId = beanGrid.getColumnId(cellInd);
  
  vitems = new Array();
  if("Y" == cellValue(rowId,"msdsOnLine")) {
  	vitems[vitems.length ] = "text="+messagesData.viewmsds+";url=javascript:viewMsds();";
  }
  vitems[vitems.length ] = "text="+messagesData.showstoragelocations+";url=javascript:showStorageLocationMenu();";

  if(cellInd >= haasGrid.getColIndexById("customerMixtureNumber") && cellValue(rowId,"customerMixtureNumber").length > 0)
  	vitems[vitems.length ] = "text="+messagesData.viewkitmsds+";url=javascript:viewKitMsds();";

  if(vitems.length == 0)
  	toggleContextMenu('contextMenu');
  else {
  	replaceMenu('rightClickMenu',vitems);  
   	toggleContextMenu('rightClickMenu');
  }
  
}

function replaceMenu(menuname,menus){
	  var i = mm_returnMenuItemCount(menuname);

	  for(;i> 1; i-- )
			mm_deleteItem(menuname,i);

	  for( i = 0; i < menus.length; ){
 		var str = menus[i];

 		i++;
		mm_insertItem(menuname,i,str);
		// delete original first item.
    	if( i == 1 ) mm_deleteItem(menuname,1);
      }
}


function showStorageLocationMenu() {
	var url = 'storagelocations.do?materialId='+ beanGrid.cells(selectedRowId,beanGrid.getColIndexById("materialId")).getValue() +
			'&facilityId=' + encodeURIComponent(parent.$v('facilityId')) +
			'&facilityName=' + encodeURIComponent(parent.$("facilityId").options[parent.$("facilityId").selectedIndex].text) +
			'&application=' + 
			'&msdsNo=' + beanGrid.cells(selectedRowId,beanGrid.getColIndexById("customerMsdsNumber")).getValue() +
			'&tradeName=' + encodeURIComponent(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("tradeName")).getValue()) +
			'&mfgDesc=' + encodeURIComponent(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("mfgDesc")).getValue()) +
			'&desc=' + encodeURIComponent(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("materialDesc")).getValue()) ;

	openWinGeneric(url,"storageLocations","500","500",'yes' );
}

function viewMsds() {
	
	var facId = parent.$v('facilityId');
	
	if(newMsdsViewer)
		openWinGeneric('viewmsds.do?act=msds'+
			'&materialId='+ beanGrid.cells(selectedRowId,beanGrid.getColIndexById("materialId")).getValue() +
			'&spec=' + // do we need to know spec id?
			'&facility=' + facId +
			'&catpartno='
			,"ViewMSDS","1024","720",'yes' );
	else
    	openWinGeneric('ViewMsds?act=msds'+
			'&id='+ beanGrid.cells(selectedRowId,beanGrid.getColIndexById("materialId")).getValue() +
			'&spec=' + // do we need to know spec id?
			'&facility=' + facId +
			'&catpartno='
			,"ViewMSDS","1024","720",'yes' );
}

function viewKitMsds() {
	var reportLoc = "/HaasReports/report/printConfigurableReport.do"+
                    "?pr_companyId="+beanGrid.cells(selectedRowId,beanGrid.getColIndexById("companyId")).getValue()+
                    "&pr_custMsdsDb="+encodeURIComponent(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("customerMsdsDb")).getValue())+
                    "&pr_custMsdsNo="+beanGrid.cells(selectedRowId,beanGrid.getColIndexById("customerMixtureNumber")).getValue()+
                    "&rpt_ReportBeanId=MSDSKitReportDefinition";
	openWinGeneric(reportLoc,"viewKitMsds","800","550","yes","100","100");
	
}
