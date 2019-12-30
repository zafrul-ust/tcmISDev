<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srndRowSpan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
    
<title>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
	messagesData={alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	recordFound:"<fmt:message key="label.recordFound"/>",
	searchDuration:"<fmt:message key="label.searchDuration"/>",
	minutes:"<fmt:message key="label.minutes"/>",
	seconds:"<fmt:message key="label.seconds"/>",
	validvalues:"<fmt:message key="label.validvalues"/>",
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
	itemInteger:"<fmt:message key="error.item.integer"/>",
	dupline:'Add CPP',
	waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
	additem:'<fmt:message key="label.additem"/>',
	newstartdate:'<fmt:message key="label.newstartdate"/>',
	deletestartdate:'<fmt:message key="label.deletestartdate"/>',
	itemnotes:"<fmt:message key="itemnotes.title"/>",
	changepriority:'<fmt:message key="label.changepriority"/>',
	newpricebreak:'<fmt:message key="label.newpricebreak"/>',
	supplieritemnotes:'<fmt:message key="supplieritemnotes.title"/>',
	editsupplimentdata:'<fmt:message key="label.editsupplimentdata"/>',
	wanttocontinue:'<fmt:message key="label.wanttocontinue"/>',
	duplicate:'<fmt:message key="label.duplicate"/>',
	batchupdate:'<fmt:message key="label.batchupdate"/>',
	addeditadditcharges:'<fmt:message key="label.addeditadditcharges"/>',
	fromQuantity:'<fmt:message key="label.fromquantity"/>'
};
resizeGridWithWindow = true;
windowCloseOnEsc = true;

function getCarrier( rowId ) {

	  loc = "/tcmIS/supply/pocarriermain.do?callbackparam="+rowId+"&callbackfunction=carrierChanged";
	  openWinGeneric(loc,"getCarrier","600","400","yes","50","50","20","20","no");
}


function clearCarrier(rowId) {
	cell(rowId,"carrierName").setValue('');
	cell(rowId,"carrier").setValue('');
	grandChanged(rowId,"carrier");
}

function carrierChanged(id,name,selectedAccount,selectedCompanyHub,rowId) {
	cell(rowId,"carrier").setValue(id);
	cell(rowId,"carrierName").setValue(name);
	grandChanged(rowId,"carrier");
}

function getBuyer( rowId ) {
	  loc = "/tcmIS/haas/searchpersonnelmain.do?fixedCompanyId=Y&callbackparam="+rowId
	  openWinGeneric(loc,"getBuyer","600","400","yes","50","50","20","20","no");
}

function setBuyer(rowId, buyer, buyerName ) {
	cell(rowId,"buyer").setValue(buyer);
	cell(rowId,"buyerName").setValue(buyerName);
	grandChanged(rowId,"buyer");  
}

function clearBuyer(rowId) {
	cell(rowId,"buyerName").setValue('');
	cell(rowId,"buyer").setValue('');
	grandChanged(rowId,"buyer");  
}

function getSupplierContact( rowId ) {
     var supplier = cellValue(rowId,"supplier");
      loc = "/tcmIS/supply/posuppliercontact.do?action=search&callbackparam="+rowId+"&supplier="+supplier+"&displayElementId=supplierContactName&valueElementId=supplierContactId&fromSupplierPriceList=Y";
      openWinGeneric(loc,"shoSupplierContacts","800","450","yes","50","50","no");
}

function setSupplierContact(rowId, id, name) {
    cell(rowId,"supplierContactId").setValue(id);
	cell(rowId,"supplierContactName").setValue(name);
	grandChanged(rowId,"supplierContactId");
}

function clearSupplierContact(rowId) {
	cell(rowId,"supplierContactName").setValue('');
	cell(rowId,"supplierContactId").setValue('');
	grandChanged(rowId,"supplierContactId");
}

var currencyId = new Array(
	<c:forEach var="cbean" items="${vvCurrencyBeanCollection}" varStatus="status">
	<c:if test="${status.index > 0}">,</c:if>
		{text:'<tcmis:jsReplace value="${cbean.currencyId}"/>',value:'${cbean.currencyId}'}
	</c:forEach>
);

//currently have up to 11.
var priority = new Array(
	{text:'1',value:'1'},
	{text:'2',value:'2'},
	{text:'3',value:'3'},
	{text:'4',value:'4'},
	{text:'5',value:'5'},
	{text:'6',value:'6'},
	{text:'7',value:'7'},
	{text:'8',value:'8'},
	{text:'9',value:'9'},
	{text:'10',value:'10'},
	{text:'11',value:'11'}
);

var roundToMultiple = new Array(
	{text:'Review',value:'REVIEW'},
	{text:'Up',value:'UP'},
	{text:'Down',value:'DOWN'}
);

var status = new Array(
	{text:'Active',value:'DBUY'},
    {text:'Pricing',value:'PRICING'},
    {text:'Inactive',value:'INACTIVE'}
);

var supplyPath = new Array(
	{text:'Purchaser',value:'Purchaser'},
    {text:'WBuy',value:'Wbuy'},
    {text:'IBuy',value:'Ibuy'},
    {text:'XBuy',value:'Xbuy'}
);

var itemStatusArray = status;

<c:set var="activeCount" value="0"/>

var freightOnBoard = new Array(
	<c:forEach var="freightBean" items="${vvFreightOnBoardBeanCollection}" varStatus="status">
		<c:if test="${status.current.status == 'ACTIVE'}">
			<c:if test="${activeCount > 0}">,</c:if>
			<c:set var="activeCount" value="${activeCount+1}"/>
			{text:'<tcmis:jsReplace value="${status.current.description}"/>',value:'${status.current.freightOnBoard}'}
		</c:if>
	</c:forEach>
);

//all same level, at least one item
function replaceMenu(menuname,menus){
	var i = mm_returnMenuItemCount(menuname);

	for(;i> 1; i-- )
		mm_deleteItem(menuname,i);

	for( i = 0; i < menus.length; ){
 		var str = menus[i];

		i++;
		mm_insertItem(menuname,i,str);
		// delete original first item.
		if( i == 1 ) 
			mm_deleteItem(menuname,1);
	}
}

function validateForm() {
        var errorMessage = "";
        var errorCount = 0;
        errorMessage +=  messagesData.validvalues + "\n";
    for(var i=0;i<haasGrid.getRowsNum();i++){
        rowid = haasGrid.getRowId(i);
		if (haasGrid.haasRowIsRendered(rowid)) {
			var pri = cellValue(rowid,"unitPrice");
			var supplyPath = cellValue(rowid,"supplyPath");
			var carrier = cellValue(rowid,"carrier");
			var buyer = cellValue(rowid,"buyer");
			var supplierContact = cellValue(rowid,"supplierContactName");
			var leadTimeDays = cellValue(rowid,"leadTimeDays");
			if(!isFloat(pri,false) ) {
				haasGrid.selectRowById(rowid);
				{
				  errorMessage += "<fmt:message key="label.unitprice"/>" + "\n"; 
				  errorCount = 1;
				}
			  }
			 if((supplyPath.trim().length != 0 ) && (supplyPath == "Wbuy" || supplyPath == "Ibuy")) {
				haasGrid.selectRowById(rowid);
				 if(carrier.trim().length == 0)
				   {errorMessage += "<fmt:message key="label.carrier"/>" + "\n";errorCount = 1;}
				 if(buyer.trim().length == 0)
				   {errorMessage += "<fmt:message key="label.buyer"/>" + "\n";errorCount = 1;}
				 if(leadTimeDays.trim().length == 0)
				   {errorMessage += "<fmt:message key="error.leadtimedays.required"/>"+ "\n";errorCount = 1;}
				 if(supplierContact.trim().length == 0)
				   {errorMessage += "<fmt:message key="label.suppliercontact"/>"+ "\n";errorCount = 1;}
				 if(cellValue(rowid,"remainingShelfLifePercent").trim().length == 0)
				   {errorMessage += "<fmt:message key="label.remainingShelfLife"/>"+ "\n";errorCount = 1;}    
				
			}
			if (errorCount >0)
			 {
			    alert(errorMessage);
			    return false;
			 }
			if (!lineMap[i] || lineMap[i] < 1) {
				var startDate = cellValue(rowid, "startDate");
				var breakQuantity = cellValue(rowid, "breakQuantity");
				var startingRow = haasGrid.haasGetRowSpanStart(rowid);
				for (var rowIndex = i - 1; rowIndex >= startingRow; rowIndex--) {
					var curRowId = haasGrid.getRowId(rowIndex);
					if (startDate == cellValue(curRowId, "startDate")) {
						var prevQuantity = cellValue(curRowId, "breakQuantity");
						if (breakQuantity == prevQuantity && !confirm(messagesData.duplicate  + " '" + messagesData.fromQuantity + "'\n" + messagesData.wanttocontinue)) {
							haasGrid.selectRowById(rowid);
							return false;
						}
					}
				}
			}
		}
	}
	return true;
}


