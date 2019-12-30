<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support for column type hcal-->
<!--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/invlevelmanagement.js"></script>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.--%>
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
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="label.invlevelmanagement"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
itemInteger:"<fmt:message key="error.item.integer"/>"};

var gridConfig = {
	divName:'invLevelManagementBean', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	onRowSelect:doOnRowSelected,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:selectRightclick  // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//	onBeforeSorting:_onBeforeSorting
};

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
<c:set var="itemid"><fmt:message key="label.itemid"/></c:set>
<c:set var="itemdescription"><fmt:message key="label.itemdescription"/></c:set>
<c:set var="inpurchasingandnotalloc"><fmt:message key="label.inpurchasingandnotalloc"/></c:set> 
var config = [
  {
  	columnId:"opsCompanyId",
  	columnName:'<fmt:message key="label.operatingentity"/>',
  	tooltip:"Y"
  },
  {
  	columnId:"hub",
  	columnName:'<fmt:message key="label.hub"/>',
  	tooltip:"Y",
  	width:7
  },
  {
  	columnId:"inventoryGroup"
  },
  {
  	columnId:"inventoryGroup",
  	columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
  	tooltip:"Y",
  	width:10
  },
  {
  	columnId:"itemID",
  	columnName:'<tcmis:jsReplace value="${itemid}"/>',  	
  	sorting:'int',
  	width:4,
  	align:"left",
  	hiddenSortingColumn:"hItemId"
  }, 
  {
  	columnId:"hItemId",
  	sorting:'int'
  },
  {
  	columnId:"itemDesc",
  	columnName:'<tcmis:jsReplace value="${itemdescription}"/>',
  	width:15,
  	tooltip:"Y"
  },
  {
		columnId:"customerPart",
		columnName:'<fmt:message key="label.customerpart"/>',
		tooltip:"Y",
		width:10
  },
 {
  	columnId:"stockingMethod",
  	columnName:'<fmt:message key="label.stockingmethod"/>',
  	width:5
  },
  {
  	columnId:"reorderPoint",
  	columnName:'<fmt:message key="label.reorderpoint"/>',
  	width:5,  
  	sorting:'int',
  	hiddenSortingColumn:"hReorderPoint"
  }, 
  {
  	columnId:"hReorderPoint",
  	sorting:'int'
  },
  {
  	columnId:"stockingLevel",
  	columnName:'<fmt:message key="label.stockinglevel"/>',
  	width:5,
  	align:'right'
  },
  {
	columnId:"reorderQuantity",
	columnName:'<fmt:message key="label.reorderquantity"/>',
	sorting:'int',
	width:5	  	
	},  
  {
  	columnId:"onHand",
  	columnName:'<fmt:message key="label.onhand"/>',
  	sorting:'int',
  	width:4
  },
  {
	  	columnId:"purchaseCost",
	  	columnName:'<fmt:message key="label.purchasecost"/>',
	  	align:"center",
	  	width:6
	  },
  {
  	columnId:"currencyId",
  	columnName:'<fmt:message key="label.currencyid"/>',
  	align:"center",
  	width:5
  },
  {
  	columnId:"onHandValue",
  	columnName:'<fmt:message key="label.onhandvalue"/>',
  	sorting:'int',
  	width:4
  },
  {
  	columnId:"pickableMinusAlloc",
  	columnName:'<fmt:message key="label.pickableandnotalloc"/>',
  	sorting:'int',
  	width:6
  },
  {
  	columnId:"nonpickableMinusAlloc",
  	columnName:'<fmt:message key="label.nonpickableandnotalloc"/>',
  	sorting:'int',
  	width:7
  },
  {
  	columnId:"pickableMinusAlloc",
  	columnName:'<fmt:message key="label.onorderandnotalloc"/>',
  	sorting:'int',
  	width:6
  },
  {
  	columnId:"onorderMinusAlloc",
  	columnName:'<tcmis:jsReplace value="${inpurchasingandnotalloc}"/>',
  	sorting:'int',
  	width:6
  },
  {
  	columnId:"inTransit",
    columnName:'<fmt:message key="label.qtyintransit"/>',
    sorting:'int',
  	width:6
  },
  {
  	columnId:"lastReceived",
  	columnName:'<fmt:message key="label.lastreceived"/>',
	sorting:'int',
	align:"center",
	width:7,
	hiddenSortingColumn:"hLastReceived"
  },
  {
  	columnId:"hLastReceived"
  },
  
  {
  	columnId:"issuedLast30",
  	columnName:'<fmt:message key="label.issuedlast30"/>',
  	sorting:'int',
  	width:5
  },
  {
  	columnId:"issued3060",
  	columnName:'<fmt:message key="label.issued30-60"/>',
  	sorting:'int',
  	width:5
  },
  {
  	columnId:"issued6090",
  	columnName:'<fmt:message key="label.issued60-90"/>',
  	sorting:'int',
  	width:5
  },
  {
  	columnId:"minUnitPrice",
  	columnName:'<fmt:message key="label.minunitprice"/>',
  	sorting:'int',
  	width:5
  },
  {
  	columnId:"avgUnitPrice",
  	columnName:'<fmt:message key="label.avgunitprice"/>',
  	sorting:'int',
  	width:5
  },
  {
  	columnId:"maxUnitPrice",
  	columnName:'<fmt:message key="label.maxunitprice"/>',
  	sorting:'int',
  	width:5
  },
  {
  	columnId:"avgRpValue",
  	columnName:'<fmt:message key="label.avgreorderpointvalue"/>',
  	sorting:'int',
  	width:7  
  },
  {
  	columnId:"avgSlValue",
  	columnName:'<fmt:message key="label.avgstockinglevelvalue"/>',
  	sorting:'int',
  	width:7 
  },
  {
  	columnId:"hHub",
  	columnName:'' 
  },
  {
  	columnId:"opsEntityId",
  	columnName:'' 
  }

  ];
  
