<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
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

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srndRowSpan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<c:set var="module">
	 <tcmis:module/>
</c:set>

<title>
<fmt:message key="label.pricelist"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>",
dupline:'Add CPP'};// use common resource when terms finalized...'<fmt:message key="receiving.label.duplicateline"/>'};

var currencyId = new Array(

<c:forEach var="cbean" items="${vvCurrencyBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
	{text:'<tcmis:jsReplace value="${cbean.currencyId}"/>',value:'${cbean.currencyId}'}
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
    	if( i == 1 ) mm_deleteItem(menuname,1);
      }
}

var qarr = new Array();
function validateForm() {
	  for(var i=0;i<haasGrid.getRowsNum();i++){
		  rowid = haasGrid.getRowId(i);
		  if(haasGrid.haasRowIsRendered(rowid)) {
	 		  if( cellValue(rowid, 'isParent') == 'Y' ) {
				 // log("row is Parent - " + rowid);
				  qarr = new Array();
			  }
			  else {
				  var qty = cellValue(rowid, 'breakQuantity');
				  //log("row is NOT Parent - " + rowid + " and qty == " + qty + "  and qarr[qty] == " + qarr[qty]);
				  if( qty == 1 || qarr[qty] != null) {
					  var changed = cellValue(rowid, 'changed'); 
					  if (changed != 'markasdelete' && changed != 'markasdelete') { 
						  $('breakQuantity'+rowid).focus();
						  alert(messagesData.validvalues+"\n"+"<fmt:message key="label.quantity"/>");
						  return false;
					  }
				  }
				  qarr[qty] = qty;
			  }
		  }
	  }
	  return true;
}
function _simpleUpdate(act,val) { 
	  if( window['validateForm'] && !validateForm() ) return false;
	  if( !act ) act = 'uAction';
	  if( !val ) val = 'update';
	  $(act).value = val;
	  if( window['showPleaseWait'] ) 
		  showPleaseWait();
	  else 
		  parent.showPleaseWait(); 
	  var preval = '';
	  for(var i=0;i<haasGrid.getRowsNum();i++){
		  rowid = haasGrid.getRowId(i);
		  if(haasGrid.haasRowIsRendered(rowid)) {
			  if( line2Map[i] != null ) {
				  preval = $v("startDateDisplay"+rowid);
				  if( preval == null ) preval = cellValue(rowid,"startDateDisplay");
			  }
			  if (!preval.match(/^<INPUT/i)) {
			  	cell(rowid, "startDate").setValue( preval );
				if (rowSpanLvl2Map[i] > 1) {
					// Handle startDate for various pricebreaks
					for (var z = 1; z < rowSpanLvl2Map[i]; z++) {
						cell(rowid + z, "startDate").setValue( preval );
					}
				}
			  }
		  }
	  }		

	  
	  haasGrid.parentFormOnSubmit(); //prepare grid for data sending
	  document.genericForm.submit();
	  return false;
}

var colorStyle = "background-color: red";

function deleteStartDate() {
	var rowId = haasGrid.getSelectedRowId();
	var rowIndex = haasGrid.haasGetRowSpanEndIndexLvl2(rowId) - 1;
	var colIndex = haasGrid.getColIndexById('changed');
	var parentIndex = haasGrid.haasGetRowSpanStartLvl2(rowId);
	
	for( ; rowIndex >= parentIndex; rowIndex-- ) {
		rowId = haasGrid.getRowId(rowIndex); 
		cell(rowId, colIndex).setValue('deleteStartDate');
		haasGrid._haas_json_data.rows[rowIndex].data[colIndex] = 'deleteStartDate';

		haasGrid.setCellTextStyle(rowId, 9, colorStyle);
		haasGrid.setCellTextStyle(rowId, 10, colorStyle);
		haasGrid.setCellTextStyle(rowId, 11, colorStyle);
		haasGrid.setCellTextStyle(rowId, 13, colorStyle);
		haasGrid.setCellTextStyle(rowId, 14, colorStyle);
	}

}

function markasdelete(rowId) {
	var rowId = haasGrid.getSelectedRowId();
	var rowIndex = haasGrid.getRowIndex(rowId);
	var colIndex = haasGrid.getColIndexById('changed');
	cell(rowId, colIndex).setValue('markasdelete');
	haasGrid._haas_json_data.rows[rowIndex].data[colIndex] = 'markasdelete';
	haasGrid.setCellTextStyle(rowId, 13, colorStyle);
	haasGrid.setCellTextStyle(rowId, 14, colorStyle);
}

