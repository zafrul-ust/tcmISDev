<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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

<tcmis:gridFontSizeCss overflow="notHidden"/>
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<%-- This handels the menu style and what happens to the right click on the whole page --%>
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %> 

<%-- For Calendar support --%>
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

 
<%-- Add any other javascript you need for the page here  --%>
<script type="text/javascript" src="/js/distribution/customerreturnrequest.js"></script>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->

<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxCombo/codebase/dhtmlxcombo.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_combo.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
    <fmt:message key="customerreturnrequest.title"/>
</title>

<script language="JavaScript" type="text/javascript"><!--

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
errors:"<fmt:message key="label.errors"/>",  
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
requestQty:"<fmt:message key="label.returnquantityrequested"/>",
newUnitPrice:"<fmt:message key="label.newunitprice"/>",
originalQtyError:"<fmt:message key="error.qtygreater.originalorderqty"/>",
originalShippedQtyError:"<fmt:message key="error.qtygreater.originalshippedqty"/>",
reason:"<fmt:message key="label.return"/> "+" <fmt:message key="label.reason"/>",	
authorizedReturnQty:"<fmt:message key="label.returnquantityauthorized"/>",
errorauthorizedReturnQty:"<fmt:message key="error.authreturnqtygreater.originalorderqty"/>",
price:"<fmt:message key="label.price"/>",
newFeesPrice:"<fmt:message key="label.newfees"/>"+" <fmt:message key="label.price"/>",
noduplicatereason:"<fmt:message key="label.noduplicatereason"/>",
replacementCartPN:"<fmt:message key="label.ourreplacementpn"/>",
noduplicatenewFees:"<fmt:message key="label.noduplicatenewFees"/>",
denialcomments:"<fmt:message key="label.denialcomments"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>", 
needDate:"<fmt:message key="label.needdate"/>",
promisedDate:"<fmt:message key="label.promiseddate"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
norowselected:'<fmt:message key="error.norowselected"/>',
maxlength:'<fmt:message key="errors.maxlength"/>',
sumofactualitemshippedequalreturnquantityrequested:'<fmt:message key="label.sumofactualitemshippedequalreturnquantityrequested"/>',
pleaseselectorenter:'<fmt:message key="label.pleaseselectorenter"/>',
description:'<fmt:message key="label.chargedescription"/>'
};

if( '${deleted}' == 'deleted' ) { 
//	tabId = 'newCustomerRequestDetail'+$v('customerRequestId');
	tabId = window.name.substr(0,window.name.length-5); 
	try { parent.parent.closeTabx(tabId);
	} catch(ex){}
	try { parent.closeTabx(tabId);
	} catch(ex){}
	window.close();
}

<c:set var="hasPermission" value='N'/> 
<c:set var="hasApprovalPermission" value='N'/> 
<c:set var="csrcsrPersonnelIdVal" value="${customerReturnRequestResultBean.customerServiceRepId}"/>
<c:set var="personnedlIdVal" value="${sessionScope.personnelBean.personnelId}"/>
<c:set var="approvalStatusVal" value="${customerReturnRequestResultBean.rmaStatus}"/>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="GenerateOrders" inventoryGroup="${customerReturnRequestResultBean.inventoryGroup}">
  <c:if test="${(approvalStatusVal eq 'Draft') and (csrcsrPersonnelIdVal eq personnedlIdVal)}">
	<c:set var="hasPermission" value='Y'/> 
  </c:if>
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="customerReturnApproval" inventoryGroup="${customerReturnRequestResultBean.inventoryGroup}">
  <c:if test="${(approvalStatusVal eq 'Draft' or approvalStatusVal eq 'Pending Approval')}">
	<c:set var="hasApprovalPermission" value='Y'/> 
  </c:if>
</tcmis:inventoryGroupPermission>

var newFeeConfig = [
{ columnId:"permission"
},                    
{
  	columnId:"itemId",
  	type:"ed"
},
{
	columnId:"oriItemId"
},
{ columnId:"itemIdDisplay",
  columnName:'<fmt:message key="label.chargedescription"/>',
  width:26,
  //type:"hcoro",
  type:"co",
  submit:false
},
{ columnId:"returnPrice",
  columnName:'<fmt:message key="label.price"/>',
  width:8,
  onChange:checkNewFeePrice,
  type:"hed"
},
{ columnId:"currencyId",
  columnName:'<fmt:message key="label.currencyid"/>'
},
{ columnId:"feeDescription"
}
]; 

var reasonConfig = [
{ columnId:"permission"
 
},                    
{columnId:"returnReasonId",
  columnName:'<fmt:message key="label.return"/> <fmt:message key="label.reason"/>',
  type:"hcoro",
  submit:true,
  sorting:"haasCoro",
  width:18
}
]; 

var returnItemConfig = [
{ columnId:"permission"
},  
{columnId:"itemId",
  columnName:'<fmt:message key="label.item"/>',
  submit:true,
  width:6
},       
{columnId:"itemDesc",
  columnName:'<fmt:message key="label.itemdesc"/>',
  submit:true,
  width:20,
  tooltip:"Y"  
},                 
{columnId:"receiptId",
  columnName:'<fmt:message key="label.receiptid"/>',
  submit:true,
  width:6
},
{columnId:"quantity",
  columnName:'<fmt:message key="label.qty"/>',
//  type:'hed',
  submit:true,
//  size:2,
  width:4
},
{columnId:"inventoryQty"
} 

]; 


var originalFeeConfig = [
{columnId:"permission",
 columnName:''
},
{ columnId:"itemDesc",
  columnName:'<fmt:message key="label.feedescription"/>',
  width:22,
  tooltip:"Y"  
},
{ columnId:"type",
  columnName:'<fmt:message key="label.type"/>'   
},
{ columnId:"displayUnitPrice",
  columnName:'<fmt:message key="label.price"/>',
  sorting:"int",
  width:10,
  hiddenSortingColumn:"hUnitPrice"
 
},
{ columnId:"hUnitPrice",
  sorting:"int"
},

{ columnId:"prNumber",
  submit:true
},
{ columnId:"lineItem",
  submit:true
},
{ columnId:"itemId",
  submit:true
},
{ columnId:"quantity",
  submit:true
},
{ columnId:"currencyId",
  submit:true
},
{ columnId:"unitPrice",
  submit:true
}

];
  
//reason section  section start
var returnReasonId = new Array(	
//		{text:messagesData.pleaseselect,value:''}
		 <c:forEach var="vvReasonBeanCollection" items="${vvReasonBeanCollection}" varStatus="status"> 
		 	<c:if test="${status.index > 0}">,</c:if>
			{text:'<fmt:message key="${status.current.reasonLabel}"/>',value:'${status.current.returnReasonId}'}
			</c:forEach>
); 

<c:choose>
<c:when test="${!empty reasonsBeanCollection}"> 

var reasonJson = new Array();
var reasonJson = {
rows:[
<c:forEach var="reasonBean" items="${reasonsBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:[ '${hasPermission}',    
   '${status.current.returnReasonId}','<tcmis:jsReplace value="${status.current.reasonLabel}" />']} 
<c:set var="dataCount" value='${dataCount+1}'/> 
</c:forEach> 
]};

// No need for hcoro function as now we have regular hcoro.
showReasonGrid = true;

</c:when>
<c:otherwise>
<c:if test="${ !empty vvReasonBeanCollection }">

var reasonJson = new Array();
var reasonJson = {
rows:[

/*The row ID needs to start with 1 per their design.*/
{ id:${1},	
 data:[ '${hasPermission}',    
   '${vvReasonBeanCollection[0].returnReasonId}','<tcmis:jsReplace value="${vvReasonBeanCollection[0].reasonDescription}}" processMultiLines="true"/>']} 
]};

