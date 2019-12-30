/************************************NEW***************************************************/
var mygrid;
var selectedRowId=null;
var preClass = null;
var preRowId = null;
/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;
var showQualitySummary = false;


function $(a) {
	return document.getElementById(a);
}

/************************************NEW***************************************************/

function doBeforeAddToCart() {
	var tmpComments = cellValue(selectedRowId,"comments");
	if( '' != tmpComments.trim()) {
		parent.showCartCommentDialog(tmpComments);
	}else {
		addToCart();
	}
}

function addToCart() {
	var stockingMethod = cellValue(selectedRowId,"stockingMethod");
	var leadTime = 28;
	var mfgPartNoArr = new Array();
	var ecommerceSource = parent.document.getElementById("ecommerceSource");
	if (ecommerceSource.value == 'OCI') {
		var supplyLeadTime = cellValue(selectedRowId,"medianSupplyLeadTime");
		var leadTime = 28;
		if( stockingMethod == 'MixMax' ) leadTime = 2;
		else if( stockingMethod == 'OOR' && supplyLeadTime != '' ) leadTime = supplyLeadTime;

		var startingRowIndex = haasGrid.haasGetRowSpanStart(selectedRowId);
		var rowSpanLength = haasGrid._haas_row_span_map[startingRowIndex];
		var mfgPartNoArr = new Array();
		var mfgPartNoArrV = new Array();
		for(var j = 0;j < rowSpanLength; j++) {
			var val = cellValue( haasGrid.getRowId(startingRowIndex+j),"mfgPartNo");
			if( mfgPartNoArrV[val] == null ) {
				mfgPartNoArrV[val] = val;
				mfgPartNoArr[mfgPartNoArr.length] = val;
			}
		}
	} else {
		mfgPartNoArr[0] = cellValue(selectedRowId,"mfgPartNo");
	}

	var mfgPartNo = mfgPartNoArr.join(" / ");
	var tempQty = 1;
	var extPrice = '';
	var catalogPrice = cellValue(selectedRowId,"finalPrice");
	var availableQtyForFee = cellValue(selectedRowId,"availableQtyForFee");
	//availableQtyForFee is filled if it's for FEE row
	//therefore if it's not empty use it
	if ('' != availableQtyForFee && availableQtyForFee != '0') {
		tempQty = availableQtyForFee;
	}else {
		var orderQty = cellValue(selectedRowId,"orderQuantity");
		if ('' != orderQty && orderQty != '0') {
			tempQty = orderQty;
		}
	}
	if ('' != catalogPrice) {
		extPrice = (tempQty * catalogPrice).toFixed(2);
	}

	var tmpPackaging = cellValue(selectedRowId,"packaging");
	if (tmpPackaging != null) {
		var tmpPkgArray = tmpPackaging.split(" (");
		tmpPackaging = tmpPkgArray[0];
	}
	
	if (ecommerceSource.value == 'SEAGATEIP' && extPrice == '') {
		alert(messagesData.nopriceerror);
		return;
	}

	var rowid = (new Date()).valueOf();
	var shoppingCartRowIdKey = cellValue(selectedRowId,"catPartNo")+parent.$("lastSearchApplication").value+rowid;
	if( parent.partNum[shoppingCartRowIdKey] == null ) {
		parent.partNum[shoppingCartRowIdKey] = {
			catalogcompanyid:		cellValue(selectedRowId,"catalogCompanyId"),
			catalog:         		cellValue(selectedRowId,"catalogId"),
			inventorygroup:  		cellValue(selectedRowId,"inventoryGroup"),
			facility:    	  		parent.$("lastSearchFacilityId").value,
			workarea:    	  		parent.$("lastSearchApplication").value,
			workareadesc:    		parent.$("lastSearchApplicationDesc").value,
			catpartno:           cellValue(selectedRowId,"catPartNo"),
			partgroupno:         cellValue(selectedRowId,"partGroupNo"),
			partdescription:  	cellValue(selectedRowId,"partDescription"),
			stockingmethod:  		cellValue(selectedRowId,"stockingMethod"),
			quantity:        		tempQty,
			uos: 		        		cellValue(selectedRowId,"unitOfSale"),
			currencyid:      		cellValue(selectedRowId,"currencyId"),
			leadtime:	     		leadTime,
			extprice:            extPrice,
			catalogprice:    		catalogPrice,
			baselineprice:   		cellValue(selectedRowId,"unitPrice"),
			minbuy:       			cellValue(selectedRowId,"minBuy"),
			macpartnum:      		mfgPartNo,
			item: 		     		cellValue(selectedRowId,"itemId"),
			examplepackaging: 	tmpPackaging,
			medianmrleadtime: 	cellValue(selectedRowId,"medianMrLeadTime"),
			prnumber:            '',
			lineitem:            '',
			dateneeded:          '',
			notes:               '',
			critical:            false,
			rowid:       			null,
			rowidd:      			null,
			unitconversionoption:cellValue(selectedRowId,"unitConversionOption"),
			itemtype:  		cellValue(selectedRowId,"itemType")
		};
		parent.totalPart++;
	}else {
		//nothing right now
	}
	//set account sys
	if (!parent.returnFromMr) {
		parent.accountSysId[parent.$("lastSearchFacilityId").value] = altAccountSysId;
	}
	parent.addPart(rowid,shoppingCartRowIdKey,cellValue(selectedRowId,"orderQuantityRule"));
}

function getComment( comment , callbackParam ) {
 	var mytoken =  selectedRowId ;
 	if( mytoken == callbackParam )
 		cell(selectedRowId,"comments").setValue(comment);
}

function editComment() {
var url = 'editcomment.do?catPartNo='+ encodeURIComponent( cellValue(selectedRowId,"catPartNo") ) +
			'&catalogCompanyId='+ cellValue(selectedRowId,"catalogCompanyId") +
            '&partGroupNo='+ cellValue(selectedRowId,"partGroupNo") +
            '&catalogId='+ cellValue(selectedRowId,"catalogId") +
   			'&facilityId=' + encodeURIComponent($('facilityId').value) +
   			'&callback=getComment' +
   			'&callbackParam=' +selectedRowId +
			'&comments=' + encodeURIComponent( cellValue(selectedRowId,"comments") );

openWinGeneric( url ,"EditComment",600,300,'yes' );
}

function approvedWorkAreas() {
    var allCatalog = "N";
    try {
        if (parent.$("allCatalogs").checked)
            allCatalog = "Y";
    }catch(ex){}
    
    var url = 'approvedworkareas.do?facPartNo='+ encodeURIComponent( cellValue(selectedRowId,"catPartNo") ) +
            '&partGroupNo='+ cellValue(selectedRowId,"partGroupNo") +
            '&catalogCompanyId='+cellValue(selectedRowId,"catalogCompanyId") +
            '&catalogId='+cellValue(selectedRowId,"catalogId")+
            '&companyId='+$('companyId').value+
            '&facilityId='+encodeURIComponent($('facilityId').value)+
            '&allCatalog='+allCatalog;

    openWinGeneric(url,"approvedworkareas",700,500,'yes' );
}

