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
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/supplier/cylinderdetails.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script	type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

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
    <fmt:message key="label.viewcylinderdetail"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--

    with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    aI("text=;url=javascript:doNothing();image=");
    }

    drawMenus();

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
        manageDocumentsImages:"<fmt:message key="label.managedocumentsimages"/>",
        errors:"<fmt:message key="label.errors"/>"
    };

    var gridConfig = {
        divName:'cylinderDetailsDiv', // the div id to contain the grid.
        beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
        beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
        config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
        rowSpan:true,			 // this page has rowSpan > 1 or not.
        selectChild: 1,
        submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
        noSmartRender:false,
        onRowSelect:selectRow,
        onRightClick:selectRow
    };

    var config = [
     {
        columnId: "permission"
     },
     {
        columnId:"dateReceived",
        columnName:'<fmt:message key="label.datereceived"/>',
        width:8
     },
     {
    	columnId:"serialNumber",
    	columnName:'<fmt:message key="label.serialnumber"/>',
    	width:10
     },
     {
        columnId:"displayCylinderStatus",
        columnName:'<fmt:message key="label.cylinderstatus"/>',
        width:5
     },
     {
    	columnId:"dotRating",
    	columnName:'<fmt:message key="label.dotrating"/>',
    	width:10
     },
     {
    	columnId:"dimensions",
    	columnName:'<fmt:message key="label.dimensions"/>',
    	width:10
     },
     {
    	columnId:"gasType",
    	columnName:'<fmt:message key="label.gastype"/>',
    	width:10
     },
     {
    	columnId:"vendorPartNo",
    	columnName:'<fmt:message key="label.nsn8120"/>',
    	width:10
     },
     {
    	columnId:"correspondingNsn",
    	columnName:'<fmt:message key="label.nsn6830"/>',
    	width:10
     },
     {
        columnId:"conversionGroup",
        columnName:'<fmt:message key="label.conversiongroup"/>',
        width:7
     },
     {
        columnId:"cylinderConditionCode",
        columnName:'<fmt:message key="label.conditioncode"/>',
        width:7
     },
     {
        columnId:"capacity",
        columnName:'<fmt:message key="label.capacity"/>',
        width:7
     },
     {
        columnId:"manufacturerName",
        columnName:'<fmt:message key="label.manufacturer"/>',
        tooltip:"Y",
        width:15
     },
     {
        columnId:"latestHydroTestDate",
        columnName:'<fmt:message key="label.latesthydrotestdate"/>',
        width:7
     },
     {
        columnId:"returnFromLocation",
        columnName:'<fmt:message key="label.returnfromlocation"/>',
        width:7
     },
     {
        columnId:"lastShippedDodaac",
        columnName:'<fmt:message key="label.lastshippeddodaac"/>',
        width:7
     },
     {
        columnId:"dateSentOut",
        columnName:'<fmt:message key="label.datesentout"/>',
        width:7
     },
     {
        columnId:"dateDisposed",
        columnName:'<fmt:message key="label.datedisposed"/>',
        width:7
     },
     {
       	columnId:"locationDesc",
       	columnName:'<fmt:message key="label.location"/>',
       	tooltip:"Y",
      	width:15
     },
     {
        columnId:"receivedBy",
        columnName:'<fmt:message key="label.receivedby"/>',
        width:10
     },
     {
        columnId:"documentNumber",
        columnName:'<fmt:message key="label.documentnumber"/>',
        tooltip:"Y",
        width:10
     },
     {
        columnId:"lastUpdated",
        columnName:'<fmt:message key="label.lastupdated"/>',
        width:7
     },
     {
        columnId:"lastUpdateBy",
        columnName:'<fmt:message key="label.lastupdatedby"/>',
        width:7
     },
     {
        columnId:"notes",
        columnName:'<fmt:message key="label.notes"/>',
        tooltip:"Y",
        width:15
     },
     {
        columnId:"refurbSubcategory",
        columnName:'<fmt:message key="label.refurbactivity"/>',
        width:17
     },
     {
        columnId:"dateServiced",
        columnName:'<fmt:message key="label.dateserviced"/>',
        width:7
     },
     {
       columnId:"supplier"
     },
     {
       columnId:"cylinderTrackingId"
     }
    ];

    var rowSpanCols = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22];
    var lineMap = new Array();
    var lineMap3 = new Array();

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="resultOnLoadWithGrid(gridConfig);" onresize="resizeFrames();" onunload="closeAllchildren();">

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
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
 <!--  result page section start -->
<div id="cylinderDetailsDiv" style="height:400px;display: none;" ></div>
<c:if test="${cylinderDetailColl != null}" >