function resetMarkedRow(rowId) {
	var changedIndex = haasGrid.getColIndexById('changed');
	var dateIndex = haasGrid.getColIndexById('startDateDisplay');
	var rowIndex = haasGrid.getRowIndex(rowId);
	if (rowIndex >= 0) {
		if (haasGrid._haas_json_data.rows[rowIndex].data[dateIndex] == 'newStartDate') {
			var datehtml = '<input class="inputBox pointer" id="startDateDisplay'+rowId+'" type="text" value="'+$v('today')+'" size="8" readonly onClick="return myGetCalendar('+rowId+')"/>';
			cell(rowId,"startDateDisplay").setCValue(datehtml);
		}
		if (haasGrid._haas_json_data.rows[rowIndex].data[changedIndex] == 'deleteStartDate') {
			haasGrid.setCellTextStyle(rowId, 9, colorStyle);
			haasGrid.setCellTextStyle(rowId, 10, colorStyle);
			haasGrid.setCellTextStyle(rowId, 11, colorStyle);
			haasGrid.setCellTextStyle(rowId, 13, colorStyle);
			haasGrid.setCellTextStyle(rowId, 14, colorStyle);
		}
		else if (haasGrid._haas_json_data.rows[rowIndex].data[changedIndex] == 'markasdelete') {
			haasGrid.setCellTextStyle(rowId, 13, colorStyle);
			haasGrid.setCellTextStyle(rowId, 14, colorStyle);
		}
	}
}

with ( milonic=new menuname("addcpp") ) {
	 top="offset=2";
	 style=submenuStyle;
	 itemheight=17;
	// style = contextStyle;
	// margin=3;
	 aI( "text=Add Part To Price Group;url=javascript:newpart();" );
	 aI( "text=New Start Date;url=javascript:newFutureCpp();" );
	 aI( "text=New Price Break;url=javascript:dupline();" );
	 aI( "text=Mark As Delete;url=javascript:markasdelete();" );
}

drawMenus();

var lineMap3 = new Array();

var config = [
   	{columnId:"permission"},
	{columnId:"opsEntityId"}, //    ,columnName:'<fmt:message key="label.operatingentity"/>',width:6
	{columnId:"priceGroupId"},
	{columnId:"priceGroupName", columnName:'<fmt:message key="label.pricelist"/>', width:4},
	{columnId:"catalogCompanyName", columnName:'<fmt:message key="label.catalogcompany"/>', width:4},
	{columnId:"catalogName", columnName:'<fmt:message key="label.catalog"/>'},
	<c:choose>
		<c:when test="${module == 'distribution'}">  
		  	{columnId:"partName", columnName:'<fmt:message key="label.catalogitem"/>',sorting:"haasStr", tooltip:true, width:6 },
			{columnId:"specList", columnName:'<fmt:message key="label.specification"/>', tooltip:true, width:10},
			{columnId:"description",columnName:'<fmt:message key="label.description"/>', sorting:"haasStr", tooltip:true, width:16 },
		</c:when>
		<c:otherwise>  
		  	{columnId:"partName", columnName:'<fmt:message key="label.catpartno"/>', width:6 },
			{columnId:"description",columnName:'<fmt:message key="label.description"/>', width:20 },
		</c:otherwise>
	</c:choose>  
	{columnId:"startDateDisplay", columnName:'<fmt:message key="label.startDate"/>', submit:false},
	{columnId:"baselinePrice",columnName:'<fmt:message key="label.baseline"/>', type:'hed'},
	{columnId:"currencyId", columnName:'<fmt:message key="label.currencyid"/>', type:'hcoro', width:5},
  	{columnId:"breakQuantityPermission"},
	{columnId:"breakQuantity", columnName:'<fmt:message key="label.quantity"/>', type:"hed", permission:true, width:4},
  	{columnId:"catalogPrice", columnName:'<fmt:message key="label.unitprice"/>', type:"hed"},
	{columnId:"dup"},
	{columnId:"catPartNo"},
	{columnId:"companyId"},
	{columnId:"catalogId"},
	{columnId:"partGroupNo"},
	{columnId:"startDate"},
	{columnId:"oldStartDate"},
	{columnId:"endDate"},
	{columnId:"oldBreakQuantity"},
	{columnId:"oldCatalogPrice"},
	{columnId:"catalogCompanyId"},
	{columnId:"specListId"},
	{columnId:"changed"},
	{columnId:"isParent"}
    ];