function workAreasComments() {

openWinGeneric('workareacommentsmain.do?catPartNo='+ encodeURIComponent( cellValue(selectedRowId,"catPartNo") ) +
            '&partGroupNo='+ cellValue(selectedRowId,"partGroupNo") +
            '&applicationId=' + encodeURIComponent( $('applicationId').value ) +
   			'&facilityId=' + encodeURIComponent($('facilityId').value) +
            '&catalogId='+ cellValue(selectedRowId,"catalogId")
            ,"workareacomments",700,500,'yes' );
}

function partComments() {
var url = 'partnotesmain.do?catalogId='+cellValue(selectedRowId,"catalogId") +
       '&readonly=yes&catPartNo='+encodeURIComponent( cellValue(selectedRowId,"catPartNo") ) +
       '&catalogCompanyId='+cellValue(selectedRowId,"catalogCompanyId") +
       '&companyId='+$('companyId').value ;
openWinGeneric(url,"PartComment",600,300,'yes' );
}

function leadTimePlots() {
//leadtime splot:
openWinGeneric('createleadtimechart.do?catPartNo='+ encodeURIComponent( cellValue(selectedRowId,"catPartNo") ) +
            '&inventoryGroup='+ cellValue(selectedRowId,"inventoryGroup") +
            '&catalogId='+cellValue(selectedRowId,"catalogId")+
            '&catalogCompanyId='+cellValue(selectedRowId,"catalogCompanyId") +
            '&partGroupNo='+ cellValue(selectedRowId,"partGroupNo") +
            '&inventoryGroupName=' + cellValue(selectedRowId,"inventoryGroupName")
            ,"LeadTimePlots","600","600",'yes' );

}

function callItemInventory() {
openWinGeneric('inventorydetail.do?catPartNo='+ encodeURIComponent( cellValue(selectedRowId,"catPartNo") ) +
            '&inventoryGroup='+ cellValue(selectedRowId,"inventoryGroup") +
            '&catalogId='+cellValue(selectedRowId,"catalogId")+
            '&catalogCompanyId='+cellValue(selectedRowId,"catalogCompanyId")+
            '&partGroupNo='+ cellValue(selectedRowId,"partGroupNo")
            ,"ItemInventory","600","600",'yes' );
}

function qualitySummary() {
	openWinGeneric('qualitysummary.do?catPartNo='+ encodeURIComponent(cellValue(selectedRowId,"catPartNo") ) +
	               '&catalogId='+cellValue(selectedRowId,"catalogId") +
	               '&catalogCompanyId='+cellValue(selectedRowId,"catalogCompanyId")+
                   '&partGroupNo='+ cellValue(selectedRowId,"partGroupNo")+
                   '&facilityId=' + encodeURIComponent($('facilityId').value)
                   ,"QualitySummary","800","600",'yes' );
	}

function showSpec( specId ) {
     var hasSpecificFacility = "Y";
     try {
        if (parent.$("allCatalogs").checked)
            hasSpecificFacility = "N";
     }catch(ex){}

    openWinGeneric('docViewer.do?uAction=viewSpec' +
			'&spec=' + encodeURIComponent(specId) +
            '&hasSpecificFacility='+hasSpecificFacility+
            '&companyId=' + $v('companyId') +
			'&facility=' + encodeURIComponent($v('facilityId')) +
			'&catalogId=' + encodeURIComponent(cellValue(selectedRowId,"catalogId")) +
			'&catpartno=' + encodeURIComponent(cellValue(selectedRowId,"catPartNo"))
			,"ViewSpec","800","800",'yes' );
}

function viewMSDS() {
	if(newMsdsViewer)
		openWinGeneric('viewmsds.do?act=msds'+
			'&materialId='+ cellValue(selectedRowId,"materialId") +
			'&showspec=N' +
			'&spec=' + // do we need to know spec id?
			'&cl='+$v('companyId') +
			'&facility=' + encodeURIComponent($('facilityId').value) +
			'&catpartno=' + encodeURIComponent( cellValue(selectedRowId,"catPartNo")  )
			,"ViewMSDS","1024","720",'yes' );
	else 
		openWinGeneric('ViewMsds?act=msds'+
			'&id='+ cellValue(selectedRowId,"materialId") +
			'&showspec=N' +
			'&spec=' + // do we need to know spec id?
			'&cl='+$v('companyId') +
			'&facility=' + encodeURIComponent($('facilityId').value) +
			'&catpartno=' + encodeURIComponent( cellValue(selectedRowId,"catPartNo")  )
			,"ViewMSDS","1024","720",'yes' );
}

function addEditSynonym() {
	parent.showTransitWin("",formatMessage(messagesData.waitingforinput, messagesData.addeditsynonym+" "+cellValue(selectedRowId,"materialId"))+"...");
	openWinGeneric('addeditsynonym.do?materialId='+ cellValue(selectedRowId,"materialId")  +
			'&facilityId=' + encodeURIComponent($v('facilityId')) 
			,"addEditSynonym","450","190",'no' );
}

function	buildMenu() {
	var menuItemCount = mm_returnMenuItemCount('addToCartMenu');
	mm_insertItem('addToCartMenu',2,aiText1);
	mm_deleteItem('addToCartMenu',2);
}

function allCatalogCatalogAddOptions() {
    var approvedCatalog = false;
    //check to see if facility has catalog_facility permission for selected catalog_id
    for (var i = 0; i < altCatalogFacility.length; i++) {
        if (altCatalogFacility[i].catalogId == cellValue(selectedRowId,"catalogId")) {
            approvedCatalog = true;
            break;
        }
    }
    if (approvedCatalog) {
        parent.$("catalogAddLink").innerHTML = '<a href="#" onclick="modifyQpl(); return false;">'+messagesData.modifyQpl+'</a>'+
                                                   ' | '+messagesData.newPartFrom+': <a href="#" onclick="newCatalogPart(); return false;">'+messagesData.beginning+'</a>'+
                                                   ' , <a href="#" onclick="newPartFromExistingPart(); return false;">'+messagesData.part+'</a>';
        if (colId0 >= mygrid.getColIndexById("shelfLifeDisplay")) {
            parent.$("catalogAddLink").innerHTML += ' , <a href="#" onclick="newPartFromExistingItem(); return false;">'+messagesData.item+'</a>'+
                                                    ' , <a href="#" onclick="newPartFromExistingItemModifyPkg(); return false;">'+messagesData.itemModifyPkg+'</a>';
        }
        parent.$("catalogAddLink").innerHTML += ' | <a href="#" onclick="newWorkAreaApproval(); return false;">'+messagesData.newWorkAreaApproval+'</a>';
    }else {
        parent.$("catalogAddLink").innerHTML = messagesData.newPartFrom+': <a href="#" onclick="newCatalogPart(); return false;">'+messagesData.beginning+'</a>';
        if (colId0 >= mygrid.getColIndexById("shelfLifeDisplay")) {
            parent.$("catalogAddLink").innerHTML += ' , <a href="#" onclick="newPartFromExistingItem(); return false;">'+messagesData.item+'</a>'+
                                                    ' , <a href="#" onclick="newPartFromExistingItemModifyPkg(); return false;">'+messagesData.itemModifyPkg+'</a>';
        }
    }
} //end of method


