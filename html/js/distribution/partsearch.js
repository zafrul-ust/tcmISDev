windowCloseOnEsc = true;

var beangrid;
var resizeGridWithWindow = true;
var selectedRowId = null;
var checked = 0;
//var tmpArray = new Array();

//var indexArray = new Array();

function resultOnLoad()
{
 totalLines = document.getElementById("totalLines").value;
 
 if (totalLines > 0)
 {
  document.getElementById("partViewBean").style["display"] = "";
  //var addAndContinue = parent.document.getElementById("addAndContinue");

  if((parent.$v('fromCustomerReturn')=="Yes"))
  {
  	parent.document.getElementById("selectedPart").style["display"] = "";
  }
  else
  {
  	parent.document.getElementById("addAndContinue").style["display"] = "";
  	parent.document.getElementById("addAndClose").style["display"] = "";
  	parent.document.getElementById("searchGlobalItemSpan").style["display"] = "";
  }
  initializeGrid();  
 }  
 else
 {
	document.getElementById("partViewBean").style["display"] = "none";
 }

// for(var i=0;i<beangrid.getRowsNum();i++)
//      indexArray[i]=false;
      
 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();

  if (totalLines == 0) {
		parent.document.getElementById('mainUpdateLinks').style["display"] = "";
	try{
  		parent.document.getElementById("searchGlobalItemSpan").style["display"] = "";
  	} catch(ex) {}
  }
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('partViewBean');

	initGridWithConfig(beangrid,config,false,false);
	if( typeof( jsonMainData ) != 'undefined' ) {		
		beangrid.parse(jsonMainData,"json");
	}	
	beangrid.attachEvent("onRowSelect",doOnRowSelected);
	beangrid.attachEvent("onRightClick",selectRightclick);
//	setTimeout('loadRowSpans()',100); 
}

function doOnRowSelected(rowId,cellInd) {
//  if (checkSelectedIndex(indexArray1[cellInd])) {
	selectedRowId = rowId;
	var selectedPart = parent.document.getElementById("selectedPart");

	 parent.selectedpartNumber = cellValue(rowId,'labelSpec');
 
	 if ((null!=parent.$('fromCustomerReturn')) && (parent.$v('fromCustomerReturn')=="Yes"))
	 {
		 selectedPart.style["display"] = "";
		 selectedPart.innerHTML = '<a href="#" onclick="selectedPartNumberCall(); return false;">'+messagesData.selectedpartnumber+' : '+cellValue(rowId,'labelSpec')+'</a> ';
/*		 parent.returnSelectedValue=cellValue(rowId,'labelSpec');
		 parent.returnSelectedDesc=cellValue(rowId,'description');
		 if(cellValue(rowId,'catalogPrice').length>0)
		 	parent.returnSelectedPrice=cellValue(rowId,'catalogPrice'); */
	 }
	 else
	 {  
	 	//selectedPart.innerHTML = '<a href="#" onclick="selectedPartNumberCall(); return false;">'+messagesData.selectedpartnumber+' : '+cellValue(rowId,'labelSpec')+'</a> | ';
	 
		 try {
		 	parent.document.getElementById("newPackagingSpan").style["display"] = "";
		 } catch(ex){}
	 }
	
	parent.selecteddescription = cellValue(rowId,'description'); 
    parent.selectedcustomerPart = cellValue(rowId,'customerPart');
    parent.selectedhazardous = cellValue(rowId,'hazardous');
    parent.selectedspecification = cellValue(rowId,'specList'); 
    parent.selectednumberOfOrders = cellValue(rowId,'hnumberOfOrders');
    parent.selectedlastOrdered = cellValue(rowId,'hlastOrdered');
    parent.selectedlastPrice = cellValue(rowId,'hlastPrice');

    parent.selectedorderQty = cellValue(rowId,'orderQty');
    parent.selectedcatalogPrice = cellValue(rowId,'catalogPrice');
    parent.selectedexpectedCost = cellValue(rowId,'expectedCost');
    parent.selectedmargin = cellValue(rowId,'margin');
    parent.selectedwarehouse = cellValue(rowId,'warehouse');
    parent.selectedregion = cellValue(rowId,'region');
    parent.selectedglobal = cellValue(rowId,'global');
    parent.selectedItemId = cellValue(rowId,'itemId');
    parent.selectedExpectedCost = cellValue(rowId,'expectedCost');
    parent.selectedInventoryGroup = cellValue(rowId,'inventoryGroup');
    parent.selectedSpecListConcat = cellValue(rowId,'specListConcat');
    parent.selectedDetailConcat = cellValue(rowId,'specDetailConcat');
    parent.selectedSpecLibraryList = cellValue(rowId,'specLibraryConcat');
    parent.selectedCocConcat = cellValue(rowId,'specCocConcat');
    parent.selectedCoaConcat = cellValue(rowId,'specCoaConcat');
    parent.selectedUnitOfMeasure = cellValue(rowId,'unitOfMeasure');
    parent.selectedUnitsPerItem = cellValue(rowId,'unitsPerItem');

//    indexArray1[selectedIndex] = true;
//  }
//  else {
//    return false;
//  }

    setResultFrameSize();
}

