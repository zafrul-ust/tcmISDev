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

function _simpleUpdate(act,val) { 
	  if( window['validateForm'] && !validateForm() ) return false;
	  if( !act ) act = 'uAction';
	  if( !val ) val = 'update';
  $(act).value = val;
  if( window['showPleaseWait'] ) 
	  showPleaseWait();
  else 
	  parent.showPleaseWait(); 
  {   var preval = '';
	  for(var i=0;i<haasGrid.getRowsNum();i++){
		  rowid = haasGrid.getRowId(i);
//		  alert( i+":"+rowid );
//		  alert( 'line2Map[i]:'+ line2Map[i]);
		  if( line2Map[i] != null ) {
			  preval = $v("startDateDisplay"+rowid);
			  if( preval == null ) preval = cellValue(rowid,"startDateDisplay");
//		  alert(i+":preval:"+ preval);		  
		  }
		  cell(rowid, "startDate").setValue( preval );
	  }
			
  }	  
  haasGrid.parentFormOnSubmit(); //prepare grid for data sending
  document.genericForm.submit();
  return false;
}

var color = "red";

function deleteStartDate() {
	var i = haasGrid.getRowIndex(selectedRowId);
	
    for( ; i > 0; i-- ) {
        var rowid = haasGrid.getRowId(i); 
		cell(rowid,'changed').setValue('deleteStartDate');
    	if( line2Map[i] == null ) {
    		haasGrid.rowsAr[ rowid ].cells[1].style.background = color;
    		haasGrid.rowsAr[ rowid ].cells[2].style.background = color;
//    		haasGrid.rowsAr[ selectedRowId ].cells[3].style.background = color;
		    continue;
    	} 
    	if( lineMap[i] == null ) {
    		haasGrid.rowsAr[ rowid ].cells[0].style.background = color;
    		haasGrid.rowsAr[ rowid ].cells[1].style.background = color;
    		haasGrid.rowsAr[ rowid ].cells[2].style.background = color;
//    		haasGrid.rowsAr[ selectedRowId ].cells[3].style.background = color;
    		haasGrid.rowsAr[ rowid ].cells[4].style.background = color;
    		haasGrid.rowsAr[ rowid ].cells[5].style.background = color;
//    		haasGrid.rowsAr[ selectedRowId ].cells[6].style.background = color;
    	}
    	else { 
        	haasGrid.rowsAr[ rowid ].cells[9].style.background = color;
        	haasGrid.rowsAr[ rowid ].cells[10].style.background = color;
        	haasGrid.rowsAr[ rowid ].cells[11].style.background = color;
        	haasGrid.rowsAr[ rowid ].cells[13].style.background = color;
        	haasGrid.rowsAr[ rowid ].cells[14].style.background = color;
    	}
		break;
    }
    i = haasGrid.getRowIndex(selectedRowId) +1;
    for( ; i < line2Map.length ; i++ ) {
        var rowid = haasGrid.getRowId(i); 
    	if( line2Map[i] ) break;
		cell(rowid,'changed').setValue('deleteStartDate');
    		haasGrid.rowsAr[ rowid ].cells[1].style.background = color;
    		haasGrid.rowsAr[ rowid ].cells[2].style.background = color;
    }

}

