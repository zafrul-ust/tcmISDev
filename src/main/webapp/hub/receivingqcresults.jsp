<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="expires" content="-1">
    <link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
    <%@ include file="/common/locale.jsp" %>
    <!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>
 
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/receivingqcresults.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/js/hub/bindata.js"></script>

<title>
    <fmt:message key="label.receivingqc"/> 
</title>
<script language="JavaScript" type="text/javascript">
<!--
var disabledPoLink = false;
<tcmis:featureReleased feature="DisabledPOLink" scope="ALL"  companyId="${personnelBean.companyId}">
	disabledPoLink = true;
</tcmis:featureReleased>

var disabledItemNotes = false;
<tcmis:featureReleased feature="DisabledItemNotes" scope="ALL"  companyId="${personnelBean.companyId}">
	disabledItemNotes = true;
</tcmis:featureReleased>

with(milonic=new menuname("receivingqcMenu")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="receivinghistory.label.approved.potitle"/>;url=javascript:showrecforinvtransfrQc();");
 aI("text=<fmt:message key="receivinghistory.label.approved.itemtitle"/>;url=javascript:showPreviousReceivedQc();");
 aI("text=<fmt:message key="label.receiptspecs"/>;url=javascript:receiptSpecs();");
 aI("text=<fmt:message key="pickingqc.viewaddreceipts"/>;url=javascript:showProjectDocuments();");
}

drawMenus();

//add all the javascript messages here, this for internationalization of client side javascript messages

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
recordFound:'<fmt:message key="label.recordFound"/>',
searchDuration:'<fmt:message key="label.searchDuration"/>',
minutes:'<fmt:message key="label.minutes"/>',
seconds:'<fmt:message key="label.seconds"/>',
validvalues:'<fmt:message key="label.validvalues"/>',
dor:"<fmt:message key="receivedreceipts.label.dor"/>",
expiredate:"<fmt:message key="label.expiredate"/>",
all:"<fmt:message key="label.all"/>",
mfglot:"<fmt:message key="label.mfglot"/>",
forreceipt:"<fmt:message key="label.forreceipt"/>",
incoming:"<fmt:message key="label.incoming"/>",
transferreceiptid:"<fmt:message key="label.transferreceiptid"/>",
bin:"<fmt:message key="label.bin"/>",
qtybeingreceived:"<fmt:message key="label.qtybeingreceived"/>",
qtyreceivednotmatch:"<fmt:message key="label.qtyreceivednotmatch"/>",
packagedqtyreceived:"<fmt:message key="label.packagedqtyreceived"/>",
checkpackagedsize:"<fmt:message key="label.checkpackagedsize"/>",
lotstatus:"<fmt:message key="label.lotstatus"/>",
cannotbe:"<fmt:message key="label.cannotbe"/>",
incoming:"<fmt:message key="label.incoming"/>",
viewpurchaseorder:"<fmt:message key="label.viewpurchaseorder"/>", 
nopermissionstoqcstatus:"<fmt:message key="label.nopermissionstoqcstatus"/>",
cannotselectreceiptwith:"<fmt:message key="label.cannotselectreceiptwith"/>",
differentmlitem:"<fmt:message key="label.differentmlitem"/>",
pendingnewchemrequest:"<fmt:message key="label.pendingnewchemrequest"/>",
nopermissiontochangestatus:"<fmt:message key="label.nopermissiontochangestatus"/>",
cannotselectreceiptwith:"<fmt:message key="label.cannotselectreceiptwith"/>",
differentmlitem:"<fmt:message key="label.differentmlitem"/>",
pendingnewchemrequest:"<fmt:message key="label.pendingnewchemrequest"/>",
labelquantity:"<fmt:message key="label.labelquantity"/>",
mustBeInteger:"<fmt:message key="label.errorinteger"/>",
mustbeanumberinthisfield:"<fmt:message key="label.mustbeanumberinthisfield"/>",
actsupshpdate:"<fmt:message key="label.actsupshpdate"/>",
potitle:"<fmt:message key="receivinghistory.label.approved.potitle"/>",
itemtitle:"<fmt:message key="receivinghistory.label.approved.itemtitle"/>",
receiptspecs:"<fmt:message key="label.receiptspecs"/>",
viewaddreceipts:"<fmt:message key="pickingqc.viewaddreceipts"/>",
viewcustomerreturnrequest:"<fmt:message key="label.viewcustomerreturnrequest"/>",
viewrma:"<fmt:message key="label.viewrma"/>",
itemnotes:"<fmt:message key="itemnotes.title"/>",
receivingchecklist:"<fmt:message key="label.receivingchecklist"/>",
customerreturnrequest:"<fmt:message key="customerreturnrequest.title"/>",
indefinite:"<fmt:message key="label.indefinite"/>",
expdatelessthanminexpdate:"<fmt:message key="label.expdatelessthanminexpdate"/>",
qastatement:"<fmt:message key="label.qastatement"/>",
norowselected:"<fmt:message key="label.norowselected"/>",
sendinghubwillbealtered:"<fmt:message key="label.sendinghubwillbealtered"/>",
startmarstest:"<fmt:message key="label.startmarstest"/>",
groupReceiptDoc:"<fmt:message key="label.groupReceiptDoc"/>",
shipbeforemanufacture:'<fmt:message key="label.shipbeforemanufacture"/>',
dom:"<fmt:message key="receivedreceipts.label.dom"/>",
dor:"<fmt:message key="receivedreceipts.label.dor"/>",
dos:"<fmt:message key="label.manufacturerdos"/>",
marsDetail:"<fmt:message key="label.marsdetail"/>",    
showMarsDetail:"<fmt:message key="label.showmarsdetail"/>",
noCompletedIncomingTest:"<fmt:message key="label.nocompletedincomingtest"/>",    
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
receivingqcdchecklist:"<fmt:message key="receivingQcCheckList"/>"
};

