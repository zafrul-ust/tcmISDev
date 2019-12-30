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
<%@ include file="/common/rightclickmenudata.jsp" %>

<%-- For Calendar support --%>
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>

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
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
    
<title>
<fmt:message key="minmaxlevel.title"/>
</title>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",

submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
criteriaRequired:"<fmt:message key="error.itempartnumber.required"/>",
itemIdInteger:"<fmt:message key="error.item.integer"/>",
reorderQuantityInteger:"<fmt:message key="error.reorderquantity.integer"/>",
reorderQuantityNotZero:"<fmt:message key="error.reorderquantity.notzero"/>",
reorderPointInteger:"<fmt:message key="error.reorderpoint.integer"/>",
stockingLevelInteger:"<fmt:message key="error.stockinglevel.integer"/>",
reorderPointVersusLevel:"<fmt:message key="error.reorderpointlevel"/>",
lookAheadDaysInteger:"<fmt:message key="error.lookaheaddays.integer"/>",
reorderQuantityNotZeroAndStockingNotZero:"<fmt:message key="error.reorderquantityandstockinglevel.notzero"/>",
pleaseincreasereorderpoint:"<fmt:message key="label.pleaseincreasereorderpoint"/>",
norowselected:"<fmt:message key="error.norowselected"/>",
projectedLeadTimeInteger:"<fmt:message key="error.projectedleadtime.integer"/>"
};

<c:set var="beanCollection" value="${currentMinMaxLevelViewBeanCollection}"/>

function onloadset() {
	  numberOfRows = $v('totalLines');
	  var flag = true;
	  for(var i=1; i<=numberOfRows; i++) {
		  try {
		  var stockingMethod = document.getElementById("stockingMethod" + i);
		  var reorderPoint = document.getElementById("reorderPoint" + i);
		  var stockingLevel = document.getElementById("stockingLevel" + i);
		  var reorderQuantity = document.getElementById("reorderQuantity" + i);

	     if(stockingMethod != null && stockingMethod.value == 'MM') {
		    reorderPoint.disabled=false;
		    reorderPoint.style.visibility = 'visible';
		    stockingLevel.disabled=false;
		    stockingLevel.style.visibility = 'visible';
		    reorderQuantity.disabled=false;
		    reorderQuantity.style.visibility = 'visible';
		  }
		  else {
			    reorderPoint.style.visibility = 'hidden';
			    stockingLevel.style.visibility = 'hidden';
			    reorderQuantity.style.visibility = 'hidden';
		  }
	      var receiptProcessingMethod = document.getElementById("receiptProcessingMethod" + i);
		  if(receiptProcessingMethod != null && receiptProcessingMethod.value == 'Issue On Receipt') {
			    reorderPoint.disabled=true;
			    stockingLevel.disabled=true;
			    reorderQuantity.disabled=true;
	  }
		  else {
			    reorderPoint.disabled=false;
			    stockingLevel.disabled=false;
			    reorderQuantity.disabled=false;
		  }
		  } catch(ex){}
	  }
	  $('showlegendLink').innerHTML = '&nbsp;';
	  <bean:size id="listSize" name="beanCollection"/>
	  <c:if test="${ listSize > 0 }">
	  var listSize = ${listSize} ;
	  if( listSize > 0 )
	  	$('showlegendLink').innerHTML =
	  	'<fmt:message key="label.itemdesc"/>'+':&nbsp; <tcmis:jsReplace value="${beanCollection[0].itemDesc}"/><br/>';
	  </c:if>
}	  