with(milonic=new menuname("invLevelManagementMenu")){
         top="offset=2"
         style = contextStyle;
         margin=3
         aI("text=<fmt:message key="label.crosstab"/>;url=javascript:showcrosstab();");  
         aI("text=<fmt:message key="label.showchangeminmax"/>;url=javascript:showMinMax();");
    }

drawMenus();

// -->
</script>
</head> 

<!--TODO: Need to show reorder quantity on this page.-->

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">

<tcmis:form action="/invlevelmanagementresults.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">
<div class="backGroundContent">
<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty invLevelManagementBeanCollection}" >
<div id="invLevelManagementBean" style="width:100%;height:400px;"></div>
<!-- Search results start -->
<script type="text/javascript">
<!--
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
<c:set var="showUpdateLink" value='N'/>
/*Storing the data to be displayed in a JSON object array.*/
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${invLevelManagementBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<c:choose>
   <c:when test="${bean.onHandValue eq null}">
     <c:set var="currencyId" value='' />
   </c:when>
   <c:otherwise>
   	 <c:set var="currencyId" value='${bean.currencyId}' />
   </c:otherwise>
</c:choose>
<fmt:formatNumber var="onHandValue" value="${bean.onHandValue}" pattern="${totalcurrencyformat}"></fmt:formatNumber>

<fmt:formatNumber var="minUnitPrice" value="${bean.minUnitPrice}" pattern="${unitpricecurrencyformat}"></fmt:formatNumber>

<fmt:formatNumber var="avgUnitPrice" value="${bean.avgUnitPrice}" pattern="${unitpricecurrencyformat}"></fmt:formatNumber>

<fmt:formatNumber var="maxUnitPrice" value="${bean.maxUnitPrice}" pattern="${unitpricecurrencyformat}"></fmt:formatNumber>

<fmt:formatNumber var="avgRpValue" value="${bean.avgRpValue}" pattern="${unitpricecurrencyformat}"></fmt:formatNumber>

<fmt:formatNumber var="avgSlValue" value="${bean.avgSlValue}" pattern="${unitpricecurrencyformat}"></fmt:formatNumber>

<fmt:formatDate var="lastReceived" value="${bean.lastReceived}" pattern="${dateFormatPattern}"/>
{ id:${status.index +1},
 data:[
  '${bean.operatingEntityName}',
  '${bean.hubName}',
  '${bean.inventoryGroup}',         
  '${bean.inventoryGroupName}',
  '${bean.itemId}','${bean.itemId}', 
  '<tcmis:jsReplace value="${bean.itemDesc}" processMultiLines="true"/>',
  '<tcmis:jsReplace value="${bean.customerPart}"/>',
  '${bean.stockingMethod}',
  '${bean.reorderPoint}','${bean.reorderPoint}',   
  '${bean.stockingLevel}','${bean.reorderQuantity}',
  '${bean.onHand}',
  '${bean.purchaseCurrencyId} ${bean.purchaseUnitCost}',
  '${currencyId}',
  '${onHandValue}','${bean.pickableMinusAlloc}',
  '${bean.nonpickableMinusAlloc}','${bean.onorderMinusAlloc}','${bean.inpurchasingMinusAlloc}',
  '${bean.inTransit}',
  '${lastReceived}','${bean.lastReceived.time}',
  '${bean.issuedLast30}','${bean.issued3060}','${bean.issued6090}',
  '${minUnitPrice}','${avgUnitPrice}',
  '${maxUnitPrice}','${avgRpValue}','${avgSlValue}','${bean.hub}','${bean.opsEntityId}'
  ]
}

 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};

// -->
</script>

</c:if>

<c:if test="${empty invLevelManagementBeanCollection}" >
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
<input name="hub" id="hub" value="<c:out value="${param.hub}"/>" type="hidden">
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

<!-- permission stuff here --!>
showUpdateLinks = true;
//-->
</script>

</body>
</html:html>