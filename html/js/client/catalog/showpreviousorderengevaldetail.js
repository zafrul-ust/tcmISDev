var _previousorderengeval_dhxWins = null;
var previousOrderEngEvalApprovalWin = null;
var previousOrderEngEvalApprovalTempDiv = null;
var evalCommentAttachObj = null;
var evalCommentTempDiv = null;

function showPreviousOrderEngEvalDetail(itemId,facilityId,application) {
	showTransitWin("justPleaseWait");
	callToServer('showpreviousorderengevaldetail.do?callback=showPreviousOrderEngEvalDetailCallback&uAction=getPreviousOrderEngEval&itemId='+itemId+'&engEvalFacilityId='+encodeURIComponent(facilityId)+'&engEvalWorkArea='+encodeURIComponent(application));
}

var attaObj = null;
function showPreviousOrderEngEvalDetailCallback(attachObj,gridConfig,title,commentAttachObj) {
	  attaObj = attachObj;
	  evalCommentAttachObj = commentAttachObj;
	  try{
		  if (previousOrderEngEvalApprovalTempDiv == null) {
		  	previousOrderEngEvalApprovalTempDiv = document.createElement('div');
			previousOrderEngEvalApprovalTempDiv.id = '_showpreviousorderengevaldetail_' ;
			previousOrderEngEvalApprovalTempDiv.style.display='none';
			previousOrderEngEvalApprovalTempDiv.style.overflow = 'auto';
			previousOrderEngEvalApprovalTempDiv.style.border='0px';
			previousOrderEngEvalApprovalTempDiv.style.width='650px';
			previousOrderEngEvalApprovalTempDiv.style.height='330px';
			document.body.appendChild(previousOrderEngEvalApprovalTempDiv);
		  }
		  attaObj = document.getElementById('_showpreviousorderengevaldetail_');
	  	  attaObj.innerHTML = attachObj.innerHTML;
		  initPopupGridWithGlobal(gridConfig);

		  //this is for showing comments
		  if (evalCommentTempDiv == null) {
		  	evalCommentTempDiv = document.createElement('div');
			evalCommentTempDiv.id = '_showpreviousorderengevalcomment_' ;
			evalCommentTempDiv.style.display='none';
			evalCommentTempDiv.style.overflow = 'auto';
			evalCommentTempDiv.style.border='0px';
			evalCommentTempDiv.style.width='400px';
			evalCommentTempDiv.style.height='150px';
			document.body.appendChild(evalCommentTempDiv);
		  }
		  evalCommentAttachObj = document.getElementById('_showpreviousorderengevalcomment_');
	  	  evalCommentAttachObj.innerHTML = commentAttachObj.innerHTML;

	  }catch(ex) {}
	  
  if( _previousorderengeval_dhxWins == null ) {
	_previousorderengeval_dhxWins = new dhtmlXWindows();
	_previousorderengeval_dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  }
  if (!_previousorderengeval_dhxWins.window("ShowPreviousOrderEngEvalDetail")) {
	  // create window first time
	  previousOrderEngEvalApprovalWin = _previousorderengeval_dhxWins.createWindow("ShowPreviousOrderEngEvalDetail", 0, 0, 655, 330);
	  previousOrderEngEvalApprovalWin.setText(title); // well, this string should be passed from the shownewchemappdetailcallback
															  // and internationalize from that screen.
	  previousOrderEngEvalApprovalWin.clearIcon();  // hide icon
	  previousOrderEngEvalApprovalWin.denyResize(); // deny resizing
	  previousOrderEngEvalApprovalWin.denyPark();   // deny parking
	  previousOrderEngEvalApprovalWin.attachObject(attaObj);
	  previousOrderEngEvalApprovalWin.attachEvent("onClose", function(previousOrderEngEvalApprovalWin){
		  previousOrderEngEvalApprovalWin.setModal(false);
		  previousOrderEngEvalApprovalWin.hide();});
	  previousOrderEngEvalApprovalWin.center();
	  previousOrderEngEvalApprovalWin.setModal(true);
  }else{
    // just show
	 previousOrderEngEvalApprovalWin.setModal(true);
	 _previousorderengeval_dhxWins.window("ShowPreviousOrderEngEvalDetail").show();
  }
  closeTransitWin();	
}

