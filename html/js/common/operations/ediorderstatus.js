var beangrid;

var resizeGridWithWindow = true;
var selectedRowId = null;

var multiplePermissions = true;
var permissionColumns = new Array();
permissionColumns={"orderedQty":true,"orderedUom":true,
					"currencyId":true,"unitPriceOnOrder":true,"shiptoPartyId":true};


function resultOnLoad()
{
 try{
	 if (!showUpdateLinks || (creditHoldCount*1 == 0)) //Dont show any update links if the user does not have permissions
	 {
	  parent.document.getElementById("updateResultLink").style["display"] = "none";
	 }
	 else if (creditHoldCount*1 > 0)
	 {
	  parent.document.getElementById("updateResultLink").style["display"] = "";
	 }
 }
 catch(ex)
 {}

 totalLines = document.getElementById("totalLines").value;
 
 if (totalLines > 0)
 {
  document.getElementById("ediOrderErrorViewBean").style["display"] = "";
  
  initializeGrid();  
 }  
 else
 {
   document.getElementById("ediOrderErrorViewBean").style["display"] = "none";   
 }

 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('ediOrderErrorViewBean');

	initGridWithConfig(beangrid,config,-1,true);
	if( typeof( jsonMainData ) != 'undefined' ) {
		beangrid.parse(jsonMainData,"json");
	}
//	beangrid.attachEvent("onBeforeSorting",sortValues);
	beangrid.attachEvent("onRightClick",selectRightclick);
	
//	beangrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
    beangrid.attachEvent("onRowSelect",doOnRowSelected);
	
}

//Function to sort date  and other fields on the grid.
//Save the old selected color here
/*
var saveRowClass = null;
function doOnBeforeSelect(rowId,oldRowId) {
	if (oldRowId != 0) {
		setRowClass(oldRowId, saveRowClass);
	}
	saveRowClass = getRowClass(rowId);
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		overrideDefaultSelect(rowId,saveRowClass);
	return true;
}  */

function doOnRowSelected(rowId,cellInd) {
	ee     = arguments[2];
  
    rightClick = false; 
    popdown();
	externalevent = null;
    if( ee != null ) {
    		if( ee.button != null && ee.button == 2 ) rightClick = true;
    		else if( ee.which == 3  ) rightClick = true;
    		if( rightClick) externalevent = ee;
    }
    
	selectedRowId = rowId; 

/*	
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		setRowClass(rowId,''+saveRowClass+'Selected');  */
}

