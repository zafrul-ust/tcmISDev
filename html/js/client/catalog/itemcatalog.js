/************************************NEW***************************************************/
var mygrid;
var selectedRowId=null;
var preClass = null;
var preRowId = null;
/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

/*TODO- Block ctrl+mouse wheel on whole page
* */

function $(a) {
	return document.getElementById(a);
}

/************************************NEW***************************************************/

function viewMSDS() {
//MSDS:
// id is material id.
	if(newMsdsViewer)
		openWinGeneric('viewmsds.do?act=msds'+
			'&materialId='+ cellValue(selectedRowId,"materialId") +
			'&showspec=N' +
			'&spec=' + // do we need to know spec id?
			'&cl=${personnelBean.companyId}'+
			'&facility=' + encodeURIComponent($('facilityId').value)
			,"ViewMSDS","1024","720",'yes' );
	else
		openWinGeneric('ViewMsds?act=msds'+
			'&id='+ cellValue(selectedRowId,"materialId") +
			'&showspec=N' +
			'&spec=' + // do we need to know spec id?
			'&cl=${personnelBean.companyId}'+
			'&facility=' + encodeURIComponent($('facilityId').value)
			,"ViewMSDS","1024","720",'yes' );
}

function selectRow() {
	// to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];
   if( ee != null ) {
   	if( ee.button != null && ee.button == 2 ) rightClick = true;
   	else if( ee.which == 3  ) rightClick = true;
   }

   selectedRowId = rowId0;
   selectedColId = colId0;

	itemid = cellValue(rowId0,"itemId");
	parent.$("selectedItemId").value = itemid;

    hasEngEvalAction = false;
    if (intcmIsApplication && $v("engEvalPermission") == 'Y' && !disableEngEval) {
		parent.document.getElementById('reorderEvalLink').style["display"] = "";
        hasEngEvalAction = true;
    }

    //save selected row data to catalogmain.jsp so we can pass them back to servers for catalog adds
	 saveDataForCatalogAdd();
	 if (intcmIsApplication && $v("catalogAddPermission") == 'Y') {
        if (colId0 >= mygrid.getColIndexById("itemId")) {
            parent.document.getElementById('itemCatalogAddLink').style["display"] = "";
            if (hasEngEvalAction) {
                parent.$("itemCatalogAddLink").innerHTML = ' | '+messagesData.newPartFrom+': <a href="#" onclick="newPartFromExistingItem(); return false;">'+messagesData.item+'</a>'+
                                                           ' , <a href="#" onclick="newPartFromExistingItemModifyPkg(); return false;">'+messagesData.itemModifyPkg+'</a>';
            }else {
                parent.$("itemCatalogAddLink").innerHTML = messagesData.newPartFrom+': <a href="#" onclick="newPartFromExistingItem(); return false;">'+messagesData.item+'</a>'+
                                                           ' , <a href="#" onclick="newPartFromExistingItemModifyPkg(); return false;">'+messagesData.itemModifyPkg+'</a>';
            }
        }else {
            parent.document.getElementById('itemCatalogAddLink').style["display"] = "none";
        }
     }

    //stop here if it's not a right mouse click
	if( !rightClick ) return;
    if( disableMenu ) return;
	contextMenuName = 'rightClickMenu';
	preContextMenuName = contextMenuName;
	if (contextMenuName.trim() != '' ){
        if (colId0 > mygrid.getColIndexById("itemId")) {
            url = "catalogmenu.do?uAction=loaditemdata&itemId="+ itemid;
            var curcursor = document.body.style.cursor;
            document.body.style.cursor = "wait";
            popup('ViewSDS/MSDS',1);
            loadJSON(url);
        }
   }
}

function saveDataForCatalogAdd() {
    parent.document.getElementById("catalogAddItemId").value = cellValue(selectedRowId,"itemId");
}


function addEditSynonym() {
	parent.showTransitWin("",formatMessage(messagesData.waitingforinput, messagesData.addeditsynonym+" "+cellValue(selectedRowId,"materialId"))+"...");
	openWinGeneric('addeditsynonym.do?materialId='+ cellValue(selectedRowId,"materialId")  +
			'&facilityId=' + encodeURIComponent($v('facilityId')) 
			,"addEditSynonym","450","190",'no' );
}

