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
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
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

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/js/common/cabinet/clientcabinettanklevelcount.js"></script>

<title>
<fmt:message key="label.tanklevelcount"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

var messagesData = new Array();
messagesData={
    alert:"<fmt:message key="label.alert"/>",
    and:"<fmt:message key="label.and"/>",
    recordFound:"<fmt:message key="label.recordFound"/>",
    searchDuration:"<fmt:message key="label.searchDuration"/>",
    minutes:"<fmt:message key="label.minutes"/>",
    seconds:"<fmt:message key="label.seconds"/>",
    validvalues:"<fmt:message key="label.validvalues"/>",
    submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
    itemInteger:"<fmt:message key="error.item.integer"/>",
    updateSuccess:"<fmt:message key="label.dataupdatedsuccessfully"/>",
    validlevelfillfield:"<fmt:message key="label.validlevelfillfield"/>",
    validlevelfield:"<fmt:message key="label.validlevelfield"/>",
    validvaluenoblank:"<fmt:message key="label.validvaluenoblank"/>",
    selectedRowMsg:"<fmt:message key="label.returnselecteddata"/>",
    emptygridmsg:"<fmt:message key="label.emptygridmsg"/>",
    duplicatedateerr:"<fmt:message key="labal.duplicatedateerr"/>",
    futuredateerror:"<fmt:message key="label.futuredateerror"/>",
    errors:"<fmt:message key="label.errors"/>"
    };



var gridConfig = {
        divName:'tankLevelCount', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
        beanData:'jsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
        beanGrid:'myGrid',     // the grid's name, as in beanGrid.attachEvent...
        config:'config',         // the column config var name, as in var config = { [ columnId:..,columnName...
        rowSpan:false,           // this page has rowSpan > 1 or not.
        submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
        noSmartRender:true, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
        onRightClick: onRightClickRemove,
        onRowSelect: onRowSelected
        };


var config = [
    {columnId:"permission"},
    {columnId:"updated"},
    {columnId:"id", columnName:'<fmt:message key="label.id"/>', width:5,  size:5, align:"center",sorting:"na"},
    {columnId:"countDatetime", columnName:'<fmt:message key="label.date"/>', width:10,  size:15, type:'hcal', align:"center", sorting:"na"},
    {columnId:"countQuantity", columnName:'<fmt:message key="label.level"/>', onChange:validateLevelCount, width:10, size:10, type:'hed',sorting:"na", align:"center"},
    {columnId:"afterReplLevelQty", columnName:'<fmt:message key="label.levelfill"/>', onChange:validateLevelFillCount, width:10, size:10, type:'hed', align:"center",sorting:"na"},
    {columnId:"processErrorMsg", columnName:'<fmt:message key="label.errormessagedisp"/>', width:20,sorting:"na" },
    {columnId:"binId"},
    {columnId:"countType"},    
    {columnId:"companyId"}
    ];
    
with(milonic=new menuname("rightClickTankLevelRemove")){
    top="offset=2"
    style = contextStyle;
    margin=3
    aI("text=<fmt:message key="label.removeLine"/>;url=javascript:delTankLevelRow();");
}

<%-- Initialize the RCMenus --%>
drawMenus();

function addNewRow(idNumber) {
    var id = myGrid.getRowsNum();
    var data = ['Y',
            'Y',
            ''+idNumber,
            '',
            '',
            '',
            '',
            '${param.binId}',
            '${param.countType}',
            '${personnelBean.companyId}'
            ];
    var nextId = id + 1;
    while (myGrid.doesRowExist(nextId)) 
        nextId++;  
    myGrid.addRow(nextId, data, myGrid.getRowsNum());
    myGrid.selectRowById(nextId);
}