function selectRightclick(rowId,cellInd){
	selectedRowId = rowId;
	beangrid.selectRowById(rowId,null,false,false);	
	//doOnRowSelected(rowId,cellInd);
//alert('orderType'+pp('orderType'));	
	if(pp('orderType') == 'Blanket%20Order' || checked > 1)
		toggleContextMenu("partSearchMenuWithNoAllocation"); 
	else
		toggleContextMenu("partSearchMenu"); 
}

function showPriceList() {
	var loc = 
		  "/tcmIS/distribution/showpricelist.do?uAction=search" +
		  "&searchField=partName&searchMode=is&searchArgument=" + gg('labelSpec') +
		  "&priceGroupId="+pp('priceGroupId')+
		  "&opsEntityId="+pp('opsEntityId');
   	var winId= 'showPriceList'+$v("prNumber");

   	parent.children[parent.children.length] = openWinGeneric(loc,winId.replace('.','a'),"800","600","yes","50","50","20","20","no");
   	//parent.children[parent.children.length] = openWinGenericDefault(loc,winId.replace('.','a'),"yes","50","50","20","20","no");
}

function showQuickView() {
	var inventoryGroup  =  cellValue(selectedRowId,'inventoryGroup');
	
	for ( var i=0; i < parent.igarr.length; i++) {
	  	if(inventoryGroup == parent.igarr[i].value) {
	    	var inventoryGroupName = parent.igarr[i].text;
	    }      
	}
	
	var loc = "quickquote.do?readonly=Y&opsEntityId="+pp('opsEntityId')+
			  "&inventoryGroupName="+encodeURIComponent(inventoryGroupName)+
			  "&hub="+pp('hub')+
			  "&itemDesc="+gg("description")+"&currencyId="+"USD"+ //$v("homeCurrencyId")+
			  "&inventoryGroup="+encodeURIComponent(inventoryGroup)+
			  "&itemId="+cellValue(selectedRowId,'itemId');
	parent.children[parent.children.length] = openWinGeneric(loc,"quickView","1100","800","yes","80","80","no");
	//parent.children[parent.children.length] = openWinGenericDefault(loc,"quickView","1100","800","yes","80","80","no");
}

function getSpecList( rowId ) {
	selectedRowId = rowId;
	loc = "speclist.do?itemId="+cellValue(rowId,'itemId')+"&catPartNo=&specListConcat="+cellValue(rowId,'specListConcat')+"&specNameConcat="+cellValue(rowId,'specNameConcat')+"&specVersionConcat="+cellValue(rowId,'specVersionConcat')+"&specAmendmentConcat="+cellValue(rowId,'specAmendmentConcat')+"&specDetailConcat="+cellValue(rowId,'specDetailConcat')+"&specLibraryDescConcat="+cellValue(rowId,'specLibraryDescConcat')+"&specLibraryConcat="+cellValue(rowId,'specLibraryConcat')+"&specCocConcat="+cellValue(rowId,'specCocConcat')+"&specCoaConcat="+cellValue(rowId,'specCoaConcat');
	parent.children[parent.children.length] = openWinGeneric(loc,"specList","680","450","yes","50","50","20","20","no");
	//parent.children[parent.children.length] = openWinGenericDefault(loc,"specList","yes","50","50","20","20","no");
}

function getList(specListDisplay, list, specVersionString, specAmendmentString, detail, library, coc, coa, libraryDesc, specNameString) {
	if (selectedRowId != null)
	{
	  beangrid.cells(selectedRowId, beangrid.getColIndexById("specList")).setValue(specListDisplay);
	  beangrid.cells(selectedRowId, beangrid.getColIndexById("specListConcat")).setValue(list);
	  beangrid.cells(selectedRowId, beangrid.getColIndexById("specVersionConcat")).setValue(specVersionString);
	  beangrid.cells(selectedRowId, beangrid.getColIndexById("specAmendmentConcat")).setValue(specAmendmentString);
	  beangrid.cells(selectedRowId, beangrid.getColIndexById("specDetailConcat")).setValue(detail);
	  beangrid.cells(selectedRowId, beangrid.getColIndexById("specLibraryDescConcat")).setValue(libraryDesc);
	  beangrid.cells(selectedRowId, beangrid.getColIndexById("specLibraryConcat")).setValue(library);
	  beangrid.cells(selectedRowId, beangrid.getColIndexById("specCocConcat")).setValue(coc);
	  beangrid.cells(selectedRowId, beangrid.getColIndexById("specCoaConcat")).setValue(coa);
	  beangrid.cells(selectedRowId, beangrid.getColIndexById("specNameConcat")).setValue(specNameString);
/*      beangrid.cells(selectedRowId,5).setValue(specListDisplay);
      beangrid.cells(selectedRowId,23).setValue(list);
      beangrid.cells(selectedRowId,24).setValue(specVersionString);
      beangrid.cells(selectedRowId,25).setValue(specAmendmentString);
      beangrid.cells(selectedRowId,26).setValue(detail);
      beangrid.cells(selectedRowId,27).setValue(libraryDesc);
      beangrid.cells(selectedRowId,28).setValue(library);
      beangrid.cells(selectedRowId,29).setValue(coc);
      beangrid.cells(selectedRowId,30).setValue(coa); */
	}
	
	//doOnRowSelected(selectedRowId,0);
//  	$("specList"+selectedRowId).value = list;
//  	mygrid.selectRowById(selectedRowId,null,false,false);
//  	parent.resizeFrames();
}

