var beangrid;
var windowCloseOnEsc = true;

var resizeGridWithWindow = true;

var selectedRowId = null;
var alternateDb = null;

function rightClickRow(rowId, colId) {
	selectedRowId = rowId;
    var status = cellValue(selectedRowId, "status");
    var vendorAssignmentStatus = cellValue(selectedRowId, "vendorAssignmentStatus");
    var searchedStatus = $v("searchedStatus");
    var requestId = getCurrentRowVal("requestId");
    var menuItems = new Array();
    // If they're NOT on specific line (i.e. to the right of "assigned to" column) they can't open a line.
	if (colId > 7 && (vendorAssignmentStatus == 'Not Assigned' || vendorAssignmentStatus == 'Closed')) {
		var lineItem = getCurrentRowVal("lineItem");
		if (status == 'Pending SDS Sourcing') {
		    menuItems[menuItems.length] = "text="+messagesData.open+" "+messagesData.chemreq+" "+requestId+"-"+lineItem+" "+messagesData.msds+";url=javascript:openCatalogMSDS();";
		}else if(status == 'Pending SDS Indexing') {
		    //the reason for this logic is because SDS Indexing and Item Creation can run in parallel
		    if ($v("searchedStatus") == 'All Statuses') {
                var sdsIndexingClosed = cellValue(selectedRowId, "sdsIndexingClosed");
                if (sdsIndexingClosed.length > 0 && sdsIndexingClosed != "N")
                    menuItems[menuItems.length] = "text="+messagesData.open+" "+messagesData.chemreq+" "+requestId+"-"+lineItem+" "+messagesData.pendingMsdsIndexing+";url=javascript:openCatalogMSDSIndexing();";
            }else
                menuItems[menuItems.length] = "text="+messagesData.open+" "+messagesData.chemreq+" "+requestId+"-"+lineItem+" "+messagesData.pendingMsdsIndexing+";url=javascript:openCatalogMSDSIndexing();";
		}else if(status == 'Pending SDS Custom Indexing') {
			menuItems[menuItems.length] = "text="+messagesData.open+" "+messagesData.chemreq+" "+requestId+"-"+lineItem+" "+messagesData.pendingCompanyMsds+";url=javascript:openCatalogCompanyMSDS();";
		}else if(status == 'Pending SDS QC') {
			menuItems[menuItems.length] = "text="+messagesData.open+" "+messagesData.chemreq+" "+requestId+"-"+lineItem+" "+messagesData.pendingSdsQc+";url=javascript:openCatalogCompanySDSQC();";
        }else if(status == 'Pending Item Creation'){
            //the reason for this logic is because a request can have 2 items and only open one where it has not been approved
            var itemVerified = cellValue(selectedRowId, "itemVerified");
            //the reason for this logic is because SDS Indexing and Item Creation can run in parallel
            if ($v("searchedStatus") == 'All Statuses') {
                var itemCreationClosed = cellValue(selectedRowId, "itemCreationClosed");
                if (itemCreationClosed.length > 0 && itemCreationClosed != "N") {
                    if (itemVerified == 'Y')
                        menuItems[menuItems.length] = "text=Item Already verified";
                    else
                        menuItems[menuItems.length] = "text="+messagesData.open+" "+messagesData.chemreq+" "+requestId+"-"+lineItem+" "+messagesData.qc+";url=javascript:openCatalogQC();";
                }
            }else {
                if (itemVerified == 'Y')
                    menuItems[menuItems.length] = "text=Item Already verified";
                else
                    menuItems[menuItems.length] = "text="+messagesData.open+" "+messagesData.chemreq+" "+requestId+"-"+lineItem+" "+messagesData.qc+";url=javascript:openCatalogQC();";
            }
		}else if(status == 'Pending Spec') {
			menuItems[menuItems.length] = "text="+messagesData.open+" "+messagesData.chemreq+" "+requestId+"-"+lineItem+" "+messagesData.spec+";url=javascript:openCatalogSpecQC();";
        }else if(status == 'Pending Item Creation + Pending SDS Indexing') {
        	var sdsIndexingClosed = cellValue(selectedRowId, "sdsIndexingClosed");
        	if (sdsIndexingClosed.length > 0 && sdsIndexingClosed != "N")
        		menuItems[menuItems.length] = "text="+messagesData.open+" "+messagesData.chemreq+" "+requestId+"-"+lineItem+" "+messagesData.pendingMsdsIndexing+";url=javascript:openCatalogMSDSIndexing();";

        	//the reason for this logic is because a request can have 2 items and only open one where it has not been approved
            var itemVerified = cellValue(selectedRowId, "itemVerified");
        	var itemCreationClosed = cellValue(selectedRowId, "itemCreationClosed");
        	if (itemCreationClosed.length > 0 && itemCreationClosed != "N") {
        	    if (itemVerified == 'Y')
        	        menuItems[menuItems.length] = "text=Item Already verified";
        		else
        		    menuItems[menuItems.length] = "text="+messagesData.open+" "+messagesData.chemreq+" "+requestId+"-"+lineItem+" "+messagesData.qc+";url=javascript:openCatalogQC();";

        	}
        }
	}else if (colId < 7 && (vendorAssignmentStatus == 'Assigned' || vendorAssignmentStatus == 'Problem'
			|| vendorAssignmentStatus == 'Closed' || vendorAssignmentStatus == 'Multiple Locale Problem')) {
	    menuItems[menuItems.length] = "text=View Details: "+requestId+";url=javascript:openCatalogAddVendorDetails();";
	    if (searchedStatus != "All Statuses" && (vendorAssignmentStatus == 'Assigned' || vendorAssignmentStatus == 'Problem'))
	        menuItems[menuItems.length] = "text=Change to Wesco: "+requestId+";url=javascript:changeRequestToWesco();";
	    if (vendorAssignmentStatus == 'Multiple Locale Problem') 
	    	menuItems[menuItems.length] = "text=Assign to myself;url=javascript:assignToSelf();";
	    /*if (vendorAssignmentStatus == 'Closed')
	        menuItems[menuItems.length] = "text=Re-Assign to Vendor: "+requestId+";url=javascript:reAssignToVendor();";*/
	} else {
        if (status.endsWith('Pending Translation')) {
            menuItems[menuItems.length] = "text="+messagesData.open+" "+messagesData.chemreq+" "+requestId+" "+messagesData.translation+";url=javascript:openTranslation();";
        }
    }
    //display menu
    if (menuItems.length == 0) {
        toggleContextMenu('contextMenu');
    } else {
        replaceMenu('rightClickMenu',menuItems);
        toggleContextMenu('rightClickMenu');
    }
} //end of method

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

