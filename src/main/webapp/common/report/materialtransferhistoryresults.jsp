<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/report/materialtransferhistoryresults.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
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
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
    <fmt:message key="materialTransferHistory"/> 
</title>
<script language="JavaScript" type="text/javascript">
<!--

with(milonic=new menuname("viewMr")){
    top="offset=2"
    style = contextStyle;
    margin=3
    aI("text=<fmt:message key="label.viewMr"/>;url=javascript:viewMr();");
}

with(milonic=new menuname("viewEmpty")){
    top="offset=2"
    style = contextStyle;
    margin=3
    aI("text=");
}

drawMenus();

//add all the javascript messages here, this for internationalization of client side javascript messages

var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
viewMr:"<fmt:message key="label.viewMr"/>",
materialrequest:"<fmt:message key="label.materialrequest"/>",    
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

var config = [
{ columnId:"transferDate",
  columnName:'<fmt:message key="label.transferreddate"/>',
  hiddenSortingColumn:"hTransferDate",
  sorting:"int",
  width:7
},
{ columnId:"hTransferDate",
  sorting:"int"
},
{ columnId:"toMrline",
  columnName:'<fmt:message key="label.to"/>',
  attachHeader:'<fmt:message key="label.mrline"/>',
  align:"left",
  width:7
},
{ columnId:"toApplicationDesc",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.workarea"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"catalogDesc",
  columnName:'<fmt:message key="label.catalog"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"facPartNo",
  columnName:'<fmt:message key="label.partno"/>'
},
{ columnId:"partDescription",
  columnName:'<fmt:message key="label.partdesc"/>',
  tooltip:"Y",
  width:15
},
{ columnId:"itemId",
  columnName:'<fmt:message key="label.item"/>',
  width:6
},
{ columnId:"itemDesc",
  columnName:'<fmt:message key="label.itemdesc"/>',
  tooltip:"Y",
  width:15
},
{ columnId:"quantity",
  columnName:'<fmt:message key="label.qty"/>',
  sorting:"int",
  width:5
},
{
  	columnId:"fromMrline",
  	columnName:'<fmt:message key="label.from"/>',
  	attachHeader:'<fmt:message key="label.mrline"/>',
  	width:6
},
{
  	columnId:"fromApplicationDesc",
  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.workarea"/>',
    tooltip:"Y",
    width:10
},
{ columnId:"toPrNumber"
},
{ columnId:"toLineItem"
}
];	

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/materialtransferhistoryresults.do" onsubmit="return submitFrameOnlyOnce();">
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

<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="materialTransferHistoryBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:if test="${materialTransferHistoryColl != null}">
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty materialTransferHistoryColl}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="materialTransferHistoryBean" items="${materialTransferHistoryColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:[
     '<fmt:formatDate value="${status.current.transferDate}" pattern="${dateFormatPattern}"/>',
     '${status.current.transferDate.time}',
     '${status.current.toPrNumber}-${status.current.toLineItem}',
     '${status.current.toApplicationDesc}','${status.current.catalogDesc}','${status.current.facPartNo}',
     '<tcmis:jsReplace value="${status.current.partDescription}" processMultiLines="true" />',
     '${status.current.itemId}','<tcmis:jsReplace value="${status.current.itemDesc}" processMultiLines="true"/>',
     '${status.current.quantity}',
     '${status.current.fromPrNumber}-${status.current.fromLineItem}',
     '${status.current.fromApplicationDesc}',
     '${status.current.toPrNumber}','${status.current.toLineItem}'
 ]}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${empty materialTransferHistoryColl}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
    <tr>
    <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr> 
 </table> 
</c:if> 
 <!-- Search results end --> 
</c:if>


    <!-- Hidden element start --> 
    <div id="hiddenElements" style="display: none;">    			
		<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
        <input name="uAction" id="uAction" value="" type="hidden"> 
        <input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden">  
        <input name="fromApplicationId" id="fromApplicationId" value="${param.fromApplicationId}" type="hidden"> 
        <input name="toApplicationId" id="toApplicationId" value="${param.toApplicationId}" type="hidden">       
        <input name="transferredFromDate" id="transferredFromDate" value="${param.transferredFromDate}" type="hidden">    
        <input name="transferredToDate" id="transferredToDate" value="${param.transferredToDate}" type="hidden">    
        <input name="searchBy" id="searchBy" value="${param.searchBy}" type="hidden"> 
        <input name="searchType" id="searchType" value="${param.searchType}" type="hidden">      
        <input name="searchText" id="searchText" value="${param.searchText}" type="hidden">           
        <input name="minHeight" id="minHeight" type="hidden" value="100"> 
		
    </div>
    <!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>

</body>
</html:html>