function markasdelete() {
	var i = haasGrid.getRowIndex(selectedRowId);
	cell(selectedRowId,'changed').setValue('markasdelete');
	if( line2Map[i] == null ) {
		haasGrid.rowsAr[ selectedRowId ].cells[1].style.background = color;
		haasGrid.rowsAr[ selectedRowId ].cells[2].style.background = color;
//		haasGrid.rowsAr[ selectedRowId ].cells[3].style.background = color;
		return; 
	} 
	if( lineMap[i] == null ) {
		haasGrid.rowsAr[ selectedRowId ].cells[4].style.background = color;
		haasGrid.rowsAr[ selectedRowId ].cells[5].style.background = color;
//		haasGrid.rowsAr[ selectedRowId ].cells[6].style.background = color;
		return;
	} 
	haasGrid.rowsAr[ selectedRowId ].cells[13].style.background = color;
	haasGrid.rowsAr[ selectedRowId ].cells[14].style.background = color;
//	haasGrid.rowsAr[ selectedRowId ].cells[10].style.background = color;
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
   {
  	columnId:"permission"
  },
  {
  	columnId:"opsEntityId"
//    ,columnName:'<fmt:message key="label.operatingentity"/>',
//	width:6
  },
  {
  	columnId:"priceGroupId"
  },
  {
	  	columnId:"priceGroupName",
	  	columnName:'<fmt:message key="label.pricelist"/>',
	  	width:4
  },
	{
		columnId:"catalogCompanyName",
	  	columnName:'<fmt:message key="label.catalogcompany"/>'
  	},
	{
		columnId:"catalogName",
	  	columnName:'<fmt:message key="label.catalog"/>'
  	},
<c:if test="${module == 'distribution'}">  
  { columnId:"partName",
	  columnName:'<fmt:message key="label.catalogitem"/>',
	  sorting:"haasStr",
	  tooltip:true,
	  width:6
	},
	{
	  	columnId:"specList",
	  	columnName:'<fmt:message key="label.specification"/>',
	  	tooltip:true,
	  	width:10
	},
	{ columnId:"description",
	  columnName:'<fmt:message key="label.description"/>',
	  sorting:"haasStr",
	  tooltip:true,
	  width:20
	},
//	{
//	    columnId:"specListId"
//		,columnName:'<fmt:message key="label.specgroup"/>',
//	    tooltip:true,
//	    width:3
//	},
</c:if>
<c:if test="${module != 'distribution'}">  
  	{
  		columnId:"partName",
  		columnName:'<fmt:message key="label.catpartno"/>',
  		width:6
  	},
  	{
	  	columnId:"description",
	  	columnName:'<fmt:message key="label.description"/>',
	  	width:20
	},
</c:if>  
	{
  		columnId:"startDateDisplay",
  		columnName:'<fmt:message key="label.startDate"/>'
  	},
	{
  		columnId:"baselinePrice",
  		columnName:'<fmt:message key="label.baseline"/>',
  		type:'hed'
  	},
	{
		columnId:"currencyId",
  		columnName:'<fmt:message key="label.currencyid"/>',
  		type:'hcoro'
	},
  {
  	columnId:"breakQuantityPermission"
  },
  {
  	columnId:"breakQuantity",
  	columnName:'<fmt:message key="label.quantity"/>',
  	type:"hed",
  	permission:true
  },
  {
  	columnId:"catalogPrice",
  	columnName:'<fmt:message key="label.unitprice"/>',
  	type:"hed"
  },
  {
	columnId:"dup"
//	,columnName:'<fmt:message key="label.duplicate"/>'
  },
  {
		columnId:"catPartNo"
  },
	{
		columnId:"companyId"
	},
	{
		columnId:"catalogId"
	},
	{
		columnId:"partGroupNo"
	},
	{
		columnId:"startDate"
	},
	{
		columnId:"oldStartDate"
	},
	{
		columnId:"endDate"
	},
	  {
	  	columnId:"oldBreakQuantity"
	  },
	  {
	  	columnId:"oldCatalogPrice"
	  },
	  {
		  	columnId:"catalogCompanyId"
	  },
		{
			columnId:"specListId"
		},
	{
		columnId:"changed"
	},
	  {
		  	columnId:"isParent"
	  }
	
    ];

function faketotal() {
	if( $v('totalLines') == '0' ) {
		$('totalLines').value = 1;
		setTimeout("parent.document.getElementById('footer').innerHTML=''",300);
		parent.document.getElementById('addparttoacllink').style["display"]="";
	}
	else
		parent.document.getElementById('addparttoacllink').style["display"]="none";
		
}

function closeTransitWin() {
	parent.closeTransitWin();
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="faketotal();resultOnLoadWithGrid(gridConfig);setTimeout('loadRowSpans()',100);">

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


function selectRightclick(rowId,cellInd){
// haasGrid.selectRowById(rowId,null,false,false);	
 selectRow(rowId,cellInd);
}


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
	    	startdate1 = cellValue(rowid1,"startDateDisplay")
		    startdateval1 = dateToIntString( startdate1 );
	    }
	    if( $('startDateDisplay'+rowid2) == null ) {
	    	startdate2 = cellValue(rowid2,"startDateDisplay")
		    startdateval2 = dateToIntString( startdate2 );
	    }
		}catch(ex){}

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