function selectRow()
{
// to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];
//
   if( ee != null ) {
	if( (ee.button != null && ee.button == 2) || ee.which == 3) {
		rightClick = true;
	}
   }


    selectedRowId = rowId0;
    selectedColId = colId0;

     //determine whether to allow user to add part to shopping cart
	 addToCartLinkExist = false;
	 var ecommerceLogon = parent.document.getElementById("ecommerceLogon");
	 var ecommerceSource = parent.document.getElementById("ecommerceSource");
	 var ecommerceFacility = parent.document.getElementById("ecommerceFacility");
	 if (cellValue(selectedRowId,"serviceFeeRow") == 'Y' && ('' == cellValue(selectedRowId,"availableQtyForFee")
	     || '0' == cellValue(selectedRowId,"availableQtyForFee"))) {
		parent.document.getElementById('updateResultLink').style["display"] = "none";
	 }else {
		 if (ecommerceLogon.value == 'Y') {
			 if( ecommerceFacility.value == ecommerceSource.value
				  && cellValue(selectedRowId,"applicationPermission") == 'Y' && cellValue(selectedRowId,'approvalStatus') == 'approved' )
			 {
				parent.document.getElementById('updateResultLink').style["display"] = "";
				addToCartLinkExist = true;
			 }else{
				parent.document.getElementById('updateResultLink').style["display"] = "none";
			 }
		 }else {  //else not coming from an ecommerce application
             if (ecommerceFacility.value == 'SEAGATEIP' || ecommerceFacility.value == 'y') {
                parent.document.getElementById('updateResultLink').style["display"] = "none";
                addToCartLinkExist = false;
             }else if(ecommerceFacility.value != 'Y' && cellValue(selectedRowId,"applicationPermission") == 'Y'
				 && cellValue(selectedRowId,'approvalStatus') == 'approved' ) {
			 	if (intcmIsApplication) {
		 			parent.document.getElementById('updateResultLink').style["display"] = "";
					addToCartLinkExist = true;
				}else{
					parent.document.getElementById('updateResultLink').style["display"] = "none";
			 	}
			 }else{
				parent.document.getElementById('updateResultLink').style["display"] = "none";
			 }
		 }
	 }
	 //save selected row data to catalogmain.jsp so we can pass them back to servers for catalog adds
	 saveDataForCatalogAdd();
	 if (intcmIsApplication && $v("catalogAddPermission") == 'Y' && parent.document.getElementById("catalog").value == 'partCatalog') {
         parent.document.getElementById('catalogAddLink').style["display"] = "";

         var allCatalog = "N";
         try {
            if (parent.$("allCatalogs").checked)
                allCatalog = "Y";
         }catch(ex){}

         if ("Y" == allCatalog) {
             allCatalogCatalogAddOptions();
         }else {
             if (addToCartLinkExist) {
                parent.$("catalogAddLink").innerHTML = ' | <a href="#" onclick="modifyQpl(); return false;">'+messagesData.modifyQpl+'</a>'+
                                                       ' | '+messagesData.newPartFrom+': <a href="#" onclick="newCatalogPart(); return false;">'+messagesData.beginning+'</a>'+
                                                       ' , <a href="#" onclick="newPartFromExistingPart(); return false;">'+messagesData.part+'</a>';
                if (colId0 >= mygrid.getColIndexById("shelfLifeDisplay")) {
                    parent.$("catalogAddLink").innerHTML += ' , <a href="#" onclick="newPartFromExistingItem(); return false;">'+messagesData.item+'</a>'+
                                                            ' , <a href="#" onclick="newPartFromExistingItemModifyPkg(); return false;">'+messagesData.itemModifyPkg+'</a>';
                }
                parent.$("catalogAddLink").innerHTML += ' | <a href="#" onclick="newWorkAreaApproval(); return false;">'+messagesData.newWorkAreaApproval+'</a>';
             }else {
                parent.$("catalogAddLink").innerHTML = '<a href="#" onclick="modifyQpl(); return false;">'+messagesData.modifyQpl+'</a>'+
                                                       ' | '+messagesData.newPartFrom+': <a href="#" onclick="newCatalogPart(); return false;">'+messagesData.beginning+'</a>'+
                                                       ' , <a href="#" onclick="newPartFromExistingPart(); return false;">'+messagesData.part+'</a>';
                if (colId0 >= mygrid.getColIndexById("shelfLifeDisplay")) {
                    parent.$("catalogAddLink").innerHTML += ' , <a href="#" onclick="newPartFromExistingItem(); return false;">'+messagesData.item+'</a>'+
                                                            ' , <a href="#" onclick="newPartFromExistingItemModifyPkg(); return false;">'+messagesData.itemModifyPkg+'</a>';
                }
                parent.$("catalogAddLink").innerHTML += ' | <a href="#" onclick="newWorkAreaApproval(); return false;">'+messagesData.newWorkAreaApproval+'</a>';
             }
         }
     }else {
		 parent.document.getElementById('catalogAddLink').style["display"] = "none";
	 }

	 if( !rightClick ) return;
	 
    if( disableMenu ) return;

   var itemColumnEndIndex = 8;
   if(isCompanyUsesCustomerMSDS == 'Y') {
    itemColumnEndIndex = 9;
   }
   if( colId0 < mygrid.getColIndexById("shelfLifeDisplay"))contextMenuName = 'addToCartMenu';
   else if( colId0 > itemColumnEndIndex ) { preContextMenuName = contextMenuName = 'addToCartMenu3'; }
   else contextMenuName = 'addToCartMenu2';

   preContextMenuName = contextMenuName;

   if (contextMenuName.trim() != '' ) {
		 catid  = cellValue(rowId0,"catalogId");
		 catp   = cellValue(rowId0,"catPartNo");
		 itemid = cellValue(rowId0,"itemId");
		 catcom = cellValue(rowId0,"catalogCompanyId");
		 pgn    = cellValue(rowId0,"partGroupNo");
		 ig     = cellValue(rowId0,"inventoryGroup");
         cid    = $('companyId').value;
         fid    = $('facilityId').value;
		 appid = $('applicationId').value;
		 url = "catalogmenu.do?uAction=loaddata&catPartNo="+ encodeURIComponent( catp ) +
															  "&catalogId="+ catid +
															  "&catalogCompanyId="+ catcom +
															  "&partGroupNo="+ pgn +
															  "&inventoryGroup="+ ig +
															  "&itemId="+ itemid +
                                                              "&companyId="+ encodeURIComponent(cid) +
                                                              "&facilityId="+ encodeURIComponent(fid) +
															  "&applicationId="+ encodeURIComponent( appid );

		var curcursor = document.body.style.cursor;
		 document.body.style.cursor = "wait";

		loadJSON(url);
    }
}