var lotStatus = [
    <c:forEach var="bean" items="${vvLotStatusBeanCollection}" varStatus="status">
        <c:set var="jspLabel" value=""/>
        <c:if test="${fn:length(status.current.jspLabel) > 0}"><c:set var="jspLabel">${status.current.jspLabel}</c:set></c:if>
        <c:if test="${ status.index !=0 }">,</c:if>
        {
            text:       '<fmt:message key="${jspLabel}"/>',
            value:      '${bean.lotStatus}',
            pickable:   '${bean.pickable}'
        }
    </c:forEach>
];


var gridConfig = {
	divName:'ReceivingQcBean', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, 
	rowSpan:true,			 // this page has rowSpan > 1 or not.
	submitDefault:true,    // the fields in grid are defaulted to be submitted or not.,
	noSmartRender: false,
	singleClickEdit:true,
	selectChild: 1,
	onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:selectRow   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	//onBeforeSorting:_onBeforeSorting
  }; 



  
 <tcmis:inventoryGroupPermission indicator="true" userGroupId="ReceivingQC" inventoryGroup="${param.inventoryGroup}">
   <c:set var="receivingQcPermission" value='Yes'/>
 </tcmis:inventoryGroupPermission>

 
<c:if test="${!empty receivingQcViewRelationBeanCollection}">  
	<c:if test="${receivingQcPermission == 'Yes'}">
	    showUpdateLinks = true;
	 </c:if>
</c:if>


var config = [

{ columnId:"radianPoDisplay",columnName:'<fmt:message key="label.po"/>',width:5},
{ columnId:"poLine",columnName:'<fmt:message key="label.poline"/>',width:3},
{ columnId:"itemId",columnName:'<fmt:message key="label.item"/>',width:5},
{ columnId:"inventoryGroupName",columnName:'<fmt:message key="label.inventorygroup"/>'},
{ columnId:"lineDesc",columnName:'<fmt:message key="label.description"/>',width:17,tooltip:"Y"},
{ columnId:"specs",columnName:'<fmt:message key="label.receiptspecs"/>',width:5},
<c:choose>
	<c:when test="${receivingQcPermission == 'Yes'}">
	{ columnId:"mfgLotPermission"},
	{ columnId:"mfgLot",columnName:'<fmt:message key="label.mfglot"/>',type:'hed',permission:true, size:15, width:15},
	{ columnId:"origMfgLot",columnName:'<fmt:message key="receivingqc.label.origlot"/>'},
	{ columnId:"okPermission"},
	{ columnId:"ok",columnName:'<fmt:message key="label.ok"/>',type:'hch',width:3,permission:true,onChange:checkChemicalReceivingQcInput},
	{ columnId:"lotStatusPermission"},
	{ columnId:"lotStatus",columnName:'<fmt:message key="label.lotstatus"/>',type:'hcoro',width:15,permission:true,onChange:lotStatusChanged},
	{ columnId:"vendorShipDateDisplay",columnName:'<fmt:message key="label.actsupshpdate"/>'},
	{ columnId:"dateOfReceiptDisplay",columnName:'<fmt:message key="receivedreceipts.label.dor"/>'},
	{ columnId:"dateOfManufactureDisplay",columnName:'<fmt:message key="receivedreceipts.label.dom"/>'},
	{ columnId:"dateOfShipmentDisplay",columnName:'<fmt:message key="label.manufacturerdos"/>'},
	{ columnId:"minimumExpireDateDisplay",columnName:'<fmt:message key="label.minexpdate"/>'},
	{ columnId:"expireDateDisplay",columnName:'<fmt:message key="label.expdate"/>'},
	{ columnId:"expireDateStr"},
	{ columnId:"binPermission"},
	{ columnId:"bin",columnName:'<fmt:message key="label.bin"/>',type:'hdoro',width:11,permission:true},
	{ columnId:"newQuantityReceivedPermission"},
	{ columnId:"newQuantityReceived",columnName:'<fmt:message key="label.quantityreceived"/>',align:"center",type:'hed',width:5,permission:true},
	{ columnId:"packaging",columnName:'<fmt:message key="label.packaging"/>',width:15,tooltip:"Y"},
	{ columnId:"closePoLinePermission"},
	{ columnId:"closePoLine",columnName:'<fmt:message key="receiving.label.closepoline"/>',align:"center",type:'hch',width:4,permission:true},
	{ columnId:"receiptId",columnName:'<fmt:message key="label.receiptid"/>',align:"center",width:7},
	{ columnId:"transferReceiptId",columnName:'<fmt:message key="receivingqc.label.transreceiptid"/>',align:"center",width:5},
	{ columnId:"labelQuantityPermission"},
	{ columnId:"labelQuantity",columnName:'<fmt:message key="label.#labels"/>',width:5,type:'hed',permission:true},
	{ columnId:"packagedQty",columnName:'<fmt:message key="receiving.label.packagedqty"/>x<fmt:message key="receiving.label.packagedsize"/>',align:"center"},
	{ columnId:"notesPermission"},
	{ columnId:"notes",columnName:'<fmt:message key="label.notes"/>',type:'txt', tooltip:"Y",permission:true},
	{ columnId:"deliveryTicketPermission"},
	{ columnId:"deliveryTicket",columnName:'<fmt:message key="receiving.label.deliveryticket"/>',width:13,type:'hed',permission:true},
	{ columnId:"qualityTrackingNumberPermission"},
	{ columnId:"qualityTrackingNumber",columnName:'<fmt:message key="label.addqualitynote"/>',width:13,type:'hed',permission:true},
	{ columnId:"unitLabelPrintedPermission"},
	{ columnId:"unitLabelPrinted",columnName:'<fmt:message key="label.unitlabeledper129p"/>',type:'hch',width:4,permission:true},
	{ columnId:"reverseReceiptDisplay",columnName:'<fmt:message key="label.reverse"/>',align:"center"},
	</c:when>
	<c:otherwise>
	{ columnId:"mfgLotPermission"},
	{ columnId:"mfgLot"},
	{ columnId:"origMfgLot"},
	{ columnId:"okPermission"},
	{ columnId:"ok"},
	{ columnId:"lotStatusPermission"},
	{ columnId:"lotStatus"},
	{ columnId:"vendorShipDateDisplay"},
	{ columnId:"dateOfReceiptDisplay"},
	{ columnId:"dateOfManufactureDisplay"},
	{ columnId:"dateOfShipmentDisplay"},
	{ columnId:"minimumExpireDateDisplay"},
	{ columnId:"expireDateDisplay"},
	{ columnId:"expireDateStr"},
	{ columnId:"binPermission"},
	{ columnId:"bin"},
	{ columnId:"newQuantityReceivedPermission"},
	{ columnId:"newQuantityReceived"},
	{ columnId:"packaging"},
	{ columnId:"closePoLinePermission"},
	{ columnId:"closePoLine"},
	{ columnId:"receiptId"},
	{ columnId:"transferReceiptId"},
	{ columnId:"labelQuantityPermission"},
	{ columnId:"labelQuantity"},
	{ columnId:"packagedQty"},
	{ columnId:"notesPermission"},
	{ columnId:"notes"},
	{ columnId:"deliveryTicketPermission"},
	{ columnId:"deliveryTicket"},
	{ columnId:"qualityTrackingNumberPermission"},
	{ columnId:"qualityTrackingNumber"},
	{ columnId:"unitLabelPrintedPermission"},
	{ columnId:"unitLabelPrinted"},
	{ columnId:"reverseReceiptDisplay"},
	</c:otherwise>
</c:choose>
{ columnId:"radianPo"},
{ columnId:"poLine"},
{ columnId:"catalogId"},
{ columnId:"catPartNo"},
{ columnId:"catalogCompanyId"},
{ columnId:"unitLabelCatPartNo"},
{ columnId:"transferRequestId"},
{ columnId:"branchPlant"},
{ columnId:"inventoryGroup"},
{ columnId:"intercompanyPo"},
{ columnId:"intercompanyPoLine"},
{ columnId:"incomingTesting",columnName:'<fmt:message key="label.incomingtesting"/>',align:"center"},
{ columnId:"receiptShelfLifeBasis"},
{ columnId:"receiptGroup"},
{ columnId:"newChemRequestId"},
{ columnId:"customerRmaId"},
{ columnId:"qcOk"},
{ columnId:"itemType"},
{ columnId:"manageKitsAsSingleUnit"},
{ columnId:"mvItem"},
{ columnId:"docType"},
{ columnId:"origLotStatus"},
{ columnId:"lotStatusRootCause"},
{ columnId:"lotStatusRootCauseNotes"},
{ columnId:"responsibleCompanyId"},
{ columnId:"qualityControlItem"},
{ columnId:"updateStatus"},
{ columnId:"dateOfManufacture"},
{ columnId:"vendorShipDate"},
{ columnId:"dateOfShipment"},
{ columnId:"dateOfReceipt"},
{ columnId:"expireDate"},
{ columnId:"minimumExpireDate"},
{ columnId:"quantityReceived"},
{ columnId:"costFactor"},
{ columnId:"shipToCompanyId"},
{ columnId:"labTestComplete"},
{ columnId:"amendment"}
];	

