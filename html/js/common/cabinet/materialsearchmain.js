function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = true; //validateSearchCriteriaInput();
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();

  if(isValidSearchForm) {
   $("userAction").value = 'search';
   document.genericForm.target='resultFrame';
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function createXSL(popname){
		  var flag = validateForm();
		  if(flag) {
			$('userAction').value = 'createExcel';
			if( popname == null ) popname = '_ExcelReport' + window.name;
		    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',popname,'650','600','yes');
		    document.genericForm.target= popname;
		    var a = window.setTimeout("document.genericForm.submit();",500);
		  }
}

var dhxWins = null;
		
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

function showTransitWin(message) {
	if (message != null && message.length > 0) {
		$("transitLabel").innerHTML = message;
	}else {
		$("transitLabel").innerHTML = messagesData.pleasewait;
	}

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