function saveDataForCatalogAdd() {
	parent.document.getElementById("catalogAddCatalogCompanyId").value = cellValue(selectedRowId,"catalogCompanyId");
	parent.document.getElementById("catalogAddCatalogId").value = cellValue(selectedRowId,"catalogId");
	parent.document.getElementById("catalogAddCatPartNo").value = cellValue(selectedRowId,"catPartNo");
	parent.document.getElementById("catalogAddPartDesc").value = cellValue(selectedRowId,"partDescription");
	parent.document.getElementById("catalogAddPartGroupNo").value = cellValue(selectedRowId,"partGroupNo");
	parent.document.getElementById("catalogAddInventoryGroup").value = cellValue(selectedRowId,"inventoryGroup");
	parent.document.getElementById("catalogAddStocked").value = cellValue(selectedRowId,"stockingMethod");
    parent.document.getElementById("catalogAddItemId").value = cellValue(selectedRowId,"itemId");
}

function loadJSON(url)
{
   callToServer(url+"&callback=processReqChangeJSON");
}

function processReqChangeJSON(xmldoc)
{
    document.body.style.cursor = curcursor;
	rowId0 = selectedRowId;
	catid = cellValue(rowId0,"catalogId");
    catp  = cellValue(rowId0,"catPartNo");

    catcom = cellValue(rowId0,"catalogCompanyId");
    pgn = cellValue(rowId0,"partGroupNo");
    ig = cellValue(rowId0,"inventoryGroup");
	fid = $('facilityId').value;
    currentComment = cellValue(rowId0,"comments");
	appid = $('applicationId').value;

	if( preContextMenuName == 'addToCartMenu' ) {
	      var aitems = new Array();
	      aitems[aitems.length] = "text=<b>"+messagesData.partno+"</b>: %% ;url=javascript:doNothing();".replace(/%%/, catp );

          // part inventory menu
    	  aitems[aitems.length] = 'showmenu=partInventory;text='+messagesData.inventory+';image=';
	      var vitems = new Array();
		  vitems[vitems.length ] = "text="+messagesData.partinv+": %% ;url=javascript:callItemInventory();".replace(/%%/,xmldoc.partInventory );
		  vitems[vitems.length ] = "text="+messagesData.reorderpoint+": %% ;url=javascript:doNothing();".replace(/%%/, xmldoc.reorderPoint) ;
		  vitems[vitems.length ] = "text="+messagesData.stockinglevel+": %% ;url=javascript:doNothing();".replace(/%%/,xmldoc.stockingLevel) ;

 		  var itemInv = xmldoc.itemInventory;
		  if( itemInv == null || itemInv.length == 0 )
     		vitems[vitems.length ] = 'text=<font color="gray">'+messagesData.iteminv+'</font>;url=javascript:doNothing();' ;
	      else  {
			vitems[vitems.length ] = 'showmenu=itemInventory;text='+messagesData.iteminv+';image=';
		    // item inv menu in part inv menu
			var vvitems = new Array();
	 		  for(i=0;i < itemInv.length; i++ ) {
	 		  	iid = itemInv[i].item;
	 		  	iq  = itemInv[i].qty;
	 		  	vvitems[vvitems.length ] = "text="+iid+" :"+iq+";url=javascript:callItemInventory();"
 			  }
	 		  replaceMenu('itemInventory',vvitems);
		  }
 		  replaceMenu('partInventory',vitems);
          // spec menu
 		  var specs = xmldoc.spec;
		  if( specs == null || specs.length == 0 ) {
		  		aitems[aitems.length] =	'text=<font color="gray">'+messagesData.spec+'</font>;url=javascript:doNothing();';
		  }
	      else {
		        aitems[aitems.length] =	'showmenu=spec;text='+messagesData.spec+';image=';
				var mitems = new Array();
		 		  for(i=0;i < specs.length; i++ ) {
	 		  		var spec = specs[i];
		 			var specId = spec.specId;
		 			var onLine = spec.onLine;
		 			if ( onLine == 'Y' )
						mitems[mitems.length ] = "text="+specId+";url=javascript:showSpec('"+spec.originalSpecId+"');";
		 			else
		 		  		mitems[mitems.length ] = "text=<font color='gray'>"+specId+"</font>;url=javascript:doNothing();";
	 		  	}
 		  		replaceMenu('spec',mitems);
 		  }
          // comments
    	  aitems[aitems.length] = 'showmenu=comments;text='+messagesData.comments+';image=';

	   	  vitems = new Array();
    	  vitems[vitems.length ] = "text="+messagesData.cartcomment+";url=javascript:editComment();";
    	  vitems[vitems.length ] = "text="+messagesData.workareacomments+";url=javascript:workAreasComments();";
    	  vitems[vitems.length ] = "text="+messagesData.partcomments+";url=javascript:partComments();";
 		  replaceMenu('comments',vitems);

// baseline, only reytheon
if( buildbaseline ) {
    	  aitems[aitems.length] = 'showmenu=baseline;text='+messagesData.baseline+';image=';
	   	  vitems = new Array();

 minPriceStartDate = cellValue(rowId0,"minPriceStartDate");
 maxPriceStartDate = cellValue(rowId0,"maxPriceStartDate");
 if( minPriceStartDate == '' || maxPriceStartDate == '' ) {
 	if( minPriceStartDate == '' )
	 	baselineReset = maxPriceStartDate;
	else
	 	baselineReset = minPriceStartDate;
 }
 else {
 	if( minPriceStartDate == maxPriceStartDate )
	 	baselineReset = minPriceStartDate;
 	else
	 	baselineReset = minPriceStartDate + "-" + maxPriceStartDate;
 }

 vitems[vitems.length ] = "text="+messagesData.baselinereset+": %% ;url=javascript:doNothing();".replace(/%%/, baselineReset );

 minPriceEndDate = cellValue(rowId0,"minPriceEndDate");
 maxPriceEndDate = cellValue(rowId0,"maxPriceEndDate");
 if( minPriceEndDate == '' || maxPriceEndDate == '' ) {
 	if( minPriceEndDate == '' )
	 	baselineExpiration = maxPriceEndDate;
	else
	 	baselineExpiration = minPriceEndDate;
 }
 else {
 	if( minPriceEndDate == maxPriceEndDate )
	 	baselineExpiration = minPriceEndDate;
 	else
	 	baselineExpiration = minPriceEndDate + "-" + maxPriceEndDate;
 }

    	  vitems[vitems.length ] = "text="+messagesData.baselineexp+": %% ;url=javascript:doNothing();".replace(/%%/,baselineExpiration) ;
 		  replaceMenu('baseline',vitems);
}

		//approved work area
		aitems[aitems.length ] = "text="+messagesData.approvedworkareas+";url=javascript:approvedWorkAreas();";
		
		// lead time.
		var leadTimeMsg = messagesData.leadtimeplotpart1;
		if (cellValue(rowId0,"medianMrLeadTime").length > 0)
		  	leadTimeMsg +=  " " + messagesData.leadtimeplotpart2.replace(/[{]0[}]/g,cellValue(rowId0,"medianMrLeadTime"));
		aitems[aitems.length] = 'text='+leadTimeMsg+' ;url=javascript:leadTimePlots();';
		
		 //qualitySummary
		if(showQualitySummary){
		aitems[aitems.length ] = "text="+messagesData.qualitysummary+";url=javascript:qualitySummary();";
		}
		//set/view directed charge
        if (altAccountSysId.length == 1) {
            if (directedChargeAssignment) {
                aitems[aitems.length ] = "text="+messagesData.editPartDirectedCharge+": "+altAccountSysId[0].id+";url=javascript:editDirectedCharge('"+altAccountSysId[0].id+"');";
            }else {
                if (xmldoc.showDirectedCharge == 'Y') {
                    aitems[aitems.length ] = "text="+messagesData.viewPartDirectedCharge+": "+altAccountSysId[0].id+";url=javascript:editDirectedCharge('"+altAccountSysId[0].id+"');";
                }
            }
        }else {
            if (directedChargeAssignment) {
                aitems[aitems.length] = 'showmenu=showDirectedChargeAccountSys;text='+messagesData.editPartDirectedCharge+';image=';
                var menuSubItems = new Array();
                for ( var i=0; i < altAccountSysId.length; i++) {
                    menuSubItems[menuSubItems.length ] = "text="+altAccountSysId[i].id+";url=javascript:editDirectedCharge('"+altAccountSysId[i].id+"');";
                }
                replaceMenu('showDirectedChargeAccountSys',menuSubItems);
            }else {
                if (xmldoc.showDirectedCharge == 'Y') {
                    aitems[aitems.length] = 'showmenu=showDirectedChargeAccountSys;text='+messagesData.viewPartDirectedCharge+';image=';
                    var menuSubItems = new Array();
                    for ( var i=0; i < altAccountSysId.length; i++) {
                        menuSubItems[menuSubItems.length ] = "text="+altAccountSysId[i].id+";url=javascript:editDirectedCharge('"+altAccountSysId[i].id+"');";
                    }
                    replaceMenu('showDirectedChargeAccountSys',menuSubItems);
                }
            }

        }
		//end of set/view directed charge
		
		var requestId = xmldoc.requestId;
		  if( requestId == 'undefined' || requestId == null || requestId.length == 0 )
     		aitems[aitems.length ] = 'text=<font color="gray">'+messagesData.viewapproval+'</font>;url=javascript:doNothing();' ;
	      else  {
			aitems[aitems.length ] = 'showmenu=requestId;text='+messagesData.viewapproval+';image=';
		    // item inv menu in part inv menu
			var menuSubItems2 = new Array();
	 		  for(i=0;i < requestId.length; i++ ) {
	 		  	if(requestId[i].applicationUseGroupName != null && requestId[i].applicationUseGroupName.length > 0)
	 		  		menuSubItems2[menuSubItems2.length ] = "text="+requestId[i].requestId+" ("+requestId[i].applicationUseGroupName+");url=javascript:showCatalogAddRequestScreen("+requestId[i].requestId+");"
	 		  	else
	 		  		menuSubItems2[menuSubItems2.length ] = "text="+requestId[i].requestId+";url=javascript:showCatalogAddRequestScreen("+requestId[i].requestId+");"
 			  }
	 		  replaceMenu('requestId',menuSubItems2);
		}

		replaceMenu('addToCartMenu',aitems);
	  }

	  if( preContextMenuName == 'addToCartMenu2' ) {
	  var itemId = cellValue(rowId0,"itemId");
		  mm_editItem('addToCartMenu2',0,"text=<b>"+messagesData.itemid+": %% ;url=javascript:doNothing();".replace(/%%/,
			  	itemId ) );
	  for(i=5; i>=2 ; i-- )
			mm_deleteItem('addToCartMenu2',i);
			// item inventory menu
 		  var itemInv = xmldoc.itemInventory;
 		  added = false;
		  if( itemInv != null )
 		  for(i=0;i < itemInv.length; i++ ) {
 		  	iid = itemInv[i].item;
 		  	iq  = itemInv[i].qty;

 			if( iid == itemId ) {
				mm_insertItem('addToCartMenu2',1,"text="+messagesData.inventory+": "+iq+" ;url=javascript:callItemInventory();");
				added = true;
			}
 		  }
 		  if( !added )
 		  		mm_insertItem('addToCartMenu2',1,'text=<font color="gray">'+messagesData.inventory+': </font>;url=javascript:doNothing();');
		  lit = xmldoc.Lit;
		  if( lit == null || lit == '' )
			mm_insertItem('addToCartMenu2',2,'text=<font color="gray">'+messagesData.mfglit+'</font>;url=javascript:doNothing();' );
		  else
			mm_insertItem('addToCartMenu2',2,"text="+messagesData.mfglit+";url=javascript:viewLiterature('"+lit+"');");

           img = xmldoc.Img;
 		  if( img == null || img == '')
			mm_insertItem('addToCartMenu2',3,'text=<font color="gray">'+messagesData.image+'</font>;url=javascript:doNothing();' );
		  else
			mm_insertItem('addToCartMenu2',3,"text="+messagesData.image+";url=javascript:viewImage('"+img+"');");

		  kitMsdsNumber = xmldoc.kitMsdsNumber;
		  if (kitMsdsNumber != null && kitMsdsNumber.length > 0) {
		  		mm_insertItem('addToCartMenu2',4,'text='+messagesData.view+" "+messagesData.kitMsds+';url=javascript:viewKitMsds();' );  
		  }

	  }
	   

	  closeAllMenus();
	  popup(preContextMenuName,1);

}

