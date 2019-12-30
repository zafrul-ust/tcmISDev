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

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

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
<script type="text/javascript" src="/js/common/admin/facilityPermission.js"></script>


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
	<fmt:message key="label.editFacilityPermissions"/> (${fullName})
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
noDefault:"<fmt:message key="label.cannotRemoveDefault"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onLoad="editOnLoad()">

<tcmis:form action="/editfacilitypermissionmain.do" onsubmit="return submitOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
 ${tcmISError}
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null and empty requestScope['tcmISError']}">
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
			  
<c:if test="${UserFacilityAdminViewBeanCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:set var="adminCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty UserFacilityAdminViewBeanCollection}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>
	<c:set var="adminCount" value='${0}'/>

	<c:forEach var="UserFacilityAdminViewBean" items="${UserFacilityAdminViewBeanCollection}" varStatus="status">

	<c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
	    <th width="15%"><fmt:message key="label.facility"/></th>
    <th width="5%">
    	    <fmt:message key="label.access"/>
    	    <c:if test="${empty firstLine}">
    	    <br/>
    		<input type="checkbox" name="ok${status.index}" id="ok${status.index}" onClick="checkAll(${status.index})" style="display:none"/>
		    <c:set var="firstLine" value='No'/>
    		</c:if>
    </th>
    	<th width="5%"><fmt:message key="label.admin"/></th>
    	<th width="5%"><fmt:message key="label.default"/></th>
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

    <c:set var="readonly" value="disabled='disabled'"/>
    <c:set var="adminreadonly" value="disabled='disabled'"/>
	<c:if test="${status.current.userAccess == 'Admin'}">
	 	<c:set var="readonly" value=""/>
	 	<c:set var="adminCount" value='${1}'/>
	</c:if>
	<c:if test="${status.current.userAccess == 'Grant Admin'}">
	 	<c:set var="adminreadonly" value=""/>
	 	<c:set var="readonly" value=""/>
	 	<c:set var="adminCount" value='${1}'/>
	 </c:if>

    <c:set var="accesschecked" value=""/>
	<c:if test="${status.current.facilityAccess eq 'Y'}">
     	<c:set var="accesschecked" value="checked='checked'"/>
	</c:if>
	
    <c:set var="adminchecked" value=""/>
	<c:if test="${status.current.adminRole == 'Admin' || status.current.adminRole == 'Grant Admin'}">
     	<c:set var="adminchecked" value="checked='checked'"/>
	</c:if>
	
    <c:set var="defaultchecked" value=""/>
	<c:if test="${status.current.facilityId eq status.current.defaultFacilityId}">
	    <c:set var="defaultchecked" value="checked='checked'"/>
	</c:if>

   <tr class="${colorClass}">
     <td width="15%">
       	<input type="hidden" name="UserFacilityAdminViewBean[${status.index}].modified" id="modified_${status.index}" value=""/>
		<input type="hidden" name="UserFacilityAdminViewBean[${status.index}].facilityId" id="facility_id_${status.index}" value="${status.current.facilityId}"/>
     ${status.current.facilityName}
     </td>
     <td width="5%" align="center">
       	<input type="hidden" name="UserFacilityAdminViewBean[${status.index}].oldFacilityAccess" id="old_facilityAccess_${status.index}" value="${status.current.facilityAccess}"/>
     	<input type="hidden" name="UserFacilityAdminViewBean[${status.index}].facilityAccess" id="facilityAccess_${status.index}" value="${status.current.facilityAccess}"/>
     	<input type="checkbox" name="_facilityAccess_${status.index}" id="_facilityAccess_${status.index}" onClick="checkDefault(${status.index})" ${accesschecked} ${readonly} />
    <td width="5%" align="center">
       	<input type="hidden" name="UserFacilityAdminViewBean[${status.index}].oldAdminRole" id="old_adminRole_${status.index}" value="${status.current.adminRole}"/>
     	<input type="hidden" name="UserFacilityAdminViewBean[${status.index}].adminRole" id="adminRole_${status.index}" value="${status.current.adminRole}" />
		<input type="checkbox" name="_adminRole_${status.index}" id="_adminRole_${status.index}" value="${status.current.adminRole}" ${adminchecked} ${adminreadonly}/>
    </td>
    <td width="5%" align="center">
		<input type="hidden" name="UserFacilityAdminViewBean[${status.index}].defaultFacilityId" id="default_facility_id_${status.index}" value="${status.current.defaultFacilityId}"/>
     	<input type="hidden" name="UserFacilityAdminViewBean[${status.index}].makeDefault" id="makeDefault_${status.index}" value=""/>
     	<input type="radio" name="defaultButton" id="defaultButton_${status.index}" value="${status.current.facilityId}" ${defaultchecked} ${readonly}/>
     </td>
   </tr>

   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
	<c:if test="${adminCount > 0}">
 		<script type="text/javascript">
 <!--
  showUpdateLinks = true;
  for(i=0;i<${dataCount+1};i++) {
  		if( document.getElementById("ok"+i) != null ) 
  			document.getElementById("ok"+i).style["display"]="";
  }
 //-->
 		</script>
	</c:if>
  </table>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty UserFacilityAdminViewBeanCollection}" >

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
 <div id="hiddenElements" style="display: none;"></div>
<input type="hidden" name="uAction" id="uAction" value="editupdate"/>
<input type="hidden" name="personnelId" id="personnelId" value="${personnelId}" />
<input type="hidden" name="userId" id="userId" value="${userId}" />
<input type="hidden" name="companyId" id="companyId" value="${param.companyId}" />
<input type="hidden" name="companyName" id="companyName" value="${param.companyName}" />
<input type="hidden" name="facilityId" id="facilityId" value="${param.facilityId}" />
<input type="hidden" name="closeAndRefresh" id="closeAndRefresh" value="${closeAndRefresh}" />
<input type="hidden" name="totalLines" id="totalLines" value="${dataCount}" >

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>