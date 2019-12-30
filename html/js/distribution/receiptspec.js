var beangrid;
/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;
windowCloseOnEsc = true;
addNewRowOK = true;

var children = new Array();
var selectedRowId = null;

function resultOnLoad()
{
	
	try{

		if (!showUpdateLinks) //Dont show any update links if the user does not have permissions/
		{
			document.getElementById("updateResultLink").style["display"] = "none";
		}
		else
		{
			document.getElementById("updateResultLink").style["display"] = "";

		}
	}
	catch(ex)
	{}
	
	totalLines = document.getElementById("totalLines").value; 

	if (totalLines > 0)
	{
		document.getElementById("specReceiptViewBean").style["display"] = "";

		initializeGrid();  
	}  
	else
	{
		document.getElementById("specReceiptViewBean").style["display"] = "none";   
	}

	/*This dislpays our standard footer message*/
	displayNoSearchSecDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultSize();
	document.getElementById("mainUpdateLinks").style["display"] = "";
}

function showLegend()
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


var saveRowClass = null;
function doOnBeforeSelect(rowId,oldRowId) {	
	if (oldRowId != 0) {
		setRowClass(oldRowId, saveRowClass);		
	}
	saveRowClass = getRowClass(rowId);
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		overrideDefaultSelect(rowId,saveRowClass);
	return true;	
}



function initializeGrid(){
	 beangrid = new dhtmlXGridObject('specReceiptViewBean');

	 initGridWithConfig(beangrid,config,false,true);
	 if( typeof( jsonMainData ) != 'undefined' ) {
	 beangrid.parse(jsonMainData,"json");

	 }
	 beangrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	 beangrid.attachEvent("onRowSelect",selectRow);
	 beangrid.attachEvent("onRightClick",selectRightclick);
}


function selectRightclick(rowId,cellInd){
	selectRow( rowId);
	toggleContextMenu('rightClickMenu');
}

function selectRow( rowId,oldRowId) {
	selectedRowId = rowId;
	  // to show menu directly
		 if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
			  setRowClass(rowId,''+saveRowClass+'Selected');
}

function addNewSpec() {
	  loc = "addspecsmain.do?fromReceiptSpec=Yes";	
	  try {
		 	children[children.length] = openWinGeneric(loc,"addNewSpec","700","500","yes","50","50","20","20","no");
	  } catch (ex){
		 	openWinGeneric(loc,"addNewSpec","700","500","yes","50","50","20","20","no");
	  }  
}

function getDetail() {
  loc = "modifyspecdetail.do?uAction=search&fromReceiptSpec=Yes&&specId="+cellValue(selectedRowId,"specId")+"&specName="+encodeURIComponent(cellValue(selectedRowId,"specName"))+"&specLibrary="+encodeURIComponent(cellValue(selectedRowId,"specLibrary"))+"&specLibraryDesc="+encodeURIComponent(cellValue(selectedRowId,"specLibraryDesc"))+"&specAmendment="+cellValue(selectedRowId,"specAmendment")+"&specVersion="+cellValue(selectedRowId,"specVersion");
  var winId= 'modifyspecdetail'; 
  children[children.length] = openWinGeneric(loc,winId,"500","300","yes","50","50","20","20","no");
}

function getSpec() {
  if(cellValue(selectedRowId,"detail").length > 0)
  	callToServer("newspecs.do?uAction=getSpecDetails&specId="+encodeURIComponent(cellValue(selectedRowId,"specId"))+"&specLibrary="+encodeURIComponent(cellValue(selectedRowId,"specLibrary"))+"&specDetail="+encodeURIComponent(cellValue(selectedRowId,"detail")));
  else
    passDefaultValues();  
}

function passDefaultValues(d) {
  if (d == null || typeof(d) == 'undefined')
  	loc = "newspecs.do?uAction=newspecs&fromReceiptSpec=Yes&specName="+encodeURIComponent(cellValue(selectedRowId,"specName"))+"&specLibrary="+encodeURIComponent(cellValue(selectedRowId,"specLibrary"))+"&specLibraryDesc="+encodeURIComponent(cellValue(selectedRowId,"specLibraryDesc"));
  else
    loc = "newspecs.do?uAction=newspecs&fromReceiptSpec=Yes&specName="+encodeURIComponent(cellValue(selectedRowId,"specName"))+"&specLibrary="+encodeURIComponent(cellValue(selectedRowId,"specLibrary"))+"&specLibraryDesc="+encodeURIComponent(cellValue(selectedRowId,"specLibraryDesc"))+
    		"&specDetailType="+encodeURIComponent(d.specDetailType)+"&specDetailClass="+encodeURIComponent(d.specDetailClass)+
    		"&specDetailForm="+encodeURIComponent(d.specDetailForm)+"&specDetailGroup="+encodeURIComponent(d.specDetailGroup)+
    		"&specDetailGrade="+encodeURIComponent(d.specDetailGrade)+"&specDetailStyle="+encodeURIComponent(d.specDetailStyle)+
    		"&specDetailFinish="+encodeURIComponent(d.specDetailFinish)+"&specDetailSize="+encodeURIComponent(d.specDetailSize)+
    		"&specDetailColor="+encodeURIComponent(d.specDetailColor)+"&specDetailOther="+encodeURIComponent(d.specDetailOther);
  
  var winId= 'modifyspec'; 
  children[children.length] = openWinGeneric(loc,winId,"550","300","yes","50","50","20","20","no"); 
}