var hdoroA = {};

function onselectHdoro(selectId, selectedOptVal) {
	hdoroA[selectId] = selectedOptVal;
}

first = true;
function eXcell_hdoro(cell){                                    
	 if (cell){                                                     
	     this.cell = cell;
	     this.grid = this.cell.parentNode.grid;
	 };
	 this.setValue=function(val){
		var columnId = this.grid.getColumnId(this.cell._cellIndex);
		var pColIndex = null;
		if ((typeof multiplePermissions != 'undefined') && (typeof permissionColumns != 'undefined')) {
			if (typeof permissionColumns[columnId] != 'undefined') {
				pColIndex = this.grid.getColIndexById(columnId + 'Permission');
			} else {
				pColIndex = this.grid.getColIndexById('permission');
			}
		} else {		
			pColIndex = this.grid.getColIndexById('permission');
		}
		var permission;
		if (pColIndex == undefined) {
			permission = 'N';
		} else {
			permission = this.grid.cellById(this.cell.parentNode.idd,pColIndex).getValue();
		}
		var selectId="" + columnId+ this.cell.parentNode.idd;
		if (permission == 'Y') {
//			hdoroA[selectId] = val;
			var selectValue = "<select class='selectBox' id='" + selectId + "' onchange=\"onselectHdoro('" +  selectId + "',this.options[this.selectedIndex].value);\">";	
			try {
				selectValue = selectValue + "<option value='" + val +  "' selected='selected'>" + val + "</option>";
//				var optArray = eval(columnId);
//				for (i=0; i < optArray.length;i++) {
//					if (val == optArray[i]["value"])
//						selectValue = selectValue + "<option value='" + optArray[i]["value"] +  "' selected='selected'>" + optArray[i]["text"] + "</option>";
//					else
//						selectValue = selectValue + "<option value='" + optArray[i]["value"] +  "'>" + optArray[i]["text"] + "</option>";
//				}
				selectValue = selectValue + "</select>"
				this.setCValue(selectValue);
//
			} catch(err) {
				alert("Column with id " + columnId + " has type as hdoro but no global array named " + columnId +" is defined for the select options.");
				return;
			}
		} else {
			var optText = val;	
			var columnId = this.grid.getColumnId(this.cell._cellIndex);		
			try {
				var optArray = eval(columnId);
				for (i=0; i < optArray.length;i++) {
					if (val == optArray[i]["value"]) {
						optText =  optArray[i]["text"];
					}
				}
			} catch(err) {};
			if (optText.length == 0) {
				this.setCValue("<label id='" + selectId + "'>&nbsp;</label>",optText);
			} else {
				this.setCValue("<label id='" + selectId + "'>" + optText + "</label>",optText);
			}
		}
	 };
	 this.getValue=function(){
		var columnId = this.grid.getColumnId(this.cell._cellIndex);
		var pColIndex = null;
		if ((typeof multiplePermissions != 'undefined') && (typeof permissionColumns != 'undefined')) {
			if (typeof permissionColumns[columnId] != 'undefined') {
				pColIndex = this.grid.getColIndexById(columnId + 'Permission');
			} else {
				pColIndex = this.grid.getColIndexById('permission');
			}
		} else {		
			pColIndex = this.grid.getColIndexById('permission');
		}
		var permission;
		if (pColIndex == undefined) {
			permission = 'N';
		} else {
			permission = this.grid.cellById(this.cell.parentNode.idd,pColIndex).getValue();
		}
		var selectId="" + columnId+ this.cell.parentNode.idd;
		if (permission == 'Y') {
			return $v(selectId);		
		} else {
			return $(selectId).innerHTML.trim(); // get value			
		}
	 };
	 this.edit=function(){};
	 this.detach=function(){
		 return false;
	 };
}
eXcell_hdoro.prototype = new eXcell;    // nest all other methods from base class


