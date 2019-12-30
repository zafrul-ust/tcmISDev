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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
</title>

<script language="JavaScript" type="text/javascript">
<!--

var beanGrid = null;
var windowCloseOnEsc = true;

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
returncatalogitem:"<fmt:message key="label.returncatalogitem"/>", 
returnSelected:"<fmt:message key='label.return'/> <fmt:message key='label.partnumber'/>", 
newcontact:"<fmt:message key="label.newcontact"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

function showErrors() {v 
	if (showErrorMessage) {
		var errorMessagesArea = $("errorMessagesArea");
		errorMessagesArea.style.display="";

		if (dhxWins == null) {
			dhxWins = new dhtmlXWindows();
			dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
		}

		if (!dhxWins.window("errorMessagesArea")) {
			// create window first time
			errorWin = dhxWins.createWindow("errorMessagesArea",0,0, 400, 200);
			errorWin.setText("");
			errorWin.clearIcon();  // hide icon
			errorWin.denyResize(); // deny resizing
			errorWin.denyPark();   // deny parking

			errorWin.attachObject("errorMessagesArea");
			// errorWin.attachEvent("onClose",
			// function(inputWin){inputWin.hide();});
			errorWin.center();
			// setting window position as default x,y position doesn't seem
			// to work, also hidding buttons.
			errorWin.setPosition(328, 131);
			errorWin.button("minmax1").hide();
			errorWin.button("park").hide();
			// errorWin.button("close").hide();
			errorWin.setModal(true);
		}else{
			// just show
			errorWin.setModal(true);
			dhxWins.window("errorMessagesArea").show();
		}
	}
}

function validate() {
	var msg = "";
	var rowCount = beanGrid.getRowsNum();
	var checkedRowCount = 0;
	for (var i = 1; i <= rowCount; i++) {
		var checkbox = $("ok"+i);
		if (checkbox && checkbox.checked) {
			checkedRowCount++;
			var rowItemId = gridCellValue(beanGrid, i, "itemId");
			if (rowItemId.length == 0) {
				msg = "Error: An Item ID is required for all selected rows.";
				break;
			}
		}
	}
	
	if (checkedRowCount == 0) {
		msg = "No rows selected.";
	}
	if (msg.length > 0) {
		alert(msg);
	}
	return msg.length == 0;
}

function addLocalesToTask() {
	if (validate()) {
		$("uAction").value = "addLocalesToTask";
		beanGrid.parentFormOnSubmit();
		document.genericForm.submit();
		return true;
	}
	return false;
}

function myHedOnChange(rowId, colId, returnError) {
	console.log(colId + " changed");
}

var gridConfig = {
		divName:'taskLocaleBean',
		beanData:'jsonMainData',
		beanGrid:'beanGrid',
		config:'config',
		rowSpan:false,
		noSmartRender: true,
		submitDefault:true
};

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
var config = [
	{columnId:"permission"},
	{columnId:"ok", columnName:"<fmt:message key="label.ok"/>", type:"hchstatus", align:"center", width:4},
	{columnId:"catalogQueueItemId"},
	{columnId:"task",columnName:'Task'},
	{columnId:"localeCode",columnName:"Locale"},
	{columnId:"localeDisplay",columnName:"Locale"},
	{columnId:"itemIdPermission",submit:false},
	{columnId:"itemId",columnName:"Item ID", type:'hed', onChange:myHedOnChange, permission:true},
	{columnId:"addlIngredItemIdPermission",submit:false},
	{columnId:"addlIngredItemId",columnName:"Addl Ingred Item ID", type:'hed', permission:true},
	{columnId:"sourcingItemIdPermission",submit:false},
	{columnId:"sourcingItemId",columnName:"Sourcing Item ID", type:'hed', permission:true}
	
];


//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);showErrors();">
<tcmis:form action="/newtaskresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors && empty tcmISError}">
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

<div id="taskLocaleBean" style="width:100%;%;height:400px;" style="display: none;"></div>
<c:set var="dataCount" value='${0}'/>	

<c:if test="${!empty beanCollection }" >
<script type="text/javascript">
<!--

var jsonMainData = {
rows:[
<c:forEach var="bean" items="${beanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

/*The row ID needs to start with 1 per their design. Use sinlge quotes for column data seperators.*/
{ id:${status.index +1},
 data:[
	  "${bean.assignedToSupplier eq 'Y'?'N':'Y'}",
	  '',
	  '${bean.catalogQueueItemId}',
	  '<tcmis:jsReplace value="${empty bean.task?param.task:bean.task}"/>',
	  '${bean.localeCode}',
	  '${bean.localeDisplay}',
	  "${empty bean.itemId?'Y':'N'}",
	  '${bean.itemId}',
	  "${empty bean.itemId?'Y':'N'}",
	  "${bean.addlIngredItemId}",
	  "${empty bean.itemId?'Y':'N'}",
	  "${bean.sourcingItemId}"
 ]}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};

// -->
</script>   
</c:if>   


<!-- If the collection is empty say no data found -->
<c:if test="${empty beanCollection}">
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
	<input name="uAction" id="uAction" type="hidden"/>
	<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
	<input name="customerId" id="customerId" type="hidden" value="<tcmis:jsReplace value="${param.customerId}"/>"/>
	<input name="minHeight" id="minHeight" type="hidden" value="100"/>
	<input name="supplier" id="supplier" type="hidden" value="<tcmis:jsReplace value="${param.supplier}"/>"/>
	<input name="supplierName" id="supplierName" type="hidden" value="<tcmis:jsReplace value="${param.supplierName}"/>"/>
	<input name="opsEntityId" id="opsEntityId" type="hidden" value="<tcmis:jsReplace value="${param.opsEntityId}"/>"/>
	<input name="task" id="task" type="hidden" value="<tcmis:jsReplace value="${param.task}"/>"/>
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>
</body>
</html:html>