function showCatalogAddRequestScreen(requestId) {
    if ( requestId != null &&  requestId != 0) {
        var loc = "catalogaddrequest.do?uAction=view&requestId="+requestId;
       
        try{
            parent.parent.openIFrame("cataddreq"+requestId,loc,""+messagesData.cataddreq+" "+requestId,"","N");
        }catch (ex) {
            openWinGeneric(loc,"cataddreq"+requestId+"","800","600","yes","50","50");
        }
    }

}

// all same level, at least one item
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

function doNothing() {
}

function newinit() {
	totalLines = document.getElementById("totalLines").value;

	if (totalLines > 0) {
		document.getElementById("prCatalogScreenSearchBean").style["display"] = "";
		parent.document.getElementById("footer").style["display"] = "";
		/*this is the new part.*/
		doInitGrid();
	}else {
		document.getElementById("prCatalogScreenSearchBean").style["display"] = "none";
		parent.document.getElementById("footer").style["display"] = "none";
	}

	parent.myResultFrameId = "partCatalogResultFrame";
	parent.document.getElementById("partCatalogDiv").style["display"] = "";
	parent.document.getElementById("itemCatalogDiv").style["display"] = "none";
	parent.document.getElementById("materialCatalogDiv").style["display"] = "none";

	setResultFrameSize();

	//because I'm adding link I need to do this after setResultFrameSize()
	//and the reason is that I'm doing thing outside of the normal scope
	if (parent.document.getElementById("catalog").value == 'partCatalog') {
		if (intcmIsApplication && $v("catalogAddPermission") == 'Y') {
			parent.document.getElementById('mainUpdateLinks').style["display"] = "";
			parent.document.getElementById('catalogAddLink').style["display"] = "";
			parent.$("catalogAddLink").innerHTML = messagesData.newPartFrom+': <a href="#" onclick="newCatalogPart(); return false;">'+messagesData.beginning+'</a>';
        }else {
			parent.document.getElementById('catalogAddLink').style["display"] = "none";
		}
	}else {
		parent.document.getElementById('catalogAddLink').style["display"] = "none";	
	}
	
	parent.document.getElementById("newMsdsLink").style["display"] = "none";
	parent.document.getElementById("newApprovalCodeLink").style["display"] = "none";
	//set catalog facility data
	parent.catalogFacility = altCatalogFacility;
	
} //end of method

