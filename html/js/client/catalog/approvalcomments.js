var _approvalComment_dhxWins = null;
var approvalCommentWin = null;

function showApprovalCommentWin() {
    if( _approvalComment_dhxWins == null ) {
        _approvalComment_dhxWins = new dhtmlXWindows();
        _approvalComment_dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
    }
    if (!_approvalComment_dhxWins.window("ShowApprovalComment")) {
	var ApprovalCommentWinArea = document.getElementById('ApprovalCommentWinArea'); 
	var approvalCommentInnerDiv = document.getElementById('approvalCommentInnerDiv');

		initPopupGridWithGlobal(approvalCommentGridConfig);
        // create window first time
        approvalCommentWin = _approvalComment_dhxWins.createWindow("ShowApprovalComment", 100, 100, 800, 450);
        approvalCommentWin.setText(messagesData.comments);
        approvalCommentWin.clearIcon();  // hide icon
        //approvalCommentWin.denyResize(); // deny resizing
        approvalCommentWin.denyPark();   // deny parking

        approvalCommentWin.attachObject("ApprovalCommentWinArea");
        //approvalCommentWin.center();
        
        approvalCommentWin.attachEvent("onResizeFinish", function(approvalCommentWin){
		  var dim = approvalCommentWin.getDimension();
		  var parentDiv = ApprovalCommentWinArea;
		  parentDiv.style.width = dim[0] + 'px';
		  parentDiv.style.height = dim[1] + 'px';
		  var containingDiv = approvalCommentInnerDiv;
		  containingDiv.style.width = (dim[0] - 20) + 'px';
		  containingDiv.style.height = (dim[1] - 80) + 'px';
		  var grid1 = beanApprovalCommentGrid;
		   grid1.setSizes();
		  resizeGridToWidth(grid1, dim[0]);
	  });
        
        
        approvalCommentWin.button("minmax1").hide();
        approvalCommentWin.button("park").hide();
        approvalCommentWin.button("close").hide();
        approvalCommentWin.setModal(true);
        updateColumnWidths(beanApprovalCommentGrid);
    }else{
        // just show
        approvalCommentWin.setModal(true);
        _approvalComment_dhxWins.window("ShowApprovalComment").show();
    }
    addApprovalCommentRow();
    $("approvalCommentActionSpan").innerHTML = '&nbsp;&nbsp;<a href="#" onclick="closeApprovalCommentWin(); return false;">'+messagesData.cancel+'</a>'+
    ' | <a href="#" onclick="returnSelectedComment(); return false;">'+messagesData.returnSelectedData+'</a>';
}

function clearApprovalCommentRow() {
	for(var i = beanApprovalCommentGrid.getRowsNum(); i > 0; i--) {
		beanApprovalCommentGrid.deleteRow(i);
	}
}

function addApprovalCommentRow() {
    clearApprovalCommentRow();
    tmpCurrentSelectedApprovalRole = beanApprovalRoleGrid.cells(beanApprovalRoleGrid.getSelectedRowId(),beanApprovalRoleGrid.getColIndexById("approvalRole")).getValue();
    approvalCommentRowCount = 1;
    for(var j = 0; j < altApprovalComment.length; j++ ) {
        if (altApprovalComment[j].approvalRole == htmlDencodeGrid(tmpCurrentSelectedApprovalRole)) {
            beanApprovalCommentGrid.addRow(approvalCommentRowCount+1,"",approvalCommentRowCount);
            beanApprovalCommentGrid.cells(approvalCommentRowCount+1,0).setValue("Y");
            beanApprovalCommentGrid.cells(approvalCommentRowCount+1,1).setValue(false);
            beanApprovalCommentGrid.cells(approvalCommentRowCount+1,2).setValue(altApprovalComment[j].requestComment);
            approvalCommentRowCount++;
        }
    }
}


function selectApprovalCommentRow() {
    rowId0 = arguments[0];
    colId0 = arguments[1];
    ee     = arguments[2];

    $("approvalCommentActionSpan").innerHTML = '&nbsp;&nbsp;<a href="#" onclick="closeApprovalCommentWin(); return false;">'+messagesData.cancel+'</a>'+
                                               ' | <a href="#" onclick="returnSelectedComment(); return false;">'+messagesData.returnSelectedData+'</a>';
}

function returnSelectedComment() {
    var tmpApprovalComment = '';
    for(var i = 1;i < beanApprovalCommentGrid.getRowsNum(); i++)
    {
    	var rowId = beanApprovalCommentGrid.getRowId(i);
      	if(beanApprovalCommentGrid.cells(rowId,beanApprovalCommentGrid.getColIndexById("ok")).getValue() == 'true')
    		tmpApprovalComment += beanApprovalCommentGrid.cells(rowId,beanApprovalCommentGrid.getColIndexById("comment")).getValue() + '; ';
    }
    var perviousReasons = beanApprovalRoleGrid.cells(beanApprovalRoleGrid.getSelectedRowId(),beanApprovalRoleGrid.getColIndexById("reason")).getValue();
    
    if(perviousReasons !='' )
    	tmpApprovalComment = perviousReasons + tmpApprovalComment;
    	
    beanApprovalRoleGrid.cells(beanApprovalRoleGrid.getSelectedRowId(),beanApprovalRoleGrid.getColIndexById("reason")).setValue(tmpApprovalComment);
    closeApprovalCommentWin();
}

function closeApprovalCommentWin() {
    _approvalComment_dhxWins.window("ShowApprovalComment").setModal(false);
    _approvalComment_dhxWins.window("ShowApprovalComment").hide();
}