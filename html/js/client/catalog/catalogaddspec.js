var dhxWins = null;
var children = new Array();
var windowCloseOnEsc = true;

function $(a) {
	return document.getElementById(a);
}

function editOnLoad(action) {
	closeTransitWin();
	if (action == 'submitSpec' && !showErrorMessage) {
		opener.reloadSpec();
		window.close();
	}else {
		if (showErrorMessage) {
			showMessageDialog();
		}
		if ($v("specId") == 'Std Mfg Cert') {
			$("specSpan").style["display"] = "none";
			$("smcSpan").style["display"] = "";
		}else {
			$("specSpan").style["display"] = "";
			$("smcSpan").style["display"] = "none";
		}
	}
}

function closeThisWindow() {
	try {
		opener.closeTransitWin();
	}catch(e){}
}

function checkClose(action) {
	if( action != 'submitSpec' ) {
		closeAllchildren();
		closeThisWindow();
	}
}

function cancel() {
	window.close();
}

function submitUpdate() {
  if (auditData()) {
      if ($v("calledFrom") == 'catalogAddSpecQc') {
        opener.returnAddNewSpec($v("specName"),$v("specTitle"),$v("specVersion"),$v("specAmendment"),$v("coc"),$v("coa"));
        window.close();
      }else {
        showTransitWin();
	    $("uAction").value = 'submitSpec';
	    document.genericForm.submit();
      }
  }else {
	  return false;
  }
}

function auditData() {
	var result = true;

	if ($v("specId") == 'Std Mfg Cert') {
		if ($("smcCoc").checked) {
			$("coc").checked = "checked";
		}else {
			$("coc").checked = "";
		}
		if ($("smcCoa").checked) {
			$("coa").checked = "checked";
		}else {
			$("coa").checked = "";
		}
	}else {
		var missingFields = "";
		if (isEmptyV("specName")) {
				missingFields += "\t"+messagesData.spec+"\n";
		}
		if (isEmptyV("specTitle")) {
				missingFields += "\t"+messagesData.title+"\n";
		}

		if (missingFields.length > 0) {
			alert(messagesData.validvalues+"\n"+missingFields);
			result = false;
		}
	}

	return result;
}

//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function regExcape(str) {
// if more special cases, need more lines.
return str.replace(new RegExp("[([]","g"),"[$&]");
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

function showMessageDialog()
{
	var inputDailogWin = document.getElementById("messageDailogWin");
	inputDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("showMessageDialog")) {
		// create window first time
		inputWin = dhxWins.createWindow("showMessageDialog",0,0, 450, 150);
		inputWin.setText(messagesData.errors);
		inputWin.clearIcon();  // hide icon
		inputWin.denyResize(); // deny resizing
		inputWin.denyPark();   // deny parking
		inputWin.attachObject("messageDailogWin");
		inputWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		inputWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		inputWin.setPosition(328, 131);
		inputWin.button("close").hide();
		inputWin.button("minmax1").hide();
		inputWin.button("park").hide();
		inputWin.setModal(true);
	}
	else
	{
		// just show
		inputWin.setModal(true);
		dhxWins.window("showMessageDialog").show();
	}
}

function closeMessageWin() {
  dhxWins.window("showMessageDialog").setModal(false);
  dhxWins.window("showMessageDialog").hide();
}

function closeAllchildren() 
{
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
//	if (document.getElementById("uAction").value != 'new') {
		try {
			for(var n=0;n<children.length;n++) {
				try {
				children[n].closeAllchildren(); 
				} catch(ex){}
			children[n].close();
			}
		} catch(ex){}
		children = new Array(); 
}