function doInitGrid(){
/*Give the div name that holds the grdi the same name as your dynabean*/
mygrid = new dhtmlXGridObject('prCatalogScreenSearchBean');
mygrid.setImagePath("/dhtmlxGrid/codebase/imgs/");
/*To internationalize column headers, get the values from messagesData*/
if(isCompanyUsesCustomerMSDS == 'Y')
	{
		mygrid.setColVAlign("middle,middle,middle,middle,middle,middle,middle,middle,middle,middle,middle,"+
								  "middle,middle,middle,middle,middle,middle,middle,middle,middle,middle,middle,"+
								  "middle,middle,middle,middle,middle,middle,middle,middle,middle,middle,"+
			                 "middle,middle,middle,middle,middle,middle,middle,middle,middle");
		mygrid.setHeader(
		  messagesData.catalog+
		  ","+messagesData.part+
		  ","+messagesData.description+
		  ","+messagesData.type+
		  ","+messagesData.price+
		  ","+messagesData.partuom+
		  ","+messagesData.qtyOfUomPerItem+
		  ","+messagesData.shelflife+
		  ","+messagesData.item+
		  ","+messagesData.kitMsds +
		  ","+messagesData.componentdescription+
		  ","+messagesData.grade+
		  ","+messagesData.packaging+
		  ","+messagesData.manufacturer+
		  ","+messagesData.mfgpartno+
		  ","+messagesData.status+
		  ","+messagesData.materialMsds+
		  ",,,,,,,,,,,,,,,"+
		  ",,,,,,,,,"
		);
		
		/*You can set column alingments*/
		mygrid.setColAlign("left,left,left,left,left,left,left,left,left,left,left,"+
								 "left,left,left,left,left,left,left,left,left,left,left,"+
								 "left,left,left,left,left,left,left,left,left,left,"+
								 "left,left,left,left,left,left,left,left,left,left");
		//possible values are: baseline,sub,super,top,text-top,middle,bottom,text-bottom
		
		/*Set the type of sort to do on this column.
		Date read-only sorting is not supported yet. For date columns set the sorting type to na*/
		//mygrid.setColSorting("na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na");
		/*Set column types, you can define and editable columns here.*/
		mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,"+
								 "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,"+
								 "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,"+
			                "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
		
		/*This enables tooltips on a column and on mouseover displays the value of the cell in a tooltip.
		* We will enable tooltips only for columns whose width might be less than the text data in that column.
		* Most likely candidates are packaging, item desc, part desc, any user comments etc.*/
		mygrid.enableTooltips("false,false,true,false,false,false,false,false,false,false,true,"+
									 "true,false,false,false,false,false,false,false,false,false,false,"+
									 "false,false,false,false,false,false,false,false,false,false,"+
			 					    "false,false,false,false,false,false,false,false,false");
		var idArr = new Array(
					     "catalogId",
		              "catPartNo",
		              "partDescription",
		              "stockingMethod",
		              "finalPriceDisplay",
		              "unitOfSale",
		              "qtyOfUomPerItem",
		              "shelfLifeDisplay",
		              "itemIdDisplay",
		              "kitMsdsNumber",
			          "materialDesc",
		
		              "grade",
			 	      "packaging",
		              "mfgDesc",
		              "mfgPartNo",
			          "approvalStatus",
			 		  "componentMsdsNumber",
					  "catalogCompanyId",
		              "partGroupNo",
		              "inventoryGroup",
		              "materialId",
		              "applicationPermission",
		
		              "minPriceStartDate",
		              "maxPriceStartDate",
		              "minPriceEndDate",
		              "maxPriceEndDate",
		              "inventoryGroupName",
		              "finalPrice",
		              "medianSupplyLeadTime",
		              "currencyId",
		              "comments",
			 		  "orderQuantity",
		
			           "orderQuantityRule",
			           "minBuy",
			           "unitPrice",
			           "availableQtyForFee",
			           "serviceFeeRow",
			           "medianMrLeadTime",
			 		   "unitConversionOption",
			 		   "itemType",
			 		   "itemId"
		
			 		   );
		
		mygrid.setColumnIds(idArr.join(","));
		if($v('showCatalogPrice') == 'Y')
			/*Set initial widths for your columns, play this by the eye.*/
			mygrid.setInitWidths("7,10,15,4,10,0,0,15,9,10,15,"+
						"10,15,15,10,5,10,0,0,0,0,0,"+
						"0,0,0,0,0,0,0,0,0,0,"+
				                "0,0,0,0,0,0,0,0,0");
		else
			mygrid.setInitWidths("7,10,15,4,0,0,0,15,9,10,15,"+
				"10,15,15,10,5,10,0,0,0,0,0,"+
				"0,0,0,0,0,0,0,0,0,0,"+
		                "0,0,0,0,0,0,0,0,0");
		
		mygrid.setColumnsVisibility("false,false,false,false,false,true,true,false,false,false,false,"+
											 "false,false,false,false,false,false,true,true,true,true,true,"+
											 "true,true,true,true,true,true,true,true,true,true,"+
											 "true,true,true,true,true,true,true,false,false");
		
		mygrid._haas_row_span = true;
		mygrid._haas_row_span_map = rowSpanMap;
		mygrid._haas_row_span_class_map = rowSpanClassMap;
		mygrid._haas_row_span_cols = [0,1,2,3,4,5,6,15];

		if (rowSpanLvl2Map) {
			mygrid._haas_row_span_lvl2 = true;
			mygrid._haas_row_span_lvl2_map = rowSpanLvl2Map;
			mygrid._haas_row_span_lvl2_cols = [7,8,9];
		}

		mygrid._haas_row_span_lvl2_child_select = true;
	}
else
	{
		mygrid.setColVAlign("middle,middle,middle,middle,middle,middle,middle,middle,middle,middle,"+
			  "middle,middle,middle,middle,middle,middle,middle,middle,middle,middle,"+
			  "middle,middle,middle,middle,middle,middle,middle,middle,middle,middle,"+
		       "middle,middle,middle,middle,middle,middle,middle,middle,middle");
	
		mygrid.setHeader(
		messagesData.catalog+
		","+messagesData.part+
		","+messagesData.description+
		","+messagesData.type+
		","+messagesData.price+
		","+messagesData.partuom+
		","+messagesData.qtyOfUomPerItem+
		","+messagesData.shelflife+
		","+messagesData.item+
		","+messagesData.componentdescription+
		","+messagesData.grade+
		","+messagesData.packaging+
		","+messagesData.manufacturer+
		","+messagesData.mfgpartno+
		","+messagesData.status+
		",,,,,,,,,,,,,,,"+
		",,,,,,,,,"
		);
		
		/*You can set column alingments*/
		mygrid.setColAlign("left,left,left,left,left,left,left,left,left,left,"+
					 "left,left,left,left,left,left,left,left,left,left,"+
					 "left,left,left,left,left,left,left,left,left,left,"+
					 "left,left,left,left,left,left,left,left,left");
		//possible values are: baseline,sub,super,top,text-top,middle,bottom,text-bottom
		
		/*Set the type of sort to do on this column.
		Date read-only sorting is not supported yet. For date columns set the sorting type to na*/
		//mygrid.setColSorting("na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na");
		/*Set column types, you can define and editable columns here.*/
		mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,"+
					 "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,"+
					 "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,"+
		      "ro,ro,ro,ro,ro,ro,ro,ro,ro");
		
		/*This enables tooltips on a column and on mouseover displays the value of the cell in a tooltip.
		* We will enable tooltips only for columns whose width might be less than the text data in that column.
		* Most likely candidates are packaging, item desc, part desc, any user comments etc.*/
		mygrid.enableTooltips("false,false,true,false,false,false,false,false,false,true,"+
						 "true,false,false,false,false,false,false,false,false,false,"+
						 "false,false,false,false,false,false,false,false,false,false,"+
					    "false,false,false,false,false,false,false,false,false");
		var idArr = new Array(
		   "catalogId",
		"catPartNo",
		"partDescription",
		"stockingMethod",
		"finalPriceDisplay",
		"unitOfSale",
		"qtyOfUomPerItem",
		"shelfLifeDisplay",
		"itemIdDisplay",
		"materialDesc",
		
		"grade",
		 "packaging",
		"mfgDesc",
		"mfgPartNo",
		"approvalStatus",
		"catalogCompanyId",
		"partGroupNo",
		"inventoryGroup",
		"materialId",
		"applicationPermission",
		
		"minPriceStartDate",
		"maxPriceStartDate",
		"minPriceEndDate",
		"maxPriceEndDate",
		"inventoryGroupName",
		"finalPrice",
		"medianSupplyLeadTime",
		"currencyId",
		"comments",
		 "orderQuantity",
		
		 "orderQuantityRule",
		 "minBuy",
		 "unitPrice",
		 "availableQtyForFee",
		 "serviceFeeRow",
		 "medianMrLeadTime",
		  "unitConversionOption",
		  "itemType",
		"itemId"
		  );
		
		mygrid.setColumnIds(idArr.join(","));
		
		if($v('showCatalogPrice') == 'Y')
		mygrid.setInitWidths("7,10,15,4,10,0,0,15,9,15,"+
		"10,15,15,10,5,0,0,0,0,0,"+
		"0,0,0,0,0,0,0,0,0,0,"+
		      "0,0,0,0,0,0,0,0,0");
		else
			mygrid.setInitWidths("7,10,15,4,0,0,0,15,9,15,"+
			"10,15,15,10,5,0,0,0,0,0,"+
			"0,0,0,0,0,0,0,0,0,0,"+
			      "0,0,0,0,0,0,0,0,0");
		
		mygrid.setColumnsVisibility("false,false,false,false,false,true,true,false,false,false,"+
								 "false,false,false,false,false,true,true,true,true,true,"+
								 "true,true,true,true,true,true,true,true,true,true,"+
								 "true,true,true,true,true,true,true,false,false");

		
		mygrid._haas_row_span = true;
		mygrid._haas_row_span_map = rowSpanMap;
		mygrid._haas_row_span_class_map = rowSpanClassMap;
		mygrid._haas_row_span_cols = [0,1,2,3,4,5,14];

		if (rowSpanLvl2Map) {
			mygrid._haas_row_span_lvl2 = true;
			mygrid._haas_row_span_lvl2_map = rowSpanLvl2Map;
			mygrid._haas_row_span_lvl2_cols = [6,7,8];
		}

		mygrid._haas_row_span_lvl2_child_select = true;
		
		
	}