function _simpleUpdate(act,val) { 
	if( window['validateForm'] && !validateForm() )
		return false;
	if( !act )
		act = 'uAction';
	if( !val )
		val = 'update';
  	$(act).value = val;

  	if( window['showPleaseWait'] ) 
		showPleaseWait();
	else 
		parent.showPleaseWait(); 

	var preval = '';
	var prevalString = '';
	// Copy startDateDisplay to startDate
	for(var i=0;i<haasGrid.getRowsNum();i++){
		rowid = haasGrid.getRowId(i);
		if (haasGrid.haasRowIsRendered(rowid)) {
			if( line2Map[i] != null ) {
				preval = $v("startDateDisplay"+rowid);
				if( preval == null ) {
					preval = cellValue(rowid,"startDateDisplay");
					prevalString = cellValue(rowid,"startDateString");
				}
			}
			if (!preval.match(/^<INPUT/i)) {
				cell(rowid, "startDate").setValue( preval );
				cell(rowid, "startDateString").setValue( prevalString );
				if (rowSpanLvl2Map[i] > 1) {
					// Handle startDate for various pricebreaks
					for (var z = 1; z < rowSpanLvl2Map[i]; z++) {
						cell(rowid + z, "startDate").setValue( preval );
						cell(rowid + z, "startDateString").setValue( prevalString );
					}
				}
			}
		}
	}
			
	haasGrid.parentFormOnSubmit(); //prepare grid for data sending
	document.genericForm.submit();
	return false;
}

function resetMarkedRows() {
	var dateIndex = haasGrid.getColIndexById('startDateDisplay');
	var rowId = 0;
	for(var rowIndex=0; rowIndex < haasGrid.getRowsNum(); rowIndex++){
		if (haasGrid._haas_json_data.rows[rowIndex].data[dateIndex] == 'newStartDate') {
			rowId = haasGrid.getRowId(rowIndex);
			if(!haasGrid.haasRowIsRendered(rowId)) {
				haasGrid.haasRenderRow(rowId);
			}
			var datehtml = '<input class="inputBox pointer" id="startDateDisplay'+rowId+'" type="text" value="'+$v('today')+'" size="8" readonly onClick="return myGetCalendar('+rowId+')"/>';
			cell(rowId,"startDateDisplay").setCValue(datehtml);
		}
	}	
}

with ( milonic=new menuname("addcpp") ) {
	 top="offset=2";
	 style=submenuStyle;
	 itemheight=17;
	 aI( "text=Add Part To Price Group;url=javascript:newitem();" );
	 aI( "text=New Start Date;url=javascript:newFutureCpp();" );
	 aI( "text=New Price Break;url=javascript:dupline();" );
}

drawMenus();

function priceChanged(rowid,columnid) {
	if( cellValue(rowid,"isParent") != "Y" ) 
		cell(rowid,"changed").setValue("Y");
	else if( cellValue(rowid,"level2Changed") != "New" ) 
		cell(rowid,"level2Changed").setValue("Y");
}

// parent level.
function parentChanged(rowid,columnid) {
	if( cellValue(rowid,"isParent") != "Y" ) return ; 
	if( cellValue(rowid,"level2Changed") != "New" ) 
		cell(rowid,"level2Changed").setValue("Y");
}

// Grand properties.supplierPartNo...
function grandChanged(rowid,columnid) {
	if( cellValue(rowid,"isGrand") != "Y" ) return ;
	if( cellValue(rowid,"level1Changed") != "New" ) 
		cell(rowid,"level1Changed").setValue("Y");
}

function multipleOfChanged(rowid,columnid) {
	grandChanged(rowid,columnid);
	if(cellValue(rowid,"multipleOf") == 1 || cellValue(rowid,"multipleOf") == 0 || cellValue(rowid,"multipleOf").trim().length == 0 ) {
		cell(rowid,"roundToMultiplePermission").setValue("N");
		haasGrid._haas_json_data.rows[haasGrid.getRowIndex(rowid)].data["roundToMultiplePermission"] = 'N';
		cell(rowid,"roundToMultiple").setValue("");
	}
	else {
		cell(rowid,"roundToMultiplePermission").setValue("Y");
		haasGrid._haas_json_data.rows[haasGrid.getRowIndex(rowid)].data["roundToMultiplePermission"] = 'Y';
		cell(rowid,"roundToMultiple").setValue(cellValue(rowid,"oldRoundToMultiple"));
	}

	var parentIndex = haasGrid.haasGetRowSpanStart(rowid);
	haasGrid.haasRenderRow(haasGrid.getRowId(parentIndex));
}

var lineMap3 = new Array();

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
<c:set var="leadtimeindays"><fmt:message key="label.leadtimeindays"/></c:set>
var config = [
	{columnId:"permission"},
	{columnId:"roundToMultiplePermission"},
	{columnId:"supplyPathPermission"},
	{columnId:"inventoryGroup"},
	{columnId:"itemId", columnName:'<fmt:message key="label.catalogitem"/>', sorting:"haasStr", tooltip:true, width:6},
	{columnId:"itemDesc", columnName:'<fmt:message key="label.description"/>', sorting:"haasStr", tooltip:true, width:20},
	{columnId:"shipToLocationId",columnName:'<fmt:message key="label.shipto"/>'},
	{columnId:"inventoryGroupName", columnName:'<tcmis:jsReplace value="${inventorygroup}"/>', tooltip:true},
	{columnId:"priority", columnName:'<fmt:message key="label.priority"/>', width:4},
	{columnId:"supplier"},
	{columnId:"supplierName"},
	{columnId:"supplierDisplay", columnName:'<fmt:message key="label.supplier"/>', width:20, tooltip:true},
	{columnId:"supplierPartNo", columnName:'<fmt:message key="label.supplierpartnum"/>', type:'hed', onChange:grandChanged},
	{columnId:"leadTimeDays", columnName:'<tcmis:jsReplace value="${leadtimeindays}"/>', type:'hed', width:4, size:2, onChange:grandChanged },
	{columnId:"status", columnName:'<fmt:message key="label.status"/>', type:'hcoro', width:7, onChange:grandChanged},
	{columnId:"startDateDisplay", columnName:'<fmt:message key="label.startDate"/>'},
	{columnId:"currencyId", columnName:'<fmt:message key="label.currencyid"/>', type:'hcoro', onChange:parentChanged},
	{columnId:"breakQuantityPermission"},
    {columnId:"breakQuantity", columnName:'<fmt:message key="label.fromquantity"/>', type:"hed", permission:true, onChange:priceChanged},
    {columnId:"unitPrice", columnName:'<fmt:message key="label.unitprice"/>', type:"hed", onChange:priceChanged},
    {columnId:"priceUpdatedByName", columnName:'<fmt:message key="label.priceLastUpdatedBy"/>', submit: false },
    {columnId:"priceUpdatedDate", columnName:'<fmt:message key="label.priceLastUpdatedDate"/>', submit: false },
    {columnId:"multipleOf", columnName:'<fmt:message key="label.multipleOf"/>', type:'hed', width:4, onChange:multipleOfChanged},
    {columnId:"minBuyQuantity", columnName:'<fmt:message key="label.minimumbuyqty"/>', type:'hed', onChange:grandChanged, width:6},
	{columnId:"minBuyValue", columnName:'<fmt:message key="label.minimumordervalue"/>', type:'hed', onChange:grandChanged, width:6},
    {columnId:"supplyPath", columnName:'<fmt:message key="label.supplypath"/>', type:'hcoro', width:8, onChange:grandChanged},
	{columnId:"remainingShelfLifePercent", columnName:'<fmt:message key="label.remainingShelfLife"/>', type:'hed', width:6, onChange:grandChanged },
    {columnId:"freightOnBoard" ,columnName:'<fmt:message key="label.incoterms"/>',type:'hcoro', width:15, onChange:grandChanged},
	{columnId:"carrierName", columnName:'<fmt:message key="label.carrier"/>', attachHeader:'<fmt:message key="label.name"/>', tooltip:true, width:6},
	{columnId:"carrierLookup", columnName:'#cspan', attachHeader:'<fmt:message key="label.lookup"/>', width:6 },
	{columnId:"criticalOrderCarrierName", columnName:'<fmt:message key="label.criticalordercarrier"/>', attachHeader:'<fmt:message key="label.name"/>', tooltip:true, width:6},
	{columnId:"criticalOrderCarrierLookup", columnName:'#cspan', attachHeader:'<fmt:message key="label.lookup"/>', width:6 },
	{columnId:"buyerName", columnName:'<fmt:message key="label.buyer"/>', attachHeader:'<fmt:message key="label.name"/>', tooltip:true, width:10},
	{columnId:"buyerLookup", columnName:'#cspan', attachHeader:'<fmt:message key="label.lookup"/>', width:6},
	{columnId:"roundToMultiple", columnName:'<fmt:message key="label.rounding"/>',	 type:'hcoro', width:7, onChange:grandChanged},
	{columnId:"supplierContactName", columnName:'<fmt:message key="label.suppliercontact"/>', attachHeader:'<fmt:message key="label.contact"/>', tooltip:true, width:10},
	{columnId:"supplierContactLookup", columnName:'#cspan', attachHeader:'<fmt:message key="label.lookup"/>', width:6},
	{columnId:"updateByName", columnName:'<fmt:message key="label.lastUpdatedBy"/>'},
  	{columnId:"updatedDate", columnName:'<fmt:message key="label.laastUpdatedDate"/>' },
	{columnId:"startDate"},
	{columnId:"oldStartDate"},
	{columnId:"startDateString"},
	{columnId:"updatedDateTime", sorting:'int'},
	{columnId:"endDate"},
	{columnId:"oldbreakQuantity"},
	{columnId:"oldUnitPrice"},
	{columnId:"carrier"},
	{columnId:"criticalOrderCarrier"},
	{columnId:"buyer"},
	{columnId:"oldRoundToMultiple"},
	{columnId:"supplierContactId"},
	{columnId:"dbuyContractId"},
	{columnId:"shipToCompanyId"},
	{columnId:"sourcer"},
	{columnId:"loadingComments"},
	{columnId:"opsEntityId"},
	{columnId:"inventoryGroupHub"},
	{columnId:"opsCompanyId"},
	{columnId:"isParent"},
	{columnId:"isGrand"},
	{columnId:"level1Changed"},
	{columnId:"level2Changed"},
	{columnId:"changed"}
    ];