function dupline(rowId){

	if(	rowId == null ) rowId = selectedRowId;
	var ind = haasGrid.getRowIndex(rowId);
	var catprice = cellValue(rowId,"catalogPrice");
	var oldprice = cellValue(rowId,"oldCatalogPrice");
	var newid = (new Date()).valueOf();

		mymap = lineIdMap[rowId];
	    var orisize = mymap.length;
	    var firstlineind = haasGrid.getRowIndex(mymap[0]); //0
	    var secondlineind = firstlineind; // initially  //0
	    var secondlinenext = 0;// initially  //0
	    var secondlength = 0;// initially //0
		var upper = firstlineind + mymap.length - 1; //5
		var firstlinenext = firstlineind + mymap.length; //6
//	    ind = upper;
	    var newlineind = ind+1; //1
	    //
// resetting linemap rowspan	    
rowSpan = 1;	    
	 haasGrid.setRowspan(mymap[0],0,1);
     haasGrid.setRowspan(mymap[0],1,1);
     haasGrid.setRowspan(mymap[0],2,1);
     haasGrid.setRowspan(mymap[0],3,1);
     haasGrid.setRowspan(mymap[0],4,1);
     haasGrid.setRowspan(mymap[0],5,1);
     haasGrid.setRowspan(mymap[0],6,1);
     haasGrid.setRowspan(mymap[0],7,1);
<c:if test="${module == 'distribution'}">  
haasGrid.setRowspan(mymap[0],8,1);
</c:if>  
     
// resetting line2map rowspan
//     haasGrid.setRowspan(mymap[0],5,rowSpan*1);
	 for(var i = firstlineind; i < firstlinenext; i++ )
		if( line2Map[i] ) {
			if( i <= ind ) secondlineind = i;
			else if( !secondlinenext ) secondlinenext = i;
<c:if test="${module == 'distribution'}">			
haasGrid.setRowspan(mymap[i-firstlineind],9,1);
haasGrid.setRowspan(mymap[i-firstlineind],10,1);
haasGrid.setRowspan(mymap[i-firstlineind],11,1);
</c:if>
<c:if test="${module != 'distribution'}">			
haasGrid.setRowspan(mymap[i-firstlineind],8,1);
haasGrid.setRowspan(mymap[i-firstlineind],9,1);
haasGrid.setRowspan(mymap[i-firstlineind],10,1);
</c:if>
	 	}
	 if( !secondlinenext ) secondlinenext = upper+1;
	 secondlength = secondlinenext - secondlineind;
// use upper bound

// handling mymap	    
	    var mapind = ind - firstlineind + 1;
	    for(var j = mymap.length ; j>  mapind; j--) 
	    	mymap[j] = mymap[j-1];
	    mymap[mapind] = newid;
	    
// handling linemap3	    
		for( var i = lineMap3.length; i > newlineind; i-- )
			lineMap3[i] = lineMap3[i-1]; 
	    lineMap3[newlineind] = lineMap3[firstlineind];//(lineMap3[newlineind-1]+1)%2;
		 
// handling linemap	    
		for( var i = lineMap.length; i > firstlinenext; i-- )
			lineMap[i] = lineMap[i-1];
		if( firstlinenext != lineMap.length )
	    	lineMap[firstlinenext] = null;
	    lineMap[firstlineind] = mymap.length;
// handling line2map
	    for( var i = line2Map.length; i > secondlinenext; i-- )
			line2Map[i] = line2Map[i-1];
		if( secondlinenext != line2Map.length )
	    	line2Map[secondlinenext] = null;
	    line2Map[secondlineind] = secondlength+1;
// handling lineIdMap	     
		lineIdMap[""+newid] = mymap;
// handling rowidmap
		for( var i = rowIdMap.length; i > newlineind; i-- )
			rowIdMap[i] = rowIdMap[i-1]; 
		rowIdMap[newlineind] = newid;
		count = 0 ;

	
/*
			    columnId:"permission"
			  	columnId:"opsEntityId",
			  	columnId:"catalogCompanyId",
			  	columnId:"partName",
			  	columnId:"description",
			  	columnId:"expireDate",
			  	columnId:"breakQuantityPermission"
			  	columnId:"breakQuantity",
			  	columnId:"catalogPrice",
				columnId:"dup",
*/				
		var thisrow = haasGrid.addRow(newid,"",newlineind);
		  haasGrid.cells(newid, count++).setValue('Y');
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"opsEntityId"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"priceGroupId"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"priceGroupName"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"catalogCompanyName"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"catalogName"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"partName"));
<c:if test="${module == 'distribution'}">  
//	  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"catPartNo"));
	  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"specList"));
</c:if>		  
		  //$v('today')
//		  var datehtml = '<input class="inputBox pointer" id="startDateDisplay'+newid+'" type="text" value="'+''+'" size="8" readonly onClick="return myGetCalendar('+newid+')"/>';
//		  haasGrid.cells(newid, count++).setCValue(datehtml);
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"description"));
		  haasGrid.cells(newid, count++).setValue('');
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"baselinePrice"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"currencyId"));
		  haasGrid.cells(newid, count++).setValue('Y');
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"breakQuantity"));
		  haasGrid.cells(newid, count++).setValue(catprice);//cellValue(rowId,"catalogPrice"));
		  var html = '<input type="button" class="smallBtns" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" name="editButton" value="'+messagesData.dupline+'"  onclick="dupline('+newid+')"/>';
		  haasGrid.cells(newid, count++).setCValue(html);
//		  haasGrid.cells(newid, count++).setValue('');
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"catPartNo"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"companyId"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"catalogId"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"partGroupNo"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"startDate"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"oldStartDate"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"endDate"));
//		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"currencyId"));
		  haasGrid.cells(newid, count++).setValue('');//cellValue(rowId,"oldBreakQuantity"));
		  haasGrid.cells(newid, count++).setValue('');//cellValue(rowId,"oldCatalogPrice"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"catalogCompanyId"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"specListId"));
		  haasGrid.cells(newid, count++).setValue('Y');
		  haasGrid.cells(newid, count++).setValue('N');

		setTimeout('loadRowSpans()',100);

		
}

