var children = new Array();
windowCloseOnEsc = true;

var selectedRowId = null;
var dhxFreezeWins = null;

function resultOnLoad(gridConfig)
{
 try{
 if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
 }
 catch(ex)
 {}

 initGridWithGlobal(gridConfig);

 /*This dislpays our standard footer message*/
 displayGridSearchDuration();

 /*It is important to call this after all the divs have been turned on or off.*/
 setResultSize();

}

function selectRow(rowId,cellInd){
   selectedRowId = rowId;
}

function selectRightclick(rowId,cellInd){
   selectRow(rowId,cellInd);
 
   if(cellInd >= 10) {
   		vitems = new Array();
		if (cellValue(rowId,"showDisplayCerts") == "Y") {
			vitems[vitems.length] = "text="+messagesData.displaycerts+";url=javascript:showReceiptDocument();";
		}
		
		if (cellValue(rowId,"showDisplayMSDS") == "Y") {
			$("selectedRefNumber").value = cellValue(rowId,"refNumber");
			$("selectedLineItem").value = cellValue(rowId,"lineItem");    
			vitems[vitems.length] = "text="+messagesData.displaymsds+";url=javascript:showMSDS();";
			vitems[vitems.length] = "text="+messagesData.requestmsds+";offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;";
		}
		else {
			vitems[vitems.length] = "text="+messagesData.displaymsds+";offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;";
			vitems[vitems.length] = "text="+messagesData.requestmsds+";url=javascript:requestMSDS();";
		}

		if (cellValue(rowId,"shipmentId") != null && cellValue(rowId,"shipmentId").length > 0) {
			vitems[vitems.length] = "text="+messagesData.displaypackinglist+";url=javascript:showPackList();";
		}
		if (cellValue(rowId,"showDisplayInvoice") == "Y") {
			vitems[vitems.length] = "text="+messagesData.displayinvoice+";url=javascript:showInvoice();";
		}
		if (vitems.length > 0 ) {
			replaceMenu('rightClickMenu',vitems); 
			toggleContextMenu('rightClickMenu');
		}
 	}
 	else {
 		toggleContextMenu('contextMenu');
 	}
}

function replaceMenu(menuname,menus){
	  var i = mm_returnMenuItemCount(menuname);

	  for(;i> 1; i-- )
			mm_deleteItem(menuname,i);

	  for( i = 0; i < menus.length; ){
 		var str = menus[i];

 		i++;
		mm_insertItem(menuname,i,str);
		// delete original first item.
    	if( i == 1 ) mm_deleteItem(menuname,1);
      }
}

function showReceiptDocument() {
	callToServer("mrallocationreport.do?uAction=getCertFileNames&mrNumber="+$v("mrNumber")+"&receiptId="+cellValue(selectedRowId,"receiptDocument"));
}

function getCerts(fileNames) {
	var loc = "viewfile.jsp?type=certs&fileNames="+fileNames;
	children[children.length] = openWinGeneric(loc,"DisplayCerts"+cellValue(selectedRowId,"receiptDocument"),"800","600","yes","50","50","20","20","yes");
}

function getReceipts(receiptId, documentId) {
	var tmpUrl = "";
    if ($v("secureDocViewer") == 'true')
        tmpUrl = "/DocViewer/client/";
	openWinGeneric(tmpUrl + "receiptdocviewer.do?uAction=viewReceiptDoc&documentId="+documentId+"&receiptId="+receiptId+"&companyId="+$v("companyId")
			,'_MrAllocationShowDocument','650','600','yes');
}

function showMSDS() {
	callToServer("mrallocationreport.do?uAction=getMSDSFileNames&type=msds&mrNumber="+$v("mrNumber")+"&refType="+encodeURIComponent(cellValue(selectedRowId,"refType"))+
              "&refNumber="+cellValue(selectedRowId,"refNumber")+"&lineItem="+cellValue(selectedRowId,"lineItem"));
}
	
function getMSDS(fileNames) {
	if(fileNames.length > 0) {
		var loc = "viewfile.jsp?type=msds&fileNames="+fileNames;
		children[children.length] = openWinGeneric(loc,"DisplayMSDS","800","600","yes","50","50","20","20","yes");
	}
}

function requestMSDS() {
	callToServer("mrallocationreport.do?uAction=requestMSDS&itemId="+cellValue(selectedRowId,"itemId")+"&csrEmail="+$v("csrEmail"));
}

function showPackList() {
	var loc = "viewfile.jsp?type=packList&prNumber="+$v("mrNumber")+"&shipmentId="+cellValue(selectedRowId,"shipmentId");
	children[children.length] = openWinGeneric(loc,"DisplayPackList","800","600","yes","50","50","20","20","yes");
}

function showInvoice()
{
	var loc = "viewfile.jsp?type=invoice&prNumber="+$v("mrNumber")+"&shipmentId="+cellValue(selectedRowId,"shipmentId");
    children[children.length] = openWinGeneric(loc,"DisplayInvoice","800","600","yes","50","50","20","20","yes");
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