var loaded = new Array();
var loadedIndex = new Array();
var url = null;

function loadBins(itemId,hub,index)
{  
    if( loadedIndex[index] ) {
	   return;
   }
   
   if( loaded[itemId] ) {
       setBins(index,loaded[itemId]);
	   return; 
   } 
   url = "/tcmIS/hub/receivingqcresults.do?userAction=loaddata&index="+index+"&itemId="+itemId+"&hub="+hub+"&callback=processReqChangeJSON";
   callToServer(url);
}

function processReqChangeJSON(xmldoc) 
{   
    var bins = loaded[xmldoc.itemId] = xmldoc.bins;
    setBins(xmldoc.index,bins); 
}

function setBins(index,bins) { 
	var opts = $('bin'+index).options;
	var val  = $('bin'+index).value;
	for(var i = 0 ; i < bins.length; i++ ) {
		if( val != bins[i] ) opts[opts.length] = new Option(bins[i],bins[i]);
	}
	loadedIndex[index] = true;
}
// end of dynamic load drop down code

function getBin(value,text,rowid) {
	   obj = document.getElementById("bin"+rowid);
	   var index = obj.length;
	   obj.options[index]=new Option(text,value);
	   obj.options[index].selected = true; 
}

function checkaddbins(){
	{
		//var loc = "/tcmIS/hub/showhubbin.do?callbackparam="+selectedRowId+"&branchPlant=" + $v('hub') + "&userAction=showBins";
		var loc = "showhubbin.do?callbackparam="+selectedRowId+"&branchPlant=" + $v('hub') + "&userAction=showBins";
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/hub/" + loc;
		
		var winname = null;
		try {
			winname = openWinGeneric(loc, "showVvHubBins", "300", "150", "no", "80", "80");
			children[children.length] = winname;
			} catch (ex) {
//			openWinGeneric(loc, "showVvHubBins", "300", "150", "no", "80", "80");
		}
	}
}


function checklinks()
{
  if(showUpdateLinks)
  {
     parent.document.getElementById("chemicalResultLink").style["display"] = "";
     parent.document.getElementById("nonChemicalResultLink").style["display"] = "none";   
  }
}


//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);checklinks();">
<tcmis:form action="/receivingqcresults.do" onsubmit="return submitFrameOnlyOnce();">
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>

//-->
</script>
<!-- Error Messages Ends -->  

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="ReceivingQcBean" style="width:100%;height:400px;" style="display: none;"></div>
<c:set var="colorClass" value=''/>
<c:if test="${empty receivingQcViewRelationBeanCollection}">
		<%-- If the collection is empty say no data found --%> 
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
			<tr>
				<td width="100%"><fmt:message key="main.nodatafound" /></td>
			</tr>
		</table>
</c:if>
<c:if test="${!empty receivingQcViewRelationBeanCollection}">
<script type="text/javascript">
<!--
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
<c:set var="showUpdateLink" value='N'/>

var lineMap = new Array();
var lineMap3 = new Array();
//Date Of Manufacture
function myGetCalendarM(rowid)
{
	var rowid0 = rowid;
	return getCalendar($('dateOfManufactureDisplay'+rowid0),null,$('tomorrow'),null,$('dateOfShipmentDisplay'+rowid0));
}
function setMChanged(rowid) {
	cell(rowid,"dateOfManufacture").setValue($v('dateOfManufactureDisplay'+rowid));
	
}
//Vendor Ship Date
function myGetCalendarVS(rowid)
{
	var rowid0 = rowid;
	return getCalendar($('vendorShipDateDisplay'+rowid0),null,$('tomorrow'),null,$('dateOfReceiptDisplay'+rowid0));
}
function setVSChanged(rowid)
{
	cell(rowid,"vendorShipDate").setValue($v('vendorShipDateDisplay'+rowid));
	
}

