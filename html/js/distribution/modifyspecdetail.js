var children = new Array();
windowCloseOnEsc = true;

var dhxFreezeWins = null;

var selectedDetail = null;

function resultOnLoadWithGrid(gridConfig)
{
 totalLines = document.getElementById("totalLines").value; 
 if (totalLines > 0)
 {
  document.getElementById("selectedDetailSpan").style["display"] = "";
  document.getElementById("newDetailSpan").style["display"] = "none";
  /*this is the new part.*/
  initGridWithGlobal(gridConfig); 
 }
 else
 {
   document.getElementById("selectedDetailSpan").style["display"] = "none"; 
   document.getElementById("newDetailSpan").style["display"] = "";  
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
    
   selectedDetail = cellValue(rowId,'detail');
	
   $("selectedDetailSpan").innerHTML = ' | <a href="#" onclick="returnDetail(); return false;">' + messagesData.selecteddetail + ' : ' + selectedDetail + '</a>';
}

function returnDetail()
{ 
  if($v("fromReceiptSpec") == "Yes")
	window.opener.addNewReceiptSpec($v("specId"),$v("specName"),selectedDetail,"","","","","","","","","","","","","","",$v("specLibrary"),$v("specLibraryDesc"),"","","",$v("specVersion"),$v("specAmendment"),"Y");
  else
  	window.opener.addNewRow($v("specId"),$v("specName"),selectedDetail,"","","","","","","","","","","","","","",$v("specLibraryDesc"),$v("specLibrary"),"","","",$v("specVersion"),$v("specAmendment"),"Y");
  window.close();
}

function newDetail() {
  showTransitWin(messagesData.newspecdetail);
  if($v("fromReceiptSpec") == "Yes")
  	loc = "newdetail.do?fromReceiptSpec=Yes&specId="+$v("specId")+"&specName="+encodeURIComponent($v("specName"))+"&specLibrary="+encodeURIComponent($v("specLibrary"))+"&specLibraryDesc="+encodeURIComponent($v("specLibraryDesc"))+"&specAmendment="+$v("specAmendment")+"&specVersion="+$v("specVersion");
  else
    loc = "newdetail.do?specId="+$v("specId")+"&specName="+encodeURIComponent($v("specName"))+"&specLibrary="+encodeURIComponent($v("specLibrary"))+"&specLibraryDesc="+encodeURIComponent($v("specLibraryDesc"))+"&specAmendment="+$v("specAmendment")+"&specVersion="+$v("specVersion");
  
  var winId= 'newdetail'; 
  children[children.length] = openWinGeneric(loc,winId,"500","300","yes","50","50","20","20","no");
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