function faketotal() {
	if( $v('totalLines') == '0' )
		document.getElementById('updateResultLink').style["display"]="none";
	else
		document.getElementById('updateResultLink').style["display"]="";
}

var dhxFreezeWins = null;
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
		transitWin.setModal(true);
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

function addnewitem() {
	var loc = "newitem.do?uAction=new&shipToId=" + 
	"&itemId=" + '${param.itemId}'+
	"&inventoryGroup=" + $v("inventoryGroup")+
	"&hub=" + $v("hub")+
	"&opsEntityId=" + $v("opsEntityId")+
	"&supplier=" + $v("supplier")+
	"&supplierName=" +  encodeURIComponent( $v("supplierName") )+
	"&companyId=Radian";
	try {
		if( cellValue(1,'itemDesc').length > 0)
			loc += "&itemDesc=" + encodeURIComponent(cellValue(1,'itemDesc'));
	} catch(ex) {}

	openWinGeneric(loc,'newitem',"550","310","yes","50","50","20","20","no");
}

<c:set var="permission" value="N"/>
<c:set var="supplyPathPermission" value="N"/>
var multiplePermissions = true;

// Build up the array for the columns which use different permissions
var permissionColumns = new Array();
permissionColumns = {
        "supplyPath" : true
        
};

function doOnload(){
	faketotal();
	
	if($v('totalLines') == 0) $('totalLines').value = 1;
	
	if(window['gridConfig'])
		popupOnLoadWithGrid(gridConfig);
		
	if($v('dataCount') == 0) {
		$('updateResultLink').style['display'] = 'none';
		$('footer').style['display'] = 'none';
	}
	else {
// old highlight
		/*		rowid = haasGrid.getRowId(0);
		for(var rowIndex=1; rowIndex < haasGrid.getRowsNum(); rowIndex++){
			if( cellValue(rowid,'inventoryGroup') == '${param.inventoryGroup}' && cellValue(rowId,"unitPrice") == '${param.uPrice}' ){
				rowId = haasGrid.getRowId(rowIndex);
				break;
			}
			if( cellValue(rowId,"unitPrice") == '${param.uPrice}' ){
				rowId = haasGrid.getRowId(rowIndex);
			}
		}
*/ 
		for(var rowIndex=1; rowIndex < haasGrid.getRowsNum(); rowIndex++){
			if( cellValue(rowid,'dbuyContractId') == '${param.betPriceDbuyContractId}' ) {
				rowId = haasGrid.getRowId(rowIndex);
				haasGrid._haas_row_span_lvl2_child_select = true;
				haasGrid.selectRow(newid);
			}
		}
	}
}


// -->
</script>
</head>
<body bgcolor="#ffffff" onload="doOnload();"  onresize="resizeFrames()">

<tcmis:form action="/editpricelist.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
    showErrorMessage = false;
<c:if test="${!empty tcmISError}">
	showErrorMessage = true;
</c:if>
//-->
</script>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display:">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
      <span id="updateResultLink" style="display: none">
         <a href="#" onclick="_simpleUpdate();"><fmt:message key="label.update"/></a> |
      </span>
      
      <c:forEach var="opsEntityBean" items="${personnelBean.permissionBean.userGroupMemberOpsEntityBeanCollection}" varStatus="status">
			  <c:if test="${opsEntityBean.userGroupId  == 'supplierPriceList' && opsEntityBean.opsEntityId == param.opsEntityId}">
			    <c:set var="addItemPermission" value="Y" />
			  </c:if>
	  </c:forEach>
		  <c:if test="${addItemPermission  == 'Y' }">
		  <a href="javascript:addnewitem()"><fmt:message key="label.additem"/></a>
		  </c:if>
      <div id="headerline"><span id="mainUpdateLinks" style="display:"></span> 
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
<!--  result page section start -->

<c:set var="dataCount" value='${0}'/>

<div id="SupperPriceListBean" style="height:400px;"></div>
<!-- Search results start -->
<script type="text/javascript">
<!--
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
<c:set var="showUpdateLink" value='N'/>
/*Storing the data to be displayed in a JSON object array.*/

var rowSpanCols = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38];
var rowSpanLvl2Cols = [15,16];
var rowSpanLvl2Map = line2Map;
//third level 17,18,19,20,21


function myGetCalendar(rowid)
{
	parentChanged(rowid,null);
	return getCalendar($('startDateDisplay'+rowid),null,null,$('today'));
// do date calculation later.
	var part = cellValue(rowid,'partName');

	var rowid1 = partMap1[part];
	var rowid2 = partMap2[part];

	var rowid0 = rowid;
	var todayval = dateToIntString($v('today'));

	var startdateval1 = dateToIntString($v('startDateDisplay'+rowid1));
	var startdate1  = $v('startDateDisplay'+rowid1);
	var startdateval2 = dateToIntString($v('startDateDisplay'+rowid2));
	var startdate2  = $v('startDateDisplay'+rowid2);
	try {
		if( $('startDateDisplay'+rowid1) == null ) {
			startdate1 = cellValue(rowid1,"startDateDisplay")
			startdateval1 = dateToIntString( startdate1 );
		}
		if( $('startDateDisplay'+rowid2) == null ) {
			startdate2 = cellValue(rowid2,"startDateDisplay")
			startdateval2 = dateToIntString( startdate2 );
		}
	}
	catch(ex){}

	var field = 'startDateDisplay'+rowid0;
	
	if( rowid0 == rowid2 ) {
		$('tmpdate').value = startdate1;
		return getCalendar($('startDateDisplay'+rowid0),null,$('tmpdate'),$('today'));
	}
	if( rowid0 == rowid1 ) {
		if( todayval > startdateval2 )
			return getCalendar($('startDateDisplay'+rowid0),null,null,$('today'));
		else  {
			$('tmpdate').value = startdate2;
			return getCalendar($('startDateDisplay'+rowid0),$('tmpdate'));
		}
	}
	else 
		return getCalendar($('startDateDisplay'+rowid0));
}

var updateSupplyPath = false;

