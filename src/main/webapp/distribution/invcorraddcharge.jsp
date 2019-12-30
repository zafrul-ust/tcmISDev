<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
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
<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hch -checkbox
--%>
<script type="text/javascript" src="/dhtmlxCombo/codebase/dhtmlxcombo.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_combo.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/js/distribution/addcharge.js"></script>
    
<title>
	${param.curpath} 
</title>

<script language="JavaScript" type="text/javascript">
// add all the javascript messages here, this for internationalization of client
// side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
		errors:"<fmt:message key="label.errors"/>",
		recordFound:"<fmt:message key="label.recordFound"/>",
		searchDuration:"<fmt:message key="label.searchDuration"/>",
		minutes:"<fmt:message key="label.minutes"/>",
		seconds:"<fmt:message key="label.seconds"/>",
		pleaseselect:"<fmt:message key="label.pleaseselect"/>",  
		totallinecharges:"<fmt:message key="label.totallinecharges"/>", 
		alreadyselected:"<fmt:message key="label.alreadyselected"/>",
		validvalues:"<fmt:message key="label.validvalues"/>",
		price:'<fmt:message key="label.price"/>',
		description:'<fmt:message key="label.chargedescription"/>',
		norowselected:'<fmt:message key="error.norowselected"/>',
		pleaseselectorenter:'<fmt:message key="label.pleaseselectorenter"/>'
};

var priceColumn = "adjustedUnitPrice";
<c:if test="${source eq 'addchargeheader'}">
	priceColumn = "adjustedPrice";
</c:if>

with ( milonic=new menuname("deleteLine") ) {
	 top="offset=2";
	 style=submenuStyle;
	 itemheight=17;
	 aI( "text=<fmt:message key="label.removeLine"/> ;url=javascript:removeLine()" );
}

//drawMenus();

_gridConfig.submitDefault = true;
_gridConfig.divName = 'MrAddChargeView';
_gridConfig.rowSpan = -1;
_gridConfig.beanGrid = 'newFeesGrid';
_gridConfig.onRowSelect = selectRow;
_gridConfig.onRightClick = selectRow;
_gridConfig.singleClickEdit = true;


function priceChanged(rowId,columnId) {
	var v = cellValue(rowId,"changed");
	if( v != 'New' ) {
		gridCell(newFeesGrid,rowId,"changed").setValue("Y");
	}
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
	var v = cellValue(rowId,"changed");
	if( v != 'New' ) {
		gridCell(newFeesGrid,rowId,"changed").setValue("Y");
	}
	return true;
}


function taxChanged(rowId,columnId) {
	var v = cellValue(rowId,"changed");
	if( v != 'New' ) 
		gridCell(newFeesGrid,rowId,"changed").setValue("Y");
	return true;
}

function buildNewItemIdValudset( row ) {
	var newItemIdArray = new Array();

	for( i=0;i < itemIdDisplayArr.length; i++) {
		newItemIdArray[itemIdDisplayArr[i].value] = {text:itemIdDisplayArr[i].text,value:itemIdDisplayArr[i].value};
	}
	
	for( rowid in rowids ) {
		val = gridCellValue(newFeesGrid,rowid,"itemId");
		if( row == rowid ) continue;
		if( val )
			delete( newItemIdArray[val] ) ;
	}

	var valArray = new Array();
	for( v in newItemIdArray ) {
		valArray[valArray.length] = newItemIdArray[v];
	}
	return valArray;
}

var rowids = new Array();
var orirowids = new Array();

function showRemoveLine() {
	var line = $v('totalLines');
	if( hasApprovalPermission == 'Y' )
		if( line != 0 ) $('newFeeRemoveLine').style.display="";
	for(i=1;i<=line;i++) {
		rowids[""+i] = i; 
		orirowids[""+i] = i;
	}
}