function faketotal() {
	if( $v('totalLines') == '0' ) {
		$('totalLines').value = 1;
		setTimeout("parent.document.getElementById('footer').innerHTML=''",300);
//		parent.document.getElementById('addparttoacllink').style["display"]="";
	}
//	else
//		parent.document.getElementById('addparttoacllink').style["display"]="none";
	if( '${param.priceGroupId}' )
		parent.document.getElementById('addparttoacllink').style["display"]="";
		
}

function closeTransitWin() {
	parent.closeTransitWin();
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="faketotal();resultOnLoadWithGrid(gridConfig);">

<tcmis:form action="/pricelistresults.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">
<div class="backGroundContent">
<c:set var="dataCount" value='${0}'/>


<div id="PriceListViewBean" style="width:100%;height:400px;"></div>
<c:if test="${ 1 == 1 }" >
<%--
<c:if test="${!empty beanCollection}" >
--%>
<!-- Search results start -->
<script type="text/javascript">
<!--

/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
<c:set var="showUpdateLink" value='N'/>
/*Storing the data to be displayed in a JSON object array.*/

/*I am looping thru the map I created in the JSP to set the row spans for different columns*/

function myGetCalendar(rowid)
{
	var part = cellValue(rowid,'priceGroupId')+"-"+cellValue(rowid,'partName')+"-"+cellValue(rowid,'specListId');

	var rowid1 = partMap1[part];
	var rowid2 = partMap2[part];

//	mymap = lineIdMap[rowid];
	var rowid0 = rowid;
	var todayval = dateToIntString($v('today'));

	var startdateval1 = dateToIntString($v('startDateDisplay'+rowid1));
	var startdate1  = $v('startDateDisplay'+rowid1);
	var startdateval2 = dateToIntString($v('startDateDisplay'+rowid2));
	var startdate2  = $v('startDateDisplay'+rowid2);
		try {
	    if( $('startDateDisplay'+rowid1) == null ) {
	    	startdate1 = cellValue(rowid1,"startDateDisplay");
		    startdateval1 = dateToIntString( startdate1 );
	    }
	    if( $('startDateDisplay'+rowid2) == null ) {
	    	startdate2 = cellValue(rowid2,"startDateDisplay")
		    startdateval2 = dateToIntString( startdate2 );
	    }
		}catch(ex){}

	var field = 'startDateDisplay'+rowid0;
	
	if( rowid0 == rowid2 ) {
		return getCalendar($('startDateDisplay'+rowid0),null,startdate1,$v('today'));
	}
	if( rowid0 == rowid1 ) {
		if( todayval > startdateval2 ) {
			return getCalendar($('startDateDisplay'+rowid0),null,null,$v('today'));
		}
		else  {
			return getCalendar($('startDateDisplay'+rowid0),startdate2);
		}
	}
	else 
		return getCalendar($('startDateDisplay'+rowid0));
}

function dupline(arg0){
	var rowId = arg0;
	if(rowId == null ) {
		rowId = haasGrid.getSelectedRowId();
	}
	//var rowId = haasGrid.getSelectedRowId();
	var ind = haasGrid.getRowIndex(rowId);

	var parentIndex = haasGrid.haasGetRowSpanStart(rowId);
	var parentIndex2 = haasGrid.haasGetRowSpanStartLvl2(rowId);
	var newLinePosition = haasGrid.haasGetRowSpanEndIndexLvl2(rowId); 

	var catprice = cellValue(rowId,"catalogPrice");
	var oldprice = cellValue(rowId,"oldCatalogPrice");


	mymap = lineIdMap[rowId];
	     
	// handling mymap	    
	for(var j = mymap.length ; j>  newLinePosition; j--) 
		mymap[j] = mymap[j-1];
	mymap[newLinePosition] = newLinePosition;
	    
	// handling lineIdMap	     
	lineIdMap[""+(newLinePosition+1)] = mymap;

	// handling rowidmap
	for( var i = rowIdMap.length; i > newLinePosition; i-- )
		rowIdMap[i] = rowIdMap[i-1]; 
	rowIdMap[newLinePosition] = newLinePosition;
	
	    
	var newRowData = ['Y',
			  cellValue(rowId,"opsEntityId"),
			  cellValue(rowId,"priceGroupId"),
			  cellValue(rowId,"priceGroupName"),
			  cellValue(rowId,"catalogCompanyName"),
			  cellValue(rowId,"catalogName"),
			  cellValue(rowId,"partName"),
				<c:if test="${module == 'distribution'}">  
					  cellValue(rowId,"specList"),
				</c:if>		  
			  cellValue(rowId,"description"),
			  '',
			  cellValue(rowId,"baselinePrice"),
			  cellValue(rowId,"currencyId"),
			  'Y',
			  cellValue(rowId,"breakQuantity"),
			  catprice,
			  //var html = '<input type="button" class="smallBtns" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" name="editButton" value="'+messagesData.dupline+'"  onclick="dupline('+newid+')"/>';
			  //haasGrid.cells(newid, count++).setCValue(html);
			  '', // Edit cell goes here
			  cellValue(rowId,"catPartNo"),
			  cellValue(rowId,"companyId"),
			  cellValue(rowId,"catalogId"),
			  cellValue(rowId,"partGroupNo"),
			  cellValue(rowId,"startDate"),
			  cellValue(rowId,"oldStartDate"),
			  cellValue(rowId,"endDate"),
			  '', //cellValue(rowId,"oldBreakQuantity"),
			  '', //cellValue(rowId,"oldCatalogPrice"),
			  cellValue(rowId,"catalogCompanyId"),
			  cellValue(rowId,"specListId"),
			  'Y',
			  'N'];
	haasGrid.haasAddRowToRowSpan(newRowData,newLinePosition, parentIndex, parentIndex2);
	haasGrid.haasRenderRow(haasGrid.getRowId(parentIndex));
	haasGrid.selectRow(parentIndex);
}

function newFutureCpp(){
	var rowid0 = selectedRowId;

	var part = cellValue(rowid0,'priceGroupId')+"-"+cellValue(rowid0,'partName')+"-"+cellValue(rowid0,'specListId');
	
	var rowid1 = partMap1[part];
	var rowid2 = partMap2[part];

	if( rowid2 != null ) { alert('Cannot add a new start date.'); return ; }

	var rowId = haasGrid.getSelectedRowId();
	var ind = haasGrid.getRowIndex(rowId);

	var parentIndex = haasGrid.haasGetRowSpanStart(rowId);
	var newLinePosition = haasGrid.haasGetRowSpanEndIndex(rowId); 

	mymap = lineIdMap[rowId];
	     
	// handling mymap	    
	for(var j = mymap.length ; j>  newLinePosition; j--) 
		mymap[j] = mymap[j-1];
	mymap[newLinePosition] = newLinePosition;

	// handling lineIdMap	     
	lineIdMap["" + (newLinePosition +1)] = mymap;

	// handling rowidmap
	for( var i = rowIdMap.length; i > newLinePosition; i-- )
		rowIdMap[i] = rowIdMap[i-1]; 
	rowIdMap[newLinePosition] = newLinePosition;
	
	var newRowData = ['Y',
			  cellValue(rowId,"opsEntityId"),
			  cellValue(rowId,"priceGroupId"),
			  cellValue(rowId,"priceGroupName"),
			  cellValue(rowId,"catalogCompanyName"),
			  cellValue(rowId,"catalogName"),
			  cellValue(rowId,"partName"),
		    	<c:if test="${module == 'distribution'}">  
				  cellValue(rowId,"specList"),
			</c:if>		  
			  cellValue(rowId,"description"),
			  //var datehtml = '<input class="inputBox pointer" id="startDateDisplay'+newid+'" type="text" value="'+$v('today')+'" size="8" readonly onClick="return myGetCalendar('+newid+')"/>';
			  //haasGrid.cells(newid, count++).setCValue(datehtml,
			  'newStartDate',//cellValue(rowId,"startDate"),---
			  cellValue(rowId,"baselinePrice"),
			  cellValue(rowId,"currencyId"),
			  'N',
			  1,
			  cellValue(rowId,"catalogPrice"),
			  //var html = '<input type="button" class="smallBtns" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" name="editButton" value="'+messagesData.dupline+'"  onclick="dupline('+newid+')"/>';
			  //haasGrid.cells(newid, count++).setCValue(html,
			  '',
			  cellValue(rowId,"catPartNo"),
			  cellValue(rowId,"companyId"),
			  cellValue(rowId,"catalogId"),
			  cellValue(rowId,"partGroupNo"),
			  $v('today'),//cellValue(rowId,"startDate"),
			  cellValue(rowId,"startDate"),//cellValue(rowId,"oldStartDate"),
			  '',//cellValue(rowId,"endDate"),
			  '',//cellValue(rowId,"oldBreakQuantity"),
			  '',//cellValue(rowId,"oldCatalogPrice"),
			  cellValue(rowId,"catalogCompanyId"),
			  cellValue(rowId,"specListId"),
			  'Y',
			  'Y'];
		  
	haasGrid.haasAddRowToRowSpan(newRowData,newLinePosition, parentIndex);
	
	haasGrid.haasRenderRow(newLinePosition);
	newLinePosition++;
	var datehtml = '<input class="inputBox pointer" id="startDateDisplay'+newLinePosition+'" type="text" value="'+$v('today')+'" size="8" readonly onClick="return myGetCalendar('+newLinePosition+')"/>';
	cell(newLinePosition,"startDateDisplay").setCValue(datehtml);
	var html = '<input type="button" class="smallBtns" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" name="editButton" value="'+messagesData.dupline+'"  onclick="dupline('+newLinePosition+')"/>';
	cell(newLinePosition,"dup").setCValue(html);
		
	
	  partMap2[part] = partMap1[part];
	  partMap1[part] = newLinePosition;
	
	  alertrowid = true;

	haasGrid.haasRenderRow(haasGrid.getRowId(parentIndex));
	haasGrid.selectRow(parentIndex);	
		
}

var children = new Array();
function pp(name) {
	var value = parent.$v(name);
//	alert( value );
	return encodeURIComponent(value);
}

function gg(name) {
	var value = null;
	value = cellValue(selectedRowId,name);
//	alert( value );
	return encodeURIComponent(value);
}

function addpart(partinfo) {
	rowid0 = newpartrowid;

	if( !rowid0 ) return addpart2(partinfo);
		
	
	var ind = haasGrid.getRowIndex(rowid0);
	var rowId = haasGrid.getSelectedRowId();
	var parentIndex = ind;
	var newLinePosition = ind + 1; 

	var newlineind = newLinePosition;
	
	    //
// handling mymap	    
	mymap = new Array();
	mymap[0] = newLinePosition;
	    
// handling linemap3	    
// handling linemap	    
// handling line2map
// handling lineIdMap	     
		lineIdMap[""+newid] = mymap;
// handling rowidmap
		for( var i = rowIdMap.length; i > newlineind; i-- )
			rowIdMap[i] = rowIdMap[i-1]; 
		rowIdMap[newlineind] = newid;


		var newRowData = ['Y',
				partinfo.opsEntityId,
				partinfo.priceGroupId ,
				partinfo.priceGroupName ,
				partinfo.catalogCompanyName ,
				partinfo.catalogName ,
				partinfo.partName , //catalog Item.
				<c:if test="${module == 'distribution'}">partinfo.specList,</c:if>		  
				partinfo.description ,
				'newStartDate',
				'',
				partinfo.currencyId ,
				'N',
				'1',
				'1',
				'',
				partinfo.catPartNo,
				partinfo.companyId , // pg owner company id...?
				partinfo.catalogId ,
				partinfo.partGroupNo ,
				'',
				'',
				'01-'+month_abbrev_Locale_Java[pageLocale][0]+'-4000', // double check this one.
				'',//cellValue(rowid0,"oldBreakQuantity"),
				'',//cellValue(rowid0,"oldCatalogPrice"),
				partinfo.catalogCompanyId ,
				partinfo.specListId,
				'Y',
				'Y'];

		haasGrid.haasAddRowToRowSpan(newRowData,newLinePosition);
		
		haasGrid.haasRenderRow(newLinePosition);
		newLinePosition++;
		var datehtml = '<input class="inputBox pointer" id="startDateDisplay'+newLinePosition+'" type="text" value="'+$v('today')+'" size="8" readonly onClick="return myGetCalendar('+newLinePosition+')"/>';
		cell(newLinePosition,"startDateDisplay").setCValue(datehtml);
		var html = '<input type="button" class="smallBtns" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" name="editButton" value="'+messagesData.dupline+'"  onclick="dupline('+newLinePosition+')"/>';
		cell(newLinePosition,"dup").setCValue(html);
			  
		partMap1[partinfo.priceGroupId+"-"+partinfo.partName+"-"+partinfo.specListId] = newLinePosition;

		haasGrid.selectRow(newLinePosition);
}

function addpart2(partinfo) {
	var size = haasGrid.getRowsNum();
	var newid = 1;
	var mymap = new Array();
    	mymap[0] = newid;

	if( !size ) {
		lineMap3[0] = 0;
		lineMap[0] = 1;
		line2Map[0] = 1;
		lineIdMap[""+newid] = mymap;
		rowIdMap[0] = newid;
	}
	else {
		newid = size;
		// handling lineIdMap	     
		lineIdMap[""+newid] = mymap;
		// handling rowidmap
		rowIdMap[newid] = newid;
	}

	var newRowData = ['Y',
			   partinfo.opsEntityId,
			   partinfo.priceGroupId ,
			   partinfo.priceGroupName ,
			   partinfo.catalogCompanyName ,
			   partinfo.catalogName ,
			   partinfo.partName , //catalog Item.
			<c:if test="${module == 'distribution'}">  
			  partinfo.specList,
			</c:if>		  
			 partinfo.description ,
			'newStartDate',
			  '',
			   partinfo.currencyId ,
			  'N',
			  '1',
			  '1',
			  '',
			  partinfo.catPartNo,
			   partinfo.companyId , // pg owner company id...?
			   partinfo.catalogId ,
			   partinfo.partGroupNo ,
			  '',
			  '',
			  '01-'+month_abbrev_Locale_Java[pageLocale][0]+'-4000', // double check this one.
			  '',//cellValue(rowid0,"oldBreakQuantity"),
			  '',//cellValue(rowid0,"oldCatalogPrice"),
			   partinfo.catalogCompanyId ,
			   partinfo.specListId ,
			  'Y',
			  'Y'];

	haasGrid.haasAddRowToRowSpan(newRowData,newid);
	
	haasGrid.haasRenderRow(newid);
	resetMarkedRow();
	haasGrid.selectRow(newid);
	newid++;
	var datehtml = '<input class="inputBox pointer" id="startDateDisplay'+newid+'" type="text" value="'+$v('today')+'" size="8" readonly onClick="return getCalendar($('+"'startDateDisplay"+newid+"'),null,null,$('today'))"+'"/>';
	cell(newid,"startDateDisplay").setCValue(datehtml);
	var html = '<input type="button" class="smallBtns" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" name="editButton" value="'+messagesData.dupline+'"  onclick="dupline('+newid+')"/>';
	cell(newid,"dup").setCValue(html);

	
	 partMap1[partinfo.priceGroupId+"-"+partinfo.partName+"-"+partinfo.specListId] = newid;

}

var newpartrowid = null;
function newpart(rowid0){

	parent.showTransitWin('<fmt:message key="label.pricelist"/>');
	if( rowid0 == null ) rowid0 = selectedRowId;
	
	var ind = haasGrid.getRowIndex(rowid0);

	var loc = "pricegrouppartsearch.do?uAction=new&companyId=" + parent.getOpsCompanyid( gg("priceGroupId") )+ 
	"&opsEntityId=" + gg("opsEntityId") + 
	"&priceGroupId=" + gg("priceGroupId") +
	"&priceGroupName=" + gg("priceGroupName") ;
	var winId= 'pricegrouppartsearch';//+$v("prNumber");
	parent.children[parent.children.length] = openWinGeneric(loc,winId.replace('.','a'),"1024","600","yes","50","50","20","20","no");
	newpartrowid = rowid0;
	return ;
		
}

function newpart2(){
	var loc = "pricegrouppartsearch.do?uAction=new&companyId=" + parent.getOpsCompanyid( pp("priceGroupId") )+ 
	"&opsEntityId=" + pp("opsEntityId") + 
	"&priceGroupId=" + pp("priceGroupId") +
	"&priceGroupName=" + pp("priceGroupName");
	var winId= 'pricegrouppartsearch';//+$v("prNumber");
	children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"1024","600","yes","50","50","20","20","no");
	return ;
		
}


