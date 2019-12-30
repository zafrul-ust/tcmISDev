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
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
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

<c:set var="hasCatalogManagementPermission" value='Y'/>

<c:set var="module">
	 <tcmis:module/>
</c:set>

<title>
 Catalog Vendor Management
</title>

<script language="JavaScript" type="text/javascript">
<!--


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
	duplicateitem:'<fmt:message key="label.duplicateitem"/>',
	addeditadditcharges:'<fmt:message key="label.addeditadditcharges"/>',
	xxpositiveinteger:'<fmt:message key="label.xxpositiveinteger"/>',
	fromQuantity:'<fmt:message key="label.fromquantity"/>',
	pleaseselectarowforupdate:"<fmt:message key="label.pleaseselectarowforupdate"/>",
	needPositiveInteger:"Need to be a positive Integer:", 
	needNumber:"Need to be a number:",
	startdatelessstopdate:"Start date needs to be before stop date"
};



var currencyId = new Array(
	<c:forEach var="cbean" items="${vvCurrencyBeanCollection}" varStatus="status">
	<c:if test="${status.index > 0}">,</c:if>
		{text:'<tcmis:jsReplace value="${cbean.currencyId}"/>',value:'${cbean.currencyId}'}
	</c:forEach>
);


var task = new Array(
		{text:'Task 1',value:'Task 1'},
		{text:'Task 2',value:'Task 2'},
		{text:'Task 3',value:'Task 3'},
		{text:'Task 4',value:'Task 4'}
	);

var status1 = new Array(
		{text:'Active',value:'A'},
	    {text:'Inactive',value:'I'}
	);

var billedByComponent = new Array(
		{text:'Y',value:'Y'},
	    {text:'N',value:'N'}
	);


var opsEntityId = new Array(
		{text:'',value:''}
		<c:forEach var="opsEntity" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status">
			,{text:'<tcmis:jsReplace value="${opsEntity.operatingEntityName}"/>',value:'${opsEntity.opsEntityId}'}
		</c:forEach>
	);

<c:set var="activeCount" value="0"/>

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
		var rowsNum = beanGrid.getRowsNum();
		var selectedRowCount = 0;
		for ( var p = 1; p <= rowsNum; p++) {
			if (gridCellValue(beanGrid, p, "ok") == "true") {
				selectedRowCount++;
			}
			// percentage
			if ( !isNonnegativeInteger( gridCellValue(beanGrid, p, config[11].columnId ), true ) ) {
				alert( messagesData.needPositiveInteger + config[11].columnName );
				return false;
			}
			// addlIngredBaseQty
			if ( !isFloat( gridCellValue(beanGrid, p, config[15].columnId ), true ) ) {
				alert( messagesData.needNumber + config[15].columnName );
				return false;
			}
			// addlIngredBundleQty
			if ( !isFloat( gridCellValue(beanGrid, p, config[16].columnId ), true ) ) {
				alert( messagesData.needNumber + config[16].columnName );
				return false;
			}
			// unitPrice
			if ( !isFloat( gridCellValue(beanGrid, p, config[17].columnId ), true ) ) {
				alert( messagesData.needNumber + config[17].columnName );
				return false;
			}
			// addlIngredUnitPrice
			if ( !isFloat( gridCellValue(beanGrid, p, config[18].columnId ), true ) ) {
				alert( messagesData.needNumber + config[18].columnName );
				return false;
			}
			// sourcingUnitPrice
			if ( !isFloat( gridCellValue(beanGrid, p, config[22].columnId ), true ) ) {
				alert( messagesData.needNumber + config[22].columnName );
				return false;
			}
			startDateVal = dateToIntString( gridCellValue(beanGrid, p, "startDate" ) );
			stopDateVal = dateToIntString( gridCellValue(beanGrid, p, "stopDate" ) );
			if( startDateVal != "" && stopDateVal != "" && stopDateVal < startDateVal ) {
				alert( messagesData.startdatelessstopdate );
				return false;
			}
		}
		if( selectedRowCount ==0 ) {
			alert(messagesData.pleaseselectarowforupdate);
			return false;
		}
/* don't need to check unchecked...
        for ( var p = 1; p <= rowsNum; p++) {
			// percentage
			if ( !isPositiveInteger( gridCellValue(beanGrid, p, config[11].columnId ), true ) ) {
				alert( messagesData.needPositiveInteger + config[11].columnName );
				return false;
			}
		}
*/		
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
	haasGrid.parentFormOnSubmit(); //prepare grid for data sending
	document.genericForm.submit();
	return false;
}