//Manufacture date of shipment
function myGetCalendarS(rowid)
{
	var rowid0 = rowid;
	return getCalendar($('dateOfShipmentDisplay'+rowid0),null,$('tomorrow'),$('dateOfManufactureDisplay'+rowid0),null);
}
function setSChanged(rowid) {
	cell(rowid,"dateOfShipment").setValue($v('dateOfShipmentDisplay'+rowid));
}

//date of Receive
function myGetCalendarR(rowid)
{
	var rowid0 = rowid;
	return getCalendar($('dateOfReceiptDisplay'+rowid0),null,null,$('day60'),$('tomorrow'));
}
function setRChanged(rowid) {
	cell(rowid,"dateOfReceipt").setValue($v('dateOfReceiptDisplay'+rowid));
}

//expire Date
function myGetCalendarE(rowid)
{
	var rowid0 = rowid;	
	//return getCalendar($('expireDateDisplay'+rowid0),null,null,$('dateOfReceipt'+rowid0),$v('todayoneyear'),$('dateOfReceipt'+rowid0),'Y');
	return getCalendar($('expireDateDisplay'+rowid0),null,null,$v('todayoneyear'),null,'Y');		
}
function setExpDateChanged(rowid) {
	cell(rowid,"expireDateStr").setValue($v('expireDateDisplay'+rowid));
}

function reverseReceipt(rowid) {
	var receiptId = cellValue(rowid,"receiptId");
    if (receiptId.trim().length > 0) {
		/* loc = "/tcmIS/hub/showreversereceipt.do?receiptId=";
		loc = loc + receiptId; */
		
		var loc = "showreversereceipt.do?receiptId=" + receiptId;
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/hub/" + loc;
		
		try {
			children[children.length] = openWinGeneric(loc,
					"Reverse_Receiving", "300", "150", "no")
		} catch (ex) {
			openWinGeneric(loc, "Reverse_Receiving", "300", "150", "no")
		}
		return true;
	}
	return false;
}


<c:set var="gridind" value="0"/>

<c:forEach var="bean" items="${receivingQcViewRelationBeanCollection}" varStatus="status">
	
	<c:set var="colorIndex" value="${colorIndex+1}"/>
	
	<bean:size collection="${bean.kitCollection}" id="resultSize"/>
	<c:if test="${resultSize > 0}">
	    lineMap[${gridind}]  = ${resultSize};
	    <c:forEach var="bean2" items="${bean.kitCollection}" varStatus="status2">
			lineMap3[${gridind}] = ${colorIndex%2} ;
			<c:set var="gridind" value="${gridind+1}"/> 
	    </c:forEach>
	</c:if>
	<c:if test="${resultSize == 0}">
		lineMap[${gridind}]  = 1;
		lineMap3[${gridind}] = ${colorIndex%2};
		<c:set var="gridind" value="${gridind+1}"/>	
	</c:if>

</c:forEach>

