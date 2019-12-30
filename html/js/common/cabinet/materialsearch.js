windowCloseOnEsc = true;
var currentMaterialId = '';
var currentMfgId = '';
var currentMaterialDesc = '';
var currentTradeName = '';
var selectedRow = 0;
function selectRow(rowid) {
	selectedRow = rowid;
	materialId = cellValue(rowid, "materialId");
	currentMaterialDesc = cellValue(rowid, "description");
	msdsNumber = cellValue(rowid, "msdsNumber");
	companyId = $v("companyId");
	catalogIdCatalogCompanyIdString = $v("catalogIdCatalogCompanyIdString");
	var mySplitResult = catalogIdCatalogCompanyIdString.split(",");
	
	catalogCompanyId = mySplitResult[1];
	catalogId = mySplitResult[0];
	
	var selectedMaterial = parent.document.getElementById("returnResultLink");
	selectedMaterial.innerHTML = ' <a href="#" onclick="call(\'selectData\'); return false;">' + messagesData.selectedRowMsg + ' (' + materialId + ' - ' + currentMaterialDesc + ')</a>';
}

function selectData() {
	
	parent.showTransitWin();
	var nonManagedMaterialUrl = "addnonmanagedmaterial.do?uAction=getSizeUnitString&companyId="+companyId+"&catalogCompanyId="+catalogCompanyId+"&catalogId="+catalogId+"&materialId="+materialId+"&msdsNumber="+msdsNumber+"&application="+$v('application')+"&applicationDesc="+$v('applicationDesc');
	var facilityId = $v('facilityId');
	if(facilityId != null || facilityId != undefined)
		nonManagedMaterialUrl += "&facilityId=" + facilityId;
	openWinGeneric(nonManagedMaterialUrl, "addNonManagedMaterial", "600", "275","yes", "50", "50");
}