function priceChanged(rowid,columnid) {
}

var config = [
	{columnId:"permission"},
	{ columnId:"ok", type:'hchstatus'
		<c:if test="${hasCatalogManagementPermission  == 'Y'}">
		 , columnName:'<fmt:message key="label.ok"/>',
		  align:'center',
		  width:4
		</c:if>
	},
	{columnId:"catalogVendorAssignmentId"},
	{columnId:"supplier"},
	{columnId:"supplierName", columnName:'<fmt:message key="label.supplier"/>', width:10, tooltip:true},
	{columnId:"task", columnName:'<fmt:message key="label.task"/>', width:8, tooltip:true},
	{columnId:"localeCode", columnName:'<fmt:message key="label.locale"/>', width:5, tooltip:true},
	
	{columnId:"itemId", columnName:'Item ID', width:5, tooltip:true},
	{columnId:"itemDesc", columnName:'Item Description', width:20, tooltip:true},
	{columnId:"addlIngredItemId"},
	{columnId:"sourcingItemId"},
	
	{columnId:"percentage", columnName:'<fmt:message key="label.percentage"/>', width:5, type:'hed', align:'right'},
	{columnId:"status1", columnName:'<fmt:message key="label.status"/>', type:'hcoro', width:7},// fix name problem...
	{columnId:"startDate", columnName:'<fmt:message key="label.startDate"/>',	type:'hcal'},
	{columnId:"stopDate", columnName:'End Date',	type:'hcal'},
	{columnId:"addlIngredBaseQty", columnName:'Addl Ingred Base Qty', width:7, type:'hed', align:'right'},

	{columnId:"addlIngredBundleQty", columnName:'Addl Ingred Bundle Qty', width:7, type:'hed', align:'right'},
	{columnId:"unitPrice", columnName:'<fmt:message key="label.unitprice"/>', width:5, type:'hed', align:'right'},
	{columnId:"addlIngredUnitPrice", columnName:'Addl Ingred Unit Price', width:7, type:'hed', align:'right'},
	{columnId:"currencyId", columnName:'<fmt:message key="label.currencyid"/>', type:'hcoro', width:7},//, onChange:parentChanged
	{columnId:"currencyDescription"},

	{columnId:"catalogQueueItemId"},
	{columnId:"sourcingUnitPrice", columnName:'Sourcing Unit Price', width:7, type:'hed', align:'right'},
	{columnId:"opsEntityId", columnName:'Operating Entity', width:20, tooltip:true, type:'hcoro'},
	{columnId:"billedByComponent", columnName:'Billed By Component', width:5, type:'hcoro'}
    ];
    


function faketotal() {
	if( $v('totalLines') == '0' ) {
		$('totalLines').value = 1;
		//parent.document.getElementById('updateResultLink').innerHTML='';
		setTimeout("parent.document.getElementById('footer').innerHTML=''",300);		 
	}
	s="";
	s = ' | <a href="javascript:newAssignment('+$v('supplier')+",'"+$v('supplierName')+"')"+'">Add Assignment To '+$v('supplierName')+"</a>";
	try {
	parent.document.getElementById('additemtoacllink').innerHTML = s;
	<c:if test="${hasCatalogManagementPermission  == 'Y'}">
		parent.document.getElementById('additemtoacllink').style["display"]="";
	</c:if>
	<c:if test="${hasCatalogManagementPermission  != 'Y'}">
		parent.document.getElementById('additemtoacllink').style["display"]="none";
	</c:if>
	} catch(ex){};
}

function faketotal2() {
	try { 
	if( $v('selRow') >= 0 ) {
		beanGrid.selectRow($v('selRow'));
		}
	} catch(ex){};
}


function closeTransitWin() {
	parent.closeTransitWin();
}

<c:set var="permission" value="Y"/>
<c:set var="supplyPathPermission" value="Y"/>
var multiplePermissions = true;

// Build up the array for the columns which use different permissions
var permissionColumns = new Array();
permissionColumns = {
        "supplyPath" : true, "roundToMultiple" : true
        
};

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="faketotal();resultOnLoadWithGrid(gridConfig);faketotal2();">

<tcmis:form action="/catalogsupplierresults.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">
<div class="backGroundContent">
<c:set var="dataCount" value='${0}'/>


<div id="CatalogVendorAssignmentBean" style="width:100%;height:400px;"></div>
<c:set var="colorClass" value=''/>
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

var updateSupplyPath = true;