function selectRightclick(rowId,cellInd,ee){
	beangrid.selectRowById(rowId,null,false,false);
	doOnRowSelected(rowId,cellInd,ee);
	
 vitems = new Array();

//alert("companyId"+$v("companyId")+"  facilityId:"+cellValue(beangrid.getSelectedRowId(),"facilityId")+"  customerPoLineNoTrim:"+cellValue(beangrid.getSelectedRowId(),"customerPoLineNoTrim")+"   transactionRefNum:"+cellValue(beangrid.getSelectedRowId(),"transactionRefNum").length+"personnelId"+$v("personnelId"));
//show cancel order when transaction ref num exist
 if($v("companyId")=="USGOV") {
 	if((cellValue(beangrid.getSelectedRowId(),"facilityId")=="DLA Gases" && cellValue(beangrid.getSelectedRowId(),"customerPoLineNoTrim")=="1" && cellValue(beangrid.getSelectedRowId(),"transactionRefNum").length > 0)
 		&& ($v("personnelId")=='19375' || $v("personnelId")=='86030' || $v("personnelId")=='15583' || $v("personnelId")=='15143' || $v("personnelId")=='86405' || $v("personnelId")=='17654'))
 		vitems[vitems.length ] = "text="+messagesData.cancelorder+";url=javascript:cancelOrder();";
 }
 
//alert("editShipTo"+$v("editShipTo")+"    multiplePart:"+cellValue(beangrid.getSelectedRowId(),"multiplePart"));
//show part list right click when multiple
 if($v("editShipTo")=="true" && cellValue(beangrid.getSelectedRowId(),"multiplePart").length>0)
 	vitems[vitems.length ] = "text="+messagesData.partlist+";url=javascript:showPartList();";
 	
//alert("facilityId"+cellValue(beangrid.getSelectedRowId(),"facilityId")+"    customerPoLineNoTrim:"+cellValue(beangrid.getSelectedRowId(),"customerPoLineNoTrim"));
// USGOV VMI right click
 if($v("companyId")=="USGOV") { 
	 if(cellValue(beangrid.getSelectedRowId(),"facilityId")=="DLA Gases" && cellValue(beangrid.getSelectedRowId(),"customerPoLineNoTrim")!="1")
 		vitems[vitems.length ] = "text="+messagesData.vmistockout+";url=javascript:showVMIStockOut();";
 } 
 
 //USGOV, show dpms address click when addressChangeRequestId
 if($v("companyId")=="USGOV") { 
	 if(cellValue(beangrid.getSelectedRowId(),"addressChangeAllowed")=="Y" && cellValue(beangrid.getSelectedRowId(),"addressChangeRequestId").length>0)
 		vitems[vitems.length ] = "text="+messagesData.dpmsaddress+";url=javascript:openAddressChangeRequest();";
 } 	

//alert("editPrice"+$v("editPrice")+"    unitPriceOnOrder:"+cellValue(beangrid.getSelectedRowId(),"unitPriceOnOrder")); 
 if($v("editPrice")=="true" && $v("companyId")=="MILLER" && (cellValue(beangrid.getSelectedRowId(),"unitPriceOnOrder")==".0100" || cellValue(beangrid.getSelectedRowId(),"unitPriceOnOrder")=="0.0100"))
 	vitems[vitems.length ] = "text="+messagesData.ignore+";url=javascript:ignoreErrorLine();";
 	
 if(vitems.length > 0) {
   	replaceMenu('ediOrderStatusMenu',vitems);  
   	toggleContextMenu('ediOrderStatusMenu');
 }
 else
 	toggleContextMenu('contextMenu');
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

function cancelOrder()
{
   var loc = "/tcmIS/haas/dlagasordertrackingresults.do?userAction=cancelOrder&docNumWithSuffix="+cellValue(beangrid.getSelectedRowId(),"transactionRefNum");                           
   openWinGeneric(loc,'_CancelOrder','380','150','yes',"50","50","no");
}

function showPartList()
{
   var loc = "/tcmIS/hub/chgpart.do?partnum="+cellValue(beangrid.getSelectedRowId(),"catPartNoOrig")+"&rownum="+selectedRowId+"&qty="+cellValue(beangrid.getSelectedRowId(),"orderedQty")+"&company="+$v("companyId")+"&fac="+cellValue(beangrid.getSelectedRowId(),"catalogId");
   openWinGeneric(loc,'_PartList','700','500','yes',"50","50","yes");
}

function showVMIStockOut()
{
   var loc = "/tcmIS/supplier/vmistockout.do?catPartNo="+cellValue(beangrid.getSelectedRowId(),"catPartNoOnOrder")+"&companyId="+$v("companyId")+"&transactionType="+cellValue(beangrid.getSelectedRowId(),"transactionType")+"&lineSeq="+cellValue(beangrid.getSelectedRowId(),"lineSequence")+"&changeOrderSeq="+cellValue(beangrid.getSelectedRowId(),"changeOrderSequence")+"&customerPO="+cellValue(beangrid.getSelectedRowId(),"customerPoNo")+"&poLine="+cellValue(beangrid.getSelectedRowId(),"customerPoLineNoTrim")+"&orderQty="+cellValue(beangrid.getSelectedRowId(),"orderedQty")+"&unitOfSale="+cellValue(beangrid.getSelectedRowId(),"orderedUom");
   openWinGeneric(loc,'_VMIStockOut','700','500','yes',"50","50","yes");
}

function openAddressChangeRequest()
{
  var addressChangeUrl = "/tcmIS/hub/dpmsaddress.do?addressChangeRequestId="+cellValue(beangrid.getSelectedRowId(),"addressChangeRequestId")+"&type="+cellValue(beangrid.getSelectedRowId(),"addressChangeType");

  openWinGeneric(addressChangeUrl,"address_ChangeRequest","800","650","yes","50","50");
}

function checkAll(checkboxname)
{
  var checkall = $("chkAllReset");
  
  var rowsNum = beangrid.getRowsNum();

  rowsNum = rowsNum*1;

  if( checkall.checked ) {
		for(var p = 1 ; p < (rowsNum+1) ; p ++) {
			var cid = checkboxname+p;
			if( ! $(cid).disabled && !$(cid).checked) {
				$(cid).checked = true;
				updateHchStatusA(cid);
			}
		}
  }
  else {
		for(var p = 1 ; p < (rowsNum+1) ; p ++) {
			var cid = checkboxname+p;
			if( !$(cid).disabled && $(cid).checked) {
				$(cid).checked = false;
				updateHchStatusA(cid);
			}
		}
  }
  return true;  
}

function resetSelections()
{
	var rowsNum = beangrid.getRowsNum();
	var flag = false;
	var errorCount = 0;
	var errorMessage = messagesData.validvalues+"\n\n";
	for(var p = 1 ; p < (rowsNum+1) ; p ++) {
		var cid = "okDoReset"+p;
		if($(cid).checked) {
			var orderedQty = beangrid.cellById(p, beangrid.getColIndexById("orderedQty")).getValue();
	  		if (!(isFloat(orderedQty.trim(),false)) )
	   		{
		   	   errorMessage = errorMessage +"\n"+ messagesData.quantity;
		   	   beangrid.cells(p, beangrid.getColIndexById("orderedQty")).setValue("");
			   errorCount = 1;
	   		}
	   		
	   		var unitPriceOnOrder = beangrid.cellById(p, beangrid.getColIndexById("unitPriceOnOrder")).getValue();
	  		if (!(isFloat(unitPriceOnOrder.trim(),false)) )
	   		{
		   	   errorMessage = errorMessage +"\n"+ messagesData.unitprice;
		   	   beangrid.cells(p, beangrid.getColIndexById("unitPriceOnOrder")).setValue("");
			   errorCount = 1;
	   		}
	   		
	   		if(errorCount == 1) {
	   			alert(errorMessage);
	   			$(cid).checked = false;
				updateHchStatusA(cid);
				beangrid.selectRowById(p,null,false,false);
	   			return false;
			}
			else
				flag = true;
		}
	}
	
	if(flag) {
		$("uAction").value = 'resetStatus';
	    parent.showPleaseWait();
	    if (beangrid != null) {
		    beangrid.parentFormOnSubmit();
		}
	    document.genericForm.submit();
    }
    else {
    	alert(messagesData.norowselected);
    	return false;
    }
}


//TODO: finish this function
function ignoreErrorLine()
{

   if (confirm(messagesData.ignorequestion)) {
   	  parent.showPleaseWait();
   	  
   	  $("uAction").value = 'ignoreLine';
	  $("selectedCustomerPoNo").value = cellValue(beangrid.getSelectedRowId(),"customerPoNo");
	  $("selectedCustomerPoLineNo").value = cellValue(beangrid.getSelectedRowId(),"customerPoLineNo");
	  $("selectedLoadId").value = cellValue(beangrid.getSelectedRowId(),"loadId");
	  $("selectedLoadLine").value = cellValue(beangrid.getSelectedRowId(),"loadLine");
      document.genericForm.submit();
   }  
}
/*
function populateShipToList(companyId, dropDownElemName, firstLetter, currentVal) 
{
   var dropDownElem = document.getElementById(dropDownElemName);
   if (dropDownElem.length<=1) {
      var i=0;
      for (i=1; i < altShipToList.length; i++) {
         setOption(i, altShipToList[i].id, 'shiptoid'+i, dropDownElemName, null);
         if (companyId=='IAI') {
            if ((firstLetter==altShipToList[i].firstetter) ||
                (firstLetter=='X' && altShipToList[i].firstletter=='L') ||
                (firstLetter=='C' && altShipToList[i].firstletter=='M') &&
                (currentVal != altShipToList[i].id)) {
               setOption(i, altShipToList[i].id, altShipToList[i].id, dropDownElemName, null);
            }
         } else {
            if (currentVal != altShipToList[i].id) {
               setOption(i, altShipToList[i].id, altShipToList[i].id, dropDownElemName, null);
            }
         }
      }
   }
   return true;
}  */

