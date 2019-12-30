var mr_approval_detail_dhxWins = null;
var mrApprovalWin = null;
var mrTempDiv = null;


var financeBeanGrid = null;
var releaserBeanGrid = null;
var useBeanGrid = null;
var listBeanGrid = null;
var approverBeanGrid = null;

function showMrApprovalDetail(companyId,prNum,line) {
	callToServer('showmrapprovaldetail.do?callback=showMrApprovalDetailCallback&prNumber='+prNum+'&lineItem='+line+'&companyId='+companyId);
}

var attaObj = null;
function showMrApprovalDetailCallback(attachObj,financeGridConfig,releaserGridConfig,useGridConfig,listGridConfig,approverGridConfig,title) {
	  attaObj = attachObj;
	  try{
		if (mrTempDiv == null ) {
		   mrTempDiv = document.createElement('div');
			mrTempDiv.id = '_showmrapprovaldetail_';
			mrTempDiv.style.display='none';
			mrTempDiv.style.overflow = 'auto';
			mrTempDiv.style.border='0px';
			mrTempDiv.style.width='650px';
			mrTempDiv.style.height='550px';
			document.body.appendChild(mrTempDiv);
		}

		attaObj = document.getElementById('_showmrapprovaldetail_');
	  	attaObj.innerHTML = attachObj.innerHTML;
	  	
	  	financeBeanGrid = null;
	  	releaserBeanGrid = null;
	  	useBeanGrid = null;
	  	listBeanGrid = null;
	  	approverBeanGrid = null;
		
	  	if (financeGridConfig.beanData.length != '0') {
		  initPopupGridWithGlobal(financeGridConfig);
		}
		if (releaserGridConfig.beanData.length != '0') {
		  initPopupGridWithGlobal(releaserGridConfig);
		}
		if (useGridConfig.beanData.length != '0') {
			initPopupGridWithGlobal(useGridConfig);
		}
		if (listGridConfig.beanData.length != '0') {
			initPopupGridWithGlobal(listGridConfig);
		}
		if (approverGridConfig.beanData.length != '0') {
			initPopupGridWithGlobal(approverGridConfig);
		}
      }catch(ex) {}
	  
  if( mr_approval_detail_dhxWins == null ) {
	mr_approval_detail_dhxWins = new dhtmlXWindows();
	mr_approval_detail_dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  }
  if (!mr_approval_detail_dhxWins.window("ShowMrApprovalDetail")) {
	  // create window first time
	  mrApprovalWin = mr_approval_detail_dhxWins.createWindow("ShowNewShemAppDetail", 0, 0, 655, 555);
	  mrApprovalWin.setText(title); // well, this string should be passed from the shownewchemappdetailcallback
															  // and internationalize from that screen.
	  mrApprovalWin.clearIcon();  // hide icon
	  //mrApprovalWin.denyResize(); // deny resizing
	  mrApprovalWin.denyPark();   // deny parking
	  mrApprovalWin.attachObject(attaObj);
	  mrApprovalWin.attachEvent("onClose", function(mrApprovalWin){mrApprovalWin.hide();});
	  mrApprovalWin.attachEvent("onResizeFinish", function(mrApprovalWin){
		  var containingDiv = mrTempDiv;
		  var dim = mrApprovalWin.getDimension();
		  var width = dim[0] - 5;
		  containingDiv.style.width = width + 'px';
		  containingDiv.style.height = dim[1] + 'px';
		  var grid1 = financeBeanGrid;
		  var grid2 = releaserBeanGrid;
		  var grid3 = useBeanGrid;
		  var grid4 = listBeanGrid;
		  var grid5 = approverBeanGrid;
		  // Auto adjust height of List Approver div
		  var listDiv = document.getElementById('ListDiv');
		  if (dim[1] > 555) {
			  listDiv.style.height = 145 + (dim[1] - 555) + 'px';
			  grid4.setSizes();
		  }else if (dim[1] < 555) {
			  listDiv.style.height = '145px';
			  grid4.setSizes();
		  }

		  resizeGridToWidth(grid1, width);
		  resizeGridToWidth(grid2, width);
		  resizeGridToWidth(grid3, width);
		  resizeGridToWidth(grid4, width);
		  resizeGridToWidth(grid5, width);
	  });
	  mrApprovalWin.center();
  }else{
    // just show
    mr_approval_detail_dhxWins.window("ShowMrApprovalDetail").show();
  }
}

