<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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

<%-- Form popup Calendar support --%>
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/supplier/cylindereditsubcategory.js"></script>

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
    <fmt:message key="label.updaterefurbactivity"/>
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
        is:"<fmt:message key="label.is"/>",
        forX:"<fmt:message key="label.for"/>",
        missing:"<fmt:message key="label.missing"/>",
        dateServiced:"<fmt:message key="label.dateserviced"/>",
        noRowSelected:"<fmt:message key="error.norowselected"/>"
    };

    var gridConfig = {
        divName:'cylinderSubcategoryDiv', // the div id to contain the grid.
        beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
        beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
        config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
        rowSpan:false,			 // this page has rowSpan > 1 or not.
        submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
        noSmartRender:true
    };

    var config = [
    {
        columnId:"permission"
    },
    {
        columnId:"selectedRow",
        columnName:'<fmt:message key="label.select"/>',
        type:'hchstatus',
        align:'center',
        onChange:updateDateServiced,
        width:4
    },
    { columnId:"refurbSubcategory",
      columnName:'<fmt:message key="label.refurbactivity"/>',
      tooltip:true,
      width:20
    },
    { columnId:"dateServiced",
      columnName:'<fmt:message key="label.dateserviced"/>',
      type:'hcal',
      width:10
    },
    {
      columnId:"cylinderRefurbTransId"
    }
    ];

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="resultOnLoadWithGrid(gridConfig);" onresize="resizeFrames()">

<tcmis:form action="/cylindermanagementresults.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

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
      	<span id='selectedFieldsSpan'></span>
      	<%-- This is being taken out because the Non-Hazardous flag is deemed sufficient --%>
      	<a href="#" onclick="submitUpdate();"><fmt:message key="label.submit"/></a> |
      	<a href="#" onclick="submitUpdateAndClose();"><fmt:message key="label.submitandclose"/></a> |
      	<a href="#" onclick="window.close();"><fmt:message key="label.close"/></a>
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
 <!--  result page section start -->
<div id="cylinderSubcategoryDiv" style="height:400px;display: none;" ></div>
<c:if test="${subcategoryColl != null}" >

<%--NEW - storing the data to be displayed in the grid in a JSON.--%>
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty subcategoryColl}" >
    <script type="text/javascript">
    <!--
        var jsonMainData = new Array();
        var jsonMainData = {
        rows:[
            <c:forEach var="cylinderManagementBean" items="${subcategoryColl}" varStatus="status">
                <fmt:formatDate var="fmtDateServiced" value="${cylinderManagementBean.dateServiced}" pattern="${dateFormatPattern}"/>
                <c:set var="tmpSelect" value=''/>
                <c:if test="${!empty cylinderManagementBean.cylinderRefurbTransId}">
                    <c:set var="tmpSelect" value='true'/>
                </c:if>
                <c:if test="${!status.first}">,</c:if>
                { id:${status.index +1},
                 data:[
                    'Y',
                    ${tmpSelect},
                    '<tcmis:jsReplace value="${cylinderManagementBean.refurbSubcategory}" />',
                    '${fmtDateServiced}',
                    '${cylinderManagementBean.cylinderRefurbTransId}'
                 ]}
             </c:forEach>
        ]};
    // -->
    </script>
</c:if>

<!-- If the collection is empty say no data found -->
<c:if test="${empty subcategoryColl}" >
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
<input name="totalLines" id="totalLines" value="${fn:length(subcategoryColl)}" type="hidden"/>
<input name="userAction" id="userAction" value='<tcmis:jsReplace value="${param.userAction}"/>' type="hidden"/>
<input name="supplier" is="supplier" value='<tcmis:jsReplace value="${param.supplier}"/>' type="hidden"/>
<input name="refurbCategory" is="refurbCategory" value='<tcmis:jsReplace value="${param.refurbCategory}"/>' type="hidden"/>
<input name="cylinderTrackingId" is="cylinderTrackingId" value='<tcmis:jsReplace value="${param.cylinderTrackingId}"/>' type="hidden"/>
<input type="hidden" name="blockBefore_dateServiced" id="blockBefore_dateServiced" value=""/>
<input type="hidden" name="blockAfter_dateServiced" id="blockAfter_dateServiced" value="<tcmis:getDateTag numberOfDaysFromToday="1" datePattern="${dateFormatPattern}"/>"/>
<input type="hidden" name="blockBeforeExclude_dateServiced" id="blockBeforeExclude_dateServiced" value=""/>
<input type="hidden" name="blockAfterExclude_dateServiced" id="blockAfterExclude_dateServiced" value=""/>
<input type="hidden" name="inDefinite_dateServiced" id="inDefinite_dateServiced" value=""/>
<input type="hidden" name="submitAndClose" id="submitAndClose" value='<tcmis:jsReplace value="${param.submitAndClose}"/>'>
<input type="hidden" name="errorMsg" id="errorMsg" value="${tcmISError}">
<input type="hidden" name="todayDate" id="todayDate" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>"/>
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

<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
    <tr><td width="100px" style="background-color:darkGray">&nbsp;</td><td class="legendText"><fmt:message key="label.maxcomestimate"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>
        
</body>
</html:html>