function validateUpdateForm(numberOfRows) {
	  var flag = true;
	  var selected = false;
	  if(numberOfRows != null) {
	    for(var i=1; i<=numberOfRows; i++) {
		  var checked = false;
		  try {
			  checked = cellValue(i,'okDoUpdate');
		  } catch(ex){}
		  if( !checked ) continue;
		  selected = true;
		  var stockingMethod = document.getElementById("stockingMethod" + i);
	      var found = false;
	      if(stockingMethod != null && stockingMethod.value == 'MM') {
	        found = true
	      }
	      else {
	        found = false;
	      }
	      if(found) {
	    	  var reorderPoint = $("reorderPoint" + i);
	    	  var stockingLevel = $("stockingLevel" + i);
	    	  var reorderQuantity = $("reorderQuantity" + i);
	    	  var lookAheadDays  =  $("lookAheadDays"+i+"");
	    	  var projectedLeadTime  =  $("projectedLeadTime"+i+"");
              var levelHoldEndDate  =  $("levelHoldEndDate"+i+"");
              
	    	  reorderPoint.value = reorderPoint.value.trim();
	    	  stockingLevel.value = stockingLevel.value.trim();
	    	  reorderQuantity.value = reorderQuantity.value.trim();
	    	  lookAheadDays.value = lookAheadDays.value.trim();
	    	  projectedLeadTime.value= projectedLeadTime.value.trim();
              levelHoldEndDate.value=levelHoldEndDate.value.trim();

	    	//if( reorderQuantity.value == '0' )
	    	//	  reorderQuantity.value = '';
	    	  
	        if(!checkReorderPoint(i)) {
	          return false;
	        }
	        if(!checkReorderQuantity(i)) {
	          return false;
	        }
	        if(!checkStockingLevel(i)) {
	          return false;
	        }
	        if(!checkLookAheadDays(i)) {
	          return false;
	        }
	        if(!checkProjectedLeadTime(i)) {
              return false;
            }
	      }
	    }
	  }
	  if( !selected ) {
		  alert(messagesData.norowselected);
		  return false;
	  } 
	  return flag;
	}

function submitUpdate() {
	  numberOfRows = document.getElementById('totalLines').value;
	  var flag = validateUpdateForm(numberOfRows);
	  if(flag) {
	    var action = document.getElementById("uAction");
	    action.value = 'update';
	    submitOnlyOnce();
    	haasGrid.parentFormOnSubmit();
	    document.genericForm.submit();
		parent.showPleaseWait();
	  }
	  return flag;
}

function stockingMethodChanged(rowNumber) {
	  var stockingMethod = document.getElementById("stockingMethod" + rowNumber);
	  var found = false;
	  if(stockingMethod != null && stockingMethod.value == 'MM') {
	    found = true
	  }
	  else {
	    found = false;
	  }
	  var reorderPoint = document.getElementById("reorderPoint" + rowNumber);
//	  var oldReorderPoint = cellValue("oldReorderPoint" , rowNumber);//document.getElementById("oldReorderPoint" + rowNumber);
	  var stockingLevel = document.getElementById("stockingLevel" + rowNumber);
//	  var oldStockingLevel = cellValue("oldStockingLevel" , rowNumber);//document.getElementById("oldStockingLevel" + rowNumber);
	  var reorderQuantity = document.getElementById("reorderQuantity" + rowNumber);
//	  var oldReorderQuantity = cellValue("oldReorderQuantity" , rowNumber);//document.getElementById("oldReorderQuantity" + rowNumber);

	  if(found) {
//	    oldReorderPoint.disabled=false;
	    reorderPoint.disabled=false;
	    reorderPoint.style.visibility = 'visible';
//	    oldStockingLevel.disabled=false;
	    stockingLevel.disabled=false;
	    stockingLevel.style.visibility = 'visible';
//	    oldReorderQuantity.disabled=false;
	    reorderQuantity.disabled=false;
	    reorderQuantity.style.visibility = 'visible';
	    //reorderQuantity.value="";
	    //document.getElementById("lookAheadDays"+rowNumber+"").value ="";
	  }
	  else if(stockingMethod != null){
//	    oldReorderPoint.disabled=true;
//	    reorderPoint.disabled=true;
	    reorderPoint.style.visibility = 'hidden';
//	    oldStockingLevel.disabled=true;
//	    stockingLevel.disabled=true;
	    stockingLevel.style.visibility = 'hidden';
//	    oldReorderQuantity.disabled=true;
//	    reorderQuantity.disabled=true;
	    reorderQuantity.style.visibility = 'hidden';
	  }
	  receiptProcessingMethodChanged(rowNumber);
	}

	function receiptProcessingMethodChanged(rowNumber) {
	  var receiptProcessingMethod = document.getElementById("receiptProcessingMethod" + rowNumber);
	  var found = false;
	  if(receiptProcessingMethod != null && receiptProcessingMethod.value == 'Issue On Receipt') {
	    found = true
	  }
	  else {
	    found = false;
	  }
	  var reorderPoint = document.getElementById("reorderPoint" + rowNumber);
//	  var oldReorderPoint = document.getElementById("oldReorderPoint" + rowNumber);
	  var stockingLevel = document.getElementById("stockingLevel" + rowNumber);
//	  var oldStockingLevel = document.getElementById("oldStockingLevel" + rowNumber);
	  var reorderQuantity = document.getElementById("reorderQuantity" + rowNumber);
//	  var oldReorderQuantity = document.getElementById("oldReorderQuantity" + rowNumber);
	  if(found) {
	    reorderPoint.value = "0";
	    stockingLevel.value = "0";
	    reorderQuantity.value = "";
	    reorderPoint.disabled=true;
	    stockingLevel.disabled=true;
	    reorderQuantity.disabled=true;
        var stockingMethod = document.getElementById("stockingMethod" + rowNumber);
  	    if(stockingMethod != null) {
	     stockingMethod.value = 'OOR';
	  }
	  }
	  else {
	    reorderPoint.disabled=false;
	    stockingLevel.disabled=false;
	    reorderQuantity.disabled=false;
	  }
	}

	function checkReorderQuantity(rowNumber) {
	  var message = "";
	  var errorFlag = false;
	  var stockingMethod  =  document.getElementById("stockingMethod"+rowNumber+"");
	  var reorderPoint  =  document.getElementById("reorderPoint"+rowNumber+"");
	  var stockingLevel  =  document.getElementById("stockingLevel"+rowNumber+"");
	  var reorderQuantity  =  document.getElementById("reorderQuantity"+rowNumber+"");

	    if ( !(isNonnegativeInteger(reorderQuantity.value, true)) ) {
	      message = message + messagesData.reorderQuantityInteger + "\n\n";
	      reorderQuantity.focus();
	      errorFlag = true;

	    }
	    else if ( (reorderQuantity.value.trim().length > 0 && reorderQuantity.value != '0') && (stockingLevel.value.trim().length >0 && stockingLevel.value != '0') ) {
			message = message + messagesData.reorderQuantityNotZeroAndStockingNotZero + "\n\n";
	      			errorFlag = true;
			stockingLevel.focus();
	    		}
/*	    else if (   reorderQuantity.value.length > 0 && stockingLevel.value != '0' ) {
	message = message + messagesData.reorderQuantityNotZeroAndStockingNotZero + "\n\n";
	errorFlag = true;
	stockingLevel.focus();
	  }
*/
	  if (errorFlag) {
	    alert(message);
	  }
	  return !errorFlag;
	}

