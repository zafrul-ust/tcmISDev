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
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/resultiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/peiproject/peiprojectlist.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="peiproject.label.projectlist"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/> ",
    recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">

<!--Uncomment for production-->
<tcmis:form action="/peiprojectlistresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<!--Uncomment for production-->
<%--
<tcmis:permission indicator="true" userGroupId="pageNameUserGroup" facilityId="${param.testingField}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</tcmis:permission>
--%>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

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
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage"> <!-- start of interface -->
<div class="backGroundContent"> <!-- start of backGroundContent -->

<c:if test="${peiProjectViewListBeanCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:set var="rowCount" value='${0}'/>
 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty peiProjectViewListBeanCollection}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
<c:forEach var="peiProjectViewBean" items="${peiProjectViewListBeanCollection}" varStatus="status">
<c:set var="rowCount" value='${rowCount+1}'/>
<c:if test="${status.index % 10 == 0}">

<tr>
<th width="2%"><fmt:message key="label.print"/></th>
<th width="5%"><fmt:message key="label.company"/></th>
<th width="5%"><fmt:message key="label.facility"/></th>
<th width="5%"><fmt:message key="label.category"/></th>
<th width="2%"><fmt:message key="peiproject.label.bestpractice"/></th>
<th width="2%"><fmt:message key="label.id"/></th>
<th width="10%"><fmt:message key="peiproject.label.projectname"/></th>
<th width="15%"><fmt:message key="peiproject.label.projectdesc"/></th>
<th width="5%"><fmt:message key="peiproject.label.projectmanager"/></th>
<th width="5%"><fmt:message key="peiproject.label.proposeddate"/><hr><fmt:message key="label.approveddate"/></th>
<th width="5%"><fmt:message key="peiproject.label.gainshareduration"/></th>
<th width="5%"><fmt:message key="peiproject.label.projectedfinishdate"/><hr><fmt:message key="peiproject.label.actualfinishdate"/></th>
<th width="5%"><fmt:message key="peiproject.label.projectedcost"/><hr><fmt:message key="peiproject.label.actualcost"/></th>
<th width="5%"><fmt:message key="peiproject.label.totalprojectedsavings"/><hr><fmt:message key="peiproject.label.totalactualsavings"/></th>
<th width="5%"><fmt:message key="label.status"/></th>
</tr>
</c:if>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value=''/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='alt'/>
  </c:otherwise>
</c:choose>

   <tr class="${colorClass}">
       <input type="hidden" name="projectId${status.index}" id="projectId${status.index}" value="${status.current.projectId}">
       <td width="2%">
           <c:set var="printPdf" value='${status.current.printPdf}'/>
           <c:if test="${printPdf != null}" >
           <c:set var="checkBoxChecked" value='checked'/>
           </c:if>
        <input type="checkbox" name="printPdf${status.index}" id="printPdf${status.index}" value="Y" ${checkBoxChecked}>
      </td>
      <td width="5%">${status.current.companyId}</td>
      <td width="5%">${status.current.facilityId}</td>
      <td width="5%">
         <c:set var="selectedProjectCategory" value='${status.current.projectCategory}'/>
         <c:forEach var="vvProjectCategoryBean" items="${vvProjectCategoryCollection}" varStatus="statusCategory">
         <c:set var="currentProjectCategory" value='${statusCategory.current.projectCategory}'/>
         <c:choose>
         <c:when test="${currentProjectCategory == selectedProjectCategory}">
         ${statusCategory.current.projectCategoryDesc}
         </c:when>
         </c:choose>
         </c:forEach>
<!--        </select> -->
     </td>
     <td width="2%">${status.current.bestPractice}</td>
     <td width="2%">

            <a href="javascript: showProjectDetails(${status.current.projectId});" style="color:#0000ff;cursor:pointer;text-decoration:underline">${status.current.projectId}</a>
           <%--<a href="${linkModule}/showpeiproject.do?projectId=${status.current.projectId}" target="new${status.index}" style="color:#0000ff;cursor:pointer;text-decoration:underline">${status.current.projectId}</a>--%>
         </td>
     <td width="10%">${status.current.projectName}</td>
     <td width="15%">${status.current.projectDesc}</td>
     <td width="5%">${status.current.projectManager}</td>

     <td width="5%">
     <fmt:formatDate var="formattedProposedDateToClient" value="${status.current.proposedDateToClient}" pattern="${dateFormatPattern}"/>
      ${formattedProposedDateToClient}
       <hr>
<fmt:formatDate var="formattedApprovedDate" value="${status.current.approvedDate}" pattern="${dateFormatPattern}"/>
${formattedApprovedDate}
</td>
<td width="5%">${status.current.gainShareDuration}</td>

<td width="5%">
<fmt:formatDate var="formattedProjectedFinistedDate" value="${status.current.projectedFinistedDate}" pattern="${dateFormatPattern}"/>
${formattedProjectedFinistedDate}<hr>
<fmt:formatDate var="formattedActualFinishDate" value="${status.current.actualFinishDate}" pattern="${dateFormatPattern}"/>
${formattedActualFinishDate}
</td>
<td width="5%">${status.current.pojectedCost}<hr>${status.current.actualCost}</td>
<td width="5%">${status.current.totalProjectedPeriodSavings}<hr>${status.current.totalActualPeriodSavings}</td>
<td width="5%">
<c:set var="selectedProjectStatus" value='${status.current.projectStatus}'/>
  <c:forEach var="vvPeiProjectStatusBean" items="${vvPeiProjectStatusCollection}" varStatus="statusProject">
  <c:set var="currentProjectStatus" value='${statusProject.current.projectStatus}'/>
  <c:choose>
   <c:when test="${currentProjectStatus == selectedProjectStatus}">
    ${statusProject.current.projectStatusDesc}
   </c:when>
  </c:choose>
  </c:forEach>
</td>
</tr>

<c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>
   </table>
</c:if>
   <!-- If the collection is empty say no data found -->

<c:if test="${empty peiProjectViewListBeanCollection}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
	</c:if>

<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden">
<input type="hidden" name="rowCount" id="rowCount" value="${rowCount}">

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<tcmis:saveRequestParameter/>

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

<!--Uncomment for production-->
</tcmis:form>
</body>
</html:html>