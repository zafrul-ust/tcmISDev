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
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/cabinet/clientcabinetdefinitionresults.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<%--This is to allow copy and paste. works only in IE--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_selection.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js"></script>
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="cabinetdefinition.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

var config = [
{ columnId:"useApplication",
  columnName:'<fmt:message key="label.workarea"/>',
  width:12
},
{ columnId:"catalogDesc",
  columnName:'<fmt:message key="label.catalog"/>'
},
{ columnId:"catPartNo",
  columnName:'<fmt:message key="label.partnumber"/>',
  tooltip:"Y"
},
{ columnId:"rpslrq",
  columnName:'<fmt:message key="label.rpslrq"/>',
  align:'center'
},
{ columnId:"kanBanReorderQuantity",
  columnName:'<fmt:message key="label.kanbanreorderqty"/>',
  width:4,
  align:'right'
},
{ columnId:"leadTimeInDays",
  columnName:'<fmt:message key="label.leadtimeindays"/>',
  align:'right'
},
{ columnId:"itemId",
  columnName:'<fmt:message key="label.item"/>',
  align:'right' 	
},
{ columnId:"itemDesc",
  columnName:'<fmt:message key="label.description"/>',
  tooltip:"Y",
  width:30
},
{
	columnId:"materialIdString",
	columnName:'<fmt:message key="label.msdsnumber"/>'
}
];

var map = null;
var lineMap = new Array();
var lineMap2 = new Array();
var lineMap3 = new Array();

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnload()">

<tcmis:form action="/clientcabinetdefinitionresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null}">
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

<div id="cabinetDefinitionDiv" style="width:100%;height:400px;" style="display: none;"></div>
<c:if test="${cabinetDefinitionColl != null}" >

<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty cabinetDefinitionColl}" >
	<script type="text/javascript">
	<!--
		<c:set var="dataCount" value='${0}'/>
		var jsonMainData = new Array();
		var jsonMainData = {
		rows:[
		<c:forEach var="tmpBean" items="${cabinetDefinitionColl}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>

			<tcmis:jsReplace var="materialDesc" value="${status.current.materialDesc}" processMultiLines="true" />
			<tcmis:jsReplace var="tmpWorkArea" value="${status.current.cabinetName}" processMultiLines="true" />
			 /*The row ID needs to start with 1 per their design.*/
			{ id:${status.index +1},
			 data:['${tmpWorkArea}',
			 '${status.current.catalogDesc}','${status.current.catPartNo}',
			 '${status.current.reorderPoint} / ${status.current.stockingLevel} / ${status.current.reorderQuantity}',
			 '${status.current.kanbanReorderQuantity}',	  
			 '${status.current.leadTimeDays}',
			 '${status.current.itemId}','${materialDesc}',
			 '${tmpBean.msdsString}'
			 ]
			}
			 <c:set var="dataCount" value='${dataCount+1}'/>
		 </c:forEach>
		]};
	//-->
	</script>
 </c:if>
	
	<!-- If the collection is empty say no data found -->
   <c:if test="${empty cabinetDefinitionColl}" >
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
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden">

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<tcmis:saveRequestParameter/>

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>

<c:set var="preFirstLevel" value=""/>
<c:set var="parCount" value="1"/>
<c:forEach var="tmpBean" items="${cabinetDefinitionColl}" varStatus="status">
	<c:set var="curFirstLevel">${status.current.cabinetId}${status.current.binId}${status.current.catPartNo}</c:set>
	<c:set var="curSecondLevel">secondLevel${status.current.cabinetId}${status.current.binId}${status.current.catPartNo}${status.current.itemId}</c:set>

	<script language="JavaScript" type="text/javascript">
	<!--
		<c:if test="${!(curFirstLevel eq preFirstLevel)}">
			<c:set var="parCount" value="${parCount+1}"/>
			<c:set var="preSecondLevel" value=""/>
			lineMap[${status.index}] = ${rowCountFirstLevel[curFirstLevel]} ;
			map = new Array();
		</c:if>
		<c:if test="${ !( curSecondLevel eq preSecondLevel)}">
			<c:set var="componentSize" value='${rowCountSecondLevel[curFirstLevel][curSecondLevel]}' />
			lineMap2[${status.index}] = ${componentSize} ;
		</c:if>
		lineMap3[${status.index}] = ${parCount}%2 ;
		map[map.length] =  ${status.index} ;
	// -->
	</script>

	<c:set var="preFirstLevel" value="${curFirstLevel}"/>
	<c:set var="preSecondLevel" value="${curSecondLevel}"/>
</c:forEach>

</body>
</html:html>