/*Storing the data to be displayed in a JSON object array.*/
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${receivingQcViewRelationBeanCollection}" varStatus="status">
	     	     
	     <c:set var="kitCollection"  value='${bean.kitCollection}'/>
	     <bean:size id="listSize" name="kitCollection"/>
		 <c:set var="mvItem" value='${bean.mvItem}'/>
		 <c:set var="manageKitsAsSingleUnit" value='${bean.manageKitsAsSingleUnit}'/>
		 <c:set var="docType" value='${bean.docType}'/>
		 <c:set var="qualityControlItem" value='${bean.qualityControlItem}'/>
				  		 
		 <c:set var="inventoryGroupPermission" value=''/>
		  <tcmis:inventoryGroupPermission indicator="true" userGroupId="ReceivingQC" inventoryGroup="${status.current.inventoryGroup}">
		   <c:set var="inventoryGroupPermission" value='Yes'/>
		  </tcmis:inventoryGroupPermission>
		
		  <c:set var="statusUpdatePermission" value=''/>
		  <tcmis:inventoryGroupPermission indicator="true" userGroupId="PickStatusUpd" inventoryGroup="${status.current.inventoryGroup}">
		   <c:set var="statusUpdatePermission" value='Yes'/>
		  </tcmis:inventoryGroupPermission>
		
		  <c:set var="onlynonPickableStatusPermission" value=''/>
		  <tcmis:inventoryGroupPermission indicator="true" userGroupId="onlynonPickableStatus" inventoryGroup="${status.current.inventoryGroup}">
		   <c:set var="onlynonPickableStatusPermission" value='Yes'/>
		  </tcmis:inventoryGroupPermission>
		  
		   <c:set var="checkBoxPermission" value='N'/>
			     <tcmis:inventoryGroupPermission indicator="true" userGroupId="transferReconciliation" inventoryGroup="${status.current.inventoryGroup}">
				   <c:set var="checkBoxPermission" value='Y'/>
			</tcmis:inventoryGroupPermission> 
			
								  
				 
	    <c:choose>
		<c:when test="${status.index % 2 == 0}" >
		  <c:set var="colorClass" value='grid_white'/>
		</c:when>
		<c:otherwise>
		   <c:set var="colorClass" value='grid_lightblue'/>
		</c:otherwise>
		</c:choose>
		  <c:set var="critical" value='${status.current.critical}'/>
		  <c:if test="${critical == 'Y' || critical == 'y'}">
		   <c:set var="colorClass" value='grid_red'/>
		  </c:if>
		
		  <c:if test="${critical == 'S' || critical == 's'}">
		   <c:set var="colorClass" value='grid_pink'/>
		  </c:if>
		
		  <c:set var="excess" value='${status.current.excess}'/>
		  <c:if test="${excess == 'YES' || excess == 'Yes'}">
		   <c:set var="colorClass" value='grid_orange'/>
		  </c:if>
		
		  <c:if test="${status.current.itemType == 'ML'}">
		   <c:set var="colorClass" value='grid_green'/>
		  </c:if>
		  
		  <c:set var="updateStatus" value='${status.current.updateStatus}'/>
		  <c:if test="${updateStatus == 'NO' || updateStatus == 'Error'}">
		   <c:set var="colorClass" value='grid_error'/>
		  </c:if>

		  <c:set var="radianPoDisplay" value=""/>
          <c:choose>
		   <c:when test="${docType == 'IT' && ! empty bean.customerRmaId}" >
		     <c:set var="radianPoDisplay"  value="RMA ${bean.customerRmaId}"/>
		   </c:when>      
		   <c:when test="${docType == 'IT'}" >
		   <c:set var="radianPoDisplay"  value="TR ${bean.transferRequestId}"/>
		   </c:when>
		   <c:when test="${docType == 'IA'}" >
		     <c:set var="radianPoDisplay" value="${bean.returnPrNumber}-${bean.returnLineItem}"/>
		   </c:when>
		   <c:when test="${!empty bean.intercompanyPo && !empty bean.intercompanyPoLine}">
		     <c:set var="radianPoDisplay" value="${bean.intercompanyPo} (${bean.radianPo})"/>
		   </c:when>      
		   <c:otherwise>
		   	<c:set var="radianPoDisplay" value="${bean.radianPo}"/>	 
		   </c:otherwise>
		  </c:choose>

		  <c:if test="${fn:length(bean.poNumber) > 0}"><c:set var="radianPoDisplay" value="${radianPoDisplay} (${bean.poNumber})"/></c:if>

		  <c:choose>
		   <c:when test="${docType == 'IT' || docType == 'IA'}">
		    <c:set var="poLineDisplay" value=""/>
		   </c:when>
		   <c:when test="${!empty bean.intercompanyPo && !empty bean.intercompanyPoLine}" >
		    <c:set var="poLineDisplay"  value="${bean.intercompanyPoLine} (${bean.poLine})"/>
		   </c:when>
		   <c:otherwise>
		     <c:set var="poLineDisplay" value="${bean.poLine}"/>
		   </c:otherwise>
		  </c:choose>
	  <bean:size collection="${bean.kitCollection}" id="resultSize"/>
	     <c:if test="${resultSize > 0}">

		<c:forEach var="kbean" items="${bean.kitCollection}" varStatus="kitstatus">
		 <c:set var="kitUpdateStatus" value='${kbean.updateStatus}'/>
		   <c:if test="${kitUpdateStatus == 'NO' || kitUpdateStatus == 'Error'}">
             <c:set var="colorClass" value='error'/>
           </c:if>
         
	      
          <c:if test="${status.current.ok != null}" >
	          <c:set var="checkBoxChecked" value='true'/>
	       </c:if>
	       
	       
             <c:if test="${listSize > 1 && manageKitsAsSingleUnit == 'N'}">
              <c:set var="itemDesc" value='${kbean.materialDesc}'/>
            </c:if>
            
            <c:if test="${manageKitsAsSingleUnit != 'N'}">
              <c:set var="itemDesc" value='${bean.lineDesc}'/>
            </c:if>
            
          
        	         
          <fmt:formatDate var="formattedShipDate" value="${bean.vendorShipDate}" pattern="${dateFormatPattern}"/>
          <fmt:formatDate var="formattedDateOfReceipt" value="${bean.dateOfReceipt}" pattern="${dateFormatPattern}"/>
          <fmt:formatDate var="formattedDateOfManufacture" value="${bean.dateOfManufacture}" pattern="${dateFormatPattern}"/>
          <fmt:formatDate var="formattedDateOfShipment" value="${bean.dateOfShipment}" pattern="${dateFormatPattern}"/>
          <fmt:formatDate var="localeExpireDate" value="${bean.expireDate}" pattern="${dateFormatPattern}"/>
          <fmt:formatDate var="minimumExpireDate" value="${bean.minimumExpireDate}" pattern="${dateFormatPattern}"/>
          <fmt:formatDate var="fmtExpireDate" value="${bean.expireDate}" pattern="MM/dd/yyyy"/>
		          
	        <c:choose>
	          <c:when test="${fmtExpireDate == '01/01/3000'}" >
	             <c:set var="formattedExpirationDate">
	     	   			<fmt:message key="label.indefinite"/>
	     	   	</c:set>
	          </c:when>
	          <c:otherwise>
	             <c:set var="formattedExpirationDate" value="${localeExpireDate}"/>
	          </c:otherwise>
	        </c:choose>    
	        
	         <c:set var="mfgLot"  value="${kbean.mfgLot}" />
	         <c:set var="lotStatus"  value="${bean.lotStatus}" />
	         <c:set var="bin"  value="${bean.bin}" />
	         
	         <c:if test="${status.current.closePoLine != null}" >
              <c:set var="checkCloseBoxChecked" value='true'/>
              </c:if>
	          
		 
          <c:choose> 
            <c:when test="${mvItem != 'Y'}">
                <c:set var="quantityReceived"  value="${kbean.quantityReceived}" />
                <c:set var="labelQuantity"  value="${kbean.quantityReceived}" />
                <c:set var="packagingQuantity"  value="" />
	        </c:when>
	        <c:otherwise>
		       <c:set var="quantityReceived"  value="${bean.totalMvQuantityReceived}" /> 
		       <c:set var="labelQuantity"  value="${kbean.labelQuantity}" />
		       <c:set var="packagingQuantity"  value="${kbean.quantityReceived} X ${kbean.costFactor} ${kbean.purchasingUnitOfMeasure} ${kbean.displayPkgStyle}" />
		    </c:otherwise>
		  </c:choose>
         
            
		<c:if test="${dataCount > 0}">,</c:if>
		<c:set var="dataCount" value='${dataCount+1}'/>
		<c:set var="dateOfManufactureDisplay">
	      <input class="inputBox pointer" id="dateOfManufactureDisplay${dataCount}" type="text" value="${formattedDateOfManufacture}" size="9" readonly onClick="return myGetCalendarM(${dataCount})" onchange="setMChanged(${dataCount})"/>
        </c:set>
        <c:set var="dateOfReceiptDisplay">
		  <input class="inputBox pointer" id="dateOfReceiptDisplay${dataCount}" type="text" value="${formattedDateOfReceipt}" size="9" readonly onClick="return myGetCalendarR(${dataCount})" onchange="setRChanged(${dataCount})"/>
	    </c:set>
        <c:set var="vendorShipDateDisplay">
	      <input class="inputBox pointer" id="vendorShipDateDisplay${dataCount}" type="text" value="${formattedShipDate}" size="9" readonly onClick="return myGetCalendarVS(${dataCount})" onchange="setVSChanged(${dataCount})"/>
        </c:set>
        <c:set var="dateOfShipmentDisplay">
		  <input class="inputBox pointer" id="dateOfShipmentDisplay${dataCount}" type="text" value="${formattedDateOfShipment}" size="9" readonly onClick="return myGetCalendarS(${dataCount})" onchange="setSChanged(${dataCount})"/>
	    </c:set>
	     <c:set var="expireDateDisplay">
		    <input class="inputBox pointer" id="expireDateDisplay${dataCount}" type="text" value="${formattedExpirationDate}" size="9" readonly onClick="return myGetCalendarE(${dataCount})" onchange="setExpDateChanged(${dataCount})"/>
	     </c:set>
	     
	      <c:set var="reverseReceiptDisplay">
		    <input  class="smallBtns" id="reverseButton${dataCount}" type="button"  value="<fmt:message key="label.reverse"/>"  onClick="return reverseReceipt(${dataCount})" />
	      </c:set>
	         
		{ id:${dataCount},'class':"${colorClass}",
		 data:[
		  '${radianPoDisplay}',
		  '${poLineDisplay}',
		  '${bean.itemId}',
		  '${bean.inventoryGroupName}',
		  '<tcmis:jsReplace value="${itemDesc}" processMultiLines="true"/>',
	      '${bean.specs}',  
          <c:choose>
            <c:when test="${inventoryGroupPermission == 'Yes'}">
             'Y',
           </c:when>
            <c:otherwise>
             'N', 
            </c:otherwise>
           </c:choose>
          '${mfgLot}',
          '${bean.origMfgLot}',
	       <c:choose>
	          <c:when test="${(docType == 'IT' && kbean.qcOk == 'N' && checkBoxPermission == 'Y')}" >
	           'Y',
	          </c:when>
	         <c:when test="${!empty kbean.origMfgLot && !(kbean.mfgLot == kbean.origMfgLot)}" >
	          'N',
	         </c:when>
	         <c:otherwise>
	           'Y',
	         </c:otherwise>
		 </c:choose>
         '',
          <c:choose>
            <c:when test="${inventoryGroupPermission == 'Yes' && (statusUpdatePermission == 'Yes'  || qualityControlItem !='Y' ) && onlynonPickableStatusPermission != 'Yes'}">
             'Y', 
            </c:when>
            <c:otherwise>
             'N', 
            </c:otherwise>
           </c:choose>
          '${lotStatus}',
          <c:choose>
            <c:when test="${inventoryGroupPermission == 'Yes'}">
		    '${vendorShipDateDisplay}',
		   </c:when>
            <c:otherwise>
              '${formattedShipDate}', 
            </c:otherwise>
           </c:choose>
            <c:choose>
            <c:when test="${inventoryGroupPermission == 'Yes'}">
		      '${dateOfReceiptDisplay}',
		    </c:when>
            <c:otherwise>
              '${formattedDateOfReceipt}', 
            </c:otherwise>
           </c:choose>
            <c:choose>
            <c:when test="${inventoryGroupPermission == 'Yes'}">
		      '${dateOfManufactureDisplay}',
		      </c:when>
            <c:otherwise>
              '${formattedDateOfManufacture}', 
            </c:otherwise>
           </c:choose>
           <c:choose>
            <c:when test="${inventoryGroupPermission == 'Yes'}">
		     '${dateOfShipmentDisplay}',
		      </c:when>
            <c:otherwise>
              '${formattedDateOfShipment}', 
            </c:otherwise>
           </c:choose> 
           <c:choose>
            <c:when test="${inventoryGroupPermission == 'Yes'}">
		     '${minimumExpireDate}',
		     </c:when>
            <c:otherwise>
              '${formattedExpirationDate}', 
            </c:otherwise>
           </c:choose> 
		    <c:choose>
             <c:when test="${inventoryGroupPermission == 'Yes'}">
             <c:choose>
                <c:when test="${status.current.lockExpireDate != null && status.current.lockExpireDate != 'Y'}">       
                  '${expireDateDisplay}', 		 
                </c:when>
                <c:otherwise>
                  '${formattedExpirationDate}',
                </c:otherwise> 
              </c:choose>
              </c:when>
            <c:otherwise>
              '', 
            </c:otherwise>
           </c:choose> 
          '${formattedExpirationDate}',
           <c:choose>
            <c:when test="${inventoryGroupPermission == 'Yes'}">
             'Y',
           </c:when>
            <c:otherwise>
             'N', 
            </c:otherwise>
           </c:choose>
		  '<tcmis:jsReplace value="${bin}"/>',
		  <c:choose>
           <c:when test="${status.current.ownerCompanyId != null && status.current.docType == 'IA' && status.current.qcOk == 'Y'}">
		   'Y',
		   </c:when>
		   <c:otherwise>
		   'N',
           </c:otherwise>
          </c:choose>
           '${quantityReceived}',
		   '<tcmis:jsReplace value="${kbean.packaging}" processMultiLines="true"/>',
		   <c:choose>
             <c:when test="${(status.current.orderQtyUpdateOnReceipt == 'y' || status.current.orderQtyUpdateOnReceipt == 'Y')}" >
		     'Y',
		     </c:when>
		     <c:otherwise>
		     'N',
            </c:otherwise>
           </c:choose>
          '${checkCloseBoxChecked}',
		 '${bean.receiptId}',
		 '${bean.transferReceiptId}',
		 'Y',
		 <c:choose>
            <c:when test="${inventoryGroupPermission == 'Yes'}">
		    '${labelQuantity}',
		    </c:when>
		     <c:otherwise>
		     '',
            </c:otherwise>
           </c:choose>
		 '${packagingQuantity}',
		 <c:choose>
            <c:when test="${inventoryGroupPermission == 'Yes'}">
             'Y',
           </c:when>
            <c:otherwise>
             'N', 
            </c:otherwise>
           </c:choose>
		 '<tcmis:jsReplace value="${bean.notes}" processMultiLines="true"/>',
		 <c:choose>
            <c:when test="${inventoryGroupPermission == 'Yes'}">
             'Y',
           </c:when>
            <c:otherwise>
             'N', 
            </c:otherwise>
           </c:choose>
		 '${bean.deliveryTicket}',
		 <c:choose>
            <c:when test="${inventoryGroupPermission == 'Yes'}">
             'Y',
           </c:when>
            <c:otherwise>
             'N', 
            </c:otherwise>
           </c:choose>
		 '${bean.qualityTrackingNumber}',
		   <c:choose>
             <c:when test="${kitstatus.current.polchemIg == 'Y' && kitstatus.current.doNumberRequired=='N'}">
		     'Y',
		     </c:when>
		     <c:otherwise>
		     'N',
            </c:otherwise>
          </c:choose>
		 '${bean.unitLabelPrinted}',
		 '${reverseReceiptDisplay}',
		 '${bean.radianPo}',
		 '${bean.poLine}',
		 '${bean.catalogId}',
		 '${bean.catPartNo}',
		 '${bean.catalogCompanyId}',
		 '${bean.unitLabelCatPartNo}',
		 '${bean.transferRequestId}',
		 '${bean.branchPlant}',
		 '${bean.inventoryGroup}',
		 '${bean.intercompanyPo}',
		 '${bean.intercompanyPoLine}',
		 '${bean.incomingTesting}',
		 '${bean.receiptShelfLifeBasis}',
		 '${bean.receiptGroup}',
		 '${bean.newChemRequestId}',
		 '${bean.customerRmaId}',
		 '${bean.qcOk}',
		 '${bean.itemType}',
		 '${bean.manageKitsAsSingleUnit}',
		 '${bean.mvItem}',
		 '${bean.docType}',
		 '${bean.lotStatus}',
		 '${bean.lotStatusRootCause}',
		 '${bean.lotStatusRootCauseNotes}',
		 '${bean.responsibleCompanyId}',
		 '${bean.qualityControlItem}',
		 <c:choose>
            <c:when test="${inventoryGroupPermission == 'Yes'}">
             '',
           </c:when>
            <c:otherwise>
             'readOnly', 
            </c:otherwise>
           </c:choose>
		 '${formattedDateOfManufacture}',
		 '${formattedShipDate}',
		 '${formattedDateOfShipment}',
		 '${formattedDateOfReceipt}',
		 '${localeExpireDate}',
		 '${minimumExpireDate}',
		 '${quantityReceived}',
		 '${bean.costFactor}',
		 '${bean.shipToCompanyId}',
         '${bean.labTestComplete}',
         '${bean.amendment}'
		  ]
		}
		</c:forEach>
    </c:if>  
 </c:forEach>
]};
//-->
</script>
		