function openCatalogAddVendorDetails() {
	var vendorAssignmentStatus = cellValue(selectedRowId, "vendorAssignmentStatus");
	var requestId = getCurrentRowVal("requestId");
	var loc = "/tcmIS/catalog/catalogaddvendorqueuedetails.do?catalogAddRequestId="+requestId+"&task="+getVendorTask()+"&vendorAssignmentStatus="+vendorAssignmentStatus;
	openWinGeneric(loc,"workQueueDetails","900","600","yes","80","80","yes");
}

function getVendorTask() {
    var searchStatus = $v("searchedStatus");
    var task = "";
    if (searchStatus == 'Pending SDS Sourcing')
        if (getCurrentRowVal("startingView") == '9')
            task = 'SDS Authoring';
        else
            task = 'SDS Sourcing';
    else if (searchStatus == 'Pending SDS Indexing')
        task = 'SDS Indexing';
    else if (searchStatus == 'Pending Item Creation')
        task = 'Item Creation';
    return task;
}

function reAssignToVendor() {
    var requestId = getCurrentRowVal("requestId");
    var loc = "/tcmIS/catalog/problemqueuereason.do?uAction=open&reAssignToVendor=Y&catalogAddRequestId="+requestId+"&task="+getVendorTask();
    openWinGeneric(loc,"workQueueReason","400","300","yes","80","80","yes");
}

function changeRequestToWesco() {
    var requestId = getCurrentRowVal("requestId");
    var loc = "/tcmIS/catalog/problemqueuereason.do?uAction=open&reAssignTo=catalog&catalogAddRequestId="+requestId+"&task="+getVendorTask();
    openWinGeneric(loc,"workQueueReason","400","300","yes","80","80","yes");
}

function assignToSelf() {
	var requestId = getCurrentRowVal("requestId");
	
	j$.ajax({
		url:"catalogaddqcresults.do",
		cache:false,
		data:{uAction: "assignToSelf", 
			requestId: getCurrentRowVal("requestId")
		},
		success: function(data) {
			var n = data.search(/error/i);
			if (n == 0) {
				alert(data);
			}
			else {
				setCellValue(selectedRowId, "assignedTo", data + "; Vendor Reported as Problem");
			}
		}
	});
}

function problemReported() {
    parent.autoSubmitSearchForm();
}

function openTranslation() {
    var companyId = getCurrentRowVal("companyId");
    var requestId = getCurrentRowVal("requestId");
    var lineItem = getCurrentRowVal("lineItem");
    var loc = "/tcmIS/catalog/catalogaddtranslation.do?uAction=&companyId="+companyId+"&requestId="+requestId+"&alternateDb="+$v("alternateDb");
    try {
        parent.parent.openIFrame("pendingTranslation"+requestId,loc,messagesData.pendingTranslation+" "+requestId,"","N");
    }catch (ex) {
        openWinGeneric(loc,"pendingTranslation","900","600","yes","80","80","yes");
    }
}

