var _newchem_dhxWins = null;
var approvalRoleWin = null;
function showApprovalRoleWin() {
  if( _newchem_dhxWins == null ) {
	_newchem_dhxWins = new dhtmlXWindows();
	_newchem_dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  }
  if (!_newchem_dhxWins.window("ShowApprovalRole")) {
	var approvalRoleDataWinArea = document.getElementById('ApprovalRoleDataWinArea'); 
	var approvalRoleDataInnerDiv = document.getElementById('approvalRoleDataInnerDiv');

	  initPopupGridWithGlobal(approvalRoleGridConfig);
	  // create window first time
	  approvalRoleWin = _newchem_dhxWins.createWindow("ShowApprovalRole", 150, 150, 800, 300);
	  approvalRoleWin.setText(messagesData.approvaldetail);
	  approvalRoleWin.clearIcon();  // hide icon
	  //approvalRoleWin.denyResize(); // deny resizing
	  approvalRoleWin.denyPark();   // deny parking

	  approvalRoleWin.attachObject("ApprovalRoleDataWinArea");
	  
	  approvalRoleWin.attachEvent("onResizeFinish", function(approvalRoleWin){
		  var dim = approvalRoleWin.getDimension();
		  var parentDiv = approvalRoleDataWinArea;
		  parentDiv.style.width = dim[0] + 'px';
		  parentDiv.style.height = dim[1] + 'px';
		  var containingDiv = approvalRoleDataInnerDiv;
		  containingDiv.style.width = (dim[0] - 20) + 'px';
		  containingDiv.style.height = (dim[1] - 80) + 'px';
		  var grid1 = beanApprovalRoleGrid;
		  grid1.setSizes();
		  resizeGridToWidth(grid1, dim[0]);
	  });
	  approvalRoleWin.button("minmax1").hide();
	  approvalRoleWin.button("park").hide();
	  approvalRoleWin.button("close").hide();
	  approvalRoleWin.setModal(true);
	  preSelectRole();
	  updateColumnWidths(beanApprovalRoleGrid);
  }else{
    // just show
	 approvalRoleWin.setModal(true);
	 _newchem_dhxWins.window("ShowApprovalRole").show();
  }
}

//pre select approval role if user is an approver for only one role
function preSelectRole() {
    if (beanApprovalRoleGrid.getRowsNum() == 1) {
        beanApprovalRoleGrid.cells(1,beanApprovalRoleGrid.getColIndexById("status")).setValue(true);
        beanApprovalRoleGrid.cells(1,beanApprovalRoleGrid.getColIndexById("status")).setChecked(true);
        beanApprovalRoleGrid.setSelectedRow(1);
    }
}

function selectApprovalRow() {
    rowId0 = arguments[0];
    colId0 = arguments[1];
    ee     = arguments[2];

    if (colId0 == beanApprovalRoleGrid.getColIndexById("lookupComment")) {
        if (beanApprovalRoleGrid.cells(beanApprovalRoleGrid.getSelectedRowId(),beanApprovalRoleGrid.getColIndexById("showLookupComment")).getValue() == 'Y') {
            showApprovalCommentWin();
        }
    }
}

function submitApproveRequest() {
	var approveOk = false;
	for (var i = 1; i <= beanApprovalRoleGrid.getRowsNum(); i++) {
        if(gridCellValue(beanApprovalRoleGrid,i,'status')) {
			approveOk = true;
		}
	}

	if (approveOk) {
        showTransitWin();
		//prepare grid for data sending
		beanApprovalRoleGrid.parentFormOnSubmit();
		_newchem_dhxWins.window("ShowApprovalRole").setModal(false);
  		_newchem_dhxWins.window("ShowApprovalRole").hide();
		$("uAction").value = 'approve';
  		document.genericForm.submit();
	}else {
		alert(messagesData.norowselected);
	}
}

function approveRequest() {
	$("approvedWithRestriction").value = "N";
    submitApproveRequest();
}

function approveWithRestriction() {
	$("approvedWithRestriction").value = "Y";
    submitApproveRequest();
}

function rejectRequest() {
	var approveOk = false;
	for (var i = 1; i <= beanApprovalRoleGrid.getRowsNum(); i++) {
		if(gridCellValue(beanApprovalRoleGrid,i,'status')) {
			approveOk = true;
		}
	}

	if (approveOk) {
		showTransitWin();
		//prepare grid for data sending
		beanApprovalRoleGrid.parentFormOnSubmit();
		_newchem_dhxWins.window("ShowApprovalRole").setModal(false);
  		_newchem_dhxWins.window("ShowApprovalRole").hide();
		$("uAction").value = 'reject';
  		document.genericForm.submit();
	}else {
		alert(messagesData.norowselected);
	}
}

function closeChemicalApprovalWin() {
  _newchem_dhxWins.window("ShowApprovalRole").setModal(false);
  _newchem_dhxWins.window("ShowApprovalRole").hide();
  setViewLevel();
}