var map = null;
var lineMap = new Array();
var line2Map = new Array();
var lineIdMap = new Array();
var rowIdMap = new Array();
var partMap1 = new Array();
var partMap2 = new Array();

<c:set var="gridind" value="0"/>
<c:set var="prePar" value=""/>
<c:set var="samePartCount" value="0"/>
<c:set var="colorIndex" value="-1"/>
<c:forEach var="bean" items="${beanCollection}" varStatus="status">
	<c:set var="curPar" value="${bean.priceGroupId}-${bean.partName}-${bean.specListId}"/>
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
    lineIdMap[""+${gridind}] = map;
	map[map.length] = ${gridind} ;
    rowIdMap[rowIdMap.length] = ${gridind} ;

    <c:forEach var="bean2" items="${status.current.priceBreakCollection}" varStatus="status2">
		lineMap3[${gridind}] = ${colorIndex%2} ;
<%--
		<c:if test="${status2.index==0}">
			line2Map[${gridind}] = ${resultSize};
		</c:if>
--%>
	<c:set var="gridind" value="${gridind+1}"/> 
		lineIdMap[""+${gridind}] = map;
		map[map.length] = ${gridind} ;
	    rowIdMap[rowIdMap.length] = ${gridind} ;
	</c:forEach>
	<c:set var="prePar" value="${curPar}"/>
