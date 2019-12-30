<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>
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

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srndRowSpan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hchstatus -checkbox
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript" src="/js/common/admin/catalogaddapproverpermissionresults.js"></script>


<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
approvalRole:"<fmt:message key="label.approvalRole"/>",
active:"<fmt:message key="label.active"/>",
approver:"<fmt:message key="label.approver"/>",
deleteLine:"<fmt:message key="label.deleteline"/>",
addApprover:"<fmt:message key="label.addapprover"/>",
pleasewait:"<fmt:message key="label.pleasewaitmenu"/>"
};

with ( milonic=new menuname("myRightClickMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=<b>Item Id: $$ ;url=javascript:doNothing();" );
}
drawMenus();

<c:set var="showCompanyColumn" value="N"/>
<c:if test="${param.companySize*1 > 1}">
	<c:set var="showCompanyColumn" value="Y"/>
</c:if>
<c:set var="showFacilityColumn" value="N"/>
<c:if test="${searchedFacilityIsGroup == 'Y'}">
	<c:set var="showFacilityColumn" value="Y"/>
</c:if>

var config = [
{
	columnId:"permission",
  	columnName:''
},
{
    columnId:"companyName",
	 <c:if test="${showCompanyColumn == 'Y'}">
  		columnName:'<fmt:message key="label.company"/>',
  	 </c:if>
	 width:10
},
{
    columnId:"facilityName",
	 <c:if test="${showFacilityColumn == 'Y'}">
  		columnName:'<fmt:message key="label.facility"/>',
  	 </c:if>
	 width:10
},
{
    columnId:"approvalRole",
	 columnName:messagesData.approvalRole,
	 width:30
},
{
    columnId:"active",
	 columnName:messagesData.active,
	 type: 'hchstatus',
	 align: 'center',
	 onChange:activeChanged,
	 width:5
},
{
    columnId:"approverName"  ,
	 columnName:messagesData.approver,
	 width:15
},
{
   columnId:"personnelId",
	columnName:''
},
{
   columnId:"statusChanged",
	columnName:''
},
{
   columnId:"companyId",
	columnName:''
},
{
   columnId:"catalogCompanyId",
	columnName:''
},
{
   columnId:"catalogId",
	columnName:''
},
{
   columnId:"facilityId",
	columnName:''
}
];

var gridConfig = {
		divName:'catalogAddApproverBeanDiv', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'mygrid',     // the grid's name, as in beanGrid.attachEvent...
		config:config,	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not.
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
		onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:selectRow   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//		onBeforeSorting:_onBeforeSorting
	};

// -->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);setTimeout('resultOnLoad()',100);">
<tcmis:form action="/catalogaddapproverpermissionresults.do" onsubmit="return submitFrameOnlyOnce();">

<script language="JavaScript" type="text/javascript">
<!--
<c:set var="parCount" value="0"/>

<c:set var="dataCount" value='${0}'/>
<c:set var="prePar" value=""/>
<c:set var="tmpAllowUpdate" value="N"/>

<c:if test="${chemicalApproverBeanCollection != null}" >
 <c:if test="${!empty chemicalApproverBeanCollection}" >