/*This keeps the row height the same*/
mygrid.enableMultiline(false);
/*This si used to tell the grid that we have row height set at 30px*/
//mygrid.setAwaitedRowHeight(gridRowHeight);
/*You can use this to call the select row functions and the right click funcitons.*/
if( !disableMenu ) {
	mygrid.attachEvent("onRowSelect",selectRow);
	mygrid.attachEvent("onRightClick",selectRow);
}

mygrid.enableSmartRendering(true);

mygrid.setSkin("haas");

mygrid.init();
updateColumnWidths(mygrid);

/*loading from JSON object*/

//updateColumnWidths(mygrid);	

setHaasGrid(mygrid);
mygrid.parse(jsonMainData,"json");
//setTimeout('loadRowSpans()',300);

/*allow copy and paste feature */
mygrid.entBox.onselectstart = function(){ return true; };

/*This is to disable the mouse wheel scroll problem in IE.*/
if (_isIE)
 mygrid.entBox.onmousewheel=stop_event;

/*This is to load the rowspans*/
displayGridSearchDuration();
parent.document.getElementById("partCatalogLastSearchDurationMsg").value = getDisplaySearchDuration();
}


function setGridHeight(resultFrameHeight)
{
  var griDiv = document.getElementById("prCatalogScreenSearchBean");
  griDiv.style.height = resultFrameHeight-3 + "px";
}

function setGridSize()
{
  mygrid.setSizes();
}


var chargeNumberSetup = false;
var directedChargeAssignment = false;
var curAccountSysId = "";
function editDirectedCharge(accountSysId) {
	parent.showTransitWin("",formatMessage(messagesData.waitingforinput, messagesData.directedCharge)+"...");
	curAccountSysId = accountSysId;

	var url = 'clientcabinetmanagementmain.do?uAction=editCabinetDirectedCharge';
	url += '&companyId='+$v("companyId");
	url += '&accountSysId='+accountSysId;
	url += '&facilityId='+$v("facilityId");
	url += '&facilityName='+parent.$v("lastSearchFacilityDesc");
	url += '&sourcePage=clientCabinetManagement';
	url += '&searchText=part';   //I am using this field to keep track of where this is called from
	url += '&applicationId='+$v("applicationId");
	url += '&catalogCompanyId='+cellValue(mygrid.getSelectedRowId(),"catalogCompanyId");
	url += '&catalogId='+cellValue(mygrid.getSelectedRowId(),"catalogId");
	url += '&catalogAddPartGroupNo='+cellValue(mygrid.getSelectedRowId(),"partGroupNo");
	url += '&catalogAddCatPartNo='+cellValue(mygrid.getSelectedRowId(),"catPartNo");  //the reason I am using this is because cat_part_no is an array
    if (directedChargeAssignment) {
        url += '&canEditData=Y';    
    }else {
         url += '&canEditData=N';
    }

    openWinGeneric(url,"_editWorkAreaCabinetPartDirectedCharge",480,380,'no' );
}