function dupline(rowId){
	if(rowId == null ) {
		rowId = haasGrid.getSelectedRowId();
	}
	var catprice = cellValue(rowId,"unitPrice");
	var oldprice = cellValue(rowId,"oldUnitPrice");
	var parentIndex = haasGrid.haasGetRowSpanStart(rowId);
	var parentIndex2 = haasGrid.haasGetRowSpanStartLvl2(rowId);
	var newLinePosition = haasGrid.haasGetRowSpanEndIndexLvl2(rowId);

	var readonly='N';
	if(updateSupplyPath){
	     readonly='Y';
	}

	var newRowData = [
	    'Y',
	    cellValue(rowId,"roundToMultiplePermission"),
	    readonly,
		cellValue(rowId,"inventoryGroup"),
		cellValue(rowId,"itemId"),
		cellValue(rowId,"itemDesc"),
		cellValue(rowId,"shipToLocationId"),
		cellValue(rowId,"inventoryGroupName"),
		cellValue(rowId,"priority"),
		cellValue(rowId,"supplier"),
		cellValue(rowId,"supplierName"),
		cellValue(rowId,"supplierDisplay"),
		cellValue(rowId,"supplierPartNo"),
		cellValue(rowId,"leadTimeDays"),
		cellValue(rowId,"status"),
		'',//startDateDisplay
		cellValue(rowId,"currencyId"),
        'Y', //breakQuantityPermission
		cellValue(rowId,"breakQuantity"),
		catprice,//cellValue(rowId,"catalogPrice"),
		'', //priceLastUpdatedBy
        '', //priceLastUpdatedDate
        cellValue(rowId,"multipleOf"),
        cellValue(rowId,"minBuyQuantity"),
        cellValue(rowId,"minBuyValue"),
        cellValue(rowId,"supplyPath"),
        cellValue(rowId,"remainingShelfLifePercent"),
        cellValue(rowId,"freightOnBoard"), //incoterms
		cellValue(rowId,"carrierName"),
		'',//carrierLookup
		cellValue(rowId,"criticalOrderCarrierName"),
		'',//criticalOrderCarrierLookup
		cellValue(rowId,"buyerName"),
		'',//buyerLookup
		cellValue(rowId,"roundToMultiple"),
		cellValue(rowId,"supplierContactName"),
		'',//supplierContactLookup
		cellValue(rowId,"updateByName"),
		cellValue(rowId,"updatedDate"),
		cellValue(rowId,"startDate"),
		cellValue(rowId,"oldStartDate"),
		cellValue(rowId,"startDateString"),
		cellValue(rowId,"updatedDateTime"),
		cellValue(rowId,"endDate"),
		cellValue(rowId,"oldbreakQuantity"),
		cellValue(rowId,"oldUnitPrice"),
		cellValue(rowId,"carrier"),
		cellValue(rowId,"criticalOrderCarrier"),
		cellValue(rowId,"buyer"),
		cellValue(rowId,"roundToMultiple"),
		cellValue(rowId,"supplierContactId"),
		cellValue(rowId,"dbuyContractId"),
		cellValue(rowId,"shipToCompanyId"),
		cellValue(rowId,"sourcer"),
		cellValue(rowId,"loadingComments"),
		cellValue(rowId,"opsEntityId"),
		cellValue(rowId,"inventoryGroupHub"),
		cellValue(rowId,"opsCompanyId"),
		'N',
		'N',
		'N',
		'N',
		'Y'];

	status = itemStatusArray;
	haasGrid.haasAddRowToRowSpan(newRowData,newLinePosition,parentIndex,parentIndex2);
	modificationsMade = true;
	resetMarkedRows();

	haasGrid.haasRenderRow(haasGrid.getRowId(parentIndex));
	haasGrid.selectRow(parentIndex);
}

function newFutureCpp(){
	var rowId = haasGrid.getSelectedRowId();
	var part = cellValue(rowId,'dbuyContractId');
	var catprice = cellValue(rowId,"unitPrice");	

	var parentIndex = haasGrid.haasGetRowSpanStart(rowId);
	var newLinePosition = haasGrid.haasGetRowSpanEndIndex(rowId);

	var readonly='N';
	if(updateSupplyPath){
	     readonly='Y';
	}

	var newRowData = [
	    'Y',
	    cellValue(rowId,"roundToMultiplePermission"),
	    readonly,
		cellValue(rowId,"inventoryGroup"),
		cellValue(rowId,"itemId"),
		cellValue(rowId,"itemDesc"),
		cellValue(rowId,"shipToLocationId"),
		cellValue(rowId,"inventoryGroupName"),
		cellValue(rowId,"priority"),
		cellValue(rowId,"supplier"),
		cellValue(rowId,"supplierName"),
		cellValue(rowId,"supplierDisplay"),
		cellValue(rowId,"supplierPartNo"),
		cellValue(rowId,"leadTimeDays"),
		cellValue(rowId,"status"),
		'newStartDate', //startDateDisplay;
		cellValue(rowId,"currencyId"),
		'N', //breakQuantityPermission
        '1', //breakQuantity
        catprice,//catalogPrice
        '', //priceLastUpdatedBy
        '', //priceLastUpdatedDate
		cellValue(rowId,"multipleOf"),
		cellValue(rowId,"minBuyQuantity"),
        cellValue(rowId,"minBuyValue"),
        cellValue(rowId,"supplyPath"),
        cellValue(rowId,"remainingShelfLifePercent"),
        cellValue(rowId,"freightOnBoard"), //incoterms
		cellValue(rowId,"carrierName"),
		'',//carrierLookup
		cellValue(rowId,"criticalOrderCarrierName"),
		'',//criticalOrderCarrierLookup
		cellValue(rowId,"buyerName"),
		'',//buyerLookup
		cellValue(rowId,"roundToMultiple"),
		cellValue(rowId,"supplierContactName"),
		'',//supplierContactLookup
		cellValue(rowId,"updateByName"),
		cellValue(rowId,"updatedDate"),
		$v('today'),//cellValue(rowId,"startDate"),
		'',//cellValue(rowId,"oldStartDate"),
		$v('todayString'),//cellValue(rowId,"startDateString"),
		cellValue(rowId,"updatedDateTime"),
		'',//cellValue(rowId,"endDate"),
		'',//cellValue(rowId,"oldbreakQuantity"),
		'',//cellValue(rowId,"oldUnitPrice"),
		cellValue(rowId,"carrier"),
		cellValue(rowId,"criticalOrderCarrier"),
		cellValue(rowId,"buyer"),
		cellValue(rowId,"oldRoundToMultiple"),
		cellValue(rowId,"supplierContactId"),
		cellValue(rowId,"dbuyContractId"),
		cellValue(rowId,"shipToCompanyId"),
		cellValue(rowId,"sourcer"),
		cellValue(rowId,"loadingComments"),
		cellValue(rowId,"opsEntityId"),
		cellValue(rowId,"inventoryGroupHub"),
		cellValue(rowId,"opsCompanyId"),
		'Y',
		'N',
		'N',
		'New',
		'Y'];

	status = itemStatusArray;
	haasGrid.haasAddRowToRowSpan(newRowData,newLinePosition, parentIndex);
	modificationsMade = true;

	haasGrid.haasRenderRow(newLinePosition);
	newLinePosition++;
	var datehtml = '<input class="inputBox pointer" id="startDateDisplay'+newLinePosition+'" type="text" value="'+$v('today')+'" size="8" readonly onClick="return myGetCalendar('+newLinePosition+')"/>';
	cell(newLinePosition,"startDateDisplay").setCValue(datehtml);

	resetMarkedRows();
	status = itemStatusArray;
	haasGrid.haasRenderRow(haasGrid.getRowId(parentIndex));
	haasGrid.selectRow(parentIndex);	
}



var children = new Array();
function pp(name) {
	var value = $v(name);
//	alert( value );
	return encodeURIComponent(value);
}

function gg(name,rowId) {
	if(rowId ==null ) rowId = selectedRowId;
	var value = null;
	value = cellValue(rowId,name);
//	alert( value );
	return encodeURIComponent(value);
}

function showItemNotes() {
	var loc = "/tcmIS/supply/edititemnotes.do?itemId=" + gg('itemId');
   	var winId= 'showItemNotes';
   	
	children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"800","600","yes","50","50","20","20","no");
}

function checkPriority() {
	showTransitWin("");
 	callToServer("splchangepriority.do?uAction=checkPriorityCount&itemId="+gg('itemId')+"&opsEntityId="+$v('opsEntityId')+
				"&inventoryGroup="+gg('inventoryGroup')+"&shipToCompanyId="+gg('shipToCompanyId')+"&shipToLocationId="+gg('shipToLocationId'));
}

function changePriority(popUp) {
	if(popUp == 'N') {
		closeTransitWin();
		return false;
	}
	else {	 	
		var loc = "splchangepriority.do?uAction=search&itemId="+gg('itemId')+"&opsEntityId="+$v('opsEntityId')+
					"&inventoryGroup="+gg('inventoryGroup')+"&shipToCompanyId="+gg('shipToCompanyId')+"&shipToLocationId="+gg('shipToLocationId');
	   	var winId= 'changePriority';
	   	
		children[children.length] = openWinGeneric(loc,winId,"800","350","yes","50","50","20","20","no");
	}
}


var newcontractid = 1;
var newpartrowid = null;
var priorityrowid = null;

