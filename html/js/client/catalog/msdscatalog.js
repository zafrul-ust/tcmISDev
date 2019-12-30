/************************************NEW***************************************************/
var mygrid;
var selectedRowId = null;
/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;
var showMsds = false;
var selectedColIndex = null;

function newinit() {
	totalLines = document.getElementById("totalLines").value;

	if (totalLines > 0) {
		document.getElementById("msdsCatalogScreenSearchBean").style["display"] = "";
        doInitGrid();
        displayGridSearchDuration();
        parent.$("materialCatalogLastSearchDurationMsg").value = getDisplaySearchDuration();
	}else {
		document.getElementById("msdsCatalogScreenSearchBean").style["display"] = "none";
		displayGridSearchDuration();
		parent.document.getElementById("materialCatalogLastSearchDurationMsg").value = "";
	}

	parent.myResultFrameId = "materialCatalogResultFrame";
	parent.document.getElementById("partCatalogDiv").style["display"] = "none";
	parent.document.getElementById("itemCatalogDiv").style["display"] = "none";
	parent.document.getElementById("materialCatalogDiv").style["display"] = "";

	setResultFrameSize();

	if (intcmIsApplication) {
		parent.document.getElementById('mainUpdateLinks').style["display"] = "";
	}
	
	parent.document.getElementById('newMsdsLink').style["display"] = "";
	parent.document.getElementById('newApprovalCodeLink').style["display"] = "none";
    //set catalog facility data
	parent.catalogFacility = altCatalogFacility;
}

function doInitGrid() {
    mygrid = new dhtmlXGridObject('msdsCatalogScreenSearchBean');
    initGridWithConfig(mygrid,config,true,true);

    //setting smart rendering
	mygrid.enableSmartRendering(true);
	mygrid._haas_row_span = true;
	mygrid._haas_row_span_map = lineMap;
	mygrid._haas_row_span_class_map = lineMap3;
	mygrid._haas_row_span_cols = [0,1,2];
    mygrid._haas_row_span_child_select = true;
	//mygrid._haas_row_span_lvl1_child_select = true;

    if( typeof( jsonMainData ) != 'undefined' ) {
        mygrid.parse(jsonMainData,"json");
    }
    mygrid.attachEvent("onRowSelect",selectRow);
    mygrid.attachEvent("onRightClick",rightClick);
    
    contextMenu = 'rightClickMenu';

}


function selectRow() {
    rightClick = false;
    rowId0 = arguments[0];
    colId0 = arguments[1];
    ee     = arguments[2];
/*
    if( ee != null ) {
		if( (ee.button != null && ee.button == 2) || ee.which == 3) {
			rightClick = true;
		}
    }  */
    selectedRowId = rowId0;

    var customerMixtureNumber = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("customerMixtureNumber")).getValue();
    var customerMsdsNumber = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("customerMsdsNumber")).getValue();
    var materialId = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("materialId")).getValue();
    
    custMsdsNo = customerMsdsNumber;
    if(colId0 < mygrid.getColIndexById("customerMsdsNumber") && customerMixtureNumber != null && customerMixtureNumber.length != 0)
    {
        displayCustMsdsNo = customerMixtureNumber;
        custMsdsNo = customerMixtureNumber;
    }
    else if(customerMsdsNumber != null && customerMsdsNumber.length != 0)
        displayCustMsdsNo = customerMsdsNumber;
    else
    	displayCustMsdsNo = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("materialId")).getValue();

    if ("Y" == showFacilityUseCode) {
        parent.document.getElementById('newApprovalCodeLink').style["display"] = "";
	    parent.document.getElementById('newApprovalCodeLink').innerHTML = " | <a href=\"#\" onclick=newApprovalCode("+materialId+",'"+custMsdsNo+"'); return false;>"+messagesData.addApprovalCode+" : "+displayCustMsdsNo+"</a>";
    }else
        parent.document.getElementById('newApprovalCodeLink').style["display"] = "none";    
}    
 
function rightClick(rowId0,colId0) {
	selectRow(rowId0);
    selectedColIndex = colId0;

     materialId = cellValue(rowId0,"materialId");
     cid    = $('companyId').value;
     kitMsdsNumber = "";
     if (selectedColIndex < mygrid.getColIndexById("customerMsdsNumber"))
        kitMsdsNumber = cellValue(rowId0,"customerMixtureNumber");
     msdsNumber = cellValue(rowId0,"customerMsdsNumber");
     url = "catalogmenu.do?uAction=loadRequestforMaterialCatalog&materialId="+ materialId +
           "&companyId="+ encodeURIComponent(cid)+"&kitMsdsNumber="+ encodeURIComponent(kitMsdsNumber)+"&componentMsdsNumber="+ encodeURIComponent(msdsNumber);
	callToServer(url+"&callback=processReqChangeJSON");
    replaceMenu('rightClickMenu',new Array("text=;url=;"));
}

