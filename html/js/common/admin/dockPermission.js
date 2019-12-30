windowCloseOnEsc = true;
var resizeGridWithWindow = true;
var beangrid;

function $(a) {
	return document.getElementById(a);
}

function isArray(testObject) { 
	return testObject &&
		!( testObject.propertyIsEnumerable('length')) &&
		typeof testObject === 'object' && 
		typeof testObject.length === 'number';
}

function showFacility(selectedCompany,selectedInv) {
	var idArray = altFacilityId[selectedCompany];
	var nameArray = altFacilityName[selectedCompany];
	var selectI = 0;
	var inserted = 0 ;
	
	var inv = $("facilityId");
	for (var i = inv.length; i > 0; i--) {
		inv[i] = null;
	}
	if( nameArray != null )
		for (var i=0; i < nameArray.length; i++) {
			if( nameArray.length != 2 || i == 1 ) {
				setOption(inserted,nameArray[i],idArray[i], "facilityId");
				if( selectedInv == idArray[i] )
					selectI = inserted;
				inserted++;
			}
		}
	if( inserted == 0 )
		setOption(inserted,messagesData.myfacilities,"", "facilityId");
	$("facilityId").selectedIndex = selectI;
}

function showCompany(oldCompany){
	var idArray = altCompanyId;
	var nameArray = altCompanyName;
	var selectI = 0 ;
	var inserted =0;
	
	for (var i=0; i < nameArray.length; i++) {
		if( nameArray.length != 2 || i != 0 ) {
			setOption(inserted,nameArray[i],idArray[i], "companyId");
			if( oldCompany == idArray[i] )
				selectI = inserted;
			inserted++;
		}
	}
	$("companyId").selectedIndex = selectI;
}

function setCompany() {
	var oldCompany =  $("oldcompanyId").value;
	var oldinv =  $("oldfacilityId").value;
	try {
		showCompany(oldCompany);
		showFacility($("companyId").value,oldinv);
	} catch (ex) {}
}

function CompanyChanged() {
	var Company = $("companyId");
	var selectedCompany = Company.value;
	showFacility(selectedCompany,null);
}

function validateForm() {
	return true;
}

function showErrorMessages() {
	parent.showErrorMessages();
}

function dockPermissionsUpdate() {
	document.getElementById('uAction').value="update";
	document.genericForm.target='';
	parent.showPleaseWait();
	beangrid.parentFormOnSubmit();
	document.genericForm.submit();
}

function resultOnLoad() {
	totalLines = document.getElementById("totalLines").value; 
	
	if (totalLines > 0)	{
		document.getElementById("UserGroupAccessInputBean").style["display"] = "";
		initializeGrid();
		parent.document.getElementById("mainUpdateLinks").style["display"] = "";
		if(showUpdateLinks)	{
			parent.document.getElementById("updateResultLink").style["display"] = "";
		} else {
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		}
		
		if(beangrid.getColIndexById("userGroupAccess") != null)	{
			showChecked();
		}
	} else {
		document.getElementById("UserGroupAccessInputBean").style["display"] = "none";
		parent.document.getElementById("mainUpdateLinks").style["display"] = "none";
	}
	
	/*This dislpays our standard footer message*/
	displayGridSearchDuration();
	
	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
	
	try{
		parent.resetTimer();
	} catch (ex) {}
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('UserGroupAccessInputBean');
	initGridWithConfig(beangrid,config,-1,true,true);
	if( typeof( jsonMainData ) != 'undefined' ) {
		beangrid.parse(jsonMainData,"json");
	}
	beangrid.attachEvent("onRowSelect",selectRow);
}

function selectRow(rowId, cellInd) {
	var columnId = beangrid.getColumnId(cellInd);
	switch (columnId) {
		case "userGroupAccess":
			rowChanged(rowId);
			checkOne(rowId,columnId);
		default:
	}	
}

function rowChanged(rowId) {
	beangrid.cellById(rowId, beangrid.getColIndexById("modified")).setValue("Y");
}

function checkOne(rowId,columnId) {
	var checkBox = $(columnId + rowId);
	
	if (checkBox != null && !checkBox.disabled)	{
		if(cell(rowId, columnId).getValue() == 'false')	{
			cell(rowId, columnId).setChecked(false);
		} else {
			cell(rowId, columnId).setChecked(true);
		}
	}
} 

function showChecked() {
	var numRows = beangrid.getRowsNum();
	for (var rowId = 1; rowId <= numRows; rowId++) {
		if(beangrid.cells(rowId,beangrid.getColIndexById("display")).getValue() == 'Y')	{
			beangrid.cells(rowId,beangrid.getColIndexById("userGroupAccess")).setDisabled(true);
		}
		
		if(beangrid.cells(rowId,beangrid.getColIndexById("userGroupAccess")).getValue() == '1')	{
			beangrid.cells(rowId,beangrid.getColIndexById("userGroupAccess")).setChecked(true);
		}
	}
}

function search() {
	document.getElementById('uAction').value="search";
	document.genericForm.target='resultFrame';
	parent.showPleaseWait();
	document.genericForm.submit();
	return true;
}

function createXSL() {
	document.getElementById('uAction').value="createXSL";
	document.genericForm.target='_excel_report_file';
	openWinGenericExcel('/tcmIS/common/loadingfile.jsp','_excel_report_file','650','600','yes');
	setTimeout("document.genericForm.submit()",300);
}