function newFutureCpp(){

	var rowid0 = selectedRowId;

	var part = cellValue(rowid0,'priceGroupId')+"-"+cellValue(rowid,'partName')+"-"+cellValue(rowid,'specListId');
	
	var rowid1 = partMap1[part];
	var rowid2 = partMap2[part];

	if( rowid2 != null ) { alert('Cannot new start date'); return ; }

	var rowId = selectedRowId;
	var ind = haasGrid.getRowIndex(rowId);
	var newid = (new Date()).valueOf();
    
	var mymap = lineIdMap[rowId];
	    var firstlineind = haasGrid.getRowIndex(mymap[0]); //0
		var upper = firstlineind + mymap.length - 1; //5
		var firstlinenext = firstlineind + mymap.length; //6
	    ind = upper;
	    var newlineind = ind+1; //1
	    //
// handling mymap	    
		mymap[mymap.length] = newid;
	    
// handling linemap3	    
		for( var i = lineMap3.length; i > ind; i-- )
			lineMap3[i] = lineMap3[i-1]; 
//	    lineMap3[newlineind] = lineMap3[firstlineind];//(lineMap3[newlineind-1]+1)%2;
// handling linemap	    
		for( var i = lineMap.length; i > firstlinenext; i-- )
			lineMap[i] = lineMap[i-1];
		if( firstlinenext != lineMap.length )
	    	lineMap[firstlinenext] = null;
	    lineMap[firstlineind] = mymap.length;
// handling line2map
	    for( var i = line2Map.length; i > newlineind; i-- )
			line2Map[i] = line2Map[i-1];
	    line2Map[newlineind] = 1;
// handling lineIdMap	     
		lineIdMap[""+newid] = mymap;
// handling rowidmap
		for( var i = rowIdMap.length; i > newlineind; i-- )
			rowIdMap[i] = rowIdMap[i-1]; 
		rowIdMap[newlineind] = newid;
		count = 0 ;
/*
			    columnId:"permission"
			  	columnId:"opsEntityId",
			  	columnId:"catalogCompanyId",
			  	columnId:"partName",
			  	columnId:"description",
			  	columnId:"expireDate",
			  	columnId:"breakQuantityPermission"
			  	columnId:"breakQuantity",
			  	columnId:"catalogPrice",
				columnId:"dup",
*/				
		var thisrow = haasGrid.addRow(newid,"",newlineind);
		  haasGrid.cells(newid, count++).setValue('Y');
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"opsEntityId"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"priceGroupId"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"priceGroupName"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"catalogCompanyName"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"catalogName"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"partName"));
	    <c:if test="${module == 'distribution'}">  
//			  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"catPartNo"));
			  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"specList"));
		</c:if>		  
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"description"));
		  var datehtml = '<input class="inputBox pointer" id="startDateDisplay'+newid+'" type="text" value="'+$v('today')+'" size="8" readonly onClick="return myGetCalendar('+newid+')"/>';
		  haasGrid.cells(newid, count++).setCValue(datehtml);
//		  haasGrid.cells(newid, count++).setValue('');//cellValue(rowId,"startDate"));---
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"baselinePrice"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"currencyId"));
		  haasGrid.cells(newid, count++).setValue('N');
		  haasGrid.cells(newid, count++).setValue(1);
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"catalogPrice"));
		  var html = '<input type="button" class="smallBtns" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" name="editButton" value="'+messagesData.dupline+'"  onclick="dupline('+newid+')"/>';
		  haasGrid.cells(newid, count++).setCValue(html);
//		  haasGrid.cells(newid, count++).setValue('');
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"catPartNo"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"companyId"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"catalogId"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"partGroupNo"));
		  haasGrid.cells(newid, count++).setValue('');//cellValue(rowId,"startDate"));
		  haasGrid.cells(newid, count++).setValue('');//cellValue(rowId,"oldStartDate"));
		  haasGrid.cells(newid, count++).setValue('');//cellValue(rowId,"endDate"));
//		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"currencyId"));
		  haasGrid.cells(newid, count++).setValue('');//cellValue(rowId,"oldBreakQuantity"));
		  haasGrid.cells(newid, count++).setValue('');//cellValue(rowId,"oldCatalogPrice"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"catalogCompanyId"));
		  haasGrid.cells(newid, count++).setValue(cellValue(rowId,"specListId"));
		  haasGrid.cells(newid, count++).setValue('Y');
		  haasGrid.cells(newid, count++).setValue('Y');

		  partMap2[part] = partMap1[part];
		  partMap1[part] = newid;

		  alertrowid = true;
		  
		setTimeout('loadRowSpans()',100);
		
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
	var newid = (new Date()).valueOf();

		mymap = lineIdMap[rowid0];
	    var firstlineind = haasGrid.getRowIndex(mymap[0]); //0
		var upper = firstlineind + mymap.length - 1; //5
		var firstlinenext = firstlineind + mymap.length; //6
	    ind = upper;
	    var newlineind = ind+1; //1
	    //