</c:if>
</c:otherwise>
</c:choose>

//reason section  section end

// Item Receipt section start
<c:if test="${ !empty customerReturnRcptShpViewColl }">

var returnItemJson = new Array();
var returnItemJson = {
rows:[
<c:forEach var="returnItemBean" items="${customerReturnRcptShpViewColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:[ '${hasPermission}',    
   '${status.current.itemId}','<tcmis:jsReplace value="${status.current.itemDesc}" />',
   '${status.current.receiptId}','${status.current.quantity}']} 
</c:forEach> 
]};
</c:if>
// Item Receipt section end

//new fees section start
var selectCurrency= '${customerReturnRequestResultBean.currencyId}';

var itemIdDisplayArr = new Array(
		{text:messagesData.pleaseselect,value:''}
		<c:set var="extra" value="1"/>			
	 <c:forEach var="newItemsBean" items="${newItemsBeanCollection}" varStatus="status"> 
	    	<c:if test="${ status.index+extra > 0}">,</c:if>  
		{text:'<tcmis:jsReplace value="${status.current.itemDesc}" processMultiLines="true" />',value:'${status.current.itemId}'}
		</c:forEach>
			); 

var itemIdDisplay = {
		<c:forEach  items="${newFeesAddedBeanCollection}" varStatus="status">
        <c:if test="${ status.index > 0}">,</c:if>
	        ${status.index+1}:itemIdDisplayArr
        </c:forEach>
};

var selectedReturnItemRowId = null;

function getReturnItem() {
	var opsEntityId = $v("opsEntityId");
	var opsEntityName = $v("opsEntityName");
	var hub = $v("hub");
	var inventoryGroup = $v("inventoryGroup");
 
    loc = "inventorylookupmain.do?opsEntityId="+encodeURIComponent(opsEntityId)+"&hub="+encodeURIComponent(hub)+"&inventoryGroup="+encodeURIComponent(inventoryGroup);
  
	children[children.length] = openWinGeneric(loc,"InventoryLookUp","950","450","yes","50","50","20","20","no");
}

function addReturnItemLine(itemReceipt) 
{
	   var rowid = (new Date()).valueOf();
	   ind = haasGrid.getRowsNum();
//		charges[rowKey].rowidd = thisrow.idd;
		  
		  count = 0 ;
//		  itemIdDisplay[rowid] = buildNewItemIdValudset();
	      var thisrow = returnItemGrid.addRow(rowid,"",ind);
//	      alertthis = true;
		  returnItemGrid.cells(rowid, count++).setValue('Y');
		  returnItemGrid.cells(rowid, count++).setValue(itemReceipt.itemId);
		  returnItemGrid.cells(rowid, count++).setValue(itemReceipt.itemDesc);
		  returnItemGrid.cells(rowid, count++).setValue(itemReceipt.receiptId);
		  returnItemGrid.cells(rowid, count++).setValue(itemReceipt.qtyShipped);

//		  rowids[""+rowid] = rowid;
		  selectedReturnItemRowId = rowid; 
		  returnItemGrid.selectRow(returnItemGrid.getRowIndex(rowid));
		  $('returnItemRemoveLine').style.display=""; 
}

var selectedRowId = null;
function addNewFeesLine(rowKey,orderQuantityRule) {
	   var rowid = (new Date()).valueOf();
	   ind = haasGrid.getRowsNum();
//		charges[rowKey].rowidd = thisrow.idd;
		  
		  count = 0 ;
		  itemIdDisplay[rowid] = buildNewItemIdValudset();
	      var thisrow = haasGrid.addRow(rowid,"",ind);
	      alertthis = true;
		  haasGrid.cells(rowid, count++).setValue('Y');
		  haasGrid.cells(rowid, count++).setValue('');
		  haasGrid.cells(rowid, count++).setValue('');
		  haasGrid.cells(rowid, count++).setValue('');
		  haasGrid.cells(rowid, count++).setValue('');
		  haasGrid.cells(rowid, count++).setValue(selectCurrency);
		  haasGrid.cells(rowid, count++).setValue('');

		  rowids[""+rowid] = rowid;
		  selectedRowId = rowid;
		  haasGrid.selectRow(haasGrid.getRowIndex(rowid));
		  $('newFeeRemoveLine').style.display="";
		  
			var options = itemIdDisplay[rowid];
			var z= cell( rowid,"itemIdDisplay").combo;
			z.clear();
			z.put(messagesData.pleaseselectorenter,messagesData.pleaseselectorenter );
//			for( a in z ) 
//				alert( a+ ":"+ z ) ;
			for( j =0; j<  options.length; j++) {
				z.put( options[j].value,options[j].text ) ;
			}
			cell(rowid, "itemIdDisplay").setValue(messagesData.pleaseselectorenter);
	}

//new fees section end

function fillcombo() {
	haasGrid = newFeesGrid;//originalFeesGrid
//    if( hasApprovalPermission != 'Y' ) return;
    //haasGrid.enableEditEvents(true, false,false);//true, true); // single, double click and push f2 to start edit.
    if(null!=haasGrid)
    {    
		haasGrid.attachEvent("onEditCell",myf);
		var rows = haasGrid.getRowsNum();
		for( i = 1; i <= rows; i++ ) {
			var options = itemIdDisplay[i];
			var z= cell( i,"itemIdDisplay").combo;
	//		for ( a in z )
	//			alert( a+":"+z[a]);
			for( j =0; j<  options.length; j++) {
				z.put( options[j].value,options[j].text ) ;
			}
			if( cellValue(i,"itemId") == '148742' ) { 
				cell( i,"itemId" ).setValue('');
				cell( i,"itemIdDisplay" ).setValue( cellValue(i,"feeDescription") );
			}
			else
				cell( i,"itemIdDisplay" ).setValue( cellValue(i,"itemId") );
		}
    }
}

function limitText(id, label, limitNum) {
    var limitCount;
    var limitField  =  document.getElementById(id);
// alert(limitField.value.length);
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
		var lengthMsg = messagesData.maxlength;
		lengthMsg = lengthMsg.replace(/[{]0[}]/g,label);
		lengthMsg = lengthMsg.replace(/[{]1[}]/g,limitNum);
		alert(lengthMsg);
	} 
}

var editing = false;
function myf(stage,rowid,index){

    var ind=haasGrid.getRowIndex(rowid);
    if( index != haasGrid.getColIndexById("itemIdDisplay") )
    	    return true; 
    if( rowid == orirowids[rowid] && cellValue(rowid,"oriItemId") != '148742' ) return false;
    if( cellValue(rowid,"permission") != 'Y' ) return false;

    if (stage==0)//start edit Shipping column
        {
        editing = true;
    	var val = cell(rowid,"itemIdDisplay").getValue();
		a = cell(rowid,"itemIdDisplay").combo.get(val);
		itemIdDisplay[rowid] = buildNewItemIdValudset( rowid );
		var z= cell( rowid,"itemIdDisplay").combo;
		z.clear();
		z.put('',messagesData.pleaseselectorenter );
//		for( a in z ) 
//			alert( a+ ":"+ z ) ;
	    var options = itemIdDisplay[rowid];
		for( j =0; j<  options.length; j++) {
			z.put( options[j].value,options[j].text ) ;
		}
		if( cellValue(	rowid, "itemIdDisplay" ) ==	messagesData.pleaseselectorenter )
			cell(rowid, "itemIdDisplay").setValue('');
		return true;
        }

    if (stage==2)      {       //for finishing edit
    	var val = cellValue(rowid,"itemIdDisplay");//.combo.get(val);
    	a = cell(rowid,"itemIdDisplay").combo.get(val);
	    if( a ) {
    		cell(rowid,"itemId").setValue(val);
    		cell(rowid,"feeDescription").setValue(a);
	    }
	    else {
    		cell(rowid,"itemId").setValue('');
    		cell(rowid,"feeDescription").setValue(val);
	    }
        editing = false;
    }
    //       mygrid.getCombo(5).restore();        //restore combo state
    return true;
}

