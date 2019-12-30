var beangrid;
var selectedRowId = null;
var windowCloseOnEsc = true;

var resizeGridWithWindow = true;


function resultOnLoad()
{/*
 try{
	if (!showUpdateLinks) //Dont show any update links if the user does not have permissions
	 {
	  parent.document.getElementById("updateResultLink").style["display"] = "none";
	 }
	 else
	 {
	  parent.document.getElementById("updateResultLink").style["display"] = "";
	 }
 }
 catch(ex)
 {}	*/
 parent.window.document.getElementById("selectedRow").innerHTML = "";	
 totalLines = document.getElementById("totalLines").value;
 
 if (totalLines > 0)
 {
  document.getElementById("specsBean").style["display"] = "";
  
  initializeGrid();  
 }  
 else
 {
   document.getElementById("specsBean").style["display"] = "none";   
 }

 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}


function initializeGrid(){
	 beangrid = new dhtmlXGridObject('specsBean');

	 initGridWithConfig(beangrid,config,false,true);
	 if( typeof( jsonMainData ) != 'undefined' ) {
	 beangrid.parse(jsonMainData,"json");

	 }
	 
	 	//beangrid.attachEvent("onRightClick",selectRightclick);
		beangrid.attachEvent("onRowSelect",doOnRowSelected);
	}

function validateForm()
{
	return true;
}


function selectRightclick(rowId,cellInd){
	beangrid.selectRowById(rowId,null,false,false);
	toggleContextMenu("openscratchpad");
}

var specId = '';
var detail = '';
var specLibraryDesc ='';
var specLibrary ='';
var specName ='';
var coc ='';
var coa ='';
var specAmendment ='';
var specVersion ='';
var certReference ='';
function doOnRowSelected(rowId,cellInd) {
	 var columnId = beangrid.getColumnId(cellInd);  
	 selectedRowId = rowId;
	 specId = beangrid.cellById(rowId, beangrid.getColIndexById("specId")).getValue();
	 specName = beangrid.cellById(rowId, beangrid.getColIndexById("specName")).getValue();
	 detail = beangrid.cellById(rowId, beangrid.getColIndexById("detail")).getValue();
	 specVersion = beangrid.cellById(rowId, beangrid.getColIndexById("specVersion")).getValue();
	 specAmendment = beangrid.cellById(rowId, beangrid.getColIndexById("specAmendment")).getValue();
	 //coc = beangrid.cellById(rowId, beangrid.getColIndexById("coc")).getValue();
	 //coa = beangrid.cellById(rowId, beangrid.getColIndexById("coa")).getValue();
	 specLibraryDesc = beangrid.cellById(rowId, beangrid.getColIndexById("specLibraryDesc")).getValue();
	 specLibrary = beangrid.cellById(rowId, beangrid.getColIndexById("specLibrary")).getValue();
/*	 
	 specId = specName;
	 
	 if(specVersion.length > 0)
	 	 specId += "_"+specVersion;
	 	 
	 if(specAmendment.length > 0)
	 	 specId += "_"+specAmendment;
*/	 	 
	 var selectedSpec = parent.window.document.getElementById("selectedRow");
	 if (showUpdateLinks)
	 { 
		 selectedSpec.innerHTML = " | <a href=\"#\" onclick=call('returnSpec'); return false;>"+messagesData.returnspec+' '+specId+"</a>";
	 }	
	 
}

function returnSpec() {
  doOnRowSelected(selectedRowId,0);
  
  try {
	if(fromReceiptSpec)
	{   
		parent.opener.addNewReceiptSpec(specId, specName, detail, "","","","","","","","","","","","","","",specLibrary, specLibraryDesc,"Y","Y",specVersion, specAmendment,'');
		top.close();	
	}
	else
	{
		callToServer("addspecsmain.do?uAction=getNewSpecId&specLibrary="+specLibrary+"&specName="+specName+"&specVersion="+specVersion+"&specAdmentment="+specAmendment); 
	}
  } catch(ex) {}
}

function returnToSpecList(newSpecId, tcmISError) {//alert("newSpecId"+newSpecId);
	if (newSpecId != null && newSpecId.length != 0) {
		specId = newSpecId;
		parent.opener.addNewRow(specId, specName, detail, "", "", "", "", "", "", "", "", "", "", "", "", "", "", specLibraryDesc, specLibrary, "", "", "", specVersion, specAmendment);

		top.close();  
	} else {
		alert(tcmISError);
	}
} 