function getCurrentRowVal(name) {
	return encodeURIComponent(cellValue(selectedRowId, name));
}

function openCatalogCompanySDSQC() {
    openCatalogMSDSMaintenance("SDS QC");
}

function openCatalogCompanyMSDS() {
    openCatalogMSDSMaintenance("Custom Indexing");
}

function openCatalogMSDSIndexing() {
    openCatalogMSDSMaintenance("MSDS Indexing");
}

function openCatalogMSDS() {
    openCatalogMSDSMaintenance("Material QC");
}

function openCatalogMSDSMaintenance(approvalRole) {
	var requestId = getCurrentRowVal("requestId");
	var lineItem = getCurrentRowVal("lineItem");

    var loc="/tcmIS/catalog/msdsindexingmain.do?uAction=getMaterialQc&requestId="+requestId+"&lineItem="+lineItem+"&approvalRole="+encodeURIComponent(approvalRole);
	try {
		parent.parent.openIFrame("ChemReq"+requestId+"-"+lineItem,loc,messagesData.chemreq+" "+requestId +"-"+lineItem,"","N");
	}
	catch (ex) {
		openWinGeneric(loc,"catalogMSDS","900","600","yes","80","80","yes");
	}
}

function openCatalogQC() {
	var requestId = getCurrentRowVal("requestId");
	var lineItem = getCurrentRowVal("lineItem");
	var companyId = getCurrentRowVal("companyId");
	var itemId = getCurrentRowVal("itemId");
	
	var loc="/tcmIS/catalog/catalogitemqcdetails.do?uAction=search&companyId="+companyId+"&requestId="+requestId+"&lineItem="+lineItem+"&itemId="+itemId+"&approvalRole="+encodeURIComponent("Item QC");
	try {
		parent.parent.openIFrame("ChemReqQC"+requestId+"-"+lineItem,loc,messagesData.chemreq+" QC","","N");
	}
	catch (ex) {
		openWinGeneric(loc,"catalogQC","900","600","yes","80","80","yes");
	}
}

/*function openCatalogQC() {
	var requestId = getCurrentRowVal("requestId");
	var lineItem = getCurrentRowVal("lineItem");
	var companyId = getCurrentRowVal("companyId");
	var loc = "/tcmIS/Catalog/catqcdetails?isTab=Item&Action=details&request_id="+requestId+"&searchlike=car.request_id&searchfor=Equal&line_item="+lineItem ;
	loc += "&Company=" + companyId+"&alternateDb="+$v("alternateDb");
	try {
		parent.parent.openIFrame("ChemReqQC",loc,messagesData.chemreq+" QC","","N");
	}
	catch (ex) {
		openWinGeneric(loc,"catalogQc","900","600","yes","80","80","yes");
	}


}*/

function openCatalogSpecQC() {
	var requestId = getCurrentRowVal("requestId");
	var lineItem = getCurrentRowVal("lineItem");
	var companyId = getCurrentRowVal("companyId");
	var loc = "/tcmIS/catalog/catalogspecqc.do?uAction=search&requestId="+requestId+"&lineItem="+lineItem+"&companyId="+companyId;
	
	try {
		parent.parent.openIFrame("ChemReq"+requestId+"-"+lineItem,loc,messagesData.chemreq+" "+requestId +"-"+lineItem,"","N");
	}
	catch (ex) {
		openWinGeneric(loc,"catalogQc","900","600","yes","80","80","yes");	
	}
		
		
}

function update() {
	document.genericForm.target='';
	document.getElementById('uAction').value = 'update';

    var now = new Date();
    parent.document.getElementById("startSearchTime").value = now.getTime();
	parent.showPleaseWait();

	// Call this function to send the data from grid back to the server
	if (mygrid != null) {
		mygrid.parentFormOnSubmit();
	}

	document.genericForm.submit();
}

function onChangeAssignedTo(rowId, col) {
	var cid = "updated" + rowId;
	$(cid).checked = true;
	updateHchStatusA(cid);
}

function doRefresh(requestTabId) {
	window.setTimeout('finishRefresh();', 10);
	//CLose current tab
	parent.parent.closeTab();
}

function finishRefresh() {
	// Select this tab
	parent.parent.togglePage("catalogAddProcess");

	// redo the search
	document.genericForm.target='';
	document.getElementById('uAction').value = 'search';

	parent.showPleaseWait();


	document.genericForm.submit();
}