var editing = false;
function headerChargeEdit(stage,rowid,index){
    var ind=haasGrid.getRowIndex(rowid);
    if( index != haasGrid.getColIndexById("itemIdDisplay") )
    	    return true; 
    if( rowid == orirowids[rowid] && cellValue(rowid,"oriItemId") != '148742' ) {
		return false;
	}
    if( cellValue(rowid,"permission") != 'Y' ) {
		return false;
	}
    
	// Build the combo box
    if (stage==0) { // Beginning of edit
        editing = true;
    	var val = cell(rowid,"itemIdDisplay").getValue();
		//a = cell(rowid,"itemIdDisplay").combo.get(val);
		itemIdDisplay[rowid] = buildNewItemIdValudset( rowid );
		var z= cell( rowid,"itemIdDisplay").combo;
		z.clear();
		z.put('',messagesData.pleaseselectorenter );
	    var options = itemIdDisplay[rowid];
		for( j =0; j<  options.length; j++) {
			z.put( options[j].value,options[j].text ) ;
		}
		if( cellValue(	rowid, "itemIdDisplay" ) ==	messagesData.pleaseselectorenter ) {
			cell(rowid, "itemIdDisplay").setValue('');
		}
		return true;
	}

    if (stage==2) { // Finishing edit
    	var val = cellValue(rowid,"itemIdDisplay");
    	a = cell(rowid,"itemIdDisplay").combo.get(val);
	    if( a ) {
    		cell(rowid,"itemId").setValue(val);
    		cell(rowid,"chargeDescription").setValue(a);
    		if (itemIdPrice[val]) {
	    		cell(rowid,priceColumn).setValue(itemIdPrice[val]);
    		}
	    }
	    else {
    		cell(rowid,"itemId").setValue('');
    		cell(rowid,"chargeDescription").setValue(val);
		}
	}
    return true;
}

function lineChargeEdit(stage,rowid,index){
	if( index != haasGrid.getColIndexById("itemIdDisplay")) {
        	return true; 
	}
	if( cellValue(rowid,"permission") != 'Y' ) {
		return false;
	}
	
	// When finished editting cell
	if (stage==2) { 
		var val = cellValue(rowid,"itemIdDisplay");
		if (itemIdPrice[val]) {
			cell(rowid,priceColumn).setValue(itemIdPrice[val]);
		}
    	}
	return true;
}

function validateForm() {
	try {
	    if( editing ) {
		    editing = false;
			setTimeout("_simpleUpdate()",200);
			return false;
	    }
	}catch(ex){}

	for( rowId in rowids ) {
		var errorMsg = '';

		if ($(priceColumn+rowId) != null && !isSignedFloat($v(priceColumn+rowId),false ) ) {
			errorMsg += "\n"+messagesData.price;
		}
		
		<c:if test="${source eq 'addchargeheader'}">
			var val = cell(rowId,"itemIdDisplay").getValue();

			if( !val || val.trim().length == 0 || val.trim() == messagesData.pleaseselectorenter ) 
				errorMsg += "\n"+messagesData.description;

			if( val.length > 100 ) {
				errorMsg += "\n" + '<fmt:message key="errors.maxlength"/>'.replace(/[{]0[}]/g,'<fmt:message key="label.chargedescription"/>').replace(/[{]1[}]/g,'100');
			}
		</c:if>

	
	}

	if( errorMsg != '' ) {
	   	alert(messagesData.validvalues+errorMsg);
	   	return false;
	}

	return true;
}

function showMeValue(rowId,columnId) {
		var value = $v(""+columnId+rowId);
		var v = cellValue(rowId,"changed");
		if( v != 'New' ) 
			gridCell(newFeesGrid,rowId,"changed").setValue("Y");
}

