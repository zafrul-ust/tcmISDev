<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss overflow="notHidden"/>
<%-- Add any other stylesheets you need for the page here --%>
<!-- Add any other stylesheets you need for the page here -->
<link rel="stylesheet" type="text/css" href="/css/tabs.css"/>  

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
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


<!-- These are for the Grid-->
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>


<%-- add all the javascript messages here, this for internationalization of client side javascript messages --%>
<script type="text/javascript">
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

<%-- Define the right click menus --%>
with(milonic=new menuname("viewDoc")){
	top="offset=2"
	style = contextStyle;
	margin=3
	aI("text=<fmt:message key="pickingqc.viewaddreceipts"/>;url=javascript:showProjectDocuments();");
}

<%-- Initialize the RCMenus --%>
drawMenus();

var gridConfig = {
		divName:'receivingHistoryViewBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beangrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'beanConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:'false',			 // this page has rowSpan > 1 or not.
		submitDefault:'true',    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		noSmartRender:false, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		onRightClick: showMenu // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		};

var beanConfig = [
{
  		columnId:"permission"
}, 
{
	  columnId:"poLine",
	  columnName:'<fmt:message key="label.poline"/>',
	  width:8
},
{
	  columnId:"supplier",
	  columnName:'<fmt:message key="label.supplier"/>',
	  width:15,
	  toolTip:"Y"
},
{
	  columnId:"item",
	  columnName:'<fmt:message key="label.item"/>',
	  width:5
},
{
	  columnId:"bin",
	  columnName:'<fmt:message key="label.bin"/>',
	  width:10
},
{
	  columnId:"receiptIdDisplay",
	  columnName:'<fmt:message key="label.receiptid"/>',
	  width:7
},
{
	  columnId:"inventoryGroup",
	  columnName:'<fmt:message key="label.inventorygroup"/>',
	  width:10
},
{
	  columnId:"status",
	  columnName:'<fmt:message key="label.status"/>',
	  width:5
},
{
	  columnId:"variable",
	  <c:choose>
		  <c:when test="${!empty itemId}" >
		  	columnName:'<fmt:message key="label.mfglot"/>',
		  </c:when>
		 <c:when test="${!empty radianPo}" >
		  	columnName:'<fmt:message key="receivinghistory.label.supplierref"/>',
		 </c:when>
	  </c:choose>
	  width:10
},
{
	  columnId:"qty",
	  columnName:'<fmt:message key="label.qty"/>',
	  width:3
},
{
	  columnId:"dor",
	  columnName:'<fmt:message key="receivedreceipts.label.dor"/>',
	  width:7,
	  hiddenSortingColumn:'hiddenDor', 
	  sorting:'int' 
},
{
	  columnId:"dateQced",
	  columnName:'<fmt:message key="receivinghistory.label.dateqced"/>',
	  width:7,
	  hiddenSortingColumn:'hiddenDateQced', 
	  sorting:'int' 
},
{
	 columnId:"receiptId"
},
{ 
	  columnId:"expireDateDisplay",
	  columnName:'<fmt:message key="label.expiredate"/>',
	  width:7,
	  hiddenSortingColumn:'hiddenExpireDate', 
	  sorting:'int' 
},
{columnId:"hiddenExpireDate", sorting:'int' },
{columnId:"hiddenDateQced", sorting:'int' },
{columnId:"hiddenDor", sorting:'int' }

]

function showMenu() {
	 toggleContextMenu('viewDoc');
}

function showProjectDocuments() {

	var rowNumber = beangrid.getSelectedRowId();
	/* var loc = "/tcmIS/hub/receiptdocuments.do?receiptId=" + cellValue(rowNumber,'receiptId')
			+ "&showDocuments=Yes&inventoryGroup=" + cellValue(rowNumber,'inventoryGroup') + ""; */
	var loc = "receiptdocuments.do?receiptId=" + cellValue(rowNumber,'receiptId')
			+ "&showDocuments=Yes&inventoryGroup=" + cellValue(rowNumber,'inventoryGroup') + "";
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	try {
		children[children.length] = openWinGeneric(loc,
				"showAllProjectDocuments", "450", "300", "no", "80", "80");
	} catch (ex) {
		openWinGeneric(loc, "showAllProjectDocuments", "450", "300", "no",
				"80", "80");
	}
}

</script>


<c:set var="itemId" value='${param.itemId}'/>
<c:set var="radianPo" value='${param.radianPo}'/>

<title>
<c:choose>
  <c:when test="${!empty itemId}" >
   <fmt:message key="receivinghistory.label.approved.itemtitle"/>&nbsp;:&nbsp;<c:out value="${param.itemId}"/> in <c:out value="${param.inventoryGroup}"/>
  </c:when>
  <c:when test="${!empty radianPo}" >
   <fmt:message key="receivinghistory.label.approved.potitle"/>&nbsp;:&nbsp;<c:out value="${param.radianPo}"/>-<c:out value="${param.poLine}"/> in <c:out value="${param.inventoryGroup}"/>
  </c:when>
</c:choose>
</title>
</head>

<body bgcolor="#ffffff" onload="popupOnLoadWithGrid(gridConfig);"  onresize="setTimeout('resizeFrames();',55)">
<tcmis:form action="/showreceivedreceipts.do" onsubmit="return submitFrameOnlyOnce();">


<div class="interface" id="mainPage"> <!-- start of interface-->

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="100%" class="headingl">
<c:choose>
  <c:when test="${!empty itemId}" >
   <fmt:message key="receivinghistory.label.approved.itemtitle"/>&nbsp;:&nbsp;<c:out value="${param.itemId}"/> in <c:out value="${param.inventoryGroup}"/>
  </c:when>
  <c:when test="${!empty radianPo}" >
   <fmt:message key="receivinghistory.label.approved.potitle"/>&nbsp;:&nbsp;<c:out value="${param.radianPo}"/>-<c:out value="${param.poLine}"/> in <c:out value="${param.inventoryGroup}"/>
  </c:when>
</c:choose>
</td>
</tr>
</table>
</div>

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
<!-- Error Messages Ends -->  

<div id="transitDailogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
</div>
<div id="transitDailogWinBody" class="errorMessages" style="display: none;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" id="transitLabel">
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</td>
		</tr>
	</table>
</div>

<div class="interface" id="resultsPage">
<div class="backGroundContent"> <!-- start of backGroundContent -->
<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;">

<!-- results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
          <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
     <tr>
      <td class="alignLeft" width="5%"><fmt:message key="label.rctLegend"/>&nbsp;</td>
      <!--  for black color
      -->
      <td class="black" width="5%">&nbsp;</td>
      <td class="alignLeft" width="100%">&nbsp;<fmt:message key="label.rctvtrRMA"/>
        | <a href="#" onclick="window.close()"><fmt:message key="label.close"/></a>
      </td>
    </tr>
   </table>
    </div>
     </div> <%-- mainUpdateLinks Ends --%>
    <div class="dataContent">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

  <div id="receivingHistoryViewBean" style="width:100%;height:500px;" style="display: none;"></div>
	<c:set var="dataCount" value='${0}'/>
	<script type="text/javascript">
	
	<!--
	<c:if test="${!empty receivingHistoryViewBeanCollection}">
	var jsonMainData = new Array();
	var jsonMainData = {
	rows:[
	<c:forEach var="bean" items="${receivingHistoryViewBeanCollection}" varStatus="status">
	<c:if test="${status.index > 0}">,</c:if>
	{id:${status.index +1},
		<c:if test="${critical == 'Y' || critical == 'y'}">
			'class':"grid_black",
		</c:if>
	 data:[
	       'N',
	       '${status.current.radianPo} - ${status.current.poLine}',
	       '${status.current.supplierName}',
	       '${status.current.itemId}',
	       '${status.current.bin}',
	       '<c:if test="${status.current.receiptDocumentAvailable == 'Y'}"><img src="/images/buttons/document.gif"/></c:if>${status.current.receiptId}',
	       '${status.current.inventoryGroup}',
	       '${status.current.lotStatus}',
	       '${status.current.mfgLot}',
	       '${status.current.quantityReceived}',
	       '<fmt:formatDate value="${status.current.dateOfReceipt}" pattern="${dateFormatPattern}"/>',
	       	'<fmt:formatDate value="${status.current.qcDate}" pattern="${dateFormatPattern}"/>',
	       	'${status.current.receiptId}',
	    	'<fmt:formatDate value="${status.current.expireDate}" pattern="${dateFormatPattern}"/>',
	    	'${status.current.expireDate.time}',
	    	'${status.current.qcDate.time}',
	    	'${status.current.dateOfReceipt.time}'
	    	
	    	
	  ]}
	  
	 <c:set var="dataCount" value='${dataCount+1}'/> 
	 </c:forEach>
	]};
	</c:if>
	
	 //-->
	</script>
  


   <%-- If the collection is empty say no data found --%>
   <c:if test="${empty receivingHistoryViewBeanCollection}" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>

  </div>
  </div>
  
  </div> <!-- close of contentArea -->
     <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>
   </div>
   
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
    </td>
</tr>
</table>


</div> <!-- close of background 1 -->

</div> <!-- close of interface -->


<%-- Hidden element start --%>
 <div id="hiddenElements" style="display: none;">
    <input type="hidden" name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>"/>
    <input name="startSearchTime" id="startSearchTime" type="hidden" value="${param.startSearchTime}"/>
	<!--This sets the end time for time elapesed from the action.-->
	<input name="endSearchTime" id="endSearchTime" type="hidden" value=""/>
	<input name="minHeight" id="minHeight" type="hidden" value="100"/>
	   <input type="hidden" name="personnelCompanyId"  id="personnelCompanyId" value="${personnelBean.companyId}""/>
 </div>
<%-- Hidden elements end --%>

</div> <%-- close of contentArea --%>

<%-- Footer message start --%>
 <div class="messageBar">&nbsp;</div>
<%-- Footer message end --%>
</div> 
</div> <%-- close of interface --%>
</tcmis:form>
</body>
</html:html>