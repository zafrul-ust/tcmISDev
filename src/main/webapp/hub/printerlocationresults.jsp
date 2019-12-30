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
This looks at what the users preffered font size and which application he is viewing to set the correct CSS. -->
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

<!-- For Calendar support for column type hcal-->
<!--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/printerlocationresults.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srndRowSpan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="label.labelPrinter"/>
</title>

<script language="JavaScript" type="text/javascript">
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
ok:"<fmt:message key="label.ok"/>",
labelPrinter:"<fmt:message key="label.labelPrinter"/>",size:"<fmt:message key="label.size"/>",printerpath:"<fmt:message key="label.printerpath"/>",
status:"<fmt:message key="label.status"/>",altPrinterLocation:"<fmt:message key="label.altprinterlocation"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};

<tcmis:facilityPermission indicator="true" userGroupId="updatePrinterPath">
with(milonic=new menuname("addMenu")){
   top="offset=2"
    style = contextStyle;
    margin=3
    	aI("text=<fmt:message key="label.addprinterpath"/>;url=javascript:addPrinterPath();");
    }
</tcmis:facilityPermission>
drawMenus();

</script>
</head>

<body bgcolor="#ffffff" onload="setHlink('${param.hub}');myResultOnload()">

<tcmis:form action="/printerlocationresults.do" onsubmit="return submitFrameOnlyOnce();">
<c:set var="printerLocationPermission" value=''/>
<tcmis:facilityPermission indicator="true" userGroupId="updatePrinterPath" >
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
 <c:set var="printerLocationPermission" value='Y'/>
</tcmis:facilityPermission>

<c:set var="alternatePrinterLocPermission" value='N'/>
<tcmis:facilityPermission indicator="true" userGroupId="updateAltPrinterLocation" >
 <c:set var="alternatePrinterLocPermission" value='Y'/>
</tcmis:facilityPermission>

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
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
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null and empty tcmISErrors and empty tcmISError}">
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
    
<!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
<div id="locationLabelPrinterBean" style="width:100%;height:400px;" style="display: none;"></div>
<!-- Search results start -->
<c:if test="${locationLabelPrinterBeanCollection != null}" >
<script type="text/javascript">
<!--
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
var status= new Array(			 
	 {text:'Offline',value:'Offline'},
         {text:'Online',value:'Online'}
);
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty locationLabelPrinterBeanCollection}" >
/*Storing the data to be displayed in a JSON object array.*/
var jsonMainData = new Array();
jsonMainData = {
rows:[
<c:forEach var="locationLabelPrinterBean" items="${locationLabelPrinterBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
/*Do any formatting you need to do before entering ti into the JSON. This grid will display what is in the JSON.*/

/*Use this to replace any special characters in the data, this is mostly done to data that includes
  user input and strings with posibilites of having special characters.*/
<tcmis:jsReplace var="printerLocation" value="${status.current.printerLocation}" processMultiLines="false" />
<tcmis:jsReplace var="printerPath" value="${status.current.printerPath}" processMultiLines="false" />
/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
<c:if test="${status.current.status eq 'Offline'}">'class':"grid_black",</c:if>
 data:['${printerLocationPermission}',
    '${statusPerm}',
    '${altlocPerm}',
    false,
   '${printerLocation}','${status.current.labelStock}','${printerPath}','${printerPath}','${status.current.hub}']}
  <c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>
]};
</c:if>
//-->
</script>
       
<!-- If the collection is empty say no data found -->
<c:if test="${empty locationLabelPrinterBeanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>

</c:if>
<!-- Search results end -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
<input name="action" id="action" value="" type="hidden">

<%-- can use on read-only pages only. Store all search criteria in hidden elements, need this to requery the database after updates
Do not use <tcmis:saveRequestParameter/> on pages with updates
The purpose of this tag was to save the search criteria on the result page.
When there is a form in the result section and gets submitted to the server there is no difference between
the search parameters and the result parameters. This causes duplicates of the result section parameters.
This can potentially cause lot of mix-ups.
 --%>
<%--<tcmis:saveRequestParameter/>--%>

<%--Need to store search input options here. This is used to re-do the original search upon updates etc.--%>    

<input name="hub" id="hub" type="hidden" value="${param.hub}">

<%--This is the minimum height of the result section you want to display--%>
<input name="minHeight" id="minHeight" type="hidden" value="0">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

<!-- If the user has permissions and needs to see the update links,set the variable showUpdateLinks javascript to true.
     The default value of showUpdateLinks is false.
-->
<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>

</tcmis:form>
</body>
</html:html>
