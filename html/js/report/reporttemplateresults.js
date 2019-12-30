var beangrid;
windowCloseOnEsc = true;

var resizeGridWithWindow = true;

function resultOnLoad()
{
 totalLines = document.getElementById("totalLines").value;
 
 if (totalLines > 0)
 {
  document.getElementById("reportTemplateDiv").style["display"] = "";
  
  initializeGrid();  
 }  
 else
 {
   document.getElementById("reportTemplateDiv").style["display"] = "none";
 }

 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('reportTemplateDiv');

	beangrid.enableMultiline(true);
	initGridWithConfig(beangrid,config,false,false);
	if( typeof( jsonMainData ) != 'undefined' ) {		
		beangrid.parse(jsonMainData,"json");
	}	
	beangrid.attachEvent("onRightClick",rightMouseClicked);
}

function activateTemplate() {
	parent.$("templateId").value = cellValue(beangrid.getSelectedRowId(),"templateId");
	parent.activateTemplate();
}

function inactivateTemplate() {
	parent.$("templateId").value = cellValue(beangrid.getSelectedRowId(),"templateId");
	parent.inactivateTemplate();
}

function deleteTemplate() {
	parent.$("templateId").value = cellValue(beangrid.getSelectedRowId(),"templateId");
	parent.deleteTemplate();
}

function rightMouseClicked()
{
	rightClick = false;
	rowId0 = arguments[0];
	colId0 = arguments[1];
	ee     = arguments[2];

	var aitems = new Array();
	var loc  =  cellValue(beangrid.getSelectedRowId(),"urlPageArg");
	if (loc.length > 0) {
		aitems[aitems.length] = "text="+messagesData.openReport+";url=javascript:openReportTemplate();";
	}

	var allowEdit  =  cellValue(beangrid.getSelectedRowId(),"allowEdit");
	if (allowEdit == 'Y') {
		/*
		if (cellValue(beangrid.getSelectedRowId(),"status") == 'A') {
			aitems[aitems.length] = "text="+messagesData.inactivateTemplate+";url=javascript:inactivateTemplate();";
		}else {
			aitems[aitems.length] = "text="+messagesData.activateTemplate+";url=javascript:activateTemplate();";
		}
		*/
		aitems[aitems.length] = "text="+messagesData.deleteTemplate+";url=javascript:deleteTemplate();";
	}

	if (aitems.length > 0) {
		replaceMenu('rightClickedMenu',aitems);
		toggleContextMenu('rightClickedMenu');
	}

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

function openReportTemplate() {
	var templateId = cellValue(beangrid.getSelectedRowId(),"templateId");
	var loc  =  cellValue(beangrid.getSelectedRowId(),"urlPageArg");
	var reportType = cellValue(beangrid.getSelectedRowId(),"reportType");
	var tmpWindowName = cellValue(beangrid.getSelectedRowId(),"globalizationLabelLetter")+templateId;
    var pageId = cellValue(beangrid.getSelectedRowId(),"pageId");
    if (loc.length > 0) {
		try{
			var truncateName = false;
            loc = loc.replace("&amp;","&");
            if (pageId == null || pageId.length == 0) {
                loc += templateId;
				truncateName = true;
			    var templateName = cellValue(beangrid.getSelectedRowId(),"templateName");
			    tmpWindowName += "-"+templateName;
                parent.parent.openIFrame(templateId,loc,tmpWindowName,"","N",truncateName);
            }else {
                //open regular page
                tmpWindowName = cellValue(beangrid.getSelectedRowId(),"templateName");
                parent.parent.openIFrame(pageId,loc,tmpWindowName,"","N",truncateName);
            }
        }catch (ex) {
			openWinGeneric(loc,tmpWindowName,"800","600","yes","50","50");
		}
	}
}

function validateForm()
{
	return true;
}




