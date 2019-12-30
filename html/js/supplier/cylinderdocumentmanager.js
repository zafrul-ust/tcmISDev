windowCloseOnEsc = true;
var resizeGridWithWindow = true;
var children = new Array();

function resultOnLoad()
{
	totalLines = document.getElementById("totalLines").value; 
	if (totalLines > 0){
		document.getElementById("cylinderDocumentBean").style["display"] = "";
		initializeGrid();
	}else{
		document.getElementById("cylinderDocumentBean").style["display"] = "none";
	}
	
		/*This dislpays our standard footer message*/
	displayNoSearchSecDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultSize();
	document.getElementById("mainUpdateLinks").style["display"] = "";
   try{
    parent.resetTimer();
   }
   catch (ex)
   {
   }
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('cylinderDocumentBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
    // -1 is for disable rowSpan and smart rendering, but sorting still works; false will disable rowSpan and sorting but smart rendering is enabled
    // set submitDefault to true: Send the data to the server
    // singleClickEdit: this is for type:"txt",
	initGridWithConfig(beangrid,config,false,true);
	if( typeof( jsonMainData ) != 'undefined' ) {
		beangrid.parse(jsonMainData,"json");

	}
	beangrid.attachEvent("onRightClick",selectRightclick);
}

function selectRightclick(rowId,cellInd){
	toggleContextMenu('rightClickMenu');
}

function includeDeletedDocuments() {
	parent.showPleaseWait();
	document.genericForm.submit();
}

function viewDocument() {
	var documentUrl = cellValue(beangrid.getSelectedRowId(),"documentUrl");
	openWinGeneric(documentUrl,"showDocument","800","600","yes","80","80");
}

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

function cancel()
{
    window.close();
}

function validateDeleteForm(numberOfRows) {
	  var flag = true;
	  var selected = false;
	  if(numberOfRows != null) {
	    for(var i=1; i<=numberOfRows; i++) {
		  var checked = false;
		  try {
			  checked = cellValue(i,'ok');
		  } catch(ex){}
		  if( !checked ) continue;
		  selected = true;
	    }
	  }
		  if( !selected ) {
			  alert(messagesData.norowselected);
			  return false;
		  }	  
		  
		  return flag;
	}

function update() {
	numberOfRows = $v('totalLines');
    var flag = validateDeleteForm(numberOfRows);
    if(flag) {
        $("userAction").value = 'deleteDocument';
        beangrid.parentFormOnSubmit();
        document.genericForm.submit();
        parent.showPleaseWait();
    }
    return flag;
}

function addNewDocument() {
    children[children.length] = openWinGeneric('cylinderdocumentmanager.do?userAction=showAddCylinderDocument'+
                                                '&documentSource=CylinderTracking'+
                                                '&serialNumber='+encodeURIComponent($v("serialNumber"))+
                                                '&manufacturerName='+encodeURIComponent($v("manufacturerName"))+
                                                '&supplier='+encodeURIComponent($v("supplier"))+
                                                '&cylinderTrackingId='+encodeURIComponent($v("cylinderTrackingId"))
                                                ,'showAddCylinderDocumentManager', "600","200","no","100","100");
}
