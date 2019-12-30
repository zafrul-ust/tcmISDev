var specBeanGrid = null;
var selectedRowId = null;
var children = new Array();
var dhxWins = null;

var resizeGridWithWindow = true;

function resultOnLoad() {

	document.getElementById("mainUpdateLinks").style["display"] = "";
   try {
	   initGridWithGlobal(gridConfig);
   }
   catch(ex) {}
   
	/*It is important to call this after all the divs have been turned on or off.*/
 	setResultFrameSize();
}

//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
}

function showTransitWin()
{
	document.getElementById("transitLabel").innerHTML = messagesData.pleasewait;

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);
		dhxWins.window("transitDailogWin").show();
	}
}

function selectRow(rowId){
	selectedRowId = rowId;
}

function selectRightclick(rowId,cellInd) {	
	selectedRowId = rowId;
	
	if($v('viewLevel') == 'approver') {
		var specName = cellValue(rowId,"specName");
		var updatePermission = cellValue(rowId,"permission");

        vitems = new Array();
        if(specName == 'Std Mfg Cert' || updatePermission == 'N'){
			vitems[vitems.length ] = "text="+messagesData.addSpec+";url=javascript:addSpec()";
			var dataSource = cellValue(rowId,"dataSource");
			if (dataSource == "QC") {
				vitems[vitems.length ] = "text="+messagesData.deleteSpec+";url=javascript:deleteSpec()";
			}
        }else{
			var specUpdatePermission = cellValue(rowId,"specNamePermission");
			if(specUpdatePermission == 'Y') {
				vitems[vitems.length ] = "text="+messagesData.uploadSpec+";url=javascript:uploadImage()";
            }
            var onLine = cellValue(rowId,"onLine");
            //always allow users to view spec if it's online
            if (onLine == 'Y') {
                //vitems[vitems.length ] = "text="+messagesData.deleteuploadedimage+";url=javascript:deleteUploadedimage()";
                vitems[vitems.length ] = "text="+messagesData.viewSpec+";url=javascript:viewSpecImage()";
            }
            vitems[vitems.length ] = "text="+messagesData.addSpec+";url=javascript:addSpec()";
            vitems[vitems.length ] = "text="+messagesData.modifySpec+";url=javascript:modifySpec()";
            vitems[vitems.length ] = "text="+messagesData.deleteSpec+";url=javascript:deleteSpec()";
            vitems[vitems.length ] = "text="+messagesData.addmodifyspecdetail+";url=javascript:getDetail()";
		}
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

// add/modify spec
function updateSpecDetail(detail, specDetailType, specDetailClass,
		specDetailForm, specDetailGroup, specDetailGrade, specDetailStyle, specDetailFinish,
		specDetailSize, specDetailColor, specDetailOther)
{
	var detail = detail;
	   if(!specDetailType == ""){detail = detail + "Ty "+specDetailType};
	   if(!specDetailClass == ""){detail = detail + " Cl "+specDetailClass};
	   if(!specDetailForm == ""){detail = detail + " Frm "+specDetailForm};
	   if(!specDetailGroup == ""){detail = detail + " Grp "+specDetailGroup};
	   if(!specDetailGrade == ""){detail = detail + " Gr "+specDetailGrade};
	   if(!specDetailStyle == ""){detail = detail + " Sty "+specDetailStyle};
	   if(!specDetailFinish == ""){detail = detail + " Fin "+specDetailFinish};
	   if(!specDetailSize == ""){detail = detail + " Sz "+specDetailSize};
	   if(!specDetailColor == ""){detail = detail + " Clr "+specDetailColor};
	   if(!specDetailOther == ""){detail = detail + " Other "+specDetailOther};
	   cell(selectedRowId,"specDetail").setValue(detail);
}

function getDetail() {
    if(cellValue(selectedRowId,"updated"))
		alert(messagesData.savebeforeaddmodifydetail);
	else if(validateRow()){
		showTransitWin();
		var specId = cellValue(selectedRowId,"specId");
		var specName = cellValue(selectedRowId,"specName");
		var specLibrary = cellValue(selectedRowId,"specLibrary");
		var specAmendment = cellValue(selectedRowId,"specAmendment");
		var specVersion = cellValue(selectedRowId,"specVersion");
		
		loc = "/tcmIS/distribution/newdetail.do?uAction=search&fromSpecQC=Yes&companyId="+$v("companyId")+"&requestId="+$v("requestId")+"&specId="+specId+"&specName="+encodeURIComponent(specName)+"&specLibrary="+encodeURIComponent(specLibrary)+"&specAmendment="+specAmendment+"&specVersion="+specVersion;
	
		try {
			children[children.length] = openWinGeneric(loc,"newdetail","700","500","yes","50","50","20","20","no");
		} catch (ex){
			openWinGeneric(loc,"newdetail","700","500","yes","50","50","20","20","no");
		} 
	}
}

function specOnlineUpdate() {
    cell(selectedRowId,"onLine").setValue("Y");
    if ($v("itarControl") == 'true')
        cell(selectedRowId,"itar").setValue("Y");      
}

function uploadImage(){
	showTransitWin();
	tmpSpecId = cellValue(selectedRowId,"specId");
    //for file name remove any space
    var fileName = $v("requestId")+"-"+tmpSpecId.replace(/\s/g, "");

    var tmpUrl = "uploadfile.do?fileName="+fileName+"&modulePath=catalogAddSpec&calledFrom=catalogAddSpecQc&companyId="+$v("companyId")+"&requestId="+$v("requestId")+"&specId="+tmpSpecId;
    children[children.length] = openWinGeneric(tmpUrl,"uploadfile",500,200,'yes' );
}

function viewSpecImage(){
    var tmpUrl = '';
    if ($v("secureDocViewer") == 'true')
        tmpUrl = '/DocViewer/client/';
    tmpUrl += 'docViewer.do?uAction=viewCatalogAddSpecQc'
                 +'&spec=' + encodeURIComponent(cellValue(selectedRowId,"specId"))
                 +'&catalogAddRequestId='+$v("requestId")
                 +'&companyId='+$v("companyId");
    openWinGeneric(tmpUrl,"ViewSpec","800","800",'yes' );
}

function addSpec(){
    showTransitWin();
    var tmpUrl = 'speclookupmain.do?calledFrom=catalogAddSpecQc&companyId='+$v("companyId")+"&requestId="+$v("requestId");
    children[children.length] = openWinGeneric(tmpUrl,"specLookup",800,800,'yes' );
}

function modifySpec(){
    showTransitWin();
    var tmpUrl = 'speclookupmain.do?calledFrom=catalogAddSpecQc&companyId='+$v("companyId")+"&requestId="+$v("requestId")+"&modifySpecId="+encodeURIComponent(cellValue(selectedRowId,"specId"));
    children[children.length] = openWinGeneric(tmpUrl,"specLookup",800,800,'yes' );
}

function deleteSpec() {
    document.genericForm.target = '';
    $('uAction').value = 'deleteSpec';
    $('specId').value = cellValue(selectedRowId,"oldSpecId");
    $('lineItem').value = cellValue(selectedRowId,"lineItem");
    
    showTransitWin(); // Show "please wait" while updating
    document.genericForm.submit(); // Submit the form
}

function addExistingSpec(specId,specLibrary,modifySpecId) {
    document.genericForm.target = '';
    $('uAction').value = 'addExistingSpec';
    $("specId").value = specId;
    $("specLibrary").value = specLibrary;
    $("oldSpecId").value = modifySpecId;
    $("lineItem").value = cellValue(selectedRowId,"lineItem");
    // set start search time
    var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
    startSearchTime.value = now.getTime();
    showTransitWin(); // Show "please wait" while updating

    if (specBeanGrid != null) {
        // This function prepares the grid dat for submitting top the server
        specBeanGrid.parentFormOnSubmit();
    }
    document.genericForm.submit(); // Submit the form
}

function addNewSpec(specName,specTitle,specVersion,specAmendment,coc,coa) {
    document.genericForm.target = '';
    $('uAction').value = 'addNewSpec';
    $("specName").value = specName;
    $("specTitle").value = specTitle;
    $("specVersion").value = specVersion;
    $("specAmendment").value = specAmendment;
    $("coc").value = coc;
    $("coa").value = coa;
    // set start search time
    var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
    startSearchTime.value = now.getTime();
    showTransitWin(); // Show "please wait" while updating

    if (specBeanGrid != null) {
        // This function prepares the grid dat for submitting top the server
        specBeanGrid.parentFormOnSubmit();
    }
    document.genericForm.submit(); // Submit the form
}

function deleteUploadedImage(){
	cell(selectedRowId,"onLine").setValue("N");    
}

function validateRow() {
	var result = true;
	var missingFields = "";
	
	if (cellValue(selectedRowId,"specId") == null || cellValue(selectedRowId,"specId").length < 1) 
				missingFields += "\n"+messagesData.id;
	
	if (cellValue(selectedRowId,"specLibrary") == null || cellValue(selectedRowId,"specLibrary").length < 1) 
				missingFields += "\n"+messagesData.library;
	
	if (missingFields.length > 0) {
		alert(messagesData.validvalues+missingFields);
		result = false;
	}
	return result;
}

function validateForm() {
	var result = true;
	var missingFields = "";
	
	for(var i = 1; i <= specBeanGrid.getRowsNum();i++) {
		if (cellValue(i,"specName") == null || cellValue(i,"specName").length < 1) {
			if(missingFields.indexOf(messagesData.name) < 0)
				missingFields += "\n"+messagesData.name;
		}
	}
	
	if (missingFields.length > 0) {
		alert(messagesData.validvalues+missingFields);
		result = false;
	}
	return result;
}

function auditData() {
	var result = true;
	var missingFields = "";
	
	for(var i = 1; i <= specBeanGrid.getRowsNum();i++) {
		if(cellValue(i,"dataSource") == 'catalog_add_spec_qc') {
			if (cellValue(i,"specId") == null || cellValue(i,"specId").length < 1) {
				if(missingFields.indexOf(messagesData.id) < 0)
					missingFields += "\n"+messagesData.id;
			}
		
			if (cellValue(i,"specLibrary") == null || cellValue(i,"specLibrary").length < 1) {
				if(missingFields.indexOf(messagesData.library) < 0)
					missingFields += "\n"+messagesData.library;
			}
		}
	}
	
	if (missingFields.length > 0) {
		alert(messagesData.validvalues+missingFields);
		result = false;
	}
	return result;
}

function submitApproveForm() {
	if (auditData()) {
        try {
        	document.genericForm.target = '';
        	$('uAction').value = "approve";
    		
    		showTransitWin(); // Show "please wait" while updating
    		
    		if (specBeanGrid != null) {
    	        // This function prepares the grid dat for submitting top the server
    	        specBeanGrid.parentFormOnSubmit();
    	    }
    		
    		document.genericForm.submit();
        } catch(ex) {}

	}
}

function submitRejectForm() {
	if (validateForm()) {
        try {
        	document.genericForm.target = '';
        	$('uAction').value = "reject";
    		
    		showTransitWin(); // Show "please wait" while updating
    		
    		document.genericForm.submit();
        } catch(ex) {}

	}
}

function saveData() {
	if(validateForm()){
		document.genericForm.target = '';
	    $('uAction').value = 'save';
	
	    showTransitWin(); // Show "please wait" while updating
	
	    if (specBeanGrid != null) {
	        // This function prepares the grid dat for submitting top the server
	        specBeanGrid.parentFormOnSubmit();
	    }
	    document.genericForm.submit(); // Submit the form
	}
}

function showApprovalDetail() {
	var requestId = document.getElementById("requestId").value;
	if(requestId.value!='')  {	  
		showNewChemApprovalDetail(requestId);
	}
}

function submitRevert() {
	document.genericForm.target = '';
    $('uAction').value = 'revert';

    showTransitWin(); // Show "please wait" while updating

    if (specBeanGrid != null) {
        // This function prepares the grid dat for submitting top the server
        specBeanGrid.parentFormOnSubmit();
    }
    document.genericForm.submit(); // Submit the form
}

function lineUpdated() {
	cell(selectedRowId,"updated").setValue(true); 
}

function updateSpecId() {
	var specName = cellValue(selectedRowId,"specName");
	var specVersion = cellValue(selectedRowId,"specVersion");
	var specAmendment = cellValue(selectedRowId,"specAmendment");

	var specId = specName;
	
	if(!specVersion == ""){specId = specId + "_"+specVersion};
	if(!specAmendment == ""){specId = specId + "_"+specAmendment};
	
    cell(selectedRowId,"specId").setValue(specId); 
    lineUpdated();
}