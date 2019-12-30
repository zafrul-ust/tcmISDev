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

<!-- For Calendar support for column type hcal-->
<!--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<!-- These are for the Grid-->
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
<fmt:message key="label.approvalcode"/>
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
	seconds:"<fmt:message key="label.seconds"/>"
	};

var gridConfig = {
		divName:'useCodes', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'jsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'myGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:'false',			 // this page has rowSpan > 1 or not.
		submitDefault:'false',    // the fields in grid are defaulted to be submitted or not.
		noSmartRender:false // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
	};

var config = [
        {columnId:"permission", submit:false},
		{columnId:"addRemove", columnName:'<fmt:message key="label.addremove"/>', width:5, type:'hchstatus', align:"center"},
		{columnId:"useCodeName", columnName:'<fmt:message key="label.approvalcode"/>', width:5, tooltip: true,align:"center"},
        {columnId:"useCodeDesc", columnName:'<fmt:message key="label.description"/>', width:30, tooltip: true},
        {columnId:"useCodeId"}
 	];
	
windowCloseOnEsc = true;
window.onresize = resizeFrames;

var selectedId;
var selectedDesc;
var windowClose = true;
function myOnUnload() {
	if (windowClose) {
		opener.parent.closeTransitWin();
	}
}

var parentRowId = '${param.rowId}';
function doUpdate()
{
	var id = "";
	var name = "";
	var addCommas = 0;
	var size = myGrid.getRowsNum();
	for(var i = 1; i <= size;i++)
	{
		if($('addRemove'+ i).checked == true)
		{
			addCommas++;
			if(addCommas > 1 && i < size + 1)
			{
				id += ',';
				name += ', ';
			}	
			id += cellValue(i,'useCodeId');
			name += cellValue(i,'useCodeName');	
		}
	}
	opener.approvalCodeCallBack(parentRowId,id,name);
	myOnUnload();
	window.close();
}

var useCodeId = '${param.useCodeIdString}';
function myOnLoad() {

	for(var i = 0; i < jsonData.rows.length;i++)
	{
		if(useCodeId.indexOf(jsonData.rows[i].data[myGrid.getColIndexById("useCodeId")]) != -1)
			jsonData.rows[i].data[myGrid.getColIndexById("addRemove")] = true;
	}
}

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="popupOnLoadWithGrid(gridConfig);myOnLoad();" onresize="resizeFrames()" onUnload="myOnUnload();">

<tcmis:form action="/manageusecode.do" onsubmit="return submitOnlyOnce();">
<div class="interface" id="resultsPage">

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

//-->
</script>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>

<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<div id="resultGridDiv" style="display: none;">
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
	<a href="javascript:doUpdate()"><fmt:message key="label.makechange"/></a>
	<span id="updateResultLink" style="display: none">
	</span>
        &nbsp;
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
<c:set var="dataCount" value='0'/>
<!--  result page section start -->

<div id="useCodes" style="height:400px;display: none;" ></div>
<script type="text/javascript">
<!--

var jsonData = {
	rows:[<c:forEach var="bean" items="${useCodes}" varStatus="status">
		{ id:${status.index +1},	
		  data:[
			'Y',
			false,
  			'${bean.useCodeName}',
            '<tcmis:jsReplace value="${bean.useCodeDesc}" processMultiLines="true"/>',
            '${bean.useCodeId}'
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
</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<!--This sets the start time for time elapesed from the action.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
<!--This sets the end time for time elapesed from the action.-->
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">

<input name="minHeight" id="minHeight" type="hidden" value="100">
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