</c:if>				

  <!-- Hidden element start --> 
   <div id="hiddenElements" style="display: none;">
   <input type="hidden" name="sourceHubName" id="sourceHubName" value="<c:out value="${selectedHubName}"/>">
   <input type="hidden" name="labelReceipts" id="labelReceipts" value="<c:out value="${labelReceipts}"/>">
   <input type="hidden" name="paperSize" id="paperSize" value="31">
   <input type="hidden" name="userAction" id="userAction" value="">
   <input type="hidden" name="category" id="category" value="${param.category}"/>
   <input type="hidden" name="searchWhat" id="searchWhat" value="${param.searchWhat}"/>
   <input type="hidden" name="search" id="search" value="${param.search}"/>
   <input type="hidden" name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}"/>
   <input type="hidden" name="hub" id="hub" value="${param.sourceHub}"/>
   <input type="hidden" name="sort" id="sort" value="${param.sort}"/>
   <input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
   <input name="minHeight" id="minHeight" type="hidden" value="210"/>
   <input type="hidden" name="selectedItem" id="selectedItem" value="">
   <input type="hidden" name="submitReceive" id="submitReceive" value="">
   <input type="hidden" name="groupReceiptDoc" id="groupReceiptDoc" value="">
   <input name='tomorrow' id='tomorrow' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'/>
   <input name='day60' id='day60' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="-59" datePattern="${dateFormatPattern}"/>'  />
   <input name='todayoneyear' id='todayoneyear' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="-365" datePattern="${dateFormatPattern}"/>'  />
   <input type="hidden" name="personnelCompanyId"  id="personnelCompanyId" value="${personnelBean.companyId}""/>
    

</div>
    <!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>

</body>
</html:html>