function addNewFeesLine(rowKey,orderQuantityRule) {

	   var rowid = (new Date()).valueOf();
	   ind = haasGrid.getRowsNum();

		  count = 0 ;
		  itemIdDisplay[rowid] = buildNewItemIdValudset();
	      var thisrow = haasGrid.addRow(rowid,"",ind);
	      alertthis = true;
		  haasGrid.cells(rowid, count++).setValue('Y');
		  haasGrid.cells(rowid, count++).setValue('');
		  haasGrid.cells(rowid, count++).setValue('');
		  haasGrid.cells(rowid, count++).setValue('Y');
		  haasGrid.cells(rowid, count++).setValue('');
  		  haasGrid.cells(rowid, count++).setValue('');
// delete charge
  		  haasGrid.cells(rowid, count++).setValue(false);
//		  haasGrid.cells(rowid, count++).setValue(selectCurrency);
//		  haasGrid.cells(rowid, count++).setValue('once');
		  haasGrid.cells(rowid, count++).setValue($v('lineItem'));
		  haasGrid.cells(rowid, count++).setValue($v('companyId'));
		  haasGrid.cells(rowid, count++).setValue($v('prNumber'));
		  haasGrid.cells(rowid, count++).setValue('New'); // new
		  haasGrid.cells(rowid, count++).setValue($v('orderType')); 
		  haasGrid.cells(rowid, count++).setValue(''); 
		  haasGrid.cells(rowid, count++).setValue($v('invoice')); 
		  rowids[""+rowid] = rowid;
		  selectedRowId = rowid;
		  haasGrid.selectRow(haasGrid.getRowIndex(rowid));
		  $('newFeeRemoveLine').style.display="";
	<c:if test="${ source eq 'addchargeheader' }">
			var options = itemIdDisplay[rowid];
			var z= cell( rowid,"itemIdDisplay").combo;
			z.clear();
			z.put(messagesData.pleaseselectorenter,messagesData.pleaseselectorenter );
			for( j =0; j<  options.length; j++) {
				z.put( options[j].value,options[j].text ) ;
			}
			cell(rowid, "itemIdDisplay").setValue(messagesData.pleaseselectorenter);
	</c:if>		  
		
}

function fillcombo() {
	<c:choose>		
		<c:when test="${ source ne 'addchargeheader' }">
			if( hasApprovalPermission == 'Y' ) {
				haasGrid.attachEvent("onEditCell",lineChargeEdit);
			}
		</c:when>
		<c:otherwise>
		   	if( hasApprovalPermission == 'Y' ) {
				haasGrid.attachEvent("onEditCell",headerChargeEdit);
				
				var rows = haasGrid.getRowsNum();
				for( i = 1; i <= rows; i++ ) {
					var options = itemIdDisplay[i];
					var z= cell( i,"itemIdDisplay").combo;
					for( j =0; j<  options.length; j++) {
						z.put( options[j].value,options[j].text ) ;
					}
					// Ignore itemId for miscellaneous charge
					if( cellValue(i,"itemId") == '148742' ) {
						cell( i,"itemId" ).setValue('');
					}
					cell( i,"itemIdDisplay" ).setValue( cellValue(i,"chargeDescription") );
				}
			}
		</c:otherwise>
	</c:choose>
}

var hasApprovalPermission = 'N';
window.onresize = resizeFrames;

function removeLine() {
	if(!selectedRowId){
		alert(messagesData.norowselected);return false;
	}
	if( cellValue(selectedRowId,'changed') != 'New' ) {
		gridCell(newFeesGrid,selectedRowId,'changed').setValue('Remove');_simpleUpdate();
	} 
	else removeNewFeesLine();
}

</script>

</head>

<body bgcolor="#ffffff" onload="popupOnLoadWithGrid();fillcombo();showRemoveLine();getTotal();"  onunload="if($v('uAction') != 'update')try{opener.refresh();opener._closeTransitWin();}catch(ex){}">

<tcmis:form action="/invoicecorrection.do" onsubmit="return submitFrameOnlyOnce();">

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
<c:forEach var="errorMsg" items="${tcmISError}">
	<c:if test="${!empty errorMsg}">
		showErrorMessage = true;
	</c:if>
</c:forEach>

//-->
</script>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display: none;">
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
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Since this page is read-only I don't need to hide the mainUpdateLinks div, I am showing the link Close to all.
      --%>      
      <div id="headerline"><span id="mainUpdateLinks" style="display:"></span> <%-- mainUpdateLinks Begins --%>