function loadJSON(url) 
{  
   callToServer(url+"&callback=processReqChangeJSON");
}

function processReqChangeJSON(xmldoc) 
{  
   document.body.style.cursor = curcursor;
	rowId0 = selectedRowId;

	if( preContextMenuName == 'rightClickMenu' ) {
		msdsOnLine = cellValue(rowId0,"msdsOnLine");
		var menuItems = new Array();
	  	if( msdsOnLine == null || msdsOnLine == '' || msdsOnLine == 'N')
			menuItems[0] ='text=<font color="gray">'+messagesData.viewmsds+'</font>;url=javascript:doNothing();';
	  	else
			menuItems[0] = 'text='+messagesData.viewmsds+';url=javascript:viewMSDS();';
			
		materialId = cellValue(rowId0,"materialId");
		if( materialId == null || materialId == '' || selectedColId == 0)
			menuItems[1] ='text=<font color="gray">'+messagesData.addeditsynonym+'</font>;url=javascript:doNothing();';
	  	else
			menuItems[1] = 'text='+messagesData.addeditsynonym+';url=javascript:addEditSynonym();';

		lit = xmldoc.Lit;
	  	if( lit == null || lit == '' )
			menuItems[2] = 'text=<font color="gray">'+messagesData.mfglit+'</font>;url=javascript:doNothing();';
	  	else
			menuItems[2] = 'text='+messagesData.mfglit+';url=' + lit;

	  	img = xmldoc.Img;
	  	if( img == null || img == '')
			menuItems[3] = 'text=<font color="gray">'+messagesData.image+'</font>;url=javascript:doNothing();';
	  	else
			menuItems[3] = 'text=Image;url='+img;

		if (cellValue(rowId0,"engEval") == 'y') {
			menuItems[4] = 'text='+messagesData.showevaluation+';url=javascript:showPreviousOrderEngEvalDetail();';
		}
		
		replaceMenu("rightClickMenu",menuItems);
	 }
	 closeAllMenus();
	 popup(preContextMenuName,1);

}

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

function newinit() {
	totalLines = document.getElementById("totalLines").value;

	if (totalLines > 0) {
		document.getElementById("itemCatalogScreenSearchBean").style["display"] = "";
		/*this is the new part.*/
		doInitGrid();
	}else {
		document.getElementById("itemCatalogScreenSearchBean").style["display"] = "none";
		displayGridSearchDuration();
		parent.document.getElementById("itemCatalogLastSearchDurationMsg").value = "";
	}

	parent.myResultFrameId = "itemCatalogResultFrame";
	parent.document.getElementById("partCatalogDiv").style["display"] = "none";
	parent.document.getElementById("itemCatalogDiv").style["display"] = "";
	parent.document.getElementById("materialCatalogDiv").style["display"] = "none";

	setResultFrameSize();

	if (intcmIsApplication) {
		parent.document.getElementById('mainUpdateLinks').style["display"] = "";
        if ($v("engEvalPermission") == 'Y' && !disableEngEval) {
            parent.document.getElementById('newEvalLink').style["display"] = "";
        }
    }

	parent.document.getElementById("newMsdsLink").style["display"] = "none";
	parent.document.getElementById("newApprovalCodeLink").style["display"] = "none";

	parent.accountSysId[parent.$("itemCatalogLastSearchFacilityId").value] = altAccountSysId;
    //set catalog facility data
	parent.catalogFacility = altCatalogFacility;
    //set flag whether to ask user for how part is used
    parent.$("engEvalPartTypeRequired").value = $v("engEvalPartTypeRequired");
    parent.$("facLocAppChargeTypeDefault").value = $v("facLocAppChargeTypeDefault");
}


