<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS -->
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


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/cabinet/workareastocktransferresults.js"></script>

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
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
    <fmt:message key="workAreaStockTransfer"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
            validvalues:"<fmt:message key="label.validvalues"/>",
            submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
            recordFound:"<fmt:message key="label.recordFound"/>",
            searchDuration:"<fmt:message key="label.searchDuration"/>",
            minutes:"<fmt:message key="label.minutes"/>",
            seconds:"<fmt:message key="label.seconds"/>",
            ok:"<fmt:message key="label.ok"/>",
            updatesucess:"<fmt:message key="msg.savesuccess"/>",
            errorInteger:"<fmt:message key="errors.integer"/>",
    		transferQuantity:"<fmt:message key="label.transfer"/>",
    		transferQuantityLessThanQuantity:"<fmt:message key="error.transferquantity.lessthanquantity"/>",
    		positiveInteger:"<fmt:message key="label.positiveinteger"/>"
			};
				

var gridConfig = {
		divName:'workAreaStockTransferBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not. Set this to -1 to disable rowSpan and smart rendering, but the sorting will still work
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click
	    noSmartRender:false // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
	};


var config = [
  {columnId:"permission"},
  {columnId:"catPartNo", columnName:'<fmt:message key="label.part"/>', sorting:'haasStr',  align:'right', width:5},
  {columnId:"okDoUpdate"},
  {columnId:"transferQuantityPermission"},
  {columnId:"transferQuantity" ,columnName:'<fmt:message key="label.transfer"/>', align:'center', type:'hed', onChange:setUpdate, width:3, permission:true },
  {columnId:"quantity", columnName:'<fmt:message key="label.quantity"/>', width:3 },
  {columnId:"receiptId", columnName:'<fmt:message key="label.receipt"/>', sorting:'int', width:5},
  {columnId:"itemId", columnName:'<fmt:message key="label.item"/>', sorting:'int', width:5},
  {columnId:"itemDesc", columnName:'<fmt:message key="label.description"/>', tooltip:"Y", width:25 },
  {columnId:"fromBinId"},
  {columnId:"toBinId"}
];

// -->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig); checkUpdateSuccess()">
<tcmis:form action="/workareastocktransferresults.do" onsubmit="return submitFrameOnlyOnce();">
	<tcmis:applicationPermission indicator="true" userGroupId="StockedPartMgmt" companyId="${param.companyId}" facilityId="${param.facilityId}">
			<c:set var="userHasPermission" value='Y'/>
			<script type="text/javascript">
				<!--
                    showUpdateLinks = true;
				 //-->
			</script>
	</tcmis:applicationPermission>
<div id="errorMessagesAreaBody" style="display:none;">
    <c:forEach var="tcmisError" items="${tcmISError}">
      ${tcmisError}<br/><br/>
    </c:forEach>
    <c:if test="${param.maxData == fn:length(searchResultBeanCollection)}">
     <fmt:message key="label.maxdata">
       <fmt:param value="${param.maxData}"/>
     </fmt:message>
    </c:if>
</div>


<script type="text/javascript">
    <c:choose>
    <c:when test="${empty tcmISErrors and empty tcmISError}">
     <c:choose>
      <c:when test="${param.maxData == fn:length(searchResultBeanCollection)}">
        showErrorMessage = true;
        parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
      </c:when>
      <c:otherwise>
        showErrorMessage = false;
      </c:otherwise>
     </c:choose>
    </c:when>
    <c:otherwise>
      parent.messagesData.errors = "<fmt:message key="label.errors"/>";
      showErrorMessage = true;
      </c:otherwise>
    </c:choose>
    <c:choose>
	<c:when test="${updateSuccess == 'Y'}">
		updateSuccess = true;
	</c:when>
	<c:otherwise>
		updateSuccess = false;
	</c:otherwise>
	</c:choose>
    //-->
</script>

<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="workAreaStockTransferBean" style="width:100%;height:400px;" style="display: none;"></div>

<script type="text/javascript">
<!--

