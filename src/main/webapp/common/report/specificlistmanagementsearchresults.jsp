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

<!-- For Calendar support for column type hcal-->
<!--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/common/report/specificlistmanagement.js"></script>

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

<title>
<fmt:message key="label.listmanagement"/> - ${param.listName}
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
open:"<fmt:message key="label.open"/>",
maxrecordsdisplayed:"<fmt:message key="label.maxrecordsdisplayed"/>",
pleasewaitmenu:"<fmt:message key="label.pleasewaitmenu"/>",
positivenumber:"<fmt:message key="label.positivenumber"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

<c:set var="thresholdColName1" value=""/>
<c:set var="thresholdColName2" value=""/>
<c:set var="thresholdColName3" value=""/>
var thresholdColName1Exists = false;
var thresholdColName2Exists = false;
var thresholdColName3Exists = false;
var maxData = ${param.maxData};

<c:forEach var="colBean" items="${thresholdColNames}" varStatus="colStatus">
<c:if test="${fn:length(colBean.thresholdName) > 0}">
	<c:set var="thresholdColName1" value="${colBean.thresholdName} (${colBean.thresholdUnit})"/>
	thresholdColName1Exists = true;
</c:if>
<c:if test="${fn:length(colBean.thresholdName2) > 0}">
	<c:set var="thresholdColName2" value="${colBean.thresholdName2} (${colBean.thresholdUnit2})"/>
	thresholdColName2Exists = true;
</c:if>
<c:if test="${fn:length(colBean.thresholdName3) > 0}">
	<c:set var="thresholdColName3" value="${colBean.thresholdName3} (${colBean.thresholdUnit3})"/>
	thresholdColName3Exists = true;
</c:if>
</c:forEach>

var thresholdColName1 = '<tcmis:jsReplace value="${thresholdColName1}" processMultiLines="true"/>';
var thresholdColName2 = '<tcmis:jsReplace value="${thresholdColName2}" processMultiLines="true"/>';
var thresholdColName3 = '<tcmis:jsReplace value="${thresholdColName3}" processMultiLines="true"/>';

