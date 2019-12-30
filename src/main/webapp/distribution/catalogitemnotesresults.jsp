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
<script type="text/javascript" src="/js/distribution/catalogitemnotes.js"></script>

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
    <fmt:message key="label.catalogitemnotes"/>
</title>

<script type="text/javascript">
    <!--
   	 showUpdateLinks = false;
    //-->
</script>

<script language="JavaScript" type="text/javascript">
<!--

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
selectedcontact:"<fmt:message key="label.selectedcontact"/>", 
selectedcustomercontact:"<fmt:message key="label.selectedcustomercontact"/>", 
newcontact:"<fmt:message key="label.newcontact"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

var config = [
{
  columnId:"permission"
},  
{ columnId:"deleteColumn",
  columnName:'<fmt:message key="label.delete"/>',
//  type:'hchstatus',
  align:'center',
  width:8
},
{
  columnId:"transactionDate",
  columnName:'<fmt:message key="label.date"/>',
  width:12
},
{ columnId:"enteredByName",
  columnName:'<fmt:message key="label.enteredby"/>',
  sorting:"haasStr",
  tooltip:true,
  width:15
},
{
  columnId:"comments",
  columnName:'<fmt:message key="label.comments"/>',
  type:"txt",
  tooltip:true,
  width:30
},
{ 
  columnId:"originalComments"
},
{ 
  columnId:"itemId"
},
{
  columnId:"recordNo"
}
];

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="initializeGrid();setResultFrameSize();">
<tcmis:form action="/catalogitemnotesresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors }">
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

<div id="catalogItemNotesBean" style="width:100%;height:500px;" style="display: none;"></div>
<c:set var="dataCount" value='${0}'/>

<script type="text/javascript">
<!--
<%--NEW - storing the data to be displayed in the grid in a JSON. notice the ID, this will be the id of the cell in the grid.--%>
<%--TODO - Right click to show links for receipt labels, print BOL, transactions history.--%>

var jsonMainData = new Array();
var jsonMainData = {
rows:[
{id:1, data:['Y','New','<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>',
			'<tcmis:jsReplace value="${personnelBean.firstName}"/> <tcmis:jsReplace value="${personnelBean.lastName}"/>',
          	'','','${param.itemId}','0']}
<c:forEach var="itemNotesBean" items="${itemNotesColl}" varStatus="status">
,

{ id:${status.index +2},
 data:['Y',
  '<input name="catalogItemNotesBean[${status.index+1}].delete" value="yes" type="checkbox" >',
  '<fmt:formatDate value="${itemNotesBean.transactionDate}" pattern="${dateTimeFormatPattern}"/>',
  '<tcmis:jsReplace value="${itemNotesBean.enteredByName}" />',
  '<tcmis:jsReplace value="${itemNotesBean.comments}" processMultiLines="true"/>',
  '<tcmis:jsReplace value="${itemNotesBean.comments}" processMultiLines="true"/>',
  '${itemNotesBean.itemId}','${itemNotesBean.recordNo}']}

<c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};


//-->
</script>




<!-- Hidden element start --> 
<div id="hiddenElements" style="display: none;">    			
	<input name="totalLines" id="totalLines" value="${dataCount}" type="text">
	<input name="itemId" id="itemId" type="hidden" value="${param.itemId}">
	<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>
</body>
</html:html>