function mainOnload()
{
  windowCloseOnEsc = true;
  document.getElementById("selectedRow").innerHTML="";
}

function validateForm(){
	if ($v("searchArgument").length < 1) {
		alert(messagesData.missingSearchText);
		return false;
	}
	else {
		return true;
	}
}

function ifMultiShipName(c)
{
	shippingNames = c;
	if(c.length > 1)
	{
		//alert("Check");
		loc = "../hub/iatashippingnameselect.do";  
		openWinGeneric(loc,"iatashippingnameselect","800","450","yes","50","50","no")  ;  
	}
	else
		{
			properShippingName = c[0];
			returnValues();
		}
}

function showTransitWin()
{
	document.getElementById("transitLabel").innerHTML = messagesData.pleasewait;

	//var transitDailogWin = document.getElementById("transitDailogWin");
	//transitDailogWin.style.display="";

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
		//transitWin.setPosition(328, 131);
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

function returnValues()
{
	var c = new iataInfo();
	
	if( window.opener.setIataInfo ) {
	  	window.opener.setIataInfo(c);
	}

	window.close();
}

function selectedRow()
{ 
	callToServer("iatashippingnamemain.do?uAction=shippingNameDupCheck&iataId=" + iataDgId + "&callback=ifMultiShipName");
}

function showshippingpagelegend()
{
	  var showLegendArea = document.getElementById("showLegendArea");
	  showLegendArea.style.display="";

	  var dhxWins = new dhtmlXWindows()
	  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	  if (!dhxWins.window(messagesData.showlegend)) {
	  // create window first time
	  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 150);
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

function iataInfo()
{
	this.iataDgId=iataDgId;
	this.identificationNumber=identificationNumber;
	this.properShippingNameDesc = properShippingNameDesc;
	this.classOrDivision=classOrDivision;
	this.subrisk = subrisk;
	this.hazardLabel = hazardLabel;	
	this.packingGroup=packingGroup;
	this.pCLtdQtyPkgInstr = pCLtdQtyPkgInstr;
	this.pCLtdQtyMaxNetQtyPerPkg = pCLtdQtyMaxNetQtyPerPkg;
	this.pCPkgInstr = pCPkgInstr;
	this.pCMaxNetQtyPerPkg = pCMaxNetQtyPerPkg;
	this.cOnlyPkgInstr = cOnlyPkgInstr;
	this.cOnlyMaxNetQtyPerPkg = cOnlyMaxNetQtyPerPkg;
	this.specialProvision = specialProvision;
	this.ergCode = ergCode;
	this.pCComment = pCComment;
	this.cOnlyComment = cOnlyComment;
	this.technicalNameRequired=technicalNameRequired;
	this.pickable = pickable;
	this.hazmatId = hazmatId;
	this.pCLtdQtyMaxNetValPerPkg = pCLtdQtyMaxNetValPerPkg;
	this.pCLtdQtyMaxNetUntPerPkg = pCLtdQtyMaxNetUntPerPkg;
	this.cOnlyMaxNetValPerPkg = cOnlyMaxNetValPerPkg;
	this.cOnlyMaxNetUnitPerPkg = cOnlyMaxNetUnitPerPkg;
	this.pCLtdQtyG = pCLtdQtyG;
	this.pCG = pCG;
	this.cOnlyG	= cOnlyG;
	this.shippingNameCount = shippingNameCount;
	if(properShippingName == null || properShippingName == "" )
		this.properShippingName = "";
	else
		this.properShippingName = properShippingName;
	this.pCMaxNetUnitPerPkg = pCMaxNetUnitPerPkg;
	this.pCMaxNetValPerPkg = pCMaxNetValPerPkg;
}