</c:forEach>

<c:set var="todayval">
	<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="yyyy-MM-dd"/>
</c:set>

<%-- Check update permission here --%>
<c:set var="updatePermission" value='N'/>
<tcmis:opsEntityPermission indicator="true" userGroupId="updateCustomerPriceList" opsEntityId="${param.opsEntityId}">
	<c:set var="updatePermission" value='Y'/> 
</tcmis:opsEntityPermission >
				
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${beanCollection}" varStatus="status">
<fmt:formatDate var="startDatev" value="${bean.startDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="endDatev" value="${bean.endDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="startDate" value="${bean.startDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="expireDate" value="${bean.endDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="expireYear" value="${bean.endDate}" pattern="yyyy"/>
<c:if test="${expireYear == '3000'}">
	<c:set var="expireDate"><fmt:message key="label.indefinite"/></c:set>
</c:if>
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
		<input class="inputBox pointer" id="startDateDisplay${dataCount}" type="text" value="${startDate}" size="8" readonly onClick="return myGetCalendar(${dataCount})"/>
	</c:set>
</c:if>

<tcmis:jsReplace value="${dup}" var="dup"/>
<tcmis:jsReplace value="${bean.partDesc}" var="partDesc" processMultiLines="true"/>
<tcmis:jsReplace value="${bean.specList}" var="specList" />

{ id:${dataCount},
 data:[
   '${updatePermission}',
  '${bean.opsEntityId}',
  '${bean.priceGroupId}',  
  '<tcmis:jsReplace value="${bean.priceGroupName}" />', 
  '<tcmis:jsReplace value="${bean.catalogCompanyName}" />',  
  '<tcmis:jsReplace value="${bean.catalogName}" />', 
  '<tcmis:jsReplace value="${bean.partName}" />',
  <c:if test="${module == 'distribution'}">  
      "${bean.specListId}:${specList}",
  </c:if>
  '${partDesc}', 
  '${startDate}',
  '${bean.baselinePrice}',
  '${bean.currencyId}',
  'N',
  '1',
  '${bean.catalogPrice}',
  '${dup}',
  '<tcmis:jsReplace value="${bean.catPartNo}" />',
  '${bean.companyId}',
  '${bean.catalogId}',
  '${bean.partGroupNo}',
  '${startDatev}',
  '${startDatev}',
  '${endDatev}',
//  '${bean.currencyId}',
  '1',
  '${bean.catalogPrice}',
  '${bean.catalogCompanyId}',
  '${bean.specListId}',
  '',
  'Y'
  ]
}

  <c:forEach var="bean2" items="${status.current.priceBreakCollection}" varStatus="status2">
  <c:if test="${dataCount > 0}">,</c:if>
  <c:set var="dataCount" value='${dataCount+1}'/>
  <c:set var="dup">
  <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="editButton" value="Add CPP" onclick="dupline(${dataCount})" />
  </c:set>
  <tcmis:jsReplace value="${dup}" var="dup"/>
  <tcmis:jsReplace value="${bean.specList}" var="specList"/>
  
  { id:${dataCount},
   data:[
    '${updatePermission}',
    '${bean.opsEntityId}',//  "bean.operatingEntityName"
    '${bean.priceGroupId}',  
    '<tcmis:jsReplace value="${bean.priceGroupName}" />',  
    '<tcmis:jsReplace value="${bean.catalogCompanyName}" />',  
    '<tcmis:jsReplace value="${bean.catalogName}" />', 
    '<tcmis:jsReplace value="${bean.partName}" />',
    <c:if test="${module == 'distribution'}">  
	'${bean.specListId}:${specList}',
	</c:if>
    '${partDesc}',
    '${expireDate}',
    '${bean.baselinePrice}',
    '${bean.currencyId}',
    'Y',
    '${bean2.breakQuantity}',
    '${bean2.catalogPrice}',
//    '',
    '${dup}',
    '<tcmis:jsReplace value="${bean.catPartNo}" />',
    '${bean.companyId}',
    '${bean.catalogId}',
    '<tcmis:jsReplace value="${bean.partGroupNo}" />',
    '${startDatev}',
    '${startDatev}',
    '${endDatev}',
    '${bean2.breakQuantity}',
    '${bean2.catalogPrice}',
    '${bean.catalogCompanyId}',
    '${bean.specListId}',
    '',
    'N'
    ]
  }
  </c:forEach>

</c:forEach>
]};

