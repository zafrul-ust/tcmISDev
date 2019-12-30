<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

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
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

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

function loadpupup() {
//	initGridWithGlobal(hgridConfig);
//	initGridWithGlobal(gridConfig);
	attachObj = $('resultsPage');
	eval('parent.${param.callback}(gridConfig,attachObj)');
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadpupup()">

<div class="interface" id="resultsPage" style="width:650px;height:400px;">
<div class="backGroundContent">

<div id="LegendArea" style="width:100%;height:372px;"></div>
<script language="JavaScript" type="text/javascript">
<!--
var config = [
  {
  	columnId:"lotStatus",
  	columnName:'<fmt:message key="label.lotstatus"/>',
  	width:10
  },
  {
  	columnId:"col2",
  	columnName:'<fmt:message key="label.pickable"/>',
  	width:6
  },
  {
  	columnId:"col3",
  	columnName:'<fmt:message key="label.allocationorder"/>',
  	align:"right",
  	width:6
  },
  {
  	columnId:"col4",
  	columnName:'<fmt:message key="label.scrapallocationorder"/>',
  	align:"right",
  	width:6
  },
  {
  	columnId:"col5",
  	columnName:'<fmt:message key="label.description"/>',
  	width:30
  },
  {
  	  	columnId:"col1"
  }
  ];

<c:set var="dataCount" value='0'/>
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${vvLotStatusLegend}" varStatus="status">
<c:set var="jspLabel" value=""/>
<c:if test="${fn:length(status.current.jspLabel) > 0}"><c:set var="jspLabel">${status.current.jspLabel}</c:set></c:if>
<c:if test="${status.index > 0}">,</c:if>
{ id:${status.index +1},
 data:[
  '<fmt:message key="${jspLabel}"/>',
  '${bean.pickable}',
  '${bean.allocationOrder}',
  '${bean.scrapAllocationOrder}',
  '<tcmis:jsReplace value="${bean.description}" processMultiLines="true"/>',
  '${bean.lotStatus}'
  ]
}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};

var gridConfig = {
    name:'<fmt:message key="label.lotstatuslegend"/>',
	width:650,
	height:400,
	divName:'LegendArea', // the div id to contain the grid.
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	beanData:jsonMainData,     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	config:config,	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	onRowSelect:null,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:null   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//	onBeforeSorting:_onBeforeSorting
};


// -->
</script>
</div>
</div> <!-- close of interface -->

</body>
</html>