var dhxWins = null;
//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
    if (dhxWins == null) {
        dhxWins = new dhtmlXWindows();
        dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
    }
}
function closeTransitWin() {
    if (dhxWins != null) {
        if (dhxWins.window("transitDialogWin")) {
            dhxWins.window("transitDialogWin").setModal(false);
            dhxWins.window("transitDialogWin").hide();
        }
    }
}
function showTransitWin(message) {
    if (message != null && message.length > 0) {
        document.getElementById("transitLabel").innerHTML = message;
    }else {
        document.getElementById("transitLabel").innerHTML = messagesData.pleasewait;
    }

    var transitDialogWin = document.getElementById("transitDialogWin");
    transitDialogWin.style.display="";

    initializeDhxWins();
    if (!dhxWins.window("transitDialogWin")) {
        // create window first time
        transitWin = dhxWins.createWindow("transitDialogWin",0,0, 400, 200);
        transitWin.setText("");
        transitWin.clearIcon();  // hide icon
        transitWin.denyResize(); // deny resizing
        transitWin.denyPark();   // deny parking

        transitWin.attachObject("transitDialogWin");
        //transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
        transitWin.center();
        // setting window position as default x,y position doesn't seem to work, also hidding buttons.
        transitWin.setPosition(328, 131);
        transitWin.button("minmax1").hide();
        transitWin.button("park").hide();
        transitWin.button("close").hide();
        transitWin.setModal(true);
    }else{
        // just show
        transitWin.setModal(true);
        dhxWins.window("transitDialogWin").show();
    }
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnLoad(${uploadSequence});" onUnload="myOnUnload();">

<tcmis:form action="/clientcabinettanklevelcount.do" onsubmit="return submitOnlyOnce();">
<div class="interface" id="mainPage">

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
    ${status.current}<br/>
  </c:forEach>
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
    showErrorMessage = false;
<c:if test="${!empty tcmISError}">
    showErrorMessage = true;
</c:if>
<c:forEach var="errorMsg" items="${tcmISError}">
    <c:if test="${!empty errorMsg}">
        showErrorMessage = true;
    </c:if>
</c:forEach>
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

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<div id="resultGridDiv" style="display: none;">
<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">

<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>    
      <c:if test="${param.countType eq 'L'}">
   	  	<div id="levelUnit" class="optionTitle"><b><fmt:message key="label.levelunit"/>:</b> &nbsp;&nbsp;&nbsp;<c:out value="${levelUnit}"></c:out></div>
   	  </c:if>
   	    <div id="binDetails" class="optionTitle">
   	        <b><fmt:message key="label.workareadescription"/>:</b> &nbsp;<c:out value="${param.workAreaDesc}"></c:out>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   	        <b><fmt:message key="label.binid"/>:</b> &nbsp;<c:out value="${param.binId}"></c:out>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   	        <b><fmt:message key="label.partnumber"/>:</b> &nbsp;<c:out value="${param.partNumber}"></c:out><br/>
   	        <b><fmt:message key="label.partdescription"/>:</b> &nbsp;<c:out value="${param.partDesc}"></c:out>   	        
   	    </div><br/>
      <div id="mainUpdateLinks" style="display: none"><%-- mainUpdateLinks Begins --%> 
        <a href="javascript:callAddNewRow()"><fmt:message key="label.newtanklevelcount"/></a>&nbsp;
        |&nbsp;<a href="javascript:doUpdate()"><fmt:message key="label.submit"/></a>&nbsp;
        |&nbsp;<a href="javascript:closePopup()"><fmt:message key="label.cancel"/></a>&nbsp;
        <span id="updateResultLink" style="display: none"></span>&nbsp;
      </div><%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent" style="height:550px">
<c:set var="dataCount" value='0'/>
<!--  result page section start -->

<div id="tankLevelCount" style="height:100%;display: none;" ></div>
<script type="text/javascript">
<!--

var jsonData = {
    rows:[<c:forEach var="bean" items="${tankLevelCountCol}" varStatus="status">
        { id:${status.index +1},       
          <c:if test="${bean.processingStatus == 'ERROR'}">'class': 'grid_red',</c:if>                 
          data:['Y',
            'Y',            
            '${status.index +1}',            
            '<fmt:formatDate value="${bean.countDatetime}" pattern="${dateFormatPattern}"/>',        
            '${bean.countQuantity}',
            '${bean.afterReplLevelQty}',
            '<tcmis:jsReplace value="${bean.processErrorMsg}" processMultiLines="true" />',
            '${bean.binId}',
            '${bean.countType}',
            '${bean.companyId}' 
            ]
        }<c:if test="${!status.last}">,</c:if> <c:set var="dataCount" value='${dataCount+1}'/>
    </c:forEach>
    ]};

// -->
</script>


<!-- Search results end -->
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
    <div id="transitDialogWin" class="errorMessages" style="display: none;overflow: auto;">
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
</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
    <input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
    <input name="companyId" id="companyId" value="${param.companyId}" type="hidden"/>
    <input name="binId" id="binId" value="${param.binId}" type="hidden"/>
    <input name="countType" id="countType" value="${param.countType}" type="hidden"/>
    <input name="uploadSequence" id="uploadSequence" value="${uploadSequence}" type="hidden"/>
    <input name="rowId" id="rowId" value="${param.rowId}" type="hidden"/>
    <input name="uAction" id="uAction" value="" type="hidden"/>
    <input name="lastQtyOnHand" id="lastQtyOnHand" value="${lastQtyOnHand}" type="hidden"/>
    <!--This sets the start time for time elapesed.-->
    <input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
    <input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
    <input name="minHeight" id="minHeight" type="hidden" value="100"/>

	<input type="hidden" name="blockBefore_countDatetime" id="blockBefore_countDatetime" value=""/>
	<input type="hidden" name="blockAfter_countDatetime" id="blockAfter_countDatetime" value="<tcmis:getDateTag numberOfDaysFromToday="1" datePattern="${dateFormatPattern}"/>"/>
	<input type="hidden" name="blockBeforeExclude_countDatetime" id="blockBeforeExclude_countDatetime" value=""/>
	<input type="hidden" name="blockAfterExclude_countDatetime" id="blockAfterExclude_countDatetime" value=""/>
	<input type="hidden" name="inDefinite_countDatetime" id="inDefinite_countDatetime" value=""/>
	<input type="hidden" name="blockBeforeExcludeDate" id="blockBeforeExcludeDate" value=""/>    
 
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->
</tcmis:form>

<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

<script type="text/javascript">
showUpdateLinks = true;
//-->
</script>

</body>
</html:html>