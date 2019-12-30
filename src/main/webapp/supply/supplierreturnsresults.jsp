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
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/supply/supplierreturns.js"></script>

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
<fmt:message key="label.customerreturntracking"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
maximumallowedtext:"<fmt:message key="label.maximumallowedtext">
          	    <fmt:param>${400}</fmt:param>
          	  </fmt:message>",
qtyreturned:"<fmt:message key="label.qtyreturned"/>",returndate:"<fmt:message key="label.returndate"/>",supplierrma:"<fmt:message key="label.supplierrma"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);resultOnLoad();">
<tcmis:form action="/supplierreturnsresults.do" onsubmit="return submitFrameOnlyOnce();">

<script language="JavaScript" type="text/javascript">
<!--
var gridConfig = {
	divName:'logisticsViewBean', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
//	onCheck:checkBoxValidate,
	onRowSelect:null,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:null,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
    singleClickEdit:true
//	onBeforeSorting:_onBeforeSorting
};

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
<c:set var="itemid"><fmt:message key="label.itemid"/></c:set>
var config = [
  {
  	columnId:"permission",
  	columnName:''
  },
  {
  	columnId:"operatingEntityName",
  	columnName:'<fmt:message key="label.operatingentity"/>'
  },
  {
  	columnId:"hubName",
  	columnName:'<fmt:message key="label.hub"/>'
  },
  {
  	columnId:"inventoryGroupName",
  	columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
  	width:10
  },
  {
  	columnId:"itemId",
  	columnName:'<tcmis:jsReplace value="${itemid}"/>'
  },
  {
  	columnId:"partShortName",
  	columnName:'<fmt:message key="label.shortDesc"/>',
  	width:12,
  	tooltip:"Y"
  },
  {
  	columnId:"haasPo",
  	columnName:'<fmt:message key="label.haaspo"/>'
  }, 
  {
  	columnId:"receiptId",
  	columnName:'<fmt:message key="label.receiptid"/>',
  	width:8
  },
  {
  	columnId:"quantity",
  	columnName:'<fmt:message key="label.qtyavailable"/>',
  	width:6,
  	sorting:'int'
  },
  {
  	columnId:"lotStatus",
  	columnName:'<fmt:message key="label.lotstatus"/>',
  	width:8
  },
  {
  	columnId:"ok",
    columnName:'<fmt:message key="label.ok"/>',
    type:'hch',
    width:4,
    onCheck:checkBoxValidate
  },
  {
  	columnId:"qtyReturned",
  	columnName:'<fmt:message key="label.qtyreturned"/>',
  	type:'hed',
//  	onChange:checkQtyReturned,
    width:6
  },
  {
  	columnId:"returnDate",
  	columnName:'<fmt:message key="label.returndate"/>',
  	type:'hcal',
    width:10
  },
  {
  	columnId:"supplierRma",
  	columnName:'<fmt:message key="label.supplierrma"/>',
  	type:"hed",
    width:7
  },
  {
  	columnId:"comments",
  	columnName:'<fmt:message key="label.comments"/>',
  	type:"txt",
    width:20
  }
  ];

// -->
</script>

<div class="interface" id="resultsPage">
<div class="backGroundContent">
<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty logisticsViewBeanCollection}" >
<div id="logisticsViewBean" style="width:100%;height:400px;"></div>
<!-- Search results start -->
<script type="text/javascript">
<!--

<c:set var="dataCount" value='${0}'/>
<c:set var="customerOwnedDataCount" value='${0}'/>
<c:set var="showUpdateLink" value=''/>
<c:if test="${!empty logisticsViewBeanCollection}" >
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="logisticsBean" items="${logisticsViewBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<c:set var="permission" value=''/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="supplierReturns" inventoryGroup="${logisticsBean.inventoryGroup}">
    <c:set var="permission" value='Y'/>
    <c:set var="showUpdateLink" value='Y'/>
</tcmis:inventoryGroupPermission>

<c:set var="rowClass" value=""/>
<c:choose>
	<c:when test="${dataCount % 2 == 1}"><c:set var="rowState" value="odd_haas"/></c:when>
	<c:otherwise><c:set var="rowState" value="ev_haas"/></c:otherwise>
</c:choose>
<c:if test="${!empty logisticsBean.ownerCompanyId && 'HAAS' != logisticsBean.ownerCompanyId && 'RADIAN' != logisticsBean.ownerCompanyId}">
	<c:set var="permission" value='N'/>
	<c:set var="rowClass" value="grid_red "/>
	<c:set var="customerOwnedDataCount" value='${customerOwnedDataCount+1}'/> 
</c:if>

<tcmis:jsReplace var="partShortName" value="${logisticsBean.partShortName}" processMultiLines="true" />  

/*The row ID needs to start with 1 per their design.*/ 
{ id:${status.index +1},
  "class": '${rowClass}${rowState}',
  data:['${permission}',
	  '${logisticsBean.operatingEntityName}', '<tcmis:jsReplace value="${logisticsBean.hubName}" />', 
	  '<tcmis:jsReplace value="${logisticsBean.inventoryGroupName}" />',
	  '${logisticsBean.itemId}', '${partShortName}','${logisticsBean.radianPo}-${logisticsBean.poLine}',
	  '${logisticsBean.receiptId}', '${logisticsBean.quantity}','${logisticsBean.lotStatus}', 
	  '','','','',''
  	]}  
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>
// -->
</script>
</c:if>

<c:if test="${empty logisticsViewBeanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>

<c:if test="${showUpdateLink == 'Y' && dataCount-customerOwnedDataCount > 0}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>

<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input type="hidden" name="uAction" id="uAction" value=""/>
<input name="opsEntityId" id="totaopsEntityIdlLines" value="${param.opsEntityId}" type="hidden"/>
<input name="hub" id="hub" value="${param.hub}" type="hidden"/>
<input name="inventoryGroup" id="inventoryGroup" value="${param.inventoryGroup}" type="hidden"/>
<input name="searchField" id="searchField" value="${param.searchField}" type="hidden"/>
<input name="searchMode" id="searchMode" value="${param.searchMode}" type="hidden"/>
<input name="searchArgument" id="searchArgument" value="${param.searchArgument}" type="hidden"/>
<input type="hidden" name="blockBefore_returnDate" id="blockBefore_returnDate" value=""/>
<input type="hidden" name="blockAfter_returnDate" id="blockAfter_returnDate" value=""/>
<input type="hidden" name="blockBeforeExclude_returnDate" id="blockBeforeExclude_returnDate" value=""/>
<input type="hidden" name="blockAfterExclude_returnDate" id="blockAfterExclude_returnDate" value='<tcmis:getDateTag numberOfDaysFromToday="7" datePattern="${dateFormatPattern}"/>'/>
<input type="hidden" name="inDefinite_returnDate" id="inDefinite_returnDate" value=""/>

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

//-->
</script>
</body>
</html:html>