// handling mymap	    
		mymap = new Array();
	    mymap[0] = newid;
	    
// handling linemap3	    
		for( var i = lineMap3.length; i > ind; i-- )
			lineMap3[i] = (lineMap3[i-1]+1)%2; 
//	    lineMap3[newlineind] = lineMap3[firstlineind];//(lineMap3[newlineind-1]+1)%2;
// handling linemap	    
		for( var i = lineMap.length; i > newlineind; i-- )
			lineMap[i] = lineMap[i-1];
	    lineMap[newlineind] = 1;
// handling line2map
	    for( var i = line2Map.length; i > newlineind; i-- )
			line2Map[i] = line2Map[i-1];
	    line2Map[newlineind] = 1;
// handling lineIdMap	     
		lineIdMap[""+newid] = mymap;
// handling rowidmap
		for( var i = rowIdMap.length; i > newlineind; i-- )
			rowIdMap[i] = rowIdMap[i-1]; 
		rowIdMap[newlineind] = newid;
		count = 0 ;

	
		var thisrow = haasGrid.addRow(newid,"",newlineind);
		  haasGrid.cells(newid, count++).setValue('Y');
		  haasGrid.cells(newid, count++).setValue( partinfo.opsEntityId);
		  haasGrid.cells(newid, count++).setValue( partinfo.priceGroupId );
		  haasGrid.cells(newid, count++).setValue( partinfo.priceGroupName );
		  haasGrid.cells(newid, count++).setValue( partinfo.catalogCompanyName );
		  haasGrid.cells(newid, count++).setValue( partinfo.catalogName );
		  haasGrid.cells(newid, count++).setValue( partinfo.partName ); //catalog Item.
<c:if test="${module == 'distribution'}">  
//		  haasGrid.cells(newid, count++).setValue(partinfo.catPartNo);// parse this.
		  haasGrid.cells(newid, count++).setValue(partinfo.specList);
</c:if>		  
haasGrid.cells(newid, count++).setValue( partinfo.description );
/*
  partinfo.catalogItem.....
  partinfo.description
  partinfo.specListId}',
  partinfo.specList}"/>'
 */
//		  var todayval = dateToIntString($v('today'));
		  var datehtml = '<input class="inputBox pointer" id="startDateDisplay'+newid+'" type="text" value="'+$v('today')+'" size="8" readonly onClick="return getCalendar($('+"'startDateDisplay"+newid+"'),null,null,$('today'))"+'"/>';
		  haasGrid.cells(newid, count++).setCValue(datehtml);
		  haasGrid.cells(newid, count++).setValue('');
		  haasGrid.cells(newid, count++).setValue( partinfo.currencyId );
		  haasGrid.cells(newid, count++).setValue('N');
		  haasGrid.cells(newid, count++).setValue('1');
		  haasGrid.cells(newid, count++).setValue("1");
		  var html = '<input type="button" class="smallBtns" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" name="editButton" value="'+messagesData.dupline+'"  onclick="dupline('+newid+')"/>';
		  haasGrid.cells(newid, count++).setCValue(html);
//		  haasGrid.cells(newid, count++).setValue('');
		  haasGrid.cells(newid, count++).setValue(partinfo.catPartNo);
/// need to do this one..
		  haasGrid.cells(newid, count++).setValue( partinfo.companyId ); // pg owner company id...?
		  haasGrid.cells(newid, count++).setValue( partinfo.catalogId );
		  haasGrid.cells(newid, count++).setValue( partinfo.partGroupNo );
		  haasGrid.cells(newid, count++).setValue('');
		  haasGrid.cells(newid, count++).setValue('');
		  haasGrid.cells(newid, count++).setValue('01-'+month_abbrev_Locale_Java[pageLocale][0]+'-4000'); // double check this one.
		  haasGrid.cells(newid, count++).setValue('');//cellValue(rowid0,"oldBreakQuantity"));
		  haasGrid.cells(newid, count++).setValue('');//cellValue(rowid0,"oldCatalogPrice"));
		  haasGrid.cells(newid, count++).setValue( partinfo.catalogCompanyId );
		  haasGrid.cells(newid, count++).setValue( partinfo.specListId);
		  haasGrid.cells(newid, count++).setValue('Y');
		  haasGrid.cells(newid, count++).setValue('Y');
   
		  partMap1[partinfo.priceGroupId+"-"+partinfo.partName+"-"+partinfo.specListId] = newid;
	  
			setTimeout('loadRowSpans()',100);
			setTimeout('haasGrid.selectRowById('+newid+')',200);
			setTimeout('selectRow('+newid+',1)',300);
}