var rowids = new Array();
var orirowids = new Array();

function showRemoveLine() {
	haasGrid = newFeesGrid;
	if(null!=haasGrid)
	{	
		haasGrid.attachEvent("onRowSelect",selectRow);
	
		var line = haasGrid.getRowsNum();
		if( $('newFeeAddLine') != null )
			if( line != 0 ) $('newFeeRemoveLine').style.display="";
		for(i=1;i<=line;i++) {
			rowids[""+i] = i; 
			orirowids[""+i] = i;
		}
	}	
	
	if(null!=reasonGrid)
	{	
		reasonGrid.attachEvent("onRowSelect",selectReasonRow);
	}  
	
	if(null!=returnItemGrid)
	{	
		returnItemGrid.attachEvent("onRowSelect",selectReturnItemRow);
	
		if( $('returnItemAddLine') != null &&  returnItemGrid.getRowsNum() != 0 )
			$('returnItemRemoveLine').style.display="";
	}
	
	
/*	try {
		opener.closeTransitWin();
	} catch(ex){}  */
}


function removeLine() {
	if(selectedRowId == null){
		alert(messagesData.norowselected);return false;
	}
	try{
	haasGrid.deleteRow(selectedRowId);
	}catch(ex){}
	delete( rowids[selectedRowId] ) ;
	if( haasGrid.getRowsNum() == 0 ) 
		$('newFeeRemoveLine').style["display"] = "none";
		
	submitNewFeesGridForDeletion = true;
	return ;
}

function removeReturnItemLine() {
	if(selectedReturnItemRowId == null){
		alert(messagesData.norowselected);return false;
	}
	try{
	returnItemGrid.deleteRow(selectedReturnItemRowId);
	}catch(ex){}
//	delete( rowids[selectedRowId] ) ;
	if( returnItemGrid.getRowsNum() == 0 ) 
		$('returnItemRemoveLine').style["display"] = "none";
		
//	submitNewFeesGridForDeletion = true;
	return ;
}

function itemIdChanged(rowId,columnId) {
    
	var value = $v(""+columnId+rowId);
	for( row in rowids ) {
		var	val = gridCellValue(newFeesGrid,row,"itemId");
		if( value == val && rowId != row ) {
			alert(messagesData.alreadyselected);
			gridCell(newFeesGrid,rowId,"itemIdDisplay").setValue(gridCellValue(newFeesGrid,rowId,"itemId"));
			return false;
		}
	}
	
	gridCell(newFeesGrid,rowId,"itemId").setValue(value);
//	var v = cellValue(rowId,"changed");
//	if( v != 'New' ) 
//		gridCell(newFeesGrid,rowId,"changed").setValue("Y");
	return true;
}

function buildNewItemIdValudset( row ) {
	var newItemIdArray = new Array();
	for( i=0;i < itemIdDisplayArr.length; i++) 
		newItemIdArray[itemIdDisplayArr[i].value] = {text:itemIdDisplayArr[i].text,value:itemIdDisplayArr[i].value};

//	for( rowid = 1; rowid <= ind; rowid++ ) {
	for( rowid in rowids ) {
		val = gridCellValue(newFeesGrid,rowid,"itemId");
		if( row == rowid ) continue;
		if( val )
			delete( newItemIdArray[val] ) ;
	}
	var valArray = new Array();
	for( v in newItemIdArray )
		valArray[valArray.length] = newItemIdArray[v];
	return valArray;
}

function selectRow()
{  
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

   oldRowId = rowId0;

   if( ee != null ) {
   		if( ee.button != null && ee.button == 2 ) rightClick = true;
   		else if( ee.which == 3  ) rightClick = true;
   }
//   if( colId0 == haasGrid.getColIndexById("itemIdDisplay") && 
//		   cellValue( rowId0,"itemIdDisplay" ) == messagesData.pleaseselectorenter )
//	   cell( rowId0,"itemIdDisplay" ).setValue('');
   selectedRowId = rowId0;
   if(null!=$('newFeeRemoveLine'))
   {	   
	   if( cellValue(selectedRowId,"permission") == "Y" ) 
		   $('newFeeRemoveLine').style["display"] = "";
	   else 
		   $('newFeeRemoveLine').style["display"] = "none";
   }
   if( !rightClick ) return true;
   	 haasGrid.selectRowById(rowId0,null,false,false);	
   if( cellValue(selectedRowId,"permission") != "Y" ) return true;
//   toggleContextMenu('deleteLine');

} //end of method

function selectReasonRow(rowId,cellInd) {
	selectedReasonRowId = rowId;
}

function selectReturnItemRow(rowId,cellInd) {
	selectedReturnItemRowId = rowId;
}

//-->
</script>
</head>

<body bgcolor="#ffffff" onload="setGrids();fillcombo();setResultSize();canAddRemoveReasonLines();showRemoveLine();showOrHidereturnItemGrid();showOrHideReplacementDetail();" onunload="closeAllchildren();">

<tcmis:form action="/customerreturnrequest.do" onsubmit="return submitOnlyOnce();">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->


<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>
<!-- Error Messages Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors && empty tcmISError}"> 
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>   
//-->
</script>

<div id="resultGridDiv" style="display: none;">

<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop"> 
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>   
      
  <c:choose>
   <c:when test="${hasPermission eq 'Y' && hasApprovalPermission eq 'Y'}">		
       <span id="saveData"><a href="javascript:submitCustomerReturnRequest('update','Draft');"><fmt:message key="button.save"/></a></span> 		 
       		&nbsp;|&nbsp;   
       <span id="approveButton"><a href="javascript:submitCustomerReturnRequest('approve','Approved');"><fmt:message key="label.approve"/></a></span>	 
         	&nbsp;|&nbsp;            		 
       <span id="denyButton"><a href="javascript:submitCustomerReturnRequest('deny','Rejected');"><fmt:message key="label.deny"/></a></span>
       
       <c:if test="${ (approvalStatusVal eq 'Draft') and (csrcsrPersonnelIdVal eq personnedlIdVal)}">
           &nbsp;|&nbsp;            		 
           <span id="denyButton"><a href="javascript:submitCustomerReturnRequest('delete','Delete');"><fmt:message key="label.delete"/></a></span>
       </c:if>
    </c:when>
	<c:when test="${hasPermission eq 'Y'}">		
	      <span id="saveData"><a href="javascript:submitCustomerReturnRequest('update','Draft');"><fmt:message key="button.save"/></a></span> 		 
          	&nbsp;|&nbsp;         		 
          <span id="saveDataAndStatus"><a href="javascript: submitCustomerReturnRequest('submit','Pending Approval');"><fmt:message key="button.submit"/></a></span>
        <c:if test="${ (approvalStatusVal eq 'Draft') and (csrcsrPersonnelIdVal eq personnedlIdVal)}">
          	&nbsp;|&nbsp;            		 
          <span id="denyButton"><a href="javascript:submitCustomerReturnRequest('delete','Delete');"><fmt:message key="label.delete"/></a></span>
        </c:if>
    </c:when>
    <c:when test="${hasApprovalPermission eq 'Y'}">	
    	  <span id="saveData"><a href="javascript:submitCustomerReturnRequest('update','Draft');"><fmt:message key="button.save"/></a></span> 		 
          		&nbsp;|&nbsp;  	
	      <span id="approveButton"><a href="javascript:submitCustomerReturnRequest('approve','Approved');"><fmt:message key="label.approve"/></a></span>	 
         		 &nbsp;|&nbsp;            		 
          <span id="denyButton"><a href="javascript:submitCustomerReturnRequest('deny','Rejected');"><fmt:message key="label.deny"/></a></span>
    </c:when>
	<c:otherwise>
	</c:otherwise>
  </c:choose>         
  <c:if test="${ approvalStatusVal eq 'Approved'}">
      <span id="printButton"><a href="javascript:printCustReturnReq();"><fmt:message key="label.print"/></a></span>
  </c:if> 
