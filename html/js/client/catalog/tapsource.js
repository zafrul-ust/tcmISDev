var children = new Array();
var dhxWins = null;
var selectedRowId = '';
var currentReceiptId = '';
var intoBin = '';
function closeAllchildren()
{
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

function myOnLoad() {
	var totalLines = $("totalLines").value;
	if (totalLines > 0) {
		document.getElementById('headerDataSpan').style["display"]="";
	}
}

function selectRow()
{
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

   oldRowId = rowId0;

   if( ee != null ) {
   		if( ee.button != null && ee.button == 2 ) rightClick = true;
   		else if( ee.which == 3  ) rightClick = true;
   }

   selectedRowId = rowId0;

	currentReceiptId = cellValue(rowId0,"receiptId");
	intoBin = cellValue(rowId0,"newBin");
	$("updateResultLink").innerHTML = '<a href="#" onclick="selectData()"; return false;">'+messagesData.tapSource+' : '+ currentReceiptId +'</a>';
} //end of method

function selectData() {
	try {
		closeAllchildren();
		opener.receiptTapped(currentReceiptId,intoBin,$("itemId").value,$("inventoryGroup").value);
  	} catch(exx) {}
}

//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showMessageDialog()
{
	var inputDailogWin = document.getElementById("messageDailogWin");
	inputDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("showMessageDialog")) {
		// create window first time
		inputWin = dhxWins.createWindow("showMessageDialog",0,0, 250, 80);
		inputWin.setText(messagesData.newBin);
		inputWin.clearIcon();  // hide icon
		inputWin.denyResize(); // deny resizing
		inputWin.denyPark();   // deny parking
		inputWin.attachObject("messageDailogWin");
		inputWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		inputWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		//inputWin.setPosition(328, 131);
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
  addNewBin();
}

function cancelMessageWin() {
  dhxWins.window("showMessageDialog").setModal(false);
  dhxWins.window("showMessageDialog").hide();
}

function addNewBin() {
	setOption($("newBin"+selectedRowId).options.length,$("messageText").value,$("messageText").value, "newBin"+selectedRowId);
	$("newBin"+selectedRowId).selectedIndex = $("newBin"+selectedRowId).options.length -1;
}