function processReqChangeJSON(xmldoc) {
	
    var tmpKitMsdsNo = cellValue(mygrid.getSelectedRowId(),"customerMixtureNumber");
    vitems = new Array();
    if (tmpKitMsdsNo.length > 0) {
        if (selectedColIndex < mygrid.getColIndexById("customerMsdsNumber")) {
            if("Y" == cellValue(mygrid.getSelectedRowId(),"msdsOnLine") && tmpKitMsdsNo.length > 0) {
                vitems[vitems.length ] = "text="+messagesData.viewkitmsds+";url=javascript:viewKitMsds();";
            }
            if (setGrandfatheredMaterialPermission && tmpKitMsdsNo.length > 0) {
                var tmpApprovalCode = cellValue(mygrid.getSelectedRowId(),"approvalCode");
                if (tmpApprovalCode == null || tmpApprovalCode.length == 0) {
                    vitems[vitems.length ] = "text="+messagesData.grandfatheredMaterial+";url=javascript:addGrandfatheredMaterial();";
                }
            }
            if (lmcoModule) {
            	vitems[vitems.length ] = "text="+messagesData.printsecondarylabelinformation+";url=javascript:printSecondaryLabelInformation();";
                vitems[vitems.length ] = "text="+messagesData.secondarylabelinformation+";url=javascript:updateSecondaryLabelInformation();";	
            }
        }else {
            if("Y" == cellValue(mygrid.getSelectedRowId(),"msdsOnLine")) {
                vitems[vitems.length ] = "text="+messagesData.viewComponentMsds+";url=javascript:viewMsds();";
            }
            vitems[vitems.length ] = "text="+messagesData.addeditsynonym+";url=javascript:addEditSynonym();";
            if(cellValue(mygrid.getSelectedRowId(),"customerMsdsNumber").length > 0)
                vitems[vitems.length ] = "text="+messagesData.showstoragelocations+";url=javascript:showStorageLocationMenu();";

            if (setGrandfatheredMaterialPermission && tmpKitMsdsNo.length == 0 && cellValue(mygrid.getSelectedRowId(),"customerMsdsNumber").length > 0) {
                var tmpApprovalCode = cellValue(mygrid.getSelectedRowId(),"approvalCode");
                if (tmpApprovalCode == null || tmpApprovalCode.length == 0) {
                    vitems[vitems.length ] = "text="+messagesData.grandfatheredMaterial+";url=javascript:addGrandfatheredMaterial();";
                }
            }
            if (lmcoModule) {
            	vitems[vitems.length ] = "text="+messagesData.printsecondarylabelinformation+";url=javascript:printSecondaryLabelInformation();";
                vitems[vitems.length ] = "text="+messagesData.secondarylabelinformation+";url=javascript:updateSecondaryLabelInformation();";	
            }
            if (printHazCommLabels)
                vitems[vitems.length ] = 'text='+messagesData.printHazCommLabels+';url=javascript:printHazCommLabel();';
        }
    }else {
        if("Y" == cellValue(mygrid.getSelectedRowId(),"msdsOnLine")) {
            vitems[vitems.length ] = "text="+messagesData.viewComponentMsds+";url=javascript:viewMsds();";
        }
        vitems[vitems.length ] = "text="+messagesData.addeditsynonym+";url=javascript:addEditSynonym();";
        if(cellValue(mygrid.getSelectedRowId(),"customerMsdsNumber").length > 0)
            vitems[vitems.length ] = "text="+messagesData.showstoragelocations+";url=javascript:showStorageLocationMenu();";

        if (setGrandfatheredMaterialPermission && tmpKitMsdsNo.length == 0 && cellValue(mygrid.getSelectedRowId(),"customerMsdsNumber").length > 0) {
            var tmpApprovalCode = cellValue(mygrid.getSelectedRowId(),"approvalCode");
            if (tmpApprovalCode == null || tmpApprovalCode.length == 0) {
                vitems[vitems.length ] = "text="+messagesData.grandfatheredMaterial+";url=javascript:addGrandfatheredMaterial();";
            }
        }
        if (lmcoModule) {
        	vitems[vitems.length ] = "text="+messagesData.printsecondarylabelinformation+";url=javascript:printSecondaryLabelInformation();";
            vitems[vitems.length ] = "text="+messagesData.secondarylabelinformation+";url=javascript:updateSecondaryLabelInformation();";	
        }
        if (printHazCommLabels)
            vitems[vitems.length ] = 'text='+messagesData.printHazCommLabels+';url=javascript:printHazCommLabel();';
    }
    
    
    var requestId = xmldoc.requestId;
    if( requestId == 'undefined' || requestId == null || requestId.length == 0 ) {
        vitems[vitems.length ] = 'text=<font color="gray">'+messagesData.viewapproval+'</font>;url=javascript:doNothing();' ;
    }else  {
        vitems[vitems.length ] = 'showmenu=requestId;text='+messagesData.viewapproval+';image=';
        // item inv menu in part inv menu
        var menuSubItems2 = new Array();
        for(i=0;i < requestId.length; i++ ) {
        	appCodeDateRange = '';
        	if(requestId[i].startDate != null && requestId[i].startDate != '')
        		appCodeDateRange = messagesData.startDate + ":" + requestId[i].startDate;
        	if (requestId[i].endDate != null && requestId[i].endDate != '')
        		appCodeDateRange += " " + messagesData.endDate + ":" + requestId[i].endDate;
            if( requestId[i].applicationUseGroupName != null && requestId[i].applicationUseGroupName.length > 0)
                menuSubItems2[menuSubItems2.length ] = "text="+requestId[i].requestId+" ("+requestId[i].applicationUseGroupName+") "+ appCodeDateRange+";url=javascript:showCatalogAddRequestScreen("+requestId[i].requestId+");"
            else
                menuSubItems2[menuSubItems2.length ] = "text="+requestId[i].requestId+" "+appCodeDateRange+";url=javascript:showCatalogAddRequestScreen("+requestId[i].requestId+");"
        }
        replaceMenu('requestId',menuSubItems2);
	}

    replaceMenu('rightClickMenu',vitems);
    toggleContextMenu('rightClickMenu');
}