</div> <%-- boxhead Ends --%>
    
    
    <div class="dataContent">
    <!-- Insert all the search option within this div -->
    <%--  <table width="700" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch"> --%>
    <table width="100%" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
      <td colspan="4" class="optionTitleBoldLeft"> 
      <fieldset>
<legend>
      <fmt:message key="label.rma"/>:
      ${customerReturnRequestResultBean.customerRmaId}
      <fmt:message key="label.operatingentity"/>:
      ${customerReturnRequestResultBean.opsEntityName}
      <fmt:message key="label.csr"/>:
       ${customerReturnRequestResultBean.csrName}
   </legend> 
   <table border="0">
<tr> 
    <c:set var="approvStatus" value=''/>
	<c:choose>
	   <c:when test="${customerReturnRequestResultBean.rmaStatus eq 'Open'}">
	    <c:set var="approvStatus" value='Pending Approval'/>
	   </c:when>
	   <c:otherwise>
	   <c:set var="approvStatus" value='${customerReturnRequestResultBean.rmaStatus}'/>
	   </c:otherwise>
	</c:choose>   
	<td width="10%"  class="optionTitleBoldRight"><fmt:message key="label.company"/>:</td>
	<td width="25%" class="optionTitleLeft">${customerReturnRequestResultBean.customerName}</td>
	<td width="20%" class="optionTitleBoldRight"><fmt:message key="label.mrline"/>:</td>
	<td width="45%" nowrap class="optionTitleLeft">${customerReturnRequestResultBean.prNumber} - ${customerReturnRequestResultBean.lineItem}</td>
</tr>

<tr>
	<td width="10%" nowrap class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
	<td width="25%" class="optionTitleLeft">${customerReturnRequestResultBean.facilityId}</td>
	<td width="20%"  class="optionTitleBoldRight"><fmt:message key="label.customerpartnumber"/>:</td>
	<td width="45%" nowrap class="optionTitleLeft">${customerReturnRequestResultBean.facPartNo}</td>
</tr>

<tr>
	<td width="10%" valign="top" nowrap class="optionTitleBoldRight"><fmt:message key="label.shipto"/>:</td>
	<td width="25%" valign="top" class="optionTitleLeft">${customerReturnRequestResultBean.shipToAddress}</td>
	<td width="20%" valign="top" nowrap class="optionTitleBoldRight"><fmt:message key="label.description"/>:</td>
	<td width="45%" valign="top" class="optionTitleLeft">${customerReturnRequestResultBean.partDescription}</td>
</tr>

<tr>
	<td width="10%" nowrap class="optionTitleBoldRight">&nbsp;</td>
	<td width="25%" class="optionTitleLeft">&nbsp;</td>
	<td width="20%" nowrap class="optionTitleBoldRight"><fmt:message key="label.ourpartnumber"/>:</td>
	<td width="45%" nowrap class="optionTitleLeft">${customerReturnRequestResultBean.haasPartNo}</td>
</tr>

<tr>
	<td width="10%" nowrap class="optionTitleBoldRight"><fmt:message key="label.contact"/>:</td>
	<td width="25%" class="optionTitleLeft">${customerReturnRequestResultBean.requestorName}</td>
	<td width="20%" nowrap class="optionTitleBoldRight"><fmt:message key="label.orderquantity"/>:</td>
	<td width="45%" class="optionTitleLeft">${customerReturnRequestResultBean.orderQuantity}</td>
</tr>

<tr>
	<td width="10%" nowrap class="optionTitleBoldRight"><fmt:message key="label.email"/>:</td>
	<td width="25%" class="optionTitleLeft">
	 ${customerReturnRequestResultBean.requestorEmail}
	</td>
	<td width="20%" class="optionTitleBoldRight"><fmt:message key="label.orderunitprice"/>:</td>
	<td width="45%" class="optionTitleLeft">
	 ${customerReturnRequestResultBean.unitPrice}&nbsp;<span class="optionTitleLeft">${customerReturnRequestResultBean.currencyId}</span>
	</td>
</tr>

<tr>
	<td width="10%" nowrap class="optionTitleBoldRight"><fmt:message key="label.phone"/>:</td>
	<td width="25%" class="optionTitleLeft">${customerReturnRequestResultBean.requestorPhone}</td>
	<td width="20%" class="optionTitleBoldRight"><fmt:message key="label.quantityshipped"/>:</td>
	<td width="45%" class="optionTitleLeft">
		<input type="hidden" class="inputBox" name="quantityShipped" id="quantityShipped" value="${customerReturnRequestResultBean.quantityShipped}" size="8" maxlength="16"/>
	 	${customerReturnRequestResultBean.quantityShipped}
	</td>
</tr>
</table>
   </fieldset>   
      </td>                 
    </tr> 
 <!--  Return Detail Start-->
    <tr>
      <td colspan="4" class="optionTitleBoldLeft">
      <fieldset>
		<legend>
			<fmt:formatDate var="fmtRequestStartDate" value="${customerReturnRequestResultBean.requestStartDate}" pattern="${dateFormatPattern}"/>&nbsp;
	      	<fmt:message key="label.returndetail"/> : <fmt:message key="label.requeststartdate"/>:${fmtRequestStartDate}  
   		</legend> 
   
<table border="0">
<tr> 
<td width="20%" nowrap  class="optionTitleBoldRight"><fmt:message key="label.returnrequestor"/>:</td>
<td width="25%" class="optionTitleLeft">
<c:choose>
	<c:when test="${hasPermission eq 'Y' or hasApprovalPermission eq 'Y'}">
		<input type="text" class="inputBox" name="requestorName" id="requestorName" value='<tcmis:inputTextReplace value="${customerReturnRequestResultBean.returnRequestorName}" />' size="30" maxlength="100"/>	
	</c:when>
	<c:otherwise>
		<input type="hidden"   class="inputBox" name="requestorName" id="requestorName" value='<tcmis:inputTextReplace value="${customerReturnRequestResultBean.returnRequestorName}" />'/>
		<tcmis:inputTextReplace value="${customerReturnRequestResultBean.returnRequestorName}" />
	</c:otherwise>
</c:choose>
</td>
<td width="10%" class="optionTitleBoldLeft">&nbsp;</td>
<td width="15%" nowrap class="optionTitleBoldRight">
	<span style='font-size:12.0pt;color:red'>*</span>
	<fmt:message key="label.returnquantityrequested"/>:
</td>
<td width="30%" class="optionTitleLeft">
	<c:choose>
		<c:when test="${hasPermission eq 'Y' or hasApprovalPermission eq 'Y'}">
			<input type="text" class="inputBox" name="returnQuantityRequested" id="returnQuantityRequested" value="${customerReturnRequestResultBean.quantityReturnRequested}" size="8" maxlength="16"/>	
		</c:when>
		<c:otherwise>
			<input type="hidden"  class="inputBox" name="returnQuantityRequested" id="returnQuantityRequested" value="${customerReturnRequestResultBean.quantityReturnRequested}" size="8" maxlength="16"/>
			${customerReturnRequestResultBean.quantityReturnRequested} 	
		</c:otherwise>
	</c:choose>
