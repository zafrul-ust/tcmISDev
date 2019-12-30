var beangrid;
var resizeGridWithWindow = true;

function resultOnLoad(){
 totalLines = document.getElementById("totalLines").value;
 if (totalLines > 0) {
  document.getElementById("newChemTrackingViewBean").style["display"] = "";
  initializeGrid();
 }else {
   document.getElementById("newChemTrackingViewBean").style["display"] = "none";   
 }
 displayGridSearchDuration();
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('newChemTrackingViewBean');
	initGridWithConfig(beangrid,config,true,true);

	beangrid.enableSmartRendering(true);
	beangrid._haas_row_span = true;
	beangrid._haas_row_span_map = lineMap;
	beangrid._haas_row_span_class_map = lineMap3;
	beangrid._haas_row_span_cols = [0,1,2,3,4,5,6,7,8,9,10,27,28];

	if (lineMap2) {
		beangrid._haas_row_span_lvl2 = true;
		beangrid._haas_row_span_lvl2_map = lineMap2;
		beangrid._haas_row_span_lvl2_cols = [11,12];
	}

	if( typeof( jsonMainData ) != 'undefined' ) {
		beangrid.parse(jsonMainData,"json");
	}
	beangrid.attachEvent("onRowSelect",selectRow);
	beangrid.attachEvent("onRightClick",selectRow);
}

function validateForm() {
	return true;
}

function selectRow()
{
// to show menu directly
	rightClick = false;
	rowId0 = arguments[0];
	colId0 = arguments[1];
	ee     = arguments[2];

	if( ee != null ) {
		if( (ee.button != null && ee.button == 2) || ee.which == 3) {
			rightClick = true;
		}
   }
	
	if( !rightClick ) return;

	var tmpRequestStatus = cellValue(rowId0,"requestStatus");
	var tmpEngEval = cellValue(rowId0,"engEval");
	var aitems = new Array();
    //view request
    if (tmpEngEval.toLowerCase() == 'y') {
		aitems[aitems.length] = "text="+messagesData.viewEval+";url=javascript:showEvalDetail();";
	}else {
		aitems[aitems.length] = "text="+messagesData.viewApproveCatAdd+";url=javascript:showCatalogAddRequestScreen();";
	}
    //view request status
    if (tmpRequestStatus*1 > 4 && tmpRequestStatus*1 != 14 && tmpRequestStatus*1 != 15 && tmpRequestStatus*1 != 17 ) {
		aitems[aitems.length] = "text="+messagesData.approvalDetail+";url=javascript:showApprovalDetail();";
	}
    //allow user to resubmit rejected requests
    if (tmpRequestStatus*1 == 7) {
        aitems[aitems.length] = "text="+messagesData.resubmit+";url=javascript:resubmitRejectedRequest();";
    }

    replaceMenu('catAddTrackingMenu',aitems);
	toggleContextMenu('catAddTrackingMenu');

}  //end of method

// all same level, at least one item
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

function doNothing() {
}

function showApprovalDetail()
{
  var requestId = cellValue(beangrid.getSelectedRowId(),"requestId"); 
  if(requestId.value!='')  {	  
	 parent.showApprovalDetail(requestId);
  }
}

function showEvalDetail() {
 var requestId  =  cellValue(beangrid.getSelectedRowId(),"requestId");

 if ( requestId != null &&  requestId != 0) {
  var loc = "engeval.do?uAction=view&requestId="+requestId;
  try {
    parent.parent.openIFrame("engeval",loc,""+messagesData.engineeringevaluation+"","","N");
  }catch (ex) {
    openWinGeneric(loc,"engeval"+"","800","600","yes","50","50");
  }
 }
}

function showCatalogAddRequestScreen() {
    var requestId  =  cellValue(beangrid.getSelectedRowId(),"requestId");
    if ( requestId != null &&  requestId != 0) {
        var loc = "catalogaddrequest.do?uAction=view&requestId="+requestId;
        var startingView = cellValue(beangrid.getSelectedRowId(),"startingView");
        if (startingView == '6' || startingView == '7' || startingView == '9') {
            loc = "catalogaddmsdsrequest.do?uAction=view&requestId="+requestId;    
        }
        try{
            parent.parent.openIFrame("cataddreq"+requestId,loc,""+messagesData.cataddreq+" "+requestId,"","N");
        }catch (ex) {
            openWinGeneric(loc,"cataddreq"+requestId+"","800","600","yes","50","50");
        }
    }
}

function resubmitRejectedRequest() {
    callToServer("catalogaddtrackingresults.do?action=resubmitRejectedRequest&requestId="+cellValue(beangrid.getSelectedRowId(),"requestId")+
                 "&companyId="+cellValue(beangrid.getSelectedRowId(),"companyId")+"&startingView="+cellValue(beangrid.getSelectedRowId(),"startingView"));
}

function resubmitRejectedRequestCallback(requestId, startingView) {
    if ( requestId != null &&  requestId != 0) {
        var loc = "catalogaddrequest.do?uAction=view&requestId="+requestId;
        if (startingView == '6' || startingView == '7' || startingView == '9') {
            loc = "catalogaddmsdsrequest.do?uAction=view&requestId="+requestId;
        }
        try{
            parent.parent.openIFrame("cataddreq"+requestId,loc,""+messagesData.cataddreq+" "+requestId,"","N");
        }catch (ex) {
            openWinGeneric(loc,"cataddreq"+requestId+"","800","600","yes","50","50");
        }
    }
}




