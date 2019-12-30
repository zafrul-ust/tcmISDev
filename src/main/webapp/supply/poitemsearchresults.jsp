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

<!-- For Calendar support 

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

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

<script type="text/javascript" src="/js/distribution/companysearch.js"></script>

<title>
<fmt:message key="label.purchaseorderitemsearch"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",	
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"
,selectedRowMsg:"<fmt:message key="label.selecteditem"/>"};

var config = [
  {
  	columnId:"itemId",
  	columnName:'<fmt:message key="label.itemid"/>',
		width:6
  },
	{
		columnId:"itemType",
		columnName:'<fmt:message key="label.type"/>',
		width:4
	},
	{
  		columnId:"catalogId",
  		columnName:'<fmt:message key="label.catalogid"/>'
  	},
	{
  		columnId:"catPartNo",
  		columnName:'<fmt:message key="label.partnum"/>',
  		width:12
  	},
	{
  		columnId:"priority",
  		columnName:'<fmt:message key="label.priority"/>'  			,
  			width:3
  	},
	{
  		columnId:"status",
  		columnName:'<fmt:message key="label.status"/>',
		width:3
  	},
	{
  		columnId:"itemDesc",
  		columnName:'<fmt:message key="label.itemdesc"/>',
  		width:20
  	},
	{
  		columnId:"packaging",
  		columnName:'<fmt:message key="label.packaging"/>',
  		width:20
  	},
  	{
  		columnId:"companyId"
    }
    ,
  	{
  		columnId:"partGroupNo"
    }
    ,
  	{
  		columnId:"unitOfSale"
    }		
  ];

_gridConfig.onRowSelect = selectRow;
_gridConfig.onRightClick= selectRow;

var selectedrow=null;
var currentId = '';
var currentType = '';
var currentName = '';
var currentShortName = '';
var currentCompanyId = '';
var currentCatalogId = '';
var currentCatPartNo = '';

windowCloseOnEsc = true;

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
   haasGrid.selectRowById(rowId0,null,false,false);	
 //  selectRow(rowId,cellInd);

//   if( !rightClick ) return;
	
   selectedrow = rowId0;
   currentId = cellValue(rowId0,"itemId")
   currentType = cellValue(rowId0,"itemType");
   currentName = cellValue(rowId0,"itemDesc");
//   currentShortName = cellValue(rowId0,"companyShortName");
   currentCompanyId = cellValue(rowId0,"companyId");
   currentCatalogId = cellValue(rowId0,"catalogId");
   currentCatPartNo = cellValue(rowId0,"catPartNo");
   currentPartGroupNo = cellValue(rowId0,"partGroupNo");
   currentUnitOfSale = cellValue(rowId0,"unitOfSale");
   
   var selectedUser = parent.document.getElementById("updateResultLink");
   selectedUser.innerHTML = '<a href="#" onclick="call(\'selectData\'); return false;">'+messagesData.selectedRowMsg+' : '+ currentId +'</a>';
   
}

function selectData()
{ 
  try {
 	parent.opener.document.getElementById(parent.$v('valueElementId')).value = currentId;
  	var openerdisplayElementId = parent.opener.document.getElementById(parent.$v('displayElementId'));
  	openerdisplayElementId.className = "inputBox";
  	openerdisplayElementId.value = currentName;
  } catch( ex ) {}
  
  try {
  if( parent.opener.itemChanged ) {
	  try {
	  	parent.opener.itemChanged(currentId,currentName,currentType);
	  }
	  catch (e) {
		parent.opener.itemChanged(currentId,currentName);
	  }
  }
  if( parent.opener.returnCatalogInfo) {
         var p = new catalogInfo();
	  	parent.opener.returnCatalogInfo(p);
	  	
	  }
  } catch(exx) {}
	parent.close();
}

function catalogInfo() {
    this.companyId = currentCompanyId;
	this.catalogId = currentCatalogId;
	this.catPartNo = currentCatPartNo; 
	this.partGroupNo = currentPartGroupNo;
	this.unitOfSale = currentUnitOfSale;
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(_gridConfig);parent.document.getElementById('updateResultLink').innerHTML=''">

<tcmis:form action="/poitemsearchresults.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">
<div class="backGroundContent">
<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty pOItemBeanCollection}" >
<div id="beanData" style="width:100%;height:400px;"></div>
<!-- Search results start -->
<script type="text/javascript">
<!--
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
<c:set var="showUpdateLink" value='N'/>
/*Storing the data to be displayed in a JSON object array.*/
var jsonData = {
rows:[
<c:forEach var="bean" items="${pOItemBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<tcmis:jsReplace var="itemDesc" value="${bean.itemDesc}" processMultiLines="true"/>
<tcmis:jsReplace var="packaging" value="${bean.packaging}" processMultiLines="true"/>
<tcmis:jsReplace var="catPartNo" value="${bean.catPartNo}"/>
{ id:${status.index +1},
 data:[
	  '${bean.itemId}',
	  '${bean.itemType}',
	  '${bean.catalogId}',
	  '${catPartNo}',
	  '${bean.priority}',
	  '${bean.status}',
	  '${itemDesc}',
	  '${packaging}',
	  '${bean.companyId}',
	  '${bean.partGroupNo}',
	  '${bean.unitOfSale}'
  ]
}

 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
//-->
</script>

</c:if>

<c:if test="${empty pOItemBeanCollection}" >
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