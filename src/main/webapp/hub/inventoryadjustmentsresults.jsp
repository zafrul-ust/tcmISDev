<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/inventoryadjustmentsresults.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

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
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
    <fmt:message key="inventoryadjustments.title"/>
</title>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
lotstatus:"<fmt:message key="label.lotstatus"/>",
pleaseSelect:"<fmt:message key="label.pleaseselect"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};


<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
<c:set var="approvalcomments"><fmt:message key="label.approvalcomments"/></c:set>
var config = [
{ columnId:"permission",
  columnName:''
},
{ columnId:"operatingEntityName",
  columnName:'<fmt:message key="label.operatingentity"/>',
  width:9
},
{ columnId:"hubName"
  <%--,
  columnName:'<fmt:message key="label.hub"/>',
  width:6--%>
},
{ columnId:"inventoryGroupName",
  columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
  width:9
},
{ columnId:"itemId",
  columnName:'<fmt:message key="label.item"/>',
  width:5
},
{ columnId:"itemDesc",
  columnName:'<fmt:message key="label.shortDesc"/>',
  width:15,
  tooltip:"Y"
},
{ columnId:"packaging",
  columnName:'<fmt:message key="label.pkg"/>',
  width:10,
  tooltip:"Y"
},
{ columnId:"type",
  columnName:'<fmt:message key="label.type"/>',
  width:6
},
{ columnId:"receiptId",
  columnName:'<fmt:message key="label.receiptid"/>',
  width:6
},
{ columnId:"lotStatus",
  columnName:'<fmt:message key="label.lotstatus"/>'
},
{ columnId:"displayQuantity",
  columnName:'<fmt:message key="label.qty"/>',
  align:'right',
  width:4
},
{ columnId:"displayFullUnitPrice",
  columnName:'<fmt:message key="label.cost"/>',
  sorting:"int",
  hiddenSortingColumn:"hfullUnitPrice",
  width:10
},
{ columnId:"displayExtendedPrice",
  columnName:'<fmt:message key="label.extcost"/>',
  sorting:"int",
  hiddenSortingColumn:"hextendedPrice",
  width:8
},
{ columnId:"displayTotalPrice",
  columnName:'<fmt:message key="label.totaladjustment"/>',
  sorting:"int",
  width:8
},
{ columnId:"resultingInventory",
  columnName:'<fmt:message key="label.resultinginventory"/>',
  sorting:"int",
  width:6
},
{ columnId:"okDisplay",
  columnName:'<fmt:message key="label.ok"/>',
  type:"hch",
  sorting:"Haashch",
  width:4
},
{ columnId:"reviewerComment",
  columnName:'<tcmis:jsReplace value="${approvalcomments}"/>',
  type:"txt",
  width:12
},
{ columnId:"requestor",
  columnName:'<fmt:message key="label.requestor"/>',
  width:9
},
{ columnId:"requestDate",
  columnName:'<fmt:message key="label.requestdate"/>',
  sorting:"int",
  hiddenSortingColumn:"hrequestDate",
  width:7
},
{ columnId:"hrequestDate",
  sorting:"int"
},
{ columnId:"originalReviewerComment",
  columnName:'<fmt:message key="label.requestorcomments"/>',
  tooltip:"Y",
  width:14
},
{ columnId:"hfullUnitPrice",
  sorting:"int"
},
{ columnId:"hextendedPrice",
  sorting:"int"
},
{ columnId:"requestId"
},
{ columnId:"approve"
},
{ columnId:"quantity"
},
{ columnId:"extendedPrice"
},
{ columnId:"opsEntityId"
},
{ columnId:"inventoryGroup"
},
{ columnId:"companyId"
},
{ columnId:"fullUnitPrice"
},
{ columnId:"totalPrice"
},
{ columnId:"homeCurrencyId"
}
];

with(milonic=new menuname("reject")){
	 top="offset=2"
	 style = contextStyle;
	 margin=3
	 aI("text=<fmt:message key="label.reject"/>;url=javascript:showRejection();");
	}

drawMenus();

	function selectRightclick(rowId,cellInd){
		 selectRow(rowId,cellInd);
	}

	var selectedRowId=null;


	function selectRow(rowId,cellInd){
	   selectedRowId = rowId;
	}

var lineMap3 = new Array();
var lineMap = new Array();
var map = new Array();
var rowSpanCols = [1,2,3,4,5,13,14,15,16];

var gridConfig = {
		divName:'pendingInventoryViewBean', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beangrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not.
		noSmartRender: false,
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
		onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:selectRow,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//		onBeforeSorting:_onBeforeSorting
		singleClickEdit : true
	};

// -->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">
<tcmis:form action="/inventoryadjustmentsresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors }">
     showErrorMessage = false;
    </c:when>
    <c:otherwise>
     showErrorMessage = true;
    </c:otherwise>
   </c:choose>
    //-->