function addpart2(partinfo) {

	
	var size = haasGrid.getRowsNum();
	var newid = (new Date()).valueOf();
	var	mymap = new Array();
    	mymap[0] = newid;

	if( !size ) {
		lineMap3[0] = 0;
		lineMap[0] = 1;
		line2Map[0] = 1;
		lineIdMap[""+newid] = mymap;
		rowIdMap[0] = newid;
	}
	else {
		var rowid0 = haasGrid.getRowId(size-1);
		var map1 = lineIdMap[rowid0];
	    var firstlineind = haasGrid.getRowIndex(map1[0]); //24
		var upper = firstlineind + map1.length - 1; //24
	    ind = upper; //24
	    var newlineind = ind+1; //25
	    //
// handling mymap	    
	    
// handling linemap3	    
		for( var i = lineMap3.length; i > ind; i-- )
			lineMap3[i] = (lineMap3[i-1]+1)%2; 
//	    lineMap3[newlineind] = lineMap3[firstlineind];//(lineMap3[newlineind-1]+1)%2;
// handling linemap	    
		for( var i = lineMap.length; i > newlineind; i-- )
			lineMap[i] = lineMap[i-1];
	    lineMap[newlineind] = 1;
// handling line2map
	    for( var i = line2Map.length; i > newlineind; i-- )
			line2Map[i] = line2Map[i-1];
	    line2Map[newlineind] = 1;
// handling lineIdMap	     
		lineIdMap[""+newid] = mymap;
// handling rowidmap
		for( var i = rowIdMap.length; i > newlineind; i-- )
			rowIdMap[i] = rowIdMap[i-1]; 
		rowIdMap[newlineind] = newid;
	}

		count = 0 ;

	
		var thisrow = haasGrid.addRow(newid,"",newlineind);
		  haasGrid.cells(newid, count++).setValue('Y');
		  haasGrid.cells(newid, count++).setValue( partinfo.opsEntityId);
		  haasGrid.cells(newid, count++).setValue( partinfo.priceGroupId );
		  haasGrid.cells(newid, count++).setValue( partinfo.priceGroupName );
		  haasGrid.cells(newid, count++).setValue( partinfo.catalogCompanyName );
		  haasGrid.cells(newid, count++).setValue( partinfo.catalogName );
		  haasGrid.cells(newid, count++).setValue( partinfo.partName ); //catalog Item.
<c:if test="${module == 'distribution'}">  
//		  haasGrid.cells(newid, count++).setValue(partinfo.catPartNo);// parse this.
		  haasGrid.cells(newid, count++).setValue(partinfo.specList);
</c:if>		  
haasGrid.cells(newid, count++).setValue( partinfo.description );
/*
  partinfo.catalogItem.....
  partinfo.description
  partinfo.specListId}',
  partinfo.specList}"/>'
 */
//		  var todayval = dateToIntString($v('today'));
		  var datehtml = '<input class="inputBox pointer" id="startDateDisplay'+newid+'" type="text" value="'+$v('today')+'" size="8" readonly onClick="return getCalendar($('+"'startDateDisplay"+newid+"'),null,null,$('today'))"+'"/>';
		  haasGrid.cells(newid, count++).setCValue(datehtml);
		  haasGrid.cells(newid, count++).setValue('');
		  haasGrid.cells(newid, count++).setValue( partinfo.currencyId );
		  haasGrid.cells(newid, count++).setValue('N');
		  haasGrid.cells(newid, count++).setValue('1');
		  haasGrid.cells(newid, count++).setValue("1");
		  var html = '<input type="button" class="smallBtns" onmouseover="this.className='+"'"+'smallBtns smallBtnsOver'+"'"+'" onMouseout="this.className='+"'"+'smallBtns'+"'"+'" name="editButton" value="'+messagesData.dupline+'"  onclick="dupline('+newid+')"/>';
		  haasGrid.cells(newid, count++).setCValue(html);
//		  haasGrid.cells(newid, count++).setValue('');
		  haasGrid.cells(newid, count++).setValue(partinfo.catPartNo);
/// need to do this one..
		  haasGrid.cells(newid, count++).setValue( partinfo.companyId ); // pg owner company id...?
		  haasGrid.cells(newid, count++).setValue( partinfo.catalogId );
		  haasGrid.cells(newid, count++).setValue( partinfo.partGroupNo );
		  haasGrid.cells(newid, count++).setValue('');
		  haasGrid.cells(newid, count++).setValue('');
		  haasGrid.cells(newid, count++).setValue('01-'+month_abbrev_Locale_Java[pageLocale][0]+'-4000'); // double check this one.
		  haasGrid.cells(newid, count++).setValue('');//cellValue(rowid0,"oldBreakQuantity"));
		  haasGrid.cells(newid, count++).setValue('');//cellValue(rowid0,"oldCatalogPrice"));
		  haasGrid.cells(newid, count++).setValue( partinfo.catalogCompanyId );
		  haasGrid.cells(newid, count++).setValue( partinfo.specListId );
		  haasGrid.cells(newid, count++).setValue('Y');
		  haasGrid.cells(newid, count++).setValue('Y');
   
		  partMap1[partinfo.priceGroupId+"-"+partinfo.partName+"-"+partinfo.specListId] = newid;
	  
			setTimeout('loadRowSpans()',100);
			setTimeout('haasGrid.selectRowById('+newid+')',200);
			setTimeout('selectRow('+newid+',1)',300);
}

