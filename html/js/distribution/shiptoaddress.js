var children = new Array();
windowCloseOnEsc = true;

var dhxFreezeWins = null;

var selectedLocationType = null;
var selectedLocationShortName = null;
var selectedLocationDesc = null;
var selectedShipToLocationId = null;
var selectedShipToAddressLine1Display = null;
var selectedShipToAddressLine2Display = null;
var selectedShipToAddressLine3Display = null;
var selectedShipToAddressLine4Display = null;
var selectedShipToAddressLine5Display = null;
var selectedFieldSalesRepId = null;
var	selectedFieldSalesRepName = null;
var selectedShiptoNotes = null;

function resultOnLoadWithGrid(gridConfig)
{
 totalLines = document.getElementById("totalLines").value; 
 if (totalLines > 0)
 {
  document.getElementById("selectedAddressSpan").style["display"] = ""; 
  /*this is the new part.*/
  initGridWithGlobal(gridConfig); 
 }
 else
 {
   document.getElementById("selectedAddressSpan").style["display"] = "none";   
 }

 /*This dislpays our standard footer message*/
 displayNoSearchSecDuration();

 /*It is important to call this after all the divs have been turned on or off.*/
 setResultSize();
}

function selectRow(rowId,cellInd){
 
   rowId0 = arguments[0];
   colId0 = arguments[1];

   beanGrid.selectRowById(rowId0);
 
   if(cellValue(rowId,'status') == 'A') {
	   selectedLocationDesc = cellValue(rowId,'locationDesc');
	
	   $("selectedAddressSpan").innerHTML = ' | <a href="#" onclick="selectedAddress(); return false;">' + messagesData.selectedaddress + ' : ' + selectedLocationDesc + '</a>';
	  
	   selectedLocationType = cellValue(rowId,'locationType');
	   selectedLocationShortName = cellValue(rowId,'locationShortName');
	   selectedShipToLocationId = cellValue(rowId,'shipToLocationId');
	   selectedShipToCompanyId = cellValue(rowId,'shipToCompanyId');
	   selectedShipToAddressLine1Display = cellValue(rowId,'shipToAddressLine1Display');
	   selectedShipToAddressLine2Display = cellValue(rowId,'shipToAddressLine2Display');
	   selectedShipToAddressLine3Display = cellValue(rowId,'shipToAddressLine3Display');
	   selectedShipToAddressLine4Display = cellValue(rowId,'shipToAddressLine4Display'); 
	   selectedShipToAddressLine5Display = cellValue(rowId,'shipToAddressLine5Display'); 
	   selectedFieldSalesRepId = cellValue(rowId,'fieldSalesRepId'); 
	   selectedFieldSalesRepName = cellValue(rowId,'fieldSalesRepName'); 
	   selectedShiptoNotes = cellValue(rowId,'shiptoNotes'); 
   }
   else {
   	   $("selectedAddressSpan").innerHTML = '';
   }
}

function selectedAddress()
{ 
  var s = new addressInfo()
  window.opener.addressChanged(s);
  window.close();
}

function addressInfo()
{
	this.locationType = selectedLocationType; 
	this.locationShortName = selectedLocationShortName;
	this.locationDesc = selectedLocationDesc;
	this.shipToLocationId = selectedShipToLocationId;
	this.shipToCompanyId = selectedShipToCompanyId;
	this.shipToAddressLine1Display = selectedShipToAddressLine1Display;
	this.shipToAddressLine2Display = selectedShipToAddressLine2Display;
	this.shipToAddressLine3Display = selectedShipToAddressLine3Display;
	this.shipToAddressLine4Display = selectedShipToAddressLine4Display;
	this.shipToAddressLine5Display = selectedShipToAddressLine5Display;
	this.fieldSalesRepId = selectedFieldSalesRepId;
	this.fieldSalesRepName = selectedFieldSalesRepName;
	this.shiptoNotes = selectedShiptoNotes;
}

function newShipToAddress() 
{
  var customerId = $v("customerId");
  var companyId = $v("companyId");
  var fieldSalesRepId = $v("fieldSalesRepId");
  var fieldSalesRepName = $v("fieldSalesRepName");
  var priceList = $v("priceList");
  
  if(customerId.length == 0) {
    alert("Please get a customer Id before adding a shipto address.");
    return false;
  } else {
    showTransitWin(messagesData.newshiptoaddress);
    loc = "newshiptoaddress.do?fromShipToAddress=Yes&customerId="+customerId+"&billToCompanyId="+companyId+"&fieldSalesRepId="+fieldSalesRepId+"&fieldSalesRepName="+encodeURIComponent(fieldSalesRepName)+"&priceList="+priceList;
  } 
  
  children[children.length] = openWinGeneric(loc,"newShipToAddress","980","550","yes","50","50","no"); 
}

function initializeDhxWins() {
	if (dhxFreezeWins == null) {
		dhxFreezeWins = new dhtmlXWindows();
		dhxFreezeWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}


function showTransitWin(messageType)
{
	var waitingMsg = messagesData.waitingforinputfrom+"...";
	$("transitLabel").innerHTML = waitingMsg.replace(/[{]0[}]/g,messageType);

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.innerHTML = document.getElementById("transitDailogWinBody").innerHTML;
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxFreezeWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxFreezeWins.createWindow("transitDailogWin",0,0, 400, 200);
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
		transitWin.setModal(true);  // freeze the window here
		dhxFreezeWins.window("transitDailogWin").show();
	}
}

function closeTransitWin() {
	if (dhxFreezeWins != null) {
		if (dhxFreezeWins.window("transitDailogWin")) {
			dhxFreezeWins.window("transitDailogWin").setModal(false);
			dhxFreezeWins.window("transitDailogWin").hide();
		}
	}
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