//  var jsonMainData = new Array();
//  The color stuff is not working at this moment, I will use
//  javascript to fix it.
  var jsonMainData = {
  rows:[
  <c:forEach var="p" items="${chemicalApproverBeanCollection}" varStatus="status">
	   <c:if test="${status.index != 0 }">,</c:if>
		<c:set var="curPar" value="${status.current.approvalRole}"/>
		<c:if test="${!(curPar eq prePar)}">
			<c:set var="parCount" value="${parCount+1}"/>
			<c:if test="${ parCount % 2 == 0 }">
				<c:set var="partClass" value="ev_haas"/>
			</c:if>
			<c:if test="${ parCount % 2 != 0 }">
				<c:set var="partClass" value="odd_haas"/>
			</c:if>
		</c:if>
		<c:set var="tmpActive" value="false"/>
  		<c:if test="${p.active == 'Y' || p.active == 'y'}">
	  		<c:set var="tmpActive" value="true"/>
	   </c:if>

		<c:set var="tmpPermission" value="N"/>
		<c:choose>
			<c:when test="${p.adminType == 'No Change'}">
				<c:set var="tmpPermission" value="N"/>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${p.adminType == 'Customer'}">
						<c:forEach var="tmpBean" items="${catalogAddApproverAdminTypeColl}" varStatus="status2">
		            	<c:if test="${status2.current.companyId == p.companyId && status2.current.facilityId == p.facilityId && status2.current.userGroupId == 'Administrator'}">
		              		<c:set var="tmpPermission" value="Y"/>
							</c:if>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:if test="${p.adminType == 'HAAS'}">
		            	<c:forEach var="tmpBean" items="${catalogAddApproverAdminTypeColl}" varStatus="status2">
								<c:if test="${status2.current.companyId == p.companyId && status2.current.facilityId == p.facilityId && status2.current.userGroupId == 'NewUserGroupIdHereForHaas'}">
									<c:set var="tmpPermission" value="Y"/>
								</c:if>
							</c:forEach>
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>

		<c:if test="${tmpAllowUpdate == 'N'}">
			<c:if test="${tmpPermission == 'Y'}">
		   	<c:set var="tmpAllowUpdate" value="Y"/>
			</c:if>
		</c:if>

		{ id:${status.index +1},"class":"${partClass}",
			  data:[
					  '${tmpPermission}',
					  '<tcmis:jsReplace value="${p.companyName}"/>',
					  '<tcmis:jsReplace value="${p.facilityName}"/>',
					  '<tcmis:jsReplace value="${p.approvalRole}"/>',
					  ${tmpActive},
					  '<tcmis:jsReplace value="${p.approverName}"/>',
					  '${p.personnelId}',
					  'N',
					  '${p.companyId}',
					  '${p.catalogCompanyId}',
					  '${p.catalogId}',
					  '${p.facilityId}'
		]}
		<c:set var="numberOfApprovers" value="${status.current.numberOfApprovers}"/>
		<c:if test="${!(numberOfApprovers eq -1)}">
			<c:set var="dataCount" value='${dataCount+1}'/>
		</c:if>
		<c:set var="prePar" value="${status.current.approvalRole}"/>

     </c:forEach>
  ]};

  </c:if>
 </c:if>

// -->
</script>

<%-- determining rowspan --%>
<c:set var="approverCount" value='0'/>
<c:forEach var="p" items="${chemicalApproverBeanCollection}" varStatus="status">
	<c:set var="numberOfApprovers" value="${status.current.numberOfApprovers}"/>
	<script language="JavaScript" type="text/javascript">
	<!--
	   lineMap[${status.index}] = ${numberOfApprovers} ;
		<c:if test="${!(numberOfApprovers eq -1)}">
	      <c:set var="approverCount" value='${approverCount+1}'/>
			map = new Array();
		</c:if>
	   lineMap3[${status.index}] = ${approverCount} % 2;
		map[map.length] =  ${status.index} ; lineIdMap[${status.index}] = map;
	// -->
	</script>
</c:forEach>


 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
${tcmisError}
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmisError}">
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

<%--NEW - there is no results table anymore--%>
<div id="catalogAddApproverBeanDiv" style="width:100%;%;height:400px;" style="display: none;"></div>


<c:if test="${chemicalApproverBeanCollection != null}" >
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty chemicalApproverBeanCollection}" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
	<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
	<input name="uAction" id="uAction" value="" type="hidden"/>
	<input name="companyId" id="companyId" value="${param.companyId}" type="hidden"/>
	<input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden"/>
	<input name="showInactive" id="showInactive" value="${param.showInactive}" type="hidden"/>
	<input name="allowUpdate" id="allowUpdate" value="${tmpAllowUpdate}" type="hidden"/>
	<input name="personnelChanged" id="personnelChanged" value="true" type="hidden"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>

</body>
</html>