var newpartrowid = null;
function newpart(rowid0){

	parent.showTransitWin('<fmt:message key="label.pricelist"/>');
	if( rowid0 == null ) rowid0 = selectedRowId;
	
	var ind = haasGrid.getRowIndex(rowid0);

	var loc = "pricegrouppartsearch.do?uAction=new&companyId=" + gg("companyId")+ 
	"&opsEntityId=" + gg("opsEntityId") + 
	"&priceGroupId=" + gg("priceGroupId") +
	"&priceGroupName=" + gg("priceGroupName") ;
	var winId= 'pricegrouppartsearch';//+$v("prNumber");
	parent.children[parent.children.length] = openWinGeneric(loc,winId.replace('.','a'),"1024","600","yes","50","50","20","20","no");
	newpartrowid = rowid0;
	return ;
		
}

function newpart2(){
	var loc = "pricegrouppartsearch.do?uAction=new&companyId=" + pp("companyId")+ 
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
	<c:set var="prePar" value="curPar"/>
</c:forEach>

<c:set var="todayval">
	<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="yyyy-MM-dd"/>
</c:set>
		
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
<tcmis:jsReplace value="${bean.partDesc}" var="partDesc"/>
<tcmis:jsReplace value="${bean.specList}" var="specList"/>

{ id:${dataCount},
 data:[
  'Y',
  '${bean.opsEntityId}',
  '${bean.priceGroupId}', 
  '<tcmis:jsReplace value="${bean.priceGroupName}" />', 
  '<tcmis:jsReplace value="${bean.catalogCompanyName}" />', 
  '<tcmis:jsReplace value="${bean.catalogName}" />',  
  '<tcmis:jsReplace value="${bean.partName}" />',
  <c:if test="${module == 'distribution'}">  
      '${bean.specListId}:${specList}',
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
    'Y',
    '${bean.opsEntityId}',
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
    '${dup}',
    '<tcmis:jsReplace value="${bean.catPartNo}" />',
    '${bean.companyId}',
    '${bean.catalogId}',
    '${bean.partGroupNo}',
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
<input type="hidden" name="tmpdate" id="tmpdate" value=""/>
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

function loadRowSpans()
{

//    haasGrid.setRowspan(2,5,3);
 //   return ;
/*	
for(var i=0;i<haasGrid.getRowsNum();i++){
   try
   {
//	      alert(i);
//	      alert( lineMap[i] );
//	      alert( rowIdMap[i] );
     var row2Span = line2Map[i];

     if( row2Span != null ) {
         haasGrid.setRowspan(rowIdMap[i],5,row2Span*1);
     }
   }
   catch (ex)
   {
      alert(":"+i+"::"+row2Span);
      alert( line2Map[i] );
      alert( rowIdMap[i] );
   }
 } 
  */ for(var i=0;i<haasGrid.getRowsNum();i++){
	   try
	   { var step = 1;
//		      alert(i);
//		      alert( lineMap[i] );
//		      alert( rowIdMap[i] );
     var row2Span = line2Map[i];

     if( row2Span != null ) {
<c:if test="${module == 'distribution'}">  
haasGrid.setRowspan(rowIdMap[i],9,row2Span*1);
haasGrid.setRowspan(rowIdMap[i],10,row2Span*1);
haasGrid.setRowspan(rowIdMap[i],11,row2Span*1);
</c:if>         
<c:if test="${module != 'distribution'}">  
haasGrid.setRowspan(rowIdMap[i],8,row2Span*1);
haasGrid.setRowspan(rowIdMap[i],9,row2Span*1);
haasGrid.setRowspan(rowIdMap[i],10,row2Span*1);
</c:if>         
     }
	     var rowSpan = lineMap[i];
	     if( rowSpan == null || rowSpan == 1 ) continue;
//	     alert( i+":"+lineMap[i]+"::"+rowIdMap[i]);
	     
	     haasGrid.setRowspan(rowIdMap[i],0,rowSpan*1);
	     haasGrid.setRowspan(rowIdMap[i],1,rowSpan*1);
	     haasGrid.setRowspan(rowIdMap[i],2,rowSpan*1);
	     haasGrid.setRowspan(rowIdMap[i],3,rowSpan*1);
	     haasGrid.setRowspan(rowIdMap[i],4,rowSpan*1);
	     haasGrid.setRowspan(rowIdMap[i],5,rowSpan*1);
		 haasGrid.setRowspan(rowIdMap[i],6,rowSpan*1);
		 haasGrid.setRowspan(rowIdMap[i],7,rowSpan*1);
<c:if test="${module == 'distribution'}">  
	 haasGrid.setRowspan(rowIdMap[i],8,rowSpan*1);
</c:if>         
	   }
	   catch (ex)
	   {
	      alert(i);
	      alert( line2Map[i]+":"+lineMap[i] );
	      alert( rowIdMap[i] );
	   }

 }
 /*Need to call this only if the grid has rowspans > 1*/
 //haasGrid._fixAlterCss();
}

var selectedRowId=null;
var preClass = null;
var preRowId = null;

function fixRowColor(thisrow,rowind){
	var cname = "ev_haas rowselected";
	thisrow.className = cname;
}
var alertrowid = false;

function selectRow(rowId,cellInd){
	
	rowId0 = arguments[0];
    colId0 = arguments[1];

//    haasGrid.selectRowById(rowId0);
// color style stuff
    
	if( preRowId != null) {
		if(	lineMap3[haasGrid.getRowIndex(preRowId)] == 1 )
			preClass="odd_haas";
		else
			preClass="ev_haas";
		mymap = lineIdMap[preRowId];
		for(var j = 0;j < mymap.length; j++) 
			haasGrid.rowsAr[ mymap[j] ].className = preClass;
	}

	mymap = lineIdMap[rowId0];
	for(var j = 0;j < mymap.length; j++) 
		fixRowColor( haasGrid.rowsAr[ mymap[j] ] );//,true,false,false);

    preRowId      = rowId0;
    selectedRowId = rowId0;

//    if( cellInd == 9 && $('startDateDisplay'+rowId0) != null ) return;
	if( alertrowid ) {
    	//alert(rowId+":"+cellInd);
	}
    var vvitems = new Array();
	vvitems[vvitems.length] = "text=Add Part To Price Group "+cellValue(rowId0,'priceGroupName')+" ;url=javascript:newpart();"
<c:if test="${module == 'distribution'}">  
	if( cellInd < 10 ) 
</c:if>	
<c:if test="${module != 'distribution'}">  
	if( cellInd < 9 ) 
</c:if>	
    {
//   	 aI( "text=Add Part To Price Group;url=javascript:newpart();" );
//	 aI( "text=New Start Date;url=javascript:newFutureCpp();" );
//	 aI( "text=New Price Break;url=javascript:dupline();" );
//	 aI( "text=Mark As Delete;url=javascript:markasdelete();" );
		replaceMenu('addcpp',vvitems);
		toggleContextMenu('addcpp');
		return; 
    }
	vvitems[vvitems.length] = "text=New Start Date;url=javascript:newFutureCpp();";
	var changed = cellValue(rowId0,"changed");

	if( changed != 'deleteStartDate' )
	for( kk = haasGrid.getRowIndex(rowId0); kk>= 0; kk-- ) {
		if( line2Map[kk] ) {
			if( $('startDateDisplay'+haasGrid.getRowId(kk)) != null ) { 
				vvitems[vvitems.length] = "text=Delete Start Date;url=javascript:deleteStartDate();";
			}
			break;
		}
	}
<c:if test="${module == 'distribution'}">  
	if( cellInd < 14 ) 
</c:if>	
<c:if test="${module != 'distribution'}">  
	if( cellInd < 13 ) 
</c:if>	
	{ 
		replaceMenu('addcpp',vvitems);
    	toggleContextMenu('addcpp');
    	return ;
	}

	vvitems[vvitems.length] = "text=New Price Break;url=javascript:dupline();";

	if( cellValue(rowId0,"breakQuantityPermission") == "Y" && changed != 'markasdelete' && changed != 'deleteStartDate' )
		vvitems[vvitems.length] = "text=Mark As Delete;url=javascript:markasdelete();";
	replaceMenu('addcpp',vvitems);
    toggleContextMenu('addcpp');
	
//    

}

var gridConfig = {
		divName:'PriceListViewBean', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not.
		evenoddmap:lineMap3,
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
		onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:selectRow   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//		onBeforeSorting:_onBeforeSorting
	};

<!-- permission stuff here --!>
showUpdateLinks = true;
//-->
</script>

</body>
</html>