function showCatalogAddRequestScreen(requestId) {
    if ( requestId != null &&  requestId != 0) {
        var loc = "catalogaddrequest.do?uAction=view&requestId="+requestId;
       
        try{
            parent.parent.openIFrame("cataddreq"+requestId,loc,""+messagesData.cataddreq+" "+requestId,"","N");
        }catch (ex) {
            openWinGeneric(loc,"cataddreq"+requestId+"","800","600","yes","50","50");
        }
    }

}

function addGrandfatheredMaterial() {
    var tmpCustMsdsNo = cellValue(selectedRowId,"customerMixtureNumber");
    if (tmpCustMsdsNo == null || tmpCustMsdsNo.length == 0) {
        tmpCustMsdsNo = cellValue(selectedRowId,"customerMsdsNumber");
    }
    callToServer("catalogresults.do?uAction=addGrandfatheredMaterial&companyId="+$v("companyId")+
                 "&facilityId="+$v("facilityId")+"&custMsdsNo="+tmpCustMsdsNo+
                 "&customerMsdsDb="+cellValue(selectedRowId,"customerMsdsDb"));
}

function callToServerCallback() {
    var tmpApprovalCode = cellValue(selectedRowId,"approvalCode");
    if (tmpApprovalCode != null && tmpApprovalCode.length > 0) {
        tmpApprovalCode += " AG";
    }else {
        tmpApprovalCode = parent.$v("materialCatalogLastSearchFacilityDesc")+": AG";
    }
    mygrid.cells(selectedRowId,mygrid.getColIndexById("approvalCode")).setValue(tmpApprovalCode);
}

function selectRightclick(rowId,cellInd) {
    /*
  mygrid.selectRowById(rowId,null,false,false);
  selectRow(rowId,cellInd);

  var columnId = mygrid.getColumnId(cellInd);
  
  vitems = new Array();
  if (cellInd < mygrid.getColIndexById("customerMsdsNumber")) {
    if("Y" == cellValue(rowId,"msdsOnLine") && cellValue(rowId,"customerMixtureNumber").length > 0) {
        vitems[vitems.length ] = "text="+messagesData.viewkitmsds+";url=javascript:viewKitMsds();";
      }
  }else {
    if("Y" == cellValue(rowId,"msdsOnLine")) {
        vitems[vitems.length ] = "text="+messagesData.viewComponentMsds+";url=javascript:viewMsds();";
    }
    vitems[vitems.length ] = "text="+messagesData.addeditsynonym+";url=javascript:addEditSynonym();";
    if(cellValue(rowId,"customerMsdsNumber").length > 0)
  	    vitems[vitems.length ] = "text="+messagesData.showstoragelocations+";url=javascript:showStorageLocationMenu();";
  }
  
  replaceMenu('rightClickMenu',vitems);  
  toggleContextMenu('rightClickMenu');
  */
}

