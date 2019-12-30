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
<script type="text/javascript" src="/js/catalog/itemsharmonizedcode.js"></script>
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="label.logistics"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};

<tcmis:opsEntityPermission indicator="true" userGroupId="itemHarmonizedCode">
showUpdateLinks = true;
<c:set var="permission" value="Y"/>
</tcmis:opsEntityPermission>

var lineMap3 = new Array();

with ( milonic=new menuname("newItem") ) {
	 top="offset=2";
	 style=submenuStyle;
	 itemheight=17;
	 aI( "text=<fmt:message key="label.new"/> ;url=javascript:additem()" );
}

drawMenus();

function doOnRightClick(rowId,cellInd){

	<c:if test="${permission != 'Y'}">
		toggleContextMenu('');
		return;
	</c:if>

	toggleContextMenu('newItem');

}

function additem() {
	var rowId = haasGrid.getSelectedRowId();
	var ind = haasGrid.getRowIndex(rowId);
	var parentIndex = haasGrid.haasGetRowSpanStart(rowId);
	var newLinePosition = haasGrid.haasGetRowSpanEndIndex(rowId); 
		    
	var newRowData = ['Y',
	                 cellValue(rowId,"itemId"),
	                 cellValue(rowId,"itemDesc"),
	                 cellValue(rowId,"ok"),
	                 cellValue(rowId,"harmonizedTradeCode"),
	                 'Y',
	                 '',
	                 'Y',
	                 '',
	                 'N',
	                 'New'];
	haasGrid.haasAddRowToRowSpan(newRowData,newLinePosition, parentIndex);
	haasGrid.haasRenderRow(haasGrid.getRowId(parentIndex));
	haasGrid.selectRow(parentIndex);
}


var config = [
  {
  	columnId:"permission",
  	columnName:''
  },
  {
  	columnId:"itemId",
  	columnName:'<fmt:message key="label.item"/>'
  },
  {
  	columnId:"itemDesc",
  	columnName:'<fmt:message key="label.itemdescription"/>',
  	width:30
  },
  <c:if test="${ permission == 'Y'}">
  {
  	columnId:"ok",
  	columnName:'<fmt:message key="label.ok"/>',
  	type:'hch',
  	width:3
  },
  </c:if>
  {
  	columnId:"harmonizedTradeCode",
  	columnName:'<fmt:message key="label.harmonizedtradecode"/>',
  	type:'hcoro',
  	width:16
  }
  ,
  {
  	columnId:"customsRegionPermission"
  }
  ,
  {
  	columnId:"customsRegion",
  	columnName:'<fmt:message key="label.region"/>',
  	type:'hcoro',
  	width:12,
  	permission:true
  }
  ,
  {
  	columnId:"localExtensionPermission"
  }
  ,
  {
  	columnId:"localExtension",
  	columnName:'<fmt:message key="label.extension"/>',
  	type:'hed',
  	permission:true
  },
  {
	  	columnId:"isParent"
  },
  {
	  	columnId:"changed"
  }
  ];

// for regionColl dropdown
//private String customsRegion;
//private String description;

// beanOvCollection
//	private BigDecimal itemId;
//	private String itemDesc;
//	private String harmonizedTradeCode;
//	private Collection<HtcLocalObjBean> htcLocalCollection;
//	private Array htcLocalCollectionArray;

// HtcLocalObjBean, second level.
//	private String customsRegion;
//	private String localExtension;

<c:set var="harmonizedTradeCodeColl" value="${headerColl[2]}"/>
var harmonizedTradeCode = new Array( 
		{text:'<fmt:message key="label.pleaseselect"/>',value:''}
		<c:forEach var="vvBean" items="${harmonizedTradeCodeColl}" varStatus="status"> 
		,{text:'${vvBean[0]}',value:'${vvBean[0]}'}
		</c:forEach>  	
		);  

