var _hazmatdetail_dhxWins = null;
var hazmatDetailWin = null;
var hazmatDetailDiv = null;

function viewHazmatInfo(tmpConsolidationNumber) {
	showTransitWin("justPleaseWait");
	callToServer('showhazmatdetail.do?callback=showHazmatCallback&uAction=search&consolidationNumber='+tmpConsolidationNumber);
}

var attaObj = null;
function showHazmatCallback(attachObj,gridConfig,title) {
	  attaObj = attachObj;
	  try{
		  if (hazmatDetailDiv == null) {
		  	hazmatDetailDiv = document.createElement('div');
			hazmatDetailDiv.id = '_showHazmatdetail_' ;
			hazmatDetailDiv.style.display='none';
			hazmatDetailDiv.style.overflow = 'auto';
			hazmatDetailDiv.style.border='0px';
			hazmatDetailDiv.style.width='640px';
			hazmatDetailDiv.style.height='400px';
			document.body.appendChild(hazmatDetailDiv);
		  }
		  attaObj = document.getElementById('_showHazmatdetail_');
	  	  attaObj.innerHTML = attachObj.innerHTML;
	  	  initPopupGridWithGlobal(gridConfig);
	  }catch(ex) {}

  if( _hazmatdetail_dhxWins == null ) {
	_hazmatdetail_dhxWins = new dhtmlXWindows();
	_hazmatdetail_dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  }
  if (!_hazmatdetail_dhxWins.window("ShowHazmatDetail")) {
	  // create window first time
	  hazmatDetailWin = _hazmatdetail_dhxWins.createWindow("ShowHazmatDetail", 0, 0, 640, 400);
	  hazmatDetailWin.setText(title); // well, this string should be passed from the shownewchemappdetailcallback
															  // and internationalize from that screen.
	  hazmatDetailWin.clearIcon();  // hide icon
	  hazmatDetailWin.denyResize(); // deny resizing
	  hazmatDetailWin.denyPark();   // deny parking
	  hazmatDetailWin.attachObject(attaObj);
	  hazmatDetailWin.attachEvent("onClose", function(hazmatDetailWin){hazmatDetailWin.setModal(false);hazmatDetailWin.hide();});
	  hazmatDetailWin.center();
	  hazmatDetailWin.setModal(true);
  }else{
    // just show
	  hazmatDetailWin.setModal(true);
	 _hazmatdetail_dhxWins.window("ShowHazmatDetail").show();
  }
  closeTransitWin();
}