<c:set var="hasApprovalPermission" value="Y"/>
<c:choose>
<c:when test="${ hasApprovalPermission eq 'Y' }"> 
<span id="updateHeaderFee">
<a href="#" onclick="_simpleUpdate()"><fmt:message key="label.update"/></a>	
&nbsp;|&nbsp;
<a href="#" onclick="window.close();"><fmt:message key="label.close"/></a>	
&nbsp;|&nbsp;
</span>
<span id="newFeeAddLine">
<a href="#" onclick="addNewFeesLine()"><fmt:message key="label.addline"/></a>	
&nbsp;
</span>
<span id="newFeeRemoveLine" style="display: none">
<%-- |&nbsp;
<a href="#" onclick="removeLine();"><fmt:message key="label.removeLine"/></a>
--%>
</span> 
<br/>
<%-- 
<c:if test="${source eq 'addchargeheader'}">
    <fmt:message key="label.totallinecharges"/> : <fmt:formatNumber value="${param.totalLineCharge}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>
</c:if>	--%>
</c:when>
<c:otherwise>
&nbsp;
</c:otherwise>
</c:choose>

     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
<!--  result page section start -->
<div id="MrAddChargeView" style="height:400px;display: none;" ></div>
<script type="text/javascript">

<c:set var="extra" value="0"/>

var itemIdDisplayArr = new Array(
		<c:if test="${ source ne 'addchargeheader' }">
			{text:messagesData.pleaseselect,value:''}
			<c:set var="extra" value="1"/>			
		</c:if>  
	<c:forEach var="newItemsBean" items="${feeDropDown}" varStatus="status">
    	<c:if test="${ status.index+extra > 0}">,</c:if>  
		{text:'<tcmis:jsReplace value="${status.current.itemDescription}" processMultiLines="true"/>',value:'${status.current.itemId}'}
	</c:forEach>
); 

<%--NEW - storing the data to be displayed in the grid in a JSON.--%>
<c:set var="dataCount" value='${0}'/>
<c:set var="extra" value="0"/>

var itemIdDisplay = {
		<c:forEach var="bean" items="${beanColl}" varStatus="status">
        <c:if test="${ status.index+extra > 0}">,</c:if>
	        ${status.index+1+extra}:itemIdDisplayArr
        </c:forEach>
};

var jsonData = {
        rows:[
<c:forEach var="bean" items="${beanColl}" varStatus="status">
        <c:if test="${status.index + extra > 0}">,</c:if>

        <c:set var="p" value="${hasApprovalPermission}"/>
<%--  GenerateOrders and shipConfirm ig permission --%>
<c:set var="deleteCharge" value="false"/>
<c:if test="${bean.deleteCharge eq 'Y'}">
	<c:set var="deleteCharge" value="true"/>
</c:if>
        /*The row ID needs to start with 1 per their design.*/
        { 
  			id:${status.index + 1+ extra },
          data:[
 '${p}',
 '${bean.itemId}',
 '${bean.itemId}',
 'N',//'${p}',
 '${bean.itemId}',
<c:if test="${source eq 'addchargeheader'}">
'${bean.adjustedPrice}',
</c:if>	
<c:if test="${source ne 'addchargeheader'}">
'${bean.adjustedPrice}',
</c:if>	
 ${deleteCharge},
 '${bean.lineItem}',
 '${param.companyId}',
 '${param.prNumber}',
 '','${param.orderType}','<tcmis:jsReplace value="${bean.chargeDescription}" processMultiLines="true"/>',
 '${param.invoice}',
 ]}
    <c:set var="dataCount" value='${dataCount+1}'/>
     </c:forEach>
    ]};

selectCurrency='${param.currencyId}';

