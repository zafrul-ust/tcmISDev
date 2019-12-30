windowCloseOnEsc = true;

function clearAll()
{
	$("searchPartNumberMode").selectedIndex=1;
	$("partNumber").value = "";
	$("searchCustomerPartNumberMode").selectedIndex=1;
	$("customerPartNumber").value = "";
	$("searchPartDescMode").selectedIndex=1;
	$("partDesc").value = "";
	$("searchTextMode").selectedIndex=1;
	$("text").value = "";
	$("searchSpecificationMode").selectedIndex=1;
	$("specification").value = "";
	$("searchAlternateMode").selectedIndex=1;
	$("alternate").value = "";
	$("resultGridDiv").style.display = "none";
}

function submitSearchForm()
{
	var searchPartNumberMode = document.getElementById("searchPartNumberMode").value;
	var partNumber = document.getElementById("partNumber").value;
	var searchCustomerPartNumberMode = document.getElementById("searchCustomerPartNumberMode").value;
	var customerPartNumber = document.getElementById("customerPartNumber").value;
	var searchPartDescMode = document.getElementById("searchPartDescMode").value;
	var partDesc = document.getElementById("partDesc").value;
	var searchTextMode = document.getElementById("searchTextMode").value;
	var text = document.getElementById("text").value;
	var searchSpecificationMode = document.getElementById("searchSpecificationMode").value;
	var specification = document.getElementById("specification").value;
	var searchAlternateMode = document.getElementById("searchAlternateMode").value;
	var alternate = document.getElementById("alternate").value;
	
	window.opener.saveItemSearch(searchPartNumberMode, partNumber, searchCustomerPartNumberMode, customerPartNumber, 
				searchPartDescMode, partDesc, searchTextMode, text, searchSpecificationMode, specification, searchAlternateMode, alternate);
	
	  var now = new Date();
	  document.getElementById("startSearchTime").value = now.getTime();
	  
	  document.getElementById("selectedPart").innerHTML = "";
	
	  if(validateSearchForm()) { 
	   $('uAction').value = 'search';
	   document.genericForm.target='resultFrame';
	   showPleaseWait();
	   return true;
	  }
	  else
	  {
	    return false;
	  }
}

function validateSearchForm()
{
 return true;
}

function autoSubmit(){
	var partNumber = document.getElementById("partNumber").value;
	var customerPartNumber = document.getElementById("customerPartNumber").value;
	var partDesc = document.getElementById("partDesc").value;
	var text = document.getElementById("text").value;
	var specification = document.getElementById("specification").value;
	var alternate = document.getElementById("alternate").value;
	
	if(partNumber.length > 0 || customerPartNumber.length > 0 || partDesc.length > 0 || text.length > 0 || specification.length > 0 || alternate.length > 0) 
	{ 
		if( submitSearchForm() )
			document.genericForm.submit();
	}
}

var selecteddescription = "";
var selectedItemId = "";

function selectedPartNumberCall()
{ 	
  
  try {
	  if( window.opener.setItem ) {
	    window.opener.setItem(selectedItemId,selecteddescription);
	    window.close();
	  }
  } catch(exx) {}
 
//  window.close();
}

function validateSearchFormXSL()
{
  if( validateSearchForm() ) {
      document.getElementById("uAction").value = "createExcel"; 
		document.genericForm.target='_ContactLookUpExcel';
	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_ContactLookUpExcel','650','600','yes');
		setTimeout("document.genericForm.submit()",300);
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