function checkReorderPoint (rowNumber) {

	  var message = "";
	  var stockingMethod  =  document.getElementById("stockingMethod"+rowNumber);
	  var reorderPoint  =  document.getElementById("reorderPoint"+rowNumber);
	  var stockingLevel  =  document.getElementById("stockingLevel"+rowNumber);
	  var reorderQuantity  =  document.getElementById("reorderQuantity"+rowNumber);

	    if ( !(isNonnegativeInteger(reorderPoint.value, false)) ) {
	      message = message + messagesData.reorderPointInteger + "\n\n";
	    }
	    else if ( (reorderPoint.value == '0' || reorderPoint.value.trim().length == '0' )&&
                  !((stockingLevel.value == '0' || stockingLevel.value.trim().length == '0' )
                     || (reorderQuantity.value == '0' || reorderQuantity.value.trim().length == '0' )) ) {
		      message = message + messagesData.pleaseincreasereorderpoint + "\n\n";
	    }
	    else if ((reorderQuantity.value == '0' || reorderQuantity.value.trim().length == '0' ) && (reorderPoint.value*1 > stockingLevel.value*1)){
	      message = message + messagesData.reorderPointVersusLevel + "\n\n";
	  }

	  if ( message ) {
		reorderPoint.focus();
	    alert(message);
	    return false;
	  }
	  return true;
	}