</td>
</tr>

<tr>
<td width="20%" class="optionTitleBoldRight"><fmt:message key="label.email"/>:</td>
<td width="25%" class="optionTitleLeft">
	<c:choose>
		<c:when test="${hasPermission eq 'Y' or hasApprovalPermission eq 'Y'}">
			<input type="text" class="inputBox" name="requestorEmail" id="requestorEmail" value="<tcmis:inputTextReplace value="${customerReturnRequestResultBean.returnRequestorEmail}" />" size="30" maxlength="200"/>	
		</c:when>
		<c:otherwise>
			<input type="hidden"  class="inputBox" name="requestorEmail" id="requestorEmail" value="<tcmis:inputTextReplace value="${customerReturnRequestResultBean.returnRequestorEmail}" />" size="30" maxlength="200"/>
			<tcmis:inputTextReplace value="${customerReturnRequestResultBean.returnRequestorEmail}" /> 	
		</c:otherwise>
	</c:choose>
</td>
<td width="10%" class="optionTitleBoldLeft">&nbsp;</td>
<td width="15%" nowrap class="optionTitleBoldRight"><fmt:message key="label.customerreturnrequest.retentionquestion"/></td>
<td width="30%" class="optionTitleBoldLeft">

 <c:set var="returnMaterialY" value=""/>
 <c:set var="returnMaterialN" value=""/>
  
 <c:if test="${ ( approvalStatusVal eq 'Draft' ) and (empty customerReturnRequestResultBean.returnMaterial)}">
 	<c:set var="returnMaterialN" value="checked"/>
 </c:if>  
 
 <c:if test="${ !empty customerReturnRequestResultBean }">
	 <c:if test="${ !empty customerReturnRequestResultBean.returnMaterial && customerReturnRequestResultBean.returnMaterial == 'Y' }">
	 	<c:set var="returnMaterialN" value="checked"/>
	 </c:if>
	 <c:if test="${ !empty customerReturnRequestResultBean.returnMaterial && customerReturnRequestResultBean.returnMaterial == 'N' }">
		<c:set var="returnMaterialY" value="checked"/>
	 </c:if>
 </c:if>

<c:choose>
<c:when test="${hasPermission eq 'Y' or hasApprovalPermission eq 'Y'}">
	<input type="radio" name="keepMaterialRdBtn"  id="keepMaterialRdBtn" value="Y" ${returnMaterialY} /><fmt:message key="label.yes" />&nbsp;
	<input type="radio" name="keepMaterialRdBtn"  id="keepMaterialRdBtn" value="N" ${returnMaterialN}  /><fmt:message key="label.no" />
</c:when>
<c:otherwise>
	<input type="radio" name="keepMaterialRdBtn"  id="keepMaterialRdBtn" value="Y" ${returnMaterialY} disabled /><fmt:message key="label.yes" />&nbsp;
	<input type="radio" name="keepMaterialRdBtn"  id="keepMaterialRdBtn" value="N" ${returnMaterialN} disabled/><fmt:message key="label.no" />
</c:otherwise>   
</c:choose>
</td>
</tr>

<tr>
<td width="20%" class="optionTitleBoldRight"><fmt:message key="label.phone"/>:</td>
<td width="25%" class="optionTitleLeft">
  <c:choose>
	<c:when test="${hasPermission eq 'Y' or hasApprovalPermission eq 'Y'}">
		<input type="text" class="inputBox" name="requestorPhone" id="requestorPhone" value='<tcmis:inputTextReplace value="${customerReturnRequestResultBean.returnRequestorPhone}" />' size="30" maxlength="40"/>	
	</c:when>
	<c:otherwise>
		<input type="hidden"  class="inputBox" name="requestorPhone" id="requestorPhone" value='<tcmis:inputTextReplace value="${customerReturnRequestResultBean.returnRequestorPhone}" />'/>
		<tcmis:inputTextReplace value="${customerReturnRequestResultBean.returnRequestorPhone}" /> 	
	</c:otherwise>
  </c:choose>
</td>
<td width="10%" class="optionTitleBoldLeft">&nbsp;</td>
<td width="15%" nowrap class="optionTitleBoldRight"><fmt:message key="label.shipcorrectitem"/></td>
<td width="30%" class="optionTitleBoldLeft">
 <c:set var="correctItemShippedY" value=""/>
 <c:set var="correctItemShippedN" value=""/>
  
 <c:if test="${ ( approvalStatusVal eq 'Draft' ) and (empty customerReturnRequestResultBean.correctItemShipped)}">
 	<c:set var="correctItemShippedY" value="checked"/>
 </c:if>  
 
 <c:if test="${ !empty customerReturnRequestResultBean }">
	 <c:if test="${ !empty customerReturnRequestResultBean.correctItemShipped && customerReturnRequestResultBean.correctItemShipped == 'Y' }">
	 	<c:set var="correctItemShippedY" value="checked"/>
	 </c:if>
	 
	 <c:if test="${ !empty customerReturnRequestResultBean.correctItemShipped && customerReturnRequestResultBean.correctItemShipped == 'N' }">
		<c:set var="correctItemShippedN" value="checked"/>
	 </c:if>
 </c:if>

 <c:choose>
	<c:when test="${hasPermission eq 'Y' or hasApprovalPermission eq 'Y'}">
		<input type="radio" name="correctItemShipped"  id="correctItemShipped" value="Y" onclick="showOrHidereturnItemGrid();" ${correctItemShippedY} /><fmt:message key="label.yes" />&nbsp;
		<input type="radio" name="correctItemShipped"  id="correctItemShipped" value="N" onclick="showOrHidereturnItemGrid();" ${correctItemShippedN}  /><fmt:message key="label.no" />
	</c:when>
	<c:otherwise>
		<input type="radio" name="correctItemShipped"  id="correctItemShipped" value="Y" ${correctItemShippedY} disabled /><fmt:message key="label.yes" />&nbsp;
		<input type="radio" name="correctItemShipped"  id="correctItemShipped" value="N" ${correctItemShippedN} disabled/><fmt:message key="label.no" />
	</c:otherwise>   
 </c:choose>
</td>
</tr>

<tr>
<td width="20%" class="optionTitleBoldLeft">&nbsp;</td>
<td width="25%" class="optionTitleBoldLeft">
  <c:choose>
	<c:when test="${hasPermission eq 'Y' or hasApprovalPermission eq 'Y'}">
		<span id="reasonAddLine">
		<a href="#" onclick="addReasonLine(); "><fmt:message key="label.addline"/></a>
		</span>&nbsp;
		<span id="reasonRemoveLine" style="display: none">
		<a href="#" onclick="removeReasonLine();"><fmt:message key="label.removeLine"/></a>
		</span> 	
	</c:when>
	<c:otherwise>
		&nbsp;
	</c:otherwise>
  </c:choose>
</td>
<td width="10%" class="optionTitleBoldLeft">&nbsp;</td>
<td width="45%" class="optionTitleBoldLeft" colspan="2" id="returnItemLink">
  <c:choose>
	<c:when test="${hasPermission eq 'Y' or hasApprovalPermission eq 'Y'}">
		<span id="returnItemAddLine">
		<a href="#" onclick="getReturnItem(); "><fmt:message key="label.addline"/></a>
		</span> &nbsp;
		<span id="returnItemRemoveLine" style="display: none">
		<a href="#" onclick="removeReturnItemLine();"><fmt:message key="label.removeLine"/></a>
		</span> 	
	</c:when>
	<c:otherwise>
			&nbsp;
	</c:otherwise>
  </c:choose>
