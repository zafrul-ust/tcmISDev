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
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
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
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/admin/pagePermission.js"></script>

<title>
<fmt:message key="label.pagePermissionsTitle"/></title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

var config = [
{
  columnId:"permission"
},
{
  columnId:"pageAccessPermission"
},
{
  columnId:"adminRolePermission"
},
{ columnId:"companyName",
  columnName:'<fmt:message key="label.company"/>',
  width:12,
  tooltip:"Y"
},
{ columnId:"pageName",
  columnName:'<fmt:message key="label.pages"/>',
  width:18,
  tooltip:"Y"
},
{ columnId:"pageAccess",
  columnName:'<fmt:message key="label.access"/><br><input type="checkbox" value="" onClick="return checkAll(\'pageAccess\');" name="checkAllAccess" id="checkAllAccess"/>',
  width:8,
  align:'center',
  type:'hchstatus'
},
{ columnId:"adminRole",
  columnName:'<fmt:message key="label.admin"/>',
  width:7,
  align:'center',
  type:'hchstatus'
},
{
  columnId:"accessChecked"
},
{
  columnId:"adminChecked"
},
{
  columnId:"pageId"
},
{
  columnId:"companyId"
},
{
  columnId:"personnelId"
},
{
  columnId:"userAccess"
},
{
  columnId:"oldPageAccess"
},
{
  columnId:"oldAdminRole"
},
{
  columnId:"modified"
}
];

//-->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/pagepermissionresults.do" onsubmit="return submitFrameOnlyOnce();">
  
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

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="UserPageAdminViewBean" style="width:100%;height:500px;" style="display: none;"></div>
<c:set var="dataCount" value='${0}'/>
<c:if test="${UserPageAdminViewCollection != null}">
<script type="text/javascript">
<!--
<c:if test="${!empty UserPageAdminViewCollection}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${UserPageAdminViewCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<c:set var="readonly" value="N"/>
<c:set var="adminreadonly" value="N"/>
	<c:if test="${status.current.userAccess == 'Admin'}">
	 	<c:set var="readonly" value="Y"/>
	 	<c:set var="adminCount" value='${1}'/>
	</c:if>
	<c:if test="${status.current.userAccess == 'Grant Admin'}">
	 	<c:set var="adminreadonly" value="Y"/>
	 	<c:set var="readonly" value="Y"/>
	 	<c:set var="adminCount" value='${1}'/>
	</c:if>
	
	
    <c:set var="accesschecked" value="N"/>
	<c:if test="${status.current.pageAccess == 'Y'}">
     	<c:set var="accesschecked" value="Y"/>
	</c:if>
	
    <c:set var="adminchecked" value="N"/>
	<c:if test="${status.current.adminRole == 'Admin' || status.current.adminRole == 'Grant Admin'}">
     	<c:set var="adminchecked" value="Y"/>
	</c:if>
	<c:if test="${status.current.unrestrictedAccess == 'Y'}">
     	<c:set var="accesschecked" value="Y"/>
	    <c:set var="readonly" value="N"/>
    	<c:set var="adminreadonly" value="N"/>
   	</c:if>

	
{ id:${status.index +1},
 data:['Y','${readonly}','${adminreadonly}',
	 '<tcmis:jsReplace value="${status.current.companyName}" />',
	 '<fmt:message key="${bean.pageId}"/>',
	 '${status.current.pageAccess}','${status.current.adminRole}',
	 '${accesschecked}','${adminchecked}','${status.current.pageId}',
	 '${status.current.companyId}','${status.current.personnelId}','${status.current.userAccess}',
	 '${status.current.pageAccess}','${status.current.adminRole}',''
	  ]}

  <c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>
]};
</c:if>
 //-->
</script>
</c:if>
<!-- If the collection is empty say no data found -->
<c:if test="${empty UserPageAdminViewCollection}" >
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
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">
<input type="hidden" name="personnelId" id="personnelId" value="${param.personnelId}"/>
<input name="minHeight" id="minHeight" type="hidden" value="100">
<input type="hidden" name="adminCount" id="adminCount" value="${adminCount}"/>
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input name="companyId" id="companyId" type="hidden" value="${param.companyId}"/>
</div>

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

<c:if test="${adminCount > 0}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>

</tcmis:form>
</body>
</html:html>