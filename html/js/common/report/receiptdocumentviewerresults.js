var beangrid;
var selectedRowId = null;

var resizeGridWithWindow = true;

function resultOnLoad()
{
/*try{
 if (!showUpdateLinks) Dont show any update links if the user does not have permissions
 {
  document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  document.getElementById("updateResultLink").style["display"] = "";
 }
 }
 catch(ex)
 {}*/  
	if( (typeof( "receiptDocumentBean" ) != 'undefined') && (document.getElementById("receiptDocumentBean")!=null))
	{	
		 totalLines = document.getElementById("totalLines").value;
		 
		 if (totalLines > 0)
		 {
		  document.getElementById("receiptDocumentBean").style["display"] = "";
		  initializeGrid();  
		 }  
		 else
		 {
		   document.getElementById("receiptDocumentBean").style["display"] = "none";   
		 }
	
		 displayGridSearchDuration();
	 
	 	/*It is important to call this after all the divs have been turned on or off.*/
		setResultFrameSize();
	
	}
}


function initializeGrid(){
 beangrid = new dhtmlXGridObject('receiptDocumentBean');

 initGridWithConfig(beangrid,config,false,true);
 if( typeof( jsonMainData ) != 'undefined' ) 
 {
 beangrid.parse(jsonMainData,"json");

 }
 beangrid.attachEvent("onRowSelect",selectRow);
 beangrid.attachEvent("onRightClick",selectRightclick);
}

function selectRow(rowId,cellInd)
{
	selectedRowId = rowId;
}

function selectRightclick(rowId,cellInd)
{
  beangrid.selectRowById(rowId,null,false,false);	
  selectRow(rowId,cellInd);

  var documentUrl = beangrid.cellById(beangrid.getSelectedRowId(), beangrid.getColIndexById("hdocumentUrl")).getValue();
  var quantityIssued = beangrid.cellById(beangrid.getSelectedRowId(), beangrid.getColIndexById("quantityIssued")).getValue();
  if(documentUrl!='' && quantityIssued != null && quantityIssued > 0*1)
   	toggleContextMenu("allMenu");
  else if(quantityIssued != null && quantityIssued > 0*1)
  	toggleContextMenu("moveMaterialMenu");
  else if(documentUrl != '')
  	toggleContextMenu("showDocMenu");
  else
  	toggleContextMenu('contextMenu');
}

function showReceiptDocument(rowId,cellInd)
{  
   var documentUrl = beangrid.cellById(beangrid.getSelectedRowId(), beangrid.getColIndexById("hdocumentUrl")).getValue();
   if(documentUrl!='')
    	openWinGeneric(documentUrl,"RecevingDocuments","800","450","yes","50","50","no"); 
  
}

function moveToAnotherWorkArea()
{
	var facilityId = beangrid.cellById(beangrid.getSelectedRowId(), beangrid.getColIndexById("facilityId")).getValue();
	if(altAccountSysId.length > 1) {
		// Show the dialog box
		parent.showAccountInputDialog();
	}
	else {
		$("accountSysId").value = altAccountSysId[0].id;
		popMovementofMaterial();
	}
}  

function popMovementofMaterial()
{
	parent.showTransitWin();
	var applicationDesc = beangrid.cellById(beangrid.getSelectedRowId(), beangrid.getColIndexById("applicationDesc")).getValue();
	var application = beangrid.cellById(beangrid.getSelectedRowId(), beangrid.getColIndexById("application")).getValue();
	var facPartNo = beangrid.cellById(beangrid.getSelectedRowId(), beangrid.getColIndexById("facPartNo")).getValue();
	var itemId = beangrid.cellById(beangrid.getSelectedRowId(), beangrid.getColIndexById("itemId")).getValue();
	var receiptId = beangrid.cellById(beangrid.getSelectedRowId(), beangrid.getColIndexById("receiptId")).getValue();
	var quantityIssued = beangrid.cellById(beangrid.getSelectedRowId(), beangrid.getColIndexById("quantityIssued")).getValue();
	var prNumber = beangrid.cellById(beangrid.getSelectedRowId(), beangrid.getColIndexById("prNumber")).getValue();
	var lineItem = beangrid.cellById(beangrid.getSelectedRowId(), beangrid.getColIndexById("lineItem")).getValue();
	var loc = "movementofmaterial.do?uAction=new&accountSysId="+$v("accountSysId")+"&facilityId="+$v("facilityId")+
			"&fromPrNumber="+prNumber+"&fromLineItem="+lineItem+
			"&facPartNo="+facPartNo+"&itemId="+itemId+"&receiptId="+receiptId+"&deliveredQty="+quantityIssued+
			"&fromApplication="+application+"&applicationDesc="+encodeURIComponent(applicationDesc);
	openWinGeneric(loc,"movementOfMaterial","825","340","yes","50","50","no"); 
}