function checkStockingLevel (rowNumber) {
	  var message = "";
	  var errorFlag = false;
	  var stockingMethod  =  document.getElementById("stockingMethod"+rowNumber+"");
	  var reorderPoint  =  document.getElementById("reorderPoint"+rowNumber+"");
	  var stockingLevel  =  document.getElementById("stockingLevel"+rowNumber+"");
	  var reorderQuantity  =  document.getElementById("reorderQuantity"+rowNumber+"");
	  if (stockingMethod != null && stockingMethod.value == "MM") {
	    if ( !(isNonnegativeInteger(stockingLevel.value, true)) ) {
	     message = message + messagesData.stockingLevelInteger + "\n\n";
	      errorFlag = true;
	    }
	    else {
	      if(stockingLevel.value*1 != 0) {
	        reorderQuantity.value = "";
	      }
	      /* already checked at check reorder point.
	      if (reorderQuantity.value.trim().length == 0 && (reorderPoint.value.trim()*1 > stockingLevel.value.trim()*1)){
	        message = message + messagesData.reorderPointVersusLevel + "\n\n";
	        errorFlag = true;
	      }
	      */
	    }
	  }

	  if (errorFlag) {
		stockingLevel.focus();
	    alert(message);
	  }
	    return !errorFlag;
	}

function checkLookAheadDays(rowNumber) {
	 var message = "";
	  var errorFlag = false;
	  var stockingMethod  =  document.getElementById("stockingMethod"+rowNumber+"");
	  var lookAheadDays  =  document.getElementById("lookAheadDays"+rowNumber+"");

	  if ( !(isInteger(lookAheadDays.value, true)) ) {
	    message = message + messagesData.lookAheadDaysInteger + "\n\n";
	    errorFlag = true;
	  }

	  if (errorFlag) {
	    alert(message);
	  }
	  return !errorFlag;
	}

function checkProjectedLeadTime(rowNumber) {
    var message = "";
     var errorFlag = false;
     var stockingMethod  =  document.getElementById("stockingMethod"+rowNumber+"");
     var projectedLeadTime  =  document.getElementById("projectedLeadTime"+rowNumber+"");

     if ( !(isInteger(projectedLeadTime.value, true)) ) {
       message = message + messagesData.projectedLeadTimeInteger + "\n\n";
       errorFlag = true;
     }

     if (errorFlag) {
       alert(message);
     }
     return !errorFlag;
   }

with ( milonic=new menuname("showCustomer") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
// style = contextStyle;
// margin=3;
 aI( "text=<fmt:message key="label.showhistory"/> ;url=javascript:viewHistory();" );
}
drawMenus();

var selectedRowId = null;
function pp(name) {
	var value = $v(name);
//	alert( value );
	return encodeURIComponent(value);
}

function gg(name) {
	var value = null;
	value = cellValue(selectedRowId,name);
//	alert( value );
	return encodeURIComponent(value);
}


function viewHistory() {

	openWinGeneric("/tcmIS/hub/minmaxlevelhistory.do?uAction=history"+
			"&opsEntityId="+pp('opsEntityId')+	
			"&hub="+pp('hub')+
			"&inventoryGroup="+gg('inventoryGroup')+
			"&itemId="+gg('itemId')+
			"&catPartNo="+gg('catPartNo'),"recipeDetail","1024","600","yes");
	
}

function selectRow()
{  
// to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];
//
   if( ee != null ) {
   		if( ee.button != null && ee.button == 2 ) rightClick = true;
   		else if( ee.which == 3  ) rightClick = true;
   }
   selectedRowId = rowId0;
// change to here for txt object.    
   if( colId0 == haasGrid.getColIndexById("remarks") && cellValue(rowId0,"remarksPermission") != 'Y' ){//&& cellValue("remarks")) {
	   haasGrid.lockRow(rowId0,"true");//return true;//alert('hh');
   }
//   else
//   	   haasGrid.lockRow(rowId0,"false");//return true;//alert('hh');
   
   if( !rightClick ) return true;
   haasGrid.selectRowById(rowId0,null,false,false);	
//  selectRow(rowId,cellInd);

//   if( !rightClick ) return false;

	partno = cellValue(rowId0,"catPartNo");
	ig = cellValue(rowId0,"inventoryGroup");
	
	toggleContextMenu('showCustomer');
//contextDisabled = true;
//closeAllMenus();
//popup('showCustomer',1);
}

//_gridConfig.submitDefault = true;
//_gridConfig.divName = 'OrdersBean';
//_gridConfig.rowSpan = false;
//_gridConfig.beanGrid = 'ordersGrid';
//_gridConfig.onRowSelect = selectRow;