<%--NEW - storing the data to be displayed in the grid in a JSON.--%>
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty cylinderDetailColl}" >
    <script type="text/javascript">
    <!--
        var jsonMainData = new Array();
        var jsonMainData = {
        rows:[
            <c:forEach var="cylinderManagementBean" items="${cylinderDetailColl}" varStatus="status">
                <c:if test="${!status.first}">,</c:if>
                <c:set var="tmpStatus"><fmt:message key="label.active"/></c:set>
                <c:if test="${cylinderManagementBean.status == 'I'}">
                    <c:set var="tmpStatus"><fmt:message key="label.inactive"/></c:set>
                </c:if>

                {id:${status.count},
                    data:[
                        'N',
                        '<fmt:formatDate value="${cylinderManagementBean.dateReceived}" pattern="${dateTimeFormatPattern}"/>',
                        '<tcmis:jsReplace value="${cylinderManagementBean.serialNumber}" processMultiLines="true" />',
                        '<tcmis:jsReplace value="${cylinderManagementBean.cylinderStatus}" processMultiLines="true" />',
                        '<tcmis:jsReplace value="${cylinderManagementBean.dotRating}" processMultiLines="true" />',
                        '<tcmis:jsReplace value="${cylinderManagementBean.dimensions}" processMultiLines="true" />',
                        '<tcmis:jsReplace value="${cylinderManagementBean.gasType}" processMultiLines="true" />',
                        '<tcmis:jsReplace value="${cylinderManagementBean.vendorPartNo}" processMultiLines="true" />',
                        '<tcmis:jsReplace value="${cylinderManagementBean.correspondingNsn}" processMultiLines="true" />',
                        '${cylinderManagementBean.conversionGroup}',
                        '${cylinderManagementBean.cylinderConditionCode}',
                        '<tcmis:jsReplace value="${cylinderManagementBean.capacity}" processMultiLines="true" />',
                        '<tcmis:jsReplace value="${cylinderManagementBean.manufacturerName}" processMultiLines="true" />',
                        '<fmt:formatDate value="${cylinderManagementBean.latestHydroTestDate}" pattern="${dateFormatPattern}"/>',
                        '<tcmis:jsReplace value="${cylinderManagementBean.returnFromLocation}" processMultiLines="true" />',
                        '<tcmis:jsReplace value="${cylinderManagementBean.lastShippedDodaac}" processMultiLines="true" />',
                        '<fmt:formatDate value="${cylinderManagementBean.dateSentOut}" pattern="${dateTimeFormatPattern}"/>',
                        '<fmt:formatDate value="${cylinderManagementBean.dateDisposed}" pattern="${dateTimeFormatPattern}"/>',
                        '<tcmis:jsReplace value="${cylinderManagementBean.locationDesc}" processMultiLines="true" />',
                        '<tcmis:jsReplace value="${cylinderManagementBean.receivedByName}" processMultiLines="true" />',
                        '<tcmis:jsReplace value="${cylinderManagementBean.documentNumber}" processMultiLines="true" />',
                        '<fmt:formatDate value="${cylinderManagementBean.lastUpdated}" pattern="${dateTimeFormatPattern}"/>',
                        '<tcmis:jsReplace value="${cylinderManagementBean.lastUpdatedByName}" processMultiLines="true" />',
                        '<tcmis:jsReplace value="${cylinderManagementBean.notes}" processMultiLines="true" />',
                        '${cylinderManagementBean.refurbSubcategory}',
                        '<fmt:formatDate value="${cylinderManagementBean.dateServiced}" pattern="${dateTimeFormatPattern}"/>',
                        '${cylinderManagementBean.supplier}',
                        '${cylinderManagementBean.cylinderTrackingId}'
                ]}
         </c:forEach>
        ]};

        <c:set var="preCylinderTrackingId" value=''/>
        <c:set var="dataCount" value='0'/>
        <c:forEach var="cylinderManagementBean" items="${cylinderDetailColl}" varStatus="status">
        <c:choose>
            <c:when test="${cylinderManagementBean.cylinderTrackingId != preCylinderTrackingId}">
                lineMap[${status.index}] = 1;
                <c:set var="preCylinderTrackingId" value='${cylinderManagementBean.cylinderTrackingId}'/>
                <c:set var="dataCount" value="${dataCount + 1}"/>
                <c:set var="parent" value="${status.index}"/>
            </c:when>
            <c:otherwise>
                lineMap[${parent}]++;
            </c:otherwise>
        </c:choose>
            lineMap3[${status.index}] = ${dataCount % 2};
        </c:forEach>

    // -->
    </script>
</c:if>

<!-- If the collection is empty say no data found -->
<c:if test="${empty cylinderDetailColl}" >
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
<input name="totalLines" id="totalLines" value="${fn:length(cylinderDetailColl)}" type="hidden"/>
<input name="userAction" id="userAction" value='<tcmis:jsReplace value="${param.userAction}"/>' type="hidden"/>
<input name="supplier" is="supplier" value='<tcmis:jsReplace value="${param.supplier}"/>' type="hidden"/>
<input name="refurbCategory" is="refurbCategory" value='<tcmis:jsReplace value="${param.refurbCategory}"/>' type="hidden"/>
<input name="cylinderTrackingId" is="cylinderTrackingId" value='<tcmis:jsReplace value="${param.cylinderTrackingId}"/>' type="hidden"/>
<input type="hidden" name="errorMsg" id="errorMsg" value="${tcmISError}">
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