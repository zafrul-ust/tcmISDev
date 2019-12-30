windowCloseOnEsc = true;
var resizeGridWithWindow = true;
var children = new Array();

function resultOnLoad()
{
	
	totalLines = document.getElementById("totalLines").value; 

	if (totalLines > 0)
	{
		document.getElementById("poDocumentViewBean").style["display"] = "";

		initializeGrid();  
	}  
	else
	{
		document.getElementById("poDocumentViewBean").style["display"] = "none";   
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
	beangrid = new dhtmlXGridObject('poDocumentViewBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
    // -1 is for disable rowSpan and smart rendering, but sorting still works; false will disable rowSpan and sorting but smart rendering is enabled
    // set submitDefault to true: Send the data to the server
    // singleClickEdit: this is for type:"txt",
	initGridWithConfig(beangrid,config,-1,true);
	if( typeof( jsonMainData ) != 'undefined' ) {
		beangrid.parse(jsonMainData,"json");

	}
	beangrid.attachEvent("onRowSelect",selectRow);
	beangrid.attachEvent("onRightClick",selectRightclick);
}




function selectRow(rowId,cellInd) {

selectedRowId = rowId;
}

function selectRightclick(rowId,cellInd){
	beangrid.selectRowById(rowId,null,false,false);
// The right click event needs to call selectRow function.
	selectRow(rowId,cellInd);
// Show right-click menu


  toggleContextMenu('rightClickMenu');

}



function doShowDocument()
{
  var newDocumentUrl = document.getElementById("newDocumentUrl");
  var loc = newDocumentUrl.value;
  try {
 	children[children.length] = openWinGeneric(loc,"doShowDocumentsaved","800","600","yes","80","80");
  } catch (ex){
 	openWinGeneric(loc,"doShowDocumentsaved","800","600","yes","80","80");
  }

}

function doShowDocuments()
{
	window.opener.document.getElementById("showDocuments").value = 'show';
	window.close();
	//window.opener.location.reload(true);
	window.opener.showPleaseWait();
	window.opener.document.genericForm.submit();
		  
}

function viewDocument()
{
	var documentUrl = cellValue(beangrid.getSelectedRowId(),"documentUrl");
	openWinGeneric(documentUrl,"doShowDocument","800","600","yes","80","80");
	
}


function addNewPoDocument()
{
	
  var radianPo = document.getElementById("radianPo");
  var loc = "/tcmIS/supply/addpodocument.do?radianPo="+radianPo.value;
  try {
 	children[children.length] = openWinGeneric(loc,"showaddNewPoDocument","600","300","no","100","100");
  } catch (ex){
 	openWinGeneric(loc,"showaddNewPoDocument","600","300","no","100","100");
  }
  
}


function checkInput()
{
	if (!validateFileExtension()) return false;
	
	var errorMessage = messagesData.validvalues +"\n\n";
	var errorCount = 0;
	
	var documentName = document.getElementById("documentName");
	if (documentName.value.trim().length == 0)
	{
	 errorMessage = errorMessage + messagesData.document + messagesData.name + ".\n" ;
	 errorCount = 1;
	}
	
	var documentDate = document.getElementById("documentDate");
	if (documentDate.value.trim().length == 0)
	{
	 errorMessage = errorMessage + messagesData.document + messagesData.date + ".\n" ;
	 errorCount = 1;
	}
	
	var theFile = document.getElementById("theFile");
	if (theFile.value.trim().length == 0)
	{
	 errorMessage = errorMessage + messagesData.file + ".\n" ;
	 errorCount = 1;
	}
	
	var documentType = document.getElementById("documentType");
	if (documentType.value.trim().length == 0)
	{
	 errorMessage = errorMessage + messagesData.document + messagesData.type + ".\n" ;
	 errorCount = 1;
	}
	
	if (errorCount >0)
	{
	  alert(errorMessage);
	  return false;
	}
	else
	{
	  return true;
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

function update()
{
	numberOfRows = $v('totalLines');
	  
	  var flag = validateDeleteForm(numberOfRows);
	  if(flag) {
		document.getElementById("updateDocuments").value = 'update';
	    
	    beangrid.parentFormOnSubmit();
	    document.genericForm.submit();
		parent.showPleaseWait();
	  }
	  return flag;
}