var gridConfig = {
	divName:'minMaxLevelInputBean', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:true,    // the fields in grid aalre defaulted to be submitted or not.
	singleClickEdit:true,	
	onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:selectRow   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//	onBeforeSorting:_onBeforeSorting
};

<c:set var="itemid"><fmt:message key="label.itemid"/></c:set>
var config = [
{ columnId:"permission"
},
  {
  	columnId:"inventoryGroup",
  	columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
    tooltip:"Y",
    width:6
  },
  {
  	columnId:"companyId",
  	columnName:'<fmt:message key="label.company"/>',
    tooltip:"Y",
    width:6
  },
  {
  	columnId:"catalogDesc",
  	columnName:'<fmt:message key="label.catalog"/>',
    tooltip:"Y",
    width:6
  },
  {
  	columnId:"catPartNo",
  	columnName:'<fmt:message key="label.partnumber"/>',
    tooltip:"Y",
    width:8
  },
  {
  	columnId:"partGroupNo",
  	columnName:'<fmt:message key="label.partgroupnumber"/>',
    width:4
  },
  {
  	columnId:"specList",
  	columnName:'<fmt:message key="label.specification"/>',
    tooltip:"Y",
    width:8
  },
  {
  	columnId:"itemId",
  	columnName:'<tcmis:jsReplace value="${itemid}"/>',
    width:5
  },
  {
  	columnId:"currentStockingMethod",
  	columnName:'<fmt:message key="label.currentstockingmethod"/>',
    width:5
  },
  {
	  	columnId:"okDoUpdatePermission"
  },
  {
  	columnId:"okDoUpdate",
  	columnName:'<fmt:message key="label.ok"/>',
  	width:3,
  	type:'hch'
  },
  {
  	columnId:"stockingMethod",
  	columnName:'<fmt:message key="label.stockingmethod"/>',
    width:7,
    size:3,
  	onChange:stockingMethodChanged,
  	type:'hcoro'
  },
  {
  	columnId:"reorderPoint",
  	columnName:'<fmt:message key="label.reorderpoint"/>',
    width:5,
    size:3,
//  	onChange:checkReorderPoint,
  	type:'hed'
  },
  {
  	columnId:"stockingLevel",
  	columnName:'<fmt:message key="label.stockinglevel"/>', 	
    width:5,
    size:3,
  	//onChange:checkStockingLevel,
  	type:'hed'
  },
  {
  	columnId:"reorderQuantity",
  	columnName:'<fmt:message key="label.reorderquantity"/>', 	
    width:5,
    size:3,
//  	onChange:checkReorderQuantity,
  	type:'hed'
  },
  {
  	columnId:"lookAheadDaysPermission"
  },
  {
  	columnId:"lookAheadDays",
  	columnName:'<fmt:message key="label.lookaheaddays"/>', 	
    width:6,
    size:3,
  	//onChange:checkLookAheadDays,
  	type:'hed'
  },
  {
  	columnId:"dropShipOverride",
  	columnName:'<fmt:message key="label.dropship"/>',
    width:4,
  	type:'hchstatus'
  },
  {
      columnId:"levelHoldEndDate",
      columnName:'<fmt:message key="label.levelholdenddate"/>',
      width:8,
      size:10,
      type:'hcal',
      align:'right',
      sorting:'int',
      maxlength:16
  },
  {
      columnId:"projectedLeadTime",
      columnName:'<fmt:message key="label.projectedleadtimeindays"/>',
      width:6,
      size:3,
      type:'hed' 
  },
  {
  	columnId:"receiptProcessingMethodPermission"
  },
  {
  	columnId:"receiptProcessingMethod",
	columnName:'<fmt:message key="label.receiptprocessingmethod"/>',
	// I will do this later. 
	onChange:receiptProcessingMethodChanged,
	permission:true,
  	type:'hcoro', 	
   	width:16
  	//onChange:receiptProcessingMethodChanged
  },
  {
  	columnId:"billingMethodPermission"
  },
  {
  	columnId:"billingMethod",
	columnName:'<fmt:message key="label.billingmethod"/>',
	permission:true,
	width:12,
  	type:'hcoro'
  },
  {
  	columnId:"remarksPermission"
  },
  {
  	columnId:"remarks",
  	columnName:'<fmt:message key="label.remarks"/>',
  	type:'txt'
  },
  {
  	columnId:"oldStockingMethod"
  },
  {
  	columnId:"oldReorderPoint"
  },
  {
  	columnId:"oldStockingLevel"
  },
  {
  	columnId:"oldReorderQuantity"
  },
  {
  	columnId:"oldLookAheadDays"
  },
  {
  	columnId:"oldReceiptProcessingMethod"
  },
  {
  	columnId:"oldBillingMethod"
  },
  {
  	columnId:"catalogCompanyId"
  },
  {
  	columnId:"catalogId"
  },
  {
    columnId:"oldLevelHoldEndDate"
  },
  {
    columnId:"oldProjectedLeadTime"
  }
  ];
  