<c:set var="customsRegion" value="${regionColl[2]}"/>
var customsRegion = new Array( 
		{text:'<fmt:message key="label.pleaseselect"/>',value:''}
		<c:forEach var="vvBean" items="${customsRegion}" varStatus="status"> 
		,{text:'${vvBean[1]}',value:'${vvBean[0]}'}
		</c:forEach>  	
		);  

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">

<tcmis:form action="/itemsharmonizedcoderesults.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">
<div class="backGroundContent">
<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty beanOvCollection}" >
<div id="ItemBean" style="width:100%;height:400px;"></div>
<!-- Search results start -->
<script type="text/javascript">
<!--
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
<c:set var="showUpdateLink" value='N'/>

var lineMap = new Array();
var lineMap3 = new Array();

<c:set var="gridind" value="0"/>
<c:set var="colorIndex" value="-1"/>
<c:forEach var="bean" items="${beanOvCollection}" varStatus="status">
	
	<c:set var="colorIndex" value="${colorIndex+1}"/>
	
	<bean:size collection="${status.current.htcLocalCollection}" id="resultSize"/>
	<c:if test="${resultSize > 0}">
	    lineMap[${gridind}]  = ${resultSize};
	    <c:forEach var="bean2" items="${status.current.htcLocalCollection}" varStatus="status2">
			lineMap3[${gridind}] = ${colorIndex%2} ;
			<c:set var="gridind" value="${gridind+1}"/> 
	    </c:forEach>
	</c:if>
	<c:if test="${resultSize == 0}">
		lineMap[${gridind}]  = 1;
		lineMap3[${gridind}] = ${colorIndex%2} ;
		<c:set var="gridind" value="${gridind+1}"/>	
	</c:if>

</c:forEach>

//for regionColl dropdown
//private String customsRegion;
//private String description;

//beanOvCollection
//	private BigDecimal itemId;
//	private String itemDesc;
//	private String harmonizedTradeCode;
//	private Collection<HtcLocalObjBean> htcLocalCollection;
//	private Array htcLocalCollectionArray;

//HtcLocalObjBean, second level.
//	private String customsRegion;
//	private String localExtension;

/*Storing the data to be displayed in a JSON object array.*/
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${beanOvCollection}" varStatus="status">
<c:set var="isParent" value="Y"/>
<bean:size collection="${status.current.htcLocalCollection}" id="resultSize"/>
<c:if test="${resultSize > 0}">

<c:forEach var="rbean" items="${status.current.htcLocalCollection}" varStatus="status2">
<c:if test="${dataCount > 0}">,</c:if>
<c:set var="dataCount" value='${dataCount+1}'/>
{ id:${dataCount},
 data:[
  "${permission}",
  '${bean.itemId}',
  '<tcmis:jsReplace value="${bean.itemDesc}" processMultiLines="true"/>',
  <c:if test="${ permission == 'Y'}">
  	"",
  </c:if>
  '${bean.harmonizedTradeCode}',
  "N",
  '${rbean.customsRegion}',
  "${permission}",
  '${rbean.localExtension}',
  '${isParent}',
  ''
  ]
}
<c:set var="isParent" value="N"/>
</c:forEach>
</c:if>  

<c:if test="${resultSize == 0}">
<c:if test="${dataCount > 0}">,</c:if>
<c:set var="dataCount" value='${dataCount+1}'/>
{ id:${dataCount},
 data:[
  "Y",
  '${bean.itemId}',
  '<tcmis:jsReplace value="${bean.itemDesc}" processMultiLines="true"/>',
  <c:if test="${ permission == 'Y'}">  
  "",
  </c:if>
  '${bean.harmonizedTradeCode}',
  "Y",
  '',
  "Y",
  '',
  '${isParent}',
  'New'
]
}
</c:if>  

 </c:forEach>
]};
//-->
</script>

</div> <!-- end of grid div. -->
</c:if>

<c:if test="${empty beanOvCollection}" >
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

var gridConfig = {
		divName:'ItemBean', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not.
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
		noSmartRender: false,
		onRightClick:doOnRightClick   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	};
// What columns are rowspan
var rowSpanCols = [1,2,3,4];
		
//-->
</script>

</body>
</html:html>