function additem(iteminfo) {
	var rowId = newpartrowid;
	if( !rowId ) return additem2(iteminfo);
		
	var ind = haasGrid.getRowIndex(rowId);
	var parentIndex = ind;
	var newLinePosition = ind + 1; 

	var newlineind = newLinePosition;
	priorityrowid = newLinePosition;

	var carrierhtml = '<input class="lookupBtn" type="button" onmouseover="this.className='+"'"+'lookupBtn lookupBtnOver'+"'"+'" onmouseout="this.className='+"'"+'lookupBtn'+"'"+'" value="" onclick="getCarrier('+priorityrowid+')"/>'+
					  '<input class="smallBtns" type="button" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" value="<fmt:message key="label.clear"/>" onclick="clearCarrier('+priorityrowid+')"/>';
	var criticalOrderCarrierhtml = '<input class="lookupBtn" type="button" onmouseover="this.className='+"'"+'lookupBtn lookupBtnOver'+"'"+'" onmouseout="this.className='+"'"+'lookupBtn'+"'"+'" value="" onclick="getCriticalOrderCarrier('+priorityrowid+')"/>'+
					  '<input class="smallBtns" type="button" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" value="<fmt:message key="label.clear"/>" onclick="clearCriticalOrderCarrier('+priorityrowid+')"/>';
	var buyerhtml = '<input class="lookupBtn" type="button" onmouseover="this.className='+"'"+'lookupBtn lookupBtnOver'+"'"+'" onmouseout="this.className='+"'"+'lookupBtn'+"'"+'" value="" onclick="getBuyer('+priorityrowid+')"/>'+
					  '<input class="smallBtns" type="button" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" value="<fmt:message key="label.clear"/>" onclick="clearBuyer('+priorityrowid+')"/>';
    var supplierhtml = '<input class="lookupBtn" type="button" onmouseover="this.className='+"'"+'lookupBtn lookupBtnOver'+"'"+'" onmouseout="this.className='+"'"+'lookupBtn'+"'"+'" value="" onclick="getSupplierContact('+priorityrowid+')"/>'+
					  '<input class="smallBtns" type="button" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" value="<fmt:message key="label.clear"/>" onclick="clearSupplierContact('+priorityrowid+')"/>';



  	callToServer("supplierpricelistresults.do?uAction=getPriority&itemId=" + iteminfo.itemId +
			"&opsEntityId=" + $v('opsEntityId')+
			"&inventoryGroup=" + iteminfo.inventoryGroup +
			"&shipToCompanyId=" + iteminfo.shipToCompanyId +
			"&shipToLocationId=" + iteminfo.shipToLocationId);
	var readonly='N';
	if(updateSupplyPath){
	     readonly='Y';
	}

	var newRowData = [
	    'Y',
	    cellValue(rowId,"roundToMultiplePermission"),
	    readonly,
		cellValue(rowId,"inventoryGroup"),
		iteminfo.itemId,
		iteminfo.itemDesc,
		iteminfo.shipToLocationId,
		cellValue(rowId,"inventoryGroupName"),
		'1', //priority
		iteminfo.supplier,
		iteminfo.supplierName,
		iteminfo.supplier+"-"+iteminfo.supplierName,
		'', //supplierpartno
		'', //leadTimeDays
		'DBUY',//status
		$v('today'), //startDateDisplay
		iteminfo.currencyId,
		'N', //breakQuantityPermission
        '1', //cellValue(rowId,"breakQuantity"),
        '',  //cellValue(rowId,"catalogPrice"),
		'',  //priceLastUpdatedBy
        '',  //priceLastUpdatedDate
		1,   //cellValue(rowId,"multipleOf"),
		'',  //minBuyQuantity
        '',  //minBuyValue
        'Purchaser',//supplyPath
        '',  //remainingShelfLife
		'',  //incoterms
		'', //carrierName
		carrierhtml,//carrierLookup
		criticalOrderCarrierhtml,//criticalOrderCarrierName
		'',//criticalOrderCarrierLookup
		iteminfo.buyerName,
		buyerhtml,//buyerLookup
		'&nbsp;',// round to multiple
		'',//supplierContactName
		supplierhtml,//supplierLookup
		'',//updatebyname
		'',//updatedDate
		'',//cellValue(rowId,"startDate"),
		'',//cellValue(rowId,"oldStartDate"),
		'',//cellValue(rowId,"startDateString"),
		'',//cellValue(rowId,"updatedDateTime"),
		'',//cellValue(rowId,"endDate"),
		'',//cellValue(rowId,"oldbreakQuantity"),
		'',//cellValue(rowId,"oldUnitPrice"),
		'',//carrier
		'',//criticalOrderCarrier
		iteminfo.buyer,
		'REVIEW',//oldRoundToMultiple
		'',//supplierContactId
		'-'+(newcontractid++),//iteminfo.dbuyContractId,
		iteminfo.shipToCompanyId,//shipToCompanyId
		'',//sourcer
		'',//loadingComments
		$v('opsEntityId'),
		cellValue(rowId,"inventoryGroupHub"),
		'HAAS',//opsCompanyId
		'Y',
		'Y',
		'New',
		'New',
		'Y'];

	status = itemStatusArray;
	haasGrid.haasAddRowToRowSpan(newRowData,newLinePosition);
	modificationsMade = true;
	
	haasGrid.haasRenderRow(newLinePosition);
	newLinePosition++;

	var datehtml = '<input class="inputBox pointer" id="startDateDisplay'+newLinePosition+'" type="text" value="'+$v('today')+'" size="8" readonly onClick="return myGetCalendar('+newLinePosition+')"/>';
	cell(newLinePosition,"startDateDisplay").setCValue(datehtml);
		  
	partMap1[partinfo.priceGroupId+"-"+partinfo.partName+"-"+partinfo.specListId] = newLinePosition;

	resetMarkedRows();


	haasGrid.haasRenderRow(haasGrid.getRowId(newLinePosition));
	haasGrid.selectRow(newLinePosition);
}