var config = [
  {
  	columnId:"permission",
  	columnName:''
  },
  {
	  	columnId:"itemId",
	  	type:"ed"
},
{
  	columnId:"oriItemId"
},
{
  	columnId:"itemIdDisplayPermission"
},
{ columnId:"itemIdDisplay",
  columnName:'<fmt:message key="label.chargedescription"/>',
  width:26,
<c:if test="${ source eq 'addchargeheader' and hasApprovalPermission == 'Y' }">
//type:"hcoro",
  type:"co",
</c:if>  
<c:if test="${ source ne 'addchargeheader' || hasApprovalPermission != 'Y' }">
  type:"hcoro",
  permission:true,
  onChange:itemIdChanged,
  sorting:"haasCoro",
</c:if>  
  submit:false
},
{ columnId:priceColumn,
  columnName:'<fmt:message key="label.price"/>',
  width:6,
  size:4,
  maxlength:11,
  onChange:priceChanged,
  type:"hed"
},
{
  	columnId:"deleteCharge",
	columnName:"<fmt:message key="catalogspec.label.markedfordeletion"/>",
	type:'hchstatus'
//	,permission:true
},
  {
	  	columnId:"lineItem"
  },
  {
	  	columnId:"companyId"
  },
  {
	  	columnId:"prNumber"
  }, 
/* 
<c:if test="${source eq 'addchargeheader'}">  
  {
  	columnId:"taxExempt",
  	columnName:'<fmt:message key="label.taxexempt"/>',
    type:"hchstatus",
    onChange:taxChanged
   },  
</c:if>  */
  {
	  	columnId:"changed"//,type:"ed"
  },
  {
     columnId:"orderType"
  },  
  {
	 columnId:"chargeDescription"
  },  
  {
	 columnId:"invoice"
  }
];


var chargeRecurrence = new Array( 
//		{text:'<fmt:message key="label.pleaseselect"/>',value:''}
		<c:forEach var="vvBean" items="${addChargeRecurrenceDropDown}" varStatus="status"> 
		<c:if test="${status.index > 0}">,</c:if>
		<c:set var="rlabel"><fmt:message key="${vvBean.jspLabel}"/></c:set>
		{text:'${rlabel}',value:'${vvBean.chargeRecurrence}'}
		</c:forEach>  	
		);  

var itemIdPrice = {
	<c:forEach var="newItemsBean" items="${feeDropDown}" varStatus="status">
    		<c:if test="${ status.index > 0}">,</c:if>
    		 <fmt:formatNumber var="defaultPrice" value="${status.current.defaultPrice}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>  
		"${status.current.itemId}":'${defaultPrice}'
	</c:forEach>
};


</script>

<!-- If the collection is empty say no data found -->
<%--
<c:if test="${empty beanColl}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td>
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>
--%>
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

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="uAction" id="uAction" value="" type="hidden"/>
<input type="hidden" name="prNumber" id="prNumber" value="${param.prNumber}"/>
<input type="hidden" name="invoice" id="invoice" value="${param.invoice}"/>
<input type="hidden" name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}"/>
<input type="hidden" name="companyId" id="companyId" value="${param.companyId}"/>
<input type="hidden" name="lineItem" id="lineItem" value="${param.lineItem}"/> 
<input type="hidden" name="currencyId" id="currencyId" value="${param.currencyId}"/>
<input type="hidden" name="totalLineCharge" id="totalLineCharge" value="${param.totalLineCharge}"/>    
<input type="hidden" name="inventoryGroup" id="inventoryGroup" value="${param.inventoryGroup}"/>
<input type="hidden" name="orderType" id="orderType" value="${param.orderType}"/>
<input type="hidden" name="lineQuantity" id="lineQuantity" value="${param.lineQuantity}"/>    

<input type="hidden" name="source" id="source" value="${source}"/>  
<!--This sets the start time for time elapesed.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="newFeesAddLimit" id="newFeesAddLimit" type="hidden" value="20"/>
<input name="newFeesDeleteOnly" id="newFeesDeleteOnly" type="hidden" value="" />
<input name="minHeight" id="minHeight" type="hidden" value="210"/>

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

<c:if test="${hasApprovalPermission == 'Y'}">
 <script type="text/javascript">
 <!--
  hasApprovalPermission = 'Y';
  showUpdateLinks = true;
 //-->
 </script>
</c:if>

</body>
</html>