var gridConfig = {
		divName:'listManagementViewBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beangrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:'false',			 // this page has rowSpan > 1 or not.
		submitDefault:'true',    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		noSmartRender:false // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		};

var config = [
{
  columnId:"permission"
}, 
{columnId:"okPermission"},
{
  columnId:"ok",
  columnName:'<fmt:message key="label.delete"/>',
  width:5,
  type:'hch'
},
{ 
  columnId:"casNumber",
  columnName:'<fmt:message key="label.casnumber"/>',
  tooltip:true,
  width:15
}, 
{ 
  columnId:"rptChemical",
  columnName:'<fmt:message key="label.chemicalname"/>',
  width:31,
  tooltip:true,
  onChange:rowChanged,
  size:110,
  type:"hed"
},
{ 
  columnId:"listId"
},
{ 
  columnId:"companyId"
},
{
  columnId:"isAddLine"
},
{
  columnId:"updated"
}
<c:if test="${thresholdColName1 != ''}">
,
{
	  columnId:"threshold",
	  columnName:'<tcmis:jsReplace value="${thresholdColName1}" processMultiLines="true"/>',
	  width:15,
	  tooltip:true,
	  onChange:rowChanged,
	  size:30,
	  type:"hed"
}
</c:if>

<c:if test="${thresholdColName2 != ''}">
,
{
	  columnId:"threshold2",
	  columnName:'<tcmis:jsReplace value="${thresholdColName2}" processMultiLines="true"/>',
	  width:15,
	  tooltip:true,
	  onChange:rowChanged,
	  size:30,
	  type:"hed"
}
</c:if>
<c:if test="${thresholdColName3 != ''}">
,
{
	  columnId:"threshold3",
	  columnName:'<tcmis:jsReplace value="${thresholdColName3}" processMultiLines="true"/>',
	  width:15,
	  tooltip:true,
	  onChange:rowChanged,
	  size:30,
	  type:"hed"
}
</c:if>
];

//-->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnLoad();" onresize="resizeFrames()"  onunload="isClose()">
<tcmis:form action="/specificlistmanagementsearchresults.do" onsubmit="return submitFrameOnlyOnce();">

<c:set var="readonly" value='N'/>
<tcmis:permission indicator="true"  userGroupId="editCustomerList">
 <script type="text/javascript">
 <!--
showUpdateLinks = false;
var hasPermission;
 <c:choose>
	<c:when test="${param.permission == 'Y'}">
        <c:set var="readonly" value="Y"/>
        hasPermission = true;
    </c:when>
	<c:otherwise>
        <c:set var="readonly" value="N"/>
        hasPermission = false;
    </c:otherwise>
</c:choose>
 //-->
 </script>
</tcmis:permission>
   
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
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Please keep the <span></span> on the same line this will avoid extra virtical space.
      --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
           <span id="addResultLinkSolo" style="display: none">
    	  		<a href="#" onclick="addListChemical();return false;"><fmt:message key="label.add"/></a>
    	   </span>
          <span id="updateResultLink" style="display: none">
          <a href="#" onclick="submitUpdate();return false;"><fmt:message key="label.update"/></a>&nbsp;|&nbsp;
           </span>
          <span id="addResultLink" style="display: none">
    	  <a href="#" onclick="addListChemical();return false;"><fmt:message key="label.add"/></a>&nbsp;|&nbsp;
    	   </span>
    	  <span id="excelResultLink" style="display: none">
    	   <a href="#" onclick="generateExcel();return false;"><fmt:message key="label.savetoexcel"/></a><br/>
    	   </span>
    	   <fmt:message key="label.listname"/> : ${param.listName}
      </div>
     </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
	</div> <%-- boxhead Ends --%>

    <div class="dataContent">

<div id="listManagementViewBean" style="width:100%;height:500px;" style="display: none;"></div>
<c:set var="dataCount" value='${0}'/>
<c:if test="${listChemicalColl != null}">
<script type="text/javascript">
<!--
<c:if test="${!empty listChemicalColl}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${listChemicalColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
{id:${status.index +1},
 data:[
  '${readonly}',
  '${readonly}',
  '',
  '${bean.casNumber}',
  '<tcmis:jsReplace value="${bean.rptChemical}" processMultiLines="true"/>',
  '${bean.listId}',
  '${bean.companyId}',
  false,
  ''
  <c:if test="${thresholdColName1 != ''}">
  ,'${bean.threshold}',
  </c:if>
  <c:if test="${thresholdColName2 != ''}">
  '${bean.threshold2}',
  </c:if>
  <c:if test="${thresholdColName3 != ''}">
  '${bean.threshold3}'
  </c:if>
  ]}
  
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>

 //-->
</script>
</c:if>
        
   <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>
   </div>

</div>
</div>

<div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
    
  </td>
</tr>
</table>
<!-- results end -->

<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount + 1}"/>" type="hidden"/>
<input name="permission" id="permission" value="${param.permission}" type="hidden"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
<input type="hidden" name="uAction" id="uAction" value="${uAction}"/>
<input name="listId" id="listId" type="hidden" value="${param.listId}"/>
<input name="listName" id="listName" type="hidden" value="${param.listName}"/>
<input name="listDescription" id="listDescription" type="hidden" value="${param.listDescription}"/>
<input name="reference" id="reference" type="hidden" value="${param.reference}"/>
<input type="hidden" name="companyId" id="companyId" value="${personnelBean.companyId}"/>
<!--This sets the start time for time elapesed from the action.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
<!--This sets the end time for time elapesed from the action.-->
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">
<input type="hidden" name="ownerCompanyId" id="ownerCompanyId" value="${param.ownerCompanyId}"/>
</div>

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</tcmis:form>
</body>
</html:html>