</script>

<tcmis:opsEntityPermission indicator="true" userGroupId="inventoryAdjustment" opsEntityId="${param.opsEntityId}">
<script type="text/javascript">
    <!--
   	 showUpdateLinks = true;
    //-->
</script>
</tcmis:opsEntityPermission>

<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<div class="backGroundContent">

<c:if test="${empty pendingInventoryCollBean}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
    <tr>
    <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
 </table>
</c:if>

<div id="pendingInventoryViewBean" style="width:100%;height:400px;" style="display: none;"></div>

<script type="text/javascript">
<!--

<c:set var="dataCount" value='${0}'/>
<c:set var="dataCount1" value='${0}'/>
<c:if test="${!empty pendingInventoryCollBean}">

<c:set var="prePart" value=""/>
<c:set var="prePos" value="-1"/>
<c:set var="idcount" value="-1"/>
<c:forEach var="bean" items="${pendingInventoryCollBean}" varStatus="status">
<c:set var="curPart" value="${bean.inventoryGroup}===${bean.itemId}"/>
<c:if test="${ curPart != prePart }">
	<c:if test="${! empty prePart}">
		lineMap[${prePos}] = map.length;
	</c:if>
	<c:set var="idcount" value="${idcount+1}"/>
	<c:set var="prePos" value="${status.index}"/>
	map = new Array();
</c:if>
 lineMap3[${status.index}] = ${idcount%2} ;
 map[map.length] = ${status.index} ;
 <c:set var="prePart" value='${curPart}'/>
<c:set var="dataCount1" value='${dataCount1+1}'/>
</c:forEach>
lineMap3.push(${dataCount1}+1);
status1 = ${dataCount1}+1;
<c:forEach var="bean1" items="${sumInventoryCollBean}" >
lineMap3.push(status1);
status1 = status1 +1;
</c:forEach>
<c:if test="${! empty prePart}">
lineMap[${prePos}] = map.length;
</c:if>


<c:set var="showUpdateLink" value='N'/>

<tcmis:opsEntityPermission indicator="true" userGroupId="inventoryAdjustment" opsEntityId="${param.opsEntityId}">
	<c:set var="showUpdateLink" value='Y'/>
</tcmis:opsEntityPermission>

var jsonMainData = new Array();
var jsonMainData = {

rows:[
<c:forEach var="pendingInventoryCollBean" items="${pendingInventoryCollBean}" varStatus="status">

<c:if test="${status.index > 0}">,</c:if>

<fmt:formatDate var="fmtRequestDate" value="${status.current.requestDate}" pattern="${dateFormatPattern}"/>
<c:set var="requestDateSortable" value="${status.current.requestDate.time}"/>

<tcmis:jsReplace var="itemDesc" value="${status.current.itemDesc}"  processMultiLines="true"/>
<tcmis:jsReplace var="packaging" value="${status.current.packaging}"  processMultiLines="true"/>
<tcmis:jsReplace var="requestorComment" value="${status.current.requestorComment}"  processMultiLines="true"/>
<tcmis:jsReplace var="approverComments" value="${status.current.reviewerComment}" processMultiLines="true" />
/*The row ID needs to start with 1 per their design.*/

 <c:choose>
   <c:when test="${(!empty status.current.fullUnitPrice) and  (!empty status.current.quantity )}">
     <c:set var="extPrice" value='${status.current.fullUnitPrice * status.current.quantity}' />
   </c:when>
   <c:otherwise>
     <c:set var="extPrice" value='' />
  </c:otherwise>
 </c:choose>


<fmt:formatNumber var="unitCost" value="${status.current.fullUnitPrice}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>
<fmt:formatNumber var="extPriceFinal" value="${extPrice*(-1)}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>
<fmt:formatNumber var="totalPriceFinal" value="${status.current.totalPrice*(-1)}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>

{ id:${status.index +1},
<c:if test="${status.current.writeOffDiff < 0}">'class':"grid_red",</c:if>
 data:['${showUpdateLink}',
 '${status.current.operatingEntityName}','${status.current.hubName}',
 '<tcmis:jsReplace value="${status.current.inventoryGroupName}"/>','${status.current.itemId}',
 '${itemDesc}','${packaging}',
 <c:choose>
 <c:when test="${status.current.quantity > 0  }">
 '<fmt:message key="label.writeoff"/>',
 </c:when>
 <c:otherwise>
 '<fmt:message key="label.writeon"/>',
 </c:otherwise>
</c:choose>

 '${status.current.receiptId}','${status.current.lotStatus}','${status.current.quantity*(-1)}',
 <c:choose>
   <c:when test="${empty status.current.fullUnitPrice}">
    '',
   </c:when>
   <c:otherwise>
    '${status.current.homeCurrencyId} ${unitCost}',
   </c:otherwise>
  </c:choose>
   <c:choose>
   <c:when test="${empty extPrice}">
    '',
   </c:when>
   <c:otherwise>
    '${status.current.homeCurrencyId} ${extPriceFinal}',
   </c:otherwise>
  </c:choose>
  <c:choose>
   <c:when test="${empty totalPriceFinal}">
    '',
   </c:when>
   <c:otherwise>
    '${status.current.homeCurrencyId} ${totalPriceFinal}',
   </c:otherwise>
  </c:choose>
 '${status.current.writeOffDiff}', 
 '','${approverComments}',
 '<tcmis:jsReplace value="${status.current.requestor}" processMultiLines="false" />','${fmtRequestDate}','${requestDateSortable}',
 '${requestorComment}','${status.current.fullUnitPrice}','${status.current.extendedPrice}','${status.current.requestId}','N',
 '${status.current.quantity}','${extPrice*(-1)}','${status.current.opsEntityId}','${status.current.inventoryGroup}',
 '${status.current.companyId}','${status.current.fullUnitPrice}','${status.current.totalPrice*(-1)}','${status.current.homeCurrencyId}'
]}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
 ,
{ id:${dataCount+1},
 data:['',
 '','','','',
 '','','',
 '','',
  '','<b><fmt:message key="label.inventorygroup"/></b>',
 '<b><fmt:message key="label.total"/></b>',
 '',
 '',
 '','','',
 '','','','','N',
 '','','','',
 '','',''
]}
<c:set var="sumCount" value='${dataCount+2}'/>
<c:forEach var="sumInventoryCollBean" items="${sumInventoryCollBean}" >
<fmt:formatNumber var="sumextendedPrice" value="${sumInventoryCollBean.extendedPrice}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>
,
{ id:${sumCount},
 data:['',
 '','','','',
 '','','',
 '','',
 '','${sumInventoryCollBean.inventoryGroupName}',
 '${sumInventoryCollBean.homeCurrencyId} ${sumextendedPrice*(-1)}',
 '',
 '',
 '','','',
 '','','','','N',
 '','','','',
 '','','',''
]} 
 <c:set var="sumCount" value='${sumCount+1}'/>
 </c:forEach>
 
]};
</c:if>
//-->
</script>
<!-- If the collection is empty say no data found -->

