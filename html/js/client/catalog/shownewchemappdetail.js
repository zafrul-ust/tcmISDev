var _newChemAppDetail_dhxWins = null;
var reqId = '';
var newCatAddApprovalWin = null;
var newCatAddApprovalTempDiv = null;

function showNewChemApprovalDetail(requestId) {
	reqId = requestId;
	callToServer('shownewchemappdetail.do?callback=shownewchemappdetailcallback&requestId='+requestId);
}

var attaObj = null;
function shownewchemappdetailcallback(attachObj,hgridConfig,gridConfig,title) {
	  attaObj = attachObj;
	  try{
		  if (newCatAddApprovalTempDiv == null) {
		  	newCatAddApprovalTempDiv = document.createElement('div');
			newCatAddApprovalTempDiv.id = '_shownewchemappdetail_' ;
			newCatAddApprovalTempDiv.style.display='none';
			newCatAddApprovalTempDiv.style.overflow = 'auto';
			newCatAddApprovalTempDiv.style.border='0px';
			newCatAddApprovalTempDiv.style.width='855px';
			newCatAddApprovalTempDiv.style.height='450px';
			document.body.appendChild(newCatAddApprovalTempDiv);
		  }
		  attaObj = document.getElementById('_shownewchemappdetail_');
	  	  attaObj.innerHTML = attachObj.innerHTML;
	  	  initPopupGridWithGlobal(hgridConfig);
	  	  initPopupGridWithGlobal(gridConfig);
	  }catch(ex) {}
	  
  if( _newChemAppDetail_dhxWins == null ) {
	_newChemAppDetail_dhxWins = new dhtmlXWindows();
	_newChemAppDetail_dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  }
  if (!_newChemAppDetail_dhxWins.window("ShowNewShemAppDetail")) {
	  // create window first time
	  newCatAddApprovalWin = _newChemAppDetail_dhxWins.createWindow("ShowNewShemAppDetail", 100, 100, 850, 450);
	  newCatAddApprovalWin.setText(title); // well, this string should be passed from the shownewchemappdetailcallback
															  // and internationalize from that screen.
	  newCatAddApprovalWin.clearIcon();  // hide icon
	  //newCatAddApprovalWin.denyResize(); // deny resizing
	  newCatAddApprovalWin.denyPark();   // deny parking
	  newCatAddApprovalWin.attachObject(attaObj);
	  newCatAddApprovalWin.attachEvent("onClose", function(newCatAddApprovalWin){newCatAddApprovalWin.setModal(false);newCatAddApprovalWin.hide();});
	  newCatAddApprovalWin.attachEvent("onResizeFinish", function(newCatAddApprovalWin){
		  var containingDiv = newCatAddApprovalTempDiv;
		  var dim = newCatAddApprovalWin.getDimension();
		  var width = dim[0] - 5;
		  containingDiv.style.width = width + 'px';
		  containingDiv.style.height = dim[1] + 'px';
		  var grid1 = hDetailBeanGrid;
		  var grid2 = hbeanGrid;
		  
		  // Auto adjust height of List Approver div
		  var detailDiv = document.getElementById('DetailBean');
		  if (dim[1] > 400) {
			  detailDiv.style.height = 200 + (dim[1] - 400) + 'px';
			  grid1.setSizes();
		  }
		  else if (dim[1] < 400) {
			  detailDiv.style.height = '200px';
			  grid1.setSizes();
		  }

		  
		  resizeGridToWidth(grid1, width);
		  resizeGridToWidth(grid2, width);
	  });
	  //newCatAddApprovalWin.center();
	  newCatAddApprovalWin.setModal(true);
	  updateColumnWidths(hDetailBeanGrid);
	  updateColumnWidths(hbeanGrid);
  }else{
    // just show
	 newCatAddApprovalWin.setModal(true);
	 _newChemAppDetail_dhxWins.window("ShowNewShemAppDetail").show();
     var containingDiv = newCatAddApprovalTempDiv;
		  var dim = newCatAddApprovalWin.getDimension();
		  var width = dim[0] - 5;
		  containingDiv.style.width = width + 'px';
		  containingDiv.style.height = dim[1] + 'px';
		  var grid1 = hDetailBeanGrid;
		  var grid2 = hbeanGrid;

		  // Auto adjust height of List Approver div
		  var detailDiv = document.getElementById('DetailBean');
		  if (dim[1] > 400) {
			  detailDiv.style.height = 200 + (dim[1] - 400) + 'px';
			  grid1.setSizes();
		  }
		  else if (dim[1] < 400) {
			  detailDiv.style.height = '200px';
			  grid1.setSizes();
		  }


		  resizeGridToWidth(grid1, width);
		  resizeGridToWidth(grid2, width);
  }
}