function setWorkAreaDirectedChargeNumbers(chargeType,chargeNumbers,poNumber,releaseNumber,userEnteredChargeNumber) {
	var url = "clientcabinetmanagementmain.do?uAction=setWorkAreaCabinetPartDirectedCharge";
	url += "&companyId="+$v("companyId");
	url += "&accountSysId="+curAccountSysId;
	url += "&facilityId="+$v("facilityId");
	url += "&poNumber="+poNumber;
	url += "&poLine="+releaseNumber;
	url += "&chargeType="+chargeType;
	url += "&chargeNumbers="+chargeNumbers;
	url += "&userEnteredChargeNumber="+userEnteredChargeNumber;
	url += "&sourcePage=clientCabinetManagement";
	url += "&searchText=part";   //I am using this field to keep track of where this is called from
	url += '&applicationId='+$v("applicationId");
	url += '&catalogCompanyId='+cellValue(mygrid.getSelectedRowId(),"catalogCompanyId");
	url += '&catalogId='+cellValue(mygrid.getSelectedRowId(),"catalogId");
	url += '&catalogAddPartGroupNo='+cellValue(mygrid.getSelectedRowId(),"partGroupNo");
	url += '&catalogAddCatPartNo='+cellValue(mygrid.getSelectedRowId(),"catPartNo");  //the reason I am using this is because cat_part_no is an array

	callToServer(url);
} //end of method


function setChargeNumberPoData(chargeType,chargeNumber,poNumber,releaseNumber,userEnteredChargeNumber) {
	if (chargeNumber.length == 0 && poNumber.length == 0) {
		parent.closeTransitWin();
	}else {
		setWorkAreaDirectedChargeNumbers(chargeType,chargeNumber,poNumber,releaseNumber,userEnteredChargeNumber);
	}
}

function deleteDirectedCharge(chargeType) {
	var url = "clientcabinetmanagementmain.do?uAction=deleteDirectedCharge";
	url += "&companyId="+$v("companyId");
	url += "&facilityId="+$v("facilityId");
    url += "&sourcePage=clientCabinetManagement";
    url += "&searchText=part";   //I am using this field to keep track of where this is called from
	url += "&applicationId="+$v("applicationId");
    url += "&chargeType="+chargeType;
    url += '&catalogCompanyId='+cellValue(mygrid.getSelectedRowId(),"catalogCompanyId");
	url += '&catalogId='+cellValue(mygrid.getSelectedRowId(),"catalogId");
	url += '&catalogAddPartGroupNo='+cellValue(mygrid.getSelectedRowId(),"partGroupNo");
	url += '&catalogAddCatPartNo='+cellValue(mygrid.getSelectedRowId(),"catPartNo");  //the reason I am using this is because cat_part_no is an array
	callToServer(url);
}

function validateChargeNumber(chargeNumbersOk,tmpCallEditDirectedChargeFrom,tmpAccountSysId,tmpChargeType,tmpChargeNumbers,tmpPo,tmpPoLine,tmpUserEnteredChargeNumber) {
	parent.closeTransitWin();
	if (chargeNumbersOk != 'OK') {
		curAccountSysId = tmpAccountSysId;
		if (chargeNumberSetup) {
			var answer = confirm(messagesData.invalidChargeNumberAddTolist);
			if (answer) {
				addWorkAreaCabinetPartDirectedChargeNumbers(tmpChargeType,tmpChargeNumbers,tmpPo,tmpPoLine,tmpUserEnteredChargeNumber);
			}else {
				editDirectedCharge(tmpAccountSysId);
			}
		}else {
			alert(messagesData.invalidChargeNumbers);
			editDirectedCharge(tmpAccountSysId);
		}
	}
}

function addWorkAreaCabinetPartDirectedChargeNumbers(chargeType,chargeNumbers,poNumber,releaseNumber,userEnteredChargeNumber) {
	parent.showTransitWin("",formatMessage(messagesData.waitingforinput, messagesData.directedCharge)+"...");

	var url = "clientcabinetmanagementmain.do?uAction=addWorkAreaCabinetPartDirectedCharge";
	url += "&companyId="+$v("companyId");
	url += "&accountSysId="+curAccountSysId;
	url += "&facilityId="+$v("facilityId");
	url += "&poNumber="+poNumber;
	url += "&poLine="+releaseNumber;
	url += "&chargeType="+chargeType;
	url += "&chargeNumbers="+chargeNumbers;
	url += "&userEnteredChargeNumber="+userEnteredChargeNumber;
	url += "&sourcePage=clientCabinetManagement";
	url += "&searchText=part";   //I am using this field to keep track of where this is called from
	url += '&applicationId='+$v("applicationId");
	url += '&catalogCompanyId='+cellValue(mygrid.getSelectedRowId(),"catalogCompanyId");
	url += '&catalogId='+cellValue(mygrid.getSelectedRowId(),"catalogId");
	url += '&catalogAddPartGroupNo='+cellValue(mygrid.getSelectedRowId(),"partGroupNo");
	url += '&catalogAddCatPartNo='+cellValue(mygrid.getSelectedRowId(),"catPartNo");  //the reason I am using this is because cat_part_no is an array

	callToServer(url);
}

function viewKitMsds() {
	var reportLoc = "/HaasReports/report/printConfigurableReport.do"+
                    "?pr_itemId="+cellValue(mygrid.getSelectedRowId(),"itemId")+
                    "&pr_companyId="+$v("companyId")+
				    "&pr_custMsdsDb="+
				    "&pr_custMsdsNo="+kitMsdsNumber+
                    "&pr_catalogCompanyId="+cellValue(mygrid.getSelectedRowId(),"catalogCompanyId")+
                    "&pr_catalogId="+cellValue(mygrid.getSelectedRowId(),"catalogId")+
                    "&rpt_ReportBeanId=MSDSKitReportDefinition";
	openWinGeneric(reportLoc,"viewKitMsds","800","550","yes","100","100");
}


function printSecondaryLabelInformation() {
var labelQty = 1;
labelQty = prompt(messagesData.labelquantity,labelQty);
openWinGeneric('printsecondarylabelinformation.do?materialId='+ cellValue(selectedRowId,"materialId") +
			'&facilityId=' + encodeURIComponent($('facilityId').value) +
			'&catalogId=' + encodeURIComponent( cellValue(selectedRowId,"catalogId"))+
            '&labelQty=' + labelQty
            ,"SecondaryLabelInformation","900","600",'yes' );
}

function updateSecondaryLabelInformation() {
	parent.showTransitWin("",formatMessage(messagesData.waitingforinput, messagesData.secondarylabelinformationformaterialid+" "+cellValue(selectedRowId,"materialId"))+"...");
	openWinGeneric('secondarylabelinformation.do?materialId='+ cellValue(selectedRowId,"materialId") +
			'&facilityId=' + encodeURIComponent($('facilityId').value) +
			'&catalogId=' + encodeURIComponent( cellValue(selectedRowId,"catalogId"))
			,"SecondaryLabelInformation","900","600",'yes' );
}

function viewLiterature(loc) {
    try {
        openWinGeneric(loc,"viewLiterature","800","550",'yes');
    }catch(ex) {}
}

function viewImage(loc) {
    try {
        openWinGeneric(loc,"viewImage","800","550",'yes');
    }catch(ex) {}
}
