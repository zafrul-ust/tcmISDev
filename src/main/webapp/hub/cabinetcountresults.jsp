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
<script type="text/javascript" src="/js/hub/cabinetcount.js"></script>

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
<fmt:message key="label.cabinetcount"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
quantity:"<fmt:message key="label.quantity"/>",
updateSuccess:"<fmt:message key="msg.updatesuccess"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

var config = [
{
  	columnId:"permission"
 },
{
  columnId:"cabinetName",
  columnName:'<fmt:message key="label.cabinet"/>',
  width:15
},
{ columnId:"bin",
  columnName:'<fmt:message key="label.bin"/>',
  tooltip:true,
  width:15
},
{
  columnId:"itemId",
  columnName:'<fmt:message key="label.item"/>',
  width:6
},
{
  columnId:"cpigStatus",
  columnName:'<fmt:message key="label.status"/>',
  width:6
},
{
  columnId:"packaging",
  columnName:'<fmt:message key="label.packaging"/>',
  tooltip:true,
  width:20
},
{
  columnId:"description",
  columnName:'<fmt:message key="label.description"/>',
  tooltip:true,
  width:22
},
{
  columnId:"countQuantity",
  columnName:'<fmt:message key="label.quantity"/>',
  type:'hed',
  width:5
},
{ 
  columnId:"companyId"
},
{ 
  columnId:"binId"
},
{ 
  columnId:"cabinetId"
},
{
  columnId:"status"
}
,
{
  columnId:"cabinetRowspan"
}
];

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myResultBodyOnload()">

<tcmis:form action="/cabinetcountresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<tcmis:facilityPermission indicator="true" userGroupId="CabinetMgmt" facilityId="${param.hubName}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</tcmis:facilityPermission>
<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
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

<script type="text/javascript">
<!--
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

<div id="cabinetBinItemBean" style="width:100%;height:500px;" style="display: none;"></div>
<c:set var="dataCount" value='${0}'/>
<c:if test="${cabinetsCollection != null}">
<script type="text/javascript">
<!--
<c:if test="${!empty cabinetsCollection}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="cabinetBinItemViewBean" items="${cabinetsCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<c:if test="${empty status.current.updatedStatus}">
<c:set var="quantity" value=""/>
</c:if>
<c:if test="${!empty status.current.updatedStatus}">
<c:set var="quantity" value="${status.current.quantity}"/>
</c:if>

<c:set var="cpigStatus" value='' />
	<c:if test="${cabinetBinItemViewBean.cpigStatus ==  'A' }"><c:set var="cpigStatus"><fmt:message key="label.active"/></c:set></c:if>
	<c:if test="${cabinetBinItemViewBean.cpigStatus ==  'D' }"><c:set var="cpigStatus"><fmt:message key="label.drawdown"/></c:set></c:if>
	<c:if test="${cabinetBinItemViewBean.cpigStatus ==  'O' }"><c:set var="cpigStatus"><fmt:message key="label.obsolete"/></c:set></c:if>
	
 
{id:${status.index +1},
<c:if test="${status.current.cpigStatus eq 'O'}">'class':"grid_black",</c:if>
 data:['Y',
  '<tcmis:jsReplace value="${cabinetBinItemViewBean.cabinetName}" processMultiLines="false"/>',
  '<tcmis:jsReplace value="${cabinetBinItemViewBean.binName}" processMultiLines="true"/>',
  '<tcmis:jsReplace value="${cabinetBinItemViewBean.itemId}" processMultiLines="false"/>','${cpigStatus}',
  '<tcmis:jsReplace value="${cabinetBinItemViewBean.packaging}" processMultiLines="true"/>',
  '<tcmis:jsReplace value="${cabinetBinItemViewBean.description}" processMultiLines="true"/>',
  '${quantity}',
  '<tcmis:jsReplace value="${cabinetBinItemViewBean.companyId}" processMultiLines="false"/>',
  '<tcmis:jsReplace value="${cabinetBinItemViewBean.binId}" processMultiLines="false"/>',
  '<tcmis:jsReplace value="${cabinetBinItemViewBean.cabinetId}" processMultiLines="false"/>',
  '${cabinetBinItemViewBean.updatedStatus}',
  '${cabinetBinItemViewBean.cabinetRowspan}'
  ]}
  
   
  <c:set var="dataCount" value='${dataCount+1}'/>
  
  
 </c:forEach>
]};
</c:if>
 //-->
</script>
</c:if>
   <!-- If the collection is empty say no data found -->

   <c:if test="${empty cabinetsCollection}" >

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
<input name="action" id="action" type="hidden" value="${param.action}">

<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
<!-- Hidden elements for re-search-after-update purpose -->	
	<input name="hubName" id="hubName" type="hidden" value="${param.hubName}"/>
	<input name="cabinetIdArray" id="cabinetIdArray" type="hidden" value="${param.cabinetIdArray}"/>
	<input name="facilityId" id="facilityId" type="hidden" value="${param.facilityId}"/>
	<input name="application" id="application" type="hidden" value="${param.application}"/>
	<input name="branchPlant" id="branchPlant" type="hidden" value="${param.branchPlant}"/>
	<input name="companyId" id="companyId" type="hidden" value="${param.companyId}"/>
	<input name="searchField" id="searchField" type="hidden" value="${param.searchField}"/>
	<input name="searchMode" id="searchMode" type="hidden" value="${param.searchMode}"/>
	<input name="searchArgument" id="searchArgument" type="hidden" value="${param.searchArgument}"/>
	<input name="minHeight" id="minHeight" type="hidden" value="100"/>


<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>