//-->
</script>

<!-- end of grid div. -->
</c:if>

<c:if test="${empty beanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>

<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input type="hidden" name="uAction" id="uAction" value="update"/>
<input type="hidden" name="searchField" id="searchField" value="${param.searchField}"/>
<input type="hidden" name="searchMode" id="searchMode" value="${param.searchMode}"/>
<input type="hidden" name="searchArgument" id="searchArgument" value="${param.searchArgument}"/>
<input type="hidden" name="checkbox" id="checkbox" value="${param.checkbox}"/>
<input name='today' id='today' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'  />
<input type="hidden" name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}"/>
<input type="hidden" name="priceGroupId" id="priceGroupId" value="${param.priceGroupId}"/>
<input name="minHeight" id="minHeight" type="hidden" value="210"/>
</div>

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
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

<c:choose>
	<c:when test="${module == 'distribution'}">  
		var rowSpanCols = [0,1,2,3,4,5,6,7,8];
		var rowSpanLvl2Cols = [9,10,11];
	</c:when>         
	<c:otherwise>  
		var rowSpanCols = [0,1,2,3,4,5,6,7];
		var rowSpanLvl2Cols = [8,9,10];
	</c:otherwise>         
</c:choose>
var rowSpanLvl2Map = line2Map;

var selectedRowId=null;
var alertrowid = false;

