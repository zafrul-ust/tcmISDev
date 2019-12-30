<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

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
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS -->
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
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/cabinet/clientcabinetputawayresults.js"></script>

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
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
    <fmt:message key="clientCabinetManagement"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
            validvalues:"<fmt:message key="label.validvalues"/>",
            submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
            recordFound:"<fmt:message key="label.recordFound"/>",
            searchDuration:"<fmt:message key="label.searchDuration"/>",
            minutes:"<fmt:message key="label.minutes"/>",
            seconds:"<fmt:message key="label.seconds"/>",
            ok:"<fmt:message key="label.ok"/>",
            noRowSelected:"<fmt:message key="label.norowselected"/>",
            updatesucess:"<fmt:message key="msg.savesuccess"/>"
			};
				
// -->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/clientcabinetputawayresults.do" onsubmit="return submitFrameOnlyOnce();">
    <c:set var="userHasPermission" value='N'/>
    <tcmis:applicationPermission indicator="true" userGroupId="StockedPartMgmt" companyId="${param.companyId}" facilityId="${param.facilityId}">
			<c:set var="userHasPermission" value='Y'/>
			<script type="text/javascript">
				<!--
                    showUpdateLinks = true;
				 //-->
			</script>
	</tcmis:applicationPermission>

  <script type="text/javascript">
    var config = [
      {columnId:"permission"},
      {columnId:"okDoUpdate",
         <c:if test="${'Y' == userHasPermission}">
            columnName:'<fmt:message key="label.ok"/><br><input type="checkbox" value="" onClick="return checkAll(\'okDoUpdate\');" name="chkAllOkDoUpdate" id="chkAllOkDoUpdate">', type:'hchstatus',
         </c:if>
         align:'center',  width:3
      },
      {columnId:"companyId" },
      {columnId:"applicationId" },
      {columnId:"application" },
      {columnId:"applicationDesc", columnName:'<fmt:message key="label.workarea"/>', tooltip:"Y", width:20 },
      {columnId:"shipmentId", columnName:'<fmt:message key="label.packinglist"/>', tooltip:"Y", width:20 },
      {columnId:"dateDelivered", type:'hcal', columnName:'<fmt:message key="label.datedelivered"/>', align:'center', width:15, permission: true }
    ];
    // -->
    </script>
<div id="errorMessagesAreaBody" style="display:none;">
    <c:forEach var="tcmisError" items="${tcmISError}">
      ${tcmisError}<br/><br/>
    </c:forEach>
    <c:if test="${param.maxData == fn:length(searchResultBeanCollection)}">
     <fmt:message key="label.maxdata">
       <fmt:param value="${param.maxData}"/>
     </fmt:message>
    </c:if>
</div>


<script type="text/javascript">
    <c:choose>
    <c:when test="${empty tcmISErrors and empty tcmISError}">
     <c:choose>
      <c:when test="${param.maxData == fn:length(searchResultBeanCollection)}">
        showErrorMessage = true;
        parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
      </c:when>
      <c:otherwise>
        showErrorMessage = false;
      </c:otherwise>
     </c:choose>
    </c:when>
    <c:otherwise>
      parent.messagesData.errors = "<fmt:message key="label.errors"/>";
      showErrorMessage = true;
      </c:otherwise>
    </c:choose>
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

<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="cabinetPutAwayBean" style="width:100%;height:400px;" style="display: none;"></div>

<script type="text/javascript">
<!--

<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty searchResultBeanCollection}" >  
	var jsonMainData = new Array();
	var jsonMainData = {
	rows:[
		<c:forEach var="bean" items="${searchResultBeanCollection}" varStatus="status">
		<c:if test="${status.index > 0}">,</c:if>
	
		/*The row ID needs to start with 1 per their design.*/
		{ id:${status.index +1},
		  data:[
				'${userHasPermission}',
                <c:if test="${'Y' == userHasPermission}">
                'N',
                </c:if>
                '${bean.companyId}',
				'${bean.applicationId}',
				'${bean.application}',
				'<tcmis:jsReplace value="${bean.applicationDesc}" processMultiLines="true"/>',
				'${bean.shipmentId}',
				'<fmt:formatDate value="${bean.dateDelivered}" pattern="${dateFormatPattern}"/>'
		  ]}  
	 	<c:set var="dataCount" value='${dataCount+1}'/> 
	</c:forEach>
	]};
</c:if>

//-->  
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty searchResultBeanCollection}">
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
	<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden">
    <input name="uAction" id="uAction" value="" type="hidden">
    <input name="minHeight" id="minHeight" type="hidden" value="100">
      
  <!-- Put all the Search Criteria here for re-search after update-->
  
	<input name="applicationId" id="applicationId" value="${param.applicationId}" type="hidden"/>
	<input name="searchArgument" id="searchArgument" value="<c:out value="${param.searchArgument}"/>" type="hidden">
	<input name="criterion" id="criterion" value="<c:out value="${param.criterion}"/>" type="hidden">
	<input name="criteria" id="criteria" value="<c:out value="${param.criteria}"/>" type="hidden">
	<input name="companyId" id="companyId" value="<c:out value="${param.companyId}"/>" type="hidden">
	<input name="facilityId" id="facilityId" value="<c:out value="${param.facilityId}"/>" type="hidden">
	<input name="facilityName" id="facilityName" value="<c:out value="${param.facilityName}"/>" type="hidden">
	<input name="reportingEntityId" id="reportingEntityId" value="<c:out value="${param.reportingEntityId}"/>" type="hidden">
    <input name="deptId" id="deptId" value="${param.deptId}" type="hidden">
    <input name="areaId" id="areaId" value="${param.areaId}" type="hidden">
    <input name="buildingId" id="buildingId" value="${param.buildingId}" type="hidden">
    <input name="maxData" id="maxData" type="hidden" value="${param.maxData}">
	<input name="dateStart" id="dateStart" value="${param.dateStart}" type="hidden">
    <input name="dateStop" id="dateStop" value="${param.dateStop}" type="hidden">  
    <input name="putAwayMethod" id="putAwayMethod" value="${param.putAwayMethod}" type="hidden">  
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>
</body>
</html:html>