function clearSpecList( rowId ) {
      beangrid.cells(selectedRowId, beangrid.getColIndexById("specList")).setValue();
	  beangrid.cells(selectedRowId, beangrid.getColIndexById("specListConcat")).setValue();
	  beangrid.cells(selectedRowId, beangrid.getColIndexById("specVersionConcat")).setValue();
	  beangrid.cells(selectedRowId, beangrid.getColIndexById("specAmendmentConcat")).setValue();
	  beangrid.cells(selectedRowId, beangrid.getColIndexById("specDetailConcat")).setValue();
	  beangrid.cells(selectedRowId, beangrid.getColIndexById("specLibraryDescConcat")).setValue();
	  beangrid.cells(selectedRowId, beangrid.getColIndexById("specLibraryConcat")).setValue();
	  beangrid.cells(selectedRowId, beangrid.getColIndexById("specCocConcat")).setValue();
	  beangrid.cells(selectedRowId, beangrid.getColIndexById("specCoaConcat")).setValue();
//	$("specList"+selectedRowId).value = '';
	parent.resizeFrames();
}

function doF6() {
	if (pp('orderType') == 'Blanket%20Order')
 	{}
 	else
 		allocationPopup('IG');
}

function doF10() {
	if (cellValue(selectedRowId,"itemId").length > 0) 
		showQuickView();
}

function updateChanged(rowId)
{
	if ($("okDoUpdate" + rowId).checked) 
		checked++;
	else
		checked--;
}

function checkAll()
{
	renderAllRows();
  
  	var checkall = $("chkAllOkDoUpdate");
  	var rowsNum = beangrid.getRowsNum();
  	rowsNum = rowsNum*1;

  	var result ;

	if (checkall.checked)
	{
	  result = true;
	  checked = rowsNum;
	}
	else
	{
	  result = false;
	  checked = 0;
	}
	
	for(var i=1; i<rowsNum+1; i++) { 
	  var checkbox = document.getElementById("okDoUpdate" + i);
	  checkbox.checked = result;
	}
}

function clearAll()
{
	var checkall = $("chkAllOkDoUpdate");
	checkall.checked = false;
	
	checkAll();
}

function getParts()
{
	var rowsNum = beangrid.getRowsNum();
  	rowsNum = rowsNum*1;
  	var added = 0;

  	try{
	  	// iterate through all parts displayed
	  	for(var i=0; i<rowsNum; i++) { 
	  		partId = beangrid.getRowId(i);
	  		var checkbox = document.getElementById("okDoUpdate" + partId);
	  		if(checkbox.checked)
	  		{
	  			// if part has been selected, create a part object and add to the parts array
	  		  	var part = new Object();
	  		  	var rowId = partId;
	  		  	part.partNumber = cellValue(rowId,'labelSpec');
				part.description = cellValue(rowId,'description'); 
				part.customerPart = cellValue(rowId,'customerPart');
				part.hazardous = cellValue(rowId,'hazardous');
				part.specification = cellValue(rowId,'specList'); 
				part.numberOfOrders = cellValue(rowId,'hnumberOfOrders');
				part.lastOrdered = cellValue(rowId,'hlastOrdered');
				part.lastPrice = cellValue(rowId,'hlastPrice');			
				part.orderQty = cellValue(rowId,'orderQty');
				part.catalogPrice = cellValue(rowId,'catalogPrice');
				part.expectedCost = cellValue(rowId,'expectedCost');
				part.margin = cellValue(rowId,'margin');
				part.warehouse = cellValue(rowId,'warehouse');
				part.region = cellValue(rowId,'region');
				part.global = cellValue(rowId,'global');
				part.itemId = cellValue(rowId,'itemId');
				part.expectedCost = cellValue(rowId,'expectedCost');
				part.inventoryGroup = cellValue(rowId,'inventoryGroup');
				part.specListConcat = cellValue(rowId,'specListConcat');
				part.detailConcat = cellValue(rowId,'specDetailConcat');
				part.specLibraryList = cellValue(rowId,'specLibraryConcat');
				part.cocConcat = cellValue(rowId,'specCocConcat');
				part.coaConcat = cellValue(rowId,'specCoaConcat');
				part.unitOfMeasure = cellValue(rowId,'unitOfMeasure');
				part.unitsPerItem = cellValue(rowId,'unitsPerItem');
	
				parent.parts.push(part);
				added ++;
	  		}
	  		if(added >= checked)
	  			return;
	  	}
  	}
  	catch(ex){}
}