function submitReceiptSpec() {
	showPleaseWait();	
	$('action').value='submit';
	beangrid.parentFormOnSubmit();
	document.genericForm.target='';
	document.genericForm.submit();
}

function addNewReceiptSpec(	specId,	specName, detail, specDetailType, specDetailClass,
		specDetailForm, specDetailGroup, specDetailGrade, specDetailStyle, specDetailFinish,
		specDetailSize, specDetailColor, specDetailMethod, specDetailCondition,
		specDetailDash, specDetailNote, specDetailOther, specLibrary, specLibraryDesc,  coc, coa, specVersion, specAmendment, certReference, addDetail)
{
	$("specReceiptViewBean").style["display"] = "";

	   if(beangrid == null) {
	     initializeGrid(); 
	   }  
	  
	    var rowid = beangrid.getRowsNum()*1+1;
    
   		if(addDetail == "Y")
   			var ind = beangrid.getRowIndex(selectedRowId)*1 + 1;  
   		else
   			var ind = beangrid.getRowsNum();
	     
	   var thisrow = beangrid.addRow(rowid,"",ind);
	 
	   var cc =0; 
	 
	   beangrid.cells(rowid,cc++).setValue("Y");
	   beangrid.cells(rowid,cc++).setValue(specLibraryDesc);
	   beangrid.cells(rowid,cc++).setValue(specName);
	   beangrid.cells(rowid,cc++).setValue(specVersion);
	   beangrid.cells(rowid,cc++).setValue(specAmendment);
	   var detail = detail;
	   if(!specDetailType == ""){detail = detail + "Ty "+specDetailType};
   	   if(!specDetailClass == ""){detail = detail + " Cl "+specDetailClass};
   	   if(!specDetailForm == ""){detail = detail + " Frm "+specDetailForm};
   	   if(!specDetailGroup == ""){detail = detail + " Grp "+specDetailGroup};
   	   if(!specDetailGrade == ""){detail = detail + " Gr "+specDetailGrade};
   	   if(!specDetailStyle == ""){detail = detail + " Sty "+specDetailStyle};
       if(!specDetailFinish == ""){detail = detail + " Fin "+specDetailFinish};
       if(!specDetailSize == ""){detail = detail + " Sz "+specDetailSize};
       if(!specDetailColor == ""){detail = detail + " Clr "+specDetailColor};
       if(!specDetailMethod == ""){detail = detail + " Md "+specDetailMethod};
       if(!specDetailCondition == ""){detail = detail + " Cd "+specDetailCondition};
      //if(!specDetailDash == ""){detail = detail + " Dash "+specDetailDash};
      if(!specDetailNote == ""){detail = detail + " Note "+specDetailNote};
      if(!specDetailOther == ""){detail = detail + " Other "+specDetailOther};
	   beangrid.cells(rowid,cc++).setValue(detail);  
	   beangrid.cells(rowid,cc++).setValue(coc); 
	   beangrid.cells(rowid,cc++).setValue(coa); 
	   beangrid.cells(rowid,cc++).setValue("");
	   beangrid.cells(rowid,cc++).setValue(specId);
	   beangrid.cells(rowid,cc++).setValue(specLibrary);
	   beangrid.cells(rowid,cc++).setValue("N");
	   beangrid.cells(rowid,cc++).setValue($v('receiptId'));
	  // beangrid.cells(rowid,cc++).setValue(specId);
	   
	   if(coc == "Y") {
		   	$("coc"+rowid).checked = true; 
			hchstatusA["coc"+rowid] = true;
	   }
	   if(coa == "Y") {
		   	$("coa"+rowid).checked = true; 
			hchstatusA["coa"+rowid] = true;
	   }
	   
	   beangrid.selectRow(beangrid.getRowIndex(rowid),null,false,false);
	   
	   try {
   		  $("resultsPageTable").style.display="none";
   	   } catch(ex){}
   	   
   	   setResultSize();
	   document.getElementById("mainUpdateLinks").style["display"] = "";
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