windowCloseOnEsc = true;
// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="popupOnLoadWithGrid(gridConfig);onloadset()" onresize="resizeFrames()">

<tcmis:form action="/minmaxlevelresults.do" onsubmit="return submitFrameOnlyOnce();">

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

<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
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
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      <span id="updateResultLink" style="display: none">
<span id="showlegendLink">
<%--         <a href="javascript:simpleCallToServerWithGrid('inventoryreportresults.do?uAction=loaddata')"><fmt:message key="label.lotstatuslegend"/></a>
--%>
      </span>
              <a href="#" onclick="submitUpdate();"><fmt:message key="label.update"/></a>
	  </span>
	   &nbsp;
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
<!--  result page section start -->
<c:set var="beanCollection" value="${currentMinMaxLevelViewBeanCollection}"/>
<c:set var="dataCount" value='0'/>

<c:if test="${!empty beanCollection}" >

<div id="minMaxLevelInputBean" style="height:400px;display: none;" ></div>
<script type="text/javascript">
<!--
var stockingMethod = new Array(
		  {value:"exitMM",text:'<fmt:message key="label.exitmm"/>'},
		  {value:"MM",text:'<fmt:message key="label.mm"/>'},
		  {value:"OOR",text:'<fmt:message key="label.oor"/>'},
		  {value:"virtualMM",text:'<fmt:message key="label.virtualmm"/>'}
		);
var receiptProcessingMethod = new Array();
var billingMethod = new Array();


<c:forEach var="bean" items="${beanCollection}" varStatus="status">

<c:if test="${ status.current.issueGeneration == 'Item Counting' }">
receiptProcessingMethod[${status.index+1}] = new Array(
		<c:forEach var="rBean" items="${bean.igReceiptProcessingViewBeanCollection}" varStatus="status1">
		  <c:if test="${status1.index > 0}">,</c:if>
		  {value:"${status1.current.receiptProcessingMethod}",text:'<tcmis:jsReplace value="${status1.current.receiptProcessingMethodDesc}"/>'}
		</c:forEach>
);

billingMethod[${status.index+1}] = new Array(
		<c:forEach var="gBean" items="${bean.igBillingMethodViewBeanCollection}" varStatus="status1">
		  <c:if test="${status1.index > 0}">,</c:if>
		  {value:"${status1.current.billingMethod}",text:'${status1.current.billingMethod}'}
		</c:forEach>
		);

</c:if>

<c:if test="${ status.current.issueGeneration != 'Item Counting' }">

<c:set var="rdesc" value="${status.current.receiptProcessingMethod}"/>
	<c:forEach var="rBean" items="${bean.igReceiptProcessingViewBeanCollection}" varStatus="status1">
		  <c:if test="${ status.current.receiptProcessingMethod == status1.current.receiptProcessingMethod }">
		  	<tcmis:jsReplace var="rdesc" value="${status1.current.receiptProcessingMethodDesc}"/>
		  </c:if>
	</c:forEach>
receiptProcessingMethod[${status.index+1}] = new Array(
	  {value:"${status.current.receiptProcessingMethod}",text:'${rdesc}'}
);

billingMethod[${status.index+1}] = new Array(
{value:"${status.current.billingMethod}",text:'${status.current.billingMethod}'}
);

</c:if>



</c:forEach>


/*Storing the data to be displayed in a JSON object array.*/