//Add a new item
function additem2(iteminfo) {
	var size = haasGrid.getRowsNum();
	var newid = 1;

	if( !size ) {
		lineMap3[0] = 0;
		lineMap[0] = 1;
		line2Map[0] = 1;
		priorityrowid = newid;
	}
	else {
		newid = size;
		priorityrowid = newid + 1;
	}
	
    $('updateResultLink').style['display'] = '';

	var carrierhtml = '<input class="lookupBtn" type="button" onmouseover="this.className='+"'"+'lookupBtn lookupBtnOver'+"'"+'" onmouseout="this.className='+"'"+'lookupBtn'+"'"+'" value="" onclick="getCarrier('+priorityrowid+')"/>'+
		'<input class="smallBtns" type="button" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" value="<fmt:message key="label.clear"/>" onclick="clearCarrier('+priorityrowid+')"/>';
	var criticalOrderCarrierhtml = '<input class="lookupBtn" type="button" onmouseover="this.className='+"'"+'lookupBtn lookupBtnOver'+"'"+'" onmouseout="this.className='+"'"+'lookupBtn'+"'"+'" value="" onclick="getCriticalOrderCarrier('+priorityrowid+')"/>'+
		'<input class="smallBtns" type="button" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" value="<fmt:message key="label.clear"/>" onclick="clearCriticalOrderCarrier('+priorityrowid+')"/>';
	var buyerhtml = '<input class="lookupBtn" type="button" onmouseover="this.className='+"'"+'lookupBtn lookupBtnOver'+"'"+'" onmouseout="this.className='+"'"+'lookupBtn'+"'"+'" value="" onclick="getBuyer('+priorityrowid+')"/>'+
		'<input class="smallBtns" type="button" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" value="<fmt:message key="label.clear"/>" onclick="clearBuyer('+priorityrowid+')"/>';
    var supplierhtml = '<input class="lookupBtn" type="button" onmouseover="this.className='+"'"+'lookupBtn lookupBtnOver'+"'"+'" onmouseout="this.className='+"'"+'lookupBtn'+"'"+'" value="" onclick="getSupplierContact('+priorityrowid+')"/>'+
					  '<input class="smallBtns" type="button" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" value="<fmt:message key="label.clear"/>" onclick="clearSupplierContact('+priorityrowid+')"/>';

  	callToServer("supplierpricelistresults.do?uAction=getPriority&itemId=" + iteminfo.itemId +
			"&opsEntityId=" + $v('opsEntityId')+
			"&inventoryGroup=" + iteminfo.inventoryGroup +
			"&shipToCompanyId=" + iteminfo.shipToCompanyId +
			"&shipToLocationId=" + iteminfo.shipToLocationId);

	var readonly='N';
	if(updateSupplyPath){
	     readonly='Y';
	}

	var newRowData = [
	    'Y',
	    'N',
	    readonly,
		iteminfo.inventoryGroup,// cellValue(rowId,"inventoryGroup"),
		iteminfo.itemId,
		iteminfo.itemDesc,
		iteminfo.shipToLocationId,
		iteminfo.inventoryGroupName,//cellValue(rowId,"inventoryGroupName"),
		'1',//priority
		iteminfo.supplier,
		iteminfo.supplierName,
		iteminfo.supplier+"-"+iteminfo.supplierName,
		'',//supplierPartNo
		'',//leadTimeDays
        'DBUY',//status
        $v('today'), // startDateDisplay
        iteminfo.currencyId,
        'N', //breakQuantityPermission
        '1',//cellValue(rowId,"breakQuantity"),
        '',//catprice,//cellValue(rowId,"catalogPrice"),
        '',  //priceLastUpdatedBy
        '',  //priceLastUpdatedDate
        1,//cellValue(rowId,"multipleOf"),
        '',//minBuyQuantity
        '',//minBuyValue
        'Purchaser',//supplyPath
        '',//remainingShelfLife
        '',//incoterms
        '',//carrierName
		carrierhtml,//carrierLookup
		criticalOrderCarrierhtml,//criticalOrderCarrierName
		'',//criticalOrderCarrierLookup
		iteminfo.buyerName,
		buyerhtml,//buyerLookup
		'&nbsp;',// round to multiple
		'',//supplierContactName
		supplierhtml,//supplierLookup
		'',//updatebyname
		'',//updatedDate
		'',//cellValue(rowId,"startDate"),
		'',//cellValue(rowId,"oldStartDate"),
		'',//cellValue(rowId,"startDateString"),
		'',//cellValue(rowId,"updatedDateTime"),
		'',//cellValue(rowId,"endDate"),
		'',//cellValue(rowId,"oldbreakQuantity"),
		'',//cellValue(rowId,"oldUnitPrice"),
		'',//carrier
        '',//criticalOrderCarrier
		iteminfo.buyer,
		'REVIEW',//oldRoundToMultiple
		'',//supplierContactId
		'-'+(newcontractid++),//iteminfo.dbuyContractId,
		iteminfo.shipToCompanyId,//shipToCompanyId
		'',//sourcer
		'',//loadingComments
		$v('opsEntityId'),
		iteminfo.inventoryGroupHub,
		'HAAS',//opsCompanyId
		'Y',
		'Y',
		'New',
		'New',
		'Y'];

	status = itemStatusArray;
	haasGrid.haasAddRowToRowSpan(newRowData,newid);
	modificationsMade = true;

	haasGrid.haasRenderRow(newid);
	resetMarkedRows();
	haasGrid.selectRow(newid);

	newid++;

  	var datehtml = '<input class="inputBox pointer" id="startDateDisplay'+newid+'" type="text" value="'+$v('today')+'" size="8" readonly onClick="return getCalendar($('+"'startDateDisplay"+newid+"'),null,null,$('today'))"+'"/>';
	cell(newid,"startDateDisplay").setCValue(datehtml);
	var html = '<input type="button" class="smallBtns" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" name="editButton" value="'+messagesData.dupline+'"  onclick="dupline('+newid+')"/>';
	cell(newid,"dup").setCValue(html);

	partMap1[partinfo.priceGroupId+"-"+partinfo.partName+"-"+partinfo.specListId] = newid;
}

function setNewRowPriority(priority) {
	var priorityRowIndex = priorityrowid -1;
	var max = haasGrid.getRowsNum()
	for (var rowIndex = 0; rowIndex < max; rowIndex++) {
		if(rowIndex != priorityRowIndex && jsonMainData.rows[rowIndex].data[61] == 'New') {
			if (jsonMainData.rows[rowIndex].data[3] == jsonMainData.rows[priorityRowIndex].data[3] && // Inventory Group
			    jsonMainData.rows[rowIndex].data[4] == jsonMainData.rows[priorityRowIndex].data[4] && // Item Id
			    jsonMainData.rows[rowIndex].data[8] == jsonMainData.rows[priorityRowIndex].data[8] && // Ship To Location Id
			    jsonMainData.rows[rowIndex].data[54] == jsonMainData.rows[priorityRowIndex].data[54] && // Ship To Company Id
			    jsonMainData.rows[rowIndex].data[10] >= priority) {
				priority = (jsonMainData.rows[rowIndex].data[10] * 1) + 1
			}
		}
	}
	if (priority > 1) {
		if (!haasGrid.haasRowIsRendered(priorityrowid)) {
			haasGrid.haasRenderRow(priorityrowid);
		}
		cell(priorityrowid, "priority").setValue(priority);
	}
}

function newitem(rowid0){

	showTransitWin('<fmt:message key="label.pricelist"/>');
	if( rowid0 == null ) rowid0 = selectedRowId;
	
	var ind = haasGrid.getRowIndex(rowid0);

	var shipid = gg("shipToLocationId");
    if( shipid == 'All' ) shipid = '';
	var loc = "newitem.do?uAction=new&shipToId=" + shipid+ 
	"&inventoryGroup=" + gg("inventoryGroup")+
	"&supplier=" + $v("supplier")+
	"&supplierName=" +  encodeURIComponent( $v("supplierName") )+
	"&companyId=Radian";
	
	var winId= 'newitem';//+$v("prNumber");
	children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"500","310","yes","50","50","20","20","no");
	newpartrowid = rowid0;
	return ;
		
}


var map = null;
var lineMap = new Array();
var line2Map = new Array();
var partMap1 = new Array();
var partMap2 = new Array();

<c:set var="gridind" value="0"/>
<c:set var="prePar" value=""/>
<c:set var="samePartCount" value="0"/>
<c:set var="colorIndex" value="-1"/>
<c:forEach var="bean" items="${beanCollection}" varStatus="status">
	<c:set var="curPar" value="${bean.dbuyContractId}"/>
	<tcmis:jsReplace value="${curPar}" var="jsCurPar"/>
	<c:if test="${ curPar == prePar }">
		<c:set var="samePartCount" value="${samePartCount+1}"/>
		partMap2['${jsCurPar}'] = partMap1['${jsCurPar}'];
		partMap1['${jsCurPar}'] = ${gridind+1};
	</c:if>
	<c:if test="${ curPar != prePar }">
		<c:set var="samePartCount" value="1"/>
		<c:set var="firstLineIndex" value="${gridind}"/>
		<c:set var="colorIndex" value="${colorIndex+1}"/>
		partMap1['${jsCurPar}'] = ${gridind+1};
	</c:if>
	
	<bean:size collection="${status.current.priceBreakCollection}" id="resultSize"/>
    lineMap3[${gridind}] = ${colorIndex%2} ;
	line2Map[${gridind}] = ${resultSize+1};
	<c:if test="${ curPar == prePar }">
		lineMap[${firstLineIndex}] += ${resultSize+1};
	</c:if>
	<c:if test="${ curPar != prePar }">
		lineMap[${gridind}] = ${resultSize+1};
		map = new Array();
	</c:if>
    <c:set var="gridind" value="${gridind+1}"/> 

    <c:forEach var="bean2" items="${status.current.priceBreakCollection}" varStatus="status2">
		lineMap3[${gridind}] = ${colorIndex%2} ;
	<c:set var="gridind" value="${gridind+1}"/> 
	</c:forEach>
	<c:set var="prePar" value="${curPar}"/>
</c:forEach>

<c:set var="prePar" value=""/>

<c:set var="todayval">
	<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="yyyy-MM-dd"/>
</c:set>
		
<tcmis:opsEntityPermission indicator="true" userGroupId="supplierPriceList" opsEntityId="${param.opsEntityId}">
	<c:set var="permission" value="Y"/>
</tcmis:opsEntityPermission>
<tcmis:opsEntityPermission indicator="true" userGroupId="SPLsupplyPath" opsEntityId="${param.opsEntityId}">
	<c:set var="supplyPathPermission" value="Y"/>
</tcmis:opsEntityPermission>






var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${beanCollection}" varStatus="status">
<c:set var="isGrand" value="N"/>
<tcmis:jsReplace value="${curPar}" var="jsCurPar"/>
<c:set var="curPar" value="${bean.dbuyContractId}"/>
<c:if test="${ curPar != prePar }">
<c:set var="isGrand" value="Y"/>
</c:if>
<fmt:formatDate var="startDatev" value="${bean.startDate}" pattern="${dateFormatPattern}"/>

<fmt:setLocale value="en_US"/>
<fmt:formatDate var="startDateString" value="${bean.startDate}" pattern="${dateFormatPattern}"/>
<fmt:setLocale value="${pageLocale}" scope="page"/>

<fmt:formatDate var="startDate" value="${bean.startDate}" pattern="${dateFormatPattern}"/>
<bean:size collection="${status.current.priceBreakCollection}" id="resultSize"/>