</td>
</tr>

<tr>
<td width="20%" class="optionTitleBoldRight"><fmt:message key="label.return"/>&nbsp;<fmt:message key="label.reason"/><span style='font-size:12.0pt;color:red'>*</span>:</td>
<td width="25%" class="optionTitleBoldLeft">
	<div id="reasonDiv" style="width:220px;height:160px;" style="display: none;">
	</div>
</td>
<td width="10%" class="optionTitleBoldRight">&nbsp;</td>
<td class="optionTitleBoldLeft" colspan="2">
	<div id="returnItemDiv" style="width:430px;height:160px;" style="display: none;">
	</div>
</td>
</tr>

<tr>
<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.comments"/>:</td>
<td width="50%" class="optionTitleLeft" colspan="4">
  <c:choose>
	<c:when test="${hasPermission eq 'Y' or hasApprovalPermission eq 'Y'}">
		<TEXTAREA name="returnNotes" id="returnNotes" onKeyDown='limitText("returnNotes", "<fmt:message key="label.comments"/>", 4000);' onKeyUp='limitText("returnNotes", "<fmt:message key="label.comments"/>", 4000);' class="inputBox" COLS=150 ROWS=3><tcmis:inputTextReplace value="${customerReturnRequestResultBean.returnNotes}"/></TEXTAREA>
	</c:when>
	<c:otherwise>
		<input type="hidden"  class="inputBox" name="returnNotes" id="returnNotes" value="<tcmis:inputTextReplace value="${customerReturnRequestResultBean.returnNotes}" />" />
		${customerReturnRequestResultBean.returnNotes}
	</c:otherwise>
  </c:choose>
</td>
</tr>

</table>
</fieldset>   
</td>      
             
</tr> 
<!--  Return Detail End -->  


<!--  Replacement Detail Start -->
<tr>
 <td colspan="4" class="optionTitleBoldLeft">
 <fieldset>
   <legend>
      <fmt:message key="label.replacementdetail"/>  
   </legend> 
     <table border="0">
       <tr>
     	<td  width="30%" nowrap class="optionTitleBoldRight">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     		<fmt:message key="label.customerreturnrequest.replacementquestion"/></td>
		<td width="70%" class="optionTitleBoldLeft">
		<c:set var="wantReplacementMaterialY" value=""/>
		<c:set var="wantReplacementMaterialN" value=""/>
		
		<c:if test="${  (approvalStatusVal eq 'Draft') and (empty customerReturnRequestResultBean.wantReplacementMaterial) }">
		 <c:set var="wantReplacementMaterialY" value="checked"/>
		</c:if>  
		  
		 <c:if test="${ !empty customerReturnRequestResultBean }">
		 
		 <c:if test="${ !empty customerReturnRequestResultBean.wantReplacementMaterial && customerReturnRequestResultBean.wantReplacementMaterial == 'Y' }">
		 <c:set var="wantReplacementMaterialY" value="checked"/>
		 </c:if>
		 
		 <c:if test="${ !empty customerReturnRequestResultBean.wantReplacementMaterial && customerReturnRequestResultBean.wantReplacementMaterial == 'N' }">
		 <c:set var="wantReplacementMaterialN" value="checked"/>
		 </c:if> 
		 </c:if>

		<c:choose>
		<c:when test="${hasPermission eq 'Y' or hasApprovalPermission eq 'Y'}">
			<input type="radio" name="wantReplacement"  id="wantReplacement" value="Y" onclick="showOrHideReplacementDetail();" ${wantReplacementMaterialY} /><fmt:message key="label.yes"/>&nbsp;
			<input type="radio" name="wantReplacement"  id="wantReplacement" value="N" onclick="showOrHideReplacementDetail();" ${wantReplacementMaterialN} /><fmt:message key="label.no"/>
		</c:when>
		<c:otherwise>
			<input type="radio" name="wantReplacement"  id="wantReplacement" value="Y" onclick="showOrHideReplacementDetail();" ${wantReplacementMaterialY} disabled /><fmt:message key="label.yes"/>&nbsp;
			<input type="radio" name="wantReplacement"  id="wantReplacement" value="N" onclick="showOrHideReplacementDetail();" ${wantReplacementMaterialN} disabled /><fmt:message key="label.no"/>
		</c:otherwise>
		</c:choose>
	  </td>
    </tr>
	<tr id="replacementDetail1">
	  <td  width="30%" class="optionTitleBoldRight" nowrap><fmt:message key="label.ourreplacementpn"/>:</td>
	  <td  width="70%" colspan="3" class="optionTitleLeft">
		<c:choose>
			<c:when test="${hasPermission eq 'Y' or hasApprovalPermission eq 'Y'}">
				<input type="text"  class="invGreyInputText" readonly name="replacementCartPN" id="replacementCartPN" value="${customerReturnRequestResultBean.replacementCatPartNo}" size="9" maxlength="30"/>	
			</c:when>
			<c:otherwise>
				<input type="hidden"  class="inputBox" name="replacementCartPN" id="replacementCartPN" value="${customerReturnRequestResultBean.replacementCatPartNo}" size="20" maxlength="30"/>
				${customerReturnRequestResultBean.replacementCatPartNo}
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${hasPermission eq 'Y' or hasApprovalPermission eq 'Y'}">
				<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" value="..." onclick="lookupReplacePartNum();" />	
				<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
				                                             name="None" value=""  OnClick="clearReplacementNumber()"><fmt:message key="label.clear"/> </button>
			</c:when>
			<c:otherwise>
				&nbsp;
			</c:otherwise>
		</c:choose>
		</td>
	  </tr>
		
	  <tr id="replacementDetail2"> 
		<td width="30%" valign="top" class="optionTitleBoldRight" ><fmt:message key="label.description"/>:</td>
		<td width="70%" valign="top" class="optionTitleLeft">
			<span name="replacementPartDescriptionSpan" id="replacementPartDescriptionSpan" style="width:300px;" >
				${customerReturnRequestResultBean.replacementPartDescription}
			</span>
			<input type="hidden"  name="replacementPartDescription" id="replacementPartDescription" value="${customerReturnRequestResultBean.replacementPartDescription}"/>
		</td>
	  </tr>
		
	  <tr id="replacementDetail3">
		<td width="30%"  class="optionTitleBoldRight" nowrap>
			<span style='font-size:12.0pt;color:red'>*</span>
			<fmt:message key="label.newunitprice"/>:</td>
		<td width="70%" nowrap class="optionTitleLeft">
		<c:choose>
		<c:when test="${hasPermission eq 'Y' or hasApprovalPermission eq 'Y'}">
			<input type="text" class="inputBox" name="newUnitPrice" id="newUnitPrice" value="${customerReturnRequestResultBean.newUnitPrice}"  size="9" maxlength="16"/>	
			&nbsp;<span class="optionTitleLeft">${customerReturnRequestResultBean.currencyId}</span>
		</c:when>
		<c:otherwise>
			<input type="hidden" class="inputBox" name="newUnitPrice" id="newUnitPrice" value="${customerReturnRequestResultBean.newUnitPrice}"  size="9" maxlength="16"/>
			${customerReturnRequestResultBean.newUnitPrice}&nbsp;<span class="optionTitleLeft">${customerReturnRequestResultBean.currencyId}</span>
		</c:otherwise>
		</c:choose>
		</td>
		</tr>
		
		<tr id="replacementDetail4">
		<td width="30%"  class="optionTitleBoldRight" nowrap>
			<span style='font-size:12.0pt;color:red'>*</span>
			<fmt:message key="label.needdate"/>:</td>
		<td width="70%" nowrap class="optionTitleLeft">
		  <fmt:formatDate var="fmtNeedDate" value="${customerReturnRequestResultBean.replacementRequiredDatetime}" pattern="${dateFormatPattern}"/>
		  <c:choose>
			<c:when test="${hasPermission eq 'Y' or hasApprovalPermission eq 'Y'}">
				<input class="inputBox pointer" readonly type="text" name="replacementRequiredDatetime" id="replacementRequiredDatetime" value="${fmtNeedDate}"  
					onClick="return getCalendar(document.genericForm.replacementRequiredDatetime,null,null);"  maxlength="10" size="9"/>
			</c:when>
			<c:otherwise>
				<input type="hidden" class="inputBox" name="replacementRequiredDatetime" id="replacementRequiredDatetime" value="${fmtNeedDate}"/>
				${fmtNeedDate}
			</c:otherwise>
		  </c:choose>
		</td>
		</tr>
		
		<tr id="replacementDetail5">
		<td width="30%"  class="optionTitleBoldRight" nowrap>
			<span style='font-size:12.0pt;color:red'>*</span>
			<fmt:message key="label.promiseddate"/>:</td>
		<td width="70%" nowrap class="optionTitleLeft">
			<fmt:formatDate var="fmtPromisedDate" value="${customerReturnRequestResultBean.replacementPromiseDate}" pattern="${dateFormatPattern}"/>
		<c:choose>
		<c:when test="${hasPermission eq 'Y' or hasApprovalPermission eq 'Y'}">
			<input class="inputBox pointer" readonly type="text" name="replacementPromiseDate" id="replacementPromiseDate" value="${fmtPromisedDate}"  onClick="return getCalendar(document.genericForm.replacementPromiseDate,null,null);"
		                                                                    maxlength="10" size="9"/>
		</c:when>
		<c:otherwise>
			<input type="hidden" class="inputBox" name="replacementPromiseDate" id="replacementPromiseDate" value="${fmtPromisedDate}" />
			${fmtPromisedDate}
		</c:otherwise>
		</c:choose>
	  </td>
	  </tr>
	</table>
 </fieldset>   