function rightClick(rowId, cellInd){
	popdown();
	var parentIndex = 0;
	var rowIndex = 0;
	selectedRowId = rowId;

	if (cellInd < haasGrid.getColIndexById('startDateDisplay')) {
		toggleContextMenu('contextMenu');
		return; 
    	}

	var vvitems = new Array();
	vvitems[vvitems.length] = "text=New Start Date;url=javascript:newFutureCpp();";
	var changed = cellValue(rowId,"changed");

	if( changed != 'deleteStartDate' ) {
		parentIndex = haasGrid.haasGetRowSpanStart(rowId);
		for( rowIndex = haasGrid.getRowIndex(rowId); rowIndex >= parentIndex; rowIndex-- ) {
			if( line2Map[rowIndex] ) {
				if( $('startDateDisplay'+haasGrid.getRowId(rowIndex)) != null ) { 
					vvitems[vvitems.length] = "text=Delete Start Date;url=javascript:deleteStartDate();";
				}
				break;
			}
		}
	}
	
	if (cellInd >= haasGrid.getColIndexById('breakQuantity') && changed != 'deleteStartDate') { 
		vvitems[vvitems.length] = "text=New Price Break;url=javascript:dupline();";

		if( cellValue(rowId,"breakQuantityPermission") == "Y" && changed != 'markasdelete' ) {
			vvitems[vvitems.length] = "text=Mark As Delete;url=javascript:markasdelete();";
		}
	}

	replaceMenu('addcpp',vvitems);
    	toggleContextMenu('addcpp');
}

var gridConfig = {
		divName:'PriceListViewBean', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not.
		noSmartRender:false,
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
		onRightClick:rightClick,
		onAfterHaasRenderRow:resetMarkedRow   
};

<!-- permission stuff here --!>

 <tcmis:opsEntityPermission indicator="true" userGroupId="updateCustomerPriceList" opsEntityId="${param.opsEntityId}">
    showUpdateLinks = true;
 </tcmis:opsEntityPermission>

</script>

</body>
</html>