function addEditSynonym() {
	openWinGeneric('addeditsynonym.do?materialId='+ cellValue(selectedRowId,"materialId")  +
			'&facilityId=' + encodeURIComponent($v('facilityId')) 
			,"addEditSynonym","450","190",'no' );
}

function showStorageLocationMenu() {
	var url = 'storagelocations.do?materialId='+ mygrid.cells(selectedRowId,mygrid.getColIndexById("materialId")).getValue() +
			'&facilityId=' + encodeURIComponent(parent.$v('facilityId')) +
			'&facilityName=' + encodeURIComponent(parent.$("facilityId").options[parent.$("facilityId").selectedIndex].text) +
			'&application=' + 
			'&msdsNo=' + mygrid.cells(selectedRowId,mygrid.getColIndexById("customerMsdsNumber")).getValue() +
			'&tradeName=' + encodeURIComponent(mygrid.cells(selectedRowId,mygrid.getColIndexById("tradeName")).getValue()) +
			'&mfgDesc=' + encodeURIComponent(mygrid.cells(selectedRowId,mygrid.getColIndexById("mfgDesc")).getValue()) +
			'&desc=' + encodeURIComponent(mygrid.cells(selectedRowId,mygrid.getColIndexById("materialDesc")).getValue()) ;

	openWinGeneric(url,"storageLocations","500","500",'yes' );
}

/*
function viewMsds() {
    openWinGeneric('ViewMsds?act=msds'+
			'&id='+ cellValue(selectedRowId,"materialId") +
			'&showspec=N' +
			'&spec=' + // do we need to know spec id?
			'&cl='+$v('companyId')+
			'&facility=' + encodeURIComponent($v('facilityId')) +
			'&catpartno='
			,"ViewMSDS","900","600",'yes' );
}
*/

function viewMsds() {
	if(newMsdsViewer)
    	openWinGeneric('viewmsds.do?act=msds'+
			'&materialId='+ mygrid.cells(selectedRowId,mygrid.getColIndexById("materialId")).getValue() +
			'&spec=' + // do we need to know spec id?
			'&facility=' + encodeURIComponent($v('facilityId')) +
			'&catpartno='
			,"ViewMSDS","1024","720",'yes' );
	else
		openWinGeneric('ViewMsds?act=msds'+
			'&id='+ cellValue(selectedRowId,"materialId") +
			'&showspec=N' +
			'&spec=' + // do we need to know spec id?
			'&cl='+$v('companyId')+
			'&facility=' + encodeURIComponent($v('facilityId')) +
			'&catpartno='
			,"ViewMSDS","1024","720",'yes' );
}

function setGridHeight(resultFrameHeight)
{
  var griDiv = document.getElementById("msdsCatalogScreenSearchBean");
  griDiv.style.height = resultFrameHeight-3 + "px";
}

function setGridSize()
{
  mygrid.setSizes();
}

function viewKitMsds() {
	var reportLoc = "/HaasReports/report/printConfigurableReport.do"+
                    "?pr_companyId="+$v("companyId")+
                    "&pr_custMsdsDb="+encodeURIComponent(cellValue(mygrid.getSelectedRowId(),"customerMsdsDb"))+
					"&pr_custMsdsNo="+cellValue(mygrid.getSelectedRowId(),"customerMixtureNumber")+
					"&rpt_ReportBeanId=MSDSKitReportDefinition";
	openWinGeneric(reportLoc,"viewKitMsds","800","550","yes","100","100");
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

function printHazCommLabel() {
	var reportLoc = "/HaasReports/report/printConfigurableReport.do"+
                    "?pr_companyId="+$v("companyId")+
                    "&pr_materialId="+cellValue(selectedRowId,"materialId")
                    "&rpt_ReportBeanId=printHazCommLabel";
	openWinGeneric(reportLoc,"printHazCommLabel","800","550","yes","100","100");
}


function printSecondaryLabelInformation() {
var labelQty = 1;
labelQty = prompt(messagesData.labelquantity,labelQty);
openWinGeneric('printsecondarylabelinformation.do?materialId='+ cellValue(selectedRowId,"materialId") +
			'&facilityId=' + encodeURIComponent($('facilityId').value) +			
            '&labelQty=' + labelQty
            ,"SecondaryLabelInformation","900","600",'yes' );
}

function updateSecondaryLabelInformation() {
	parent.showTransitWin("",formatMessage(messagesData.waitingforinput, messagesData.secondarylabelinformationformaterialid+" "+cellValue(selectedRowId,"materialId"))+"...");
	openWinGeneric('secondarylabelinformation.do?materialId='+ cellValue(selectedRowId,"materialId") +
			'&facilityId=' + encodeURIComponent($('facilityId').value)			
			,"SecondaryLabelInformation","900","600",'yes' );
}