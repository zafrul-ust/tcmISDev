function facilityChanged() {
  var selectedFacility = $("facilityId").value;
  var idArray = altApplication[selectedFacility];
  var nameArray = altApplicationDesc[selectedFacility];
  var selectI = 0;
  var inserted = 0 ;
  
  var fromApplicationId = $("fromApplicationId");
  for (var i = fromApplicationId.length; i > 0; i--) {
    fromApplicationId[i] = null;
  }

  if( nameArray != null ) {
	  var startingIndex = 0;
	  if (nameArray.length == 0 || nameArray.length > 1) {
	  	 setOption(0,messagesData.all,"", "fromApplicationId");
		 startingIndex = 1;
	  }
	  for (var i=0; i < nameArray.length; i++) {
	    	setOption(i+startingIndex,nameArray[i],idArray[i], "fromApplicationId");
	  }
	  //if only one workarea
	  if (nameArray.length == 1) {
		  $("workAreaApprovedOnly").checked = "checked";
	  }
  }else {
    setOption(0,messagesData.all,"", "fromApplicationId");
  }
  
  var toApplicationId = $("toApplicationId");
  for (var i = toApplicationId.length; i > 0; i--) {
    toApplicationId[i] = null;
  }

  if( nameArray != null ) {
	  var startingIndex = 0;
	  if (nameArray.length == 0 || nameArray.length > 1) {
	  	 setOption(0,messagesData.all,"", "toApplicationId");
		 startingIndex = 1;
	  }
	  for (var i=0; i < nameArray.length; i++) {
	    	setOption(i+startingIndex,nameArray[i],idArray[i], "toApplicationId");
	  }
	  //if only one workarea
	  if (nameArray.length == 1) {
		  $("workAreaApprovedOnly").checked = "checked";
	  }
  }else {
    setOption(0,messagesData.all,"", "toApplicationId");
  }
  
} //end of method

function validateForm()
{
    /*
    var searchText = document.getElementById("searchText");
	  if ($v("transferredFromDate").length == 0 || $v("transferredToDate").length == 0) 
	  {
	       alert(messagesData.validvalues+"\n\n"+messagesData.transferreddate);
	       return false;
	  }
    */
    return true;
}


function submitForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  if(validateForm()) { 
   $('uAction').value = 'search';
   document.genericForm.target='resultFrame';
   showPleaseWait();
   document.genericForm.submit();
  }
}

function generateExcel() 
{
	var flag = validateForm();
	if(flag) 
	{	
	 $('uAction').value = 'createExcel';
	 openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',"_MaterialTransferHistoryExcel","650","600","yes");             
	 document.genericForm.target='_MaterialTransferHistoryExcel';
	 var a = window.setTimeout("document.genericForm.submit();",500);
	}	
}

var dhxWins = null;
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
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

function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
}
