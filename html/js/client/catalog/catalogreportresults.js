/************************************NEW***************************************************/
var beangrid;
var selectedRowId=null;
var selectedColId=null;
var resizeGridWithWindow = true;
var showQualitySummary = false;
var facilityId;

var preContextMenuName = null

var children = new Array();

function $(a) {
	return document.getElementById(a);
}

function selectRow(rowId)
{
	 selectedRowId =rowId;
}


function workAreasComments() {

openWinGeneric('workareacommentsmain.do?catPartNo='+ encodeURIComponent(cellValue(selectedRowId,"catPartNo") ) +
            '&partGroupNo='+ cellValue(selectedRowId,"partGroupNo") +
            '&applicationId=' + encodeURIComponent( $('applicationId').value ) +
   			'&facilityId=' + encodeURIComponent($('facilityId').value) +
            '&catalogId='+ cellValue(selectedRowId,"catalogId")
            ,"workareacomments",700,500,'yes' );
}

function partComments() {
var url = 'partnotesmain.do?catalogId='+cellValue(selectedRowId,"catalogId") +
       '&readonly=yes&catPartNo='+encodeURIComponent( cellValue(selectedRowId,"catPartNo") ) +
       '&catalogCompanyId='+cellValue(selectedRowId,"catalogCompanyId") +
       '&companyId='+ encodeURIComponent(cellValue(selectedRowId,"companyId")) ;
children[children.length] = openWinGeneric(url,"PartComment",600,300,'yes' );
}

function qualitySummary() {
	children[children.length] = openWinGeneric("qualitysummary.do?catPartNo="+encodeURIComponent(cellValue(beangrid.getSelectedRowId(),"catPartNo"))+
	               "&catalogId="+encodeURIComponent(cellValue(beangrid.getSelectedRowId(),"catalogId")) +
	               "&catalogDesc="+encodeURIComponent(cellValue(beangrid.getSelectedRowId(),"catalogDesc")) +
	               "&catalogCompanyId="+encodeURIComponent(cellValue(beangrid.getSelectedRowId(),"catalogCompanyId")),"QualitySummary","800","600",'yes');
}


function viewMSDS() {
	if(newMsdsViewer)
		children[children.length] = openWinGeneric('viewmsds.do?act=msds'+
			'&materialId='+ cellValue(selectedRowId,"materialId") +
			'&showspec=N' +
			'&spec=' + // do we need to know spec id?
			'&cl='+ encodeURIComponent(cellValue(selectedRowId,"companyId")) +
			'&facility=' + encodeURIComponent($('facilityId').value) +
			'&catpartno=' + encodeURIComponent(cellValue(selectedRowId,"catPartNo"))
			,"ViewMSDS","1024","720",'yes' );
	else
		children[children.length] = openWinGeneric('ViewMsds?act=msds'+
			'&id='+ cellValue(selectedRowId,"materialId") +
			'&showspec=N' +
			'&spec=' + // do we need to know spec id?
			'&cl='+ encodeURIComponent(cellValue(selectedRowId,"companyId")) +
			'&facility=' + encodeURIComponent($('facilityId').value) +
			'&catpartno=' + encodeURIComponent(cellValue(selectedRowId,"catPartNo"))
			,"ViewMSDS","1024","720",'yes' );
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

function getCurrentRowVal(name) {
	var value = null;
	value = cellValue(selectedRowId, name);
	return encodeURIComponent(value);
}



function rightClickRow(rowId, colId)
{   
	selectedRowId = rowId;
	
	beangrid.selectRowById(rowId,null,false,false);
	var catPartNo = beangrid.cellById(rowId, beangrid.getColIndexById("catPartNo")).getValue();
	
	var workArea = parent.document.getElementById("applicationId").value;
	 
	var aitems = new Array();
	aitems[aitems.length] = "text=<b>"+messagesData.partno+"</b>: %% ;url=javascript:doNothing();".replace(/%%/, catPartNo );

    // comments
     aitems[aitems.length] = 'showmenu=comments;text='+messagesData.comments+';image=';

	 vitems = new Array();
    	  
	 if(workArea != '') {
    	  vitems[vitems.length] = "text="+messagesData.workareacomments+";url=javascript:workAreasComments();";
     }
     
     //if(cellValue(selectedRowId,"incomingTesting") == 'Y'){
		aitems[aitems.length] = "text="+messagesData.testdefinition+";url=javascript:testDefinition();";
  	 //}
    	  
     vitems[vitems.length] = "text="+messagesData.partcomments+";url=javascript:partComments();";
     replaceMenu('comments',vitems);
 		
	 //qualitySummary
	 if(showQualitySummary){
		aitems[aitems.length] = "text="+messagesData.qualitysummary+";url=javascript:qualitySummary();";
	 }
	 replaceMenu('addToCartMenu',aitems);
		
		if (colId < beangrid.getColIndexById("itemId")){
			toggleContextMenu('addToCartMenu');
		}
		if (colId > beangrid.getColIndexById("itemId")){
			toggleContextMenu('addToCartMenu3');
		}
		if (colId == beangrid.getColIndexById("itemId")) {
			toggleContextMenu('');
		}

}


function doNothing() {
}

function newinit() {
	
	totalLines = document.getElementById("totalLines").value;
	
	if (totalLines > 0) {
		
		document.getElementById("prCatalogScreenSearchBean").style["display"] = "";
		
		initializeGrid();  
	}else {
		
		document.getElementById("prCatalogScreenSearchBean").style["display"] = "none";
		
	}
	
		
	
	setResultFrameSize();
	displayGridSearchDuration();
	
} //end of method

if(facilityId == "Palmdale")
{
	var rowSpanCols = [0,1,2,3,4,5,6,7,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41];
}
else
{
   var rowSpanCols = [0,1,2,3,4,5,6,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40];
}
function initializeGrid(){
	
	initGridWithGlobal(gridConfig);
}    


function testDefinition() {
	parent.showTransitWin(messagesData.testdefinition);
	var companyId = cellValue(beangrid.getSelectedRowId(),"companyId");
	var catalogId = cellValue(beangrid.getSelectedRowId(),"catalogId");
	var catalogCompanyId = cellValue(beangrid.getSelectedRowId(),"catalogCompanyId");
	var catPartNo = cellValue(beangrid.getSelectedRowId(),"catPartNo");	
	var partGroupNo = cellValue(beangrid.getSelectedRowId(),"partGroupNo");
	var partDesc = cellValue(beangrid.getSelectedRowId(),"partDescription");
	
	children[children.length] = openWinGeneric('testdefinition.do?companyId='+companyId+'&facilityId='+encodeURIComponent($v("facilityId"))+
	               '&catalogId='+encodeURIComponent(catalogId) +'&catalogCompanyId='+catalogCompanyId+
	               '&catPartNo='+encodeURIComponent(catPartNo)+'&partGroupNo='+partGroupNo+
	               '&activeTestsOnly=Y'+
	               '&partDesc='+encodeURIComponent(partDesc),"testDefinition","950","650",'yes');
	
}

function closeAllchildren()
{
//	if (document.getElementById("uAction").value != 'new') {
		try {
			for(var n=0;n<children.length;n++) {
				try {
				children[n].closeAllchildren(); 
				} catch(ex){}
			children[n].close();
			}
		} catch(ex){}
		children = new Array(); 
		
//	} 
}


