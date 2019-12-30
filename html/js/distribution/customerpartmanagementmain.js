var dhxWins = null;
var children = new Array();

function lookupCustomer() {
  loc = "../distribution/customerlookupsearchmain.do?popup=Y&displayElementId=customerName&valueElementId=customerId";
  children[children.length] = openWinGeneric(loc,"customerlookup","800","500","yes","50","50","20","20","no");
}

function clearCustomer() {
    $("customerName").value = "";
    $("customerId").value = "";  
}

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = checkInput();
  var now = new Date();
  $("uAction").value = 'search';
  $("startSearchTime").value = now.getTime();

  if(isValidSearchForm) {
   document.genericForm.target='resultFrame';
	$("lastSearchCustomerId").value = $v("customerId");
	showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function validateSearchFormXSL()
{
  if( checkInput() ) {
      $("uAction").value = "createExcel";
		document.genericForm.target='_CustomerPartManagementExcel';
	   openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_CustomerPartManagementExcel','650','600','yes');
		setTimeout("document.genericForm.submit()",300);
	}
}

function checkInput() {
	var result = true;
	var errorMessage = "";
	if (isEmptyV("customerId")) {
		errorMessage +=  messagesData.customer+ "\n";
	}
	if (isEmptyV("searchText") && !$("activeOnly").checked) {
		errorMessage +=  messagesData.search+ "\n";
	}
	if (!isEmptyV("searchText")) {
		if ($v("searchBy") == 'item') {
			if (!validSearchTextForItem($v("searchText").trim())) {
				alert(messagesData.itemInteger);
				result = false;
			}
		}
	}

	if (errorMessage.length >0) {
    	alert(messagesData.validvalues+"\n"+errorMessage);
   	result = false;
 	}
 	return result;
}

function validSearchTextForItem(searchText) {
	var result = true;
	if(searchText.indexOf(" or ") > 0) {
		var tmpArray = searchText.split(" or ");
		for (var i = 0; i < tmpArray.length; i++) {
		  if (!validSearchTextForItem(tmpArray[i].trim())) {
			  result = false;
			  break;
		  }
		}
	}else if(searchText.indexOf(" and ") > 0) {
		var tmpArray = searchText.split(" and ");
		for (var i = 0; i < tmpArray.length; i++) {
		  if (!validSearchTextForItem(tmpArray[i].trim())) {
			  result = false;
			  break;
		  }
		}
	}else if(searchText.indexOf(" but not ") > 0) {
		var tmpArray = searchText.split(" but not ");
		for (var i = 0; i < tmpArray.length; i++) {
		  if (!validSearchTextForItem(tmpArray[i].trim())) {
			  result = false;
			  break;
		  }
		}
	}else {
		if(!isInteger(searchText, true)) {
			result = false;
		}
	}

	return result;
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
//	}
}

function showLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows()
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 100);
  legendWin.setText(messagesData.showlegend);
  legendWin.clearIcon();  // hide icon
  legendWin.denyResize(); // deny resizing
  legendWin.denyPark();   // deny parking
  legendWin.attachObject("showLegendArea");
  legendWin.attachEvent("onClose", function(legendWin){legendWin.hide();});
  legendWin.center();
  }
  else
  {
    // just show
    dhxWins.window("legendwin").show();
  }
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
		dhxWins.window("transitDailogWin").show();
	}
}


