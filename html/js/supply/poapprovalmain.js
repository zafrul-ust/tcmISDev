
var children=[];

function lookupSuppliers() {
	/*var loc = "supplierlookup.do?uAction=search";
	children[children.length] = openWinGeneric(loc,"SupplierLookup","300","300","yes","50","50","yes");*/
	
	var opsEntityId = $v("opsEntityId");
	var inventoryGroup = $v("inventoryGroup");
	var rowNumber = 1;	
	loc = "/tcmIS/distribution/entitysuppliersearchmain.do?valueElementId=supplierId&statusFlag=true&displayElementId=supplierName&fromPoApproval=Y&inventoryGroup="+inventoryGroup+"&opsEntityId="+opsEntityId+"&rowNumber="+rowNumber;
	openWinGeneric(loc,"Supplier","600","500","yes","50","50");	
}

function supplierChanged(supplier) {
	$("supplier").value = supplier.returnSelectedId;
	$("supplierName").value = supplier.returnSelectedValue;
}

function clearSupplier() {
	$("supplier").value = "";
}

function submitSearchForm() {
	 var now = new Date();
	document.getElementById("startSearchTime").value = now.getTime();
	$("uAction").value = "search";
	document.genericForm.target='resultFrame';
	showPleaseWait();
	return true;
}

function generateExcel() {
	var searchWhat = $("searchWhat");
	var searchWhatDesc = $("searchWhatDesc");
	searchWhatDesc.value = searchWhat.options[searchWhat.selectedIndex].text;
	var searchType = $("searchType");
	var searchTypeDesc = $("searchTypeDesc");
	searchTypeDesc.value = searchType.options[searchType.selectedIndex].text;
	var buyerId = $("buyerId");
	var selectedBuyer = $("selectedBuyer");
	selectedBuyer.value = buyerId.options[buyerId.selectedIndex].text;
	var approverId = $("approverId");
	var selectedApprover = $("selectedApprover");
	selectedApprover.value = approverId.options[approverId.selectedIndex].text;
	var opsEntityId = $("opsEntityId");
	var opsEntityDesc = $("opsEntityDesc");
	opsEntityDesc.value = opsEntityId.options[opsEntityId.selectedIndex].text;
	var branchPlant = $("branchPlant");
	var hubName = $("hubName");
	hubName.value = branchPlant.options[branchPlant.selectedIndex].text;
	var inventoryGroup = $("inventoryGroup");
	var inventoryGroupDesc = $("inventoryGroupDesc");
	inventoryGroupDesc.value = inventoryGroup.options[inventoryGroup.selectedIndex].text;
	
	document.getElementById("uAction").value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_poApprovalGenerateExcel','650','600','yes');
    document.genericForm.target='_poApprovalGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
}

function setSearchWhat(searchWhatArray) {
	var obj = $("searchWhat");
	for ( var i = obj.length; i >= 0; i--)
		obj[i] = null;

	for ( var j = 0; j < searchWhatArray.length; j++) {
		setOption(j, searchWhatArray[j].text, searchWhatArray[j].id,
				"searchWhat");
	}
}

function setApproveButtonVisible(visible) {
	if (visible) {
		$("approveLink").style.display="";
	}
	else {
		$("approveLink").style.display="none";
	}
}

function selectStatus(status) {
	$("selectedStatus").value=status;
}

function closeAllchildren() {
	try {
		for(var n=0;n<children.length;n++) {
			try {
				children[n].closeAllchildren();
			} catch(ex){}
			children[n].close();
		}
	} catch(ex){}
	children = [];
}