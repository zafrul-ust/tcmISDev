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
<tcmis:gridFontSizeCss/>
<!-- CSS for YUI -->
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
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
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script src="/js/hub/tabletpickableunitviewresults.js" language="JavaScript"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<title>
<fmt:message key="label.picklistno"/>:<c:out value="${param.picklistId}"/>
</title>

<script language="JavaScript" type="text/javascript">
//add all the javascript messages here, this for internationalization of client side javascript messages
var resizeGridWithWindow = true;
var messagesData = new Array();
messagesData={     alert:"<fmt:message key="label.alert"/>",
                     and:"<fmt:message key="label.and"/>",
          submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
          daySpanInteger:"<fmt:message key="error.dayspan.integer"/>",
     pleasemakeselection:"<fmt:message key="label.pleasemakeselection"/>",
           itemMrInteger:"<fmt:message key="error.itemmr.integer"/>",
            recordFound:"<fmt:message key="label.recordFound"/>",
    		searchDuration:"<fmt:message key="label.searchDuration"/>",
    		minutes:"<fmt:message key="label.minutes"/>",
    		seconds:"<fmt:message key="label.seconds"/>",
            total:"<fmt:message key="label.total"/>"};

var windowCloseOnEsc = true;

var gridConfig = {
		divName:'picklistData',
		beanData:'jsonData',
		beanGrid:'beanGrid',
		config:'columnConfig',
		rowSpan:false,
		noSmartRender: true,
		onRowSelect: rowSelected,
		submitDefault:false
};


var pickingGroupId = [
	{text:'<fmt:message key="label.pleaseselect"/>',value:''}
];

<fmt:message var="pleaseselect" key="label.pleaseselect"/>
var selectAll = "<select onchange=\"doSelectAll(this.value)\" class=\"selectBox\" name=\"selectAll\" id=\"selectAll\">"
		+ "<option value=\"\">${pleaseselect}</option>";
		
<c:forEach var="pickingGroup" items="${pickingGroupColl}" varStatus="pgStatus">
pickingGroupId[<c:out value="${pgStatus.count}"/>] = {text:'<tcmis:jsReplace value="${pickingGroup.pickingGroupName}" processMultiLines="false"/>',value:'${pickingGroup.pickingGroupId}'};
selectAll = selectAll + "<option value=\"${pickingGroup.pickingGroupId}\">${pickingGroup.pickingGroupName}</option>";
</c:forEach>

selectAll = selectAll + "</select>"

var columnConfig = [
{ columnId:"permission" },
{ columnId:"update", columnName:'<fmt:message key="label.update"/><br><input type="checkbox" value="" onclick="return checkAll();" name="chkAllOk" id="chkAllOk">', type:"hchstatus", align:"center", width:4, submit:true},
{ columnId:"pickingGroupId", columnName:'<fmt:message key="label.pickinggroup"/><br>'+selectAll, type:"hcoro", align:"center", width:12, submit:true, sorting:'none'},
{ columnId:"pickingUnitId", columnName:'<fmt:message key="label.pickingunit"/>', width:6, tooltip:"Y",submit:true},
{ columnId:"shipto", columnName:'<fmt:message key="label.shipto"/>', tooltip:"Y", width:10 },
{ columnId:"room", columnName:'<fmt:message key="label.room"/>', tooltip:"Y", width:10 },
{ columnId:"bin", columnName:'<fmt:message key="label.bin"/>', tooltip:"Y", width:12},
{ columnId:"prNumber" },
{ columnId:"lineItem" },
{ columnId:"prNumberLineItem", columnName:'<fmt:message key="label.mrline"/>', width:8},
{ columnId:"pickingState" },
{ columnId:"pickingStateDesc",columnName:'<fmt:message key="label.pickingstate"/>',width:8}
];
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">

<tcmis:form action="/tabletpickableunitviewresults.do" onsubmit="return submitFrameOnlyOnce();">

<%--**TODO** check for permissions--%>
<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<c:if test="${! empty pickableUnitColl}" >
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</c:if>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
</div>

<script type="text/javascript">
<!--
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
<div id="picklistData" style="width:100%;height:400px;" style="display:none;"></div>
<c:if test="${pickableUnitColl != null}" >
<!-- Search results start -->
<script type="text/javascript">

	<c:if test="${not empty pickableUnitColl}">
		var jsonMainData = new Array();
		jsonData = {
				rows:[
				<c:forEach var="bean" items="${pickableUnitColl}" varStatus="status">
				{ id:${status.count},
					data:[
					     "Y",
					     "",
					     <tcmis:jsReplace value="${bean.pickingGroupId}" processMultiLines="false"/>,
					     "${bean.pickingUnitId}",
					     '<tcmis:jsReplace value="${bean.shipToLocationDesc}" processMultiLines="false"/>',
					     '<tcmis:jsReplace value="${bean.room}" processMultiLines="false"/>',
					     '<tcmis:jsReplace value="${bean.bin}" processMultiLines="false"/>',
					     "${bean.prNumber}",
					     "${bean.lineItem}",
					     "${bean.prNumber} - ${bean.lineItem}",
					     '<tcmis:jsReplace value="${bean.pickingState}" processMultiLines="false"/>',
					     '<tcmis:jsReplace value="${bean.pickingStateDesc}" processMultiLines="false"/>'
					      ]
				}<c:if test="${!status.last}">,</c:if>
				</c:forEach>
				]
		};
	</c:if>
</script>
    <!-- If the collection is empty say no data found -->
    <c:if test="${empty pickableUnitColl}">

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
<input name="action" id="action" type="hidden" value="">
<input name="totalLines" id="totalLines" value="<c:out value="${fn:length(pickableUnitColl)}"/>" type="hidden">

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<%--<tcmis:saveRequestParameter/>--%>
<input name="picklistId" id="picklistId" type="hidden" value="<tcmis:jsReplace value="${param.picklistId}"/>">
<input type="hidden" name="inventoryGroup" id="inventoryGroup" value="<tcmis:jsReplace value="${param.inventoryGroup}"/>"/>
<input type="hidden" name="opsEntityId" id="opsEntityId" value="<tcmis:jsReplace value="${param.opsEntityId}"/>"/>
<input type="hidden" name="hub" id="hub" value="<tcmis:jsReplace value="${param.hub}"/>"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>