<!-- Search results end --> 

<!-- Hidden element start --> 
 <div id="hiddenElements" style="display: none;">    			
 <input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
      <input name="action" id="action" value="" type="hidden">     
      <input name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}" type="hidden">     
      <input name="hub" id="hub" value="${param.hub}" type="hidden"> 
      <input name="searchField" id="searchField" value="${param.searchField}" type="hidden"> 
      <input name="searchArgument" id="searchArgument" value="${param.searchArgument}" type="hidden"> 
      <input name="searchMode" id="searchMode" value="${param.searchMode}" type="hidden">     
      <input name="inventoryGroup" id="inventoryGroup" value="${param.inventoryGroup}" type="hidden">     
      <input name="minHeight" id="minHeight" type="hidden" value="100"> 
      

 </div>  <!-- Hidden elements end -->
 
 
<!-- Input Dialog Window Begins -->
<div id="lotStatusDialogWin" class="errorMessages" style="display: none;overflow: auto;">
<table width="100%" border="0" cellpadding="2" cellspacing="1">
<tr>
<td align="center"  width="100%"><fmt:message key="label.lotstatus"/>:</td>
</tr>
<tr>
<td align="center"  width="100%">
 <select class="selectBox" name="lotStatusSelectBox" id="lotStatus">
 	<option value=""></option>
 	<option value=""><fmt:message key="label.pleaseselect"/></option>
   <c:forEach var="bean" items="${vvLotStatusBeanCollection}" varStatus="status">
	   	<c:set var="jspLabel" value=""/>
	    <c:if test="${fn:length(status.current.jspLabel) > 0}"><c:set var="jspLabel">${status.current.jspLabel}</c:set></c:if>
		<option value="${bean.lotStatus}"><fmt:message key="${status.current.jspLabel}"/></option>
   </c:forEach>
</select>
</td>
</tr>
<tr>
<td align="center" width="100%">
<input name="cartCommentOk"  id="cartCommentOk"  type="button" value="<fmt:message key="label.continue"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="continueButton();"/>
&nbsp;<input name="cartCommentCancel"  id="cartCommentCancel"  type="button" value="<fmt:message key="label.cancel"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="cancelButton();"/>
</td>
</tr>
</table>
</div><!--  Input Dialog End -->

</div> 
<!-- close of backGroundContent -->
</div>

<!-- close of interface -->
</tcmis:form>
</body>
</html:html>