<c:if test="${dataCount > 0}">,</c:if>
<c:set var="dataCount" value='${dataCount+1}'/>
<c:set var="dup">
<%--
<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="editButton" value="<fmt:message key="receiving.label.duplicateline"/>" onclick="dupline(${dataCount})" />
--%>
<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="editButton" value="Add CPP" onclick="dupline(${dataCount})" />
</c:set>

<fmt:formatDate var="startDateVal" value="${bean.startDate}" pattern="yyyy-MM-dd"/>
<c:if test="${ startDateVal > todayval }">
	<c:set var="startDate">
		<input class="inputBox pointer" id="startDateDisplay${dataCount}" type="text" value="${startDate}" size="9" readonly onClick="return myGetCalendar(${dataCount})"/>
	</c:set>
</c:if>

<tcmis:jsReplace value="${dup}" var="dup"/>

<c:set var="carrierDisplay" value=""/>
<c:if test="${permission == 'Y'}">
<c:set var="carrierDisplay">
	<input class="lookupBtn" type="button" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" value="" onclick="getCarrier(${dataCount})"/>
	<input class="smallBtns" type="button" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" value="<fmt:message key="label.clear"/>" onclick="clearCarrier(${dataCount})"/> 
</c:set>
</c:if>
<tcmis:jsReplace var="carrierDisplay" value="${carrierDisplay}"  processMultiLines="true" />

<c:set var="buyerDisplay" value=""/>
<c:if test="${permission == 'Y'}">
<c:set var="buyerDisplay">
	<input class="lookupBtn" type="button" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" value="" onclick="getBuyer(${dataCount})"/>
	<input class="smallBtns" type="button" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" value="<fmt:message key="label.clear"/>" onclick="clearBuyer(${dataCount})"/> 
</c:set>
</c:if>
<tcmis:jsReplace var="buyerDisplay" value="${buyerDisplay}"  processMultiLines="true" />

<c:set var="supplierContactDisplay" value=""/>
<c:if test="${permission == 'Y'}">
<c:set var="supplierContactDisplay">
	<input class="lookupBtn" type="button" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" value="" onclick="getSupplierContact(${dataCount})"/>
	<input class="smallBtns" type="button" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" value="<fmt:message key="label.clear"/>" onclick="clearSupplierContact(${dataCount})"/> 
</c:set>
</c:if>
<tcmis:jsReplace var="supplierContactDisplay" value="${supplierContactDisplay}"  processMultiLines="true" />

<c:set var="consignment" value='false'/>
<c:if test="${ bean.consignment == 'Y' }">
	<c:set var="consignment" value='true'/>
</c:if>

<tcmis:jsReplace value="${bean.itemDesc}" var="itemDesc" processMultiLines="true"/>
<fmt:formatDate var="updatedDate" value="${bean.updatedDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="priceUpdatedDate" value="${bean.priceUpdatedDate}" pattern="${dateFormatPattern}"/>
<c:set var="bstatus" value='DBUY' />
<c:if test="${bean.status == 'INACTIVE'}"><c:set var="bstatus" value='INACTIVE' /></c:if>
<c:choose>
<c:when test="${status.index % 2 == 0}" >
  <c:set var="colorClass" value='grid_white'/>
</c:when>
<c:otherwise>
   <c:set var="colorClass" value='grid_lightblue'/>
</c:otherwise>
</c:choose>
<c:if test="${bstatus == 'INACTIVE'}">
  <c:set var="colorClass" value='grid_lightgray'/>
</c:if>

{ id:${dataCount},'class':"${colorClass}",
	  data:[
           '${permission}',
           <c:choose>
           <c:when test="${bean.multipleOf != 1 && bean.multipleOf != 0 && !empty bean.multipleOf}">
                   'Y',
           </c:when>
           <c:otherwise>
                   'N',
           </c:otherwise>
           </c:choose>
           '${supplyPathPermission}',
           '${bean.inventoryGroup}',
           '${bean.itemId}',
           '${itemDesc}',
           '${bean.shipToLocationId}',
           '<tcmis:jsReplace value="${bean.inventoryGroupName}" />',
           '${bean.priority}',
           '<tcmis:jsReplace value="${bean.supplier}"/>',
           '<tcmis:jsReplace value="${bean.supplierName}"/>',
           '<tcmis:jsReplace value="${bean.supplier}" processMultiLines="true"/>-<tcmis:jsReplace value="${bean.supplierName}" processMultiLines="true"/>',
           '<tcmis:jsReplace value="${bean.supplierPartNo}"/>',
           '${bean.leadTimeDays}',
           '${bean.status}',
           '<fmt:formatDate value="${bean.startDate}" pattern="${dateFormatPattern}"/>',
           '${bean.currencyId}',
           'N',
           '1',
           '${bean.unitPrice}',
           '<tcmis:jsReplace value="${bean.priceUpdatedByName}"/>',
           '<fmt:formatDate value="${bean.priceUpdatedDate}" pattern="${dateFormatPattern}"/>',
           '${bean.multipleOf}',
           '${bean.minBuyQuantity}',
           '${bean.minBuyValue}',
           '${bean.supplyPath}',
           '${bean.remainingShelfLifePercent}',
           '${bean.freightOnBoard}',
           '<tcmis:jsReplace value="${bean.carrierName}"/>',
           '${carrierDisplay}',
           '<tcmis:jsReplace value="${bean.criticalOrderCarrierName}"/>',
           '${criticalOrderCarrierDisplay}',
           '<tcmis:jsReplace value="${bean.buyerName}"/>',
           '${buyerDisplay}',
            <c:if test="${bean.multipleOf != 1}">
                   '${bean.roundToMultiple}',
            </c:if>
            <c:if test="${bean.multipleOf == 1}">
                   '&nbsp;',
            </c:if>
           '<tcmis:jsReplace value="${bean.supplierContactName}"/>',
           '${supplierContactDisplay}',
           '<tcmis:jsReplace value="${bean.updateByName}"/>',
           '<fmt:formatDate value="${bean.updatedDate}" pattern="${dateFormatPattern}"/>',
           '<fmt:formatDate value="${bean.startDate}" pattern="${dateFormatPattern}"/>',
           '<fmt:formatDate value="${bean.startDate}" pattern="${dateFormatPattern}"/>',
           '<fmt:formatDate value="${bean.startDate}" pattern="${dateFormatPattern}"/>',
           '${bean.updatedDate.time}',
           '',
           '1',
           '${bean.unitPrice}',
           '<tcmis:jsReplace value="${bean.carrier}"/>',
           '<tcmis:jsReplace value="${bean.criticalOrderCarrier}"/>',
           '${bean.buyer}',
           '${bean.roundToMultiple}',
           '<tcmis:jsReplace value="${bean.supplierContactId}"/>',
           '${bean.dbuyContractId}',
           '${bean.shipToCompanyId}',
           '${bean.sourcer}',
           '<tcmis:jsReplace var="carrierDisplay" value="${bean.loadingComments}" processMultiLines="true" />',
           '${bean.opsEntityId}',
           '${bean.inventoryGroupHub}',
           '${bean.opsCompanyId}',
           'Y',
           '${isGrand}',
           '',
           '',
           ''
	   ]
	 }

  <c:forEach var="bean2" items="${status.current.priceBreakCollection}" varStatus="status2">
  <c:if test="${dataCount > 0}">,</c:if>
  <c:set var="dataCount" value='${dataCount+1}'/>
  <c:set var="dup">
  <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="editButton" value="Add CPP" onclick="dupline(${dataCount})" />
  </c:set>
  <tcmis:jsReplace value="${dup}" var="dup"/>
  <fmt:formatDate var="updatedDate" value="${bean.updatedDate}" pattern="${dateFormatPattern}"/>
  <c:set var="bstatus" value='DBUY' />
  <c:if test="${bean.status == 'INACTIVE'}"><c:set var="bstatus" value='INACTIVE' /></c:if>

  { id:${dataCount},
	  data:[
           '${permission}','N',
           '${supplyPathPermission}',
           '${bean.inventoryGroup}',
           '${bean.itemId}',
           '${itemDesc}',
           '${bean.shipToLocationId}',
           '<tcmis:jsReplace value="${bean.inventoryGroupName}" />',
           '${bean.priority}',
           '<tcmis:jsReplace value="${bean.supplier}"/>',
           '<tcmis:jsReplace value="${bean.supplierName}"/>',
           '<tcmis:jsReplace value="${bean.supplier}" processMultiLines="true"/>-<tcmis:jsReplace value="${bean.supplierName}" processMultiLines="true"/>',
           '<tcmis:jsReplace value="${bean.supplierPartNo}"/>',
           '${bean.leadTimeDays}',
           '${bean.status}',
           '<fmt:formatDate value="${bean.startDate}" pattern="${dateFormatPattern}"/>',
           '${bean.currencyId}',
           '${permission}',
           '${bean2.breakQuantity}',
           '${bean2.unitPrice}',
           '<tcmis:jsReplace value="${bean2.updatedBy}"/>',
           '<fmt:formatDate value="${bean2.updatedDate}" pattern="${dateFormatPattern}"/>',
           '${bean.multipleOf}',
           '${bean.minBuyQuantity}',
           '${bean.minBuyValue}',
           '${bean.supplyPath}',
           '${bean.remainingShelfLifePercent}',
           '${bean.freightOnBoard}',
           '<tcmis:jsReplace value="${bean.carrierName}"/>',
           '${carrierDisplay}',
           '<tcmis:jsReplace value="${bean.criticalOrderCarrierName}"/>',
           '${criticalOrderCarrierDisplay}',
           '<tcmis:jsReplace value="${bean.buyerName}"/>',
           '${buyerDisplay}',
           '&nbsp;',
           '<tcmis:jsReplace value="${bean.supplierContactName}"/>',
           '${supplierContactDisplay}',
           '<tcmis:jsReplace value="${bean.updateByName}"/>',
           '<fmt:formatDate value="${bean.updatedDate}" pattern="${dateFormatPattern}"/>',
           '<fmt:formatDate value="${bean.startDate}" pattern="${dateFormatPattern}"/>',
           '<fmt:formatDate value="${bean.startDate}" pattern="${dateFormatPattern}"/>',
           '<fmt:formatDate value="${bean.startDate}" pattern="${dateFormatPattern}"/>',
           '${bean.updatedDate.time}',
           '',
           '${bean2.breakQuantity}',
           '${bean2.unitPrice}',
           '<tcmis:jsReplace value="${bean.carrier}"/>',
           '<tcmis:jsReplace value="${bean.criticalOrderCarrier}"/>',
           '${bean.buyer}',
           '${bean.roundToMultiple}',
           '<tcmis:jsReplace value="${bean.supplierContactId}"/>',
           '${bean.dbuyContractId}',
           '${bean.shipToCompanyId}',
           '${bean.sourcer}',
           '<tcmis:jsReplace var="carrierDisplay" value="${bean.loadingComments}" processMultiLines="true" />',
           '${bean.opsEntityId}',
           '${bean.inventoryGroupHub}',
           '${bean.opsCompanyId}',
           'N',
           'N',
           '',
           '',
           ''
	   ]
	 }

  </c:forEach>