var children = new Array();
function pp(name) {
	var value = parent.$v(name);
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

<c:set var="gridind" value="0"/>
<c:set var="prePar" value=""/>
<c:set var="samePartCount" value="0"/>
<c:set var="colorIndex" value="-1"/>

<c:set var="prePar" value=""/>


<c:set var="selRow" value='-1'/>

var jsonMainData = {
rows:[
<c:forEach var="bean" items="${beanCollection}" varStatus="status">
<c:if test="${bean.catalogVendorAssignmentId == newCatalogVendorAssignmen }">
	<c:set var="selRow" value='${dataCount}'/>
</c:if>
<fmt:setLocale value="en_US"/>
<fmt:setLocale value="${pageLocale}" scope="page"/>

<c:if test="${dataCount > 0}">,</c:if>
<c:set var="dataCount" value='${dataCount+1}'/>
<c:set var="dup">
    <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="editButton" value="Add CPP" onclick="dupline(${dataCount})" />
</c:set>

<fmt:formatDate var="startDateVal" value="${bean.startDate}" pattern="yyyy-MM-dd"/>
<c:if test="${ startDateVal > todayval }">
	<c:set var="startDate">
		<input class="inputBox pointer" id="startDateDisplay${dataCount}" type="text" value="${startDate}" size="9" readonly onClick="return myGetCalendar(${dataCount})"/>
	</c:set>
</c:if>

<tcmis:jsReplace value="${dup}" var="dup"/>
<tcmis:jsReplace value="${bean.itemDesc}" var="itemDesc" processMultiLines="true"/>

{ id:${dataCount},'class':"${colorClass}",
	  data:[
           '${hasCatalogManagementPermission}',
                   'N',
           '${bean.catalogVendorAssignmentId}',
           '${bean.supplier}',
           '${bean.supplierName}',
           '${bean.task}',
           '${bean.localeCode}',
           
           '${bean.itemId}',
           '${itemDesc}',
           '${bean.addlIngredItemId}',
           '${bean.sourcingItemId}',

           '${bean.percentage}',
           '${bean.status}',
           '<fmt:formatDate value="${bean.startDate}" pattern="${dateFormatPattern}"/>',
           '<fmt:formatDate value="${bean.stopDate}" pattern="${dateFormatPattern}"/>',
           '${bean.addlIngredBaseQty}',

           '${bean.addlIngredBundleQty}',
           '${bean.unitPrice}',
           '${bean.addlIngredUnitPrice}',
           '${bean.currencyId}',
           '${bean.currencyDescription}',

           '${bean.catalogQueueItemId}',
           '${bean.sourcingUnitPrice}',
           '${bean.opsEntityId}',
           '${bean.billedByComponent}'
	   ]
	 }
<c:set var="prePar" value="${curPar}"/>
</c:forEach>
]};

//-->
</script>
<c:if test="${supplyPathPermission == 'Y'}">
<script language="JavaScript" type="text/javascript">
<!--
	updateSupplyPath = true;
// -->    	
</script>		
</c:if>

<!-- end of grid div. -->


<c:if test="${empty beanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/> <c:if test="${ ! param.supplierName }" > <a href="javascript:newAssignment('"${param.supplier}','${param.supplierName}')">Add Assignment To ${param.supplierName}</a></c:if>
  </td>
 </tr>
</table>
</c:if>

<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input type="hidden" name="uAction" id="uAction" value="update"/>
<%-- seriously,... who made inDefinite_startDate required...--%>
<input type="hidden" name="inDefinite_startDate " id="inDefinite_startDate" value=""/>
<input type="hidden" name="inDefinite_stopDate " id="inDefinite_stopDate" value=""/>
<input type="hidden" name="supplier" id="supplier" value="${param.supplier}"/>
<input type="hidden" name="supplierName" id="supplierName" value="${param.supplierName}"/>
<input type="hidden" name="task" id="task" value="${param.task}"/>
<input type="hidden" name="selRow" id="selRow" value="${selRow}"/>
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
<input type="hidden" name="showInactive" id="showInactive" value="${param.showInactive}"/>
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

var selectedRowId=null;
var modificationsMade = false;

var gridConfig = {
		divName:'CatalogVendorAssignmentBean', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		noSmartRender:false,
		submitDefault:true    // the fields in grid are defaulted to be submitted or not.
	};

<!-- permission stuff here --!>

<c:if test="${permission == 'Y'}">
showUpdateLinks = true;
</c:if>

//-->
</script>

</body>
</html>