</td>      
</tr>
 <!--  Replacement Detail End -->  
 
 
<!--  Approveral Detail Start -->
<tr>
 <td colspan="4" class="optionTitleBoldLeft">
 <fieldset>
   <legend>
      <fmt:message key="label.approvaldetail"/>  
   </legend> 
   <table border="0" width="100%">
     <tr> 
        <td width="25%"  class="optionTitleBoldRight"><fmt:message key="label.approver"/>:</td>
		<td width="75%" nowrap class="optionTitleLeft" colspan="3">
			${customerReturnRequestResultBean.approverName}
		</td>
	</tr>
	<tr>
		<td width="20%" class="optionTitleBoldRight"><fmt:message key="label.requeststatus"/>:</td>
		<td width="30%" class="optionTitleLeft">${approvStatus}</td>
		<td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.ApprovedDate"/>:</td>
		<td width="40%" class="optionTitleLeft">
			<fmt:formatDate var="ApprovedDate" value="${customerReturnRequestResultBean.approvalDate}" pattern="${dateFormatPattern}"/>
			${ApprovedDate}&nbsp;
		</td>
	</tr>

<tr>
	<td width="20%" nowrap class="optionTitleBoldRight"><fmt:message key="label.returnquantityauthorized"/>:</td>
	<td width="30%" class="optionTitleLeft" >
		<c:choose>
			<c:when test="${ hasApprovalPermission eq 'Y' }">
				<input type="text" class="inputBox" name="quantityReturnAuthorized" id="quantityReturnAuthorized" value="${customerReturnRequestResultBean.quantityReturnAuthorized}" size="5" maxlength="10"/>
			</c:when>
			<c:otherwise>
				<input type="hidden" class="inputBox" name="quantityReturnAuthorized" id="quantityReturnAuthorized" value="${customerReturnRequestResultBean.quantityReturnAuthorized}" size="5" maxlength="10"/>
				${customerReturnRequestResultBean.quantityReturnAuthorized}	
			</c:otherwise>
		</c:choose>
	</td>
	
	<c:choose>
		<c:when test="${approvalStatusVal eq 'Rejected' }">
			<td  width="10%" nowrap class="optionTitleBoldRight"><fmt:message key="label.comments"/>:</td>
			<td width="40%" class="optionTitleLeft">${customerReturnRequestResultBean.rejectionComment}</td>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
</tr>

<tr>
<td width="25%"  class="optionTitleBoldRight"><fmt:message key="label.originalorderfees"/>:</td>
<td width="75%" colspan="3" class="optionTitleLeft">
<div id="originalFeesDiv" style="width:520px;height:150px;" style="display: none;"></div>

<c:if test="${ (originalFeeHeaderChargeCollection != null) or (originalFeeLineChargeCollection != null)}">
<script type="text/javascript">
<!--
<c:set var="originalFeeDataCount" value='${0}'/>
<c:if test="${(!empty originalFeeHeaderChargeCollection) or (!empty originalFeeLineChargeCollection)}">
var originalFeeJson = new Array();
var originalFeeJson = {
rows:[
      
<c:if test="${!empty originalFeeHeaderChargeCollection}">

<c:forEach var="originalFeeHeaderChargeCollection" items="${originalFeeHeaderChargeCollection}" varStatus="hstatus">
<c:if test="${hstatus.index > 0}">,</c:if>
<tcmis:jsReplace var="itemDesc" value="${hstatus.current.description}"  processMultiLines="true"/>
 /*The row ID needs to start with 1 per their design.*/
{ id:${hstatus.index +1}, 
 data:['${hasApprovalPermission}','${itemDesc}','<fmt:message key="label.headercharges"/>',
       '${hstatus.current.price} ${hstatus.current.currencyId}','${hstatus.current.unitPrice}',
   '${hstatus.current.prNumber}','${hstatus.current.lineItem}','${hstatus.current.itemId}',
 '${hstatus.current.quantity}','${hstatus.current.currencyId}','${hstatus.current.unitPrice}','${itemDesc}'
 ]}
 <c:set var="originalFeeDataCount" value='${originalFeeDataCount+1}'/>
 </c:forEach>

 </c:if>
 <c:if test="${!empty originalFeeLineChargeCollection}">
 
 <c:forEach var="originalFeeLineChargeCollection" items="${originalFeeLineChargeCollection}" varStatus="status">
 <c:if test="${status.index > 0}">,</c:if>
 <tcmis:jsReplace var="itemDesc" value="${status.current.description}"  processMultiLines="true"/>
  /*The row ID needs to start with 1 per their design.*/
 { id:${status.index +1}, 
  data:['${hasApprovalPermission}','${itemDesc}','<fmt:message key="label.linecharges"/>',
        '${status.current.price} ${status.current.currencyId}','${status.current.unitPrice}','',
    '${status.current.prNumber}','${status.current.lineItem}','${status.current.itemId}',
  '${status.current.quantity}','${status.current.currencyId}','${status.current.unitPrice}','${itemDesc}'
  ]}
  <c:set var="originalFeeDataCount" value='${originalFeeDataCount+1}'/>
  </c:forEach>
  </c:if>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${(empty originalFeeHeaderChargeCollection) and (empty originalFeeLineChargeCollection)}">
   <fmt:message key="label.none"/>
</c:if> 
 <!-- Search results end --> 
</c:if>
</td>
</tr>
	<c:set var="updateNewFeesPermission" value='N'/>
	<c:if test="${(hasPermission eq 'Y' and csrcsrPersonnelIdVal eq personnedlIdVal) or hasApprovalPermission eq 'Y' }">
	  <tr>
		<td width="30%"  class="optionTitleBoldLeft"></td>
		<td width="70%" class="optionTitleBoldLeft" colspan="2">
			<span id="newFeeAddLine">
				<a href="#newFeeAddLine" onclick="addNewFeesLine();"><fmt:message key="label.addline"/></a>	&nbsp;
			<span id="newFeeRemoveLine" style="display: none">
				<a href="#newFeeRemoveLine" onclick="removeLine();"><fmt:message key="label.removeLine"/></a>
			</span>
		</td>
	  </tr>
	  <c:set var="updateNewFeesPermission" value='Y'/>
	</c:if>
<tr>
<td width="25%"  class="optionTitleBoldRight"><fmt:message key="label.newfees"/>:</td>
<td colspan="75"  class="optionTitleLeft">
<c:choose>
<c:when test="${!empty newFeesAddedBeanCollection}"> 
<c:set var="newFeesDataCount" value='${0}'/>
<script type="text/javascript">
     
var newFeesJson = new Array();
var newFeesJson = {
rows:[
<c:forEach var="newFeesBean" items="${newFeesAddedBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:[ '${updateNewFeesPermission}',    
        '${status.current.itemId}', 
        '${status.current.itemId}', 
        '${status.current.itemId}', 
        '${status.current.returnPrice}',
        '${status.current.currencyId}',
        '<tcmis:jsReplace value="${status.current.feeDescription}"/>']} 
<c:set var="newFeesDataCount" value='${newFeesDataCount+1}'/> 
</c:forEach> 
]};

