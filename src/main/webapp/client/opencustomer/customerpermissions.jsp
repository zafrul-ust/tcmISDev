<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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
<%-- Add any other stylesheets you need for the page here --%>

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
<script type="text/javascript" src="/js/client/opencustomer/customerpermissions.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
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
<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hch -checkbox
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
    
<title>
	<fmt:message key="label.customerpermission"/> (${param.fullName})
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
selectedaddress:"<fmt:message key="label.selectedaddress"/>",
errors:"<fmt:message key="label.errors"/>",  
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
nothing:"<fmt:message key="label.nothing"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
    <script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
 var gridConfig = {
	divName:'customerViewBean', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:true    // the fields in grid are defaulted to be submitted or not.
//	onRowSelect:selectRow   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
//	onRightClick:selectRightclick    the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//	onBeforeSorting:_onBeforeSorting
};

var config = [
{
  	columnId:"permission",
  	submit: false
},
{
  	columnId:"_adminRole_Permission",
  	submit: false
},
{ columnId:"customerId",
    columnName:'<fmt:message key="label.customerid"/>',
    tooltip:true,
    width:8,
	submit: false
},
{
    columnId:"customerName",
    columnName:'<fmt:message key="label.customer"/>',
    tooltip:true,
    width:30,
	submit: false
},
{ columnId:"_customerAccess_",
  columnName:'<fmt:message key="label.access"/><br> <input type="checkbox" value="" onClick="return checkAll();" name="ok" id="ok" >',
  type:'hchstatus',  // checkbox:The value is string "true" if checked
  onChange:customerAccessChanged,
  align:'center',
  width:8,
	submit: false
},
{ columnId:"_adminRole_",
  columnName:'<fmt:message key="label.admin"/>',
  type:'hchstatus',  // checkbox:The value is string "true" if checked
  onChange:adminAccessChanged,
  align:'center',
  width:8,
	submit: false
},
{ columnId:"modified"
},
{ columnId:"oldCustomerAccess"
},
{ columnId:"customerAccess"
},
{ columnId:"oldAdminRole"
},
{ columnId:"adminRole"
}
];

// -->
    </script>
</head>

<body bgcolor="#ffffff"  onload="resultOnLoadWithGrid(gridConfig)" onunload="" onresize="resizeFrames()">

<tcmis:form action="/customerpermissions.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!--  update permissions here, if it is read-only page you don't need this -->
<%--<c:set var="pickingPermission" value=''/>
<tcmis:facilityPermission indicator="true" userGroupId="Picking" >--%><%--
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
  <c:set var="pickingPermission" value='Yes'/>
 //-->
 </script>
</tcmis:facilityPermission>--%>

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
</div>
<!-- Error Messages Ends -->

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

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

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


<div id="resultGridDiv" style="display: none;">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
      <div id="mainUpdateLinks"> 
         <span id="updateResultLink" style="display:none">
	         <a href="#" onclick="customerPermissionsUpdate(); return false;"><fmt:message key="label.update"/></a> |
	      </span>
	      <a href="#" onclick="createXSL();"><fmt:message key="label.createExcel"/></a> |
	      <a href="#" onclick="parent.window.close()"><fmt:message key="label.cancel"/></a>
	      &nbsp;
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
 <!--  result page section start -->
<div id="customerViewBean" style="height:400px;display: none;" ></div>
<c:if test="${UserCustomerAdminViewBeanCollection != null}" >

<%--NEW - storing the data to be displayed in the grid in a JSON.--%>
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty UserCustomerAdminViewBeanCollection}" >
    <script type="text/javascript">
    <!--
        var jsonMainData = new Array();
        var jsonMainData = {
        rows:[
        <c:forEach var="customerBean" items="${UserCustomerAdminViewBeanCollection}" varStatus="status">
   
        <c:if test="${status.index > 0}">,</c:if>
        <tcmis:jsReplace var="customerName" value='${customerBean.customerName}' processMultiLines="true"/>
  
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

    <c:set var="accesschecked" value="false"/>
	<c:if test="${status.current.customerAccess eq 'Y'}">
     	<c:set var="accesschecked" value="true"/>
	</c:if>
	
    <c:set var="adminchecked" value="false"/>
	<c:if test="${status.current.adminRole == 'Admin' || status.current.adminRole == 'Grant Admin'}">
     	<c:set var="adminchecked" value="true"/>
	</c:if>
        
        { id:${status.index +1},
         data:[
         '${readonly}','${adminreadonly}',
         '${status.current.customerId}','${customerName}',${accesschecked},${adminchecked},'N',
  		 '${status.current.customerAccess}','${status.current.customerAccess}',
  		 '${status.current.adminRole}','${status.current.adminRole}']}

        <c:set var="dataCount" value='${dataCount+1}'/>
         </c:forEach>
        ]};
    // -->
    </script>
    
    <c:if test="${adminCount > 0}">
	 <script type="text/javascript">
	 <!--
		  showUpdateLinks = true;
	 //-->
	 </script>
	</c:if>
    
</c:if>

<!-- If the collection is empty say no data found -->
<c:if test="${empty UserCustomerAdminViewBeanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td>
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>
<!-- Search results end -->
</c:if>
</div>

 <%-- result count and time --%>
 <div id="footer" class="messageBar"></div>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>

</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="action" id="action" type="hidden" value="update"/>
<input name="personnelId" id="personnelId" type="hidden" value="${personnelId}"/>
<input name="fullName" id="fullName" type="hidden" value="${param.fullName}"/>
<input name="totalLines" id="totalLines" type="hidden" value="${dataCount}"/>
<!--This sets the start time for time elapesed.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">    
<input name="minHeight" id="minHeight" type="hidden" value="100">

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html:html>