<c:set var="prePar" value="${curPar}"/>
</c:forEach>
]};

var rowSpanLvl2Map = line2Map;
//-->
</script>

<!-- end of grid div. -->

<!-- If the collection is empty say no data found 
<c:if test="${empty beanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>  -->
<!-- Search results end -->
</div>

 <%-- result count and time --%>
 <div id="footer" class="messageBar"></div>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>


</div><!-- Result Frame Ends -->

<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="dataCount" id="dataCount" value="${dataCount}" type="hidden"/>
<input type="hidden" name="uAction" id="uAction" value="update"/>
<input type="hidden" name="supplier" id="supplier" value="${param.supplier}"/>
<input type="hidden" name="supplierName" id="supplierName" value="${param.supplierName}"/>
<input type="hidden" name="inventoryGroup" id="inventoryGroup" value="${param.inventoryGroup}"/>
<input type="hidden" name="hub" id="hub" value="${param.hub}"/>
<input type="hidden" name="searchField" id="searchField" value="${param.searchField}"/>
<input type="hidden" name="searchMode" id="searchMode" value="${param.searchMode}"/>
<input type="hidden" name="searchArgument" id="searchArgument" value="${param.searchArgument}"/>
<input type="hidden" name="showExpired" id="showExpired" value="${param.showExpired}"/>
<input name='today' id='today' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'  />
<input name='todayString' id='todayString' type="hidden" value='${startDateString}'  />
<input type="hidden" name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}"/>
<input type="hidden" name="opsCompanyId" id="opsCompanyId" value="${param.opsCompanyId}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input type="hidden" name="tmpdate" id="tmpdate" value=""/>
<input name="minHeight" id="minHeight" type="hidden" value="210"/>

</div>

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

<div id="transitDailogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
</div>
<div id="transitDailogWinBody" class="errorMessages" style="display: none;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" id="transitLabel">
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="/images/rel_interstitial_loading.gif" align="middle"/>
			</td>
		</tr>
	</table>
</div>


<c:if test="${permission == 'Y'}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
</script>
</c:if>

 <script type="text/javascript">
 <!--
  var selectedRowId=null;
  var modificationsMade = false;

  function rightClick(rowId, cellInd){
	<c:choose>
		<c:when test="${permission != 'Y'}">
			toggleContextMenu('contextMenu');
		</c:when>
		<c:otherwise>
			popdown();
			selectedRowId = rowId;
			
			var menuLines = new Array();
			menuLines[menuLines.length] = "text="+messagesData.itemnotes+";url=javascript:showItemNotes();";
			if (!modificationsMade) {
				menuLines[menuLines.length] = "text="+messagesData.changepriority+";url=javascript:checkPriority();";
			}
			menuLines[menuLines.length] = "text="+messagesData.supplieritemnotes+";url=javascript:showSupplierItemNotes();";
			menuLines[menuLines.length] = "text="+messagesData.editsupplimentdata+";url=javascript:editSupplimentoryData();";
			/*menuLines[menuLines.length] = "text="+messagesData.batchupdate+";url=javascript:doBatchUpdateBuyer();";*/
	//		menuLines[menuLines.length] = "text="+messagesData.duplicateitem+";url=javascript:duplicateItem();";
            
			if (cellInd >= haasGrid.getColIndexById('startDateDisplay')) {
				menuLines[menuLines.length] = "text="+messagesData.newstartdate+";url=javascript:newFutureCpp();";
				menuLines[menuLines.length] = "text="+messagesData.addeditadditcharges+";url=javascript:editAdditionalCharges();";
				if (cellInd >= haasGrid.getColIndexById('breakQuantity')) { 
					menuLines[menuLines.length] = "text="+messagesData.newpricebreak+";url=javascript:dupline();";
				}
			}

			replaceMenu('addcpp',menuLines);
			toggleContextMenu('addcpp');
		</c:otherwise>
	</c:choose>
}

function editAdditionalCharges(rowId){
   var loc = "dbuyadditionalcharges.do?action=search"+
             "&dbuyContractId=" + gg('dbuyContractId') +
             "&startDateString=" + gg('startDateString')+
             "&currencyId=" + gg('currencyId');
                  
     openWinGeneric(loc,"editAdditionalCharges","500","400","yes","50","50","20","20","no");
}

function showSupplierItemNotes() {
    var title = "showSupplierItemNotes_" + gg('itemId');
	var loc = "/tcmIS/supply/showsupplieritemnotes.do?" +
		"uAction=search" +
		"&opsEntityId=" + $v('opsEntityId') +
		"&searchMode=is" +
		"&searchField=itemId" +
		"&searchArgument=" + gg('itemId')+
		"&itemId=" + gg('itemId')+
		"&itemDesc=" + gg('itemDesc')+
		"&supplier=" + gg('supplier') +
		"&supplierName=" + gg('supplierName');
		   	
	children[children.length] = openWinGeneric(loc,title,"800","600","yes","50","50","20","20","no");
}

function editSupplimentoryData() {
   	var loc = "/tcmIS/distribution/editsupplimentorydata.do?uAction=editsupplimentorydata&dbuyContractId=" + gg('dbuyContractId');
	var winId= 'editSupplimentoryData';
   	children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"500","525","yes","50","50","20","20","no");
}

var supplyPathPermission = 'N';
<tcmis:opsEntityPermission indicator="true" userGroupId="SPLsupplyPath" opsEntityId="${param.opsEntityId}">
	supplyPathPermission = 'Y';
</tcmis:opsEntityPermission> 

function doBatchUpdateBuyer() {

	var loc = "batchupdatebuyer.do?uAction=new&shipToId=" + 
	"&inventoryGroup=" + gg("inventoryGroup")+
	"&hub=" + gg('inventoryGroupHub')+
	"&opsEntityId=" + gg("opsEntityId")+
	"&supplier=" + gg("itemId") +
	"&itemId=" + gg("supplier") +
	"&itemDesc=" + gg("itemDesc") +
	"&supplierName=" + gg('supplierName')+
	"&companyId=Radian"; 
	
	loc += "&supplyPathPermission="+supplyPathPermission;
	
	children[children.length] = openWinGeneric(loc,'batchUpdateBuyer',"650","580","yes","50","50","20","20","no");
}



var gridConfig = {
		divName:'SupperPriceListBean', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not.
		noSmartRender:false,
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
		onRightClick:rightClick,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
		selectChild: 2 // Double select single row
};

 //-->
</script>


</body>
</html>