// No need for hcoro function as now we have regular hcoro.
showNewFeesGrid = true;

</script>

<div id="newFeesDiv" style="width:520px;height:160px;" style="display: none;"></div>
</c:when>
<c:otherwise>
  <c:choose>
	<c:when test="${(approvalStatusVal eq 'Approved') or (approvalStatusVal eq 'Rejected')}">
		<fmt:message key="label.none"/> 
	</c:when>
	<c:otherwise>
		<c:if test="${ !empty newItemsBeanCollection }">
		<script type="text/javascript">
		var newFeesJson = new Array();
		var newFeesJson = {
		rows:[
		/*The row ID needs to start with 1 per their design.*/
		{ id:${1},	
		 data:[ '${updateNewFeesPermission}',    
		        '${newItemsBeanCollection[0].itemId}',
		        '${newItemsBeanCollection[0].itemId}',
		        '${newItemsBeanCollection[0].itemId}',
		        '${newItemsBeanCollection[0].newprice}',
		        '${newItemsBeanCollection[0].selectCurrency}',
		   		'<tcmis:jsReplace value="${status.current.feeDescription}"/>'
		   		]
		   } 
		 
		]};
		</script>
		</c:if>
		<div id="newFeesDiv" style="width:520px;height:120px;" style="display: none;"></div>
	</c:otherwise>
  </c:choose>
</c:otherwise>
</c:choose>
</td>

</tr>
</table>
   </fieldset>   
      </td>      
             
    </tr>
 <!--  Approveral Detail End -->  
    <tr>
    </tr>      
    </table>
    
    </div>
   <div id="footer" class="messageBar"></div>
   <!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table><!-- Search Option Ends -->
</div> <!-- Result Frame -->
<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="action" id="action" value=""/>
<input type="hidden" name="copyReasonBean" id="copyReasonBean" value=""/>
<input type="hidden" name="hubName" id="hubName" value=""/>
<input type="hidden" name="rmaId" id="rmaId" value="${rmaId}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="totalLines" id="totalLines" type="hidden" value="0" />
<input name="rmaStatus" id="rmaStatus" type="hidden" value="${approvStatus}" />
<input name="csrName" id="csrName" type="hidden" value="${customerReturnRequestResultBean.csrName}" />
<input name="originalQty" id="originalQty" type="hidden" value="${customerReturnRequestResultBean.orderQuantity}" />
<input name="returnMaterial" id="returnMaterial" type="hidden" value="" />
<input name="reasonAddLimit" id="reasonAddLimit" type="hidden" value="${reasonAddLimit}" />
<input name="prNumber" id="prNumber" type="hidden" value="${prNumber}" />
<input name="lineItem" id="lineItem" type="hidden" value="${lineItem}" />
<input name="originalFeesTotalLines" id="originalFeesTotalLines" type="hidden" value="${originalFeeDataCount}" />
<input type="hidden" name="copyNewFeesBean" id="copyNewFeesBean" value=""/>
<input name="newFeesAddLimit" id="newFeesAddLimit" type="hidden" value="${newFeesAddLimit}" />
<input name="reasonsDeleteOnly" id="reasonsDeleteOnly" type="hidden" value="" />
<input name="newFeesDeleteOnly" id="newFeesDeleteOnly" type="hidden" value="" />
<input name="denialComments" id="denialComments" type="hidden" value="" />
<%-- <input name="reasonTotalLines" id="reasonTotalLines" type="hidden" value="${reasonDataCount}" />  --%>
<input name="companyId" id="companyId" type="hidden"  value="${customerReturnRequestResultBean.companyId}" />
<input name="opsEntityId" id="opsEntityId" type="hidden"  value="${customerReturnRequestResultBean.opsEntityId}" />
<input name="hub" id="hub" type="hidden"  value="${customerReturnRequestResultBean.hub}" />
<input name="inventoryGroup" id="inventoryGroup" type="hidden"  value="${customerReturnRequestResultBean.inventoryGroup}" />
<input name="currencyId" id="currencyId" type="hidden"  value="${customerReturnRequestResultBean.currencyId}" />
<input name="facilityId" id="facilityId" type="hidden"  value="${customerReturnRequestResultBean.facilityId}" />
<input name="customerServiceRepId" id="customerServiceRepId" type="hidden"  value="${customerReturnRequestResultBean.customerServiceRepId}" /> 
<input name="originalUnitPrice" id="originalUnitPrice" type="hidden"  value="${customerReturnRequestResultBean.originalUnitPrice}" />
<input name="priceGroupId" id="priceGroupId" type="hidden"  value="${customerReturnRequestResultBean.priceGroupId}" />
<input name="replacementItemId" id="replacementItemId" type="hidden"  value="${customerReturnRequestResultBean.replacementItemId}" />  
<input type="hidden" name="specListConcat" id="specListConcat" value="${customerReturnRequestResultBean.specIdList}"/>
<input type="hidden" name="detailConcat" id="detailConcat" value="${customerReturnRequestResultBean.specDetailList}"/>
<input type="hidden" name="specLibraryList" id="specLibraryList" value="${customerReturnRequestResultBean.specLibraryList}"/>
<input type="hidden" name="cocConcat" id="cocConcat" value="${customerReturnRequestResultBean.specCocList}"/>
<input type="hidden" name="coaConcat" id="coaConcat" value="${customerReturnRequestResultBean.specCoaList}"/>
<input type="hidden" name="customerId" id="customerId" value="${customerReturnRequestResultBean.customerId}"/>
<input name="catalogId" id="catalogId" type="hidden"  value="${customerReturnRequestResultBean.catalogId}" />
<input name="catalogCompanyId" id="catalogCompanyId" type="hidden"  value="${customerReturnRequestResultBean.catalogCompanyId}" />
</div>
<!-- Hidden elements end -->
<!-- Error Messages Ends -->
</tcmis:form>
<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;"></div>
</body>
</html:html>