<c:set var="p" value="N"/>

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${beanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<c:set var="okp" value="N"/>

<c:set var="ip" value="N"/>
<c:set var="mp" value="N"/>
<c:set var="bp" value="N"/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="MinMaxChg" inventoryGroup="${status.current.inventoryGroup}">
<%--
<tcmis:inventoryGroupPermission indicator="true" userGroupId="ItemMgmt" inventoryGroup="${status.current.inventoryGroup}">
--%>
<c:if test="${status.current.issueGeneration == 'Item Counting'}">
	<c:set var="ip" value="Y"/>
</c:if>
<%--
</tcmis:inventoryGroupPermission>
--%>
	<c:set var="mp" value="Y"/>
	<c:set var="okp" value="Y"/>
	<c:set var="p" value="Y"/>
	<c:set var="bp" value="Y"/>
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="BuyOrder">
<c:set var="okp" value="Y"/>
<c:set var="bp" value="Y"/>
<c:set var="p" value="Y"/>
</tcmis:inventoryGroupPermission>

<c:set var="dropship" value="false"/>
<c:if test="${bean.dropShipOverride == 'Y'}">
	<c:set var="dropship" value="true"/>
</c:if>

<fmt:formatDate var="levelHoldEndDate" value="${bean.levelHoldEndDate}" pattern="${dateFormatPattern}"/>

<%--
<fmt:formatDate var="dateApproved" value="${bean.dateApproved}" pattern="${dateFormatPattern}"/>
<tcmis:jsReplace value="${bean.remarks}" processMultiLines="true"/>
--%>
{ id:${status.index +1},
data:[
'${mp}',
'${bean.inventoryGroup}',
'${bean.companyId}',
'<tcmis:jsReplace value="${bean.catalogDesc}"/>',
'<tcmis:jsReplace value="${bean.catPartNo}" processSpaces="true"/>',
'${bean.partGroupNo}',
'<tcmis:jsReplace value="${bean.specList}"/>',
'${bean.itemId}',         
'${bean.currentStockingMethod}',
'${okp}',  
'',
'${bean.stockingMethod}',
'${bean.reorderPoint}',
'${bean.stockingLevel}',
'${bean.reorderQuantity}',
'${bp}',
'${bean.lookAheadDays}',
  ${dropship},
'${levelHoldEndDate}',
'${bean.projectedLeadTime}',
'${ip}',
'${bean.receiptProcessingMethod}',
'${ip}',
'${bean.billingMethod}',
'${bp}',
'',
'${bean.stockingMethod}',
'${bean.reorderPoint}',
'${bean.stockingLevel}',
'${bean.reorderQuantity}',
'${bean.lookAheadDays}',
'${bean.receiptProcessingMethod}',
'${bean.billingMethod}',
'${bean.catalogCompanyId}',
'${bean.catalogId}',
'${levelHoldEndDate}',
'${bean.projectedLeadTime}'                
]
}

<c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>
]};

// -->
</script>
</c:if>

<!-- If the collection is empty say no data found -->
<c:if test="${empty beanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td>
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>
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
<input name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}" type="hidden"/>
<input name="hub" id="hub" value="${param.hub}" type="hidden"/>
<input name="criteria" id="criteria" value="${param.criteria}" type="hidden"/>
<input name="criterion" id="criterion" value="${param.criterion}" type="hidden"/>
<input name="inventoryGroup" id="inventoryGroup" value="${param.inventoryGroup}" type="hidden"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="showlevels" id="showlevels" type="hidden" value="showlevels"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>

<!-- Popup Calendar input options for hcal column Type in the grid-->
<input type="hidden" name="blockBefore_levelHoldEndDate" id="blockBefore_levelHoldEndDate" value="<tcmis:getDateTag numberOfDaysFromToday="-1" datePattern="${dateFormatPattern}"/>"/>
<input type="hidden" name="blockAfter_levelHoldEndDate" id="blockAfter_levelHoldEndDate" value=""/>
<input type="hidden" name="blockBeforeExclude_levelHoldEndDate" id="blockBeforeExclude_levelHoldEndDate" value=""/>
<input type="hidden" name="blockAfterExclude_levelHoldEndDate" id="blockAfterExclude_levelHoldEndDate" value=""/>
<input type="hidden" name="inDefinite_levelHoldEndDate" id="inDefinite_levelHoldEndDate" value=""/>

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

<script type="text/javascript">
<!--
<!-- permission stuff here --!>
showUpdateLinks = false;
<c:if test="${p == 'Y'}">
showUpdateLinks = true;
</c:if>
//-->
</script>

</body>
</html>