function doInitGrid(){
/*Give the div name that holds the grdi the same name as your dynabean*/
mygrid = new dhtmlXGridObject('itemCatalogScreenSearchBean');
mygrid.setImagePath("/dhtmlxGrid/codebase/imgs/");
/*To internationalize column headers, get the values from messagesData*/

mygrid.setColVAlign("middle,middle,middle,middle,middle,middle,middle,middle,middle,middle");
mygrid.setHeader(
  ""+messagesData.item+
  ","+messagesData.componentdescription+
  ","+messagesData.packaging+
  ","+messagesData.grade+
  ","+messagesData.manufacturer+
  ","+messagesData.country+
  ","+messagesData.mfgpartno+
  ","+messagesData.shelflife+
  ",,,"
); 

/*You can set column alingments*/
mygrid.setColAlign("left,left,left,left,left,left,left,left,left,left,left");
//possible values are: baseline,sub,super,top,text-top,middle,bottom,text-bottom

/*Set the type of sort to do on this column.
Date read-only sorting is not supported yet. For date columns set the sorting type to na*/
//mygrid.setColSorting("na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na");
/*Set column types, you can define and editable columns here.*/
mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");

/*This enables tooltips on a column and on mouseover displays the value of the cell in a tooltip.
* We will enable tooltips only for columns whose width might be less than the text data in that column.
* Most likely candidates are packaging, item desc, part desc, any user comments etc.*/
mygrid.enableTooltips("false,true,false,false,false,false,false,false,false,false,false");
/*****************
* This mehtod calls are optional.
 very strange that set setColumnsVisibility false shows the column  -According to dhtmlx this is a feature
 Use this to hide columns which is data you need to do updates/send to server. I usually stick them at the end of the grid.
*/
var idArr = new Array(
              "itemId",
              "materialDesc",
              "packaging",
              "grade",
              "mfgDesc",
              "country",
	 			  "mfgPartNo",
	           "shelfLifeDisplay",
			// hidden here.
              "msdsOnLine",
	 			  "materialId",
	 			  "engEval"
				  );

mygrid.setColumnIds(idArr.join(","));

mygrid.setColumnsVisibility("false,false,false,false,false,false,false,false,true,true,true");

/*Set initial widths for your columns, play this by the eye.*/
mygrid.setInitWidths("5,20,20,10,15,10,10,10,0,0,0");

/*This keeps the row height the same*/
mygrid.enableMultiline(false);
/*This si used to tell the grid that we have row height set at 30px*/
mygrid.setAwaitedRowHeight(gridRowHeight);
/*You can use this to call the select row functions and the right click funcitons.*/
if( !disableMenu ) {
	mygrid.attachEvent("onRowSelect",selectRow);
	mygrid.attachEvent("onRightClick",selectRow);
}

/*this enabels smart rendering, which buffers the loading to avoid any huge memory usages.
* this is not compatible with rowspans.*/
mygrid.enableSmartRendering(true);

mygrid._haas_row_span = true;
mygrid._haas_row_span_map = lineMap;
mygrid._haas_row_span_class_map = lineMap3;
mygrid._haas_row_span_cols = [0];

mygrid._haas_row_span_lvl2_child_select = true;    
mygrid.setSkin("haas");

mygrid.init();
/*allow copy and paste feature */
mygrid.entBox.onselectstart = function(){ return true; };	

setHaasGrid(mygrid);

/*loading from JSON object*/
mygrid.parse(jsonMainData,"json");
/*This will update the column headers widths according to font size.*/
updateColumnWidths(mygrid);

/*This is to disable the mouse wheel scroll problem in IE.*/
if (_isIE)
 mygrid.entBox.onmousewheel=stop_event;

/*This is to load the rowspans*/
//setTimeout('loadRowSpans()',100);



displayGridSearchDuration();
parent.document.getElementById("itemCatalogLastSearchDurationMsg").value = getDisplaySearchDuration();
}



function setGridHeight(resultFrameHeight)
{
  var griDiv = document.getElementById("itemCatalogScreenSearchBean");
  griDiv.style.height = resultFrameHeight-3 + "px";
}

function setGridSize()
{
  mygrid.setSizes();
}

function showPreviousOrderEngEvalDetail()
{
	var itemId = cellValue(selectedRowId,"itemId");
	parent.showPreviousOrderedEvalDetail(itemId);
}

