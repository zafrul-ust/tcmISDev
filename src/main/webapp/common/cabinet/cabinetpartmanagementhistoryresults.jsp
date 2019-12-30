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
<fmt:message key="catalogspec.label.viewparthistory"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
pleasewaitmenu:"<fmt:message key="label.pleasewaitmenu"/>"};

windowCloseOnEsc = true;

var gridConfig = {
		divName:'cabinetManagementBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beangrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:'false',			 // this page has rowSpan > 1 or not.
		submitDefault:'false',    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:false,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		noSmartRender:false // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		};

var config = [
{
  columnId:"permission"
},
{
  columnId:"insertedBy",
  columnName:'<fmt:message key="label.updatedby"/>',
  width:11
},
{
  columnId:"insertedOn",
  columnName:'<fmt:message key="label.updateddate"/>',
  width:12,
  hiddenSortingColumn:'hiddenInsertedOn', sorting:'int' },
{
  columnId:"hiddenInsertedOn",
  sorting:'int'
},
{
  columnId:"oldBinName",
  columnName:'<fmt:message key="label.old"/>',
  attachHeader:'<fmt:message key="label.binname"/>',
  width:10
},
{
  columnId:"oldStatus",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.status"/>',
  width:5
},
{
  columnId:"oldCountType",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.counttype"/>',
  width:8
},
{
  columnId: "oldTierIITemperature",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.tieriistoragetemperature"/>',
  width:8
},
{
  columnId:"oldOwnershipName",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.ownership"/>',
  width:10
},
{
  columnId:"oldDropShipOverride",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.dropship"/>',
  width:10
},
{
  columnId:"newBinName",
  columnName:'<fmt:message key="label.new"/>',
  attachHeader:'<fmt:message key="label.binname"/>',
  width:10
},
{
  columnId:"newStatus",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.status"/>',
  width:5
},
{
  columnId:"newCountType",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.counttype"/>',
  width:8
},
{
  columnId: "newTierIITemperature",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.tieriistoragetemperature"/>',
  width:10
},
{
  columnId:"newOwnershipName",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.ownership"/>',
  width:10
},
{
  columnId:"newDropShipOverride",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.dropship"/>',
  width:10
}

];

//-->
</script>
</head>

<body bgcolor="#ffffff"  onload="var now = new Date();$('endSearchTime').value=now.getTime();popupOnLoadWithGrid(gridConfig);" onresize="resizeFrames()">
<tcmis:form action="/clientcabinetmanagementresults.do" onsubmit="return submitFrameOnlyOnce();">

 <script type="text/javascript">
 <!--
showUpdateLinks = false;
 //-->
 </script>

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
      </div>
      <fmt:message key="label.partnumber"/>: ${param.catPartNo}
     </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
	</div> <%-- boxhead Ends --%>

<div class="dataContent">

<div id="cabinetManagementBean" style="width:100%;height:500px;" style="display: none;"></div>
<c:set var="dataCount" value='${0}'/>
<c:if test="${partHistoryResults != null}">
    <script type="text/javascript">
    <!--
    <c:if test="${!empty partHistoryResults}">
        var jsonMainData = new Array();
        var jsonMainData = {
        rows:[
        <c:forEach var="CabinetPartLevelLogViewBean" items="${partHistoryResults}" varStatus="status">
            <c:set var="oldStatus"><fmt:message key="label.inactive"/></c:set>
            <c:set var="newStatus"><fmt:message key="label.inactive"/></c:set>
            <c:if test="${CabinetPartLevelLogViewBean.oldStatus == 'A'}">
                <c:set var="oldStatus"><fmt:message key="label.active"/></c:set>
            </c:if>
            <c:if test="${CabinetPartLevelLogViewBean.newStatus == 'A'}">
                <c:set var="newStatus"><fmt:message key="label.active"/></c:set>
            </c:if>
            <c:set var="oldCountType" value="${CabinetPartLevelLogViewBean.oldCountType}"/>
            <c:if test="${CabinetPartLevelLogViewBean.oldCountType == 'ITEM_ID'}">
                <c:set var="oldCountType"><fmt:message key="label.itemid"/></c:set>
            </c:if>
            <c:if test="${CabinetPartLevelLogViewBean.oldCountType == 'RECEIPT_ID'}">
                <c:set var="oldCountType"><fmt:message key="label.receiptid"/></c:set>
            </c:if>
            <c:if test="${CabinetPartLevelLogViewBean.oldCountType == 'KanBan'}">
                <c:set var="oldCountType"><fmt:message key="label.kanban"/></c:set>
            </c:if>
            <c:if test="${CabinetPartLevelLogViewBean.oldCountType == 'NotCounted'}">
                <c:set var="oldCountType"><fmt:message key="label.notcounted"/></c:set>
            </c:if>
            <c:set var="newCountType" value="${CabinetPartLevelLogViewBean.newCountType}"/>
            <c:if test="${CabinetPartLevelLogViewBean.newCountType == 'ITEM_ID'}">
                <c:set var="newCountType"><fmt:message key="label.itemid"/></c:set>
            </c:if>
            <c:if test="${CabinetPartLevelLogViewBean.newCountType == 'RECEIPT_ID'}">
                <c:set var="newCountType"><fmt:message key="label.receiptid"/></c:set>
            </c:if>
            <c:if test="${CabinetPartLevelLogViewBean.newCountType == 'KanBan'}">
                <c:set var="newCountType"><fmt:message key="label.kanban"/></c:set>
            </c:if>
            <c:if test="${CabinetPartLevelLogViewBean.newCountType == 'NotCounted'}">
                <c:set var="newCountType"><fmt:message key="label.notcounted"/></c:set>
            </c:if>

            <c:if test="${status.index > 0}">,</c:if>
            {id:${status.index +1},
             data:[
                  'N',
                  '<tcmis:jsReplace value="${CabinetPartLevelLogViewBean.modifiedByName}" processMultiLines="false" />',
                  '<fmt:formatDate value="${CabinetPartLevelLogViewBean.dateModified}" pattern="${dateTimeFormatPattern}"/>',
                  '${CabinetPartLevelLogViewBean.dateModified}',
                  '${CabinetPartLevelLogViewBean.oldBinName}',
                  '${oldStatus}',
                  '${oldCountType}',
                  '${CabinetPartLevelLogViewBean.oldTierIITemperature}',
                  '${CabinetPartLevelLogViewBean.oldOwnershipName}',
                  '${CabinetPartLevelLogViewBean.oldDropShipOverride}',
                  '${CabinetPartLevelLogViewBean.newBinName}',
                  '${newStatus}',
                  '${newCountType}',
                  '${CabinetPartLevelLogViewBean.newTierIITemperature}',
                  '${CabinetPartLevelLogViewBean.newOwnershipName}',
                  '${CabinetPartLevelLogViewBean.newDropShipOverride}'   
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
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
<!--This sets the start time for time elapesed from the action.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${param.startSearchTime}">
<!--This sets the end time for time elapesed from the action.-->
<input name="endSearchTime" id="endSearchTime" type="hidden" value="">
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