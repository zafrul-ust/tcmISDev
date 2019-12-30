
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
<script type="text/javascript" src="/js/client/het/monitorusageresults.js"></script>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
    <fmt:message key="label.changehistory"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {
	alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	validvalues:"<fmt:message key="label.validvalues"/>",
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
        recordFound:"<fmt:message key="label.recordFound"/>",
        searchDuration:"<fmt:message key="label.searchDuration"/>",
        minutes:"<fmt:message key="label.minutes"/>",
        seconds:"<fmt:message key="label.seconds"/>",
        ok:"<fmt:message key="label.ok"/>",
        quantityError:"<fmt:message key="label.positivenumber"><fmt:param><fmt:message key="label.quantity"/></fmt:param></fmt:message>",
	dateRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.usagedate"/></fmt:param></fmt:message>",   
        quantity:"<fmt:message key="label.quantity"/>"
        };             
var config = [
	{ columnId:"workArea", columnName:'<fmt:message key="label.workarea"/>', width:15, tooltip: true },
	{ columnId:"loggerName", columnName:'<fmt:message key="label.loggedby"/>', width:10, tooltip: true },
	{ columnId:"transactionId", columnName:'<fmt:message key="label.transactionid"/>', align:'center', width:6 },
	{ columnId:"modifiedDate", type:'hcal', columnName:'<fmt:message key="label.changedon"/>', align:'center', width:12 },
	{ columnId:"catPartNo", columnName:'<fmt:message key="label.partno"/>', width:8, align:'center' },
	{ columnId:"msdsNo", columnName:'<fmt:message key="label.msdsnumber"/>', width:8, align:'center' },
	{ columnId:"employee", columnName:'<fmt:message key="label.employee"/>', align:'left', width:7, size:4, maxlength:5 },
	{ columnId:"containerId", columnName:'<fmt:message key="label.containerid"/>', width:7, align:'center'},
	{ columnId:"permit", columnName:'<fmt:message key="label.permit"/>', align:'center', width:8 },
	{ columnId:"controlDevice", columnName:'<fmt:message key="label.controldevice"/>', align:'center', width:8},
	{ columnId:"applicationMethod", columnName:'<fmt:message key="label.applicationmethod"/>', align:'center', width:5 },
	{ columnId:"painted", columnName:'<fmt:message key="label.painted"/>', align:'center', width:5 },
	{ columnId:"substrate", columnName:'<fmt:message key="label.substrate"/>',  align:'center', width:5 },
	{ columnId:"partType", columnName:'<fmt:message key="label.parttype"/>',  align:'center', width:5 },
	{ columnId:"quantity", columnName:'<fmt:message key="label.usage"/>', attachHeader:'<fmt:message key="label.quantity"/>',  align:'right', width:6, size:4, maxlength:5 },
	{ columnId:"unitOfMeasure", columnName:'#cspan', attachHeader:'<fmt:message key="label.unit"/>', align:"center", width:3},
	{ columnId:"amountRemaining", columnName:'#cspan', attachHeader:'<fmt:message key="label.remaining"/>',  align:'center', width:6, size:4, maxlength:5 },
	{ columnId:"discarded", columnName:'<fmt:message key="label.discard"/>', align:'center', width:5 },
	{ columnId:"usageDate", type:'hcal', columnName:'<fmt:message key="label.usagedate"/>', align:'center', width:8 },
	{ columnId:"exported", columnName:'<fmt:message key="label.exported"/>', align:'center', width:5 },
	{ columnId:"modified", columnName:'<fmt:message key="label.modified"/>',  align:'center', width:5 },
	{columnId:"remarks", columnName:'<fmt:message key="label.remarks"/>', width:40, size:40, maxlength:200 }
];
            
// -->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoadForHistory()">
<tcmis:form action="/monitorusageresults.do">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
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

<div id="HetUsageBean" style="width:100%;height:400px;" style="display: none;"></div>

<script type="text/javascript">
<!--
<c:if test="${!empty hetUsageBeanColl}" >  
var jsonMainData =  {
	rows:[<c:forEach var="bean" items="${hetUsageBeanColl}" varStatus="status">
		{id:${status.count},
		data:[
		  '${bean.applicationDesc}',
		  '${bean.loggerName}',
		  '${bean.transactionId}',
		  '<c:choose><c:when test="${bean.modified}"><fmt:formatDate value="${bean.modifiedDate}" pattern="${dateTimeFormatPattern}"/></c:when><c:otherwise><fmt:formatDate value="${bean.insertDate}" pattern="${dateTimeFormatPattern}"/></c:otherwise></c:choose>',
		  '${bean.catPartNo}',
		  '${bean.msdsNo}',
		  '<tcmis:jsReplace value="${bean.employee}"/>',
		  '${bean.containerId}',
		  '<tcmis:jsReplace value="${bean.permit}"/>',
	 	  '<tcmis:jsReplace value="${bean.controlDevice}"/>',
		  '<tcmis:jsReplace value="${bean.applicationMethod}"/>',
		  '<c:choose><c:when test="${bean.painted}" >Y</c:when><c:otherwise>N</c:otherwise></c:choose>',
		  '${bean.substrate}',
		  '${bean.partType}',
		  '${bean.quantity}',
		  '${bean.unitOfMeasure}',
		  '${bean.amountRemaining}',
		  '<c:choose><c:when test="${bean.discarded}">Y</c:when><c:otherwise>N</c:otherwise></c:choose>',
		  '<fmt:formatDate value="${bean.usageDate}" pattern="${dateFormatPattern}"/>',
		  '<c:choose><c:when test="${bean.exported}">Y</c:when><c:otherwise>N</c:otherwise></c:choose>',
		  '<c:choose><c:when test="${bean.modified}">Y</c:when><c:otherwise>N</c:otherwise></c:choose>',
		  '<tcmis:jsReplace value="${bean.remarks}"/>',
		  ]}<c:if test="${!status.last}">,</c:if>  
	 </c:forEach>
]};
</c:if>
//-->  
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty hetUsageBeanColl}">
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
	<input name="minHeight" id="minHeight" type="hidden" value="100">
	<input name="totalLines" id="totalLines" value="${fn:length(hetUsageBeanColl)}" type="hidden"/>
	<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
	<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">	
	<input name="uAction" id="uAction" value="" type="hidden"/>	
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->

</tcmis:form>
</body>
</html:html>