<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty searchResultBeanCollection}" >  
	var jsonMainData = new Array();
	var jsonMainData = {
	rows:[
		<c:forEach var="bean" items="${searchResultBeanCollection}" varStatus="status">
		<c:if test="${status.index > 0}">,</c:if>

		/*The row ID needs to start with 1 per their design.*/
		{ id:${status.index +1},
		  data:[
				'${userHasPermission}',
				'${bean.catPartNo}',
				'',
				'${userHasPermission}',
				'',
				'${bean.quantity}',
				'${bean.receiptId}',
				'${bean.itemId}',
				'<tcmis:jsReplace value="${bean.itemDesc}" processMultiLines="true"/>',
				'${bean.fromBinId}',
				'${bean.toBinId}'
		  ]}  
	 	<c:set var="dataCount" value='${dataCount+1}'/> 
	</c:forEach>
	]};
</c:if>

var rowSpanMap = new Array();
var rowSpanClassMap = new Array();
var rowSpanCols = [0,1];

<%-- determining rowspan --%>
<c:forEach var="row" items="${searchResultBeanCollection}" varStatus="status">
<c:choose>
	<c:when test="${status.first}">
		<c:set var="rowSpanStart" value='0' />
		<c:set var="rowSpanCount" value='1' />
		rowSpanMap[0] = 1;
		rowSpanClassMap[0] = 1 % 2;
	</c:when>
	<c:when test="${row.catPartNo == prevCatPartNo}">
		rowSpanMap[${rowSpanStart}]++;
		rowSpanMap[${status.index}] = 0;
		rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
	</c:when>
	<c:otherwise>
		<c:set var="rowSpanStart" value='${status.index}' />
		<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
		rowSpanMap[${status.index}] = 1;
		rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
	</c:otherwise>
</c:choose>
<c:set var="prevCatPartNo" value='${row.catPartNo}' />
</c:forEach>
//-->  
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty searchResultBeanCollection}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
    <tr>
       <td width="100%">
          <fmt:message key="main.nodatafound"/>
       </td>
    </tr>
  </table>
</c:if>
        
<!-- Search results end -->

<!-- Hidden element start --> 
<div id="hiddenElements" style="display: none;">    			
	<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
    <input name="uAction" id="uAction" value="" type="hidden"/>
    <input name="minHeight" id="minHeight" type="hidden" value="100"/>
    <input name="dateStart" id="dateStart" value="${param.dateStart}" type="hidden"/>
    <input name="dateStop" id="dateStop" value="${param.dateStop}" type="hidden"/> 
    <input name="searchArgument" id="searchArgument" value="${param.searchArgument}" type="hidden"/>
	<input name="criterion" id="criterion" value="${param.criterion}" type="hidden"/>
	<input name="criteria" id="criteria" value="${param.criteria}" type="hidden"/>
	<input name="maxData" id="maxData" value="${param.maxData}" type="hidden"/>
	<input name="companyId" id="companyId" value="${param.companyId}" type="hidden" />
	<input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden"/>
	<input name="facilityName" id="facilityName" value="${param.facilityName}" type="hidden"/>
    <input name="inventoryGroup" id="inventoryGroup" value="${param.inventoryGroup}" type="hidden"/>
  <!-- Put all the Search Criteria here for re-search after update-->
  	<div id="input">
  		<c:forEach var="row" items="${searchInputBeanCollection}" varStatus="status">
			<input name="input[${status.index}].applicationId" id="input[${status.index}].applicationId" value="${row.applicationId}" type="hidden"/>	
			<input name="input[${status.index}].reportingEntityId" id="input[${status.index}].reportingEntityId" value="${row.reportingEntityId}" type="hidden"/>
		    <input name="input[${status.index}].deptId" id="input[${status.index}].deptId" value="${row.deptId}" type="hidden"/>
		    <input name="input[${status.index}].areaId" id="input[${status.index}].areaId" value="${row.areaId}" type="hidden"/>
		    <input name="input[${status.index}].buildingId" id="input[${status.index}].buildingId" value="${row.buildingId}" type="hidden"/>
		</c:forEach>
    </div>
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>
</body>
</html:html>