function previousOrderSelectRow() {
	rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

	//setting selected row so I can get data from grid later
	parent.selectedPreviousOrderEngEvalRowId = rowId0;

	if (gridCellValue(parent.previousOrderEvalDetailBeanGrid,rowId0,"statusValue") == 'ongoingeval') {
		parent.$("updateSpan").style["display"] = "none";
		if (gridCellValue(parent.previousOrderEvalDetailBeanGrid,rowId0,"requestor") == $v("personnelId")) {
			parent.$("completedSpan").style["display"] = "";
		}else {
			parent.$("completedSpan").style["display"] = "none";
		}
	}else if (gridCellValue(parent.previousOrderEvalDetailBeanGrid,rowId0,"statusValue") == 'completedeval') {
		parent.$("completedSpan").style["display"] = "none";
		if (gridCellValue(parent.previousOrderEvalDetailBeanGrid,rowId0,"requestor") == $v("personnelId")) {
			parent.$("updateSpan").style["display"] = "";
		}else {
			parent.$("updateSpan").style["display"] = "none";
		}
	}else {
		parent.$("completedSpan").style["display"] = "none";
		parent.$("updateSpan").style["display"] = "none";
	}
}

function closeOk() {
	_previousorderengeval_dhxWins.window("ShowPreviousOrderEngEvalDetail").setModal(false);
	_previousorderengeval_dhxWins.window("ShowPreviousOrderEngEvalDetail").hide();
}

//this function will intialize dhtmlXWindow if it's null
function initializeEvalDhxWins() {
	if (_previousorderengeval_dhxWins == null) {
		_previousorderengeval_dhxWins = new dhtmlXWindows();
		_previousorderengeval_dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showEvalCommentDialog()
{
	$("evalCommentText").innerHTML = gridCellValue(previousOrderEvalDetailBeanGrid,selectedPreviousOrderEngEvalRowId,"reason");
	initializeEvalDhxWins();
	//need to set parent Modal to false, so I can set this popup to Modal to true
	_previousorderengeval_dhxWins.window("ShowPreviousOrderEngEvalDetail").setModal(false);
	if (!_previousorderengeval_dhxWins.window("showEvalCommentDialog")) {
		// create window first time
		inputWin = _previousorderengeval_dhxWins.createWindow("showEvalCommentDialog",0,0, 400, 150);
		inputWin.setText($('popupTitle').innerHTML);
		inputWin.clearIcon();  // hide icon
		inputWin.denyResize(); // deny resizing
		inputWin.denyPark();   // deny parking
		inputWin.attachObject(evalCommentAttachObj);
		inputWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		inputWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		inputWin.setPosition(328, 131);
		inputWin.button("close").hide();
		inputWin.button("minmax1").hide();
		inputWin.button("park").hide();
		inputWin.setModal(true);
	}else {
		// just show
		inputWin.setModal(true);
		_previousorderengeval_dhxWins.window("showEvalCommentDialog").show();
	}
}

function evalCommentOk() {
	_previousorderengeval_dhxWins.window("showEvalCommentDialog").setModal(false);
	_previousorderengeval_dhxWins.window("showEvalCommentDialog").hide();
	//close previously evaluated window
	closeOk();
	//refresh window
	completedPreviousOrderEngEval(gridCellValue(previousOrderEvalDetailBeanGrid,selectedPreviousOrderEngEvalRowId,"itemId"),
		 									gridCellValue(previousOrderEvalDetailBeanGrid,selectedPreviousOrderEngEvalRowId,"facilityId"),
		 									gridCellValue(previousOrderEvalDetailBeanGrid,selectedPreviousOrderEngEvalRowId,"application"),
		 									$("evalCommentText").innerHTML,
		                           gridCellValue(previousOrderEvalDetailBeanGrid,selectedPreviousOrderEngEvalRowId,"prNumber"),
		                           gridCellValue(previousOrderEvalDetailBeanGrid,selectedPreviousOrderEngEvalRowId,"lineItem"));
}

function evalCommentCancel() {
	_previousorderengeval_dhxWins.window("showEvalCommentDialog").setModal(false);
	_previousorderengeval_dhxWins.window("showEvalCommentDialog").hide();
	//need to set parent Modal to true
	_previousorderengeval_dhxWins.window("ShowPreviousOrderEngEvalDetail").setModal(true);
}

function completedPreviousOrderEngEval(itemId,facilityId,application,evalComment,prNumber,lineItem) {
	showTransitWin("justPleaseWait");
	callToServer('showpreviousorderengevaldetail.do?callback=showPreviousOrderEngEvalDetailCallback&uAction=completedPreviousOrderEngEval&itemId='+itemId+
					 '&engEvalFacilityId='+encodeURIComponent(facilityId)+'&engEvalWorkArea='+encodeURIComponent(application)+
		          '&evalComment='+encodeURIComponent(evalComment)+'&prNumber='+prNumber+'&lineItem='+lineItem);
}