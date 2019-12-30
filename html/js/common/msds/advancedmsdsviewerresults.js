var beanGrid;
var selectedRowId = null;
var windowCloseOnEsc = true;
resizeGridWithWindow = true;
var showMsds = false;

function resultOnLoad() {
/*    
    if (opener.$v("hideOrShowDiv") == 'show') {
		topSectionHeight = 445;
    }
    else {
    	topSectionHeight = 102;
    }	
    
	opener.$("searchFrameDiv").style.height = topSectionHeight;
  	opener.$("searchHeight").value = topSectionHeight;
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
    $('endSearchTime').value = new Date().getTime();
    displayNoSearchSecDuration();
   /* if (typeof( extraReduction ) != 'undefined')
    	setResultSize(extraReduction);
    else*/
    	setResultSize();
    
    
    
    try {
	    if($v("showFacilityUseCode") == 'Y') 
		     $('mainUpdateLinks').style["display"] = "";
		else 
		    $('mainUpdateLinks').style["display"] = "none";
	    $('resultGridDiv').style["display"] = "";
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
	  //initGridWithGlobal(gridConfig); 
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
			'&facilityId=' + encodeURIComponent(opener.$v('facilityId')) +
			'&facilityName=' + encodeURIComponent(opener.$("facilityId").options[opener.$("facilityId").selectedIndex].text) +
			'&application=' + 
			'&msdsNo=' + beanGrid.cells(selectedRowId,beanGrid.getColIndexById("customerMsdsNumber")).getValue() +
			'&tradeName=' + encodeURIComponent(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("tradeName")).getValue()) +
			'&mfgDesc=' + encodeURIComponent(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("mfgDesc")).getValue()) +
			'&desc=' + encodeURIComponent(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("materialDesc")).getValue()) ;

	opener.window.children[opener.window.children.length] = openWinGeneric(url,"storageLocations","500","500",'yes' );
}

function viewMsds() {
	if(newMsdsViewer)
		opener.window.children[opener.window.children.length] = openWinGeneric('viewmsds.do?act=msds'+
			'&materialId='+ beanGrid.cells(selectedRowId,beanGrid.getColIndexById("materialId")).getValue() +
			'&spec=' + // do we need to know spec id?
			'&facility=' + encodeURIComponent($v('facilityId')) +
			'&catpartno='
			,"ViewMSDS","1024","720",'yes' );
	else
    	opener.window.children[opener.window.children.length] = openWinGeneric('ViewMsds?act=msds'+
			'&id='+ beanGrid.cells(selectedRowId,beanGrid.getColIndexById("materialId")).getValue() +
			'&spec=' + // do we need to know spec id?
			'&facility=' + encodeURIComponent($v('facilityId')) +
			'&catpartno='
			,"ViewMSDS","1024","720",'yes' );
}

function viewKitMsds() {
	var reportLoc = "/HaasReports/report/printConfigurableReport.do"+
                    "?pr_companyId="+beanGrid.cells(selectedRowId,beanGrid.getColIndexById("companyId")).getValue()+
                    "&pr_custMsdsDb="+encodeURIComponent(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("customerMsdsDb")).getValue())+
                    "&pr_custMsdsNo="+beanGrid.cells(selectedRowId,beanGrid.getColIndexById("customerMixtureNumber")).getValue()+
                    "&rpt_ReportBeanId=MSDSKitReportDefinition";
	opener.window.children[opener.window.children.length] = openWinGeneric(reportLoc,"viewKitMsds","800","550","yes","100","100");
	
}

function reAdjust() {
	
	  alert('stop');
	  /*var width;
	  var height;
	  var containingDiv = $('resultsPage');
	   if (_isIE)
	   {
		   width = document.documentElement.offsetWidth;
		   height = document.documentElement.offsetHeight;

	   }
	   else
		   {
		       width = window.innerWidth;
		       height = window.innerHeight;
		   }
	   	  //width -= 5;
		  containingDiv.style.width = width + 'px';
		  containingDiv.style.height = height + 'px';
		  
		  var grid1 = beanGrid;
		  
		  // Auto adjust height of List Approver div
		 // var detailDiv = document.getElementById('DetailBean');
		  if (height > 500) {
			  containingDiv.style.height = 200 + (height - 500) + 'px';
			  grid1.setSizes();
		  }
		  else if (height < 500) {
			  containingDiv.style.height = '200px';
			  grid1.setSizes();
		  }

		  resizeGridToWidth(grid1, width );*/
		  
}
